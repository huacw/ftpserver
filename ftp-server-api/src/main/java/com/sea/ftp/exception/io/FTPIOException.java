package com.sea.ftp.exception.io;

import com.sea.ftp.exception.FTPServerException;

/**
 * 
 * FTP服务器IO异常
 * 
 * @author sea
 */
@SuppressWarnings("serial")
public class FTPIOException extends FTPServerException {

	public FTPIOException() {
		super();
	}

	public FTPIOException(String message, Throwable cause, String... args) {
		super(message, cause, args);
	}

	public FTPIOException(String message) {
		super(message);
	}

	public FTPIOException(Throwable cause) {
		super(cause);
	}

}
