package com.sea.ftp.command.impl;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.exception.illegal.IllegalInetAddressException;
import com.sea.ftp.exception.illegal.IllegalPortException;
import com.sea.ftp.exception.io.FTPIOException;
import com.sea.ftp.util.SocketAddressEncoder;
import sun.net.ftp.FtpReplyCode;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * （Data Port，数据端口）命令
 *
 * @author sea
 */
public class PORT extends AbstractCommand {

    @Override
    public void done(CommandContext context) throws FTPIOException {
        String[] args = context.getRequest().getArgs();
        if (args == null) {
            write(context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS);
            return;
        }
        //TODO 判断端口是否可用
        //解析客户端地址
        InetSocketAddress address;
        try {
            address = SocketAddressEncoder.decode(args[0]);

            // 端口不能为0
            if (address.getPort() == 0) {
                throw new IllegalPortException("PORT port must not be 0");
            }
        } catch (IllegalInetAddressException e) {
            writeConent(context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "PORT");
            return;
        } catch (IllegalPortException e) {
            logger.debug("Invalid data port: " + args[0], e);
            writeConent(context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "PORT.invalid");
            return;
        } catch (UnknownHostException e) {
            logger.debug("Unknown host", e);
            writeConent(context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "PORT.host");
            return;
        }

        //TODO 判断IP是否可用
        //返回消息码
        write(context, FtpReply.REPLY_200_COMMAND_OKAY);
    }

}
