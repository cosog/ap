package com.cosog.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

public final class PropertiesHelper {

	public static boolean getBoolean(String property, Properties properties) {
		return Boolean.valueOf(properties.getProperty(property)).booleanValue();
	}

	public static boolean getBoolean(String property, Properties properties,
			boolean defaultValue) {
		String setting = properties.getProperty(property);
		return (setting == null) ? defaultValue : Boolean.valueOf(setting)
				.booleanValue();
	}

	public static int getInt(String property, Properties properties,
			int defaultValue) {
		String propValue = properties.getProperty(property);
		return (propValue == null) ? defaultValue : Integer.parseInt(propValue);
	}

	public static long getLong(String property, Properties properties,
			long defaultValue) {
		String propValue = properties.getProperty(property);
		return (propValue == null) ? defaultValue : Long.parseLong(propValue);
	}

	public static String getString(String property, Properties properties,
			String defaultValue) {
		String propValue = properties.getProperty(property);
		return (propValue == null) ? defaultValue : propValue;
	}

	public static Integer getInteger(String property, Properties properties) {
		String propValue = properties.getProperty(property);
		return (propValue == null) ? null : Integer.valueOf(propValue);
	}

	public static Map<String,String> toMap(String property, String delim, Properties properties) {
		Map<String,String> map = new HashMap<String,String>();
		String propValue = properties.getProperty(property);
		if (propValue != null) {
			StringTokenizer tokens = new StringTokenizer(propValue, delim);
			while (tokens.hasMoreTokens()) {
				map.put(tokens.nextToken(), tokens.hasMoreElements() ? tokens
						.nextToken() : "");
			}
		}
		return map;
	}

	public static String[] toStringArray(String property, String delim,
			Properties properties) {
		return toStringArray(properties.getProperty(property), delim);
	}

	public static String[] split(String seperators, String list) {
		return split(seperators, list, false);
	}

	public static String[] split(String seperators, String list, boolean include) {
		StringTokenizer tokens = new StringTokenizer(list, seperators, include);
		String[] result = new String[tokens.countTokens()];
		int i = 0;
		while (tokens.hasMoreTokens()) {
			result[i++] = tokens.nextToken();
		}
		return result;
	}

	public static String[] toStringArray(String propValue, String delim) {
		if (propValue != null) {
			return split(delim, propValue);
		} else {
			return null;
		}
	}

	/**
	 * replace a property by a starred version
	 * 
	 * @param props
	 *            properties to check
	 * @param key
	 *            proeprty to mask
	 * @return cloned and masked properties
	 */
	public static Properties maskOut(Properties props, String key) {
		Properties clone = (Properties) props.clone();
		if (clone.get(key) != null) {
			clone.setProperty(key, "****");
		}
		return clone;
	}

	private PropertiesHelper() {
	}
}
