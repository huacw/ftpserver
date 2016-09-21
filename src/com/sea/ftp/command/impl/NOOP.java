package com.sea.ftp.command.impl;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.exception.io.FTPIOException;

/**
 * 
 * （NOOP，无操作）命令
 * 
 *
 * @author sea 
 */
public class NOOP extends AbstractCommand {

	@Override
	public void done(CommandContext context) throws FTPIOException {
		// TODO Auto-generated method stub

	}

}
