package com.sea.ftp.server.impl.config.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 数据库初始用户
 * 
 * @author sea
 */
@XmlRootElement(name = "db-user")
@XmlAccessorType(XmlAccessType.NONE)
public class DBUser {
	@XmlAttribute(name = "db-driver-class", required = true)
	private String dbDriverClass;
	@XmlAttribute(name = "db-url", required = true)
	private String dbUrl;
	@XmlAttribute(name = "db-user-name", required = true)
	private String dbUserName;
	@XmlAttribute(name = "db-password", required = true)
	private String dbPassword;
	@XmlAttribute(name = "insert-user", required = true)
	private String insertUserSQL;
	@XmlAttribute(name = "update-user", required = true)
	private String updateUserSQL;
	@XmlAttribute(name = "delete-user", required = true)
	private String deleteUserSQL;
	@XmlAttribute(name = "select-user", required = true)
	private String selectUserSQL;
	@XmlAttribute(name = "select-all-user", required = true)
	private String selectAllUserSQL;

	public String getDbDriverClass() {
		return dbDriverClass;
	}

	public void setDbDriverClass(String dbDriverClass) {
		this.dbDriverClass = dbDriverClass;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getInsertUserSQL() {
		return insertUserSQL;
	}

	public void setInsertUserSQL(String insertUserSQL) {
		this.insertUserSQL = insertUserSQL;
	}

	public String getUpdateUserSQL() {
		return updateUserSQL;
	}

	public void setUpdateUserSQL(String updateUserSQL) {
		this.updateUserSQL = updateUserSQL;
	}

	public String getDeleteUserSQL() {
		return deleteUserSQL;
	}

	public void setDeleteUserSQL(String deleteUserSQL) {
		this.deleteUserSQL = deleteUserSQL;
	}

	public String getSelectUserSQL() {
		return selectUserSQL;
	}

	public void setSelectUserSQL(String selectUserSQL) {
		this.selectUserSQL = selectUserSQL;
	}

	public String getSelectAllUserSQL() {
		return selectAllUserSQL;
	}

	public void setSelectAllUserSQL(String selectAllUserSQL) {
		this.selectAllUserSQL = selectAllUserSQL;
	}
}