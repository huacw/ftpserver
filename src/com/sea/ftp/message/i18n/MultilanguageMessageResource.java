package com.sea.ftp.message.i18n;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.sea.ftp.message.AbstractMessageResouce;
import com.sea.ftp.util.StringUtils;

/**
 * 多语言提示信息
 * 
 * @author sea
 * 
 */
public class MultilanguageMessageResource extends AbstractMessageResouce {
	private Locale defaultLocale;
	// 支持的语言
	@SuppressWarnings("serial")
	private static List<String> availableLanguages = new ArrayList<String>() {
		{
			add(Locale.getDefault().toString());
			add(Locale.US.toString());
			add(Locale.ENGLISH.toString());
		}
	};

	public MultilanguageMessageResource() {
		loadResource();
	}

	@Override
	public List<String> getAvailableLanguages() {
		return availableLanguages;
	}

	/**
	 * 注册要支持的地区语言
	 * 
	 * @param locale
	 *            地区
	 */
	public void registerLanguage(Locale locale) {
		if (locale == null) {
			return;
		}
		registerLanguage(locale.toString());
	}

	/**
	 * 注册要支持的地区语言
	 * 
	 * @param language
	 *            语言
	 */
	public void registerLanguage(String language) {
		if (StringUtils.isBlank(language)) {
			return;
		}
		if (availableLanguages.contains(language)) {
			return;
		}
		availableLanguages.add(language);
	}

	/**
	 * 设置默认的语言
	 * 
	 * @param language
	 *            语言
	 */
	public void setDefaultLanguage(String language) {
		setDefaultLanguage(Locale.forLanguageTag(language));
	}

	/**
	 * 设置默认的语言
	 * 
	 * @param language
	 *            语言
	 */
	public void setDefaultLanguage(Locale language) {
		// 注册默认语言
		registerLanguage(language);
		// 设置默认语言
		defaultLocale = language;
	}

	@Override
	protected Locale getDefaultLocale() {
		if (defaultLocale == null) {
			return Locale.getDefault();
		}
		return defaultLocale;
	}

	/**
	 * 注册要支持的地区语言
	 * 
	 * @param lang
	 *            语言
	 * @param country
	 *            国家代码
	 */
	public void registerLanguage(String lang, String country) {
		if (StringUtils.isBlank(lang)) {
			return;
		}
		if (StringUtils.isBlank(country)) {
			country = "";
		}
		registerLanguage(new Locale(lang, country));
	}

	/**
	 * 取消注册要支持的地区语言
	 * 
	 * @param locale
	 *            地区
	 */
	public void unregisterLanguage(Locale locale) {
		if (locale == null) {
			return;
		}
		unregisterLanguage(locale.toString());
	}

	/**
	 * 取消注册要支持的地区语言
	 * 
	 * @param language
	 *            语言
	 */
	public void unregisterLanguage(String language) {
		if (StringUtils.isBlank(language)) {
			return;
		}
		availableLanguages.remove(language);
	}

	/**
	 * 取消注册要支持的地区语言
	 * 
	 * @param lang
	 *            语言
	 * @param country
	 *            国家代码
	 */
	public void unregisterLanguage(String lang, String country) {
		if (StringUtils.isBlank(lang)) {
			return;
		}
		if (StringUtils.isBlank(country)) {
			country = "";
		}
		unregisterLanguage(new Locale(lang, country));
	}

}
