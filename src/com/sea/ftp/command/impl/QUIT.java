package com.sea.ftp.command.impl;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.exception.io.FTPIOException;

/**
 * 
 * （Logout,注销）命令
 * 
 *
 * @author sea
 */
public class QUIT extends AbstractCommand {

	@Override
	public void done(CommandContext context) throws FTPIOException {
		getSessionStream(context).close();
		writeConent(context, FtpReply.REPLY_221_CLOSING_CONTROL_CONNECTION, "\n");
	}

}
