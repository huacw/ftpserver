package com.sea.ftp.message;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 消息码
 * 
 * @author sea
 */
public class MessageCode {
	public static enum MessageType {
		Error, Reply, Info, Other;
	}

	@SuppressWarnings("serial")
	public static Map<MessageType, MessageCode> codeMapping = Collections.unmodifiableMap(new HashMap<MessageType, MessageCode>() {
		{
			this.put(MessageType.Error, new MessageCode());
			this.put(MessageType.Reply, new MessageCode());
			this.put(MessageType.Info, new MessageCode());
			this.put(MessageType.Other, new MessageCode());
		}
	});

	private int code = -1;// 消息编码
	private String msgKey;// 国际化消息键
	private String desc;// 消息描述

	private MessageCode() {
	}

	public static MessageCode newMessageCode(MessageType type) {
		MessageCode messageCode = codeMapping.get(type);
		messageCode.reset();
		return messageCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	private void reset() {
		code = -1;
		msgKey = null;
		desc = null;

	}
}
