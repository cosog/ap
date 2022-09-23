package com.cosog.service.back;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AcquisitionGroup;
import com.cosog.model.AcquisitionUnitGroup;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.PumpingModelInformation;
import com.cosog.model.MasterAndAuxiliaryDevice;
import com.cosog.model.PCPDeviceAddInfo;
import com.cosog.model.PcpDeviceInformation;
import com.cosog.model.RPCDeviceAddInfo;
import com.cosog.model.RpcDeviceInformation;
import com.cosog.model.SmsDeviceInformation;
import com.cosog.model.User;
import com.cosog.model.calculate.AlarmInstanceOwnItem;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.PCPProductionData;
import com.cosog.model.calculate.PumpingPRTFData;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.RPCDeviceInfo;
import com.cosog.model.calculate.RPCProductionData;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.drive.KafkaConfig;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.drive.WaterCutRawData;
import com.cosog.model.gridmodel.PumpingModelHandsontableChangedData;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
		String sql = " select  t.wellName as wellName,t.wellName as dm from  "+tableName+" t  ,tbl_org  g where 1=1 and  t.orgId=g.org_id  and g.org_id in ("
				+ orgId + ")";
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
				jedis = RedisUtil.jedisPool.getResource();
				if(deviceType>=100&&deviceType<200){
					if(!jedis.exists("RPCDeviceInfo".getBytes())){
						MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
					}
					List<byte[]> deviceInfoByteList =jedis.hvals("RPCDeviceInfo".getBytes());
					for(int i=0;i<deviceInfoByteList.size();i++){
						Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
						if (obj instanceof RPCDeviceInfo) {
							RPCDeviceInfo deviceInfo=(RPCDeviceInfo)obj;
							if(StringManagerUtils.existOrNot(selectedDeviceId.split(","), deviceInfo.getId()+"", false)){
								deviceInfo.setOrgId(StringManagerUtils.stringToInteger(selectedOrgId));
								deviceInfo.setOrgName(selectedOrgName);
								jedis.hset("RPCDeviceInfo".getBytes(), (deviceInfo.getId()+"").getBytes(), SerializeObjectUnils.serialize(deviceInfo));
							}
						}
					}
				}else if(deviceType>=200&&deviceType<300){
					if(!jedis.exists("PCPDeviceInfo".getBytes())){
						MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
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
				jedis=null;
			}
			if(jedis!=null){
				jedis.close();
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
		
		String sql="select t.code,t.name from tbl_protocolinstance t where t.devicetype="+protocolType+" order by t.sort";
		
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
		
		String sql="select t.code,t.name from tbl_protocoldisplayinstance t where t.devicetype="+protocolType+" order by t.sort";
		
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
		
		String sql="select t.code,t.name from tbl_protocolalarminstance t where t.devicetype="+protocolType+" order by t.sort";
		
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
	
	public String batchAddRPCDevice(WellInformationManagerService<?> wellInformationManagerService,WellHandsontableChangedData wellHandsontableChangedData,String orgId,int deviceType,String isCheckout,User user) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer  collisionBuff = new StringBuffer();
		StringBuffer overlayBuff = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer displayInstanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		StringBuffer applicationScenariosDropdownData = new StringBuffer();
		int collisionCount=0;
		int overlayCount=0;
		String ddicName="deviceInfo_RPCDeviceBatchAdd";
		String columns=service.showTableHeadersColumns(ddicName);
		List<WellHandsontableChangedData.Updatelist> list=getBaseDao().batchAddRPCDevice(wellInformationManagerService,wellHandsontableChangedData,orgId,deviceType,isCheckout,user);
		String instanceSql="select t.name from tbl_protocolinstance t where t.devicetype=0 order by t.sort";
		String displayInstanceSql="select t.name from tbl_protocoldisplayinstance t where t.devicetype=0 order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t where t.devicetype=0 order by t.sort";
		String applicationScenariosSql="select c.itemname from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' order by c.itemvalue";
		instanceDropdownData.append("[");
		displayInstanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");
		applicationScenariosDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
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
		alarmInstanceDropdownData.append("]");
		applicationScenariosDropdownData.append("]");
		collisionBuff.append("[");
		overlayBuff.append("[");
		if(list!=null){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getSaveSign()==-22){//冲突
					collisionCount+=1;
					collisionBuff.append("{\"id\":\""+list.get(i).getId()+"\",");
					collisionBuff.append("\"wellName\":\""+list.get(i).getWellName()+"\",");
					collisionBuff.append("\"applicationScenariosName\":\""+list.get(i).getApplicationScenariosName()+"\",");
					collisionBuff.append("\"instanceName\":\""+list.get(i).getInstanceName()+"\",");
					collisionBuff.append("\"displayInstanceName\":\""+list.get(i).getDisplayInstanceName()+"\",");
					collisionBuff.append("\"alarmInstanceName\":\""+list.get(i).getAlarmInstanceName()+"\",");
					collisionBuff.append("\"tcpType\":\""+list.get(i).getTcpType()+"\",");
					collisionBuff.append("\"signInId\":\""+list.get(i).getSignInId()+"\",");
					collisionBuff.append("\"slave\":\""+list.get(i).getSlave()+"\",");
					collisionBuff.append("\"peakDelay\":\""+list.get(i).getPeakDelay()+"\",");
					collisionBuff.append("\"videoUrl\":\""+list.get(i).getVideoUrl()+"\",");
					collisionBuff.append("\"videoAccessToken\":\""+list.get(i).getVideoAccessToken()+"\",");
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
		        	collisionBuff.append("\"netGrossRatio\":\""+list.get(i).getNetGrossRatio()+"\",");
		        	collisionBuff.append("\"netGrossValue\":\""+list.get(i).getNetGrossValue()+"\",");
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
					overlayBuff.append("\"wellName\":\""+list.get(i).getWellName()+"\",");
					overlayBuff.append("\"applicationScenariosName\":\""+list.get(i).getApplicationScenariosName()+"\",");
					overlayBuff.append("\"instanceName\":\""+list.get(i).getInstanceName()+"\",");
					overlayBuff.append("\"displayInstanceName\":\""+list.get(i).getDisplayInstanceName()+"\",");
					overlayBuff.append("\"alarmInstanceName\":\""+list.get(i).getAlarmInstanceName()+"\",");
					overlayBuff.append("\"tcpType\":\""+list.get(i).getTcpType()+"\",");
					overlayBuff.append("\"signInId\":\""+list.get(i).getSignInId()+"\",");
					overlayBuff.append("\"slave\":\""+list.get(i).getSlave()+"\",");
					overlayBuff.append("\"peakDelay\":\""+list.get(i).getPeakDelay()+"\",");
					overlayBuff.append("\"videoUrl\":\""+list.get(i).getVideoUrl()+"\",");
					overlayBuff.append("\"videoAccessToken\":\""+list.get(i).getVideoAccessToken()+"\",");
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
		        	overlayBuff.append("\"netGrossRatio\":\""+list.get(i).getNetGrossRatio()+"\",");
		        	overlayBuff.append("\"netGrossValue\":\""+list.get(i).getNetGrossValue()+"\",");
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
		result_json.append("{\"success\":true,\"collisionCount\":"+collisionCount+",\"overlayCount\":"+overlayCount+","
				+ "\"instanceDropdownData\":"+instanceDropdownData.toString()+","
				+ "\"displayInstanceDropdownData\":"+displayInstanceDropdownData.toString()+","
				+ "\"alarmInstanceDropdownData\":"+alarmInstanceDropdownData.toString()+","
				+ "\"applicationScenariosDropdownData\":"+applicationScenariosDropdownData.toString()+","
				+ "\"columns\":"+columns+",\"collisionList\":"+collisionBuff+",\"overlayList\":"+overlayBuff+"}");
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
	
	public String batchAddPCPDevice(WellInformationManagerService<?> wellInformationManagerService,WellHandsontableChangedData wellHandsontableChangedData,String orgId,int deviceType,String isCheckout,User user) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer  collisionBuff = new StringBuffer();
		StringBuffer overlayBuff = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer displayInstanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		StringBuffer applicationScenariosDropdownData = new StringBuffer();
		int collisionCount=0;
		int overlayCount=0;
		String ddicName="deviceInfo_PCPDeviceBatchAdd";
		String columns=service.showTableHeadersColumns(ddicName);
		List<WellHandsontableChangedData.Updatelist> list=getBaseDao().batchAddPCPDevice(wellInformationManagerService,wellHandsontableChangedData,orgId,deviceType,isCheckout,user);
		String instanceSql="select t.name from tbl_protocolinstance t where t.devicetype=1 order by t.sort";
		String displayInstanceSql="select t.name from tbl_protocoldisplayinstance t where t.devicetype=1 order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t where t.devicetype=1 order by t.sort";
		String applicationScenariosSql="select c.itemname from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' order by c.itemvalue";
		instanceDropdownData.append("[");
		displayInstanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");
		applicationScenariosDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
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
		alarmInstanceDropdownData.append("]");
		applicationScenariosDropdownData.append("]");
		collisionBuff.append("[");
		overlayBuff.append("[");
		if(list!=null){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getSaveSign()==-22){//冲突
					collisionCount+=1;
					collisionBuff.append("{\"id\":\""+list.get(i).getId()+"\",");
					collisionBuff.append("\"wellName\":\""+list.get(i).getWellName()+"\",");
					collisionBuff.append("\"applicationScenariosName\":\""+list.get(i).getApplicationScenariosName()+"\",");
					collisionBuff.append("\"instanceName\":\""+list.get(i).getInstanceName()+"\",");
					collisionBuff.append("\"displayInstanceName\":\""+list.get(i).getDisplayInstanceName()+"\",");
					collisionBuff.append("\"alarmInstanceName\":\""+list.get(i).getAlarmInstanceName()+"\",");
					collisionBuff.append("\"tcpType\":\""+list.get(i).getTcpType()+"\",");
					collisionBuff.append("\"signInId\":\""+list.get(i).getSignInId()+"\",");
					collisionBuff.append("\"slave\":\""+list.get(i).getSlave()+"\",");
					collisionBuff.append("\"peakDelay\":\""+list.get(i).getPeakDelay()+"\",");
					collisionBuff.append("\"videoUrl\":\""+list.get(i).getVideoUrl()+"\",");
					collisionBuff.append("\"videoAccessToken\":\""+list.get(i).getVideoAccessToken()+"\",");
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
					overlayBuff.append("\"wellName\":\""+list.get(i).getWellName()+"\",");
					overlayBuff.append("\"applicationScenariosName\":\""+list.get(i).getApplicationScenariosName()+"\",");
					overlayBuff.append("\"instanceName\":\""+list.get(i).getInstanceName()+"\",");
					overlayBuff.append("\"displayInstanceName\":\""+list.get(i).getDisplayInstanceName()+"\",");
					overlayBuff.append("\"alarmInstanceName\":\""+list.get(i).getAlarmInstanceName()+"\",");
					overlayBuff.append("\"tcpType\":\""+list.get(i).getTcpType()+"\",");
					overlayBuff.append("\"signInId\":\""+list.get(i).getSignInId()+"\",");
					overlayBuff.append("\"slave\":\""+list.get(i).getSlave()+"\",");
					overlayBuff.append("\"peakDelay\":\""+list.get(i).getPeakDelay()+"\",");
					overlayBuff.append("\"videoUrl\":\""+list.get(i).getVideoUrl()+"\",");
					overlayBuff.append("\"videoAccessToken\":\""+list.get(i).getVideoAccessToken()+"\",");
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
		result_json.append("{\"success\":true,\"collisionCount\":"+collisionCount+",\"overlayCount\":"+overlayCount+","
				+ "\"instanceDropdownData\":"+instanceDropdownData.toString()+","
				+ "\"displayInstanceDropdownData\":"+displayInstanceDropdownData.toString()+","
				+ "\"alarmInstanceDropdownData\":"+alarmInstanceDropdownData.toString()+","
				+ "\"applicationScenariosDropdownData\":"+applicationScenariosDropdownData.toString()+","
				+ "\"columns\":"+columns+",\"collisionList\":"+collisionBuff+",\"overlayList\":"+overlayBuff+"}");
		
		return result_json.toString().replaceAll("null", "");
	}
	
	public void saveSMSDeviceData(WellHandsontableChangedData wellHandsontableChangedData,String orgId,int deviceType,User user) throws Exception {
		getBaseDao().saveSMSDeviceData(wellHandsontableChangedData,orgId,deviceType,user);
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
	
	public void deleteMasterAndAuxiliary(final int masterid) throws Exception {
		final String hql = "DELETE MasterAndAuxiliaryDevice u where u.masterid ="+masterid+"";
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void deleteDeviceAdditionalInfo(final int deviceId,int deviceType) throws Exception {
		String model="RPCDeviceAddInfo";
		if(deviceType>=200&&deviceType<300){
			model="PCPDeviceAddInfo";
		}
		final String hql = "DELETE "+model+" u where u.wellId ="+deviceId+"";
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void grantMasterAuxiliaryDevice(MasterAndAuxiliaryDevice r) throws Exception {
		getBaseDao().saveOrUpdateObject(r);
	}
	
	public void saveProductionData(int deviceType,int deviceId,String deviceProductionData) throws Exception {
		String tableName="tbl_rpcdevice";
		if(deviceType>=200&&deviceType<300){
			tableName="tbl_pcpdevice";
		}
		String time=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String sql = "update "+tableName+" t set t.productiondata='"+deviceProductionData+"',t.productiondataupdatetime=to_date('"+time+"','yyyy-mm-dd hh24:mi:ss') where t.id="+deviceId;
		this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public void saveRPCPumpingModel(int deviceId,String pumpingModelId) throws Exception {
		String sql = "update tbl_rpcdevice t set t.pumpingmodelid="+pumpingModelId+" where t.id="+deviceId;
		if(!StringManagerUtils.isNotNull(pumpingModelId)){
			sql = "update tbl_rpcdevice t set t.pumpingmodelid=null where t.id="+deviceId;
		}
		this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public void saveRPCPumpingInfo(int deviceId,String strokeStr,String balanceInfo) throws Exception {
		if(!StringManagerUtils.isNumber(strokeStr)){
			strokeStr="null";
		}
		String sql = "update tbl_rpcdevice t set t.stroke="+strokeStr+",t.balanceinfo='"+balanceInfo+"' where t.id="+deviceId;
		this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public void saveDeviceAdditionalInfo(RPCDeviceAddInfo r) throws Exception {
		getBaseDao().saveOrUpdateObject(r);
	}
	
	public void saveDeviceAdditionalInfo(PCPDeviceAddInfo r) throws Exception {
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
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		StringBuffer applicationScenariosDropdownData = new StringBuffer();
		String ddicName="deviceInfo_RPCDeviceManager";
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
		String sql = "select id,orgName,wellName,applicationScenariosName,instanceName,displayInstanceName,alarmInstanceName,"
				+ " tcptype,signInId,slave,t.peakdelay,"
				+ " videoUrl,videoAccessToken,"
				+ " sortNum,status,statusName,allpath"
				+ " from "+tableName+" t where 1=1"
				+ WellInformation_Str;
		sql+= " and t.orgid in ("+orgId+" )";
		sql+= " and t.devicetype="+deviceType;
		sql+= " order by t.sortnum,t.wellname ";
		String instanceSql="select t.name from tbl_protocolinstance t where t.devicetype="+protocolType+" order by t.sort";
		String displayInstanceSql="select t.name from tbl_protocoldisplayinstance t where t.devicetype="+protocolType+" order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t where t.devicetype="+protocolType+" order by t.sort";
		String applicationScenariosSql="select c.itemname from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' order by c.itemvalue";
		
		instanceDropdownData.append("[");
		displayInstanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");
		applicationScenariosDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
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
			if(instanceDropdownData.toString().endsWith(",")){
				displayInstanceDropdownData.deleteCharAt(displayInstanceDropdownData.length() - 1);
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
		alarmInstanceDropdownData.append("]");
		applicationScenariosDropdownData.append("]");
		
		String json = "";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+","
				+ "\"instanceDropdownData\":"+instanceDropdownData.toString()+","
				+ "\"displayInstanceDropdownData\":"+displayInstanceDropdownData.toString()+","
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
			result_json.append("\"tcpType\":\""+obj[7]+"\",");
			result_json.append("\"signInId\":\""+obj[8]+"\",");
			result_json.append("\"slave\":\""+obj[9]+"\",");
			result_json.append("\"peakDelay\":\""+obj[10]+"\",");
			result_json.append("\"videoUrl\":\""+obj[11]+"\",");
			result_json.append("\"videoAccessToken\":\""+obj[12]+"\",");
			result_json.append("\"status\":\""+obj[14]+"\",");
			result_json.append("\"statusName\":\""+obj[15]+"\",");
			result_json.append("\"allPath\":\""+obj[16]+"\",");
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
				+ " videoUrl,videoAccessToken,"
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
			result_json.append("\"tcpType\":\""+obj[7]+"\",");
			result_json.append("\"signInId\":\""+obj[8]+"\",");
			result_json.append("\"slave\":\""+obj[9]+"\",");
			result_json.append("\"peakDelay\":\""+obj[10]+"\",");
			result_json.append("\"videoUrl\":\""+obj[11]+"\",");
			result_json.append("\"videoAccessToken\":\""+obj[12]+"\",");
			result_json.append("\"status\":\""+obj[14]+"\",");
			result_json.append("\"statusName\":\""+obj[15]+"\",");
			result_json.append("\"allPath\":\""+obj[16]+"\",");
			result_json.append("\"sortNum\":\""+obj[13]+"\"},");
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
	
	@SuppressWarnings("rawtypes")
	public String getPCPDeviceInfoList(Map map,Page pager,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer displayInstanceDropdownData = new StringBuffer();
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
		String sql = "select id,orgName,wellName,applicationScenariosName,instanceName,displayInstanceName,alarmInstanceName,"
				+ " tcptype,signInId,slave,t.peakdelay,"
				+ " videoUrl,videoAccessToken,"
				+ " sortNum,status,statusName,allpath"
				+ " from "+tableName+" t where 1=1"
				+ WellInformation_Str;
		sql+= " and t.orgid in ("+orgId+" )  ";		
		
		sql+= " and t.devicetype="+deviceType;
		sql+= " order by t.sortnum,t.wellname ";
		String instanceSql="select t.name from tbl_protocolinstance t where t.devicetype="+protocolType+" order by t.sort";
		String displayInstanceSql="select t.name from tbl_protocoldisplayinstance t where t.devicetype="+protocolType+" order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t where t.devicetype="+protocolType+" order by t.sort";
		String applicationScenariosSql="select c.itemname from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' order by c.itemvalue";
		
		instanceDropdownData.append("[");
		displayInstanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");
		applicationScenariosDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
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
		alarmInstanceDropdownData.append("]");
		applicationScenariosDropdownData.append("]");
		
		String json = "";
		
		List<?> list = this.findCallSql(sql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+","
				+ "\"instanceDropdownData\":"+instanceDropdownData.toString()+","
				+ "\"displayInstanceDropdownData\":"+displayInstanceDropdownData.toString()+","
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
			result_json.append("\"tcpType\":\""+obj[7]+"\",");
			result_json.append("\"signInId\":\""+obj[8]+"\",");
			result_json.append("\"slave\":\""+obj[9]+"\",");
			result_json.append("\"peakDelay\":\""+obj[10]+"\",");
			result_json.append("\"videoUrl\":\""+obj[11]+"\",");
			result_json.append("\"videoAccessToken\":\""+obj[12]+"\",");
			result_json.append("\"status\":\""+obj[14]+"\",");
			result_json.append("\"statusName\":\""+obj[15]+"\",");
			result_json.append("\"allPath\":\""+obj[16]+"\",");
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
				+ " videoUrl,videoAccessToken,"
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
			result_json.append("\"tcpType\":\""+obj[7]+"\",");
			result_json.append("\"signInId\":\""+obj[8]+"\",");
			result_json.append("\"slave\":\""+obj[9]+"\",");
			result_json.append("\"peakDelay\":\""+obj[10]+"\",");
			result_json.append("\"videoUrl\":\""+obj[11]+"\",");
			result_json.append("\"videoAccessToken\":\""+obj[12]+"\",");
			result_json.append("\"status\":\""+obj[14]+"\",");
			result_json.append("\"statusName\":\""+obj[15]+"\",");
			result_json.append("\"allPath\":\""+obj[16]+"\",");
			result_json.append("\"sortNum\":\""+obj[13]+"\"},");
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
	public String doPumpingModelShow(Map map,Page pager,String deviceType,int recordCount) throws SQLException, IOException {
		StringBuffer result_json = new StringBuffer();
		String ddicName="pumpingDevice_PumpingModelManager";
		String columns=service.showTableHeadersColumns(ddicName);
		String sql = "select t.id,t.manufacturer,t.model,t.stroke,decode(t.crankrotationdirection,'Anticlockwise','逆时针','Clockwise','顺时针','') as crankrotationdirection,"
				+ " t.offsetangleofcrank,t.crankgravityradius,t.singlecrankweight,t.singlecrankpinweight,"
				+ " t.structuralunbalance,t.balanceweight"
//				+ " t.prtf "
				+ " from tbl_pumpingmodel t"
				+ " order by t.id,t.manufacturer,t.model";
		
		String json = "";
		
		List<?> list = this.findCallSql(sql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
//			String prtf="[]";
//			if(obj[11]!=null){
//				SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[11]);
//				CLOB realClob = (CLOB) proxy.getWrappedClob(); 
//				prtf=StringManagerUtils.CLOBtoString(realClob);
//			}
//			if(!StringManagerUtils.isNotNull(prtf)){
//				prtf="[]";
//			}
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
		json=result_json.toString().replaceAll("null", "");
		return json;
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
		
		String json = "";
		
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
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getRPCPumpingModelList(String deviceId,String deviceType) {
		StringBuffer result_json = new StringBuffer();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"name\",width:120 ,children:[] },"
				+ "{ \"header\":\"规格型号\",\"dataIndex\":\"model\",width:80 ,children:[] }"
				+ "]";
		String sql = "select t.id,t.manufacturer,t.model,t.stroke,t.balanceweight from tbl_pumpingmodel t order by t.id,t.manufacturer,t.model";
		String devicePumpingModelSql="select t.pumpingmodelid from tbl_rpcdevice t where t.id="+deviceId;
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
				+ "{ \"header\":\"名称\",\"dataIndex\":\"itemName\",width:120 ,children:[] },"
				+ "{ \"header\":\"值\",\"dataIndex\":\"itemValue\",width:80 ,children:[] },"
				+ "{ \"header\":\"\",\"dataIndex\":\"itemValue2\",width:80 ,children:[] }"
				+ "]";
		String sql = "select t.stroke,t.balanceinfo from tbl_rpcdevice t where t.id="+deviceId;
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
		type = new TypeToken<RPCProductionData.Balance>() {}.getType();
		RPCProductionData.Balance balance=gson.fromJson(balanceInfo, type);
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
		result_json.append("{\"id\":1,\"itemName\":\"冲程(m)\",\"itemValue\":\""+stroke+"\",\"itemValue2\":\"\"},");
		result_json.append("{\"id\":2,\"itemName\":\"平衡块1位置/重量\",\"itemValue\":\""+position1+"\",\"itemValue2\":\""+weight1+"\"},");
		result_json.append("{\"id\":3,\"itemName\":\"平衡块2位置/重量\",\"itemValue\":\""+position2+"\",\"itemValue2\":\""+weight2+"\"},");
		result_json.append("{\"id\":4,\"itemName\":\"平衡块3位置/重量\",\"itemValue\":\""+position3+"\",\"itemValue2\":\""+weight3+"\"},");
		result_json.append("{\"id\":5,\"itemName\":\"平衡块4位置/重量\",\"itemValue\":\""+position4+"\",\"itemValue2\":\""+weight4+"\"},");
		result_json.append("{\"id\":6,\"itemName\":\"平衡块5位置/重量\",\"itemValue\":\""+position5+"\",\"itemValue2\":\""+weight5+"\"},");
		result_json.append("{\"id\":7,\"itemName\":\"平衡块6位置/重量\",\"itemValue\":\""+position6+"\",\"itemValue2\":\""+weight6+"\"},");
		result_json.append("{\"id\":8,\"itemName\":\"平衡块7位置/重量\",\"itemValue\":\""+position7+"\",\"itemValue2\":\""+weight7+"\"},");
		result_json.append("{\"id\":9,\"itemName\":\"平衡块8位置/重量\",\"itemValue\":\""+position8+"\",\"itemValue2\":\""+weight8+"\"}");
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getDeviceProductionDataInfo(String deviceId,String deviceType) {
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"itemName\",width:120 ,children:[] },"
				+ "{ \"header\":\"值\",\"dataIndex\":\"itemValue\",width:120 ,children:[] }"
				+ "]";
		String deviceTableName="tbl_rpcdevice";
		if(StringManagerUtils.stringToInteger(deviceType)>=200 && StringManagerUtils.stringToInteger(deviceType)<300){
			deviceTableName="tbl_pcpdevice";
		}
		String sql = "select t.productiondata,to_char(t.productiondataupdatetime,'yyyy-mm-dd hh24:mi:ss') "
				+ " from "+deviceTableName+" t "
				+ " where t.id="+deviceId;
		
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");
		if(list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			String productionData=obj[0]+"";
			String updateTime=obj[1]+"";
			if(StringManagerUtils.stringToInteger(deviceType)>=100 && StringManagerUtils.stringToInteger(deviceType)<200){
				type = new TypeToken<RPCProductionData>() {}.getType();
				RPCProductionData rpcProductionData=gson.fromJson(productionData, type);
				if(rpcProductionData!=null){
					result_json.append("{\"id\":1,\"itemName\":\"原油密度(g/cm^3)\",\"itemValue\":\""+(rpcProductionData.getFluidPVT()!=null?rpcProductionData.getFluidPVT().getCrudeOilDensity():"")+"\"},");
					result_json.append("{\"id\":2,\"itemName\":\"水密度(g/cm^3)\",\"itemValue\":\""+(rpcProductionData.getFluidPVT()!=null?rpcProductionData.getFluidPVT().getWaterDensity():"")+"\"},");
					result_json.append("{\"id\":3,\"itemName\":\"天然气相对密度\",\"itemValue\":\""+(rpcProductionData.getFluidPVT()!=null?rpcProductionData.getFluidPVT().getNaturalGasRelativeDensity():"")+"\"},");
					result_json.append("{\"id\":4,\"itemName\":\"饱和压力(MPa)\",\"itemValue\":\""+(rpcProductionData.getFluidPVT()!=null?rpcProductionData.getFluidPVT().getSaturationPressure():"")+"\"},");
					
					result_json.append("{\"id\":5,\"itemName\":\"油层中部深度(m)\",\"itemValue\":\""+(rpcProductionData.getReservoir()!=null?rpcProductionData.getReservoir().getDepth():"")+"\"},");
					result_json.append("{\"id\":6,\"itemName\":\"油层中部温度(℃)\",\"itemValue\":\""+(rpcProductionData.getReservoir()!=null?rpcProductionData.getReservoir().getTemperature():"")+"\"},");
					
					result_json.append("{\"id\":7,\"itemName\":\"油压(MPa)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getProduction().getTubingPressure():"")+"\"},");
					result_json.append("{\"id\":8,\"itemName\":\"套压(MPa)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getProduction().getCasingPressure():"")+"\"},");
					result_json.append("{\"id\":9,\"itemName\":\"井口温度(℃)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getProduction().getWellHeadTemperature():"")+"\"},");
					result_json.append("{\"id\":10,\"itemName\":\"含水率(%)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getProduction().getWaterCut():"")+"\"},");
					result_json.append("{\"id\":11,\"itemName\":\"生产气油比(m^3/t)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getProduction().getProductionGasOilRatio():"")+"\"},");
					result_json.append("{\"id\":12,\"itemName\":\"动液面(m)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getProduction().getProducingfluidLevel():"")+"\"},");
					result_json.append("{\"id\":13,\"itemName\":\"泵挂(m)\",\"itemValue\":\""+(rpcProductionData.getProduction()!=null?rpcProductionData.getProduction().getPumpSettingDepth():"")+"\"},");
					
					String pumpType="";
					String barrelType="";
					if(rpcProductionData.getPump()!=null&&rpcProductionData.getPump().getPumpType()!=null){
						if("R".equalsIgnoreCase(rpcProductionData.getPump().getPumpType())){
							pumpType="杆式泵";
						}else if("T".equalsIgnoreCase(rpcProductionData.getPump().getPumpType())){
							pumpType="管式泵";
						}
					}
					if(rpcProductionData.getPump()!=null&&rpcProductionData.getPump().getBarrelType()!=null){
						if("L".equalsIgnoreCase(rpcProductionData.getPump().getBarrelType())){
							barrelType="组合泵";
						}else if("H".equalsIgnoreCase(rpcProductionData.getPump().getBarrelType())){
							barrelType="整筒泵";
						}
					}
					result_json.append("{\"id\":14,\"itemName\":\"泵类型\",\"itemValue\":\""+pumpType+"\"},");
					result_json.append("{\"id\":15,\"itemName\":\"泵筒类型\",\"itemValue\":\""+barrelType+"\"},");
					result_json.append("{\"id\":16,\"itemName\":\"泵级别\",\"itemValue\":\""+(rpcProductionData.getPump()!=null?rpcProductionData.getPump().getPumpGrade():"")+"\"},");
					result_json.append("{\"id\":17,\"itemName\":\"泵径(mm)\",\"itemValue\":\""+(rpcProductionData.getPump()!=null?(rpcProductionData.getPump().getPumpBoreDiameter()*1000):"")+"\"},");
					result_json.append("{\"id\":18,\"itemName\":\"柱塞长(m)\",\"itemValue\":\""+(rpcProductionData.getPump()!=null?rpcProductionData.getPump().getPlungerLength():"")+"\"},");
					
					result_json.append("{\"id\":19,\"itemName\":\"油管内径(mm)\",\"itemValue\":\""+(rpcProductionData.getTubingString()!=null&&rpcProductionData.getTubingString().getEveryTubing()!=null&&rpcProductionData.getTubingString().getEveryTubing().size()>0?(rpcProductionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000):"")+"\"},");
					result_json.append("{\"id\":20,\"itemName\":\"套管内径(mm)\",\"itemValue\":\""+(rpcProductionData.getCasingString()!=null&&rpcProductionData.getCasingString().getEveryCasing()!=null&&rpcProductionData.getCasingString().getEveryCasing().size()>0?(rpcProductionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000):"")+"\"},");
					
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
					result_json.append("{\"id\":21,\"itemName\":\"一级杆级别\",\"itemValue\":\""+rodGrade1+"\"},");
					result_json.append("{\"id\":22,\"itemName\":\"一级杆外径(mm)\",\"itemValue\":\""+rodOutsideDiameter1+"\"},");
					result_json.append("{\"id\":23,\"itemName\":\"一级杆内径(mm)\",\"itemValue\":\""+rodInsideDiameter1+"\"},");
					result_json.append("{\"id\":24,\"itemName\":\"一级杆长度(m)\",\"itemValue\":\""+rodLength1+"\"},");
					
					result_json.append("{\"id\":25,\"itemName\":\"二级杆级别\",\"itemValue\":\""+rodGrade2+"\"},");
					result_json.append("{\"id\":26,\"itemName\":\"二级杆外径(mm)\",\"itemValue\":\""+rodOutsideDiameter2+"\"},");
					result_json.append("{\"id\":27,\"itemName\":\"二级杆内径(mm)\",\"itemValue\":\""+rodInsideDiameter2+"\"},");
					result_json.append("{\"id\":28,\"itemName\":\"二级杆长度(m)\",\"itemValue\":\""+rodLength2+"\"},");
					
					result_json.append("{\"id\":29,\"itemName\":\"三级杆级别\",\"itemValue\":\""+rodGrade3+"\"},");
					result_json.append("{\"id\":30,\"itemName\":\"三级杆外径(mm)\",\"itemValue\":\""+rodOutsideDiameter3+"\"},");
					result_json.append("{\"id\":31,\"itemName\":\"三级杆内径(mm)\",\"itemValue\":\""+rodInsideDiameter3+"\"},");
					result_json.append("{\"id\":32,\"itemName\":\"三级杆长度(m)\",\"itemValue\":\""+rodLength3+"\"},");
					
					result_json.append("{\"id\":33,\"itemName\":\"四级杆级别\",\"itemValue\":\""+rodGrade4+"\"},");
					result_json.append("{\"id\":34,\"itemName\":\"四级杆外径(mm)\",\"itemValue\":\""+rodOutsideDiameter4+"\"},");
					result_json.append("{\"id\":35,\"itemName\":\"四级杆内径(mm)\",\"itemValue\":\""+rodInsideDiameter4+"\"},");
					result_json.append("{\"id\":36,\"itemName\":\"四级杆长度(m)\",\"itemValue\":\""+rodLength4+"\"},");
					
					result_json.append("{\"id\":37,\"itemName\":\"净毛比(小数)\",\"itemValue\":\""+(rpcProductionData.getManualIntervention()!=null?rpcProductionData.getManualIntervention().getNetGrossRatio():"")+"\"},");
					result_json.append("{\"id\":38,\"itemName\":\"净毛值(m^3/d)\",\"itemValue\":\""+(rpcProductionData.getManualIntervention()!=null?rpcProductionData.getManualIntervention().getNetGrossValue():"")+"\"},");
				}else{
					result_json.append("{\"id\":1,\"itemName\":\"原油密度(g/cm^3)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":2,\"itemName\":\"水密度(g/cm^3)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":3,\"itemName\":\"天然气相对密度\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":4,\"itemName\":\"饱和压力(MPa)\",\"itemValue\":\"\"},");
					
					result_json.append("{\"id\":5,\"itemName\":\"油层中部深度(m)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":6,\"itemName\":\"油层中部温度(℃)\",\"itemValue\":\"\"},");
					
					result_json.append("{\"id\":7,\"itemName\":\"油压(MPa)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":8,\"itemName\":\"套压(MPa)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":9,\"itemName\":\"井口温度(℃)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":10,\"itemName\":\"含水率(%)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":11,\"itemName\":\"生产气油比(m^3/t)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":12,\"itemName\":\"动液面(m)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":13,\"itemName\":\"泵挂(m)\",\"itemValue\":\"\"},");
					
					result_json.append("{\"id\":14,\"itemName\":\"泵类型\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":15,\"itemName\":\"泵筒类型\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":16,\"itemName\":\"泵级别\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":17,\"itemName\":\"泵径(mm)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":18,\"itemName\":\"柱塞长(m)\",\"itemValue\":\"\"},");
					
					result_json.append("{\"id\":19,\"itemName\":\"油管内径(mm)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":20,\"itemName\":\"套管内径(mm)\",\"itemValue\":\"\"},");
					
					
					result_json.append("{\"id\":21,\"itemName\":\"一级杆级别\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":22,\"itemName\":\"一级杆外径(mm)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":23,\"itemName\":\"一级杆内径(mm)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":24,\"itemName\":\"一级杆长度(m)\",\"itemValue\":\"\"},");
					
					result_json.append("{\"id\":25,\"itemName\":\"二级杆级别\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":26,\"itemName\":\"二级杆外径(mm)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":27,\"itemName\":\"二级杆内径(mm)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":28,\"itemName\":\"二级杆长度(m)\",\"itemValue\":\"\"},");
					
					result_json.append("{\"id\":29,\"itemName\":\"三级杆级别\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":30,\"itemName\":\"三级杆外径(mm)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":31,\"itemName\":\"三级杆内径(mm)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":32,\"itemName\":\"三级杆长度(m)\",\"itemValue\":\"\"},");
					
					result_json.append("{\"id\":33,\"itemName\":\"四级杆级别\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":34,\"itemName\":\"四级杆外径(mm)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":35,\"itemName\":\"四级杆内径(mm)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":36,\"itemName\":\"四级杆长度(m)\",\"itemValue\":\"\"},");
					
					result_json.append("{\"id\":37,\"itemName\":\"净毛比(小数)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":38,\"itemName\":\"净毛值(m^3/d)\",\"itemValue\":\"\"},");
				}
				result_json.append("{\"id\":39,\"itemName\":\"更新时间\",\"itemValue\":\""+updateTime+"\"}");
			}else if(StringManagerUtils.stringToInteger(deviceType)>=200 && StringManagerUtils.stringToInteger(deviceType)<300){
				type = new TypeToken<PCPProductionData>() {}.getType();
				PCPProductionData pcpProductionData=gson.fromJson(productionData, type);
				if(pcpProductionData!=null){
					result_json.append("{\"id\":1,\"itemName\":\"原油密度(g/cm^3)\",\"itemValue\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getCrudeOilDensity():"")+"\"},");
					result_json.append("{\"id\":2,\"itemName\":\"水密度(g/cm^3)\",\"itemValue\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getWaterDensity():"")+"\"},");
					result_json.append("{\"id\":3,\"itemName\":\"天然气相对密度\",\"itemValue\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getNaturalGasRelativeDensity():"")+"\"},");
					result_json.append("{\"id\":4,\"itemName\":\"饱和压力(MPa)\",\"itemValue\":\""+(pcpProductionData.getFluidPVT()!=null?pcpProductionData.getFluidPVT().getSaturationPressure():"")+"\"},");
					
					result_json.append("{\"id\":5,\"itemName\":\"油层中部深度(m)\",\"itemValue\":\""+(pcpProductionData.getReservoir()!=null?pcpProductionData.getReservoir().getDepth():"")+"\"},");
					result_json.append("{\"id\":6,\"itemName\":\"油层中部温度(℃)\",\"itemValue\":\""+(pcpProductionData.getReservoir()!=null?pcpProductionData.getReservoir().getTemperature():"")+"\"},");
					
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
					result_json.append("{\"id\":37,\"itemName\":\"净毛值(m^3/d)\",\"itemValue\":\""+(pcpProductionData.getManualIntervention()!=null?pcpProductionData.getManualIntervention().getNetGrossValue():"")+"\"},");
				}else{
					result_json.append("{\"id\":1,\"itemName\":\"原油密度(g/cm^3)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":2,\"itemName\":\"水密度(g/cm^3)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":3,\"itemName\":\"天然气相对密度\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":4,\"itemName\":\"饱和压力(MPa)\",\"itemValue\":\"\"},");
					
					result_json.append("{\"id\":5,\"itemName\":\"油层中部深度(m)\",\"itemValue\":\"\"},");
					result_json.append("{\"id\":6,\"itemName\":\"油层中部温度(℃)\",\"itemValue\":\"\"},");
					
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
					result_json.append("{\"id\":37,\"itemName\":\"净毛值(m^3/d)\",\"itemValue\":\"\"},");
				}
				result_json.append("{\"id\":38,\"itemName\":\"更新时间\",\"itemValue\":\""+updateTime+"\"}");
			}
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
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
	
	public String exportWellInformationData(Map map,Page pager,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		String ddicName="deviceInfo_RPCDeviceManager";
		String wellInformationName = (String) map.get("wellInformationName");
		int deviceType=StringManagerUtils.stringToInteger((String) map.get("deviceType"));
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		if (StringManagerUtils.isNotNull(wellInformationName)) {
			WellInformation_Str = " and t.wellname like '%" + wellInformationName+ "%'";
		}
		if(deviceType==0){
			ddicName="deviceInfo_RPCDeviceManager";
		}else if(deviceType==1){
			ddicName="deviceInfo_PCPDeviceManager";
		}else if(deviceType==2){
			ddicName="SMSDevice_SMSDeviceManager";
		}
		String sql = "select id,orgName,wellName,applicationScenariosName,instanceName,alarmInstanceName,signInId,slave,"
				+ " factorynumber,model,productiondate,deliverydate,commissioningdate,controlcabinetmodel,t.pcplength,"
				+ " videoUrl,sortNum"
				+ " from viw_wellinformation t where 1=1"
				+ WellInformation_Str;
//		if(deviceType!=2){
//			sql+= " and t.orgid in ("+orgId+" )  ";
//		}
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
			result_json.append("\"alarmInstanceName\":\""+obj[5]+"\",");
			result_json.append("\"signInId\":\""+obj[6]+"\",");
			result_json.append("\"slave\":\""+obj[7]+"\",");
			
			result_json.append("\"factoryNumber\":\""+obj[8]+"\",");
			result_json.append("\"model\":\""+obj[9]+"\",");
			result_json.append("\"productionDate\":\""+obj[10]+"\",");
			result_json.append("\"deliveryDate\":\""+obj[11]+"\",");
			result_json.append("\"commissioningDate\":\""+obj[12]+"\",");
			result_json.append("\"controlcabinetDodel\":\""+obj[13]+"\",");
			
			result_json.append("\"pcpLength\":\""+obj[14]+"\",");
			
			result_json.append("\"videoUrl\":\""+obj[15]+"\",");
			result_json.append("\"sortNum\":\""+obj[16]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}

	
	@SuppressWarnings("rawtypes")
	public String getRPCDeviceInformationData(String recordId) {
		StringBuffer result_json = new StringBuffer();
		String sql = "select t.id,t.wellname,t.orgid,t.orgName,t.devicetype,t.devicetypename,t.applicationscenarios,t.applicationScenariosName,t.signinid,t.slave,videoUrl,"
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
			result_json.append("\"videoUrl\":\""+obj[10]+"\",");
			result_json.append("\"instanceCode\":\""+obj[11]+"\",");
			result_json.append("\"instanceName\":\""+obj[12]+"\",");
			result_json.append("\"alarmInstanceCode\":\""+obj[13]+"\",");
			result_json.append("\"alarminstanceName\":\""+obj[14]+"\",");
			result_json.append("\"sortNum\":\""+obj[15]+"\"");
			result_json.append("}");
		}
		
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	@SuppressWarnings("rawtypes")
	public String getBatchAddDeviceTableInfo(String deviceTypeStr,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer displayInstanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		StringBuffer applicationScenariosDropdownData = new StringBuffer();
		String ddicName="deviceInfo_RPCDeviceBatchAdd";
		int protocolType=0;
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		if(deviceType>=200&&deviceType<300){
			ddicName="deviceInfo_PCPDeviceBatchAdd";
			protocolType=1;
		}
		
		String instanceSql="select t.name from tbl_protocolinstance t where t.devicetype="+protocolType+" order by t.sort";
		String displayInstanceSql="select t.name from tbl_protocoldisplayinstance t where t.devicetype="+protocolType+" order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t where t.devicetype="+protocolType+" order by t.sort";
		String applicationScenariosSql="select c.itemname from tbl_code c where c.itemcode='APPLICATIONSCENARIOS' order by c.itemvalue";
		String columns=service.showTableHeadersColumns(ddicName);
		instanceDropdownData.append("[");
		displayInstanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");
		applicationScenariosDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> displayInstanceList = this.findCallSql(displayInstanceSql);
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
		alarmInstanceDropdownData.append("]");
		applicationScenariosDropdownData.append("]");
		
		String json = "";
		result_json.append("{\"success\":true,\"totalCount\":"+recordCount+","
				+ "\"instanceDropdownData\":"+instanceDropdownData.toString()+","
				+ "\"displayInstanceDropdownData\":"+displayInstanceDropdownData.toString()+","
				+ "\"alarmInstanceDropdownData\":"+alarmInstanceDropdownData.toString()+","
				+ "\"applicationScenariosDropdownData\":"+applicationScenariosDropdownData.toString()+","
				+ "\"columns\":"+columns+",\"totalRoot\":[");
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
		String tableName="tbl_rpcdevice";
		if(deviceType>=200&&deviceType<300){
			tableName="tbl_pcpdevice";
		}else if(deviceType>=300){
			tableName="tbl_smsdevice";
		}
		if (StringManagerUtils.isNotNull(deviceName)&&StringManagerUtils.isNotNull(orgId)) {
			String sql = "select t.id from "+tableName+" t where t.wellname='"+deviceName+"' and t.orgid="+orgId;
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgeDeviceExistOrNotBySigninIdAndSlave(String deviceTypeStr,String signinId,String slaveStr) {
		boolean flag = false;
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		int slave=StringManagerUtils.stringToInteger(slaveStr);
		String tableName="tbl_rpcdevice";
		if(deviceType>=200&&deviceType<300){
			tableName="tbl_pcpdevice";
		}else if(deviceType>=300){
			tableName="tbl_smsdevice";
		}
		if (StringManagerUtils.isNotNull(signinId)&&StringManagerUtils.isNotNull(slaveStr)) {
			String rpcSql = "select t.id from tbl_rpcdevice t where t.signinid='"+signinId+"' and to_number(t.slave)="+slave;
			List<?> rpcList = this.findCallSql(rpcSql);
			if (rpcList.size() > 0) {
				flag = true;
			}else{
				String pcpSql = "select t.id from tbl_pcpdevice t where t.signinid='"+signinId+"' and to_number(t.slave)="+slave;
				List<?> pcpList = this.findCallSql(pcpSql);
				if (pcpList.size() > 0) {
					flag = true;
				}
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
				+ "{ \"header\":\"上行通信状态\",\"dataIndex\":\"upCommStatusName\",width:90,children:[] },"
				+ "{ \"header\":\"下行通信状态\",\"dataIndex\":\"downCommStatusName\",width:90,children:[] },"
				+ "{ \"header\":\"注册包ID\",\"dataIndex\":\"signinId\",flex:1,children:[] },"
				+ "{ \"header\":\"设备从地址\",\"dataIndex\":\"slave\",flex:1,children:[] }"
				+ "]";
		
		String sql="select t.id,t.wellname,"
				+ "t2.upcommstatus,decode(t2.upcommstatus,1,'在线','离线') as upCommStatusName,"
				+ "t2.downcommstatus,decode(t2.downcommstatus,1,'在线','离线') as downCommStatusName,"
				+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'),t.signinid,t.slave "
				+ " from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on t2.wellid=t.id";
		sql+= " where  t.orgid in ("+orgId+") ";
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
			result_json.append("\"upCommStatus\":"+obj[2]+",");
			result_json.append("\"upCommStatusName\":\""+obj[3]+"\",");
			result_json.append("\"downCommStatus\":"+obj[4]+",");
			result_json.append("\"downCommStatusName\":\""+obj[5]+"\",");
			result_json.append("\"acqTime\":\""+obj[6]+"\",");
			result_json.append("\"signinId\":\""+obj[7]+"\",");
			result_json.append("\"slave\":\""+obj[8]+"\"},");
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
			String url=Config.getInstance().configFile.getAd_rpc().getReadTopicReq();
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
			String url=Config.getInstance().configFile.getAd_rpc().getReadTopicReq();
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
			
			type = new TypeToken<WaterCutRawData>() {}.getType();
			WaterCutRawData waterCutRawData=gson.fromJson(responseData, type);
			
			if(waterCutRawData!=null && waterCutRawData.getResultStatus()==1 && waterCutRawData.getMessage()!=null && waterCutRawData.getMessage().getWaterCut()!=null){
				result=gson.toJson(waterCutRawData);
			}
		}
		return result;
	}
	
	public String getWaterCutRawDataExport(String signinId,String slave) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		result_json.append("[");
		if(StringManagerUtils.isNotNull(signinId) && StringManagerUtils.isNotNull(slave)){
			String url=Config.getInstance().configFile.getAd_rpc().getReadTopicReq();
			String topic="rawwatercut";
			StringBuffer requestBuff = new StringBuffer();
			requestBuff.append("{\"ID\":\""+signinId+"\",");
			requestBuff.append("\"Topic\":\""+topic+"\"}");
			String responseData=StringManagerUtils.sendPostMethod(url, requestBuff.toString(),"utf-8",0,0);
			
//			String path="";
//			StringManagerUtils stringManagerUtils=new StringManagerUtils();
//			path=stringManagerUtils.getFilePath("test7.json","example/");
//			responseData=stringManagerUtils.readFile(path,"utf-8");
			
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
}
