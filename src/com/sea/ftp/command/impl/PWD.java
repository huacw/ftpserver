package com.sea.ftp.command.impl;

import java.io.File;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.exception.io.FTPIOException;
import com.sea.ftp.user.User;
import com.sea.ftp.util.ArrayUtil;

/**
 * 
 * (Print Working Directory,打印工作目录) 命令
 * 
 * 
 * @author sea
 */
public class PWD extends AbstractCommand {

	@Override
	public void done(CommandContext context) throws FTPIOException {
		User user = getLoginUser(context);
		if (user == null) {
			write(context, FtpReply.REPLY_332_NEED_ACCOUNT_FOR_LOGIN);
			return;
		}
		String[] args = getCmdArgs(context);
		String path = "/";
		if (!ArrayUtil.isEmpty(args)) {
			path = args[0];
		}
		File realPath = user.getAbstractUserFile(path);
		if (realPath == null || (!realPath.exists())) {
			write(context, FtpReply.REPLY_450_REQUESTED_FILE_ACTION_NOT_TAKEN);
			return;
		}
	}

}
