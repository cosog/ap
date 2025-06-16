package com.cosog.service.mobile;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cosog.dao.BaseDao;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.Code;
import com.cosog.model.DataMapping;
import com.cosog.model.KeyValue;
import com.cosog.model.Org;
import com.cosog.model.User;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AcqInstanceOwnItem;
import com.cosog.model.calculate.AlarmInstanceOwnItem;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.DisplayInstanceOwnItem;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.PCPProductionData;
import com.cosog.model.calculate.PumpingPRTFData;
import com.cosog.model.calculate.SRPCalculateRequestData;
import com.cosog.model.calculate.SRPDeviceInfo;
import com.cosog.model.calculate.SRPProductionData;
import com.cosog.model.calculate.UserInfo;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.service.right.UserManagerService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.task.MemoryDataManagerTask.CalItem;
import com.cosog.utils.AcquisitionItemColumnsMap;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.Page;
import com.cosog.utils.PageHandler;
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

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.SerializableClobProxy;


@SuppressWarnings("deprecation")
@Component("mobileService")
public class MobileService<T> extends BaseService<T> {
	@SuppressWarnings("unused")
	private BaseDao dao;
	@SuppressWarnings("unused")
	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	@Autowired
	private UserManagerService<User> userManagerService;
	
	public List<T> getOrganizationData(Class<Org> class1, String userAccount) {
		String queryString="";
		if(StringManagerUtils.isNotNull(userAccount)){
			String sql="select t.user_orgid from tbl_user t where t.user_id='"+userAccount+"'";
			List<?> list = this.findCallSql(sql);
			String orgId="";
			if(list.size()>0){
				orgId=list.get(0).toString();
			}
			if("0".equals(orgId)){
				queryString = "SELECT {Org.*} FROM tbl_org Org   "
						+ " order by Org.org_code  ";
			}else{
				queryString = "SELECT {Org.*} FROM tbl_org Org   "
						+ " start with Org.org_id=( select t2.user_orgid from tbl_user t2 where t2.user_id='"+userAccount+"' )   "
						+ " connect by Org.org_parent= prior Org.org_id "
						+ " order by Org.org_code  ";
			}
		}else{
			queryString = "SELECT {Org.*} FROM tbl_org Org   "
					+ " order by Org.org_code  ";
		}
		
		return getBaseDao().getSqlToHqlOrgObjects(queryString);
	}
	
	public String getRealtimeStatisticsData(String data){
		List<String> deviceNameList=new ArrayList<>();
		String json="";
		int type=1;
		String user = "";
		String password = "";
		if(StringManagerUtils.isNotNull(data)){
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
				
				try{
					type=jsonObject.getInt("StatType");
				}catch(Exception e){
					e.printStackTrace();
				}

				try{
					JSONArray jsonArray = jsonObject.getJSONArray("DeviceList");
					for(int i=0;jsonArray!=null&&i<jsonArray.size();i++){
						deviceNameList.add(jsonArray.getString(i));
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		try{
			if(type==1){
				json=this.getRealTimeMonitoringCommStatusStatData(user,password,deviceNameList);
			}else if(type==2){
				json=this.getRealTimeMonitoringRunStatusStatData(user,password,deviceNameList);
			}else if(type==3){
				json=this.getRealTimeMonitoringFESDiagramResultStatData(user,password,deviceNameList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return json;
	}
	
	public String getRealTimeMonitoringFESDiagramResultStatData(String user,String password,List deviceNameList) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",\"DataList\":[");
		List<?> list=null;
		if(userCheckSign==1){
			User userInfo=this.userManagerService.getUser(user, password);
			String language=userInfo!=null?userInfo.getLanguageName():"";
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			String orgSql="select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent";
			list = this.findCallSql(orgSql);
			
			StringBuffer orgBuff = new StringBuffer();
			for(int i=0;i<list.size();i++){
				orgBuff.append(list.get(i)+",");
			}
			if(orgBuff.toString().endsWith(",")){
				orgBuff.deleteCharAt(orgBuff.length() - 1);
			}
			String orgId=orgBuff.toString();
			
			List<DeviceInfo> deviceList=null;
			boolean jedisStatus=false;
			try{
				try{
					jedisStatus=MemoryDataManagerTask.getJedisStatus();
					deviceList=MemoryDataManagerTask.getDeviceInfoByOrgIdArr(orgId.split(","));
				}catch(Exception e){
					e.printStackTrace();
				}
				int totalCount=0;
				if(jedisStatus){
					if(deviceList!=null){
						Map<Integer,Integer> totalMap=new TreeMap<Integer,Integer>();
						for(int i=0;i<deviceList.size();i++){
							DeviceInfo deviceInfo=deviceList.get(i);
							if(StringManagerUtils.stringToArrExistNum(orgId, deviceInfo.getOrgId()) 
//									&& StringManagerUtils.stringToArrExistNum(deviceType, deviceInfo.getDeviceType()) 
									&& deviceInfo.getCalculateType()==1){
								if(deviceNameList.size()==0 || (deviceNameList.size()>0 && StringManagerUtils.existOrNot(deviceNameList, deviceInfo.getDeviceName(), true) ) ){
									int count=1;
									int resultCode=deviceInfo.getResultCode()==null?0:deviceInfo.getResultCode();
									if(totalMap.containsKey(resultCode)){
										count=totalMap.get(resultCode)+1;
									}
									totalMap.put(resultCode, count);
								}
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
							result_json.append("{\"Item\":\""+item+"\",");
							result_json.append("\"Count\":"+totalMap.get(key)+"},");
							index++;
						}
						
						if(result_json.toString().endsWith(",")){
							result_json.deleteCharAt(result_json.length() - 1);
						}
						result_json.append("]");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				
			}
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getRealTimeMonitoringCommStatusStatData(String user,String password,List<String> deviceNameList) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int online=0,goOnline=0,offline=0;
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign);
		if(userCheckSign==1){
			User userInfo=this.userManagerService.getUser(user, password);
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo!=null?userInfo.getLanguageName():"");
			
			
			String tableName="tbl_acqdata_latest";
			String deviceTableName="viw_device";
			
			String sql="select t2.commstatus,count(1) from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on  t2.deviceId=t.id "
					+ " where t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent) ";
			if(deviceNameList.size()>0){
				sql+=" and t.deviceName in ( "+StringManagerUtils.joinStringArr2(deviceNameList, ",")+" )";
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
			result_json.append(",\"DataList\":[");
			result_json.append("{\"Item\":'"+languageResourceMap.get("online")+"',");
			result_json.append("\"Count\":"+online+"},");
			result_json.append("{\"Item\":'"+languageResourceMap.get("goOnline")+"',");
			result_json.append("\"Count\":"+goOnline+"},");
			result_json.append("{\"Item\":'"+languageResourceMap.get("offline")+"',");
			result_json.append("\"Count\":"+offline+"}]");
		}
		
		result_json.append("}");
	
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getRealTimeMonitoringRunStatusStatData(String user,String password,List<String> deviceNameList) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int run=0,stop=0,noData=0,offline=0,goOnline=0;
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign);
		if(userCheckSign==1){
			User userInfo=this.userManagerService.getUser(user, password);
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo!=null?userInfo.getLanguageName():"");
			
			String tableName="tbl_acqdata_latest";
			String deviceTableName="viw_device";
			String sql="select decode(t2.commstatus,0,-1,2,-2,decode(t2.runstatus,null,2,t2.runstatus)) as runstatus,count(1) from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on  t2.deviceId=t.id "
					+ " where 1=1 ";
			if(deviceNameList.size()>0){
				sql+=" and t.deviceName in ( "+StringManagerUtils.joinStringArr2(deviceNameList, ",")+" )";
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
			result_json.append(",\"DataList\":[");
			result_json.append("{\"Item\":'"+languageResourceMap.get("run")+"',\"Count\":"+run+"},");
			result_json.append("{\"Item\":'"+languageResourceMap.get("stop")+"',\"Count\":"+stop+"},");
			result_json.append("{\"Item\":'"+languageResourceMap.get("emptyMsg")+"',\"Count\":"+noData+"},");
			result_json.append("{\"Item\":'"+languageResourceMap.get("goOnline")+"',\"Count\":"+goOnline+"},");
			result_json.append("{\"Item\":'"+languageResourceMap.get("offline")+"',\"Count\":"+offline+"}]");
		}
		
		result_json.append("}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getDeviceRealTimeOverview(String data,Page pager)throws Exception {
		String json = "";
		List<String> deviceList= new ArrayList<>();
		int statType=1;
		String statValue="";
		String user="";
		String password="";
		if(StringManagerUtils.isNotNull(data)){
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
				
				try{
					statType=jsonObject.getInt("StatType");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					statValue=jsonObject.getString("StatValue");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					JSONArray jsonArray = jsonObject.getJSONArray("DeviceList");
					for(int i=0;jsonArray!=null&&i<jsonArray.size();i++){
						deviceList.add(jsonArray.getString(i));
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		json=getDeviceRealTimeOverview(user,password,deviceList, statType, statValue);
		
		return json;
	}
	
	
	public String getDeviceRealTimeOverview(String user,String password,List<String> deviceList,int statType,String statValue) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",");
		result_json.append("\"DataList\":[");
		if(userCheckSign==1){
			User u=this.userManagerService.getUser(user, password);
			String language=u!=null?u.getLanguageName():"";
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			
			
			AlarmShowStyle alarmShowStyle=null;
			AcqInstanceOwnItem acqInstanceOwnItem=null;
			DisplayInstanceOwnItem displayInstanceOwnItem=null;
			AlarmInstanceOwnItem alarmInstanceOwnItem=null;
			List<CalItem> calItemList=null;
			List<CalItem> inputItemList=null;
			UserInfo userInfo=MemoryDataManagerTask.getUserInfoByNo(u!=null?(u.getUserNo()+""):"");
			
			ModbusProtocolConfig.Protocol protocol=null;
			
			String acqInstanceCode="";
			String displayInstanceCode="";
			String alarmInstanceCode="";
			
			DeviceInfo deviceInfo=null;
			String deviceInfoKey="DeviceInfo";
			
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
			Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
			
			
			String realtimeTableName="tbl_acqdata_latest";
			String deviceTableName="VIW_DEVICE";
			
			String calAndInputTableName="tbl_srpacqdata_latest";
			
			
			String sql="select t.id,t.devicename,"//0~1
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"//2
					+ "t2.commstatus,decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,"//3~4
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"//5~7
					+ "decode(t2.runstatus,null,2,t2.runstatus),decode(t2.commstatus,1,decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"'),'') as runStatusName,"//8~9
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"//10~12
					+" t.deviceTypeName_"+language+","//13
					+ "t.calculateType";//14
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+realtimeTableName+" t2 on t2.deviceId=t.id"
					+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  ";
			
			if(deviceList.size()>0){
				sql+= " and t.devicename in("+StringManagerUtils.joinStringArr2(deviceList, ",")+")";
			}
			
			if(StringManagerUtils.isNotNull(statValue)){
				if(statType==1){
					sql+=" and decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"')='"+statValue+"'";
				}else if(statType==2){
					sql+=" and decode(t2.commstatus,0,'"+languageResourceMap.get("offline")+"',2,'"+languageResourceMap.get("goOnline")+"',decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"'))='"+statValue+"'";
				}else if(statType==3){
					if(statValue.equalsIgnoreCase(languageResourceMap.get("emptyMsg"))){
						sql+=" and (t3.resultcode=0 t3.resultcode is null)";
					}else{
						sql+=" and t3.resultcode="+MemoryDataManagerTask.getWorkTypeByName(statValue,language).getResultCode();
					}
				}
			}
			sql+=" order by t.sortnum,t.devicename";
			
			List<?> list = this.findCallSql(sql);
			StringBuffer acqDataBuf = new StringBuffer();
			StringBuffer calDataBuf = new StringBuffer();
			StringBuffer inputDataBuf = new StringBuffer();
			for(int i=0;i<list.size();i++){
				Object[] deviceObj=(Object[]) list.get(i);
				
				List<ModbusProtocolConfig.Items> protocolItems=new ArrayList<ModbusProtocolConfig.Items>();
				List<CalItem> displayCalItemList=new ArrayList<CalItem>();
				List<CalItem> displayInputItemList=new ArrayList<CalItem>();
				Map<String,DisplayInstanceOwnItem.DisplayItem> dailyTotalCalItemMap=new LinkedHashMap<>();
				
				acqDataBuf = new StringBuffer();
				calDataBuf = new StringBuffer();
				inputDataBuf = new StringBuffer();
				acqDataBuf.append("[");
				calDataBuf.append("[");
				inputDataBuf.append("[");
				
				String deviceId=deviceObj[0]+"";
				String commStatusName=deviceObj[4]+"";
				String runStatusName=deviceObj[9]+"";
				String calculateType=deviceObj[14]+"";
				
				if(StringManagerUtils.stringToInteger(calculateType)==1){
					calAndInputTableName="tbl_srpacqdata_latest";
				}else if(StringManagerUtils.stringToInteger(calculateType)==2){
					calAndInputTableName="tbl_pcpacqdata_latest";
				}
				
				deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
				if(deviceInfo!=null){
					displayInstanceCode=deviceInfo.getDisplayInstanceCode();
					alarmInstanceCode=deviceInfo.getAlarmInstanceCode();
					acqInstanceCode=deviceInfo.getInstanceCode();
				}
				
				alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
				acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(acqInstanceCode);
				displayInstanceOwnItem=MemoryDataManagerTask.getDisplayInstanceOwnItemByCode(displayInstanceCode);
				alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(alarmInstanceCode);
				
				if(displayInstanceOwnItem!=null){
					protocol=MemoryDataManagerTask.getProtocolByName(displayInstanceOwnItem.getProtocol());
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
				
				
				
				int commAlarmLevel=0,runAlarmLevel=0;
				if(alarmInstanceOwnItem!=null){
					for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
						if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
							commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
							runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}
					}
				}
				
				result_json.append("{\"Id\":"+deviceId+",");
				result_json.append("\"DeviceName\":\""+deviceObj[1]+"\",");
				result_json.append("\"DeviceType\":\""+deviceObj[13]+"\",");
				result_json.append("\"AcqTime\":\""+deviceObj[2]+"\",");
				result_json.append("\"CommStatus\":\""+commStatusName+"\",");
				result_json.append("\"CommTime\":\""+deviceObj[5]+"\",");
				result_json.append("\"CommTimeEfficiency\":\""+deviceObj[6]+"\",");
				result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(deviceObj[7])+"\",");
				
				result_json.append("\"RunStatus\":\""+runStatusName+"\",");
				result_json.append("\"RunTime\":\""+deviceObj[10]+"\",");
				result_json.append("\"RunTimeEfficiency\":\""+deviceObj[11]+"\",");
				result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(deviceObj[12])+"\",");
				
				result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+",");
				
				
				
				if(displayInstanceOwnItem!=null&&userInfo!=null&&protocol!=null){
					String displayItemSql="select t.unitid,t.id as itemid,t.itemname,t.itemcode,t.bitindex,"
							+ "decode(t.showlevel,null,9999,t.showlevel) as showlevel,"
							+ "decode(t.realtimeSort,null,9999,t.realtimeSort) as realtimeSort,"
							+ "decode(t.historySort,null,9999,t.historySort) as historySort,"
							+ "t.realtimecurveconf,t.historycurveconf,"
							+ "t.type "
							+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2 "
							+ " where t.unitid=t2.id and t2.id="+displayInstanceOwnItem.getUnitId()
							+ " and t.type<>2"
							+ " and t.historyData=1"
							+ " and decode(t.showlevel,null,9999,t.showlevel)>="+userInfo.getRoleShowLevel();
					displayItemSql+=" order by t.historySort,t.type,t.id";
					List<?> displayItemQueryList = this.findCallSql(displayItemSql);
					List<DisplayInstanceOwnItem.DisplayItem> displayItemList=new ArrayList<>();
					for(int j=0;j<displayItemQueryList.size();j++){
						Object[] displayItemObj=(Object[]) displayItemQueryList.get(j);
						DisplayInstanceOwnItem.DisplayItem displayItem=new DisplayInstanceOwnItem.DisplayItem();
						displayItem.setUnitId(StringManagerUtils.stringToInteger(displayItemObj[0]+""));
	    				displayItem.setItemId(StringManagerUtils.stringToInteger(displayItemObj[1]+""));
	    				displayItem.setItemName(displayItemObj[2]+"");
	    				displayItem.setItemCode(displayItemObj[3]+"");
	    				displayItem.setBitIndex(StringManagerUtils.stringToInteger(displayItemObj[4]+""));
	    				displayItem.setShowLevel(StringManagerUtils.stringToInteger(displayItemObj[5]+""));
	    				displayItem.setRealtimeSort(StringManagerUtils.stringToInteger(displayItemObj[6]+""));
	    				displayItem.setHistorySort(StringManagerUtils.stringToInteger(displayItemObj[7]+""));
	    				displayItem.setRealtimeCurveConf(displayItemObj[8]+"");
	    				displayItem.setHistoryCurveConf(displayItemObj[9]+"");
	    				displayItem.setType(StringManagerUtils.stringToInteger(displayItemObj[10]+""));
	    				displayItemList.add(displayItem);
					}
					
					
					//采集项
					for(int j=0;j<protocol.getItems().size();j++){
						if((!"w".equalsIgnoreCase(protocol.getItems().get(j).getRWType())) 
								&& (StringManagerUtils.existDisplayItem(displayInstanceOwnItem.getItemList(), protocol.getItems().get(j).getTitle(), false))){
							for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
								if(displayInstanceOwnItem.getItemList().get(k).getType()==0 
										&& displayInstanceOwnItem.getItemList().get(k).getHistoryData()==1
										&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
										&& protocol.getItems().get(j).getTitle().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemName())
										){
									protocolItems.add(protocol.getItems().get(j));
									break;
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
											&& displayInstanceOwnItem.getItemList().get(k).getHistoryData()==1
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
											&& displayInstanceOwnItem.getItemList().get(k).getHistoryData()==1
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
											&& displayInstanceOwnItem.getItemList().get(k).getHistoryData()==1
											&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
											&& calItem.getCode().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode())){
										displayInputItemList.add(calItem);
										break;
									}
								}
								
							}
						}
					}
					
					
					for(int k=0;k<displayItemList.size();k++){
						if(displayItemList.get(k).getType()!=2 && displayItemList.get(k).getShowLevel()>=userInfo.getRoleShowLevel()){
							String header=displayItemList.get(k).getItemName();
							String dataIndex=displayItemList.get(k).getItemCode();
							String unit="";
							
							if(displayItemList.get(k).getType()==0){
								ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, header);
								if(item!=null){
									unit=item.getUnit();
								}
							}else if(displayItemList.get(k).getType()==1){
								if(dailyTotalCalItemMap.containsKey(dataIndex.toUpperCase())){
									DisplayInstanceOwnItem.DisplayItem displayItem=dailyTotalCalItemMap.get(dataIndex.toUpperCase()) ;
									if(displayItem!=null){
										ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, displayItem.getItemSourceName());
										if(item!=null){
											unit=item.getUnit();
										}
									}
								}else{
									CalItem calItem=MemoryDataManagerTask.getSingleCalItemByCode(dataIndex, calItemList);
									if(calItem!=null){
										header=calItem.getName();
										unit=calItem.getUnit();
									}
								}
								
								
							}else if(displayItemList.get(k).getType()==3){
								CalItem calItem=MemoryDataManagerTask.getSingleCalItemByCode(dataIndex, inputItemList);
								if(calItem!=null){
									header=calItem.getName();
									unit=calItem.getUnit();
								}
							}
							
							for(ModbusProtocolConfig.Items item:protocol.getItems()){
								if(item.getResolutionMode()==0 
										&& displayItemList.get(k).getItemName().equalsIgnoreCase(item.getTitle())
										&& item.getMeaning()!=null
										&& item.getMeaning().size()>0
										){//开关量处理
									for(ModbusProtocolConfig.ItemsMeaning itemsMeaning: item.getMeaning()){
										if(displayItemList.get(k).getBitIndex()==itemsMeaning.getValue()){
											header=itemsMeaning.getMeaning();
											dataIndex+="_"+itemsMeaning.getValue();
											break;
										}
									}
									break;
								}
							}
							
							if(StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))){
								header+="("+unit+")";
							}
							
							displayItemList.get(k).setItemName(header);
						}
					}
				}
				
				
				sql="select t2.id,t.devicename,"//0~1
						+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"//2
						+ "t2.acqdata";
				
				if(StringManagerUtils.stringToInteger(calculateType)>0){
					for(CalItem calItem:displayCalItemList){
						String column=calItem.getCode();
						if("resultName".equalsIgnoreCase(column)){
							column="resultCode";
						}
						sql+=",t3."+column;
					}
					if(displayInputItemList.size()>0){
						sql+=",t3.productiondata";
					}
				}else{
					for(int j=0;j<displayCalItemList.size();i++){
						String column=displayCalItemList.get(i).getCode();
						sql+=",t2."+column;
					}
				}
				
				sql+= " from "+deviceTableName+" t "
						+ " left outer join "+realtimeTableName+" t2 on t2.deviceid=t.id";
				if(StringManagerUtils.isNotNull(calAndInputTableName)&&(displayCalItemList.size()>0 || displayInputItemList.size()>0)){
					sql+=" left outer join "+calAndInputTableName+" t3 on t3.deviceid=t.id";
				}
				
				sql+= " where t.id="+deviceId;
				
				List<?> realtimeDataLlist = this.findCallSql(sql);
				String dailyTotalDatasql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
						+ "t.itemcolumn,t.itemname,"
						+ "t.totalvalue,t.todayvalue "
						+ " from TBL_DAILYTOTALCALCULATE_LATEST t,"+deviceTableName+" t2 "
						+ " where t.deviceId=t2.id"
						+ " and t.deviceId="+deviceId;
				List<?> dailyTotalDatasList = this.findCallSql(dailyTotalDatasql);
				
				for(int j=0;j<realtimeDataLlist.size();j++){
					List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
					
					Object[] realtimeDataObj=(Object[]) realtimeDataLlist.get(j);
					String acqData=StringManagerUtils.CLOBObjectToString(realtimeDataObj[3]);
					
					
					if(displayInputItemList.size()>0){
						String productionData=(realtimeDataObj[realtimeDataObj.length-1]+"").replaceAll("null", "");
						if(StringManagerUtils.stringToInteger(calculateType)==1){
							type = new TypeToken<SRPCalculateRequestData>() {}.getType();
							SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
							
							for(CalItem calItem:displayInputItemList){
								String itemName=calItem.getName();
								String value="";
								String unit=calItem.getUnit();
								String column=calItem.getCode();
								int alarmLevel=0;
								
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
											itemName=languageResourceMap.get("reservoirDepth_cbm");
										}
									}else if("ReservoirTemperature".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
										value=srpProductionData.getReservoir().getTemperature()+"";
										if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
											itemName=languageResourceMap.get("reservoirTemperature_cbm");
										}
									}else if("TubingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
										value=srpProductionData.getProduction().getTubingPressure()+"";
										if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
											itemName=languageResourceMap.get("tubingPressure_cbm");
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
								
								itemName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(itemName+"("+unit+")"):itemName;
								inputDataBuf.append("{\"ItemName\":\""+itemName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
							}
						}else if(StringManagerUtils.stringToInteger(calculateType)==2){
							type = new TypeToken<PCPCalculateRequestData>() {}.getType();
							PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
							for(CalItem calItem:displayInputItemList){
								String itemName=calItem.getName();
								String value="";
								String unit=calItem.getUnit();
								String column=calItem.getCode();
								int alarmLevel=0;
								
								
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
											itemName=languageResourceMap.get("reservoirDepth_cbm");
										}
									}else if("ReservoirTemperature".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
										value=pcpProductionData.getReservoir().getTemperature()+"";
										if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
											itemName=languageResourceMap.get("reservoirTemperature_cbm");
										}
									}else if("TubingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
										value=pcpProductionData.getProduction().getTubingPressure()+"";
										if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
											itemName=languageResourceMap.get("tubingPressure_cbm");
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
								itemName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(itemName+"("+unit+")"):itemName;
								inputDataBuf.append("{\"ItemName\":\""+itemName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
							}
						}
					}
					
					for(CalItem calItem:displayCalItemList){
						int index=j+4;
						if(index<realtimeDataObj.length-1){
							String itemName=calItem.getName();
							String value=realtimeDataObj[index]+"";
							if(realtimeDataObj[index] instanceof CLOB || realtimeDataObj[index] instanceof Clob){
								value=StringManagerUtils.CLOBObjectToString(realtimeDataObj[index]);
							}
							String column=calItem.getCode();
							String unit=calItem.getUnit();
							int alarmLevel=0;
							for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
								if(column.equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
									//如果是工况
									if("resultCode".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())||"resultName".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
										WorkType workType=MemoryDataManagerTask.getWorkTypeByCode(value,language);
										if(workType!=null){
											value=workType.getResultName();
											for(AlarmInstanceOwnItem.AlarmItem alarmItem:alarmInstanceOwnItem.getItemList()){
												if(alarmItem.getAlarmLevel()>0 && alarmItem.getType()==4 && alarmItem.getItemCode().equalsIgnoreCase(workType.getResultCode()+"")){
													alarmLevel=alarmItem.getAlarmLevel();
												}
											}
											
										}
									}
									break;
								}
							}
							
							itemName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(itemName+"("+unit+")"):itemName;
							calDataBuf.append("{\"ItemName\":\""+itemName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
						}
					}
					
					if(protocolItems.size()>0){
						type = new TypeToken<List<KeyValue>>() {}.getType();
						List<KeyValue> acqDataList=gson.fromJson(acqData, type);
						if(acqDataList!=null){
							for(KeyValue keyValue:acqDataList){
								for(ModbusProtocolConfig.Items item: protocolItems){
									String column=loadProtocolMappingColumnByTitleMap.get(item.getTitle()).getMappingColumn();
									if(StringManagerUtils.isNotNull(column) && column.equalsIgnoreCase(keyValue.getKey())){
										String columnName=item.getTitle();
										String rawColumnName=columnName;
										String value=keyValue.getValue();
										String rawValue=value;
										String addr=item.getAddr()+"";
										String columnDataType=item.getIFDataType();
										String resolutionMode=item.getResolutionMode()+"";
										String bitIndex="";
										String unit=item.getUnit();
										int sort=9999;
										int alarmLevel=0;
										if("int".equalsIgnoreCase(item.getIFDataType()) || "uint".equalsIgnoreCase(item.getIFDataType()) || item.getIFDataType().contains("int")
												||"float32".equalsIgnoreCase(item.getIFDataType())
												||"float64".equalsIgnoreCase(item.getIFDataType())){
											if(value.toUpperCase().contains("E")){
							                 	value=StringManagerUtils.scientificNotationToNormal(value);
							                 }
										}
										if(item.getResolutionMode()==1||item.getResolutionMode()==2){//如果是枚举量
											for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
												if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column) && displayInstanceOwnItem.getItemList().get(l).getType()!=2){
													sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
													break;
												}
											}
											
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
											
											columnName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(columnName+"("+unit+")"):columnName;
											acqDataBuf.append("{\"ItemName\":\""+columnName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
											
										}else if(item.getResolutionMode()==0){//如果是开关量
											boolean isMatch=false;
											if(item.getMeaning()!=null&&item.getMeaning().size()>0){
												String[] valueArr=value.split(",");
												for(int l=0;l<item.getMeaning().size();l++){
													isMatch=false;
													columnName=item.getMeaning().get(l).getMeaning();
													sort=9999;
													
													for(int n=0;n<displayInstanceOwnItem.getItemList().size();n++){
														if(displayInstanceOwnItem.getItemList().get(n).getItemCode().equalsIgnoreCase(column) 
																&&displayInstanceOwnItem.getItemList().get(n).getBitIndex()==item.getMeaning().get(l).getValue()
																){
															sort=displayInstanceOwnItem.getItemList().get(n).getRealtimeSort();
															isMatch=true;
															break;
														}
													}
													if(!isMatch){
														continue;
													}
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
																columnName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(columnName+"("+unit+")"):columnName;
																acqDataBuf.append("{\"ItemName\":\""+columnName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
																match=true;
																break;
															}
														}
														if(!match){
															value="";
															rawValue="";
															bitIndex=item.getMeaning().get(l).getValue()+"";
															columnName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(columnName+"("+unit+")"):columnName;
															acqDataBuf.append("{\"ItemName\":\""+columnName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
														}
													}else{
														for(int m=0;m<displayInstanceOwnItem.getItemList().size();m++){
															if(displayInstanceOwnItem.getItemList().get(m).getItemCode().equalsIgnoreCase(column) 
																	&& displayInstanceOwnItem.getItemList().get(m).getBitIndex()==item.getMeaning().get(l).getValue() ){
																sort=displayInstanceOwnItem.getItemList().get(m).getRealtimeSort();
																break;
															}
														}
														value="";
														rawValue="";
														bitIndex=item.getMeaning().get(l).getValue()+"";
														columnName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(columnName+"("+unit+")"):columnName;
														acqDataBuf.append("{\"ItemName\":\""+columnName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
													}
												}
											}else{
												for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
													if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column)){
														sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
														break;
													}
												}
												columnName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(columnName+"("+unit+")"):columnName;
												acqDataBuf.append("{\"ItemName\":\""+columnName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
											}
										}else{//如果是数据量
											for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
												if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column) && displayInstanceOwnItem.getItemList().get(l).getType()!=2){
													sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
													break;
												}
											}
											
											columnName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(columnName+"("+unit+")"):columnName;
											acqDataBuf.append("{\"ItemName\":\""+columnName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
										} 
										break;
									}
								}
							}
						}
						
					}
					
					//获取日汇总计算数据
					if(dailyTotalCalItemMap.size()>0){
						for(int k=0;k<dailyTotalDatasList.size();k++){
							Object[] dailyTotalDataObj=(Object[]) dailyTotalDatasList.get(k);
							if(dailyTotalCalItemMap.containsKey( (dailyTotalDataObj[1]+"").toUpperCase() ) ){
								DisplayInstanceOwnItem.DisplayItem displayItem=dailyTotalCalItemMap.get( (dailyTotalDataObj[1]+"").toUpperCase() );
								if(displayItem!=null){
									String itemName=dailyTotalDataObj[2]+"";
									String value=dailyTotalDataObj[4]+"";
									String unit="";
									String column=dailyTotalDataObj[1]+"";
									int alarmLevel=0;
									ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, displayItem.getItemSourceName());
									if(item!=null){
										unit=item.getUnit();
									}
									itemName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(itemName+"("+unit+")"):itemName;
									calDataBuf.append("{\"ItemName\":\""+itemName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
								}
							}
						}
					}
				}
				
				if(acqDataBuf.toString().endsWith(",")){
					acqDataBuf.deleteCharAt(acqDataBuf.length() - 1);
				}
				
				if(calDataBuf.toString().endsWith(",")){
					calDataBuf.deleteCharAt(calDataBuf.length() - 1);
				}
				
				if(inputDataBuf.toString().endsWith(",")){
					inputDataBuf.deleteCharAt(inputDataBuf.length() - 1);
				}
				
				
				acqDataBuf.append("]");
				calDataBuf.append("]");
				inputDataBuf.append("]");
				
				result_json.append("\"AcquisitionDataList\":"+acqDataBuf.toString()+",");
				result_json.append("\"CalculateDataList\":"+calDataBuf.toString()+",");
				result_json.append("\"InputDataList\":"+inputDataBuf.toString()+"},");
			}
			
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	
	public String getDeviceHistoryData(String data,Page pager)throws Exception {
		String json="";;
		String user="";
		String password="";
		String deviceName="";
		String startDate=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String endDate=startDate;
		int limit=0;
		if(StringManagerUtils.isNotNull(data)){
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
				
				try{
					deviceName=jsonObject.getString("DeviceName");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					startDate=jsonObject.getString("StartDate");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					endDate=jsonObject.getString("EndDate");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					limit=jsonObject.getInt("Limit");
				}catch(Exception e){
					e.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		json=getDeviceHistoryData(user,password,deviceName,limit,startDate,endDate);
		
		return json;
	}
	
	
	public String getDeviceHistoryData(String user,String password,String deviceName,int limit,String startDate,String endDate) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",");
		result_json.append("\"DataList\":[");
		if(userCheckSign==1){
			User u=this.userManagerService.getUser(user, password);
			String language=u!=null?u.getLanguageName():"";
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			
			
			AlarmShowStyle alarmShowStyle=null;
			AcqInstanceOwnItem acqInstanceOwnItem=null;
			DisplayInstanceOwnItem displayInstanceOwnItem=null;
			AlarmInstanceOwnItem alarmInstanceOwnItem=null;
			List<CalItem> calItemList=null;
			List<CalItem> inputItemList=null;
			UserInfo userInfo=MemoryDataManagerTask.getUserInfoByNo(u!=null?(u.getUserNo()+""):"");
			
			ModbusProtocolConfig.Protocol protocol=null;
			
			String acqInstanceCode="";
			String displayInstanceCode="";
			String alarmInstanceCode="";
			
			DeviceInfo deviceInfo=null;
			String deviceInfoKey="DeviceInfo";
			
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
			Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
			
			String realtimeTableName="tbl_acqdata_latest";
			String deviceTableName="VIW_DEVICE";
			
			String historyTableName="tbl_acqdata_hist";
			String calAndInputTableName="tbl_srpacqdata_hist";
			
			
			String sql="select t.id,t.devicename,"//0~1
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"//2
					+ "t2.commstatus,decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,"//3~4
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"//5~7
					+ "decode(t2.runstatus,null,2,t2.runstatus),decode(t2.commstatus,1,decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"'),'') as runStatusName,"//8~9
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"//10~12
					+" t.deviceTypeName_"+language+","//13
					+ "t.calculateType";//14
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+realtimeTableName+" t2 on t2.deviceId=t.id"
					+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  ";
			
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+= " and t.devicename='"+deviceName+"'";
			}
			sql+=" order by t.sortnum,t.devicename";
			
			List<?> list = this.findCallSql(sql);
			StringBuffer acqDataBuf = new StringBuffer();
			StringBuffer calDataBuf = new StringBuffer();
			StringBuffer inputDataBuf = new StringBuffer();
			for(int i=0;i<list.size();i++){
				Object[] deviceObj=(Object[]) list.get(i);
				
				List<ModbusProtocolConfig.Items> protocolItems=new ArrayList<ModbusProtocolConfig.Items>();
				List<CalItem> displayCalItemList=new ArrayList<CalItem>();
				List<CalItem> displayInputItemList=new ArrayList<CalItem>();
				Map<String,DisplayInstanceOwnItem.DisplayItem> dailyTotalCalItemMap=new LinkedHashMap<>();
				
				acqDataBuf = new StringBuffer();
				calDataBuf = new StringBuffer();
				inputDataBuf = new StringBuffer();
				acqDataBuf.append("[");
				calDataBuf.append("[");
				inputDataBuf.append("[");
				
				String deviceId=deviceObj[0]+"";
				String commStatusName=deviceObj[4]+"";
				String runStatusName=deviceObj[9]+"";
				String calculateType=deviceObj[14]+"";
				
				if(StringManagerUtils.stringToInteger(calculateType)==1){
					calAndInputTableName="tbl_srpacqdata_hist";
				}else if(StringManagerUtils.stringToInteger(calculateType)==2){
					calAndInputTableName="tbl_pcpacqdata_hist";
				}
				
				deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
				if(deviceInfo!=null){
					displayInstanceCode=deviceInfo.getDisplayInstanceCode();
					alarmInstanceCode=deviceInfo.getAlarmInstanceCode();
					acqInstanceCode=deviceInfo.getInstanceCode();
				}
				
				alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
				acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(acqInstanceCode);
				displayInstanceOwnItem=MemoryDataManagerTask.getDisplayInstanceOwnItemByCode(displayInstanceCode);
				alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(alarmInstanceCode);
				
				if(displayInstanceOwnItem!=null){
					protocol=MemoryDataManagerTask.getProtocolByName(displayInstanceOwnItem.getProtocol());
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
				
				int commAlarmLevel=0,runAlarmLevel=0;
				if(alarmInstanceOwnItem!=null){
					for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
						if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
							commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
							runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}
					}
				}
				
				result_json.append("{\"Id\":"+deviceId+",");
				result_json.append("\"DeviceName\":\""+deviceObj[1]+"\",");
				result_json.append("\"DeviceType\":\""+deviceObj[13]+"\",");
				result_json.append("\"AcqTime\":\""+deviceObj[2]+"\",");
				result_json.append("\"CommStatus\":\""+commStatusName+"\",");
				result_json.append("\"CommTime\":\""+deviceObj[5]+"\",");
				result_json.append("\"CommTimeEfficiency\":\""+deviceObj[6]+"\",");
				result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(deviceObj[7])+"\",");
				
				result_json.append("\"RunStatus\":\""+runStatusName+"\",");
				result_json.append("\"RunTime\":\""+deviceObj[10]+"\",");
				result_json.append("\"RunTimeEfficiency\":\""+deviceObj[11]+"\",");
				result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(deviceObj[12])+"\",");
				
				result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+",");
				
				
				
				if(displayInstanceOwnItem!=null&&userInfo!=null&&protocol!=null){
					String displayItemSql="select t.unitid,t.id as itemid,t.itemname,t.itemcode,t.bitindex,"
							+ "decode(t.showlevel,null,9999,t.showlevel) as showlevel,"
							+ "decode(t.realtimeSort,null,9999,t.realtimeSort) as realtimeSort,"
							+ "decode(t.historySort,null,9999,t.historySort) as historySort,"
							+ "t.realtimecurveconf,t.historycurveconf,"
							+ "t.type "
							+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2 "
							+ " where t.unitid=t2.id and t2.id="+displayInstanceOwnItem.getUnitId()
							+ " and t.type<>2"
							+ " and t.historyData=1"
							+ " and decode(t.showlevel,null,9999,t.showlevel)>="+userInfo.getRoleShowLevel();
					displayItemSql+=" order by t.historySort,t.type,t.id";
					List<?> displayItemQueryList = this.findCallSql(displayItemSql);
					List<DisplayInstanceOwnItem.DisplayItem> displayItemList=new ArrayList<>();
					for(int j=0;j<displayItemQueryList.size();j++){
						Object[] displayItemObj=(Object[]) displayItemQueryList.get(j);
						DisplayInstanceOwnItem.DisplayItem displayItem=new DisplayInstanceOwnItem.DisplayItem();
						displayItem.setUnitId(StringManagerUtils.stringToInteger(displayItemObj[0]+""));
	    				displayItem.setItemId(StringManagerUtils.stringToInteger(displayItemObj[1]+""));
	    				displayItem.setItemName(displayItemObj[2]+"");
	    				displayItem.setItemCode(displayItemObj[3]+"");
	    				displayItem.setBitIndex(StringManagerUtils.stringToInteger(displayItemObj[4]+""));
	    				displayItem.setShowLevel(StringManagerUtils.stringToInteger(displayItemObj[5]+""));
	    				displayItem.setRealtimeSort(StringManagerUtils.stringToInteger(displayItemObj[6]+""));
	    				displayItem.setHistorySort(StringManagerUtils.stringToInteger(displayItemObj[7]+""));
	    				displayItem.setRealtimeCurveConf(displayItemObj[8]+"");
	    				displayItem.setHistoryCurveConf(displayItemObj[9]+"");
	    				displayItem.setType(StringManagerUtils.stringToInteger(displayItemObj[10]+""));
	    				displayItemList.add(displayItem);
					}
					
					
					//采集项
					for(int j=0;j<protocol.getItems().size();j++){
						if((!"w".equalsIgnoreCase(protocol.getItems().get(j).getRWType())) 
								&& (StringManagerUtils.existDisplayItem(displayInstanceOwnItem.getItemList(), protocol.getItems().get(j).getTitle(), false))){
							for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
								if(displayInstanceOwnItem.getItemList().get(k).getType()==0 
										&& displayInstanceOwnItem.getItemList().get(k).getHistoryData()==1
										&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
										&& protocol.getItems().get(j).getTitle().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemName())
										){
									protocolItems.add(protocol.getItems().get(j));
									break;
								}
							}
						}
					}
					
					//计算项
					if(calItemList!=null){
						for(CalItem calItem:calItemList){
							if(StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), calItem.getCode(), false,0,2)){
								for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
									if(displayInstanceOwnItem.getItemList().get(k).getType()==1
											&& displayInstanceOwnItem.getItemList().get(k).getHistoryData()==1
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
							if(acqItem.getDailyTotalCalculate()==1 && StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), (acqItem.getItemCode()+"_total").toUpperCase(), false,0,2)){
								for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
									if(displayInstanceOwnItem.getItemList().get(k).getType()==1
											&& displayInstanceOwnItem.getItemList().get(k).getHistoryData()==1
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
							if(StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), calItem.getCode(), false,0,2)){
								for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
									if(displayInstanceOwnItem.getItemList().get(k).getType()==3
											&& displayInstanceOwnItem.getItemList().get(k).getHistoryData()==1
											&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
											&& calItem.getCode().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode())){
										displayInputItemList.add(calItem);
										break;
									}
								}
								
							}
						}
					}
					
					
					for(int k=0;k<displayItemList.size();k++){
						if(displayItemList.get(k).getType()!=2 && displayItemList.get(k).getShowLevel()>=userInfo.getRoleShowLevel()){
							String header=displayItemList.get(k).getItemName();
							String dataIndex=displayItemList.get(k).getItemCode();
							String unit="";
							
							if(displayItemList.get(k).getType()==0){
								ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, header);
								if(item!=null){
									unit=item.getUnit();
								}
							}else if(displayItemList.get(k).getType()==1){
								if(dailyTotalCalItemMap.containsKey(dataIndex.toUpperCase())){
									DisplayInstanceOwnItem.DisplayItem displayItem=dailyTotalCalItemMap.get(dataIndex.toUpperCase()) ;
									if(displayItem!=null){
										ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, displayItem.getItemSourceName());
										if(item!=null){
											unit=item.getUnit();
										}
									}
								}else{
									CalItem calItem=MemoryDataManagerTask.getSingleCalItemByCode(dataIndex, calItemList);
									if(calItem!=null){
										header=calItem.getName();
										unit=calItem.getUnit();
									}
								}
								
								
							}else if(displayItemList.get(k).getType()==3){
								CalItem calItem=MemoryDataManagerTask.getSingleCalItemByCode(dataIndex, inputItemList);
								if(calItem!=null){
									header=calItem.getName();
									unit=calItem.getUnit();
								}
							}
							
							for(ModbusProtocolConfig.Items item:protocol.getItems()){
								if(item.getResolutionMode()==0 
										&& displayItemList.get(k).getItemName().equalsIgnoreCase(item.getTitle())
										&& item.getMeaning()!=null
										&& item.getMeaning().size()>0
										){//开关量处理
									for(ModbusProtocolConfig.ItemsMeaning itemsMeaning: item.getMeaning()){
										if(displayItemList.get(k).getBitIndex()==itemsMeaning.getValue()){
											header=itemsMeaning.getMeaning();
											dataIndex+="_"+itemsMeaning.getValue();
											break;
										}
									}
									break;
								}
							}
							
							if(StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))){
								header+="("+unit+")";
							}
							
							displayItemList.get(k).setItemName(header);
						}
					}
				}
				
				
				sql="select t2.id,t.devicename,"//0~1
						+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"//2
						+ "t2.acqdata";
				
				if(StringManagerUtils.stringToInteger(calculateType)>0){
					for(CalItem calItem:displayCalItemList){
						String column=calItem.getCode();
						if("resultName".equalsIgnoreCase(column)){
							column="resultCode";
						}
						sql+=",t3."+column;
					}
					if(displayInputItemList.size()>0){
						sql+=",t3.productiondata";
					}
				}else{
					for(int j=0;j<displayCalItemList.size();i++){
						String column=displayCalItemList.get(i).getCode();
						sql+=",t2."+column;
					}
				}
				
				sql+= " from "+deviceTableName+" t "
						+ " left outer join "+historyTableName+" t2 on t2.deviceid=t.id";
				if(StringManagerUtils.isNotNull(calAndInputTableName)&&(displayCalItemList.size()>0 || displayInputItemList.size()>0)){
					sql+=" left outer join "+calAndInputTableName+" t3 on t3.deviceid=t.id and t3.acqtime=t2.acqtime";
				}
				
				sql+= " where t.id="+deviceId+"";
				if(limit<=0){
					sql+=" and t2.acqTime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss') ";
				}
				sql+= " order by t2.acqtime desc";
				
				if(limit>0){
					sql="select v.* from ("+sql+") v where rownum <="+limit;
				}
				
				
				List<?> realtimeDataLlist = this.findCallSql(sql);
				
				String dailyTotalDatasql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
						+ "t.itemcolumn,t.itemname,"
						+ "t.totalvalue,t.todayvalue "
						+ " from TBL_DAILYTOTALCALCULATE_HIST t,"+historyTableName+" t2 "
						+ " where t.deviceId=t2.deviceId and t.acqTime=t2.acqTime"
						+ " and t.deviceId="+deviceId;
				if(limit<=0){
					dailyTotalDatasql+=" and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss') ";
				}	
				dailyTotalDatasql+= " order by t.acqTime desc";
				if(limit>0){
					dailyTotalDatasql="select v.* from ("+dailyTotalDatasql+") v where rownum <="+limit;
				}
				
				List<?> dailyTotalDatasList = this.findCallSql(dailyTotalDatasql);
				
				for(int j=0;j<realtimeDataLlist.size();j++){
					List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
					
					Object[] realtimeDataObj=(Object[]) realtimeDataLlist.get(j);
					String acqData=StringManagerUtils.CLOBObjectToString(realtimeDataObj[3]);
					String acqTime=realtimeDataObj[2]+"";
					
					if(displayInputItemList.size()>0){
						String productionData=(realtimeDataObj[realtimeDataObj.length-1]+"").replaceAll("null", "");
						if(StringManagerUtils.stringToInteger(calculateType)==1){
							type = new TypeToken<SRPCalculateRequestData>() {}.getType();
							SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
							
							for(CalItem calItem:displayInputItemList){
								String itemName=calItem.getName();
								String value="";
								String unit=calItem.getUnit();
								String column=calItem.getCode();
								int alarmLevel=0;
								
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
											itemName=languageResourceMap.get("reservoirDepth_cbm");
										}
									}else if("ReservoirTemperature".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
										value=srpProductionData.getReservoir().getTemperature()+"";
										if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
											itemName=languageResourceMap.get("reservoirTemperature_cbm");
										}
									}else if("TubingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
										value=srpProductionData.getProduction().getTubingPressure()+"";
										if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
											itemName=languageResourceMap.get("tubingPressure_cbm");
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
								
								itemName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(itemName+"("+unit+")"):itemName;
								inputDataBuf.append("{\"ItemName\":\""+itemName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
							}
						}else if(StringManagerUtils.stringToInteger(calculateType)==2){
							type = new TypeToken<PCPCalculateRequestData>() {}.getType();
							PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
							for(CalItem calItem:displayInputItemList){
								String itemName=calItem.getName();
								String value="";
								String unit=calItem.getUnit();
								String column=calItem.getCode();
								int alarmLevel=0;
								
								
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
											itemName=languageResourceMap.get("reservoirDepth_cbm");
										}
									}else if("ReservoirTemperature".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
										value=pcpProductionData.getReservoir().getTemperature()+"";
										if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
											itemName=languageResourceMap.get("reservoirTemperature_cbm");
										}
									}else if("TubingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
										value=pcpProductionData.getProduction().getTubingPressure()+"";
										if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
											itemName=languageResourceMap.get("tubingPressure_cbm");
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
								itemName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(itemName+"("+unit+")"):itemName;
								inputDataBuf.append("{\"ItemName\":\""+itemName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
							}
						}
					}
					
					for(CalItem calItem:displayCalItemList){
						int index=j+4;
						if(index<realtimeDataObj.length-1){
							String itemName=calItem.getName();
							String value=realtimeDataObj[index]+"";
							if(realtimeDataObj[index] instanceof CLOB || realtimeDataObj[index] instanceof Clob){
								value=StringManagerUtils.CLOBObjectToString(realtimeDataObj[index]);
							}
							String column=calItem.getCode();
							String unit=calItem.getUnit();
							int alarmLevel=0;
							for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
								if(column.equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
									//如果是工况
									if("resultCode".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())||"resultName".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
										WorkType workType=MemoryDataManagerTask.getWorkTypeByCode(value,language);
										if(workType!=null){
											value=workType.getResultName();
											for(AlarmInstanceOwnItem.AlarmItem alarmItem:alarmInstanceOwnItem.getItemList()){
												if(alarmItem.getAlarmLevel()>0 && alarmItem.getType()==4 && alarmItem.getItemCode().equalsIgnoreCase(workType.getResultCode()+"")){
													alarmLevel=alarmItem.getAlarmLevel();
												}
											}
											
										}
									}
									break;
								}
							}
							
							itemName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(itemName+"("+unit+")"):itemName;
							calDataBuf.append("{\"ItemName\":\""+itemName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
						}
					}
					
					if(protocolItems.size()>0){
						type = new TypeToken<List<KeyValue>>() {}.getType();
						List<KeyValue> acqDataList=gson.fromJson(acqData, type);
						if(acqDataList!=null){
							for(KeyValue keyValue:acqDataList){
								for(ModbusProtocolConfig.Items item: protocolItems){
									String column=loadProtocolMappingColumnByTitleMap.get(item.getTitle()).getMappingColumn();
									if(StringManagerUtils.isNotNull(column) && column.equalsIgnoreCase(keyValue.getKey())){
										String columnName=item.getTitle();
										String rawColumnName=columnName;
										String value=keyValue.getValue();
										String rawValue=value;
										String addr=item.getAddr()+"";
										String columnDataType=item.getIFDataType();
										String resolutionMode=item.getResolutionMode()+"";
										String bitIndex="";
										String unit=item.getUnit();
										int sort=9999;
										int alarmLevel=0;
										if("int".equalsIgnoreCase(item.getIFDataType()) || "uint".equalsIgnoreCase(item.getIFDataType()) || item.getIFDataType().contains("int")
												||"float32".equalsIgnoreCase(item.getIFDataType())
												||"float64".equalsIgnoreCase(item.getIFDataType())){
											if(value.toUpperCase().contains("E")){
							                 	value=StringManagerUtils.scientificNotationToNormal(value);
							                 }
										}
										if(item.getResolutionMode()==1||item.getResolutionMode()==2){//如果是枚举量
											for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
												if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column) && displayInstanceOwnItem.getItemList().get(l).getType()!=2){
													sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
													break;
												}
											}
											
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
											
											columnName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(columnName+"("+unit+")"):columnName;
											acqDataBuf.append("{\"ItemName\":\""+columnName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
											
										}else if(item.getResolutionMode()==0){//如果是开关量
											boolean isMatch=false;
											if(item.getMeaning()!=null&&item.getMeaning().size()>0){
												String[] valueArr=value.split(",");
												for(int l=0;l<item.getMeaning().size();l++){
													isMatch=false;
													columnName=item.getMeaning().get(l).getMeaning();
													sort=9999;
													
													for(int n=0;n<displayInstanceOwnItem.getItemList().size();n++){
														if(displayInstanceOwnItem.getItemList().get(n).getItemCode().equalsIgnoreCase(column) 
																&&displayInstanceOwnItem.getItemList().get(n).getBitIndex()==item.getMeaning().get(l).getValue()
																){
															sort=displayInstanceOwnItem.getItemList().get(n).getRealtimeSort();
															isMatch=true;
															break;
														}
													}
													if(!isMatch){
														continue;
													}
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
																columnName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(columnName+"("+unit+")"):columnName;
																acqDataBuf.append("{\"ItemName\":\""+columnName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
																match=true;
																break;
															}
														}
														if(!match){
															value="";
															rawValue="";
															bitIndex=item.getMeaning().get(l).getValue()+"";
															columnName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(columnName+"("+unit+")"):columnName;
															acqDataBuf.append("{\"ItemName\":\""+columnName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
														}
													}else{
														for(int m=0;m<displayInstanceOwnItem.getItemList().size();m++){
															if(displayInstanceOwnItem.getItemList().get(m).getItemCode().equalsIgnoreCase(column) 
																	&& displayInstanceOwnItem.getItemList().get(m).getBitIndex()==item.getMeaning().get(l).getValue() ){
																sort=displayInstanceOwnItem.getItemList().get(m).getRealtimeSort();
																break;
															}
														}
														value="";
														rawValue="";
														bitIndex=item.getMeaning().get(l).getValue()+"";
														columnName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(columnName+"("+unit+")"):columnName;
														acqDataBuf.append("{\"ItemName\":\""+columnName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
													}
												}
											}else{
												for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
													if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column)){
														sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
														break;
													}
												}
												columnName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(columnName+"("+unit+")"):columnName;
												acqDataBuf.append("{\"ItemName\":\""+columnName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
											}
										}else{//如果是数据量
											for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
												if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column) && displayInstanceOwnItem.getItemList().get(l).getType()!=2){
													sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
													break;
												}
											}
											
											columnName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(columnName+"("+unit+")"):columnName;
											acqDataBuf.append("{\"ItemName\":\""+columnName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
										} 
										break;
									}
								}
							}
						}
						
					}
					
					//获取日汇总计算数据
					if(dailyTotalCalItemMap.size()>0){
						for(int k=0;k<dailyTotalDatasList.size();k++){
							Object[] dailyTotalDataObj=(Object[]) dailyTotalDatasList.get(k);
							if(dailyTotalCalItemMap.containsKey( (dailyTotalDataObj[1]+"").toUpperCase() )  && acqTime.equalsIgnoreCase(dailyTotalDataObj[0]+"")  ){
								DisplayInstanceOwnItem.DisplayItem displayItem=dailyTotalCalItemMap.get( (dailyTotalDataObj[1]+"").toUpperCase() );
								if(displayItem!=null){
									String itemName=dailyTotalDataObj[2]+"";
									String value=dailyTotalDataObj[4]+"";
									String unit="";
									String column=dailyTotalDataObj[1]+"";
									int alarmLevel=0;
									ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, displayItem.getItemSourceName());
									if(item!=null){
										unit=item.getUnit();
									}
									itemName=StringManagerUtils.isNotNull(unit.replaceAll(" ", ""))?(itemName+"("+unit+")"):itemName;
									calDataBuf.append("{\"ItemName\":\""+itemName+"\",\"ItemValue\":\""+value+"\",\"ItemCode\":\""+column+"\",\"AlarmLevel\":"+alarmLevel+"},");
								}
							}
						}
					}
				}
				
				if(acqDataBuf.toString().endsWith(",")){
					acqDataBuf.deleteCharAt(acqDataBuf.length() - 1);
				}
				
				if(calDataBuf.toString().endsWith(",")){
					calDataBuf.deleteCharAt(calDataBuf.length() - 1);
				}
				
				if(inputDataBuf.toString().endsWith(",")){
					inputDataBuf.deleteCharAt(inputDataBuf.length() - 1);
				}
				
				
				acqDataBuf.append("]");
				calDataBuf.append("]");
				inputDataBuf.append("]");
				
				result_json.append("\"AcquisitionDataList\":"+acqDataBuf.toString()+",");
				result_json.append("\"CalculateDataList\":"+calDataBuf.toString()+",");
				result_json.append("\"InputDataList\":"+inputDataBuf.toString()+"},");
			}
			
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getLastestFESDiagramData(String data) throws SQLException, IOException {
		StringBuffer result_json = new StringBuffer();
		String user="";
		String password="";
		String deviceName="";
		if(StringManagerUtils.isNotNull(data)){
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
				
				try{
					deviceName=jsonObject.getString("DeviceName");
				}catch(Exception e){
					e.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",");
		result_json.append("\"DataList\":[");
		if(userCheckSign==1){
			User userInfo=this.userManagerService.getUser(user, password);
			String language=userInfo!=null?userInfo.getLanguageName():"";
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			String sql="";
			String hisTableName="tbl_srpacqdata_latest";
			String deviceTableName="tbl_device";
			
			sql="select t.id, t2.deviceName, to_char(t.fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime, " //0~2
					+ " t.upperLoadline, t.lowerloadline, t.fmax, t.fmin, t.stroke, t.SPM, "                    //3~8
					+ " t.fullnesscoefficient,t.noliquidfullnesscoefficient,"                                   //9~10
					+ " t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,"    //11~13
					+ " t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,"                //14~16
					+ " t.theoreticalproduction,"//17
					+ " t.pumpeff,"//18
					+ " t.resultcode,t.submergence,"//19~20
					+ " t.downstrokeimax,t.upstrokeimax,t.idegreebalance,"//21~23
					+ " t.downstrokewattmax,t.upstrokewattmax,t.wattdegreebalance,t.deltaradius,"//24~27
					+ " t.welldownsystemefficiency,t.surfacesystemefficiency,t.systemefficiency,t.energyper100mlift,"//28~31
					+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve"//32~35
					+ " from "+hisTableName+" t, "+deviceTableName+" t2"
					+ " where t.deviceId=t2.id "
					+ " and t2.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  "
					+ " and t2.deviceName='" + deviceName + "' ";
			List<?> list=this.findCallSql(sql);
			
			
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				CLOB realClob=null;
				SerializableClobProxy   proxy=null;
				String sStr="";
		        String fStr="";
		        String wattStr="";
		        String aStr="";
		        String pointCount="";
		        
		        
		        
		        if(obj[32]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[32]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					sStr=StringManagerUtils.CLOBtoString(realClob);
					if(StringManagerUtils.isNotNull(sStr)){
						pointCount=sStr.split(",").length+"";
					}
				}
		        if(obj[33]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[33]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					fStr=StringManagerUtils.CLOBtoString(realClob);
				}
		        if(obj[34]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[34]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					wattStr=StringManagerUtils.CLOBtoString(realClob);
				}
		        if(obj[35]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[35]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					aStr=StringManagerUtils.CLOBtoString(realClob);
				}
		        
				result_json.append("{ \"Id\":\"" + obj[0] + "\",");
				result_json.append("\"DeviceName\":\"" + obj[1] + "\",");
				result_json.append("\"AcqTime\":\"" + obj[2] + "\",");
				result_json.append("\"UpperLoadLine\":\"" + obj[3] + "\",");
				result_json.append("\"LowerLoadLine\":\"" + obj[4] + "\",");
				result_json.append("\"Fmax\":\""+obj[5]+"\",");
				result_json.append("\"Fmin\":\""+obj[6]+"\",");
				result_json.append("\"Stroke\":\""+obj[7]+"\",");
				result_json.append("\"SPM\":\""+obj[8]+"\",");
				
				result_json.append("\"FullnessCoefficient\":\""+obj[9]+"\",");
				result_json.append("\"NoLiquidFullnessCoefficient\":\""+obj[10]+"\",");
				
				result_json.append("\"LiquidVolumetricProduction\":\""+obj[11]+"\",");
				result_json.append("\"OilVolumetricProduction\":\""+obj[12]+"\",");
				result_json.append("\"WaterVolumetricProduction\":\""+obj[13]+"\",");
				result_json.append("\"LiquidWeightProduction\":\""+obj[14]+"\",");
				result_json.append("\"OilWeightProduction\":\""+obj[15]+"\",");
				result_json.append("\"WaterWeightProduction\":\""+obj[16]+"\",");
				
				result_json.append("\"TheoreticalProduction\":\""+obj[17]+"\",");
				result_json.append("\"PumpEff\":\""+obj[18]+"\",");
				
				
				String resultCode=obj[19]+"";
				WorkType workType=MemoryDataManagerTask.getWorkTypeByCode(resultCode,language);
				result_json.append("\"ResultCode\":\""+resultCode+"\",");
				result_json.append("\"ResultName\":\""+(workType!=null?workType.getResultName():"")+"\",");
				result_json.append("\"OptimizationSuggestion\":\""+(workType!=null?workType.getOptimizationSuggestion():"")+"\",");
				
				result_json.append("\"Submergence\":\""+obj[20]+"\",");
				
				result_json.append("\"DownStrokeIMax\":\""+obj[21]+"\",");
				result_json.append("\"UpStrokeIMax\":\""+obj[22]+"\",");
				result_json.append("\"IDegreeBalance\":\""+obj[23]+"\",");
				result_json.append("\"DownStrokeWattMax\":\""+obj[24]+"\",");
				result_json.append("\"UpStrokeWattMax\":\""+obj[25]+"\",");     
				result_json.append("\"WattDegreeBalance\":\""+obj[26]+"\",");
				result_json.append("\"DeltaRadius\":\""+obj[27]+"\",");
				
				result_json.append("\"WellDownSystemEfficiency\":\""+obj[28]+"\",");
				result_json.append("\"SurfaceSystemEfficiency\":\""+obj[29]+"\",");
				result_json.append("\"SystemEfficiency\":\""+obj[30]+"\",");
				result_json.append("\"EnergyPer100mLift\":\""+obj[31]+"\",");
				result_json.append("\"PointCount\":\""+pointCount+"\","); 
				result_json.append("\"S\":["+sStr+"],"); 
				result_json.append("\"F\":["+fStr+"],"); 
				result_json.append("\"Watt\":["+wattStr+"],"); 
				result_json.append("\"A\":["+aStr+"]},");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getHistoryFESDiagramData(String data) throws SQLException, IOException {
		StringBuffer result_json = new StringBuffer();
		String user="";
		String password="";
		String deviceName="";
		String startDate=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String endDate=startDate;
		int limit=0;
		if(StringManagerUtils.isNotNull(data)){
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
				
				try{
					deviceName=jsonObject.getString("DeviceName");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					startDate=jsonObject.getString("StartDate");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					endDate=jsonObject.getString("EndDate");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					limit=jsonObject.getInt("Limit");
				}catch(Exception e){
					e.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",");
		result_json.append("\"DataList\":[");
		if(userCheckSign==1){
			User userInfo=this.userManagerService.getUser(user, password);
			String language=userInfo!=null?userInfo.getLanguageName():"";
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			String sql="";
			String hisTableName="tbl_srpacqdata_hist";
			String deviceTableName="tbl_device";
			
			sql="select t.id, t2.deviceName, to_char(t.fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime, " //0~2
					+ " t.upperLoadline, t.lowerloadline, t.fmax, t.fmin, t.stroke, t.SPM, "                    //3~8
					+ " t.fullnesscoefficient,t.noliquidfullnesscoefficient,"                                   //9~10
					+ " t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,"    //11~13
					+ " t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,"                //14~16
					+ " t.theoreticalproduction,"//17
					+ " t.pumpeff,"//18
					+ " t.resultcode,t.submergence,"//19~20
					+ " t.downstrokeimax,t.upstrokeimax,t.idegreebalance,"//21~23
					+ " t.downstrokewattmax,t.upstrokewattmax,t.wattdegreebalance,t.deltaradius,"//24~27
					+ " t.welldownsystemefficiency,t.surfacesystemefficiency,t.systemefficiency,t.energyper100mlift,"//28~31
					+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve"//32~35
					+ " from "+hisTableName+" t, "+deviceTableName+" t2"
					+ " where t.deviceId=t2.id "
					+ " and t2.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  ";
			if(limit<=0){
				sql+=" and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss')";
			}
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t2.deviceName='" + deviceName + "' ";
			} 
			sql+=" order by t.acqTime desc";
			
			if(limit>0){
				sql="select v.* from ("+sql+") v where rownum<="+limit;
			}
			
			List<?> list=this.findCallSql(sql);
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				CLOB realClob=null;
				SerializableClobProxy   proxy=null;
				String sStr="";
		        String fStr="";
		        String wattStr="";
		        String aStr="";
		        String pointCount="";
		        
		        if(obj[32]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[32]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					sStr=StringManagerUtils.CLOBtoString(realClob);
					if(StringManagerUtils.isNotNull(sStr)){
						pointCount=sStr.split(",").length+"";
					}
				}
		        if(obj[33]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[33]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					fStr=StringManagerUtils.CLOBtoString(realClob);
				}
		        if(obj[34]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[34]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					wattStr=StringManagerUtils.CLOBtoString(realClob);
				}
		        if(obj[35]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[35]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					aStr=StringManagerUtils.CLOBtoString(realClob);
				}
		        
				result_json.append("{ \"Id\":\"" + obj[0] + "\",");
				result_json.append("\"DeviceName\":\"" + obj[1] + "\",");
				result_json.append("\"AcqTime\":\"" + obj[2] + "\",");
				result_json.append("\"UpperLoadLine\":\"" + obj[3] + "\",");
				result_json.append("\"LowerLoadLine\":\"" + obj[4] + "\",");
				result_json.append("\"Fmax\":\""+obj[5]+"\",");
				result_json.append("\"Fmin\":\""+obj[6]+"\",");
				result_json.append("\"Stroke\":\""+obj[7]+"\",");
				result_json.append("\"SPM\":\""+obj[8]+"\",");
				
				result_json.append("\"FullnessCoefficient\":\""+obj[9]+"\",");
				result_json.append("\"NoLiquidFullnessCoefficient\":\""+obj[10]+"\",");
				
				result_json.append("\"LiquidVolumetricProduction\":\""+obj[11]+"\",");
				result_json.append("\"OilVolumetricProduction\":\""+obj[12]+"\",");
				result_json.append("\"WaterVolumetricProduction\":\""+obj[13]+"\",");
				result_json.append("\"LiquidWeightProduction\":\""+obj[14]+"\",");
				result_json.append("\"OilWeightProduction\":\""+obj[15]+"\",");
				result_json.append("\"WaterWeightProduction\":\""+obj[16]+"\",");
				
				result_json.append("\"TheoreticalProduction\":\""+obj[17]+"\",");
				result_json.append("\"PumpEff\":\""+obj[18]+"\",");
				
				
				String resultCode=obj[19]+"";
				WorkType workType=MemoryDataManagerTask.getWorkTypeByCode(resultCode,language);
				result_json.append("\"ResultCode\":\""+resultCode+"\",");
				result_json.append("\"ResultName\":\""+(workType!=null?workType.getResultName():"")+"\",");
				result_json.append("\"OptimizationSuggestion\":\""+(workType!=null?workType.getOptimizationSuggestion():"")+"\",");
				
				result_json.append("\"Submergence\":\""+obj[20]+"\",");
				
				result_json.append("\"DownStrokeIMax\":\""+obj[21]+"\",");
				result_json.append("\"UpStrokeIMax\":\""+obj[22]+"\",");
				result_json.append("\"IDegreeBalance\":\""+obj[23]+"\",");
				result_json.append("\"DownStrokeWattMax\":\""+obj[24]+"\",");
				result_json.append("\"UpStrokeWattMax\":\""+obj[25]+"\",");     
				result_json.append("\"WattDegreeBalance\":\""+obj[26]+"\",");
				result_json.append("\"DeltaRadius\":\""+obj[27]+"\",");
				
				result_json.append("\"WellDownSystemEfficiency\":\""+obj[28]+"\",");
				result_json.append("\"SurfaceSystemEfficiency\":\""+obj[29]+"\",");
				result_json.append("\"SystemEfficiency\":\""+obj[30]+"\",");
				result_json.append("\"EnergyPer100mLift\":\""+obj[31]+"\",");
				
				result_json.append("\"PointCount\":\""+pointCount+"\","); 
				result_json.append("\"S\":["+sStr+"],"); 
				result_json.append("\"F\":["+fStr+"],"); 
				result_json.append("\"Watt\":["+wattStr+"],"); 
				result_json.append("\"A\":["+aStr+"]},");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getHistoryCurveData(String data) throws SQLException, IOException {
		StringBuffer result_json = new StringBuffer();
		String user="";
		String password="";
		String deviceName="";
		String item="";
		String code="";
		String startDate=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String endDate=startDate;
		int limit=0;
		if(StringManagerUtils.isNotNull(data)){
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
				
				try{
					deviceName=jsonObject.getString("DeviceName");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					item=jsonObject.getString("ItemName");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					startDate=jsonObject.getString("StartDate");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					endDate=jsonObject.getString("EndDate");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					limit=jsonObject.getInt("Limit");
				}catch(Exception e){
					e.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",");
		result_json.append("\"DataList\":[");
		try{
			if(userCheckSign==1 && StringManagerUtils.isNotNull(deviceName) && StringManagerUtils.isNotNull(item)){
				User u=this.userManagerService.getUser(user, password);
				String language=u!=null?u.getLanguageName():"";
				Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
				
				Gson gson = new Gson();
				java.lang.reflect.Type type=null;
				
				
				AlarmShowStyle alarmShowStyle=null;
				AcqInstanceOwnItem acqInstanceOwnItem=null;
				DisplayInstanceOwnItem displayInstanceOwnItem=null;
				AlarmInstanceOwnItem alarmInstanceOwnItem=null;
				List<CalItem> calItemList=null;
				List<CalItem> inputItemList=null;
				UserInfo userInfo=MemoryDataManagerTask.getUserInfoByNo(u!=null?(u.getUserNo()+""):"");
				
				ModbusProtocolConfig.Protocol protocol=null;
				
				String acqInstanceCode="";
				String displayInstanceCode="";
				String alarmInstanceCode="";
				
				DeviceInfo deviceInfo=null;
				String deviceInfoKey="DeviceInfo";
				
				Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
				Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
				
				String realtimeTableName="tbl_acqdata_latest";
				String deviceTableName="VIW_DEVICE";
				
				String historyTableName="tbl_acqdata_hist";
				
				
				String sql="select t.id,t.devicename,"//0~1
						+ "t.calculateType";//2
				sql+= " from "+deviceTableName+" t "
						+ " left outer join "+realtimeTableName+" t2 on t2.deviceId=t.id"
						+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  ";
				
				if(StringManagerUtils.isNotNull(deviceName)){
					sql+= " and t.devicename='"+deviceName+"'";
				}
				sql+=" order by t.id";
				
				List<?> list = this.findCallSql(sql);
				if(list.size()>0){
					Object[] deviceObj=(Object[]) list.get(0);
					
					List<ModbusProtocolConfig.Items> protocolItems=new ArrayList<ModbusProtocolConfig.Items>();
					List<CalItem> displayCalItemList=new ArrayList<CalItem>();
					List<CalItem> displayInputItemList=new ArrayList<CalItem>();
					Map<String,DisplayInstanceOwnItem.DisplayItem> dailyTotalCalItemMap=new LinkedHashMap<>();
					
					
					
					String deviceId=deviceObj[0]+"";
					String calculateType=deviceObj[2]+"";
					
					if(StringManagerUtils.stringToInteger(calculateType)==1){
						historyTableName="tbl_srpacqdata_hist";
					}else if(StringManagerUtils.stringToInteger(calculateType)==2){
						historyTableName="tbl_pcpacqdata_hist";
					}
					
					deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
					if(deviceInfo!=null){
						displayInstanceCode=deviceInfo.getDisplayInstanceCode();
						alarmInstanceCode=deviceInfo.getAlarmInstanceCode();
						acqInstanceCode=deviceInfo.getInstanceCode();
					}
					
					alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
					acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(acqInstanceCode);
					displayInstanceOwnItem=MemoryDataManagerTask.getDisplayInstanceOwnItemByCode(displayInstanceCode);
					alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(alarmInstanceCode);
					
					if(displayInstanceOwnItem!=null){
						protocol=MemoryDataManagerTask.getProtocolByName(displayInstanceOwnItem.getProtocol());
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
					
					int itemType=0;
					CalItem c=MemoryDataManagerTask.getSingleCalItem(item, calItemList);
					if(c!=null){
						itemType=1;
						code=c.getCode();
					}else{
						c=MemoryDataManagerTask.getSingleCalItem(item, inputItemList);
						if(c!=null){
							itemType=2;
							code=c.getCode();
						}
					}
					if(c==null){
						itemType=0;
						DataMapping dataMapping=loadProtocolMappingColumnByTitleMap.get(item);
						if(dataMapping!=null){
							code=dataMapping.getMappingColumn();
						}
						historyTableName="tbl_acqdata_hist";
					}
					if(StringManagerUtils.isNotNull(code)){
						List<DisplayInstanceOwnItem.DisplayItem> displayItemList=new ArrayList<>();
						if(displayInstanceOwnItem!=null && userInfo!=null && protocol!=null){
							String displayItemSql="select t.unitid,t.id as itemid,t.itemname,t.itemcode,t.bitindex,"
									+ "decode(t.showlevel,null,9999,t.showlevel) as showlevel,"
									+ "decode(t.realtimeSort,null,9999,t.realtimeSort) as realtimeSort,"
									+ "decode(t.historySort,null,9999,t.historySort) as historySort,"
									+ "t.realtimecurveconf,t.historycurveconf,"
									+ "t.type "
									+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2 "
									+ " where t.unitid=t2.id and t2.id="+displayInstanceOwnItem.getUnitId()
									+ " and t.itemcode='"+code+"'"
									+ " and decode(t.showlevel,null,9999,t.showlevel)>="+userInfo.getRoleShowLevel();
							List<?> displayItemQueryList = this.findCallSql(displayItemSql);
							for(int i=0;i<displayItemQueryList.size();i++){
								Object[] displayItemObj=(Object[]) displayItemQueryList.get(i);
								DisplayInstanceOwnItem.DisplayItem displayItem=new DisplayInstanceOwnItem.DisplayItem();
								displayItem.setUnitId(StringManagerUtils.stringToInteger(displayItemObj[0]+""));
			    				displayItem.setItemId(StringManagerUtils.stringToInteger(displayItemObj[1]+""));
			    				displayItem.setItemName(displayItemObj[2]+"");
			    				displayItem.setItemCode(displayItemObj[3]+"");
			    				displayItem.setBitIndex(StringManagerUtils.stringToInteger(displayItemObj[4]+""));
			    				displayItem.setShowLevel(StringManagerUtils.stringToInteger(displayItemObj[5]+""));
			    				displayItem.setRealtimeSort(StringManagerUtils.stringToInteger(displayItemObj[6]+""));
			    				displayItem.setHistorySort(StringManagerUtils.stringToInteger(displayItemObj[7]+""));
			    				displayItem.setRealtimeCurveConf(displayItemObj[8]+"");
			    				displayItem.setHistoryCurveConf(displayItemObj[9]+"");
			    				displayItem.setType(StringManagerUtils.stringToInteger(displayItemObj[10]+""));
			    				displayItemList.add(displayItem);
							}
						}
						if(displayItemList.size()>0){
							if(itemType==0){
								sql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,t.acqdata from "+historyTableName+" t "
										+ " where t.deviceid= "+deviceId;
								if(limit<=0){
									sql+=" and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss') ";
								}
								sql+= " order by t.acqtime";
								
								if(limit>0){
									sql="select v.* from ("+sql+") v where rownum <="+limit;
								}
								List<?> curveDataList = this.findCallSql(sql);
								for(int i=0;i<curveDataList.size();i++){
									Object[] curveDataObj=(Object[]) curveDataList.get(i);
									String acqTime=curveDataObj[0]+"";
									String acqData=StringManagerUtils.CLOBObjectToString(curveDataObj[1]);
									String value="";
									type = new TypeToken<List<KeyValue>>() {}.getType();
									List<KeyValue> acqDataList=gson.fromJson(acqData, type);
									if(acqDataList!=null){
										for(KeyValue keyValue:acqDataList){
											if(code.equalsIgnoreCase(keyValue.getKey())){
												value=keyValue.getValue();
												break;
											}
										}
									}
									
									result_json.append("{\"AcqTime\":\""+acqTime+"\",\"Value\":\""+value+"\"},");
								}
							}else if(itemType==1){
								sql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,t."+code+" from "+historyTableName+" t "
										+ " where t.deviceid= "+deviceId;
								if(limit<=0){
									sql+=" and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss') ";
								}
								sql+= " order by t.acqtime";
								
								if(limit>0){
									sql="select v.* from ("+sql+") v where rownum <="+limit;
								}
								List<?> curveDataList = this.findCallSql(sql);
								for(int i=0;i<curveDataList.size();i++){
									Object[] curveDataObj=(Object[]) curveDataList.get(i);
									result_json.append("{\"AcqTime\":\""+curveDataObj[0]+"\",\"Value\":\""+curveDataObj[1]+"\"},");
								}
							}else if(itemType==2){
								sql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,t.productiondata from "+historyTableName+" t "
										+ " where t.deviceid= "+deviceId;
								if(limit<=0){
									sql+=" and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss') ";
								}
								sql+= " order by t.acqtime";
								
								if(limit>0){
									sql="select v.* from ("+sql+") v where rownum <="+limit;
								}
								List<?> curveDataList = this.findCallSql(sql);
								for(int i=0;i<curveDataList.size();i++){
									Object[] curveDataObj=(Object[]) curveDataList.get(i);
									String acqTime=curveDataObj[0]+"";
									String productionData=(curveDataObj[1]+"").replaceAll("null", "");
									String value="";
									if(StringManagerUtils.stringToInteger(calculateType)==1){
										type = new TypeToken<SRPCalculateRequestData>() {}.getType();
										SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
										if(srpProductionData!=null){
											if("CrudeOilDensity".equalsIgnoreCase(code) && srpProductionData.getFluidPVT()!=null ){
												value=srpProductionData.getFluidPVT().getCrudeOilDensity()+"";
											}else if("WaterDensity".equalsIgnoreCase(code) && srpProductionData.getFluidPVT()!=null ){
												value=srpProductionData.getFluidPVT().getWaterDensity()+"";
											}else if("NaturalGasRelativeDensity".equalsIgnoreCase(code) && srpProductionData.getFluidPVT()!=null ){
												value=srpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
											}else if("SaturationPressure".equalsIgnoreCase(code) && srpProductionData.getFluidPVT()!=null ){
												value=srpProductionData.getFluidPVT().getSaturationPressure()+"";
											}else if("ReservoirDepth".equalsIgnoreCase(code) && srpProductionData.getReservoir()!=null ){
												value=srpProductionData.getReservoir().getDepth()+"";
											}else if("ReservoirTemperature".equalsIgnoreCase(code) && srpProductionData.getReservoir()!=null ){
												value=srpProductionData.getReservoir().getTemperature()+"";
											}else if("TubingPressure".equalsIgnoreCase(code) && srpProductionData.getProduction()!=null ){
												value=srpProductionData.getProduction().getTubingPressure()+"";
											}else if("CasingPressure".equalsIgnoreCase(code) && srpProductionData.getProduction()!=null ){
												value=srpProductionData.getProduction().getCasingPressure()+"";
											}else if("WellHeadTemperature".equalsIgnoreCase(code) && srpProductionData.getProduction()!=null ){
												value=srpProductionData.getProduction().getWellHeadTemperature()+"";
											}else if("WaterCut".equalsIgnoreCase(code) && srpProductionData.getProduction()!=null ){
												value=srpProductionData.getProduction().getWaterCut()+"";
											}else if("ProductionGasOilRatio".equalsIgnoreCase(code) && srpProductionData.getProduction()!=null ){
												value=srpProductionData.getProduction().getProductionGasOilRatio()+"";
											}else if("ProducingfluidLevel".equalsIgnoreCase(code) && srpProductionData.getProduction()!=null ){
												value=srpProductionData.getProduction().getProducingfluidLevel()+"";
											}else if("PumpSettingDepth".equalsIgnoreCase(code) && srpProductionData.getProduction()!=null ){
												value=srpProductionData.getProduction().getPumpSettingDepth()+"";
											}else if("PumpBoreDiameter".equalsIgnoreCase(code) && srpProductionData.getPump()!=null ){
												value=srpProductionData.getPump().getPumpBoreDiameter()*1000+"";
											}else if("LevelCorrectValue".equalsIgnoreCase(code) && srpProductionData.getManualIntervention()!=null ){
												value=srpProductionData.getManualIntervention().getLevelCorrectValue()+"";
											}
										}
									}else if(StringManagerUtils.stringToInteger(calculateType)==2){
										type = new TypeToken<PCPCalculateRequestData>() {}.getType();
										PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
										if(pcpProductionData!=null){
											if("CrudeOilDensity".equalsIgnoreCase(code) && pcpProductionData.getFluidPVT()!=null ){
												value=pcpProductionData.getFluidPVT().getCrudeOilDensity()+"";
											}else if("WaterDensity".equalsIgnoreCase(code) && pcpProductionData.getFluidPVT()!=null ){
												value=pcpProductionData.getFluidPVT().getWaterDensity()+"";
											}else if("NaturalGasRelativeDensity".equalsIgnoreCase(code) && pcpProductionData.getFluidPVT()!=null ){
												value=pcpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
											}else if("SaturationPressure".equalsIgnoreCase(code) && pcpProductionData.getFluidPVT()!=null ){
												value=pcpProductionData.getFluidPVT().getSaturationPressure()+"";
											}else if("ReservoirDepth".equalsIgnoreCase(code) && pcpProductionData.getReservoir()!=null ){
												value=pcpProductionData.getReservoir().getDepth()+"";
											}else if("ReservoirTemperature".equalsIgnoreCase(code) && pcpProductionData.getReservoir()!=null ){
												value=pcpProductionData.getReservoir().getTemperature()+"";
											}else if("TubingPressure".equalsIgnoreCase(code) && pcpProductionData.getProduction()!=null ){
												value=pcpProductionData.getProduction().getTubingPressure()+"";
											}else if("CasingPressure".equalsIgnoreCase(code) && pcpProductionData.getProduction()!=null ){
												value=pcpProductionData.getProduction().getCasingPressure()+"";
											}else if("WellHeadTemperature".equalsIgnoreCase(code) && pcpProductionData.getProduction()!=null ){
												value=pcpProductionData.getProduction().getWellHeadTemperature()+"";
											}else if("WaterCut".equalsIgnoreCase(code) && pcpProductionData.getProduction()!=null ){
												value=pcpProductionData.getProduction().getWaterCut()+"";
											}else if("ProductionGasOilRatio".equalsIgnoreCase(code) && pcpProductionData.getProduction()!=null ){
												value=pcpProductionData.getProduction().getProductionGasOilRatio()+"";
											}else if("ProducingfluidLevel".equalsIgnoreCase(code) && pcpProductionData.getProduction()!=null ){
												value=pcpProductionData.getProduction().getProducingfluidLevel()+"";
											}else if("PumpSettingDepth".equalsIgnoreCase(code) && pcpProductionData.getProduction()!=null ){
												value=pcpProductionData.getProduction().getPumpSettingDepth()+"";
											}
										}
									}
									result_json.append("{\"AcqTime\":\""+acqTime+"\",\"Value\":\""+value+"\"},");
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getPumpunitRealtimeWellAnalysisData(String data) throws SQLException, IOException{
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String deviceName="";
		String acqTime="";
		String user="";
		String password="";
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		try{
			JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
			String json="";
			if(jsonObject!=null){
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
				
				try{
					deviceName=jsonObject.getString("DeviceName");
				}catch(Exception e){
					e.printStackTrace();
				}
				try{
					acqTime=jsonObject.getString("AcqTime");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",");
		
		if(userCheckSign==1){
			if(StringManagerUtils.isNotNull(deviceName) && StringManagerUtils.isNotNull(acqTime)){
				String prodCol=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,"
						+ " t.availablePlungerstrokeProd_W,t.pumpClearanceLeakProd_W,t.tvleakWeightProduction,t.svleakWeightProduction,t.gasInfluenceProd_W,";
				if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("stere")){
					prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,"
							+ " t.availablePlungerstrokeProd_V,t.pumpClearanceLeakProd_V,t.tvleakVolumetricProduction,t.svleakVolumetricProduction,t.gasInfluenceProd_V,";;
				}
				String hisTableName="tbl_srpacqdata_hist";
				String deviceTableName="tbl_device";
				String sql="select t3.resultName,t3.optimizationSuggestion,"//0~1
						+ " t.UpStrokeWattMax,t.DownStrokeWattMax,t.wattDegreeBalance,"//2~4
						+ " t.UpStrokeIMax,t.DownStrokeIMax,t.iDegreeBalance,"//5~7
						+ " t.deltaRadius*100,"//8
						+ prodCol//9~16
						+ " t.theoreticalProduction,"//17
						+ " t.fullnessCoefficient,t.plungerstroke,t.availableplungerstroke,"//18~20
						+ " t.levelDifferenceValue,t.calcProducingfluidLevel,"//21~22
						+ " t.submergence,"//23
						+ " t.stroke,t.spm,"//24~25
						+ " t.fmax,t.fmin,t.fmax-t.fmin as deltaF,"//26~28
						+ " t.upperloadline,t.lowerloadline,t.upperloadline-t.lowerloadline as deltaLoadLine,area,"//29~32
						+ " t.averageWatt,t.polishrodPower,t.waterPower,"//33~35
						+ " t.surfaceSystemEfficiency*100,t.welldownSystemEfficiency*100,t.systemEfficiency*100,t.energyPer100mLift,"//36~39
						+ " t.pumpEff1*100,t.pumpEff2*100,t.pumpEff3*100,t.pumpEff4*100,t.pumpEff*100,"//40~44
						+ " t.rodFlexLength,t.tubingFlexLength,t.inertiaLength,"//45~47
						+ " t.pumpintakep,t.pumpintaket,t.pumpintakegol,t.pumpintakevisl,t.pumpintakebo,"//48~52
						+ " t.pumpoutletp,t.pumpoutlett,t.pumpOutletGol,t.pumpoutletvisl,t.pumpoutletbo,"//53~57
						+ " t.productiondata"//58
						+ " from "+hisTableName+" t, "+deviceTableName+" t2,tbl_srp_worktype t3"
						+ " where t.deviceId=t2.id and t.resultcode=t3.resultcode"
						+ " and  t2.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  "
						+ " and  t2.deviceName='"+deviceName+"' and t.fesdiagramAcqTime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')"; 
				List<?> list = this.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					
					String waterCut="";
					String pumpBoreDiameter="";
					String pumpSettingDepth="";
					String producingFluidLevel="";
					String productionData=obj[obj.length-1].toString();
					type = new TypeToken<SRPCalculateRequestData>() {}.getType();
					SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
					if(srpProductionData!=null && srpProductionData.getProduction()!=null){
						if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("stere")){
							waterCut=srpProductionData.getProduction().getWaterCut()+"";
						}else{
							waterCut=srpProductionData.getProduction().getWeightWaterCut()+"";
						}
						pumpSettingDepth=srpProductionData.getProduction().getPumpSettingDepth()+"";
						producingFluidLevel=srpProductionData.getProduction().getProducingfluidLevel()+"";
					}
					if(srpProductionData!=null && srpProductionData.getPump()!=null){
						pumpBoreDiameter=srpProductionData.getPump().getPumpBoreDiameter()*1000+"";
					}
					
					result_json.append("\"ResultName\":\""+obj[0]+"\",");
					result_json.append("\"OptimizationSuggestion\":\""+obj[1]+"\",");
					
					result_json.append("\"UpStrokeWattMax\":\""+obj[2]+"\",");
					result_json.append("\"DownStrokeWattMax\":\""+obj[3]+"\",");
					result_json.append("\"WattDegreeBalance\":\""+obj[4]+"\",");
					
					result_json.append("\"UpStrokeIMax\":\""+obj[5]+"\",");
					result_json.append("\"DownStrokeIMax\":\""+obj[6]+"\",");
					result_json.append("\"IDegreeBalance\":\""+obj[7]+"\",");
					
					result_json.append("\"DeltaRadius\":\""+obj[8]+"\",");
					
					result_json.append("\"LiquidProduction\":\""+obj[9]+"\",");
					result_json.append("\"OilProduction\":\""+obj[10]+"\",");
					result_json.append("\"WaterProduction\":\""+obj[11]+"\",");
					result_json.append("\"waterCut\":\""+waterCut+"\",");
					
					result_json.append("\"AvailablePlungerstrokeProd\":\""+obj[12]+"\",");
					result_json.append("\"PumpClearanceLeakProd\":\""+obj[13]+"\",");
					result_json.append("\"TvleakProduction\":\""+obj[14]+"\",");
					result_json.append("\"SvleakProduction\":\""+obj[15]+"\",");
					result_json.append("\"GasInfluenceProd\":\""+obj[16]+"\",");
					
					result_json.append("\"TheoreticalProduction\":\""+obj[17]+"\",");
					result_json.append("\"FullnessCoefficient\":\""+obj[18]+"\",");
					result_json.append("\"PlungerStroke\":\""+obj[19]+"\",");
					result_json.append("\"AvailablePlungerStroke\":\""+obj[20]+"\",");
					
					result_json.append("\"PumpBoreDiameter\":\""+pumpBoreDiameter+"\",");
					result_json.append("\"PumpSettingDepth\":\""+pumpSettingDepth+"\",");
					result_json.append("\"ProducingFluidLevel\":\""+producingFluidLevel+"\",");
					result_json.append("\"LevelDifferenceValue\":\""+obj[21]+"\",");
					result_json.append("\"CalcProducingfluidLevel\":\""+obj[22]+"\",");
					result_json.append("\"Submergence\":\""+obj[23]+"\",");
					
					result_json.append("\"Stroke\":\""+obj[24]+"\",");
					result_json.append("\"SPM\":\""+obj[25]+"\",");
					result_json.append("\"Fmax\":\""+obj[26]+"\",");
					result_json.append("\"Fmin\":\""+obj[27]+"\",");
					result_json.append("\"DeltaF\":\""+obj[28]+"\",");
					
					
					result_json.append("\"UpperLoadLine\":\""+obj[29]+"\",");
					result_json.append("\"LowerLoadLine\":\""+obj[30]+"\",");
					result_json.append("\"DeltaLoadLine\":\""+obj[31]+"\",");
					result_json.append("\"Area\":\""+obj[32]+"\",");
					
					result_json.append("\"AverageWatt\":\""+obj[33]+"\",");
					result_json.append("\"PolishrodPower\":\""+obj[34]+"\",");
					result_json.append("\"WaterPower\":\""+obj[35]+"\",");
					result_json.append("\"SurfaceSystemEfficiency\":\""+obj[36]+"\",");
					result_json.append("\"WelldownSystemEfficiency\":\""+obj[37]+"\",");
					result_json.append("\"SystemEfficiency\":\""+obj[38]+"\",");
					result_json.append("\"EnergyPer100mLift\":\""+obj[39]+"\",");
					
					result_json.append("\"PumpEff1\":\""+obj[40]+"\",");
					result_json.append("\"PumpEff2\":\""+obj[41]+"\",");
					result_json.append("\"PumpEff3\":\""+obj[42]+"\",");
					result_json.append("\"PumpEff4\":\""+obj[43]+"\",");
					result_json.append("\"PumpEff\":\""+obj[44]+"\",");
					result_json.append("\"RodFlexLength\":\""+obj[45]+"\",");
					result_json.append("\"TubingFlexLength\":\""+obj[46]+"\",");
					result_json.append("\"InertiaLength\":\""+obj[47]+"\",");
					
					result_json.append("\"PumpIntakeP\":\""+obj[48]+"\",");
					result_json.append("\"PumpIntakeT\":\""+obj[49]+"\",");
					result_json.append("\"PumpIntakeGOL\":\""+obj[50]+"\",");
					result_json.append("\"PumpIntakeVisl\":\""+obj[51]+"\",");
					result_json.append("\"PumpIntakeBo\":\""+obj[52]+"\",");
					
					result_json.append("\"PumpOutletP\":\""+obj[53]+"\",");
					result_json.append("\"PumpOutletT\":\""+obj[54]+"\",");
					result_json.append("\"PumpOutletGOL\":\""+obj[55]+"\",");
					result_json.append("\"PumpOutletVisl\":\""+obj[56]+"\",");
					result_json.append("\"PumpOutletBo\":\""+obj[57]+"\"");
				}
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getOilWellTotalStatisticsData(String data){
		StringBuffer wells= new StringBuffer();
		String json="";
		int liftingType=1;
		int type=1;
		String user="";
		String password="";
		String date=StringManagerUtils.getCurrentTime();
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
			
			try{
				liftingType=jsonObject.getInt("LiftingType");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				type=jsonObject.getInt("StatType");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				date=jsonObject.getString("Date");
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				JSONArray jsonArray = jsonObject.getJSONArray("WellList");
				for(int i=0;jsonArray!=null&&i<jsonArray.size();i++){
					wells.append(""+jsonArray.getString(i)+",");
				}
				if(wells.toString().endsWith(",")){
					wells.deleteCharAt(wells.length() - 1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			if(liftingType==1 && type==1){
				json=this.getTotalFESDiagramResultStatData(user,password,wells.toString(),date);
			}else if(type==2){
				json=this.getTotalCommStatusStatData(user,password,wells.toString(),liftingType,date);
			}else if(type==3){
				json=this.getTotalRunStatusStatData(user,password,wells.toString(),liftingType,date);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return json;
	}
	
	public String getTotalFESDiagramResultStatData(String user,String password,String wells,String date) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",\"Date\":\""+date+"\",\"DataList\":[");
		if(userCheckSign==1){
			String [] wellList=null;
			if(StringManagerUtils.isNotNull(wells)){
				wellList=wells.split(",");
			}
			String sql="select decode(t2.resultcode,0,'无数据',null,'无数据',t3.resultname) as resultname,t2.resultcode,count(1) "
					+ " from tbl_device t "
					+ " left outer join tbl_srpdailycalculationdata t2 on t2.deviceId=t.id "
					+ " left outer join tbl_srp_worktype t3 on t2.resultcode=t3.resultcode "
					+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  "
					+ "and t2.caldate=to_date('"+date+"','yyyy-mm-dd') ";
			if(wellList!=null){
				sql+=" and t.deviceName in ( "+StringManagerUtils.joinStringArr2(wellList, ",")+" )";
			}
			sql+= "group by t3.resultname,t2.resultcode order by t2.resultcode";
			
			List<?> list = this.findCallSql(sql);
			
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				result_json.append("{\"Item\":\""+obj[0]+"\",");
				result_json.append("\"Count\":"+obj[2]+"},");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getTotalCommStatusStatData(String user,String password,String wells,int deviceType,String date) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int online=0,goOnline=0,offline=0;
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",\"Date\":\""+date+"\",\"DataList\":[");
		
		if(userCheckSign==1){
			UserInfo userInfo=MemoryDataManagerTask.getUserInfoByAccount(user);
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo!=null?userInfo.getLanguageName():"");
			String [] wellList=null;
			if(StringManagerUtils.isNotNull(wells)){
				wellList=wells.split(",");
			}
			String tableName="tbl_srpdailycalculationdata";
			String deviceTableName="tbl_device";
			if(deviceType!=1){
				tableName="tbl_pcpdailycalculationdata";
				deviceTableName="tbl_device";
			}
			
			String sql="select t2.commstatus,count(1) "
					+ " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceId=t.id"
					+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  "
					+ " and t2.caldate=to_date('"+date+"','yyyy-mm-dd') ";
			if(wellList!=null){
				sql+=" and t.deviceName in ( "+StringManagerUtils.joinStringArr2(wellList, ",")+" )";
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
			
			result_json.append("{\"Item\":'"+languageResourceMap.get("online")+"',");
			result_json.append("\"Count\":"+online+"},");
			
			result_json.append("{\"Item\":'"+languageResourceMap.get("goOnline")+"',");
			result_json.append("\"Count\":"+goOnline+"},");
			
			result_json.append("{\"Item\":'"+languageResourceMap.get("offline")+"',");
			result_json.append("\"Count\":"+offline+"}");
		}
		
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getTotalRunStatusStatData(String user,String password,String wells,int deviceType,String date) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int run=0,stop=0,noData=0,offline=0,goOnline=0;
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",\"Date\":\""+date+"\",\"DataList\":[");
		if(userCheckSign==1){
			UserInfo userInfo=MemoryDataManagerTask.getUserInfoByAccount(user);
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo!=null?userInfo.getLanguageName():"");
			String [] wellList=null;
			if(StringManagerUtils.isNotNull(wells)){
				wellList=wells.split(",");
			}
			String tableName="tbl_srpdailycalculationdata";
			String deviceTableName="tbl_device";
			if(deviceType!=1){
				tableName="tbl_pcpdailycalculationdata";
				deviceTableName="tbl_device";
			}
			
			String sql="select decode(t2.commstatus,0,-1,2,-2,decode(t2.runstatus,null,2,t2.runstatus)) as runstatus,count(1) "
					+ " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceId=t.id"
					+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  "
					+ " and t2.caldate=to_date('"+date+"','yyyy-mm-dd') ";
			if(wellList!=null){
				sql+=" and t.deviceName in ( "+StringManagerUtils.joinStringArr2(wellList, ",")+" )";
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
			result_json.append("{\"Item\":'"+languageResourceMap.get("run")+"',\"Count\":"+run+"},");
			result_json.append("{\"Item\":'"+languageResourceMap.get("stop")+"',\"Count\":"+stop+"},");
			result_json.append("{\"Item\":'"+languageResourceMap.get("emptyMsg")+"',\"Count\":"+noData+"},");
			result_json.append("{\"Item\":'"+languageResourceMap.get("goOnline")+"',\"Count\":"+goOnline+"},");
			result_json.append("{\"Item\":'"+languageResourceMap.get("offline")+"',\"Count\":"+offline+"}");
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getOilWellTotalWellListData(String data,Page pager)throws Exception {
		StringBuffer wells= new StringBuffer();
		String result = "";
		int liftingType=1;
		String date=StringManagerUtils.getCurrentTime();
		int statType=1;
		String statValue="";
		String user = "";
		String password = "";
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
			
			try{
				liftingType=jsonObject.getInt("LiftingType");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				date=jsonObject.getString("Date");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				statType=jsonObject.getInt("StatType");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				statValue=jsonObject.getString("StatValue");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				JSONArray jsonArray = jsonObject.getJSONArray("WellList");
				for(int i=0;jsonArray!=null&&i<jsonArray.size();i++){
					wells.append(""+jsonArray.getString(i)+",");
				}
				if(wells.toString().endsWith(",")){
					wells.deleteCharAt(wells.length() - 1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(liftingType==1){
			result=this.getDeviceTotalOverview(user,password,wells.toString(), statType, statValue, date);
		}else{
			result=this.getPCPDeviceTotalOverview(user,password,wells.toString(), statType, statValue, date);
		}
		return result;
	}
	
	public String getDeviceTotalOverview(String user,String password,String wells,int statType,String statValue,String date) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",");
		result_json.append("\"DataList\":[");
		if(userCheckSign==1){
			Jedis jedis=null;
			String [] wellList=null;
			if(StringManagerUtils.isNotNull(wells)){
				wellList=wells.split(",");
			}
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("DeviceInfo".getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				
				if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String tableName="tbl_srpdailycalculationdata";
			String deviceTableName="tbl_device";
			
			String sql="select t2.id,t.id as deviceId,t.deviceName,to_char(t2.caldate,'yyyy-mm-dd') as caldate,t2.ExtendedDays,"
					+ "decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据')) as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "t2.resultcode,decode(t2.resultcode,0,'无数据',null,'无数据',t3.resultName) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
					+ "t2.liquidWeightProduction,t2.oilWeightProduction,t2.waterWeightProduction,t2.weightWaterCut,"
					+ "t2.liquidVolumetricProduction,t2.oilVolumetricProduction,t2.waterVolumetricProduction,t2.volumeWaterCut,"
					+ "t2.fullnesscoefficient,t2.pumpsettingdepth,t2.producingfluidlevel,t2.submergence,"
					+ "t2.wattDegreeBalance,t2.iDegreeBalance,t2.deltaRadius*100,"
					+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
					+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
					+ "t2.systemEfficiency*100 as systemEfficiency,"
					+ "t2.energyper100mlift,t2.todayKWattH,"
					+ "t2.pumpEff*100 as pumpEff";
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceId=t.id"
					+ " left outer join tbl_srp_worktype t3 on t2.resultcode=t3.resultcode "
					+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  "
					+ " and t2.caldate= to_date('"+date+"','yyyy-mm-dd') ";
			if(wellList!=null){
				sql+=" and t.deviceName in ( "+StringManagerUtils.joinStringArr2(wellList, ",")+" )";
			}
			if(statType==1 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.resultcode,0,'无数据',null,'无数据',t3.resultName)='"+statValue+"'";
			}else if(statType==2 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+statValue+"'";
			}else if(statType==3 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据'))='"+statValue+"'";
			}
			sql+=" order by t.sortnum,t.deviceName";
			
			List<?> list = this.findCallSql(sql);
			
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[1]+"";
				String commStatusName=obj[5]+"";
				String runStatusName=obj[9]+"";
				String resultcode=obj[13]+"";
				
				
				SRPDeviceInfo srpDeviceInfo=null;
				if(jedis!=null&&jedis.hexists("DeviceInfo".getBytes(), deviceId.getBytes())){
					srpDeviceInfo=(SRPDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("DeviceInfo".getBytes(), deviceId.getBytes()));
				}
				
				AlarmInstanceOwnItem alarmInstanceOwnItem=null;
				if(jedis!=null&&srpDeviceInfo!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), srpDeviceInfo.getAlarmInstanceCode().getBytes())){
					alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), srpDeviceInfo.getAlarmInstanceCode().getBytes()));
				}
				
				int commAlarmLevel=0,resultAlarmLevel=0,runAlarmLevel=0;
				if(alarmInstanceOwnItem!=null){
					for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
						if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
							commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
							runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==4 && alarmInstanceOwnItem.getItemList().get(j).getItemCode().equalsIgnoreCase(resultcode)){
							resultAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}
					}
				}
				
				result_json.append("{\"Id\":"+obj[0]+",");
				result_json.append("\"deviceName\":\""+obj[2]+"\",");
				result_json.append("\"Caldate\":\""+obj[3]+"\",");
				result_json.append("\"ExtendedDays\":\""+obj[4]+"\",");
				
				result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
				result_json.append("\"CommTime\":\""+obj[6]+"\",");
				result_json.append("\"CommTimeEfficiency\":\""+obj[7]+"\",");
				result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
				
				result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
				result_json.append("\"RunTime\":\""+obj[10]+"\",");
				result_json.append("\"RunTimeEfficiency\":\""+obj[11]+"\",");
				result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
				result_json.append("\"ResultName\":\""+obj[14]+"\",");
				result_json.append("\"OptimizationSuggestion\":\""+obj[15]+"\",");
				
				result_json.append("\"LiquidWeightProduction\":\""+obj[16]+"\",");
				result_json.append("\"OilWeightProduction\":\""+obj[17]+"\",");
				result_json.append("\"WaterWeightProduction\":\""+obj[18]+"\",");
				result_json.append("\"WeightWaterCut\":\""+obj[19]+"\",");
				
				result_json.append("\"LiquidVolumetricProduction\":\""+obj[20]+"\",");
				result_json.append("\"OilVolumetricProduction\":\""+obj[21]+"\",");
				result_json.append("\"WaterVolumetricProduction\":\""+obj[22]+"\",");
				result_json.append("\"VolumeWaterCut\":\""+obj[23]+"\",");
				
				result_json.append("\"FullnessCoefficient\":\""+obj[24]+"\",");
				result_json.append("\"PumpSettingDepth\":\""+obj[25]+"\",");
				result_json.append("\"ProducingFluidlevel\":\""+obj[26]+"\",");
				result_json.append("\"Submergence\":\""+obj[27]+"\",");
				
				result_json.append("\"IDegreeBalance\":\""+obj[28]+"\",");
				result_json.append("\"WattDegreeBalance\":\""+obj[29]+"\",");
				result_json.append("\"Deltaradius\":\""+obj[30]+"\",");
				
				result_json.append("\"SurfaceSystemEfficiency\":\""+obj[31]+"\",");
				result_json.append("\"WelldownSystemEfficiency\":\""+obj[32]+"\",");
				result_json.append("\"SystemEfficiency\":\""+obj[33]+"\",");
				result_json.append("\"Energyper100mlift\":\""+obj[34]+"\",");
				result_json.append("\"TodayKWattH\":\""+obj[35]+"\",");
				result_json.append("\"PumpEff\":\""+obj[36]+"\",");

				result_json.append("\"ResultAlarmLevel\":"+resultAlarmLevel+",");
				result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
			}
			if(jedis!=null){
				jedis.close();
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getPCPDeviceTotalOverview(String user,String password,String wells,int statType,String statValue,String date) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",");
		result_json.append("\"DataList\":[");
		if(userCheckSign==1){
			Jedis jedis=null;
			String [] wellList=null;
			if(StringManagerUtils.isNotNull(wells)){
				wellList=wells.split(",");
			}
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("PCPDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				
				if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String tableName="tbl_pcpdailycalculationdata";
			String deviceTableName="tbl_device";
			
			String sql="select t2.id,t.id as deviceId,t.deviceName,to_char(caldate,'yyyy-mm-dd') as caldate,extendedDays,"
					+ "decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据')) as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,weightWaterCut,"
					+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,volumeWaterCut,"
					+ "t2.pumpsettingdepth,t2.producingfluidlevel,t2.submergence,"
					+ "t2.systemEfficiency*100 as systemEfficiency,"
					+ "t2.todayKWattH,"
					+ "t2.pumpEff*100 as pumpEff";
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceId=t.id"
					+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  "
					+ " and t2.caldate= to_date('"+date+"','yyyy-mm-dd') ";
			if(wellList!=null){
				sql+=" and t.deviceName in ( "+StringManagerUtils.joinStringArr2(wellList, ",")+" )";
			}
			if(statType==2 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+statValue+"'";
			}else if(statType==3 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据'))='"+statValue+"'";
			}
			sql+=" order by t.sortnum,t.deviceName";
			List<?> list = this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[1]+"";
				String commStatusName=obj[5]+"";
				String runStatusName=obj[9]+"";
				
				PCPDeviceInfo pcpDeviceInfo=null;
				if(jedis!=null&&jedis.hexists("PCPDeviceInfo".getBytes(), deviceId.getBytes())){
					pcpDeviceInfo=(PCPDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("PCPDeviceInfo".getBytes(), deviceId.getBytes()));
				}
				
				AlarmInstanceOwnItem alarmInstanceOwnItem=null;
				if(jedis!=null&&pcpDeviceInfo!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), pcpDeviceInfo.getAlarmInstanceCode().getBytes())){
					alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), pcpDeviceInfo.getAlarmInstanceCode().getBytes()));
				}
				
				int commAlarmLevel=0,runAlarmLevel=0;
				if(alarmInstanceOwnItem!=null){
					for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
						if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
							commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
							runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}
					}
				}
				
				result_json.append("{\"Id\":"+obj[0]+",");
				result_json.append("\"deviceName\":\""+obj[2]+"\",");
				result_json.append("\"Caldate\":\""+obj[3]+"\",");
				result_json.append("\"ExtendedDays\":\""+obj[4]+"\",");
				
				result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
				result_json.append("\"CommTime\":\""+obj[6]+"\",");
				result_json.append("\"CommTimeEfficiency\":\""+obj[7]+"\",");
				result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
				
				result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
				result_json.append("\"RunTime\":\""+obj[10]+"\",");
				result_json.append("\"RunTimeEfficiency\":\""+obj[11]+"\",");
				result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
				
				result_json.append("\"LiquidWeightProduction\":\""+obj[13]+"\",");
				result_json.append("\"OilWeightProduction\":\""+obj[14]+"\",");
				result_json.append("\"WaterWeightProduction\":\""+obj[15]+"\",");
				result_json.append("\"WeightWaterCut\":\""+obj[16]+"\",");
				
				result_json.append("\"LiquidVolumetricProduction\":\""+obj[17]+"\",");
				result_json.append("\"OilVolumetricProduction\":\""+obj[18]+"\",");
				result_json.append("\"WaterVolumetricProduction\":\""+obj[19]+"\",");
				result_json.append("\"VolumeWaterCut\":\""+obj[20]+"\",");
				
				result_json.append("\"PumpSettingDepth\":\""+obj[21]+"\",");
				result_json.append("\"ProducingFluidlevel\":\""+obj[22]+"\",");
				result_json.append("\"Submergence\":\""+obj[23]+"\",");
				
				result_json.append("\"SystemEfficiency\":\""+obj[24]+"\",");
				result_json.append("\"TodayKWattH\":\""+obj[25]+"\",");
				result_json.append("\"PumpEff\":\""+obj[26]+"\",");

				result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
			}
			result_json.append("]}");
			if(jedis!=null){
				jedis.close();
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getOilWellTotalHistoryData(String data,Page pager)throws Exception {
		String result = "";
		int liftingType=1;
		String startDate=StringManagerUtils.getCurrentTime();
		String endDate=StringManagerUtils.getCurrentTime();
		String deviceName="";
		int statType=1;
		String statValue="";
		String user="";
		String password="";
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
			
			try{
				liftingType=jsonObject.getInt("LiftingType");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				startDate=jsonObject.getString("StartDate");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				endDate=jsonObject.getString("EndDate");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				deviceName=jsonObject.getString("DeviceName");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				statType=jsonObject.getInt("StatType");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				statValue=jsonObject.getString("StatValue");
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(liftingType==1){
			result=this.getDeviceTotalHistory(user,password,deviceName.toString(), statType, statValue, startDate,endDate);
		}else{
			result=this.getPCPDeviceTotalHistory(user,password,deviceName.toString(), statType, statValue, startDate,endDate);
		}
		return result;
	}
	
	public String getDeviceTotalHistory(String user,String password,String deviceName,int statType,String statValue,String startDate,String endDate) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",");
		result_json.append("\"DataList\":[");
		if(userCheckSign==1){
			Jedis jedis=null;
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("DeviceInfo".getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				
				if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String tableName="tbl_srpdailycalculationdata";
			String deviceTableName="tbl_device";
			
			String sql="select t2.id,t.id as deviceId,t.deviceName,to_char(t2.caldate,'yyyy-mm-dd') as caldate,t2.ExtendedDays,"
					+ "decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据')) as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "t2.resultcode,decode(t2.resultcode,0,'无数据',null,'无数据',t3.resultName) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
					+ "t2.liquidWeightProduction,t2.oilWeightProduction,t2.waterWeightProduction,t2.weightWaterCut,"
					+ "t2.liquidVolumetricProduction,t2.oilVolumetricProduction,t2.waterVolumetricProduction,t2.volumeWaterCut,"
					+ "t2.fullnesscoefficient,t2.pumpsettingdepth,t2.producingfluidlevel,t2.submergence,"
					+ "t2.wattDegreeBalance,t2.iDegreeBalance,t2.deltaRadius*100,"
					+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
					+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
					+ "t2.systemEfficiency*100 as systemEfficiency,"
					+ "t2.energyper100mlift,t2.todayKWattH,"
					+ "t2.pumpEff*100 as pumpEff";
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceId=t.id"
					+ " left outer join tbl_srp_worktype t3 on t2.resultcode=t3.resultcode "
					+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  "
					+ " and t2.caldate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+= "and t.deviceName='"+deviceName+"'";
			}
			if(statType==1 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.resultcode,0,'无数据',null,'无数据',t3.resultName)='"+statValue+"'";
			}else if(statType==2 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+statValue+"'";
			}else if(statType==3 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据'))='"+statValue+"'";
			}
			sql+=" order by t2.caldate,t.sortnum,t.deviceName";
			
			List<?> list = this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[1]+"";
				String commStatusName=obj[5]+"";
				String runStatusName=obj[9]+"";
				String resultcode=obj[13]+"";
				
				
				SRPDeviceInfo srpDeviceInfo=null;
				if(jedis!=null&&jedis.hexists("DeviceInfo".getBytes(), deviceId.getBytes())){
					srpDeviceInfo=(SRPDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("DeviceInfo".getBytes(), deviceId.getBytes()));
				}
				
				AlarmInstanceOwnItem alarmInstanceOwnItem=null;
				if(jedis!=null&&srpDeviceInfo!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), srpDeviceInfo.getAlarmInstanceCode().getBytes())){
					alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), srpDeviceInfo.getAlarmInstanceCode().getBytes()));
				}
				
				int commAlarmLevel=0,resultAlarmLevel=0,runAlarmLevel=0;
				if(alarmInstanceOwnItem!=null){
					for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
						if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
							commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
							runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==4 && alarmInstanceOwnItem.getItemList().get(j).getItemCode().equalsIgnoreCase(resultcode)){
							resultAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}
					}
				}
				
				result_json.append("{\"Id\":"+obj[0]+",");
				result_json.append("\"deviceName\":\""+obj[2]+"\",");
				result_json.append("\"Caldate\":\""+obj[3]+"\",");
				result_json.append("\"ExtendedDays\":\""+obj[4]+"\",");
				
				result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
				result_json.append("\"CommTime\":\""+obj[6]+"\",");
				result_json.append("\"CommTimeEfficiency\":\""+obj[7]+"\",");
				result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
				
				result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
				result_json.append("\"RunTime\":\""+obj[10]+"\",");
				result_json.append("\"RunTimeEfficiency\":\""+obj[11]+"\",");
				result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
				result_json.append("\"ResultName\":\""+obj[14]+"\",");
				result_json.append("\"OptimizationSuggestion\":\""+obj[15]+"\",");
				
				result_json.append("\"LiquidWeightProduction\":\""+obj[16]+"\",");
				result_json.append("\"OilWeightProduction\":\""+obj[17]+"\",");
				result_json.append("\"WaterWeightProduction\":\""+obj[18]+"\",");
				result_json.append("\"WeightWaterCut\":\""+obj[19]+"\",");
				
				result_json.append("\"LiquidVolumetricProduction\":\""+obj[20]+"\",");
				result_json.append("\"OilVolumetricProduction\":\""+obj[21]+"\",");
				result_json.append("\"WaterVolumetricProduction\":\""+obj[22]+"\",");
				result_json.append("\"VolumeWaterCut\":\""+obj[23]+"\",");
				
				result_json.append("\"FullnessCoefficient\":\""+obj[24]+"\",");
				result_json.append("\"PumpSettingDepth\":\""+obj[25]+"\",");
				result_json.append("\"ProducingFluidlevel\":\""+obj[26]+"\",");
				result_json.append("\"Submergence\":\""+obj[27]+"\",");
				
				result_json.append("\"IDegreeBalance\":\""+obj[28]+"\",");
				result_json.append("\"WattDegreeBalance\":\""+obj[29]+"\",");
				result_json.append("\"Deltaradius\":\""+obj[30]+"\",");
				
				result_json.append("\"SurfaceSystemEfficiency\":\""+obj[31]+"\",");
				result_json.append("\"WelldownSystemEfficiency\":\""+obj[32]+"\",");
				result_json.append("\"SystemEfficiency\":\""+obj[33]+"\",");
				result_json.append("\"Energyper100mlift\":\""+obj[34]+"\",");
				result_json.append("\"TodayKWattH\":\""+obj[35]+"\",");
				result_json.append("\"PumpEff\":\""+obj[36]+"\",");

				result_json.append("\"ResultAlarmLevel\":"+resultAlarmLevel+",");
				result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
			}
			if(jedis!=null){
				jedis.close();
			}
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getPCPDeviceTotalHistory(String user,String password,String deviceName,int statType,String statValue,String startDate,String endDate) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",");
		result_json.append("\"DataList\":[");
		if(userCheckSign==1){
			Jedis jedis=null;
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("PCPDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				
				if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String tableName="tbl_pcpdailycalculationdata";
			String deviceTableName="tbl_device";
			
			String sql="select t2.id,t.id as deviceId,t.deviceName,to_char(caldate,'yyyy-mm-dd') as caldate,extendedDays,"
					+ "decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据')) as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,weightWaterCut,"
					+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,volumeWaterCut,"
					+ "t2.pumpsettingdepth,t2.producingfluidlevel,t2.submergence,"
					+ "t2.systemEfficiency*100 as systemEfficiency,"
					+ "t2.todayKWattH,"
					+ "t2.pumpEff*100 as pumpEff";
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceId=t.id"
					+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  "
					+ " and t2.caldate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+= "and t.deviceName='"+deviceName+"'";
			}
			if(statType==2 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+statValue+"'";
			}else if(statType==3 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据'))='"+statValue+"'";
			}
			sql+=" order by t2.caldate,t.sortnum,t.deviceName";
			
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[1]+"";
				String commStatusName=obj[5]+"";
				String runStatusName=obj[9]+"";
				
				PCPDeviceInfo pcpDeviceInfo=null;
				if(jedis!=null&&jedis.hexists("PCPDeviceInfo".getBytes(), deviceId.getBytes())){
					pcpDeviceInfo=(PCPDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("PCPDeviceInfo".getBytes(), deviceId.getBytes()));
				}
				
				AlarmInstanceOwnItem alarmInstanceOwnItem=null;
				if(jedis!=null&&pcpDeviceInfo!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), pcpDeviceInfo.getAlarmInstanceCode().getBytes())){
					alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), pcpDeviceInfo.getAlarmInstanceCode().getBytes()));
				}
				
				
				int commAlarmLevel=0,runAlarmLevel=0;
				if(alarmInstanceOwnItem!=null){
					for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
						if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
							commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
							runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}
					}
				}
				
				result_json.append("{\"Id\":"+obj[0]+",");
				result_json.append("\"deviceName\":\""+obj[2]+"\",");
				result_json.append("\"Caldate\":\""+obj[3]+"\",");
				result_json.append("\"ExtendedDays\":\""+obj[4]+"\",");
				
				result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
				result_json.append("\"CommTime\":\""+obj[6]+"\",");
				result_json.append("\"CommTimeEfficiency\":\""+obj[7]+"\",");
				result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
				
				result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
				result_json.append("\"RunTime\":\""+obj[10]+"\",");
				result_json.append("\"RunTimeEfficiency\":\""+obj[11]+"\",");
				result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
				
				result_json.append("\"LiquidWeightProduction\":\""+obj[13]+"\",");
				result_json.append("\"OilWeightProduction\":\""+obj[14]+"\",");
				result_json.append("\"WaterWeightProduction\":\""+obj[15]+"\",");
				result_json.append("\"WeightWaterCut\":\""+obj[16]+"\",");
				
				result_json.append("\"LiquidVolumetricProduction\":\""+obj[17]+"\",");
				result_json.append("\"OilVolumetricProduction\":\""+obj[18]+"\",");
				result_json.append("\"WaterVolumetricProduction\":\""+obj[19]+"\",");
				result_json.append("\"VolumeWaterCut\":\""+obj[20]+"\",");
				
				result_json.append("\"PumpSettingDepth\":\""+obj[21]+"\",");
				result_json.append("\"ProducingFluidlevel\":\""+obj[22]+"\",");
				result_json.append("\"Submergence\":\""+obj[23]+"\",");
				
				result_json.append("\"SystemEfficiency\":\""+obj[24]+"\",");
				result_json.append("\"TodayKWattH\":\""+obj[25]+"\",");
				result_json.append("\"PumpEff\":\""+obj[26]+"\",");

				result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
			}
			if(jedis!=null){
				jedis.close();
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getDeviceInformation(String data,Page pager)throws Exception {
		String json = "";
		List<String> deviceList= new ArrayList<>();
		String user="";
		String password="";
		if(StringManagerUtils.isNotNull(data)){
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
				
				try{
					JSONArray jsonArray = jsonObject.getJSONArray("DeviceList");
					for(int i=0;jsonArray!=null&&i<jsonArray.size();i++){
						deviceList.add(jsonArray.getString(i));
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		json=this.getDeviceInfoList(user,password,deviceList);
		return json;
	}
	
	
	@SuppressWarnings("rawtypes")
	public String getDeviceInfoList(String user,String password,List<String> deviceList) {
		StringBuffer result_json = new StringBuffer();
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",");
		result_json.append("\"DataList\":[");
		if(userCheckSign==1){
			User u=this.userManagerService.getUser(user, password);
			String language=u.getLanguageName();
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			Map<String,Code> codeMap=MemoryDataManagerTask.getCodeMap("APPLICATIONSCENARIOS",language);
			String tableName="viw_device";
			String sql = "select id,orgName_"+language+",deviceName,deviceTypeName_"+language+","
					+ " applicationScenarios,"
					+ " instanceName,displayInstanceName,alarmInstanceName,reportInstanceName,"
					+ " tcptype,signInId,ipport,slave,t.peakdelay,"
					+ " status,decode(t.status,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as statusName,"
					+ " allpath_"+language+","
					+ " to_char(productiondataupdatetime,'yyyy-mm-dd hh24:mi:ss') as productiondataupdatetime,"
					+ " sortNum,"
					+ " decode(t.calculatetype,1,'"+languageResourceMap.get("SRPCalculate")+"',2,'"+languageResourceMap.get("PCPCalculate")+"','"+languageResourceMap.get("nothing")+"') as calculatetype"
					+ " from "+tableName+" t "
					+ " left outer join tbl_role t4 on t4.role_id=(select u.user_type from tbl_user u where u.user_id='"+user+"')"
					+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  ";

			if (deviceList.size()>0) {
				sql += " and t.devicename in (" + StringManagerUtils.joinStringArr2(deviceList, ",")+ ")";
			};
			sql+= " order by t.sortnum,t.devicename ";
			
			List<?> list = this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				result_json.append("{\"DeviceId\":\""+obj[0]+"\",");
				result_json.append("\"OrgName\":\""+obj[1]+"\",");
				result_json.append("\"DeviceName\":\""+obj[2]+"\",");
				result_json.append("\"DeviceType\":\""+obj[3]+"\",");
				result_json.append("\"ApplicationScenarios\":\""+(codeMap.get(obj[4]+"")!=null?codeMap.get(obj[4]+"").getItemname():"")+"\",");
				result_json.append("\"CalculateType\":\""+obj[19]+"\",");
				result_json.append("\"Instance\":\""+obj[5]+"\",");
				result_json.append("\"DisplayInstance\":\""+obj[6]+"\",");
				result_json.append("\"AlarmInstance\":\""+obj[7]+"\",");
				result_json.append("\"ReportInstance\":\""+obj[8]+"\",");
				result_json.append("\"TcpType\":\""+(obj[9]+"").replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
				result_json.append("\"SignInId\":\""+obj[10]+"\",");
				result_json.append("\"IpPort\":\""+obj[11]+"\",");
				result_json.append("\"Slave\":\""+obj[12]+"\",");
				result_json.append("\"PeakDelay\":\""+obj[13]+"\",");
				result_json.append("\"Status\":\""+obj[15]+"\",");
				result_json.append("\"AllPath\":\""+obj[16]+"\",");
				result_json.append("\"SortNum\":\""+obj[18]+"\"},");
			}
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		String json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getPumpingModelInformation(String data,Page pager,String language)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String user="";
		String password="";
		String manufacturer="";
		String model="";
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		if(StringManagerUtils.isNotNull(data)){
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
				
				try{
					manufacturer=jsonObject.getString("Manufacturer");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					model=jsonObject.getString("Model");
				}catch(Exception e){
					e.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",");
		result_json.append("\"DataList\":[");
		if(userCheckSign==1){
			String sql = "select t.id,t.manufacturer,t.model,t.stroke,decode(t.crankrotationdirection,'Anticlockwise','"+languageResourceMap.get("anticlockwise")+"','Clockwise','"+languageResourceMap.get("clockwise")+"','') as crankrotationdirection,"
					+ " t.offsetangleofcrank,t.crankgravityradius,t.singlecrankweight,t.singlecrankpinweight,"
					+ " t.structuralunbalance,t.balanceweight,"
					+ " t.prtf"
					+ " from tbl_pumpingmodel t where 1=1";
			if (StringManagerUtils.isNotNull(manufacturer)) {
				sql += " and t.manufacturer = '"+manufacturer+"'";
			}
			if (StringManagerUtils.isNotNull(model)) {
				sql += " and t.model = '"+model+"'";
			}
			sql+= " order by t.id,t.manufacturer,t.model";
			List<?> list = this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				
				String prtfStr="";
				if(obj[11]!=null){
					try {
						SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[11]);
						CLOB realClob = (CLOB) proxy.getWrappedClob(); 
						prtfStr=StringManagerUtils.CLOBtoString(realClob);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(!StringManagerUtils.isNotNull(prtfStr)){
					prtfStr="{}";
				}
				
				result_json.append("{\"Id\":\""+obj[0]+"\",");
				result_json.append("\"Manufacturer\":\""+obj[1]+"\",");
				result_json.append("\"Model\":\""+obj[2]+"\",");
				result_json.append("\"Stroke\":\""+obj[3]+"\",");
				result_json.append("\"CrankRotationDirection\":\""+obj[4]+"\",");
				result_json.append("\"OffsetAngleOfCrank\":\""+obj[5]+"\",");
				result_json.append("\"CrankGravityRadius\":\""+obj[6]+"\",");
				result_json.append("\"SingleCrankWeight\":\""+obj[7]+"\",");
				result_json.append("\"SingleCrankPinWeight\":\""+obj[8]+"\",");
				result_json.append("\"StructuralUnbalance\":\""+obj[9]+"\",");
				result_json.append("\"BalanceWeight\":\""+obj[10]+"\",");
				result_json.append("\"PumpingPRTF\":"+prtfStr+"},");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
}
