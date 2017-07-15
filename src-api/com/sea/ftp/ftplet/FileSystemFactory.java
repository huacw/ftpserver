package com.sea.ftp.ftplet;

import com.sea.ftp.exception.FTPServerException;
import com.sea.ftp.user.User;

/**
 * 文件系统工厂
 * 
 * @author sea
 * 
 */
public interface FileSystemFactory {

	/**
	 * Create user specific file system view.
	 * 
	 * @param user
	 *            The user for which the file system should be created
	 * @return The current {@link FileSystemView} for the provided user
	 * @throws FTPServerException
	 */
	FileSystemView createFileSystemView(User user) throws FTPServerException;

}
