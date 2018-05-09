package com.synco.oa.service;

import com.synco.oa.pojo.User;

/**
 * @author LiQian 用户服务层
 */
public interface UserMapperService {

	int inserUserInfoId(User user);

	int selectUser(User user);

	/**
	 * 根据负责人账户id查询用户id
	 * 
	 * @param Aid
	 * @return
	 */
	String findUserIdbyAid(String uid);
}
