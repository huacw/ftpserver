package com.sea.ftp.server.impl.config.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 本地文件系统配置
 * 
 * @author sea
 */
@XmlRootElement(name = "native-filesystem")
@XmlAccessorType(XmlAccessType.NONE)
public class NativeFilesystem {
	@XmlAttribute(name = "case-insensitive")
	private boolean caseInsensitive;
	@XmlAttribute(name = "create-workdir")
	private boolean createWorkdir;

	public boolean isCaseInsensitive() {
		return caseInsensitive;
	}

	public void setCaseInsensitive(boolean caseInsensitive) {
		this.caseInsensitive = caseInsensitive;
	}

	public boolean isCreateWorkdir() {
		return createWorkdir;
	}

	public void setCreateWorkdir(boolean createWorkdir) {
		this.createWorkdir = createWorkdir;
	}

}
