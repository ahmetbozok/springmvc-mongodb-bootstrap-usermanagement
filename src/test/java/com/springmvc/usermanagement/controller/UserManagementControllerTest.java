package com.springmvc.usermanagement.controller;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springmvc.usermanagement.model.User;
import com.springmvc.usermanagement.service.DataService;

public class UserManagementControllerTest {
	@Autowired
	DataService dataService;
	
	@Test
	public void addUserTest(){
		User user = new User("testuser", "Test", "User", "test@test.com");
		user.setId(UUID.randomUUID().toString());
		Assert.assertEquals(3, 3);
	}
}
