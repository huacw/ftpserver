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

package com.sea.ftp.filesystem.nativefs;

import java.io.File;

import org.apache.log4j.Logger;

import com.sea.ftp.exception.FTPServerException;
import com.sea.ftp.filesystem.nativefs.impl.NativeFileSystemView;
import com.sea.ftp.ftplet.FileSystemFactory;
import com.sea.ftp.ftplet.FileSystemView;
import com.sea.ftp.user.User;

/**
 * 
 * @author sea
 * 
 */
public class NativeFileSystemFactory implements FileSystemFactory {

	private final Logger LOG = Logger.getLogger(NativeFileSystemFactory.class);

	private boolean createHome;

	private boolean caseInsensitive;

	/**
	 * Should the home directories be created automatically
	 * 
	 * @return true if the file system will create the home directory if not
	 *         available
	 */
	public boolean isCreateHome() {
		return createHome;
	}

	/**
	 * Set if the home directories be created automatically
	 * 
	 * @param createHome
	 *            true if the file system will create the home directory if not
	 *            available
	 */

	public void setCreateHome(boolean createHome) {
		this.createHome = createHome;
	}

	/**
	 * Is this file system case insensitive. Enabling might cause problems when
	 * working against case-sensitive file systems, like on Linux
	 * 
	 * @return true if this file system is case insensitive
	 */
	public boolean isCaseInsensitive() {
		return caseInsensitive;
	}

	/**
	 * Should this file system be case insensitive. Enabling might cause
	 * problems when working against case-sensitive file systems, like on Linux
	 * 
	 * @param caseInsensitive
	 *            true if this file system should be case insensitive
	 */
	public void setCaseInsensitive(boolean caseInsensitive) {
		this.caseInsensitive = caseInsensitive;
	}

	/**
	 * Create the appropriate user file system view.
	 */
	public FileSystemView createFileSystemView(User user) throws FTPServerException {
		synchronized (user) {
			// create home if does not exist
			if (createHome) {
				String homeDirStr = user.getHomeDirectory();
				File homeDir = new File(homeDirStr);
				if (homeDir.isFile()) {
					LOG.warn("Not a directory :: " + homeDirStr);
					throw new FTPServerException("Not a directory :: " + homeDirStr);
				}
				if ((!homeDir.exists()) && (!homeDir.mkdirs())) {
					LOG.warn("Cannot create user home :: " + homeDirStr);
					throw new FTPServerException("Cannot create user home :: " + homeDirStr);
				}
			}

			FileSystemView fsView = new NativeFileSystemView(user, caseInsensitive);
			return fsView;
		}
	}

}
