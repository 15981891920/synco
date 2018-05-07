/**
 * 
 */
package com.synco.oa.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author LiQian
 *
 *         任务信息实体类
 */

public class Task implements Serializable {

	private Integer id;
	private String task_id;
	private List<tasks> tasks;// 包含任务ID,和名称
	private Float task_integral;
	private Integer task_taskstate_id;
	private Float task_customer_score;
	private Float task_admin_score;
	private Date task_inserttime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public List<tasks> getTasks() {
		return tasks;
	}

	public void setTasks(List<tasks> tasks) {
		this.tasks = tasks;
	}

	public Float getTask_integral() {
		return task_integral;
	}

	public void setTask_integral(Float task_integral) {
		this.task_integral = task_integral;
	}

	public Integer getTask_taskstate_id() {
		return task_taskstate_id;
	}

	public void setTask_taskstate_id(Integer task_taskstate_id) {
		this.task_taskstate_id = task_taskstate_id;
	}

	public Float getTask_customer_score() {
		return task_customer_score;
	}

	public void setTask_customer_score(Float task_customer_score) {
		this.task_customer_score = task_customer_score;
	}

	public Float getTask_admin_score() {
		return task_admin_score;
	}

	public void setTask_admin_score(Float task_admin_score) {
		this.task_admin_score = task_admin_score;
	}

	public Date getTask_inserttime() {
		return task_inserttime;
	}

	public void setTask_inserttime(Date task_inserttime) {
		this.task_inserttime = task_inserttime;
	}

}
