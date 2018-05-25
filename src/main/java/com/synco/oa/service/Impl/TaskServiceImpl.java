package com.synco.oa.service.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.synco.oa.dao.TaskMapper;
import com.synco.oa.pojo.Task;
import com.synco.oa.pojo.tasks;
import com.synco.oa.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Resource
	TaskMapper taskMapper;

	/**
	 * 新增任务
	 * 
	 * @author 10049
	 */
	@Override
	public Integer inserTaskInfoId(Task task) {
		return taskMapper.inserTaskInfoId(task);
	}

	/**
	 * 判断时间是否新增任务 经过不断修改，改成了一个比较傻B的逻辑判断，最后就这样吧，不想改了
	 * 
	 * @author 10049
	 */
	@Override
	public Integer findTaskInsertTime(Task task, String createdTime, String updateTime) {
		SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date maxTime = taskMapper.findTaskInsertTime();
		Integer a = 100;
		if (maxTime == null) {
			a = inserTaskInfoId(task);
			return a;
		}
		try {
			if (simdf.parse(simdf.format(maxTime)).getTime() < simdf.parse(createdTime).getTime()) {
				a = inserTaskInfoId(task);
			} else if (simdf.parse(updateTime).getTime() > simdf.parse(simdf.format(maxTime)).getTime()) {
				String taskId = findTaskId(task.getTask_id());
				if (!taskId.equals(task.getTask_id())) {
					a = inserTaskInfoId(task);
				}
			} else {
				if (findTaskId(task.getTask_id()) == "C") {
					a = inserTaskInfoId(task);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public Integer findTaskIntegral(String task_id) {
		return taskMapper.findTaskIntegral(task_id);
	}

	@Override
	public String taskStateList(String task_id) {
		return taskMapper.taskStateList(task_id);
	}

	@Override
	public List<Task> taskStateListAll() {
		return taskMapper.taskStateListAll();
	}

	@Override
	public Integer editTaskState(String task_id, Integer taskState) {
		return taskMapper.editTaskState(task_id, taskState);
	}

	@Override
	public String findTaskId(String task_id) {
		String s = taskMapper.findTaskId(task_id);
		if (s == null) {
			s = "C";
		}
		return s;
	}

	@Override
	public String TaskJson(String json, String taskfen) {
		List<tasks> taskList = JSONObject.parseArray(json, tasks.class);
		List<tasks> taskList3 = new ArrayList<tasks>();
		String retuls = null;
		if (taskfen != null && taskfen != "") {
			if (taskfen.equals("1")) {
				taskfen = "已完成";
			}
			for (tasks tasks : taskList) {
				if (taskfen.equals(tasks.getUserState())) {
					taskList3.add(tasks);
					retuls = JSON.toJSONString(taskList3);
				} else if (taskfen.equals(tasks.getTaskState())) {
					taskList3.add(tasks);
					retuls = JSON.toJSONString(taskList3);
				}
			}
		} else {
			retuls = JSON.toJSONString(taskList);
		}
		return retuls;
	}

}
