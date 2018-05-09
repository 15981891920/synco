package com.synco.oa.service;

import com.synco.oa.pojo.User_task;

public interface UserTaskService {

	/**
	 * 新增中间表信息
	 * 
	 * @return
	 */
	int InsertUserTask(User_task userTask);

	/**
	 * 新增判断
	 * 
	 * @param task
	 * @param createdTime
	 * @param updateTime
	 * @param task_id
	 * @return
	 */
	Integer findTaskInsertTime(User_task userTask, String createdTime, String updateTime, String task_id);

	/**
	 * 查询任务id
	 * 
	 * @param task_id
	 * @return
	 */
	String findTaskId(String task_id);

	/**
	 * 查询个人所得分
	 * 
	 * @param user_id
	 * @param task_id
	 * @return
	 */
	int findUserIntegralByTask(String user_id, String task_id);

	/**
	 * 修改个人所得分
	 * 
	 * @param user_id
	 * @param task_id
	 * @return
	 */
	int editUserIntegralByTask(String user_id, String task_id);

	/**
	 * 查询用户权限
	 * 
	 * @param user_id
	 * @param task_id
	 * @return
	 */
	int findUsertaskrole(String user_id, String task_id);

	/**
	 * 修改用户权限
	 * 
	 * @param user_id
	 * @param task_id
	 * @return
	 */
	int editUsertaskrole(String user_id, String task_id);
}
