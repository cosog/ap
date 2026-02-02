package com.cosog.service.realTimeMonitoring;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AccessToken;
import com.cosog.model.DataMapping;
import com.cosog.model.DeviceAddInfo;
import com.cosog.model.KeyValue;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.CommStatus;
import com.cosog.model.CurveConf;
import com.cosog.model.User;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AcqInstanceOwnItem;
import com.cosog.model.calculate.AlarmInstanceOwnItem;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.DisplayInstanceOwnItem;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.PCPProductionData;
import com.cosog.model.calculate.SRPCalculateRequestData;
import com.cosog.model.calculate.SRPProductionData;
import com.cosog.model.calculate.UserInfo;
import com.cosog.model.calculate.DisplayInstanceOwnItem.DisplayItem;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.data.DataitemsInfo;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.drive.ModbusProtocolConfig.Items;
import com.cosog.model.drive.ModbusProtocolConfig.Protocol;
import com.cosog.model.gridmodel.WellHandsontableChangedData;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.task.MemoryDataManagerTask.CalItem;
import com.cosog.utils.AcquisitionItemColumnsMap;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.Page;
import com.cosog.utils.ProtocolItemResolutionData;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.excel.ExcelUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;
import redis.clients.jedis.Jedis;

@Service("realTimeMonitoringService")
public class RealTimeMonitoringService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	
	public String getRealTimeMonitoringFESDiagramResultStatData(String orgId,String deviceType,String commStatusStatValue,String deviceTypeStatValue,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		AlarmShowStyle alarmShowStyle=null;
		List<DeviceInfo> deviceList=null;
		boolean jedisStatus=false;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try{
			try{
				jedisStatus=MemoryDataManagerTask.getJedisStatus();
			}catch(Exception e){
				e.printStackTrace();
			}
			String columns = "["
					+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",\"width\":50,\"children\":[] },"
					+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"item\",\"children\":[] },"
					+ "{ \"header\":\""+languageResourceMap.get("variable")+"\",\"dataIndex\":\"count\",\"children\":[] }"
					+ "]";
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"totalRoot\":[");
			int totalCount=0;
			if(jedisStatus){
				alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
				deviceList=MemoryDataManagerTask.getDeviceInfoByOrgIdArr(orgId.split(","));
				if(deviceList!=null){
					Map<Integer,Integer> totalMap=new TreeMap<Integer,Integer>();
					for(int i=0;i<deviceList.size();i++){
						DeviceInfo deviceInfo=deviceList.get(i);
						if(StringManagerUtils.stringToArrExistNum(orgId, deviceInfo.getOrgId()) 
								&& StringManagerUtils.stringToArrExistNum(deviceType, deviceInfo.getDeviceType()) 
								&& deviceInfo.getCalculateType()==1){
							int count=1;
							int resultCode=deviceInfo.getResultCode()==null?0:deviceInfo.getResultCode();
							if(totalMap.containsKey(resultCode)){
								count=totalMap.get(resultCode)+1;
							}
							totalMap.put(resultCode, count);
						}
					}
					
					int index=1;
					totalCount=totalMap.size();
					for(Integer key:totalMap.keySet()){
						String item=languageResourceMap.get("emptyMsg");
						WorkType workType=MemoryDataManagerTask.getWorkTypeByCode(key+"",language);
						if(workType!=null){
							item=workType.getResultName();
						}
						result_json.append("{\"id\":"+index+",");
						result_json.append("\"item\":\""+item+"\",");
						result_json.append("\"itemCode\":\""+key+"\",");
						result_json.append("\"count\":"+totalMap.get(key)+"},");
						index++;
					}
					
					if(result_json.toString().endsWith(",")){
						result_json.deleteCharAt(result_json.length() - 1);
					}
					result_json.append("]");
				}
			}
			
			result_json.append(",\"totalCount\":"+totalCount+",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle));
			result_json.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getRealTimeMonitoringNumStatusStatData(String orgId,String deviceType,String deviceTypeStatValue,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		AlarmShowStyle alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"item\",children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("variable")+"\",\"dataIndex\":\"count\",children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":4,");
		

		String tableName="tbl_acqdata_latest";
		String deviceTableName="viw_device";
		
		String sql="select t2.commstatus,t2.runstatus,t2.alarminfo,t.alarmInstanceCode "
				+ " from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on  t2.deviceid=t.id "
				+ " where t.orgid in("+orgId+") "
				+ " and t.deviceType in ("+deviceType+")";
		int normalDeviceCount=0,firstLevelCount=0,secondLevelCount=0,thirdLevelCount=0;
		
		
		List<?> list = this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
//			int commStatus=StringManagerUtils.stringToInteger(obj[0]+"");
//			int runStatus=StringManagerUtils.stringToInteger(obj[1]+"");
			String deviceAlarmInfo=StringManagerUtils.CLOBObjectToString(obj[2]);
			if(!StringManagerUtils.isNotNull(deviceAlarmInfo)){
				deviceAlarmInfo="[]";
			}
			String alarmInstanceCode=(obj[3]+"").replace("null", "");
			
			AlarmInstanceOwnItem alarmInstanceOwnItem=null;
			alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(alarmInstanceCode);
			
			type = new TypeToken<List<KeyValue>>() {}.getType();
			List<KeyValue> alarmInfoList=gson.fromJson(deviceAlarmInfo, type);
			
			
//			int commAlarmLevel=0,runAlarmLevel=0,maxAlarmLevel=0;
			int maxAlarmLevel=0;
			if(alarmInstanceOwnItem!=null){
//				for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
//					if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getValue()==commStatus){
//						commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
//					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getValue()==runStatus){
//						runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
//					}
//				}
//				
//				if(commAlarmLevel==100){
//					firstLevelCount++;
//				}else if(commAlarmLevel==200){
//					secondLevelCount++;
//				}else if(commAlarmLevel==300){
//					thirdLevelCount++;
//				}
//				
//				if(runAlarmLevel==100){
//					firstLevelCount++;
//				}else if(runAlarmLevel==200){
//					secondLevelCount++;
//				}else if(runAlarmLevel==300){
//					thirdLevelCount++;
//				}
//				
//				
//				if(commAlarmLevel>0){
//					 maxAlarmLevel=commAlarmLevel;
//				 }
//				 
//				 if(runAlarmLevel>0 && (maxAlarmLevel==0 || runAlarmLevel<maxAlarmLevel) ){
//					 maxAlarmLevel=runAlarmLevel;
//				 }
				
				
				for(KeyValue keyValue:alarmInfoList){
					int itemALarmLevel=StringManagerUtils.stringToInteger(keyValue.getValue());
					if(itemALarmLevel>0 && (maxAlarmLevel==0 || itemALarmLevel<maxAlarmLevel) ){
						 maxAlarmLevel=itemALarmLevel;
					}
					if(itemALarmLevel==100){
						firstLevelCount++;
					}else if(itemALarmLevel==200){
						secondLevelCount++;
					}else if(itemALarmLevel==300){
						thirdLevelCount++;
					}
				}
				
			}
			if(maxAlarmLevel==0){
				normalDeviceCount++;
			}
		}
		result_json.append("\"totalRoot\":[");
		result_json.append("{\"id\":1,");
		result_json.append("\"item\":'"+languageResourceMap.get("normal")+"',");
		result_json.append("\"level\":0,");
		result_json.append("\"count\":"+normalDeviceCount+"},");
		
		result_json.append("{\"id\":2,");
		result_json.append("\"item\":'"+languageResourceMap.get("alarmLevel1")+"',");
		result_json.append("\"level\":100,");
		result_json.append("\"count\":"+firstLevelCount+"},");
		
		result_json.append("{\"id\":3,");
		result_json.append("\"item\":'"+languageResourceMap.get("alarmLevel2")+"',");
		result_json.append("\"level\":200,");
		result_json.append("\"count\":"+secondLevelCount+"},");
		
		result_json.append("{\"id\":4,");
		result_json.append("\"item\":'"+languageResourceMap.get("alarmLevel3")+"',");
		result_json.append("\"level\":300,");
		result_json.append("\"count\":"+thirdLevelCount+"}");
		result_json.append("]");
		result_json.append(",\"AlarmShowStyle\":"+(alarmShowStyle!=null?new Gson().toJson(alarmShowStyle):"{}"));
		result_json.append("}");

		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getRealTimeMonitoringCommStatusStatData(String orgId,String deviceType,String deviceTypeStatValue,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		AlarmShowStyle alarmShowStyle=null;
		List<DeviceInfo> deviceList=null;
		boolean jedisStatus=false;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try{
			jedisStatus=MemoryDataManagerTask.getJedisStatus();
			alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
			
			String columns = "["
					+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"item\",children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("variable")+"\",\"dataIndex\":\"count\",children:[] }"
					+ "]";
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"totalCount\":3,");
			int total=0,online=0,goOnline=0,offline=0;
			if(!jedisStatus){
				String tableName="tbl_acqdata_latest";
				String deviceTableName="viw_device";
				
				String sql="select t2.commstatus,count(1) "
						+ " from "+deviceTableName+" t "
						+ " left outer join "+tableName+" t2 on  t2.deviceid=t.id "
						+ " where t.orgid in("+orgId+") "
						+ " and t.deviceType in ("+deviceType+")";
				if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
					sql+=" and t.devicetypename_"+language+"='"+deviceTypeStatValue+"'";
				}
				sql+=" group by t2.commstatus";
				
				List<?> list = this.findCallSql(sql);
				for(int i=0;i<list.size();i++){
					Object[] obj=(Object[]) list.get(i);
					if(StringManagerUtils.stringToInteger(obj[0]+"")==1){
						online=StringManagerUtils.stringToInteger(obj[1]+"");
					}else if(StringManagerUtils.stringToInteger(obj[0]+"")==2){
						goOnline=StringManagerUtils.stringToInteger(obj[1]+"");
					}else{
						offline=StringManagerUtils.stringToInteger(obj[1]+"");
					}
				}
			}else{
				deviceList =MemoryDataManagerTask.getDeviceInfoByOrgIdArr(orgId.split(","));
				if(deviceList!=null){
					for(int i=0;i<deviceList.size();i++){
						int commStatus=0;
						DeviceInfo deviceInfo=deviceList.get(i);
						if(StringManagerUtils.stringToArrExistNum(orgId, deviceInfo.getOrgId()) 
								&& StringManagerUtils.stringToArrExistNum(deviceType, deviceInfo.getDeviceType()) 
								){
							commStatus=deviceInfo.getOnLineCommStatus();
							if(commStatus==1){
								online+=1;
							}else if(commStatus==2){
								goOnline+=1;
							}else{
								offline+=1;;
							}
						}
					}
				}
			}
			
			total=online+goOnline+offline;
			result_json.append("\"totalRoot\":[");
			result_json.append("{\"id\":1,");
			result_json.append("\"item\":'"+languageResourceMap.get("all")+"',");
			result_json.append("\"itemCode\":\"all\",");
			result_json.append("\"count\":"+total+"},");
			
			result_json.append("{\"id\":2,");
			result_json.append("\"item\":'"+languageResourceMap.get("online")+"',");
			result_json.append("\"itemCode\":\"online\",");
			result_json.append("\"count\":"+online+"},");
			
			result_json.append("{\"id\":3,");
			result_json.append("\"item\":'"+languageResourceMap.get("goOnline")+"',");
			result_json.append("\"itemCode\":\"goOnline\",");
			result_json.append("\"count\":"+goOnline+"},");
			
			result_json.append("{\"id\":4,");
			result_json.append("\"item\":'"+languageResourceMap.get("offline")+"',");
			result_json.append("\"itemCode\":\"offline\",");
			result_json.append("\"count\":"+offline+"}");
			result_json.append("]");
			result_json.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle));
			result_json.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getRealTimeMonitoringRunStatusStatData(String orgId,String deviceType,String deviceTypeStatValue,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		AlarmShowStyle alarmShowStyle=null;
		List<DeviceInfo> deviceList=null;
		boolean jedisStatus=false;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try{
			jedisStatus=MemoryDataManagerTask.getJedisStatus();
			alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
			deviceList =MemoryDataManagerTask.getDeviceInfoByOrgIdArr(orgId.split(","));
			String columns = "["
					+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"item\",children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("variable")+"\",\"dataIndex\":\"count\",children:[] }"
					+ "]";
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"totalCount\":4,");
			int total=0,run=0,stop=0,noData=0,offline=0,goOnline=0;
			if(!jedisStatus){
				String tableName="tbl_acqdata_latest";
				String deviceTableName="viw_device";
				
				String sql="select decode(t2.commstatus,0,-1,2,-2,decode(t2.runstatus,null,2,t2.runstatus)) as runstatus,count(1) from "+deviceTableName+" t "
						+ " left outer join "+tableName+" t2 on  t2.deviceid=t.id "
						+ " where t.orgid in("+orgId+") "
						+ " and t.deviceType in ("+deviceType+")";
				if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
					sql+=" and t.devicetypename_"+language+"='"+deviceTypeStatValue+"'";
				}
				sql+=" group by t2.commstatus,t2.runstatus";
				
				List<?> list = this.findCallSql(sql);
				for(int i=0;i<list.size();i++){
					Object[] obj=(Object[]) list.get(i);
					if(StringManagerUtils.stringToInteger(obj[0]+"")==1){
						run=StringManagerUtils.stringToInteger(obj[1]+"");
					}else if(StringManagerUtils.stringToInteger(obj[0]+"")==0){
						stop=StringManagerUtils.stringToInteger(obj[1]+"");
					}else if(StringManagerUtils.stringToInteger(obj[0]+"")==-1){
						offline=StringManagerUtils.stringToInteger(obj[1]+"");
					}else if(StringManagerUtils.stringToInteger(obj[0]+"")==-2){
						goOnline=StringManagerUtils.stringToInteger(obj[1]+"");
					}else{
						noData=StringManagerUtils.stringToInteger(obj[1]+"");
					}
				}
			}else{
				if(deviceList!=null){
					for(int i=0;i<deviceList.size();i++){
						int commStatus=0;
						int runStatus=0;
						DeviceInfo deviceInfo=(DeviceInfo)deviceList.get(i);
						if(StringManagerUtils.stringToArrExistNum(orgId, deviceInfo.getOrgId())  
								&& StringManagerUtils.stringToArrExistNum(deviceType, deviceInfo.getDeviceType()) 
								){
							commStatus=deviceInfo.getOnLineCommStatus();
							runStatus=deviceInfo.getRunStatus();
							if(commStatus==1){
								if(runStatus==1){
									run+=1;
								}else if(runStatus==0){
									stop+=1;;
								}else{
									noData+=1;
								}
							}else if(commStatus==2){
								goOnline+=1;
							}else{
								offline+=1;
							}
						}
					
					
					}
				}
			}
			
			total=run+stop+noData+offline;
			result_json.append("\"totalRoot\":[");
			result_json.append("{\"id\":1,");
			result_json.append("\"item\":'"+languageResourceMap.get("all")+"',");
			result_json.append("\"itemCode\":\"all\",");
			result_json.append("\"count\":"+total+"},");
			
			result_json.append("{\"id\":2,");
			result_json.append("\"item\":'"+languageResourceMap.get("run")+"',");
			result_json.append("\"itemCode\":\"run\",");
			result_json.append("\"count\":"+run+"},");
			
			result_json.append("{\"id\":3,");
			result_json.append("\"item\":'"+languageResourceMap.get("stop")+"',");
			result_json.append("\"itemCode\":\"stop\",");
			result_json.append("\"count\":"+stop+"},");
			
			result_json.append("{\"id\":4,");
			result_json.append("\"item\":'"+languageResourceMap.get("emptyMsg")+"',");
			result_json.append("\"itemCode\":\"noData\",");
			result_json.append("\"count\":"+noData+"},");
			
			result_json.append("{\"id\":5,");
			result_json.append("\"item\":'"+languageResourceMap.get("goOnline")+"',");
			result_json.append("\"itemCode\":\"goOnline\",");
			result_json.append("\"count\":"+goOnline+"},");
			
			result_json.append("{\"id\":6,");
			result_json.append("\"item\":'"+languageResourceMap.get("offline")+"',");
			result_json.append("\"itemCode\":\"offline\",");
			result_json.append("\"count\":"+offline+"}");
			result_json.append("]");
			result_json.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle));
			result_json.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getRealTimeMonitoringDeviceTypeStatData(String orgId,String deviceType,String commStatusStatValue,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		AlarmShowStyle alarmShowStyle=null;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try{
			alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
			
			String tableName="tbl_acqdata_latest";
			String deviceTableName="viw_device";
			
			String sql="select t.devicetypename_"+language+",t.devicetype,count(1) from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t.id=t2.deviceid "
					+ " where t.orgid in("+orgId+") "
					+ " and t.deviceType in ("+deviceType+")";
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"')='"+commStatusStatValue+"'";
			}
			sql+=" group by t.devicetypename_"+language+",t.devicetype";
			sql+=" order by t.devicetype";
			
			List<?> list = this.findCallSql(sql);
			String columns = "["
					+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"item\",children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("variable")+"\",\"dataIndex\":\"count\",children:[] }"
					+ "]";
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"totalCount\":"+list.size()+",");
			
			result_json.append("\"totalRoot\":[");
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				result_json.append("{\"id\":"+(i+1)+",");
				result_json.append("\"item\":\""+obj[0]+"\",");
				result_json.append("\"itemCode\":\""+obj[1]+"\",");
				result_json.append("\"count\":"+obj[2]+"},");
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]");
			result_json.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle));
			result_json.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public int getDeviceRealTimeOverviewDataPage(String orgId,String deviceId,String deviceName,String deviceType,String FESdiagramResultStatValue,String commStatusStatValue,String runStatusStatValue,String numStatusStatValue,String deviceTypeStatValue,String limit,String language){
		int dataPage=1;
		try{
			String tableName="tbl_acqdata_latest";
			String deviceTableName="viw_device";
			String calTableName="tbl_srpacqdata_latest";
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			String sql="select t.id from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceid=t.id";
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+= " left outer join "+calTableName+" t3 on t3.deviceid=t.id";
			}
			sql+= " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNum(deviceType)){
				sql+= " and t.devicetype="+deviceType;
			}else{
				sql+= " and t.devicetype in ("+deviceType+")";
			}
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+=" and t.calculateType=1 ";
				if(FESdiagramResultStatValue.equalsIgnoreCase(languageResourceMap.get("emptyMsg"))){
					sql+=" and (t3.resultcode=0 or t3.resultcode is null)";
				}else{
					sql+=" and t3.resultcode="+MemoryDataManagerTask.getWorkTypeByName(FESdiagramResultStatValue,language).getResultCode();
				}
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'"+languageResourceMap.get("offline")+"',null,'"+languageResourceMap.get("offline")+"',2,'"+languageResourceMap.get("goOnline")+"',decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"'))='"+runStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(numStatusStatValue)){
				if(StringManagerUtils.stringToInteger(numStatusStatValue)==0){
					sql+=" and decode(t2.alarmlevel1,null,0,t2.alarmlevel1) = 0 and decode(t2.alarmlevel2,null,0,t2.alarmlevel2) = 0 and decode(t2.alarmlevel3,null,0,t2.alarmlevel3) = 0";
				}else if(StringManagerUtils.stringToInteger(numStatusStatValue)==100){
					sql+=" and decode(t2.alarmlevel1,null,0,t2.alarmlevel1)>0";
				}else if(StringManagerUtils.stringToInteger(numStatusStatValue)==200){
					sql+=" and decode(t2.alarmlevel2,null,0,t2.alarmlevel2)>0";
				}else if(StringManagerUtils.stringToInteger(numStatusStatValue)==300){
					sql+=" and decode(t2.alarmlevel3,null,0,t2.alarmlevel3)>0";
				}
			}
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.deviceName";
			dataPage=this.getDataPage(StringManagerUtils.stringToInteger(deviceId), sql, StringManagerUtils.stringToInteger(limit));
		}catch(Exception e){
			dataPage=1;
		}
		return dataPage;
	}
	
	public String getDeviceOverview(
			String orgId,
			String deviceName,
			String deviceType,
			String dictDeviceType,
			String FESdiagramResultStatValue,
			String commStatusStatValue,
			String runStatusStatValue,
			String numStatusStatValue,
			String deviceTypeStatValue,
			Page pager,
			User user) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		AlarmShowStyle alarmShowStyle=null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		try{
			String language=user!=null?user.getLanguageName():"";
			int languageValue=user!=null?user.getLanguage():0;
			int userNo=user!=null?user.getUserNo():0;
			
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
			Map<String,DataMapping> protocolExtendedFieldColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(1);
			Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
			
			UserInfo userInfo=MemoryDataManagerTask.getUserInfoByNo(userNo+"");
			
			alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
			
			
			String tableName="tbl_acqdata_latest";
			String deviceTableName="viw_device";
			String calAndInputTableName="tbl_srpacqdata_latest";
			String ddicCode="realTimeMonitoring_Overview";
			DataDictionary ddic = null;
			
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicCode,dictDeviceType,language);
			List<DataitemsInfo> dataItemList=ddic.getDataItemList();
			
			String columns = ddic.getTableHeader();
			
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			int timeEfficiencyZoom=1;
			if(timeEfficiencyUnitType==2){
				timeEfficiencyZoom=100;
			}
			
			List<DataitemsInfo> protocolItems=new ArrayList<>();
			List<DataitemsInfo> protocolExtendedFieldList=new ArrayList<>();
			List<DataitemsInfo> displayCalItemList=new ArrayList<>();
			List<DataitemsInfo> displayInputItemList=new ArrayList<>();
			List<DataitemsInfo> dailyTotalCalItemMap=new ArrayList<>();
			
			List<DataitemsInfo> addInfoDataItemList=new ArrayList();
			List<String> addInfoNameList=new ArrayList<>();
			
			for(DataitemsInfo dataitemsInfo:dataItemList){
				if(dataitemsInfo.getColumnDataSource()==2){
					addInfoDataItemList.add(dataitemsInfo);
					String addInfoName=dataitemsInfo.getConfigItemName();
					addInfoNameList.add(addInfoName);
				}else if(dataitemsInfo.getColumnDataSource()==1){
					if(dataitemsInfo.getDataSource()==0){
						protocolItems.add(dataitemsInfo);
					}else if(dataitemsInfo.getDataSource()==1){
						displayCalItemList.add(dataitemsInfo);
					}else if(dataitemsInfo.getDataSource()==2){
						displayInputItemList.add(dataitemsInfo);
					}else if(dataitemsInfo.getDataSource()==5){
						protocolExtendedFieldList.add(dataitemsInfo);
					}
				}
			}
			
			
			List<?> addInfoList=new ArrayList<>();
			if(addInfoDataItemList.size()>0){
				String addInfoSql="select t.deviceid,t.itemname,t.itemvalue,t.itemunit "
						+ " from tbl_deviceaddinfo t "
						+ " where t.itemname in ("+StringManagerUtils.joinStringArr2(addInfoNameList, ",")+") ";
				addInfoList = this.findCallSql(addInfoSql);
			}
			
			
			String sql="select t.id,t.devicename,"//0~1
					+ "t.videourl1,t.videokeyid1,t.videourl2,t.videokeyid2,"//2~5
					+ "c1.name_"+language+" as devicetypename,"//6
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"//7
					+ "decode(t2.commstatus,null,0,t2.commstatus),decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,"//8~9
					+ "decode(t2.runstatus,null,2,t2.runstatus) as runStatusCalValue,"//10
					+ "t.calculateType,t.deviceType,"//11~12
					+ "to_char(t.commissioningdate,'yyyy-mm-dd') as commissioningDate,t.operatingdays,"//13~14
					+ "t.productiondata,"//15
					+ "t2.acqdata,t2.alarminfo";//16~17
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceid=t.id"
					+ " left outer join tbl_devicetypeinfo c1 on c1.id=t.devicetype ";
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+=" left outer join tbl_srpacqdata_latest t3 on t2.deviceid=t3.deviceid ";
			}
			sql+= " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNum(deviceType)){
				sql+= " and t.devicetype="+deviceType;
			}else{
				sql+= " and t.devicetype in ("+deviceType+")";
			}
			
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+= " and t.devicename='"+deviceName+"'";
			}
			
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+=" and t.calculateType=1 ";
				if(FESdiagramResultStatValue.equalsIgnoreCase(languageResourceMap.get("emptyMsg"))){
					sql+=" and (t3.resultcode=0 or t3.resultcode is null)";
				}else{
					sql+=" and t3.resultcode="+MemoryDataManagerTask.getWorkTypeByName(FESdiagramResultStatValue,language).getResultCode();
				}
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'"+languageResourceMap.get("offline")+"',null,'"+languageResourceMap.get("offline")+"',2,'"+languageResourceMap.get("goOnline")+"',decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"'))='"+runStatusStatValue+"'";
			}
			
			if(StringManagerUtils.isNotNull(numStatusStatValue)){
				if(StringManagerUtils.stringToInteger(numStatusStatValue)==0){
					sql+=" and decode(t2.alarmlevel1,null,0,t2.alarmlevel1) = 0 and decode(t2.alarmlevel2,null,0,t2.alarmlevel2) = 0 and decode(t2.alarmlevel3,null,0,t2.alarmlevel3) = 0";
				}else if(StringManagerUtils.stringToInteger(numStatusStatValue)==100){
					sql+=" and decode(t2.alarmlevel1,null,0,t2.alarmlevel1)>0";
				}else if(StringManagerUtils.stringToInteger(numStatusStatValue)==200){
					sql+=" and decode(t2.alarmlevel2,null,0,t2.alarmlevel2)>0";
				}else if(StringManagerUtils.stringToInteger(numStatusStatValue)==300){
					sql+=" and decode(t2.alarmlevel3,null,0,t2.alarmlevel3)>0";
				}
			}
			
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.devicename";
			
			int maxvalue=pager.getLimit()+pager.getStart();
			String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
			
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(finalSql);
			
			result_json.append("{ \"success\":true,");
			result_json.append("\"totalCount\":"+totals+",");
			result_json.append("\"totalRoot\":[");
			
			
			//计算数据
			boolean SRPOrPCPCalculateData=false;
			boolean haveSRPCalculateType=false;
			boolean havePCPCalculateType=false;
			for(int j=0;j<displayCalItemList.size();j++){
				String column=displayCalItemList.get(j).getCode();
				if(!StringManagerUtils.generalCalColumnFiter(column)){
					SRPOrPCPCalculateData=true;
					break;
				}
			}
			List<String> deviceIdList=new ArrayList<>();
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[0]+"";
				String calculateType=obj[11]+"";
				deviceIdList.add(deviceId);
				if("1".equalsIgnoreCase(calculateType)){
					haveSRPCalculateType=true;
				}else if("2".equalsIgnoreCase(calculateType)){
					havePCPCalculateType=true;
				}
			}
			
			Map<String,Map<String,String>> calDataQueryValueMap=new HashMap<>();
			if(displayCalItemList.size()>0 && deviceIdList.size()>0){
				String calDataSql="select t.deviceid,"
						+ " t.commtime,t.commtimeefficiency*"+timeEfficiencyZoom+" as commtimeefficiency,t.commrange,"
						+ " decode(t.runstatus,null,2,t.runstatus) as runStatusCalValue,t.runtime,t.runtimeefficiency*"+timeEfficiencyZoom+" as runtimeefficiency,t.runrange "
						+ " from tbl_acqdata_latest t,tbl_device t2"
						+ " where t.deviceid=t2.id"
						+ " and t2.orgid in ("+orgId+") ";
				if(StringManagerUtils.isNum(deviceType)){
					sql+= " and t2.devicetype="+deviceType;
				}else{
					sql+= " and t2.devicetype in ("+deviceType+")";
				}
				
				if(StringManagerUtils.isNotNull(deviceName)){
					sql+= " and t2.devicename='"+deviceName+"'";
				}
				
				List<?> calDataList = this.findCallSql(calDataSql);
				for(int i=0;i<calDataList.size();i++){
					Object[] obj=(Object[]) calDataList.get(i);
					String deviceId=obj[0]+"";
					Map<String,String> deviceCalDataMap=new HashMap<>();
					deviceCalDataMap.put("CommTime".toUpperCase(), obj[1]+"");
					deviceCalDataMap.put("commTimeEfficiency".toUpperCase(), obj[2]+"");
					deviceCalDataMap.put("commRange".toUpperCase(), StringManagerUtils.CLOBObjectToString(obj[3]));
					
					deviceCalDataMap.put("RunStatus".toUpperCase(), obj[4]+"");
					deviceCalDataMap.put("RunTime".toUpperCase(), obj[5]+"");
					deviceCalDataMap.put("RunTimeEfficiency".toUpperCase(), obj[6]+"");
					deviceCalDataMap.put("RunRange".toUpperCase(), StringManagerUtils.CLOBObjectToString(obj[7]));
					
					calDataQueryValueMap.put(deviceId, deviceCalDataMap);
				}
				if(SRPOrPCPCalculateData){
					if(haveSRPCalculateType){
						List<CalItem> calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
						List<CalItem> deviceCalItemList=new ArrayList<>();
						for(int j=0;j<displayCalItemList.size();j++){
							String column=displayCalItemList.get(j).getCode();
							CalItem calItem=MemoryDataManagerTask.getSingleCalItemByCode(column, calItemList);
							if(calItem!=null && !StringManagerUtils.generalCalColumnFiter(calItem.getCode())){
								deviceCalItemList.add(calItem);
							}
						}
						
						String srpCalDataSql="select t.deviceid";
						for(CalItem calItem:deviceCalItemList){
							String code=calItem.getCode();
							if("resultName".equalsIgnoreCase(code)){
								code="ResultCode";
							}
							srpCalDataSql+=",t."+code;
						}
						srpCalDataSql+= " from tbl_srpacqdata_latest t,tbl_device t2,tbl_tabmanager_device t3 "
						+ " where t.deviceid=t2.id and t2.calculatetype=t3.id and t3.calculatetype=1"
						+ " and t2.orgid in ("+orgId+") ";
						
						if(StringManagerUtils.isNum(deviceType)){
							srpCalDataSql+= " and t2.devicetype="+deviceType;
						}else{
							srpCalDataSql+= " and t2.devicetype in ("+deviceType+")";
						}
						
						if(StringManagerUtils.isNotNull(deviceName)){
							srpCalDataSql+= " and t2.devicename='"+deviceName+"'";
						}
						List<?> SRPCalDataList = this.findCallSql(srpCalDataSql);
						for(int i=0;i<SRPCalDataList.size();i++){
							Object[] obj=null;
							if(deviceCalItemList.size()>0){
								obj=(Object[]) SRPCalDataList.get(i);
							}else{
								obj=new Object[(int) SRPCalDataList.get(i)];
							
							}
							String deviceId=obj[0]+"";
							if(calDataQueryValueMap.containsKey(deviceId)){
								Map<String,String> deviceCalDataMap= calDataQueryValueMap.get(deviceId);
								for(int j=0;j<deviceCalItemList.size();j++){
									String code=deviceCalItemList.get(j).getCode();
									if("resultName".equalsIgnoreCase(code)){
										code="ResultCode";
									}
									deviceCalDataMap.put(code.toUpperCase(), obj[j+1]+"");
								}
							}
						}
					}
					
					if(havePCPCalculateType){
						List<CalItem> calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
						List<CalItem> deviceCalItemList=new ArrayList<>();
						for(int j=0;j<displayCalItemList.size();j++){
							String column=displayCalItemList.get(j).getCode();
							CalItem calItem=MemoryDataManagerTask.getSingleCalItemByCode(column, calItemList);
							if(calItem!=null && !StringManagerUtils.generalCalColumnFiter(calItem.getCode())){
								deviceCalItemList.add(calItem);
							}
						}
						
						
						String pcpCalDataSql="select t.deviceid";
						for(CalItem calItem:deviceCalItemList){
							pcpCalDataSql+=",t."+calItem.getCode();
						}
						pcpCalDataSql+= " from tbl_pcpacqdata_latest t,tbl_device t2,tbl_tabmanager_device t3 "
						+ " where t.deviceid=t2.id and t2.calculatetype=t3.id and t3.calculatetype=2"
						+ " and t2.orgid in ("+orgId+") ";
						
						if(StringManagerUtils.isNum(deviceType)){
							pcpCalDataSql+= " and t2.devicetype="+deviceType;
						}else{
							pcpCalDataSql+= " and t2.devicetype in ("+deviceType+")";
						}
						
						if(StringManagerUtils.isNotNull(deviceName)){
							pcpCalDataSql+= " and t2.devicename='"+deviceName+"'";
						}
						
						List<?> PCPCalDataList = this.findCallSql(pcpCalDataSql);
						for(int i=0;i<PCPCalDataList.size();i++){
							Object[] obj=null;
							if(deviceCalItemList.size()>0){
								obj=(Object[]) PCPCalDataList.get(i);
							}else{
								obj=new Object[(int) PCPCalDataList.get(i)];
							
							}
							
							String deviceId=obj[0]+"";
							if(calDataQueryValueMap.containsKey(deviceId)){
								Map<String,String> deviceCalDataMap= calDataQueryValueMap.get(deviceId);
								for(int j=0;j<deviceCalItemList.size();j++){
									deviceCalDataMap.put(deviceCalItemList.get(j).getCode().toUpperCase(), obj[j+1]+"");
								}
							}
						}
					}
				}
			}
			
//			//最新记录报警信息
//			long t1=System.nanoTime();
//			Map<String,Integer> alarmDataMap=new HashMap<>();
//			String alarmQuerySql="select t.deviceid,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.alarmtype,t.itemname,t.alarmvalue,t.alarmlevel,t.itemCode,t.bitindex "
//					+ " from tbl_alarminfo_hist t ,tbl_acqdata_latest t2,tbl_device t3"
//					+ " where t.deviceid=t2.deviceid and t.acqtime=t2.acqtime and t.deviceid=t3.id"
//					+ " and t3.orgid in ("+orgId+") ";
//			if(StringManagerUtils.isNum(deviceType)){
//				alarmQuerySql+= " and t3.devicetype="+deviceType;
//			}else{
//				alarmQuerySql+= " and t3.devicetype in ("+deviceType+")";
//			}
//			
//			if(StringManagerUtils.isNotNull(deviceName)){
//				alarmQuerySql+= " and t3.devicename='"+deviceName+"'";
//			};
//			alarmQuerySql+= " order by t3.id";
//			
//			List<?> alarmQueryList = this.findCallSql(alarmQuerySql);
//			long t2=System.nanoTime();
//			System.out.println("实时报警数据查询耗时:"+StringManagerUtils.getTimeDiff(t1, t2)+",alarmQuerySql:"+alarmQuerySql);
			
//			for(int i=0;i<alarmQueryList.size();i++){
//				Object[] obj=(Object[]) alarmQueryList.get(i);
//				
//				String deviceId=obj[0]+"";
//				String alarmTime=obj[1]+"";
//				int alarmType=StringManagerUtils.stringToInteger(obj[2]+"");
//				String alarmItemName=obj[3]+"";
//				String alarmValue=obj[4]+"";
//				int alarmLevel=StringManagerUtils.stringToInteger(obj[5]+"");
//				String alarmItemCode=obj[6]+"";
//				String bitIndex=obj[7]!=null?(obj[7]+""):"";
//				if(StringManagerUtils.isNotNull(bitIndex)){
//					alarmItemCode+="_"+bitIndex;
//				}
//				if(StringManagerUtils.isNotNull(alarmItemCode) && alarmLevel>0){
//					String key=deviceId+"_"+alarmItemCode+"_"+alarmTime+"_"+alarmType;
//					alarmDataMap.put(key.toUpperCase(), alarmLevel);
//				}
//			}
			
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[0]+"";
				String calculateType=obj[11]+"";
				String productionData=obj[15]+""; 
				
				
				String acqTime=obj[7]+"";
				int commStatus=StringManagerUtils.stringToInteger(obj[8]+"");
				String commStatusName=obj[9]+"";
				int runStatus=StringManagerUtils.stringToInteger(obj[10]+"");
				String runStatusName="";
				String acqData=StringManagerUtils.CLOBObjectToString(obj[16]);
				String deviceAlarmInfo=StringManagerUtils.CLOBObjectToString(obj[17]);
				if(!StringManagerUtils.isNotNull(deviceAlarmInfo)){
					deviceAlarmInfo="[]";
				}
				
				
				if(commStatus==1){
					if(runStatus==1){
						runStatusName=languageResourceMap.get("run");
					}else if(runStatus==0){
						runStatusName=languageResourceMap.get("stop");
					}else{
						runStatusName=languageResourceMap.get("emptyMsg");
					}
				}
				
				
				ModbusProtocolConfig.Protocol protocol=null;
				List<CalItem> calItemList=null;
				List<CalItem> inputItemList=null;
				AcqInstanceOwnItem acqInstanceOwnItem=null;
				AlarmInstanceOwnItem alarmInstanceOwnItem=null;
				String acqInstanceCode="";
				String alarmInstanceCode="";
				
				DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
				if(deviceInfo!=null){
					alarmInstanceCode=deviceInfo.getAlarmInstanceCode();
					acqInstanceCode=deviceInfo.getInstanceCode();
				}
				
				acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(acqInstanceCode);
				alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(alarmInstanceCode);
				
				if(acqInstanceOwnItem!=null){
					protocol=MemoryDataManagerTask.getProtocolByCode(acqInstanceOwnItem.getProtocolCode());
				}
				
				if(StringManagerUtils.stringToInteger(calculateType)==1){
					calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
					inputItemList=MemoryDataManagerTask.getSRPInputItem(language);
				}else if(StringManagerUtils.stringToInteger(calculateType)==2){
					calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
					inputItemList=MemoryDataManagerTask.getPCPInputItem(language);
				}else{
					calItemList=MemoryDataManagerTask.getAcqCalculateItem(language);
					inputItemList=new ArrayList<>();
				}
				
				if(StringManagerUtils.stringToInteger(calculateType)==1){
					calAndInputTableName="tbl_srpacqdata_latest";
				}else if(StringManagerUtils.stringToInteger(calculateType)==2){
					calAndInputTableName="tbl_pcpacqdata_latest";
				}
				
				
				StringBuffer alarmInfo = new StringBuffer();
				int commAlarmLevel=0,runAlarmLevel=0,resultAlarmLevel=0;
				if(alarmInstanceOwnItem!=null){
					for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
						if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
							commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
							runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}
					}
				}
				
				
				
				List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
				
				result_json.append("{\"id\":"+deviceId+",");
				result_json.append("\"deviceName\":\""+obj[1]+"\",");
				
				result_json.append("\"videoUrl1\":\""+obj[2]+"\",");
				result_json.append("\"videoKeyId1\":\""+obj[3]+"\",");
				result_json.append("\"videoUrl2\":\""+obj[4]+"\",");
				result_json.append("\"videoKeyId2\":\""+obj[5]+"\",");
				
				
				result_json.append("\"deviceTypeName\":\""+obj[6]+"\",");
				result_json.append("\"acqTime\":\""+acqTime+"\",");
				
				result_json.append("\"commStatus\":"+commStatus+",");
				result_json.append("\"commStatusName\":\""+commStatusName+"\",");
				result_json.append("\"commAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"runStatus\":"+runStatus+",");
				result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
				result_json.append("\"runAlarmLevel\":"+runAlarmLevel+",");
				
				result_json.append("\"calculateType\":"+calculateType+",");
				result_json.append("\"deviceType\":"+obj[12]+",");
				
				result_json.append("\"commissioningDate\":\""+obj[13]+"\",");
				result_json.append("\"operatingDays\":\""+obj[14]+"\",");
				
				type = new TypeToken<List<KeyValue>>() {}.getType();
				List<KeyValue> acqDataList=gson.fromJson(acqData, type);
				if(userInfo!=null && protocol!=null){
					//采集项
					
					
					//计算项
					
					//日汇总计算项
//					if(acqInstanceOwnItem!=null){
//						for(AcqInstanceOwnItem.AcqItem acqItem:acqInstanceOwnItem.getItemList()){
//							if(acqItem.getDailyTotalCalculate()==1 && StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), (acqItem.getItemCode()+"_total").toUpperCase(), false,0,2)){
//								for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
//									if(displayInstanceOwnItem.getItemList().get(k).getType()==1
//											&& displayInstanceOwnItem.getItemList().get(k).getRealtimeOverview()==1
//											&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
//											&& (acqItem.getItemCode()+"_total").equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode())){
//										displayInstanceOwnItem.getItemList().get(k).setItemSourceName(acqItem.getItemName());
//										displayInstanceOwnItem.getItemList().get(k).setItemSourceCode(acqItem.getItemCode());
//										dailyTotalCalItemMap.put(displayInstanceOwnItem.getItemList().get(k).getItemCode().toUpperCase(), displayInstanceOwnItem.getItemList().get(k));
//										break;
//									}
//								}
//								
//							}
//						}
//					}
				}
				
				if(protocol!=null && protocolItems.size()>0){
					if(acqDataList!=null){
						for(KeyValue keyValue:acqDataList){
							for(DataitemsInfo dataitemsInfo: protocolItems){
								String columnName=dataitemsInfo.getName_zh_CN();
								if("en".equalsIgnoreCase(language)){
									columnName=dataitemsInfo.getName_en();
								}else if("ru".equalsIgnoreCase(language)){
									columnName=dataitemsInfo.getName_ru();
								}
								String column=dataitemsInfo.getCode();
								ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, loadProtocolMappingColumnMap.get(column)!=null?loadProtocolMappingColumnMap.get(column).getName():columnName);
								if(item!=null && StringManagerUtils.isNotNull(column) && column.equalsIgnoreCase(keyValue.getKey())){
									String rawColumnName=columnName;
									String value=keyValue.getValue();
									String rawValue=value;
									String addr=item.getAddr()+"";
									String columnDataType=item.getIFDataType();
									String resolutionMode=item.getResolutionMode()+"";
									String bitIndex="";
									String unit=item.getUnit();
									int sort=dataitemsInfo.getSorts();
									if("int".equalsIgnoreCase(item.getIFDataType()) || "uint".equalsIgnoreCase(item.getIFDataType()) || item.getIFDataType().contains("int")
											||"float32".equalsIgnoreCase(item.getIFDataType())
											||"float64".equalsIgnoreCase(item.getIFDataType())){
										if(value.toUpperCase().contains("E")){
						                 	value=StringManagerUtils.scientificNotationToNormal(value);
						                 }
									}
									if(item.getResolutionMode()==1||item.getResolutionMode()==2){//如果是枚举量
										if(StringManagerUtils.isNotNull(value)&&item.getMeaning()!=null&&item.getMeaning().size()>0){
											for(int l=0;l<item.getMeaning().size();l++){
												if(StringManagerUtils.stringToFloat(value)==(item.getMeaning().get(l).getValue())){
													value=item.getMeaning().get(l).getMeaning();
													break;
												}
											}
										}
										ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
										protocolItemResolutionDataList.add(protocolItemResolutionData);
									}else if(item.getResolutionMode()==0){//如果是开关量
										boolean isMatch=false;
										if(item.getMeaning()!=null&&item.getMeaning().size()>0){
											String[] valueArr=value.split(",");
											for(int l=0;l<item.getMeaning().size();l++){
												columnName=item.getMeaning().get(l).getMeaning();
												sort=dataitemsInfo.getSorts();
												String status0=StringManagerUtils.isNotNull(item.getMeaning().get(l).getStatus0())?item.getMeaning().get(l).getStatus0():languageResourceMap.get("switchingCloseValue");
												String status1=StringManagerUtils.isNotNull(item.getMeaning().get(l).getStatus1())?item.getMeaning().get(l).getStatus1():languageResourceMap.get("switchingOpenValue");
												if(StringManagerUtils.isNotNull(value)){
													boolean match=false;
													for(int m=0;valueArr!=null&&m<valueArr.length;m++){
														if(m==item.getMeaning().get(l).getValue()){
															bitIndex=m+"";
															if("bool".equalsIgnoreCase(columnDataType) || "boolean".equalsIgnoreCase(columnDataType)){
																value=("true".equalsIgnoreCase(valueArr[m]) || "1".equalsIgnoreCase(valueArr[m]))?status1:status0;
																rawValue=("true".equalsIgnoreCase(valueArr[m]) || "1".equalsIgnoreCase(valueArr[m]))?"1":"0";
															}else{
																value=valueArr[m];
															}
															ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column+"_"+bitIndex,columnDataType,resolutionMode,bitIndex,unit,sort,0);
															protocolItemResolutionDataList.add(protocolItemResolutionData);
															match=true;
															break;
														}
													}
													if(!match){
														value="";
														rawValue="";
														bitIndex=item.getMeaning().get(l).getValue()+"";
														ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column+"_"+bitIndex,columnDataType,resolutionMode,item.getMeaning().get(l).getValue()+"",unit,sort,0);
														protocolItemResolutionDataList.add(protocolItemResolutionData);
													}
												}else{
													value="";
													rawValue="";
													bitIndex=item.getMeaning().get(l).getValue()+"";
													ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column+"_"+bitIndex,columnDataType,resolutionMode,item.getMeaning().get(l).getValue()+"",unit,sort,0);
													protocolItemResolutionDataList.add(protocolItemResolutionData);
												}
											}
										}else{
											ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
											protocolItemResolutionDataList.add(protocolItemResolutionData);
										}
									}else{//如果是数据量
										ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
										protocolItemResolutionDataList.add(protocolItemResolutionData);
									} 
									break;
								}
							}
						}
					}
				}
				
				if(protocol!=null && protocolExtendedFieldList.size()>0 && protocol.getExtendedFields()!=null){
					if(acqDataList!=null){
						for(int j=0;j<protocolExtendedFieldList.size();j++){
							String title=protocolExtendedFieldList.get(j).getName_zh_CN();
							if("en".equalsIgnoreCase(language)){
								title=protocolExtendedFieldList.get(j).getName_en();
							}else if("ru".equalsIgnoreCase(language)){
								title=protocolExtendedFieldList.get(j).getName_ru();
							}
							String extendedFieldCode=protocolExtendedFieldList.get(j).getCode();
							
							for(ModbusProtocolConfig.ExtendedField extendedField: protocol.getExtendedFields()){
								DataMapping dataMapping=protocolExtendedFieldColumnByTitleMap.get(extendedField.getTitle());
								if(dataMapping!=null && extendedFieldCode.equalsIgnoreCase(dataMapping.getMappingColumn())){
									String extendedFieldValue="";
									int sort=protocolExtendedFieldList.get(j).getSorts();
									String itemUnit=extendedField.getUnit();
									
									for(KeyValue keyValue:acqDataList){
										if(extendedFieldCode.equalsIgnoreCase(keyValue.getKey())){
											extendedFieldValue=keyValue.getValue();
											break;
										}
									}
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(
											extendedField.getTitle(),
											extendedField.getTitle(),
											extendedFieldValue,
											extendedFieldValue,
											"",
											extendedFieldCode,
											"",
											"7",
											"",
											extendedField.getUnit(),
											sort,
											5);
									protocolItemResolutionDataList.add(protocolItemResolutionData);
									break;
								}
							}
						}
					}
				}
				
				if(displayCalItemList.size()>0){
					List<DataitemsInfo> deviceCalItemList=new ArrayList<>();
					for(int j=0;j<displayCalItemList.size();j++){
						String column=displayCalItemList.get(j).getCode();
						CalItem calItem=MemoryDataManagerTask.getSingleCalItemByCode(column, calItemList);
						if(calItem!=null){
							deviceCalItemList.add(displayCalItemList.get(j));
						}
					}
					if(deviceCalItemList.size()>0){
						if(calDataQueryValueMap.containsKey(deviceId)){
							Map<String,String> deviceCalDataMap= calDataQueryValueMap.get(deviceId);
							
							for(int j=0;j<deviceCalItemList.size();j++){

								String columnName=deviceCalItemList.get(j).getName_zh_CN();
								if("en".equalsIgnoreCase(language)){
									columnName=deviceCalItemList.get(j).getName_en();
								}else if("ru".equalsIgnoreCase(language)){
									columnName=deviceCalItemList.get(j).getName_ru();
								}
								
								
								String rawColumnName=columnName;
								String column=deviceCalItemList.get(j).getCode();
								
								String value="";
								if("resultCode".equalsIgnoreCase(column)||"resultName".equalsIgnoreCase(column)){
									value=deviceCalDataMap.containsKey("resultCode".toUpperCase())?deviceCalDataMap.get("resultCode".toUpperCase()):"";
								}else if("runStatus".equalsIgnoreCase(column)||"RunStatusName".equalsIgnoreCase(column)){
									value=deviceCalDataMap.containsKey("runStatus".toUpperCase())?deviceCalDataMap.get("runStatus".toUpperCase()):"";
								}else{
									value=deviceCalDataMap.containsKey(column.toUpperCase())?deviceCalDataMap.get(column.toUpperCase()):"";
								}
								String rawValue=value;
								String addr="";
								
								String columnDataType="";
								String resolutionMode="5";
								String bitIndex="";
								String unit=deviceCalItemList.get(j).getDataUnit();
								int sort=deviceCalItemList.get(j).getSorts();
																	
								if("resultCode".equalsIgnoreCase(column)||"resultName".equalsIgnoreCase(column)){
									resolutionMode="4";
									WorkType workType=MemoryDataManagerTask.getWorkTypeByCode(value,language);
									if(workType!=null){
										value=workType.getResultName();
										if(alarmInstanceOwnItem!=null){
											for(AlarmInstanceOwnItem.AlarmItem alarmItem:alarmInstanceOwnItem.getItemList()){
												if(alarmItem.getAlarmLevel()>0 && alarmItem.getType()==4 && alarmItem.getItemCode().equalsIgnoreCase(workType.getResultCode()+"")){
													resultAlarmLevel=alarmItem.getAlarmLevel();
												}
											}
										}
									}
								}
								
								if( !("runStatus".equalsIgnoreCase(column)||"RunStatusName".equalsIgnoreCase(column)) ){
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,1);
									protocolItemResolutionDataList.add(protocolItemResolutionData);
								}
							}
						}
					}
				}
				
				
				if(StringManagerUtils.stringToInteger(calculateType)>0 && displayInputItemList.size()>0){
					if(StringManagerUtils.stringToInteger(calculateType)==1){
						type = new TypeToken<SRPCalculateRequestData>() {}.getType();
						SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
						for(int j=0;j<displayInputItemList.size();j++){
							String columnName=displayInputItemList.get(j).getName_zh_CN();
							if("en".equalsIgnoreCase(language)){
								columnName=displayInputItemList.get(j).getName_en();
							}else if("ru".equalsIgnoreCase(language)){
								columnName=displayInputItemList.get(j).getName_ru();
							}
							String rawColumnName=columnName;
							String value="";
							String rawValue=value;
							String addr="";
							String column=displayInputItemList.get(j).getCode();
							String columnDataType="";
							String resolutionMode="";
							String bitIndex="";
							String unit=displayInputItemList.get(j).getDataUnit();
							int sort=displayInputItemList.get(j).getSorts();
							
							
							if(srpProductionData!=null){
								if("CrudeOilDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
									value=srpProductionData.getFluidPVT().getCrudeOilDensity()+"";
								}else if("WaterDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
									value=srpProductionData.getFluidPVT().getWaterDensity()+"";
								}else if("NaturalGasRelativeDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
									value=srpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
								}else if("SaturationPressure".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
									value=srpProductionData.getFluidPVT().getSaturationPressure()+"";
								}else if("ReservoirDepth".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
									value=srpProductionData.getReservoir().getDepth()+"";
									if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
										columnName=languageResourceMap.get("reservoirDepth_cbm");
									}
								}else if("ReservoirTemperature".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
									value=srpProductionData.getReservoir().getTemperature()+"";
									if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
										columnName=languageResourceMap.get("reservoirTemperature_cbm");
									}
								}else if("TubingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
									value=srpProductionData.getProduction().getTubingPressure()+"";
									if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
										columnName=languageResourceMap.get("tubingPressure_cbm");
									}
								}else if("CasingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
									value=srpProductionData.getProduction().getCasingPressure()+"";
								}else if("WellHeadTemperature".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
									value=srpProductionData.getProduction().getWellHeadTemperature()+"";
								}else if("WaterCut".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
									value=srpProductionData.getProduction().getWaterCut()+"";
								}else if("ProductionGasOilRatio".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
									value=srpProductionData.getProduction().getProductionGasOilRatio()+"";
								}else if("ProducingfluidLevel".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
									value=srpProductionData.getProduction().getProducingfluidLevel()+"";
								}else if("PumpSettingDepth".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
									value=srpProductionData.getProduction().getPumpSettingDepth()+"";
								}else if("PumpBoreDiameter".equalsIgnoreCase(column) && srpProductionData.getPump()!=null ){
									value=srpProductionData.getPump().getPumpBoreDiameter()*1000+"";
								}else if("LevelCorrectValue".equalsIgnoreCase(column) && srpProductionData.getManualIntervention()!=null ){
									value=srpProductionData.getManualIntervention().getLevelCorrectValue()+"";
								}
							}
							rawValue=value;
							rawColumnName=columnName;
							ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,3);
							protocolItemResolutionDataList.add(protocolItemResolutionData);
						}
					}else if(StringManagerUtils.stringToInteger(calculateType)==2){
						type = new TypeToken<PCPCalculateRequestData>() {}.getType();
						PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
						for(int j=0;j<displayInputItemList.size();j++){
							String columnName=displayInputItemList.get(j).getName_zh_CN();
							if("en".equalsIgnoreCase(language)){
								columnName=displayInputItemList.get(j).getName_en();
							}else if("ru".equalsIgnoreCase(language)){
								columnName=displayInputItemList.get(j).getName_ru();
							}
							String rawColumnName=columnName;
							String value="";
							String rawValue=value;
							String addr="";
							String column=displayInputItemList.get(j).getCode();
							String columnDataType="";
							String resolutionMode="";
							String bitIndex="";
							String unit=displayInputItemList.get(j).getDataUnit();
							int sort=displayInputItemList.get(j).getSorts();
							
							if(pcpProductionData!=null){
								if("CrudeOilDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
									value=pcpProductionData.getFluidPVT().getCrudeOilDensity()+"";
								}else if("WaterDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
									value=pcpProductionData.getFluidPVT().getWaterDensity()+"";
								}else if("NaturalGasRelativeDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
									value=pcpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
								}else if("SaturationPressure".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
									value=pcpProductionData.getFluidPVT().getSaturationPressure()+"";
								}else if("ReservoirDepth".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
									value=pcpProductionData.getReservoir().getDepth()+"";
									if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
										columnName=languageResourceMap.get("reservoirDepth_cbm");
									}
								}else if("ReservoirTemperature".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
									value=pcpProductionData.getReservoir().getTemperature()+"";
									if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
										columnName=languageResourceMap.get("reservoirTemperature_cbm");
									}
								}else if("TubingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
									value=pcpProductionData.getProduction().getTubingPressure()+"";
									if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
										columnName=languageResourceMap.get("tubingPressure_cbm");
									}
								}else if("CasingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
									value=pcpProductionData.getProduction().getCasingPressure()+"";
								}else if("WellHeadTemperature".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
									value=pcpProductionData.getProduction().getWellHeadTemperature()+"";
								}else if("WaterCut".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
									value=pcpProductionData.getProduction().getWaterCut()+"";
								}else if("ProductionGasOilRatio".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
									value=pcpProductionData.getProduction().getProductionGasOilRatio()+"";
								}else if("ProducingfluidLevel".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
									value=pcpProductionData.getProduction().getProducingfluidLevel()+"";
								}else if("PumpSettingDepth".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
									value=pcpProductionData.getProduction().getPumpSettingDepth()+"";
								}
							}
							rawValue=value;
							rawColumnName=columnName;
							ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,3);
							protocolItemResolutionDataList.add(protocolItemResolutionData);
						}
					}
				}
				
//				if(dailyTotalCalItemMap.size()>0){
//					String dailyTotalDatasql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
//							+ "t.itemcolumn,t.itemname,"
//							+ "t.totalvalue,t.todayvalue "
//							+ " from tbl_dailytotalcalculate_latest t "
//							+ " where t.deviceId="+deviceId;
//					
//					List<?> dailyTotalDatasList = this.findCallSql(dailyTotalDatasql);
//					for(int j=0;j<dailyTotalDatasList.size();j++){
//						Object[] dailyTotalDataObj=(Object[]) dailyTotalDatasList.get(j);
//						if(dailyTotalCalItemMap.containsKey( (dailyTotalDataObj[1]+"").toUpperCase() )  && acqTime.equalsIgnoreCase(dailyTotalDataObj[0]+"")  ){
//							DisplayInstanceOwnItem.DisplayItem displayItem=dailyTotalCalItemMap.get( (dailyTotalDataObj[1]+"").toUpperCase() );
//							if(displayItem!=null){
//								String unit="";
//								ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, displayItem.getItemSourceName());
//								if(item!=null){
//									unit=item.getUnit();
//								}
//								ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(
//										dailyTotalDataObj[2]+"",
//										dailyTotalDataObj[2]+"",
//										dailyTotalDataObj[4]+"",
//										dailyTotalDataObj[4]+"",
//										"",
//										dailyTotalDataObj[1]+"",
//										"",
//										"",
//										"",
//										unit,
//										displayItem.getRealtimeSort(),
//										1);
//								protocolItemResolutionDataList.add(protocolItemResolutionData);
//							}
//						}
//					}
//				
//				}
				
				//附加信息
				if(addInfoDataItemList.size()>0){
					for(int j=0;j<addInfoList.size();j++){
						Object[] addInfoObj=(Object[]) addInfoList.get(j);
						
						String itemDeviceId=addInfoObj[0]+"";
						String itemName=addInfoObj[1]+"";
						String itemValue=addInfoObj[2]+"";
						String itemUnit=addInfoObj[3]+"";
						
						if(deviceId.equalsIgnoreCase(itemDeviceId)){
							String addInfoColumn="";
							
							for(int k=0;k<addInfoDataItemList.size();k++){
								String addInfoName=addInfoDataItemList.get(k).getConfigItemName();
								if(itemName.equals(addInfoName)){
									addInfoColumn="addInfoColumn"+(k+1);
									break;
								}
							}
							result_json.append("\""+addInfoColumn+"\":\""+itemValue+"\",");
						}
					}
				}
				
				for(int j=0;j<protocolItemResolutionDataList.size();j++){
					String rawValue=protocolItemResolutionDataList.get(j).getRawValue();
					String value=protocolItemResolutionDataList.get(j).getValue();
					String column=protocolItemResolutionDataList.get(j).getColumn();
					result_json.append("\""+column+"\":\""+value+"\",");
				}
				
				//报警信息
				alarmInfo.append("[");
				type = new TypeToken<List<KeyValue>>() {}.getType();
				List<KeyValue> alarmInfoList=gson.fromJson(deviceAlarmInfo, type);
				if(alarmInfoList!=null){
					for(KeyValue keyValue:alarmInfoList){
						alarmInfo.append("{\"item\":\""+keyValue.getKey()+"\","
								+" \"alarmLevel\":"+keyValue.getValue()+"},");
					}
				}
				
//				for(int j=0;j<protocolItemResolutionDataList.size();j++){
//					String rawValue=protocolItemResolutionDataList.get(j).getRawValue();
//					String value=protocolItemResolutionDataList.get(j).getValue();
//					String column=protocolItemResolutionDataList.get(j).getColumn();
//					result_json.append("\""+column+"\":\""+value+"\",");
//					//报警判断
//					if(alarmInstanceOwnItem!=null && alarmDataMap.size()>0){
//						String key=(deviceId+"_"
//								+protocolItemResolutionDataList.get(j).getColumn()+"_"
//								+acqTime+"_"
//								+protocolItemResolutionDataList.get(j).getResolutionMode()).toUpperCase();
//						if(alarmDataMap.containsKey(key) && alarmDataMap.get(key)>0){
//							alarmInfo.append("{\"item\":\""+protocolItemResolutionDataList.get(j).getColumn()+"\","
//									+" \"alarmLevel\":"+alarmDataMap.get(key)+"},");
//						}
//					}
//				}
				
				if(alarmInfo.toString().endsWith(",")){
					alarmInfo.deleteCharAt(alarmInfo.length() - 1);
				}
				alarmInfo.append("]");
				
				result_json.append("\"resultAlarmLevel\":\""+resultAlarmLevel+"\",");
				result_json.append("\"alarmInfo\":"+alarmInfo+"");
				result_json.append("},");
			}
			
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("],");
			
			result_json.append("\"columns\":"+columns+",");
			result_json.append("\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle)+"}");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public boolean exportDeviceOverviewData(User user,HttpServletResponse response,String fileName,String sheetName,String head,String field,
			String orgId,String deviceName,
			String deviceType,String dictDeviceType,
			String FESdiagramResultStatValue,String commStatusStatValue,String runStatusStatValue,String numStatusStatValue,String deviceTypeStatValue,Page pager,
			int userNo,
			String language){
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try{
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
			Map<String,DataMapping> protocolExtendedFieldColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(1);
			Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
			UserInfo userInfo=MemoryDataManagerTask.getUserInfoByNo(userNo+"");
			String tableName="tbl_acqdata_latest";
			String deviceTableName="viw_device";
			String calAndInputTableName="tbl_srpacqdata_latest";
			String ddicCode="realTimeMonitoring_Overview";
			DataDictionary ddic = null;
			
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicCode,dictDeviceType,language);
			List<DataitemsInfo> dataItemList=ddic.getDataItemList();
			
			String columns = ddic.getTableHeader();
			
			
			
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			int timeEfficiencyZoom=1;
			if(timeEfficiencyUnitType==2){
				timeEfficiencyZoom=100;
			}
			
			List<DataitemsInfo> protocolItems=new ArrayList<>();
			List<DataitemsInfo> protocolExtendedFieldList=new ArrayList<>();
			List<DataitemsInfo> displayCalItemList=new ArrayList<>();
			List<DataitemsInfo> displayInputItemList=new ArrayList<>();
			List<DataitemsInfo> dailyTotalCalItemMap=new ArrayList<>();
			
			List<DataitemsInfo> addInfoDataItemList=new ArrayList();
			List<String> addInfoNameList=new ArrayList<>();
			
			for(DataitemsInfo dataitemsInfo:dataItemList){
				if(dataitemsInfo.getColumnDataSource()==2){
					addInfoDataItemList.add(dataitemsInfo);
					String addInfoName=dataitemsInfo.getConfigItemName();
					addInfoNameList.add(addInfoName);
				}else if(dataitemsInfo.getColumnDataSource()==1){
					if(dataitemsInfo.getDataSource()==0){
						protocolItems.add(dataitemsInfo);
					}else if(dataitemsInfo.getDataSource()==1){
						displayCalItemList.add(dataitemsInfo);
					}else if(dataitemsInfo.getDataSource()==2){
						displayInputItemList.add(dataitemsInfo);
					}else if(dataitemsInfo.getDataSource()==5){
						protocolExtendedFieldList.add(dataitemsInfo);
					}
				}
			}
			
			List<?> addInfoList=new ArrayList<>();
			if(addInfoDataItemList.size()>0){
				String addInfoSql="select t.deviceid,t.itemname,t.itemvalue,t.itemunit "
						+ " from tbl_deviceaddinfo t "
						+ " where t.itemname in ("+StringManagerUtils.joinStringArr2(addInfoNameList, ",")+") ";
				addInfoList = this.findCallSql(addInfoSql);
			}
			
			String sql="select t.id,t.devicename,"//0~1
					+ "t.videourl1,t.videokeyid1,t.videourl2,t.videokeyid2,"//2~5
					+ "c1.name_"+language+" as devicetypename,"//6
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"//7
					+ "decode(t2.commstatus,null,0,t2.commstatus),decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,"//8~9
					+ "decode(t2.runstatus,null,2,t2.runstatus) as runStatusCalValue,"//10
					+ "t.calculateType,t.deviceType,"//11~12
					+ "to_char(t.commissioningdate,'yyyy-mm-dd') as commissioningDate,t.operatingdays,"//13~14
					+ "t.productiondata,"//15
					+ "t2.acqdata";//16
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceid=t.id"
					+ " left outer join tbl_devicetypeinfo c1 on c1.id=t.devicetype ";
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+=" left outer join tbl_srpacqdata_latest t3 on t2.deviceid=t3.deviceid ";
			}
			sql+= " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNum(deviceType)){
				sql+= " and t.devicetype="+deviceType;
			}else{
				sql+= " and t.devicetype in ("+deviceType+")";
			}
			
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+= " and t.devicename='"+deviceName+"'";
			}
			
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+=" and t.calculateType=1 ";
				if(FESdiagramResultStatValue.equalsIgnoreCase(languageResourceMap.get("emptyMsg"))){
					sql+=" and (t3.resultcode=0 or t3.resultcode is null)";
				}else{
					sql+=" and t3.resultcode="+MemoryDataManagerTask.getWorkTypeByName(FESdiagramResultStatValue,language).getResultCode();
				}
				
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'"+languageResourceMap.get("offline")+"',null,'"+languageResourceMap.get("offline")+"',2,'"+languageResourceMap.get("goOnline")+"',decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"'))='"+runStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(numStatusStatValue)){
				if(StringManagerUtils.stringToInteger(numStatusStatValue)==0){
					sql+=" and decode(t2.alarmlevel1,null,0,t2.alarmlevel1) = 0 and decode(t2.alarmlevel2,null,0,t2.alarmlevel2) = 0 and decode(t2.alarmlevel3,null,0,t2.alarmlevel3) = 0";
				}else if(StringManagerUtils.stringToInteger(numStatusStatValue)==100){
					sql+=" and decode(t2.alarmlevel1,null,0,t2.alarmlevel1)>0";
				}else if(StringManagerUtils.stringToInteger(numStatusStatValue)==200){
					sql+=" and decode(t2.alarmlevel2,null,0,t2.alarmlevel2)>0";
				}else if(StringManagerUtils.stringToInteger(numStatusStatValue)==300){
					sql+=" and decode(t2.alarmlevel3,null,0,t2.alarmlevel3)>0";
				}
			}
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.devicename";
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			
			
			List<String> headList = new ArrayList<>(Arrays.asList(head.split(",")));
			List<String> columnList=new ArrayList<>(Arrays.asList(field.split(",")));
						
			List<?> list = this.findCallSql(finalSql);
			
			//计算数据
			boolean SRPOrPCPCalculateData=false;
			boolean haveSRPCalculateType=false;
			boolean havePCPCalculateType=false;
			for(int j=0;j<displayCalItemList.size();j++){
				String column=displayCalItemList.get(j).getCode();
				if(!StringManagerUtils.generalCalColumnFiter(column)){
					SRPOrPCPCalculateData=true;
					break;
				}
			}
			List<String> deviceIdList=new ArrayList<>();
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[0]+"";
				String calculateType=obj[11]+"";
				deviceIdList.add(deviceId);
				if("1".equalsIgnoreCase(calculateType)){
					haveSRPCalculateType=true;
				}else if("2".equalsIgnoreCase(calculateType)){
					havePCPCalculateType=true;
				}
			}
			
			
			
			Map<String,Map<String,String>> calDataQueryValueMap=new HashMap<>();
			if(displayCalItemList.size()>0 && deviceIdList.size()>0){
				String calDataSql="select t.deviceid,"
						+ " t.commtime,t.commtimeefficiency*"+timeEfficiencyZoom+" as commtimeefficiency,t.commrange,"
						+ " decode(t.runstatus,null,2,t.runstatus) as runStatusCalValue,t.runtime,t.runtimeefficiency*"+timeEfficiencyZoom+" as runtimeefficiency,t.runrange "
						+ " from tbl_acqdata_latest t,tbl_device t2"
						+ " where t.deviceid=t2.id"
						+ " and t2.orgid in ("+orgId+") ";
				if(StringManagerUtils.isNum(deviceType)){
					sql+= " and t2.devicetype="+deviceType;
				}else{
					sql+= " and t2.devicetype in ("+deviceType+")";
				}
				
				if(StringManagerUtils.isNotNull(deviceName)){
					sql+= " and t2.devicename='"+deviceName+"'";
				}
				
				List<?> calDataList = this.findCallSql(calDataSql);
				for(int i=0;i<calDataList.size();i++){
					Object[] obj=(Object[]) calDataList.get(i);
					String deviceId=obj[0]+"";
					Map<String,String> deviceCalDataMap=new HashMap<>();
					deviceCalDataMap.put("CommTime".toUpperCase(), obj[1]+"");
					deviceCalDataMap.put("commTimeEfficiency".toUpperCase(), obj[2]+"");
					deviceCalDataMap.put("commRange".toUpperCase(), StringManagerUtils.CLOBObjectToString(obj[3]));
					
					deviceCalDataMap.put("RunStatus".toUpperCase(), obj[4]+"");
					deviceCalDataMap.put("RunTime".toUpperCase(), obj[5]+"");
					deviceCalDataMap.put("RunTimeEfficiency".toUpperCase(), obj[6]+"");
					deviceCalDataMap.put("RunRange".toUpperCase(), StringManagerUtils.CLOBObjectToString(obj[7]));
					
					calDataQueryValueMap.put(deviceId, deviceCalDataMap);
				}
				if(SRPOrPCPCalculateData){
					if(haveSRPCalculateType){
						List<CalItem> calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
						List<CalItem> deviceCalItemList=new ArrayList<>();
						for(int j=0;j<displayCalItemList.size();j++){
							String column=displayCalItemList.get(j).getCode();
							CalItem calItem=MemoryDataManagerTask.getSingleCalItemByCode(column, calItemList);
							if(calItem!=null && !StringManagerUtils.generalCalColumnFiter(calItem.getCode())){
								deviceCalItemList.add(calItem);
							}
						}
						
						String srpCalDataSql="select t.deviceid";
						for(CalItem calItem:deviceCalItemList){
							String code=calItem.getCode();
							if("resultName".equalsIgnoreCase(code)){
								code="ResultCode";
							}
							srpCalDataSql+=",t."+code;
						}
						srpCalDataSql+= " from tbl_srpacqdata_latest t,tbl_device t2,tbl_tabmanager_device t3 "
						+ " where t.deviceid=t2.id and t2.calculatetype=t3.id and t3.calculatetype=1"
						+ " and t2.orgid in ("+orgId+") ";
						
						if(StringManagerUtils.isNum(deviceType)){
							srpCalDataSql+= " and t2.devicetype="+deviceType;
						}else{
							srpCalDataSql+= " and t2.devicetype in ("+deviceType+")";
						}
						
						if(StringManagerUtils.isNotNull(deviceName)){
							srpCalDataSql+= " and t2.devicename='"+deviceName+"'";
						}
						List<?> SRPCalDataList = this.findCallSql(srpCalDataSql);
						for(int i=0;i<SRPCalDataList.size();i++){
							Object[] obj=(Object[]) SRPCalDataList.get(i);
							String deviceId=obj[0]+"";
							if(calDataQueryValueMap.containsKey(deviceId)){
								Map<String,String> deviceCalDataMap= calDataQueryValueMap.get(deviceId);
								for(int j=0;j<deviceCalItemList.size();j++){
									String code=deviceCalItemList.get(j).getCode();
									if("resultName".equalsIgnoreCase(code)){
										code="ResultCode";
									}
									deviceCalDataMap.put(code.toUpperCase(), obj[j+1]+"");
								}
							}
						}
					}
					
					if(havePCPCalculateType){
						List<CalItem> calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
						List<CalItem> deviceCalItemList=new ArrayList<>();
						for(int j=0;j<displayCalItemList.size();j++){
							String column=displayCalItemList.get(j).getCode();
							CalItem calItem=MemoryDataManagerTask.getSingleCalItemByCode(column, calItemList);
							if(calItem!=null && !StringManagerUtils.generalCalColumnFiter(calItem.getCode())){
								deviceCalItemList.add(calItem);
							}
						}
						
						
						String pcpCalDataSql="select t.deviceid";
						for(CalItem calItem:deviceCalItemList){
							pcpCalDataSql+=",t."+calItem.getCode();
						}
						pcpCalDataSql+= " from tbl_pcpacqdata_latest t,tbl_device t2,tbl_tabmanager_device t3 "
						+ " where t.deviceid=t2.id and t2.calculatetype=t3.id and t3.calculatetype=2"
						+ " and t2.orgid in ("+orgId+") ";
						
						if(StringManagerUtils.isNum(deviceType)){
							pcpCalDataSql+= " and t2.devicetype="+deviceType;
						}else{
							pcpCalDataSql+= " and t2.devicetype in ("+deviceType+")";
						}
						
						if(StringManagerUtils.isNotNull(deviceName)){
							pcpCalDataSql+= " and t2.devicename='"+deviceName+"'";
						}
						
						List<?> PCPCalDataList = this.findCallSql(pcpCalDataSql);
						for(int i=0;i<PCPCalDataList.size();i++){
							Object[] obj=(Object[]) PCPCalDataList.get(i);
							String deviceId=obj[0]+"";
							if(calDataQueryValueMap.containsKey(deviceId)){
								Map<String,String> deviceCalDataMap= calDataQueryValueMap.get(deviceId);
								for(int j=0;j<deviceCalItemList.size();j++){
									deviceCalDataMap.put(deviceCalItemList.get(j).getCode().toUpperCase(), obj[j+1]+"");
								}
							}
						}
					}
				}
			}
			
			
			
			List<Object> record=null;
			JSONObject jsonObject=null;
			Object[] obj=null;
			List<String> dataList=new ArrayList<>();
			for(int i=0;i<list.size();i++){
				StringBuffer result_json = new StringBuffer();
				obj=(Object[]) list.get(i);
				
				String deviceId=obj[0]+"";
				String calculateType=obj[11]+"";
				String productionData=obj[15]+""; 
				
				
				String acqTime=obj[7]+"";
				int commStatus=StringManagerUtils.stringToInteger(obj[8]+"");
				String commStatusName=obj[9]+"";
				int runStatus=StringManagerUtils.stringToInteger(obj[10]+"");
				String runStatusName="";
				String acqData=StringManagerUtils.CLOBObjectToString(obj[16]);
				
				if(commStatus==1){
					if(runStatus==1){
						runStatusName=languageResourceMap.get("run");
					}else if(runStatus==0){
						runStatusName=languageResourceMap.get("stop");
					}else{
						runStatusName=languageResourceMap.get("emptyMsg");
					}
				}
				
				
				ModbusProtocolConfig.Protocol protocol=null;
				List<CalItem> calItemList=null;
				List<CalItem> inputItemList=null;
				AcqInstanceOwnItem acqInstanceOwnItem=null;
				AlarmInstanceOwnItem alarmInstanceOwnItem=null;
				String acqInstanceCode="";
				String alarmInstanceCode="";
				
				DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
				if(deviceInfo!=null){
					alarmInstanceCode=deviceInfo.getAlarmInstanceCode();
					acqInstanceCode=deviceInfo.getInstanceCode();
				}
				
				acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(acqInstanceCode);
				alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(alarmInstanceCode);
				
				if(acqInstanceOwnItem!=null){
					protocol=MemoryDataManagerTask.getProtocolByCode(acqInstanceOwnItem.getProtocolCode());
				}
				
				if(StringManagerUtils.stringToInteger(calculateType)==1){
					calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
					inputItemList=MemoryDataManagerTask.getSRPInputItem(language);
				}else if(StringManagerUtils.stringToInteger(calculateType)==2){
					calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
					inputItemList=MemoryDataManagerTask.getPCPInputItem(language);
				}else{
					calItemList=MemoryDataManagerTask.getAcqCalculateItem(language);
					inputItemList=new ArrayList<>();
				}
				
				if(StringManagerUtils.stringToInteger(calculateType)==1){
					calAndInputTableName="tbl_srpacqdata_latest";
				}else if(StringManagerUtils.stringToInteger(calculateType)==2){
					calAndInputTableName="tbl_pcpacqdata_latest";
				}
				
				
				
				
				List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
				
				result_json.append("{\"id\":"+(i+1)+",");
				result_json.append("\"deviceName\":\""+obj[1]+"\",");
				
				result_json.append("\"videoUrl1\":\""+obj[2]+"\",");
				result_json.append("\"videoKeyId1\":\""+obj[3]+"\",");
				result_json.append("\"videoUrl2\":\""+obj[4]+"\",");
				result_json.append("\"videoKeyId2\":\""+obj[5]+"\",");
				
				
				result_json.append("\"deviceTypeName\":\""+obj[6]+"\",");
				result_json.append("\"acqTime\":\""+acqTime+"\",");
				
				result_json.append("\"commStatus\":"+commStatus+",");
				result_json.append("\"commStatusName\":\""+commStatusName+"\",");
				result_json.append("\"runStatus\":"+runStatus+",");
				result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
				
				result_json.append("\"calculateType\":"+calculateType+",");
				result_json.append("\"deviceType\":"+obj[12]+",");
				
				result_json.append("\"commissioningDate\":\""+obj[13]+"\",");
				result_json.append("\"operatingDays\":\""+obj[14]+"\",");
				
				type = new TypeToken<List<KeyValue>>() {}.getType();
				List<KeyValue> acqDataList=gson.fromJson(acqData, type);
				if(protocol!=null && protocolItems.size()>0){
					if(acqDataList!=null){
						for(KeyValue keyValue:acqDataList){
							for(DataitemsInfo dataitemsInfo: protocolItems){
								String columnName=dataitemsInfo.getName_zh_CN();
								if("en".equalsIgnoreCase(language)){
									columnName=dataitemsInfo.getName_en();
								}else if("ru".equalsIgnoreCase(language)){
									columnName=dataitemsInfo.getName_ru();
								}
								String column=dataitemsInfo.getCode();
								ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, loadProtocolMappingColumnMap.get(column)!=null?loadProtocolMappingColumnMap.get(column).getName():columnName);
								
								if(item!=null && StringManagerUtils.isNotNull(column) && column.equalsIgnoreCase(keyValue.getKey())){
									String rawColumnName=columnName;
									String value=keyValue.getValue();
									String rawValue=value;
									String addr=item.getAddr()+"";
									String columnDataType=item.getIFDataType();
									String resolutionMode=item.getResolutionMode()+"";
									String bitIndex="";
									String unit=item.getUnit();
									int sort=dataitemsInfo.getSorts();
									if("int".equalsIgnoreCase(item.getIFDataType()) || "uint".equalsIgnoreCase(item.getIFDataType()) || item.getIFDataType().contains("int")
											||"float32".equalsIgnoreCase(item.getIFDataType())
											||"float64".equalsIgnoreCase(item.getIFDataType())){
										if(value.toUpperCase().contains("E")){
						                 	value=StringManagerUtils.scientificNotationToNormal(value);
						                 }
									}
									if(item.getResolutionMode()==1||item.getResolutionMode()==2){//如果是枚举量
										if(StringManagerUtils.isNotNull(value)&&item.getMeaning()!=null&&item.getMeaning().size()>0){
											for(int l=0;l<item.getMeaning().size();l++){
												if(StringManagerUtils.stringToFloat(value)==(item.getMeaning().get(l).getValue())){
													value=item.getMeaning().get(l).getMeaning();
													break;
												}
											}
										}
										ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
										protocolItemResolutionDataList.add(protocolItemResolutionData);
									}else if(item.getResolutionMode()==0){//如果是开关量
										boolean isMatch=false;
										if(item.getMeaning()!=null&&item.getMeaning().size()>0){
											String[] valueArr=value.split(",");
											for(int l=0;l<item.getMeaning().size();l++){
												columnName=item.getMeaning().get(l).getMeaning();
												sort=dataitemsInfo.getSorts();
												if(StringManagerUtils.isNotNull(value)){
													boolean match=false;
													for(int m=0;valueArr!=null&&m<valueArr.length;m++){
														if(m==item.getMeaning().get(l).getValue()){
															bitIndex=m+"";
															if("bool".equalsIgnoreCase(columnDataType) || "boolean".equalsIgnoreCase(columnDataType)){
																value=("true".equalsIgnoreCase(valueArr[m]) || "1".equalsIgnoreCase(valueArr[m]))?"开":"关";
																rawValue=("true".equalsIgnoreCase(valueArr[m]) || "1".equalsIgnoreCase(valueArr[m]))?"1":"0";
															}else{
																value=valueArr[m];
															}
															ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column+"_"+bitIndex,columnDataType,resolutionMode,bitIndex,unit,sort,0);
															protocolItemResolutionDataList.add(protocolItemResolutionData);
															match=true;
															break;
														}
													}
													if(!match){
														value="";
														rawValue="";
														bitIndex=item.getMeaning().get(l).getValue()+"";
														ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column+"_"+bitIndex,columnDataType,resolutionMode,item.getMeaning().get(l).getValue()+"",unit,sort,0);
														protocolItemResolutionDataList.add(protocolItemResolutionData);
													}
												}else{
													value="";
													rawValue="";
													bitIndex=item.getMeaning().get(l).getValue()+"";
													ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column+"_"+bitIndex,columnDataType,resolutionMode,item.getMeaning().get(l).getValue()+"",unit,sort,0);
													protocolItemResolutionDataList.add(protocolItemResolutionData);
												}
											}
										}else{
											ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
											protocolItemResolutionDataList.add(protocolItemResolutionData);
										}
									}else{//如果是数据量
										ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
										protocolItemResolutionDataList.add(protocolItemResolutionData);
									} 
									break;
								}
							}
						}
					}
				}
				
				if(protocol!=null && protocolExtendedFieldList.size()>0 && protocol.getExtendedFields()!=null){
					if(acqDataList!=null){
						for(int j=0;j<protocolExtendedFieldList.size();j++){
							String title=protocolExtendedFieldList.get(j).getName_zh_CN();
							if("en".equalsIgnoreCase(language)){
								title=protocolExtendedFieldList.get(j).getName_en();
							}else if("ru".equalsIgnoreCase(language)){
								title=protocolExtendedFieldList.get(j).getName_ru();
							}
							String extendedFieldCode=protocolExtendedFieldList.get(j).getCode();
							
							for(ModbusProtocolConfig.ExtendedField extendedField: protocol.getExtendedFields()){
								DataMapping dataMapping=protocolExtendedFieldColumnByTitleMap.get(extendedField.getTitle());
								if(dataMapping!=null && extendedFieldCode.equalsIgnoreCase(dataMapping.getMappingColumn())){
									String extendedFieldValue="";
									int sort=protocolExtendedFieldList.get(j).getSorts();
									String itemUnit=extendedField.getUnit();
									
									for(KeyValue keyValue:acqDataList){
										if(extendedFieldCode.equalsIgnoreCase(keyValue.getKey())){
											extendedFieldValue=keyValue.getValue();
											break;
										}
									}
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(
											extendedField.getTitle(),
											extendedField.getTitle(),
											extendedFieldValue,
											extendedFieldValue,
											"",
											extendedFieldCode,
											"",
											"",
											"",
											extendedField.getUnit(),
											sort,
											5);
									protocolItemResolutionDataList.add(protocolItemResolutionData);
									break;
								}
							}
						}
					}
				}
				
				if(displayCalItemList.size()>0){
					List<DataitemsInfo> deviceCalItemList=new ArrayList<>();
					for(int j=0;j<displayCalItemList.size();j++){
						String column=displayCalItemList.get(j).getCode();
						CalItem calItem=MemoryDataManagerTask.getSingleCalItemByCode(column, calItemList);
						if(calItem!=null){
							deviceCalItemList.add(displayCalItemList.get(j));
						}
					}
					if(deviceCalItemList.size()>0){
						if(calDataQueryValueMap.containsKey(deviceId)){
							Map<String,String> deviceCalDataMap= calDataQueryValueMap.get(deviceId);
							
							for(int j=0;j<deviceCalItemList.size();j++){

								String columnName=deviceCalItemList.get(j).getName_zh_CN();
								if("en".equalsIgnoreCase(language)){
									columnName=deviceCalItemList.get(j).getName_en();
								}else if("ru".equalsIgnoreCase(language)){
									columnName=deviceCalItemList.get(j).getName_ru();
								}
								
								
								String rawColumnName=columnName;
								String column=deviceCalItemList.get(j).getCode();
								
								String value="";
								if("resultCode".equalsIgnoreCase(column)||"resultName".equalsIgnoreCase(column)){
									value=deviceCalDataMap.containsKey("resultCode".toUpperCase())?deviceCalDataMap.get("resultCode".toUpperCase()):"";
								}else if("runStatus".equalsIgnoreCase(column)||"RunStatusName".equalsIgnoreCase(column)){
									value=deviceCalDataMap.containsKey("runStatus".toUpperCase())?deviceCalDataMap.get("runStatus".toUpperCase()):"";
								}else{
									value=deviceCalDataMap.containsKey(column.toUpperCase())?deviceCalDataMap.get(column.toUpperCase()):"";
								}
								String rawValue=value;
								String addr="";
								
								String columnDataType="";
								String resolutionMode="";
								String bitIndex="";
								String unit=deviceCalItemList.get(j).getDataUnit();
								int sort=deviceCalItemList.get(j).getSorts();
																	
								if("resultCode".equalsIgnoreCase(column)||"resultName".equalsIgnoreCase(column)){
									WorkType workType=MemoryDataManagerTask.getWorkTypeByCode(value,language);
									if(workType!=null){
										value=workType.getResultName();
									}
								}
								
								if( !("runStatus".equalsIgnoreCase(column)||"RunStatusName".equalsIgnoreCase(column)) ){
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,1);
									protocolItemResolutionDataList.add(protocolItemResolutionData);
								}
							}
						}
					}
				}
				
				
				if(StringManagerUtils.stringToInteger(calculateType)>0 && displayInputItemList.size()>0){
					if(StringManagerUtils.stringToInteger(calculateType)==1){
						type = new TypeToken<SRPCalculateRequestData>() {}.getType();
						SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
						for(int j=0;j<displayInputItemList.size();j++){
							String columnName=displayInputItemList.get(j).getName_zh_CN();
							if("en".equalsIgnoreCase(language)){
								columnName=displayInputItemList.get(j).getName_en();
							}else if("ru".equalsIgnoreCase(language)){
								columnName=displayInputItemList.get(j).getName_ru();
							}
							String rawColumnName=columnName;
							String value="";
							String rawValue=value;
							String addr="";
							String column=displayInputItemList.get(j).getCode();
							String columnDataType="";
							String resolutionMode="";
							String bitIndex="";
							String unit=displayInputItemList.get(j).getDataUnit();
							int sort=displayInputItemList.get(j).getSorts();
							
							
							if(srpProductionData!=null){
								if("CrudeOilDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
									value=srpProductionData.getFluidPVT().getCrudeOilDensity()+"";
								}else if("WaterDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
									value=srpProductionData.getFluidPVT().getWaterDensity()+"";
								}else if("NaturalGasRelativeDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
									value=srpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
								}else if("SaturationPressure".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
									value=srpProductionData.getFluidPVT().getSaturationPressure()+"";
								}else if("ReservoirDepth".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
									value=srpProductionData.getReservoir().getDepth()+"";
									if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
										columnName=languageResourceMap.get("reservoirDepth_cbm");
									}
								}else if("ReservoirTemperature".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
									value=srpProductionData.getReservoir().getTemperature()+"";
									if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
										columnName=languageResourceMap.get("reservoirTemperature_cbm");
									}
								}else if("TubingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
									value=srpProductionData.getProduction().getTubingPressure()+"";
									if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
										columnName=languageResourceMap.get("tubingPressure_cbm");
									}
								}else if("CasingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
									value=srpProductionData.getProduction().getCasingPressure()+"";
								}else if("WellHeadTemperature".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
									value=srpProductionData.getProduction().getWellHeadTemperature()+"";
								}else if("WaterCut".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
									value=srpProductionData.getProduction().getWaterCut()+"";
								}else if("ProductionGasOilRatio".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
									value=srpProductionData.getProduction().getProductionGasOilRatio()+"";
								}else if("ProducingfluidLevel".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
									value=srpProductionData.getProduction().getProducingfluidLevel()+"";
								}else if("PumpSettingDepth".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
									value=srpProductionData.getProduction().getPumpSettingDepth()+"";
								}else if("PumpBoreDiameter".equalsIgnoreCase(column) && srpProductionData.getPump()!=null ){
									value=srpProductionData.getPump().getPumpBoreDiameter()*1000+"";
								}else if("LevelCorrectValue".equalsIgnoreCase(column) && srpProductionData.getManualIntervention()!=null ){
									value=srpProductionData.getManualIntervention().getLevelCorrectValue()+"";
								}
							}
							rawValue=value;
							rawColumnName=columnName;
							ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,3);
							protocolItemResolutionDataList.add(protocolItemResolutionData);
						}
					}else if(StringManagerUtils.stringToInteger(calculateType)==2){
						type = new TypeToken<PCPCalculateRequestData>() {}.getType();
						PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
						for(int j=0;j<displayInputItemList.size();j++){
							String columnName=displayInputItemList.get(j).getName_zh_CN();
							if("en".equalsIgnoreCase(language)){
								columnName=displayInputItemList.get(j).getName_en();
							}else if("ru".equalsIgnoreCase(language)){
								columnName=displayInputItemList.get(j).getName_ru();
							}
							String rawColumnName=columnName;
							String value="";
							String rawValue=value;
							String addr="";
							String column=displayInputItemList.get(j).getCode();
							String columnDataType="";
							String resolutionMode="";
							String bitIndex="";
							String unit=displayInputItemList.get(j).getDataUnit();
							int sort=displayInputItemList.get(j).getSorts();
							
							if(pcpProductionData!=null){
								if("CrudeOilDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
									value=pcpProductionData.getFluidPVT().getCrudeOilDensity()+"";
								}else if("WaterDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
									value=pcpProductionData.getFluidPVT().getWaterDensity()+"";
								}else if("NaturalGasRelativeDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
									value=pcpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
								}else if("SaturationPressure".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
									value=pcpProductionData.getFluidPVT().getSaturationPressure()+"";
								}else if("ReservoirDepth".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
									value=pcpProductionData.getReservoir().getDepth()+"";
									if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
										columnName=languageResourceMap.get("reservoirDepth_cbm");
									}
								}else if("ReservoirTemperature".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
									value=pcpProductionData.getReservoir().getTemperature()+"";
									if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
										columnName=languageResourceMap.get("reservoirTemperature_cbm");
									}
								}else if("TubingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
									value=pcpProductionData.getProduction().getTubingPressure()+"";
									if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
										columnName=languageResourceMap.get("tubingPressure_cbm");
									}
								}else if("CasingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
									value=pcpProductionData.getProduction().getCasingPressure()+"";
								}else if("WellHeadTemperature".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
									value=pcpProductionData.getProduction().getWellHeadTemperature()+"";
								}else if("WaterCut".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
									value=pcpProductionData.getProduction().getWaterCut()+"";
								}else if("ProductionGasOilRatio".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
									value=pcpProductionData.getProduction().getProductionGasOilRatio()+"";
								}else if("ProducingfluidLevel".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
									value=pcpProductionData.getProduction().getProducingfluidLevel()+"";
								}else if("PumpSettingDepth".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
									value=pcpProductionData.getProduction().getPumpSettingDepth()+"";
								}
							}
							rawValue=value;
							rawColumnName=columnName;
							ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,3);
							protocolItemResolutionDataList.add(protocolItemResolutionData);
						}
					}
				}
				
				
				
				//附加信息
				if(addInfoDataItemList.size()>0){
					for(int j=0;j<addInfoList.size();j++){
						Object[] addInfoObj=(Object[]) addInfoList.get(j);
						
						String itemDeviceId=addInfoObj[0]+"";
						String itemName=addInfoObj[1]+"";
						String itemValue=addInfoObj[2]+"";
						String itemUnit=addInfoObj[3]+"";
						
						if(deviceId.equalsIgnoreCase(itemDeviceId)){
							String addInfoColumn="";
							
							for(int k=0;k<addInfoDataItemList.size();k++){
								String addInfoName=addInfoDataItemList.get(k).getConfigItemName();
								if(itemName.equals(addInfoName)){
									addInfoColumn="addInfoColumn"+(k+1);
									break;
								}
							}
							result_json.append("\""+addInfoColumn+"\":\""+itemValue+"\",");
						}
					}
				}
				
				for(int j=0;j<protocolItemResolutionDataList.size();j++){
					String rawValue=protocolItemResolutionDataList.get(j).getRawValue();
					String value=protocolItemResolutionDataList.get(j).getValue();
					ModbusProtocolConfig.Items item=null;
					
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col="";
							if(loadProtocolMappingColumnByTitleMap.containsKey(protocol.getItems().get(k).getTitle())){
								col=loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(k).getTitle()).getMappingColumn();
							}
							if(col!=null&&col.equalsIgnoreCase(protocolItemResolutionDataList.get(j).getColumn())){
								item=protocol.getItems().get(k);
								break;
							}
						}
					}
					
					result_json.append("\""+protocolItemResolutionDataList.get(j).getColumn()+"\":\""+value+"\",");
				}
				result_json.append("}");
				dataList.add(result_json.toString());
			}
			
			List<List<Object>> sheetDataList = new ArrayList<>();
			//创建第一行表头
			List<Object> headRow = new ArrayList<>();
			
			for(int i=0;i<headList.size();i++){
				headRow.add(headList.get(i));
			}
		    sheetDataList.add(headRow);
		    
			for(int i=0;i<dataList.size();i++){
				record = new ArrayList<>();
				jsonObject = JSONObject.fromObject(dataList.get(i).replaceAll("null", ""));
				for (int j = 0; j < columnList.size(); j++) {
					if(jsonObject.has(columnList.get(j))){
						record.add(jsonObject.getString(columnList.get(j)));
					}else{
						record.add("");
					}
				}
				sheetDataList.add(record);
			}
			ExcelUtils.export(response,fileName,sheetName, sheetDataList,1);
			if(user!=null){
		    	try {
					saveSystemLog(user,4,languageResourceMap.get("exportFile")+":"+sheetName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			
		}
		return true;
	}
	
	public boolean exportDeviceRealTimeOverviewData(User user,HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceName,
			String deviceType,String dictDeviceType,
			String FESdiagramResultStatValue,String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,Page pager,
			String language){
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try{
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			int timeEfficiencyZoom=1;
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
				timeEfficiencyZoom=100;
			}
			
			if(timeEfficiencyUnitType==2){
				head=head.replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)");
			}else{
				head=head.replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)");
			}
			
			List<String> firstFeadList = new ArrayList<>(Arrays.asList(head.split(",")));
			List<String> headList = new ArrayList<>(Arrays.asList(head.split(",")));
			List<String> columnList=new ArrayList<>(Arrays.asList(field.split(",")));
			
			Map<Integer,Map<String,String>> addInfoMap=new HashMap<>();
			Map<String,Integer> addInfoKeyMap=new LinkedHashMap<>();
			
			int maxAuxiliaryDeviceCount=0;
			
			String tableName="tbl_acqdata_latest";
			String deviceTableName="viw_device";
			String calTableName="tbl_srpacqdata_latest";
			
			String sql="select t.id,t.devicename,"//0~1
					+ "t.videourl1,t.videokeyid1,t.videourl2,t.videokeyid2,"//2~5
					+ "c1.name_"+language+" as devicetypename,"//6
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"//7
					+ "decode(t2.commstatus,null,0,t2.commstatus) as commstatus,decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,"//8~9
					+ "t2.commtime,t2.commtimeefficiency*"+timeEfficiencyZoom+",t2.commrange,"//10~12
					+ "decode(t2.runstatus,null,2,t2.runstatus) as runstatus,decode(t2.commstatus,0,'"+languageResourceMap.get("offline")+"',null,'"+languageResourceMap.get("offline")+"',2,'"+languageResourceMap.get("goOnline")+"',decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"')) as runStatusName,"//13~14
					+ "t2.runtime,t2.runtimeefficiency*"+timeEfficiencyZoom+",t2.runrange,"//15~17
					+ "t.calculateType,t.deviceType,"//18~19
					+ "t.productiondata";
			
			
			String addInfoSql="select t.id,t2.itemname,t2.itemvalue,t2.itemunit from "
					+ " tbl_device t,tbl_deviceaddinfo t2 "
					+ " where t.id=t2.deviceid "
					+ " and t.orgid in ("+orgId+") ";
			
			String auxiliaryDeviceSql="select t.id,t3.name,t3.manufacturer,t3.model,t3.remark "
					+ " from tbl_device t,tbl_auxiliary2master t2,tbl_auxiliarydevice t3"
					+ " where t.id=t2.masterid and t2.auxiliaryid=t3.id"
					+ " and t.orgid in ("+orgId+") ";
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceid=t.id"
					+ " left outer join tbl_devicetypeinfo c1 on c1.id=t.devicetype ";
			
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+= " left outer join "+calTableName+" t3 on t3.deviceid=t.id";
			}
			sql+= " where  t.orgid in ("+orgId+") ";
			
			if(StringManagerUtils.isNum(deviceType)){
				sql+= " and t.devicetype="+deviceType;
				addInfoSql+= " and t.devicetype="+deviceType;
				auxiliaryDeviceSql+= " and t.devicetype="+deviceType;
			}else{
				sql+= " and t.devicetype in ("+deviceType+")";
				addInfoSql+= " and t.devicetype in ("+deviceType+")";
				auxiliaryDeviceSql+= " and t.devicetype in ("+deviceType+")";
			}
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+= " and t.devicename='"+deviceName+"'";
				addInfoSql+= " and t.devicename='"+deviceName+"'";
				auxiliaryDeviceSql+= " and t.devicename='"+deviceName+"'";
			}
			
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+=" and t.calculateType=1 ";
				if(FESdiagramResultStatValue.equalsIgnoreCase(languageResourceMap.get("emptyMsg"))){
					sql+=" and (t3.resultcode=0 or t3.resultcode is null)";
				}else{
					sql+=" and t3.resultcode="+MemoryDataManagerTask.getWorkTypeByName(FESdiagramResultStatValue,language).getResultCode();
				}
			}
			
			
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'"+languageResourceMap.get("offline")+"',null,'"+languageResourceMap.get("offline")+"',2,'"+languageResourceMap.get("goOnline")+"',decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"'))='"+runStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.devicename";
			addInfoSql+=" order by t.id,t2.id";
			auxiliaryDeviceSql+=" order by t.sortnum,t.devicename,t3.sort";
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			List<?> list = this.findCallSql(finalSql);
			List<?> addInfoList = this.findCallSql(addInfoSql);
			List<?> auxiliaryDeviceList = this.findCallSql(auxiliaryDeviceSql);
			List<Object> record=null;
			JSONObject jsonObject=null;
			Object[] obj=null;
			List<String> dataList=new ArrayList<>();
			for(int i=0;i<list.size();i++){
				obj=(Object[]) list.get(i);
				StringBuffer result_json = new StringBuffer();
				
				String deviceId=obj[0]+"";
				
				String calculateType=obj[18]+"";
				String productionData=obj[20]+""; 
				
				DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
				String protocolCode="";
				AcqInstanceOwnItem acqInstanceOwnItem=null;
				if(deviceInfo!=null){
					acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(deviceInfo.getInstanceCode());
					if(acqInstanceOwnItem!=null){
						protocolCode=acqInstanceOwnItem.getProtocolCode();
					}
				}
				ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByCode(protocolCode);
				
				//附加信息
				Map<String,String> deviceAddInfoMap=new LinkedHashMap<>(); 
				for(int j=0;j<addInfoList.size();j++){
					Object[] addInfoObj=(Object[]) addInfoList.get(j);
					if(deviceId.equalsIgnoreCase(addInfoObj[0]+"")){
						deviceAddInfoMap.put(addInfoObj[1]+"", StringManagerUtils.isNotNull(addInfoObj[3]+"")?(addInfoObj[2]+"("+addInfoObj[3]+")"):(addInfoObj[2]+""));
						if(!addInfoKeyMap.containsKey(addInfoObj[1]+"")){
							addInfoKeyMap.put(addInfoObj[1]+"", addInfoKeyMap.size()+1);
						}
					}
				}
				addInfoMap.put(StringManagerUtils.stringToInteger(deviceId), deviceAddInfoMap);
				
				result_json.append("{\"id\":"+(i+1)+",");
				result_json.append("\"deviceName\":\""+obj[1]+"\",");
				
				result_json.append("\"videoUrl1\":\""+obj[2]+"\",");
				result_json.append("\"videoKeyId1\":\""+obj[3]+"\",");
				result_json.append("\"videoUrl2\":\""+obj[4]+"\",");
				result_json.append("\"videoKeyId2\":\""+obj[5]+"\",");
				
				
				result_json.append("\"deviceTypeName\":\""+obj[6]+"\",");
				result_json.append("\"acqTime\":\""+obj[7]+"\",");
				result_json.append("\"commStatus\":\""+obj[8]+"\",");
				result_json.append("\"commStatusName\":\""+obj[9]+"\",");
				result_json.append("\"commTime\":\""+obj[10]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[11]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
				result_json.append("\"runStatus\":\""+obj[13]+"\",");
				result_json.append("\"runStatusName\":\""+obj[14]+"\",");
				result_json.append("\"runTime\":\""+obj[15]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[16]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[17])+"\",");
				result_json.append("\"calculateType\":"+obj[18]);
				
				if(StringManagerUtils.stringToInteger(calculateType)==1){
					type = new TypeToken<SRPProductionData>() {}.getType();
					SRPProductionData srpProductionData=gson.fromJson(productionData, type);
					if(srpProductionData!=null){
						result_json.append(",\"crudeoilDensity\":\""+(srpProductionData.getFluidPVT()!=null?srpProductionData.getFluidPVT().getCrudeOilDensity():"")+"\",");
						result_json.append("\"waterDensity\":\""+(srpProductionData.getFluidPVT()!=null?srpProductionData.getFluidPVT().getWaterDensity():"")+"\",");
						result_json.append("\"naturalGasRelativeDensity\":\""+(srpProductionData.getFluidPVT()!=null?srpProductionData.getFluidPVT().getNaturalGasRelativeDensity():"")+"\",");
						result_json.append("\"saturationPressure\":\""+(srpProductionData.getFluidPVT()!=null?srpProductionData.getFluidPVT().getSaturationPressure():"")+"\",");
						
						result_json.append("\"reservoirDepth\":\""+(srpProductionData.getReservoir()!=null?srpProductionData.getReservoir().getDepth():"")+"\",");
						result_json.append("\"reservoirTemperature\":\""+(srpProductionData.getReservoir()!=null?srpProductionData.getReservoir().getTemperature():"")+"\",");
						
						result_json.append("\"tubingPressure\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getProduction().getTubingPressure():"")+"\",");
						result_json.append("\"casingPressure\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getProduction().getCasingPressure():"")+"\",");
						result_json.append("\"wellHeadFluidTemperature\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getProduction().getWellHeadTemperature():"")+"\",");
						result_json.append("\"weightWaterCut\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getProduction().getWaterCut():"")+"\",");
						result_json.append("\"productionGasOilRatio\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getProduction().getProductionGasOilRatio():"")+"\",");
						result_json.append("\"producingFluidLevel\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getProduction().getProducingfluidLevel():"")+"\",");
						result_json.append("\"pumpSettingDepth\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getProduction().getPumpSettingDepth():"")+"\",");
						
						String barrelType="";
						if(srpProductionData.getPump()!=null&&srpProductionData.getPump().getBarrelType()!=null){
							if("L".equalsIgnoreCase(srpProductionData.getPump().getBarrelType())){
								barrelType=languageResourceMap.get("barrelType_L");
							}else if("H".equalsIgnoreCase(srpProductionData.getPump().getBarrelType())){
								barrelType=languageResourceMap.get("barrelType_H");
							}
						}
						result_json.append("\"barrelTypeName\":\""+barrelType+"\",");
						result_json.append("\"pumpGrade\":\""+(srpProductionData.getPump()!=null?srpProductionData.getPump().getPumpGrade():"")+"\",");
						result_json.append("\"pumpboreDiameter\":\""+(srpProductionData.getPump()!=null?(srpProductionData.getPump().getPumpBoreDiameter()*1000):"")+"\",");
						result_json.append("\"plungerLength\":\""+(srpProductionData.getPump()!=null?srpProductionData.getPump().getPlungerLength():"")+"\",");
						
						result_json.append("\"tubingStringInsideDiameter\":\""+(srpProductionData.getTubingString()!=null&&srpProductionData.getTubingString().getEveryTubing()!=null&&srpProductionData.getTubingString().getEveryTubing().size()>0?(srpProductionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000):"")+"\",");
						result_json.append("\"casingStringInsideDiameter\":\""+(srpProductionData.getCasingString()!=null&&srpProductionData.getCasingString().getEveryCasing()!=null&&srpProductionData.getCasingString().getEveryCasing().size()>0?(srpProductionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000):"")+"\",");
						
						String rodType1="",rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="";
						String rodType2="",rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="";
						String rodType3="",rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="";
						String rodType4="",rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="";
						if(srpProductionData.getRodString()!=null&&srpProductionData.getRodString().getEveryRod()!=null&&srpProductionData.getRodString().getEveryRod().size()>0){
							if(srpProductionData.getRodString().getEveryRod().size()>0){
								if(srpProductionData.getRodString().getEveryRod().get(0).getType()==1){
									rodType1=languageResourceMap.get("rodStringTypeValue1");
								}else if(srpProductionData.getRodString().getEveryRod().get(0).getType()==2){
									rodType1=languageResourceMap.get("rodStringTypeValue2");
								}else if(srpProductionData.getRodString().getEveryRod().get(0).getType()==3){
									rodType1=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType1=languageResourceMap.get("rodStringTypeValue1");
								}
								rodGrade1=srpProductionData.getRodString().getEveryRod().get(0).getGrade();
								rodOutsideDiameter1=srpProductionData.getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
								rodInsideDiameter1=srpProductionData.getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
								rodLength1=srpProductionData.getRodString().getEveryRod().get(0).getLength()+"";
							}
							if(srpProductionData.getRodString().getEveryRod().size()>1){
								if(srpProductionData.getRodString().getEveryRod().get(1).getType()==1){
									rodType2=languageResourceMap.get("rodStringTypeValue1");
								}else if(srpProductionData.getRodString().getEveryRod().get(1).getType()==2){
									rodType2=languageResourceMap.get("rodStringTypeValue2");
								}else if(srpProductionData.getRodString().getEveryRod().get(1).getType()==3){
									rodType2=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType2=languageResourceMap.get("rodStringTypeValue1");
								}
								rodGrade2=srpProductionData.getRodString().getEveryRod().get(1).getGrade();
								rodOutsideDiameter2=srpProductionData.getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
								rodInsideDiameter2=srpProductionData.getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
								rodLength2=srpProductionData.getRodString().getEveryRod().get(1).getLength()+"";
							}
							if(srpProductionData.getRodString().getEveryRod().size()>2){
								if(srpProductionData.getRodString().getEveryRod().get(2).getType()==1){
									rodType3=languageResourceMap.get("rodStringTypeValue1");
								}else if(srpProductionData.getRodString().getEveryRod().get(2).getType()==2){
									rodType3=languageResourceMap.get("rodStringTypeValue2");
								}else if(srpProductionData.getRodString().getEveryRod().get(2).getType()==3){
									rodType3=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType3=languageResourceMap.get("rodStringTypeValue1");
								}
								rodGrade3=srpProductionData.getRodString().getEveryRod().get(2).getGrade();
								rodOutsideDiameter3=srpProductionData.getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
								rodInsideDiameter3=srpProductionData.getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
								rodLength3=srpProductionData.getRodString().getEveryRod().get(2).getLength()+"";
							}
							if(srpProductionData.getRodString().getEveryRod().size()>3){
								if(srpProductionData.getRodString().getEveryRod().get(3).getType()==1){
									rodType4=languageResourceMap.get("rodStringTypeValue1");
								}else if(srpProductionData.getRodString().getEveryRod().get(3).getType()==2){
									rodType4=languageResourceMap.get("rodStringTypeValue2");
								}else if(srpProductionData.getRodString().getEveryRod().get(3).getType()==3){
									rodType4=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType4=languageResourceMap.get("rodStringTypeValue4");
								}
								rodGrade4=srpProductionData.getRodString().getEveryRod().get(3).getGrade();
								rodOutsideDiameter4=srpProductionData.getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
								rodInsideDiameter4=srpProductionData.getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
								rodLength4=srpProductionData.getRodString().getEveryRod().get(3).getLength()+"";
							}
						}
						result_json.append("\"rodTypeName1\":\""+rodType1+"\",");
						result_json.append("\"rodGrade1\":\""+rodGrade1+"\",");
						result_json.append("\"rodOutsideDiameter1\":\""+rodOutsideDiameter1+"\",");
						result_json.append("\"rodInsideDiameter1\":\""+rodInsideDiameter1+"\",");
						result_json.append("\"rodLength1\":\""+rodLength1+"\",");
						
						result_json.append("\"rodTypeName2\":\""+rodType2+"\",");
						result_json.append("\"rodGrade2\":\""+rodGrade2+"\",");
						result_json.append("\"rodOutsideDiameter2\":\""+rodOutsideDiameter2+"\",");
						result_json.append("\"rodInsideDiameter2\":\""+rodInsideDiameter2+"\",");
						result_json.append("\"rodLength2\":\""+rodLength2+"\",");
						
						result_json.append("\"rodTypeName3\":\""+rodType3+"\",");
						result_json.append("\"rodGrade3\":\""+rodGrade3+"\",");
						result_json.append("\"rodOutsideDiameter3\":\""+rodOutsideDiameter3+"\",");
						result_json.append("\"rodInsideDiameter3\":\""+rodInsideDiameter3+"\",");
						result_json.append("\"rodLength3\":\""+rodLength3+"\",");
						
						result_json.append("\"rodTypeName4\":\""+rodType4+"\",");
						result_json.append("\"rodGrade4\":\""+rodGrade4+"\",");
						result_json.append("\"rodOutsideDiameter4\":\""+rodOutsideDiameter4+"\",");
						result_json.append("\"rodInsideDiameter4\":\""+rodInsideDiameter4+"\",");
						result_json.append("\"rodLength4\":\""+rodLength4+"\",");
						
						
						String manualInterventionName=languageResourceMap.get("noIntervention");
						if(srpProductionData.getManualIntervention()!=null && srpProductionData.getManualIntervention().getCode()>0){
							WorkType workType=MemoryDataManagerTask.getWorkTypeByCode(srpProductionData.getManualIntervention().getCode()+"",language);
							if(workType!=null){
								manualInterventionName=workType.getResultName();
							}
						}
						result_json.append("\"manualInterventionResult\":\""+manualInterventionName+"\",");
						result_json.append("\"netGrossRatio\":\""+(srpProductionData.getManualIntervention()!=null?srpProductionData.getManualIntervention().getNetGrossRatio():"")+"\",");
						result_json.append("\"netGrossValue\":\""+(srpProductionData.getManualIntervention()!=null?srpProductionData.getManualIntervention().getNetGrossValue():"")+"\",");
						result_json.append("\"levelCorrectValue\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getManualIntervention().getLevelCorrectValue():"")+"\"");
					}
				}else if(StringManagerUtils.stringToInteger(calculateType)==2){
					type = new TypeToken<PCPProductionData>() {}.getType();
					PCPProductionData pcpProductionData=gson.fromJson(productionData, type);
					if(pcpProductionData!=null){
						result_json.append(",\"crudeoilDensity\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getCrudeOilDensity():"")+"\",");
						result_json.append("\"waterDensity\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getWaterDensity():"")+"\",");
						result_json.append("\"naturalGasRelativeDensity\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getNaturalGasRelativeDensity():"")+"\",");
						result_json.append("\"saturationPressure\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getSaturationPressure():"")+"\",");
						
						result_json.append("\"reservoirDepth\":\""+(pcpProductionData.getReservoir()!=null?pcpProductionData.getReservoir().getDepth():"")+"\",");
						result_json.append("\"reservoirTemperature\":\""+(pcpProductionData.getReservoir()!=null?pcpProductionData.getReservoir().getTemperature():"")+"\",");
						
						result_json.append("\"tubingPressure\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getTubingPressure():"")+"\",");
						result_json.append("\"casingPressure\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getCasingPressure():"")+"\",");
						result_json.append("\"wellHeadFluidTemperature\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getWellHeadTemperature():"")+"\",");
						result_json.append("\"weightWaterCut\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getWaterCut():"")+"\",");
						result_json.append("\"productionGasOilRatio\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getProductionGasOilRatio():"")+"\",");
						result_json.append("\"producingFluidLevel\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getProducingfluidLevel():"")+"\",");
						result_json.append("\"pumpSettingDepth\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getPumpSettingDepth():"")+"\",");
						
						result_json.append("\"barrelSeries\":\""+(pcpProductionData.getPump()!=null?pcpProductionData.getPump().getBarrelSeries():"")+"\",");
						result_json.append("\"rotorDiameter\":\""+(pcpProductionData.getPump()!=null?(pcpProductionData.getPump().getRotorDiameter()*1000):"")+"\",");
						result_json.append("\"qpr\":\""+(pcpProductionData.getPump()!=null?(pcpProductionData.getPump().getQPR()*1000*1000):"")+"\",");
						
						result_json.append("\"tubingStringInsideDiameter\":\""+(pcpProductionData.getTubingString()!=null&&pcpProductionData.getTubingString().getEveryTubing()!=null&&pcpProductionData.getTubingString().getEveryTubing().size()>0?(pcpProductionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000):"")+"\",");
						result_json.append("\"casingStringInsideDiameter\":\""+(pcpProductionData.getCasingString()!=null&&pcpProductionData.getCasingString().getEveryCasing()!=null&&pcpProductionData.getCasingString().getEveryCasing().size()>0?(pcpProductionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000):"")+"\",");
						
						String rodType1="",rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="";
						String rodType2="",rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="";
						String rodType3="",rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="";
						String rodType4="",rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="";
						if(pcpProductionData.getRodString()!=null&&pcpProductionData.getRodString().getEveryRod()!=null&&pcpProductionData.getRodString().getEveryRod().size()>0){
							if(pcpProductionData.getRodString().getEveryRod().size()>0){
								if(pcpProductionData.getRodString().getEveryRod().get(0).getType()==1){
									rodType1=languageResourceMap.get("rodStringTypeValue1");
								}else if(pcpProductionData.getRodString().getEveryRod().get(0).getType()==2){
									rodType1=languageResourceMap.get("rodStringTypeValue2");
								}else if(pcpProductionData.getRodString().getEveryRod().get(0).getType()==3){
									rodType1=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType1=languageResourceMap.get("rodStringTypeValue1");
								}
								rodGrade1=pcpProductionData.getRodString().getEveryRod().get(0).getGrade();
								rodOutsideDiameter1=pcpProductionData.getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
								rodInsideDiameter1=pcpProductionData.getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
								rodLength1=pcpProductionData.getRodString().getEveryRod().get(0).getLength()+"";
							}
							if(pcpProductionData.getRodString().getEveryRod().size()>1){
								if(pcpProductionData.getRodString().getEveryRod().get(1).getType()==1){
									rodType2=languageResourceMap.get("rodStringTypeValue1");
								}else if(pcpProductionData.getRodString().getEveryRod().get(1).getType()==2){
									rodType2=languageResourceMap.get("rodStringTypeValue2");
								}else if(pcpProductionData.getRodString().getEveryRod().get(1).getType()==3){
									rodType2=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType2=languageResourceMap.get("rodStringTypeValue1");
								}
								rodGrade2=pcpProductionData.getRodString().getEveryRod().get(1).getGrade();
								rodOutsideDiameter2=pcpProductionData.getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
								rodInsideDiameter2=pcpProductionData.getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
								rodLength2=pcpProductionData.getRodString().getEveryRod().get(1).getLength()+"";
							}
							if(pcpProductionData.getRodString().getEveryRod().size()>2){
								if(pcpProductionData.getRodString().getEveryRod().get(2).getType()==1){
									rodType3=languageResourceMap.get("rodStringTypeValue1");
								}else if(pcpProductionData.getRodString().getEveryRod().get(2).getType()==2){
									rodType3=languageResourceMap.get("rodStringTypeValue2");
								}else if(pcpProductionData.getRodString().getEveryRod().get(2).getType()==3){
									rodType3=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType3=languageResourceMap.get("rodStringTypeValue1");
								}
								rodGrade3=pcpProductionData.getRodString().getEveryRod().get(2).getGrade();
								rodOutsideDiameter3=pcpProductionData.getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
								rodInsideDiameter3=pcpProductionData.getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
								rodLength3=pcpProductionData.getRodString().getEveryRod().get(2).getLength()+"";
							}
							if(pcpProductionData.getRodString().getEveryRod().size()>3){
								if(pcpProductionData.getRodString().getEveryRod().get(3).getType()==1){
									rodType4=languageResourceMap.get("rodStringTypeValue1");
								}else if(pcpProductionData.getRodString().getEveryRod().get(3).getType()==2){
									rodType4=languageResourceMap.get("rodStringTypeValue2");
								}else if(pcpProductionData.getRodString().getEveryRod().get(3).getType()==3){
									rodType4=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType4=languageResourceMap.get("rodStringTypeValue4");
								}
								rodGrade4=pcpProductionData.getRodString().getEveryRod().get(3).getGrade();
								rodOutsideDiameter4=pcpProductionData.getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
								rodInsideDiameter4=pcpProductionData.getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
								rodLength4=pcpProductionData.getRodString().getEveryRod().get(3).getLength()+"";
							}
						}
						result_json.append("\"rodTypeName1\":\""+rodType1+"\",");
						result_json.append("\"rodGrade1\":\""+rodGrade1+"\",");
						result_json.append("\"rodOutsideDiameter1\":\""+rodOutsideDiameter1+"\",");
						result_json.append("\"rodInsideDiameter1\":\""+rodInsideDiameter1+"\",");
						result_json.append("\"rodLength1\":\""+rodLength1+"\",");
						
						result_json.append("\"rodTypeName2\":\""+rodType2+"\",");
						result_json.append("\"rodGrade2\":\""+rodGrade2+"\",");
						result_json.append("\"rodOutsideDiameter2\":\""+rodOutsideDiameter2+"\",");
						result_json.append("\"rodInsideDiameter2\":\""+rodInsideDiameter2+"\",");
						result_json.append("\"rodLength2\":\""+rodLength2+"\",");
						
						result_json.append("\"rodTypeName3\":\""+rodType3+"\",");
						result_json.append("\"rodGrade3\":\""+rodGrade3+"\",");
						result_json.append("\"rodOutsideDiameter3\":\""+rodOutsideDiameter3+"\",");
						result_json.append("\"rodInsideDiameter3\":\""+rodInsideDiameter3+"\",");
						result_json.append("\"rodLength3\":\""+rodLength3+"\",");
						
						result_json.append("\"rodTypeName4\":\""+rodType4+"\",");
						result_json.append("\"rodGrade4\":\""+rodGrade4+"\",");
						result_json.append("\"rodOutsideDiameter4\":\""+rodOutsideDiameter4+"\",");
						result_json.append("\"rodInsideDiameter4\":\""+rodInsideDiameter4+"\",");
						result_json.append("\"rodLength4\":\""+rodLength4+"\",");
						
						result_json.append("\"netGrossRatio\":\""+(pcpProductionData.getManualIntervention()!=null?pcpProductionData.getManualIntervention().getNetGrossRatio():"")+"\",");
						result_json.append("\"netGrossValue\":\""+(pcpProductionData.getManualIntervention()!=null?pcpProductionData.getManualIntervention().getNetGrossValue():"")+"\"");
					}else{
						
					}
				}
				
				if(deviceAddInfoMap.size()>0){
					Iterator<Map.Entry<String,String>> iterator = deviceAddInfoMap.entrySet().iterator();
					while (iterator.hasNext()) {
					    Map.Entry<String,String> entry = iterator.next();
					    String key = entry.getKey();
					    String value = entry.getValue();
					    if(addInfoKeyMap.containsKey(key)){
					    	String col="addInfoColumn"+addInfoKeyMap.get(key);
					    	result_json.append(",\""+col+"\":\""+value+"\"");
					    }
					}
				}
				
				
				//辅件设备
				int auxiliaryDeviceCount=0;
				for(int j=0;j<auxiliaryDeviceList.size();j++){
					Object[] auxiliaryDeviceObj=(Object[]) auxiliaryDeviceList.get(j);
					if(deviceId.equalsIgnoreCase(auxiliaryDeviceObj[0]+"")){
						auxiliaryDeviceCount++;
						result_json.append(",\"auxiliaryDevice"+auxiliaryDeviceCount+"Name\":\""+auxiliaryDeviceObj[1]+"\"");
						result_json.append(",\"auxiliaryDevice"+auxiliaryDeviceCount+"Manufacturer\":\""+auxiliaryDeviceObj[2]+"\"");
						result_json.append(",\"auxiliaryDevice"+auxiliaryDeviceCount+"Model\":\""+auxiliaryDeviceObj[3]+"\"");
						result_json.append(",\"auxiliaryDevice"+auxiliaryDeviceCount+"Remark\":\""+auxiliaryDeviceObj[4]+"\"");
					}
				}
				
				if(maxAuxiliaryDeviceCount<auxiliaryDeviceCount){
					maxAuxiliaryDeviceCount=auxiliaryDeviceCount;
				}
				result_json.append("}");
				dataList.add(result_json.toString());
			}
			
			int overviewColSize=headList.size();
			int rowIndexCol=0;
			int addInfoColSize=addInfoKeyMap.size();
			if(headList.size()>0 && ("序号".equalsIgnoreCase(headList.get(0))||headList.get(0).equalsIgnoreCase(languageResourceMap.get("idx")))     ){
				rowIndexCol=1;
				overviewColSize-=1;
			}
			
			Iterator<Map.Entry<String,Integer>> iterator = addInfoKeyMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String,Integer> entry = iterator.next();
				String key = entry.getKey();
			    int value = entry.getValue();
			    headList.add(key);
			    firstFeadList.add(key);
			    columnList.add("addInfoColumn"+value);
			}
			
			if(maxAuxiliaryDeviceCount>0){
				for(int i=0;i<maxAuxiliaryDeviceCount;i++){
					headList.add(languageResourceMap.get("deviceName"));
					headList.add(languageResourceMap.get("manufacturer"));
					headList.add(languageResourceMap.get("model"));
					headList.add(languageResourceMap.get("remark"));
					
					firstFeadList.add(languageResourceMap.get("deviceName"));
					firstFeadList.add(languageResourceMap.get("manufacturer"));
					firstFeadList.add(languageResourceMap.get("model"));
					firstFeadList.add(languageResourceMap.get("remark"));
					
				    columnList.add("auxiliaryDevice"+(i+1)+"Name");
				    columnList.add("auxiliaryDevice"+(i+1)+"Manufacturer");
				    columnList.add("auxiliaryDevice"+(i+1)+"Model");
				    columnList.add("auxiliaryDevice"+(i+1)+"Remark");
				}
			}
			
			List<List<Object>> sheetDataList = new ArrayList<>();
			//创建第一行表头
			List<Object> firstHeadRow = new ArrayList<>();
			if(overviewColSize>0){
				firstFeadList.set(rowIndexCol, languageResourceMap.get("deviceOverview"));
				for(int i=1;i<overviewColSize;i++){
					firstFeadList.set(rowIndexCol+i, ExcelUtils.COLUMN_MERGE);
				}
			}
			if(addInfoColSize>0){
				firstFeadList.set(rowIndexCol+overviewColSize, languageResourceMap.get("additionalInformation"));
				for(int i=1;i<addInfoColSize;i++){
					firstFeadList.set(rowIndexCol+overviewColSize+i, ExcelUtils.COLUMN_MERGE);
				}
			}
			if(maxAuxiliaryDeviceCount>0){
				firstFeadList.set(rowIndexCol+overviewColSize+addInfoColSize, languageResourceMap.get("auxiliaryDevice"));
				for(int i=1;i<maxAuxiliaryDeviceCount*4;i++){
					firstFeadList.set(rowIndexCol+overviewColSize+addInfoColSize+i, ExcelUtils.COLUMN_MERGE);
				}
			}
			
			for(int i=0;i<firstFeadList.size();i++){
				firstHeadRow.add(firstFeadList.get(i));
			}
		    sheetDataList.add(firstHeadRow);
			
		    //创建第二行表头
			List<Object> headRow = new ArrayList<>();
			if(rowIndexCol==1){
				headList.set(rowIndexCol-1, ExcelUtils.ROW_MERGE);
			}
			for(int i=0;i<headList.size();i++){
				headRow.add(headList.get(i));
			}
		    sheetDataList.add(headRow);
		    
			for(int i=0;i<dataList.size();i++){
				record = new ArrayList<>();
				jsonObject = JSONObject.fromObject(dataList.get(i).replaceAll("null", ""));
				for (int j = 0; j < columnList.size(); j++) {
					if(jsonObject.has(columnList.get(j))){
						record.add(jsonObject.getString(columnList.get(j)));
					}else{
						record.add("");
					}
				}
				sheetDataList.add(record);
			}
			ExcelUtils.export(response,fileName,title, sheetDataList,2);
			if(user!=null){
		    	try {
					saveSystemLog(user,4,languageResourceMap.get("exportFile")+":"+title);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			
		}
		return true;
	}

	public String getDeviceRealTimeMonitoringData(String deviceId,String deviceName,String deviceType,String calculateType,int userNo,String language) throws IOException, SQLException{
		int items=3;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer result_json = new StringBuffer();
		StringBuffer info_json = new StringBuffer();
		AlarmShowStyle alarmShowStyle=null;
		AcqInstanceOwnItem acqInstanceOwnItem=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		AlarmInstanceOwnItem alarmInstanceOwnItem=null;
		List<CalItem> calItemList=null;
		List<CalItem> inputItemList=null;
		UserInfo userInfo=null;
		String tableName="tbl_acqdata_latest";
		String calAndInputTableName="";
		String deviceTableName="tbl_device";
		String acqInstanceCode="";
		String displayInstanceCode="";
		String alarmInstanceCode="";
		
		DeviceInfo deviceInfo=null;
		
		if(StringManagerUtils.stringToInteger(calculateType)==1){
			calAndInputTableName="tbl_srpacqdata_latest";
		}else if(StringManagerUtils.stringToInteger(calculateType)==2){
			calAndInputTableName="tbl_pcpacqdata_latest";
		}
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
		String timeEfficiencyUnit=languageResourceMap.get("decimals");
		int timeEfficiencyZoom=1;
		if(timeEfficiencyUnitType==2){
			timeEfficiencyUnit="%";
			timeEfficiencyZoom=100;
		}
		
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
		Map<String,DataMapping> protocolExtendedFieldColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(1);
		try{
			deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
			alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
			userInfo=MemoryDataManagerTask.getUserInfoByNo(userNo+"");
			if(deviceInfo!=null){
				displayInstanceCode=deviceInfo.getDisplayInstanceCode();
				alarmInstanceCode=deviceInfo.getAlarmInstanceCode();
				acqInstanceCode=deviceInfo.getInstanceCode();
			}
			
			acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(acqInstanceCode);
			displayInstanceOwnItem=MemoryDataManagerTask.getDisplayInstanceOwnItemByCode(displayInstanceCode);
			alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(alarmInstanceCode);
			ModbusProtocolConfig.Protocol protocol=null;
			if(displayInstanceOwnItem!=null){
				protocol=MemoryDataManagerTask.getProtocolByCode(displayInstanceOwnItem.getProtocolCode());
			}
			
			
			if(StringManagerUtils.stringToInteger(calculateType)==1){
				calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
				inputItemList=MemoryDataManagerTask.getSRPInputItem(language);
			}else if(StringManagerUtils.stringToInteger(calculateType)==2){
				calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
				inputItemList=MemoryDataManagerTask.getPCPInputItem(language);
			}else{
				calItemList=MemoryDataManagerTask.getAcqCalculateItem(language);
				inputItemList=new ArrayList<>();
			}
			
			String columns = "[";
			for(int i=1;i<=items;i++){
				columns+= "{ \"header\":\"名称\",\"dataIndex\":\"name"+i+"\",children:[] },"
						+ "{ \"header\":\"变量\",\"dataIndex\":\"value"+i+"\",children:[] }";
				if(i<items){
					columns+=",";
				}
			}
			columns+= "]";
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"totalRoot\":[");
			info_json.append("[");
			if(displayInstanceOwnItem!=null&&userInfo!=null){
				if(protocol!=null){
					List<ModbusProtocolConfig.Items> protocolItems=new ArrayList<ModbusProtocolConfig.Items>();
					List<ModbusProtocolConfig.ExtendedField> protocolExtendedFields=new ArrayList<ModbusProtocolConfig.ExtendedField>();
					List<CalItem> displayCalItemList=new ArrayList<CalItem>();
					List<CalItem> displayInputItemList=new ArrayList<CalItem>();
					Map<String,DisplayInstanceOwnItem.DisplayItem> dailyTotalCalItemMap=new HashMap<>();
					List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
					WorkType workType=null;
					for(int j=0;j<protocol.getItems().size();j++){
						if((!"w".equalsIgnoreCase(protocol.getItems().get(j).getRWType())) 
								&& (StringManagerUtils.existDisplayItem(displayInstanceOwnItem.getItemList(), protocol.getItems().get(j).getTitle(), false))){
							for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
								if(displayInstanceOwnItem.getItemList().get(k).getType()==0 
										&& displayInstanceOwnItem.getItemList().get(k).getRealtimeData()==1
										&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
										&& protocol.getItems().get(j).getTitle().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemName())
										){
									protocolItems.add(protocol.getItems().get(j));
									break;
								}
							}
						}
					}
					
					//拓展字段
					if(protocol.getExtendedFields()!=null && protocol.getExtendedFields().size()>0){
						for(int j=0;j<protocol.getExtendedFields().size();j++){
							DataMapping dataMapping=protocolExtendedFieldColumnByTitleMap.get(protocol.getExtendedFields().get(j).getTitle());
							if(dataMapping!=null){
								String extendedField=dataMapping.getMappingColumn();
								for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
									if(displayInstanceOwnItem.getItemList().get(k).getType()==5 
											&& displayInstanceOwnItem.getItemList().get(k).getRealtimeData()==1
											&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
											&& extendedField.equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode()) ){
										protocolExtendedFields.add(protocol.getExtendedFields().get(j));
										break;
									}
								}
							}
						}
					}
					
					//计算项
					if(calItemList!=null){
						for(CalItem calItem:calItemList){
							if(StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), calItem.getCode(), false,0,1)){
								for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
									if(displayInstanceOwnItem.getItemList().get(k).getType()==1
											&& displayInstanceOwnItem.getItemList().get(k).getRealtimeData()==1
											&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
											&& calItem.getCode().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode())){
										displayCalItemList.add(calItem);
										break;
									}
								}
								
							}
						}
					}
					//日汇总计算项
					if(acqInstanceOwnItem!=null){
						for(AcqInstanceOwnItem.AcqItem acqItem:acqInstanceOwnItem.getItemList()){
							if(acqItem.getDailyTotalCalculate()==1 && StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), (acqItem.getItemCode()+"_total").toUpperCase(), false,0,1)){
								for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
									if(displayInstanceOwnItem.getItemList().get(k).getType()==1
											&& displayInstanceOwnItem.getItemList().get(k).getRealtimeData()==1
											&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
											&& (acqItem.getItemCode()+"_total").equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode())){
										displayInstanceOwnItem.getItemList().get(k).setItemSourceName(acqItem.getItemName());
										displayInstanceOwnItem.getItemList().get(k).setItemSourceCode(acqItem.getItemCode());
										dailyTotalCalItemMap.put(displayInstanceOwnItem.getItemList().get(k).getItemCode().toUpperCase(), displayInstanceOwnItem.getItemList().get(k));
										break;
									}
								}
								
							}
						}
					}
					//录入项
					if(inputItemList!=null){
						for(CalItem calItem:inputItemList){
							if(StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), calItem.getCode(), false,0,1)){
								for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
									if(displayInstanceOwnItem.getItemList().get(k).getType()==3
											&& displayInstanceOwnItem.getItemList().get(k).getRealtimeData()==1
											&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
											&& calItem.getCode().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode())){
										displayInputItemList.add(calItem);
										break;
									}
								}
								
							}
						}
					}
					
					String sql="select t.id,t.devicename,to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'), "
							+ "t2.commstatus,decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,decode(t2.commstatus,1,0,100) as commAlarmLevel,"
							+ " t2.runStatus as runStatusCalValue,decode(t2.commstatus,0,'"+languageResourceMap.get("offline")+"',null,'"+languageResourceMap.get("offline")+"',2,'"+languageResourceMap.get("goOnline")+"',decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"')) as runStatusName,decode(t2.runstatus,1,0,100) as runAlarmLevel,"
							+ "t2.acqdata,t2.alarmInfo ";
					
					for(int i=0;i<displayCalItemList.size();i++){
						String column=displayCalItemList.get(i).getCode();
						if(StringManagerUtils.stringToInteger(calculateType)>0){
							if("resultName".equalsIgnoreCase(column)){
								column="resultCode";
							}else if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
								column=column+"*"+timeEfficiencyZoom+" as "+column;
							}else if("runstatusName".equalsIgnoreCase(column)){
								column="runstatus";
							}
							sql+=",t3."+column;
						}else{
							if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
								column=column+"*"+timeEfficiencyZoom+" as "+column;
							}else if("runstatusName".equalsIgnoreCase(column)){
								column="runstatus";
							}
							sql+=",t2."+column;
						}
					}
					
					if(StringManagerUtils.stringToInteger(calculateType)>0){
						if(displayInputItemList.size()>0){
							sql+=",t3.productiondata";
						}
					}
					
					sql+= " from "+deviceTableName+" t "
							+ " left outer join "+tableName+" t2 on t2.deviceid=t.id";
					if(StringManagerUtils.isNotNull(calAndInputTableName)&&(displayCalItemList.size()>0 || inputItemList.size()>0)){
						sql+=" left outer join "+calAndInputTableName+" t3 on t3.deviceid=t.id";
					}
					
					sql+= " where  t.id="+deviceId;
					List<?> list = this.findCallSql(sql);
					if(list.size()>0){
						
						//历史报警查询
//						Map<String,Integer> alarmDataMap=new HashMap<>();
//						if(alarmInstanceOwnItem!=null){
//							String alarmQuerySql="select alarm.deviceid,to_char(alarm.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,alarm.alarmtype,alarm.itemname,alarm.alarmvalue,alarm.alarmlevel,alarm.itemCode,alarm.bitindex  "
//									+ " from tbl_alarminfo_hist alarm,tbl_acqdata_latest t2 "
//									+ " where alarm.deviceid=t2.deviceid and alarm.acqTime = t2.acqTime"
//									+ " and t2.deviceId="+deviceId;
//							List<?> alarmQueryList = this.findCallSql(alarmQuerySql);
//							for(int i=0;i<alarmQueryList.size();i++){
//								Object[] obj=(Object[]) alarmQueryList.get(i);
//								
//								String alarmTime=obj[1]+"";
//								int alarmType=StringManagerUtils.stringToInteger(obj[2]+"");
//								String alarmItemName=obj[3]+"";
//								String alarmValue=obj[4]+"";
//								int alarmLevel=StringManagerUtils.stringToInteger(obj[5]+"");
//								String alarmItemCode=obj[6]+"";
//								String bitIndex=obj[7]!=null?(obj[7]+""):"";
//								if(StringManagerUtils.isNotNull(bitIndex)){
//									alarmItemCode+="_"+bitIndex;
//								}
//								if(StringManagerUtils.isNotNull(alarmItemCode) && alarmLevel>0){
//									String key=alarmItemCode+"_"+alarmTime+"_"+alarmType;
//									alarmDataMap.put(key.toUpperCase(), alarmLevel);
//								}
//							}
//						}
						
						
						int row=1;
						Object[] obj=(Object[]) list.get(0);
						String acqTime=obj[2]+"";
						String runStatus=obj[6]+"";
						String runStatusName=obj[7]+"";
						String acqData=StringManagerUtils.CLOBObjectToString(obj[9]);
						String deviceAlarmInfo=StringManagerUtils.CLOBObjectToString(obj[10]);
						if(!StringManagerUtils.isNotNull(deviceAlarmInfo)){
							deviceAlarmInfo="[]";
						}
						
						
						type = new TypeToken<List<KeyValue>>() {}.getType();
						List<KeyValue> acqDataList=gson.fromJson(acqData, type);
						
						type = new TypeToken<List<KeyValue>>() {}.getType();
						List<KeyValue> alarmInfoList=gson.fromJson(deviceAlarmInfo, type);
						
						for(DisplayInstanceOwnItem.DisplayItem displayItem:displayInstanceOwnItem.getItemList()){
							if(displayItem.getType()!=2 
									&& displayItem.getRealtimeData()==1
									&& displayItem.getShowLevel()>=userInfo.getRoleShowLevel()){
								String column=displayItem.getItemCode();
								String columnName=displayItem.getItemName();
								String rawColumnName=columnName;
								String value="";
								String rawValue="";
								String addr="";
								String columnDataType="";
								String resolutionMode="";
								String bitIndex=displayItem.getBitIndex()+"";
								String unit="";
								int sort=displayItem.getRealtimeSort();
								int switchingValueShowType=displayItem.getSwitchingValueShowType();
								boolean existData=false;
								if(displayItem.getType()==0){//采集项
									ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, rawColumnName);
									if(item!=null){
										addr=item.getAddr()+"";
										columnDataType=item.getIFDataType();
										resolutionMode=item.getResolutionMode()+"";
										unit=item.getUnit();
									}
									if(protocolItems.size()>0 && acqDataList!=null){
										for(KeyValue keyValue:acqDataList){
											if(column.equalsIgnoreCase(keyValue.getKey())){
												value=keyValue.getValue();
												rawValue=value;
												if(item!=null){
													if("int".equalsIgnoreCase(item.getIFDataType()) || "uint".equalsIgnoreCase(item.getIFDataType()) || item.getIFDataType().contains("int")
															||"float32".equalsIgnoreCase(item.getIFDataType())
															||"float64".equalsIgnoreCase(item.getIFDataType())){
														if(value.toUpperCase().contains("E")){
										                 	value=StringManagerUtils.scientificNotationToNormal(value);
										                 }
													}
													
													if(item.getResolutionMode()==1 || item.getResolutionMode()==2){//如果是枚举量
														if(StringManagerUtils.isNotNull(value)&&item.getMeaning()!=null&&item.getMeaning().size()>0){
															for(int l=0;l<item.getMeaning().size();l++){
																if(StringManagerUtils.stringToFloat(value)==(item.getMeaning().get(l).getValue())){
																	value=item.getMeaning().get(l).getMeaning();
																	break;
																}
															}
														}
														ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
														protocolItemResolutionDataList.add(protocolItemResolutionData);
														existData=true;
													}else if(item.getResolutionMode()==0){//如果是开关量
														ModbusProtocolConfig.ItemsMeaning itemsMeaning=MemoryDataManagerTask.getProtocolItemMeaning(item, bitIndex);
														if(itemsMeaning!=null && StringManagerUtils.isNotNull(value)){
															String[] valueArr=value.split(",");
															columnName=StringManagerUtils.isNotNull(itemsMeaning.getMeaning())?itemsMeaning.getMeaning():(languageResourceMap.get("bit")+displayItem.getBitIndex());
															String status0=StringManagerUtils.isNotNull(itemsMeaning.getStatus0())?itemsMeaning.getStatus0():"";
															String status1=StringManagerUtils.isNotNull(itemsMeaning.getStatus1())?itemsMeaning.getStatus1():"";
															if(switchingValueShowType==1){
																columnName=rawColumnName+"/"+columnName;
															}
															for(int m=0;valueArr!=null && m<valueArr.length;m++){
																if(m==itemsMeaning.getValue()){
																	if("bool".equalsIgnoreCase(columnDataType) || "boolean".equalsIgnoreCase(columnDataType)){
																		value=("true".equalsIgnoreCase(valueArr[m]) || "1".equalsIgnoreCase(valueArr[m]))?status1:status0;
																		rawValue=("true".equalsIgnoreCase(valueArr[m]) || "1".equalsIgnoreCase(valueArr[m]))?"1":"0";
																	}else{
																		value=valueArr[m];
																	}
																	ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column+"_"+bitIndex,columnDataType,resolutionMode,bitIndex,unit,sort,0);
																	protocolItemResolutionDataList.add(protocolItemResolutionData);
																	existData=true;
																	break;
																}
															}
														}else{
															ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column+"_"+bitIndex,columnDataType,resolutionMode,bitIndex,unit,sort,0);
															protocolItemResolutionDataList.add(protocolItemResolutionData);
															existData=true;
														}
													}else{//如果是数据量
														for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
															if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column) && displayInstanceOwnItem.getItemList().get(l).getType()!=2){
																sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
																break;
															}
														}
														ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
														protocolItemResolutionDataList.add(protocolItemResolutionData);
														existData=true;
													}
												}
												break;
											}
										}
									}
								}else if(displayItem.getType()==5){//协议拓展项
									ModbusProtocolConfig.ExtendedField extendedField=MemoryDataManagerTask.getProtocolExtendedField(protocol, rawColumnName);
									if(extendedField!=null){
										unit=extendedField.getUnit();
									}
									if(protocolExtendedFields.size()>0 && acqDataList!=null){
										for(KeyValue keyValue:acqDataList){
											if(column.equalsIgnoreCase(keyValue.getKey())){
												value=keyValue.getValue();
												rawValue=value;
												
												if(extendedField!=null){
													String extendedFieldValue=keyValue.getValue();
													ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(
															extendedField.getTitle(),
															extendedField.getTitle(),
															extendedFieldValue,
															extendedFieldValue,
															"",
															column,
															"",
															"7",
															"",
															extendedField.getUnit(),
															sort,
															5);
													protocolItemResolutionDataList.add(protocolItemResolutionData);
													existData=true;
												}
												break;
											}
										}
									}
								}else if(displayItem.getType()==1){//计算项
									resolutionMode="5";
									if(!column.toUpperCase().endsWith("_TOTAL")){
										CalItem calItem=MemoryDataManagerTask.getCalItemByCode(column, language);
										if(calItem!=null){
											unit=calItem.getUnit();
										}
										
										//计算项
										for(int i=0;i<displayCalItemList.size();i++){
											if(column.equalsIgnoreCase(displayCalItemList.get(i).getCode())){
												int index=i+11;
												if(index<obj.length){
													value=obj[index]+"";
													if(obj[index] instanceof CLOB || obj[index] instanceof Clob){
														value=StringManagerUtils.CLOBObjectToString(obj[index]);
													}
													unit=displayCalItemList.get(i).getUnit();
													
													//如果是工况
													if("resultCode".equalsIgnoreCase(column)||"resultName".equalsIgnoreCase(column)){
														resolutionMode="4";
														workType=MemoryDataManagerTask.getWorkTypeByCode(value,language);
														if(workType!=null){
															value=workType.getResultName();
														}
													}else if("runStatus".equalsIgnoreCase(column)||"runStatusName".equalsIgnoreCase(column)){
														resolutionMode="6";
														value=runStatusName;
														rawValue=runStatus;
													}
													
													ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,1);
													protocolItemResolutionDataList.add(protocolItemResolutionData);
													existData=true;
												}
												break;
											}
										}
									}else{
										//日累计计算
										ModbusProtocolConfig.Items sourceItem=MemoryDataManagerTask.getProtocolItemByMappingColumn(protocol, column.toUpperCase().replace("_TOTAL", ""));
										if(sourceItem!=null){
											unit=sourceItem.getUnit();
										}
										
										if(dailyTotalCalItemMap.size()>0){
											String dailyTotalDatasql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
													+ "t.itemcolumn,t.itemname,"
													+ "t.totalvalue,t.todayvalue "
													+ " from TBL_DAILYTOTALCALCULATE_LATEST t "
													+ " where t.deviceid="+deviceId;
											List<?> dailyTotalDatasList = this.findCallSql(dailyTotalDatasql);
											for(int j=0;j<dailyTotalDatasList.size();j++){
												Object[] dailyTotalDataObj=(Object[]) dailyTotalDatasList.get(j);
												if((dailyTotalDataObj[1]+"").equalsIgnoreCase(column)){
													if(dailyTotalCalItemMap.containsKey( (dailyTotalDataObj[1]+"").toUpperCase() )){
														displayItem.setItemSourceName(dailyTotalCalItemMap.get( (dailyTotalDataObj[1]+"").toUpperCase() ).getItemSourceName());
														displayItem.setItemSourceCode(dailyTotalCalItemMap.get( (dailyTotalDataObj[1]+"").toUpperCase() ).getItemSourceCode());
														
														ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, displayItem.getItemSourceName());
														if(item!=null){
															unit=item.getUnit();
														}
														
														ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(
																dailyTotalDataObj[2]+"",
																dailyTotalDataObj[2]+"",
																dailyTotalDataObj[4]+"",
																dailyTotalDataObj[4]+"",
																"",
																dailyTotalDataObj[1]+"",
																"",
																"",
																"",
																unit,
																displayItem.getRealtimeSort(),
																1);
														protocolItemResolutionDataList.add(protocolItemResolutionData);
														existData=true;
													}
												}
											}
										}
									}
								}else if(displayItem.getType()==3){//录入项
									CalItem calItem=MemoryDataManagerTask.getInputItemByCode(column, language);
									if(calItem!=null){
										unit=calItem.getUnit();
									}
									
									if(displayInputItemList.size()>0){
										String productionData=(obj[obj.length-1]+"").replaceAll("null", "");
										if(StringManagerUtils.stringToInteger(calculateType)==1){
											type = new TypeToken<SRPCalculateRequestData>() {}.getType();
											SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
											
											if(srpProductionData!=null){
												if("CrudeOilDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
													value=srpProductionData.getFluidPVT().getCrudeOilDensity()+"";
												}else if("WaterDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
													value=srpProductionData.getFluidPVT().getWaterDensity()+"";
												}else if("NaturalGasRelativeDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
													value=srpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
												}else if("SaturationPressure".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
													value=srpProductionData.getFluidPVT().getSaturationPressure()+"";
												}else if("ReservoirDepth".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
													value=srpProductionData.getReservoir().getDepth()+"";
													if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
														columnName=languageResourceMap.get("reservoirDepth_cbm");
													}
												}else if("ReservoirTemperature".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
													value=srpProductionData.getReservoir().getTemperature()+"";
													if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
														columnName=languageResourceMap.get("reservoirTemperature_cbm");
													}
												}else if("TubingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
													value=srpProductionData.getProduction().getTubingPressure()+"";
													if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
														columnName=languageResourceMap.get("tubingPressure_cbm");
													}
												}else if("CasingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
													value=srpProductionData.getProduction().getCasingPressure()+"";
												}else if("WellHeadTemperature".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
													value=srpProductionData.getProduction().getWellHeadTemperature()+"";
												}else if("WaterCut".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
													value=srpProductionData.getProduction().getWaterCut()+"";
												}else if("ProductionGasOilRatio".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
													value=srpProductionData.getProduction().getProductionGasOilRatio()+"";
												}else if("ProducingfluidLevel".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
													value=srpProductionData.getProduction().getProducingfluidLevel()+"";
												}else if("PumpSettingDepth".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
													value=srpProductionData.getProduction().getPumpSettingDepth()+"";
												}else if("PumpBoreDiameter".equalsIgnoreCase(column) && srpProductionData.getPump()!=null ){
													value=srpProductionData.getPump().getPumpBoreDiameter()*1000+"";
												}else if("LevelCorrectValue".equalsIgnoreCase(column) && srpProductionData.getManualIntervention()!=null ){
													value=srpProductionData.getManualIntervention().getLevelCorrectValue()+"";
												}
											}
											rawValue=value;
											rawColumnName=columnName;
											ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,3);
											protocolItemResolutionDataList.add(protocolItemResolutionData);
											existData=true;
										}else if(StringManagerUtils.stringToInteger(calculateType)==2){
											type = new TypeToken<PCPCalculateRequestData>() {}.getType();
											PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
											
											if(pcpProductionData!=null){
												if("CrudeOilDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
													value=pcpProductionData.getFluidPVT().getCrudeOilDensity()+"";
												}else if("WaterDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
													value=pcpProductionData.getFluidPVT().getWaterDensity()+"";
												}else if("NaturalGasRelativeDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
													value=pcpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
												}else if("SaturationPressure".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
													value=pcpProductionData.getFluidPVT().getSaturationPressure()+"";
												}else if("ReservoirDepth".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
													value=pcpProductionData.getReservoir().getDepth()+"";
													if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
														columnName=languageResourceMap.get("reservoirDepth_cbm");
													}
												}else if("ReservoirTemperature".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
													value=pcpProductionData.getReservoir().getTemperature()+"";
													if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
														columnName=languageResourceMap.get("reservoirTemperature_cbm");
													}
												}else if("TubingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
													value=pcpProductionData.getProduction().getTubingPressure()+"";
													if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
														columnName=languageResourceMap.get("tubingPressure_cbm");
													}
												}else if("CasingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
													value=pcpProductionData.getProduction().getCasingPressure()+"";
												}else if("WellHeadTemperature".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
													value=pcpProductionData.getProduction().getWellHeadTemperature()+"";
												}else if("WaterCut".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
													value=pcpProductionData.getProduction().getWaterCut()+"";
												}else if("ProductionGasOilRatio".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
													value=pcpProductionData.getProduction().getProductionGasOilRatio()+"";
												}else if("ProducingfluidLevel".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
													value=pcpProductionData.getProduction().getProducingfluidLevel()+"";
												}else if("PumpSettingDepth".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
													value=pcpProductionData.getProduction().getPumpSettingDepth()+"";
												}
											}
											rawValue=value;
											rawColumnName=columnName;
											ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,3);
											protocolItemResolutionDataList.add(protocolItemResolutionData);
											existData=true;
										}
									}
								}
								if(!existData){
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
									protocolItemResolutionDataList.add(protocolItemResolutionData);
								}
							}
						}
						
						//排序
						Collections.sort(protocolItemResolutionDataList);
						//插入排序间隔的空项
						List<ProtocolItemResolutionData> finalProtocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
						for(int j=0;j<protocolItemResolutionDataList.size();j++){
							if(j>0&&protocolItemResolutionDataList.get(j).getSort()<9999
								&&protocolItemResolutionDataList.get(j).getSort()-protocolItemResolutionDataList.get(j-1).getSort()>1
							){
								int def=protocolItemResolutionDataList.get(j).getSort()-protocolItemResolutionDataList.get(j-1).getSort();
								for(int k=1;k<def;k++){
									finalProtocolItemResolutionDataList.add(new ProtocolItemResolutionData());
								}
							}
							finalProtocolItemResolutionDataList.add(protocolItemResolutionDataList.get(j));
						}
						
						
						if(finalProtocolItemResolutionDataList.size()%items==0){
							row=finalProtocolItemResolutionDataList.size()/items+1;
						}else{
							row=finalProtocolItemResolutionDataList.size()/items+2;
						}
						result_json.append("{\"name1\":\""+(obj[1]+":"+acqTime+" "+obj[4])+"\"},");
						
						for(int j=1;j<row;j++){
							//记录每一行的详细信息
							result_json.append("{");
							for(int k=0;k<items;k++){
								int index=items*(j-1)+k;
								String columnName="";
								String value="";
								String rawValue="";
								String addr="";
								String column="";
								String columnDataType="";
								String resolutionMode="";
								String bitIndex="";
								String unit="";
								
								String realtimeColor="";
								String realtimeBgColor="";
								String historyColor="";
								String historyBgColor="";
								
								int dataType=0;
								
								int alarmLevel=0;
								if(index<finalProtocolItemResolutionDataList.size()
										&&StringManagerUtils.isNotNull(finalProtocolItemResolutionDataList.get(index).getColumnName())
										){
									columnName=finalProtocolItemResolutionDataList.get(index).getColumnName();
									value=finalProtocolItemResolutionDataList.get(index).getValue();
									unit=finalProtocolItemResolutionDataList.get(index).getUnit();
									rawValue=finalProtocolItemResolutionDataList.get(index).getRawValue();
									addr=finalProtocolItemResolutionDataList.get(index).getAddr();
									column=finalProtocolItemResolutionDataList.get(index).getColumn();
									columnDataType=finalProtocolItemResolutionDataList.get(index).getColumnDataType();
									resolutionMode=finalProtocolItemResolutionDataList.get(index).getResolutionMode();
									bitIndex=finalProtocolItemResolutionDataList.get(index).getBitIndex();
									dataType=finalProtocolItemResolutionDataList.get(index).getType();
									
									for(DisplayInstanceOwnItem.DisplayItem displayItem:displayInstanceOwnItem.getItemList()){
										if(dataType==0){//采控项
											if("0".equalsIgnoreCase(resolutionMode) 
													&& displayItem.getType()==dataType
													&& displayItem.getItemCode().equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getColumn())  
													&& displayItem.getBitIndex()==StringManagerUtils.stringToInteger(finalProtocolItemResolutionDataList.get(index).getBitIndex())
													){//开关量
												realtimeColor=displayItem.getRealtimeColor();
												realtimeBgColor=displayItem.getRealtimeBgColor();
												historyColor=displayItem.getHistoryColor();
												historyBgColor=displayItem.getHistoryBgColor();
												break;
											}else if( ("1".equalsIgnoreCase(resolutionMode) || "2".equalsIgnoreCase(resolutionMode) )
													&& displayItem.getType()==dataType
													&& displayItem.getItemCode().equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getColumn())  
													){
												realtimeColor=displayItem.getRealtimeColor();
												realtimeBgColor=displayItem.getRealtimeBgColor();
												historyColor=displayItem.getHistoryColor();
												historyBgColor=displayItem.getHistoryBgColor();
												break;
											}
										}else if(dataType==1 || dataType==3 || dataType==5){//计算项和录入项
											if(displayItem.getType()==dataType
													&& displayItem.getItemCode().equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getColumn())  
													){
												realtimeColor=displayItem.getRealtimeColor();
												realtimeBgColor=displayItem.getRealtimeBgColor();
												historyColor=displayItem.getHistoryColor();
												historyBgColor=displayItem.getHistoryBgColor();
												break;
											}
										}
									}
									
									if(alarmInfoList!=null){
										for(KeyValue keyValue:alarmInfoList){
											if(finalProtocolItemResolutionDataList.get(index).getColumn().equalsIgnoreCase(keyValue.getKey())){
												alarmLevel=StringManagerUtils.stringToInteger(keyValue.getValue());
												break;
											}
										}
									}
									
//									if(alarmInstanceOwnItem!=null && alarmDataMap.size()>0){
//										String key=(finalProtocolItemResolutionDataList.get(index).getColumn()+"_"
//												+acqTime+"_"
//												+finalProtocolItemResolutionDataList.get(index).getResolutionMode()).toUpperCase();
//										if(alarmDataMap.containsKey(key) && alarmDataMap.get(key)>0){
//											alarmLevel=alarmDataMap.get(key);
//										}
//									}
									
								}
								
								if(StringManagerUtils.isNotNull(columnName) && StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))){
									result_json.append("\"name"+(k+1)+"\":\""+(columnName+"("+unit+")")+"\",");
								}else{
									result_json.append("\"name"+(k+1)+"\":\""+columnName+"\",");
								}
								
								result_json.append("\"value"+(k+1)+"\":\""+value+"\",");
								
								info_json.append("{\"row\":"+j+",\"col\":"+k+",\"addr\":\""+addr+"\","
										+ "\"columnName\":\""+columnName+"\","
										+ "\"column\":\""+column+"\","
										+ "\"bitIndex\":\""+bitIndex+"\","
										+ "\"value\":\""+value+"\","
										+ "\"columnDataType\":\""+columnDataType+"\","
										+ "\"resolutionMode\":\""+resolutionMode+"\","
										+ "\"realtimeColor\":\""+realtimeColor+"\","
										+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
										+ "\"historyColor\":\""+historyColor+"\","
										+ "\"historyBgColor\":\""+historyBgColor+"\","
										+ "\"type\":\""+dataType+"\","
										+ "\"alarmLevel\":"+alarmLevel+"},");
							}
							if(result_json.toString().endsWith(",")){
								result_json.deleteCharAt(result_json.length() - 1);
							}
							result_json.append("},");
						}
						if(result_json.toString().endsWith(",")){
							result_json.deleteCharAt(result_json.length() - 1);
						}
					}
					
				}
			}
			if(info_json.toString().endsWith(",")){
				info_json.deleteCharAt(info_json.length() - 1);
			}
			info_json.append("]");
			result_json.append("]");
			result_json.append(",\"CellInfo\":"+info_json);
			result_json.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle));
			result_json.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result_json.toString().replaceAll("null", "");
	}
	
	public boolean exportDeviceRealTimeMonitoringData(User user,HttpServletResponse response,String deviceId,String deviceName,String calculateType){
		ConfigFile configFile=Config.getInstance().configFile;
		String language=user.getLanguageName();
		int userNo=user.getUserNo();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		String title=languageResourceMap.get("dynamicData")+ "-"+ deviceName;
		String fileName =title+ "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		
		List<List<Object>> sheetDataList = new ArrayList<>();
		try{
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			int items=3;
			AcqInstanceOwnItem acqInstanceOwnItem=null;
			DisplayInstanceOwnItem displayInstanceOwnItem=null;
			
			List<CalItem> calItemList=null;
			List<CalItem> inputItemList=null;
			UserInfo userInfo=null;
			String tableName="tbl_acqdata_latest";
			String calAndInputTableName="";
			String deviceTableName="tbl_device";
			String acqInstanceCode="";
			String displayInstanceCode="";
			
			DeviceInfo deviceInfo=null;
			
			if(StringManagerUtils.stringToInteger(calculateType)==1){
				calAndInputTableName="tbl_srpacqdata_latest";
			}else if(StringManagerUtils.stringToInteger(calculateType)==2){
				calAndInputTableName="tbl_pcpacqdata_latest";
			}
			
			
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			int timeEfficiencyZoom=1;
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
				timeEfficiencyZoom=100;
			}
			
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
			Map<String,DataMapping> protocolExtendedFieldColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(1);

			deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
			userInfo=MemoryDataManagerTask.getUserInfoByNo(userNo+"");
			if(deviceInfo!=null){
				displayInstanceCode=deviceInfo.getDisplayInstanceCode();
				acqInstanceCode=deviceInfo.getInstanceCode();
			}
			
			acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(acqInstanceCode);
			displayInstanceOwnItem=MemoryDataManagerTask.getDisplayInstanceOwnItemByCode(displayInstanceCode);
			ModbusProtocolConfig.Protocol protocol=null;
			if(displayInstanceOwnItem!=null){
				protocol=MemoryDataManagerTask.getProtocolByCode(displayInstanceOwnItem.getProtocolCode());
			}
			
			
			if(StringManagerUtils.stringToInteger(calculateType)==1){
				calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
				inputItemList=MemoryDataManagerTask.getSRPInputItem(language);
			}else if(StringManagerUtils.stringToInteger(calculateType)==2){
				calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
				inputItemList=MemoryDataManagerTask.getPCPInputItem(language);
			}else{
				calItemList=MemoryDataManagerTask.getAcqCalculateItem(language);
				inputItemList=new ArrayList<>();
			}
			
			String head="",field="";
			
			for(int i=1;i<=items;i++){
				head+=languageResourceMap.get("name")+","+languageResourceMap.get("variable");
				field+="name"+i+","+"value"+i;
				if(i<items){
					head+=",";
					field+=",";
				}
			}
			
			
			StringBuffer result_json = new StringBuffer();
			
			result_json.append("[");
			if(displayInstanceOwnItem!=null&&userInfo!=null){
				if(protocol!=null){
					List<ModbusProtocolConfig.Items> protocolItems=new ArrayList<ModbusProtocolConfig.Items>();
					List<ModbusProtocolConfig.ExtendedField> protocolExtendedFields=new ArrayList<ModbusProtocolConfig.ExtendedField>();
					List<CalItem> displayCalItemList=new ArrayList<CalItem>();
					List<CalItem> displayInputItemList=new ArrayList<CalItem>();
					Map<String,DisplayInstanceOwnItem.DisplayItem> dailyTotalCalItemMap=new HashMap<>();
					List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
					WorkType workType=null;
					for(int j=0;j<protocol.getItems().size();j++){
						if((!"w".equalsIgnoreCase(protocol.getItems().get(j).getRWType())) 
								&& (StringManagerUtils.existDisplayItem(displayInstanceOwnItem.getItemList(), protocol.getItems().get(j).getTitle(), false))){
							for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
								if(displayInstanceOwnItem.getItemList().get(k).getType()==0 
										&& displayInstanceOwnItem.getItemList().get(k).getRealtimeData()==1
										&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
										&& protocol.getItems().get(j).getTitle().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemName())
										){
									protocolItems.add(protocol.getItems().get(j));
									break;
								}
							}
						}
					}
					
					//拓展字段
					if(protocol.getExtendedFields()!=null && protocol.getExtendedFields().size()>0){
						for(int j=0;j<protocol.getExtendedFields().size();j++){
							DataMapping dataMapping=protocolExtendedFieldColumnByTitleMap.get(protocol.getExtendedFields().get(j).getTitle());
							if(dataMapping!=null){
								String extendedField=dataMapping.getMappingColumn();
								for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
									if( extendedField.equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode()) ){
										protocolExtendedFields.add(protocol.getExtendedFields().get(j));
										break;
									}
								}
							}
						}
					}
					
					//计算项
					if(calItemList!=null){
						for(CalItem calItem:calItemList){
							if(StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), calItem.getCode(), false,0,1)){
								for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
									if(displayInstanceOwnItem.getItemList().get(k).getType()==1
											&& displayInstanceOwnItem.getItemList().get(k).getRealtimeData()==1
											&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
											&& calItem.getCode().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode())){
										displayCalItemList.add(calItem);
										break;
									}
								}
								
							}
						}
					}
					//日汇总计算项
					if(acqInstanceOwnItem!=null){
						for(AcqInstanceOwnItem.AcqItem acqItem:acqInstanceOwnItem.getItemList()){
							if(acqItem.getDailyTotalCalculate()==1 && StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), (acqItem.getItemCode()+"_total").toUpperCase(), false,0,1)){
								for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
									if(displayInstanceOwnItem.getItemList().get(k).getType()==1
											&& displayInstanceOwnItem.getItemList().get(k).getRealtimeData()==1
											&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
											&& (acqItem.getItemCode()+"_total").equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode())){
										displayInstanceOwnItem.getItemList().get(k).setItemSourceName(acqItem.getItemName());
										displayInstanceOwnItem.getItemList().get(k).setItemSourceCode(acqItem.getItemCode());
										dailyTotalCalItemMap.put(displayInstanceOwnItem.getItemList().get(k).getItemCode().toUpperCase(), displayInstanceOwnItem.getItemList().get(k));
										break;
									}
								}
								
							}
						}
					}
					//录入项
					if(inputItemList!=null){
						for(CalItem calItem:inputItemList){
							if(StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), calItem.getCode(), false,0,1)){
								for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
									if(displayInstanceOwnItem.getItemList().get(k).getType()==3
											&& displayInstanceOwnItem.getItemList().get(k).getRealtimeData()==1
											&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
											&& calItem.getCode().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode())){
										displayInputItemList.add(calItem);
										break;
									}
								}
								
							}
						}
					}
					
					String sql="select t.id,t.devicename,to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'), "
							+ "t2.commstatus,decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,decode(t2.commstatus,1,0,100) as commAlarmLevel,"
							+ " t2.runStatus as runStatusCalValue,decode(t2.commstatus,0,'"+languageResourceMap.get("offline")+"',null,'"+languageResourceMap.get("offline")+"',2,'"+languageResourceMap.get("goOnline")+"',decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"')) as runStatusName,decode(t2.runstatus,1,0,100) as runAlarmLevel,"
							+ "t2.acqdata ";
					
					for(int i=0;i<displayCalItemList.size();i++){
						String column=displayCalItemList.get(i).getCode();
						if(StringManagerUtils.stringToInteger(calculateType)>0){
							if("resultName".equalsIgnoreCase(column)){
								column="resultCode";
							}else if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
								column=column+"*"+timeEfficiencyZoom+" as "+column;
							}else if("runstatusName".equalsIgnoreCase(column)){
								column="runstatus";
							}
							sql+=",t3."+column;
						}else{
							if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
								column=column+"*"+timeEfficiencyZoom+" as "+column;
							}else if("runstatusName".equalsIgnoreCase(column)){
								column="runstatus";
							}
							sql+=",t2."+column;
						}
					}
					
					if(StringManagerUtils.stringToInteger(calculateType)>0){
						if(displayInputItemList.size()>0){
							sql+=",t3.productiondata";
						}
					}
					
					sql+= " from "+deviceTableName+" t "
							+ " left outer join "+tableName+" t2 on t2.deviceid=t.id";
					if(StringManagerUtils.isNotNull(calAndInputTableName)&&(displayCalItemList.size()>0 || inputItemList.size()>0)){
						sql+=" left outer join "+calAndInputTableName+" t3 on t3.deviceid=t.id";
					}
					
					sql+= " where  t.id="+deviceId;
					List<?> list = this.findCallSql(sql);
					if(list.size()>0){
						int row=1;
						Object[] obj=(Object[]) list.get(0);
						String runStatus=obj[6]+"";
						String runStatusName=obj[7]+"";
						String acqData=StringManagerUtils.CLOBObjectToString(obj[9]);
						
						type = new TypeToken<List<KeyValue>>() {}.getType();
						List<KeyValue> acqDataList=gson.fromJson(acqData, type);
						
						for(DisplayInstanceOwnItem.DisplayItem displayItem:displayInstanceOwnItem.getItemList()){
							if(displayItem.getType()!=2 
									&& displayItem.getRealtimeData()==1
									&& displayItem.getShowLevel()>=userInfo.getRoleShowLevel()){
								String column=displayItem.getItemCode();
								String columnName=displayItem.getItemName();
								String rawColumnName=columnName;
								String value="";
								String rawValue="";
								String addr="";
								String columnDataType="";
								String resolutionMode="";
								String bitIndex=displayItem.getBitIndex()+"";
								String unit="";
								int sort=displayItem.getRealtimeSort();
								int switchingValueShowType=displayItem.getSwitchingValueShowType();
								boolean existData=false;
								if(displayItem.getType()==0){//采集项
									ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, rawColumnName);
									if(item!=null){
										addr=item.getAddr()+"";
										columnDataType=item.getIFDataType();
										resolutionMode=item.getResolutionMode()+"";
										unit=item.getUnit();
									}
									if(protocolItems.size()>0 && acqDataList!=null){
										for(KeyValue keyValue:acqDataList){
											if(column.equalsIgnoreCase(keyValue.getKey())){
												value=keyValue.getValue();
												rawValue=value;
												if(item!=null){
													if("int".equalsIgnoreCase(item.getIFDataType()) || "uint".equalsIgnoreCase(item.getIFDataType()) || item.getIFDataType().contains("int")
															||"float32".equalsIgnoreCase(item.getIFDataType())
															||"float64".equalsIgnoreCase(item.getIFDataType())){
														if(value.toUpperCase().contains("E")){
										                 	value=StringManagerUtils.scientificNotationToNormal(value);
										                 }
													}
													
													if(item.getResolutionMode()==1 || item.getResolutionMode()==2){//如果是枚举量
														if(StringManagerUtils.isNotNull(value)&&item.getMeaning()!=null&&item.getMeaning().size()>0){
															for(int l=0;l<item.getMeaning().size();l++){
																if(StringManagerUtils.stringToFloat(value)==(item.getMeaning().get(l).getValue())){
																	value=item.getMeaning().get(l).getMeaning();
																	break;
																}
															}
														}
														ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
														protocolItemResolutionDataList.add(protocolItemResolutionData);
														existData=true;
													}else if(item.getResolutionMode()==0){//如果是开关量
														ModbusProtocolConfig.ItemsMeaning itemsMeaning=MemoryDataManagerTask.getProtocolItemMeaning(item, bitIndex);
														if(itemsMeaning!=null && StringManagerUtils.isNotNull(value)){
															String[] valueArr=value.split(",");
															columnName=StringManagerUtils.isNotNull(itemsMeaning.getMeaning())?itemsMeaning.getMeaning():(languageResourceMap.get("bit")+displayItem.getBitIndex());
															String status0=StringManagerUtils.isNotNull(itemsMeaning.getStatus0())?itemsMeaning.getStatus0():"";
															String status1=StringManagerUtils.isNotNull(itemsMeaning.getStatus1())?itemsMeaning.getStatus1():"";
															if(switchingValueShowType==1){
																columnName=rawColumnName+"/"+columnName;
															}
															for(int m=0;valueArr!=null && m<valueArr.length;m++){
																if(m==itemsMeaning.getValue()){
																	if("bool".equalsIgnoreCase(columnDataType) || "boolean".equalsIgnoreCase(columnDataType)){
																		value=("true".equalsIgnoreCase(valueArr[m]) || "1".equalsIgnoreCase(valueArr[m]))?status1:status0;
																		rawValue=("true".equalsIgnoreCase(valueArr[m]) || "1".equalsIgnoreCase(valueArr[m]))?"1":"0";
																	}else{
																		value=valueArr[m];
																	}
																	ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
																	protocolItemResolutionDataList.add(protocolItemResolutionData);
																	existData=true;
																	break;
																}
															}
														}else{
															ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
															protocolItemResolutionDataList.add(protocolItemResolutionData);
															existData=true;
														}
													}else{//如果是数据量
														for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
															if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column) && displayInstanceOwnItem.getItemList().get(l).getType()!=2){
																sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
																break;
															}
														}
														ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
														protocolItemResolutionDataList.add(protocolItemResolutionData);
														existData=true;
													}
												}
												break;
											}
										}
									}
								}else if(displayItem.getType()==5){//协议拓展项
									ModbusProtocolConfig.ExtendedField extendedField=MemoryDataManagerTask.getProtocolExtendedField(protocol, rawColumnName);
									if(extendedField!=null){
										unit=extendedField.getUnit();
									}
									if(protocolExtendedFields.size()>0 && acqDataList!=null){
										for(KeyValue keyValue:acqDataList){
											if(column.equalsIgnoreCase(keyValue.getKey())){
												value=keyValue.getValue();
												rawValue=value;
												
												if(extendedField!=null){
													String extendedFieldValue=keyValue.getValue();
													ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(
															extendedField.getTitle(),
															extendedField.getTitle(),
															extendedFieldValue,
															extendedFieldValue,
															"",
															column,
															"",
															"",
															"",
															extendedField.getUnit(),
															sort,
															5);
													protocolItemResolutionDataList.add(protocolItemResolutionData);
													existData=true;
												}
												break;
											}
										}
									}
								}else if(displayItem.getType()==1){//计算项
									if(!column.toUpperCase().endsWith("_TOTAL")){
										CalItem calItem=MemoryDataManagerTask.getCalItemByCode(column, language);
										if(calItem!=null){
											unit=calItem.getUnit();
										}
										
										//计算项
										for(int i=0;i<displayCalItemList.size();i++){
											if(column.equalsIgnoreCase(displayCalItemList.get(i).getCode())){
												int index=i+10;
												if(index<obj.length){
													value=obj[index]+"";
													if(obj[index] instanceof CLOB || obj[index] instanceof Clob){
														value=StringManagerUtils.CLOBObjectToString(obj[index]);
													}
													unit=displayCalItemList.get(i).getUnit();
													
													//如果是工况
													if("resultCode".equalsIgnoreCase(column)||"resultName".equalsIgnoreCase(column)){
														workType=MemoryDataManagerTask.getWorkTypeByCode(value,language);
														if(workType!=null){
															value=workType.getResultName();
														}
													}else if("runStatus".equalsIgnoreCase(column)||"runStatusName".equalsIgnoreCase(column)){
														value=runStatusName;
														rawValue=runStatus;
													}
													
													ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,1);
													protocolItemResolutionDataList.add(protocolItemResolutionData);
													existData=true;
												}
												break;
											}
										}
									}else{
										//日累计计算
										ModbusProtocolConfig.Items sourceItem=MemoryDataManagerTask.getProtocolItemByMappingColumn(protocol, column.toUpperCase().replace("_TOTAL", ""));
										if(sourceItem!=null){
											unit=sourceItem.getUnit();
										}
										
										if(dailyTotalCalItemMap.size()>0){
											String dailyTotalDatasql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
													+ "t.itemcolumn,t.itemname,"
													+ "t.totalvalue,t.todayvalue "
													+ " from TBL_DAILYTOTALCALCULATE_LATEST t "
													+ " where t.deviceid="+deviceId;
											List<?> dailyTotalDatasList = this.findCallSql(dailyTotalDatasql);
											for(int j=0;j<dailyTotalDatasList.size();j++){
												Object[] dailyTotalDataObj=(Object[]) dailyTotalDatasList.get(j);
												if((dailyTotalDataObj[1]+"").equalsIgnoreCase(column)){
													if(dailyTotalCalItemMap.containsKey( (dailyTotalDataObj[1]+"").toUpperCase() )){
														displayItem.setItemSourceName(dailyTotalCalItemMap.get( (dailyTotalDataObj[1]+"").toUpperCase() ).getItemSourceName());
														displayItem.setItemSourceCode(dailyTotalCalItemMap.get( (dailyTotalDataObj[1]+"").toUpperCase() ).getItemSourceCode());
														
														ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, displayItem.getItemSourceName());
														if(item!=null){
															unit=item.getUnit();
														}
														
														ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(
																dailyTotalDataObj[2]+"",
																dailyTotalDataObj[2]+"",
																dailyTotalDataObj[4]+"",
																dailyTotalDataObj[4]+"",
																"",
																dailyTotalDataObj[1]+"",
																"",
																"",
																"",
																unit,
																displayItem.getRealtimeSort(),
																1);
														protocolItemResolutionDataList.add(protocolItemResolutionData);
														existData=true;
													}
												}
											}
										}
									}
								}else if(displayItem.getType()==3){//录入项
									CalItem calItem=MemoryDataManagerTask.getInputItemByCode(column, language);
									if(calItem!=null){
										unit=calItem.getUnit();
									}
									
									if(displayInputItemList.size()>0){
										String productionData=(obj[obj.length-1]+"").replaceAll("null", "");
										if(StringManagerUtils.stringToInteger(calculateType)==1){
											type = new TypeToken<SRPCalculateRequestData>() {}.getType();
											SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
											
											if(srpProductionData!=null){
												if("CrudeOilDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
													value=srpProductionData.getFluidPVT().getCrudeOilDensity()+"";
												}else if("WaterDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
													value=srpProductionData.getFluidPVT().getWaterDensity()+"";
												}else if("NaturalGasRelativeDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
													value=srpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
												}else if("SaturationPressure".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
													value=srpProductionData.getFluidPVT().getSaturationPressure()+"";
												}else if("ReservoirDepth".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
													value=srpProductionData.getReservoir().getDepth()+"";
													if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
														columnName=languageResourceMap.get("reservoirDepth_cbm");
													}
												}else if("ReservoirTemperature".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
													value=srpProductionData.getReservoir().getTemperature()+"";
													if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
														columnName=languageResourceMap.get("reservoirTemperature_cbm");
													}
												}else if("TubingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
													value=srpProductionData.getProduction().getTubingPressure()+"";
													if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
														columnName=languageResourceMap.get("tubingPressure_cbm");
													}
												}else if("CasingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
													value=srpProductionData.getProduction().getCasingPressure()+"";
												}else if("WellHeadTemperature".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
													value=srpProductionData.getProduction().getWellHeadTemperature()+"";
												}else if("WaterCut".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
													value=srpProductionData.getProduction().getWaterCut()+"";
												}else if("ProductionGasOilRatio".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
													value=srpProductionData.getProduction().getProductionGasOilRatio()+"";
												}else if("ProducingfluidLevel".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
													value=srpProductionData.getProduction().getProducingfluidLevel()+"";
												}else if("PumpSettingDepth".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
													value=srpProductionData.getProduction().getPumpSettingDepth()+"";
												}else if("PumpBoreDiameter".equalsIgnoreCase(column) && srpProductionData.getPump()!=null ){
													value=srpProductionData.getPump().getPumpBoreDiameter()*1000+"";
												}else if("LevelCorrectValue".equalsIgnoreCase(column) && srpProductionData.getManualIntervention()!=null ){
													value=srpProductionData.getManualIntervention().getLevelCorrectValue()+"";
												}
											}
											rawValue=value;
											rawColumnName=columnName;
											ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,3);
											protocolItemResolutionDataList.add(protocolItemResolutionData);
											existData=true;
										}else if(StringManagerUtils.stringToInteger(calculateType)==2){
											type = new TypeToken<PCPCalculateRequestData>() {}.getType();
											PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
											
											if(pcpProductionData!=null){
												if("CrudeOilDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
													value=pcpProductionData.getFluidPVT().getCrudeOilDensity()+"";
												}else if("WaterDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
													value=pcpProductionData.getFluidPVT().getWaterDensity()+"";
												}else if("NaturalGasRelativeDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
													value=pcpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
												}else if("SaturationPressure".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
													value=pcpProductionData.getFluidPVT().getSaturationPressure()+"";
												}else if("ReservoirDepth".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
													value=pcpProductionData.getReservoir().getDepth()+"";
													if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
														columnName=languageResourceMap.get("reservoirDepth_cbm");
													}
												}else if("ReservoirTemperature".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
													value=pcpProductionData.getReservoir().getTemperature()+"";
													if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
														columnName=languageResourceMap.get("reservoirTemperature_cbm");
													}
												}else if("TubingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
													value=pcpProductionData.getProduction().getTubingPressure()+"";
													if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
														columnName=languageResourceMap.get("tubingPressure_cbm");
													}
												}else if("CasingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
													value=pcpProductionData.getProduction().getCasingPressure()+"";
												}else if("WellHeadTemperature".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
													value=pcpProductionData.getProduction().getWellHeadTemperature()+"";
												}else if("WaterCut".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
													value=pcpProductionData.getProduction().getWaterCut()+"";
												}else if("ProductionGasOilRatio".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
													value=pcpProductionData.getProduction().getProductionGasOilRatio()+"";
												}else if("ProducingfluidLevel".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
													value=pcpProductionData.getProduction().getProducingfluidLevel()+"";
												}else if("PumpSettingDepth".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
													value=pcpProductionData.getProduction().getPumpSettingDepth()+"";
												}
											}
											rawValue=value;
											rawColumnName=columnName;
											ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,3);
											protocolItemResolutionDataList.add(protocolItemResolutionData);
											existData=true;
										}
									}
								}
								if(!existData){
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
									protocolItemResolutionDataList.add(protocolItemResolutionData);
								}
							}
						}
						
						//排序
						Collections.sort(protocolItemResolutionDataList);
						//插入排序间隔的空项
						List<ProtocolItemResolutionData> finalProtocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
						for(int j=0;j<protocolItemResolutionDataList.size();j++){
							if(j>0&&protocolItemResolutionDataList.get(j).getSort()<9999
								&&protocolItemResolutionDataList.get(j).getSort()-protocolItemResolutionDataList.get(j-1).getSort()>1
							){
								int def=protocolItemResolutionDataList.get(j).getSort()-protocolItemResolutionDataList.get(j-1).getSort();
								for(int k=1;k<def;k++){
									finalProtocolItemResolutionDataList.add(new ProtocolItemResolutionData());
								}
							}
							finalProtocolItemResolutionDataList.add(protocolItemResolutionDataList.get(j));
						}
						
						
						if(finalProtocolItemResolutionDataList.size()%items==0){
							row=finalProtocolItemResolutionDataList.size()/items+1;
						}else{
							row=finalProtocolItemResolutionDataList.size()/items+2;
						}
						result_json.append("{\"name1\":\""+(obj[1]+":"+obj[2]+" "+obj[4])+"\"},");
						
						for(int j=1;j<row;j++){
							//记录每一行的详细信息
							result_json.append("{");
							for(int k=0;k<items;k++){
								int index=items*(j-1)+k;
								String columnName="";
								String value="";
								String rawValue="";
								String addr="";
								String column="";
								String columnDataType="";
								String resolutionMode="";
								String bitIndex="";
								String unit="";
								
								String realtimeColor="";
								String realtimeBgColor="";
								String historyColor="";
								String historyBgColor="";
								
								int dataType=0;
								
								int alarmLevel=0;
								if(index<finalProtocolItemResolutionDataList.size()
										&&StringManagerUtils.isNotNull(finalProtocolItemResolutionDataList.get(index).getColumnName())
										){
									columnName=finalProtocolItemResolutionDataList.get(index).getColumnName();
									value=finalProtocolItemResolutionDataList.get(index).getValue();
									unit=finalProtocolItemResolutionDataList.get(index).getUnit();
									rawValue=finalProtocolItemResolutionDataList.get(index).getRawValue();
									addr=finalProtocolItemResolutionDataList.get(index).getAddr();
									column=finalProtocolItemResolutionDataList.get(index).getColumn();
									columnDataType=finalProtocolItemResolutionDataList.get(index).getColumnDataType();
									resolutionMode=finalProtocolItemResolutionDataList.get(index).getResolutionMode();
									bitIndex=finalProtocolItemResolutionDataList.get(index).getBitIndex();
									dataType=finalProtocolItemResolutionDataList.get(index).getType();
									
									for(DisplayInstanceOwnItem.DisplayItem displayItem:displayInstanceOwnItem.getItemList()){
										if(dataType==0){//采控项
											if("0".equalsIgnoreCase(resolutionMode) 
													&& displayItem.getType()==dataType
													&& displayItem.getItemCode().equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getColumn())  
													&& displayItem.getBitIndex()==StringManagerUtils.stringToInteger(finalProtocolItemResolutionDataList.get(index).getBitIndex())
													){//开关量
												realtimeColor=displayItem.getRealtimeColor();
												realtimeBgColor=displayItem.getRealtimeBgColor();
												historyColor=displayItem.getHistoryColor();
												historyBgColor=displayItem.getHistoryBgColor();
												break;
											}else if( ("1".equalsIgnoreCase(resolutionMode) || "2".equalsIgnoreCase(resolutionMode) )
													&& displayItem.getType()==dataType
													&& displayItem.getItemCode().equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getColumn())  
													){
												realtimeColor=displayItem.getRealtimeColor();
												realtimeBgColor=displayItem.getRealtimeBgColor();
												historyColor=displayItem.getHistoryColor();
												historyBgColor=displayItem.getHistoryBgColor();
												break;
											}
										}else if(dataType==1 || dataType==3 || dataType==5){//计算项和录入项
											if(displayItem.getType()==dataType
													&& displayItem.getItemCode().equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getColumn())  
													){
												realtimeColor=displayItem.getRealtimeColor();
												realtimeBgColor=displayItem.getRealtimeBgColor();
												historyColor=displayItem.getHistoryColor();
												historyBgColor=displayItem.getHistoryBgColor();
												break;
											}
										}
									}
									
								}
								
								if(StringManagerUtils.isNotNull(columnName) && StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))){
									result_json.append("\"name"+(k+1)+"\":\""+(columnName+"("+unit+")")+"\",");
								}else{
									result_json.append("\"name"+(k+1)+"\":\""+columnName+"\",");
								}
								
								result_json.append("\"value"+(k+1)+"\":\""+value+"\",");
								
							}
							if(result_json.toString().endsWith(",")){
								result_json.deleteCharAt(result_json.length() - 1);
							}
							result_json.append("},");
						}
						if(result_json.toString().endsWith(",")){
							result_json.deleteCharAt(result_json.length() - 1);
						}
					}
					
				}
			}
			result_json.append("]");
			List<String> columnList=new ArrayList<>(Arrays.asList(field.split(",")));
			
			List<Object> record=null;
			JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+result_json+"}");//解析数据
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			
			for(int i=0;i<jsonArray.size();i++){
				record = new ArrayList<>();
				JSONObject object = jsonArray.getJSONObject(i);
				for (int j = 0; j < columnList.size(); j++) {
					if(object.has(columnList.get(j))){
						record.add(object.getString(columnList.get(j)));
					}else{
						if(i==0){
							record.add(ExcelUtils.COLUMN_MERGE);
						}else{
							record.add("");
						}
					}
				}
				sheetDataList.add(record);
			}
			if(user!=null){
		    	try {
					saveSystemLog(user,4,languageResourceMap.get("exportFile")+":"+fileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			
		}
		ExcelUtils.exportDataWithTitleAndHead(response, fileName, title, sheetDataList, null, null,1,null,language);
		return true;
	}
	
	public String getDeviceControlData(String deviceId,String deviceName,String deviceType,User user)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String deviceTableName="tbl_device";
		String deviceInfoKey="DeviceInfo";
		
		DeviceInfo deviceInfo=null;
		UserInfo userInfo=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		String protocolCode="";
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user!=null?user.getLanguageName():"");
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
		try{
			try{
				deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
				userInfo=MemoryDataManagerTask.getUserInfoByNo(user.getUserNo()+"");
				if(deviceInfo!=null){
					displayInstanceOwnItem=MemoryDataManagerTask.getDisplayInstanceOwnItemByCode(deviceInfo.getDisplayInstanceCode());
					if(displayInstanceOwnItem!=null){
						protocolCode=displayInstanceOwnItem.getProtocolCode();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			String matrix=getUserRoleModuleMatrix(userInfo!=null?userInfo.getUserNo():0,"DeviceRealTimeMonitoring");
			
			int isControl=StringManagerUtils.getModuleRightFlagFromMatrix(matrix,2);
			
			List<String> controlItems=new ArrayList<String>();
			List<String> controlItemBitindexList=new ArrayList<String>();
			List<String> controlColumns=new ArrayList<String>();
			List<Integer> controlItemResolutionMode=new ArrayList<Integer>();
			List<Integer> controlItemSwitchingValueShowType=new ArrayList<Integer>();
			List<String> controlItemMeaningList=new ArrayList<String>();
			List<ModbusProtocolConfig.Items> controlItemList=new ArrayList<>();
			StringBuffer deviceControlList=new StringBuffer();
			Map<String,String> controlItemMeaningMap=new LinkedHashMap<>();
			deviceControlList.append("[");
			
			if(displayInstanceOwnItem!=null){
				ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByCode(protocolCode);
				if(protocol!=null){
					for(int j=0;j<displayInstanceOwnItem.getItemList().size();j++){
						if(displayInstanceOwnItem.getItemList().get(j).getType()==2 
								&& displayInstanceOwnItem.getItemList().get(j).getShowLevel()>=userInfo.getRoleShowLevel()){
							for(int k=0;k<protocol.getItems().size();k++){
								if(displayInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(protocol.getItems().get(k).getTitle())){
									if("rw".equalsIgnoreCase(protocol.getItems().get(k).getRWType())
											||"w".equalsIgnoreCase(protocol.getItems().get(k).getRWType())){
										String title=protocol.getItems().get(k).getTitle();
										String col="";
										if(loadProtocolMappingColumnByTitleMap.containsKey(title)){
											col=loadProtocolMappingColumnByTitleMap.get(title).getMappingColumn();
										}
										
										String switchingValueMeaning="[['1','"+languageResourceMap.get("switchingOpenValue")+"'],['0','"+languageResourceMap.get("switchingCloseValue")+"']]";
										String itemMeaning="";
										if(controlItemMeaningMap.containsKey(col)){
											itemMeaning=controlItemMeaningMap.get(col);
										}
										if(protocol.getItems().get(k).getResolutionMode()==0 && protocol.getItems().get(k).getMeaning()!=null && protocol.getItems().get(k).getMeaning().size()>0 ){
											int bitIndex=displayInstanceOwnItem.getItemList().get(j).getBitIndex();
											for(ModbusProtocolConfig.ItemsMeaning itemsMeaning:protocol.getItems().get(k).getMeaning()){
												if(itemsMeaning.getValue()==bitIndex){
													switchingValueMeaning="[['1','"+(StringManagerUtils.isNotNull(itemsMeaning.getStatus1())?itemsMeaning.getStatus1():languageResourceMap.get("switchingOpenValue"))+"'],['0','"+(StringManagerUtils.isNotNull(itemsMeaning.getStatus0())?itemsMeaning.getStatus0():languageResourceMap.get("switchingCloseValue"))+"']]";
													if(displayInstanceOwnItem.getItemList().get(j).getSwitchingValueShowType()==1){
														title+="/"+itemsMeaning.getMeaning();
													}else{
														title=itemsMeaning.getMeaning();
													}
													if(StringManagerUtils.isNotNull(itemsMeaning.getStatus0())){
														if(itemMeaning.endsWith("}")){
															itemMeaning+=",";
														}
														itemMeaning+="{\"bitIndex\":"+itemsMeaning.getValue()+",\"meaning\":\""+itemsMeaning.getMeaning()+"\",\"value\":0,\"status\":\""+itemsMeaning.getStatus0()+"\""+"}";
													}
													if(StringManagerUtils.isNotNull(itemsMeaning.getStatus1())){
														if(itemMeaning.endsWith("}")){
															itemMeaning+=",";
														}
														itemMeaning+="{\"bitIndex\":"+itemsMeaning.getValue()+",\"meaning\":\""+itemsMeaning.getMeaning()+"\",\"value\":1,\"status\":\""+itemsMeaning.getStatus1()+"\""+"}";
													}
													
													break;
												}
											}
										}
										
										
										if(StringManagerUtils.isNotNull(protocol.getItems().get(k).getUnit())){
											title+="("+protocol.getItems().get(k).getUnit()+")";
										}
										controlItems.add(title);
										controlItemBitindexList.add(displayInstanceOwnItem.getItemList().get(j).getBitIndex()+"");
										controlItemList.add(protocol.getItems().get(k));
										
										controlColumns.add(col);
										controlItemResolutionMode.add(protocol.getItems().get(k).getResolutionMode());
										if(protocol.getItems().get(k).getResolutionMode()==0){//开关量
											controlItemMeaningList.add(switchingValueMeaning);
											controlItemMeaningMap.put(col, itemMeaning);
										}else if(protocol.getItems().get(k).getResolutionMode()==1){//枚举量
											if(protocol.getItems().get(k).getMeaning()!=null && protocol.getItems().get(k).getMeaning().size()>0){
												StringBuffer itemMeaning_buff = new StringBuffer();
												itemMeaning_buff.append("[");
												for(int n=0;n<protocol.getItems().get(k).getMeaning().size();n++){
													itemMeaning_buff.append("["+protocol.getItems().get(k).getMeaning().get(n).getValue()+",'"+protocol.getItems().get(k).getMeaning().get(n).getMeaning()+"'],");
												}
												if(itemMeaning_buff.toString().endsWith(",")){
													itemMeaning_buff.deleteCharAt(itemMeaning_buff.length() - 1);
												}
												itemMeaning_buff.append("]");
												controlItemMeaningList.add(itemMeaning_buff.toString());
											}else{
												controlItemMeaningList.add("[]");
											}
										}else{
											controlItemMeaningList.add("[]");
										}
									}
									break;
								}
							}
						}
					}
				}
			}
			
			String tableName="tbl_acqdata_latest";
			String sql="select t2.commStatus ";
			sql+= " from "+deviceTableName+" t,"+tableName+" t2 where t.id=t2.deviceid and t.id="+deviceId;
			result_json.append("{ \"success\":true,\"isControl\":"+isControl+",");
			int commStatus=0;
			List<?> list = this.findCallSql(sql);
			if(list.size()>0){
				result_json.append("\"commStatus\":\""+list.get(0)+"\",");
				commStatus=StringManagerUtils.stringToInteger(list.get(0)+"");
			}
			
			Map<String,String> addItemMap=new HashMap<>();
			if(controlColumns.size()>0){
				for(int i=0;i<controlColumns.size();i++){
					if(!addItemMap.containsKey(controlColumns.get(i))){
						addItemMap.put(controlColumns.get(i), controlColumns.get(i));
						String controlItemMeaning=controlItemMeaningList.get(i);
						if(controlItemMeaningMap.containsKey(controlColumns.get(i))){
							controlItemMeaning="["+controlItemMeaningMap.get(controlColumns.get(i))+"]";
						}
						deviceControlList.append("{\"item\":\""+controlItemList.get(i).getTitle()+"\","
								+ "\"deviceId\":\""+deviceId+"\","
								+ "\"deviceName\":\""+deviceName+"\","
								+ "\"itemName\":\""+controlItemList.get(i).getTitle()+"\","
								+ "\"itemcode\":\""+controlColumns.get(i)+"\","
								+ "\"bitIndex\":\""+controlItemBitindexList.get(i)+"\","
								+ "\"resolutionMode\":"+controlItemResolutionMode.get(i)+","
								
								+ "\"storeDataType\":\""+controlItemList.get(i).getStoreDataType()+"\","
								+ "\"unit\":\""+controlItemList.get(i).getUnit()+"\","
								+ "\"quantity\":\""+controlItemList.get(i).getQuantity()+"\","
								
								+ "\"value\":\"\","
								+ "\"operation\":"+true+","
								+ "\"isControl\":"+isControl+","
								+ "\"showType\":1,"
								+ "\"commStatus\":"+commStatus+","
								+ "\"itemMeaning\":"+controlItemMeaning+"},");
					}
					
				}
				if(deviceControlList.toString().endsWith(",")){
					deviceControlList.deleteCharAt(deviceControlList.length() - 1);
				}
			}
			
			deviceControlList.append("]");
			
			result_json.append("\"totalRoot\":"+deviceControlList);
			result_json.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getDeviceAddInfoData(String deviceId,String deviceName,String deviceType,String calculateType,int userId,String language)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String deviceTableName="tbl_device";
		String infoTableName="tbl_deviceaddinfo";
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		String auxiliaryDeviceSql="select t3.id,t3.name,t3.manufacturer,t3.model,t3.remark "
				+ " from "+deviceTableName+" t,tbl_auxiliary2master t2,tbl_auxiliarydevice t3 "
				+ " where t.id=t2.masterid and t2.auxiliaryid=t3.id "
				+" and t3.type="+deviceType
				+" and t.id="+deviceId
				+ " order by t3.sort,t3.name";
		String deviceAddInfoSql = "select t2.id,t2.itemname,t2.itemvalue||decode(t2.itemunit,null,'','','','('||t2.itemunit||')') as itemvalue "
				+ " from "+deviceTableName+" t,"+infoTableName+" t2 "
				+ " where t.id=t2.deviceid and t.id="+deviceId
				+ " order by t2.id";
		
		String auxiliaryDeviceDetailsSql="select t4.deviceid,t4.itemname,t4.itemvalue,t3.specifictype,t4.itemcode "
				+ " from tbl_device t,tbl_auxiliary2master t2,tbl_auxiliarydevice t3,tbl_auxiliarydeviceaddinfo t4 "
				+ " where t.id=t2.masterid and t2.auxiliaryid=t3.id and t3.id=t4.deviceid "
				+ " and t3.type= "+deviceType
				+ " and t.id= "+deviceId;
		if(StringManagerUtils.stringToInteger(calculateType)!=1){
			auxiliaryDeviceDetailsSql+=" and t3.specifictype<>1";
		}
		auxiliaryDeviceDetailsSql+= " order by t4.deviceid,t4.id";
		
		List<?> auxiliaryDeviceQueryList = this.findCallSql(auxiliaryDeviceSql);
		List<?> deviceAddInfoList = this.findCallSql(deviceAddInfoSql);
		List<?> auxiliaryDeviceDetailsList = this.findCallSql(auxiliaryDeviceDetailsSql);
		
		
		StringBuffer deviceInfoDataList=new StringBuffer();
		StringBuffer auxiliaryDeviceList=new StringBuffer();
		deviceInfoDataList.append("[");
		auxiliaryDeviceList.append("[");
		
		//辅件设备
		for(int i=0;i<auxiliaryDeviceQueryList.size();i++){
			Object[] obj=(Object[]) auxiliaryDeviceQueryList.get(i);
			String details="<h style='line-height:1.5;'>"+languageResourceMap.get("manufacturer")+": "+obj[2]+"</h><br/><h style='line-height:1.5;'>"+languageResourceMap.get("model")+": "+obj[3]+"</h><br/><h style='line-height:1.5;'>"+languageResourceMap.get("remark")+": "+obj[4]+"</h>";
			for(int j=0;j<auxiliaryDeviceDetailsList.size();j++){
				Object[] detailsObj=(Object[]) auxiliaryDeviceDetailsList.get(j);
				String itemName=detailsObj[1]+"";
				String itemValue=detailsObj[2]+"";
				String specificType=detailsObj[3]+"";
				String itemCode=detailsObj[4]+"";
				if("1".equalsIgnoreCase(specificType)){
					if("stroke".equalsIgnoreCase(itemCode)){
						itemName=languageResourceMap.get("stroke");
					}else if("structureType".equalsIgnoreCase(itemCode)){
						if(StringManagerUtils.stringToInteger(itemValue)==1){
							itemValue=languageResourceMap.get("pumpingUnitStructureType1");
						}else if(StringManagerUtils.stringToInteger(itemValue)==2){
							itemValue=languageResourceMap.get("pumpingUnitStructureType2");
						}else if(StringManagerUtils.stringToInteger(itemValue)==3){
							itemValue=languageResourceMap.get("pumpingUnitStructureType3");
						}
						itemName=languageResourceMap.get("pumpingUnitStructureType");
					}else if("crankRotationDirection".equalsIgnoreCase(itemCode)){
						if("Clockwise".equalsIgnoreCase(itemValue)){
							itemValue=languageResourceMap.get("clockwise");
						}else if("Anticlockwise".equalsIgnoreCase(itemValue)){
							itemValue=languageResourceMap.get("anticlockwise");
						}
						itemName=languageResourceMap.get("crankRotationDirection");
					}else if("offsetAngleOfCrank".equalsIgnoreCase(itemCode)){
						itemName=languageResourceMap.get("offsetAngleOfCrank");
					}else if("crankGravityRadius".equalsIgnoreCase(itemCode)){
						itemName=languageResourceMap.get("crankGravityRadius");
					}else if("singleCrankWeight".equalsIgnoreCase(itemCode)){
						itemName=languageResourceMap.get("singleCrankWeight");
					}else if("singleCrankPinWeight".equalsIgnoreCase(itemCode)){
						itemName=languageResourceMap.get("singleCrankPinWeight");
					}else if("structuralUnbalance".equalsIgnoreCase(itemCode)){
						itemName=languageResourceMap.get("structuralUnbalance");
					}else if("balanceWeight".equalsIgnoreCase(itemCode)){
						itemName=languageResourceMap.get("balanceWeight");
					}
				}
				if((detailsObj[0]+"").equalsIgnoreCase(obj[0]+"")){
					details+="<br/><h style='line-height:1.5;'>"+itemName+": "+itemValue+"</h>";
				}
			}
			
			auxiliaryDeviceList.append("{\"id\":"+obj[0]+","
					+ "\"name\":\""+obj[1]+"\","
//					+ "\"manufacturer\":\""+obj[2]+"\","
//					+ "\"model\":\""+obj[3]+"\","
					+ "\"detailsInfo\":\""+details+"\"},");
		}
		
		if(auxiliaryDeviceList.toString().endsWith(",")){
			auxiliaryDeviceList.deleteCharAt(auxiliaryDeviceList.length() - 1);
		}
		
		//设备附加信息
		for(int i=0;i<deviceAddInfoList.size();i++){
			Object[] obj=(Object[]) deviceAddInfoList.get(i);
			deviceInfoDataList.append("{\"name\":\""+obj[1]+"\","
					+ "\"value\":\""+obj[2]+"\"},");
		}
		
		if(deviceInfoDataList.toString().endsWith(",")){
			deviceInfoDataList.deleteCharAt(deviceInfoDataList.length() - 1);
		}
		
		
		
		result_json.append("{ \"success\":true,");
		deviceInfoDataList.append("]");
		auxiliaryDeviceList.append("]");
		result_json.append("\"deviceInfoDataList\":"+deviceInfoDataList+",");
		result_json.append("\"auxiliaryDeviceList\":"+auxiliaryDeviceList+"");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	
	public String getRealTimeCurveData(String deviceName,String item,String deviceType)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String deviceTableName="tbl_device";
		String tableName="tbl_srpacqdata_hist";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			deviceTableName="tbl_pcpdevice";
			tableName="tbl_pcpacqdata_hist";
		}
		String protocolSql="select upper(t3.protocol) from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t.instancecode=t2.code and t2.unitid=t3.id"
				+ " and  t.devicename='"+deviceName+"'";
		List<?> protocolList = this.findCallSql(protocolSql);
		String protocolName="";
		String column="";
		String unit="";
		String dataType="";
		int resolutionMode=0;
		if(protocolList.size()>0){
			protocolName=protocolList.get(0)+"";
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()!=null){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
						for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
							if(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle().equalsIgnoreCase(item)){
								column="ADDR"+modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getAddr();
								unit=modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getUnit();
								dataType=modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getIFDataType();
								resolutionMode=modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getResolutionMode();
								break;
							}
						}
						break;
					}
				}
			}
		}
		
		result_json.append("{\"deviceName\":\""+deviceName+"\",\"item\":\""+item+"\",\"column\":\""+column+"\",\"unit\":\""+unit+"\",\"dataType\":\""+dataType+"\",\"resolutionMode\":"+resolutionMode+",\"list\":[");
		if(resolutionMode==2){//只查询数据量的曲线
			String sql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss'), t."+column+" "
					+ " from "+tableName +" t,"+deviceTableName+" t2 "
					+ " where t.deviceid=t2.id "
					+ " and t.acqtime >to_date('"+StringManagerUtils.getCurrentTime("yyyy-MM-dd")+"','yyyy-mm-dd') "
					+ " and t2.devicename='"+deviceName+"' "
					+ " order by t.acqtime";
			List<?> list = this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				result_json.append("{acqTime:\"" + obj[0] + "\",");
				result_json.append("value:\"" + obj[1] + "\"},");
			}
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getRealTimeMonitoringCurveDataFromMemory(String deviceId,String deviceName,String deviceType,String calculateType,int userNo,String language)throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer itemsBuff = new StringBuffer();
		StringBuffer curveConfBuff = new StringBuffer();
		int vacuateRecord=Config.getInstance().configFile.getAp().getDataVacuate().getVacuateRecord();
		UserInfo userInfo=null;
		List<CalItem> calItemList=null;
		List<CalItem> inputItemList=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		DeviceInfo deviceInfo=null;
		String displayInstanceCode="";
		String tableName="tbl_acqdata_hist";
		String deviceTableName="tbl_device";
		
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
		Map<String,DataMapping> protocolExtendedFieldColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(1);
		try{
			try{
				userInfo=MemoryDataManagerTask.getUserInfoByNo(userNo+"");
				deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
				if(deviceInfo!=null){
					displayInstanceCode=deviceInfo.getDisplayInstanceCode()+"";
				}
				
				displayInstanceOwnItem=MemoryDataManagerTask.getDisplayInstanceOwnItemByCode(displayInstanceCode);
				if(displayInstanceOwnItem!=null){
					Collections.sort(displayInstanceOwnItem.getItemList());
				}
				
				if(StringManagerUtils.stringToInteger(calculateType)==1){
					calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
					inputItemList=MemoryDataManagerTask.getSRPInputItem(language);
				}else if(StringManagerUtils.stringToInteger(calculateType)==2){
					calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
					inputItemList=MemoryDataManagerTask.getPCPInputItem(language);
				}else{
					calItemList=MemoryDataManagerTask.getAcqCalculateItem(language);
					inputItemList=new ArrayList<>();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			List<String> acqItemColumnList=new ArrayList<String>();
			List<String> extendedFieldColumnList=new ArrayList<String>();
			List<String> calItemColumnList=new ArrayList<String>();
			List<String> inputItemColumnList=new ArrayList<String>();
			
			
			List<String> acqTimeList=new ArrayList<>();
			Map<Integer,String> itemNameMap=new TreeMap<>();
			Map<String,Integer> itemCodeSortMap=new HashMap<>();
			Map<Integer,String> curveConfMap=new TreeMap<>();
			Map<Integer,List<String>> curveDataMap=new TreeMap<>();
			
			if(displayInstanceOwnItem!=null){
				Collections.sort(displayInstanceOwnItem.getItemList(),new Comparator<DisplayInstanceOwnItem.DisplayItem>(){
					@Override
					public int compare(DisplayInstanceOwnItem.DisplayItem item1,DisplayInstanceOwnItem.DisplayItem item2){
						Gson gson = new Gson();
						java.lang.reflect.Type type=null;
						int sort1=0;
						int sort2=0;
						type = new TypeToken<CurveConf>() {}.getType();
						CurveConf curveConfObj1=gson.fromJson(item1.getRealtimeCurveConf(), type);
						
						type = new TypeToken<CurveConf>() {}.getType();
						CurveConf curveConfObj2=gson.fromJson(item2.getRealtimeCurveConf(), type);
						
						if(curveConfObj1!=null){
							sort1=curveConfObj1.getSort();
						}
						
						if(curveConfObj2!=null){
							sort2=curveConfObj2.getSort();
						}
						
						int diff=sort1-sort2;
						if(diff>0){
							return 1;
						}else if(diff<0){
							return -1;
						}
						return 0;
					}
				});
				String protocolCode=displayInstanceOwnItem.getProtocolCode();
				Protocol protocol=MemoryDataManagerTask.getProtocolByCode(protocolCode);
				if(protocol!=null){
					for(int j=0;j<displayInstanceOwnItem.getItemList().size();j++){
						Gson gson = new Gson();
						java.lang.reflect.Type reflectType=new TypeToken<CurveConf>() {}.getType();
						CurveConf curveConfObj=gson.fromJson(displayInstanceOwnItem.getItemList().get(j).getRealtimeCurveConf(), reflectType);
						if(curveConfObj!=null && curveConfObj.getSort()>0 && displayInstanceOwnItem.getItemList().get(j).getShowLevel()>=userInfo.getRoleShowLevel()){
							String itemname=displayInstanceOwnItem.getItemList().get(j).getItemName();
							String realtimecurveconf=displayInstanceOwnItem.getItemList().get(j).getRealtimeCurveConf();
							String itemcode=displayInstanceOwnItem.getItemList().get(j).getItemCode();
							String type=displayInstanceOwnItem.getItemList().get(j).getType()+"";
							int sort=curveConfObj.getSort();
							while(itemNameMap.containsKey(sort)){
								sort+=1;
							}
							if("0".equalsIgnoreCase(type)){
								for(int k=0;k<protocol.getItems().size();k++){
									if(protocol.getItems().get(k).getTitle().equalsIgnoreCase(itemname)){
										String col="";
										if(loadProtocolMappingColumnByTitleMap.containsKey(protocol.getItems().get(k).getTitle())){
											col=loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(k).getTitle()).getMappingColumn();
										}
										if(StringManagerUtils.isNotNull(protocol.getItems().get(k).getUnit())){
											itemNameMap.put(sort, protocol.getItems().get(k).getTitle()+"("+protocol.getItems().get(k).getUnit()+")");
										}else{
											itemNameMap.put(sort, protocol.getItems().get(k).getTitle());
										}
										itemCodeSortMap.put(col, sort);
										acqItemColumnList.add(col);
										curveConfMap.put(sort, realtimecurveconf.replaceAll("null", ""));
										curveDataMap.put(sort, new ArrayList<>());
										break;
									}
								}
							}else if("5".equalsIgnoreCase(type)){
								if(protocol.getExtendedFields()!=null && protocol.getExtendedFields().size()>0){
									for(int k=0;k<protocol.getExtendedFields().size();k++){
										if(protocol.getExtendedFields().get(k).getTitle().equalsIgnoreCase(itemname)){
											DataMapping dataMapping=protocolExtendedFieldColumnByTitleMap.get(protocol.getExtendedFields().get(k).getTitle());
											if(dataMapping!=null){
												String extendedFieldCode=dataMapping.getMappingColumn();
												if(StringManagerUtils.isNotNull(protocol.getExtendedFields().get(k).getUnit())){
													itemNameMap.put(sort, protocol.getExtendedFields().get(k).getTitle()+"("+protocol.getExtendedFields().get(k).getUnit()+")");
												}else{
													itemNameMap.put(sort, protocol.getExtendedFields().get(k).getTitle());
												}
												itemCodeSortMap.put(extendedFieldCode, sort);
												extendedFieldColumnList.add(extendedFieldCode);
												curveConfMap.put(sort, realtimecurveconf.replaceAll("null", ""));
												curveDataMap.put(sort, new ArrayList<>());
											}
											break;
										}
									}
								}
							}else if("1".equalsIgnoreCase(type)){
								String itemName=itemname;
								if(calItemList!=null && calItemList.size()>0){
									for(CalItem calItem:calItemList){
										if(itemcode.equalsIgnoreCase(calItem.getCode())){
											itemName=calItem.getName();
											if(StringManagerUtils.isNotNull(calItem.getUnit())){
												itemName=itemName+"("+calItem.getUnit()+")";
											}
											break;
										}
									}
								}
								if(StringManagerUtils.stringToInteger(calculateType)==0){
									if(StringManagerUtils.generalCalColumnFiter(itemcode)){
										calItemColumnList.add(itemcode);
										itemCodeSortMap.put(itemcode, sort);
										itemNameMap.put(sort, itemName);
										curveConfMap.put(sort, realtimecurveconf.replaceAll("null", ""));
										curveDataMap.put(sort, new ArrayList<>());
									}
								}else{
									calItemColumnList.add(itemcode);
									itemCodeSortMap.put(itemcode, sort);
									itemNameMap.put(sort, itemName);
									curveConfMap.put(sort, realtimecurveconf.replaceAll("null", ""));
									curveDataMap.put(sort, new ArrayList<>());
								}
							}else if("3".equalsIgnoreCase(type)){
								if(StringManagerUtils.stringToInteger(calculateType)>0){
									String itemName=itemname;
									if(inputItemList!=null && inputItemList.size()>0){
										for(CalItem calItem:inputItemList){
											if(itemcode.equalsIgnoreCase(calItem.getCode())){
												itemName=calItem.getName();
												if(StringManagerUtils.isNotNull(calItem.getUnit())){
													itemName=itemName+"("+calItem.getUnit()+")";
												}
												break;
											}
											
										}
									}
									
									inputItemColumnList.add(itemcode);
									itemCodeSortMap.put(itemcode, sort);
									itemNameMap.put(sort, itemName);
									curveConfMap.put(sort, realtimecurveconf.replaceAll("null", ""));
									curveDataMap.put(sort, new ArrayList<>());
								}
								
							}
						}
					}
				}
			}
			
			itemsBuff.append("[");
			for (Integer key : itemNameMap.keySet()) {
	            itemsBuff.append("\""+itemNameMap.get(key)+"\",");
	        }
			if (itemsBuff.toString().endsWith(",")) {
				itemsBuff.deleteCharAt(itemsBuff.length() - 1);
			}
			itemsBuff.append("]");
			
			curveConfBuff.append("[");
			for (Integer key : curveConfMap.keySet()) {
	            curveConfBuff.append(""+curveConfMap.get(key)+",");
	        }
			if (curveConfBuff.toString().endsWith(",")) {
				curveConfBuff.deleteCharAt(curveConfBuff.length() - 1);
			}
			curveConfBuff.append("]");
			
			
			if(itemCodeSortMap.size()>0){
				Map<String,Map<String,String>> realtimeDataTimeMap=MemoryDataManagerTask.getDeviceRealtimeAcqDataById(deviceId+"",language);
				if(realtimeDataTimeMap!=null && realtimeDataTimeMap.size()>0){
					Iterator<Map.Entry<String,Map<String,String>>> realtimeDataTimeMapIterator = realtimeDataTimeMap.entrySet().iterator();
					while(realtimeDataTimeMapIterator.hasNext()){
						Map.Entry<String,Map<String,String>> entry = realtimeDataTimeMapIterator.next();
					    String key = entry.getKey();
					    Map<String,String> everyDataMap = entry.getValue();
					    acqTimeList.add(key);
					    String commStatus=everyDataMap.get("commStatus");
					    String checkSign=everyDataMap.get("checkSign");
					    for (String itemCode : itemCodeSortMap.keySet()) {
					    	String value="null";
							if(everyDataMap.containsKey(itemCode.toUpperCase())){
								value=everyDataMap.get(itemCode.toUpperCase());
							}
							if(!StringManagerUtils.isNum(value)){
								value="null";
							}
							
					    	if(!curveDataMap.containsKey(itemCodeSortMap.get(itemCode))){
					    		curveDataMap.put(itemCodeSortMap.get(itemCode), new ArrayList<>());
					    	}
					    	curveDataMap.get(itemCodeSortMap.get(itemCode)).add(value);
					    }
					}
				}else{
					String columns="";
					String calAndInputColumn="";
					String calAndInputDataTable="";
					if(StringManagerUtils.stringToInteger(calculateType)==1){
						calAndInputDataTable="tbl_srpacqdata_hist";
					}else if(StringManagerUtils.stringToInteger(calculateType)==2){
						calAndInputDataTable="tbl_pcpacqdata_hist";
					}
					if(acqItemColumnList.size()>0 || extendedFieldColumnList.size()>0){
						columns+=",t.acqdata";
					}
					if(StringManagerUtils.stringToInteger(calculateType)>0){
						for(int i=0;i<calItemColumnList.size();i++){
							calAndInputColumn+=",t3."+calItemColumnList.get(i);
						}
						if(inputItemColumnList.size()>0){
							calAndInputColumn+=",t3.productiondata";
						}
					}else{
						for(int i=0;i<calItemColumnList.size();i++){
							calAndInputColumn+=",t."+calItemColumnList.get(i);
						}
					}
					
					String sql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime"+columns+calAndInputColumn
							+ " from "+tableName +" t"
							+ " left outer join "+deviceTableName+" t2 on t.deviceid=t2.id";
					if(StringManagerUtils.stringToInteger(calculateType)>0){
						sql+= " left outer join "+calAndInputDataTable+" t3 on t.deviceid=t3.deviceid and t.acqtime=t3.acqtime";
					}	
					sql+= " where t.acqtime >to_date('"+StringManagerUtils.getCurrentTime("yyyy-MM-dd")+"','yyyy-mm-dd') "
							+ " and t2.id="+deviceId;
					int total=this.getTotalCountRows(sql);
					int rarefy=total/vacuateRecord+1;
					sql+= " order by t.acqtime";
					
					String finalSql=sql;
					if(rarefy>1){
						finalSql="select * from  (select v.*, rownum as rn from ("+sql+") v ) v2 where mod(rn*"+vacuateRecord+","+total+")<"+vacuateRecord+"";
					}
					List<?> list = this.findCallSql(finalSql);
					for(int i=0;i<list.size();i++){
						Object[] obj=(Object[]) list.get(i);
						acqTimeList.add(obj[0]+"");
						
						int startIndex=1;
						if(acqItemColumnList.size()>0 || extendedFieldColumnList.size()>0){
							startIndex=2;
							Gson gson = new Gson();
							java.lang.reflect.Type type=null;
							String acqData=StringManagerUtils.CLOBObjectToString(obj[1]);
							type = new TypeToken<List<KeyValue>>() {}.getType();
							List<KeyValue> acqDataList=gson.fromJson(acqData, type);
							
							for(String itemColumn:acqItemColumnList){
								String value="null";
								if(acqDataList!=null){
									for(KeyValue keyValue:acqDataList){
										if(itemColumn.equalsIgnoreCase(keyValue.getKey())){
											value=keyValue.getValue();
											break;
										}
									}
								}
								if(!StringManagerUtils.isNum(value)){
									value="null";
								}
								if(!curveDataMap.containsKey(itemCodeSortMap.get(itemColumn))){
						    		curveDataMap.put(itemCodeSortMap.get(itemColumn), new ArrayList<>());
						    	}
						    	curveDataMap.get(itemCodeSortMap.get(itemColumn)).add(value);
							}
							
							for(String itemColumn:extendedFieldColumnList){
								String value="null";
								if(acqDataList!=null){
									for(KeyValue keyValue:acqDataList){
										if(itemColumn.equalsIgnoreCase(keyValue.getKey())){
											value=keyValue.getValue();
											break;
										}
									}
								}
								if(!StringManagerUtils.isNum(value)){
									value="null";
								}
								if(!curveDataMap.containsKey(itemCodeSortMap.get(itemColumn))){
						    		curveDataMap.put(itemCodeSortMap.get(itemColumn), new ArrayList<>());
						    	}
						    	curveDataMap.get(itemCodeSortMap.get(itemColumn)).add(value);
							}
						}
						
						for(int j=startIndex;j<startIndex+calItemColumnList.size();j++){
							String value=obj[j]+"";
							String itemCode=calItemColumnList.get(j-startIndex);
							if(!curveDataMap.containsKey(itemCodeSortMap.get(itemCode))){
					    		curveDataMap.put(itemCodeSortMap.get(itemCode), new ArrayList<>());
					    	}
							if(!StringManagerUtils.isNum(value)){
								value="null";
							}
					    	curveDataMap.get(itemCodeSortMap.get(itemCode)).add(value);
						}
						if(inputItemColumnList.size()>0){
							String productionData=(obj[obj.length-1]+"").replaceAll("null", "");
							if(rarefy>1){
								productionData=(obj[obj.length-2]+"").replaceAll("null", "");
							}
							
							Gson gson = new Gson();
							java.lang.reflect.Type type=null;
							if(StringManagerUtils.stringToInteger(calculateType)==1){
								type = new TypeToken<SRPCalculateRequestData>() {}.getType();
								SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
								for(int j=0;j<inputItemColumnList.size();j++){
									String inputItemValue="\"\"";
									String column=inputItemColumnList.get(j);
									if(srpProductionData!=null){
										if("CrudeOilDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
											inputItemValue=srpProductionData.getFluidPVT().getCrudeOilDensity()+"";
										}else if("WaterDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
											inputItemValue=srpProductionData.getFluidPVT().getWaterDensity()+"";
										}else if("NaturalGasRelativeDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
											inputItemValue=srpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
										}else if("SaturationPressure".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
											inputItemValue=srpProductionData.getFluidPVT().getSaturationPressure()+"";
										}else if("ReservoirDepth".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
											inputItemValue=srpProductionData.getReservoir().getDepth()+"";
										}else if("ReservoirTemperature".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
											inputItemValue=srpProductionData.getReservoir().getTemperature()+"";
										}else if("TubingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
											inputItemValue=srpProductionData.getProduction().getTubingPressure()+"";
										}else if("CasingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
											inputItemValue=srpProductionData.getProduction().getCasingPressure()+"";
										}else if("WellHeadTemperature".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
											inputItemValue=srpProductionData.getProduction().getWellHeadTemperature()+"";
										}else if("WaterCut".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
											inputItemValue=srpProductionData.getProduction().getWaterCut()+"";
										}else if("ProductionGasOilRatio".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
											inputItemValue=srpProductionData.getProduction().getProductionGasOilRatio()+"";
										}else if("ProducingfluidLevel".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
											inputItemValue=srpProductionData.getProduction().getProducingfluidLevel()+"";
										}else if("PumpSettingDepth".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
											inputItemValue=srpProductionData.getProduction().getPumpSettingDepth()+"";
										}else if("PumpBoreDiameter".equalsIgnoreCase(column) && srpProductionData.getPump()!=null ){
											inputItemValue=srpProductionData.getPump().getPumpBoreDiameter()*1000+"";
										}else if("LevelCorrectValue".equalsIgnoreCase(column) && srpProductionData.getManualIntervention()!=null ){
											inputItemValue=srpProductionData.getManualIntervention().getLevelCorrectValue()+"";
										}
									}
									if(!StringManagerUtils.isNum(inputItemValue)){
										inputItemValue="null";
									}
									if(!curveDataMap.containsKey(itemCodeSortMap.get(column))){
							    		curveDataMap.put(itemCodeSortMap.get(column), new ArrayList<>());
							    	}
							    	curveDataMap.get(itemCodeSortMap.get(column)).add(inputItemValue);
								}
							}else if(StringManagerUtils.stringToInteger(calculateType)==2){
								type = new TypeToken<PCPCalculateRequestData>() {}.getType();
								PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
								for(int j=0;j<inputItemColumnList.size();j++){
									String inputItemValue="\"\"";
									String column=inputItemColumnList.get(j);
									if(pcpProductionData!=null){
										if("CrudeOilDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
											inputItemValue=pcpProductionData.getFluidPVT().getCrudeOilDensity()+"";
										}else if("WaterDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
											inputItemValue=pcpProductionData.getFluidPVT().getWaterDensity()+"";
										}else if("NaturalGasRelativeDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
											inputItemValue=pcpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
										}else if("SaturationPressure".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
											inputItemValue=pcpProductionData.getFluidPVT().getSaturationPressure()+"";
										}else if("ReservoirDepth".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
											inputItemValue=pcpProductionData.getReservoir().getDepth()+"";
										}else if("ReservoirTemperature".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
											inputItemValue=pcpProductionData.getReservoir().getTemperature()+"";
										}else if("TubingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
											inputItemValue=pcpProductionData.getProduction().getTubingPressure()+"";
										}else if("CasingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
											inputItemValue=pcpProductionData.getProduction().getCasingPressure()+"";
										}else if("WellHeadTemperature".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
											inputItemValue=pcpProductionData.getProduction().getWellHeadTemperature()+"";
										}else if("WaterCut".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
											inputItemValue=pcpProductionData.getProduction().getWaterCut()+"";
										}else if("ProductionGasOilRatio".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
											inputItemValue=pcpProductionData.getProduction().getProductionGasOilRatio()+"";
										}else if("ProducingfluidLevel".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
											inputItemValue=pcpProductionData.getProduction().getProducingfluidLevel()+"";
										}else if("PumpSettingDepth".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
											inputItemValue=pcpProductionData.getProduction().getPumpSettingDepth()+"";
										}
									}
									if(!StringManagerUtils.isNum(inputItemValue)){
										inputItemValue="null";
									}
									if(!curveDataMap.containsKey(itemCodeSortMap.get(column))){
							    		curveDataMap.put(itemCodeSortMap.get(column), new ArrayList<>());
							    	}
							    	curveDataMap.get(itemCodeSortMap.get(column)).add(inputItemValue);
								}
							}
						}
					}
				}
			}
			
			result_json.append("{\"deviceName\":\""+deviceName+"\","
					+ "\"curveCount\":"+itemCodeSortMap.size()+","
					+ "\"curveItems\":"+itemsBuff+","
					+ "\"curveConf\":"+curveConfBuff+","
					+ "\"list\":[");
			
			for(int i=0;i<acqTimeList.size();i++){
				result_json.append("{\"acqTime\":\"" + acqTimeList.get(i) + "\",\"data\":[");
				for (Integer key : itemNameMap.keySet()) {
					result_json.append(""+curveDataMap.get(key).get(i)+",");
		        }
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
				result_json.append("]},");
			}
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]}");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result_json.toString();
	}
	
	public String getRealTimeMonitoringCurveDataFromDatabase(String deviceId,String deviceName,String deviceType,String calculateType,int userNo,String language)throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer itemsBuff = new StringBuffer();
		StringBuffer curveConfBuff = new StringBuffer();
		int vacuateRecord=Config.getInstance().configFile.getAp().getDataVacuate().getVacuateRecord();
		UserInfo userInfo=null;
		List<CalItem> calItemList=null;
		List<CalItem> inputItemList=null;
		DeviceInfo deviceInfo=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		String displayInstanceCode="";
		String tableName="tbl_acqdata_hist";
		String deviceTableName="tbl_device";
		
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
		try{
			try{
				userInfo=MemoryDataManagerTask.getUserInfoByNo(userNo+"");
				deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
				if(deviceInfo!=null){
					displayInstanceCode=deviceInfo.getDisplayInstanceCode()+"";
				}
				
				displayInstanceOwnItem=MemoryDataManagerTask.getDisplayInstanceOwnItemByCode(displayInstanceCode);
				if(displayInstanceOwnItem!=null){
					Collections.sort(displayInstanceOwnItem.getItemList());
				}
				
				if(StringManagerUtils.stringToInteger(calculateType)==1){
					calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
					inputItemList=MemoryDataManagerTask.getSRPInputItem(language);
				}else if(StringManagerUtils.stringToInteger(calculateType)==2){
					calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
					inputItemList=MemoryDataManagerTask.getPCPInputItem(language);
				}else{
					calItemList=MemoryDataManagerTask.getAcqCalculateItem(language);
					inputItemList=new ArrayList<>();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			List<String> itemNameList=new ArrayList<String>();
			List<String> itemColumnList=new ArrayList<String>();
			List<String> curveConfList=new ArrayList<String>();
			
			List<String> calItemNameList=new ArrayList<String>();
			List<String> calItemColumnList=new ArrayList<String>();
			List<String> calItemCurveConfList=new ArrayList<String>();
			
			List<String> inputItemNameList=new ArrayList<String>();
			List<String> inputItemColumnList=new ArrayList<String>();
			List<String> inputItemCurveConfList=new ArrayList<String>();
			

			String protocolSql="select upper(t3.protocol) from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t.instancecode=t2.code and t2.unitid=t3.id"
					+ " and  t.id="+deviceId;
			String curveItemsSql="select t4.itemname,t4.bitindex,t4.realtimecurveconf,t4.itemcode,t4.type "
					+ " from "+deviceTableName+" t,tbl_protocoldisplayinstance t2,tbl_display_unit_conf t3,tbl_display_items2unit_conf t4 "
					+ " where t.displayinstancecode=t2.code and t2.displayunitid=t3.id and t3.id=t4.unitid and t4.type<>2 "
					+ " and t.id="+deviceId+" "
					+ " and t4.realtimecurveconf is not null "
					+ " and decode(t4.showlevel,null,9999,t4.showlevel)>=( select r.showlevel from tbl_role r,tbl_user u where u.user_type=r.role_id and u.user_no='"+userNo+"' )"
					+ " order by t4.realtimeSort,t4.id";
			List<?> protocolList = this.findCallSql(protocolSql);
			List<?> curveItemList = this.findCallSql(curveItemsSql);
			String protocolName="";
			
			if(protocolList.size()>0){
				protocolName=protocolList.get(0)+"";
				ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
				if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()!=null){
					for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
						if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
							for(int j=0;j<curveItemList.size();j++){
								Object[] itemObj=(Object[]) curveItemList.get(j);
								String itemname=itemObj[0]+"";
								String bitindex=itemObj[1]+"";
								String realtimecurveconf=itemObj[2]+"";
								String itemcode=itemObj[3]+"";
								String type=itemObj[4]+"";
								if("0".equalsIgnoreCase(type)){
									for(int k=0;k<modbusProtocolConfig.getProtocol().get(i).getItems().size();k++){
										if(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle().equalsIgnoreCase(itemname)){
											String col="";
											if(loadProtocolMappingColumnByTitleMap.containsKey(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle())){
												col=loadProtocolMappingColumnByTitleMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()).getMappingColumn();
											}
											itemColumnList.add(col);
											if(StringManagerUtils.isNotNull(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getUnit())){
												itemNameList.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()+"("+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getUnit()+")");
											}else{
												itemNameList.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle());
											}
											curveConfList.add(realtimecurveconf.replaceAll("null", ""));
											break;
										}
									}
								}else if("1".equalsIgnoreCase(type)){
									calItemColumnList.add(itemcode);
									String itemName=itemname;
									if(calItemList!=null && calItemList.size()>0){
										for(CalItem calItem:calItemList){
											if(itemcode.equalsIgnoreCase(calItem.getCode())){
												if(StringManagerUtils.isNotNull(calItem.getUnit())){
													itemName=itemName+"("+calItem.getUnit()+")";
												}
												break;
											}
											
										}
									}
									
									if(StringManagerUtils.stringToInteger(calculateType)==0){
										if(StringManagerUtils.generalCalColumnFiter(itemcode)){
											calItemNameList.add(itemName);
											calItemCurveConfList.add(realtimecurveconf.replaceAll("null", ""));
										}
									}else{
										calItemNameList.add(itemName);
										calItemCurveConfList.add(realtimecurveconf.replaceAll("null", ""));
									}
								}else if("3".equalsIgnoreCase(type)){
									inputItemColumnList.add(itemcode);
									String itemName=itemname;
									if(inputItemList!=null && inputItemList.size()>0){
										for(CalItem calItem:inputItemList){
											if(itemcode.equalsIgnoreCase(calItem.getCode())){
												if(StringManagerUtils.isNotNull(calItem.getUnit())){
													itemName=itemName+"("+calItem.getUnit()+")";
												}
												break;
											}
											
										}
									}
									if(StringManagerUtils.stringToInteger(calculateType)>0){
										inputItemNameList.add(itemName);
										inputItemCurveConfList.add(realtimecurveconf.replaceAll("null", ""));
									}
								}
							}
							break;
						}
					}
				}
			}
			
			itemsBuff.append("[");
			for(int i=0;i<itemNameList.size();i++){
				itemsBuff.append("\""+itemNameList.get(i)+"\",");
			}
			for(int i=0;i<calItemNameList.size();i++){
				itemsBuff.append("\""+calItemNameList.get(i)+"\",");
			}
			for(int i=0;i<inputItemNameList.size();i++){
				itemsBuff.append("\""+inputItemNameList.get(i)+"\",");
			}
			if (itemsBuff.toString().endsWith(",")) {
				itemsBuff.deleteCharAt(itemsBuff.length() - 1);
			}
			itemsBuff.append("]");
			
			curveConfBuff.append("[");
			for(int i=0;i<curveConfList.size();i++){
				curveConfBuff.append(""+curveConfList.get(i)+",");
			}
			for(int i=0;i<calItemCurveConfList.size();i++){
				curveConfBuff.append(""+calItemCurveConfList.get(i)+",");
			}
			for(int i=0;i<inputItemCurveConfList.size();i++){
				curveConfBuff.append(""+inputItemCurveConfList.get(i)+",");
			}
			if (curveConfBuff.toString().endsWith(",")) {
				curveConfBuff.deleteCharAt(curveConfBuff.length() - 1);
			}
			curveConfBuff.append("]");
			
			result_json.append("{\"deviceName\":\""+deviceName+"\","
					+ "\"curveCount\":"+(itemNameList.size()+calItemNameList.size()+inputItemNameList.size())+","
					+ "\"curveItems\":"+itemsBuff+","
					+ "\"curveConf\":"+curveConfBuff+","
					+ "\"list\":[");
			if(itemColumnList.size()>0 || calItemColumnList.size()>0 || inputItemColumnList.size()>0){
				String columns="";
				String calAndInputColumn="";
				String calAndInputDataTable="";
				if(StringManagerUtils.stringToInteger(calculateType)==1){
					calAndInputDataTable="tbl_srpacqdata_hist";
				}else if(StringManagerUtils.stringToInteger(calculateType)==2){
					calAndInputDataTable="tbl_pcpacqdata_hist";
				}
				if(itemColumnList.size()>0){
					columns+=",t.acqdata";
				}
				if(StringManagerUtils.stringToInteger(calculateType)>0){
					for(int i=0;i<calItemColumnList.size();i++){
						calAndInputColumn+=",t3."+calItemColumnList.get(i);
					}
					if(inputItemColumnList.size()>0){
						calAndInputColumn+=",t3.productiondata";
					}
				}else{
					for(int i=0;i<calItemColumnList.size();i++){
						calAndInputColumn+=",t."+calItemColumnList.get(i);
					}
				}
				
				String sql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime"+columns+calAndInputColumn
						+ " from "+tableName +" t"
						+ " left outer join "+deviceTableName+" t2 on t.deviceid=t2.id";
				if(StringManagerUtils.stringToInteger(calculateType)>0){
					sql+= " left outer join "+calAndInputDataTable+" t3 on t.deviceid=t3.deviceid and t.acqtime=t3.acqtime";
				}	
				sql+= " where t.acqtime >to_date('"+StringManagerUtils.getCurrentTime("yyyy-MM-dd")+"','yyyy-mm-dd') "
						+ " and t2.id="+deviceId;
				int total=this.getTotalCountRows(sql);
				int rarefy=total/vacuateRecord+1;
				sql+= " order by t.acqtime";
				
				String finalSql=sql;
				if(rarefy>1){
					finalSql="select acqtime"+columns+" from  (select v.*, rownum as rn from ("+sql+") v ) v2 where mod(rn*"+vacuateRecord+","+total+")<"+vacuateRecord+"";
				}
				List<?> list = this.findCallSql(finalSql);
				for(int i=0;i<list.size();i++){
					Object[] obj=(Object[]) list.get(i);
					result_json.append("{\"acqTime\":\"" + obj[0] + "\",\"data\":[");
					int startIndex=1;
					if(itemColumnList.size()>0){
						startIndex=2;
						Gson gson = new Gson();
						java.lang.reflect.Type type=null;
						String acqData=StringManagerUtils.CLOBObjectToString(obj[1]);
						type = new TypeToken<List<KeyValue>>() {}.getType();
						List<KeyValue> acqDataList=gson.fromJson(acqData, type);
						
						for(String itemColumn:itemColumnList){
							String value="";
							if(acqDataList!=null){
								for(KeyValue keyValue:acqDataList){
									if(itemColumn.equalsIgnoreCase(keyValue.getKey())){
										value=keyValue.getValue();
										break;
									}
								}
							}
							
							result_json.append(value+",");
						}
					}
					for(int j=startIndex;j<startIndex+calItemColumnList.size();j++){
						result_json.append(obj[j]+",");
					}
					if(inputItemColumnList.size()>0){
						String productionData=(obj[obj.length-1]+"").replaceAll("null", "");
						Gson gson = new Gson();
						java.lang.reflect.Type type=null;
						if(StringManagerUtils.stringToInteger(calculateType)==1){
							type = new TypeToken<SRPCalculateRequestData>() {}.getType();
							SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
							for(int j=0;j<inputItemColumnList.size();j++){
								String inputItemValue="";
								String column=inputItemColumnList.get(j);
								if(srpProductionData!=null){
									if("CrudeOilDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
										inputItemValue=srpProductionData.getFluidPVT().getCrudeOilDensity()+"";
									}else if("WaterDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
										inputItemValue=srpProductionData.getFluidPVT().getWaterDensity()+"";
									}else if("NaturalGasRelativeDensity".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
										inputItemValue=srpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
									}else if("SaturationPressure".equalsIgnoreCase(column) && srpProductionData.getFluidPVT()!=null ){
										inputItemValue=srpProductionData.getFluidPVT().getSaturationPressure()+"";
									}else if("ReservoirDepth".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
										inputItemValue=srpProductionData.getReservoir().getDepth()+"";
									}else if("ReservoirTemperature".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
										inputItemValue=srpProductionData.getReservoir().getTemperature()+"";
									}else if("TubingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
										inputItemValue=srpProductionData.getProduction().getTubingPressure()+"";
									}else if("CasingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
										inputItemValue=srpProductionData.getProduction().getCasingPressure()+"";
									}else if("WellHeadTemperature".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
										inputItemValue=srpProductionData.getProduction().getWellHeadTemperature()+"";
									}else if("WaterCut".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
										inputItemValue=srpProductionData.getProduction().getWaterCut()+"";
									}else if("ProductionGasOilRatio".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
										inputItemValue=srpProductionData.getProduction().getProductionGasOilRatio()+"";
									}else if("ProducingfluidLevel".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
										inputItemValue=srpProductionData.getProduction().getProducingfluidLevel()+"";
									}else if("PumpSettingDepth".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
										inputItemValue=srpProductionData.getProduction().getPumpSettingDepth()+"";
									}else if("PumpBoreDiameter".equalsIgnoreCase(column) && srpProductionData.getPump()!=null ){
										inputItemValue=srpProductionData.getPump().getPumpBoreDiameter()*1000+"";
									}else if("LevelCorrectValue".equalsIgnoreCase(column) && srpProductionData.getManualIntervention()!=null ){
										inputItemValue=srpProductionData.getManualIntervention().getLevelCorrectValue()+"";
									}
								}
								result_json.append(inputItemValue+",");
							}
						}else if(StringManagerUtils.stringToInteger(calculateType)==2){
							type = new TypeToken<PCPCalculateRequestData>() {}.getType();
							PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
							for(int j=0;j<inputItemColumnList.size();j++){
								String inputItemValue="";
								String column=inputItemColumnList.get(j);
								if(pcpProductionData!=null){
									if("CrudeOilDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
										inputItemValue=pcpProductionData.getFluidPVT().getCrudeOilDensity()+"";
									}else if("WaterDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
										inputItemValue=pcpProductionData.getFluidPVT().getWaterDensity()+"";
									}else if("NaturalGasRelativeDensity".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
										inputItemValue=pcpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
									}else if("SaturationPressure".equalsIgnoreCase(column) && pcpProductionData.getFluidPVT()!=null ){
										inputItemValue=pcpProductionData.getFluidPVT().getSaturationPressure()+"";
									}else if("ReservoirDepth".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
										inputItemValue=pcpProductionData.getReservoir().getDepth()+"";
									}else if("ReservoirTemperature".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
										inputItemValue=pcpProductionData.getReservoir().getTemperature()+"";
									}else if("TubingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
										inputItemValue=pcpProductionData.getProduction().getTubingPressure()+"";
									}else if("CasingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
										inputItemValue=pcpProductionData.getProduction().getCasingPressure()+"";
									}else if("WellHeadTemperature".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
										inputItemValue=pcpProductionData.getProduction().getWellHeadTemperature()+"";
									}else if("WaterCut".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
										inputItemValue=pcpProductionData.getProduction().getWaterCut()+"";
									}else if("ProductionGasOilRatio".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
										inputItemValue=pcpProductionData.getProduction().getProductionGasOilRatio()+"";
									}else if("ProducingfluidLevel".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
										inputItemValue=pcpProductionData.getProduction().getProducingfluidLevel()+"";
									}else if("PumpSettingDepth".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
										inputItemValue=pcpProductionData.getProduction().getPumpSettingDepth()+"";
									}
								}
								result_json.append(inputItemValue+",");
							}
						}
					}
					
					if (result_json.toString().endsWith(",")) {
						result_json.deleteCharAt(result_json.length() - 1);
					}
					result_json.append("]},");
				}
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("]}");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result_json.toString();
	}
	
	public void saveDeviceControlLog(String deviceId,String deviceName,String deviceType,String title,String value,User user) throws SQLException{
		getBaseDao().saveDeviceControlLog(deviceId,deviceName,deviceType,title,value,user);
	}
	
	public String getResourceProbeHistoryCurveData(String startDate,String endDate,String itemName,String itemCode) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		String tableName="tbl_resourcemonitoring";
		String code=itemCode;
		if("jedisStatus".equalsIgnoreCase(itemCode)){
			code="round(t.cachemaxmemory/(1024*1024),2)||';'|| round(t.cacheusedmemory/(1024*1024),2) as jedisStatus";
		}else if("tableSpaceSize".equalsIgnoreCase(itemCode)){
			code="tablespaceusedpercent";
			tableName="tbl_dbmonitoring";
		}
		String sql="select to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss'),"+code
				+" from "+tableName+" t "
				+ " where t.acqTime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss') ";
		if("tableSpaceSize".equalsIgnoreCase(itemCode)){
			sql+=" and t.tablespaceusedpercent>0";
		}
		sql+= " order by t.acqTime";
		int resourceMonitoringVacuateThreshold=Config.getInstance().configFile.getAp().getOthers().getResourceMonitoringVacuateThreshold();
		int total = getTotalCountRows(sql);//获取总记录数
		
		String finalSql=sql;
		if(!"tableSpaceSize".equalsIgnoreCase(itemCode)){
			int rarefy=total/resourceMonitoringVacuateThreshold+1;
			if(rarefy>1){
				finalSql="select * from  (select v.*, rownum as rn from ("+sql+") v ) v2 where mod(rn*"+resourceMonitoringVacuateThreshold+","+total+")<"+resourceMonitoringVacuateThreshold+"";
			}
		}
		
		List<?> list=this.findCallSql(finalSql);
		String minAcqTime="";
		String maxAcqTime="";
		dynSbf.append("{\"success\":true,\"totalCount\":" + list.size() + ",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				dynSbf.append("{ \"acqTime\":\"" + obj[0] + "\",");
				dynSbf.append("\"value\":\""+obj[1]+"\"},");
				
				if(i==0){
					minAcqTime=obj[0]+"";
				}
				if(i==list.size()-1){
					maxAcqTime=obj[0]+"";
				}
			}
			if(dynSbf.toString().endsWith(",")){
				dynSbf.deleteCharAt(dynSbf.length() - 1);
			}
		}
//		dynSbf.append("]}");
		dynSbf.append("],\"minAcqTime\":\""+minAcqTime+"\",\"maxAcqTime\":\""+maxAcqTime+"\"}");
		return dynSbf.toString().replaceAll("null", "");
	}
	
	public String querySingleDetailsWellBoreChartsData(int id,String deviceName,String language) throws SQLException, IOException{
		byte[] bytes; 
		ConfigFile configFile=Config.getInstance().configFile;
		BufferedInputStream bis = null;
        StringBuffer dataSbf = new StringBuffer();
        StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
        String tableName="tbl_srpacqdata_latest";
        String prodCol=" liquidVolumetricProduction";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol=" liquidweightproduction";
		}
        String sql="select t2.deviceName as deviceName, "
        		+ " to_char(t.fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
        		+ " t.pumpfsdiagram,"
        		+ " t.upperloadline,t.lowerloadline, t.fmax,t.fmin,t.stroke,t.spm, "
        		+ " t."+prodCol+","
        		+ " t.resultcode,"//10
        		+ " t.rodstring,"
        		+ " t.pumpeff1*100 as pumpeff1, t.pumpeff2*100 as pumpeff2, t.pumpeff3*100 as pumpeff3, t.pumpeff4*100 as pumpeff4,"
        		+ " t.position_curve,t.load_curve"
        		+ " from "+tableName+" t"
        		+ " left outer join tbl_device t2 on t.deviceid=t2.id"
        		+ " where 1=1 ";
        sql+=" and t.deviceid="+id;
		List<?> list=this.findCallSql(sql);
		String pointCount="";
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			String positionCurveData="";
			String loadCurveData="";
			String pumpFSDiagram="";
			SerializableClobProxy   proxy=null;
			CLOB realClob=null;
			String resultCode=obj[10]+"";
			WorkType workType=MemoryDataManagerTask.getWorkTypeByCode(resultCode+"",language);
			if(obj[2]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[2]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				pumpFSDiagram=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[16]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[16]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				positionCurveData=StringManagerUtils.CLOBtoString(realClob);
				if(StringManagerUtils.isNotNull(positionCurveData)){
					pointCount=positionCurveData.split(",").length+"";
				}
			}
			
			if(obj[17]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[17]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				loadCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			
			
			String rodStressRatio1="0",rodStressRatio2="0",rodStressRatio3="0",rodStressRatio4="0";
			
			if("1232".equals(resultCode) || !StringManagerUtils.isNotNull(pumpFSDiagram)){//采集异常
				String positionCurveDataArr[]=positionCurveData.split(",");
				String loadCurveDataArr[]=loadCurveData.split(",");
				for(int i=0;i<positionCurveDataArr.length&&i<loadCurveDataArr.length;i++){
					pumpFSDiagramStrBuff.append(positionCurveDataArr[i]+",").append(loadCurveDataArr[i]+",");
				}
			}else{
				String arrbgt[]=pumpFSDiagram.split(";");  // 以;为界放入数组
		        for(int i=2;i<(arrbgt.length);i++){
		        	String arrbgtdata[]=arrbgt[i].split(",");  // 以,为界放入数组
		        	for(int j=0;j<arrbgtdata.length;j+=2){
		        		pumpFSDiagramStrBuff.append(arrbgtdata[j] + ",");
		        		pumpFSDiagramStrBuff.append(arrbgtdata[j+1] + ",");
			        }
		        	pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
		        	pumpFSDiagramStrBuff.append("#");
		        }
		        if(obj[11]!=null){
		        	String rodDataArr[]=obj[11].toString().split(";");
			        for(int i=1;i<rodDataArr.length;i++){
			        	if(i==1&&rodDataArr[i].split(",").length==6){
			        		rodStressRatio1=rodDataArr[i].split(",")[5];
			        	}else if(i==2&&rodDataArr[i].split(",").length==6){
			        		rodStressRatio2=rodDataArr[i].split(",")[5];
			        	}else if(i==3&&rodDataArr[i].split(",").length==6){
			        		rodStressRatio3=rodDataArr[i].split(",")[5];
			        	}else if(i==4&&rodDataArr[i].split(",").length==6){
			        		rodStressRatio4=rodDataArr[i].split(",")[5];
			        	}
			        }
		        }
			}
	        if(pumpFSDiagramStrBuff.toString().endsWith(",")){
	        	pumpFSDiagramStrBuff=pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
	        }
	        String pumpFSDiagramData = pumpFSDiagramStrBuff.toString();
	        
	        dataSbf.append("{success:true,");
	        dataSbf.append("deviceName:\""+deviceName+"\",");           // 设备名称
	        dataSbf.append("acqTime:\""+obj[1]+"\",");         // 时间
	        dataSbf.append("pointCount:\""+pointCount+"\","); 
	        dataSbf.append("upperLoadLine:\""+obj[3]+"\",");         // 理论上载荷
	        dataSbf.append("lowerLoadLine:\""+obj[4]+"\",");         // 理论下载荷
	        dataSbf.append("fmax:\""+obj[5]+"\",");         // 最大载荷
	        dataSbf.append("fmin:\""+obj[6]+"\",");         // 最小载荷
	        dataSbf.append("stroke:\""+obj[7]+"\",");         // 冲程
	        dataSbf.append("spm:\""+obj[8]+"\",");         // 冲次
	        dataSbf.append("liquidProduction:\""+obj[9]+"\",");         // 日累计产液量
	        
	        dataSbf.append("resultCode:\""+resultCode+"\",");         // 工况代码
	        dataSbf.append("resultName:\""+(workType!=null?workType.getResultName():"")+"\",");         // 工况类型
	        dataSbf.append("optimizationSuggestion:\""+(workType!=null?workType.getOptimizationSuggestion():"")+"\",");         // 优化建议
	        
	        dataSbf.append("rodStressRatio1:"+rodStressRatio1+",");       // 一级应力百分比
	        dataSbf.append("rodStressRatio2:"+rodStressRatio2+",");       // 二级应力百分比 
	        dataSbf.append("rodStressRatio3:"+rodStressRatio3+",");           // 三级应力百分比
	        dataSbf.append("rodStressRatio4:"+rodStressRatio4+",");           // 四级应力百分比
	        
	        dataSbf.append("pumpEff1:"+StringManagerUtils.stringToFloat(obj[12]==null?"":obj[12].toString(),1)+",");       // 冲程损失系数
	        dataSbf.append("pumpEff2:"+StringManagerUtils.stringToFloat(obj[13]==null?"":obj[13].toString().toString(),1)+",");       // 充满系数
	        dataSbf.append("pumpEff3:"+StringManagerUtils.stringToFloat(obj[14]==null?"":obj[14].toString().toString(),1)+",");           // 漏失系数
	        dataSbf.append("pumpEff4:"+StringManagerUtils.stringToFloat(obj[15]==null?"":obj[15].toString().toString(),1)+",");           // 液体收缩系数
	        dataSbf.append("pumpFSDiagramData:\""+pumpFSDiagramData+"\",");         // 泵功图数据
	        dataSbf.append("positionCurveData:\""+positionCurveData+"\",");         
	        dataSbf.append("loadCurveData:\""+loadCurveData+"\""); 
	        dataSbf.append("}");
		}else{
			dataSbf.append("{success:true,");
			dataSbf.append("deviceName:\""+deviceName+"\",");
	        dataSbf.append("acqTime:\"\",");
	        dataSbf.append("pointCount:\""+pointCount+"\","); 
	        dataSbf.append("upperLoadLine:\"\",");  
	        dataSbf.append("lowerLoadLine:\"\","); 
	        dataSbf.append("fmax:\"\",");  
	        dataSbf.append("fmin:\"\",");
	        dataSbf.append("stroke:\"\",");  
	        dataSbf.append("spm:\"\","); 
	        dataSbf.append("liquidProduction:\"\",");  
	        dataSbf.append("resultName:\"\",");
	        dataSbf.append("resultCode:\"\",");  
	        dataSbf.append("optimizationSuggestion:\"\",");
	        dataSbf.append("rodStressRatio1:\"\","); 
	        dataSbf.append("rodStressRatio2:\"\",");  
	        dataSbf.append("rodStressRatio3:\"\",");
	        dataSbf.append("rodStressRatio4:\"\",");  
	        dataSbf.append("pumpEff1:\"\","); 
	        dataSbf.append("pumpEff2:\"\",");  
	        dataSbf.append("pumpEff3:\"\",");
	        dataSbf.append("pumpEff4:\"\",");  
	        dataSbf.append("pumpFSDiagramData:\"\",");
	        dataSbf.append("positionCurveData:\"\",");
	        dataSbf.append("loadCurveData:\"\"");
	        dataSbf.append("}");
		}
		return dataSbf.toString().replaceAll("null", "");
	}

	public String querySingleDetailsSurfaceData(int id,String deviceName,String language) throws SQLException, IOException{
		byte[] bytes; 
		ConfigFile configFile=Config.getInstance().configFile;
		BufferedInputStream bis = null;
        StringBuffer dataSbf = new StringBuffer();
        StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
        String tableName="tbl_srpacqdata_latest";
        
        String sql="select well.deviceName, to_char(t.fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
        		+ " t.upstrokewattmax,t.downstrokewattmax,t.wattdegreebalance,t.upstrokeimax,t.downstrokeimax,t.idegreebalance,t.deltaRadius*100,"
        		+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
        		+ " t.crankangle,t.loadtorque,t.cranktorque,t.currentbalancetorque,t.currentnettorque,"
        		+ " t.expectedbalancetorque,t.expectednettorque,"
        		+ " t.polishrodV,t.polishrodA "
        		+ " from "+tableName+" t"
        		+ " left outer join tbl_device well on t.deviceid=well.id"
        		+ " where 1=1 ";
        sql+=" and t.deviceid="+id;
		List<?> list=this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			String positionCurveData="";
			String loadCurveData="";
			String wattCurveData="";
			String iCurveData="";
			String crankAngle="",loadRorque="",crankTorque="",currentBalanceTorque="",currentNetTorque="",expectedBalanceTorque="",expectedNetTorque="";
			String polishrodV="",polishrodA="";
			SerializableClobProxy   proxy=null;
			CLOB realClob=null;
			
			if(obj[9]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[9]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				positionCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[10]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[10]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				loadCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[11]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[11]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				wattCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[12]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[12]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				iCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[13]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[13]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				crankAngle=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[14]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[14]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				loadRorque=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[15]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[15]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				crankTorque=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[16]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[16]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				currentBalanceTorque=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[17]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[17]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				currentNetTorque=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[18]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[18]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				expectedBalanceTorque=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[19]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[19]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				expectedNetTorque=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[20]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[20]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				polishrodV=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[21]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[21]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				polishrodA=StringManagerUtils.CLOBtoString(realClob);
			}
			
	        dataSbf.append("{success:true,");
	        dataSbf.append("deviceName:\""+deviceName+"\",");           // 设备名称
	        dataSbf.append("acqTime:\""+obj[1]+"\",");         // 时间
	        dataSbf.append("upStrokeWattMax:\""+obj[2]+"\",");         
	        dataSbf.append("downStrokeWattMax:\""+obj[3]+"\",");
	        dataSbf.append("wattDegreeBalance:\""+obj[4]+"\",");
	        dataSbf.append("upStrokeIMax:\""+obj[5]+"\",");
	        dataSbf.append("downStrokeIMax:\""+obj[6]+"\",");
	        dataSbf.append("iDegreeBalance:\""+obj[7]+"\",");
	        dataSbf.append("deltaRadius:\""+obj[8]+"\",");
	        dataSbf.append("positionCurveData:\""+positionCurveData+"\",");
	        dataSbf.append("loadCurveData:\""+loadCurveData+"\",");
	        dataSbf.append("powerCurveData:\""+wattCurveData+"\",");
	        dataSbf.append("currentCurveData:\""+iCurveData+"\",");
	        dataSbf.append("crankAngle:\""+crankAngle+"\","); 
	        dataSbf.append("loadRorque:\""+loadRorque+"\","); 
	        dataSbf.append("crankTorque:\""+crankTorque+"\","); 
	        dataSbf.append("currentBalanceTorque:\""+currentBalanceTorque+"\","); 
	        dataSbf.append("currentNetTorque:\""+currentNetTorque+"\","); 
	        dataSbf.append("expectedBalanceTorque:\""+expectedBalanceTorque+"\","); 
	        dataSbf.append("expectedNetTorque:\""+expectedNetTorque+"\","); 
	        dataSbf.append("polishrodV:\""+polishrodV+"\","); 
	        dataSbf.append("polishrodA:\""+polishrodA+"\""); 
	        dataSbf.append("}");
	        
		}else{
			dataSbf.append("{success:true,");
	        dataSbf.append("deviceName:\""+deviceName+"\",");           // 设备名称
	        dataSbf.append("acqTime:\"\",");         // 时间
	        dataSbf.append("upStrokeWattMax:\"\",");         
	        dataSbf.append("downStrokeWattMax:\"\",");
	        dataSbf.append("wattDegreeBalance:\"\",");
	        dataSbf.append("upStrokeIMax:\"\",");
	        dataSbf.append("downStrokeIMax:\"\",");
	        dataSbf.append("iDegreeBalance:\"\",");
	        dataSbf.append("deltaRadius:\"\",");
	        dataSbf.append("positionCurveData:\"\",");
	        dataSbf.append("loadCurveData:\"\",");
	        dataSbf.append("powerCurveData:\"\",");
	        dataSbf.append("currentCurveData:\"\",");
	        dataSbf.append("crankAngle:\"\","); 
	        dataSbf.append("loadRorque:\"\","); 
	        dataSbf.append("crankTorque:\"\","); 
	        dataSbf.append("currentBalanceTorque:\"\","); 
	        dataSbf.append("currentNetTorque:\"\","); 
	        dataSbf.append("expectedBalanceTorque:\"\","); 
	        dataSbf.append("expectedNetTorque:\"\","); 
	        dataSbf.append("polishrodV:\"\","); 
	        dataSbf.append("polishrodA:\"\""); 
	        dataSbf.append("}");
		}
		return dataSbf.toString().replaceAll("null", "");
	}
	
	public String getUIKitAccessToken(String videoKeyId) throws SQLException, IOException{
		StringBuffer dataSbf = new StringBuffer();
		AccessToken accessToken=null;
		boolean success=false;
		String accessTokenStr="";
		long expireTime=0;
		List<Integer> isList=new ArrayList<>();
		isList.add(StringManagerUtils.stringToInteger(videoKeyId));
		try {
			accessToken=MemoryDataManagerTask.getUIKitAccessTokenById(StringManagerUtils.stringToInteger(videoKeyId));
			if(accessToken!=null&&"200".equalsIgnoreCase(accessToken.getCode())){
				success=true;
				accessTokenStr=accessToken.getData().getAccessToken();
				expireTime=accessToken.getData().getExpireTime();
			}
			dataSbf.append("{\"success\":"+success+",\"accessToken\":\""+accessTokenStr+"\",\"expireTime\":"+expireTime+"}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		
		return dataSbf.toString();
	}
	
	public String getCalculateTypeDeviceCount(String orgId,String deviceType,String calculateType){
		int deviceCount=0;
		try{
			String deviceTableName="viw_device";
			
			String sql="select count(1) from "+deviceTableName+" t "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNum(deviceType)){
				sql+= " and t.devicetype="+deviceType;
			}else{
				sql+= " and t.devicetype in ("+deviceType+")";
			}	
			sql+= " and t.calculateType="+calculateType;
			List<?> list = this.findCallSql(sql);
			if(list.size()>0){
				deviceCount=StringManagerUtils.stringToInteger(list.get(0)+"");
			}
		}catch(Exception e){
			deviceCount=1;
		}
		String json="{\"success\":true,\"deviceCount\":"+deviceCount+"}";
		return json;
	}
	
	public String getDeviceAddInfoAndControlInfo(String deviceId,String deviceType){
		int videoNum=0;
		int controlItemNum=0;
		int addInfoNum=0;
		int auxiliaryDeviceNum=0;
		
		String videoSql="select t.videourl1,t.videourl2 "
				+ " from TBL_DEVICE t "
				+ " where t.id="+deviceId;
		String controlItemSql="select t4.itemname "
				+ " from TBL_DEVICE t,tbl_protocoldisplayinstance t2,tbl_display_unit_conf t3,tbl_display_items2unit_conf t4 "
				+ " where t.displayinstancecode=t2.code and t2.displayunitid=t3.id and t3.id=t4.unitid and t4.type=2 "
				+ " and t.id="+deviceId;
		String addInfoSql="select t2.itemname "
				+ " from tbl_device t,tbl_deviceaddinfo t2 "
				+ " where t.id=t2.deviceid "
				+ " and t.id="+deviceId;
		String auxiliaryDeviceSql="select t3.name,t3.manufacturer,t3.model "
				+ " from tbl_device t,tbl_auxiliary2master t2,tbl_auxiliarydevice t3 "
				+ " where t.id=t2.masterid and t2.auxiliaryid=t3.id "
				+ " and t.id="+deviceId;
		
		List<?> videoList = this.findCallSql(videoSql);
		List<?> controlItemList = this.findCallSql(controlItemSql);
		List<?> addInfoList = this.findCallSql(addInfoSql);
		List<?> auxiliaryDeviceList = this.findCallSql(auxiliaryDeviceSql);
		
		if(videoList.size()>0){
			Object[] obj=(Object[])videoList.get(0);
			if(StringManagerUtils.isNotNull(obj[0]+"")){
				videoNum+=1;
			}
			if(StringManagerUtils.isNotNull(obj[1]+"")){
				videoNum+=1;
			}
		}
		controlItemNum=controlItemList.size();
		addInfoNum=addInfoList.size();
		auxiliaryDeviceNum=auxiliaryDeviceList.size();
		
		String json="{"
				+ "\"videoNum\":"+videoNum+","
				+ "\"controlItemNum\":"+controlItemNum+","
				+ "\"addInfoNum\":"+addInfoNum+","
				+ "\"auxiliaryDeviceNum\":"+auxiliaryDeviceNum
				+ "}";
		return json;
	}
}
