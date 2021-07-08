package com.cosog.service.acquisitionUnit;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AcquisitionGroup;
import com.cosog.model.AcquisitionGroupItem;
import com.cosog.model.AcquisitionItem;
import com.cosog.model.AcquisitionUnitGroup;
import com.cosog.model.drive.KafkaConfig;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.utils.DataSourceConfig;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.TcpServerConfigMap;
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
		String protocolName = (String) map.get("protocolName");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark "
				+ " from tbl_acq_unit_conf t where 1=1");
		if (StringManagerUtils.isNotNull(protocolName)) {
			sqlBuffer.append(" and t.protocol = '" + protocolName + "' ");
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
		String protocolName = (String) map.get("protocolName");
		StringBuffer sqlBuffer = new StringBuffer();
		StringBuffer jsonBuffer = new StringBuffer();
		sqlBuffer.append("select t.id as id,t.group_name as groupName,t.group_code as groupCode,"
				+ " t.acq_cycle as acqCycle,t.save_cycle as saveCycle,t.remark "
				+ " from tbl_acq_group_conf t"
				+ " where 1=1");
		sqlBuffer.append(" and t.protocol='"+protocolName+"'");
		if (StringManagerUtils.isNotNull(groupName)) {
			sqlBuffer.append(" and t.group_name like '%" + groupName + "%' ");
		}
		sqlBuffer.append(" order by t.id  asc");
		String json = "";
//		String columns=service.showTableHeadersColumns("acquisitionUnit");
		String columns="["
				+ "{ \"header\":\"\",\"dataIndex\":\"checked\",width:50 ,children:[] },"
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"groupName\" ,children:[] },"
				+ "{ \"header\":\"编码\",\"dataIndex\":\"groupCode\" ,children:[] },"
				+ "{ \"header\":\"采集周期(s)\",\"dataIndex\":\"acqCycle\" ,children:[] },"
				+ "{ \"header\":\"保存周期(s)\",\"dataIndex\":\"saveCycle\" ,children:[] },"
				+ "{ \"header\":\"描述\",\"dataIndex\":\"remark\",width:200 ,children:[] }"
				+ "]";
		List<?> list=this.findCallSql(sqlBuffer.toString());
		jsonBuffer.append("{\"success\":true,\"totalCount\":" + list.size() + ",\"columns\":"+columns+",\"totalRoot\":[");
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			jsonBuffer.append("{\"checked\":false,");
			jsonBuffer.append("\"id\":\""+obj[0]+"\",");
			jsonBuffer.append("\"groupName\":\""+obj[1]+"\",");
			jsonBuffer.append("\"groupCode\":\""+obj[2]+"\",");
			jsonBuffer.append("\"acqCycle\":\""+obj[3]+"\",");
			jsonBuffer.append("\"saveCycle\":\""+obj[4]+"\",");
			jsonBuffer.append("\"remark\":\""+obj[5]+"\"},");
		}
		if(jsonBuffer.toString().endsWith(",")){
			jsonBuffer.deleteCharAt(jsonBuffer.length() - 1);
		}
		jsonBuffer.append("]}");
//		try {
//			json=this.findPageBySqlEntity(sqlBuffer.toString(),columns , pager );
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return jsonBuffer.toString();
	}
	
	public String getDriverConfigData(){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		//驱动排序
		Map<Integer,Object> equipmentDriveSortMap=new TreeMap<Integer,Object>();
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			ModbusProtocolConfig.Protocol protocolConfig=(ModbusProtocolConfig.Protocol)modbusProtocolConfig.getProtocol().get(i);
			equipmentDriveSortMap.put(protocolConfig.getSort(), protocolConfig);
		}
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"协议名称\",\"dataIndex\":\"ProtocolName\",width:120 ,children:[] },"
				+ "{ \"header\":\"协议类型\",\"dataIndex\":\"ProtocolType\",width:50 ,children:[] },"
				+ "{ \"header\":\"注册包前缀(HEX)\",\"dataIndex\":\"SignInPrefix\",width:50 ,children:[] },"
				+ "{ \"header\":\"注册包后缀(HEX)\",\"dataIndex\":\"SignInSuffix\",width:50 ,children:[] },"
				+ "{ \"header\":\"心跳包前缀(HEX)\",\"dataIndex\":\"HeartbeatPrefix\",width:50 ,children:[] },"
				+ "{ \"header\":\"心跳包后缀(HEX)\",\"dataIndex\":\"HeartbeatSuffix\",width:50 ,children:[] }"
				+ "]";
		
		String diagramTableColumns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"item\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"数量\",\"dataIndex\":\"quantity\",width:80 ,children:[] },"
				+ "{ \"header\":\"存储数据类型\",\"dataIndex\":\"storeDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\"接口数据类型\",\"dataIndex\":\"IFDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\"读写类型\",\"dataIndex\":\"RWType\",width:80 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\"换算比例\",\"dataIndex\":\"ratio\",width:80 ,children:[] },"
				+ "{ \"header\":\"采集模式\",\"dataIndex\":\"acqMode\",width:80 ,children:[] }"
				+ "]";
		
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",\"diagramTableColumns\":"+diagramTableColumns+",");
		result_json.append("\"totalRoot\":[");
		int i=0;
		for(Entry<Integer, Object> entry:equipmentDriveSortMap.entrySet()){
			i++;
			ModbusProtocolConfig.Protocol protocolConfig=(ModbusProtocolConfig.Protocol)entry.getValue();
			if(!protocolConfig.getCode().toUpperCase().contains("KAFKA") && !protocolConfig.getCode().toUpperCase().contains("MQTT")){
				StringBuffer driverConfigData = new StringBuffer();
				driverConfigData.append("[");
				if("IntelligentPumpingUnitDrive".equalsIgnoreCase(protocolConfig.getCode())){
					
				}else{
					
				}
				for(int j=0;j<protocolConfig.getItems().size();j++){
					driverConfigData.append("{\"checked\":false,"
							+ "\"id\":"+(j+1)+","
							+ "\"item\":\""+protocolConfig.getItems().get(j).getName()+"\","
							+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+","
							+ "\"quantity\":"+protocolConfig.getItems().get(j).getQuantity()+","
							+ "\"storeDataType\":\""+protocolConfig.getItems().get(j).getStoreDataType()+"\","
							+ "\"IFDataType\":\""+protocolConfig.getItems().get(j).getIFDataType()+"\","
							+ "\"ratio\":"+protocolConfig.getItems().get(j).getRatio()+","
							+ "\"RWType\":\""+(protocolConfig.getItems().get(j).getRWType()?"只读":"读写")+"\","
							+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
							+ "\"acqMode\":\""+(protocolConfig.getItems().get(j).getAcqMode()?"主动上传":"被动响应")+"\"},");
				}
				if(driverConfigData.toString().endsWith(",")){
					driverConfigData.deleteCharAt(driverConfigData.length() - 1);
				}
				
				driverConfigData.append("]");
				result_json.append("{"
						+ "\"id\":"+i+","
						+ "\"ProtocolName\":\""+protocolConfig.getName()+"\","
						+ "\"ProtocolType\":\""+getProtocolType(protocolConfig.getType())+"\","
						+ "\"SignInPrefix\":\""+protocolConfig.getSignInPrefix()+"\","
						+ "\"SignInSuffix\":\""+protocolConfig.getSignInSuffix()+"\","
						+ "\"HeartbeatPrefix\":\""+protocolConfig.getHeartbeatPrefix()+"\","
						+ "\"HeartbeatSuffix\":\""+protocolConfig.getHeartbeatSuffix()+"\","
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
				+ "{ \"header\":\"名称\",\"dataIndex\":\"item\",width:120 ,children:[] },"
				+ "{ \"header\":\"变量\",\"dataIndex\":\"value\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		KafkaConfig driveConfig=(KafkaConfig)equipmentDriveMap.get("KafkaDrive");
		
		result_json.append("\"totalRoot\":[");
		if(driveConfig!=null){
			
			result_json.append("{\"id\":\"序号\",\"item\":\"名称\",\"value\":\"变量\"},");
			
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
	
	public String getKafkaConfigWellList(){
		StringBuffer result_json = new StringBuffer();
		DataSourceConfig dataSourceConfig=DataSourceConfig.getInstance();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"item\",width:120 ,children:[] },"
				+ "{ \"header\":\"变量\",\"dataIndex\":\"value\",width:80 ,children:[] }"
				+ "]";
		
		String diagramTableColumns = "["
				+ "{ \"header\":\"选择\",\"dataIndex\":\"checked\",width:50 ,children:[] },"
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"字段名称\",\"dataIndex\":\"item\",width:120 ,children:[] },"
				+ "{ \"header\":\"字段代码\",\"dataIndex\":\"columnName\",width:80 ,children:[] },"
				+ "{ \"header\":\"字段类型\",\"dataIndex\":\"columnType\",width:80 ,children:[] }"
				+ "]";
		
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",\"diagramTableColumns\":"+diagramTableColumns+",");
		result_json.append("\"totalRoot\":[");
		if(dataSourceConfig!=null){
			result_json.append("{\"id\":1,\"item\":\"IP\",\"value\":\""+dataSourceConfig.getIP()+"\"},");
			result_json.append("{\"id\":2,\"item\":\"Port\",\"value\":\""+dataSourceConfig.getPort()+"\"},");
			result_json.append("{\"id\":3,\"item\":\"数据库类型\",\"value\":\""+(dataSourceConfig.getType()==0?"oracle":"sql server")+"\"},");
			result_json.append("{\"id\":4,\"item\":\"数据库版本\",\"value\":\""+dataSourceConfig.getVersion()+"\"},");
			result_json.append("{\"id\":5,\"item\":\"数据库名称\",\"value\":\""+dataSourceConfig.getInstanceName()+"\"},");
			result_json.append("{\"id\":6,\"item\":\"用户名\",\"value\":\""+dataSourceConfig.getUser()+"\"},");
			result_json.append("{\"id\":7,\"item\":\"密码\",\"value\":\""+dataSourceConfig.getPassword()+"\"}");
		}
		result_json.append("],");
		result_json.append("\"columnRoot\":[");
		if(dataSourceConfig!=null){
			result_json.append("{\"checked\":true,\"id\":\"功图数据\",\"item\":\""+dataSourceConfig.getDiagramTable().getName()+"\",\"columnName\":\"\",\"columnType\":\"\"},");
			result_json.append("{\"checked\":true,\"id\":\"序号\",\"item\":\"字段名称\",\"columnName\":\"字段代码\",\"columnType\":\"字段类型\"},");
			
			result_json.append("{\"checked\":true,\"id\":1,\"item\":\"井名\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getWellName().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getWellName().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":2,\"item\":\"采集时间\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getAcqTime().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getAcqTime().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":3,\"item\":\"冲程\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getStroke().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getStroke().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":4,\"item\":\"冲次\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getSPM().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getSPM().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":5,\"item\":\"功图点数\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getPointCount().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getPointCount().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":6,\"item\":\"位移\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getS().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getS().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":7,\"item\":\"载荷\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getF().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getF().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":8,\"item\":\"电流\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getI().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getI().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":9,\"item\":\"功率\",\"columnName\":\""+dataSourceConfig.getDiagramTable().getColumns().getKWatt().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getDiagramTable().getColumns().getKWatt().getType()+"\"},");
			//油层数据表
			result_json.append("{\"checked\":true,\"id\":\"油层数据\",\"item\":\""+dataSourceConfig.getReservoirTable().getName()+"\",\"columnName\":\"\",\"columnType\":\"\"},");
			result_json.append("{\"checked\":true,\"id\":\"序号\",\"item\":\"字段名称\",\"columnName\":\"字段代码\",\"columnType\":\"字段类型\"},");
			result_json.append("{\"checked\":true,\"id\":1,\"item\":\"井名\",\"columnName\":\""+dataSourceConfig.getReservoirTable().getColumns().getWellName().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getReservoirTable().getColumns().getWellName().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":2,\"item\":\"油层中部深度\",\"columnName\":\""+dataSourceConfig.getReservoirTable().getColumns().getDepth().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getReservoirTable().getColumns().getDepth().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":3,\"item\":\"油层中部温度\",\"columnName\":\""+dataSourceConfig.getReservoirTable().getColumns().getTemperature().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getReservoirTable().getColumns().getTemperature().getType()+"\"},");
			//杆柱组合
			result_json.append("{\"checked\":true,\"id\":\"杆柱组合数据\",\"item\":\""+dataSourceConfig.getRodStringTable().getName()+"\",\"columnName\":\"\",\"columnType\":\"\"},");
			result_json.append("{\"checked\":true,\"id\":\"序号\",\"item\":\"字段名称\",\"columnName\":\"字段代码\",\"columnType\":\"字段类型\"},");
			result_json.append("{\"checked\":true,\"id\":1,\"item\":\"井名\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getWellName().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getWellName().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":2,\"item\":\"一级杆级别\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getGrade1().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getGrade1().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":3,\"item\":\"一级杆外径\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getOutsideDiameter1().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getOutsideDiameter1().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":4,\"item\":\"一级杆内径\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getInsideDiameter1().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getInsideDiameter1().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":5,\"item\":\"一级杆长度\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getLength1().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getLength1().getType()+"\"},");
			
			result_json.append("{\"checked\":true,\"id\":6,\"item\":\"二级杆级别\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getGrade2().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getGrade2().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":7,\"item\":\"二级杆外径\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getOutsideDiameter2().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getOutsideDiameter2().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":8,\"item\":\"二级杆内径\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getInsideDiameter2().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getInsideDiameter2().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":9,\"item\":\"二级杆长度\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getLength2().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getLength2().getType()+"\"},");
			
			result_json.append("{\"checked\":true,\"id\":10,\"item\":\"三级杆级别\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getGrade3().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getGrade3().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":11,\"item\":\"三级杆外径\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getOutsideDiameter3().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getOutsideDiameter3().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":12,\"item\":\"三级杆内径\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getInsideDiameter3().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getInsideDiameter3().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":13,\"item\":\"三级杆长度\",\"columnName\":\""+dataSourceConfig.getRodStringTable().getColumns().getLength3().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getRodStringTable().getColumns().getLength3().getType()+"\"},");
			//油管组合数据
			result_json.append("{\"checked\":true,\"id\":\"油管数据\",\"item\":\""+dataSourceConfig.getTubingStringTable().getName()+"\",\"columnName\":\"\",\"columnType\":\"\"},");
			result_json.append("{\"checked\":true,\"id\":\"序号\",\"item\":\"字段名称\",\"columnName\":\"字段代码\",\"columnType\":\"字段类型\"},");
			result_json.append("{\"checked\":true,\"id\":1,\"item\":\"井名\",\"columnName\":\""+dataSourceConfig.getTubingStringTable().getColumns().getWellName().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getTubingStringTable().getColumns().getWellName().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":2,\"item\":\"油管内径\",\"columnName\":\""+dataSourceConfig.getTubingStringTable().getColumns().getInsideDiameter().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getTubingStringTable().getColumns().getInsideDiameter().getType()+"\"},");
			//套管组合数据
			result_json.append("{\"checked\":true,\"id\":\"套管数据\",\"item\":\""+dataSourceConfig.getCasingStringTable().getName()+"\",\"columnName\":\"\",\"columnType\":\"\"},");
			result_json.append("{\"checked\":true,\"id\":\"序号\",\"item\":\"字段名称\",\"columnName\":\"字段代码\",\"columnType\":\"字段类型\"},");
			result_json.append("{\"checked\":true,\"id\":1,\"item\":\"井名\",\"columnName\":\""+dataSourceConfig.getCasingStringTable().getColumns().getWellName().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getCasingStringTable().getColumns().getWellName().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":2,\"item\":\"套管内径\",\"columnName\":\""+dataSourceConfig.getCasingStringTable().getColumns().getInsideDiameter().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getCasingStringTable().getColumns().getInsideDiameter().getType()+"\"},");
			//泵数据
			result_json.append("{\"checked\":true,\"id\":\"泵数据\",\"item\":\""+dataSourceConfig.getPumpTable().getName()+"\",\"columnName\":\"\",\"columnType\":\"\"},");
			result_json.append("{\"checked\":true,\"id\":\"序号\",\"item\":\"字段名称\",\"columnName\":\"字段代码\",\"columnType\":\"字段类型\"},");
			result_json.append("{\"checked\":true,\"id\":1,\"item\":\"井名\",\"columnName\":\""+dataSourceConfig.getPumpTable().getColumns().getWellName().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getPumpTable().getColumns().getWellName().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":2,\"item\":\"泵级别\",\"columnName\":\""+dataSourceConfig.getPumpTable().getColumns().getPumpGrade().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getPumpTable().getColumns().getPumpGrade().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":3,\"item\":\"泵径\",\"columnName\":\""+dataSourceConfig.getPumpTable().getColumns().getPumpBoreDiameter().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getPumpTable().getColumns().getPumpBoreDiameter().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":4,\"item\":\"柱塞长\",\"columnName\":\""+dataSourceConfig.getPumpTable().getColumns().getPlungerLength().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getPumpTable().getColumns().getPlungerLength().getType()+"\"},");
			//动态数据
			result_json.append("{\"checked\":true,\"id\":\"动态数据\",\"item\":\""+dataSourceConfig.getProductionTable().getName()+"\",\"columnName\":\"\",\"columnType\":\"\"},");
			result_json.append("{\"checked\":true,\"id\":\"序号\",\"item\":\"字段名称\",\"columnName\":\"字段代码\",\"columnType\":\"字段类型\"},");
			result_json.append("{\"checked\":true,\"id\":1,\"item\":\"井名\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getWellName().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getWellName().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":2,\"item\":\"含水率\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getWaterCut().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getWaterCut().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":3,\"item\":\"生产汽油比\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getProductionGasOilRatio().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getProductionGasOilRatio().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":4,\"item\":\"油压\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getTubingPressure().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getTubingPressure().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":5,\"item\":\"套压\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getCasingPressure().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getCasingPressure().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":6,\"item\":\"井口流温\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getWellHeadFluidTemperature().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getWellHeadFluidTemperature().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":7,\"item\":\"动液面\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getProducingfluidLevel().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getProducingfluidLevel().getType()+"\"},");
			result_json.append("{\"checked\":true,\"id\":8,\"item\":\"泵挂\",\"columnName\":\""+dataSourceConfig.getProductionTable().getColumns().getPumpSettingDepth().getColumn()+"\",\"columnType\":\""+dataSourceConfig.getProductionTable().getColumns().getPumpSettingDepth().getType()+"\"}");
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString();
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
	
	public List<T> showAcquisitionGroupOwnItems(Class<AcquisitionGroupItem> class1, String groupCode) {
		String queryString = "select u FROM AcquisitionGroupItem u,AcquisitionGroup u2 where u.groupId= u2.id and  u2.groupCode='" + groupCode + "' order by u.id asc";
		return getBaseDao().find(queryString);
	}
	
	public List<T> showAcquisitionUnitOwnGroups(Class<AcquisitionUnitGroup> class1, String unitId) {
		String queryString = "select u FROM AcquisitionGroup u, AcquisitionUnitGroup u2 "
				+ "where u.id=u2.groupId and u2.unitId=" + unitId + " order by u.id asc";
		return getBaseDao().find(queryString);
	}
	
	public void deleteCurrentAcquisitionGroupOwnItems(final String groupId) throws Exception {
		final String hql = "DELETE AcquisitionGroupItem u where u.groupId ="+groupId+"";
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
	
	
	
	public static String getDataItemsType(String type){
		String dataType="";
		if(type.equalsIgnoreCase("int")){
			dataType="有符号整型";
		}else if(type.equalsIgnoreCase("uint")){
			dataType="无符号整型";
		}else if(type.equalsIgnoreCase("float")){
			dataType="实型";
		}else if(type.equalsIgnoreCase("bcd")){
			dataType="BCD码";
		}else if(type.equalsIgnoreCase("asc")){
			dataType="ASCII";
		}
		return dataType;
	}
	
	public static String getProtocolType(int type){
		String protocolType="";
		if(type==0){
			protocolType="modbus-tcp";
		}else if(type==1){
			protocolType="modbus-rtu";
		}else if(type==2){
			protocolType="modbus-rtu拓展(主动上传)";
		}
		return protocolType;
	}
}
