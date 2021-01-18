package com.sea.ftp.exception.illegal;

import com.sea.ftp.exception.FTPServerRuntimeException;

/**
 * 
 * 无效命令异常
 * 
 * @author sea
 */
@SuppressWarnings("serial")
public class IllegalCommandException extends FTPServerRuntimeException {
	public IllegalCommandException() {
		super();
	}

	public IllegalCommandException(String message, Throwable cause, String... args) {
		super(message, cause, args);
	}

	public IllegalCommandException(String message) {
		super(message);
	}

	public IllegalCommandException(Throwable cause) {
		super(cause);
	}

}
