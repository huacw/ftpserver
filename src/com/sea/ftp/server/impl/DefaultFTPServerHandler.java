package com.sea.ftp.server.impl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.command.impl.LocalizedFTPReply;
import com.sea.ftp.constants.Constants;
import com.sea.ftp.context.application.FTPServerContext;
import com.sea.ftp.context.request.FTPRequest;
import com.sea.ftp.context.response.FTPResponse;
import com.sea.ftp.context.session.FTPServerSession;
import com.sea.ftp.exception.illegal.IllegalCommandException;
import com.sea.ftp.exception.io.FTPIOException;
import com.sea.ftp.handler.FTPServerHandler;
import com.sea.ftp.message.MessageCode;
import com.sea.ftp.message.MessageCode.MessageType;
import com.sea.ftp.server.context.application.DefaultFTPServerContext;
import com.sea.ftp.server.context.request.DefaultFTPServerRequest;
import com.sea.ftp.server.context.response.DefaultFTPServerResponse;
import com.sea.ftp.server.context.session.DefaultFTPServerSession;

/**
 * 
 * 默认的FTP服务器处理器
 * 
 * @author sea
 */
@Sharable
public class DefaultFTPServerHandler extends FTPServerHandler {
	// @SuppressWarnings("serial")
	// private List<String> closeCmds = Collections.unmodifiableList(new
	// ArrayList<String>() {
	// {
	// add("quit");
	// add("bye");
	// }
	// });

	public DefaultFTPServerHandler() {
		// 初始化服务器上下文
		setServerContext(new DefaultFTPServerContext());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		MessageCode code = MessageCode.newMessageCode(MessageType.Info);
		code.setMsgKey("conntion.welcome");
		Channel channel = ctx.channel();
		String message = getMessageResource().getMessage(code, InetAddress.getLocalHost().getHostAddress(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
		channel.write(message);
		channel.flush();
		// 创建用户会话
		createSession(ctx);
	}

	/**
	 * 创建用户会话
	 * 
	 * @param ctx
	 */
	private void createSession(ChannelHandlerContext ctx) {
		// 获取FTP服务器全局上下文
		FTPServerContext customServerContext = getServerContext();
		// 设置FTP服务器上下文
		if (customServerContext == null) {
			customServerContext = new DefaultFTPServerContext();
			setServerContext(customServerContext);
		}
		DefaultFTPServerSession session = new DefaultFTPServerSession();
		// 保存session的流通道
		session.setAtrribute(Constants.KEY_SESSION_STREAM, ctx);
		customServerContext.addFTPServerSession(ctx.hashCode() + "", new DefaultFTPServerSession());
		logger.debug("ctx hashCode:" + ctx.hashCode());
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, String request) {
		String[] items = request.split("\\s+");
		if (items == null || items.length < 1) {
			return;
		}
		// 获取服务器会话
		FTPServerContext serverContext = getServerContext();

		FTPServerSession ftpServerSession = serverContext.getFTPServerSession(ctx.hashCode() + "");

		FTPRequest ftpRequest = new DefaultFTPServerRequest();
		ftpRequest.setAtrribute(DefaultFTPServerRequest.FTP_SERVER_SESSION, ftpServerSession);
		String cmd = items[0];
		ftpRequest.setAtrribute(DefaultFTPServerRequest.FTP_REQUEST_CMD, cmd);
		if (items.length > 1) {
			int length = items.length;
			String[] args = new String[length - 1];
			for (int i = 0; i < length - 1; i++) {
				args[i] = items[i + 1];
			}
			ftpRequest.setAtrribute(DefaultFTPServerRequest.FTP_REQUEST_CMD_ARGS, args);
		}

		FTPResponse ftpResponse = new DefaultFTPServerResponse();
		ftpResponse.setAtrribute(Constants.KEY_SESSION_STREAM, ctx);

		CommandContext cmdContext = new CommandContext();
		cmdContext.setFtpServerSession(ftpServerSession);
		cmdContext.setRequest(ftpRequest);
		cmdContext.setResponse(ftpResponse);
		cmdContext.setServerContext(serverContext);

		try {
			serverContext.getCommandFactory().getCommand(cmd).execute(cmdContext);
		} catch (IllegalCommandException e) {
			FtpReply reply = new LocalizedFTPReply();
			ctx.writeAndFlush(reply.getMessage(500, e.getMessage()) + "\n");
		} catch (FTPIOException e) {
			logger.error(e.getMessage(), e);
		}

		// Generate and write a response.
		// System.out.println("messageReceived:" + ctx.hashCode());
		// String response;
		// boolean close = false;
		// if (request.isEmpty()) {
		// response = "Please type something.\r\n";
		// } else if (closeCmds.contains(request.toLowerCase())) {
		// response = "Have a good day!\r\n";
		// close = true;
		// } else {
		// response = "Did you say '" + request + "'?\r\n";
		// }

		// We do not need to write a ChannelBuffer here.
		// We know the encoder inserted at TelnetPipelineFactory will do the
		// conversion.
		// ChannelFuture future = ctx.channel().write(response);

		// Close the connection after sending 'Have a good day!'
		// if the client has sent 'bye'.
		// if (close) {
		// future.addListener(ChannelFutureListener.CLOSE);
		// }
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		logger.error("连接异常", cause);
	}
}
