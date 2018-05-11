package com.synco.oa.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.synco.oa.pojo.Task;
import com.synco.oa.pojo.User;
import com.synco.oa.pojo.User_task;
import com.synco.oa.service.TaskService;
import com.synco.oa.service.UserMapperService;
import com.synco.oa.service.UserTaskService;

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
	public String taskStart(String taskJson) {
		List<User_task> tt = JSONObject.parseArray(taskJson, User_task.class);
		for (User_task user_task : tt) {
			if (userTaskService.editUserIntegralByTask(user_task) > 0) {
				if (editTaskState(user_task.getTask_id(), 2).equals("ON")) {
					return "NO";
				}
			}
		}
		return "OK";
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
	public String taskSettlement(String taskJson) {
		List<User_task> tt = JSONObject.parseArray(taskJson, User_task.class);
		for (User_task usertask : tt) {
			if (userTaskService.editUserIntegralByTask(usertask) > 0) {
				if (editTaskState(usertask.getTask_id(), 3).equals("ON")) {
					return "NO";
				}
			}
		}
		return "OK";
	}

	/**
	 * 任务完成
	 * 
	 * @param taskid
	 * @return
	 */
	@RequestMapping("/taskComplete")
	@ResponseBody
	public String taskComplete(String taskJson) {
		List<User_task> tt = JSONObject.parseArray(taskJson, User_task.class);
		int a = 0;
		User user = new User();
		for (User_task usertask : tt) {
			a = userTaskService.findUserIntegralByTask(usertask);
			user.setUser_id(usertask.getUser_id());
			user.setUser_integral(userService.findUserIntegral(usertask.getUser_id()) + a);
			if (userService.editUserIntegral(user) > 0) {
				editTaskState(usertask.getTask_id(), 4);
				usertask.setUserIntegral(0);
				if (userTaskService.editUserIntegralByTask(usertask) <= 0) {
					return "NO";
				}
			}
		}
		return "OK";
	}

}