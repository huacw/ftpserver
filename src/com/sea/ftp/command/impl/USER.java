package com.sea.ftp.command.impl;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.constants.Constants;
import com.sea.ftp.exception.io.FTPIOException;
import com.sea.ftp.user.User;
import com.sea.ftp.user.UserManager;
import com.sea.ftp.util.StringUtils;

/**
 * 
 * （User，用户登录）命令
 * 
 * 
 * 
 * @author sea
 */
public class USER extends AbstractCommand {

	@Override
	public void execute(CommandContext context) throws FTPIOException {
		UserManager userManager = context.getServerContext().getUserManager();
		String[] args = getCmdArgs(context);
		if (args == null || args.length == 0) {
			write(context, FtpReply.REPLY_332_NEED_ACCOUNT_FOR_LOGIN);
			return;
		}
		// 获取系统用户
		User user = null;
		String userName = args[0];
		if (StringUtils.isBlank(userName) || Constants.ANONYMOUS_USER_NAME.equalsIgnoreCase(userName)) {
			user = userManager.getUserByName(Constants.ANONYMOUS_USER_NAME);
		} else {
			user = userManager.getUserByName(userName);
		}
		if (user == null) {
			write(context, FtpReply.REPLY_332_NEED_ACCOUNT_FOR_LOGIN);
			return;
		}
		context.getFtpServerSession().setAtrribute(Constants.KEY_LOGIN_USER, user);
		write(context, FtpReply.REPLY_331_USER_NAME_OKAY_NEED_PASSWORD);
	}

}
