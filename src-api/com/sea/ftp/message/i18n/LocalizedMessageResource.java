package com.sea.ftp.message.i18n;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.sea.ftp.message.AbstractMessageResouce;
import com.sea.ftp.message.MessageCode;
import com.sea.ftp.message.MessageCode.MessageType;

/**
 * 
 * 本地化提示信息
 * 
 * @author sea 
 */
public final class LocalizedMessageResource extends AbstractMessageResouce {
	private static LocalizedMessageResource instance;// 本地化消息示例
	private static Object lock = new Object();// 同步锁
	// 支持的语言
	@SuppressWarnings("serial")
	private static List<String> availableLanguages = Collections
			.unmodifiableList(new ArrayList<String>() {
				{
					add(Locale.getDefault().toString());
				}
			});

	/**
	 * 自由化构造函数
	 */
	private LocalizedMessageResource() {
	}

	/**
	 * 获取本地化消息实例
	 * 
	 * @return
	 */
	public static LocalizedMessageResource newInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new LocalizedMessageResource();
				}
			}
		}
		return instance;
	}

	@Override
	public List<String> getAvailableLanguages() {
		return availableLanguages;
	}

	public static void main(String[] args) {
		LocalizedMessageResource lmr = newInstance();
		MessageCode code = MessageCode.newMessageCode(MessageType.Error);
		code.setMsgKey("notfound.userdir");
		System.out.println(lmr.getMessage(code, "ddd", "uu"));
	}
}