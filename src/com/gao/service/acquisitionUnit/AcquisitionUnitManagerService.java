package com.gao.service.acquisitionUnit;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gao.model.AcquisitionGroup;
import com.gao.model.AcquisitionItem;
import com.gao.model.AcquisitionUnitGroup;
import com.gao.model.AcquisitionGroupItem;
import com.gao.model.drive.KafkaConfig;
import com.gao.model.drive.RTUDriveConfig;
import com.gao.service.base.BaseService;
import com.gao.service.base.CommonDataService;
import com.gao.task.EquipmentDriverServerTask;
import com.gao.utils.DataSourceConfig;
import com.gao.utils.EquipmentDriveMap;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;
import com.gao.utils.TcpServerConfigMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * <p>描述：角色维护服务</p>
 * 
 * @author gao 2014-06-06
 *
 * @param <T>
 */
@Service("acquisitionUnitManagerService")
@SuppressWarnings("rawtypes")
public class AcquisitionUnitManagerService<T> extends BaseService<T> {
	@Autowired
private CommonDataService service;

	public String getAcquisitionUnitList(Map map,Page pager) {
		String unitName = (String) map.get("unitName");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark from tbl_acq_unit_conf t where 1=1");
		if (StringManagerUtils.isNotNull(unitName)) {
			sqlBuffer.append(" and t.unit_name like '%" + unitName + "%' ");
		}
		sqlBuffer.append(" order by t.id  asc");
		String json = "";
		String columns=service.showTableHeadersColumns("acquisitionUnit");
		try {
			json=this.findPageBySqlEntity(sqlBuffer.toString(),columns , pager );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public String doAcquisitionGroupShow(Map map,Page pager) {
		String groupName = (String) map.get("groupName");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.id as id,t.group_code as groupCode,t.group_name as groupName,"
				+ "t.acq_cycle as acqCycle,t.save_cycle as saveCycle,t.remark from tbl_acq_group_conf t where 1=1");
		if (StringManagerUtils.isNotNull(groupName)) {
			sqlBuffer.append(" and t.group_name like '%" + groupName + "%' ");
		}
		sqlBuffer.append(" order by t.id  asc");
		String json = "";
//		String columns=service.showTableHeadersColumns("acquisitionUnit");
		String columns="["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"groupName\" ,children:[] },"
				+ "{ \"header\":\"编码\",\"dataIndex\":\"groupCode\" ,children:[] },"
				+ "{ \"header\":\"采集周期(min)\",\"dataIndex\":\"acqCycle\" ,children:[] },"
				+ "{ \"header\":\"保存周期(min)\",\"dataIndex\":\"saveCycle\" ,children:[] },"
				+ "{ \"header\":\"描述\",\"dataIndex\":\"remark\",width:200 ,children:[] }"
				+ "]";
		try {
			json=this.findPageBySqlEntity(sqlBuffer.toString(),columns , pager );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public String getDriverConfigData(){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.initDriverConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		//驱动排序
		Map<Integer,Object> equipmentDriveSortMap=new TreeMap<Integer,Object>();
		for(Entry<String, Object> entry:equipmentDriveMap.entrySet()){
			if(!(entry.getKey().toUpperCase().contains("KAFKA")||entry.getKey().toUpperCase().contains("MQTT"))){
				RTUDriveConfig driveConfig=(RTUDriveConfig)entry.getValue();
				equipmentDriveSortMap.put(driveConfig.getSort(), driveConfig);
			}
			
		}
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"协议名称\",\"dataIndex\":\"ProtocolName\",width:120 ,children:[] },"
				+ "{ \"header\":\"协议类型\",\"dataIndex\":\"ProtocolType\",width:50 ,children:[] },"
				+ "{ \"header\":\"储存模式\",\"dataIndex\":\"StoreMode\",width:50 ,children:[] },"
				+ "{ \"header\":\"注册包/心跳包前缀\",\"dataIndex\":\"HeartbeatPrefix\",width:50 ,children:[] },"
				+ "{ \"header\":\"注册包/心跳包后缀\",\"dataIndex\":\"HeartbeatSuffix\",width:50 ,children:[] }"
				+ "]";
		
		String diagramTableColumns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"item\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"address\",width:80 ,children:[] },"
				+ "{ \"header\":\"寄存器数量\",\"dataIndex\":\"length\",width:80 ,children:[] },"
				+ "{ \"header\":\"数据类型\",\"dataIndex\":\"dataType\",width:80 ,children:[] },"
				+ "{ \"header\":\"读写类型\",\"dataIndex\":\"readonly\",width:80 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\"单位换算系数\",\"dataIndex\":\"zoom\",width:80 ,children:[] },"
				+ "{ \"header\":\"模式\",\"dataIndex\":\"initiative\",width:80 ,children:[] }"
				+ "]";
		
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",\"diagramTableColumns\":"+diagramTableColumns+",");
		result_json.append("\"totalRoot\":[");
		int i=0;
		for(Entry<Integer, Object> entry:equipmentDriveSortMap.entrySet()){
			i++;
			RTUDriveConfig driveConfig=(RTUDriveConfig)entry.getValue();
			if(!driveConfig.getProtocolCode().toUpperCase().contains("KAFKA") && !driveConfig.getProtocolCode().toUpperCase().contains("MQTT")){
				StringBuffer driverConfigData = new StringBuffer();
				driverConfigData.append("[");
				if("IntelligentPumpingUnitDrive".equalsIgnoreCase(driveConfig.getProtocolCode())){
					
				}else{
					
				}
				driverConfigData.append("{\"checked\":false,\"id\":1,\"item\":\"运行状态\",\"address\":"+driveConfig.getDataConfig().getRunStatus().getAddress()+",\"length\":"+driveConfig.getDataConfig().getRunStatus().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getRunStatus().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getRunStatus().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getRunStatus().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getRunStatus().getUnit()+"\","
						+ "\"initiative\":\""+(driveConfig.getDataConfig().getRunStatus().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":2,\"item\":\"启停控制\",\"address\":"+driveConfig.getDataConfig().getRunControl().getAddress()+",\"length\":"+driveConfig.getDataConfig().getRunControl().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getRunControl().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getRunControl().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getRunControl().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getRunControl().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getRunControl().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":3,\"item\":\"A相电流\",\"address\":"+driveConfig.getDataConfig().getCurrentA().getAddress()+",\"length\":"+driveConfig.getDataConfig().getCurrentA().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getCurrentA().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getCurrentA().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getCurrentA().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getCurrentA().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getCurrentA().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":4,\"item\":\"B相电流\",\"address\":"+driveConfig.getDataConfig().getCurrentB().getAddress()+",\"length\":"+driveConfig.getDataConfig().getCurrentB().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getCurrentB().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getCurrentB().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getCurrentB().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getCurrentB().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getCurrentB().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":5,\"item\":\"C相电流\",\"address\":"+driveConfig.getDataConfig().getCurrentC().getAddress()+",\"length\":"+driveConfig.getDataConfig().getCurrentC().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getCurrentC().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getCurrentC().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getCurrentC().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getCurrentC().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getCurrentC().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":6,\"item\":\"A相电压\",\"address\":"+driveConfig.getDataConfig().getVoltageA().getAddress()+",\"length\":"+driveConfig.getDataConfig().getVoltageA().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getVoltageA().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getVoltageA().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getVoltageA().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getVoltageA().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getVoltageA().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":7,\"item\":\"B相电压\",\"address\":"+driveConfig.getDataConfig().getVoltageB().getAddress()+",\"length\":"+driveConfig.getDataConfig().getVoltageB().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getVoltageB().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getVoltageB().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getVoltageB().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getVoltageB().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getVoltageB().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":8,\"item\":\"C相电压\",\"address\":"+driveConfig.getDataConfig().getVoltageC().getAddress()+",\"length\":"+driveConfig.getDataConfig().getVoltageC().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getVoltageC().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getVoltageC().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getVoltageC().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getVoltageC().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getVoltageC().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":9,\"item\":\"有功功耗\",\"address\":"+driveConfig.getDataConfig().getActivePowerConsumption().getAddress()+",\"length\":"+driveConfig.getDataConfig().getActivePowerConsumption().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getActivePowerConsumption().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getActivePowerConsumption().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getActivePowerConsumption().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getActivePowerConsumption().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getActivePowerConsumption().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":10,\"item\":\"无功功耗\",\"address\":"+driveConfig.getDataConfig().getReactivePowerConsumption().getAddress()+",\"length\":"+driveConfig.getDataConfig().getReactivePowerConsumption().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getReactivePowerConsumption().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getReactivePowerConsumption().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getReactivePowerConsumption().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getReactivePowerConsumption().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getReactivePowerConsumption().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":11,\"item\":\"有功功率\",\"address\":"+driveConfig.getDataConfig().getActivePower().getAddress()+",\"length\":"+driveConfig.getDataConfig().getActivePower().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getActivePower().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getActivePower().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getActivePower().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getActivePower().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getActivePower().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":12,\"item\":\"无功功率\",\"address\":"+driveConfig.getDataConfig().getReactivePower().getAddress()+",\"length\":"+driveConfig.getDataConfig().getReactivePower().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getReactivePower().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getReactivePower().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getReactivePower().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getReactivePower().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getReactivePower().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":13,\"item\":\"反向功率\",\"address\":"+driveConfig.getDataConfig().getReversePower().getAddress()+",\"length\":"+driveConfig.getDataConfig().getReversePower().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getReversePower().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getReversePower().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getReversePower().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getReversePower().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getReversePower().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":14,\"item\":\"功率因数\",\"address\":"+driveConfig.getDataConfig().getPowerFactor().getAddress()+",\"length\":"+driveConfig.getDataConfig().getPowerFactor().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getPowerFactor().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getPowerFactor().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getPowerFactor().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getPowerFactor().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getPowerFactor().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":15,\"item\":\"油压\",\"address\":"+driveConfig.getDataConfig().getTubingPressure().getAddress()+",\"length\":"+driveConfig.getDataConfig().getTubingPressure().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getTubingPressure().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getTubingPressure().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getTubingPressure().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getTubingPressure().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getTubingPressure().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":16,\"item\":\"套压\",\"address\":"+driveConfig.getDataConfig().getCasingPressure().getAddress()+",\"length\":"+driveConfig.getDataConfig().getCasingPressure().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getCasingPressure().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getCasingPressure().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getCasingPressure().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getCasingPressure().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getCasingPressure().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":17,\"item\":\"回压\",\"address\":"+driveConfig.getDataConfig().getBackPressure().getAddress()+",\"length\":"+driveConfig.getDataConfig().getBackPressure().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getBackPressure().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getBackPressure().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getBackPressure().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getBackPressure().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getBackPressure().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":18,\"item\":\"井口流温\",\"address\":"+driveConfig.getDataConfig().getWellHeadFluidTemperature().getAddress()+",\"length\":"+driveConfig.getDataConfig().getWellHeadFluidTemperature().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getWellHeadFluidTemperature().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getWellHeadFluidTemperature().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getWellHeadFluidTemperature().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getWellHeadFluidTemperature().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getWellHeadFluidTemperature().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":19,\"item\":\"动液面\",\"address\":"+driveConfig.getDataConfig().getProducingfluidLevel().getAddress()+",\"length\":"+driveConfig.getDataConfig().getProducingfluidLevel().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getProducingfluidLevel().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getProducingfluidLevel().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getProducingfluidLevel().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getProducingfluidLevel().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getProducingfluidLevel().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":20,\"item\":\"含水率\",\"address\":"+driveConfig.getDataConfig().getWaterCut().getAddress()+",\"length\":"+driveConfig.getDataConfig().getWaterCut().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getWaterCut().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getWaterCut().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getWaterCut().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getWaterCut().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getWaterCut().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":21,\"item\":\"变频设置频率\",\"address\":"+driveConfig.getDataConfig().getSetFrequency().getAddress()+",\"length\":"+driveConfig.getDataConfig().getSetFrequency().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getSetFrequency().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getSetFrequency().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getSetFrequency().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getSetFrequency().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getSetFrequency().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":22,\"item\":\"变频运行频率\",\"address\":"+driveConfig.getDataConfig().getRunFrequency().getAddress()+",\"length\":"+driveConfig.getDataConfig().getRunFrequency().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getRunFrequency().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getRunFrequency().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getRunFrequency().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getRunFrequency().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getRunFrequency().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":23,\"item\":\"螺杆泵转速\",\"address\":"+driveConfig.getDataConfig().getRPM().getAddress()+",\"length\":"+driveConfig.getDataConfig().getRPM().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getRPM().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getRPM().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getRPM().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getRPM().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getRPM().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":24,\"item\":\"螺杆泵扭矩\",\"address\":"+driveConfig.getDataConfig().getTorque().getAddress()+",\"length\":"+driveConfig.getDataConfig().getTorque().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getTorque().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getTorque().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getTorque().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getTorque().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getTorque().getInitiative()?"主动轮询":"被动接收")+"\"},");
				//功图
				driverConfigData.append("{\"checked\":false,\"id\":25,\"item\":\"功图采集间隔\",\"address\":"+driveConfig.getDataConfig().getFSDiagramAcquisitionInterval().getAddress()+",\"length\":"+driveConfig.getDataConfig().getFSDiagramAcquisitionInterval().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getFSDiagramAcquisitionInterval().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getFSDiagramAcquisitionInterval().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getFSDiagramAcquisitionInterval().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getFSDiagramAcquisitionInterval().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getFSDiagramAcquisitionInterval().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":26,\"item\":\"功图设置点数\",\"address\":"+driveConfig.getDataConfig().getFSDiagramSetPointCount().getAddress()+",\"length\":"+driveConfig.getDataConfig().getFSDiagramSetPointCount().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getFSDiagramSetPointCount().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getFSDiagramSetPointCount().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getFSDiagramSetPointCount().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getFSDiagramSetPointCount().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getFSDiagramSetPointCount().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":27,\"item\":\"功图实测点数\",\"address\":"+driveConfig.getDataConfig().getFSDiagramPointCount().getAddress()+",\"length\":"+driveConfig.getDataConfig().getFSDiagramPointCount().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getFSDiagramPointCount().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getFSDiagramPointCount().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getFSDiagramPointCount().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getFSDiagramPointCount().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getFSDiagramPointCount().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":28,\"item\":\"功图采集时间\",\"address\":"+driveConfig.getDataConfig().getAcqTime().getAddress()+",\"length\":"+driveConfig.getDataConfig().getAcqTime().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getAcqTime().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getAcqTime().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getAcqTime().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getAcqTime().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getAcqTime().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":29,\"item\":\"冲次\",\"address\":"+driveConfig.getDataConfig().getSPM().getAddress()+",\"length\":"+driveConfig.getDataConfig().getSPM().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getSPM().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getSPM().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getSPM().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getSPM().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getSPM().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":30,\"item\":\"冲程\",\"address\":"+driveConfig.getDataConfig().getStroke().getAddress()+",\"length\":"+driveConfig.getDataConfig().getStroke().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getStroke().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getStroke().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getStroke().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getStroke().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getStroke().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":31,\"item\":\"功图数据-位移\",\"address\":"+driveConfig.getDataConfig().getSDiagram().getAddress()+",\"length\":"+driveConfig.getDataConfig().getSDiagram().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getSDiagram().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getSDiagram().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getSDiagram().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getSDiagram().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getSDiagram().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":32,\"item\":\"功图数据-载荷\",\"address\":"+driveConfig.getDataConfig().getFDiagram().getAddress()+",\"length\":"+driveConfig.getDataConfig().getFDiagram().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getFDiagram().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getFDiagram().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getFDiagram().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getFDiagram().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getFDiagram().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":33,\"item\":\"功图数据-电流\",\"address\":"+driveConfig.getDataConfig().getADiagram().getAddress()+",\"length\":"+driveConfig.getDataConfig().getADiagram().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getADiagram().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getADiagram().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getADiagram().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getADiagram().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getADiagram().getInitiative()?"主动轮询":"被动接收")+"\"},");
				driverConfigData.append("{\"checked\":false,\"id\":34,\"item\":\"功图数据-功率\",\"address\":"+driveConfig.getDataConfig().getPDiagram().getAddress()+",\"length\":"+driveConfig.getDataConfig().getPDiagram().getLength()+","
						+ "\"dataType\":\""+getDataItemsType(driveConfig.getDataConfig().getPDiagram().getDataType())+"\",\"zoom\":"+driveConfig.getDataConfig().getPDiagram().getZoom()+","
						+ "\"readonly\":\""+(driveConfig.getDataConfig().getPDiagram().getReadonly()?"只读":"读写")+"\","
						+ "\"unit\":\""+driveConfig.getDataConfig().getPDiagram().getUnit()+"\","
						+"\"initiative\":\""+(driveConfig.getDataConfig().getPDiagram().getInitiative()?"主动轮询":"被动接收")+"\"}");
				driverConfigData.append("]");
				result_json.append("{"
						+ "\"id\":"+i+","
						+ "\"ProtocolName\":\""+driveConfig.getProtocolName()+"\","
						+ "\"ProtocolType\":\""+(driveConfig.getProtocolType()==0?"modbus-tcp":"modbus-rtu")+"\","
						+ "\"StoreMode\":\""+(driveConfig.getStoreMode()==0?"大端":"小端")+"\","
						+ "\"HeartbeatPrefix\":\""+driveConfig.getHeartbeatPrefix()+"\","
						+ "\"HeartbeatSuffix\":\""+driveConfig.getHeartbeatSuffix()+"\","
						+ "\"dataConfig\":"+driverConfigData.toString()+""
						+ "},");
			}
			
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString();
	}
	
	public String getKafkaDriverConfigData(){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"项\",\"dataIndex\":\"item\",width:120 ,children:[] },"
				+ "{ \"header\":\"值\",\"dataIndex\":\"value\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.initDriverConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		KafkaConfig driveConfig=(KafkaConfig)equipmentDriveMap.get("KafkaDrive");
		
		result_json.append("\"totalRoot\":[");
		if(driveConfig!=null){
			
			result_json.append("{\"id\":\"序号\",\"item\":\"项\",\"value\":\"值\"},");
			
			result_json.append("{\"id\":\"Server\",\"item\":\"\",\"value\":\"\"},");
			result_json.append("{\"id\":1,\"item\":\"IP\",\"value\":\""+driveConfig.getServer().getIP()+"\"},");
			result_json.append("{\"id\":2,\"item\":\"Port\",\"value\":\""+driveConfig.getServer().getPort()+"\"},");
			
			result_json.append("{\"id\":\"上行主题\",\"item\":\"\",\"value\":\"\"},");
			result_json.append("{\"id\":1,\"item\":\"上行数据\",\"value\":\""+driveConfig.getTopic().getUp().getNormData()+"\"},");
			result_json.append("{\"id\":2,\"item\":\"上行原始数据\",\"value\":\""+driveConfig.getTopic().getUp().getRawData()+"\"},");
			result_json.append("{\"id\":3,\"item\":\"上行配置\",\"value\":\""+driveConfig.getTopic().getUp().getConfig()+"\"},");
			result_json.append("{\"id\":4,\"item\":\"上行模型\",\"value\":\""+driveConfig.getTopic().getUp().getModel()+"\"},");
			result_json.append("{\"id\":5,\"item\":\"上行频率\",\"value\":\""+driveConfig.getTopic().getUp().getFreq()+"\"},");
			result_json.append("{\"id\":6,\"item\":\"上行时钟\",\"value\":\""+driveConfig.getTopic().getUp().getRTC()+"\"},");
			result_json.append("{\"id\":7,\"item\":\"上行上线/离线\",\"value\":\""+driveConfig.getTopic().getUp().getOnline()+"\"},");
			result_json.append("{\"id\":8,\"item\":\"上行运行/停止\",\"value\":\""+driveConfig.getTopic().getUp().getRunStatus()+"\"},");
			
			result_json.append("{\"id\":\"下行主题\",\"item\":\"\",\"value\":\"\"},");
			result_json.append("{\"id\":1,\"item\":\"下行模型\",\"value\":\""+driveConfig.getTopic().getDown().getModel()+"\"},");
			result_json.append("{\"id\":2,\"item\":\"下行PVT物性\",\"value\":\""+driveConfig.getTopic().getDown().getModel_FluidPVT()+"\"},");
			result_json.append("{\"id\":3,\"item\":\"下行油藏\",\"value\":\""+driveConfig.getTopic().getDown().getModel_Reservoir()+"\"},");
			result_json.append("{\"id\":4,\"item\":\"下行井身轨迹\",\"value\":\""+driveConfig.getTopic().getDown().getModel_WellboreTrajectory()+"\"},");
			result_json.append("{\"id\":5,\"item\":\"下行杆柱数据\",\"value\":\""+driveConfig.getTopic().getDown().getModel_RodString()+"\"},");
			result_json.append("{\"id\":6,\"item\":\"下行管柱数据\",\"value\":\""+driveConfig.getTopic().getDown().getModel_TubingString()+"\"},");
			result_json.append("{\"id\":7,\"item\":\"下行泵数据\",\"value\":\""+driveConfig.getTopic().getDown().getModel_Pump()+"\"},");
			result_json.append("{\"id\":8,\"item\":\"下行尾管数据\",\"value\":\""+driveConfig.getTopic().getDown().getModel_TailtubingString()+"\"},");
			result_json.append("{\"id\":9,\"item\":\"下行套管数据\",\"value\":\""+driveConfig.getTopic().getDown().getModel_CasingString()+"\"},");
			result_json.append("{\"id\":10,\"item\":\"下行抽油机数据\",\"value\":\""+driveConfig.getTopic().getDown().getModel_PumpingUnit()+"\"},");
			result_json.append("{\"id\":11,\"item\":\"下行系统效率\",\"value\":\""+driveConfig.getTopic().getDown().getModel_SystemEfficiency()+"\"},");
			result_json.append("{\"id\":12,\"item\":\"下行生产参数\",\"value\":\""+driveConfig.getTopic().getDown().getModel_Production()+"\"},");
			result_json.append("{\"id\":13,\"item\":\"下行特征库\",\"value\":\""+driveConfig.getTopic().getDown().getModel_FeatureDB()+"\"},");
			result_json.append("{\"id\":14,\"item\":\"下行计算方法\",\"value\":\""+driveConfig.getTopic().getDown().getModel_CalculationMethod()+"\"},");
			result_json.append("{\"id\":15,\"item\":\"下行人工干预\",\"value\":\""+driveConfig.getTopic().getDown().getModel_ManualIntervention()+"\"},");
			
			result_json.append("{\"id\":16,\"item\":\"下行配置\",\"value\":\""+driveConfig.getTopic().getDown().getConfig()+"\"},");
			result_json.append("{\"id\":17,\"item\":\"下行启抽\",\"value\":\""+driveConfig.getTopic().getDown().getStartRPC()+"\"},");
			result_json.append("{\"id\":18,\"item\":\"下行停抽\",\"value\":\""+driveConfig.getTopic().getDown().getStopRPC()+"\"},");
			result_json.append("{\"id\":19,\"item\":\"下行看门狗重启\",\"value\":\""+driveConfig.getTopic().getDown().getDogRestart()+"\"},");
			result_json.append("{\"id\":20,\"item\":\"下行频率\",\"value\":\""+driveConfig.getTopic().getDown().getFreq()+"\"},");
			result_json.append("{\"id\":21,\"item\":\"下行时钟\",\"value\":\""+driveConfig.getTopic().getDown().getRTC()+"\"},");
			result_json.append("{\"id\":22,\"item\":\"下行请求读驱动配置\",\"value\":\""+driveConfig.getTopic().getDown().getReq()+"\"},");
			result_json.append("{\"id\":23,\"item\":\"下行程序A9\",\"value\":\""+driveConfig.getTopic().getDown().getA9()+"\"},");
			result_json.append("{\"id\":24,\"item\":\"下行程序AC\",\"value\":\""+driveConfig.getTopic().getDown().getAC()+"\"}");
		}
		result_json.append("]");
		result_json.append("}");
		
		return result_json.toString();
//		return gson.toJson(driveConfig);
	}
	
	
	public void doAcquisitionGroupAdd(AcquisitionGroup acquisitionGroup) throws Exception {
		getBaseDao().addObject(acquisitionGroup);
	}
	
	public void doAcquisitionGroupEdit(AcquisitionGroup acquisitionGroup) throws Exception {
		getBaseDao().updateObject(acquisitionGroup);
	}
	
	public void doAcquisitionGroupBulkDelete(final String ids) throws Exception {
		final String hql = "DELETE AcquisitionGroup u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}
	
	public void doAcquisitionUnitAdd(T acquisitionUnit) throws Exception {
		getBaseDao().addObject(acquisitionUnit);
	}
	
	public void doAcquisitionUnitEdit(T acquisitionUnit) throws Exception {
		getBaseDao().updateObject(acquisitionUnit);
	}
	
	public void doAcquisitionUnitBulkDelete(final String ids) throws Exception {
		final String hql = "DELETE AcquisitionUnit u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}
	
	public List<T> queryAcquisitionItemsData(Class<AcquisitionItem> class1) {
		String queryString = "SELECT u FROM AcquisitionItem u  order by u.seq ";
		return getBaseDao().find(queryString);
	}
	
	public List<T> showAcquisitionGroupOwnItems(Class<AcquisitionGroupItem> class1, String groupId) {
		if(!StringManagerUtils.isNotNull(groupId)){
			groupId="0";
		}
		String queryString = "select u FROM AcquisitionGroupItem u where   u.groupId=" + groupId + " order by u.id asc";
		return getBaseDao().find(queryString);
	}
	
	public List<T> showAcquisitionUnitOwnGroups(Class<AcquisitionUnitGroup> class1, String unitId) {
		if(!StringManagerUtils.isNotNull(unitId)){
			unitId="0";
		}
		String queryString = "select u FROM AcquisitionUnitGroup u where   u.unitId=" + unitId + " order by u.id asc";
		return getBaseDao().find(queryString);
	}
	
	public void deleteCurrentAcquisitionGroupOwnItems(final String groupId) throws Exception {
		final String hql = "DELETE AcquisitionGroupItem u where u.groupId = " + groupId + "";
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void deleteCurrentAcquisitionUnitOwnGroups(final String unitId) throws Exception {
		final String hql = "DELETE AcquisitionUnitGroup u where u.unitId = " + unitId + "";
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void grantAcquisitionItemsPermission(T acquisitionUnitItem) throws Exception {
		getBaseDao().saveOrUpdateObject(acquisitionUnitItem);
	}
	
	public void grantAcquisitionGroupsPermission(AcquisitionUnitGroup r) throws Exception {
		getBaseDao().saveOrUpdateObject(r);
	}
	
	public static String getDataItemsType(int type){
		String dataType="";
		if(type==1){
			dataType="整型";
		}else if(type==2){
			dataType="实型";
		}else if(type==3){
			dataType="BCD码";
		}
		return dataType;
	}
}
