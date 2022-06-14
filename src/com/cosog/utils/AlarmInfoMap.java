package com.cosog.utils;

import java.util.HashMap;
import java.util.Map;

public final class AlarmInfoMap {
	private static Map<String, String> map;
	static {
		map = new HashMap<String, String>();
	}

	public static Map<String, String> getMapObject() {
		return map;
	}
	@SuppressWarnings("unused")
	private void addMapObject(final String name, final String o) {
		map.put(name, o);
	}
}
