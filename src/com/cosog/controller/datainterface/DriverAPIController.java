package com.cosog.controller.datainterface;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.DataMapping;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AcqInstanceOwnItem;
import com.cosog.model.calculate.AlarmInstanceOwnItem;
import com.cosog.model.calculate.CommResponseData;
import com.cosog.model.calculate.DisplayInstanceOwnItem;
import com.cosog.model.calculate.EnergyCalculateResponseData;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.PCPCalculateResponseData;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.PCPDeviceTodayData;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.RPCCalculateResponseData;
import com.cosog.model.calculate.RPCDeviceInfo;
import com.cosog.model.calculate.RPCDeviceTodayData;
import com.cosog.model.calculate.TimeEffResponseData;
import com.cosog.model.calculate.TotalAnalysisResponseData;
import com.cosog.model.calculate.UserInfo;
import com.cosog.model.drive.AcqGroup;
import com.cosog.model.drive.AcqOnline;
import com.cosog.model.drive.AcquisitionItemInfo;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.datainterface.CalculateDataService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.AcquisitionItemColumnsMap;
import com.cosog.utils.AlarmInfoMap;
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.ProtocolItemResolutionData;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.cosog.websocket.config.WebSocketByJavax;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
	@Bean
    public static WebSocketByJavax infoHandler() {
        return new WebSocketByJavax();
    }
	
	@RequestMapping("/acq/allDeviceOffline")
	public String AllDeviceOffline() throws Exception {
		StringBuffer webSocketSendData = new StringBuffer();
		Jedis jedis=null;
		String functionCode="adExitAndDeviceOffline";
		String time=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("RPCDeviceInfo".getBytes())){
				MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
			}
			List<byte[]> rpcDeviceInfoByteList =jedis.hvals("RPCDeviceInfo".getBytes());
			for(int i=0;i<rpcDeviceInfoByteList.size();i++){
				String realtimeTable="tbl_pumpacqdata_latest";
				String historyTable="tbl_pumpacqdata_hist";
				Object obj = SerializeObjectUnils.unserizlize(rpcDeviceInfoByteList.get(i));
				if (obj instanceof RPCDeviceInfo) {
					RPCDeviceInfo rpcDeviceInfo=(RPCDeviceInfo)obj;
					String instanceCode=rpcDeviceInfo.getInstanceCode();
					if(!(!StringManagerUtils.isNotNull(instanceCode)||instanceCode.toUpperCase().contains("KAFKA")||instanceCode.toUpperCase().contains("RPC") || instanceCode.toUpperCase().contains("MQTT"))){
						if(rpcDeviceInfo.getCommStatus()==1){
							String commRequest="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+rpcDeviceInfo.getWellName()+"\",";
							if(StringManagerUtils.isNotNull(rpcDeviceInfo.getAcqTime())&&StringManagerUtils.isNotNull(rpcDeviceInfo.getCommRange())){
								commRequest+= "\"Last\":{"
										+ "\"AcqTime\": \""+rpcDeviceInfo.getAcqTime()+"\","
										+ "\"CommStatus\": "+(rpcDeviceInfo.getCommStatus()==1?true:false)+","
										+ "\"CommEfficiency\": {"
										+ "\"Efficiency\": "+rpcDeviceInfo.getCommEff()+","
										+ "\"Time\": "+rpcDeviceInfo.getCommTime()+","
										+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(rpcDeviceInfo.getCommRange())+""
										+ "}"
										+ "},";
							}	
							commRequest+= "\"Current\": {"
									+ "\"AcqTime\":\""+time+"\","
									+ "\"CommStatus\":false"
									+ "}"
									+ "}";
							CommResponseData commResponseData=CalculateUtils.commCalculate(commRequest);
							
							String updateRealData="update "+realtimeTable+" t set t.acqTime=to_date('"+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"','yyyy-mm-dd hh24:mi:ss'), t.CommStatus=0";
							String updateRealCommRangeClobSql="update "+realtimeTable+" t set t.commrange=?";
							
							String updateHistData="update "+historyTable+" t set t.acqTime=to_date('"+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"','yyyy-mm-dd hh24:mi:ss'), t.CommStatus=0";
							String updateHistCommRangeClobSql="update "+historyTable+" t set t.commrange=?";
							List<String> clobCont=new ArrayList<String>();
							
							if(commResponseData!=null&&commResponseData.getResultStatus()==1){
								updateRealData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
										+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
								updateHistData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
										+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
								
								clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
							}
							updateRealData+=" where t.wellId= "+rpcDeviceInfo.getId();
							updateRealCommRangeClobSql+=" where t.wellId= "+rpcDeviceInfo.getId();
							
							updateHistData+=" where t.wellId= "+rpcDeviceInfo.getId()+" and t.acqtime=( select max(t2.acqtime) from "+historyTable+" t2 where t2.wellid=t.wellid )";
							updateHistCommRangeClobSql+=" where t.wellId= "+rpcDeviceInfo.getId()+" and t.acqtime=( select max(t2.acqtime) from "+historyTable+" t2 where t2.wellid=t.wellid )";;
							
							int result=commonDataService.getBaseDao().updateOrDeleteBySql(updateRealData);
							result=commonDataService.getBaseDao().updateOrDeleteBySql(updateHistData);
							if(commResponseData!=null&&commResponseData.getResultStatus()==1){
								result=commonDataService.getBaseDao().executeSqlUpdateClob(updateRealCommRangeClobSql,clobCont);
								result=commonDataService.getBaseDao().executeSqlUpdateClob(updateHistCommRangeClobSql,clobCont);
							}
							
							
							rpcDeviceInfo.setCommStatus(0);
							if(commResponseData!=null && commResponseData.getResultStatus()==1){
								rpcDeviceInfo.setCommTime(commResponseData.getCurrent().getCommEfficiency().getTime());
								rpcDeviceInfo.setCommEff(commResponseData.getCurrent().getCommEfficiency().getEfficiency());
								rpcDeviceInfo.setCommRange(commResponseData.getCurrent().getCommEfficiency().getRangeString());
							}
							String key=rpcDeviceInfo.getId()+"";
							jedis.hset("RPCDeviceInfo".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(rpcDeviceInfo));
						}
					}
				}
			}

			if(!jedis.exists("PCPDeviceInfo".getBytes())){
				MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
			}
			List<byte[]> pcpDeviceInfoByteList =jedis.hvals("PCPDeviceInfo".getBytes());
			for(int i=0;i<pcpDeviceInfoByteList.size();i++){
				String realtimeTable="tbl_pumpacqdata_latest";
				String historyTable="tbl_pumpacqdata_hist";
				Object obj = SerializeObjectUnils.unserizlize(pcpDeviceInfoByteList.get(i));
				if (obj instanceof PCPDeviceInfo) {
					PCPDeviceInfo pcpDeviceInfo=(PCPDeviceInfo)obj;
					String instanceCode=pcpDeviceInfo.getInstanceCode();
					if(!(!StringManagerUtils.isNotNull(instanceCode)||instanceCode.toUpperCase().contains("KAFKA")||instanceCode.toUpperCase().contains("RPC") || instanceCode.toUpperCase().contains("MQTT"))){
						if(pcpDeviceInfo.getCommStatus()==1){
							String commRequest="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+pcpDeviceInfo.getWellName()+"\",";
							if(StringManagerUtils.isNotNull(pcpDeviceInfo.getAcqTime())&&StringManagerUtils.isNotNull(pcpDeviceInfo.getCommRange())){
								commRequest+= "\"Last\":{"
										+ "\"AcqTime\": \""+pcpDeviceInfo.getAcqTime()+"\","
										+ "\"CommStatus\": "+(pcpDeviceInfo.getCommStatus()==1?true:false)+","
										+ "\"CommEfficiency\": {"
										+ "\"Efficiency\": "+pcpDeviceInfo.getCommEff()+","
										+ "\"Time\": "+pcpDeviceInfo.getCommTime()+","
										+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(pcpDeviceInfo.getCommRange())+""
										+ "}"
										+ "},";
							}	
							commRequest+= "\"Current\": {"
									+ "\"AcqTime\":\""+time+"\","
									+ "\"CommStatus\":false"
									+ "}"
									+ "}";
							CommResponseData commResponseData=CalculateUtils.commCalculate(commRequest);
							
							String updateRealData="update "+realtimeTable+" t set t.acqTime=to_date('"+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"','yyyy-mm-dd hh24:mi:ss'), t.CommStatus=0";
							String updateRealCommRangeClobSql="update "+realtimeTable+" t set t.commrange=?";
							
							String updateHistData="update "+historyTable+" t set t.acqTime=to_date('"+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"','yyyy-mm-dd hh24:mi:ss'), t.CommStatus=0";
							String updateHistCommRangeClobSql="update "+historyTable+" t set t.commrange=?";
							List<String> clobCont=new ArrayList<String>();
							
							if(commResponseData!=null&&commResponseData.getResultStatus()==1){
								updateRealData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
										+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
								updateHistData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
										+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
								
								clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
							}
							updateRealData+=" where t.wellId= "+pcpDeviceInfo.getId();
							updateRealCommRangeClobSql+=" where t.wellId= "+pcpDeviceInfo.getId();
							
							updateHistData+=" where t.wellId= "+pcpDeviceInfo.getId()+" and t.acqtime=( select max(t2.acqtime) from "+historyTable+" t2 where t2.wellid=t.wellid )";
							updateHistCommRangeClobSql+=" where t.wellId= "+pcpDeviceInfo.getId()+" and t.acqtime=( select max(t2.acqtime) from "+historyTable+" t2 where t2.wellid=t.wellid )";;
							
							int result=commonDataService.getBaseDao().updateOrDeleteBySql(updateRealData);
							result=commonDataService.getBaseDao().updateOrDeleteBySql(updateHistData);
							if(commResponseData!=null&&commResponseData.getResultStatus()==1){
								result=commonDataService.getBaseDao().executeSqlUpdateClob(updateRealCommRangeClobSql,clobCont);
								result=commonDataService.getBaseDao().executeSqlUpdateClob(updateHistCommRangeClobSql,clobCont);
							}
							
							
							pcpDeviceInfo.setCommStatus(0);
							if(commResponseData!=null && commResponseData.getResultStatus()==1){
								pcpDeviceInfo.setCommTime(commResponseData.getCurrent().getCommEfficiency().getTime());
								pcpDeviceInfo.setCommEff(commResponseData.getCurrent().getCommEfficiency().getEfficiency());
								pcpDeviceInfo.setCommRange(commResponseData.getCurrent().getCommEfficiency().getRangeString());
							}
							String key=pcpDeviceInfo.getId()+"";
							jedis.hset("PCPDeviceInfo".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(pcpDeviceInfo));
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			jedis=null;
		}
		
		webSocketSendData.append("{\"functionCode\":\""+functionCode+"\",");
		webSocketSendData.append("\"time\":\""+time+"\"");
		webSocketSendData.append("}");
		if(StringManagerUtils.isNotNull(webSocketSendData.toString())){
			infoHandler().sendMessageToBy("ApWebSocketClient", webSocketSendData.toString());
		}
		if(jedis!=null){
			jedis.close();
		}
		String json = "{success:true,flag:true}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/acq/online")
	public String AcqOnlineData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		Gson gson=new Gson();
		StringBuffer webSocketSendData = new StringBuffer();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8");
		StringManagerUtils.printLog("接收到ad推送online数据："+data);
		java.lang.reflect.Type type = new TypeToken<AcqOnline>() {}.getType();
		AcqOnline acqOnline=gson.fromJson(data, type);
		Jedis jedis=null;
		AlarmShowStyle alarmShowStyle=null;
		AlarmInstanceOwnItem alarmInstanceOwnItem=null;
		if(acqOnline!=null){
			try{
				jedis = RedisUtil.jedisPool.getResource();
				RPCDeviceInfo rpcDeviceInfo=null;
				PCPDeviceInfo pcpDeviceInfo=null;
				int deviceType=101;
				int deviceId=0;
				String wellName="";
				int orgId=0;
				String currentTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
				}
				alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
				
				if(!jedis.exists("RPCDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
				}
				List<byte[]> rpcDeviceInfoByteList =jedis.hvals("RPCDeviceInfo".getBytes());
				for(int i=0;i<rpcDeviceInfoByteList.size();i++){
					Object obj = SerializeObjectUnils.unserizlize(rpcDeviceInfoByteList.get(i));
					if (obj instanceof RPCDeviceInfo) {
						RPCDeviceInfo memRPCDeviceInfo=(RPCDeviceInfo)obj;
						if(acqOnline.getID().equalsIgnoreCase(memRPCDeviceInfo.getSignInId()) && acqOnline.getSlave()==StringManagerUtils.stringToInteger(memRPCDeviceInfo.getSlave())){
							rpcDeviceInfo=memRPCDeviceInfo;
							rpcDeviceInfo.setCommStatus(acqOnline.getStatus()?1:0);
							deviceType=rpcDeviceInfo.getDeviceType();
							deviceId=rpcDeviceInfo.getId();
							wellName=rpcDeviceInfo.getWellName();
							orgId=rpcDeviceInfo.getOrgId();
							break;
						}
					}
				}
				
				
				if(rpcDeviceInfo==null){
					if(!jedis.exists("PCPDeviceInfo".getBytes())){
						MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
					}
					List<byte[]> pcpDeviceInfoByteList =jedis.hvals("PCPDeviceInfo".getBytes());
					for(int i=0;i<pcpDeviceInfoByteList.size();i++){
						Object obj = SerializeObjectUnils.unserizlize(pcpDeviceInfoByteList.get(i));
						if (obj instanceof PCPDeviceInfo) {
							PCPDeviceInfo memPCPDeviceInfo=(PCPDeviceInfo)obj;
							if(acqOnline.getID().equalsIgnoreCase(memPCPDeviceInfo.getSignInId()) && acqOnline.getSlave()==StringManagerUtils.stringToInteger(memPCPDeviceInfo.getSlave())){
								pcpDeviceInfo=memPCPDeviceInfo;
								pcpDeviceInfo.setCommStatus(acqOnline.getStatus()?1:0);
								deviceType=pcpDeviceInfo.getDeviceType();
								deviceId=pcpDeviceInfo.getId();
								wellName=pcpDeviceInfo.getWellName();
								orgId=pcpDeviceInfo.getOrgId();
								break;
							}
						}
					}
				}
				
				
				if(rpcDeviceInfo!=null || pcpDeviceInfo!=null){
					if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
						MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
					}
					
					String realtimeTable="";
					String historyTable="";
					
					String functionCode="rpcDeviceRealTimeMonitoringStatusData";
					String alarmTableName="tbl_rpcalarminfo_hist";
					String commRequest="";
					if(deviceType>=100 && deviceType<200){//如果是抽油机
						alarmTableName="tbl_rpcalarminfo_hist";
						realtimeTable="tbl_rpcacqdata_latest";
						historyTable="tbl_rpcacqdata_hist";
						functionCode="rpcDeviceRealTimeMonitoringStatusData";
						if(jedis!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), rpcDeviceInfo.getAlarmInstanceCode().getBytes())){
							alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), rpcDeviceInfo.getAlarmInstanceCode().getBytes()));
						}
						commRequest="{"
								+ "\"AKString\":\"\","
								+ "\"WellName\":\""+rpcDeviceInfo.getWellName()+"\",";
						if(StringManagerUtils.isNotNull(rpcDeviceInfo.getAcqTime())&&StringManagerUtils.isNotNull(rpcDeviceInfo.getCommRange())){
							commRequest+= "\"Last\":{"
									+ "\"AcqTime\": \""+rpcDeviceInfo.getAcqTime()+"\","
									+ "\"CommStatus\": "+(rpcDeviceInfo.getCommStatus()==1?true:false)+","
									+ "\"CommEfficiency\": {"
									+ "\"Efficiency\": "+rpcDeviceInfo.getCommEff()+","
									+ "\"Time\": "+rpcDeviceInfo.getCommTime()+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(rpcDeviceInfo.getCommRange())+""
									+ "}"
									+ "},";
						}	
						commRequest+= "\"Current\": {"
								+ "\"AcqTime\":\""+commRequest+"\","
								+ "\"CommStatus\":false"
								+ "}"
								+ "}";
					}else if(deviceType>=200 && deviceType<300){//否则螺杆泵
						alarmTableName="tbl_pcpalarminfo_hist";
						realtimeTable="tbl_pcpacqdata_latest";
						historyTable="tbl_pcpacqdata_hist";
						functionCode="pcpDeviceRealTimeMonitoringStatusData";
						if(jedis!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), pcpDeviceInfo.getAlarmInstanceCode().getBytes())){
							alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), pcpDeviceInfo.getAlarmInstanceCode().getBytes()));
						}
						
						commRequest="{"
								+ "\"AKString\":\"\","
								+ "\"WellName\":\""+pcpDeviceInfo.getWellName()+"\",";
						if(StringManagerUtils.isNotNull(pcpDeviceInfo.getAcqTime())&&StringManagerUtils.isNotNull(pcpDeviceInfo.getCommRange())){
							commRequest+= "\"Last\":{"
									+ "\"AcqTime\": \""+pcpDeviceInfo.getAcqTime()+"\","
									+ "\"CommStatus\": "+(pcpDeviceInfo.getCommStatus()==1?true:false)+","
									+ "\"CommEfficiency\": {"
									+ "\"Efficiency\": "+pcpDeviceInfo.getCommEff()+","
									+ "\"Time\": "+pcpDeviceInfo.getCommTime()+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(pcpDeviceInfo.getCommRange())+""
									+ "}"
									+ "},";
						}	
						commRequest+= "\"Current\": {"
								+ "\"AcqTime\":\""+commRequest+"\","
								+ "\"CommStatus\":false"
								+ "}"
								+ "}";
					}
					
					
					CommResponseData commResponseData=CalculateUtils.commCalculate(commRequest);
					
					String updateRealData="update "+realtimeTable+" t set t.acqTime=to_date('"+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"','yyyy-mm-dd hh24:mi:ss'), t.CommStatus=0";
					String updateRealCommRangeClobSql="update "+realtimeTable+" t set t.commrange=?";
					
					String updateHistData="update "+historyTable+" t set t.acqTime=to_date('"+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"','yyyy-mm-dd hh24:mi:ss'), t.CommStatus=0";
					String updateHistCommRangeClobSql="update "+historyTable+" t set t.commrange=?";
					List<String> clobCont=new ArrayList<String>();
					
					if(commResponseData!=null&&commResponseData.getResultStatus()==1){
						updateRealData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
								+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
						updateHistData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
								+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
						
						clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
					}
					updateRealData+=" where t.wellId= "+deviceId;
					updateRealCommRangeClobSql+=" where t.wellId= "+deviceId;
					
					updateHistData+=" where t.wellId= "+deviceId+" and t.acqtime=( select max(t2.acqtime) from "+historyTable+" t2 where t2.wellid=t.wellid )";
					updateHistCommRangeClobSql+=" where t.wellId= "+deviceId+" and t.acqtime=( select max(t2.acqtime) from "+historyTable+" t2 where t2.wellid=t.wellid )";
					
					int result=commonDataService.getBaseDao().updateOrDeleteBySql(updateRealData);
					result=commonDataService.getBaseDao().updateOrDeleteBySql(updateHistData);
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
							key=wellName+","+deviceType+",上线";
							alarmInfo="上线";
							alarmSMSContent="设备"+wellName+"于"+currentTime+"上线";
						}else{
							key=wellName+","+deviceType+",离线";
							alarmInfo="离线";
							alarmSMSContent="设备"+wellName+"于"+currentTime+"离线";
						}
						for(int i=0;i<alarmInstanceOwnItem.getItemList().size();i++){
							if(alarmInstanceOwnItem.getItemList().get(i).getType()==3 &&   alarmInfo.equalsIgnoreCase(alarmInstanceOwnItem.getItemList().get(i).getItemName()) && alarmInstanceOwnItem.getItemList().get(i).getAlarmLevel()>0){
								commAlarmLevel=alarmInstanceOwnItem.getItemList().get(i).getAlarmLevel();
								isSendMessage=alarmInstanceOwnItem.getItemList().get(i).getIsSendMessage();
								isSendMail=alarmInstanceOwnItem.getItemList().get(i).getIsSendMail();
								delay=alarmInstanceOwnItem.getItemList().get(i).getDelay();
								break;
							}
						}
						commAlarm="insert into "+alarmTableName+" (wellid,alarmtime,itemname,alarmtype,alarmvalue,alarminfo,alarmlevel)"
								+ "values("+deviceId+",to_date('"+currentTime+"','yyyy-mm-dd hh24:mi:ss'),'通信状态',3,"+(acqOnline.getStatus()?1:0)+",'"+alarmInfo+"',"+commAlarmLevel+")";
						
						
						String lastAlarmTime=alarmInfoMap.get(key);
						long timeDiff=StringManagerUtils.getTimeDifference(lastAlarmTime, currentTime, "yyyy-MM-dd HH:mm:ss");
						if(commAlarmLevel>0&&timeDiff>delay*1000){
							result=commonDataService.getBaseDao().updateOrDeleteBySql(commAlarm);
							calculateDataService.sendAlarmSMS(wellName, deviceType+"",isSendMessage==1,isSendMail==1,alarmSMSContent,alarmSMSContent);
							alarmInfoMap.put(key, currentTime);
						}
					}
					
					if(rpcDeviceInfo!=null){
						rpcDeviceInfo.setCommStatus(0);
						if(commResponseData!=null && commResponseData.getResultStatus()==1){
							rpcDeviceInfo.setCommTime(commResponseData.getCurrent().getCommEfficiency().getTime());
							rpcDeviceInfo.setCommEff(commResponseData.getCurrent().getCommEfficiency().getEfficiency());
							rpcDeviceInfo.setCommRange(commResponseData.getCurrent().getCommEfficiency().getRangeString());
						}
						jedis.hset("RPCDeviceInfo".getBytes(), (rpcDeviceInfo.getId()+"").getBytes(), SerializeObjectUnils.serialize(rpcDeviceInfo));
					}
					
					if(pcpDeviceInfo!=null){
						pcpDeviceInfo.setCommStatus(0);
						if(commResponseData!=null && commResponseData.getResultStatus()==1){
							pcpDeviceInfo.setCommTime(commResponseData.getCurrent().getCommEfficiency().getTime());
							pcpDeviceInfo.setCommEff(commResponseData.getCurrent().getCommEfficiency().getEfficiency());
							pcpDeviceInfo.setCommRange(commResponseData.getCurrent().getCommEfficiency().getRangeString());
						}
						jedis.hset("PCPDeviceInfo".getBytes(), (pcpDeviceInfo.getId()+"").getBytes(), SerializeObjectUnils.serialize(pcpDeviceInfo));
					}
					
					webSocketSendData.append("{\"functionCode\":\""+functionCode+"\",");
					webSocketSendData.append("\"wellName\":\""+wellName+"\",");
					webSocketSendData.append("\"orgId\":"+orgId+",");
					webSocketSendData.append("\"acqTime\":\""+currentTime+"\",");
					webSocketSendData.append("\"commStatus\":"+(acqOnline.getStatus()?1:0)+",");
					
					webSocketSendData.append("\"commAlarmLevel\":"+commAlarmLevel);
					webSocketSendData.append("}");
					if(StringManagerUtils.isNotNull(webSocketSendData.toString())){
						infoHandler().sendMessageToBy("ApWebSocketClient", webSocketSendData.toString());
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				jedis=null;
			}
		}
		if(jedis!=null){
			jedis.close();
		}
		String json = "{success:true,flag:true}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/acq/group")
	public String AcqGroupData() throws Exception{
		ServletInputStream ss = request.getInputStream();
		Gson gson=new Gson();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8");
		StringManagerUtils.printLog(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"接收到ad推送group数据："+data);
		java.lang.reflect.Type type = new TypeToken<AcqGroup>() {}.getType();
		AcqGroup acqGroup=gson.fromJson(data, type);
		String json = "{success:true,flag:true}";
		Jedis jedis=null;
		if(acqGroup!=null){
			try{
				jedis = RedisUtil.jedisPool.getResource();
				RPCDeviceInfo rpcDeviceInfo=null;
				PCPDeviceInfo pcpDeviceInfo=null;
				
				if(!jedis.exists("RPCDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
				}
				List<byte[]> rpcDeviceInfoByteList =jedis.hvals("RPCDeviceInfo".getBytes());
				for(int i=0;i<rpcDeviceInfoByteList.size();i++){
					Object obj = SerializeObjectUnils.unserizlize(rpcDeviceInfoByteList.get(i));
					if (obj instanceof RPCDeviceInfo) {
						RPCDeviceInfo memRPCDeviceInfo=(RPCDeviceInfo)obj;
						if(acqGroup.getID().equalsIgnoreCase(memRPCDeviceInfo.getSignInId()) && acqGroup.getSlave()==StringManagerUtils.stringToInteger(memRPCDeviceInfo.getSlave())){
							rpcDeviceInfo=memRPCDeviceInfo;
							break;
						}
					}
				}
				
				
				if(rpcDeviceInfo==null){
					if(!jedis.exists("PCPDeviceInfo".getBytes())){
						MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
					}
					List<byte[]> pcpDeviceInfoByteList =jedis.hvals("PCPDeviceInfo".getBytes());
					for(int i=0;i<pcpDeviceInfoByteList.size();i++){
						Object obj = SerializeObjectUnils.unserizlize(pcpDeviceInfoByteList.get(i));
						if (obj instanceof PCPDeviceInfo) {
							PCPDeviceInfo memPCPDeviceInfo=(PCPDeviceInfo)obj;
							if(acqGroup.getID().equalsIgnoreCase(memPCPDeviceInfo.getSignInId()) && acqGroup.getSlave()==StringManagerUtils.stringToInteger(memPCPDeviceInfo.getSlave())){
								pcpDeviceInfo=memPCPDeviceInfo;
								break;
							}
						}
					}
				}
				
				if(rpcDeviceInfo!=null){
					this.RPCDataProcessing(rpcDeviceInfo,acqGroup);
				}
				if(pcpDeviceInfo!=null){
					this.PCPDataProcessing(pcpDeviceInfo,acqGroup);
				}
			}catch(Exception e){
				e.printStackTrace();
				jedis=null;
			}
			
		}else{
			json = "{success:true,flag:false}";
		}
		if(jedis!=null){
			jedis.close();
		}
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	};
	
	@SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	public String RPCDataProcessing(RPCDeviceInfo rpcDeviceInfo,AcqGroup acqGroup) throws Exception{
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		String productionUnit=Config.getInstance().configFile.getAp().getOthers().getProductionUnit();
		List<String> websocketClientUserList=new ArrayList<>();
		for (WebSocketByJavax item : WebSocketByJavax.clients.values()) { 
            String[] clientInfo=item.userId.split("_");
            if(clientInfo!=null && clientInfo.length==3 && !StringManagerUtils.existOrNot(websocketClientUserList, clientInfo[1], true)){
            	websocketClientUserList.add(clientInfo[1]);
            }
        }
		
		StringBuffer webSocketSendData = new StringBuffer();
		StringBuffer displayItemInfo_json = new StringBuffer();
		StringBuffer allItemInfo_json = new StringBuffer();

		StringBuffer wellBoreChartsData = new StringBuffer();
		StringBuffer surfaceChartsData = new StringBuffer();
		boolean save=false;
		boolean alarm=false;
		boolean sendMessage=false;
		Jedis jedis=null;
		AlarmShowStyle alarmShowStyle=null;
		RPCDeviceTodayData deviceTodayData=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("AlarmShowStyle".getBytes())){
				MemoryDataManagerTask.initAlarmStyle();
			}
			alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
			
			if(!jedis.exists("RPCDeviceTodayData".getBytes())){
				MemoryDataManagerTask.loadTodayFESDiagram(null,0);
			}
			if(jedis.hexists("RPCDeviceTodayData".getBytes(), (rpcDeviceInfo.getId()+"").getBytes())){
				deviceTodayData=(RPCDeviceTodayData) SerializeObjectUnils.unserizlize(jedis.hget("RPCDeviceTodayData".getBytes(), (rpcDeviceInfo.getId()+"").getBytes()));
			}
			
			if(!jedis.exists("RPCWorkType".getBytes())){
				MemoryDataManagerTask.loadRPCWorkType();
			}
			
			if(!jedis.exists("rpcCalItemList".getBytes())){
				MemoryDataManagerTask.loadRPCCalculateItem();
			}
			
			if(!jedis.exists("UserInfo".getBytes())){
				MemoryDataManagerTask.loadUserInfo(null,0,"update");
			}
			
			if(!jedis.exists("AcqInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadAcqInstanceOwnItemById("","update");
			}
			
			if(!jedis.exists("DisplayInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadDisplayInstanceOwnItemById("","update");
			}
			
			if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
			}
			
			if(!jedis.exists("ProtocolMappingColumn".getBytes())){
				MemoryDataManagerTask.loadProtocolMappingColumn();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String realtimeTable="tbl_rpcacqdata_latest";
		String historyTable="tbl_rpcacqdata_hist";
		String rawDataTable="tbl_rpcacqrawdata";
		String totalDataTable="tbl_rpcdailycalculationdata";
		String functionCode="rpcDeviceRealTimeMonitoringData";
		String columnsKey="rpcDeviceAcquisitionItemColumns";
		int DeviceType=0;
		if(rpcDeviceInfo.getDeviceType()>=100 && rpcDeviceInfo.getDeviceType()<200){
			DeviceType=0;
		}else if(rpcDeviceInfo.getDeviceType()>=200 && rpcDeviceInfo.getDeviceType()<300){
			DeviceType=1;
		}
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
			EquipmentDriverServerTask.loadAcquisitionItemColumns(DeviceType);
		}
		Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
		if(acqGroup!=null){
			Set<byte[]>rpcCalItemSet=null;
			if(jedis!=null){
				rpcCalItemSet= jedis.zrange("rpcCalItemList".getBytes(), 0, -1);
			}
			String protocolName="";
			AcqInstanceOwnItem acqInstanceOwnItem=null;
			if(jedis!=null&&jedis.hexists("AcqInstanceOwnItem".getBytes(), rpcDeviceInfo.getInstanceCode().getBytes())){
				acqInstanceOwnItem=(AcqInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AcqInstanceOwnItem".getBytes(), rpcDeviceInfo.getInstanceCode().getBytes()));
				protocolName=acqInstanceOwnItem.getProtocol();
			}
			DisplayInstanceOwnItem displayInstanceOwnItem=null;
			if(jedis!=null&&jedis.hexists("DisplayInstanceOwnItem".getBytes(), rpcDeviceInfo.getDisplayInstanceCode().getBytes())){
				displayInstanceOwnItem=(DisplayInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("DisplayInstanceOwnItem".getBytes(), rpcDeviceInfo.getDisplayInstanceCode().getBytes()));
			}
			
			AlarmInstanceOwnItem alarmInstanceOwnItem=null;
			if(jedis!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), rpcDeviceInfo.getAlarmInstanceCode().getBytes())){
				alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), rpcDeviceInfo.getAlarmInstanceCode().getBytes()));
			}
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			
			ModbusProtocolConfig.Protocol protocol=null;
			if(StringManagerUtils.isNotNull(protocolName)){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
						protocol=modbusProtocolConfig.getProtocol().get(i);
						break;
					}
				}
			}
			
			if(protocol!=null){
				String lastSaveTime=rpcDeviceInfo.getSaveTime();
				int save_cycle=acqInstanceOwnItem.getSaveCycle();
				String acqTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
				String date=StringManagerUtils.getCurrentTime("yyyy-MM-dd");
				long timeDiff=StringManagerUtils.getTimeDifference(lastSaveTime, acqTime, "yyyy-MM-dd HH:mm:ss");
				if(timeDiff>save_cycle*1000){
					save=true;
				}
				
				RPCCalculateRequestData rpcCalculateRequestData=new RPCCalculateRequestData();
				rpcCalculateRequestData.init();
				
				
				rpcCalculateRequestData.setWellName(rpcDeviceInfo.getWellName());
				rpcCalculateRequestData.setFluidPVT(rpcDeviceInfo.getFluidPVT());
				rpcCalculateRequestData.setReservoir(rpcDeviceInfo.getReservoir());
				rpcCalculateRequestData.setRodString(rpcDeviceInfo.getRodString());
				rpcCalculateRequestData.setTubingString(rpcDeviceInfo.getTubingString());
				rpcCalculateRequestData.setCasingString(rpcDeviceInfo.getCasingString());
				rpcCalculateRequestData.setPump(rpcDeviceInfo.getPump());
				rpcCalculateRequestData.setPumpingUnit(rpcDeviceInfo.getPumpingUnit());
				rpcCalculateRequestData.setProduction(rpcDeviceInfo.getProduction());
				rpcCalculateRequestData.setManualIntervention(rpcDeviceInfo.getManualIntervention());
				
				RPCCalculateResponseData rpcCalculateResponseData=null;
				CommResponseData commResponseData=null;
				TimeEffResponseData timeEffResponseData=null;
				EnergyCalculateResponseData energyCalculateResponseData=null;
				TotalAnalysisResponseData totalAnalysisResponseData=null;
				
				boolean isAcqRunStatus=false,isAcqEnergy=false;
				int runStatus=0;
				float totalKWattH=0;
				
				//进行通信计算
				String commRequest="{"
						+ "\"AKString\":\"\","
						+ "\"WellName\":\""+rpcDeviceInfo.getWellName()+"\",";
				if(StringManagerUtils.isNotNull(rpcDeviceInfo.getAcqTime())&&StringManagerUtils.isNotNull(rpcDeviceInfo.getCommRange())){
					commRequest+= "\"Last\":{"
							+ "\"AcqTime\": \""+rpcDeviceInfo.getAcqTime()+"\","
							+ "\"CommStatus\": "+(rpcDeviceInfo.getCommStatus()==1?true:false)+","
							+ "\"CommEfficiency\": {"
							+ "\"Efficiency\": "+rpcDeviceInfo.getCommEff()+","
							+ "\"Time\": "+rpcDeviceInfo.getCommTime()+","
							+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(rpcDeviceInfo.getCommRange())+""
							+ "}"
							+ "},";
				}	
				commRequest+= "\"Current\": {"
						+ "\"AcqTime\":\""+acqTime+"\","
						+ "\"CommStatus\":true"
						+ "}"
						+ "}";
				commResponseData=CalculateUtils.commCalculate(commRequest);
				
				String updateRealtimeData="update "+realtimeTable+" t set t.acqTime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),t.CommStatus=1";
				
				
				String insertHistColumns="wellid,acqTime,CommStatus";
				String insertHistValue=rpcDeviceInfo.getId()+",to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),1";
				String insertHistSql="";
				
				String updateTotalDataSql="update "+totalDataTable+" t set t.CommStatus=1";
				
				List<AcquisitionItemInfo> acquisitionItemInfoList=new ArrayList<AcquisitionItemInfo>();
				List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
				int FESDiagramAcqCount=0;
				for(int i=0;acqGroup.getAddr()!=null &&i<acqGroup.getAddr().size();i++){
					for(int j=0;j<protocol.getItems().size();j++){
						if(acqGroup.getAddr().get(i)==protocol.getItems().get(j).getAddr()){
							String value="";
							String columnName=loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(j).getTitle());
							
							DataMapping dataMappingColumn=(DataMapping)SerializeObjectUnils.unserizlize(jedis.hget("ProtocolMappingColumn".getBytes(), (protocol.getDeviceType()+"_"+columnName).getBytes()));
							
							if(acqGroup.getValue()!=null&&acqGroup.getValue().size()>i&&acqGroup.getValue().get(i)!=null){
								value=StringManagerUtils.objectListToString(acqGroup.getValue().get(i), protocol.getItems().get(j).getIFDataType());
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
								updateRealtimeData+=",t."+columnName+"='"+rawValue+"'";
								insertHistColumns+=","+columnName;
								insertHistValue+=",'"+rawValue+"'";
								if(protocol.getItems().get(j).getResolutionMode()==1||protocol.getItems().get(j).getResolutionMode()==2){//如果是枚举量或数据量
									if(protocol.getItems().get(j).getMeaning()!=null&&protocol.getItems().get(j).getMeaning().size()>0){
										for(int l=0;l<protocol.getItems().get(j).getMeaning().size();l++){
											if(StringManagerUtils.isNotNull(value)&&StringManagerUtils.stringToFloat(value)==(protocol.getItems().get(j).getMeaning().get(l).getValue())){
												value=protocol.getItems().get(j).getMeaning().get(l).getMeaning();
												break;
											}
										}
									}
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort);
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
														ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort);
														protocolItemResolutionDataList.add(protocolItemResolutionData);
														match=true;
														break;
													}
												}
												if(!match){
													value="";
													rawValue="";
													bitIndex=protocol.getItems().get(j).getMeaning().get(l).getValue()+"";
													ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort);
													protocolItemResolutionDataList.add(protocolItemResolutionData);
												}
											}
										}
									}else{
										ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort);
										protocolItemResolutionDataList.add(protocolItemResolutionData);
									}
								}else{
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort);
									protocolItemResolutionDataList.add(protocolItemResolutionData);
								}
								
								if("runStatus".equalsIgnoreCase(dataMappingColumn.getCalColumn())){//运行状态
									isAcqRunStatus=true;
									runStatus=StringManagerUtils.stringToInteger(rawValue);
								}else if("totalKWattH".equalsIgnoreCase(dataMappingColumn.getCalColumn())){//累计有功功耗
									isAcqEnergy=true;
									totalKWattH=StringManagerUtils.stringToFloat(rawValue);
								}else if("TubingPressure".equalsIgnoreCase(dataMappingColumn.getCalColumn())){//油压
									rpcCalculateRequestData.getProduction().setTubingPressure(StringManagerUtils.stringToFloat(rawValue));
									rpcDeviceInfo.getProduction().setTubingPressure(StringManagerUtils.stringToFloat(rawValue));
								}else if("CasingPressure".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									rpcCalculateRequestData.getProduction().setCasingPressure(StringManagerUtils.stringToFloat(rawValue));
									rpcDeviceInfo.getProduction().setCasingPressure(StringManagerUtils.stringToFloat(rawValue));
								}else if("ProducingfluidLevel".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									rpcCalculateRequestData.getProduction().setProducingfluidLevel(StringManagerUtils.stringToFloat(rawValue));
									insertHistValue+=","+rawValue+"";
								}else if("volumeWaterCut".equalsIgnoreCase(dataMappingColumn.getCalColumn()) || "waterCut".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									rpcCalculateRequestData.getProduction().setWaterCut(StringManagerUtils.stringToFloat(rawValue));
									rpcDeviceInfo.getProduction().setWaterCut(StringManagerUtils.stringToFloat(rawValue));
								}else if("FESDiagramAcqCount".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									FESDiagramAcqCount=StringManagerUtils.stringToInteger(rawValue);
								}else if("FESDiagramAcqtime".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									rpcCalculateRequestData.getFESDiagram().setAcqTime(rawValue);
									rpcCalculateRequestData.getFESDiagram().setAcqTime(acqTime);
								}else if("stroke".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									rpcCalculateRequestData.getFESDiagram().setStroke(StringManagerUtils.stringToFloat(rawValue));
								}else if("spm".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									rpcCalculateRequestData.getFESDiagram().setSPM(StringManagerUtils.stringToFloat(rawValue));
								}else if("position_curve".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									if(StringManagerUtils.isNotNull(rawValue)){
										String[] dataArr=rawValue.split(",");
										for(int k=0;k<dataArr.length;k++){
											rpcCalculateRequestData.getFESDiagram().getS().add(StringManagerUtils.stringToFloat(dataArr[k]));
										}
									}
								}else if("load_curve".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									if(StringManagerUtils.isNotNull(rawValue)){
										String[] dataArr=rawValue.split(",");
										for(int k=0;k<dataArr.length;k++){
											rpcCalculateRequestData.getFESDiagram().getF().add(StringManagerUtils.stringToFloat(dataArr[k]));
										}
									}
								}else if("power_curve".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									if(StringManagerUtils.isNotNull(rawValue)){
										String[] dataArr=rawValue.split(",");
										for(int k=0;k<dataArr.length;k++){
											rpcCalculateRequestData.getFESDiagram().getWatt().add(StringManagerUtils.stringToFloat(dataArr[k]));
										}
									}
								}else if("current_curve".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									if(StringManagerUtils.isNotNull(rawValue)){
										String[] dataArr=rawValue.split(",");
										for(int k=0;k<dataArr.length;k++){
											rpcCalculateRequestData.getFESDiagram().getI().add(StringManagerUtils.stringToFloat(dataArr[k]));
										}
									}
								}
							}
							break;
						}
					}
				}
				//判断是否采集了运行状态，如采集则进行时率计算
				if(isAcqRunStatus){
					String tiemEffRequest="{"
							+ "\"AKString\":\"\","
							+ "\"WellName\":\""+rpcDeviceInfo.getWellName()+"\",";
					if(StringManagerUtils.isNotNull(rpcDeviceInfo.getAcqTime())&&StringManagerUtils.isNotNull(rpcDeviceInfo.getRunRange())){
						tiemEffRequest+= "\"Last\":{"
								+ "\"AcqTime\": \""+rpcDeviceInfo.getAcqTime()+"\","
								+ "\"RunStatus\": "+(rpcDeviceInfo.getRunStatus()==1?true:false)+","
								+ "\"RunEfficiency\": {"
								+ "\"Efficiency\": "+rpcDeviceInfo.getRunEff()+","
								+ "\"Time\": "+rpcDeviceInfo.getRunTime()+","
								+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(rpcDeviceInfo.getRunRange())+""
								+ "}"
								+ "},";
					}	
					tiemEffRequest+= "\"Current\": {"
							+ "\"AcqTime\":\""+acqTime+"\","
							+ "\"RunStatus\":"+(runStatus==1?true:false)+""
							+ "}"
							+ "}";
					timeEffResponseData=CalculateUtils.runCalculate(tiemEffRequest);
				}
				
				//判断是否采集了电量，如采集则进行电量计算
				if(isAcqEnergy){
					String energyRequest="{"
							+ "\"AKString\":\"\","
							+ "\"WellName\":\""+rpcDeviceInfo.getWellName()+"\",";
					if(StringManagerUtils.isNotNull(rpcDeviceInfo.getAcqTime())){
						energyRequest+= "\"Last\":{"
								+ "\"AcqTime\": \""+rpcDeviceInfo.getAcqTime()+"\","
								+ "\"Total\":{"
								+ "\"KWattH\":"+rpcDeviceInfo.getTotalKWattH()
								+ "},\"Today\":{"
								+ "\"KWattH\":"+rpcDeviceInfo.getTodayKWattH()
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
				}
				
				//进行功图计算
				WorkType workType=null;
				if(StringManagerUtils.isNotNull(rpcCalculateRequestData.getFESDiagram().getAcqTime())
						&& rpcCalculateRequestData.getFESDiagram().getS().size()>0
						&& rpcCalculateRequestData.getFESDiagram().getF().size()>0){
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
					}
					rpcCalculateResponseData=CalculateUtils.fesDiagramCalculate(gson.toJson(rpcCalculateRequestData));
					if(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1){
						if(jedis.hexists("RPCWorkType".getBytes(), (rpcCalculateResponseData.getCalculationStatus().getResultCode()+"").getBytes())){
							workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkType".getBytes(), (rpcCalculateResponseData.getCalculationStatus().getResultCode()+"").getBytes()));
						}

						//删除非当天采集的功图数据
						if(deviceTodayData!=null){
							Iterator<RPCCalculateResponseData> it = deviceTodayData.getRPCCalculateList().iterator();
							while(it.hasNext()){
								RPCCalculateResponseData responseData=(RPCCalculateResponseData)it.next();
								if(responseData.getFESDiagram()==null 
										|| !StringManagerUtils.isNotNull(responseData.getFESDiagram().getAcqTime())
										|| responseData.getFESDiagram().getAcqTime().indexOf(date)<0  ){
									it.remove();
								}
							}
							deviceTodayData.getRPCCalculateList().add(rpcCalculateResponseData);
						}else{
							deviceTodayData=new RPCDeviceTodayData();
							deviceTodayData.setId(rpcDeviceInfo.getId());
							deviceTodayData.setRPCCalculateList(new ArrayList<RPCCalculateResponseData>());
							deviceTodayData.setAcquisitionItemInfoList(new ArrayList<AcquisitionItemInfo>());
							deviceTodayData.getRPCCalculateList().add(rpcCalculateResponseData);
						}
						
					}
				}
				
				
				List<ProtocolItemResolutionData> calItemResolutionDataList=getFESDiagramCalItemData(rpcCalculateRequestData,rpcCalculateResponseData);
				
				//更新内存数据
				//功图
				if(rpcCalculateResponseData!=null){
					rpcDeviceInfo.setResultStatus(rpcCalculateResponseData.getCalculationStatus().getResultStatus());
					rpcDeviceInfo.setResultCode(rpcCalculateResponseData.getCalculationStatus().getResultCode());
				}else{
					rpcDeviceInfo.setResultStatus(0);
					rpcDeviceInfo.setResultCode(0);
				}
				//通信
				rpcDeviceInfo.setAcqTime(acqTime);
				rpcDeviceInfo.setCommStatus(1);
				calItemResolutionDataList.add(new ProtocolItemResolutionData("通信状态","通信状态","在线","1","","commStatusName","","","","",1));
				if(commResponseData!=null&&commResponseData.getResultStatus()==1){
					updateRealtimeData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
							+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
					insertHistColumns+=",commTimeEfficiency,commTime";
					insertHistValue+=","+commResponseData.getCurrent().getCommEfficiency().getEfficiency()+","+commResponseData.getCurrent().getCommEfficiency().getTime();
					
					updateTotalDataSql+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
							+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
					
					rpcDeviceInfo.setCommTime(commResponseData.getCurrent().getCommEfficiency().getTime());
					rpcDeviceInfo.setCommEff(commResponseData.getCurrent().getCommEfficiency().getEfficiency());
					rpcDeviceInfo.setCommRange(commResponseData.getCurrent().getCommEfficiency().getRangeString());
					
					calItemResolutionDataList.add(new ProtocolItemResolutionData("通信时间","通信时间",commResponseData.getCurrent().getCommEfficiency().getTime()+"",commResponseData.getCurrent().getCommEfficiency().getTime()+"","","commTime","","","","",1));
					calItemResolutionDataList.add(new ProtocolItemResolutionData("通信时率","通信时率",commResponseData.getCurrent().getCommEfficiency().getEfficiency()+"",commResponseData.getCurrent().getCommEfficiency().getEfficiency()+"","","commtimeEfficiency","","","","",1));
					calItemResolutionDataList.add(new ProtocolItemResolutionData("通信区间","通信区间",commResponseData.getCurrent().getCommEfficiency().getRangeString(),commResponseData.getCurrent().getCommEfficiency().getRangeString(),"","commRange","","","","",1));
				}
				//如果进行了时率计算
				if(isAcqRunStatus){
					updateRealtimeData+=",t.runStatus= "+runStatus;
					insertHistColumns+=",runStatus";
					insertHistValue+=","+runStatus;
					updateTotalDataSql+=",t.runStatus= "+runStatus;
					
					rpcDeviceInfo.setRunStatus(runStatus);
					
					calItemResolutionDataList.add(new ProtocolItemResolutionData("运行状态","运行状态",runStatus==1?"运行":"停抽",runStatus+"","","runStatusName","","","","",1));
				}
				if(timeEffResponseData!=null && timeEffResponseData.getResultStatus()==1){
					updateRealtimeData+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
							+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
					insertHistColumns+=",runTimeEfficiency,runTime";
					insertHistValue+=","+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()+","+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
					updateTotalDataSql+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
							+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
					
					rpcDeviceInfo.setRunTime(timeEffResponseData.getCurrent().getRunEfficiency().getTime());
					rpcDeviceInfo.setRunEff(timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency());
					rpcDeviceInfo.setRunRange(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());
					
					calItemResolutionDataList.add(new ProtocolItemResolutionData("运行时间","运行时间",timeEffResponseData.getCurrent().getRunEfficiency().getTime()+"",timeEffResponseData.getCurrent().getRunEfficiency().getTime()+"","","runTime","","","","",1));
					calItemResolutionDataList.add(new ProtocolItemResolutionData("运行时率","运行时率",timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()+"",timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()+"","","runtimeEfficiency","","","","",1));
					calItemResolutionDataList.add(new ProtocolItemResolutionData("运行区间","运行区间",timeEffResponseData.getCurrent().getRunEfficiency().getRangeString(),timeEffResponseData.getCurrent().getRunEfficiency().getRangeString(),"","runRange","","","","",1));
				}
				//如果进行了功耗计算
				if(isAcqEnergy){
					updateRealtimeData+=",t.totalKWattH= "+totalKWattH;
					insertHistColumns+=",totalKWattH";
					insertHistValue+=","+totalKWattH;
					updateTotalDataSql+=",t.totalKWattH= "+totalKWattH;
					
					rpcDeviceInfo.setTotalKWattH(totalKWattH);
					calItemResolutionDataList.add(new ProtocolItemResolutionData("累计用电量","累计用电量",totalKWattH+"",totalKWattH+"","","totalKWattH","","","","kW·h",1));
				}
				
				if(energyCalculateResponseData!=null&&energyCalculateResponseData.getResultStatus()==1){
					updateRealtimeData+=",t.todayKWattH="+energyCalculateResponseData.getCurrent().getToday().getKWattH();
					insertHistColumns+=",todayKWattH";
					insertHistValue+=","+energyCalculateResponseData.getCurrent().getToday().getKWattH();
					updateTotalDataSql+=",t.todayKWattH="+energyCalculateResponseData.getCurrent().getToday().getKWattH();
					
					rpcDeviceInfo.setTodayKWattH(energyCalculateResponseData.getCurrent().getToday().getKWattH());
					calItemResolutionDataList.add(new ProtocolItemResolutionData("日用电量","日用电量",energyCalculateResponseData.getCurrent().getToday().getKWattH()+"",energyCalculateResponseData.getCurrent().getToday().getKWattH()+"","","todayKWattH","","","","",1));
				}
				
				//同时进行了时率计算和功图计算，则进行功图汇总计算
				if(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&timeEffResponseData!=null && timeEffResponseData.getResultStatus()==1 && deviceTodayData!=null){
					//排序
					Collections.sort(deviceTodayData.getRPCCalculateList());
					String totalRequestData=CalculateUtils.getFESDiagramTotalRequestData(date, rpcDeviceInfo,deviceTodayData);
					totalAnalysisResponseData=CalculateUtils.totalCalculate(totalRequestData);
					calItemResolutionDataList.add(new ProtocolItemResolutionData("累计产液量","累计产液量",totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+"",totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+"","","liquidVolumetricProduction_l","","","","m^3/d",1));
					calItemResolutionDataList.add(new ProtocolItemResolutionData("累计产液量","累计产液量",totalAnalysisResponseData.getLiquidWeightProduction().getValue()+"",totalAnalysisResponseData.getLiquidWeightProduction().getValue()+"","","liquidWeightProduction_l","","","","t/d",1));
				}
				
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					updateRealtimeData+=",t.liquidvolumetricproduction_l="+totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+",t.liquidweightproduction_l="+totalAnalysisResponseData.getLiquidWeightProduction().getValue();
					insertHistColumns+=",liquidvolumetricproduction_l,liquidweightproduction_l";
					insertHistValue+=","+totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+","+totalAnalysisResponseData.getLiquidWeightProduction().getValue();
				}
				
				updateRealtimeData+=" where t.wellId= "+rpcDeviceInfo.getId();
				insertHistSql="insert into "+historyTable+"("+insertHistColumns+")values("+insertHistValue+")";
				updateTotalDataSql+=" where t.wellId= "+rpcDeviceInfo.getId()+"and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
				
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
						}else if(isAcqRunStatus&&alarmInstanceOwnItem.getItemList().get(i).getType()==6 && alarmInstanceOwnItem.getItemList().get(i).getItemName().equalsIgnoreCase(runStatus==1?"运行":"停抽")){
							runAlarmLevel=alarmInstanceOwnItem.getItemList().get(i).getAlarmLevel();
						}
					}
				}
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
					if(acquisitionItemInfo.getAlarmLevel()>0){
						alarm=true;
					}
					acquisitionItemInfoList.add(acquisitionItemInfo);
				}
				//添加计算项
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

					if(acquisitionItemInfo.getAlarmLevel()>0){
						alarm=true;
					}
					acquisitionItemInfoList.add(acquisitionItemInfo);
				}
				
				//将采集数据放入内存
				if(deviceTodayData!=null){
					deviceTodayData.setAcquisitionItemInfoList(acquisitionItemInfoList);
				}
				
				//如果满足保存周期或者有报警，保存数据
				if(save || alarm){
					String saveRawDataSql="insert into "+rawDataTable+"(wellid,acqtime,rawdata)values("+rpcDeviceInfo.getId()+",to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),'"+acqGroup.getRawData()+"' )";
					rpcDeviceInfo.setSaveTime(acqTime);
					commonDataService.getBaseDao().updateOrDeleteBySql(updateRealtimeData);
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
						updateRealRangeClobSql+=" where t.wellid="+rpcDeviceInfo.getId();
						updateHisRangeClobSql+=" where t.wellid="+rpcDeviceInfo.getId() +" and t.acqTime="+"to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
						updateTotalRangeClobSql+=" where t.wellId= "+rpcDeviceInfo.getId()+"and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
						commonDataService.getBaseDao().executeSqlUpdateClob(updateRealRangeClobSql,clobCont);
						commonDataService.getBaseDao().executeSqlUpdateClob(updateHisRangeClobSql,clobCont);
						commonDataService.getBaseDao().executeSqlUpdateClob(updateTotalRangeClobSql,clobCont);
					}
					commonDataService.getBaseDao().saveAcqFESDiagramAndCalculateData(rpcDeviceInfo,rpcCalculateRequestData,rpcCalculateResponseData);
					if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){//保存汇总数据
						commonDataService.getBaseDao().saveFESDiagramTotalCalculateData(rpcDeviceInfo,totalAnalysisResponseData,date);
					}else{
						
					}
					//报警项
					if(alarm){
						calculateDataService.saveAndSendAlarmInfo(rpcDeviceInfo.getWellName(),rpcDeviceInfo.getDeviceType()+"",acqTime,acquisitionItemInfoList);
					}
				}
				//放入内存数据库中
				if(jedis!=null && jedis.hexists("RPCDeviceInfo".getBytes(), (rpcDeviceInfo.getId()+"").getBytes())){
					jedis.hset("RPCDeviceInfo".getBytes(), (rpcDeviceInfo.getId()+"").getBytes(), SerializeObjectUnils.serialize(rpcDeviceInfo));
				}
				if(jedis!=null && deviceTodayData!=null){
					jedis.hset("RPCDeviceTodayData".getBytes(), (rpcDeviceInfo.getId()+"").getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
				}
				
				
				//处理websocket推送
				if(displayInstanceOwnItem!=null){
					//井筒分析图形数据
					StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
					String rodStressRatio1="0",rodStressRatio2="0",rodStressRatio3="0",rodStressRatio4="0";
					if(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232){
						int curvecount=rpcCalculateResponseData.getFESDiagram().getS().get(0).size();
						int pointcount=rpcCalculateResponseData.getFESDiagram().getS().size();
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
					}else{
						for(int i=0;i<rpcCalculateRequestData.getFESDiagram().getS().size();i++){
							pumpFSDiagramStrBuff.append(rpcCalculateRequestData.getFESDiagram().getS().get(i)+",");//位移
							pumpFSDiagramStrBuff.append(rpcCalculateRequestData.getFESDiagram().getF().get(i)+",");//载荷
						}
						if(pumpFSDiagramStrBuff.toString().endsWith(",")){
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
					
					wellBoreChartsData.append("{\"success\":true,");
					wellBoreChartsData.append("\"wellName\":\""+rpcDeviceInfo.getWellName()+"\",");
					wellBoreChartsData.append("\"acqTime\":\""+rpcCalculateRequestData.getFESDiagram().getAcqTime()+"\",");
					wellBoreChartsData.append("\"pointCount\":\""+rpcCalculateRequestData.getFESDiagram().getS().size()+"\",");
					wellBoreChartsData.append("\"upperLoadLine\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getUpperLoadLine():"")+"\",");
					wellBoreChartsData.append("\"lowerLoadLine\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getLowerLoadLine():"")+"\",");
					wellBoreChartsData.append("\"fmax\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getFMax().get(0):"")+"\",");
					wellBoreChartsData.append("\"fmin\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getFMin().get(0):"")+"\",");
					wellBoreChartsData.append("\"stroke\":\""+rpcCalculateRequestData.getFESDiagram().getStroke()+"\",");
					wellBoreChartsData.append("\"spm\":\""+rpcCalculateRequestData.getFESDiagram().getSPM()+"\",");
					
					wellBoreChartsData.append("\"liquidProduction\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?(productionUnit.equalsIgnoreCase("ton")?rpcCalculateResponseData.getProduction().getLiquidWeightProduction():rpcCalculateResponseData.getProduction().getLiquidVolumetricProduction()):"")+"\",");
					wellBoreChartsData.append("\"resultName\":\""+(workType!=null?workType.getResultName():"")+"\",");
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
					wellBoreChartsData.append("\"positionCurveData\":\""+StringUtils.join(rpcCalculateRequestData.getFESDiagram().getS(), ",")+"\",");
					wellBoreChartsData.append("\"loadCurveData\":\""+StringUtils.join(rpcCalculateRequestData.getFESDiagram().getF(), ",")+"\"");
					
					wellBoreChartsData.append("}");
					
					//地面分析图形数据
					surfaceChartsData.append("{\"success\":true,");
					surfaceChartsData.append("\"wellName\":\""+rpcDeviceInfo.getWellName()+"\",");
					surfaceChartsData.append("\"acqTime\":\""+rpcCalculateRequestData.getFESDiagram().getAcqTime()+"\",");
					
					surfaceChartsData.append("\"upStrokeWattMax\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getUpStrokeWattMax():"")+"\",");
					surfaceChartsData.append("\"downStrokeWattMax\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getDownStrokeWattMax():"")+"\",");
					surfaceChartsData.append("\"wattDegreeBalance\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getWattDegreeBalance():"")+"\",");
					surfaceChartsData.append("\"upStrokeIMax\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getUpStrokeIMax():"")+"\",");
					surfaceChartsData.append("\"downStrokeIMax\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getDownStrokeIMax():"")+"\",");
					surfaceChartsData.append("\"iDegreeBalance\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getIDegreeBalance():"")+"\",");
					surfaceChartsData.append("\"deltaRadius\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?rpcCalculateResponseData.getFESDiagram().getIDegreeBalance():"")+"\",");
					
					surfaceChartsData.append("\"positionCurveData\":\""+StringUtils.join(rpcCalculateRequestData.getFESDiagram().getS(), ",")+"\",");
					surfaceChartsData.append("\"loadCurveData\":\""+StringUtils.join(rpcCalculateRequestData.getFESDiagram().getF(), ",")+"\",");
					surfaceChartsData.append("\"powerCurveData\":\""+StringUtils.join(rpcCalculateRequestData.getFESDiagram().getWatt(), ",")+"\",");
					surfaceChartsData.append("\"currentCurveData\":\""+StringUtils.join(rpcCalculateRequestData.getFESDiagram().getI(), ",")+"\",");
					
					surfaceChartsData.append("\"crankAngle\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getCrankAngle(), ","):"")+"\",");
					surfaceChartsData.append("\"loadRorque\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getLoadTorque(), ","):"")+"\",");
					surfaceChartsData.append("\"crankTorque\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getCrankTorque(), ","):"")+"\",");
					surfaceChartsData.append("\"currentBalanceTorque\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getCurrentBalanceTorque(), ","):"")+"\",");
					surfaceChartsData.append("\"currentNetTorque\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getCurrentNetTorque(), ","):"")+"\",");
					surfaceChartsData.append("\"expectedBalanceTorque\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getExpectedBalanceTorque(), ","):"")+"\",");
					surfaceChartsData.append("\"expectedNetTorque\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getExpectedNetTorque(), ","):"")+"\",");
					surfaceChartsData.append("\"polishrodV\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getV(), ","):"")+"\",");
					surfaceChartsData.append("\"polishrodA\":\""+(rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1&&rpcCalculateResponseData.getCalculationStatus().getResultCode()!=1232?StringUtils.join(rpcCalculateResponseData.getFESDiagram().getA(), ","):"")+"\"");
					
					surfaceChartsData.append("}");
					
					for (String websocketClientUser : websocketClientUserList) {
						if(jedis.hexists("UserInfo".getBytes(), websocketClientUser.getBytes())){
							UserInfo userInfo=(UserInfo) SerializeObjectUnils.unserizlize(jedis.hget("UserInfo".getBytes(), websocketClientUser.getBytes()));
							
							int items=3;
							String columns = "[";
							for(int i=1;i<=items;i++){
								columns+= "{ \"header\":\"名称\",\"dataIndex\":\"name"+i+"\",children:[] },"
										+ "{ \"header\":\"变量\",\"dataIndex\":\"value"+i+"\",children:[] }";
								if(i<items){
									columns+=",";
								}
							}
							columns+= "]";
							
							webSocketSendData.append("{ \"success\":true,\"functionCode\":\""+functionCode+"\",\"wellName\":\""+rpcDeviceInfo.getWellName()+"\",\"acqTime\":\""+acqTime+"\",\"columns\":"+columns+",");
							webSocketSendData.append("\"commAlarmLevel\":"+commAlarmLevel+",");
							webSocketSendData.append("\"runAlarmLevel\":"+runAlarmLevel+",");
							webSocketSendData.append("\"resultAlarmLevel\":"+resultAlarmLevel+",");
							webSocketSendData.append("\"totalRoot\":[");
							displayItemInfo_json.append("[");
							allItemInfo_json.append("[");
							webSocketSendData.append("{\"name1\":\""+rpcDeviceInfo.getWellName()+":"+acqTime+" 在线\"},");
							
							//筛选
							List<AcquisitionItemInfo> userAcquisitionItemInfoList=new ArrayList<AcquisitionItemInfo>();
							for(int j=0;j<acquisitionItemInfoList.size();j++){
								allItemInfo_json.append("{\"columnName\":\""+acquisitionItemInfoList.get(j).getTitle()+"\",\"column\":\""+acquisitionItemInfoList.get(j).getColumn()+"\",\"value\":\""+acquisitionItemInfoList.get(j).getValue()+"\",\"rawValue\":\""+acquisitionItemInfoList.get(j).getRawValue()+"\",\"columnDataType\":\""+acquisitionItemInfoList.get(j).getDataType()+"\",\"resolutionMode\":\""+acquisitionItemInfoList.get(j).getResolutionMode()+"\",\"alarmLevel\":"+acquisitionItemInfoList.get(j).getAlarmLevel()+"},");
								if(StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), acquisitionItemInfoList.get(j).getColumn(), false,0)){
									for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
										if(acquisitionItemInfoList.get(j).getColumn().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode()) && displayInstanceOwnItem.getItemList().get(k).getType()!=2){
											if(displayInstanceOwnItem.getItemList().get(k).getShowLevel()==0||displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()){
												acquisitionItemInfoList.get(j).setSort(displayInstanceOwnItem.getItemList().get(k).getSort());
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
//											&&StringManagerUtils.existOrNot(userItems, finalAcquisitionItemInfoList.get(index).getRawTitle(),false)
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
							infoHandler().sendMessageToUser(websocketClientUser, webSocketSendData.toString());
						}
					}
				}
			}
		}
		if(jedis!=null&&jedis.isConnected()){
			jedis.close();
		}
		return null;
	}
	
	public String PCPDataProcessing(PCPDeviceInfo pcpDeviceInfo,AcqGroup acqGroup) throws Exception{
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		List<String> websocketClientUserList=new ArrayList<>();
		for (WebSocketByJavax item : WebSocketByJavax.clients.values()) { 
            String[] clientInfo=item.userId.split("_");
            if(clientInfo!=null && clientInfo.length==3 && !StringManagerUtils.existOrNot(websocketClientUserList, clientInfo[1], true)){
            	websocketClientUserList.add(clientInfo[1]);
            }
        }
		
		StringBuffer webSocketSendData = new StringBuffer();
		StringBuffer displayItemInfo_json = new StringBuffer();
		StringBuffer allItemInfo_json = new StringBuffer();

		boolean save=false;
		boolean alarm=false;
		boolean sendMessage=false;
		Jedis jedis=null;
		AlarmShowStyle alarmShowStyle=null;
		PCPDeviceTodayData deviceTodayData=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("AlarmShowStyle".getBytes())){
				MemoryDataManagerTask.initAlarmStyle();
			}
			alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
			
			if(!jedis.exists("PCPDeviceTodayData".getBytes())){
				MemoryDataManagerTask.loadTodayRPMData(null,0);
			}
			if(jedis.hexists("PCPDeviceTodayData".getBytes(), (pcpDeviceInfo.getId()+"").getBytes())){
				deviceTodayData=(PCPDeviceTodayData) SerializeObjectUnils.unserizlize(jedis.hget("PCPDeviceTodayData".getBytes(), (pcpDeviceInfo.getId()+"").getBytes()));
			}
			
			if(!jedis.exists("pcpCalItemList".getBytes())){
				MemoryDataManagerTask.loadPCPCalculateItem();
			}
			
			if(!jedis.exists("UserInfo".getBytes())){
				MemoryDataManagerTask.loadUserInfo(null,0,"update");
			}
			
			if(!jedis.exists("AcqInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadAcqInstanceOwnItemById("","update");
			}
			
			if(!jedis.exists("DisplayInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadDisplayInstanceOwnItemById("","update");
			}
			
			if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
			}
			if(!jedis.exists("ProtocolMappingColumn".getBytes())){
				MemoryDataManagerTask.loadProtocolMappingColumn();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String realtimeTable="tbl_pcpacqdata_latest";
		String historyTable="tbl_pcpacqdata_hist";
		String rawDataTable="tbl_pcpacqrawdata";
		String totalDataTable="tbl_pcpdailycalculationdata";
		String functionCode="pcpDeviceRealTimeMonitoringData";
		String columnsKey="pcpDeviceAcquisitionItemColumns";
		int DeviceType=0;
		if(pcpDeviceInfo.getDeviceType()>=100 && pcpDeviceInfo.getDeviceType()<200){
			DeviceType=0;
		}else if(pcpDeviceInfo.getDeviceType()>=200 && pcpDeviceInfo.getDeviceType()<300){
			DeviceType=1;
		}
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
			EquipmentDriverServerTask.loadAcquisitionItemColumns(DeviceType);
		}
		Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
		if(acqGroup!=null){
			Set<byte[]>pcpCalItemSet=null;
			if(jedis!=null){
				pcpCalItemSet= jedis.zrange("pcpCalItemList".getBytes(), 0, -1);
			}
			String protocolName="";
			AcqInstanceOwnItem acqInstanceOwnItem=null;
			if(jedis!=null&&jedis.hexists("AcqInstanceOwnItem".getBytes(), pcpDeviceInfo.getInstanceCode().getBytes())){
				acqInstanceOwnItem=(AcqInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AcqInstanceOwnItem".getBytes(), pcpDeviceInfo.getInstanceCode().getBytes()));
				protocolName=acqInstanceOwnItem.getProtocol();
			}
			DisplayInstanceOwnItem displayInstanceOwnItem=null;
			if(jedis!=null&&jedis.hexists("DisplayInstanceOwnItem".getBytes(), pcpDeviceInfo.getDisplayInstanceCode().getBytes())){
				displayInstanceOwnItem=(DisplayInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("DisplayInstanceOwnItem".getBytes(), pcpDeviceInfo.getDisplayInstanceCode().getBytes()));
			}
			
			AlarmInstanceOwnItem alarmInstanceOwnItem=null;
			if(jedis!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), pcpDeviceInfo.getAlarmInstanceCode().getBytes())){
				alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), pcpDeviceInfo.getAlarmInstanceCode().getBytes()));
			}
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			
			ModbusProtocolConfig.Protocol protocol=null;
			if(StringManagerUtils.isNotNull(protocolName)){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
						protocol=modbusProtocolConfig.getProtocol().get(i);
						break;
					}
				}
			}
			
			if(protocol!=null){
				String lastSaveTime=pcpDeviceInfo.getSaveTime();
				int save_cycle=acqInstanceOwnItem.getSaveCycle();
				String acqTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
				String date=StringManagerUtils.getCurrentTime("yyyy-MM-dd");
				long timeDiff=StringManagerUtils.getTimeDifference(lastSaveTime, acqTime, "yyyy-MM-dd HH:mm:ss");
				if(timeDiff>save_cycle*1000){
					save=true;
				}
				
				PCPCalculateRequestData pcpCalculateRequestData=new PCPCalculateRequestData();
				pcpCalculateRequestData.init();
				
				
				pcpCalculateRequestData.setWellName(pcpDeviceInfo.getWellName());
				pcpCalculateRequestData.setFluidPVT(pcpDeviceInfo.getFluidPVT());
				pcpCalculateRequestData.setReservoir(pcpDeviceInfo.getReservoir());
				pcpCalculateRequestData.setRodString(pcpDeviceInfo.getRodString());
				pcpCalculateRequestData.setTubingString(pcpDeviceInfo.getTubingString());
				pcpCalculateRequestData.setCasingString(pcpDeviceInfo.getCasingString());
				pcpCalculateRequestData.setPump(pcpDeviceInfo.getPump());
				pcpCalculateRequestData.setProduction(pcpDeviceInfo.getProduction());
				pcpCalculateRequestData.setManualIntervention(pcpDeviceInfo.getManualIntervention());
				
				PCPCalculateResponseData pcpCalculateResponseData=null;
				CommResponseData commResponseData=null;
				TimeEffResponseData timeEffResponseData=null;
				EnergyCalculateResponseData energyCalculateResponseData=null;
				TotalAnalysisResponseData totalAnalysisResponseData=null;
				
				boolean isAcqRunStatus=false,isAcqEnergy=false,isAcqRPM=false;
				int runStatus=0;
				float totalKWattH=0;
				
				//进行通信计算
				String commRequest="{"
						+ "\"AKString\":\"\","
						+ "\"WellName\":\""+pcpDeviceInfo.getWellName()+"\",";
				if(StringManagerUtils.isNotNull(pcpDeviceInfo.getAcqTime())&&StringManagerUtils.isNotNull(pcpDeviceInfo.getCommRange())){
					commRequest+= "\"Last\":{"
							+ "\"AcqTime\": \""+pcpDeviceInfo.getAcqTime()+"\","
							+ "\"CommStatus\": "+(pcpDeviceInfo.getCommStatus()==1?true:false)+","
							+ "\"CommEfficiency\": {"
							+ "\"Efficiency\": "+pcpDeviceInfo.getCommEff()+","
							+ "\"Time\": "+pcpDeviceInfo.getCommTime()+","
							+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(pcpDeviceInfo.getCommRange())+""
							+ "}"
							+ "},";
				}	
				commRequest+= "\"Current\": {"
						+ "\"AcqTime\":\""+acqTime+"\","
						+ "\"CommStatus\":true"
						+ "}"
						+ "}";
				commResponseData=CalculateUtils.commCalculate(commRequest);
				
				String updateRealtimeData="update "+realtimeTable+" t set t.acqTime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),t.CommStatus=1";
				
				
				String insertHistColumns="wellid,acqTime,CommStatus";
				String insertHistValue=pcpDeviceInfo.getId()+",to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),1";
				String insertHistSql="";
				
				String updateTotalDataSql="update "+totalDataTable+" t set t.CommStatus=1";
				
				List<AcquisitionItemInfo> acquisitionItemInfoList=new ArrayList<AcquisitionItemInfo>();
				List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
				for(int i=0;acqGroup.getAddr()!=null &&i<acqGroup.getAddr().size();i++){
					for(int j=0;j<protocol.getItems().size();j++){
						if(acqGroup.getAddr().get(i)==protocol.getItems().get(j).getAddr()){
							String value="";
							String columnName=loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(j).getTitle());
							
							DataMapping dataMappingColumn=(DataMapping)SerializeObjectUnils.unserizlize(jedis.hget("ProtocolMappingColumn".getBytes(), (protocol.getDeviceType()+"_"+columnName).getBytes()));
							
							if(acqGroup.getValue()!=null&&acqGroup.getValue().size()>i&&acqGroup.getValue().get(i)!=null){
								value=StringManagerUtils.objectListToString(acqGroup.getValue().get(i), protocol.getItems().get(j).getIFDataType());
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
								updateRealtimeData+=",t."+columnName+"='"+rawValue+"'";
								insertHistColumns+=","+columnName;
								insertHistValue+=",'"+rawValue+"'";
								
								
								if(protocol.getItems().get(j).getResolutionMode()==1||protocol.getItems().get(j).getResolutionMode()==2){//如果是枚举量或数据量
									if(protocol.getItems().get(j).getMeaning()!=null&&protocol.getItems().get(j).getMeaning().size()>0){
										for(int l=0;l<protocol.getItems().get(j).getMeaning().size();l++){
											if(StringManagerUtils.isNotNull(value)&&StringManagerUtils.stringToFloat(value)==(protocol.getItems().get(j).getMeaning().get(l).getValue())){
												value=protocol.getItems().get(j).getMeaning().get(l).getMeaning();
												break;
											}
										}
									}
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort);
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
														ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort);
														protocolItemResolutionDataList.add(protocolItemResolutionData);
														match=true;
														break;
													}
												}
												if(!match){
													value="";
													rawValue="";
													bitIndex=protocol.getItems().get(j).getMeaning().get(l).getValue()+"";
													ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort);
													protocolItemResolutionDataList.add(protocolItemResolutionData);
												}
											}
										}
									}else{
										ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort);
										protocolItemResolutionDataList.add(protocolItemResolutionData);
									}
								}else{
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawTitle,title,value,rawValue,addr,columnName,columnDataType,resolutionMode,bitIndex,unit,sort);
									protocolItemResolutionDataList.add(protocolItemResolutionData);
								}
								
								if("runStatus".equalsIgnoreCase(dataMappingColumn.getCalColumn())){//运行状态
									isAcqRunStatus=true;
									runStatus=StringManagerUtils.stringToInteger(rawValue);
								}else if("totalKWattH".equalsIgnoreCase(dataMappingColumn.getCalColumn())){//累计有功功耗
									isAcqEnergy=true;
									totalKWattH=StringManagerUtils.stringToFloat(rawValue);
								}else if("TubingPressure".equalsIgnoreCase(dataMappingColumn.getCalColumn())){//油压
									pcpCalculateRequestData.getProduction().setTubingPressure(StringManagerUtils.stringToFloat(rawValue));
									pcpDeviceInfo.getProduction().setTubingPressure(StringManagerUtils.stringToFloat(rawValue));
								}else if("CasingPressure".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									pcpCalculateRequestData.getProduction().setCasingPressure(StringManagerUtils.stringToFloat(rawValue));
									pcpDeviceInfo.getProduction().setCasingPressure(StringManagerUtils.stringToFloat(rawValue));
								}else if("ProducingfluidLevel".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									pcpCalculateRequestData.getProduction().setProducingfluidLevel(StringManagerUtils.stringToFloat(rawValue));
									insertHistValue+=","+rawValue+"";
								}else if("volumeWaterCut".equalsIgnoreCase(dataMappingColumn.getCalColumn()) || "waterCut".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									pcpCalculateRequestData.getProduction().setWaterCut(StringManagerUtils.stringToFloat(rawValue));
									pcpDeviceInfo.getProduction().setWaterCut(StringManagerUtils.stringToFloat(rawValue));
								}else if("RPM".equalsIgnoreCase(dataMappingColumn.getCalColumn())){
									isAcqRPM=true;
									pcpCalculateRequestData.setRPM(StringManagerUtils.stringToFloat(rawValue));
								}
							}
							break;
						}
					}
				}
				//判断是否采集了运行状态，如采集则进行时率计算
				if(isAcqRunStatus){
					String tiemEffRequest="{"
							+ "\"AKString\":\"\","
							+ "\"WellName\":\""+pcpDeviceInfo.getWellName()+"\",";
					if(StringManagerUtils.isNotNull(pcpDeviceInfo.getAcqTime())&&StringManagerUtils.isNotNull(pcpDeviceInfo.getRunRange())){
						tiemEffRequest+= "\"Last\":{"
								+ "\"AcqTime\": \""+pcpDeviceInfo.getAcqTime()+"\","
								+ "\"RunStatus\": "+(pcpDeviceInfo.getRunStatus()==1?true:false)+","
								+ "\"RunEfficiency\": {"
								+ "\"Efficiency\": "+pcpDeviceInfo.getRunEff()+","
								+ "\"Time\": "+pcpDeviceInfo.getRunTime()+","
								+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(pcpDeviceInfo.getRunRange())+""
								+ "}"
								+ "},";
					}	
					tiemEffRequest+= "\"Current\": {"
							+ "\"AcqTime\":\""+acqTime+"\","
							+ "\"RunStatus\":"+(runStatus==1?true:false)+""
							+ "}"
							+ "}";
					timeEffResponseData=CalculateUtils.runCalculate(tiemEffRequest);
				}
				
				//判断是否采集了电量，如采集则进行电量计算
				if(isAcqEnergy){
					
					String energyRequest="{"
							+ "\"AKString\":\"\","
							+ "\"WellName\":\""+pcpDeviceInfo.getWellName()+"\",";
					if(StringManagerUtils.isNotNull(pcpDeviceInfo.getAcqTime())){
						energyRequest+= "\"Last\":{"
								+ "\"AcqTime\": \""+pcpDeviceInfo.getAcqTime()+"\","
								+ "\"Total\":{"
								+ "\"KWattH\":"+pcpDeviceInfo.getTotalKWattH()
								+ "},\"Today\":{"
								+ "\"KWattH\":"+pcpDeviceInfo.getTodayKWattH()
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
				}
				
				//进行转速计算
				if(isAcqRPM){
					pcpCalculateResponseData=CalculateUtils.rpmCalculate(gson.toJson(pcpCalculateRequestData));
					if(pcpCalculateResponseData!=null&&pcpCalculateResponseData.getCalculationStatus().getResultStatus()==1){
						//删除非当天采集的转速数据
						if(deviceTodayData!=null){
							Iterator<PCPCalculateResponseData> it = deviceTodayData.getPCPCalculateList().iterator();
							while(it.hasNext()){
								PCPCalculateResponseData responseData=(PCPCalculateResponseData)it.next();
								if(!StringManagerUtils.isNotNull(responseData.getAcqTime())
										|| responseData.getAcqTime().indexOf(date)<0  ){
									it.remove();
								}
							}
							deviceTodayData.getPCPCalculateList().add(pcpCalculateResponseData);
						}else{
							deviceTodayData=new PCPDeviceTodayData();
							deviceTodayData.setId(pcpDeviceInfo.getId());
							deviceTodayData.setPCPCalculateList(new ArrayList<PCPCalculateResponseData>());
							deviceTodayData.setAcquisitionItemInfoList(new ArrayList<AcquisitionItemInfo>());
							deviceTodayData.getPCPCalculateList().add(pcpCalculateResponseData);
						}
					}
				}
				
				List<ProtocolItemResolutionData> calItemResolutionDataList=getRPMCalItemData(pcpCalculateRequestData,pcpCalculateResponseData);
				
				//通信
				pcpDeviceInfo.setAcqTime(acqTime);
				pcpDeviceInfo.setCommStatus(1);
				calItemResolutionDataList.add(new ProtocolItemResolutionData("通信状态","通信状态","在线","1","","commStatusName","","","","",1));
				if(commResponseData!=null&&commResponseData.getResultStatus()==1){
					updateRealtimeData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
							+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
					insertHistColumns+=",commTimeEfficiency,commTime";
					insertHistValue+=","+commResponseData.getCurrent().getCommEfficiency().getEfficiency()+","+commResponseData.getCurrent().getCommEfficiency().getTime();
					
					updateTotalDataSql+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
							+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
					
					pcpDeviceInfo.setCommTime(commResponseData.getCurrent().getCommEfficiency().getTime());
					pcpDeviceInfo.setCommEff(commResponseData.getCurrent().getCommEfficiency().getEfficiency());
					pcpDeviceInfo.setCommRange(commResponseData.getCurrent().getCommEfficiency().getRangeString());
					
					calItemResolutionDataList.add(new ProtocolItemResolutionData("通信时间","通信时间",commResponseData.getCurrent().getCommEfficiency().getTime()+"",commResponseData.getCurrent().getCommEfficiency().getTime()+"","","commTime","","","","",1));
					calItemResolutionDataList.add(new ProtocolItemResolutionData("通信时率","通信时率",commResponseData.getCurrent().getCommEfficiency().getEfficiency()+"",commResponseData.getCurrent().getCommEfficiency().getEfficiency()+"","","commtimeEfficiency","","","","",1));
					calItemResolutionDataList.add(new ProtocolItemResolutionData("通信区间","通信区间",commResponseData.getCurrent().getCommEfficiency().getRangeString(),commResponseData.getCurrent().getCommEfficiency().getRangeString(),"","commRange","","","","",1));
				}
				//如果进行了时率计算
				if(isAcqRunStatus){
					updateRealtimeData+=",t.runStatus= "+runStatus;
					insertHistColumns+=",runStatus";
					insertHistValue+=","+runStatus;
					updateTotalDataSql+=",t.runStatus= "+runStatus;
					
					pcpDeviceInfo.setRunStatus(runStatus);
					
					calItemResolutionDataList.add(new ProtocolItemResolutionData("运行状态","运行状态",runStatus==1?"运行":"停抽",runStatus+"","","runStatusName","","","","",1));
				}
				if(timeEffResponseData!=null && timeEffResponseData.getResultStatus()==1){
					updateRealtimeData+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
							+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
					insertHistColumns+=",runTimeEfficiency,runTime";
					insertHistValue+=","+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()+","+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
					updateTotalDataSql+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
							+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
					
					pcpDeviceInfo.setRunTime(timeEffResponseData.getCurrent().getRunEfficiency().getTime());
					pcpDeviceInfo.setRunEff(timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency());
					pcpDeviceInfo.setRunRange(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());
					
					calItemResolutionDataList.add(new ProtocolItemResolutionData("运行时间","运行时间",timeEffResponseData.getCurrent().getRunEfficiency().getTime()+"",timeEffResponseData.getCurrent().getRunEfficiency().getTime()+"","","runTime","","","","",1));
					calItemResolutionDataList.add(new ProtocolItemResolutionData("运行时率","运行时率",timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()+"",timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()+"","","runtimeEfficiency","","","","",1));
					calItemResolutionDataList.add(new ProtocolItemResolutionData("运行区间","运行区间",timeEffResponseData.getCurrent().getRunEfficiency().getRangeString(),timeEffResponseData.getCurrent().getRunEfficiency().getRangeString(),"","runRange","","","","",1));
				}
				//如果进行了功耗计算
				if(isAcqEnergy){
					updateRealtimeData+=",t.totalKWattH= "+totalKWattH;
					insertHistColumns+=",totalKWattH";
					insertHistValue+=","+totalKWattH;
					updateTotalDataSql+=",t.totalKWattH= "+totalKWattH;
					
					pcpDeviceInfo.setTotalKWattH(totalKWattH);
					calItemResolutionDataList.add(new ProtocolItemResolutionData("累计用电量","累计用电量",totalKWattH+"",totalKWattH+"","","totalKWattH","","","","",1));
				}
				
				if(energyCalculateResponseData!=null&&energyCalculateResponseData.getResultStatus()==1){
					updateRealtimeData+=",t.todayKWattH="+energyCalculateResponseData.getCurrent().getToday().getKWattH();
					insertHistColumns+=",todayKWattH";
					insertHistValue+=","+energyCalculateResponseData.getCurrent().getToday().getKWattH();
					updateTotalDataSql+=",t.todayKWattH="+energyCalculateResponseData.getCurrent().getToday().getKWattH();
					
					pcpDeviceInfo.setTodayKWattH(energyCalculateResponseData.getCurrent().getToday().getKWattH());
					calItemResolutionDataList.add(new ProtocolItemResolutionData("日用电量","日用电量",energyCalculateResponseData.getCurrent().getToday().getKWattH()+"",energyCalculateResponseData.getCurrent().getToday().getKWattH()+"","","todayKWattH","","","","",1));
				}
				
				//同时进行了时率计算和功图计算，则进行功图汇总计算
				if(pcpCalculateResponseData!=null&&pcpCalculateResponseData.getCalculationStatus().getResultStatus()==1&&timeEffResponseData!=null && timeEffResponseData.getResultStatus()==1 && deviceTodayData!=null){
					//排序
					Collections.sort(deviceTodayData.getPCPCalculateList());
					String totalRequestData=CalculateUtils.getRPMTotalRequestData(date, pcpDeviceInfo,deviceTodayData);
					totalAnalysisResponseData=CalculateUtils.totalCalculate(totalRequestData);
				}
				
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					updateRealtimeData+=",t.liquidvolumetricproduction_l="+totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+",t.liquidweightproduction_l="+totalAnalysisResponseData.getLiquidWeightProduction().getValue();
					insertHistColumns+=",liquidvolumetricproduction_l,liquidweightproduction_l";
					insertHistValue+=","+totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+","+totalAnalysisResponseData.getLiquidWeightProduction().getValue();
				}
				
				updateRealtimeData+=" where t.wellId= "+pcpDeviceInfo.getId();
				insertHistSql="insert into "+historyTable+"("+insertHistColumns+")values("+insertHistValue+")";
				updateTotalDataSql+=" where t.wellId= "+pcpDeviceInfo.getId()+"and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
				
				//排序
				Collections.sort(protocolItemResolutionDataList);
				//报警判断
				int commAlarmLevel=0,runAlarmLevel=0;
				if(alarmInstanceOwnItem!=null){
					for(int i=0;i<alarmInstanceOwnItem.itemList.size();i++){
						if(alarmInstanceOwnItem.getItemList().get(i).getType()==3 && alarmInstanceOwnItem.getItemList().get(i).getItemName().equalsIgnoreCase("在线")){
							commAlarmLevel=alarmInstanceOwnItem.getItemList().get(i).getAlarmLevel();
						}else if(isAcqRunStatus&&alarmInstanceOwnItem.getItemList().get(i).getType()==6 && alarmInstanceOwnItem.getItemList().get(i).getItemName().equalsIgnoreCase(runStatus==1?"运行":"停抽")){
							runAlarmLevel=alarmInstanceOwnItem.getItemList().get(i).getAlarmLevel();
						}
					}
				}
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
									alarmLevel=alarmInstanceOwnItem.getItemList().get(l).getAlarmSign()>0?alarmInstanceOwnItem.getItemList().get(l).getAlarmLevel():0;
									acquisitionItemInfo.setAlarmLevel(alarmLevel);
									acquisitionItemInfo.setHystersis(hystersis);
									acquisitionItemInfo.setAlarmLimit(alarmInstanceOwnItem.getItemList().get(l).getUpperLimit());
									acquisitionItemInfo.setAlarmInfo("高报");
									acquisitionItemInfo.setAlarmType(2);
									acquisitionItemInfo.setAlarmDelay(alarmInstanceOwnItem.getItemList().get(l).getDelay());
									acquisitionItemInfo.setIsSendMessage(alarmInstanceOwnItem.getItemList().get(l).getIsSendMessage());
									acquisitionItemInfo.setIsSendMail(alarmInstanceOwnItem.getItemList().get(l).getIsSendMail());
								}else if((StringManagerUtils.isNotNull(alarmInstanceOwnItem.getItemList().get(l).getLowerLimit()+"") && StringManagerUtils.stringToFloat(acquisitionItemInfo.getRawValue())<alarmInstanceOwnItem.getItemList().get(l).getLowerLimit()-hystersis)){
									alarmLevel=alarmInstanceOwnItem.getItemList().get(l).getAlarmSign()>0?alarmInstanceOwnItem.getItemList().get(l).getAlarmLevel():0;
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
										alarmLevel=alarmInstanceOwnItem.getItemList().get(l).getAlarmSign()>0?alarmInstanceOwnItem.getItemList().get(l).getAlarmLevel():0;
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
									alarmLevel=alarmInstanceOwnItem.getItemList().get(l).getAlarmSign()>0?alarmInstanceOwnItem.getItemList().get(l).getAlarmLevel():0;
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
					if(acquisitionItemInfo.getAlarmLevel()>0){
						alarm=true;
					}
					acquisitionItemInfoList.add(acquisitionItemInfo);
				}
				//添加计算项
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
					acquisitionItemInfo.setAlarmLevel(alarmLevel);
					acquisitionItemInfo.setUnit(calItemResolutionDataList.get(i).getUnit());
					acquisitionItemInfo.setSort(calItemResolutionDataList.get(i).getSort());

					for(int k=0;alarmInstanceOwnItem!=null&&k<alarmInstanceOwnItem.getItemList().size();k++){
						if(("runStatus".equalsIgnoreCase(calItemResolutionDataList.get(i).getColumn())||"runStatusName".equalsIgnoreCase(calItemResolutionDataList.get(i).getColumn()))
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
					
					
					if(acquisitionItemInfo.getAlarmLevel()>0){
						alarm=true;
					}
					acquisitionItemInfoList.add(acquisitionItemInfo);
				}
				
				//将采集数据放入内存
				if(deviceTodayData!=null){
					deviceTodayData.setAcquisitionItemInfoList(acquisitionItemInfoList);
				}
				
				//如果满足保存周期或者有报警，保存数据
				if(save || alarm){
					String saveRawDataSql="insert into "+rawDataTable+"(wellid,acqtime,rawdata)values("+pcpDeviceInfo.getId()+",to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss'),'"+acqGroup.getRawData()+"' )";
					pcpDeviceInfo.setSaveTime(acqTime);
					commonDataService.getBaseDao().updateOrDeleteBySql(updateRealtimeData);
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
						updateRealRangeClobSql+=" where t.wellid="+pcpDeviceInfo.getId();
						updateHisRangeClobSql+=" where t.wellid="+pcpDeviceInfo.getId() +" and t.acqTime="+"to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
						updateTotalRangeClobSql+=" where t.wellId= "+pcpDeviceInfo.getId()+"and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
						commonDataService.getBaseDao().executeSqlUpdateClob(updateRealRangeClobSql,clobCont);
						commonDataService.getBaseDao().executeSqlUpdateClob(updateHisRangeClobSql,clobCont);
						commonDataService.getBaseDao().executeSqlUpdateClob(updateTotalRangeClobSql,clobCont);
					}
					commonDataService.getBaseDao().saveAcqRPMAndCalculateData(pcpDeviceInfo,pcpCalculateRequestData,pcpCalculateResponseData);
					if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){//保存汇总数据
						commonDataService.getBaseDao().saveRPMTotalCalculateData(pcpDeviceInfo,totalAnalysisResponseData,date);
					}else{
						
					}
					//报警项
					if(alarm){
						calculateDataService.saveAndSendAlarmInfo(pcpDeviceInfo.getWellName(),pcpDeviceInfo.getDeviceType()+"",acqTime,acquisitionItemInfoList);
					}
				}
				//放入内存数据库中
				if(jedis!=null && jedis.hexists("PCPDeviceInfo".getBytes(), (pcpDeviceInfo.getId()+"").getBytes())){
					jedis.hset("PCPDeviceInfo".getBytes(), (pcpDeviceInfo.getId()+"").getBytes(), SerializeObjectUnils.serialize(pcpDeviceInfo));
				}
				if(jedis!=null && deviceTodayData!=null){
					jedis.hset("PCPDeviceTodayData".getBytes(), (pcpDeviceInfo.getId()+"").getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
				}
				
				//处理websocket推送
				if(displayInstanceOwnItem!=null){
					for (String websocketClientUser : websocketClientUserList) {
						if(jedis.hexists("UserInfo".getBytes(), websocketClientUser.getBytes())){
							UserInfo userInfo=(UserInfo) SerializeObjectUnils.unserizlize(jedis.hget("UserInfo".getBytes(), websocketClientUser.getBytes()));
							
							int items=3;
							String columns = "[";
							for(int i=1;i<=items;i++){
								columns+= "{ \"header\":\"名称\",\"dataIndex\":\"name"+i+"\",children:[] },"
										+ "{ \"header\":\"变量\",\"dataIndex\":\"value"+i+"\",children:[] }";
								if(i<items){
									columns+=",";
								}
							}
							columns+= "]";
							
							webSocketSendData.append("{ \"success\":true,\"functionCode\":\""+functionCode+"\",\"wellName\":\""+pcpDeviceInfo.getWellName()+"\",\"acqTime\":\""+acqTime+"\",\"columns\":"+columns+",");
							webSocketSendData.append("\"commAlarmLevel\":"+commAlarmLevel+",");
							webSocketSendData.append("\"runAlarmLevel\":"+runAlarmLevel+",");
							webSocketSendData.append("\"totalRoot\":[");
							displayItemInfo_json.append("[");
							allItemInfo_json.append("[");
							webSocketSendData.append("{\"name1\":\""+pcpDeviceInfo.getWellName()+":"+acqTime+" 在线\"},");
							
							//筛选
							List<AcquisitionItemInfo> userAcquisitionItemInfoList=new ArrayList<AcquisitionItemInfo>();
							for(int j=0;j<acquisitionItemInfoList.size();j++){
								allItemInfo_json.append("{\"columnName\":\""+acquisitionItemInfoList.get(j).getTitle()+"\",\"column\":\""+acquisitionItemInfoList.get(j).getColumn()+"\",\"value\":\""+acquisitionItemInfoList.get(j).getValue()+"\",\"rawValue\":\""+acquisitionItemInfoList.get(j).getRawValue()+"\",\"columnDataType\":\""+acquisitionItemInfoList.get(j).getDataType()+"\",\"resolutionMode\":\""+acquisitionItemInfoList.get(j).getResolutionMode()+"\",\"alarmLevel\":"+acquisitionItemInfoList.get(j).getAlarmLevel()+"},");
								if(StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), acquisitionItemInfoList.get(j).getColumn(), false,0)){
									for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
										if(acquisitionItemInfoList.get(j).getColumn().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode()) && displayInstanceOwnItem.getItemList().get(k).getType()!=2){
											if(displayInstanceOwnItem.getItemList().get(k).getShowLevel()==0||displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()){
												acquisitionItemInfoList.get(j).setSort(displayInstanceOwnItem.getItemList().get(k).getSort());
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
//											&&StringManagerUtils.existOrNot(userItems, finalAcquisitionItemInfoList.get(index).getRawTitle(),false)
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
							webSocketSendData.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle)+"}");
							infoHandler().sendMessageToUser(websocketClientUser, webSocketSendData.toString());
						}
					}
				}
			}
		}
		if(jedis!=null&&jedis.isConnected()){
			jedis.close();
		}
		return null;
	}
	
	
	
	public static List<ProtocolItemResolutionData> getFESDiagramCalItemData(RPCCalculateRequestData calculateRequestData,RPCCalculateResponseData calculateResponseData){
		List<ProtocolItemResolutionData> FESDiagramCalItemList=new ArrayList<ProtocolItemResolutionData>();
		//功图采集时间
		FESDiagramCalItemList.add(new ProtocolItemResolutionData("功图采集时间","功图采集时间",calculateRequestData.getFESDiagram().getAcqTime(),calculateRequestData.getFESDiagram().getAcqTime(),"","FESDiagramAcqtime","","","","",1));
		//冲程、冲次
		FESDiagramCalItemList.add(new ProtocolItemResolutionData("冲程","冲程",calculateRequestData.getFESDiagram().getStroke()+"",calculateRequestData.getFESDiagram().getStroke()+"","","Stroke","","","","m",1));
		FESDiagramCalItemList.add(new ProtocolItemResolutionData("冲次","冲次",calculateRequestData.getFESDiagram().getSPM()+"",calculateRequestData.getFESDiagram().getSPM()+"","","spm","","","","1/min",1));
		if(calculateResponseData!=null&&calculateResponseData.getCalculationStatus().getResultStatus()==1){
			//工况
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("工况","工况",calculateResponseData.getCalculationStatus().getResultCode()+"",calculateResponseData.getCalculationStatus().getResultCode()+"","","resultName","","","","",1));
			//最大最小载荷
			String FMax="",FMin="";
			if(calculateResponseData.getFESDiagram().getFMax()!=null&&calculateResponseData.getFESDiagram().getFMax().size()>0){
				FMax=calculateResponseData.getFESDiagram().getFMax().get(0)+"";
			}
			if(calculateResponseData.getFESDiagram().getFMin()!=null&&calculateResponseData.getFESDiagram().getFMin().size()>0){
				FMin=calculateResponseData.getFESDiagram().getFMin().get(0)+"";
			}
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("最大载荷","最大载荷",FMax,FMax,"","FMax","","","","kN",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("最小载荷","最小载荷",FMin,FMin,"","FMin","","","","kN",1));
			//平衡
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("上冲程最大电流","上冲程最大电流",calculateResponseData.getFESDiagram().getUpStrokeIMax()+"",calculateResponseData.getFESDiagram().getUpStrokeIMax()+"","","UPSTROKEIMAX","","","","A",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("下冲程最大电流","下冲程最大电流",calculateResponseData.getFESDiagram().getDownStrokeIMax()+"",calculateResponseData.getFESDiagram().getDownStrokeIMax()+"","","DOWNSTROKEIMAX","","","","A",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("上冲程最大功率","上冲程最大功率",calculateResponseData.getFESDiagram().getUpStrokeWattMax()+"",calculateResponseData.getFESDiagram().getUpStrokeWattMax()+"","","UPSTROKEWATTMAX","","","","A",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("下冲程最大功率","下冲程最大功率",calculateResponseData.getFESDiagram().getDownStrokeWattMax()+"",calculateResponseData.getFESDiagram().getDownStrokeWattMax()+"","","DOWNSTROKEWATTMAX","","","","A",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("电流平衡度","电流平衡度",calculateResponseData.getFESDiagram().getIDegreeBalance()+"",calculateResponseData.getFESDiagram().getIDegreeBalance()+"","","IDEGREEBALANCE","","","","%",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("功率平衡度","功率平衡度",calculateResponseData.getFESDiagram().getWattDegreeBalance()+"",calculateResponseData.getFESDiagram().getWattDegreeBalance()+"","","WATTDEGREEBALANCE","","","","%",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("移动距离","移动距离",calculateResponseData.getFESDiagram().getDeltaRadius()+"",calculateResponseData.getFESDiagram().getDeltaRadius()+"","","DELTARADIUS","","","","m",1));
			
			//充满系数、抽空充满系数
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("充满系数","充满系数",calculateResponseData.getFESDiagram().getFullnessCoefficient()+"",calculateResponseData.getFESDiagram().getFullnessCoefficient()+"","","FULLNESSCOEFFICIENT","","","","小数",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("抽空充满系数","抽空充满系数",calculateResponseData.getFESDiagram().getNoLiquidFullnessCoefficient()+"",calculateResponseData.getFESDiagram().getNoLiquidFullnessCoefficient()+"","","NOLIQUIDFULLNESSCOEFFICIENT","","","","小数",1));
			//柱塞冲程、柱塞有效冲程、抽空柱塞有效冲程
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞冲程","柱塞冲程",calculateResponseData.getFESDiagram().getPlungerStroke()+"",calculateResponseData.getFESDiagram().getPlungerStroke()+"","","PLUNGERSTROKE","","","","m",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞有效冲程","柱塞有效冲程",calculateResponseData.getFESDiagram().getAvailablePlungerStroke()+"",calculateResponseData.getFESDiagram().getAvailablePlungerStroke()+"","","AVAILABLEPLUNGERSTROKE","","","","m",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("抽空柱塞有效冲程","抽空柱塞有效冲程",calculateResponseData.getFESDiagram().getNoLiquidAvailablePlungerStroke()+"",calculateResponseData.getFESDiagram().getNoLiquidAvailablePlungerStroke()+"","","NOLIQUIDAVAILABLEPLUNGERSTROKE","","","","m",1));
			
			//上下理论载荷线
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("理论上载荷","理论上载荷",calculateResponseData.getFESDiagram().getUpperLoadLine()+"",calculateResponseData.getFESDiagram().getUpperLoadLine()+"","","UPPERLOADLINE","","","","kN",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("考虑沉没压力的理论上载荷","考虑沉没压力的理论上载荷",calculateResponseData.getFESDiagram().getUpperLoadLineOfExact()+"",calculateResponseData.getFESDiagram().getUpperLoadLineOfExact()+"","","UPPERLOADLINEOFEXACT","","","","kN",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("理论下载荷","理论下载荷",calculateResponseData.getFESDiagram().getLowerLoadLine()+"",calculateResponseData.getFESDiagram().getLowerLoadLine()+"","","LOWERLOADLINE","","","","kN",1));

			//位移最大、最小值索引
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("位移最大值索引","位移最大值索引",calculateResponseData.getFESDiagram().getSMaxIndex()+"",calculateResponseData.getFESDiagram().getSMaxIndex()+"","","SMAXINDEX","","","","",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("位移最小值索引","位移最小值索引",calculateResponseData.getFESDiagram().getSMinIndex()+"",calculateResponseData.getFESDiagram().getSMinIndex()+"","","SMININDEX","","","","",1));
			
			
			//产量
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("理论排量","理论排量",calculateResponseData.getProduction().getTheoreticalProduction()+"",calculateResponseData.getProduction().getTheoreticalProduction()+"","","THEORETICALPRODUCTION","","","","m^3/d",1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("产液量","产液量",calculateResponseData.getProduction().getLiquidVolumetricProduction()+"",calculateResponseData.getProduction().getLiquidVolumetricProduction()+"","","LIQUIDVOLUMETRICPRODUCTION","","","","m^3/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("产油量","产油量",calculateResponseData.getProduction().getOilVolumetricProduction()+"",calculateResponseData.getProduction().getOilVolumetricProduction()+"","","OILVOLUMETRICPRODUCTION","","","","m^3/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("产水量","产水量",calculateResponseData.getProduction().getWaterVolumetricProduction()+"",calculateResponseData.getProduction().getWaterVolumetricProduction()+"","","WATERVOLUMETRICPRODUCTION","","","","m^3/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞有效冲程计算产量","柱塞有效冲程计算产量",calculateResponseData.getProduction().getAvailablePlungerStrokeVolumetricProduction()+"",calculateResponseData.getProduction().getAvailablePlungerStrokeVolumetricProduction()+"","","AVAILABLEPLUNGERSTROKEPROD_V","","","","m^3/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵间隙漏失量","泵间隙漏失量",calculateResponseData.getProduction().getPumpClearanceLeakVolumetricProduction()+"",calculateResponseData.getProduction().getPumpClearanceLeakVolumetricProduction()+"","","PUMPCLEARANCELEAKPROD_V","","","","m^3/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("游动凡尔漏失量","游动凡尔漏失量",calculateResponseData.getProduction().getTVLeakVolumetricProduction()+"",calculateResponseData.getProduction().getTVLeakVolumetricProduction()+"","","TVLEAKVOLUMETRICPRODUCTION","","","","m^3/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("固定凡尔漏失量","固定凡尔漏失量",calculateResponseData.getProduction().getSVLeakVolumetricProduction()+"",calculateResponseData.getProduction().getSVLeakVolumetricProduction()+"","","SVLEAKVOLUMETRICPRODUCTION","","","","m^3/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("气影响","气影响",calculateResponseData.getProduction().getGasInfluenceVolumetricProduction()+"",calculateResponseData.getProduction().getGasInfluenceVolumetricProduction()+"","","GASINFLUENCEPROD_V","","","","m^3/d",1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("产液量","产液量",calculateResponseData.getProduction().getLiquidWeightProduction()+"",calculateResponseData.getProduction().getLiquidWeightProduction()+"","","LIQUIDWEIGHTPRODUCTION","","","","t/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("产油量","产油量",calculateResponseData.getProduction().getOilWeightProduction()+"",calculateResponseData.getProduction().getOilWeightProduction()+"","","OILWEIGHTPRODUCTION","","","","t/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("产水量","产水量",calculateResponseData.getProduction().getWaterWeightProduction()+"",calculateResponseData.getProduction().getWaterWeightProduction()+"","","WATERWEIGHTPRODUCTION","","","","t/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞有效冲程计算产量","柱塞有效冲程计算产量",calculateResponseData.getProduction().getAvailablePlungerStrokeWeightProduction()+"",calculateResponseData.getProduction().getAvailablePlungerStrokeWeightProduction()+"","","AVAILABLEPLUNGERSTROKEPROD_W","","","","t/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵间隙漏失量","泵间隙漏失量",calculateResponseData.getProduction().getPumpClearanceLeakWeightProduction()+"",calculateResponseData.getProduction().getPumpClearanceLeakWeightProduction()+"","","PUMPCLEARANCELEAKPROD_W","","","","t/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("游动凡尔漏失量","游动凡尔漏失量",calculateResponseData.getProduction().getTVLeakWeightProduction()+"",calculateResponseData.getProduction().getTVLeakWeightProduction()+"","","TVLEAKWEIGHTPRODUCTION","","","","t/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("固定凡尔漏失量","固定凡尔漏失量",calculateResponseData.getProduction().getSVLeakWeightProduction()+"",calculateResponseData.getProduction().getSVLeakWeightProduction()+"","","SVLEAKWEIGHTPRODUCTION","","","","t/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("气影响","气影响",calculateResponseData.getProduction().getGasInfluenceWeightProduction()+"",calculateResponseData.getProduction().getGasInfluenceWeightProduction()+"","","GASINFLUENCEPROD_W","","","","t/d",1));
			
			//液面反演校正值、反演液面
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("液面反演校正值","液面反演校正值",calculateResponseData.getProduction().getLevelCorrectValue()+"",calculateResponseData.getProduction().getLevelCorrectValue()+"","","LEVELCORRECTVALUE","","","","m",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("动液面","动液面",calculateResponseData.getProduction().getProducingfluidLevel()+"",calculateResponseData.getProduction().getProducingfluidLevel()+"","","INVERPRODUCINGFLUIDLEVEL","","","","m",1));
			
			//系统效率
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("有功功率","有功功率",calculateResponseData.getFESDiagram().getAvgWatt()+"",calculateResponseData.getFESDiagram().getAvgWatt()+"","","AVERAGEWATT","","","","kW",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("光杆功率","光杆功率",calculateResponseData.getSystemEfficiency().getPolishRodPower()+"",calculateResponseData.getSystemEfficiency().getPolishRodPower()+"","","POLISHRODPOWER","","","","kW",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("水功率","水功率",calculateResponseData.getSystemEfficiency().getWaterPower()+"",calculateResponseData.getSystemEfficiency().getWaterPower()+"","","WATERPOWER","","","","kW",1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("地面效率","地面效率",calculateResponseData.getSystemEfficiency().getSurfaceSystemEfficiency()+"",calculateResponseData.getSystemEfficiency().getSurfaceSystemEfficiency()+"","","SURFACESYSTEMEFFICIENCY","","","","小数",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("井下效率","井下效率",calculateResponseData.getSystemEfficiency().getWellDownSystemEfficiency()+"",calculateResponseData.getSystemEfficiency().getWellDownSystemEfficiency()+"","","WELLDOWNSYSTEMEFFICIENCY","","","","小数",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("系统效率","系统效率",calculateResponseData.getSystemEfficiency().getSystemEfficiency()+"",calculateResponseData.getSystemEfficiency().getSystemEfficiency()+"","","SYSTEMEFFICIENCY","","","","小数",1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("吨液百米耗电量","吨液百米耗电量",calculateResponseData.getSystemEfficiency().getEnergyPer100mLift()+"",calculateResponseData.getSystemEfficiency().getEnergyPer100mLift()+"","","ENERGYPER100MLIFT","","","","kW· h/100m· t",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("功图面积","功图面积",calculateResponseData.getFESDiagram().getArea()+"",calculateResponseData.getFESDiagram().getArea()+"","","AREA","","","","",1));
			
			//泵效
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("抽油杆伸长量","抽油杆伸长量",calculateResponseData.getPumpEfficiency().getRodFlexLength()+"",calculateResponseData.getPumpEfficiency().getRodFlexLength()+"","","RODFLEXLENGTH","","","","m",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("油管伸缩量","油管伸缩量",calculateResponseData.getPumpEfficiency().getTubingFlexLength()+"",calculateResponseData.getPumpEfficiency().getTubingFlexLength()+"","","TUBINGFLEXLENGTH","","","","m",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("惯性载荷增量","惯性载荷增量",calculateResponseData.getPumpEfficiency().getInertiaLength()+"",calculateResponseData.getPumpEfficiency().getInertiaLength()+"","","INERTIALENGTH","","","","m",1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("冲程损失系数","冲程损失系数",calculateResponseData.getPumpEfficiency().getPumpEff1()+"",calculateResponseData.getPumpEfficiency().getPumpEff1()+"","","PUMPEFF1","","","","小数",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("充满系数","充满系数",calculateResponseData.getPumpEfficiency().getPumpEff2()+"",calculateResponseData.getPumpEfficiency().getPumpEff2()+"","","PUMPEFF2","","","","小数",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("间隙漏失系数","间隙漏失系数",calculateResponseData.getPumpEfficiency().getPumpEff3()+"",calculateResponseData.getPumpEfficiency().getPumpEff3()+"","","PUMPEFF3","","","","小数",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("液体收缩系数","液体收缩系数",calculateResponseData.getPumpEfficiency().getPumpEff4()+"",calculateResponseData.getPumpEfficiency().getPumpEff4()+"","","PUMPEFF4","","","","小数",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("总泵效","总泵效",calculateResponseData.getPumpEfficiency().getPumpEff()+"",calculateResponseData.getPumpEfficiency().getPumpEff()+"","","PUMPEFF","","","","小数",1));
			
			//泵入口出口参数
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口压力","泵入口压力",calculateResponseData.getProduction().getPumpIntakeP()+"",calculateResponseData.getProduction().getPumpIntakeP()+"","","PUMPINTAKEP","","","","MPa",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口温度","泵入口温度",calculateResponseData.getProduction().getPumpIntakeT()+"",calculateResponseData.getProduction().getPumpIntakeT()+"","","PUMPINTAKET","","","","℃",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口就地气液比","泵入口就地气液比",calculateResponseData.getProduction().getPumpIntakeGOL()+"",calculateResponseData.getProduction().getPumpIntakeGOL()+"","","PUMPINTAKEGOL","","","","m^3/m^3",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口粘度","泵入口粘度",calculateResponseData.getProduction().getPumpIntakeVisl()+"",calculateResponseData.getProduction().getPumpIntakeVisl()+"","","PUMPINTAKEVISL","","","","mPa·s",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口原油体积系数","泵入口原油体积系数",calculateResponseData.getProduction().getPumpIntakeBo()+"",calculateResponseData.getProduction().getPumpIntakeBo()+"","","PUMPINTAKEBO","","","","小数",1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口压力","泵出口压力",calculateResponseData.getProduction().getPumpOutletP()+"",calculateResponseData.getProduction().getPumpOutletP()+"","","PUMPOUTLETP","","","","MPa",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口温度","泵出口温度",calculateResponseData.getProduction().getPumpOutletT()+"",calculateResponseData.getProduction().getPumpOutletT()+"","","PUMPOUTLETT","","","","℃",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口就地气液比","泵出口就地气液比",calculateResponseData.getProduction().getPumpOutletGOL()+"",calculateResponseData.getProduction().getPumpOutletGOL()+"","","PUMPOUTLETGOL","","","","m^3/m^3",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口粘度","泵出口粘度",calculateResponseData.getProduction().getPumpOutletVisl()+"",calculateResponseData.getProduction().getPumpOutletVisl()+"","","PUMPOUTLETVISL","","","","mPa·s",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口原油体积系数","泵出口原油体积系数",calculateResponseData.getProduction().getPumpOutletBo()+"",calculateResponseData.getProduction().getPumpOutletBo()+"","","PUMPOUTLETBO","","","","小数",1));
			
			
			//杆参数
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("杆参数","杆参数",calculateResponseData.getRodCalData()+"",calculateResponseData.getRodCalData()+"","","RODSTRING","","","","",1));
		}else{
			//工况
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("工况","工况","","","","resultName","","","","",1));
			//最大最小载荷
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("最大载荷","最大载荷","","","","FMax","","","","kN",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("最小载荷","最小载荷","","","","FMin","","","","kN",1));
			//平衡
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("上冲程最大电流","上冲程最大电流","","","","UPSTROKEIMAX","","","","A",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("下冲程最大电流","下冲程最大电流","","","","DOWNSTROKEIMAX","","","","A",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("上冲程最大功率","上冲程最大功率","","","","UPSTROKEWATTMAX","","","","A",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("下冲程最大功率","下冲程最大功率","","","","DOWNSTROKEWATTMAX","","","","A",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("电流平衡度","电流平衡度","","","","IDEGREEBALANCE","","","","%",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("功率平衡度","功率平衡度","","","","WATTDEGREEBALANCE","","","","%",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("移动距离","移动距离","","","","DELTARADIUS","","","","m",1));
			
			//充满系数、抽空充满系数
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("充满系数","充满系数","","","","FULLNESSCOEFFICIENT","","","","小数",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("抽空充满系数","抽空充满系数","","","","NOLIQUIDFULLNESSCOEFFICIENT","","","","小数",1));
			//柱塞冲程、柱塞有效冲程、抽空柱塞有效冲程
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞冲程","柱塞冲程","","","","PLUNGERSTROKE","","","","m",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞有效冲程","柱塞有效冲程","","","","AVAILABLEPLUNGERSTROKE","","","","m",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("抽空柱塞有效冲程","抽空柱塞有效冲程","","","","NOLIQUIDAVAILABLEPLUNGERSTROKE","","","","m",1));
			
			//上下理论载荷线
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("上理论载荷线","上理论载荷线","","","","UPPERLOADLINE","","","","kN",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("考虑沉没压力的理论上载荷","考虑沉没压力的理论上载荷","","","","UPPERLOADLINEOFEXACT","","","","kN",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("下理论载荷线","下理论载荷线","","","","LOWERLOADLINE","","","","kN",1));

			//位移最大、最小值索引
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("位移最大值索引","位移最大值索引","","","","SMAXINDEX","","","","",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("位移最小值索引","位移最小值索引","","","","SMININDEX","","","","",1));
			
			
			//产量
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("理论排量","理论排量","","","","THEORETICALPRODUCTION","","","","m^3/d",1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("产液量","产液量","","","","LIQUIDVOLUMETRICPRODUCTION","","","","m^3/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("产油量","产油量","","","","OILVOLUMETRICPRODUCTION","","","","m^3/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("产水量","产水量","","","","WATERVOLUMETRICPRODUCTION","","","","m^3/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞有效冲程计算产量","柱塞有效冲程计算产量","","","","AVAILABLEPLUNGERSTROKEPROD_V","","","","m^3/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵间隙漏失量","泵间隙漏失量","","","","PUMPCLEARANCELEAKPROD_V","","","","m^3/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("游动凡尔漏失量","游动凡尔漏失量","","","","TVLEAKVOLUMETRICPRODUCTION","","","","m^3/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("固定凡尔漏失量","固定凡尔漏失量","","","","SVLEAKVOLUMETRICPRODUCTION","","","","m^3/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("气影响","气影响","","","","GASINFLUENCEPROD_V","","","","m^3/d",1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("产液量","产液量","","","","LIQUIDWEIGHTPRODUCTION","","","","t/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("产油量","产油量","","","","OILWEIGHTPRODUCTION","","","","t/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("产水量","产水量","","","","WATERWEIGHTPRODUCTION","","","","t/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("柱塞有效冲程计算产量","柱塞有效冲程计算产量","","","","AVAILABLEPLUNGERSTROKEPROD_W","","","","t/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵间隙漏失量","泵间隙漏失量","","","","PUMPCLEARANCELEAKPROD_W","","","","t/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("游动凡尔漏失量","游动凡尔漏失量","","","","TVLEAKWEIGHTPRODUCTION","","","","t/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("固定凡尔漏失量","固定凡尔漏失量","","","","SVLEAKWEIGHTPRODUCTION","","","","t/d",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("气影响","气影响","","","","GASINFLUENCEPROD_W","","","","t/d",1));
			
			//液面反演校正值、反演液面
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("液面反演校正值","液面反演校正值","","","","LEVELCORRECTVALUE","","","","m",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("反演液面","反演液面","","","","INVERPRODUCINGFLUIDLEVEL","","","","m",1));
			
			//系统效率
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("有功功率","有功功率","","","","AVERAGEWATT","","","","kW",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("光杆功率","光杆功率","","","","POLISHRODPOWER","","","","kW",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("水功率","水功率","","","","WATERPOWER","","","","kW",1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("地面效率","地面效率","","","","SURFACESYSTEMEFFICIENCY","","","","小数",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("井下效率","井下效率","","","","WELLDOWNSYSTEMEFFICIENCY","","","","小数",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("系统效率","系统效率","","","","SYSTEMEFFICIENCY","","","","小数",1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("吨液百米耗电量","吨液百米耗电量","","","","ENERGYPER100MLIFT","","","","kW· h/100m· t",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("功图面积","功图面积","","","","AREA","","","","",1));
			
			//泵效
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("抽油杆伸长量","抽油杆伸长量","","","","RODFLEXLENGTH","","","","m",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("油管伸缩量","油管伸缩量","","","","TUBINGFLEXLENGTH","","","","m",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("惯性载荷增量","惯性载荷增量","","","","INERTIALENGTH","","","","m",1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("冲程损失系数","冲程损失系数","","","","PUMPEFF1","","","","小数",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("充满系数","充满系数","","","","PUMPEFF2","","","","小数",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("间隙漏失系数","间隙漏失系数","","","","PUMPEFF3","","","","小数",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("液体收缩系数","液体收缩系数","","","","PUMPEFF4","","","","小数",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("总泵效","总泵效","","","","PUMPEFF","","","","小数",1));
			
			//泵入口出口参数
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口压力","泵入口压力","","","","PUMPINTAKEP","","","","MPa",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口温度","泵入口温度","","","","PUMPINTAKET","","","","℃",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口就地气液比","泵入口就地气液比","","","","PUMPINTAKEGOL","","","","m^3/m^3",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口粘度","泵入口粘度","","","","PUMPINTAKEVISL","","","","mPa·s",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵入口原油体积系数","泵入口原油体积系数","","","","PUMPINTAKEBO","","","","小数",1));
			
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口压力","泵出口压力","","","","PUMPOUTLETP","","","","MPa",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口温度","泵出口温度","","","","PUMPOUTLETT","","","","℃",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口就地气液比","泵出口就地气液比","","","","PUMPOUTLETGOL","","","","m^3/m^3",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口粘度","泵出口粘度","","","","PUMPOUTLETVISL","","","","mPa·s",1));
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("泵出口原油体积系数","泵出口原油体积系数","","","","PUMPOUTLETBO","","","","小数",1));
			
			
			//杆参数
			FESDiagramCalItemList.add(new ProtocolItemResolutionData("杆参数","杆参数","","","","RODSTRING","","","","",1));
		}
		return FESDiagramCalItemList;
	}
	
	public static List<ProtocolItemResolutionData> getRPMCalItemData(PCPCalculateRequestData calculateRequestData,PCPCalculateResponseData calculateResponseData){
		List<ProtocolItemResolutionData> calItemList=new ArrayList<ProtocolItemResolutionData>();
		if(calculateResponseData!=null&&calculateResponseData.getCalculationStatus().getResultStatus()==1){
			//产量
			calItemList.add(new ProtocolItemResolutionData("理论排量","理论排量",calculateResponseData.getProduction().getTheoreticalProduction()+"",calculateResponseData.getProduction().getTheoreticalProduction()+"","","THEORETICALPRODUCTION","","","","m^3/d",1));
			
			calItemList.add(new ProtocolItemResolutionData("产液量","产液量",calculateResponseData.getProduction().getLiquidVolumetricProduction()+"",calculateResponseData.getProduction().getLiquidVolumetricProduction()+"","","LIQUIDVOLUMETRICPRODUCTION","","","","m^3/d",1));
			calItemList.add(new ProtocolItemResolutionData("产油量","产油量",calculateResponseData.getProduction().getOilVolumetricProduction()+"",calculateResponseData.getProduction().getOilVolumetricProduction()+"","","OILVOLUMETRICPRODUCTION","","","","m^3/d",1));
			calItemList.add(new ProtocolItemResolutionData("产水量","产水量",calculateResponseData.getProduction().getWaterVolumetricProduction()+"",calculateResponseData.getProduction().getWaterVolumetricProduction()+"","","WATERVOLUMETRICPRODUCTION","","","","m^3/d",1));
			
			calItemList.add(new ProtocolItemResolutionData("产液量","产液量",calculateResponseData.getProduction().getLiquidWeightProduction()+"",calculateResponseData.getProduction().getLiquidWeightProduction()+"","","LIQUIDWEIGHTPRODUCTION","","","","t/d",1));
			calItemList.add(new ProtocolItemResolutionData("产油量","产油量",calculateResponseData.getProduction().getOilWeightProduction()+"",calculateResponseData.getProduction().getOilWeightProduction()+"","","OILWEIGHTPRODUCTION","","","","t/d",1));
			calItemList.add(new ProtocolItemResolutionData("产水量","产水量",calculateResponseData.getProduction().getWaterWeightProduction()+"",calculateResponseData.getProduction().getWaterWeightProduction()+"","","WATERWEIGHTPRODUCTION","","","","t/d",1));
			
			//系统效率
			calItemList.add(new ProtocolItemResolutionData("有功功率","有功功率",calculateResponseData.getSystemEfficiency().getMotorInputWatt()+"",calculateResponseData.getSystemEfficiency().getMotorInputWatt()+"","","AVERAGEWATT","","","","kW",1));
			calItemList.add(new ProtocolItemResolutionData("水功率","水功率",calculateResponseData.getSystemEfficiency().getWaterPower()+"",calculateResponseData.getSystemEfficiency().getWaterPower()+"","","WATERPOWER","","","","kW",1));
			calItemList.add(new ProtocolItemResolutionData("系统效率","系统效率",calculateResponseData.getSystemEfficiency().getSystemEfficiency()+"",calculateResponseData.getSystemEfficiency().getSystemEfficiency()+"","","SYSTEMEFFICIENCY","","","","小数",1));
			
			//泵效
			calItemList.add(new ProtocolItemResolutionData("容积效率","容积效率",calculateResponseData.getPumpEfficiency().getPumpEff1()+"",calculateResponseData.getPumpEfficiency().getPumpEff1()+"","","PUMPEFF1","","","","小数",1));
			calItemList.add(new ProtocolItemResolutionData("液体收缩系数","液体收缩系数",calculateResponseData.getPumpEfficiency().getPumpEff2()+"",calculateResponseData.getPumpEfficiency().getPumpEff2()+"","","PUMPEFF2","","","","小数",1));
			calItemList.add(new ProtocolItemResolutionData("总泵效","总泵效",calculateResponseData.getPumpEfficiency().getPumpEff()+"",calculateResponseData.getPumpEfficiency().getPumpEff()+"","","PUMPEFF","","","","小数",1));
			
			//泵入口出口参数
			calItemList.add(new ProtocolItemResolutionData("泵入口压力","泵入口压力",calculateResponseData.getProduction().getPumpIntakeP()+"",calculateResponseData.getProduction().getPumpIntakeP()+"","","PUMPINTAKEP","","","","MPa",1));
			calItemList.add(new ProtocolItemResolutionData("泵入口温度","泵入口温度",calculateResponseData.getProduction().getPumpIntakeT()+"",calculateResponseData.getProduction().getPumpIntakeT()+"","","PUMPINTAKET","","","","℃",1));
			calItemList.add(new ProtocolItemResolutionData("泵入口就地气液比","泵入口就地气液比",calculateResponseData.getProduction().getPumpIntakeGOL()+"",calculateResponseData.getProduction().getPumpIntakeGOL()+"","","PUMPINTAKEGOL","","","","m^3/m^3",1));
			calItemList.add(new ProtocolItemResolutionData("泵入口粘度","泵入口粘度",calculateResponseData.getProduction().getPumpIntakeVisl()+"",calculateResponseData.getProduction().getPumpIntakeVisl()+"","","PUMPINTAKEVISL","","","","mPa·s",1));
			calItemList.add(new ProtocolItemResolutionData("泵入口原油体积系数","泵入口原油体积系数",calculateResponseData.getProduction().getPumpIntakeBo()+"",calculateResponseData.getProduction().getPumpIntakeBo()+"","","PUMPINTAKEBO","","","","小数",1));
			
			calItemList.add(new ProtocolItemResolutionData("泵出口压力","泵出口压力",calculateResponseData.getProduction().getPumpOutletP()+"",calculateResponseData.getProduction().getPumpOutletP()+"","","PUMPOUTLETP","","","","MPa",1));
			calItemList.add(new ProtocolItemResolutionData("泵出口温度","泵出口温度",calculateResponseData.getProduction().getPumpOutletT()+"",calculateResponseData.getProduction().getPumpOutletT()+"","","PUMPOUTLETT","","","","℃",1));
			calItemList.add(new ProtocolItemResolutionData("泵出口就地气液比","泵出口就地气液比",calculateResponseData.getProduction().getPumpOutletGOL()+"",calculateResponseData.getProduction().getPumpOutletGOL()+"","","PUMPOUTLETGOL","","","","m^3/m^3",1));
			calItemList.add(new ProtocolItemResolutionData("泵出口粘度","泵出口粘度",calculateResponseData.getProduction().getPumpOutletVisl()+"",calculateResponseData.getProduction().getPumpOutletVisl()+"","","PUMPOUTLETVISL","","","","mPa·s",1));
			calItemList.add(new ProtocolItemResolutionData("泵出口原油体积系数","泵出口原油体积系数",calculateResponseData.getProduction().getPumpOutletBo()+"",calculateResponseData.getProduction().getPumpOutletBo()+"","","PUMPOUTLETBO","","","","小数",1));
			
			
			//杆参数
			calItemList.add(new ProtocolItemResolutionData("杆参数","杆参数",calculateResponseData.getRodCalData()+"",calculateResponseData.getRodCalData()+"","","RODSTRING","","","","",1));
		}else{
			//产量
			calItemList.add(new ProtocolItemResolutionData("理论排量","理论排量","","","","THEORETICALPRODUCTION","","","","m^3/d",1));
			
			calItemList.add(new ProtocolItemResolutionData("产液量","产液量","","","","LIQUIDVOLUMETRICPRODUCTION","","","","m^3/d",1));
			calItemList.add(new ProtocolItemResolutionData("产油量","产油量","","","","OILVOLUMETRICPRODUCTION","","","","m^3/d",1));
			calItemList.add(new ProtocolItemResolutionData("产水量","产水量","","","","WATERVOLUMETRICPRODUCTION","","","","m^3/d",1));
			calItemList.add(new ProtocolItemResolutionData("柱塞有效冲程计算产量","柱塞有效冲程计算产量","","","","AVAILABLEPLUNGERSTROKEPROD_V","","","","m^3/d",1));
			calItemList.add(new ProtocolItemResolutionData("泵间隙漏失量","泵间隙漏失量","","","","PUMPCLEARANCELEAKPROD_V","","","","m^3/d",1));
			calItemList.add(new ProtocolItemResolutionData("游动凡尔漏失量","游动凡尔漏失量","","","","TVLEAKVOLUMETRICPRODUCTION","","","","m^3/d",1));
			calItemList.add(new ProtocolItemResolutionData("固定凡尔漏失量","固定凡尔漏失量","","","","SVLEAKVOLUMETRICPRODUCTION","","","","m^3/d",1));
			calItemList.add(new ProtocolItemResolutionData("气影响","气影响","","","","GASINFLUENCEPROD_V","","","","m^3/d",1));
			
			calItemList.add(new ProtocolItemResolutionData("产液量","产液量","","","","LIQUIDWEIGHTPRODUCTION","","","","t/d",1));
			calItemList.add(new ProtocolItemResolutionData("产油量","产油量","","","","OILWEIGHTPRODUCTION","","","","t/d",1));
			calItemList.add(new ProtocolItemResolutionData("产水量","产水量","","","","WATERWEIGHTPRODUCTION","","","","t/d",1));
			calItemList.add(new ProtocolItemResolutionData("柱塞有效冲程计算产量","柱塞有效冲程计算产量","","","","AVAILABLEPLUNGERSTROKEPROD_W","","","","t/d",1));
			calItemList.add(new ProtocolItemResolutionData("泵间隙漏失量","泵间隙漏失量","","","","PUMPCLEARANCELEAKPROD_W","","","","t/d",1));
			calItemList.add(new ProtocolItemResolutionData("游动凡尔漏失量","游动凡尔漏失量","","","","TVLEAKWEIGHTPRODUCTION","","","","t/d",1));
			calItemList.add(new ProtocolItemResolutionData("固定凡尔漏失量","固定凡尔漏失量","","","","SVLEAKWEIGHTPRODUCTION","","","","t/d",1));
			calItemList.add(new ProtocolItemResolutionData("气影响","气影响","","","","GASINFLUENCEPROD_W","","","","t/d",1));
			
			
			
			//系统效率
			calItemList.add(new ProtocolItemResolutionData("有功功率","有功功率","","","","AVERAGEWATT","","","","kW",1));
			calItemList.add(new ProtocolItemResolutionData("水功率","水功率","","","","WATERPOWER","","","","kW",1));
			calItemList.add(new ProtocolItemResolutionData("系统效率","系统效率","","","","SYSTEMEFFICIENCY","","","","小数",1));
			
			//泵效
			calItemList.add(new ProtocolItemResolutionData("抽油杆伸长量","抽油杆伸长量","","","","RODFLEXLENGTH","","","","m",1));
			calItemList.add(new ProtocolItemResolutionData("油管伸缩量","油管伸缩量","","","","TUBINGFLEXLENGTH","","","","m",1));
			calItemList.add(new ProtocolItemResolutionData("惯性载荷增量","惯性载荷增量","","","","INERTIALENGTH","","","","m",1));
			
			calItemList.add(new ProtocolItemResolutionData("容积效率","容积效率","","","","PUMPEFF1","","","","小数",1));
			calItemList.add(new ProtocolItemResolutionData("液体收缩系数","液体收缩系数","","","","PUMPEFF2","","","","小数",1));
			calItemList.add(new ProtocolItemResolutionData("总泵效","总泵效","","","","PUMPEFF","","","","小数",1));
			
			//泵入口出口参数
			calItemList.add(new ProtocolItemResolutionData("泵入口压力","泵入口压力","","","","PUMPINTAKEP","","","","MPa",1));
			calItemList.add(new ProtocolItemResolutionData("泵入口温度","泵入口温度","","","","PUMPINTAKET","","","","℃",1));
			calItemList.add(new ProtocolItemResolutionData("泵入口就地气液比","泵入口就地气液比","","","","PUMPINTAKEGOL","","","","m^3/m^3",1));
			calItemList.add(new ProtocolItemResolutionData("泵入口粘度","泵入口粘度","","","","PUMPINTAKEVISL","","","","mPa·s",1));
			calItemList.add(new ProtocolItemResolutionData("泵入口原油体积系数","泵入口原油体积系数","","","","PUMPINTAKEBO","","","","小数",1));
			
			calItemList.add(new ProtocolItemResolutionData("泵出口压力","泵出口压力","","","","PUMPOUTLETP","","","","MPa",1));
			calItemList.add(new ProtocolItemResolutionData("泵出口温度","泵出口温度","","","","PUMPOUTLETT","","","","℃",1));
			calItemList.add(new ProtocolItemResolutionData("泵出口就地气液比","泵出口就地气液比","","","","PUMPOUTLETGOL","","","","m^3/m^3",1));
			calItemList.add(new ProtocolItemResolutionData("泵出口粘度","泵出口粘度","","","","PUMPOUTLETVISL","","","","mPa·s",1));
			calItemList.add(new ProtocolItemResolutionData("泵出口原油体积系数","泵出口原油体积系数","","","","PUMPOUTLETBO","","","","小数",1));
			
			
			//杆参数
			calItemList.add(new ProtocolItemResolutionData("杆参数","杆参数","","","","RODSTRING","","","","",1));
		}
		return calItemList;
	}
}
