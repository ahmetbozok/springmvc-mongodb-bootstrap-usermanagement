package com.springmvc.usermanagement.dao;

import java.util.List;

import com.springmvc.usermanagement.model.User;

public interface UserDAO {
	public void addUser(User user) throws Exception;

	public void updateUser(User user) throws Exception;
	
	public void deleteUser(User user) throws Exception;
	
	public User getUserById(String id) throws Exception;
	
	public User getUserByUserName(String userName) throws Exception;

	public List<User> getUserList() throws Exception;
}
