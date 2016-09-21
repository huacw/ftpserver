package com.sea.ftp.user;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sea.ftp.constants.Constants;
import com.sea.ftp.exception.illegal.IllegalArgumentException;
import com.sea.ftp.exception.illegal.IllegalConfigException;
import com.sea.ftp.exception.illegal.NotFoundException;
import com.sea.ftp.server.impl.config.xml.enumeration.EncryptedStrategyType;
import com.sea.ftp.user.authority.Authority;
import com.sea.ftp.util.StringUtils;

/**
 * 
 * FTP用户
 * 
 * @author sea
 */
public class User {
	/**
	 * 
	 * 用户路径
	 * 
	 * @author sea
	 */
	protected class UserPath {
		private String path;// 实际路径地址
		private List<Authority> authorities;// 路径权限

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public List<Authority> getAuthorities() {
			return authorities;
		}

		public void setAuthorities(List<Authority> authorities) {
			this.authorities = authorities;
		}
	}

	// 工作目录
	private Map<String, UserPath> workDirectroies = new HashMap<String, User.UserPath>();
	/**
	 * 用户默认工作目录
	 */
	public static final String USER_HOME = "/";

	private String userName;// 用户名
	private String password;// 密码
	private EncryptedStrategyType encryptedStrategy;// 加密方式

	/**
	 * 获取用户默认工作目录
	 * 
	 * @return 用户默认工作目录
	 */
	public String getHomeDirectory() {
		try {
			return getWorkDirectroy(USER_HOME);
		} catch (NotFoundException e) {
			throw new NotFoundException("notfound.homedir", null, userName);
		}
	}

	/**
	 * 设置用户默认路径以及权限
	 * 
	 * @param path
	 *            用户默认路径
	 * @param authorities
	 *            权限
	 */
	public void setHomeDirectroy(String path, Authority... authorities) {
		setUserDirectroy(USER_HOME, path, authorities);
	}

	/**
	 * 设置用户路径以及权限
	 * 
	 * @param pathName
	 *            用户路径名称
	 * @param path
	 *            用户路径
	 * @param authorities
	 *            权限
	 */
	public void setUserDirectroy(String pathName, String path, Authority... authorities) {
		// 判断是否为有效路径名称
		if (StringUtils.isBlank(pathName) || (!pathName.startsWith("/"))) {
			throw new IllegalArgumentException("illegal.user.pathname", null, pathName, userName);
		}
		if (workDirectroies.containsKey(pathName)) {
			throw new IllegalConfigException("user.same.virthpath", null, pathName, userName);
		}
		// 判断是否为有效路径
		if (StringUtils.isBlank(path) || (!new File(path).isAbsolute())) {
			throw new IllegalArgumentException("illegal.user.path", null, path, userName);
		}
		UserPath userPath = new UserPath();
		userPath.setPath(path);
		// 添加用户权限
		if (authorities != null) {
			userPath.setAuthorities(Arrays.asList(authorities));
		}
		workDirectroies.put(pathName, userPath);
	}

	/**
	 * 获取用户工作目录
	 * 
	 * @param name
	 *            工作目录名称
	 * @return 用户工作目录
	 */
	public String getWorkDirectroy(String name) {
		if (!workDirectroies.containsKey(name)) {
			throw new NotFoundException("notfound.userdir", null, name, userName);
		}
		// 如果路径名称为空或者不是以'/'开始，抛出异常
		if (StringUtils.isBlank(name) || (!name.startsWith("/"))) {
			throw new IllegalArgumentException("illegal.user.pathname", null, name, userName);
		}
		// 判断是否为用户虚拟路径
		int index = name.indexOf(name, 1);
		if (index == -1) {
			return name;
		}
		name = name.substring(0, index);
		if (workDirectroies.containsKey(name)) {
			return name;
		}
		return USER_HOME;
	}

	/**
	 * 获取用户绝对路径
	 * 
	 * @param name
	 *            路径名称
	 * @param relativePath
	 *            相对访问地址
	 * @return 绝对路径
	 */
	public String getAbstractUserPath(String name, String relativePath) {
		return getAbstractUserFile(name, relativePath).getAbsolutePath();
	}

	/**
	 * 获取用户绝对路径
	 * 
	 * @param name
	 *            路径名称
	 * @param relativePath
	 *            相对访问地址
	 * @return 绝对路径文件
	 */
	public File getAbstractUserFile(String name, String relativePath) {
		UserPath userPath = workDirectroies.get(getWorkDirectroy(name));
		if (userPath == null) {
			throw new NotFoundException("notfound.userdir", null, name, userName);
		}
		return new File(userPath.getPath(), relativePath);
	}

	/**
	 * 返回用户访问路径的绝对地址
	 * 
	 * @param accessPath
	 * @return
	 */
	public String getAbstractUserPath(String accessPath) {
		String[] results = parseAccessPath(new File(accessPath));
		return getAbstractUserPath(results[0], results[1]);
	}

	/**
	 * 返回用户访问路径的绝对地址
	 * 
	 * @param accessPath
	 * @return
	 */
	public File getAbstractUserFile(String accessPath) {
		String[] results = parseAccessPath(new File(accessPath));
		return getAbstractUserFile(results[0], results[1]);
	}

	/**
	 * 解析用户访问路径
	 * 
	 * @param file
	 * @return
	 */
	private String[] parseAccessPath(File file) {
		String workDirName = getWorkDirNameOfAccessPath(file);
		String relativePath = file == null ? USER_HOME : file.getAbsolutePath().substring(file.getAbsolutePath().indexOf(workDirName) + workDirName.length());
		return new String[] { workDirName, relativePath };
	}

	/**
	 * 获取访问路径的虚拟路径名称
	 * 
	 * @param file
	 * @return
	 */
	private String getWorkDirNameOfAccessPath(File file) {
		if (file == null) {
			return USER_HOME;
		}
		String parent = file.getParent();
		if (workDirectroies.containsKey(parent)) {
			return parent;
		} else {
			return getWorkDirNameOfAccessPath(file.getParentFile());
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	public EncryptedStrategyType getEncryptedStrategy() {
		return encryptedStrategy;
	}

	public void setEncryptedStrategy(EncryptedStrategyType encryptedStrategy) {
		this.encryptedStrategy = encryptedStrategy;
	}

	/**
	 * 是否为匿名用户
	 * 
	 * @return
	 */
	public boolean isAnonymousUser() {
		if (StringUtils.isBlank(userName) || Constants.ANONYMOUS_USER_NAME.equalsIgnoreCase(userName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取用户所有工作目录
	 * 
	 * @return
	 */
	public List<File> getUserWorkDirs() {
		List<File> userWorkPaths = new ArrayList<>();
		Set<String> keySet = workDirectroies.keySet();
		for (String key : keySet) {
			userWorkPaths.add(new File(getWorkDirectroy(key)));
		}
		return userWorkPaths;
	}
}
