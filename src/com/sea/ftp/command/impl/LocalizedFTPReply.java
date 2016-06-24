package com.sea.ftp.command.impl;

import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.message.MessageCode;
import com.sea.ftp.message.MessageCode.MessageType;
import com.sea.ftp.message.i18n.LocalizedMessageResource;

/**
 * 
 * 本地化回复信息
 * 
 * @author sea
 */
public class LocalizedFTPReply implements FtpReply {
	// 本地化消息处理器
	private static LocalizedMessageResource lmr = LocalizedMessageResource.newInstance();
	// 消息键前缀
	private static final String REPLY_PREFIX = "ftpserver.reply";

	@Override
	public String getMessage(int code, String... args) {
		MessageCode eCode = MessageCode.newMessageCode(MessageType.Reply);
		eCode.setCode(code);
		eCode.setMsgKey(REPLY_PREFIX);
		return code + " " + lmr.getMessage(eCode, args);
	}

}
