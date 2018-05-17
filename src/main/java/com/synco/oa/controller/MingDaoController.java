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
@Api("测试明道API")
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

	/**
	 * 获取项目下成员任务甘特图用列表（全部任务）
	 * 
	 * @param access_token
	 * @param folder_id
	 * @return
	 */
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
	public String TasksInsert(String access_token) throws Exception {
		// 拼接url
		String urls = "https://api.mingdao.com/v2/task/get_task_list?access_token=" + access_token
				+ "&project_id=all&page_index=1&page_size=20&filter_type=6&status=0&tag_ids=&without_tag=false&sort=10&is_star=false&time_format=true&format=json&account=true";
		String result = HttpClientUtils.get(urls.toString(), "UTF-8");
		List<Task> tt = JSONObject.parseArray(JsonUtil.getJsonPojo(result), Task.class);
		Task task = null;
		User_task usertask = null;
		for (Task taskJson : tt) {
			task = new Task();
			usertask = new User_task();
			for (tasks tasks : taskJson.getTasks()) {
				task.setTask_id(tasks.getTask_id());
				usertask = JSONObject.parseObject(JsonUtil.getJsonPojo(getUserInfo(access_token)), User_task.class);
				String taskInfo = getTaskInfo(access_token, tasks.getTask_id());
				tasks taskIn = JSONObject.parseObject(JsonUtil.getJsonPojo(taskInfo), tasks.class);
				String aid = userService.findUserIdbyAid(usertask.getUser_id());
				usertask.setUser_id(usertask.getUser_id());
				usertask.setTask_id(tasks.getTask_id());
				if (taskIn.getCharge_user().getAccount_id().equals(aid)) {
					usertask.setTaskrole_id(2);
					// taskIn.setUserState("2");
				} else {
					usertask.setTaskrole_id(3);
					// taskIn.setUserState("3");
				}
				taskService.findTaskInsertTime(task, taskIn.getCreate_time(), taskIn.getUpdate_time(),
						tasks.getTask_id());
				userTaskService.findTaskInsertTime(usertask, taskIn.getCreate_time(), taskIn.getUpdate_time(),
						tasks.getTask_id());
			}
		}
		return "OK";
	}

	@RequestMapping("/getTasks")
	@ResponseBody
	public String getTask(String access_token, String taskfen) throws Exception {
		TasksInsert(access_token);
		List<tasks> listJson = new ArrayList<tasks>();
		User_task usertask = JSONObject.parseObject(JsonUtil.getJsonPojo(getUserInfo(access_token)), User_task.class);
		List<User_task> taskList = userTaskService.findTaskIdByUserId(usertask.getUser_id());
		for (User_task user_task : taskList) {
			String taskInfo = getTaskInfo(access_token, user_task.getTask_id());
			tasks taskIn = JSONObject.parseObject(JsonUtil.getJsonPojo(taskInfo), tasks.class);
			String aid = userService.findUserIdbyAid(usertask.getUser_id());
			if (taskIn.getCharge_user().getAccount_id().equals(aid)) {
				// usertask.setTaskrole_id(2);
				taskIn.setUserState("2");
			} else {
				// usertask.setTaskrole_id(3);
				taskIn.setUserState("3");
			}
			taskIn.setTaskIntgarl(taskService.findTaskIntegral(user_task.getTask_id()));
			taskIn.setTaskState(taskService.taskStateList(user_task.getTask_id()));
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
