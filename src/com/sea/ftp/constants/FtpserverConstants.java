package com.sea.ftp.constants;

/**
 * 常量类
 * 
 * @author sea
 *
 */
public interface FtpserverConstants {
	/**
	 * 分隔符解码器名称（处理半包问题）
	 */
	public static final String HANDLER_NAME_DELIMITER_DECODER = "delimiter-decoder";
	/**
	 * 字符解码器
	 */
	public static final String HANDLER_NAME_CHARSET_DECODER = "charset-decoder";
	/**
	 * 字符的编码器
	 */
	public static final String HANDLER_NAME_CHARSET_ENCODER = "charset-encoder";
	/**
	 * ftpserver服务器的处理器
	 */
	public static final String HANDLER_NAME_FTPSERVER_HANDLER = "ftpserver-handler";
}
