package com.sea.ftp.server.impl.config.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * SSL配置
 *
 * @author sea
 */
@XmlRootElement(name = "ssl")
@XmlAccessorType(XmlAccessType.NONE)
public class SSLConfiguration {
	@XmlAttribute
	private boolean enable;

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
