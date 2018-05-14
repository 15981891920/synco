package com.synco.oa.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.synco.oa.pojo.User;

/**
 * @author LiQian 用户服务层
 */
public interface UserMapperService {

	/**
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	int inserUserInfoId(User user);

	/**
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	int selectUser(User user);

	/**
	 * 根据负责人账户id查询用户id
	 * 
	 * @param Aid
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	String findUserIdbyAid(String uid);

	/**
	 * 查询个人总积分
	 * 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	Integer findUserIntegral(String userid);

	/**
	 * 修改用户总积分
	 * 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	int editUserIntegral(User user);
	/**
	 * 查用户返回实体
	 * @param user
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	 List<User> selectUser2(User user);
	 /**
	  * 修改令牌
	  * @param u
	  * @return
	  */
	@Transactional(propagation = Propagation.REQUIRED)
	 int modifyUser(User u);
}
