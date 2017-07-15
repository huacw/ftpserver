/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.sea.ftp.ftplet;

import com.sea.ftp.exception.FTPServerException;

/**
 * 
 * @author sea
 *
 */
public interface FileSystemView {

	/**
	 * Get the user home directory.
	 * 
	 * @return The {@link FtpFile} for the users home directory
	 * @throws FTPServerException
	 */
	public FtpFile getHomeDirectory() throws FTPServerException;

	/**
	 * Get user current directory.
	 * 
	 * @return The {@link FtpFile} for the users current directory
	 * @throws FTPServerException
	 */
	public FtpFile getWorkingDirectory() throws FTPServerException;

	/**
	 * Change directory.
	 * 
	 * @param dir
	 *            The path of the directory to set as the current directory for
	 *            the user
	 * @return true if successful
	 * @throws FTPServerException
	 */
	public boolean changeWorkingDirectory(String dir) throws FTPServerException;

	/**
	 * Get file object.
	 * 
	 * @param file
	 *            The path to the file to get
	 * @return The {@link FtpFile} for the provided path
	 * @throws FTPServerException
	 */
	public FtpFile getFile(String file) throws FTPServerException;

	/**
	 * Does the file system support random file access?
	 * 
	 * @return true if the file supports random access
	 * @throws FTPServerException
	 */
	public boolean isRandomAccessible() throws FTPServerException;

	/**
	 * Dispose file system view.
	 */
	public void dispose();
}
