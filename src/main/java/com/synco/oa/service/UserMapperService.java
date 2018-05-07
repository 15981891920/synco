/**
 * 
 */
package com.synco.oa.service;

import com.synco.oa.pojo.User;

/**
 * @author LiQian
 * 用户服务层
 */
public interface UserMapperService {
	 int inserUserInfoId(User user);
	 
	 int selectUser(User user);
}
