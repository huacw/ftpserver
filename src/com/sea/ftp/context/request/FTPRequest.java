package com.sea.ftp.context.request;

import com.sea.ftp.context.Context;
import com.sea.ftp.context.session.FTPServerSession;

/**
 * 
 * FTP请求对象
 * 
 * @author sea
 */
public interface FTPRequest extends Context {
	/**
	 * 获取当前请求所属会话
	 * 
	 * @return
	 */
	public FTPServerSession getFTPServerSession();
}
