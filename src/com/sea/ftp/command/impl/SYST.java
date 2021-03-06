package com.sea.ftp.command.impl;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.exception.io.FTPIOException;

/**
 * 
 * （System,系统）命令
 * 
 *
 * @author sea
 */
public class SYST extends AbstractCommand {

	@Override
	public void done(CommandContext context) throws FTPIOException {
		writeConent(context, FtpReply.REPLY_215_NAME_SYSTEM_TYPE, System.getProperties().getProperty("os.name"));
	}

}
