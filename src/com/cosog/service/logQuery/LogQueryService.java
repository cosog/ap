package com.cosog.service.logQuery;

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

@Service("logQueryService")
public class LogQueryService<T> extends BaseService<T>  {

	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	
	public String getDeviceOperationLogData(String orgId,String deviceType,String deviceName,String operationType,Page pager) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String ddicName="deviceOperationLog";
		DataDictionary ddic = null;
		List<String> ddicColumnsList=new ArrayList<String>();
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		String columns = ddic.getTableHeader();
		String sql=ddic.getSql()+" from viw_deviceoperationlog t where 1=1"
				+ " and t.orgid in ("+orgId+")"
				+ " and t.createtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
		if(StringManagerUtils.isNotNull(deviceType)){
			sql+=" and t.devicetype="+deviceType;
		}
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.wellName='"+deviceName+"'";
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
	
	public String getDeviceOperationLogExportData(String orgId,String deviceType,String deviceName,String operationType,Page pager) throws IOException, SQLException{
		String ddicName="deviceOperationLog";
		DataDictionary ddic = null;
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		String columns = ddic.getTableHeader();
		String sql=ddic.getSql()+" from viw_deviceoperationlog t where 1=1"
				+ " and t.orgid in ("+orgId+")"
				+ " and t.createtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
		if(StringManagerUtils.isNotNull(deviceType)){
			sql+=" and t.devicetype="+deviceType;
		}
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.wellName='"+deviceName+"'";
		}
		if(StringManagerUtils.isNotNull(operationType)){
			sql+=" and t.action="+operationType;
		}
		sql+=" order by t.createtime desc";
		
		String getResult = this.findExportDataBySqlEntity(sql,sql, columns, 20 + "", pager);
		return getResult.replaceAll("\"null\"", "\"\"");
	}
	
	public String getSystemLogData(String orgId,String operationType,Page pager) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String ddicName="SystemLog";
		DataDictionary ddic = null;
		List<String> ddicColumnsList=new ArrayList<String>();
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		String columns = ddic.getTableHeader();
		String sql=ddic.getSql()+" from viw_systemlog t where t.orgid in ("+orgId+") "
				+ " and t.createtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
		
		if(StringManagerUtils.isNotNull(operationType)){
//			sql+=" and t.action="+operationType;
		}
		sql+=" order by t.createtime desc";
		int maxvalue=pager.getLimit()+pager.getStart();
		String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		String getResult = this.findCustomPageBySqlEntity(sql,finalSql, columns, 20 + "", pager);
		return getResult.replaceAll("\"null\"", "\"\"");
	}
	
	public String getSystemLogExportData(String orgId,String operationType,Page pager) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String ddicName="SystemLog";
		DataDictionary ddic = null;
		List<String> ddicColumnsList=new ArrayList<String>();
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		String columns = ddic.getTableHeader();
		String sql=ddic.getSql()+" from viw_systemlog t where t.orgid in ("+orgId+") "
				+ " and t.createtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
		
		if(StringManagerUtils.isNotNull(operationType)){
//			sql+=" and t.action="+operationType;
		}
		sql+=" order by t.createtime desc";
		
		String getResult = this.findExportDataBySqlEntity(sql,sql, columns, 20 + "", pager);
		return getResult.replaceAll("\"null\"", "\"\"");
	}
}
