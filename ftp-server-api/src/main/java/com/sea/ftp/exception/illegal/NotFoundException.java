package com.sea.ftp.exception.illegal;

import com.sea.ftp.exception.FTPServerRuntimeException;

/**
 * 
 * 未找到异常
 * 
 * @author sea
 */
@SuppressWarnings("serial")
public class NotFoundException extends FTPServerRuntimeException {

	public NotFoundException() {
		super();
	}

	public NotFoundException(String message, Throwable cause, String... args) {
		super(message, cause, args);
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}

}
