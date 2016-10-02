package com.springmvc.usermanagement.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

public class User {
	private String id;
	@NotEmpty
	private String userName;
	@NotEmpty
	private String name;
	@NotEmpty
	private String lastName;
	@NotEmpty @Email
	private String email;
	
	public User(){}
	
	public User(String userName, String name, String lastName, String email){
		this.userName = userName;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
	}
	
	public boolean isNew() {
		return StringUtils.isEmpty(this.id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName="+userName + ", name=" + name + ", lastName=" + lastName + ", email=" + email;
	}
	
}
