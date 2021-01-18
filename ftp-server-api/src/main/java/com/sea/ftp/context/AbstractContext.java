package com.sea.ftp.context;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

/**
 * 抽象的上下文
 * 
 * @author sea
 * 
 */
public abstract class AbstractContext implements Context {
	protected Logger logger = Logger.getLogger(getClass());
	private String id;

	public AbstractContext() {
		id = UUID.randomUUID().toString();
	}

	// 属性列表
	protected Map<String, Object> attributes = new HashMap<String, Object>();

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setAtrribute(String name, Object value) {
		attributes.put(name, value);
	}

	@Override
	public Object getAtrribute(String name) {
		return attributes.get(name);
	}

}
