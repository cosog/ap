package com.cosog.service.acqUnit;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import com.cosog.model.calculate.AcqInstanceOwnItem;
import com.cosog.model.calculate.DisplayInstanceOwnItem;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.RPCDeviceInfo;
import com.cosog.model.calculate.UserInfo;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.drive.KafkaConfig;
import com.cosog.model.drive.ModbusProtocolAlarmUnitSaveData;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.drive.ModbusProtocolConfig.Protocol;
import com.cosog.model.gridmodel.DatabaseMappingProHandsontableChangedData;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.task.MemoryDataManagerTask.CalItem;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.DataSourceConfig;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.Page;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.TcpServerConfigMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import redis.clients.jedis.Jedis;

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
				+ " t.grouptiminginterval as groupTimingInterval,t.groupsavinginterval as groupSavingInterval,t.remark "
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
				+ "{ \"header\":\"单组定时间隔(s)\",\"dataIndex\":\"groupTimingInterval\" ,children:[] },"
				+ "{ \"header\":\"单组入库间隔(s)\",\"dataIndex\":\"groupSavingInterval\" ,children:[] },"
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
			jsonBuffer.append("\"groupTimingInterval\":\""+obj[3]+"\",");
			jsonBuffer.append("\"groupSavingInterval\":\""+obj[4]+"\",");
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
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
//				+ "{ \"header\":\"名称\",\"dataIndex\":\"name\",width:120 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"存储数据数量\",\"dataIndex\":\"quantity\",width:80 ,children:[] },"
				+ "{ \"header\":\"存储数据类型\",\"dataIndex\":\"storeDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\"接口数据类型\",\"dataIndex\":\"IFDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\"小数位数\",\"dataIndex\":\"prec\",width:80 ,children:[] },"
				+ "{ \"header\":\"换算比例\",\"dataIndex\":\"ratio\",width:80 ,children:[] },"
				+ "{ \"header\":\"读写类型\",\"dataIndex\":\"RWType\",width:80 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
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
							+ "\"prec\":"+(protocolConfig.getItems().get(j).getIFDataType().toUpperCase().indexOf("FLOAT")>=0?protocolConfig.getItems().get(j).getPrec():"\"\"")+","
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
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"存储数据数量\",\"dataIndex\":\"quantity\",width:80 ,children:[] },"
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
		List<String> itemsBitIndexList=new ArrayList<String>();
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.bitindex from TBL_ACQ_ITEM2GROUP_CONF t,tbl_acq_group_conf t2 where t.groupid=t2.id and t2.group_code='"+code+"' order by t.id";
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsBitIndexList.add(obj[1]+"");
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
								for(int m=0;m<itemsList.size();m++){
									if(itemsList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())
											&&itemsBitIndexList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"")
										){
										checked=true;
										break;
									}
								}
								
								result_json.append("{"
										+ "\"checked\":"+checked+","
										+ "\"id\":"+(index)+","
										+ "\"title\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
										+ "\"bitIndex\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"\","
										+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+","
										+ "\"quantity\":"+1+","
										+ "\"storeDataType\":\""+protocolConfig.getItems().get(j).getStoreDataType()+"\","
										+ "\"IFDataType\":\""+protocolConfig.getItems().get(j).getIFDataType()+"\","
										+ "\"ratio\":"+protocolConfig.getItems().get(j).getRatio()+","
										+ "\"RWType\":\""+RWType+"\","
										+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
										+ "\"resolutionMode\":\""+resolutionMode+"\","
										+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?"主动上传":"被动响应")+"\""
										+ "},");
								index++;
							}
						}else{
							checked=StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(),false);
//							if(checked){
//								for(int k=0;k<itemsList.size();k++){
//									if(itemsList.get(k).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())){
//										break;
//									}
//								}
//							}
							result_json.append("{\"checked\":"+checked+","
									+ "\"id\":"+(index)+","
									+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
									+ "\"bitIndex\":\"\","
									+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+","
									+ "\"quantity\":"+protocolConfig.getItems().get(j).getQuantity()+","
									+ "\"storeDataType\":\""+protocolConfig.getItems().get(j).getStoreDataType()+"\","
									+ "\"IFDataType\":\""+protocolConfig.getItems().get(j).getIFDataType()+"\","
									+ "\"ratio\":"+protocolConfig.getItems().get(j).getRatio()+","
									+ "\"RWType\":\""+RWType+"\","
									+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
									+ "\"resolutionMode\":\""+resolutionMode+"\","
									+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?"主动上传":"被动响应")+"\""
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
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
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
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
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
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
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
	
	public String getModbusProtocolCalNumAlarmItemsConfigData(String deviceType,String classes,String code){
		StringBuffer result_json = new StringBuffer();
		
		Jedis jedis=null;
		Set<byte[]>calItemSet=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(StringManagerUtils.stringToInteger(deviceType)==0){
				if(!jedis.exists("rpcCalItemList".getBytes())){
					MemoryDataManagerTask.loadRPCCalculateItem();
				}
				calItemSet= jedis.zrange("rpcCalItemList".getBytes(), 0, -1);
			}else{
				if(!jedis.exists("pcpCalItemList".getBytes())){
					MemoryDataManagerTask.loadPCPCalculateItem();
				}
				calItemSet= jedis.zrange("pcpCalItemList".getBytes(), 0, -1);
			}
			String columns = "["
					+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
					+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
					+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:120 ,children:[] },"
					+ "{ \"header\":\"上限\",\"dataIndex\":\"upperLimit\",width:80 ,children:[] },"
					+ "{ \"header\":\"下限\",\"dataIndex\":\"lowerLimit\",width:80 ,children:[] },"
					+ "{ \"header\":\"回差\",\"dataIndex\":\"hystersis\",width:80 ,children:[] },"
					+ "{ \"header\":\"延时(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
					+ "{ \"header\":\"报警级别\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
					+ "{ \"header\":\"报警使能\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
					+ "{ \"header\":\"是否发送短信\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
					+ "{ \"header\":\"是否发送邮件\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
					+ "]";
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"totalRoot\":[");
			if(calItemSet!=null){
				List<String> itemsList=new ArrayList<String>();
				List<?> list=null;
				if("3".equalsIgnoreCase(classes)){
					String sql="select t.itemname,t.itemcode,t.upperlimit,t.lowerlimit,t.hystersis,t.delay,"
							+ " t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效') as alarmsign,"
							+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
							+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_code t3  "
							+ " where t.type=5 and t.unitid=t2.id and upper(t3.itemcode)=upper('BJJB') and t.alarmlevel=t3.itemvalue and t2.unit_code='"+code+"' "
							+ " order by t.id";
					list=this.findCallSql(sql);
					for(int i=0;i<list.size();i++){
						Object[] obj = (Object[]) list.get(i);
						itemsList.add(obj[1]+"");
					}
				}
				
				int index=1;
				for(byte[] rpcCalItemByteArr:calItemSet){
					CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
					if(calItem.getDataType()==2){
						String upperLimit="",lowerLimit="",hystersis="",delay="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
						boolean checked=false;
						for(int k=0;k<itemsList.size();k++){
							Object[] obj = (Object[]) list.get(k);
							if(calItem.getCode().equalsIgnoreCase(itemsList.get(k))){
								checked=true;
								upperLimit=obj[2]+"";
								lowerLimit=obj[3]+"";
								hystersis=obj[4]+"";
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
								+ "\"title\":\""+calItem.getName()+"\","
								+ "\"unit\":\""+calItem.getUnit()+"\","
								+ "\"code\":\""+calItem.getCode()+"\","
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
			}
			
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]");
			result_json.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return result_json.toString();
	}
	
	public String getModbusProtocolEnumAlarmItemsConfigData(String protocolName,String classes,String unitCode,String itemAddr,String itemResolutionMode){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
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
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
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
		
		
		for(int i=0;i<commStatusItemsList.size();i++){
			boolean checked=false;
			String delay="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
			String itemCode="online";
			int value=1;
			if("离线".equals(commStatusItemsList.get(i))){
				itemCode="offline";
				value=0;
			}
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
					+ "\"code\":\""+itemCode+"\","
					+ "\"value\":\""+value+"\","
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
	
	public String getModbusProtocolRunStatusAlarmItemsConfigData(String protocolName,String classes,String code){
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
		List<String> runStatusItemsList=new ArrayList<String>();
		runStatusItemsList.add("运行");
		runStatusItemsList.add("停抽");
		List<?> list=null;
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.delay,"
					+ " t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效') as alarmsign,"
					+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_code t3  "
					+ " where t.type=6 and t.unitid=t2.id and upper(t3.itemcode)=upper('BJJB') and t.alarmlevel=t3.itemvalue and t2.unit_code='"+code+"' "
					+ " order by t.id";
			list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				itemsList.add(obj[0]+"");
			}
		}
		
		
		for(int i=0;i<runStatusItemsList.size();i++){
			boolean checked=false;
			String delay="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
			String itemCode="run";
			int value=1;
			if("停抽".equals(runStatusItemsList.get(i))){
				itemCode="stop";
				value=0;
			}
			
			if(StringManagerUtils.existOrNot(itemsList,runStatusItemsList.get(i),false)){
				for(int j=0;j<list.size();j++){
					Object[] obj = (Object[]) list.get(j);
					if(runStatusItemsList.get(i).equalsIgnoreCase(obj[0]+"")){
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
					+ "\"title\":\""+runStatusItemsList.get(i)+"\","
					+ "\"code\":\""+itemCode+"\","
					+ "\"value\":\""+value+"\","
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
	
	public String getModbusProtocolFESDiagramConditionsAlarmItemsConfigData(String protocolName,String classes,String code){
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
//		List<String> conditionsListItemsList=new ArrayList<String>();
		
		String conditionsSql="select t.resultname,t.resultcode from tbl_rpc_worktype t order by t.resultcode";
		List<?> conditionsList=this.findCallSql(conditionsSql);
//		for(int i=0;i<conditionsList.size();i++){
//			conditionsListItemsList.add(conditionsList.get(i).toString());
//		}
		List<?> list=null;
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.delay,"
					+ " t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效') as alarmsign,"
					+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_code t3  "
					+ " where t.type=4 and t.unitid=t2.id and upper(t3.itemcode)=upper('BJJB') and t.alarmlevel=t3.itemvalue and t2.unit_code='"+code+"' "
					+ " order by t.id";
			list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				itemsList.add(obj[0]+"");
			}
		}
		
		
		for(int i=0;i<conditionsList.size();i++){
			boolean checked=false;
			Object[] conditionsObj = (Object[]) conditionsList.get(i);
			
			String delay="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
			if(StringManagerUtils.existOrNot(itemsList,conditionsObj[0]+"",false)){
				for(int j=0;j<list.size();j++){
					Object[] obj = (Object[]) list.get(j);
					if((conditionsObj[0]+"").equalsIgnoreCase(obj[0]+"")){
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
					+ "\"title\":\""+conditionsObj[0]+"\","
					+ "\"code\":\""+conditionsObj[1]+"\","
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
	
	public String getProtocolDisplayUnitAcqItemsConfigData(String protocolName,String classes,String code,String unitId,String acqUnitId){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示级别\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线颜色\",\"dataIndex\":\"realtimeCurveColor\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线颜色\",\"dataIndex\":\"historyCurveColor\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> acqItemsList=new ArrayList<String>();
		List<String> acqItemsBitIndexList=new ArrayList<String>();
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveList=new ArrayList<String>();
		List<String> realtimeCurveColorList=new ArrayList<String>();
		List<String> historyCurveList=new ArrayList<String>();
		List<String> historyCurveColorList=new ArrayList<String>();
		if("2".equalsIgnoreCase(classes)){
			String acqUnitIiemsSql="select distinct t.itemname,t.bitindex "
					+ "from TBL_ACQ_ITEM2GROUP_CONF t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4 "
					+ "where t.groupid=t2.id  and t2.id=t3.groupid and t3.unitid=t4.id and t4.id="+acqUnitId+" and t2.type=0";
			
			String sql="select t.itemname,t.bitindex,t.sort,t.showlevel,t.realtimeCurve,t.realtimeCurveColor,historyCurve,historyCurveColor "
					+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2 "
					+ " where t.unitid=t2.id and t2.id= "+unitId+" and t.type=0"
					+ " order by t.sort";
			List<?> acqItemList=this.findCallSql(acqUnitIiemsSql);
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<acqItemList.size();i++){
				Object[] obj=(Object[])acqItemList.get(i);
				acqItemsList.add(obj[0]+"");
				acqItemsBitIndexList.add(obj[1]+"");
			}
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsBitIndexList.add(obj[1]+"");
				itemsSortList.add(obj[2]+"");
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
					if(!"w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						boolean sign=false;
						if(!"2".equalsIgnoreCase(classes)){
							sign=true;
						}else{
							if(StringManagerUtils.existOrNot(acqItemsList, protocolConfig.getItems().get(j).getTitle(), false)){
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
											+ "\"RWType\":\""+RWType+"\","
											+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
											+ "\"resolutionMode\":\""+resolutionMode+"\","
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
										+ "\"RWType\":\""+RWType+"\","
										+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
										+ "\"resolutionMode\":\""+resolutionMode+"\","
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
	
	public String getProtocolDisplayUnitCtrlItemsConfigData(String protocolName,String classes,String code,String unitId,String acqUnitId){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示级别\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> acqItemsList=new ArrayList<String>();
		List<String> acqItemsBitIndexList=new ArrayList<String>();
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		if("2".equalsIgnoreCase(classes)){
			String acqUnitIiemsSql="select distinct t.itemname,t.bitindex "
					+ "from TBL_ACQ_ITEM2GROUP_CONF t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4 "
					+ "where t.groupid=t2.id  and t2.id=t3.groupid and t3.unitid=t4.id and t4.id="+acqUnitId+" and t2.type=1";
			String sql="select t.itemname,t.bitindex,t.sort,t.showlevel "
					+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2 "
					+ " where t.unitid=t2.id and t2.id= "+unitId+" and t.type=2"
					+ " order by t.sort";
			List<?> acqItemList=this.findCallSql(acqUnitIiemsSql);
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<acqItemList.size();i++){
				Object[] obj=(Object[])acqItemList.get(i);
				acqItemsList.add(obj[0]+"");
				acqItemsBitIndexList.add(obj[1]+"");
			}
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsBitIndexList.add(obj[1]+"");
				itemsSortList.add(obj[2]+"");
				itemsShowLevelList.add(obj[3]+"");
			}
		}
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			ModbusProtocolConfig.Protocol protocolConfig=modbusProtocolConfig.getProtocol().get(i);
			if(protocolName.equalsIgnoreCase(protocolConfig.getName())){
//				Collections.sort(protocolConfig.getItems());
				int index=1;
				for(int j=0;j<protocolConfig.getItems().size();j++){
					if(!"r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						boolean sign=false;
						if(!"2".equalsIgnoreCase(classes)){
							sign=true;
						}else{
							if(StringManagerUtils.existOrNot(acqItemsList, protocolConfig.getItems().get(j).getTitle(), false)){
								sign=true;
							}
						}
						if(sign){
							boolean checked=false;
							String sort="";
							String showLevel="";
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
									for(int m=0;m<itemsList.size();m++){
										if(itemsList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())
												&&itemsBitIndexList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"")
											){
											checked=true;
											sort=itemsSortList.get(m);
											showLevel=itemsShowLevelList.get(m);
											break;
										}
									}
									
									result_json.append("{"
											+ "\"checked\":"+checked+","
											+ "\"id\":"+(index)+","
											+ "\"title\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
											+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+","
											+ "\"RWType\":\""+RWType+"\","
											+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
											+ "\"resolutionMode\":\""+resolutionMode+"\","
											+ "\"showLevel\":\""+showLevel+"\","
											+ "\"sort\":\""+sort+"\""
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
											break;
										}
									}
								}
								result_json.append("{\"checked\":"+checked+","
										+ "\"id\":"+(index)+","
										+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
										+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+","
										+ "\"RWType\":\""+RWType+"\","
										+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
										+ "\"resolutionMode\":\""+resolutionMode+"\","
										+ "\"showLevel\":\""+showLevel+"\","
										+ "\"sort\":\""+sort+"\""
										+ "},");
								index++;
							}
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
	
	public String getProtocolDisplayUnitCalItemsConfigData(String deviceType,String classes,String unitId){
		StringBuffer result_json = new StringBuffer();
		String key="rpcCalItemList";
		if("1".equalsIgnoreCase(deviceType)){
			key="pcpCalItemList";
		}
		Jedis jedis=null;
		Set<byte[]>rpcCalItemSet=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists(key.getBytes())){
				if("1".equalsIgnoreCase(deviceType)){
					MemoryDataManagerTask.loadPCPCalculateItem();
				}else{
					MemoryDataManagerTask.loadRPCCalculateItem();
				}
			}
			rpcCalItemSet= jedis.zrange(key.getBytes(), 0, -1);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
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
		List<String> itemsCodeList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveList=new ArrayList<String>();
		List<String> realtimeCurveColorList=new ArrayList<String>();
		List<String> historyCurveList=new ArrayList<String>();
		List<String> historyCurveColorList=new ArrayList<String>();
		if("2".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.sort,t.showlevel,t.realtimeCurve,t.realtimeCurveColor,historyCurve,historyCurveColor "
					+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2 "
					+ " where t.unitid=t2.id and t2.id= "+unitId+" and t.type=1"
					+ " order by t.sort";
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsCodeList.add(obj[1]+"");
				itemsSortList.add(obj[2]+"");
				itemsShowLevelList.add(obj[3]+"");
				realtimeCurveList.add(obj[4]+"");
				realtimeCurveColorList.add(obj[5]+"");
				historyCurveList.add(obj[6]+"");
				historyCurveColorList.add(obj[7]+"");
			}
		}
		
		int index=1;
		if(rpcCalItemSet!=null){
			for(byte[] rpcCalItemByteArr:rpcCalItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
				
				boolean checked=false;
				String sort="";
				String showLevel="";
				String isRealtimeCurve="";
				String realtimeCurveColor="";
				String isHistoryCurve="";
				String historyCurveColor="";

				checked=StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(),false);
				if(checked){
					for(int k=0;k<itemsList.size();k++){
						if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
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
						+ "\"title\":\""+calItem.getName()+"\","
						+ "\"unit\":\""+calItem.getUnit()+"\","
						+ "\"showLevel\":\""+showLevel+"\","
						+ "\"sort\":\""+sort+"\","
						+ "\"isRealtimeCurve\":\""+isRealtimeCurve+"\","
						+ "\"realtimeCurveColor\":\""+realtimeCurveColor+"\","
						+ "\"isHistoryCurve\":\""+isHistoryCurve+"\","
						+ "\"historyCurveColor\":\""+historyCurveColor+"\","
						+ "\"code\":\""+calItem.getCode()+"\""
						+ "},");
				index++;
			
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolInstanceItemsConfigData(String id,String classes){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		int index=1;
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"存储数据数量\",\"dataIndex\":\"quantity\",width:80 ,children:[] },"
				+ "{ \"header\":\"存储数据类型\",\"dataIndex\":\"storeDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\"接口数据类型\",\"dataIndex\":\"IFDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\"小数位数\",\"dataIndex\":\"prec\",width:80 ,children:[] },"
				+ "{ \"header\":\"换算比例\",\"dataIndex\":\"ratio\",width:80 ,children:[] },"
				+ "{ \"header\":\"读写类型\",\"dataIndex\":\"RWType\",width:80 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
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
			if(modbusProtocolConfig!=null){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					ModbusProtocolConfig.Protocol protocolConfig=modbusProtocolConfig.getProtocol().get(i);
					if(protocolName.equalsIgnoreCase(protocolConfig.getName())){
						Collections.sort(protocolConfig.getItems());
						for(int j=0;j<protocolConfig.getItems().size();j++){
							if(StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(),false)){
//								if(RWType.equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){}
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
										+ "\"prec\":"+(protocolConfig.getItems().get(j).getIFDataType().toLowerCase().indexOf("float")>=0?protocolConfig.getItems().get(j).getPrec():"\"\"")+","
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
			
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString();
	}
	
	public String getProtocolDisplayInstanceAcqItemsConfigData(String id,String classes){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
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
		
		String protocolSql="select t.protocol from tbl_display_unit_conf t,tbl_protocoldisplayinstance t2 where t.id=t2.displayunitid and t2.id="+id+"";
		String sql="select t.itemname,t.bitindex,t.sort,t.showlevel,t.realtimeCurve,t.realtimeCurveColor,historyCurve,historyCurveColor "
				+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2,tbl_protocoldisplayinstance t3 "
				+ " where t.unitid=t2.id and t2.id=t3.displayunitid and t.type=0 and t3.id= "+id
				+ " order by t.id";
		List<?> protocolList=this.findCallSql(protocolSql);
		if(protocolList.size()>0){
			String protocolName=protocolList.get(0)+"";
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsBitIndexList.add(obj[1]+"");
				itemsSortList.add(obj[2]+"");
				itemsShowLevelList.add(obj[3]+"");
				realtimeCurveList.add(obj[4]+"");
				realtimeCurveColorList.add(obj[5]+"");
				historyCurveList.add(obj[6]+"");
				historyCurveColorList.add(obj[7]+"");
			}
			if(modbusProtocolConfig!=null){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					ModbusProtocolConfig.Protocol protocolConfig=modbusProtocolConfig.getProtocol().get(i);
					if(protocolName.equalsIgnoreCase(protocolConfig.getName())){
						Collections.sort(protocolConfig.getItems());
						int index=1;
						for(int j=0;j<protocolConfig.getItems().size();j++){
							if(StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(), false)){
								String sort="";
								String showLevel="";
								String isRealtimeCurve="";
								String realtimeCurveColor="";
								String isHistoryCurve="";
								String historyCurveColor="";
								
								if(protocolConfig.getItems().get(j).getResolutionMode()==0
										&&protocolConfig.getItems().get(j).getMeaning()!=null
										&&protocolConfig.getItems().get(j).getMeaning().size()>0){
									Collections.sort(protocolConfig.getItems().get(j).getMeaning());//排序
									for(int k=0;k<protocolConfig.getItems().get(j).getMeaning().size();k++){
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
												+ "\"id\":"+(index)+","
												+ "\"title\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
												+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
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
									result_json.append("{"
											+ "\"id\":"+(index)+","
											+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
											+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
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
			}
		}
		
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolDisplayInstanceCtrlItemsConfigData(String id,String classes){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示级别\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		
		String protocolSql="select t.protocol from tbl_display_unit_conf t,tbl_protocoldisplayinstance t2 where t.id=t2.displayunitid and t2.id="+id+"";
		String sql="select t.itemname,t.bitindex,t.sort,t.showlevel"
				+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2,tbl_protocoldisplayinstance t3 "
				+ " where t.unitid=t2.id and t2.id=t3.displayunitid and t.type=2 and t3.id= "+id
				+ " order by t.id";
		List<?> protocolList=this.findCallSql(protocolSql);
		if(protocolList.size()>0){
			String protocolName=protocolList.get(0)+"";
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsBitIndexList.add(obj[1]+"");
				itemsSortList.add(obj[2]+"");
				itemsShowLevelList.add(obj[3]+"");
			}
			if(modbusProtocolConfig!=null){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					ModbusProtocolConfig.Protocol protocolConfig=modbusProtocolConfig.getProtocol().get(i);
					if(protocolName.equalsIgnoreCase(protocolConfig.getName())){
//						Collections.sort(protocolConfig.getItems());
						int index=1;
						for(int j=0;j<protocolConfig.getItems().size();j++){
							if(StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(), false)){
								String sort="";
								String showLevel="";
								if(protocolConfig.getItems().get(j).getResolutionMode()==0
										&&protocolConfig.getItems().get(j).getMeaning()!=null
										&&protocolConfig.getItems().get(j).getMeaning().size()>0){
									Collections.sort(protocolConfig.getItems().get(j).getMeaning());//排序
									for(int k=0;k<protocolConfig.getItems().get(j).getMeaning().size();k++){
										sort="";
										showLevel="";
										for(int m=0;m<itemsList.size();m++){
											if(itemsList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())
													&&itemsBitIndexList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"")
												){
												sort=itemsSortList.get(m);
												showLevel=itemsShowLevelList.get(m);
												break;
											}
										}
										
										result_json.append("{"
												+ "\"id\":"+(index)+","
												+ "\"title\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
												+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
												+ "\"showLevel\":\""+showLevel+"\","
												+ "\"sort\":\""+sort+"\""
												+ "},");
										index++;
									}
								}else{
									for(int k=0;k<itemsList.size();k++){
										if(itemsList.get(k).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())){
											sort=itemsSortList.get(k);
											showLevel=itemsShowLevelList.get(k);
											break;
										}
									}
									result_json.append("{"
											+ "\"id\":"+(index)+","
											+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
											+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
											+ "\"showLevel\":\""+showLevel+"\","
											+ "\"sort\":\""+sort+"\""
											+ "},");
									index++;
								}
							}
						}
						break;
					}
				}
			}
		}
		
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolDisplayInstanceCalItemsConfigData(String id,String classes,String deviceType){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		String key="rpcCalItemList";
		if("1".equalsIgnoreCase(deviceType)){
			key="pcpCalItemList";
		}
		Jedis jedis=null;
		Set<byte[]>rpcCalItemSet=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists(key.getBytes())){
				MemoryDataManagerTask.loadRPCCalculateItem();
			}
			
			rpcCalItemSet= jedis.zrange(key.getBytes(), 0, -1);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
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
		List<String> itemsCodeList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveList=new ArrayList<String>();
		List<String> realtimeCurveColorList=new ArrayList<String>();
		List<String> historyCurveList=new ArrayList<String>();
		List<String> historyCurveColorList=new ArrayList<String>();
		
		String sql="select t.itemname,t.itemcode,t.bitindex,t.sort,t.showlevel,t.realtimeCurve,t.realtimeCurveColor,historyCurve,historyCurveColor "
				+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2,tbl_protocoldisplayinstance t3 "
				+ " where t.unitid=t2.id and t2.id=t3.displayunitid and t.type=1 and t3.id= "+id
				+ " order by t.id";
		if(rpcCalItemSet!=null){
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsCodeList.add(obj[1]+"");
				itemsBitIndexList.add(obj[2]+"");
				itemsSortList.add(obj[3]+"");
				itemsShowLevelList.add(obj[4]+"");
				realtimeCurveList.add(obj[5]+"");
				realtimeCurveColorList.add(obj[6]+"");
				historyCurveList.add(obj[7]+"");
				historyCurveColorList.add(obj[8]+"");
			}
			int index=1;
			for(byte[] rpcCalItemByteArr:rpcCalItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
				if(StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(), false)){
					String sort="";
					String showLevel="";
					String isRealtimeCurve="";
					String realtimeCurveColor="";
					String isHistoryCurve="";
					String historyCurveColor="";

					for(int k=0;k<itemsList.size();k++){
						if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
							sort=itemsSortList.get(k);
							showLevel=itemsShowLevelList.get(k);
							isRealtimeCurve=realtimeCurveList.get(k);
							realtimeCurveColor=realtimeCurveColorList.get(k);
							isHistoryCurve=historyCurveList.get(k);
							historyCurveColor=historyCurveColorList.get(k);
							break;
						}
					}
					result_json.append("{"
							+ "\"id\":"+index+","
							+ "\"title\":\""+calItem.getName()+"\","
							+ "\"unit\":\""+calItem.getUnit()+"\","
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
		
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolAlarmInstanceNumItemsConfigData(String id,String classes,String resolutionMode){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"上限\",\"dataIndex\":\"upperLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\"下限\",\"dataIndex\":\"lowerLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\"回差\",\"dataIndex\":\"hystersis\",width:80 ,children:[] },"
				+ "{ \"header\":\"延时(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警级别\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警开关\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送短信\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送邮件\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		List<Integer> itemAddrsList=new ArrayList<Integer>();
		
		String itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.upperlimit,t.lowerlimit,t.hystersis,t.delay,"
				+ "t4.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效'), "
				+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3, tbl_code t4 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid and upper(t4.itemcode)=upper('BJJB') and t.alarmlevel=t4.itemvalue "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.itemaddr";
		
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.upperlimit,t.lowerlimit,t.hystersis,t.delay,"
					+ "t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效'), "
					+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
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
					+ "\"alarmSign\":\""+obj[9]+"\","
					+ "\"isSendMessage\":\""+obj[10]+"\","
					+ "\"isSendMail\":\""+obj[11]+"\"},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolAlarmInstanceCalNumItemsConfigData(String id,String classes,String resolutionMode,String deviceType){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Jedis jedis=null;
		Set<byte[]>calItemSet=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(StringManagerUtils.stringToInteger(deviceType)==0){
				if(!jedis.exists("rpcCalItemList".getBytes())){
					MemoryDataManagerTask.loadRPCCalculateItem();
				}
				calItemSet= jedis.zrange("rpcCalItemList".getBytes(), 0, -1);
			}else{
				if(!jedis.exists("pcpCalItemList".getBytes())){
					MemoryDataManagerTask.loadPCPCalculateItem();
				}
				calItemSet= jedis.zrange("pcpCalItemList".getBytes(), 0, -1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"上限\",\"dataIndex\":\"upperLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\"下限\",\"dataIndex\":\"lowerLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\"回差\",\"dataIndex\":\"hystersis\",width:80 ,children:[] },"
				+ "{ \"header\":\"延时(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警级别\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警开关\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送短信\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送邮件\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		List<Integer> itemAddrsList=new ArrayList<Integer>();
		
		String itemsSql="select t.id, t.itemname,t.itemcode,t.upperlimit,t.lowerlimit,t.hystersis,t.delay,"
				+ "t4.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效'), "
				+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3, tbl_code t4 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid and upper(t4.itemcode)=upper('BJJB') and t.alarmlevel=t4.itemvalue "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.id";
		
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,t.upperlimit,t.lowerlimit,t.hystersis,t.delay,"
					+ "t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效'), "
					+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_code t3 "
					+ " where t.unitid=t2.id and upper(t3.itemcode)=upper('BJJB') and t.alarmlevel=t3.itemvalue "
					+ " and t2.id="+id+" "
					+ " and t.type="+resolutionMode
					+ " order by t.id";
		}
		
		List<?> list=this.findCallSql(itemsSql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String unit="";
			if(calItemSet!=null){
				for(byte[] rpcCalItemByteArr:calItemSet){
					CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
					if(calItem.getDataType()==2&&(obj[2]+"").equalsIgnoreCase(calItem.getCode())){
						unit=calItem.getUnit();
						break;
					}
				}
			}
			result_json.append("{\"id\":"+(i+1)+","
					+ "\"title\":\""+obj[1]+"\","
					+ "\"code\":\""+obj[2]+"\","
					+ "\"unit\":\""+unit+"\","
					+ "\"upperLimit\":\""+obj[3]+"\","
					+ "\"lowerLimit\":\""+obj[4]+"\","
					+ "\"hystersis\":\""+obj[5]+"\","
					+ "\"delay\":\""+obj[6]+"\","
					+ "\"alarmLevel\":\""+obj[7]+"\","
					+ "\"alarmSign\":\""+obj[8]+"\","
					+ "\"isSendMessage\":\""+obj[9]+"\","
					+ "\"isSendMail\":\""+obj[10]+"\"},");
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
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"位\",\"dataIndex\":\"bitIndex\",width:80 ,children:[] },"
				+ "{ \"header\":\"含义\",\"dataIndex\":\"meaning\",width:80 ,children:[] },"
				+ "{ \"header\":\"触发状态\",\"dataIndex\":\"value\",width:80 ,children:[] },"
				+ "{ \"header\":\"延时(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警级别\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警开关\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送短信\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送邮件\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		List<Integer> itemAddrsList=new ArrayList<Integer>();
		
		String itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.bitIndex,t.value,t.delay,"
				+ " t4.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效'), "
				+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail,"
				+ " t2.protocol "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3, tbl_code t4 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid and upper(t4.itemcode)=upper('BJJB') and t.alarmlevel=t4.itemvalue "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.itemaddr,t.bitindex";
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.bitIndex,t.value,t.delay,"
					+ " t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效'), "
					+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail,"
					+ " t2.protocol "
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
					+ "\"alarmSign\":\""+obj[8]+"\","
					+ "\"isSendMessage\":\""+obj[9]+"\","
					+ "\"isSendMail\":\""+obj[10]+"\"},");
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
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"值\",\"dataIndex\":\"value\",width:80 ,children:[] },"
				+ "{ \"header\":\"含义\",\"dataIndex\":\"meaning\",width:80 ,children:[] },"
				+ "{ \"header\":\"延时(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警级别\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警开关\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送短信\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送邮件\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		List<Integer> itemAddrsList=new ArrayList<Integer>();
		
		String itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.value,t.delay,"
				+ " t4.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效'),"
				+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail,"
				+ " t2.protocol "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3, tbl_code t4 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid and upper(t4.itemcode)=upper('BJJB') and t.alarmlevel=t4.itemvalue "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.itemaddr,t.bitindex";
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.value,t.delay,"
					+ " t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效'),"
					+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail,"
					+ " t2.protocol "
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
					+ "\"alarmSign\":\""+obj[7]+"\","
					+ "\"isSendMessage\":\""+obj[8]+"\","
					+ "\"isSendMail\":\""+obj[9]+"\"},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolAlarmInstanceFESDiagramResultItemsConfigData(String id,String classes,String resolutionMode){
		StringBuffer result_json = new StringBuffer();
		String columns = "["
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
		
		String itemsSql="select t.id, t.itemname,t.itemcode,t.delay,"
				+ " t4.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效') as alarmsign, "
				+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3, tbl_code t4 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid and upper(t4.itemcode)=upper('BJJB') and t.alarmlevel=t4.itemvalue "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.id";
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,t.delay,"
					+ " t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效') as alarmsign,"
					+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2, tbl_code t3 "
					+ " where t.unitid=t2.id and upper(t3.itemcode)=upper('BJJB') and t.alarmlevel=t3.itemvalue "
					+ " and t2.id="+id+" "
					+ " and t.type="+resolutionMode
					+ " order by t.id";
		}
		List<?> list=this.findCallSql(itemsSql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":"+(i+1)+","
					+ "\"title\":\""+obj[1]+"\","
					+ "\"code\":\""+obj[2]+"\","
					+ "\"delay\":\""+obj[3]+"\","
					+ "\"alarmLevel\":\""+obj[4]+"\","
					+ "\"alarmSign\":\""+obj[5]+"\","
					+ "\"isSendMessage\":\""+obj[6]+"\","
					+ "\"isSendMail\":\""+obj[7]+"\"},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolAlarmInstanceRunStatusItemsConfigData(String id,String classes,String resolutionMode){
		StringBuffer result_json = new StringBuffer();
		String columns = "["
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
		
		String itemsSql="select t.id, t.itemname,t.itemcode,t.delay,"
				+ " t4.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效') as alarmsign, "
				+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3, tbl_code t4 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid and upper(t4.itemcode)=upper('BJJB') and t.alarmlevel=t4.itemvalue "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.id";
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,t.delay,"
					+ " t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效') as alarmsign,"
					+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2, tbl_code t3 "
					+ " where t.unitid=t2.id and upper(t3.itemcode)=upper('BJJB') and t.alarmlevel=t3.itemvalue "
					+ " and t2.id="+id+" "
					+ " and t.type="+resolutionMode
					+ " order by t.id";
		}
		List<?> list=this.findCallSql(itemsSql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":"+(i+1)+","
					+ "\"title\":\""+obj[1]+"\","
					+ "\"code\":\""+obj[2]+"\","
					+ "\"delay\":\""+obj[3]+"\","
					+ "\"alarmLevel\":\""+obj[4]+"\","
					+ "\"alarmSign\":\""+obj[5]+"\","
					+ "\"isSendMessage\":\""+obj[6]+"\","
					+ "\"isSendMail\":\""+obj[7]+"\"},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolAlarmInstanceCommStatusItemsConfigData(String id,String classes,String resolutionMode){
		StringBuffer result_json = new StringBuffer();
		String columns = "["
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
		
		String itemsSql="select t.id, t.itemname,t.itemcode,t.delay,"
				+ " t4.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效') as alarmsign, "
				+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3, tbl_code t4 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid and upper(t4.itemcode)=upper('BJJB') and t.alarmlevel=t4.itemvalue "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.id";
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,t.delay,"
					+ " t3.itemname as alarmLevel,decode(t.alarmsign,1,'使能','失效') as alarmsign,"
					+ " decode(t.issendmessage,1,'是','否') as issendmessage,decode(t.issendmail,1,'是','否') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2, tbl_code t3 "
					+ " where t.unitid=t2.id and upper(t3.itemcode)=upper('BJJB') and t.alarmlevel=t3.itemvalue "
					+ " and t2.id="+id+" "
					+ " and t.type="+resolutionMode
					+ " order by t.id";
		}
		List<?> list=this.findCallSql(itemsSql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":"+(i+1)+","
					+ "\"title\":\""+obj[1]+"\","
					+ "\"code\":\""+obj[2]+"\","
					+ "\"delay\":\""+obj[3]+"\","
					+ "\"alarmLevel\":\""+obj[4]+"\","
					+ "\"alarmSign\":\""+obj[5]+"\","
					+ "\"isSendMessage\":\""+obj[6]+"\","
					+ "\"isSendMail\":\""+obj[7]+"\"},");
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
		StringBuffer rpcTree_json = new StringBuffer();
		StringBuffer pcpTree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		rpcTree_json.append("[");
		pcpTree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			String unitSql="select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark,t.protocol from tbl_acq_unit_conf t order by t.protocol,t.id";
			String groupSql="select t3.id,t3.group_code,t3.group_name,t3.grouptiminginterval,t3.groupsavinginterval,t3.remark,t3.protocol,t3.type,t2.id as unitId "
					+ " from TBL_ACQ_GROUP2UNIT_CONF t,tbl_acq_unit_conf t2,tbl_acq_group_conf t3 "
					+ " where t.unitid=t2.id and t.groupid=t3.id "
					+ " order by t3.protocol,t2.unit_code,t3.type,t3.id";
			List<?> unitList=this.findCallSql(unitSql);
			List<?> groupList=this.findCallSql(groupSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(modbusProtocolConfig.getProtocol().get(i).getDeviceType()==0){
					rpcTree_json.append("{\"classes\":1,");
					rpcTree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					rpcTree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					rpcTree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
					rpcTree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					rpcTree_json.append("\"iconCls\": \"protocol\",");
					rpcTree_json.append("\"expanded\": true,");
					rpcTree_json.append("\"children\": [");
					for(int j=0;j<unitList.size();j++){
						Object[] unitObj = (Object[]) unitList.get(j);
						if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[unitObj.length-1]+"")){
							rpcTree_json.append("{\"classes\":2,");
							rpcTree_json.append("\"id\":"+unitObj[0]+",");
							rpcTree_json.append("\"code\":\""+unitObj[1]+"\",");
							rpcTree_json.append("\"text\":\""+unitObj[2]+"\",");
							rpcTree_json.append("\"remark\":\""+unitObj[3]+"\",");
							rpcTree_json.append("\"protocol\":\""+unitObj[4]+"\",");
							rpcTree_json.append("\"iconCls\": \"acqUnit\",");
							rpcTree_json.append("\"expanded\": true,");
							rpcTree_json.append("\"children\": [");
							for(int k=0;k<groupList.size();k++){
								Object[] groupObj = (Object[]) groupList.get(k);
								if((unitObj[0]+"").equalsIgnoreCase(groupObj[groupObj.length-1]+"")){
									rpcTree_json.append("{\"classes\":3,");
									rpcTree_json.append("\"id\":"+groupObj[0]+",");
									rpcTree_json.append("\"code\":\""+groupObj[1]+"\",");
									rpcTree_json.append("\"text\":\""+groupObj[2]+"\",");
									rpcTree_json.append("\"groupTimingInterval\":\""+groupObj[3]+"\",");
									rpcTree_json.append("\"groupSavingInterval\":\""+groupObj[4]+"\",");
									rpcTree_json.append("\"remark\":\""+groupObj[5]+"\",");
									rpcTree_json.append("\"protocol\":\""+groupObj[6]+"\",");
									rpcTree_json.append("\"type\":"+groupObj[7]+",");
									rpcTree_json.append("\"typeName\":\""+("0".equalsIgnoreCase(groupObj[7]+"")?"采集组":"控制组")+"\",");
									rpcTree_json.append("\"unitId\":"+groupObj[groupObj.length-1]+",");
									rpcTree_json.append("\"iconCls\": \"acqGroup\",");
									rpcTree_json.append("\"leaf\": true");
									rpcTree_json.append("},");
								}
							}
							if(rpcTree_json.toString().endsWith(",")){
								rpcTree_json.deleteCharAt(rpcTree_json.length() - 1);
							}
							
							rpcTree_json.append("]},");
						}
					}
					if(rpcTree_json.toString().endsWith(",")){
						rpcTree_json.deleteCharAt(rpcTree_json.length() - 1);
					}
					rpcTree_json.append("]},");
				}else{
					pcpTree_json.append("{\"classes\":1,");
					pcpTree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					pcpTree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					pcpTree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
					pcpTree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					pcpTree_json.append("\"iconCls\": \"protocol\",");
					pcpTree_json.append("\"expanded\": true,");
					pcpTree_json.append("\"children\": [");
					for(int j=0;j<unitList.size();j++){
						Object[] unitObj = (Object[]) unitList.get(j);
						if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[unitObj.length-1]+"")){
							pcpTree_json.append("{\"classes\":2,");
							pcpTree_json.append("\"id\":"+unitObj[0]+",");
							pcpTree_json.append("\"code\":\""+unitObj[1]+"\",");
							pcpTree_json.append("\"text\":\""+unitObj[2]+"\",");
							pcpTree_json.append("\"remark\":\""+unitObj[3]+"\",");
							pcpTree_json.append("\"protocol\":\""+unitObj[4]+"\",");
							pcpTree_json.append("\"iconCls\": \"acqUnit\",");
							pcpTree_json.append("\"expanded\": true,");
							pcpTree_json.append("\"children\": [");
							for(int k=0;k<groupList.size();k++){
								Object[] groupObj = (Object[]) groupList.get(k);
								if((unitObj[0]+"").equalsIgnoreCase(groupObj[groupObj.length-1]+"")){
									pcpTree_json.append("{\"classes\":3,");
									pcpTree_json.append("\"id\":"+groupObj[0]+",");
									pcpTree_json.append("\"code\":\""+groupObj[1]+"\",");
									pcpTree_json.append("\"text\":\""+groupObj[2]+"\",");
									pcpTree_json.append("\"groupTimingInterval\":\""+groupObj[3]+"\",");
									pcpTree_json.append("\"groupSavingInterval\":\""+groupObj[4]+"\",");
									pcpTree_json.append("\"remark\":\""+groupObj[5]+"\",");
									pcpTree_json.append("\"protocol\":\""+groupObj[6]+"\",");
									pcpTree_json.append("\"type\":"+groupObj[7]+",");
									pcpTree_json.append("\"typeName\":\""+("0".equalsIgnoreCase(groupObj[7]+"")?"采集组":"控制组")+"\",");
									pcpTree_json.append("\"unitId\":"+groupObj[groupObj.length-1]+",");
									pcpTree_json.append("\"iconCls\": \"acqGroup\",");
									pcpTree_json.append("\"leaf\": true");
									pcpTree_json.append("},");
								}
							}
							if(pcpTree_json.toString().endsWith(",")){
								pcpTree_json.deleteCharAt(pcpTree_json.length() - 1);
							}
							
							pcpTree_json.append("]},");
						}
					}
					if(pcpTree_json.toString().endsWith(",")){
						pcpTree_json.deleteCharAt(pcpTree_json.length() - 1);
					}
					pcpTree_json.append("]},");
				}
			}
		}
		if(rpcTree_json.toString().endsWith(",")){
			rpcTree_json.deleteCharAt(rpcTree_json.length() - 1);
		}
		rpcTree_json.append("]");
		
		if(pcpTree_json.toString().endsWith(",")){
			pcpTree_json.deleteCharAt(pcpTree_json.length() - 1);
		}
		pcpTree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"抽油机井\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+rpcTree_json+"},");
		result_json.append("{\"classes\":0,\"text\":\"螺杆泵井\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pcpTree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getDisplayUnitTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer rpcTree_json = new StringBuffer();
		StringBuffer pcpTree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		rpcTree_json.append("[");
		pcpTree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			String unitSql="select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark,t.protocol,t.acqunitid,t2.unit_name as acqunitname "
					+ " from tbl_display_unit_conf t,tbl_acq_unit_conf t2 "
					+ " where t.acqunitid=t2.id "
					+ " order by t.protocol,t.id";
			List<?> unitList=this.findCallSql(unitSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(modbusProtocolConfig.getProtocol().get(i).getDeviceType()==0){
					rpcTree_json.append("{\"classes\":1,");
					rpcTree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					rpcTree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					rpcTree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
					rpcTree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					rpcTree_json.append("\"iconCls\": \"protocol\",");
					rpcTree_json.append("\"expanded\": true,");
					rpcTree_json.append("\"children\": [");
					for(int j=0;j<unitList.size();j++){
						Object[] unitObj = (Object[]) unitList.get(j);
						if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[4]+"")){
							rpcTree_json.append("{\"classes\":2,");
							rpcTree_json.append("\"id\":"+unitObj[0]+",");
							rpcTree_json.append("\"code\":\""+unitObj[1]+"\",");
							rpcTree_json.append("\"text\":\""+unitObj[2]+"\",");
							rpcTree_json.append("\"remark\":\""+unitObj[3]+"\",");
							rpcTree_json.append("\"protocol\":\""+unitObj[4]+"\",");
							rpcTree_json.append("\"acqUnitId\":\""+unitObj[5]+"\",");
							rpcTree_json.append("\"acqUnitName\":\""+unitObj[6]+"\",");
							rpcTree_json.append("\"iconCls\": \"acqUnit\",");
							rpcTree_json.append("\"leaf\": true");
							rpcTree_json.append("},");
						}
					}
					if(rpcTree_json.toString().endsWith(",")){
						rpcTree_json.deleteCharAt(rpcTree_json.length() - 1);
					}
					rpcTree_json.append("]},");
				}else{
					pcpTree_json.append("{\"classes\":1,");
					pcpTree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					pcpTree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					pcpTree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
					pcpTree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					pcpTree_json.append("\"iconCls\": \"protocol\",");
					pcpTree_json.append("\"expanded\": true,");
					pcpTree_json.append("\"children\": [");
					for(int j=0;j<unitList.size();j++){
						Object[] unitObj = (Object[]) unitList.get(j);
						if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[4]+"")){
							pcpTree_json.append("{\"classes\":2,");
							pcpTree_json.append("\"id\":"+unitObj[0]+",");
							pcpTree_json.append("\"code\":\""+unitObj[1]+"\",");
							pcpTree_json.append("\"text\":\""+unitObj[2]+"\",");
							pcpTree_json.append("\"remark\":\""+unitObj[3]+"\",");
							pcpTree_json.append("\"protocol\":\""+unitObj[4]+"\",");
							pcpTree_json.append("\"acqUnitId\":\""+unitObj[5]+"\",");
							pcpTree_json.append("\"acqUnitName\":\""+unitObj[6]+"\",");
							pcpTree_json.append("\"iconCls\": \"acqUnit\",");
							pcpTree_json.append("\"leaf\": true");
							pcpTree_json.append("},");
						}
					}
					if(pcpTree_json.toString().endsWith(",")){
						pcpTree_json.deleteCharAt(pcpTree_json.length() - 1);
					}
					pcpTree_json.append("]},");
				}
			}
		}
		if(rpcTree_json.toString().endsWith(",")){
			rpcTree_json.deleteCharAt(rpcTree_json.length() - 1);
		}
		rpcTree_json.append("]");
		
		if(pcpTree_json.toString().endsWith(",")){
			pcpTree_json.deleteCharAt(pcpTree_json.length() - 1);
		}
		pcpTree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"抽油机井\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+rpcTree_json+"},");
		result_json.append("{\"classes\":0,\"text\":\"螺杆泵井\",\"deviceType\":1,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pcpTree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String modbusProtocolAddrMappingTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer rpcTree_json = new StringBuffer();
		StringBuffer pcpTree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		rpcTree_json.append("[");
		pcpTree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(modbusProtocolConfig.getProtocol().get(i).getDeviceType()==0){
					rpcTree_json.append("{\"classes\":1,");
					rpcTree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					rpcTree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					rpcTree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
					rpcTree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					rpcTree_json.append("\"iconCls\": \"protocol\",");
					rpcTree_json.append("\"leaf\": true");
					rpcTree_json.append("},");
				}else{
					pcpTree_json.append("{\"classes\":1,");
					pcpTree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					pcpTree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					pcpTree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
					pcpTree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					pcpTree_json.append("\"iconCls\": \"protocol\",");
					pcpTree_json.append("\"leaf\": true");
					pcpTree_json.append("},");
				}
			}
		}
		if(rpcTree_json.toString().endsWith(",")){
			rpcTree_json.deleteCharAt(rpcTree_json.length() - 1);
		}
		rpcTree_json.append("]");
		
		if(pcpTree_json.toString().endsWith(",")){
			pcpTree_json.deleteCharAt(pcpTree_json.length() - 1);
		}
		pcpTree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"抽油机井\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+rpcTree_json+"},");
		result_json.append("{\"classes\":0,\"text\":\"螺杆泵井\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pcpTree_json+"}");
		result_json.append("]");
		return result_json.toString();
	}
	
	public String modbusProtocolAndAcqUnitTreeData(String deviceTypeStr,String protocol){
		StringBuffer result_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
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
				if((!StringManagerUtils.isNotNull(deviceTypeStr))||modbusProtocolConfig.getProtocol().get(i).getDeviceType()==deviceType){
					if((!StringManagerUtils.isNotNull(protocol)) || protocol.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
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
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]");
		return result_json.toString();
	}
	
	public String modbusProtocolAndDisplayUnitTreeData(String deviceTypeStr){
		StringBuffer result_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		int deviceType=0;
		if(StringManagerUtils.stringToInteger(deviceTypeStr)>0){
			deviceType=1;
		}
		
		result_json.append("[");
		if(modbusProtocolConfig!=null){
			String alarmUnitSql="select t.id,t.unit_code,t.unit_name,t.remark,t.protocol from tbl_display_unit_conf t order by t.protocol,t.id";
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
	
	
	public String modbusProtocolAndAlarmUnitTreeData(String deviceTypeStr){
		StringBuffer result_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
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
		StringBuffer rpcTree_json = new StringBuffer();
		StringBuffer pcpTree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		rpcTree_json.append("[");
		pcpTree_json.append("[");
		
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
					rpcTree_json.append("{\"classes\":1,");
					rpcTree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					rpcTree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					rpcTree_json.append("\"deviceType\":"+0+",");
					rpcTree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					rpcTree_json.append("\"iconCls\": \"protocol\",");
					rpcTree_json.append("\"expanded\": true,");
					rpcTree_json.append("\"children\": [");
					for(int j=0;j<unitList.size();j++){
						Object[] unitObj = (Object[]) unitList.get(j);
						if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[unitObj.length-1]+"")){
							rpcTree_json.append("{\"classes\":3,");
							rpcTree_json.append("\"id\":"+unitObj[0]+",");
							rpcTree_json.append("\"deviceType\":"+0+",");
							rpcTree_json.append("\"code\":\""+unitObj[1]+"\",");
							rpcTree_json.append("\"text\":\""+unitObj[2]+"\",");
							rpcTree_json.append("\"remark\":\""+unitObj[3]+"\",");
							rpcTree_json.append("\"protocol\":\""+unitObj[4]+"\",");
							rpcTree_json.append("\"iconCls\": \"acqGroup\",");
							rpcTree_json.append("\"leaf\": true");
							rpcTree_json.append("},");
						}
					}
					if(rpcTree_json.toString().endsWith(",")){
						rpcTree_json.deleteCharAt(rpcTree_json.length() - 1);
					}
					rpcTree_json.append("]},");
				}else{
					pcpTree_json.append("{\"classes\":1,");
					pcpTree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					pcpTree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					pcpTree_json.append("\"deviceType\":"+1+",");
					pcpTree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					pcpTree_json.append("\"iconCls\": \"protocol\",");
					pcpTree_json.append("\"expanded\": true,");
					pcpTree_json.append("\"children\": [");
					for(int j=0;j<unitList.size();j++){
						Object[] unitObj = (Object[]) unitList.get(j);
						if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[unitObj.length-1]+"")){
							pcpTree_json.append("{\"classes\":3,");
							pcpTree_json.append("\"id\":"+unitObj[0]+",");
							pcpTree_json.append("\"deviceType\":"+1+",");
							pcpTree_json.append("\"code\":\""+unitObj[1]+"\",");
							pcpTree_json.append("\"text\":\""+unitObj[2]+"\",");
							pcpTree_json.append("\"remark\":\""+unitObj[3]+"\",");
							pcpTree_json.append("\"protocol\":\""+unitObj[4]+"\",");
							pcpTree_json.append("\"iconCls\": \"acqGroup\",");
							pcpTree_json.append("\"leaf\": true");
							pcpTree_json.append("},");
						}
					}
					if(pcpTree_json.toString().endsWith(",")){
						pcpTree_json.deleteCharAt(pcpTree_json.length() - 1);
					}
					pcpTree_json.append("]},");
				}
			}
		}
		if(rpcTree_json.toString().endsWith(",")){
			rpcTree_json.deleteCharAt(rpcTree_json.length() - 1);
		}
		rpcTree_json.append("]");
		
		if(pcpTree_json.toString().endsWith(",")){
			pcpTree_json.deleteCharAt(pcpTree_json.length() - 1);
		}
		pcpTree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"deviceType\": 0,\"text\":\"抽油机井\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+rpcTree_json+"},");
		result_json.append("{\"classes\":0,\"deviceType\": 1,\"text\":\"螺杆泵井\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pcpTree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getAcqUnitList(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer rpcUnit_json = new StringBuffer();
		StringBuffer pcpUnit_json = new StringBuffer();

		rpcUnit_json.append("[");
		pcpUnit_json.append("[");
		String acqUnitSql="select t.unit_code,t.unit_name,t.protocol from TBL_ACQ_UNIT_CONF t order by t.id";
		List<?> unitList=this.findCallSql(acqUnitSql);
		for(int i=0;i<unitList.size();i++){
			Object[] obj = (Object[]) unitList.get(i);
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()!=null){
				for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
					if(modbusProtocolConfig.getProtocol().get(j).getName().equalsIgnoreCase(obj[2]+"")){
						if(modbusProtocolConfig.getProtocol().get(j).getDeviceType()==0){
							rpcUnit_json.append("\""+obj[1]+"\",");
						}else{
							pcpUnit_json.append("\""+obj[1]+"\",");
						}
						break;
					}
				}
			}
		}
		if(rpcUnit_json.toString().endsWith(",")){
			rpcUnit_json.deleteCharAt(rpcUnit_json.length() - 1);
		}
		if(pcpUnit_json.toString().endsWith(",")){
			pcpUnit_json.deleteCharAt(pcpUnit_json.length() - 1);
		}
		rpcUnit_json.append("]");
		pcpUnit_json.append("]");
		result_json.append("{\"rpcAcqUnit\":"+rpcUnit_json+",");
		result_json.append("\"pcpAcqUnit\":"+pcpUnit_json+"}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusProtocolInstanceConfigTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer rpcTree_json = new StringBuffer();
		StringBuffer pcpTree_json = new StringBuffer();
		rpcTree_json.append("[");
		pcpTree_json.append("[");
		String sql="select t.id,t.name,t.code,t.acqprotocoltype,t.ctrlprotocoltype,"//0~4
				+ " t.signinprefix,t.signinsuffix,t.heartbeatprefix,t.heartbeatsuffix,"//5~8
				+ " t.packetsendinterval,t.prefixsuffixhex,"//9~10
				+ " t.devicetype,t.sort,t.unitid,t2.unit_name "//11~14
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
			if(StringManagerUtils.stringToInteger(obj[11]+"")==0){
				rpcTree_json.append("{\"classes\":1,");
				rpcTree_json.append("\"id\":\""+obj[0]+"\",");
				rpcTree_json.append("\"text\":\""+obj[1]+"\",");
				rpcTree_json.append("\"code\":\""+obj[2]+"\",");
				rpcTree_json.append("\"acqProtocolType\":\""+obj[3]+"\",");
				rpcTree_json.append("\"ctrlProtocolType\":\""+obj[4]+"\",");
				rpcTree_json.append("\"signInPrefix\":\""+obj[5]+"\",");
				rpcTree_json.append("\"signInSuffix\":\""+obj[6]+"\",");
				rpcTree_json.append("\"heartbeatPrefix\":\""+obj[7]+"\",");
				rpcTree_json.append("\"heartbeatSuffix\":\""+obj[8]+"\",");
				rpcTree_json.append("\"packetSendInterval\":\""+obj[9]+"\",");
				rpcTree_json.append("\"prefixSuffixHex\":\""+obj[10]+"\",");
				rpcTree_json.append("\"deviceType\":"+obj[11]+",");
				rpcTree_json.append("\"sort\":\""+obj[12]+"\",");
				rpcTree_json.append("\"unitId\":"+obj[13]+",");
				rpcTree_json.append("\"unitName\":\""+obj[14]+"\",");
				rpcTree_json.append("\"iconCls\": \"protocol\",");

				rpcTree_json.append("\"expanded\": true,");
				rpcTree_json.append("\"children\": [");
				rpcTree_json.append("{\"classes\":2,");
				rpcTree_json.append("\"text\":\""+obj[14]+"\",");
				rpcTree_json.append("\"id\":"+obj[13]+",");
				rpcTree_json.append("\"iconCls\": \"acqUnit\","); 
				
				rpcTree_json.append("\"expanded\": true,");
				rpcTree_json.append("\"children\": [");
				for(int j=0;j<groupList.size();j++){
					Object[] groupObj = (Object[]) groupList.get(j);
					if((obj[13]+"").equalsIgnoreCase(groupObj[3]+"")){
						rpcTree_json.append("{\"classes\":3,");
						rpcTree_json.append("\"id\":"+groupObj[0]+",");
						rpcTree_json.append("\"text\":\""+groupObj[1]+"\",");
						rpcTree_json.append("\"code\":\""+groupObj[2]+"\",");
						rpcTree_json.append("\"iconCls\": \"acqGroup\","); 
						rpcTree_json.append("\"leaf\": true},");
					}
				}
				if(rpcTree_json.toString().endsWith(",")){
					rpcTree_json.deleteCharAt(rpcTree_json.length() - 1);
				}
				
				rpcTree_json.append("]");
				
				rpcTree_json.append("}]");
				
				rpcTree_json.append("},");
			}else{
				pcpTree_json.append("{\"classes\":1,");
				pcpTree_json.append("\"id\":\""+obj[0]+"\",");
				pcpTree_json.append("\"text\":\""+obj[1]+"\",");
				pcpTree_json.append("\"code\":\""+obj[2]+"\",");
				pcpTree_json.append("\"acqProtocolType\":\""+obj[3]+"\",");
				pcpTree_json.append("\"ctrlProtocolType\":\""+obj[4]+"\",");
				pcpTree_json.append("\"signInPrefix\":\""+obj[5]+"\",");
				pcpTree_json.append("\"signInSuffix\":\""+obj[6]+"\",");
				pcpTree_json.append("\"heartbeatPrefix\":\""+obj[7]+"\",");
				pcpTree_json.append("\"heartbeatSuffix\":\""+obj[8]+"\",");
				pcpTree_json.append("\"packetSendInterval\":\""+obj[9]+"\",");
				pcpTree_json.append("\"prefixSuffixHex\":\""+obj[10]+"\",");
				pcpTree_json.append("\"deviceType\":"+obj[11]+",");
				pcpTree_json.append("\"sort\":\""+obj[12]+"\",");
				pcpTree_json.append("\"unitId\":"+obj[13]+",");
				pcpTree_json.append("\"unitName\":\""+obj[14]+"\",");
				pcpTree_json.append("\"iconCls\": \"protocol\",");
				pcpTree_json.append("\"expanded\": true,");
				
				pcpTree_json.append("\"children\": [");
				pcpTree_json.append("{\"classes\":2,");
				pcpTree_json.append("\"text\":\""+obj[14]+"\",");
				pcpTree_json.append("\"id\":"+obj[13]+",");
				pcpTree_json.append("\"iconCls\": \"acqUnit\","); 
				pcpTree_json.append("\"expanded\": true,");
				pcpTree_json.append("\"children\": [");
				for(int j=0;j<groupList.size();j++){
					Object[] groupObj = (Object[]) groupList.get(j);
					if((obj[13]+"").equalsIgnoreCase(groupObj[3]+"")){
						pcpTree_json.append("{\"classes\":3,");
						pcpTree_json.append("\"id\":"+groupObj[0]+",");
						pcpTree_json.append("\"text\":\""+groupObj[1]+"\",");
						pcpTree_json.append("\"code\":\""+groupObj[2]+"\",");
						pcpTree_json.append("\"iconCls\": \"acqGroup\","); 
						pcpTree_json.append("\"leaf\": true},");
					}
				}
				if(pcpTree_json.toString().endsWith(",")){
					pcpTree_json.deleteCharAt(pcpTree_json.length() - 1);
				}
				
				pcpTree_json.append("]");
				pcpTree_json.append("}]");
				
				pcpTree_json.append("},");
			}
		}
		
		if(rpcTree_json.toString().endsWith(",")){
			rpcTree_json.deleteCharAt(rpcTree_json.length() - 1);
		}
		rpcTree_json.append("]");
		
		if(pcpTree_json.toString().endsWith(",")){
			pcpTree_json.deleteCharAt(pcpTree_json.length() - 1);
		}
		pcpTree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"抽油机井\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+rpcTree_json+"},");
		result_json.append("{\"classes\":0,\"text\":\"螺杆泵井\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pcpTree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusDisplayProtocolInstanceConfigTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer rpcTree_json = new StringBuffer();
		StringBuffer pcpTree_json = new StringBuffer();
		rpcTree_json.append("[");
		pcpTree_json.append("[");
		String sql="select t.id,t.name,t.code,t.displayUnitId,t2.unit_name,t.devicetype,t.sort   "
				+ " from tbl_protocoldisplayinstance t ,tbl_display_unit_conf t2 where t.displayUnitId=t2.id "
				+ " order by t.devicetype,t.sort";
		List<?> list=this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			if(StringManagerUtils.stringToInteger(obj[5]+"")==0){
				rpcTree_json.append("{\"classes\":1,");
				rpcTree_json.append("\"id\":\""+obj[0]+"\",");
				rpcTree_json.append("\"text\":\""+obj[1]+"\",");
				rpcTree_json.append("\"code\":\""+obj[2]+"\",");
				rpcTree_json.append("\"displayUnitId\":"+obj[3]+",");
				rpcTree_json.append("\"displayUnitName\":\""+obj[4]+"\",");
				rpcTree_json.append("\"deviceType\":"+obj[5]+",");
				rpcTree_json.append("\"sort\":\""+obj[6]+"\",");
				rpcTree_json.append("\"iconCls\": \"protocol\",");
				rpcTree_json.append("\"expanded\": true,");
				rpcTree_json.append("\"children\": [");
				rpcTree_json.append("{\"classes\":2,");
				rpcTree_json.append("\"id\":"+obj[3]+",");
				rpcTree_json.append("\"text\":\""+obj[4]+"\",");
				rpcTree_json.append("\"iconCls\": \"acqUnit\","); 
				rpcTree_json.append("\"leaf\": true}");
				rpcTree_json.append("]");
				rpcTree_json.append("},");
			}else{
				pcpTree_json.append("{\"classes\":1,");
				pcpTree_json.append("\"id\":\""+obj[0]+"\",");
				pcpTree_json.append("\"text\":\""+obj[1]+"\",");
				pcpTree_json.append("\"code\":\""+obj[2]+"\",");
				pcpTree_json.append("\"displayUnitId\":"+obj[3]+",");
				pcpTree_json.append("\"displayUnitName\":\""+obj[4]+"\",");
				pcpTree_json.append("\"deviceType\":"+obj[5]+",");
				pcpTree_json.append("\"sort\":\""+obj[6]+"\",");
				pcpTree_json.append("\"iconCls\": \"protocol\",");
				pcpTree_json.append("\"expanded\": true,");
				pcpTree_json.append("\"children\": [");
				pcpTree_json.append("{\"classes\":2,");
				pcpTree_json.append("\"id\":"+obj[3]+",");
				pcpTree_json.append("\"text\":\""+obj[4]+"\",");
				pcpTree_json.append("\"iconCls\": \"acqUnit\","); 
				pcpTree_json.append("\"leaf\": true}");
				pcpTree_json.append("]");
				pcpTree_json.append("},");
			}
		}
		
		if(rpcTree_json.toString().endsWith(",")){
			rpcTree_json.deleteCharAt(rpcTree_json.length() - 1);
		}
		rpcTree_json.append("]");
		
		if(pcpTree_json.toString().endsWith(",")){
			pcpTree_json.deleteCharAt(pcpTree_json.length() - 1);
		}
		pcpTree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"抽油机井\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+rpcTree_json+"},");
		result_json.append("{\"classes\":0,\"text\":\"螺杆泵井\",\"deviceType\":1,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pcpTree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusAlarmProtocolInstanceConfigTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer rpcTree_json = new StringBuffer();
		StringBuffer pcpTree_json = new StringBuffer();
		rpcTree_json.append("[");
		pcpTree_json.append("[");
		String sql="select t.id,t.name,t.code,t.alarmUnitId,t2.unit_name,t.devicetype,t.sort   "
				+ " from tbl_protocolalarminstance t ,tbl_alarm_unit_conf t2 where t.alarmunitid=t2.id "
				+ " order by t.devicetype,t.sort";
		List<?> list=this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			if(StringManagerUtils.stringToInteger(obj[5]+"")==0){
				rpcTree_json.append("{\"classes\":1,");
				rpcTree_json.append("\"id\":\""+obj[0]+"\",");
				rpcTree_json.append("\"text\":\""+obj[1]+"\",");
				rpcTree_json.append("\"code\":\""+obj[2]+"\",");
				rpcTree_json.append("\"alarmUnitId\":"+obj[3]+",");
				rpcTree_json.append("\"alarmUnitName\":\""+obj[4]+"\",");
				rpcTree_json.append("\"deviceType\":"+obj[5]+",");
				rpcTree_json.append("\"sort\":\""+obj[6]+"\",");
				rpcTree_json.append("\"iconCls\": \"protocol\",");
				rpcTree_json.append("\"expanded\": true,");
				rpcTree_json.append("\"children\": [");
				rpcTree_json.append("{\"classes\":2,");
				rpcTree_json.append("\"id\":"+obj[3]+",");
				rpcTree_json.append("\"deviceType\":"+obj[5]+",");
				rpcTree_json.append("\"text\":\""+obj[4]+"\",");
				rpcTree_json.append("\"iconCls\": \"acqGroup\","); 
				rpcTree_json.append("\"leaf\": true}");
				rpcTree_json.append("]");
				rpcTree_json.append("},");
			}else{
				pcpTree_json.append("{\"classes\":1,");
				pcpTree_json.append("\"id\":\""+obj[0]+"\",");
				pcpTree_json.append("\"text\":\""+obj[1]+"\",");
				pcpTree_json.append("\"code\":\""+obj[2]+"\",");
				pcpTree_json.append("\"alarmUnitId\":"+obj[3]+",");
				pcpTree_json.append("\"alarmUnitName\":\""+obj[4]+"\",");
				pcpTree_json.append("\"deviceType\":"+obj[5]+",");
				pcpTree_json.append("\"sort\":\""+obj[6]+"\",");
				pcpTree_json.append("\"iconCls\": \"protocol\",");
				pcpTree_json.append("\"expanded\": true,");
				pcpTree_json.append("\"children\": [");
				pcpTree_json.append("{\"classes\":2,");
				pcpTree_json.append("\"deviceType\":"+1+",");
				pcpTree_json.append("\"id\":"+obj[3]+",");
				pcpTree_json.append("\"deviceType\":"+obj[5]+",");
				pcpTree_json.append("\"text\":\""+obj[4]+"\",");
				pcpTree_json.append("\"iconCls\": \"acqGroup\","); 
				pcpTree_json.append("\"leaf\": true}");
				pcpTree_json.append("]");
				pcpTree_json.append("},");
			}
		}
		
		if(rpcTree_json.toString().endsWith(",")){
			rpcTree_json.deleteCharAt(rpcTree_json.length() - 1);
		}
		rpcTree_json.append("]");
		
		if(pcpTree_json.toString().endsWith(",")){
			pcpTree_json.deleteCharAt(pcpTree_json.length() - 1);
		}
		pcpTree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"deviceType\":0,\"text\":\"抽油机井\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+rpcTree_json+"},");
		result_json.append("{\"classes\":0,\"deviceType\":1,\"text\":\"螺杆泵井\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+pcpTree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusProtoclCombList(){
		StringBuffer result_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
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
		String sql="select t.id,t.name,t.mappingcolumn,t.calcolumn from tbl_datamapping t where t.protocoltype="+deviceType+" order by t.id";
		String columns="["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"itemName\" ,children:[] },"
				+ "{ \"header\":\"字段\",\"dataIndex\":\"itemColumn\",children:[] },"
				+ "{ \"header\":\"计算字段\",\"dataIndex\":\"calColumn\",children:[] }"
				+ "]";
		List<?> list=this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":" + list.size() + ",\"columns\":"+columns+",\"totalRoot\":[");
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"itemName\":\""+obj[1]+"\",");
			result_json.append("\"itemColumn\":\""+obj[2]+"\",");
			result_json.append("\"calColumn\":\""+obj[3]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public void saveDatabaseColumnMappingTable(DatabaseMappingProHandsontableChangedData databaseMappingProHandsontableChangedData,String protocolType) throws Exception {
		if(databaseMappingProHandsontableChangedData.getUpdatelist()!=null){
			String updateSql="";
			for(int i=0;i<databaseMappingProHandsontableChangedData.getUpdatelist().size();i++){
				updateSql="update tbl_datamapping t set t.calcolumn='"+databaseMappingProHandsontableChangedData.getUpdatelist().get(i).getCalColumn()+"'"
						+ " where t.name='"+databaseMappingProHandsontableChangedData.getUpdatelist().get(i).getItemName()+"' "
						+ " and t.mappingcolumn='"+databaseMappingProHandsontableChangedData.getUpdatelist().get(i).getItemColumn()+"' "
						+ " and t.protocoltype="+protocolType;
				getBaseDao().updateOrDeleteBySql(updateSql);
			}
		}
	}
	
	public boolean judgeProtocolExistOrNot(int deviceType,String protocolName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(protocolName)) {
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
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
	
	public boolean judgeDisplayInstanceExistOrNot(int deviceType,String instanceName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(instanceName)) {
			String sql = "select t.id from TBL_PROTOCOLDISPLAYINSTANCE t where t.devicetype="+deviceType+" and t.name='"+instanceName+"'";
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
		
		String delDisplayItem="delete from TBL_DISPLAY_ITEMS2UNIT_CONF t "
				+ " where t.type<>1 "
				+ " and t.type in (select decode(t8.type,1,2,t8.type) from tbl_acq_group_conf t8 where t8.id="+ids+" )"
				+ " and t.unitid in (select t2.id from tbl_display_unit_conf t2,tbl_acq_unit_conf t3,tbl_acq_group2unit_conf t4,tbl_acq_group_conf t5 "
				+ "      where t2.acqunitid=t3.id and t3.id=t4.unitid and t4.groupid=t5.id and t5.id="+ids+") ";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(delDisplayItem);
		
		String sql="delete from TBL_ACQ_ITEM2GROUP_CONF t where t.groupid in ("+ids+")";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_acq_group2unit_conf t where t.groupid in("+ids+")";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		final String hql = "DELETE AcquisitionGroup u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}
	
	public void doAcquisitionGroupOwnItemChange(final String ids) throws Exception {
		String sql="delete from TBL_DISPLAY_ITEMS2UNIT_CONF t "
				+ " where t.type<>1 "
				+ " and t.type in (select decode(t8.type,1,2,t8.type) from tbl_acq_group_conf t8 where t8.id="+ids+" )"
				+ " and t.unitid in (select t2.id from tbl_display_unit_conf t2,tbl_acq_unit_conf t3,tbl_acq_group2unit_conf t4,tbl_acq_group_conf t5 "
				+ "      where t2.acqunitid=t3.id and t3.id=t4.unitid and t4.groupid=t5.id and t5.id="+ids+") "
				+ " and t.itemname not in( select t7.itemname from tbl_acq_group_conf t6,tbl_acq_item2group_conf t7 where t6.id=t7.groupid and t6.id="+ids+"  )";
		this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public void doAcquisitionUnitAdd(T acquisitionUnit) throws Exception {
		getBaseDao().addObject(acquisitionUnit);
	}
	
	public void doAcquisitionUnitEdit(T acquisitionUnit) throws Exception {
		getBaseDao().updateObject(acquisitionUnit);
	}
	
	public void doAcquisitionUnitBulkDelete(final String ids,String deviceType) throws Exception {
		int delorUpdateCount=0;
		String tableName="tbl_rpcdevice";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="tbl_pcpdevice";
		}
		//处理显示实例、显示单元数据
		String sql = "update "+tableName+" t set t.displayinstancecode='' where t.displayinstancecode in ( select t2.code from tbl_protocoldisplayinstance t2,tbl_display_unit_conf t3 where t2.displayunitid=t3.id and t3.acqunitid in("+ids+") )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
				
		sql="delete from tbl_display_items2unit_conf t where t.unitid in ( select t2.id from tbl_display_unit_conf t2 where t2.acqunitid in("+ids+") )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
				
		sql="delete from tbl_protocoldisplayinstance t where t.displayunitid in ( select t2.id from tbl_display_unit_conf t2 where t2.acqunitid in("+ids+") )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
				
		sql="delete from tbl_display_unit_conf t where t.acqunitid in( "+ids+")";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		//处理采集实例、采集单元、采集组数据
		sql = "update "+tableName+" t set t.instancecode='' where t.instancecode in ( select t2.code from tbl_protocolinstance t2  where t2.unitid in ("+ids+") )";
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
		String tableName="tbl_rpcdevice";
		if(deviceType==1){
			tableName="tbl_pcpdevice";
		}
		//处理显示实例、显示单元数据
		String sql = "update "+tableName+" t set t.displayinstancecode='' where t.displayinstancecode in ( select t2.code from tbl_protocoldisplayinstance t2,tbl_display_unit_conf t3,tbl_acq_unit_conf t4 where t2.displayunitid=t3.id and t3.acqunitid=t4.id and t4.protocol='"+protocolName+"' )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_display_items2unit_conf t where t.unitid in ( select t2.id from tbl_display_unit_conf t2,tbl_acq_unit_conf t3 where t2.acqunitid=t3.id and t3.protocol='"+protocolName+"' )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_protocoldisplayinstance t where t.displayunitid in ( select t2.id from tbl_display_unit_conf t2,tbl_acq_unit_conf t3 where t2.acqunitid=t3.id and t3.protocol='"+protocolName+"' )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_display_unit_conf t where t.acqunitid in( select t2.id from tbl_acq_unit_conf t2 where t2.protocol='"+protocolName+"')";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		//处理报警实例、报警单元数据
		sql = "update "+tableName+" t set t.alarminstancecode='' where t.alarminstancecode in ( select t2.code from tbl_protocolalarminstance t2,tbl_alarm_unit_conf t3 where t2.alarmunitid=t3.id and t3.protocol='"+protocolName+"' )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_alarm_item2unit_conf t where t.unitid in( select id from tbl_alarm_unit_conf t2 where t2.protocol='"+protocolName+"' )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_protocolalarminstance t where t.alarmunitid in( select id from tbl_alarm_unit_conf t2 where t2.protocol='"+protocolName+"' )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_alarm_unit_conf t where t.protocol ='"+protocolName+"'";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		//处理采集实例、采集单元、采集组数据
		sql = "update "+tableName+" t set t.instancecode='' where t.instancecode in ( select t2.code from tbl_protocolinstance t2,tbl_acq_unit_conf t3 where t2.unitid=t3.id and t3.protocol='"+protocolName+"' )";
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
	
	public void grantDisplayItemsPermission(T displayUnitItem) throws Exception {
		getBaseDao().saveOrUpdateObject(displayUnitItem);
	}
	
	public void deleteCurrentDisplayUnitOwnItems(final String unitId,final String itemType) throws Exception {
		final String hql = "DELETE DisplayUnitItem u where u.unitId ="+unitId+" and u.type="+itemType;
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void doModbusProtocolInstanceAdd(T protocolInstance) throws Exception {
		getBaseDao().addObject(protocolInstance);
	}
	public void doModbusProtocolInstanceEdit(T protocolInstance) throws Exception {
		getBaseDao().updateObject(protocolInstance);
	}
	
	public void doModbusProtocolInstanceBulkDelete(final String ids,int deviceType) throws Exception {
		String tableName="tbl_rpcdevice";
		if(deviceType==1){
			tableName="tbl_pcpdevice";
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
	
	public Serializable doDisplayUnitAdd(T displayUnit) throws Exception {
		return getBaseDao().addObject(displayUnit);
	}
	
	public void doDisplayUnitBulkDelete(final String ids,String deviceType) throws Exception {
		int delorUpdateCount=0;
		String tableName="tbl_rpcdevice";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			tableName="tbl_pcpdevice";
		}
		String sql = "update "+tableName+" t set t.instancecode='' where t.instancecode in ( select t2.code from tbl_protocolinstance t2  where t2.unitid in ("+ids+") )";
//		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from TBL_DISPLAY_ITEMS2UNIT_CONF t where t.unitid in("+ids+")";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		
		sql="delete from tbl_protocolinstance t where t.unitid in("+ids+")";
//		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		final String hql = "DELETE DisplayUnit u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}
	
	public void doDisplayUnitEdit(T displayUnit) throws Exception {
		getBaseDao().updateObject(displayUnit);
	}
	
	public void doModbusProtocolAlarmUnitDelete(final String ids) throws Exception {
		String delItemHql = "DELETE AlarmUnitItem u where u.unitId in (" + ids + ")";
		String hql = "DELETE AlarmUnit u where u.id in (" + ids + ")";
		getBaseDao().bulkObjectDelete(delItemHql);
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void deleteCurrentAlarmUnitOwnItems(ModbusProtocolAlarmUnitSaveData modbusProtocolAlarmUnitSaveData) throws Exception {
		String hql = "DELETE AlarmUnitItem u where u.unitId ="+modbusProtocolAlarmUnitSaveData.getId()+" and u.type="+modbusProtocolAlarmUnitSaveData.getResolutionMode();
		if(modbusProtocolAlarmUnitSaveData.getResolutionMode()==2){
			hql = "DELETE AlarmUnitItem u where u.unitId ="+modbusProtocolAlarmUnitSaveData.getId()+" and u.type in(2,5)";
		}
		if(modbusProtocolAlarmUnitSaveData.getResolutionMode()==0 || modbusProtocolAlarmUnitSaveData.getResolutionMode()==1 &&StringManagerUtils.isNotNull(modbusProtocolAlarmUnitSaveData.getAlarmItemName())){
			hql+=" and u.itemName='"+modbusProtocolAlarmUnitSaveData.getAlarmItemName()+"' and u.itemAddr="+modbusProtocolAlarmUnitSaveData.getAlarmItemAddr();
		}
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void grantAlarmItemsPermission(T alarmUnitItem) throws Exception {
		getBaseDao().addObject(alarmUnitItem);
	}
	
	public void doModbusProtocolDisplayInstanceEdit(T protocolDisplayInstance) throws Exception {
		getBaseDao().updateObject(protocolDisplayInstance);
	}
	
	public void doModbusProtocolAlarmInstanceEdit(T protocolAlarmInstance) throws Exception {
		getBaseDao().updateObject(protocolAlarmInstance);
	}
	
	public void doModbusProtocolAlarmInstanceBulkDelete(final String ids) throws Exception {
		final String hql = "DELETE ProtocolAlarmInstance u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}
	
	public void doModbusProtocolDisplayInstanceBulkDelete(final String ids) throws Exception {
		final String hql = "DELETE ProtocolDisplayInstance u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}
	
	public void doModbusProtocolDisplayInstanceAdd(T protocolDisplayInstance) throws Exception {
		getBaseDao().addObject(protocolDisplayInstance);
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
