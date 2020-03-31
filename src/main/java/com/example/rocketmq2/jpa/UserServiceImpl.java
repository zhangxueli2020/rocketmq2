package com.example.rocketmq2.jpa;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public void saveUser(TUser user) {
		if (null == user) {
			return;
		}
		userDao.save(user);
		return ;
	}

	@Override
	public TUser findByName(String name) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		return userDao.findById(name).orElse(null);
	}

	
	
}
