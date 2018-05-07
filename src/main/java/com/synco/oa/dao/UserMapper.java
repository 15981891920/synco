/**
 * 
 */
package com.synco.oa.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.synco.oa.pojo.User;

/**
 * @author LiQian 用户信息接口
 */
@Mapper
public interface UserMapper {

	/**
	 * 
	 * 新增存储用户ID以及账户ID user_integral积分默认为0 返回值为int,标识是否插入成功
	 */
	int inserUserInfoId(User user);

	/**
	 * 查询DB中是否存在该用户,存在则显示,不存在则跳过
	 */
	int selectUser(User user);

}