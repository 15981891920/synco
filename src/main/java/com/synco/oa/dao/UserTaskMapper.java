package com.synco.oa.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.synco.oa.pojo.User_task;

@Mapper
public interface UserTaskMapper {

	/**
	 * 新增中间表信息
	 * 
	 * @return
	 */
	int InsertUserTask(User_task userTask);

	/**
	 * 查询最后一次插入时间
	 * 
	 * @return
	 */
	Date findInsertTime();

	/**
	 * 查询任务id
	 * 
	 * @param task_id
	 * @return
	 */
	String findTaskId(@Param("task_id") String task_id);

	/**
	 * 查询个人所得分
	 * 
	 * @param user_id
	 * @param task_id
	 * @return
	 */
	int findUserIntegralByTask(@Param("user_id") String user_id, @Param("task_id") String task_id);

	/**
	 * 修改个人所得分
	 * 
	 * @param user_id
	 * @param task_id
	 * @return
	 */
	int editUserIntegralByTask(@Param("user_id") String user_id, @Param("task_id") String task_id);

	/**
	 * 查询用户权限
	 * 
	 * @param user_id
	 * @param task_id
	 * @return
	 */
	int findUsertaskrole(@Param("user_id") String user_id, @Param("task_id") String task_id);

	/**
	 * 修改用户权限
	 * 
	 * @param user_id
	 * @param task_id
	 * @return
	 */
	int editUsertaskrole(@Param("user_id") String user_id, @Param("task_id") String task_id);
}
