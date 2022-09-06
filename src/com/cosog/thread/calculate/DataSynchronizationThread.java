package com.cosog.thread.calculate;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cosog.dao.BaseDao;
import com.cosog.model.PcpDeviceInformation;
import com.cosog.model.RpcDeviceInformation;
import com.cosog.model.User;
import com.cosog.service.back.WellInformationManagerService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;

public class DataSynchronizationThread  extends Thread{
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
	public RpcDeviceInformation rpcDeviceInformation;
	public PcpDeviceInformation pcpDeviceInformation;
	public WellInformationManagerService<?> wellInformationManagerService;
	public WellInformationManagerService<RpcDeviceInformation> rpcDeviceManagerService;
	public void run(){
		try {
			if(sign==0){//添加抽油机
				MemoryDataManagerTask.loadRPCDeviceInfo(initWellList,condition,method);
				if(rpcDeviceInformation.getStatus()==1){
					EquipmentDriverServerTask.initRPCDriverAcquisitionInfoConfig(initWellList,condition,method);
				}
				rpcDeviceManagerService.getBaseDao().saveDeviceOperationLog(updateList, addList, deleteNameList, rpcDeviceInformation.getDeviceType(), user);
			}else if(sign==1){//删除抽油机
				EquipmentDriverServerTask.initRPCDriverAcquisitionInfoConfig(deleteList,condition,method);
				MemoryDataManagerTask.loadRPCDeviceInfo(deleteList,condition,method);
				wellInformationManagerService.getBaseDao().saveDeviceOperationLog(updateList,addList,deleteNameList,deviceType,user);
			}else if(sign==2){//修改抽油机
				MemoryDataManagerTask.loadRPCDeviceInfo(initWellList,condition,method);
				EquipmentDriverServerTask.initRPCDriverAcquisitionInfoConfig(initWellList,condition,method);
				wellInformationManagerService.getBaseDao().saveDeviceOperationLog(updateList,addList,deleteNameList,deviceType,user);
			}else if(sign==3){
				
			}else if(sign==4){
				
			}
		} catch (SQLException e) {
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
}
