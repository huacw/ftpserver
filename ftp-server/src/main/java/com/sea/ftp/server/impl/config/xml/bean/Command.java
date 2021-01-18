package com.sea.ftp.server.impl.config.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 自定义命令配置
 * 
 * @author sea
 */
@XmlRootElement(name = "command")
@XmlAccessorType(XmlAccessType.NONE)
public class Command {
	@XmlAttribute(name = "name", required = true)
	private String cmdName;
	@XmlAttribute(name = "cmd-class", required = true)
	private String clazz;
	@XmlAttribute(name = "use-default")
	private Boolean useDefault;

	public String getCmdName() {
		return cmdName;
	}

	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public Boolean isUseDefault() {
		return useDefault;
	}

	public void setUseDefault(Boolean useDefault) {
		this.useDefault = useDefault;
	}
}
