package com.striverfeng.dao;

import com.striverfeng.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}