package com.cosog.service.historyQuery;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AlarmShowStyle;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AcqInstanceOwnItem;
import com.cosog.model.calculate.AlarmInstanceOwnItem;
import com.cosog.model.calculate.DisplayInstanceOwnItem;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.RPCDeviceInfo;
import com.cosog.model.calculate.UserInfo;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.gridmodel.GraphicSetData;
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
import com.cosog.utils.PageHandler;
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

@Service("historyQueryService")
public class HistoryQueryService<T> extends BaseService<T>  {
	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	
	public String getHistoryQueryCommStatusStatData(String orgId,String deviceType,String deviceTypeStatValue) throws IOException, SQLException{
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
	
	public String getHistoryQueryDeviceTypeStatData(String orgId,String deviceType,String commStatusStatValue) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		AlarmShowStyle alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		if(alarmShowStyle==null){
			MemoryDataManagerTask.initAlarmStyle();
			alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
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
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getHistoryQueryDeviceList(String orgId,String deviceName,String deviceType,String FESdiagramResultStatValue,String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,Page pager) throws IOException, SQLException{
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
				if(StringManagerUtils.stringToInteger(deviceType)==0){
					if(!jedis.exists("RPCDeviceInfo".getBytes())){
						MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
					}
				}else if(StringManagerUtils.stringToInteger(deviceType)==1){
					if(!jedis.exists("PCPDeviceInfo".getBytes())){
						MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
					}
				}
				if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String deviceTableName="tbl_rpcdevice";
			String tableName="tbl_rpcacqdata_latest";
			if(StringManagerUtils.stringToInteger(deviceType)==1){
				tableName="tbl_pcpacqdata_latest";
				deviceTableName="tbl_pcpdevice";
			}
			String columns = "["
					+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50,children:[] },"
					+ "{ \"header\":\"井名\",\"dataIndex\":\"wellName\",flex:9,children:[] },"
					+ "{ \"header\":\"设备类型\",\"dataIndex\":\"deviceTypeName\",flex:6,children:[] },"
					+ "{ \"header\":\"采集时间\",\"dataIndex\":\"acqTime\",flex:11,children:[] },"
					+ "{ \"header\":\"通信状态\",\"dataIndex\":\"commStatusName\",flex:5,children:[] },"
					+ "]";
			
			String sql="select t.id,t.wellname,t2.commstatus,"
					+ "decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'),c1.itemname as devicetypename ";
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
					+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue ";
			if(StringManagerUtils.stringToInteger(deviceType)==0){
				sql+=" left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode";
			}
			
			sql+= " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.wellName='"+deviceName+"'";
			}
			if(StringManagerUtils.stringToInteger(deviceType)==0&&StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
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
			result_json.append("\"start_date\":\""+pager.getStart_date()+"\",");
			result_json.append("\"end_date\":\""+pager.getEnd_date()+"\",");
			result_json.append("\"totalCount\":"+totals+",");
			result_json.append("\"totalRoot\":[");
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[0]+"";
				String alarmInstanceCode="";
				int commAlarmLevel=0;
				if(StringManagerUtils.stringToInteger(deviceType)==0){
					if(jedis!=null&&jedis.hexists("RPCDeviceInfo".getBytes(), deviceId.getBytes())){
						RPCDeviceInfo rpcDeviceInfo=(RPCDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("RPCDeviceInfo".getBytes(), deviceId.getBytes()));
						alarmInstanceCode=rpcDeviceInfo.getAlarmInstanceCode();
					}
				}else if(StringManagerUtils.stringToInteger(deviceType)==1){
					if(jedis!=null&&jedis.hexists("PCPDeviceInfo".getBytes(), deviceId.getBytes())){
						PCPDeviceInfo pcpDeviceInfo=(PCPDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("PCPDeviceInfo".getBytes(), deviceId.getBytes()));
						alarmInstanceCode=pcpDeviceInfo.getAlarmInstanceCode();
					}
				}
				
				if(StringManagerUtils.isNotNull(alarmInstanceCode)){
					AlarmInstanceOwnItem alarmInstanceOwnItem=null;
					if(jedis!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), alarmInstanceCode.getBytes())){
						alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), alarmInstanceCode.getBytes()));
						for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
							if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(obj[3]+"")){
								commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
								break;
							}
						}
					}
				}
				
				result_json.append("{\"id\":"+obj[0]+",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"commStatus\":"+obj[2]+",");
				result_json.append("\"commStatusName\":\""+obj[3]+"\",");
				result_json.append("\"commAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"acqTime\":\""+obj[4]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[5]+"\"},");
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
	
	public String getHistoryQueryDeviceListExportData(String orgId,String deviceName,String deviceType,String FESdiagramResultStatValue,String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,Page pager) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String deviceTableName="tbl_rpcdevice";
		String tableName="tbl_rpcacqdata_latest";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="tbl_pcpacqdata_latest";
			deviceTableName="tbl_pcpdevice";
		}
		
		String sql="select t.id,t.wellname,t2.commstatus,"
				+ "decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
				+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'),c1.itemname as devicetypename ";
		sql+= " from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
				+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue ";
		if(StringManagerUtils.stringToInteger(deviceType)==0){
			sql+=" left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode";
		}
		
		sql+= " where  t.orgid in ("+orgId+") ";
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.wellName='"+deviceName+"'";
		}
		if(StringManagerUtils.stringToInteger(deviceType)==0&&StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
			sql+=" and decode(t2.resultcode,null,'无数据',t3.resultName)='"+FESdiagramResultStatValue+"'";
		}
		if(StringManagerUtils.isNotNull(commStatusStatValue)){
			sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
		}
		if(StringManagerUtils.isNotNull(runStatusStatValue)){
			sql+=" and decode(t2.commstatus,1,decode(t2.runstatus,1,'运行','停抽'),'离线')='"+runStatusStatValue+"'";
		}
		if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
			sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
		}
		sql+=" order by t.sortnum,t.wellname";
		
		int maxvalue=pager.getLimit()+pager.getStart();
		String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		List<?> list = this.findCallSql(finalSql);
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"commStatus\":"+obj[2]+",");
			result_json.append("\"commStatusName\":\""+obj[3]+"\",");
			result_json.append("\"acqTime\":\""+obj[4]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[5]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public boolean exportHistoryQueryDeviceListData(HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceName,String deviceType,String FESdiagramResultStatValue,
			String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,Page pager) throws IOException, SQLException{
		try{
			StringBuffer result_json = new StringBuffer();
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			String deviceTableName="tbl_rpcdevice";
			String tableName="tbl_rpcacqdata_latest";
			if(StringManagerUtils.stringToInteger(deviceType)==1){
				tableName="tbl_pcpacqdata_latest";
				deviceTableName="tbl_pcpdevice";
			}
			
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			
			List<Object> headRow = new ArrayList<>();
			for(int i=0;i<heads.length;i++){
				headRow.add(heads[i]);
			}
		    List<List<Object>> sheetDataList = new ArrayList<>();
		    sheetDataList.add(headRow);
			
			String sql="select t.id,t.wellname,t2.commstatus,"
					+ "decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'),c1.itemname as devicetypename ";
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
					+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue ";
			if(StringManagerUtils.stringToInteger(deviceType)==0){
				sql+=" left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode";
			}
			
			sql+= " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.wellName='"+deviceName+"'";
			}
			if(StringManagerUtils.stringToInteger(deviceType)==0&&StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				sql+=" and decode(t2.resultcode,null,'无数据',t3.resultName)='"+FESdiagramResultStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,decode(t2.runstatus,1,'运行','停抽'),'离线')='"+runStatusStatValue+"'";
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
				result_json.append("{\"id\":"+(i+1)+",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"commStatus\":"+obj[2]+",");
				result_json.append("\"commStatusName\":\""+obj[3]+"\",");
				result_json.append("\"acqTime\":\""+obj[4]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[5]+"\"}");
				
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
		}
		return true;
	}
	
	public String getDeviceHistoryData(String orgId,String deviceId,String deviceName,String deviceType,Page pager) throws IOException, SQLException{
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
			
			String hisTableName="tbl_rpcacqdata_hist";
			String deviceTableName="tbl_rpcdevice";
			String ddicName="historyQuery_RPCHistoryData";
			String columnsKey="rpcDeviceAcquisitionItemColumns";
			DataDictionary ddic = null;
			List<String> ddicColumnsList=new ArrayList<String>();
			if(StringManagerUtils.stringToInteger(deviceType)==1){
				hisTableName="tbl_pcpacqdata_hist";
				deviceTableName="tbl_pcpdevice";
				ddicName="historyQuery_PCPHistoryData";
				columnsKey="pcpDeviceAcquisitionItemColumns";
			}
			
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
			
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
			for(int j=0;modbusProtocolConfig!=null && j<modbusProtocolConfig.getProtocol().size();j++){
				if(modbusProtocolConfig.getProtocol().get(j).getDeviceType()==StringManagerUtils.stringToInteger(deviceType) 
						&& protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
					protocol=modbusProtocolConfig.getProtocol().get(j);
					break;
				}
			}
			
			
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			String columns = ddic.getTableHeader();
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+hisTableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			
			String prodCol="liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol="liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,";
			}
			String sql="select t2.id,t.wellname,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,1,decode(t2.runstatus,1,'运行','停抽'),'') as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "t2.resultcode,decode(t2.commstatus,1,decode(t2.resultcode,null,'无数据',t3.resultName),'' ) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
					+ prodCol+""
					+ "t2.FMax,t2.FMin,t2.fullnessCoefficient,"
					+ "t2.averageWatt,t2.polishrodPower,t2.waterPower,"
					+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
					+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
					+ "t2.systemEfficiency*100 as systemEfficiency,"
					+ "t2.energyper100mlift,t2.pumpEff*100 as pumpEff,"
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
					+ " left outer join "+hisTableName+" t2 on t2.wellid=t.id"
					+ " left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode "
					+ " where  t.orgid in ("+orgId+") "
					+ " and t2.acqTime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') and t.id="+deviceId+""
					+ "  order by t2.acqtime desc";
			int maxvalue=pager.getLimit()+pager.getStart();
			String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
			
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(finalSql);
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"start_date\":\""+pager.getStart_date()+"\",");
			result_json.append("\"end_date\":\""+pager.getEnd_date()+"\",");
			result_json.append("\"totalCount\":"+totals+",");
			result_json.append("\"totalRoot\":[");
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				StringBuffer alarmInfo = new StringBuffer();
				
				int commAlarmLevel=0,resultAlarmLevel=0,runAlarmLevel=0;
				if(alarmInstanceOwnItem!=null){
					for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
						if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(obj[4]+"")){
							commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(obj[9]+"")){
							runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==4 && alarmInstanceOwnItem.getItemList().get(j).getItemCode().equalsIgnoreCase(obj[13]+"")){
							resultAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}
					}
				}
				
				result_json.append("{\"id\":"+obj[0]+",");
				result_json.append("\"deviceId\":\""+deviceId+"\",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"acqTime\":\""+obj[2]+"\",");
				result_json.append("\"commStatus\":"+obj[3]+",");
				result_json.append("\"commStatusName\":\""+obj[4]+"\",");
				result_json.append("\"commTime\":\""+obj[5]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[6]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[7])+"\",");
				result_json.append("\"commAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"runStatus\":"+obj[8]+",");
				result_json.append("\"runStatusName\":\""+obj[9]+"\",");
				result_json.append("\"runTime\":\""+obj[10]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[11]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
				result_json.append("\"runAlarmLevel\":"+runAlarmLevel+",");
				result_json.append("\"resultCode\":\""+obj[13]+"\",");
				result_json.append("\"resultName\":\""+obj[14]+"\",");
				result_json.append("\"optimizationSuggestion\":\""+obj[15]+"\",");
				result_json.append("\"resultAlarmLevel\":"+resultAlarmLevel+",");
				result_json.append("\""+prodCol.split(",")[0]+"\":\""+obj[16]+"\",");
				result_json.append("\""+prodCol.split(",")[1]+"\":\""+obj[17]+"\",");
				result_json.append("\""+prodCol.split(",")[2]+"\":\""+obj[18]+"\",");
				result_json.append("\""+prodCol.split(",")[3]+"\":\""+obj[19]+"\",");
				
				result_json.append("\"FMax\":\""+obj[20]+"\",");
				result_json.append("\"FMin\":\""+obj[21]+"\",");
				result_json.append("\"fullnessCoefficient\":\""+obj[22]+"\",");
				
				result_json.append("\"averageWatt\":\""+obj[23]+"\",");
				result_json.append("\"polishrodPower\":\""+obj[24]+"\",");
				result_json.append("\"waterPower\":\""+obj[25]+"\",");
				
				result_json.append("\"surfaceSystemEfficiency\":\""+obj[26]+"\",");
				result_json.append("\"welldownSystemEfficiency\":\""+obj[27]+"\",");
				result_json.append("\"systemEfficiency\":\""+obj[28]+"\",");
				result_json.append("\"energyper100mlift\":\""+obj[29]+"\",");
				result_json.append("\"pumpEff\":\""+obj[30]+"\",");
				
				result_json.append("\"iDegreeBalance\":\""+obj[31]+"\",");
				result_json.append("\"wattDegreeBalance\":\""+obj[32]+"\",");
				result_json.append("\"deltaradius\":\""+obj[33]+"\",");
				result_json.append("\"levelCorrectValue\":\""+obj[34]+"\",");
				result_json.append("\"inverProducingfluidLevel\":\""+obj[35]+"\",");
				result_json.append("\"todayKWattH\":\""+obj[36]+"\",");
				result_json.append("\"details\":\"\",");
				
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
					String rawValue=obj[37+j]+"";
					String value=rawValue;
					ModbusProtocolConfig.Items item=null;
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col=dataSaveMode==0?("addr"+protocol.getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(k).getTitle()));
							if(col.equalsIgnoreCase(ddicColumnsList.get(j))){
								item=protocol.getItems().get(k);
								if(protocol.getItems().get(k).getMeaning()!=null && protocol.getItems().get(k).getMeaning().size()>0){
									for(int l=0;l<protocol.getItems().get(k).getMeaning().size();l++){
										if(value.equals(protocol.getItems().get(k).getMeaning().get(l).getValue()+"") || StringManagerUtils.stringToFloat(value)==protocol.getItems().get(k).getMeaning().get(l).getValue()){
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
	
	public String getDeviceHistoryExportData(String orgId,String deviceId,String deviceName,String deviceType,Page pager) throws IOException, SQLException{
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
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
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
			}catch(Exception e){
				e.printStackTrace();
			}
			
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			
			String hisTableName="tbl_rpcacqdata_hist";
			String deviceTableName="tbl_rpcdevice";
			String ddicName="historyQuery_RPCHistoryData";
			String columnsKey="rpcDeviceAcquisitionItemColumns";
			DataDictionary ddic = null;
			List<String> ddicColumnsList=new ArrayList<String>();
			if(StringManagerUtils.stringToInteger(deviceType)==1){
				hisTableName="tbl_pcpacqdata_hist";
				deviceTableName="tbl_pcpdevice";
				ddicName="historyQuery_PCPHistoryData";
				columnsKey="pcpDeviceAcquisitionItemColumns";
			}
			
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
			
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
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+hisTableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			
			String prodCol="liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol="liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,";
			}
			String sql="select t2.id,t.wellname,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,1,decode(t2.runstatus,1,'运行','停抽'),'') as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "t2.resultcode,decode(t2.commstatus,1,decode(t2.resultcode,null,'无数据',t3.resultName),'' ) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
					+ prodCol+""
					+ "t2.FMax,t2.FMin,t2.fullnessCoefficient,"
					+ "t2.averageWatt,t2.polishrodPower,t2.waterPower,"
					+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
					+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
					+ "t2.systemEfficiency*100 as systemEfficiency,"
					+ "t2.energyper100mlift,t2.pumpEff*100 as pumpEff,"
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
					+ " left outer join "+hisTableName+" t2 on t2.wellid=t.id"
					+ " left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode "
					+ " where  t.orgid in ("+orgId+") "
					+ " and t2.acqTime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') and t.id="+deviceId+""
					+ "  order by t2.acqtime desc";
			List<?> list = this.findCallSql(sql);
			result_json.append("[");
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				result_json.append("{\"id\":"+obj[0]+",");
				result_json.append("\"deviceId\":\""+deviceId+"\",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"acqTime\":\""+obj[2]+"\",");
				result_json.append("\"commStatus\":"+obj[3]+",");
				result_json.append("\"commStatusName\":\""+obj[4]+"\",");
				result_json.append("\"commTime\":\""+obj[5]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[6]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[7])+"\",");
				result_json.append("\"runStatus\":"+obj[8]+",");
				result_json.append("\"runStatusName\":\""+obj[9]+"\",");
				result_json.append("\"runTime\":\""+obj[10]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[11]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
				result_json.append("\"resultCode\":\""+obj[13]+"\",");
				result_json.append("\"resultName\":\""+obj[14]+"\",");
				result_json.append("\"optimizationSuggestion\":\""+obj[15]+"\",");
				result_json.append("\""+prodCol.split(",")[0]+"\":\""+obj[16]+"\",");
				result_json.append("\""+prodCol.split(",")[1]+"\":\""+obj[17]+"\",");
				result_json.append("\""+prodCol.split(",")[2]+"\":\""+obj[18]+"\",");
				result_json.append("\""+prodCol.split(",")[3]+"\":\""+obj[19]+"\",");
				
				result_json.append("\"FMax\":\""+obj[20]+"\",");
				result_json.append("\"FMin\":\""+obj[21]+"\",");
				result_json.append("\"fullnessCoefficient\":\""+obj[22]+"\",");
				
				result_json.append("\"averageWatt\":\""+obj[23]+"\",");
				result_json.append("\"polishrodPower\":\""+obj[24]+"\",");
				result_json.append("\"waterPower\":\""+obj[25]+"\",");
				
				result_json.append("\"surfaceSystemEfficiency\":\""+obj[26]+"\",");
				result_json.append("\"welldownSystemEfficiency\":\""+obj[27]+"\",");
				result_json.append("\"systemEfficiency\":\""+obj[28]+"\",");
				result_json.append("\"energyper100mlift\":\""+obj[29]+"\",");
				result_json.append("\"pumpEff\":\""+obj[30]+"\",");
				
				result_json.append("\"iDegreeBalance\":\""+obj[31]+"\",");
				result_json.append("\"wattDegreeBalance\":\""+obj[32]+"\",");
				result_json.append("\"deltaradius\":\""+obj[33]+"\",");
				result_json.append("\"levelCorrectValue\":\""+obj[34]+"\",");
				result_json.append("\"inverProducingfluidLevel\":\""+obj[35]+"\",");
				result_json.append("\"todayKWattH\":\""+obj[36]+"\",");
				result_json.append("\"details\":\"\",");
				
				for(int j=0;j<ddicColumnsList.size();j++){
					String rawValue=obj[37+j]+"";
					String value=rawValue;
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col=dataSaveMode==0?("addr"+protocol.getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(k).getTitle()));
							if(col.equalsIgnoreCase(ddicColumnsList.get(j))){
								if(protocol.getItems().get(k).getMeaning()!=null && protocol.getItems().get(k).getMeaning().size()>0){
									for(int l=0;l<protocol.getItems().get(k).getMeaning().size();l++){
										if(value.equals(protocol.getItems().get(k).getMeaning().get(l).getValue()+"") || StringManagerUtils.stringToFloat(value)==protocol.getItems().get(k).getMeaning().get(l).getValue()){
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
	
	public boolean exportDeviceHistoryData(HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceId,String deviceName,String deviceType,Page pager){
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
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
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
			
			String hisTableName="tbl_rpcacqdata_hist";
			String deviceTableName="tbl_rpcdevice";
			String ddicName="historyQuery_RPCHistoryData";
			String columnsKey="rpcDeviceAcquisitionItemColumns";
			DataDictionary ddic = null;
			List<String> ddicColumnsList=new ArrayList<String>();
			if(StringManagerUtils.stringToInteger(deviceType)==1){
				hisTableName="tbl_pcpacqdata_hist";
				deviceTableName="tbl_pcpdevice";
				ddicName="historyQuery_PCPHistoryData";
				columnsKey="pcpDeviceAcquisitionItemColumns";
			}
			
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
			
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
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+hisTableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			
			String prodCol="liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol="liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,";
			}
			String sql="select t2.id,t.wellname,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,1,decode(t2.runstatus,1,'运行','停抽'),'') as runStatusName,"
					+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "t2.resultcode,decode(t2.commstatus,1,decode(t2.resultcode,null,'无数据',t3.resultName),'' ) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
					+ prodCol+""
					+ "t2.FMax,t2.FMin,t2.fullnessCoefficient,"
					+ "t2.averageWatt,t2.polishrodPower,t2.waterPower,"
					+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
					+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
					+ "t2.systemEfficiency*100 as systemEfficiency,"
					+ "t2.energyper100mlift,t2.pumpEff*100 as pumpEff,"
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
					+ " left outer join "+hisTableName+" t2 on t2.wellid=t.id"
					+ " left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode "
					+ " where  t.orgid in ("+orgId+") "
					+ " and t2.acqTime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') and t.id="+deviceId+""
					+ "  order by t2.acqtime desc";
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			List<?> list = this.findCallSql(finalSql);
			List<Object> record=null;
			JSONObject jsonObject=null;
			Object[] obj=null;
			for(int i=0;i<list.size();i++){
				obj=(Object[]) list.get(i);
				result_json = new StringBuffer();
				record = new ArrayList<>();
				result_json.append("{\"id\":"+(i+1)+",");
				result_json.append("\"deviceId\":\""+deviceId+"\",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"acqTime\":\""+obj[2]+"\",");
				result_json.append("\"commStatus\":\""+obj[3]+"\",");
				result_json.append("\"commStatusName\":\""+obj[4]+"\",");
				result_json.append("\"commTime\":\""+obj[5]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[6]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[7])+"\",");
				result_json.append("\"runStatus\":\""+obj[8]+"\",");
				result_json.append("\"runStatusName\":\""+obj[9]+"\",");
				result_json.append("\"runTime\":\""+obj[10]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[11]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
				result_json.append("\"resultCode\":\""+obj[13]+"\",");
				result_json.append("\"resultName\":\""+obj[14]+"\",");
				result_json.append("\"optimizationSuggestion\":\""+obj[15]+"\",");
				result_json.append("\""+prodCol.split(",")[0]+"\":\""+obj[16]+"\",");
				result_json.append("\""+prodCol.split(",")[1]+"\":\""+obj[17]+"\",");
				result_json.append("\""+prodCol.split(",")[2]+"\":\""+obj[18]+"\",");
				result_json.append("\""+prodCol.split(",")[3]+"\":\""+obj[19]+"\",");
				
				result_json.append("\"FMax\":\""+obj[20]+"\",");
				result_json.append("\"FMin\":\""+obj[21]+"\",");
				result_json.append("\"fullnessCoefficient\":\""+obj[22]+"\",");
				
				result_json.append("\"averageWatt\":\""+obj[23]+"\",");
				result_json.append("\"polishrodPower\":\""+obj[24]+"\",");
				result_json.append("\"waterPower\":\""+obj[25]+"\",");
				
				result_json.append("\"surfaceSystemEfficiency\":\""+obj[26]+"\",");
				result_json.append("\"welldownSystemEfficiency\":\""+obj[27]+"\",");
				result_json.append("\"systemEfficiency\":\""+obj[28]+"\",");
				result_json.append("\"energyper100mlift\":\""+obj[29]+"\",");
				result_json.append("\"pumpEff\":\""+obj[30]+"\",");
				
				result_json.append("\"iDegreeBalance\":\""+obj[31]+"\",");
				result_json.append("\"wattDegreeBalance\":\""+obj[32]+"\",");
				result_json.append("\"deltaradius\":\""+obj[33]+"\",");
				result_json.append("\"levelCorrectValue\":\""+obj[34]+"\",");
				result_json.append("\"inverProducingfluidLevel\":\""+obj[35]+"\",");
				result_json.append("\"todayKWattH\":\""+obj[36]+"\"");
				
				for(int j=0;j<ddicColumnsList.size();j++){
					String rawValue=obj[37+j]+"";
					String value=rawValue;
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col=dataSaveMode==0?("addr"+protocol.getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(k).getTitle()));
							if(col.equalsIgnoreCase(ddicColumnsList.get(j))){
								if(protocol.getItems().get(k).getMeaning()!=null && protocol.getItems().get(k).getMeaning().size()>0){
									for(int l=0;l<protocol.getItems().get(k).getMeaning().size();l++){
										if(value.equals(protocol.getItems().get(k).getMeaning().get(l).getValue()+"") || StringManagerUtils.stringToFloat(value)==protocol.getItems().get(k).getMeaning().get(l).getValue()){
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
	
	public String getPCPDeviceHistoryData(String orgId,String deviceId,String deviceName,String deviceType,Page pager){
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
			
			String hisTableName="tbl_pcpacqdata_hist";
			String deviceTableName="tbl_pcpdevice";
			String ddicName="historyQuery_PCPHistoryData";
			String columnsKey="pcpDeviceAcquisitionItemColumns";
			DataDictionary ddic = null;
			List<String> ddicColumnsList=new ArrayList<String>();
			
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
			
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
			
			
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			String columns = ddic.getTableHeader();
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+hisTableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			
			String prodCol="liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol="liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,";
			}
			String sql="select t2.id,t.wellname,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,1,decode(t2.runstatus,1,'运行','停抽'),'') as runStatusName,"
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
					+ " left outer join "+hisTableName+" t2 on t2.wellid=t.id"
					+ " where  t.orgid in ("+orgId+") "
					+ " and t2.acqTime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') and t.id="+deviceId+""
					+ "  order by t2.acqtime desc";
			
			int maxvalue=pager.getLimit()+pager.getStart();
			String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
			
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(finalSql);
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"start_date\":\""+pager.getStart_date()+"\",");
			result_json.append("\"end_date\":\""+pager.getEnd_date()+"\",");
			result_json.append("\"totalCount\":"+totals+",");
			result_json.append("\"totalRoot\":[");
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				StringBuffer alarmInfo = new StringBuffer();
				
				int commAlarmLevel=0,runAlarmLevel=0;
				if(alarmInstanceOwnItem!=null){
					for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
						if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(obj[4]+"")){
							commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(obj[9]+"")){
							runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
						}
					}
				}
				
				result_json.append("{\"id\":"+obj[0]+",");
				result_json.append("\"deviceId\":\""+deviceId+"\",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"acqTime\":\""+obj[2]+"\",");
				result_json.append("\"commStatus\":"+obj[3]+",");
				result_json.append("\"commStatusName\":\""+obj[4]+"\",");
				result_json.append("\"commTime\":\""+obj[5]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[6]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[7])+"\",");
				result_json.append("\"commAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"runStatus\":"+obj[8]+",");
				result_json.append("\"runStatusName\":\""+obj[9]+"\",");
				result_json.append("\"runTime\":\""+obj[10]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[11]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
				result_json.append("\"runAlarmLevel\":"+runAlarmLevel+",");
				result_json.append("\""+prodCol.split(",")[0]+"\":\""+obj[13]+"\",");
				result_json.append("\""+prodCol.split(",")[1]+"\":\""+obj[14]+"\",");
				result_json.append("\""+prodCol.split(",")[2]+"\":\""+obj[15]+"\",");
				result_json.append("\""+prodCol.split(",")[3]+"\":\""+obj[16]+"\",");
				
				result_json.append("\"averageWatt\":\""+obj[17]+"\",");
				result_json.append("\"waterPower\":\""+obj[18]+"\",");
				
				result_json.append("\"systemEfficiency\":\""+obj[19]+"\",");
				result_json.append("\"energyper100mlift\":\""+obj[20]+"\",");
				result_json.append("\"pumpEff\":\""+obj[21]+"\",");
				result_json.append("\"todayKWattH\":\""+obj[22]+"\",");
				result_json.append("\"details\":\"\",");
				
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
					String rawValue=obj[23+j]+"";
					String value=rawValue;
					ModbusProtocolConfig.Items item=null;
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col=dataSaveMode==0?("addr"+protocol.getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(k).getTitle()));
							if(col.equalsIgnoreCase(ddicColumnsList.get(j))){
								item=protocol.getItems().get(k);
								if(protocol.getItems().get(k).getMeaning()!=null && protocol.getItems().get(k).getMeaning().size()>0){
									for(int l=0;l<protocol.getItems().get(k).getMeaning().size();l++){
										if(value.equals(protocol.getItems().get(k).getMeaning().get(l).getValue()+"") || StringManagerUtils.stringToFloat(value)==protocol.getItems().get(k).getMeaning().get(l).getValue()){
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
	
	public String getPCPDeviceHistoryExportData(String orgId,String deviceId,String deviceName,String deviceType,Page pager){
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
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
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
			}catch(Exception e){
				e.printStackTrace();
			}
			
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			
			String hisTableName="tbl_pcpacqdata_hist";
			String deviceTableName="tbl_pcpdevice";
			String ddicName="historyQuery_PCPHistoryData";
			String columnsKey="pcpDeviceAcquisitionItemColumns";
			DataDictionary ddic = null;
			List<String> ddicColumnsList=new ArrayList<String>();
			
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
			
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
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+hisTableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			
			String prodCol="liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol="liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,";
			}
			String sql="select t2.id,t.wellname,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,1,decode(t2.runstatus,1,'运行','停抽'),'') as runStatusName,"
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
					+ " left outer join "+hisTableName+" t2 on t2.wellid=t.id"
					+ " where  t.orgid in ("+orgId+") "
					+ " and t2.acqTime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') and t.id="+deviceId+""
					+ "  order by t2.acqtime desc";
			List<?> list = this.findCallSql(sql);
			result_json.append("[");
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				result_json.append("{\"id\":"+(i+1)+",");
				result_json.append("\"deviceId\":\""+deviceId+"\",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"acqTime\":\""+obj[2]+"\",");
				result_json.append("\"commStatus\":"+obj[3]+",");
				result_json.append("\"commStatusName\":\""+obj[4]+"\",");
				result_json.append("\"commTime\":\""+obj[5]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[6]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[7])+"\",");
				result_json.append("\"runStatus\":"+obj[8]+",");
				result_json.append("\"runStatusName\":\""+obj[9]+"\",");
				result_json.append("\"runTime\":\""+obj[10]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[11]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
				result_json.append("\""+prodCol.split(",")[0]+"\":\""+obj[13]+"\",");
				result_json.append("\""+prodCol.split(",")[1]+"\":\""+obj[14]+"\",");
				result_json.append("\""+prodCol.split(",")[2]+"\":\""+obj[15]+"\",");
				result_json.append("\""+prodCol.split(",")[3]+"\":\""+obj[16]+"\",");
				
				result_json.append("\"averageWatt\":\""+obj[17]+"\",");
				result_json.append("\"waterPower\":\""+obj[18]+"\",");
				
				result_json.append("\"systemEfficiency\":\""+obj[19]+"\",");
				result_json.append("\"energyper100mlift\":\""+obj[20]+"\",");
				result_json.append("\"pumpEff\":\""+obj[21]+"\",");
				result_json.append("\"todayKWattH\":\""+obj[22]+"\",");
				result_json.append("\"details\":\"\",");
				
				for(int j=0;j<ddicColumnsList.size();j++){
					String rawValue=obj[23+j]+"";
					String value=rawValue;
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col=dataSaveMode==0?("addr"+protocol.getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(k).getTitle()));
							if(col.equalsIgnoreCase(ddicColumnsList.get(j))){
								if(protocol.getItems().get(k).getMeaning()!=null && protocol.getItems().get(k).getMeaning().size()>0){
									for(int l=0;l<protocol.getItems().get(k).getMeaning().size();l++){
										if(value.equals(protocol.getItems().get(k).getMeaning().get(l).getValue()+"") || StringManagerUtils.stringToFloat(value)==protocol.getItems().get(k).getMeaning().get(l).getValue()){
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
	
	public boolean exportPCPDeviceHistoryData(HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceId,String deviceName,String deviceType,Page pager) throws IOException, SQLException{
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
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
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
			
			String hisTableName="tbl_pcpacqdata_hist";
			String deviceTableName="tbl_pcpdevice";
			String ddicName="historyQuery_PCPHistoryData";
			String columnsKey="pcpDeviceAcquisitionItemColumns";
			DataDictionary ddic = null;
			List<String> ddicColumnsList=new ArrayList<String>();
			
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
			
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
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+hisTableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			
			String prodCol="liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol="liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,";
			}
			String sql="select t2.id,t.wellname,"
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
					+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,decode(t2.commstatus,1,decode(t2.runstatus,1,'运行','停抽'),'') as runStatusName,"
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
					+ " left outer join "+hisTableName+" t2 on t2.wellid=t.id"
					+ " where  t.orgid in ("+orgId+") "
					+ " and t2.acqTime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') and t.id="+deviceId+""
					+ "  order by t2.acqtime desc";
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			List<?> list = this.findCallSql(finalSql);
			List<Object> record=null;
			JSONObject jsonObject=null;
			Object[] obj=null;
			for(int i=0;i<list.size();i++){
				obj=(Object[]) list.get(i);
				result_json = new StringBuffer();
				record = new ArrayList<>();
				result_json.append("{\"id\":"+obj[0]+",");
				result_json.append("\"deviceId\":\""+deviceId+"\",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"acqTime\":\""+obj[2]+"\",");
				result_json.append("\"commStatus\":"+obj[3]+",");
				result_json.append("\"commStatusName\":\""+obj[4]+"\",");
				result_json.append("\"commTime\":\""+obj[5]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[6]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[7])+"\",");
				result_json.append("\"runStatus\":"+obj[8]+",");
				result_json.append("\"runStatusName\":\""+obj[9]+"\",");
				result_json.append("\"runTime\":\""+obj[10]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[11]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
				result_json.append("\""+prodCol.split(",")[0]+"\":\""+obj[13]+"\",");
				result_json.append("\""+prodCol.split(",")[1]+"\":\""+obj[14]+"\",");
				result_json.append("\""+prodCol.split(",")[2]+"\":\""+obj[15]+"\",");
				result_json.append("\""+prodCol.split(",")[3]+"\":\""+obj[16]+"\",");
				
				result_json.append("\"averageWatt\":\""+obj[17]+"\",");
				result_json.append("\"waterPower\":\""+obj[18]+"\",");
				
				result_json.append("\"systemEfficiency\":\""+obj[19]+"\",");
				result_json.append("\"energyper100mlift\":\""+obj[20]+"\",");
				result_json.append("\"pumpEff\":\""+obj[21]+"\",");
				result_json.append("\"todayKWattH\":\""+obj[22]+"\"");
				
				for(int j=0;j<ddicColumnsList.size();j++){
					String rawValue=obj[23+j]+"";
					String value=rawValue;
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col=dataSaveMode==0?("addr"+protocol.getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(k).getTitle()));
							if(col.equalsIgnoreCase(ddicColumnsList.get(j))){
								if(protocol.getItems().get(k).getMeaning()!=null && protocol.getItems().get(k).getMeaning().size()>0){
									for(int l=0;l<protocol.getItems().get(k).getMeaning().size();l++){
										if(value.equals(protocol.getItems().get(k).getMeaning().get(l).getValue()+"") || StringManagerUtils.stringToFloat(value)==protocol.getItems().get(k).getMeaning().get(l).getValue()){
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
	
	public String getDeviceHistoryDetailsData(String deviceId,String deviceName,String deviceType,String recordId,int userNo){
		StringBuffer result_json = new StringBuffer();
		Jedis jedis=null;
		try{
			int items=3;
			StringBuffer info_json = new StringBuffer();
			int dataSaveMode=1;
			jedis = RedisUtil.jedisPool.getResource();
			AlarmShowStyle alarmShowStyle=null;
			DisplayInstanceOwnItem displayInstanceOwnItem=null;
			AlarmInstanceOwnItem alarmInstanceOwnItem=null;
			Set<byte[]>calItemSet=null;
			UserInfo userInfo=null;
			String tableName="tbl_rpcacqdata_hist";
			String deviceTableName="tbl_rpcdevice";
			String columnsKey="rpcDeviceAcquisitionItemColumns";
			String deviceInfoKey="RPCDeviceInfo";
			String calItemsKey="rpcCalItemList";
			if(StringManagerUtils.stringToInteger(deviceType)!=0){
				tableName="tbl_pcpacqdata_hist";
				deviceTableName="tbl_pcpdevice";
				columnsKey="pcpDeviceAcquisitionItemColumns";
				deviceInfoKey="PCPDeviceInfo";
				calItemsKey="pcpCalItemList";
			}
			String displayInstanceCode="";
			String alarmInstanceCode="";
			if(StringManagerUtils.stringToInteger(deviceType)==0){
				if(!jedis.exists(deviceInfoKey.getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
				}
				RPCDeviceInfo rpcDeviceInfo=(RPCDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget(deviceInfoKey.getBytes(), deviceId.getBytes()));
				displayInstanceCode=rpcDeviceInfo.getDisplayInstanceCode();
				alarmInstanceCode=rpcDeviceInfo.getAlarmInstanceCode();
			}else{
				if(!jedis.exists(deviceInfoKey.getBytes())){
					MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
				}
				PCPDeviceInfo pcpDeviceInfo=(PCPDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget(deviceInfoKey.getBytes(), deviceId.getBytes()));
				displayInstanceCode=pcpDeviceInfo.getDisplayInstanceCode();
				alarmInstanceCode=pcpDeviceInfo.getAlarmInstanceCode();
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
							+ " where  t2.id="+recordId;
					List<?> list = this.findCallSql(sql);
					if(list.size()>0){
						int row=1;
						Object[] obj=(Object[]) list.get(0);
						
						for(int i=0;i<calItemList.size();i++){
							String columnName=calItemList.get(i).getName();
							String rawColumnName=columnName;
							String value=obj[i+6+protocolItems.size()]+"";
//							if(value.toUpperCase().contains("CLOB")){
//								value=StringManagerUtils.CLOBObjectToString(obj[i+6+protocolItems.size()]);
//							}
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
									if("resultCode".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode()) || "resultName".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
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
										if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column) && displayInstanceOwnItem.getItemList().get(l).getType()!=2){
											sort=displayInstanceOwnItem.getItemList().get(l).getSort();
											break;
										}
									}
									ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort);
									protocolItemResolutionDataList.add(protocolItemResolutionData);
								}
							}else{//如果是数据量
								for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
									if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column)){
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
													if("resultCode".equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getColumn()) || "resultName".equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getColumn())){
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
	
	public String getHistoryQueryCurveData(String deviceId,String deviceName,String deviceType,String startDate,String endDate,int userNo)throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer itemsBuff = new StringBuffer();
		StringBuffer itemsCodeBuff = new StringBuffer();
		StringBuffer curveColorBuff = new StringBuffer();
		Jedis jedis=null;
		UserInfo userInfo=null;
		Set<byte[]>calItemSet=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		int dataSaveMode=1;
		String displayInstanceCode="";
		String graphicSet="{}";
		String tableName="tbl_rpcacqdata_hist";
		String deviceTableName="tbl_rpcdevice";
		String graphicSetTableName="tbl_rpcdevicegraphicset";
		String columnsKey="rpcDeviceAcquisitionItemColumns";
		String calItemsKey="rpcCalItemList";
		String deviceInfoKey="RPCDeviceInfo";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="tbl_pcpacqdata_hist";
			deviceTableName="tbl_pcpdevice";
			graphicSetTableName="tbl_pcpdevicegraphicset";
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
					displayInstanceCode=rpcDeviceInfo.getDisplayInstanceCode()+"";
				}else{
					if(!jedis.exists(deviceInfoKey.getBytes())){
						MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
					}
					PCPDeviceInfo pcpDeviceInfo=(PCPDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget(deviceInfoKey.getBytes(), deviceId.getBytes()));
					displayInstanceCode=pcpDeviceInfo.getDisplayInstanceCode()+"";
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
			List<String> curveColorList=new ArrayList<String>();
			
			String graphicSetSql="select t.graphicstyle from "+graphicSetTableName+" t where t.wellid="+deviceId;
			List<?> graphicSetList = this.findCallSql(graphicSetSql);
			
			if(displayInstanceOwnItem!=null){
				Collections.sort(displayInstanceOwnItem.getItemList(),new Comparator<DisplayInstanceOwnItem.DisplayItem>(){
					@Override
					public int compare(DisplayInstanceOwnItem.DisplayItem item1,DisplayInstanceOwnItem.DisplayItem item2){
						int diff=item1.getHistoryCurve()-item2.getHistoryCurve();
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
								if(displayInstanceOwnItem.getItemList().get(j).getHistoryCurve()>0 && displayInstanceOwnItem.getItemList().get(j).getShowLevel()>=userInfo.getRoleShowLevel()){
									String itemname=displayInstanceOwnItem.getItemList().get(j).getItemName();
									String bitindex=displayInstanceOwnItem.getItemList().get(j).getBitIndex()+"";
									String historycurvecolor=displayInstanceOwnItem.getItemList().get(j).getHistoryCurveColor();
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
												curveColorList.add(historycurvecolor.replaceAll("null", ""));
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
										curveColorList.add(historycurvecolor.replaceAll("null", ""));
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
				
				String curveItemsSql="select t4.itemname,t4.bitindex,t4.historycurvecolor "
						+ " from "+deviceTableName+" t,tbl_protocoldisplayinstance t2,tbl_display_unit_conf t3,tbl_display_items2unit_conf t4 "
						+ " where t.displayinstancecode=t2.code and t2.displayunitid=t3.id and t3.id=t4.unitid and t4.type=0 "
						+ " and t.id="+deviceId+" and t4.historycurve>=0 "
						+ " order by t4.historycurve,t4.sort,t4.id";
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
									for(int k=0;k<modbusProtocolConfig.getProtocol().get(i).getItems().size();k++){
										if(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle().equalsIgnoreCase(itemObj[0]+"")){
											String col=dataSaveMode==0?("addr"+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()));
											itemColumnList.add(col);
											if(StringManagerUtils.isNotNull(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getUnit())){
												itemNameList.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()+"("+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getUnit()+")");
											}else{
												itemNameList.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle());
											}
											curveColorList.add((itemObj[2]+"").replaceAll("null", ""));
											break;
										}
									}
								}
								break;
							}
						}
					}
				}
			}
			
			
			
			if(graphicSetList.size()>0){
				graphicSet=graphicSetList.get(0).toString().replaceAll(" ", "").replaceAll("\r\n", "").replaceAll("\n", "");
			}
			
			itemsBuff.append("[");
			for(int i=0;i<itemNameList.size();i++){
				itemsBuff.append("\""+itemNameList.get(i)+"\",");
			}
			if (itemsBuff.toString().endsWith(",")) {
				itemsBuff.deleteCharAt(itemsBuff.length() - 1);
			}
			itemsBuff.append("]");
			
			itemsCodeBuff.append("[");
			for(int i=0;i<itemColumnList.size();i++){
				itemsCodeBuff.append("\""+itemColumnList.get(i)+"\",");
			}
			if (itemsCodeBuff.toString().endsWith(",")) {
				itemsCodeBuff.deleteCharAt(itemsCodeBuff.length() - 1);
			}
			itemsCodeBuff.append("]");
			
			curveColorBuff.append("[");
			for(int i=0;i<curveColorList.size();i++){
				curveColorBuff.append("\""+curveColorList.get(i)+"\",");
			}
			if (curveColorBuff.toString().endsWith(",")) {
				curveColorBuff.deleteCharAt(curveColorBuff.length() - 1);
			}
			curveColorBuff.append("]");
			
			result_json.append("{\"deviceName\":\""+deviceName+"\","
					+ "\"startDate\":\""+startDate+"\","
					+ "\"endDate\":\""+endDate+"\","
					+ "\"curveItems\":"+itemsBuff+","
					+ "\"curveItemCodes\":"+itemsCodeBuff+","
					+ "\"curveColors\":"+curveColorBuff+","
					+ "\"graphicSet\":"+graphicSet+","
					+ "\"list\":[");
			if(itemColumnList.size()>0){
				String columns="";
				for(int i=0;i<itemColumnList.size();i++){
					columns+=","+itemColumnList.get(i);
				}
				String sql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime"+columns
						+ " from "+tableName +" t,"+deviceTableName+" t2 "
						+ " where t.wellid=t2.id "
						+ " and t.acqtime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss')"
						+ " and t2.id="+deviceId;
				int total=this.getTotalCountRows(sql);
				int rarefy=total/500+1;
				sql+= " order by t.acqtime";
				
				String finalSql=sql;
				if(rarefy>1){
					finalSql="select acqtime"+columns+" from  (select v.*, rownum as rn from ("+sql+") v ) v2 where mod(rn-1,"+rarefy+")=0";
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
			if(jedis!=null){
				jedis.close();
			}
		}
		return result_json.toString();
	}
	
	public String getHistoryQueryCurveSetData(String deviceId,String deviceType)throws Exception {
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		int dataSaveMode=1;
		String deviceTableName="tbl_rpcdevice";
		String graphicSetTableName="tbl_rpcdevicegraphicset";
		String columnsKey="rpcDeviceAcquisitionItemColumns";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			deviceTableName="tbl_pcpdevice";
			graphicSetTableName="tbl_pcpdevicegraphicset";
			columnsKey="pcpDeviceAcquisitionItemColumns";
		}
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
			EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
		}
		Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
		
		String protocolSql="select upper(t3.protocol) from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 "
				+ " where t.instancecode=t2.code and t2.unitid=t3.id"
				+ " and  t.id="+deviceId;
		String graphicSetSql="select t.graphicstyle from "+graphicSetTableName+" t where t.wellid="+deviceId;
		String curveItemsSql="select t4.itemname,t4.bitindex,t4.historycurvecolor,t4.itemcode,t4.type "
				+ " from "+deviceTableName+" t,tbl_protocoldisplayinstance t2,tbl_display_unit_conf t3,tbl_display_items2unit_conf t4 "
				+ " where t.displayinstancecode=t2.code and t2.displayunitid=t3.id and t3.id=t4.unitid and t4.type<>2 "
				+ " and t.id="+deviceId+" and t4.historycurve>=0 "
				+ " order by t4.historycurve,t4.sort,t4.id";
		List<?> protocolList = this.findCallSql(protocolSql);
		List<?> graphicSetList = this.findCallSql(graphicSetSql);
		List<?> curveItemList = this.findCallSql(curveItemsSql);
		String protocolName="";
		String unit="";
		String dataType="";
		GraphicSetData graphicSetData=null;
		int resolutionMode=0;
//		List<String> itemNameList=new ArrayList<String>();
//		List<String> itemColumnList=new ArrayList<String>();
//		List<String> curveColorList=new ArrayList<String>();
//		if(protocolList.size()>0){
//			protocolName=protocolList.get(0)+"";
//			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
//			if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()!=null){
//				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
//					if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
//						for(int j=0;j<curveItemList.size();j++){
//							Object[] itemObj=(Object[]) curveItemList.get(j);
//							for(int k=0;k<modbusProtocolConfig.getProtocol().get(i).getItems().size();k++){
//								if(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle().equalsIgnoreCase(itemObj[0]+"")){
//									String col=dataSaveMode==0?("addr"+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()));
////									itemColumnList.add(col);
//									if(StringManagerUtils.isNotNull(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getUnit())){
//										itemNameList.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()+"("+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getUnit()+")");
//									}else{
//										itemNameList.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle());
//									}
////									curveColorList.add((itemObj[2]+"").replaceAll("null", ""));
//									break;
//								}
//							}
//						}
//						break;
//					}
//				}
//			}
//		}
		
		if(graphicSetList.size()>0){
			String graphicSet=graphicSetList.get(0).toString().replaceAll(" ", "").replaceAll("\r\n", "").replaceAll("\n", "");
			type = new TypeToken<GraphicSetData>() {}.getType();
			graphicSetData=gson.fromJson(graphicSet, type);
		}
		
		result_json.append("{\"success\":true,\"totalCount\":"+curveItemList.size()+",\"totalRoot\":[");
		for(int i=0;i<curveItemList.size();i++){
			Object[] itemObj=(Object[]) curveItemList.get(i);
			result_json.append("{\"curveName\":\"" + itemObj[0] + "\",\"itemCode\":\"" + itemObj[3] + "\",\"itemType\":\"" + itemObj[4] + "\",");
			if(graphicSetData!=null && graphicSetData.getHistory()!=null && graphicSetData.getHistory().size()>i){
				result_json.append("\"yAxisMaxValue\":\"" + graphicSetData.getHistory().get(i).getyAxisMaxValue() + "\",");
				result_json.append("\"yAxisMinValue\":\"" + graphicSetData.getHistory().get(i).getyAxisMinValue() + "\"},");
			}else{
				result_json.append("\"yAxisMaxValue\":\"\",");
				result_json.append("\"yAxisMinValue\":\"\"},");
			}
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		
		result_json.append("]}");
		return result_json.toString();
	}
	
	public int setHistoryDataGraphicInfo(String deviceId,String deviceType,String graphicSetData)throws Exception {
		int result=0;
		if(StringManagerUtils.stringToInteger(deviceId)>0){
			String deviceTableName="tbl_rpcdevice";
			String graphicSetTableName="tbl_rpcdevicegraphicset";
			if(StringManagerUtils.stringToInteger(deviceType)==1){
				deviceTableName="tbl_pcpdevice";
				graphicSetTableName="tbl_pcpdevicegraphicset";
			}
			String sql="select t.wellid from "+graphicSetTableName+" t where t.wellid="+deviceId;
			String updateSql="";
			List<?> list = this.findCallSql(sql);
			if(list.size()>0){
				updateSql="update "+graphicSetTableName+" t set t.graphicstyle='"+graphicSetData+"' where t.wellid="+deviceId;
			}else{
				updateSql="insert into "+graphicSetTableName+" (wellid,graphicstyle) values("+deviceId+",'"+graphicSetData+"')";
			}
			result=this.getBaseDao().updateOrDeleteBySql(updateSql);
		}
		return result;
	}
	
	public String querySurfaceCard(String orgId,String deviceId,String deviceName,String deviceType,Page pager) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int intPage = pager.getPage();
		int limit = pager.getLimit();
		int start = pager.getStart();
		int maxvalue = limit + start;
		String allsql="",sql="",totalSql="";
		allsql="select t.id,well.wellname,to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ " t.stroke,t.spm,"
				+ " t.fmax,t.fmin,t.position_curve,t.load_curve,"
				+ " t.resultcode,t2.resultname,t2.optimizationSuggestion,"
				+ " t.upperloadline,t.lowerloadline,t.liquidvolumetricproduction "
				+ " from tbl_rpcacqdata_hist t"
				+ " left outer join tbl_rpcdevice well on well.id=t.wellid"
				+ " left outer join tbl_rpc_worktype t2 on t.resultcode=t2.resultcode"
				+ " where  1=1 "
				+ " and t.fesdiagramacqtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') "
				+ " and t.wellid="+deviceId+" ";
		totalSql=allsql;
		allsql+= " order by t.fesdiagramacqtime desc";
		
		
		sql="select b.* from (select a.*,rownum as rn from  ("+ allsql +") a where rownum <= "+ maxvalue +") b where rn > "+ start +"";
		long time1=System.nanoTime()/1000;
		int totals = getTotalCountRows(totalSql);//获取总记录数
		long time2=System.nanoTime()/1000;
		System.out.println("查询功图平铺图形总数耗时:"+(time2-time1));
		List<?> list=this.findCallSql(sql);
		long time3=System.nanoTime()/1000;
		System.out.println("查询功图平铺图形分页数据耗时:"+(time3-time2));
		PageHandler handler = new PageHandler(intPage, totals, limit);
		int totalPages = handler.getPageCount(); // 总页数
		dynSbf.append("{\"success\":true,\"totals\":" + totals + ",\"totalPages\":\"" + totalPages + "\",\"start_date\":\""+pager.getStart_date()+"\",\"end_date\":\""+pager.getEnd_date()+"\",\"list\":[");
		
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			CLOB realClob=null;
			SerializableClobProxy   proxy=null;
			String DiagramXData="";
	        String DiagramYData="";
	        String pointCount="";
	        if(obj[7]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[7]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				DiagramXData=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(StringManagerUtils.isNotNull(DiagramXData)){
				pointCount=DiagramXData.split(",").length+"";
			}
	        if(obj[8]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[8]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				DiagramYData=StringManagerUtils.CLOBtoString(realClob);
			}
	        
			dynSbf.append("{ \"id\":\"" + obj[0] + "\",");
			dynSbf.append("\"wellName\":\"" + obj[1] + "\",");
			dynSbf.append("\"acqTime\":\"" + obj[2] + "\",");
			dynSbf.append("\"stroke\":\""+obj[3]+"\",");
			dynSbf.append("\"spm\":\""+obj[4]+"\",");
			dynSbf.append("\"fmax\":\""+obj[5]+"\",");
			dynSbf.append("\"fmin\":\""+obj[6]+"\",");
			
			dynSbf.append("\"resultName\":\""+obj[10]+"\",");
			dynSbf.append("\"optimizationSuggestion\":\""+obj[11]+"\",");
			
			dynSbf.append("\"upperLoadLine\":\"" + obj[12] + "\",");
			dynSbf.append("\"lowerLoadLine\":\"" + obj[13] + "\",");
			dynSbf.append("\"liquidProduction\":\""+obj[14]+"\",");
			
			dynSbf.append("\"pointCount\":\""+pointCount+"\","); 
			dynSbf.append("\"positionCurveData\":\""+DiagramXData+"\",");
			dynSbf.append("\"loadCurveData\":\""+DiagramYData+"\"},");
		}
		if(dynSbf.toString().endsWith(",")){
			dynSbf.deleteCharAt(dynSbf.length() - 1);
		}
		dynSbf.append("]}");
		long time4=System.nanoTime()/1000;
		System.out.println("形成功图平铺json据耗时:"+(time4-time3));
		return dynSbf.toString().replaceAll("null", "");
	}
	
	@SuppressWarnings("deprecation")
	public String getFESDiagramOverlayData(String orgId,String deviceId,String deviceName,String deviceType,Page pager) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		DataDictionary ddic = null;
		
		String prodCol="liquidVolumetricProduction,liquidVolumetricProduction_L";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol="liquidWeightProduction,liquidWeightProduction_L";
		}
		Jedis jedis = null;
		AlarmShowStyle alarmShowStyle=null;
		AlarmInstanceOwnItem alarmInstanceOwnItem=null;
		RPCDeviceInfo rpcDeviceInfo=null;
		String alarmInstanceCode="";
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("RPCDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
				}
				if(jedis.hexists("RPCDeviceInfo".getBytes(), deviceId.getBytes())){
					rpcDeviceInfo=(RPCDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("RPCDeviceInfo".getBytes(), deviceId.getBytes()));
					alarmInstanceCode=rpcDeviceInfo.getAlarmInstanceCode();
				}
				
				if(!jedis.exists("AlarmShowStyle".getBytes())){
					MemoryDataManagerTask.initAlarmStyle();
				}
				alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
				
				
				if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
					MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
				}
				
				if(StringManagerUtils.isNotNull(alarmInstanceCode)&&jedis.hexists("AlarmInstanceOwnItem".getBytes(),alarmInstanceCode.getBytes())){
					alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(),alarmInstanceCode.getBytes()));
				}
				
				if(!jedis.exists("RPCWorkType".getBytes())){
					MemoryDataManagerTask.loadRPCWorkType();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			String sql="select t.id,well.wellname,to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
					+ " t.resultcode,t2.resultname,"
					+ " t.stroke,t.spm,"
					+ " t.fmax,t.fmin,"
					+ " t.upperloadline,t.lowerloadline,"+prodCol+", "
					+ " t.iDegreeBalance,t.wattDegreeBalance,"
					+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve"
					+ " from tbl_rpcacqdata_hist t"
					+ " left outer join tbl_rpcdevice well on well.id=t.wellid"
					+ " left outer join tbl_rpc_worktype t2 on t.resultcode=t2.resultcode"
					+ " where  1=1 "
					+ " and t.fesdiagramacqtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') "
					+ " and t.wellid="+deviceId+" "
					+ " order by t.fesdiagramacqtime desc";
			String countSql="select count(1) from tbl_rpcacqdata_hist t"
					+ " left outer join tbl_rpcdevice well on well.id=t.wellid"
					+ " left outer join tbl_rpc_worktype t2 on t.resultcode=t2.resultcode"
					+ " where  1=1 "
					+ " and t.fesdiagramacqtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') "
					+ " and t.wellid="+deviceId;
			int total=this.getTotalCountRows(countSql);
			int coefficient=1500;
			int rarefy=(total%coefficient)==0?(total/coefficient):(total/coefficient+1);
			String finalSql=sql;
			if(rarefy>1){
				finalSql="select v2.* from  (select v.*, rownum as rn from ("+sql+") v ) v2 where mod(rn-1,"+rarefy+")=0";
			}
			List<?> list=this.findCallSql(finalSql);
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId("historyQuery_FESDiagramOverlay");
			String columns = ddic.getTableHeader();
			String[] ddicColumns=ddic.getSql().split(",");
			dynSbf.append("{\"success\":true,\"totalCount\":" + list.size() + ",\"wellName\":\""+deviceName+"\",\"start_date\":\""+pager.getStart_date()+"\",\"end_date\":\""+pager.getEnd_date()+"\",\"columns\":"+columns+",\"totalRoot\":[");
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					StringBuffer alarmInfo = new StringBuffer();
					String positionCurveData="",loadCurveData="",powerCurveData="",currentCurveData="";
					int resultAlarmLevel=0;
					String resultCode=obj[3]+"";
					if(alarmInstanceOwnItem!=null){
						for(int j=0;j<alarmInstanceOwnItem.getItemList().size();j++){
							if(alarmInstanceOwnItem.getItemList().get(j).getType()==4 && alarmInstanceOwnItem.getItemList().get(j).getItemCode().equalsIgnoreCase(resultCode)){
								resultAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
								break;
							}
						}
					}
					SerializableClobProxy   proxy=null;
					CLOB realClob=null;
					if(obj[14]!=null){
						proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[15]);
						realClob = (CLOB) proxy.getWrappedClob(); 
						positionCurveData=StringManagerUtils.CLOBtoString(realClob);
					}
					if(obj[15]!=null){
						proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[16]);
						realClob = (CLOB) proxy.getWrappedClob(); 
						loadCurveData=StringManagerUtils.CLOBtoString(realClob);
					}
					if(obj[16]!=null){
						proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[17]);
						realClob = (CLOB) proxy.getWrappedClob(); 
						powerCurveData=StringManagerUtils.CLOBtoString(realClob);
					}
					if(obj[17]!=null){
						proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[18]);
						realClob = (CLOB) proxy.getWrappedClob(); 
						currentCurveData=StringManagerUtils.CLOBtoString(realClob);
					}
					dynSbf.append("{ \"id\":\"" + obj[0] + "\",");
					dynSbf.append("\"wellName\":\"" + obj[1] + "\",");
					dynSbf.append("\"acqTime\":\"" + obj[2] + "\",");
					dynSbf.append("\"resultCode\":\""+resultCode+"\",");
					dynSbf.append("\"resultName\":\""+obj[4]+"\",");
					dynSbf.append("\"resultAlarmLevel\":\""+resultAlarmLevel+"\",");
					dynSbf.append("\"stroke\":\""+obj[5]+"\",");
					dynSbf.append("\"spm\":\""+obj[6]+"\",");
					dynSbf.append("\"fmax\":\""+obj[7]+"\",");
					dynSbf.append("\"fmin\":\""+obj[8]+"\",");
					dynSbf.append("\"upperLoadLine\":\""+obj[9]+"\",");
					dynSbf.append("\"lowerLoadLine\":\""+obj[10]+"\",");
					dynSbf.append("\""+prodCol.split(",")[0]+"\":\""+obj[11]+"\",");
					dynSbf.append("\""+prodCol.split(",")[1]+"\":\""+obj[12]+"\",");
					dynSbf.append("\"iDegreeBalance\":\"" + obj[13] + "\",");
					dynSbf.append("\"wattDegreeBalance\":\"" + obj[14] + "\",");
					dynSbf.append("\"positionCurveData\":\"" + positionCurveData + "\",");
					dynSbf.append("\"loadCurveData\":\"" + loadCurveData + "\",");
					dynSbf.append("\"powerCurveData\":\"" + powerCurveData + "\",");
					dynSbf.append("\"currentCurveData\":\"" + currentCurveData + "\",");
					
					//计算项报警判断
					alarmInfo.append("[");
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
					if(alarmInfo.toString().endsWith(",")){
						alarmInfo.deleteCharAt(alarmInfo.length() - 1);
					}
					alarmInfo.append("]");
					dynSbf.append("\"alarmInfo\":"+alarmInfo+"");
					dynSbf.append("},");
				}
				
				if(dynSbf.toString().endsWith(",")){
					dynSbf.deleteCharAt(dynSbf.length() - 1);
				}
			}
			dynSbf.append("]");
			dynSbf.append(",\"AlarmShowStyle\":"+(alarmShowStyle==null?"\"\"":new Gson().toJson(alarmShowStyle)));
			dynSbf.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return dynSbf.toString().replaceAll("null", "");
	}
	
	@SuppressWarnings("deprecation")
	public String getFESDiagramOverlayExportData(String orgId,String deviceId,String deviceName,Page pager) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		String prodCol="liquidVolumetricProduction,liquidVolumetricProduction_L";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol="liquidWeightProduction,liquidWeightProduction_L";
		}
		
		String sql="select t.id,well.wellname,to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ " t.resultcode,t2.resultname,"
				+ " t.stroke,t.spm,"
				+ " t.fmax,t.fmin,"
				+ prodCol+", "
				+ " t.iDegreeBalance,t.wattDegreeBalance"
				+ " from tbl_rpcdevice well,tbl_rpcacqdata_hist t,tbl_rpc_worktype t2 "
				+ " where well.id=t.wellid and t.resultcode=t2.resultcode "
				+ " and t.wellid="+deviceId+" "
				+ " and t.fesdiagramacqtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') "
				+ " order by t.fesdiagramacqtime desc";
		
		List<?> list=this.findCallSql(sql);
		
		dynSbf.append("[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				dynSbf.append("{ \"id\":\"" + obj[0] + "\",");
				dynSbf.append("\"wellName\":\"" + obj[1] + "\",");
				dynSbf.append("\"acqTime\":\"" + obj[2] + "\",");
				dynSbf.append("\"resultCode\":\""+obj[3]+"\",");
				dynSbf.append("\"resultName\":\""+obj[4]+"\",");
				dynSbf.append("\"stroke\":\""+obj[5]+"\",");
				dynSbf.append("\"spm\":\""+obj[6]+"\",");
				dynSbf.append("\"fmax\":\""+obj[7]+"\",");
				dynSbf.append("\"fmin\":\""+obj[8]+"\",");
				dynSbf.append("\""+prodCol.split(",")[0]+"\":\""+obj[9]+"\",");
				dynSbf.append("\""+prodCol.split(",")[1]+"\":\""+obj[10]+"\",");
				dynSbf.append("\"iDegreeBalance\":\"" + obj[11] + "\",");
				dynSbf.append("\"wattDegreeBalance\":\"" + obj[12] + "\"},");
			}
			if(dynSbf.toString().endsWith(",")){
				dynSbf.deleteCharAt(dynSbf.length() - 1);
			}
		}
		dynSbf.append("]");
		return dynSbf.toString().replaceAll("null", "");
	}
	
	public boolean exportFESDiagramOverlayData(HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceId,String deviceName,Page pager){
		try{
			StringBuffer result_json = new StringBuffer();
			ConfigFile configFile=Config.getInstance().configFile;
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
			
			String prodCol="liquidVolumetricProduction,liquidVolumetricProduction_L";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol="liquidWeightProduction,liquidWeightProduction_L";
			}
			
			String sql="select t.id,well.wellname,to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
					+ " t.resultcode,t2.resultname,"
					+ " t.stroke,t.spm,"
					+ " t.fmax,t.fmin,"
					+ prodCol+", "
					+ " t.iDegreeBalance,t.wattDegreeBalance"
					+ " from tbl_rpcdevice well,tbl_rpcacqdata_hist t,tbl_rpc_worktype t2 "
					+ " where well.id=t.wellid and t.resultcode=t2.resultcode "
					+ " and t.wellid="+deviceId+" "
					+ " and t.fesdiagramacqtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') "
					+ " order by t.fesdiagramacqtime desc";
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			List<?> list=this.findCallSql(finalSql);
			List<Object> record=null;
			JSONObject jsonObject=null;
			Object[] obj=null;
			for(int i=0;i<list.size();i++){
				obj=(Object[]) list.get(i);
				result_json = new StringBuffer();
				record = new ArrayList<>();
				result_json.append("{ \"id\":\"" + (i+1) + "\",");
				result_json.append("\"wellName\":\"" + obj[1] + "\",");
				result_json.append("\"acqTime\":\"" + obj[2] + "\",");
				result_json.append("\"resultCode\":\""+obj[3]+"\",");
				result_json.append("\"resultName\":\""+obj[4]+"\",");
				result_json.append("\"stroke\":\""+obj[5]+"\",");
				result_json.append("\"spm\":\""+obj[6]+"\",");
				result_json.append("\"fmax\":\""+obj[7]+"\",");
				result_json.append("\"fmin\":\""+obj[8]+"\",");
				result_json.append("\""+prodCol.split(",")[0]+"\":\""+obj[9]+"\",");
				result_json.append("\""+prodCol.split(",")[1]+"\":\""+obj[10]+"\",");
				result_json.append("\"iDegreeBalance\":\"" + obj[11] + "\",");
				result_json.append("\"wattDegreeBalance\":\"" + obj[12] + "\"}");
				
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
		}
		return true;
	}
}
