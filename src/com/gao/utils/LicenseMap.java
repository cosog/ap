package com.gao.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LicenseMap {
	private static Map<Integer, License> map;
	private static Map<Integer, List<Integer>> moduleMap;
	public static int SN=0;
	public static int modulesSN=0;
	public static int dataSourceSN=0;//数据源 0-采控直读和数据库直读 1-采控直读 2-数据库直读
	static {
		map = new HashMap<Integer, License>();
		map.put(0, new License("阿里云服务器 Windows Server2019", "00:16:3E:01:D8:63", 0,"IP:8.130.30.138"));
		map.put(1, new License("必创", "", 200,"大庆九厂试验项目"));
		map.put(2, new License("青海", "00:50:56:9D:6B:D3", 200,"青海项目"));
		
		moduleMap=new HashMap<Integer, List<Integer>>();//全集
		List<Integer> moduleList0=new ArrayList<Integer>();
		moduleList0.add(9999);//功能导航
		moduleList0.add(1757);//功图软件
		moduleList0.add(5);//实时评价
		moduleList0.add(1678);//全天评价
		moduleList0.add(554);//生产报表
		moduleList0.add(1315);//图形查询
		moduleList0.add(2001);//WebSocket测试
		moduleList0.add(1958);//网关测调
		moduleList0.add(1938);//命令下行
		moduleList0.add(1959);//原始数据
		moduleList0.add(27);//权限管理
		moduleList0.add(24);//单位管理
		moduleList0.add(28);//用户管理
		moduleList0.add(29);//角色管理
		moduleList0.add(31);//数据配置
		moduleList0.add(1777);//数据源
		moduleList0.add(34);//井名信息
		moduleList0.add(35);//生产数据
		moduleList0.add(36);//井身轨迹
		moduleList0.add(1718);//抽油机信息
		moduleList0.add(2000);//数据维护
		moduleList0.add(1797);//计算维护
		moduleList0.add(1515);//功图上传
		moduleList0.add(1877);//图形优化
		moduleList0.add(23);//系统配置
		moduleList0.add(26);//模块配置
		moduleList0.add(894);//字典配置
		moduleList0.add(1697);//统计配置
		moduleList0.add(47);//报警配置
		moduleMap.put(0, moduleList0);
		
		List<Integer> moduleList1=new ArrayList<Integer>();//自动化采集
		moduleList1.add(9999);//功能导航
		moduleList1.add(1757);//功图软件
		moduleList1.add(5);//实时评价
		moduleList1.add(1678);//全天评价
		moduleList1.add(554);//生产报表
		moduleList1.add(1315);//图形查询
//		moduleList1.add(2001);//WebSocket测试
//		moduleList1.add(1958);//网关测调
//		moduleList1.add(1938);//命令下行
//		moduleList1.add(1959);//原始数据
		moduleList1.add(27);//权限管理
		moduleList1.add(24);//单位管理
		moduleList1.add(28);//用户管理
		moduleList1.add(29);//角色管理
		moduleList1.add(31);//数据配置
		moduleList0.add(1777);//数据源
		moduleList1.add(34);//井名信息
		moduleList1.add(35);//生产数据
		moduleList1.add(36);//井身轨迹
		moduleList1.add(1718);//抽油机信息
		moduleList1.add(2000);//数据维护
		moduleList1.add(1797);//计算维护
//		moduleList1.add(1515);//功图上传
//		moduleList1.add(1877);//图形优化
		moduleList1.add(23);//系统配置
		moduleList1.add(26);//模块配置
		moduleList1.add(894);//字典配置
		moduleList1.add(1697);//统计配置
		moduleList1.add(47);//报警配置
		moduleMap.put(1, moduleList1);
		
		List<Integer> moduleList2=new ArrayList<Integer>();//非自动化
		moduleList2.add(9999);//功能导航
		moduleList2.add(1757);//功图软件
		moduleList2.add(5);//实时评价
		moduleList2.add(1678);//全天评价
		moduleList2.add(554);//生产报表
		moduleList2.add(1315);//图形查询
//		moduleList2.add(2001);//WebSocket测试
//		moduleList2.add(1958);//网关测调
//		moduleList2.add(1938);//命令下行
//		moduleList2.add(1959);//原始数据
		moduleList2.add(27);//权限管理
		moduleList2.add(24);//单位管理
		moduleList2.add(28);//用户管理
		moduleList2.add(29);//角色管理
		moduleList2.add(31);//数据配置
		moduleList0.add(1777);//数据源
		moduleList2.add(34);//井名信息
		moduleList2.add(35);//生产数据
		moduleList2.add(36);//井身轨迹
		moduleList2.add(1718);//抽油机信息
		moduleList2.add(2000);//数据维护
		moduleList2.add(1797);//计算维护
//		moduleList2.add(1515);//功图上传
//		moduleList2.add(1877);//图形优化
		moduleList2.add(23);//系统配置
		moduleList2.add(26);//模块配置
		moduleList2.add(894);//字典配置
		moduleList2.add(1697);//统计配置
		moduleList2.add(47);//报警配置
		moduleMap.put(2, moduleList2);
	}

	public static Map<Integer, License> getMapObject() {
		return map;
	}
	
	public static Map<Integer, List<Integer>> getModuleMapObject() {
		return moduleMap;
	}
	
	public static class License{
		public String Customer;
		public String Mac;
		public int Number;
		public String Remark;
		public List<Integer> modules;
		public License(String customer, String mac, int number, String remark) {
			super();
			Customer = customer;
			Mac = mac;
			Number = number;
			Remark = remark;
			modules=new ArrayList<Integer>();
			switch(modulesSN){
			case 0 :
//				modules.add(e)
				break;
		    case 1 :
		    	break;
		    default : //可选
		    	//语句
			}
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
		public List<Integer> getModules() {
			return modules;
		}
		public void setModules(List<Integer> modules) {
			this.modules = modules;
		}
	}

}
