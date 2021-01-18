package com.sea.ftp.server.impl;

import java.nio.charset.Charset;

import com.sea.ftp.constants.FtpserverConstants;
import com.sea.ftp.handler.FTPServerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

/**
 * 默认FTP服务器初始化加载器
 * 
 * @author sea
 * 
 */
public class DefaultFTPServerInitializer extends ChannelInitializer<SocketChannel> {
	private StringDecoder DECODER;
	private StringEncoder ENCODER;

	private final FTPServerHandler SERVER_HANDLER = new DefaultFTPServerHandler();

	private SslContext sslCtx;

	public DefaultFTPServerInitializer() {
		this(Charset.defaultCharset(), null);
	}

	public SslContext getSslCtx() {
		return sslCtx;
	}

	public void setSslCtx(SslContext sslCtx) {
		this.sslCtx = sslCtx;
	}

	public DefaultFTPServerInitializer(Charset charset) {
		this(charset, null);
	}

	public DefaultFTPServerInitializer(Charset charset, SslContext sslCtx) {
		super();
		this.sslCtx = sslCtx;
		DECODER = new StringDecoder(charset);
		ENCODER = new StringEncoder(charset);
	}

	public DefaultFTPServerInitializer(SslContext sslCtx) {
		this(Charset.defaultCharset(), sslCtx);
	}

	public FTPServerHandler getServerHandler() {
		return SERVER_HANDLER;
	}

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		if (sslCtx != null) {
			pipeline.addLast(sslCtx.newHandler(ch.alloc()));
		}
		pipeline.addLast(FtpserverConstants.HANDLER_NAME_DELIMITER_DECODER,
				new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
				.addLast(FtpserverConstants.HANDLER_NAME_CHARSET_DECODER, DECODER)
				.addLast(FtpserverConstants.HANDLER_NAME_CHARSET_ENCODER, ENCODER)
				// .addLast(FtpserverConstants.HANDLER_NAME_CHUNKED_HANDLER, new
				// ChunkedWriteHandler())
				.addLast(FtpserverConstants.HANDLER_NAME_FTPSERVER_HANDLER, SERVER_HANDLER);
		System.err.println(pipeline);
	}
}
