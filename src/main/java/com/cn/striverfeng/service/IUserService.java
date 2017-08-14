package com.cn.striverfeng.service;

import com.cn.striverfeng.pojo.User;

public interface IUserService {

	User getUserById(int userId);

	User getUser(String userId);
}
