package com.sea.ftp.server.impl.config.xml.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * FTP服务命令配置
 *
 * @author sea
 */
@XmlRootElement(name = "commands")
@XmlAccessorType(XmlAccessType.NONE)
public class Commands {
	@XmlAttribute(name = "use-default")
	private Boolean useDefault;
	private List<Command> commands;

	public Boolean isUseDefault() {
		return useDefault;
	}

	public void setUseDefault(Boolean useDefault) {
		this.useDefault = useDefault;
	}

	@XmlElement(name = "command")
	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

}
