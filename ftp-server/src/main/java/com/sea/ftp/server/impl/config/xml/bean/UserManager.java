package com.sea.ftp.server.impl.config.xml.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.sea.ftp.enumeration.EncryptedStrategyType;
import com.sea.ftp.server.impl.config.xml.enumeration.UserConfigFileType;
import com.sea.ftp.server.impl.config.xml.enumeration.UserMangerInitializationType;
import com.sea.ftp.server.impl.config.xml.enumeration.adapter.EncryptedStrategyTypeAdapter;
import com.sea.ftp.server.impl.config.xml.enumeration.adapter.UserConfigFileTypeAdapter;
import com.sea.ftp.server.impl.config.xml.enumeration.adapter.UserManagerInitializationTypeAdapter;

/**
 * 
 * 用户管理
 * 
 * @author sea
 */
@XmlRootElement(name = "user-manager")
@XmlAccessorType(XmlAccessType.NONE)
public class UserManager {
	@XmlAttribute(required = true)
	@XmlJavaTypeAdapter(UserManagerInitializationTypeAdapter.class)
	private UserMangerInitializationType type = UserMangerInitializationType.none;
	private List<User> users;
	@XmlAttribute(name = "config-file")
	private String userConfig;
	@XmlAttribute(name = "file-type")
	@XmlJavaTypeAdapter(UserConfigFileTypeAdapter.class)
	private UserConfigFileType fileType;
	@XmlElement(name = "db-user")
	private DBUser dbUser;
	@XmlAttribute(name = "encrypt-password")
	private boolean encryptPassword = false;
	@XmlAttribute
	private boolean anonymous = false;
	@XmlAttribute(name = "anonymous-authority")
	private String anonymousAuthority = "none";
	@XmlAttribute(name = "anonymous-path")
	private String anonymousPath;
	@XmlAttribute(name = "encrypt-strategy")
	@XmlJavaTypeAdapter(EncryptedStrategyTypeAdapter.class)
	private EncryptedStrategyType encryptedStrategy = EncryptedStrategyType.none;

	public UserMangerInitializationType getType() {
		return type;
	}

	public void setType(UserMangerInitializationType type) {
		this.type = type;
	}

	@XmlElement(name = "user")
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getUserConfig() {
		return userConfig;
	}

	public void setUserConfig(String userConfig) {
		this.userConfig = userConfig;
	}

	public DBUser getDbUser() {
		return dbUser;
	}

	public void setDbUser(DBUser dbUser) {
		this.dbUser = dbUser;
	}

	public boolean isEncryptPassword() {
		encryptPassword = encryptPassword || encryptedStrategy != EncryptedStrategyType.none;
		return encryptPassword;
	}

	public void setEncryptPassword(boolean encryptPassword) {
		this.encryptPassword = encryptPassword;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	public String getAnonymousAuthority() {
		return anonymousAuthority;
	}

	public void setAnonymousAuthority(String anonymousAuthority) {
		this.anonymousAuthority = anonymousAuthority;
	}

	public String getAnonymousPath() {
		return anonymousPath;
	}

	public void setAnonymousPath(String anonymousPath) {
		this.anonymousPath = anonymousPath;
	}

	public EncryptedStrategyType getEncryptedStrategy() {
		return encryptedStrategy;
	}

	public void setEncryptedStrategy(EncryptedStrategyType encryptedStrategy) {
		this.encryptedStrategy = encryptedStrategy;
	}

	public UserConfigFileType getFileType() {
		return fileType;
	}

	public void setFileType(UserConfigFileType fileType) {
		this.fileType = fileType;
	}

}
