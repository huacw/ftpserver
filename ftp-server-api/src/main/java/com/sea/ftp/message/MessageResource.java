package com.sea.ftp.message;

import java.util.List;
import java.util.Locale;

/**
 * 
 * 消息资源接口
 *
 * @author sea 
 */
public interface MessageResource {

	/**
	 * 获取所有有效的语言
	 * 
	 * @return 有效的语言的列表
	 */
	public List<String> getAvailableLanguages();

	/**
	 * 根据消息码获取国际化信息，如果未找到返回null
	 * 
	 * @param code
	 *            消息代码
	 * @param language
	 *            指定语言
	 * @param args
	 *            消息中所用参数
	 * @return 返回指定语言的国际化信息，如果未找到返回null
	 */
	public String getMessage(MessageCode code, Locale language, String... args);

	/**
	 * 根据消息代码和子参数获取消息信息，如果未找到返回null
	 * 
	 * @param code
	 *            消息代码
	 * @param args
	 *            消息中所用参数
	 * @return 返回系统默认区域的国际化信息，如果未找到返回null
	 */
	public String getMessage(MessageCode code, String... args);
}