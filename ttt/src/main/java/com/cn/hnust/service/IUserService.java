package com.cn.hnust.service;


import java.util.List;

import com.cn.hnust.pojo.ConditionUser;
import com.cn.hnust.pojo.User;

public interface IUserService {
	public User getUserById(int userId);

	public List<User> selectAll();

	public int updateByPrimaryKey(User user);

	public int deleteByPrimaryKey(int id);

	public int insertSelective(User user);

	public List<User> searchUsers(ConditionUser conditionUser);
}
