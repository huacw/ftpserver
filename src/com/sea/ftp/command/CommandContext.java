package com.sea.ftp.command;

import com.sea.ftp.context.application.FTPServerContext;
import com.sea.ftp.context.request.FTPRequest;
import com.sea.ftp.context.response.FTPResponse;
import com.sea.ftp.context.session.FTPServerSession;

/**
 * 
 * 命令的上下文
 * 
 * @author sea
 */
public class CommandContext {
	// FTP服务器全局上下文
	private FTPServerContext serverContext;
	// FTP请求会话
	private FTPServerSession ftpServerSession;
	// FTP的请求对象
	private FTPRequest request;
	// FTP的响应对象
	private FTPResponse response;

	public FTPServerContext getServerContext() {
		return serverContext;
	}

	public void setServerContext(FTPServerContext serverContext) {
		this.serverContext = serverContext;
	}

	public FTPServerSession getFtpServerSession() {
		return ftpServerSession;
	}

	public void setFtpServerSession(FTPServerSession ftpServerSession) {
		this.ftpServerSession = ftpServerSession;
	}

	public FTPRequest getRequest() {
		return request;
	}

	public void setRequest(FTPRequest request) {
		this.request = request;
	}

	public FTPResponse getResponse() {
		return response;
	}

	public void setResponse(FTPResponse response) {
		this.response = response;
	}

}
