package com.sea.ftp.user.factory.builder;

import com.sea.ftp.user.UserManager;

/**
 * 
 * 用户管理创建接口
 * 
 * @author sea
 */
public interface UserManagerBuilder {
	/**
	 * 根据配置创建用户管理类
	 * 
	 * @param umCfg
	 * @param canCreateHome
	 * @return
	 */
	public UserManager createUserManager(com.sea.ftp.server.impl.config.xml.bean.UserManager umCfg, boolean canCreateHome);
}