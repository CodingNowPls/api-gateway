package com.striverfeng.service;

import com.striverfeng.pojo.User;

public interface IUserService {

	User getUserById(int userId);

	User getUser(Integer userId);
}
