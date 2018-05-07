/**
 * 
 */
package com.synco.oa.pojo;

import java.io.Serializable;

/**
 * @author LiQian
 *	任务子表实体类
 */
public class User_task implements Serializable {
	private Integer id;
	private Integer user_id;
	private Integer task_id;
	private Integer taskrole_id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getTask_id() {
		return task_id;
	}
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
	public Integer getTaskrole_id() {
		return taskrole_id;
	}
	public void setTaskrole_id(Integer taskrole_id) {
		this.taskrole_id = taskrole_id;
	}
	
}
