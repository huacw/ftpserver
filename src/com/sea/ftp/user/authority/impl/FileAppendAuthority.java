package com.sea.ftp.user.authority.impl;

import com.sea.ftp.user.authority.Authority;
import com.sea.ftp.user.authority.AuthorizationRequest;

/**
 * 
 * 文件续传权限
 *
 * @author sea
 */
public class FileAppendAuthority implements Authority {

	@Override
	public boolean canAuthorize(AuthorizationRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AuthorizationRequest authorize(AuthorizationRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}