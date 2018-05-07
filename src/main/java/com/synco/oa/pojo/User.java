/**
 * 
 */
package com.synco.oa.pojo;

import java.io.Serializable;

import javax.persistence.Entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author LiQian
 *
 * 用户信息实体类
 */
@Entity
public class User implements Serializable {
	
	
	private Integer id;
	@JSONField(name="user_id")
	private String user_id;
	@JSONField(name="account_id")
	private String account_id;
	private Float user_integral;
	private String user_token;
	private String user_flush_token;
	
	//private String user_username;
	//private String user_phone;
	//private String user_email;
	//private String user_company;
	
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
	public Float getUser_integral() {
		return user_integral;
	}
	public void setUser_integral(Float user_integral) {
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
	
	
}