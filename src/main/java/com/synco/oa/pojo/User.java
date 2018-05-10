/**
 * 
 */
package com.synco.oa.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author LiQian
 *
 *         用户信息实体类
 */
public class User implements Serializable {

	private Integer id;
	private String user_id;
	@JSONField(name = "account_id")
	private String account_id;
	private String avatar;
	private Integer user_integral;
	private String user_token;
	private String user_flush_token;
	private String full_name;

	// private String user_username;
	// private String user_phone;
	// private String user_email;
	// private String user_company;

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

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

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public Integer getUser_integral() {
		return user_integral;
	}

	public void setUser_integral(Integer user_integral) {
		this.user_integral = user_integral;
	}

	public String getUser_token() {
		return user_token;
	}

	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}

	public String getUser_flush_token() {
		return user_flush_token;
	}

	public void setUser_flush_token(String user_flush_token) {
		this.user_flush_token = user_flush_token;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
