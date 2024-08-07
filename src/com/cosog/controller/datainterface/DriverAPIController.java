package com.cosog.controller.datainterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.DataMapping;
import com.cosog.model.DataWriteBackConfig;
import com.cosog.model.KeyValue;
import com.cosog.model.Org;
import com.cosog.model.ProtocolRunStatusConfig;
import com.cosog.model.User;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AcqInstanceOwnItem;
import com.cosog.model.calculate.AlarmInstanceOwnItem;
import com.cosog.model.calculate.CommResponseData;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.DisplayInstanceOwnItem;
import com.cosog.model.calculate.EnergyCalculateResponseData;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.PCPCalculateResponseData;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.PCPDeviceTodayData;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.RPCCalculateResponseData;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.RPCDeviceTodayData;
import com.cosog.model.calculate.TimeEffResponseData;
import com.cosog.model.calculate.TotalAnalysisRequestData;
import com.cosog.model.calculate.TotalAnalysisResponseData;
import com.cosog.model.calculate.UserInfo;
import com.cosog.model.calculate.DeviceInfo.DailyTotalItem;
import com.cosog.model.drive.AcqGroup;
import com.cosog.model.drive.AcqOnline;
import com.cosog.model.drive.AcquisitionItemInfo;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.datainterface.CalculateDataService;
import com.cosog.service.mobile.MobileService;
import com.cosog.service.right.UserManagerService;
import com.cosog.task.CalculateDataManagerTask;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.task.OuterDatabaseSyncTask;
import com.cosog.task.OuterDatabaseSyncTask.RPCWellDataSyncThread;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.utils.AcquisitionItemColumnsMap;
import com.cosog.utils.AlarmInfoMap;
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.Page;
import com.cosog.utils.ProtocolItemResolutionData;
import com.cosog.utils.Recursion;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.cosog.websocket.config.WebSocketByJavax;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/api")
@Scope("prototype")
public class DriverAPIController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CalculateDataService<?> calculateDataService;
	@Autowired
	private CommonDataService commonDataService;
	@Autowired
	private MobileService<?> mobileService;
	@Autowired
	private UserManagerService<User> userManagerService;
	@Bean
    public static WebSocketByJavax infoHandler() {
        return new WebSocketByJavax();
    }
	
	@RequestMapping("/acq/allDeviceOffline")
	public String AllDeviceOffline(){
		StringBuffer webSocketSendData = new StringBuffer();
		Jedis jedis=null;
		String functionCode="adExitAndDeviceOffline";
		String time=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		PrintWriter pw=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("DeviceInfo".getBytes())){
				MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
			}
			List<byte[]> deviceInfoByteList =jedis.hvals("DeviceInfo".getBytes());
			for(int i=0;i<deviceInfoByteList.size();i++){
				String realtimeTable="tbl_acqdata_latest";
				String historyTable="tbl_acqdata_hist";
				Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
				if (obj instanceof DeviceInfo) {
					DeviceInfo deviceInfo=(DeviceInfo)obj;
					String instanceCode=deviceInfo.getInstanceCode();
					if(!(!StringManagerUtils.isNotNull(instanceCode)||instanceCode.toUpperCase().contains("KAFKA")||instanceCode.toUpperCase().contains("RPC") || instanceCode.toUpperCase().contains("MQTT"))){
						if(deviceInfo.getOnLineCommStatus()>0){
							String commRequest="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+deviceInfo.getWellName()+"\","
									+ "\"OffsetHour\":"+Config.getInstance().configFile.getAp().getReport().getOffsetHour()+",";
							if(StringManagerUtils.isNotNull(deviceInfo.getOnLineAcqTime())&&StringManagerUtils.isNotNull(deviceInfo.getOnLineCommRange())){
								commRequest+= "\"Last\":{"
										+ "\"AcqTime\": \""+deviceInfo.getOnLineAcqTime()+"\","
										+ "\"CommStatus\": "+(deviceInfo.getOnLineCommStatus()>0?true:false)+","
										+ "\"CommEfficiency\": {"
										+ "\"Efficiency\": "+deviceInfo.getOnLineCommEff()+","
										+ "\"Time\": "+deviceInfo.getOnLineCommTime()+","
										+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(deviceInfo.getOnLineCommRange())+""
										+ "}"
										+ "},";
							}	
							commRequest+= "\"Current\": {"
									+ "\"AcqTime\":\""+time+"\","
									+ "\"CommStatus\":false"
									+ "}"
									+ "}";
							CommResponseData commResponseData=CalculateUtils.commCalculate(commRequest);
							
							String updateRealData="update "+realtimeTable+" t set t.acqTime=to_date('"+time+"','yyyy-mm-dd hh24:mi:ss'), t.CommStatus=0";
							String updateRealCommRangeClobSql="update "+realtimeTable+" t set t.commrange=?";
							
//							String updateHistData="update "+historyTable+" t set t.acqTime=to_date('"+time+"','yyyy-mm-dd hh24:mi:ss'), t.CommStatus=0";
							String insertHistColumns="deviceid,acqTime,CommStatus";
							String insertHistValue=deviceInfo.getId()+",to_date('"+time+"','yyyy-mm-dd hh24:mi:ss'),0";
							String insertHistSql="";
							String updateHistCommRangeClobSql="update "+historyTable+" t set t.commrange=?";
							List<String> clobCont=new ArrayList<String>();
							
							if(commResponseData!=null&&commResponseData.getResultStatus()==1){
								updateRealData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
										+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
								insertHistColumns+=",commTimeEfficiency,commTime";
								insertHistValue+=","+commResponseData.getCurrent().getCommEfficiency().getEfficiency()+","+commResponseData.getCurrent().getCommEfficiency().getTime();
								
								clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
							}
							updateRealData+=" where t.deviceId= "+deviceInfo.getId();
							updateRealCommRangeClobSql+=" where t.deviceId= "+deviceInfo.getId();
							
//							updateHistData+=" where t.deviceId= "+deviceInfo.getId()+" and t.acqtime=( select max(t2.acqtime) from "+historyTable+" t2 where t2.deviceid=t.deviceid )";
							insertHistSql="insert into "+historyTable+"("+insertHistColumns+")values("+insertHistValue+")";
							updateHistCommRangeClobSql+=" where t.deviceId= "+deviceInfo.getId()+" and t.acqtime=to_date('"+time+"','yyyy-mm-dd hh24:mi:ss')";
							
							int result=commonDataService.getBaseDao().updateOrDeleteBySql(updateRealData);
							if(result==0){
								result=commonDataService.getBaseDao().updateOrDeleteBySql(insertHistSql.replaceAll(historyTable, realtimeTable));
							}
							result=commonDataService.getBaseDao().updateOrDeleteBySql(insertHistSql);
							if(commResponseData!=null&&commResponseData.getResultStatus()==1){
								result=commonDataService.getBaseDao().executeSqlUpdateClob(updateRealCommRangeClobSql,clobCont);
								result=commonDataService.getBaseDao().executeSqlUpdateClob(updateHistCommRangeClobSql,clobCont);
							}
							
							deviceInfo.setCommStatus(0);
							deviceInfo.setOnLineCommStatus(0);
							if(commResponseData!=null && commResponseData.getResultStatus()==1){
								deviceInfo.setAcqTime(time);
								deviceInfo.setCommTime(commResponseData.getCurrent().getCommEfficiency().getTime());
								deviceInfo.setCommEff(commResponseData.getCurrent().getCommEfficiency().getEfficiency());
								deviceInfo.setCommRange(commResponseData.getCurrent().getCommEfficiency().getRangeString());
								
								deviceInfo.setOnLineAcqTime(time);
								deviceInfo.setOnLineCommTime(commResponseData.getCurrent().getCommEfficiency().getTime());
								deviceInfo.setOnLineCommEff(commResponseData.getCurrent().getCommEfficiency().getEfficiency());
								deviceInfo.setOnLineCommRange(commResponseData.getCurrent().getCommEfficiency().getRangeString());
							}
							String key=deviceInfo.getId()+"";
							jedis.hset("DeviceInfo".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(deviceInfo));
						}
					}
				}
			}
			webSocketSendData.append("{\"functionCode\":\""+functionCode+"\",");
			webSocketSendData.append("\"time\":\""+time+"\"");
			webSocketSendData.append("}");
			if(StringManagerUtils.isNotNull(webSocketSendData.toString())){
				infoHandler().sendMessageToBy("ApWebSocketClient", webSocketSendData.toString());
			}
			
			String json = "{success:true,flag:true}";
			response.setContentType("application/json;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
			if(pw!=null){
				pw.close();
			}
		}
		
		return null;
	}
	
	@RequestMapping(path={"/acq/id/online","/acq/online"})
	public String AcqOnlineData() {
		ServletInputStream ss=null;
		Gson gson=new Gson();
		Jedis jedis=null;
		AlarmInstanceOwnItem alarmInstanceOwnItem=null;
		String json = "{success:true,flag:true}";
		PrintWriter pw=null;
		try {
			ss = request.getInputStream();
			StringBuffer webSocketSendData = new StringBuffer();
			String data=StringManagerUtils.convertStreamToString(ss,"utf-8");
			StringManagerUtils.printLog("接收到ad推送online数据："+data);
			java.lang.reflect.Type type = new TypeToken<AcqOnline>() {}.getType();
			AcqOnline acqOnline=gson.fromJson(data, type);
			String acqTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			
			if(acqOnline!=null){
				jedis = RedisUtil.jedisPool.getResource();
				List<DeviceInfo> deviceInfoList=new ArrayList<DeviceInfo>();
				int deviceType=0;
				int deviceId=0;
				String deviceName="";
				int orgId=0;
				String currentTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
				
				if(!jedis.exists("DeviceInfo".getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				List<byte[]> deviceInfoByteList =jedis.hvals("DeviceInfo".getBytes());
				for(int i=0;i<deviceInfoByteList.size();i++){
					Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
					if (obj instanceof DeviceInfo) {
						DeviceInfo memDeviceInfo=(DeviceInfo)obj;
						if(acqOnline.getID().equalsIgnoreCase(memDeviceInfo.getSignInId()) && StringManagerUtils.isNotNull(memDeviceInfo.getSlave())){
							if(acqOnline.getSlave()>0 || data.contains("\"Slave\"")){
								if(acqOnline.getSlave()==StringManagerUtils.stringToInteger(memDeviceInfo.getSlave())){
									deviceInfoList.add(memDeviceInfo);
								}
							}else if(memDeviceInfo.getStatus()==1){
								deviceInfoList.add(memDeviceInfo);
							}
						}
					}
				}
				
				
				if(deviceInfoList.size()>0){
					String realtimeTable="";
					String historyTable="";
					
					String functionCode="deviceRealTimeMonitoringStatusData";
					String alarmTableName="tbl_alarminfo_hist";
					String commRequest="";
					
					for(int i=0;i<deviceInfoList.size();i++){
						//抽油机
						DeviceInfo deviceInfo=deviceInfoList.get(i);
						deviceType=deviceInfo.getDeviceType();
						deviceId=deviceInfo.getId();
						deviceName=deviceInfo.getWellName();
						orgId=deviceInfo.getOrgId();
						
						alarmTableName="tbl_alarminfo_hist";
						realtimeTable="tbl_acqdata_latest";
						historyTable="tbl_acqdata_hist";
						functionCode="deviceRealTimeMonitoringStatusData";
						
						String commTime="",commTimeEfficiency="",commRange="";
						alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(deviceInfo.getAlarmInstanceCode());
						commRequest="{"
								+ "\"AKString\":\"\","
								+ "\"WellName\":\""+deviceInfo.getWellName()+"\","
								+ "\"OffsetHour\":"+Config.getInstance().configFile.getAp().getReport().getOffsetHour()+",";
						if(StringManagerUtils.isNotNull(deviceInfo.getOnLineAcqTime())&&StringManagerUtils.isNotNull(deviceInfo.getOnLineCommRange())){
							commRequest+= "\"Last\":{"
									+ "\"AcqTime\": \""+deviceInfo.getOnLineAcqTime()+"\","
									+ "\"CommStatus\": "+(deviceInfo.getOnLineCommStatus()>0?true:false)+","
									+ "\"CommEfficiency\": {"
									+ "\"Efficiency\": "+deviceInfo.getOnLineCommEff()+","
									+ "\"Time\": "+deviceInfo.getOnLineCommTime()+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(deviceInfo.getOnLineCommRange())+""
									+ "}"
									+ "},";
						}	
						commRequest+= "\"Current\": {"
								+ "\"AcqTime\":\""+acqTime+"\","
								+ "\"CommStatus\":"+acqOnline.getStatus()+""
								+ "}"
								+ "}";
						CommResponseData commResponseData=CalculateUtils.commCalculate(commRequest);
						
						String updateRealData="update "+realtimeTable+" t set t.acqTime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'), t.CommStatus="+(acqOnline.getStatus()?2:0);
						String updateRealCommRangeClobSql="update "+realtimeTable+" t set t.commrange=?";
						
						String insertHistColumns="deviceid,acqTime,CommStatus,runStatus,runTimeEfficiency,runTime";
						String insertHistValue=deviceId+",to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),"+(acqOnline.getStatus()?2:0)+",null,null,null";
						String insertHistSql="";
						
						String updateHistCommRangeClobSql="update "+historyTable+" t set t.commrange=?";
						List<String> clobCont=new ArrayList<String>();
						
						if(commResponseData!=null&&commResponseData.getResultStatus()==1){
							commTime=commResponseData.getCurrent().getCommEfficiency().getTime()+"";
							commTimeEfficiency=commResponseData.getCurrent().getCommEfficiency().getEfficiency()+"";
							commRange=commResponseData.getCurrent().getCommEfficiency().getRangeString();
							updateRealData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
									+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
							
							insertHistColumns+=",commTimeEfficiency,commTime";
							insertHistValue+=","+commResponseData.getCurrent().getCommEfficiency().getEfficiency()+","+commResponseData.getCurrent().getCommEfficiency().getTime();
							
							clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
						}
						updateRealData+=" where t.deviceId= "+deviceId;
						updateRealCommRangeClobSql+=" where t.deviceId= "+deviceId;
						insertHistSql="insert into "+historyTable+"("+insertHistColumns+")values("+insertHistValue+")";
						
						updateHistCommRangeClobSql+=" where t.deviceId= "+deviceId+" and t.acqtime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
						
						int result=commonDataService.getBaseDao().updateOrDeleteBySql(updateRealData);
						if(result==0){
							result=commonDataService.getBaseDao().updateOrDeleteBySql(insertHistSql.replaceAll(historyTable, realtimeTable));
						}
						result=commonDataService.getBaseDao().updateOrDeleteBySql(insertHistSql);
						if(commResponseData!=null&&commResponseData.getResultStatus()==1){
							result=commonDataService.getBaseDao().executeSqlUpdateClob(updateRealCommRangeClobSql,clobCont);
							result=commonDataService.getBaseDao().executeSqlUpdateClob(updateHistCommRangeClobSql,clobCont);
						}
						
						
						String commAlarm="";
						int commAlarmLevel=0,isSendMessage=0,isSendMail=0,delay=0;
						String key="";
						String alarmInfo="";
						String alarmSMSContent="";
						if(alarmInstanceOwnItem!=null){
							Map<String, String> alarmInfoMap=AlarmInfoMap.getMapObject();
							if(acqOnline.getStatus()){
								key=deviceId+","+deviceType+",上线";
								alarmInfo="上线";
								alarmSMSContent="设备"+deviceName+"于"+currentTime+"上线";
							}else{
								key=deviceId+","+deviceType+",离线";
								alarmInfo="离线";
								alarmSMSContent="设备"+deviceName+"于"+currentTime+"离线";
							}
							for(int j=0;j<alarmInstanceOwnItem.getItemList().size();j++){
								if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 &&   alarmInfo.equalsIgnoreCase(alarmInstanceOwnItem.getItemList().get(j).getItemName()) && alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel()>0){
									commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
									isSendMessage=alarmInstanceOwnItem.getItemList().get(j).getIsSendMessage();
									isSendMail=alarmInstanceOwnItem.getItemList().get(j).getIsSendMail();
									delay=alarmInstanceOwnItem.getItemList().get(j).getDelay();
									break;
								}
							}
							commAlarm="insert into "+alarmTableName+" (deviceid,alarmtime,itemname,alarmtype,alarmvalue,alarminfo,alarmlevel)"
									+ "values("+deviceId+",to_date('"+currentTime+"','yyyy-mm-dd hh24:mi:ss'),'通信状态',3,"+(acqOnline.getStatus()?2:0)+",'"+alarmInfo+"',"+commAlarmLevel+")";
							
							
							String lastAlarmTime=alarmInfoMap.get(key);
							long timeDiff=StringManagerUtils.getTimeDifference(lastAlarmTime, currentTime, "yyyy-MM-dd HH:mm:ss");
							if(commAlarmLevel>0&&timeDiff>delay*1000){
								result=commonDataService.getBaseDao().updateOrDeleteBySql(commAlarm);
								calculateDataService.sendAlarmSMS(deviceName, deviceType+"",isSendMessage==1,isSendMail==1,alarmSMSContent,alarmSMSContent);
								alarmInfoMap.put(key, currentTime);
							}
						}
						
						if(deviceInfo!=null){
							deviceInfo.setOnLineCommStatus(acqOnline.getStatus()?2:0);
							if(commResponseData!=null && commResponseData.getResultStatus()==1){
								deviceInfo.setOnLineAcqTime(acqTime);
								deviceInfo.setOnLineCommTime(commResponseData.getCurrent().getCommEfficiency().getTime());
								deviceInfo.setOnLineCommEff(commResponseData.getCurrent().getCommEfficiency().getEfficiency());
								deviceInfo.setOnLineCommRange(commResponseData.getCurrent().getCommEfficiency().getRangeString());
							}
							jedis.hset("DeviceInfo".getBytes(), (deviceInfo.getId()+"").getBytes(), SerializeObjectUnils.serialize(deviceInfo));
						}
						webSocketSendData = new StringBuffer();
						webSocketSendData.append("{\"functionCode\":\""+functionCode+"\",");
						webSocketSendData.append("\"deviceName\":\""+deviceName+"\",");
						webSocketSendData.append("\"deviceId\":"+deviceId+",");
						webSocketSendData.append("\"orgId\":"+orgId+",");
						webSocketSendData.append("\"deviceType\":"+deviceType+",");
						webSocketSendData.append("\"acqTime\":\""+currentTime+"\",");
						webSocketSendData.append("\"commStatus\":"+(acqOnline.getStatus()?2:0)+",");
						webSocketSendData.append("\"commStatusName\":\""+(acqOnline.getStatus()?"上线":"离线")+"\",");
						webSocketSendData.append("\"commTime\":\""+commTime+"\",");
						webSocketSendData.append("\"commTimeEfficiency\":\""+commTimeEfficiency+"\",");
						webSocketSendData.append("\"commRange\":\""+commRange+"\",");
						webSocketSendData.append("\"commAlarmLevel\":"+commAlarmLevel);
						webSocketSendData.append("}");
						if(StringManagerUtils.isNotNull(webSocketSendData.toString())){
							infoHandler().sendMessageToBy("ApWebSocketClient", webSocketSendData.toString());
						}
					}
				}
			}else{
				json = "{success:true,flag:false}";
			}
			response.setContentType("application/json;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
			if(ss!=null){
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(pw!=null){
				pw.close();
			}
		}
		return null;
	}
	
	@RequestMapping("/acq/ipport/online")
	public String IPPortAcqOnlineData() {
		ServletInputStream ss=null;
		Gson gson=new Gson();
		Jedis jedis=null;
		AlarmInstanceOwnItem alarmInstanceOwnItem=null;
		PrintWriter pw=null;
		String json = "{success:true,flag:true}";
		try {
			ss = request.getInputStream();
			StringBuffer webSocketSendData = new StringBuffer();
			String data=StringManagerUtils.convertStreamToString(ss,"utf-8");
			StringManagerUtils.printLog("接收到ad推送online数据："+data);
			java.lang.reflect.Type type = new TypeToken<AcqOnline>() {}.getType();
			AcqOnline acqOnline=gson.fromJson(data, type);
			String acqTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			if(acqOnline!=null){
//				acqOnline.setID(acqOnline.getIPPort());
				jedis = RedisUtil.jedisPool.getResource();
				List<DeviceInfo> deviceInfoList=new ArrayList<DeviceInfo>();
				int deviceType=0;
				int deviceId=0;
				String deviceName="";
				int orgId=0;
				String currentTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
				if(!jedis.exists("DeviceInfo".getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				if(!jedis.exists("PCPDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				List<byte[]> deviceInfoByteList =jedis.hvals("DeviceInfo".getBytes());
				for(int i=0;i<deviceInfoByteList.size();i++){
					Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
					if (obj instanceof DeviceInfo) {
						DeviceInfo memDeviceInfo=(DeviceInfo)obj;
						if(acqOnline.getIPPort().equalsIgnoreCase(memDeviceInfo.getIpPort()) && StringManagerUtils.isNotNull(memDeviceInfo.getSlave()) && memDeviceInfo.getStatus()==1){
							if(acqOnline.getSlave()>0){
								if(acqOnline.getSlave()==StringManagerUtils.stringToInteger(memDeviceInfo.getSlave())){
									deviceInfoList.add(memDeviceInfo);
								}
							}else{
								deviceInfoList.add(memDeviceInfo);
							}
						}
					}
				}
				
				if(deviceInfoList.size()>0 ){
					String realtimeTable="";
					String historyTable="";
					
					String functionCode="deviceRealTimeMonitoringStatusData";
					String alarmTableName="tbl_alarminfo_hist";
					String commRequest="";
					
					for(int i=0;i<deviceInfoList.size();i++){
						//抽油机
						DeviceInfo deviceInfo=deviceInfoList.get(i);
						deviceType=deviceInfo.getDeviceType();
						deviceId=deviceInfo.getId();
						deviceName=deviceInfo.getWellName();
						orgId=deviceInfo.getOrgId();
						
						alarmTableName="tbl_alarminfo_hist";
						realtimeTable="tbl_acqdata_latest";
						historyTable="tbl_acqdata_hist";
						functionCode="deviceRealTimeMonitoringStatusData";
						
						String commTime="",commTimeEfficiency="",commRange="";
						alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(deviceInfo.getAlarmInstanceCode());
						commRequest="{"
								+ "\"AKString\":\"\","
								+ "\"WellName\":\""+deviceInfo.getWellName()+"\","
								+ "\"OffsetHour\":"+Config.getInstance().configFile.getAp().getReport().getOffsetHour()+",";
						if(StringManagerUtils.isNotNull(deviceInfo.getOnLineAcqTime())&&StringManagerUtils.isNotNull(deviceInfo.getOnLineCommRange())){
							commRequest+= "\"Last\":{"
									+ "\"AcqTime\": \""+deviceInfo.getOnLineAcqTime()+"\","
									+ "\"CommStatus\": "+(deviceInfo.getOnLineCommStatus()>0?true:false)+","
									+ "\"CommEfficiency\": {"
									+ "\"Efficiency\": "+deviceInfo.getOnLineCommEff()+","
									+ "\"Time\": "+deviceInfo.getOnLineCommTime()+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(deviceInfo.getOnLineCommRange())+""
									+ "}"
									+ "},";
						}
						commRequest+= "\"Current\": {"
								+ "\"AcqTime\":\""+acqTime+"\","
								+ "\"CommStatus\":"+acqOnline.getStatus()+""
								+ "}"
								+ "}";
						CommResponseData commResponseData=CalculateUtils.commCalculate(commRequest);
						
						String updateRealData="update "+realtimeTable+" t set t.acqTime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'), t.CommStatus="+(acqOnline.getStatus()?2:0);
						String updateRealCommRangeClobSql="update "+realtimeTable+" t set t.commrange=?";
						
						String insertHistColumns="deviceid,acqTime,CommStatus,runStatus,runTimeEfficiency,runTime";
						String insertHistValue=deviceId+",to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),"+(acqOnline.getStatus()?2:0)+",null,null,null";
						String insertHistSql="";
						
						String updateHistCommRangeClobSql="update "+historyTable+" t set t.commrange=?";
						List<String> clobCont=new ArrayList<String>();
						
						if(commResponseData!=null&&commResponseData.getResultStatus()==1){
							commTime=commResponseData.getCurrent().getCommEfficiency().getTime()+"";
							commTimeEfficiency=commResponseData.getCurrent().getCommEfficiency().getEfficiency()+"";
							commRange=commResponseData.getCurrent().getCommEfficiency().getRangeString();
							updateRealData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
									+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
							
							insertHistColumns+=",commTimeEfficiency,commTime";
							insertHistValue+=","+commResponseData.getCurrent().getCommEfficiency().getEfficiency()+","+commResponseData.getCurrent().getCommEfficiency().getTime();
							
							clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
						}
						updateRealData+=" where t.deviceId= "+deviceId;
						updateRealCommRangeClobSql+=" where t.deviceId= "+deviceId;
						insertHistSql="insert into "+historyTable+"("+insertHistColumns+")values("+insertHistValue+")";
						
						updateHistCommRangeClobSql+=" where t.deviceId= "+deviceId+" and t.acqtime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
						
						int result=commonDataService.getBaseDao().updateOrDeleteBySql(updateRealData);
						result=commonDataService.getBaseDao().updateOrDeleteBySql(insertHistSql);
						if(commResponseData!=null&&commResponseData.getResultStatus()==1){
							result=commonDataService.getBaseDao().executeSqlUpdateClob(updateRealCommRangeClobSql,clobCont);
							result=commonDataService.getBaseDao().executeSqlUpdateClob(updateHistCommRangeClobSql,clobCont);
						}
						
						
						String commAlarm="";
						int commAlarmLevel=0,isSendMessage=0,isSendMail=0,delay=0;
						String key="";
						String alarmInfo="";
						String alarmSMSContent="";
						if(alarmInstanceOwnItem!=null){
							Map<String, String> alarmInfoMap=AlarmInfoMap.getMapObject();
							if(acqOnline.getStatus()){
								key=deviceId+","+deviceType+",上线";
								alarmInfo="上线";
								alarmSMSContent="设备"+deviceName+"于"+currentTime+"上线";
							}else{
								key=deviceId+","+deviceType+",离线";
								alarmInfo="离线";
								alarmSMSContent="设备"+deviceName+"于"+currentTime+"离线";
							}
							for(int j=0;j<alarmInstanceOwnItem.getItemList().size();j++){
								if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 &&   alarmInfo.equalsIgnoreCase(alarmInstanceOwnItem.getItemList().get(j).getItemName()) && alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel()>0){
									commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
									isSendMessage=alarmInstanceOwnItem.getItemList().get(j).getIsSendMessage();
									isSendMail=alarmInstanceOwnItem.getItemList().get(j).getIsSendMail();
									delay=alarmInstanceOwnItem.getItemList().get(j).getDelay();
									break;
								}
							}
							commAlarm="insert into "+alarmTableName+" (deviceid,alarmtime,itemname,alarmtype,alarmvalue,alarminfo,alarmlevel)"
									+ "values("+deviceId+",to_date('"+currentTime+"','yyyy-mm-dd hh24:mi:ss'),'通信状态',3,"+(acqOnline.getStatus()?2:0)+",'"+alarmInfo+"',"+commAlarmLevel+")";
							
							
							String lastAlarmTime=alarmInfoMap.get(key);
							long timeDiff=StringManagerUtils.getTimeDifference(lastAlarmTime, currentTime, "yyyy-MM-dd HH:mm:ss");
							if(commAlarmLevel>0&&timeDiff>delay*1000){
								result=commonDataService.getBaseDao().updateOrDeleteBySql(commAlarm);
								calculateDataService.sendAlarmSMS(deviceName, deviceType+"",isSendMessage==1,isSendMail==1,alarmSMSContent,alarmSMSContent);
								alarmInfoMap.put(key, currentTime);
							}
						}
						
						if(deviceInfo!=null){
							deviceInfo.setOnLineCommStatus(acqOnline.getStatus()?2:0);
							if(commResponseData!=null && commResponseData.getResultStatus()==1){
								deviceInfo.setOnLineAcqTime(acqTime);
								deviceInfo.setOnLineCommTime(commResponseData.getCurrent().getCommEfficiency().getTime());
								deviceInfo.setOnLineCommEff(commResponseData.getCurrent().getCommEfficiency().getEfficiency());
								deviceInfo.setOnLineCommRange(commResponseData.getCurrent().getCommEfficiency().getRangeString());
							}
							jedis.hset("DeviceInfo".getBytes(), (deviceInfo.getId()+"").getBytes(), SerializeObjectUnils.serialize(deviceInfo));
						}
						
						webSocketSendData = new StringBuffer();
						webSocketSendData.append("{\"functionCode\":\""+functionCode+"\",");
						webSocketSendData.append("\"deviceName\":\""+deviceName+"\",");
						webSocketSendData.append("\"deviceId\":"+deviceId+",");
						webSocketSendData.append("\"orgId\":"+orgId+",");
						webSocketSendData.append("\"deviceType\":"+deviceType+",");
						webSocketSendData.append("\"acqTime\":\""+currentTime+"\",");
						webSocketSendData.append("\"commStatus\":"+(acqOnline.getStatus()?2:0)+",");
						webSocketSendData.append("\"commStatusName\":\""+(acqOnline.getStatus()?"上线":"离线")+"\",");
						webSocketSendData.append("\"commTime\":\""+commTime+"\",");
						webSocketSendData.append("\"commTimeEfficiency\":\""+commTimeEfficiency+"\",");
						webSocketSendData.append("\"commRange\":\""+commRange+"\",");
						webSocketSendData.append("\"commAlarmLevel\":"+commAlarmLevel);
						webSocketSendData.append("}");
						if(StringManagerUtils.isNotNull(webSocketSendData.toString())){
							infoHandler().sendMessageToBy("ApWebSocketClient", webSocketSendData.toString());
						}
					}
				}
			}else{
				json = "{success:true,flag:false}";
			}
			
			response.setContentType("application/json;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
			if(ss!=null){
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(pw!=null){
				pw.close();
			}
		}
		return null;
	}
	
	@RequestMapping(path={"/acq/id/group","/acq/group"})
	public String AcqGroupData(){
		ServletInputStream ss=null;
		Jedis jedis=null;
		String json = "{success:true,flag:true}";
		PrintWriter pw=null;
		try {
			ss = request.getInputStream();
			Gson gson=new Gson();
			String data=StringManagerUtils.convertStreamToString(ss,"utf-8");
			StringManagerUtils.printLog(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"接收到ad推送group数据："+data);
			java.lang.reflect.Type type = new TypeToken<AcqGroup>() {}.getType();
			AcqGroup acqGroup=gson.fromJson(data, type);
			if(acqGroup!=null){
				jedis = RedisUtil.jedisPool.getResource();
				DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(acqGroup.getID(), acqGroup.getSlave());
				if(deviceInfo!=null){
					this.DataProcessing(deviceInfo,acqGroup);
				}else{
					StringManagerUtils.printLog(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"接收到ad推送group数据无对应设备："+acqGroup.getID()+","+acqGroup.getSlave());
				}
			}else{
				json = "{success:true,flag:false}";
			}
			response.setContentType("application/json;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
			if(ss!=null){
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(pw!=null){
				pw.close();
			}
		}
		return null;
	}
	
	@RequestMapping("/acq/ipport/group")
	public String IPPortAcqGroupData(){
		ServletInputStream ss=null;
		Jedis jedis=null;
		String json = "{success:true,flag:true}";
		PrintWriter pw=null;
		try {
			ss = request.getInputStream();
			Gson gson=new Gson();
			String data=StringManagerUtils.convertStreamToString(ss,"utf-8");
			StringManagerUtils.printLog(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"接收到ad推送group数据："+data);
			java.lang.reflect.Type type = new TypeToken<AcqGroup>() {}.getType();
			AcqGroup acqGroup=gson.fromJson(data, type);
			if(acqGroup!=null){
//				acqGroup.setID(acqGroup.getIPPort());
				jedis = RedisUtil.jedisPool.getResource();
				DeviceInfo deviceInfo=null;
				
				if(!jedis.exists("DeviceInfo".getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				List<byte[]> deviceInfoByteList =jedis.hvals("DeviceInfo".getBytes());
				for(int i=0;i<deviceInfoByteList.size();i++){
					Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
					if (obj instanceof DeviceInfo) {
						DeviceInfo memDeviceInfo=(DeviceInfo)obj;
						if(acqGroup.getIPPort().equalsIgnoreCase(memDeviceInfo.getIpPort()) && acqGroup.getSlave()==StringManagerUtils.stringToInteger(memDeviceInfo.getSlave())){
							deviceInfo=memDeviceInfo;
							break;
						}
					}
				}
				
				
				this.DataProcessing(deviceInfo,acqGroup);
			}else{
				json = "{success:true,flag:false}";
			}
			response.setContentType("application/json;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
			if(ss!=null){
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(pw!=null){
				pw.close();
			}
		}
		
		return null;
	}
	
	public List<AcquisitionItemInfo> DataAlarmProcessing(List<ProtocolItemResolutionData> protocolItemResolutionDataList,AlarmInstanceOwnItem alarmInstanceOwnItem,List<AcquisitionItemInfo> acquisitionItemInfoList){
		for(int i=0;i<protocolItemResolutionDataList.size();i++){
			int alarmLevel=0;
			AcquisitionItemInfo acquisitionItemInfo=new AcquisitionItemInfo();
			acquisitionItemInfo.setAddr(StringManagerUtils.stringToInteger(protocolItemResolutionDataList.get(i).getAddr()));
			acquisitionItemInfo.setColumn(protocolItemResolutionDataList.get(i).getColumn());
			acquisitionItemInfo.setTitle(protocolItemResolutionDataList.get(i).getColumnName());
			acquisitionItemInfo.setRawTitle(protocolItemResolutionDataList.get(i).getRawColumnName());
			acquisitionItemInfo.setValue(protocolItemResolutionDataList.get(i).getValue());
			acquisitionItemInfo.setRawValue(protocolItemResolutionDataList.get(i).getRawValue());
			acquisitionItemInfo.setDataType(protocolItemResolutionDataList.get(i).getColumnDataType());
			acquisitionItemInfo.setResolutionMode(protocolItemResolutionDataList.get(i).getResolutionMode());
			acquisitionItemInfo.setBitIndex(protocolItemResolutionDataList.get(i).getBitIndex());
			acquisitionItemInfo.setAlarmLevel(alarmLevel);
			acquisitionItemInfo.setUnit(protocolItemResolutionDataList.get(i).getUnit());
			acquisitionItemInfo.setSort(protocolItemResolutionDataList.get(i).getSort());
			for(int l=0;alarmInstanceOwnItem!=null&&l<alarmInstanceOwnItem.getItemList().size();l++){
				if((acquisitionItemInfo.getAddr()+"").equals(alarmInstanceOwnItem.getItemList().get(l).getItemAddr()+"")){
					int alarmType=alarmInstanceOwnItem.getItemList().get(l).getType();
					if(alarmType==2 && StringManagerUtils.isNotNull(acquisitionItemInfo.getRawValue())){//数据量报警
						float hystersis=alarmInstanceOwnItem.getItemList().get(l).getHystersis();
						if(StringManagerUtils.isNotNull(alarmInstanceOwnItem.getItemList().get(l).getUpperLimit()+"") && StringManagerUtils.stringToFloat(acquisitionItemInfo.getRawValue())>alarmInstanceOwnItem.getItemList().get(l).getUpperLimit()+hystersis){
							alarmLevel=alarmInstanceOwnItem.getItemList().get(l).getAlarmLevel();
							acquisitionItemInfo.setAlarmLevel(alarmLevel);
							acquisitionItemInfo.setHystersis(hystersis);
							acquisitionItemInfo.setAlarmLimit(alarmInstanceOwnItem.getItemList().get(l).getUpperLimit());
							acquisitionItemInfo.setAlarmInfo("高报");
							acquisitionItemInfo.setAlarmType(2);
							acquisitionItemInfo.setAlarmDelay(alarmInstanceOwnItem.getItemList().get(l).getDelay());
							acquisitionItemInfo.setIsSendMessage(alarmInstanceOwnItem.getItemList().get(l).getIsSendMessage());
							acquisitionItemInfo.setIsSendMail(alarmInstanceOwnItem.getItemList().get(l).getIsSendMail());
						}else if((StringManagerUtils.isNotNull(alarmInstanceOwnItem.getItemList().get(l).getLowerLimit()+"") && StringManagerUtils.stringToFloat(acquisitionItemInfo.getRawValue())<alarmInstanceOwnItem.getItemList().get(l).getLowerLimit()-hystersis)){
							alarmLevel=alarmInstanceOwnItem.getItemList().get(l).getAlarmLevel();
							acquisitionItemInfo.setAlarmLevel(alarmLevel);
							acquisitionItemInfo.setHystersis(hystersis);
							acquisitionItemInfo.setAlarmLimit(alarmInstanceOwnItem.getItemList().get(l).getLowerLimit());
							acquisitionItemInfo.setAlarmInfo("低报");
							acquisitionItemInfo.setAlarmType(2);
							acquisitionItemInfo.setAlarmDelay(alarmInstanceOwnItem.getItemList().get(l).getDelay());
							acquisitionItemInfo.setIsSendMessage(alarmInstanceOwnItem.getItemList().get(l).getIsSendMessage());
							acquisitionItemInfo.setIsSendMail(alarmInstanceOwnItem.getItemList().get(l).getIsSendMail());
						}
						break;
					}else if(alarmType==0  && StringManagerUtils.isNotNull(acquisitionItemInfo.getRawValue()) ){//开关量报警
						if(StringManagerUtils.isNotNull(acquisitionItemInfo.getBitIndex())){
							if(acquisitionItemInfo.getBitIndex().equals(alarmInstanceOwnItem.getItemList().get(l).getBitIndex()+"") && StringManagerUtils.stringToInteger(acquisitionItemInfo.getRawValue())==StringManagerUtils.stringToInteger(alarmInstanceOwnItem.getItemList().get(l).getValue()+"")){
								alarmLevel=alarmInstanceOwnItem.getItemList().get(l).getAlarmLevel();
								acquisitionItemInfo.setAlarmLevel(alarmLevel);
								acquisitionItemInfo.setAlarmInfo(acquisitionItemInfo.getValue());
								acquisitionItemInfo.setAlarmType(0);
								acquisitionItemInfo.setAlarmDelay(alarmInstanceOwnItem.getItemList().get(l).getDelay());
								acquisitionItemInfo.setIsSendMessage(alarmInstanceOwnItem.getItemList().get(l).getIsSendMessage());
								acquisitionItemInfo.setIsSendMail(alarmInstanceOwnItem.getItemList().get(l).getIsSendMail());
							}
						}
					}else if(alarmType==1  && StringManagerUtils.isNotNull(acquisitionItemInfo.getRawValue()) ){//枚举量报警
						if(StringManagerUtils.stringToInteger(acquisitionItemInfo.getRawValue())==StringManagerUtils.stringToInteger(alarmInstanceOwnItem.getItemList().get(l).getValue()+"")){
							alarmLevel=alarmInstanceOwnItem.getItemList().get(l).getAlarmLevel();
							acquisitionItemInfo.setAlarmLevel(alarmLevel);
							acquisitionItemInfo.setAlarmInfo(acquisitionItemInfo.getValue());
							acquisitionItemInfo.setAlarmType(1);
							acquisitionItemInfo.setAlarmDelay(alarmInstanceOwnItem.getItemList().get(l).getDelay());
							acquisitionItemInfo.setIsSendMessage(alarmInstanceOwnItem.getItemList().get(l).getIsSendMessage());
							acquisitionItemInfo.setIsSendMail(alarmInstanceOwnItem.getItemList().get(l).getIsSendMail());
						}
					}
				}
			}
			acquisitionItemInfoList.add(acquisitionItemInfo);
		}
		return acquisitionItemInfoList;
	}
	
	public List<AcquisitionItemInfo> CalculateDataAlarmProcessing(List<ProtocolItemResolutionData> calItemResolutionDataList,AlarmInstanceOwnItem alarmInstanceOwnItem,
			List<AcquisitionItemInfo> acquisitionItemInfoList,RPCCalculateResponseData rpcCalculateResponseData){
		WorkType workType=null;
		if(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1){
			workType=MemoryDataManagerTask.getWorkTypeByCode(rpcCalculateResponseData.getCalculationStatus().getResultCode()+"");
		}
		for(int i=0;i<calItemResolutionDataList.size();i++){

			int alarmLevel=0;
			AcquisitionItemInfo acquisitionItemInfo=new AcquisitionItemInfo();
			acquisitionItemInfo.setAddr(StringManagerUtils.stringToInteger(calItemResolutionDataList.get(i).getAddr()));
			acquisitionItemInfo.setColumn(calItemResolutionDataList.get(i).getColumn());
			acquisitionItemInfo.setTitle(calItemResolutionDataList.get(i).getColumnName());
			acquisitionItemInfo.setRawTitle(calItemResolutionDataList.get(i).getRawColumnName());
			acquisitionItemInfo.setValue(calItemResolutionDataList.get(i).getValue());
			acquisitionItemInfo.setRawValue(calItemResolutionDataList.get(i).getRawValue());
			acquisitionItemInfo.setDataType(calItemResolutionDataList.get(i).getColumnDataType());
			acquisitionItemInfo.setResolutionMode(calItemResolutionDataList.get(i).getResolutionMode());
			acquisitionItemInfo.setBitIndex(calItemResolutionDataList.get(i).getBitIndex());
			
			
			if("resultCode".equalsIgnoreCase(calItemResolutionDataList.get(i).getColumn())||"resultName".equalsIgnoreCase(calItemResolutionDataList.get(i).getColumn())){
				if(workType!=null){
					acquisitionItemInfo.setValue(workType.getResultName());
				}
			}else if("optimizationSuggestion".equalsIgnoreCase(calItemResolutionDataList.get(i).getColumn())){
				if(workType!=null){
					acquisitionItemInfo.setValue(workType.getOptimizationSuggestion());
					acquisitionItemInfo.setRawValue(workType.getOptimizationSuggestion());
				}
			}
			
			for(int k=0;alarmInstanceOwnItem!=null&&k<alarmInstanceOwnItem.getItemList().size();k++){
				if(("resultCode".equalsIgnoreCase(calItemResolutionDataList.get(i).getColumn())||"resultName".equalsIgnoreCase(calItemResolutionDataList.get(i).getColumn()))
						&&alarmInstanceOwnItem.getItemList().get(k).getType()==4
						&&workType!=null&&workType.getResultCode()==StringManagerUtils.stringToInteger(alarmInstanceOwnItem.getItemList().get(k).getItemCode())){
					alarmLevel=alarmInstanceOwnItem.getItemList().get(k).getAlarmLevel();
					if(alarmLevel>0){
						acquisitionItemInfo.setAlarmInfo("工况报警:"+workType.getResultName());
						acquisitionItemInfo.setAlarmType(4);
						acquisitionItemInfo.setAlarmDelay(alarmInstanceOwnItem.getItemList().get(k).getDelay());
						acquisitionItemInfo.setIsSendMessage(alarmInstanceOwnItem.getItemList().get(k).getIsSendMessage());
						acquisitionItemInfo.setIsSendMail(alarmInstanceOwnItem.getItemList().get(k).getIsSendMail());
					}
					break;
				}else if(("runStatus".equalsIgnoreCase(calItemResolutionDataList.get(i).getColumn())||"runStatusName".equalsIgnoreCase(calItemResolutionDataList.get(i).getColumn()))
						&& alarmInstanceOwnItem.getItemList().get(k).getType()==6
						&& calItemResolutionDataList.get(i).getValue().equalsIgnoreCase(alarmInstanceOwnItem.getItemList().get(k).getItemName()) ){
					alarmLevel=alarmInstanceOwnItem.getItemList().get(k).getAlarmLevel();
					if(alarmLevel>0){
						acquisitionItemInfo.setAlarmLevel(alarmLevel);
						acquisitionItemInfo.setAlarmInfo(calItemResolutionDataList.get(i).getValue());
						acquisitionItemInfo.setAlarmType(alarmInstanceOwnItem.getItemList().get(k).getType());
						acquisitionItemInfo.setAlarmDelay(alarmInstanceOwnItem.getItemList().get(k).getDelay());
						acquisitionItemInfo.setIsSendMessage(alarmInstanceOwnItem.getItemList().get(k).getIsSendMessage());
						acquisitionItemInfo.setIsSendMail(alarmInstanceOwnItem.getItemList().get(k).getIsSendMail());
					}
					break;
				}else if(alarmInstanceOwnItem.getItemList().get(k).getType()==5&&calItemResolutionDataList.get(i).getColumn().equalsIgnoreCase(alarmInstanceOwnItem.getItemList().get(k).getItemCode())){
					float hystersis=alarmInstanceOwnItem.getItemList().get(k).getHystersis();
					if(StringManagerUtils.isNotNull(alarmInstanceOwnItem.getItemList().get(k).getUpperLimit()+"") && StringManagerUtils.stringToFloat(acquisitionItemInfo.getRawValue())>alarmInstanceOwnItem.getItemList().get(k).getUpperLimit()+hystersis){
						alarmLevel=alarmInstanceOwnItem.getItemList().get(k).getAlarmLevel();
						if(alarmLevel>0){
							acquisitionItemInfo.setAlarmLevel(alarmLevel);
							acquisitionItemInfo.setHystersis(hystersis);
							acquisitionItemInfo.setAlarmLimit(alarmInstanceOwnItem.getItemList().get(k).getUpperLimit());
							acquisitionItemInfo.setAlarmInfo("高报");
							acquisitionItemInfo.setAlarmType(5);
							acquisitionItemInfo.setAlarmDelay(alarmInstanceOwnItem.getItemList().get(k).getDelay());
							acquisitionItemInfo.setIsSendMessage(alarmInstanceOwnItem.getItemList().get(k).getIsSendMessage());
							acquisitionItemInfo.setIsSendMail(alarmInstanceOwnItem.getItemList().get(k).getIsSendMail());
						}
					}else if((StringManagerUtils.isNotNull(alarmInstanceOwnItem.getItemList().get(k).getLowerLimit()+"") && StringManagerUtils.stringToFloat(acquisitionItemInfo.getRawValue())<alarmInstanceOwnItem.getItemList().get(k).getLowerLimit()-hystersis)){
						alarmLevel=alarmInstanceOwnItem.getItemList().get(k).getAlarmSign()>0?alarmInstanceOwnItem.getItemList().get(k).getAlarmLevel():0;
						if(alarmLevel>0){
							acquisitionItemInfo.setAlarmLevel(alarmLevel);
							acquisitionItemInfo.setHystersis(hystersis);
							acquisitionItemInfo.setAlarmLimit(alarmInstanceOwnItem.getItemList().get(k).getLowerLimit());
							acquisitionItemInfo.setAlarmInfo("低报");
							acquisitionItemInfo.setAlarmType(5);
							acquisitionItemInfo.setAlarmDelay(alarmInstanceOwnItem.getItemList().get(k).getDelay());
							acquisitionItemInfo.setIsSendMessage(alarmInstanceOwnItem.getItemList().get(k).getIsSendMessage());
							acquisitionItemInfo.setIsSendMail(alarmInstanceOwnItem.getItemList().get(k).getIsSendMail());
						}
					}
					break;
				}
			}
			acquisitionItemInfo.setAlarmLevel(alarmLevel);
			acquisitionItemInfo.setUnit(calItemResolutionDataList.get(i).getUnit());
			acquisitionItemInfo.setSort(calItemResolutionDataList.get(i).getSort());
			acquisitionItemInfoList.add(acquisitionItemInfo);
		}
		return acquisitionItemInfoList;
	}
	
	public List<AcquisitionItemInfo> InputDataAlarmProcessing(List<ProtocolItemResolutionData> inputItemItemResolutionDataList,AlarmInstanceOwnItem alarmInstanceOwnItem,List<AcquisitionItemInfo> acquisitionItemInfoList){
		for(int i=0;i<inputItemItemResolutionDataList.size();i++){
			int alarmLevel=0;
			AcquisitionItemInfo acquisitionItemInfo=new AcquisitionItemInfo();
			acquisitionItemInfo.setAddr(StringManagerUtils.stringToInteger(inputItemItemResolutionDataList.get(i).getAddr()));
			acquisitionItemInfo.setColumn(inputItemItemResolutionDataList.get(i).getColumn());
			acquisitionItemInfo.setTitle(inputItemItemResolutionDataList.get(i).getColumnName());
			acquisitionItemInfo.setRawTitle(inputItemItemResolutionDataList.get(i).getRawColumnName());
			acquisitionItemInfo.setValue(inputItemItemResolutionDataList.get(i).getValue());
			acquisitionItemInfo.setRawValue(inputItemItemResolutionDataList.get(i).getRawValue());
			acquisitionItemInfo.setDataType(inputItemItemResolutionDataList.get(i).getColumnDataType());
			acquisitionItemInfo.setResolutionMode(inputItemItemResolutionDataList.get(i).getResolutionMode());
			acquisitionItemInfo.setBitIndex(inputItemItemResolutionDataList.get(i).getBitIndex());
			
			acquisitionItemInfo.setAlarmLevel(alarmLevel);
			acquisitionItemInfo.setUnit(inputItemItemResolutionDataList.get(i).getUnit());
			acquisitionItemInfo.setSort(inputItemItemResolutionDataList.get(i).getSort());
			acquisitionItemInfoList.add(acquisitionItemInfo);
		}
		return acquisitionItemInfoList;
	}
	
	public Map<String,String> DataProcessing(AcqGroup acqGroup,ModbusProtocolConfig.Protocol protocol){
		Map<String,String> map=new HashMap<>();
		
		List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		if(!dataModelMap.containsKey("ProtocolMappingColumnByTitle")){
			MemoryDataManagerTask.loadProtocolMappingColumnByTitle();
		}
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
		
		if(acqGroup!=null && protocol!=null){
			for(int i=0;acqGroup.getAddr()!=null &&i<acqGroup.getAddr().size();i++){
				for(int j=0;j<protocol.getItems().size();j++){
					if(acqGroup.getAddr().get(i)==protocol.getItems().get(j).getAddr()){
						String value="";
						DataMapping dataMappingColumn=loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(j).getTitle());
						String columnName=dataMappingColumn.getMappingColumn();
						
						if(acqGroup.getValue()!=null&&acqGroup.getValue().size()>i&&acqGroup.getValue().get(i)!=null ){
							value=StringManagerUtils.objectListToString(acqGroup.getValue().get(i), protocol.getItems().get(j));
						}
						String rawValue=value;
						String addr=protocol.getItems().get(j).getAddr()+"";
						String title=protocol.getItems().get(j).getTitle();
						String rawTitle=title;
						String columnDataType=protocol.getItems().get(j).getIFDataType();
						String resolutionMode=protocol.getItems().get(j).getResolutionMode()+"";
						String bitIndex="";
						String unit=protocol.getItems().get(j).getUnit();
						int alarmLevel=0;
						int sort=9999;
						
						String saveValue=rawValue;
						if(protocol.getItems().get(j).getQuantity()==1&&rawValue.length()>50){
							saveValue=rawValue.substring(0, 50);
						}
						map.put(columnName, saveValue);
					}
				}
			}
		}
		return map;
	} 
	
	public void saveDailyTotalData(DeviceInfo deviceInfo){
		if(deviceInfo.getDailyTotalItemMap().size()>0){
			Iterator<Map.Entry<String, DeviceInfo.DailyTotalItem>> iterator = deviceInfo.getDailyTotalItemMap().entrySet().iterator();
			while (iterator.hasNext()) {
			    Map.Entry<String,  DeviceInfo.DailyTotalItem> entry = iterator.next();
			    String key = entry.getKey();
			    DeviceInfo.DailyTotalItem dailyTotalItem = entry.getValue();
			    try {
					commonDataService.getBaseDao().saveDailyTotalData(deviceInfo.getId(), dailyTotalItem);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void cleanDailyTotalItems(DeviceInfo deviceInfo,AcqInstanceOwnItem acqInstanceOwnItem){
		List<String> dailyTotalItemList=new ArrayList<>();
		for(AcqInstanceOwnItem.AcqItem acqItem:acqInstanceOwnItem.getItemList()){
			if(acqItem.getDailyTotalCalculate()==1){
				dailyTotalItemList.add((acqItem.getItemCode()+"_total").toUpperCase());
			}
		}
		Iterator<Map.Entry<String, DailyTotalItem>> iterator = deviceInfo.getDailyTotalItemMap().entrySet().iterator();
		while (iterator.hasNext()) {
		    Map.Entry<String,  DeviceInfo.DailyTotalItem> entry = iterator.next();
		    String key = entry.getKey();
		    DeviceInfo.DailyTotalItem value = entry.getValue();
		    if(!StringManagerUtils.existOrNot(dailyTotalItemList, key, false)){
		    	deviceInfo.getDailyTotalItemMap().remove(key);
		    }
		}
	}
	
	public List<ProtocolItemResolutionData> DataProcessing(DeviceInfo deviceInfo,String acqTime,AcqGroup acqGroup,ModbusProtocolConfig.Protocol protocol,
			AcqInstanceOwnItem acqInstanceOwnItem,
			List<ProtocolItemResolutionData> calItemResolutionDataList,
			List<KeyValue> acqDataList){
		List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		if(!dataModelMap.containsKey("ProtocolMappingColumnByTitle")){
			MemoryDataManagerTask.loadProtocolMappingColumnByTitle();
		}
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
		
		if(acqGroup!=null && protocol!=null && acqInstanceOwnItem!=null){
			for(int i=0;acqGroup.getAddr()!=null &&i<acqGroup.getAddr().size();i++){
				for(int j=0;j<protocol.getItems().size();j++){
					if(acqGroup.getAddr().get(i)==protocol.getItems().get(j).getAddr()){
						String value="";
						String title=protocol.getItems().get(j).getTitle();
						DataMapping dataMappingColumn=loadProtocolMappingColumnByTitleMap.get(title);
						String columnName=dataMappingColumn.getMappingColumn();
						
						if(acqGroup.getValue()!=null&&acqGroup.getValue().size()>i&&acqGroup.getValue().get(i)!=null &&acqGroup.getValue().get(i).size()>0){
							value=StringManagerUtils.objectListToString(acqGroup.getValue().get(i), protocol.getItems().get(j));
						}
						String rawValue=value;
						String addr=protocol.getItems().get(j).getAddr()+"";
						
						String rawTitle=title;
						String columnDataType=protocol.getItems().get(j).getIFDataType();
						String resolutionMode=protocol.getItems().get(j).getResolutionMode()+"";
						String bitIndex="";
						String unit=protocol.getItems().get(j).getUnit();
						int sort=9999;
						
						KeyValue keyValue=new KeyValue(columnName,rawValue);
						acqDataList.add(keyValue);
						
						if(StringManagerUtils.existAcqItem(acqInstanceOwnItem.getItemList(), title, false)){
							AcqInstanceOwnItem.AcqItem thisAcqItem=null;
							for(AcqInstanceOwnItem.AcqItem acqItem:acqInstanceOwnItem.getItemList()){
								if(title.equalsIgnoreCase(acqItem.getItemName())){
									thisAcqItem=acqItem;
									break;
								}
							}
							
							if(thisAcqItem!=null && thisAcqItem.getDailyTotalCalculate()==1){
								String code=(columnName+"_total").toUpperCase();
								DeviceInfo.DailyTotalItem thisDailyTotalItem=deviceInfo.getDailyTotalItemMap().get(code);
								EnergyCalculateResponseData totalCalculateResponseData=energyCalaulate(deviceInfo,thisDailyTotalItem,acqTime,StringManagerUtils.stringToFloat(rawValue));
								if(totalCalculateResponseData!=null && totalCalculateResponseData.getResultStatus()==1){
									calItemResolutionDataList.add(new ProtocolItemResolutionData(thisAcqItem.getDailyTotalCalculateName(),thisAcqItem.getDailyTotalCalculateName(),
											totalCalculateResponseData.getCurrent().getToday().getKWattH()+"",
											totalCalculateResponseData.getCurrent().getToday().getKWattH()+"","",
											code,"","","",
											unit,1,1)
											);
									if(thisDailyTotalItem==null){
										thisDailyTotalItem=new DeviceInfo.DailyTotalItem();
									}
									thisDailyTotalItem.setAcqTime(acqTime);
									thisDailyTotalItem.setItemColumn(code);
									thisDailyTotalItem.setItemName(thisAcqItem.getDailyTotalCalculateName());
									thisDailyTotalItem.setTotalValue(StringManagerUtils.stringToFloat(rawValue));
									thisDailyTotalItem.setTodayValue(totalCalculateResponseData.getCurrent().getToday().getKWattH());
									
									deviceInfo.getDailyTotalItemMap().put(thisDailyTotalItem.getItemColumn(), thisDailyTotalItem);
								}
							}
							if(protocol.getItems().get(j).getResolutionMode()==1||protocol.getItems().get(j).getResolutionMode()==2){//如果是枚举量或数据量
								if(protocol.getItems().get(j).getMeaning()!=null&&protocol.getItems().get(j).getMeaning().size()>0){
									for(int l=0;l<protocol.getItems().get(j).getMeaning().size();l++){
										if(StringManagerUtils.isNotNull(value)&&StringManagerUtils.stringToFloat(value)==(protocol.getItems().get(j).getMeaning().get(l).getValue())){
											value=protocol.getItems().get(j).getMeaning().get(l).getMeaning();
											break;
										}
									}
								}
								ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort,0);
								protocolItemResolutionDataList.add(protocolItemResolutionData);
							}else if(protocol.getItems().get(j).getResolutionMode()==0){//如果是开关量
								boolean isMatch=false;
								if(protocol.getItems().get(j).getMeaning()!=null&&protocol.getItems().get(j).getMeaning().size()>0){
									int maxIndex=0;
									for(int l=0;l<protocol.getItems().get(j).getMeaning().size();l++){
										if(protocol.getItems().get(j).getMeaning().get(l).getValue()>maxIndex){
											maxIndex=protocol.getItems().get(j).getMeaning().get(l).getValue();
										}
									}
									String[] valueArr=new String[maxIndex+1];
									if(StringManagerUtils.isNotNull(value)){
										valueArr=value.split(",");
									}
									for(int l=0;l<protocol.getItems().get(j).getMeaning().size();l++){
										title=protocol.getItems().get(j).getMeaning().get(l).getMeaning();
										isMatch=false;
										for(int n=0;n<acqInstanceOwnItem.getItemList().size();n++){
											if(acqInstanceOwnItem.getItemList().get(n).getItemName().equalsIgnoreCase(protocol.getItems().get(j).getTitle()) 
													&&acqInstanceOwnItem.getItemList().get(n).getBitIndex()==protocol.getItems().get(j).getMeaning().get(l).getValue()
													){
												isMatch=true;
												break;
											}
										}
										if(!isMatch){
											continue;
										}
										if(StringManagerUtils.isNotNull(value) || true){
											boolean match=false;
											for(int m=0;valueArr!=null&&m<valueArr.length;m++){
												if(m==protocol.getItems().get(j).getMeaning().get(l).getValue()){
													bitIndex=protocol.getItems().get(j).getMeaning().get(l).getValue()+"";
													if(("bool".equalsIgnoreCase(columnDataType) || "boolean".equalsIgnoreCase(columnDataType)) && StringManagerUtils.isNotNull(valueArr[m])){
														value=("true".equalsIgnoreCase(valueArr[m]) || "1".equalsIgnoreCase(valueArr[m]))?"开":"关";
														rawValue=("true".equalsIgnoreCase(valueArr[m]) || "1".equalsIgnoreCase(valueArr[m]))?"1":"0";
													}else{
														value="";
														rawValue="";
													}
													ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort,0);
													protocolItemResolutionDataList.add(protocolItemResolutionData);
													match=true;
													break;
												}
											}
											if(!match){
												value="";
												rawValue="";
												bitIndex=protocol.getItems().get(j).getMeaning().get(l).getValue()+"";
												ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort,0);
												protocolItemResolutionDataList.add(protocolItemResolutionData);
											}
										}
									}
								}else{
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort,0);
									protocolItemResolutionDataList.add(protocolItemResolutionData);
								}
							}else{
								ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort,0);
								protocolItemResolutionDataList.add(protocolItemResolutionData);
							}
						}
						break;
					}
				}
			}
		}
		
		return protocolItemResolutionDataList;
	}
	
	public int RunStatusProcessing(String rawValue,String code){
		int runStatus=2;
		Jedis jedis=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			ProtocolRunStatusConfig protocolRunStatusConfig=MemoryDataManagerTask.getProtocolRunStatusConfig(code);
			if(protocolRunStatusConfig!=null && StringManagerUtils.isNotNull(rawValue) && StringManagerUtils.isNumber(rawValue)){
				if(protocolRunStatusConfig.getResolutionMode()==1){
					int rawRunStatus=StringManagerUtils.stringToInteger(rawValue);
					if(StringManagerUtils.existOrNot(protocolRunStatusConfig.getRunValue(), rawRunStatus)){
						runStatus=1;
					}else if(StringManagerUtils.existOrNot(protocolRunStatusConfig.getStopValue(), rawRunStatus)){
						runStatus=0;
					}else{
					}
				}else if(protocolRunStatusConfig.getResolutionMode()==2){
					if(protocolRunStatusConfig.getRunConditionList()!=null && protocolRunStatusConfig.getRunConditionList().size()>0){
						float rawRunStatus=StringManagerUtils.stringToFloat(rawValue);
						boolean runConditionMatch=false;
						boolean stopConditionMatch=false;
						for(int k=0;k<protocolRunStatusConfig.getRunConditionList().size();k++){
							if(protocolRunStatusConfig.getRunConditionList().get(k).getLogic()==1){//大于
								if(rawRunStatus>protocolRunStatusConfig.getRunConditionList().get(k).getValue()){
									runConditionMatch=true;
								}else{
									runConditionMatch=false;
									break;
								}
							}else if(protocolRunStatusConfig.getRunConditionList().get(k).getLogic()==2){//大于等于
								if(rawRunStatus>=protocolRunStatusConfig.getRunConditionList().get(k).getValue()){
									runConditionMatch=true;
								}else{
									runConditionMatch=false;
									break;
								}
							}else if(protocolRunStatusConfig.getRunConditionList().get(k).getLogic()==3){//大于等于
								if(rawRunStatus<=protocolRunStatusConfig.getRunConditionList().get(k).getValue()){
									runConditionMatch=true;
								}else{
									runConditionMatch=false;
									break;
								}
							}else if(protocolRunStatusConfig.getRunConditionList().get(k).getLogic()==4){//小于
								if(rawRunStatus<protocolRunStatusConfig.getRunConditionList().get(k).getValue()){
									runConditionMatch=true;
								}else{
									runConditionMatch=false;
									break;
								}
							}
						}
						
						for(int k=0;k<protocolRunStatusConfig.getStopConditionList().size();k++){
							if(protocolRunStatusConfig.getStopConditionList().get(k).getLogic()==1){//大于
								if(rawRunStatus>protocolRunStatusConfig.getStopConditionList().get(k).getValue()){
									stopConditionMatch=true;
								}else{
									stopConditionMatch=false;
									break;
								}
							}else if(protocolRunStatusConfig.getStopConditionList().get(k).getLogic()==2){//大于等于
								if(rawRunStatus>=protocolRunStatusConfig.getStopConditionList().get(k).getValue()){
									stopConditionMatch=true;
								}else{
									stopConditionMatch=false;
									break;
								}
							}else if(protocolRunStatusConfig.getStopConditionList().get(k).getLogic()==3){//大于等于
								if(rawRunStatus<=protocolRunStatusConfig.getStopConditionList().get(k).getValue()){
									stopConditionMatch=true;
								}else{
									stopConditionMatch=false;
									break;
								}
							}else if(protocolRunStatusConfig.getStopConditionList().get(k).getLogic()==4){//小于
								if(rawRunStatus<protocolRunStatusConfig.getStopConditionList().get(k).getValue()){
									stopConditionMatch=true;
								}else{
									stopConditionMatch=false;
									break;
								}
							}
						}
						
						if(runConditionMatch){
							runStatus=1;
						}else if(stopConditionMatch){
							runStatus=0;
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return runStatus;
	}
	
	public CommResponseData commEffCalaulate(DeviceInfo deviceInfo,String acqTime,int commStatus){
		CommResponseData commResponseData=null;
		String commRequest="{"
				+ "\"AKString\":\"\","
				+ "\"WellName\":\""+deviceInfo.getWellName()+"\","
				+ "\"OffsetHour\":"+Config.getInstance().configFile.getAp().getReport().getOffsetHour()+",";
		if(StringManagerUtils.isNotNull(deviceInfo.getOnLineAcqTime())&&StringManagerUtils.isNotNull(deviceInfo.getOnLineCommRange())){
			commRequest+= "\"Last\":{"
					+ "\"AcqTime\": \""+deviceInfo.getOnLineAcqTime()+"\","
					+ "\"CommStatus\": "+(deviceInfo.getOnLineCommStatus()>0?true:false)+","
					+ "\"CommEfficiency\": {"
					+ "\"Efficiency\": "+deviceInfo.getOnLineCommEff()+","
					+ "\"Time\": "+deviceInfo.getOnLineCommTime()+","
					+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(deviceInfo.getOnLineCommRange())+""
					+ "}"
					+ "},";
		}	
		commRequest+= "\"Current\": {"
				+ "\"AcqTime\":\""+acqTime+"\","
				+ "\"CommStatus\":"+(commStatus==1?true:false)+""
				+ "}"
				+ "}";
		commResponseData=CalculateUtils.commCalculate(commRequest);
	
		return commResponseData;
	}
	
	public TimeEffResponseData tiemEffCalaulate(DeviceInfo deviceInfo,String acqTime,int runStatus){
		TimeEffResponseData timeEffResponseData=null;
		String tiemEffRequest="{"
				+ "\"AKString\":\"\","
				+ "\"WellName\":\""+deviceInfo.getWellName()+"\","
				+ "\"OffsetHour\":"+Config.getInstance().configFile.getAp().getReport().getOffsetHour()+",";
		if(StringManagerUtils.isNotNull(deviceInfo.getRunStatusAcqTime())&&StringManagerUtils.isNotNull(deviceInfo.getRunRange())){
			tiemEffRequest+= "\"Last\":{"
					+ "\"AcqTime\": \""+deviceInfo.getRunStatusAcqTime()+"\","
					+ "\"RunStatus\": "+(deviceInfo.getRunStatus()==1?true:false)+","
					+ "\"RunEfficiency\": {"
					+ "\"Efficiency\": "+deviceInfo.getRunEff()+","
					+ "\"Time\": "+deviceInfo.getRunTime()+","
					+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(deviceInfo.getRunRange())+""
					+ "}"
					+ "},";
		}	
		tiemEffRequest+= "\"Current\": {"
				+ "\"AcqTime\":\""+acqTime+"\","
				+ "\"RunStatus\":"+(runStatus==1?true:false)+""
				+ "}"
				+ "}";
		timeEffResponseData=CalculateUtils.runCalculate(tiemEffRequest);
	
		return timeEffResponseData;
	}
	
	public EnergyCalculateResponseData energyCalaulate(DeviceInfo deviceInfo,DeviceInfo.DailyTotalItem thisDailyTotalItem,String acqTime,float totalKWattH){
		EnergyCalculateResponseData energyCalculateResponseData=null;

		String energyRequest="{"
				+ "\"AKString\":\"\","
				+ "\"WellName\":\""+deviceInfo.getWellName()+"\","
				+ "\"OffsetHour\":"+Config.getInstance().configFile.getAp().getReport().getOffsetHour()+",";
		if(thisDailyTotalItem!=null){
			energyRequest+= "\"Last\":{"
					+ "\"AcqTime\": \""+thisDailyTotalItem.getAcqTime()+"\","
					+ "\"Total\":{"
					+ "\"KWattH\":"+thisDailyTotalItem.getTotalValue()
					+ "},\"Today\":{"
					+ "\"KWattH\":"+thisDailyTotalItem.getTodayValue()
					+ "}"
					+ "},";
		}	
		energyRequest+= "\"Current\": {"
				+ "\"AcqTime\":\""+acqTime+"\","
				+ "\"Total\":{"
				+ "\"KWattH\":"+totalKWattH
				+ "}"
				+ "}"
				+ "}";
		energyCalculateResponseData=CalculateUtils.energyCalculate(energyRequest);
	
		return energyCalculateResponseData;
	}
	
	public String getWebSocketSendData(DeviceInfo deviceInfo,String acqTime,UserInfo userInfo,List<AcquisitionItemInfo> acquisitionItemInfoList,
			DisplayInstanceOwnItem displayInstanceOwnItem,int items,
			String functionCode,int commAlarmLevel,int runAlarmLevel,
			RPCCalculateResponseData rpcCalculateResponseData,RPCCalculateRequestData rpcCalculateRequestData,int resultAlarmLevel,
			AlarmShowStyle alarmShowStyle){
		StringBuffer webSocketSendData = new StringBuffer();
		StringBuffer displayItemInfo_json = new StringBuffer();
		StringBuffer allItemInfo_json = new StringBuffer();
		
		StringBuffer wellBoreChartsData = new StringBuffer();
		StringBuffer surfaceChartsData = new StringBuffer();
		
		WorkType workType=null;
		if(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1){
			workType=MemoryDataManagerTask.getWorkTypeByCode(rpcCalculateResponseData.getCalculationStatus().getResultCode()+"");
		}
		
		String productionUnit=Config.getInstance().configFile.getAp().getOthers().getProductionUnit();
		//井筒分析图形数据
		StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
		String rodStressRatio1="0",rodStressRatio2="0",rodStressRatio3="0",rodStressRatio4="0";
		if(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232){
			int curvecount=rpcCalculateResponseData.getFESDiagram().getS().get(0).size();
			int pointcount=rpcCalculateResponseData.getFESDiagram().getS().size();
			if(pointcount<rpcCalculateResponseData.getFESDiagram().getF().size()){
				pointcount=rpcCalculateResponseData.getFESDiagram().getF().size();
			}
			for(int i=0;i<curvecount;i++){
				for(int j=0;j<pointcount;j++){
					pumpFSDiagramStrBuff.append(rpcCalculateResponseData.getFESDiagram().getS().get(j).get(i)+",");//位移
					pumpFSDiagramStrBuff.append(rpcCalculateResponseData.getFESDiagram().getF().get(j).get(i)+",");//载荷
				}
				if(pumpFSDiagramStrBuff.toString().endsWith(",")){
					pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
				}
				pumpFSDiagramStrBuff.append("#");
			}
			if(pumpFSDiagramStrBuff.toString().endsWith("#")){
				pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
			}
		}
		
		if(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232){
			for(int i=0;i<rpcCalculateResponseData.getRodString().getEveryRod().size();i++){
				if(i==0){
	        		rodStressRatio1=rpcCalculateResponseData.getRodString().getEveryRod().get(i).getStressRatio()+"";
	        	}else if(i==1){
	        		rodStressRatio2=rpcCalculateResponseData.getRodString().getEveryRod().get(i).getStressRatio()+"";
	        	}if(i==2){
	        		rodStressRatio3=rpcCalculateResponseData.getRodString().getEveryRod().get(i).getStressRatio()+"";
	        	}if(i==3){
	        		rodStressRatio4=rpcCalculateResponseData.getRodString().getEveryRod().get(i).getStressRatio()+"";
	        	}
			}
		}
		wellBoreChartsData.append("{");
		surfaceChartsData.append("{");
		if(deviceInfo.getCalculateType()==1){
			wellBoreChartsData.append("\"success\":true,");
			wellBoreChartsData.append("\"deviceName\":\""+deviceInfo.getWellName()+"\",");
			wellBoreChartsData.append("\"acqTime\":\""+(rpcCalculateRequestData!=null&&rpcCalculateRequestData.getFESDiagram()!=null?rpcCalculateRequestData.getFESDiagram().getAcqTime():"")+"\",");
			
			wellBoreChartsData.append("\"pointCount\":\""+(rpcCalculateRequestData!=null&&rpcCalculateRequestData.getFESDiagram()!=null?rpcCalculateRequestData.getFESDiagram().getS().size():"")+"\",");
			wellBoreChartsData.append("\"upperLoadLine\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1?rpcCalculateResponseData.getFESDiagram().getUpperLoadLine():"")+"\",");
			wellBoreChartsData.append("\"lowerLoadLine\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1?rpcCalculateResponseData.getFESDiagram().getLowerLoadLine():"")+"\",");
			
			String fmax="",fmin="";
			if(rpcCalculateResponseData!=null&&(rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
				if(rpcCalculateResponseData.getFESDiagram()!=null&&rpcCalculateResponseData.getFESDiagram().getFMax()!=null&&rpcCalculateResponseData.getFESDiagram().getFMax().size()>0){
					fmax=rpcCalculateResponseData.getFESDiagram().getFMax().get(0)+"";
				}
				if(rpcCalculateResponseData.getFESDiagram()!=null&&rpcCalculateResponseData.getFESDiagram().getFMin()!=null&&rpcCalculateResponseData.getFESDiagram().getFMin().size()>0){
					fmin=rpcCalculateResponseData.getFESDiagram().getFMin().get(0)+"";
				}
			}
			
			wellBoreChartsData.append("\"fmax\":\""+fmax+"\",");
			wellBoreChartsData.append("\"fmin\":\""+fmin+"\",");
			
			wellBoreChartsData.append("\"stroke\":\""+(rpcCalculateRequestData!=null&&rpcCalculateRequestData.getFESDiagram()!=null?rpcCalculateRequestData.getFESDiagram().getStroke():"")+"\",");
			wellBoreChartsData.append("\"spm\":\""+(rpcCalculateRequestData!=null&&rpcCalculateRequestData.getFESDiagram()!=null?rpcCalculateRequestData.getFESDiagram().getSPM():"")+"\",");
			
			wellBoreChartsData.append("\"liquidProduction\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?(productionUnit.equalsIgnoreCase("ton")?rpcCalculateResponseData.getProduction().getLiquidWeightProduction():rpcCalculateResponseData.getProduction().getLiquidVolumetricProduction()):"")+"\",");
			wellBoreChartsData.append("\"resultName\":\""+(workType!=null?workType.getResultName():"")+"\",");
			wellBoreChartsData.append("\"optimizationSuggestion\":\""+(workType!=null?workType.getOptimizationSuggestion():"")+"\",");
			wellBoreChartsData.append("\"resultCode\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1?rpcCalculateResponseData.getCalculationStatus().getResultCode():"")+"\",");
			
			
			wellBoreChartsData.append("\"rodStressRatio1\":\""+rodStressRatio1+"\",");
			wellBoreChartsData.append("\"rodStressRatio2\":\""+rodStressRatio2+"\",");
			wellBoreChartsData.append("\"rodStressRatio3\":\""+rodStressRatio3+"\",");
			wellBoreChartsData.append("\"rodStressRatio4\":\""+rodStressRatio4+"\",");
			
			wellBoreChartsData.append("\"pumpEff1\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getPumpEfficiency().getPumpEff1():"")+"\",");
			wellBoreChartsData.append("\"pumpEff2\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getPumpEfficiency().getPumpEff2():"")+"\",");
			wellBoreChartsData.append("\"pumpEff3\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getPumpEfficiency().getPumpEff3():"")+"\",");
			wellBoreChartsData.append("\"pumpEff4\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getPumpEfficiency().getPumpEff4():"")+"\",");
			wellBoreChartsData.append("\"pumpEff\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getPumpEfficiency().getPumpEff():"")+"\",");
			
			wellBoreChartsData.append("\"pumpFSDiagramData\":\""+pumpFSDiagramStrBuff.toString()+"\",");
			wellBoreChartsData.append("\"positionCurveData\":\""+((rpcCalculateRequestData!=null && rpcCalculateRequestData.getFESDiagram()!=null && rpcCalculateRequestData.getFESDiagram().getS()!=null)?(StringUtils.join(rpcCalculateRequestData.getFESDiagram().getS(), ",")):"")+"\",");
			wellBoreChartsData.append("\"loadCurveData\":\""+((rpcCalculateRequestData!=null && rpcCalculateRequestData.getFESDiagram()!=null && rpcCalculateRequestData.getFESDiagram().getF()!=null)?(StringUtils.join(rpcCalculateRequestData.getFESDiagram().getF(), ",")):"")+"\"");
			
			
			
			//地面分析图形数据
			surfaceChartsData.append("\"success\":true,");
			surfaceChartsData.append("\"deviceName\":\""+deviceInfo.getWellName()+"\",");
			surfaceChartsData.append("\"acqTime\":\""+((rpcCalculateRequestData!=null && rpcCalculateRequestData.getFESDiagram()!=null)?rpcCalculateRequestData.getFESDiagram().getAcqTime():"")+"\",");
			
			surfaceChartsData.append("\"upStrokeWattMax\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getUpStrokeWattMax():"")+"\",");
			surfaceChartsData.append("\"downStrokeWattMax\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getDownStrokeWattMax():"")+"\",");
			surfaceChartsData.append("\"wattDegreeBalance\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getWattDegreeBalance():"")+"\",");
			surfaceChartsData.append("\"upStrokeIMax\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getUpStrokeIMax():"")+"\",");
			surfaceChartsData.append("\"downStrokeIMax\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getDownStrokeIMax():"")+"\",");
			surfaceChartsData.append("\"iDegreeBalance\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getIDegreeBalance():"")+"\",");
			surfaceChartsData.append("\"deltaRadius\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getIDegreeBalance():"")+"\",");
			
			surfaceChartsData.append("\"positionCurveData\":\""+((rpcCalculateRequestData!=null && rpcCalculateRequestData.getFESDiagram()!=null && rpcCalculateRequestData.getFESDiagram().getS()!=null)?(StringUtils.join(rpcCalculateRequestData.getFESDiagram().getS(), ",")):"")+"\",");
			surfaceChartsData.append("\"loadCurveData\":\""+((rpcCalculateRequestData!=null && rpcCalculateRequestData.getFESDiagram()!=null && rpcCalculateRequestData.getFESDiagram().getF()!=null)?(StringUtils.join(rpcCalculateRequestData.getFESDiagram().getF(), ",")):"")+"\",");
			surfaceChartsData.append("\"powerCurveData\":\""+((rpcCalculateRequestData!=null && rpcCalculateRequestData.getFESDiagram()!=null && rpcCalculateRequestData.getFESDiagram().getWatt()!=null)?(StringUtils.join(rpcCalculateRequestData.getFESDiagram().getWatt(), ",")):"")+"\",");
			surfaceChartsData.append("\"currentCurveData\":\""+((rpcCalculateRequestData!=null && rpcCalculateRequestData.getFESDiagram()!=null && rpcCalculateRequestData.getFESDiagram().getI()!=null)?(StringUtils.join(rpcCalculateRequestData.getFESDiagram().getI(), ",")):"")+"\",");
			
			surfaceChartsData.append("\"crankAngle\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getCrankAngle(), ","):"")+"\",");
			surfaceChartsData.append("\"loadRorque\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getLoadTorque(), ","):"")+"\",");
			surfaceChartsData.append("\"crankTorque\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getCrankTorque(), ","):"")+"\",");
			surfaceChartsData.append("\"currentBalanceTorque\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getCurrentBalanceTorque(), ","):"")+"\",");
			surfaceChartsData.append("\"currentNetTorque\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getCurrentNetTorque(), ","):"")+"\",");
			surfaceChartsData.append("\"expectedBalanceTorque\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getExpectedBalanceTorque(), ","):"")+"\",");
			surfaceChartsData.append("\"expectedNetTorque\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getExpectedNetTorque(), ","):"")+"\",");
			surfaceChartsData.append("\"polishrodV\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getV(), ","):"")+"\",");
			surfaceChartsData.append("\"polishrodA\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getA(), ","):"")+"\"");
		}
		wellBoreChartsData.append("}");
		surfaceChartsData.append("}");
		
		String columns = "[";
		for(int i=1;i<=items;i++){
			columns+= "{ \"header\":\"名称\",\"dataIndex\":\"name"+i+"\",children:[] },"
					+ "{ \"header\":\"变量\",\"dataIndex\":\"value"+i+"\",children:[] }";
			if(i<items){
				columns+=",";
			}
		}
		columns+= "]";
		
		webSocketSendData.append("{ \"success\":true,\"functionCode\":\""+functionCode+"\","
				+ "\"deviceId\":"+deviceInfo.getId()+","
				+ "\"deviceName\":\""+deviceInfo.getWellName()+"\","
				+ "\"orgId\":"+deviceInfo.getOrgId()+","
				+ "\"deviceType\":"+deviceInfo.getDeviceType()+","
				+ "\"acqTime\":\""+acqTime+"\",\"columns\":"+columns+",");
		webSocketSendData.append("\"commAlarmLevel\":"+commAlarmLevel+",");
		webSocketSendData.append("\"runAlarmLevel\":"+runAlarmLevel+",");
		webSocketSendData.append("\"resultAlarmLevel\":"+resultAlarmLevel+",");
		webSocketSendData.append("\"totalRoot\":[");
		displayItemInfo_json.append("[");
		allItemInfo_json.append("[");
		webSocketSendData.append("{\"name1\":\""+deviceInfo.getWellName()+":"+acqTime+" 在线\"},");
		
		//筛选
		List<AcquisitionItemInfo> userAcquisitionItemInfoList=new ArrayList<AcquisitionItemInfo>();
		for(int j=0;j<acquisitionItemInfoList.size();j++){
			allItemInfo_json.append("{\"columnName\":\""+acquisitionItemInfoList.get(j).getTitle()+"\",\"column\":\""+acquisitionItemInfoList.get(j).getColumn()+"\",\"value\":\""+acquisitionItemInfoList.get(j).getValue()+"\",\"rawValue\":\""+acquisitionItemInfoList.get(j).getRawValue()+"\",\"columnDataType\":\""+acquisitionItemInfoList.get(j).getDataType()+"\",\"resolutionMode\":\""+acquisitionItemInfoList.get(j).getResolutionMode()+"\",\"alarmLevel\":"+acquisitionItemInfoList.get(j).getAlarmLevel()+"},");
			if(StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), acquisitionItemInfoList.get(j).getColumn(), false,0)){
				for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
					if(acquisitionItemInfoList.get(j).getColumn().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode()) && displayInstanceOwnItem.getItemList().get(k).getType()!=2){
						if(displayInstanceOwnItem.getItemList().get(k).getShowLevel()==0||displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()){
							acquisitionItemInfoList.get(j).setSort(displayInstanceOwnItem.getItemList().get(k).getRealtimeSort());
							userAcquisitionItemInfoList.add(acquisitionItemInfoList.get(j));
						}
						break;
					}
				}
			}
		}
		if(allItemInfo_json.toString().endsWith(",")){
			allItemInfo_json.deleteCharAt(allItemInfo_json.length() - 1);
		}
		allItemInfo_json.append("]");
		
		//排序
		Collections.sort(userAcquisitionItemInfoList);
		//插入排序间隔的空项
		List<AcquisitionItemInfo> finalAcquisitionItemInfoList=new ArrayList<AcquisitionItemInfo>();
		for(int j=0;j<userAcquisitionItemInfoList.size();j++){
			if(j>0&&userAcquisitionItemInfoList.get(j).getSort()<9999
					&&userAcquisitionItemInfoList.get(j).getSort()-userAcquisitionItemInfoList.get(j-1).getSort()>1
				){
					int def=userAcquisitionItemInfoList.get(j).getSort()-userAcquisitionItemInfoList.get(j-1).getSort();
					for(int k=1;k<def;k++){
						AcquisitionItemInfo acquisitionItemInfo=new AcquisitionItemInfo();
						finalAcquisitionItemInfoList.add(acquisitionItemInfo);
					}
				}
				finalAcquisitionItemInfoList.add(userAcquisitionItemInfoList.get(j));
		}
		
		int row=1;
		if(finalAcquisitionItemInfoList.size()%items==0){
			row=finalAcquisitionItemInfoList.size()/items+1;
		}else{
			row=finalAcquisitionItemInfoList.size()/items+2;
		}
		
		for(int j=1;j<row;j++){
			webSocketSendData.append("{");
			for(int k=0;k<items;k++){
				int index=items*(j-1)+k;
				String columnName="";
				String value="";
				String rawValue="";
				String column="";
				String columnDataType="";
				String resolutionMode="";
				String unit="";
				int alarmLevel=0;
				if(index<finalAcquisitionItemInfoList.size() 
						&& StringManagerUtils.isNotNull(finalAcquisitionItemInfoList.get(index).getTitle())
//						&&StringManagerUtils.existOrNot(userItems, finalAcquisitionItemInfoList.get(index).getRawTitle(),false)
						){
					columnName=finalAcquisitionItemInfoList.get(index).getTitle();
					value=finalAcquisitionItemInfoList.get(index).getValue();
					rawValue=finalAcquisitionItemInfoList.get(index).getRawValue();
					column=finalAcquisitionItemInfoList.get(index).getColumn();
					columnDataType=finalAcquisitionItemInfoList.get(index).getDataType();
					resolutionMode=finalAcquisitionItemInfoList.get(index).getResolutionMode()+"";
					alarmLevel=finalAcquisitionItemInfoList.get(index).getAlarmLevel();
					unit=finalAcquisitionItemInfoList.get(index).getUnit();
				}
				
				if(StringManagerUtils.isNotNull(columnName)&&StringManagerUtils.isNotNull(unit)){
					webSocketSendData.append("\"name"+(k+1)+"\":\""+(columnName+"("+unit+")")+"\",");
				}else{
					webSocketSendData.append("\"name"+(k+1)+"\":\""+columnName+"\",");
				}
				webSocketSendData.append("\"value"+(k+1)+"\":\""+value+"\",");
				displayItemInfo_json.append("{\"row\":"+j+",\"col\":"+k+",\"columnName\":\""+columnName+"\",\"column\":\""+column+"\",\"value\":\""+value+"\",\"rawValue\":\""+rawValue+"\",\"columnDataType\":\""+columnDataType+"\",\"resolutionMode\":\""+resolutionMode+"\",\"alarmLevel\":"+alarmLevel+"},");
			}
			if(webSocketSendData.toString().endsWith(",")){
				webSocketSendData.deleteCharAt(webSocketSendData.length() - 1);
			}
			webSocketSendData.append("},");
		}
		if(webSocketSendData.toString().endsWith(",")){
			webSocketSendData.deleteCharAt(webSocketSendData.length() - 1);
		}
		
		if(displayItemInfo_json.toString().endsWith(",")){
			displayItemInfo_json.deleteCharAt(displayItemInfo_json.length() - 1);
		}
		displayItemInfo_json.append("]");
		
		webSocketSendData.append("]");
		webSocketSendData.append(",\"CellInfo\":"+displayItemInfo_json);
		webSocketSendData.append(",\"allItemInfo\":"+allItemInfo_json);
		webSocketSendData.append(",\"wellBoreChartsData\":"+wellBoreChartsData);
		webSocketSendData.append(",\"surfaceChartsData\":"+surfaceChartsData);
		webSocketSendData.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle)+"}");
		return webSocketSendData.toString();
	}
	
	public boolean acqGroupDataCheck(ModbusProtocolConfig.Protocol protocol,AcqGroup acqGroup){
		boolean r=true;
		if(acqGroup.getAddr().size()!=acqGroup.getValue().size()){
			return false;
		}
		for(int i=0;i<acqGroup.getAddr().size();i++){
			for(int j=0;j<protocol.getItems().size();j++){
				if(acqGroup.getAddr().get(i)==protocol.getItems().get(j).getAddr() && protocol.getItems().get(j).getResolutionMode()!=0){
					if( !(acqGroup.getValue().get(i)!=null && acqGroup.getValue().get(i).size()==protocol.getItems().get(j).getQuantity()) ){
						r=false;
					}
					
					break;
				}
			}
		}
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public String DataProcessing(DeviceInfo deviceInfo,AcqGroup acqGroup){
		List<String> websocketClientUserList=new ArrayList<>();
		for (WebSocketByJavax item : WebSocketByJavax.clients.values()) {
            String[] clientInfo=item.userId.split("_");
            if(clientInfo!=null && clientInfo.length==3 && !StringManagerUtils.existOrNot(websocketClientUserList, clientInfo[1], true)){
            	websocketClientUserList.add(clientInfo[1]);
            }
        }
		
		boolean save=false;
		boolean alarm=false;
		try{
			AlarmShowStyle alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
			
			Map<String, Object> dataModelMap=DataModelMap.getMapObject();
			if(!dataModelMap.containsKey("ProtocolMappingColumnByTitle")){
				MemoryDataManagerTask.loadProtocolMappingColumnByTitle();
			}
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
			
			String realtimeTable="tbl_acqdata_latest";
			String historyTable="tbl_acqdata_hist";
			String rawDataTable="tbl_acqrawdata";
			String totalDataTable="tbl_dailycalculationdata";
			String functionCode="deviceRealTimeMonitoringData";
			if(acqGroup!=null){
				String protocolName="";
				List<KeyValue> acqDataList=new ArrayList<>();
				AcqInstanceOwnItem acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(deviceInfo.getInstanceCode());
				if(acqInstanceOwnItem!=null){
					protocolName=acqInstanceOwnItem.getProtocol();
				}
				DisplayInstanceOwnItem displayInstanceOwnItem=MemoryDataManagerTask.getDisplayInstanceOwnItemByCode(deviceInfo.getDisplayInstanceCode());
				AlarmInstanceOwnItem alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(deviceInfo.getAlarmInstanceCode());
				ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
				
				if(protocol!=null){
					Map<String,Map<String,String>> realtimeDataTimeMap=MemoryDataManagerTask.getDeviceRealtimeAcqDataById(deviceInfo.getId()+"");
					if(realtimeDataTimeMap==null){
						realtimeDataTimeMap=new LinkedHashMap<>();
					}
					
					String lastSaveTime=deviceInfo.getSaveTime();
					int save_cycle=acqInstanceOwnItem.getGroupSavingInterval();
					int acq_cycle=acqInstanceOwnItem.getGroupTimingInterval();
					String acqTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
					String date=StringManagerUtils.getCurrentTime("yyyy-MM-dd");
					
					if(!StringManagerUtils.timeMatchDate(acqTime, date, Config.getInstance().configFile.getAp().getReport().getOffsetHour())){
						date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(date),-1);
					}
					
					CommResponseData.Range dateTimeRange= StringManagerUtils.getTimeRange(date,Config.getInstance().configFile.getAp().getReport().getOffsetHour());
					
					long timeDiff=StringManagerUtils.getTimeDifference(lastSaveTime, acqTime, "yyyy-MM-dd HH:mm:ss");
					if(save_cycle<=acq_cycle || timeDiff>=save_cycle*1000){
						save=true;
					}
					CommResponseData commResponseData=null;
					TimeEffResponseData timeEffResponseData=null;
					
					RPCCalculateRequestData rpcCalculateRequestData=null;
					RPCCalculateResponseData rpcCalculateResponseData=null;
					WorkType workType=null;
					PCPCalculateResponseData pcpCalculateResponseData=null;
					
					boolean isAcqRunStatus=false,isAcqEnergy=false,isAcqTotalGasProd=false,isAcqTotalWaterProd=false;
					int runStatus=2;
					int checkSign=acqGroupDataCheck(protocol,acqGroup)?1:0;
					
					//进行通信计算
					commResponseData=commEffCalaulate(deviceInfo,acqTime,1);
					
					String updateRealtimeData="update "+realtimeTable+" t set t.acqTime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),t.CommStatus=1";
					String insertHistColumns="deviceid,acqTime,checkSign,CommStatus";
					String insertHistValue=deviceInfo.getId()+",to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),"+checkSign+",1";
					String insertHistSql="";
					
					String updateTotalDataSql="update "+totalDataTable+" t set t.CommStatus=1";
					
					List<AcquisitionItemInfo> acquisitionItemInfoList=new ArrayList<AcquisitionItemInfo>();
					List<ProtocolItemResolutionData> calItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
					
					List<ProtocolItemResolutionData> protocolItemResolutionDataList=DataProcessing(deviceInfo,acqTime,acqGroup,protocol,acqInstanceOwnItem,calItemResolutionDataList,acqDataList);
										
					cleanDailyTotalItems(deviceInfo,acqInstanceOwnItem);
					
					
//					Map<String,String> saveDataMap=DataProcessing(acqGroup,protocol);
					
//					Iterator<Map.Entry<String, String>> iterator = saveDataMap.entrySet().iterator();
//					while (iterator.hasNext()) {
//					    Map.Entry<String, String> entry = iterator.next();
//					    String key = entry.getKey();
//					    String value = entry.getValue();
//					    updateRealtimeData+=",t."+key+"='"+value+"'";
//						insertHistColumns+=","+key;
//						insertHistValue+=",'"+value+"'";
//					}
					
					
					if(protocolItemResolutionDataList!=null && protocolItemResolutionDataList.size()>0){
						for(int i=0;i<protocolItemResolutionDataList.size();i++){
							DataMapping dataMappingColumn=loadProtocolMappingColumnByTitleMap.get(protocolItemResolutionDataList.get(i).getColumnName());
							String columnName=dataMappingColumn.getMappingColumn();
							String rawValue=protocolItemResolutionDataList.get(i).getRawValue();
							if(runStatus==2){
								runStatus=RunStatusProcessing(rawValue,protocol.getCode()+"_"+protocolItemResolutionDataList.get(i).getColumnName());
							}
							
							if(runStatus==2){
								isAcqRunStatus=false;
							}else{
								isAcqRunStatus=true;
							}
						}
					}
					if(isAcqRunStatus){
						timeEffResponseData=tiemEffCalaulate(deviceInfo,acqTime,runStatus);
					}
					//通信
					deviceInfo.setAcqTime(acqTime);
					deviceInfo.setCommStatus(1);
					
					
					deviceInfo.setOnLineCommStatus(1);
					calItemResolutionDataList.add(new ProtocolItemResolutionData("通信状态","通信状态","在线","1","","commStatusName","","","","",1,1));
					if(commResponseData!=null&&commResponseData.getResultStatus()==1){
						deviceInfo.setOnLineAcqTime(acqTime);
						
						updateRealtimeData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
								+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
						insertHistColumns+=",commTimeEfficiency,commTime";
						insertHistValue+=","+commResponseData.getCurrent().getCommEfficiency().getEfficiency()+","+commResponseData.getCurrent().getCommEfficiency().getTime();
						
						updateTotalDataSql+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
								+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
						
						deviceInfo.setCommTime(commResponseData.getCurrent().getCommEfficiency().getTime());
						deviceInfo.setCommEff(commResponseData.getCurrent().getCommEfficiency().getEfficiency());
						deviceInfo.setCommRange(commResponseData.getCurrent().getCommEfficiency().getRangeString());
						
						deviceInfo.setOnLineCommTime(commResponseData.getCurrent().getCommEfficiency().getTime());
						deviceInfo.setOnLineCommEff(commResponseData.getCurrent().getCommEfficiency().getEfficiency());
						deviceInfo.setOnLineCommRange(commResponseData.getCurrent().getCommEfficiency().getRangeString());
						
						calItemResolutionDataList.add(new ProtocolItemResolutionData("通信时间","通信时间",commResponseData.getCurrent().getCommEfficiency().getTime()+"",commResponseData.getCurrent().getCommEfficiency().getTime()+"","","commTime","","","","",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("通信时率","通信时率",commResponseData.getCurrent().getCommEfficiency().getEfficiency()+"",commResponseData.getCurrent().getCommEfficiency().getEfficiency()+"","","commtimeEfficiency","","","","",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("通信区间","通信区间",commResponseData.getCurrent().getCommEfficiency().getRangeString(),commResponseData.getCurrent().getCommEfficiency().getRangeString(),"","commRange","","","","",1,1));
					}
					updateRealtimeData+=",t.runStatus= "+runStatus;
					insertHistColumns+=",runStatus";
					insertHistValue+=","+runStatus;
					updateTotalDataSql+=",t.runStatus= "+runStatus;
					calItemResolutionDataList.add(new ProtocolItemResolutionData("运行状态","运行状态",runStatus==1?"运行":(runStatus==0?"停抽":"无数据"),runStatus+"","","runStatusName","","","","",1,1));
					
					if(timeEffResponseData!=null && timeEffResponseData.getResultStatus()==1){
						updateRealtimeData+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
								+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
						insertHistColumns+=",runTimeEfficiency,runTime";
						insertHistValue+=","+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()+","+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
						updateTotalDataSql+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
								+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
						
						deviceInfo.setRunStatusAcqTime(acqTime);
						deviceInfo.setRunStatus(runStatus);
						deviceInfo.setRunTime(timeEffResponseData.getCurrent().getRunEfficiency().getTime());
						deviceInfo.setRunEff(timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency());
						deviceInfo.setRunRange(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());
						
						calItemResolutionDataList.add(new ProtocolItemResolutionData("运行时间","运行时间",timeEffResponseData.getCurrent().getRunEfficiency().getTime()+"",timeEffResponseData.getCurrent().getRunEfficiency().getTime()+"","","runTime","","","","",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("运行时率","运行时率",timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()+"",timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()+"","","runtimeEfficiency","","","","",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("运行区间","运行区间",timeEffResponseData.getCurrent().getRunEfficiency().getRangeString(),timeEffResponseData.getCurrent().getRunEfficiency().getRangeString(),"","runRange","","","","",1,1));
					}
					
					List<ProtocolItemResolutionData> inputItemItemResolutionDataList=new ArrayList<>();;
					
					if(deviceInfo.getCalculateType()==1){
						rpcCalculateRequestData=new RPCCalculateRequestData();
						rpcCalculateRequestData.init();
						rpcCalculateResponseData=RPCDataProcessing(deviceInfo,acqGroup,commResponseData,timeEffResponseData,
								acqTime,calItemResolutionDataList,runStatus,rpcCalculateRequestData,save,checkSign);
						inputItemItemResolutionDataList=getRPCInputItemData(deviceInfo);
						if(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1){
							workType=MemoryDataManagerTask.getWorkTypeByCode(rpcCalculateResponseData.getCalculationStatus().getResultCode()+"");
						}
					}else if(deviceInfo.getCalculateType()==2){
						pcpCalculateResponseData=PCPDataProcessing(deviceInfo,acqGroup,commResponseData,timeEffResponseData,
								acqTime,calItemResolutionDataList,runStatus,save,checkSign);
						inputItemItemResolutionDataList=getPCPInputItemData(deviceInfo);
					}
					
					
					
					updateRealtimeData+=" where t.deviceId= "+deviceInfo.getId();
					insertHistSql="insert into "+historyTable+"("+insertHistColumns+")values("+insertHistValue+")";
					updateTotalDataSql+=" where t.deviceId= "+deviceInfo.getId()+" and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
					
					//排序
					Collections.sort(protocolItemResolutionDataList);
					//报警判断
					int commAlarmLevel=0,resultAlarmLevel=0,runAlarmLevel=0;
					if(alarmInstanceOwnItem!=null){
						for(int i=0;i<alarmInstanceOwnItem.itemList.size();i++){
							if(alarmInstanceOwnItem.getItemList().get(i).getType()==3 && alarmInstanceOwnItem.getItemList().get(i).getItemName().equalsIgnoreCase("在线")){
								commAlarmLevel=alarmInstanceOwnItem.getItemList().get(i).getAlarmLevel();
							}else if(workType!=null&&alarmInstanceOwnItem.getItemList().get(i).getType()==4 && alarmInstanceOwnItem.getItemList().get(i).getItemCode().equalsIgnoreCase(workType.getResultCode()+"")){
								resultAlarmLevel=alarmInstanceOwnItem.getItemList().get(i).getAlarmLevel();
							}else if(alarmInstanceOwnItem.getItemList().get(i).getType()==6 && alarmInstanceOwnItem.getItemList().get(i).getItemName().equalsIgnoreCase(runStatus==1?"运行":(runStatus==0?"停抽":"无数据"))){
								runAlarmLevel=alarmInstanceOwnItem.getItemList().get(i).getAlarmLevel();
							}
						}
					}
					acquisitionItemInfoList=DataAlarmProcessing(protocolItemResolutionDataList,alarmInstanceOwnItem,acquisitionItemInfoList);
					acquisitionItemInfoList=CalculateDataAlarmProcessing(calItemResolutionDataList,alarmInstanceOwnItem,acquisitionItemInfoList,rpcCalculateResponseData);
					acquisitionItemInfoList=InputDataAlarmProcessing(inputItemItemResolutionDataList,alarmInstanceOwnItem,acquisitionItemInfoList);
					
					for(AcquisitionItemInfo acquisitionItemInfo: acquisitionItemInfoList){
						if(acquisitionItemInfo.getAlarmLevel()>0){
							alarm=true;
							break;
						}
					}
					
					//保存至内存中
					if(realtimeDataTimeMap!=null){
						//删除非当天采集的数据
						Iterator<Map.Entry<String,Map<String,String>>> realtimeDataTimeMapIterator = realtimeDataTimeMap.entrySet().iterator();
						while(realtimeDataTimeMapIterator.hasNext()){
							Map.Entry<String,Map<String,String>> entry = realtimeDataTimeMapIterator.next();
						    String key = entry.getKey();
						    Map<String,String> value = entry.getValue();
							if(!StringManagerUtils.timeMatchDate(key, StringManagerUtils.getCurrentTime(), 0)
									){
								realtimeDataTimeMapIterator.remove();
							}
						}
						//添加新采集数据至内存
						Map<String,String> everyDataMap =new LinkedHashMap<>();
						for(AcquisitionItemInfo acquisitionItemInfo: acquisitionItemInfoList){
							everyDataMap.put(acquisitionItemInfo.getColumn().toUpperCase(), acquisitionItemInfo.getValue());
						}
						realtimeDataTimeMap.put(acqTime, everyDataMap);
						
						MemoryDataManagerTask.updateDeviceRealtimeAcqData(deviceInfo.getId()+"",realtimeDataTimeMap);
					}
					
					//如果满足单组入库间隔或者有报警，保存数据
					if(save || alarm){
						String saveRawDataSql="insert into "+rawDataTable+"(deviceid,acqtime,rawdata)values("+deviceInfo.getId()+",to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),'"+acqGroup.getRawData()+"' )";
						deviceInfo.setSaveTime(acqTime);
						int result=commonDataService.getBaseDao().updateOrDeleteBySql(updateRealtimeData);
						if(result==0){
							updateRealtimeData=insertHistSql.replace(historyTable, realtimeTable);
							result=commonDataService.getBaseDao().updateOrDeleteBySql(updateRealtimeData);
						}
						commonDataService.getBaseDao().updateOrDeleteBySql(insertHistSql);
						commonDataService.getBaseDao().updateOrDeleteBySql(saveRawDataSql);
						commonDataService.getBaseDao().updateOrDeleteBySql(updateTotalDataSql);
						
						if(commResponseData!=null&&commResponseData.getResultStatus()==1){
							List<String> clobCont=new ArrayList<String>();
							String updateRealRangeClobSql="update "+realtimeTable+" t set t.commrange=?";
							String updateHisRangeClobSql="update "+historyTable+" t set t.commrange=?";
							String updateTotalRangeClobSql="update "+totalDataTable+" t set t.commrange=?";
							clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
							if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
								updateRealRangeClobSql+=", t.runrange=?";
								updateHisRangeClobSql+=", t.runrange=?";
								updateTotalRangeClobSql+=", t.runrange=?";
								clobCont.add(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());
							}
							updateRealRangeClobSql+=" where t.deviceid="+deviceInfo.getId();
							updateHisRangeClobSql+=" where t.deviceid="+deviceInfo.getId() +" and t.acqTime="+"to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
							updateTotalRangeClobSql+=" where t.deviceId= "+deviceInfo.getId()+"and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
							commonDataService.getBaseDao().executeSqlUpdateClob(updateRealRangeClobSql,clobCont);
							commonDataService.getBaseDao().executeSqlUpdateClob(updateHisRangeClobSql,clobCont);
							commonDataService.getBaseDao().executeSqlUpdateClob(updateTotalRangeClobSql,clobCont);
						}
						
						//更新采集数据
						List<String> acqDataClobCont=new ArrayList<String>();
						acqDataClobCont.add(new Gson().toJson(acqDataList));
						String updateRealAcqDataClobSql="update "+realtimeTable+" t set t.acqdata=? where t.deviceid="+deviceInfo.getId();
						String updateHisAcqDataClobSql="update "+historyTable+" t set t.acqdata=? where t.deviceid="+deviceInfo.getId() +" and t.acqTime="+"to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
						commonDataService.getBaseDao().executeSqlUpdateClob(updateRealAcqDataClobSql,acqDataClobCont);
						commonDataService.getBaseDao().executeSqlUpdateClob(updateHisAcqDataClobSql,acqDataClobCont);
						
						//保存日汇总数据
						saveDailyTotalData(deviceInfo);
						//统计
						CalculateDataManagerTask.acquisitionDataTotalCalculate(deviceInfo.getId()+"", date);
						//报警项
						if(alarm){
							calculateDataService.saveAndSendAlarmInfo(deviceInfo.getId(),deviceInfo.getWellName(),deviceInfo.getDeviceType()+"",acqTime,acquisitionItemInfoList);
						}
						
						//保存计算数据
						if(rpcCalculateResponseData!=null){
							
						}
						if(pcpCalculateResponseData!=null){
							
						}
					}
					
					//放入内存数据库中
					MemoryDataManagerTask.updateDeviceInfo(deviceInfo);
					//处理websocket推送
					if(displayInstanceOwnItem!=null){
						for (String websocketClientUser : websocketClientUserList) {
							UserInfo userInfo=MemoryDataManagerTask.getUserInfoByNo(websocketClientUser);
							if(userInfo!=null){
								int items=3;
								String webSocketSendDataStr=getWebSocketSendData(deviceInfo,acqTime,userInfo,acquisitionItemInfoList,displayInstanceOwnItem,items,functionCode,commAlarmLevel,runAlarmLevel,
										rpcCalculateResponseData,rpcCalculateRequestData,resultAlarmLevel,
										alarmShowStyle);
								infoHandler().sendMessageToUser(websocketClientUser, webSocketSendDataStr);
//								System.out.println(webSocketSendDataStr);
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return null;
	}
	
	public void SaveFESDiagramCalData(DeviceInfo deviceInfo,
			CommResponseData commResponseData,TimeEffResponseData timeEffResponseData,
			String acqTime,int runStatus,
			RPCCalculateRequestData rpcCalculateRequestData,RPCCalculateResponseData rpcCalculateResponseData){
		String realtimeTable="tbl_rpcacqdata_latest";
		String historyTable="tbl_rpcacqdata_hist";
		String totalDataTable="tbl_rpcdailycalculationdata";
		
		String updateRealtimeData="update "+realtimeTable+" t set t.acqTime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),t.CommStatus=1,t.runStatus="+runStatus;
		
		
		String insertHistColumns="deviceid,acqTime,CommStatus,runStatus";
		String insertHistValue=deviceInfo.getId()+",to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),1,"+runStatus;
		String insertHistSql="";
		
		String updateTotalDataSql="update "+totalDataTable+" t set t.CommStatus=1,t.runStatus="+runStatus;
		
		if(commResponseData!=null&&commResponseData.getResultStatus()==1){
			updateRealtimeData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
					+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
			insertHistColumns+=",commTimeEfficiency,commTime";
			insertHistValue+=","+commResponseData.getCurrent().getCommEfficiency().getEfficiency()+","+commResponseData.getCurrent().getCommEfficiency().getTime();
			
			updateTotalDataSql+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
					+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
		}
		if(timeEffResponseData!=null && timeEffResponseData.getResultStatus()==1){
			updateRealtimeData+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
					+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
			insertHistColumns+=",runTimeEfficiency,runTime";
			insertHistValue+=","+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()+","+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
			updateTotalDataSql+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
					+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
		}
		
		
		updateTotalDataSql+=",t.TubingPressure="+rpcCalculateRequestData.getProduction().getTubingPressure()+"";
		updateTotalDataSql+=",t.CasingPressure="+rpcCalculateRequestData.getProduction().getCasingPressure()+"";
		updateTotalDataSql+=",t.ProducingfluidLevel="+rpcCalculateRequestData.getProduction().getProducingfluidLevel()+"";
		updateTotalDataSql+=",t.Stroke="+rpcCalculateRequestData.getFESDiagram().getStroke()+"";
		updateTotalDataSql+=",t.TubingPressure="+rpcCalculateRequestData.getProduction().getTubingPressure()+"";
		updateTotalDataSql+=",t.TubingPressure="+rpcCalculateRequestData.getProduction().getTubingPressure()+"";
		
		if(rpcCalculateResponseData!=null && rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1 
				&& rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232
				&& rpcCalculateResponseData.getProduction().getProducingfluidLevel()>=0
				&& !updateTotalDataSql.toLowerCase().contains("producingfluidLevel")){
			updateTotalDataSql+=",t.producingfluidLevel= "+rpcCalculateResponseData.getProduction().getProducingfluidLevel();
		}
		
	}
	
	public RPCCalculateResponseData RPCDataProcessing(DeviceInfo deviceInfo,AcqGroup acqGroup,CommResponseData commResponseData,TimeEffResponseData timeEffResponseData,String acqTime,
			List<ProtocolItemResolutionData> calItemResolutionDataList,int runStatus,RPCCalculateRequestData rpcCalculateRequestData,
			boolean save,int checkSign){
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		
		DataWriteBackConfig dataWriteBackConfig=MemoryDataManagerTask.getDataWriteBackConfig();
		
		Jedis jedis=null;
		RPCDeviceTodayData deviceTodayData=null;
		RPCCalculateResponseData rpcCalculateResponseData=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("RPCDeviceTodayData".getBytes())){
				MemoryDataManagerTask.loadTodayFESDiagram(null,0);
			}
			if(jedis.hexists("RPCDeviceTodayData".getBytes(), (deviceInfo.getId()+"").getBytes())){
				deviceTodayData=(RPCDeviceTodayData) SerializeObjectUnils.unserizlize(jedis.hget("RPCDeviceTodayData".getBytes(), (deviceInfo.getId()+"").getBytes()));
			}
			
			if(!jedis.exists("rpcCalItemList".getBytes())){
				MemoryDataManagerTask.loadRPCCalculateItem();
			}
			
			if(!jedis.exists("AcqInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadAcqInstanceOwnItemById("","update");
			}
			
			if(!jedis.exists("ProtocolMappingColumn".getBytes())){
				MemoryDataManagerTask.loadProtocolMappingColumn();
			}
			
			Map<String, Object> dataModelMap=DataModelMap.getMapObject();
			if(!dataModelMap.containsKey("ProtocolMappingColumnByTitle")){
				MemoryDataManagerTask.loadProtocolMappingColumnByTitle();
			}
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
			
			String realtimeTable="tbl_rpcacqdata_latest";
			String historyTable="tbl_rpcacqdata_hist";
			String totalDataTable="tbl_rpcdailycalculationdata";
			if(acqGroup!=null){
				String protocolName="";
				String acqProtocolType="";
				AcqInstanceOwnItem acqInstanceOwnItem=null;
				if(jedis!=null&&jedis.hexists("AcqInstanceOwnItem".getBytes(), deviceInfo.getInstanceCode().getBytes())){
					acqInstanceOwnItem=(AcqInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AcqInstanceOwnItem".getBytes(), deviceInfo.getInstanceCode().getBytes()));
					if(acqInstanceOwnItem!=null){
						protocolName=acqInstanceOwnItem.getProtocol();
						acqProtocolType=acqInstanceOwnItem.getAcqProtocolType();
					}
				}
				ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
				
				if(protocol!=null){
					String date=StringManagerUtils.getCurrentTime("yyyy-MM-dd");
					if(!StringManagerUtils.timeMatchDate(acqTime, date, Config.getInstance().configFile.getAp().getReport().getOffsetHour())){
						date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(date),-1);
					}
					
					
					if("private-mqtt".equalsIgnoreCase(acqProtocolType)){
						rpcCalculateRequestData.getFESDiagram().setSrc(1);
					}else{
						rpcCalculateRequestData.getFESDiagram().setSrc(0);
					}
					updateRequestData(rpcCalculateRequestData,deviceInfo);
					
					
					
					TotalAnalysisResponseData totalAnalysisResponseData=null;
					TotalAnalysisRequestData totalAnalysisRequestData=null;
					String totalRequestData="";
					
					boolean isAcqCalResultData=false;
					boolean isAcqRPMData=false;
					
					int acqResultCode=0;
					int acqResultStatus=1;
					float acqFMax=0,acqFMin=0,acqFullnessCoefficient=0,
					acqUpperLoadLine=0,acqLowerLoadLine=0,
					acqTheoreticalProduction=0,
					acqLiquidVolumetricProduction=0,acqOilVolumetricProduction=0,acqWaterVolumetricProduction=0,
					acqAvailablePlungerStrokeProd_v=0,acqPumpClearanceLeakProd_v=0,acqTVLeakVolumetricProduction=0,acqSVLeakVolumetricProduction=0,acqGasInfluenceProd_v=0,
					acqLiquidWeightProduction=0,acqOilWeightProduction=0,acqWaterWeightProduction=0,
					acqAvailablePlungerStrokeProd_w=0,acqPumpClearanceLeakProd_w=0,acqTVLeakWeightProduction=0,acqSVLeakWeightProduction=0,acqGasInfluenceProd_w=0,
					acqAverageWatt=0,acqPolishRodPower=0,acqWaterPower=0,acqSurfaceSystemEfficiency=0,acqWellDownSystemEfficiency=0,acqSystemEfficiency=0,acqEnergyPer100mLift=0,acqArea=0,
					acqRodFlexLength=0,acqTubingFlexLength=0,acqInertiaLength=0,acqPumpEff1=0,acqPumpEff2=0,acqPumpEff3=0,acqPumpEff4=0,acqPumpEff=0,
					acqCalcProducingfluidLevel=0,acqLevelDifferenceValue=0,
					acqUpStrokeWattMax=0,acqDownStrokeWattMax=0,acqWattDegreeBalance=0,acqUpStrokeIMax=0,acqDownStrokeIMax=0,acqIDegreeBalance=0,acqDeltaRadius=0,
					acqSubmergence=0,
					acqRPM=0;
					
					
					String updateRealtimeData="update "+realtimeTable+" t set t.acqTime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),t.CommStatus=1,t.runStatus="+runStatus;
					
					
					String insertHistColumns="deviceid,acqTime,CommStatus,runStatus";
					String insertHistValue=deviceInfo.getId()+",to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),1,"+runStatus;
					String insertHistSql="";
					
					String updateTotalDataSql="update "+totalDataTable+" t set t.CommStatus=1,t.runStatus="+runStatus;
					
					if(commResponseData!=null&&commResponseData.getResultStatus()==1){
						updateRealtimeData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
								+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
						insertHistColumns+=",commTimeEfficiency,commTime";
						insertHistValue+=","+commResponseData.getCurrent().getCommEfficiency().getEfficiency()+","+commResponseData.getCurrent().getCommEfficiency().getTime();
						
						updateTotalDataSql+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
								+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
					}
					if(timeEffResponseData!=null && timeEffResponseData.getResultStatus()==1){
						updateRealtimeData+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
								+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
						insertHistColumns+=",runTimeEfficiency,runTime";
						insertHistValue+=","+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()+","+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
						updateTotalDataSql+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
								+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
					}
					
					int FESDiagramAcqCount=0;
					boolean FESDiagramCalculate=false;
					for(int i=0;acqGroup.getAddr()!=null &&i<acqGroup.getAddr().size();i++){
						for(int j=0;j<protocol.getItems().size();j++){
							if(acqGroup.getAddr().get(i)==protocol.getItems().get(j).getAddr()){
								String value="";
								DataMapping dataMappingColumn=loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(j).getTitle());
								String columnName=dataMappingColumn.getMappingColumn();
								
								if(acqGroup.getValue()!=null&&acqGroup.getValue().size()>i&&acqGroup.getValue().get(i)!=null ){
									value=StringManagerUtils.objectListToString(acqGroup.getValue().get(i), protocol.getItems().get(j));
								}
								String rawValue=value;
								String addr=protocol.getItems().get(j).getAddr()+"";
								String title=protocol.getItems().get(j).getTitle();
								String rawTitle=title;
								String columnDataType=protocol.getItems().get(j).getIFDataType();
								String resolutionMode=protocol.getItems().get(j).getResolutionMode()+"";
								String bitIndex="";
								String unit=protocol.getItems().get(j).getUnit();
								int alarmLevel=0;
								int sort=9999;
								
								if(StringManagerUtils.existAcqItem(acqInstanceOwnItem.getItemList(), title, false)){
									
									for(AcqInstanceOwnItem.AcqItem acqItem:acqInstanceOwnItem.getItemList()){
										
									}
									
									
									String saveValue=rawValue;
									if(protocol.getItems().get(j).getQuantity()==1&&rawValue.length()>50){
										saveValue=rawValue.substring(0, 50);
									}
									
									if("TubingPressure".equalsIgnoreCase(dataMappingColumn.getCalColumn())){//油压
										if(StringManagerUtils.isNum(rawValue) || StringManagerUtils.isNumber(rawValue)){
											if(rpcCalculateRequestData.getProduction()!=null){
												rpcCalculateRequestData.getProduction().setTubingPressure(StringManagerUtils.stringToFloat(rawValue));
											}
											if(deviceInfo.getRpcCalculateRequestData()!=null && deviceInfo.getRpcCalculateRequestData().getProduction()!=null){
												deviceInfo.getRpcCalculateRequestData().getProduction().setTubingPressure(StringManagerUtils.stringToFloat(rawValue));
											}
											updateTotalDataSql+=",t."+dataMappingColumn.getCalColumn()+"="+StringManagerUtils.stringToFloat(rawValue)+"";
										}
									}else if("CasingPressure".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										if(StringManagerUtils.isNum(rawValue) || StringManagerUtils.isNumber(rawValue)){
											if(rpcCalculateRequestData.getProduction()!=null){
												rpcCalculateRequestData.getProduction().setCasingPressure(StringManagerUtils.stringToFloat(rawValue));
											}
											if(deviceInfo.getRpcCalculateRequestData()!=null && deviceInfo.getRpcCalculateRequestData().getProduction()!=null){
												deviceInfo.getRpcCalculateRequestData().getProduction().setCasingPressure(StringManagerUtils.stringToFloat(rawValue));
											}
											updateTotalDataSql+=",t."+dataMappingColumn.getCalColumn()+"="+StringManagerUtils.stringToFloat(rawValue)+"";
										}
									}else if("WellHeadTemperature".equalsIgnoreCase(dataMappingColumn.getCalColumn())){//油压
										if(StringManagerUtils.isNum(rawValue) || StringManagerUtils.isNumber(rawValue)){
											if(rpcCalculateRequestData.getProduction()!=null){
												rpcCalculateRequestData.getProduction().setWellHeadTemperature(StringManagerUtils.stringToFloat(rawValue));
											}
											if(deviceInfo.getRpcCalculateRequestData()!=null && deviceInfo.getRpcCalculateRequestData().getProduction()!=null){
												deviceInfo.getRpcCalculateRequestData().getProduction().setWellHeadTemperature(StringManagerUtils.stringToFloat(rawValue));
											}
										}
									}else if("ProducingfluidLevel".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										if(StringManagerUtils.isNum(rawValue) || StringManagerUtils.isNumber(rawValue)){
											if(rpcCalculateRequestData.getProduction()!=null){
												rpcCalculateRequestData.getProduction().setProducingfluidLevel(StringManagerUtils.stringToFloat(rawValue));
											}
											if(deviceInfo.getRpcCalculateRequestData()!=null && deviceInfo.getRpcCalculateRequestData().getProduction()!=null){
												deviceInfo.getRpcCalculateRequestData().getProduction().setProducingfluidLevel(StringManagerUtils.stringToFloat(rawValue));
											}
											updateTotalDataSql+=",t."+dataMappingColumn.getCalColumn()+"="+StringManagerUtils.stringToFloat(rawValue)+"";
										}
									}else if("BottomHolePressure".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										if(StringManagerUtils.isNum(rawValue) || StringManagerUtils.isNumber(rawValue)){
											updateTotalDataSql+=",t."+dataMappingColumn.getCalColumn()+"="+StringManagerUtils.stringToFloat(rawValue)+"";
										}
									}else if("VolumeWaterCut".equalsIgnoreCase(dataMappingColumn.getCalColumn()) || "WaterCut".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										if(StringManagerUtils.isNum(rawValue) || StringManagerUtils.isNumber(rawValue)){
											if(rpcCalculateRequestData.getProduction()!=null){
												rpcCalculateRequestData.getProduction().setWaterCut(StringManagerUtils.stringToFloat(rawValue));
												rpcCalculateRequestData.getProduction().setWeightWaterCut(StringManagerUtils.stringToFloat(rawValue));
											}
											if(deviceInfo.getRpcCalculateRequestData()!=null&&deviceInfo.getRpcCalculateRequestData().getProduction()!=null){
												deviceInfo.getRpcCalculateRequestData().getProduction().setWaterCut(StringManagerUtils.stringToFloat(rawValue));
												deviceInfo.getRpcCalculateRequestData().getProduction().setWeightWaterCut(StringManagerUtils.stringToFloat(rawValue));
											}
										}
									}else if("RealtimeLiquidVolumetricProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())
											|| "RealtimeOilVolumetricProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())
											|| "RealtimeWaterVolumetricProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())
											|| "RealtimeGasVolumetricProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())
											|| "RealtimeLiquidWeightProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())
											|| "RealtimeOilWeightProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())
											|| "RealtimeWaterWeightProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())
											){
										if(StringManagerUtils.isNum(rawValue) || StringManagerUtils.isNumber(rawValue)){
											updateRealtimeData+=",t."+dataMappingColumn.getCalColumn()+"="+StringManagerUtils.stringToFloat(rawValue)+"";
											insertHistColumns+=","+dataMappingColumn.getCalColumn();
											insertHistValue+=","+StringManagerUtils.stringToFloat(rawValue)+"";
										}
									}
									else if("FESDiagramAcqCount".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										FESDiagramAcqCount=StringManagerUtils.stringToInteger(rawValue);
										FESDiagramCalculate=true;
									}else if("FESDiagramAcqtime".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										String FESDiagramAcqtime="";
										if(rawValue.endsWith("simulate")){
											FESDiagramAcqtime=acqTime;
										}else{
											if("bcd".equalsIgnoreCase(protocol.getItems().get(j).getStoreDataType())&&rawValue.length()==24){
									        	String[] acqTimeStrArr=StringManagerUtils.stringToStringArray(rawValue,2);
									        	if(acqTimeStrArr!=null && acqTimeStrArr.length==12){
									        		FESDiagramAcqtime=acqTimeStrArr[0]+acqTimeStrArr[1]+"-"+acqTimeStrArr[3]+"-"+acqTimeStrArr[5]+" "+acqTimeStrArr[7]+":"+acqTimeStrArr[9]+":"+acqTimeStrArr[11];
									        	}
									        }
										}
								        rpcCalculateRequestData.getFESDiagram().setAcqTime(FESDiagramAcqtime);
								        FESDiagramCalculate=true;
									}else if("Stroke".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										updateTotalDataSql+=",t."+dataMappingColumn.getCalColumn()+"="+StringManagerUtils.stringToFloat(rawValue)+"";
										rpcCalculateRequestData.getFESDiagram().setStroke(StringManagerUtils.stringToFloat(rawValue));
									}else if("SPM".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										updateTotalDataSql+=",t."+dataMappingColumn.getCalColumn()+"="+StringManagerUtils.stringToFloat(rawValue)+"";
										rpcCalculateRequestData.getFESDiagram().setSPM(StringManagerUtils.stringToFloat(rawValue));
									}else if("Position_Curve".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										if(StringManagerUtils.isNotNull(rawValue)){
											String[] dataArr=rawValue.split(",");
											for(int k=0;k<dataArr.length;k++){
												rpcCalculateRequestData.getFESDiagram().getS().add(StringManagerUtils.stringToFloat(dataArr[k]));
											}
										}
										FESDiagramCalculate=true;
									}else if("Load_Curve".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										if(StringManagerUtils.isNotNull(rawValue)){
											String[] dataArr=rawValue.split(",");
											for(int k=0;k<dataArr.length;k++){
												rpcCalculateRequestData.getFESDiagram().getF().add(StringManagerUtils.stringToFloat(dataArr[k]));
											}
										}
										FESDiagramCalculate=true;
									}else if("Power_Curve".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										if(StringManagerUtils.isNotNull(rawValue)){
											String[] dataArr=rawValue.split(",");
											for(int k=0;k<dataArr.length;k++){
												rpcCalculateRequestData.getFESDiagram().getWatt().add(StringManagerUtils.stringToFloat(dataArr[k]));
											}
										}
										FESDiagramCalculate=true;
									}else if("Current_Curve".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										if(StringManagerUtils.isNotNull(rawValue)){
											String[] dataArr=rawValue.split(",");
											for(int k=0;k<dataArr.length;k++){
												rpcCalculateRequestData.getFESDiagram().getI().add(StringManagerUtils.stringToFloat(dataArr[k]));
											}
										}
										FESDiagramCalculate=true;
									}
									else if("ResultCode".equalsIgnoreCase(dataMappingColumn.getCalColumn())){//功图工况
										isAcqCalResultData=true;
										acqResultCode=StringManagerUtils.stringToInteger(rawValue);
									}else if("FMax".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqFMax=StringManagerUtils.stringToFloat(rawValue);
									}else if("FMin".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqFMin=StringManagerUtils.stringToFloat(rawValue);
									}else if("FullnessCoefficient".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqFullnessCoefficient=StringManagerUtils.stringToFloat(rawValue);
									}else if("UpperLoadLine".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqUpperLoadLine=StringManagerUtils.stringToFloat(rawValue);
									}else if("LowerLoadLine".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqLowerLoadLine=StringManagerUtils.stringToFloat(rawValue);
									}else if("TheoreticalProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqTheoreticalProduction=StringManagerUtils.stringToFloat(rawValue);
									}else if("LiquidVolumetricProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqLiquidVolumetricProduction=StringManagerUtils.stringToFloat(rawValue);
									}else if("OilVolumetricProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqOilVolumetricProduction=StringManagerUtils.stringToFloat(rawValue);
									}else if("WaterVolumetricProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqWaterVolumetricProduction=StringManagerUtils.stringToFloat(rawValue);
									}else if("AvailablePlungerStrokeProd_v".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqAvailablePlungerStrokeProd_v=StringManagerUtils.stringToFloat(rawValue);
									}else if("PumpClearanceLeakProd_v".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqPumpClearanceLeakProd_v=StringManagerUtils.stringToFloat(rawValue);
									}else if("TVLeakVolumetricProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqTVLeakVolumetricProduction=StringManagerUtils.stringToFloat(rawValue);
									}else if("SVLeakVolumetricProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqSVLeakVolumetricProduction=StringManagerUtils.stringToFloat(rawValue);
									}else if("GasInfluenceProd_v".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqGasInfluenceProd_v=StringManagerUtils.stringToFloat(rawValue);
									}else if("LiquidWeightProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqLiquidWeightProduction=StringManagerUtils.stringToFloat(rawValue);
									}else if("OilWeightProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqOilWeightProduction=StringManagerUtils.stringToFloat(rawValue);
									}else if("WaterWeightProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqWaterWeightProduction=StringManagerUtils.stringToFloat(rawValue);
									}else if("AvailablePlungerStrokeProd_w".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqAvailablePlungerStrokeProd_w=StringManagerUtils.stringToFloat(rawValue);
									}else if("PumpClearanceLeakProd_w".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqPumpClearanceLeakProd_w=StringManagerUtils.stringToFloat(rawValue);
									}else if("TVLeakWeightProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqTVLeakWeightProduction=StringManagerUtils.stringToFloat(rawValue);
									}else if("SVLeakWeightProduction".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqSVLeakWeightProduction=StringManagerUtils.stringToFloat(rawValue);
									}else if("GasInfluenceProd_w".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqGasInfluenceProd_w=StringManagerUtils.stringToFloat(rawValue);
									}else if("AverageWatt".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqAverageWatt=StringManagerUtils.stringToFloat(rawValue);
									}else if("PolishRodPower".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqPolishRodPower=StringManagerUtils.stringToFloat(rawValue);
									}else if("WaterPower".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqWaterPower=StringManagerUtils.stringToFloat(rawValue);
									}else if("SurfaceSystemEfficiency".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqSurfaceSystemEfficiency=StringManagerUtils.stringToFloat(rawValue);
									}else if("WellDownSystemEfficiency".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqWellDownSystemEfficiency=StringManagerUtils.stringToFloat(rawValue);
									}else if("SystemEfficiency".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqSystemEfficiency=StringManagerUtils.stringToFloat(rawValue);
									}else if("EnergyPer100mLift".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqEnergyPer100mLift=StringManagerUtils.stringToFloat(rawValue);
									}else if("Area".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqArea=StringManagerUtils.stringToFloat(rawValue);
									}else if("RodFlexLength".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqRodFlexLength=StringManagerUtils.stringToFloat(rawValue);
									}else if("TubingFlexLength".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqTubingFlexLength=StringManagerUtils.stringToFloat(rawValue);
									}else if("InertiaLength".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqInertiaLength=StringManagerUtils.stringToFloat(rawValue);
									}else if("PumpEff1".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqPumpEff1=StringManagerUtils.stringToFloat(rawValue);
									}else if("PumpEff2".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqPumpEff2=StringManagerUtils.stringToFloat(rawValue);
									}else if("PumpEff3".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqPumpEff3=StringManagerUtils.stringToFloat(rawValue);
									}else if("PumpEff4".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqPumpEff4=StringManagerUtils.stringToFloat(rawValue);
									}else if("PumpEff".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqPumpEff=StringManagerUtils.stringToFloat(rawValue);
									}else if("CalcProducingfluidLevel".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqCalcProducingfluidLevel=StringManagerUtils.stringToFloat(rawValue);
									}else if("LevelDifferenceValue".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqLevelDifferenceValue=StringManagerUtils.stringToFloat(rawValue);
									}else if("UpStrokeWattMax".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqUpStrokeWattMax=StringManagerUtils.stringToFloat(rawValue);
									}else if("DownStrokeWattMax".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqDownStrokeWattMax=StringManagerUtils.stringToFloat(rawValue);
									}else if("WattDegreeBalance".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqWattDegreeBalance=StringManagerUtils.stringToFloat(rawValue);
									}else if("UpStrokeIMax".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqUpStrokeIMax=StringManagerUtils.stringToFloat(rawValue);
									}else if("DownStrokeIMax".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqDownStrokeIMax=StringManagerUtils.stringToFloat(rawValue);
									}else if("IDegreeBalance".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqIDegreeBalance=StringManagerUtils.stringToFloat(rawValue);
									}else if("DeltaRadius".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqDeltaRadius=StringManagerUtils.stringToFloat(rawValue);
									}else if("Submergence".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										acqSubmergence=StringManagerUtils.stringToFloat(rawValue);
									}else if("RPM".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										isAcqCalResultData=true;
										isAcqRPMData=true;
										acqRPM=StringManagerUtils.stringToFloat(rawValue);
									}
								}
								break;
							}
						}
					}
					
					//进行功图计算
					WorkType workType=null;
					boolean fesDiagramEnabled=false;
					
					//如果采集了计算数据
					if(isAcqCalResultData && checkSign==1){
						fesDiagramEnabled=true;
						
						rpcCalculateResponseData=new RPCCalculateResponseData();
						rpcCalculateResponseData.init();
						
						rpcCalculateResponseData.getFESDiagram().setAcqTime(acqTime);
						rpcCalculateRequestData.getFESDiagram().setAcqTime(acqTime);
						
						rpcCalculateResponseData.getCalculationStatus().setResultStatus(acqResultStatus);
						rpcCalculateResponseData.getCalculationStatus().setResultCode(acqResultCode);
						
						rpcCalculateResponseData.getFESDiagram().getFMax().add(acqFMax);
						rpcCalculateResponseData.getFESDiagram().getFMin().add(acqFMin);
						rpcCalculateResponseData.getFESDiagram().setFullnessCoefficient(acqFullnessCoefficient);
						rpcCalculateResponseData.getFESDiagram().setUpperLoadLine(acqUpperLoadLine);
						rpcCalculateResponseData.getFESDiagram().setLowerLoadLine(acqLowerLoadLine);
						
						rpcCalculateResponseData.getProduction().setTheoreticalProduction(acqTheoreticalProduction);
						rpcCalculateResponseData.getProduction().setLiquidVolumetricProduction(acqLiquidVolumetricProduction);
						rpcCalculateResponseData.getProduction().setOilVolumetricProduction(acqOilVolumetricProduction);
						rpcCalculateResponseData.getProduction().setWaterVolumetricProduction(acqWaterVolumetricProduction);
						rpcCalculateResponseData.getProduction().setAvailablePlungerStrokeVolumetricProduction(acqAvailablePlungerStrokeProd_v);
						rpcCalculateResponseData.getProduction().setPumpClearanceLeakVolumetricProduction(acqPumpClearanceLeakProd_v);
						rpcCalculateResponseData.getProduction().setTVLeakVolumetricProduction(acqTVLeakVolumetricProduction);
						rpcCalculateResponseData.getProduction().setSVLeakVolumetricProduction(acqSVLeakVolumetricProduction);
						rpcCalculateResponseData.getProduction().setGasInfluenceVolumetricProduction(acqGasInfluenceProd_v);
						
						rpcCalculateResponseData.getProduction().setLiquidWeightProduction(acqLiquidWeightProduction);
						rpcCalculateResponseData.getProduction().setOilWeightProduction(acqOilWeightProduction);
						rpcCalculateResponseData.getProduction().setWaterWeightProduction(acqWaterWeightProduction);
						rpcCalculateResponseData.getProduction().setAvailablePlungerStrokeWeightProduction(acqAvailablePlungerStrokeProd_w);
						rpcCalculateResponseData.getProduction().setPumpClearanceLeakWeightProduction(acqPumpClearanceLeakProd_w);
						rpcCalculateResponseData.getProduction().setTVLeakWeightProduction(acqTVLeakWeightProduction);
						rpcCalculateResponseData.getProduction().setSVLeakWeightProduction(acqSVLeakWeightProduction);
						rpcCalculateResponseData.getProduction().setGasInfluenceWeightProduction(acqGasInfluenceProd_w);
						
						rpcCalculateResponseData.getFESDiagram().setAvgWatt(acqAverageWatt);
						rpcCalculateResponseData.getSystemEfficiency().setPolishRodPower(acqPolishRodPower);
						rpcCalculateResponseData.getSystemEfficiency().setWaterPower(acqWaterPower);
						rpcCalculateResponseData.getSystemEfficiency().setSurfaceSystemEfficiency(acqSurfaceSystemEfficiency);
						rpcCalculateResponseData.getSystemEfficiency().setWellDownSystemEfficiency(acqWellDownSystemEfficiency);
						rpcCalculateResponseData.getSystemEfficiency().setSystemEfficiency(acqSystemEfficiency);
						rpcCalculateResponseData.getSystemEfficiency().setEnergyPer100mLift(acqEnergyPer100mLift);
						rpcCalculateResponseData.getFESDiagram().setArea(acqArea);
						
						rpcCalculateResponseData.getPumpEfficiency().setRodFlexLength(acqRodFlexLength);
						rpcCalculateResponseData.getPumpEfficiency().setTubingFlexLength(acqTubingFlexLength);
						rpcCalculateResponseData.getPumpEfficiency().setInertiaLength(acqInertiaLength);
						rpcCalculateResponseData.getPumpEfficiency().setPumpEff1(acqPumpEff1);
						rpcCalculateResponseData.getPumpEfficiency().setPumpEff2(acqPumpEff2);
						rpcCalculateResponseData.getPumpEfficiency().setPumpEff3(acqPumpEff3);
						rpcCalculateResponseData.getPumpEfficiency().setPumpEff4(acqPumpEff4);
						rpcCalculateResponseData.getPumpEfficiency().setPumpEff(acqPumpEff);
						
						rpcCalculateResponseData.getProduction().setCalcProducingfluidLevel(acqCalcProducingfluidLevel);
						rpcCalculateResponseData.getProduction().setLevelDifferenceValue(acqLevelDifferenceValue);
						
						rpcCalculateResponseData.getFESDiagram().setUpStrokeWattMax(acqUpStrokeWattMax);
						rpcCalculateResponseData.getFESDiagram().setDownStrokeWattMax(acqDownStrokeWattMax);
						rpcCalculateResponseData.getFESDiagram().setWattDegreeBalance(acqWattDegreeBalance);
						rpcCalculateResponseData.getFESDiagram().setUpStrokeIMax(acqUpStrokeIMax);
						rpcCalculateResponseData.getFESDiagram().setDownStrokeIMax(acqDownStrokeIMax);
						rpcCalculateResponseData.getFESDiagram().setIDegreeBalance(acqIDegreeBalance);
						rpcCalculateResponseData.getFESDiagram().setDeltaRadius(acqDeltaRadius);
						
						rpcCalculateResponseData.getProduction().setSubmergence(acqSubmergence);
						
						if(rpcCalculateRequestData.getProduction()!=null){
							rpcCalculateResponseData.getProduction().setWaterCut(rpcCalculateRequestData.getProduction().getWaterCut());
							rpcCalculateResponseData.getProduction().setProductionGasOilRatio(rpcCalculateRequestData.getProduction().getProductionGasOilRatio());
							rpcCalculateResponseData.getProduction().setTubingPressure(rpcCalculateRequestData.getProduction().getTubingPressure());
							rpcCalculateResponseData.getProduction().setCasingPressure(rpcCalculateRequestData.getProduction().getCasingPressure());
							rpcCalculateResponseData.getProduction().setWellHeadTemperature(rpcCalculateRequestData.getProduction().getWellHeadTemperature());
							rpcCalculateResponseData.getProduction().setPumpSettingDepth(rpcCalculateRequestData.getProduction().getPumpSettingDepth());
							rpcCalculateResponseData.getProduction().setProducingfluidLevel(rpcCalculateRequestData.getProduction().getProducingfluidLevel());
						}
						if(isAcqRPMData){
							rpcCalculateResponseData.setRPM(acqRPM);
						}
					}
					
					if(FESDiagramCalculate && checkSign==1){
						fesDiagramEnabled=true;
						if(FESDiagramAcqCount>0){
							if(rpcCalculateRequestData.getFESDiagram().getS().size()>FESDiagramAcqCount){
								List<Float> curveArr=new ArrayList<Float>();
								for(int i=0;i<FESDiagramAcqCount;i++){
									curveArr.add(rpcCalculateRequestData.getFESDiagram().getS().get(i));
							    }
							    rpcCalculateRequestData.getFESDiagram().setS(curveArr);
							}

							if(rpcCalculateRequestData.getFESDiagram().getF().size()>FESDiagramAcqCount){
								List<Float> curveArr=new ArrayList<Float>();
								for(int i=0;i<FESDiagramAcqCount;i++){
									curveArr.add(rpcCalculateRequestData.getFESDiagram().getF().get(i));
							    }
							    rpcCalculateRequestData.getFESDiagram().setF(curveArr);
							}
							
							if(rpcCalculateRequestData.getFESDiagram().getWatt().size()>FESDiagramAcqCount){
								List<Float> curveArr=new ArrayList<Float>();
								for(int i=0;i<FESDiagramAcqCount;i++){
									curveArr.add(rpcCalculateRequestData.getFESDiagram().getWatt().get(i));
							    }
							    rpcCalculateRequestData.getFESDiagram().setWatt(curveArr);
							}
							
							if(rpcCalculateRequestData.getFESDiagram().getI().size()>FESDiagramAcqCount){
								List<Float> curveArr=new ArrayList<Float>();
								for(int i=0;i<FESDiagramAcqCount;i++){
									curveArr.add(rpcCalculateRequestData.getFESDiagram().getI().get(i));
							    }
							    rpcCalculateRequestData.getFESDiagram().setI(curveArr);
							}
							
							
							if(rpcCalculateRequestData.getProduction()!=null && rpcCalculateRequestData.getFluidPVT()!=null){
//								float weightWaterCut=CalculateUtils.volumeWaterCutToWeightWaterCut(rpcCalculateRequestData.getProduction().getWaterCut(), rpcCalculateRequestData.getFluidPVT().getCrudeOilDensity(), rpcCalculateRequestData.getFluidPVT().getWaterDensity());
								float weightWaterCut=rpcCalculateRequestData.getProduction().getWaterCut();
								rpcCalculateRequestData.getProduction().setWeightWaterCut(weightWaterCut);
								deviceInfo.getRpcCalculateRequestData().getProduction().setWaterCut(weightWaterCut);
							}
						}
						rpcCalculateResponseData=CalculateUtils.fesDiagramCalculate(gson.toJson(rpcCalculateRequestData));
						
						if(rpcCalculateResponseData!=null && isAcqRPMData){
							rpcCalculateResponseData.setRPM(acqRPM);
						}
					}
					//计算结果回写
					if(dataWriteBackConfig!=null && dataWriteBackConfig.isEnable() && rpcCalculateResponseData!=null && checkSign==1){
						ThreadPool executor = new ThreadPool("DiagramDataWriteBack",
								Config.getInstance().configFile.getAp().getThreadPool().getDataWriteBack().getCorePoolSize(), 
								Config.getInstance().configFile.getAp().getThreadPool().getDataWriteBack().getMaximumPoolSize(), 
								Config.getInstance().configFile.getAp().getThreadPool().getDataWriteBack().getKeepAliveTime(), 
								TimeUnit.SECONDS, 
								Config.getInstance().configFile.getAp().getThreadPool().getDataWriteBack().getWattingCount());
						executor.execute(new OuterDatabaseSyncTask.DiagramDataWriteBackThread(rpcCalculateResponseData));
					}
					
					if(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1){
						workType=MemoryDataManagerTask.getWorkTypeByCode(rpcCalculateResponseData.getCalculationStatus().getResultCode()+"");
						RPCCalculateResponseData responseResultData =new RPCCalculateResponseData(); 
						responseResultData.init();
						
						responseResultData.setWellName(rpcCalculateResponseData.getWellName());
						responseResultData.setRPM(rpcCalculateResponseData.getRPM());
						responseResultData.getCalculationStatus().setResultCode(rpcCalculateResponseData.getCalculationStatus().getResultCode());
						if(responseResultData.getFESDiagram()!=null && rpcCalculateResponseData.getFESDiagram()!=null){
							responseResultData.getFESDiagram().setAcqTime(rpcCalculateResponseData.getFESDiagram().getAcqTime());
							responseResultData.getFESDiagram().setStroke(rpcCalculateResponseData.getFESDiagram().getStroke());
							responseResultData.getFESDiagram().setSPM(rpcCalculateResponseData.getFESDiagram().getSPM());
							responseResultData.getFESDiagram().setFMax(rpcCalculateResponseData.getFESDiagram().getFMax());;
							responseResultData.getFESDiagram().setFMin(rpcCalculateResponseData.getFESDiagram().getFMin());
							responseResultData.getFESDiagram().setFullnessCoefficient(rpcCalculateResponseData.getFESDiagram().getFullnessCoefficient());
							
							responseResultData.getFESDiagram().setWattDegreeBalance(rpcCalculateResponseData.getFESDiagram().getWattDegreeBalance());
							responseResultData.getFESDiagram().setIDegreeBalance(rpcCalculateResponseData.getFESDiagram().getIDegreeBalance());
							responseResultData.getFESDiagram().setDeltaRadius(rpcCalculateResponseData.getFESDiagram().getDeltaRadius());
						}
						
						if(responseResultData.getProduction()!=null && rpcCalculateResponseData.getProduction()!=null){
							responseResultData.getProduction().setTheoreticalProduction(rpcCalculateResponseData.getProduction().getTheoreticalProduction());
							responseResultData.getProduction().setLiquidVolumetricProduction(rpcCalculateResponseData.getProduction().getLiquidVolumetricProduction());
							responseResultData.getProduction().setOilVolumetricProduction(rpcCalculateResponseData.getProduction().getOilVolumetricProduction());
							responseResultData.getProduction().setWaterVolumetricProduction(rpcCalculateResponseData.getProduction().getWaterVolumetricProduction());
							responseResultData.getProduction().setLiquidWeightProduction(rpcCalculateResponseData.getProduction().getLiquidWeightProduction());
							responseResultData.getProduction().setOilWeightProduction(rpcCalculateResponseData.getProduction().getOilWeightProduction());
							responseResultData.getProduction().setWaterWeightProduction(rpcCalculateResponseData.getProduction().getWaterWeightProduction());
							responseResultData.getProduction().setWaterCut(rpcCalculateResponseData.getProduction().getWaterCut());
							
							responseResultData.getProduction().setPumpSettingDepth(rpcCalculateResponseData.getProduction().getPumpSettingDepth());
							responseResultData.getProduction().setProducingfluidLevel(rpcCalculateResponseData.getProduction().getProducingfluidLevel());
							responseResultData.getProduction().setCalcProducingfluidLevel(rpcCalculateResponseData.getProduction().getCalcProducingfluidLevel());
							responseResultData.getProduction().setLevelDifferenceValue(rpcCalculateResponseData.getProduction().getLevelDifferenceValue());
							responseResultData.getProduction().setSubmergence(rpcCalculateResponseData.getProduction().getSubmergence());
							
							responseResultData.getProduction().setTubingPressure(rpcCalculateResponseData.getProduction().getTubingPressure());
							responseResultData.getProduction().setCasingPressure(rpcCalculateResponseData.getProduction().getCasingPressure());
							if(rpcCalculateRequestData.getProduction()!=null){
								responseResultData.getProduction().setWeightWaterCut(rpcCalculateRequestData.getProduction().getWeightWaterCut());
							}
						}
						
						if(responseResultData.getSystemEfficiency()!=null && rpcCalculateResponseData.getSystemEfficiency()!=null){
							responseResultData.getSystemEfficiency().setSystemEfficiency(rpcCalculateResponseData.getSystemEfficiency().getSystemEfficiency());
							responseResultData.getSystemEfficiency().setSurfaceSystemEfficiency(rpcCalculateResponseData.getSystemEfficiency().getSurfaceSystemEfficiency());
							responseResultData.getSystemEfficiency().setWellDownSystemEfficiency(rpcCalculateResponseData.getSystemEfficiency().getWellDownSystemEfficiency());
							responseResultData.getSystemEfficiency().setEnergyPer100mLift(rpcCalculateResponseData.getSystemEfficiency().getEnergyPer100mLift());
						}
						
						if(responseResultData.getPumpEfficiency()!=null && rpcCalculateResponseData.getPumpEfficiency()!=null){
							responseResultData.getPumpEfficiency().setPumpEff1(rpcCalculateResponseData.getPumpEfficiency().getPumpEff1());
							responseResultData.getPumpEfficiency().setPumpEff2(rpcCalculateResponseData.getPumpEfficiency().getPumpEff2());
							responseResultData.getPumpEfficiency().setPumpEff3(rpcCalculateResponseData.getPumpEfficiency().getPumpEff3());
							responseResultData.getPumpEfficiency().setPumpEff4(rpcCalculateResponseData.getPumpEfficiency().getPumpEff4());
							responseResultData.getPumpEfficiency().setPumpEff(rpcCalculateResponseData.getPumpEfficiency().getPumpEff());
						}
						
						//删除非当天采集的功图数据
						if(deviceTodayData!=null){
							Iterator<RPCCalculateResponseData> it = deviceTodayData.getRPCCalculateList().iterator();
							while(it.hasNext()){
								RPCCalculateResponseData responseData=(RPCCalculateResponseData)it.next();
								if(responseData.getFESDiagram()==null 
										|| !StringManagerUtils.isNotNull(responseData.getFESDiagram().getAcqTime())
										|| !StringManagerUtils.timeMatchDate(responseData.getFESDiagram().getAcqTime(), date, Config.getInstance().configFile.getAp().getReport().getOffsetHour())
										){
									it.remove();
								}
							}
							deviceTodayData.getRPCCalculateList().add(responseResultData);
						}else{
							deviceTodayData=new RPCDeviceTodayData();
							deviceTodayData.setId(deviceInfo.getId());
							deviceTodayData.setRPCCalculateList(new ArrayList<RPCCalculateResponseData>());
							deviceTodayData.setAcquisitionItemInfoList(new ArrayList<AcquisitionItemInfo>());
							deviceTodayData.getRPCCalculateList().add(responseResultData);
						}
					}
					
					
					calItemResolutionDataList=getFESDiagramCalItemData(rpcCalculateRequestData,rpcCalculateResponseData,calItemResolutionDataList);
					if(workType!=null){
						calItemResolutionDataList.add(new ProtocolItemResolutionData("优化建议","优化建议",workType.getOptimizationSuggestion(),workType.getOptimizationSuggestion(),"","optimizationSuggestion","","","","",1,1));
					}else{
						calItemResolutionDataList.add(new ProtocolItemResolutionData("优化建议","优化建议","","","","optimizationSuggestion","","","","",1,1));
					}
					//更新内存数据
					//功图
					if(rpcCalculateResponseData!=null){
						deviceInfo.setResultStatus(rpcCalculateResponseData.getCalculationStatus().getResultStatus());
						deviceInfo.setResultCode(rpcCalculateResponseData.getCalculationStatus().getResultCode());
					}else{
						deviceInfo.setResultStatus(0);
						deviceInfo.setResultCode(0);
					}
					
					//更新液面反演值
					if(rpcCalculateResponseData!=null && rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1 
							&& rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232
							&& rpcCalculateResponseData.getProduction().getProducingfluidLevel()>=0
							&& FESDiagramCalculate
							&& !updateTotalDataSql.toLowerCase().contains("producingfluidLevel")){
						updateTotalDataSql+=",t.producingfluidLevel= "+rpcCalculateResponseData.getProduction().getProducingfluidLevel();
					}
					
					//同时进行了时率计算和功图计算，则进行功图汇总计算
					if(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&timeEffResponseData!=null && timeEffResponseData.getResultStatus()==1 && deviceTodayData!=null){
						//排序
						Collections.sort(deviceTodayData.getRPCCalculateList());
						totalRequestData=CalculateUtils.getFESDiagramTotalRequestData(date, deviceInfo,deviceTodayData);
						
						type = new TypeToken<TotalAnalysisRequestData>() {}.getType();
						totalAnalysisRequestData = gson.fromJson(totalRequestData, type);
						
						totalAnalysisResponseData=CalculateUtils.totalCalculate(totalRequestData);
					}
					
					if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产液量","日累计产液量",totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+"",totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+"","","liquidVolumetricProduction_l","","","","m^3/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产油量","日累计产油量",totalAnalysisResponseData.getOilVolumetricProduction().getValue()+"",totalAnalysisResponseData.getOilVolumetricProduction().getValue()+"","","oilVolumetricProduction_l","","","","m^3/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产水量","日累计产水量",totalAnalysisResponseData.getWaterVolumetricProduction().getValue()+"",totalAnalysisResponseData.getWaterVolumetricProduction().getValue()+"","","waterVolumetricProduction_l","","","","m^3/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产液量","日累计产液量",totalAnalysisResponseData.getLiquidWeightProduction().getValue()+"",totalAnalysisResponseData.getLiquidWeightProduction().getValue()+"","","liquidWeightProduction_l","","","","t/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产油量","日累计产油量",totalAnalysisResponseData.getOilWeightProduction().getValue()+"",totalAnalysisResponseData.getOilWeightProduction().getValue()+"","","oilWeightProduction_l","","","","t/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产水量","日累计产水量",totalAnalysisResponseData.getWaterWeightProduction().getValue()+"",totalAnalysisResponseData.getWaterWeightProduction().getValue()+"","","waterWeightProduction_l","","","","t/d",1,1));
						
						updateRealtimeData+=",t.liquidvolumetricproduction_l="+totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()
								+",t.oilvolumetricproduction_l="+totalAnalysisResponseData.getOilVolumetricProduction().getValue()
								+",t.watervolumetricproduction_l="+totalAnalysisResponseData.getWaterVolumetricProduction().getValue()
								+",t.liquidweightproduction_l="+totalAnalysisResponseData.getLiquidWeightProduction().getValue()
								+",t.oilweightproduction_l="+totalAnalysisResponseData.getOilWeightProduction().getValue()
								+",t.waterweightproduction_l="+totalAnalysisResponseData.getWaterWeightProduction().getValue();
						insertHistColumns+=",liquidvolumetricproduction_l,oilvolumetricproduction_l,watervolumetricproduction_l,"
								+ "liquidweightproduction_l,oilweightproduction_l,waterweightproduction_l";
						insertHistValue+=","+totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+","+totalAnalysisResponseData.getOilVolumetricProduction().getValue()+","+totalAnalysisResponseData.getWaterVolumetricProduction().getValue()
								+","+totalAnalysisResponseData.getLiquidWeightProduction().getValue()+","+totalAnalysisResponseData.getOilWeightProduction().getValue()+","+totalAnalysisResponseData.getWaterWeightProduction().getValue();
					}else{
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产液量","日累计产液量","","","","liquidVolumetricProduction_l","","","","m^3/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产油量","日累计产油量","","","","oilVolumetricProduction_l","","","","m^3/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产水量","日累计产水量","","","","waterVolumetricProduction_l","","","","m^3/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产液量","日累计产液量","","","","liquidWeightProduction_l","","","","t/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产油量","日累计产油量","","","","oilWeightProduction_l","","","","t/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产水量","日累计产水量","","","","waterWeightProduction_l","","","","t/d",1,1));
					}
					
					updateRealtimeData+=" where t.deviceId= "+deviceInfo.getId();
					insertHistSql="insert into "+historyTable+"("+insertHistColumns+")values("+insertHistValue+")";
					updateTotalDataSql+=" where t.deviceId= "+deviceInfo.getId()+" and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
					
					if(save && checkSign==1){
						int result=commonDataService.getBaseDao().updateOrDeleteBySql(updateRealtimeData);
						if(result==0){
							updateRealtimeData=insertHistSql.replace(historyTable, realtimeTable);
							result=commonDataService.getBaseDao().updateOrDeleteBySql(updateRealtimeData);
						}
						
						commonDataService.getBaseDao().updateOrDeleteBySql(insertHistSql);
						commonDataService.getBaseDao().updateOrDeleteBySql(updateTotalDataSql);
						
						if(commResponseData!=null&&commResponseData.getResultStatus()==1){
							List<String> clobCont=new ArrayList<String>();
							String updateRealRangeClobSql="update "+realtimeTable+" t set t.commrange=?";
							String updateHisRangeClobSql="update "+historyTable+" t set t.commrange=?";
							String updateTotalRangeClobSql="update "+totalDataTable+" t set t.commrange=?";
							clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
							if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
								updateRealRangeClobSql+=", t.runrange=?";
								updateHisRangeClobSql+=", t.runrange=?";
								updateTotalRangeClobSql+=", t.runrange=?";
								clobCont.add(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());
							}
							updateRealRangeClobSql+=" where t.deviceid="+deviceInfo.getId();
							updateHisRangeClobSql+=" where t.deviceid="+deviceInfo.getId() +" and t.acqTime="+"to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
							updateTotalRangeClobSql+=" where t.deviceId= "+deviceInfo.getId()+"and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
							commonDataService.getBaseDao().executeSqlUpdateClob(updateRealRangeClobSql,clobCont);
							commonDataService.getBaseDao().executeSqlUpdateClob(updateHisRangeClobSql,clobCont);
							commonDataService.getBaseDao().executeSqlUpdateClob(updateTotalRangeClobSql,clobCont);
						}
						
						if(FESDiagramCalculate || isAcqCalResultData){
							commonDataService.getBaseDao().saveAcqFESDiagramAndCalculateData(deviceInfo,rpcCalculateRequestData,rpcCalculateResponseData,fesDiagramEnabled);
						}
						
						if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){//保存汇总数据
							int recordCount=totalAnalysisRequestData.getAcqTime()!=null?totalAnalysisRequestData.getAcqTime().size():0;
							commonDataService.getBaseDao().saveFESDiagramTotalCalculateData(deviceInfo,totalAnalysisResponseData,totalAnalysisRequestData,date,recordCount);
						}
					}
					//放入内存数据库中
					if(jedis!=null && deviceTodayData!=null){
						jedis.hset("RPCDeviceTodayData".getBytes(), (deviceInfo.getId()+"").getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return rpcCalculateResponseData;
	}
	
	public PCPCalculateResponseData PCPDataProcessing(DeviceInfo deviceInfo,AcqGroup acqGroup,CommResponseData commResponseData,TimeEffResponseData timeEffResponseData,
			String acqTime,List<ProtocolItemResolutionData> calItemResolutionDataList,int runStatus,
			boolean save,int checkSign){
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		Jedis jedis=null;
		PCPDeviceTodayData deviceTodayData=null;
		PCPCalculateResponseData pcpCalculateResponseData=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			
			if(!jedis.exists("PCPDeviceTodayData".getBytes())){
				MemoryDataManagerTask.loadTodayRPMData(null,0);
			}
			if(jedis.hexists("PCPDeviceTodayData".getBytes(), (deviceInfo.getId()+"").getBytes())){
				deviceTodayData=(PCPDeviceTodayData) SerializeObjectUnils.unserizlize(jedis.hget("PCPDeviceTodayData".getBytes(), (deviceInfo.getId()+"").getBytes()));
			}
			
			if(!jedis.exists("pcpCalItemList".getBytes())){
				MemoryDataManagerTask.loadPCPCalculateItem();
			}
			
			if(!jedis.exists("ProtocolMappingColumn".getBytes())){
				MemoryDataManagerTask.loadProtocolMappingColumn();
			}
			
			Map<String, Object> dataModelMap=DataModelMap.getMapObject();
			if(!dataModelMap.containsKey("ProtocolMappingColumnByTitle")){
				MemoryDataManagerTask.loadProtocolMappingColumnByTitle();
			}
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
			
			String realtimeTable="tbl_pcpacqdata_latest";
			String historyTable="tbl_pcpacqdata_hist";
			String totalDataTable="tbl_pcpdailycalculationdata";
			if(acqGroup!=null){
				List<byte[]> pcpCalItemSet=null;
				if(jedis!=null){
					pcpCalItemSet= jedis.zrange("pcpCalItemList".getBytes(), 0, -1);
				}
				String protocolName="";
				AcqInstanceOwnItem acqInstanceOwnItem=null;
				if(jedis!=null&&jedis.hexists("AcqInstanceOwnItem".getBytes(), deviceInfo.getInstanceCode().getBytes())){
					acqInstanceOwnItem=(AcqInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AcqInstanceOwnItem".getBytes(), deviceInfo.getInstanceCode().getBytes()));
					protocolName=acqInstanceOwnItem.getProtocol();
				}
				
				ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
				
				if(protocol!=null){
					String date=StringManagerUtils.getCurrentTime("yyyy-MM-dd");
					if(!StringManagerUtils.timeMatchDate(acqTime, date, Config.getInstance().configFile.getAp().getReport().getOffsetHour())){
						date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(date),-1);
					}
					
					PCPCalculateRequestData pcpCalculateRequestData=new PCPCalculateRequestData();
					pcpCalculateRequestData.init();
					
					if(deviceInfo.getApplicationScenarios()==0){
						pcpCalculateRequestData.setScene("cbm");
					}else{
						pcpCalculateRequestData.setScene("oil");
					}
					updateRPMRequestData(pcpCalculateRequestData,deviceInfo);
					
					
					
					TotalAnalysisResponseData totalAnalysisResponseData=null;
					TotalAnalysisRequestData totalAnalysisRequestData=null;
					
					boolean isAcqRPM=false;
					
					String updateRealtimeData="update "+realtimeTable+" t set t.acqTime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),t.CommStatus=1,t.runStatus="+runStatus;
					
					
					String insertHistColumns="deviceid,acqTime,CommStatus,runStatus";
					String insertHistValue=deviceInfo.getId()+",to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),1,"+runStatus;
					String insertHistSql="";
					
					String updateTotalDataSql="update "+totalDataTable+" t set t.CommStatus=1,t.runStatus="+runStatus;
					
					if(commResponseData!=null&&commResponseData.getResultStatus()==1){
						updateRealtimeData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
								+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
						insertHistColumns+=",commTimeEfficiency,commTime";
						insertHistValue+=","+commResponseData.getCurrent().getCommEfficiency().getEfficiency()+","+commResponseData.getCurrent().getCommEfficiency().getTime();
						
						updateTotalDataSql+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
								+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
					}
					if(timeEffResponseData!=null && timeEffResponseData.getResultStatus()==1){
						updateRealtimeData+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
								+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
						insertHistColumns+=",runTimeEfficiency,runTime";
						insertHistValue+=","+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()+","+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
						updateTotalDataSql+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
								+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
					}
					
					for(int i=0;acqGroup.getAddr()!=null &&i<acqGroup.getAddr().size();i++){
						for(int j=0;j<protocol.getItems().size();j++){
							if(acqGroup.getAddr().get(i)==protocol.getItems().get(j).getAddr()){
								String value="";
								DataMapping dataMappingColumn=loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(j).getTitle());
								String columnName=dataMappingColumn.getMappingColumn();
								
								if(acqGroup.getValue()!=null&&acqGroup.getValue().size()>i&&acqGroup.getValue().get(i)!=null){
									value=StringManagerUtils.objectListToString(acqGroup.getValue().get(i), protocol.getItems().get(j));
								}
								String rawValue=value;
								String addr=protocol.getItems().get(j).getAddr()+"";
								String title=protocol.getItems().get(j).getTitle();
								String rawTitle=title;
								String columnDataType=protocol.getItems().get(j).getIFDataType();
								String resolutionMode=protocol.getItems().get(j).getResolutionMode()+"";
								String bitIndex="";
								String unit=protocol.getItems().get(j).getUnit();
								int alarmLevel=0;
								int sort=9999;
								
								if(StringManagerUtils.existAcqItem(acqInstanceOwnItem.getItemList(), title, false)){
									String saveValue=rawValue;
									if(protocol.getItems().get(j).getQuantity()==1&&rawValue.length()>50){
										saveValue=rawValue.substring(0, 50);
									}
									
								
									
									if("TubingPressure".equalsIgnoreCase(dataMappingColumn.getCalColumn())){//油压
										if(StringManagerUtils.isNum(rawValue) || StringManagerUtils.isNumber(rawValue)){
											if(pcpCalculateRequestData.getProduction()!=null){
												pcpCalculateRequestData.getProduction().setTubingPressure(StringManagerUtils.stringToFloat(rawValue));
											}
											if(deviceInfo.getPcpCalculateRequestData().getProduction()!=null){
												deviceInfo.getPcpCalculateRequestData().getProduction().setTubingPressure(StringManagerUtils.stringToFloat(rawValue));
											}
											updateTotalDataSql+=",t."+dataMappingColumn.getCalColumn()+"="+StringManagerUtils.stringToFloat(rawValue)+"";
										}
									}else if("CasingPressure".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										if(StringManagerUtils.isNum(rawValue) || StringManagerUtils.isNumber(rawValue)){
											if(pcpCalculateRequestData.getProduction()!=null){
												pcpCalculateRequestData.getProduction().setCasingPressure(StringManagerUtils.stringToFloat(rawValue));
											}
											if(deviceInfo.getPcpCalculateRequestData().getProduction()!=null){
												deviceInfo.getPcpCalculateRequestData().getProduction().setCasingPressure(StringManagerUtils.stringToFloat(rawValue));
											}
											updateTotalDataSql+=",t."+dataMappingColumn.getCalColumn()+"="+StringManagerUtils.stringToFloat(rawValue)+"";
										}
									}else if("WellHeadTemperature".equalsIgnoreCase(dataMappingColumn.getCalColumn())){//油压
										if(StringManagerUtils.isNum(rawValue) || StringManagerUtils.isNumber(rawValue)){
											if(pcpCalculateRequestData.getProduction()!=null){
												pcpCalculateRequestData.getProduction().setWellHeadTemperature(StringManagerUtils.stringToFloat(rawValue));
											}
											if(deviceInfo.getPcpCalculateRequestData().getProduction()!=null){
												deviceInfo.getPcpCalculateRequestData().getProduction().setWellHeadTemperature(StringManagerUtils.stringToFloat(rawValue));
											}
										}
									}else if("ProducingfluidLevel".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										if(StringManagerUtils.isNum(rawValue) || StringManagerUtils.isNumber(rawValue)){
											if(pcpCalculateRequestData.getProduction()!=null){
												pcpCalculateRequestData.getProduction().setProducingfluidLevel(StringManagerUtils.stringToFloat(rawValue));
											}
											if(deviceInfo.getPcpCalculateRequestData().getProduction()!=null){
												deviceInfo.getPcpCalculateRequestData().getProduction().setProducingfluidLevel(StringManagerUtils.stringToFloat(rawValue));
											}
											updateTotalDataSql+=",t."+dataMappingColumn.getCalColumn()+"="+StringManagerUtils.stringToFloat(rawValue)+"";
										}
									}else if("BottomHolePressure".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										if(StringManagerUtils.isNum(rawValue) || StringManagerUtils.isNumber(rawValue)){
											updateTotalDataSql+=",t."+dataMappingColumn.getCalColumn()+"="+StringManagerUtils.stringToFloat(rawValue)+"";
										}
									}else if("VolumeWaterCut".equalsIgnoreCase(dataMappingColumn.getCalColumn()) || "WaterCut".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										if(StringManagerUtils.isNum(rawValue) || StringManagerUtils.isNumber(rawValue)){
											if(pcpCalculateRequestData.getProduction()!=null){
												pcpCalculateRequestData.getProduction().setWaterCut(StringManagerUtils.stringToFloat(rawValue));
											}
											if(deviceInfo.getPcpCalculateRequestData().getProduction()!=null){
												deviceInfo.getPcpCalculateRequestData().getProduction().setWaterCut(StringManagerUtils.stringToFloat(rawValue));
											}
										}
									}else if("RPM".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
										if(StringManagerUtils.isNum(rawValue) || StringManagerUtils.isNumber(rawValue)){
											isAcqRPM=true;
											pcpCalculateRequestData.setRPM(StringManagerUtils.stringToFloat(rawValue));
										}
									}
								}
								break;
							}
						}
					}
					
					//进行转速计算
					if(isAcqRPM && checkSign==1){
						if(pcpCalculateRequestData.getProduction()!=null && pcpCalculateRequestData.getFluidPVT()!=null){
							float weightWaterCut=CalculateUtils.volumeWaterCutToWeightWaterCut(pcpCalculateRequestData.getProduction().getWaterCut(), pcpCalculateRequestData.getFluidPVT().getCrudeOilDensity(), pcpCalculateRequestData.getFluidPVT().getWaterDensity());
							pcpCalculateRequestData.getProduction().setWeightWaterCut(weightWaterCut);
						}
						pcpCalculateResponseData=CalculateUtils.rpmCalculate(gson.toJson(pcpCalculateRequestData));
						if(pcpCalculateResponseData!=null&&pcpCalculateResponseData.getCalculationStatus().getResultStatus()==1){
							PCPCalculateResponseData responseResultData =new PCPCalculateResponseData(); 
							responseResultData.init();
							
							responseResultData.setWellName(pcpCalculateResponseData.getWellName());
							responseResultData.setAcqTime(pcpCalculateResponseData.getAcqTime());
							responseResultData.setRPM(pcpCalculateResponseData.getRPM());
							responseResultData.getCalculationStatus().setResultCode(pcpCalculateResponseData.getCalculationStatus().getResultCode());
							
							if(responseResultData.getProduction()!=null && pcpCalculateResponseData.getProduction()!=null){
								responseResultData.getProduction().setTheoreticalProduction(pcpCalculateResponseData.getProduction().getTheoreticalProduction());
								responseResultData.getProduction().setLiquidVolumetricProduction(pcpCalculateResponseData.getProduction().getLiquidVolumetricProduction());
								responseResultData.getProduction().setOilVolumetricProduction(pcpCalculateResponseData.getProduction().getOilVolumetricProduction());
								responseResultData.getProduction().setWaterVolumetricProduction(pcpCalculateResponseData.getProduction().getWaterVolumetricProduction());
								responseResultData.getProduction().setLiquidWeightProduction(pcpCalculateResponseData.getProduction().getLiquidWeightProduction());
								responseResultData.getProduction().setOilWeightProduction(pcpCalculateResponseData.getProduction().getOilWeightProduction());
								responseResultData.getProduction().setWaterWeightProduction(pcpCalculateResponseData.getProduction().getWaterWeightProduction());
								responseResultData.getProduction().setWaterCut(pcpCalculateResponseData.getProduction().getWaterCut());
								
								responseResultData.getProduction().setPumpSettingDepth(pcpCalculateResponseData.getProduction().getPumpSettingDepth());
								responseResultData.getProduction().setProducingfluidLevel(pcpCalculateResponseData.getProduction().getProducingfluidLevel());
								responseResultData.getProduction().setSubmergence(pcpCalculateResponseData.getProduction().getSubmergence());
								responseResultData.getProduction().setTubingPressure(pcpCalculateResponseData.getProduction().getTubingPressure());
								responseResultData.getProduction().setCasingPressure(pcpCalculateResponseData.getProduction().getCasingPressure());
								
								if(pcpCalculateRequestData.getProduction()!=null){
									responseResultData.getProduction().setWeightWaterCut(pcpCalculateRequestData.getProduction().getWeightWaterCut());
								}
							}
							
							if(responseResultData.getSystemEfficiency()!=null && pcpCalculateResponseData.getSystemEfficiency()!=null){
								responseResultData.getSystemEfficiency().setSystemEfficiency(pcpCalculateResponseData.getSystemEfficiency().getSystemEfficiency());
								responseResultData.getSystemEfficiency().setEnergyPer100mLift(pcpCalculateResponseData.getSystemEfficiency().getEnergyPer100mLift());
							}
							
							if(responseResultData.getPumpEfficiency()!=null && pcpCalculateResponseData.getPumpEfficiency()!=null){
								responseResultData.getPumpEfficiency().setPumpEff1(pcpCalculateResponseData.getPumpEfficiency().getPumpEff1());
								responseResultData.getPumpEfficiency().setPumpEff2(pcpCalculateResponseData.getPumpEfficiency().getPumpEff2());
								responseResultData.getPumpEfficiency().setPumpEff(pcpCalculateResponseData.getPumpEfficiency().getPumpEff());
							}
							
							
							//删除非当天采集的转速数据
							if(deviceTodayData!=null){
								Iterator<PCPCalculateResponseData> it = deviceTodayData.getPCPCalculateList().iterator();
								while(it.hasNext()){
									PCPCalculateResponseData responseData=(PCPCalculateResponseData)it.next();
									if(!StringManagerUtils.isNotNull(responseData.getAcqTime())
											|| !StringManagerUtils.timeMatchDate(responseData.getAcqTime(), date, Config.getInstance().configFile.getAp().getReport().getOffsetHour())  ){
										it.remove();
									}
								}
								deviceTodayData.getPCPCalculateList().add(responseResultData);
							}else{
								deviceTodayData=new PCPDeviceTodayData();
								deviceTodayData.setId(deviceInfo.getId());
								deviceTodayData.setPCPCalculateList(new ArrayList<PCPCalculateResponseData>());
								deviceTodayData.setAcquisitionItemInfoList(new ArrayList<AcquisitionItemInfo>());
								deviceTodayData.getPCPCalculateList().add(responseResultData);
							}
						}
					}
					
					calItemResolutionDataList=getRPMCalItemData(pcpCalculateRequestData,pcpCalculateResponseData,calItemResolutionDataList);
					
					
					//同时进行了时率计算和转速计算，则进行转速汇总计算
					if(pcpCalculateResponseData!=null&&pcpCalculateResponseData.getCalculationStatus().getResultStatus()==1&&timeEffResponseData!=null && timeEffResponseData.getResultStatus()==1 && deviceTodayData!=null){
						//排序
						Collections.sort(deviceTodayData.getPCPCalculateList());
						String totalRequestData=CalculateUtils.getRPMTotalRequestData(date, deviceInfo,deviceTodayData);
						type = new TypeToken<TotalAnalysisRequestData>() {}.getType();
						totalAnalysisRequestData = gson.fromJson(totalRequestData, type);
						totalAnalysisResponseData=CalculateUtils.totalCalculate(totalRequestData);
					}
					
					if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产液量","日累计产液量",totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+"",totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+"","","liquidVolumetricProduction_l","","","","m^3/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产油量","日累计产油量",totalAnalysisResponseData.getOilVolumetricProduction().getValue()+"",totalAnalysisResponseData.getOilVolumetricProduction().getValue()+"","","oilVolumetricProduction_l","","","","m^3/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产水量","日累计产水量",totalAnalysisResponseData.getWaterVolumetricProduction().getValue()+"",totalAnalysisResponseData.getWaterVolumetricProduction().getValue()+"","","waterVolumetricProduction_l","","","","m^3/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产液量","日累计产液量",totalAnalysisResponseData.getLiquidWeightProduction().getValue()+"",totalAnalysisResponseData.getLiquidWeightProduction().getValue()+"","","liquidWeightProduction_l","","","","t/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产油量","日累计产油量",totalAnalysisResponseData.getOilWeightProduction().getValue()+"",totalAnalysisResponseData.getOilWeightProduction().getValue()+"","","oilWeightProduction_l","","","","t/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产水量","日累计产水量",totalAnalysisResponseData.getWaterWeightProduction().getValue()+"",totalAnalysisResponseData.getWaterWeightProduction().getValue()+"","","waterWeightProduction_l","","","","t/d",1,1));
						

						updateRealtimeData+=",t.liquidvolumetricproduction_l="+totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()
								+",t.oilvolumetricproduction_l="+totalAnalysisResponseData.getOilVolumetricProduction().getValue()
								+",t.watervolumetricproduction_l="+totalAnalysisResponseData.getWaterVolumetricProduction().getValue()
								+",t.liquidweightproduction_l="+totalAnalysisResponseData.getLiquidWeightProduction().getValue()
								+",t.oilweightproduction_l="+totalAnalysisResponseData.getOilWeightProduction().getValue()
								+",t.waterweightproduction_l="+totalAnalysisResponseData.getWaterWeightProduction().getValue();
						insertHistColumns+=",liquidvolumetricproduction_l,oilvolumetricproduction_l,watervolumetricproduction_l,"
								+ "liquidweightproduction_l,oilweightproduction_l,waterweightproduction_l";
						insertHistValue+=","+totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+","+totalAnalysisResponseData.getOilVolumetricProduction().getValue()+","+totalAnalysisResponseData.getWaterVolumetricProduction().getValue()
								+","+totalAnalysisResponseData.getLiquidWeightProduction().getValue()+","+totalAnalysisResponseData.getOilWeightProduction().getValue()+","+totalAnalysisResponseData.getWaterWeightProduction().getValue();
					}else{
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产液量","日累计产液量","","","","liquidVolumetricProduction_l","","","","m^3/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产油量","日累计产油量","","","","oilVolumetricProduction_l","","","","m^3/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产水量","日累计产水量","","","","waterVolumetricProduction_l","","","","m^3/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产液量","日累计产液量","","","","liquidWeightProduction_l","","","","t/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产油量","日累计产油量","","","","oilWeightProduction_l","","","","t/d",1,1));
						calItemResolutionDataList.add(new ProtocolItemResolutionData("日累计产水量","日累计产水量","","","","waterWeightProduction_l","","","","t/d",1,1));
					}
					
					updateRealtimeData+=" where t.deviceId= "+deviceInfo.getId();
					insertHistSql="insert into "+historyTable+"("+insertHistColumns+")values("+insertHistValue+")";
					updateTotalDataSql+=" where t.deviceId= "+deviceInfo.getId()+" and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
					
					if(save && checkSign==1){
						int result=commonDataService.getBaseDao().updateOrDeleteBySql(updateRealtimeData);
						if(result==0){
							updateRealtimeData=insertHistSql.replace(historyTable, realtimeTable);
							result=commonDataService.getBaseDao().updateOrDeleteBySql(updateRealtimeData);
						}
						commonDataService.getBaseDao().updateOrDeleteBySql(insertHistSql);
						commonDataService.getBaseDao().updateOrDeleteBySql(updateTotalDataSql);
						
						if(commResponseData!=null&&commResponseData.getResultStatus()==1){
							List<String> clobCont=new ArrayList<String>();
							String updateRealRangeClobSql="update "+realtimeTable+" t set t.commrange=?";
							String updateHisRangeClobSql="update "+historyTable+" t set t.commrange=?";
							String updateTotalRangeClobSql="update "+totalDataTable+" t set t.commrange=?";
							clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
							if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
								updateRealRangeClobSql+=", t.runrange=?";
								updateHisRangeClobSql+=", t.runrange=?";
								updateTotalRangeClobSql+=", t.runrange=?";
								clobCont.add(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());
							}
							updateRealRangeClobSql+=" where t.deviceid="+deviceInfo.getId();
							updateHisRangeClobSql+=" where t.deviceid="+deviceInfo.getId() +" and t.acqTime="+"to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
							updateTotalRangeClobSql+=" where t.deviceId= "+deviceInfo.getId()+"and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
							commonDataService.getBaseDao().executeSqlUpdateClob(updateRealRangeClobSql,clobCont);
							commonDataService.getBaseDao().executeSqlUpdateClob(updateHisRangeClobSql,clobCont);
							commonDataService.getBaseDao().executeSqlUpdateClob(updateTotalRangeClobSql,clobCont);
						}
						
						commonDataService.getBaseDao().saveAcqRPMAndCalculateData(deviceInfo,pcpCalculateRequestData,pcpCalculateResponseData);
						if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){//保存汇总数据
							int recordCount=totalAnalysisRequestData.getAcqTime()!=null?totalAnalysisRequestData.getAcqTime().size():0;
							commonDataService.getBaseDao().saveRPMTotalCalculateData(deviceInfo,totalAnalysisResponseData,totalAnalysisRequestData,date,recordCount);
						}
					}
					
					if(jedis!=null && deviceTodayData!=null){
						jedis.hset("PCPDeviceTodayData".getBytes(), (deviceInfo.getId()+"").getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		
		return pcpCalculateResponseData;
	}
	
	public static List<ProtocolItemResolutionData> getRPCInputItemData(DeviceInfo deviceInfo){
		List<ProtocolItemResolutionData> rpcInputItemList=new ArrayList<ProtocolItemResolutionData>();
		String reservoirName="油层";
		String tubingPressureName="油压";
		if(deviceInfo.getApplicationScenarios()==0){
			reservoirName="煤层";
			tubingPressureName="管压";
		}
		if(deviceInfo!=null && deviceInfo.getRpcCalculateRequestData()!=null && deviceInfo.getRpcCalculateRequestData().getFluidPVT()!=null){
			rpcInputItemList.add(new ProtocolItemResolutionData("原油密度","原油密度",deviceInfo.getRpcCalculateRequestData().getFluidPVT().getCrudeOilDensity()+"",deviceInfo.getRpcCalculateRequestData().getFluidPVT().getCrudeOilDensity()+"","","CrudeOilDensity","","","","g/cm^3",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("水密度","水密度",deviceInfo.getRpcCalculateRequestData().getFluidPVT().getWaterDensity()+"",deviceInfo.getRpcCalculateRequestData().getFluidPVT().getWaterDensity()+"","","WaterDensity","","","","g/cm^3",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("天然气相对密度","天然气相对密度",deviceInfo.getRpcCalculateRequestData().getFluidPVT().getNaturalGasRelativeDensity()+"",deviceInfo.getRpcCalculateRequestData().getFluidPVT().getNaturalGasRelativeDensity()+"","","NaturalGasRelativeDensity","","","","",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("饱和压力","饱和压力",deviceInfo.getRpcCalculateRequestData().getFluidPVT().getSaturationPressure()+"",deviceInfo.getRpcCalculateRequestData().getFluidPVT().getSaturationPressure()+"","","SaturationPressure","","","","MPa",1,3));
		}else{
			rpcInputItemList.add(new ProtocolItemResolutionData("原油密度","原油密度","","","","CrudeOilDensity","","","","g/cm^3",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("水密度","水密度","","","","WaterDensity","","","","g/cm^3",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("天然气相对密度","天然气相对密度","","","","NaturalGasRelativeDensity","","","","",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("饱和压力","饱和压力","","","","SaturationPressure","","","","MPa",1,3));
		}
		
		if(deviceInfo!=null && deviceInfo.getRpcCalculateRequestData()!=null && deviceInfo.getRpcCalculateRequestData().getReservoir()!=null){
			rpcInputItemList.add(new ProtocolItemResolutionData(reservoirName+"中部深度",reservoirName+"中部深度",deviceInfo.getRpcCalculateRequestData().getReservoir().getDepth()+"",deviceInfo.getRpcCalculateRequestData().getReservoir().getDepth()+"","","ReservoirDepth","","","","m",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData(reservoirName+"中部温度",reservoirName+"中部温度",deviceInfo.getRpcCalculateRequestData().getReservoir().getTemperature()+"",deviceInfo.getRpcCalculateRequestData().getReservoir().getTemperature()+"","","ReservoirTemperature","","","","℃",1,3));
		}else{
			rpcInputItemList.add(new ProtocolItemResolutionData(reservoirName+"中部深度",reservoirName+"中部深度","","","","ReservoirDepth","","","","m",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData(reservoirName+"中部温度",reservoirName+"中部温度","","","","ReservoirTemperature","","","","℃",1,3));
		}
		
		if(deviceInfo!=null && deviceInfo.getRpcCalculateRequestData()!=null && deviceInfo.getRpcCalculateRequestData().getProduction()!=null){
			rpcInputItemList.add(new ProtocolItemResolutionData(tubingPressureName,tubingPressureName,deviceInfo.getRpcCalculateRequestData().getProduction().getTubingPressure()+"",deviceInfo.getRpcCalculateRequestData().getProduction().getTubingPressure()+"","","TubingPressure","","","","MPa",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("套压","套压",deviceInfo.getRpcCalculateRequestData().getProduction().getCasingPressure()+"",deviceInfo.getRpcCalculateRequestData().getProduction().getCasingPressure()+"","","CasingPressure","","","","MPa",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("井口温度","井口温度",deviceInfo.getRpcCalculateRequestData().getProduction().getWellHeadTemperature()+"",deviceInfo.getRpcCalculateRequestData().getProduction().getWellHeadTemperature()+"","","WellHeadTemperature","","","","℃",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("含水率","含水率",deviceInfo.getRpcCalculateRequestData().getProduction().getWaterCut()+"",deviceInfo.getRpcCalculateRequestData().getProduction().getWaterCut()+"","","WaterCut","","","","%",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("生产气油比","生产气油比",deviceInfo.getRpcCalculateRequestData().getProduction().getProductionGasOilRatio()+"",deviceInfo.getRpcCalculateRequestData().getProduction().getProductionGasOilRatio()+"","","ProductionGasOilRatio","","","","m^3/t",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("动液面","动液面",deviceInfo.getRpcCalculateRequestData().getProduction().getProducingfluidLevel()+"",deviceInfo.getRpcCalculateRequestData().getProduction().getProducingfluidLevel()+"","","ProducingfluidLevel","","","","m",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("泵挂","泵挂",deviceInfo.getRpcCalculateRequestData().getProduction().getPumpSettingDepth()+"",deviceInfo.getRpcCalculateRequestData().getProduction().getPumpSettingDepth()+"","","PumpSettingDepth","","","","m",1,3));
		}else{
			rpcInputItemList.add(new ProtocolItemResolutionData("油压","油压","","","","TubingPressure","","","","MPa",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("套压","套压","","","","CasingPressure","","","","MPa",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("井口温度","井口温度","","","","WellHeadTemperature","","","","℃",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("含水率","含水率","","","","WaterCut","","","","%",1,1));
			rpcInputItemList.add(new ProtocolItemResolutionData("生产气油比","生产气油比","","","","ProductionGasOilRatio","","","","m^3/t",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("动液面","动液面","","","","ProducingfluidLevel","","","","m",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("泵挂","泵挂","","","","PumpSettingDepth","","","","m",1,3));
		}
		
		if(deviceInfo!=null && deviceInfo.getRpcCalculateRequestData()!=null && deviceInfo.getRpcCalculateRequestData().getPump()!=null){
			rpcInputItemList.add(new ProtocolItemResolutionData("泵径","泵径",deviceInfo.getRpcCalculateRequestData().getPump().getPumpBoreDiameter()*1000+"",deviceInfo.getRpcCalculateRequestData().getPump().getPumpBoreDiameter()*1000+"","","PumpBoreDiameter","","","","mm",1,3));
		}else{
			rpcInputItemList.add(new ProtocolItemResolutionData("泵径","泵径","","","","PumpBoreDiameter","","","","mm",1,3));
		}
		
		if(deviceInfo!=null && deviceInfo.getRpcCalculateRequestData().getManualIntervention()!=null){
			rpcInputItemList.add(new ProtocolItemResolutionData("反演液面校正值","反演液面校正值",deviceInfo.getRpcCalculateRequestData().getManualIntervention().getLevelCorrectValue()+"",deviceInfo.getRpcCalculateRequestData().getManualIntervention().getLevelCorrectValue()+"","","LevelCorrectValue","","","","MPa",1,3));
		}else{
			rpcInputItemList.add(new ProtocolItemResolutionData("反演液面校正值","反演液面校正值","","","","LevelCorrectValue","","","","MPa",1,3));
		}
		
		return rpcInputItemList;
	}
	
	public static List<ProtocolItemResolutionData> getPCPInputItemData(DeviceInfo deviceInfo){
		List<ProtocolItemResolutionData> rpcInputItemList=new ArrayList<ProtocolItemResolutionData>();
		String reservoirName="油层";
		if(deviceInfo.getApplicationScenarios()==0){
			reservoirName="煤层";
		}
		if(deviceInfo!=null && deviceInfo.getPcpCalculateRequestData().getFluidPVT()!=null){
			rpcInputItemList.add(new ProtocolItemResolutionData("原油密度","原油密度",deviceInfo.getPcpCalculateRequestData().getFluidPVT().getCrudeOilDensity()+"",deviceInfo.getPcpCalculateRequestData().getFluidPVT().getCrudeOilDensity()+"","","CrudeOilDensity","","","","g/cm^3",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("水密度","水密度",deviceInfo.getPcpCalculateRequestData().getFluidPVT().getWaterDensity()+"",deviceInfo.getPcpCalculateRequestData().getFluidPVT().getWaterDensity()+"","","WaterDensity","","","","g/cm^3",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("天然气相对密度","天然气相对密度",deviceInfo.getPcpCalculateRequestData().getFluidPVT().getNaturalGasRelativeDensity()+"",deviceInfo.getPcpCalculateRequestData().getFluidPVT().getNaturalGasRelativeDensity()+"","","NaturalGasRelativeDensity","","","","",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("饱和压力","饱和压力",deviceInfo.getPcpCalculateRequestData().getFluidPVT().getSaturationPressure()+"",deviceInfo.getPcpCalculateRequestData().getFluidPVT().getSaturationPressure()+"","","SaturationPressure","","","","MPa",1,3));
		}else{
			rpcInputItemList.add(new ProtocolItemResolutionData("原油密度","原油密度","","","","CrudeOilDensity","","","","g/cm^3",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("水密度","水密度","","","","WaterDensity","","","","g/cm^3",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("天然气相对密度","天然气相对密度","","","","NaturalGasRelativeDensity","","","","",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("饱和压力","饱和压力","","","","SaturationPressure","","","","MPa",1,3));
		}
		
		if(deviceInfo!=null && deviceInfo.getPcpCalculateRequestData().getReservoir()!=null){
			rpcInputItemList.add(new ProtocolItemResolutionData(reservoirName+"中部深度",reservoirName+"中部深度",deviceInfo.getPcpCalculateRequestData().getReservoir().getDepth()+"",deviceInfo.getPcpCalculateRequestData().getReservoir().getDepth()+"","","ReservoirDepth","","","","m",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData(reservoirName+"中部温度",reservoirName+"中部温度",deviceInfo.getPcpCalculateRequestData().getReservoir().getTemperature()+"",deviceInfo.getPcpCalculateRequestData().getReservoir().getTemperature()+"","","ReservoirTemperature","","","","℃",1,3));
		}else{
			rpcInputItemList.add(new ProtocolItemResolutionData(reservoirName+"中部深度",reservoirName+"中部深度","","","","ReservoirDepth","","","","m",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData(reservoirName+"中部温度",reservoirName+"中部温度","","","","ReservoirTemperature","","","","℃",1,3));
		}
		
		if(deviceInfo!=null && deviceInfo.getPcpCalculateRequestData().getProduction()!=null){
			rpcInputItemList.add(new ProtocolItemResolutionData("油压","油压",deviceInfo.getPcpCalculateRequestData().getProduction().getTubingPressure()+"",deviceInfo.getPcpCalculateRequestData().getProduction().getTubingPressure()+"","","TubingPressure","","","","MPa",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("套压","套压",deviceInfo.getPcpCalculateRequestData().getProduction().getCasingPressure()+"",deviceInfo.getPcpCalculateRequestData().getProduction().getCasingPressure()+"","","CasingPressure","","","","MPa",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("井口温度","井口温度",deviceInfo.getPcpCalculateRequestData().getProduction().getWellHeadTemperature()+"",deviceInfo.getPcpCalculateRequestData().getProduction().getWellHeadTemperature()+"","","WellHeadTemperature","","","","℃",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("含水率","含水率",deviceInfo.getPcpCalculateRequestData().getProduction().getWaterCut()+"",deviceInfo.getPcpCalculateRequestData().getProduction().getWaterCut()+"","","WaterCut","","","","%",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("生产气油比","生产气油比",deviceInfo.getPcpCalculateRequestData().getProduction().getProductionGasOilRatio()+"",deviceInfo.getPcpCalculateRequestData().getProduction().getProductionGasOilRatio()+"","","ProductionGasOilRatio","","","","m^3/t",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("动液面","动液面",deviceInfo.getPcpCalculateRequestData().getProduction().getProducingfluidLevel()+"",deviceInfo.getPcpCalculateRequestData().getProduction().getProducingfluidLevel()+"","","ProducingfluidLevel","","","","m",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("泵挂","泵挂",deviceInfo.getPcpCalculateRequestData().getProduction().getPumpSettingDepth()+"",deviceInfo.getPcpCalculateRequestData().getProduction().getPumpSettingDepth()+"","","PumpSettingDepth","","","","m",1,3));
		}else{
			rpcInputItemList.add(new ProtocolItemResolutionData("油压","油压","","","","TubingPressure","","","","MPa",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("套压","套压","","","","CasingPressure","","","","MPa",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("井口温度","井口温度","","","","WellHeadTemperature","","","","℃",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("含水率","含水率","","","","WaterCut","","","","%",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("生产气油比","生产气油比","","","","ProductionGasOilRatio","","","","m^3/t",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("动液面","动液面","","","","ProducingfluidLevel","","","","m",1,3));
			rpcInputItemList.add(new ProtocolItemResolutionData("泵挂","泵挂","","","","PumpSettingDepth","","","","m",1,3));
		}
		
		return rpcInputItemList;
	}
	
	public static List<ProtocolItemResolutionData> getFESDiagramCalItemData(RPCCalculateRequestData calculateRequestData,RPCCalculateResponseData calculateResponseData,List<ProtocolItemResolutionData> FESDiagramCalItemList){
		//功图采集时间
		FESDiagramCalItemList.add(new ProtocolItemResolutionData("功图采集时间","功图采集时间",calculateRequestData.getFESDiagram().getAcqTime(),calculateRequestData.getFESDiagram().getAcqTime(),"","FESDiagramAcqtime","","","","",1,1));
		//冲程、冲次
		FESDiagramCalItemList.add(new ProtocolItemResolutionData("冲次","冲次",calculateRequestData.getFESDiagram().getSPM()+"",calculateRequestData.getFESDiagram().getSPM()+"","","SPM","","","","1/min",1,1));
		if(calculateResponseData!=null&&calculateResponseData.getCalculationStatus().getResultStatus()==1){
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("冲程","冲程",calculateResponseData.getFESDiagram().getStroke()+"",calculateResponseData.getFESDiagram().getStroke()+"","","Stroke","","","","m",1,1));
			//工况
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("工况","工况",calculateResponseData.getCalculationStatus().getResultCode()+"",calculateResponseData.getCalculationStatus().getResultCode()+"","","resultName","","","","",1,1));
			
			//最大最小载荷
			String FMax="",FMin="";
			if(calculateResponseData.getFESDiagram().getFMax()!=null&&calculateResponseData.getFESDiagram().getFMax().size()>0){
				FMax=calculateResponseData.getFESDiagram().getFMax().get(0)+"";
			}
			if(calculateResponseData.getFESDiagram().getFMin()!=null&&calculateResponseData.getFESDiagram().getFMin().size()>0){
				FMin=calculateResponseData.getFESDiagram().getFMin().get(0)+"";
			}
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("最大载荷","最大载荷",FMax,FMax,"","FMax","","","","kN",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("最小载荷","最小载荷",FMin,FMin,"","FMin","","","","kN",1,1));
			//平衡
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("上冲程最大电流","上冲程最大电流",calculateResponseData.getFESDiagram().getUpStrokeIMax()+"",calculateResponseData.getFESDiagram().getUpStrokeIMax()+"","","UPSTROKEIMAX","","","","A",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("下冲程最大电流","下冲程最大电流",calculateResponseData.getFESDiagram().getDownStrokeIMax()+"",calculateResponseData.getFESDiagram().getDownStrokeIMax()+"","","DOWNSTROKEIMAX","","","","A",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("上冲程最大功率","上冲程最大功率",calculateResponseData.getFESDiagram().getUpStrokeWattMax()+"",calculateResponseData.getFESDiagram().getUpStrokeWattMax()+"","","UPSTROKEWATTMAX","","","","A",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("下冲程最大功率","下冲程最大功率",calculateResponseData.getFESDiagram().getDownStrokeWattMax()+"",calculateResponseData.getFESDiagram().getDownStrokeWattMax()+"","","DOWNSTROKEWATTMAX","","","","A",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("电流平衡度","电流平衡度",calculateResponseData.getFESDiagram().getIDegreeBalance()+"",calculateResponseData.getFESDiagram().getIDegreeBalance()+"","","IDEGREEBALANCE","","","","%",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("功率平衡度","功率平衡度",calculateResponseData.getFESDiagram().getWattDegreeBalance()+"",calculateResponseData.getFESDiagram().getWattDegreeBalance()+"","","WATTDEGREEBALANCE","","","","%",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("移动距离","移动距离",calculateResponseData.getFESDiagram().getDeltaRadius()+"",calculateResponseData.getFESDiagram().getDeltaRadius()+"","","DELTARADIUS","","","","m",1,1));
			
			//充满系数、抽空充满系数
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("充满系数","充满系数",calculateResponseData.getFESDiagram().getFullnessCoefficient()+"",calculateResponseData.getFESDiagram().getFullnessCoefficient()+"","","FULLNESSCOEFFICIENT","","","","小数",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("抽空充满系数","抽空充满系数",calculateResponseData.getFESDiagram().getNoLiquidFullnessCoefficient()+"",calculateResponseData.getFESDiagram().getNoLiquidFullnessCoefficient()+"","","NOLIQUIDFULLNESSCOEFFICIENT","","","","小数",1,1));
			//柱塞冲程、柱塞有效冲程、抽空柱塞有效冲程
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞冲程","柱塞冲程",calculateResponseData.getFESDiagram().getPlungerStroke()+"",calculateResponseData.getFESDiagram().getPlungerStroke()+"","","PLUNGERSTROKE","","","","m",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞有效冲程","柱塞有效冲程",calculateResponseData.getFESDiagram().getAvailablePlungerStroke()+"",calculateResponseData.getFESDiagram().getAvailablePlungerStroke()+"","","AVAILABLEPLUNGERSTROKE","","","","m",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("抽空柱塞有效冲程","抽空柱塞有效冲程",calculateResponseData.getFESDiagram().getNoLiquidAvailablePlungerStroke()+"",calculateResponseData.getFESDiagram().getNoLiquidAvailablePlungerStroke()+"","","NOLIQUIDAVAILABLEPLUNGERSTROKE","","","","m",1,1));
			
			//上下理论载荷线
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("理论上载荷","理论上载荷",calculateResponseData.getFESDiagram().getUpperLoadLine()+"",calculateResponseData.getFESDiagram().getUpperLoadLine()+"","","UPPERLOADLINE","","","","kN",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("考虑沉没压力的理论上载荷","考虑沉没压力的理论上载荷",calculateResponseData.getFESDiagram().getUpperLoadLineOfExact()+"",calculateResponseData.getFESDiagram().getUpperLoadLineOfExact()+"","","UPPERLOADLINEOFEXACT","","","","kN",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("理论下载荷","理论下载荷",calculateResponseData.getFESDiagram().getLowerLoadLine()+"",calculateResponseData.getFESDiagram().getLowerLoadLine()+"","","LOWERLOADLINE","","","","kN",1,1));

			//位移最大、最小值索引
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("位移最大值索引","位移最大值索引",calculateResponseData.getFESDiagram().getSMaxIndex()+"",calculateResponseData.getFESDiagram().getSMaxIndex()+"","","SMAXINDEX","","","","",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("位移最小值索引","位移最小值索引",calculateResponseData.getFESDiagram().getSMinIndex()+"",calculateResponseData.getFESDiagram().getSMinIndex()+"","","SMININDEX","","","","",1,1));
			
			
			//产量
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("理论排量","理论排量",calculateResponseData.getProduction().getTheoreticalProduction()+"",calculateResponseData.getProduction().getTheoreticalProduction()+"","","THEORETICALPRODUCTION","","","","m^3/d",1,1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("瞬时产液量","瞬时产液量",calculateResponseData.getProduction().getLiquidVolumetricProduction()+"",calculateResponseData.getProduction().getLiquidVolumetricProduction()+"","","LIQUIDVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("瞬时产油量","瞬时产油量",calculateResponseData.getProduction().getOilVolumetricProduction()+"",calculateResponseData.getProduction().getOilVolumetricProduction()+"","","OILVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("瞬时产水量","瞬时产水量",calculateResponseData.getProduction().getWaterVolumetricProduction()+"",calculateResponseData.getProduction().getWaterVolumetricProduction()+"","","WATERVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞有效冲程计算产量","柱塞有效冲程计算产量",calculateResponseData.getProduction().getAvailablePlungerStrokeVolumetricProduction()+"",calculateResponseData.getProduction().getAvailablePlungerStrokeVolumetricProduction()+"","","AVAILABLEPLUNGERSTROKEPROD_V","","","","m^3/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵间隙漏失量","泵间隙漏失量",calculateResponseData.getProduction().getPumpClearanceLeakVolumetricProduction()+"",calculateResponseData.getProduction().getPumpClearanceLeakVolumetricProduction()+"","","PUMPCLEARANCELEAKPROD_V","","","","m^3/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("游动凡尔漏失量","游动凡尔漏失量",calculateResponseData.getProduction().getTVLeakVolumetricProduction()+"",calculateResponseData.getProduction().getTVLeakVolumetricProduction()+"","","TVLEAKVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("固定凡尔漏失量","固定凡尔漏失量",calculateResponseData.getProduction().getSVLeakVolumetricProduction()+"",calculateResponseData.getProduction().getSVLeakVolumetricProduction()+"","","SVLEAKVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("气影响","气影响",calculateResponseData.getProduction().getGasInfluenceVolumetricProduction()+"",calculateResponseData.getProduction().getGasInfluenceVolumetricProduction()+"","","GASINFLUENCEPROD_V","","","","m^3/d",1,1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("瞬时产液量","瞬时产液量",calculateResponseData.getProduction().getLiquidWeightProduction()+"",calculateResponseData.getProduction().getLiquidWeightProduction()+"","","LIQUIDWEIGHTPRODUCTION","","","","t/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("瞬时产油量","瞬时产油量",calculateResponseData.getProduction().getOilWeightProduction()+"",calculateResponseData.getProduction().getOilWeightProduction()+"","","OILWEIGHTPRODUCTION","","","","t/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("瞬时产水量","瞬时产水量",calculateResponseData.getProduction().getWaterWeightProduction()+"",calculateResponseData.getProduction().getWaterWeightProduction()+"","","WATERWEIGHTPRODUCTION","","","","t/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞有效冲程计算产量","柱塞有效冲程计算产量",calculateResponseData.getProduction().getAvailablePlungerStrokeWeightProduction()+"",calculateResponseData.getProduction().getAvailablePlungerStrokeWeightProduction()+"","","AVAILABLEPLUNGERSTROKEPROD_W","","","","t/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵间隙漏失量","泵间隙漏失量",calculateResponseData.getProduction().getPumpClearanceLeakWeightProduction()+"",calculateResponseData.getProduction().getPumpClearanceLeakWeightProduction()+"","","PUMPCLEARANCELEAKPROD_W","","","","t/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("游动凡尔漏失量","游动凡尔漏失量",calculateResponseData.getProduction().getTVLeakWeightProduction()+"",calculateResponseData.getProduction().getTVLeakWeightProduction()+"","","TVLEAKWEIGHTPRODUCTION","","","","t/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("固定凡尔漏失量","固定凡尔漏失量",calculateResponseData.getProduction().getSVLeakWeightProduction()+"",calculateResponseData.getProduction().getSVLeakWeightProduction()+"","","SVLEAKWEIGHTPRODUCTION","","","","t/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("气影响","气影响",calculateResponseData.getProduction().getGasInfluenceWeightProduction()+"",calculateResponseData.getProduction().getGasInfluenceWeightProduction()+"","","GASINFLUENCEPROD_W","","","","t/d",1,1));
			
			//液面校正差值、反演动液面、沉没度
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("液面校正差值","液面校正差值",calculateResponseData.getProduction().getLevelDifferenceValue()+"",calculateResponseData.getProduction().getLevelDifferenceValue()+"","","LEVELDIFFERENCEVALUE","","","","m",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("反演动液面","反演动液面",calculateResponseData.getProduction().getCalcProducingfluidLevel()+"",calculateResponseData.getProduction().getCalcProducingfluidLevel()+"","","CALCPRODUCINGFLUIDLEVEL","","","","m",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("沉没度","沉没度",calculateResponseData.getProduction().getSubmergence()+"",calculateResponseData.getProduction().getSubmergence()+"","","SUBMERGENCE","","","","m",1,1));
			
			//系统效率
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("有功功率","有功功率",calculateResponseData.getFESDiagram().getAvgWatt()+"",calculateResponseData.getFESDiagram().getAvgWatt()+"","","AVERAGEWATT","","","","kW",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("光杆功率","光杆功率",calculateResponseData.getSystemEfficiency().getPolishRodPower()+"",calculateResponseData.getSystemEfficiency().getPolishRodPower()+"","","POLISHRODPOWER","","","","kW",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("水功率","水功率",calculateResponseData.getSystemEfficiency().getWaterPower()+"",calculateResponseData.getSystemEfficiency().getWaterPower()+"","","WATERPOWER","","","","kW",1,1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("地面效率","地面效率",calculateResponseData.getSystemEfficiency().getSurfaceSystemEfficiency()+"",calculateResponseData.getSystemEfficiency().getSurfaceSystemEfficiency()+"","","SURFACESYSTEMEFFICIENCY","","","","小数",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("井下效率","井下效率",calculateResponseData.getSystemEfficiency().getWellDownSystemEfficiency()+"",calculateResponseData.getSystemEfficiency().getWellDownSystemEfficiency()+"","","WELLDOWNSYSTEMEFFICIENCY","","","","小数",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("系统效率","系统效率",calculateResponseData.getSystemEfficiency().getSystemEfficiency()+"",calculateResponseData.getSystemEfficiency().getSystemEfficiency()+"","","SYSTEMEFFICIENCY","","","","小数",1,1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("吨液百米耗电量","吨液百米耗电量",calculateResponseData.getSystemEfficiency().getEnergyPer100mLift()+"",calculateResponseData.getSystemEfficiency().getEnergyPer100mLift()+"","","ENERGYPER100MLIFT","","","","kW· h/100m· t",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("功图面积","功图面积",calculateResponseData.getFESDiagram().getArea()+"",calculateResponseData.getFESDiagram().getArea()+"","","AREA","","","","",1,1));
			
			//泵效
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("抽油杆伸长量","抽油杆伸长量",calculateResponseData.getPumpEfficiency().getRodFlexLength()+"",calculateResponseData.getPumpEfficiency().getRodFlexLength()+"","","RODFLEXLENGTH","","","","m",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("油管伸缩量","油管伸缩量",calculateResponseData.getPumpEfficiency().getTubingFlexLength()+"",calculateResponseData.getPumpEfficiency().getTubingFlexLength()+"","","TUBINGFLEXLENGTH","","","","m",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("惯性载荷增量","惯性载荷增量",calculateResponseData.getPumpEfficiency().getInertiaLength()+"",calculateResponseData.getPumpEfficiency().getInertiaLength()+"","","INERTIALENGTH","","","","m",1,1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("冲程损失系数","冲程损失系数",calculateResponseData.getPumpEfficiency().getPumpEff1()+"",calculateResponseData.getPumpEfficiency().getPumpEff1()+"","","PUMPEFF1","","","","小数",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("充满系数","充满系数",calculateResponseData.getPumpEfficiency().getPumpEff2()+"",calculateResponseData.getPumpEfficiency().getPumpEff2()+"","","PUMPEFF2","","","","小数",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("间隙漏失系数","间隙漏失系数",calculateResponseData.getPumpEfficiency().getPumpEff3()+"",calculateResponseData.getPumpEfficiency().getPumpEff3()+"","","PUMPEFF3","","","","小数",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("液体收缩系数","液体收缩系数",calculateResponseData.getPumpEfficiency().getPumpEff4()+"",calculateResponseData.getPumpEfficiency().getPumpEff4()+"","","PUMPEFF4","","","","小数",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("总泵效","总泵效",calculateResponseData.getPumpEfficiency().getPumpEff()+"",calculateResponseData.getPumpEfficiency().getPumpEff()+"","","PUMPEFF","","","","小数",1,1));
			
			//泵入口出口参数
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口压力","泵入口压力",calculateResponseData.getProduction().getPumpIntakeP()+"",calculateResponseData.getProduction().getPumpIntakeP()+"","","PUMPINTAKEP","","","","MPa",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口温度","泵入口温度",calculateResponseData.getProduction().getPumpIntakeT()+"",calculateResponseData.getProduction().getPumpIntakeT()+"","","PUMPINTAKET","","","","℃",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口就地气液比","泵入口就地气液比",calculateResponseData.getProduction().getPumpIntakeGOL()+"",calculateResponseData.getProduction().getPumpIntakeGOL()+"","","PUMPINTAKEGOL","","","","m^3/m^3",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口粘度","泵入口粘度",calculateResponseData.getProduction().getPumpIntakeVisl()+"",calculateResponseData.getProduction().getPumpIntakeVisl()+"","","PUMPINTAKEVISL","","","","mPa·s",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口原油体积系数","泵入口原油体积系数",calculateResponseData.getProduction().getPumpIntakeBo()+"",calculateResponseData.getProduction().getPumpIntakeBo()+"","","PUMPINTAKEBO","","","","小数",1,1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口压力","泵出口压力",calculateResponseData.getProduction().getPumpOutletP()+"",calculateResponseData.getProduction().getPumpOutletP()+"","","PUMPOUTLETP","","","","MPa",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口温度","泵出口温度",calculateResponseData.getProduction().getPumpOutletT()+"",calculateResponseData.getProduction().getPumpOutletT()+"","","PUMPOUTLETT","","","","℃",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口就地气液比","泵出口就地气液比",calculateResponseData.getProduction().getPumpOutletGOL()+"",calculateResponseData.getProduction().getPumpOutletGOL()+"","","PUMPOUTLETGOL","","","","m^3/m^3",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口粘度","泵出口粘度",calculateResponseData.getProduction().getPumpOutletVisl()+"",calculateResponseData.getProduction().getPumpOutletVisl()+"","","PUMPOUTLETVISL","","","","mPa·s",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口原油体积系数","泵出口原油体积系数",calculateResponseData.getProduction().getPumpOutletBo()+"",calculateResponseData.getProduction().getPumpOutletBo()+"","","PUMPOUTLETBO","","","","小数",1,1));
			
			
			//杆参数
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("杆参数","杆参数",calculateResponseData.getRodCalData()+"",calculateResponseData.getRodCalData()+"","","RODSTRING","","","","",1,1));
		}else{
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("冲程","冲程",calculateRequestData.getFESDiagram().getStroke()+"",calculateRequestData.getFESDiagram().getStroke()+"","","Stroke","","","","m",1,1));
			//工况
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("工况","工况","","","","resultName","","","","",1,1));
			//最大最小载荷
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("最大载荷","最大载荷","","","","FMax","","","","kN",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("最小载荷","最小载荷","","","","FMin","","","","kN",1,1));
			//平衡
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("上冲程最大电流","上冲程最大电流","","","","UPSTROKEIMAX","","","","A",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("下冲程最大电流","下冲程最大电流","","","","DOWNSTROKEIMAX","","","","A",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("上冲程最大功率","上冲程最大功率","","","","UPSTROKEWATTMAX","","","","A",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("下冲程最大功率","下冲程最大功率","","","","DOWNSTROKEWATTMAX","","","","A",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("电流平衡度","电流平衡度","","","","IDEGREEBALANCE","","","","%",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("功率平衡度","功率平衡度","","","","WATTDEGREEBALANCE","","","","%",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("移动距离","移动距离","","","","DELTARADIUS","","","","m",1,1));
			
			//充满系数、抽空充满系数
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("充满系数","充满系数","","","","FULLNESSCOEFFICIENT","","","","小数",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("抽空充满系数","抽空充满系数","","","","NOLIQUIDFULLNESSCOEFFICIENT","","","","小数",1,1));
			//柱塞冲程、柱塞有效冲程、抽空柱塞有效冲程
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞冲程","柱塞冲程","","","","PLUNGERSTROKE","","","","m",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞有效冲程","柱塞有效冲程","","","","AVAILABLEPLUNGERSTROKE","","","","m",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("抽空柱塞有效冲程","抽空柱塞有效冲程","","","","NOLIQUIDAVAILABLEPLUNGERSTROKE","","","","m",1,1));
			
			//上下理论载荷线
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("上理论载荷线","上理论载荷线","","","","UPPERLOADLINE","","","","kN",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("考虑沉没压力的理论上载荷","考虑沉没压力的理论上载荷","","","","UPPERLOADLINEOFEXACT","","","","kN",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("下理论载荷线","下理论载荷线","","","","LOWERLOADLINE","","","","kN",1,1));

			//位移最大、最小值索引
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("位移最大值索引","位移最大值索引","","","","SMAXINDEX","","","","",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("位移最小值索引","位移最小值索引","","","","SMININDEX","","","","",1,1));
			
			
			//产量
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("理论排量","理论排量","","","","THEORETICALPRODUCTION","","","","m^3/d",1,1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("瞬时产液量","瞬时产液量","","","","LIQUIDVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("瞬时产油量","瞬时产油量","","","","OILVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("瞬时产水量","瞬时产水量","","","","WATERVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞有效冲程计算产量","柱塞有效冲程计算产量","","","","AVAILABLEPLUNGERSTROKEPROD_V","","","","m^3/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵间隙漏失量","泵间隙漏失量","","","","PUMPCLEARANCELEAKPROD_V","","","","m^3/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("游动凡尔漏失量","游动凡尔漏失量","","","","TVLEAKVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("固定凡尔漏失量","固定凡尔漏失量","","","","SVLEAKVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("气影响","气影响","","","","GASINFLUENCEPROD_V","","","","m^3/d",1,1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("瞬时产液量","瞬时产液量","","","","LIQUIDWEIGHTPRODUCTION","","","","t/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("瞬时产油量","瞬时产油量","","","","OILWEIGHTPRODUCTION","","","","t/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("瞬时产水量","瞬时产水量","","","","WATERWEIGHTPRODUCTION","","","","t/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞有效冲程计算产量","柱塞有效冲程计算产量","","","","AVAILABLEPLUNGERSTROKEPROD_W","","","","t/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵间隙漏失量","泵间隙漏失量","","","","PUMPCLEARANCELEAKPROD_W","","","","t/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("游动凡尔漏失量","游动凡尔漏失量","","","","TVLEAKWEIGHTPRODUCTION","","","","t/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("固定凡尔漏失量","固定凡尔漏失量","","","","SVLEAKWEIGHTPRODUCTION","","","","t/d",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("气影响","气影响","","","","GASINFLUENCEPROD_W","","","","t/d",1,1));
			
			//液面校正差值、反演动液面、沉没度
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("液面校正差值","液面校正差值","","","","LEVELDIFFERENCEVALUE","","","","m",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("反演动液面","反演动液面","","","","CALCPRODUCINGFLUIDLEVEL","","","","m",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("沉没度","沉没度","","","","SUBMERGENCE","","","","m",1,1));
			
			//系统效率
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("有功功率","有功功率","","","","AVERAGEWATT","","","","kW",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("光杆功率","光杆功率","","","","POLISHRODPOWER","","","","kW",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("水功率","水功率","","","","WATERPOWER","","","","kW",1,1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("地面效率","地面效率","","","","SURFACESYSTEMEFFICIENCY","","","","小数",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("井下效率","井下效率","","","","WELLDOWNSYSTEMEFFICIENCY","","","","小数",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("系统效率","系统效率","","","","SYSTEMEFFICIENCY","","","","小数",1,1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("吨液百米耗电量","吨液百米耗电量","","","","ENERGYPER100MLIFT","","","","kW· h/100m· t",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("功图面积","功图面积","","","","AREA","","","","",1,1));
			
			//泵效
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("抽油杆伸长量","抽油杆伸长量","","","","RODFLEXLENGTH","","","","m",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("油管伸缩量","油管伸缩量","","","","TUBINGFLEXLENGTH","","","","m",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("惯性载荷增量","惯性载荷增量","","","","INERTIALENGTH","","","","m",1,1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("冲程损失系数","冲程损失系数","","","","PUMPEFF1","","","","小数",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("充满系数","充满系数","","","","PUMPEFF2","","","","小数",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("间隙漏失系数","间隙漏失系数","","","","PUMPEFF3","","","","小数",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("液体收缩系数","液体收缩系数","","","","PUMPEFF4","","","","小数",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("总泵效","总泵效","","","","PUMPEFF","","","","小数",1,1));
			
			//泵入口出口参数
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口压力","泵入口压力","","","","PUMPINTAKEP","","","","MPa",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口温度","泵入口温度","","","","PUMPINTAKET","","","","℃",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口就地气液比","泵入口就地气液比","","","","PUMPINTAKEGOL","","","","m^3/m^3",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口粘度","泵入口粘度","","","","PUMPINTAKEVISL","","","","mPa·s",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口原油体积系数","泵入口原油体积系数","","","","PUMPINTAKEBO","","","","小数",1,1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口压力","泵出口压力","","","","PUMPOUTLETP","","","","MPa",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口温度","泵出口温度","","","","PUMPOUTLETT","","","","℃",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口就地气液比","泵出口就地气液比","","","","PUMPOUTLETGOL","","","","m^3/m^3",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口粘度","泵出口粘度","","","","PUMPOUTLETVISL","","","","mPa·s",1,1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口原油体积系数","泵出口原油体积系数","","","","PUMPOUTLETBO","","","","小数",1,1));
			
			
			//杆参数
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("杆参数","杆参数","","","","RODSTRING","","","","",1,1));
		}
		return FESDiagramCalItemList;
	}
	
	public static List<ProtocolItemResolutionData> getRPMCalItemData(PCPCalculateRequestData calculateRequestData,PCPCalculateResponseData calculateResponseData,
			List<ProtocolItemResolutionData> calItemList){
		if(calculateResponseData!=null&&calculateResponseData.getCalculationStatus().getResultStatus()==1){
			//产量
			calItemList.add(new ProtocolItemResolutionData("理论排量","理论排量",calculateResponseData.getProduction().getTheoreticalProduction()+"",calculateResponseData.getProduction().getTheoreticalProduction()+"","","THEORETICALPRODUCTION","","","","m^3/d",1,1));
			
			calItemList.add(new ProtocolItemResolutionData("瞬时产液量","瞬时产液量",calculateResponseData.getProduction().getLiquidVolumetricProduction()+"",calculateResponseData.getProduction().getLiquidVolumetricProduction()+"","","LIQUIDVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("瞬时产油量","瞬时产油量",calculateResponseData.getProduction().getOilVolumetricProduction()+"",calculateResponseData.getProduction().getOilVolumetricProduction()+"","","OILVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("瞬时产水量","瞬时产水量",calculateResponseData.getProduction().getWaterVolumetricProduction()+"",calculateResponseData.getProduction().getWaterVolumetricProduction()+"","","WATERVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			
			calItemList.add(new ProtocolItemResolutionData("瞬时产液量","瞬时产液量",calculateResponseData.getProduction().getLiquidWeightProduction()+"",calculateResponseData.getProduction().getLiquidWeightProduction()+"","","LIQUIDWEIGHTPRODUCTION","","","","t/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("瞬时产油量","瞬时产油量",calculateResponseData.getProduction().getOilWeightProduction()+"",calculateResponseData.getProduction().getOilWeightProduction()+"","","OILWEIGHTPRODUCTION","","","","t/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("瞬时产水量","瞬时产水量",calculateResponseData.getProduction().getWaterWeightProduction()+"",calculateResponseData.getProduction().getWaterWeightProduction()+"","","WATERWEIGHTPRODUCTION","","","","t/d",1,1));
			
			//系统效率
			calItemList.add(new ProtocolItemResolutionData("有功功率","有功功率",calculateResponseData.getSystemEfficiency().getMotorInputWatt()+"",calculateResponseData.getSystemEfficiency().getMotorInputWatt()+"","","AVERAGEWATT","","","","kW",1,1));
			calItemList.add(new ProtocolItemResolutionData("水功率","水功率",calculateResponseData.getSystemEfficiency().getWaterPower()+"",calculateResponseData.getSystemEfficiency().getWaterPower()+"","","WATERPOWER","","","","kW",1,1));
			calItemList.add(new ProtocolItemResolutionData("系统效率","系统效率",calculateResponseData.getSystemEfficiency().getSystemEfficiency()+"",calculateResponseData.getSystemEfficiency().getSystemEfficiency()+"","","SYSTEMEFFICIENCY","","","","小数",1,1));
			
			//泵效
			calItemList.add(new ProtocolItemResolutionData("容积效率","容积效率",calculateResponseData.getPumpEfficiency().getPumpEff1()+"",calculateResponseData.getPumpEfficiency().getPumpEff1()+"","","PUMPEFF1","","","","小数",1,1));
			calItemList.add(new ProtocolItemResolutionData("液体收缩系数","液体收缩系数",calculateResponseData.getPumpEfficiency().getPumpEff2()+"",calculateResponseData.getPumpEfficiency().getPumpEff2()+"","","PUMPEFF2","","","","小数",1,1));
			calItemList.add(new ProtocolItemResolutionData("总泵效","总泵效",calculateResponseData.getPumpEfficiency().getPumpEff()+"",calculateResponseData.getPumpEfficiency().getPumpEff()+"","","PUMPEFF","","","","小数",1,1));
			
			//泵入口出口参数
			calItemList.add(new ProtocolItemResolutionData("泵入口压力","泵入口压力",calculateResponseData.getProduction().getPumpIntakeP()+"",calculateResponseData.getProduction().getPumpIntakeP()+"","","PUMPINTAKEP","","","","MPa",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵入口温度","泵入口温度",calculateResponseData.getProduction().getPumpIntakeT()+"",calculateResponseData.getProduction().getPumpIntakeT()+"","","PUMPINTAKET","","","","℃",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵入口就地气液比","泵入口就地气液比",calculateResponseData.getProduction().getPumpIntakeGOL()+"",calculateResponseData.getProduction().getPumpIntakeGOL()+"","","PUMPINTAKEGOL","","","","m^3/m^3",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵入口粘度","泵入口粘度",calculateResponseData.getProduction().getPumpIntakeVisl()+"",calculateResponseData.getProduction().getPumpIntakeVisl()+"","","PUMPINTAKEVISL","","","","mPa·s",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵入口原油体积系数","泵入口原油体积系数",calculateResponseData.getProduction().getPumpIntakeBo()+"",calculateResponseData.getProduction().getPumpIntakeBo()+"","","PUMPINTAKEBO","","","","小数",1,1));
			
			calItemList.add(new ProtocolItemResolutionData("泵出口压力","泵出口压力",calculateResponseData.getProduction().getPumpOutletP()+"",calculateResponseData.getProduction().getPumpOutletP()+"","","PUMPOUTLETP","","","","MPa",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵出口温度","泵出口温度",calculateResponseData.getProduction().getPumpOutletT()+"",calculateResponseData.getProduction().getPumpOutletT()+"","","PUMPOUTLETT","","","","℃",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵出口就地气液比","泵出口就地气液比",calculateResponseData.getProduction().getPumpOutletGOL()+"",calculateResponseData.getProduction().getPumpOutletGOL()+"","","PUMPOUTLETGOL","","","","m^3/m^3",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵出口粘度","泵出口粘度",calculateResponseData.getProduction().getPumpOutletVisl()+"",calculateResponseData.getProduction().getPumpOutletVisl()+"","","PUMPOUTLETVISL","","","","mPa·s",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵出口原油体积系数","泵出口原油体积系数",calculateResponseData.getProduction().getPumpOutletBo()+"",calculateResponseData.getProduction().getPumpOutletBo()+"","","PUMPOUTLETBO","","","","小数",1,1));
			
			
			//杆参数
			calItemList.add(new ProtocolItemResolutionData("杆参数","杆参数",calculateResponseData.getRodCalData()+"",calculateResponseData.getRodCalData()+"","","RODSTRING","","","","",1,1));
		}else{
			//产量
			calItemList.add(new ProtocolItemResolutionData("理论排量","理论排量","","","","THEORETICALPRODUCTION","","","","m^3/d",1,1));
			
			calItemList.add(new ProtocolItemResolutionData("瞬时产液量","瞬时产液量","","","","LIQUIDVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("瞬时产油量","瞬时产油量","","","","OILVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("瞬时产水量","瞬时产水量","","","","WATERVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("柱塞有效冲程计算产量","柱塞有效冲程计算产量","","","","AVAILABLEPLUNGERSTROKEPROD_V","","","","m^3/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵间隙漏失量","泵间隙漏失量","","","","PUMPCLEARANCELEAKPROD_V","","","","m^3/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("游动凡尔漏失量","游动凡尔漏失量","","","","TVLEAKVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("固定凡尔漏失量","固定凡尔漏失量","","","","SVLEAKVOLUMETRICPRODUCTION","","","","m^3/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("气影响","气影响","","","","GASINFLUENCEPROD_V","","","","m^3/d",1,1));
			
			calItemList.add(new ProtocolItemResolutionData("瞬时产液量","瞬时产液量","","","","LIQUIDWEIGHTPRODUCTION","","","","t/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("瞬时产油量","瞬时产油量","","","","OILWEIGHTPRODUCTION","","","","t/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("瞬时产水量","瞬时产水量","","","","WATERWEIGHTPRODUCTION","","","","t/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("柱塞有效冲程计算产量","柱塞有效冲程计算产量","","","","AVAILABLEPLUNGERSTROKEPROD_W","","","","t/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵间隙漏失量","泵间隙漏失量","","","","PUMPCLEARANCELEAKPROD_W","","","","t/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("游动凡尔漏失量","游动凡尔漏失量","","","","TVLEAKWEIGHTPRODUCTION","","","","t/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("固定凡尔漏失量","固定凡尔漏失量","","","","SVLEAKWEIGHTPRODUCTION","","","","t/d",1,1));
			calItemList.add(new ProtocolItemResolutionData("气影响","气影响","","","","GASINFLUENCEPROD_W","","","","t/d",1,1));
			
			
			
			//系统效率
			calItemList.add(new ProtocolItemResolutionData("有功功率","有功功率","","","","AVERAGEWATT","","","","kW",1,1));
			calItemList.add(new ProtocolItemResolutionData("水功率","水功率","","","","WATERPOWER","","","","kW",1,1));
			calItemList.add(new ProtocolItemResolutionData("系统效率","系统效率","","","","SYSTEMEFFICIENCY","","","","小数",1,1));
			
			//泵效
			calItemList.add(new ProtocolItemResolutionData("抽油杆伸长量","抽油杆伸长量","","","","RODFLEXLENGTH","","","","m",1,1));
			calItemList.add(new ProtocolItemResolutionData("油管伸缩量","油管伸缩量","","","","TUBINGFLEXLENGTH","","","","m",1,1));
			calItemList.add(new ProtocolItemResolutionData("惯性载荷增量","惯性载荷增量","","","","INERTIALENGTH","","","","m",1,1));
			
			calItemList.add(new ProtocolItemResolutionData("容积效率","容积效率","","","","PUMPEFF1","","","","小数",1,1));
			calItemList.add(new ProtocolItemResolutionData("液体收缩系数","液体收缩系数","","","","PUMPEFF2","","","","小数",1,1));
			calItemList.add(new ProtocolItemResolutionData("总泵效","总泵效","","","","PUMPEFF","","","","小数",1,1));
			
			//泵入口出口参数
			calItemList.add(new ProtocolItemResolutionData("泵入口压力","泵入口压力","","","","PUMPINTAKEP","","","","MPa",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵入口温度","泵入口温度","","","","PUMPINTAKET","","","","℃",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵入口就地气液比","泵入口就地气液比","","","","PUMPINTAKEGOL","","","","m^3/m^3",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵入口粘度","泵入口粘度","","","","PUMPINTAKEVISL","","","","mPa·s",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵入口原油体积系数","泵入口原油体积系数","","","","PUMPINTAKEBO","","","","小数",1,1));
			
			calItemList.add(new ProtocolItemResolutionData("泵出口压力","泵出口压力","","","","PUMPOUTLETP","","","","MPa",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵出口温度","泵出口温度","","","","PUMPOUTLETT","","","","℃",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵出口就地气液比","泵出口就地气液比","","","","PUMPOUTLETGOL","","","","m^3/m^3",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵出口粘度","泵出口粘度","","","","PUMPOUTLETVISL","","","","mPa·s",1,1));
			calItemList.add(new ProtocolItemResolutionData("泵出口原油体积系数","泵出口原油体积系数","","","","PUMPOUTLETBO","","","","小数",1,1));
			
			
			//杆参数
			calItemList.add(new ProtocolItemResolutionData("杆参数","杆参数","","","","RODSTRING","","","","",1,1));
		}
		return calItemList;
	}
	
	public void updateRequestData(RPCCalculateRequestData calculateRequestData,DeviceInfo deviceInfo){
		calculateRequestData.setWellName(deviceInfo.getWellName());
		
		if(deviceInfo.getApplicationScenarios()==0){
			calculateRequestData.setScene("cbm");
		}else{
			calculateRequestData.setScene("oil");
		}
		
		calculateRequestData.setFluidPVT(deviceInfo.getRpcCalculateRequestData().getFluidPVT());
		calculateRequestData.setReservoir(deviceInfo.getRpcCalculateRequestData().getReservoir());
		calculateRequestData.setRodString(deviceInfo.getRpcCalculateRequestData().getRodString());
		calculateRequestData.setTubingString(deviceInfo.getRpcCalculateRequestData().getTubingString());
		calculateRequestData.setCasingString(deviceInfo.getRpcCalculateRequestData().getCasingString());
		calculateRequestData.setPump(deviceInfo.getRpcCalculateRequestData().getPump());
		calculateRequestData.setPumpingUnit(deviceInfo.getRpcCalculateRequestData().getPumpingUnit());
		calculateRequestData.setProduction(deviceInfo.getRpcCalculateRequestData().getProduction());
		calculateRequestData.setManualIntervention(deviceInfo.getRpcCalculateRequestData().getManualIntervention());
		
	}
	
	public void updateRPMRequestData(PCPCalculateRequestData pcpCalculateRequestData,DeviceInfo deviceInfo){
		if(deviceInfo.getApplicationScenarios()==0){
			pcpCalculateRequestData.setScene("cbm");
		}else{
			pcpCalculateRequestData.setScene("oil");
		}
		pcpCalculateRequestData.setWellName(deviceInfo.getWellName());
//		pcpCalculateRequestData.setAcqTime(acqTime);
		pcpCalculateRequestData.setFluidPVT(deviceInfo.getPcpCalculateRequestData().getFluidPVT());
		pcpCalculateRequestData.setReservoir(deviceInfo.getPcpCalculateRequestData().getReservoir());
		pcpCalculateRequestData.setRodString(deviceInfo.getPcpCalculateRequestData().getRodString());
		pcpCalculateRequestData.setTubingString(deviceInfo.getPcpCalculateRequestData().getTubingString());
		pcpCalculateRequestData.setCasingString(deviceInfo.getPcpCalculateRequestData().getCasingString());
		pcpCalculateRequestData.setPump(deviceInfo.getPcpCalculateRequestData().getPump());
		pcpCalculateRequestData.setProduction(deviceInfo.getPcpCalculateRequestData().getProduction());
		pcpCalculateRequestData.setManualIntervention(deviceInfo.getPcpCalculateRequestData().getManualIntervention());
	}
	
	@RequestMapping("/userLogin")
	public String userLogin() throws Exception {
		String user = "";
		String password = "";
		
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{\"User\": \"admin\",\"Password\": \"123456\"}";
		try{
			JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
			try{
				user=jsonObject.getString("User");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				password=jsonObject.getString("Password");
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		String result="";
		String msg="";
		int userCheckSign=this.userManagerService.userCheck(user, password);
		if(userCheckSign==-1) {
			msg="用户名不能为空";
		}else if (userCheckSign==-2) {
			msg="用户密码不能为空";
		}else if (userCheckSign==-3) {
			msg="账号或密码错误";
		}else if (userCheckSign==-4) {
			msg="用户" + user + "已被禁用 !";
		}else if (userCheckSign==1) {
			msg="登录成功";
		}
		result="{\"ResultStatus\":"+userCheckSign+",\"Msg\":\""+msg+"\"}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(result);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(ss!=null){
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/read/getOrganizationData")
	public String getOrganizationData() throws Exception {
		StringBuffer result_json = new StringBuffer();
		String orgListStr = "";
		String user = "";
		String password = "";
		
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{\"User\": \"admin\",\"Password\": \"123456\"}";
		try{
			JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
			try{
				user=jsonObject.getString("User");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				password=jsonObject.getString("Password");
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(ss!=null){
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		int userCheckSign=this.userManagerService.userCheck(user, password);
		if(userCheckSign==1){
			List<Org> list = (List<Org>) mobileService.getOrganizationData(Org.class, user);
			StringBuffer strBuf = new StringBuffer();
			Recursion r = new Recursion();// 递归类，将org集合构建成一棵树形菜单的json
			for (Org org : list) {
				if (!r.hasParent(list, org)) {
					orgListStr = r.recursionMobileOrgTree(list, org);
				}
			}
			orgListStr = orgListStr.replaceAll(",]", "]");
			strBuf.append(orgListStr);
			if(strBuf.toString().endsWith(",")){
				strBuf.deleteCharAt(strBuf.length() - 1);
			}
			orgListStr = strBuf.toString();
		}else{
			orgListStr="{}";
		}
		result_json.append("{\"ResultStatus\":"+userCheckSign+",\"Org\":"+orgListStr+"}");
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(result_json.toString());
		pw.flush();
		pw.close();

		return null;
	}
	
	/******
	 * 获取设备信息
	 * ***/
	@RequestMapping("/read/oilWell/wellInformation")
	public String getOilWellInformation() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"User\": \"admin\",\"Password\": \"123456\",\"LiftingType\":1,\"WellList\":[\"rpc01\"]}";
		this.pager = new Page("pagerForm", request);
		String json = mobileService.getOilWellInformation(data,pager);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(ss!=null){
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/******
	 * 获取设备信息
	 * ***/
	@RequestMapping("/read/oilWell/pumpingModelInformation")
	public String getPumpingModelInformation() throws Exception {
		ServletInputStream ss=null;
		try {
			ss = request.getInputStream();
			String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//			data="{}";
//			data="{\"User\": \"admin\",\"Password\": \"123456\",\"Manufacturer\":\"大庆\",\"Model\":\"CYJY8-3-37HB\"}";
			this.pager = new Page("pagerForm", request);
			String json = mobileService.getPumpingModelInformation(data,pager);
			response.setContentType("application/json;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw;
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(ss!=null){
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/******
	 * 统计饼图及柱状图需要的data信息
	 * ***/
	@RequestMapping("/read/oilWell/realtime/statisticsData")
	public String getPumpingRealtimeStatisticsData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"User\": \"admin\",\"Password\": \"123456\",\"LiftingType\":1,\"StatType\":3,\"WellList\":[\"rpc01\",\"rpc02\"]}";
		String json = mobileService.getPumpingRealtimeStatisticsDataByWellList(data);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/******
	 * 查询处于某种统计值下的实时井列表及数据
	 * ***/
	@RequestMapping("/read/oilWell/realtime/wellListData")
	public String getOilWellRealtimeWellListData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"User\": \"admin\",\"Password\": \"123456\",\"LiftingType\":1,\"StatType\":1,\"StatValue\":\"正常\",\"WellList\":[\"rpc01\",\"rpc02\"]}";
		this.pager = new Page("pagerForm", request);
		String json = mobileService.getOilWellRealtimeWellListData(data,pager);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/******
	 * 查询处于某种统计值下的井历史数据
	 * ***/
	@RequestMapping("/read/oilWell/realtime/wellHistoryData")
	public String getOilWellHistoryData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"User\": \"admin\",\"Password\": \"123456\",\"LiftingType\":1,\"StatType\":1,\"StatValue\":\"正常\",\"StartDate\":\"2023-08-01 00:00:00\",\"EndDate\":\"2023-08-04 23:59:59\",\"WellName\":\"rpc01\"}";
		this.pager = new Page("pagerForm", request);
		String json = mobileService.getOilWellHistoryData(data,pager);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/read/oilWell/realtime/wellAnalysisData")
	public String getOilWellAnalysisData()throws Exception{
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"User\": \"admin\",\"Password\": \"123456\",\"LiftingType\":1,\"WellName\":\"rpc01\",\"AcqTime\":\"2023-08-02 02:00:00\"}";
		String json = this.mobileService.getOilWellAnalysisData(data);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/read/oilWell/realtime/singleFESDiagramData")
	public String singleFESDiagramData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{\"User\": \"admin\",\"Password\": \"123456\",\"WellName\":\"rpc01\",\"AcqTime\":\"2023-08-02 02:00:00\"}";
		String json = this.mobileService.singleFESDiagramData(data);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/read/oilWell/realtime/historyFESDiagramData")
	public String historyFESDiagramData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{\"User\": \"admin\",\"Password\": \"123456\",\"WellName\":\"rpc01\",\"StartDate\":\"2023-08-02 02:00:00\",\"EndDate\":\"2023-08-02 02:00:00\"}";
		String json = this.mobileService.historyFESDiagramData(data);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/******
	 * 全天统计饼图及柱状图需要的data信息
	 * ***/
	@RequestMapping("/read/oilWell/total/statisticsData")
	public String getOilWellTotalStatisticsData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"User\": \"admin\",\"Password\": \"123456\",\"LiftingType\":1,\"Date\":\"2023-08-02\",\"StatType\":3,\"WellList\":[\"rpc01\",\"rpc02\"]}";
		String json = mobileService.getOilWellTotalStatisticsData(data);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/******
	 * 查询某天处于某种统计值下的全天井列表及数据
	 * ***/
	@RequestMapping("/read/oilWell/total/wellListData")
	public String getOilWellTotalWellListData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"User\": \"admin\",\"Password\": \"123456\",\"LiftingType\":1,\"Date\":\"2023-08-02\",\"StatType\":1,\"StatValue\":\"正常\",\"WellList\":[\"rpc01\",\"rpc02\"]}";
		this.pager = new Page("pagerForm", request);
		String json = mobileService.getOilWellTotalWellListData(data,pager);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/******
	 * 查询处于某种统计值下的井全天历史数据
	 * ***/
	@RequestMapping("/read/oilWell/total/wellHistoryData")
	public String getOilWellTotalHistoryData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
//		data="{}";
//		data="{\"User\": \"admin\",\"Password\": \"123456\",\"LiftingType\": 1,\"WellName\":\"rpc01\",\"StartDate\": \"2023-08-02\",\"EndDate\": \"2023-08-02\",\"StatType\": 1,\"StatValue\": \"正常\"}";
//		data="{\"LiftingType\": 1,\"StartDate\": \"2021-01-27\",\"EndDate\": \"2021-04-27\",\"StatType\": 1}";
		this.pager = new Page("pagerForm", request);
		String json = mobileService.getOilWellTotalHistoryData(data,pager);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/******
	 * 生产数据写入
	 * ***/
	@RequestMapping("/write/production")
	public String writeOilWellProductionData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
		this.pager = new Page("pagerForm", request);
		String json = "{\"Msg\":\"此接口位预留\"}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/******
	 * 功图数据写入
	 * ***/
	@RequestMapping("/write/FESDiagram")
	public String writeOilWellFESDiagramData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8").replaceAll(" ", "");
		this.pager = new Page("pagerForm", request);
		String json = "{\"Msg\":\"此接口位预留\"}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
