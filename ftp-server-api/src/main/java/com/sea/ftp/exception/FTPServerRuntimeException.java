package com.sea.ftp.exception;

import com.sea.ftp.message.MessageCode;
import com.sea.ftp.message.MessageCode.MessageType;
import com.sea.ftp.message.i18n.LocalizedMessageResource;

/**
 * 
 * FTP服务器运行时异常
 * 
 * @author sea
 */
@SuppressWarnings("serial")
public class FTPServerRuntimeException extends RuntimeException {

	public FTPServerRuntimeException() {
		super();
	}

	public FTPServerRuntimeException(String message, Throwable cause,
			String... args) {
		super(getLocalMessage(message, args), cause);
	}

	public FTPServerRuntimeException(String message) {
		this(message, null);
	}

	public FTPServerRuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * 获取本地消息
	 * 
	 * @param message
	 * @param args
	 * @return
	 */
	private static String getLocalMessage(String message, String... args) {
		MessageCode code = MessageCode.newMessageCode(MessageType.Error);
		code.setMsgKey(message);
		return LocalizedMessageResource.newInstance().getMessage(code, args);
	}

}
