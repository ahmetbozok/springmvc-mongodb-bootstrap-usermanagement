package com.springmvc.usermanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Matchers.any;

import com.springmvc.usermanagement.dao.UserDAO;
import com.springmvc.usermanagement.model.User;

public class UserDataServciceTest {
	@Autowired
	UserDAO dao;
	
	@InjectMocks
	DataServiceImp dataService;
	
	@Spy
	List<User> users = new ArrayList<User>();
	
	@Test
	public void saveEmployee() throws Exception{
//		doNothing().when(dao).addUser(any(User.class));
//		dataService.addUser(any(User.class));
//		verify(dao, atLeastOnce()).addUser(any(User.class));
	}
	
	public List<User> getUserList(){
		User u1 = new User();
		u1.setId(UUID.randomUUID().toString());
		u1.setUserName("a.akca");
		u1.setName("Ahmet");
		u1.setLastName("Akça");
		u1.setEmail("a.akca@gmail.com");
		
		User u2 = new User();
		u2.setId(UUID.randomUUID().toString());
		u2.setUserName("m.kara");
		u2.setName("Mehmet");
		u2.setLastName("Kara");
		u2.setEmail("m.kara@gmail.com");
		
		users.add(u1);
		users.add(u2);
		return users;
	}
}
