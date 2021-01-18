package com.sea.ftp.user.factory.builder.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sea.ftp.exception.illegal.IllegalAuthorityException;
import com.sea.ftp.user.authority.Authority;
import com.sea.ftp.user.authority.impl.FileAppendAuthority;
import com.sea.ftp.user.authority.impl.FileDeleteAuthority;
import com.sea.ftp.user.authority.impl.FileDownloadAuthority;
import com.sea.ftp.user.authority.impl.FileUploadAuthority;
import com.sea.ftp.user.authority.impl.FolderCreateAuthority;
import com.sea.ftp.user.authority.impl.FolderDeleteAuthority;
import com.sea.ftp.user.authority.impl.FolderListAuthority;
import com.sea.ftp.user.authority.impl.RenameAuthority;
import com.sea.ftp.user.factory.builder.UserManagerBuilder;
import com.sea.ftp.util.StringUtils;

/**
 * 
 * 抽象的用户管理创建类
 * 
 * @author sea
 */
public abstract class AbstractUserManagerBuilder implements UserManagerBuilder {
	@SuppressWarnings("serial")
	private static Map<String, Authority> authorityMapping = Collections
			.unmodifiableMap(new HashMap<String, Authority>() {
				{
					put("R", new FileDownloadAuthority());
					put("W", new FileUploadAuthority());
					put("D", new FileDeleteAuthority());
					put("FC", new FolderCreateAuthority());
					put("FD", new FolderDeleteAuthority());
					put("RN", new RenameAuthority());
					put("APD", new FileAppendAuthority());
					put("L", new FolderListAuthority());
				}
			});

	/**
	 * 解析配置中的权限信息
	 * 
	 * @param authStr
	 *            权限字符串，以逗号分隔，权限字符为： R-文件权限，W-文件写权限，D-文件删除权限，
	 *            FC-文件夹创建权限，FD-文件夹删除权限， RN-重命名权限，APD-文件续传（暂时不支持）， L-显示目录列表
	 * @return 权限列表
	 */
	protected Authority[] parseAuth(String authStr) {
		if (StringUtils.isBlank(authStr)) {
			return null;
		}
		String[] aus = authStr.split(",");
		List<Authority> authorities = new ArrayList<Authority>();
		for (String str : aus) {
			Authority auth = authorityMapping.get(str.toUpperCase());
			if (auth == null) {
				throw new IllegalAuthorityException();
			}
			authorities.add(auth);
		}
		return authorities.toArray(new Authority[] {});
	}
}
