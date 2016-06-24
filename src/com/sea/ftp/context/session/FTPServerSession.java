package com.sea.ftp.context.session;

import com.sea.ftp.context.Context;
import com.sea.ftp.ftplet.FileSystemView;
import com.sea.ftp.user.User;

/**
 * 
 * FTP会话
 * 
 * @author sea
 */
public interface FTPServerSession extends Context {
	/**
	 * 获取当前的登录用户
	 * 
	 * @return
	 */
	public User getCurrentUser();

	/**
	 * 设置文件系统视图
	 * 
	 * @param view
	 */
	public void setSystemFileView(FileSystemView view);

	/**
	 * 获取文件系统视图
	 * 
	 * @return
	 */
	public FileSystemView getSystemFileView();
}