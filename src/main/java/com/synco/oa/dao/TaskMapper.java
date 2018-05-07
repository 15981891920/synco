package com.synco.oa.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;

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
	 * 查询最后一次插入时间
	 */
	Date findTaskInsertTime();
}
