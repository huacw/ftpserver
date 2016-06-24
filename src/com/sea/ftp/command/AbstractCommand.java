package com.sea.ftp.command;

import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.command.impl.LocalizedFTPReply;
import com.sea.ftp.constants.Constants;
import com.sea.ftp.server.context.request.DefaultFTPServerRequest;
import com.sea.ftp.user.User;

/**
 * 
 * 抽象命令
 * 
 * @author sea
 */
public abstract class AbstractCommand implements Command {
	// 日志记录对象
	protected Logger logger = Logger.getLogger(getClass());
	// 国际化响应消息
	protected static FtpReply reply = new LocalizedFTPReply();

	/**
	 * 返回响应信息
	 * 
	 * @param context
	 *            命令上下文
	 * @param code
	 *            响应码
	 * @param args
	 */
	protected void write(CommandContext context, int code, String... args) {
		ChannelHandlerContext ctx = (ChannelHandlerContext) context.getResponse().getAtrribute(Constants.KEY_SESSION_STREAM);
		logger.info(reply.getMessage(code, args));
		ctx.writeAndFlush(reply.getMessage(code, args) + "\n");
	}

	/**
	 * 获取命令参数
	 * 
	 * @param context
	 * @return
	 */
	protected String[] getCmdArgs(CommandContext context) {
		String[] args = (String[]) context.getRequest().getAtrribute(DefaultFTPServerRequest.FTP_REQUEST_CMD_ARGS);
		return args;
	}

	protected User getLoginUser(CommandContext context) {
		Object user = context.getFtpServerSession().getAtrribute(Constants.KEY_LOGIN_USER);
		if (user == null) {
			return null;
		}
		if (user instanceof User) {
			return (User) user;
		}
		return null;
	}
}
