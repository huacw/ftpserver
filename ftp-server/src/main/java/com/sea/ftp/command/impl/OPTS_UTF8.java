package com.sea.ftp.command.impl;

import java.nio.charset.Charset;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.constants.FtpserverConstants;
import com.sea.ftp.exception.io.FTPIOException;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * OPTS UTF8
 * 
 * @author huachengwu
 *
 */
public class OPTS_UTF8 extends AbstractCommand {

	@Override
	protected void done(CommandContext context) throws FTPIOException {
		String[] argument = context.getRequest().getArgs();
		// 修改编码
		if (argument.length == 2) {
			ChannelHandlerContext ctx = getSessionStream(context);
			ChannelPipeline pipeline = ctx.pipeline();
			if ("on".equalsIgnoreCase(argument[1])) {
				pipeline.replace(StringEncoder.class, FtpserverConstants.HANDLER_NAME_CHARSET_ENCODER,
						new StringEncoder(Charset.forName("UTF-8")));
				pipeline.replace(StringDecoder.class, FtpserverConstants.HANDLER_NAME_CHARSET_DECODER,
						new StringDecoder(Charset.forName("UTF-8")));
			}
		}
		write(context, FtpReply.REPLY_200_COMMAND_OKAY, "OPTS.UTF8");
	}
}
