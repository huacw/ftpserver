package com.sea.ftp.ftplet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 
 * FTP文件
 *
 * @author sea 
 */
public interface FtpFile {

	/**
	 * Get the full path from the base directory of the FileSystemView.
	 * 
	 * @return a path where the path separator is '/' (even if the operating
	 *         system uses another character as path separator).
	 */
	public String getAbsolutePath();

	/**
	 * Get the file name of the file
	 * 
	 * @return the last part of the file path (the part after the last '/').
	 */
	public String getName();

	/**
	 * Is the file hidden?
	 * 
	 * @return true if the {@link FtpFile} is hidden
	 */
	public boolean isHidden();

	/**
	 * Is it a directory?
	 * 
	 * @return true if the {@link FtpFile} is a directory
	 */
	public boolean isDirectory();

	/**
	 * Is it a file?
	 * 
	 * @return true if the {@link FtpFile} is a file, false if it is a directory
	 */
	public boolean isFile();

	/**
	 * Does this file exists?
	 * 
	 * @return true if the {@link FtpFile} exists
	 */
	public boolean doesExist();

	/**
	 * Has read permission?
	 * 
	 * @return true if the {@link FtpFile} is readable by the user
	 */
	public boolean isReadable();

	/**
	 * Has write permission?
	 * 
	 * @return true if the {@link FtpFile} is writable by the user
	 */
	public boolean isWritable();

	/**
	 * Has delete permission?
	 * 
	 * @return true if the {@link FtpFile} is removable by the user
	 */
	public boolean isRemovable();

	/**
	 * Get the owner name.
	 * 
	 * @return The name of the owner of the {@link FtpFile}
	 */
	public String getOwnerName();

	/**
	 * Get owner group name.
	 * 
	 * @return The name of the group that owns the {@link FtpFile}
	 */
	public String getGroupName();

	/**
	 * Get link count.
	 * 
	 * @return The number of links for the {@link FtpFile}
	 */
	public int getLinkCount();

	/**
	 * Get last modified time in UTC.
	 * 
	 * @return The timestamp of the last modified time for the {@link FtpFile}
	 */
	public long getLastModified();

	/**
	 * Set the last modified time stamp of a file
	 * 
	 * @param time
	 *            The last modified time, in milliseconds since the epoch. See
	 *            {@link File#setLastModified(long)}.
	 */
	public boolean setLastModified(long time);

	/**
	 * Get file size.
	 * 
	 * @return The size of the {@link FtpFile} in bytes
	 */
	public long getSize();

	/**
	 * Create directory.
	 * 
	 * @return true if the operation was successful
	 */
	public boolean mkdir();

	/**
	 * Delete file.
	 * 
	 * @return true if the operation was successful
	 */
	public boolean delete();

	/**
	 * Move file.
	 * 
	 * @param destination
	 *            The target {@link FtpFile} to move the current {@link FtpFile}
	 *            to
	 * @return true if the operation was successful
	 */
	public boolean move(FtpFile destination);

	/**
	 * List file objects. If not a directory or does not exist, null will be
	 * returned. Files must be returned in alphabetical order. List must be
	 * immutable.
	 * 
	 * @return The {@link List} of {@link FtpFile}s
	 */
	public List<FtpFile> listFiles();

	/**
	 * Create output stream for writing.
	 * 
	 * @param offset
	 *            The number of bytes at where to start writing. If the file is
	 *            not random accessible, any offset other than zero will throw
	 *            an exception.
	 * @return An {@link OutputStream} used to write to the {@link FtpFile}
	 * @throws IOException
	 */
	public OutputStream createOutputStream(long offset) throws IOException;

	/**
	 * Create input stream for reading.
	 * 
	 * @param offset
	 *            The number of bytes of where to start reading. If the file is
	 *            not random accessible, any offset other than zero will throw
	 *            an exception.
	 * @return An {@link InputStream} used to read the {@link FtpFile}
	 * @throws IOException
	 */
	public InputStream createInputStream(long offset) throws IOException;

}
