package com.gao.utils;

import java.util.HashMap;
import java.util.Map;

public final class LicenseMap {
	private static Map<Integer, License> map;
	public static int SN=0;
	static {
		map = new HashMap<Integer, License>();
		map.put(0, new License("必创", "", 200,"大庆九厂试验项目"));
		map.put(1, new License("阿里云服务器 Windows Server2019", "00:16:3E:01:D8:63", 0,"IP:8.130.30.138"));
	}

	public static Map<Integer, License> getMapObject() {
		return map;
	}
	
	public static class License{
		public String Customer;
		public String Mac;
		public int Number;
		public String Remark;
		public License(String customer, String mac, int number, String remark) {
			super();
			Customer = customer;
			Mac = mac;
			Number = number;
			Remark = remark;
		}
		public String getCustomer() {
			return Customer;
		}
		public void setCustomer(String customer) {
			Customer = customer;
		}
		public String getMac() {
			return Mac;
		}
		public void setMac(String mac) {
			Mac = mac;
		}
		public int getNumber() {
			return Number;
		}
		public void setNumber(int number) {
			Number = number;
		}
		public String getRemark() {
			return Remark;
		}
		public void setRemark(String remark) {
			Remark = remark;
		}
	}

}
