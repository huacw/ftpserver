package com.sea.ftp.user.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sea.ftp.user.User;
import com.sea.ftp.user.UserManager;

/**
 * 
 * 默认的用户管理类
 *
 * @author sea
 */
public class DefaultUserManager implements UserManager {
	private Map<String, User> registeredUsers = new HashMap<String, User>();

	@Override
	public void save(User user) {
		// 如果用户已经注册过，当前用户信息覆盖原用户信息
		registeredUsers.put(user.getUserName(), user);
	}

	/**
	 * 获取系统已经注册的所有用户
	 * 
	 * @return
	 */
	@Override
	public Set<User> getAllUsers() {
		Set<User> users = new HashSet<User>();
		users.addAll(registeredUsers.values());
		return users;
	}

	/**
	 * 根据用户名获取用户
	 */
	@Override
	public User getUserByName(String userName) {
		return registeredUsers.get(userName);
	}
}
