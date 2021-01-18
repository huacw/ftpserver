package com.sea.ftp.server.impl.config.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 用户虚拟路径
 * 
 * @author sea
 */
@XmlRootElement(name = "virtual-path")
@XmlAccessorType(XmlAccessType.NONE)
public class VirtualPath {
	@XmlAttribute(required = true)
	private String name;
	@XmlAttribute(required = true)
	private String path;
	@XmlAttribute(required = true)
	private String authority = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}