/**
 * 
 */
package com.synco.oa.service.Impl;

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
public class UserMapperServiceImpl implements UserMapperService{
	
	@Resource
	private UserMapper userMapper;

	@Override
	public int inserUserInfoId(User user) {
		// TODO Auto-generated method stub
		return userMapper.inserUserInfoId(user);
	}

	@Override
	public int selectUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectUser(user);
	}
	
	
}
