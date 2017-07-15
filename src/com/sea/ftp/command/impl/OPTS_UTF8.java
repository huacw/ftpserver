package com.sea.ftp.command.impl;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.exception.io.FTPIOException;

/**
 * OPTS UTF8
 * @author huachengwu
 *
 */
public class OPTS_UTF8 extends AbstractCommand {

    @Override
    protected void done(CommandContext context) throws FTPIOException {
        write(context, FtpReply.REPLY_200_COMMAND_OKAY, "OPTS.UTF8");
    }
}
