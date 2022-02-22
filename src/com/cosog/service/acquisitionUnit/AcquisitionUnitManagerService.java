package com.cosog.service.acquisitionUnit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AcquisitionGroup;
import com.cosog.model.AcquisitionGroupItem;
import com.cosog.model.AcquisitionUnitGroup;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.ProtocolAlarmInstance;
import com.cosog.model.ProtocolSMSInstance;
import com.cosog.model.User;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.drive.KafkaConfig;
import com.cosog.model.drive.ModbusProtocolAlarmUnitSaveData;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.drive.ModbusProtocolConfig.Protocol;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.utils.DataModelMap;
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
	@Autowired
	private DataitemsInfoService dataitemsInfoService;

	public String getAcquisitionUnitList(Map map,Page pager) {
		String protocolName = (String) map.get("protocolName");
		String unitName = (String) map.get("unitName");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark "
				+ " from tbl_acq_unit_conf t where 1=1");
//		if (StringManagerUtils.isNotNull(protocolName)) {
//			sqlBuffer.append(" and t.protocol = '" + protocolName + "' ");
//		}
		sqlBuffer.append(" and t.protocol = '" + protocolName + "' ");
		
		
		sqlBuffer.append(" and t.protocol='"+protocolName+"'");
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
		return jsonBuffer.toString();
	}
	
	public String getProtocolItemsConfigData(String protocolName,String classes,String code){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
//				+ "{ \"header\":\"名称\",\"dataIndex\":\"name\",width:120 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"数量\",\"dataIndex\":\"quantity\",width:80 ,children:[] },"
				+ "{ \"header\":\"存储数据类型\",\"dataIndex\":\"storeDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\"接口数据类型\",\"dataIndex\":\"IFDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\"读写类型\",\"dataIndex\":\"RWType\",width:80 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\"换算比例\",\"dataIndex\":\"ratio\",width:80 ,children:[] },"
				+ "{ \"header\":\"解析模式\",\"dataIndex\":\"resolutionMode\",width:80 ,children:[] },"
				+ "{ \"header\":\"采集模式\",\"dataIndex\":\"acqMode\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.sort from TBL_ACQ_ITEM2GROUP_CONF t,tbl_acq_group_conf t2 where t.groupid=t2.id and t2.group_code='"+code+"' order by t.id";
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsSortList.add(obj[1]+"");
			}
		}
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			ModbusProtocolConfig.Protocol protocolConfig=modbusProtocolConfig.getProtocol().get(i);
			if(protocolName.equalsIgnoreCase(protocolConfig.getName())){
				Collections.sort(protocolConfig.getItems());
				for(int j=0;j<protocolConfig.getItems().size();j++){
					boolean checked=false;
					String sort="";
					checked=StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(),false);
					if(checked){
						for(int k=0;k<itemsList.size();k++){
							if(itemsList.get(k).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())){
								sort=itemsSortList.get(k);
								break;
							}
						}
					}
					String resolutionMode="数据量";
					if(protocolConfig.getItems().get(j).getResolutionMode()==0){
						resolutionMode="开关量";
					}else if(protocolConfig.getItems().get(j).getResolutionMode()==1){
						resolutionMode="枚举量";
					}
					String RWType="只读";
					if("r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWType="只读";
					}else if("w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWType="只写";
					}else if("rw".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWType="读写";
					}
					result_json.append("{\"checked\":"+checked+","
							+ "\"id\":"+(j+1)+","
							+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
							+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+","
							+ "\"quantity\":"+protocolConfig.getItems().get(j).getQuantity()+","
							+ "\"storeDataType\":\""+protocolConfig.getItems().get(j).getStoreDataType()+"\","
							+ "\"IFDataType\":\""+protocolConfig.getItems().get(j).getIFDataType()+"\","
							+ "\"ratio\":"+protocolConfig.getItems().get(j).getRatio()+","
							+ "\"RWType\":\""+RWType+"\","
							+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
							+ "\"resolutionMode\":\""+resolutionMode+"\","
							+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?"主动上传":"被动响应")+"\","
							+ "\"sort\":\""+sort+"\"},");
					
				}
				break;
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolAcqUnitItemsConfigData(String protocolName,String classes,String code,String type){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
//				+ "{ \"header\":\"名称\",\"dataIndex\":\"name\",width:120 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"数量\",\"dataIndex\":\"quantity\",width:80 ,children:[] },"
				+ "{ \"header\":\"存储数据类型\",\"dataIndex\":\"storeDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\"接口数据类型\",\"dataIndex\":\"IFDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\"读写类型\",\"dataIndex\":\"RWType\",width:80 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\"换算比例\",\"dataIndex\":\"ratio\",width:80 ,children:[] },"
				+ "{ \"header\":\"解析模式\",\"dataIndex\":\"resolutionMode\",width:80 ,children:[] },"
				+ "{ \"header\":\"采集模式\",\"dataIndex\":\"acqMode\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示级别\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线颜色\",\"dataIndex\":\"realtimeCurveColor\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线颜色\",\"dataIndex\":\"historyCurveColor\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveList=new ArrayList<String>();
		List<String> realtimeCurveColorList=new ArrayList<String>();
		List<String> historyCurveList=new ArrayList<String>();
		List<String> historyCurveColorList=new ArrayList<String>();
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.sort,t.bitindex,t.showlevel,t.realtimeCurve,t.realtimeCurveColor,historyCurve,historyCurveColor from TBL_ACQ_ITEM2GROUP_CONF t,tbl_acq_group_conf t2 where t.groupid=t2.id and t2.group_code='"+code+"' order by t.id";
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsSortList.add(obj[1]+"");
				itemsBitIndexList.add(obj[2]+"");
				itemsShowLevelList.add(obj[3]+"");
				realtimeCurveList.add(obj[4]+"");
				realtimeCurveColorList.add(obj[5]+"");
				historyCurveList.add(obj[6]+"");
				historyCurveColorList.add(obj[7]+"");
			}
		}
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			ModbusProtocolConfig.Protocol protocolConfig=modbusProtocolConfig.getProtocol().get(i);
			if(protocolName.equalsIgnoreCase(protocolConfig.getName())){
				Collections.sort(protocolConfig.getItems());
				int index=1;
				for(int j=0;j<protocolConfig.getItems().size();j++){
					boolean sign=false;
					if(!"3".equalsIgnoreCase(classes)){
						sign=true;
					}else{
						if("0".equals(type)&&(!("w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())))){//采集组
							sign=true;
						}else if("1".equals(type)&&(!("r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())))){//控制组
							sign=true;
						}
					}
					if(sign){
						boolean checked=false;
						String sort="";
						String showLevel="";
						String isRealtimeCurve="";
						String realtimeCurveColor="";
						String isHistoryCurve="";
						String historyCurveColor="";
						String resolutionMode="数据量";
						if(protocolConfig.getItems().get(j).getResolutionMode()==0){
							resolutionMode="开关量";
						}else if(protocolConfig.getItems().get(j).getResolutionMode()==1){
							resolutionMode="枚举量";
						}
						String RWType="只读";
						if("r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
							RWType="只读";
						}else if("w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
							RWType="只写";
						}else if("rw".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
							RWType="读写";
						}
						if(protocolConfig.getItems().get(j).getResolutionMode()==0
								&&protocolConfig.getItems().get(j).getMeaning()!=null
								&&protocolConfig.getItems().get(j).getMeaning().size()>0){
							Collections.sort(protocolConfig.getItems().get(j).getMeaning());//排序
							for(int k=0;k<protocolConfig.getItems().get(j).getMeaning().size();k++){
								checked=false;
								sort="";
								showLevel="";
								isRealtimeCurve="";
								realtimeCurveColor="";
								isHistoryCurve="";
								historyCurveColor="";
								for(int m=0;m<itemsList.size();m++){
									if(itemsList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())
											&&itemsBitIndexList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"")
										){
										checked=true;
										sort=itemsSortList.get(m);
										showLevel=itemsShowLevelList.get(m);
										isRealtimeCurve=realtimeCurveList.get(m);
										realtimeCurveColor=realtimeCurveColorList.get(m);
										isHistoryCurve=historyCurveList.get(m);
										historyCurveColor=historyCurveColorList.get(m);
										break;
									}
								}
								
								result_json.append("{"
										+ "\"checked\":"+checked+","
										+ "\"id\":"+(index)+","
										+ "\"title\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
										+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+","
										+ "\"quantity\":"+1+","
										+ "\"storeDataType\":\""+protocolConfig.getItems().get(j).getStoreDataType()+"\","
										+ "\"IFDataType\":\""+protocolConfig.getItems().get(j).getIFDataType()+"\","
										+ "\"ratio\":"+protocolConfig.getItems().get(j).getRatio()+","
										+ "\"RWType\":\""+RWType+"\","
										+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
										+ "\"resolutionMode\":\""+resolutionMode+"\","
										+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?"主动上传":"被动响应")+"\","
										+ "\"showLevel\":\""+showLevel+"\","
										+ "\"sort\":\""+sort+"\","
										+ "\"isRealtimeCurve\":\""+isRealtimeCurve+"\","
										+ "\"realtimeCurveColor\":\""+realtimeCurveColor+"\","
										+ "\"isHistoryCurve\":\""+isHistoryCurve+"\","
										+ "\"historyCurveColor\":\""+historyCurveColor+"\""
										+ "},");
								index++;
							}
						}else{
							checked=StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(),false);
							if(checked){
								for(int k=0;k<itemsList.size();k++){
									if(itemsList.get(k).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())){
										sort=itemsSortList.get(k);
										showLevel=itemsShowLevelList.get(k);
										isRealtimeCurve=realtimeCurveList.get(k);
										realtimeCurveColor=realtimeCurveColorList.get(k);
										isHistoryCurve=historyCurveList.get(k);
										historyCurveColor=historyCurveColorList.get(k);
										break;
									}
								}
							}
							result_json.append("{\"checked\":"+checked+","
									+ "\"id\":"+(index)+","
									+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
									+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+","
									+ "\"quantity\":"+protocolConfig.getItems().get(j).getQuantity()+","
									+ "\"storeDataType\":\""+protocolConfig.getItems().get(j).getStoreDataType()+"\","
									+ "\"IFDataType\":\""+protocolConfig.getItems().get(j).getIFDataType()+"\","
									+ "\"ratio\":"+protocolConfig.getItems().get(j).getRatio()+","
									+ "\"RWType\":\""+RWType+"\","
									+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
									+ "\"resolutionMode\":\""+resolutionMode+"\","
									+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?"主动上传":"被动响应")+"\","
									+ "\"showLevel\":\""+showLevel+"\","
									+ "\"sort\":\""+sort+"\","
									+ "\"isRealtimeCurve\":\""+isRealtimeCurve+"\","
									+ "\"realtimeCurveColor\":\""+realtimeCurveColor+"\","
									+ "\"isHistoryCurve\":\""+isHistoryCurve+"\","
									+ "\"historyCurveColor\":\""+historyCurveColor+"\""
									+ "},");
							index++;
						}
					}
				}
				break;
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolEnumOrSwitchItemsConfigData(String protocolCode,String resolutionMode){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			ModbusProtocolConfig.Protocol protocolConfig=modbusProtocolConfig.getProtocol().get(i);
			if(protocolCode.equalsIgnoreCase(protocolConfig.getCode())){
				for(int j=0;j<protocolConfig.getItems().size();j++){
					if((!"w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())) && protocolConfig.getItems().get(j).getResolutionMode()==StringManagerUtils.stringToInteger(resolutionMode)){
						result_json.append("{\"id\":"+(j+1)+","
								+ "\"protocolCode\":\""+protocolCode+"\","
								+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
								+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+"},");
					}
				}
				break;
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString();
	}
	
	public String getProtocolItemMeaningConfigData(String protocolCode,String itemAddr){
		StringBuffer result_json = new StringBuffer();
		StringBuffer totalRoot = new StringBuffer();
		Gson gson = new Gson();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		int resolutionMode=0;
		String title="";
		totalRoot.append("[");
		int totolCount=0;
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			ModbusProtocolConfig.Protocol protocolConfig=modbusProtocolConfig.getProtocol().get(i);
			if(protocolCode.equalsIgnoreCase(protocolConfig.getCode())){
				for(int j=0;j<protocolConfig.getItems().size();j++){
					if(protocolConfig.getItems().get(j).getAddr()==StringManagerUtils.stringToInteger(itemAddr)){
						resolutionMode=protocolConfig.getItems().get(j).getResolutionMode();
						title=protocolConfig.getItems().get(j).getTitle();
						if(protocolConfig.getItems().get(j).getMeaning()!=null&&protocolConfig.getItems().get(j).getMeaning().size()>0){
							Collections.sort(protocolConfig.getItems().get(j).getMeaning());
						}
						for(int k=0;protocolConfig.getItems().get(j).getMeaning()!=null&&k<protocolConfig.getItems().get(j).getMeaning().size();k++){
							totolCount=k+1;
							totalRoot.append("{\"id\":"+(k+1)+","
									+ "\"value\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"\","
									+ "\"meaning\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\"},");
						}
						break;
					}
				}
				break;
			}
		}
		if(totolCount<30){
			for(int i=totolCount;i<20;i++){
				totalRoot.append("{},");
			}
		}
		if(totalRoot.toString().endsWith(",")){
			totalRoot.deleteCharAt(totalRoot.length() - 1);
		}
		totalRoot.append("]");
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"值\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"含义\",\"dataIndex\":\"addr\",width:80 ,children:[] }"
				+ "]";
		if(resolutionMode==0){
			columns = "["
					+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
					+ "{ \"header\":\"位\",\"dataIndex\":\"title\",width:120 ,children:[] },"
					+ "{ \"header\":\"含义\",\"dataIndex\":\"addr\",width:80 ,children:[] }"
					+ "]";
		}
		result_json.append("{ \"success\":true,\"columns\":"+columns+",\"itemResolutionMode\":"+resolutionMode+",\"itemTitle\":\""+title+"\",");
		result_json.append("\"totalRoot\":"+totalRoot+"");
		result_json.append("}");
		return result_json.toString();
	}
	
	public String getModbusProtocolNumAlarmItemsConfigData(String protocolName,String classes,String code){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		String columns = "["
				+ "{ \"header\":\"\",\"dataIndex\":\"checked\",width:20 ,children:[] },"
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"上限\",\"dataIndex\":\"upperLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\"下限\",\"dataIndex\":\"lowerLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\"回差\",\"dataIndex\":\"hystersis\",width:80 ,children:[] },"
				+ "{ \"header\":\"延时(S)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警级别\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警使能\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送短信\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送邮件\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<Integer> itemAddrsList=new ArrayList<Integer>();
		List<?> list=null;
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.itemaddr,t.upperlimit,t.lowerlimit,t.hystersis,t.delay,"
					+ " t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效') as alarmsign,"
					+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_code t3  "
					+ " where t.type=2 and t.unitid=t2.id and upper(t3.itemcode)=upper('BJJB') and t.alarmlevel=t3.itemvalue and t2.unit_code='"+code+"' "
					+ " order by t.id";
			list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				itemAddrsList.add(StringManagerUtils.stringToInteger(obj[2]+""));
			}
		}
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			ModbusProtocolConfig.Protocol protocolConfig=modbusProtocolConfig.getProtocol().get(i);
			if(protocolName.equalsIgnoreCase(protocolConfig.getName())){
				Collections.sort(protocolConfig.getItems());
				int index=1;
				for(int j=0;j<protocolConfig.getItems().size();j++){
					if((!"w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())) && protocolConfig.getItems().get(j).getResolutionMode()==2){
						String upperLimit="",lowerLimit="",hystersis="",delay="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
						boolean checked=false;
						for(int k=0;k<itemAddrsList.size();k++){
							Object[] obj = (Object[]) list.get(k);
							if(itemAddrsList.get(k)==protocolConfig.getItems().get(j).getAddr()){
								checked=true;
								upperLimit=obj[3]+"";
								lowerLimit=obj[4]+"";
								hystersis=obj[5]+"";
								delay=obj[6]+"";
								alarmLevel=obj[7]+"";
								alarmSign=obj[8]+"";
								isSendMessage=obj[9]+"";
								isSendMail=obj[10]+"";
								break;
							}
						}
						result_json.append("{\"checked\":"+checked+","
								+ "\"id\":"+(index)+","
								+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
								+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+","
								+ "\"upperLimit\":\""+upperLimit+"\","
								+ "\"lowerLimit\":\""+lowerLimit+"\","
								+ "\"hystersis\":\""+hystersis+"\","
								+ "\"delay\":\""+delay+"\","
								+ "\"alarmLevel\":\""+alarmLevel+"\","
								+ "\"alarmSign\":\""+alarmSign+"\","
								+ "\"isSendMessage\":\""+isSendMessage+"\","
								+ "\"isSendMail\":\""+isSendMail+"\""
								+ "},");
						index++;
					}
				}
				break;
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString();
	}
	
	public String getModbusProtocolEnumAlarmItemsConfigData(String protocolName,String classes,String unitCode,String itemAddr,String itemResolutionMode){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		String columns = "["
				+ "{ \"header\":\"\",\"dataIndex\":\"checked\",width:20 ,children:[] },"
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"值\",\"dataIndex\":\"value\",width:120 ,children:[] },"
				+ "{ \"header\":\"含义\",\"dataIndex\":\"meaning\",width:80 ,children:[] },"
				+ "{ \"header\":\"延时(S)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警级别\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警使能\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送短信\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送邮件\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<Integer> itemValueList=new ArrayList<Integer>();
		List<?> list=null;
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.itemaddr,t.value,t.delay,"
					+ " t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效'), "
					+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_code t3  "
					+ " where t.type="+itemResolutionMode+" and t.itemAddr="+itemAddr+" and t.unitid=t2.id and upper(t3.itemcode)=upper('BJJB') and t.alarmlevel=t3.itemvalue and t2.unit_code='"+unitCode+"' "
					+ " order by t.id";
			list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				itemValueList.add(StringManagerUtils.stringToInteger(obj[3]+""));
			}
		}
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			ModbusProtocolConfig.Protocol protocolConfig=modbusProtocolConfig.getProtocol().get(i);
			if(protocolName.equalsIgnoreCase(protocolConfig.getName())){
				Collections.sort(protocolConfig.getItems());
				int index=1;
				for(int j=0;j<protocolConfig.getItems().size();j++){
					if(protocolConfig.getItems().get(j).getResolutionMode()==StringManagerUtils.stringToInteger(itemResolutionMode)
							&& protocolConfig.getItems().get(j).getAddr()==StringManagerUtils.stringToInteger(itemAddr)){
						for(int k=0;protocolConfig.getItems().get(j).getMeaning()!=null&&k<protocolConfig.getItems().get(j).getMeaning().size();k++){
							String value="",delay="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
							boolean checked=false;
							for(int m=0;m<itemValueList.size();m++){
								Object[] obj = (Object[]) list.get(m);
								if(itemValueList.get(m)==protocolConfig.getItems().get(j).getMeaning().get(k).getValue()){
									checked=true;
									delay=obj[4]+"";
									alarmLevel=obj[5]+"";
									alarmSign=obj[6]+"";
									isSendMessage=obj[7]+"";
									isSendMail=obj[8]+"";
									break;
								}
							}
							result_json.append("{\"checked\":"+checked+","
									+ "\"id\":"+(index)+","
									+ "\"value\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"\","
									+ "\"meaning\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
									+ "\"delay\":\""+delay+"\","
									+ "\"alarmLevel\":\""+alarmLevel+"\","
									+ "\"alarmSign\":\""+alarmSign+"\","
									+ "\"isSendMessage\":\""+isSendMessage+"\","
									+ "\"isSendMail\":\""+isSendMail+"\""
									+ "},");
							index++;
						}
					}
				}
				break;
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString();
	}
	
	public String getModbusProtocolSwitchAlarmItemsConfigData(String protocolName,String classes,String unitCode,String itemAddr,String itemResolutionMode){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		String columns = "["
				+ "{ \"header\":\"\",\"dataIndex\":\"checked\",width:20 ,children:[] },"
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"位\",\"dataIndex\":\"bitIndex\",width:120 ,children:[] },"
				+ "{ \"header\":\"含义\",\"dataIndex\":\"meaning\",width:80 ,children:[] },"
				+ "{ \"header\":\"触发状态\",\"dataIndex\":\"value\",width:80 ,children:[] },"
				+ "{ \"header\":\"延时(S)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警级别\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警使能\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送短信\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送邮件\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<Integer> itemValueList=new ArrayList<Integer>();
		List<?> list=null;
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.itemaddr,t.bitindex,t.value,t.delay,"
					+ " t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效'), "
					+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_code t3  "
					+ " where t.type="+itemResolutionMode+" and t.itemAddr="+itemAddr+" and t.unitid=t2.id and upper(t3.itemcode)=upper('BJJB') and t.alarmlevel=t3.itemvalue and t2.unit_code='"+unitCode+"' "
					+ " order by t.id";
			list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				itemValueList.add(StringManagerUtils.stringToInteger(obj[3]+""));
			}
		}
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			ModbusProtocolConfig.Protocol protocolConfig=modbusProtocolConfig.getProtocol().get(i);
			if(protocolName.equalsIgnoreCase(protocolConfig.getName())){
				int index=1;
				Collections.sort(protocolConfig.getItems());
				for(int j=0;j<protocolConfig.getItems().size();j++){
					if(protocolConfig.getItems().get(j).getResolutionMode()==StringManagerUtils.stringToInteger(itemResolutionMode)
							&& protocolConfig.getItems().get(j).getAddr()==StringManagerUtils.stringToInteger(itemAddr)){
						for(int k=0;protocolConfig.getItems().get(j).getMeaning()!=null&&k<protocolConfig.getItems().get(j).getMeaning().size();k++){
							String value="",delay="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
							boolean checked=false;
							for(int m=0;m<itemValueList.size();m++){
								Object[] obj = (Object[]) list.get(m);
								if(itemValueList.get(m)==protocolConfig.getItems().get(j).getMeaning().get(k).getValue()){
									checked=true;
									if("0".equals(obj[4]+"")){
										value="关";
									}else if("1".equals(obj[4]+"")){
										value="开";
									}
									delay=obj[5]+"";
									alarmLevel=obj[6]+"";
									alarmSign=obj[7]+"";
									isSendMessage=obj[8]+"";
									isSendMail=obj[9]+"";
									break;
								}
							}
							result_json.append("{\"checked\":"+checked+","
									+ "\"id\":"+(index)+","
									+ "\"bitIndex\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"\","
									+ "\"meaning\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
									+ "\"value\":\""+value+"\","
									+ "\"delay\":\""+delay+"\","
									+ "\"alarmLevel\":\""+alarmLevel+"\","
									+ "\"alarmSign\":\""+alarmSign+"\","
									+ "\"isSendMessage\":\""+isSendMessage+"\","
									+ "\"isSendMail\":\""+isSendMail+"\""
									+ "},");
							index++;
						}
					}
				}
				break;
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString();
	}
	
	public String getModbusProtocolCommStatusAlarmItemsConfigData(String protocolName,String classes,String code){
		StringBuffer result_json = new StringBuffer();
		String columns = "["
				+ "{ \"header\":\"\",\"dataIndex\":\"checked\",width:20 ,children:[] },"
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"延时(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警级别\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警使能\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送短信\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送邮件\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> commStatusItemsList=new ArrayList<String>();
		commStatusItemsList.add("上线");
		commStatusItemsList.add("离线");
		List<?> list=null;
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.delay,"
					+ " t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效') as alarmsign,"
					+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_code t3  "
					+ " where t.type=3 and t.unitid=t2.id and upper(t3.itemcode)=upper('BJJB') and t.alarmlevel=t3.itemvalue and t2.unit_code='"+code+"' "
					+ " order by t.id";
			list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				itemsList.add(obj[0]+"");
			}
		}
		
		boolean checked=false;
		String delay="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
		for(int i=0;i<commStatusItemsList.size();i++){
			if(StringManagerUtils.existOrNot(itemsList,commStatusItemsList.get(i),false)){
				for(int j=0;j<list.size();j++){
					Object[] obj = (Object[]) list.get(j);
					if(commStatusItemsList.get(i).equalsIgnoreCase(obj[0]+"")){
						checked=true;
						delay=obj[2]+"";
						alarmLevel=obj[3]+"";
						alarmSign=obj[4]+"";
						isSendMessage=obj[5]+"";
						isSendMail=obj[6]+"";
						break;
					}
				}
			}
			result_json.append("{\"checked\":"+checked+","
					+ "\"id\":"+(i+1)+","
					+ "\"title\":\""+commStatusItemsList.get(i)+"\","
					+ "\"delay\":\""+delay+"\","
					+ "\"alarmLevel\":\""+alarmLevel+"\","
					+ "\"alarmSign\":\""+alarmSign+"\","
					+ "\"isSendMessage\":\""+isSendMessage+"\","
					+ "\"isSendMail\":\""+isSendMail+"\""
					+ "},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString();
	}
	
	public String getProtocolInstanceItemsConfigData(String id,String classes){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		int index=1;
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"数量\",\"dataIndex\":\"quantity\",width:80 ,children:[] },"
				+ "{ \"header\":\"存储数据类型\",\"dataIndex\":\"storeDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\"接口数据类型\",\"dataIndex\":\"IFDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\"读写类型\",\"dataIndex\":\"RWType\",width:80 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\"换算比例\",\"dataIndex\":\"ratio\",width:80 ,children:[] },"
				+ "{ \"header\":\"采集模式\",\"dataIndex\":\"acqMode\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		List<String> itemsList=new ArrayList<String>();
		
		String protocolSql="select t.protocol from tbl_acq_unit_conf t,tbl_protocolinstance t2 where t2.unitid=t.id and t2.id="+id+"";
		String itemsSql="select distinct(t.itemname) "
				+ " from tbl_acq_item2group_conf t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4,tbl_protocolinstance t5 "
				+ " where t.groupid=t2.id and t2.id=t3.groupid and t3.unitid=t4.id and t4.id=t5.unitid and t5.id="+id+"";
		if("2".equals(classes)){
			protocolSql="select t.protocol from tbl_acq_unit_conf t where t.id="+id+"";
			itemsSql="select distinct(t.itemname) "
					+ " from tbl_acq_item2group_conf t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4 "
					+ " where t.groupid=t2.id and t2.id=t3.groupid and t3.unitid=t4.id and t4.id="+id+"";
		}else if("3".equals(classes)){
			protocolSql="select t.protocol from tbl_acq_group_conf t where t.id="+id+"";
			itemsSql="select distinct(t.itemname) "
					+ " from tbl_acq_item2group_conf t,tbl_acq_group_conf t2 "
					+ " where t.groupid=t2.id and t2.id="+id+"";
		}
		List<?> protocolList=this.findCallSql(protocolSql);
		if(protocolList.size()>0){
			String protocolName=protocolList.get(0)+"";
			List<?> list=this.findCallSql(itemsSql);
			for(int i=0;i<list.size();i++){
				itemsList.add(list.get(i)+"");
			}
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				ModbusProtocolConfig.Protocol protocolConfig=modbusProtocolConfig.getProtocol().get(i);
				if(protocolName.equalsIgnoreCase(protocolConfig.getName())){
					Collections.sort(protocolConfig.getItems());
					for(int j=0;j<protocolConfig.getItems().size();j++){
						if(StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(),false)){
//							if(RWType.equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){}
							String RWTypeName="只读";
							if("r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
								RWTypeName="只读";
							}else if("w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
								RWTypeName="只写";
							}else if("rw".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
								RWTypeName="读写";
							}
							
							result_json.append("{\"id\":"+index+","
									+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
									+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+","
									+ "\"quantity\":"+protocolConfig.getItems().get(j).getQuantity()+","
									+ "\"storeDataType\":\""+protocolConfig.getItems().get(j).getStoreDataType()+"\","
									+ "\"IFDataType\":\""+protocolConfig.getItems().get(j).getIFDataType()+"\","
									+ "\"ratio\":"+protocolConfig.getItems().get(j).getRatio()+","
									+ "\"RWType\":\""+RWTypeName+"\","
									+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
									+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?"主动上传":"被动响应")+"\"},");
							index++;
						}
					}
					break;
				}
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString();
	}
	
	public String getProtocolAlarmInstanceNumItemsConfigData(String id,String classes,String resolutionMode){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"上限\",\"dataIndex\":\"upperLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\"下限\",\"dataIndex\":\"lowerLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\"回差\",\"dataIndex\":\"hystersis\",width:80 ,children:[] },"
				+ "{ \"header\":\"延时(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警级别\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警开关\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		List<Integer> itemAddrsList=new ArrayList<Integer>();
		
		String itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.upperlimit,t.lowerlimit,t.hystersis,t.delay,"
				+ "t4.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效') "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3, tbl_code t4 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid and upper(t4.itemcode)=upper('BJJB') and t.alarmlevel=t4.itemvalue "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.itemaddr";
		
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.upperlimit,t.lowerlimit,t.hystersis,t.delay,"
					+ "t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效') "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_code t3 "
					+ " where t.unitid=t2.id and upper(t3.itemcode)=upper('BJJB') and t.alarmlevel=t3.itemvalue "
					+ " and t2.id="+id+" "
					+ " and t.type="+resolutionMode
					+ " order by t.itemaddr";
		}
		
		List<?> list=this.findCallSql(itemsSql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":"+(i+1)+","
					+ "\"title\":\""+obj[1]+"\","
					+ "\"code\":\""+obj[2]+"\","
					+ "\"addr\":\""+obj[3]+"\","
					+ "\"upperLimit\":\""+obj[4]+"\","
					+ "\"lowerLimit\":\""+obj[5]+"\","
					+ "\"hystersis\":\""+obj[6]+"\","
					+ "\"delay\":\""+obj[7]+"\","
					+ "\"alarmLevel\":\""+obj[8]+"\","
					+ "\"alarmSign\":\""+obj[9]+"\"},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolAlarmInstanceSwitchItemsConfigData(String id,String classes,String resolutionMode){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"位\",\"dataIndex\":\"bitIndex\",width:80 ,children:[] },"
				+ "{ \"header\":\"含义\",\"dataIndex\":\"meaning\",width:80 ,children:[] },"
				+ "{ \"header\":\"触发状态\",\"dataIndex\":\"value\",width:80 ,children:[] },"
				+ "{ \"header\":\"延时(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警级别\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警开关\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		List<Integer> itemAddrsList=new ArrayList<Integer>();
		
		String itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.bitIndex,t.value,t.delay,"
				+ " t4.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效'),t2.protocol "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3, tbl_code t4 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid and upper(t4.itemcode)=upper('BJJB') and t.alarmlevel=t4.itemvalue "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.itemaddr,t.bitindex";
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.bitIndex,t.value,t.delay,"
					+ " t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效'),t2.protocol "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2, tbl_code t3 "
					+ " where t.unitid=t2.id and upper(t3.itemcode)=upper('BJJB') and t.alarmlevel=t3.itemvalue "
					+ " and t2.id="+id+" "
					+ " and t.type="+resolutionMode
					+ " order by t.itemaddr,t.bitindex";
		}
		List<?> list=this.findCallSql(itemsSql);
		String protocolName="";
		Protocol protocol=null;
		if(list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			protocolName=obj[obj.length-1]+"";
			for (int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(protocolName.equals(modbusProtocolConfig.getProtocol().get(i).getName())){
					protocol=modbusProtocolConfig.getProtocol().get(i);
					break;
				}
			}
		}
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			int itemAddr=StringManagerUtils.stringToInteger(obj[3]+"");
			int bitIndex=StringManagerUtils.stringToInteger(obj[4]+"");
			String meaning="";
			for(int j=0;protocol!=null&&j<protocol.getItems().size();j++){
				if(itemAddr==protocol.getItems().get(j).getAddr()&&protocol.getItems().get(j).getMeaning()!=null&&protocol.getItems().get(j).getMeaning().size()>0){
					for(int k=0;k<protocol.getItems().get(j).getMeaning().size();k++){
						if(bitIndex==protocol.getItems().get(j).getMeaning().get(k).getValue()){
							meaning=protocol.getItems().get(j).getMeaning().get(k).getMeaning();
							break;
						}
					}
					break;
				}
			}
			result_json.append("{\"id\":"+(i+1)+","
					+ "\"title\":\""+obj[1]+"\","
					+ "\"code\":\""+obj[2]+"\","
					+ "\"addr\":\""+obj[3]+"\","
					+ "\"bitIndex\":\""+obj[4]+"\","
					+ "\"meaning\":\""+meaning+"\","
					+ "\"value\":\""+("1".equalsIgnoreCase(obj[5]+"")?"开":"关")+"\","
					+ "\"delay\":\""+obj[6]+"\","
					+ "\"alarmLevel\":\""+obj[7]+"\","
					+ "\"alarmSign\":\""+obj[8]+"\"},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolAlarmInstanceEnumItemsConfigData(String id,String classes,String resolutionMode){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"值\",\"dataIndex\":\"value\",width:80 ,children:[] },"
				+ "{ \"header\":\"含义\",\"dataIndex\":\"meaning\",width:80 ,children:[] },"
				+ "{ \"header\":\"延时(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警级别\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警开关\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		List<Integer> itemAddrsList=new ArrayList<Integer>();
		
		String itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.value,t.delay,"
				+ " t4.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效'),t2.protocol "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3, tbl_code t4 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid and upper(t4.itemcode)=upper('BJJB') and t.alarmlevel=t4.itemvalue "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.itemaddr,t.bitindex";
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.value,t.delay,"
					+ " t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效'),t2.protocol "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2, tbl_code t3 "
					+ " where t.unitid=t2.id and upper(t3.itemcode)=upper('BJJB') and t.alarmlevel=t3.itemvalue "
					+ " and t2.id="+id+" "
					+ " and t.type="+resolutionMode
					+ " order by t.itemaddr,t.bitindex";
		}
		List<?> list=this.findCallSql(itemsSql);
		String protocolName="";
		Protocol protocol=null;
		if(list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			protocolName=obj[obj.length-1]+"";
			for (int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(protocolName.equals(modbusProtocolConfig.getProtocol().get(i).getName())){
					protocol=modbusProtocolConfig.getProtocol().get(i);
					break;
				}
			}
		}
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			int itemAddr=StringManagerUtils.stringToInteger(obj[3]+"");
			int value=StringManagerUtils.stringToInteger(obj[4]+"");
			String meaning="";
			for(int j=0;protocol!=null&&j<protocol.getItems().size();j++){
				if(itemAddr==protocol.getItems().get(j).getAddr()&&protocol.getItems().get(j).getMeaning()!=null&&protocol.getItems().get(j).getMeaning().size()>0){
					for(int k=0;k<protocol.getItems().get(j).getMeaning().size();k++){
						if(value==protocol.getItems().get(j).getMeaning().get(k).getValue()){
							meaning=protocol.getItems().get(j).getMeaning().get(k).getMeaning();
							break;
						}
					}
					break;
				}
			}
			result_json.append("{\"id\":"+(i+1)+","
					+ "\"title\":\""+obj[1]+"\","
					+ "\"code\":\""+obj[2]+"\","
					+ "\"addr\":\""+obj[3]+"\","
					+ "\"value\":\""+obj[4]+"\","
					+ "\"meaning\":\""+meaning+"\","
					+ "\"delay\":\""+obj[5]+"\","
					+ "\"alarmLevel\":\""+obj[6]+"\","
					+ "\"alarmSign\":\""+obj[7]+"\"},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getAcquisitionUnitTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer pumpTree_json = new StringBuffer();
		StringBuffer pipelineTree_json = new StringBuffer();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		
		pumpTree_json.append("[");
		pipelineTree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			String unitSql="select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark,t.protocol from tbl_acq_unit_conf t order by t.protocol,t.id";
			String groupSql="select t3.id,t3.group_code,t3.group_name,t3.acq_cycle,t3.save_cycle,t3.remark,t3.protocol,t3.type,t2.id as unitId "
					+ " from TBL_ACQ_GROUP2UNIT_CONF t,tbl_acq_unit_conf t2,tbl_acq_group_conf t3 "
					+ " where t.unitid=t2.id and t.groupid=t3.id "
					+ " order by t3.protocol,t2.unit_code,t3.type,t3.id";
			List<?> unitList=this.findCallSql(unitSql);
			List<?> groupList=this.findCallSql(groupSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(modbusProtocolConfig.getProtocol().get(i).getDeviceType()==0){
					pumpTree_json.append("{\"classes\":1,");
					pumpTree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					pumpTree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					pumpTree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
					pumpTree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					pumpTree_json.append("\"iconCls\": \"protocol\",");
					pumpTree_json.append("\"expanded\": true,");
					pumpTree_json.append("\"children\": [");
					for(int j=0;j<unitList.size();j++){
						Object[] unitObj = (Object[]) unitList.get(j);
						if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[unitObj.length-1]+"")){
							pumpTree_json.append("{\"classes\":2,");
							pumpTree_json.append("\"id\":"+unitObj[0]+",");
							pumpTree_json.append("\"code\":\""+unitObj[1]+"\",");
							pumpTree_json.append("\"text\":\""+unitObj[2]+"\",");
							pumpTree_json.append("\"remark\":\""+unitObj[3]+"\",");
							pumpTree_json.append("\"protocol\":\""+unitObj[4]+"\",");
							pumpTree_json.append("\"iconCls\": \"acqUnit\",");
							pumpTree_json.append("\"expanded\": true,");
							pumpTree_json.append("\"children\": [");
							for(int k=0;k<groupList.size();k++){
								Object[] groupObj = (Object[]) groupList.get(k);
								if((unitObj[0]+"").equalsIgnoreCase(groupObj[groupObj.length-1]+"")){
									pumpTree_json.append("{\"classes\":3,");
									pumpTree_json.append("\"id\":"+groupObj[0]+",");
									pumpTree_json.append("\"code\":\""+groupObj[1]+"\",");
									pumpTree_json.append("\"text\":\""+groupObj[2]+"\",");
									pumpTree_json.append("\"acq_cycle\":\""+groupObj[3]+"\",");
									pumpTree_json.append("\"save_cycle\":\""+groupObj[4]+"\",");
									pumpTree_json.append("\"remark\":\""+groupObj[5]+"\",");
									pumpTree_json.append("\"protocol\":\""+groupObj[6]+"\",");
									pumpTree_json.append("\"type\":"+groupObj[7]+",");
									pumpTree_json.append("\"typeName\":\""+("0".equalsIgnoreCase(groupObj[7]+"")?"采集组":"控制组")+"\",");
									pumpTree_json.append("\"unitId\":"+groupObj[groupObj.length-1]+",");
									pumpTree_json.append("\"iconCls\": \"acqGroup\",");
									pumpTree_json.append("\"leaf\": true");
									pumpTree_json.append("},");
								}
							}
							if(pumpTree_json.toString().endsWith(",")){
								pumpTree_json.deleteCharAt(pumpTree_json.length() - 1);
							}
							
							pumpTree_json.append("]},");
						}
					}
					if(pumpTree_json.toString().endsWith(",")){
						pumpTree_json.deleteCharAt(pumpTree_json.length() - 1);
					}
					pumpTree_json.append("]},");
				}else{
					pipelineTree_json.append("{\"classes\":1,");
					pipelineTree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					pipelineTree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					pipelineTree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
					pipelineTree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					pipelineTree_json.append("\"iconCls\": \"protocol\",");
					pipelineTree_json.append("\"expanded\": true,");
					pipelineTree_json.append("\"children\": [");
					for(int j=0;j<unitList.size();j++){
						Object[] unitObj = (Object[]) unitList.get(j);
						if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[unitObj.length-1]+"")){
							pipelineTree_json.append("{\"classes\":2,");
							pipelineTree_json.append("\"id\":"+unitObj[0]+",");
							pipelineTree_json.append("\"code\":\""+unitObj[1]+"\",");
							pipelineTree_json.append("\"text\":\""+unitObj[2]+"\",");
							pipelineTree_json.append("\"remark\":\""+unitObj[3]+"\",");
							pipelineTree_json.append("\"protocol\":\""+unitObj[4]+"\",");
							pipelineTree_json.append("\"iconCls\": \"acqUnit\",");
							pipelineTree_json.append("\"expanded\": true,");
							pipelineTree_json.append("\"children\": [");
							for(int k=0;k<groupList.size();k++){
								Object[] groupObj = (Object[]) groupList.get(k);
								if((unitObj[0]+"").equalsIgnoreCase(groupObj[groupObj.length-1]+"")){
									pipelineTree_json.append("{\"classes\":3,");
									pipelineTree_json.append("\"id\":"+groupObj[0]+",");
									pipelineTree_json.append("\"code\":\""+groupObj[1]+"\",");
									pipelineTree_json.append("\"text\":\""+groupObj[2]+"\",");
									pipelineTree_json.append("\"acq_cycle\":\""+groupObj[3]+"\",");
									pipelineTree_json.append("\"save_cycle\":\""+groupObj[4]+"\",");
									pipelineTree_json.append("\"remark\":\""+groupObj[5]+"\",");
									pipelineTree_json.append("\"protocol\":\""+groupObj[6]+"\",");
									pipelineTree_json.append("\"type\":"+groupObj[7]+",");
									pipelineTree_json.append("\"typeName\":\""+("0".equalsIgnoreCase(groupObj[7]+"")?"采集组":"控制组")+"\",");
									pipelineTree_json.append("\"unitId\":"+groupObj[groupObj.length-1]+",");
									pipelineTree_json.append("\"iconCls\": \"acqGroup\",");
									pipelineTree_json.append("\"leaf\": true");
									pipelineTree_json.append("},");
								}
							}
							if(pipelineTree_json.toString().endsWith(",")){
								pipelineTree_json.deleteCharAt(pipelineTree_json.length() - 1);
							}
							
							pipelineTree_json.append("]},");
						}
					}
					if(pipelineTree_json.toString().endsWith(",")){
						pipelineTree_json.deleteCharAt(pipelineTree_json.length() - 1);
					}
					pipelineTree_json.append("]},");
				}
			}
		}
		if(pumpTree_json.toString().endsWith(",")){
			pumpTree_json.deleteCharAt(pumpTree_json.length() - 1);
		}
		pumpTree_json.append("]");
		
		if(pipelineTree_json.toString().endsWith(",")){
			pipelineTree_json.deleteCharAt(pipelineTree_json.length() - 1);
		}
		pipelineTree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"泵设备\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pumpTree_json+"},");
		result_json.append("{\"classes\":0,\"text\":\"管设备\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pipelineTree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	
	public String modbusProtocolAddrMappingTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer pumpTree_json = new StringBuffer();
		StringBuffer pipelineTree_json = new StringBuffer();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		
		pumpTree_json.append("[");
		pipelineTree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(modbusProtocolConfig.getProtocol().get(i).getDeviceType()==0){
					pumpTree_json.append("{\"classes\":1,");
					pumpTree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					pumpTree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					pumpTree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
					pumpTree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					pumpTree_json.append("\"iconCls\": \"protocol\",");
					pumpTree_json.append("\"leaf\": true");
					pumpTree_json.append("},");
				}else{
					pipelineTree_json.append("{\"classes\":1,");
					pipelineTree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					pipelineTree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					pipelineTree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
					pipelineTree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					pipelineTree_json.append("\"iconCls\": \"protocol\",");
					pipelineTree_json.append("\"leaf\": true");
					pipelineTree_json.append("},");
				}
			}
		}
		if(pumpTree_json.toString().endsWith(",")){
			pumpTree_json.deleteCharAt(pumpTree_json.length() - 1);
		}
		pumpTree_json.append("]");
		
		if(pipelineTree_json.toString().endsWith(",")){
			pipelineTree_json.deleteCharAt(pipelineTree_json.length() - 1);
		}
		pipelineTree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"泵设备\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pumpTree_json+"},");
		result_json.append("{\"classes\":0,\"text\":\"管设备\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pipelineTree_json+"}");
		result_json.append("]");
		return result_json.toString();
	}
	
	public String modbusProtocolAndAcqUnitTreeData(String deviceTypeStr){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		
		int deviceType=0;
		if(StringManagerUtils.stringToInteger(deviceTypeStr)>0){
			deviceType=1;
		}
		
		result_json.append("[");
		if(modbusProtocolConfig!=null){
			String unitSql="select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark,t.protocol from tbl_acq_unit_conf t order by t.protocol,t.id";
			List<?> unitList=this.findCallSql(unitSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(modbusProtocolConfig.getProtocol().get(i).getDeviceType()==deviceType){
					result_json.append("{\"classes\":1,");
					result_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					result_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					result_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					result_json.append("\"iconCls\": \"protocol\",");
					result_json.append("\"expanded\": true,");
					result_json.append("\"disabled\": true,");
					result_json.append("\"children\": [");
					for(int j=0;j<unitList.size();j++){
						Object[] unitObj = (Object[]) unitList.get(j);
						if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[unitObj.length-1]+"")){
							result_json.append("{\"classes\":2,");
							result_json.append("\"id\":"+unitObj[0]+",");
							result_json.append("\"code\":\""+unitObj[1]+"\",");
							result_json.append("\"text\":\""+unitObj[2]+"\",");
							result_json.append("\"remark\":\""+unitObj[3]+"\",");
							result_json.append("\"protocol\":\""+unitObj[4]+"\",");
							result_json.append("\"iconCls\": \"acqUnit\",");
							result_json.append("\"leaf\": true},");
						}
					}
					if(result_json.toString().endsWith(",")){
						result_json.deleteCharAt(result_json.length() - 1);
					}
					result_json.append("]},");
				}
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]");
		return result_json.toString();
	}
	
	
	public String modbusProtocolAndAlarmUnitTreeData(String deviceTypeStr){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		
		int deviceType=0;
		if(StringManagerUtils.stringToInteger(deviceTypeStr)>0){
			deviceType=1;
		}
		
		result_json.append("[");
		if(modbusProtocolConfig!=null){
			String alarmUnitSql="select t.id,t.unit_code,t.unit_name,t.remark,t.protocol from tbl_alarm_unit_conf t order by t.protocol,t.id";
			List<?> alarmUnitList=this.findCallSql(alarmUnitSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(modbusProtocolConfig.getProtocol().get(i).getDeviceType()==deviceType){
					result_json.append("{\"classes\":1,");
					result_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					result_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					result_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					result_json.append("\"iconCls\": \"protocol\",");
					result_json.append("\"expanded\": true,");
					result_json.append("\"disabled\": true,");
					result_json.append("\"children\": [");
					for(int j=0;j<alarmUnitList.size();j++){
						Object[] unitObj = (Object[]) alarmUnitList.get(j);
						if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[unitObj.length-1]+"")){
							result_json.append("{\"classes\":2,");
							result_json.append("\"id\":"+unitObj[0]+",");
							result_json.append("\"code\":\""+unitObj[1]+"\",");
							result_json.append("\"text\":\""+unitObj[2]+"\",");
							result_json.append("\"remark\":\""+unitObj[3]+"\",");
							result_json.append("\"protocol\":\""+unitObj[4]+"\",");
							result_json.append("\"iconCls\": \"acqGroup\",");
							result_json.append("\"leaf\": true},");
						}
					}
					if(result_json.toString().endsWith(",")){
						result_json.deleteCharAt(result_json.length() - 1);
					}
					result_json.append("]},");
				}
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]");
		return result_json.toString();
	}
	
	
	public String modbusProtocolAlarmUnitTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer pumpTree_json = new StringBuffer();
		StringBuffer pipelineTree_json = new StringBuffer();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		
		pumpTree_json.append("[");
		pipelineTree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			String unitSql="select t.id,t.unit_code,t.unit_name,t.remark,t.protocol"
					+ " from tbl_alarm_unit_conf t "
					+ " where 1=1 "
					+ " order by t.protocol,t.unit_code";
			List<?> unitList=this.findCallSql(unitSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(modbusProtocolConfig.getProtocol().get(i).getDeviceType()==0){
					pumpTree_json.append("{\"classes\":1,");
					pumpTree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					pumpTree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					pumpTree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
					pumpTree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					pumpTree_json.append("\"iconCls\": \"protocol\",");
					pumpTree_json.append("\"expanded\": true,");
					pumpTree_json.append("\"children\": [");
					for(int j=0;j<unitList.size();j++){
						Object[] unitObj = (Object[]) unitList.get(j);
						if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[unitObj.length-1]+"")){
							pumpTree_json.append("{\"classes\":3,");
							pumpTree_json.append("\"id\":"+unitObj[0]+",");
							pumpTree_json.append("\"code\":\""+unitObj[1]+"\",");
							pumpTree_json.append("\"text\":\""+unitObj[2]+"\",");
							pumpTree_json.append("\"remark\":\""+unitObj[3]+"\",");
							pumpTree_json.append("\"protocol\":\""+unitObj[4]+"\",");
							pumpTree_json.append("\"iconCls\": \"acqGroup\",");
							pumpTree_json.append("\"leaf\": true");
							pumpTree_json.append("},");
						}
					}
					if(pumpTree_json.toString().endsWith(",")){
						pumpTree_json.deleteCharAt(pumpTree_json.length() - 1);
					}
					pumpTree_json.append("]},");
				}else{
					pipelineTree_json.append("{\"classes\":1,");
					pipelineTree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					pipelineTree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					pipelineTree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
					pipelineTree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					pipelineTree_json.append("\"iconCls\": \"protocol\",");
					pipelineTree_json.append("\"expanded\": true,");
					pipelineTree_json.append("\"children\": [");
					for(int j=0;j<unitList.size();j++){
						Object[] unitObj = (Object[]) unitList.get(j);
						if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[unitObj.length-1]+"")){
							pipelineTree_json.append("{\"classes\":3,");
							pipelineTree_json.append("\"id\":"+unitObj[0]+",");
							pipelineTree_json.append("\"code\":\""+unitObj[1]+"\",");
							pipelineTree_json.append("\"text\":\""+unitObj[2]+"\",");
							pipelineTree_json.append("\"remark\":\""+unitObj[3]+"\",");
							pipelineTree_json.append("\"protocol\":\""+unitObj[4]+"\",");
							pipelineTree_json.append("\"iconCls\": \"acqGroup\",");
							pipelineTree_json.append("\"leaf\": true");
							pipelineTree_json.append("},");
						}
					}
					if(pipelineTree_json.toString().endsWith(",")){
						pipelineTree_json.deleteCharAt(pipelineTree_json.length() - 1);
					}
					pipelineTree_json.append("]},");
				}
			}
		}
		if(pumpTree_json.toString().endsWith(",")){
			pumpTree_json.deleteCharAt(pumpTree_json.length() - 1);
		}
		pumpTree_json.append("]");
		
		if(pipelineTree_json.toString().endsWith(",")){
			pipelineTree_json.deleteCharAt(pipelineTree_json.length() - 1);
		}
		pipelineTree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"泵设备\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pumpTree_json+"},");
		result_json.append("{\"classes\":0,\"text\":\"管设备\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pipelineTree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusProtocolInstanceConfigTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer pumpTree_json = new StringBuffer();
		StringBuffer pipelineTree_json = new StringBuffer();
		pumpTree_json.append("[");
		pipelineTree_json.append("[");
		String sql="select t.id,t.name,t.code,t.acqprotocoltype,t.ctrlprotocoltype,t.signinprefix,t.signinsuffix,t.heartbeatprefix,t.heartbeatsuffix,"
				+ " t.devicetype,t.sort,t.unitid,t2.unit_name "
				+ " from tbl_protocolinstance t ,tbl_acq_unit_conf t2 where t.unitid=t2.id"
				+ " order by t.devicetype,t.sort";
		String groupSql="select t.id,t.group_name,t.group_code,t2.unitid "
				+ " from tbl_acq_group_conf t,tbl_acq_group2unit_conf t2 "
				+ " where t.id=t2.groupid "
				+ " order by t2.unitid,t.type";
		List<?> list=this.findCallSql(sql);
		List<?> groupList=this.findCallSql(groupSql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			if(StringManagerUtils.stringToInteger(obj[9]+"")==0){
				pumpTree_json.append("{\"classes\":1,");
				pumpTree_json.append("\"id\":\""+obj[0]+"\",");
				pumpTree_json.append("\"text\":\""+obj[1]+"\",");
				pumpTree_json.append("\"code\":\""+obj[2]+"\",");
				pumpTree_json.append("\"acqProtocolType\":\""+obj[3]+"\",");
				pumpTree_json.append("\"ctrlProtocolType\":\""+obj[4]+"\",");
				pumpTree_json.append("\"signInPrefix\":\""+obj[5]+"\",");
				pumpTree_json.append("\"signInSuffix\":\""+obj[6]+"\",");
				pumpTree_json.append("\"heartbeatPrefix\":\""+obj[7]+"\",");
				pumpTree_json.append("\"heartbeatSuffix\":\""+obj[8]+"\",");
				pumpTree_json.append("\"deviceType\":"+obj[9]+",");
				pumpTree_json.append("\"sort\":\""+obj[10]+"\",");
				pumpTree_json.append("\"unitId\":"+obj[11]+",");
				pumpTree_json.append("\"unitName\":\""+obj[12]+"\",");
				pumpTree_json.append("\"iconCls\": \"protocol\",");

				pumpTree_json.append("\"expanded\": true,");
				pumpTree_json.append("\"children\": [");
				pumpTree_json.append("{\"classes\":2,");
				pumpTree_json.append("\"text\":\""+obj[12]+"\",");
				pumpTree_json.append("\"id\":"+obj[11]+",");
				pumpTree_json.append("\"iconCls\": \"acqUnit\","); 
				
				pumpTree_json.append("\"expanded\": true,");
				pumpTree_json.append("\"children\": [");
				for(int j=0;j<groupList.size();j++){
					Object[] groupObj = (Object[]) groupList.get(j);
					if((obj[11]+"").equalsIgnoreCase(groupObj[3]+"")){
						pumpTree_json.append("{\"classes\":3,");
						pumpTree_json.append("\"id\":"+groupObj[0]+",");
						pumpTree_json.append("\"text\":\""+groupObj[1]+"\",");
						pumpTree_json.append("\"code\":\""+groupObj[2]+"\",");
						pumpTree_json.append("\"iconCls\": \"acqGroup\","); 
						pumpTree_json.append("\"leaf\": true},");
					}
				}
				if(pumpTree_json.toString().endsWith(",")){
					pumpTree_json.deleteCharAt(pumpTree_json.length() - 1);
				}
				
				pumpTree_json.append("]");
				
				pumpTree_json.append("}]");
				
				pumpTree_json.append("},");
			}else{
				pipelineTree_json.append("{\"classes\":1,");
				pipelineTree_json.append("\"id\":\""+obj[0]+"\",");
				pipelineTree_json.append("\"text\":\""+obj[1]+"\",");
				pipelineTree_json.append("\"code\":\""+obj[2]+"\",");
				pipelineTree_json.append("\"acqProtocolType\":\""+obj[3]+"\",");
				pipelineTree_json.append("\"ctrlProtocolType\":\""+obj[4]+"\",");
				pipelineTree_json.append("\"signInPrefix\":\""+obj[5]+"\",");
				pipelineTree_json.append("\"signInSuffix\":\""+obj[6]+"\",");
				pipelineTree_json.append("\"heartbeatPrefix\":\""+obj[7]+"\",");
				pipelineTree_json.append("\"heartbeatSuffix\":\""+obj[8]+"\",");
				pipelineTree_json.append("\"deviceType\":"+obj[9]+",");
				pipelineTree_json.append("\"sort\":\""+obj[10]+"\",");
				pipelineTree_json.append("\"unitId\":"+obj[11]+",");
				pipelineTree_json.append("\"unitName\":\""+obj[12]+"\",");
				pipelineTree_json.append("\"iconCls\": \"protocol\",");
				pipelineTree_json.append("\"expanded\": true,");
				
				pipelineTree_json.append("\"children\": [");
				pipelineTree_json.append("{\"classes\":2,");
				pipelineTree_json.append("\"text\":\""+obj[12]+"\",");
				pipelineTree_json.append("\"id\":"+obj[11]+",");
				pipelineTree_json.append("\"iconCls\": \"acqUnit\","); 
				pipelineTree_json.append("\"expanded\": true,");
				pipelineTree_json.append("\"children\": [");
				for(int j=0;j<groupList.size();j++){
					Object[] groupObj = (Object[]) groupList.get(j);
					if((obj[11]+"").equalsIgnoreCase(groupObj[3]+"")){
						pipelineTree_json.append("{\"classes\":3,");
						pipelineTree_json.append("\"id\":"+groupObj[0]+",");
						pipelineTree_json.append("\"text\":\""+groupObj[1]+"\",");
						pipelineTree_json.append("\"code\":\""+groupObj[2]+"\",");
						pipelineTree_json.append("\"iconCls\": \"acqGroup\","); 
						pipelineTree_json.append("\"leaf\": true},");
					}
				}
				if(pipelineTree_json.toString().endsWith(",")){
					pipelineTree_json.deleteCharAt(pipelineTree_json.length() - 1);
				}
				
				pipelineTree_json.append("]");
				pipelineTree_json.append("}]");
				
				pipelineTree_json.append("},");
			}
		}
		
		if(pumpTree_json.toString().endsWith(",")){
			pumpTree_json.deleteCharAt(pumpTree_json.length() - 1);
		}
		pumpTree_json.append("]");
		
		if(pipelineTree_json.toString().endsWith(",")){
			pipelineTree_json.deleteCharAt(pipelineTree_json.length() - 1);
		}
		pipelineTree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"泵设备\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pumpTree_json+"},");
		result_json.append("{\"classes\":0,\"text\":\"管设备\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pipelineTree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusAlarmProtocolInstanceConfigTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer pumpTree_json = new StringBuffer();
		StringBuffer pipelineTree_json = new StringBuffer();
		pumpTree_json.append("[");
		pipelineTree_json.append("[");
		String sql="select t.id,t.name,t.code,t.alarmUnitId,t2.unit_name,t.devicetype,t.sort   "
				+ " from tbl_protocolalarminstance t ,tbl_alarm_unit_conf t2 where t.alarmunitid=t2.id "
				+ " order by t.devicetype,t.sort";
		List<?> list=this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			if(StringManagerUtils.stringToInteger(obj[5]+"")==0){
				pumpTree_json.append("{\"classes\":1,");
				pumpTree_json.append("\"id\":\""+obj[0]+"\",");
				pumpTree_json.append("\"text\":\""+obj[1]+"\",");
				pumpTree_json.append("\"code\":\""+obj[2]+"\",");
				pumpTree_json.append("\"alarmUnitId\":"+obj[3]+",");
				pumpTree_json.append("\"alarmUnitName\":\""+obj[4]+"\",");
				pumpTree_json.append("\"deviceType\":"+obj[5]+",");
				pumpTree_json.append("\"sort\":\""+obj[6]+"\",");
				pumpTree_json.append("\"iconCls\": \"protocol\",");
				pumpTree_json.append("\"expanded\": true,");
				pumpTree_json.append("\"children\": [");
				pumpTree_json.append("{\"classes\":2,");
				pumpTree_json.append("\"id\":"+obj[3]+",");
				pumpTree_json.append("\"text\":\""+obj[4]+"\",");
				pumpTree_json.append("\"iconCls\": \"acqGroup\","); 
				pumpTree_json.append("\"leaf\": true}");
				pumpTree_json.append("]");
				
				
				
				pumpTree_json.append("},");
			}else{
				pipelineTree_json.append("{\"classes\":1,");
				pipelineTree_json.append("\"id\":\""+obj[0]+"\",");
				pipelineTree_json.append("\"text\":\""+obj[1]+"\",");
				pipelineTree_json.append("\"code\":\""+obj[2]+"\",");
				pipelineTree_json.append("\"alarmUnitId\":"+obj[3]+",");
				pipelineTree_json.append("\"alarmUnitName\":\""+obj[4]+"\",");
				pipelineTree_json.append("\"deviceType\":"+obj[5]+",");
				pipelineTree_json.append("\"sort\":\""+obj[6]+"\",");
				pipelineTree_json.append("\"iconCls\": \"protocol\",");
				pipelineTree_json.append("\"expanded\": true,");
				pipelineTree_json.append("\"children\": [");
				pipelineTree_json.append("{\"classes\":2,");
				pipelineTree_json.append("\"id\":"+obj[3]+",");
				pipelineTree_json.append("\"text\":\""+obj[4]+"\",");
				pipelineTree_json.append("\"iconCls\": \"acqGroup\","); 
				pipelineTree_json.append("\"leaf\": true}");
				pipelineTree_json.append("]");
				
				pipelineTree_json.append("},");
			}
		}
		
		if(pumpTree_json.toString().endsWith(",")){
			pumpTree_json.deleteCharAt(pumpTree_json.length() - 1);
		}
		pumpTree_json.append("]");
		
		if(pipelineTree_json.toString().endsWith(",")){
			pipelineTree_json.deleteCharAt(pipelineTree_json.length() - 1);
		}
		pipelineTree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"泵设备\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pumpTree_json+"},");
		result_json.append("{\"classes\":0,\"text\":\"管设备\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pipelineTree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusProtoclCombList(){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
		//排序
		Collections.sort(modbusProtocolConfig.getProtocol());
		
		result_json.append("{\"totals\":"+modbusProtocolConfig.getProtocol().size()+",\"list\":[");
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			result_json.append("{boxkey:\"" + modbusProtocolConfig.getProtocol().get(i).getName() + "\",");
			result_json.append("boxval:\"" + modbusProtocolConfig.getProtocol().get(i).getName() + "\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getSMSInstanceList(String name,Page pager) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		
//		String ddicName="pumpRealTimeOverview";
//		DataDictionary ddic = null;
//		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
//		String columns = ddic.getTableHeader();
		
		String columns = "["
		+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
		+ "{ \"header\":\"实例名称\",\"dataIndex\":\"name\" ,children:[] },"
		+ "{ \"header\":\"采集协议类型\",\"dataIndex\":\"acqProtocolType\" ,children:[] },"
		+ "{ \"header\":\"控制协议类型\",\"dataIndex\":\"ctrlProtocolType\" ,children:[] },"
		+ "{ \"header\":\"排序\",\"dataIndex\":\"sort\",children:[] }"
		+ "]";
		
		String sql="select t.id,t.name,t.code,t.acqprotocoltype,t.ctrlprotocoltype,t.sort from tbl_protocolsmsinstance t where 1=1";
		if(StringManagerUtils.isNotNull(name)){
			sql+=" and t.name like '%"+name+"%'";
		}
		sql+=" order by t.sort";
		
		int maxvalue=pager.getLimit()+pager.getStart();
		String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		finalSql=sql;
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"name\":\""+obj[1]+"\",");
			result_json.append("\"code\":\""+obj[2]+"\",");
			result_json.append("\"acqProtocolType\":\""+obj[3]+"\",");
			result_json.append("\"ctrlProtocolType\":\""+obj[4]+"\",");
			result_json.append("\"sort\":\""+obj[5]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	
	
	public String getAcquisitionUnitCombList(String protocol){
		StringBuffer result_json = new StringBuffer();
		String sql="select t.id,t.unit_name from TBL_ACQ_UNIT_CONF t where 1=1";
		if(StringManagerUtils.isNotNull(protocol)){
			sql+=" and t.protocol='"+protocol+"'";
		}
		
		List<?> list=this.findCallSql(sql);
		result_json.append("{\"totals\":"+list.size()+",\"list\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{boxkey:\"" + obj[0] + "\",");
			result_json.append("boxval:\"" + obj[1] + "\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getDatabaseColumnMappingTable(String deviceType) {
		StringBuffer result_json = new StringBuffer();
		String sql="select t.id,t.name,t.mappingcolumn from tbl_datamapping t where t.protocoltype="+deviceType+" order by t.id";
		String columns="["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"itemName\" ,children:[] },"
				+ "{ \"header\":\"字段\",\"dataIndex\":\"itemColumn\",children:[] }"
				+ "]";
		List<?> list=this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":" + list.size() + ",\"columns\":"+columns+",\"totalRoot\":[");
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"itemName\":\""+obj[1]+"\",");
			result_json.append("\"itemColumn\":\""+obj[2]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public boolean judgeProtocolExistOrNot(int deviceType,String protocolName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(protocolName)) {
			Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
			if(equipmentDriveMap.size()==0){
				EquipmentDriverServerTask.loadProtocolConfig();
				equipmentDriveMap = EquipmentDriveMap.getMapObject();
			}
			ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(modbusProtocolConfig.getProtocol().get(i).getDeviceType()==deviceType && protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	public boolean judgeAcqUnitExistOrNot(String protocolName,String unitName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(protocolName)&&StringManagerUtils.isNotNull(unitName)) {
			String sql = "select t.id from tbl_acq_unit_conf t where t.protocol='"+protocolName+"' and t.unit_name='"+unitName+"'";
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgeAcqGroupExistOrNot(String protocolName,String unitName,String groupName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(protocolName)&&StringManagerUtils.isNotNull(unitName)) {
			String sql = "select t.id from TBL_ACQ_GROUP_CONF t,tbl_acq_group2unit_conf t2,tbl_acq_unit_conf t3 "
					+ " where t.id=t2.groupid and t2.unitid=t3.id "
					+ " and t3.protocol='"+protocolName+"' and t3.unit_name='"+unitName+"' and t.group_name='"+groupName+"'";
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgeAlarmUnitExistOrNot(String protocolName,String unitName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(protocolName)&&StringManagerUtils.isNotNull(unitName)) {
			String sql = "select t.id from TBL_ALARM_UNIT_CONF t where t.protocol='"+protocolName+"' and t.unit_name='"+unitName+"'";
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgeInstanceExistOrNot(int deviceType,String instanceName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(instanceName)) {
			String sql = "select t.id from TBL_PROTOCOLINSTANCE t where t.devicetype="+deviceType+" and t.name='"+instanceName+"'";
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgeAlarmInstanceExistOrNot(int deviceType,String instanceName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(instanceName)) {
			String sql = "select t.id from TBL_PROTOCOLALARMINSTANCE t where t.devicetype="+deviceType+" and t.name='"+instanceName+"'";
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public void doAcquisitionGroupAdd(AcquisitionGroup acquisitionGroup) throws Exception {
		getBaseDao().addObject(acquisitionGroup);
	}
	
	public void doAcquisitionGroupEdit(AcquisitionGroup acquisitionGroup) throws Exception {
		getBaseDao().updateObject(acquisitionGroup);
	}
	
	public void doAcquisitionGroupBulkDelete(final String ids) throws Exception {
		int delorUpdateCount;
		String sql="delete from TBL_ACQ_ITEM2GROUP_CONF t where t.groupid in ("+ids+")";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_acq_group2unit_conf t where t.groupid in("+ids+")";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		final String hql = "DELETE AcquisitionGroup u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}
	
	public void doAcquisitionUnitAdd(T acquisitionUnit) throws Exception {
		getBaseDao().addObject(acquisitionUnit);
	}
	
	public void doAcquisitionUnitEdit(T acquisitionUnit) throws Exception {
		getBaseDao().updateObject(acquisitionUnit);
	}
	
	public void doAcquisitionUnitBulkDelete(final String ids,String deviceType) throws Exception {
		int delorUpdateCount=0;
		String tableName="tbl_pumpdevice";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="tbl_pipelinedevice";
		}
		String sql = "update "+tableName+" t set t.instancecode='' where t.instancecode in ( select t2.code from tbl_protocolinstance t2  where t2.unitid in ("+ids+") )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from TBL_ACQ_ITEM2GROUP_CONF t where t.groupid in (select t2.id from tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3 where t2.id=t3.groupid and t3.unitid in("+ids+"))";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_acq_group_conf t where t.id in ( select t2.groupid from tbl_acq_group2unit_conf t2 where t2.unitid in("+ids+") )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_acq_group2unit_conf t where t.unitid in("+ids+")";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_protocolinstance t where t.unitid in("+ids+")";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		final String hql = "DELETE AcquisitionUnit u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}
	
	public void doDeleteProtocolAssociation(int deviceType,String protocolName) throws Exception {
		int delorUpdateCount=0;
		String tableName="tbl_pumpdevice";
		if(deviceType==1){
			tableName="tbl_pipelinedevice";
		}
		String sql = "update "+tableName+" t set t.instancecode='' where t.instancecode in ( select t2.code from tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t2.unitid=t3.id and t3.protocol='"+protocolName+"' )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from TBL_ACQ_ITEM2GROUP_CONF t where t.groupid in (select t2.id from tbl_acq_group_conf t2 where t2.protocol='"+protocolName+"')";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_acq_group_conf t where t.protocol ='"+protocolName+"'";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_acq_group2unit_conf t where t.unitid in (select t2.id from tbl_acq_unit_conf t2 where t2.protocol='"+protocolName+"')";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_protocolinstance t where t.unitid in (select t2.id from tbl_acq_unit_conf t2 where t2.protocol='"+protocolName+"')";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_acq_unit_conf t where t.protocol ='"+protocolName+"'";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		
		sql = "update "+tableName+" t set t.alarminstancecode='' where t.alarminstancecode in ( select t2.code from tbl_protocolalarminstance t2,tbl_alarm_unit_conf t3 where t2.alarmunitid=t3.id and t3.protocol='"+protocolName+"' )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_alarm_item2unit_conf t where t.unitid in( select id from tbl_alarm_unit_conf t2 where t2.protocol='"+protocolName+"' )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_protocolalarminstance t where t.alarmunitid in( select id from tbl_alarm_unit_conf t2 where t2.protocol='"+protocolName+"' )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_alarm_unit_conf t where t.protocol ='"+protocolName+"'";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
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
	
	public void doModbusProtocolInstanceAdd(T protocolInstance) throws Exception {
		getBaseDao().addObject(protocolInstance);
	}
	public void doModbusProtocolInstanceEdit(T protocolInstance) throws Exception {
		getBaseDao().updateObject(protocolInstance);
	}
	
	public void doModbusProtocolInstanceBulkDelete(final String ids,int deviceType) throws Exception {
		String tableName="tbl_pumpdevice";
		if(deviceType==1){
			tableName="tbl_pipelinedevice";
		}
		String sql = "update "+tableName+" t set t.instancecode='' where t.instancecode in ( select t2.code from TBL_PROTOCOLINSTANCE t2 where t2.id in ("+ids+") )";
		this.getBaseDao().updateOrDeleteBySql(sql);
		
		final String hql = "DELETE ProtocolInstance u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}
	
	public void doAlarmUnitAdd(T doAlarmUnitAdd) throws Exception {
		getBaseDao().addObject(doAlarmUnitAdd);
	}
	
	public void doAlarmUnitEdit(T doAlarmUnitAdd) throws Exception {
		getBaseDao().updateObject(doAlarmUnitAdd);
	}
	
	public void doModbusProtocolAlarmUnitDelete(final String ids) throws Exception {
		final String hql = "DELETE AlarmUnit u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}
	
	public void deleteCurrentAlarmUnitOwnItems(ModbusProtocolAlarmUnitSaveData modbusProtocolAlarmUnitSaveData) throws Exception {
		String hql = "DELETE AlarmUnitItem u where u.unitId ="+modbusProtocolAlarmUnitSaveData.getId()+" and u.type="+modbusProtocolAlarmUnitSaveData.getResolutionMode();
		if(modbusProtocolAlarmUnitSaveData.getResolutionMode()==0 || modbusProtocolAlarmUnitSaveData.getResolutionMode()==1 &&StringManagerUtils.isNotNull(modbusProtocolAlarmUnitSaveData.getAlarmItemName())){
			hql+=" and u.itemName='"+modbusProtocolAlarmUnitSaveData.getAlarmItemName()+"' and u.itemAddr="+modbusProtocolAlarmUnitSaveData.getAlarmItemAddr();
		}
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void grantAlarmItemsPermission(T alarmUnitItem) throws Exception {
		getBaseDao().addObject(alarmUnitItem);
	}
	
	public void doModbusProtocolAlarmInstanceEdit(T protocolAlarmInstance) throws Exception {
		getBaseDao().updateObject(protocolAlarmInstance);
	}
	
	public void doModbusProtocolAlarmInstanceBulkDelete(final String ids) throws Exception {
		final String hql = "DELETE ProtocolAlarmInstance u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}
	
	public void doModbusProtocolAlarmInstanceAdd(T protocolAlarmInstance) throws Exception {
		getBaseDao().addObject(protocolAlarmInstance);
	}
	
	public void doModbusProtocolSMSInstanceAdd(T protocolSMSInstance) throws Exception {
		getBaseDao().addObject(protocolSMSInstance);
	}
	
	public void doModbusProtocolSMSInstanceEdit(ProtocolSMSInstance protocolSMSInstance) throws Exception {
		getBaseDao().updateObject(protocolSMSInstance);
	}
	
	public void doModbusProtocolSMSInstanceDelete(final String ids) throws Exception {
		final String hql = "DELETE ProtocolSMSInstance u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
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
