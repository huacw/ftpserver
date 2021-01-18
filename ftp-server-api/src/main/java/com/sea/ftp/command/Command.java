package com.sea.ftp.command;

import com.sea.ftp.exception.io.FTPIOException;

/**
 * 
 * FTP命令接口
 *
 * @author sea 
 */
public interface Command {
	/**
	 * 命令执行方法
	 * 
	 * @param context
	 *            命令所需的上下文对象
	 * @throws FTPIOException
	 */
	public void execute(CommandContext context) throws FTPIOException;
}
