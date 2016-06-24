package com.sea.ftp.server.impl.config.xml.enumeration.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.sea.ftp.server.impl.config.xml.enumeration.UserConfigFileType;

/**
 * 
 * 用户管理配置文件类型转换器
 * 
 * @author sea
 */
public class UserConfigFileTypeAdapter extends
		XmlAdapter<String, UserConfigFileType> {

	@Override
	public UserConfigFileType unmarshal(String type) throws Exception {
		try {
			return UserConfigFileType.valueOf(type);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String marshal(UserConfigFileType type) throws Exception {
		if (type == null) {
			return "none";
		}
		return type.name();
	}
}