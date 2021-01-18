package com.sea.ftp.server.impl.config.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 消息配置
 *
 * @author sea
 */
@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.NONE)
public class Message {
	@XmlAttribute
	private String languages;

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

}
