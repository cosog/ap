package com.cosog.service.back;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AcquisitionGroup;
import com.cosog.model.AcquisitionUnitGroup;
import com.cosog.model.PcpDeviceInformation;
import com.cosog.model.RpcDeviceInformation;
import com.cosog.model.SmsDeviceInformation;
import com.cosog.model.User;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.drive.KafkaConfig;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.gridmodel.AuxiliaryDeviceHandsontableChangedData;
import com.cosog.model.gridmodel.WellGridPanelData;
import com.cosog.model.gridmodel.WellHandsontableChangedData;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.LicenseMap;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.LicenseMap.License;

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
		if(deviceType>=400&&deviceType<500){
			tableName="tbl_pcpdevice";
		}else if(deviceType>=800){
			tableName="tbl_smsdevice";
		}
		if(deviceType==1){
			tableName="tbl_pcpdevice";
		}else if(deviceType==2){
			tableName="tbl_smsdevice";
		}
		String sql = " select  t.wellName as wellName,t.wellName as dm from  "+tableName+" t  ,tbl_org  g where 1=1 and  t.orgId=g.org_id  and g.org_id in ("
				+ orgId + ")";
		if(StringManagerUtils.isNotNull(deviceTypeStr) && deviceType>=200 && StringManagerUtils.isNotNull(deviceTypeStr) && deviceType<800){
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
		if(deviceType>=400&&deviceType<500){
			tableName="tbl_pcpdevice";
		}else if(deviceType>=800){
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
	
	public void changeDeviceOrg(String selectedDeviceId,String selectedOrgId,String deviceTypeStr) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		String tableName="tbl_rpcdevice";
		if(deviceType>=400&&deviceType<500){
			tableName="tbl_pcpdevice";
		}else if(deviceType>=800){
			tableName="tbl_smsdevice";
		}
		String sql = "update "+tableName+" t set t.orgid="+selectedOrgId+" where t.id in ("+selectedDeviceId+")";
		this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public String getAcqInstanceCombList(String deviceTypeStr){
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		StringBuffer result_json = new StringBuffer();
		int protocolType=0;
		if((deviceType>=400&&deviceType<500)||deviceType==1){
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
	
	public String getAlarmInstanceCombList(String deviceTypeStr){
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		StringBuffer result_json = new StringBuffer();
		int protocolType=0;
		if((deviceType>=400&&deviceType<500)||deviceType==1){
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
	
	public String saveRPCDeviceData(WellHandsontableChangedData wellHandsontableChangedData,String orgId,int deviceType,User user) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer collisionbuff = new StringBuffer();
		List<WellHandsontableChangedData.Updatelist> list=getBaseDao().saveRPCDeviceData(wellHandsontableChangedData,orgId,deviceType,user);
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
	
	public String batchAddRPCDevice(WellHandsontableChangedData wellHandsontableChangedData,String orgId,int deviceType,String isCheckout,User user) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer  collisionBuff = new StringBuffer();
		StringBuffer overlayBuff = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		int collisionCount=0;
		int overlayCount=0;
		String ddicName="wellInfo";
		String columns=service.showTableHeadersColumns(ddicName);
		List<WellHandsontableChangedData.Updatelist> list=getBaseDao().batchAddRPCDevice(wellHandsontableChangedData,orgId,deviceType,isCheckout,user);
		String instanceSql="select t.name from tbl_protocolinstance t where t.devicetype=0 order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t where t.devicetype=0 order by t.sort";
		instanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		
		if(instanceList.size()>0){
			instanceDropdownData.append("\"\",");
			for(int i=0;i<instanceList.size();i++){
				instanceDropdownData.append("'"+instanceList.get(i)+"',");
			}
			if(instanceDropdownData.toString().endsWith(",")){
				instanceDropdownData.deleteCharAt(instanceDropdownData.length() - 1);
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
		
		instanceDropdownData.append("]");
		alarmInstanceDropdownData.append("]");
		collisionBuff.append("[");
		overlayBuff.append("[");
		if(list!=null){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getSaveSign()==-22){//冲突
					collisionCount+=1;
					collisionBuff.append("{\"id\":\""+list.get(i).getId()+"\",");
					collisionBuff.append("\"wellName\":\""+list.get(i).getWellName()+"\",");
					collisionBuff.append("\"instanceName\":\""+list.get(i).getInstanceName()+"\",");
					collisionBuff.append("\"alarmInstanceName\":\""+list.get(i).getAlarmInstanceName()+"\",");
					collisionBuff.append("\"signInId\":\""+list.get(i).getSignInId()+"\",");
					collisionBuff.append("\"slave\":\""+list.get(i).getSlave()+"\",");
					collisionBuff.append("\"statusName\":\""+list.get(i).getStatusName()+"\",");
					collisionBuff.append("\"sortNum\":\""+list.get(i).getSortNum()+"\",");
					collisionBuff.append("\"dataInfo\":\""+list.get(i).getSaveStr()+"\"},");
				}else if(list.get(i).getSaveSign()==-33){//覆盖信息
					overlayCount+=1;
					overlayBuff.append("{\"id\":\""+list.get(i).getId()+"\",");
					overlayBuff.append("\"wellName\":\""+list.get(i).getWellName()+"\",");
					overlayBuff.append("\"instanceName\":\""+list.get(i).getInstanceName()+"\",");
					overlayBuff.append("\"alarmInstanceName\":\""+list.get(i).getAlarmInstanceName()+"\",");
					overlayBuff.append("\"signInId\":\""+list.get(i).getSignInId()+"\",");
					overlayBuff.append("\"slave\":\""+list.get(i).getSlave()+"\",");
					overlayBuff.append("\"statusName\":\""+list.get(i).getStatusName()+"\",");
					overlayBuff.append("\"sortNum\":\""+list.get(i).getSortNum()+"\",");
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
				+ "\"alarmInstanceDropdownData\":"+alarmInstanceDropdownData.toString()+","
				+ "\"columns\":"+columns+",\"collisionList\":"+collisionBuff+",\"overlayList\":"+overlayBuff+"}");
		
		return result_json.toString().replaceAll("null", "");
	}
	
	public String savePCPDeviceData(WellHandsontableChangedData wellHandsontableChangedData,String orgId,int deviceType,User user) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer collisionbuff = new StringBuffer();
		List<WellHandsontableChangedData.Updatelist> list=getBaseDao().savePCPDeviceData(wellHandsontableChangedData,orgId,deviceType,user);
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
	
	public String batchAddPCPDevice(WellHandsontableChangedData wellHandsontableChangedData,String orgId,int deviceType,String isCheckout,User user) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer  collisionBuff = new StringBuffer();
		StringBuffer overlayBuff = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		int collisionCount=0;
		int overlayCount=0;
		String ddicName="wellInfo";
		String columns=service.showTableHeadersColumns(ddicName);
		List<WellHandsontableChangedData.Updatelist> list=getBaseDao().batchAddPCPDevice(wellHandsontableChangedData,orgId,deviceType,isCheckout,user);
		String instanceSql="select t.name from tbl_protocolinstance t where t.devicetype=0 order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t where t.devicetype=0 order by t.sort";
		instanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		
		if(instanceList.size()>0){
			instanceDropdownData.append("\"\",");
			for(int i=0;i<instanceList.size();i++){
				instanceDropdownData.append("'"+instanceList.get(i)+"',");
			}
			if(instanceDropdownData.toString().endsWith(",")){
				instanceDropdownData.deleteCharAt(instanceDropdownData.length() - 1);
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
		instanceDropdownData.append("]");
		alarmInstanceDropdownData.append("]");
		collisionBuff.append("[");
		overlayBuff.append("[");
		if(list!=null){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getSaveSign()==-22){//冲突
					collisionCount+=1;
					collisionBuff.append("{\"id\":\""+list.get(i).getId()+"\",");
					collisionBuff.append("\"wellName\":\""+list.get(i).getWellName()+"\",");
					collisionBuff.append("\"instanceName\":\""+list.get(i).getInstanceName()+"\",");
					collisionBuff.append("\"alarmInstanceName\":\""+list.get(i).getAlarmInstanceName()+"\",");
					collisionBuff.append("\"signInId\":\""+list.get(i).getSignInId()+"\",");
					collisionBuff.append("\"slave\":\""+list.get(i).getSlave()+"\",");
					collisionBuff.append("\"statusName\":\""+list.get(i).getStatusName()+"\",");
					collisionBuff.append("\"sortNum\":\""+list.get(i).getSortNum()+"\",");
					collisionBuff.append("\"dataInfo\":\""+list.get(i).getSaveStr()+"\"},");
				}else if(list.get(i).getSaveSign()==-33){//覆盖信息
					overlayCount+=1;
					overlayBuff.append("{\"id\":\""+list.get(i).getId()+"\",");
					overlayBuff.append("\"wellName\":\""+list.get(i).getWellName()+"\",");
					overlayBuff.append("\"instanceName\":\""+list.get(i).getInstanceName()+"\",");
					overlayBuff.append("\"alarmInstanceName\":\""+list.get(i).getAlarmInstanceName()+"\",");
					overlayBuff.append("\"signInId\":\""+list.get(i).getSignInId()+"\",");
					overlayBuff.append("\"slave\":\""+list.get(i).getSlave()+"\",");
					overlayBuff.append("\"statusName\":\""+list.get(i).getStatusName()+"\",");
					overlayBuff.append("\"sortNum\":\""+list.get(i).getSortNum()+"\",");
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
				+ "\"alarmInstanceDropdownData\":"+alarmInstanceDropdownData.toString()+","
				+ "\"columns\":"+columns+",\"collisionList\":"+collisionBuff+",\"overlayList\":"+overlayBuff+"}");
		
		return result_json.toString().replaceAll("null", "");
	}
	
	public void saveSMSDeviceData(WellHandsontableChangedData wellHandsontableChangedData,String orgId,int deviceType,User user) throws Exception {
		getBaseDao().saveSMSDeviceData(wellHandsontableChangedData,orgId,deviceType,user);
	}
	
	public void doRPCDeviceEdit(RpcDeviceInformation rpcDeviceInformation) throws Exception {
		getBaseDao().updateObject(rpcDeviceInformation);
	}
	
	public void doRPCDeviceAdd(RpcDeviceInformation rpcDeviceInformation) throws Exception {
		getBaseDao().addObject(rpcDeviceInformation);
	}
	
	public void doPCPDeviceEdit(PcpDeviceInformation pcpDeviceInformation) throws Exception {
		getBaseDao().updateObject(pcpDeviceInformation);
	}
	
	public void doPCPDeviceAdd(PcpDeviceInformation pcpDeviceInformation) throws Exception {
		getBaseDao().addObject(pcpDeviceInformation);
	}
	
	public void doSMSDeviceAdd(SmsDeviceInformation smsDeviceInformation) throws Exception {
		getBaseDao().addObject(smsDeviceInformation);
	}
	
	public void deleteMasterAndAuxiliary(final int masterid) throws Exception {
		final String hql = "DELETE MasterAndAuxiliaryDevice u where u.masterid ="+masterid+"";
		getBaseDao().bulkObjectDelete(hql);
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
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		String ddicName="wellInfo";
		String tableName="viw_rpcdevice";
		int protocolType=0;
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		String wellInformationName = (String) map.get("wellInformationName");
		int deviceType=StringManagerUtils.stringToInteger((String) map.get("deviceType"));
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		if (StringManagerUtils.isNotNull(wellInformationName)) {
			WellInformation_Str = " and t.wellname like '%" + wellInformationName+ "%'";
		}
		
		String columns=service.showTableHeadersColumns(ddicName);
		String sql = "select id,orgName,wellName,instanceName,alarmInstanceName,signInId,slave,"
				+ " videoUrl,sortNum,status,statusName"
				+ " from "+tableName+" t where 1=1"
				+ WellInformation_Str;
		sql+= " and t.orgid in ("+orgId+" )";
		sql+= " and t.devicetype="+deviceType;
		sql+= " order by t.sortnum,t.wellname ";
		String instanceSql="select t.name from tbl_protocolinstance t where t.devicetype="+protocolType+" order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t where t.devicetype="+protocolType+" order by t.sort";
		
		instanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		
		if(instanceList.size()>0){
			instanceDropdownData.append("\"\",");
			for(int i=0;i<instanceList.size();i++){
				instanceDropdownData.append("'"+instanceList.get(i)+"',");
			}
			if(instanceDropdownData.toString().endsWith(",")){
				instanceDropdownData.deleteCharAt(instanceDropdownData.length() - 1);
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
		instanceDropdownData.append("]");
		alarmInstanceDropdownData.append("]");
		
		String json = "";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+","
				+ "\"instanceDropdownData\":"+instanceDropdownData.toString()+","
				+ "\"alarmInstanceDropdownData\":"+alarmInstanceDropdownData.toString()+","
				+ "\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"orgName\":\""+obj[1]+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"instanceName\":\""+obj[3]+"\",");
			result_json.append("\"alarmInstanceName\":\""+obj[4]+"\",");
			result_json.append("\"signInId\":\""+obj[5]+"\",");
			result_json.append("\"slave\":\""+obj[6]+"\",");
			result_json.append("\"videoUrl\":\""+obj[7]+"\",");
			result_json.append("\"status\":\""+obj[9]+"\",");
			result_json.append("\"statusName\":\""+obj[10]+"\",");
			result_json.append("\"sortNum\":\""+obj[8]+"\"},");
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
	public String getRPCDeviceInfoExportData(Map map,Page pager,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		String tableName="viw_rpcdevice";
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		String wellInformationName = (String) map.get("wellInformationName");
		int deviceType=StringManagerUtils.stringToInteger((String) map.get("deviceType"));
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		if (StringManagerUtils.isNotNull(wellInformationName)) {
			WellInformation_Str = " and t.wellname like '%" + wellInformationName+ "%'";
		}
		String sql = "select id,orgName,wellName,instanceName,alarmInstanceName,signInId,slave,"
				+ " videoUrl,sortNum,status,statusName"
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
			result_json.append("\"instanceName\":\""+obj[3]+"\",");
			result_json.append("\"alarmInstanceName\":\""+obj[4]+"\",");
			result_json.append("\"signInId\":\""+obj[5]+"\",");
			result_json.append("\"slave\":\""+obj[6]+"\",");
			result_json.append("\"videoUrl\":\""+obj[7]+"\",");
			result_json.append("\"status\":\""+obj[9]+"\",");
			result_json.append("\"statusName\":\""+obj[10]+"\",");
			result_json.append("\"sortNum\":\""+obj[8]+"\"},");
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
	public String getPipeDeviceInfoList(Map map,Page pager,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		String ddicName="wellInfo";
		String tableName="viw_pcpdevice";
		int protocolType=1;
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		String wellInformationName = (String) map.get("wellInformationName");
		int deviceType=StringManagerUtils.stringToInteger((String) map.get("deviceType"));
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		if (StringManagerUtils.isNotNull(wellInformationName)) {
			WellInformation_Str = " and t.wellname like '%" + wellInformationName+ "%'";
		}
		
		String columns=service.showTableHeadersColumns(ddicName);
		String sql = "select id,orgName,wellName,instanceName,alarmInstanceName,signInId,slave,"
				+ " videoUrl,sortNum,status,statusName"
				+ " from "+tableName+" t where 1=1"
				+ WellInformation_Str;
		sql+= " and t.orgid in ("+orgId+" )  ";		
		
		sql+= " and t.devicetype="+deviceType;
		sql+= " order by t.sortnum,t.wellname ";
		String instanceSql="select t.name from tbl_protocolinstance t where t.devicetype="+protocolType+" order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t where t.devicetype="+protocolType+" order by t.sort";
		
		instanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		
		if(instanceList.size()>0){
			instanceDropdownData.append("\"\",");
			for(int i=0;i<instanceList.size();i++){
				instanceDropdownData.append("'"+instanceList.get(i)+"',");
			}
			if(instanceDropdownData.toString().endsWith(",")){
				instanceDropdownData.deleteCharAt(instanceDropdownData.length() - 1);
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
	
		instanceDropdownData.append("]");
		alarmInstanceDropdownData.append("]");
		
		String json = "";
		
		List<?> list = this.findCallSql(sql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+","
				+ "\"instanceDropdownData\":"+instanceDropdownData.toString()+","
				+ "\"alarmInstanceDropdownData\":"+alarmInstanceDropdownData.toString()+","
				+ "\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"orgName\":\""+obj[1]+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"instanceName\":\""+obj[3]+"\",");
			result_json.append("\"alarmInstanceName\":\""+obj[4]+"\",");
			result_json.append("\"signInId\":\""+obj[5]+"\",");
			result_json.append("\"slave\":\""+obj[6]+"\",");
			result_json.append("\"videoUrl\":\""+obj[7]+"\",");
			result_json.append("\"status\":\""+obj[9]+"\",");
			result_json.append("\"statusName\":\""+obj[10]+"\",");
			result_json.append("\"sortNum\":\""+obj[8]+"\"},");
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
		String sql = "select id,orgName,wellName,instanceName,alarmInstanceName,signInId,slave,"
				+ " videoUrl,sortNum,status,statusName"
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
			result_json.append("\"instanceName\":\""+obj[3]+"\",");
			result_json.append("\"alarmInstanceName\":\""+obj[4]+"\",");
			result_json.append("\"signInId\":\""+obj[5]+"\",");
			result_json.append("\"slave\":\""+obj[6]+"\",");
			result_json.append("\"videoUrl\":\""+obj[7]+"\",");
			result_json.append("\"status\":\""+obj[9]+"\",");
			result_json.append("\"statusName\":\""+obj[10]+"\",");
			result_json.append("\"sortNum\":\""+obj[8]+"\"},");
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
		String ddicName="SMSDeviceManager";
		String tableName="viw_smsdevice";
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
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
		String deviceTableName="tbl_rpcdevice";
		if(StringManagerUtils.stringToInteger(deviceType)>=400 && StringManagerUtils.stringToInteger(deviceType)<500){
			deviceTableName="tbl_pcpdevice";
		}
		
		String sql = "select t.id,t.name,decode(t.type,1,'管辅件','泵辅件') as type,t.model,t.remark,t.sort from tbl_auxiliarydevice t where 1=1";
		String auxiliarySql="select t2.auxiliaryid from "+deviceTableName+" t,tbl_auxiliary2master t2 "
				+ " where t.id=t2.masterid and t.id="+deviceId;
		if(StringManagerUtils.stringToInteger(deviceType)>=400 && StringManagerUtils.stringToInteger(deviceType)<500){
			sql+= " and t.type=1";
		}else{
			sql+= " and t.type=0";
		}
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
		String deviceTableName="tbl_rpcdevice";
		String infoTableName="tbl_rpcdeviceaddinfo";
		if(StringManagerUtils.stringToInteger(deviceType)>=400 && StringManagerUtils.stringToInteger(deviceType)<500){
			deviceTableName="tbl_pcpdevice";
			infoTableName="tbl_pcpdeviceaddinfo";
		}
		String sql = "select t2.id,t2.itemname,t2.itemvalue,t2.itemunit "
				+ " from "+deviceTableName+" t,"+infoTableName+" t2 "
				+ " where t.id=t2.wellid and t.id="+deviceId
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
		String sql = "select t.id,t.wellname,t.orgid,t.orgName,t.devicetype,t.devicetypename,t.signinid,t.slave,videoUrl,"
				+ "t.instancecode,t.instancename,t.alarminstancecode,t.alarminstancename,t.sortnum "
				+ "from viw_pumpdevice  t where t.id="+recordId;
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
			result_json.append("\"signInId\":\""+obj[6]+"\",");
			result_json.append("\"slave\":\""+obj[7]+"\",");
			result_json.append("\"videoUrl\":\""+obj[8]+"\",");
			result_json.append("\"instanceCode\":\""+obj[9]+"\",");
			result_json.append("\"instanceName\":\""+obj[10]+"\",");
			result_json.append("\"alarmInstanceCode\":\""+obj[11]+"\",");
			result_json.append("\"alarminstanceName\":\""+obj[12]+"\",");
			result_json.append("\"sortNum\":\""+obj[13]+"\"");
			result_json.append("}");
		}
		
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	@SuppressWarnings("rawtypes")
	public String getBatchAddDeviceTableInfo(String deviceTypeStr,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer instanceDropdownData = new StringBuffer();
		StringBuffer alarmInstanceDropdownData = new StringBuffer();
		String ddicName="wellInfo";
		int protocolType=0;
		int deviceType=StringManagerUtils.stringToInteger(deviceTypeStr);
		if(deviceType>=400&&deviceType<500){
			ddicName="wellInfo";
			protocolType=1;
		}
		
		String instanceSql="select t.name from tbl_protocolinstance t where t.devicetype="+protocolType+" order by t.sort";
		String alarmInstanceSql="select t.name from tbl_protocolalarminstance t where t.devicetype="+protocolType+" order by t.sort";
		String columns=service.showTableHeadersColumns(ddicName);
		instanceDropdownData.append("[");
		alarmInstanceDropdownData.append("[");

		List<?> instanceList = this.findCallSql(instanceSql);
		List<?> alarmInstanceList = this.findCallSql(alarmInstanceSql);
		
		if(instanceList.size()>0){
			instanceDropdownData.append("\"\",");
			for(int i=0;i<instanceList.size();i++){
				instanceDropdownData.append("'"+instanceList.get(i)+"',");
			}
			if(instanceDropdownData.toString().endsWith(",")){
				instanceDropdownData.deleteCharAt(instanceDropdownData.length() - 1);
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
		
		instanceDropdownData.append("]");
		alarmInstanceDropdownData.append("]");
		
		String json = "";
		result_json.append("{\"success\":true,\"totalCount\":"+recordCount+","
				+ "\"instanceDropdownData\":"+instanceDropdownData.toString()+","
				+ "\"alarmInstanceDropdownData\":"+alarmInstanceDropdownData.toString()+","
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
		if(deviceType>=400&&deviceType<500){
			tableName="tbl_pcpdevice";
		}else if(deviceType>=800){
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
		if(deviceType>=400&&deviceType<500){
			tableName="tbl_pcpdevice";
		}else if(deviceType>=800){
			tableName="tbl_smsdevice";
		}
		if (StringManagerUtils.isNotNull(signinId)&&StringManagerUtils.isNotNull(slaveStr)) {
			String sql = "select t.id from "+tableName+" t where t.signinid='"+signinId+"' and to_number(t.slave)="+slave;
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
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
}
