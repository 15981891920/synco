/**
 * 
 */
package com.synco.oa.pojo;

import java.io.Serializable;

/**
 * @author LiQian 项目状态实体
 */
public class Taskstate implements Serializable {
	private Integer id;
	private String taskstate_name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskstate_name() {
		return taskstate_name;
	}

	public void setTaskstate_name(String taskstate_name) {
		this.taskstate_name = taskstate_name;
	}

}
