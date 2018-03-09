package com.sea.ftp.command.impl;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.exception.io.FTPIOException;
import com.sea.ftp.server.context.session.DefaultFTPServerSession;

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
		writeConent(context, FtpReply.REPLY_257_PATHNAME_CREATED,
				(String) context.getFtpServerSession().getAtrribute(DefaultFTPServerSession.KEY_USER_CURRENT_PATH)
						+ "\n");
	}

}
