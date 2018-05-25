package com.synco.oa.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synco.oa.pojo.Task;
import com.synco.oa.pojo.User;
import com.synco.oa.pojo.User_task;
import com.synco.oa.service.TaskService;
import com.synco.oa.service.UserMapperService;
import com.synco.oa.service.UserTaskService;
import com.synco.oa.util.JsonUtil;

@Controller
@RequestMapping("/Tast")
public class TaskController {

	@Resource
	private TaskService taskService;

	@Resource
	private UserTaskService userTaskService;

	@Resource
	private UserMapperService userService;

	/**
	 * 获取任务列表
	 * 
	 * @return
	 */
	@RequestMapping("/getTaskState")
	@ResponseBody
	public List<Task> getTaskState() {
		return taskService.taskStateListAll();
	}

	/**
	 * 修改任务状态
	 * 
	 * @param taskid
	 * @param taskStateid
	 * @return
	 */
	@RequestMapping("/editTaskState")
	@ResponseBody
	public String editTaskState(String taskid, Integer taskStateid) {
		if (taskService.editTaskState(taskid, taskStateid) > 0) {
			return "OK";
		}
		return "NO";
	}

	/**
	 * 任务开始
	 * 
	 * @param taskid
	 * @return
	 */
	@RequestMapping("/taskStart")
	@ResponseBody
	public String taskStart(HttpServletRequest request) {
		String[] taskJson = request.getParameterValues("data");
		if (taskJson == null) {
			return "{\"ifSuccess\":\"null\"}";
		}
		String jb = JSON.toJSONString(taskJson);
		JSONArray jb1 = JSONObject.parseArray(jb);
		User_task user_task = null;
		for (int i = 0; i < jb1.size(); i++) {
			JSONObject a = JSONObject.parseObject(jb1.get(i).toString());
			user_task = new User_task();
			user_task.setTask_id(a.get("task_id").toString());
			String uid = userService.findUserIdbyAidU(a.get("user_id").toString());
			user_task.setUser_id(uid);
			user_task.setUserIntegral(Integer.valueOf(a.get("userIntegral").toString()));
			int c = userTaskService.editUserIntegralByTask(user_task);
			System.out.println("--------------------------" + c);
			if (c == 0) {
				return "{\"error\":\"105\",\"Reason\":\"个人所得分异常\"}";
			}
		}
		if (editTaskState(user_task.getTask_id(), 2).equals("ON")) {
			return "{\"error\":\"100\",\"Reason\":\"任务状态异常\"}";
		}
		return "{\"ifSuccess\":\"success\"}";
	}

	/**
	 * 任务结算
	 * 
	 * @param access_token
	 * @param task_id
	 * @return
	 */
	@RequestMapping("/taskSettlement")
	@ResponseBody
	public String taskSettlement(HttpServletRequest request) {
		String[] taskJson = request.getParameterValues("data");
		if (taskJson == null) {
			return "{\"ifSuccess\":\"null\"}";
		}
		User_task user_task = null;
		String jb = JSON.toJSONString(taskJson);
		JSONArray jb1 = JSONObject.parseArray(jb);
		for (int i = 0; i < jb1.size(); i++) {
			JSONObject a = JSONObject.parseObject(jb1.get(i).toString());
			user_task = new User_task();
			user_task.setTask_id(a.get("task_id").toString());
			String uid = userService.findUserIdbyAidU(a.get("user_id").toString());
			user_task.setUser_id(uid);
			user_task.setUserIntegral(Integer.valueOf(a.get("userIntegral").toString()));
			int c = userTaskService.editUserIntegralByTask(user_task);
			if (c > 0) {
				if (editTaskState(user_task.getTask_id(), 3).equals("ON")) {
					return "{\"error\":\"100\",\"Reason\":\"任务状态修改失败\"}";
				}
			} else {
				return "{\"error\":\"105\",\"Reason\":\"个人所得分异常\"}";
			}
		}
		return "{\"ifSuccess\":\"success\"}";
	}

	/**
	 * 任务完成
	 * 
	 * @param taskid
	 * @return
	 */
	@RequestMapping("/taskComplete")
	@ResponseBody
	public String taskComplete(HttpServletRequest request) {
		String[] taskJson = request.getParameterValues("data");
		if (taskJson == null) {
			return "{\"ifSuccess\":\"null\"}";
		}
		int a = 0;
		User user = new User();
		User_task user_task = null;
		String jb = JSON.toJSONString(taskJson);
		JSONArray jb1 = JSONObject.parseArray(jb);
		for (int i = 0; i < jb1.size(); i++) {
			JSONObject d = JSONObject.parseObject(jb1.get(i).toString());
			user_task = new User_task();
			user_task.setTask_id(d.get("task_id").toString());
			String uid = userService.findUserIdbyAidU(d.get("user_id").toString());
			user_task.setUser_id(uid);
			user_task.setUserIntegral(Integer.valueOf(d.get("userIntegral").toString()));
			a = userTaskService.findUserIntegralByTask(user_task);
			user.setUser_id(user_task.getUser_id());
			user.setUser_integral(userService.findUserIntegral(user_task.getUser_id()) + a);
			if (userService.editUserIntegral(user) > 0) {
				if (editTaskState(user_task.getTask_id(), 4).equals("OK")) {
					user_task.setUserIntegral(0);
					if (userTaskService.editUserIntegralByTask(user_task) <= 0) {
						return "{\"error\":\"105\",\"Reason\":\"个人所得分异常\"}";
					}
				} else {
					return "{\"error\":\"100\",\"Reason\":\"任务状态修改失败\"}";
				}
			} else {
				return "{\"error\":\"110\",\"Reason\":\"个人总分异常\"}";
			}
		}
		return "{\"ifSuccess\":\"success\"}";
	}

}
