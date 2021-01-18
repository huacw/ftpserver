package com.sea.ftp.exception.illegal;

import com.sea.ftp.exception.FTPServerRuntimeException;

/**
 * 
 * 无效的配置异常
 * 
 * @author sea
 */
@SuppressWarnings("serial")
public class IllegalConfigException extends FTPServerRuntimeException {
	public IllegalConfigException() {
		super();
	}

	public IllegalConfigException(String message, Throwable cause,
			String... args) {
		super(message, cause, args);
	}

	public IllegalConfigException(String message) {
		super(message);
	}

	public IllegalConfigException(Throwable cause) {
		super(cause);
	}

}
