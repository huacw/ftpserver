package com.sea.ftp.command.impl;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.exception.io.FTPIOException;
import com.sea.ftp.server.context.session.DefaultFTPServerSession;

/**
 * 
 * （Passive，被动的）命令
 * 
 *
 * @author sea 
 */
public class PASV extends AbstractCommand {

	@Override
	public void done(CommandContext context) throws FTPIOException {
		//TODO 创建数据连接
//		context.getFtpServerSession().get
		
		writeConent(context, FtpReply.REPLY_227_ENTERING_PASSIVE_MODE,
				(String) context.getFtpServerSession().getAtrribute(DefaultFTPServerSession.KEY_USER_CURRENT_PATH)
						+ "\n");
	}

}
