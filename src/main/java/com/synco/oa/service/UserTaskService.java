package com.synco.oa.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.synco.oa.pojo.User_task;

public interface UserTaskService {

	/**
	 * 新增中间表信息
	 * 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	Integer InsertUserTask(User_task userTask);

	/**
	 * 新增判断
	 * 
	 * @param task
	 * @param createdTime
	 * @param updateTime
	 * @param task_id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	Integer findTaskInsertTime(User_task userTask, String createdTime, String updateTime, String task_id);

	/**
	 * 查询任务id
	 * 
	 * @param task_id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	String findTaskId(User_task usertask);

	/**
	 * 根据userid查询taskid
	 * 
	 * @param task_id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	List<User_task> findTaskIdByUserId(String user_id);

	/**
	 * 查询个人所得分
	 * 
	 * @param user_id
	 * @param task_id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	Integer findUserIntegralByTask(User_task usertask);

	/**
	 * 修改个人所得分
	 * 
	 * @param user_id
	 * @param task_id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	Integer editUserIntegralByTask(User_task usertask);

	/**
	 * 查询用户权限
	 * 
	 * @param user_id
	 * @param task_id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	Integer findUsertaskrole(String user_id, String task_id);

	/**
	 * 修改用户权限
	 * 
	 * @param user_id
	 * @param task_id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	Integer editUsertaskrole(String user_id, String task_id);
}
