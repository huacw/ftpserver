package com.sea.ftp.server.context.session;

import com.sea.ftp.context.AbstractContext;
import com.sea.ftp.context.session.FTPServerSession;
import com.sea.ftp.exception.illegal.NotFoundException;
import com.sea.ftp.ftplet.DataType;
import com.sea.ftp.ftplet.FileSystemView;
import com.sea.ftp.server.connection.DataConnectionFactory;
import com.sea.ftp.user.User;

/**
 * FTP会话
 * 
 * @author sea
 * 
 */
public class DefaultFTPServerSession extends AbstractContext implements FTPServerSession {

	/**
	 * 当前用户键值
	 */
	public static final String KEY_CURRENT_USER = "ftp.server.current.user";
	/**
	 * 当前用户键值
	 */
	public static final String KEY_SYSTEM_FILE_VIEW = "ftp.server.systemfile.view";
	/**
	 * 数据传输类型键
	 */
	public static final String KEY_TRANSFER_DATA_TYPE = "ftp.server.session.transfer.datatype";

	@Override
	public User getCurrentUser() {
		Object user = getAtrribute(KEY_CURRENT_USER);
		if (user == null) {
			throw new NotFoundException("no.login.user");
		}
		if (user instanceof User) {
			return (User) user;
		}
		throw new IllegalArgumentException("no.login.user");
	}

	/**
	 * 设置系统文件视图
	 * 
	 * @param view
	 */
	@Override
	public void setSystemFileView(FileSystemView view) {
		setAtrribute(KEY_SYSTEM_FILE_VIEW, view);
	}

	/**
	 * 获取系统文件视图
	 * 
	 * @return
	 */
	@Override
	public FileSystemView getSystemFileView() {
		return (FileSystemView) getAtrribute(KEY_SYSTEM_FILE_VIEW);
	}

	@Override
	public DataConnectionFactory getDataConnectionFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataType getDataType() {
		Object atrribute = getAtrribute(KEY_TRANSFER_DATA_TYPE);
		return atrribute == null ? DataType.ASCII : (DataType) atrribute;
	}

	@Override
	public void setDataType(DataType dataType) {
		setAtrribute(KEY_TRANSFER_DATA_TYPE, dataType);
	}
}
