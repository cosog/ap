package com.cosog.service.operationMaintenance;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.Code;
import com.cosog.model.CurveConf;
import com.cosog.model.DataMapping;
import com.cosog.model.KeyValue;
import com.cosog.model.User;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.DisplayInstanceOwnItem;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.SRPCalculateRequestData;
import com.cosog.model.calculate.UserInfo;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.drive.ModbusProtocolConfig.Protocol;
import com.cosog.model.gridmodel.GraphicSetData;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.task.MemoryDataManagerTask.CalItem;
import com.cosog.utils.Config;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service("operationMaintenanceService")
public class OperationMaintenanceService<T> extends BaseService<T>  {
	@Autowired
	private CommonDataService service;
	
	public String batchExportModuleList(User user){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		String language=user!=null?user.getLanguageName():"";
		int languageValue=user!=null?user.getLanguage():0;
		int roleId=user!=null?user.getUserType():0;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		String userModule="select t.md_code,t2.rm_matrix from tbl_module t ,tbl_module2role t2 where t.md_id=t2.rm_moduleid and t2.rm_roleid="+roleId;
		List<?> moduleList=this.findCallSql(userModule);
		List<String> userModuleCodeList=new ArrayList<>();
		Map<String,Boolean> userModuleEditMap=new HashMap<>();
		for(int i=0;i<moduleList.size();i++){
			Object[] obj = (Object[]) moduleList.get(i);
			userModuleCodeList.add(obj[0]+"");
			String rights=obj[1]+"";
			boolean editabled=false;
			if(StringManagerUtils.isNotNull(rights)){
				String[] rightArr=rights.split(",");
				if(rightArr.length==3){
					editabled=StringManagerUtils.stringToInteger(rightArr[1])==1;
				}
			}
			userModuleEditMap.put(obj[0]+"", editabled);
			
		}
		
		tree_json.append("[");
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("module")+"\",");
		tree_json.append("\"code\":\"module\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("ModuleManagement")&&userModuleEditMap.get("ModuleManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("dataDictionary")+"\",");
		tree_json.append("\"code\":\"dataDictionary\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("DataDictionaryManagement")&& userModuleEditMap.get("DataDictionaryManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("organization")+"\",");
		tree_json.append("\"code\":\"organization\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("OrganizationAndUserManagement")&& userModuleEditMap.get("OrganizationAndUserManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("role")+"\",");
		tree_json.append("\"code\":\"role\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("RoleManagement")&& userModuleEditMap.get("RoleManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("user")+"\",");
		tree_json.append("\"code\":\"user\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("OrganizationAndUserManagement")&& userModuleEditMap.get("OrganizationAndUserManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("protocol")+"\",");
		tree_json.append("\"code\":\"protocol\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("acqUnit")+"\",");
		tree_json.append("\"code\":\"acqUnit\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("displayUnit")+"\",");
		tree_json.append("\"code\":\"displayUnit\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("alarmUnit")+"\",");
		tree_json.append("\"code\":\"alarmUnit\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("reportUnit")+"\",");
		tree_json.append("\"code\":\"reportUnit\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("acqInstance")+"\",");
		tree_json.append("\"code\":\"acqInstance\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("displayInstance")+"\",");
		tree_json.append("\"code\":\"displayInstance\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("alarmInstance")+"\",");
		tree_json.append("\"code\":\"alarmInstance\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("reportInstance")+"\",");
		tree_json.append("\"code\":\"reportInstance\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("auxiliaryDevice")+"\",");
		tree_json.append("\"code\":\"auxiliaryDevice\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		tree_json.append("{\"classes\":1,");
		tree_json.append("\"text\":\""+languageResourceMap.get("primaryDevice")+"\",");
		tree_json.append("\"code\":\"primaryDevice\",");
		tree_json.append("\"disabled\": "+(! userModuleEditMap.containsKey("DriverManagement")&& userModuleEditMap.get("DriverManagement") )+",");
		tree_json.append("\"checked\": false");
//		tree_json.append("\"leaf\": true");
		tree_json.append("},");
		
		
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("{ \"success\":true,\"columns\":[],");
		result_json.append("\"totalCount\":"+16+",");
		result_json.append("\"totalRoot\":"+tree_json+"");
		result_json.append("}");
		
//		result_json.append("[");
//		result_json.append("{\"classes\":0,\"text\":\"数据列表\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
//		result_json.append("]");
		
		return result_json.toString();
	}
	
	public String importedFilePermissionVerification(User user,String code){
		boolean r=true;
		if("module".equalsIgnoreCase(code)){
			
		}else if("dataDictionary".equalsIgnoreCase(code)){
			String sql="select count(1) from tbl_module t,tbl_module2role t2 where t.md_id=t2.rm_moduleid and t2.rm_roleid="+(user!=null?user.getUserType():0);
			int total=this.getTotalCountRows(sql);
			if(total>0){
				r=true;
			}else {
				r=false;
			}
	    }else if("organization".equalsIgnoreCase(code)){
	    	
	    }else if("role".equalsIgnoreCase(code)){
	    	String sql="select count(1) from tbl_module t,tbl_module2role t2 where t.md_id=t2.rm_moduleid and t2.rm_roleid="+(user!=null?user.getUserType():0);
	    	int total=this.getTotalCountRows(sql);
			if(total>0){
				r=true;
			}else {
				r=false;
			}
	    }else if("user".equalsIgnoreCase(code)){
	    	String orgSql="select count(1) from tbl_org t where t.org_id="+(user!=null?user.getUserOrgid():0);
	    	String roleSql="select count(1) from tbl_role t where t.role_id="+(user!=null?user.getUserType():0);
	    	int orgCount=this.getTotalCountRows(orgSql);
	    	int roleount=this.getTotalCountRows(roleSql);
			if(orgCount>0 && roleount>0){
				r=true;
			}else {
				r=false;
			}
	    }else if("auxiliaryDevice".equalsIgnoreCase(code)){
	    	
	    }else if("primaryDevice".equalsIgnoreCase(code)){
	    	String orgSql="select count(1) from tbl_org t where t.org_id="+(user!=null?user.getUserOrgid():0);
	    	int orgCount=this.getTotalCountRows(orgSql);
			if(orgCount>0){
				r=true;
			}else {
				r=false;
			}
	    }else if("protocol".equalsIgnoreCase(code)){
	    	
	    }else if("acqUnit".equalsIgnoreCase(code)){
	    	String sql="select count(1) from tbl_protocol t";
			int total=this.getTotalCountRows(sql);
			if(total>0){
				r=true;
			}else {
				r=false;
			}
	    }else if("displayUnit".equalsIgnoreCase(code)){
	    	String sql="select count(1) from tbl_acq_unit_conf t";
			int total=this.getTotalCountRows(sql);
			if(total>0){
				r=true;
			}else {
				r=false;
			}
	    }else if("alarmUnit".equalsIgnoreCase(code)){
	    	String sql="select count(1) from tbl_protocol t";
			int total=this.getTotalCountRows(sql);
			if(total>0){
				r=true;
			}else {
				r=false;
			}
	    }else if("reportUnit".equalsIgnoreCase(code)){
	    	
	    }else if("acqInstance".equalsIgnoreCase(code)){
	    	String sql="select count(1) from tbl_acq_unit_conf t";
			int total=this.getTotalCountRows(sql);
			if(total>0){
				r=true;
			}else {
				r=false;
			}
	    }else if("displayInstance".equalsIgnoreCase(code)){
	    	String sql="select count(1) from tbl_display_unit_conf t";
			int total=this.getTotalCountRows(sql);
			if(total>0){
				r=true;
			}else {
				r=false;
			}
	    }else if("alarmInstance".equalsIgnoreCase(code)){
	    	String sql="select count(1) from tbl_alarm_unit_conf t";
			int total=this.getTotalCountRows(sql);
			if(total>0){
				r=true;
			}else {
				r=false;
			}
	    }else if("reportInstance".equalsIgnoreCase(code)){
	    	String sql="select count(1) from tbl_report_unit_conf t";
			int total=this.getTotalCountRows(sql);
			if(total>0){
				r=true;
			}else {
				r=false;
			}
	    }
		
		return  "{success:true,msg:"+r+"}";
	}
	
	public String getOperationMaintenanceMonitorCurveData(String startDate,String endDate,User user)throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer itemsBuff = new StringBuffer();
		StringBuffer itemsCodeBuff = new StringBuffer();
		String language=user!=null?user.getLanguageName():"";
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try{
			List<String> acqTimeList=new ArrayList<>();
			itemsBuff.append("[\""+languageResourceMap.get("totalMemoryUsage")+"(Mb)\",\""+languageResourceMap.get("tomcatPhysicalMemory")+"(Mb)\",\""+languageResourceMap.get("JVMMemory")+"(Mb)\",\""+languageResourceMap.get("JVMHeapMemory")+"(Mb)\",\""+languageResourceMap.get("JVMNonHeapMemory")+"(Mb)\",\""+languageResourceMap.get("oraclePhysicalMemory")+"(Mb)\"]");
			
			itemsCodeBuff.append("[\"totalMemoryUsage\",\"tomcatPhysicalMemory\",\"jvmMemoryUsage\",\"jvmHeapMemoryUsage\",\"jvmNonHeapMemoryUsage\",\"oraclePhysicalMemory\"]");
			
			String sql="select to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
					+ " round(t.totalmemoryusage*1024,2) as totalmemoryusage,"
					+ " round(t.tomcatPhysicalMemory/1024/1024,2) as tomcatPhysicalMemory,"
					+ " round(t.jvmmemoryusage/1024/1024,2) as jvmmemoryusage,"
					+ " round(t.jvmheapmemoryusage/1024/1024,2) as jvmheapmemoryusage,"
					+ " round(t.jvmnonheapmemoryusage/1024/1024,2) as jvmnonheapmemoryusage,"
					+ " round(t.oraclephysicalmemory/1024/1024,2) as oraclephysicalmemory"
					+ " from TBL_RESOURCEMONITORING t "
					+ " where t.acqtime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss')"
					+ " order by t.acqtime";
			List<?> list = this.findCallSql(sql);
			
			result_json.append("{\"startDate\":\""+startDate+"\","
					+ "\"endDate\":\""+endDate+"\","
					+ "\"curveCount\":3,"
					+ "\"curveItems\":"+itemsBuff+","
					+ "\"curveItemCodes\":"+itemsCodeBuff+","
					+ "\"totalCount\":\""+list.size()+"\","
					+ "\"list\":[");
			
			String minAcqTime="";
			String maxAcqTime="";
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				if(i==0){
					minAcqTime=obj[0]+"";
				}else if(i==list.size()-1){
					maxAcqTime=obj[0]+"";
				}
				acqTimeList.add(obj[0]+"");
				result_json.append("{\"acqTime\":\""+obj[0]+"\",\"data\":["
				+(StringManagerUtils.isNum(obj[1]+"")?(obj[1]+""):null)+","
				+(StringManagerUtils.isNum(obj[2]+"")?(obj[2]+""):null)+","
				+(StringManagerUtils.isNum(obj[3]+"")?(obj[3]+""):null)+","
				+(StringManagerUtils.isNum(obj[4]+"")?(obj[4]+""):null)+","
				+(StringManagerUtils.isNum(obj[5]+"")?(obj[5]+""):null)+","
				+(StringManagerUtils.isNum(obj[6]+"")?(obj[6]+""):null)
				+"]},");
				
			}
			result_json.append("],\"minAcqTime\":\""+minAcqTime+"\",\"maxAcqTime\":\""+maxAcqTime+"\"}");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result_json.toString();
	}
	
	public List<T> operationMaintenanceService(Class<T> clazz, User user) throws Exception {
		
		String queryString = "SELECT tab FROM DeviceTypeInfo tab where tab.id in " 
				+ "( select distinct rt.rdDeviceTypeId from User u ,Role role,RoleDeviceType rt "
				+ "where  role.roleId =rt.rdRoleId   " 
				+ " and role.roleId = u.userType   and u.userNo="
				+ user.getUserNo() + ") order by tab.sortNum, tab.id";
		return find(queryString);
	}
	
	public int saveDeviceTypeContentConfigData(String selectDeviceTypeId,String contentConfig) throws IOException, SQLException {
		String sql="update tbl_devicetypeinfo t set t.config='"+contentConfig+"' where t.id="+selectDeviceTypeId;
		int r=getBaseDao().updateOrDeleteBySql(sql);
		return r;
	}
	
	public void modifyDeviceType(T deviceType) throws Exception {
		getBaseDao().updateObject(deviceType);
	}
	
	public String getDeviceTabManagerData(User user) throws IOException, SQLException {
		StringBuffer result_json = new StringBuffer();
		StringBuffer language_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		
		String columns=	"[]";
		
		String sql="select t.id,t.name,"
				+ " decode(t.calculatetype,1,'"+languageResourceMap.get("SRPCalculate")+"',2,'"+languageResourceMap.get("PCPCalculate")+"','"+languageResourceMap.get("nothing")+"') as calculateTypeName,"
				+ " t.sort "
				+ " from tbl_tabmanager_device t "
				+ " order by t.sort";


		
		List<?> list = this.findCallSql(sql);
		language_json.append("[");
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",");
		
		result_json.append("\"calculateTypeList\":[['"+languageResourceMap.get("nothing")+"','"+languageResourceMap.get("nothing")+"'],['"+languageResourceMap.get("SRPCalculate")+"','"+languageResourceMap.get("SRPCalculate")+"'],['"+languageResourceMap.get("PCPCalculate")+"','"+languageResourceMap.get("PCPCalculate")+"']],");
		result_json.append("\"totalRoot\":[");
		for (Object o : list) {
			Object[] obj = (Object[]) o;
			result_json.append("{\"instanceId\":"+obj[0]+",");
			result_json.append("\"name\":\""+obj[1]+"\",");
			result_json.append("\"calculateType\":\""+obj[2]+"\",");
			result_json.append("\"sort\":\""+obj[3]+"\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public void addDeviceTabManagerInstance(T deviceTabManager) throws Exception {
		getBaseDao().addObject(deviceTabManager);
	}
	
	public void modifyDeviceTabManagerInstance(T instance) throws Exception {
		getBaseDao().updateObject(instance);
	}
	
	public String loadDeviceTabManagerInstance(String instanceId,User user) throws IOException, SQLException {
		String result="{}";
		String sql="select t.config from tbl_tabmanager_device t where t.id="+instanceId;
		List<?> list = this.findCallSql(sql);
		if(list.size()>0 && list.get(0)!=null){
			result=list.get(0).toString().replaceAll("\r\n", "\n").replaceAll("\n", "").replaceAll(" ", "");
		}
		result="{\"success\":true,\"config\":"+result+"}";
		return result;
	}
	
	public String getDeviceTabInstanceConfig(String name) throws IOException, SQLException {
		String config="{}";
		String sql="select t.id,t.name,t.calculatetype,t.config from tbl_tabmanager_device t where t.name='"+name+"'";
		String instanceId="0";
		String instanceName="";
		String calculateType="0";
		List<?> list = this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			instanceId=obj[0]+"";
			instanceName=obj[1]+"";
			calculateType=obj[2]+"";
			if(obj[3]!=null){
				config=(obj[3]+"").replaceAll("\r\n", "\n").replaceAll("\n", "").replaceAll(" ", "");
			}else{
				config="{}";
			}
		}
		String result="{\"success\":true,\"instanceId\":"+instanceId+",\"instanceName\":\""+instanceName+"\",\"calculateType\":"+calculateType+",\"config\":"+config+"}";
		return result;
	}
	
	public String getDeviceTabInstanceInfoByDeviceId(String deviceId) throws IOException, SQLException {
		String config="{}";
		String sql="select t.id,t.name,t.calculatetype,t.config "
				+ " from tbl_tabmanager_device t,tbl_device t2 "
				+ " where t.id=t2.calculatetype "
				+ " and t2.id="+deviceId;
		String instanceId="0";
		String instanceName="";
		String calculateType="0";
		List<?> list = this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			instanceId=obj[0]+"";
			instanceName=obj[1]+"";
			calculateType=obj[2]+"";
			if(obj[3]!=null){
				config=(obj[3]+"").replaceAll("\r\n", "\n").replaceAll("\n", "").replaceAll(" ", "");
			}else{
				config="{}";
			}
		}
		String result="{\"success\":true,\"instanceId\":"+instanceId+",\"instanceName\":\""+instanceName+"\",\"calculateType\":"+calculateType+",\"config\":"+config+"}";
		return result;
	}
	
	public String loadDeviceTypeContentConfig(String deviceTypeId,User user) throws IOException, SQLException {
		String result="{}";
		String sql="select t.config from tbl_devicetypeinfo t where t.id="+deviceTypeId;
		List<?> list = this.findCallSql(sql);
		if(list.size()>0 && list.get(0)!=null){
			result=list.get(0).toString().replaceAll("\r\n", "\n").replaceAll("\n", "").replaceAll(" ", "");
		}
		result="{\"success\":true,\"config\":"+result+"}";
		return result;
	}
	
	public int saveDeviceTabManagerInstanceConfigData(String selectInstanceId,String instanceConfig) throws IOException, SQLException {
		String sql="update tbl_tabmanager_device t set t.config='"+instanceConfig+"' where t.id="+selectInstanceId;
		int r=getBaseDao().updateOrDeleteBySql(sql);
		return r;
	}
	
	public int deleteDeviceTabManagerInstance(String instanceIds) throws IOException, SQLException {
		String sql="delete from tbl_tabmanager_device t where t.id in ("+instanceIds+")";
		int r=getBaseDao().updateOrDeleteBySql(sql);
		return r;
	}
}
