package com.synco.oa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.synco.oa.pojo.Members;
import com.synco.oa.pojo.Task;
import com.synco.oa.pojo.User;
import com.synco.oa.pojo.User_task;
import com.synco.oa.pojo.tasks;
import com.synco.oa.service.TaskService;
import com.synco.oa.service.UserMapperService;
import com.synco.oa.service.UserTaskService;
import com.synco.oa.util.HttpClientUtils;
import com.synco.oa.util.JsonUtil;
import io.swagger.annotations.Api;

/**
 * @author LiQian
 *
 */
@Controller
@RequestMapping("/Test")
public class MingDaoController {

	@Resource
	private UserMapperService userService;

	@Resource
	private TaskService taskService;

	@Resource
	private UserTaskService userTaskService;

	/**
	 * 获取当前登录用户的基本信息
	 * 
	 * @param access_token
	 * @return
	 */
	@RequestMapping("/getUserInfo")
	@ResponseBody
	public String getUserInfo(String access_token) throws Exception {
		String url = "https://api.mingdao.com/v1/passport/get_passport_detail?access_token=" + access_token;
		String result = HttpClientUtils.get(url.toString(), "UTF-8");
		// 转化实体类
		User pojo = JSONObject.parseObject(JsonUtil.getJsonPojo(result), User.class);
		if (pojo == null) {
			return "{\"task_id\":\"233\"}";
		}
		int nums = userService.selectUser(pojo);
		if (nums > 0) {
			return result;
		} else {
			pojo.setUser_integral(0);
			nums = userService.inserUserInfoId(pojo);
			if (nums >= 1) {
				return result;
			}
		}
		return result;
	}

	/**
	 * 获取当前登陆用户的总积分
	 * 
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUserIntegral")
	@ResponseBody
	public Map<String, Object> getUserIntegral(String access_token) throws Exception {
		User userinfo = JSONObject.parseObject(JsonUtil.getJsonPojo(getUserInfo(access_token)), User.class);
		String userid = userinfo.getUser_id();
		int userIn = userService.findUserIntegral(userid);
		Map<String, Object> userMap = new HashMap<>();
		userMap.put("user_id", userid);
		userMap.put("userIntegral", userIn);
		return userMap;
	}

	/*
	 * @RequestMapping("/getFoldersGantt")
	 * 
	 * @ResponseBody public String getFoldersGantt(String access_token, String
	 * folder_id) throws Exception { String url =
	 * "https://api.mingdao.com/v2/task/get_folder_task_gantt?access_token=" +
	 * access_token + "&folder_id=" + folder_id; String result =
	 * HttpClientUtils.get(url.toString(), "UTF-8");
	 * System.out.println(url.toString()); return result; }
	 */
	/**
	 * 获取当前登录账户的任务列表
	 * 
	 * @param access_token
	 * @return
	 */
	@RequestMapping("/TasksInsert")
	@ResponseBody
	public List<tasks> TasksInsert(String access_token) throws Exception {
		// 拼接url
		String urls = "https://api.mingdao.com/v2/task/get_task_list?access_token=" + access_token
				+ "&project_id=all&page_index=1&page_size=20&filter_type=6&status=0&tag_ids=&without_tag=false&sort=10&is_star=false&time_format=true&format=json&account=true";
		String result = HttpClientUtils.get(urls.toString(), "UTF-8");
		List<Task> tt = JSONObject.parseArray(JsonUtil.getJsonPojo(result), Task.class);
		User_task usertask = JSONObject.parseObject(JsonUtil.getJsonPojo(getUserInfo(access_token)), User_task.class);
		if (usertask == null) {
			return null;
		}
		Task task = null;
		tasks taskIn = null;
		List<tasks> taskslist = new ArrayList<tasks>();
		if (tt != null) {
			for (Task taskJson : tt) {
				task = new Task();
				for (tasks tasks : taskJson.getTasks()) {
					task.setTask_id(tasks.getTask_id());
					taskIn = JSONObject.parseObject(JsonUtil.getJsonPojo(getTaskInfo(access_token, tasks.getTask_id())),
							tasks.class);
					if (taskIn == null) {
						continue;
					}
					taskslist.add(taskIn);
					String aid = userService.findUserIdbyAid(usertask.getUser_id());
					usertask.setTask_id(tasks.getTask_id());
					if (taskIn.getCharge_user().getAccount_id().equals(aid)) {
						usertask.setTaskrole_id(2);
					} else {
						usertask.setTaskrole_id(3);
					}
					taskService.findTaskInsertTime(task);
					userTaskService.findTaskInsertTime(usertask, taskIn);
				}
			}
		}

		return taskslist;
	}

	/**
	 * 获取项目下成员全部任务信息
	 * 
	 * @param access_token
	 * @param folder_id
	 * @return
	 */
	@RequestMapping("/getTasks")
	@ResponseBody
	public String getTask(String access_token, String taskfen) throws Exception {
		List<tasks> taskslist = TasksInsert(access_token);
		if (taskslist == null) {
			return "{\"error\":\"用户访问令牌失效，请重新登陆\"}";
		}
		List<tasks> listJson = new ArrayList<tasks>();
		User_task usertask = JSONObject.parseObject(JsonUtil.getJsonPojo(getUserInfo(access_token)), User_task.class);
		for (tasks taskIn : taskslist) {
			String aid = userService.findUserIdbyAid(usertask.getUser_id());
			if (taskIn.getCharge_user().getAccount_id().equals(aid)) {
				taskIn.setUserState("2");
			} else {
				taskIn.setUserState("3");
			}
			taskIn.setTaskIntgarl(taskService.findTaskIntegral(taskIn.getTask_id()));
			taskIn.setTaskState(taskService.taskStateList(taskIn.getTask_id()));
			usertask.setTask_id(taskIn.getTask_id());
			taskIn.setUserIntgarl(userTaskService.findUserIntegralByTask(usertask));
			listJson.add(taskIn);
		}
		String JSONarr = taskService.TaskJson(JSON.toJSONString(listJson), taskfen);
		return JSONarr;
	}

	/**
	 * 获取任务详情（主要是获取当前任务所在阶段）
	 * 
	 * @param access_token
	 * @param folder_id
	 * @return
	 */
	@RequestMapping("/getTaskInfo")
	@ResponseBody
	public String getTaskInfo(String access_token, String task_id) throws Exception {
		// 拼接url
		String url = "https://api.mingdao.com/v2/task/get_task_detail?access_token=" + access_token + "&task_id="
				+ task_id;
		String result = HttpClientUtils.get(url.toString(), "UTF-8");
		return result;
	}
}
