package com.synco.oa.service;

import java.util.List;

import com.synco.oa.pojo.Task;

public interface TaskService {

	/**
	 * 新增任务id
	 * 
	 * @param task
	 * @return
	 */
	Integer inserTaskInfoId(Task task);

	/**
	 * 查询最后一次插入时间
	 */
	Integer findTaskInsertTime(Task task, String createdTime, String updateTime, String task_id);

	/**
	 * 查询任务id
	 * 
	 * @param task_id
	 * @return
	 */
	String findTaskId(String task_id);

	/**
	 * 查询任务总积分
	 * 
	 * @param task_id
	 * @param integral
	 * @return
	 */
	Integer findTaskIntegral(String task_id);

	/**
	 * 查询任务状态
	 * 
	 * @param task_id
	 * @return
	 */

	String taskStateList(String task_id);

	/**
	 * 查询状态列表
	 * 
	 * @return
	 */
	List<Task> taskStateListAll();

	/**
	 * 修改任务状态
	 * 
	 * @param taskState
	 * @return
	 */
	Integer editTaskState(String task_id, Integer taskState);

	/**
	 * 处理任务JSON数据
	 * 
	 * @param json
	 * @return
	 */
	String TaskJson(String json, String taskfen);

}
