package com.sea.ftp.server.impl.config.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * FTP服务器xml配置文件根节点
 * 
 * @author sea
 */
@XmlRootElement(name = "server")
@XmlAccessorType(XmlAccessType.NONE)
public class Server {
	@XmlAttribute(required = true)
	private String id;
	@XmlElement(required = true)
	private Listener listener;
	@XmlElement(name = "user-manager", required = true)
	private UserManager um;
	@XmlElement(name = "native-filesystem")
	private NativeFilesystem nf;
	@XmlElement(name = "commands")
	private Commands commands;
	@XmlElement(name = "message")
	private Message message;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Listener getListener() {
		return listener;
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public UserManager getUm() {
		return um;
	}

	public void setUm(UserManager um) {
		this.um = um;
	}

	public NativeFilesystem getNf() {
		return nf;
	}

	public void setNf(NativeFilesystem nf) {
		this.nf = nf;
	}

	public Commands getCommands() {
		return commands;
	}

	public void setCommands(Commands commands) {
		this.commands = commands;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
