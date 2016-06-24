package com.sea.ftp.user;

import java.util.Set;

/**
 * 
 * FTP用户管理接口
 *
 * @author sea 
 */
public interface UserManager {
	/**
	 * 保存用户信息
	 * 
	 * @param user
	 *            用户信息
	 */
	public void save(User user);

	/**
	 * 获取系统已经注册的所有用户
	 * 
	 * @return
	 */
	public Set<User> getAllUsers();

	/**
	 * 根据用户名获取用户
	 */
	public User getUserByName(String userName);
}
