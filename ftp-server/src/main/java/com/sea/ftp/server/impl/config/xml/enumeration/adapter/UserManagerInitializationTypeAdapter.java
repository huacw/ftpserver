package com.sea.ftp.server.impl.config.xml.enumeration.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.sea.ftp.server.impl.config.xml.enumeration.UserMangerInitializationType;

/**
 * 
 * 用户管理初始化类型转换器
 * 
 * @author sea
 */
public class UserManagerInitializationTypeAdapter extends
		XmlAdapter<String, UserMangerInitializationType> {

	@Override
	public UserMangerInitializationType unmarshal(String type) throws Exception {
		try {
			return UserMangerInitializationType.valueOf(type);
		} catch (Exception e) {
			return UserMangerInitializationType.none;
		}
	}

	@Override
	public String marshal(UserMangerInitializationType type) throws Exception {
		if (type == null) {
			return "none";
		}
		return type.name();
	}
}