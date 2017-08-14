package com.cn.striverfeng.service.impl;

import com.cn.striverfeng.core.APIMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.striverfeng.dao.IUserDao;
import com.cn.striverfeng.pojo.User;
import com.cn.striverfeng.service.IUserService;
import org.springframework.util.Assert;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDao;
//	@Override
	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		return this.userDao.selectByPrimaryKey(userId);
	}
	@APIMapping("com.cn.striverfeng.service.userService")
	public User getUser(String userId){
		Assert.notNull(userId);
		User user=new User();
		user.setAge(12);
		user.setId(Integer.valueOf(userId));
		user.setPassword("123456");
		user.setUserName("张三");
		return  user;
	}
}
