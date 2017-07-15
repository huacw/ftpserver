package com.sea.ftp.command.impl;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.exception.io.FTPIOException;

public class UnsupportedCommand extends AbstractCommand {

    @Override
    protected void done(CommandContext context) throws FTPIOException {
        write(context, FtpReply.REPLY_502_COMMAND_NOT_IMPLEMENTED);
    }

}
