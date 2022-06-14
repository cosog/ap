package com.cosog.utils;

import java.util.Locale;
import java.util.ResourceBundle;


public class LoadI18NUtils {
	private static ResourceBundle resource;
//	private static String request_locale = "zh_CN";
	//private static Log log = LogFactory.getLog(LoadI18NUtils.class);

	public static ResourceBundle getResourceBundle(String locale) {
		String[] locale_country = locale.split("_");
		resource = ResourceBundle.getBundle("smart", new Locale(
				locale_country[0], locale_country[1]));
		return resource;
	}

	public static ResourceBundle getLocaleResourceBundle() {
		ResourceBundle res = ResourceBundle.getBundle("db");
		String request_locale = res.getString("language");
		String[] locale_country = request_locale.split("_");
		resource = ResourceBundle.getBundle("smart", new Locale(
				locale_country[0], locale_country[1]));
		return resource;
	}

	public static void main(String[] args) {
		ResourceBundle res = getResourceBundle("en_US");
		StringManagerUtils.printLog("cosog.softwareBrand=="
				+ res.getString("cosog.softwareBrand"));
	}
}
