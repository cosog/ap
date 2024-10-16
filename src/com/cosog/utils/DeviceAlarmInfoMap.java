package com.cosog.utils;

import java.util.HashMap;
import java.util.Map;

public final class DeviceAlarmInfoMap {
	private static Map<Integer, DeviceAlarmInfo> map;
	static {
		map = new HashMap<Integer, DeviceAlarmInfo>();
	}

	public static Map<Integer, DeviceAlarmInfo> getDeviceAlarmInfoMap() {
		return map;
	}
	@SuppressWarnings("unused")
	private void addMapObject(final Integer deviceId, final DeviceAlarmInfo o) {
		map.put(deviceId, o);
	}

}
