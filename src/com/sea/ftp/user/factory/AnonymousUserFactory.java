package com.sea.ftp.user.factory;

import com.sea.ftp.constants.Constants;
import com.sea.ftp.user.User;
import com.sea.ftp.user.authority.Authority;

/**
 * 
 * 匿名用户工厂
 *
 * @author sea 
 */
public class AnonymousUserFactory {
	/**
	 * 创建匿名用户
	 * 
	 * @param path
	 *            匿名用户路径
	 * @param authorities
	 *            访问权限
	 * @return 匿名用户
	 */
	public static User createAnonymousUser(String path,
			Authority... authorities) {
		User user = new User();
		user.setUserName(Constants.ANONYMOUS_USER_NAME);
		user.setHomeDirectroy(path, authorities);
		return user;
	}
}
