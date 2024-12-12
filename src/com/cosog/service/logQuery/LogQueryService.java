package com.cosog.service.logQuery;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AlarmShowStyle;
import com.cosog.model.Code;
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
		String sql="select t.id,t.devicetype,t.deviceTypeName,"
				+ " t.deviceName,to_char(t.createtime,'yyyy-mm-dd hh24:mi:ss') as createtime,"
				+ " t.user_id,t.loginip,"
				+ " t.action,"
				+ " t.remark,t.orgid "
				+ " from viw_deviceoperationlog t where 1=1"
				+ " and t.orgid in ("+orgId+")"
				+ " and ("
				+ " t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
				+ " or t.user_no=(select t2.user_no from tbl_user t2 where  t2.user_no="+user.getUserNo()+")"
				+ " )"
				+ " and t.createtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
		if(StringManagerUtils.isNotNull(deviceType)){
			if(StringManagerUtils.isNum(deviceType)){
				sql+= " and t.devicetype="+deviceType;
			}else{
				sql+= " and t.devicetype in ("+deviceType+")";
			}
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
		
		
		
		List<?> list=this.findCallSql(finalSql);
		int totals=this.getTotalCountRows(sql);
		
		result_json.append("{ \"success\":true,\"totalCount\":"+totals+ ",\"start_date\":\"" + pager.getStart_date() + "\",\"end_date\":\"" + pager.getEnd_date() + "\",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[]obj=(Object[]) list.get(i);
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"deviceType\":\""+obj[1]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[2]+"\",");
			result_json.append("\"deviceName\":\""+obj[3]+"\",");
			result_json.append("\"createTime\":\""+obj[4]+"\",");
			result_json.append("\"user_id\":\""+obj[5]+"\",");
			result_json.append("\"loginIp\":\""+obj[6]+"\",");
			result_json.append("\"action\":\""+obj[7]+"\",");
			result_json.append("\"actionName\":\""+MemoryDataManagerTask.getCodeName("ACTION",obj[7]+"", user.getLanguageName())+"\",");
			result_json.append("\"remark\":\""+obj[8]+"\",");
			result_json.append("\"orgId\":\""+obj[9]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
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
					+ " t.action,"
					+ " t.remark,t.orgid "
					+ " from viw_deviceoperationlog t where 1=1"
					+ " and t.orgid in ("+orgId+")"
					+ " and ("
					+ " t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
					+ " or t.user_no=(select t2.user_no from tbl_user t2 where  t2.user_no="+user.getUserNo()+")"
					+ " )"
					+ " and t.createtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
			if(StringManagerUtils.isNotNull(deviceType)){
				if(StringManagerUtils.isNum(deviceType)){
					sql+= " and t.devicetype="+deviceType;
				}else{
					sql+= " and t.devicetype in ("+deviceType+")";
				}
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
				result_json.append("\"actionName\":\""+MemoryDataManagerTask.getCodeName("ACTION",obj[7]+"", user.getLanguageName())+"\",");
				result_json.append("\"remark\":\""+obj[8]+"\",");
				result_json.append("\"orgId\":\""+obj[9]+"\"}");
				
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
	
	public String getSystemLogData(String orgId,String operationType,Page pager,User user,String selectUserId,String language) throws IOException, SQLException{
		String ddicName="logQuery_SystemLog";
		StringBuffer result_json = new StringBuffer();
		DataDictionary ddic = null;
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		String columns = ddic.getTableHeader();
		String currentDeviceTypeIds=user.getDeviceTypeIds();
		String sql="select t.id,to_char(t.createtime,'yyyy-mm-dd hh24:mi:ss') as createtime,"
				+ " t.user_no,t.user_id,t.role_id,t.role_level,"
				+ " t.loginip,t.action,"
				+ " t.remark,t.orgid "
				+ " from viw_systemlog t where "
				+ "t.orgid in ("+orgId+") "
				+ " and t.role_id not in( select distinct(t5.rd_roleid) from TBL_DEVICETYPE2ROLE t5 where t5.rd_devicetypeid not in("+currentDeviceTypeIds+") )"
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
		
		List<?> list=this.findCallSql(finalSql);
		int totals=this.getTotalCountRows(sql);
		
		result_json.append("{ \"success\":true,\"totalCount\":"+totals+ ",\"start_date\":\"" + pager.getStart_date() + "\",\"end_date\":\"" + pager.getEnd_date() + "\",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[]obj=(Object[]) list.get(i);
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"createTime\":\""+obj[1]+"\",");
			result_json.append("\"user_no\":\""+obj[2]+"\",");
			result_json.append("\"user_id\":\""+obj[3]+"\",");
			result_json.append("\"role_id\":\""+obj[4]+"\",");
			result_json.append("\"role_level\":\""+obj[5]+"\",");
			result_json.append("\"loginIp\":\""+obj[6]+"\",");
			result_json.append("\"action\":\""+obj[7]+"\",");
			result_json.append("\"actionName\":\""+MemoryDataManagerTask.getCodeName("SYSTEMACTION",obj[7]+"", language)+"\",");
			result_json.append("\"remark\":\""+obj[8]+"\",");
			result_json.append("\"orgId\":\""+obj[9]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public boolean exportSystemLogData(HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String operationType,Page pager,User user,String selectUserId) throws IOException, SQLException{
		try{
			StringBuffer result_json = new StringBuffer();
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			String currentDeviceTypeIds=user.getDeviceTypeIds();
			List<Object> headRow = new ArrayList<>();
			for(int i=0;i<heads.length;i++){
				headRow.add(heads[i]);
			}
		    List<List<Object>> sheetDataList = new ArrayList<>();
		    sheetDataList.add(headRow);
			String sql="select t.id,to_char(t.createtime,'yyyy-mm-dd hh24:mi:ss') as createtime,"
					+ " t.user_no,t.user_id,t.role_id,t.role_level,"
					+ " t.loginip,t.action,"
					+ " t.remark,t.orgid "
					+ " from viw_systemlog t where "
					+ " t.orgid in ("+orgId+") "
					+ " and t.role_id not in( select distinct(t5.rd_roleid) from TBL_DEVICETYPE2ROLE t5 where t5.rd_devicetypeid not in("+currentDeviceTypeIds+") )"
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
				result_json.append("\"actionName\":\""+MemoryDataManagerTask.getCodeName("SYSTEMACTION",obj[7]+"", user.getLanguageName())+"\",");
				result_json.append("\"remark\":\""+obj[8]+"\",");
				result_json.append("\"orgId\":\""+obj[9]+"\"}");
				
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
					saveSystemLog(user,4,"export file:"+title);
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
	
	public String loadSystemLogActionComboxList(String language) throws Exception {
		StringBuffer result_json = new StringBuffer();
		Map<String,Code> codeMap=MemoryDataManagerTask.getCodeMap("SYSTEMACTION",language);
		result_json.append("{\"totals\":"+(codeMap.size()+1)+",\"list\":[{boxkey:\"\",boxval:\""+MemoryDataManagerTask.getLanguageResourceItem(language,"selectAll")+"\"},");
		Iterator<Map.Entry<String,Code>> it = codeMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Code> entry = it.next();
			Code c=entry.getValue();
			result_json.append("{boxkey:\"" + c.getItemvalue() + "\",");
			result_json.append("boxval:\"" + c.getItemname() + "\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
}
