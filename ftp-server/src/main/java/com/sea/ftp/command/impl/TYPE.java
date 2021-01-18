package com.sea.ftp.command.impl;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.exception.io.FTPIOException;
import com.sea.ftp.ftplet.DataType;

/**
 * 
 * （Representation Type,表达类型）命令
 * 
 *
 * @author sea
 */
public class TYPE extends AbstractCommand {

	@Override
	public void done(CommandContext context) throws FTPIOException {
		String[] args = context.getRequest().getArgs();
		if (args == null || args.length == 0) {
			write(context, FtpReply.REPLY_500_SYNTAX_ERROR_COMMAND_UNRECOGNIZED);
			return;
		}
		context.getFtpServerSession().setDataType(DataType.parseArgument(args[0].charAt(0)));
		write(context, FtpReply.REPLY_200_COMMAND_OKAY);
	}

}
