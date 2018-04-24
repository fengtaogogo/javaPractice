package com.cn.hnust.dao;

import java.util.List;

import com.cn.hnust.pojo.ConditionUser;
import com.cn.hnust.pojo.User;

public interface IUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> selectAll();

	List<User> searchUsers(ConditionUser conditionUser);
 }