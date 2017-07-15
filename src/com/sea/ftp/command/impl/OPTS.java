package com.sea.ftp.command.impl;

import java.util.HashMap;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.Command;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.exception.io.FTPIOException;

/**
 * 自定义配置命令
 * 
 * @author huachengwu
 *
 */
public class OPTS extends AbstractCommand {
    private static final HashMap<String, Command> COMMAND_MAP = new HashMap<>();
    static {
        COMMAND_MAP.put("OPTS_MLST", new OPTS_MLST());
        COMMAND_MAP.put("OPTS_UTF8", new OPTS_UTF8());
    }

    @Override
    protected void done(CommandContext context) throws FTPIOException {
        String[] argument = context.getRequest().getArgs();
        if (argument == null || argument.length == 0) {
            write(context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "OPTS");
            return;
        }

        String arg = argument[0].toUpperCase();

        String optsRequest = "OPTS_" + arg;
        Command command = COMMAND_MAP.get(optsRequest);
        try {
            if (command != null) {
                command.execute(context);
            } else {
                write(context, FtpReply.REPLY_502_COMMAND_NOT_IMPLEMENTED, "OPTS.not.implemented");
            }
        } catch (Exception ex) {
            logger.warn("OPTS.execute()", ex);
            write(context, FtpReply.REPLY_500_SYNTAX_ERROR_COMMAND_UNRECOGNIZED, "OPTS");
        }
    }

}
