package com.sea.ftp.server.impl.config.xml.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.sea.ftp.server.impl.config.xml.enumeration.EncryptedStrategyType;
import com.sea.ftp.server.impl.config.xml.enumeration.adapter.EncryptedStrategyTypeAdapter;

/**
 * 
 * 用户
 * 
 * @author sea
 */
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.NONE)
public class User {
	@XmlAttribute(name = "user-name", required = true)
	private String userName;
	@XmlAttribute
	private String password;
	@XmlAttribute(required = true)
	private String home;
	private List<VirtualPath> virtualPaths;
	@XmlAttribute(required = true)
	private String authority = "";
	@XmlAttribute(name = "encrypt-strategy")
	@XmlJavaTypeAdapter(EncryptedStrategyTypeAdapter.class)
	private EncryptedStrategyType encryptedStrategy = EncryptedStrategyType.none;

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

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	@XmlElement(name = "virtual-path")
	public List<VirtualPath> getVirtualPaths() {
		return virtualPaths;
	}

	public void setVirtualPaths(List<VirtualPath> virtualPaths) {
		this.virtualPaths = virtualPaths;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public EncryptedStrategyType getEncryptedStrategy() {
		return encryptedStrategy;
	}

	public void setEncryptedStrategy(EncryptedStrategyType encryptedStrategy) {
		this.encryptedStrategy = encryptedStrategy;
	}

	public static void main(String[] args) {
		System.out.println(EncryptedStrategyType.MD5.name());
	}
}
