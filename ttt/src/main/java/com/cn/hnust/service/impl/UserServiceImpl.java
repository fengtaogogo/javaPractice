package com.cn.hnust.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.hnust.dao.IUserDao;
import com.cn.hnust.pojo.ConditionUser;
import com.cn.hnust.pojo.User;
import com.cn.hnust.service.IUserService;


@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;
	@Override
	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		//User user=new User(001,"fengtao","666",100);
		return this.userDao.selectByPrimaryKey(userId);
	}
	@Override
	public List<User> selectAll() {
		// TODO Auto-generated method stub
		return this.userDao.selectAll();
	}
	@Override
	public int updateByPrimaryKey(User user) {
		// TODO Auto-generated method stub
		return this.userDao.updateByPrimaryKey(user);
	}
	@Override
	public int deleteByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return this.userDao.deleteByPrimaryKey(id);
	}
	@Override
	public int insertSelective(User user) {
		// TODO Auto-generated method stub
		return this.userDao.insertSelective(user);
	}
	public List<User> searchUsers(ConditionUser conditionUser) {
		// TODO Auto-generated method stub
		return this.userDao.searchUsers(conditionUser);
	}
	

}
