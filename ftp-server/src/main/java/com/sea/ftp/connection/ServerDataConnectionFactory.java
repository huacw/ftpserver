package com.sea.ftp.connection;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.sea.ftp.exception.DataConnectionException;
import com.sea.ftp.server.connection.DataConnectionFactory;

/**
 * 服务器数据连接工厂接口
 * 
 * @author sea
 *
 */
public interface ServerDataConnectionFactory extends DataConnectionFactory {

	/**
	 * 初始化主动数据连接
	 * 
	 * @param address
	 */
	public void initActiveDataConnection(InetSocketAddress address);

	/**
	 * 初始化被动数据连接
	 * 
	 * @return 返回连接地址{@link InetSocketAddress}
	 * @throws DataConnectionException
	 */
	public InetSocketAddress initPassiveDataConnection() throws DataConnectionException;

	/**
	 * 设置安全协议
	 * 
	 * @param secure
	 */
	public void setSecure(boolean secure);

	/**
	 * 设置服务器的控制地址
	 * 
	 * @param serverControlAddress
	 */
	public void setServerControlAddress(InetAddress serverControlAddress);

	/**
	 * 设置压缩模式
	 * 
	 * @param zip
	 */
	public void setZipMode(boolean zip);

	/**
	 * 检查数据连接是否超时
	 * 
	 * @param currTime
	 * @return
	 */
	public boolean isTimeout(long currTime);

	/**
	 * 关闭数据数据连接
	 */
	public void dispose();

	/**
	 * 是否压缩模式
	 */
	public boolean isZipMode();

	/**
	 * 获取客户端地址
	 * 
	 * @return
	 */
	public InetAddress getInetAddress();

	/**
	 * 获取端口号
	 * 
	 * @return
	 */
	public int getPort();
}