/*******************************************************************************
 * $Header$
 * $Revision$
 * $Date$
 *
 *==============================================================================
 *
 * Copyright (c) 2005-2015 Tansun Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2015年11月3日
 *******************************************************************************/

package com.sea.ftp.server.impl.config.xml.enumeration.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.sea.ftp.enumeration.EncryptedStrategyType;

/**
 * 
 * 加密测试类型转换类
 *
 * @author sea
 */
public class EncryptedStrategyTypeAdapter extends
		XmlAdapter<String, EncryptedStrategyType> {

	@Override
	public EncryptedStrategyType unmarshal(String type) throws Exception {
		try {
			return EncryptedStrategyType.valueOf(type);
		} catch (Exception e) {
			return EncryptedStrategyType.none;
		}
	}

	@Override
	public String marshal(EncryptedStrategyType type) throws Exception {
		if (type == null) {
			return "none";
		}
		return type.name();
	}

}
