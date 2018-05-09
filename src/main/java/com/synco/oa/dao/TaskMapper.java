package com.synco.oa.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.synco.oa.pojo.Task;

/**
 * 任务接口
 * 
 * @author 10049
 *
 */
@Mapper
public interface TaskMapper {

	/**
	 * 新增任务id
	 * 
	 * @param task
	 * @return
	 */
	int inserTaskInfoId(Task task);

	/**
	 * 查询任务id
	 * 
	 * @param task_id
	 * @return
	 */
	String findTaskId(@Param("task_id") String task_id);

	/**
	 * 查询最后一次插入时间
	 */
	Date findTaskInsertTime();

	/**
	 * 查询任务总积分
	 * 
	 * @param task_id
	 * @param integral
	 * @return
	 */
	int findTaskIntegral(@Param("task_id") String task_id);

	/**
	 * 查询任务状态
	 * 
	 * @param task_id
	 * @return
	 */

	String taskStateList(@Param("task_id") String task_id);

	/**
	 * 查询状态列表
	 * 
	 * @return
	 */
	List<Task> taskStateList();

	/**
	 * 修改任务状态
	 * 
	 * @param taskState
	 * @return
	 */
	int editTaskState(@Param("task_id") String task_id, @Param("taskState") Integer taskState);
}
