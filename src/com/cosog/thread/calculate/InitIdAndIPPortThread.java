package com.cosog.thread.calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.SRPDeviceInfo;
import com.cosog.model.drive.InitId;
import com.cosog.model.drive.InitializedDeviceInfo;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.utils.AdInitMap;
import com.cosog.utils.Config;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

public class InitIdAndIPPortThread implements Runnable{

	private DeviceInfo deviceInfo;
	private int deviceType;
	private boolean initEnable;
	private String method;

	public InitIdAndIPPortThread(DeviceInfo deviceInfo, int deviceType,boolean initEnable,String method) {
		super();
		this.deviceInfo = deviceInfo;
		this.deviceType = deviceType;
		this.initEnable = initEnable;
		this.method = method;
	}


	@Override
	public void run(){
		String tcpType="";
		String id="";
		String ipPort="";
		String slave="";
		String wellType="";
		long time1 =System.nanoTime()/1000;

		if(deviceInfo!=null){
			initDevice(deviceInfo,initEnable,method);
			tcpType=deviceInfo.getTcpType();
			id=deviceInfo.getSignInId();
			ipPort=deviceInfo.getIpPort();
			slave=deviceInfo.getSlave();
			wellType="抽油机井";
		}
	
		long time2 =System.nanoTime()/1000;
//		StringManagerUtils.printLog(wellType+"ID/IPPort初始化耗时："+(time2-time1)+"μs"+",TCPType:"+tcpType+",ID:"+id+",IPPort:"+ipPort+",Slave:"+slave+",ThreadId:"+Thread.currentThread().getId());
	}

	public static void initDevice(DeviceInfo deviceInfo,boolean initEnable,String method){
		if(deviceInfo==null){
			return;
		}
		Gson gson = new Gson();
		String initUrl=Config.getInstance().configFile.getAd().getInit().getId();
		String initIPPortUrl=Config.getInstance().configFile.getAd().getInit().getIpPort();
		Map<String,InitializedDeviceInfo> initializedDeviceList=AdInitMap.getIdInitMapObject();
		Map<String,InitializedDeviceInfo> initializedIPPortDeviceList=AdInitMap.getIpPoetInitMapObject();

		String wellName=deviceInfo.getDeviceName();
		String tcpType=deviceInfo.getTcpType()==null?"":deviceInfo.getTcpType();
		String signinId=deviceInfo.getSignInId()==null?"":deviceInfo.getSignInId();
		String ipPort=deviceInfo.getIpPort()==null?"":deviceInfo.getIpPort();
		String slaveStr=deviceInfo.getSlave()==null?"":deviceInfo.getSlave();
		int slave=StringManagerUtils.stringToInteger(slaveStr);
		int peakDelay=deviceInfo.getPeakDelay();
		String instanceName=deviceInfo.getInstanceName()==null?"":deviceInfo.getInstanceName();
		int deviceType=deviceInfo.getDeviceType();
		int deviceId=deviceInfo.getId();
		int orgId=deviceInfo.getOrgId();
		int status=deviceInfo.getStatus();
		
		InitializedDeviceInfo initialized=null;
		InitializedDeviceInfo otherInitialized=null;
		String url=initUrl;
		int initType=0;
		if("TCPServer".equalsIgnoreCase(tcpType.replaceAll(" ", ""))){
			url=initIPPortUrl;
			initType=1;
			initialized=initializedIPPortDeviceList.get(0+"_"+deviceId);
			//另一种方式是否执行了初始化，是则删除
			otherInitialized=initializedDeviceList.get(0+"_"+deviceId);
			if(otherInitialized!=null){
				InitId initId=new InitId();
				initId.setMethod("delete");
				initId.setID(otherInitialized.getSigninid());
				initId.setSlave(otherInitialized.getSlave());
				initId.setInstanceName(otherInitialized.getInstanceName());
				initId.setPeakDelay(peakDelay);
				StringManagerUtils.printLog("ID初始化："+initUrl+","+gson.toJson(initId));
				String response="";
				if(initEnable){
					response=StringManagerUtils.sendPostMethod(initUrl, gson.toJson(initId),"utf-8",0,0);
				}
//				if(StringManagerUtils.isNotNull(response)){
					initializedDeviceList.remove(0+"_"+deviceId);
//				}
			}
		}else{
			initialized=initializedDeviceList.get(0+"_"+deviceId);
			//另一种方式是否执行了初始化，是则删除
			otherInitialized=initializedIPPortDeviceList.get(0+"_"+deviceId);
			if(otherInitialized!=null){
				InitId initId=new InitId();
				initId.setMethod("delete");
				initId.setIPPort(otherInitialized.getIpPort());
				initId.setSlave(otherInitialized.getSlave());
				initId.setInstanceName(otherInitialized.getInstanceName());
				initId.setPeakDelay(peakDelay);
				StringManagerUtils.printLog("ID初始化："+initIPPortUrl+","+gson.toJson(initId));
				String response="";
				if(initEnable){
					response=StringManagerUtils.sendPostMethod(initIPPortUrl, gson.toJson(initId),"utf-8",0,0);
				}
//				if(StringManagerUtils.isNotNull(response)){
					initializedIPPortDeviceList.remove(0+"_"+deviceId);
//				}
			}
		}
		 
		if("update".equalsIgnoreCase(method)&&status==1){
			if(initialized==null
					&& StringManagerUtils.isNotNull(tcpType)
					&&( ( "TCPServer".equalsIgnoreCase(tcpType.replaceAll(" ", "")) && StringManagerUtils.isNotNull(ipPort)  ) || ( "TCPClient".equalsIgnoreCase(tcpType.replaceAll(" ", "")) && StringManagerUtils.isNotNull(signinId)  )) 
//					&& slave>0 
					&& StringManagerUtils.isNotNull(slaveStr)
					&& StringManagerUtils.isNotNull(instanceName)
					){//如果未初始化
				InitId initId=new InitId();
				initId.setMethod("update");
				if(initType==0){
					initId.setID(signinId);
				}else{
					initId.setIPPort(ipPort);
				}
				initId.setSlave((byte) slave);
				initId.setInstanceName(instanceName);
				initId.setPeakDelay(peakDelay);
				StringManagerUtils.printLog("ID初始化："+url+","+gson.toJson(initId));
				String response="";
				if(initEnable){
					response=StringManagerUtils.sendPostMethod(url, gson.toJson(initId),"utf-8",0,0);
				}
//				if(StringManagerUtils.isNotNull(response)){
					InitializedDeviceInfo initializedDeviceInfo=new InitializedDeviceInfo(orgId,deviceId,wellName,deviceType,tcpType,signinId,ipPort,(byte) slave,peakDelay,instanceName);
					if(initType==0){
						initializedDeviceList.put(0+"_"+initializedDeviceInfo.getDeviceId(), initializedDeviceInfo);
					}else{
						initializedIPPortDeviceList.put(0+"_"+initializedDeviceInfo.getDeviceId(), initializedDeviceInfo);
					}
//				}
			}else if(initialized!=null){
				//如果已经初始化但下位机TCP类型、注册包ID、设备从地址、实例有一项为空，删除设备
				if( (!StringManagerUtils.isNotNull(tcpType)) 
						||( "TCPClient".equalsIgnoreCase(tcpType.replaceAll(" ", "")) && (!StringManagerUtils.isNotNull(signinId))) 
						||( "TCPServer".equalsIgnoreCase(tcpType.replaceAll(" ", "")) && (!StringManagerUtils.isNotNull(ipPort))) 
//						|| slave==0 
						|| (!StringManagerUtils.isNotNull(slaveStr)) 
						|| (!StringManagerUtils.isNotNull(instanceName)) 
						){
					InitId initId=new InitId();
					initId.setMethod("delete");
					if(initType==0){
						initId.setID(initialized.getSigninid());
					}else{
						initId.setIPPort(initialized.getIpPort());
					}
					initId.setSlave(initialized.getSlave());
					initId.setInstanceName(initialized.getInstanceName());
					initId.setPeakDelay(peakDelay);
					StringManagerUtils.printLog("ID初始化："+url+","+gson.toJson(initId));
					String response="";
					if(initEnable){
						response=StringManagerUtils.sendPostMethod(url, gson.toJson(initId),"utf-8",0,0);
					}
//					if(StringManagerUtils.isNotNull(response)){
						if(initType==0){
							initializedDeviceList.remove(0+"_"+deviceId);
						}else{
							initializedIPPortDeviceList.remove(0+"_"+deviceId);
						}
//					}
				}
				//如果已经初始化但信息有变化
				else if(! (
						initialized.getSigninid().equalsIgnoreCase(signinId) 
						&& initialized.getIpPort().equalsIgnoreCase(ipPort) 
						&& initialized.getSlave()==(byte) slave 
						&& initialized.getInstanceName().equalsIgnoreCase(instanceName)  
						&& initialized.getPeakDelay()==peakDelay
						)  ){
					InitId initId=new InitId();
					//如果注册包ID、设备从地址、实例变化，删除设备
					if(! (
							initialized.getSigninid().equalsIgnoreCase(signinId) 
							&& initialized.getIpPort().equalsIgnoreCase(ipPort) 
							&& initialized.getSlave()==(byte) slave 
							&& initialized.getInstanceName().equalsIgnoreCase(instanceName)
							) ){
						//删掉原有初始化
						initId.setMethod("delete");
						if(initType==0){
							initId.setID(initialized.getSigninid());
						}else{
							initId.setIPPort(initialized.getIpPort());
						}
						initId.setSlave(initialized.getSlave());
						initId.setInstanceName(initialized.getInstanceName());
						initId.setPeakDelay(peakDelay);
						StringManagerUtils.printLog("ID初始化："+url+","+gson.toJson(initId));
						if(initEnable){
							StringManagerUtils.sendPostMethod(url, gson.toJson(initId),"utf-8",0,0);
						}
					}
					//重新初始化
					initId=new InitId();
					initId.setMethod("update");
					if(initType==0){
						initId.setID(signinId);
					}else{
						initId.setIPPort(ipPort);
					}
					initId.setSlave((byte) slave);
					initId.setInstanceName(instanceName);
					initId.setPeakDelay(peakDelay);
					StringManagerUtils.printLog("ID初始化："+url+","+gson.toJson(initId));
					String response="";
					if(initEnable){
						response=StringManagerUtils.sendPostMethod(url, gson.toJson(initId),"utf-8",0,0);
					}
//					if(StringManagerUtils.isNotNull(response)){
						InitializedDeviceInfo initializedDeviceInfo=new InitializedDeviceInfo(orgId,deviceId,wellName,deviceType,tcpType,signinId,ipPort,(byte) slave,peakDelay,instanceName);
						if(initType==0){
							initializedDeviceList.put(0+"_"+initializedDeviceInfo.getDeviceId(), initializedDeviceInfo);
						}else{
							initializedIPPortDeviceList.put(0+"_"+initializedDeviceInfo.getDeviceId(), initializedDeviceInfo);
						}
//					}
				}
			}
		}else{
			if(initialized!=null){
				//删掉原有初始化
				InitId initId=new InitId();
				initId.setMethod("delete");
				if(initType==0){
					initId.setID(initialized.getSigninid());
				}else{
					initId.setIPPort(initialized.getIpPort());
				}
				initId.setSlave(initialized.getSlave());
				initId.setInstanceName(initialized.getInstanceName());
				initId.setPeakDelay(peakDelay);
				StringManagerUtils.printLog("ID初始化："+url+","+gson.toJson(initId));
				String response="";
				if(initEnable){
					response=StringManagerUtils.sendPostMethod(url, gson.toJson(initId),"utf-8",0,0);
				}
//				if(StringManagerUtils.isNotNull(response)){
					if(initType==0){
						initializedDeviceList.remove(0+"_"+deviceId);
					}else{
						initializedIPPortDeviceList.remove(0+"_"+deviceId);
					}
//				}
				if("update".equalsIgnoreCase(method)){
					List<String> offlineWellList=new ArrayList<String>();
					offlineWellList.add(deviceId+"");
					EquipmentDriverServerTask.sendDeviceOfflineInfo(offlineWellList);
				}
			}
		}
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public boolean isInitEnable() {
		return initEnable;
	}

	public void setInitEnable(boolean initEnable) {
		this.initEnable = initEnable;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}


	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}


	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
}
