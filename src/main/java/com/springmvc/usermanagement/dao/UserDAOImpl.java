package com.springmvc.usermanagement.dao;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.springmvc.usermanagement.model.User;
import com.springmvc.usermanagement.model.UserAttribute;

@Repository
public class UserDAOImpl implements UserDAO {
	static final Logger logger = Logger.getLogger(UserDAOImpl.class);
	public static final String COLLECTION_NAME_USER = "user";
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void addUser(User user) throws Exception {
		user.setId(UUID.randomUUID().toString());
		mongoTemplate.insert(user, COLLECTION_NAME_USER);
	}
	
	@Override
	public void updateUser(User user) throws Exception {
		mongoTemplate.save(user, COLLECTION_NAME_USER);
	}
	
	@Override
	public void deleteUser(User user) throws Exception {
		mongoTemplate.remove(user, COLLECTION_NAME_USER);
	}
	
	@Override
	public User getUserById(String id) throws Exception {
		Query searchUserQuery = new Query(Criteria.where(UserAttribute.id.name()).is(id));
		return mongoTemplate.findOne(searchUserQuery, User.class);
	}
	
	@Override
	public User getUserByUserName(String userName) throws Exception {
		Query searchUserQuery = new Query(Criteria.where(UserAttribute.userName.name()).is(userName));
		return mongoTemplate.findOne(searchUserQuery, User.class);
	}
	
	@Override
	public List<User> getUserList() throws Exception {
		return mongoTemplate.findAll(User.class, COLLECTION_NAME_USER);
	}

}
