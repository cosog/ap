package com.cosog.service.back;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import com.cosog.model.AuxiliaryDeviceInformation;
import com.cosog.model.DeviceInformation;
import com.cosog.model.PumpingModelInformation;
import com.cosog.model.MasterAndAuxiliaryDevice;
import com.cosog.model.PcpDeviceInformation;
import com.cosog.model.DeviceAddInfo;
import com.cosog.model.RpcDeviceInformation;
import com.cosog.model.SmsDeviceInformation;
import com.cosog.model.User;
import com.cosog.model.VideoKey;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AlarmInstanceOwnItem;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.PCPProductionData;
import com.cosog.model.calculate.PumpingPRTFData;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.RPCDeviceInfo;
import com.cosog.model.calculate.RPCProductionData;
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
	
	public String loadWellComboxList(Page pager,String orgId,String wellName,String deviceTypeStr) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		String tableName="tbl_device";
		if(deviceType>=300){
			tableName="tbl_smsdevice";
		}
		String sql = " select  t.deviceName as deviceName,t.deviceName as dm from  "+tableName+" t  ,tbl_org  g where 1=1 and  t.orgId=g.org_id  and g.org_id in ("
				+ orgId + ")";
		if (StringManagerUtils.isNotNull(wellName)) {
			sql += " and t.deviceName like '%" + wellName + "%'";
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
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"},");
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
	
	public String loadRPCDeviceComboxList(Page pager,String orgId,String wellName,String deviceTypeStr) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		String tableName="tbl_rpcdevice";
		if(deviceType>=200&&deviceType<300){
			tableName="tbl_pcpdevice";
		}else if(deviceType>=300){
			tableName="tbl_smsdevice";
		}
		if(deviceType==1){
			tableName="tbl_pcpdevice";
		}else if(deviceType==2){
			tableName="tbl_smsdevice";
		}
		String sql = " select  t.wellName as wellName,t.wellName as dm from  "+tableName+" t  ,tbl_org  g,tbl_protocolinstance t2 "
				+ " where t.orgId=g.org_id and t.instancecode=t2.code  "
				+ " and g.org_id in ("+ orgId + ")"
				+ " and ( t2.acqprotocoltype ='private-rpc' or t2.ctrlprotocoltype ='private-rpc')";
		if(StringManagerUtils.isNotNull(deviceTypeStr) && deviceType>=100 && StringManagerUtils.isNotNull(deviceTypeStr) && deviceType<300){
			sql += " and t.deviceType ="+deviceType;
		}
		if (StringManagerUtils.isNotNull(wellName)) {
			sql += " and t.wellName like '%" + wellName + "%'";
		}
		sql += " order by t.sortNum, t.wellName";
		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
		sqlCuswhere.append(""+sql);
		int maxvalue=pager.getLimit()+pager.getStart();
		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
		sqlCuswhere.append(" where rn >"+pager.getStart());
		String finalsql=sqlCuswhere.toString();
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(finalsql);
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"},");
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
	
	public String loadPumpingManufacturerComboxList(String manufacturer) {
		StringBuffer result_json = new StringBuffer();
		String sql = " select distinct(t.manufacturer) from tbl_pumpingmodel t where 1=1";
		
		if (StringManagerUtils.isNotNull(manufacturer)) {
			sql += " and t.manufacturer like '%"+manufacturer+"%'";
		}
		sql += " order by t.manufacturer";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"totals\":"+list.size()+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"},");
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
	
	public String loadPumpingModelComboxList(String manufacturer,String model) {
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
		result_json.append("{\"totals\":"+list.size()+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"},");
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
	
	public String getDeviceOrgChangeDeviceList(Page pager,String orgId,String wellName,String deviceTypeStr) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		String tableName="tbl_rpcdevice";
		if(deviceType>=200&&deviceType<300){
			tableName="tbl_pcpdevice";
		}else if(deviceType>=300){
			tableName="tbl_smsdevice";
		}
		String sql = " select  t.id,t.wellName from  "+tableName+" t where t.orgid in ("+ orgId + ")"
				+ " and t.deviceType ="+deviceType;
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and t.wellname like '%"+wellName+"%'";
		}	
		sql+= " order by t.sortNum, t.wellName";
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"wellName\",width:120 ,children:[] }"
				+ "]";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");

		for (Object o : list) {
			Object[] obj = (Object[]) o;
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"wellName\":\""+obj[1]+"\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getApplicationScenariosList() throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		String sql = "select t.itemvalue,t.itemname from TBL_CODE t where t.itemcode='APPLICATIONSCENARIOS' order by t.itemvalue desc";
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"应用场景\",\"dataIndex\":\"applicationScenariosName\",width:120 ,children:[] }"
				+ "]";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");

		for (Object o : list) {
			Object[] obj = (Object[]) o;
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"applicationScenarios\":"+obj[0]+",");
			result_json.append("\"applicationScenariosName\":\""+obj[1]+"\"},");
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
			String tableName="tbl_rpcdevice";
			if(deviceType>=200&&deviceType<300){
				tableName="tbl_pcpdevice";
			}else if(deviceType>=300){
				tableName="tbl_smsdevice";
			}
			String sql = "update "+tableName+" t set t.orgid="+selectedOrgId+" where t.id in ("+selectedDeviceId+")";
			this.getBaseDao().updateOrDeleteBySql(sql);
			
			Jedis jedis=null;
			try{
				try{
					jedis = RedisUtil.jedisPool.getResource();
				}catch(Exception e){
					e.printStackTrace();
				}
				if(deviceType>=100&&deviceType<200){
					if(!jedis.exists("DeviceInfo".getBytes())){
						MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
					}
					List<byte[]> deviceInfoByteList =jedis.hvals("DeviceInfo".getBytes());
					for(int i=0;i<deviceInfoByteList.size();i++){
						Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
						if (obj instanceof RPCDeviceInfo) {
							RPCDeviceInfo deviceInfo=(RPCDeviceInfo)obj;
							if(StringManagerUtils.existOrNot(selectedDeviceId.split(","), deviceInfo.getId()+"", false)){
								deviceInfo.setOrgId(StringManagerUtils.stringToInteger(selectedOrgId));
								deviceInfo.setOrgName(selectedOrgName);
								jedis.hset("DeviceInfo".getBytes(), (deviceInfo.getId()+"").getBytes(), SerializeObjectUnils.serialize(deviceInfo));
							}
						}
					}
				}else if(deviceType>=200&&deviceType<300){
					if(!jedis.exists("PCPDeviceInfo".getBytes())){
						MemoryDataManagerTask.loadDeviceInfo(null,0,"update");
					}
					List<byte[]> deviceInfoByteList =jedis.hvals("PCPDeviceInfo".getBytes());
					for(int i=0;i<deviceInfoByteList.size();i++){
						Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
						if (obj instanceof PCPDeviceInfo) {
							PCPDeviceInfo deviceInfo=(PCPDeviceInfo)obj;
							if(StringManagerUtils.existOrNot(selectedDeviceId.split(","), deviceInfo.getId()+"", false)){
								deviceInfo.setOrgId(StringManagerUtils.stringToInteger(selectedOrgId));
								deviceInfo.setOrgName(selectedOrgName);
								jedis.hset("PCPDeviceInfo".getBytes(), (deviceInfo.getId()+"").getBytes(), SerializeObjectUnils.serialize(deviceInfo));
							}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(jedis!=null){
					jedis.close();
				}
			}
		}
	}
	
	public String getAcqInstanceCombList(String deviceTypeStr){
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		StringBuffer result_json = new StringBuffer();
		int protocolType=0;
		if((deviceType>=200&&deviceType<300)||deviceType==1){
			protocolType=1;
		}
		
		String sql="select t.code,t.name from tbl_protocolinstance t  order by t.sort";
		
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
	
	public String getDisplayInstanceCombList(String deviceTypeStr){
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		StringBuffer result_json = new StringBuffer();
		int protocolType=0;
		if((deviceType>=200&&deviceType<300)||deviceType==1){
			protocolType=1;
		}
		
		String sql="select t.code,t.name from tbl_protocoldisplayinstance t order by t.sort";
		
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
	
	public String getAlarmInstanceCombList(String deviceTypeStr){
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		StringBuffer result_json = new StringBuffer();
		int protocolType=0;
		if((deviceType>=200&&deviceType<300)||deviceType==1){
			protocolType=1;
		}
		
		String sql="select t.code,t.name from tbl_protocolalarminstance t  order by t.sort";
		
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
	
	public String loadDeviceTypeComboxList() throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		String sql = "select t.itemvalue,t.itemname from TBL_CODE t where upper(t.itemcode)=upper('deviceType') order by t.itemvalue ";
		
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(sql);
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"},");
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
	
	public String loadDataDictionaryComboxList(String itemCode) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		String sql = "select t.itemvalue,t.itemname from TBL_CODE t where upper(t.itemcode)=upper('"+itemCode+"') order by t.itemvalue ";
		
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(sql);
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"},");
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
	
//	public void saveWellEditerGridData(WellHandsontableChangedData wellHandsontableChangedData,String orgId,int deviceType,User user) throws Exception {
//		getBaseDao().saveWellEditerGridData(wellHandsontableChangedData,orgId,deviceType,user);
//	}
	
	public String saveDeviceData(WellInformationManagerService<?> wellInformationManagerService,WellHandsontableChangedData wellHandsontableChangedData,String orgId,int deviceType,User user) throws Exception {
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
	
	public String saveRPCDeviceData(WellInformationManagerService<?> wellInformationManagerService,WellHandsontableChangedData wellHandsontableChangedData,String orgId,int deviceType,User user) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer collisionbuff = new StringBuffer();
		List<WellHandsontableChangedData.Updatelist> list=getBaseDao().saveRPCDeviceData(wellInformationManagerService,wellHandsontableChangedData,orgId,deviceType,user);
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
	
	public String batchAddRPCDevice(WellInformationManagerService<?> wellInformationManagerService,WellHandsontableChangedData wellHandsontableChangedData,
			String orgId,int deviceType,String applicationScenarios,String isCheckout,User user) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer  collisionBuff = new StringBuffer();
		StringBuffer overlayBuff = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer displayInstanceDropdownData = new StringBuffer();
		StringBuffer reportInstanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		StringBuffer applicationScenariosDropdownData = new StringBuffer();
		StringBuffer resultNameDropdownData = new StringBuffer();
		int collisionCount=0;
		int overlayCount=0;
		int overCount=0;
		String ddicName="deviceInfo_RPCDeviceBatchAdd";
		String columns=service.showTableHeadersColumns(ddicName);
		List<WellHandsontableChangedData.Updatelist> list=getBaseDao().batchAddRPCDevice(wellInformationManagerService,wellHandsontableChangedData,orgId,deviceType,applicationScenarios,isCheckout,user);
		String instanceSql="select t.name from tbl_protocolinstance t where t.devicetype=0 order by t.sort";
		String displayInstanceSql="select t.name from tbl_protocoldisplayinstance t where t.devicetype=0 order by t.sort";
		String reportInstanceSql="select t.name from tbl_protocolreportinstance t where t.devicetype=0 order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t where t.devicetype=0 order by t.sort";
		String applicationScenariosSql="select c.itemname from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' order by c.itemvalue desc";
		String resultSql="select t.resultname from tbl_rpc_worktype t order by t.resultcode";
		instanceDropdownData.append("[");
		displayInstanceDropdownData.append("[");
		reportInstanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");
		applicationScenariosDropdownData.append("[");
		resultNameDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
		List<?> reportInstanceList = this.findCallSql(reportInstanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		List<?> applicationScenariosList = this.findCallSql(applicationScenariosSql);
		List<?> resultList = this.findCallSql(resultSql);
		
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
		
		
		for(int i=0;i<applicationScenariosList.size();i++){
			applicationScenariosDropdownData.append("'"+applicationScenariosList.get(i)+"',");
		}
		if(applicationScenariosDropdownData.toString().endsWith(",")){
			applicationScenariosDropdownData.deleteCharAt(applicationScenariosDropdownData.length() - 1);
		}
		
		if(resultList.size()>0){
			resultNameDropdownData.append("\"不干预\"");
			for(int i=0;i<resultList.size();i++){
				resultNameDropdownData.append(",\""+resultList.get(i).toString()+"\"");
			}
		}
		
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
					collisionBuff.append("{\"id\":\""+list.get(i).getId()+"\",");
					collisionBuff.append("\"wellName\":\""+list.get(i).getDeviceName()+"\",");
					collisionBuff.append("\"applicationScenariosName\":\""+list.get(i).getApplicationScenariosName()+"\",");
					collisionBuff.append("\"instanceName\":\""+list.get(i).getInstanceName()+"\",");
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
					overlayBuff.append("{\"id\":\""+list.get(i).getId()+"\",");
					overlayBuff.append("\"wellName\":\""+list.get(i).getDeviceName()+"\",");
					overlayBuff.append("\"applicationScenariosName\":\""+list.get(i).getApplicationScenariosName()+"\",");
					overlayBuff.append("\"instanceName\":\""+list.get(i).getInstanceName()+"\",");
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
		
		String pumpingModelInfo=this.getPumpingModelInfo();
		result_json.append("{\"success\":true,"
				+ "\"applicationScenarios\":"+applicationScenarios+","
				+ "\"collisionCount\":"+collisionCount+","
				+ "\"overlayCount\":"+overlayCount+","
				+ "\"overCount\":"+overCount+","
				+ "\"instanceDropdownData\":"+instanceDropdownData.toString()+","
				+ "\"displayInstanceDropdownData\":"+displayInstanceDropdownData.toString()+","
				+ "\"reportInstanceDropdownData\":"+reportInstanceDropdownData.toString()+","
				+ "\"alarmInstanceDropdownData\":"+alarmInstanceDropdownData.toString()+","
				+ "\"applicationScenariosDropdownData\":"+applicationScenariosDropdownData.toString()+","
				+ "\"resultNameDropdownData\":"+resultNameDropdownData.toString()+","
				+ "\"pumpingModelInfo\":"+pumpingModelInfo+","
				+ "\"columns\":"+columns+","
				+ "\"collisionList\":"+collisionBuff+","
				+ "\"overlayList\":"+overlayBuff+"}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String savePCPDeviceData(WellInformationManagerService<?> wellInformationManagerService,WellHandsontableChangedData wellHandsontableChangedData,String orgId,int deviceType,User user) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer collisionbuff = new StringBuffer();
		List<WellHandsontableChangedData.Updatelist> list=getBaseDao().savePCPDeviceData(wellInformationManagerService,wellHandsontableChangedData,orgId,deviceType,user);
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
	
	public String batchAddPCPDevice(WellInformationManagerService<?> wellInformationManagerService,WellHandsontableChangedData wellHandsontableChangedData,
			String orgId,int deviceType,String applicationScenarios,String isCheckout,User user) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer  collisionBuff = new StringBuffer();
		StringBuffer overlayBuff = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer displayInstanceDropdownData = new StringBuffer();
		StringBuffer reportInstanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		StringBuffer applicationScenariosDropdownData = new StringBuffer();
		int collisionCount=0;
		int overlayCount=0;
		int overCount=0;
		String ddicName="deviceInfo_PCPDeviceBatchAdd";
		String columns=service.showTableHeadersColumns(ddicName);
		List<WellHandsontableChangedData.Updatelist> list=getBaseDao().batchAddPCPDevice(wellInformationManagerService,wellHandsontableChangedData,orgId,deviceType,applicationScenarios,isCheckout,user);
		String instanceSql="select t.name from tbl_protocolinstance t where t.devicetype=1 order by t.sort";
		String displayInstanceSql="select t.name from tbl_protocoldisplayinstance t where t.devicetype=1 order by t.sort";
		String reportInstanceSql="select t.name from tbl_protocoldisplayinstance t where t.devicetype=1 order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t where t.devicetype=1 order by t.sort";
		String applicationScenariosSql="select c.itemname from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' order by c.itemvalue desc";
		instanceDropdownData.append("[");
		displayInstanceDropdownData.append("[");
		reportInstanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");
		applicationScenariosDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
		List<?> reportInstanceList = this.findCallSql(reportInstanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		List<?> applicationScenariosList = this.findCallSql(applicationScenariosSql);
		
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
		
		
		for(int i=0;i<applicationScenariosList.size();i++){
			applicationScenariosDropdownData.append("'"+applicationScenariosList.get(i)+"',");
		}
		if(applicationScenariosDropdownData.toString().endsWith(",")){
			applicationScenariosDropdownData.deleteCharAt(applicationScenariosDropdownData.length() - 1);
		}
		instanceDropdownData.append("]");
		displayInstanceDropdownData.append("]");
		reportInstanceDropdownData.append("]");
		alarmInstanceDropdownData.append("]");
		applicationScenariosDropdownData.append("]");
		collisionBuff.append("[");
		overlayBuff.append("[");
		if(list!=null){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getSaveSign()==-22){//冲突
					collisionCount+=1;
					collisionBuff.append("{\"id\":\""+list.get(i).getId()+"\",");
					collisionBuff.append("\"wellName\":\""+list.get(i).getDeviceName()+"\",");
					collisionBuff.append("\"applicationScenariosName\":\""+list.get(i).getApplicationScenariosName()+"\",");
					collisionBuff.append("\"instanceName\":\""+list.get(i).getInstanceName()+"\",");
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
		        	
		        	collisionBuff.append("\"barrelLength\":\""+list.get(i).getBarrelLength()+"\",");
		        	collisionBuff.append("\"barrelSeries\":\""+list.get(i).getBarrelSeries()+"\",");
		        	collisionBuff.append("\"rotorDiameter\":\""+list.get(i).getRotorDiameter()+"\",");
		        	collisionBuff.append("\"QPR\":\""+list.get(i).getQPR()+"\",");
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
		        	collisionBuff.append("\"netGrossRatio\":\""+list.get(i).getNetGrossRatio()+"\",");
		        	collisionBuff.append("\"netGrossValue\":\""+list.get(i).getNetGrossValue()+"\",");
					
					
					collisionBuff.append("\"dataInfo\":\""+list.get(i).getSaveStr()+"\"},");
				}else if(list.get(i).getSaveSign()==-33){//覆盖信息
					overlayCount+=1;
					overlayBuff.append("{\"id\":\""+list.get(i).getId()+"\",");
					overlayBuff.append("\"wellName\":\""+list.get(i).getDeviceName()+"\",");
					overlayBuff.append("\"applicationScenariosName\":\""+list.get(i).getApplicationScenariosName()+"\",");
					overlayBuff.append("\"instanceName\":\""+list.get(i).getInstanceName()+"\",");
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
		        	
					overlayBuff.append("\"barrelLength\":\""+list.get(i).getBarrelLength()+"\",");
					overlayBuff.append("\"barrelSeries\":\""+list.get(i).getBarrelSeries()+"\",");
					overlayBuff.append("\"rotorDiameter\":\""+list.get(i).getRotorDiameter()+"\",");
					overlayBuff.append("\"QPR\":\""+list.get(i).getQPR()+"\",");
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
					overlayBuff.append("\"netGrossRatio\":\""+list.get(i).getNetGrossRatio()+"\",");
					overlayBuff.append("\"netGrossValue\":\""+list.get(i).getNetGrossValue()+"\",");
		        	
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
		result_json.append("{\"success\":true,"
				+ "\"applicationScenarios\":"+applicationScenarios+","
				+ "\"collisionCount\":"+collisionCount+","
				+ "\"overlayCount\":"+overlayCount+","
				+ "\"overCount\":"+overCount+","
				+ "\"instanceDropdownData\":"+instanceDropdownData.toString()+","
				+ "\"displayInstanceDropdownData\":"+displayInstanceDropdownData.toString()+","
				+ "\"reportInstanceDropdownData\":"+reportInstanceDropdownData.toString()+","
				+ "\"alarmInstanceDropdownData\":"+alarmInstanceDropdownData.toString()+","
				+ "\"applicationScenariosDropdownData\":"+applicationScenariosDropdownData.toString()+","
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
	
	public void doRPCDeviceAdd(RpcDeviceInformation rpcDeviceInformation) throws Exception {
		getBaseDao().addObject(rpcDeviceInformation);
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
	
	public void grantMasterAuxiliaryDevice(MasterAndAuxiliaryDevice r) throws Exception {
		getBaseDao().saveOrUpdateObject(r);
	}
	
	public void saveProductionData(int deviceType,int deviceId,String deviceProductionData,int deviceCalculateDataType) throws Exception {
		String tableName="tbl_device";
		String time=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String sql = "update "+tableName+" t "
				+ " set t.productiondata='"+deviceProductionData+"',"
				+ " t.calculatetype="+deviceCalculateDataType+","
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
	
	public void saveRPCPumpingModel(int deviceId,String pumpingModelId) throws Exception {
		String sql = "update tbl_device t set t.pumpingmodelid="+pumpingModelId+" where t.id="+deviceId;
		if(!StringManagerUtils.isNotNull(pumpingModelId)){
			sql = "update tbl_device t set t.pumpingmodelid=null where t.id="+deviceId;
		}
		this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public void savePumpingInfo(int deviceId,String strokeStr,String balanceInfo) throws Exception {
		if(!StringManagerUtils.isNumber(strokeStr)){
			strokeStr="null";
		}
		String sql = "update tbl_device t set t.stroke="+strokeStr+",t.balanceinfo='"+balanceInfo+"' where t.id="+deviceId;
		this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public void saveDeviceAdditionalInfo(DeviceAddInfo r) throws Exception {
		getBaseDao().saveOrUpdateObject(r);
	}
	
	public String savePumpingModelHandsontableData(PumpingModelHandsontableChangedData pumpingModelHandsontableChangedData,String selectedRecordId) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer collisionbuff = new StringBuffer();
		List<PumpingModelHandsontableChangedData.Updatelist> list=getBaseDao().savePumpingModelHandsontableData(pumpingModelHandsontableChangedData,selectedRecordId);
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
	
	public String savePumpingPRTFData(String recordId,String data) throws Exception {
		String result = "{success:true,msg:true}";
		try{
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			type = new TypeToken<PumpingPRTFData.EveryStroke>() {}.getType();
			PumpingPRTFData.EveryStroke everyStroke=gson.fromJson(data, type);
			if(everyStroke!=null){
				String sql = "select t.prtf from tbl_pumpingmodel t where t.id="+recordId;
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
					String updatePRTFClobSql="update tbl_pumpingmodel t set t.prtf=? where t.id="+recordId;
					getBaseDao().executeSqlUpdateClob(updatePRTFClobSql,clobCont);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "{success:false,msg:false}";
		}
		return result;
	}
	
	public String batchAddPumpingModel(PumpingModelHandsontableChangedData auxiliaryDeviceHandsontableChangedData,String isCheckout) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer overlayBuff = new StringBuffer();
		int overlayCount=0;
		String ddicName="pumpingDevice_PumpingModelManager";
		String columns=service.showTableHeadersColumns(ddicName);
		List<PumpingModelHandsontableChangedData.Updatelist> list=getBaseDao().batchAddPumpingModel(auxiliaryDeviceHandsontableChangedData,StringManagerUtils.stringToInteger(isCheckout));
		
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
	
	public void editRPCDeviceName(String oldWellName,String newWellName,String orgid) throws Exception {
		getBaseDao().editRPCDeviceName(oldWellName,newWellName,orgid);
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

	public List<T> loadWellOrgInfo() {
		String queryString = "SELECT distinct(o.orgName) as orgName ,o.orgCode FROM WellInformation u ,Org o where u.dwbh=o.org_code  order by o.orgCode";
		return getBaseDao().find(queryString);
	}

	public String showWellTypeTree() throws Exception {
		String sql = "select t.dm as id,t.itemname as text from tbl_code t where t.itemcode='JLX'";
		List<?> list = this.findCallSql(sql);
		StringBuffer result_json = new StringBuffer();
		String get_key = "";
		String get_val = "";
		result_json.append("[");
		if (null != list && list.size() > 0) {
			for (Object o : list) {
				Object[] obj = (Object[]) o;
				get_key = obj[0] + "";
				get_val = obj[1] + "";
				if (get_key.equalsIgnoreCase("100") || get_key.equalsIgnoreCase("200")) {
					 if(get_key.equalsIgnoreCase( "200")){
						result_json.deleteCharAt(result_json.length() - 1);
						result_json.append("]}]} ,");
					}
					result_json.append("{");
					result_json.append("id:'" + get_key + "',");
					result_json.append("text:'" + get_val + "',");
					result_json.append("expanded:true,");
					result_json.append("children:[");
				} else if (get_key.equalsIgnoreCase( "101") || get_key .equalsIgnoreCase("111")) {
					if(get_key .equalsIgnoreCase( "111")){
						result_json.deleteCharAt(result_json.length() - 1);
						result_json.append("]},");
					}
					result_json.append("{");
					if(get_key.equalsIgnoreCase( "101")||get_key.equalsIgnoreCase( "111")){
						result_json.append("id:'" + get_key + "_p',");
					}else{
					    result_json.append("id:'" + get_key + "',");
					}
					result_json.append("text:'" + get_val + "',");
					result_json.append("expanded:true,");
					result_json.append("children:[");
					if(get_key .equalsIgnoreCase( "101")){
						result_json.append("{id:'" + get_key + "',");
						result_json.append("text:'" + get_val + "',");
						result_json.append("leaf:true },");
					}else  if(get_key .equalsIgnoreCase( "111")){
						result_json.append("{id:'" + get_key + "',");
						result_json.append("text:'" + get_val + "',");
						result_json.append("leaf:true },");
					}
				} else if (get_key.startsWith("10") || get_key.startsWith("11")
						|| get_key.startsWith("20")) {
					result_json.append("{id:'" + get_key + "',");
					result_json.append("text:'" + get_val + "',");
					result_json.append("leaf:true },");
				}
			}
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]}]");
		String da="100_p";
		da.substring(0, 3);
			
		return result_json.toString();

	}

	/**
	 * <p>
	 * 描述：加载组织类型的下拉菜单数据信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loadSszcdyType(String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		sql = " select t.itemvalue,t.itemname from tbl_code t where  itemcode='SSZCDY'";
		try {
			List<?> list = this.find(sql);
			result_json.append("[");
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
			result_json.append("]");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}

//	public List<T> fingWellByJhList() throws Exception {
//		String sql = " select  distinct (wellName) from tbl_wellinformation w  order by sortNum ";
//		return this.getBaseDao().find(sql);
//	}
	
	@SuppressWarnings("rawtypes")
	public String getRPCDeviceInfoList(Map map,Page pager,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer displayInstanceDropdownData = new StringBuffer();
		StringBuffer reportInstanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		StringBuffer applicationScenariosDropdownData = new StringBuffer();
		String ddicName="deviceInfo_DeviceManager";
		String tableName="viw_rpcdevice";
		int protocolType=0;
		String wellInformationName = (String) map.get("wellInformationName");
		int deviceType=StringManagerUtils.stringToInteger((String) map.get("deviceType"));
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		if (StringManagerUtils.isNotNull(wellInformationName)) {
			WellInformation_Str = " and t.wellname like '%" + wellInformationName+ "%'";
		}
		
		String columns=service.showTableHeadersColumns(ddicName);
		String sql = "select id,orgName,wellName,applicationScenariosName,"
				+ " instanceName,displayInstanceName,alarmInstanceName,reportInstanceName,"
				+ " tcptype,signInId,ipport,slave,t.peakdelay,"
				+ " sortNum,status,statusName,allpath,to_char(productiondataupdatetime,'yyyy-mm-dd hh24:mi:ss') as productiondataupdatetime"
				+ " from "+tableName+" t "
				+ " where 1=1"
				+ WellInformation_Str;
		sql+= " and t.orgid in ("+orgId+" )";
		sql+= " and t.devicetype="+deviceType;
		sql+= " order by t.sortnum,t.wellname ";
		String instanceSql="select t.name from tbl_protocolinstance t  order by t.sort";
		String displayInstanceSql="select t.name from tbl_protocoldisplayinstance t  order by t.sort";
		String reportInstanceSql="select t.name from tbl_protocolreportinstance t  order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t  order by t.sort";
		String applicationScenariosSql="select c.itemname from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' order by c.itemvalue desc";
		
		
		instanceDropdownData.append("[");
		displayInstanceDropdownData.append("[");
		reportInstanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");
		applicationScenariosDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
		List<?> reportInstanceList = this.findCallSql(reportInstanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		List<?> applicationScenariosList = this.findCallSql(applicationScenariosSql);
		
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
		
		
		for(int i=0;i<applicationScenariosList.size();i++){
			applicationScenariosDropdownData.append("'"+applicationScenariosList.get(i)+"',");
		}
		if(applicationScenariosDropdownData.toString().endsWith(",")){
			applicationScenariosDropdownData.deleteCharAt(applicationScenariosDropdownData.length() - 1);
		}
		instanceDropdownData.append("]");
		displayInstanceDropdownData.append("]");
		reportInstanceDropdownData.append("]");
		alarmInstanceDropdownData.append("]");
		applicationScenariosDropdownData.append("]");
		
		String json = "";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+","
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
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"applicationScenariosName\":\""+obj[3]+"\",");
			result_json.append("\"instanceName\":\""+obj[4]+"\",");
			result_json.append("\"displayInstanceName\":\""+obj[5]+"\",");
			result_json.append("\"alarmInstanceName\":\""+obj[6]+"\",");
			result_json.append("\"reportInstanceName\":\""+obj[7]+"\",");
			result_json.append("\"tcpType\":\""+(obj[8]+"").replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
			result_json.append("\"signInId\":\""+obj[9]+"\",");
			result_json.append("\"ipPort\":\""+obj[10]+"\",");
			result_json.append("\"slave\":\""+obj[11]+"\",");
			result_json.append("\"peakDelay\":\""+obj[12]+"\",");
			result_json.append("\"status\":\""+obj[14]+"\",");
			result_json.append("\"statusName\":\""+obj[15]+"\",");
			result_json.append("\"allPath\":\""+obj[16]+"\",");
			result_json.append("\"productionDataUpdateTime\":\""+obj[17]+"\",");
			result_json.append("\"sortNum\":\""+obj[13]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	@SuppressWarnings("rawtypes")
	public String getDeviceInfoList(Map map,Page pager,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer displayInstanceDropdownData = new StringBuffer();
		StringBuffer reportInstanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		StringBuffer applicationScenariosDropdownData = new StringBuffer();
		String ddicName="deviceInfo_DeviceManager";
		String tableName="viw_device";
		String wellInformationName = (String) map.get("wellInformationName");
		int deviceType=StringManagerUtils.stringToInteger((String) map.get("deviceType"));
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		if (StringManagerUtils.isNotNull(wellInformationName)) {
			WellInformation_Str = " and t.devicename like '%" + wellInformationName+ "%'";
		}
		
		String columns=service.showTableHeadersColumns(ddicName);
		String sql = "select id,orgName,deviceName,applicationScenariosName,"
				+ " instanceName,displayInstanceName,alarmInstanceName,reportInstanceName,"
				+ " tcptype,signInId,ipport,slave,t.peakdelay,"
				+ " sortNum,status,statusName,allpath,to_char(productiondataupdatetime,'yyyy-mm-dd hh24:mi:ss') as productiondataupdatetime"
				+ " from "+tableName+" t "
				+ " where 1=1"
				+ WellInformation_Str;
		sql+= " and t.orgid in ("+orgId+" )";
		sql+= " and t.devicetype="+deviceType;
		sql+= " order by t.sortnum,t.devicename ";
		String instanceSql="select t.name from tbl_protocolinstance t  order by t.sort";
		String displayInstanceSql="select t.name from tbl_protocoldisplayinstance t  order by t.sort";
		String reportInstanceSql="select t.name from tbl_protocolreportinstance t  order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t  order by t.sort";
		String applicationScenariosSql="select c.itemname from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' order by c.itemvalue desc";
		
		
		instanceDropdownData.append("[");
		displayInstanceDropdownData.append("[");
		reportInstanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");
		applicationScenariosDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
		List<?> reportInstanceList = this.findCallSql(reportInstanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		List<?> applicationScenariosList = this.findCallSql(applicationScenariosSql);
		
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
		
		
		for(int i=0;i<applicationScenariosList.size();i++){
			applicationScenariosDropdownData.append("'"+applicationScenariosList.get(i)+"',");
		}
		if(applicationScenariosDropdownData.toString().endsWith(",")){
			applicationScenariosDropdownData.deleteCharAt(applicationScenariosDropdownData.length() - 1);
		}
		instanceDropdownData.append("]");
		displayInstanceDropdownData.append("]");
		reportInstanceDropdownData.append("]");
		alarmInstanceDropdownData.append("]");
		applicationScenariosDropdownData.append("]");
		
		String json = "";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+","
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
			result_json.append("\"applicationScenariosName\":\""+obj[3]+"\",");
			result_json.append("\"instanceName\":\""+obj[4]+"\",");
			result_json.append("\"displayInstanceName\":\""+obj[5]+"\",");
			result_json.append("\"alarmInstanceName\":\""+obj[6]+"\",");
			result_json.append("\"reportInstanceName\":\""+obj[7]+"\",");
			result_json.append("\"tcpType\":\""+(obj[8]+"").replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
			result_json.append("\"signInId\":\""+obj[9]+"\",");
			result_json.append("\"ipPort\":\""+obj[10]+"\",");
			result_json.append("\"slave\":\""+obj[11]+"\",");
			result_json.append("\"peakDelay\":\""+obj[12]+"\",");
			result_json.append("\"status\":\""+obj[14]+"\",");
			result_json.append("\"statusName\":\""+obj[15]+"\",");
			result_json.append("\"allPath\":\""+obj[16]+"\",");
			result_json.append("\"productionDataUpdateTime\":\""+obj[17]+"\",");
			result_json.append("\"sortNum\":\""+obj[13]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getExportRPCDeviceInfo(String orgId,String deviceTypeStr,String applicationScenarios) {
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String tableName="viw_rpcdevice";
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		String ddicName="deviceInfo_RPCDeviceBatchAdd";
		if(deviceType>=200&&deviceType<300){
			ddicName="deviceInfo_PCPDeviceBatchAdd";
		}
		
		String columns=service.showTableHeadersColumns(ddicName);
		String sql = "select id,orgName,wellName,applicationScenariosName,"//3
				+ " instanceName,displayInstanceName,alarmInstanceName,reportInstanceName,"//7
				+ " tcptype,signInId,slave,t.peakdelay,"//11
				+ " videoUrl1,videoKeyName1,videoUrl2,videoKeyName2,"//15
				+ " sortNum,statusName,"//17
				+ " t.productiondata,"//18
				+ " t.manufacturer,t.model,t.stroke,"//21
				+ " decode( lower(t.crankrotationdirection),'clockwise','顺时针','anticlockwise','逆时针','' ) as crankrotationdirection,"//22
				+ " t.offsetangleofcrank,t.crankgravityradius,t.singlecrankweight,t.singlecrankpinweight,t.structuralunbalance,"//27
				+ " t.balanceinfo"//28
				+ " from "+tableName+" t "
				+ " where t.orgid in ("+orgId+" ) and t.applicationScenarios="+applicationScenarios;
		
		sql+= " order by t.sortnum,t.wellname ";
		String finalSql=sql;
		List<?> list=this.findCallSql(finalSql);
		result_json.append("{\"success\":true,"
				+ "\"totalCount\":"+list.size()+","
				+ "\"applicationScenarios\":"+applicationScenarios+","
				+ "\"columns\":"+columns+","
				+ "\"totalRoot\":[");
		
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String productionDataStr=obj[18]+"";
			String balanceInfo=obj[28]+"";
			String crudeOilDensity="",waterDensity="",naturalGasRelativeDensity="",saturationPressure="",
					reservoirDepth="",reservoirTemperature="",
					tubingPressure="",casingPressure="",wellHeadTemperature="",waterCut="",productionGasOilRatio="",producingfluidLevel="",pumpSettingDepth="",
					barrelType="",pumpGrade="",pumpBoreDiameter="",plungerLength="",
					tubingStringInsideDiameter="",casingStringInsideDiameter="",
					rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="",
					rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="",
					rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="",
					rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="",
					netGrossRatio="",netGrossValue="",levelCorrectValue="";
			String balanceWeight="",balancePosition="";
			
			
			if(StringManagerUtils.isNotNull(productionDataStr)){
				type = new TypeToken<RPCProductionData>() {}.getType();
				RPCProductionData productionData=gson.fromJson(productionDataStr, type);
				if(productionData!=null){
					if(productionData.getFluidPVT()!=null){
						crudeOilDensity=productionData.getFluidPVT().getCrudeOilDensity()+"";
						waterDensity=productionData.getFluidPVT().getWaterDensity()+"";
						naturalGasRelativeDensity=productionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
						saturationPressure=productionData.getFluidPVT().getSaturationPressure()+"";
					}
					if(productionData.getReservoir()!=null){
						reservoirDepth=productionData.getReservoir().getDepth()+"";
						reservoirTemperature=productionData.getReservoir().getTemperature()+"";
					}
					if(productionData.getProduction()!=null){
						tubingPressure=productionData.getProduction().getTubingPressure()+"";
						casingPressure=productionData.getProduction().getCasingPressure()+"";
						wellHeadTemperature=productionData.getProduction().getWellHeadTemperature()+"";
						waterCut=productionData.getProduction().getWaterCut()+"";
						productionGasOilRatio=productionData.getProduction().getProductionGasOilRatio()+"";
						producingfluidLevel=productionData.getProduction().getProducingfluidLevel()+"";
						pumpSettingDepth=productionData.getProduction().getPumpSettingDepth()+"";
					}
					if(productionData.getPump()!=null){
						if("L".equalsIgnoreCase(productionData.getPump().getBarrelType())){
							barrelType="组合泵";
						}else{
							barrelType="整筒泵";
						}
						pumpGrade=productionData.getPump().getPumpGrade()+"";
						pumpBoreDiameter=productionData.getPump().getPumpBoreDiameter()*1000+"";
						plungerLength=productionData.getPump().getPlungerLength()+"";
					}
					if(productionData.getTubingString()!=null && productionData.getTubingString().getEveryTubing()!=null && productionData.getTubingString().getEveryTubing().size()>0){
						tubingStringInsideDiameter=productionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000+"";
					}
					if(productionData.getCasingString()!=null && productionData.getCasingString().getEveryCasing()!=null && productionData.getCasingString().getEveryCasing().size()>0){
						casingStringInsideDiameter=productionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000+"";
					}
					if(productionData.getRodString()!=null && productionData.getRodString().getEveryRod()!=null && productionData.getRodString().getEveryRod().size()>0){
						rodGrade1=productionData.getRodString().getEveryRod().get(0).getGrade()+"";
						rodOutsideDiameter1=productionData.getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
						rodInsideDiameter1=productionData.getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
						rodLength1=productionData.getRodString().getEveryRod().get(0).getLength()+"";
						if(productionData.getRodString().getEveryRod().size()>1){
							rodGrade2=productionData.getRodString().getEveryRod().get(1).getGrade()+"";
							rodOutsideDiameter2=productionData.getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
							rodInsideDiameter2=productionData.getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
							rodLength2=productionData.getRodString().getEveryRod().get(1).getLength()+"";
							if(productionData.getRodString().getEveryRod().size()>2){
								rodGrade3=productionData.getRodString().getEveryRod().get(2).getGrade()+"";
								rodOutsideDiameter3=productionData.getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
								rodInsideDiameter3=productionData.getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
								rodLength3=productionData.getRodString().getEveryRod().get(2).getLength()+"";
								if(productionData.getRodString().getEveryRod().size()>3){
									rodGrade4=productionData.getRodString().getEveryRod().get(3).getGrade()+"";
									rodOutsideDiameter4=productionData.getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
									rodInsideDiameter4=productionData.getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
									rodLength4=productionData.getRodString().getEveryRod().get(3).getLength()+"";
								}
							}
						}
					}
					if(productionData.getManualIntervention()!=null){
						netGrossRatio=productionData.getManualIntervention().getNetGrossRatio()+"";
						netGrossValue=productionData.getManualIntervention().getNetGrossValue()+"";
						levelCorrectValue=productionData.getManualIntervention().getLevelCorrectValue()+"";
					}
				}
			}
			
			if(StringManagerUtils.isNotNull(balanceInfo)){
				type = new TypeToken<RPCCalculateRequestData.Balance>() {}.getType();
				RPCCalculateRequestData.Balance balance=gson.fromJson(balanceInfo, type);
				if(balance!=null && balance.getEveryBalance().size()>0){
					for(int j=0;j<balance.getEveryBalance().size();j++){
						balanceWeight+=balance.getEveryBalance().get(j).getWeight()+"";
						balancePosition+=balance.getEveryBalance().get(j).getPosition()+"";
						if(j<balance.getEveryBalance().size()-1){
							balanceWeight+=",";
							balancePosition+=",";
						}
					}
				}
			}
			
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"orgName\":\""+obj[1]+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"applicationScenariosName\":\""+obj[3]+"\",");
			result_json.append("\"instanceName\":\""+obj[4]+"\",");
			result_json.append("\"displayInstanceName\":\""+obj[5]+"\",");
			result_json.append("\"alarmInstanceName\":\""+obj[6]+"\",");
			result_json.append("\"reportInstanceName\":\""+obj[7]+"\",");
			
			result_json.append("\"tcpType\":\""+(obj[8]+"").replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
			result_json.append("\"signInId\":\""+obj[9]+"\",");
			result_json.append("\"slave\":\""+obj[10]+"\",");
			result_json.append("\"peakDelay\":\""+obj[11]+"\",");
			
			result_json.append("\"videoUrl1\":\""+obj[12]+"\",");
			result_json.append("\"videoKeyName1\":\""+obj[13]+"\",");
			result_json.append("\"videoUrl2\":\""+obj[14]+"\",");
			result_json.append("\"videoKeyName2\":\""+obj[15]+"\",");
			
			result_json.append("\"sortNum\":\""+obj[16]+"\",");
			result_json.append("\"statusName\":\""+obj[17]+"\",");
			
			result_json.append("\"crudeOilDensity\":\""+crudeOilDensity+"\",");
			result_json.append("\"waterDensity\":\""+waterDensity+"\",");
			result_json.append("\"naturalGasRelativeDensity\":\""+naturalGasRelativeDensity+"\",");
			result_json.append("\"saturationPressure\":\""+saturationPressure+"\",");
			result_json.append("\"reservoirDepth\":\""+reservoirDepth+"\",");
			result_json.append("\"reservoirTemperature\":\""+reservoirTemperature+"\",");
			result_json.append("\"tubingPressure\":\""+tubingPressure+"\",");
			result_json.append("\"casingPressure\":\""+casingPressure+"\",");
			result_json.append("\"wellHeadTemperature\":\""+wellHeadTemperature+"\",");
			result_json.append("\"waterCut\":\""+waterCut+"\",");
			result_json.append("\"productionGasOilRatio\":\""+productionGasOilRatio+"\",");
			result_json.append("\"producingfluidLevel\":\""+producingfluidLevel+"\",");
			result_json.append("\"pumpSettingDepth\":\""+pumpSettingDepth+"\",");
			result_json.append("\"barrelType\":\""+barrelType+"\",");
			result_json.append("\"pumpGrade\":\""+pumpGrade+"\",");
			result_json.append("\"pumpBoreDiameter\":\""+pumpBoreDiameter+"\",");
			result_json.append("\"plungerLength\":\""+plungerLength+"\",");
			result_json.append("\"tubingStringInsideDiameter\":\""+tubingStringInsideDiameter+"\",");
			result_json.append("\"casingStringInsideDiameter\":\""+casingStringInsideDiameter+"\",");
			result_json.append("\"rodGrade1\":\""+rodGrade1+"\",");
			result_json.append("\"rodOutsideDiameter1\":\""+rodOutsideDiameter1+"\",");
			result_json.append("\"rodInsideDiameter1\":\""+rodInsideDiameter1+"\",");
			result_json.append("\"rodLength1\":\""+rodLength1+"\",");
			result_json.append("\"rodGrade2\":\""+rodGrade2+"\",");
			result_json.append("\"rodOutsideDiameter2\":\""+rodOutsideDiameter2+"\",");
			result_json.append("\"rodInsideDiameter2\":\""+rodInsideDiameter2+"\",");
			result_json.append("\"rodLength2\":\""+rodLength2+"\",");
			result_json.append("\"rodGrade3\":\""+rodGrade3+"\",");
			result_json.append("\"rodOutsideDiameter3\":\""+rodOutsideDiameter3+"\",");
			result_json.append("\"rodInsideDiameter3\":\""+rodInsideDiameter3+"\",");
			result_json.append("\"rodLength3\":\""+rodLength3+"\",");
			result_json.append("\"rodGrade4\":\""+rodGrade4+"\",");
			result_json.append("\"rodOutsideDiameter4\":\""+rodOutsideDiameter4+"\",");
			result_json.append("\"rodInsideDiameter4\":\""+rodInsideDiameter4+"\",");
			result_json.append("\"rodLength4\":\""+rodLength4+"\",");
			
			result_json.append("\"netGrossRatio\":\""+netGrossRatio+"\",");
			result_json.append("\"netGrossValue\":\""+netGrossValue+"\",");
			result_json.append("\"levelCorrectValue\":\""+levelCorrectValue+"\",");
			
			result_json.append("\"manufacturer\":\""+obj[19]+"\",");
			result_json.append("\"model\":\""+obj[20]+"\",");
			result_json.append("\"stroke\":\""+obj[21]+"\",");
			result_json.append("\"crankRotationDirection\":\""+obj[22]+"\",");
			result_json.append("\"offsetAngleOfCrank\":\""+obj[23]+"\",");
			result_json.append("\"crankGravityRadius\":\""+obj[24]+"\",");
			result_json.append("\"singleCrankWeight\":\""+obj[25]+"\",");
			result_json.append("\"singleCrankPinWeight\":\""+obj[26]+"\",");
			result_json.append("\"structuralUnbalance\":\""+obj[27]+"\",");
			
			
			result_json.append("\"balanceWeight\":\""+balanceWeight+"\",");
			result_json.append("\"balancePosition\":\""+balancePosition+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getExportPCPDeviceInfo(String orgId,String deviceTypeStr,String applicationScenarios) {
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String tableName="viw_rpcdevice";
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		String ddicName="deviceInfo_RPCDeviceBatchAdd";
		if(deviceType>=200&&deviceType<300){
			tableName="viw_pcpdevice";
			ddicName="deviceInfo_PCPDeviceBatchAdd";
		}
		
		String columns=service.showTableHeadersColumns(ddicName);
		String sql = "select id,orgName,wellName,applicationScenariosName,"//3
				+ " instanceName,displayInstanceName,alarmInstanceName,reportInstanceName,"//7
				+ " tcptype,signInId,slave,t.peakdelay,"//11
				+ " videoUrl1,videoKeyName1,videoUrl2,videoKeyName2,"//15
				+ " sortNum,statusName,"//17
				+ " t.productiondata"//18
				+ " from "+tableName+" t "
				+ " where t.applicationScenarios="+applicationScenarios+" and t.orgid in ("+orgId+" )";
		sql+= " order by t.sortnum,t.wellname ";
		String finalSql=sql;
		List<?> list=this.findCallSql(finalSql);
		result_json.append("{\"success\":true,"
				+ "\"applicationScenarios\":"+applicationScenarios+","
				+ "\"totalCount\":"+list.size()+","
				+ "\"columns\":"+columns+","
				+ "\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String productionDataStr=obj[18]+"";
			String crudeOilDensity="",waterDensity="",naturalGasRelativeDensity="",saturationPressure="",
					reservoirDepth="",reservoirTemperature="",
					tubingPressure="",casingPressure="",wellHeadTemperature="",waterCut="",productionGasOilRatio="",producingfluidLevel="",pumpSettingDepth="",
					barrelLength="",barrelSeries="",rotorDiameter="",QPR="",
					tubingStringInsideDiameter="",casingStringInsideDiameter="",
					rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="",
					rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="",
					rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="",
					rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="",
					netGrossRatio="",netGrossValue="";
			
			if(StringManagerUtils.isNotNull(productionDataStr)){
				type = new TypeToken<PCPProductionData>() {}.getType();
				PCPProductionData productionData=gson.fromJson(productionDataStr, type);
				if(productionData!=null){
					if(productionData.getFluidPVT()!=null){
						crudeOilDensity=productionData.getFluidPVT().getCrudeOilDensity()+"";
						waterDensity=productionData.getFluidPVT().getWaterDensity()+"";
						naturalGasRelativeDensity=productionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
						saturationPressure=productionData.getFluidPVT().getSaturationPressure()+"";
					}
					if(productionData.getReservoir()!=null){
						reservoirDepth=productionData.getReservoir().getDepth()+"";
						reservoirTemperature=productionData.getReservoir().getTemperature()+"";
					}
					if(productionData.getProduction()!=null){
						tubingPressure=productionData.getProduction().getTubingPressure()+"";
						casingPressure=productionData.getProduction().getCasingPressure()+"";
						wellHeadTemperature=productionData.getProduction().getWellHeadTemperature()+"";
						waterCut=productionData.getProduction().getWaterCut()+"";
						productionGasOilRatio=productionData.getProduction().getProductionGasOilRatio()+"";
						producingfluidLevel=productionData.getProduction().getProducingfluidLevel()+"";
						pumpSettingDepth=productionData.getProduction().getPumpSettingDepth()+"";
					}
					if(productionData.getPump()!=null){
						barrelLength=productionData.getPump().getBarrelLength()+"";
						barrelSeries=productionData.getPump().getBarrelSeries()+"";
						rotorDiameter=productionData.getPump().getRotorDiameter()*1000+"";
						QPR=productionData.getPump().getQPR()*1000*1000+"";
					}
					if(productionData.getTubingString()!=null && productionData.getTubingString().getEveryTubing()!=null && productionData.getTubingString().getEveryTubing().size()>0){
						tubingStringInsideDiameter=productionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000+"";
					}
					if(productionData.getCasingString()!=null && productionData.getCasingString().getEveryCasing()!=null && productionData.getCasingString().getEveryCasing().size()>0){
						casingStringInsideDiameter=productionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000+"";
					}
					if(productionData.getRodString()!=null && productionData.getRodString().getEveryRod()!=null && productionData.getRodString().getEveryRod().size()>0){
						rodGrade1=productionData.getRodString().getEveryRod().get(0).getGrade()+"";
						rodOutsideDiameter1=productionData.getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
						rodInsideDiameter1=productionData.getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
						rodLength1=productionData.getRodString().getEveryRod().get(0).getLength()+"";
						if(productionData.getRodString().getEveryRod().size()>1){
							rodGrade2=productionData.getRodString().getEveryRod().get(1).getGrade()+"";
							rodOutsideDiameter2=productionData.getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
							rodInsideDiameter2=productionData.getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
							rodLength2=productionData.getRodString().getEveryRod().get(1).getLength()+"";
							if(productionData.getRodString().getEveryRod().size()>2){
								rodGrade3=productionData.getRodString().getEveryRod().get(2).getGrade()+"";
								rodOutsideDiameter3=productionData.getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
								rodInsideDiameter3=productionData.getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
								rodLength3=productionData.getRodString().getEveryRod().get(2).getLength()+"";
								if(productionData.getRodString().getEveryRod().size()>3){
									rodGrade4=productionData.getRodString().getEveryRod().get(3).getGrade()+"";
									rodOutsideDiameter4=productionData.getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
									rodInsideDiameter4=productionData.getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
									rodLength4=productionData.getRodString().getEveryRod().get(3).getLength()+"";
								}
							}
						}
					}
					if(productionData.getManualIntervention()!=null){
						netGrossRatio=productionData.getManualIntervention().getNetGrossRatio()+"";
						netGrossValue=productionData.getManualIntervention().getNetGrossValue()+"";
					}
				}
			}
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"orgName\":\""+obj[1]+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"applicationScenariosName\":\""+obj[3]+"\",");
			result_json.append("\"instanceName\":\""+obj[4]+"\",");
			result_json.append("\"displayInstanceName\":\""+obj[5]+"\",");
			result_json.append("\"alarmInstanceName\":\""+obj[6]+"\",");
			result_json.append("\"reportInstanceName\":\""+obj[7]+"\",");
			
			result_json.append("\"tcpType\":\""+(obj[8]+"").replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
			result_json.append("\"signInId\":\""+obj[9]+"\",");
			result_json.append("\"slave\":\""+obj[10]+"\",");
			result_json.append("\"peakDelay\":\""+obj[11]+"\",");
			
			result_json.append("\"videoUrl1\":\""+obj[12]+"\",");
			result_json.append("\"videoKeyName1\":\""+obj[13]+"\",");
			result_json.append("\"videoUrl2\":\""+obj[14]+"\",");
			result_json.append("\"videoKeyName2\":\""+obj[15]+"\",");
			
			result_json.append("\"sortNum\":\""+obj[16]+"\",");
			result_json.append("\"statusName\":\""+obj[17]+"\",");
			
			result_json.append("\"crudeOilDensity\":\""+crudeOilDensity+"\",");
			result_json.append("\"waterDensity\":\""+waterDensity+"\",");
			result_json.append("\"naturalGasRelativeDensity\":\""+naturalGasRelativeDensity+"\",");
			result_json.append("\"saturationPressure\":\""+saturationPressure+"\",");
			result_json.append("\"reservoirDepth\":\""+reservoirDepth+"\",");
			result_json.append("\"reservoirTemperature\":\""+reservoirTemperature+"\",");
			result_json.append("\"tubingPressure\":\""+tubingPressure+"\",");
			result_json.append("\"casingPressure\":\""+casingPressure+"\",");
			result_json.append("\"wellHeadTemperature\":\""+wellHeadTemperature+"\",");
			result_json.append("\"waterCut\":\""+waterCut+"\",");
			result_json.append("\"productionGasOilRatio\":\""+productionGasOilRatio+"\",");
			result_json.append("\"producingfluidLevel\":\""+producingfluidLevel+"\",");
			result_json.append("\"pumpSettingDepth\":\""+pumpSettingDepth+"\",");
			result_json.append("\"barrelLength\":\""+barrelLength+"\",");
			result_json.append("\"barrelSeries\":\""+barrelSeries+"\",");
			result_json.append("\"rotorDiameter\":\""+rotorDiameter+"\",");
			result_json.append("\"QPR\":\""+QPR+"\",");
			result_json.append("\"tubingStringInsideDiameter\":\""+tubingStringInsideDiameter+"\",");
			result_json.append("\"casingStringInsideDiameter\":\""+casingStringInsideDiameter+"\",");
			result_json.append("\"rodGrade1\":\""+rodGrade1+"\",");
			result_json.append("\"rodOutsideDiameter1\":\""+rodOutsideDiameter1+"\",");
			result_json.append("\"rodInsideDiameter1\":\""+rodInsideDiameter1+"\",");
			result_json.append("\"rodLength1\":\""+rodLength1+"\",");
			result_json.append("\"rodGrade2\":\""+rodGrade2+"\",");
			result_json.append("\"rodOutsideDiameter2\":\""+rodOutsideDiameter2+"\",");
			result_json.append("\"rodInsideDiameter2\":\""+rodInsideDiameter2+"\",");
			result_json.append("\"rodLength2\":\""+rodLength2+"\",");
			result_json.append("\"rodGrade3\":\""+rodGrade3+"\",");
			result_json.append("\"rodOutsideDiameter3\":\""+rodOutsideDiameter3+"\",");
			result_json.append("\"rodInsideDiameter3\":\""+rodInsideDiameter3+"\",");
			result_json.append("\"rodLength3\":\""+rodLength3+"\",");
			result_json.append("\"rodGrade4\":\""+rodGrade4+"\",");
			result_json.append("\"rodOutsideDiameter4\":\""+rodOutsideDiameter4+"\",");
			result_json.append("\"rodInsideDiameter4\":\""+rodInsideDiameter4+"\",");
			result_json.append("\"rodLength4\":\""+rodLength4+"\",");
			
			result_json.append("\"netGrossRatio\":\""+netGrossRatio+"\",");
			result_json.append("\"netGrossValue\":\""+netGrossValue+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	@SuppressWarnings("rawtypes")
	public String getRPCDeviceInfoExportData(Map map,Page pager,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		String tableName="viw_rpcdevice";
		String wellInformationName = (String) map.get("wellInformationName");
		int deviceType=StringManagerUtils.stringToInteger((String) map.get("deviceType"));
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		if (StringManagerUtils.isNotNull(wellInformationName)) {
			WellInformation_Str = " and t.wellname like '%" + wellInformationName+ "%'";
		}
		String sql = "select id,orgName,wellName,applicationScenariosName,instanceName,displayInstanceName,alarmInstanceName,"
				+ " tcptype,signInId,slave,t.peakdelay,"
				+ " sortNum,status,statusName,allpath"
				+ " from "+tableName+" t where 1=1"
				+ WellInformation_Str;
		sql+= " and t.orgid in ("+orgId+" )";
		sql+= " and t.devicetype="+deviceType;
		sql+= " order by t.sortnum,t.wellname ";
		
		
		String json = "";
		List<?> list = this.findCallSql(sql);
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"orgName\":\""+obj[1]+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"applicationScenariosName\":\""+obj[3]+"\",");
			result_json.append("\"instanceName\":\""+obj[4]+"\",");
			result_json.append("\"displayInstanceName\":\""+obj[5]+"\",");
			result_json.append("\"alarmInstanceName\":\""+obj[6]+"\",");
			result_json.append("\"tcpType\":\""+(obj[7]+"").replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
			result_json.append("\"signInId\":\""+obj[8]+"\",");
			result_json.append("\"slave\":\""+obj[9]+"\",");
			result_json.append("\"peakDelay\":\""+obj[10]+"\",");
			result_json.append("\"status\":\""+obj[12]+"\",");
			result_json.append("\"statusName\":\""+obj[13]+"\",");
			result_json.append("\"allPath\":\""+obj[14]+"\",");
			result_json.append("\"sortNum\":\""+obj[11]+"\"},");
		}
		for(int i=1;i<=recordCount-list.size();i++){
			result_json.append("{\"jlbh\":\"-99999\",\"id\":\"-99999\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public boolean exportRPCDeviceInfoData(User user,HttpServletResponse response,String fileName,String title,String head,String field,Map map,Page pager,int recordCount) {
		try{
			StringBuffer result_json = new StringBuffer();
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			String tableName="viw_rpcdevice";
			String wellInformationName = (String) map.get("wellInformationName");
			int deviceType=StringManagerUtils.stringToInteger((String) map.get("deviceType"));
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
			
			String sql = "select id,orgName,wellName,applicationScenariosName,instanceName,displayInstanceName,alarmInstanceName,"
					+ " tcptype,signInId,slave,t.peakdelay,"
					+ " sortNum,status,statusName,allpath"
					+ " from "+tableName+" t where 1=1"
					+ WellInformation_Str;
			sql+= " and t.orgid in ("+orgId+" )";
			sql+= " and t.devicetype="+deviceType;
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
				result_json.append("{\"id\":\""+(i+1)+"\",");
				result_json.append("\"orgName\":\""+obj[1]+"\",");
				result_json.append("\"wellName\":\""+obj[2]+"\",");
				result_json.append("\"applicationScenariosName\":\""+obj[3]+"\",");
				result_json.append("\"instanceName\":\""+obj[4]+"\",");
				result_json.append("\"displayInstanceName\":\""+obj[5]+"\",");
				result_json.append("\"alarmInstanceName\":\""+obj[6]+"\",");
				result_json.append("\"tcpType\":\""+(obj[7]+"").replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
				result_json.append("\"signInId\":\""+obj[8]+"\",");
				result_json.append("\"slave\":\""+obj[9]+"\",");
				result_json.append("\"peakDelay\":\""+obj[10]+"\",");
				result_json.append("\"status\":\""+obj[12]+"\",");
				result_json.append("\"statusName\":\""+obj[13]+"\",");
				result_json.append("\"allPath\":\""+obj[14]+"\",");
				result_json.append("\"sortNum\":\""+obj[11]+"\"}");
				
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
	
	@SuppressWarnings("rawtypes")
	public String getPCPDeviceInfoList(Map map,Page pager,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer displayInstanceDropdownData = new StringBuffer();
		StringBuffer reportInstanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		StringBuffer applicationScenariosDropdownData = new StringBuffer();
		String ddicName="deviceInfo_PCPDeviceManager";
		String tableName="viw_pcpdevice";
		int protocolType=1;
		String wellInformationName = (String) map.get("wellInformationName");
		int deviceType=StringManagerUtils.stringToInteger((String) map.get("deviceType"));
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		if (StringManagerUtils.isNotNull(wellInformationName)) {
			WellInformation_Str = " and t.wellname like '%" + wellInformationName+ "%'";
		}
		
		String columns=service.showTableHeadersColumns(ddicName);
		String sql = "select id,orgName,wellName,applicationScenariosName,"
				+ " instanceName,displayInstanceName,alarmInstanceName,t.reportInstanceName,"
				+ " tcptype,signInId,ipport,slave,t.peakdelay,"
				+ " sortNum,status,statusName,allpath,to_char(productiondataupdatetime,'yyyy-mm-dd hh24:mi:ss') as productiondataupdatetime"
				+ " from "+tableName+" t where 1=1"
				+ WellInformation_Str;
		sql+= " and t.orgid in ("+orgId+" )  ";		
		
		sql+= " and t.devicetype="+deviceType;
		sql+= " order by t.sortnum,t.wellname ";
		String instanceSql="select t.name from tbl_protocolinstance t  order by t.sort";
		String displayInstanceSql="select t.name from tbl_protocoldisplayinstance t  order by t.sort";
		String reportInstanceSql="select t.name from tbl_protocolreportinstance t  order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t  order by t.sort";
		String applicationScenariosSql="select c.itemname from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' order by c.itemvalue desc";
		
		instanceDropdownData.append("[");
		displayInstanceDropdownData.append("[");
		reportInstanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");
		applicationScenariosDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
		List<?> reportInstanceList = this.findCallSql(reportInstanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		List<?> applicationScenariosList = this.findCallSql(applicationScenariosSql);
		
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
		
		for(int i=0;i<applicationScenariosList.size();i++){
			applicationScenariosDropdownData.append("'"+applicationScenariosList.get(i)+"',");
		}
		if(applicationScenariosDropdownData.toString().endsWith(",")){
			applicationScenariosDropdownData.deleteCharAt(applicationScenariosDropdownData.length() - 1);
		}
	
		instanceDropdownData.append("]");
		displayInstanceDropdownData.append("]");
		reportInstanceDropdownData.append("]");
		alarmInstanceDropdownData.append("]");
		applicationScenariosDropdownData.append("]");
		
		String json = "";
		
		List<?> list = this.findCallSql(sql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+","
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
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"applicationScenariosName\":\""+obj[3]+"\",");
			result_json.append("\"instanceName\":\""+obj[4]+"\",");
			result_json.append("\"displayInstanceName\":\""+obj[5]+"\",");
			result_json.append("\"alarmInstanceName\":\""+obj[6]+"\",");
			result_json.append("\"reportInstanceName\":\""+obj[7]+"\",");
			result_json.append("\"tcpType\":\""+(obj[8]+"").replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
			result_json.append("\"signInId\":\""+obj[9]+"\",");
			result_json.append("\"ipPort\":\""+obj[10]+"\",");
			result_json.append("\"slave\":\""+obj[11]+"\",");
			result_json.append("\"peakDelay\":\""+obj[12]+"\",");
			result_json.append("\"status\":\""+obj[14]+"\",");
			result_json.append("\"statusName\":\""+obj[15]+"\",");
			result_json.append("\"allPath\":\""+obj[16]+"\",");
			result_json.append("\"productionDataUpdateTime\":\""+obj[17]+"\",");
			result_json.append("\"sortNum\":\""+obj[13]+"\"},");
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
	public String getPipeDeviceInfoExportData(Map map,Page pager,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		String tableName="viw_pcpdevice";
		String wellInformationName = (String) map.get("wellInformationName");
		int deviceType=StringManagerUtils.stringToInteger((String) map.get("deviceType"));
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		if (StringManagerUtils.isNotNull(wellInformationName)) {
			WellInformation_Str = " and t.wellname like '%" + wellInformationName+ "%'";
		}
		
		String sql = "select id,orgName,wellName,applicationScenariosName,instanceName,displayInstanceName,alarmInstanceName,"
				+ " tcptype,signInId,slave,t.peakdelay,"
				+ " sortNum,status,statusName,allpath"
				+ " from "+tableName+" t where 1=1"
				+ WellInformation_Str;
		sql+= " and t.orgid in ("+orgId+" )  ";		
		
		sql+= " and t.devicetype="+deviceType;
		sql+= " order by t.sortnum,t.wellname ";
		
		String json = "";
		
		List<?> list = this.findCallSql(sql);
		
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"orgName\":\""+obj[1]+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"applicationScenariosName\":\""+obj[3]+"\",");
			result_json.append("\"instanceName\":\""+obj[4]+"\",");
			result_json.append("\"displayInstanceName\":\""+obj[5]+"\",");
			result_json.append("\"alarmInstanceName\":\""+obj[6]+"\",");
			result_json.append("\"tcpType\":\""+(obj[7]+"").replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
			result_json.append("\"signInId\":\""+obj[8]+"\",");
			result_json.append("\"slave\":\""+obj[9]+"\",");
			result_json.append("\"peakDelay\":\""+obj[10]+"\",");
			result_json.append("\"status\":\""+obj[12]+"\",");
			result_json.append("\"statusName\":\""+obj[13]+"\",");
			result_json.append("\"allPath\":\""+obj[14]+"\",");
			result_json.append("\"sortNum\":\""+obj[11]+"\"},");
		}
		for(int i=1;i<=recordCount-list.size();i++){
			result_json.append("{\"jlbh\":\"-99999\",\"id\":\"-99999\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public boolean exportPCPDeviceInfoData(User user,HttpServletResponse response,String fileName,String title,String head,String field,Map map,Page pager,int recordCount) {
		try{
			StringBuffer result_json = new StringBuffer();
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			String tableName="viw_pcpdevice";
			String wellInformationName = (String) map.get("wellInformationName");
			int deviceType=StringManagerUtils.stringToInteger((String) map.get("deviceType"));
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
			
			String sql = "select id,orgName,wellName,applicationScenariosName,instanceName,displayInstanceName,alarmInstanceName,"
					+ " tcptype,signInId,slave,t.peakdelay,"
					+ " sortNum,status,statusName,allpath"
					+ " from "+tableName+" t where 1=1"
					+ WellInformation_Str;
			sql+= " and t.orgid in ("+orgId+" )  ";		
			
			sql+= " and t.devicetype="+deviceType;
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
				result_json.append("\"applicationScenariosName\":\""+obj[3]+"\",");
				result_json.append("\"instanceName\":\""+obj[4]+"\",");
				result_json.append("\"displayInstanceName\":\""+obj[5]+"\",");
				result_json.append("\"alarmInstanceName\":\""+obj[6]+"\",");
				result_json.append("\"tcpType\":\""+(obj[7]+"").replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
				result_json.append("\"signInId\":\""+obj[8]+"\",");
				result_json.append("\"slave\":\""+obj[9]+"\",");
				result_json.append("\"peakDelay\":\""+obj[10]+"\",");
				result_json.append("\"status\":\""+obj[12]+"\",");
				result_json.append("\"statusName\":\""+obj[13]+"\",");
				result_json.append("\"allPath\":\""+obj[14]+"\",");
				result_json.append("\"sortNum\":\""+obj[11]+"\"}");
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
	
	@SuppressWarnings("rawtypes")
	public String getSMSDeviceInfoList(Map map,Page pager,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer SMSInstanceDropdownData = new StringBuffer();
		String ddicName="SMSDevice_SMSDeviceManager";
		String tableName="viw_smsdevice";
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		String wellInformationName = (String) map.get("wellInformationName");
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		if (StringManagerUtils.isNotNull(wellInformationName)) {
			WellInformation_Str = " and t.wellname like '%" + wellInformationName+ "%'";
		}
		
		String columns=service.showTableHeadersColumns(ddicName);
		String sql = "select id,orgName,wellName,instanceName,signInId,sortNum"
				+ " from "+tableName+" t where 1=1"
				+ WellInformation_Str;
		sql+= " and t.orgid in ("+orgId+" )  ";		
		
		sql+= " order by t.sortnum,t.wellname ";
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
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	@SuppressWarnings("rawtypes")
	public String getSMSDeviceInfoExportData(Map map,Page pager,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		String tableName="viw_smsdevice";
		String wellInformationName = (String) map.get("wellInformationName");
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		if (StringManagerUtils.isNotNull(wellInformationName)) {
			WellInformation_Str = " and t.wellname like '%" + wellInformationName+ "%'";
		}
		String sql = "select id,orgName,wellName,instanceName,signInId,sortNum"
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
			
			String sql = "select id,orgName,wellName,instanceName,signInId,sortNum"
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
	
	@SuppressWarnings("rawtypes")
	public String getPumpingPRTFData(String recordId,String stroke) throws SQLException, IOException {
		StringBuffer result_json = new StringBuffer();
		StringBuffer strokeListBuff = new StringBuffer();
		String sql = "select t.stroke,t.prtf from tbl_pumpingmodel t where t.id="+recordId;
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
			if(pumpingPRTFData!=null&&pumpingPRTFData.getList()!=null&&pumpingPRTFData.getList().size()>0){
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
	public String doPumpingModelShow(String manufacturer,String model) throws SQLException, IOException {
		StringBuffer result_json = new StringBuffer();
		String ddicName="pumpingDevice_PumpingModelManager";
		String columns=service.showTableHeadersColumns(ddicName);
		String sql = "select t.id,t.manufacturer,t.model,t.stroke,decode(t.crankrotationdirection,'Anticlockwise','逆时针','Clockwise','顺时针','') as crankrotationdirection,"
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
	public String getBatchAddPumpingModelTableInfo(int recordCount) {
		StringBuffer result_json = new StringBuffer();
		String ddicName="pumpingDevice_PumpingModelManager";
		String columns=service.showTableHeadersColumns(ddicName);
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
	public String getPumpingModelExportData(Map map,Page pager,String deviceType,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		String sql = "select t.id,t.manufacturer,t.model,t.stroke,decode(t.crankrotationdirection,'Anticlockwise','逆时针','Clockwise','顺时针','') as crankrotationdirection,"
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
			String sql = "select t.id,t.manufacturer,t.model,t.stroke,decode(t.crankrotationdirection,'Anticlockwise','逆时针','Clockwise','顺时针','') as crankrotationdirection,"
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
	
	public String getPumpingModelList(String deviceId,String deviceType) {
		StringBuffer result_json = new StringBuffer();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"name\",width:120 ,children:[] },"
				+ "{ \"header\":\"规格型号\",\"dataIndex\":\"model\",width:80 ,children:[] }"
				+ "]";
		String sql = "select t.id,t.manufacturer,t.model,t.stroke,t.balanceweight from tbl_pumpingmodel t order by t.id,t.manufacturer,t.model";
		String devicePumpingModelSql="select t.pumpingmodelid from tbl_device t where t.id="+deviceId;
		String json = "";
		List<?> list = this.findCallSql(sql);
		List<?> devicePumpingModel = this.findCallSql(devicePumpingModelSql);
		int devicePumpingModelId=0;
		if(devicePumpingModel.size()>0&&devicePumpingModel.get(0)!=null){
			devicePumpingModelId=StringManagerUtils.stringToInteger(devicePumpingModel.get(0).toString());
		}
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			boolean checked=false;
			if(devicePumpingModelId== StringManagerUtils.stringToInteger(obj[0]+"")){
				checked=true;
			}
			result_json.append("{\"checked\":"+checked+",");
			result_json.append("\"id\":\""+(i+1)+"\",");
			result_json.append("\"realId\":\""+obj[0]+"\",");
			result_json.append("\"manufacturer\":\""+obj[1]+"\",");
			result_json.append("\"model\":\""+obj[2]+"\",");
			result_json.append("\"stroke\":["+obj[3]+"],");
			result_json.append("\"balanceWeight\":["+obj[4]+"]},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getDevicePumpingInfo(String deviceId,String deviceType) {
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"itemValue1\",width:120 ,children:[] },"
				+ "{ \"header\":\"变量\",\"dataIndex\":\"itemValue2\",width:80 ,children:[] }"
				+ "]";
		String sql = "select t.stroke,t.balanceinfo from tbl_device t where t.id="+deviceId;
		String json = "";
		List<?> list = this.findCallSql(sql);
		String stroke="",balanceInfo="";
		String position1="",weight1="",position2="",weight2="",position3="",weight3="",position4="",weight4="",position5="",weight5="",position6="",weight6="",position7="",weight7="",position8="",weight8="";
		result_json.append("{\"success\":true,\"totalCount\":9,\"columns\":"+columns+",\"totalRoot\":[");
		if(list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			stroke=obj[0]+"";
			balanceInfo=obj[1]+"";
		}
		type = new TypeToken<RPCCalculateRequestData.Balance>() {}.getType();
		RPCCalculateRequestData.Balance balance=gson.fromJson(balanceInfo, type);
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
		result_json.append("{\"id\":1,\"itemValue1\":\"冲程(m)\",\"itemValue2\":\""+stroke+"\"},");
		result_json.append("{\"id\":2,\"itemValue1\":\"平衡块信息\",\"itemValue2\":\"\"},");
		result_json.append("{\"id\":3,\"itemValue1\":\"位置(m)\",\"itemValue2\":\"重量(kN)\"},");
		
		
		result_json.append("{\"id\":4,\"itemValue1\":\""+position1+"\",\"itemValue2\":\""+weight1+"\"},");
		result_json.append("{\"id\":5,\"itemValue1\":\""+position2+"\",\"itemValue2\":\""+weight2+"\"},");
		result_json.append("{\"id\":6,\"itemValue1\":\""+position3+"\",\"itemValue2\":\""+weight3+"\"},");
		result_json.append("{\"id\":7,\"itemValue1\":\""+position4+"\",\"itemValue2\":\""+weight4+"\"},");
		result_json.append("{\"id\":8,\"itemValue1\":\""+position5+"\",\"itemValue2\":\""+weight5+"\"},");
		result_json.append("{\"id\":9,\"itemValue1\":\""+position6+"\",\"itemValue2\":\""+weight6+"\"},");
		result_json.append("{\"id\":10,\"itemValue1\":\""+position7+"\",\"itemValue2\":\""+weight7+"\"},");
		result_json.append("{\"id\":11,\"itemValue1\":\""+position8+"\",\"itemValue2\":\""+weight8+"\"}");
		
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getDeviceProductionDataInfo(String deviceId,String deviceType,String deviceCalculateDataType) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer resultNameBuff = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Jedis jedis=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("RPCWorkType".getBytes())){
					MemoryDataManagerTask.loadRPCWorkType();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String columns = "["
					+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
					+ "{ \"header\":\"名称\",\"dataIndex\":\"itemName\",width:120 ,children:[] },"
					+ "{ \"header\":\"变量\",\"dataIndex\":\"itemValue\",width:120 ,children:[] }"
					+ "]";
			String deviceTableName="tbl_device";
			String resultSql="select t.resultname from tbl_rpc_worktype t order by t.resultcode";
			String sql = "select t.productiondata,to_char(t.productiondataupdatetime,'yyyy-mm-dd hh24:mi:ss'),t.applicationscenarios "
					+ " from "+deviceTableName+" t "
					+ " where t.id="+deviceId;
			
			List<?> list = this.findCallSql(sql);
			result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");
			resultNameBuff.append("[");
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
				
				String reservoirShow="油层";
				if("0".equalsIgnoreCase(applicationScenarios)){
					reservoirShow="煤层";
				}
				
				if(StringManagerUtils.stringToInteger(deviceCalculateDataType)==1){
					resultNameBuff.append("\"不干预\"");
					List<?> resultList = this.findCallSql(resultSql);
					for(int i=0;i<resultList.size();i++){
						resultNameBuff.append(",\""+resultList.get(i).toString()+"\"");
					}
					type = new TypeToken<RPCProductionData>() {}.getType();
					RPCProductionData rpcProductionData=gson.fromJson(productionData, type);
					if(rpcProductionData!=null){
						result_json.append("{\"id\":1,\"itemName\":\"原油密度(g/cm^3)\",\"itemValue\":\""+(rpcProductionData.getFluidPVT()!=null?rpcProductionData.getFluidPVT().getCrudeOilDensity():"")+"\"},");
						result_json.append("{\"id\":2,\"itemName\":\"水密度(g/cm^3)\",\"itemValue\":\""+(rpcProductionData.getFluidPVT()!=null?rpcProductionData.getFluidPVT().getWaterDensity():"")+"\"},");
						result_json.append("{\"id\":3,\"itemName\":\"天然气相对密度\",\"itemValue\":\""+(rpcProductionData.getFluidPVT()!=null?rpcProductionData.getFluidPVT().getNaturalGasRelativeDensity():"")+"\"},");
						result_json.append("{\"id\":4,\"itemName\":\"饱和压力(MPa)\",\"itemValue\":\""+(rpcProductionData.getFluidPVT()!=null?rpcProductionData.getFluidPVT().getSaturationPressure():"")+"\"},");
						
						result_json.append("{\"id\":5,\"itemName\":\""+reservoirShow+"中部深度(m)\",\"itemValue\":\""+(rpcProductionData.getReservoir()!=null?rpcProductionData.getReservoir().getDepth():"")+"\"},");
						result_json.append("{\"id\":6,\"itemName\":\""+reservoirShow+"中部温度(℃)\",\"itemValue\":\""+(rpcProductionData.getReservoir()!=null?rpcProductionData.getReservoir().getTemperature():"")+"\"},");
						
						result_json.append("{\"id\":7,\"itemName\":\"油压(MPa)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getProduction().getTubingPressure():"")+"\"},");
						result_json.append("{\"id\":8,\"itemName\":\"套压(MPa)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getProduction().getCasingPressure():"")+"\"},");
						result_json.append("{\"id\":9,\"itemName\":\"井口温度(℃)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getProduction().getWellHeadTemperature():"")+"\"},");
						result_json.append("{\"id\":10,\"itemName\":\"含水率(%)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getProduction().getWaterCut():"")+"\"},");
						result_json.append("{\"id\":11,\"itemName\":\"生产气油比(m^3/t)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getProduction().getProductionGasOilRatio():"")+"\"},");
						result_json.append("{\"id\":12,\"itemName\":\"动液面(m)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getProduction().getProducingfluidLevel():"")+"\"},");
						result_json.append("{\"id\":13,\"itemName\":\"泵挂(m)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getProduction().getPumpSettingDepth():"")+"\"},");
						
						String barrelType="";
						if(rpcProductionData.getPump()!=null&&rpcProductionData.getPump().getBarrelType()!=null){
							if("L".equalsIgnoreCase(rpcProductionData.getPump().getBarrelType())){
								barrelType="组合泵";
							}else if("H".equalsIgnoreCase(rpcProductionData.getPump().getBarrelType())){
								barrelType="整筒泵";
							}
						}
//						result_json.append("{\"id\":14,\"itemName\":\"泵类型\",\"itemValue\":\""+pumpType+"\"},");
						result_json.append("{\"id\":14,\"itemName\":\"泵筒类型\",\"itemValue\":\""+barrelType+"\"},");
						result_json.append("{\"id\":15,\"itemName\":\"泵级别\",\"itemValue\":\""+(rpcProductionData.getPump()!=null?rpcProductionData.getPump().getPumpGrade():"")+"\"},");
						result_json.append("{\"id\":16,\"itemName\":\"泵径(mm)\",\"itemValue\":\""+(rpcProductionData.getPump()!=null?(rpcProductionData.getPump().getPumpBoreDiameter()*1000):"")+"\"},");
						result_json.append("{\"id\":17,\"itemName\":\"柱塞长(m)\",\"itemValue\":\""+(rpcProductionData.getPump()!=null?rpcProductionData.getPump().getPlungerLength():"")+"\"},");
						
						result_json.append("{\"id\":18,\"itemName\":\"油管内径(mm)\",\"itemValue\":\""+(rpcProductionData.getTubingString()!=null&&rpcProductionData.getTubingString().getEveryTubing()!=null&&rpcProductionData.getTubingString().getEveryTubing().size()>0?(rpcProductionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000):"")+"\"},");
						result_json.append("{\"id\":19,\"itemName\":\"套管内径(mm)\",\"itemValue\":\""+(rpcProductionData.getCasingString()!=null&&rpcProductionData.getCasingString().getEveryCasing()!=null&&rpcProductionData.getCasingString().getEveryCasing().size()>0?(rpcProductionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000):"")+"\"},");
						
						String rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="";
						String rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="";
						String rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="";
						String rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="";
						if(rpcProductionData.getRodString()!=null&&rpcProductionData.getRodString().getEveryRod()!=null&&rpcProductionData.getRodString().getEveryRod().size()>0){
							if(rpcProductionData.getRodString().getEveryRod().size()>0){
								rodGrade1=rpcProductionData.getRodString().getEveryRod().get(0).getGrade();
								rodOutsideDiameter1=rpcProductionData.getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
								rodInsideDiameter1=rpcProductionData.getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
								rodLength1=rpcProductionData.getRodString().getEveryRod().get(0).getLength()+"";
							}
							if(rpcProductionData.getRodString().getEveryRod().size()>1){
								rodGrade2=rpcProductionData.getRodString().getEveryRod().get(1).getGrade();
								rodOutsideDiameter2=rpcProductionData.getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
								rodInsideDiameter2=rpcProductionData.getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
								rodLength2=rpcProductionData.getRodString().getEveryRod().get(1).getLength()+"";
							}
							if(rpcProductionData.getRodString().getEveryRod().size()>2){
								rodGrade3=rpcProductionData.getRodString().getEveryRod().get(2).getGrade();
								rodOutsideDiameter3=rpcProductionData.getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
								rodInsideDiameter3=rpcProductionData.getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
								rodLength3=rpcProductionData.getRodString().getEveryRod().get(2).getLength()+"";
							}
							if(rpcProductionData.getRodString().getEveryRod().size()>3){
								rodGrade4=rpcProductionData.getRodString().getEveryRod().get(3).getGrade();
								rodOutsideDiameter4=rpcProductionData.getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
								rodInsideDiameter4=rpcProductionData.getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
								rodLength4=rpcProductionData.getRodString().getEveryRod().get(3).getLength()+"";
							}
						}
						result_json.append("{\"id\":20,\"itemName\":\"一级杆级别\",\"itemValue\":\""+rodGrade1+"\"},");
						result_json.append("{\"id\":21,\"itemName\":\"一级杆外径(mm)\",\"itemValue\":\""+rodOutsideDiameter1+"\"},");
						result_json.append("{\"id\":22,\"itemName\":\"一级杆内径(mm)\",\"itemValue\":\""+rodInsideDiameter1+"\"},");
						result_json.append("{\"id\":23,\"itemName\":\"一级杆长度(m)\",\"itemValue\":\""+rodLength1+"\"},");
						
						result_json.append("{\"id\":24,\"itemName\":\"二级杆级别\",\"itemValue\":\""+rodGrade2+"\"},");
						result_json.append("{\"id\":25,\"itemName\":\"二级杆外径(mm)\",\"itemValue\":\""+rodOutsideDiameter2+"\"},");
						result_json.append("{\"id\":26,\"itemName\":\"二级杆内径(mm)\",\"itemValue\":\""+rodInsideDiameter2+"\"},");
						result_json.append("{\"id\":27,\"itemName\":\"二级杆长度(m)\",\"itemValue\":\""+rodLength2+"\"},");
						
						result_json.append("{\"id\":28,\"itemName\":\"三级杆级别\",\"itemValue\":\""+rodGrade3+"\"},");
						result_json.append("{\"id\":39,\"itemName\":\"三级杆外径(mm)\",\"itemValue\":\""+rodOutsideDiameter3+"\"},");
						result_json.append("{\"id\":30,\"itemName\":\"三级杆内径(mm)\",\"itemValue\":\""+rodInsideDiameter3+"\"},");
						result_json.append("{\"id\":31,\"itemName\":\"三级杆长度(m)\",\"itemValue\":\""+rodLength3+"\"},");
						
						result_json.append("{\"id\":32,\"itemName\":\"四级杆级别\",\"itemValue\":\""+rodGrade4+"\"},");
						result_json.append("{\"id\":33,\"itemName\":\"四级杆外径(mm)\",\"itemValue\":\""+rodOutsideDiameter4+"\"},");
						result_json.append("{\"id\":34,\"itemName\":\"四级杆内径(mm)\",\"itemValue\":\""+rodInsideDiameter4+"\"},");
						result_json.append("{\"id\":35,\"itemName\":\"四级杆长度(m)\",\"itemValue\":\""+rodLength4+"\"},");
						
						
						String manualInterventionName="不干预";
						if(rpcProductionData.getManualIntervention()!=null && rpcProductionData.getManualIntervention().getCode()>0){
							if(jedis!=null && jedis.hexists("RPCWorkType".getBytes(), (rpcProductionData.getManualIntervention().getCode()+"").getBytes())){
								WorkType workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkType".getBytes(), (rpcProductionData.getManualIntervention().getCode()+"").getBytes()));
								manualInterventionName=workType.getResultName();
							}else{
								String resultNameSql="select t.resultname from tbl_rpc_worktype t where t.resultcode="+rpcProductionData.getManualIntervention().getCode();
								List<?> resultNameList = this.findCallSql(resultNameSql);
								if(resultNameList.size()>0){
									manualInterventionName=resultNameList.get(0).toString();
								}
							}
						}
						result_json.append("{\"id\":36,\"itemName\":\"工况干预\",\"itemValue\":\""+manualInterventionName+"\"},");
						result_json.append("{\"id\":37,\"itemName\":\"净毛比(小数)\",\"itemValue\":\""+(rpcProductionData.getManualIntervention()!=null?rpcProductionData.getManualIntervention().getNetGrossRatio():"")+"\"},");
						result_json.append("{\"id\":38,\"itemName\":\"净毛值(m^3/d)\",\"itemValue\":\""+(rpcProductionData.getManualIntervention()!=null?rpcProductionData.getManualIntervention().getNetGrossValue():"")+"\"},");
						result_json.append("{\"id\":39,\"itemName\":\"反演液面校正值(MPa)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getManualIntervention().getLevelCorrectValue():"")+"\"}");
					}else{
						result_json.append("{\"id\":1,\"itemName\":\"原油密度(g/cm^3)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":2,\"itemName\":\"水密度(g/cm^3)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":3,\"itemName\":\"天然气相对密度\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":4,\"itemName\":\"饱和压力(MPa)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":5,\"itemName\":\""+reservoirShow+"中部深度(m)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":6,\"itemName\":\""+reservoirShow+"中部温度(℃)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":7,\"itemName\":\"油压(MPa)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":8,\"itemName\":\"套压(MPa)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":9,\"itemName\":\"井口温度(℃)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":10,\"itemName\":\"含水率(%)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":11,\"itemName\":\"生产气油比(m^3/t)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":12,\"itemName\":\"动液面(m)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":13,\"itemName\":\"泵挂(m)\",\"itemValue\":\"\"},");
						
//						result_json.append("{\"id\":14,\"itemName\":\"泵类型\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":14,\"itemName\":\"泵筒类型\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":15,\"itemName\":\"泵级别\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":16,\"itemName\":\"泵径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":17,\"itemName\":\"柱塞长(m)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":18,\"itemName\":\"油管内径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":19,\"itemName\":\"套管内径(mm)\",\"itemValue\":\"\"},");
						
						
						result_json.append("{\"id\":20,\"itemName\":\"一级杆级别\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":21,\"itemName\":\"一级杆外径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":22,\"itemName\":\"一级杆内径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":23,\"itemName\":\"一级杆长度(m)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":24,\"itemName\":\"二级杆级别\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":25,\"itemName\":\"二级杆外径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":26,\"itemName\":\"二级杆内径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":27,\"itemName\":\"二级杆长度(m)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":28,\"itemName\":\"三级杆级别\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":29,\"itemName\":\"三级杆外径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":30,\"itemName\":\"三级杆内径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":31,\"itemName\":\"三级杆长度(m)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":32,\"itemName\":\"四级杆级别\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":33,\"itemName\":\"四级杆外径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":34,\"itemName\":\"四级杆内径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":35,\"itemName\":\"四级杆长度(m)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":36,\"itemName\":\"工况干预\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":37,\"itemName\":\"净毛比(小数)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":38,\"itemName\":\"净毛值(m^3/d)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":39,\"itemName\":\"反演液面校正值(MPa)\",\"itemValue\":\"\"}");
					}
//					result_json.append("{\"id\":40,\"itemName\":\"更新时间\",\"itemValue\":\""+updateTime+"\"}");
				}else if(StringManagerUtils.stringToInteger(deviceCalculateDataType)==2){
					type = new TypeToken<PCPProductionData>() {}.getType();
					PCPProductionData pcpProductionData=gson.fromJson(productionData, type);
					if(pcpProductionData!=null){
						result_json.append("{\"id\":1,\"itemName\":\"原油密度(g/cm^3)\",\"itemValue\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getCrudeOilDensity():"")+"\"},");
						result_json.append("{\"id\":2,\"itemName\":\"水密度(g/cm^3)\",\"itemValue\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getWaterDensity():"")+"\"},");
						result_json.append("{\"id\":3,\"itemName\":\"天然气相对密度\",\"itemValue\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getNaturalGasRelativeDensity():"")+"\"},");
						result_json.append("{\"id\":4,\"itemName\":\"饱和压力(MPa)\",\"itemValue\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getSaturationPressure():"")+"\"},");
						
						result_json.append("{\"id\":5,\"itemName\":\""+reservoirShow+"中部深度(m)\",\"itemValue\":\""+(pcpProductionData.getReservoir()!=null?pcpProductionData.getReservoir().getDepth():"")+"\"},");
						result_json.append("{\"id\":6,\"itemName\":\""+reservoirShow+"中部温度(℃)\",\"itemValue\":\""+(pcpProductionData.getReservoir()!=null?pcpProductionData.getReservoir().getTemperature():"")+"\"},");
						
						result_json.append("{\"id\":7,\"itemName\":\"油压(MPa)\",\"itemValue\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getTubingPressure():"")+"\"},");
						result_json.append("{\"id\":8,\"itemName\":\"套压(MPa)\",\"itemValue\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getCasingPressure():"")+"\"},");
						result_json.append("{\"id\":9,\"itemName\":\"井口温度(℃)\",\"itemValue\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getWellHeadTemperature():"")+"\"},");
						result_json.append("{\"id\":10,\"itemName\":\"含水率(%)\",\"itemValue\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getWaterCut():"")+"\"},");
						result_json.append("{\"id\":11,\"itemName\":\"生产气油比(m^3/t)\",\"itemValue\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getProductionGasOilRatio():"")+"\"},");
						result_json.append("{\"id\":12,\"itemName\":\"动液面(m)\",\"itemValue\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getProducingfluidLevel():"")+"\"},");
						result_json.append("{\"id\":13,\"itemName\":\"泵挂(m)\",\"itemValue\":\""+(pcpProductionData.getProduction()!=null?pcpProductionData.getProduction().getPumpSettingDepth():"")+"\"},");
						
						result_json.append("{\"id\":14,\"itemName\":\"泵筒长(m)\",\"itemValue\":\""+(pcpProductionData.getPump()!=null?pcpProductionData.getPump().getBarrelLength():"")+"\"},");
						result_json.append("{\"id\":15,\"itemName\":\"泵级数\",\"itemValue\":\""+(pcpProductionData.getPump()!=null?pcpProductionData.getPump().getBarrelSeries():"")+"\"},");
						result_json.append("{\"id\":16,\"itemName\":\"转子直径(mm)\",\"itemValue\":\""+(pcpProductionData.getPump()!=null?(pcpProductionData.getPump().getRotorDiameter()*1000):"")+"\"},");
						result_json.append("{\"id\":17,\"itemName\":\"公称排量(ml/转)\",\"itemValue\":\""+(pcpProductionData.getPump()!=null?(pcpProductionData.getPump().getQPR()*1000*1000):"")+"\"},");
						
						result_json.append("{\"id\":18,\"itemName\":\"油管内径(mm)\",\"itemValue\":\""+(pcpProductionData.getTubingString()!=null&&pcpProductionData.getTubingString().getEveryTubing()!=null&&pcpProductionData.getTubingString().getEveryTubing().size()>0?(pcpProductionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000):"")+"\"},");
						result_json.append("{\"id\":19,\"itemName\":\"套管内径(mm)\",\"itemValue\":\""+(pcpProductionData.getCasingString()!=null&&pcpProductionData.getCasingString().getEveryCasing()!=null&&pcpProductionData.getCasingString().getEveryCasing().size()>0?(pcpProductionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000):"")+"\"},");
						
						String rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="";
						String rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="";
						String rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="";
						String rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="";
						if(pcpProductionData.getRodString()!=null&&pcpProductionData.getRodString().getEveryRod()!=null&&pcpProductionData.getRodString().getEveryRod().size()>0){
							if(pcpProductionData.getRodString().getEveryRod().size()>0){
								rodGrade1=pcpProductionData.getRodString().getEveryRod().get(0).getGrade();
								rodOutsideDiameter1=pcpProductionData.getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
								rodInsideDiameter1=pcpProductionData.getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
								rodLength1=pcpProductionData.getRodString().getEveryRod().get(0).getLength()+"";
							}
							if(pcpProductionData.getRodString().getEveryRod().size()>1){
								rodGrade2=pcpProductionData.getRodString().getEveryRod().get(1).getGrade();
								rodOutsideDiameter2=pcpProductionData.getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
								rodInsideDiameter2=pcpProductionData.getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
								rodLength2=pcpProductionData.getRodString().getEveryRod().get(1).getLength()+"";
							}
							if(pcpProductionData.getRodString().getEveryRod().size()>2){
								rodGrade3=pcpProductionData.getRodString().getEveryRod().get(2).getGrade();
								rodOutsideDiameter3=pcpProductionData.getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
								rodInsideDiameter3=pcpProductionData.getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
								rodLength3=pcpProductionData.getRodString().getEveryRod().get(2).getLength()+"";
							}
							if(pcpProductionData.getRodString().getEveryRod().size()>3){
								rodGrade4=pcpProductionData.getRodString().getEveryRod().get(3).getGrade();
								rodOutsideDiameter4=pcpProductionData.getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
								rodInsideDiameter4=pcpProductionData.getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
								rodLength4=pcpProductionData.getRodString().getEveryRod().get(3).getLength()+"";
							}
						}
						result_json.append("{\"id\":20,\"itemName\":\"一级杆级别\",\"itemValue\":\""+rodGrade1+"\"},");
						result_json.append("{\"id\":21,\"itemName\":\"一级杆外径(mm)\",\"itemValue\":\""+rodOutsideDiameter1+"\"},");
						result_json.append("{\"id\":22,\"itemName\":\"一级杆内径(mm)\",\"itemValue\":\""+rodInsideDiameter1+"\"},");
						result_json.append("{\"id\":23,\"itemName\":\"一级杆长度(m)\",\"itemValue\":\""+rodLength1+"\"},");
						
						result_json.append("{\"id\":24,\"itemName\":\"二级杆级别\",\"itemValue\":\""+rodGrade2+"\"},");
						result_json.append("{\"id\":25,\"itemName\":\"二级杆外径(mm)\",\"itemValue\":\""+rodOutsideDiameter2+"\"},");
						result_json.append("{\"id\":26,\"itemName\":\"二级杆内径(mm)\",\"itemValue\":\""+rodInsideDiameter2+"\"},");
						result_json.append("{\"id\":27,\"itemName\":\"二级杆长度(m)\",\"itemValue\":\""+rodLength2+"\"},");
						
						result_json.append("{\"id\":28,\"itemName\":\"三级杆级别\",\"itemValue\":\""+rodGrade3+"\"},");
						result_json.append("{\"id\":29,\"itemName\":\"三级杆外径(mm)\",\"itemValue\":\""+rodOutsideDiameter3+"\"},");
						result_json.append("{\"id\":30,\"itemName\":\"三级杆内径(mm)\",\"itemValue\":\""+rodInsideDiameter3+"\"},");
						result_json.append("{\"id\":31,\"itemName\":\"三级杆长度(m)\",\"itemValue\":\""+rodLength3+"\"},");
						
						result_json.append("{\"id\":32,\"itemName\":\"四级杆级别\",\"itemValue\":\""+rodGrade4+"\"},");
						result_json.append("{\"id\":33,\"itemName\":\"四级杆外径(mm)\",\"itemValue\":\""+rodOutsideDiameter4+"\"},");
						result_json.append("{\"id\":34,\"itemName\":\"四级杆内径(mm)\",\"itemValue\":\""+rodInsideDiameter4+"\"},");
						result_json.append("{\"id\":35,\"itemName\":\"四级杆长度(m)\",\"itemValue\":\""+rodLength4+"\"},");
						
						result_json.append("{\"id\":36,\"itemName\":\"净毛比(小数)\",\"itemValue\":\""+(pcpProductionData.getManualIntervention()!=null?pcpProductionData.getManualIntervention().getNetGrossRatio():"")+"\"},");
						result_json.append("{\"id\":37,\"itemName\":\"净毛值(m^3/d)\",\"itemValue\":\""+(pcpProductionData.getManualIntervention()!=null?pcpProductionData.getManualIntervention().getNetGrossValue():"")+"\"}");
					}else{
						result_json.append("{\"id\":1,\"itemName\":\"原油密度(g/cm^3)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":2,\"itemName\":\"水密度(g/cm^3)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":3,\"itemName\":\"天然气相对密度\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":4,\"itemName\":\"饱和压力(MPa)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":5,\"itemName\":\""+reservoirShow+"中部深度(m)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":6,\"itemName\":\""+reservoirShow+"中部温度(℃)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":7,\"itemName\":\"油压(MPa)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":8,\"itemName\":\"套压(MPa)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":9,\"itemName\":\"井口温度(℃)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":10,\"itemName\":\"含水率(%)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":11,\"itemName\":\"生产气油比(m^3/t)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":12,\"itemName\":\"动液面(m)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":13,\"itemName\":\"泵挂(m)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":14,\"itemName\":\"泵筒长(m)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":15,\"itemName\":\"泵级数\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":16,\"itemName\":\"转子直径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":17,\"itemName\":\"公称排量(ml/转)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":18,\"itemName\":\"油管内径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":19,\"itemName\":\"套管内径(mm)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":20,\"itemName\":\"一级杆级别\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":21,\"itemName\":\"一级杆外径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":22,\"itemName\":\"一级杆内径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":23,\"itemName\":\"一级杆长度(m)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":24,\"itemName\":\"二级杆级别\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":25,\"itemName\":\"二级杆外径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":26,\"itemName\":\"二级杆内径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":27,\"itemName\":\"二级杆长度(m)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":28,\"itemName\":\"三级杆级别\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":29,\"itemName\":\"三级杆外径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":30,\"itemName\":\"三级杆内径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":31,\"itemName\":\"三级杆长度(m)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":32,\"itemName\":\"四级杆级别\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":33,\"itemName\":\"四级杆外径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":34,\"itemName\":\"四级杆内径(mm)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":35,\"itemName\":\"四级杆长度(m)\",\"itemValue\":\"\"},");
						
						result_json.append("{\"id\":36,\"itemName\":\"净毛比(小数)\",\"itemValue\":\"\"},");
						result_json.append("{\"id\":37,\"itemName\":\"净毛值(m^3/d)\",\"itemValue\":\"\"}");
					}
//					result_json.append("{\"id\":38,\"itemName\":\"更新时间\",\"itemValue\":\""+updateTime+"\"}");
				}
			}
			result_json.append("]");
			resultNameBuff.append("]");
			result_json.append(",\"resultNameList\":"+resultNameBuff);
			result_json.append(",\"applicationScenarios\":\""+applicationScenarios+"\"");
			result_json.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getDeviceVideoInfo(String deviceId,String deviceType,String orgId) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer videoKeyDropdownData = new StringBuffer();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",flex:1 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"itemName\",flex:1 ,children:[] },"
				+ "{ \"header\":\"监控路径\",\"dataIndex\":\"videoUrl\",flex:5 ,children:[] },"
				+ "{ \"header\":\"视频密钥\",\"dataIndex\":\"videoKey\",flex:1 ,children:[] }"
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
		result_json.append("{\"id\":1,\"itemName\":\"视频1\",\"videoUrl\":\""+videoUrl1+"\",\"videoKey\":\""+videoKey1+"\"},");
		result_json.append("{\"id\":2,\"itemName\":\"视频2\",\"videoUrl\":\""+videoUrl2+"\",\"videoKey\":\""+videoKey2+"\"},");
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getVideoKeyData(String orgId){
		StringBuffer result_json = new StringBuffer();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",flex:1 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"account\",flex:1 ,children:[] },"
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
	public String getRPCDeviceInformationData(String recordId) {
		StringBuffer result_json = new StringBuffer();
		String sql = "select t.id,t.wellname,t.orgid,t.orgName,t.devicetype,t.devicetypename,t.applicationscenarios,t.applicationScenariosName,t.signinid,t.slave,"
				+ "t.instancecode,t.instancename,t.alarminstancecode,t.alarminstancename,t.sortnum "
				+ "from viw_rpcdevice  t where t.id="+recordId;
		String json = "";
		List<?> list = this.findCallSql(sql);
		if(list.size()>0){
			result_json.append("{\"success\":true,");
			Object[] obj = (Object[]) list.get(0);
			result_json.append("\"id\":"+obj[0]+",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"orgId\":"+obj[2]+",");
			result_json.append("\"orgName\":\""+obj[3]+"\",");
			result_json.append("\"deviceType\":"+obj[4]+",");
			result_json.append("\"deviceTypeName\":\""+obj[5]+"\",");
			result_json.append("\"applicationScenarios\":"+obj[6]+",");
			result_json.append("\"applicationScenariosName\":\""+obj[7]+"\",");
			result_json.append("\"signInId\":\""+obj[8]+"\",");
			result_json.append("\"slave\":\""+obj[9]+"\",");
			result_json.append("\"instanceCode\":\""+obj[10]+"\",");
			result_json.append("\"instanceName\":\""+obj[11]+"\",");
			result_json.append("\"alarmInstanceCode\":\""+obj[12]+"\",");
			result_json.append("\"alarminstanceName\":\""+obj[13]+"\",");
			result_json.append("\"sortNum\":\""+obj[14]+"\"");
			result_json.append("}");
		}
		
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	@SuppressWarnings("rawtypes")
	public String getBatchAddDeviceTableInfo(String deviceTypeStr,String applicationScenarios,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer displayInstanceDropdownData = new StringBuffer();
		StringBuffer reportInstanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		StringBuffer applicationScenariosDropdownData = new StringBuffer();
		StringBuffer resultNameDropdownData = new StringBuffer();
		String ddicName="deviceInfo_RPCDeviceBatchAdd";
		int protocolType=0;
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		if(deviceType>=200&&deviceType<300){
			ddicName="deviceInfo_PCPDeviceBatchAdd";
			protocolType=1;
		}
		
		String instanceSql="select t.name from tbl_protocolinstance t  order by t.sort";
		String displayInstanceSql="select t.name from tbl_protocoldisplayinstance t  order by t.sort";
		String reportInstanceSql="select t.name from tbl_protocolreportinstance t  order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t  order by t.sort";
		String applicationScenariosSql="select c.itemname from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' order by c.itemvalue desc";
		String resultSql="select t.resultname from tbl_rpc_worktype t order by t.resultcode";
		String columns=service.showTableHeadersColumns(ddicName);
		instanceDropdownData.append("[");
		displayInstanceDropdownData.append("[");
		reportInstanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");
		applicationScenariosDropdownData.append("[");
		resultNameDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
		List<?> reportInstanceList = this.findCallSql(reportInstanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		List<?> applicationScenariosList = this.findCallSql(applicationScenariosSql);
		List<?> resultList = this.findCallSql(resultSql);
		
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
		
		
		for(int i=0;i<applicationScenariosList.size();i++){
			applicationScenariosDropdownData.append("'"+applicationScenariosList.get(i)+"',");
		}
		if(applicationScenariosDropdownData.toString().endsWith(",")){
			applicationScenariosDropdownData.deleteCharAt(applicationScenariosDropdownData.length() - 1);
		}
		
		if(resultList.size()>0){
			resultNameDropdownData.append("\"不干预\"");
			for(int i=0;i<resultList.size();i++){
				resultNameDropdownData.append(",\""+resultList.get(i).toString()+"\"");
			}
		}
		
		
		instanceDropdownData.append("]");
		displayInstanceDropdownData.append("]");
		reportInstanceDropdownData.append("]");
		alarmInstanceDropdownData.append("]");
		applicationScenariosDropdownData.append("]");
		resultNameDropdownData.append("]");
		
		String json = "";
		result_json.append("{\"success\":true,"
				+ "\"totalCount\":"+recordCount+","
				+ "\"applicationScenarios\":"+applicationScenarios+","
				+ "\"instanceDropdownData\":"+instanceDropdownData.toString()+","
				+ "\"displayInstanceDropdownData\":"+displayInstanceDropdownData.toString()+","
				+ "\"reportInstanceDropdownData\":"+reportInstanceDropdownData.toString()+","
				+ "\"alarmInstanceDropdownData\":"+alarmInstanceDropdownData.toString()+","
				+ "\"applicationScenariosDropdownData\":"+applicationScenariosDropdownData.toString()+","
				+ "\"resultNameDropdownData\":"+resultNameDropdownData.toString()+",");
		if(protocolType==0){
			String pumpingModelInfo=this.getPumpingModelInfo();
			result_json.append("\"pumpingModelInfo\":"+pumpingModelInfo+",");
		}
		
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
			String rpcSql = "select t.id from tbl_device t where t.signinid='"+signinId+"' and to_number(t.slave)="+slave;
			List<?> rpcList = this.findCallSql(rpcSql);
			if (rpcList.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgeDeviceExistOrNotByIpPortAndSlave(String deviceTypeStr,String ipPort,String slaveStr) {
		boolean flag = false;
		int slave=StringManagerUtils.stringToInteger(slaveStr);
		if (StringManagerUtils.isNotNull(ipPort)&&StringManagerUtils.isNotNull(slaveStr)) {
			String rpcSql = "select t.id from tbl_device t where t.ipPort='"+ipPort+"' and to_number(t.slave)="+slave;
			List<?> rpcList = this.findCallSql(rpcSql);
			if (rpcList.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgePumpingModelExistOrNot(String manufacturer,String model) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(manufacturer)&&StringManagerUtils.isNotNull(model)) {
			String sql = "select t.id from tbl_pumpingmodel t where t.manufacturer='"+manufacturer+"' and upper(t.model)=upper('"+model+"')";
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
	
	public String getUpstreamAndDownstreamInteractionDeviceList(String orgId,String deviceName,String deviceType,Page pager) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String deviceTableName="tbl_rpcdevice";
		String tableName="tbl_rpcacqdata_latest";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="tbl_pcpacqdata_latest";
			deviceTableName="tbl_pcpdevice";
		}
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50,children:[] },"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"wellName\",flex:1,children:[] },"
				+ "{ \"header\":\"通信状态\",\"dataIndex\":\"commStatusName\",width:90,children:[] },"
				+ "{ \"header\":\"注册包ID\",\"dataIndex\":\"signinId\",flex:1,children:[] },"
				+ "{ \"header\":\"设备从地址\",\"dataIndex\":\"slave\",flex:1,children:[] }"
				+ "]";
		
		String sql="select t.id,t.wellname,"
				+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
				+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'),t.signinid,t.slave "
				+ " from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on t2.wellid=t.id";
		sql+= " where  t.orgid in ("+orgId+") "
				+ " and t.instancecode in ( select t3.code from tbl_protocolinstance t3 where t3.acqprotocoltype ='private-rpc' or t3.ctrlprotocoltype ='private-rpc' )";
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.wellName='"+deviceName+"'";
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
		RPCCalculateRequestData calculateRequestData=null;
		String result_json="";
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String sql="select t.productiondata,t.balanceinfo,t.stroke,"
				+ " t.pumpingmodelid,"
				+ " t2.manufacturer,t2.model,t2.crankrotationdirection,t2.offsetangleofcrank,t2.crankgravityradius,t2.singlecrankweight,t2.singlecrankpinweight,t2.structuralunbalance,"
				+ " t2.prtf"
				+ " from tbl_rpcdevice t "
				+ " left outer join tbl_pumpingmodel t2 on t.pumpingmodelid=t2.id"
				+ " where t.id= "+deviceId;
		List<?> list = this.findCallSql(sql);
		
		if(list.size()>0){
			Object[] object = (Object[]) list.get(0);
			String productionData=object[0].toString(); 
			type = new TypeToken<RPCCalculateRequestData>() {}.getType();
			calculateRequestData=gson.fromJson(productionData, type);
			if(calculateRequestData!=null){
				int pumpingModelId=StringManagerUtils.stringToInteger(object[3]+"");
	        	if(pumpingModelId>0){
	        		calculateRequestData.setPumpingUnit(new RPCCalculateRequestData.PumpingUnit());
	        		String balanceInfo=object[1]+"";
					type = new TypeToken<RPCCalculateRequestData.Balance>() {}.getType();
					RPCCalculateRequestData.Balance balance=gson.fromJson(balanceInfo, type);
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
								calculateRequestData.getPumpingUnit().setPRTF(new RPCCalculateRequestData.PRTF());
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
	
	public String getWaterCutRawData(String signinId,String slave) throws IOException, SQLException{
		String result_json = "";
		List<StringBuffer> groupList=new ArrayList<StringBuffer>();
		int totals=0;
		String acqTime="";
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50,children:[] },"
				+ "{ \"header\":\"采样时间\",\"dataIndex\":\"pointAcqTime\",flex:2,children:[] },"
				+ "{ \"header\":\"采样间隔(ms)\",\"dataIndex\":\"interval\",flex:1,children:[] },"
				+ "{ \"header\":\"含水率(%)\",\"dataIndex\":\"waterCut\",flex:1,children:[] },"
				+ "{ \"header\":\"压力(MPa)\",\"dataIndex\":\"tubingPressure\",flex:1,children:[] },"
				+ "{ \"header\":\"位置\",\"dataIndex\":\"position\",flex:1,children:[] }"
				+ "]";
		result_json+="{ \"success\":true,\"columns\":"+columns+",";
		result_json+="\"totalRoot\":[";
		if(StringManagerUtils.isNotNull(signinId) && StringManagerUtils.isNotNull(slave)){
			String url=Config.getInstance().configFile.getAd().getRpc().getReadTopicReq();
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
		System.out.println("result_json长度:"+result_json.length());
		return result_json.replaceAll("\"null\"", "\"\"");
	}
	
	public String getWaterCutRawData2(String signinId,String slave) throws IOException, SQLException{
		String result = "";
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		if(StringManagerUtils.isNotNull(signinId) && StringManagerUtils.isNotNull(slave)){
			try{
				String url=Config.getInstance().configFile.getAd().getRpc().getReadTopicReq();
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
			String url=Config.getInstance().configFile.getAd().getRpc().getReadTopicReq();
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
	
	public boolean exportRPCDeviceInfoDetailsData(User user,HttpServletResponse response,String fileName,String title,String  orgId,String applicationScenarios,String wellInformationName) {
		try{
			StringBuffer result_json = new StringBuffer();
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			String tableName="viw_rpcdevice";
			DataDictionary ddic = null;
			String ddicName="deviceInfo_RPCDeviceBatchAdd";
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			String head=StringUtils.join(ddic.getHeaders(), ",");
			String field=StringUtils.join(ddic.getFields(), ",");
			
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			
			List<Object> headRow = new ArrayList<>();
			List<String> realColumns=new ArrayList<>();
			for(int i=0;i<heads.length;i++){
				if(StringManagerUtils.stringToInteger(applicationScenarios)==0){
					if(  !("crudeOilDensity".equalsIgnoreCase(columns[i]) 
							|| "saturationPressure".equalsIgnoreCase(columns[i]) 
							|| "waterCut".equalsIgnoreCase(columns[i]) 
							|| "weightWaterCut".equalsIgnoreCase(columns[i]) 
							|| "productionGasOilRatio".equalsIgnoreCase(columns[i]) 
							) ){
						String thishead=heads[i];
						if("reservoirDepth".equalsIgnoreCase(columns[i]) || "reservoirTemperature".equalsIgnoreCase(columns[i])){
							thishead=thishead.replace("油层", "煤层");
						}
						headRow.add(thishead);
						realColumns.add(columns[i]);
					}
				}else{
					headRow.add(heads[i]);
					realColumns.add(columns[i]);
				}
			}
		    List<List<Object>> sheetDataList = new ArrayList<>();
		    sheetDataList.add(headRow);
			
			String sql = "select id,orgName,wellName,applicationScenariosName,"//3
					+ " instanceName,displayInstanceName,alarmInstanceName,reportInstanceName,"//7
					+ " tcptype,signInId,slave,t.peakdelay,"//11
					+ " videoUrl1,videoKeyName1,videoUrl2,videoKeyName2,"//15
					+ "sortNum,statusName,"//17
					+ " t.productiondata,"//18
					+ " t.manufacturer,t.model,t.stroke,"//21
					+ " decode( lower(t.crankrotationdirection),'clockwise','顺时针','anticlockwise','逆时针','' ) as crankrotationdirection,"//22
					+ " t.offsetangleofcrank,t.crankgravityradius,t.singlecrankweight,t.singlecrankpinweight,t.structuralunbalance,"//27
					+ " t.balanceinfo"//28
					+ " from "+tableName+" t "
					+ " where t.applicationScenarios="+applicationScenarios
					+ " and t.orgid in ("+orgId+" )";
			if (StringManagerUtils.isNotNull(wellInformationName)) {
				sql+= " and t.wellname like '%" + wellInformationName+ "%'";
			}
			
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
				String productionDataStr=obj[18]+"";
				String balanceInfo=obj[28]+"";
				String videoUrl1="",videoUrl2="";
				String crudeOilDensity="",waterDensity="",naturalGasRelativeDensity="",saturationPressure="",
						reservoirDepth="",reservoirTemperature="",
						tubingPressure="",casingPressure="",wellHeadTemperature="",waterCut="",productionGasOilRatio="",producingfluidLevel="",pumpSettingDepth="",
						barrelType="",pumpGrade="",pumpBoreDiameter="",plungerLength="",
						tubingStringInsideDiameter="",casingStringInsideDiameter="",
						rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="",
						rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="",
						rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="",
						rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="",
						netGrossRatio="",netGrossValue="";
				String balanceWeight="",balancePosition="";
				
				
				if(StringManagerUtils.isNotNull(productionDataStr)){
					type = new TypeToken<RPCProductionData>() {}.getType();
					RPCProductionData productionData=gson.fromJson(productionDataStr, type);
					if(productionData!=null){
						if(productionData.getFluidPVT()!=null){
							crudeOilDensity=productionData.getFluidPVT().getCrudeOilDensity()+"";
							waterDensity=productionData.getFluidPVT().getWaterDensity()+"";
							naturalGasRelativeDensity=productionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
							saturationPressure=productionData.getFluidPVT().getSaturationPressure()+"";
						}
						if(productionData.getReservoir()!=null){
							reservoirDepth=productionData.getReservoir().getDepth()+"";
							reservoirTemperature=productionData.getReservoir().getTemperature()+"";
						}
						if(productionData.getProduction()!=null){
							tubingPressure=productionData.getProduction().getTubingPressure()+"";
							casingPressure=productionData.getProduction().getCasingPressure()+"";
							wellHeadTemperature=productionData.getProduction().getWellHeadTemperature()+"";
							waterCut=productionData.getProduction().getWaterCut()+"";
							productionGasOilRatio=productionData.getProduction().getProductionGasOilRatio()+"";
							producingfluidLevel=productionData.getProduction().getProducingfluidLevel()+"";
							pumpSettingDepth=productionData.getProduction().getPumpSettingDepth()+"";
						}
						if(productionData.getPump()!=null){
							if("L".equalsIgnoreCase(productionData.getPump().getBarrelType())){
								barrelType="组合泵";
							}else{
								barrelType="整筒泵";
							}
							pumpGrade=productionData.getPump().getPumpGrade()+"";
							pumpBoreDiameter=productionData.getPump().getPumpBoreDiameter()*1000+"";
							plungerLength=productionData.getPump().getPlungerLength()+"";
						}
						if(productionData.getTubingString()!=null && productionData.getTubingString().getEveryTubing()!=null && productionData.getTubingString().getEveryTubing().size()>0){
							tubingStringInsideDiameter=productionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000+"";
						}
						if(productionData.getCasingString()!=null && productionData.getCasingString().getEveryCasing()!=null && productionData.getCasingString().getEveryCasing().size()>0){
							casingStringInsideDiameter=productionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000+"";
						}
						if(productionData.getRodString()!=null && productionData.getRodString().getEveryRod()!=null && productionData.getRodString().getEveryRod().size()>0){
							rodGrade1=productionData.getRodString().getEveryRod().get(0).getGrade()+"";
							rodOutsideDiameter1=productionData.getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
							rodInsideDiameter1=productionData.getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
							rodLength1=productionData.getRodString().getEveryRod().get(0).getLength()+"";
							if(productionData.getRodString().getEveryRod().size()>1){
								rodGrade2=productionData.getRodString().getEveryRod().get(1).getGrade()+"";
								rodOutsideDiameter2=productionData.getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
								rodInsideDiameter2=productionData.getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
								rodLength2=productionData.getRodString().getEveryRod().get(1).getLength()+"";
								if(productionData.getRodString().getEveryRod().size()>2){
									rodGrade3=productionData.getRodString().getEveryRod().get(2).getGrade()+"";
									rodOutsideDiameter3=productionData.getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
									rodInsideDiameter3=productionData.getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
									rodLength3=productionData.getRodString().getEveryRod().get(2).getLength()+"";
									if(productionData.getRodString().getEveryRod().size()>3){
										rodGrade4=productionData.getRodString().getEveryRod().get(3).getGrade()+"";
										rodOutsideDiameter4=productionData.getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
										rodInsideDiameter4=productionData.getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
										rodLength4=productionData.getRodString().getEveryRod().get(3).getLength()+"";
									}
								}
							}
						}
						if(productionData.getManualIntervention()!=null){
							netGrossRatio=productionData.getManualIntervention().getNetGrossRatio()+"";
							netGrossValue=productionData.getManualIntervention().getNetGrossValue()+"";
						}
					}
				}
				
				if(StringManagerUtils.isNotNull(balanceInfo)){
					type = new TypeToken<RPCCalculateRequestData.Balance>() {}.getType();
					RPCCalculateRequestData.Balance balance=gson.fromJson(balanceInfo, type);
					if(balance!=null && balance.getEveryBalance().size()>0){
						for(int j=0;j<balance.getEveryBalance().size();j++){
							balanceWeight+=balance.getEveryBalance().get(j).getWeight()+"";
							balancePosition+=balance.getEveryBalance().get(j).getPosition()+"";
							if(j<balance.getEveryBalance().size()-1){
								balanceWeight+=",";
								balancePosition+=",";
							}
						}
					}
				}
				
				
				result_json.append("{\"id\":\""+(i+1)+"\",");
				result_json.append("\"orgName\":\""+obj[1]+"\",");
				result_json.append("\"wellName\":\""+obj[2]+"\",");
				result_json.append("\"applicationScenariosName\":\""+obj[3]+"\",");
				result_json.append("\"instanceName\":\""+obj[4]+"\",");
				result_json.append("\"displayInstanceName\":\""+obj[5]+"\",");
				result_json.append("\"alarmInstanceName\":\""+obj[6]+"\",");
				result_json.append("\"reportInstanceName\":\""+obj[7]+"\",");
				
				result_json.append("\"tcpType\":\""+(obj[8]+"").replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
				result_json.append("\"signInId\":\""+obj[9]+"\",");
				result_json.append("\"slave\":\""+obj[10]+"\",");
				result_json.append("\"peakDelay\":\""+obj[11]+"\",");
				
				result_json.append("\"videoUrl1\":\""+obj[12]+"\",");
				result_json.append("\"videoKeyName1\":\""+obj[13]+"\",");
				result_json.append("\"videoUrl2\":\""+obj[14]+"\",");
				result_json.append("\"videoKeyName2\":\""+obj[15]+"\",");
				
				result_json.append("\"sortNum\":\""+obj[16]+"\",");
				result_json.append("\"statusName\":\""+obj[17]+"\",");
				
				result_json.append("\"crudeOilDensity\":\""+crudeOilDensity+"\",");
				result_json.append("\"waterDensity\":\""+waterDensity+"\",");
				result_json.append("\"naturalGasRelativeDensity\":\""+naturalGasRelativeDensity+"\",");
				result_json.append("\"saturationPressure\":\""+saturationPressure+"\",");
				result_json.append("\"reservoirDepth\":\""+reservoirDepth+"\",");
				result_json.append("\"reservoirTemperature\":\""+reservoirTemperature+"\",");
				result_json.append("\"tubingPressure\":\""+tubingPressure+"\",");
				result_json.append("\"casingPressure\":\""+casingPressure+"\",");
				result_json.append("\"wellHeadTemperature\":\""+wellHeadTemperature+"\",");
				result_json.append("\"waterCut\":\""+waterCut+"\",");
				result_json.append("\"productionGasOilRatio\":\""+productionGasOilRatio+"\",");
				result_json.append("\"producingfluidLevel\":\""+producingfluidLevel+"\",");
				result_json.append("\"pumpSettingDepth\":\""+pumpSettingDepth+"\",");
				result_json.append("\"barrelType\":\""+barrelType+"\",");
				result_json.append("\"pumpGrade\":\""+pumpGrade+"\",");
				result_json.append("\"pumpBoreDiameter\":\""+pumpBoreDiameter+"\",");
				result_json.append("\"plungerLength\":\""+plungerLength+"\",");
				result_json.append("\"tubingStringInsideDiameter\":\""+tubingStringInsideDiameter+"\",");
				result_json.append("\"casingStringInsideDiameter\":\""+casingStringInsideDiameter+"\",");
				result_json.append("\"rodGrade1\":\""+rodGrade1+"\",");
				result_json.append("\"rodOutsideDiameter1\":\""+rodOutsideDiameter1+"\",");
				result_json.append("\"rodInsideDiameter1\":\""+rodInsideDiameter1+"\",");
				result_json.append("\"rodLength1\":\""+rodLength1+"\",");
				result_json.append("\"rodGrade2\":\""+rodGrade2+"\",");
				result_json.append("\"rodOutsideDiameter2\":\""+rodOutsideDiameter2+"\",");
				result_json.append("\"rodInsideDiameter2\":\""+rodInsideDiameter2+"\",");
				result_json.append("\"rodLength2\":\""+rodLength2+"\",");
				result_json.append("\"rodGrade3\":\""+rodGrade3+"\",");
				result_json.append("\"rodOutsideDiameter3\":\""+rodOutsideDiameter3+"\",");
				result_json.append("\"rodInsideDiameter3\":\""+rodInsideDiameter3+"\",");
				result_json.append("\"rodLength3\":\""+rodLength3+"\",");
				result_json.append("\"rodGrade4\":\""+rodGrade4+"\",");
				result_json.append("\"rodOutsideDiameter4\":\""+rodOutsideDiameter4+"\",");
				result_json.append("\"rodInsideDiameter4\":\""+rodInsideDiameter4+"\",");
				result_json.append("\"rodLength4\":\""+rodLength4+"\",");
				
				result_json.append("\"netGrossRatio\":\""+netGrossRatio+"\",");
				result_json.append("\"netGrossValue\":\""+netGrossValue+"\",");
				
				result_json.append("\"manufacturer\":\""+obj[19]+"\",");
				result_json.append("\"model\":\""+obj[20]+"\",");
				result_json.append("\"stroke\":\""+obj[21]+"\",");
				result_json.append("\"crankRotationDirection\":\""+obj[22]+"\",");
				result_json.append("\"offsetAngleOfCrank\":\""+obj[23]+"\",");
				result_json.append("\"crankGravityRadius\":\""+obj[24]+"\",");
				result_json.append("\"singleCrankWeight\":\""+obj[25]+"\",");
				result_json.append("\"singleCrankPinWeight\":\""+obj[26]+"\",");
				result_json.append("\"structuralUnbalance\":\""+obj[27]+"\",");
				
				
				result_json.append("\"balanceWeight\":\""+balanceWeight+"\",");
				result_json.append("\"balancePosition\":\""+balancePosition+"\"}");
				
				jsonObject = JSONObject.fromObject(result_json.toString().replaceAll("null", ""));
				for (int j = 0; j < realColumns.size(); j++) {
					if(jsonObject.has(realColumns.get(j))){
						record.add(jsonObject.getString(realColumns.get(j)));
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
	
	public boolean exportPCPDeviceInfoDetailsData(User user,HttpServletResponse response,String fileName,String title,String  orgId,String applicationScenarios,String wellInformationName) {
		try{
			StringBuffer result_json = new StringBuffer();
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			String tableName="viw_pcpdevice";
			DataDictionary ddic = null;
			String ddicName="deviceInfo_PCPDeviceBatchAdd";
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			String head=StringUtils.join(ddic.getHeaders(), ",");
			String field=StringUtils.join(ddic.getFields(), ",");
			
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			
			List<Object> headRow = new ArrayList<>();
			List<String> realColumns=new ArrayList<>();
			for(int i=0;i<heads.length;i++){
				if(StringManagerUtils.stringToInteger(applicationScenarios)==0){
					if(  !("crudeOilDensity".equalsIgnoreCase(columns[i]) 
							|| "saturationPressure".equalsIgnoreCase(columns[i]) 
							|| "waterCut".equalsIgnoreCase(columns[i]) 
							|| "weightWaterCut".equalsIgnoreCase(columns[i]) 
							|| "productionGasOilRatio".equalsIgnoreCase(columns[i]) 
							) ){
						String thishead=heads[i];
						if("reservoirDepth".equalsIgnoreCase(columns[i]) || "reservoirTemperature".equalsIgnoreCase(columns[i])){
							thishead=thishead.replace("油层", "煤层");
						}
						headRow.add(thishead);
						realColumns.add(columns[i]);
					}
				}else{
					headRow.add(heads[i]);
					realColumns.add(columns[i]);
				}
			}
		    List<List<Object>> sheetDataList = new ArrayList<>();
		    sheetDataList.add(headRow);
			
			String sql = "select id,orgName,wellName,applicationScenariosName,"//3
					+ " instanceName,displayInstanceName,alarmInstanceName,reportInstanceName,"//7
					+ " tcptype,signInId,slave,t.peakdelay,"//11
					+ " videoUrl1,videoKeyName1,videoUrl2,videoKeyName2,"//15
					+ " sortNum,statusName,"//17
					+ " t.productiondata"//18
					+ " from "+tableName+" t "
					+ " where t.applicationScenarios="+applicationScenarios
					+ " and t.orgid in ("+orgId+" )";
			if (StringManagerUtils.isNotNull(wellInformationName)) {
				sql+= " and t.wellname like '%" + wellInformationName+ "%'";
			}
			
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
				
				String productionDataStr=obj[18]+"";
				String videoUrl1="",videoUrl2="";
				String crudeOilDensity="",waterDensity="",naturalGasRelativeDensity="",saturationPressure="",
						reservoirDepth="",reservoirTemperature="",
						tubingPressure="",casingPressure="",wellHeadTemperature="",waterCut="",productionGasOilRatio="",producingfluidLevel="",pumpSettingDepth="",
						barrelLength="",barrelSeries="",rotorDiameter="",QPR="",
						tubingStringInsideDiameter="",casingStringInsideDiameter="",
						rodGrade1="",rodOutsideDiameter1="",rodInsideDiameter1="",rodLength1="",
						rodGrade2="",rodOutsideDiameter2="",rodInsideDiameter2="",rodLength2="",
						rodGrade3="",rodOutsideDiameter3="",rodInsideDiameter3="",rodLength3="",
						rodGrade4="",rodOutsideDiameter4="",rodInsideDiameter4="",rodLength4="",
						netGrossRatio="",netGrossValue="";
				
				if(StringManagerUtils.isNotNull(productionDataStr)){
					type = new TypeToken<PCPProductionData>() {}.getType();
					PCPProductionData productionData=gson.fromJson(productionDataStr, type);
					if(productionData!=null){
						if(productionData.getFluidPVT()!=null){
							crudeOilDensity=productionData.getFluidPVT().getCrudeOilDensity()+"";
							waterDensity=productionData.getFluidPVT().getWaterDensity()+"";
							naturalGasRelativeDensity=productionData.getFluidPVT().getNaturalGasRelativeDensity()+"";
							saturationPressure=productionData.getFluidPVT().getSaturationPressure()+"";
						}
						if(productionData.getReservoir()!=null){
							reservoirDepth=productionData.getReservoir().getDepth()+"";
							reservoirTemperature=productionData.getReservoir().getTemperature()+"";
						}
						if(productionData.getProduction()!=null){
							tubingPressure=productionData.getProduction().getTubingPressure()+"";
							casingPressure=productionData.getProduction().getCasingPressure()+"";
							wellHeadTemperature=productionData.getProduction().getWellHeadTemperature()+"";
							waterCut=productionData.getProduction().getWaterCut()+"";
							productionGasOilRatio=productionData.getProduction().getProductionGasOilRatio()+"";
							producingfluidLevel=productionData.getProduction().getProducingfluidLevel()+"";
							pumpSettingDepth=productionData.getProduction().getPumpSettingDepth()+"";
						}
						if(productionData.getPump()!=null){
							barrelLength=productionData.getPump().getBarrelLength()+"";
							barrelSeries=productionData.getPump().getBarrelSeries()+"";
							rotorDiameter=productionData.getPump().getRotorDiameter()*1000+"";
							QPR=productionData.getPump().getQPR()*1000*1000+"";
						}
						if(productionData.getTubingString()!=null && productionData.getTubingString().getEveryTubing()!=null && productionData.getTubingString().getEveryTubing().size()>0){
							tubingStringInsideDiameter=productionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000+"";
						}
						if(productionData.getCasingString()!=null && productionData.getCasingString().getEveryCasing()!=null && productionData.getCasingString().getEveryCasing().size()>0){
							casingStringInsideDiameter=productionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000+"";
						}
						if(productionData.getRodString()!=null && productionData.getRodString().getEveryRod()!=null && productionData.getRodString().getEveryRod().size()>0){
							rodGrade1=productionData.getRodString().getEveryRod().get(0).getGrade()+"";
							rodOutsideDiameter1=productionData.getRodString().getEveryRod().get(0).getOutsideDiameter()*1000+"";
							rodInsideDiameter1=productionData.getRodString().getEveryRod().get(0).getInsideDiameter()*1000+"";
							rodLength1=productionData.getRodString().getEveryRod().get(0).getLength()+"";
							if(productionData.getRodString().getEveryRod().size()>1){
								rodGrade2=productionData.getRodString().getEveryRod().get(1).getGrade()+"";
								rodOutsideDiameter2=productionData.getRodString().getEveryRod().get(1).getOutsideDiameter()*1000+"";
								rodInsideDiameter2=productionData.getRodString().getEveryRod().get(1).getInsideDiameter()*1000+"";
								rodLength2=productionData.getRodString().getEveryRod().get(1).getLength()+"";
								if(productionData.getRodString().getEveryRod().size()>2){
									rodGrade3=productionData.getRodString().getEveryRod().get(2).getGrade()+"";
									rodOutsideDiameter3=productionData.getRodString().getEveryRod().get(2).getOutsideDiameter()*1000+"";
									rodInsideDiameter3=productionData.getRodString().getEveryRod().get(2).getInsideDiameter()*1000+"";
									rodLength3=productionData.getRodString().getEveryRod().get(2).getLength()+"";
									if(productionData.getRodString().getEveryRod().size()>3){
										rodGrade4=productionData.getRodString().getEveryRod().get(3).getGrade()+"";
										rodOutsideDiameter4=productionData.getRodString().getEveryRod().get(3).getOutsideDiameter()*1000+"";
										rodInsideDiameter4=productionData.getRodString().getEveryRod().get(3).getInsideDiameter()*1000+"";
										rodLength4=productionData.getRodString().getEveryRod().get(3).getLength()+"";
									}
								}
							}
						}
						if(productionData.getManualIntervention()!=null){
							netGrossRatio=productionData.getManualIntervention().getNetGrossRatio()+"";
							netGrossValue=productionData.getManualIntervention().getNetGrossValue()+"";
						}
					}
				}
				
				result_json.append("{\"id\":\""+(i+1)+"\",");
				result_json.append("\"orgName\":\""+obj[1]+"\",");
				result_json.append("\"wellName\":\""+obj[2]+"\",");
				result_json.append("\"applicationScenariosName\":\""+obj[3]+"\",");
				result_json.append("\"instanceName\":\""+obj[4]+"\",");
				result_json.append("\"displayInstanceName\":\""+obj[5]+"\",");
				result_json.append("\"alarmInstanceName\":\""+obj[6]+"\",");
				result_json.append("\"reportInstanceName\":\""+obj[7]+"\",");
				
				result_json.append("\"tcpType\":\""+(obj[8]+"").replaceAll(" ", "").toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client")+"\",");
				result_json.append("\"signInId\":\""+obj[9]+"\",");
				result_json.append("\"slave\":\""+obj[10]+"\",");
				result_json.append("\"peakDelay\":\""+obj[11]+"\",");
				
				result_json.append("\"videoUrl1\":\""+obj[12]+"\",");
				result_json.append("\"videoKeyName1\":\""+obj[13]+"\",");
				result_json.append("\"videoUrl2\":\""+obj[14]+"\",");
				result_json.append("\"videoKeyName2\":\""+obj[15]+"\",");
				
				result_json.append("\"sortNum\":\""+obj[16]+"\",");
				result_json.append("\"statusName\":\""+obj[17]+"\",");
				
				result_json.append("\"crudeOilDensity\":\""+crudeOilDensity+"\",");
				result_json.append("\"waterDensity\":\""+waterDensity+"\",");
				result_json.append("\"naturalGasRelativeDensity\":\""+naturalGasRelativeDensity+"\",");
				result_json.append("\"saturationPressure\":\""+saturationPressure+"\",");
				result_json.append("\"reservoirDepth\":\""+reservoirDepth+"\",");
				result_json.append("\"reservoirTemperature\":\""+reservoirTemperature+"\",");
				result_json.append("\"tubingPressure\":\""+tubingPressure+"\",");
				result_json.append("\"casingPressure\":\""+casingPressure+"\",");
				result_json.append("\"wellHeadTemperature\":\""+wellHeadTemperature+"\",");
				result_json.append("\"waterCut\":\""+waterCut+"\",");
				result_json.append("\"productionGasOilRatio\":\""+productionGasOilRatio+"\",");
				result_json.append("\"producingfluidLevel\":\""+producingfluidLevel+"\",");
				result_json.append("\"pumpSettingDepth\":\""+pumpSettingDepth+"\",");
				result_json.append("\"barrelLength\":\""+barrelLength+"\",");
				result_json.append("\"barrelSeries\":\""+barrelSeries+"\",");
				result_json.append("\"rotorDiameter\":\""+rotorDiameter+"\",");
				result_json.append("\"QPR\":\""+QPR+"\",");
				result_json.append("\"tubingStringInsideDiameter\":\""+tubingStringInsideDiameter+"\",");
				result_json.append("\"casingStringInsideDiameter\":\""+casingStringInsideDiameter+"\",");
				result_json.append("\"rodGrade1\":\""+rodGrade1+"\",");
				result_json.append("\"rodOutsideDiameter1\":\""+rodOutsideDiameter1+"\",");
				result_json.append("\"rodInsideDiameter1\":\""+rodInsideDiameter1+"\",");
				result_json.append("\"rodLength1\":\""+rodLength1+"\",");
				result_json.append("\"rodGrade2\":\""+rodGrade2+"\",");
				result_json.append("\"rodOutsideDiameter2\":\""+rodOutsideDiameter2+"\",");
				result_json.append("\"rodInsideDiameter2\":\""+rodInsideDiameter2+"\",");
				result_json.append("\"rodLength2\":\""+rodLength2+"\",");
				result_json.append("\"rodGrade3\":\""+rodGrade3+"\",");
				result_json.append("\"rodOutsideDiameter3\":\""+rodOutsideDiameter3+"\",");
				result_json.append("\"rodInsideDiameter3\":\""+rodInsideDiameter3+"\",");
				result_json.append("\"rodLength3\":\""+rodLength3+"\",");
				result_json.append("\"rodGrade4\":\""+rodGrade4+"\",");
				result_json.append("\"rodOutsideDiameter4\":\""+rodOutsideDiameter4+"\",");
				result_json.append("\"rodInsideDiameter4\":\""+rodInsideDiameter4+"\",");
				result_json.append("\"rodLength4\":\""+rodLength4+"\",");
				
				result_json.append("\"netGrossRatio\":\""+netGrossRatio+"\",");
				result_json.append("\"netGrossValue\":\""+netGrossValue+"\"}");
				jsonObject = JSONObject.fromObject(result_json.toString().replaceAll("null", ""));
				for (int j = 0; j < realColumns.size(); j++) {
					if(jsonObject.has(realColumns.get(j))){
						record.add(jsonObject.getString(realColumns.get(j)));
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
	
	public String getDefaultInstanceName(int deviceType){
		if(deviceType>=100 && deviceType<200){
			deviceType=0;
		}else if(deviceType>=200 && deviceType<300){
			deviceType=1;
		}
		
		String acqInstanceName=" ",displayInstanceName=" ",alarmInstanceName=" ",reportInstanceName=" ";
		String acqInstanceSql="select v.name from(select t.name from TBL_PROTOCOLINSTANCE t where t.devicetype="+deviceType+" order by t.id) v where rownum=1";
		String displayInstanceSql="select v.name from(select t.name from tbl_protocoldisplayinstance t where t.devicetype="+deviceType+" order by t.id) v where rownum=1";
		String alarmInstanceSql="select v.name from(select t.name from tbl_protocolalarminstance t where t.devicetype="+deviceType+" order by t.id) v where rownum=1";
		String reportInstanceSql="select v.name from(select t.name from tbl_protocolreportinstance t where t.devicetype="+deviceType+" order by t.id) v where rownum=1";
		
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
	public String doAuxiliaryDeviceShow(Map map,Page pager,String deviceType,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		String ddicName="auxiliaryDeviceManager";
		
		String columns=service.showTableHeadersColumns(ddicName);
		String sql = "select t.id,t.name,decode(t.type,1,'管辅件','泵辅件') as type,t.model,t.remark,t.sort from tbl_auxiliarydevice t where 1=1";
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
			result_json.append("\"name\":\""+obj[1]+"\",");
			result_json.append("\"type\":\""+obj[2]+"\",");
			result_json.append("\"model\":\""+obj[3]+"\",");
			result_json.append("\"remark\":\""+obj[4]+"\",");
			result_json.append("\"sort\":\""+obj[5]+"\"},");
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
	public String getBatchAddAuxiliaryDeviceTableInfo(int recordCount) {
		StringBuffer result_json = new StringBuffer();
		String ddicName="auxiliaryDeviceManager";
		String columns=service.showTableHeadersColumns(ddicName);
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
	
	public String getAuxiliaryDevice(String deviceId,String deviceType) {
		StringBuffer result_json = new StringBuffer();
		List<Integer> auxiliaryIdList=new ArrayList<Integer>();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"name\",width:120 ,children:[] },"
				+ "{ \"header\":\"规格型号\",\"dataIndex\":\"model\",width:80 ,children:[] }"
				+ "]";
		String deviceTableName="tbl_device";
		
		String sql = "select t.id,t.name,t.type,t.model,t.remark,t.sort from tbl_auxiliarydevice t where 1=1";
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
			result_json.append("\"model\":\""+obj[3]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getDeviceAdditionalInfo(String deviceId,String deviceType) {
		StringBuffer result_json = new StringBuffer();
		List<Integer> auxiliaryIdList=new ArrayList<Integer>();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"itemName\",width:120 ,children:[] },"
				+ "{ \"header\":\"值\",\"dataIndex\":\"itemValue\",width:120 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"itemUnit\",width:80 ,children:[] }"
				+ "]";
		String deviceTableName="tbl_device";
		String infoTableName="tbl_deviceaddinfo";
		String sql = "select t2.id,t2.itemname,t2.itemvalue,t2.itemunit "
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
			result_json.append("\"itemUnit\":\""+obj[3]+"\"},");
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
	
	public boolean judgeAuxiliaryDeviceExistOrNot(String name,String type,String model) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(name)&&StringManagerUtils.isNotNull(type)&&StringManagerUtils.isNotNull(model)) {
			String sql = "select t.id from tbl_auxiliarydevice t where t.name='"+name+"' and t.type="+type+" and t.model='"+model+"'";
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
	
	public String saveAuxiliaryDeviceHandsontableData(AuxiliaryDeviceHandsontableChangedData auxiliaryDeviceHandsontableChangedData) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer collisionbuff = new StringBuffer();
		List<AuxiliaryDeviceHandsontableChangedData.Updatelist> list=getBaseDao().saveAuxiliaryDeviceHandsontableData(auxiliaryDeviceHandsontableChangedData);
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
	
	public String batchAddAuxiliaryDevice(AuxiliaryDeviceHandsontableChangedData auxiliaryDeviceHandsontableChangedData,String isCheckout) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer overlayBuff = new StringBuffer();
		int overlayCount=0;
		String ddicName="auxiliaryDeviceManager";
		String columns=service.showTableHeadersColumns(ddicName);
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
}
