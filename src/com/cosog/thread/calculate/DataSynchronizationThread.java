package com.cosog.thread.calculate;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cosog.dao.BaseDao;
import com.cosog.model.AcquisitionUnit;
import com.cosog.model.DeviceInformation;
import com.cosog.model.User;
import com.cosog.service.acqUnit.AcquisitionUnitManagerService;
import com.cosog.service.back.WellInformationManagerService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;

public class DataSynchronizationThread implements Runnable{
	public List<String> initWellList;
	public List<String> updateList;
	public List<String> addList;
	public List<String> deleteList;
	public List<String> deleteNameList;
	public int condition;
	public String method;
	public String param1;
	public int sign;
	public User user;
	public int deviceType;
	
	public AcquisitionUnitManagerService<AcquisitionUnit> acquisitionUnitManagerService;
	
	public DeviceInformation deviceInformation;
	public WellInformationManagerService<?> wellInformationManagerService;
	public WellInformationManagerService<DeviceInformation> deviceManagerService;
	public void run(){
		try {
			if(sign==001){//创建协议
				EquipmentDriverServerTask.initProtocolConfig(param1,method);
			}else if(sign==002){//删除协议
				MemoryDataManagerTask.loadAcqInstanceOwnItemByProtocolName(param1,method);
				MemoryDataManagerTask.loadAlarmInstanceOwnItemByProtocolName(param1,method);
				MemoryDataManagerTask.loadDisplayInstanceOwnItemByProtocolName(param1,method);
				
				EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolName(param1,method);
				EquipmentDriverServerTask.initInstanceConfigByProtocolName(param1,method);
				EquipmentDriverServerTask.initProtocolConfig(param1,method);
				this.acquisitionUnitManagerService.doDeleteProtocolAssociation(param1);
			}else if(sign==003){//修改协议
				MemoryDataManagerTask.loadAcqInstanceOwnItemByProtocolName(param1,method);
				MemoryDataManagerTask.loadAlarmInstanceOwnItemByProtocolName(param1,method);
				MemoryDataManagerTask.loadDisplayInstanceOwnItemByProtocolName(param1,method);
				
				EquipmentDriverServerTask.initProtocolConfig(param1,method);
				EquipmentDriverServerTask.initInstanceConfigByProtocolName(param1,method);
			}else if(sign==011){//删除采集单元
				EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByAcqUnitId(param1,method);
				EquipmentDriverServerTask.initInstanceConfigByAcqUnitId(param1,method);
				MemoryDataManagerTask.loadAcqInstanceOwnItemByUnitId(param1,method);
				MemoryDataManagerTask.loadDisplayInstanceOwnItemByUnitId(param1,method);
				acquisitionUnitManagerService.doAcquisitionUnitBulkDelete(param1);
			}
			else if(sign==021){//采控组 项授权
				EquipmentDriverServerTask.initInstanceConfigByAcqGroupId(param1,method);
				EquipmentDriverServerTask.initPumpDriverAcquisitionInfoConfigByAcqGroupId(param1,method);
				acquisitionUnitManagerService.doAcquisitionGroupOwnItemChange(param1);
				MemoryDataManagerTask.loadAcqInstanceOwnItemByGroupId(param1,method);
				MemoryDataManagerTask.loadDisplayInstanceOwnItemByAcqGroupId(param1,method);
			}
			else if(sign==022){//删除采控组
				EquipmentDriverServerTask.initInstanceConfigByAcqUnitId(param1,method);
				MemoryDataManagerTask.loadAcqInstanceOwnItemByUnitId(param1,method);
				MemoryDataManagerTask.loadDisplayInstanceOwnItemByAcqUnitId(param1,method);
			}
			else if(sign==031){//删除报警单元
				MemoryDataManagerTask.loadAlarmInstanceOwnItemByUnitId(param1,method);
				acquisitionUnitManagerService.doModbusProtocolAlarmUnitDelete(param1);
			}
			else if(sign==032){//报警单元 报警项授权
				MemoryDataManagerTask.loadAlarmInstanceOwnItemByUnitId(param1,method);
			}
			
			else if(sign==041){//删除显示单元
				MemoryDataManagerTask.loadDisplayInstanceOwnItemByUnitId(param1,method);
				acquisitionUnitManagerService.doDisplayUnitBulkDelete(param1);
			}else if(sign==042){//显示单元 显示项授权
				MemoryDataManagerTask.loadDisplayInstanceOwnItemByUnitId(param1,method);
			}
			
			else if(sign==051){//添加采控实例
				MemoryDataManagerTask.loadAcqInstanceOwnItemByName(param1,method);
				EquipmentDriverServerTask.initInstanceConfig(initWellList, method);
			}else if(sign==052){//删除采控实例
				EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolInstanceId(param1,method);
				EquipmentDriverServerTask.initInstanceConfig(initWellList,method);
				MemoryDataManagerTask.loadAcqInstanceOwnItemById(param1,method);
				acquisitionUnitManagerService.doModbusProtocolInstanceBulkDelete(param1);
				MemoryDataManagerTask.loadDeviceInfoByInstanceCode("","update");
				MemoryDataManagerTask.loadDeviceInfoByInstanceCode("","update");
			}else if(sign==053){//采控实例修改，但名称未改变
				MemoryDataManagerTask.loadAcqInstanceOwnItemById(param1,method);
				MemoryDataManagerTask.loadDeviceInfoByInstanceId(param1,"update");
				MemoryDataManagerTask.loadDeviceInfoByInstanceId(param1,"update");
				EquipmentDriverServerTask.initInstanceConfig(initWellList,method);
				EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolInstanceId(param1,method);
			}else if(sign==054){//采控实例修改，但名称改变
				EquipmentDriverServerTask.initInstanceConfig(deleteList, "delete");
				MemoryDataManagerTask.loadAcqInstanceOwnItemById(param1,"update");
				MemoryDataManagerTask.loadDeviceInfoByInstanceId(param1,"update");
				MemoryDataManagerTask.loadDeviceInfoByInstanceId(param1,"update");
				EquipmentDriverServerTask.initInstanceConfig(initWellList,"update");
				EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolInstanceId(param1, "update");
			}
			
			else if(sign==061){//添加显示实例
				MemoryDataManagerTask.loadDisplayInstanceOwnItemByName(param1,method);
			}else if(sign==062){//删除显示实例
				MemoryDataManagerTask.loadDisplayInstanceOwnItemById(param1,method);
				acquisitionUnitManagerService.doModbusProtocolDisplayInstanceBulkDelete(param1);
				MemoryDataManagerTask.loadDeviceInfoByDisplayInstanceCode("","update");
				MemoryDataManagerTask.loadDeviceInfoByDisplayInstanceCode("","update");
			}else if(sign==063){//修改显示实例
				MemoryDataManagerTask.loadDisplayInstanceOwnItemById(param1,method);
				MemoryDataManagerTask.loadDeviceInfoByInstanceId(param1,"update");
				MemoryDataManagerTask.loadDeviceInfoByInstanceId(param1,"update");
			}
			
			else if(sign==071){//添加报警实例
				MemoryDataManagerTask.loadAlarmInstanceOwnItemByName(param1,method);
			}else if(sign==072){//删除报警实例
				MemoryDataManagerTask.loadAlarmInstanceOwnItemById(param1,method);
				acquisitionUnitManagerService.doModbusProtocolAlarmInstanceBulkDelete(param1);
				MemoryDataManagerTask.loadDeviceInfoByAlarmInstanceCode("","update");
				MemoryDataManagerTask.loadDeviceInfoByAlarmInstanceCode("","update");
			}else if(sign==073){//修改报警实例
				MemoryDataManagerTask.loadAlarmInstanceOwnItemById(param1,method);
				MemoryDataManagerTask.loadDeviceInfoByAlarmInstanceId(param1,"update");
				MemoryDataManagerTask.loadDeviceInfoByAlarmInstanceId(param1,"update");
			}
			
			else if(sign==101){//添加抽油机井
				MemoryDataManagerTask.loadDeviceInfo(initWellList,condition,method);
				if(deviceInformation.getStatus()==1){
					EquipmentDriverServerTask.initDriverAcquisitionInfoConfig(initWellList,condition,method);
				}
				deviceManagerService.getBaseDao().saveDeviceOperationLog(updateList, addList, deleteNameList, user,deviceType);
			}else if(sign==102){//删除抽油机井
				EquipmentDriverServerTask.initDriverAcquisitionInfoConfig(deleteList,condition,method);
				MemoryDataManagerTask.loadDeviceInfo(deleteList,condition,method);
				wellInformationManagerService.getBaseDao().saveDeviceOperationLog(updateList,addList,deleteNameList,user,deviceType);
			}else if(sign==103){//修改抽油机井
				MemoryDataManagerTask.loadDeviceInfo(initWellList,condition,method);
				EquipmentDriverServerTask.initDriverAcquisitionInfoConfig(initWellList,condition,method);
				wellInformationManagerService.getBaseDao().saveDeviceOperationLog(updateList,addList,deleteNameList,user,deviceType);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<String> getUpdateList() {
		return updateList;
	}
	public void setUpdateList(List<String> updateList) {
		this.updateList = updateList;
	}
	public List<String> getAddList() {
		return addList;
	}
	public void setAddList(List<String> addList) {
		this.addList = addList;
	}
	public List<String> getDeleteList() {
		return deleteList;
	}
	public void setDeleteList(List<String> deleteList) {
		this.deleteList = deleteList;
	}
	public int getCondition() {
		return condition;
	}
	public void setCondition(int condition) {
		this.condition = condition;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public int getSign() {
		return sign;
	}
	public void setSign(int sign) {
		this.sign = sign;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<String> getInitWellList() {
		return initWellList;
	}
	public void setInitWellList(List<String> initWellList) {
		this.initWellList = initWellList;
	}
	public List<String> getDeleteNameList() {
		return deleteNameList;
	}
	public void setDeleteNameList(List<String> deleteNameList) {
		this.deleteNameList = deleteNameList;
	}
	public WellInformationManagerService<?> getWellInformationManagerService() {
		return wellInformationManagerService;
	}
	public void setWellInformationManagerService(WellInformationManagerService<?> wellInformationManagerService) {
		this.wellInformationManagerService = wellInformationManagerService;
	}
	public AcquisitionUnitManagerService<AcquisitionUnit> getAcquisitionUnitManagerService() {
		return acquisitionUnitManagerService;
	}
	public void setAcquisitionUnitManagerService(
			AcquisitionUnitManagerService<AcquisitionUnit> acquisitionUnitManagerService) {
		this.acquisitionUnitManagerService = acquisitionUnitManagerService;
	}
	public DeviceInformation getDeviceInformation() {
		return deviceInformation;
	}
	public void setDeviceInformation(DeviceInformation deviceInformation) {
		this.deviceInformation = deviceInformation;
	}
	public WellInformationManagerService<DeviceInformation> getDeviceManagerService() {
		return deviceManagerService;
	}
	public void setDeviceManagerService(WellInformationManagerService<DeviceInformation> deviceManagerService) {
		this.deviceManagerService = deviceManagerService;
	}
	public int getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
}
