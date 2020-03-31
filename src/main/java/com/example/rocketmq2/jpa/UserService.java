package com.example.rocketmq2.jpa;

public interface UserService {

	void saveUser(TUser user);
	
	TUser findByName(String name);
}
