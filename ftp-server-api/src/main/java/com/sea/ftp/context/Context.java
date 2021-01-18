package com.sea.ftp.context;

/**
 * 
 * 上下文接口
 *
 * @author sea 
 */
public interface Context {
	/**
	 * 获取上下文ID
	 * 
	 * @return 上下文ID
	 */
	public String getId();

	/**
	 * 设置上下文文属性
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 */
	public void setAtrribute(String name, Object value);

	/**
	 * 获取指定属性
	 * 
	 * @param name
	 *            属性名
	 * @return 属性值
	 */
	public Object getAtrribute(String name);
}
