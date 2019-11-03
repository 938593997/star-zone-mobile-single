package com.starzone.service.master.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starzone.dao.BaseDao;
import com.starzone.dao.master.UserDao;
import com.starzone.pojo.User;
import com.starzone.service.impl.BaseServiceImpl;
import com.starzone.service.master.UserService;

/**
 * 用户操作实现类
 * @doc 说明
 * @FileName UserServiceImpl.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年4月13日
 * @history 1.0.0.0 2019年4月13日 下午12:06:03 created by【qiu_hf】
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	protected BaseDao<User> getMapper() {
		return this.userDao;
	}
	
}
