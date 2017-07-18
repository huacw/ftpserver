package com.sea.ftp.server.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sea.ftp.context.session.FTPServerSession;
import com.sea.ftp.exception.io.FTPIOException;

/**
 * FTP数据连接
 * 
 * @author huachengwu
 *
 */
public interface DataConnection {
	/**
	 * 从客户端传输数据
	 * 
	 * @param session
	 *            当前FTPServer会话 {@link FTPServerSession}
	 * @param out
	 *            客户端的输出流
	 * @return 客户端传输的数据长度
	 * @throws IOException
	 */
	public long transferFromClient(FTPServerSession session, OutputStream out) throws FTPIOException;

	/**
	 * 向客户端传输数据
	 * 
	 * @param session
	 *            当前FTPServer会话 {@link FTPServerSession}
	 * @param 客户端的输入流
	 * @return 客户端传输的数据长度
	 * @throws IOException
	 */
	public long transferToClient(FTPServerSession session, InputStream in) throws FTPIOException;

	/**
	 * 向客户端传输数据
	 * 
	 * @param session
	 *            当前FTPServer会话 {@link FTPServerSession}
	 * @param str
	 *            传输的字符串数据
	 * @throws IOException
	 */
	public void transferToClient(FTPServerSession session, String str) throws FTPIOException;
}
