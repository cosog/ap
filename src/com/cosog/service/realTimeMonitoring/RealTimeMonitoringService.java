package com.cosog.service.realTimeMonitoring;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AlarmShowStyle;
import com.cosog.model.CommStatus;
import com.cosog.model.User;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.drive.KafkaConfig;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.gridmodel.WellGridPanelData;
import com.cosog.model.gridmodel.WellHandsontableChangedData;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.utils.AcquisitionItemColumnsMap;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.Page;
import com.cosog.utils.ProtocolItemResolutionData;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

@Service("realTimeMonitoringService")
public class RealTimeMonitoringService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	
	public String getDeviceRealTimeStat(String orgId,String deviceType) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		AlarmShowStyle alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		if(alarmShowStyle==null){
			EquipmentDriverServerTask.initAlarmStyle();
			alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		}
		String tableName="tbl_pumpacqdata_latest";
		String deviceTableName="tbl_pumpdevice";
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pipelineacqdata_latest";
			deviceTableName="tbl_pipelinedevice";
		}
		
		String sql="select t.commstatus,count(1) from "+tableName+" t,"+deviceTableName+" t2 "
				+ " where t.wellid=t2.id and t2.orgid in("+orgId+") "
				+"  group by t.commstatus";
		
		
		List<?> list = this.findCallSql(sql);
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"item\",children:[] },"
				+ "{ \"header\":\"变量\",\"dataIndex\":\"count\",children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":3,");
		
		int total=0,online=0,offline=0;
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			if(StringManagerUtils.stringToInteger(obj[0]+"")==1){
				online=StringManagerUtils.stringToInteger(obj[1]+"");
			}else{
				offline=StringManagerUtils.stringToInteger(obj[1]+"");
			}
		}
		total=online+offline;
		result_json.append("{\"id\":1,");
		result_json.append("\"item\":\"全部\",");
		result_json.append("\"itemCode\":\"all\",");
		result_json.append("\"count\":"+total+"},");
		
		result_json.append("{\"id\":2,");
		result_json.append("\"item\":\"在线\",");
		result_json.append("\"itemCode\":\"online\",");
		result_json.append("\"count\":"+online+"},");
		
		result_json.append("{\"id\":3,");
		result_json.append("\"item\":\"离线\",");
		result_json.append("\"itemCode\":\"offline\",");
		result_json.append("\"count\":"+offline+"}");
		result_json.append("]");
		result_json.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle));
		result_json.append("}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getRealTimeMonitoringCommStatusStatData(String orgId,String deviceType,String deviceTypeStatValue) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		AlarmShowStyle alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		if(alarmShowStyle==null){
			EquipmentDriverServerTask.initAlarmStyle();
			alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		}
		String tableName="tbl_pumpacqdata_latest";
		String deviceTableName="viw_pumpdevice";
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pipelineacqdata_latest";
			deviceTableName="viw_pipelinedevice";
		}
		
		String sql="select t2.commstatus,count(1) from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on  t2.wellid=t.id "
				+ " where t.orgid in("+orgId+") ";
		if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
			sql+=" and t.devicetypename='"+deviceTypeStatValue+"'";
		}
		sql+=" group by t2.commstatus";
		
		List<?> list = this.findCallSql(sql);
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"item\",children:[] },"
				+ "{ \"header\":\"变量\",\"dataIndex\":\"count\",children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":3,");
		
		int total=0,online=0,offline=0;
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			if(StringManagerUtils.stringToInteger(obj[0]+"")==1){
				online=StringManagerUtils.stringToInteger(obj[1]+"");
			}else{
				offline=StringManagerUtils.stringToInteger(obj[1]+"");
			}
		}
		total=online+offline;
		result_json.append("{\"id\":1,");
		result_json.append("\"item\":\"全部\",");
		result_json.append("\"itemCode\":\"all\",");
		result_json.append("\"count\":"+total+"},");
		
		result_json.append("{\"id\":2,");
		result_json.append("\"item\":\"在线\",");
		result_json.append("\"itemCode\":\"online\",");
		result_json.append("\"count\":"+online+"},");
		
		result_json.append("{\"id\":3,");
		result_json.append("\"item\":\"离线\",");
		result_json.append("\"itemCode\":\"offline\",");
		result_json.append("\"count\":"+offline+"}");
		result_json.append("]");
		result_json.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle));
		result_json.append("}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getRealTimeMonitoringDeviceTypeStatData(String orgId,String deviceType,String commStatusStatValue) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		AlarmShowStyle alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		if(alarmShowStyle==null){
			EquipmentDriverServerTask.initAlarmStyle();
			alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		}
		String tableName="tbl_pumpacqdata_latest";
		String deviceTableName="viw_pumpdevice";
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pipelineacqdata_latest";
			deviceTableName="viw_pipelinedevice";
		}
		
		String sql="select t.devicetypename,t.devicetype,count(1) from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on t.id=t2.wellid "
				+ " where t.orgid in("+orgId+") ";
		
		
		
		if(StringManagerUtils.isNotNull(commStatusStatValue)){
			sql+=" and decode(t2.commstatus,1,'在线','离线')='"+commStatusStatValue+"'";
		}
		sql+=" group by t.devicetypename,t.devicetype";
		sql+=" order by t.devicetype";
		
		List<?> list = this.findCallSql(sql);
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"item\",children:[] },"
				+ "{ \"header\":\"变量\",\"dataIndex\":\"count\",children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+list.size()+",");
		
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+(i+1)+",");
			result_json.append("\"item\":\""+obj[0]+"\",");
			result_json.append("\"itemCode\":\""+obj[1]+"\",");
			result_json.append("\"count\":"+obj[2]+"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle));
		result_json.append("}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getDeviceRealTimeCommStatusStat(String orgId,String deviceType) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		int deviceTypeLower=100;
		int deviceTypeUpper=200;
		if(StringManagerUtils.stringToInteger(deviceType) ==1){
			deviceTypeLower=200;
			deviceTypeUpper=300;
		}
		
		
		List<CommStatus> commStatusList=(List<CommStatus>) dataModelMap.get("DeviceCommStatus");
		if(commStatusList==null){
			EquipmentDriverServerTask.LoadDeviceCommStatus();
			commStatusList=(List<CommStatus>) dataModelMap.get("DeviceCommStatus");
		}
		
		result_json.append("{ \"success\":true,\"orgId\":\""+orgId+"\",\"deviceType\":"+deviceType+",");
		int all=0,online=0,offline=0;
		for(int i=0;i<commStatusList.size();i++){
			CommStatus commStatus=commStatusList.get(i);
			if(commStatus.getDeviceType()>=deviceTypeLower&&commStatus.getDeviceType()<deviceTypeUpper
					&& StringManagerUtils.existOrNot(orgId.split(","), commStatus.getOrgId()+"")){
				if(commStatus.getCommStatus()==1){
					online+=1;
				}else{
					offline+=1;;
				}
			}
		}
		all=online+offline;
		result_json.append("\"all\":"+all+",");
		result_json.append("\"online\":"+online+",");
		result_json.append("\"offline\":"+offline);
		result_json.append("}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getDeviceRealTimeOverview(String orgId,String deviceName,String deviceType,String commStatusStatValue,String deviceTypeStatValue,Page pager) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int dataSaveMode=Config.getInstance().configFile.getOthers().getDataSaveMode();
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		AlarmShowStyle alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		if(alarmShowStyle==null){
			EquipmentDriverServerTask.initAlarmStyle();
			alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		}
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		
		String tableName="tbl_pumpacqdata_latest";
		String deviceTableName="tbl_pumpdevice";
		String ddicName="pumpRealTimeOverview";
		String columnsKey="pumpDeviceAcquisitionItemColumns";
		DataDictionary ddic = null;
		List<String> ddicColumnsList=new ArrayList<String>();
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pipelineacqdata_latest";
			deviceTableName="tbl_pipelinedevice";
			ddicName="pipelineRealTimeOverview";
			columnsKey="pipelineDeviceAcquisitionItemColumns";
		}
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
			EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
		}
		Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		String columns = ddic.getTableHeader();
		
		String sql="select t.id,t.wellname,t2.commstatus,"
				+ "decode(t2.commstatus,1,'在线','离线') as commStatusName,"
				+ "decode(t5.alarmsign,0,0,null,0,t5.alarmlevel) as commAlarmLevel,"
				+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'),c1.itemname as devicetypename ";
		
		String[] ddicColumns=ddic.getSql().split(",");
		for(int i=0;i<ddicColumns.length;i++){
			if(dataSaveMode==0){
				if(StringManagerUtils.existOrNot(loadedAcquisitionItemColumnsMap, ddicColumns[i],false)){
					ddicColumnsList.add(ddicColumns[i]);
				}
			}else{
				if(StringManagerUtils.existOrNotByValue(loadedAcquisitionItemColumnsMap, ddicColumns[i],false)){
					ddicColumnsList.add(ddicColumns[i]);
				}
			}
		}
		for(int i=0;i<ddicColumnsList.size();i++){
//			boolean isMatch=false;
//			String itemStr="";
//			if(protocol!=null){
//				for(int j=0;j<protocol.getItems().size();j++){
//					String col=dataSaveMode==0?("addr"+protocol.getItems().get(j).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(j).getTitle()));
//					if(ddicColumnsList.get(i).equalsIgnoreCase(col)){
//						if(protocol.getItems().get(j).getMeaning()!=null && protocol.getItems().get(j).getMeaning().size()>0){
//							isMatch=true;
//							itemStr=",decode(t2."+ddicColumnsList.get(i);
//							for(int k=0;k<protocol.getItems().get(j).getMeaning().size();k++){
//								itemStr+=","+protocol.getItems().get(j).getMeaning().get(k).getValue()+",'"+protocol.getItems().get(j).getMeaning().get(k).getMeaning()+"'";
//							}
//							
//							itemStr+=",t2."+ddicColumnsList.get(i)+") as "+ddicColumnsList.get(i);
//						}
//						break;
//					}
//				}
//			}
//			if(isMatch){
//				sql+=itemStr;
//			}else{
//				sql+=",t2."+ddicColumnsList.get(i);
//			}
			sql+=",t2."+ddicColumnsList.get(i);
		}
		sql+= " from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
				+ " left outer join tbl_protocolalarminstance t3 on t.alarminstancecode=t3.code"
				+ " left outer join tbl_alarm_unit_conf t4 on t3.alarmunitid=t4.id"
				+ " left outer join tbl_alarm_item2unit_conf t5 on t4.id=t5.unitid and t5.type=3  and  decode(t2.commstatus,1,'在线','离线')=t5.itemname"
				+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue "
				+ " where  t.orgid in ("+orgId+") ";
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.wellName='"+deviceName+"'";
		}
		if(StringManagerUtils.isNotNull(commStatusStatValue)){
			sql+=" and decode(t2.commstatus,1,'在线','离线')='"+commStatusStatValue+"'";
		}
		if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
			sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
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
			StringBuffer alarmInfo = new StringBuffer();
			String protocolSql="select t3.protocol from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 "
					+ " where t.instancecode=t2.code and t2.unitid=t3.id and t.id="+obj[0];
			String alarmItemsSql="select t2.itemname,t2.itemcode,t2.itemaddr,t2.type,t2.bitindex,t2.value, "
					+ " t2.upperlimit,t2.lowerlimit,t2.hystersis,t2.delay,decode(t2.alarmsign,0,0,t2.alarmlevel) as alarmlevel "
					+ " from "+deviceTableName+" t, tbl_alarm_item2unit_conf t2,tbl_alarm_unit_conf t3,tbl_protocolalarminstance t4 "
					+ " where t.alarminstancecode=t4.code and t4.alarmunitid=t3.id and t3.id=t2.unitid "
					+ " and t.id="+obj[0]
					+ " order by t2.id";
			List<?> protocolList = this.findCallSql(protocolSql);
			List<?> alarmItemsList = this.findCallSql(alarmItemsSql);
			ModbusProtocolConfig.Protocol protocol=null;
			if(protocolList.size()>0){
				String protocolName=protocolList.get(0).toString();
				for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
					if(modbusProtocolConfig.getProtocol().get(j).getDeviceType()==StringManagerUtils.stringToInteger(deviceType) 
							&& protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
						protocol=modbusProtocolConfig.getProtocol().get(j);
						break;
					}
				}
			}
			
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"commStatus\":"+obj[2]+",");
			result_json.append("\"commStatusName\":\""+obj[3]+"\",");
			result_json.append("\"commAlarmLevel\":"+obj[4]+",");
			result_json.append("\"acqTime\":\""+obj[5]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[6]+"\",");
			alarmInfo.append("[");
			for(int j=0;j<ddicColumnsList.size();j++){
				String rawValue=obj[7+j]+"";
				String value=obj[7+j]+"";
				ModbusProtocolConfig.Items item=null;
				if(protocol!=null){
					for(int k=0;k<protocol.getItems().size();k++){
						String col=dataSaveMode==0?("addr"+protocol.getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(k).getTitle()));
						if(col.equalsIgnoreCase(ddicColumnsList.get(j))){
							item=protocol.getItems().get(k);
							if(protocol.getItems().get(k).getMeaning()!=null && protocol.getItems().get(k).getMeaning().size()>0){
								for(int l=0;l<protocol.getItems().get(k).getMeaning().size();l++){
									if(value.equals(protocol.getItems().get(k).getMeaning().get(l).getValue()+"")||StringManagerUtils.stringToFloat(value)==protocol.getItems().get(k).getMeaning().get(l).getValue()){
										value=protocol.getItems().get(k).getMeaning().get(l).getMeaning();
										break;
									}
								}
							}
							break;
						}
					}
				}
				//判断报警
				if(item!=null){
					for(int k=0;k<alarmItemsList.size();k++){
						Object[] alarmItemObj=(Object[]) alarmItemsList.get(k);
						if(item.getTitle().equalsIgnoreCase(alarmItemObj[0]+"") && item.getAddr()==StringManagerUtils.stringToInteger(alarmItemObj[2]+"")){
							int alarmType=StringManagerUtils.stringToInteger(alarmItemObj[3]+"");
							if(alarmType==2){//数据量报警
								alarmInfo.append("{\"item\":\""+ddicColumnsList.get(j).replaceAll(" ", "")+"\","
										+ "\"itemName\":\""+alarmItemObj[0]+"\","
										+ "\"itemAddr\":\""+alarmItemObj[3]+"\","
										+ "\"alarmType\":\""+alarmType+"\","
										+ "\"upperLimit\":\""+StringManagerUtils.stringToFloat(alarmItemObj[6]+"")+"\","
										+ "\"lowerLimit\":\""+StringManagerUtils.stringToFloat(alarmItemObj[7]+"")+"\","
										+ "\"hystersis\":\""+StringManagerUtils.stringToFloat(alarmItemObj[8]+"")+"\","
										+" \"alarmLevel\":"+StringManagerUtils.stringToInteger(alarmItemObj[10]+"")+"},");
								break;
							}else if(alarmType==1){//枚举量报警
								String alarmValueMeaning="";
								if(item.getMeaning()!=null && item.getMeaning().size()>0){
									for(int l=0;l<item.getMeaning().size();l++){
										if(StringManagerUtils.stringToInteger(alarmItemObj[5]+"")==item.getMeaning().get(l).getValue()){
											alarmValueMeaning=item.getMeaning().get(l).getMeaning();
											break;
										}
									}
								}
									
								alarmInfo.append("{\"item\":\""+ddicColumnsList.get(j).replaceAll(" ", "")+"\","
										+ "\"itemName\":\""+alarmItemObj[0]+"\","
										+ "\"itemAddr\":\""+alarmItemObj[3]+"\","
										+ "\"alarmType\":\""+alarmType+"\","
										+ "\"alarmValue\":\""+StringManagerUtils.stringToInteger(alarmItemObj[5]+"")+"\","
										+ "\"alarmValueMeaning\":\""+alarmValueMeaning+"\","
										+ "\"alarmLevel\":"+StringManagerUtils.stringToInteger(alarmItemObj[10]+"")+"},");
							}else if(alarmType==0){//开关量报警
//								if(StringManagerUtils.isNotNull(bitIndex)){
//									if(bitIndex.equals(alarmItemObj[4]+"") && StringManagerUtils.stringToInteger(rawValue)==StringManagerUtils.stringToInteger(alarmItemObj[5]+"")){
//										alarmLevel=StringManagerUtils.stringToInteger(alarmItemObj[10]+"");
//									}
//								}
							}
							
						}
					}
				}
				result_json.append("\""+ddicColumnsList.get(j).replaceAll(" ", "")+"\":\""+value+"\",");
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			if(alarmInfo.toString().endsWith(",")){
				alarmInfo.deleteCharAt(alarmInfo.length() - 1);
			}
			alarmInfo.append("]");
			result_json.append(",\"alarmInfo\":"+alarmInfo+"");
			result_json.append("},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle)+"}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getDeviceRealTimeOverviewExportData(String orgId,String deviceName,String deviceType,String commStatusStatValue,String deviceTypeStatValue) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		int dataSaveMode=Config.getInstance().configFile.getOthers().getDataSaveMode();
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		
		String tableName="tbl_pumpacqdata_latest";
		String deviceTableName="tbl_pumpdevice";
		String ddicName="pumpRealTimeOverview";
		String columnsKey="pumpDeviceAcquisitionItemColumns";
		DataDictionary ddic = null;
		List<String> ddicColumnsList=new ArrayList<String>();
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pipelineacqdata_latest";
			deviceTableName="tbl_pipelinedevice";
			ddicName="pipelineRealTimeOverview";
			columnsKey="pipelineDeviceAcquisitionItemColumns";
		}
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
			EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
		}
		Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		
		String sql="select t.id,t.wellname,t2.commstatus,"
				+ "decode(t2.commstatus,1,'在线','离线') as commStatusName,"
				+ "decode(t5.alarmsign,0,0,null,0,t5.alarmlevel) as commAlarmLevel,"
				+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'),c1.itemname as devicetypename ";
		
		String[] ddicColumns=ddic.getSql().split(",");
		for(int i=0;i<ddicColumns.length;i++){
			if(dataSaveMode==0){
				if(StringManagerUtils.existOrNot(loadedAcquisitionItemColumnsMap, ddicColumns[i],false)){
					ddicColumnsList.add(ddicColumns[i]);
				}
			}else{
				if(StringManagerUtils.existOrNotByValue(loadedAcquisitionItemColumnsMap, ddicColumns[i],false)){
					ddicColumnsList.add(ddicColumns[i]);
				}
			}
		}
		for(int i=0;i<ddicColumnsList.size();i++){
			sql+=",t2."+ddicColumnsList.get(i);
		}
		sql+= " from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
				+ " left outer join tbl_protocolalarminstance t3 on t.alarminstancecode=t3.code"
				+ " left outer join tbl_alarm_unit_conf t4 on t3.alarmunitid=t4.id"
				+ " left outer join tbl_alarm_item2unit_conf t5 on t4.id=t5.unitid and t5.type=3  and  decode(t2.commstatus,1,'在线','离线')=t5.itemname"
				+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue "
				+ " where  t.orgid in ("+orgId+") ";
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t.wellName='"+deviceName+"'";
		}
		if(StringManagerUtils.isNotNull(commStatusStatValue)){
			sql+=" and decode(t2.commstatus,1,'在线','离线')='"+commStatusStatValue+"'";
		}
		if(StringManagerUtils.isNotNull(deviceTypeStatValue)){
			sql+=" and c1.itemname='"+deviceTypeStatValue+"'";
		}
		sql+=" order by t.sortnum,t.wellname";
		
		List<?> list = this.findCallSql(sql);
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String protocolSql="select t3.protocol from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 "
					+ " where t.instancecode=t2.code and t2.unitid=t3.id and t.id="+obj[0];
			List<?> protocolList = this.findCallSql(protocolSql);
			ModbusProtocolConfig.Protocol protocol=null;
			if(protocolList.size()>0){
				String protocolName=protocolList.get(0).toString();
				for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
					if(modbusProtocolConfig.getProtocol().get(j).getDeviceType()==StringManagerUtils.stringToInteger(deviceType) 
							&& protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
						protocol=modbusProtocolConfig.getProtocol().get(j);
						break;
					}
				}
			}
			
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"commStatus\":"+obj[2]+",");
			result_json.append("\"commStatusName\":\""+obj[3]+"\",");
			result_json.append("\"commAlarmLevel\":"+obj[4]+",");
			result_json.append("\"acqTime\":\""+obj[5]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[6]+"\",");
			for(int j=0;j<ddicColumnsList.size();j++){
				String value=obj[7+j]+"";
				if(protocol!=null){
					for(int k=0;k<protocol.getItems().size();k++){
						String col=dataSaveMode==0?("addr"+protocol.getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(k).getTitle()));
						if(col.equalsIgnoreCase(ddicColumnsList.get(j))){
							if(protocol.getItems().get(k).getMeaning()!=null && protocol.getItems().get(k).getMeaning().size()>0){
								for(int l=0;l<protocol.getItems().get(k).getMeaning().size();l++){
									if(value.equals(protocol.getItems().get(k).getMeaning().get(l).getValue()+"")||StringManagerUtils.stringToFloat(value)==protocol.getItems().get(k).getMeaning().get(l).getValue()){
										value=protocol.getItems().get(k).getMeaning().get(l).getMeaning();
										break;
									}
								}
							}
							break;
						}
					}
				}
				result_json.append("\""+ddicColumnsList.get(j).replaceAll(" ", "")+"\":\""+value+"\",");
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}

	public String getDeviceRealTimeMonitoringData(String deviceId,String deviceName,String deviceType,String userAccount) throws IOException, SQLException{
		int items=3;
		StringBuffer result_json = new StringBuffer();
		StringBuffer info_json = new StringBuffer();
		int dataSaveMode=Config.getInstance().configFile.getOthers().getDataSaveMode();
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		AlarmShowStyle alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		if(alarmShowStyle==null){
			EquipmentDriverServerTask.initAlarmStyle();
			alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		}
		String tableName="tbl_pumpacqdata_latest";
		String deviceTableName="tbl_pumpdevice";
		String columnsKey="pumpDeviceAcquisitionItemColumns";
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pipelineacqdata_latest";
			deviceTableName="tbl_pipelinedevice";
			columnsKey="pipelineDeviceAcquisitionItemColumns";
		}
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
			EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
		}
		Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
		
		String itemsSql="select t.wellname,t3.protocol, "
				+ " listagg(t6.itemname, ',') within group(order by t6.groupid,t6.id ) key,"
				+ " listagg(decode(t6.sort,null,9999,t6.sort), ',') within group(order by t6.groupid,t6.id ) sort,"
				+ " listagg(decode(t6.bitindex,null,9999,t6.bitindex), ',') within group(order by t6.groupid,t6.id ) bitindex  "
				+ " from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3,tbl_acq_group2unit_conf t4,tbl_acq_group_conf t5,tbl_acq_item2group_conf t6 "
				+ " where t.instancecode=t2.code and t2.unitid=t3.id and t3.id=t4.unitid and t4.groupid=t5.id and t5.id=t6.groupid "
				+ " and t.id="+deviceId
				+ " and decode(t6.showlevel,null,9999,t6.showlevel)>=( select r.showlevel from tbl_role r,tbl_user u where u.user_type=r.role_id and u.user_id='"+userAccount+"' )"
				+ " group by t.wellname,t3.protocol";
		String alarmItemsSql="select t2.itemname,t2.itemcode,t2.itemaddr,t2.type,t2.bitindex,t2.value, "
				+ " t2.upperlimit,t2.lowerlimit,t2.hystersis,t2.delay,decode(t2.alarmsign,0,0,t2.alarmlevel) as alarmlevel "
				+ " from "+deviceTableName+" t, tbl_alarm_item2unit_conf t2,tbl_alarm_unit_conf t3,tbl_protocolalarminstance t4 "
				+ " where t.alarminstancecode=t4.code and t4.alarmunitid=t3.id and t3.id=t2.unitid "
				+ " and t.id="+deviceId
				+ " order by t2.id";
		List<?> itemsList = this.findCallSql(itemsSql);
		List<?> alarmItemsList = this.findCallSql(alarmItemsSql);
		String columns = "[";
		for(int i=1;i<=items;i++){
			columns+= "{ \"header\":\"名称\",\"dataIndex\":\"name"+i+"\",children:[] },"
					+ "{ \"header\":\"变量\",\"dataIndex\":\"value"+i+"\",children:[] }";
			if(i<items){
				columns+=",";
			}
		}
		columns+= "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		info_json.append("[");
		for(int i=0;i<itemsList.size();i++){
			Object[] itemsObj=(Object[]) itemsList.get(i);
			String protocolCode=itemsObj[1]+"";
			Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
			if(equipmentDriveMap.size()==0){
				EquipmentDriverServerTask.loadProtocolConfig();
				equipmentDriveMap = EquipmentDriveMap.getMapObject();
			}
			ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
			
			ModbusProtocolConfig.Protocol protocol=null;
			for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
				if(protocolCode.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
					protocol=modbusProtocolConfig.getProtocol().get(j);
					break;
				}
			}
			
			if(protocol!=null && StringManagerUtils.isNotNull(itemsObj[2]+"")){
				String[] itemsArr=(itemsObj[2]+"").split(",");
				String[] itemsSortArr=(itemsObj[3]+"").split(",");
				String[] itemsBitIndexArr=(itemsObj[4]+"").split(",");
				List<ModbusProtocolConfig.Items> protocolItems=new ArrayList<ModbusProtocolConfig.Items>();
				List<ProtocolItemResolutionData> protocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
				for(int j=0;j<protocol.getItems().size();j++){
					if((!"w".equalsIgnoreCase(protocol.getItems().get(j).getRWType())) 
							&& (StringManagerUtils.existOrNot(itemsArr, protocol.getItems().get(j).getTitle(), false))){
						for(int k=0;k<itemsArr.length;k++){
							if(protocol.getItems().get(j).getTitle().equalsIgnoreCase(itemsArr[k])){
								protocolItems.add(protocol.getItems().get(j));
								break;
							}
						}
					}
				}
				
				String sql="select t.id,t.wellname,to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'), t2.commstatus,decode(t2.commstatus,1,'在线','离线') as commStatusName,decode(t2.commstatus,1,0,100) as commAlarmLevel ";
				for(int j=0;j<protocolItems.size();j++){
					String col=dataSaveMode==0?("addr"+protocolItems.get(j).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocolItems.get(j).getTitle()));
					sql+=",t2."+col;
				}
				sql+= " from "+deviceTableName+" t "
						+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
						+ " where  t.id="+deviceId;
				List<?> list = this.findCallSql(sql);
				if(list.size()>0){
					int row=1;
					Object[] obj=(Object[]) list.get(0);
					for(int j=0;j<protocolItems.size();j++){
						String columnName=protocolItems.get(j).getTitle();
						String rawColumnName=columnName;
						String value=obj[j+6]+"";
						String rawValue=value;
						String addr=protocolItems.get(j).getAddr()+"";
						String column=dataSaveMode==0?("addr"+protocolItems.get(j).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocolItems.get(j).getTitle()));
						String columnDataType=protocolItems.get(j).getIFDataType();
						String resolutionMode=protocolItems.get(j).getResolutionMode()+"";
						String bitIndex="";
						String unit=protocolItems.get(j).getUnit();
						int sort=9999;
						
						if(protocolItems.get(j).getResolutionMode()==1||protocolItems.get(j).getResolutionMode()==2){//如果是枚举量
							for(int l=0;l<itemsArr.length;l++){
								if(itemsArr[l].equalsIgnoreCase(protocolItems.get(j).getTitle())){
									sort=StringManagerUtils.stringToInteger(itemsSortArr[l]);
									break;
								}
							}
							
							if(StringManagerUtils.isNotNull(value)&&protocolItems.get(j).getMeaning()!=null&&protocolItems.get(j).getMeaning().size()>0){
								for(int l=0;l<protocolItems.get(j).getMeaning().size();l++){
									if(StringManagerUtils.stringToFloat(value)==(protocolItems.get(j).getMeaning().get(l).getValue())){
										value=protocolItems.get(j).getMeaning().get(l).getMeaning();
										break;
									}
								}
							}
							ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort);
							protocolItemResolutionDataList.add(protocolItemResolutionData);
						}else if(protocolItems.get(j).getResolutionMode()==0){//如果是开关量
							boolean isMatch=false;
							if(protocolItems.get(j).getMeaning()!=null&&protocolItems.get(j).getMeaning().size()>0){
								String[] valueArr=value.split(",");
								for(int l=0;l<protocolItems.get(j).getMeaning().size();l++){
									isMatch=false;
									columnName=protocolItems.get(j).getMeaning().get(l).getMeaning();
									sort=9999;
									
									for(int n=0;n<itemsArr.length;n++){
										if(itemsArr[n].equalsIgnoreCase(protocolItems.get(j).getTitle()) 
												&&StringManagerUtils.stringToInteger(itemsBitIndexArr[n])==protocolItems.get(j).getMeaning().get(l).getValue()
												){
											sort=StringManagerUtils.stringToInteger(itemsSortArr[n]);
											isMatch=true;
											break;
										}
									}
									if(!isMatch){
										continue;
									}
									if(StringManagerUtils.isNotNull(value)){
										boolean match=false;
										for(int m=0;valueArr!=null&&m<valueArr.length;m++){
											if(m==protocolItems.get(j).getMeaning().get(l).getValue()){
												bitIndex=m+"";
												if("bool".equalsIgnoreCase(columnDataType) || "boolean".equalsIgnoreCase(columnDataType)){
													value=("true".equalsIgnoreCase(valueArr[m]) || "1".equalsIgnoreCase(valueArr[m]))?"开":"关";
													rawValue=("true".equalsIgnoreCase(valueArr[m]) || "1".equalsIgnoreCase(valueArr[m]))?"1":"0";
												}else{
													value=valueArr[m];
												}
												ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort);
												protocolItemResolutionDataList.add(protocolItemResolutionData);
												match=true;
												break;
											}
										}
										if(!match){
											value="";
											rawValue="";
											bitIndex=protocolItems.get(j).getMeaning().get(l).getValue()+"";
											ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,protocolItems.get(j).getMeaning().get(l).getValue()+"",unit,sort);
											protocolItemResolutionDataList.add(protocolItemResolutionData);
										}
									}else{
										for(int m=0;m<itemsArr.length;m++){
											if(itemsArr[m].equalsIgnoreCase(protocolItems.get(j).getTitle()) && itemsBitIndexArr[m].equalsIgnoreCase(protocolItems.get(j).getMeaning().get(l).getValue()+"") ){
												sort=StringManagerUtils.stringToInteger(itemsSortArr[m]);
												break;
											}
										}
										value="";
										rawValue="";
										bitIndex=protocolItems.get(j).getMeaning().get(l).getValue()+"";
										ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,protocolItems.get(j).getMeaning().get(l).getValue()+"",unit,sort);
										protocolItemResolutionDataList.add(protocolItemResolutionData);
									}
								}
							}else{
								for(int l=0;l<itemsArr.length;l++){
									if(itemsArr[l].equalsIgnoreCase(protocolItems.get(j).getTitle())){
										sort=StringManagerUtils.stringToInteger(itemsSortArr[l]);
										break;
									}
								}
								ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort);
								protocolItemResolutionDataList.add(protocolItemResolutionData);
							}
						}else{//如果是数据量
							for(int l=0;l<itemsArr.length;l++){
								if(itemsArr[l].equalsIgnoreCase(protocolItems.get(j).getTitle())){
									sort=StringManagerUtils.stringToInteger(itemsSortArr[l]);
									break;
								}
							}
							
							ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData(rawColumnName,columnName,value,rawValue,addr,column,columnDataType,resolutionMode,bitIndex,unit,sort);
							protocolItemResolutionDataList.add(protocolItemResolutionData);
						} 
					}
					
					//排序
					Collections.sort(protocolItemResolutionDataList);
					//插入排序间隔的空项
					List<ProtocolItemResolutionData> finalProtocolItemResolutionDataList=new ArrayList<ProtocolItemResolutionData>();
					for(int j=0;j<protocolItemResolutionDataList.size();j++){
						if(j>0&&protocolItemResolutionDataList.get(j).getSort()<9999
							&&protocolItemResolutionDataList.get(j).getSort()-protocolItemResolutionDataList.get(j-1).getSort()>1
						){
							int def=protocolItemResolutionDataList.get(j).getSort()-protocolItemResolutionDataList.get(j-1).getSort();
							for(int k=1;k<def;k++){
								ProtocolItemResolutionData protocolItemResolutionData =new ProtocolItemResolutionData();
								finalProtocolItemResolutionDataList.add(protocolItemResolutionData);
							}
						}
						finalProtocolItemResolutionDataList.add(protocolItemResolutionDataList.get(j));
					}
					
					
					if(finalProtocolItemResolutionDataList.size()%items==0){
						row=finalProtocolItemResolutionDataList.size()/items+1;
					}else{
						row=finalProtocolItemResolutionDataList.size()/items+2;
					}
					result_json.append("{\"name1\":\""+(obj[1]+":"+obj[2]+" "+obj[4])+"\"},");
					
					for(int j=1;j<row;j++){
						//记录每一行的详细信息
						result_json.append("{");
						for(int k=0;k<items;k++){
							int index=items*(j-1)+k;
							String columnName="";
							String value="";
							String rawValue="";
							String addr="";
							String column="";
							String columnDataType="";
							String resolutionMode="";
							String bitIndex="";
							String unit="";
							int alarmLevel=0;
							if(index<finalProtocolItemResolutionDataList.size()&&StringManagerUtils.isNotNull(finalProtocolItemResolutionDataList.get(index).getColumnName())){
								columnName=finalProtocolItemResolutionDataList.get(index).getColumnName();
								value=finalProtocolItemResolutionDataList.get(index).getValue();
								unit=finalProtocolItemResolutionDataList.get(index).getUnit();
								rawValue=finalProtocolItemResolutionDataList.get(index).getRawValue();
								addr=finalProtocolItemResolutionDataList.get(index).getAddr();
								column=finalProtocolItemResolutionDataList.get(index).getColumn();
								columnDataType=finalProtocolItemResolutionDataList.get(index).getColumnDataType();
								resolutionMode=finalProtocolItemResolutionDataList.get(index).getResolutionMode();
								bitIndex=finalProtocolItemResolutionDataList.get(index).getBitIndex();
								for(int l=0;l<alarmItemsList.size();l++){
									Object[] alarmItemObj=(Object[]) alarmItemsList.get(l);
									if(finalProtocolItemResolutionDataList.get(index).getAddr().equals(alarmItemObj[2]+"")){
										int alarmType=StringManagerUtils.stringToInteger(alarmItemObj[3]+"");
										if(alarmType==2 && StringManagerUtils.isNotNull(rawValue)){//数据量报警
											float hystersis=StringManagerUtils.stringToFloat(alarmItemObj[8]+"");
											if((StringManagerUtils.isNotNull(alarmItemObj[6]+"") && StringManagerUtils.stringToFloat(rawValue)>StringManagerUtils.stringToFloat(alarmItemObj[6]+"")+hystersis)
													||(StringManagerUtils.isNotNull(alarmItemObj[7]+"") && StringManagerUtils.stringToFloat(rawValue)<StringManagerUtils.stringToFloat(alarmItemObj[7]+"")-hystersis)
													){
												alarmLevel=StringManagerUtils.stringToInteger(alarmItemObj[10]+"");
												
											}
											break;
										}else if(alarmType==0){//开关量报警
											if(StringManagerUtils.isNotNull(bitIndex)){
												if(bitIndex.equals(alarmItemObj[4]+"") && StringManagerUtils.stringToInteger(rawValue)==StringManagerUtils.stringToInteger(alarmItemObj[5]+"")){
													alarmLevel=StringManagerUtils.stringToInteger(alarmItemObj[10]+"");
												}
											}
										}else if(alarmType==1){//枚举量报警
											if(StringManagerUtils.stringToInteger(rawValue)==StringManagerUtils.stringToInteger(alarmItemObj[5]+"")){
												alarmLevel=StringManagerUtils.stringToInteger(alarmItemObj[10]+"");
											}
										}
									}
								}
							}
							
							if(StringManagerUtils.isNotNull(columnName)&&StringManagerUtils.isNotNull(unit)){
								result_json.append("\"name"+(k+1)+"\":\""+(columnName+"("+unit+")")+"\",");
							}else{
								result_json.append("\"name"+(k+1)+"\":\""+columnName+"\",");
							}
							
							result_json.append("\"value"+(k+1)+"\":\""+value+"\",");

							
							info_json.append("{\"row\":"+j+",\"col\":"+k+",\"addr\":\""+addr+"\","
									+ "\"columnName\":\""+columnName+"\","
									+ "\"column\":\""+column+"\","
									+ "\"value\":\""+value+"\","
									+ "\"columnDataType\":\""+columnDataType+"\","
									+ "\"resolutionMode\":\""+resolutionMode+"\","
									+ "\"alarmLevel\":"+alarmLevel+"},");
						}
						if(result_json.toString().endsWith(",")){
							result_json.deleteCharAt(result_json.length() - 1);
						}
						result_json.append("},");
					}
					if(result_json.toString().endsWith(",")){
						result_json.deleteCharAt(result_json.length() - 1);
					}
				}
			}
		}
		if(info_json.toString().endsWith(",")){
			info_json.deleteCharAt(info_json.length() - 1);
		}
		info_json.append("]");
		result_json.append("]");
		result_json.append(",\"CellInfo\":"+info_json);
		result_json.append(",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle));
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	
	public String getDeviceControlandInfoData(String deviceId,String wellName,String deviceType,int userId)throws Exception {
		StringBuffer result_json = new StringBuffer();
		int dataSaveMode=Config.getInstance().configFile.getOthers().getDataSaveMode();
		String deviceTableName="tbl_pumpdevice";
		String infoTableName="tbl_pumpdeviceaddinfo";
		String columnsKey="pumpDeviceAcquisitionItemColumns";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			deviceTableName="tbl_pipelinedevice";
			infoTableName="tbl_pipelinedeviceaddinfo";
			columnsKey="pipelineDeviceAcquisitionItemColumns";
		}
		
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
			EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
		}
		Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
		
		String isControlSql="select t2.role_flag from tbl_user t,tbl_role t2 where t.user_type=t2.role_id and t.user_no="+userId;
		String protocolItemsSql="select t.wellname,t3.protocol, "
				+ " listagg(t6.itemname, ',') within group(order by t6.groupid,t6.sort,t6.id,t6.bitindex ) key,"
				+ " listagg(decode(t6.sort,null,9999,t6.sort), ',') within group(order by t6.groupid,t6.sort,t6.id,t6.bitindex ) sort "
				+ " from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3,tbl_acq_group2unit_conf t4,tbl_acq_group_conf t5,tbl_acq_item2group_conf t6 "
				+ " where t.instancecode=t2.code and t2.unitid=t3.id and t3.id=t4.unitid and t4.groupid=t5.id and t5.id=t6.groupid "
				+ " and t.id="+deviceId+" and t5.type=1"
				+ " and decode(t6.showlevel,null,9999,t6.showlevel)>=( select r.showlevel from tbl_role r,tbl_user u where u.user_type=r.role_id and u.user_no="+userId+" )"
				+ " group by t.wellname,t3.protocol";
		String auxiliaryDeviceSql="select t3.id,t3.name,t3.model,t3.remark "
				+ " from "+deviceTableName+" t,tbl_auxiliary2master t2,tbl_auxiliarydevice t3 "
				+ " where t.id=t2.masterid and t2.auxiliaryid=t3.id and t.id="+deviceId
				+ " order by t3.sort,t3.name";
		String deviceAddInfoSql = "select t2.id,t2.itemname,t2.itemvalue||decode(t2.itemunit,null,'','','','('||t2.itemunit||')') as itemvalue "
				+ " from "+deviceTableName+" t,"+infoTableName+" t2 "
				+ " where t.id=t2.wellid and t.id="+deviceId
				+ " order by t2.id";
		
		List<?> isControlList = this.findCallSql(isControlSql);
		List<?> itemsList = this.findCallSql(protocolItemsSql);
		List<?> auxiliaryDeviceQueryList = this.findCallSql(auxiliaryDeviceSql);
		List<?> deviceAddInfoList = this.findCallSql(deviceAddInfoSql);
		
		String isControl=isControlList.size()>0?isControlList.get(0).toString():"0";
		
		
		List<String> controlItems=new ArrayList<String>();
		List<String> controlColumns=new ArrayList<String>();
		List<Integer> controlItemResolutionMode=new ArrayList<Integer>();
		List<String> controlItemMeaningList=new ArrayList<String>();
		StringBuffer deviceInfoDataList=new StringBuffer();
		StringBuffer deviceControlList=new StringBuffer();
		StringBuffer auxiliaryDeviceList=new StringBuffer();
		deviceInfoDataList.append("[");
		deviceControlList.append("[");
		auxiliaryDeviceList.append("[");
		
		String protocolCode="";
		for(int i=0;i<itemsList.size();i++){
			Object[] obj=(Object[]) itemsList.get(i);
			protocolCode=obj[1]+"";
			String[] itemsArr=(obj[2]+"").split(",");
			String[] itemsSortArr=(obj[3]+"").split(",");
			Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
			if(equipmentDriveMap.size()==0){
				EquipmentDriverServerTask.loadProtocolConfig();
				equipmentDriveMap = EquipmentDriveMap.getMapObject();
			}
			ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
			if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()!=null){
				for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
					if(protocolCode.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getCode())){
						for(int m=0;m<itemsArr.length;m++){
							for(int k=0;k<modbusProtocolConfig.getProtocol().get(j).getItems().size();k++){
								if(itemsArr[m].equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getTitle())){
									if("rw".equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getRWType())
											||"w".equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getRWType())){
										String title=modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getTitle();
										if(StringManagerUtils.isNotNull(modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getUnit())){
											title+="("+modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getUnit()+")";
										}
										controlItems.add(title);
										String col=dataSaveMode==0?("ADDR"+modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getTitle()));
										controlColumns.add(col);
										controlItemResolutionMode.add(modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getResolutionMode());
										if(modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getResolutionMode()==2){//数据量
											controlItemMeaningList.add("[]");
										}else if(modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getResolutionMode()==1){//枚举量
											if(modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getMeaning()!=null && modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getMeaning().size()>0){
												StringBuffer itemMeaning_buff = new StringBuffer();
												itemMeaning_buff.append("[");
												for(int n=0;n<modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getMeaning().size();n++){
													itemMeaning_buff.append("["+modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getMeaning().get(n).getValue()+",'"+modbusProtocolConfig.getProtocol().get(j).getItems().get(k).getMeaning().get(n).getMeaning()+"'],");
												}
												if(itemMeaning_buff.toString().endsWith(",")){
													itemMeaning_buff.deleteCharAt(itemMeaning_buff.length() - 1);
												}
												itemMeaning_buff.append("]");
												controlItemMeaningList.add(itemMeaning_buff.toString());
											}else{
												controlItemMeaningList.add("[]");
											}
										}else{
											controlItemMeaningList.add("[['true','开'],['false','关']]");
										}
									}
									break;
								}
							}
						}
						break;
					}
				}
			}
		}
		
		//辅件设备
		for(int i=0;i<auxiliaryDeviceQueryList.size();i++){
			Object[] obj=(Object[]) auxiliaryDeviceQueryList.get(i);
			auxiliaryDeviceList.append("{\"id\":"+obj[0]+","
					+ "\"name\":\""+obj[1]+"\","
					+ "\"model\":\""+obj[2]+"\","
					+ "\"remark\":\""+obj[3]+"\"},");
		}
		
		if(auxiliaryDeviceList.toString().endsWith(",")){
			auxiliaryDeviceList.deleteCharAt(auxiliaryDeviceList.length() - 1);
		}
		
		//设备附加信息
		for(int i=0;i<deviceAddInfoList.size();i++){
			Object[] obj=(Object[]) deviceAddInfoList.get(i);
			deviceInfoDataList.append("{\"name\":\""+obj[1]+"\","
					+ "\"value\":\""+obj[2]+"\"},");
		}
		
		if(deviceInfoDataList.toString().endsWith(",")){
			deviceInfoDataList.deleteCharAt(deviceInfoDataList.length() - 1);
		}
		
		String tableName="tbl_pumpacqdata_latest";
		String sql="select t2.commStatus ";
		if(StringManagerUtils.stringToInteger(deviceType)>0){
			tableName="tbl_pipelineacqdata_latest";
		}
//		for(int i=0;i<controlColumns.size();i++){
//			sql+=",t2."+controlColumns.get(i);
//		}
		sql+= " from "+deviceTableName+" t,"+tableName+" t2 where t.id=t2.wellid and t.id="+deviceId;
		
		result_json.append("{ \"success\":true,\"isControl\":"+isControl+",");
		List<?> list = this.findCallSql(sql);
		if(list.size()>0){
			if(controlColumns.size()>0){
//				Object[] obj=(Object[]) list.get(0);
				result_json.append("\"commStatus\":\""+list.get(0)+"\",");
				for(int i=0;i<controlColumns.size();i++){
					deviceControlList.append("{\"title\":\""+controlItems.get(i)+"\",\"name\":\""+controlColumns.get(i)+"\",\"resolutionMode\":"+controlItemResolutionMode.get(i)+",\"value\":\"\",\"itemMeaning\":\""+controlItemMeaningList.get(i)+"\"},");
				}
				if(deviceControlList.toString().endsWith(",")){
					deviceControlList.deleteCharAt(deviceControlList.length() - 1);
				}
			}else{
				result_json.append("\"commStatus\":\""+list.get(0)+"\",");
			}
		}
		deviceInfoDataList.append("]");
		auxiliaryDeviceList.append("]");
		deviceControlList.append("]");
		result_json.append("\"deviceInfoDataList\":"+deviceInfoDataList+",");
		result_json.append("\"auxiliaryDeviceList\":"+auxiliaryDeviceList+",");
		result_json.append("\"deviceControlList\":"+deviceControlList);
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String loadCurveTypeComboxList(String wellName,String deviceType)throws Exception {
		StringBuffer result_json = new StringBuffer();
		List<String> controlItems=new ArrayList<String>();
		List<String> controlColumns=new ArrayList<String>();
		String deviceTableName="tbl_pumpdevice";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			deviceTableName="tbl_pipelinedevice";
		}
		
		
		String protocolSql="select upper(t.protocolcode) from "+deviceTableName+" t where t.wellname='"+wellName+"'";
		List<?> protocolList = this.findCallSql(protocolSql);
		String protocolCode="";
		if(protocolList.size()>0){
			protocolCode=protocolList.get(0)+"";
			Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
			if(equipmentDriveMap.size()==0){
				EquipmentDriverServerTask.loadProtocolConfig();
				equipmentDriveMap = EquipmentDriveMap.getMapObject();
			}
			ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
			if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()!=null){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					if(protocolCode.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getCode())){
						for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
							if(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getIFDataType().contains("float")||modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getIFDataType().contains("int")){//如果float或者int
								controlItems.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle());
								controlColumns.add("ADDR"+modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getAddr());
							}
						}
						break;
					}
				}
			}
		}
		
		result_json.append("{\"totals\":"+controlColumns.size()+",\"list\":[");
		for(int i=0;i<controlColumns.size();i++){
			result_json.append("{boxkey:\"" + controlColumns.get(i) + "\",");
			result_json.append("boxval:\"" + controlItems.get(i) + "\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	
	public String getRealTimeCurveData(String deviceName,String item,String deviceType)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String deviceTableName="tbl_pumpdevice";
		String tableName="tbl_pumpacqdata_hist";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			deviceTableName="tbl_pipelinedevice";
			tableName="tbl_pipelineacqdata_hist";
		}
		String protocolSql="select upper(t3.protocol) from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t.instancecode=t2.code and t2.unitid=t3.id"
				+ " and  t.wellname='"+deviceName+"'";
		List<?> protocolList = this.findCallSql(protocolSql);
		String protocolName="";
		String column="";
		String unit="";
		String dataType="";
		int resolutionMode=0;
		if(protocolList.size()>0){
			protocolName=protocolList.get(0)+"";
			Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
			if(equipmentDriveMap.size()==0){
				EquipmentDriverServerTask.loadProtocolConfig();
				equipmentDriveMap = EquipmentDriveMap.getMapObject();
			}
			ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
			if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()!=null){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
						for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
							if(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle().equalsIgnoreCase(item)){
								column="ADDR"+modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getAddr();
								unit=modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getUnit();
								dataType=modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getIFDataType();
								resolutionMode=modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getResolutionMode();
								break;
							}
						}
						break;
					}
				}
			}
		}
		
		result_json.append("{\"deviceName\":\""+deviceName+"\",\"item\":\""+item+"\",\"column\":\""+column+"\",\"unit\":\""+unit+"\",\"dataType\":\""+dataType+"\",\"resolutionMode\":"+resolutionMode+",\"list\":[");
		if(resolutionMode==2){//只查询数据量的曲线
			String sql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss'), t."+column+" "
					+ " from "+tableName +" t,"+deviceTableName+" t2 "
					+ " where t.wellid=t2.id "
					+ " and t.acqtime >to_date('"+StringManagerUtils.getCurrentTime("yyyy-MM-dd")+"','yyyy-mm-dd') "
					+ " and t2.wellname='"+deviceName+"' "
					+ " order by t.acqtime";
			List<?> list = this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				result_json.append("{acqTime:\"" + obj[0] + "\",");
				result_json.append("value:\"" + obj[1] + "\"},");
			}
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getRealTimeMonitoringCurveData(String deviceId,String deviceName,String deviceType)throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer itemsBuff = new StringBuffer();
		StringBuffer curveColorBuff = new StringBuffer();
		
		int dataSaveMode=Config.getInstance().configFile.getOthers().getDataSaveMode();
		
		String tableName="tbl_pumpacqdata_hist";
		String deviceTableName="tbl_pumpdevice";
		String columnsKey="pumpDeviceAcquisitionItemColumns";
		
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="tbl_pipelineacqdata_hist";
			deviceTableName="tbl_pipelinedevice";
			columnsKey="pipelineDeviceAcquisitionItemColumns";
		}
		Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
		if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
			EquipmentDriverServerTask.loadAcquisitionItemColumns(StringManagerUtils.stringToInteger(deviceType));
		}
		Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
		
		
		String protocolSql="select upper(t3.protocol) from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t.instancecode=t2.code and t2.unitid=t3.id"
				+ " and  t.id="+deviceId;
		
		String curveItemsSql="select t6.itemname,t6.bitindex,t6.realtimecurvecolor "
				+ " from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3,tbl_acq_group2unit_conf t4,tbl_acq_group_conf t5,tbl_acq_item2group_conf t6 "
				+ " where t.instancecode=t2.code and t2.unitid=t3.id and t3.id=t4.unitid and t4.groupid=t5.id and t5.id=t6.groupid "
				+ " and t.id="+deviceId+" and t6.realtimecurve>=0 "
				+ " order by t6.realtimecurve,t6.sort,t6.id";
		List<?> protocolList = this.findCallSql(protocolSql);
		List<?> curveItemList = this.findCallSql(curveItemsSql);
		String protocolName="";
		String unit="";
		String dataType="";
		int resolutionMode=0;
		List<String> itemNameList=new ArrayList<String>();
		List<String> itemColumnList=new ArrayList<String>();
		List<String> curveColorList=new ArrayList<String>();
		if(protocolList.size()>0){
			protocolName=protocolList.get(0)+"";
			Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
			if(equipmentDriveMap.size()==0){
				EquipmentDriverServerTask.loadProtocolConfig();
				equipmentDriveMap = EquipmentDriveMap.getMapObject();
			}
			ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
			if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()!=null){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
						for(int j=0;j<curveItemList.size();j++){
							Object[] itemObj=(Object[]) curveItemList.get(j);
							for(int k=0;k<modbusProtocolConfig.getProtocol().get(i).getItems().size();k++){
								if(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle().equalsIgnoreCase(itemObj[0]+"")){
									String col=dataSaveMode==0?("addr"+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getAddr()):(loadedAcquisitionItemColumnsMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()));
									itemColumnList.add(col);
									if(StringManagerUtils.isNotNull(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getUnit())){
										itemNameList.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle()+"("+modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getUnit()+")");
									}else{
										itemNameList.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle());
									}
									curveColorList.add((itemObj[2]+"").replaceAll("null", ""));
									break;
								}
							}
						}
						break;
					}
				}
			}
		}
		
		itemsBuff.append("[");
		for(int i=0;i<itemNameList.size();i++){
			itemsBuff.append("\""+itemNameList.get(i)+"\",");
		}
		if (itemsBuff.toString().endsWith(",")) {
			itemsBuff.deleteCharAt(itemsBuff.length() - 1);
		}
		itemsBuff.append("]");
		
		curveColorBuff.append("[");
		for(int i=0;i<curveColorList.size();i++){
			curveColorBuff.append("\""+curveColorList.get(i)+"\",");
		}
		if (curveColorBuff.toString().endsWith(",")) {
			curveColorBuff.deleteCharAt(curveColorBuff.length() - 1);
		}
		curveColorBuff.append("]");
		
		result_json.append("{\"deviceName\":\""+deviceName+"\",\"curveCount\":"+itemNameList.size()+",\"curveItems\":"+itemsBuff+",\"curveColors\":"+curveColorBuff+",\"list\":[");
		if(itemColumnList.size()>0){
			String columns="";
			for(int i=0;i<itemColumnList.size();i++){
				columns+=","+itemColumnList.get(i);
			}
			String sql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime"+columns
					+ " from "+tableName +" t,"+deviceTableName+" t2 "
					+ " where t.wellid=t2.id "
					+ " and t.acqtime >to_date('"+StringManagerUtils.getCurrentTime("yyyy-MM-dd")+"','yyyy-mm-dd') "
					+ " and t2.id="+deviceId;
			int total=this.getTotalCountRows(sql);
			int rarefy=total/500+1;
			sql+= " order by t.acqtime";
			
			String finalSql=sql;
			if(rarefy>1){
				finalSql="select acqtime"+columns+" from  (select v.*, rownum as rn from ("+sql+") v ) v2 where mod(rn-1,"+rarefy+")=0";
			}
			List<?> list = this.findCallSql(finalSql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				result_json.append("{\"acqTime\":\"" + obj[0] + "\",\"data\":[");
				for(int j=1;j<obj.length;j++){
					result_json.append(obj[j]+",");
				}
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
				result_json.append("]},");
			}
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public void saveDeviceControlLog(String deviceId,String wellName,String deviceType,String title,String value,User user) throws SQLException{
		getBaseDao().saveDeviceControlLog(deviceId,wellName,deviceType,title,value,user);
	}
	
	public String getResourceProbeHistoryCurveData(String startDate,String endDate,String itemName,String itemCode) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		String sql="select to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss'),"+itemCode+" from tbl_resourcemonitoring t "
				+ " where t.acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') +1 "
				+ " order by t.acqTime";
		int totals = getTotalCountRows(sql);//获取总记录数
		List<?> list=this.findCallSql(sql);
		dynSbf.append("{\"success\":true,\"totalCount\":" + totals + ",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				dynSbf.append("{ \"acqTime\":\"" + obj[0] + "\",");
				dynSbf.append("\"value\":\""+obj[1]+"\"},");
			}
			if(dynSbf.toString().endsWith(",")){
				dynSbf.deleteCharAt(dynSbf.length() - 1);
			}
		}
		dynSbf.append("]}");
		return dynSbf.toString().replaceAll("null", "");
	}
}
