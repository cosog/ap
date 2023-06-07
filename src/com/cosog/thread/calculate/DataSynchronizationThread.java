package com.cosog.thread.calculate;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cosog.dao.BaseDao;
import com.cosog.model.AcquisitionUnit;
import com.cosog.model.PcpDeviceInformation;
import com.cosog.model.RpcDeviceInformation;
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
	public int deviceType;
	public String method;
	public String param1;
	public int sign;
	public User user;
	
	public AcquisitionUnitManagerService<AcquisitionUnit> acquisitionUnitManagerService;
	
	public RpcDeviceInformation rpcDeviceInformation;
	public PcpDeviceInformation pcpDeviceInformation;
	public WellInformationManagerService<?> wellInformationManagerService;
	public WellInformationManagerService<RpcDeviceInformation> rpcDeviceManagerService;
	public WellInformationManagerService<PcpDeviceInformation> pcpDeviceManagerService;
	public void run(){
		try {
			if(sign==001){//创建协议
				EquipmentDriverServerTask.initProtocolConfig(param1,method);
			}else if(sign==002){//删除协议
				MemoryDataManagerTask.loadAcqInstanceOwnItemByProtocolName(param1,method);
				MemoryDataManagerTask.loadAlarmInstanceOwnItemByProtocolName(param1,method);
				MemoryDataManagerTask.loadDisplayInstanceOwnItemByProtocolName(param1,method);
				
				EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolName(param1,deviceType,method);
				EquipmentDriverServerTask.initInstanceConfigByProtocolName(param1,method);
				EquipmentDriverServerTask.initProtocolConfig(param1,method);
				this.acquisitionUnitManagerService.doDeleteProtocolAssociation(deviceType,param1);
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
				acquisitionUnitManagerService.doAcquisitionUnitBulkDelete(param1,deviceType+"");
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
				acquisitionUnitManagerService.doDisplayUnitBulkDelete(param1,deviceType+"");
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
				acquisitionUnitManagerService.doModbusProtocolInstanceBulkDelete(param1,deviceType);
				MemoryDataManagerTask.loadRPCDeviceInfoByInstanceCode("","update");
				MemoryDataManagerTask.loadPCPDeviceInfoByInstanceCode("","update");
			}else if(sign==053){//采控实例修改，但名称未改变
				MemoryDataManagerTask.loadAcqInstanceOwnItemById(param1,method);
				MemoryDataManagerTask.loadRPCDeviceInfoByInstanceId(param1,"update");
				MemoryDataManagerTask.loadPCPDeviceInfoByInstanceId(param1,"update");
				EquipmentDriverServerTask.initInstanceConfig(initWellList,method);
				EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolInstanceId(param1,method);
			}else if(sign==054){//采控实例修改，但名称改变
				EquipmentDriverServerTask.initInstanceConfig(deleteList, "delete");
				MemoryDataManagerTask.loadAcqInstanceOwnItemById(param1,"update");
				MemoryDataManagerTask.loadRPCDeviceInfoByInstanceId(param1,"update");
				MemoryDataManagerTask.loadPCPDeviceInfoByInstanceId(param1,"update");
				EquipmentDriverServerTask.initInstanceConfig(initWellList,"update");
				EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolInstanceId(param1, "update");
			}
			
			else if(sign==061){//添加显示实例
				MemoryDataManagerTask.loadDisplayInstanceOwnItemByName(param1,method);
			}else if(sign==062){//删除显示实例
				MemoryDataManagerTask.loadDisplayInstanceOwnItemById(param1,method);
				acquisitionUnitManagerService.doModbusProtocolDisplayInstanceBulkDelete(param1);
				MemoryDataManagerTask.loadRPCDeviceInfoByDisplayInstanceCode("","update");
				MemoryDataManagerTask.loadPCPDeviceInfoByDisplayInstanceCode("","update");
			}else if(sign==063){//修改显示实例
				MemoryDataManagerTask.loadDisplayInstanceOwnItemById(param1,method);
				MemoryDataManagerTask.loadRPCDeviceInfoByInstanceId(param1,"update");
				MemoryDataManagerTask.loadPCPDeviceInfoByInstanceId(param1,"update");
			}
			
			else if(sign==071){//添加报警实例
				MemoryDataManagerTask.loadAlarmInstanceOwnItemByName(param1,method);
			}else if(sign==072){//删除报警实例
				MemoryDataManagerTask.loadAlarmInstanceOwnItemById(param1,method);
				acquisitionUnitManagerService.doModbusProtocolAlarmInstanceBulkDelete(param1);
				MemoryDataManagerTask.loadRPCDeviceInfoByAlarmInstanceCode("","update");
				MemoryDataManagerTask.loadPCPDeviceInfoByAlarmInstanceCode("","update");
			}else if(sign==073){//修改报警实例
				MemoryDataManagerTask.loadAlarmInstanceOwnItemById(param1,method);
				MemoryDataManagerTask.loadRPCDeviceInfoByAlarmInstanceId(param1,"update");
				MemoryDataManagerTask.loadPCPDeviceInfoByAlarmInstanceId(param1,"update");
			}
			
			else if(sign==101){//添加抽油机井
				MemoryDataManagerTask.loadRPCDeviceInfo(initWellList,condition,method);
				if(rpcDeviceInformation.getStatus()==1){
					EquipmentDriverServerTask.initRPCDriverAcquisitionInfoConfig(initWellList,condition,method);
				}
				rpcDeviceManagerService.getBaseDao().saveDeviceOperationLog(updateList, addList, deleteNameList, rpcDeviceInformation.getDeviceType(), user);
			}else if(sign==102){//删除抽油机井
				EquipmentDriverServerTask.initRPCDriverAcquisitionInfoConfig(deleteList,condition,method);
				MemoryDataManagerTask.loadRPCDeviceInfo(deleteList,condition,method);
				wellInformationManagerService.getBaseDao().saveDeviceOperationLog(updateList,addList,deleteNameList,deviceType,user);
			}else if(sign==103){//修改抽油机井
				MemoryDataManagerTask.loadRPCDeviceInfo(initWellList,condition,method);
				EquipmentDriverServerTask.initRPCDriverAcquisitionInfoConfig(initWellList,condition,method);
				wellInformationManagerService.getBaseDao().saveDeviceOperationLog(updateList,addList,deleteNameList,deviceType,user);
			}else if(sign==201){//添加螺杆泵井
				MemoryDataManagerTask.loadPCPDeviceInfo(initWellList,condition,method);
				if(pcpDeviceInformation.getStatus()==1){
					EquipmentDriverServerTask.initPCPDriverAcquisitionInfoConfig(initWellList,condition,method);
				}
				pcpDeviceManagerService.getBaseDao().saveDeviceOperationLog(updateList, addList, deleteNameList, pcpDeviceInformation.getDeviceType(), user);
			}else if(sign==202){//删除螺杆泵井
				EquipmentDriverServerTask.initPCPDriverAcquisitionInfoConfig(deleteList,condition,method);
				MemoryDataManagerTask.loadPCPDeviceInfo(deleteList,condition,method);
				wellInformationManagerService.getBaseDao().saveDeviceOperationLog(updateList,addList,deleteNameList,deviceType,user);
			}else if(sign==203){//修改螺杆泵井
				MemoryDataManagerTask.loadPCPDeviceInfo(initWellList,condition,method);
				EquipmentDriverServerTask.initPCPDriverAcquisitionInfoConfig(initWellList,condition,method);
				wellInformationManagerService.getBaseDao().saveDeviceOperationLog(updateList,addList,deleteNameList,deviceType,user);
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
	public int getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
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
	public RpcDeviceInformation getRpcDeviceInformation() {
		return rpcDeviceInformation;
	}
	public void setRpcDeviceInformation(RpcDeviceInformation rpcDeviceInformation) {
		this.rpcDeviceInformation = rpcDeviceInformation;
	}
	public PcpDeviceInformation getPcpDeviceInformation() {
		return pcpDeviceInformation;
	}
	public void setPcpDeviceInformation(PcpDeviceInformation pcpDeviceInformation) {
		this.pcpDeviceInformation = pcpDeviceInformation;
	}
	public WellInformationManagerService<RpcDeviceInformation> getRpcDeviceManagerService() {
		return rpcDeviceManagerService;
	}
	public void setRpcDeviceManagerService(WellInformationManagerService<RpcDeviceInformation> rpcDeviceManagerService) {
		this.rpcDeviceManagerService = rpcDeviceManagerService;
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
	public WellInformationManagerService<PcpDeviceInformation> getPcpDeviceManagerService() {
		return pcpDeviceManagerService;
	}
	public void setPcpDeviceManagerService(WellInformationManagerService<PcpDeviceInformation> pcpDeviceManagerService) {
		this.pcpDeviceManagerService = pcpDeviceManagerService;
	}
	public AcquisitionUnitManagerService<AcquisitionUnit> getAcquisitionUnitManagerService() {
		return acquisitionUnitManagerService;
	}
	public void setAcquisitionUnitManagerService(
			AcquisitionUnitManagerService<AcquisitionUnit> acquisitionUnitManagerService) {
		this.acquisitionUnitManagerService = acquisitionUnitManagerService;
	}
}
