package com.sea.ftp.message;

import java.io.IOException;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.sea.ftp.exception.FTPServerRuntimeException;
import com.sea.ftp.exception.illegal.NotFoundException;
import com.sea.ftp.util.StringUtils;

/**
 * 
 * 抽象的提示信息
 * 
 * @author sea 
 */
public abstract class AbstractMessageResouce implements MessageResource {
	// 默认的国际化文件地址
	protected final static String DEFAULT_RESOURCE_FILE = "com/sea/ftp/message/message";
	// 默认的国际化文件地址后缀
	protected final static String DEFAULT_RESOURCE_FILE_SUFFIX = ".properties";
	// 默认的资源配置
	protected static Properties defaultPropties;
	// 语言属性对应关系
	protected static Map<String, Properties> languageProps = new HashMap<String, Properties>();

	/**
	 * 加载默认国际化文件
	 */
	protected void loadResource() {
		defaultPropties = (Properties) loadResource(
				DEFAULT_RESOURCE_FILE + DEFAULT_RESOURCE_FILE_SUFFIX).clone();
	}

	@Override
	public String getMessage(MessageCode code, String... args) {
		return getMessage(code, getDefaultLocale(), args);
	}

	@Override
	public String getMessage(MessageCode code, Locale language, String... args) {
		String defaultLanguage = language.toString();
		Properties prop = null;
		if (languageProps.containsKey(defaultLanguage)) {
			prop = languageProps.get(defaultLanguage);
		} else {
			prop = loadResource(DEFAULT_RESOURCE_FILE
					+ (StringUtils.isBlank(defaultLanguage) ? "" : "_")
					+ defaultLanguage + DEFAULT_RESOURCE_FILE_SUFFIX);
			languageProps.put(defaultLanguage, prop);
		}
		return getMessage(code, prop, language, args);
	}

	/**
	 * 获取指定属性文件的对应消息代码的提示信息
	 * 
	 * @param code
	 *            消息代码
	 * @param prop
	 *            属性文件
	 * @param local
	 *            所属区域
	 * @param args
	 *            参数列表
	 * @return 提示信息
	 */
	private String getMessage(MessageCode code, Properties prop, Locale local,
			String... args) {
		Locale locale = local;
		if (code == null) {
			throw new FTPServerRuntimeException("消息代码为空");
		}
		if (prop == null) {
			throw new NotFoundException("未找到指定的属性文件");
		}
		if (locale == null) {
			locale = new Locale("");
		}
		// 获取消息
		String message = getNotFormattedMessage(code, prop);
		MessageFormat mf = new MessageFormat(message, locale);
		StringBuffer result = new StringBuffer();
		mf.format(args, result, new FieldPosition(0));
		return result.toString();
	}

	/**
	 * 获取未格式化的消息
	 * 
	 * @param code
	 *            消息代码
	 * @param prop
	 *            属性文件
	 * @return 未格式化的消息
	 */
	private String getNotFormattedMessage(MessageCode code, Properties prop) {
		StringBuffer messageKey = new StringBuffer();
		String msgKey = code.getMsgKey();
		boolean hasPrefix = false;
		if (StringUtils.isNotEmpty(msgKey)) {
			messageKey.append(msgKey);
			hasPrefix = true;
		}
		int msgCode = code.getCode();
		if (msgCode != -1) {
			if (hasPrefix) {
				messageKey.append(".");
			}
			messageKey.append(msgCode);
		}
		String mKey = messageKey.toString();
		String message = prop.getProperty(mKey);
		// 如果未获取消息使用默认的消息
		if (StringUtils.isBlank(message)) {
			message = getDefaultMessage(mKey);
			return StringUtils.isBlank(message) ? msgKey : message;
		}
		return message;
	}

	/**
	 * 获取默认的属性
	 * 
	 * @param key
	 *            属性键
	 * @return 属性
	 */
	private String getDefaultMessage(String key) {
		if (defaultPropties == null) {
			loadResource();
			if (defaultPropties == null) {
				throw new NotFoundException("未找到默认的属性文件");
			}
		}
		return defaultPropties.getProperty(key);
	}

	/**
	 * 获取默认地区
	 * 
	 * @return 默认地区
	 */
	protected Locale getDefaultLocale() {
		return Locale.getDefault();
	}

	/**
	 * 获取指定语言的区域
	 * 
	 * @param language
	 *            指定语言
	 * @return 区域
	 */
	protected Locale getLocale(String language) {
		if (StringUtils.isEmpty(language)) {
			return new Locale("");
		}
		String[] tmpArray = language.split("_");
		if (tmpArray.length == 1) {
			return new Locale(tmpArray[0]);
		} else if (tmpArray.length > 1) {
			return new Locale(tmpArray[0], tmpArray[1]);
		} else {
			return new Locale("");
		}
	}

	/**
	 * 加载指定的国际化文件
	 * 
	 * @param resouceFile
	 *            指定的国际化文件
	 * @return 国际化文件消息属性列表
	 */
	protected Properties loadResource(String resouceFile) {
		Properties props = new Properties();
		try {
			props.load(AbstractMessageResouce.class.getClassLoader()
					.getResourceAsStream(resouceFile));
		} catch (IOException e) {
			throw new FTPServerRuntimeException("加载国际化文件异常");
		}
		return props;
	}

}
