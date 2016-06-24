package com.sea.ftp.server;

/**
 * 
 * FTP服务器
 * 
 * @author sea 
 */
public interface FTPServer {
	/**
	 * 服务器启动方法
	 */
	public void start();

	/**
	 * 服务器停止方法
	 */
	public void stop();

	/**
	 * 服务器暂停方法
	 */
	public void suspended();

	/**
	 * 服务器恢复方法
	 */
	public void resume();

	/**
	 * 判断服务器是否启动
	 * 
	 * @return true-已启动，false-未启动
	 */
	public boolean isStarted();

	/**
	 * 判断服务器是否暂停
	 * 
	 * @return true-已暂停，false-未暂停
	 */
	public boolean isSuspended();
}
