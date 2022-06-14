package com.cosog.service.alarmQuery;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AlarmShowStyle;
import com.cosog.model.data.DataDictionary;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

@Service("alarmQueryService")
public class AlarmQueryService<T> extends BaseService<T>  {

	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	
	public String getAlarmData(String orgId,String deviceType,String deviceId,String deviceName,String alarmType,String alarmLevel,String isSendMessage,Page pager) throws IOException, SQLException{
		String ddicName="commStatusAlarm";
		if(StringManagerUtils.stringToInteger(alarmType)==0){
			ddicName="switchingValueAlarm";
		}else if(StringManagerUtils.stringToInteger(alarmType)==1){
			ddicName="enumValueAlarm";
		}else if(StringManagerUtils.stringToInteger(alarmType)==2){
			ddicName="numericValueAlarm";
		}else if(StringManagerUtils.stringToInteger(alarmType)==3){
			ddicName="commStatusAlarm";
		}
		
		String tableName="viw_rpcalarminfo_hist";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="viw_pcpalarminfo_hist";
		}
		
		DataDictionary ddic = null;
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		String columns = ddic.getTableHeader();
		String sql=ddic.getSql()+" from "+tableName+" t where t.orgid in ("+orgId+") "
				+ " and t.alarmtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
		if(StringManagerUtils.isNotNull(deviceId)){
			sql+=" and t.wellid="+deviceId;
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
		
		String getResult = this.findCustomPageBySqlEntity(sql,finalSql, columns, 20 + "", pager);
		return getResult.replaceAll("\"null\"", "\"\"");
	}
	
	public String getAlarmExportData(String orgId,String deviceType,String deviceId,String deviceName,String alarmType,String alarmLevel,String isSendMessage,Page pager) throws IOException, SQLException{
		String ddicName="commStatusAlarm";
		if(StringManagerUtils.stringToInteger(alarmType)==0){
			ddicName="switchingValueAlarm";
		}else if(StringManagerUtils.stringToInteger(alarmType)==1){
			ddicName="enumValueAlarm";
		}else if(StringManagerUtils.stringToInteger(alarmType)==2){
			ddicName="numericValueAlarm";
		}else if(StringManagerUtils.stringToInteger(alarmType)==3){
			ddicName="commStatusAlarm";
		}
		String tableName="viw_rpcalarminfo_hist";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="viw_pcpalarminfo_hist";
		}
		DataDictionary ddic = null;
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		String columns = ddic.getTableHeader();
		String sql=ddic.getSql()+" from "+tableName+" t where t.orgid in ("+orgId+") "
				+ " and t.alarmtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
		if(StringManagerUtils.isNotNull(deviceId)){
			sql+=" and t.wellid="+deviceId;
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
		
		String getResult = this.findExportDataBySqlEntity(sql,sql, columns, 20 + "", pager);
		return getResult.replaceAll("\"null\"", "\"\"");
	}
	
	public String getAlarmOverviewData(String orgId,String deviceType,String deviceName,String alarmType,String alarmLevel,String isSendMessage,Page pager) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		
		String tableName="viw_rpcalarminfo_latest";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="viw_pcpalarminfo_latest";
		}
		
		String columns="["
				+ "{\"header\":\"序号\",\"dataIndex\":\"id\",width:50,children:[]},"
				+ "{\"header\":\"井名\",\"dataIndex\":\"wellName\",flex:8,children:[]},"
				+ "{\"header\":\"报警时间\",\"dataIndex\":\"alarmTime\",flex:10,children:[]},"
				+ "{ \"header\":\"设备类型\",\"dataIndex\":\"deviceTypeName\",flex:6,children:[] }"
				+ "]";
		String sql="select v.wellid,v.wellname,v.devicetypename,v.alarmtype,v.alarmtime from "
				+ " (select t.orgid,t.wellid,t.wellname,c1.itemname as devicetypename,t.alarmtype,max(t.alarmtime) as alarmtime "
				+ " from "+tableName+" t,tbl_code c1 "
				+ " where c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue";
		if(StringManagerUtils.isNotNull(alarmLevel)){
			sql+=" and t.alarmLevel="+alarmLevel+"";
		}
		if(StringManagerUtils.isNotNull(isSendMessage)){
			sql+=" and t.isSendMessage="+isSendMessage+"";
		}
		sql+= " group by t.orgid,t.wellid,t.wellname,c1.itemname,t.alarmtype) v "
				+ " where v.orgid in("+orgId+") ";
		
		if(StringManagerUtils.stringToInteger(alarmType)==2){
			sql+=" and v.alarmType=2 or v.alarmType=5";
		}else {
			sql+= " and v.alarmtype="+alarmType;
		}
		
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and v.wellName='"+deviceName+"'";
		}
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
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[2]+"\",");
			result_json.append("\"alarmType\":\""+obj[3]+"\",");
			result_json.append("\"alarmTime\":\""+obj[4]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getAlarmOverviewExportData(String orgId,String deviceType,String deviceName,String alarmType,String alarmLevel,String isSendMessage,Page pager) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String tableName="viw_rpcalarminfo_latest";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="viw_pcpalarminfo_latest";
		}
		String sql="select v.wellid,v.wellname,v.devicetypename,v.alarmtype,v.alarmtime from "
				+ " (select t.orgid,t.wellid,t.wellname,c1.itemname as devicetypename,t.alarmtype,max(t.alarmtime) as alarmtime "
				+ " from "+tableName+" t,tbl_code c1 "
				+ " where c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue";
		if(StringManagerUtils.isNotNull(alarmLevel)){
			sql+=" and t.alarmLevel="+alarmLevel+"";
		}
		if(StringManagerUtils.isNotNull(isSendMessage)){
			sql+=" and t.isSendMessage="+isSendMessage+"";
		}
		sql+= " group by t.orgid,t.wellid,t.wellname,c1.itemname,t.alarmtype) v "
				+ " where v.orgid in("+orgId+") ";
		
		if(StringManagerUtils.stringToInteger(alarmType)==2){
			sql+=" and v.alarmType=2 or v.alarmType=5";
		}else {
			sql+= " and v.alarmtype="+alarmType;
		}
		
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and v.wellName='"+deviceName+"'";
		}
		sql+=" order by v.alarmtime desc";
		List<?> list = this.findCallSql(sql);
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[2]+"\",");
			result_json.append("\"alarmType\":\""+obj[3]+"\",");
			result_json.append("\"alarmTime\":\""+obj[4]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
}
