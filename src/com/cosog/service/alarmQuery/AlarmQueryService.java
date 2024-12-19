package com.cosog.service.alarmQuery;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AlarmShowStyle;
import com.cosog.model.User;
import com.cosog.model.data.DataDictionary;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.excel.ExcelUtils;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

@Service("alarmQueryService")
public class AlarmQueryService<T> extends BaseService<T>  {

	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	
	public String getAlarmData(String orgId,String deviceType,String deviceId,String deviceName,String alarmType,String alarmLevel,String isSendMessage,Page pager,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String ddicCode="alarmQuery_CommStatusAlarm";
		if(StringManagerUtils.stringToInteger(alarmType)==0){
			ddicCode="alarmQuery_SwitchingValueAlarm";
		}else if(StringManagerUtils.stringToInteger(alarmType)==1){
			ddicCode="alarmQuery_EnumValueAlarm";
		}else if(StringManagerUtils.stringToInteger(alarmType)==2){
			ddicCode="alarmQuery_NumericValueAlarm";
		}else if(StringManagerUtils.stringToInteger(alarmType)==3){
			ddicCode="alarmQuery_CommStatusAlarm";
		}
		
		String tableName="viw_alarminfo_hist";
		
		DataDictionary ddic = null;
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicCode);
		String columns = ddic.getTableHeader();
		String sql="select t.id,t.deviceid,t.devicename,t.devicetype,t.deviceTypeName_"+language+",to_char(t.alarmtime,'yyyy-mm-dd hh24:mi:ss') as alarmtime,"
				+ " t.itemname,t.alarmtype,"
				+ " t.alarmvalue,t.alarminfo,t.alarmlimit,t.hystersis,"
				+ " t.alarmlevel,"
				+ " t.issendmessage,t.issendmail,"
				+ " t.recoverytime,t.orgid "
				+ " from "+tableName+" t where t.orgid in ("+orgId+") "
				+ " and t.alarmtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
		if(StringManagerUtils.isNotNull(deviceId)){
			sql+=" and t.deviceid="+deviceId;
		}
		if(StringManagerUtils.isNotNull(alarmType)){
			if(StringManagerUtils.stringToInteger(alarmType)==2){
				sql+=" and t.alarmType=2 or t.alarmType=5";
			}else {
				sql+=" and t.alarmType="+alarmType;
			}
		}
		if(StringManagerUtils.isNotNull(alarmLevel)){
			sql+=" and t.alarmLevel="+alarmLevel;
		}
		if(StringManagerUtils.isNotNull(isSendMessage)){
			sql+=" and t.isSendMessage="+isSendMessage;
		}
		sql+=" order by t.alarmtime desc";
		int maxvalue=pager.getLimit()+pager.getStart();
		String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		
		List<?> list=this.findCallSql(finalSql);
		int totals=this.getTotalCountRows(sql);
		
		result_json.append("{ \"success\":true,\"totalCount\":"+totals+ ",\"start_date\":\"" + pager.getStart_date() + "\",\"end_date\":\"" + pager.getEnd_date() + "\",\"columns\":"+columns+",\"totalRoot\":[");
		
		for(int i=0;i<list.size();i++){
			Object[]obj=(Object[]) list.get(i);
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"deviceId\":\""+obj[1]+"\",");
			result_json.append("\"deviceName\":\""+obj[2]+"\",");
			result_json.append("\"deviceType\":\""+obj[3]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[4]+"\",");
			result_json.append("\"alarmTime\":\""+obj[5]+"\",");
			result_json.append("\"itemName\":\""+obj[6]+"\",");
			result_json.append("\"alarmType\":\""+obj[7]+"\",");
			result_json.append("\"alarmTypeName\":\""+MemoryDataManagerTask.getCodeName("ALARMTYPE",obj[7]+"", language)+"\",");
			result_json.append("\"alarmValue\":\""+obj[8]+"\",");
			result_json.append("\"alarmInfo\":\""+obj[9]+"\",");
			result_json.append("\"alarmLimit\":\""+obj[10]+"\",");
			result_json.append("\"hystersis\":\""+obj[11]+"\",");
			result_json.append("\"alarmLevel\":\""+obj[12]+"\",");
			result_json.append("\"alarmLevelName\":\""+MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[12]+"", language)+"\",");
			result_json.append("\"isSendMessage\":\""+obj[13]+"\",");
			result_json.append("\"isSendMail\":\""+obj[14]+"\",");
			result_json.append("\"recoveryTime\":\""+obj[15]+"\",");
			result_json.append("\"orgId\":\""+obj[16]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public boolean exportAlarmData(User user,HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceType,String deviceId,String deviceName,String alarmType,String alarmLevel,String isSendMessage,Page pager,String language){
		try{
			
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			String tableName="viw_alarminfo_hist";
			
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			
			List<Object> headRow = new ArrayList<>();
			for(int i=0;i<heads.length;i++){
				headRow.add(heads[i]);
			}
		    List<List<Object>> sheetDataList = new ArrayList<>();
		    sheetDataList.add(headRow);
			
			String sql="select t.id,t.deviceid,t.devicename,t.devicetype,t.deviceTypeName_"+language+",to_char(t.alarmtime,'yyyy-mm-dd hh24:mi:ss') as alarmtime,"
					+ " t.itemname,t.alarmtype,"
					+ " t.alarmvalue,t.alarminfo,t.alarmlimit,t.hystersis,"
					+ " t.alarmlevel,"
					+ " t.issendmessage,t.issendmail,"
					+ " t.recoverytime,t.orgid "
					+ " from "+tableName+" t where t.orgid in ("+orgId+") "
					+ " and t.alarmtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
			if(StringManagerUtils.isNotNull(deviceId)){
				sql+=" and t.deviceid="+deviceId;
			}
			if(StringManagerUtils.isNotNull(alarmType)){
				if(StringManagerUtils.stringToInteger(alarmType)==2){
					sql+=" and t.alarmType=2 or t.alarmType=5";
				}else {
					sql+=" and t.alarmType="+alarmType;
				}
			}
			if(StringManagerUtils.isNotNull(alarmLevel)){
				sql+=" and t.alarmLevel="+alarmLevel;
			}
			if(StringManagerUtils.isNotNull(isSendMessage)){
				sql+=" and t.isSendMessage="+isSendMessage;
			}
			sql+=" order by t.alarmtime desc";
			
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			
			List<?> list=this.findCallSql(finalSql);
			List<Object> record=null;
			JSONObject jsonObject=null;
			Object[] obj=null;
			StringBuffer result_json = null;
			for(int i=0;i<list.size();i++){
				obj=(Object[]) list.get(i);
				result_json = new StringBuffer();
				record = new ArrayList<>();
				result_json.append("{\"id\":\""+(i+1)+"\",");
				result_json.append("\"deviceId\":\""+obj[1]+"\",");
				result_json.append("\"deviceName\":\""+obj[2]+"\",");
				result_json.append("\"deviceType\":\""+obj[3]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[4]+"\",");
				result_json.append("\"alarmTime\":\""+obj[5]+"\",");
				result_json.append("\"itemName\":\""+obj[6]+"\",");
				result_json.append("\"alarmType\":\""+obj[7]+"\",");
				result_json.append("\"alarmTypeName\":\""+MemoryDataManagerTask.getCodeName("ALARMTYPE",obj[7]+"", user.getLanguageName())+"\",");
				result_json.append("\"alarmValue\":\""+obj[8]+"\",");
				result_json.append("\"alarmInfo\":\""+obj[9]+"\",");
				result_json.append("\"alarmLimit\":\""+obj[10]+"\",");
				result_json.append("\"hystersis\":\""+obj[11]+"\",");
				result_json.append("\"alarmLevel\":\""+obj[12]+"\",");
				result_json.append("\"alarmLevelName\":\""+MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[12]+"", user.getLanguageName())+"\",");
				result_json.append("\"isSendMessage\":\""+obj[13]+"\",");
				result_json.append("\"isSendMail\":\""+obj[14]+"\",");
				result_json.append("\"recoveryTime\":\""+obj[15]+"\",");
				result_json.append("\"orgId\":\""+obj[16]+"\"}");
				
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
	
	public String getAlarmOverviewData(String orgId,String deviceType,String deviceName,String alarmType,String alarmLevel,String isSendMessage,Page pager,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String tableName="viw_alarminfo_latest";
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns="["
				+ "{\"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50,children:[]},"
				+ "{\"header\":\""+languageResourceMap.get("deviceName")+"\",\"dataIndex\":\"deviceName\",flex:8,children:[]},"
				+ "{\"header\":\""+languageResourceMap.get("alarmTime")+"\",\"dataIndex\":\"alarmTime\",flex:10,children:[]},"
				+ "{ \"header\":\""+languageResourceMap.get("deviceType")+"\",\"dataIndex\":\"deviceTypeName\",flex:6,children:[] }"
				+ "]";
		String sql="select v.deviceid,v.devicename,v.devicetypename_"+language+",v.alarmtime from ("
				+ " select t.orgid,t.deviceid,t.devicename,t.devicetypename_"+language+",max(t.alarmtime) as alarmtime  "
				+ " from "+tableName+" t "
				+ " where t.orgid in("+orgId+")";
		if(StringManagerUtils.isNum(deviceType)){
			sql+= " and t.devicetype="+deviceType;
		}else{
			sql+= " and t.devicetype in ("+deviceType+")";
		}
		
		if(StringManagerUtils.stringToInteger(alarmType)==2){
			sql+=" and (t.alarmType=2 or t.alarmType=5)";
		}else {
			sql+= " and t.alarmtype="+alarmType;
		}
		
		if(StringManagerUtils.isNotNull(alarmLevel)){
			sql+=" and t.alarmLevel="+alarmLevel+"";
		}
		if(StringManagerUtils.isNotNull(isSendMessage)){
			sql+=" and t.isSendMessage="+isSendMessage+"";
		}
		
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.deviceName='"+deviceName+"'";
		}
		
		sql+= " group by t.orgid,t.deviceid,t.devicename,t.devicetypename_"+language+") v ";
		
		sql+=" order by v.alarmtime desc";
		int maxvalue=pager.getLimit()+pager.getStart();
		String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"deviceName\":\""+obj[1]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[2]+"\",");
			result_json.append("\"alarmTime\":\""+obj[3]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getAlarmOverviewExportData(String orgId,String deviceType,String deviceName,String alarmType,String alarmLevel,String isSendMessage,Page pager,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String tableName="viw_alarminfo_latest";
		String sql="select v.deviceid,v.devicename,v.devicetypename+"+language+",v.alarmtime from ("
				+ " select t.orgid,t.deviceid,t.devicename,t.devicetypename_"+language+",max(t.alarmtime) as alarmtime  "
				+ " from "+tableName+" t "
				+ " where t.orgid in("+orgId+")";
		if(StringManagerUtils.isNum(deviceType)){
			sql+= " and t.devicetype="+deviceType;
		}else{
			sql+= " and t.devicetype in ("+deviceType+")";
		}
		
		if(StringManagerUtils.stringToInteger(alarmType)==2){
			sql+=" and (t.alarmType=2 or t.alarmType=5)";
		}else {
			sql+= " and t.alarmtype="+alarmType;
		}
		
		if(StringManagerUtils.isNotNull(alarmLevel)){
			sql+=" and t.alarmLevel="+alarmLevel+"";
		}
		if(StringManagerUtils.isNotNull(isSendMessage)){
			sql+=" and t.isSendMessage="+isSendMessage+"";
		}
		
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.deviceName='"+deviceName+"'";
		}
		
		sql+= " group by t.orgid,t.deviceid,t.devicename,t.devicetypename_"+language+") v ";
		
		sql+=" order by v.alarmtime desc";
		List<?> list = this.findCallSql(sql);
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"deviceName\":\""+obj[1]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[2]+"\",");
			result_json.append("\"alarmTime\":\""+obj[3]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public boolean exportAlarmOverviewData(User user,HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceType,String deviceName,String alarmType,String alarmLevel,String isSendMessage,Page pager,String language){
		try{
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			String tableName="viw_alarminfo_latest";
			
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			
			List<Object> headRow = new ArrayList<>();
			for(int i=0;i<heads.length;i++){
				headRow.add(heads[i]);
			}
		    List<List<Object>> sheetDataList = new ArrayList<>();
		    sheetDataList.add(headRow);
		    
			String sql="select v.deviceid,v.devicename,v.devicetypename_"+language+",v.alarmtype,v.alarmtime from "
					+ " (select t.orgid,t.deviceid,t.devicename,t.devicetypename_"+language+",t.alarmtype,max(t.alarmtime) as alarmtime "
					+ " from "+tableName+" t"
					+ " where 1=1";
			if(StringManagerUtils.isNum(deviceType)){
				sql+= " and t.devicetype="+deviceType;
			}else{
				sql+= " and t.devicetype in ("+deviceType+")";
			}
			if(StringManagerUtils.isNotNull(alarmLevel)){
				sql+=" and t.alarmLevel="+alarmLevel+"";
			}
			if(StringManagerUtils.isNotNull(isSendMessage)){
				sql+=" and t.isSendMessage="+isSendMessage+"";
			}
			sql+= " group by t.orgid,t.deviceid,t.devicename,t.devicetypename_"+language+",t.alarmtype) v "
					+ " where v.orgid in("+orgId+") ";
			
			if(StringManagerUtils.stringToInteger(alarmType)==2){
				sql+=" and v.alarmType=2 or v.alarmType=5";
			}else {
				sql+= " and v.alarmtype="+alarmType;
			}
			
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and v.deviceName='"+deviceName+"'";
			}
			sql+=" order by v.alarmtime desc";
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			List<?> list=this.findCallSql(finalSql);
			List<Object> record=null;
			JSONObject jsonObject=null;
			StringBuffer result_json = null;
			Object[] obj=null;
			for(int i=0;i<list.size();i++){
				obj=(Object[]) list.get(i);
				result_json = new StringBuffer();
				record = new ArrayList<>();
				result_json.append("{\"id\":"+(i+1)+",");
				result_json.append("\"deviceName\":\""+obj[1]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[2]+"\",");
				result_json.append("\"alarmType\":\""+obj[3]+"\",");
				result_json.append("\"alarmTime\":\""+obj[4]+"\"}");
				
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
}
