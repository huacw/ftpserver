package com.sea.ftp.context.application;

import java.util.List;

import com.sea.ftp.command.CommandFactory;
import com.sea.ftp.context.Context;
import com.sea.ftp.context.session.FTPServerSession;
import com.sea.ftp.user.UserManager;

/**
 * 
 * FTP服务器上下文
 * 
 * @author sea 
 */
public interface FTPServerContext extends Context {
	/**
	 * 添加FTP会话
	 * 
	 * @param session
	 *            FTP会话
	 */
	public void addFTPServerSession(FTPServerSession session);

	/**
	 * 添加FTP会话，指定会话名称
	 * 
	 * @param name
	 *            会话名称
	 * @param session
	 *            FTP会话
	 */
	public void addFTPServerSession(String name, FTPServerSession session);

	/**
	 * 获取指定的FTP会话
	 * 
	 * @return FTP会话
	 */
	public List<FTPServerSession> getFTPServerSessions();

	/**
	 * 获取指定的FTP会话
	 * 
	 * @param name
	 *            FTP会话名称
	 * @return FTP会话
	 */
	public FTPServerSession getFTPServerSession(String name);

	/**
	 * 删除指定的FTP会话
	 * 
	 * @param name
	 *            FTP会话名称
	 * @return TODO
	 */
	public List<FTPServerSession> removeAllFTPServerSessions();

	/**
	 * 删除指定的FTP会话
	 * 
	 * @param name
	 *            FTP会话名称
	 * @return FTP会话
	 */
	public FTPServerSession removeFTPServerSession(String name);

	/**
	 * 添加用户管理工厂
	 * 
	 * @param um
	 *            用户管理工厂
	 */
	public void addUserManager(UserManager um);

	/**
	 * 获取用户管理工厂
	 * 
	 * @return 用户管理工厂
	 */
	public UserManager getUserManager();

	/**
	 * 删除用户管理工厂
	 */
	public void removeUserManager();

	/**
	 * 添加命令工厂
	 * 
	 * @param cm
	 *            命令工厂
	 */
	public void addCommandFactory(CommandFactory cm);

	/**
	 * 删除命令工厂
	 */
	public void removeCommandFactory();

	/**
	 * 获取命令工厂
	 * 
	 * @return 命令工厂
	 */
	public CommandFactory getCommandFactory();
}