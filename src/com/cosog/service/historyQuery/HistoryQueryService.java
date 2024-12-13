package com.cosog.service.historyQuery;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AlarmShowStyle;
import com.cosog.model.CurveConf;
import com.cosog.model.DataMapping;
import com.cosog.model.KeyValue;
import com.cosog.model.User;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AcqInstanceOwnItem;
import com.cosog.model.calculate.AlarmInstanceOwnItem;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.DisplayInstanceOwnItem;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.RPCDeviceInfo;
import com.cosog.model.calculate.RPCProductionData;
import com.cosog.model.calculate.UserInfo;
import com.cosog.model.calculate.DisplayInstanceOwnItem.DisplayItem;
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
	
	public String getHistoryQueryCommStatusStatData(String orgId,String deviceType,String deviceTypeStatValue,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		AlarmShowStyle alarmShowStyle=null;
		List<DeviceInfo> deviceInfoList=null;
		boolean jedisStatus=false;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try{
			try{
				jedisStatus= MemoryDataManagerTask.getJedisStatus();
				alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
				deviceInfoList=MemoryDataManagerTask.getDeviceInfoByOrgIdArr(orgId.split(","));
			}catch(Exception e){
				e.printStackTrace();
			}
			String columns = "["
					+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"item\",children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("variable")+"\",\"dataIndex\":\"count\",children:[] }"
					+ "]";
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"totalCount\":3,");
			int total=0,online=0,goOnline=0,offline=0;
			if(!jedisStatus){
				String tableName="tbl_rpcacqdata_latest";
				String deviceTableName="viw_rpcdevice";
				if(StringManagerUtils.stringToInteger(deviceType)!=0){
					tableName="tbl_pcpacqdata_latest";
					deviceTableName="viw_pcpdevice";
				}
				
				String sql="select t2.commstatus,count(1) from "+deviceTableName+" t "
						+ " left outer join "+tableName+" t2 on  t2.deviceId=t.id "
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
				if(deviceInfoList!=null){
					for(int i=0;i<deviceInfoList.size();i++){
						int commStatus=0;
						DeviceInfo deviceInfo=deviceInfoList.get(i);
						if(StringManagerUtils.stringToArrExistNum(orgId, deviceInfo.getOrgId())){
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
			result_json.append("\"item\":\""+languageResourceMap.get("all")+"\",");
			result_json.append("\"itemCode\":\"all\",");
			result_json.append("\"count\":"+total+"},");
			
			result_json.append("{\"id\":2,");
			result_json.append("\"item\":\""+languageResourceMap.get("online")+"\",");
			result_json.append("\"itemCode\":\"online\",");
			result_json.append("\"count\":"+online+"},");
			
			result_json.append("{\"id\":3,");
			result_json.append("\"item\":\""+languageResourceMap.get("goOnline")+"\",");
			result_json.append("\"itemCode\":\"goOnline\",");
			result_json.append("\"count\":"+goOnline+"},");
			
			result_json.append("{\"id\":4,");
			result_json.append("\"item\":\""+languageResourceMap.get("offline")+"\",");
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
	
	public String getHistoryQueryDeviceTypeStatData(String orgId,String deviceType,String commStatusStatValue,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		AlarmShowStyle alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
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
				+ " left outer join "+tableName+" t2 on t.id=t2.deviceId "
				+ " where t.orgid in("+orgId+") ";
		
		
		
		if(StringManagerUtils.isNotNull(commStatusStatValue)){
			sql+=" and decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"')='"+commStatusStatValue+"'";
		}
		sql+=" group by t.devicetypename,t.devicetype";
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
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public int getHistoryQueryDeviceListDataPage(String orgId,String deviceId,String deviceName,String deviceType,String FESdiagramResultStatValue,String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,String limit,String language){
		int dataPage=1;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try{
			String deviceTableName="tbl_device";
			String tableName="tbl_acqdata_latest";
			String calTableName="tbl_rpcacqdata_latest";
			
			String sql="select t.id from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceid=t.id"
					+ " left outer join "+calTableName+" t3 on t3.deviceid=t.id"
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNum(deviceType)){
				sql+= " and t.devicetype="+deviceType;
			}else{
				sql+= " and t.devicetype in ("+deviceType+")";
			}
			
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.deviceName='"+deviceName+"'";
			}
			
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				if(FESdiagramResultStatValue.equalsIgnoreCase(languageResourceMap.get("emptyMsg"))){
					sql+=" and (t3.resultcode=0 t3.resultcode is null)";
				}else{
					sql+=" and t3.resultcode="+MemoryDataManagerTask.getWorkTypeByName(FESdiagramResultStatValue,language).getResultCode();
				}
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'"+languageResourceMap.get("offline")+"',2,'"+languageResourceMap.get("goOnline")+"',decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"','"+languageResourceMap.get("stop")+"'))='"+runStatusStatValue+"'";
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
	
	public String getHistoryQueryDeviceList(String orgId,String deviceName,String deviceType,String FESdiagramResultStatValue,String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,Page pager,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		AlarmShowStyle alarmShowStyle=null;
		try{
			alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			int timeEfficiencyZoom=1;
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
				timeEfficiencyZoom=100;
			}
			
			String deviceTableName="tbl_device";
			String tableName="tbl_acqdata_latest";
			String calTableName="tbl_rpcacqdata_latest";
			String ddicName="historyQuery_Overview";
			DataDictionary ddic = null;
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			String columns = ddic.getTableHeader();
			
			if(timeEfficiencyUnitType==2){
				columns=columns.replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)");
			}else{
				columns=columns.replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)");
			}
			
			String sql="select t.id,t.devicename,c1.name as devicetypename,"
					+ " to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'),"
					+ " t2.commstatus,decode(t2.commstatus,1,'"+languageResourceMap.get("offline")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,"
					+ " t2.commtime,t2.commtimeefficiency*"+timeEfficiencyZoom+",t2.commrange,"
					+ " decode(t2.runstatus,null,2,t2.runstatus) as runstatus,decode(t2.commstatus,0,'"+languageResourceMap.get("offline")+"',2,'"+languageResourceMap.get("goOnline")+"',decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"')) as runStatusName,"
					+ " t2.runtime,t2.runtimeefficiency*"+timeEfficiencyZoom+",t2.runrange,"
					+ " t.calculateType "
					+ " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceid=t.id"
					+ " left outer join "+calTableName+" t3 on t3.deviceid=t.id"
					+ " left outer join tbl_devicetypeinfo c1 on c1.id=t.devicetype "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNum(deviceType)){
				sql+= " and t.devicetype="+deviceType;
			}else{
				sql+= " and t.devicetype in ("+deviceType+")";
			}
			
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.deviceName='"+deviceName+"'";
			}
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				if(FESdiagramResultStatValue.equalsIgnoreCase(languageResourceMap.get("emptyMsg"))){
					sql+=" and (t3.resultcode=0 t3.resultcode is null)";
				}else{
					sql+=" and t3.resultcode="+MemoryDataManagerTask.getWorkTypeByName(FESdiagramResultStatValue,language).getResultCode();
				}
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'"+languageResourceMap.get("offline")+"',2,'"+languageResourceMap.get("goOnline")+"',decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"'))='"+runStatusStatValue+"'";
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
			result_json.append("\"start_date\":\""+pager.getStart_date()+"\",");
			result_json.append("\"end_date\":\""+pager.getEnd_date()+"\",");
			result_json.append("\"totalCount\":"+totals+",");
			result_json.append("\"totalRoot\":[");
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				String deviceId=obj[0]+"";
				String commStatusName=obj[5]+"";
				String runStatusName=obj[10]+"";
				
				DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
				String protocolName="";
				AcqInstanceOwnItem acqInstanceOwnItem=null;
				AlarmInstanceOwnItem alarmInstanceOwnItem=null;
				if(deviceInfo!=null){
					acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(deviceInfo.getInstanceCode());
					if(acqInstanceOwnItem!=null){
						protocolName=acqInstanceOwnItem.getProtocol();
					}
					alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(deviceInfo.getAlarmInstanceCode());
				}
				
				
				
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
				
				result_json.append("{\"id\":"+obj[0]+",");
				result_json.append("\"deviceName\":\""+obj[1]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[2]+"\",");
				result_json.append("\"acqTime\":\""+obj[3]+"\",");
				result_json.append("\"commStatus\":"+obj[4]+",");
				result_json.append("\"commStatusName\":\""+commStatusName+"\",");
				result_json.append("\"commTime\":\""+obj[6]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[7]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
				result_json.append("\"commAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"runStatus\":"+obj[9]+",");
				result_json.append("\"runStatusName\":\""+runStatusName+"\",");
				result_json.append("\"runTime\":\""+obj[11]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[12]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[13])+"\",");
				result_json.append("\"runAlarmLevel\":"+runAlarmLevel+",");
				result_json.append("\"calculateType\":"+obj[14]+"},");
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]");
			result_json.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle)+"}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	public boolean exportHistoryQueryDeviceListData(User user,HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceName,String deviceType,String FESdiagramResultStatValue,
			String commStatusStatValue,String runStatusStatValue,String deviceTypeStatValue,Page pager,String language) throws IOException, SQLException{
		try{
			StringBuffer result_json = new StringBuffer();
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
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
			
			String deviceTableName="tbl_device";
			String tableName="tbl_acqdata_latest";
			String calTableName="tbl_rpcacqdata_latest";
			
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			
			List<Object> headRow = new ArrayList<>();
			for(int i=0;i<heads.length;i++){
				headRow.add(heads[i]);
			}
		    List<List<Object>> sheetDataList = new ArrayList<>();
		    sheetDataList.add(headRow);
			
		    String sql="select t.id,t.devicename,c1.name as devicetypename,"
					+ " to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'),"
					+ " t2.commstatus,decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,"
					+ " t2.commtime,t2.commtimeefficiency*"+timeEfficiencyZoom+",t2.commrange,"
					+ " decode(t2.runstatus,null,2,t2.runstatus) as runstatus,decode(t2.commstatus,0,'"+languageResourceMap.get("offline")+"',2,'"+languageResourceMap.get("goOnline")+"',decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"')) as runStatusName,"
					+ " t2.runtime,t2.runtimeefficiency*"+timeEfficiencyZoom+",t2.runrange,"
					+ " t.calculateType "
					+ " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on t2.deviceid=t.id"
					+ " left outer join "+calTableName+" t3 on t3.deviceid=t.id"
					+ " left outer join tbl_devicetypeinfo c1 on c1.id=t.devicetype "
					+ " where  t.orgid in ("+orgId+") ";
			if(StringManagerUtils.isNum(deviceType)){
				sql+= " and t.devicetype="+deviceType;
			}else{
				sql+= " and t.devicetype in ("+deviceType+")";
			}
			
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.deviceName='"+deviceName+"'";
			}
			if(StringManagerUtils.isNotNull(FESdiagramResultStatValue)){
				if(FESdiagramResultStatValue.equalsIgnoreCase(languageResourceMap.get("emptyMsg"))){
					sql+=" and (t3.resultcode=0 t3.resultcode is null)";
				}else{
					sql+=" and t3.resultcode="+MemoryDataManagerTask.getWorkTypeByName(FESdiagramResultStatValue,language).getResultCode();
				}
			}
			if(StringManagerUtils.isNotNull(commStatusStatValue)){
				sql+=" and decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"')='"+commStatusStatValue+"'";
			}
			if(StringManagerUtils.isNotNull(runStatusStatValue)){
				sql+=" and decode(t2.commstatus,0,'"+languageResourceMap.get("offline")+"',2,'"+languageResourceMap.get("goOnline")+"',decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"'))='"+runStatusStatValue+"'";
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
				result_json.append("{\"id\":"+(i+1)+",");
				result_json.append("\"deviceName\":\""+obj[1]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[2]+"\",");
				result_json.append("\"acqTime\":\""+obj[3]+"\",");
				result_json.append("\"commStatus\":\""+obj[4]+"\",");
				result_json.append("\"commStatusName\":\""+obj[5]+"\",");
				result_json.append("\"commTime\":\""+obj[6]+"\",");
				result_json.append("\"commTimeEfficiency\":\""+obj[7]+"\",");
				result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
				result_json.append("\"runStatus\":\""+obj[9]+"\",");
				result_json.append("\"runStatusName\":\""+obj[10]+"\",");
				result_json.append("\"runTime\":\""+obj[11]+"\",");
				result_json.append("\"runTimeEfficiency\":\""+obj[12]+"\",");
				result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[13])+"\",");
				result_json.append("\"calculateType\":\""+obj[14]+"\"}");
				
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
			ExcelUtils.export(response,fileName,title, sheetDataList,1);
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
		}
		return true;
	}
	
	public String getDeviceHistoryData(String orgId,String deviceId,String deviceName,
			String deviceType,String calculateType,
			Page pager,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		AlarmShowStyle alarmShowStyle=null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		try{
			try{
				alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
			}catch(Exception e){
				e.printStackTrace();
			}
			
//			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			int timeEfficiencyZoom=1;
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
				timeEfficiencyZoom=100;
			}
			
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
			Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
			
			String hisTableName="tbl_acqdata_hist";
			String deviceTableName="tbl_device";
			String calTableName="tbl_rpcacqdata_hist";
			String ddicName="historyQuery_HistoryData";
			DataDictionary ddic = null;
			List<String> ddicAcqColumnsList=new ArrayList<String>();
			
			DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
			
			String protocolName="";
			AcqInstanceOwnItem acqInstanceOwnItem=null;
			if(deviceInfo!=null){
				acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(deviceInfo.getInstanceCode());
				if(acqInstanceOwnItem!=null){
					protocolName=acqInstanceOwnItem.getProtocol();
				}
			}
			
			AlarmInstanceOwnItem alarmInstanceOwnItem=null;
			if(deviceInfo!=null){
				alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(deviceInfo.getAlarmInstanceCode());
			}
			ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			String columns = ddic.getTableHeader();
			
			String columnSql="select t.COLUMN_NAME from user_tab_cols t where t.TABLE_NAME=UPPER('"+hisTableName+"') order by t.COLUMN_ID";
			List<String> tableColumnsList=new ArrayList<String>();
			List<?> columnList = this.findCallSql(columnSql);
			for(int i=0;i<columnList.size();i++){
				tableColumnsList.add(columnList.get(i).toString());
			}
			String sql="select t2.id,t.devicename,"//0~1
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"//2
					+ "t2.commstatus,decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,"//3~4
					+ "t2.commtime,t2.commtimeefficiency*"+timeEfficiencyZoom+",t2.commrange,"//5~7
					+ "decode(t2.runstatus,null,2,t2.runstatus),decode(t2.commstatus,1,decode(t2.runstatus,1,'"+languageResourceMap.get("run")+"',0,'"+languageResourceMap.get("stop")+"','"+languageResourceMap.get("emptyMsg")+"'),'') as runStatusName,"//8~9
					+ "t2.runtime,t2.runtimeefficiency*"+timeEfficiencyZoom+",t2.runrange,"//10~12
					+ "t.calculateType";//13
			
			String[] ddicColumns=ddic.getSql().split(",");
			for(int i=0;i<ddicColumns.length;i++){
				if(StringManagerUtils.dataMappingKeyExistOrNot(loadProtocolMappingColumnMap, ddicColumns[i],false) && StringManagerUtils.existOrNot(tableColumnsList, ddicColumns[i],false)){
					ddicAcqColumnsList.add(ddicColumns[i]);
				}
			}
			for(int i=0;i<ddicAcqColumnsList.size();i++){
				sql+=",t2."+ddicAcqColumnsList.get(i);
			}
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+hisTableName+" t2 on t2.deviceId=t.id"
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
				String commStatusName=obj[4]+"";
				String runStatusName=obj[9]+"";
				
				StringBuffer alarmInfo = new StringBuffer();
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
				
				result_json.append("{\"id\":"+obj[0]+",");
				result_json.append("\"deviceId\":\""+deviceId+"\",");
				result_json.append("\"deviceName\":\""+obj[1]+"\",");
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
				result_json.append("\"calculateType\":"+obj[13]+",");
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
				
				for(int j=0;j<ddicAcqColumnsList.size();j++){
					String rawValue=obj[14+j]+"";
					String value=rawValue;
					ModbusProtocolConfig.Items item=null;
					if(protocol!=null){
						for(int k=0;k<protocol.getItems().size();k++){
							String col="";
							if(loadProtocolMappingColumnByTitleMap.containsKey(protocol.getItems().get(k).getTitle())){
								col=loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(k).getTitle()).getMappingColumn();
							}
							if(col!=null&&col.equalsIgnoreCase(ddicAcqColumnsList.get(j))){
								item=protocol.getItems().get(k);
								
								if("int".equalsIgnoreCase(item.getIFDataType()) || "uint".equalsIgnoreCase(item.getIFDataType()) || item.getIFDataType().contains("int")
										||"float32".equalsIgnoreCase(item.getIFDataType())
										||"float64".equalsIgnoreCase(item.getIFDataType())){
									if(value.toUpperCase().contains("E")){
					                 	value=StringManagerUtils.scientificNotationToNormal(value);
					                 }
								}
								
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
									alarmInfo.append("{\"item\":\""+ddicAcqColumnsList.get(j).replaceAll(" ", "")+"\","
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
										
									alarmInfo.append("{\"item\":\""+ddicAcqColumnsList.get(j).replaceAll(" ", "")+"\","
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
					result_json.append("\""+ddicAcqColumnsList.get(j).replaceAll(" ", "")+"\":\""+value+"\",");
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
			
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getDeviceHistoryData(String orgId,String deviceId,String deviceName,
			String deviceType,String calculateType,
			Page pager,
			int userNo,
			String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		StringBuffer columns = new StringBuffer();
		StringBuffer totalRoot = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		
		AlarmShowStyle alarmShowStyle=null;
		AcqInstanceOwnItem acqInstanceOwnItem=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		AlarmInstanceOwnItem alarmInstanceOwnItem=null;
		List<CalItem> calItemList=null;
		List<CalItem> inputItemList=null;
		UserInfo userInfo=null;
		
		ModbusProtocolConfig.Protocol protocol=null;
		
		String acqInstanceCode="";
		String displayInstanceCode="";
		String alarmInstanceCode="";
		
		DeviceInfo deviceInfo=null;
		String deviceInfoKey="DeviceInfo";
		
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
		Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
		
		int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
		String timeEfficiencyUnit=languageResourceMap.get("decimals");
		int timeEfficiencyZoom=1;
		if(timeEfficiencyUnitType==2){
			timeEfficiencyUnit="%";
			timeEfficiencyZoom=100;
		}
		try{
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
			
			userInfo=MemoryDataManagerTask.getUserInfoByNo(userNo+"");
			
			if(displayInstanceOwnItem!=null){
				protocol=MemoryDataManagerTask.getProtocolByName(displayInstanceOwnItem.getProtocol());
			}
			
			if(StringManagerUtils.stringToInteger(calculateType)==1){
				calItemList=MemoryDataManagerTask.getRPCCalculateItem(language);
				inputItemList=MemoryDataManagerTask.getRPCInputItem(language);
			}else if(StringManagerUtils.stringToInteger(calculateType)==2){
				calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
				inputItemList=MemoryDataManagerTask.getPCPInputItem(language);
			}else{
				calItemList=MemoryDataManagerTask.getAcqCalculateItem(language);
				inputItemList=new ArrayList<>();
			}
			
			String hisTableName="tbl_acqdata_hist";
			String deviceTableName="tbl_device";
			String calAndInputTableName="tbl_rpcacqdata_hist";
			
			
			if(StringManagerUtils.stringToInteger(calculateType)==1){
				calAndInputTableName="tbl_rpcacqdata_hist";
			}else if(StringManagerUtils.stringToInteger(calculateType)==2){
				calAndInputTableName="tbl_pcpacqdata_hist";
			}
			
			
			List<ModbusProtocolConfig.Items> protocolItems=new ArrayList<ModbusProtocolConfig.Items>();
			List<CalItem> displayCalItemList=new ArrayList<CalItem>();
			List<CalItem> displayInputItemList=new ArrayList<CalItem>();
			Map<String,DisplayInstanceOwnItem.DisplayItem> dailyTotalCalItemMap=new LinkedHashMap<>();
			
			columns.append("[");
			columns.append("{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",\"width\":50,\"children\":[] },");
			columns.append("{ \"header\":\""+languageResourceMap.get("deviceName")+"\",\"dataIndex\":\"deviceName\"},");
			columns.append("{ \"header\":\""+languageResourceMap.get("acqTime")+"\",\"dataIndex\":\"acqTime\",\"width\": 130},");
			columns.append("{ \"header\":\""+languageResourceMap.get("commStatus")+"\",\"dataIndex\":\"commStatusName\"},");
			
			String sql="select t2.id,t.devicename,"//0~1
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"//2
					+ "t2.commstatus,decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,"//3~4
					+ "decode(t2.runstatus,null,2,t2.runstatus) as runstatus,t.calculateType,"
					+ "t2.acqdata";
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
						+ " and decode(t.showlevel,null,9999,t.showlevel)>="+userInfo.getRoleShowLevel();
				displayItemSql+=" order by t.historySort,t.type,t.id";
				List<?> displayItemQueryList = this.findCallSql(displayItemSql);
				List<DisplayInstanceOwnItem.DisplayItem> displayItemList=new ArrayList<>();
				for(int i=0;i<displayItemQueryList.size();i++){
					Object[] obj=(Object[]) displayItemQueryList.get(i);
					DisplayInstanceOwnItem.DisplayItem displayItem=new DisplayInstanceOwnItem.DisplayItem();
					displayItem.setUnitId(StringManagerUtils.stringToInteger(obj[0]+""));
    				displayItem.setItemId(StringManagerUtils.stringToInteger(obj[1]+""));
    				displayItem.setItemName(obj[2]+"");
    				displayItem.setItemCode(obj[3]+"");
    				displayItem.setBitIndex(StringManagerUtils.stringToInteger(obj[4]+""));
    				displayItem.setShowLevel(StringManagerUtils.stringToInteger(obj[5]+""));
    				displayItem.setRealtimeSort(StringManagerUtils.stringToInteger(obj[6]+""));
    				displayItem.setHistorySort(StringManagerUtils.stringToInteger(obj[7]+""));
    				displayItem.setRealtimeCurveConf(obj[8]+"");
    				displayItem.setHistoryCurveConf(obj[9]+"");
    				displayItem.setType(StringManagerUtils.stringToInteger(obj[10]+""));
    				displayItemList.add(displayItem);
				}
				
				
				//采集项
				for(int j=0;j<protocol.getItems().size();j++){
					if((!"w".equalsIgnoreCase(protocol.getItems().get(j).getRWType())) 
							&& (StringManagerUtils.existDisplayItem(displayInstanceOwnItem.getItemList(), protocol.getItems().get(j).getTitle(), false))){
						for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
							if(displayInstanceOwnItem.getItemList().get(k).getType()==0 
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
								CalItem calItem=MemoryDataManagerTask.getSingleCalItem(header, calItemList);
								if(calItem!=null){
									unit=calItem.getUnit();
								}
							}
							
							
						}else if(displayItemList.get(k).getType()==3){
							CalItem calItem=MemoryDataManagerTask.getSingleCalItem(header, inputItemList);
							if(calItem!=null){
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
						
						if(StringManagerUtils.isNotNull(unit)){
							header+="("+unit+")";
						}
						
						columns.append("{ \"header\":\""+header+"\",\"dataIndex\":\""+dataIndex+"\"},");
					}
				}
			}

			if(StringManagerUtils.stringToInteger(calculateType)>0){
				for(int i=0;i<displayCalItemList.size();i++){
					String column=displayCalItemList.get(i).getCode();
					if("resultName".equalsIgnoreCase(column)){
						column="resultCode";
					}else if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
						column=column+"*"+timeEfficiencyZoom+" as "+column;
					}
					sql+=",t3."+column;
				}
				if(displayInputItemList.size()>0){
					sql+=",t3.productiondata";
				}
			}else{
				for(int i=0;i<displayCalItemList.size();i++){
					String column=displayCalItemList.get(i).getCode();
					if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
						column=column+"*"+timeEfficiencyZoom+" as "+column;
					}
					sql+=",t2."+column;
				}
			}
			
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+hisTableName+" t2 on t2.deviceid=t.id";
			if(StringManagerUtils.isNotNull(calAndInputTableName)&&(displayCalItemList.size()>0 || displayInputItemList.size()>0)){
				sql+=" left outer join "+calAndInputTableName+" t3 on t3.deviceid=t2.deviceid and t3.acqtime=t2.acqtime ";
			}
			
			sql+= " where t2.acqTime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') "
				+ " and t.id="+deviceId+""
				+ " order by t2.acqtime desc";
			int maxvalue=pager.getLimit()+pager.getStart();
			String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
			
			List<?> list = this.findCallSql(finalSql);
			String startTime=pager.getStart_date();
			String endTime=pager.getEnd_date();
			if(list!=null && list.size()>0){
				startTime=((Object[])list.get(list.size()-1))[2]+"";
				endTime=((Object[])list.get(0))[2]+"";
			}
			
			String dailyTotalDatasql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t.itemcolumn,t.itemname,"
					+ "t.totalvalue,t.todayvalue "
					+ " from TBL_DAILYTOTALCALCULATE_HIST t,"+hisTableName+" t2 "
					+ " where t.deviceId=t2.deviceId and t.acqTime=t2.acqTime"
					+ " and t.acqTime between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') "
					+ " and t.deviceId="+deviceId
					+ " order by t.acqTime desc";
			
			int totals=this.getTotalCountRows(sql);
			
			List<?> dailyTotalDatasList = this.findCallSql(dailyTotalDatasql);
			
			if(columns.toString().endsWith(",")){
				columns.deleteCharAt(columns.length() - 1);
			}
			columns.append("]");
			
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"start_date\":\""+pager.getStart_date()+"\",");
			result_json.append("\"end_date\":\""+pager.getEnd_date()+"\",");
			result_json.append("\"totalCount\":"+totals+",");
			result_json.append("\"totalRoot\":[");
			
			for(int i=0;i<list.size();i++){
				List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
				
				Object[] obj=(Object[]) list.get(i);
				String acqTime=obj[2]+"";
				int commStatus=StringManagerUtils.stringToInteger(obj[3]+"");
				String commStatusName=obj[4]+"";
				int runStatus=StringManagerUtils.stringToInteger(obj[5]+"");
				String runStatusName="";
				String acqData=StringManagerUtils.CLOBObjectToString(obj[7]);
				
				if(commStatus==1){
					if(runStatus==1){
						runStatusName=languageResourceMap.get("run");
					}else if(runStatus==0){
						runStatusName=languageResourceMap.get("stop");
					}else{
						runStatusName=languageResourceMap.get("emptyMsg");
					}
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
				
				result_json.append("{\"id\":"+obj[0]+",");
				result_json.append("\"deviceId\":\""+deviceId+"\",");
				result_json.append("\"deviceName\":\""+obj[1]+"\",");
				result_json.append("\"acqTime\":\""+acqTime+"\",");
				result_json.append("\"commStatus\":"+obj[3]+",");
				result_json.append("\"commStatusName\":\""+obj[4]+"\",");
				result_json.append("\"commAlarmLevel\":"+commAlarmLevel+",");
				result_json.append("\"runStatus\":"+runStatus+",");
				result_json.append("\"runStatusName\":\""+runStatusName+"\",");
				result_json.append("\"runAlarmLevel\":"+runAlarmLevel+",");
				result_json.append("\"calculateType\":"+obj[6]+",");
				result_json.append("\"details\":\"\",");
				
				if(displayInputItemList.size()>0){
					String productionData=(obj[obj.length-2]+"").replaceAll("null", "");
					if(StringManagerUtils.stringToInteger(calculateType)==1){
						type = new TypeToken<RPCCalculateRequestData>() {}.getType();
						RPCCalculateRequestData rpcProductionData=gson.fromJson(productionData, type);
						for(int j=0;j<displayInputItemList.size();j++){
							String columnName=displayInputItemList.get(j).getName();
							String rawColumnName=columnName;
							String value="";
							String rawValue=value;
							String addr="";
							String column=displayInputItemList.get(j).getCode();
							String columnDataType="";
							String resolutionMode="";
							String bitIndex="";
							String unit=displayInputItemList.get(j).getUnit();
							int sort=9999;
							for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
								if(column.equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
									sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
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
									if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
										columnName=columnName.replace("油压", "管压");
									}
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
							ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,3);
							protocolItemResolutionDataList.add(protocolItemResolutionData);
						}
					}else if(StringManagerUtils.stringToInteger(calculateType)==2){
						type = new TypeToken<PCPCalculateRequestData>() {}.getType();
						PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
						for(int j=0;j<displayInputItemList.size();j++){
							String columnName=displayInputItemList.get(j).getName();
							String rawColumnName=columnName;
							String value="";
							String rawValue=value;
							String addr="";
							String column=displayInputItemList.get(j).getCode();
							String columnDataType="";
							String resolutionMode="";
							String bitIndex="";
							String unit=displayInputItemList.get(j).getUnit();
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
				
				for(int j=0;j<displayCalItemList.size();j++){
					int index=j+8;
					if(index<obj.length-1){
						String columnName=displayCalItemList.get(j).getName();
						String rawColumnName=columnName;
						String value=obj[index]+"";
						if(obj[index] instanceof CLOB || obj[index] instanceof Clob){
							value=StringManagerUtils.CLOBObjectToString(obj[index]);
						}
						String rawValue=value;
						String addr="";
						String column=displayCalItemList.get(j).getCode();
						String columnDataType="";
						String resolutionMode="";
						String bitIndex="";
						String unit=displayCalItemList.get(j).getUnit();
						int sort=9999;
						for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
							if(column.equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
								sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
								//如果是工况
								if("resultCode".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())||"resultName".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
									WorkType workType=MemoryDataManagerTask.getWorkTypeByCode(value,language);
									if(workType!=null){
										value=workType.getResultName();
										for(AlarmInstanceOwnItem.AlarmItem alarmItem:alarmInstanceOwnItem.getItemList()){
											if(alarmItem.getAlarmLevel()>0 && alarmItem.getType()==4 && alarmItem.getItemCode().equalsIgnoreCase(workType.getResultCode()+"")){
												resultAlarmLevel=alarmItem.getAlarmLevel();
											}
										}
										
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
													ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column+"_"+bitIndex,columnDataType,resolutionMode,item.getMeaning().get(l).getValue()+"",unit,sort,0);
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
					for(int j=0;j<dailyTotalDatasList.size();j++){
						Object[] dailyTotalDataObj=(Object[]) dailyTotalDatasList.get(j);
						if(dailyTotalCalItemMap.containsKey( (dailyTotalDataObj[1]+"").toUpperCase() )  && acqTime.equalsIgnoreCase(dailyTotalDataObj[0]+"")  ){
							DisplayInstanceOwnItem.DisplayItem displayItem=dailyTotalCalItemMap.get( (dailyTotalDataObj[1]+"").toUpperCase() );
							if(displayItem!=null){
								String unit="";
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
							}
						}
					}
				}
				
				alarmInfo.append("[");
				//计算项报警判断
				if(alarmInstanceOwnItem!=null){
					for(int j=0;j<protocolItemResolutionDataList.size();j++){
						String column=protocolItemResolutionDataList.get(j).getColumn();
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
					//判断报警
					if(item!=null&&alarmInstanceOwnItem!=null){
						for(int k=0;k<alarmInstanceOwnItem.getItemList().size();k++){
							int alarmType=alarmInstanceOwnItem.getItemList().get(k).getType();
							if(alarmType<=2&&item.getTitle().equalsIgnoreCase(alarmInstanceOwnItem.getItemList().get(k).getItemName()) && item.getAddr()==alarmInstanceOwnItem.getItemList().get(k).getItemAddr()){
								if(alarmType==2){//数据量报警
									alarmInfo.append("{\"item\":\""+protocolItemResolutionDataList.get(j).getColumn()+"\","
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
										
									alarmInfo.append("{\"item\":\""+protocolItemResolutionDataList.get(j).getColumn()+"\","
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
				}
				result_json.append("\"resultAlarmLevel\":\""+resultAlarmLevel+"\",");
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
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public boolean exportDeviceHistoryData(User user,HttpServletResponse response,String fileName,String title,
			String orgId,String deviceId,String deviceName,String deviceType,String calculateType,Page pager,int userNo,String language){
		ConfigFile configFile=Config.getInstance().configFile;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		
		AlarmShowStyle alarmShowStyle=null;
		AcqInstanceOwnItem acqInstanceOwnItem=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		AlarmInstanceOwnItem alarmInstanceOwnItem=null;
		List<CalItem> calItemList=null;
		List<CalItem> inputItemList=null;
		UserInfo userInfo=null;
		
		ModbusProtocolConfig.Protocol protocol=null;
		
		String acqInstanceCode="";
		String displayInstanceCode="";
		String alarmInstanceCode="";
		
		DeviceInfo deviceInfo=null;
		String deviceInfoKey="DeviceInfo";
		
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
		Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
		String timeEfficiencyUnit=languageResourceMap.get("decimals");
		int timeEfficiencyZoom=1;
		if(timeEfficiencyUnitType==2){
			timeEfficiencyUnit="%";
			timeEfficiencyZoom=100;
		}
		try{
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
			
			userInfo=MemoryDataManagerTask.getUserInfoByNo(userNo+"");
			
			if(acqInstanceOwnItem!=null){
				protocol=MemoryDataManagerTask.getProtocolByName(acqInstanceOwnItem.getProtocol());
			}
			
			if(StringManagerUtils.stringToInteger(calculateType)==1){
				calItemList=MemoryDataManagerTask.getRPCCalculateItem(language);
				inputItemList=MemoryDataManagerTask.getRPCInputItem(language);
			}else if(StringManagerUtils.stringToInteger(calculateType)==2){
				calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
				inputItemList=MemoryDataManagerTask.getPCPInputItem(language);
			}else{
				calItemList=MemoryDataManagerTask.getAcqCalculateItem(language);
				inputItemList=new ArrayList<>();
			}
			
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			
			String head=languageResourceMap.get("idx")+","+languageResourceMap.get("deviceName")+","+languageResourceMap.get("acqTime")+","+languageResourceMap.get("commStatus")+"";
			
			String field="id,deviceName,acqTime,commStatusName";
			
		    String hisTableName="tbl_acqdata_hist";
			String deviceTableName="tbl_device";
			String calAndInputTableName="tbl_rpcacqdata_hist";
			
			if(StringManagerUtils.stringToInteger(calculateType)==1){
				calAndInputTableName="tbl_rpcacqdata_hist";
			}else if(StringManagerUtils.stringToInteger(calculateType)==2){
				calAndInputTableName="tbl_pcpacqdata_hist";
			}
			
			List<ModbusProtocolConfig.Items> protocolItems=new ArrayList<ModbusProtocolConfig.Items>();
			List<CalItem> displayCalItemList=new ArrayList<CalItem>();
			List<CalItem> displayInputItemList=new ArrayList<CalItem>();
			Map<String,DisplayInstanceOwnItem.DisplayItem> dailyTotalCalItemMap=new LinkedHashMap<>();
			
			String sql="select t2.id,t.devicename,"//0~1
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"//2
					+ "t2.commstatus,decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,"//3~4
					+ "decode(t2.runstatus,null,2,t2.runstatus) as runstatus,t.calculateType,"
					+ "t2.acqdata";
			if(displayInstanceOwnItem!=null&&userInfo!=null&&protocol!=null){
				Collections.sort(displayInstanceOwnItem.getItemList());
				//采集项
				for(int j=0;j<protocol.getItems().size();j++){
					if((!"w".equalsIgnoreCase(protocol.getItems().get(j).getRWType())) 
							&& (StringManagerUtils.existDisplayItem(displayInstanceOwnItem.getItemList(), protocol.getItems().get(j).getTitle(), false))){
						for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
							if(displayInstanceOwnItem.getItemList().get(k).getType()==0 
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
										&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
										&& calItem.getCode().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode())){
									displayInputItemList.add(calItem);
									break;
								}
							}
							
						}
					}
				}
				
				for(int k=0;k<displayInstanceOwnItem.getItemList().size();k++){
					if(displayInstanceOwnItem.getItemList().get(k).getType()!=2 && displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()){
						String header=displayInstanceOwnItem.getItemList().get(k).getItemName();
						String dataIndex=displayInstanceOwnItem.getItemList().get(k).getItemCode();
						
						String unit="";
						if(displayInstanceOwnItem.getItemList().get(k).getType()==0){
							ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, header);
							if(item!=null){
								unit=item.getUnit();
							}
						}else if(displayInstanceOwnItem.getItemList().get(k).getType()==1){
							if(dailyTotalCalItemMap.containsKey(dataIndex.toUpperCase())){
								DisplayInstanceOwnItem.DisplayItem displayItem=dailyTotalCalItemMap.get(dataIndex.toUpperCase()) ;
								if(displayItem!=null){
									ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, displayItem.getItemSourceName());
									if(item!=null){
										unit=item.getUnit();
									}
								}
							}else{
								CalItem calItem=MemoryDataManagerTask.getSingleCalItem(header, calItemList);
								if(calItem!=null){
									unit=calItem.getUnit();
								}
							}
						}else if(displayInstanceOwnItem.getItemList().get(k).getType()==3){
							CalItem calItem=MemoryDataManagerTask.getSingleCalItem(header, inputItemList);
							if(calItem!=null){
								unit=calItem.getUnit();
							}
						}
						
						
						for(ModbusProtocolConfig.Items item:protocol.getItems()){
							if(item.getResolutionMode()==0 
									&& header.equalsIgnoreCase(item.getTitle())
									&& item.getMeaning()!=null
									&& item.getMeaning().size()>0
									){//开关量处理
								for(ModbusProtocolConfig.ItemsMeaning itemsMeaning: item.getMeaning()){
									if(displayInstanceOwnItem.getItemList().get(k).getBitIndex()==itemsMeaning.getValue()){
										header=itemsMeaning.getMeaning();
										dataIndex+="_"+itemsMeaning.getValue();
										break;
									}
								}
								break;
							}
						}
						
						if(StringManagerUtils.isNotNull(unit)){
							header+="("+unit+")";
						}
						
						field+=","+dataIndex;
						head+=","+header;
					}
				}
			}

			if(StringManagerUtils.stringToInteger(calculateType)>0){
				for(int i=0;i<displayCalItemList.size();i++){
					String column=displayCalItemList.get(i).getCode();
					if("resultName".equalsIgnoreCase(column)){
						column="resultCode";
					}else if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
						column=column+"*"+timeEfficiencyZoom+" as "+column;
					}
					sql+=",t3."+column;
				}
				if(displayInputItemList.size()>0){
					sql+=",t3.productiondata";
				}
			}else{
				for(int i=0;i<displayCalItemList.size();i++){
					String column=displayCalItemList.get(i).getCode();
					if("commtimeEfficiency".equalsIgnoreCase(column) || "runtimeEfficiency".equalsIgnoreCase(column)){
						column=column+"*"+timeEfficiencyZoom+" as "+column;
					}
					sql+=",t2."+column;
				}
			}
			
			sql+= " from "+deviceTableName+" t "
					+ " left outer join "+hisTableName+" t2 on t2.deviceid=t.id";
			if(StringManagerUtils.isNotNull(calAndInputTableName)&&(displayCalItemList.size()>0 || displayInputItemList.size()>0)){
				sql+=" left outer join "+calAndInputTableName+" t3 on t3.deviceid=t2.deviceid and t3.acqtime=t2.acqtime ";
			}
			
			sql+= " where t2.acqTime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') "
				+ " and t.id="+deviceId+""
				+ "  order by t2.acqtime desc";
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			
			List<?> list = this.findCallSql(finalSql);
			String startTime=pager.getStart_date();
			String endTime=pager.getEnd_date();
			if(list!=null && list.size()>0){
				startTime=((Object[])list.get(list.size()-1))[2]+"";
				endTime=((Object[])list.get(0))[2]+"";
			}
			
			String dailyTotalDatasql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
					+ "t.itemcolumn,t.itemname,"
					+ "t.totalvalue,t.todayvalue "
					+ " from TBL_DAILYTOTALCALCULATE_HIST t,"+hisTableName+" t2 "
					+ " where t.deviceId=t2.deviceId and t.acqTime=t2.acqTime"
					+ " and t.acqTime between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') "
					+ " and t.deviceId="+deviceId
					+ " order by t.acqTime desc";
			List<?> dailyTotalDatasList = this.findCallSql(dailyTotalDatasql);
			
			StringBuffer result_json = null;
			List<Object> record=null;
			JSONObject jsonObject=null;
			Object[] obj=null;
			
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			
			List<Object> headRow = new ArrayList<>();
			for(int i=0;i<heads.length;i++){
				headRow.add(heads[i]);
			}
		    List<List<Object>> sheetDataList = new ArrayList<>();
		    sheetDataList.add(headRow);
			
			int row=0;
			for(int i=0;i<list.size();i++){
				List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
				obj=(Object[]) list.get(i);
				record = new ArrayList<>();
				result_json = new StringBuffer();
				
				String acqTime=obj[2]+"";
				int commStatus=StringManagerUtils.stringToInteger(obj[3]+"");
				String commStatusName=obj[4]+"";
				int runStatus=StringManagerUtils.stringToInteger(obj[5]+"");
				String runStatusName="";
				String acqData=StringManagerUtils.CLOBObjectToString(obj[7]);
				if(commStatus==1){
					if(runStatus==1){
						runStatusName=languageResourceMap.get("run");
					}else if(runStatus==0){
						runStatusName=languageResourceMap.get("stop");
					}else{
						runStatusName=languageResourceMap.get("emptyMsg");
					}
				}
				
				row++;
				result_json.append("{\"id\":"+row+",");
				result_json.append("\"deviceId\":\""+deviceId+"\",");
				result_json.append("\"deviceName\":\""+obj[1]+"\",");
				result_json.append("\"acqTime\":\""+acqTime+"\",");
				result_json.append("\"commStatus\":"+obj[3]+",");
				result_json.append("\"commStatusName\":\""+obj[4]+"\",");
				result_json.append("\"runStatus\":"+runStatus+",");
				result_json.append("\"runStatusName\":\""+runStatusName+"\",");
				result_json.append("\"calculateType\":"+obj[6]+",");
				
				
				if(displayInputItemList.size()>0){
					String productionData=(obj[obj.length-1]+"").replaceAll("null", "");
					if(StringManagerUtils.stringToInteger(calculateType)==1){
						type = new TypeToken<RPCCalculateRequestData>() {}.getType();
						RPCCalculateRequestData rpcProductionData=gson.fromJson(productionData, type);
						for(int j=0;j<displayInputItemList.size();j++){
							String columnName=displayInputItemList.get(j).getName();
							String rawColumnName=columnName;
							String value="";
							String rawValue=value;
							String addr="";
							String column=displayInputItemList.get(j).getCode();
							String columnDataType="";
							String resolutionMode="";
							String bitIndex="";
							String unit=displayInputItemList.get(j).getUnit();
							int sort=9999;
							for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
								if(column.equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
									sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
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
									if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
										columnName=columnName.replace("油压", "管压");
									}
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
							ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,3);
							protocolItemResolutionDataList.add(protocolItemResolutionData);
						}
					}else if(StringManagerUtils.stringToInteger(calculateType)==2){
						type = new TypeToken<PCPCalculateRequestData>() {}.getType();
						PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
						for(int j=0;j<displayInputItemList.size();j++){
							String columnName=displayInputItemList.get(j).getName();
							String rawColumnName=columnName;
							String value="";
							String rawValue=value;
							String addr="";
							String column=displayInputItemList.get(j).getCode();
							String columnDataType="";
							String resolutionMode="";
							String bitIndex="";
							String unit=displayInputItemList.get(j).getUnit();
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
				
				for(int j=0;j<displayCalItemList.size();j++){
					int index=j+8;
					if(index<obj.length-1){
						String columnName=displayCalItemList.get(j).getName();
						String rawColumnName=columnName;
						String value=obj[index]+"";
						if(obj[index] instanceof CLOB || obj[index] instanceof Clob){
							value=StringManagerUtils.CLOBObjectToString(obj[index]);
						}
						String rawValue=value;
						String addr="";
						String column=displayCalItemList.get(j).getCode();
						String columnDataType="";
						String resolutionMode="";
						String bitIndex="";
						String unit=displayCalItemList.get(j).getUnit();
						int sort=9999;
						for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
							if(column.equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
								sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
								//如果是工况
								if("resultCode".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())||"resultName".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
									WorkType workType=MemoryDataManagerTask.getWorkTypeByCode(value,language);
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
													for(int m=0;m<displayInstanceOwnItem.getItemList().size();m++){
														if(displayInstanceOwnItem.getItemList().get(m).getItemCode().equalsIgnoreCase(column) && displayInstanceOwnItem.getItemList().get(m).getBitIndex()==item.getMeaning().get(l).getValue() ){
															sort=displayInstanceOwnItem.getItemList().get(m).getRealtimeSort();
															break;
														}
													}
													value="";
													rawValue="";
													bitIndex=item.getMeaning().get(l).getValue()+"";
													ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column+"_"+bitIndex,columnDataType,resolutionMode,item.getMeaning().get(l).getValue()+"",unit,sort,0);
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
					for(int j=0;j<dailyTotalDatasList.size();j++){
						Object[] dailyTotalDataObj=(Object[]) dailyTotalDatasList.get(j);
						if(dailyTotalCalItemMap.containsKey( (dailyTotalDataObj[1]+"").toUpperCase() )  && acqTime.equalsIgnoreCase(dailyTotalDataObj[0]+"")  ){
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
										0);
								protocolItemResolutionDataList.add(protocolItemResolutionData);
							}
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
				
				if(result_json.toString().endsWith(",")){
					result_json.deleteCharAt(result_json.length() - 1);
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
			
			ExcelUtils.export(response,fileName,title, sheetDataList,1);
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
		}
		
		return true;
	}
	
	public String getDeviceHistoryDetailsData(String deviceId,String deviceName,String deviceType,String recordId,String calculateType,int userNo,String language){
		StringBuffer result_json = new StringBuffer();
		try{
			int items=3;
			StringBuffer info_json = new StringBuffer();
			int dataSaveMode=1;
			AlarmShowStyle alarmShowStyle=null;
			AcqInstanceOwnItem acqInstanceOwnItem=null;
			DisplayInstanceOwnItem displayInstanceOwnItem=null;
			AlarmInstanceOwnItem alarmInstanceOwnItem=null;
			
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
			Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			int timeEfficiencyZoom=1;
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
				timeEfficiencyZoom=100;
			}
			
			List<CalItem> calItemList=null;
			List<CalItem> inputItemList=null;
			UserInfo userInfo=null;
			String tableName="tbl_acqdata_hist";
			String calAndInputTableName="";
			String deviceTableName="tbl_device";
			DeviceInfo deviceInfo=null;
			
			if(StringManagerUtils.stringToInteger(calculateType)==1){
				calAndInputTableName="tbl_rpcacqdata_hist";
			}else if(StringManagerUtils.stringToInteger(calculateType)==2){
				calAndInputTableName="tbl_pcpacqdata_hist";
			}

			
			deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
			userInfo=MemoryDataManagerTask.getUserInfoByNo(userNo+"");
			if(deviceInfo!=null){
				acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(deviceInfo.getInstanceCode());
				displayInstanceOwnItem=MemoryDataManagerTask.getDisplayInstanceOwnItemByCode(deviceInfo.getDisplayInstanceCode());
				alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(deviceInfo.getAlarmInstanceCode());
			}
			
			alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
			
			if(StringManagerUtils.stringToInteger(calculateType)==1){
				calItemList=MemoryDataManagerTask.getRPCCalculateItem(language);
				inputItemList=MemoryDataManagerTask.getRPCInputItem(language);
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
				
				ModbusProtocolConfig.Protocol protocol=null;
				if(displayInstanceOwnItem!=null){
					protocol=MemoryDataManagerTask.getProtocolByName(displayInstanceOwnItem.getProtocol());
				}
				
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
											&& displayInstanceOwnItem.getItemList().get(k).getShowLevel()>=userInfo.getRoleShowLevel()
											&& calItem.getCode().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(k).getItemCode())){
										displayInputItemList.add(calItem);
										break;
									}
								}
								
							}
						}
					}
					String sql="select t.id,t.devicename,to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'), "//0~2
							+ "t2.commstatus,decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,decode(t2.commstatus,1,0,100) as commAlarmLevel,"//3~5
							+ "t2.acqdata ";//6
					
					for(int i=0;i<displayCalItemList.size();i++){
						String column=displayCalItemList.get(i).getCode();
						if(StringManagerUtils.stringToInteger(calculateType)>0){
							if("resultName".equalsIgnoreCase(displayCalItemList.get(i).getCode())){
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
							+ " left outer join "+tableName+" t2 on t2.deviceId=t.id";
					if(StringManagerUtils.isNotNull(calAndInputTableName) && (displayCalItemList.size()>0 || displayInputItemList.size()>0)){
						sql+=" left outer join "+calAndInputTableName+" t3 on t3.deviceid=t.id and t2.acqtime=t3.acqtime";
					}
					sql+= " where  t2.id="+recordId;
					List<?> list = this.findCallSql(sql);
					if(list.size()>0){
						int row=1;
						Object[] obj=(Object[]) list.get(0);
						String acqTime=obj[2]+"";
						String acqData=StringManagerUtils.CLOBObjectToString(obj[6]);
						
						if(displayInputItemList.size()>0){
							String productionData=(obj[obj.length-1]+"").replaceAll("null", "");
							Gson gson = new Gson();
							java.lang.reflect.Type type=null;
							if(StringManagerUtils.stringToInteger(calculateType)==1){
								type = new TypeToken<RPCCalculateRequestData>() {}.getType();
								RPCCalculateRequestData rpcProductionData=gson.fromJson(productionData, type);
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
											if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
												columnName=columnName.replace("油压", "管压");
											}
										}else if("CasingPressure".equalsIgnoreCase(column) && rpcProductionData.getProduction()!=null ){
											value=rpcProductionData.getProduction().getCasingPressure()+"";
											if(deviceInfo!=null && deviceInfo.getApplicationScenarios()==0){
												columnName=columnName.replace("油压", "管压");
											}
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
										if("resultCode".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode()) || "resultName".equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(l).getItemCode())){
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
														if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column) && displayInstanceOwnItem.getItemList().get(l).getType()!=2){
															sort=displayInstanceOwnItem.getItemList().get(l).getRealtimeSort();
															break;
														}
													}
													ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort,0);
													protocolItemResolutionDataList.add(protocolItemResolutionData);
												}
											}else{//如果是数据量
												for(int l=0;l<displayInstanceOwnItem.getItemList().size();l++){
													if(displayInstanceOwnItem.getItemList().get(l).getItemCode().equalsIgnoreCase(column)){
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
									+ " from TBL_DAILYTOTALCALCULATE_HIST t "
									+ " where t.deviceid="+deviceId
									+ " and t.acqtime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
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
													if("resultCode".equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getColumn()) || "resultName".equalsIgnoreCase(finalProtocolItemResolutionDataList.get(index).getColumn())){
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
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getHistoryQueryCurveData(String deviceId,String deviceName,String deviceType,String calculateType,String startDate,String endDate,int userNo,String language)throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer itemsBuff = new StringBuffer();
		StringBuffer itemsCodeBuff = new StringBuffer();
		StringBuffer curveConfBuff = new StringBuffer();
		int vacuateThreshold=Config.getInstance().configFile.getAp().getOthers().getVacuateThreshold();
		UserInfo userInfo=null;
		List<CalItem> calItemList=null;
		List<CalItem> inputItemList=null;
		DisplayInstanceOwnItem displayInstanceOwnItem=null;
		String displayInstanceCode="";
		String graphicSet="{}";
		String tableName="tbl_acqdata_hist";
		String deviceTableName="tbl_device";
		String graphicSetTableName="tbl_devicegraphicset";
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
		try{
			try{
				userInfo=MemoryDataManagerTask.getUserInfoByNo(userNo+"");
				DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
				if(deviceInfo!=null){
					displayInstanceCode=deviceInfo.getDisplayInstanceCode()+"";
				}
				
				displayInstanceOwnItem=MemoryDataManagerTask.getDisplayInstanceOwnItemByCode(displayInstanceCode);
				if(displayInstanceOwnItem!=null){
					Collections.sort(displayInstanceOwnItem.getItemList());
				}
				
				if(StringManagerUtils.stringToInteger(calculateType)==1){
					calItemList=MemoryDataManagerTask.getRPCCalculateItem(language);
					inputItemList=MemoryDataManagerTask.getRPCInputItem(language);
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
			
			String graphicSetSql="select t.graphicstyle from "+graphicSetTableName+" t where t.deviceId="+deviceId;
			List<?> graphicSetList = this.findCallSql(graphicSetSql);
			
			if(displayInstanceOwnItem!=null){
				Collections.sort(displayInstanceOwnItem.getItemList(),new Comparator<DisplayInstanceOwnItem.DisplayItem>(){
					@Override
					public int compare(DisplayInstanceOwnItem.DisplayItem item1,DisplayInstanceOwnItem.DisplayItem item2){
						Gson gson = new Gson();
						java.lang.reflect.Type type=null;
						int sort1=0;
						int sort2=0;
						boolean yAxisOpposite1=false;
						boolean yAxisOpposite2=false;
						type = new TypeToken<CurveConf>() {}.getType();
						CurveConf historyCurveConfObj1=gson.fromJson(item1.getHistoryCurveConf(), type);
						
						type = new TypeToken<CurveConf>() {}.getType();
						CurveConf historyCurveConfObj2=gson.fromJson(item2.getHistoryCurveConf(), type);
						
						if(historyCurveConfObj1!=null){
							sort1=historyCurveConfObj1.getSort();
							yAxisOpposite1=historyCurveConfObj1.getYAxisOpposite();
						}
						
						if(historyCurveConfObj2!=null){
							sort2=historyCurveConfObj2.getSort();
							yAxisOpposite2=historyCurveConfObj2.getYAxisOpposite();
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
								CurveConf historyCurveConfObj=gson.fromJson(displayInstanceOwnItem.getItemList().get(j).getHistoryCurveConf(), reflectType);
								
								if(historyCurveConfObj!=null && historyCurveConfObj.getSort()>0 && displayInstanceOwnItem.getItemList().get(j).getShowLevel()>=userInfo.getRoleShowLevel()){
									String itemname=displayInstanceOwnItem.getItemList().get(j).getItemName();
									String bitindex=displayInstanceOwnItem.getItemList().get(j).getBitIndex()+"";
									String historycurveconf=displayInstanceOwnItem.getItemList().get(j).getHistoryCurveConf();
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
												curveConfList.add(historycurveconf.replaceAll("null", ""));
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
										
										calItemNameList.add(itemName);
										calItemCurveConfList.add(historycurveconf.replaceAll("null", ""));
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
										
										inputItemNameList.add(itemName);
										inputItemCurveConfList.add(historycurveconf.replaceAll("null", ""));
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
				
				String curveItemsSql="select t4.itemname,t4.bitindex,t4.historycurveconf,t4.itemcode,t4.type "
						+ " from "+deviceTableName+" t,tbl_protocoldisplayinstance t2,tbl_display_unit_conf t3,tbl_display_items2unit_conf t4 "
						+ " where t.displayinstancecode=t2.code and t2.displayunitid=t3.id and t3.id=t4.unitid and t4.type<>2 "
						+ " and t.id="+deviceId+" and t4.historycurveconf is not null "
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
									String historycurveconf=itemObj[2]+"";
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
												curveConfList.add(historycurveconf.replaceAll("null", ""));
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
										
										calItemNameList.add(itemName);
										calItemCurveConfList.add(historycurveconf.replaceAll("null", ""));
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
										
										inputItemNameList.add(itemName);
										inputItemCurveConfList.add(historycurveconf.replaceAll("null", ""));
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
			
			itemsCodeBuff.append("[");
			for(int i=0;i<itemColumnList.size();i++){
				itemsCodeBuff.append("\""+itemColumnList.get(i)+"\",");
			}
			for(int i=0;i<calItemColumnList.size();i++){
				itemsCodeBuff.append("\""+calItemColumnList.get(i)+"\",");
			}
			for(int i=0;i<inputItemColumnList.size();i++){
				itemsCodeBuff.append("\""+inputItemColumnList.get(i)+"\",");
			}
			if (itemsCodeBuff.toString().endsWith(",")) {
				itemsCodeBuff.deleteCharAt(itemsCodeBuff.length() - 1);
			}
			itemsCodeBuff.append("]");
			
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
			
			String minAcqTime="";
			String maxAcqTime="";
			result_json.append("{\"deviceName\":\""+deviceName+"\","
					+ "\"startDate\":\""+startDate+"\","
					+ "\"endDate\":\""+endDate+"\","
					+ "\"curveCount\":"+(itemNameList.size()+calItemNameList.size()+inputItemNameList.size())+","
					+ "\"curveItems\":"+itemsBuff+","
					+ "\"curveItemCodes\":"+itemsCodeBuff+","
					+ "\"curveConf\":"+curveConfBuff+","
					+ "\"graphicSet\":"+graphicSet+","
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
				sql+= " where t.acqtime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss')"
						+ " and t2.id="+deviceId;
				int total=this.getTotalCountRows(sql);
				int rarefy=total/vacuateThreshold+1;
				sql+= " order by t.acqtime";
				
				String finalSql=sql;
				if(rarefy>1){
					finalSql="select * from  (select v.*, rownum as rn from ("+sql+") v ) v2 where mod(rn*"+vacuateThreshold+","+total+")<"+vacuateThreshold+"";
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
						if(rarefy>1){
							productionData=(obj[obj.length-2]+"").replaceAll("null", "");
						}
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
					if(i==0){
						minAcqTime=obj[0]+"";
					}
					if(i==list.size()-1){
						maxAcqTime=obj[0]+"";
					}
				}
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("],\"minAcqTime\":\""+minAcqTime+"\",\"maxAcqTime\":\""+maxAcqTime+"\"}");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result_json.toString();
	}
	
	public String getHistoryQueryCurveSetData(String deviceId,String deviceType,int userNo)throws Exception {
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		int dataSaveMode=1;
		List<DisplayItem> itemList=null;
		String deviceTableName="tbl_device";
		String graphicSetTableName="tbl_devicegraphicset";
		
		String protocolSql="select upper(t3.protocol) from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 "
				+ " where t.instancecode=t2.code and t2.unitid=t3.id"
				+ " and  t.id="+deviceId;
		String graphicSetSql="select t.graphicstyle from "+graphicSetTableName+" t where t.deviceId="+deviceId;
		String curveItemsSql="select t4.itemname,t4.bitindex,t4.historycurveconf,t4.itemcode,t4.type "
				+ " from "+deviceTableName+" t,tbl_protocoldisplayinstance t2,tbl_display_unit_conf t3,tbl_display_items2unit_conf t4 "
				+ " where t.displayinstancecode=t2.code and t2.displayunitid=t3.id and t3.id=t4.unitid and t4.type<>2 "
				+ " and t.id="+deviceId+" "
				+ " and t4.historycurveconf is not null "
				+ " and (t4.showlevel is null or t4.showlevel>=(select r.showlevel from tbl_user u,tbl_role r where u.user_type=r.role_level and u.user_no="+userNo+"))"
				+ " order by t4.realtimeSort,t4.id";
		List<?> protocolList = this.findCallSql(protocolSql);
		List<?> graphicSetList = this.findCallSql(graphicSetSql);
		List<?> curveItemList = this.findCallSql(curveItemsSql);
		String protocolName="";
		String unit="";
		String dataType="";
		GraphicSetData graphicSetData=null;
		int resolutionMode=0;
		
		if(graphicSetList.size()>0){
			String graphicSet=graphicSetList.get(0).toString().replaceAll(" ", "").replaceAll("\r\n", "").replaceAll("\n", "");
			type = new TypeToken<GraphicSetData>() {}.getType();
			graphicSetData=gson.fromJson(graphicSet, type);
		}
		
		itemList=new ArrayList<DisplayItem>();
		for(int i=0;i<curveItemList.size();i++){
			Object[] itemObj=(Object[]) curveItemList.get(i);
			DisplayItem displayItem=new DisplayItem();
			displayItem.setItemName(itemObj[0]+"");
			displayItem.setItemCode(itemObj[3]+"");
			displayItem.setType(StringManagerUtils.stringToInteger(itemObj[4]+""));
			displayItem.setHistoryCurveConf(itemObj[2]+"");
			itemList.add(displayItem);
		}
		if(itemList.size()>0){
			Collections.sort(itemList,new Comparator<DisplayInstanceOwnItem.DisplayItem>(){
				@Override
				public int compare(DisplayInstanceOwnItem.DisplayItem item1,DisplayInstanceOwnItem.DisplayItem item2){
					Gson gson = new Gson();
					java.lang.reflect.Type type=null;
					int sort1=0;
					int sort2=0;
					boolean yAxisOpposite1=false,yAxisOpposite2=false;
					int diff=0;
					type = new TypeToken<CurveConf>() {}.getType();
					CurveConf historyCurveConfObj1=gson.fromJson(item1.getHistoryCurveConf(), type);
					
					type = new TypeToken<CurveConf>() {}.getType();
					CurveConf historyCurveConfObj2=gson.fromJson(item2.getHistoryCurveConf(), type);
					
					if(historyCurveConfObj1!=null){
						sort1=historyCurveConfObj1.getSort();
						yAxisOpposite1=historyCurveConfObj1.getYAxisOpposite();
					}
					
					if(historyCurveConfObj2!=null){
						sort2=historyCurveConfObj2.getSort();
						yAxisOpposite2=historyCurveConfObj2.getYAxisOpposite();
					}
					
					if(yAxisOpposite1==yAxisOpposite2){
//						if(yAxisOpposite1){//如果都在右侧
//							diff=sort1-sort2;//按序号升序
//						}else{//如果都在左侧
//							diff=sort2-sort1;//按序号降序
//						}
						diff=sort1-sort2;//按序号升序
					}else{
						if(yAxisOpposite1){
							diff=1;
						}else{
							diff=-1;
						}
					}
					
					
//					diff=sort1-sort2;
					if(diff>0){
						return 1;
					}else if(diff<0){
						return -1;
					}
					return 0;
				}
			});
		}
		
		
		
		result_json.append("{\"success\":true,\"totalCount\":"+curveItemList.size()+",\"totalRoot\":[");
		
		for(int i=0;i<itemList.size();i++){
			
			String curveName=itemList.get(i).getItemName();
			String itemCode=itemList.get(i).getItemCode();
			String itemType=itemList.get(i).getType()+"";
			
			String yAxisMaxValue="";
			String yAxisMinValue="";
			result_json.append("{\"curveName\":\"" + curveName + "\",\"itemCode\":\"" + itemCode + "\",\"itemType\":\""+itemType+"\",");
			if(graphicSetData!=null && graphicSetData.getHistory()!=null && graphicSetData.getHistory().size()>0){
				for(int j=0;j<graphicSetData.getHistory().size();j++){
					if(itemCode.equalsIgnoreCase(graphicSetData.getHistory().get(j).getItemCode())){
						yAxisMaxValue=graphicSetData.getHistory().get(j).getyAxisMaxValue();
						yAxisMinValue=graphicSetData.getHistory().get(j).getyAxisMinValue();
						break;
					}
				}
			}
			
			result_json.append("\"yAxisMaxValue\":\""+yAxisMaxValue+"\",");
			result_json.append("\"yAxisMinValue\":\""+yAxisMinValue+"\"},");
		}
		
		
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		
		result_json.append("]}");
		return result_json.toString();
	}
	
	public int setHistoryDataGraphicInfo(String deviceId,String deviceType,String graphicSetSaveDataStr)throws Exception {
		int result=0;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		
		if(StringManagerUtils.stringToInteger(deviceId)>0){
			String deviceTableName="tbl_device";
			String graphicSetTableName="tbl_devicegraphicset";
			
			type = new TypeToken<GraphicSetData>() {}.getType();
			GraphicSetData graphicSetSaveData=gson.fromJson(graphicSetSaveDataStr, type);
			String graphicSetSql="select t.graphicstyle from "+graphicSetTableName+" t where t.deviceId="+deviceId;
			List<?> graphicSetList = this.findCallSql(graphicSetSql);
			GraphicSetData graphicSetData=null;
			if(graphicSetList.size()>0){
				String graphicSet=graphicSetList.get(0).toString().replaceAll(" ", "").replaceAll("\r\n", "").replaceAll("\n", "");
				type = new TypeToken<GraphicSetData>() {}.getType();
				graphicSetData=gson.fromJson(graphicSet, type);
			}
			String saveStr=graphicSetSaveDataStr;
			if(graphicSetData!=null){
				if(graphicSetData.getHistory()!=null&&graphicSetData.getHistory().size()>0){
					for(int i=0;i<graphicSetSaveData.getHistory().size();i++){
						boolean isExit=false;
						for(int j=0;j<graphicSetData.getHistory().size();j++){
							if(graphicSetSaveData.getHistory().get(i).getItemCode().equalsIgnoreCase(graphicSetData.getHistory().get(j).getItemCode())){
								isExit=true;
								graphicSetData.getHistory().get(j).setyAxisMaxValue(graphicSetSaveData.getHistory().get(i).getyAxisMaxValue());
								graphicSetData.getHistory().get(j).setyAxisMinValue(graphicSetSaveData.getHistory().get(i).getyAxisMinValue());
								break;
							}
						}
						if(!isExit){
							graphicSetData.getHistory().add(graphicSetSaveData.getHistory().get(i));
						}
					}
				}else{
					graphicSetData.setHistory(graphicSetSaveData.getHistory());
				}
				saveStr=gson.toJson(graphicSetData);
			}
			String sql="select t.deviceId from "+graphicSetTableName+" t where t.deviceId="+deviceId;
			String updateSql="";
			List<?> list = this.findCallSql(sql);
			if(list.size()>0){
				updateSql="update "+graphicSetTableName+" t set t.graphicstyle='"+saveStr+"' where t.deviceId="+deviceId;
			}else{
				updateSql="insert into "+graphicSetTableName+" (deviceId,graphicstyle) values("+deviceId+",'"+saveStr+"')";
			}
			result=this.getBaseDao().updateOrDeleteBySql(updateSql);
		}
		return result;
	}
	
	public String querySurfaceCard(String orgId,String deviceId,String deviceName,String deviceType,Page pager,String language) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int vacuateThreshold=configFile.getAp().getOthers().getVacuateThreshold();
		int intPage = pager.getPage();
		int limit = pager.getLimit();
		int start = pager.getStart();
		int maxvalue = limit + start;
		String allsql="",sql="",totalSql="";
		allsql="select t.id,well.devicename,to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ " t.stroke,t.spm,"
				+ " t.fmax,t.fmin,t.position_curve,t.load_curve,"
				+ " t.resultcode,"
				+ " t.upperloadline,t.lowerloadline,t.liquidvolumetricproduction "
				+ " from tbl_rpcacqdata_hist t"
				+ " left outer join tbl_device well on well.id=t.deviceId"
				+ " where  1=1 "
				+ " and t.fesdiagramacqtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') "
				+ " and t.deviceId="+deviceId+" ";
		totalSql="select count(1) from tbl_rpcacqdata_hist t"
				+ " left outer join tbl_device well on well.id=t.deviceId"
				+ " where  1=1 "
				+ " and t.fesdiagramacqtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') "
				+ " and t.deviceId="+deviceId+" ";
		
		int totals = getTotalCountRows(totalSql);//获取总记录数
		
		int rarefy=totals/vacuateThreshold+1;
		if(rarefy>1){
			totalSql="select count(1) from  (select v.*, rownum as rn from ("+allsql+") v ) v2 where mod(rn*"+vacuateThreshold+","+totals+")<"+vacuateThreshold+"";
			totals = getTotalCountRows(totalSql);
			
		}
		allsql+= " order by t.fesdiagramacqtime desc";
		if(rarefy>1){
			allsql="select v2.* from  (select v.*, rownum as rn from ("+allsql+") v ) v2 where mod(rn*"+vacuateThreshold+","+totals+")<"+vacuateThreshold+"";
		}
		sql="select b.* from (select a.*,rownum as rn2 from  ("+ allsql +") a where rownum <= "+ maxvalue +") b where rn2 > "+ start +"";
		
		List<?> list=this.findCallSql(sql);
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
	        String resultCode=obj[9]+"";
	        
	        WorkType workType=MemoryDataManagerTask.getWorkTypeByCode(resultCode,language);
	        
	        
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
			dynSbf.append("\"deviceName\":\"" + obj[1] + "\",");
			dynSbf.append("\"acqTime\":\"" + obj[2] + "\",");
			dynSbf.append("\"stroke\":\""+obj[3]+"\",");
			dynSbf.append("\"spm\":\""+obj[4]+"\",");
			dynSbf.append("\"fmax\":\""+obj[5]+"\",");
			dynSbf.append("\"fmin\":\""+obj[6]+"\",");
			
			dynSbf.append("\"resultName\":\""+workType.getResultName()+"\",");
			dynSbf.append("\"optimizationSuggestion\":\""+workType.getOptimizationSuggestion()+"\",");
			
			
			dynSbf.append("\"upperLoadLine\":\"" + obj[10] + "\",");
			dynSbf.append("\"lowerLoadLine\":\"" + obj[11] + "\",");
			dynSbf.append("\"liquidProduction\":\""+obj[12]+"\",");
			
			dynSbf.append("\"pointCount\":\""+pointCount+"\","); 
			dynSbf.append("\"positionCurveData\":\""+DiagramXData+"\",");
			dynSbf.append("\"loadCurveData\":\""+DiagramYData+"\"},");
		}
		if(dynSbf.toString().endsWith(",")){
			dynSbf.deleteCharAt(dynSbf.length() - 1);
		}
		dynSbf.append("]}");
		return dynSbf.toString().replaceAll("null", "");
	}
	
	public boolean exportHistoryQueryFESDiagramDataExcel(User user,HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceId,String deviceName,Page pager){
		try{
			StringBuffer result_json = new StringBuffer();
			ConfigFile configFile=Config.getInstance().configFile;
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			int vacuateThreshold=configFile.getAp().getOthers().getVacuateThreshold();
			boolean vacuate=true;
			fileName += "-" + pager.getStart_date()+"~"+pager.getEnd_date();
			String[] heads={"井号","日期时间","冲程(m)","冲次(次/分钟)","最小载荷(kN)","最大载荷(kN)","曲线点数","位移","载荷"};
			String[] columns={"deviceName","acqTime","stroke","spm","fmin","fmax","pointCount","positionCurveData","loadCurveData"};
			
			List<Object> headRow = new ArrayList<>();
			for(int i=0;i<heads.length;i++){
				headRow.add(heads[i]);
			}
		    List<List<Object>> sheetDataList = new ArrayList<>();
		    sheetDataList.add(headRow);
			
			String sql="select t.id,well.devicename,to_char(t.fesdiagramacqtime,'yyyy/mm/dd hh24:mi:ss') as acqTime,"
					+ " t.stroke,t.spm,"
					+ " t.fmax,t.fmin,"
					+ " t.position_curve,t.load_curve"
					+ " from tbl_rpcacqdata_hist t"
					+ " left outer join tbl_device well on well.id=t.deviceId"
					+ " where  1=1 "
					+ " and t.fesdiagramacqtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') "
					+ " and t.deviceId="+deviceId+" "
					+ " order by t.fesdiagramacqtime";
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			int totals = getTotalCountRows(sql);//获取总记录数
			int rarefy=0;
			if(vacuate && vacuateThreshold>0){
				rarefy=totals/vacuateThreshold+1;
				if(rarefy>1){
					finalSql="select v2.* from  (select v.*, rownum as rn from ("+finalSql+") v ) v2 where mod(rn*"+vacuateThreshold+","+totals+")<"+vacuateThreshold+"";
				}
			}
			List<?> list=this.findCallSql(finalSql);
			List<Object> record=null;
			JSONObject jsonObject=null;
			Object[] obj=null;
			for(int i=0;i<list.size();i++){
				obj=(Object[]) list.get(i);
				result_json = new StringBuffer();
				record = new ArrayList<>();
				
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
				
		        result_json.append("{ \"id\":\"" + obj[0] + "\",");
		        result_json.append("\"deviceName\":\"" + obj[1] + "\",");
		        result_json.append("\"acqTime\":\"" + obj[2] + "\",");
		        result_json.append("\"stroke\":\""+obj[3]+"\",");
		        result_json.append("\"spm\":\""+obj[4]+"\",");
		        result_json.append("\"fmax\":\""+obj[5]+"\",");
		        result_json.append("\"fmin\":\""+obj[6]+"\",");
		        result_json.append("\"pointCount\":\""+pointCount+"\","); 
		        result_json.append("\"positionCurveData\":\""+DiagramXData+"\",");
		        result_json.append("\"loadCurveData\":\""+DiagramYData+"\"}");
				
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
			ExcelUtils.export(response,fileName,title, sheetDataList,1);
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
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public String getFESDiagramOverlayData(String orgId,String deviceId,String deviceName,String deviceType,Page pager,String language){
		StringBuffer dynSbf = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		DataDictionary ddic = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		
		String prodCol="liquidVolumetricProduction,liquidVolumetricProduction_L";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol="liquidWeightProduction,liquidWeightProduction_L";
		}
		int vacuateThreshold=configFile.getAp().getOthers().getVacuateThreshold();
		AlarmShowStyle alarmShowStyle=null;
		AlarmInstanceOwnItem alarmInstanceOwnItem=null;
		DeviceInfo deviceInfo=null;
		String alarmInstanceCode="";
		try{
			try{
				alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
				deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId);
				if(deviceInfo!=null){
					alarmInstanceCode=deviceInfo.getAlarmInstanceCode();
					alarmInstanceOwnItem=MemoryDataManagerTask.getAlarmInstanceOwnItemByCode(alarmInstanceCode);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			Map<String,WorkType> workTypeMap=MemoryDataManagerTask.getWorkTypeMap(language);
			
			int timeEfficiencyUnitType=Config.getInstance().configFile.getAp().getOthers().getTimeEfficiencyUnit();
			String timeEfficiencyUnit=languageResourceMap.get("decimals");
			int timeEfficiencyZoom=1;
			if(timeEfficiencyUnitType==2){
				timeEfficiencyUnit="%";
				timeEfficiencyZoom=100;
			}
			
			String sql="select t.id,well.devicename,to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"//0~2
					+ "t.commstatus,decode(t.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,"//3~4
					+ "t.commtime,t.commtimeefficiency*"+timeEfficiencyZoom+",t.commrange,"//5~7
					+ "t.runstatus,decode(t.commstatus,0,'"+languageResourceMap.get("offline")+"',decode(t.runstatus,1,'"+languageResourceMap.get("run")+"','"+languageResourceMap.get("stop")+"')) as runStatusName,"//8~9
					+ "t.runtime,t.runtimeefficiency*"+timeEfficiencyZoom+",t.runrange,"//10~12
					+ " t.resultcode,"//13
					+ " t.stroke,t.spm,"//14~15
					+ " t.fmax,t.fmin,"//16~17
					+ " t.fullnessCoefficient,t.plungerStroke,t.availablePlungerStroke,"//18~20
					+ " t.upperloadline,t.lowerloadline,"//21~22
					+ prodCol+", "//23~24
					+ " t.productiondata,t.submergence,"//25~26
					+ " t.levelDifferenceValue,t.calcProducingfluidLevel,"//27~28
					+ " t.averageWatt,t.polishrodPower,t.waterPower,"//29~31
					+ " t.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"//32
					+ " t.welldownSystemEfficiency*100 as welldownSystemEfficiency,"//33
					+ " t.systemEfficiency*100 as systemEfficiency,t.energyper100mlift,"//34~35
					+ " t.pumpEff*100 as pumpEff,"//36
					+ " t.UpStrokeIMax,t.DownStrokeIMax,t.iDegreeBalance,"//37~39
					+ " t.UpStrokeWattMax,t.DownStrokeWattMax,t.wattDegreeBalance,"//40~42
					+ " t.deltaradius*100 as deltaradius,"//43
					+ " t.todayKWattH,"//44
					+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve"//45~48
					+ " from tbl_rpcacqdata_hist t"
					+ " left outer join tbl_device well on well.id=t.deviceId"
					+ " where  1=1 "
					+ " and t.fesdiagramacqtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') "
					+ " and t.deviceId="+deviceId+" "
					+ " order by t.fesdiagramacqtime desc";
			
			String countSql="select count(1) from tbl_rpcacqdata_hist t"
					+ " left outer join tbl_device well on well.id=t.deviceId"
					+ " where  1=1 "
					+ " and t.fesdiagramacqtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') "
					+ " and t.deviceId="+deviceId;
			int total=this.getTotalCountRows(countSql);
			int rarefy=total/vacuateThreshold+1;
			String finalSql=sql;
			if(rarefy>1){
				finalSql="select v2.* from  (select v.*, rownum as rn from ("+sql+") v ) v2 where mod(rn*"+vacuateThreshold+","+total+")<"+vacuateThreshold+"";
			}
			List<?> list=this.findCallSql(finalSql);
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId("historyQuery_FESDiagramOverlay");
			String columns = ddic.getTableHeader();
			if(timeEfficiencyUnitType==2){
				columns=columns.replace("在线时率(小数)", "在线时率(%)").replace("运行时率(小数)", "运行时率(%)");
			}else{
				columns=columns.replace("在线时率(%)", "在线时率(小数)").replace("运行时率(%)", "运行时率(小数)");
			}
			String[] ddicColumns=ddic.getSql().split(",");
			dynSbf.append("{\"success\":true,"
					+ "\"totalCount\":" + list.size() + ","
					+ "\"deviceName\":\""+deviceName+"\","
					+ "\"start_date\":\""+pager.getStart_date()+"\","
					+ "\"end_date\":\""+pager.getEnd_date()+"\","
					+ "\"columns\":"+columns+",");
			boolean outOfMemory=false;
			StringBuffer dataBuff = new StringBuffer();
			try{
				dataBuff.append("[");
				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						Object[] obj = (Object[]) list.get(i);
						StringBuffer alarmInfo = new StringBuffer();
						String positionCurveData="",loadCurveData="",powerCurveData="",currentCurveData="";
						String commStatusName=(obj[4]+"").replaceAll("null", "");
						String runStatusName=(obj[9]+"").replaceAll("null", "");
						String resultCode=(obj[13]+"").replaceAll("null", "");
						int commAlarmLevel=0,resultAlarmLevel=0,runAlarmLevel=0;
						String productionDataStr=(obj[23]+"").replaceAll("null", "");
						
						WorkType workType=workTypeMap.get(resultCode);
						
						type = new TypeToken<RPCCalculateRequestData>() {}.getType();
						RPCCalculateRequestData productionData=gson.fromJson(productionDataStr, type);
						
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
						
						positionCurveData=StringManagerUtils.CLOBObjectToString(obj[45]);
						loadCurveData=StringManagerUtils.CLOBObjectToString(obj[46]);
						powerCurveData=StringManagerUtils.CLOBObjectToString(obj[47]);
						currentCurveData=StringManagerUtils.CLOBObjectToString(obj[48]);
						
						dataBuff.append("{ \"id\":\"" + obj[0] + "\",");
						dataBuff.append("\"deviceName\":\"" + obj[1] + "\",");
						dataBuff.append("\"acqTime\":\"" + obj[2] + "\",");
						dataBuff.append("\"commStatus\":"+obj[3]+",");
						dataBuff.append("\"commStatusName\":\""+commStatusName+"\",");
						dataBuff.append("\"commTime\":\""+obj[5]+"\",");
						dataBuff.append("\"commTimeEfficiency\":\""+obj[6]+"\",");
						dataBuff.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[7])+"\",");
						dataBuff.append("\"commAlarmLevel\":"+commAlarmLevel+",");
						dataBuff.append("\"runStatus\":"+obj[8]+",");
						dataBuff.append("\"runStatusName\":\""+runStatusName+"\",");
						dataBuff.append("\"runTime\":\""+obj[10]+"\",");
						dataBuff.append("\"runTimeEfficiency\":\""+obj[11]+"\",");
						dataBuff.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
						dataBuff.append("\"runAlarmLevel\":"+runAlarmLevel+",");
						
						dataBuff.append("\"resultCode\":\""+resultCode+"\",");
						dataBuff.append("\"resultName\":\""+workType.getResultName()+"\",");
						dataBuff.append("\"optimizationSuggestion\":\""+workType.getOptimizationSuggestion()+"\",");
						dataBuff.append("\"resultAlarmLevel\":"+resultAlarmLevel+",");
						
						dataBuff.append("\"stroke\":\""+obj[14]+"\",");
						dataBuff.append("\"spm\":\""+obj[15]+"\",");
						dataBuff.append("\"fmax\":\""+obj[16]+"\",");
						dataBuff.append("\"fmin\":\""+obj[17]+"\",");
						
						dataBuff.append("\"fullnessCoefficient\":\""+obj[18]+"\",");
						dataBuff.append("\"plungerStroke\":\""+obj[19]+"\",");
						dataBuff.append("\"availablePlungerStroke\":\""+obj[20]+"\",");
						
						dataBuff.append("\"upperLoadLine\":\""+obj[21]+"\",");
						dataBuff.append("\"lowerLoadLine\":\""+obj[22]+"\",");
						dataBuff.append("\""+prodCol.split(",")[0]+"\":\""+obj[23]+"\",");
						dataBuff.append("\""+prodCol.split(",")[1]+"\":\""+obj[24]+"\",");
						
						
						dataBuff.append("\"pumpSettingDepth\":\""+(productionData!=null&&productionData.getProduction()!=null?productionData.getProduction().getPumpSettingDepth():"")+"\",");
						dataBuff.append("\"producingfluidLevel\":\""+(productionData!=null&&productionData.getProduction()!=null?productionData.getProduction().getProducingfluidLevel():"")+"\",");
						dataBuff.append("\"levelCorrectValue\":\""+(productionData!=null&&productionData.getManualIntervention()!=null?productionData.getManualIntervention().getLevelCorrectValue():"")+"\",");
						dataBuff.append("\"calcProducingfluidLevel\":\""+obj[28]+"\",");
						dataBuff.append("\"levelDifferenceValue\":\""+obj[27]+"\",");
						dataBuff.append("\"submergence\":\""+obj[26]+"\",");
						
						dataBuff.append("\"averageWatt\":\""+obj[29]+"\",");
						dataBuff.append("\"polishrodPower\":\""+obj[30]+"\",");
						dataBuff.append("\"waterPower\":\""+obj[31]+"\",");
						
						dataBuff.append("\"surfaceSystemEfficiency\":\""+obj[32]+"\",");
						dataBuff.append("\"welldownSystemEfficiency\":\""+obj[33]+"\",");
						dataBuff.append("\"systemEfficiency\":\""+obj[34]+"\",");
						
						dataBuff.append("\"energyper100mlift\":\""+obj[35]+"\",");
						dataBuff.append("\"pumpEff\":\""+obj[36]+"\",");
						
						dataBuff.append("\"upStrokeIMax\":\""+obj[37]+"\",");
						dataBuff.append("\"downStrokeIMax\":\""+obj[38]+"\",");
						dataBuff.append("\"iDegreeBalance\":\""+obj[39]+"\",");
						
						dataBuff.append("\"upStrokeWattMax\":\""+obj[40]+"\",");
						dataBuff.append("\"downStrokeWattMax\":\""+obj[41]+"\",");
						dataBuff.append("\"wattDegreeBalance\":\""+obj[42]+"\",");
						
						dataBuff.append("\"deltaradius\":\""+obj[43]+"\",");
						dataBuff.append("\"todayKWattH\":\""+obj[44]+"\",");
						
						dataBuff.append("\"positionCurveData\":\"" + positionCurveData + "\",");
						dataBuff.append("\"loadCurveData\":\"" + loadCurveData + "\",");
						dataBuff.append("\"powerCurveData\":\"" + powerCurveData + "\",");
						dataBuff.append("\"currentCurveData\":\"" + currentCurveData + "\",");
						
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
						dataBuff.append("\"alarmInfo\":"+alarmInfo+"");
						dataBuff.append("},");
					}
					
					if(dataBuff.toString().endsWith(",")){
						dataBuff.deleteCharAt(dataBuff.length() - 1);
					}
				}
				dataBuff.append("]");
			}catch(OutOfMemoryError|StackOverflowError e){
				e.printStackTrace();
				outOfMemory=true;
				dataBuff = new StringBuffer();
				dataBuff.append("[]");
			}
			dynSbf.append("\"totalRoot\":"+dataBuff+",");
			dynSbf.append("\"outOfMemory\":"+outOfMemory+",");
			dynSbf.append("\"AlarmShowStyle\":"+(alarmShowStyle==null?"\"\"":new Gson().toJson(alarmShowStyle)));
			dynSbf.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return dynSbf.toString().replaceAll("null", "");
	}
	
	public boolean exportFESDiagramOverlayData(User user,HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceId,String deviceName,Page pager,String language){
		try{
			StringBuffer dataBuff = new StringBuffer();
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			ConfigFile configFile=Config.getInstance().configFile;
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			int vacuateThreshold=configFile.getAp().getOthers().getVacuateThreshold();
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			Map<String,WorkType> workTypeMap=MemoryDataManagerTask.getWorkTypeMap(language);
			
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
			
			String sql="select t.id,well.devicename,to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"//0~2
					+ "t.commstatus,decode(t.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,"//3~4
					+ "t.commtime,t.commtimeefficiency*"+timeEfficiencyZoom+",t.commrange,"//5~7
					+ "t.runstatus,decode(t.commstatus,0,'"+languageResourceMap.get("offline")+"',decode(t.runstatus,1,'"+languageResourceMap.get("run")+"','"+languageResourceMap.get("stop")+"')) as runStatusName,"//8~9
					+ "t.runtime,t.runtimeefficiency*"+timeEfficiencyZoom+",t.runrange,"//10~12
					+ " t.resultcode,"//13
					+ " t.stroke,t.spm,"//14~15
					+ " t.fmax,t.fmin,"//16~17
					+ " t.fullnessCoefficient,t.plungerStroke,t.availablePlungerStroke,"//18~20
					+ " t.upperloadline,t.lowerloadline,"//21~22
					+ prodCol+", "//23~24
					+ " t.productiondata,t.submergence,"//25~26
					+ " t.levelDifferenceValue,t.calcProducingfluidLevel,"//27~28
					+ " t.averageWatt,t.polishrodPower,t.waterPower,"//29~31
					+ " t.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"//32
					+ " t.welldownSystemEfficiency*100 as welldownSystemEfficiency,"//33
					+ " t.systemEfficiency*100 as systemEfficiency,t.energyper100mlift,"//34~35
					+ " t.pumpEff*100 as pumpEff,"//36
					+ " t.UpStrokeIMax,t.DownStrokeIMax,t.iDegreeBalance,"//37~39
					+ " t.UpStrokeWattMax,t.DownStrokeWattMax,t.wattDegreeBalance,"//40~42
					+ " t.deltaradius*100 as deltaradius,"//43
					+ " t.todayKWattH"//44
					+ " from tbl_rpcacqdata_hist t"
					+ " left outer join tbl_device well on well.id=t.deviceId"
					+ " where  1=1 "
					+ " and t.fesdiagramacqtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss') "
					+ " and t.deviceId="+deviceId+" ";
			int total=this.getTotalCountRows(sql);
			int rarefy=total/vacuateThreshold+1;
			sql+= " order by t.fesdiagramacqtime desc";
			
			String finalSql=sql;
			if(rarefy>1){
				sql="select v2.* from  (select v.*, rownum as rn from ("+sql+") v ) v2 where mod(rn*"+vacuateThreshold+","+total+")<"+vacuateThreshold+"";
				finalSql="select a.*,rownum as rn2 from  ("+ sql +") a where rownum <= "+ maxvalue;
			}else{
				finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			}
			
			List<?> list=this.findCallSql(finalSql);
			List<Object> record=null;
			JSONObject jsonObject=null;
			Object[] obj=null;
			for(int i=0;i<list.size();i++){
				obj = (Object[]) list.get(i);
				StringBuffer alarmInfo = new StringBuffer();
				String positionCurveData="",loadCurveData="",powerCurveData="",currentCurveData="";
				String commStatusName=(obj[4]+"").replaceAll("null", "");
				String runStatusName=(obj[9]+"").replaceAll("null", "");
				String resultCode=(obj[13]+"").replaceAll("null", "");
				int commAlarmLevel=0,resultAlarmLevel=0,runAlarmLevel=0;
				String productionDataStr=(obj[23]+"").replaceAll("null", "");
				
				WorkType workType=workTypeMap.get(resultCode);
				
				type = new TypeToken<RPCCalculateRequestData>() {}.getType();
				RPCCalculateRequestData productionData=gson.fromJson(productionDataStr, type);
				
				dataBuff.append("{ \"id\":\"" + (1+i) + "\",");
				dataBuff.append("\"deviceName\":\"" + obj[1] + "\",");
				dataBuff.append("\"acqTime\":\"" + obj[2] + "\",");
				dataBuff.append("\"commStatus\":"+obj[3]+",");
				dataBuff.append("\"commStatusName\":\""+commStatusName+"\",");
				dataBuff.append("\"commTime\":\""+obj[5]+"\",");
				dataBuff.append("\"commTimeEfficiency\":\""+obj[6]+"\",");
				dataBuff.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[7])+"\",");
				dataBuff.append("\"commAlarmLevel\":"+commAlarmLevel+",");
				dataBuff.append("\"runStatus\":"+obj[8]+",");
				dataBuff.append("\"runStatusName\":\""+runStatusName+"\",");
				dataBuff.append("\"runTime\":\""+obj[10]+"\",");
				dataBuff.append("\"runTimeEfficiency\":\""+obj[11]+"\",");
				dataBuff.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
				dataBuff.append("\"runAlarmLevel\":"+runAlarmLevel+",");
				
				dataBuff.append("\"resultCode\":\""+resultCode+"\",");
				dataBuff.append("\"resultName\":\""+workType.getResultName()+"\",");
				dataBuff.append("\"optimizationSuggestion\":\""+workType.getOptimizationSuggestion()+"\",");
				dataBuff.append("\"resultAlarmLevel\":"+resultAlarmLevel+",");
				
				dataBuff.append("\"stroke\":\""+obj[14]+"\",");
				dataBuff.append("\"spm\":\""+obj[15]+"\",");
				dataBuff.append("\"fmax\":\""+obj[16]+"\",");
				dataBuff.append("\"fmin\":\""+obj[17]+"\",");
				
				dataBuff.append("\"fullnessCoefficient\":\""+obj[18]+"\",");
				dataBuff.append("\"plungerStroke\":\""+obj[19]+"\",");
				dataBuff.append("\"availablePlungerStroke\":\""+obj[20]+"\",");
				
				dataBuff.append("\"upperLoadLine\":\""+obj[21]+"\",");
				dataBuff.append("\"lowerLoadLine\":\""+obj[22]+"\",");
				dataBuff.append("\""+prodCol.split(",")[0]+"\":\""+obj[23]+"\",");
				dataBuff.append("\""+prodCol.split(",")[1]+"\":\""+obj[24]+"\",");
				
				
				dataBuff.append("\"pumpSettingDepth\":\""+(productionData!=null&&productionData.getProduction()!=null?productionData.getProduction().getPumpSettingDepth():"")+"\",");
				dataBuff.append("\"producingfluidLevel\":\""+(productionData!=null&&productionData.getProduction()!=null?productionData.getProduction().getProducingfluidLevel():"")+"\",");
				dataBuff.append("\"levelCorrectValue\":\""+(productionData!=null&&productionData.getManualIntervention()!=null?productionData.getManualIntervention().getLevelCorrectValue():"")+"\",");
				dataBuff.append("\"calcProducingfluidLevel\":\""+obj[28]+"\",");
				dataBuff.append("\"levelDifferenceValue\":\""+obj[27]+"\",");
				dataBuff.append("\"submergence\":\""+obj[26]+"\",");
				
				dataBuff.append("\"averageWatt\":\""+obj[29]+"\",");
				dataBuff.append("\"polishrodPower\":\""+obj[30]+"\",");
				dataBuff.append("\"waterPower\":\""+obj[31]+"\",");
				
				dataBuff.append("\"surfaceSystemEfficiency\":\""+obj[32]+"\",");
				dataBuff.append("\"welldownSystemEfficiency\":\""+obj[33]+"\",");
				dataBuff.append("\"systemEfficiency\":\""+obj[34]+"\",");
				
				dataBuff.append("\"energyper100mlift\":\""+obj[35]+"\",");
				dataBuff.append("\"pumpEff\":\""+obj[36]+"\",");
				
				dataBuff.append("\"upStrokeIMax\":\""+obj[37]+"\",");
				dataBuff.append("\"downStrokeIMax\":\""+obj[38]+"\",");
				dataBuff.append("\"iDegreeBalance\":\""+obj[39]+"\",");
				
				dataBuff.append("\"upStrokeWattMax\":\""+obj[40]+"\",");
				dataBuff.append("\"downStrokeWattMax\":\""+obj[41]+"\",");
				dataBuff.append("\"wattDegreeBalance\":\""+obj[42]+"\",");
				
				dataBuff.append("\"deltaradius\":\""+obj[43]+"\",");
				dataBuff.append("\"todayKWattH\":\""+obj[44]+"\"}");
				
				jsonObject = JSONObject.fromObject(dataBuff.toString().replaceAll("null", ""));
				for (int j = 0; j < columns.length; j++) {
					if(jsonObject.has(columns[j])){
						record.add(jsonObject.getString(columns[j]));
					}else{
						record.add("");
					}
				}
				sheetDataList.add(record);
			}
			ExcelUtils.export(response,fileName,title, sheetDataList,1);
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
		}
		return true;
	}
}
