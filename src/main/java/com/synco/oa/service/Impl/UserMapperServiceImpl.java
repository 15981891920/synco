package com.synco.oa.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.synco.oa.dao.UserMapper;
import com.synco.oa.pojo.User;
import com.synco.oa.service.UserMapperService;

/**
 * @author LiQian
 *
 */
@Service("UserMapperServiceImpl")
public class UserMapperServiceImpl implements UserMapperService {

	@Resource
	private UserMapper userMapper;

	@Override
	public int inserUserInfoId(User user) {
		return userMapper.inserUserInfoId(user);
	}

	@Override
	public int selectUser(User user) {
		return userMapper.selectUser(user);
	}

	@Override
	public String findUserIdbyAid(String uid) {
		return userMapper.findUserIdbyAid(uid);
	}

	@Override
	public Integer findUserIntegral(String userid) {
		return userMapper.findUserIntegral(userid);
	}

	@Override
	public int editUserIntegral(User user) {
		return userMapper.editUserIntegral(user);
	}

	@Override
	public List<User> selectUser2(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectUser2(user);
	}

	@Override
	public int modifyUser(User u) {
		// TODO Auto-generated method stub
		return userMapper.updateUser(u);
	}

	@Override
	public String findUserIdbyAidU(String aid) {
		return userMapper.findUserIdbyAidU(aid);
	}

}
