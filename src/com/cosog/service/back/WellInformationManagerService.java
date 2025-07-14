package com.cosog.service.back;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AcquisitionGroup;
import com.cosog.model.AcquisitionUnitGroup;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.AuxiliaryDeviceAddInfo;
import com.cosog.model.AuxiliaryDeviceInformation;
import com.cosog.model.Code;
import com.cosog.model.DeviceInformation;
import com.cosog.model.PumpingModelInformation;
import com.cosog.model.MasterAndAuxiliaryDevice;
import com.cosog.model.PcpDeviceInformation;
import com.cosog.model.DeviceAddInfo;
import com.cosog.model.SRPDeviceInformation;
import com.cosog.model.SmsDeviceInformation;
import com.cosog.model.User;
import com.cosog.model.VideoKey;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AlarmInstanceOwnItem;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.FSDiagramConstructionRequestData;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.PCPProductionData;
import com.cosog.model.calculate.PumpingPRTFData;
import com.cosog.model.calculate.SRPCalculateRequestData;
import com.cosog.model.calculate.SRPDeviceInfo;
import com.cosog.model.calculate.SRPProductionData;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.drive.WaterCutRawData;
import com.cosog.model.gridmodel.AuxiliaryDeviceHandsontableChangedData;
import com.cosog.model.gridmodel.PumpingModelHandsontableChangedData;
import com.cosog.model.gridmodel.VideoKeyHandsontableChangedData;
import com.cosog.model.gridmodel.WellGridPanelData;
import com.cosog.model.gridmodel.WellHandsontableChangedData;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.LicenseMap;
import com.cosog.utils.Page;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.LicenseMap.License;
import com.cosog.utils.excel.ExcelUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONObject;
import oracle.sql.CLOB;
import redis.clients.jedis.Jedis;

@Service("wellInformationManagerService")
public class WellInformationManagerService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	
	public String loadWellComboxList(Page pager,String orgId,String deviceName,String deviceTypeStr,String calculateTypeStr,String language) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		String tableName="tbl_device";
		if(deviceType>=300){
			tableName="tbl_smsdevice";
		}
		String sql = " select  t.deviceName as deviceName,t.deviceName as dm "
				+ " from  "+tableName+" t  ,tbl_org  g "
				+ " where t.orgId=g.org_id  "
				+ " and g.org_id in ("+ orgId + ")";
		if(!"tbl_smsdevice".equalsIgnoreCase(tableName)){
			if (StringManagerUtils.isNotNull(deviceTypeStr)) {
				if(StringManagerUtils.isNum(deviceTypeStr)){
					sql+= " and t.devicetype="+deviceTypeStr;
				}else{
					sql+= " and t.devicetype in ("+deviceTypeStr+")";
				}
			}
			if (StringManagerUtils.isNotNull(calculateTypeStr)) {
				sql += " and t.calculateType="+calculateTypeStr;
			}
		}
		if (StringManagerUtils.isNotNull(deviceName)) {
			sql += " and t.deviceName like '%" + deviceName + "%'";
		}
		sql += " order by t.sortNum, t.deviceName";
		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
		sqlCuswhere.append(""+sql);
		int maxvalue=pager.getLimit()+pager.getStart();
		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
		sqlCuswhere.append(" where rn >"+pager.getStart());
		String finalsql=sqlCuswhere.toString();
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(finalsql);
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\""+MemoryDataManagerTask.getLanguageResourceItem(language,"selectAll")+"\"},");
			String get_key = "";
			String get_val = "";
			if (null != list && list.size() > 0) {
				for (Object o : list) {
					Object[] obj = (Object[]) o;
					get_key = obj[0] + "";
					get_val = (String) obj[1];
					result_json.append("{boxkey:\"" + get_key + "\",");
					result_json.append("boxval:\"" + get_val + "\"},");
				}
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("]}");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}
	
	public String loadPumpingManufacturerComboxList(String manufacturer,String language) {
		StringBuffer result_json = new StringBuffer();
		String sql = " select distinct(t.manufacturer) from tbl_pumpingmodel t where 1=1";
		
		if (StringManagerUtils.isNotNull(manufacturer)) {
			sql += " and t.manufacturer like '%"+manufacturer+"%'";
		}
		sql += " order by t.manufacturer";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"totals\":"+list.size()+",\"list\":[{boxkey:\"\",boxval:\""+MemoryDataManagerTask.getLanguageResourceItem(language,"selectAll")+"\"},");
		String get_key = "";
		String get_val = "";
		if (null != list && list.size() > 0) {
			for (Object o : list) {
				get_key = o.toString();
				get_val = get_key;
				result_json.append("{boxkey:\"" + get_key + "\",");
				result_json.append("boxval:\"" + get_val + "\"},");
			}
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String loadPumpingManufacturerDropdownList() {
		StringBuffer result_json = new StringBuffer();
		String sql = " select distinct(t.manufacturer) from tbl_pumpingmodel t order by t.manufacturer";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"items\":[\"\",");
		if (null != list && list.size() > 0) {
			for (Object o : list) {
				result_json.append("\""+o.toString()+"\",");
			}
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getPumpingModelInfo(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer modelBuf = new StringBuffer();
		Map<String,List<String>> manufacturerMap=new HashMap<>();
		String sql = "select t.manufacturer,t.model,t.stroke,t.balanceweight from tbl_pumpingmodel t order by t.manufacturer,t.id";
		List<?> list = this.findCallSql(sql);
		for (Object o : list) {
			modelBuf = new StringBuffer();
			Object[] obj = (Object[]) o;
			String manufacturer=obj[0]+"";
			String model=obj[1]+"";
			String stroke=obj[2]+"";
			String balanceweight=obj[3]+"";
			modelBuf.append("{\"model\":\""+model+"\",\"stroke\":["+stroke+"],\"balanceWeight\":["+balanceweight+"]}");
			List<String> modelList=manufacturerMap.get(manufacturer);
			if(modelList==null){
				modelList=new ArrayList<>();
			}
			modelList.add(modelBuf.toString());
			manufacturerMap.put(manufacturer, modelList);
		}
		
		result_json.append("{\"manufacturerList\":[");
		for (Map.Entry<String,List<String>> entry : manufacturerMap.entrySet()) {
	        String manufacturer = entry.getKey();
	        List<String> modelList = entry.getValue();
	        result_json.append("{\"manufacturer\":\""+manufacturer+"\",\"modelList\":[");
	        for(int i=0;i<modelList.size();i++){
	        	result_json.append(modelList.get(i)+",");
	        }
	        if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
	        result_json.append("]");
	        result_json.append("},");
	    }
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		
		return result_json.toString();
	}
	
	public String loadPumpingModelDropdownList(String manufacturer) {
		StringBuffer result_json = new StringBuffer();
		String sql = " select t.model from tbl_pumpingmodel t where 1=1";
		if (StringManagerUtils.isNotNull(manufacturer)) {
			sql += " and t.manufacturer = '"+manufacturer+"'";
		}
		sql += "order by t.manufacturer,t.model";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"items\":[\"\",");
		if (null != list && list.size() > 0) {
			for (Object o : list) {
				result_json.append("\""+o.toString()+"\",");
			}
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String loadPumpingModelComboxList(String manufacturer,String model,String language) {
		StringBuffer result_json = new StringBuffer();
		String sql = "select t.model from tbl_pumpingmodel t where 1=1";
		
		if (StringManagerUtils.isNotNull(manufacturer)) {
			sql += " and t.manufacturer like '%"+manufacturer+"%'";
		}
		if (StringManagerUtils.isNotNull(model)) {
			sql += " and t.model like '%"+model+"%'";
		}
		sql += " order by t.manufacturer,t.model";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"totals\":"+list.size()+",\"list\":[{boxkey:\"\",boxval:\""+MemoryDataManagerTask.getLanguageResourceItem(language,"selectAll")+"\"},");
		String get_key = "";
		String get_val = "";
		if (null != list && list.size() > 0) {
			for (Object o : list) {
				get_key = o.toString();
				get_val = get_key;
				result_json.append("{boxkey:\"" + get_key + "\",");
				result_json.append("boxval:\"" + get_val + "\"},");
			}
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getDeviceOrgChangeDeviceList(Page pager,String orgId,String deviceName,String deviceTypeStr,String language) throws Exception {
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		StringBuffer result_json = new StringBuffer();
		String tableName="VIW_DEVICE";
		if(StringManagerUtils.isNum(deviceTypeStr) &&StringManagerUtils.stringToInteger(deviceTypeStr)>=300){
			tableName="tbl_smsdevice";
		}
		String sql = " select  t.id,t.deviceName,t.allpath_"+language+",deviceTypeAllPath_"+language+" "
				+ " from  "+tableName+" t "
						+ " where t.orgid in ("+ orgId + ")"
				+ " and t.deviceType in ("+deviceTypeStr+")";
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.deviceName like '%"+deviceName+"%'";
		}	
		sql+= " order by t.sortNum, t.deviceName";
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"deviceName\",width:120 ,children:[] }"
				+ "]";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");

		for (Object o : list) {
			Object[] obj = (Object[]) o;
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"deviceName\":\""+obj[1]+"\",");
			result_json.append("\"orgName\":\""+obj[2]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[3]+"\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getApplicationScenariosList(String language) throws Exception {
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		StringBuffer result_json = new StringBuffer();
		Map<String,Code> codeMap=MemoryDataManagerTask.getCodeMap("APPLICATIONSCENARIOS",language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("applicationScenarios")+"\",\"dataIndex\":\"applicationScenariosName\",width:120 ,children:[] }"
				+ "]";
		result_json.append("{\"success\":true,\"totalCount\":"+codeMap.size()+",\"columns\":"+columns+",\"totalRoot\":[");
		Iterator<Map.Entry<String,Code>> it = codeMap.entrySet().iterator();
		int idx=1;
		while(it.hasNext()){
			Map.Entry<String, Code> entry = it.next();
			Code c=entry.getValue();
			result_json.append("{\"id\":"+idx+",");
			result_json.append("\"applicationScenarios\":"+c.getItemvalue()+",");
			result_json.append("\"applicationScenariosName\":\""+c.getItemname()+"\"},");
			idx++;
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getApplicationScenariosComb(String language) throws Exception {
		StringBuffer result_json = new StringBuffer();
		Map<String,Code> codeMap=MemoryDataManagerTask.getCodeMap("APPLICATIONSCENARIOS",language);
		Iterator<Map.Entry<String,Code>> it = codeMap.entrySet().iterator();
		result_json.append("{\"totals\":"+codeMap.size()+",\"list\":[");
		while(it.hasNext()){
			Map.Entry<String, Code> entry = it.next();
			Code c=entry.getValue();
			result_json.append("{\"boxkey\":\"" + c.getItemvalue() + "\",");
			result_json.append("\"boxval\":\"" + c.getItemname() + "\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getDeviceTypeComb(String deviceTypes,String language) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "select t.id,t.name_"+language+" from tbl_devicetypeinfo t where t.id in("+deviceTypes+") ";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"totals\":"+(list.size())+",\"list\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[])list.get(i);
			result_json.append("{\"boxkey\":\"" + obj[0] + "\",");
			result_json.append("\"boxval\":\"" + obj[1] + "\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public void changeDeviceOrg(String selectedDeviceId,String selectedOrgId,String selectedOrgName,String deviceTypeStr) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		if(StringManagerUtils.stringToInteger(selectedOrgId)>0 && StringManagerUtils.isNotNull(selectedDeviceId)){
			int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
			String tableName="tbl_device";
			if(deviceType>=300){
				tableName="tbl_smsdevice";
			}
			String sql = "update "+tableName+" t set t.orgid="+selectedOrgId+" where t.id in ("+selectedDeviceId+")";
			this.getBaseDao().updateOrDeleteBySql(sql);
			
			try{
				if(deviceType<300){
					List<DeviceInfo> deviceInfoList =MemoryDataManagerTask.getDeviceInfo();
					
					String orgNameSql="select t.org_name_zh_cn,t.org_name_en,t.org_name_ru from TBL_ORG t where t.org_id="+selectedOrgId;
					List<?> list = this.findCallSql(orgNameSql);
					String orgName_zh_CN="";
					String orgName_en="";
					String orgName_ru="";
					if(list.size()>0){
						Object[] obj=(Object[]) list.get(0);
						orgName_zh_CN=obj[0]+"";
						orgName_en=obj[1]+"";
						orgName_ru=obj[2]+"";
					}
					for(int i=0;i<deviceInfoList.size();i++){
						DeviceInfo deviceInfo=deviceInfoList.get(i);
						if(StringManagerUtils.existOrNot(selectedDeviceId.split(","), deviceInfo.getId()+"", false)){
							deviceInfo.setOrgId(StringManagerUtils.stringToInteger(selectedOrgId));
							deviceInfo.setOrgName_zh_CN(orgName_zh_CN);
							deviceInfo.setOrgName_en(orgName_en);
							deviceInfo.setOrgName_ru(orgName_ru);
							MemoryDataManagerTask.updateDeviceInfo(deviceInfo);
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				
			}
		}
	}
	
	public void changeDeviceType(String selectedDeviceId,String selectedDeviceTypeId,String selectedDeviceTypeName,String deviceTypeStr,String language) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		if(StringManagerUtils.stringToInteger(selectedDeviceTypeId)>0 && StringManagerUtils.isNotNull(selectedDeviceId)){
			int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
			String tableName="tbl_device";
			if(deviceType>=300){
				tableName="tbl_smsdevice";
			}
			String sql = "update "+tableName+" t set t.devicetype="+selectedDeviceTypeId+" where t.id in ("+selectedDeviceId+")";
			this.getBaseDao().updateOrDeleteBySql(sql);
			
			String typeNameSql="select t.name_zh_cn,t.name_en,t.name_ru from TBL_DEVICETYPEINFO t where t.id="+selectedDeviceTypeId;
			List<?> list = this.findCallSql(typeNameSql);
			String deviceTypeName_zh_CN="";
			String deviceTypeName_en="";
			String deviceTypeName_ru="";
			if(list.size()>0){
				Object[] obj=(Object[]) list.get(0);
				deviceTypeName_zh_CN=obj[0]+"";
				deviceTypeName_en=obj[1]+"";
				deviceTypeName_ru=obj[2]+"";
			}
			
			try{
				List<DeviceInfo> deviceInfoList =MemoryDataManagerTask.getDeviceInfo(selectedDeviceId.split(","));
				for(int i=0;i<deviceInfoList.size();i++){
					DeviceInfo deviceInfo=deviceInfoList.get(i);
					if(StringManagerUtils.existOrNot(selectedDeviceId.split(","), deviceInfo.getId()+"", false)){
						deviceInfo.setDeviceType(StringManagerUtils.stringToInteger(selectedDeviceTypeId));
						deviceInfo.setDeviceTypeName_zh_CN(deviceTypeName_zh_CN);
						deviceInfo.setDeviceTypeName_en(deviceTypeName_en);
						deviceInfo.setDeviceTypeName_ru(deviceTypeName_ru);
						MemoryDataManagerTask.updateDeviceInfo(deviceInfo);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				
			}
		}
	}
	
	public String getAcqInstanceCombList(String deviceTypeStr,User user){
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		StringBuffer result_json = new StringBuffer();
		String sql="select t.code,t.name "
				+ " from tbl_protocolinstance t,tbl_acq_unit_conf t2,tbl_protocol t3 "
				+ " where t.unitid=t2.id and t2.protocol=t3.name "
				+ " and t3.language="+(user!=null?user.getLanguage():0)
				+ " order by t.sort";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"totals\":"+(list.size()+1)+",\"list\":[{\"boxkey\":\"\",\"boxval\":\"&nbsp;\"},");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[])list.get(i);
			result_json.append("{\"boxkey\":\"" + obj[0] + "\",");
			result_json.append("\"boxval\":\"" + obj[1] + "\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getDisplayInstanceCombList(String deviceTypeStr,User user){
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		StringBuffer result_json = new StringBuffer();
		String sql="select t.code,t.name "
				+ " from tbl_protocoldisplayinstance t,tbl_display_unit_conf t2,tbl_acq_unit_conf t3,tbl_protocol t4 "
				+ " where t.displayunitid=t2.id and t2.acqunitid=t3.id and t3.protocol=t4.name "
				+ " and t4.language= "+(user!=null?user.getLanguage():0)
				+ " order by t.sort";
		
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"totals\":"+(list.size()+1)+",\"list\":[{\"boxkey\":\"\",\"boxval\":\"&nbsp;\"},");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[])list.get(i);
			result_json.append("{\"boxkey\":\"" + obj[0] + "\",");
			result_json.append("\"boxval\":\"" + obj[1] + "\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getReportInstanceCombList(String deviceTypeStr){
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		StringBuffer result_json = new StringBuffer();
		int protocolType=0;
		if((deviceType>=200&&deviceType<300)||deviceType==1){
			protocolType=1;
		}
		
		String sql="select t.code,t.name from tbl_protocolreportinstance t order by t.sort";
		
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"totals\":"+(list.size()+1)+",\"list\":[{\"boxkey\":\"\",\"boxval\":\"&nbsp;\"},");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[])list.get(i);
			result_json.append("{\"boxkey\":\"" + obj[0] + "\",");
			result_json.append("\"boxval\":\"" + obj[1] + "\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getAlarmInstanceCombList(String deviceTypeStr,User user){
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		StringBuffer result_json = new StringBuffer();
		String sql="select t.code,t.name "
				+ " from tbl_protocolalarminstance t,tbl_alarm_unit_conf t2, tbl_protocol t3 "
				+ " where t.alarmunitid=t2.id and t2.protocol=t3.name "
				+ " and t3.language="+(user!=null?user.getLanguage():0)
				+ " order by t.sort";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"totals\":"+(list.size()+1)+",\"list\":[{\"boxkey\":\"\",\"boxval\":\"&nbsp;\"},");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[])list.get(i);
			result_json.append("{\"boxkey\":\"" + obj[0] + "\",");
			result_json.append("\"boxval\":\"" + obj[1] + "\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getSMSInstanceCombList(){
		StringBuffer result_json = new StringBuffer();
		String sql="select t.code,t.name from tbl_protocolsmsinstance t order by t.sort";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"totals\":"+(list.size()+1)+",\"list\":[{\"boxkey\":\"\",\"boxval\":\"&nbsp;\"},");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[])list.get(i);
			result_json.append("{\"boxkey\":\"" + obj[0] + "\",");
			result_json.append("\"boxval\":\"" + obj[1] + "\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String loadDeviceTypeComboxListFromTab(String language) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		String sql = "SELECT t.id,t.name_"+language+" "
				+ " FROM TBL_DEVICETYPEINFO t "
				+ " START WITH t.parentid = 0 "
				+ " CONNECT BY t.parentid = PRIOR t.id "
				+ " ORDER SIBLINGS BY t.id ";
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(sql);
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\""+MemoryDataManagerTask.getLanguageResourceItem(language,"selectAll")+"\"},");
			String get_key = "";
			String get_val = "";
			if (null != list && list.size() > 0) {
				for (Object o : list) {
					Object[] obj = (Object[]) o;
					get_key = obj[0] + "";
					get_val = (String) obj[1];
					result_json.append("{boxkey:\"" + get_key + "\",");
					result_json.append("boxval:\"" + get_val + "\"},");
				}
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("]}");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}
	
	public String loadDataDictionaryComboxList(String itemCode,String language) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		String sql = "select t.itemvalue,t.itemname from TBL_CODE t where upper(t.itemcode)=upper('"+itemCode+"') order by t.itemvalue ";
		
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(sql);
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\""+MemoryDataManagerTask.getLanguageResourceItem(language,"selectAll")+"\"},");
			String get_key = "";
			String get_val = "";
			if (null != list && list.size() > 0) {
				for (Object o : list) {
					Object[] obj = (Object[]) o;
					get_key = obj[0] + "";
					get_val = (String) obj[1];
					result_json.append("{boxkey:\"" + get_key + "\",");
					result_json.append("boxval:\"" + get_val + "\"},");
				}
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("]}");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}
	
	public String loadCodeComboxList(String itemCode,String language) throws Exception {
		StringBuffer result_json = new StringBuffer();
		Map<String,Code> codeMap=MemoryDataManagerTask.getCodeMap(itemCode.toUpperCase(),language);
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
	
	public String loadCodeComboxListWithoutAll(String itemCode,String values,String language) throws Exception {
		StringBuffer result_json = new StringBuffer();
		Map<String,Code> codeMap=MemoryDataManagerTask.getCodeMap(itemCode.toUpperCase(),values,language);
		result_json.append("{\"totals\":"+codeMap.size()+",\"list\":[");
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
	
	public String saveDeviceData(WellInformationManagerService<?> wellInformationManagerService,WellHandsontableChangedData wellHandsontableChangedData,String orgId,String deviceType,User user) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer collisionbuff = new StringBuffer();
		List<WellHandsontableChangedData.Updatelist> list=getBaseDao().saveDeviceData(wellInformationManagerService,wellHandsontableChangedData,orgId,deviceType,user);
		int successCount=0;
		int collisionCount=0;
		collisionbuff.append("[");
		for(int i=0;i<list.size();i++){
			if(list.get(i).getSaveSign()==-22||list.get(i).getSaveSign()==-33){
				collisionCount++;
				collisionbuff.append("\""+list.get(i).getSaveStr()+"\",");
			}else if(list.get(i).getSaveSign()==0||list.get(i).getSaveSign()==1){
				successCount++;
			}
		}
		if(collisionbuff.toString().endsWith(",")){
			collisionbuff.deleteCharAt(collisionbuff.length() - 1);
		}
		collisionbuff.append("]");
		
		result_json.append("{\"success\":true,\"successCount\":"+successCount+",\"collisionCount\":"+collisionCount+",\"list\":"+collisionbuff+"}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String batchAddDevice(WellInformationManagerService<?> wellInformationManagerService,WellHandsontableChangedData wellHandsontableChangedData,
			String orgId,String deviceType,String isCheckout,String dictDeviceType,User user) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer collisionBuff = new StringBuffer();
		StringBuffer overlayBuff = new StringBuffer();
		
		StringBuffer deviceTypeDropdownData = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer displayInstanceDropdownData = new StringBuffer();
		StringBuffer reportInstanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		StringBuffer applicationScenariosDropdownData = new StringBuffer();
		StringBuffer resultNameDropdownData = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		Map<String,Code> codeMap=MemoryDataManagerTask.getCodeMap("APPLICATIONSCENARIOS",user.getLanguageName());
		Map<String,WorkType> workTypeMap=MemoryDataManagerTask.getWorkTypeMap(user.getLanguageName());
		int collisionCount=0;
		int overlayCount=0;
		int overCount=0;
		String ddicCode="deviceInfo_DeviceBatchAdd";
		String columns=service.showTableHeadersColumns(ddicCode,dictDeviceType,user.getLanguageName());
		List<WellHandsontableChangedData.Updatelist> list=getBaseDao().batchAddDevice(wellInformationManagerService,wellHandsontableChangedData,orgId,deviceType,isCheckout,user);
		
		String deviceTypeSql="select t.name_"+user.getLanguageName()+" from tbl_devicetypeinfo t where t.id in ("+deviceType+") order by t.id";
		String instanceSql="select t.name "
				+ " from tbl_protocolinstance t,tbl_acq_unit_conf t2,tbl_protocol t3 "
				+ " where t.unitid=t2.id and t2.protocol=t3.name "
				+ " and t3.language="+(user!=null?user.getLanguage():0)
				+ " order by t.sort";
		String displayInstanceSql="select t.name "
				+ " from tbl_protocoldisplayinstance t,tbl_display_unit_conf t2,tbl_acq_unit_conf t3,tbl_protocol t4 "
				+ " where t.displayunitid=t2.id and t2.acqunitid=t3.id and t3.protocol=t4.name "
				+ " and t4.language= "+(user!=null?user.getLanguage():0)
				+ " order by t.sort";
		String reportInstanceSql="select t.name from tbl_protocolreportinstance t  order by t.sort";
		String alarmInstanceSql="select t.name "
				+ " from tbl_protocolalarminstance t,tbl_alarm_unit_conf t2, tbl_protocol t3 "
				+ " where t.alarmunitid=t2.id and t2.protocol=t3.name "
				+ " and t3.language="+(user!=null?user.getLanguage():0)
				+ " order by t.sort";
		
		deviceTypeDropdownData.append("[");
		instanceDropdownData.append("[");
		displayInstanceDropdownData.append("[");
		reportInstanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");
		applicationScenariosDropdownData.append("[");
		resultNameDropdownData.append("[");

		List<?> deviceTypeList = this.findCallSql(deviceTypeSql);
		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
		List<?> reportInstanceList = this.findCallSql(reportInstanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		
		if(deviceTypeList.size()>0){
			for(int i=0;i<deviceTypeList.size();i++){
				deviceTypeDropdownData.append("'"+deviceTypeList.get(i)+"',");
			}
			if(deviceTypeDropdownData.toString().endsWith(",")){
				deviceTypeDropdownData.deleteCharAt(deviceTypeDropdownData.length() - 1);
			}
		}
		
		if(instanceList.size()>0){
			instanceDropdownData.append("\"\",");
			for(int i=0;i<instanceList.size();i++){
				instanceDropdownData.append("'"+instanceList.get(i)+"',");
			}
			if(instanceDropdownData.toString().endsWith(",")){
				instanceDropdownData.deleteCharAt(instanceDropdownData.length() - 1);
			}
		}
		
		if(displayInstanceList.size()>0){
			displayInstanceDropdownData.append("\"\",");
			for(int i=0;i<displayInstanceList.size();i++){
				displayInstanceDropdownData.append("'"+displayInstanceList.get(i)+"',");
			}
			if(displayInstanceDropdownData.toString().endsWith(",")){
				displayInstanceDropdownData.deleteCharAt(displayInstanceDropdownData.length() - 1);
			}
		}
		
		if(reportInstanceList.size()>0){
			reportInstanceDropdownData.append("\"\",");
			for(int i=0;i<reportInstanceList.size();i++){
				reportInstanceDropdownData.append("'"+reportInstanceList.get(i)+"',");
			}
			if(reportInstanceDropdownData.toString().endsWith(",")){
				reportInstanceDropdownData.deleteCharAt(reportInstanceDropdownData.length() - 1);
			}
		}
		
		if(alarmInstanceList.size()>0){
			alarmInstanceDropdownData.append("\"\",");
			for(int i=0;i<alarmInstanceList.size();i++){
				alarmInstanceDropdownData.append("'"+alarmInstanceList.get(i)+"',");
			}
			if(alarmInstanceDropdownData.toString().endsWith(",")){
				alarmInstanceDropdownData.deleteCharAt(alarmInstanceDropdownData.length() - 1);
			}
		}
		
		Iterator<Map.Entry<String,Code>> it = codeMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Code> entry = it.next();
			Code c=entry.getValue();
			applicationScenariosDropdownData.append("'"+c.getItemname()+"',");
		}
		if(applicationScenariosDropdownData.toString().endsWith(",")){
			applicationScenariosDropdownData.deleteCharAt(applicationScenariosDropdownData.length() - 1);
		}
		
		if(workTypeMap.size()>0){
			resultNameDropdownData.append("\""+languageResourceMap.get("noIntervention")+"\"");
			
			Iterator<Map.Entry<String, WorkType>> workTypeMapIt = workTypeMap.entrySet().iterator();
			while(workTypeMapIt.hasNext()){
				Map.Entry<String, WorkType> entry = workTypeMapIt.next();
				String resultCode=new String(entry.getKey());
				WorkType w=entry.getValue();
				resultNameDropdownData.append(",\""+w.getResultName()+"\"");
			}
		}
		
		
		deviceTypeDropdownData.append("]");
		instanceDropdownData.append("]");
		displayInstanceDropdownData.append("]");
		reportInstanceDropdownData.append("]");
		alarmInstanceDropdownData.append("]");
		applicationScenariosDropdownData.append("]");
		resultNameDropdownData.append("]");
		collisionBuff.append("[");
		overlayBuff.append("[");
		if(list!=null){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getSaveSign()==-22){//冲突
					collisionCount+=1;
					String deviceTypeName="";
					if("zh_CN".equalsIgnoreCase(user.getLanguageName())){
						deviceTypeName=list.get(i).getDeviceTypeName_zh_CN();
					}else if("en".equalsIgnoreCase(user.getLanguageName())){
						deviceTypeName=list.get(i).getDeviceTypeName_en();
					}else if("ru".equalsIgnoreCase(user.getLanguageName())){
						deviceTypeName=list.get(i).getDeviceTypeName_ru();
					}
					collisionBuff.append("{\"id\":\""+list.get(i).getId()+"\",");
					collisionBuff.append("\"deviceName\":\""+list.get(i).getDeviceName()+"\",");
					collisionBuff.append("\"deviceTypeName\":\""+deviceTypeName+"\",");
					collisionBuff.append("\"instanceName\":\""+list.get(i).getInstanceName()+"\",");
					collisionBuff.append("\"applicationScenariosName\":\""+list.get(i).getApplicationScenariosName()+"\",");
					collisionBuff.append("\"displayInstanceName\":\""+list.get(i).getDisplayInstanceName()+"\",");
					collisionBuff.append("\"reportInstanceName\":\""+list.get(i).getReportInstanceName()+"\",");
					collisionBuff.append("\"alarmInstanceName\":\""+list.get(i).getAlarmInstanceName()+"\",");
					collisionBuff.append("\"tcpType\":\""+list.get(i).getTcpType().replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
					collisionBuff.append("\"signInId\":\""+list.get(i).getSignInId()+"\",");
					collisionBuff.append("\"ipPort\":\""+list.get(i).getIpPort()+"\",");
					collisionBuff.append("\"slave\":\""+list.get(i).getSlave()+"\",");
					collisionBuff.append("\"peakDelay\":\""+list.get(i).getPeakDelay()+"\",");
					collisionBuff.append("\"videoUrl1\":\""+list.get(i).getVideoUrl1()+"\",");
					collisionBuff.append("\"videoKeyName1\":\""+list.get(i).getVideoKeyName1()+"\",");
					collisionBuff.append("\"videoUrl2\":\""+list.get(i).getVideoUrl2()+"\",");
					collisionBuff.append("\"videoKeyName2\":\""+list.get(i).getVideoKeyName2()+"\",");
					collisionBuff.append("\"statusName\":\""+list.get(i).getStatusName()+"\",");
					collisionBuff.append("\"commissioningDate\":\""+list.get(i).getCommissioningDate()+"\",");
					
					collisionBuff.append("\"sortNum\":\""+list.get(i).getSortNum()+"\",");
					
					collisionBuff.append("\"crudeOilDensity\":\""+list.get(i).getCrudeOilDensity()+"\",");
		        	collisionBuff.append("\"waterDensity\":\""+list.get(i).getWaterDensity()+"\",");
		        	collisionBuff.append("\"naturalGasRelativeDensity\":\""+list.get(i).getNaturalGasRelativeDensity()+"\",");
		        	collisionBuff.append("\"saturationPressure\":\""+list.get(i).getSaturationPressure()+"\",");
		        	collisionBuff.append("\"reservoirDepth\":\""+list.get(i).getReservoirDepth()+"\",");
		        	collisionBuff.append("\"reservoirTemperature\":\""+list.get(i).getReservoirTemperature()+"\",");
		        	collisionBuff.append("\"tubingPressure\":\""+list.get(i).getTubingPressure()+"\",");
		        	collisionBuff.append("\"casingPressure\":\""+list.get(i).getCasingPressure()+"\",");
		        	collisionBuff.append("\"wellHeadTemperature\":\""+list.get(i).getWellHeadTemperature()+"\",");
		        	collisionBuff.append("\"waterCut\":\""+list.get(i).getWaterCut()+"\",");
		        	collisionBuff.append("\"productionGasOilRatio\":\""+list.get(i).getProductionGasOilRatio()+"\",");
		        	collisionBuff.append("\"producingfluidLevel\":\""+list.get(i).getProducingfluidLevel()+"\",");
		        	collisionBuff.append("\"pumpSettingDepth\":\""+list.get(i).getPumpSettingDepth()+"\",");
		        	collisionBuff.append("\"pumpType\":\""+list.get(i).getPumpType()+"\",");
		        	collisionBuff.append("\"barrelType\":\""+list.get(i).getBarrelType()+"\",");
		        	collisionBuff.append("\"pumpGrade\":\""+list.get(i).getPumpGrade()+"\",");
		        	collisionBuff.append("\"pumpBoreDiameter\":\""+list.get(i).getPumpBoreDiameter()+"\",");
		        	collisionBuff.append("\"plungerLength\":\""+list.get(i).getPlungerLength()+"\",");
		        	collisionBuff.append("\"tubingStringInsideDiameter\":\""+list.get(i).getTubingStringInsideDiameter()+"\",");
		        	collisionBuff.append("\"casingStringInsideDiameter\":\""+list.get(i).getCasingStringInsideDiameter()+"\",");
		        	collisionBuff.append("\"rodGrade1\":\""+list.get(i).getRodGrade1()+"\",");
		        	collisionBuff.append("\"rodOutsideDiameter1\":\""+list.get(i).getRodOutsideDiameter1()+"\",");
		        	collisionBuff.append("\"rodInsideDiameter1\":\""+list.get(i).getRodInsideDiameter1()+"\",");
		        	collisionBuff.append("\"rodLength1\":\""+list.get(i).getRodLength1()+"\",");
		        	collisionBuff.append("\"rodGrade2\":\""+list.get(i).getRodGrade2()+"\",");
		        	collisionBuff.append("\"rodOutsideDiameter2\":\""+list.get(i).getRodOutsideDiameter2()+"\",");
		        	collisionBuff.append("\"rodInsideDiameter2\":\""+list.get(i).getRodInsideDiameter2()+"\",");
		        	collisionBuff.append("\"rodLength2\":\""+list.get(i).getRodLength2()+"\",");
		        	collisionBuff.append("\"rodGrade3\":\""+list.get(i).getRodGrade3()+"\",");
		        	collisionBuff.append("\"rodOutsideDiameter3\":\""+list.get(i).getRodOutsideDiameter3()+"\",");
		        	collisionBuff.append("\"rodInsideDiameter3\":\""+list.get(i).getRodInsideDiameter3()+"\",");
		        	collisionBuff.append("\"rodLength3\":\""+list.get(i).getRodLength3()+"\",");
		        	collisionBuff.append("\"rodGrade4\":\""+list.get(i).getRodGrade4()+"\",");
		        	collisionBuff.append("\"rodOutsideDiameter4\":\""+list.get(i).getRodOutsideDiameter4()+"\",");
		        	collisionBuff.append("\"rodInsideDiameter4\":\""+list.get(i).getRodInsideDiameter4()+"\",");
		        	collisionBuff.append("\"rodLength4\":\""+list.get(i).getRodLength4()+"\",");
		        	
		        	collisionBuff.append("\"manualInterventionResultName\":\""+list.get(i).getManualInterventionResultName()+"\",");
		        	
		        	collisionBuff.append("\"netGrossRatio\":\""+list.get(i).getNetGrossRatio()+"\",");
		        	collisionBuff.append("\"netGrossValue\":\""+list.get(i).getNetGrossValue()+"\",");
		        	
		        	collisionBuff.append("\"levelCorrectValue\":\""+list.get(i).getLevelCorrectValue()+"\",");
		        	
		        	collisionBuff.append("\"manufacturer\":\""+list.get(i).getManufacturer()+"\",");
		        	collisionBuff.append("\"model\":\""+list.get(i).getModel()+"\",");
		        	collisionBuff.append("\"stroke\":\""+list.get(i).getStroke()+"\",");
		        	collisionBuff.append("\"crankRotationDirection\":\""+list.get(i).getCrankGravityRadius()+"\",");
		        	collisionBuff.append("\"offsetAngleOfCrank\":\""+list.get(i).getOffsetAngleOfCrank()+"\",");
		        	collisionBuff.append("\"crankGravityRadius\":\""+list.get(i).getCrankGravityRadius()+"\",");
		        	collisionBuff.append("\"singleCrankWeight\":\""+list.get(i).getSingleCrankWeight()+"\",");
		        	collisionBuff.append("\"singleCrankPinWeight\":\""+list.get(i).getSingleCrankPinWeight()+"\",");
		        	collisionBuff.append("\"structuralUnbalance\":\""+list.get(i).getStructuralUnbalance()+"\",");
		        	collisionBuff.append("\"balanceWeight\":\""+list.get(i).getBalanceWeight()+"\",");
		        	collisionBuff.append("\"balancePosition\":\""+list.get(i).getBalancePosition()+"\",");
		        	
		        	collisionBuff.append("\"dataInfo\":\""+list.get(i).getSaveStr()+"\"},");
				}else if(list.get(i).getSaveSign()==-33){//覆盖信息
					overlayCount+=1;
					String deviceTypeName="";
					if("zh_CN".equalsIgnoreCase(user.getLanguageName())){
						deviceTypeName=list.get(i).getDeviceTypeName_zh_CN();
					}else if("en".equalsIgnoreCase(user.getLanguageName())){
						deviceTypeName=list.get(i).getDeviceTypeName_en();
					}else if("ru".equalsIgnoreCase(user.getLanguageName())){
						deviceTypeName=list.get(i).getDeviceTypeName_ru();
					}
					overlayBuff.append("{\"id\":\""+list.get(i).getId()+"\",");
					overlayBuff.append("\"deviceName\":\""+list.get(i).getDeviceName()+"\",");
					overlayBuff.append("\"deviceTypeName\":\""+deviceTypeName+"\",");
					overlayBuff.append("\"instanceName\":\""+list.get(i).getInstanceName()+"\",");
					overlayBuff.append("\"applicationScenariosName\":\""+list.get(i).getApplicationScenariosName()+"\",");
					overlayBuff.append("\"displayInstanceName\":\""+list.get(i).getDisplayInstanceName()+"\",");
					overlayBuff.append("\"reportInstanceName\":\""+list.get(i).getReportInstanceName()+"\",");
					overlayBuff.append("\"alarmInstanceName\":\""+list.get(i).getAlarmInstanceName()+"\",");
					overlayBuff.append("\"tcpType\":\""+list.get(i).getTcpType().replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
					overlayBuff.append("\"signInId\":\""+list.get(i).getSignInId()+"\",");
					overlayBuff.append("\"ipPort\":\""+list.get(i).getIpPort()+"\",");
					overlayBuff.append("\"slave\":\""+list.get(i).getSlave()+"\",");
					overlayBuff.append("\"peakDelay\":\""+list.get(i).getPeakDelay()+"\",");
					overlayBuff.append("\"videoUrl1\":\""+list.get(i).getVideoUrl1()+"\",");
					overlayBuff.append("\"videoKeyName1\":\""+list.get(i).getVideoKeyName1()+"\",");
					overlayBuff.append("\"videoUrl2\":\""+list.get(i).getVideoUrl2()+"\",");
					overlayBuff.append("\"videoKeyName2\":\""+list.get(i).getVideoKeyName2()+"\",");
					overlayBuff.append("\"statusName\":\""+list.get(i).getStatusName()+"\",");
					overlayBuff.append("\"commissioningDate\":\""+list.get(i).getCommissioningDate()+"\",");
					overlayBuff.append("\"sortNum\":\""+list.get(i).getSortNum()+"\",");
					
					overlayBuff.append("\"crudeOilDensity\":\""+list.get(i).getCrudeOilDensity()+"\",");
		        	overlayBuff.append("\"waterDensity\":\""+list.get(i).getWaterDensity()+"\",");
		        	overlayBuff.append("\"naturalGasRelativeDensity\":\""+list.get(i).getNaturalGasRelativeDensity()+"\",");
		        	overlayBuff.append("\"saturationPressure\":\""+list.get(i).getSaturationPressure()+"\",");
		        	overlayBuff.append("\"reservoirDepth\":\""+list.get(i).getReservoirDepth()+"\",");
		        	overlayBuff.append("\"reservoirTemperature\":\""+list.get(i).getReservoirTemperature()+"\",");
		        	overlayBuff.append("\"tubingPressure\":\""+list.get(i).getTubingPressure()+"\",");
		        	overlayBuff.append("\"casingPressure\":\""+list.get(i).getCasingPressure()+"\",");
		        	overlayBuff.append("\"wellHeadTemperature\":\""+list.get(i).getWellHeadTemperature()+"\",");
		        	overlayBuff.append("\"waterCut\":\""+list.get(i).getWaterCut()+"\",");
		        	overlayBuff.append("\"productionGasOilRatio\":\""+list.get(i).getProductionGasOilRatio()+"\",");
		        	overlayBuff.append("\"producingfluidLevel\":\""+list.get(i).getProducingfluidLevel()+"\",");
		        	overlayBuff.append("\"pumpSettingDepth\":\""+list.get(i).getPumpSettingDepth()+"\",");
		        	overlayBuff.append("\"pumpType\":\""+list.get(i).getPumpType()+"\",");
		        	overlayBuff.append("\"barrelType\":\""+list.get(i).getBarrelType()+"\",");
		        	overlayBuff.append("\"pumpGrade\":\""+list.get(i).getPumpGrade()+"\",");
		        	overlayBuff.append("\"pumpBoreDiameter\":\""+list.get(i).getPumpBoreDiameter()+"\",");
		        	overlayBuff.append("\"plungerLength\":\""+list.get(i).getPlungerLength()+"\",");
		        	overlayBuff.append("\"tubingStringInsideDiameter\":\""+list.get(i).getTubingStringInsideDiameter()+"\",");
		        	overlayBuff.append("\"casingStringInsideDiameter\":\""+list.get(i).getCasingStringInsideDiameter()+"\",");
		        	overlayBuff.append("\"rodGrade1\":\""+list.get(i).getRodGrade1()+"\",");
		        	overlayBuff.append("\"rodOutsideDiameter1\":\""+list.get(i).getRodOutsideDiameter1()+"\",");
		        	overlayBuff.append("\"rodInsideDiameter1\":\""+list.get(i).getRodInsideDiameter1()+"\",");
		        	overlayBuff.append("\"rodLength1\":\""+list.get(i).getRodLength1()+"\",");
		        	overlayBuff.append("\"rodGrade2\":\""+list.get(i).getRodGrade2()+"\",");
		        	overlayBuff.append("\"rodOutsideDiameter2\":\""+list.get(i).getRodOutsideDiameter2()+"\",");
		        	overlayBuff.append("\"rodInsideDiameter2\":\""+list.get(i).getRodInsideDiameter2()+"\",");
		        	overlayBuff.append("\"rodLength2\":\""+list.get(i).getRodLength2()+"\",");
		        	overlayBuff.append("\"rodGrade3\":\""+list.get(i).getRodGrade3()+"\",");
		        	overlayBuff.append("\"rodOutsideDiameter3\":\""+list.get(i).getRodOutsideDiameter3()+"\",");
		        	overlayBuff.append("\"rodInsideDiameter3\":\""+list.get(i).getRodInsideDiameter3()+"\",");
		        	overlayBuff.append("\"rodLength3\":\""+list.get(i).getRodLength3()+"\",");
		        	overlayBuff.append("\"rodGrade4\":\""+list.get(i).getRodGrade4()+"\",");
		        	overlayBuff.append("\"rodOutsideDiameter4\":\""+list.get(i).getRodOutsideDiameter4()+"\",");
		        	overlayBuff.append("\"rodInsideDiameter4\":\""+list.get(i).getRodInsideDiameter4()+"\",");
		        	overlayBuff.append("\"rodLength4\":\""+list.get(i).getRodLength4()+"\",");
		        	
		        	overlayBuff.append("\"manualInterventionResultName\":\""+list.get(i).getManualInterventionResultName()+"\",");
		        	
		        	overlayBuff.append("\"netGrossRatio\":\""+list.get(i).getNetGrossRatio()+"\",");
		        	overlayBuff.append("\"netGrossValue\":\""+list.get(i).getNetGrossValue()+"\",");
		        	
		        	overlayBuff.append("\"levelCorrectValue\":\""+list.get(i).getLevelCorrectValue()+"\",");
		        	
		        	overlayBuff.append("\"manufacturer\":\""+list.get(i).getManufacturer()+"\",");
		        	overlayBuff.append("\"model\":\""+list.get(i).getModel()+"\",");
		        	overlayBuff.append("\"stroke\":\""+list.get(i).getStroke()+"\",");
		        	overlayBuff.append("\"crankRotationDirection\":\""+list.get(i).getCrankGravityRadius()+"\",");
		        	overlayBuff.append("\"offsetAngleOfCrank\":\""+list.get(i).getOffsetAngleOfCrank()+"\",");
		        	overlayBuff.append("\"crankGravityRadius\":\""+list.get(i).getCrankGravityRadius()+"\",");
		        	overlayBuff.append("\"singleCrankWeight\":\""+list.get(i).getSingleCrankWeight()+"\",");
		        	overlayBuff.append("\"singleCrankPinWeight\":\""+list.get(i).getSingleCrankPinWeight()+"\",");
		        	overlayBuff.append("\"structuralUnbalance\":\""+list.get(i).getStructuralUnbalance()+"\",");
		        	overlayBuff.append("\"balanceWeight\":\""+list.get(i).getBalanceWeight()+"\",");
		        	overlayBuff.append("\"balancePosition\":\""+list.get(i).getBalancePosition()+"\",");
					
					overlayBuff.append("\"dataInfo\":\""+list.get(i).getSaveStr()+"\"},");
				}else if(list.get(i).getSaveSign()==-66){//井数许可超限
					overCount+=1;
				}
			}
			if (collisionBuff.toString().endsWith(",")) {
				collisionBuff.deleteCharAt(collisionBuff.length() - 1);
			}
			if (overlayBuff.toString().endsWith(",")) {
				overlayBuff.deleteCharAt(overlayBuff.length() - 1);
			}
		}
		collisionBuff.append("]");
		overlayBuff.append("]");
		
//		String pumpingModelInfo=this.getPumpingModelInfo();
		result_json.append("{\"success\":true,"
				+ "\"collisionCount\":"+collisionCount+","
				+ "\"overlayCount\":"+overlayCount+","
				+ "\"overCount\":"+overCount+","
				+ "\"deviceTypeDropdownData\":"+deviceTypeDropdownData.toString()+","
				+ "\"instanceDropdownData\":"+instanceDropdownData.toString()+","
				+ "\"displayInstanceDropdownData\":"+displayInstanceDropdownData.toString()+","
				+ "\"reportInstanceDropdownData\":"+reportInstanceDropdownData.toString()+","
				+ "\"alarmInstanceDropdownData\":"+alarmInstanceDropdownData.toString()+","
				+ "\"applicationScenariosDropdownData\":"+applicationScenariosDropdownData.toString()+","
				+ "\"resultNameDropdownData\":"+resultNameDropdownData.toString()+","
//				+ "\"pumpingModelInfo\":"+pumpingModelInfo+","
				+ "\"columns\":"+columns+","
				+ "\"collisionList\":"+collisionBuff+","
				+ "\"overlayList\":"+overlayBuff+"}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public void saveSMSDeviceData(WellHandsontableChangedData wellHandsontableChangedData,String orgId,int deviceType,User user) throws Exception {
		getBaseDao().saveSMSDeviceData(wellHandsontableChangedData,orgId,deviceType,user);
	}
	
	public void doDeviceAdd(DeviceInformation deviceInformation) throws Exception {
		getBaseDao().addObject(deviceInformation);
	}
	
	public void doSRPDeviceAdd(SRPDeviceInformation srpDeviceInformation) throws Exception {
		getBaseDao().addObject(srpDeviceInformation);
	}
	
	public void doPCPDeviceAdd(PcpDeviceInformation pcpDeviceInformation) throws Exception {
		getBaseDao().addObject(pcpDeviceInformation);
	}
	
	public void doSMSDeviceAdd(SmsDeviceInformation smsDeviceInformation) throws Exception {
		getBaseDao().addObject(smsDeviceInformation);
	}
	
	public void doPumpingModelAdd(PumpingModelInformation auxiliaryDeviceInformation) throws Exception {
		getBaseDao().addObject(auxiliaryDeviceInformation);
	}
	
	public void doVideoKeyAdd(VideoKey videoKey) throws Exception {
		getBaseDao().addObject(videoKey);
	}
	
	public void deleteMasterAndAuxiliary(final int masterid) throws Exception {
		final String hql = "DELETE MasterAndAuxiliaryDevice u where u.masterid ="+masterid+"";
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void deleteDeviceAdditionalInfo(final int deviceId,int deviceType) throws Exception {
		String model="DeviceAddInfo";
		final String hql = "DELETE "+model+" u where u.deviceId ="+deviceId+"";
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void deleteAuxiliaryDeviceAdditionalInfo(final int deviceId) throws Exception {
		String model="AuxiliaryDeviceAddInfo";
		final String hql = "DELETE "+model+" u where u.deviceId ="+deviceId+"";
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void grantMasterAuxiliaryDevice(MasterAndAuxiliaryDevice r) throws Exception {
		getBaseDao().saveOrUpdateObject(r);
	}
	
	public void saveProductionData(int deviceId,String deviceProductionData,int deviceCalculateDataType,int applicationScenarios) throws Exception {
		String tableName="tbl_device";
		String time=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String sql = "update "+tableName+" t "
				+ " set t.productiondata='"+deviceProductionData+"',"
				+ " t.calculatetype="+deviceCalculateDataType+","
				+ " t.applicationscenarios="+applicationScenarios+","
				+ " t.productiondataupdatetime=to_date('"+time+"','yyyy-mm-dd hh24:mi:ss') "
				+ " where t.id="+deviceId;
		this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public void saveVideiData(int deviceType,int deviceId,String videoUrl1,String videoKeyName1,String videoUrl2,String videoKeyName2) throws Exception {
		String tableName="tbl_device";
		String videoKeyId1="null",videoKeyId2="null";
		if(StringManagerUtils.isNotNull(videoKeyName1)){
			String sql="select t.id from TBL_VIDEOKEY t where t.account='"+videoKeyName1+"'";
			List<?> list = this.findCallSql(sql);
			if(list.size()>0){
				videoKeyId1=list.get(0).toString();
			}
		}
		
		if(StringManagerUtils.isNotNull(videoKeyName2)){
			String sql="select t.id from TBL_VIDEOKEY t where t.account='"+videoKeyName2+"'";
			List<?> list = this.findCallSql(sql);
			if(list.size()>0){
				videoKeyId2=list.get(0).toString();
			}
		}
		
		
		String sql = "update "+tableName+" t set t.videoUrl1='"+videoUrl1+"',t.videokeyid1="+videoKeyId1+",t.videoUrl2='"+videoUrl2+"',t.videokeyid2="+videoKeyId2+" where t.id="+deviceId;
		this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public void saveSRPPumpingModel(int deviceId,String manufacturer,String model) throws Exception {
		String delSql="delete from TBL_AUXILIARY2MASTER t "
				+ " where t.masterid= "+deviceId
				+ " and t.auxiliaryid in (  select t2.id from tbl_auxiliarydevice t2 where t2.specifictype=1 )";
		String insertSql="insert into TBL_AUXILIARY2MASTER(MASTERID,AUXILIARYID,MATRIX) "
				+ " select "+deviceId+" as MASTERID,t2.id as AUXILIARYID,'0,0,0' as MATRIX"
				+ " from tbl_auxiliarydevice t2 "
				+ " where t2.manufacturer='"+manufacturer+"' and t2.model='"+model+"' and rownum=1";
		
		int r=this.getBaseDao().updateOrDeleteBySql(delSql);
		r=this.getBaseDao().updateOrDeleteBySql(insertSql);
		r+=1;
	}
	
	public void savePumpingInfo(int deviceId,String strokeStr,String balanceInfo) throws Exception {
		if(!StringManagerUtils.isNumber(strokeStr)){
			strokeStr="null";
		}
		String sql = "update tbl_device t set t.stroke="+strokeStr+",t.balanceinfo='"+balanceInfo+"' where t.id="+deviceId;
		this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public void saveFSDiagramConstructionData(int deviceId,String data) throws Exception {
		String sql = "update tbl_device t set t.constructiondata='"+data+"' where t.id="+deviceId;
		this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public void saveDeviceAdditionalInfo(DeviceAddInfo r) throws Exception {
		getBaseDao().saveOrUpdateObject(r);
	}
	
	public void saveAuxiliaryDeviceAddInfo(AuxiliaryDeviceAddInfo r) throws Exception {
		getBaseDao().saveOrUpdateObject(r);
	}
	
	public String savePumpingModelHandsontableData(PumpingModelHandsontableChangedData pumpingModelHandsontableChangedData,String selectedRecordId,String language) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer collisionbuff = new StringBuffer();
		List<PumpingModelHandsontableChangedData.Updatelist> list=getBaseDao().savePumpingModelHandsontableData(pumpingModelHandsontableChangedData,selectedRecordId,language);
		int successCount=0;
		int collisionCount=0;
		collisionbuff.append("[");
		for(int i=0;i<list.size();i++){
			if(list.get(i).getSaveSign()==-22||list.get(i).getSaveSign()==-33){
				collisionCount++;
				collisionbuff.append("\""+list.get(i).getSaveStr()+"\",");
			}else if(list.get(i).getSaveSign()==0||list.get(i).getSaveSign()==1){
				successCount++;
			}
		}
		if(collisionbuff.toString().endsWith(",")){
			collisionbuff.deleteCharAt(collisionbuff.length() - 1);
		}
		collisionbuff.append("]");
		
		result_json.append("{\"success\":true,\"successCount\":"+successCount+",\"collisionCount\":"+collisionCount+",\"list\":"+collisionbuff+"}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String savePumpingPRTFData(String deviceId,String data) throws Exception {
		String result = "{success:true,msg:true}";
		try{
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			type = new TypeToken<PumpingPRTFData.EveryStroke>() {}.getType();
			PumpingPRTFData.EveryStroke everyStroke=gson.fromJson(data, type);
			if(everyStroke!=null){
				String sql = "select t.prtf from tbl_auxiliarydevice t where t.id="+deviceId;
				PumpingPRTFData pumpingPRTFData=null;
				List<String> clobCont=new ArrayList<String>();
				List<?> list = this.findCallSql(sql);
				if(list.size()>0){
					String prtf="";
					Object obj=(Object)list.get(0);
					if(obj!=null){
						SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj);
						CLOB realClob = (CLOB) proxy.getWrappedClob(); 
						prtf=StringManagerUtils.CLOBtoString(realClob);
						type = new TypeToken<PumpingPRTFData>() {}.getType();
						pumpingPRTFData=gson.fromJson(prtf, type);
					}
					if(pumpingPRTFData!=null&&pumpingPRTFData.getList()!=null&&pumpingPRTFData.getList().size()>0){
						boolean exist=false;
						for(int i=0;i<pumpingPRTFData.getList().size();i++){
							if(pumpingPRTFData.getList().get(i).getStroke()==everyStroke.getStroke()){
								pumpingPRTFData.getList().set(i, everyStroke);
								exist=true;
								break;
							}
						}
						if(!exist){
							pumpingPRTFData.getList().add(everyStroke);
						}
					}else{
						pumpingPRTFData=new PumpingPRTFData();
						pumpingPRTFData.setList(new ArrayList<PumpingPRTFData.EveryStroke>());
						pumpingPRTFData.getList().add(everyStroke);
					}
					clobCont.add(gson.toJson(pumpingPRTFData));
					String updatePRTFClobSql="update tbl_auxiliarydevice t set t.prtf=? where t.id="+deviceId;
					getBaseDao().executeSqlUpdateClob(updatePRTFClobSql,clobCont);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "{success:false,msg:false}";
		}
		return result;
	}
	
	public String batchAddPumpingModel(PumpingModelHandsontableChangedData auxiliaryDeviceHandsontableChangedData,String isCheckout,String dictDeviceType,String language) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer overlayBuff = new StringBuffer();
		int overlayCount=0;
		String ddicCode="pumpingDevice_PumpingModelManager";
		String columns=service.showTableHeadersColumns(ddicCode,dictDeviceType,language);
		List<PumpingModelHandsontableChangedData.Updatelist> list=getBaseDao().batchAddPumpingModel(auxiliaryDeviceHandsontableChangedData,StringManagerUtils.stringToInteger(isCheckout),language);
		
		overlayBuff.append("[");
		if(list!=null){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getSaveSign()==-33){//覆盖信息
					overlayCount+=1;
					overlayBuff.append("{\"id\":\""+list.get(i).getId()+"\",");
					overlayBuff.append("\"manufacturer\":\""+list.get(i).getManufacturer()+"\",");
					overlayBuff.append("\"model\":\""+list.get(i).getModel()+"\",");
					overlayBuff.append("\"stroke\":\""+list.get(i).getStroke()+"\",");
					overlayBuff.append("\"crankRotationDirection\":\""+list.get(i).getCrankRotationDirection()+"\",");
					overlayBuff.append("\"offsetAngleOfCrank\":\""+list.get(i).getOffsetAngleOfCrank()+"\",");
					overlayBuff.append("\"crankGravityRadius\":\""+list.get(i).getCrankGravityRadius()+"\",");
					overlayBuff.append("\"singleCrankWeight\":\""+list.get(i).getSingleCrankWeight()+"\",");
					overlayBuff.append("\"singleCrankPinWeight\":\""+list.get(i).getSingleCrankPinWeight()+"\",");
					overlayBuff.append("\"structuralUnbalance\":\""+list.get(i).getStructuralUnbalance()+"\",");
					overlayBuff.append("\"balanceWeight\":\""+list.get(i).getBalanceWeight()+"\",");
					overlayBuff.append("\"dataInfo\":\""+list.get(i).getSaveStr()+"\"},");
				}
			}
			if (overlayBuff.toString().endsWith(",")) {
				overlayBuff.deleteCharAt(overlayBuff.length() - 1);
			}
		}
		overlayBuff.append("]");
		result_json.append("{\"success\":true,\"overlayCount\":"+overlayCount+","+ "\"columns\":"+columns+",\"overlayList\":"+overlayBuff+"}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public void editSRPDeviceName(String oldWellName,String newWellName,String orgid) throws Exception {
		getBaseDao().editSRPDeviceName(oldWellName,newWellName,orgid);
	}
	
	public void editPCPDeviceName(String oldWellName,String newWellName,String orgid) throws Exception {
		getBaseDao().editPCPDeviceName(oldWellName,newWellName,orgid);
	}
	
	public void editSMSDeviceName(String oldWellName,String newWellName,String orgid) throws Exception {
		getBaseDao().editSMSDeviceName(oldWellName,newWellName,orgid);
	}

	public List<T> loadWellInformationID(Class<T> clazz) {
		String queryString = "SELECT u.jlbh,u.jh FROM WellInformation u order by u.jlbh ";
		return getBaseDao().find(queryString);
	}
	
	
	@SuppressWarnings("rawtypes")
	public String getDeviceInfoList(Map map,Page pager,int recordCount,String dictDeviceType,User user) {
		StringBuffer result_json = new StringBuffer();
		String language=user!=null?user.getLanguageName():"";
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Map<String,Code> codeMap=MemoryDataManagerTask.getCodeMap("APPLICATIONSCENARIOS",language);
		StringBuffer deviceTypeDropdownData = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer displayInstanceDropdownData = new StringBuffer();
		StringBuffer reportInstanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		StringBuffer applicationScenariosDropdownData = new StringBuffer();
		String ddicCode="deviceInfo_DeviceManager";
		String tableName="viw_device";
		String deviceName = (String) map.get("deviceName");
		String deviceType=(String) map.get("deviceType");
		String orgId = (String) map.get("orgId");
		
		String columns=service.showTableHeadersColumns(ddicCode,dictDeviceType,language);
		String sql = "select id,orgName_"+language+",deviceName,deviceTypeName_"+language+","
				+ " applicationScenarios,"
				+ " instanceName,displayInstanceName,alarmInstanceName,reportInstanceName,"
				+ " tcptype,signInId,ipport,slave,t.peakdelay,"
				+ " status,decode(t.status,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as statusName,"
				+ " allpath_"+language+","
				+ " to_char(productiondataupdatetime,'yyyy-mm-dd hh24:mi:ss') as productiondataupdatetime,"
				+ " to_char(commissioningdate,'yyyy-mm-dd') as commissioningdate,"
				+ " calculatetype,decode(t.calculatetype,1,'"+languageResourceMap.get("SRPCalculate")+"',2,'"+languageResourceMap.get("PCPCalculate")+"','"+languageResourceMap.get("nothing")+"') as calculateTypeName,"
				+ " sortNum"
				+ " from "+tableName+" t "
				+ " where 1=1";
		if (StringManagerUtils.isNotNull(deviceName)) {
			sql += " and t.devicename like '%" + deviceName+ "%'";
		};
		sql+= " and t.orgid in ("+orgId+" )";
		sql+= " and t.devicetype in ("+deviceType+")";
		sql+= " order by t.sortnum,t.devicename ";
		
		String deviceTypeSql="select t.name_"+language+" from tbl_devicetypeinfo t where t.id in ("+deviceType+") order by t.id";
		String instanceSql="select t.name "
				+ " from tbl_protocolinstance t,tbl_acq_unit_conf t2,tbl_protocol t3 "
				+ " where t.unitid=t2.id and t2.protocol=t3.name "
				+ " and t3.language="+(user!=null?user.getLanguage():0)
				+ " order by t.sort";
		String displayInstanceSql="select t.name "
				+ " from tbl_protocoldisplayinstance t,tbl_display_unit_conf t2,tbl_acq_unit_conf t3,tbl_protocol t4 "
				+ " where t.displayunitid=t2.id and t2.acqunitid=t3.id and t3.protocol=t4.name "
				+ " and t4.language= "+(user!=null?user.getLanguage():0)
				+ " order by t.sort";
		String reportInstanceSql="select t.name from tbl_protocolreportinstance t  order by t.sort";
		String alarmInstanceSql="select t.name "
				+ " from tbl_protocolalarminstance t,tbl_alarm_unit_conf t2, tbl_protocol t3 "
				+ " where t.alarmunitid=t2.id and t2.protocol=t3.name "
				+ " and t3.language="+(user!=null?user.getLanguage():0)
				+ " order by t.sort";
		
		
		deviceTypeDropdownData.append("[");
		instanceDropdownData.append("[");
		displayInstanceDropdownData.append("[");
		reportInstanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");
		applicationScenariosDropdownData.append("[");

		List<?> deviceTypeList = this.findCallSql(deviceTypeSql);
		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
		List<?> reportInstanceList = this.findCallSql(reportInstanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		
		
		if(deviceTypeList.size()>0){
			for(int i=0;i<deviceTypeList.size();i++){
				deviceTypeDropdownData.append("'"+deviceTypeList.get(i)+"',");
			}
			if(deviceTypeDropdownData.toString().endsWith(",")){
				deviceTypeDropdownData.deleteCharAt(deviceTypeDropdownData.length() - 1);
			}
		}
		
		if(instanceList.size()>0){
			instanceDropdownData.append("\"\",");
			for(int i=0;i<instanceList.size();i++){
				instanceDropdownData.append("'"+instanceList.get(i)+"',");
			}
			if(instanceDropdownData.toString().endsWith(",")){
				instanceDropdownData.deleteCharAt(instanceDropdownData.length() - 1);
			}
		}
		
		if(displayInstanceList.size()>0){
			displayInstanceDropdownData.append("\"\",");
			for(int i=0;i<displayInstanceList.size();i++){
				displayInstanceDropdownData.append("'"+displayInstanceList.get(i)+"',");
			}
			if(displayInstanceDropdownData.toString().endsWith(",")){
				displayInstanceDropdownData.deleteCharAt(displayInstanceDropdownData.length() - 1);
			}
		}
		
		if(reportInstanceList.size()>0){
			reportInstanceDropdownData.append("\"\",");
			for(int i=0;i<reportInstanceList.size();i++){
				reportInstanceDropdownData.append("'"+reportInstanceList.get(i)+"',");
			}
			if(reportInstanceDropdownData.toString().endsWith(",")){
				reportInstanceDropdownData.deleteCharAt(reportInstanceDropdownData.length() - 1);
			}
		}
		
		
		if(alarmInstanceList.size()>0){
			alarmInstanceDropdownData.append("\"\",");
			for(int i=0;i<alarmInstanceList.size();i++){
				alarmInstanceDropdownData.append("'"+alarmInstanceList.get(i)+"',");
			}
			if(alarmInstanceDropdownData.toString().endsWith(",")){
				alarmInstanceDropdownData.deleteCharAt(alarmInstanceDropdownData.length() - 1);
			}
		}
		
		
		Iterator<Map.Entry<String,Code>> it = codeMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Code> entry = it.next();
			Code c=entry.getValue();
			applicationScenariosDropdownData.append("'"+c.getItemname()+"',");
		}
		if(applicationScenariosDropdownData.toString().endsWith(",")){
			applicationScenariosDropdownData.deleteCharAt(applicationScenariosDropdownData.length() - 1);
		}
		
		deviceTypeDropdownData.append("]");
		instanceDropdownData.append("]");
		displayInstanceDropdownData.append("]");
		reportInstanceDropdownData.append("]");
		alarmInstanceDropdownData.append("]");
		applicationScenariosDropdownData.append("]");
		
		String json = "";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+","
				+ "\"deviceTypeDropdownData\":"+deviceTypeDropdownData.toString()+","
				+ "\"instanceDropdownData\":"+instanceDropdownData.toString()+","
				+ "\"displayInstanceDropdownData\":"+displayInstanceDropdownData.toString()+","
				+ "\"reportInstanceDropdownData\":"+reportInstanceDropdownData.toString()+","
				+ "\"alarmInstanceDropdownData\":"+alarmInstanceDropdownData.toString()+","
				+ "\"applicationScenariosDropdownData\":"+applicationScenariosDropdownData.toString()+","
				+ "\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"orgName\":\""+obj[1]+"\",");
			result_json.append("\"deviceName\":\""+obj[2]+"\",");
			
			result_json.append("\"deviceTypeName\":\""+obj[3]+"\",");
			
			result_json.append("\"applicationScenarios\":\""+obj[4]+"\",");
			result_json.append("\"applicationScenariosName\":\""+(codeMap.get(obj[4]+"")!=null?codeMap.get(obj[4]+"").getItemname():"")+"\",");
			
			result_json.append("\"instanceName\":\""+obj[5]+"\",");
			result_json.append("\"displayInstanceName\":\""+obj[6]+"\",");
			result_json.append("\"alarmInstanceName\":\""+obj[7]+"\",");
			result_json.append("\"reportInstanceName\":\""+obj[8]+"\",");
			result_json.append("\"tcpType\":\""+(obj[9]+"").replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
			result_json.append("\"signInId\":\""+obj[10]+"\",");
			result_json.append("\"ipPort\":\""+obj[11]+"\",");
			result_json.append("\"slave\":\""+obj[12]+"\",");
			result_json.append("\"peakDelay\":\""+obj[13]+"\",");
			result_json.append("\"status\":\""+obj[14]+"\",");
			result_json.append("\"statusName\":\""+obj[15]+"\",");
			result_json.append("\"allPath\":\""+obj[16]+"\",");
			result_json.append("\"productionDataUpdateTime\":\""+obj[17]+"\",");
			result_json.append("\"commissioningDate\":\""+obj[18]+"\",");
			result_json.append("\"calculateType\":\""+obj[19]+"\",");
			result_json.append("\"calculateTypeName\":\""+obj[20]+"\",");
			result_json.append("\"sortNum\":\""+obj[21]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public boolean exportDeviceInfoData(User user,HttpServletResponse response,String fileName,String title,String head,String field,Map map,Page pager,int recordCount) {
		try{
			StringBuffer result_json = new StringBuffer();
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
			String tableName="viw_device";
			String deviceName = (String) map.get("deviceName");
			String deviceType=(String) map.get("deviceType");
			String dictDeviceType=(String) map.get("dictDeviceType");
			String orgId = (String) map.get("orgId");
			
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			
			List<Object> headRow = new ArrayList<>();
			for(int i=0;i<heads.length;i++){
				headRow.add(heads[i]);
			}
		    List<List<Object>> sheetDataList = new ArrayList<>();
		    sheetDataList.add(headRow);
			
		    String sql = "select id,orgName_"+user.getLanguageName()+",deviceName,deviceTypeName_"+user.getLanguageName()+","
					+ " applicationScenarios,"
					+ " instanceName,displayInstanceName,alarmInstanceName,reportInstanceName,"
					+ " tcptype,signInId,ipport,slave,t.peakdelay,"
					+ " status,decode(t.status,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as statusName,allpath_"+user.getLanguageName()+","
					+ " to_char(productiondataupdatetime,'yyyy-mm-dd hh24:mi:ss') as productiondataupdatetime,"
					+ " sortNum"
					+ " from "+tableName+" t "
					+ " where 1=1";
			if (StringManagerUtils.isNotNull(deviceName)) {
				sql += " and t.devicename like '%" + deviceName+ "%'";
			}
			sql+= " and t.orgid in ("+orgId+" )";
			sql+= " and t.devicetype in ("+deviceType+")";
			sql+= " order by t.sortnum,t.devicename ";
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
				result_json.append("\"orgName\":\""+obj[1]+"\",");
				result_json.append("\"deviceName\":\""+obj[2]+"\",");
				
				result_json.append("\"deviceTypeName\":\""+obj[3]+"\",");
				
				result_json.append("\"applicationScenarios\":\""+obj[4]+"\",");
				result_json.append("\"applicationScenariosName\":\""+MemoryDataManagerTask.getCodeName("APPLICATIONSCENARIOS",obj[4]+"", user.getLanguageName())+"\",");
				
				result_json.append("\"instanceName\":\""+obj[6]+"\",");
				result_json.append("\"displayInstanceName\":\""+obj[7]+"\",");
				result_json.append("\"alarmInstanceName\":\""+obj[8]+"\",");
				result_json.append("\"reportInstanceName\":\""+obj[9]+"\",");
				result_json.append("\"tcpType\":\""+(obj[10]+"").replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
				result_json.append("\"signInId\":\""+obj[11]+"\",");
				result_json.append("\"ipPort\":\""+obj[12]+"\",");
				result_json.append("\"slave\":\""+obj[13]+"\",");
				result_json.append("\"peakDelay\":\""+obj[14]+"\",");
				result_json.append("\"status\":\""+obj[15]+"\",");
				result_json.append("\"statusName\":\""+obj[16]+"\",");
				result_json.append("\"allPath\":\""+obj[17]+"\",");
				result_json.append("\"productionDataUpdateTime\":\""+obj[18]+"\",");
				result_json.append("\"sortNum\":\""+obj[19]+"\"}");
				
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
					saveSystemLog(user,4,languageResourceMap.get("exportFile")+":"+title);
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
	
	@SuppressWarnings("rawtypes")
	public String getSMSDeviceInfoList(Map map,Page pager,int recordCount,String language) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer SMSInstanceDropdownData = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String tableName="viw_smsdevice";
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		String deviceName = (String) map.get("deviceName");
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		if (StringManagerUtils.isNotNull(deviceName)) {
			WellInformation_Str = " and t.deviceName like '%" + deviceName+ "%'";
		}
		
		String columns="["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",\"columnDataSource\":0,\"width\":50 ,\"children\":[] },"
				+ "{ \"header\":\""+languageResourceMap.get("deviceName")+"\",\"dataIndex\":\"deviceName\",\"columnDataSource\":0 ,\"children\":[] },"
				+ "{ \"header\":\""+languageResourceMap.get("SMSInstance")+"\",\"dataIndex\":\"instanceName\",\"columnDataSource\":0,\"width\":120 ,\"children\":[] },"
				+ "{ \"header\":\""+languageResourceMap.get("signInId")+"\",\"dataIndex\":\"signInId\",\"columnDataSource\":0 ,\"children\":[] },"
				+ "{ \"header\":\""+languageResourceMap.get("sortNum")+"\",\"dataIndex\":\"sortNum\",\"columnDataSource\":0 ,\"children\":[] }"
				+ "]";
		String sql = "select id,orgName_"+language+",deviceName,instanceName,signInId,sortNum"
				+ " from "+tableName+" t where 1=1"
				+ WellInformation_Str;
		sql+= " and t.orgid in ("+orgId+" )  ";		
		
		sql+= " order by t.sortnum,t.deviceName ";
		String SMSInstanceSql="select t.name from tbl_protocolsmsinstance t order by t.sort";

		SMSInstanceDropdownData.append("[");
		List<?> SMSInstanceList = this.findCallSql(SMSInstanceSql);
		
		if(SMSInstanceList.size()>0){
			SMSInstanceDropdownData.append("\"\",");
			for(int i=0;i<SMSInstanceList.size();i++){
				SMSInstanceDropdownData.append("'"+SMSInstanceList.get(i)+"',");
			}
			if(SMSInstanceDropdownData.toString().endsWith(",")){
				SMSInstanceDropdownData.deleteCharAt(SMSInstanceDropdownData.length() - 1);
			}
		}
		
		SMSInstanceDropdownData.append("]");
		
		String json = "";
		
		List<?> list = this.findCallSql(sql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+","
				+ "\"SMSInstanceDropdownData\":"+SMSInstanceDropdownData.toString()+","
				+ "\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"orgName\":\""+obj[1]+"\",");
			result_json.append("\"deviceName\":\""+obj[2]+"\",");
			result_json.append("\"instanceName\":\""+obj[3]+"\",");
			result_json.append("\"signInId\":\""+obj[4]+"\",");
			result_json.append("\"sortNum\":\""+obj[5]+"\"},");
		}
//		for(int i=1;i<=recordCount-list.size();i++){
//			result_json.append("{\"jlbh\":\"-99999\",\"id\":\"-99999\"},");
//		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	@SuppressWarnings("rawtypes")
	public String getSMSDeviceInfoExportData(Map map,Page pager,int recordCount,String language) {
		StringBuffer result_json = new StringBuffer();
		String tableName="viw_smsdevice";
		String wellInformationName = (String) map.get("wellInformationName");
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		if (StringManagerUtils.isNotNull(wellInformationName)) {
			WellInformation_Str = " and t.wellname like '%" + wellInformationName+ "%'";
		}
		String sql = "select id,orgName_"+language+",wellName,instanceName,signInId,sortNum"
				+ " from "+tableName+" t where 1=1"
				+ WellInformation_Str;
		sql+= " and t.orgid in ("+orgId+" )  ";		
		
		sql+= " order by t.sortnum,t.wellname ";
		
		String json = "";
		
		List<?> list = this.findCallSql(sql);
		
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"orgName\":\""+obj[1]+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"instanceName\":\""+obj[3]+"\",");
			result_json.append("\"signInId\":\""+obj[4]+"\",");
			result_json.append("\"sortNum\":\""+obj[5]+"\"},");
		}
//		for(int i=1;i<=recordCount-list.size();i++){
//			result_json.append("{\"jlbh\":\"-99999\",\"id\":\"-99999\"},");
//		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public boolean exportSMSDeviceInfoData(User user,HttpServletResponse response,String fileName,String title,String head,String field,Map map,Page pager,int recordCount) {
		try{
			StringBuffer result_json = new StringBuffer();
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			String tableName="viw_smsdevice";
			String wellInformationName = (String) map.get("wellInformationName");
			String orgId = (String) map.get("orgId");
			String WellInformation_Str = "";
			if (StringManagerUtils.isNotNull(wellInformationName)) {
				WellInformation_Str = " and t.wellname like '%" + wellInformationName+ "%'";
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
			
			String sql = "select id,orgName_"+user.getLanguageName()+",wellName,instanceName,signInId,sortNum"
					+ " from "+tableName+" t where 1=1"
					+ WellInformation_Str;
			sql+= " and t.orgid in ("+orgId+" )  ";		
			
			sql+= " order by t.sortnum,t.wellname ";
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
				result_json.append("\"orgName\":\""+obj[1]+"\",");
				result_json.append("\"wellName\":\""+obj[2]+"\",");
				result_json.append("\"instanceName\":\""+obj[3]+"\",");
				result_json.append("\"signInId\":\""+obj[4]+"\",");
				result_json.append("\"sortNum\":\""+obj[5]+"\"}");
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
					saveSystemLog(user,4,languageResourceMap.get("exportFile")+":"+title);
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
	
	@SuppressWarnings("rawtypes")
	public String getPumpingPRTFData(String deviceId,String stroke) throws SQLException, IOException {
		StringBuffer result_json = new StringBuffer();
		StringBuffer strokeListBuff = new StringBuffer();
		String sql = "select t2.itemvalue,t.prtf from tbl_auxiliarydevice t left outer join tbl_auxiliarydeviceaddinfo t2 on  t.id=t2.deviceid and lower(t2.itemcode)='stroke' where t.id="+deviceId;
		String json = "";
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"totalRoot\":[");
		strokeListBuff.append("[");
		if(list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			String prtf="";
			String strokeListData=obj[0].toString();
			if(StringManagerUtils.isNotNull(strokeListData)){
				String[] strokeArr=strokeListData.split(",");
				for(int i=0;i<strokeArr.length;i++){
					strokeListBuff.append("["+strokeArr[i]+","+strokeArr[i]+"],");
					if((!StringManagerUtils.isNotNull(stroke)) && i==0){
						stroke=strokeArr[i];
					}
				}
			}
			
			if(obj[1]!=null){
				SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[1]);
				CLOB realClob = (CLOB) proxy.getWrappedClob(); 
				prtf=StringManagerUtils.CLOBtoString(realClob);
			}
			if(!StringManagerUtils.isNotNull(prtf)){
				prtf="";
			}
			type = new TypeToken<PumpingPRTFData>() {}.getType();
			PumpingPRTFData pumpingPRTFData=gson.fromJson(prtf, type);
			if(pumpingPRTFData!=null && pumpingPRTFData.getList()!=null && pumpingPRTFData.getList().size()>0){
				for(int i=0;i<pumpingPRTFData.getList().size();i++){
					if(StringManagerUtils.isNotNull(stroke)){
						if(pumpingPRTFData.getList().get(i).getStroke()==StringManagerUtils.stringToFloat(stroke)){
							for(int j=0;j<pumpingPRTFData.getList().get(i).getPRTF().size();j++){
								result_json.append("{\"CrankAngle\":\""+pumpingPRTFData.getList().get(i).getPRTF().get(j).getCrankAngle()+"\",");
								result_json.append("\"PR\":\""+pumpingPRTFData.getList().get(i).getPRTF().get(j).getPR()+"\",");
								result_json.append("\"TF\":\""+pumpingPRTFData.getList().get(i).getPRTF().get(j).getTF()+"\"},");
							}
							break;
						}
					}else{
						if(i==0){
							for(int j=0;j<pumpingPRTFData.getList().get(i).getPRTF().size();j++){
								result_json.append("{\"CrankAngle\":\""+pumpingPRTFData.getList().get(i).getPRTF().get(j).getCrankAngle()+"\",");
								result_json.append("\"PR\":\""+pumpingPRTFData.getList().get(i).getPRTF().get(j).getPR()+"\",");
								result_json.append("\"TF\":\""+pumpingPRTFData.getList().get(i).getPRTF().get(j).getTF()+"\"},");
							}
							break;
						}
					}
				}
			}
		}
		if(strokeListBuff.toString().endsWith(",")){
			strokeListBuff.deleteCharAt(strokeListBuff.length() - 1);
		}
		strokeListBuff.append("]");
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("],");
		result_json.append("\"strokeList\":"+strokeListBuff.toString());
		result_json.append("}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	@SuppressWarnings("rawtypes")
	public String getDevicePumpingPRTFData(String manufacturer,String model,String stroke) throws SQLException, IOException {
		StringBuffer result_json = new StringBuffer();
		StringBuffer strokeListBuff = new StringBuffer();
		String sql = "select t.id,t.prtf "
				+ " from tbl_auxiliarydevice t "
				+ " where t.id=(select t3.id from tbl_auxiliarydevice t3 where t3.manufacturer='"+manufacturer+"' and t3.model='"+model+"' and rownum=1) ";
		String json = "";
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"totalRoot\":[");
		strokeListBuff.append("[");
		if(list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			
			String prtf="";
			if(obj[1]!=null){
				SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[1]);
				CLOB realClob = (CLOB) proxy.getWrappedClob(); 
				prtf=StringManagerUtils.CLOBtoString(realClob);
			}
			if(!StringManagerUtils.isNotNull(prtf)){
				prtf="";
			}
			type = new TypeToken<PumpingPRTFData>() {}.getType();
			PumpingPRTFData pumpingPRTFData=gson.fromJson(prtf, type);
			if(pumpingPRTFData!=null && pumpingPRTFData.getList()!=null && pumpingPRTFData.getList().size()>0){
				for(int i=0;i<pumpingPRTFData.getList().size();i++){
					if(StringManagerUtils.isNotNull(stroke)){
						if(pumpingPRTFData.getList().get(i).getStroke()==StringManagerUtils.stringToFloat(stroke)){
							for(int j=0;j<pumpingPRTFData.getList().get(i).getPRTF().size();j++){
								result_json.append("{\"CrankAngle\":\""+pumpingPRTFData.getList().get(i).getPRTF().get(j).getCrankAngle()+"\",");
								result_json.append("\"PR\":\""+pumpingPRTFData.getList().get(i).getPRTF().get(j).getPR()+"\",");
								result_json.append("\"TF\":\""+pumpingPRTFData.getList().get(i).getPRTF().get(j).getTF()+"\"},");
							}
							break;
						}
					}
				}
			}
		}
		if(strokeListBuff.toString().endsWith(",")){
			strokeListBuff.deleteCharAt(strokeListBuff.length() - 1);
		}
		strokeListBuff.append("]");
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("],");
		result_json.append("\"strokeList\":"+strokeListBuff.toString());
		result_json.append("}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	@SuppressWarnings("rawtypes")
	public String doPumpingModelShow(String manufacturer,String model,String dictDeviceType,String language) throws SQLException, IOException {
		StringBuffer result_json = new StringBuffer();
		String ddicCode="pumpingDevice_PumpingModelManager";
		String columns=service.showTableHeadersColumns(ddicCode,dictDeviceType,language);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String sql = "select t.id,t.manufacturer,t.model,t.stroke,decode(t.crankrotationdirection,'Anticlockwise','"+languageResourceMap.get("anticlockwise")+"','Clockwise','"+languageResourceMap.get("clockwise")+"','') as crankrotationdirection,"
				+ " t.offsetangleofcrank,t.crankgravityradius,t.singlecrankweight,t.singlecrankpinweight,"
				+ " t.structuralunbalance,t.balanceweight"
				+ " from tbl_pumpingmodel t where 1=1";
		if (StringManagerUtils.isNotNull(manufacturer)) {
			sql += " and t.manufacturer = '"+manufacturer+"'";
		}
		if (StringManagerUtils.isNotNull(model)) {
			sql += " and t.model = '"+model+"'";
		}
		sql+= " order by t.id,t.manufacturer,t.model";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"manufacturer\":\""+obj[1]+"\",");
			result_json.append("\"model\":\""+obj[2]+"\",");
			result_json.append("\"stroke\":\""+obj[3]+"\",");
			result_json.append("\"crankRotationDirection\":\""+obj[4]+"\",");
			result_json.append("\"offsetAngleOfCrank\":\""+obj[5]+"\",");
			result_json.append("\"crankGravityRadius\":\""+obj[6]+"\",");
			result_json.append("\"singleCrankWeight\":\""+obj[7]+"\",");
			result_json.append("\"singleCrankPinWeight\":\""+obj[8]+"\",");
			result_json.append("\"structuralUnbalance\":\""+obj[9]+"\",");
			result_json.append("\"balanceWeight\":\""+obj[10]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	@SuppressWarnings("rawtypes")
	public String getBatchAddPumpingModelTableInfo(int recordCount,String dictDeviceType,String language) {
		StringBuffer result_json = new StringBuffer();
		String ddicCode="pumpingDevice_PumpingModelManager";
		String columns=service.showTableHeadersColumns(ddicCode,dictDeviceType,language);
		String json = "";
		result_json.append("{\"success\":true,\"totalCount\":"+recordCount+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<recordCount;i++){
			result_json.append("{},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	@SuppressWarnings("rawtypes")
	public String getPumpingModelExportData(Map map,Page pager,String deviceType,int recordCount,String language) {
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String sql = "select t.id,t.manufacturer,t.model,t.stroke,decode(t.crankrotationdirection,'Anticlockwise','"+languageResourceMap.get("anticlockwise")+"','Clockwise','"+languageResourceMap.get("clockwise")+"','') as crankrotationdirection,"
				+ " t.offsetangleofcrank,t.crankgravityradius,t.singlecrankweight,t.singlecrankpinweight,t.structuralunbalance,t.balanceweight "
				+ " from tbl_pumpingmodel t"
				+ " order by t.id,t.manufacturer,t.model";
		List<?> list = this.findCallSql(sql);
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"manufacturer\":\""+obj[1]+"\",");
			result_json.append("\"model\":\""+obj[2]+"\",");
			result_json.append("\"stroke\":\""+obj[3]+"\",");
			result_json.append("\"crankRotationDirection\":\""+obj[4]+"\",");
			result_json.append("\"offsetAngleOfCrank\":\""+obj[5]+"\",");
			result_json.append("\"crankGravityRadius\":\""+obj[6]+"\",");
			result_json.append("\"singleCrankWeight\":\""+obj[7]+"\",");
			result_json.append("\"singleCrankPinWeight\":\""+obj[8]+"\",");
			result_json.append("\"structuralUnbalance\":\""+obj[9]+"\",");
			result_json.append("\"balanceWeight\":\""+obj[10]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public boolean exportPumpingModelData(User user,HttpServletResponse response,String fileName,String title,String head,String field,String manufacturer,String model) {
		try{
			StringBuffer result_json = new StringBuffer();
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
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
			String sql = "select t.id,t.manufacturer,t.model,t.stroke,decode(t.crankrotationdirection,'Anticlockwise','"+languageResourceMap.get("anticlockwise")+"','Clockwise','"+languageResourceMap.get("clockwise")+"','') as crankrotationdirection,"
					+ " t.offsetangleofcrank,t.crankgravityradius,t.singlecrankweight,t.singlecrankpinweight,t.structuralunbalance,t.balanceweight "
					+ " from tbl_pumpingmodel t where 1=1";
			if (StringManagerUtils.isNotNull(manufacturer)) {
				sql += " and t.manufacturer = '"+manufacturer+"'";
			}
			if (StringManagerUtils.isNotNull(model)) {
				sql += " and t.model = '"+model+"'";
			}
			sql+= " order by t.id,t.manufacturer,t.model";
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
				result_json.append("\"manufacturer\":\""+obj[1]+"\",");
				result_json.append("\"model\":\""+obj[2]+"\",");
				result_json.append("\"stroke\":\""+obj[3]+"\",");
				result_json.append("\"crankRotationDirection\":\""+obj[4]+"\",");
				result_json.append("\"offsetAngleOfCrank\":\""+obj[5]+"\",");
				result_json.append("\"crankGravityRadius\":\""+obj[6]+"\",");
				result_json.append("\"singleCrankWeight\":\""+obj[7]+"\",");
				result_json.append("\"singleCrankPinWeight\":\""+obj[8]+"\",");
				result_json.append("\"structuralUnbalance\":\""+obj[9]+"\",");
				result_json.append("\"balanceWeight\":\""+obj[10]+"\"}");
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
					saveSystemLog(user,4,languageResourceMap.get("exportFile")+":"+title);
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
	
	public String getPumpingModelList(String deviceId,String deviceType,String language) {
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"name\",width:120 ,children:[] },"
				+ "{ \"header\":\"规格型号\",\"dataIndex\":\"model\",width:80 ,children:[] }"
				+ "]";
		String sql = "select t3.id,t3.manufacturer,t3.model "
				+ " from tbl_device t,tbl_auxiliary2master t2,tbl_auxiliarydevice t3 "
				+ " where t.id=t2.masterid and t2.auxiliaryid=t3.id and t3.specifictype=1 "
				+ " and t.id="+deviceId;
		String json = "";
		List<?> list = this.findCallSql(sql);
		List<?> detailsList=null;
		
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String stroke="";
			String balanceWeight="";
			String realId=obj[0]+"";
			
			String detailsSql="select t.itemname,t.itemvalue from tbl_auxiliarydeviceaddinfo t where t.deviceid="+realId+" and t.itemname in ('"+languageResourceMap.get("stroke")+"','"+languageResourceMap.get("balanceWeight")+"')";
			detailsList=this.findCallSql(detailsSql);
			for(int j=0;j<detailsList.size();j++ ){
				Object[] detailsObj = (Object[]) detailsList.get(j);
				if(languageResourceMap.get("stroke").equalsIgnoreCase(detailsObj[0]+"")){
					stroke=detailsObj[1]+"";
				}else if(languageResourceMap.get("balanceWeight").equalsIgnoreCase(detailsObj[0]+"")){
					balanceWeight=detailsObj[1]+"";
				}
			}
			
			boolean checked=true;
			result_json.append("{\"checked\":"+checked+",");
			result_json.append("\"id\":\""+(i+1)+"\",");
			result_json.append("\"realId\":\""+realId+"\",");
			result_json.append("\"manufacturer\":\""+obj[1]+"\",");
			result_json.append("\"model\":\""+obj[2]+"\",");
			result_json.append("\"stroke\":["+stroke+"],");
			result_json.append("\"balanceWeight\":["+balanceWeight+"]},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	@SuppressWarnings("unused")
	public String getDevicePumpingInfo(String deviceId,String deviceType,String auxiliaryDeviceType,String language) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer allPumpingUnitBuff = new StringBuffer();
		StringBuffer allPumpingUnitManufacturerBuff = new StringBuffer();
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"itemValue1\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("variable")+"\",\"dataIndex\":\"itemValue2\",width:80 ,children:[] }"
				+ "]";
		String sql = "select t.stroke,t.balanceinfo from tbl_device t where t.id="+deviceId;
		String auxiliaryDeviceSql="select t3.name,t3.manufacturer,t3.model "
				+ " from tbl_device t,tbl_auxiliary2master t2,tbl_auxiliarydevice t3 "
				+ " where t.id=t2.masterid and t2.auxiliaryid=t3.id "
				+ " and t3.specifictype=1"
				+ " and t.id="+deviceId;
		
		String auxiliaryDeviceDetailsSql="select t4.itemname,t4.itemvalue,t4.itemcode "
				+ " from tbl_device t,tbl_auxiliary2master t2,tbl_auxiliarydevice t3,tbl_auxiliarydeviceaddinfo t4 "
				+ " where t.id=t2.masterid and t2.auxiliaryid=t3.id and t3.id=t4.deviceid "
				+ " and t3.specifictype=1"
				+ " and lower(t4.itemcode) in('stroke','balanceweight') "
				+ " and t.id="+deviceId;
		
		String allPumpingUnitSql="select t.id,t.manufacturer,t.model,v.itemvalue as stroke,v2.itemvalue as balanceweight "
				+ " from TBL_AUXILIARYDEVICE t "
				+ " left outer join (select t2.deviceid,t2.itemvalue from tbl_auxiliarydeviceaddinfo t2 where lower(t2.itemcode)='stroke') v on v.deviceid=t.id"
				+ " left outer join (select t3.deviceid,t3.itemvalue from tbl_auxiliarydeviceaddinfo t3 where lower(t3.itemcode)='balanceweight') v2 on v2.deviceid=t.id"
				+ " where t.specifictype=1 "
				+ " and t.type="+auxiliaryDeviceType
				+ " order by t.manufacturer,t.sort,t.model";
		Map<String,List<String>> allPumpingUnitMap=new LinkedHashMap<>();
		String json = "";
		List<?> list = this.findCallSql(sql);
		List<?> auxiliaryDeviceList = this.findCallSql(auxiliaryDeviceSql);
		List<?> auxiliaryDeviceDetailsList = this.findCallSql(auxiliaryDeviceDetailsSql);
		List<?> allPumpingUnitList = this.findCallSql(allPumpingUnitSql);
		
		for(int i=0;i<allPumpingUnitList.size();i++){
			Object[] obj = (Object[]) allPumpingUnitList.get(i);
			String pumpingUnitId=obj[0]+"";
			String manufacturer=obj[1]+"";
			String model=obj[2]+"";
			String stroke=obj[3]+"";
			String balanceWeight=obj[4]+"";
			if(!allPumpingUnitMap.containsKey(manufacturer)){
				allPumpingUnitMap.put(manufacturer, new ArrayList<>());
			}
			allPumpingUnitMap.get(manufacturer).add("{\"deviceId\":"+pumpingUnitId+",\"model\":\""+model+"\",\"stroke\":["+stroke+"],\"balanceWeight\":["+balanceWeight+"]}");
		}
		
		allPumpingUnitBuff.append("[");
		allPumpingUnitManufacturerBuff.append("[");
		for (Map.Entry<String,List<String>> entry : allPumpingUnitMap.entrySet()) {
		    String manufacturer = entry.getKey();
		    List<String> modelList = entry.getValue();
		    allPumpingUnitBuff.append("{\"manufacturer\":\""+manufacturer+"\",\"modelList\":[");
		    allPumpingUnitManufacturerBuff.append("\""+manufacturer+"\",");
		    for(String model:modelList){
		    	allPumpingUnitBuff.append(""+model+",");
		    }
		    if (allPumpingUnitBuff.toString().endsWith(",")) {
		    	allPumpingUnitBuff.deleteCharAt(allPumpingUnitBuff.length() - 1);
			}
		    allPumpingUnitBuff.append("]},");
		}
		if (allPumpingUnitBuff.toString().endsWith(",")) {
	    	allPumpingUnitBuff.deleteCharAt(allPumpingUnitBuff.length() - 1);
		}
		if (allPumpingUnitManufacturerBuff.toString().endsWith(",")) {
			allPumpingUnitManufacturerBuff.deleteCharAt(allPumpingUnitManufacturerBuff.length() - 1);
		}
		allPumpingUnitBuff.append("]");
		allPumpingUnitManufacturerBuff.append("]");
		
		String pumpingUnitList=allPumpingUnitBuff.toString();
		String manufacturerList=allPumpingUnitManufacturerBuff.toString();
		
		String auxiliaryDeviceName="";
		String model="";
		String manufacturer="";
		String stroke="",balanceInfo="";
		String strokeArrStr="";
		String balanceInfoArrStr="";
		String position1="",weight1="",position2="",weight2="",position3="",weight3="",position4="",weight4="",position5="",weight5="",position6="",weight6="",position7="",weight7="",position8="",weight8="";
		
		
		for(int i=0;i<auxiliaryDeviceDetailsList.size();i++){
			Object[] obj = (Object[]) auxiliaryDeviceDetailsList.get(i);
			String itemName=obj[0]+"";
			String itemValue=obj[1]+"";
			String itemCode=obj[2]+"";
			if("stroke".equalsIgnoreCase(itemCode)){
				strokeArrStr=itemValue;
			}else if("balanceWeight".equalsIgnoreCase(itemCode)){
				balanceInfoArrStr=itemValue;
			}
		}
		
		result_json.append("{\"success\":true,\"totalCount\":9,\"strokeArrStr\":["+strokeArrStr+"],\"balanceInfoArrStr\":["+balanceInfoArrStr+"],\"pumpingUnitList\":"+pumpingUnitList+",\"columns\":"+columns+",\"totalRoot\":[");
		
		if(auxiliaryDeviceList.size()>0){
			Object[] obj = (Object[]) auxiliaryDeviceList.get(0);
			auxiliaryDeviceName=obj[0]+"";
			manufacturer=obj[1]+"";
			model=obj[2]+"";
		}
		
		if(list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			stroke=obj[0]+"";
			balanceInfo=obj[1]+"";
		}
		type = new TypeToken<SRPCalculateRequestData.Balance>() {}.getType();
		SRPCalculateRequestData.Balance balance=gson.fromJson(balanceInfo, type);
		if(balance!=null&&balance.getEveryBalance()!=null&&balance.getEveryBalance().size()>0){
			if(balance.getEveryBalance().size()>0){
				weight1=balance.getEveryBalance().get(0).getWeight()+"";
				position1=balance.getEveryBalance().get(0).getPosition()+"";
			}
			if(balance.getEveryBalance().size()>1){
				weight2=balance.getEveryBalance().get(1).getWeight()+"";
				position2=balance.getEveryBalance().get(1).getPosition()+"";
			}
			if(balance.getEveryBalance().size()>2){
				weight3=balance.getEveryBalance().get(2).getWeight()+"";
				position3=balance.getEveryBalance().get(2).getPosition()+"";
			}
			if(balance.getEveryBalance().size()>3){
				weight4=balance.getEveryBalance().get(3).getWeight()+"";
				position4=balance.getEveryBalance().get(3).getPosition()+"";
			}
			if(balance.getEveryBalance().size()>4){
				weight5=balance.getEveryBalance().get(4).getWeight()+"";
				position5=balance.getEveryBalance().get(4).getPosition()+"";
			}
			if(balance.getEveryBalance().size()>5){
				weight6=balance.getEveryBalance().get(5).getWeight()+"";
				position6=balance.getEveryBalance().get(5).getPosition()+"";
			}
			if(balance.getEveryBalance().size()>6){
				weight7=balance.getEveryBalance().get(6).getWeight()+"";
				position7=balance.getEveryBalance().get(6).getPosition()+"";
			}
			if(balance.getEveryBalance().size()>7){
				weight8=balance.getEveryBalance().get(7).getWeight()+"";
				position8=balance.getEveryBalance().get(7).getPosition()+"";
			}
		}
		
		result_json.append("{\"id\":1,\"itemValue1\":\""+languageResourceMap.get("name")+"\",\"itemValue2\":\""+auxiliaryDeviceName+"\",\"itemCode\":\"deviceName\"},");
		result_json.append("{\"id\":2,\"itemValue1\":\""+languageResourceMap.get("manufacturer")+"\",\"itemValue2\":\""+manufacturer+"\",\"itemCode\":\"manufacturer\"},");
		result_json.append("{\"id\":3,\"itemValue1\":\""+languageResourceMap.get("model")+"\",\"itemValue2\":\""+model+"\",\"itemCode\":\"model\"},");
		
		result_json.append("{\"id\":4,\"itemValue1\":\""+languageResourceMap.get("stroke")+"(m)\",\"itemValue2\":\""+stroke+"\",\"itemCode\":\"stroke\"},");
		result_json.append("{\"id\":5,\"itemValue1\":\""+languageResourceMap.get("balanceInfo")+"\",\"itemValue2\":\"\",\"itemCode\":\"balanceInfo\"},");
		result_json.append("{\"id\":6,\"itemValue1\":\""+languageResourceMap.get("position")+"(m)\",\"itemValue2\":\""+languageResourceMap.get("weight")+"(kN)\",\"itemCode\":\"positionAndWeight\"},");
		
		
		result_json.append("{\"id\":7,\"itemValue1\":\""+position1+"\",\"itemValue2\":\""+weight1+"\",\"itemCode\":\"positionAndWeight1\"},");
		result_json.append("{\"id\":8,\"itemValue1\":\""+position2+"\",\"itemValue2\":\""+weight2+"\",\"itemCode\":\"positionAndWeight2\"},");
		result_json.append("{\"id\":9,\"itemValue1\":\""+position3+"\",\"itemValue2\":\""+weight3+"\",\"itemCode\":\"positionAndWeight3\"},");
		result_json.append("{\"id\":10,\"itemValue1\":\""+position4+"\",\"itemValue2\":\""+weight4+"\",\"itemCode\":\"positionAndWeight4\"},");
		result_json.append("{\"id\":11,\"itemValue1\":\""+position5+"\",\"itemValue2\":\""+weight5+"\",\"itemCode\":\"positionAndWeight5\"},");
		result_json.append("{\"id\":12,\"itemValue1\":\""+position6+"\",\"itemValue2\":\""+weight6+"\",\"itemCode\":\"positionAndWeight6\"},");
		result_json.append("{\"id\":13,\"itemValue1\":\""+position7+"\",\"itemValue2\":\""+weight7+"\",\"itemCode\":\"positionAndWeight7\"},");
		result_json.append("{\"id\":14,\"itemValue1\":\""+position8+"\",\"itemValue2\":\""+weight8+"\",\"itemCode\":\"positionAndWeight8\"}");
		
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getDeviceProductionDataInfo(String deviceId,String deviceType,String deviceCalculateDataType,String language) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer resultNameBuff = new StringBuffer();
		StringBuffer FESdiagramSrcBuff = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Map<String,WorkType> workTypeMap=MemoryDataManagerTask.getWorkTypeMap(language);
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		try{
			String columns = "["
					+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"itemName\",width:120 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("variable")+"\",\"dataIndex\":\"itemValue\",width:120 ,children:[] }"
					+ "]";
			String deviceTableName="tbl_device";
			String sql = "select t.productiondata,to_char(t.productiondataupdatetime,'yyyy-mm-dd hh24:mi:ss'),t.applicationscenarios "
					+ " from "+deviceTableName+" t "
					+ " where t.id="+deviceId;
			
			List<?> list = this.findCallSql(sql);
			result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");
			resultNameBuff.append("[");
			FESdiagramSrcBuff.append("[");
			String applicationScenarios="";
			if(list.size()>0){
				Object[] obj = (Object[]) list.get(0);
				String productionData=obj[0]+"";
				String updateTime=obj[1]+"";
				applicationScenarios=obj[2]+"";
				
				if(!"all".equalsIgnoreCase(Config.getInstance().configFile.getAp().getOthers().getScene())){
					if("cbm".equalsIgnoreCase(Config.getInstance().configFile.getAp().getOthers().getScene())){
						applicationScenarios="0";
					}else{
						applicationScenarios="1";
					}
				}
				
				String reservoirDepthKey="reservoirDepth";
				String reservoirTemperatureKey="reservoirTemperature";
				String tubingPressurekey="tubingPressure";
				if("0".equalsIgnoreCase(applicationScenarios)){
					reservoirDepthKey=reservoirDepthKey+"_cbm";
					reservoirTemperatureKey=reservoirTemperatureKey+"_cbm";
					tubingPressurekey=tubingPressurekey+"_cbm";
				}
				
				if(StringManagerUtils.stringToInteger(deviceCalculateDataType)==1){
					resultNameBuff.append("\""+languageResourceMap.get("noIntervention")+"\"");
					
					FESdiagramSrcBuff.append("\""+MemoryDataManagerTask.getCodeName("FESDIAGRAMSRC", "0", language)+"\",\""+MemoryDataManagerTask.getCodeName("FESDIAGRAMSRC", "1", language)+"\"");
					
//					List<?> resultList = this.findCallSql(resultSql);
					
					Iterator<Map.Entry<String, WorkType>> it = workTypeMap.entrySet().iterator();
					while(it.hasNext()){
						Map.Entry<String, WorkType> entry = it.next();
						String resultCode=new String(entry.getKey());
						WorkType w=entry.getValue();
						resultNameBuff.append(",\""+w.getResultName()+"\"");
					}
					type = new TypeToken<SRPProductionData>() {}.getType();
					SRPProductionData srpProductionData=gson.fromJson(productionData, type);
					if(srpProductionData!=null){
						int FESDiagramSrc=srpProductionData.getFESDiagram()!=null?srpProductionData.getFESDiagram().getSrc():0;
						
						result_json.append("{\"id\":1,\"itemName\":\""+languageResourceMap.get("crudeOilDensity")+"(g/cm^3)\",\"itemCode\":\"crudeOilDensity\",\"itemValue\":\""+(srpProductionData.getFluidPVT()!=null?srpProductionData.getFluidPVT().getCrudeOilDensity():"")+"\"},");
						result_json.append("{\"id\":2,\"itemName\":\""+languageResourceMap.get("waterDensity")+"(g/cm^3)\",\"itemCode\":\"waterDensity\",\"itemValue\":\""+(srpProductionData.getFluidPVT()!=null?srpProductionData.getFluidPVT().getWaterDensity():"")+"\"},");
						result_json.append("{\"id\":3,\"itemName\":\""+languageResourceMap.get("naturalGasRelativeDensity")+"\",\"itemCode\":\"naturalGasRelativeDensity\",\"itemValue\":\""+(srpProductionData.getFluidPVT()!=null?srpProductionData.getFluidPVT().getNaturalGasRelativeDensity():"")+"\"},");
						result_json.append("{\"id\":4,\"itemName\":\""+languageResourceMap.get("saturationPressure")+"(MPa)\",\"itemCode\":\"saturationPressure\",\"itemValue\":\""+(srpProductionData.getFluidPVT()!=null?srpProductionData.getFluidPVT().getSaturationPressure():"")+"\"},");
						
						result_json.append("{\"id\":5,\"itemName\":\""+languageResourceMap.get(reservoirDepthKey)+"(m)\",\"itemCode\":\"reservoirDepth\",\"itemValue\":\""+(srpProductionData.getReservoir()!=null?srpProductionData.getReservoir().getDepth():"")+"\"},");
						result_json.append("{\"id\":6,\"itemName\":\""+languageResourceMap.get(reservoirTemperatureKey)+"(℃)\",\"itemCode\":\"reservoirTemperature\",\"itemValue\":\""+(srpProductionData.getReservoir()!=null?srpProductionData.getReservoir().getTemperature():"")+"\"},");
						
						result_json.append("{\"id\":7,\"itemName\":\""+languageResourceMap.get(tubingPressurekey)+"(MPa)\",\"itemCode\":\"tubingPressure\",\"itemValue\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getProduction().getTubingPressure():"")+"\"},");
						result_json.append("{\"id\":8,\"itemName\":\""+languageResourceMap.get("casingPressure")+"(MPa)\",\"itemCode\":\"casingPressure\",\"itemValue\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getProduction().getCasingPressure():"")+"\"},");
						result_json.append("{\"id\":9,\"itemName\":\""+languageResourceMap.get("wellHeadTemperature")+"(℃)\",\"itemCode\":\"wellHeadTemperature\",\"itemValue\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getProduction().getWellHeadTemperature():"")+"\"},");
						result_json.append("{\"id\":10,\"itemName\":\""+languageResourceMap.get("waterCut")+"(%)\",\"itemCode\":\"waterCut\",\"itemValue\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getProduction().getWaterCut():"")+"\"},");
						result_json.append("{\"id\":11,\"itemName\":\""+languageResourceMap.get("productionGasOilRatio")+"(m^3/t)\",\"itemCode\":\"productionGasOilRatio\",\"itemValue\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getProduction().getProductionGasOilRatio():"")+"\"},");
						result_json.append("{\"id\":12,\"itemName\":\""+languageResourceMap.get("producingfluidLevel")+"(m)\",\"itemCode\":\"producingfluidLevel\",\"itemValue\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getProduction().getProducingfluidLevel():"")+"\"},");
						result_json.append("{\"id\":13,\"itemName\":\""+languageResourceMap.get("pumpSettingDepth")+"(m)\",\"itemCode\":\"pumpSettingDepth\",\"itemValue\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getProduction().getPumpSettingDepth():"")+"\"},");
						
						String barrelType="";
						if(srpProductionData.getPump()!=null&&srpProductionData.getPump().getBarrelType()!=null){
							if("L".equalsIgnoreCase(srpProductionData.getPump().getBarrelType())){
								barrelType=languageResourceMap.get("barrelType_L");
							}else if("H".equalsIgnoreCase(srpProductionData.getPump().getBarrelType())){
								barrelType=languageResourceMap.get("barrelType_H");
							}
						}
						result_json.append("{\"id\":14,\"itemName\":\""+languageResourceMap.get("barrelType")+"\",\"itemCode\":\"barrelType\",\"itemValue\":\""+barrelType+"\"},");
						result_json.append("{\"id\":15,\"itemName\":\""+languageResourceMap.get("pumpGrade")+"\",\"itemCode\":\"pumpGrade\",\"itemValue\":\""+(srpProductionData.getPump()!=null?srpProductionData.getPump().getPumpGrade():"")+"\"},");
						result_json.append("{\"id\":16,\"itemName\":\""+languageResourceMap.get("pumpBoreDiameter")+"(mm)\",\"itemCode\":\"pumpBoreDiameter\",\"itemValue\":\""+(srpProductionData.getPump()!=null?(srpProductionData.getPump().getPumpBoreDiameter()*1000):"")+"\"},");
						result_json.append("{\"id\":17,\"itemName\":\""+languageResourceMap.get("plungerLength")+"(m)\",\"itemCode\":\"plungerLength\",\"itemValue\":\""+(srpProductionData.getPump()!=null?srpProductionData.getPump().getPlungerLength():"")+"\"},");
						
						result_json.append("{\"id\":18,\"itemName\":\""+languageResourceMap.get("tubingStringInsideDiameter")+"(mm)\",\"itemCode\":\"tubingStringInsideDiameter\",\"itemValue\":\""+(srpProductionData.getTubingString()!=null&&srpProductionData.getTubingString().getEveryTubing()!=null&&srpProductionData.getTubingString().getEveryTubing().size()>0?(srpProductionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000):"")+"\"},");
						result_json.append("{\"id\":19,\"itemName\":\""+languageResourceMap.get("casingStringOutsideDiameter")+"(mm)\",\"itemCode\":\"casingStringOutsideDiameter\",\"itemValue\":\""+(srpProductionData.getCasingString()!=null&&srpProductionData.getCasingString().getEveryCasing()!=null&&srpProductionData.getCasingString().getEveryCasing().size()>0?(srpProductionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000):"")+"\"},");
						
						String rodType1="",rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="";
						String rodType2="",rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="";
						String rodType3="",rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="";
						String rodType4="",rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="";
						if(srpProductionData.getRodString()!=null&&srpProductionData.getRodString().getEveryRod()!=null&&srpProductionData.getRodString().getEveryRod().size()>0){
							if(srpProductionData.getRodString().getEveryRod().size()>0){
								if(srpProductionData.getRodString().getEveryRod().get(0).getType()==1){
									rodType1=languageResourceMap.get("rodStringTypeValue1");
								}else if(srpProductionData.getRodString().getEveryRod().get(0).getType()==2){
									rodType1=languageResourceMap.get("rodStringTypeValue2");
								}else if(srpProductionData.getRodString().getEveryRod().get(0).getType()==3){
									rodType1=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType1=languageResourceMap.get("rodStringTypeValue1");
								}
								rodGrade1=srpProductionData.getRodString().getEveryRod().get(0).getGrade();
								rodOutsideDiameter1=srpProductionData.getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
								rodInsideDiameter1=srpProductionData.getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
								rodLength1=srpProductionData.getRodString().getEveryRod().get(0).getLength()+"";
							}
							if(srpProductionData.getRodString().getEveryRod().size()>1){
								if(srpProductionData.getRodString().getEveryRod().get(1).getType()==1){
									rodType2=languageResourceMap.get("rodStringTypeValue1");
								}else if(srpProductionData.getRodString().getEveryRod().get(1).getType()==2){
									rodType2=languageResourceMap.get("rodStringTypeValue2");
								}else if(srpProductionData.getRodString().getEveryRod().get(1).getType()==3){
									rodType2=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType2=languageResourceMap.get("rodStringTypeValue1");
								}
								rodGrade2=srpProductionData.getRodString().getEveryRod().get(1).getGrade();
								rodOutsideDiameter2=srpProductionData.getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
								rodInsideDiameter2=srpProductionData.getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
								rodLength2=srpProductionData.getRodString().getEveryRod().get(1).getLength()+"";
							}
							if(srpProductionData.getRodString().getEveryRod().size()>2){
								if(srpProductionData.getRodString().getEveryRod().get(2).getType()==1){
									rodType3=languageResourceMap.get("rodStringTypeValue1");
								}else if(srpProductionData.getRodString().getEveryRod().get(2).getType()==2){
									rodType3=languageResourceMap.get("rodStringTypeValue2");
								}else if(srpProductionData.getRodString().getEveryRod().get(2).getType()==3){
									rodType3=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType3=languageResourceMap.get("rodStringTypeValue1");
								}
								rodGrade3=srpProductionData.getRodString().getEveryRod().get(2).getGrade();
								rodOutsideDiameter3=srpProductionData.getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
								rodInsideDiameter3=srpProductionData.getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
								rodLength3=srpProductionData.getRodString().getEveryRod().get(2).getLength()+"";
							}
							if(srpProductionData.getRodString().getEveryRod().size()>3){
								if(srpProductionData.getRodString().getEveryRod().get(3).getType()==1){
									rodType4=languageResourceMap.get("rodStringTypeValue1");
								}else if(srpProductionData.getRodString().getEveryRod().get(3).getType()==2){
									rodType4=languageResourceMap.get("rodStringTypeValue2");
								}else if(srpProductionData.getRodString().getEveryRod().get(3).getType()==3){
									rodType4=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType4=languageResourceMap.get("rodStringTypeValue4");
								}
								rodGrade4=srpProductionData.getRodString().getEveryRod().get(3).getGrade();
								rodOutsideDiameter4=srpProductionData.getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
								rodInsideDiameter4=srpProductionData.getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
								rodLength4=srpProductionData.getRodString().getEveryRod().get(3).getLength()+"";
							}
						}
						result_json.append("{\"id\":20,\"itemName\":\""+languageResourceMap.get("rodStringType1")+"\",\"itemCode\":\"rodStringType1\",\"itemValue\":\""+rodType1+"\"},");
						result_json.append("{\"id\":21,\"itemName\":\""+languageResourceMap.get("rodGrade1")+"\",\"itemCode\":\"rodGrade1\",\"itemValue\":\""+rodGrade1+"\"},");
						result_json.append("{\"id\":22,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter1")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter1\",\"itemValue\":\""+rodOutsideDiameter1+"\"},");
						result_json.append("{\"id\":23,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter1")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter1\",\"itemValue\":\""+rodInsideDiameter1+"\"},");
						result_json.append("{\"id\":24,\"itemName\":\""+languageResourceMap.get("rodStringLength1")+"(m)\",\"itemCode\":\"rodStringLength1\",\"itemValue\":\""+rodLength1+"\"},");
						
						result_json.append("{\"id\":25,\"itemName\":\""+languageResourceMap.get("rodStringType2")+"\",\"itemCode\":\"rodStringType2\",\"itemValue\":\""+rodType2+"\"},");
						result_json.append("{\"id\":26,\"itemName\":\""+languageResourceMap.get("rodGrade2")+"\",\"itemCode\":\"rodGrade2\",\"itemValue\":\""+rodGrade2+"\"},");
						result_json.append("{\"id\":27,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter2")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter2\",\"itemValue\":\""+rodOutsideDiameter2+"\"},");
						result_json.append("{\"id\":28,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter2")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter2\",\"itemValue\":\""+rodInsideDiameter2+"\"},");
						result_json.append("{\"id\":29,\"itemName\":\""+languageResourceMap.get("rodStringLength2")+"(m)\",\"itemCode\":\"rodStringLength2\",\"itemValue\":\""+rodLength2+"\"},");
						
						result_json.append("{\"id\":30,\"itemName\":\""+languageResourceMap.get("rodStringType3")+"\",\"itemCode\":\"rodStringType3\",\"itemValue\":\""+rodType3+"\"},");
						result_json.append("{\"id\":31,\"itemName\":\""+languageResourceMap.get("rodGrade3")+"\",\"itemCode\":\"rodGrade3\",\"itemValue\":\""+rodGrade3+"\"},");
						result_json.append("{\"id\":32,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter3")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter3\",\"itemValue\":\""+rodOutsideDiameter3+"\"},");
						result_json.append("{\"id\":33,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter3")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter3\",\"itemValue\":\""+rodInsideDiameter3+"\"},");
						result_json.append("{\"id\":34,\"itemName\":\""+languageResourceMap.get("rodStringLength3")+"(m)\",\"itemCode\":\"rodStringLength3\",\"itemValue\":\""+rodLength3+"\"},");
						
						result_json.append("{\"id\":35,\"itemName\":\""+languageResourceMap.get("rodStringType4")+"\",\"itemCode\":\"rodStringType4\",\"itemValue\":\""+rodType4+"\"},");
						result_json.append("{\"id\":36,\"itemName\":\""+languageResourceMap.get("rodGrade4")+"\",\"itemCode\":\"rodGrade4\",\"itemValue\":\""+rodGrade4+"\"},");
						result_json.append("{\"id\":37,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter4")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter4\",\"itemValue\":\""+rodOutsideDiameter4+"\"},");
						result_json.append("{\"id\":38,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter4")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter4\",\"itemValue\":\""+rodInsideDiameter4+"\"},");
						result_json.append("{\"id\":39,\"itemName\":\""+languageResourceMap.get("rodStringLength4")+"(m)\",\"itemCode\":\"rodStringLength4\",\"itemValue\":\""+rodLength4+"\"},");
						
						
						String manualInterventionName=languageResourceMap.get("noIntervention");
						if(srpProductionData.getManualIntervention()!=null && srpProductionData.getManualIntervention().getCode()>0){
							WorkType workType=MemoryDataManagerTask.getWorkTypeByCode(srpProductionData.getManualIntervention().getCode()+"",language);
							if(workType!=null){
								manualInterventionName=workType.getResultName();
							}
						}
						result_json.append("{\"id\":40,\"itemName\":\""+languageResourceMap.get("manualInterventionCode")+"\",\"itemCode\":\"manualInterventionCode\",\"itemValue\":\""+manualInterventionName+"\"},");
						result_json.append("{\"id\":41,\"itemName\":\""+languageResourceMap.get("netGrossRatio")+"\",\"itemCode\":\"netGrossRatio\",\"itemValue\":\""+(srpProductionData.getManualIntervention()!=null?srpProductionData.getManualIntervention().getNetGrossRatio():"")+"\"},");
						result_json.append("{\"id\":42,\"itemName\":\""+languageResourceMap.get("netGrossValue")+"(m^3/d)\",\"itemCode\":\"netGrossValue\",\"itemValue\":\""+(srpProductionData.getManualIntervention()!=null?srpProductionData.getManualIntervention().getNetGrossValue():"")+"\"},");
						result_json.append("{\"id\":43,\"itemName\":\""+languageResourceMap.get("levelCorrectValue")+"(MPa)\",\"itemCode\":\"levelCorrectValue\",\"itemValue\":\""+(srpProductionData.getProduction()!=null?srpProductionData.getManualIntervention().getLevelCorrectValue():"")+"\"},");
						
						result_json.append("{\"id\":44,\"itemName\":\""+languageResourceMap.get("FESDiagramSrc")+"\",\"itemCode\":\"FESDiagramSrc\",\"itemValue\":\""+MemoryDataManagerTask.getCodeName("FESDIAGRAMSRC", FESDiagramSrc+"", language)+"\"}");
						
					}else{
						result_json.append("{\"id\":1,\"itemName\":\""+languageResourceMap.get("crudeOilDensity")+"(g/cm^3)\",\"itemCode\":\"crudeOilDensity\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":2,\"itemName\":\""+languageResourceMap.get("waterDensity")+"(g/cm^3)\",\"itemCode\":\"waterDensity\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":3,\"itemName\":\""+languageResourceMap.get("naturalGasRelativeDensity")+"\",\"itemCode\":\"naturalGasRelativeDensity\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":4,\"itemName\":\""+languageResourceMap.get("saturationPressure")+"(MPa)\",\"itemCode\":\"saturationPressure\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":5,\"itemName\":\""+languageResourceMap.get(reservoirDepthKey)+"(m)\",\"itemCode\":\"reservoirDepth\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":6,\"itemName\":\""+languageResourceMap.get(reservoirTemperatureKey)+"(℃)\",\"itemCode\":\"reservoirTemperature\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":7,\"itemName\":\""+languageResourceMap.get(tubingPressurekey)+"(MPa)\",\"itemCode\":\"tubingPressure\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":8,\"itemName\":\""+languageResourceMap.get("casingPressure")+"(MPa)\",\"itemCode\":\"casingPressure\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":9,\"itemName\":\""+languageResourceMap.get("wellHeadTemperature")+"(℃)\",\"itemCode\":\"wellHeadTemperature\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":10,\"itemName\":\""+languageResourceMap.get("waterCut")+"(%)\",\"itemCode\":\"waterCut\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":11,\"itemName\":\""+languageResourceMap.get("productionGasOilRatio")+"(m^3/t)\",\"itemCode\":\"productionGasOilRatio\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":12,\"itemName\":\""+languageResourceMap.get("producingfluidLevel")+"(m)\",\"itemCode\":\"producingfluidLevel\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":13,\"itemName\":\""+languageResourceMap.get("pumpSettingDepth")+"(m)\",\"itemCode\":\"pumpSettingDepth\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":14,\"itemName\":\""+languageResourceMap.get("barrelType")+"\",\"itemCode\":\"barrelType\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":15,\"itemName\":\""+languageResourceMap.get("pumpGrade")+"\",\"itemCode\":\"pumpGrade\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":16,\"itemName\":\""+languageResourceMap.get("pumpBoreDiameter")+"(mm)\",\"itemCode\":\"pumpBoreDiameter\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":17,\"itemName\":\""+languageResourceMap.get("plungerLength")+"(m)\",\"itemCode\":\"plungerLength\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":18,\"itemName\":\""+languageResourceMap.get("tubingStringInsideDiameter")+"(mm)\",\"itemCode\":\"tubingStringInsideDiameter\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":19,\"itemName\":\""+languageResourceMap.get("casingStringOutsideDiameter")+"(mm)\",\"itemCode\":\"casingStringOutsideDiameter\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":20,\"itemName\":\""+languageResourceMap.get("rodStringType1")+"\",\"itemCode\":\"rodStringType1\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":21,\"itemName\":\""+languageResourceMap.get("rodGrade1")+"\",\"itemCode\":\"rodGrade1\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":22,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter1")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter1\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":23,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter1")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter1\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":24,\"itemName\":\""+languageResourceMap.get("rodStringLength1")+"(m)\",\"itemCode\":\"rodStringLength1\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":25,\"itemName\":\""+languageResourceMap.get("rodStringType2")+"\",\"itemCode\":\"rodStringType2\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":26,\"itemName\":\""+languageResourceMap.get("rodGrade2")+"\",\"itemCode\":\"rodGrade2\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":27,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter2")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter2\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":28,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter2")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter2\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":29,\"itemName\":\""+languageResourceMap.get("rodStringLength2")+"(m)\",\"itemCode\":\"rodStringLength2\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":30,\"itemName\":\""+languageResourceMap.get("rodStringType3")+"\",\"itemCode\":\"rodStringType3\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":31,\"itemName\":\""+languageResourceMap.get("rodGrade3")+"\",\"itemCode\":\"rodGrade3\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":32,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter3")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter3\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":33,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter3")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter3\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":34,\"itemName\":\""+languageResourceMap.get("rodStringLength3")+"(m)\",\"itemCode\":\"rodStringLength3\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":35,\"itemName\":\""+languageResourceMap.get("rodStringType4")+"\",\"itemCode\":\"rodStringType4\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":36,\"itemName\":\""+languageResourceMap.get("rodGrade4")+"\",\"itemCode\":\"rodGrade4\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":37,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter4")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter4\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":38,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter4")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter4\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":39,\"itemName\":\""+languageResourceMap.get("rodStringLength4")+"(m)\",\"itemCode\":\"rodStringLength4\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":40,\"itemName\":\""+languageResourceMap.get("manualInterventionCode")+"\",\"itemCode\":\"manualInterventionCode\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":41,\"itemName\":\""+languageResourceMap.get("netGrossRatio")+"\",\"itemCode\":\"netGrossRatio\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":42,\"itemName\":\""+languageResourceMap.get("netGrossValue")+"(m^3/d)\",\"itemCode\":\"netGrossValue\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":43,\"itemName\":\""+languageResourceMap.get("levelCorrectValue")+"(MPa)\",\"itemCode\":\"levelCorrectValue\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":44,\"itemName\":\""+languageResourceMap.get("FESDiagramSrc")+"\",\"itemCode\":\"FESDiagramSrc\",\"itemValue\":\"\"}");
					}
				}else if(StringManagerUtils.stringToInteger(deviceCalculateDataType)==2){
					type = new TypeToken<PCPProductionData>() {}.getType();
					PCPProductionData pcpProductionData=gson.fromJson(productionData, type);
					if(pcpProductionData!=null){
						result_json.append("{\"id\":1,\"itemName\":\""+languageResourceMap.get("crudeOilDensity")+"(g/cm^3)\",\"itemCode\":\"crudeOilDensity\",\"itemValue\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getCrudeOilDensity():"")+"\"},");
						result_json.append("{\"id\":2,\"itemName\":\""+languageResourceMap.get("waterDensity")+"(g/cm^3)\",\"itemCode\":\"waterDensity\",\"itemValue\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getWaterDensity():"")+"\"},");
						result_json.append("{\"id\":3,\"itemName\":\""+languageResourceMap.get("naturalGasRelativeDensity")+"\",\"itemCode\":\"naturalGasRelativeDensity\",\"itemValue\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getNaturalGasRelativeDensity():"")+"\"},");
						result_json.append("{\"id\":4,\"itemName\":\""+languageResourceMap.get("saturationPressure")+"(MPa)\",\"itemCode\":\"saturationPressure\",\"itemValue\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getSaturationPressure():"")+"\"},");
						
						result_json.append("{\"id\":5,\"itemName\":\""+languageResourceMap.get(reservoirDepthKey)+"(m)\",\"itemCode\":\"reservoirDepth\",\"itemValue\":\""+(pcpProductionData.getReservoir()!=null?pcpProductionData.getReservoir().getDepth():"")+"\"},");
						result_json.append("{\"id\":6,\"itemName\":\""+languageResourceMap.get(reservoirTemperatureKey)+"(℃)\",\"itemCode\":\"reservoirTemperature\",\"itemValue\":\""+(pcpProductionData.getReservoir()!=null?pcpProductionData.getReservoir().getTemperature():"")+"\"},");
						
						result_json.append("{\"id\":7,\"itemName\":\""+languageResourceMap.get(tubingPressurekey)+"(MPa)\",\"itemCode\":\"tubingPressure\",\"itemValue\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getTubingPressure():"")+"\"},");
						result_json.append("{\"id\":8,\"itemName\":\""+languageResourceMap.get("casingPressure")+"(MPa)\",\"itemCode\":\"casingPressure\",\"itemValue\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getCasingPressure():"")+"\"},");
						result_json.append("{\"id\":9,\"itemName\":\""+languageResourceMap.get("wellHeadTemperature")+"(℃)\",\"itemCode\":\"wellHeadTemperature\",\"itemValue\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getWellHeadTemperature():"")+"\"},");
						result_json.append("{\"id\":10,\"itemName\":\""+languageResourceMap.get("waterCut")+"(%)\",\"itemCode\":\"waterCut\",\"itemValue\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getWaterCut():"")+"\"},");
						result_json.append("{\"id\":11,\"itemName\":\""+languageResourceMap.get("productionGasOilRatio")+"(m^3/t)\",\"itemCode\":\"productionGasOilRatio\",\"itemValue\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getProductionGasOilRatio():"")+"\"},");
						result_json.append("{\"id\":12,\"itemName\":\""+languageResourceMap.get("producingfluidLevel")+"(m)\",\"itemCode\":\"producingfluidLevel\",\"itemValue\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getProducingfluidLevel():"")+"\"},");
						result_json.append("{\"id\":13,\"itemName\":\""+languageResourceMap.get("pumpSettingDepth")+"(m)\",\"itemCode\":\"pumpSettingDepth\",\"itemValue\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getPumpSettingDepth():"")+"\"},");
						
						result_json.append("{\"id\":14,\"itemName\":\""+languageResourceMap.get("BarrelLength")+"(m)\",\"itemCode\":\"BarrelLength\",\"itemValue\":\""+(pcpProductionData.getPump()!=null?pcpProductionData.getPump().getBarrelLength():"")+"\"},");
						result_json.append("{\"id\":15,\"itemName\":\""+languageResourceMap.get("BarrelSeries")+"\",\"itemCode\":\"BarrelSeries\",\"itemValue\":\""+(pcpProductionData.getPump()!=null?pcpProductionData.getPump().getBarrelSeries():"")+"\"},");
						result_json.append("{\"id\":16,\"itemName\":\""+languageResourceMap.get("RotorDiameter")+"(mm)\",\"itemCode\":\"RotorDiameter\",\"itemValue\":\""+(pcpProductionData.getPump()!=null?(pcpProductionData.getPump().getRotorDiameter()*1000):"")+"\"},");
						result_json.append("{\"id\":17,\"itemName\":\""+languageResourceMap.get("QPR")+"(ml/r)\",\"itemCode\":\"QPR\",\"itemValue\":\""+(pcpProductionData.getPump()!=null?(pcpProductionData.getPump().getQPR()*1000*1000):"")+"\"},");
						
						result_json.append("{\"id\":18,\"itemName\":\""+languageResourceMap.get("tubingStringInsideDiameter")+"(mm)\",\"itemCode\":\"tubingStringInsideDiameter\",\"itemValue\":\""+(pcpProductionData.getTubingString()!=null&&pcpProductionData.getTubingString().getEveryTubing()!=null&&pcpProductionData.getTubingString().getEveryTubing().size()>0?(pcpProductionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000):"")+"\"},");
						result_json.append("{\"id\":19,\"itemName\":\""+languageResourceMap.get("casingStringOutsideDiameter")+"(mm)\",\"itemCode\":\"casingStringOutsideDiameter\",\"itemValue\":\""+(pcpProductionData.getCasingString()!=null&&pcpProductionData.getCasingString().getEveryCasing()!=null&&pcpProductionData.getCasingString().getEveryCasing().size()>0?(pcpProductionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000):"")+"\"},");
						
						String rodType1="",rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="";
						String rodType2="",rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="";
						String rodType3="",rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="";
						String rodType4="",rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="";
						if(pcpProductionData.getRodString()!=null&&pcpProductionData.getRodString().getEveryRod()!=null&&pcpProductionData.getRodString().getEveryRod().size()>0){
							if(pcpProductionData.getRodString().getEveryRod().size()>0){
								if(pcpProductionData.getRodString().getEveryRod().get(0).getType()==1){
									rodType1=languageResourceMap.get("rodStringTypeValue1");
								}else if(pcpProductionData.getRodString().getEveryRod().get(0).getType()==2){
									rodType1=languageResourceMap.get("rodStringTypeValue2");
								}else if(pcpProductionData.getRodString().getEveryRod().get(0).getType()==3){
									rodType1=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType1=languageResourceMap.get("rodStringTypeValue1");
								}
								rodGrade1=pcpProductionData.getRodString().getEveryRod().get(0).getGrade();
								rodOutsideDiameter1=pcpProductionData.getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
								rodInsideDiameter1=pcpProductionData.getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
								rodLength1=pcpProductionData.getRodString().getEveryRod().get(0).getLength()+"";
							}
							if(pcpProductionData.getRodString().getEveryRod().size()>1){
								if(pcpProductionData.getRodString().getEveryRod().get(1).getType()==1){
									rodType2=languageResourceMap.get("rodStringTypeValue1");
								}else if(pcpProductionData.getRodString().getEveryRod().get(1).getType()==2){
									rodType2=languageResourceMap.get("rodStringTypeValue2");
								}else if(pcpProductionData.getRodString().getEveryRod().get(1).getType()==3){
									rodType2=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType2=languageResourceMap.get("rodStringTypeValue1");
								}
								rodGrade2=pcpProductionData.getRodString().getEveryRod().get(1).getGrade();
								rodOutsideDiameter2=pcpProductionData.getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
								rodInsideDiameter2=pcpProductionData.getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
								rodLength2=pcpProductionData.getRodString().getEveryRod().get(1).getLength()+"";
							}
							if(pcpProductionData.getRodString().getEveryRod().size()>2){
								if(pcpProductionData.getRodString().getEveryRod().get(2).getType()==1){
									rodType3=languageResourceMap.get("rodStringTypeValue1");
								}else if(pcpProductionData.getRodString().getEveryRod().get(2).getType()==2){
									rodType3=languageResourceMap.get("rodStringTypeValue2");
								}else if(pcpProductionData.getRodString().getEveryRod().get(2).getType()==3){
									rodType3=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType3=languageResourceMap.get("rodStringTypeValue1");
								}
								rodGrade3=pcpProductionData.getRodString().getEveryRod().get(2).getGrade();
								rodOutsideDiameter3=pcpProductionData.getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
								rodInsideDiameter3=pcpProductionData.getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
								rodLength3=pcpProductionData.getRodString().getEveryRod().get(2).getLength()+"";
							}
							if(pcpProductionData.getRodString().getEveryRod().size()>3){
								if(pcpProductionData.getRodString().getEveryRod().get(3).getType()==1){
									rodType4=languageResourceMap.get("rodStringTypeValue1");
								}else if(pcpProductionData.getRodString().getEveryRod().get(3).getType()==2){
									rodType4=languageResourceMap.get("rodStringTypeValue2");
								}else if(pcpProductionData.getRodString().getEveryRod().get(3).getType()==3){
									rodType4=languageResourceMap.get("rodStringTypeValue3");
								}else{
									rodType4=languageResourceMap.get("rodStringTypeValue1");
								}
								rodGrade4=pcpProductionData.getRodString().getEveryRod().get(3).getGrade();
								rodOutsideDiameter4=pcpProductionData.getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
								rodInsideDiameter4=pcpProductionData.getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
								rodLength4=pcpProductionData.getRodString().getEveryRod().get(3).getLength()+"";
							}
						}
						result_json.append("{\"id\":20,\"itemName\":\""+languageResourceMap.get("rodStringType1")+"\",\"itemCode\":\"rodStringType1\",\"itemValue\":\""+rodType1+"\"},");
						result_json.append("{\"id\":21,\"itemName\":\""+languageResourceMap.get("rodGrade1")+"\",\"itemCode\":\"rodGrade1\",\"itemValue\":\""+rodGrade1+"\"},");
						result_json.append("{\"id\":22,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter1")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter1\",\"itemValue\":\""+rodOutsideDiameter1+"\"},");
						result_json.append("{\"id\":23,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter1")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter1\",\"itemValue\":\""+rodInsideDiameter1+"\"},");
						result_json.append("{\"id\":24,\"itemName\":\""+languageResourceMap.get("rodStringLength1")+"(m)\",\"itemCode\":\"rodStringLength1\",\"itemValue\":\""+rodLength1+"\"},");
						
						result_json.append("{\"id\":25,\"itemName\":\""+languageResourceMap.get("rodStringType2")+"\",\"itemCode\":\"rodStringType2\",\"itemValue\":\""+rodType2+"\"},");
						result_json.append("{\"id\":26,\"itemName\":\""+languageResourceMap.get("rodGrade2")+"\",\"itemCode\":\"rodGrade2\",\"itemValue\":\""+rodGrade2+"\"},");
						result_json.append("{\"id\":27,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter2")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter2\",\"itemValue\":\""+rodOutsideDiameter2+"\"},");
						result_json.append("{\"id\":28,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter2")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter2\",\"itemValue\":\""+rodInsideDiameter2+"\"},");
						result_json.append("{\"id\":29,\"itemName\":\""+languageResourceMap.get("rodStringLength2")+"(m)\",\"itemCode\":\"rodStringLength2\",\"itemValue\":\""+rodLength2+"\"},");
						
						result_json.append("{\"id\":30,\"itemName\":\""+languageResourceMap.get("rodStringType3")+"\",\"itemCode\":\"rodStringType3\",\"itemValue\":\""+rodType3+"\"},");
						result_json.append("{\"id\":31,\"itemName\":\""+languageResourceMap.get("rodGrade3")+"\",\"itemCode\":\"rodGrade3\",\"itemValue\":\""+rodGrade3+"\"},");
						result_json.append("{\"id\":32,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter3")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter3\",\"itemValue\":\""+rodOutsideDiameter3+"\"},");
						result_json.append("{\"id\":33,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter3")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter3\",\"itemValue\":\""+rodInsideDiameter3+"\"},");
						result_json.append("{\"id\":34,\"itemName\":\""+languageResourceMap.get("rodStringLength3")+"(m)\",\"itemCode\":\"rodStringLength3\",\"itemValue\":\""+rodLength3+"\"},");
						
						result_json.append("{\"id\":35,\"itemName\":\""+languageResourceMap.get("rodStringType4")+"\",\"itemCode\":\"rodStringType4\",\"itemValue\":\""+rodType4+"\"},");
						result_json.append("{\"id\":36,\"itemName\":\""+languageResourceMap.get("rodGrade4")+"\",\"itemCode\":\"rodGrade4\",\"itemValue\":\""+rodGrade4+"\"},");
						result_json.append("{\"id\":37,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter4")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter4\",\"itemValue\":\""+rodOutsideDiameter4+"\"},");
						result_json.append("{\"id\":38,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter4")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter4\",\"itemValue\":\""+rodInsideDiameter4+"\"},");
						result_json.append("{\"id\":39,\"itemName\":\""+languageResourceMap.get("rodStringLength4")+"(m)\",\"itemCode\":\"rodStringLength4\",\"itemValue\":\""+rodLength4+"\"},");
						
						result_json.append("{\"id\":40,\"itemName\":\""+languageResourceMap.get("netGrossRatio")+"\",\"itemCode\":\"netGrossRatio\",\"itemValue\":\""+(pcpProductionData.getManualIntervention()!=null?pcpProductionData.getManualIntervention().getNetGrossRatio():"")+"\"},");
						result_json.append("{\"id\":41,\"itemName\":\""+languageResourceMap.get("netGrossValue")+"(m^3/d)\",\"itemCode\":\"netGrossValue\",\"itemValue\":\""+(pcpProductionData.getManualIntervention()!=null?pcpProductionData.getManualIntervention().getNetGrossValue():"")+"\"}");
					}else{
						result_json.append("{\"id\":1,\"itemName\":\""+languageResourceMap.get("crudeOilDensity")+"(g/cm^3)\",\"itemCode\":\"crudeOilDensity\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":2,\"itemName\":\""+languageResourceMap.get("waterDensity")+"(g/cm^3)\",\"itemCode\":\"waterDensity\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":3,\"itemName\":\""+languageResourceMap.get("naturalGasRelativeDensity")+"\",\"itemCode\":\"naturalGasRelativeDensity\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":4,\"itemName\":\""+languageResourceMap.get("saturationPressure")+"(MPa)\",\"itemCode\":\"saturationPressure\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":5,\"itemName\":\""+languageResourceMap.get(reservoirDepthKey)+"(m)\",\"itemCode\":\"reservoirDepth\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":6,\"itemName\":\""+languageResourceMap.get(reservoirTemperatureKey)+"(℃)\",\"itemCode\":\"reservoirTemperature\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":7,\"itemName\":\""+languageResourceMap.get(tubingPressurekey)+"(MPa)\",\"itemCode\":\"tubingPressure\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":8,\"itemName\":\""+languageResourceMap.get("casingPressure")+"(MPa)\",\"itemCode\":\"casingPressure\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":9,\"itemName\":\""+languageResourceMap.get("wellHeadTemperature")+"(℃)\",\"itemCode\":\"wellHeadTemperature\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":10,\"itemName\":\""+languageResourceMap.get("waterCut")+"(%)\",\"itemCode\":\"waterCut\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":11,\"itemName\":\""+languageResourceMap.get("productionGasOilRatio")+"(m^3/t)\",\"itemCode\":\"productionGasOilRatio\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":12,\"itemName\":\""+languageResourceMap.get("producingfluidLevel")+"(m)\",\"itemCode\":\"producingfluidLevel\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":13,\"itemName\":\""+languageResourceMap.get("pumpSettingDepth")+"(m)\",\"itemCode\":\"pumpSettingDepth\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":14,\"itemName\":\""+languageResourceMap.get("BarrelLength")+"(m)\",\"itemCode\":\"pumpSettingDepth\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":15,\"itemName\":\""+languageResourceMap.get("BarrelSeries")+"\",\"itemCode\":\"BarrelSeries\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":16,\"itemName\":\""+languageResourceMap.get("RotorDiameter")+"(mm)\",\"itemCode\":\"RotorDiameter\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":17,\"itemName\":\""+languageResourceMap.get("QPR")+"(ml/r)\",\"itemCode\":\"QPR\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":18,\"itemName\":\""+languageResourceMap.get("tubingStringInsideDiameter")+"(mm)\",\"itemCode\":\"tubingStringInsideDiameter\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":19,\"itemName\":\""+languageResourceMap.get("casingStringOutsideDiameter")+"(mm)\",\"itemCode\":\"casingStringOutsideDiameter\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":20,\"itemName\":\""+languageResourceMap.get("rodStringType1")+"\",\"itemCode\":\"rodStringType1\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":21,\"itemName\":\""+languageResourceMap.get("rodGrade1")+"\",\"itemCode\":\"rodGrade1\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":22,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter1")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter1\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":23,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter1")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter1\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":24,\"itemName\":\""+languageResourceMap.get("rodStringLength1")+"(m)\",\"itemCode\":\"rodStringLength1\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":25,\"itemName\":\""+languageResourceMap.get("rodStringType2")+"\",\"itemCode\":\"rodStringType2\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":26,\"itemName\":\""+languageResourceMap.get("rodGrade2")+"\",\"itemCode\":\"rodGrade2\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":27,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter2")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter2\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":28,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter2")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter2\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":29,\"itemName\":\""+languageResourceMap.get("rodStringLength2")+"(m)\",\"itemCode\":\"rodStringLength2\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":30,\"itemName\":\""+languageResourceMap.get("rodStringType3")+"\",\"itemCode\":\"rodStringType3\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":31,\"itemName\":\""+languageResourceMap.get("rodGrade3")+"\",\"itemCode\":\"rodGrade3\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":32,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter3")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter3\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":33,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter3")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter3\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":34,\"itemName\":\""+languageResourceMap.get("rodStringLength3")+"(m)\",\"itemCode\":\"rodStringLength3\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":35,\"itemName\":\""+languageResourceMap.get("rodStringType4")+"\",\"itemCode\":\"rodStringType4\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":36,\"itemName\":\""+languageResourceMap.get("rodGrade4")+"\",\"itemCode\":\"rodGrade4\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":37,\"itemName\":\""+languageResourceMap.get("rodStringOutsideDiameter4")+"(mm)\",\"itemCode\":\"rodStringOutsideDiameter4\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":38,\"itemName\":\""+languageResourceMap.get("rodStringInsideDiameter4")+"(mm)\",\"itemCode\":\"rodStringInsideDiameter4\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":39,\"itemName\":\""+languageResourceMap.get("rodStringLength4")+"(m)\",\"itemCode\":\"rodStringLength4\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":40,\"itemName\":\""+languageResourceMap.get("netGrossRatio")+"\",\"itemCode\":\"netGrossRatio\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":41,\"itemName\":\""+languageResourceMap.get("netGrossValue")+"(m^3/d)\",\"itemCode\":\"netGrossValue\",\"itemValue\":\"\"}");
					}
				}
			}
			result_json.append("]");
			resultNameBuff.append("]");
			FESdiagramSrcBuff.append("]");
			result_json.append(",\"resultNameList\":"+resultNameBuff);
			result_json.append(",\"FESdiagramSrcList\":"+FESdiagramSrcBuff);
			result_json.append(",\"applicationScenarios\":\""+applicationScenarios+"\"");
			result_json.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getFSDiagramConstructionDataInfo(String deviceId,String language) {
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		try{
			String columns = "["
					+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"itemName\",width:120 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("variable")+"\",\"dataIndex\":\"itemValue\",width:120 ,children:[] }"
					+ "]";
			String deviceTableName="tbl_device";
			String sql = "select t.constructiondata "
					+ " from "+deviceTableName+" t "
					+ " where t.id="+deviceId;
			List<?> list = this.findCallSql(sql);
			result_json.append("{\"success\":true,\"totalCount\":13,\"columns\":"+columns+",\"totalRoot\":[");
			if(list.size()>0){
				String fsDiagramConstructionData=list.get(0)+"";
				type = new TypeToken<FSDiagramConstructionRequestData>() {}.getType();
				FSDiagramConstructionRequestData requestData=gson.fromJson(fsDiagramConstructionData, type);
				if(requestData!=null){
					String boardDataSource="";
					if(requestData.getBoardDataSource()==1){
						boardDataSource=languageResourceMap.get("boardDataSource1");
					}else if(requestData.getBoardDataSource()==2){
						boardDataSource=languageResourceMap.get("boardDataSource2");
					}else if(requestData.getBoardDataSource()==3){
						boardDataSource=languageResourceMap.get("boardDataSource3");
					}
					result_json.append("{\"id\":1,\"itemName\":\""+languageResourceMap.get("boardDataSource")+"\",\"itemCode\":\"boardDataSource\",\"itemValue\":\""+boardDataSource+"\"},");
					
					result_json.append("{\"id\":2,\"itemName\":\""+languageResourceMap.get("crankDIInitAngle")+"(°)\",\"itemCode\":\"crankDIInitAngle\",\"itemValue\":\""+requestData.getCrankDIInitAngle()+"\"},");
					result_json.append("{\"id\":3,\"itemName\":\""+languageResourceMap.get("interpolationCNT")+"\",\"itemCode\":\"interpolationCNT\",\"itemValue\":\""+requestData.getInterpolationCNT()+"\"},");
					result_json.append("{\"id\":4,\"itemName\":\""+languageResourceMap.get("surfaceSystemEfficiency")+"("+languageResourceMap.get("decimals")+")\",\"itemCode\":\"surfaceSystemEfficiency\",\"itemValue\":\""+requestData.getSurfaceSystemEfficiency()+"\"},");
					result_json.append("{\"id\":5,\"itemName\":\""+languageResourceMap.get("wattTimes")+"\",\"itemCode\":\"wattTimes\",\"itemValue\":\""+requestData.getWattTimes()+"\"},");
					result_json.append("{\"id\":6,\"itemName\":\""+languageResourceMap.get("iTimes")+"\",\"itemCode\":\"iTimes\",\"itemValue\":\""+requestData.getITimes()+"\"},");
					result_json.append("{\"id\":7,\"itemName\":\""+languageResourceMap.get("fsDiagramTimes")+"\",\"itemCode\":\"fsDiagramTimes\",\"itemValue\":\""+requestData.getFSDiagramTimes()+"\"},");
					result_json.append("{\"id\":8,\"itemName\":\""+languageResourceMap.get("fsDiagramLeftTimes")+"\",\"itemCode\":\"fsDiagramLeftTimes\",\"itemValue\":\""+requestData.getFSDiagramLeftTimes()+"\"},");
					result_json.append("{\"id\":9,\"itemName\":\""+languageResourceMap.get("fsDiagramRightTimes")+"\",\"itemCode\":\"fsDiagramRightTimes\",\"itemValue\":\""+requestData.getFSDiagramRightTimes()+"\"},");
					result_json.append("{\"id\":10,\"itemName\":\""+languageResourceMap.get("leftPercent")+"(%)\",\"itemCode\":\"leftPercent\",\"itemValue\":\""+requestData.getLeftPercent()+"\"},");
					result_json.append("{\"id\":11,\"itemName\":\""+languageResourceMap.get("rightPercent")+"(%)\",\"itemCode\":\"rightPercent\",\"itemValue\":\""+requestData.getRightPercent()+"\"},");
					result_json.append("{\"id\":12,\"itemName\":\""+languageResourceMap.get("positiveXWatt")+"\",\"itemCode\":\"positiveXWatt\",\"itemValue\":\""+requestData.getPositiveXWatt()+"\"},");
					result_json.append("{\"id\":13,\"itemName\":\""+languageResourceMap.get("negativeXWatt")+"\",\"itemCode\":\"negativeXWatt\",\"itemValue\":\""+requestData.getNegativeXWatt()+"\"},");
					
					
					
					String PRTFSrc="";
					if(requestData.getPRTFSrc()==1){
						PRTFSrc=languageResourceMap.get("PRTFSrc1");
					}else if(requestData.getPRTFSrc()==2){
						PRTFSrc=languageResourceMap.get("PRTFSrc2");
					}
					
					result_json.append("{\"id\":14,\"itemName\":\""+languageResourceMap.get("PRTFSrc")+"\",\"itemCode\":\"PRTFSrc\",\"itemValue\":\""+PRTFSrc+"\"}");
					
				}else{
					result_json.append("{\"id\":1,\"itemName\":\""+languageResourceMap.get("boardDataSource")+"\",\"itemCode\":\"boardDataSource\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":2,\"itemName\":\""+languageResourceMap.get("crankDIInitAngle")+"(°)\",\"itemCode\":\"crankDIInitAngle\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":3,\"itemName\":\""+languageResourceMap.get("interpolationCNT")+"\",\"itemCode\":\"interpolationCNT\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":4,\"itemName\":\""+languageResourceMap.get("surfaceSystemEfficiency")+"("+languageResourceMap.get("decimals")+")\",\"itemCode\":\"surfaceSystemEfficiency\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":5,\"itemName\":\""+languageResourceMap.get("wattTimes")+"\",\"itemCode\":\"wattTimes\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":6,\"itemName\":\""+languageResourceMap.get("iTimes")+"\",\"itemCode\":\"iTimes\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":7,\"itemName\":\""+languageResourceMap.get("fsDiagramTimes")+"\",\"itemCode\":\"fsDiagramTimes\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":8,\"itemName\":\""+languageResourceMap.get("fsDiagramLeftTimes")+"\",\"itemCode\":\"fsDiagramLeftTimes\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":9,\"itemName\":\""+languageResourceMap.get("fsDiagramRightTimes")+"\",\"itemCode\":\"fsDiagramRightTimes\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":10,\"itemName\":\""+languageResourceMap.get("leftPercent")+"(%)\",\"itemCode\":\"leftPercent\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":11,\"itemName\":\""+languageResourceMap.get("rightPercent")+"(%)\",\"itemCode\":\"rightPercent\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":12,\"itemName\":\""+languageResourceMap.get("positiveXWatt")+"\",\"itemCode\":\"positiveXWatt\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":13,\"itemName\":\""+languageResourceMap.get("negativeXWatt")+"\",\"itemCode\":\"negativeXWatt\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":14,\"itemName\":\""+languageResourceMap.get("PRTFSrc")+"\",\"itemCode\":\"PRTFSrc\",\"itemValue\":\"\"}");
					
				}
			}
			result_json.append("]");
			result_json.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getSystemParameterInfo(String deviceId,String language) {
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		try{
			String columns = "["
					+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"itemName\",width:120 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("variable")+"\",\"dataIndex\":\"itemValue\",width:120 ,children:[] }"
					+ "]";
			
			result_json.append("{\"success\":true,\"totalCount\":2,\"columns\":"+columns+",\"totalRoot\":[");
			result_json.append("{\"id\":1,\"itemName\":\""+languageResourceMap.get("systemDate")+"\",\"itemCode\":\"systemDate\",\"itemValue\":\""+StringManagerUtils.getCurrentTime("yyyy-MM-dd")+"\"},");
			result_json.append("{\"id\":2,\"itemName\":\""+languageResourceMap.get("systemTime")+"\",\"itemCode\":\"systemTime\",\"itemValue\":\""+StringManagerUtils.getCurrentTime("HH:mm:ss")+"\"}");
			result_json.append("]");
			result_json.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getDeviceVideoInfo(String deviceId,String deviceType,String orgId,String language) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer videoKeyDropdownData = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",flex:1 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"itemName\",flex:1 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("videoUrl")+"\",\"dataIndex\":\"videoUrl\",flex:5 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("videoKey")+"\",\"dataIndex\":\"videoKey\",flex:1 ,children:[] }"
				+ "]";
		String deviceTableName="tbl_device";
		String sql = "select t.videourl1,t2.account as videokey1,t.videourl2,t3.account as videokey2"
				+ " from "+deviceTableName+" t"
				+ " left outer join TBL_VIDEOKEY t2 on t.videokeyid1=t2.id "
				+ " left outer join TBL_VIDEOKEY t3 on t.videokeyid2=t3.id "
				+ " where t.id="+deviceId;
		String videoKeySql="select t.account from TBL_VIDEOKEY t where t.orgid in("+orgId+") order by t.id";
		List<?> videoKeyList = this.findCallSql(videoKeySql);
		videoKeyDropdownData.append("[");
		if(videoKeyList.size()>0){
			videoKeyDropdownData.append("\"\",");
			for(int i=0;i<videoKeyList.size();i++){
				videoKeyDropdownData.append("\""+videoKeyList.get(i)+"\",");
			}
			if(videoKeyDropdownData.toString().endsWith(",")){
				videoKeyDropdownData.deleteCharAt(videoKeyDropdownData.length() - 1);
			}
		}
		videoKeyDropdownData.append("]");
		
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":2,\"columns\":"+columns+",\"videoKeyList\":"+videoKeyDropdownData.toString()+",\"totalRoot\":[");
		String videoUrl1="",videoUrl2="",videoKey1="",videoKey2="";
		if(list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			videoUrl1=obj[0]+"";
			videoKey1=obj[1]+"";
			videoUrl2=obj[2]+"";
			videoKey2=obj[3]+"";
		}
		result_json.append("{\"id\":1,\"itemName\":\""+languageResourceMap.get("video1")+"\",\"videoUrl\":\""+videoUrl1+"\",\"videoKey\":\""+videoKey1+"\"},");
		result_json.append("{\"id\":2,\"itemName\":\""+languageResourceMap.get("video2")+"\",\"videoUrl\":\""+videoUrl2+"\",\"videoKey\":\""+videoKey2+"\"},");
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getVideoKeyData(String orgId,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",flex:1 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"account\",flex:1 ,children:[] },"
				+ "{ \"header\":\"appKey\",\"dataIndex\":\"appKey\",flex:5 ,children:[] },"
				+ "{ \"header\":\"secret\",\"dataIndex\":\"secret\",flex:5 ,children:[] }"
				+ "]";
		String sql="select t.id,t.account,t.appkey,t.secret from TBL_VIDEOKEY t where t.orgid in("+orgId+") order by t.id";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"account\":\""+obj[1]+"\",");
			result_json.append("\"appKey\":\""+obj[2]+"\",");
			result_json.append("\"secret\":\""+obj[3]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getAcquisitionUnitList(String protocol){
		StringBuffer result_json = new StringBuffer();
		String unitSql="select t.unit_name from tbl_acq_unit_conf t where 1=1";
		if(StringManagerUtils.isNotNull(protocol)){
			unitSql+=" and t.protocol='"+protocol+"'";
		}
		unitSql+= " order by t.id";
		List<?> unitList = this.findCallSql(unitSql);
		result_json.append("{\"data\":[");
		for(int i=0;i<unitList.size();i++){
			result_json.append("\""+unitList.get(i)+"\",");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public String getBatchAddDeviceTableInfo(String deviceType,int recordCount,String dictDeviceType,User user) {
		StringBuffer result_json = new StringBuffer();
		String language=user!=null?user.getLanguageName():"";
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Map<String,Code> codeMap=MemoryDataManagerTask.getCodeMap("APPLICATIONSCENARIOS",language);
		Map<String,WorkType> workTypeMap=MemoryDataManagerTask.getWorkTypeMap(language);
		
		StringBuffer deviceTypeDropdownData = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer displayInstanceDropdownData = new StringBuffer();
		StringBuffer reportInstanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		StringBuffer applicationScenariosDropdownData = new StringBuffer();
		StringBuffer resultNameDropdownData = new StringBuffer();
		String ddicCode="deviceInfo_DeviceBatchAdd";
		
		String deviceTypeSql="select t.name_"+language+" from tbl_devicetypeinfo t where t.id in ("+deviceType+") order by t.id";
		String instanceSql="select t.name "
				+ " from tbl_protocolinstance t,tbl_acq_unit_conf t2,tbl_protocol t3 "
				+ " where t.unitid=t2.id and t2.protocol=t3.name "
				+ " and t3.language="+(user!=null?user.getLanguage():0)
				+ " order by t.sort";
		String displayInstanceSql="select t.name "
				+ " from tbl_protocoldisplayinstance t,tbl_display_unit_conf t2,tbl_acq_unit_conf t3,tbl_protocol t4 "
				+ " where t.displayunitid=t2.id and t2.acqunitid=t3.id and t3.protocol=t4.name "
				+ " and t4.language= "+(user!=null?user.getLanguage():0)
				+ " order by t.sort";
		String reportInstanceSql="select t.name from tbl_protocolreportinstance t  order by t.sort";
		String alarmInstanceSql="select t.name "
				+ " from tbl_protocolalarminstance t,tbl_alarm_unit_conf t2, tbl_protocol t3 "
				+ " where t.alarmunitid=t2.id and t2.protocol=t3.name "
				+ " and t3.language="+(user!=null?user.getLanguage():0)
				+ " order by t.sort";
		String columns=service.showTableHeadersColumns(ddicCode,dictDeviceType,language);
		
		deviceTypeDropdownData.append("[");
		instanceDropdownData.append("[");
		displayInstanceDropdownData.append("[");
		reportInstanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");
		applicationScenariosDropdownData.append("[");
		resultNameDropdownData.append("[");

		List<?> deviceTypeList = this.findCallSql(deviceTypeSql);
		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
		List<?> reportInstanceList = this.findCallSql(reportInstanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		
		if(deviceTypeList.size()>0){
			for(int i=0;i<deviceTypeList.size();i++){
				deviceTypeDropdownData.append("'"+deviceTypeList.get(i)+"',");
			}
			if(deviceTypeDropdownData.toString().endsWith(",")){
				deviceTypeDropdownData.deleteCharAt(deviceTypeDropdownData.length() - 1);
			}
		}
		
		if(instanceList.size()>0){
			instanceDropdownData.append("\"\",");
			for(int i=0;i<instanceList.size();i++){
				instanceDropdownData.append("'"+instanceList.get(i)+"',");
			}
			if(instanceDropdownData.toString().endsWith(",")){
				instanceDropdownData.deleteCharAt(instanceDropdownData.length() - 1);
			}
		}
		
		if(displayInstanceList.size()>0){
			displayInstanceDropdownData.append("\"\",");
			for(int i=0;i<displayInstanceList.size();i++){
				displayInstanceDropdownData.append("'"+displayInstanceList.get(i)+"',");
			}
			if(displayInstanceDropdownData.toString().endsWith(",")){
				displayInstanceDropdownData.deleteCharAt(displayInstanceDropdownData.length() - 1);
			}
		}
		
		if(reportInstanceList.size()>0){
			reportInstanceDropdownData.append("\"\",");
			for(int i=0;i<reportInstanceList.size();i++){
				reportInstanceDropdownData.append("'"+reportInstanceList.get(i)+"',");
			}
			if(reportInstanceDropdownData.toString().endsWith(",")){
				reportInstanceDropdownData.deleteCharAt(reportInstanceDropdownData.length() - 1);
			}
		}
		
		if(alarmInstanceList.size()>0){
			alarmInstanceDropdownData.append("\"\",");
			for(int i=0;i<alarmInstanceList.size();i++){
				alarmInstanceDropdownData.append("'"+alarmInstanceList.get(i)+"',");
			}
			if(alarmInstanceDropdownData.toString().endsWith(",")){
				alarmInstanceDropdownData.deleteCharAt(alarmInstanceDropdownData.length() - 1);
			}
		}
		
		
		Iterator<Map.Entry<String,Code>> it = codeMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Code> entry = it.next();
			Code c=entry.getValue();
			applicationScenariosDropdownData.append("'"+c.getItemname()+"',");
		}
		if(applicationScenariosDropdownData.toString().endsWith(",")){
			applicationScenariosDropdownData.deleteCharAt(applicationScenariosDropdownData.length() - 1);
		}
		
		if(workTypeMap.size()>0){
			resultNameDropdownData.append("\""+languageResourceMap.get("noIntervention")+"\"");
			Iterator<Map.Entry<String, WorkType>> workTypeMapIt = workTypeMap.entrySet().iterator();
			while(workTypeMapIt.hasNext()){
				Map.Entry<String, WorkType> entry = workTypeMapIt.next();
				String resultCode=new String(entry.getKey());
				WorkType w=entry.getValue();
				resultNameDropdownData.append(",\""+w.getResultName()+"\"");
			}
		}
		
		deviceTypeDropdownData.append("]");
		instanceDropdownData.append("]");
		displayInstanceDropdownData.append("]");
		reportInstanceDropdownData.append("]");
		alarmInstanceDropdownData.append("]");
		applicationScenariosDropdownData.append("]");
		resultNameDropdownData.append("]");
		
		String json = "";
		result_json.append("{\"success\":true,"
				+ "\"totalCount\":"+recordCount+","
				+ "\"deviceTypeDropdownData\":"+deviceTypeDropdownData.toString()+","
				+ "\"instanceDropdownData\":"+instanceDropdownData.toString()+","
				+ "\"displayInstanceDropdownData\":"+displayInstanceDropdownData.toString()+","
				+ "\"reportInstanceDropdownData\":"+reportInstanceDropdownData.toString()+","
				+ "\"alarmInstanceDropdownData\":"+alarmInstanceDropdownData.toString()+","
				+ "\"applicationScenariosDropdownData\":"+applicationScenariosDropdownData.toString()+","
				+ "\"resultNameDropdownData\":"+resultNameDropdownData.toString()+",");
		result_json.append("\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=1;i<=recordCount;i++){
			result_json.append("{},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public boolean judgeDeviceExistOrNot(String orgId,String deviceName,String deviceTypeStr) {
		boolean flag = false;
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		String tableName="tbl_device";
		if(deviceType>=300){
			tableName="tbl_smsdevice";
		}
		if (StringManagerUtils.isNotNull(deviceName)&&StringManagerUtils.isNotNull(orgId)) {
			String sql = "select t.id from "+tableName+" t where t.deviceName='"+deviceName+"' and t.orgid="+orgId;
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgeDeviceExistOrNotBySigninIdAndSlave(String deviceTypeStr,String signinId,String slaveStr) {
		boolean flag = false;
		int slave=StringManagerUtils.stringToInteger(slaveStr);
		if (StringManagerUtils.isNotNull(signinId)&&StringManagerUtils.isNotNull(slaveStr)) {
			String srpSql = "select t.id from tbl_device t where t.signinid='"+signinId+"' and to_number(t.slave)="+slave;
			List<?> srpList = this.findCallSql(srpSql);
			if (srpList.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgeDeviceExistOrNotByIpPortAndSlave(String deviceTypeStr,String ipPort,String slaveStr) {
		boolean flag = false;
		int slave=StringManagerUtils.stringToInteger(slaveStr);
		if (StringManagerUtils.isNotNull(ipPort)&&StringManagerUtils.isNotNull(slaveStr)) {
			String srpSql = "select t.id from tbl_device t where t.ipPort='"+ipPort+"' and to_number(t.slave)="+slave;
			List<?> srpList = this.findCallSql(srpSql);
			if (srpList.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgePumpingModelExistOrNot(String manufacturer,String model) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(manufacturer)&&StringManagerUtils.isNotNull(model)) {
			String sql = "select t.id from tbl_pumpingmodel t where t.manufacturer='"+manufacturer+"' and t.model='"+model+"'";
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgeVideoKeyExistOrNot(String account) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(account)) {
			String sql = "select t.id from TBL_VIDEOKEY t where t.account='"+account+"'";
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public String getUpstreamAndDownstreamInteractionDeviceList(String orgId,String deviceName,String deviceType,Page pager,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String deviceTableName="tbl_device";
		String tableName="tbl_acqdata_latest";
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("deviceName")+"\",\"dataIndex\":\"wellName\",flex:1,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("commStatus")+"\",\"dataIndex\":\"commStatusName\",width:90,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("signInId")+"\",\"dataIndex\":\"signinId\",flex:1,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("slave")+"\",\"dataIndex\":\"slave\",flex:1,children:[] }"
				+ "]";
		
		String sql="select t.id,t.devicename,"
				+ "t2.commstatus,decode(t2.commstatus,1,'"+languageResourceMap.get("online")+"',2,'"+languageResourceMap.get("goOnline")+"','"+languageResourceMap.get("offline")+"') as commStatusName,"
				+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'),t.signinid,t.slave "
				+ " from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on t2.deviceid=t.id";
		sql+= " where  t.orgid in ("+orgId+") "
				+ " and t.instancecode in ( select t3.code from tbl_protocolinstance t3 where t3.acqprotocoltype ='private-srp' or t3.ctrlprotocoltype ='private-srp' )";
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.deviceName='"+deviceName+"'";
		}
		sql+=" order by t.sortnum,t.deviceName";
		
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
			result_json.append("\"commStatus\":"+obj[2]+",");
			result_json.append("\"commStatusName\":\""+obj[3]+"\",");
			
			result_json.append("\"acqTime\":\""+obj[4]+"\",");
			result_json.append("\"signinId\":\""+obj[5]+"\",");
			result_json.append("\"slave\":\""+obj[6]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getDeviceModelData(String deviceId) {
		SRPCalculateRequestData calculateRequestData=null;
		String result_json="";
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String sql="select t.productiondata,t.balanceinfo,t.stroke,"
				+ " t.pumpingmodelid,"
				+ " t2.manufacturer,t2.model,t2.crankrotationdirection,t2.offsetangleofcrank,t2.crankgravityradius,t2.singlecrankweight,t2.singlecrankpinweight,t2.structuralunbalance,"
				+ " t2.prtf"
				+ " from tbl_device t "
				+ " left outer join tbl_pumpingmodel t2 on t.pumpingmodelid=t2.id"
				+ " where t.id= "+deviceId;
		List<?> list = this.findCallSql(sql);
		
		if(list.size()>0){
			Object[] object = (Object[]) list.get(0);
			String productionData=object[0].toString(); 
			type = new TypeToken<SRPCalculateRequestData>() {}.getType();
			calculateRequestData=gson.fromJson(productionData, type);
			if(calculateRequestData!=null){
				int pumpingModelId=StringManagerUtils.stringToInteger(object[3]+"");
	        	if(pumpingModelId>0){
	        		calculateRequestData.setPumpingUnit(new SRPCalculateRequestData.PumpingUnit());
	        		String balanceInfo=object[1]+"";
					type = new TypeToken<SRPCalculateRequestData.Balance>() {}.getType();
					SRPCalculateRequestData.Balance balance=gson.fromJson(balanceInfo, type);
					if(balance!=null){
						calculateRequestData.getPumpingUnit().setBalance(balance);
					}
	        		float stroke=StringManagerUtils.stringToFloat(object[2]+"");
					calculateRequestData.getPumpingUnit().setStroke(stroke);
	        		calculateRequestData.getPumpingUnit().setManufacturer(object[4]+"");
	        		calculateRequestData.getPumpingUnit().setModel(object[5]+"");
	        		calculateRequestData.getPumpingUnit().setCrankRotationDirection(object[6]+"");
	        		calculateRequestData.getPumpingUnit().setOffsetAngleOfCrank(StringManagerUtils.stringToFloat(object[7]+""));
					calculateRequestData.getPumpingUnit().setCrankGravityRadius(StringManagerUtils.stringToFloat(object[8]+""));
					calculateRequestData.getPumpingUnit().setSingleCrankWeight(StringManagerUtils.stringToFloat(object[9]+""));
					calculateRequestData.getPumpingUnit().setSingleCrankPinWeight(StringManagerUtils.stringToFloat(object[10]+""));
					calculateRequestData.getPumpingUnit().setStructuralUnbalance(StringManagerUtils.stringToFloat(object[11]+""));
					
					String prtf="";
					if(object[12]!=null){
						try {
							SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[12]);
							CLOB realClob = (CLOB) proxy.getWrappedClob(); 
							prtf=StringManagerUtils.CLOBtoString(realClob);
						} catch (SQLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if(!StringManagerUtils.isNotNull(prtf)){
						prtf="";
					}
					type = new TypeToken<PumpingPRTFData>() {}.getType();
					PumpingPRTFData pumpingPRTFData=gson.fromJson(prtf, type);
					if(pumpingPRTFData!=null&&pumpingPRTFData.getList()!=null&&pumpingPRTFData.getList().size()>0){
						for(int i=0;i<pumpingPRTFData.getList().size();i++){
							if(stroke>0&&stroke==pumpingPRTFData.getList().get(i).getStroke()){
								calculateRequestData.getPumpingUnit().setPRTF(new SRPCalculateRequestData.PRTF());
								calculateRequestData.getPumpingUnit().getPRTF().setCrankAngle(new ArrayList<Float>());
								calculateRequestData.getPumpingUnit().getPRTF().setPR(new ArrayList<Float>());
								calculateRequestData.getPumpingUnit().getPRTF().setTF(new ArrayList<Float>());
								if(pumpingPRTFData.getList().get(i).getPRTF()!=null && pumpingPRTFData.getList().get(i).getPRTF().size()>0){
									int prtfSize=pumpingPRTFData.getList().get(i).getPRTF().size();
									for(int j=0;j<prtfSize;j++){
										calculateRequestData.getPumpingUnit().getPRTF().getCrankAngle().add(pumpingPRTFData.getList().get(i).getPRTF().get(j).getCrankAngle());
										calculateRequestData.getPumpingUnit().getPRTF().getPR().add(pumpingPRTFData.getList().get(i).getPRTF().get(j).getPR());
										calculateRequestData.getPumpingUnit().getPRTF().getTF().add(pumpingPRTFData.getList().get(i).getPRTF().get(j).getTF());
									}
								}
								break;
							}
						}
					}
	        	}else{
	        		calculateRequestData.setPumpingUnit(null);
	        	}
	        	result_json=gson.toJson(calculateRequestData);
	        	result_json = StringManagerUtils.toPrettyFormat(result_json);
			}
		}

		return result_json;
	}
	
	public String getWaterCutRawData(String signinId,String slave,String language) throws IOException, SQLException{
		String result_json = "";
		List<StringBuffer> groupList=new ArrayList<StringBuffer>();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		int totals=0;
		String acqTime="";
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50,children:[] },"
				+ "{ \"header\":\"采样时间\",\"dataIndex\":\"pointAcqTime\",flex:2,children:[] },"
				+ "{ \"header\":\"采样间隔(ms)\",\"dataIndex\":\"interval\",flex:1,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("waterCut")+"(%)\",\"dataIndex\":\"waterCut\",flex:1,children:[] },"
				+ "{ \"header\":\"压力(MPa)\",\"dataIndex\":\"tubingPressure\",flex:1,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("position")+"\",\"dataIndex\":\"position\",flex:1,children:[] }"
				+ "]";
		result_json+="{ \"success\":true,\"columns\":"+columns+",";
		result_json+="\"totalRoot\":[";
		if(StringManagerUtils.isNotNull(signinId) && StringManagerUtils.isNotNull(slave)){
			String url=Config.getInstance().configFile.getAd().getSrp().getReadTopicReq();
			String topic="rawwatercut";
			
			StringBuffer requestBuff = new StringBuffer();
//			signinId="d1e3643c140569d4";
			requestBuff.append("{\"ID\":\""+signinId+"\",");
			requestBuff.append("\"Topic\":\""+topic+"\"}");
			String responseData=StringManagerUtils.sendPostMethod(url, requestBuff.toString(),"utf-8",5,180);
//			String path="";
//			StringManagerUtils stringManagerUtils=new StringManagerUtils();
//			path=stringManagerUtils.getFilePath("test7.json","example/");
//			responseData=stringManagerUtils.readFile(path,"utf-8");
			
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			type = new TypeToken<WaterCutRawData>() {}.getType();
			WaterCutRawData waterCutRawData=gson.fromJson(responseData, type);
			
			if(waterCutRawData!=null && waterCutRawData.getResultStatus()==1 && waterCutRawData.getMessage()!=null && waterCutRawData.getMessage().getWaterCut()!=null){
				totals=waterCutRawData.getMessage().getWaterCut().size();
				int groupCount=totals%10000==0?(totals/10000):(totals/10000+1);
				for(int i=0;i<groupCount;i++){
					groupList.add(new StringBuffer());
				}
				acqTime=waterCutRawData.getMessage().getAcqTime();
				String startTime=acqTime.split("~")[0];
				long timeStamp=StringManagerUtils.getTimeStamp(startTime, "yyyy-MM-dd HH:mm:ss");
				for(int i=0;i<waterCutRawData.getMessage().getWaterCut().size();i++){
					int groupIndex=i/10000;
					if(i>0){
						timeStamp+=waterCutRawData.getMessage().getInterval().get(i);
					}
					String pointAcqTime=StringManagerUtils.timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss.SSS");
					groupList.get(groupIndex).append("{\"id\":"+(i+1)+",");
					groupList.get(groupIndex).append("\"pointAcqTime\":\""+pointAcqTime+"\",");
					groupList.get(groupIndex).append("\"interval\":\""+waterCutRawData.getMessage().getInterval().get(i)+"\",");
					groupList.get(groupIndex).append("\"timeStamp\":"+timeStamp+",");
					groupList.get(groupIndex).append("\"waterCut\":"+waterCutRawData.getMessage().getWaterCut().get(i)+",");
					groupList.get(groupIndex).append("\"tubingPressure\":\""+waterCutRawData.getMessage().getTubingPressure().get(i)+"\",");
					groupList.get(groupIndex).append("\"position\":\""+waterCutRawData.getMessage().getPosition().get(i)+"\"},");
				}
			}
			
		}
		for(int i=0;i<groupList.size();i++){
			result_json+=groupList.get(i);
		}
		if(result_json.endsWith(",")){
			result_json.substring(0,result_json.length()-1);
		}
		result_json+="],\"totalCount\":"+totals+",\"acqTime\":\""+acqTime+"\"}";
		return result_json.replaceAll("\"null\"", "\"\"");
	}
	
	public String getWaterCutRawData2(String signinId,String slave) throws IOException, SQLException{
		String result = "";
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		if(StringManagerUtils.isNotNull(signinId) && StringManagerUtils.isNotNull(slave)){
			try{
				String url=Config.getInstance().configFile.getAd().getSrp().getReadTopicReq();
				String topic="rawwatercut";
				
				StringBuffer requestBuff = new StringBuffer();
				requestBuff.append("{\"ID\":\""+signinId+"\",");
				requestBuff.append("\"Topic\":\""+topic+"\"}");
				String responseData=StringManagerUtils.sendPostMethod(url, requestBuff.toString(),"utf-8",5,180);
				
				type = new TypeToken<WaterCutRawData>() {}.getType();
				WaterCutRawData waterCutRawData=gson.fromJson(responseData, type);
				
				if(waterCutRawData!=null && waterCutRawData.getResultStatus()==1 && waterCutRawData.getMessage()!=null && waterCutRawData.getMessage().getWaterCut()!=null){
					result=gson.toJson(waterCutRawData);
				}
			}catch(OutOfMemoryError|StackOverflowError e){
				e.printStackTrace();
				result="{\"ResultStatus\":-999,\"OutOfMemory\":true}";
			}
		}
		return result;
	}
	
	public String getWaterCutRawDataExport(String signinId,String slave) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		result_json.append("[");
		if(StringManagerUtils.isNotNull(signinId) && StringManagerUtils.isNotNull(slave)){
			String url=Config.getInstance().configFile.getAd().getSrp().getReadTopicReq();
			String topic="rawwatercut";
			StringBuffer requestBuff = new StringBuffer();
			requestBuff.append("{\"ID\":\""+signinId+"\",");
			requestBuff.append("\"Topic\":\""+topic+"\"}");
			String responseData=StringManagerUtils.sendPostMethod(url, requestBuff.toString(),"utf-8",0,0);
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			type = new TypeToken<WaterCutRawData>() {}.getType();
			WaterCutRawData waterCutRawData=gson.fromJson(responseData, type);
			if(waterCutRawData!=null && waterCutRawData.getResultStatus()==1 && waterCutRawData.getMessage()!=null && waterCutRawData.getMessage().getWaterCut()!=null){
				for(int i=0;i<waterCutRawData.getMessage().getWaterCut().size();i++){
					result_json.append("{\"id\":"+(i+1)+",");
					result_json.append("\"interval\":\""+waterCutRawData.getMessage().getInterval().get(i)+"\",");
					result_json.append("\"waterCut\":"+waterCutRawData.getMessage().getWaterCut().get(i)+",");
					result_json.append("\"tubingPressure\":\""+waterCutRawData.getMessage().getTubingPressure().get(i)+"\",");
					result_json.append("\"position\":\""+waterCutRawData.getMessage().getPosition().get(i)+"\"},");
				}
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String saveVideoKeyHandsontableData(VideoKeyHandsontableChangedData videoKeyHandsontableChangedData,String orgId) throws Exception {
		String sql="";
		List<Integer> idList=new ArrayList<>();
		List<Integer> delIdList=new ArrayList<>();
		if(videoKeyHandsontableChangedData!=null){
			if(videoKeyHandsontableChangedData.getUpdatelist()!=null){
				for(int i=0;i<videoKeyHandsontableChangedData.getUpdatelist().size();i++){
					sql="update TBL_VIDEOKEY t set t.account='"+videoKeyHandsontableChangedData.getUpdatelist().get(i).getAccount()+"',"
							+ "t.appkey='"+videoKeyHandsontableChangedData.getUpdatelist().get(i).getAppKey()+"',"
							+ "t.secret='"+videoKeyHandsontableChangedData.getUpdatelist().get(i).getSecret()+"' "
							+ " where t.id="+videoKeyHandsontableChangedData.getUpdatelist().get(i).getId();
					this.getBaseDao().updateOrDeleteBySql(sql);
					idList.add(StringManagerUtils.stringToInteger(videoKeyHandsontableChangedData.getUpdatelist().get(i).getId()));
				}
			}
			if(videoKeyHandsontableChangedData.getDelidslist()!=null && videoKeyHandsontableChangedData.getDelidslist().size()>0){
				sql="delete from TBL_VIDEOKEY t where t.id in("+StringUtils.join(videoKeyHandsontableChangedData.getDelidslist(), ",")+")";
				this.getBaseDao().updateOrDeleteBySql(sql);
				for(int i=0;i<videoKeyHandsontableChangedData.getDelidslist().size();i++){
					delIdList.add(StringManagerUtils.stringToInteger(videoKeyHandsontableChangedData.getDelidslist().get(i)));
				}
			}
		}
		if(idList.size()>0){
			MemoryDataManagerTask.loadUIKitAccessToken(idList,"update");
		}
		if(delIdList.size()>0){
			MemoryDataManagerTask.loadUIKitAccessToken(delIdList,"delete");
		}
		return "";
	}
	
	public String getDefaultInstanceCode(int deviceType){
		if(deviceType>=100 && deviceType<200){
			deviceType=0;
		}else if(deviceType>=200 && deviceType<300){
			deviceType=1;
		}
		
		String acqInstanceCode=" ",displayInstanceCode=" ",alarmInstanceCode=" ",reportInstanceCode=" ";
		String acqInstanceSql="select v.code from(select t.code from TBL_PROTOCOLINSTANCE t where t.devicetype="+deviceType+" order by t.id) v where rownum=1";
		String displayInstanceSql="select v.code from(select t.code from tbl_protocoldisplayinstance t where t.devicetype="+deviceType+" order by t.id) v where rownum=1";
		String alarmInstanceSql="select v.code from(select t.code from tbl_protocolalarminstance t where t.devicetype="+deviceType+" order by t.id) v where rownum=1";
		String reportInstanceSql="select v.code from(select t.code from tbl_protocolreportinstance t where t.devicetype="+deviceType+" order by t.id) v where rownum=1";
		
		List<?> acqInstanceList = this.findCallSql(acqInstanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		List<?> reportInstanceList = this.findCallSql(reportInstanceSql);
		
		if(acqInstanceList.size()>0 && acqInstanceList.get(0)!=null ){
			acqInstanceCode=acqInstanceList.get(0).toString().replaceAll("null", "");
		}
		if(displayInstanceList.size()>0 && displayInstanceList.get(0)!=null ){
			displayInstanceCode=displayInstanceList.get(0).toString().replaceAll("null", "");
		}
		if(alarmInstanceList.size()>0 && alarmInstanceList.get(0)!=null ){
			alarmInstanceCode=alarmInstanceList.get(0).toString().replaceAll("null", "");
		}
		if(reportInstanceList.size()>0 && reportInstanceList.get(0)!=null ){
			reportInstanceCode=reportInstanceList.get(0).toString().replaceAll("null", "");
		}
		return acqInstanceCode+";"+displayInstanceCode+";"+alarmInstanceCode+";"+reportInstanceCode;
	}
	
	public String getDefaultInstanceName(String deviceType){
		String acqInstanceName=" ",displayInstanceName=" ",alarmInstanceName=" ",reportInstanceName=" ";
		String acqInstanceSql="select v.name from(select t.name from TBL_PROTOCOLINSTANCE t where t.devicetype in ("+deviceType+") order by t.id) v where rownum=1";
		String displayInstanceSql="select v.name from(select t.name from tbl_protocoldisplayinstance t where t.devicetype in ("+deviceType+") order by t.id) v where rownum=1";
		String alarmInstanceSql="select v.name from(select t.name from tbl_protocolalarminstance t where t.devicetype in ("+deviceType+") order by t.id) v where rownum=1";
		String reportInstanceSql="select v.name from(select t.name from tbl_protocolreportinstance t where t.devicetype in ("+deviceType+") order by t.id) v where rownum=1";
		
		List<?> acqInstanceList = this.findCallSql(acqInstanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		List<?> reportInstanceList = this.findCallSql(reportInstanceSql);
		
		if(acqInstanceList.size()>0 && acqInstanceList.get(0)!=null ){
			acqInstanceName=acqInstanceList.get(0).toString().replaceAll("null", "");
		}
		if(displayInstanceList.size()>0 && displayInstanceList.get(0)!=null ){
			displayInstanceName=displayInstanceList.get(0).toString().replaceAll("null", "");
		}
		if(alarmInstanceList.size()>0 && alarmInstanceList.get(0)!=null ){
			alarmInstanceName=alarmInstanceList.get(0).toString().replaceAll("null", "");
		}
		if(reportInstanceList.size()>0 && reportInstanceList.get(0)!=null ){
			reportInstanceName=reportInstanceList.get(0).toString().replaceAll("null", "");
		}
		return acqInstanceName+";"+displayInstanceName+";"+alarmInstanceName+";"+reportInstanceName;
	}
	
	@SuppressWarnings("rawtypes")
	public String doAuxiliaryDeviceShow(Map map,Page pager,String deviceType,int recordCount,String dictDeviceType,String language) {
		StringBuffer result_json = new StringBuffer();
		String ddicCode="auxiliaryDeviceManager";
		
		String columns=service.showTableHeadersColumns(ddicCode,dictDeviceType,language);
		String sql = "select t.id,t.specificType,t.name,t.manufacturer,t.model,t.remark,t.sort "
				+ " from tbl_auxiliarydevice t,tbl_devicetypeinfo t2"
				+ " where t.type=t2.id";
		if(StringManagerUtils.isNotNull(deviceType)){
			sql+= " and t.type="+deviceType;
		}
		sql+= " order by t.sort,t.name";
		
		String json = "";
		
		List<?> list = this.findCallSql(sql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"specificType\":\""+obj[1]+"\",");
			result_json.append("\"name\":\""+obj[2]+"\",");
			result_json.append("\"manufacturer\":\""+obj[3]+"\",");
			result_json.append("\"model\":\""+obj[4]+"\",");
			result_json.append("\"remark\":\""+obj[5]+"\",");
			result_json.append("\"sort\":\""+obj[6]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getAuxiliaryDeviceDetailsInfo(String deviceId,String auxiliaryDeviceSpecificType,String language) {
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"itemName\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("value")+"\",\"dataIndex\":\"itemValue\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"itemUnit\",width:80 ,children:[] }"
				+ "]";
		 
		int totalCount=0;
		StringBuffer totalRootBuffer = new StringBuffer();
		String sql = "select t.id,t.itemname,t.itemvalue,t.itemcode,t.itemunit "
				+ " from tbl_auxiliarydeviceaddinfo t,tbl_auxiliarydevice t2 "
				+ " where t.deviceid=t2.id "
				+  "and t.deviceid="+deviceId
				+  "and t2.specifictype="+auxiliaryDeviceSpecificType
				+ " order by t.id";
		List<?> list = this.findCallSql(sql);
		if(StringManagerUtils.stringToInteger(auxiliaryDeviceSpecificType)==1){
			String structureType="",stroke="",crankRotationDirection="",offsetAngleOfCrank="",crankGravityRadius="",singleCrankWeight="",singleCrankPinWeight="",structuralUnbalance="",balanceWeight="";
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Object[] obj = (Object[]) list.get(i);
					String itemName=obj[1]+"";
					String itemValue=obj[2]+"";
					String itemCode=obj[3]+"";
					String itemUnit=obj[4]+"";
					if("structureType".equalsIgnoreCase(itemCode)){
						if(StringManagerUtils.stringToInteger(itemValue)==1){
							structureType=languageResourceMap.get("pumpingUnitStructureType1");
						}else if(StringManagerUtils.stringToInteger(itemValue)==2){
							structureType=languageResourceMap.get("pumpingUnitStructureType2");
						}else if(StringManagerUtils.stringToInteger(itemValue)==3){
							structureType=languageResourceMap.get("pumpingUnitStructureType3");
						}
					}else if("stroke".equalsIgnoreCase(itemCode)){
						stroke=itemValue+"";
					}else if("crankRotationDirection".equalsIgnoreCase(itemCode)){
						if("Clockwise".equalsIgnoreCase(itemValue)){
							crankRotationDirection=languageResourceMap.get("clockwise");
						}else if("Anticlockwise".equalsIgnoreCase(itemValue)){
							crankRotationDirection=languageResourceMap.get("anticlockwise");
						}
					}else if("offsetAngleOfCrank".equalsIgnoreCase(itemCode)){
						offsetAngleOfCrank=itemValue;
					}else if("crankGravityRadius".equalsIgnoreCase(itemCode)){
						crankGravityRadius=itemValue;
					}else if("singleCrankWeight".equalsIgnoreCase(itemCode)){
						singleCrankWeight=itemValue;
					}else if("singleCrankPinWeight".equalsIgnoreCase(itemCode)){
						singleCrankPinWeight=itemValue;
					}else if("structuralUnbalance".equalsIgnoreCase(itemCode)){
						structuralUnbalance=itemValue;
					}else if("balanceWeight".equalsIgnoreCase(itemCode)){
						balanceWeight=itemValue;
					}
				}
			}
			
			totalRootBuffer.append("{\"id\":1,\"itemName\":\""+languageResourceMap.get("pumpingUnitStructure")+"\",\"itemValue\":\""+structureType+"\",\"itemUnit\":\"\",\"itemCode\":\"structureType\"},");
			totalRootBuffer.append("{\"id\":2,\"itemName\":\""+languageResourceMap.get("stroke")+"\",\"itemValue\":\""+stroke+"\",\"itemUnit\":\"m\",\"itemCode\":\"stroke\"},");
			totalRootBuffer.append("{\"id\":3,\"itemName\":\""+languageResourceMap.get("crankRotationDirection")+"\",\"itemValue\":\""+crankRotationDirection+"\",\"itemUnit\":\"\",\"itemCode\":\"crankRotationDirection\"},");
			totalRootBuffer.append("{\"id\":4,\"itemName\":\""+languageResourceMap.get("offsetAngleOfCrank")+"\",\"itemValue\":\""+offsetAngleOfCrank+"\",\"itemUnit\":\"°\",\"itemCode\":\"offsetAngleOfCrank\"},");
			totalRootBuffer.append("{\"id\":5,\"itemName\":\""+languageResourceMap.get("crankGravityRadius")+"\",\"itemValue\":\""+crankGravityRadius+"\",\"itemUnit\":\"m\",\"itemCode\":\"crankGravityRadius\"},");
			totalRootBuffer.append("{\"id\":6,\"itemName\":\""+languageResourceMap.get("singleCrankWeight")+"\",\"itemValue\":\""+singleCrankWeight+"\",\"itemUnit\":\"kN\",\"itemCode\":\"singleCrankWeight\"},");
			totalRootBuffer.append("{\"id\":7,\"itemName\":\""+languageResourceMap.get("singleCrankPinWeight")+"\",\"itemValue\":\""+singleCrankPinWeight+"\",\"itemUnit\":\"kN\",\"itemCode\":\"singleCrankPinWeight\"},");
			totalRootBuffer.append("{\"id\":8,\"itemName\":\""+languageResourceMap.get("structuralUnbalance")+"\",\"itemValue\":\""+structuralUnbalance+"\",\"itemUnit\":\"kN\",\"itemCode\":\"structuralUnbalance\"},");
			totalRootBuffer.append("{\"id\":9,\"itemName\":\""+languageResourceMap.get("balanceWeight")+"\",\"itemValue\":\""+balanceWeight+"\",\"itemUnit\":\"kN\",\"itemCode\":\"balanceWeight\"}");
			
			totalCount=9;
		}else{
			totalCount=20;
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				String itemName=obj[1]+"";
				String itemValue=obj[2]+"";
				String itemCode=obj[3]+"";
				String itemUnit=obj[4]+"";
				totalRootBuffer.append("{\"id\":"+obj[0]+",");
				totalRootBuffer.append("\"itemName\":\""+itemName+"\",");
				totalRootBuffer.append("\"itemValue\":\""+itemValue+"\",");
				totalRootBuffer.append("\"itemUnit\":\""+itemUnit+"\",");
				totalRootBuffer.append("\"itemCode\":\""+itemCode+"\"},");
			}
			for(int i=list.size();i<20;i++){
				totalRootBuffer.append("{},");
			}
			
			if(totalRootBuffer.toString().endsWith(",")){
				totalRootBuffer.deleteCharAt(totalRootBuffer.length() - 1);
			}
			
			if(list.size()>20){
				totalCount=list.size();
			}
		}
		if(totalRootBuffer.toString().endsWith(",")){
			totalRootBuffer.deleteCharAt(totalRootBuffer.length() - 1);
		}
		result_json.append("{\"success\":true,\"totalCount\":"+totalCount+",\"columns\":"+columns+",\"totalRoot\":[");
		result_json.append(totalRootBuffer.toString());
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getPumpingUnitDetailsInfo(String manufacturer,String model,String language) {
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"itemName\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("value")+"\",\"dataIndex\":\"itemValue\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"itemUnit\",width:80 ,children:[] }"
				+ "]";
		 
		int totalCount=0;
		StringBuffer totalRootBuffer = new StringBuffer();
		String sql = "select t.id,t.itemname,t.itemvalue,t.itemcode,t.itemunit "
				+ " from tbl_auxiliarydeviceaddinfo t,tbl_auxiliarydevice t2  "
				+ " where t.deviceid=t2.id "
				+ " and t2.id=( select t3.id from tbl_auxiliarydevice t3 where t3.manufacturer='"+manufacturer+"' and t3.model='"+model+"' and rownum=1 )"
				+  "and t2.specifictype=1"
				+ " order by t.id";
		List<?> list = this.findCallSql(sql);

		String structureType="",stroke="",crankRotationDirection="",offsetAngleOfCrank="",crankGravityRadius="",singleCrankWeight="",singleCrankPinWeight="",structuralUnbalance="",balanceWeight="";
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				String itemName=obj[1]+"";
				String itemValue=obj[2]+"";
				String itemCode=obj[3]+"";
				String itemUnit=obj[4]+"";
				
				itemName+=StringManagerUtils.isNotNull(itemUnit)?("("+itemUnit+")"):"";
				
				if("structureType".equalsIgnoreCase(itemCode)){
					if(StringManagerUtils.stringToInteger(itemValue)==1){
						structureType=languageResourceMap.get("pumpingUnitStructureType1");
					}else if(StringManagerUtils.stringToInteger(itemValue)==2){
						structureType=languageResourceMap.get("pumpingUnitStructureType2");
					}else if(StringManagerUtils.stringToInteger(itemValue)==3){
						structureType=languageResourceMap.get("pumpingUnitStructureType3");
					}
				}else if("stroke".equalsIgnoreCase(itemCode)){
					stroke=itemValue+"";
				}else if("crankRotationDirection".equalsIgnoreCase(itemCode)){
					if("Clockwise".equalsIgnoreCase(itemValue)){
						crankRotationDirection=languageResourceMap.get("clockwise");
					}else if("Anticlockwise".equalsIgnoreCase(itemValue)){
						crankRotationDirection=languageResourceMap.get("anticlockwise");
					}
				}else if("offsetAngleOfCrank".equalsIgnoreCase(itemCode)){
					offsetAngleOfCrank=itemValue;
				}else if("crankGravityRadius".equalsIgnoreCase(itemCode)){
					crankGravityRadius=itemValue;
				}else if("singleCrankWeight".equalsIgnoreCase(itemCode)){
					singleCrankWeight=itemValue;
				}else if("singleCrankPinWeight".equalsIgnoreCase(itemCode)){
					singleCrankPinWeight=itemValue;
				}else if("structuralUnbalance".equalsIgnoreCase(itemCode)){
					structuralUnbalance=itemValue;
				}else if("balanceWeight".equalsIgnoreCase(itemCode)){
					balanceWeight=itemValue;
				}
			}
		}
		
		totalRootBuffer.append("{\"id\":1,\"itemName\":\""+languageResourceMap.get("pumpingUnitStructure")+"\",\"itemValue\":\""+structureType+"\",\"itemUnit\":\"\",\"itemCode\":\"structureType\"},");
//		totalRootBuffer.append("{\"id\":2,\"itemName\":\""+languageResourceMap.get("stroke")+"\",\"itemValue\":\""+stroke+"\",\"itemUnit\":\"m\",\"itemCode\":\"strokeAll\"},");
		totalRootBuffer.append("{\"id\":2,\"itemName\":\""+languageResourceMap.get("crankRotationDirection")+"\",\"itemValue\":\""+crankRotationDirection+"\",\"itemUnit\":\"\",\"itemCode\":\"crankRotationDirection\"},");
		totalRootBuffer.append("{\"id\":3,\"itemName\":\""+languageResourceMap.get("offsetAngleOfCrank")+"\",\"itemValue\":\""+offsetAngleOfCrank+"\",\"itemUnit\":\"°\",\"itemCode\":\"offsetAngleOfCrank\"},");
		totalRootBuffer.append("{\"id\":4,\"itemName\":\""+languageResourceMap.get("crankGravityRadius")+"\",\"itemValue\":\""+crankGravityRadius+"\",\"itemUnit\":\"m\",\"itemCode\":\"crankGravityRadius\"},");
		totalRootBuffer.append("{\"id\":5,\"itemName\":\""+languageResourceMap.get("singleCrankWeight")+"\",\"itemValue\":\""+singleCrankWeight+"\",\"itemUnit\":\"kN\",\"itemCode\":\"singleCrankWeight\"},");
		totalRootBuffer.append("{\"id\":6,\"itemName\":\""+languageResourceMap.get("singleCrankPinWeight")+"\",\"itemValue\":\""+singleCrankPinWeight+"\",\"itemUnit\":\"kN\",\"itemCode\":\"singleCrankPinWeight\"},");
		totalRootBuffer.append("{\"id\":7,\"itemName\":\""+languageResourceMap.get("structuralUnbalance")+"\",\"itemValue\":\""+structuralUnbalance+"\",\"itemUnit\":\"kN\",\"itemCode\":\"structuralUnbalance\"},");
//		totalRootBuffer.append("{\"id\":9,\"itemName\":\""+languageResourceMap.get("balanceWeight")+"\",\"itemValue\":\""+balanceWeight+"\",\"itemUnit\":\"kN\",\"itemCode\":\"balanceWeight\"}");
		
		totalCount=9;
	
		if(totalRootBuffer.toString().endsWith(",")){
			totalRootBuffer.deleteCharAt(totalRootBuffer.length() - 1);
		}
		result_json.append("{\"success\":true,\"totalCount\":"+totalCount+",\"columns\":"+columns+",\"totalRoot\":[");
		result_json.append(totalRootBuffer.toString());
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	@SuppressWarnings("rawtypes")
	public String getBatchAddAuxiliaryDeviceTableInfo(int recordCount,String dictDeviceType,String language) {
		StringBuffer result_json = new StringBuffer();
		String ddicCode="auxiliaryDeviceManager";
		String columns=service.showTableHeadersColumns(ddicCode,dictDeviceType,language);
		String json = "";
		result_json.append("{\"success\":true,\"totalCount\":"+recordCount+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<recordCount;i++){
			result_json.append("{},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	@SuppressWarnings("rawtypes")
	public String getAuxiliaryDeviceExportData(Map map,Page pager,String deviceType,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		String sql = "select t.id,t.name,decode(t.type,1,'管辅件','泵辅件') as type,t.model,t.remark,t.sort from tbl_auxiliarydevice t where 1=1";
		if(StringManagerUtils.isNotNull(deviceType)){
			sql+= " and t.type="+deviceType;
		}
		sql+= " order by t.sort,t.name";
		
		String json = "";
		
		List<?> list = this.findCallSql(sql);
		
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"name\":\""+obj[1]+"\",");
			result_json.append("\"type\":\""+obj[2]+"\",");
			result_json.append("\"model\":\""+obj[3]+"\",");
			result_json.append("\"remark\":\""+obj[4]+"\",");
			result_json.append("\"sort\":\""+obj[5]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getAuxiliaryDevice(String deviceId,String deviceType,String language) {
		StringBuffer result_json = new StringBuffer();
		List<Integer> auxiliaryIdList=new ArrayList<Integer>();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"name\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("model")+"\",\"dataIndex\":\"model\",width:80 ,children:[] }"
				+ "]";
		String deviceTableName="tbl_device";
		
		String sql = "select t.id,t.name,t.type,t.manufacturer,t.model,"
				+ " t.remark,t.sort,"
				+ " decode(t.specificType,1,'"+languageResourceMap.get("pumping")+"','无') as specificTypeName,t.specificType"
				+ " from tbl_auxiliarydevice t where t.type="+deviceType;
		String auxiliarySql="select t2.auxiliaryid from "+deviceTableName+" t,tbl_auxiliary2master t2 "
				+ " where t.id=t2.masterid and t.id="+deviceId;
		
		sql+= " order by t.sort,t.name";
		
		String json = "";
		
		List<?> list = this.findCallSql(sql);
		List<?> auxiliaryList = this.findCallSql(auxiliarySql);
		for(int i=0;i<auxiliaryList.size();i++){
			auxiliaryIdList.add(StringManagerUtils.stringToInteger(auxiliaryList.get(i)+""));
		}
		
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			boolean checked=false;
			if(StringManagerUtils.existOrNot(auxiliaryIdList, StringManagerUtils.stringToInteger(obj[0]+""))){
				checked=true;
			}
			result_json.append("{\"checked\":"+checked+",");
			result_json.append("\"id\":\""+(i+1)+"\",");
			result_json.append("\"realId\":\""+obj[0]+"\",");
			result_json.append("\"name\":\""+obj[1]+"\",");
			result_json.append("\"manufacturer\":\""+obj[3]+"\",");
			result_json.append("\"model\":\""+obj[4]+"\",");
			result_json.append("\"specificTypeName\":\""+obj[7]+"\",");
			result_json.append("\"specificType\":\""+obj[8]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getDeviceAdditionalInfo(String deviceId,String deviceType,String language) {
		StringBuffer result_json = new StringBuffer();
		List<Integer> auxiliaryIdList=new ArrayList<Integer>();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"itemName\",width:120 ,children:[] },"
				+ "{ \"header\":\"值\",\"dataIndex\":\"itemValue\",width:120 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"itemUnit\",width:80 ,children:[] }"
				+ "]";
		String deviceTableName="tbl_device";
		String infoTableName="tbl_deviceaddinfo";
		String sql = "select t2.id,t2.itemname,t2.itemvalue,t2.itemunit,t2.overview,t2.overviewSort "
				+ " from "+deviceTableName+" t,"+infoTableName+" t2 "
				+ " where t.id=t2.deviceid and t.id="+deviceId
				+ " order by t2.id";
		
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"itemName\":\""+obj[1]+"\",");
			result_json.append("\"itemValue\":\""+obj[2]+"\",");
			result_json.append("\"itemUnit\":\""+obj[3]+"\",");
			result_json.append("\"overview\":"+(StringManagerUtils.stringToInteger(obj[4]+"")==1)+",");
			result_json.append("\"overviewSort\":\""+obj[5]+"\"},");
		}
		for(int i=list.size();i<20;i++){
			result_json.append("{},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public boolean judgeAuxiliaryDeviceExistOrNot(String name,String type,String manufacturer,String model) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(name)&&StringManagerUtils.isNotNull(type)&&StringManagerUtils.isNotNull(model)) {
			String sql = "select t.id from tbl_auxiliarydevice t "
					+ " where t.name='"+name+"' "
					+ " and t.type="+type
					+ " and t.model='"+model+"'"
					+ " and t.manufacturer='"+manufacturer+"'";
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public void doAuxiliaryDeviceAdd(AuxiliaryDeviceInformation auxiliaryDeviceInformation) throws Exception {
		getBaseDao().addObject(auxiliaryDeviceInformation);
	}
	
	public String saveAuxiliaryDeviceHandsontableData(AuxiliaryDeviceHandsontableChangedData auxiliaryDeviceHandsontableChangedData,int deviceType) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer collisionbuff = new StringBuffer();
		List<AuxiliaryDeviceHandsontableChangedData.Updatelist> list=getBaseDao().saveAuxiliaryDeviceHandsontableData(auxiliaryDeviceHandsontableChangedData,deviceType);
		int successCount=0;
		int collisionCount=0;
		collisionbuff.append("[");
		for(int i=0;i<list.size();i++){
			if(list.get(i).getSaveSign()==-22||list.get(i).getSaveSign()==-33){
				collisionCount++;
				collisionbuff.append("\""+list.get(i).getSaveStr()+"\",");
			}else if(list.get(i).getSaveSign()==0||list.get(i).getSaveSign()==1){
				successCount++;
			}
		}
		if(collisionbuff.toString().endsWith(",")){
			collisionbuff.deleteCharAt(collisionbuff.length() - 1);
		}
		collisionbuff.append("]");
		
		result_json.append("{\"success\":true,\"successCount\":"+successCount+",\"collisionCount\":"+collisionCount+",\"list\":"+collisionbuff+"}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public int updateAuxiliaryDeviceSpecificType(String deviceId,String auxiliaryDeviceSpecificType){
		String sql = "update tbl_auxiliarydevice t set t.specifictype="+auxiliaryDeviceSpecificType+" where t.id ="+deviceId;
		return this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public String batchAddAuxiliaryDevice(AuxiliaryDeviceHandsontableChangedData auxiliaryDeviceHandsontableChangedData,String isCheckout,String dictDeviceType,String language) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer overlayBuff = new StringBuffer();
		int overlayCount=0;
		String ddicCode="auxiliaryDeviceManager";
		String columns=service.showTableHeadersColumns(ddicCode,dictDeviceType,language);
		List<AuxiliaryDeviceHandsontableChangedData.Updatelist> list=getBaseDao().batchAddAuxiliaryDevice(auxiliaryDeviceHandsontableChangedData,StringManagerUtils.stringToInteger(isCheckout));
		
		overlayBuff.append("[");
		if(list!=null){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getSaveSign()==-33){//覆盖信息
					overlayCount+=1;
					overlayBuff.append("{\"id\":\""+list.get(i).getId()+"\",");
					overlayBuff.append("\"name\":\""+list.get(i).getName()+"\",");
					overlayBuff.append("\"type\":\""+list.get(i).getType()+"\",");
					overlayBuff.append("\"model\":\""+list.get(i).getModel()+"\",");
					overlayBuff.append("\"remark\":\""+list.get(i).getRemark()+"\",");
					overlayBuff.append("\"sort\":\""+list.get(i).getSort()+"\",");
					overlayBuff.append("\"dataInfo\":\""+list.get(i).getSaveStr()+"\"},");
				}
			}
			if (overlayBuff.toString().endsWith(",")) {
				overlayBuff.deleteCharAt(overlayBuff.length() - 1);
			}
		}
		overlayBuff.append("]");
		result_json.append("{\"success\":true,\"overlayCount\":"+overlayCount+","+ "\"columns\":"+columns+",\"overlayList\":"+overlayBuff+"}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public int getDeviceCalculateType(String deviceId){
		int calculateType=0;
		try{
			String deviceTableName="tbl_device";
			
			String sql="select t.calculatetype from "+deviceTableName+" t where t.id="+deviceId;
			List<?> list = this.findCallSql(sql);
			if(list.size()>0){
				calculateType=StringManagerUtils.stringToInteger(list.get(0)+"");
			}
		}catch(Exception e){
			calculateType=0;
		}
		return calculateType;
	}
	
	public int getApplicationScenariosType(String deviceId){
		int applicationScenarios=0;
		try{
			String deviceTableName="tbl_device";
			
			String sql="select t.applicationscenarios from "+deviceTableName+" t where t.id="+deviceId;
			List<?> list = this.findCallSql(sql);
			if(list.size()>0){
				applicationScenarios=StringManagerUtils.stringToInteger(list.get(0)+"");
			}
		}catch(Exception e){
			applicationScenarios=0;
		}
		return applicationScenarios;
	}
}
