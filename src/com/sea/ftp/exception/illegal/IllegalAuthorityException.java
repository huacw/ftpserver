package com.sea.ftp.exception.illegal;

import com.sea.ftp.exception.FTPServerRuntimeException;

/**
 * 
 * 无效权限异常
 * 
 * @author sea
 */
@SuppressWarnings("serial")
public class IllegalAuthorityException extends FTPServerRuntimeException {
	public IllegalAuthorityException() {
		super();
	}

	public IllegalAuthorityException(String message, Throwable cause,
			String... args) {
		super(message, cause, args);
	}

	public IllegalAuthorityException(String message) {
		super(message);
	}

	public IllegalAuthorityException(Throwable cause) {
		super(cause);
	}

}
