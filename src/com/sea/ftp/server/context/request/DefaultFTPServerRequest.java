package com.sea.ftp.server.context.request;

import com.sea.ftp.context.AbstractContext;
import com.sea.ftp.context.request.FTPRequest;
import com.sea.ftp.context.session.FTPServerSession;

/**
 * 默认的FTP请求对象
 * 
 * @author sea
 * 
 */
public class DefaultFTPServerRequest extends AbstractContext implements
		FTPRequest {
	/** 服务器session键值 */
	public static final String FTP_SERVER_SESSION = "ftp.server.session";
	/** 服务器请求命令键值 */
	public static final String FTP_REQUEST_CMD = "ftp.server.request.cmd";
	/** 服务器请求命令参数键值 */
	public static final String FTP_REQUEST_CMD_ARGS = "ftp.server.request.cmd.args";

	@Override
	public FTPServerSession getFTPServerSession() {
		Object session = getAtrribute(FTP_SERVER_SESSION);
		if (session == null) {
			return null;
		}
		if (session instanceof FTPServerSession) {
			return (FTPServerSession) session;
		}
		throw new IllegalArgumentException("illegal.session.data");
	}

}
