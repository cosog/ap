package com.cosog.service.realTimeMonitoring;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.RPCProductionData;
import com.cosog.model.calculate.UserInfo;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.gridmodel.WellGridPanelData;
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

import net.sf.json.JSONObject;
import oracle.sql.CLOB;
import redis.clients.jedis.Jedis;

@Service("realTimeMonitoringService")
public class RealTimeMonitoringService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	
	public String getRealTimeMonitoringFESDiagramResultStatData(String orgId,String deviceType,String commStatusStatValue,String deviceTypeStatValue) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Jedis jedis = null;
		AlarmShowStyle alarmShowStyle=null;
		List<byte[]> deviceInfoByteList=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
				}
				alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
				
				if(!jedis.exists("DeviceInfo".getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				
				if(!jedis.exists("RPCWorkType".getBytes())){
					MemoryDataManagerTask.loadRPCWorkType();
				}
				deviceInfoByteList =jedis.hvals("DeviceInfo".getBytes());
			}catch(Exception e){
				e.printStackTrace();
			}
			String columns = "["
					+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50,children:[] },"
					+ "{ \"header\":\"名称\",\"dataIndex\":\"item\",children:[] },"
					+ "{ \"header\":\"变量\",\"dataIndex\":\"count\",children:[] }"
					+ "]";
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			
			int totalCount=0;
			if(jedis==null){
				String tableName="tbl_rpcacqdata_latest";
				String deviceTableName="viw_device";
				
				String sql="select decode(t2.resultcode,0,'无数据',null,'无数据',t3.resultname) as resultname,t2.resultcode,count(1) from "+deviceTableName+" t "
						+ " left outer join "+tableName+" t2 on  t2.deviceid=t.id"
						+ " left outer join tbl_rpc_worktype t3 on  t2.resultcode=t3.resultcode"
						+ " where t.orgid in("+orgId+") ";
				if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
					sql+=" and t.devicetypename='"+deviceTypeStatValue+"'";
				}
				if(StringManagerUtils.isNotNull(commStatusStatValue)){
					sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
				}
				sql+=" group by t3.resultname,t2.resultcode "
						+ " order by t2.resultcode";
				
				List<?> list = this.findCallSql(sql);
				result_json.append("\"totalCount\":"+totalCount+",");
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
			}else{
				if(deviceInfoByteList!=null){
					Map<Integer,Integer> totalMap=new TreeMap<Integer,Integer>();
					for(int i=0;i<deviceInfoByteList.size();i++){
						Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
						if (obj instanceof DeviceInfo) {
							DeviceInfo deviceInfo=(DeviceInfo)obj;
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
					}
					
					int index=1;
					result_json.append("\"totalCount\":"+totalMap.size()+",");
					result_json.append("\"totalRoot\":[");
					for(Integer key:totalMap.keySet()){
						String item="无数据";
						if(jedis.hexists("RPCWorkType".getBytes(), (key+"").getBytes())){
							WorkType workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkType".getBytes(), (key+"").getBytes()));
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
			
			result_json.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle));
			result_json.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getRealTimeMonitoringCommStatusStatData(String orgId,String deviceType,String deviceTypeStatValue) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Jedis jedis = null;
		AlarmShowStyle alarmShowStyle=null;
		List<byte[]> deviceInfoByteList=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
				}
				alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
				

				if(!jedis.exists("DeviceInfo".getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				deviceInfoByteList =jedis.hvals("DeviceInfo".getBytes());
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String columns = "["
					+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50,children:[] },"
					+ "{ \"header\":\"名称\",\"dataIndex\":\"item\",children:[] },"
					+ "{ \"header\":\"变量\",\"dataIndex\":\"count\",children:[] }"
					+ "]";
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"totalCount\":3,");
			int total=0,online=0,goOnline=0,offline=0;
			if(jedis==null){
				String tableName="tbl_acqdata_latest";
				String deviceTableName="viw_device";
				
				String sql="select t2.commstatus,count(1) from "+deviceTableName+" t "
						+ " left outer join "+tableName+" t2 on  t2.deviceid=t.id "
						+ " where t.orgid in("+orgId+") ";
				if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
					sql+=" and t.devicetypename='"+deviceTypeStatValue+"'";
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
				if(deviceInfoByteList!=null){
					for(int i=0;i<deviceInfoByteList.size();i++){
						int commStatus=0;
						Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
						if (obj instanceof DeviceInfo) {
							DeviceInfo deviceInfo=(DeviceInfo)obj;
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
			}
			
			total=online+goOnline+offline;
			result_json.append("\"totalRoot\":[");
			result_json.append("{\"id\":1,");
			result_json.append("\"item\":\"全部\",");
			result_json.append("\"itemCode\":\"all\",");
			result_json.append("\"count\":"+total+"},");
			
			result_json.append("{\"id\":2,");
			result_json.append("\"item\":\"在线\",");
			result_json.append("\"itemCode\":\"online\",");
			result_json.append("\"count\":"+online+"},");
			
			result_json.append("{\"id\":3,");
			result_json.append("\"item\":\"上线\",");
			result_json.append("\"itemCode\":\"goOnline\",");
			result_json.append("\"count\":"+goOnline+"},");
			
			result_json.append("{\"id\":4,");
			result_json.append("\"item\":\"离线\",");
			result_json.append("\"itemCode\":\"offline\",");
			result_json.append("\"count\":"+offline+"}");
			result_json.append("]");
			result_json.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle));
			result_json.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getRealTimeMonitoringRunStatusStatData(String orgId,String deviceType,String deviceTypeStatValue) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Jedis jedis = null;
		AlarmShowStyle alarmShowStyle=null;
		List<byte[]> deviceInfoByteList=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
				}
				alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
				

				if(!jedis.exists("DeviceInfo".getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				deviceInfoByteList =jedis.hvals("DeviceInfo".getBytes());
			
			}catch(Exception e){
				e.printStackTrace();
			}
			String columns = "["
					+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50,children:[] },"
					+ "{ \"header\":\"名称\",\"dataIndex\":\"item\",children:[] },"
					+ "{ \"header\":\"变量\",\"dataIndex\":\"count\",children:[] }"
					+ "]";
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"totalCount\":4,");
			int total=0,run=0,stop=0,noData=0,offline=0,goOnline=0;
			if(jedis==null){
				String tableName="tbl_acqdata_latest";
				String deviceTableName="viw_device";
				
				String sql="select decode(t2.commstatus,0,-1,2,-2,decode(t2.runstatus,null,2,t2.runstatus)) as runstatus,count(1) from "+deviceTableName+" t "
						+ " left outer join "+tableName+" t2 on  t2.deviceid=t.id "
						+ " where t.orgid in("+orgId+") ";
				if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
					sql+=" and t.devicetypename='"+deviceTypeStatValue+"'";
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
				if(deviceInfoByteList!=null){
					for(int i=0;i<deviceInfoByteList.size();i++){
						int commStatus=0;
						int runStatus=0;

						Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
						if (obj instanceof DeviceInfo) {
							DeviceInfo deviceInfo=(DeviceInfo)obj;
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
			}
			
			total=run+stop+noData+offline;
			result_json.append("\"totalRoot\":[");
			result_json.append("{\"id\":1,");
			result_json.append("\"item\":\"全部\",");
			result_json.append("\"itemCode\":\"all\",");
			result_json.append("\"count\":"+total+"},");
			
			result_json.append("{\"id\":2,");
			result_json.append("\"item\":\"运行\",");
			result_json.append("\"itemCode\":\"run\",");
			result_json.append("\"count\":"+run+"},");
			
			result_json.append("{\"id\":3,");
			result_json.append("\"item\":\"停抽\",");
			result_json.append("\"itemCode\":\"stop\",");
			result_json.append("\"count\":"+stop+"},");
			
			result_json.append("{\"id\":4,");
			result_json.append("\"item\":\"无数据\",");
			result_json.append("\"itemCode\":\"noData\",");
			result_json.append("\"count\":"+noData+"},");
			
			result_json.append("{\"id\":5,");
			result_json.append("\"item\":\"上线\",");
			result_json.append("\"itemCode\":\"goOnline\",");
			result_json.append("\"count\":"+goOnline+"},");
			
			result_json.append("{\"id\":6,");
			result_json.append("\"item\":\"离线\",");
			result_json.append("\"itemCode\":\"offline\",");
			result_json.append("\"count\":"+offline+"}");
			result_json.append("]");
			result_json.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle));
			result_json.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getRealTimeMonitoringDeviceTypeStatData(String orgId,String deviceType,String commStatusStatValue) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Jedis jedis = null;
		AlarmShowStyle alarmShowStyle=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
				}
				alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String tableName="tbl_acqdata_latest";
			String deviceTableName="viw_device";
			
			String sql="select t.devicetypename,t.devicetype,count(1) from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t.id=t2.deviceid "
					+ " where t.orgid in("+orgId+") ";
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
			}
			sql+=" group by t.devicetypename,t.devicetype";
			sql+=" order by t.devicetype";
			
			List<?> list = this.findCallSql(sql);
			String columns = "["
					+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50,children:[] },"
					+ "{ \"header\":\"名称\",\"dataIndex\":\"item\",children:[] },"
					+ "{ \"header\":\"变量\",\"dataIndex\":\"count\",children:[] }"
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
			if(jedis!=null){
				jedis.close();
			}
		}
		
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public int getDeviceRealTimeOverviewDataPage(String orgId,String deviceId,String deviceName,String deviceType,String FESdiagramResultStatValue,String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,String limit){
		int dataPage=1;
		try{
			String tableName="tbl_acqdata_latest";
			String deviceTableName="tbl_device";
			String calTableName="tbl_rpcacqdata_latest";
			
			String sql="select t.id from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceid=t.id"
					+ " left outer join "+calTableName+" t3 on t3.deviceid=t.id"
					+ " left outer join tbl_rpc_worktype t4 on t4.resultcode=t3.resultcode "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNum(deviceType)){
				sql+= " and t.devicetype="+deviceType;
			}else{
				sql+= " and t.devicetype in ("+deviceType+")";
			}
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+=" and decode(t3.resultcode,0,'无数据',null,'无数据',t4.resultName)='"+FESdiagramResultStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停抽','无数据'))='"+runStatusStatValue+"'";
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
	
	public String getDeviceRealTimeOverview(String orgId,String deviceName,String deviceType,String FESdiagramResultStatValue,String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,Page pager) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		ConfigFile configFile=Config.getInstance().configFile;
		int dataSaveMode=1;
		Jedis jedis=null;
		AlarmShowStyle alarmShowStyle=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("DeviceInfo".getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
				}
				alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
				
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
			}catch(Exception e){
				e.printStackTrace();
			}
			
			Map<String, Object> dataModelMap=DataModelMap.getMapObject();
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
			Map<String,DataMapping> loadProtocolMappingColumnMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumn");
			
			String tableName="tbl_acqdata_latest";
			String deviceTableName="tbl_device";
			String calTableName="tbl_rpcacqdata_latest";
			String ddicName="realTimeMonitoring_Overview";
			DataDictionary ddic = null;
			List<String> ddicColumnsList=new ArrayList<String>();
			
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			String columns = ddic.getTableHeader();
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+tableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			
//			String prodCol="liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,"
//					+ "liquidVolumetricProduction_L,oilVolumetricProduction_L,waterVolumetricProduction_L,"
//					+ "availablePlungerStrokeProd_v,pumpClearanceleakProd_v,tvleakVolumetricProduction,svleakVolumetricProduction,gasInfluenceProd_v,";
//			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
//				prodCol="liquidWeightProduction,oilWeightProduction,waterWeightProduction,"
//						+ "liquidWeightProduction_L,oilWeightProduction_L,waterWeightProduction_L,"
//						+ "availablePlungerStrokeProd_w,pumpClearanceleakProd_w,tvleakWeightProduction,svleakWeightProduction,gasInfluenceProd_w,";
//			}
			
			String sql="select t.id,t.devicename,"//0~1
					+ "t.videourl1,t.videokeyid1,t.videourl2,t.videokeyid2,"//2~5
					+ "c1.name as devicetypename,"//6
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"//7
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"//8~9
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"//10~12
					+ "decode(t2.runstatus,null,2,t2.runstatus),decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停抽','无数据')) as runStatusName,"//13~14
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"//15~17
					+ "t.calculateType";//18
//					+ "t2.resultcode,decode(t2.resultcode,0,'无数据',null,'无数据',t3.resultName) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"//18~20
//					+ "t2.TheoreticalProduction,"//21
//					+ prodCol+""//22~32
//					+ "t2.FMax,t2.FMin,"//33~34
//					+ "t2.Stroke,t2.SPM,"//35~36
//					+ "t2.fullnessCoefficient,t2.plungerStroke,t2.availablePlungerStroke,"//37~39
//					+ "t2.UpperLoadLine,t2.LowerLoadLine,"//40~41
//					+ "t2.averageWatt,t2.polishrodPower,t2.waterPower,"//42~44
//					+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"//45
//					+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"//46
//					+ "t2.systemEfficiency*100 as systemEfficiency,t2.energyper100mlift,t2.Area,"//47~49
//					+ "t2.RodFlexLength,t2.TubingFlexLength,t2.InertiaLength,"//50~52
//					+ "t2.PumpEff1*100 as PumpEff1,"//53
//					+ "t2.pumpEff2*100 as pumpEff2,"//54
//					+ "t2.pumpEff3*100 as pumpEff3,"//55
//					+ "t2.pumpEff4*100 as pumpEff4,"//56
//					+ "t2.pumpEff*100 as pumpEff,"//57
//					+ "t2.UpStrokeIMax,t2.DownStrokeIMax,t2.iDegreeBalance,"//58~60
//					+ "t2.UpStrokeWattMax,t2.DownStrokeWattMax,t2.wattDegreeBalance,"//61~63
//					+ "t2.deltaradius*100 as deltaradius,"//64
//					+ "t2.levelDifferenceValue,t2.calcProducingfluidLevel,"//65~66
//					+ "t2.submergence,"//67
//					+ "t2.todayKWattH,"//68
//					+ "t2.PumpIntakeP,t2.PumpIntakeT,t2.PumpIntakeGOL,t2.PumpIntakeVisl,t2.PumpIntakeBo,"//69~73
//					+ "t2.PumpOutletP,t2.PumpOutletT,t2.PumpOutletGOL,t2.PumpOutletVisl,t2.PumpOutletBo,"//74~78
//					+ "t2.productiondata";//79
			String[] ddicColumns=ddic.getSql().split(",");
			for(int i=0;i<ddicColumns.length;i++){
				if(StringManagerUtils.dataMappingKeyExistOrNot(loadProtocolMappingColumnMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
					ddicColumnsList.add(ddicColumns[i]);
				}
			}
			for(int i=0;i<ddicColumnsList.size();i++){
				sql+=",t2."+ddicColumnsList.get(i);
			}
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceid=t.id"
					+ " left outer join "+calTableName+" t3 on t3.deviceid=t.id"
					+ " left outer join tbl_rpc_worktype t4 on t4.resultcode=t3.resultcode "
					+ " left outer join tbl_devicetypeinfo c1 on c1.id=t.devicetype "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNum(deviceType)){
				sql+= " and t.devicetype="+deviceType;
			}else{
				sql+= " and t.devicetype in ("+deviceType+")";
			}
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+=" and decode(t3.resultcode,0,'无数据',null,'无数据',t4.resultName)='"+FESdiagramResultStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停抽','无数据'))='"+runStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.devicename";
			
			int maxvalue=pager.getLimit()+pager.getStart();
			String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
			
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(finalSql);
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"totalCount\":"+totals+",");
			result_json.append("\"totalRoot\":[");
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				StringBuffer alarmInfo = new StringBuffer();
				String deviceId=obj[0]+"";
				String commStatusName=obj[9]+"";
				String runStatusName=obj[14]+"";
//				String resultCode=obj[18]+"";
//				String productionDataStr=obj[79]+"";
				
//				String tubingPressure="",casingPressure="",wellHeadTemperature="",waterCut="",weightWaterCut="",
//						productionGasOilRatio="",producingfluidLevel="",levelCorrectValue="",
//						pumpSettingDepth="",pumpBoreDiameter="";
						
//				if(StringManagerUtils.isNotNull(productionDataStr)){
//					type = new TypeToken<RPCProductionData>() {}.getType();
//					RPCProductionData productionData=gson.fromJson(productionDataStr, type);
//					if(productionData.getProduction()!=null){
//						tubingPressure=productionData.getProduction().getTubingPressure()+"";
//						casingPressure=productionData.getProduction().getCasingPressure()+"";
//						wellHeadTemperature=productionData.getProduction().getWellHeadTemperature()+"";
//						waterCut=productionData.getProduction().getWaterCut()+"";
//						weightWaterCut=productionData.getProduction().getWeightWaterCut()+"";
//						productionGasOilRatio=productionData.getProduction().getProductionGasOilRatio()+"";
//						producingfluidLevel=productionData.getProduction().getProducingfluidLevel()+"";
//						pumpSettingDepth=productionData.getProduction().getPumpSettingDepth()+"";
//					}
//					if(productionData.getPump()!=null){
//						pumpBoreDiameter=productionData.getPump().getPumpBoreDiameter()*1000+"";
//					}
//					if(productionData.getManualIntervention()!=null){
//						levelCorrectValue=productionData.getManualIntervention().getLevelCorrectValue()+"";
//					}
//				}
				
				DeviceInfo deviceInfo=null;
				if(jedis!=null&&jedis.hexists("DeviceInfo".getBytes(), deviceId.getBytes())){
					deviceInfo=(DeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("DeviceInfo".getBytes(), deviceId.getBytes()));
				}
				String protocolName="";
				AcqInstanceOwnItem acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(deviceInfo.getInstanceCode());
				if(acqInstanceOwnItem!=null){
					protocolName=acqInstanceOwnItem.getProtocol();
				}
				DisplayInstanceOwnItem displayInstanceOwnItem=MemoryDataManagerTask.getDisplayInstanceOwnItemByCode(deviceInfo.getDisplayInstanceCode());
				AlarmInstanceOwnItem alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(deviceInfo.getAlarmInstanceCode());
				
				ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
				
				int commAlarmLevel=0,resultAlarmLevel=0,runAlarmLevel=0;
				if(alarmInstanceOwnItem!=null){
					for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
						if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
							commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
							runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}
					}
				}
				
				result_json.append("{\"id\":"+deviceId+",");
				result_json.append("\"deviceName\":\""+obj[1]+"\",");
				
				result_json.append("\"videoUrl1\":\""+obj[2]+"\",");
				result_json.append("\"videoKeyId1\":\""+obj[3]+"\",");
				result_json.append("\"videoUrl2\":\""+obj[4]+"\",");
				result_json.append("\"videoKeyId2\":\""+obj[5]+"\",");
				
				
				result_json.append("\"deviceTypeName\":\""+obj[6]+"\",");
				result_json.append("\"acqTime\":\""+obj[7]+"\",");
				result_json.append("\"commStatus\":"+obj[8]+",");
				result_json.append("\"commStatusName\":\""+commStatusName+"\",");
				result_json.append("\"commTime\":\""+obj[10]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[11]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
				result_json.append("\"commAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"runStatus\":"+obj[13]+",");
				result_json.append("\"runStatusName\":\""+runStatusName+"\",");
				result_json.append("\"runTime\":\""+obj[15]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[16]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[17])+"\",");
				result_json.append("\"runAlarmLevel\":"+runAlarmLevel+",");
				result_json.append("\"calculateType\":"+obj[18]+",");
//				result_json.append("\"resultCode\":\""+resultCode+"\",");
//				result_json.append("\"resultName\":\""+obj[19]+"\",");
//				result_json.append("\"optimizationSuggestion\":\""+obj[20]+"\",");
//				result_json.append("\"resultAlarmLevel\":"+resultAlarmLevel+",");
//				result_json.append("\"theoreticalProduction\":\""+obj[21]+"\",");
//				
//				result_json.append("\""+prodCol.split(",")[0]+"\":\""+obj[22]+"\",");
//				result_json.append("\""+prodCol.split(",")[1]+"\":\""+obj[23]+"\",");
//				result_json.append("\""+prodCol.split(",")[2]+"\":\""+obj[24]+"\",");
//				result_json.append("\"waterCut\":\""+waterCut+"\",");
//				result_json.append("\"weightWaterCut\":\""+weightWaterCut+"\",");
//				result_json.append("\""+prodCol.split(",")[3]+"\":\""+obj[25]+"\",");
//				result_json.append("\""+prodCol.split(",")[4]+"\":\""+obj[26]+"\",");
//				result_json.append("\""+prodCol.split(",")[5]+"\":\""+obj[27]+"\",");
//				result_json.append("\""+prodCol.split(",")[6]+"\":\""+obj[28]+"\",");
//				result_json.append("\""+prodCol.split(",")[7]+"\":\""+obj[29]+"\",");
//				result_json.append("\""+prodCol.split(",")[8]+"\":\""+obj[30]+"\",");
//				result_json.append("\""+prodCol.split(",")[9]+"\":\""+obj[31]+"\",");
//				result_json.append("\""+prodCol.split(",")[10]+"\":\""+obj[32]+"\",");
//				
//				result_json.append("\"FMax\":\""+obj[33]+"\",");
//				result_json.append("\"FMin\":\""+obj[34]+"\",");
//				
//				result_json.append("\"stroke\":\""+obj[35]+"\",");
//				result_json.append("\"SPM\":\""+obj[36]+"\",");
//				
//				result_json.append("\"fullnessCoefficient\":\""+obj[37]+"\",");
//				result_json.append("\"plungerStroke\":\""+obj[38]+"\",");
//				result_json.append("\"availablePlungerStroke\":\""+obj[39]+"\",");
//				
//				result_json.append("\"upperLoadLine\":\""+obj[40]+"\",");
//				result_json.append("\"lowerLoadLine\":\""+obj[41]+"\",");
//				
//				result_json.append("\"averageWatt\":\""+obj[42]+"\",");
//				result_json.append("\"polishrodPower\":\""+obj[43]+"\",");
//				result_json.append("\"waterPower\":\""+obj[44]+"\",");
//				
//				result_json.append("\"surfaceSystemEfficiency\":\""+obj[45]+"\",");
//				result_json.append("\"welldownSystemEfficiency\":\""+obj[46]+"\",");
//				result_json.append("\"systemEfficiency\":\""+obj[47]+"\",");
//				result_json.append("\"energyper100mlift\":\""+obj[48]+"\",");
//				result_json.append("\"area\":\""+obj[49]+"\",");
//				
//				result_json.append("\"rodFlexLength\":\""+obj[50]+"\",");
//				result_json.append("\"tubingFlexLength\":\""+obj[51]+"\",");
//				result_json.append("\"inertiaLength\":\""+obj[52]+"\",");
//				
//				result_json.append("\"pumpEff1\":\""+obj[53]+"\",");
//				result_json.append("\"pumpEff2\":\""+obj[54]+"\",");
//				result_json.append("\"pumpEff3\":\""+obj[55]+"\",");
//				result_json.append("\"pumpEff4\":\""+obj[56]+"\",");
//				result_json.append("\"pumpEff\":\""+obj[57]+"\",");
//				
//				result_json.append("\"upStrokeIMax\":\""+obj[58]+"\",");
//				result_json.append("\"downStrokeIMax\":\""+obj[59]+"\",");
//				result_json.append("\"iDegreeBalance\":\""+obj[60]+"\",");
//				
//				result_json.append("\"upStrokeWattMax\":\""+obj[61]+"\",");
//				result_json.append("\"downStrokeWattMax\":\""+obj[62]+"\",");
//				result_json.append("\"wattDegreeBalance\":\""+obj[63]+"\",");
//				
//				result_json.append("\"deltaradius\":\""+obj[64]+"\",");
//				
//				result_json.append("\"producingfluidLevel\":\""+producingfluidLevel+"\",");
//				result_json.append("\"levelCorrectValue\":\""+levelCorrectValue+"\",");
//				result_json.append("\"levelDifferenceValue\":\""+obj[65]+"\",");
//				result_json.append("\"calcProducingfluidLevel\":\""+obj[66]+"\",");
//				result_json.append("\"submergence\":\""+obj[67]+"\",");
//				
//				result_json.append("\"pumpSettingDepth\":\""+pumpSettingDepth+"\",");
//				result_json.append("\"pumpBoreDiameter\":\""+pumpBoreDiameter+"\",");
//				
//				result_json.append("\"todayKWattH\":\""+obj[68]+"\",");
//				
//				result_json.append("\"tubingPressure\":\""+tubingPressure+"\",");
//				result_json.append("\"casingPressure\":\""+casingPressure+"\",");
//				result_json.append("\"wellHeadTemperature\":\""+wellHeadTemperature+"\",");
//				result_json.append("\"productionGasOilRatio\":\""+productionGasOilRatio+"\",");
//				
//				result_json.append("\"pumpIntakeP\":\""+obj[69]+"\",");
//				result_json.append("\"pumpIntakeT\":\""+obj[70]+"\",");
//				result_json.append("\"pumpIntakeGOL\":\""+obj[71]+"\",");
//				result_json.append("\"pumpIntakeVisl\":\""+obj[72]+"\",");
//				result_json.append("\"pumpIntakeBo\":\""+obj[73]+"\",");
//				
//				result_json.append("\"pumpOutletP\":\""+obj[74]+"\",");
//				result_json.append("\"pumpOutletT\":\""+obj[75]+"\",");
//				result_json.append("\"pumpOutletGOL\":\""+obj[76]+"\",");
//				result_json.append("\"pumpOutletVisl\":\""+obj[77]+"\",");
//				result_json.append("\"pumpOutletBo\":\""+obj[78]+"\",");
				
				alarmInfo.append("[");
				//计算项报警判断
				if(alarmInstanceOwnItem!=null){
					for(int j=0;j<ddicColumns.length;j++){
						String column=ddicColumns[j].trim();
						String[] attr = column.split(" as ");
						if (attr.length > 1) {
							column=attr[attr.length-1];
						}else{
							if(column.indexOf(".") > 0){
								column = column.substring(column.indexOf(".") + 1);
							}
						}
						for(int k=0;k<alarmInstanceOwnItem.getItemList().size();k++){
							if(alarmInstanceOwnItem.getItemList().get(k).getType()==5&&column.equalsIgnoreCase(alarmInstanceOwnItem.getItemList().get(k).getItemCode())){
								alarmInfo.append("{\"item\":\""+alarmInstanceOwnItem.getItemList().get(k).getItemCode()+"\","
										+ "\"itemName\":\""+alarmInstanceOwnItem.getItemList().get(k).getItemName()+"\","
										+ "\"itemAddr\":\""+alarmInstanceOwnItem.getItemList().get(k).getItemAddr()+"\","
										+ "\"alarmType\":\""+alarmInstanceOwnItem.getItemList().get(k).getType()+"\","
										+ "\"upperLimit\":\""+alarmInstanceOwnItem.getItemList().get(k).getUpperLimit()+"\","
										+ "\"lowerLimit\":\""+alarmInstanceOwnItem.getItemList().get(k).getLowerLimit()+"\","
										+ "\"hystersis\":\""+alarmInstanceOwnItem.getItemList().get(k).getHystersis()+"\","
										+" \"alarmLevel\":"+alarmInstanceOwnItem.getItemList().get(k).getAlarmLevel()+"},");
								break;
							}
						}
					}
				}
				
				for(int j=0;j<ddicColumnsList.size();j++){
					String rawValue=obj[19+j]+"";
					String value=rawValue;
					ModbusProtocolConfig.Items item=null;
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col="";
							if(loadProtocolMappingColumnByTitleMap.containsKey(protocol.getItems().get(k).getTitle())){
								col=loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(k).getTitle()).getMappingColumn();
							}
							if(col!=null&&col.equalsIgnoreCase(ddicColumnsList.get(j))){
								item=protocol.getItems().get(k);
								if(protocol.getItems().get(k).getMeaning()!=null && protocol.getItems().get(k).getMeaning().size()>0){
									for(int l=0;l<protocol.getItems().get(k).getMeaning().size();l++){
										if(value.equals(protocol.getItems().get(k).getMeaning().get(l).getValue()+"")||StringManagerUtils.stringToFloat(value)==protocol.getItems().get(k).getMeaning().get(l).getValue()){
											value=protocol.getItems().get(k).getMeaning().get(l).getMeaning();
											break;
										}
									}
								}
								break;
							}
						}
					}
					//判断报警
					if(item!=null&&alarmInstanceOwnItem!=null){
						for(int k=0;k<alarmInstanceOwnItem.getItemList().size();k++){
							int alarmType=alarmInstanceOwnItem.getItemList().get(k).getType();
							if(alarmType<=2&&item.getTitle().equalsIgnoreCase(alarmInstanceOwnItem.getItemList().get(k).getItemName()) && item.getAddr()==alarmInstanceOwnItem.getItemList().get(k).getItemAddr()){
								if(alarmType==2){//数据量报警
									alarmInfo.append("{\"item\":\""+ddicColumnsList.get(j).replaceAll(" ", "")+"\","
											+ "\"itemName\":\""+alarmInstanceOwnItem.getItemList().get(k).getItemName()+"\","
											+ "\"itemAddr\":\""+alarmInstanceOwnItem.getItemList().get(k).getItemAddr()+"\","
											+ "\"alarmType\":\""+alarmType+"\","
											+ "\"upperLimit\":\""+alarmInstanceOwnItem.getItemList().get(k).getUpperLimit()+"\","
											+ "\"lowerLimit\":\""+alarmInstanceOwnItem.getItemList().get(k).getLowerLimit()+"\","
											+ "\"hystersis\":\""+alarmInstanceOwnItem.getItemList().get(k).getHystersis()+"\","
											+" \"alarmLevel\":"+alarmInstanceOwnItem.getItemList().get(k).getAlarmLevel()+"},");
									break;
								}else if(alarmType==1){//枚举量报警
									String alarmValueMeaning="";
									if(item.getMeaning()!=null && item.getMeaning().size()>0){
										for(int l=0;l<item.getMeaning().size();l++){
											if(alarmInstanceOwnItem.getItemList().get(k).getValue()==item.getMeaning().get(l).getValue()){
												alarmValueMeaning=item.getMeaning().get(l).getMeaning();
												break;
											}
										}
									}
										
									alarmInfo.append("{\"item\":\""+ddicColumnsList.get(j).replaceAll(" ", "")+"\","
											+ "\"itemName\":\""+alarmInstanceOwnItem.getItemList().get(k).getItemName()+"\","
											+ "\"itemAddr\":\""+alarmInstanceOwnItem.getItemList().get(k).getItemAddr()+"\","
											+ "\"alarmType\":\""+alarmType+"\","
											+ "\"alarmValue\":\""+alarmInstanceOwnItem.getItemList().get(k).getValue()+"\","
											+ "\"alarmValueMeaning\":\""+alarmValueMeaning+"\","
											+ "\"alarmLevel\":"+alarmInstanceOwnItem.getItemList().get(k).getAlarmLevel()+"},");
								}else if(alarmType==0){//开关量报警
									
								}
								
							}
						}
					}
					result_json.append("\""+ddicColumnsList.get(j).replaceAll(" ", "")+"\":\""+value+"\",");
				}
				if(result_json.toString().endsWith(",")){
					result_json.deleteCharAt(result_json.length() - 1);
				}
				if(alarmInfo.toString().endsWith(",")){
					alarmInfo.deleteCharAt(alarmInfo.length() - 1);
				}
				alarmInfo.append("]");
				result_json.append(",\"alarmInfo\":"+alarmInfo+"");
				result_json.append("},");
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]");
			result_json.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle)+"}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public boolean exportDeviceRealTimeOverviewData(User user,HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceName,String deviceType,
			String FESdiagramResultStatValue,String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,Page pager){
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int dataSaveMode=1;
		Jedis jedis=null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("DeviceInfo".getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			
			List<Object> headRow = new ArrayList<>();
			for(int i=0;i<heads.length;i++){
				headRow.add(heads[i]);
			}
		    List<List<Object>> sheetDataList = new ArrayList<>();
		    sheetDataList.add(headRow);
		    
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			
			Map<String, Object> dataModelMap=DataModelMap.getMapObject();
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
			Map<String,DataMapping> loadProtocolMappingColumnMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumn");
			
			String tableName="tbl_acqdata_latest";
			String deviceTableName="tbl_device";
			String calTableName="tbl_rpcacqdata_latest";
			String ddicName="realTimeMonitoring_Overview";
			DataDictionary ddic = null;
			List<String> ddicColumnsList=new ArrayList<String>();
			
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+tableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			
			String sql="select t.id,t.devicename,"//0~1
					+ "t.videourl1,t.videokeyid1,t.videourl2,t.videokeyid2,"//2~5
					+ "c1.itemname as devicetypename,"//6
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"//7
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"//8~9
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"//10~12
					+ "decode(t2.runstatus,null,2,t2.runstatus),decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停抽','无数据')) as runStatusName,"//13~14
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange"//15~17
					+"";
			
			String[] ddicColumns=ddic.getSql().split(",");
			for(int i=0;i<ddicColumns.length;i++){
				if(StringManagerUtils.dataMappingKeyExistOrNot(loadProtocolMappingColumnMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
					ddicColumnsList.add(ddicColumns[i]);
				}
			}
			for(int i=0;i<ddicColumnsList.size();i++){
				sql+=",t2."+ddicColumnsList.get(i);
			}
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceid=t.id"
					+ " left outer join "+calTableName+" t3 on t3.deviceid=t.id"
					+ " left outer join tbl_rpc_worktype t4 on t4.resultcode=t3.resultcode "
					+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNum(deviceType)){
				sql+= " and t.devicetype="+deviceType;
			}else{
				sql+= " and t.devicetype in ("+deviceType+")";
			}
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+=" and decode(t3.resultcode,0,'无数据',null,'无数据',t4.resultName)='"+FESdiagramResultStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停抽','无数据'))='"+runStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.devicename";
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			List<?> list = this.findCallSql(finalSql);
			List<Object> record=null;
			JSONObject jsonObject=null;
			Object[] obj=null;
			for(int i=0;i<list.size();i++){
				obj=(Object[]) list.get(i);
				result_json = new StringBuffer();
				record = new ArrayList<>();
				
				String deviceId=obj[0]+"";
				
				
				DeviceInfo deviceInfo=null;
				if(jedis.hexists("DeviceInfo".getBytes(), deviceId.getBytes())){
					deviceInfo=(DeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("DeviceInfo".getBytes(), deviceId.getBytes()));
				}
				String protocolName="";
				AcqInstanceOwnItem acqInstanceOwnItem=null;
				if(jedis!=null&&deviceInfo!=null&&jedis.hexists("AcqInstanceOwnItem".getBytes(), deviceInfo.getInstanceCode().getBytes())){
					acqInstanceOwnItem=(AcqInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AcqInstanceOwnItem".getBytes(), deviceInfo.getInstanceCode().getBytes()));
					protocolName=acqInstanceOwnItem.getProtocol();
				}
				
				ModbusProtocolConfig.Protocol protocol=null;
				for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
					if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
						protocol=modbusProtocolConfig.getProtocol().get(j);
						break;
					}
				}
				
				result_json.append("{\"id\":"+(i+1)+",");
				result_json.append("\"deviceName\":\""+obj[1]+"\",");
				
				result_json.append("\"videoUrl1\":\""+obj[2]+"\",");
				result_json.append("\"videoKeyId1\":\""+obj[3]+"\",");
				result_json.append("\"videoUrl2\":\""+obj[4]+"\",");
				result_json.append("\"videoKeyId2\":\""+obj[5]+"\",");
				
				
				result_json.append("\"deviceTypeName\":\""+obj[6]+"\",");
				result_json.append("\"acqTime\":\""+obj[7]+"\",");
				result_json.append("\"commStatus\":"+obj[8]+",");
				result_json.append("\"commTime\":\""+obj[10]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[11]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
				result_json.append("\"runStatus\":"+obj[13]+",");
				result_json.append("\"runTime\":\""+obj[15]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[16]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[17])+"\"");
				
				for(int j=0;j<ddicColumnsList.size();j++){
					String value=obj[18+j]+"";
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col="";
							if(loadProtocolMappingColumnByTitleMap.containsKey(protocol.getItems().get(k).getTitle())){
								col=loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(k).getTitle()).getMappingColumn();
							}
							if(col!=null&&col.equalsIgnoreCase(ddicColumnsList.get(j))){
								if(protocol.getItems().get(k).getMeaning()!=null && protocol.getItems().get(k).getMeaning().size()>0){
									for(int l=0;l<protocol.getItems().get(k).getMeaning().size();l++){
										if(value.equals(protocol.getItems().get(k).getMeaning().get(l).getValue()+"")||StringManagerUtils.stringToFloat(value)==protocol.getItems().get(k).getMeaning().get(l).getValue()){
											value=protocol.getItems().get(k).getMeaning().get(l).getMeaning();
											break;
										}
									}
								}
								break;
							}
						}
					}
					result_json.append(",\""+ddicColumnsList.get(j).replaceAll(" ", "")+"\":\""+value+"\"");
				}
				result_json.append("}");
				jsonObject = JSONObject.fromObject(result_json.toString().replaceAll("null", ""));
				for (int j = 0; j < columns.length; j++) {
					if(jsonObject.has(columns[j])){
						record.add(jsonObject.getString(columns[j]));
					}else{
						record.add("");
					}
				}
				sheetDataList.add(record);
			}
			ExcelUtils.export(response,fileName,title, sheetDataList);
			if(user!=null){
		    	try {
					saveSystemLog(user,4,"导出文件:"+title);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return true;
	}
	
	public int getPCPDeviceRealTimeOverviewDataPage(String orgId,String deviceId,String deviceName,String deviceType,String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,String limit){
		int dataPage=1;
		try{
			String tableName="tbl_pcpacqdata_latest";
			String deviceTableName="tbl_pcpdevice";
			
			String sql="select t.id from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceid=t.id"
					+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNum(deviceType)){
				sql+= " and t.devicetype="+deviceType;
			}else{
				sql+= " and t.devicetype in ("+deviceType+")";
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'离线',2,'上线',decode(t2.runstatus,1,'运行',0,'停抽','无数据'))='"+runStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.devicename";
			dataPage=this.getDataPage(StringManagerUtils.stringToInteger(deviceId), sql, StringManagerUtils.stringToInteger(limit));
		}catch(Exception e){
			dataPage=1;
		}
		return dataPage;
	}

	public String getDeviceRealTimeMonitoringData(String deviceId,String deviceName,String deviceType,String calculateType,int userNo) throws IOException, SQLException{
		int items=3;
		StringBuffer result_json = new StringBuffer();
		StringBuffer info_json = new StringBuffer();
		int dataSaveMode=1;
		Jedis jedis = null;
		AlarmShowStyle alarmShowStyle=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		AlarmInstanceOwnItem alarmInstanceOwnItem=null;
		List<byte[]> calItemSet=null;
		List<byte[]> inputItemSet=null;
		UserInfo userInfo=null;
		String tableName="tbl_acqdata_latest";
		String calAndInputTableName="";
		String deviceTableName="tbl_device";
		String deviceInfoKey="DeviceInfo";
		String rpcCalItemsKey="rpcCalItemList";
		String rpcInputItemsKey="rpcInputItemList";
		String pcpCalItemsKey="pcpCalItemList";
		String pcpInputItemsKey="pcpInputItemList";
		String displayInstanceCode="";
		String alarmInstanceCode="";
		
		DeviceInfo deviceInfo=null;
		
		if(StringManagerUtils.stringToInteger(calculateType)==1){
			calAndInputTableName="tbl_rpcacqdata_latest";
		}else if(StringManagerUtils.stringToInteger(calculateType)==2){
			calAndInputTableName="tbl_pcpacqdata_latest";
		}
		
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
		Map<String,DataMapping> loadProtocolMappingColumnMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumn");
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();

				if(!jedis.exists(deviceInfoKey.getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				if(jedis.hexists(deviceInfoKey.getBytes(), deviceId.getBytes())){
					deviceInfo=(DeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget(deviceInfoKey.getBytes(), deviceId.getBytes()));
					displayInstanceCode=deviceInfo.getDisplayInstanceCode();
					alarmInstanceCode=deviceInfo.getAlarmInstanceCode();
				}
			
				
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
				}
				alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
				
				if(!jedis.exists("DisplayInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadDisplayInstanceOwnItemById("","update");
				}
				
				if(StringManagerUtils.isNotNull(displayInstanceCode)&&jedis.hexists("DisplayInstanceOwnItem".getBytes(),displayInstanceCode.getBytes())){
					displayInstanceOwnItem=(DisplayInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("DisplayInstanceOwnItem".getBytes(), displayInstanceCode.getBytes()));
				}
				
				if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
				}
				
				if(StringManagerUtils.isNotNull(alarmInstanceCode)&&jedis.hexists("AlarmInstanceOwnItem".getBytes(),alarmInstanceCode.getBytes())){
					alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(),alarmInstanceCode.getBytes()));
				}
				
				if(StringManagerUtils.stringToInteger(calculateType)==1){
					if(!jedis.exists(rpcCalItemsKey.getBytes())){
						MemoryDataManagerTask.loadRPCCalculateItem();
					}
					calItemSet= jedis.zrange(rpcCalItemsKey.getBytes(), 0, -1);
					
					if(!jedis.exists(rpcInputItemsKey.getBytes())){
						MemoryDataManagerTask.loadRPCInputItem();
					}
					inputItemSet= jedis.zrange(rpcInputItemsKey.getBytes(), 0, -1);
				}
				if(StringManagerUtils.stringToInteger(calculateType)==2){
					if(!jedis.exists(pcpCalItemsKey.getBytes())){
						MemoryDataManagerTask.loadPCPCalculateItem();
					}
					calItemSet= jedis.zrange(pcpCalItemsKey.getBytes(), 0, -1);
					
					if(!jedis.exists(pcpInputItemsKey.getBytes())){
						MemoryDataManagerTask.loadPCPInputItem();
					}
					inputItemSet= jedis.zrange(pcpInputItemsKey.getBytes(), 0, -1);
				}
				
				if(!jedis.exists("UserInfo".getBytes())){
					MemoryDataManagerTask.loadUserInfo(null,0,"update");
				}
				
				if(jedis.hexists("UserInfo".getBytes(), (userNo+"").getBytes())){
					userInfo=(UserInfo) SerializeObjectUnils.unserizlize(jedis.hget("UserInfo".getBytes(), (userNo+"").getBytes()));
				}
				
				if(!jedis.exists("RPCWorkType".getBytes())){
					MemoryDataManagerTask.loadRPCWorkType();
				}
			}catch(Exception e){
				e.printStackTrace();
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
				String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+tableName+"') order by t.COLUMN_ID";
				List<String> tableColumnsList=new ArrayList<String>();
				List<?> columnList = this.findCallSql(columnSql);
				for(int i=0;i<columnList.size();i++){
					tableColumnsList.add(columnList.get(i).toString());
				}
				
				String protocolCode=displayInstanceOwnItem.getProtocol();
				ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
				
				
				
				ModbusProtocolConfig.Protocol protocol=null;
				for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
					if(protocolCode.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
						protocol=modbusProtocolConfig.getProtocol().get(j);
						break;
					}
				}
				
				if(protocol!=null){
					List<ModbusProtocolConfig.Items> protocolItems=new ArrayList<ModbusProtocolConfig.Items>();
					List<CalItem> calItemList=new ArrayList<CalItem>();
					List<CalItem> inputItemList=new ArrayList<CalItem>();
					List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
					WorkType workType=null;
					for(int j=0;j<protocol.getItems().size();j++){
						if((!"w".equalsIgnoreCase(protocol.getItems().get(j).getRWType())) 
								&& (StringManagerUtils.existDisplayItem(displayInstanceOwnItem.getItemList(), protocol.getItems().get(j).getTitle(), false))){
							for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
								if(displayInstanceOwnItem.getItemList().get(k).getType()==0 
										&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
										&& protocol.getItems().get(j).getTitle().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemName())
										&& StringManagerUtils.existOrNot(tableColumnsList,loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(j).getTitle()).getMappingColumn(), false)
										){
									protocolItems.add(protocol.getItems().get(j));
									break;
								}
							}
						}
					}
					
					//计算项
					if(calItemSet!=null){
						for(byte[] calItemByteArr:calItemSet){
							CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(calItemByteArr);
							if(StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), calItem.getCode(), false,0)){
								for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
									if(displayInstanceOwnItem.getItemList().get(k).getType()==1
											&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
											&& calItem.getCode().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode())){
										calItemList.add(calItem);
										break;
									}
								}
								
							}
						}
					}
					//录入项
					if(inputItemSet!=null){
						for(byte[] inputItemByteArr:inputItemSet){
							CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(inputItemByteArr);
							if(StringManagerUtils.existDisplayItemCode(displayInstanceOwnItem.getItemList(), calItem.getCode(), false,0)){
								for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
									if(displayInstanceOwnItem.getItemList().get(k).getType()==3
											&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
											&& calItem.getCode().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode())){
										inputItemList.add(calItem);
										break;
									}
								}
								
							}
						}
					}
					
					String sql="select t.id,t.devicename,to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'), t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,decode(t2.commstatus,1,0,100) as commAlarmLevel ";
					for(int j=0;j<protocolItems.size();j++){
						String col="";
						if(loadProtocolMappingColumnByTitleMap.containsKey(protocolItems.get(j).getTitle())){
							col=loadProtocolMappingColumnByTitleMap.get(protocolItems.get(j).getTitle()).getMappingColumn();
						}
						sql+=",t2."+col;
					}

					if(StringManagerUtils.stringToInteger(calculateType)>0){
						for(int i=0;i<calItemList.size();i++){
							String column=calItemList.get(i).getCode();
							if("resultName".equalsIgnoreCase(calItemList.get(i).getCode())){
								column="resultCode";
							}
							sql+=",t3."+column;
						}
						if(inputItemList.size()>0){
							sql+=",t3.productiondata";
						}
					}
					
					sql+= " from "+deviceTableName+" t "
							+ " left outer join "+tableName+" t2 on t2.deviceid=t.id";
					if(StringManagerUtils.isNotNull(calAndInputTableName)&&(calItemList.size()>0 || inputItemList.size()>0)){
						sql+=" left outer join "+calAndInputTableName+" t3 on t3.deviceid=t.id";
					}
					
					sql+= " where  t.id="+deviceId;
					List<?> list = this.findCallSql(sql);
					if(list.size()>0){
						int row=1;
						Object[] obj=(Object[]) list.get(0);
						if(inputItemList.size()>0){
							String productionData=(obj[obj.length-1]+"").replaceAll("null", "");
							Gson gson = new Gson();
							java.lang.reflect.Type type=null;
							if(StringManagerUtils.stringToInteger(calculateType)==1){
								type = new TypeToken<RPCCalculateRequestData>() {}.getType();
								RPCCalculateRequestData rpcProductionData=gson.fromJson(productionData, type);
								for(int i=0;i<inputItemList.size();i++){
									String columnName=inputItemList.get(i).getName();
									String rawColumnName=columnName;
									String value="";
									String rawValue=value;
									String addr="";
									String column=inputItemList.get(i).getCode();
									String columnDataType="";
									String resolutionMode="";
									String bitIndex="";
									String unit=inputItemList.get(i).getUnit();
									int sort=9999;
									for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
										if(column.equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
											sort=displayInstanceOwnItem.getItemList().get(l).getSort();
											break;
										}
									}
									
									if(rpcProductionData!=null){
										if("CrudeOilDensity".equalsIgnoreCase(column) && rpcProductionData.getFluidPVT()!=null ){
											value=rpcProductionData.getFluidPVT().getCrudeOilDensity()+"";
										}else if("WaterDensity".equalsIgnoreCase(column) && rpcProductionData.getFluidPVT()!=null ){
											value=rpcProductionData.getFluidPVT().getWaterDensity()+"";
										}else if("NaturalGasRelativeDensity".equalsIgnoreCase(column) && rpcProductionData.getFluidPVT()!=null ){
											value=rpcProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
										}else if("SaturationPressure".equalsIgnoreCase(column) && rpcProductionData.getFluidPVT()!=null ){
											value=rpcProductionData.getFluidPVT().getSaturationPressure()+"";
										}else if("ReservoirDepth".equalsIgnoreCase(column) && rpcProductionData.getReservoir()!=null ){
											value=rpcProductionData.getReservoir().getDepth()+"";
											if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
												columnName=columnName.replace("油层", "煤层");
											}
										}else if("ReservoirTemperature".equalsIgnoreCase(column) && rpcProductionData.getReservoir()!=null ){
											value=rpcProductionData.getReservoir().getTemperature()+"";
											if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
												columnName=columnName.replace("油层", "煤层");
											}
										}else if("TubingPressure".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
											value=rpcProductionData.getProduction().getTubingPressure()+"";
										}else if("CasingPressure".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
											value=rpcProductionData.getProduction().getCasingPressure()+"";
										}else if("WellHeadTemperature".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
											value=rpcProductionData.getProduction().getWellHeadTemperature()+"";
										}else if("WaterCut".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
											value=rpcProductionData.getProduction().getWaterCut()+"";
										}else if("ProductionGasOilRatio".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
											value=rpcProductionData.getProduction().getProductionGasOilRatio()+"";
										}else if("ProducingfluidLevel".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
											value=rpcProductionData.getProduction().getProducingfluidLevel()+"";
										}else if("PumpSettingDepth".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
											value=rpcProductionData.getProduction().getPumpSettingDepth()+"";
										}else if("PumpBoreDiameter".equalsIgnoreCase(column) && rpcProductionData.getPump()!=null ){
											value=rpcProductionData.getPump().getPumpBoreDiameter()*1000+"";
										}else if("LevelCorrectValue".equalsIgnoreCase(column) && rpcProductionData.getManualIntervention()!=null ){
											value=rpcProductionData.getManualIntervention().getLevelCorrectValue()+"";
										}
									}
									rawValue=value;
									rawColumnName=columnName;
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort);
									protocolItemResolutionDataList.add(protocolItemResolutionData);
								}
							}else if(StringManagerUtils.stringToInteger(calculateType)==2){
								type = new TypeToken<PCPCalculateRequestData>() {}.getType();
								PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
								for(int i=0;i<inputItemList.size();i++){
									String columnName=inputItemList.get(i).getName();
									String rawColumnName=columnName;
									String value="";
									String rawValue=value;
									String addr="";
									String column=inputItemList.get(i).getCode();
									String columnDataType="";
									String resolutionMode="";
									String bitIndex="";
									String unit=inputItemList.get(i).getUnit();
									int sort=9999;
									for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
										if(column.equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
											sort=displayInstanceOwnItem.getItemList().get(l).getSort();
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
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort);
									protocolItemResolutionDataList.add(protocolItemResolutionData);
								}
							}
						}
						
						for(int i=0;i<calItemList.size();i++){
							int index=i+6+protocolItems.size();
							if(index<obj.length){
								String columnName=calItemList.get(i).getName();
								String rawColumnName=columnName;
								String value=obj[i+6+protocolItems.size()]+"";
								if(obj[i+6+protocolItems.size()] instanceof CLOB || obj[i+6+protocolItems.size()] instanceof Clob){
									value=StringManagerUtils.CLOBObjectToString(obj[i+6+protocolItems.size()]);
								}
								String rawValue=value;
								String addr="";
								String column=calItemList.get(i).getCode();
								String columnDataType="";
								String resolutionMode="";
								String bitIndex="";
								String unit=calItemList.get(i).getUnit();
								int sort=9999;
								for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
									if(column.equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
										sort=displayInstanceOwnItem.getItemList().get(l).getSort();
										//如果是工况
										if("resultCode".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())||"resultName".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
											if(jedis.hexists("RPCWorkType".getBytes(), value.getBytes())){
												workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkType".getBytes(), value.getBytes()));
											}
											if(workType!=null){
												value=workType.getResultName();
											}
										}
										break;
									}
								}
								ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort);
								protocolItemResolutionDataList.add(protocolItemResolutionData);
							}
						}
						
						for(int j=0;j<protocolItems.size();j++){
							String columnName=protocolItems.get(j).getTitle();
							String rawColumnName=columnName;
							String value=obj[j+6]+"";
							String rawValue=value;
							String addr=protocolItems.get(j).getAddr()+"";
							String column="";
							if(loadProtocolMappingColumnByTitleMap.containsKey(protocolItems.get(j).getTitle())){
								column=loadProtocolMappingColumnByTitleMap.get(protocolItems.get(j).getTitle()).getMappingColumn();
							}
							String columnDataType=protocolItems.get(j).getIFDataType();
							String resolutionMode=protocolItems.get(j).getResolutionMode()+"";
							String bitIndex="";
							String unit=protocolItems.get(j).getUnit();
							int sort=9999;
							
							if(protocolItems.get(j).getResolutionMode()==1||protocolItems.get(j).getResolutionMode()==2){//如果是枚举量
								for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
									if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column) && displayInstanceOwnItem.getItemList().get(l).getType()!=2){
										sort=displayInstanceOwnItem.getItemList().get(l).getSort();
										break;
									}
								}
								
								if(StringManagerUtils.isNotNull(value)&&protocolItems.get(j).getMeaning()!=null&&protocolItems.get(j).getMeaning().size()>0){
									for(int l=0;l<protocolItems.get(j).getMeaning().size();l++){
										if(StringManagerUtils.stringToFloat(value)==(protocolItems.get(j).getMeaning().get(l).getValue())){
											value=protocolItems.get(j).getMeaning().get(l).getMeaning();
											break;
										}
									}
								}
								ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort);
								protocolItemResolutionDataList.add(protocolItemResolutionData);
							}else if(protocolItems.get(j).getResolutionMode()==0){//如果是开关量
								boolean isMatch=false;
								if(protocolItems.get(j).getMeaning()!=null&&protocolItems.get(j).getMeaning().size()>0){
									String[] valueArr=value.split(",");
									for(int l=0;l<protocolItems.get(j).getMeaning().size();l++){
										isMatch=false;
										columnName=protocolItems.get(j).getMeaning().get(l).getMeaning();
										sort=9999;
										
										for(int n=0;n<displayInstanceOwnItem.getItemList().size();n++){
											if(displayInstanceOwnItem.getItemList().get(n).getItemCode().equalsIgnoreCase(column) 
													&&displayInstanceOwnItem.getItemList().get(n).getBitIndex()==protocolItems.get(j).getMeaning().get(l).getValue()
													){
												sort=displayInstanceOwnItem.getItemList().get(n).getSort();
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
												if(m==protocolItems.get(j).getMeaning().get(l).getValue()){
													bitIndex=m+"";
													if("bool".equalsIgnoreCase(columnDataType) || "boolean".equalsIgnoreCase(columnDataType)){
														value=("true".equalsIgnoreCase(valueArr[m]) || "1".equalsIgnoreCase(valueArr[m]))?"开":"关";
														rawValue=("true".equalsIgnoreCase(valueArr[m]) || "1".equalsIgnoreCase(valueArr[m]))?"1":"0";
													}else{
														value=valueArr[m];
													}
													ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort);
													protocolItemResolutionDataList.add(protocolItemResolutionData);
													match=true;
													break;
												}
											}
											if(!match){
												value="";
												rawValue="";
												bitIndex=protocolItems.get(j).getMeaning().get(l).getValue()+"";
												ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,protocolItems.get(j).getMeaning().get(l).getValue()+"",unit,sort);
												protocolItemResolutionDataList.add(protocolItemResolutionData);
											}
										}else{
											for(int m=0;m<displayInstanceOwnItem.getItemList().size();m++){
												if(displayInstanceOwnItem.getItemList().get(m).getItemCode().equalsIgnoreCase(column) && displayInstanceOwnItem.getItemList().get(m).getBitIndex()==protocolItems.get(j).getMeaning().get(l).getValue() ){
													sort=displayInstanceOwnItem.getItemList().get(m).getSort();
													break;
												}
											}
											value="";
											rawValue="";
											bitIndex=protocolItems.get(j).getMeaning().get(l).getValue()+"";
											ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,protocolItems.get(j).getMeaning().get(l).getValue()+"",unit,sort);
											protocolItemResolutionDataList.add(protocolItemResolutionData);
										}
									}
								}else{
									for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
										if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column)){
											sort=displayInstanceOwnItem.getItemList().get(l).getSort();
											break;
										}
									}
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort);
									protocolItemResolutionDataList.add(protocolItemResolutionData);
								}
							}else{//如果是数据量
								for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
									if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column) && displayInstanceOwnItem.getItemList().get(l).getType()!=2){
										sort=displayInstanceOwnItem.getItemList().get(l).getSort();
										break;
									}
								}
								
								ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort);
								protocolItemResolutionDataList.add(protocolItemResolutionData);
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
								int alarmLevel=0;
								if(index<finalProtocolItemResolutionDataList.size()&&StringManagerUtils.isNotNull(finalProtocolItemResolutionDataList.get(index).getColumnName())){
									columnName=finalProtocolItemResolutionDataList.get(index).getColumnName();
									value=finalProtocolItemResolutionDataList.get(index).getValue();
									unit=finalProtocolItemResolutionDataList.get(index).getUnit();
									rawValue=finalProtocolItemResolutionDataList.get(index).getRawValue();
									addr=finalProtocolItemResolutionDataList.get(index).getAddr();
									column=finalProtocolItemResolutionDataList.get(index).getColumn();
									columnDataType=finalProtocolItemResolutionDataList.get(index).getColumnDataType();
									resolutionMode=finalProtocolItemResolutionDataList.get(index).getResolutionMode();
									bitIndex=finalProtocolItemResolutionDataList.get(index).getBitIndex();
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
															
															if((StringManagerUtils.stringToFloat(rawValue)>upperLimit+hystersis)
																	||(StringManagerUtils.stringToFloat(rawValue)<lowerLimit-hystersis)
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
														if((StringManagerUtils.stringToFloat(rawValue)>upperLimit+hystersis)
																||(StringManagerUtils.stringToFloat(rawValue)<lowerLimit-hystersis)
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
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getDeviceInfoData(String deviceId,String deviceName,String deviceType,User user)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String deviceInfoKey="DeviceInfo";
		DataDictionary ddic=dataitemsInfoService.findTableSqlWhereByListFaceId("realTimeMonitoring_DeviceInfo");
		
		List<String> heads=ddic.getHeaders();
		List<String> fields=ddic.getFields();
		
		Jedis jedis=null;
		DeviceInfo deviceInfo=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists(deviceInfoKey.getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				byte[] dviceInfoByte =jedis.hget(deviceInfoKey.getBytes(),deviceId.getBytes());
				Object obj =SerializeObjectUnils.unserizlize(dviceInfoByte);
				if (obj instanceof DeviceInfo) {
					deviceInfo=(DeviceInfo)obj;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			StringBuffer deviceInfoDataList=new StringBuffer();
			deviceInfoDataList.append("[");
			
			//设备信息
			for(int i=0;i<fields.size();i++){
				if(deviceInfo.getCalculateType()==1){
					if("manufacturer".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getPumpingUnit()!=null?deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getManufacturer():"")+"\"},");
					}else if("model".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getPumpingUnit()!=null?deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getModel():"")+"\"},");
					}else if("stroke".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getPumpingUnit()!=null?deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getStroke():"")+"\"},");
					}else if("crankRotationDirection".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getPumpingUnit()!=null?("Clockwise".equalsIgnoreCase(deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getCrankRotationDirection())?"顺时针":"逆时针"):"")+"\"},");
					}else if("offsetAngleOfCrank".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getPumpingUnit()!=null?deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getOffsetAngleOfCrank():"")+"\"},");
					}else if("crankGravityRadius".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getPumpingUnit()!=null?deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getCrankGravityRadius():"")+"\"},");
					}else if("singleCrankWeight".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getPumpingUnit()!=null?deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getSingleCrankWeight():"")+"\"},");
					}else if("singleCrankPinWeight".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getPumpingUnit()!=null?deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getSingleCrankPinWeight():"")+"\"},");
					}else if("structuralUnbalance".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getPumpingUnit()!=null?deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getStructuralUnbalance():"")+"\"},");
					}else if("balance".equalsIgnoreCase(fields.get(i))){
						if(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getPumpingUnit()!=null&&deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getBalance()!=null&&deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getBalance().getEveryBalance()!=null&&deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getBalance().getEveryBalance().size()>0){
							for(int j=0;j<deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getBalance().getEveryBalance().size();j++){
								deviceInfoDataList.append("{\"item\":\"平衡块"+(j+1)+"位置重量\","+ "\"value\":\""+(deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getBalance().getEveryBalance().get(j).getPosition()+","+deviceInfo.getRpcCalculateRequestData().getPumpingUnit().getBalance().getEveryBalance().get(j).getWeight())+"\"},");
							}
						}else{
//							deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\"\"},");
						}
					}else if("crudeOilDensity".equalsIgnoreCase(fields.get(i))){
						if( !(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0) ){
							deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getFluidPVT()!=null?deviceInfo.getRpcCalculateRequestData().getFluidPVT().getCrudeOilDensity():"")+"\"},");
						}
					}else if("waterDensity".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getFluidPVT()!=null?deviceInfo.getRpcCalculateRequestData().getFluidPVT().getWaterDensity():"")+"\"},");
					}else if("naturalGasRelativeDensity".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getFluidPVT()!=null?deviceInfo.getRpcCalculateRequestData().getFluidPVT().getNaturalGasRelativeDensity():"")+"\"},");
					}else if("saturationPressure".equalsIgnoreCase(fields.get(i))){
						if( !(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0) ){
							deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getFluidPVT()!=null?deviceInfo.getRpcCalculateRequestData().getFluidPVT().getSaturationPressure():"")+"\"},");
						}
					}else if("reservoirDepth".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getReservoir()!=null?deviceInfo.getRpcCalculateRequestData().getReservoir().getDepth():"")+"\"},");
					}else if("reservoirTemperature".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getReservoir()!=null?deviceInfo.getRpcCalculateRequestData().getReservoir().getTemperature():"")+"\"},");
					}else if("tubingPressure".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getProduction()!=null?deviceInfo.getRpcCalculateRequestData().getProduction().getTubingPressure():"")+"\"},");
					}else if("casingPressure".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getProduction()!=null?deviceInfo.getRpcCalculateRequestData().getProduction().getCasingPressure():"")+"\"},");
					}else if("wellHeadTemperature".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getProduction()!=null?deviceInfo.getRpcCalculateRequestData().getProduction().getWellHeadTemperature():"")+"\"},");
					}else if("waterCut".equalsIgnoreCase(fields.get(i))){
						if( !(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0) ){
							deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getProduction()!=null?deviceInfo.getRpcCalculateRequestData().getProduction().getWaterCut():"")+"\"},");
						}
					}else if("productionGasOilRatio".equalsIgnoreCase(fields.get(i))){
						if( !(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0) ){
							deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getProduction()!=null?deviceInfo.getRpcCalculateRequestData().getProduction().getProductionGasOilRatio():"")+"\"},");
						}
					}else if("producingfluidLevel".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getProduction()!=null?deviceInfo.getRpcCalculateRequestData().getProduction().getProducingfluidLevel():"")+"\"},");
					}else if("pumpSettingDepth".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getProduction()!=null?deviceInfo.getRpcCalculateRequestData().getProduction().getPumpSettingDepth():"")+"\"},");
					}else if("barrelType".equalsIgnoreCase(fields.get(i))){
						String barrelType="";
						if(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getPump()!=null&&deviceInfo.getRpcCalculateRequestData().getPump().getBarrelType()!=null){
							if("L".equalsIgnoreCase(deviceInfo.getRpcCalculateRequestData().getPump().getBarrelType())){
								barrelType="组合泵";
							}else if("H".equalsIgnoreCase(deviceInfo.getRpcCalculateRequestData().getPump().getBarrelType())){
								barrelType="整筒泵";
							}
						}
						deviceInfoDataList.append("{\"item\":\"泵筒类型\","+ "\"value\":\""+barrelType+"\"},");
					}else if("pumpGrade".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getPump()!=null?deviceInfo.getRpcCalculateRequestData().getPump().getPumpGrade():"")+"\"},");
					}else if("pumpBoreDiameter".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getPump()!=null?deviceInfo.getRpcCalculateRequestData().getPump().getPumpBoreDiameter()*1000:"")+"\"},");
					}else if("plungerLength".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getPump()!=null?deviceInfo.getRpcCalculateRequestData().getPump().getPlungerLength():"")+"\"},");
					}else if("tubingStringInsideDiameter".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getTubingString()!=null&&deviceInfo.getRpcCalculateRequestData().getTubingString().getEveryTubing()!=null&&deviceInfo.getRpcCalculateRequestData().getTubingString().getEveryTubing().size()>0?deviceInfo.getRpcCalculateRequestData().getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000:"")+"\"},");
					}else if("casingStringInsideDiameter".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getCasingString()!=null&&deviceInfo.getRpcCalculateRequestData().getCasingString().getEveryCasing()!=null&&deviceInfo.getRpcCalculateRequestData().getCasingString().getEveryCasing().size()>0?deviceInfo.getRpcCalculateRequestData().getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000:"")+"\"},");
					}else if("rodString".equalsIgnoreCase(fields.get(i))){
						String rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="";
						String rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="";
						String rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="";
						String rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="";
						if(deviceInfo!=null&&deviceInfo.getRpcCalculateRequestData().getRodString()!=null&&deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod()!=null&&deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().size()>0){
							if(deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().size()>0){
								rodGrade1=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(0).getGrade();
								rodOutsideDiameter1=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
								rodInsideDiameter1=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
								rodLength1=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(0).getLength()+"";
							}
							if(deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().size()>1){
								rodGrade2=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(1).getGrade();
								rodOutsideDiameter2=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
								rodInsideDiameter2=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
								rodLength2=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(1).getLength()+"";
							}
							if(deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().size()>2){
								rodGrade3=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(2).getGrade();
								rodOutsideDiameter3=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
								rodInsideDiameter3=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
								rodLength3=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(2).getLength()+"";
							}
							if(deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().size()>3){
								rodGrade4=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(3).getGrade();
								rodOutsideDiameter4=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
								rodInsideDiameter4=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
								rodLength4=deviceInfo.getRpcCalculateRequestData().getRodString().getEveryRod().get(3).getLength()+"";
							}
						}
						
						deviceInfoDataList.append("{\"item\":\"一级杆级别\",\"value\":\""+rodGrade1+"\"},");
						deviceInfoDataList.append("{\"item\":\"一级杆外径(mm)\",\"value\":\""+rodOutsideDiameter1+"\"},");
						deviceInfoDataList.append("{\"item\":\"一级杆内径(mm)\",\"value\":\""+rodInsideDiameter1+"\"},");
						deviceInfoDataList.append("{\"item\":\"一级杆长度(m)\",\"value\":\""+rodLength1+"\"},");
						
						deviceInfoDataList.append("{\"item\":\"二级杆级别\",\"value\":\""+rodGrade2+"\"},");
						deviceInfoDataList.append("{\"item\":\"二级杆外径(mm)\",\"value\":\""+rodOutsideDiameter2+"\"},");
						deviceInfoDataList.append("{\"item\":\"二级杆内径(mm)\",\"value\":\""+rodInsideDiameter2+"\"},");
						deviceInfoDataList.append("{\"item\":\"二级杆长度(m)\",\"value\":\""+rodLength2+"\"},");
						
						deviceInfoDataList.append("{\"item\":\"三级杆级别\",\"value\":\""+rodGrade3+"\"},");
						deviceInfoDataList.append("{\"item\":\"三级杆外径(mm)\",\"value\":\""+rodOutsideDiameter3+"\"},");
						deviceInfoDataList.append("{\"item\":\"三级杆内径(mm)\",\"value\":\""+rodInsideDiameter3+"\"},");
						deviceInfoDataList.append("{\"item\":\"三级杆长度(m)\",\"value\":\""+rodLength3+"\"},");
						
						deviceInfoDataList.append("{\"item\":\"四级杆级别\",\"value\":\""+rodGrade4+"\"},");
						deviceInfoDataList.append("{\"item\":\"四级杆外径(mm)\",\"value\":\""+rodOutsideDiameter4+"\"},");
						deviceInfoDataList.append("{\"item\":\"四级杆内径(mm)\",\"value\":\""+rodInsideDiameter4+"\"},");
					}
				}else if(deviceInfo.getCalculateType()==2){
					if("crudeOilDensity".equalsIgnoreCase(fields.get(i))){
						if( !(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0) ){
							deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getFluidPVT()!=null?deviceInfo.getPcpCalculateRequestData().getFluidPVT().getCrudeOilDensity():"")+"\"},");
						}
					}else if("waterDensity".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getFluidPVT()!=null?deviceInfo.getPcpCalculateRequestData().getFluidPVT().getWaterDensity():"")+"\"},");
					}else if("naturalGasRelativeDensity".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getFluidPVT()!=null?deviceInfo.getPcpCalculateRequestData().getFluidPVT().getNaturalGasRelativeDensity():"")+"\"},");
					}else if("saturationPressure".equalsIgnoreCase(fields.get(i))){
						if( !(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0) ){
							deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getFluidPVT()!=null?deviceInfo.getPcpCalculateRequestData().getFluidPVT().getSaturationPressure():"")+"\"},");
						}
					}else if("reservoirDepth".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getReservoir()!=null?deviceInfo.getPcpCalculateRequestData().getReservoir().getDepth():"")+"\"},");
					}else if("reservoirTemperature".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getReservoir()!=null?deviceInfo.getPcpCalculateRequestData().getReservoir().getTemperature():"")+"\"},");
					}else if("tubingPressure".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getProduction()!=null?deviceInfo.getPcpCalculateRequestData().getProduction().getTubingPressure():"")+"\"},");
					}else if("casingPressure".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getProduction()!=null?deviceInfo.getPcpCalculateRequestData().getProduction().getCasingPressure():"")+"\"},");
					}else if("wellHeadTemperature".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getProduction()!=null?deviceInfo.getPcpCalculateRequestData().getProduction().getWellHeadTemperature():"")+"\"},");
					}else if("waterCut".equalsIgnoreCase(fields.get(i))){
						if( !(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0) ){
							deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getProduction()!=null?deviceInfo.getPcpCalculateRequestData().getProduction().getWaterCut():"")+"\"},");
						}
					}else if("productionGasOilRatio".equalsIgnoreCase(fields.get(i))){
						if( !(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0) ){
							deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getProduction()!=null?deviceInfo.getPcpCalculateRequestData().getProduction().getProductionGasOilRatio():"")+"\"},");
						}
					}else if("producingfluidLevel".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getProduction()!=null?deviceInfo.getPcpCalculateRequestData().getProduction().getProducingfluidLevel():"")+"\"},");
					}else if("pumpSettingDepth".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getProduction()!=null?deviceInfo.getPcpCalculateRequestData().getProduction().getPumpSettingDepth():"")+"\"},");
					}else if("barrelLength".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getPump()!=null?deviceInfo.getPcpCalculateRequestData().getPump().getBarrelLength():"")+"\"},");
					}else if("barrelSeries".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getPump()!=null?deviceInfo.getPcpCalculateRequestData().getPump().getBarrelSeries():"")+"\"},");
					}else if("rotorDiameter".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getPump()!=null?deviceInfo.getPcpCalculateRequestData().getPump().getRotorDiameter():"")+"\"},");
					}else if("QPR".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getPump()!=null?deviceInfo.getPcpCalculateRequestData().getPump().getQPR():"")+"\"},");
					}else if("tubingStringInsideDiameter".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getTubingString()!=null&&deviceInfo.getPcpCalculateRequestData().getTubingString().getEveryTubing()!=null&&deviceInfo.getPcpCalculateRequestData().getTubingString().getEveryTubing().size()>0?deviceInfo.getPcpCalculateRequestData().getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000:"")+"\"},");
					}else if("casingStringInsideDiameter".equalsIgnoreCase(fields.get(i))){
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getCasingString()!=null&&deviceInfo.getPcpCalculateRequestData().getCasingString().getEveryCasing()!=null&&deviceInfo.getPcpCalculateRequestData().getCasingString().getEveryCasing().size()>0?deviceInfo.getPcpCalculateRequestData().getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000:"")+"\"},");
					}else if("rodString".equalsIgnoreCase(fields.get(i))){
						String rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="";
						String rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="";
						String rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="";
						String rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="";
						if(deviceInfo!=null&&deviceInfo.getPcpCalculateRequestData().getRodString()!=null&&deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod()!=null&&deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().size()>0){
							if(deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().size()>0){
								rodGrade1=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(0).getGrade();
								rodOutsideDiameter1=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
								rodInsideDiameter1=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
								rodLength1=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(0).getLength()+"";
							}
							if(deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().size()>1){
								rodGrade2=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(1).getGrade();
								rodOutsideDiameter2=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
								rodInsideDiameter2=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
								rodLength2=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(1).getLength()+"";
							}
							if(deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().size()>2){
								rodGrade3=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(2).getGrade();
								rodOutsideDiameter3=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
								rodInsideDiameter3=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
								rodLength3=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(2).getLength()+"";
							}
							if(deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().size()>3){
								rodGrade4=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(3).getGrade();
								rodOutsideDiameter4=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
								rodInsideDiameter4=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
								rodLength4=deviceInfo.getPcpCalculateRequestData().getRodString().getEveryRod().get(3).getLength()+"";
							}
						}
						
						deviceInfoDataList.append("{\"item\":\"一级杆级别\",\"value\":\""+rodGrade1+"\"},");
						deviceInfoDataList.append("{\"item\":\"一级杆外径(mm)\",\"value\":\""+rodOutsideDiameter1+"\"},");
						deviceInfoDataList.append("{\"item\":\"一级杆内径(mm)\",\"value\":\""+rodInsideDiameter1+"\"},");
						deviceInfoDataList.append("{\"item\":\"一级杆长度(m)\",\"value\":\""+rodLength1+"\"},");
						
						deviceInfoDataList.append("{\"item\":\"二级杆级别\",\"value\":\""+rodGrade2+"\"},");
						deviceInfoDataList.append("{\"item\":\"二级杆外径(mm)\",\"value\":\""+rodOutsideDiameter2+"\"},");
						deviceInfoDataList.append("{\"item\":\"二级杆内径(mm)\",\"value\":\""+rodInsideDiameter2+"\"},");
						deviceInfoDataList.append("{\"item\":\"二级杆长度(m)\",\"value\":\""+rodLength2+"\"},");
						
						deviceInfoDataList.append("{\"item\":\"三级杆级别\",\"value\":\""+rodGrade3+"\"},");
						deviceInfoDataList.append("{\"item\":\"三级杆外径(mm)\",\"value\":\""+rodOutsideDiameter3+"\"},");
						deviceInfoDataList.append("{\"item\":\"三级杆内径(mm)\",\"value\":\""+rodInsideDiameter3+"\"},");
						deviceInfoDataList.append("{\"item\":\"三级杆长度(m)\",\"value\":\""+rodLength3+"\"},");
						
						deviceInfoDataList.append("{\"item\":\"四级杆级别\",\"value\":\""+rodGrade4+"\"},");
						deviceInfoDataList.append("{\"item\":\"四级杆外径(mm)\",\"value\":\""+rodOutsideDiameter4+"\"},");
						deviceInfoDataList.append("{\"item\":\"四级杆内径(mm)\",\"value\":\""+rodInsideDiameter4+"\"},");
					}
					
				
				}
				
				
			}
			
			if(deviceInfoDataList.toString().endsWith(",")){
				deviceInfoDataList.deleteCharAt(deviceInfoDataList.length() - 1);
			}
			deviceInfoDataList.append("]");
			result_json.append("{\"success\":true,\"totalRoot\":"+deviceInfoDataList+"}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getDeviceControlData(String deviceId,String deviceName,String deviceType,User user)throws Exception {
		StringBuffer result_json = new StringBuffer();
		int dataSaveMode=1;
		String deviceTableName="tbl_device";
		String deviceInfoKey="DeviceInfo";
		
		Jedis jedis=null;
		DeviceInfo deviceInfo=null;
		UserInfo userInfo=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		String protocolName="";
		
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists(deviceInfoKey.getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				byte[] dviceInfoByte =jedis.hget(deviceInfoKey.getBytes(),deviceId.getBytes());
				Object obj =SerializeObjectUnils.unserizlize(dviceInfoByte);
				if (obj instanceof DeviceInfo) {
					deviceInfo=(DeviceInfo)obj;
				}

				userInfo=MemoryDataManagerTask.getUserInfoByNo(user.getUserNo()+"");
				
				displayInstanceOwnItem=MemoryDataManagerTask.getDisplayInstanceOwnItemByCode(deviceInfo.getDisplayInstanceCode());
				if(displayInstanceOwnItem!=null){
					protocolName=displayInstanceOwnItem.getProtocol();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			String matrix=getUserRoleModuleMatrix(userInfo!=null?userInfo.getUserNo():0,"DeviceRealTimeMonitoring");
			
			int isControl=StringManagerUtils.getModuleRightFlagFromMatrix(matrix,2);
			
			List<String> controlItems=new ArrayList<String>();
			List<String> controlColumns=new ArrayList<String>();
			List<Integer> controlItemResolutionMode=new ArrayList<Integer>();
			List<String> controlItemMeaningList=new ArrayList<String>();
			StringBuffer deviceControlList=new StringBuffer();
			deviceControlList.append("[");
			
			if(displayInstanceOwnItem!=null){
				ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
				if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()!=null){
					for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
						if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
							for(int j=0;j<displayInstanceOwnItem.getItemList().size();j++){
								if(displayInstanceOwnItem.getItemList().get(j).getType()==2&&displayInstanceOwnItem.getItemList().get(j).getShowLevel()>=userInfo.getRoleShowLevel()){
									for(int k=0;k<modbusProtocolConfig.getProtocol().get(i).getItems().size();k++){
										if(displayInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle())){
											if("rw".equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getRWType())
													||"w".equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getRWType())){
												String title=modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle();
												if(StringManagerUtils.isNotNull(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getUnit())){
													title+="("+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getUnit()+")";
												}
												controlItems.add(title);
												String col="";
												if(loadProtocolMappingColumnByTitleMap.containsKey(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle())){
													col=loadProtocolMappingColumnByTitleMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()).getMappingColumn();
												}
												controlColumns.add(col);
												controlItemResolutionMode.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getResolutionMode());
												if(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getResolutionMode()==2){//数据量
													controlItemMeaningList.add("[]");
												}else if(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getResolutionMode()==1){//枚举量
													if(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getMeaning()!=null && modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getMeaning().size()>0){
														StringBuffer itemMeaning_buff = new StringBuffer();
														itemMeaning_buff.append("[");
														for(int n=0;n<modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getMeaning().size();n++){
															itemMeaning_buff.append("["+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getMeaning().get(n).getValue()+",'"+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getMeaning().get(n).getMeaning()+"'],");
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
													controlItemMeaningList.add("[['true','开'],['false','关']]");
												}
											}
											break;
										}
									}
								}
							}
							break;
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
			
			if(controlColumns.size()>0){
				for(int i=0;i<controlColumns.size();i++){
					deviceControlList.append("{\"item\":\""+controlItems.get(i)+"\","
							+ "\"itemcode\":\""+controlColumns.get(i)+"\","
							+ "\"resolutionMode\":"+controlItemResolutionMode.get(i)+","
							+ "\"value\":\"\","
							+ "\"operation\":"+true+","
							+ "\"isControl\":"+isControl+","
							+ "\"showType\":1,"
							+ "\"commStatus\":"+commStatus+","
							+ "\"itemMeaning\":"+controlItemMeaningList.get(i)+"},");
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
			if(jedis!=null){
				jedis.close();
			}
		}
		
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getDeviceAddInfoData(String deviceId,String deviceName,String deviceType,int userId)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String deviceTableName="tbl_device";
		String infoTableName="tbl_deviceaddinfo";
		
		
		
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
		
		String auxiliaryDeviceDetailsSql="select t4.deviceid,t4.itemname,t4.itemvalue,t3.specifictype "
				+ " from tbl_device t,tbl_auxiliary2master t2,tbl_auxiliarydevice t3,tbl_auxiliarydeviceaddinfo t4 "
				+ " where t.id=t2.masterid and t2.auxiliaryid=t3.id and t3.id=t4.deviceid "
				+ " and t3.type= "+deviceType
				+ " and t.id= "+deviceId
				+ " order by t4.deviceid,t4.id";
		
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
			String details="<h style='line-height:1.5;'><b>厂家:</b> "+obj[2]+"</h><br/><h style='line-height:1.5;'><b>规格型号:</b> "+obj[3]+"</h><br/><h style='line-height:1.5;'><b>备注:</b> "+obj[4]+"</h>";
			for(int j=0;j<auxiliaryDeviceDetailsList.size();j++){
				Object[] detailsObj=(Object[]) auxiliaryDeviceDetailsList.get(j);
				String itemName=detailsObj[1]+"";
				String itemValue=detailsObj[2]+"";
				String specificType=detailsObj[3]+"";
				if("1".equalsIgnoreCase(specificType) && "旋转方向".equalsIgnoreCase(itemName)){
					if("Clockwise".equalsIgnoreCase(itemValue)){
						itemValue="顺时针";
					}else if("Anticlockwise".equalsIgnoreCase(itemValue)){
						itemValue="逆时针";
					}
				}
				if((detailsObj[0]+"").equalsIgnoreCase(obj[0]+"")){
					details+="<br/><h style='line-height:1.5;'><b>"+itemName+":</b> "+itemValue+"</h>";
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
		String tableName="tbl_rpcacqdata_hist";
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
	
	public String getRealTimeMonitoringCurveData(String deviceId,String deviceName,String deviceType,String calculateType,int userNo)throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer itemsBuff = new StringBuffer();
		StringBuffer curveConfBuff = new StringBuffer();
		int vacuateThreshold=Config.getInstance().configFile.getAp().getOthers().getVacuateThreshold();
		Jedis jedis=null;
		UserInfo userInfo=null;
		List<byte[]> calItemSet=null;
		List<byte[]> inputItemSet=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		DeviceInfo deviceInfo=null;
		int dataSaveMode=1;
		String displayInstanceCode="";
		String tableName="tbl_acqdata_hist";
		String deviceTableName="tbl_device";
		String rpcCalItemsKey="rpcCalItemList";
		String rpcInputItemsKey="rpcInputItemList";
		String pcpCalItemsKey="pcpCalItemList";
		String pcpInputItemsKey="pcpInputItemList";
		String deviceInfoKey="DeviceInfo";
		
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("UserInfo".getBytes())){
					MemoryDataManagerTask.loadUserInfo(null,0,"update");
				}
				if(!jedis.exists("DisplayInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadDisplayInstanceOwnItemById("","update");
				}
				if(jedis.hexists("UserInfo".getBytes(), (userNo+"").getBytes())){
					userInfo=(UserInfo) SerializeObjectUnils.unserizlize(jedis.hget("UserInfo".getBytes(), (userNo+"").getBytes()));
				}
				

				if(!jedis.exists(deviceInfoKey.getBytes())){
					MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
				}
				deviceInfo=(DeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget(deviceInfoKey.getBytes(), deviceId.getBytes()));
				if(deviceInfo!=null){
					displayInstanceCode=deviceInfo.getDisplayInstanceCode();
				}
				
				if(jedis!=null&&jedis.hexists("DisplayInstanceOwnItem".getBytes(), displayInstanceCode.getBytes())){
					displayInstanceOwnItem=(DisplayInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("DisplayInstanceOwnItem".getBytes(), displayInstanceCode.getBytes()));
					Collections.sort(displayInstanceOwnItem.getItemList());
				}
				
				if(StringManagerUtils.stringToInteger(calculateType)==1){
					if(!jedis.exists(rpcCalItemsKey.getBytes())){
						MemoryDataManagerTask.loadRPCCalculateItem();
					}
					calItemSet= jedis.zrange(rpcCalItemsKey.getBytes(), 0, -1);
					
					if(!jedis.exists(rpcInputItemsKey.getBytes())){
						MemoryDataManagerTask.loadRPCInputItem();
					}
					inputItemSet= jedis.zrange(rpcInputItemsKey.getBytes(), 0, -1);
				}
				if(StringManagerUtils.stringToInteger(calculateType)==2){
					if(!jedis.exists(pcpCalItemsKey.getBytes())){
						MemoryDataManagerTask.loadPCPCalculateItem();
					}
					calItemSet= jedis.zrange(pcpCalItemsKey.getBytes(), 0, -1);
					
					if(!jedis.exists(pcpInputItemsKey.getBytes())){
						MemoryDataManagerTask.loadPCPInputItem();
					}
					inputItemSet= jedis.zrange(pcpInputItemsKey.getBytes(), 0, -1);
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
				String protocolName=displayInstanceOwnItem.getProtocol();
				ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
				if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()!=null){
					for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
						if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
							for(int j=0;j<displayInstanceOwnItem.getItemList().size();j++){
								Gson gson = new Gson();
								java.lang.reflect.Type reflectType=new TypeToken<CurveConf>() {}.getType();
								CurveConf curveConfObj=gson.fromJson(displayInstanceOwnItem.getItemList().get(j).getRealtimeCurveConf(), reflectType);
								
								if(curveConfObj!=null && curveConfObj.getSort()>0 && displayInstanceOwnItem.getItemList().get(j).getShowLevel()>=userInfo.getRoleShowLevel()){
									String itemname=displayInstanceOwnItem.getItemList().get(j).getItemName();
									String bitindex=displayInstanceOwnItem.getItemList().get(j).getBitIndex()+"";
									String realtimecurveconf=displayInstanceOwnItem.getItemList().get(j).getRealtimeCurveConf();
									String itemcode=displayInstanceOwnItem.getItemList().get(j).getItemCode();
									String type=displayInstanceOwnItem.getItemList().get(j).getType()+"";
									
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
										if(calItemSet!=null){
											for(byte[] calItemByteArr:calItemSet){
												CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(calItemByteArr);
												if(itemcode.equalsIgnoreCase(calItem.getCode())){
													if(StringManagerUtils.isNotNull(calItem.getUnit())){
														itemName=itemName+"("+calItem.getUnit()+")";
													}
													break;
												}
											}
										}
										
										calItemNameList.add(itemName);
										calItemCurveConfList.add(realtimecurveconf.replaceAll("null", ""));
									}else if("3".equalsIgnoreCase(type)){
										inputItemColumnList.add(itemcode);
										String itemName=itemname;
										if(inputItemSet!=null){
											for(byte[] inputItemByteArr:inputItemSet){
												CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(inputItemByteArr);
												if(itemcode.equalsIgnoreCase(calItem.getCode())){
													if(StringManagerUtils.isNotNull(calItem.getUnit())){
														itemName=itemName+"("+calItem.getUnit()+")";
													}
													break;
												}
												
											}
										}
										
										inputItemNameList.add(itemName);
										inputItemCurveConfList.add(realtimecurveconf.replaceAll("null", ""));
									}
								
								}
							}
							break;
						}
					}
				}
			}else{
				String protocolSql="select upper(t3.protocol) from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t.instancecode=t2.code and t2.unitid=t3.id"
						+ " and  t.id="+deviceId;
				String curveItemsSql="select t4.itemname,t4.bitindex,t4.realtimecurveconf,t4.itemcode,t4.type "
						+ " from "+deviceTableName+" t,tbl_protocoldisplayinstance t2,tbl_display_unit_conf t3,tbl_display_items2unit_conf t4 "
						+ " where t.displayinstancecode=t2.code and t2.displayunitid=t3.id and t3.id=t4.unitid and t4.type<>2 "
						+ " and t.id="+deviceId+" "
						+ " and t4.realtimecurveconf is not null "
						+ " and decode(t4.showlevel,null,9999,t4.showlevel)>=( select r.showlevel from tbl_role r,tbl_user u where u.user_type=r.role_id and u.user_no='"+userNo+"' )"
						+ " order by t4.sort,t4.id";
				List<?> protocolList = this.findCallSql(protocolSql);
				List<?> curveItemList = this.findCallSql(curveItemsSql);
				String protocolName="";
				String unit="";
				String dataType="";
				int resolutionMode=0;
				
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
										if(calItemSet!=null){
											for(byte[] calItemByteArr:calItemSet){
												CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(calItemByteArr);
												if(itemcode.equalsIgnoreCase(calItem.getCode())){
													if(StringManagerUtils.isNotNull(calItem.getUnit())){
														itemName=itemName+"("+calItem.getUnit()+")";
													}
													break;
												}
												
											}
										}
										
										calItemNameList.add(itemName);
										calItemCurveConfList.add(realtimecurveconf.replaceAll("null", ""));
									}else if("3".equalsIgnoreCase(type)){
										inputItemColumnList.add(itemcode);
										String itemName=itemname;
										if(inputItemSet!=null){
											for(byte[] inputItemByteArr:inputItemSet){
												CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(inputItemByteArr);
												if(itemcode.equalsIgnoreCase(calItem.getCode())){
													if(StringManagerUtils.isNotNull(calItem.getUnit())){
														itemName=itemName+"("+calItem.getUnit()+")";
													}
													break;
												}
												
											}
										}
										
										inputItemNameList.add(itemName);
										inputItemCurveConfList.add(realtimecurveconf.replaceAll("null", ""));
									}
								}
								break;
							}
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
					calAndInputDataTable="tbl_rpcacqdata_hist";
				}else if(StringManagerUtils.stringToInteger(calculateType)==2){
					calAndInputDataTable="tbl_pcpacqdata_hist";
				}
				for(int i=0;i<itemColumnList.size();i++){
					columns+=",t."+itemColumnList.get(i);
				}
				if(StringManagerUtils.stringToInteger(calculateType)>0){
					for(int i=0;i<calItemColumnList.size();i++){
						calAndInputColumn+=",t3."+calItemColumnList.get(i);
					}
					if(inputItemColumnList.size()>0){
						calAndInputColumn+=",t3.productiondata";
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
				int rarefy=total/vacuateThreshold+1;
				sql+= " order by t.acqtime";
				
				String finalSql=sql;
				if(rarefy>1){
					finalSql="select acqtime"+columns+" from  (select v.*, rownum as rn from ("+sql+") v ) v2 where mod(rn*"+vacuateThreshold+","+total+")<"+vacuateThreshold+"";
				}
				List<?> list = this.findCallSql(finalSql);
				for(int i=0;i<list.size();i++){
					Object[] obj=(Object[]) list.get(i);
					result_json.append("{\"acqTime\":\"" + obj[0] + "\",\"data\":[");
					for(int j=1;j<=itemColumnList.size();j++){
						result_json.append(obj[j]+",");
					}
					
					for(int j=1+itemColumnList.size();j<1+itemColumnList.size()+calItemColumnList.size();j++){
						result_json.append(obj[j]+",");
					}
					if(inputItemColumnList.size()>0){
						String productionData=(obj[obj.length-1]+"").replaceAll("null", "");
						Gson gson = new Gson();
						java.lang.reflect.Type type=null;
						if(StringManagerUtils.stringToInteger(calculateType)==1){
							type = new TypeToken<RPCCalculateRequestData>() {}.getType();
							RPCCalculateRequestData rpcProductionData=gson.fromJson(productionData, type);
							for(int j=0;j<inputItemColumnList.size();j++){
								String inputItemValue="";
								String column=inputItemColumnList.get(j);
								if(rpcProductionData!=null){
									if("CrudeOilDensity".equalsIgnoreCase(column) && rpcProductionData.getFluidPVT()!=null ){
										inputItemValue=rpcProductionData.getFluidPVT().getCrudeOilDensity()+"";
									}else if("WaterDensity".equalsIgnoreCase(column) && rpcProductionData.getFluidPVT()!=null ){
										inputItemValue=rpcProductionData.getFluidPVT().getWaterDensity()+"";
									}else if("NaturalGasRelativeDensity".equalsIgnoreCase(column) && rpcProductionData.getFluidPVT()!=null ){
										inputItemValue=rpcProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
									}else if("SaturationPressure".equalsIgnoreCase(column) && rpcProductionData.getFluidPVT()!=null ){
										inputItemValue=rpcProductionData.getFluidPVT().getSaturationPressure()+"";
									}else if("ReservoirDepth".equalsIgnoreCase(column) && rpcProductionData.getReservoir()!=null ){
										inputItemValue=rpcProductionData.getReservoir().getDepth()+"";
									}else if("ReservoirTemperature".equalsIgnoreCase(column) && rpcProductionData.getReservoir()!=null ){
										inputItemValue=rpcProductionData.getReservoir().getTemperature()+"";
									}else if("TubingPressure".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
										inputItemValue=rpcProductionData.getProduction().getTubingPressure()+"";
									}else if("CasingPressure".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
										inputItemValue=rpcProductionData.getProduction().getCasingPressure()+"";
									}else if("WellHeadTemperature".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
										inputItemValue=rpcProductionData.getProduction().getWellHeadTemperature()+"";
									}else if("WaterCut".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
										inputItemValue=rpcProductionData.getProduction().getWaterCut()+"";
									}else if("ProductionGasOilRatio".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
										inputItemValue=rpcProductionData.getProduction().getProductionGasOilRatio()+"";
									}else if("ProducingfluidLevel".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
										inputItemValue=rpcProductionData.getProduction().getProducingfluidLevel()+"";
									}else if("PumpSettingDepth".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
										inputItemValue=rpcProductionData.getProduction().getPumpSettingDepth()+"";
									}else if("PumpBoreDiameter".equalsIgnoreCase(column) && rpcProductionData.getPump()!=null ){
										inputItemValue=rpcProductionData.getPump().getPumpBoreDiameter()*1000+"";
									}else if("LevelCorrectValue".equalsIgnoreCase(column) && rpcProductionData.getManualIntervention()!=null ){
										inputItemValue=rpcProductionData.getManualIntervention().getLevelCorrectValue()+"";
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
		}finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return result_json.toString();
	}
	
	public void saveDeviceControlLog(String deviceId,String deviceName,String deviceType,String title,String value,User user) throws SQLException{
		getBaseDao().saveDeviceControlLog(deviceId,deviceName,deviceType,title,value,user);
	}
	
	public String getResourceProbeHistoryCurveData(String startDate,String endDate,String itemName,String itemCode) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		String sql="select to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss'),"+itemCode+" from tbl_resourcemonitoring t "
				+ " where t.acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') +1 "
				+ " order by t.acqTime";
		int totals = getTotalCountRows(sql);//获取总记录数
		List<?> list=this.findCallSql(sql);
		dynSbf.append("{\"success\":true,\"totalCount\":" + totals + ",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				dynSbf.append("{ \"acqTime\":\"" + obj[0] + "\",");
				dynSbf.append("\"value\":\""+obj[1]+"\"},");
			}
			if(dynSbf.toString().endsWith(",")){
				dynSbf.deleteCharAt(dynSbf.length() - 1);
			}
		}
		dynSbf.append("]}");
		return dynSbf.toString().replaceAll("null", "");
	}
	
	public String querySingleDetailsWellBoreChartsData(int id,String deviceName) throws SQLException, IOException{
		byte[] bytes; 
		ConfigFile configFile=Config.getInstance().configFile;
		BufferedInputStream bis = null;
        StringBuffer dataSbf = new StringBuffer();
        StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
        String tableName="tbl_rpcacqdata_latest";
        String prodCol=" liquidVolumetricProduction";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol=" liquidweightproduction";
		}
        String sql="select t2.deviceName as deviceName, to_char(t.fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
        		+ " t.pumpfsdiagram,"
        		+ " t.upperloadline,t.lowerloadline, t.fmax,t.fmin,t.stroke,t.spm, "
        		+ " t."+prodCol+","
        		+ " status.resultname,status.resultcode,status.optimizationSuggestion, "//12
        		+ " t.rodstring,"
        		+ " t.pumpeff1*100 as pumpeff1, t.pumpeff2*100 as pumpeff2, t.pumpeff3*100 as pumpeff3, t.pumpeff4*100 as pumpeff4,"
        		+ " t.position_curve,t.load_curve"
        		+ " from "+tableName+" t"
        		+ " left outer join tbl_device t2 on t.deviceid=t2.id"
        		+ " left outer join tbl_rpc_worktype status on t.resultcode=status.resultcode"
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
			if(obj[2]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[2]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				pumpFSDiagram=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[18]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[18]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				positionCurveData=StringManagerUtils.CLOBtoString(realClob);
				if(StringManagerUtils.isNotNull(positionCurveData)){
					pointCount=positionCurveData.split(",").length+"";
				}
			}
			
			if(obj[19]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[19]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				loadCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			
			
			String rodStressRatio1="0",rodStressRatio2="0",rodStressRatio3="0",rodStressRatio4="0";
			
			if("1232".equals(obj[11]+"") || !StringManagerUtils.isNotNull(pumpFSDiagram)){//采集异常
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
		        if(obj[13]!=null){
		        	String rodDataArr[]=obj[13].toString().split(";");
			        for(int i=1;i<rodDataArr.length;i++){
			        	if(i==1&&rodDataArr[i].split(",").length==6){
			        		rodStressRatio1=rodDataArr[i].split(",")[5];
			        	}else if(i==2&&rodDataArr[i].split(",").length==6){
			        		rodStressRatio2=rodDataArr[i].split(",")[5];
			        	}if(i==3&&rodDataArr[i].split(",").length==6){
			        		rodStressRatio3=rodDataArr[i].split(",")[5];
			        	}if(i==4&&rodDataArr[i].split(",").length==6){
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
	        dataSbf.append("deviceName:\""+deviceName+"\",");           // 井名
	        dataSbf.append("acqTime:\""+obj[1]+"\",");         // 时间
	        dataSbf.append("pointCount:\""+pointCount+"\","); 
	        dataSbf.append("upperLoadLine:\""+obj[3]+"\",");         // 理论上载荷
	        dataSbf.append("lowerLoadLine:\""+obj[4]+"\",");         // 理论下载荷
	        dataSbf.append("fmax:\""+obj[5]+"\",");         // 最大载荷
	        dataSbf.append("fmin:\""+obj[6]+"\",");         // 最小载荷
	        dataSbf.append("stroke:\""+obj[7]+"\",");         // 冲程
	        dataSbf.append("spm:\""+obj[8]+"\",");         // 冲次
	        dataSbf.append("liquidProduction:\""+obj[9]+"\",");         // 日累计产液量
	        dataSbf.append("resultName:\""+obj[10]+"\",");         // 工况类型
	        dataSbf.append("resultCode:\""+obj[11]+"\",");         // 工况代码
	        dataSbf.append("optimizationSuggestion:\""+obj[12]+"\",");         // 优化建议
	        
	        dataSbf.append("rodStressRatio1:"+rodStressRatio1+",");       // 一级应力百分比
	        dataSbf.append("rodStressRatio2:"+rodStressRatio2+",");       // 二级应力百分比 
	        dataSbf.append("rodStressRatio3:"+rodStressRatio3+",");           // 三级应力百分比
	        dataSbf.append("rodStressRatio4:"+rodStressRatio4+",");           // 四级应力百分比
	        
	        dataSbf.append("pumpEff1:"+StringManagerUtils.stringToFloat(obj[13]==null?"":obj[14].toString(),1)+",");       // 冲程损失系数
	        dataSbf.append("pumpEff2:"+StringManagerUtils.stringToFloat(obj[14]==null?"":obj[15].toString().toString(),1)+",");       // 充满系数
	        dataSbf.append("pumpEff3:"+StringManagerUtils.stringToFloat(obj[15]==null?"":obj[16].toString().toString(),1)+",");           // 漏失系数
	        dataSbf.append("pumpEff4:"+StringManagerUtils.stringToFloat(obj[16]==null?"":obj[17].toString().toString(),1)+",");           // 液体收缩系数
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

	public String querySingleDetailsSurfaceData(int id,String deviceName) throws SQLException, IOException{
		byte[] bytes; 
		ConfigFile configFile=Config.getInstance().configFile;
		BufferedInputStream bis = null;
        StringBuffer dataSbf = new StringBuffer();
        StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
        String tableName="tbl_rpcacqdata_latest";
        
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
	        dataSbf.append("deviceName:\""+deviceName+"\",");           // 井名
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
	        dataSbf.append("deviceName:\""+deviceName+"\",");           // 井名
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
		}
		return dataSbf.toString().replaceAll("null", "");
	}
	
	public String getUIKitAccessToken(String videoKeyId) throws SQLException, IOException{
		StringBuffer dataSbf = new StringBuffer();
		Jedis jedis=null;
		AccessToken accessToken=null;
		boolean success=false;
		String accessTokenStr="";
		long expireTime=0;
		List<Integer> isList=new ArrayList<>();
		isList.add(StringManagerUtils.stringToInteger(videoKeyId));
		try {
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("UIKitAccessToken".getBytes())){
					MemoryDataManagerTask.loadUIKitAccessToken(null,"update");
				}else{
					if(jedis.hexists("UIKitAccessToken".getBytes(),videoKeyId.getBytes())){
						accessToken=(AccessToken)SerializeObjectUnils.unserizlize(jedis.hget("UIKitAccessToken".getBytes(),videoKeyId.getBytes()));
					}
					if(accessToken!=null&&"200".equalsIgnoreCase(accessToken.getCode())){
						long now=new Date().getTime();
						if(now>accessToken.getData().getExpireTime()){
							MemoryDataManagerTask.loadUIKitAccessToken(isList,"update");
						}
					}else{
						MemoryDataManagerTask.loadUIKitAccessToken(isList,"update");
					}
				}
				if(jedis.hexists("UIKitAccessToken".getBytes(),videoKeyId.getBytes())){
					accessToken=(AccessToken)SerializeObjectUnils.unserizlize(jedis.hget("UIKitAccessToken".getBytes(),videoKeyId.getBytes()));
				}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			if(jedis!=null){
				jedis.close();
			}
		}
		
		return dataSbf.toString();
	}
	
	public String getCalculateTypeDeviceCount(String orgId,String deviceType,String calculateType){
		int deviceCount=0;
		try{
			String deviceTableName="tbl_device";
			
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
}
