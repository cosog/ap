package com.cosog.service.mobile;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
		StringBuffer devices= new StringBuffer();
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
						devices.append(""+jsonArray.getString(i)+",");
						deviceNameList.add(jsonArray.getString(i));
					}
					if(devices.toString().endsWith(",")){
						devices.deleteCharAt(devices.length() - 1);
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
				json=this.getRealTimeMonitoringCommStatusStatData(user,password,devices.toString());
			}else if(type==2){
				json=this.getRealTimeMonitoringRunStatusStatData(user,password,devices.toString());
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
	
	public String getRealTimeMonitoringCommStatusStatData(String user,String password,String devices) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int online=0,goOnline=0,offline=0;
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign);
		if(userCheckSign==1){
			User userInfo=this.userManagerService.getUser(user, password);
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo!=null?userInfo.getLanguageName():"");
			
			String [] deviceList=null;
			if(StringManagerUtils.isNotNull(devices)){
				deviceList=devices.split(",");
			}
			String tableName="tbl_acqdata_latest";
			String deviceTableName="viw_device";
			
			String sql="select t2.commstatus,count(1) from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on  t2.deviceId=t.id "
					+ " where t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent) ";
			if(deviceList!=null){
				sql+=" and t.deviceName in ( "+StringManagerUtils.joinStringArr2(deviceList, ",")+" )";
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
	
	public String getRealTimeMonitoringRunStatusStatData(String user,String password,String devices) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int run=0,stop=0,noData=0,offline=0,goOnline=0;
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign);
		if(userCheckSign==1){
			User userInfo=this.userManagerService.getUser(user, password);
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo!=null?userInfo.getLanguageName():"");
			String [] deviceList=null;
			if(StringManagerUtils.isNotNull(devices)){
				deviceList=devices.split(",");
			}
			String tableName="tbl_acqdata_latest";
			String deviceTableName="viw_device";
			String sql="select decode(t2.commstatus,0,-1,2,-2,decode(t2.runstatus,null,2,t2.runstatus)) as runstatus,count(1) from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on  t2.deviceId=t.id "
					+ " where 1=1 ";
			if(deviceList!=null){
				sql+=" and t.deviceName in ( "+StringManagerUtils.joinStringArr2(deviceList, ",")+" )";
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
	
	public String getOilWellRealtimeWellListData(String data,Page pager)throws Exception {
		String json = "";
		StringBuffer wells= new StringBuffer();
		int liftingType=1;
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
					liftingType=jsonObject.getInt("LiftingType");
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
		}
		if(liftingType==1){
			json=this.getDeviceRealTimeOverview(user,password,wells.toString(), statType, statValue);
		}else{
			json=this.getPCPDeviceRealTimeOverview(user,password,wells.toString(), statType, statValue);
		}
		return json;
	}
	
	
	public String getDeviceRealTimeOverview(String deviceId,String deviceName,String deviceType,String calculateType,int userNo,String language) throws IOException, SQLException{
		int items=3;
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
		
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
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
					
					//计算项
					if(calItemList!=null){
						for(CalItem calItem:calItemList){
							if(StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), calItem.getCode(), false,0)){
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
							if(acqItem.getDailyTotalCalculate()==1 && StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), (acqItem.getItemCode()+"_total").toUpperCase(), false,0)){
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
							if(StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), calItem.getCode(), false,0)){
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
							+ "t2.acqdata ";

					for(int i=0;i<displayCalItemList.size();i++){
						String column=displayCalItemList.get(i).getCode();
						if(StringManagerUtils.stringToInteger(calculateType)>0){
							if("resultName".equalsIgnoreCase(column)){
								column="resultCode";
							}else if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
								column=column+"*"+timeEfficiencyZoom+" as "+column;
							}
							sql+=",t3."+column;
						}else{
							if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
								column=column+"*"+timeEfficiencyZoom+" as "+column;
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
						String acqData=StringManagerUtils.CLOBObjectToString(obj[6]);
						if(displayInputItemList.size()>0){
							String productionData=(obj[obj.length-1]+"").replaceAll("null", "");
							Gson gson = new Gson();
							java.lang.reflect.Type type=null;
							if(StringManagerUtils.stringToInteger(calculateType)==1){
								type = new TypeToken<SRPCalculateRequestData>() {}.getType();
								SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
								for(int i=0;i<displayInputItemList.size();i++){
									String columnName=displayInputItemList.get(i).getName();
									String rawColumnName=columnName;
									String value="";
									String rawValue=value;
									String addr="";
									String column=displayInputItemList.get(i).getCode();
									String columnDataType="";
									String resolutionMode="";
									String bitIndex="";
									String unit=displayInputItemList.get(i).getUnit();
									int sort=9999;
									for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
										if(column.equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
											sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
											break;
										}
									}
									
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
												columnName=columnName.replace("油层", "煤层");
											}
										}else if("ReservoirTemperature".equalsIgnoreCase(column) && srpProductionData.getReservoir()!=null ){
											value=srpProductionData.getReservoir().getTemperature()+"";
											if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
												columnName=columnName.replace("油层", "煤层");
											}
										}else if("TubingPressure".equalsIgnoreCase(column) && srpProductionData.getProduction()!=null ){
											value=srpProductionData.getProduction().getTubingPressure()+"";
											if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
												columnName=columnName.replace("油压", "管压");
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
								for(int i=0;i<displayInputItemList.size();i++){
									String columnName=displayInputItemList.get(i).getName();
									String rawColumnName=columnName;
									String value="";
									String rawValue=value;
									String addr="";
									String column=displayInputItemList.get(i).getCode();
									String columnDataType="";
									String resolutionMode="";
									String bitIndex="";
									String unit=displayInputItemList.get(i).getUnit();
									int sort=9999;
									for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
										if(column.equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
											sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
											break;
										}
									}
									
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
												columnName=columnName.replace("油层", "煤层");
											}
										}else if("ReservoirTemperature".equalsIgnoreCase(column) && pcpProductionData.getReservoir()!=null ){
											value=pcpProductionData.getReservoir().getTemperature()+"";
											if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
												columnName=columnName.replace("油层", "煤层");
											}
										}else if("TubingPressure".equalsIgnoreCase(column) && pcpProductionData.getProduction()!=null ){
											value=pcpProductionData.getProduction().getTubingPressure()+"";
											if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
												columnName=columnName.replace("油压", "管压");
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
						
						for(int i=0;i<displayCalItemList.size();i++){
							int index=i+7;
							if(index<obj.length){
								String columnName=displayCalItemList.get(i).getName();
								String rawColumnName=columnName;
								String value=obj[index]+"";
								if(obj[index] instanceof CLOB || obj[index] instanceof Clob){
									value=StringManagerUtils.CLOBObjectToString(obj[index]);
								}
								String rawValue=value;
								String addr="";
								String column=displayCalItemList.get(i).getCode();
								String columnDataType="";
								String resolutionMode="";
								String bitIndex="";
								String unit=displayCalItemList.get(i).getUnit();
								int sort=9999;
								for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
									if(column.equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
										sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
										//如果是工况
										if("resultCode".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())||"resultName".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
											workType=MemoryDataManagerTask.getWorkTypeByCode(value,language);
											if(workType!=null){
												value=workType.getResultName();
											}
										}
										break;
									}
								}
								ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,1);
								protocolItemResolutionDataList.add(protocolItemResolutionData);
							}
						}
						
						if(protocolItems.size()>0){
							Gson gson = new Gson();
							java.lang.reflect.Type type=null;
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
											
											if("int".equalsIgnoreCase(item.getIFDataType()) || "uint".equalsIgnoreCase(item.getIFDataType()) || item.getIFDataType().contains("int")
													||"float32".equalsIgnoreCase(item.getIFDataType())
													||"float64".equalsIgnoreCase(item.getIFDataType())){
												if(value.toUpperCase().contains("E")){
								                 	value=StringManagerUtils.scientificNotationToNormal(value);
								                 }
											}
											
											if(item.getResolutionMode()==1 || item.getResolutionMode()==2){//如果是枚举量
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
																	ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
																	protocolItemResolutionDataList.add(protocolItemResolutionData);
																	match=true;
																	break;
																}
															}
															if(!match){
																value="";
																rawValue="";
																bitIndex=item.getMeaning().get(l).getValue()+"";
																ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,item.getMeaning().get(l).getValue()+"",unit,sort,0);
																protocolItemResolutionDataList.add(protocolItemResolutionData);
															}
														}else{
															for(int m=0;m<displayInstanceOwnItem.getItemList().size();m++){
																if(displayInstanceOwnItem.getItemList().get(m).getItemCode().equalsIgnoreCase(column) && displayInstanceOwnItem.getItemList().get(m).getBitIndex()==item.getMeaning().get(l).getValue() ){
																	sort=displayInstanceOwnItem.getItemList().get(m).getRealtimeSort();
																	break;
																}
															}
															value="";
															rawValue="";
															bitIndex=item.getMeaning().get(l).getValue()+"";
															ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,item.getMeaning().get(l).getValue()+"",unit,sort,0);
															protocolItemResolutionDataList.add(protocolItemResolutionData);
														}
													}
												}else{
													for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
														if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column)){
															sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
															break;
														}
													}
													ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
													protocolItemResolutionDataList.add(protocolItemResolutionData);
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
											}
											break;
										}
									}
								}
							}
						}
						
						//获取日汇总计算数据
						if(dailyTotalCalItemMap.size()>0){
							String dailyTotalDatasql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
									+ "t.itemcolumn,t.itemname,"
									+ "t.totalvalue,t.todayvalue "
									+ " from TBL_DAILYTOTALCALCULATE_LATEST t "
									+ " where t.deviceid="+deviceId;
							List<?> dailyTotalDatasList = this.findCallSql(dailyTotalDatasql);
							for(int j=0;j<dailyTotalDatasList.size();j++){
								Object[] dailyTotalDataObj=(Object[]) dailyTotalDatasList.get(j);
								if(dailyTotalCalItemMap.containsKey( (dailyTotalDataObj[1]+"").toUpperCase() )){
									DisplayInstanceOwnItem.DisplayItem displayItem=dailyTotalCalItemMap.get( (dailyTotalDataObj[1]+"").toUpperCase() );
									if(displayItem!=null){
										String unit="";
										
										if(protocol!=null&&protocol.getItems()!=null){
											for(ModbusProtocolConfig.Items item:protocol.getItems()){
												if(displayItem.getItemSourceName().equalsIgnoreCase(item.getTitle())){
													unit=item.getUnit();
													break;
												}
											}
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
									}
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
								
								int type=0;
								
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
									type=finalProtocolItemResolutionDataList.get(index).getType();
									
									for(DisplayInstanceOwnItem.DisplayItem displayItem:displayInstanceOwnItem.getItemList()){
										if(type==0){//采控项
											if("0".equalsIgnoreCase(resolutionMode) 
													&& displayItem.getType()==type
													&& displayItem.getItemCode().equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getColumn())  
													&& displayItem.getBitIndex()==StringManagerUtils.stringToInteger(finalProtocolItemResolutionDataList.get(index).getBitIndex())
													){//开关量
												realtimeColor=displayItem.getRealtimeColor();
												realtimeBgColor=displayItem.getRealtimeBgColor();
												historyColor=displayItem.getHistoryColor();
												historyBgColor=displayItem.getHistoryBgColor();
												break;
											}else if( ("1".equalsIgnoreCase(resolutionMode) || "2".equalsIgnoreCase(resolutionMode) )
													&& displayItem.getType()==type
													&& displayItem.getItemCode().equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getColumn())  
													){
												realtimeColor=displayItem.getRealtimeColor();
												realtimeBgColor=displayItem.getRealtimeBgColor();
												historyColor=displayItem.getHistoryColor();
												historyBgColor=displayItem.getHistoryBgColor();
												break;
											}
										}else if(type==1 || type==3){//计算项和录入项
											if(displayItem.getType()==type
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
									
									if(alarmInstanceOwnItem!=null){
										for(int l=0;l<alarmInstanceOwnItem.getItemList().size();l++){
											int alarmType=alarmInstanceOwnItem.getItemList().get(l).getType();
											float hystersis=alarmInstanceOwnItem.getItemList().get(l).getHystersis();
											float upperLimit=alarmInstanceOwnItem.getItemList().get(l).getUpperLimit();
											float lowerLimit=alarmInstanceOwnItem.getItemList().get(l).getLowerLimit();
											float alarmValue=alarmInstanceOwnItem.getItemList().get(l).getValue();
											if(alarmInstanceOwnItem.getItemList().get(l).getAlarmLevel()>0){
												if(alarmType<3){//采集数据报警
													if(finalProtocolItemResolutionDataList.get(index).getAddr().equals(alarmInstanceOwnItem.getItemList().get(l).getItemAddr()+"")){
														if(alarmType==2 && StringManagerUtils.isNotNull(rawValue)){//数据量报警
															if((StringManagerUtils.stringToFloat(rawValue)>upperLimit)
																	||(StringManagerUtils.stringToFloat(rawValue)<lowerLimit)
																	){
																alarmLevel=alarmInstanceOwnItem.getItemList().get(l).getAlarmLevel();
															}
															break;
														}else if(alarmType==0){//开关量报警
															if(StringManagerUtils.isNotNull(bitIndex)){
																if(bitIndex.equals(alarmInstanceOwnItem.getItemList().get(l).getBitIndex()+"") && StringManagerUtils.stringToInteger(rawValue)==alarmValue){
																	alarmLevel=alarmInstanceOwnItem.getItemList().get(l).getAlarmLevel();
																}
															}
														}else if(alarmType==1){//枚举量报警
															if(StringManagerUtils.stringToInteger(rawValue)==alarmValue){
																alarmLevel=alarmInstanceOwnItem.getItemList().get(l).getAlarmLevel();
															}
														}
													}
												}else if(alarmType==3){//通信报警
													
												}else if(alarmType==4){//工况报警
													if("resultCode".equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getColumn())||"resultName".equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getColumn())){
														if(alarmInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getRawValue())){
															alarmLevel=alarmInstanceOwnItem.getItemList().get(l).getAlarmLevel();
														}
													}
												}else if(alarmType==5){//计算数据报警
													if(finalProtocolItemResolutionDataList.get(index).getColumn().equals(alarmInstanceOwnItem.getItemList().get(l).getItemCode())){
														if((StringManagerUtils.stringToFloat(rawValue)>upperLimit)
																||(StringManagerUtils.stringToFloat(rawValue)<lowerLimit)
																){
															alarmLevel=alarmInstanceOwnItem.getItemList().get(l).getAlarmLevel();
														}
														break;
													}
												}
											}
										}
									}
									
								}
								
								if(StringManagerUtils.isNotNull(columnName)&&StringManagerUtils.isNotNull(unit)){
									result_json.append("\"name"+(k+1)+"\":\""+(columnName+"("+unit+")")+"\",");
								}else{
									result_json.append("\"name"+(k+1)+"\":\""+columnName+"\",");
								}
								
								result_json.append("\"value"+(k+1)+"\":\""+value+"\",");
								
								info_json.append("{\"row\":"+j+",\"col\":"+k+",\"addr\":\""+addr+"\","
										+ "\"columnName\":\""+columnName+"\","
										+ "\"column\":\""+column+"\","
										+ "\"value\":\""+value+"\","
										+ "\"columnDataType\":\""+columnDataType+"\","
										+ "\"resolutionMode\":\""+resolutionMode+"\","
										+ "\"realtimeColor\":\""+realtimeColor+"\","
										+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
										+ "\"historyColor\":\""+historyColor+"\","
										+ "\"historyBgColor\":\""+historyBgColor+"\","
										+ "\"type\":\""+type+"\","
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
//		System.out.println(result_json.toString().replaceAll("null", ""));
		return result_json.toString().replaceAll("null", "");
	}
	
	
	
	
	
	
	
	
	
	public String getDeviceRealTimeOverview(String user,String password,String wells,int statType,String statValue) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
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
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
				}
				
				if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String tableName="tbl_acqdata_latest";
			String deviceTableName="tbl_device";
			
			
			String sql="select t.id,t.deviceName,"
					+ "c1.name as devicetypename,"
					+ "to_char(t2.fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "decode(t2.runstatus,null,2,t2.runstatus),decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据')) as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "t2.resultcode,decode(t2.resultcode,0,'无数据',null,'无数据',t3.resultName) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
					+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,"
					+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,"
					+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
					+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
					+ "t2.systemEfficiency*100 as systemEfficiency,t2.energyper100mlift,"
					+ "t2.pumpEff*100 as pumpEff,"
					+ "t2.UpStrokeIMax,t2.DownStrokeIMax,t2.iDegreeBalance,"
					+ "t2.UpStrokeWattMax,t2.DownStrokeWattMax,t2.wattDegreeBalance,"
					+ "t2.deltaradius*100 as deltaradius,"
					+ "t2.todayKWattH,"
					+ "t2.productiondata";
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceId=t.id"
					+ " left outer join tbl_srp_worktype t3 on t2.resultcode=t3.resultcode "
					+ " left outer join tbl_devicetypeinfo c1 on t.devicetype=c1.id "
					+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  ";
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
			
			
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(sql);
			
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[0]+"";
				String commStatusName=obj[5]+"";
				String runStatusName=obj[10]+"";
				String resultcode=obj[14]+"";
				String weightWaterCut="";
				String volumeWaterCut="";
				String productionData=obj[obj.length-1].toString();
				type = new TypeToken<SRPCalculateRequestData>() {}.getType();
				SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
				if(srpProductionData!=null&&srpProductionData.getProduction()!=null){
					weightWaterCut=srpProductionData.getProduction().getWeightWaterCut()+"";
					volumeWaterCut=srpProductionData.getProduction().getWaterCut()+"";
				}
				
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
				
				result_json.append("{\"Id\":"+deviceId+",");
				result_json.append("\"deviceName\":\""+obj[1]+"\",");
				result_json.append("\"DeviceTypeName\":\""+obj[2]+"\",");
				result_json.append("\"AcqTime\":\""+obj[3]+"\",");
				result_json.append("\"CommStatus\":"+obj[4]+",");
				result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
				result_json.append("\"CommTime\":\""+obj[6]+"\",");
				result_json.append("\"CommTimeEfficiency\":\""+obj[7]+"\",");
				result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
				
				result_json.append("\"RunStatus\":"+obj[9]+",");
				result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
				result_json.append("\"RunTime\":\""+obj[11]+"\",");
				result_json.append("\"RunTimeEfficiency\":\""+obj[12]+"\",");
				result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[13])+"\",");
				result_json.append("\"ResultCode\":\""+obj[14]+"\",");
				result_json.append("\"ResultName\":\""+obj[15]+"\",");
				result_json.append("\"OptimizationSuggestion\":\""+obj[16]+"\",");
				
				result_json.append("\"LiquidWeightProduction\":\""+obj[17]+"\",");
				result_json.append("\"OilWeightProduction\":\""+obj[18]+"\",");
				result_json.append("\"WaterWeightProduction\":\""+obj[19]+"\",");
				result_json.append("\"WeightWaterCut\":\""+weightWaterCut+"\",");
				result_json.append("\"LiquidWeightProduction_L\":\""+obj[20]+"\",");
				
				result_json.append("\"LiquidVolumetricProduction\":\""+obj[21]+"\",");
				result_json.append("\"OilVolumetricProduction\":\""+obj[22]+"\",");
				result_json.append("\"WaterVolumetricProduction\":\""+obj[23]+"\",");
				result_json.append("\"VolumeWaterCut\":\""+volumeWaterCut+"\",");
				result_json.append("\"LiquidVolumetricProduction_L\":\""+obj[24]+"\",");
				
				result_json.append("\"SurfaceSystemEfficiency\":\""+obj[25]+"\",");
				result_json.append("\"WelldownSystemEfficiency\":\""+obj[26]+"\",");
				result_json.append("\"SystemEfficiency\":\""+obj[27]+"\",");
				result_json.append("\"Energyper100mlift\":\""+obj[28]+"\",");
				result_json.append("\"PumpEff\":\""+obj[29]+"\",");
				
				result_json.append("\"UpStrokeIMax\":\""+obj[30]+"\",");
				result_json.append("\"DownStrokeIMax\":\""+obj[31]+"\",");
				result_json.append("\"IDegreeBalance\":\""+obj[32]+"\",");
				
				result_json.append("\"UpStrokeWattMax\":\""+obj[33]+"\",");
				result_json.append("\"DownStrokeWattMax\":\""+obj[34]+"\",");
				result_json.append("\"WattDegreeBalance\":\""+obj[35]+"\",");
				
				result_json.append("\"Deltaradius\":\""+obj[36]+"\",");
				
				result_json.append("\"TodayKWattH\":\""+obj[37]+"\",");
				

				result_json.append("\"ResultAlarmLevel\":"+resultAlarmLevel+",");
				result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			if(jedis!=null){
				jedis.close();
			}
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getPCPDeviceRealTimeOverview(String user,String password,String wells,int statType,String statValue) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
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
			
			String tableName="tbl_pcpacqdata_latest";
			String deviceTableName="tbl_device";
			
			String sql="select t.id,t.deviceName,"
					+ "c1.name as devicetypename,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据')) as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "t2.liquidWeightProduction,t2.oilWeightProduction,t2.waterWeightProduction,t2.liquidWeightProduction_L,"
					+ "t2.liquidVolumetricProduction,t2.oilVolumetricProduction,t2.waterVolumetricProduction,t2.liquidVolumetricProduction_L,"
					+ "t2.systemEfficiency*100 as systemEfficiency,t2.energyper100mlift,t2.pumpEff*100 as pumpEff,"
					+ "t2.todayKWattH,"
					+ "t2.productiondata";
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceId=t.id"
					+ " left outer join tbl_devicetypeinfo c1 on t.devicetype=c1.id "
					+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  ";
			if(wellList!=null){
				sql+=" and t.deviceName in ( "+StringManagerUtils.joinStringArr2(wellList, ",")+" )";
			}
			if(statType==2 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+statValue+"'";
			}else if(statType==3 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据'))='"+statValue+"'";
			}
			sql+=" order by t.sortnum,t.deviceName";
			
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[0]+"";
				String commStatusName=obj[5]+"";
				String runStatusName=obj[10]+"";
				
				String weightWaterCut="";
				String volumeWaterCut="";
				String productionData=obj[obj.length-1].toString();
				type = new TypeToken<PCPCalculateRequestData>() {}.getType();
				PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
				if(pcpProductionData!=null&&pcpProductionData.getProduction()!=null){
					weightWaterCut=pcpProductionData.getProduction().getWeightWaterCut()+"";
					volumeWaterCut=pcpProductionData.getProduction().getWaterCut()+"";
				}
				
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
				
				result_json.append("{\"Id\":"+deviceId+",");
				result_json.append("\"deviceName\":\""+obj[1]+"\",");
				
				result_json.append("\"DeviceTypeName\":\""+obj[2]+"\",");
				result_json.append("\"AcqTime\":\""+obj[3]+"\",");
				result_json.append("\"CommStatus\":"+obj[4]+",");
				result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
				result_json.append("\"CommTime\":\""+obj[5]+"\",");
				result_json.append("\"CommTimeEfficiency\":\""+obj[7]+"\",");
				result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
				
				result_json.append("\"RunStatus\":"+obj[9]+",");
				result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
				result_json.append("\"RunTime\":\""+obj[11]+"\",");
				result_json.append("\"RunTimeEfficiency\":\""+obj[12]+"\",");
				result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[13])+"\",");
				
				result_json.append("\"LiquidWeightProduction\":\""+obj[14]+"\",");
				result_json.append("\"OilWeightProduction\":\""+obj[15]+"\",");
				result_json.append("\"WaterWeightProduction\":\""+obj[16]+"\",");
				result_json.append("\"WeightWaterCut\":\""+weightWaterCut+"\",");
				result_json.append("\"LiquidWeightProduction_L\":\""+obj[17]+"\",");
				
				result_json.append("\"LiquidVolumetricProduction\":\""+obj[18]+"\",");
				result_json.append("\"OilVolumetricProduction\":\""+obj[19]+"\",");
				result_json.append("\"WaterVolumetricProduction\":\""+obj[20]+"\",");
				result_json.append("\"VolumeWaterCut\":\""+volumeWaterCut+"\",");
				result_json.append("\"LiquidVolumetricProduction_L\":\""+obj[21]+"\",");
				
				result_json.append("\"SystemEfficiency\":\""+obj[22]+"\",");
				result_json.append("\"Energyper100mlift\":\""+obj[23]+"\",");
				result_json.append("\"PumpEff\":\""+obj[24]+"\",");
				
				result_json.append("\"TodayKWattH\":\""+obj[25]+"\",");
				
				result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			if(jedis!=null){
				jedis.close();
			}
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getOilWellHistoryData(String data,Page pager)throws Exception {
		String json = "";
		int liftingType=1;
		int statType=1;
		String statValue="";
		String deviceName="";
		String startDate=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String endDate=startDate;
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
					liftingType=jsonObject.getInt("LiftingType");
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
					deviceName=jsonObject.getString("deviceName");
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
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(liftingType==1){
			json=this.getDeviceHistoryData(user,password,deviceName, statType, statValue, startDate, endDate);
		}else{
			json=this.getPCPDeviceHistoryData(user,password,deviceName, statType, statValue, startDate, endDate);
		}
		return json;
	}
	
	public String getDeviceHistoryData(String user,String password,String deviceName,int statType,String statValue,String startDate,String endDate) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",");
		result_json.append("\"DataList\":[");
		if(userCheckSign==1){
			Jedis jedis=null;
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("DeviceInfo".getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
				}
				
				if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String hisTableName="tbl_srpacqdata_hist";
			String deviceTableName="tbl_device";
			String sql="select t2.id,t.id as deviceId,t.deviceName,"
					+ "to_char(t2.fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据')) as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "t2.resultcode,decode(t2.commstatus,1,decode(t2.resultcode,0,'无数据',null,'无数据',t3.resultName),'' ) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
					+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,"
					+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,"
					+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
					+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
					+ "t2.systemEfficiency*100 as systemEfficiency,"
					+ "t2.energyper100mlift,t2.pumpEff*100 as pumpEff,"
					+ "t2.UpStrokeIMax,t2.DownStrokeIMax,t2.iDegreeBalance,"
					+ "t2.UpStrokeWattMax,t2.DownStrokeWattMax,t2.wattDegreeBalance,"
					+ "t2.deltaradius*100 as deltaradius,"
					+ "t2.todayKWattH,"
					+ "t2.productiondata";
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+hisTableName+" t2 on t2.deviceId=t.id"
					+ " left outer join tbl_srp_worktype t3 on t2.resultcode=t3.resultcode "
					+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  "
					+ " and t2.fesdiagramAcqTime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss') ";
			if(statType==1 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.resultcode,0,'无数据',null,'无数据',t3.resultName)='"+statValue+"'";
			}else if(statType==2 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+statValue+"'";
			}else if(statType==3 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据'))='"+statValue+"'";
			}
			
			
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+= "and t.deviceName='"+deviceName+"'";
			}	
			sql+= "  order by t2.fesdiagramAcqTime desc";
			
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(sql);
			
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[1]+"";
				String commStatusName=obj[5]+"";
				String runStatusName=obj[10]+"";
				String resultcode=obj[14]+"";
				
				String weightWaterCut="";
				String volumeWaterCut="";
				String productionData=obj[obj.length-1].toString();
				type = new TypeToken<SRPCalculateRequestData>() {}.getType();
				SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
				if(srpProductionData!=null&&srpProductionData.getProduction()!=null){
					weightWaterCut=srpProductionData.getProduction().getWeightWaterCut()+"";
					volumeWaterCut=srpProductionData.getProduction().getWaterCut()+"";
				}
				
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
				result_json.append("\"DeviceId\":\""+deviceId+"\",");
				result_json.append("\"deviceName\":\""+obj[2]+"\",");
				result_json.append("\"AcqTime\":\""+obj[3]+"\",");
				result_json.append("\"CommStatus\":"+obj[4]+",");
				result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
				result_json.append("\"CommTime\":\""+obj[6]+"\",");
				result_json.append("\"CommTimeEfficiency\":\""+obj[7]+"\",");
				result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
				result_json.append("\"RunStatus\":"+obj[9]+",");
				result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
				result_json.append("\"RunTime\":\""+obj[11]+"\",");
				result_json.append("\"RunTimeEfficiency\":\""+obj[12]+"\",");
				result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[13])+"\",");
				result_json.append("\"ResultCode\":\""+resultcode+"\",");
				result_json.append("\"ResultName\":\""+obj[15]+"\",");
				result_json.append("\"OptimizationSuggestion\":\""+obj[16]+"\",");
				result_json.append("\"LiquidWeightProduction\":\""+obj[17]+"\",");
				result_json.append("\"OilWeightProduction\":\""+obj[18]+"\",");
				result_json.append("\"WaterWeightProduction\":\""+obj[19]+"\",");
				result_json.append("\"WeightWaterCut\":\""+weightWaterCut+"\",");
				result_json.append("\"LiquidWeightProduction_L\":\""+obj[20]+"\",");
				
				result_json.append("\"LiquidVolumetricProduction\":\""+obj[21]+"\",");
				result_json.append("\"OilVolumetricProduction\":\""+obj[22]+"\",");
				result_json.append("\"WaterVolumetricProduction\":\""+obj[23]+"\",");
				result_json.append("\"VolumeWaterCut\":\""+volumeWaterCut+"\",");
				result_json.append("\"LiquidVolumetricProduction_L\":\""+obj[24]+"\",");
				
				result_json.append("\"SurfaceSystemEfficiency\":\""+obj[25]+"\",");
				result_json.append("\"WelldownSystemEfficiency\":\""+obj[26]+"\",");
				result_json.append("\"SystemEfficiency\":\""+obj[27]+"\",");
				result_json.append("\"Energyper100mlift\":\""+obj[28]+"\",");
				result_json.append("\"PumpEff\":\""+obj[29]+"\",");
				
				result_json.append("\"UpStrokeIMax\":\""+obj[30]+"\",");
				result_json.append("\"DownStrokeIMax\":\""+obj[31]+"\",");
				result_json.append("\"IDegreeBalance\":\""+obj[32]+"\",");
				
				result_json.append("\"UpStrokeWattMax\":\""+obj[33]+"\",");
				result_json.append("\"DownStrokeWattMax\":\""+obj[34]+"\",");
				result_json.append("\"WattDegreeBalance\":\""+obj[35]+"\",");
				result_json.append("\"Deltaradius\":\""+obj[36]+"\",");
				result_json.append("\"TodayKWattH\":\""+obj[37]+"\",");
				
				result_json.append("\"ResultAlarmLevel\":"+resultAlarmLevel+",");
				result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			if(jedis!=null){
				jedis.close();
			}
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getPCPDeviceHistoryData(String user,String password,String deviceName,int statType,String statValue,String startDate,String endDate) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int userCheckSign=this.userManagerService.userCheck(user, password);
		result_json.append("{ \"ResultStatus\":"+userCheckSign+",");
		result_json.append("\"DataList\":[");
		if(userCheckSign==1){
			Jedis jedis=null;
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
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
			
			String hisTableName="tbl_pcpacqdata_hist";
			String deviceTableName="tbl_device";
			
			String sql="select t2.id,t.id as deviceId,t.deviceName,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据')) as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,"
					+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,"
					+ "systemEfficiency*100 as systemEfficiency,energyper100mlift,pumpEff*100 as pumpEff,"
					+ "todayKWattH,"
					+ "t2.productiondata";
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+hisTableName+" t2 on t2.deviceId=t.id"
					+ " where  t.orgid in( select org.org_id from tbl_org org start with org.org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent)  "
					+ " and t2.acqTime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss') ";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+= "and t.deviceName='"+deviceName+"'";
			}	
			if(statType==2 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+statValue+"'";
			}else if(statType==3 && StringManagerUtils.isNotNull(statValue)){
				sql+=" and decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停止','无数据'))='"+statValue+"'";
			}
			sql+= "  order by t2.acqtime desc";
			
			
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[1]+"";
				String commStatusName=obj[5]+"";
				String runStatusName=obj[10]+"";
				
				String weightWaterCut="";
				String volumeWaterCut="";
				String productionData=obj[obj.length-1].toString();
				type = new TypeToken<PCPCalculateRequestData>() {}.getType();
				PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
				if(pcpProductionData!=null&&pcpProductionData.getProduction()!=null){
					weightWaterCut=pcpProductionData.getProduction().getWeightWaterCut()+"";
					volumeWaterCut=pcpProductionData.getProduction().getWaterCut()+"";
				}
				
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
				result_json.append("\"DeviceId\":\""+deviceId+"\",");
				result_json.append("\"deviceName\":\""+obj[2]+"\",");
				result_json.append("\"AcqTime\":\""+obj[3]+"\",");
				result_json.append("\"CommStatus\":"+obj[4]+",");
				result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
				result_json.append("\"CommTime\":\""+obj[6]+"\",");
				result_json.append("\"CommTimeEfficiency\":\""+obj[7]+"\",");
				result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
				result_json.append("\"RunStatus\":"+obj[9]+",");
				result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
				result_json.append("\"RunTime\":\""+obj[11]+"\",");
				result_json.append("\"RunTimeEfficiency\":\""+obj[12]+"\",");
				result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[13])+"\",");
				
				result_json.append("\"LiquidWeightProduction\":\""+obj[14]+"\",");
				result_json.append("\"OilWeightProduction\":\""+obj[15]+"\",");
				result_json.append("\"WaterWeightProduction\":\""+obj[16]+"\",");
				result_json.append("\"WeightWaterCut\":\""+weightWaterCut+"\",");
				result_json.append("\"LiquidWeightProduction_L\":\""+obj[17]+"\",");
				
				result_json.append("\"LiquidVolumetricProduction\":\""+obj[18]+"\",");
				result_json.append("\"OilVolumetricProduction\":\""+obj[19]+"\",");
				result_json.append("\"WaterVolumetricProduction\":\""+obj[20]+"\",");
				result_json.append("\"VolumeWaterCut\":\""+volumeWaterCut+"\",");
				result_json.append("\"LiquidVolumetricProduction_L\":\""+obj[21]+"\",");
				
				result_json.append("\"SystemEfficiency\":\""+obj[22]+"\",");
				result_json.append("\"Energyper100mlift\":\""+obj[23]+"\",");
				result_json.append("\"PumpEff\":\""+obj[24]+"\",");
				result_json.append("\"TodayKWattH\":\""+obj[25]+"\",");
				
				result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			if(jedis!=null){
				jedis.close();
			}
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getOilWellAnalysisData(String data) throws SQLException, IOException{
		String json="";
		if(StringManagerUtils.isNotNull(data)){
			try{
				JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
				if(jsonObject!=null){
					int liftingType=1;
					try{
						liftingType=jsonObject.getInt("LiftingType");
					}catch(Exception e){
						e.printStackTrace();
					}
					if(liftingType==1){
						json=getPumpunitRealtimeWellAnalysisData(data);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return json;
	}
	
	public String getRealTimeFESDiagramData(String data) throws SQLException, IOException {
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
					deviceName=jsonObject.getString("deviceName");
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
				result_json.append("\"deviceName\":\"" + obj[1] + "\",");
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
				
				result_json.append("downStrokeIMax:\""+obj[21]+"\",");
				result_json.append("upStrokeIMax:\""+obj[22]+"\",");
				result_json.append("iDegreeBalance:\""+obj[23]+"\",");
				result_json.append("downStrokeWattMax:\""+obj[24]+"\",");
				result_json.append("upStrokeWattMax:\""+obj[25]+"\",");     
				result_json.append("wattDegreeBalance:\""+obj[26]+"\",");
				result_json.append("deltaRadius:\""+obj[27]+"\",");
				
				result_json.append("WellDownSystemEfficiency:\""+obj[28]+"\",");
				result_json.append("SurfaceSystemEfficiency:\""+obj[29]+"\",");
				result_json.append("SystemEfficiency:\""+obj[30]+"\",");
				result_json.append("EnergyPer100mLift:\""+obj[31]+"\",");
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
		ConfigFile configFile=Config.getInstance().configFile;
		String user="";
		String password="";
		String deviceName="";
		String startDate=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String endDate=startDate;
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
					deviceName=jsonObject.getString("deviceName");
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
					+ " and t.acqTime between to_date("+startDate+",'yyyy-mm-dd hh24:mi:ss') and to_date("+endDate+",'yyyy-mm-dd hh24:mi:ss')";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t2.deviceName='" + deviceName + "' ";
			} 
			sql+=" order by t.acqTime";
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
				result_json.append("\"deviceName\":\"" + obj[1] + "\",");
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
				
				result_json.append("downStrokeIMax:\""+obj[21]+"\",");
				result_json.append("upStrokeIMax:\""+obj[22]+"\",");
				result_json.append("iDegreeBalance:\""+obj[23]+"\",");
				result_json.append("downStrokeWattMax:\""+obj[24]+"\",");
				result_json.append("upStrokeWattMax:\""+obj[25]+"\",");     
				result_json.append("wattDegreeBalance:\""+obj[26]+"\",");
				result_json.append("deltaRadius:\""+obj[27]+"\",");
				
				result_json.append("WellDownSystemEfficiency:\""+obj[28]+"\",");
				result_json.append("SurfaceSystemEfficiency:\""+obj[29]+"\",");
				result_json.append("SystemEfficiency:\""+obj[30]+"\",");
				result_json.append("EnergyPer100mLift:\""+obj[31]+"\",");
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
					deviceName=jsonObject.getString("deviceName");
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
				deviceName=jsonObject.getString("deviceName");
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
		StringBuffer devices= new StringBuffer();
		int liftingType=1;
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
						devices.append(""+jsonArray.getString(i)+",");
					}
					if(devices.toString().endsWith(",")){
						devices.deleteCharAt(devices.length() - 1);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		json=this.getDeviceInfoList(user,password,devices.toString());
		return json;
	}
	
	
	@SuppressWarnings("rawtypes")
	public String getDeviceInfoList(String user,String password,String devices) {
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

			if (StringManagerUtils.isNotNull(devices)) {
				sql += " and t.devicename in (" + devices+ ")";
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
