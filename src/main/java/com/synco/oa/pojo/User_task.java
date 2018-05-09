/**
 * 
 */
package com.synco.oa.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author LiQian 任务子表实体类
 */
public class User_task implements Serializable {
	private Integer id;
	@JSONField(name = "user_id")
	private String user_id;
	private String task_id;
	private Integer taskrole_id;
	private Integer userIntegral;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public Integer getTaskrole_id() {
		return taskrole_id;
	}

	public void setTaskrole_id(Integer taskrole_id) {
		this.taskrole_id = taskrole_id;
	}

	public Integer getUserIntegral() {
		return userIntegral;
	}

	public void setUserIntegral(Integer userIntegral) {
		this.userIntegral = userIntegral;
	}

}
