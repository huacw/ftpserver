package com.sea.ftp.user.factory.builder.impl;

import java.io.File;
import java.util.List;

import com.sea.ftp.enumeration.EncryptedStrategyType;
import com.sea.ftp.exception.illegal.IllegalConfigException;
import com.sea.ftp.server.impl.config.xml.bean.User;
import com.sea.ftp.server.impl.config.xml.bean.VirtualPath;
import com.sea.ftp.user.UserManager;
import com.sea.ftp.user.authority.Authority;
import com.sea.ftp.user.factory.AnonymousUserFactory;
import com.sea.ftp.user.impl.DefaultUserManager;

/**
 * 
 * 本地配置文件的用户管理创建类
 * 
 * @author sea
 */
public class LocalConfigUserManagerBuilder extends AbstractUserManagerBuilder {

	/**
	 * 构造FTP服务器用户
	 * 
	 * @param umCfg
	 * @param um
	 * @param cfgUser
	 * @param canCreateHome
	 */
	private void buildFTPUser(com.sea.ftp.server.impl.config.xml.bean.UserManager umCfg, UserManager um, User cfgUser, boolean canCreateHome) {
		com.sea.ftp.user.User ftpUser = new com.sea.ftp.user.User();
		ftpUser.setUserName(cfgUser.getUserName());
		// 用户权限
		Authority[] userAuths = parseAuth(cfgUser.getAuthority());
		ftpUser.setHomeDirectroy(cfgUser.getHome(), userAuths);
		ftpUser.setPassword(cfgUser.getPassword());
		// 创建用户目录
		createUserHome(ftpUser, canCreateHome);
		um.save(ftpUser);
		// 设置用户的加密策略
		buildUserEcryptedStrategy(umCfg, cfgUser, ftpUser);
		// 设置用户的虚拟目录
		buildUserVirtualPath(umCfg, cfgUser, ftpUser, userAuths);
	}

	/**
	 * 创建用户目录
	 * 
	 * @param ftpUser
	 * @param canCreateHome
	 */
	private void createUserHome(com.sea.ftp.user.User ftpUser, boolean canCreateHome) {
		List<File> userWorkDirs = ftpUser.getUserWorkDirs();
		for (File workDir : userWorkDirs) {
			if (!workDir.exists()) {
				if (canCreateHome) {
					workDir.mkdirs();
				} else {
					throw new IllegalConfigException("用户【" + ftpUser.getUserName() + "】的工作目录【" + workDir + "】不存在！");
				}
			}
		}
	}

	/**
	 * 设置用户的虚拟目录
	 * 
	 * @param umCfg
	 * @param cfgUser
	 * @param ftpUser
	 * @param userAuths
	 */
	private void buildUserVirtualPath(com.sea.ftp.server.impl.config.xml.bean.UserManager umCfg, User cfgUser, com.sea.ftp.user.User ftpUser, Authority[] userAuths) {
		List<VirtualPath> virtualPaths = cfgUser.getVirtualPaths();
		if (virtualPaths == null || virtualPaths.isEmpty()) {
			return;
		}
		// 设置虚拟目录用户权限
		for (VirtualPath virtualPath : virtualPaths) {
			Authority[] virtualPathAuths = parseAuth(virtualPath.getAuthority());
			ftpUser.setUserDirectroy(virtualPath.getName(), virtualPath.getPath(), virtualPathAuths == null ? userAuths : virtualPathAuths);
		}
	}

	/**
	 * 设置用户的加密侧路
	 * 
	 * @param umCfg
	 * @param cfgUser
	 * @param ftpUser
	 */
	private void buildUserEcryptedStrategy(com.sea.ftp.server.impl.config.xml.bean.UserManager umCfg, User cfgUser, com.sea.ftp.user.User ftpUser) {
		// 密码是否加密
		boolean isEncryptPassword = umCfg.isEncryptPassword();
		if (!isEncryptPassword) {
			return;
		}
		// 没有加密策略
		boolean noEncryptedStrategy = cfgUser.getEncryptedStrategy() == EncryptedStrategyType.none;
		if (noEncryptedStrategy) {
			ftpUser.setEncryptedStrategy(cfgUser.getEncryptedStrategy());
		} else {
			ftpUser.setEncryptedStrategy(umCfg.getEncryptedStrategy());
		}
		noEncryptedStrategy = ftpUser.getEncryptedStrategy() == EncryptedStrategyType.none;
		if (isEncryptPassword && noEncryptedStrategy) {
			throw new IllegalConfigException("user.config.encrypt.error", null, cfgUser.getUserName());
		}
	}

	@Override
	public UserManager createUserManager(com.sea.ftp.server.impl.config.xml.bean.UserManager umCfg, boolean canCreateHome) {
		UserManager um = null;
		if (umCfg == null) {
			throw new IllegalConfigException("usermanager.config.error");
		}
		um = new DefaultUserManager();
		boolean canAnonVisit = false;
		// 创建匿名用户
		if (umCfg.isAnonymous()) {
			com.sea.ftp.user.User anonymousUser = AnonymousUserFactory.createAnonymousUser(umCfg.getAnonymousPath(), parseAuth(umCfg.getAnonymousAuthority()));
			um.save(anonymousUser);
			canAnonVisit = true;
			// 创建工作目录
			createUserHome(anonymousUser, canCreateHome);
		}
		List<User> users = umCfg.getUsers();
		// 判断是否有可访问用户
		if (users == null && (!canAnonVisit)) {
			throw new IllegalConfigException("user.config.error");
		}

		// 构造FTP服务器用户
		for (User user : users) {
			buildFTPUser(umCfg, um, user, canCreateHome);
		}
		return um;
	}
}
