package com.cosog.utils;

import java.util.HashMap;
import java.util.Map;

import com.cosog.model.drive.InitializedDeviceInfo;

public final class AdInitMap {
	private static Map<String, InitializedDeviceInfo> idInitMap;
	private static Map<String, InitializedDeviceInfo> ipPortInitMap;
	static {
		idInitMap = new HashMap<String, InitializedDeviceInfo>();
		ipPortInitMap = new HashMap<String, InitializedDeviceInfo>();
	}

	public static Map<String, InitializedDeviceInfo> getIdInitMapObject() {
		return idInitMap;
	}
	public static Map<String, InitializedDeviceInfo> getIpPoetInitMapObject() {
		return ipPortInitMap;
	}
	@SuppressWarnings("unused")
	private void addIdInit(final String name, final InitializedDeviceInfo o) {
		idInitMap.put(name, o);
	}
	@SuppressWarnings("unused")
	private void addIpPortInit(final String name, final InitializedDeviceInfo o) {
		ipPortInitMap.put(name, o);
	}
	
	public static void cleanData(){
		idInitMap.clear();
		ipPortInitMap.clear();
	}
}
