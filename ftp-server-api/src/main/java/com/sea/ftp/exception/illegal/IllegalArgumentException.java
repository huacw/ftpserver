package com.sea.ftp.exception.illegal;

import com.sea.ftp.exception.FTPServerRuntimeException;

/**
 * 
 * 无效参数异常
 * 
 * @author sea
 */
@SuppressWarnings("serial")
public class IllegalArgumentException extends FTPServerRuntimeException {
	public IllegalArgumentException() {
		super();
	}

	public IllegalArgumentException(String message, Throwable cause,
			String... args) {
		super(message, cause, args);
	}

	public IllegalArgumentException(String message) {
		super(message);
	}

	public IllegalArgumentException(Throwable cause) {
		super(cause);
	}

}
