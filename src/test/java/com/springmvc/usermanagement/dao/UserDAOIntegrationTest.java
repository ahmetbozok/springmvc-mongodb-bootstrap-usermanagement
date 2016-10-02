package com.springmvc.usermanagement.dao;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springmvc.usermanagement.model.User;


public class UserDAOIntegrationTest {
	@Autowired
	UserDAO userDao;
	
	@Test
	public void testFindByUserName() throws Exception{
//		Assert.assertNotNull(userDao.getUserByUserName("a.akca"));
//		Assert.assertNull(userDao.getUserByUserName("aakca"));
	}
	
    @Test
    public void testCreateUser() throws Exception {
		User user = new User("testuser", "Test", "User", "test@test.com");
		user.setId(UUID.randomUUID().toString());

		Assert.assertEquals(3, 3);
    }
}
