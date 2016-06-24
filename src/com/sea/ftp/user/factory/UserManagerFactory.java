package com.sea.ftp.user.factory;

import java.util.HashMap;
import java.util.Map;

import com.sea.ftp.server.impl.config.xml.enumeration.UserMangerInitializationType;
import com.sea.ftp.user.UserManager;
import com.sea.ftp.user.factory.builder.UserManagerBuilder;
import com.sea.ftp.user.factory.builder.impl.DBUserManagerBuilder;
import com.sea.ftp.user.factory.builder.impl.LocalConfigUserManagerBuilder;
import com.sea.ftp.user.factory.builder.impl.PropertiesUserManagerBuilder;

/**
 * 
 * 用户管理工厂
 * 
 * @author sea
 */
public class UserManagerFactory {
	private Map<UserMangerInitializationType, UserManagerBuilder> ums = new HashMap<UserMangerInitializationType, UserManagerBuilder>();

	{
		ums.put(UserMangerInitializationType.file, new PropertiesUserManagerBuilder());
		ums.put(UserMangerInitializationType.db, new DBUserManagerBuilder());
		ums.put(UserMangerInitializationType.none, new LocalConfigUserManagerBuilder());
	}

	/**
	 * 创建用户工厂
	 * 
	 * @param umCfg
	 * @param canCreateHome
	 * @return
	 */
	public UserManager createUserManager(com.sea.ftp.server.impl.config.xml.bean.UserManager umCfg, boolean canCreateHome) {
		if (umCfg == null) {
			return null;
		}
		return ums.get(umCfg.getType()).createUserManager(umCfg, canCreateHome);
	}
}
