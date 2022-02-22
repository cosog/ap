package com.cosog.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AcquisitionItemColumnsMap {
	private static Map<String, Map<String,String>> map;
	static {
		map = new HashMap<String, Map<String,String>>();
	}

	public static Map<String, Map<String,String>> getMapObject() {
		return map;
	}
	@SuppressWarnings("unused")
	private void addMapObject(final String name, final Map<String,String> o) {
		map.put(name, o);
	}

}
