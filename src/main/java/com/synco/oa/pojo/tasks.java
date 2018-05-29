package com.synco.oa.pojo;

import java.util.List;

/**
 * JSON实体类
 * 
 * @author LiQian 任务ID，任务名称
 */
public class tasks {
	private String task_id;
	private String task_name;
	// 获取参与者信息 一个名字，一个头像的链接
	private List<Members> members;
	// 获取负责人的名字以及账户ID
	private User charge_user;
	// 获取项目创建时间
	private String create_time;
	// 最后一次修改时间
	private String update_time;
	// 项目总积分
	private Integer TaskIntgarl;
	// 项目状态
	private String TaskState;
	// 你在任务中的权限
	private String userState;
	// 个人所得分
	private Integer userIntgarl;

	public Integer getUserIntgarl() {
		return userIntgarl;
	}

	public void setUserIntgarl(Integer userIntgarl) {
		this.userIntgarl = userIntgarl;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public List<Members> getMembers() {
		return members;
	}

	public void setMembers(List<Members> members) {
		this.members = members;
	}

	public User getCharge_user() {
		return charge_user;
	}

	public void setCharge_user(User charge_user) {
		this.charge_user = charge_user;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public Integer getTaskIntgarl() {
		return TaskIntgarl;
	}

	public void setTaskIntgarl(Integer taskIntgarl) {
		TaskIntgarl = taskIntgarl;
	}

	public String getTaskState() {
		return TaskState;
	}

	public void setTaskState(String taskState) {
		TaskState = taskState;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

}
