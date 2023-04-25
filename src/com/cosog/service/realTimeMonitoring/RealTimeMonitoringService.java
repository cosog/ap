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
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.CommStatus;
import com.cosog.model.CurveConf;
import com.cosog.model.User;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AcqInstanceOwnItem;
import com.cosog.model.calculate.AlarmInstanceOwnItem;
import com.cosog.model.calculate.DisplayInstanceOwnItem;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.RPCDeviceInfo;
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
				
				if(!jedis.exists("RPCDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
				}
				
				if(!jedis.exists("RPCWorkType".getBytes())){
					MemoryDataManagerTask.loadRPCWorkType();
				}
				deviceInfoByteList =jedis.hvals("RPCDeviceInfo".getBytes());
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
				String deviceTableName="viw_rpcdevice";
				
				String sql="select decode(t2.resultcode,null,'无数据',t3.resultname) as resultname,t2.resultcode,count(1) from "+deviceTableName+" t "
						+ " left outer join "+tableName+" t2 on  t2.wellid=t.id"
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
						if (obj instanceof RPCDeviceInfo) {
							RPCDeviceInfo rpcDeviceInfo=(RPCDeviceInfo)obj;
							if(StringManagerUtils.stringToArrExistNum(orgId, rpcDeviceInfo.getOrgId())){
								int count=1;
								int resultCode=rpcDeviceInfo.getResultCode()==null?0:rpcDeviceInfo.getResultCode();
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
				
				if(StringManagerUtils.stringToInteger(deviceType) ==0){
					if(!jedis.exists("RPCDeviceInfo".getBytes())){
						MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
					}
					deviceInfoByteList =jedis.hvals("RPCDeviceInfo".getBytes());
				}else{
					if(!jedis.exists("PCPDeviceInfo".getBytes())){
						MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
					}
					deviceInfoByteList =jedis.hvals("PCPDeviceInfo".getBytes());
				}
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
				String tableName="tbl_rpcacqdata_latest";
				String deviceTableName="viw_rpcdevice";
				if(StringManagerUtils.stringToInteger(deviceType)!=0){
					tableName="tbl_pcpacqdata_latest";
					deviceTableName="viw_pcpdevice";
				}
				
				String sql="select t2.commstatus,count(1) from "+deviceTableName+" t "
						+ " left outer join "+tableName+" t2 on  t2.wellid=t.id "
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
						if(StringManagerUtils.stringToInteger(deviceType) ==0){
							Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
							if (obj instanceof RPCDeviceInfo) {
								RPCDeviceInfo rpcDeviceInfo=(RPCDeviceInfo)obj;
								if(StringManagerUtils.stringToArrExistNum(orgId, rpcDeviceInfo.getOrgId())){
									commStatus=rpcDeviceInfo.getOnLineCommStatus();
									if(commStatus==1){
										online+=1;
									}else if(commStatus==2){
										goOnline+=1;
									}else{
										offline+=1;;
									}
								}
							}
						}else{
							Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
							if (obj instanceof PCPDeviceInfo) {
								PCPDeviceInfo pcpDeviceInfo=(PCPDeviceInfo)obj;
								if(StringManagerUtils.stringToArrExistNum(orgId, pcpDeviceInfo.getOrgId())){
									commStatus=pcpDeviceInfo.getOnLineCommStatus();
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
				
				if(StringManagerUtils.stringToInteger(deviceType) ==0){
					if(!jedis.exists("RPCDeviceInfo".getBytes())){
						MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
					}
					deviceInfoByteList =jedis.hvals("RPCDeviceInfo".getBytes());
				}else{
					if(!jedis.exists("PCPDeviceInfo".getBytes())){
						MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
					}
					deviceInfoByteList =jedis.hvals("PCPDeviceInfo".getBytes());
				}
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
			int total=0,run=0,stop=0,offline=0;
			if(jedis==null){
				String tableName="tbl_rpcacqdata_latest";
				String deviceTableName="viw_rpcdevice";
				if(StringManagerUtils.stringToInteger(deviceType)!=0){
					tableName="tbl_pcpacqdata_latest";
					deviceTableName="viw_pcpdevice";
				}
				
				String sql="select decode(t2.commstatus,0,-1,t2.runstatus) as runstatus,count(1) from "+deviceTableName+" t "
						+ " left outer join "+tableName+" t2 on  t2.wellid=t.id "
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
					}else{
						offline=StringManagerUtils.stringToInteger(obj[1]+"");
					}
				}
			}else{
				if(deviceInfoByteList!=null){
					for(int i=0;i<deviceInfoByteList.size();i++){
						int commStatus=0;
						int runStatus=0;
						if(StringManagerUtils.stringToInteger(deviceType) ==0){
							Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
							if (obj instanceof RPCDeviceInfo) {
								RPCDeviceInfo rpcDeviceInfo=(RPCDeviceInfo)obj;
								if(StringManagerUtils.stringToArrExistNum(orgId, rpcDeviceInfo.getOrgId())){
									commStatus=rpcDeviceInfo.getOnLineCommStatus();
									runStatus=rpcDeviceInfo.getRunStatus();
									if(commStatus>0){
										if(runStatus==1){
											run+=1;
										}else{
											stop+=1;;
										}
									}else{
										offline+=1;;
									}
								}
							}
						}else{
							Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
							if (obj instanceof PCPDeviceInfo) {
								PCPDeviceInfo pcpDeviceInfo=(PCPDeviceInfo)obj;
								if(StringManagerUtils.stringToArrExistNum(orgId, pcpDeviceInfo.getOrgId())){
									commStatus=pcpDeviceInfo.getOnLineCommStatus();
									runStatus=pcpDeviceInfo.getRunStatus();
									if(commStatus>0){
										if(runStatus==1){
											run+=1;
										}else{
											stop+=1;;
										}
									}else{
										offline+=1;;
									}
								}
							}
						}
					}
				}
			}
			
			total=run+stop+offline;
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
			
			String tableName="tbl_rpcacqdata_latest";
			String deviceTableName="viw_rpcdevice";
			if(StringManagerUtils.stringToInteger(deviceType)!=0){
				tableName="tbl_pcpacqdata_latest";
				deviceTableName="viw_pcpdevice";
			}
			
			String sql="select t.devicetypename,t.devicetype,count(1) from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t.id=t2.wellid "
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
			String tableName="tbl_rpcacqdata_latest";
			String deviceTableName="tbl_rpcdevice";
			
			String sql="select t.id from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
					+ " left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode "
					+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.wellName='"+deviceName+"'";
			}
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+=" and decode(t2.resultcode,null,'无数据',t3.resultName)='"+FESdiagramResultStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽'))='"+runStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.wellname";
			dataPage=this.getDataPage(StringManagerUtils.stringToInteger(deviceId), sql, StringManagerUtils.stringToInteger(limit));
		}catch(Exception e){
			dataPage=1;
		}
		return dataPage;
	}
	
	public String getDeviceRealTimeOverview(String orgId,String deviceName,String deviceType,String FESdiagramResultStatValue,String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,Page pager) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int dataSaveMode=1;
		Jedis jedis=null;
		AlarmShowStyle alarmShowStyle=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("RPCDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
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
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			
			String tableName="tbl_rpcacqdata_latest";
			String deviceTableName="tbl_rpcdevice";
			String ddicName="realTimeMonitoring_RPCOverview";
			String columnsKey="rpcDeviceAcquisitionItemColumns";
			DataDictionary ddic = null;
			List<String> ddicColumnsList=new ArrayList<String>();
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
			
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			String columns = ddic.getTableHeader();
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+tableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			
			String prodCol="liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol="liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,";
			}
			
			String sql="select t.id,t.wellname,t.videourl,"
					+ "c1.itemname as devicetypename,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽')) as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "t2.resultcode,decode(t2.resultcode,null,'无数据',t3.resultName) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
					+ prodCol+""
					+ "t2.FMax,t2.FMin,t2.fullnessCoefficient,"
					+ "t2.averageWatt,t2.polishrodPower,t2.waterPower,"
					+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
					+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
					+ "t2.systemEfficiency*100 as systemEfficiency,t2.energyper100mlift,"
					+ "t2.pumpEff*100 as pumpEff,"
					+ "t2.iDegreeBalance,t2.wattDegreeBalance,t2.deltaradius*100 as deltaradius,"
					+ "t2.levelCorrectValue,t2.inverProducingfluidLevel,t2.todayKWattH";
			String[] ddicColumns=ddic.getSql().split(",");
			for(int i=0;i<ddicColumns.length;i++){
				if(dataSaveMode==0){
					if(StringManagerUtils.existOrNot(loadedAcquisitionItemColumnsMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
						ddicColumnsList.add(ddicColumns[i]);
					}
				}else{
					if(StringManagerUtils.existOrNotByValue(loadedAcquisitionItemColumnsMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
						ddicColumnsList.add(ddicColumns[i]);
					}
				}
			}
			for(int i=0;i<ddicColumnsList.size();i++){
				sql+=",t2."+ddicColumnsList.get(i);
			}
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
					+ " left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode "
					+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.wellName='"+deviceName+"'";
			}
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+=" and decode(t2.resultcode,null,'无数据',t3.resultName)='"+FESdiagramResultStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽'))='"+runStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.wellname";
			
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
				String commStatusName=obj[6]+"";
				String runStatusName=obj[11]+"";
				String resultCode=obj[15]+"";
				
				RPCDeviceInfo rpcDeviceInfo=null;
				if(jedis!=null&&jedis.hexists("RPCDeviceInfo".getBytes(), deviceId.getBytes())){
					rpcDeviceInfo=(RPCDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("RPCDeviceInfo".getBytes(), deviceId.getBytes()));
				}
				String protocolName="";
				AcqInstanceOwnItem acqInstanceOwnItem=null;
				if(jedis!=null&&rpcDeviceInfo!=null&&jedis.hexists("AcqInstanceOwnItem".getBytes(), rpcDeviceInfo.getInstanceCode().getBytes())){
					acqInstanceOwnItem=(AcqInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AcqInstanceOwnItem".getBytes(), rpcDeviceInfo.getInstanceCode().getBytes()));
					protocolName=acqInstanceOwnItem.getProtocol();
				}
				DisplayInstanceOwnItem displayInstanceOwnItem=null;
				if(jedis!=null&&rpcDeviceInfo!=null&&jedis.hexists("DisplayInstanceOwnItem".getBytes(), rpcDeviceInfo.getDisplayInstanceCode().getBytes())){
					displayInstanceOwnItem=(DisplayInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("DisplayInstanceOwnItem".getBytes(), rpcDeviceInfo.getDisplayInstanceCode().getBytes()));
				}
				
				AlarmInstanceOwnItem alarmInstanceOwnItem=null;
				if(jedis!=null&&rpcDeviceInfo!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), rpcDeviceInfo.getAlarmInstanceCode().getBytes())){
					alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), rpcDeviceInfo.getAlarmInstanceCode().getBytes()));
				}
				ModbusProtocolConfig.Protocol protocol=null;
				if(modbusProtocolConfig!=null){
					for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
						if(modbusProtocolConfig.getProtocol().get(j).getDeviceType()==StringManagerUtils.stringToInteger(deviceType) 
								&& protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
							protocol=modbusProtocolConfig.getProtocol().get(j);
							break;
						}
					}
				}
				
				int commAlarmLevel=0,resultAlarmLevel=0,runAlarmLevel=0;
				if(alarmInstanceOwnItem!=null){
					for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
						if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
							commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
							runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==4 && alarmInstanceOwnItem.getItemList().get(j).getItemCode().equalsIgnoreCase(resultCode)){
							resultAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}
					}
				}
				
				result_json.append("{\"id\":"+deviceId+",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"videoUrl\":\""+obj[2]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[3]+"\",");
				result_json.append("\"acqTime\":\""+obj[4]+"\",");
				result_json.append("\"commStatus\":"+obj[5]+",");
				result_json.append("\"commStatusName\":\""+commStatusName+"\",");
				result_json.append("\"commTime\":\""+obj[7]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[8]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[9])+"\",");
				result_json.append("\"commAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"runStatus\":"+obj[10]+",");
				result_json.append("\"runStatusName\":\""+runStatusName+"\",");
				result_json.append("\"runTime\":\""+obj[12]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[13]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[14])+"\",");
				result_json.append("\"runAlarmLevel\":"+runAlarmLevel+",");
				result_json.append("\"resultCode\":\""+resultCode+"\",");
				result_json.append("\"resultName\":\""+obj[16]+"\",");
				result_json.append("\"optimizationSuggestion\":\""+obj[17]+"\",");
				result_json.append("\"resultAlarmLevel\":"+resultAlarmLevel+",");
				result_json.append("\""+prodCol.split(",")[0]+"\":\""+obj[18]+"\",");
				result_json.append("\""+prodCol.split(",")[1]+"\":\""+obj[19]+"\",");
				result_json.append("\""+prodCol.split(",")[2]+"\":\""+obj[20]+"\",");
				result_json.append("\""+prodCol.split(",")[3]+"\":\""+obj[21]+"\",");
				
				result_json.append("\"FMax\":\""+obj[22]+"\",");
				result_json.append("\"FMin\":\""+obj[23]+"\",");
				result_json.append("\"fullnessCoefficient\":\""+obj[24]+"\",");
				
				result_json.append("\"averageWatt\":\""+obj[25]+"\",");
				result_json.append("\"polishrodPower\":\""+obj[26]+"\",");
				result_json.append("\"waterPower\":\""+obj[27]+"\",");
				
				result_json.append("\"surfaceSystemEfficiency\":\""+obj[28]+"\",");
				result_json.append("\"welldownSystemEfficiency\":\""+obj[29]+"\",");
				result_json.append("\"systemEfficiency\":\""+obj[30]+"\",");
				result_json.append("\"energyper100mlift\":\""+obj[31]+"\",");
				result_json.append("\"pumpEff\":\""+obj[32]+"\",");
				
				result_json.append("\"iDegreeBalance\":\""+obj[33]+"\",");
				result_json.append("\"wattDegreeBalance\":\""+obj[34]+"\",");
				result_json.append("\"deltaradius\":\""+obj[35]+"\",");
				
				result_json.append("\"levelCorrectValue\":\""+obj[36]+"\",");
				result_json.append("\"inverProducingfluidLevel\":\""+obj[37]+"\",");
				result_json.append("\"todayKWattH\":\""+obj[38]+"\",");
				
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
					String rawValue=obj[39+j]+"";
					String value=rawValue;
					ModbusProtocolConfig.Items item=null;
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col=dataSaveMode==0?("addr"+protocol.getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(k).getTitle()));
							if(col.equalsIgnoreCase(ddicColumnsList.get(j))){
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
	
	public String getDeviceRealTimeOverviewExportData(String orgId,String deviceName,String deviceType,String FESdiagramResultStatValue,String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,Page pager) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int dataSaveMode=1;
		Jedis jedis=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("RPCDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			String tableName="tbl_rpcacqdata_latest";
			String deviceTableName="tbl_rpcdevice";
			String ddicName="realTimeMonitoring_RPCOverview";
			String columnsKey="rpcDeviceAcquisitionItemColumns";
			DataDictionary ddic = null;
			List<String> ddicColumnsList=new ArrayList<String>();
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
			
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			String columns = ddic.getTableHeader();
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+tableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			
			String prodCol="liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol="liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,";
			}
			
			String sql="select t.id,t.wellname,t.videourl,"
					+ "c1.itemname as devicetypename,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽')) as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "t2.resultcode,decode(t2.resultcode,null,'无数据',t3.resultName) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
					+ prodCol+""
					+ "t2.FMax,t2.FMin,t2.fullnessCoefficient,"
					+ "t2.averageWatt,t2.polishrodPower,t2.waterPower,"
					+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
					+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
					+ "t2.systemEfficiency*100 as systemEfficiency,t2.energyper100mlift,"
					+ "t2.pumpEff*100 as pumpEff,"
					+ "t2.iDegreeBalance,t2.wattDegreeBalance,t2.deltaradius*100 as deltaradius,"
					+ "t2.levelCorrectValue,t2.inverProducingfluidLevel,t2.todayKWattH";
			
			String[] ddicColumns=ddic.getSql().split(",");
			for(int i=0;i<ddicColumns.length;i++){
				if(dataSaveMode==0){
					if(StringManagerUtils.existOrNot(loadedAcquisitionItemColumnsMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
						ddicColumnsList.add(ddicColumns[i]);
					}
				}else{
					if(StringManagerUtils.existOrNotByValue(loadedAcquisitionItemColumnsMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
						ddicColumnsList.add(ddicColumns[i]);
					}
				}
			}
			for(int i=0;i<ddicColumnsList.size();i++){
				sql+=",t2."+ddicColumnsList.get(i);
			}
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
					+ " left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode "
					+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.wellName='"+deviceName+"'";
			}
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+=" and decode(t2.resultcode,null,'无数据',t3.resultName)='"+FESdiagramResultStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽'))='"+runStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.wellname";
			
			List<?> list = this.findCallSql(sql);
			result_json.append("[");
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[0]+"";
				
				RPCDeviceInfo rpcDeviceInfo=null;
				if(jedis.hexists("RPCDeviceInfo".getBytes(), deviceId.getBytes())){
					rpcDeviceInfo=(RPCDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("RPCDeviceInfo".getBytes(), deviceId.getBytes()));
				}
				String protocolName="";
				AcqInstanceOwnItem acqInstanceOwnItem=null;
				if(jedis!=null&&rpcDeviceInfo!=null&&jedis.hexists("AcqInstanceOwnItem".getBytes(), rpcDeviceInfo.getInstanceCode().getBytes())){
					acqInstanceOwnItem=(AcqInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AcqInstanceOwnItem".getBytes(), rpcDeviceInfo.getInstanceCode().getBytes()));
					protocolName=acqInstanceOwnItem.getProtocol();
				}
				
				ModbusProtocolConfig.Protocol protocol=null;
				for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
					if(modbusProtocolConfig.getProtocol().get(j).getDeviceType()==StringManagerUtils.stringToInteger(deviceType) 
							&& protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
						protocol=modbusProtocolConfig.getProtocol().get(j);
						break;
					}
				}
				
				result_json.append("{\"id\":"+deviceId+",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"videoUrl\":\""+obj[2]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[3]+"\",");
				result_json.append("\"acqTime\":\""+obj[4]+"\",");
				result_json.append("\"commStatus\":"+obj[5]+",");
				result_json.append("\"commStatusName\":\""+obj[6]+"\",");
				result_json.append("\"commTime\":\""+obj[7]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[8]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[9])+"\",");
				result_json.append("\"runStatus\":"+obj[10]+",");
				result_json.append("\"runStatusName\":\""+obj[11]+"\",");
				result_json.append("\"runTime\":\""+obj[12]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[13]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[14])+"\",");
				result_json.append("\"resultCode\":\""+obj[15]+"\",");
				result_json.append("\"resultName\":\""+obj[16]+"\",");
				result_json.append("\"optimizationSuggestion\":\""+obj[17]+"\",");
				result_json.append("\""+prodCol.split(",")[0]+"\":\""+obj[18]+"\",");
				result_json.append("\""+prodCol.split(",")[1]+"\":\""+obj[19]+"\",");
				result_json.append("\""+prodCol.split(",")[2]+"\":\""+obj[20]+"\",");
				result_json.append("\""+prodCol.split(",")[3]+"\":\""+obj[21]+"\",");
				
				result_json.append("\"FMax\":\""+obj[22]+"\",");
				result_json.append("\"FMin\":\""+obj[23]+"\",");
				result_json.append("\"fullnessCoefficient\":\""+obj[24]+"\",");
				
				result_json.append("\"averageWatt\":\""+obj[25]+"\",");
				result_json.append("\"polishrodPower\":\""+obj[26]+"\",");
				result_json.append("\"waterPower\":\""+obj[27]+"\",");
				
				result_json.append("\"surfaceSystemEfficiency\":\""+obj[28]+"\",");
				result_json.append("\"welldownSystemEfficiency\":\""+obj[29]+"\",");
				result_json.append("\"systemEfficiency\":\""+obj[30]+"\",");
				result_json.append("\"energyper100mlift\":\""+obj[31]+"\",");
				result_json.append("\"pumpEff\":\""+obj[32]+"\",");
				
				result_json.append("\"iDegreeBalance\":\""+obj[33]+"\",");
				result_json.append("\"wattDegreeBalance\":\""+obj[34]+"\",");
				result_json.append("\"deltaradius\":\""+obj[35]+"\",");
				
				result_json.append("\"levelCorrectValue\":\""+obj[36]+"\",");
				result_json.append("\"inverProducingfluidLevel\":\""+obj[37]+"\",");
				result_json.append("\"todayKWattH\":\""+obj[38]+"\",");
				
				for(int j=0;j<ddicColumnsList.size();j++){
					String value=obj[39+j]+"";
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col=dataSaveMode==0?("addr"+protocol.getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(k).getTitle()));
							if(col.equalsIgnoreCase(ddicColumnsList.get(j))){
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
					result_json.append("\""+ddicColumnsList.get(j).replaceAll(" ", "")+"\":\""+value+"\",");
				}
				if(result_json.toString().endsWith(",")){
					result_json.deleteCharAt(result_json.length() - 1);
				}
				result_json.append("},");
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public boolean exportDeviceRealTimeOverviewData(HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceName,String deviceType,
			String FESdiagramResultStatValue,String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,Page pager){
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int dataSaveMode=1;
		Jedis jedis=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("RPCDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
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
			String tableName="tbl_rpcacqdata_latest";
			String deviceTableName="tbl_rpcdevice";
			String ddicName="realTimeMonitoring_RPCOverview";
			String columnsKey="rpcDeviceAcquisitionItemColumns";
			DataDictionary ddic = null;
			List<String> ddicColumnsList=new ArrayList<String>();
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
			
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+tableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			
			String prodCol="liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol="liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,";
			}
			
			String sql="select t.id,t.wellname,t.videourl,"
					+ "c1.itemname as devicetypename,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽')) as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "t2.resultcode,decode(t2.resultcode,null,'无数据',t3.resultName) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
					+ prodCol+""
					+ "t2.FMax,t2.FMin,t2.fullnessCoefficient,"
					+ "t2.averageWatt,t2.polishrodPower,t2.waterPower,"
					+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
					+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
					+ "t2.systemEfficiency*100 as systemEfficiency,t2.energyper100mlift,"
					+ "t2.pumpEff*100 as pumpEff,"
					+ "t2.iDegreeBalance,t2.wattDegreeBalance,t2.deltaradius*100 as deltaradius,"
					+ "t2.levelCorrectValue,t2.inverProducingfluidLevel,t2.todayKWattH";
			
			String[] ddicColumns=ddic.getSql().split(",");
			for(int i=0;i<ddicColumns.length;i++){
				if(dataSaveMode==0){
					if(StringManagerUtils.existOrNot(loadedAcquisitionItemColumnsMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
						ddicColumnsList.add(ddicColumns[i]);
					}
				}else{
					if(StringManagerUtils.existOrNotByValue(loadedAcquisitionItemColumnsMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
						ddicColumnsList.add(ddicColumns[i]);
					}
				}
			}
			for(int i=0;i<ddicColumnsList.size();i++){
				sql+=",t2."+ddicColumnsList.get(i);
			}
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
					+ " left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode "
					+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.wellName='"+deviceName+"'";
			}
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+=" and decode(t2.resultcode,null,'无数据',t3.resultName)='"+FESdiagramResultStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽'))='"+runStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.wellname";
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
				
				RPCDeviceInfo rpcDeviceInfo=null;
				if(jedis.hexists("RPCDeviceInfo".getBytes(), deviceId.getBytes())){
					rpcDeviceInfo=(RPCDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("RPCDeviceInfo".getBytes(), deviceId.getBytes()));
				}
				String protocolName="";
				AcqInstanceOwnItem acqInstanceOwnItem=null;
				if(jedis!=null&&rpcDeviceInfo!=null&&jedis.hexists("AcqInstanceOwnItem".getBytes(), rpcDeviceInfo.getInstanceCode().getBytes())){
					acqInstanceOwnItem=(AcqInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AcqInstanceOwnItem".getBytes(), rpcDeviceInfo.getInstanceCode().getBytes()));
					protocolName=acqInstanceOwnItem.getProtocol();
				}
				
				ModbusProtocolConfig.Protocol protocol=null;
				for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
					if(modbusProtocolConfig.getProtocol().get(j).getDeviceType()==StringManagerUtils.stringToInteger(deviceType) 
							&& protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
						protocol=modbusProtocolConfig.getProtocol().get(j);
						break;
					}
				}
				
				result_json.append("{\"id\":"+(i+1)+",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"videoUrl\":\""+obj[2]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[3]+"\",");
				result_json.append("\"acqTime\":\""+obj[4]+"\",");
				result_json.append("\"commStatus\":"+obj[5]+",");
				result_json.append("\"commStatusName\":\""+obj[6]+"\",");
				result_json.append("\"commTime\":\""+obj[7]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[8]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[9])+"\",");
				result_json.append("\"runStatus\":"+obj[10]+",");
				result_json.append("\"runStatusName\":\""+obj[11]+"\",");
				result_json.append("\"runTime\":\""+obj[12]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[13]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[14])+"\",");
				result_json.append("\"resultCode\":\""+obj[15]+"\",");
				result_json.append("\"resultName\":\""+obj[16]+"\",");
				result_json.append("\"optimizationSuggestion\":\""+obj[17]+"\",");
				result_json.append("\""+prodCol.split(",")[0]+"\":\""+obj[18]+"\",");
				result_json.append("\""+prodCol.split(",")[1]+"\":\""+obj[19]+"\",");
				result_json.append("\""+prodCol.split(",")[2]+"\":\""+obj[20]+"\",");
				result_json.append("\""+prodCol.split(",")[3]+"\":\""+obj[21]+"\",");
				
				result_json.append("\"FMax\":\""+obj[22]+"\",");
				result_json.append("\"FMin\":\""+obj[23]+"\",");
				result_json.append("\"fullnessCoefficient\":\""+obj[24]+"\",");
				
				result_json.append("\"averageWatt\":\""+obj[25]+"\",");
				result_json.append("\"polishrodPower\":\""+obj[26]+"\",");
				result_json.append("\"waterPower\":\""+obj[27]+"\",");
				
				result_json.append("\"surfaceSystemEfficiency\":\""+obj[28]+"\",");
				result_json.append("\"welldownSystemEfficiency\":\""+obj[29]+"\",");
				result_json.append("\"systemEfficiency\":\""+obj[30]+"\",");
				result_json.append("\"energyper100mlift\":\""+obj[31]+"\",");
				result_json.append("\"pumpEff\":\""+obj[32]+"\",");
				
				result_json.append("\"iDegreeBalance\":\""+obj[33]+"\",");
				result_json.append("\"wattDegreeBalance\":\""+obj[34]+"\",");
				result_json.append("\"deltaradius\":\""+obj[35]+"\",");
				
				result_json.append("\"levelCorrectValue\":\""+obj[36]+"\",");
				result_json.append("\"inverProducingfluidLevel\":\""+obj[37]+"\",");
				result_json.append("\"todayKWattH\":\""+obj[38]+"\"");
				
				for(int j=0;j<ddicColumnsList.size();j++){
					String value=obj[39+j]+"";
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col=dataSaveMode==0?("addr"+protocol.getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(k).getTitle()));
							if(col.equalsIgnoreCase(ddicColumnsList.get(j))){
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
					+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
					+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.wellName='"+deviceName+"'";
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽'))='"+runStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.wellname";
			dataPage=this.getDataPage(StringManagerUtils.stringToInteger(deviceId), sql, StringManagerUtils.stringToInteger(limit));
		}catch(Exception e){
			dataPage=1;
		}
		return dataPage;
	}
	
	public String getPCPDeviceRealTimeOverview(String orgId,String deviceName,String deviceType,String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,Page pager) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int dataSaveMode=1;
		Jedis jedis=null;
		AlarmShowStyle alarmShowStyle=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("PCPDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
				}
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
				}
				alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
				
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
			}catch(Exception e){
				e.printStackTrace();
			}
			
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			
			String tableName="tbl_pcpacqdata_latest";
			String deviceTableName="tbl_pcpdevice";
			String ddicName="realTimeMonitoring_PCPOverview";
			String columnsKey="pcpDeviceAcquisitionItemColumns";
			DataDictionary ddic = null;
			List<String> ddicColumnsList=new ArrayList<String>();
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
			
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			String columns = ddic.getTableHeader();
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+tableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			
			String prodCol="liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol="liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,";
			}
			
			String sql="select t.id,t.wellname,t.videourl,"
					+ "c1.itemname as devicetypename,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽')) as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ prodCol+""
					+ "averageWatt,waterPower,"
					+ "systemEfficiency*100 as systemEfficiency,energyper100mlift,pumpEff*100 as pumpEff,"
					+ "todayKWattH";
			String[] ddicColumns=ddic.getSql().split(",");
			for(int i=0;i<ddicColumns.length;i++){
				if(dataSaveMode==0){
					if(StringManagerUtils.existOrNot(loadedAcquisitionItemColumnsMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
						ddicColumnsList.add(ddicColumns[i]);
					}
				}else{
					if(StringManagerUtils.existOrNotByValue(loadedAcquisitionItemColumnsMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
						ddicColumnsList.add(ddicColumns[i]);
					}
				}
			}
			for(int i=0;i<ddicColumnsList.size();i++){
				sql+=",t2."+ddicColumnsList.get(i);
			}
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
					+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.wellName='"+deviceName+"'";
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽'))='"+runStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.wellname";
			
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
				String commStatusName=obj[6]+"";
				String runStatusName=obj[11]+"";
				
				PCPDeviceInfo pcpDeviceInfo=null;
				if(jedis!=null&&jedis.hexists("PCPDeviceInfo".getBytes(), deviceId.getBytes())){
					pcpDeviceInfo=(PCPDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("PCPDeviceInfo".getBytes(), deviceId.getBytes()));
				}
				String protocolName="";
				AcqInstanceOwnItem acqInstanceOwnItem=null;
				if(jedis!=null&&pcpDeviceInfo!=null&&jedis.hexists("AcqInstanceOwnItem".getBytes(), pcpDeviceInfo.getInstanceCode().getBytes())){
					acqInstanceOwnItem=(AcqInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AcqInstanceOwnItem".getBytes(), pcpDeviceInfo.getInstanceCode().getBytes()));
					protocolName=acqInstanceOwnItem.getProtocol();
				}
				DisplayInstanceOwnItem displayInstanceOwnItem=null;
				if(jedis!=null&&pcpDeviceInfo!=null&&jedis.hexists("DisplayInstanceOwnItem".getBytes(), pcpDeviceInfo.getDisplayInstanceCode().getBytes())){
					displayInstanceOwnItem=(DisplayInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("DisplayInstanceOwnItem".getBytes(), pcpDeviceInfo.getDisplayInstanceCode().getBytes()));
				}
				
				AlarmInstanceOwnItem alarmInstanceOwnItem=null;
				if(jedis!=null&&pcpDeviceInfo!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), pcpDeviceInfo.getAlarmInstanceCode().getBytes())){
					alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), pcpDeviceInfo.getAlarmInstanceCode().getBytes()));
				}
				ModbusProtocolConfig.Protocol protocol=null;
				if(modbusProtocolConfig!=null){
					for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
						if(modbusProtocolConfig.getProtocol().get(j).getDeviceType()==StringManagerUtils.stringToInteger(deviceType) 
								&& protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
							protocol=modbusProtocolConfig.getProtocol().get(j);
							break;
						}
					}
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
				
				result_json.append("{\"id\":"+deviceId+",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"videoUrl\":\""+obj[2]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[3]+"\",");
				result_json.append("\"acqTime\":\""+obj[4]+"\",");
				result_json.append("\"commStatus\":"+obj[5]+",");
				result_json.append("\"commStatusName\":\""+commStatusName+"\",");
				result_json.append("\"commTime\":\""+obj[7]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[8]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[9])+"\",");
				result_json.append("\"commAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"runStatus\":"+obj[10]+",");
				result_json.append("\"runStatusName\":\""+runStatusName+"\",");
				result_json.append("\"runTime\":\""+obj[12]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[13]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[14])+"\",");
				result_json.append("\"runAlarmLevel\":"+runAlarmLevel+",");
				result_json.append("\""+prodCol.split(",")[0]+"\":\""+obj[15]+"\",");
				result_json.append("\""+prodCol.split(",")[1]+"\":\""+obj[16]+"\",");
				result_json.append("\""+prodCol.split(",")[2]+"\":\""+obj[17]+"\",");
				result_json.append("\""+prodCol.split(",")[3]+"\":\""+obj[18]+"\",");
				
				result_json.append("\"averageWatt\":\""+obj[19]+"\",");
				result_json.append("\"waterPower\":\""+obj[20]+"\",");
				
				result_json.append("\"systemEfficiency\":\""+obj[21]+"\",");
				result_json.append("\"energyper100mlift\":\""+obj[22]+"\",");
				result_json.append("\"pumpEff\":\""+obj[23]+"\",");
				
				result_json.append("\"todayKWattH\":\""+obj[24]+"\",");
				
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
					String rawValue=obj[25+j]+"";
					String value=rawValue;
					ModbusProtocolConfig.Items item=null;
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col=dataSaveMode==0?("addr"+protocol.getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(k).getTitle()));
							if(col.equalsIgnoreCase(ddicColumnsList.get(j))){
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
	
	public String getPCPDeviceRealTimeOverviewExportData(String orgId,String deviceName,String deviceType,String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,Page pager) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int dataSaveMode=1;
		Jedis jedis=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("RPCDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			String tableName="tbl_pcpacqdata_latest";
			String deviceTableName="tbl_pcpdevice";
			String ddicName="realTimeMonitoring_PCPOverview";
			String columnsKey="pcpDeviceAcquisitionItemColumns";
			DataDictionary ddic = null;
			List<String> ddicColumnsList=new ArrayList<String>();
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
			
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+tableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			
			String prodCol="liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol="liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,";
			}
			
			String sql="select t.id,t.wellname,t.videourl,"
					+ "c1.itemname as devicetypename,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽')) as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ prodCol+""
					+ "averageWatt,waterPower,"
					+ "systemEfficiency*100 as systemEfficiency,energyper100mlift,pumpEff*100 as pumpEff,"
					+ "todayKWattH";
			String[] ddicColumns=ddic.getSql().split(",");
			for(int i=0;i<ddicColumns.length;i++){
				if(dataSaveMode==0){
					if(StringManagerUtils.existOrNot(loadedAcquisitionItemColumnsMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
						ddicColumnsList.add(ddicColumns[i]);
					}
				}else{
					if(StringManagerUtils.existOrNotByValue(loadedAcquisitionItemColumnsMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
						ddicColumnsList.add(ddicColumns[i]);
					}
				}
			}
			for(int i=0;i<ddicColumnsList.size();i++){
				sql+=",t2."+ddicColumnsList.get(i);
			}
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
					+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.wellName='"+deviceName+"'";
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽'))='"+runStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.wellname";
			
			List<?> list = this.findCallSql(sql);
			result_json.append("[");
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[0]+"";
				
				PCPDeviceInfo pcpDeviceInfo=null;
				if(jedis.hexists("PCPDeviceInfo".getBytes(), deviceId.getBytes())){
					pcpDeviceInfo=(PCPDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("PCPDeviceInfo".getBytes(), deviceId.getBytes()));
				}
				String protocolName="";
				AcqInstanceOwnItem acqInstanceOwnItem=null;
				if(jedis!=null&&pcpDeviceInfo!=null&&jedis.hexists("AcqInstanceOwnItem".getBytes(), pcpDeviceInfo.getInstanceCode().getBytes())){
					acqInstanceOwnItem=(AcqInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AcqInstanceOwnItem".getBytes(), pcpDeviceInfo.getInstanceCode().getBytes()));
					protocolName=acqInstanceOwnItem.getProtocol();
				}
				
				ModbusProtocolConfig.Protocol protocol=null;
				for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
					if(modbusProtocolConfig.getProtocol().get(j).getDeviceType()==StringManagerUtils.stringToInteger(deviceType) 
							&& protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
						protocol=modbusProtocolConfig.getProtocol().get(j);
						break;
					}
				}
				
				result_json.append("{\"id\":"+deviceId+",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"videoUrl\":\""+obj[2]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[3]+"\",");
				result_json.append("\"acqTime\":\""+obj[4]+"\",");
				result_json.append("\"commStatus\":"+obj[5]+",");
				result_json.append("\"commStatusName\":\""+obj[6]+"\",");
				result_json.append("\"commTime\":\""+obj[7]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[8]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[9])+"\",");
				result_json.append("\"runStatus\":"+obj[10]+",");
				result_json.append("\"runStatusName\":\""+obj[11]+"\",");
				result_json.append("\"runTime\":\""+obj[12]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[13]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[14])+"\",");
				result_json.append("\""+prodCol.split(",")[0]+"\":\""+obj[15]+"\",");
				result_json.append("\""+prodCol.split(",")[1]+"\":\""+obj[16]+"\",");
				result_json.append("\""+prodCol.split(",")[2]+"\":\""+obj[17]+"\",");
				result_json.append("\""+prodCol.split(",")[3]+"\":\""+obj[18]+"\",");
				
				result_json.append("\"averageWatt\":\""+obj[19]+"\",");
				result_json.append("\"waterPower\":\""+obj[20]+"\",");
				
				result_json.append("\"systemEfficiency\":\""+obj[21]+"\",");
				result_json.append("\"energyper100mlift\":\""+obj[22]+"\",");
				result_json.append("\"pumpEff\":\""+obj[23]+"\",");
				
				result_json.append("\"todayKWattH\":\""+obj[24]+"\",");
				for(int j=0;j<ddicColumnsList.size();j++){
					String value=obj[25+j]+"";
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col=dataSaveMode==0?("addr"+protocol.getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(k).getTitle()));
							if(col.equalsIgnoreCase(ddicColumnsList.get(j))){
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
					result_json.append("\""+ddicColumnsList.get(j).replaceAll(" ", "")+"\":\""+value+"\",");
				}
				if(result_json.toString().endsWith(",")){
					result_json.deleteCharAt(result_json.length() - 1);
				}
				result_json.append("},");
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public boolean exportPCPDeviceRealTimeOverviewData(HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceName,String deviceType,
			String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,Page pager){
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int dataSaveMode=1;
		Jedis jedis=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("RPCDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
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
			String tableName="tbl_pcpacqdata_latest";
			String deviceTableName="tbl_pcpdevice";
			String ddicName="realTimeMonitoring_PCPOverview";
			String columnsKey="pcpDeviceAcquisitionItemColumns";
			DataDictionary ddic = null;
			List<String> ddicColumnsList=new ArrayList<String>();
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
			
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+tableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			
			String prodCol="liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol="liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,";
			}
			
			String sql="select t.id,t.wellname,t.videourl,"
					+ "c1.itemname as devicetypename,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽')) as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ prodCol+""
					+ "averageWatt,waterPower,"
					+ "systemEfficiency*100 as systemEfficiency,energyper100mlift,pumpEff*100 as pumpEff,"
					+ "todayKWattH";
			String[] ddicColumns=ddic.getSql().split(",");
			for(int i=0;i<ddicColumns.length;i++){
				if(dataSaveMode==0){
					if(StringManagerUtils.existOrNot(loadedAcquisitionItemColumnsMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
						ddicColumnsList.add(ddicColumns[i]);
					}
				}else{
					if(StringManagerUtils.existOrNotByValue(loadedAcquisitionItemColumnsMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
						ddicColumnsList.add(ddicColumns[i]);
					}
				}
			}
			for(int i=0;i<ddicColumnsList.size();i++){
				sql+=",t2."+ddicColumnsList.get(i);
			}
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
					+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.wellName='"+deviceName+"'";
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽'))='"+runStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
				sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
			}
			sql+=" order by t.sortnum,t.wellname";
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
				
				PCPDeviceInfo pcpDeviceInfo=null;
				if(jedis.hexists("PCPDeviceInfo".getBytes(), deviceId.getBytes())){
					pcpDeviceInfo=(PCPDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("PCPDeviceInfo".getBytes(), deviceId.getBytes()));
				}
				String protocolName="";
				AcqInstanceOwnItem acqInstanceOwnItem=null;
				if(jedis!=null&&pcpDeviceInfo!=null&&jedis.hexists("AcqInstanceOwnItem".getBytes(), pcpDeviceInfo.getInstanceCode().getBytes())){
					acqInstanceOwnItem=(AcqInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AcqInstanceOwnItem".getBytes(), pcpDeviceInfo.getInstanceCode().getBytes()));
					protocolName=acqInstanceOwnItem.getProtocol();
				}
				
				ModbusProtocolConfig.Protocol protocol=null;
				for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
					if(modbusProtocolConfig.getProtocol().get(j).getDeviceType()==StringManagerUtils.stringToInteger(deviceType) 
							&& protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
						protocol=modbusProtocolConfig.getProtocol().get(j);
						break;
					}
				}
				
				result_json.append("{\"id\":"+(i+1)+",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"videoUrl\":\""+obj[2]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[3]+"\",");
				result_json.append("\"acqTime\":\""+obj[4]+"\",");
				result_json.append("\"commStatus\":"+obj[5]+",");
				result_json.append("\"commStatusName\":\""+obj[6]+"\",");
				result_json.append("\"commTime\":\""+obj[7]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[8]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[9])+"\",");
				result_json.append("\"runStatus\":"+obj[10]+",");
				result_json.append("\"runStatusName\":\""+obj[11]+"\",");
				result_json.append("\"runTime\":\""+obj[12]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[13]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[14])+"\",");
				result_json.append("\""+prodCol.split(",")[0]+"\":\""+obj[15]+"\",");
				result_json.append("\""+prodCol.split(",")[1]+"\":\""+obj[16]+"\",");
				result_json.append("\""+prodCol.split(",")[2]+"\":\""+obj[17]+"\",");
				result_json.append("\""+prodCol.split(",")[3]+"\":\""+obj[18]+"\",");
				
				result_json.append("\"averageWatt\":\""+obj[19]+"\",");
				result_json.append("\"waterPower\":\""+obj[20]+"\",");
				
				result_json.append("\"systemEfficiency\":\""+obj[21]+"\",");
				result_json.append("\"energyper100mlift\":\""+obj[22]+"\",");
				result_json.append("\"pumpEff\":\""+obj[23]+"\",");
				
				result_json.append("\"todayKWattH\":\""+obj[24]+"\"");
				for(int j=0;j<ddicColumnsList.size();j++){
					String value=obj[25+j]+"";
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col=dataSaveMode==0?("addr"+protocol.getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(k).getTitle()));
							if(col.equalsIgnoreCase(ddicColumnsList.get(j))){
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

	public String getDeviceRealTimeMonitoringData(String deviceId,String deviceName,String deviceType,int userNo) throws IOException, SQLException{
		int items=3;
		StringBuffer result_json = new StringBuffer();
		StringBuffer info_json = new StringBuffer();
		int dataSaveMode=1;
		Jedis jedis = null;
		AlarmShowStyle alarmShowStyle=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		AlarmInstanceOwnItem alarmInstanceOwnItem=null;
		List<byte[]> calItemSet=null;
		UserInfo userInfo=null;
		String tableName="tbl_rpcacqdata_latest";
		String deviceTableName="tbl_rpcdevice";
		String columnsKey="rpcDeviceAcquisitionItemColumns";
		String deviceInfoKey="RPCDeviceInfo";
		String calItemsKey="rpcCalItemList";
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pcpacqdata_latest";
			deviceTableName="tbl_pcpdevice";
			columnsKey="pcpDeviceAcquisitionItemColumns";
			deviceInfoKey="PCPDeviceInfo";
			calItemsKey="pcpCalItemList";
		}
		String displayInstanceCode="";
		String alarmInstanceCode="";
		
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(StringManagerUtils.stringToInteger(deviceType)==0){
					if(!jedis.exists(deviceInfoKey.getBytes())){
						MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
					}
					if(jedis.hexists(deviceInfoKey.getBytes(), deviceId.getBytes())){
						RPCDeviceInfo rpcDeviceInfo=(RPCDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget(deviceInfoKey.getBytes(), deviceId.getBytes()));
						displayInstanceCode=rpcDeviceInfo.getDisplayInstanceCode();
						alarmInstanceCode=rpcDeviceInfo.getAlarmInstanceCode();
					}
				}else{
					if(!jedis.exists(deviceInfoKey.getBytes())){
						MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
					}
					if(jedis.hexists(deviceInfoKey.getBytes(), deviceId.getBytes())){
						PCPDeviceInfo pcpDeviceInfo=(PCPDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget(deviceInfoKey.getBytes(), deviceId.getBytes()));
						displayInstanceCode=pcpDeviceInfo.getDisplayInstanceCode();
						alarmInstanceCode=pcpDeviceInfo.getAlarmInstanceCode();
					}
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
				
				if(!jedis.exists(calItemsKey.getBytes())){
					if(StringManagerUtils.stringToInteger(deviceType)==0){
						MemoryDataManagerTask.loadRPCCalculateItem();
					}else{
						MemoryDataManagerTask.loadPCPCalculateItem();
					}
				}
				calItemSet= jedis.zrange(calItemsKey.getBytes(), 0, -1);
				
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
			
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
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
					List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
					WorkType workType=null;
					for(int j=0;j<protocol.getItems().size();j++){
						if((!"w".equalsIgnoreCase(protocol.getItems().get(j).getRWType())) 
								&& (StringManagerUtils.existDisplayItem(displayInstanceOwnItem.getItemList(), protocol.getItems().get(j).getTitle(), false))){
							for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
								if(displayInstanceOwnItem.getItemList().get(k).getType()==0 
										&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
										&& protocol.getItems().get(j).getTitle().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemName())
										&& StringManagerUtils.existOrNot(tableColumnsList, dataSaveMode==0?("addr"+protocol.getItems().get(j).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(j).getTitle())), false)
										){
									protocolItems.add(protocol.getItems().get(j));
									break;
								}
							}
						}
					}
					
					//计算项
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
					String sql="select t.id,t.wellname,to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'), t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,decode(t2.commstatus,1,0,100) as commAlarmLevel ";
					for(int j=0;j<protocolItems.size();j++){
						String col=dataSaveMode==0?("addr"+protocolItems.get(j).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocolItems.get(j).getTitle()));
						sql+=",t2."+col;
					}

					for(int i=0;i<calItemList.size();i++){
						String column=calItemList.get(i).getCode();
						if("resultName".equalsIgnoreCase(calItemList.get(i).getCode())){
							column="resultCode";
						}
						sql+=",t2."+column;
					}
					sql+= " from "+deviceTableName+" t "
							+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
							+ " where  t.id="+deviceId;
					List<?> list = this.findCallSql(sql);
					if(list.size()>0){
						int row=1;
						Object[] obj=(Object[]) list.get(0);
						
						for(int i=0;i<calItemList.size();i++){
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
						
						for(int j=0;j<protocolItems.size();j++){
							String columnName=protocolItems.get(j).getTitle();
							String rawColumnName=columnName;
							String value=obj[j+6]+"";
							String rawValue=value;
							String addr=protocolItems.get(j).getAddr()+"";
							String column=dataSaveMode==0?("addr"+protocolItems.get(j).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocolItems.get(j).getTitle()));
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
	              
	public String getRPCDeviceControlandInfoData(String deviceId,String wellName,String deviceType,User user)throws Exception {
		StringBuffer result_json = new StringBuffer();
		int dataSaveMode=1;
		String deviceTableName="tbl_rpcdevice";
		String columnsKey="rpcDeviceAcquisitionItemColumns";
		String deviceInfoKey="RPCDeviceInfo";
		DataDictionary ddic=dataitemsInfoService.findTableSqlWhereByListFaceId("realTimeMonitoring_RPCDeviceInfo");
		
		List<String> heads=ddic.getHeaders();
		List<String> fields=ddic.getFields();
		
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
			EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
		}
		Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
		
		Jedis jedis=null;
		RPCDeviceInfo deviceInfo=null;
		UserInfo userInfo=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		String protocolName="";
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists(deviceInfoKey.getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
				}
				byte[] dviceInfoByte =jedis.hget(deviceInfoKey.getBytes(),deviceId.getBytes());
				Object obj =SerializeObjectUnils.unserizlize(dviceInfoByte);
				if (obj instanceof RPCDeviceInfo) {
					deviceInfo=(RPCDeviceInfo)obj;
				}

				if(!jedis.exists("UserInfo".getBytes())){
					MemoryDataManagerTask.loadUserInfo(null,0,"update");
				}
				userInfo=(UserInfo) SerializeObjectUnils.unserizlize(jedis.hget("UserInfo".getBytes(), (user.getUserNo()+"").getBytes()));
				
				if(!jedis.exists("DisplayInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadDisplayInstanceOwnItemById("","update");
				}
				if(jedis!=null&&deviceInfo!=null&&jedis.hexists("DisplayInstanceOwnItem".getBytes(), deviceInfo.getDisplayInstanceCode().getBytes())){
					displayInstanceOwnItem=(DisplayInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("DisplayInstanceOwnItem".getBytes(), deviceInfo.getDisplayInstanceCode().getBytes()));
					protocolName=displayInstanceOwnItem.getProtocol();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			int isControl=(userInfo!=null?userInfo.getRoleFlag():0);
			
			List<String> controlItems=new ArrayList<String>();
			List<String> controlColumns=new ArrayList<String>();
			List<Integer> controlItemResolutionMode=new ArrayList<Integer>();
			List<String> controlItemMeaningList=new ArrayList<String>();
			StringBuffer deviceInfoDataList=new StringBuffer();
			StringBuffer deviceControlList=new StringBuffer();
			deviceInfoDataList.append("[");
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
												String col=dataSaveMode==0?("ADDR"+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()));
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
			
			//设备信息
			for(int i=0;i<fields.size();i++){
				if("manufacturer".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getManufacturer():"")+"\"},");
				}else if("model".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getModel():"")+"\"},");
				}else if("stroke".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getStroke():"")+"\"},");
				}else if("crankRotationDirection".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?("Clockwise".equalsIgnoreCase(deviceInfo.getPumpingUnit().getCrankRotationDirection())?"顺时针":"逆时针"):"")+"\"},");
				}else if("offsetAngleOfCrank".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getOffsetAngleOfCrank():"")+"\"},");
				}else if("crankGravityRadius".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getCrankGravityRadius():"")+"\"},");
				}else if("singleCrankWeight".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getSingleCrankWeight():"")+"\"},");
				}else if("singleCrankPinWeight".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getSingleCrankPinWeight():"")+"\"},");
				}else if("structuralUnbalance".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getStructuralUnbalance():"")+"\"},");
				}else if("balance".equalsIgnoreCase(fields.get(i))){
					if(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null&&deviceInfo.getPumpingUnit().getBalance()!=null&&deviceInfo.getPumpingUnit().getBalance().getEveryBalance()!=null&&deviceInfo.getPumpingUnit().getBalance().getEveryBalance().size()>0){
						for(int j=0;j<deviceInfo.getPumpingUnit().getBalance().getEveryBalance().size();j++){
							deviceInfoDataList.append("{\"name\":\"平衡块"+(j+1)+"位置重量\","+ "\"value\":\""+(deviceInfo.getPumpingUnit().getBalance().getEveryBalance().get(j).getPosition()+","+deviceInfo.getPumpingUnit().getBalance().getEveryBalance().get(j).getWeight())+"\"},");
						}
					}else{
						deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\"\"},");
					}
				}else if("crudeOilDensity".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getCrudeOilDensity():"")+"\"},");
				}else if("waterDensity".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getWaterDensity():"")+"\"},");
				}else if("naturalGasRelativeDensity".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getNaturalGasRelativeDensity():"")+"\"},");
				}else if("saturationPressure".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getSaturationPressure():"")+"\"},");
				}else if("reservoirDepth".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getReservoir()!=null?deviceInfo.getReservoir().getDepth():"")+"\"},");
				}else if("reservoirTemperature".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getReservoir()!=null?deviceInfo.getReservoir().getTemperature():"")+"\"},");
				}else if("tubingPressure".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getTubingPressure():"")+"\"},");
				}else if("casingPressure".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getCasingPressure():"")+"\"},");
				}else if("wellHeadTemperature".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getWellHeadTemperature():"")+"\"},");
				}else if("waterCut".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getWaterCut():"")+"\"},");
				}else if("productionGasOilRatio".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getProductionGasOilRatio():"")+"\"},");
				}else if("producingfluidLevel".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getProducingfluidLevel():"")+"\"},");
				}else if("pumpSettingDepth".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getPumpSettingDepth():"")+"\"},");
				}else if("barrelType".equalsIgnoreCase(fields.get(i))){
					String barrelType="";
					if(deviceInfo!=null&&deviceInfo.getPump()!=null&&deviceInfo.getPump().getBarrelType()!=null){
						if("L".equalsIgnoreCase(deviceInfo.getPump().getBarrelType())){
							barrelType="组合泵";
						}else if("H".equalsIgnoreCase(deviceInfo.getPump().getBarrelType())){
							barrelType="整筒泵";
						}
					}
					deviceInfoDataList.append("{\"name\":\"泵筒类型\","+ "\"value\":\""+barrelType+"\"},");
				}else if("pumpGrade".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPump()!=null?deviceInfo.getPump().getPumpGrade():"")+"\"},");
				}else if("pumpBoreDiameter".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPump()!=null?deviceInfo.getPump().getPumpBoreDiameter()*1000:"")+"\"},");
				}else if("plungerLength".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPump()!=null?deviceInfo.getPump().getPlungerLength():"")+"\"},");
				}else if("tubingStringInsideDiameter".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getTubingString()!=null&&deviceInfo.getTubingString().getEveryTubing()!=null&&deviceInfo.getTubingString().getEveryTubing().size()>0?deviceInfo.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000:"")+"\"},");
				}else if("casingStringInsideDiameter".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getCasingString()!=null&&deviceInfo.getCasingString().getEveryCasing()!=null&&deviceInfo.getCasingString().getEveryCasing().size()>0?deviceInfo.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000:"")+"\"},");
				}else if("rodString".equalsIgnoreCase(fields.get(i))){
					String rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="";
					String rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="";
					String rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="";
					String rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="";
					if(deviceInfo!=null&&deviceInfo.getRodString()!=null&&deviceInfo.getRodString().getEveryRod()!=null&&deviceInfo.getRodString().getEveryRod().size()>0){
						if(deviceInfo.getRodString().getEveryRod().size()>0){
							rodGrade1=deviceInfo.getRodString().getEveryRod().get(0).getGrade();
							rodOutsideDiameter1=deviceInfo.getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
							rodInsideDiameter1=deviceInfo.getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
							rodLength1=deviceInfo.getRodString().getEveryRod().get(0).getLength()+"";
						}
						if(deviceInfo.getRodString().getEveryRod().size()>1){
							rodGrade2=deviceInfo.getRodString().getEveryRod().get(1).getGrade();
							rodOutsideDiameter2=deviceInfo.getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
							rodInsideDiameter2=deviceInfo.getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
							rodLength2=deviceInfo.getRodString().getEveryRod().get(1).getLength()+"";
						}
						if(deviceInfo.getRodString().getEveryRod().size()>2){
							rodGrade3=deviceInfo.getRodString().getEveryRod().get(2).getGrade();
							rodOutsideDiameter3=deviceInfo.getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
							rodInsideDiameter3=deviceInfo.getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
							rodLength3=deviceInfo.getRodString().getEveryRod().get(2).getLength()+"";
						}
						if(deviceInfo.getRodString().getEveryRod().size()>3){
							rodGrade4=deviceInfo.getRodString().getEveryRod().get(3).getGrade();
							rodOutsideDiameter4=deviceInfo.getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
							rodInsideDiameter4=deviceInfo.getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
							rodLength4=deviceInfo.getRodString().getEveryRod().get(3).getLength()+"";
						}
					}
					
					deviceInfoDataList.append("{\"name\":\"一级杆级别\",\"value\":\""+rodGrade1+"\"},");
					deviceInfoDataList.append("{\"name\":\"一级杆外径(mm)\",\"value\":\""+rodOutsideDiameter1+"\"},");
					deviceInfoDataList.append("{\"name\":\"一级杆内径(mm)\",\"value\":\""+rodInsideDiameter1+"\"},");
					deviceInfoDataList.append("{\"name\":\"一级杆长度(m)\",\"value\":\""+rodLength1+"\"},");
					
					deviceInfoDataList.append("{\"name\":\"二级杆级别\",\"value\":\""+rodGrade2+"\"},");
					deviceInfoDataList.append("{\"name\":\"二级杆外径(mm)\",\"value\":\""+rodOutsideDiameter2+"\"},");
					deviceInfoDataList.append("{\"name\":\"二级杆内径(mm)\",\"value\":\""+rodInsideDiameter2+"\"},");
					deviceInfoDataList.append("{\"name\":\"二级杆长度(m)\",\"value\":\""+rodLength2+"\"},");
					
					deviceInfoDataList.append("{\"name\":\"三级杆级别\",\"value\":\""+rodGrade3+"\"},");
					deviceInfoDataList.append("{\"name\":\"三级杆外径(mm)\",\"value\":\""+rodOutsideDiameter3+"\"},");
					deviceInfoDataList.append("{\"name\":\"三级杆内径(mm)\",\"value\":\""+rodInsideDiameter3+"\"},");
					deviceInfoDataList.append("{\"name\":\"三级杆长度(m)\",\"value\":\""+rodLength3+"\"},");
					
					deviceInfoDataList.append("{\"name\":\"四级杆级别\",\"value\":\""+rodGrade4+"\"},");
					deviceInfoDataList.append("{\"name\":\"四级杆外径(mm)\",\"value\":\""+rodOutsideDiameter4+"\"},");
					deviceInfoDataList.append("{\"name\":\"四级杆内径(mm)\",\"value\":\""+rodInsideDiameter4+"\"},");
				}
				
			}
			
			if(deviceInfoDataList.toString().endsWith(",")){
				deviceInfoDataList.deleteCharAt(deviceInfoDataList.length() - 1);
			}
			
			String tableName="tbl_rpcacqdata_latest";
			String sql="select t2.commStatus ";
			if(StringManagerUtils.stringToInteger(deviceType)>0){
				tableName="tbl_pcpacqdata_latest";
			}
			sql+= " from "+deviceTableName+" t,"+tableName+" t2 where t.id=t2.wellid and t.id="+deviceId;
			
			result_json.append("{ \"success\":true,\"isControl\":"+isControl+",");
			List<?> list = this.findCallSql(sql);
			if(list.size()>0){
				if(controlColumns.size()>0){
					result_json.append("\"commStatus\":\""+list.get(0)+"\",");
					for(int i=0;i<controlColumns.size();i++){
						deviceControlList.append("{\"title\":\""+controlItems.get(i)+"\",\"name\":\""+controlColumns.get(i)+"\",\"resolutionMode\":"+controlItemResolutionMode.get(i)+",\"value\":\"\",\"itemMeaning\":\""+controlItemMeaningList.get(i)+"\"},");
					}
					if(deviceControlList.toString().endsWith(",")){
						deviceControlList.deleteCharAt(deviceControlList.length() - 1);
					}
				}else{
					result_json.append("\"commStatus\":\""+list.get(0)+"\",");
				}
			}
			deviceInfoDataList.append("]");
			deviceControlList.append("]");
			result_json.append("\"videoUrl\":\""+(deviceInfo!=null?deviceInfo.getVideoUrl():"")+"\",");
			result_json.append("\"deviceInfoDataList\":"+deviceInfoDataList+",");
			result_json.append("\"deviceControlList\":"+deviceControlList);
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
	
	public String getRPCDeviceInfoData(String deviceId,String wellName,String deviceType,User user)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String deviceInfoKey="RPCDeviceInfo";
		DataDictionary ddic=dataitemsInfoService.findTableSqlWhereByListFaceId("realTimeMonitoring_RPCDeviceInfo");
		
		List<String> heads=ddic.getHeaders();
		List<String> fields=ddic.getFields();
		
		Jedis jedis=null;
		RPCDeviceInfo deviceInfo=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists(deviceInfoKey.getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
				}
				byte[] dviceInfoByte =jedis.hget(deviceInfoKey.getBytes(),deviceId.getBytes());
				Object obj =SerializeObjectUnils.unserizlize(dviceInfoByte);
				if (obj instanceof RPCDeviceInfo) {
					deviceInfo=(RPCDeviceInfo)obj;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			StringBuffer deviceInfoDataList=new StringBuffer();
			deviceInfoDataList.append("[");
			
			//设备信息
			for(int i=0;i<fields.size();i++){
				if("manufacturer".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getManufacturer():"")+"\"},");
				}else if("model".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getModel():"")+"\"},");
				}else if("stroke".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getStroke():"")+"\"},");
				}else if("crankRotationDirection".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?("Clockwise".equalsIgnoreCase(deviceInfo.getPumpingUnit().getCrankRotationDirection())?"顺时针":"逆时针"):"")+"\"},");
				}else if("offsetAngleOfCrank".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getOffsetAngleOfCrank():"")+"\"},");
				}else if("crankGravityRadius".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getCrankGravityRadius():"")+"\"},");
				}else if("singleCrankWeight".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getSingleCrankWeight():"")+"\"},");
				}else if("singleCrankPinWeight".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getSingleCrankPinWeight():"")+"\"},");
				}else if("structuralUnbalance".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null?deviceInfo.getPumpingUnit().getStructuralUnbalance():"")+"\"},");
				}else if("balance".equalsIgnoreCase(fields.get(i))){
					if(deviceInfo!=null&&deviceInfo.getPumpingUnit()!=null&&deviceInfo.getPumpingUnit().getBalance()!=null&&deviceInfo.getPumpingUnit().getBalance().getEveryBalance()!=null&&deviceInfo.getPumpingUnit().getBalance().getEveryBalance().size()>0){
						for(int j=0;j<deviceInfo.getPumpingUnit().getBalance().getEveryBalance().size();j++){
							deviceInfoDataList.append("{\"item\":\"平衡块"+(j+1)+"位置重量\","+ "\"value\":\""+(deviceInfo.getPumpingUnit().getBalance().getEveryBalance().get(j).getPosition()+","+deviceInfo.getPumpingUnit().getBalance().getEveryBalance().get(j).getWeight())+"\"},");
						}
					}else{
						deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\"\"},");
					}
				}else if("crudeOilDensity".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getCrudeOilDensity():"")+"\"},");
				}else if("waterDensity".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getWaterDensity():"")+"\"},");
				}else if("naturalGasRelativeDensity".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getNaturalGasRelativeDensity():"")+"\"},");
				}else if("saturationPressure".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getSaturationPressure():"")+"\"},");
				}else if("reservoirDepth".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getReservoir()!=null?deviceInfo.getReservoir().getDepth():"")+"\"},");
				}else if("reservoirTemperature".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getReservoir()!=null?deviceInfo.getReservoir().getTemperature():"")+"\"},");
				}else if("tubingPressure".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getTubingPressure():"")+"\"},");
				}else if("casingPressure".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getCasingPressure():"")+"\"},");
				}else if("wellHeadTemperature".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getWellHeadTemperature():"")+"\"},");
				}else if("waterCut".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getWaterCut():"")+"\"},");
				}else if("productionGasOilRatio".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getProductionGasOilRatio():"")+"\"},");
				}else if("producingfluidLevel".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getProducingfluidLevel():"")+"\"},");
				}else if("pumpSettingDepth".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getPumpSettingDepth():"")+"\"},");
				}else if("barrelType".equalsIgnoreCase(fields.get(i))){
					String barrelType="";
					if(deviceInfo!=null&&deviceInfo.getPump()!=null&&deviceInfo.getPump().getBarrelType()!=null){
						if("L".equalsIgnoreCase(deviceInfo.getPump().getBarrelType())){
							barrelType="组合泵";
						}else if("H".equalsIgnoreCase(deviceInfo.getPump().getBarrelType())){
							barrelType="整筒泵";
						}
					}
					deviceInfoDataList.append("{\"item\":\"泵筒类型\","+ "\"value\":\""+barrelType+"\"},");
				}else if("pumpGrade".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPump()!=null?deviceInfo.getPump().getPumpGrade():"")+"\"},");
				}else if("pumpBoreDiameter".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPump()!=null?deviceInfo.getPump().getPumpBoreDiameter()*1000:"")+"\"},");
				}else if("plungerLength".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPump()!=null?deviceInfo.getPump().getPlungerLength():"")+"\"},");
				}else if("tubingStringInsideDiameter".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getTubingString()!=null&&deviceInfo.getTubingString().getEveryTubing()!=null&&deviceInfo.getTubingString().getEveryTubing().size()>0?deviceInfo.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000:"")+"\"},");
				}else if("casingStringInsideDiameter".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getCasingString()!=null&&deviceInfo.getCasingString().getEveryCasing()!=null&&deviceInfo.getCasingString().getEveryCasing().size()>0?deviceInfo.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000:"")+"\"},");
				}else if("rodString".equalsIgnoreCase(fields.get(i))){
					String rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="";
					String rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="";
					String rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="";
					String rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="";
					if(deviceInfo!=null&&deviceInfo.getRodString()!=null&&deviceInfo.getRodString().getEveryRod()!=null&&deviceInfo.getRodString().getEveryRod().size()>0){
						if(deviceInfo.getRodString().getEveryRod().size()>0){
							rodGrade1=deviceInfo.getRodString().getEveryRod().get(0).getGrade();
							rodOutsideDiameter1=deviceInfo.getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
							rodInsideDiameter1=deviceInfo.getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
							rodLength1=deviceInfo.getRodString().getEveryRod().get(0).getLength()+"";
						}
						if(deviceInfo.getRodString().getEveryRod().size()>1){
							rodGrade2=deviceInfo.getRodString().getEveryRod().get(1).getGrade();
							rodOutsideDiameter2=deviceInfo.getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
							rodInsideDiameter2=deviceInfo.getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
							rodLength2=deviceInfo.getRodString().getEveryRod().get(1).getLength()+"";
						}
						if(deviceInfo.getRodString().getEveryRod().size()>2){
							rodGrade3=deviceInfo.getRodString().getEveryRod().get(2).getGrade();
							rodOutsideDiameter3=deviceInfo.getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
							rodInsideDiameter3=deviceInfo.getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
							rodLength3=deviceInfo.getRodString().getEveryRod().get(2).getLength()+"";
						}
						if(deviceInfo.getRodString().getEveryRod().size()>3){
							rodGrade4=deviceInfo.getRodString().getEveryRod().get(3).getGrade();
							rodOutsideDiameter4=deviceInfo.getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
							rodInsideDiameter4=deviceInfo.getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
							rodLength4=deviceInfo.getRodString().getEveryRod().get(3).getLength()+"";
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
	
	public String getRPCDeviceControlData(String deviceId,String wellName,String deviceType,User user)throws Exception {
		StringBuffer result_json = new StringBuffer();
		int dataSaveMode=1;
		String deviceTableName="tbl_rpcdevice";
		String columnsKey="rpcDeviceAcquisitionItemColumns";
		String deviceInfoKey="RPCDeviceInfo";
		
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
			EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
		}
		Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
		
		Jedis jedis=null;
		RPCDeviceInfo deviceInfo=null;
		UserInfo userInfo=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		String protocolName="";
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists(deviceInfoKey.getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
				}
				byte[] dviceInfoByte =jedis.hget(deviceInfoKey.getBytes(),deviceId.getBytes());
				Object obj =SerializeObjectUnils.unserizlize(dviceInfoByte);
				if (obj instanceof RPCDeviceInfo) {
					deviceInfo=(RPCDeviceInfo)obj;
				}

				if(!jedis.exists("UserInfo".getBytes())){
					MemoryDataManagerTask.loadUserInfo(null,0,"update");
				}
				userInfo=(UserInfo) SerializeObjectUnils.unserizlize(jedis.hget("UserInfo".getBytes(), (user.getUserNo()+"").getBytes()));
				
				if(!jedis.exists("DisplayInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadDisplayInstanceOwnItemById("","update");
				}
				if(jedis!=null&&deviceInfo!=null&&jedis.hexists("DisplayInstanceOwnItem".getBytes(), deviceInfo.getDisplayInstanceCode().getBytes())){
					displayInstanceOwnItem=(DisplayInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("DisplayInstanceOwnItem".getBytes(), deviceInfo.getDisplayInstanceCode().getBytes()));
					protocolName=displayInstanceOwnItem.getProtocol();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			int isControl=(userInfo!=null?userInfo.getRoleFlag():0);
			
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
												String col=dataSaveMode==0?("ADDR"+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()));
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
			
			String tableName="tbl_rpcacqdata_latest";
			String sql="select t2.commStatus ";
			if(StringManagerUtils.stringToInteger(deviceType)>0){
				tableName="tbl_pcpacqdata_latest";
			}
			sql+= " from "+deviceTableName+" t,"+tableName+" t2 where t.id=t2.wellid and t.id="+deviceId;
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
	
	public String getPCPDeviceControlandInfoData(String deviceId,String wellName,String deviceType,User user)throws Exception {
		StringBuffer result_json = new StringBuffer();
		int dataSaveMode=1;
		String deviceTableName="tbl_pcpdevice";
		String columnsKey="pcpDeviceAcquisitionItemColumns";
		String deviceInfoKey="PCPDeviceInfo";
		DataDictionary ddic=dataitemsInfoService.findTableSqlWhereByListFaceId("realTimeMonitoring_PCPDeviceInfo");
		List<String> heads=ddic.getHeaders();
		List<String> fields=ddic.getFields();
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
			EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
		}
		Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
		
		Jedis jedis=null;
		PCPDeviceInfo deviceInfo=null;
		UserInfo userInfo=null;

		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		String protocolName="";
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists(deviceInfoKey.getBytes())){
					MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
					
				}
				byte[] dviceInfoByte =jedis.hget(deviceInfoKey.getBytes(),deviceId.getBytes());
				Object obj =SerializeObjectUnils.unserizlize(dviceInfoByte);
				if (obj instanceof PCPDeviceInfo) {
					deviceInfo=(PCPDeviceInfo)obj;
				}

				if(!jedis.exists("UserInfo".getBytes())){
					MemoryDataManagerTask.loadUserInfo(null,0,"update");
				}
				userInfo=(UserInfo) SerializeObjectUnils.unserizlize(jedis.hget("UserInfo".getBytes(), (user.getUserNo()+"").getBytes()));
				
				if(!jedis.exists("DisplayInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadDisplayInstanceOwnItemById("","update");
				}
				if(jedis!=null&&deviceInfo!=null&&jedis.hexists("DisplayInstanceOwnItem".getBytes(), deviceInfo.getDisplayInstanceCode().getBytes())){
					displayInstanceOwnItem=(DisplayInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("DisplayInstanceOwnItem".getBytes(), deviceInfo.getDisplayInstanceCode().getBytes()));
					protocolName=displayInstanceOwnItem.getProtocol();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			int isControl=userInfo!=null?userInfo.getRoleFlag():0;
			
			List<String> controlItems=new ArrayList<String>();
			List<String> controlColumns=new ArrayList<String>();
			List<Integer> controlItemResolutionMode=new ArrayList<Integer>();
			List<String> controlItemMeaningList=new ArrayList<String>();
			StringBuffer deviceInfoDataList=new StringBuffer();
			StringBuffer deviceControlList=new StringBuffer();
			deviceInfoDataList.append("[");
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
												String col=dataSaveMode==0?("ADDR"+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()));
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
			
			//设备信息
			for(int i=0;i<fields.size();i++){
				if("crudeOilDensity".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getCrudeOilDensity():"")+"\"},");
				}else if("waterDensity".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getWaterDensity():"")+"\"},");
				}else if("naturalGasRelativeDensity".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getNaturalGasRelativeDensity():"")+"\"},");
				}else if("saturationPressure".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getSaturationPressure():"")+"\"},");
				}else if("reservoirDepth".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getReservoir()!=null?deviceInfo.getReservoir().getDepth():"")+"\"},");
				}else if("reservoirTemperature".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getReservoir()!=null?deviceInfo.getReservoir().getTemperature():"")+"\"},");
				}else if("tubingPressure".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getTubingPressure():"")+"\"},");
				}else if("casingPressure".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getCasingPressure():"")+"\"},");
				}else if("wellHeadTemperature".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getWellHeadTemperature():"")+"\"},");
				}else if("waterCut".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getWaterCut():"")+"\"},");
				}else if("productionGasOilRatio".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getProductionGasOilRatio():"")+"\"},");
				}else if("producingfluidLevel".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getProducingfluidLevel():"")+"\"},");
				}else if("pumpSettingDepth".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getPumpSettingDepth():"")+"\"},");
				}else if("barrelLength".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPump()!=null?deviceInfo.getPump().getBarrelLength():"")+"\"},");
				}else if("barrelSeries".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPump()!=null?deviceInfo.getPump().getBarrelSeries():"")+"\"},");
				}else if("rotorDiameter".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPump()!=null?deviceInfo.getPump().getRotorDiameter():"")+"\"},");
				}else if("QPR".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPump()!=null?deviceInfo.getPump().getQPR():"")+"\"},");
				}else if("tubingStringInsideDiameter".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getTubingString()!=null&&deviceInfo.getTubingString().getEveryTubing()!=null&&deviceInfo.getTubingString().getEveryTubing().size()>0?deviceInfo.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000:"")+"\"},");
				}else if("casingStringInsideDiameter".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"name\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getCasingString()!=null&&deviceInfo.getCasingString().getEveryCasing()!=null&&deviceInfo.getCasingString().getEveryCasing().size()>0?deviceInfo.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000:"")+"\"},");
				}else if("rodString".equalsIgnoreCase(fields.get(i))){
					String rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="";
					String rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="";
					String rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="";
					String rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="";
					if(deviceInfo!=null&&deviceInfo.getRodString()!=null&&deviceInfo.getRodString().getEveryRod()!=null&&deviceInfo.getRodString().getEveryRod().size()>0){
						if(deviceInfo.getRodString().getEveryRod().size()>0){
							rodGrade1=deviceInfo.getRodString().getEveryRod().get(0).getGrade();
							rodOutsideDiameter1=deviceInfo.getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
							rodInsideDiameter1=deviceInfo.getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
							rodLength1=deviceInfo.getRodString().getEveryRod().get(0).getLength()+"";
						}
						if(deviceInfo.getRodString().getEveryRod().size()>1){
							rodGrade2=deviceInfo.getRodString().getEveryRod().get(1).getGrade();
							rodOutsideDiameter2=deviceInfo.getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
							rodInsideDiameter2=deviceInfo.getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
							rodLength2=deviceInfo.getRodString().getEveryRod().get(1).getLength()+"";
						}
						if(deviceInfo.getRodString().getEveryRod().size()>2){
							rodGrade3=deviceInfo.getRodString().getEveryRod().get(2).getGrade();
							rodOutsideDiameter3=deviceInfo.getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
							rodInsideDiameter3=deviceInfo.getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
							rodLength3=deviceInfo.getRodString().getEveryRod().get(2).getLength()+"";
						}
						if(deviceInfo.getRodString().getEveryRod().size()>3){
							rodGrade4=deviceInfo.getRodString().getEveryRod().get(3).getGrade();
							rodOutsideDiameter4=deviceInfo.getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
							rodInsideDiameter4=deviceInfo.getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
							rodLength4=deviceInfo.getRodString().getEveryRod().get(3).getLength()+"";
						}
					}
					
					deviceInfoDataList.append("{\"name\":\"一级杆级别\",\"value\":\""+rodGrade1+"\"},");
					deviceInfoDataList.append("{\"name\":\"一级杆外径(mm)\",\"value\":\""+rodOutsideDiameter1+"\"},");
					deviceInfoDataList.append("{\"name\":\"一级杆内径(mm)\",\"value\":\""+rodInsideDiameter1+"\"},");
					deviceInfoDataList.append("{\"name\":\"一级杆长度(m)\",\"value\":\""+rodLength1+"\"},");
					
					deviceInfoDataList.append("{\"name\":\"二级杆级别\",\"value\":\""+rodGrade2+"\"},");
					deviceInfoDataList.append("{\"name\":\"二级杆外径(mm)\",\"value\":\""+rodOutsideDiameter2+"\"},");
					deviceInfoDataList.append("{\"name\":\"二级杆内径(mm)\",\"value\":\""+rodInsideDiameter2+"\"},");
					deviceInfoDataList.append("{\"name\":\"二级杆长度(m)\",\"value\":\""+rodLength2+"\"},");
					
					deviceInfoDataList.append("{\"name\":\"三级杆级别\",\"value\":\""+rodGrade3+"\"},");
					deviceInfoDataList.append("{\"name\":\"三级杆外径(mm)\",\"value\":\""+rodOutsideDiameter3+"\"},");
					deviceInfoDataList.append("{\"name\":\"三级杆内径(mm)\",\"value\":\""+rodInsideDiameter3+"\"},");
					deviceInfoDataList.append("{\"name\":\"三级杆长度(m)\",\"value\":\""+rodLength3+"\"},");
					
					deviceInfoDataList.append("{\"name\":\"四级杆级别\",\"value\":\""+rodGrade4+"\"},");
					deviceInfoDataList.append("{\"name\":\"四级杆外径(mm)\",\"value\":\""+rodOutsideDiameter4+"\"},");
					deviceInfoDataList.append("{\"name\":\"四级杆内径(mm)\",\"value\":\""+rodInsideDiameter4+"\"},");
				}
				
			}
			
			if(deviceInfoDataList.toString().endsWith(",")){
				deviceInfoDataList.deleteCharAt(deviceInfoDataList.length() - 1);
			}
			
			String tableName="tbl_rpcacqdata_latest";
			String sql="select t2.commStatus ";
			if(StringManagerUtils.stringToInteger(deviceType)>0){
				tableName="tbl_pcpacqdata_latest";
			}
			sql+= " from "+deviceTableName+" t,"+tableName+" t2 where t.id=t2.wellid and t.id="+deviceId;
			
			result_json.append("{ \"success\":true,\"isControl\":"+isControl+",");
			List<?> list = this.findCallSql(sql);
			if(list.size()>0){
				if(controlColumns.size()>0){
//					Object[] obj=(Object[]) list.get(0);
					result_json.append("\"commStatus\":\""+list.get(0)+"\",");
					for(int i=0;i<controlColumns.size();i++){
						deviceControlList.append("{\"title\":\""+controlItems.get(i)+"\",\"name\":\""+controlColumns.get(i)+"\",\"resolutionMode\":"+controlItemResolutionMode.get(i)+",\"value\":\"\",\"itemMeaning\":\""+controlItemMeaningList.get(i)+"\"},");
					}
					if(deviceControlList.toString().endsWith(",")){
						deviceControlList.deleteCharAt(deviceControlList.length() - 1);
					}
				}else{
					result_json.append("\"commStatus\":\""+list.get(0)+"\",");
				}
			}
			deviceInfoDataList.append("]");
			deviceControlList.append("]");
			result_json.append("\"videoUrl\":\""+(deviceInfo!=null?deviceInfo.getVideoUrl():"")+"\",");
			result_json.append("\"deviceInfoDataList\":"+deviceInfoDataList+",");
			result_json.append("\"deviceControlList\":"+deviceControlList);
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
	
	public String getPCPDeviceInfoData(String deviceId,String wellName,String deviceType,User user)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String deviceInfoKey="PCPDeviceInfo";
		DataDictionary ddic=dataitemsInfoService.findTableSqlWhereByListFaceId("realTimeMonitoring_PCPDeviceInfo");
		List<String> heads=ddic.getHeaders();
		List<String> fields=ddic.getFields();
		
		Jedis jedis=null;
		PCPDeviceInfo deviceInfo=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists(deviceInfoKey.getBytes())){
					MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
					
				}
				byte[] dviceInfoByte =jedis.hget(deviceInfoKey.getBytes(),deviceId.getBytes());
				Object obj =SerializeObjectUnils.unserizlize(dviceInfoByte);
				if (obj instanceof PCPDeviceInfo) {
					deviceInfo=(PCPDeviceInfo)obj;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			StringBuffer deviceInfoDataList=new StringBuffer();
			deviceInfoDataList.append("[");
			//设备信息
			for(int i=0;i<fields.size();i++){
				if("crudeOilDensity".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getCrudeOilDensity():"")+"\"},");
				}else if("waterDensity".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getWaterDensity():"")+"\"},");
				}else if("naturalGasRelativeDensity".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getNaturalGasRelativeDensity():"")+"\"},");
				}else if("saturationPressure".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getFluidPVT()!=null?deviceInfo.getFluidPVT().getSaturationPressure():"")+"\"},");
				}else if("reservoirDepth".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getReservoir()!=null?deviceInfo.getReservoir().getDepth():"")+"\"},");
				}else if("reservoirTemperature".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getReservoir()!=null?deviceInfo.getReservoir().getTemperature():"")+"\"},");
				}else if("tubingPressure".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getTubingPressure():"")+"\"},");
				}else if("casingPressure".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getCasingPressure():"")+"\"},");
				}else if("wellHeadTemperature".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getWellHeadTemperature():"")+"\"},");
				}else if("waterCut".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getWaterCut():"")+"\"},");
				}else if("productionGasOilRatio".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getProductionGasOilRatio():"")+"\"},");
				}else if("producingfluidLevel".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getProducingfluidLevel():"")+"\"},");
				}else if("pumpSettingDepth".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getProduction()!=null?deviceInfo.getProduction().getPumpSettingDepth():"")+"\"},");
				}else if("barrelLength".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPump()!=null?deviceInfo.getPump().getBarrelLength():"")+"\"},");
				}else if("barrelSeries".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPump()!=null?deviceInfo.getPump().getBarrelSeries():"")+"\"},");
				}else if("rotorDiameter".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPump()!=null?deviceInfo.getPump().getRotorDiameter():"")+"\"},");
				}else if("QPR".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getPump()!=null?deviceInfo.getPump().getQPR():"")+"\"},");
				}else if("tubingStringInsideDiameter".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getTubingString()!=null&&deviceInfo.getTubingString().getEveryTubing()!=null&&deviceInfo.getTubingString().getEveryTubing().size()>0?deviceInfo.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000:"")+"\"},");
				}else if("casingStringInsideDiameter".equalsIgnoreCase(fields.get(i))){
					deviceInfoDataList.append("{\"item\":\""+heads.get(i)+"\","+ "\"value\":\""+(deviceInfo!=null&&deviceInfo.getCasingString()!=null&&deviceInfo.getCasingString().getEveryCasing()!=null&&deviceInfo.getCasingString().getEveryCasing().size()>0?deviceInfo.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000:"")+"\"},");
				}else if("rodString".equalsIgnoreCase(fields.get(i))){
					String rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="";
					String rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="";
					String rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="";
					String rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="";
					if(deviceInfo!=null&&deviceInfo.getRodString()!=null&&deviceInfo.getRodString().getEveryRod()!=null&&deviceInfo.getRodString().getEveryRod().size()>0){
						if(deviceInfo.getRodString().getEveryRod().size()>0){
							rodGrade1=deviceInfo.getRodString().getEveryRod().get(0).getGrade();
							rodOutsideDiameter1=deviceInfo.getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
							rodInsideDiameter1=deviceInfo.getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
							rodLength1=deviceInfo.getRodString().getEveryRod().get(0).getLength()+"";
						}
						if(deviceInfo.getRodString().getEveryRod().size()>1){
							rodGrade2=deviceInfo.getRodString().getEveryRod().get(1).getGrade();
							rodOutsideDiameter2=deviceInfo.getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
							rodInsideDiameter2=deviceInfo.getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
							rodLength2=deviceInfo.getRodString().getEveryRod().get(1).getLength()+"";
						}
						if(deviceInfo.getRodString().getEveryRod().size()>2){
							rodGrade3=deviceInfo.getRodString().getEveryRod().get(2).getGrade();
							rodOutsideDiameter3=deviceInfo.getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
							rodInsideDiameter3=deviceInfo.getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
							rodLength3=deviceInfo.getRodString().getEveryRod().get(2).getLength()+"";
						}
						if(deviceInfo.getRodString().getEveryRod().size()>3){
							rodGrade4=deviceInfo.getRodString().getEveryRod().get(3).getGrade();
							rodOutsideDiameter4=deviceInfo.getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
							rodInsideDiameter4=deviceInfo.getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
							rodLength4=deviceInfo.getRodString().getEveryRod().get(3).getLength()+"";
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
	
	public String getPCPDeviceControlData(String deviceId,String wellName,String deviceType,User user)throws Exception {
		StringBuffer result_json = new StringBuffer();
		int dataSaveMode=1;
		String deviceTableName="tbl_pcpdevice";
		String columnsKey="pcpDeviceAcquisitionItemColumns";
		String deviceInfoKey="PCPDeviceInfo";
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
			EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
		}
		Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
		
		Jedis jedis=null;
		PCPDeviceInfo deviceInfo=null;
		UserInfo userInfo=null;

		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		String protocolName="";
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists(deviceInfoKey.getBytes())){
					MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
					
				}
				byte[] dviceInfoByte =jedis.hget(deviceInfoKey.getBytes(),deviceId.getBytes());
				Object obj =SerializeObjectUnils.unserizlize(dviceInfoByte);
				if (obj instanceof PCPDeviceInfo) {
					deviceInfo=(PCPDeviceInfo)obj;
				}

				if(!jedis.exists("UserInfo".getBytes())){
					MemoryDataManagerTask.loadUserInfo(null,0,"update");
				}
				userInfo=(UserInfo) SerializeObjectUnils.unserizlize(jedis.hget("UserInfo".getBytes(), (user.getUserNo()+"").getBytes()));
				
				if(!jedis.exists("DisplayInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadDisplayInstanceOwnItemById("","update");
				}
				if(jedis!=null&&deviceInfo!=null&&jedis.hexists("DisplayInstanceOwnItem".getBytes(), deviceInfo.getDisplayInstanceCode().getBytes())){
					displayInstanceOwnItem=(DisplayInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("DisplayInstanceOwnItem".getBytes(), deviceInfo.getDisplayInstanceCode().getBytes()));
					protocolName=displayInstanceOwnItem.getProtocol();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			int isControl=userInfo!=null?userInfo.getRoleFlag():0;
			
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
												String col=dataSaveMode==0?("ADDR"+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()));
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
			
			String tableName="tbl_rpcacqdata_latest";
			String sql="select t2.commStatus ";
			if(StringManagerUtils.stringToInteger(deviceType)>0){
				tableName="tbl_pcpacqdata_latest";
			}
			sql+= " from "+deviceTableName+" t,"+tableName+" t2 where t.id=t2.wellid and t.id="+deviceId;
			
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
	
	
	public String getRealTimeCurveData(String deviceName,String item,String deviceType)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String deviceTableName="tbl_rpcdevice";
		String tableName="tbl_rpcacqdata_hist";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			deviceTableName="tbl_pcpdevice";
			tableName="tbl_pcpacqdata_hist";
		}
		String protocolSql="select upper(t3.protocol) from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t.instancecode=t2.code and t2.unitid=t3.id"
				+ " and  t.wellname='"+deviceName+"'";
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
					+ " where t.wellid=t2.id "
					+ " and t.acqtime >to_date('"+StringManagerUtils.getCurrentTime("yyyy-MM-dd")+"','yyyy-mm-dd') "
					+ " and t2.wellname='"+deviceName+"' "
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
	
	public String getRealTimeMonitoringCurveData(String deviceId,String deviceName,String deviceType,int userNo)throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer itemsBuff = new StringBuffer();
		StringBuffer curveConfBuff = new StringBuffer();
		int vacuateThreshold=Config.getInstance().configFile.getAp().getOthers().getVacuateThreshold();
		Jedis jedis=null;
		UserInfo userInfo=null;
		List<byte[]> calItemSet=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		int dataSaveMode=1;
		String displayInstanceCode="";
		String tableName="tbl_rpcacqdata_hist";
		String deviceTableName="tbl_rpcdevice";
		String columnsKey="rpcDeviceAcquisitionItemColumns";
		String calItemsKey="rpcCalItemList";
		String deviceInfoKey="RPCDeviceInfo";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="tbl_pcpacqdata_hist";
			deviceTableName="tbl_pcpdevice";
			columnsKey="pcpDeviceAcquisitionItemColumns";
			calItemsKey="pcpCalItemList";
			deviceInfoKey="PCPDeviceInfo";
		}
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
				
				if(StringManagerUtils.stringToInteger(deviceType)==0){
					if(!jedis.exists(deviceInfoKey.getBytes())){
						MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
					}
					RPCDeviceInfo rpcDeviceInfo=(RPCDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget(deviceInfoKey.getBytes(), deviceId.getBytes()));
					displayInstanceCode=rpcDeviceInfo.getDisplayInstanceCode();
				}else{
					if(!jedis.exists(deviceInfoKey.getBytes())){
						MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
					}
					PCPDeviceInfo pcpDeviceInfo=(PCPDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget(deviceInfoKey.getBytes(), deviceId.getBytes()));
					displayInstanceCode=pcpDeviceInfo.getDisplayInstanceCode();
				}
				
				if(jedis!=null&&jedis.hexists("DisplayInstanceOwnItem".getBytes(), displayInstanceCode.getBytes())){
					displayInstanceOwnItem=(DisplayInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("DisplayInstanceOwnItem".getBytes(), displayInstanceCode.getBytes()));
					Collections.sort(displayInstanceOwnItem.getItemList());
				}
				
				if(!jedis.exists(calItemsKey.getBytes())){
					if(StringManagerUtils.stringToInteger(deviceType)==0){
						MemoryDataManagerTask.loadRPCCalculateItem();
					}else{
						MemoryDataManagerTask.loadPCPCalculateItem();
					}
				}
				calItemSet= jedis.zrange(calItemsKey.getBytes(), 0, -1);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
			
			
			List<String> itemNameList=new ArrayList<String>();
			List<String> itemColumnList=new ArrayList<String>();
			List<String> curveConfList=new ArrayList<String>();
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
												String col=dataSaveMode==0?("addr"+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()));
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
										itemColumnList.add(itemcode);
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
										
										itemNameList.add(itemName);
										curveConfList.add(realtimecurveconf.replaceAll("null", ""));
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
												String col=dataSaveMode==0?("addr"+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()));
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
										itemColumnList.add(itemcode);
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
										
										itemNameList.add(itemName);
										curveConfList.add(realtimecurveconf.replaceAll("null", ""));
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
			if (itemsBuff.toString().endsWith(",")) {
				itemsBuff.deleteCharAt(itemsBuff.length() - 1);
			}
			itemsBuff.append("]");
			
			curveConfBuff.append("[");
			for(int i=0;i<curveConfList.size();i++){
				curveConfBuff.append(""+curveConfList.get(i)+",");
			}
			if (curveConfBuff.toString().endsWith(",")) {
				curveConfBuff.deleteCharAt(curveConfBuff.length() - 1);
			}
			curveConfBuff.append("]");
			
			result_json.append("{\"deviceName\":\""+deviceName+"\",\"curveCount\":"+itemNameList.size()+",\"curveItems\":"+itemsBuff+",\"curveConf\":"+curveConfBuff+",\"list\":[");
			if(itemColumnList.size()>0){
				String columns="";
				for(int i=0;i<itemColumnList.size();i++){
					columns+=","+itemColumnList.get(i);
				}
				String sql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime"+columns
						+ " from "+tableName +" t,"+deviceTableName+" t2 "
						+ " where t.wellid=t2.id "
						+ " and t.acqtime >to_date('"+StringManagerUtils.getCurrentTime("yyyy-MM-dd")+"','yyyy-mm-dd') "
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
					for(int j=1;j<obj.length;j++){
						result_json.append(obj[j]+",");
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
	
	public void saveDeviceControlLog(String deviceId,String wellName,String deviceType,String title,String value,User user) throws SQLException{
		getBaseDao().saveDeviceControlLog(deviceId,wellName,deviceType,title,value,user);
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
	
	public String querySingleDetailsWellBoreChartsData(int id,String wellName) throws SQLException, IOException{
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
        String sql="select well.wellName as wellName, to_char(t.fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
        		+ " t.pumpfsdiagram,"
        		+ " t.upperloadline,t.lowerloadline, t.fmax,t.fmin,t.stroke,t.spm, "
        		+ " t."+prodCol+","
        		+ " status.resultname,status.resultcode,status.optimizationSuggestion, "//12
        		+ " t.rodstring,"
        		+ " t.pumpeff1*100 as pumpeff1, t.pumpeff2*100 as pumpeff2, t.pumpeff3*100 as pumpeff3, t.pumpeff4*100 as pumpeff4,"
        		+ " t.position_curve,t.load_curve"
        		+ " from "+tableName+" t"
        		+ " left outer join tbl_rpcdevice well on t.wellid=well.id"
        		+ " left outer join tbl_rpc_worktype status on t.resultcode=status.resultcode"
        		+ " where 1=1 ";
        sql+=" and t.wellid="+id;
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
	        dataSbf.append("wellName:\""+wellName+"\",");           // 井名
	        dataSbf.append("acqTime:\""+obj[1]+"\",");         // 时间
	        dataSbf.append("pointCount:\""+pointCount+"\","); 
	        dataSbf.append("upperLoadLine:\""+obj[3]+"\",");         // 理论上载荷
	        dataSbf.append("lowerLoadLine:\""+obj[4]+"\",");         // 理论下载荷
	        dataSbf.append("fmax:\""+obj[5]+"\",");         // 最大载荷
	        dataSbf.append("fmin:\""+obj[6]+"\",");         // 最小载荷
	        dataSbf.append("stroke:\""+obj[7]+"\",");         // 冲程
	        dataSbf.append("spm:\""+obj[8]+"\",");         // 冲次
	        dataSbf.append("liquidProduction:\""+obj[9]+"\",");         // 日产液量
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
			dataSbf.append("wellName:\""+wellName+"\",");
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

	public String querySingleDetailsSurfaceData(int id,String wellName) throws SQLException, IOException{
		byte[] bytes; 
		ConfigFile configFile=Config.getInstance().configFile;
		BufferedInputStream bis = null;
        StringBuffer dataSbf = new StringBuffer();
        StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
        String tableName="tbl_rpcacqdata_latest";
        
        String sql="select well.wellName, to_char(t.fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
        		+ " t.upstrokewattmax,t.downstrokewattmax,t.wattdegreebalance,t.upstrokeimax,t.downstrokeimax,t.idegreebalance,t.deltaRadius*100,"
        		+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
        		+ " t.crankangle,t.loadtorque,t.cranktorque,t.currentbalancetorque,t.currentnettorque,"
        		+ " t.expectedbalancetorque,t.expectednettorque,"
        		+ " t.polishrodV,t.polishrodA "
        		+ " from "+tableName+" t"
        		+ " left outer join tbl_rpcdevice well on t.wellid=well.id"
        		+ " where 1=1 ";
        sql+=" and t.wellid="+id;
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
	        dataSbf.append("wellName:\""+wellName+"\",");           // 井名
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
	        dataSbf.append("wellName:\""+wellName+"\",");           // 井名
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
	
	public String getUIKitAccessToken() throws SQLException, IOException{
		StringBuffer dataSbf = new StringBuffer();
		Jedis jedis=null;
		AccessToken accessToken=null;
		boolean success=false;
		String accessTokenStr="";
		long expireTime=0;
		try {
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("UIKitAccessToken".getBytes())){
					MemoryDataManagerTask.loadUIKitAccessToken();
				}else{
					accessToken=(AccessToken)SerializeObjectUnils.unserizlize(jedis.get("UIKitAccessToken".getBytes()));
					if(accessToken!=null&&"200".equalsIgnoreCase(accessToken.getCode())){
						long now=new Date().getTime();
						if(now>accessToken.getData().getExpireTime()){
							MemoryDataManagerTask.loadUIKitAccessToken();
						}
					}else{
						MemoryDataManagerTask.loadUIKitAccessToken();
					}
				}
				accessToken=(AccessToken)SerializeObjectUnils.unserizlize(jedis.get("UIKitAccessToken".getBytes()));
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
}
