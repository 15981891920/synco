/**
 * 
 */
package com.synco.oa.pojo;

import java.io.Serializable;

/**
 * @author LiQian
 *	角色实体类
 */
public class Taskrole implements Serializable {
	private Integer id;
	private String taskrole_name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTaskrole_name() {
		return taskrole_name;
	}
	public void setTaskrole_name(String taskrole_name) {
		this.taskrole_name = taskrole_name;
	}
	
	
}
