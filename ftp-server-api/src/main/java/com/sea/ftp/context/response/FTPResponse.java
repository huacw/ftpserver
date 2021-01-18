package com.sea.ftp.context.response;

import io.netty.channel.Channel;

import com.sea.ftp.context.Context;

/**
 * 
 * FTP响应接口
 * 
 * @author sea
 */
public interface FTPResponse extends Context {
	/**
	 * 获取回写的管道
	 * 
	 * @return
	 */
	public Channel getResponseChannel();
}
