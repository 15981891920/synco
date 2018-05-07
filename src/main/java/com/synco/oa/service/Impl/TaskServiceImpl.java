package com.synco.oa.service.Impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.synco.oa.dao.TaskMapper;
import com.synco.oa.pojo.Task;
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
	public int inserTaskInfoId(Task task) {
		return taskMapper.inserTaskInfoId(task);
	}

	/**
	 * 判断时间是否新增任务
	 * 
	 * @author 10049
	 */
	@Override
	public Integer findTaskInsertTime(Task task, String createdTime) {
		SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date maxTime = taskMapper.findTaskInsertTime();
		Integer a = null;
		try {
			if (simdf.parse(simdf.format(maxTime)).getTime() < simdf.parse(createdTime).getTime()) {
				System.out.println("1");
				a = inserTaskInfoId(task);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("-------------" + a);
		return a;
	}

}
