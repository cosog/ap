package com.cosog.service.logQuery;

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
import com.cosog.utils.Config;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.excel.ExcelUtils;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

@Service("logQueryService")
public class LogQueryService<T> extends BaseService<T>  {

	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	
	public String getDeviceOperationLogData(String orgId,String deviceType,String deviceName,String operationType,Page pager,User user) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String ddicName="logQuery_DeviceOperationLog";
		DataDictionary ddic = null;
		List<String> ddicColumnsList=new ArrayList<String>();
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		String columns = ddic.getTableHeader();
		String sql=ddic.getSql()+" from viw_deviceoperationlog t where 1=1"
				+ " and t.orgid in ("+orgId+")"
				+ " and ("
				+ " t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
				+ " or t.user_no=(select t2.user_no from tbl_user t2 where  t2.user_no="+user.getUserNo()+")"
				+ " )"
				+ " and t.createtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
		if(StringManagerUtils.isNotNull(deviceType)){
			sql+=" and t.devicetype="+deviceType;
		}
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.deviceName='"+deviceName+"'";
		}
		if(StringManagerUtils.isNotNull(operationType)){
			sql+=" and t.action="+operationType;
		}
		sql+=" order by t.createtime desc";
		int maxvalue=pager.getLimit()+pager.getStart();
		String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		String getResult = this.findCustomPageBySqlEntity(sql,finalSql, columns, 20 + "", pager);
		return getResult.replaceAll("\"null\"", "\"\"");
	}
	
	public String getDeviceOperationLogExportData(String orgId,String deviceType,String deviceName,String operationType,Page pager,User user) throws IOException, SQLException{
		String ddicName="logQuery_DeviceOperationLog";
		DataDictionary ddic = null;
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		String columns = ddic.getTableHeader();
		String sql=ddic.getSql()+" from viw_deviceoperationlog t where 1=1"
				+ " and t.orgid in ("+orgId+")"
				+ " and ("
				+ " t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
				+ " or t.user_no=(select t2.user_no from tbl_user t2 where  t2.user_no="+user.getUserNo()+")"
				+ " )"
				+ " and t.createtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
		if(StringManagerUtils.isNotNull(deviceType)){
			sql+=" and t.devicetype="+deviceType;
		}
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.deviceName='"+deviceName+"'";
		}
		if(StringManagerUtils.isNotNull(operationType)){
			sql+=" and t.action="+operationType;
		}
		sql+=" order by t.createtime desc";
		
		String getResult = this.findExportDataBySqlEntity(sql,sql, columns, 20 + "", pager);
		return getResult.replaceAll("\"null\"", "\"\"");
	}
	
	public boolean exportDeviceOperationLogData(HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceType,String deviceName,String operationType,Page pager,User user) throws IOException, SQLException{
		try{
			StringBuffer result_json = new StringBuffer();
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
			
			String sql="select t.id,t.devicetype,t.deviceTypeName,"
					+ " t.deviceName,to_char(t.createtime,'yyyy-mm-dd hh24:mi:ss') as createtime,"
					+ " t.user_id,t.loginip,"
					+ " t.action,t.actionname,"
					+ " t.remark,t.orgid "
					+ " from viw_deviceoperationlog t where 1=1"
					+ " and t.orgid in ("+orgId+")"
					+ " and ("
					+ " t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
					+ " or t.user_no=(select t2.user_no from tbl_user t2 where  t2.user_no="+user.getUserNo()+")"
					+ " )"
					+ " and t.createtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
			if(StringManagerUtils.isNotNull(deviceType)){
				sql+=" and t.devicetype="+deviceType;
			}
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t.deviceName='"+deviceName+"'";
			}
			if(StringManagerUtils.isNotNull(operationType)){
				sql+=" and t.action="+operationType;
			}
			sql+=" order by t.createtime desc";
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			List<?> list=this.findCallSql(finalSql);
			List<Object> record=null;
			JSONObject jsonObject=null;
			Object[] obj=null;
			for(int i=0;i<list.size();i++){
				obj=(Object[]) list.get(i);
				result_json = new StringBuffer();
				record = new ArrayList<>();
				result_json.append("{\"id\":\""+(i+1)+"\",");
				result_json.append("\"deviceType\":\""+obj[1]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[2]+"\",");
				result_json.append("\"deviceName\":\""+obj[3]+"\",");
				result_json.append("\"createTime\":\""+obj[4]+"\",");
				result_json.append("\"user_id\":\""+obj[5]+"\",");
				result_json.append("\"loginIp\":\""+obj[6]+"\",");
				result_json.append("\"action\":\""+obj[7]+"\",");
				result_json.append("\"actionName\":\""+obj[8]+"\",");
				result_json.append("\"remark\":\""+obj[9]+"\",");
				result_json.append("\"orgId\":\""+obj[10]+"\"}");
				
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
		}
		return true;
	}
	
	public String getSystemLogData(String orgId,String operationType,Page pager,User user,String selectUserId) throws IOException, SQLException{
		String ddicName="logQuery_SystemLog";
		DataDictionary ddic = null;
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		String columns = ddic.getTableHeader();
		String currentTabs=user.getTabIds();
		String sql=ddic.getSql()+" from viw_systemlog t where "
				+ "t.orgid in ("+orgId+") "
				+ " and t.role_id not in( select distinct(t5.rt_roleid) from TBL_TAB2ROLE t5 where t5.rt_tabid not in("+currentTabs+") )"
				+ " and ("
				+ " t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
				+ " or t.user_no=(select t2.user_no from tbl_user t2 where  t2.user_no="+user.getUserNo()+")"
				+ " )"
				+ " and t.createtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
		
		if(StringManagerUtils.isNotNull(operationType)){
			sql+=" and t.action="+operationType;
		}
		if(StringManagerUtils.isNotNull(selectUserId)){
			sql+=" and t.user_id='"+selectUserId+"'";
		}
		sql+=" order by t.createtime desc";
		int maxvalue=pager.getLimit()+pager.getStart();
		String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		String getResult = this.findCustomPageBySqlEntity(sql,finalSql, columns, 20 + "", pager);
		return getResult.replaceAll("\"null\"", "\"\"");
	}
	
	public String getSystemLogExportData(String orgId,String operationType,Page pager,User user) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String ddicName="logQuery_SystemLog";
		DataDictionary ddic = null;
		List<String> ddicColumnsList=new ArrayList<String>();
		String currentTabs=user.getTabIds();
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		String columns = ddic.getTableHeader();
		String sql=ddic.getSql()+" from viw_systemlog t where "
				+ " t.orgid in ("+orgId+") "
				+ " and t.role_id not in( select distinct(t5.rt_roleid) from TBL_TAB2ROLE t5 where t5.rt_tabid not in("+currentTabs+") )"
				+ " and ("
				+ " t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
				+ " or t.user_no=(select t2.user_no from tbl_user t2 where  t2.user_no="+user.getUserNo()+")"
				+ " )"
				+ " and t.createtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
		
		if(StringManagerUtils.isNotNull(operationType)){
//			sql+=" and t.action="+operationType;
		}
		sql+=" order by t.createtime desc";
		
		String getResult = this.findExportDataBySqlEntity(sql,sql, columns, 20 + "", pager);
		return getResult.replaceAll("\"null\"", "\"\"");
	}
	
	public boolean exportSystemLogData(HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String operationType,Page pager,User user,String selectUserId) throws IOException, SQLException{
		try{
			StringBuffer result_json = new StringBuffer();
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			String currentTabs=user.getTabIds();
			List<Object> headRow = new ArrayList<>();
			for(int i=0;i<heads.length;i++){
				headRow.add(heads[i]);
			}
		    List<List<Object>> sheetDataList = new ArrayList<>();
		    sheetDataList.add(headRow);
			String sql="select t.id,to_char(t.createtime,'yyyy-mm-dd hh24:mi:ss') as createtime,"
					+ " t.user_no,t.user_id,t.role_id,t.role_level,"
					+ " t.loginip,t.action,t.actionname,"
					+ " t.remark,t.orgid "
					+ " from viw_systemlog t where "
					+ " t.orgid in ("+orgId+") "
					+ " and t.role_id not in( select distinct(t5.rt_roleid) from TBL_TAB2ROLE t5 where t5.rt_tabid not in("+currentTabs+") )"
					+ " and ("
					+ " t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
					+ " or t.user_no=(select t2.user_no from tbl_user t2 where  t2.user_no="+user.getUserNo()+")"
					+ " )"
					+ " and t.createtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
			if(StringManagerUtils.isNotNull(operationType)){
				sql+=" and t.action="+operationType;
			}
			if(StringManagerUtils.isNotNull(selectUserId)){
				sql+=" and t.user_id='"+selectUserId+"'";
			}
			sql+=" order by t.createtime desc";
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			List<?> list=this.findCallSql(finalSql);
			List<Object> record=null;
			JSONObject jsonObject=null;
			Object[] obj=null;
			for(int i=0;i<list.size();i++){
				obj=(Object[]) list.get(i);
				result_json = new StringBuffer();
				record = new ArrayList<>();
				result_json.append("{\"id\":\""+(i+1)+"\",");
				result_json.append("\"createTime\":\""+obj[1]+"\",");
				result_json.append("\"user_no\":\""+obj[2]+"\",");
				result_json.append("\"user_id\":\""+obj[3]+"\",");
				result_json.append("\"role_id\":\""+obj[4]+"\",");
				result_json.append("\"role_level\":\""+obj[5]+"\",");
				result_json.append("\"loginIp\":\""+obj[6]+"\",");
				result_json.append("\"action\":\""+obj[7]+"\",");
				result_json.append("\"actionName\":\""+obj[8]+"\",");
				result_json.append("\"remark\":\""+obj[9]+"\",");
				result_json.append("\"orgId\":\""+obj[10]+"\"}");
				
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
		}
		return true;
	}
	
	public String loadSystemLogActionComboxList() throws Exception {
		String sql = "select t.itemvalue,t.itemname from TBL_CODE t where t.itemcode='SYSTEMACTION' order by t.itemvalue";
		StringBuffer result_json = new StringBuffer();
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(sql);
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"},");
			if (null != list && list.size() > 0) {
				for (Object o : list) {
					Object[] obj = (Object[]) o;
					result_json.append("{boxkey:\"" + obj[0] + "\",");
					result_json.append("boxval:\"" + obj[1] + "\"},");
				}
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("]}");
		} catch (Exception e) {
			e.printStackTrace();
			result_json = new StringBuffer();
			result_json.append("{\"totals\":"+0+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"}]}");
		}
		return result_json.toString();
	}
}
