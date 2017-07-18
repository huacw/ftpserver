package com.sea.ftp.exception;

/**
 * 数据连接异常
 * 
 * @author sea
 *
 */
public class DataConnectionException extends FTPServerException {

	private static final long serialVersionUID = -2098666629178217772L;

	public DataConnectionException() {
		super();
	}

	public DataConnectionException(final String msg) {
		super(msg);
	}

	public DataConnectionException(final Throwable th) {
		super(th);
	}

	public DataConnectionException(final String msg, final Throwable th) {
		super(msg, th);
	}
}
