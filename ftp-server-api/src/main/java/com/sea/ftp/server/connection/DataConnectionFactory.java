package com.sea.ftp.server.connection;

import com.sea.ftp.exception.FTPServerException;

/**
 * FTP数据连接工厂类
 * 
 * @author huachengwu
 *
 */
public interface DataConnectionFactory {
    /**
     * 打开数据连接（主动方式）
     * @return 打开的数据连接
     * @throws Exception
     */
    public DataConnection openConnection() throws FTPServerException;

    /**
     * 是否是SSL数据连接
     * @return true-ssl数据连接
     */
    public  boolean isSecure();

    /**
     * 关闭数据连接
     */
    public void closeDataConnection();
}
