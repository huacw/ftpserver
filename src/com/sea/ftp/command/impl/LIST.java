package com.sea.ftp.command.impl;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.exception.io.FTPIOException;

/**
 * 
 * （List，列表）命令
 * 
 * 
 *
 * @author sea
 */
public class LIST extends AbstractCommand {

	@Override
	public void done(CommandContext context) throws FTPIOException {
		// TODO Auto-generated method stub
		logger.info("LIST");
	}

}
