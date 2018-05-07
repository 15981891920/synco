package com.synco.oa.service;

import java.util.Date;

import com.synco.oa.pojo.Task;

public interface TaskService {

	/**
	 * 新增任务id
	 * 
	 * @param task
	 * @return
	 */
	int inserTaskInfoId(Task task);

	/**
	 * 查询最后一次插入时间
	 */
	Integer findTaskInsertTime(Task task,String createdTime);

}
