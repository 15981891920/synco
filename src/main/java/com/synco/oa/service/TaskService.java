package com.synco.oa.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.synco.oa.pojo.Task;

public interface TaskService {

	/**
	 * 新增任务id
	 * 
	 * @param task
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	Integer inserTaskInfoId(Task task);

	/**
	 * 查询最后一次插入时间
	 * 
	 * @param task
	 * @param createdTime
	 * @param updateTime
	 * @param task_id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	Integer findTaskInsertTime(Task task);

	/**
	 * 查询任务id
	 * 
	 * @param task_id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	String findTaskId(String task_id);

	/**
	 * 查询任务总积分
	 * 
	 * @param task_id
	 * @param integral
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	Integer findTaskIntegral(String task_id);

	/**
	 * 修改任务总积分
	 * 
	 * @param task
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	Integer editTaskIntegral(Task task);

	/**
	 * 查询任务状态
	 * 
	 * @param task_id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	String taskStateList(String task_id);

	/**
	 * 查询状态列表
	 * 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	List<Task> taskStateListAll();

	/**
	 * 修改任务状态
	 * 
	 * @param taskState
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	Integer editTaskState(String task_id, Integer taskState);

	/**
	 * 处理任务JSON数据
	 * 
	 * @param json
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	String TaskJson(String json, String taskfen);

}
