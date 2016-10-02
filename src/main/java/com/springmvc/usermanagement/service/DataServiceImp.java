package com.springmvc.usermanagement.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springmvc.usermanagement.dao.UserDAO;
import com.springmvc.usermanagement.exception.UserExistException;
import com.springmvc.usermanagement.model.User;

@Service
public class DataServiceImp implements DataService {
	static final Logger logger = Logger.getLogger(DataServiceImp.class);
	
	@Autowired
	private UserDAO userDao;
	
	@Transactional
	@Override
	public void addUser(User user) throws Exception {
		User savedUser = userDao.getUserByUserName(user.getUserName());
		if(savedUser != null) {
			throw new UserExistException();
		}
		userDao.addUser(user);
	}

	@Override
	public void updateUser(User user) throws Exception {
		userDao.updateUser(user);
	}

	@Override
	public void deleteUser(User user) throws Exception {
		userDao.deleteUser(user);
	}

	@Override
	public User getUserById(String id) throws Exception {
		return userDao.getUserById(id);
	}

	@Override
	public User getUserByUserName(String userName) throws Exception {
		return userDao.getUserByUserName(userName);
	}

	@Override
	public List<User> getUserList() throws Exception {
		return userDao.getUserList();
	}

}
