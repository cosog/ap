package com.cosog.service.acqUnit;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AcquisitionGroup;
import com.cosog.model.AcquisitionGroupItem;
import com.cosog.model.AcquisitionUnit;
import com.cosog.model.AcquisitionUnitGroup;
import com.cosog.model.AlarmUnit;
import com.cosog.model.AlarmUnitItem;
import com.cosog.model.CurveConf;
import com.cosog.model.DataMapping;
import com.cosog.model.DisplayUnit;
import com.cosog.model.DisplayUnitItem;
import com.cosog.model.ProtocolAlarmInstance;
import com.cosog.model.ProtocolDisplayInstance;
import com.cosog.model.ProtocolInstance;
import com.cosog.model.ProtocolModel;
import com.cosog.model.ProtocolRunStatusConfig;
import com.cosog.model.ProtocolSMSInstance;
import com.cosog.model.ReportTemplate;
import com.cosog.model.ReportTemplate.Template;
import com.cosog.model.calculate.CalculateColumnInfo;
import com.cosog.model.calculate.CalculateColumnInfo.CalculateColumn;
import com.cosog.model.drive.ExportProtocolConfig;
import com.cosog.model.drive.ImportProtocolContent;
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
import com.cosog.utils.Page;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
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
				+ "{ \"header\":\"响应模式\",\"dataIndex\":\"acqMode\",width:80 ,children:[] },"
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] }"
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
				+ "{ \"header\":\"响应模式\",\"dataIndex\":\"acqMode\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示级别\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线顺序\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线颜色\",\"dataIndex\":\"realtimeCurveColor\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线顺序\",\"dataIndex\":\"historyCurve\",width:80 ,children:[] },"
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
				+ "{ \"header\":\"数值\",\"dataIndex\":\"title\",width:120 ,children:[] },"
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
		List<byte[]> calItemSet=null;
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
				+ "{ \"header\":\"数值\",\"dataIndex\":\"value\",width:120 ,children:[] },"
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
		java.lang.reflect.Type type=null;
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示级别\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurveConf\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveConf\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> acqItemsList=new ArrayList<String>();
		List<String> acqItemsBitIndexList=new ArrayList<String>();
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveConfList=new ArrayList<String>();
		List<String> historyCurveConfList=new ArrayList<String>();
		if("2".equalsIgnoreCase(classes)){
			String acqUnitIiemsSql="select distinct t.itemname,t.bitindex "
					+ "from TBL_ACQ_ITEM2GROUP_CONF t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4 "
					+ "where t.groupid=t2.id  and t2.id=t3.groupid and t3.unitid=t4.id and t4.id="+acqUnitId+" and t2.type=0";
			
			String sql="select t.itemname,t.bitindex,t.sort,t.showlevel,t.realtimeCurveConf,historyCurveConf "
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
				
				String realtimeCurveConf=obj[4]+"";
				if(!StringManagerUtils.isNotNull(obj[4]+"")){
					realtimeCurveConf="\"\"";
				}
				String historyCurveConf=obj[5]+"";
				if(!StringManagerUtils.isNotNull(obj[5]+"")){
					historyCurveConf="\"\"";
				}
				
				realtimeCurveConfList.add(realtimeCurveConf);
				historyCurveConfList.add(historyCurveConf);
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
							String realtimeCurveConf="\"\"";
							String realtimeCurveConfShowValue="";
							String historyCurveConf="\"\"";
							String historyCurveConfShowValue="";
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
									realtimeCurveConf="\"\"";
									realtimeCurveConfShowValue="";
									historyCurveConf="\"\"";
									historyCurveConfShowValue="";
									
									for(int m=0;m<itemsList.size();m++){
										if(itemsList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())
												&&itemsBitIndexList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"")
											){
											checked=true;
											sort=itemsSortList.get(m);
											showLevel=itemsShowLevelList.get(m);
											realtimeCurveConf=realtimeCurveConfList.get(m);
											historyCurveConf=historyCurveConfList.get(m);
											
											CurveConf realtimeCurveConfObj=null;
											if(StringManagerUtils.isNotNull(realtimeCurveConf) && !"\"\"".equals(realtimeCurveConf)){
												type = new TypeToken<CurveConf>() {}.getType();
												realtimeCurveConfObj=gson.fromJson(realtimeCurveConf, type);
											}
											
											CurveConf historyCurveConfObj=null;
											if(StringManagerUtils.isNotNull(historyCurveConf) && !"\"\"".equals(historyCurveConf)){
												type = new TypeToken<CurveConf>() {}.getType();
												historyCurveConfObj=gson.fromJson(historyCurveConf, type);
											}
											
											if(realtimeCurveConfObj!=null){
												realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+realtimeCurveConfObj.getColor();
											}
											if(historyCurveConfObj!=null){
												historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+historyCurveConfObj.getColor();
											}
											
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
											+ "\"realtimeCurveConf\":"+realtimeCurveConf+","
											+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
											+ "\"historyCurveConf\":"+historyCurveConf+","
											+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\""
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
											
											realtimeCurveConf=realtimeCurveConfList.get(k);
											historyCurveConf=historyCurveConfList.get(k);
											
											CurveConf realtimeCurveConfObj=null;
											if(StringManagerUtils.isNotNull(realtimeCurveConf) && !"\"\"".equals(realtimeCurveConf)){
												type = new TypeToken<CurveConf>() {}.getType();
												realtimeCurveConfObj=gson.fromJson(realtimeCurveConf, type);
											}
											
											CurveConf historyCurveConfObj=null;
											if(StringManagerUtils.isNotNull(historyCurveConf) && !"\"\"".equals(historyCurveConf)){
												type = new TypeToken<CurveConf>() {}.getType();
												historyCurveConfObj=gson.fromJson(historyCurveConf, type);
											}
											
											if(realtimeCurveConfObj!=null){
												realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+realtimeCurveConfObj.getColor();
											}
											if(historyCurveConfObj!=null){
												historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+historyCurveConfObj.getColor();
											}
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
										+ "\"realtimeCurveConf\":"+realtimeCurveConf+","
										+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
										+ "\"historyCurveConf\":"+historyCurveConf+","
										+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\""
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
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] }"
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
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String key="rpcCalItemList";
		if("1".equalsIgnoreCase(deviceType)){
			key="pcpCalItemList";
		}
		Jedis jedis=null;
		List<byte[]> rpcCalItemSet=null;
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
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurveConf\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveConf\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsCodeList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveConfList=new ArrayList<String>();
		List<String> historyCurveConfList=new ArrayList<String>();
		if("2".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.sort,t.showlevel,t.realtimeCurveConf,t.historyCurveConf "
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
				String realtimeCurveConf=obj[4]+"";
				if(!StringManagerUtils.isNotNull(obj[4]+"")){
					realtimeCurveConf="\"\"";
				}
				String historyCurveConf=obj[5]+"";
				if(!StringManagerUtils.isNotNull(obj[5]+"")){
					historyCurveConf="\"\"";
				}
				
				realtimeCurveConfList.add(realtimeCurveConf);
				historyCurveConfList.add(historyCurveConf);
			}
		}
		
		int index=1;
		if(rpcCalItemSet!=null){
			for(byte[] rpcCalItemByteArr:rpcCalItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
				
				boolean checked=false;
				String sort="";
				String showLevel="";
				String realtimeCurveConf="\"\"";
				String realtimeCurveConfShowValue="";
				String historyCurveConf="\"\"";
				String historyCurveConfShowValue="";

				checked=StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(),false);
				if(checked){
					for(int k=0;k<itemsList.size();k++){
						if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
							sort=itemsSortList.get(k);
							showLevel=itemsShowLevelList.get(k);
							realtimeCurveConf=realtimeCurveConfList.get(k);
							historyCurveConf=historyCurveConfList.get(k);
							
							CurveConf realtimeCurveConfObj=null;
							if(StringManagerUtils.isNotNull(realtimeCurveConf) && !"\"\"".equals(realtimeCurveConf)){
								type = new TypeToken<CurveConf>() {}.getType();
								realtimeCurveConfObj=gson.fromJson(realtimeCurveConf, type);
							}
							
							CurveConf historyCurveConfObj=null;
							if(StringManagerUtils.isNotNull(historyCurveConf) && !"\"\"".equals(historyCurveConf)){
								type = new TypeToken<CurveConf>() {}.getType();
								historyCurveConfObj=gson.fromJson(historyCurveConf, type);
							}
							
							if(realtimeCurveConfObj!=null){
								realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+realtimeCurveConfObj.getColor();
							}
							if(historyCurveConfObj!=null){
								historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+historyCurveConfObj.getColor();
							}
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
						+ "\"realtimeCurveConf\":"+realtimeCurveConf+","
						+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
						+ "\"historyCurveConf\":"+historyCurveConf+","
						+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\","
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
	
	public String getProtocolDisplayUnitInputItemsConfigData(String deviceType,String classes,String unitId){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String key="rpcInputItemList";
		if("1".equalsIgnoreCase(deviceType)){
			key="pcpInputItemList";
		}
		Jedis jedis=null;
		List<byte[]> inputItemSet=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists(key.getBytes())){
				if("1".equalsIgnoreCase(deviceType)){
					MemoryDataManagerTask.loadPCPInputItem();
				}else{
					MemoryDataManagerTask.loadRPCInputItem();
				}
			}
			inputItemSet= jedis.zrange(key.getBytes(), 0, -1);
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
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurveConf\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveConf\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsCodeList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveConfList=new ArrayList<String>();
		List<String> historyCurveConfList=new ArrayList<String>();
		if("2".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.sort,t.showlevel,t.realtimeCurveConf,t.historyCurveConf "
					+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2 "
					+ " where t.unitid=t2.id and t2.id= "+unitId+" and t.type=3"
					+ " order by t.sort";
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsCodeList.add(obj[1]+"");
				itemsSortList.add(obj[2]+"");
				itemsShowLevelList.add(obj[3]+"");
				String realtimeCurveConf=obj[4]+"";
				if(!StringManagerUtils.isNotNull(obj[4]+"")){
					realtimeCurveConf="\"\"";
				}
				String historyCurveConf=obj[5]+"";
				if(!StringManagerUtils.isNotNull(obj[5]+"")){
					historyCurveConf="\"\"";
				}
				
				realtimeCurveConfList.add(realtimeCurveConf);
				historyCurveConfList.add(historyCurveConf);
			}
		}
		
		int index=1;
		if(inputItemSet!=null){
			for(byte[] rpcCalItemByteArr:inputItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
				
				boolean checked=false;
				String sort="";
				String showLevel="";
				String realtimeCurveConf="\"\"";
				String realtimeCurveConfShowValue="";
				String historyCurveConf="\"\"";
				String historyCurveConfShowValue="";

				checked=StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(),false);
				if(checked){
					for(int k=0;k<itemsList.size();k++){
						if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
							sort=itemsSortList.get(k);
							showLevel=itemsShowLevelList.get(k);
							realtimeCurveConf=realtimeCurveConfList.get(k);
							historyCurveConf=historyCurveConfList.get(k);
							
							CurveConf realtimeCurveConfObj=null;
							if(StringManagerUtils.isNotNull(realtimeCurveConf) && !"\"\"".equals(realtimeCurveConf)){
								type = new TypeToken<CurveConf>() {}.getType();
								realtimeCurveConfObj=gson.fromJson(realtimeCurveConf, type);
							}
							
							CurveConf historyCurveConfObj=null;
							if(StringManagerUtils.isNotNull(historyCurveConf) && !"\"\"".equals(historyCurveConf)){
								type = new TypeToken<CurveConf>() {}.getType();
								historyCurveConfObj=gson.fromJson(historyCurveConf, type);
							}
							
							if(realtimeCurveConfObj!=null){
								realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+realtimeCurveConfObj.getColor();
							}
							if(historyCurveConfObj!=null){
								historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+historyCurveConfObj.getColor();
							}
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
						+ "\"realtimeCurveConf\":"+realtimeCurveConf+","
						+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
						+ "\"historyCurveConf\":"+historyCurveConf+","
						+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\","
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
	
	public String getReportUnitTotalCalItemsConfigData(String deviceType,String reportType,String unitId,String classes){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String key="rpcTotalCalItemList";
		if("1".equalsIgnoreCase(deviceType)){
			key="pcpTotalCalItemList";
		}
		Jedis jedis=null;
		List<byte[]> rpcCalItemSet=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists(key.getBytes())){
				if("1".equalsIgnoreCase(deviceType)){
					MemoryDataManagerTask.loadPCPTotalCalculateItem();
				}else{
					MemoryDataManagerTask.loadRPCTotalCalculateItem();
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
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"小数位数\",\"dataIndex\":\"prec\",width:80 ,children:[] },"
				+ "{ \"header\":\"求和\",\"dataIndex\":\"sumSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"求平均\",\"dataIndex\":\"averageSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"报表曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"报曲线统计类型\",\"dataIndex\":\"curveStatType\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsCodeList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		
		List<String> itemsPrecList=new ArrayList<String>();
		
		List<String> sumSignList=new ArrayList<String>();
		List<String> averageSignList=new ArrayList<String>();
		
		List<String> reportCurveConfList=new ArrayList<String>();
		
		List<String> curveStatTypeList=new ArrayList<String>();
		if("1".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.sort,t.showlevel,t.sumsign,t.averagesign,t.reportCurveconf,t.curvestattype,t.prec "
					+ " from tbl_report_items2unit_conf t "
					+ " where t.unitid="+unitId+" and t.reportType="+reportType
					+ " order by t.sort";
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsCodeList.add(obj[1]+"");
				itemsSortList.add(obj[2]+"");
				itemsShowLevelList.add(obj[3]+"");
				
				sumSignList.add(obj[4]+"");
				averageSignList.add(obj[5]+"");
				
				String reportCurveConf=obj[6]+"";
				if(!StringManagerUtils.isNotNull(reportCurveConf)){
					reportCurveConf="\"\"";
				}
				reportCurveConfList.add(reportCurveConf);
				curveStatTypeList.add(obj[7]+"");
				
				itemsPrecList.add(obj[8]+"");
			}
		}
		
		int index=1;
		if(rpcCalItemSet!=null){
			for(byte[] rpcCalItemByteArr:rpcCalItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
				
				boolean checked=false;
				String sort="";
				String showLevel="";
				
				String prec="";
				
				boolean sumSign=false;
				boolean averageSign=false;
				
				String reportCurveConf="\"\"";
				String reportCurveConfShowValue="";
				
				String curveStatType="";

				checked=StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(),false);
				if(checked){
					for(int k=0;k<itemsList.size();k++){
						if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
							sort=itemsSortList.get(k);
							showLevel=itemsShowLevelList.get(k);
							
							prec=itemsPrecList.get(k);
							
							if(StringManagerUtils.isNum(sumSignList.get(k))||StringManagerUtils.isNumber(sumSignList.get(k))){
								if(StringManagerUtils.stringToInteger(sumSignList.get(k))==1){
									sumSign=true;
								}else{
									sumSign=false;
								}
							}
							
							if(StringManagerUtils.isNum(averageSignList.get(k))||StringManagerUtils.isNumber(averageSignList.get(k))){
								if(StringManagerUtils.stringToInteger(averageSignList.get(k))==1){
									averageSign=true;
								}else{
									averageSign=false;
								}
							}
							
							reportCurveConf=reportCurveConfList.get(k);
							
							CurveConf reportCurveConfObj=null;
							if(StringManagerUtils.isNotNull(reportCurveConf) && !"\"\"".equals(reportCurveConf)){
								type = new TypeToken<CurveConf>() {}.getType();
								reportCurveConfObj=gson.fromJson(reportCurveConf, type);
							}
							
							if(reportCurveConfObj!=null){
								reportCurveConfShowValue=reportCurveConfObj.getSort()+";"+reportCurveConfObj.getColor();
							}
							
							
							String curveStatTypeStr=curveStatTypeList.get(k).replaceAll("null", "");
							if(StringManagerUtils.isNum(curveStatTypeStr) || StringManagerUtils.isNumber(curveStatTypeStr)){
								if(StringManagerUtils.stringToInteger(curveStatTypeStr)==1){
									curveStatType="合计";
								}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==2){
									curveStatType="平均";
								}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==3){
									curveStatType="最大值";
								}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==4){
									curveStatType="最小值";
								}
							}
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
						+ "\"prec\":\""+prec+"\","
						+ "\"sumSign\":"+sumSign+","
						+ "\"averageSign\":"+averageSign+","
						+ "\"reportCurveConfShowValue\":\""+reportCurveConfShowValue+"\","
						+ "\"reportCurveConf\":"+reportCurveConf+","
						+ "\"curveStatType\":\""+curveStatType+"\","
						+ "\"dataType\":"+calItem.getDataType()+","
						+ "\"code\":\""+calItem.getCode()+"\","
						+ "\"remark\":\""+calItem.getRemark()+"\""
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
	
	public String getReportUnitTimingTotalCalItemsConfigData(String deviceType,String reportType,String unitId,String classes){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String key="rpcTimingTotalCalItemList";
		if("1".equalsIgnoreCase(deviceType)){
			key="pcpTimingTotalCalItemList";
		}
		Jedis jedis=null;
		List<byte[]> rpcCalItemSet=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists(key.getBytes())){
				if("1".equalsIgnoreCase(deviceType)){
					MemoryDataManagerTask.loadPCPTimingTotalCalculateItem();
				}else{
					MemoryDataManagerTask.loadRPCTimingTotalCalculateItem();
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
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"小数位数\",\"dataIndex\":\"prec\",width:80 ,children:[] },"
				+ "{ \"header\":\"求和\",\"dataIndex\":\"sumSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"求平均\",\"dataIndex\":\"averageSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"报表曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"报曲线统计类型\",\"dataIndex\":\"curveStatType\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsCodeList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsPrecList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		
		List<String> sumSignList=new ArrayList<String>();
		List<String> averageSignList=new ArrayList<String>();
		
		List<String> reportCurveConfList=new ArrayList<String>();
		
		List<String> curveStatTypeList=new ArrayList<String>();
		if("1".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.sort,t.showlevel,t.sumsign,t.averagesign,t.reportCurveconf,t.curvestattype,t.prec "
					+ " from tbl_report_items2unit_conf t "
					+ " where t.unitid="+unitId+" and t.reportType="+reportType
					+ " order by t.sort";
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsCodeList.add(obj[1]+"");
				itemsSortList.add(obj[2]+"");
				itemsShowLevelList.add(obj[3]+"");
				
				sumSignList.add(obj[4]+"");
				averageSignList.add(obj[5]+"");
				
				String reportCurveConf=obj[6]+"";
				if(!StringManagerUtils.isNotNull(reportCurveConf)){
					reportCurveConf="\"\"";
				}
				reportCurveConfList.add(reportCurveConf);
				curveStatTypeList.add(obj[7]+"");
				itemsPrecList.add(obj[8]+"");
			}
		}
		
		int index=1;
		if(rpcCalItemSet!=null){
			for(byte[] rpcCalItemByteArr:rpcCalItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
				
				boolean checked=false;
				String sort="";
				String showLevel="";
				String prec="";
				
				boolean sumSign=false;
				boolean averageSign=false;
				
				String reportCurveConf="\"\"";
				String reportCurveConfShowValue="";
				
				String curveStatType="";

				checked=StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(),false);
				if(checked){
					for(int k=0;k<itemsList.size();k++){
						if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
							sort=itemsSortList.get(k);
							showLevel=itemsShowLevelList.get(k);
							prec=itemsPrecList.get(k);
							
							if(StringManagerUtils.isNum(sumSignList.get(k))||StringManagerUtils.isNumber(sumSignList.get(k))){
								if(StringManagerUtils.stringToInteger(sumSignList.get(k))==1){
									sumSign=true;
								}else{
									sumSign=false;
								}
							}
							
							if(StringManagerUtils.isNum(averageSignList.get(k))||StringManagerUtils.isNumber(averageSignList.get(k))){
								if(StringManagerUtils.stringToInteger(averageSignList.get(k))==1){
									averageSign=true;
								}else{
									averageSign=false;
								}
							}
							
							reportCurveConf=reportCurveConfList.get(k);
							
							CurveConf reportCurveConfObj=null;
							if(StringManagerUtils.isNotNull(reportCurveConf) && !"\"\"".equals(reportCurveConf)){
								type = new TypeToken<CurveConf>() {}.getType();
								reportCurveConfObj=gson.fromJson(reportCurveConf, type);
							}
							
							if(reportCurveConfObj!=null){
								reportCurveConfShowValue=reportCurveConfObj.getSort()+";"+reportCurveConfObj.getColor();
							}
							
							
							String curveStatTypeStr=curveStatTypeList.get(k).replaceAll("null", "");
							if(StringManagerUtils.isNum(curveStatTypeStr) || StringManagerUtils.isNumber(curveStatTypeStr)){
								if(StringManagerUtils.stringToInteger(curveStatTypeStr)==1){
									curveStatType="合计";
								}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==2){
									curveStatType="平均";
								}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==3){
									curveStatType="最大值";
								}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==4){
									curveStatType="最小值";
								}
							}
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
						+ "\"prec\":\""+prec+"\","
						+ "\"sumSign\":"+sumSign+","
						+ "\"averageSign\":"+averageSign+","
						+ "\"reportCurveConfShowValue\":\""+reportCurveConfShowValue+"\","
						+ "\"reportCurveConf\":"+reportCurveConf+","
						+ "\"curveStatType\":\""+curveStatType+"\","
						+ "\"dataType\":"+calItem.getDataType()+","
						+ "\"code\":\""+calItem.getCode()+"\","
						+ "\"remark\":\""+calItem.getRemark()+"\""
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
	
	public String getReportInstanceTotalCalItemsConfigData(String deviceType,String unitId,String reportType){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String key="rpcTotalCalItemList";
		if("1".equalsIgnoreCase(deviceType)){
			key="pcpTotalCalItemList";
		}
		Jedis jedis=null;
		List<byte[]> rpcCalItemSet=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists(key.getBytes())){
				if("1".equalsIgnoreCase(deviceType)){
					MemoryDataManagerTask.loadPCPTotalCalculateItem();
				}else{
					MemoryDataManagerTask.loadRPCTotalCalculateItem();
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
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"小数位数\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"求和\",\"dataIndex\":\"sumSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"求平均\",\"dataIndex\":\"averageSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"报表曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"报曲线统计类型\",\"dataIndex\":\"curveStatType\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		String sql="select t.itemname,t.itemcode,t.sort,t.showlevel,t.sumsign,t.averagesign,t.reportCurveConf,t.curvestattype,t.prec "
				+ " from tbl_report_items2unit_conf t "
				+ " where t.unitid="+unitId+" and t.reportType="+reportType
				+ " order by t.sort";
		
		List<?> list=this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[])list.get(i);
			String unit="";
			String dataType="\"\"";
			if(rpcCalItemSet!=null){
				CalItem calItem=null;
				for(byte[] rpcCalItemByteArr:rpcCalItemSet){
					calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
					if( (obj[1]+"").equalsIgnoreCase(calItem.getCode()) ){
						unit=calItem.getUnit();
						dataType=calItem.getDataType()+"";
						break;
					}
				}
			}
			boolean sumSign=false;
			boolean averageSign=false;
			String sumSignStr=obj[4]+"";
			String averageSignStr=obj[5]+"";
			
			String reportCurveConf=obj[6]+"";
			if(!StringManagerUtils.isNotNull(reportCurveConf)){
				reportCurveConf="\"\"";
			}
			String reportCurveConfShowValue="";
			
			CurveConf reportCurveConfObj=null;
			if(StringManagerUtils.isNotNull(reportCurveConf) && !"\"\"".equals(reportCurveConf)){
				type = new TypeToken<CurveConf>() {}.getType();
				reportCurveConfObj=gson.fromJson(reportCurveConf, type);
			}
			
			if(reportCurveConfObj!=null){
				reportCurveConfShowValue=reportCurveConfObj.getSort()+";"+reportCurveConfObj.getColor();
			}
			
			String curveStatType="";
			String curveStatTypeStr=(obj[7]+"").replaceAll("null", "");
			
			if(StringManagerUtils.isNum(sumSignStr)||StringManagerUtils.isNumber(sumSignStr)){
				if(StringManagerUtils.stringToInteger(sumSignStr)==1){
					sumSign=true;
				}else{
					sumSign=false;
				}
			}
			
			if(StringManagerUtils.isNum(averageSignStr)||StringManagerUtils.isNumber(averageSignStr)){
				if(StringManagerUtils.stringToInteger(averageSignStr)==1){
					averageSign=true;
				}else{
					averageSign=false;
				}
			}
			
			if(StringManagerUtils.isNum(curveStatTypeStr) || StringManagerUtils.isNumber(curveStatTypeStr)){
				if(StringManagerUtils.stringToInteger(curveStatTypeStr)==1){
					curveStatType="合计";
				}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==2){
					curveStatType="平均";
				}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==3){
					curveStatType="最大值";
				}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==4){
					curveStatType="最小值";
				}
			}
			
			
			result_json.append("{\"id\":"+(i+1)+","
					+ "\"title\":\""+obj[0]+""+"\","
					+ "\"unit\":\""+unit+"\","
					+ "\"showLevel\":\""+obj[3]+""+"\","
					+ "\"sort\":\""+obj[2]+""+"\","
					+ "\"prec\":\""+obj[8]+""+"\","
					+ "\"sumSign\":"+sumSign+""+","
					+ "\"averageSign\":"+averageSign+""+","
					+ "\"reportCurveConfShowValue\":\""+reportCurveConfShowValue+""+"\","
					+ "\"reportCurveConf\":"+reportCurveConf+""+","
					+ "\"curveStatType\":\""+curveStatType+""+"\","
					+ "\"dataType\":"+dataType+","
					+ "\"code\":\""+obj[1]+""+"\""
					+ "},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getReportInstanceTimingTotalCalItemsConfigData(String deviceType,String unitId,String reportType){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String key="rpcTimingTotalCalItemList";
		if("1".equalsIgnoreCase(deviceType)){
			key="pcpTimingTotalCalItemList";
		}
		Jedis jedis=null;
		List<byte[]> rpcCalItemSet=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists(key.getBytes())){
				if("1".equalsIgnoreCase(deviceType)){
					MemoryDataManagerTask.loadPCPTimingTotalCalculateItem();
				}else{
					MemoryDataManagerTask.loadRPCTimingTotalCalculateItem();
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
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"小数位数\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"求和\",\"dataIndex\":\"sumSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"求平均\",\"dataIndex\":\"averageSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"报表曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"报曲线统计类型\",\"dataIndex\":\"curveStatType\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		String sql="select t.itemname,t.itemcode,t.sort,t.showlevel,t.sumsign,t.averagesign,t.reportCurveConf,t.curvestattype,t.prec "
				+ " from tbl_report_items2unit_conf t "
				+ " where t.unitid="+unitId+" and t.reportType="+reportType
				+ " order by t.sort";
		
		List<?> list=this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[])list.get(i);
			String unit="";
			String dataType="\"\"";
			if(rpcCalItemSet!=null){
				CalItem calItem=null;
				for(byte[] rpcCalItemByteArr:rpcCalItemSet){
					calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
					if( (obj[1]+"").equalsIgnoreCase(calItem.getCode()) ){
						unit=calItem.getUnit();
						dataType=calItem.getDataType()+"";
						break;
					}
				}
			}
			boolean sumSign=false;
			boolean averageSign=false;
			String sumSignStr=obj[4]+"";
			String averageSignStr=obj[5]+"";
			
			String reportCurveConf=obj[6]+"";
			if(!StringManagerUtils.isNotNull(reportCurveConf)){
				reportCurveConf="\"\"";
			}
			String reportCurveConfShowValue="";
			
			CurveConf reportCurveConfObj=null;
			if(StringManagerUtils.isNotNull(reportCurveConf) && !"\"\"".equals(reportCurveConf)){
				type = new TypeToken<CurveConf>() {}.getType();
				reportCurveConfObj=gson.fromJson(reportCurveConf, type);
			}
			
			if(reportCurveConfObj!=null){
				reportCurveConfShowValue=reportCurveConfObj.getSort()+";"+reportCurveConfObj.getColor();
			}
			
			String curveStatType="";
			String curveStatTypeStr=(obj[7]+"").replaceAll("null", "");
			
			if(StringManagerUtils.isNum(sumSignStr)||StringManagerUtils.isNumber(sumSignStr)){
				if(StringManagerUtils.stringToInteger(sumSignStr)==1){
					sumSign=true;
				}else{
					sumSign=false;
				}
			}
			
			if(StringManagerUtils.isNum(averageSignStr)||StringManagerUtils.isNumber(averageSignStr)){
				if(StringManagerUtils.stringToInteger(averageSignStr)==1){
					averageSign=true;
				}else{
					averageSign=false;
				}
			}
			
			if(StringManagerUtils.isNum(curveStatTypeStr) || StringManagerUtils.isNumber(curveStatTypeStr)){
				if(StringManagerUtils.stringToInteger(curveStatTypeStr)==1){
					curveStatType="合计";
				}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==2){
					curveStatType="平均";
				}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==3){
					curveStatType="最大值";
				}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==4){
					curveStatType="最小值";
				}
			}
			
			
			result_json.append("{\"id\":"+(i+1)+","
					+ "\"title\":\""+obj[0]+""+"\","
					+ "\"unit\":\""+unit+"\","
					+ "\"showLevel\":\""+obj[3]+""+"\","
					+ "\"sort\":\""+obj[2]+""+"\","
					+ "\"prec\":\""+obj[8]+""+"\","
					+ "\"sumSign\":"+sumSign+""+","
					+ "\"averageSign\":"+averageSign+""+","
					+ "\"reportCurveConfShowValue\":\""+reportCurveConfShowValue+""+"\","
					+ "\"reportCurveConf\":"+reportCurveConf+""+","
					+ "\"curveStatType\":\""+curveStatType+""+"\","
					+ "\"dataType\":"+dataType+","
					+ "\"code\":\""+obj[1]+""+"\""
					+ "},");
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
				+ "{ \"header\":\"响应模式\",\"dataIndex\":\"acqMode\",width:80 ,children:[] }"
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
								
								String resolutionMode="数据量";
								if(protocolConfig.getItems().get(j).getResolutionMode()==0){
									resolutionMode="开关量";
								}else if(protocolConfig.getItems().get(j).getResolutionMode()==1){
									resolutionMode="枚举量";
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
										+ "\"resolutionMode\":\""+resolutionMode+"\","
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
		java.lang.reflect.Type type=null;
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示级别\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveColor\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveConfList=new ArrayList<String>();
		List<String> historyCurveConfList=new ArrayList<String>();
		
		String protocolSql="select t.protocol from tbl_display_unit_conf t,tbl_protocoldisplayinstance t2 where t.id=t2.displayunitid and t2.id="+id+"";
		String sql="select t.itemname,t.bitindex,t.sort,t.showlevel,"
				+ " t.realtimeCurveConf,historyCurveConf "
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
				
				String realtimeCurveConfShowValue="";
				String historyCurveConfShowValue="";
				
				CurveConf realtimeCurveConfObj=null;
				if(StringManagerUtils.isNotNull(obj[4]+"") && !"\"\"".equals(obj[4]+"")){
					type = new TypeToken<CurveConf>() {}.getType();
					realtimeCurveConfObj=gson.fromJson(obj[4]+"", type);
				}
				
				CurveConf historyCurveConfObj=null;
				if(StringManagerUtils.isNotNull(obj[5]+"") && !"\"\"".equals(obj[5]+"")){
					type = new TypeToken<CurveConf>() {}.getType();
					historyCurveConfObj=gson.fromJson(obj[5]+"", type);
				}
				
				if(realtimeCurveConfObj!=null){
					realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+realtimeCurveConfObj.getColor();
				}
				if(historyCurveConfObj!=null){
					historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+historyCurveConfObj.getColor();
				}
				
				realtimeCurveConfList.add(realtimeCurveConfShowValue);
				historyCurveConfList.add(historyCurveConfShowValue);
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
								
								String realtimeCurveConfShowValue="";
								String historyCurveConfShowValue="";
								
								if(protocolConfig.getItems().get(j).getResolutionMode()==0
										&&protocolConfig.getItems().get(j).getMeaning()!=null
										&&protocolConfig.getItems().get(j).getMeaning().size()>0){
									Collections.sort(protocolConfig.getItems().get(j).getMeaning());//排序
									for(int k=0;k<protocolConfig.getItems().get(j).getMeaning().size();k++){
										sort="";
										showLevel="";
										realtimeCurveConfShowValue="";
										historyCurveConfShowValue="";
										for(int m=0;m<itemsList.size();m++){
											if(itemsList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())
													&&itemsBitIndexList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"")
												){
												sort=itemsSortList.get(m);
												showLevel=itemsShowLevelList.get(m);
												realtimeCurveConfShowValue=realtimeCurveConfList.get(m);
												historyCurveConfShowValue=historyCurveConfList.get(m);
												break;
											}
										}
										
										result_json.append("{"
												+ "\"id\":"+(index)+","
												+ "\"title\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
												+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
												+ "\"showLevel\":\""+showLevel+"\","
												+ "\"sort\":\""+sort+"\","
												+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
												+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\""
												+ "},");
										index++;
									}
								}else{
									for(int k=0;k<itemsList.size();k++){
										if(itemsList.get(k).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())){
											sort=itemsSortList.get(k);
											showLevel=itemsShowLevelList.get(k);
											realtimeCurveConfShowValue=realtimeCurveConfList.get(k);
											historyCurveConfShowValue=realtimeCurveConfList.get(k);
											break;
										}
									}
									result_json.append("{"
											+ "\"id\":"+(index)+","
											+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
											+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
											+ "\"showLevel\":\""+showLevel+"\","
											+ "\"sort\":\""+sort+"\","
											+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
											+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\""
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
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] }"
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
		java.lang.reflect.Type type=null;
		String key="rpcCalItemList";
		if("1".equalsIgnoreCase(deviceType)){
			key="pcpCalItemList";
		}
		Jedis jedis=null;
		List<byte[]> rpcCalItemSet=null;
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
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveColor\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsCodeList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveConfList=new ArrayList<String>();
		List<String> historyCurveConfList=new ArrayList<String>();
		
		String sql="select t.itemname,t.itemcode,t.bitindex,t.sort,t.showlevel,"
				+ " t.realtimeCurveConf,historyCurveConf "
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
				String realtimeCurveConfShowValue="";
				String historyCurveConfShowValue="";
				
				CurveConf realtimeCurveConfObj=null;
				if(StringManagerUtils.isNotNull(obj[5]+"") && !"\"\"".equals(obj[5]+"")){
					type = new TypeToken<CurveConf>() {}.getType();
					realtimeCurveConfObj=gson.fromJson(obj[5]+"", type);
				}
				
				CurveConf historyCurveConfObj=null;
				if(StringManagerUtils.isNotNull(obj[6]+"") && !"\"\"".equals(obj[6]+"")){
					type = new TypeToken<CurveConf>() {}.getType();
					historyCurveConfObj=gson.fromJson(obj[6]+"", type);
				}
				
				if(realtimeCurveConfObj!=null){
					realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+realtimeCurveConfObj.getColor();
				}
				if(historyCurveConfObj!=null){
					historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+historyCurveConfObj.getColor();
				}
				
				realtimeCurveConfList.add(realtimeCurveConfShowValue);
				historyCurveConfList.add(historyCurveConfShowValue);
			}
			int index=1;
			for(byte[] rpcCalItemByteArr:rpcCalItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
				if(StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(), false)){
					String sort="";
					String showLevel="";
					String realtimeCurveConfShowValue="";
					String historyCurveConfShowValue="";

					for(int k=0;k<itemsList.size();k++){
						if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
							sort=itemsSortList.get(k);
							showLevel=itemsShowLevelList.get(k);
							realtimeCurveConfShowValue=realtimeCurveConfList.get(k);
							historyCurveConfShowValue=historyCurveConfList.get(k);
							break;
						}
					}
					result_json.append("{"
							+ "\"id\":"+index+","
							+ "\"title\":\""+calItem.getName()+"\","
							+ "\"unit\":\""+calItem.getUnit()+"\","
							+ "\"showLevel\":\""+showLevel+"\","
							+ "\"sort\":\""+sort+"\","
							+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
							+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\""
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
	
	public String getProtocolDisplayInstanceInputItemsConfigData(String id,String classes,String deviceType){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String key="rpcInputItemList";
		if("1".equalsIgnoreCase(deviceType)){
			key="pcpInputItemList";
		}
		Jedis jedis=null;
		List<byte[]> inputItemSet=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists(key.getBytes())){
				if("1".equalsIgnoreCase(deviceType)){
					MemoryDataManagerTask.loadPCPInputItem();
				}else{
					MemoryDataManagerTask.loadRPCInputItem();
				}
			}
			
			inputItemSet= jedis.zrange(key.getBytes(), 0, -1);
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
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveColor\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsCodeList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveConfList=new ArrayList<String>();
		List<String> historyCurveConfList=new ArrayList<String>();
		
		String sql="select t.itemname,t.itemcode,t.bitindex,t.sort,t.showlevel,"
				+ " t.realtimeCurveConf,historyCurveConf "
				+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2,tbl_protocoldisplayinstance t3 "
				+ " where t.unitid=t2.id and t2.id=t3.displayunitid and t.type=3 "
				+ " and t3.id= "+id
				+ " order by t.id";
		if(inputItemSet!=null){
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsCodeList.add(obj[1]+"");
				itemsBitIndexList.add(obj[2]+"");
				itemsSortList.add(obj[3]+"");
				itemsShowLevelList.add(obj[4]+"");
				String realtimeCurveConfShowValue="";
				String historyCurveConfShowValue="";
				
				CurveConf realtimeCurveConfObj=null;
				if(StringManagerUtils.isNotNull(obj[5]+"") && !"\"\"".equals(obj[5]+"")){
					type = new TypeToken<CurveConf>() {}.getType();
					realtimeCurveConfObj=gson.fromJson(obj[5]+"", type);
				}
				
				CurveConf historyCurveConfObj=null;
				if(StringManagerUtils.isNotNull(obj[6]+"") && !"\"\"".equals(obj[6]+"")){
					type = new TypeToken<CurveConf>() {}.getType();
					historyCurveConfObj=gson.fromJson(obj[6]+"", type);
				}
				
				if(realtimeCurveConfObj!=null){
					realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+realtimeCurveConfObj.getColor();
				}
				if(historyCurveConfObj!=null){
					historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+historyCurveConfObj.getColor();
				}
				
				realtimeCurveConfList.add(realtimeCurveConfShowValue);
				historyCurveConfList.add(historyCurveConfShowValue);
			}
			int index=1;
			for(byte[] inputItemByteArr:inputItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(inputItemByteArr);
				if(StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(), false)){
					String sort="";
					String showLevel="";
					String realtimeCurveConfShowValue="";
					String historyCurveConfShowValue="";

					for(int k=0;k<itemsList.size();k++){
						if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
							sort=itemsSortList.get(k);
							showLevel=itemsShowLevelList.get(k);
							realtimeCurveConfShowValue=realtimeCurveConfList.get(k);
							historyCurveConfShowValue=historyCurveConfList.get(k);
							break;
						}
					}
					result_json.append("{"
							+ "\"id\":"+index+","
							+ "\"title\":\""+calItem.getName()+"\","
							+ "\"unit\":\""+calItem.getUnit()+"\","
							+ "\"showLevel\":\""+showLevel+"\","
							+ "\"sort\":\""+sort+"\","
							+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
							+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\""
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
		List<byte[]> calItemSet=null;
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
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"数值\",\"dataIndex\":\"value\",width:80 ,children:[] },"
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
		StringBuffer tree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		tree_json.append("[");
		
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
				tree_json.append("{\"classes\":1,");
				tree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
				tree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
				tree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
				tree_json.append("\"iconCls\": \"protocol\",");
				tree_json.append("\"expanded\": true,");
				tree_json.append("\"children\": [");
				for(int j=0;j<unitList.size();j++){
					Object[] unitObj = (Object[]) unitList.get(j);
					if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[unitObj.length-1]+"")){
						tree_json.append("{\"classes\":2,");
						tree_json.append("\"id\":"+unitObj[0]+",");
						tree_json.append("\"code\":\""+unitObj[1]+"\",");
						tree_json.append("\"text\":\""+unitObj[2]+"\",");
						tree_json.append("\"remark\":\""+unitObj[3]+"\",");
						tree_json.append("\"protocol\":\""+unitObj[4]+"\",");
						tree_json.append("\"iconCls\": \"acqUnit\",");
						tree_json.append("\"expanded\": true,");
						tree_json.append("\"children\": [");
						for(int k=0;k<groupList.size();k++){
							Object[] groupObj = (Object[]) groupList.get(k);
							if((unitObj[0]+"").equalsIgnoreCase(groupObj[groupObj.length-1]+"")){
								tree_json.append("{\"classes\":3,");
								tree_json.append("\"id\":"+groupObj[0]+",");
								tree_json.append("\"code\":\""+groupObj[1]+"\",");
								tree_json.append("\"text\":\""+groupObj[2]+"\",");
								tree_json.append("\"groupTimingInterval\":\""+groupObj[3]+"\",");
								tree_json.append("\"groupSavingInterval\":\""+groupObj[4]+"\",");
								tree_json.append("\"remark\":\""+groupObj[5]+"\",");
								tree_json.append("\"protocol\":\""+groupObj[6]+"\",");
								tree_json.append("\"type\":"+groupObj[7]+",");
								tree_json.append("\"typeName\":\""+("0".equalsIgnoreCase(groupObj[7]+"")?"采集组":"控制组")+"\",");
								tree_json.append("\"unitId\":"+groupObj[groupObj.length-1]+",");
								tree_json.append("\"iconCls\": \"acqGroup\",");
								tree_json.append("\"leaf\": true");
								tree_json.append("},");
							}
						}
						if(tree_json.toString().endsWith(",")){
							tree_json.deleteCharAt(tree_json.length() - 1);
						}
						
						tree_json.append("]},");
					}
				}
				if(tree_json.toString().endsWith(",")){
					tree_json.deleteCharAt(tree_json.length() - 1);
				}
				tree_json.append("]},");
			}
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"单元列表\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportAcqUnitTreeData(String deviceType,String protocolName,String protocolCode){
		StringBuffer result_json = new StringBuffer();
		String unitSql="select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark,t.protocol "
				+ "from tbl_acq_unit_conf t "
				+ " where t.protocol='"+protocolName+"'"
				+ " order by t.protocol,t.id";
		String groupSql="select t3.id,t3.group_code,t3.group_name,t3.grouptiminginterval,t3.groupsavinginterval,t3.remark,t3.protocol,t3.type,t2.id as unitId "
				+ " from TBL_ACQ_GROUP2UNIT_CONF t,tbl_acq_unit_conf t2,tbl_acq_group_conf t3 "
				+ " where t.unitid=t2.id and t.groupid=t3.id "
				+ " and t2.protocol='"+protocolName+"'"
				+ " order by t3.protocol,t2.unit_code,t3.type,t3.id";
		List<?> unitList=this.findCallSql(unitSql);
		List<?> groupList=this.findCallSql(groupSql);
		
		result_json.append("[");
		for(int j=0;j<unitList.size();j++){
			Object[] unitObj = (Object[]) unitList.get(j);

			result_json.append("{\"classes\":2,");
			result_json.append("\"id\":"+unitObj[0]+",");
			result_json.append("\"code\":\""+unitObj[1]+"\",");
			result_json.append("\"text\":\""+unitObj[2]+"\",");
			result_json.append("\"remark\":\""+unitObj[3]+"\",");
			result_json.append("\"protocol\":\""+unitObj[4]+"\",");
			result_json.append("\"iconCls\": \"acqUnit\",");
			result_json.append("\"expanded\": true,");
			result_json.append("\"children\": [");
			for(int k=0;k<groupList.size();k++){
				Object[] groupObj = (Object[]) groupList.get(k);
				if((unitObj[0]+"").equalsIgnoreCase(groupObj[groupObj.length-1]+"")){
					result_json.append("{\"classes\":3,");
					result_json.append("\"id\":"+groupObj[0]+",");
					result_json.append("\"code\":\""+groupObj[1]+"\",");
					result_json.append("\"text\":\""+groupObj[2]+"\",");
					result_json.append("\"groupTimingInterval\":\""+groupObj[3]+"\",");
					result_json.append("\"groupSavingInterval\":\""+groupObj[4]+"\",");
					result_json.append("\"remark\":\""+groupObj[5]+"\",");
					result_json.append("\"protocol\":\""+groupObj[6]+"\",");
					result_json.append("\"type\":"+groupObj[7]+",");
					result_json.append("\"typeName\":\""+("0".equalsIgnoreCase(groupObj[7]+"")?"采集组":"控制组")+"\",");
					result_json.append("\"unitId\":"+groupObj[groupObj.length-1]+",");
					result_json.append("\"iconCls\": \"acqGroup\",");
					result_json.append("\"leaf\": true");
					result_json.append("},");
				}
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]},");
		}
		
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getDisplayUnitTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		tree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			String unitSql="select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark,t.protocol,t.acqunitid,t2.unit_name as acqunitname "
					+ " from tbl_display_unit_conf t,tbl_acq_unit_conf t2 "
					+ " where t.acqunitid=t2.id "
					+ " order by t.protocol,t.id";
			List<?> unitList=this.findCallSql(unitSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){

				tree_json.append("{\"classes\":1,");
				tree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
				tree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
				tree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
				tree_json.append("\"iconCls\": \"protocol\",");
				tree_json.append("\"expanded\": true,");
				tree_json.append("\"children\": [");
				for(int j=0;j<unitList.size();j++){
					Object[] unitObj = (Object[]) unitList.get(j);
					if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[4]+"")){
						tree_json.append("{\"classes\":2,");
						tree_json.append("\"id\":"+unitObj[0]+",");
						tree_json.append("\"code\":\""+unitObj[1]+"\",");
						tree_json.append("\"text\":\""+unitObj[2]+"\",");
						tree_json.append("\"remark\":\""+unitObj[3]+"\",");
						tree_json.append("\"protocol\":\""+unitObj[4]+"\",");
						tree_json.append("\"acqUnitId\":\""+unitObj[5]+"\",");
						tree_json.append("\"acqUnitName\":\""+unitObj[6]+"\",");
						tree_json.append("\"iconCls\": \"acqUnit\",");
						tree_json.append("\"leaf\": true");
						tree_json.append("},");
					}
				}
				if(tree_json.toString().endsWith(",")){
					tree_json.deleteCharAt(tree_json.length() - 1);
				}
				tree_json.append("]},");
			}
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"抽油机井\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportDisplayUnitTreeData(String deviceType,String protocolName,String protocolCode){
		StringBuffer result_json = new StringBuffer();
		result_json.append("[");
		String unitSql="select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark,t.protocol,t.acqunitid,t2.unit_name as acqunitname "
				+ " from tbl_display_unit_conf t,tbl_acq_unit_conf t2 "
				+ " where t.acqunitid=t2.id  and t2.protocol='"+protocolName+"'"
				+ " order by t.protocol,t.id";
		List<?> unitList=this.findCallSql(unitSql);
		
		for(int j=0;j<unitList.size();j++){
			Object[] unitObj = (Object[]) unitList.get(j);
			result_json.append("{\"classes\":2,");
			result_json.append("\"id\":"+unitObj[0]+",");
			result_json.append("\"code\":\""+unitObj[1]+"\",");
			result_json.append("\"text\":\""+unitObj[2]+"\",");
			result_json.append("\"remark\":\""+unitObj[3]+"\",");
			result_json.append("\"protocol\":\""+unitObj[4]+"\",");
			result_json.append("\"acqUnitId\":\""+unitObj[5]+"\",");
			result_json.append("\"acqUnitName\":\""+unitObj[6]+"\",");
			result_json.append("\"iconCls\": \"acqUnit\",");
			result_json.append("\"leaf\": true");
			result_json.append("},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String modbusProtocolAddrMappingTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		tree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){

				tree_json.append("{\"classes\":1,");
				tree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
				tree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
				tree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
				tree_json.append("\"iconCls\": \"protocol\",");
				tree_json.append("\"leaf\": true");
				tree_json.append("},");
			
			}
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		result_json.append("{\"classes\":0,\"text\":\"协议列表\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
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
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]");
		return result_json.toString();
	}
	
	public String repoerUnitTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
//		ReportTemplate reportTemplate=MemoryDataManagerTask.getReportTemplateConfig();
		
		tree_json.append("[");
		String unitSql="select t.id,t.unit_code,t.unit_name,"
				+ "t.singleWellRangeReportTemplate,t.singleWellDailyReportTemplate,t.productionreporttemplate,"
				+ "t.sort "
				+ " from tbl_report_unit_conf t "
				+ " order by t.sort";
		List<?> unitList=this.findCallSql(unitSql);
		for(int i=0;i<unitList.size();i++){
			Object[] obj = (Object[]) unitList.get(i);

			tree_json.append("{\"classes\":1,");
			tree_json.append("\"id\":"+obj[0]+",");
			tree_json.append("\"code\":\""+obj[1]+"\",");
			tree_json.append("\"text\":\""+obj[2]+"\",");
			tree_json.append("\"singleWellRangeReportTemplate\":\""+obj[3]+"\",");
			tree_json.append("\"singleWellDailyReportTemplate\":\""+obj[4]+"\",");
			tree_json.append("\"productionReportTemplate\":\""+obj[5]+"\",");
			tree_json.append("\"sort\":\""+obj[6]+"\",");
			tree_json.append("\"iconCls\": \"protocol\",");
			tree_json.append("\"leaf\": true");
			tree_json.append("},");
		
		}
		
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"单元列表\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getReportDataTemplateList(String reportType,String deviceType){
		StringBuffer result_json = new StringBuffer();
		ReportTemplate reportTemplate=MemoryDataManagerTask.getReportTemplateConfig();
		result_json.append("{\"success\":true,\"totalRoot\":[");
		int totalCount=0;
		if(reportTemplate!=null){
			List<Template> templateList=null;
			if(StringManagerUtils.stringToInteger(reportType)==0){
				templateList=reportTemplate.getSingleWellRangeReportTemplate();
			}else if(StringManagerUtils.stringToInteger(reportType)==2){
				templateList=reportTemplate.getSingleWellDailyReportTemplate();
			}else{
				templateList=reportTemplate.getProductionReportTemplate();
			}
			if(templateList!=null){
				//排序
				Collections.sort(templateList);
				for(int i=0;i<templateList.size();i++){
					totalCount++;
					result_json.append("{\"templateName\":\""+templateList.get(i).getTemplateName()+"\",");
					result_json.append("\"templateCode\":\""+templateList.get(i).getTemplateCode()+"\"},");
				}
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("],\"totalCount\":"+totalCount+"}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getReportTemplateData(String reportType,String deviceType,String code){
		StringBuffer result_json = new StringBuffer();
		ReportTemplate reportTemplate=MemoryDataManagerTask.getReportTemplateConfig();
		String result="{}";
		
		if(reportTemplate!=null){
			List<Template> templateList=null;
			if(StringManagerUtils.stringToInteger(reportType)==0){
				templateList=reportTemplate.getSingleWellRangeReportTemplate();
			}else if(StringManagerUtils.stringToInteger(reportType)==2){
				templateList=reportTemplate.getSingleWellDailyReportTemplate();
			}else{
				templateList=reportTemplate.getProductionReportTemplate();
			}
			if(templateList!=null && templateList.size()>0){
				for(int i=0;i<templateList.size();i++){
					if(code.equalsIgnoreCase(templateList.get(i).getTemplateCode()) 
							){
						Gson gson=new Gson();
						result=gson.toJson(templateList.get(i)).replaceAll("wellNameLabel", "label").replaceAll("label", "***");
						break;
					}
				}
			}
		}
		
		return result;
	}
	
	public String modbusProtocolAlarmUnitTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		tree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			String unitSql="select t.id,t.unit_code,t.unit_name,t.remark,t.protocol"
					+ " from tbl_alarm_unit_conf t "
					+ " where 1=1 "
					+ " order by t.protocol,t.unit_code";
			List<?> unitList=this.findCallSql(unitSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){

				tree_json.append("{\"classes\":1,");
				tree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
				tree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
				tree_json.append("\"deviceType\":"+0+",");
				tree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
				tree_json.append("\"iconCls\": \"protocol\",");
				tree_json.append("\"expanded\": true,");
				tree_json.append("\"children\": [");
				for(int j=0;j<unitList.size();j++){
					Object[] unitObj = (Object[]) unitList.get(j);
					if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[unitObj.length-1]+"")){
						tree_json.append("{\"classes\":3,");
						tree_json.append("\"id\":"+unitObj[0]+",");
						tree_json.append("\"deviceType\":"+0+",");
						tree_json.append("\"code\":\""+unitObj[1]+"\",");
						tree_json.append("\"text\":\""+unitObj[2]+"\",");
						tree_json.append("\"remark\":\""+unitObj[3]+"\",");
						tree_json.append("\"protocol\":\""+unitObj[4]+"\",");
						tree_json.append("\"iconCls\": \"acqGroup\",");
						tree_json.append("\"leaf\": true");
						tree_json.append("},");
					}
				}
				if(tree_json.toString().endsWith(",")){
					tree_json.deleteCharAt(tree_json.length() - 1);
				}
				tree_json.append("]},");
			
			}
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"deviceType\": 0,\"text\":\"单元列表\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportProtocolAlarmUnitTreeData(String deviceType,String protocolName,String protocolCode){
		StringBuffer result_json = new StringBuffer();
		result_json.append("[");
		String unitSql="select t.id,t.unit_code,t.unit_name,t.remark,t.protocol"
				+ " from tbl_alarm_unit_conf t "
				+ " where t.protocol='"+protocolName+"' "
				+ " order by t.protocol,t.unit_code";
		List<?> unitList=this.findCallSql(unitSql);
		
		for(int j=0;j<unitList.size();j++){
			Object[] unitObj = (Object[]) unitList.get(j);
			result_json.append("{\"classes\":3,");
			result_json.append("\"id\":"+unitObj[0]+",");
			result_json.append("\"deviceType\":"+0+",");
			result_json.append("\"code\":\""+unitObj[1]+"\",");
			result_json.append("\"text\":\""+unitObj[2]+"\",");
			result_json.append("\"remark\":\""+unitObj[3]+"\",");
			result_json.append("\"protocol\":\""+unitObj[4]+"\",");
			result_json.append("\"iconCls\": \"acqGroup\",");
			result_json.append("\"leaf\": true");
			result_json.append("},");
		}
	
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
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

						rpcUnit_json.append("\""+obj[1]+"\",");
					
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
	
	public String getReportUnitList(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer unit_json = new StringBuffer();

		unit_json.append("[");
		String acqUnitSql="select t.unit_code,t.unit_name from tbl_report_unit_conf t order by  t.sort";
		List<?> unitList=this.findCallSql(acqUnitSql);
		for(int i=0;i<unitList.size();i++){
			Object[] obj = (Object[]) unitList.get(i);
			unit_json.append("\""+obj[1]+"\",");
		}
		if(unit_json.toString().endsWith(",")){
			unit_json.deleteCharAt(unit_json.length() - 1);
		}
		unit_json.append("]");
		result_json.append("{\"unitList\":"+unit_json+"}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusProtocolInstanceConfigTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		tree_json.append("[");
		String sql="select t.id,t.name,t.code,t.acqprotocoltype,t.ctrlprotocoltype,"//0~4
				+ " t.SignInPrefixSuffixHex,t.signinprefix,t.signinsuffix,t.SignInIDHex,"//5~8
				+ " t.HeartbeatPrefixSuffixHex,t.heartbeatprefix,t.heartbeatsuffix,"//9~11
				+ " t.packetsendinterval,"//12
				+ " t.sort,t.unitid,t2.unit_name "//13~15
				+ " from tbl_protocolinstance t ,tbl_acq_unit_conf t2 where t.unitid=t2.id"
				+ " order by t.sort";
		String groupSql="select t.id,t.group_name,t.group_code,t2.unitid "
				+ " from tbl_acq_group_conf t,tbl_acq_group2unit_conf t2 "
				+ " where t.id=t2.groupid "
				+ " order by t2.unitid,t.type";
		
		List<?> list=this.findCallSql(sql);
		List<?> groupList=this.findCallSql(groupSql);
		
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);

			tree_json.append("{\"classes\":1,");
			tree_json.append("\"id\":\""+obj[0]+"\",");
			tree_json.append("\"text\":\""+obj[1]+"\",");
			tree_json.append("\"code\":\""+obj[2]+"\",");
			tree_json.append("\"acqProtocolType\":\""+obj[3]+"\",");
			tree_json.append("\"ctrlProtocolType\":\""+obj[4]+"\",");
			
			tree_json.append("\"signInPrefixSuffixHex\":\""+obj[5]+"\",");
			tree_json.append("\"signInPrefix\":\""+obj[6]+"\",");
			tree_json.append("\"signInSuffix\":\""+obj[7]+"\",");
			tree_json.append("\"signInIDHex\":\""+obj[8]+"\",");
			
			tree_json.append("\"heartbeatPrefixSuffixHex\":\""+obj[9]+"\",");
			tree_json.append("\"heartbeatPrefix\":\""+obj[10]+"\",");
			tree_json.append("\"heartbeatSuffix\":\""+obj[11]+"\",");
			
			
			tree_json.append("\"packetSendInterval\":\""+obj[12]+"\",");
			
			tree_json.append("\"sort\":\""+obj[13]+"\",");
			tree_json.append("\"unitId\":"+obj[14]+",");
			tree_json.append("\"unitName\":\""+obj[15]+"\",");
			tree_json.append("\"iconCls\": \"protocol\",");

			tree_json.append("\"expanded\": true,");
			tree_json.append("\"children\": [");
			tree_json.append("{\"classes\":2,");
			tree_json.append("\"text\":\""+obj[15]+"\",");
			tree_json.append("\"id\":"+obj[14]+",");
			tree_json.append("\"iconCls\": \"acqUnit\","); 
			
			tree_json.append("\"expanded\": true,");
			tree_json.append("\"children\": [");
			for(int j=0;j<groupList.size();j++){
				Object[] groupObj = (Object[]) groupList.get(j);
				if((obj[15]+"").equalsIgnoreCase(groupObj[3]+"")){
					tree_json.append("{\"classes\":3,");
					tree_json.append("\"id\":"+groupObj[0]+",");
					tree_json.append("\"text\":\""+groupObj[1]+"\",");
					tree_json.append("\"code\":\""+groupObj[2]+"\",");
					tree_json.append("\"iconCls\": \"acqGroup\","); 
					tree_json.append("\"leaf\": true},");
				}
			}
			if(tree_json.toString().endsWith(",")){
				tree_json.deleteCharAt(tree_json.length() - 1);
			}
			
			tree_json.append("]");
			
			tree_json.append("}]");
			
			tree_json.append("},");
		
		}
		
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"实例列表\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportProtocolAcqInstanceTreeData(String deviceType,String protocolName,String protocolCode){
		StringBuffer result_json = new StringBuffer();
		result_json.append("[");
		String sql="select t.id,t.name,t.code,t.acqprotocoltype,t.ctrlprotocoltype,"//0~4
				+ " t.SignInPrefixSuffixHex,t.signinprefix,t.signinsuffix,t.SignInIDHex,"//5~8
				+ " t.HeartbeatPrefixSuffixHex,t.heartbeatprefix,t.heartbeatsuffix,"//9~11
				+ " t.packetsendinterval,"//12
				+ " t.sort,t.unitid,t2.unit_name "//13~15
				+ " from tbl_protocolinstance t ,tbl_acq_unit_conf t2 "
				+ " where t.unitid=t2.id and t2.protocol='"+protocolName+"'"
				+ " order by t.sort";
		
		List<?> list=this.findCallSql(sql);
		
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);

			result_json.append("{\"classes\":1,");
			result_json.append("\"id\":\""+obj[0]+"\",");
			result_json.append("\"text\":\""+obj[1]+"\",");
			result_json.append("\"code\":\""+obj[2]+"\",");
			result_json.append("\"acqProtocolType\":\""+obj[3]+"\",");
			result_json.append("\"ctrlProtocolType\":\""+obj[4]+"\",");
			
			result_json.append("\"signInPrefixSuffixHex\":\""+obj[5]+"\",");
			result_json.append("\"signInPrefix\":\""+obj[6]+"\",");
			result_json.append("\"signInSuffix\":\""+obj[7]+"\",");
			result_json.append("\"signInIDHex\":\""+obj[8]+"\",");
			
			result_json.append("\"heartbeatPrefixSuffixHex\":\""+obj[9]+"\",");
			result_json.append("\"heartbeatPrefix\":\""+obj[10]+"\",");
			result_json.append("\"heartbeatSuffix\":\""+obj[11]+"\",");
			
			result_json.append("\"packetSendInterval\":\""+obj[12]+"\",");
			result_json.append("\"sort\":\""+obj[13]+"\",");
			result_json.append("\"iconCls\": \"protocol\",");
			result_json.append("\"leaf\": true},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusDisplayProtocolInstanceConfigTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		tree_json.append("[");
		String sql="select t.id,t.name,t.code,t.displayUnitId,t2.unit_name,t.sort   "
				+ " from tbl_protocoldisplayinstance t ,tbl_display_unit_conf t2 where t.displayUnitId=t2.id "
				+ " order by t.sort";
		List<?> list=this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);

			tree_json.append("{\"classes\":1,");
			tree_json.append("\"id\":\""+obj[0]+"\",");
			tree_json.append("\"text\":\""+obj[1]+"\",");
			tree_json.append("\"code\":\""+obj[2]+"\",");
			tree_json.append("\"displayUnitId\":"+obj[3]+",");
			tree_json.append("\"displayUnitName\":\""+obj[4]+"\",");
			tree_json.append("\"sort\":\""+obj[5]+"\",");
			tree_json.append("\"iconCls\": \"protocol\",");
			tree_json.append("\"expanded\": true,");
			tree_json.append("\"children\": [");
			tree_json.append("{\"classes\":2,");
			tree_json.append("\"id\":"+obj[3]+",");
			tree_json.append("\"text\":\""+obj[4]+"\",");
			tree_json.append("\"iconCls\": \"acqUnit\","); 
			tree_json.append("\"leaf\": true}");
			tree_json.append("]");
			tree_json.append("},");
		
		}
		
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"实例列表\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportProtocolDisplayInstanceTreeData(String deviceType,String protocolName,String protocolCode){
		StringBuffer result_json = new StringBuffer();
		result_json.append("[");
		String sql="select t.id,t.name,t.code,t.displayUnitId,t2.unit_name,t.sort   "
				+ " from tbl_protocoldisplayinstance t ,tbl_display_unit_conf t2 "
				+ " where t.displayUnitId=t2.id "
				+ " and t2.protocol='"+protocolName+"'"
				+ " order by t.sort";
		List<?> list=this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"classes\":1,");
			result_json.append("\"id\":\""+obj[0]+"\",");
			result_json.append("\"text\":\""+obj[1]+"\",");
			result_json.append("\"code\":\""+obj[2]+"\",");
			result_json.append("\"displayUnitId\":"+obj[3]+",");
			result_json.append("\"displayUnitName\":\""+obj[4]+"\",");
			result_json.append("\"sort\":\""+obj[5]+"\",");
			result_json.append("\"iconCls\": \"protocol\",");
			result_json.append("\"leaf\": true},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String modbusReportInstanceConfigTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		tree_json.append("[");
		String sql="select t.id,t.name,t.code,t.unitid,t2.unit_name,t.sort,"
				+ " t2.singleWellRangeReportTemplate,t2.singleWellDailyReportTemplate,t2.productionreporttemplate "
				+ " from tbl_protocolreportinstance t,tbl_report_unit_conf t2 "
				+ " where t.unitid=t2.id "
				+ " order by t.sort,t.id";
		List<?> list=this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);

			tree_json.append("{\"classes\":1,");
			tree_json.append("\"id\":"+obj[0]+",");
			tree_json.append("\"text\":\""+obj[1]+"\",");
			tree_json.append("\"code\":\""+obj[2]+"\",");
			tree_json.append("\"unitId\":"+obj[3]+",");
			tree_json.append("\"unitName\":\""+obj[4]+"\",");
			tree_json.append("\"sort\":\""+obj[5]+"\",");
			tree_json.append("\"singleWellRangeReportTemplate\":\""+obj[6]+"\",");
			tree_json.append("\"singleWellDailyReportTemplate\":\""+obj[7]+"\",");
			tree_json.append("\"productionReportTemplate\":\""+obj[8]+"\",");
			tree_json.append("\"iconCls\": \"protocol\",");
			tree_json.append("\"expanded\": true,");
			tree_json.append("\"children\": [");
			tree_json.append("{\"classes\":2,");
			tree_json.append("\"unitId\":"+obj[3]+",");
			tree_json.append("\"text\":\""+obj[4]+"\",");
			tree_json.append("\"singleWellRangeReportTemplate\":\""+obj[6]+"\",");
			tree_json.append("\"singleWellDailyReportTemplate\":\""+obj[7]+"\",");
			tree_json.append("\"productionReportTemplate\":\""+obj[8]+"\",");
			tree_json.append("\"iconCls\": \"acqUnit\","); 
			tree_json.append("\"leaf\": true}");
			tree_json.append("]");
			tree_json.append("},");
		
		}
		
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\"实例列表\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"},");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusAlarmProtocolInstanceConfigTreeData(){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		tree_json.append("[");
		String sql="select t.id,t.name,t.code,t.alarmUnitId,t2.unit_name,t.sort   "
				+ " from tbl_protocolalarminstance t ,tbl_alarm_unit_conf t2 where t.alarmunitid=t2.id "
				+ " order by t.sort";
		List<?> list=this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);

			tree_json.append("{\"classes\":1,");
			tree_json.append("\"id\":\""+obj[0]+"\",");
			tree_json.append("\"text\":\""+obj[1]+"\",");
			tree_json.append("\"code\":\""+obj[2]+"\",");
			tree_json.append("\"alarmUnitId\":"+obj[3]+",");
			tree_json.append("\"alarmUnitName\":\""+obj[4]+"\",");
			tree_json.append("\"sort\":\""+obj[5]+"\",");
			tree_json.append("\"iconCls\": \"protocol\",");
			tree_json.append("\"expanded\": true,");
			tree_json.append("\"children\": [");
			tree_json.append("{\"classes\":2,");
			tree_json.append("\"id\":"+obj[3]+",");
			tree_json.append("\"text\":\""+obj[4]+"\",");
			tree_json.append("\"iconCls\": \"acqGroup\","); 
			tree_json.append("\"leaf\": true}");
			tree_json.append("]");
			tree_json.append("},");
		
		}
		
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"deviceType\":0,\"text\":\"实例列表\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"},");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportProtocolAlarmInstanceTreeData(String deviceType,String protocolName,String protocolCode){
		StringBuffer result_json = new StringBuffer();
		result_json.append("[");
		String sql="select t.id,t.name,t.code,t.alarmUnitId,t2.unit_name,t.sort   "
				+ " from tbl_protocolalarminstance t ,tbl_alarm_unit_conf t2 "
				+ " where t.alarmunitid=t2.id and t2.protocol='"+protocolName+"'"
				+ " order by t.sort";
		List<?> list=this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"classes\":1,");
			result_json.append("\"id\":\""+obj[0]+"\",");
			result_json.append("\"text\":\""+obj[1]+"\",");
			result_json.append("\"code\":\""+obj[2]+"\",");
			result_json.append("\"alarmUnitId\":"+obj[3]+",");
			result_json.append("\"alarmUnitName\":\""+obj[4]+"\",");
			result_json.append("\"sort\":\""+obj[5]+"\",");
			result_json.append("\"iconCls\": \"protocol\",");
			result_json.append("\"leaf\": true},");
		
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
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
	
	public String getReportTemplateCombList(String deviceType){
		StringBuffer result_json = new StringBuffer();
		ReportTemplate reportTemplate=MemoryDataManagerTask.getReportTemplateConfig();
		result_json.append("{\"totals\":"+( (reportTemplate!=null&&reportTemplate.getSingleWellRangeReportTemplate()!=null)?reportTemplate.getSingleWellRangeReportTemplate().size():0 )+",\"list\":[");
		if(reportTemplate!=null&&reportTemplate.getSingleWellRangeReportTemplate()!=null){
			//排序
			Collections.sort(reportTemplate.getSingleWellRangeReportTemplate());
			for(int i=0;i<reportTemplate.getSingleWellRangeReportTemplate().size();i++){
				result_json.append("{boxkey:\"" + reportTemplate.getSingleWellRangeReportTemplate().get(i).getTemplateCode() + "\",");
				result_json.append("boxval:\"" + reportTemplate.getSingleWellRangeReportTemplate().get(i).getTemplateName() + "\"},");
			}
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getReportUnitCombList(){
		StringBuffer result_json = new StringBuffer();
		String sql="select t.id,t.unit_name from tbl_report_unit_conf t order by t.sort";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"totals\":"+list.size()+",\"list\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{boxkey:\"" + obj[0] + "\",");
			result_json.append("boxval:\"" + obj[1] + "\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
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
	
	public String getDatabaseColumnMappingTable(String classes,String deviceType,String protocolCode) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer calColumnNameBuff = new StringBuffer();
		String sql="select t.id,t.name,t.mappingcolumn,t.calcolumn from tbl_datamapping t where 1=1";
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
		if(StringManagerUtils.stringToInteger(classes)==1){//如果选中的是协议
			List<String> protocolMappingColumnList=new ArrayList<String>();
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			if(modbusProtocolConfig!=null){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					if(protocolCode.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getCode())){
						for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
							String columnName="";
							if(loadProtocolMappingColumnByTitleMap.containsKey(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle())){
								columnName=loadProtocolMappingColumnByTitleMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle()).getMappingColumn();
							}
							protocolMappingColumnList.add(columnName);
						}
						if(protocolMappingColumnList.size()>0){
							sql+=" and t.mappingcolumn in ("+StringManagerUtils.joinStringArr2(protocolMappingColumnList, ",")+")";
						}else{
							sql+=" and 1=2";
						}
						break;
					}
				}
			}
		}
				
				
		sql+=" order by t.id";
		String columns="["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"itemName\" ,children:[] },"
				+ "{ \"header\":\"字段\",\"dataIndex\":\"itemColumn\",children:[] },"
				+ "{ \"header\":\"计算字段\",\"dataIndex\":\"calColumn\",children:[] }"
				+ "]";
		
		calColumnNameBuff.append("[''");
		CalculateColumnInfo calculateColumnInfo=MemoryDataManagerTask.getCalColumnsInfo();
		List<CalculateColumn> calculateColumnList=calculateColumnInfo.getRPCCalculateColumnList();
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			calculateColumnList=calculateColumnInfo.getPCPCalculateColumnList();
		}
		for(int i = 0; i < calculateColumnList.size(); i++){
			calColumnNameBuff.append(",'"+calculateColumnList.get(i).getName()+"'");
		}
		if(calColumnNameBuff.toString().endsWith(",")){
			calColumnNameBuff.deleteCharAt(calColumnNameBuff.length() - 1);
		}
		calColumnNameBuff.append("]");
		
		List<?> list=this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":" + list.size() + ",\"calColumnNameList\":"+calColumnNameBuff+",\"columns\":"+columns+",\"totalRoot\":[");
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String calColumn=obj[3]+"";
			String calColumnName=MemoryDataManagerTask.getCalculateColumnNameFromCode(StringManagerUtils.stringToInteger(deviceType), calColumn);
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"itemName\":\""+obj[1]+"\",");
			result_json.append("\"itemColumn\":\""+obj[2]+"\",");
			result_json.append("\"calColumnName\":\""+calColumnName+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolRunStatusItems(String classes,String deviceType,String protocolCode) {
		StringBuffer result_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
		
		String sql="select t.id,t.name,t.mappingcolumn,t.calcolumn from tbl_datamapping t where 1=1 ";
		if(StringManagerUtils.stringToInteger(classes)==1){//如果选中的是协议
			List<String> protocolMappingColumnList=new ArrayList<String>();
			if(modbusProtocolConfig!=null){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					if(protocolCode.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getCode()) ){
						
						for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
							String columnName="";
							if(loadProtocolMappingColumnByTitleMap.containsKey(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle())){
								columnName=loadProtocolMappingColumnByTitleMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle()).getMappingColumn();
							}
							protocolMappingColumnList.add(columnName);
						}
						if(protocolMappingColumnList.size()>0){
							sql+=" and t.mappingcolumn in ("+StringManagerUtils.joinStringArr2(protocolMappingColumnList, ",")+")";
						}else{
							sql+=" and 1=2";
						}
						break;
					}
				}
			}
		}
		sql+= "order by t.id";
		String configedRunStatusSql="select t.id,t.protocol,t.itemname,t.itemmappingcolumn,t.protocoltype,t.resolutionmode,t.runvalue,t.stopvalue,t.runcondition,t.stopcondition "
				+ " from TBL_RUNSTATUSCONFIG t "
				+ " where t.protocol='"+protocolCode+"' ";
		int configedRunStatusIndex=-99;
		String configedRunStatusol="";
		
		String columns="["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",\"width\":50 ,\"children\":[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"itemName\",\"flex\":3,\"children\":[] }"
				+ "]";
		List<?> list=this.findCallSql(sql);
		List<?> configedRunStatusList=this.findCallSql(configedRunStatusSql);
		if(configedRunStatusList.size()>0){
			Object[] obj = (Object[]) configedRunStatusList.get(0);
			configedRunStatusol=obj[3]+"";
		}
		
		List<String> protocolNameList=new ArrayList<>();
		List<String> protocolCodeList=new ArrayList<>();
		List<String> itemNameList=new ArrayList<>();
		List<String> itemColumnList=new ArrayList<>();
		List<String> calColumnList=new ArrayList<>();
		List<Integer> resolutionModeList=new ArrayList<>();
		if(modbusProtocolConfig!=null){
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){

				if(StringManagerUtils.stringToInteger(classes)==1){
					if(protocolCode.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getCode())){
						for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
							String columnName="";
							if(loadProtocolMappingColumnByTitleMap.containsKey(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle())){
								columnName=loadProtocolMappingColumnByTitleMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle()).getMappingColumn();
							}
							
							for (int k = 0; k < list.size(); k++) {
								Object[] obj = (Object[]) list.get(k);
								if((obj[2]+"").equalsIgnoreCase(columnName)){
									protocolNameList.add(modbusProtocolConfig.getProtocol().get(i).getName());
									protocolCodeList.add(modbusProtocolConfig.getProtocol().get(i).getCode());
									resolutionModeList.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getResolutionMode());
									itemNameList.add(obj[1]+"");
									itemColumnList.add(obj[2]+"");
									calColumnList.add(obj[3]+"");
								}
							}
						}
						break;
					}
				}else{
					for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
						String columnName="";
						if(loadProtocolMappingColumnByTitleMap.containsKey(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle())){
							columnName=loadProtocolMappingColumnByTitleMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle()).getMappingColumn();
						}
						for (int k = 0; k < list.size(); k++) {
							Object[] obj = (Object[]) list.get(k);
							if((obj[2]+"").equalsIgnoreCase(columnName)){
								protocolNameList.add(modbusProtocolConfig.getProtocol().get(i).getName());
								protocolCodeList.add(modbusProtocolConfig.getProtocol().get(i).getCode());
								resolutionModeList.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getResolutionMode());
								itemNameList.add(obj[1]+"");
								itemColumnList.add(obj[2]+"");
								calColumnList.add(obj[3]+"");
							}
						}
					}
				}
			
			}
		}
		
		result_json.append("{\"success\":true,\"totalCount\":" + protocolNameList.size() + ",\"columns\":"+columns+",\"totalRoot\":[");
		for (int i = 0; i < protocolNameList.size(); i++) {
			result_json.append("{\"id\":\""+(i+1)+"\",");
			result_json.append("\"protocolName\":\""+protocolNameList.get(i)+"\",");
			result_json.append("\"protocolCode\":\""+protocolCodeList.get(i)+"\",");
			result_json.append("\"resolutionMode\":"+resolutionModeList.get(i)+",");
			result_json.append("\"itemName\":\""+itemNameList.get(i)+"\",");
			result_json.append("\"itemColumn\":\""+itemColumnList.get(i)+"\",");
			result_json.append("\"calColumn\":\""+calColumnList.get(i)+"\"},");
			
			if(configedRunStatusol.equalsIgnoreCase(itemColumnList.get(i))){
				configedRunStatusIndex=i;
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("],\"configedRunStatusIndex\":"+configedRunStatusIndex+"}");
		
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolRunStatusItemsMeaning(String status,String deviceType,String protocolCode,String itemName,String itemColumn,String resolutionMode) {
		StringBuffer result_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
		
		List<Integer> runValueIndexList=new ArrayList<Integer>();
		List<Integer> runConfigValueList=new ArrayList<Integer>();
		List<String> runConditionList=new ArrayList<>();
		
		List<Integer> stopValueIndexList=new ArrayList<Integer>();
		List<Integer> stopConfigValueList=new ArrayList<Integer>();
		List<String> stopConditionList=new ArrayList<>();
		String setValueSql="select t.runvalue,t.stopvalue,t.runcondition,t.stopcondition"
				+ " from tbl_runstatusconfig t "
				+ " where t.protocol='"+protocolCode+"' and t.itemname='"+itemName+"' and t.itemmappingcolumn='"+itemColumn+"' ";
		List<?> list=this.findCallSql(setValueSql);
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			String runValueStr=obj[0]+"";
			String stopValueStr=obj[1]+"";
			String runConditionStr=obj[2]+"";
			String stopConditionStr=obj[3]+"";
			if(StringManagerUtils.isNotNull(runValueStr)){
				String[] runValueArr=runValueStr.split(",");
				for(int i=0;i<runValueArr.length;i++){
					if(StringManagerUtils.isNum(runValueArr[i])){
						runConfigValueList.add(StringManagerUtils.stringToInteger(runValueArr[i]));
					}
				}
			}
			if(StringManagerUtils.isNotNull(stopValueStr)){
				String[] stopValueArr=stopValueStr.split(",");
				for(int i=0;i<stopValueArr.length;i++){
					if(StringManagerUtils.isNum(stopValueArr[i])){
						stopConfigValueList.add(StringManagerUtils.stringToInteger(stopValueArr[i]));
					}
				}
			}
			if(StringManagerUtils.isNotNull(runConditionStr)){//>=,0;
				String[] runConditionArr=runConditionStr.split(";");
				for(int i=0;i<runConditionArr.length;i++){
					if(StringManagerUtils.isNotNull(runConditionArr[i])){
						runConditionList.add(runConditionArr[i]);
					}
				}
			}
			
			if(StringManagerUtils.isNotNull(stopConditionStr)){
				String[] stoprunConditionArr=stopConditionStr.split(";");
				for(int i=0;i<stoprunConditionArr.length;i++){
					if(StringManagerUtils.isNotNull(stoprunConditionArr[i])){
						stopConditionList.add(stoprunConditionArr[i]);
					}
					
				}
			}
		}
		String columns="["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",\"width\":50 ,\"children\":[] },"
				+ "{ \"header\":\"数值\",\"dataIndex\":\"value\",\"flex\":1,\"children\":[] },"
				+ "{ \"header\":\"含义\",\"dataIndex\":\"meaning\",\"flex\":2,\"children\":[] }"
				+ "]";
		
		if(StringManagerUtils.stringToInteger(resolutionMode)==2){//数据量
			columns="["
					+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",\"width\":50 ,\"children\":[] },"
					+ "{ \"header\":\"运算关系\",\"dataIndex\":\"condition\",\"flex\":1,\"children\":[] },"
					+ "{ \"header\":\"值\",\"dataIndex\":\"value\",\"flex\":1,\"children\":[] }"
					+ "]";
		}
		
		result_json.append("{\"success\":true,\"resolutionMode\":"+resolutionMode+",\"columns\":"+columns+",\"totalRoot\":[");
		int totalCount=0;
		if(StringManagerUtils.stringToInteger(resolutionMode)==1){//枚举量
			if(modbusProtocolConfig!=null){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					if(protocolCode.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getCode())){
						for(int j=0;j<modbusProtocolConfig.getProtocol().get(i).getItems().size();j++){
							String columnName="";
							if(loadProtocolMappingColumnByTitleMap.containsKey(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle())){
								columnName=loadProtocolMappingColumnByTitleMap.get(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle()).getMappingColumn();
							}
							if(itemColumn.equalsIgnoreCase(columnName) && itemName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getTitle())){
								if(modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getMeaning()!=null && modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getMeaning().size()>0){
									for(int k=0;k<modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getMeaning().size();k++){
										totalCount++;
										result_json.append("{\"id\":\""+(k+1)+"\",");
										result_json.append("\"value\":\""+modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getMeaning().get(k).getValue()+"\",");
										result_json.append("\"meaning\":\""+modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getMeaning().get(k).getMeaning()+"\"},");
										
										if(StringManagerUtils.existOrNot(runConfigValueList, modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getMeaning().get(k).getValue())){
											runValueIndexList.add(k);
										}else if(StringManagerUtils.existOrNot(stopConfigValueList, modbusProtocolConfig.getProtocol().get(i).getItems().get(j).getMeaning().get(k).getValue())){
											stopValueIndexList.add(k);
										}
									}
								}
								break;
							}
						}
						break;
					}
				}
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}else if(StringManagerUtils.stringToInteger(resolutionMode)==2){//数据量
			totalCount=4;
			String runValue1="",runValue2="",runValue3="",runValue4="";
			String stopValue1="",stopValue2="",stopValue3="",stopValue4="";
			for(int i=0;i<runConditionList.size();i++){
				String[] runConditionArr= runConditionList.get(i).split(",");
				if(runConditionArr.length==2){
					if(">".equalsIgnoreCase(runConditionArr[0])){
						runValue1=runConditionArr[1];
						runValueIndexList.add(0);
					}else if(">=".equalsIgnoreCase(runConditionArr[0])){
						runValue2=runConditionArr[1];
						runValueIndexList.add(1);
					}else if("<=".equalsIgnoreCase(runConditionArr[0])){
						runValue3=runConditionArr[1];
						runValueIndexList.add(2);
					}else if("<".equalsIgnoreCase(runConditionArr[0])){
						runValue4=runConditionArr[1];
						runValueIndexList.add(3);
					}
				}
			}
			for(int i=0;i<stopConditionList.size();i++){
				String[] stopConditionArr= stopConditionList.get(i).split(",");
				if(stopConditionArr.length==2){
					if(">".equalsIgnoreCase(stopConditionArr[0])){
						stopValue1=stopConditionArr[1];
						stopValueIndexList.add(0);
					}else if(">=".equalsIgnoreCase(stopConditionArr[0])){
						stopValue2=stopConditionArr[1];
						stopValueIndexList.add(1);
					}else if("<=".equalsIgnoreCase(stopConditionArr[0])){
						stopValue3=stopConditionArr[1];
						stopValueIndexList.add(2);
					}else if("<".equalsIgnoreCase(stopConditionArr[0])){
						stopValue4=stopConditionArr[1];
						stopValueIndexList.add(3);
					}
				}
			}
			
			result_json.append("{\"id\":1,");
			result_json.append("\"condition\":\"大于\",");
			if("1".equals(status)){
				result_json.append("\"value\":\""+runValue1+"\"},");
			}else{
				result_json.append("\"value\":\""+stopValue1+"\"},");
			}
			
			result_json.append("{\"id\":2,");
			result_json.append("\"condition\":\"大于等于\",");
			if("1".equals(status)){
				result_json.append("\"value\":\""+runValue2+"\"},");
			}else{
				result_json.append("\"value\":\""+stopValue2+"\"},");
			}
			
			result_json.append("{\"id\":3,");
			result_json.append("\"condition\":\"小于等于\",");
			if("1".equals(status)){
				result_json.append("\"value\":\""+runValue3+"\"},");
			}else{
				result_json.append("\"value\":\""+stopValue3+"\"},");
			}
			
			result_json.append("{\"id\":4,");
			result_json.append("\"condition\":\"小于\",");
			if("1".equals(status)){
				result_json.append("\"value\":\""+runValue4+"\"}");
			}else{
				result_json.append("\"value\":\""+stopValue4+"\"}");
			}
		}
		String runValueIndex=StringUtils.join(runValueIndexList, ",");
		String stopValueIndex=StringUtils.join(stopValueIndexList, ",");
		result_json.append("],\"totalCount\":"+totalCount+",\"runValueIndex\":["+runValueIndex+"],\"stopValueIndex\":["+stopValueIndex+"]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public void saveDatabaseColumnMappingTable(DatabaseMappingProHandsontableChangedData databaseMappingProHandsontableChangedData,String protocolType) throws Exception {
		if(databaseMappingProHandsontableChangedData.getUpdatelist()!=null){
			String updateSql="";
			for(int i=0;i<databaseMappingProHandsontableChangedData.getUpdatelist().size();i++){
				String calColumn=MemoryDataManagerTask.getCalculateColumnFromName(StringManagerUtils.stringToInteger(protocolType), 
						databaseMappingProHandsontableChangedData.getUpdatelist().get(i).getCalColumnName());
				updateSql="update tbl_datamapping t set t.calcolumn='"+calColumn+"'"
						+ " where t.name='"+databaseMappingProHandsontableChangedData.getUpdatelist().get(i).getItemName()+"' "
						+ " and t.mappingcolumn='"+databaseMappingProHandsontableChangedData.getUpdatelist().get(i).getItemColumn()+"' ";
				getBaseDao().updateOrDeleteBySql(updateSql);
			}
		}
	}
	
	public int saveProtocolRunStatusConfig(String protocolCode,String resolutionMode,String itemName,String itemColumn,String deviceType,
			String runValue,String stopValue,String runCondition,String stopCondition) throws Exception {
		String recordCountSql="select count(1) from tbl_runstatusconfig t "
				+ " where t.protocol='"+protocolCode+"' ";
		int recordCount=this.getTotalCountRows(recordCountSql);
		String insertOrUpdateSql="";
		if(recordCount==0){
			insertOrUpdateSql="insert into tbl_runstatusconfig(protocol,itemname,itemmappingcolumn,resolutionMode,runvalue,stopvalue,runCondition,stopCondition) "
					+ "values ('"+protocolCode+"','"+itemName+"','"+itemColumn+"',"+resolutionMode+",'"+runValue+"','"+stopValue+"','"+runCondition+"','"+stopCondition+"')";
		}else{
			insertOrUpdateSql="update tbl_runstatusconfig t"
					+ " set t.resolutionMode="+resolutionMode+","
					+ " t.itemname='"+itemName+"',t.itemmappingcolumn='"+itemColumn+"', "
					+ " t.runValue='"+runValue+"',t.stopValue='"+stopValue+"', "
					+ " t.runCondition='"+runCondition+"',t.stopCondition='"+stopCondition+"' "
					+ " where t.protocol='"+protocolCode+"' ";
		}
		
		int result=getBaseDao().updateOrDeleteBySql(insertOrUpdateSql);
		return result;
	}
	
	public boolean judgeProtocolExistOrNot(String protocolName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(protocolName)) {
//			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
//			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
//				if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
//					flag = true;
//					break;
//				}
//			}
			String sql = "select t.id from TBL_PROTOCOL t where t.name='"+protocolName+"'";
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
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
	
	public boolean judgeDisplayUnitExistOrNot(String protocolName,String unitName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(protocolName)&&StringManagerUtils.isNotNull(unitName)) {
			String sql = "select t.id from TBL_DISPLAY_UNIT_CONF t where t.protocol='"+protocolName+"' and t.unit_name='"+unitName+"'";
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
	
	public boolean judgeInstanceExistOrNot(String instanceName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(instanceName)) {
			String sql = "select t.id from TBL_PROTOCOLINSTANCE t where t.name='"+instanceName+"'";
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgeAlarmInstanceExistOrNot(String instanceName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(instanceName)) {
			String sql = "select t.id from TBL_PROTOCOLALARMINSTANCE t where  t.name='"+instanceName+"'";
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgeDisplayInstanceExistOrNot(String instanceName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(instanceName)) {
			String sql = "select t.id from TBL_PROTOCOLDISPLAYINSTANCE t where t.name='"+instanceName+"'";
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgeReportUnitExistOrNot(String unitName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(unitName)) {
			String sql = "select t.id from tbl_report_unit_conf t where t.unit_name='"+unitName+"'";
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean judgeReportInstanceExistOrNot(String instanceName) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(instanceName)) {
			String sql = "select t.id from TBL_PROTOCOLREPORTINSTANCE t where t.name='"+instanceName+"'";
			List<?> list = this.findCallSql(sql);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public String exportProtocolConfigData(String protocolName,String protocolCode,
			String acqUnitStr,String acqGroupStr,
			String displayUnitStr,
			String alarmUnitStr,
			String acqInstanceStr,
			String displayInstanceStr,
			String alarmInstanceStr) {
		String result="";
		if(StringManagerUtils.isNotNull(protocolCode)){
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			String sql="select t.id,t.name,t.code,t.items,t.sort from TBL_PROTOCOL t "
					+ " where t.code='"+protocolCode+"'";
			List<?> list = this.findCallSql(sql);
			if(list.size()>0){
				Object[] obj=(Object[])list.get(0);
				ExportProtocolConfig exportProtocolConfig=new ExportProtocolConfig();
				exportProtocolConfig.init();
				
				String itemsStr=StringManagerUtils.CLOBObjectToString(obj[3]);
				if(!StringManagerUtils.isNotNull(itemsStr)){
					itemsStr="[]";
				}
				StringBuffer protocolBuff=new StringBuffer();
				protocolBuff.append("{");
				protocolBuff.append("\"Id\":\""+obj[0]+"\",");
				protocolBuff.append("\"Name\":\""+obj[1]+"\",");
				protocolBuff.append("\"Code\":\""+obj[2]+"\",");
				protocolBuff.append("\"Sort\":"+obj[4]+",");
				protocolBuff.append("\"Items\":"+itemsStr+"");
				protocolBuff.append("}");

				type = new TypeToken<ModbusProtocolConfig.Protocol>() {}.getType();
				ModbusProtocolConfig.Protocol protocol=gson.fromJson(protocolBuff.toString(), type);

				if(protocol!=null){
					Collections.sort(protocol.getItems());//排序
				}
				exportProtocolConfig.setProtocol(protocol);
				//存储字段表
				exportProtocolConfig.setDataMappingList(new ArrayList<DataMapping>());
				exportProtocolConfig.setProtocolRunStatusConfigList(new ArrayList<ProtocolRunStatusConfig>());
				if(exportProtocolConfig.getProtocol().getItems()!=null){
					List<String> itemNameList=new ArrayList<String>();
					for(int i=0;i<exportProtocolConfig.getProtocol().getItems().size();i++){
						itemNameList.add(exportProtocolConfig.getProtocol().getItems().get(i).getTitle());
					}
					if(itemNameList.size()>0){
						String mappingSql="select t.id,t.name,t.mappingcolumn,t.mappingmode,t.calcolumn,t.repetitiontimes,t.protocoltype "
								+ " from TBL_DATAMAPPING t "
								+ " where t.name in ("+StringManagerUtils.joinStringArr2(itemNameList, ",")+") "
								+ " order by t.id";
						List<?> mappingList = this.findCallSql(mappingSql);
						for(int i=0;i<itemNameList.size();i++){
							for(int j=0;j<mappingList.size();j++){
								Object[] mappingObj=(Object[])mappingList.get(j);
								if(itemNameList.get(i).equalsIgnoreCase(mappingObj[1]+"")){
									DataMapping dataMapping=new DataMapping();
									dataMapping.setId(StringManagerUtils.stringToInteger(mappingObj[0]+""));
									dataMapping.setName(mappingObj[1]+"");
									dataMapping.setMappingColumn(mappingObj[2]+"");
									dataMapping.setCalColumn(mappingObj[4]==null?"":(mappingObj[4]+""));
									dataMapping.setProtocolType(StringManagerUtils.stringToInteger(mappingObj[5]+""));
									dataMapping.setMappingMode(StringManagerUtils.stringToInteger(mappingObj[3]+""));
									if(mappingObj[6]!=null){
										dataMapping.setRepetitionTimes(StringManagerUtils.stringToInteger(mappingObj[6]+""));
									}
									exportProtocolConfig.getDataMappingList().add(dataMapping);
								}
							}
						}
					}
				}
				//运行状态配置
				String runStatusConfigSql="select t.id,t.protocol,t.itemname,t.itemmappingcolumn,t.runvalue,t.stopvalue,t.protocoltype "
						+ " from tbl_runstatusconfig t,tbl_protocol t2 "
						+ " where t.protocol=t2.code "
						+ " and t2.code='"+exportProtocolConfig.getProtocol().getCode()+"' ";
				List<?> runStatusConfigList = this.findCallSql(runStatusConfigSql);
				if(runStatusConfigList.size()>0){
					Object[] runStatusConfigObj=(Object[])runStatusConfigList.get(0);
					ProtocolRunStatusConfig protocolRunStatusConfig=new ProtocolRunStatusConfig();
					protocolRunStatusConfig.setId(StringManagerUtils.stringToInteger(runStatusConfigObj[0]+""));
					protocolRunStatusConfig.setProtocol(runStatusConfigObj[1]+"");
					protocolRunStatusConfig.setItemName(runStatusConfigObj[2]+"");
					protocolRunStatusConfig.setItemMappingColumn(runStatusConfigObj[3]+"");
					protocolRunStatusConfig.setProtocolType(StringManagerUtils.stringToInteger(runStatusConfigObj[6]+""));
					protocolRunStatusConfig.setRunValue(new ArrayList<Integer>());
					protocolRunStatusConfig.setStopValue(new ArrayList<Integer>());
					String runValueStr=runStatusConfigObj[4]!=null?(runStatusConfigObj[4]+""):"";
					String stopValueStr=runStatusConfigObj[5]!=null?(runStatusConfigObj[5]+""):"";
					if(StringManagerUtils.isNotNull(runValueStr)){
						String[] runValueArr=runValueStr.split(",");
						for(int i=0;i<runValueArr.length;i++){
							if(StringManagerUtils.isNum(runValueArr[i])){
								protocolRunStatusConfig.getRunValue().add(StringManagerUtils.stringToInteger(runValueArr[i]));
							}
						}
					}
					if(StringManagerUtils.isNotNull(stopValueStr)){
						String[] stopValueArr=stopValueStr.split(",");
						for(int i=0;i<stopValueArr.length;i++){
							if(StringManagerUtils.isNum(stopValueArr[i])){
								protocolRunStatusConfig.getStopValue().add(StringManagerUtils.stringToInteger(stopValueArr[i]));
							}
						}
					}
					exportProtocolConfig.getProtocolRunStatusConfigList().add(protocolRunStatusConfig);
				}
				
				if(StringManagerUtils.isNotNull(acqUnitStr)){
					String acqUnitSql="select t.id,t.unit_code,t.unit_name,t.protocol,t.remark from TBL_ACQ_UNIT_CONF t where t.id in ("+acqUnitStr+") order by t.id";
					List<?> acqUnitQueryList = this.findCallSql(acqUnitSql);
					if(acqUnitQueryList.size()>0){
						exportProtocolConfig.setAcqUnitList(new ArrayList<ExportProtocolConfig.AcqUnit>());
						for(int i=0;i<acqUnitQueryList.size();i++){
							Object[] acqUnitObj=(Object[])acqUnitQueryList.get(0);
							ExportProtocolConfig.AcqUnit acqUnit=new ExportProtocolConfig.AcqUnit();
							acqUnit.setId(StringManagerUtils.stringToInteger(acqUnitObj[0]+""));
							acqUnit.setUnitCode(acqUnitObj[1]+"");
							acqUnit.setUnitName(acqUnitObj[2]+"");
							acqUnit.setProtocol(acqUnitObj[3]+"");
							acqUnit.setRemark(acqUnitObj[4]+"");
							acqUnit.setAcqGroupList(new ArrayList<ExportProtocolConfig.AcqGroup>());
							exportProtocolConfig.getAcqUnitList().add(acqUnit);
						}
						
						if(StringManagerUtils.isNotNull(acqGroupStr)){
							String acqGroupSql="select t.id,t.group_code,t.group_name,t.grouptiminginterval,t.groupsavinginterval,t.protocol,t.type,t.remark,t3.id as unitid "
									+ " from tbl_acq_group_conf t,tbl_acq_group2unit_conf t2,tbl_acq_unit_conf t3 "
									+ " where t.id=t2.groupid and t2.unitid=t3.id "
									+ " and t.id in ("+acqGroupStr+") and t3.id in ("+acqUnitStr+") "
									+ " order by t3.id,t.id";
							String acqItemSql="select t.id,t.itemid,t.itemname,t.itemcode,t.groupid,t.bitindex,t.matrix "
									+ " from tbl_acq_item2group_conf t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4 "
									+ " where t.groupid=t2.id and t2.id=t3.groupid and t3.unitid=t4.id "
									+ " and t2.id in ("+acqGroupStr+") and t4.id in ("+acqUnitStr+") "
									+ " order by t4.id,t2.id,t.id";
							List<?> acqGroupQueryList = this.findCallSql(acqGroupSql);
							List<?> acqItemQueryList = this.findCallSql(acqItemSql);
							for(int i=0;i<exportProtocolConfig.getAcqUnitList().size();i++){
								for(int j=0;j<acqGroupQueryList.size();j++){
									Object[] acqGroupObj=(Object[])acqGroupQueryList.get(j);
									if(StringManagerUtils.stringToInteger(acqGroupObj[8]+"")==exportProtocolConfig.getAcqUnitList().get(i).getId()){
										ExportProtocolConfig.AcqGroup acqGroup=new ExportProtocolConfig.AcqGroup();
										acqGroup.setId(StringManagerUtils.stringToInteger(acqGroupObj[0]+""));
										acqGroup.setGroupCode(acqGroupObj[1]+"");
										acqGroup.setGroupName(acqGroupObj[2]+"");
										acqGroup.setGroupTimingInterval(StringManagerUtils.stringToInteger(acqGroupObj[3]+""));
										acqGroup.setGroupSavingInterval(StringManagerUtils.stringToInteger(acqGroupObj[4]+""));
										acqGroup.setProtocol(acqGroupObj[5]+"");
										acqGroup.setType(StringManagerUtils.stringToInteger(acqGroupObj[6]+""));
										acqGroup.setRemark((acqGroupObj[7]+"").replaceAll("null", ""));
										acqGroup.setAcqItemList(new ArrayList<ExportProtocolConfig.AcqItem>());
										
										for(int k=0;k<acqItemQueryList.size();k++){
											Object[] acqItemObj=(Object[])acqItemQueryList.get(k);
											if(StringManagerUtils.stringToInteger(acqItemObj[4]+"")==acqGroup.getId()){
												ExportProtocolConfig.AcqItem acqItem=new ExportProtocolConfig.AcqItem();
												acqItem.setId(StringManagerUtils.stringToInteger(acqItemObj[0]+""));
												acqItem.setItemId(StringManagerUtils.stringToInteger(acqItemObj[1]+""));
												acqItem.setItemName((acqItemObj[2]+"").replaceAll("null", ""));
												acqItem.setItemCode((acqItemObj[3]+"").replaceAll("null", ""));
												acqItem.setGroupId(acqGroup.getId());
												
												if(StringManagerUtils.isInteger(acqItemObj[5]+"")){
													acqItem.setBitIndex(StringManagerUtils.stringToInteger(acqItemObj[5]+""));
												}else{
													acqItem.setBitIndex(-99);
												}
												acqItem.setMatrix((acqItemObj[6]+"").replaceAll("null", ""));
												acqGroup.getAcqItemList().add(acqItem);
											}
										}
										
										exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().add(acqGroup);
									}
								}
							}
						}
						
						if(StringManagerUtils.isNotNull(displayUnitStr)){
							String displayUnitSql="select t.id,t.unit_code,t.unit_name,t.protocol,t.acqunitid,t.remark "
									+ " from tbl_display_unit_conf t,tbl_acq_unit_conf t2 "
									+ " where t.acqunitid=t2.id and t.id in ("+displayUnitStr+") and t2.id in("+acqUnitStr+") "
									+ " order by t2.id,t.id";
							String displayItemSql="select t.id,t.itemid,t.itemname,t.itemcode,t.sort,t.bitindex,t.showlevel,t.realtimecurveconf,t.historycurveconf,t.type,t.matrix,t.unitid "
									+ " from tbl_display_items2unit_conf t, tbl_display_unit_conf t2,tbl_acq_unit_conf t3 "
									+ " where t.unitid=t2.id and t2.acqunitid=t3.id "
									+ " and t2.id in ("+displayUnitStr+") and t3.id in("+acqUnitStr+") "
									+ " order by t3.id,t2.id,t.type,t.id";
							List<?> displayUnitQueryList = this.findCallSql(displayUnitSql);
							List<?> displayItemQueryList = this.findCallSql(displayItemSql);
							
							if(displayUnitQueryList.size()>0){
								exportProtocolConfig.setDisplayUnitList(new ArrayList<ExportProtocolConfig.DisplayUnit>());
								for(int i=0;i<displayUnitQueryList.size();i++){
									Object[] displayUnitObj=(Object[])displayUnitQueryList.get(i);
									ExportProtocolConfig.DisplayUnit displayUnit=new ExportProtocolConfig.DisplayUnit();
									displayUnit.setId(StringManagerUtils.stringToInteger(displayUnitObj[0]+""));
									displayUnit.setUnitCode(displayUnitObj[1]+"");
									displayUnit.setUnitName(displayUnitObj[2]+"");
									displayUnit.setProtocol(displayUnitObj[3]+"");
									displayUnit.setAcqUnitId(StringManagerUtils.stringToInteger(displayUnitObj[4]+""));
									displayUnit.setRemark((displayUnitObj[5]+"").replaceAll("null", ""));
									displayUnit.setDisplayItemList(new ArrayList<ExportProtocolConfig.DisplayItem>());
									
									for(int j=0;j<displayItemQueryList.size();j++){
										Object[] displayItemObj=(Object[])displayItemQueryList.get(j);
										if(StringManagerUtils.stringToInteger(displayItemObj[11]+"")==displayUnit.getId()){
											ExportProtocolConfig.DisplayItem displayItem=new ExportProtocolConfig.DisplayItem();
											displayItem.setId(StringManagerUtils.stringToInteger(displayItemObj[0]+""));
											if(StringManagerUtils.isInteger(displayItemObj[1]+"")){
												displayItem.setItemId(StringManagerUtils.stringToInteger(displayItemObj[1]+""));
											}else{
												displayItem.setItemId(-99);
											}
											displayItem.setItemName(displayItemObj[2]+"");
											displayItem.setItemCode(displayItemObj[3]+"");
											
											if(StringManagerUtils.isInteger(displayItemObj[4]+"")){
												displayItem.setSort(StringManagerUtils.stringToInteger(displayItemObj[4]+""));
											}else{
												displayItem.setSort(-99);
											}
											
											if(StringManagerUtils.isInteger(displayItemObj[5]+"")){
												displayItem.setBitIndex(StringManagerUtils.stringToInteger(displayItemObj[5]+""));
											}else{
												displayItem.setBitIndex(-99);
											}
											
											if(StringManagerUtils.isInteger(displayItemObj[6]+"")){
												displayItem.setShowLevel(StringManagerUtils.stringToInteger(displayItemObj[6]+""));
											}else{
												displayItem.setShowLevel(-99);
											}
											
											displayItem.setRealtimeCurveConf((displayItemObj[7]+"").replaceAll("null", ""));
											displayItem.setHistoryCurveConf((displayItemObj[8]+"").replaceAll("null", ""));
											
											displayItem.setType(StringManagerUtils.stringToInteger(displayItemObj[9]+""));
											
											displayItem.setMatrix((displayItemObj[10]+"").replaceAll("null", ""));
											
											displayItem.setUnitId(StringManagerUtils.stringToInteger(displayItemObj[11]+""));
											
											displayUnit.getDisplayItemList().add(displayItem);
										}
									}
									
									exportProtocolConfig.getDisplayUnitList().add(displayUnit);
								}
							}
						}
						
						if(StringManagerUtils.isNotNull(acqInstanceStr)){
							String acqInstanceSql="select t.id,t.name,t.code,t.acqprotocoltype,t.ctrlprotocoltype,"
									+ "t.signinprefixsuffixhex,t.signinprefix,t.signinsuffix,t.signinidhex,"
									+ "t.heartbeatprefixsuffixhex,t.heartbeatprefix,t.heartbeatsuffix,"
									+ "t.packetsendinterval,t.sort,t.unitid "
									+ " from TBL_PROTOCOLINSTANCE t "
									+ " where t.id in("+acqInstanceStr+") and t.unitid in ("+acqUnitStr+") "
									+ " order by t.id";
							List<?> acqInstanceQueryList = this.findCallSql(acqInstanceSql);
							if(acqInstanceQueryList.size()>0){
								exportProtocolConfig.setAcqInstanceList(new ArrayList<ExportProtocolConfig.AcqInstance>());
								for(int i=0;i<acqInstanceQueryList.size();i++){
									Object[] acqInstanceObj=(Object[])acqInstanceQueryList.get(i);
									ExportProtocolConfig.AcqInstance acqInstance=new ExportProtocolConfig.AcqInstance();
									acqInstance.setId(StringManagerUtils.stringToInteger(acqInstanceObj[0]+""));
									acqInstance.setName(acqInstanceObj[1]+"");
									acqInstance.setCode(acqInstanceObj[2]+"");
									acqInstance.setAcqProtocolType(acqInstanceObj[3]+"");
									acqInstance.setCtrlProtocolType(acqInstanceObj[4]+"");
									acqInstance.setSignInPrefixSuffixHex(StringManagerUtils.stringToInteger(acqInstanceObj[5]+""));
									acqInstance.setSignInPrefix((acqInstanceObj[6]+"").replaceAll("null", ""));
									acqInstance.setSignInSuffix((acqInstanceObj[7]+"").replaceAll("null", ""));
									acqInstance.setSignInIDHex(StringManagerUtils.stringToInteger(acqInstanceObj[8]+""));
									acqInstance.setHeartbeatPrefixSuffixHex(StringManagerUtils.stringToInteger(acqInstanceObj[9]+""));
									acqInstance.setHeartbeatPrefix((acqInstanceObj[10]+"").replaceAll("null", ""));
									acqInstance.setHeartbeatSuffix((acqInstanceObj[11]+"").replaceAll("null", ""));

									if(StringManagerUtils.isInteger(acqInstanceObj[12]+"")){
										acqInstance.setPacketSendInterval(StringManagerUtils.stringToInteger(acqInstanceObj[12]+""));
									}else{
										acqInstance.setPacketSendInterval(-99);
									}

									if(StringManagerUtils.isInteger(acqInstanceObj[13]+"")){
										acqInstance.setSort(StringManagerUtils.stringToInteger(acqInstanceObj[13]+""));
									}else{
										acqInstance.setSort(-99);
									}
									
									acqInstance.setUnitId(StringManagerUtils.stringToInteger(acqInstanceObj[14]+""));
									
									exportProtocolConfig.getAcqInstanceList().add(acqInstance);
								}
							}
						}
						
						if(StringManagerUtils.isNotNull(displayInstanceStr) &&  StringManagerUtils.isNotNull(displayUnitStr)){
							String displayInstanceSql="select t.id,t.name,t.code,t.displayunitid,t.sort "
									+ " from TBL_PROTOCOLDISPLAYINSTANCE t,tbl_display_unit_conf t2,tbl_acq_unit_conf t3 "
									+ " where t.displayunitid=t2.id and t2.acqunitid=t3.id "
									+ " and t.id in("+displayInstanceStr+") and t2.id in("+displayUnitStr+") and t3.id in ("+acqUnitStr+") "
									+ " order by t.displayunitid,t.id";
							List<?> displayInstanceQueryList = this.findCallSql(displayInstanceSql);
							if(displayInstanceQueryList.size()>0){
								exportProtocolConfig.setDisplayInstanceList(new ArrayList<ExportProtocolConfig.DisplayInstance>());
								for(int i=0;i<displayInstanceQueryList.size();i++){
									Object[] displayInstanceObj=(Object[])displayInstanceQueryList.get(i);
									ExportProtocolConfig.DisplayInstance displayInstance=new ExportProtocolConfig.DisplayInstance();
									displayInstance.setId(StringManagerUtils.stringToInteger(displayInstanceObj[0]+""));
									displayInstance.setName(displayInstanceObj[1]+"");
									displayInstance.setCode(displayInstanceObj[2]+"");
									displayInstance.setDisplayUnitId(StringManagerUtils.stringToInteger(displayInstanceObj[3]+""));
									
									if(StringManagerUtils.isInteger(displayInstanceObj[4]+"")){
										displayInstance.setSort(StringManagerUtils.stringToInteger(displayInstanceObj[4]+""));
									}else{
										displayInstance.setSort(-99);
									}
									
									exportProtocolConfig.getDisplayInstanceList().add(displayInstance);
									
								}
							}
						}
					}
				}
				
				if(StringManagerUtils.isNotNull(alarmUnitStr)){
					String alarmUnitSql="select t.id,t.unit_code,t.unit_name,t.protocol,t.remark from TBL_ALARM_UNIT_CONF t where t.protocol='"+protocol.getName()+"'";
					String alarmItemSql="select t.id,t.itemid,t.itemname,t.itemcode,t.itemaddr,t.value,t.upperlimit,t.lowerlimit,t.hystersis,t.delay,"
							+ " t.alarmlevel,t.alarmsign,t.type,t.bitindex,t.issendmessage,t.issendmail,t.unitid "
							+ " from tbl_alarm_item2unit_conf t,  TBL_ALARM_UNIT_CONF t2 "
							+ " where t.unitid=t2.id "
							+ " and t2.protocol='"+protocol.getName()+"' "
							+ " order by t2.id,t.id";
					List<?> alarmUnitQueryList = this.findCallSql(alarmUnitSql);
					List<?> alarmItemQueryList = this.findCallSql(alarmItemSql);
					
					if(alarmUnitQueryList.size()>0){
						exportProtocolConfig.setAlarmUnitList(new ArrayList<ExportProtocolConfig.AlarmUnit>());
						for(int i=0;i<alarmUnitQueryList.size();i++){
							Object[] alarmUnitObj=(Object[])alarmUnitQueryList.get(i);
							ExportProtocolConfig.AlarmUnit alarmUnit=new ExportProtocolConfig.AlarmUnit();
							alarmUnit.setId(StringManagerUtils.stringToInteger(alarmUnitObj[0]+""));
							alarmUnit.setUnitCode(alarmUnitObj[1]+"");
							alarmUnit.setUnitName(alarmUnitObj[2]+"");
							alarmUnit.setProtocol(alarmUnitObj[3]+"");
							alarmUnit.setRemark((alarmUnitObj[4]+"").replaceAll("null", ""));
							alarmUnit.setAlarmItemList(new ArrayList<ExportProtocolConfig.AlarmItem>());
							for(int j=0;j<alarmItemQueryList.size();j++){
								Object[] alarmItemObj=(Object[])alarmItemQueryList.get(j);
								if(StringManagerUtils.stringToInteger(alarmItemObj[16]+"")==alarmUnit.getId()){
									ExportProtocolConfig.AlarmItem  alarmItem=new ExportProtocolConfig.AlarmItem();
									alarmItem.setId(StringManagerUtils.stringToInteger(alarmItemObj[0]+""));
									alarmItem.setItemId(StringManagerUtils.isInteger(alarmItemObj[1]+"")?StringManagerUtils.stringToInteger(alarmItemObj[1]+""):null);
									alarmItem.setItemName(alarmItemObj[2]+"");
									alarmItem.setItemCode(alarmItemObj[3]+"");
									alarmItem.setItemAddr(StringManagerUtils.isInteger(alarmItemObj[4]+"")?StringManagerUtils.stringToInteger(alarmItemObj[4]+""):null);
									alarmItem.setValue(StringManagerUtils.stringToFloat(alarmItemObj[5]+""));
									alarmItem.setUpperLimit(StringManagerUtils.isNotNull(alarmItemObj[6]+"")?StringManagerUtils.stringToFloat(alarmItemObj[6]+""):null);
									alarmItem.setLowerLimit(StringManagerUtils.isNotNull(alarmItemObj[7]+"")?StringManagerUtils.stringToFloat(alarmItemObj[7]+""):null);
									alarmItem.setHystersis(StringManagerUtils.isNotNull(alarmItemObj[8]+"")?StringManagerUtils.stringToFloat(alarmItemObj[8]+""):null);
									alarmItem.setDelay(StringManagerUtils.stringToInteger(alarmItemObj[9]+""));
									
									alarmItem.setAlarmLevel(StringManagerUtils.stringToInteger(alarmItemObj[10]+""));
									alarmItem.setAlarmSign(StringManagerUtils.stringToInteger(alarmItemObj[11]+""));
									alarmItem.setType(StringManagerUtils.stringToInteger(alarmItemObj[12]+""));
									
									alarmItem.setBitIndex(StringManagerUtils.isInteger(alarmItemObj[13]+"")?StringManagerUtils.stringToInteger(alarmItemObj[13]+""):null);
									
									alarmItem.setIsSendMessage(StringManagerUtils.stringToInteger(alarmItemObj[14]+""));
									alarmItem.setIsSendMail(StringManagerUtils.stringToInteger(alarmItemObj[15]+""));
									
									alarmItem.setUnitId(StringManagerUtils.stringToInteger(alarmItemObj[16]+""));
									
									alarmUnit.getAlarmItemList().add(alarmItem);
								}
							}
							
							exportProtocolConfig.getAlarmUnitList().add(alarmUnit);
						}
						
						if(StringManagerUtils.isNotNull(alarmInstanceStr)){
							String alarmInstanceSql="select t.id,t.name,t.code,t.alarmunitid,t.sort "
									+ " from TBL_PROTOCOLALARMINSTANCE t,tbl_alarm_unit_conf t2 "
									+ " where t.alarmunitid=t2.id "
									+ " and t.id in ("+alarmInstanceStr+") and t2.id in ("+alarmUnitStr+") "
									+ " order by t.alarmunitid,t.id";
							List<?> alarmInstanceQueryList = this.findCallSql(alarmInstanceSql);
							if(alarmInstanceQueryList.size()>0){
								exportProtocolConfig.setAlarmInstanceList(new ArrayList<ExportProtocolConfig.AlarmInstance>());
								for(int i=0;i<alarmInstanceQueryList.size();i++){
									Object[] alarmInstanceObj=(Object[])alarmInstanceQueryList.get(i);
									ExportProtocolConfig.AlarmInstance alarmInstance=new ExportProtocolConfig.AlarmInstance();
									alarmInstance.setId(StringManagerUtils.stringToInteger(alarmInstanceObj[0]+""));
									alarmInstance.setName(alarmInstanceObj[1]+"");
									alarmInstance.setCode(alarmInstanceObj[2]+"");
									alarmInstance.setAlarmUnitId(StringManagerUtils.stringToInteger(alarmInstanceObj[3]+""));
									
									if(StringManagerUtils.isInteger(alarmInstanceObj[4]+"")){
										alarmInstance.setSort(StringManagerUtils.stringToInteger(alarmInstanceObj[4]+""));
									}else{
										alarmInstance.setSort(-99);
									}
									
									exportProtocolConfig.getAlarmInstanceList().add(alarmInstance);
								}
							}
						}
					}
				}
				result=gson.toJson(exportProtocolConfig);
			}
		}
		return result;
	}
	
	
	public String getImportProtocolContentData(String id,String classes,String type){
		StringBuffer result_json = new StringBuffer();
		int index=1;
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
				+ "{ \"header\":\"响应模式\",\"dataIndex\":\"acqMode\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		List<String> itemsList=new ArrayList<String>();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null){
			ModbusProtocolConfig.Protocol protocolConfig=exportProtocolConfig.getProtocol();
			
			if("1".equals(classes) && "0".equals(type) && exportProtocolConfig.getAcqUnitList()!=null && exportProtocolConfig.getAcqUnitList().size()>0){//采控单元
				for(int i=0;i<exportProtocolConfig.getAcqUnitList().size();i++){
					if(StringManagerUtils.stringToInteger(id)==exportProtocolConfig.getAcqUnitList().get(i).getId()){
						if(exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList()!=null){
							for(int j=0;j<exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().size();j++){
								if(exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getAcqItemList()!=null){
									for(int k=0;k<exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getAcqItemList().size();k++){
										if(!StringManagerUtils.existOrNot(itemsList, exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getAcqItemList().get(k).getItemName(),false)){
											itemsList.add(exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getAcqItemList().get(k).getItemName());
										}
									}
								}
								
								
							}
						}
						
						break;
					}
				}
			}else if("1".equals(classes) && "3".equals(type) && exportProtocolConfig.getAcqInstanceList()!=null && exportProtocolConfig.getAcqInstanceList().size()>0){//采控实例
				int acqUnitId=0;
				for(int i=0;i<exportProtocolConfig.getAcqInstanceList().size();i++){
					if(StringManagerUtils.stringToInteger(id)==exportProtocolConfig.getAcqInstanceList().get(i).getId()){
						acqUnitId=exportProtocolConfig.getAcqInstanceList().get(i).getUnitId();
						break;
					}
				}
				
				if(exportProtocolConfig.getAcqUnitList()!=null && exportProtocolConfig.getAcqUnitList().size()>0){
					for(int i=0;i<exportProtocolConfig.getAcqUnitList().size();i++){
						if(acqUnitId==exportProtocolConfig.getAcqUnitList().get(i).getId()){
							if(exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList()!=null){
								for(int j=0;j<exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().size();j++){
									if(exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getAcqItemList()!=null){
										for(int k=0;k<exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getAcqItemList().size();k++){
											if(!StringManagerUtils.existOrNot(itemsList, exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getAcqItemList().get(k).getItemName(),false)){
												itemsList.add(exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getAcqItemList().get(k).getItemName());
											}
										}
									}
								}
							}
							break;
						}
					}
				}
			}else if("2".equals(classes) && "0".equals(type)){//采控组
				if(exportProtocolConfig.getAcqUnitList()!=null && exportProtocolConfig.getAcqUnitList().size()>0){
					
					for(int i=0;i<exportProtocolConfig.getAcqUnitList().size();i++){
						boolean match=false;
						if(exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList()!=null){
							if(exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList()!=null){
								for(int j=0;j<exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().size();j++){
									if(StringManagerUtils.stringToInteger(id)==exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getId()){
										match=true;
										if(exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getAcqItemList()!=null){
											for(int k=0;k<exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getAcqItemList().size();k++){
												if(!StringManagerUtils.existOrNot(itemsList, exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getAcqItemList().get(k).getItemName(),false)){
													itemsList.add(exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getAcqItemList().get(k).getItemName());
												}
											}
										}
										break;
									}
								}
							}
						}
						if(match){
							break;
						}
					}
					
				}
			}

			Collections.sort(protocolConfig.getItems());
			for(int j=0;j<protocolConfig.getItems().size();j++){
				if(StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(),false)){
					String RWTypeName="只读";
					if("r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWTypeName="只读";
					}else if("w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWTypeName="只写";
					}else if("rw".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWTypeName="读写";
					}
					
					String resolutionMode="数据量";
					if(protocolConfig.getItems().get(j).getResolutionMode()==0){
						resolutionMode="开关量";
					}else if(protocolConfig.getItems().get(j).getResolutionMode()==1){
						resolutionMode="枚举量";
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
							+ "\"resolutionMode\":\""+resolutionMode+"\","
							+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?"主动上传":"被动响应")+"\"},");
					index++;
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
	
	public String getImportProtocolDisplayInstanceAcqItemsConfigData(String id,String typeStr){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示级别\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveColor\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveConfList=new ArrayList<String>();
		List<String> historyCurveConfList=new ArrayList<String>();
		
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null && exportProtocolConfig.getDisplayUnitList()!=null && exportProtocolConfig.getDisplayUnitList().size()>0 ){
			int unitId=0;
			if(StringManagerUtils.stringToInteger(typeStr)==1){//显示单元
				unitId=StringManagerUtils.stringToInteger(id);
			}else if(StringManagerUtils.stringToInteger(typeStr)==4){//显示实例
				if(exportProtocolConfig.getDisplayInstanceList()!=null && exportProtocolConfig.getDisplayInstanceList().size()>0){
					for(int i=0;i<exportProtocolConfig.getDisplayInstanceList().size();i++){
						if(StringManagerUtils.stringToInteger(id)==exportProtocolConfig.getDisplayInstanceList().get(i).getId()){
							unitId=exportProtocolConfig.getDisplayInstanceList().get(i).getDisplayUnitId();
							break;
						}
					}
				}
			}
			if(unitId>0){
				for(int i=0;i<exportProtocolConfig.getDisplayUnitList().size();i++){
					if(unitId==exportProtocolConfig.getDisplayUnitList().get(i).getId()){
						if(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList()!=null){
							for(int j=0;j<exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().size();j++){
								if(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getType()==0){
									itemsList.add(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getItemName());
									itemsBitIndexList.add((exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getBitIndex()==-99?"":exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getBitIndex())+"");
									itemsSortList.add((exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getSort()==-99?"":exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getSort())+"");
									itemsShowLevelList.add((exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getShowLevel()==-99?"":exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getShowLevel())+"");
									
									String realtimeCurveConfShowValue="";
									String historyCurveConfShowValue="";
									
									CurveConf realtimeCurveConfObj=null;
									if(StringManagerUtils.isNotNull(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getRealtimeCurveConf()) && !"\"\"".equals(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getRealtimeCurveConf())){
										type = new TypeToken<CurveConf>() {}.getType();
										realtimeCurveConfObj=gson.fromJson(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getRealtimeCurveConf(), type);
									}
									
									CurveConf historyCurveConfObj=null;
									if(StringManagerUtils.isNotNull(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getHistoryCurveConf()) && !"\"\"".equals(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getHistoryCurveConf())){
										type = new TypeToken<CurveConf>() {}.getType();
										historyCurveConfObj=gson.fromJson(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getHistoryCurveConf(), type);
									}
									
									if(realtimeCurveConfObj!=null){
										realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+realtimeCurveConfObj.getColor();
									}
									if(historyCurveConfObj!=null){
										historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+historyCurveConfObj.getColor();
									}
									
									realtimeCurveConfList.add(realtimeCurveConfShowValue);
									historyCurveConfList.add(historyCurveConfShowValue);
								}
							}
						}
						
						break;
					}
				}
			}

			ModbusProtocolConfig.Protocol protocolConfig=exportProtocolConfig.getProtocol();
			Collections.sort(protocolConfig.getItems());
			int index=1;
			for(int j=0;j<protocolConfig.getItems().size();j++){
				if(StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(), false)){
					String sort="";
					String showLevel="";
					
					String realtimeCurveConfShowValue="";
					String historyCurveConfShowValue="";
					
					if(protocolConfig.getItems().get(j).getResolutionMode()==0
							&&protocolConfig.getItems().get(j).getMeaning()!=null
							&&protocolConfig.getItems().get(j).getMeaning().size()>0){
						Collections.sort(protocolConfig.getItems().get(j).getMeaning());//排序
						for(int k=0;k<protocolConfig.getItems().get(j).getMeaning().size();k++){
							sort="";
							showLevel="";
							realtimeCurveConfShowValue="";
							historyCurveConfShowValue="";
							for(int m=0;m<itemsList.size();m++){
								if(itemsList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())
										&&itemsBitIndexList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"")
									){
									sort=itemsSortList.get(m);
									showLevel=itemsShowLevelList.get(m);
									realtimeCurveConfShowValue=realtimeCurveConfList.get(m);
									historyCurveConfShowValue=historyCurveConfList.get(m);
									break;
								}
							}
							
							result_json.append("{"
									+ "\"id\":"+(index)+","
									+ "\"title\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
									+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
									+ "\"showLevel\":\""+showLevel+"\","
									+ "\"sort\":\""+sort+"\","
									+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
									+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\""
									+ "},");
							index++;
						}
					}else{
						for(int k=0;k<itemsList.size();k++){
							if(itemsList.get(k).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())){
								sort=itemsSortList.get(k);
								showLevel=itemsShowLevelList.get(k);
								realtimeCurveConfShowValue=realtimeCurveConfList.get(k);
								historyCurveConfShowValue=realtimeCurveConfList.get(k);
								break;
							}
						}
						result_json.append("{"
								+ "\"id\":"+(index)+","
								+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
								+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
								+ "\"showLevel\":\""+showLevel+"\","
								+ "\"sort\":\""+sort+"\","
								+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
								+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\""
								+ "},");
						index++;
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
	
	public String getImportProtocolDisplayInstanceCalItemsConfigData(String id,String typeStr){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		List<byte[]> rpcCalItemSet=null;
		
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示级别\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveColor\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsCodeList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveConfList=new ArrayList<String>();
		List<String> historyCurveConfList=new ArrayList<String>();
		
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null && exportProtocolConfig.getDisplayUnitList()!=null && exportProtocolConfig.getDisplayUnitList().size()>0 ){
			String key="rpcCalItemList";
			Jedis jedis=null;
			
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
			
			int unitId=0;
			if(StringManagerUtils.stringToInteger(typeStr)==1){//显示单元
				unitId=StringManagerUtils.stringToInteger(id);
			}else if(StringManagerUtils.stringToInteger(typeStr)==4){//显示实例
				if(exportProtocolConfig.getDisplayInstanceList()!=null && exportProtocolConfig.getDisplayInstanceList().size()>0){
					for(int i=0;i<exportProtocolConfig.getDisplayInstanceList().size();i++){
						if(StringManagerUtils.stringToInteger(id)==exportProtocolConfig.getDisplayInstanceList().get(i).getId()){
							unitId=exportProtocolConfig.getDisplayInstanceList().get(i).getDisplayUnitId();
							break;
						}
					}
				}
			}
			if(unitId>0){
				for(int i=0;i<exportProtocolConfig.getDisplayUnitList().size();i++){
					if(unitId==exportProtocolConfig.getDisplayUnitList().get(i).getId()){
						if(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList()!=null){
							for(int j=0;j<exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().size();j++){
								if(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getType()==1){
									itemsList.add(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getItemName());
									itemsCodeList.add(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getItemCode());
									itemsBitIndexList.add((exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getBitIndex()==-99?"":exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getBitIndex())+"");
									itemsSortList.add((exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getSort()==-99?"":exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getSort())+"");
									itemsShowLevelList.add((exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getShowLevel()==-99?"":exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getShowLevel())+"");
									
									String realtimeCurveConfShowValue="";
									String historyCurveConfShowValue="";
									
									CurveConf realtimeCurveConfObj=null;
									if(StringManagerUtils.isNotNull(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getRealtimeCurveConf()) && !"\"\"".equals(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getRealtimeCurveConf())){
										type = new TypeToken<CurveConf>() {}.getType();
										realtimeCurveConfObj=gson.fromJson(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getRealtimeCurveConf(), type);
									}
									
									CurveConf historyCurveConfObj=null;
									if(StringManagerUtils.isNotNull(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getHistoryCurveConf()) && !"\"\"".equals(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getHistoryCurveConf())){
										type = new TypeToken<CurveConf>() {}.getType();
										historyCurveConfObj=gson.fromJson(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getHistoryCurveConf(), type);
									}
									
									if(realtimeCurveConfObj!=null){
										realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+realtimeCurveConfObj.getColor();
									}
									if(historyCurveConfObj!=null){
										historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+historyCurveConfObj.getColor();
									}
									
									realtimeCurveConfList.add(realtimeCurveConfShowValue);
									historyCurveConfList.add(historyCurveConfShowValue);
								}
							}
						}
						break;
					}
				}
			}
		}
		
		if(rpcCalItemSet!=null){
			int index=1;
			for(byte[] rpcCalItemByteArr:rpcCalItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
				if(StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(), false)){
					String sort="";
					String showLevel="";
					String realtimeCurveConfShowValue="";
					String historyCurveConfShowValue="";

					for(int k=0;k<itemsList.size();k++){
						if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
							sort=itemsSortList.get(k);
							showLevel=itemsShowLevelList.get(k);
							realtimeCurveConfShowValue=realtimeCurveConfList.get(k);
							historyCurveConfShowValue=historyCurveConfList.get(k);
							break;
						}
					}
					result_json.append("{"
							+ "\"id\":"+index+","
							+ "\"title\":\""+calItem.getName()+"\","
							+ "\"unit\":\""+calItem.getUnit()+"\","
							+ "\"showLevel\":\""+showLevel+"\","
							+ "\"sort\":\""+sort+"\","
							+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
							+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\""
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
	
	public String getImportProtocolDisplayInstanceCtrlItemsConfigData(String id,String typeStr){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"单位\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\"显示级别\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"数据顺序\",\"dataIndex\":\"sort\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsSortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();

		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null && exportProtocolConfig.getDisplayUnitList()!=null && exportProtocolConfig.getDisplayUnitList().size()>0 ){
			int unitId=0;
			if(StringManagerUtils.stringToInteger(typeStr)==1){//显示单元
				unitId=StringManagerUtils.stringToInteger(id);
			}else if(StringManagerUtils.stringToInteger(typeStr)==4){//显示实例
				if(exportProtocolConfig.getDisplayInstanceList()!=null && exportProtocolConfig.getDisplayInstanceList().size()>0){
					for(int i=0;i<exportProtocolConfig.getDisplayInstanceList().size();i++){
						if(StringManagerUtils.stringToInteger(id)==exportProtocolConfig.getDisplayInstanceList().get(i).getId()){
							unitId=exportProtocolConfig.getDisplayInstanceList().get(i).getDisplayUnitId();
							break;
						}
					}
				}
			}
			if(unitId>0){
				for(int i=0;i<exportProtocolConfig.getDisplayUnitList().size();i++){
					if(unitId==exportProtocolConfig.getDisplayUnitList().get(i).getId()){
						if(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList()!=null){
							for(int j=0;j<exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().size();j++){
								if(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getType()==2){
									itemsList.add(exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getItemName());
									itemsBitIndexList.add((exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getBitIndex()==-99?"":exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getBitIndex())+"");
									itemsSortList.add((exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getSort()==-99?"":exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getSort())+"");
									itemsShowLevelList.add((exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getShowLevel()==-99?"":exportProtocolConfig.getDisplayUnitList().get(i).getDisplayItemList().get(j).getShowLevel())+"");
								}
							}
						}
						break;
					}
				}
			}
		}
		ModbusProtocolConfig.Protocol protocolConfig=exportProtocolConfig.getProtocol();

//		Collections.sort(protocolConfig.getItems());
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
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getImportProtocolAlarmContentNumItemsConfigData(String id,String typeStr){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
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
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null && exportProtocolConfig.getAlarmUnitList()!=null && exportProtocolConfig.getAlarmUnitList().size()>0 ){
			int unitId=0;
			if(StringManagerUtils.stringToInteger(typeStr)==2){//报警单元
				unitId=StringManagerUtils.stringToInteger(id);
			}else if(StringManagerUtils.stringToInteger(typeStr)==5){//报警实例
				if(exportProtocolConfig.getAlarmInstanceList()!=null && exportProtocolConfig.getAlarmInstanceList().size()>0){
					for(int i=0;i<exportProtocolConfig.getAlarmInstanceList().size();i++){
						if(StringManagerUtils.stringToInteger(id)==exportProtocolConfig.getAlarmInstanceList().get(i).getId()){
							unitId=exportProtocolConfig.getAlarmInstanceList().get(i).getAlarmUnitId();
							break;
						}
					}
				}
			}
			if(unitId>0){
				for(int i=0;i<exportProtocolConfig.getAlarmUnitList().size();i++){
					if(unitId==exportProtocolConfig.getAlarmUnitList().get(i).getId()){
						if(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList()!=null){
							int index=1;
							for(int j=0;j<exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().size();j++){
								if(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getType()==2){
									result_json.append("{\"id\":"+(index)+","
											+ "\"title\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemName()+"\","
											+ "\"code\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemCode()+"\","
											+ "\"addr\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemAddr()+"\","
											+ "\"upperLimit\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getUpperLimit()+"\","
											+ "\"lowerLimit\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getLowerLimit()+"\","
											+ "\"hystersis\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getHystersis()+"\","
											+ "\"delay\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getDelay()+"\","
											+ "\"alarmLevel\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmLevel()+"\","
											+ "\"alarmSign\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmSign()==1?"使能":"失效" )+"\","
											+ "\"isSendMessage\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMessage()==1?"是":"否" )+"\","
											+ "\"isSendMail\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMail()==1?"是":"否" )+"\"},");
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
	
	public String getImportProtocolAlarmContentCalNumItemsConfigData(String id,String typeStr){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		List<byte[]> calItemSet=null;
		
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
		
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null && exportProtocolConfig.getAlarmUnitList()!=null && exportProtocolConfig.getAlarmUnitList().size()>0 ){
			Jedis jedis=null;
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("rpcCalItemList".getBytes())){
					MemoryDataManagerTask.loadRPCCalculateItem();
				}
				calItemSet= jedis.zrange("rpcCalItemList".getBytes(), 0, -1);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(jedis!=null){
					jedis.close();
				}
			}
			
			
			int unitId=0;
			if(StringManagerUtils.stringToInteger(typeStr)==2){//报警单元
				unitId=StringManagerUtils.stringToInteger(id);
			}else if(StringManagerUtils.stringToInteger(typeStr)==5){//报警实例
				if(exportProtocolConfig.getAlarmInstanceList()!=null && exportProtocolConfig.getAlarmInstanceList().size()>0){
					for(int i=0;i<exportProtocolConfig.getAlarmInstanceList().size();i++){
						if(StringManagerUtils.stringToInteger(id)==exportProtocolConfig.getAlarmInstanceList().get(i).getId()){
							unitId=exportProtocolConfig.getAlarmInstanceList().get(i).getAlarmUnitId();
							break;
						}
					}
				}
			}
			if(unitId>0){
				for(int i=0;i<exportProtocolConfig.getAlarmUnitList().size();i++){
					if(unitId==exportProtocolConfig.getAlarmUnitList().get(i).getId()){
						if(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList()!=null){
							int index=1;
							for(int j=0;j<exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().size();j++){
								if(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getType()==5){
									String unit="";
									if(calItemSet!=null){
										for(byte[] rpcCalItemByteArr:calItemSet){
											CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(rpcCalItemByteArr);
											if(calItem.getDataType()==2&&(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemCode()).equalsIgnoreCase(calItem.getCode())){
												unit=calItem.getUnit();
												break;
											}
										}
									}
									result_json.append("{\"id\":"+(index)+","
											+ "\"title\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemName()+"\","
											+ "\"code\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemCode()+"\","
											+ "\"unit\":\""+unit+"\","
											+ "\"upperLimit\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getUpperLimit()+"\","
											+ "\"lowerLimit\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getLowerLimit()+"\","
											+ "\"hystersis\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getHystersis()+"\","
											+ "\"delay\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getDelay()+"\","
											+ "\"alarmLevel\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmLevel()+"\","
											+ "\"alarmSign\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmSign()==1?"使能":"失效" )+"\","
											+ "\"isSendMessage\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMessage()==1?"是":"否" )+"\","
											+ "\"isSendMail\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMail()==1?"是":"否" )+"\"},");
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
	
	public String getImportProtocolAlarmContentSwitchItemsConfigData(String id,String typeStr){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
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
		
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null && exportProtocolConfig.getAlarmUnitList()!=null && exportProtocolConfig.getAlarmUnitList().size()>0 ){
			int unitId=0;
			if(StringManagerUtils.stringToInteger(typeStr)==2){//报警单元
				unitId=StringManagerUtils.stringToInteger(id);
			}else if(StringManagerUtils.stringToInteger(typeStr)==5){//报警实例
				if(exportProtocolConfig.getAlarmInstanceList()!=null && exportProtocolConfig.getAlarmInstanceList().size()>0){
					for(int i=0;i<exportProtocolConfig.getAlarmInstanceList().size();i++){
						if(StringManagerUtils.stringToInteger(id)==exportProtocolConfig.getAlarmInstanceList().get(i).getId()){
							unitId=exportProtocolConfig.getAlarmInstanceList().get(i).getAlarmUnitId();
							break;
						}
					}
				}
			}
			if(unitId>0){
				for(int i=0;i<exportProtocolConfig.getAlarmUnitList().size();i++){
					if(unitId==exportProtocolConfig.getAlarmUnitList().get(i).getId()){
						if(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList()!=null){
							int index=1;
							for(int j=0;j<exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().size();j++){
								if(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getType()==0){
									int itemAddr=exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemAddr();
									int bitIndex=exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getBitIndex();
									String meaning="";
									for(int k=0;k<exportProtocolConfig.getProtocol().getItems().size();k++){
										if(itemAddr==exportProtocolConfig.getProtocol().getItems().get(k).getAddr()&&exportProtocolConfig.getProtocol().getItems().get(k).getMeaning()!=null&&exportProtocolConfig.getProtocol().getItems().get(k).getMeaning().size()>0){
											for(int l=0;l<exportProtocolConfig.getProtocol().getItems().get(k).getMeaning().size();k++){
												if(bitIndex==exportProtocolConfig.getProtocol().getItems().get(k).getMeaning().get(l).getValue()){
													meaning=exportProtocolConfig.getProtocol().getItems().get(k).getMeaning().get(l).getMeaning();
													break;
												}
											}
											break;
										}
									}
									
									result_json.append("{\"id\":"+(index)+","
											+ "\"title\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemName()+"\","
											+ "\"code\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemCode()+"\","
											+ "\"addr\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemAddr()+"\","
											+ "\"bitIndex\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getBitIndex()==-99?"":exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getBitIndex())+""+"\","
											+ "\"meaning\":\""+meaning+"\","
											+ "\"value\":\""+("1".equalsIgnoreCase(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getValue()+"")?"开":"关")+"\","
											+ "\"delay\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getDelay()+"\","
											+ "\"alarmLevel\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmLevel()+"\","
											+ "\"alarmSign\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmSign()==1?"使能":"失效" )+"\","
											+ "\"isSendMessage\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMessage()==1?"是":"否" )+"\","
											+ "\"isSendMail\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMail()==1?"是":"否" )+"\"},");
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
	
	public String getImportProtocolAlarmContentEnumItemsConfigData(String id,String typeStr){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"名称\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\"地址\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\"数值\",\"dataIndex\":\"value\",width:80 ,children:[] },"
				+ "{ \"header\":\"含义\",\"dataIndex\":\"meaning\",width:80 ,children:[] },"
				+ "{ \"header\":\"延时(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警级别\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\"报警开关\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送短信\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\"是否发送邮件\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null && exportProtocolConfig.getAlarmUnitList()!=null && exportProtocolConfig.getAlarmUnitList().size()>0 ){
			int unitId=0;
			if(StringManagerUtils.stringToInteger(typeStr)==2){//报警单元
				unitId=StringManagerUtils.stringToInteger(id);
			}else if(StringManagerUtils.stringToInteger(typeStr)==5){//报警实例
				if(exportProtocolConfig.getAlarmInstanceList()!=null && exportProtocolConfig.getAlarmInstanceList().size()>0){
					for(int i=0;i<exportProtocolConfig.getAlarmInstanceList().size();i++){
						if(StringManagerUtils.stringToInteger(id)==exportProtocolConfig.getAlarmInstanceList().get(i).getId()){
							unitId=exportProtocolConfig.getAlarmInstanceList().get(i).getAlarmUnitId();
							break;
						}
					}
				}
			}
			if(unitId>0){
				for(int i=0;i<exportProtocolConfig.getAlarmUnitList().size();i++){
					if(unitId==exportProtocolConfig.getAlarmUnitList().get(i).getId()){
						if(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList()!=null){
							int index=1;
							for(int j=0;j<exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().size();j++){
								if(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getType()==1){
									int itemAddr=exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemAddr();
									int value=StringManagerUtils.stringToInteger(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getValue()+"");
									String meaning="";
									for(int k=0;k<exportProtocolConfig.getProtocol().getItems().size();k++){
										if(itemAddr==exportProtocolConfig.getProtocol().getItems().get(k).getAddr()&&exportProtocolConfig.getProtocol().getItems().get(k).getMeaning()!=null&&exportProtocolConfig.getProtocol().getItems().get(k).getMeaning().size()>0){
											for(int l=0;l<exportProtocolConfig.getProtocol().getItems().get(k).getMeaning().size();k++){
												if(value==exportProtocolConfig.getProtocol().getItems().get(k).getMeaning().get(l).getValue()){
													meaning=exportProtocolConfig.getProtocol().getItems().get(k).getMeaning().get(l).getMeaning();
													break;
												}
											}
											break;
										}
									}
									
									result_json.append("{\"id\":"+(index)+","
											+ "\"title\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemName()+"\","
											+ "\"code\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemCode()+"\","
											+ "\"addr\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemAddr()+"\","
											+ "\"value\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getValue()+""+"\","
											+ "\"meaning\":\""+meaning+"\","
											+ "\"delay\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getDelay()+"\","
											+ "\"alarmLevel\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmLevel()+"\","
											+ "\"alarmSign\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmSign()==1?"使能":"失效" )+"\","
											+ "\"isSendMessage\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMessage()==1?"是":"否" )+"\","
											+ "\"isSendMail\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMail()==1?"是":"否" )+"\"},");
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
	
	public String getImportProtocolAlarmContentFESDiagramResultItemsConfigData(String id,String typeStr){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
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
		
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null && exportProtocolConfig.getAlarmUnitList()!=null && exportProtocolConfig.getAlarmUnitList().size()>0 ){
			int unitId=0;
			if(StringManagerUtils.stringToInteger(typeStr)==2){//报警单元
				unitId=StringManagerUtils.stringToInteger(id);
			}else if(StringManagerUtils.stringToInteger(typeStr)==5){//报警实例
				if(exportProtocolConfig.getAlarmInstanceList()!=null && exportProtocolConfig.getAlarmInstanceList().size()>0){
					for(int i=0;i<exportProtocolConfig.getAlarmInstanceList().size();i++){
						if(StringManagerUtils.stringToInteger(id)==exportProtocolConfig.getAlarmInstanceList().get(i).getId()){
							unitId=exportProtocolConfig.getAlarmInstanceList().get(i).getAlarmUnitId();
							break;
						}
					}
				}
			}
			if(unitId>0){
				for(int i=0;i<exportProtocolConfig.getAlarmUnitList().size();i++){
					if(unitId==exportProtocolConfig.getAlarmUnitList().get(i).getId()){
						if(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList()!=null){
							int index=1;
							for(int j=0;j<exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().size();j++){
								if(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getType()==4){
									result_json.append("{\"id\":"+(index)+","
											+ "\"title\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemName()+"\","
											+ "\"code\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemCode()+"\","
											+ "\"delay\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getDelay()+"\","
											+ "\"alarmLevel\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmLevel()+"\","
											+ "\"alarmSign\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmSign()==1?"使能":"失效" )+"\","
											+ "\"isSendMessage\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMessage()==1?"是":"否" )+"\","
											+ "\"isSendMail\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMail()==1?"是":"否" )+"\"},");
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
	
	public String getImportProtocolAlarmContentRunStatusItemsConfigData(String id,String typeStr){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
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
		
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null && exportProtocolConfig.getAlarmUnitList()!=null && exportProtocolConfig.getAlarmUnitList().size()>0 ){
			int unitId=0;
			if(StringManagerUtils.stringToInteger(typeStr)==2){//报警单元
				unitId=StringManagerUtils.stringToInteger(id);
			}else if(StringManagerUtils.stringToInteger(typeStr)==5){//报警实例
				if(exportProtocolConfig.getAlarmInstanceList()!=null && exportProtocolConfig.getAlarmInstanceList().size()>0){
					for(int i=0;i<exportProtocolConfig.getAlarmInstanceList().size();i++){
						if(StringManagerUtils.stringToInteger(id)==exportProtocolConfig.getAlarmInstanceList().get(i).getId()){
							unitId=exportProtocolConfig.getAlarmInstanceList().get(i).getAlarmUnitId();
							break;
						}
					}
				}
			}
			if(unitId>0){
				for(int i=0;i<exportProtocolConfig.getAlarmUnitList().size();i++){
					if(unitId==exportProtocolConfig.getAlarmUnitList().get(i).getId()){
						if(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList()!=null){
							int index=1;
							for(int j=0;j<exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().size();j++){
								if(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getType()==6){
									result_json.append("{\"id\":"+(index)+","
											+ "\"title\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemName()+"\","
											+ "\"code\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemCode()+"\","
											+ "\"delay\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getDelay()+"\","
											+ "\"alarmLevel\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmLevel()+"\","
											+ "\"alarmSign\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmSign()==1?"使能":"失效" )+"\","
											+ "\"isSendMessage\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMessage()==1?"是":"否" )+"\","
											+ "\"isSendMail\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMail()==1?"是":"否" )+"\"},");
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
	
	public String getImportProtocolAlarmContentCommStatusItemsConfigData(String id,String typeStr){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
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
		
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null && exportProtocolConfig.getAlarmUnitList()!=null && exportProtocolConfig.getAlarmUnitList().size()>0 ){
			int unitId=0;
			if(StringManagerUtils.stringToInteger(typeStr)==2){//报警单元
				unitId=StringManagerUtils.stringToInteger(id);
			}else if(StringManagerUtils.stringToInteger(typeStr)==5){//报警实例
				if(exportProtocolConfig.getAlarmInstanceList()!=null && exportProtocolConfig.getAlarmInstanceList().size()>0){
					for(int i=0;i<exportProtocolConfig.getAlarmInstanceList().size();i++){
						if(StringManagerUtils.stringToInteger(id)==exportProtocolConfig.getAlarmInstanceList().get(i).getId()){
							unitId=exportProtocolConfig.getAlarmInstanceList().get(i).getAlarmUnitId();
							break;
						}
					}
				}
			}
			if(unitId>0){
				for(int i=0;i<exportProtocolConfig.getAlarmUnitList().size();i++){
					if(unitId==exportProtocolConfig.getAlarmUnitList().get(i).getId()){
						if(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList()!=null){
							int index=1;
							for(int j=0;j<exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().size();j++){
								if(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getType()==3){
									result_json.append("{\"id\":"+(index)+","
											+ "\"title\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemName()+"\","
											+ "\"code\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getItemCode()+"\","
											+ "\"delay\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getDelay()+"\","
											+ "\"alarmLevel\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmLevel()+"\","
											+ "\"alarmSign\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmSign()==1?"使能":"失效" )+"\","
											+ "\"isSendMessage\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMessage()==1?"是":"否" )+"\","
											+ "\"isSendMail\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMail()==1?"是":"否" )+"\"},");
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
	
	public String saveImportProtocolData(String data){
		StringBuffer result_json = new StringBuffer();
		result_json.append("{\"success\":true,\"overlayList\":[");
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		
		type = new TypeToken<ImportProtocolContent>() {}.getType();
		ImportProtocolContent importProtocolContent=gson.fromJson(data, type);
		
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null){
			boolean protocolExist=this.judgeProtocolExistOrNot(exportProtocolConfig.getProtocol().getName());
			if(!protocolExist){//如果协议不存在，则不存在冲突
				//添加协议
				ProtocolModel protocolModel=new ProtocolModel();
				protocolModel.setId(exportProtocolConfig.getProtocol().getId());
				protocolModel.setCode(exportProtocolConfig.getProtocol().getCode());
				protocolModel.setName(exportProtocolConfig.getProtocol().getName());
				protocolModel.setSort(exportProtocolConfig.getProtocol().getSort());
				this.getBaseDao().addObject(protocolModel);
				//添加后，查询自动生成的id和code
				String addProtocolSql="select t.id,t.code from TBL_PROTOCOL t where t.name='"+exportProtocolConfig.getProtocol().getName()+"' ";
				List<?> addProtocolList=this.findCallSql(addProtocolSql.toString());
				if(addProtocolList.size()>0){//添加成功，更新地址项
					Object[] addProtocolObj = (Object[]) addProtocolList.get(0);
					int addProtocolId=StringManagerUtils.stringToInteger(addProtocolObj[0]+"");
					String addProtocolCode=addProtocolObj[1]+"";
					String updateProtocolItemsClobSql="update TBL_PROTOCOL t set t.items=? where t.code='"+addProtocolCode+"'";
					List<String> clobCont=new ArrayList<String>();
					clobCont.add(gson.toJson(exportProtocolConfig.getProtocol().getItems()));
					service.getBaseDao().executeSqlUpdateClob(updateProtocolItemsClobSql,clobCont);
					
					//添加协议相关配置
					if(importProtocolContent!=null){
						//采控单元、采控组及采控实例
						Map<Integer,Integer> acqUnitIdMapping=new HashMap<>();
						Map<Integer,Integer> displayUnitIdMapping=new HashMap<>();
						Map<Integer,Integer> alarmUnitIdMapping=new HashMap<>();
						
						if(importProtocolContent.getAcqUnitList()!=null && importProtocolContent.getAcqUnitList().size()>0 && exportProtocolConfig.getAcqUnitList()!=null ){
							for(int i=0;i<importProtocolContent.getAcqUnitList().size();i++){
								ExportProtocolConfig.AcqUnit acqUnit=null;
								for(int j=0;j<exportProtocolConfig.getAcqUnitList().size();j++){
									if(importProtocolContent.getAcqUnitList().get(i).getId()==exportProtocolConfig.getAcqUnitList().get(j).getId()){
										acqUnit=exportProtocolConfig.getAcqUnitList().get(j);
										break;
									}
								}
								if(acqUnit!=null){
									AcquisitionUnit acquisitionUnit=new AcquisitionUnit();
									acquisitionUnit.setId(acqUnit.getId());
									acquisitionUnit.setUnitName(acqUnit.getUnitName());
									acquisitionUnit.setUnitCode(acqUnit.getUnitCode());
									acquisitionUnit.setProtocol(acqUnit.getProtocol());
									acquisitionUnit.setRemark(acqUnit.getRemark());
									getBaseDao().addObject(acquisitionUnit);
									//添加后，获取自动生成的单元id、code
									String addAcqUnitSql="select t.id,t.unit_code from TBL_ACQ_UNIT_CONF t where t.protocol='"+acqUnit.getProtocol()+"' and t.unit_name='"+acqUnit.getUnitName()+"'";
									List<?> addAcqUnitList=this.findCallSql(addAcqUnitSql);
									if(addAcqUnitList.size()>0){//采控单元添加成功
										Object[] addAcqUnitObj = (Object[]) addAcqUnitList.get(0);
										int addAcqUnitId=StringManagerUtils.stringToInteger(addAcqUnitObj[0]+"");
										String addAcqUnitCode=addAcqUnitObj[1]+"";
										//将采集单元id变化记录下来
										acqUnitIdMapping.put(acqUnit.getId(), addAcqUnitId);
										//添加采控组
										if(acqUnit.getAcqGroupList()!=null && acqUnit.getAcqGroupList().size()>0){
											for(int j=0;j<acqUnit.getAcqGroupList().size();j++){
												if(StringManagerUtils.existOrNot(importProtocolContent.getAcqUnitList().get(i).getAcqGroupList(), acqUnit.getAcqGroupList().get(j).getId())){
													AcquisitionGroup acquisitionGroup=new AcquisitionGroup();
													acquisitionGroup.setId(acqUnit.getAcqGroupList().get(j).getId());
													acquisitionGroup.setGroupCode(acqUnit.getAcqGroupList().get(j).getGroupCode());
													acquisitionGroup.setGroupName(acqUnit.getAcqGroupList().get(j).getGroupName());
													acquisitionGroup.setGroupTimingInterval(acqUnit.getAcqGroupList().get(j).getGroupTimingInterval());
													acquisitionGroup.setGroupSavingInterval(acqUnit.getAcqGroupList().get(j).getGroupSavingInterval());
													acquisitionGroup.setProtocol(acqUnit.getAcqGroupList().get(j).getProtocol());
													acquisitionGroup.setType(acqUnit.getAcqGroupList().get(j).getType());
													acquisitionGroup.setRemark(acqUnit.getAcqGroupList().get(j).getRemark());
													getBaseDao().addObject(acquisitionGroup);
													//添加后，获取自动生成的单元id、code
													String addAcqGroupSql="select t.id,t.group_code from TBL_ACQ_GROUP_CONF t "
															+ " where t.group_name='"+acquisitionGroup.getGroupName()+"' and t.protocol='"+acquisitionGroup.getProtocol()+"'"
															+ " order by t.id desc";
													List<?> addAcqGroupList=this.findCallSql(addAcqGroupSql);
													if(addAcqGroupList.size()>0){
														Object[] addAcqGroupObj = (Object[]) addAcqGroupList.get(0);
														int addAcqGroupId=StringManagerUtils.stringToInteger(addAcqGroupObj[0]+"");
														String addAcqGroupCode=addAcqGroupObj[1]+"";
														//采控单元、采控组授权
														AcquisitionUnitGroup acquisitionUnitGroup = new AcquisitionUnitGroup();
														acquisitionUnitGroup.setUnitId(addAcqUnitId);
														acquisitionUnitGroup.setGroupId(addAcqGroupId);
														acquisitionUnitGroup.setMatrix("0,0,0");
														getBaseDao().addObject(acquisitionUnitGroup);
														//添加采控项
														if(acqUnit.getAcqGroupList().get(j).getAcqItemList()!=null && acqUnit.getAcqGroupList().get(j).getAcqItemList().size()>0){
															for(int k=0;k<acqUnit.getAcqGroupList().get(j).getAcqItemList().size();k++){
																AcquisitionGroupItem acquisitionGroupItem = new AcquisitionGroupItem();
																acquisitionGroupItem.setId(acqUnit.getAcqGroupList().get(j).getAcqItemList().get(k).getId());
																if(acqUnit.getAcqGroupList().get(j).getAcqItemList().get(k).getItemId()>0){
																	acquisitionGroupItem.setItemId(acqUnit.getAcqGroupList().get(j).getAcqItemList().get(k).getItemId());
																}else{
																	acquisitionGroupItem.setItemId(null);
																}
																acquisitionGroupItem.setItemName(acqUnit.getAcqGroupList().get(j).getAcqItemList().get(k).getItemName());
																acquisitionGroupItem.setItemCode(acqUnit.getAcqGroupList().get(j).getAcqItemList().get(k).getItemCode());
																acquisitionGroupItem.setGroupId(addAcqGroupId);
																acquisitionGroupItem.setMatrix(acqUnit.getAcqGroupList().get(j).getAcqItemList().get(k).getMatrix());
																if(acqUnit.getAcqGroupList().get(j).getAcqItemList().get(k).getBitIndex()>=0){
																	acquisitionGroupItem.setBitIndex(acqUnit.getAcqGroupList().get(j).getAcqItemList().get(k).getBitIndex());
																}else{
																	acquisitionGroupItem.setBitIndex(null);
																}
																getBaseDao().addObject(acquisitionGroupItem);
															}
														}
													}
												}
											}
										}
										//添加采控实例
										if(exportProtocolConfig.getAcqInstanceList()!=null && importProtocolContent.getAcqInstanceList()!=null && importProtocolContent.getAcqInstanceList().size()>0){
											for(int j=0;j<importProtocolContent.getAcqInstanceList().size();j++){
												ExportProtocolConfig.AcqInstance acqInstance=null;
												for(int k=0;k<exportProtocolConfig.getAcqInstanceList().size();k++){
													if(importProtocolContent.getAcqInstanceList().get(j)==exportProtocolConfig.getAcqInstanceList().get(k).getId()
															&& exportProtocolConfig.getAcqInstanceList().get(k).getUnitId()==acqUnit.getId()){
														acqInstance=exportProtocolConfig.getAcqInstanceList().get(k);
														break;
													}
												}
												if(acqInstance!=null){
													ProtocolInstance protocolInstance=new ProtocolInstance();
													protocolInstance.setId(acqInstance.getId());
													protocolInstance.setName(acqInstance.getName());
													protocolInstance.setCode(acqInstance.getCode());
													protocolInstance.setAcqProtocolType(acqInstance.getAcqProtocolType());
													protocolInstance.setCtrlProtocolType(acqInstance.getCtrlProtocolType());
													protocolInstance.setSignInPrefixSuffixHex(acqInstance.getSignInPrefixSuffixHex());
													protocolInstance.setSignInPrefix(acqInstance.getSignInPrefix());
													protocolInstance.setSignInSuffix(acqInstance.getSignInSuffix());
													protocolInstance.setSignInIDHex(acqInstance.getSignInIDHex());
													protocolInstance.setHeartbeatPrefixSuffixHex(acqInstance.getHeartbeatPrefixSuffixHex());
													protocolInstance.setHeartbeatPrefix(acqInstance.getHeartbeatPrefix());
													protocolInstance.setHeartbeatSuffix(acqInstance.getHeartbeatSuffix());
													protocolInstance.setPacketSendInterval(acqInstance.getPacketSendInterval());
													protocolInstance.setSort(acqInstance.getSort());
													protocolInstance.setUnitId(addAcqUnitId);
													
													getBaseDao().addObject(protocolInstance);
												}
											}
										}
									}else{
										
									}
								}
							}
						}
						
						//添加显示单元和显示实例
						if(importProtocolContent.getDisplayUnitList()!=null && importProtocolContent.getDisplayUnitList().size()>0 && exportProtocolConfig.getDisplayUnitList()!=null){
							for(int i=0;i<importProtocolContent.getDisplayUnitList().size();i++){
								ExportProtocolConfig.DisplayUnit displayUnit=null;
								for(int j=0;j<exportProtocolConfig.getDisplayUnitList().size();j++){
									if(importProtocolContent.getDisplayUnitList().get(i)==exportProtocolConfig.getDisplayUnitList().get(j).getId()  ){
										displayUnit=exportProtocolConfig.getDisplayUnitList().get(j);
										break;
									}
								}
								if(displayUnit!=null && acqUnitIdMapping.containsKey(displayUnit.getAcqUnitId())){
									DisplayUnit saveDisplayUnit=new DisplayUnit();
									saveDisplayUnit.setId(displayUnit.getId());
									saveDisplayUnit.setUnitCode(displayUnit.getUnitCode());
									saveDisplayUnit.setUnitName(displayUnit.getUnitName());
									saveDisplayUnit.setProtocol(displayUnit.getProtocol());
									saveDisplayUnit.setRemark(displayUnit.getRemark());
									saveDisplayUnit.setAcqUnitId(acqUnitIdMapping.get(displayUnit.getAcqUnitId()));
									getBaseDao().addObject(saveDisplayUnit);
									
									//添加后，获取自动生成的单元id、code
									String addDisplayUnitSql="select t.id,t.unit_code from TBL_DISPLAY_UNIT_CONF t where t.unit_name='"+saveDisplayUnit.getUnitName()+"' and t.acqunitid="+saveDisplayUnit.getAcqUnitId()+" order by t.id desc";
									List<?> addDisplayUnitList=this.findCallSql(addDisplayUnitSql);
									if(addDisplayUnitList.size()>0){//采控单元添加成功
										Object[] addDisplayUnitObj = (Object[]) addDisplayUnitList.get(0);
										int addDisplayUnitId=StringManagerUtils.stringToInteger(addDisplayUnitObj[0]+"");
										String addDisplayUnitCode=addDisplayUnitObj[1]+"";
										//将显示单元id变化记录下来
										displayUnitIdMapping.put(displayUnit.getId(), addDisplayUnitId);
										//添加显示项
										if(displayUnit.getDisplayItemList()!=null && displayUnit.getDisplayItemList().size()>0){
											for(int j=0;j<displayUnit.getDisplayItemList().size();j++){
												DisplayUnitItem displayUnitItem = new DisplayUnitItem();
												displayUnitItem.setId(displayUnit.getDisplayItemList().get(j).getId());
												displayUnitItem.setItemId(displayUnit.getDisplayItemList().get(j).getItemId()>0?displayUnit.getDisplayItemList().get(j).getItemId():null);
												displayUnitItem.setItemName(displayUnit.getDisplayItemList().get(j).getItemName());
												displayUnitItem.setItemCode(displayUnit.getDisplayItemList().get(j).getItemCode());
												displayUnitItem.setSort(displayUnit.getDisplayItemList().get(j).getSort()>=0?displayUnit.getDisplayItemList().get(j).getSort():null);
												displayUnitItem.setBitIndex(displayUnit.getDisplayItemList().get(j).getBitIndex()>=0?displayUnit.getDisplayItemList().get(j).getBitIndex():null);
												displayUnitItem.setShowLevel(displayUnit.getDisplayItemList().get(j).getShowLevel()>=0?displayUnit.getDisplayItemList().get(j).getShowLevel():null);
												displayUnitItem.setType(displayUnit.getDisplayItemList().get(j).getType());
												displayUnitItem.setRealtimeCurveConf(displayUnit.getDisplayItemList().get(j).getRealtimeCurveConf());
												displayUnitItem.setHistoryCurveConf(displayUnit.getDisplayItemList().get(j).getHistoryCurveConf());
												displayUnitItem.setUnitId(addDisplayUnitId);
												getBaseDao().addObject(displayUnitItem);
											}
										}
										
										
										//添加显示实例
										if(importProtocolContent.getDisplayInstanceList()!=null && importProtocolContent.getDisplayInstanceList().size()>0 && exportProtocolConfig.getDisplayInstanceList()!=null){
											for(int j=0;j<importProtocolContent.getDisplayInstanceList().size();j++){
												ExportProtocolConfig.DisplayInstance displayInstance=null;
												for(int k=0;k<exportProtocolConfig.getDisplayInstanceList().size();k++){
													if(importProtocolContent.getDisplayInstanceList().get(j)==exportProtocolConfig.getDisplayInstanceList().get(k).getId() ){
														displayInstance=exportProtocolConfig.getDisplayInstanceList().get(k);
														break;
													}
												}
												if(displayInstance!=null && displayUnitIdMapping.containsKey(displayInstance.getDisplayUnitId())){
													ProtocolDisplayInstance protocolDisplayInstance=new ProtocolDisplayInstance();
													protocolDisplayInstance.setId(displayInstance.getId());
													protocolDisplayInstance.setName(displayInstance.getName());
													protocolDisplayInstance.setCode(displayInstance.getCode());
													protocolDisplayInstance.setSort(displayInstance.getSort());
													protocolDisplayInstance.setDisplayUnitId(displayUnitIdMapping.get(displayInstance.getDisplayUnitId()));
													getBaseDao().addObject(protocolDisplayInstance);
												}
											}
										}
									}
								}
							}
						}
						
						//添加报警单元和报警实例
						if(importProtocolContent.getAlarmUnitList()!=null && importProtocolContent.getAlarmUnitList().size()>0 && exportProtocolConfig.getAlarmUnitList()!=null){
							for(int i=0;i<importProtocolContent.getAlarmUnitList().size();i++){
								ExportProtocolConfig.AlarmUnit alarmUnit=null;
								for(int j=0;j<exportProtocolConfig.getAlarmUnitList().size();j++){
									if(importProtocolContent.getAlarmUnitList().get(i)==exportProtocolConfig.getAlarmUnitList().get(j).getId()  ){
										alarmUnit=exportProtocolConfig.getAlarmUnitList().get(j);
										break;
									}
								}
								if(alarmUnit!=null){
									AlarmUnit saveAlarmUnit=new AlarmUnit();
									saveAlarmUnit.setId(alarmUnit.getId());
									saveAlarmUnit.setUnitCode(alarmUnit.getUnitCode());
									saveAlarmUnit.setUnitName(alarmUnit.getUnitName());
									saveAlarmUnit.setProtocol(alarmUnit.getProtocol());
									saveAlarmUnit.setRemark(alarmUnit.getRemark());
									getBaseDao().addObject(saveAlarmUnit);
									//添加后，获取自动生成的单元id、code
									String addAlarmUnitSql="select t.id,t.unit_code from TBL_ALARM_UNIT_CONF t where t.unit_name='"+saveAlarmUnit.getUnitName()+"' and t.protocol='"+saveAlarmUnit.getProtocol()+"' order by t.id desc";
									List<?> addAlarmUnitList=this.findCallSql(addAlarmUnitSql);
									if(addAlarmUnitList.size()>0){//报警单元添加成功
										Object[] addAlarmUnitObj = (Object[]) addAlarmUnitList.get(0);
										int addAlarmUnitId=StringManagerUtils.stringToInteger(addAlarmUnitObj[0]+"");
										String addAlarmUnitCode=addAlarmUnitObj[1]+"";
										//将报警单元id变化记录下来
										alarmUnitIdMapping.put(alarmUnit.getId(), addAlarmUnitId);
										
										//添加报警项
										if(alarmUnit.getAlarmItemList()!=null && alarmUnit.getAlarmItemList().size()>0){
											for(int j=0;j<alarmUnit.getAlarmItemList().size();j++){
												AlarmUnitItem alarmUnitItem=new AlarmUnitItem();
												alarmUnitItem.setId(alarmUnit.getAlarmItemList().get(j).getId());
												alarmUnitItem.setUnitId(addAlarmUnitId);
												alarmUnitItem.setItemId(alarmUnit.getAlarmItemList().get(j).getItemId());
												alarmUnitItem.setItemName(alarmUnit.getAlarmItemList().get(j).getItemName());
												alarmUnitItem.setItemCode(alarmUnit.getAlarmItemList().get(j).getItemCode());
												alarmUnitItem.setItemAddr(alarmUnit.getAlarmItemList().get(j).getItemAddr());
												alarmUnitItem.setBitIndex(alarmUnit.getAlarmItemList().get(j).getBitIndex());
												alarmUnitItem.setValue(alarmUnit.getAlarmItemList().get(j).getValue());
												alarmUnitItem.setUpperLimit(alarmUnit.getAlarmItemList().get(j).getUpperLimit());
												alarmUnitItem.setLowerLimit(alarmUnit.getAlarmItemList().get(j).getLowerLimit());
												alarmUnitItem.setHystersis(alarmUnit.getAlarmItemList().get(j).getHystersis());
												alarmUnitItem.setDelay(alarmUnit.getAlarmItemList().get(j).getDelay());
												alarmUnitItem.setAlarmLevel(alarmUnit.getAlarmItemList().get(j).getAlarmLevel());
												alarmUnitItem.setAlarmSign(alarmUnit.getAlarmItemList().get(j).getAlarmSign());
												alarmUnitItem.setType(alarmUnit.getAlarmItemList().get(j).getType());
												alarmUnitItem.setIsSendMessage(alarmUnit.getAlarmItemList().get(j).getIsSendMessage());
												alarmUnitItem.setIsSendMail(alarmUnit.getAlarmItemList().get(j).getIsSendMail());
												getBaseDao().addObject(alarmUnitItem);
											}
										}
										
										//添加报警实例
										if(importProtocolContent.getAlarmInstanceList()!=null && importProtocolContent.getAlarmInstanceList().size()>0 && exportProtocolConfig.getAlarmInstanceList()!=null){
											for(int j=0;j<importProtocolContent.getAlarmInstanceList().size();j++){
												ExportProtocolConfig.AlarmInstance alarmInstance=null;
												for(int k=0;k<exportProtocolConfig.getAlarmInstanceList().size();k++){
													if(importProtocolContent.getAlarmInstanceList().get(j)==exportProtocolConfig.getAlarmInstanceList().get(k).getId() ){
														alarmInstance=exportProtocolConfig.getAlarmInstanceList().get(k);
														break;
													}
												}
												if(alarmInstance!=null && alarmUnitIdMapping.containsKey(alarmInstance.getAlarmUnitId())){
													ProtocolAlarmInstance protocolAlarmInstance=new ProtocolAlarmInstance();
													protocolAlarmInstance.setId(alarmInstance.getId());
													protocolAlarmInstance.setName(alarmInstance.getName());
													protocolAlarmInstance.setCode(alarmInstance.getCode());
													protocolAlarmInstance.setSort(alarmInstance.getSort());
													protocolAlarmInstance.setAlarmUnitId(alarmUnitIdMapping.get(alarmInstance.getAlarmUnitId()));
													getBaseDao().addObject(protocolAlarmInstance);
												}
											}
										}
									}
								}
							}
						}
					}
				}else{//添加失败
					
				}
			}else{//如果协议已存在
				try {
					result_json.append("{\"classes\":0},");
					String existProtocolSql="select t.id,t.code from TBL_PROTOCOL t where t.name='"+exportProtocolConfig.getProtocol().getName()+"'";
					List<?> existProtocolList=this.findCallSql(existProtocolSql);
					if(existProtocolList.size()>0){
						Object[] existProtocolObj = (Object[]) existProtocolList.get(0);
						int existProtocolId=StringManagerUtils.stringToInteger(existProtocolObj[0]+"");
						String existProtocolCode=existProtocolObj[1]+"";
						
						//更新协议内容
						String updateSql="update TBL_PROTOCOL t set t.name='"+exportProtocolConfig.getProtocol().getName()+"',"
								+ " t.sort="+exportProtocolConfig.getProtocol().getSort()
								+" where t.id="+existProtocolId;

						service.updateSql(updateSql);
						String updateProtocolItemsClobSql="update TBL_PROTOCOL t set t.items=? where t.id="+existProtocolId;
						List<String> clobCont=new ArrayList<String>();
						clobCont.add(gson.toJson(exportProtocolConfig.getProtocol().getItems()));
						service.getBaseDao().executeSqlUpdateClob(updateProtocolItemsClobSql,clobCont);
												
						//判断采控单元是否存在
						if(importProtocolContent.getAcqUnitList()!=null && importProtocolContent.getAcqUnitList().size()>0 && exportProtocolConfig.getAcqUnitList()!=null ){
							for(int i=0;i<importProtocolContent.getAcqUnitList().size();i++){
								ExportProtocolConfig.AcqUnit acqUnit=null;
								for(int j=0;j<exportProtocolConfig.getAcqUnitList().size();j++){
									if(importProtocolContent.getAcqUnitList().get(i).getId()==exportProtocolConfig.getAcqUnitList().get(j).getId()){
										acqUnit=exportProtocolConfig.getAcqUnitList().get(j);
										break;
									}
								}
								if(acqUnit!=null){
									String addAcqUnitSql="select t.id,t.unit_code from TBL_ACQ_UNIT_CONF t "
											+ " where t.protocol='"+acqUnit.getProtocol()+"' "
											+ " and t.unit_name='"+acqUnit.getUnitName()+"'"
											+ " order by t.id desc";
									List<?> addAcqUnitList=this.findCallSql(addAcqUnitSql);
									
									if(addAcqUnitList.size()>0){//采控单元已存在
										Object[] existAcqUnitObj = (Object[]) addAcqUnitList.get(0);
										int existAcqUnitId=StringManagerUtils.stringToInteger(existAcqUnitObj[0]+"");
										String existAcqUnitCode=existAcqUnitObj[1]+"";
										result_json.append("{\"classes\":1,\"type\":0,\"id\":"+acqUnit.getId()+",\"text\":\""+acqUnit.getUnitName()+"\"},");
										
										//更新采控单元信息
										AcquisitionUnit updateAcquisitionUnit=new AcquisitionUnit();
										updateAcquisitionUnit.setId(existAcqUnitId);
										updateAcquisitionUnit.setUnitCode(existAcqUnitCode);
										updateAcquisitionUnit.setUnitName(acqUnit.getUnitName());
										updateAcquisitionUnit.setRemark(acqUnit.getRemark());
										updateAcquisitionUnit.setProtocol(acqUnit.getProtocol());
										getBaseDao().updateObject(updateAcquisitionUnit);
										
										acqUnit.setUnitCode(existAcqUnitCode);
										
										//判断采控组是否存在
										if(acqUnit.getAcqGroupList()!=null && acqUnit.getAcqGroupList().size()>0){
											for(int j=0;j<acqUnit.getAcqGroupList().size();j++){
												if(StringManagerUtils.existOrNot(importProtocolContent.getAcqUnitList().get(i).getAcqGroupList(), acqUnit.getAcqGroupList().get(j).getId())){
													String addAcqGroupSql="select t.id,t.group_code from TBL_ACQ_GROUP_CONF t,tbl_acq_group2unit_conf t2,tbl_acq_unit_conf t3 "
															+ " where t.id=t2.groupid and t2.unitid=t3.id "
															+ " and t3.protocol='"+acqUnit.getAcqGroupList().get(j).getProtocol()+"' "
															+ " and t3.id="+existAcqUnitId
															+ " and t.group_name='"+acqUnit.getAcqGroupList().get(j).getGroupName()+"'";
													
													List<?> addAcqGroupList=this.findCallSql(addAcqGroupSql);
													if(addAcqGroupList.size()>0){//采控组已存在
														Object[] existAcqGroupObj = (Object[]) addAcqGroupList.get(0);
														int existAcqGroupId=StringManagerUtils.stringToInteger(existAcqGroupObj[0]+"");
														String existAcqGroupCode=existAcqGroupObj[1]+"";
														result_json.append("{\"classes\":2,\"type\":0,\"id\":"+acqUnit.getAcqGroupList().get(j).getId()+",\"text\":\""+acqUnit.getAcqGroupList().get(j).getGroupName()+"\"},");
														//更新采控组
														AcquisitionGroup updateAcquisitionGroup=new AcquisitionGroup();
														updateAcquisitionGroup.setId(existAcqGroupId);
														updateAcquisitionGroup.setGroupCode(existAcqGroupCode);
														updateAcquisitionGroup.setGroupName(acqUnit.getAcqGroupList().get(j).getGroupName());
														updateAcquisitionGroup.setType(acqUnit.getAcqGroupList().get(j).getType());
														updateAcquisitionGroup.setGroupTimingInterval(acqUnit.getAcqGroupList().get(j).getGroupTimingInterval());
														updateAcquisitionGroup.setGroupSavingInterval(acqUnit.getAcqGroupList().get(j).getGroupSavingInterval());
														updateAcquisitionGroup.setRemark(acqUnit.getAcqGroupList().get(j).getRemark());
														updateAcquisitionGroup.setProtocol(acqUnit.getAcqGroupList().get(j).getProtocol());
														getBaseDao().updateObject(updateAcquisitionGroup);
																											
														//更新采控项
														this.deleteCurrentAcquisitionGroupOwnItems(existAcqGroupId+"");
														if(acqUnit.getAcqGroupList().get(j).getAcqItemList()!=null && acqUnit.getAcqGroupList().get(j).getAcqItemList().size()>0){
															for(int k=0;k<acqUnit.getAcqGroupList().get(j).getAcqItemList().size();k++){
																AcquisitionGroupItem acquisitionGroupItem = new AcquisitionGroupItem();
																acquisitionGroupItem.setGroupId(existAcqGroupId);
																acquisitionGroupItem.setItemName(acqUnit.getAcqGroupList().get(j).getAcqItemList().get(k).getItemName());
//																acquisitionGroupItem.setBitIndex(bitIndex>=0?bitIndex:null);
																acquisitionGroupItem.setMatrix(acqUnit.getAcqGroupList().get(j).getAcqItemList().get(k).getMatrix());
																getBaseDao().addObject(acquisitionGroupItem);
															}
														}
														
													}else{//采控组不存在
														//插入采控组
														AcquisitionGroup addAcquisitionGroup=new AcquisitionGroup();
														addAcquisitionGroup.setGroupName(acqUnit.getAcqGroupList().get(j).getGroupName());
														addAcquisitionGroup.setGroupTimingInterval(acqUnit.getAcqGroupList().get(j).getGroupTimingInterval());
														addAcquisitionGroup.setGroupSavingInterval(acqUnit.getAcqGroupList().get(j).getGroupSavingInterval());
														addAcquisitionGroup.setProtocol(acqUnit.getAcqGroupList().get(j).getProtocol());
														addAcquisitionGroup.setType(acqUnit.getAcqGroupList().get(j).getType());
														addAcquisitionGroup.setRemark(acqUnit.getAcqGroupList().get(j).getRemark());
														getBaseDao().addObject(addAcquisitionGroup);
														//添加后，获取自动生成的单元id、code
														addAcqGroupSql="select t.id,t.group_code from TBL_ACQ_GROUP_CONF t "
																+ " where t.group_name='"+addAcquisitionGroup.getGroupName()+"' and t.protocol='"+addAcquisitionGroup.getProtocol()+"'"
																+ " order by t.id desc";
														addAcqGroupList=this.findCallSql(addAcqGroupSql);
														if(addAcqGroupList.size()>0){
															Object[] addAcqGroupObj = (Object[]) addAcqGroupList.get(0);
															int addAcqGroupId=StringManagerUtils.stringToInteger(addAcqGroupObj[0]+"");
															String addAcqGroupCode=addAcqGroupObj[1]+"";
															//采控单元、采控组授权
															AcquisitionUnitGroup acquisitionUnitGroup = new AcquisitionUnitGroup();
															acquisitionUnitGroup.setUnitId(existAcqUnitId);
															acquisitionUnitGroup.setGroupId(addAcqGroupId);
															acquisitionUnitGroup.setMatrix("0,0,0");
															getBaseDao().addObject(acquisitionUnitGroup);
															//插入采控项
															this.deleteCurrentAcquisitionGroupOwnItems(addAcqGroupId+"");
															if(acqUnit.getAcqGroupList().get(j).getAcqItemList()!=null && acqUnit.getAcqGroupList().get(j).getAcqItemList().size()>0){
																for(int k=0;k<acqUnit.getAcqGroupList().get(j).getAcqItemList().size();k++){
																	AcquisitionGroupItem acquisitionGroupItem = new AcquisitionGroupItem();
																	acquisitionGroupItem.setGroupId(addAcqGroupId);
																	acquisitionGroupItem.setItemName(acqUnit.getAcqGroupList().get(j).getAcqItemList().get(k).getItemName());
//																	acquisitionGroupItem.setBitIndex(bitIndex>=0?bitIndex:null);
																	acquisitionGroupItem.setMatrix(acqUnit.getAcqGroupList().get(j).getAcqItemList().get(k).getMatrix());
																	getBaseDao().addObject(acquisitionGroupItem);
																}
															}
														}
													}
												}
											}
										}
										//判断采控实例是否存在
										if(importProtocolContent.getAcqInstanceList()!=null && importProtocolContent.getAcqInstanceList().size()>0){
											for(int j=0;j<importProtocolContent.getAcqInstanceList().size();j++){
												ExportProtocolConfig.AcqInstance acqInstance=null;
												for(int k=0;k<exportProtocolConfig.getAcqInstanceList().size();k++){
													if(StringManagerUtils.existOrNot(importProtocolContent.getAcqInstanceList(),exportProtocolConfig.getAcqInstanceList().get(k).getId())
															&& exportProtocolConfig.getAcqInstanceList().get(k).getUnitId()==acqUnit.getId()){
														acqInstance=exportProtocolConfig.getAcqInstanceList().get(k);
														break;
													}
												}
												if(acqInstance!=null){
													String addAcqInstanceSql="select t.id,t.code from TBL_PROTOCOLINSTANCE t "
															+ " where t.name='"+acqInstance.getName()+"' "
															+ " and t.unitid="+existAcqUnitId
															+ " order by t.id desc";
													List<?> addAcqInstanceList=this.findCallSql(addAcqInstanceSql);
													if(addAcqInstanceList.size()>0){//采控实例已存在
														Object[] existAcqInstanceObj = (Object[]) addAcqInstanceList.get(0);
														int existAcqInstanceId=StringManagerUtils.stringToInteger(existAcqInstanceObj[0]+"");
														String existAcqInstanceCode=existAcqInstanceObj[1]+"";
														result_json.append("{\"classes\":1,\"type\":3,\"id\":"+acqInstance.getId()+",\"text\":\""+acqInstance.getName()+"\"},");
														//更新采控实例
														ProtocolInstance updateProtocolInstance=new ProtocolInstance();
														updateProtocolInstance.setId(existAcqInstanceId);
														updateProtocolInstance.setCode(existAcqInstanceCode);
														updateProtocolInstance.setName(acqInstance.getName());
														updateProtocolInstance.setUnitId(existAcqUnitId);
														updateProtocolInstance.setAcqProtocolType(acqInstance.getAcqProtocolType());
														updateProtocolInstance.setCtrlProtocolType(acqInstance.getCtrlProtocolType());
														
														updateProtocolInstance.setSignInPrefixSuffixHex(acqInstance.getSignInPrefixSuffixHex());
														updateProtocolInstance.setSignInPrefix(acqInstance.getSignInPrefix());
														updateProtocolInstance.setSignInSuffix(acqInstance.getSignInSuffix());
														updateProtocolInstance.setSignInIDHex(acqInstance.getSignInIDHex());
														
														updateProtocolInstance.setHeartbeatPrefixSuffixHex(acqInstance.getHeartbeatPrefixSuffixHex());
														updateProtocolInstance.setHeartbeatPrefix(acqInstance.getHeartbeatPrefix());
														updateProtocolInstance.setHeartbeatSuffix(acqInstance.getHeartbeatSuffix());
														
														updateProtocolInstance.setPacketSendInterval(acqInstance.getPacketSendInterval());
														updateProtocolInstance.setSort(acqInstance.getSort());
														getBaseDao().updateObject(updateProtocolInstance);
													}else{//采控实例不存在
														//插入实例
														ProtocolInstance addProtocolInstance=new ProtocolInstance();
														addProtocolInstance.setName(acqInstance.getName());
														addProtocolInstance.setUnitId(existAcqUnitId);
														addProtocolInstance.setAcqProtocolType(acqInstance.getAcqProtocolType());
														addProtocolInstance.setCtrlProtocolType(acqInstance.getCtrlProtocolType());
														
														addProtocolInstance.setSignInPrefixSuffixHex(acqInstance.getSignInPrefixSuffixHex());
														addProtocolInstance.setSignInPrefix(acqInstance.getSignInPrefix());
														addProtocolInstance.setSignInSuffix(acqInstance.getSignInSuffix());
														addProtocolInstance.setSignInIDHex(acqInstance.getSignInIDHex());
														
														addProtocolInstance.setHeartbeatPrefixSuffixHex(acqInstance.getHeartbeatPrefixSuffixHex());
														addProtocolInstance.setHeartbeatPrefix(acqInstance.getHeartbeatPrefix());
														addProtocolInstance.setHeartbeatSuffix(acqInstance.getHeartbeatSuffix());
														
														addProtocolInstance.setPacketSendInterval(acqInstance.getPacketSendInterval());
														addProtocolInstance.setSort(acqInstance.getSort());
														getBaseDao().addObject(addProtocolInstance);
													}
												}
											}
										}
										
										//判断显示单元
										if(importProtocolContent.getDisplayUnitList()!=null && importProtocolContent.getDisplayUnitList().size()>0){
											for(int j=0;j<importProtocolContent.getDisplayUnitList().size();j++){
												ExportProtocolConfig.DisplayUnit displayUnit=null;
												for(int k=0;k<exportProtocolConfig.getDisplayUnitList().size();k++){
													if(exportProtocolConfig.getDisplayUnitList().get(k).getAcqUnitId()==acqUnit.getId()
															&& exportProtocolConfig.getDisplayUnitList().get(k).getId()==importProtocolContent.getDisplayUnitList().get(j)){
														displayUnit=exportProtocolConfig.getDisplayUnitList().get(k);
														break;
													}
												}
												if(displayUnit!=null){
													String addDisplayUnitSql="select t.id,t.unit_code from TBL_DISPLAY_UNIT_CONF t "
															+ " where t.unit_name='"+displayUnit.getUnitName()+"' "
															+ " and t.acqunitid= "+existAcqUnitId
															+ " order by t.id desc";
													List<?> addDisplayUnitList=this.findCallSql(addDisplayUnitSql);
													if(addDisplayUnitList.size()>0){//显示单元已存在
														Object[] existDisplayUnitObj = (Object[]) addDisplayUnitList.get(0);
														int existDisplayUnitId=StringManagerUtils.stringToInteger(existDisplayUnitObj[0]+"");
														String existDisplayUnitCode=existDisplayUnitObj[1]+"";
														result_json.append("{\"classes\":1,\"type\":1,\"id\":"+displayUnit.getId()+",\"text\":\""+displayUnit.getUnitName()+"\"},");
														//更新显示单元
														DisplayUnit updateDisplayUnit=new DisplayUnit();
														updateDisplayUnit.setId(existDisplayUnitId);
														updateDisplayUnit.setProtocol(displayUnit.getProtocol());
														updateDisplayUnit.setUnitCode(existDisplayUnitCode);
														updateDisplayUnit.setUnitName(displayUnit.getUnitName());
														updateDisplayUnit.setRemark(displayUnit.getRemark());
														updateDisplayUnit.setAcqUnitId(existAcqUnitId);
														getBaseDao().updateObject(updateDisplayUnit);
														//更新显示项
														this.deleteCurrentDisplayUnitOwnItems(existDisplayUnitId+"");
														if(displayUnit.getDisplayItemList()!=null && displayUnit.getDisplayItemList().size()>0){
															for(int k=0;k<displayUnit.getDisplayItemList().size();k++){
																DisplayUnitItem displayUnitItem = new DisplayUnitItem();
																displayUnitItem.setUnitId(existDisplayUnitId);
																displayUnitItem.setItemName(displayUnit.getDisplayItemList().get(k).getItemName());
																displayUnitItem.setItemCode(displayUnit.getDisplayItemList().get(k).getItemCode());
																displayUnitItem.setType(displayUnit.getDisplayItemList().get(k).getType());
																displayUnitItem.setSort(displayUnit.getDisplayItemList().get(k).getSort()>=0?displayUnit.getDisplayItemList().get(k).getSort():null);
																displayUnitItem.setBitIndex(displayUnit.getDisplayItemList().get(k).getBitIndex()>=0?displayUnit.getDisplayItemList().get(k).getBitIndex():null);
																displayUnitItem.setShowLevel(displayUnit.getDisplayItemList().get(k).getShowLevel()>=0?displayUnit.getDisplayItemList().get(k).getShowLevel():null);
																displayUnitItem.setRealtimeCurveConf(displayUnit.getDisplayItemList().get(k).getRealtimeCurveConf());
																displayUnitItem.setHistoryCurveConf(displayUnit.getDisplayItemList().get(k).getHistoryCurveConf());
																displayUnitItem.setMatrix(displayUnit.getDisplayItemList().get(k).getMatrix());
																getBaseDao().addObject(displayUnitItem);
															}
														}
														
														//如果显示单元存在，判断改单元对应的显示实例是否存在
														if(importProtocolContent.getDisplayInstanceList()!=null && importProtocolContent.getDisplayInstanceList().size()>0 ){
															for(int m=0;m<exportProtocolConfig.getDisplayInstanceList().size();m++){
																if(StringManagerUtils.existOrNot(importProtocolContent.getDisplayInstanceList(), exportProtocolConfig.getDisplayInstanceList().get(m).getId())
																		&& exportProtocolConfig.getDisplayInstanceList().get(m).getDisplayUnitId()==displayUnit.getId()){
																	String addDisplayInstanceSql="select t.id,t.code from TBL_PROTOCOLDISPLAYINSTANCE t "
																			+ " where t.name='"+exportProtocolConfig.getDisplayInstanceList().get(m).getName()+"' "
																			+ " and t.displayunitid="+existDisplayUnitId
																			+ " order by t.id desc";
																	List<?> addDisplayInstanceList=this.findCallSql(addDisplayInstanceSql);
																	if(addDisplayInstanceList.size()>0){//显示实例已存在
																		Object[] existDisplayInstanceObj = (Object[]) addDisplayInstanceList.get(0);
																		int existDisplayInstanceId=StringManagerUtils.stringToInteger(existDisplayInstanceObj[0]+"");
																		String existDisplayInstanceCode=existDisplayInstanceObj[1]+"";
																		result_json.append("{\"classes\":1,\"type\":4,\"id\":"+exportProtocolConfig.getDisplayInstanceList().get(m).getId()+",\"text\":\""+exportProtocolConfig.getDisplayInstanceList().get(m).getName()+"\"},");
																		//更新显示实例
																		ProtocolDisplayInstance updateDrotocolDisplayInstance=new ProtocolDisplayInstance();
																		updateDrotocolDisplayInstance.setId(existDisplayInstanceId);
																		updateDrotocolDisplayInstance.setCode(existDisplayInstanceCode);
																		updateDrotocolDisplayInstance.setName(exportProtocolConfig.getDisplayInstanceList().get(m).getName());
																		updateDrotocolDisplayInstance.setDisplayUnitId(existDisplayUnitId);
																		updateDrotocolDisplayInstance.setSort(exportProtocolConfig.getDisplayInstanceList().get(m).getSort()>=0?exportProtocolConfig.getDisplayInstanceList().get(m).getSort():null);
																		getBaseDao().updateObject(updateDrotocolDisplayInstance);
																	}else{//显示实例不存在
																		//插入显示实例
																		ProtocolDisplayInstance addDrotocolDisplayInstance=new ProtocolDisplayInstance();
																		addDrotocolDisplayInstance.setName(exportProtocolConfig.getDisplayInstanceList().get(m).getName());
																		addDrotocolDisplayInstance.setDisplayUnitId(existDisplayUnitId);
																		addDrotocolDisplayInstance.setSort(exportProtocolConfig.getDisplayInstanceList().get(m).getSort()>=0?exportProtocolConfig.getDisplayInstanceList().get(m).getSort():null);
																		getBaseDao().updateObject(addDrotocolDisplayInstance);
																	}
																}
															}
														}
													}else{//显示单元不存在
														//添加显示单元
														DisplayUnit addDisplayUnit=new DisplayUnit();
														addDisplayUnit.setProtocol(displayUnit.getProtocol());
														addDisplayUnit.setUnitName(displayUnit.getUnitName());
														addDisplayUnit.setRemark(displayUnit.getRemark());
														addDisplayUnit.setAcqUnitId(existAcqUnitId);
														getBaseDao().addObject(addDisplayUnit);
														//添加后，获取自动生成的单元id、code
														addDisplayUnitSql="select t.id,t.unit_code from TBL_DISPLAY_UNIT_CONF t "
																+ " where t.unit_name='"+addDisplayUnit.getUnitName()+"' and t.acqunitid="+addDisplayUnit.getAcqUnitId()
																+ " order by t.id desc";
														addDisplayUnitList=this.findCallSql(addDisplayUnitSql);
														if(addDisplayUnitList.size()>0){//采控单元添加成功
															Object[] addDisplayUnitObj = (Object[]) addDisplayUnitList.get(0);
															int addDisplayUnitId=StringManagerUtils.stringToInteger(addDisplayUnitObj[0]+"");
															String addDisplayUnitCode=addDisplayUnitObj[1]+"";
															//更新显示项
															this.deleteCurrentDisplayUnitOwnItems(addDisplayUnitId+"");
															if(displayUnit.getDisplayItemList()!=null && displayUnit.getDisplayItemList().size()>0){
																for(int k=0;k<displayUnit.getDisplayItemList().size();k++){
																	DisplayUnitItem displayUnitItem = new DisplayUnitItem();
																	displayUnitItem.setUnitId(addDisplayUnitId);
																	displayUnitItem.setItemName(displayUnit.getDisplayItemList().get(k).getItemName());
																	displayUnitItem.setItemCode(displayUnit.getDisplayItemList().get(k).getItemCode());
																	displayUnitItem.setType(displayUnit.getDisplayItemList().get(k).getType());
																	displayUnitItem.setSort(displayUnit.getDisplayItemList().get(k).getSort()>=0?displayUnit.getDisplayItemList().get(k).getSort():null);
																	displayUnitItem.setBitIndex(displayUnit.getDisplayItemList().get(k).getBitIndex()>=0?displayUnit.getDisplayItemList().get(k).getBitIndex():null);
																	displayUnitItem.setShowLevel(displayUnit.getDisplayItemList().get(k).getShowLevel()>=0?displayUnit.getDisplayItemList().get(k).getShowLevel():null);
																	displayUnitItem.setRealtimeCurveConf(displayUnit.getDisplayItemList().get(k).getRealtimeCurveConf());
																	displayUnitItem.setHistoryCurveConf(displayUnit.getDisplayItemList().get(k).getHistoryCurveConf());
																	displayUnitItem.setMatrix(displayUnit.getDisplayItemList().get(k).getMatrix());
																	getBaseDao().addObject(displayUnitItem);
																}
															}
															//添加显示实例
															if(importProtocolContent.getDisplayInstanceList()!=null && importProtocolContent.getDisplayInstanceList().size()>0 ){
																for(int m=0;m<exportProtocolConfig.getDisplayInstanceList().size();m++){
																	if(StringManagerUtils.existOrNot(importProtocolContent.getDisplayInstanceList(), exportProtocolConfig.getDisplayInstanceList().get(m).getId())
																			&& exportProtocolConfig.getDisplayInstanceList().get(m).getDisplayUnitId()==displayUnit.getId()){
																		//插入显示实例
																		ProtocolDisplayInstance addDrotocolDisplayInstance=new ProtocolDisplayInstance();
																		addDrotocolDisplayInstance.setName(exportProtocolConfig.getDisplayInstanceList().get(m).getName());
																		addDrotocolDisplayInstance.setDisplayUnitId(addDisplayUnitId);
																		addDrotocolDisplayInstance.setSort(exportProtocolConfig.getDisplayInstanceList().get(m).getSort()>=0?exportProtocolConfig.getDisplayInstanceList().get(m).getSort():null);
																		getBaseDao().updateObject(addDrotocolDisplayInstance);
																	}
																}
															}
														}
													}
												}
											}
										}
									}else{//采控单元不存在
										//添加采控单元信息
										AcquisitionUnit addAcquisitionUnit=new AcquisitionUnit();
										addAcquisitionUnit.setUnitName(acqUnit.getUnitName());
										addAcquisitionUnit.setRemark(acqUnit.getRemark());
										addAcquisitionUnit.setProtocol(acqUnit.getProtocol());
										getBaseDao().addObject(addAcquisitionUnit);
										//添加后获取自动生成的id和code
										addAcqUnitSql="select t.id,t.unit_code from TBL_ACQ_UNIT_CONF t "
												+ " where t.protocol='"+addAcquisitionUnit.getProtocol()+"' "
												+ " and t.unit_name='"+addAcquisitionUnit.getUnitName()+"'"
												+ " order by t.id desc";
										addAcqUnitList=this.findCallSql(addAcqUnitSql);
										if(addAcqUnitList.size()>0){
											Object[] addAcqUnitObj = (Object[]) addAcqUnitList.get(0);
											int addAcqUnitId=StringManagerUtils.stringToInteger(addAcqUnitObj[0]+"");
											String addAcqUnitCode=addAcqUnitObj[1]+"";
											acqUnit.setUnitCode(addAcqUnitCode);
											//添加采控单元下的采控组
											//判断采控组是否存在
											if(acqUnit.getAcqGroupList()!=null && acqUnit.getAcqGroupList().size()>0){
												for(int j=0;j<acqUnit.getAcqGroupList().size();j++){
													if(StringManagerUtils.existOrNot(importProtocolContent.getAcqUnitList().get(i).getAcqGroupList(), acqUnit.getAcqGroupList().get(j).getId())){
														//插入采控组
														AcquisitionGroup addAcquisitionGroup=new AcquisitionGroup();
														addAcquisitionGroup.setGroupName(acqUnit.getAcqGroupList().get(j).getGroupName());
														addAcquisitionGroup.setGroupTimingInterval(acqUnit.getAcqGroupList().get(j).getGroupTimingInterval());
														addAcquisitionGroup.setGroupSavingInterval(acqUnit.getAcqGroupList().get(j).getGroupSavingInterval());
														addAcquisitionGroup.setProtocol(acqUnit.getAcqGroupList().get(j).getProtocol());
														addAcquisitionGroup.setType(acqUnit.getAcqGroupList().get(j).getType());
														addAcquisitionGroup.setRemark(acqUnit.getAcqGroupList().get(j).getRemark());
														getBaseDao().addObject(addAcquisitionGroup);
														//添加后，获取自动生成的单元id、code
														String addAcqGroupSql="select t.id,t.group_code from TBL_ACQ_GROUP_CONF t "
																+ " where t.group_name='"+addAcquisitionGroup.getGroupName()+"' and t.protocol='"+addAcquisitionGroup.getProtocol()+"'"
																+ " order by t.id desc";
														List<?> addAcqGroupList=this.findCallSql(addAcqGroupSql);
														if(addAcqGroupList.size()>0){
															Object[] addAcqGroupObj = (Object[]) addAcqGroupList.get(0);
															int addAcqGroupId=StringManagerUtils.stringToInteger(addAcqGroupObj[0]+"");
															String addAcqGroupCode=addAcqGroupObj[1]+"";
															//采控单元、采控组授权
															AcquisitionUnitGroup acquisitionUnitGroup = new AcquisitionUnitGroup();
															acquisitionUnitGroup.setUnitId(addAcqUnitId);
															acquisitionUnitGroup.setGroupId(addAcqGroupId);
															acquisitionUnitGroup.setMatrix("0,0,0");
															getBaseDao().addObject(acquisitionUnitGroup);
															//插入采控项
															if(acqUnit.getAcqGroupList().get(j).getAcqItemList()!=null && acqUnit.getAcqGroupList().get(j).getAcqItemList().size()>0){
																this.deleteCurrentAcquisitionGroupOwnItems(addAcqGroupId+"");
																for(int k=0;k<acqUnit.getAcqGroupList().get(j).getAcqItemList().size();k++){
																	AcquisitionGroupItem acquisitionGroupItem = new AcquisitionGroupItem();
																	acquisitionGroupItem.setGroupId(addAcqGroupId);
																	acquisitionGroupItem.setItemName(acqUnit.getAcqGroupList().get(j).getAcqItemList().get(k).getItemName());
//																	acquisitionGroupItem.setBitIndex(bitIndex>=0?bitIndex:null);
																	acquisitionGroupItem.setMatrix(acqUnit.getAcqGroupList().get(j).getAcqItemList().get(k).getMatrix());
																	getBaseDao().addObject(acquisitionGroupItem);
																}
															}
														}
													}
												}
											}
											//添加采控单元相关采控实例
											if(importProtocolContent.getAcqInstanceList()!=null && importProtocolContent.getAcqInstanceList().size()>0){
												for(int j=0;i<importProtocolContent.getAcqInstanceList().size();j++){
													ExportProtocolConfig.AcqInstance acqInstance=null;
													for(int k=0;k<exportProtocolConfig.getAcqInstanceList().size();k++){
														if(StringManagerUtils.existOrNot(importProtocolContent.getAcqInstanceList(),exportProtocolConfig.getAcqInstanceList().get(k).getId())
																&& exportProtocolConfig.getAcqInstanceList().get(k).getUnitId()==acqUnit.getId()){
															acqInstance=exportProtocolConfig.getAcqInstanceList().get(k);
															break;
														}
													}
													if(acqInstance!=null){
														//插入实例
														ProtocolInstance addProtocolInstance=new ProtocolInstance();
														addProtocolInstance.setName(acqInstance.getName());
														addProtocolInstance.setUnitId(addAcqUnitId);
														addProtocolInstance.setAcqProtocolType(acqInstance.getAcqProtocolType());
														addProtocolInstance.setCtrlProtocolType(acqInstance.getCtrlProtocolType());
														
														addProtocolInstance.setSignInPrefixSuffixHex(acqInstance.getSignInPrefixSuffixHex());
														addProtocolInstance.setSignInPrefix(acqInstance.getSignInPrefix());
														addProtocolInstance.setSignInSuffix(acqInstance.getSignInSuffix());
														addProtocolInstance.setSignInIDHex(acqInstance.getSignInIDHex());
														
														addProtocolInstance.setHeartbeatPrefixSuffixHex(acqInstance.getHeartbeatPrefixSuffixHex());
														addProtocolInstance.setHeartbeatPrefix(acqInstance.getHeartbeatPrefix());
														addProtocolInstance.setHeartbeatSuffix(acqInstance.getHeartbeatSuffix());
														
														addProtocolInstance.setPacketSendInterval(acqInstance.getPacketSendInterval());
														addProtocolInstance.setSort(acqInstance.getSort());
														getBaseDao().addObject(addProtocolInstance);
													
													}
												}
											}
											//添加显示单元
											if(importProtocolContent.getDisplayUnitList()!=null && importProtocolContent.getDisplayUnitList().size()>0 && exportProtocolConfig.getDisplayUnitList()!=null){
												for(int j=0;j<exportProtocolConfig.getDisplayUnitList().size();j++){
													if(StringManagerUtils.existOrNot(importProtocolContent.getDisplayUnitList(), exportProtocolConfig.getDisplayUnitList().get(j).getId())
															&&exportProtocolConfig.getDisplayUnitList().get(j).getAcqUnitId()==acqUnit.getId()){
														ExportProtocolConfig.DisplayUnit displayUnit=exportProtocolConfig.getDisplayUnitList().get(j);
														//添加显示单元
														DisplayUnit addDisplayUnit=new DisplayUnit();
														addDisplayUnit.setProtocol(displayUnit.getProtocol());
														addDisplayUnit.setUnitName(displayUnit.getUnitName());
														addDisplayUnit.setRemark(displayUnit.getRemark());
														addDisplayUnit.setAcqUnitId(addAcqUnitId);
														getBaseDao().addObject(addDisplayUnit);
														//添加后，获取自动生成的单元id、code
														String addDisplayUnitSql="select t.id,t.unit_code from TBL_DISPLAY_UNIT_CONF t "
																+ " where t.unit_name='"+addDisplayUnit.getUnitName()+"' and t.acqunitid="+addDisplayUnit.getAcqUnitId()
																+ " order by t.id desc";
														List<?> addDisplayUnitList=this.findCallSql(addDisplayUnitSql);
														if(addDisplayUnitList.size()>0){//采控单元添加成功
															Object[] addDisplayUnitObj = (Object[]) addDisplayUnitList.get(0);
															int addDisplayUnitId=StringManagerUtils.stringToInteger(addDisplayUnitObj[0]+"");
															String addDisplayUnitCode=addDisplayUnitObj[1]+"";
															//更新显示项
															this.deleteCurrentDisplayUnitOwnItems(addDisplayUnitId+"");
															if(displayUnit.getDisplayItemList()!=null && displayUnit.getDisplayItemList().size()>0){
																for(int k=0;k<displayUnit.getDisplayItemList().size();k++){
																	DisplayUnitItem displayUnitItem = new DisplayUnitItem();
																	displayUnitItem.setUnitId(addDisplayUnitId);
																	displayUnitItem.setItemName(displayUnit.getDisplayItemList().get(k).getItemName());
																	displayUnitItem.setItemCode(displayUnit.getDisplayItemList().get(k).getItemCode());
																	displayUnitItem.setType(displayUnit.getDisplayItemList().get(k).getType());
																	displayUnitItem.setSort(displayUnit.getDisplayItemList().get(k).getSort()>=0?displayUnit.getDisplayItemList().get(k).getSort():null);
																	displayUnitItem.setBitIndex(displayUnit.getDisplayItemList().get(k).getBitIndex()>=0?displayUnit.getDisplayItemList().get(k).getBitIndex():null);
																	displayUnitItem.setShowLevel(displayUnit.getDisplayItemList().get(k).getShowLevel()>=0?displayUnit.getDisplayItemList().get(k).getShowLevel():null);
																	displayUnitItem.setRealtimeCurveConf(displayUnit.getDisplayItemList().get(k).getRealtimeCurveConf());
																	displayUnitItem.setHistoryCurveConf(displayUnit.getDisplayItemList().get(k).getHistoryCurveConf());
																	displayUnitItem.setMatrix(displayUnit.getDisplayItemList().get(k).getMatrix());
																	getBaseDao().addObject(displayUnitItem);
																}
															}
															//添加显示实例
															if(importProtocolContent.getDisplayInstanceList()!=null && importProtocolContent.getDisplayInstanceList().size()>0 && exportProtocolConfig.getDisplayInstanceList()!=null){
																for(int m=0;m<exportProtocolConfig.getDisplayInstanceList().size();m++){
																	if(StringManagerUtils.existOrNot(importProtocolContent.getDisplayInstanceList(), exportProtocolConfig.getDisplayInstanceList().get(m).getId())
																			&& exportProtocolConfig.getDisplayInstanceList().get(m).getDisplayUnitId()==displayUnit.getId()){
																		//插入显示实例
																		ProtocolDisplayInstance addDrotocolDisplayInstance=new ProtocolDisplayInstance();
																		addDrotocolDisplayInstance.setName(exportProtocolConfig.getDisplayInstanceList().get(m).getName());
																		addDrotocolDisplayInstance.setDisplayUnitId(addDisplayUnitId);
																		addDrotocolDisplayInstance.setSort(exportProtocolConfig.getDisplayInstanceList().get(m).getSort()>=0?exportProtocolConfig.getDisplayInstanceList().get(m).getSort():null);
																		getBaseDao().updateObject(addDrotocolDisplayInstance);
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
						//判断报警单元
						if(importProtocolContent.getAlarmUnitList()!=null && importProtocolContent.getAlarmUnitList().size()>0 && exportProtocolConfig.getAlarmUnitList()!=null){
							for(int i=0;i<importProtocolContent.getAlarmUnitList().size();i++){
								ExportProtocolConfig.AlarmUnit alarmUnit=null;
								for(int j=0;j<exportProtocolConfig.getAlarmUnitList().size();j++){
									if(importProtocolContent.getAlarmUnitList().get(i)==exportProtocolConfig.getAlarmUnitList().get(j).getId()  ){
										alarmUnit=exportProtocolConfig.getAlarmUnitList().get(j);
										break;
									}
								}
								if(alarmUnit!=null){
									String addAlarmUnitSql="select t.id,t.unit_code from TBL_ALARM_UNIT_CONF t "
											+ " where t.unit_name='"+alarmUnit.getUnitName()+"' "
											+ " and t.protocol='"+alarmUnit.getProtocol()+"' "
											+ " order by t.id desc";
									List<?> addAlarmUnitList=this.findCallSql(addAlarmUnitSql);
									if(addAlarmUnitList.size()>0){//报警单元已存在
										Object[] addAlarmUnitObj = (Object[]) addAlarmUnitList.get(0);
										int existAlarmUnitId=StringManagerUtils.stringToInteger(addAlarmUnitObj[0]+"");
										String existAlarmUnitCode=addAlarmUnitObj[1]+"";
										result_json.append("{\"classes\":1,\"type\":2,\"id\":"+alarmUnit.getId()+",\"text\":\""+alarmUnit.getUnitName()+"\"},");
										//更新报警单元
										AlarmUnit updateAlarmUnit=new AlarmUnit();
										updateAlarmUnit.setId(existAlarmUnitId);
										updateAlarmUnit.setUnitCode(existAlarmUnitCode);
										updateAlarmUnit.setUnitName(alarmUnit.getUnitName());
										updateAlarmUnit.setProtocol(alarmUnit.getProtocol());
										updateAlarmUnit.setRemark(alarmUnit.getRemark());
										getBaseDao().updateObject(updateAlarmUnit);
										//更新报警项
										this.deleteCurrentAlarmUnitOwnItems(existAlarmUnitId+"");
										if(alarmUnit.getAlarmItemList()!=null && alarmUnit.getAlarmItemList().size()>0 ){
											for(int j=0;j<alarmUnit.getAlarmItemList().size();j++){
												AlarmUnitItem alarmUnitItem=new AlarmUnitItem();
												alarmUnitItem.setId(alarmUnit.getAlarmItemList().get(j).getId());
												alarmUnitItem.setUnitId(existAlarmUnitId);
												alarmUnitItem.setItemId(alarmUnit.getAlarmItemList().get(j).getItemId());
												alarmUnitItem.setItemName(alarmUnit.getAlarmItemList().get(j).getItemName());
												alarmUnitItem.setItemCode(alarmUnit.getAlarmItemList().get(j).getItemCode());
												alarmUnitItem.setItemAddr(alarmUnit.getAlarmItemList().get(j).getItemAddr());
												alarmUnitItem.setBitIndex(alarmUnit.getAlarmItemList().get(j).getBitIndex());
												alarmUnitItem.setValue(alarmUnit.getAlarmItemList().get(j).getValue());
												alarmUnitItem.setUpperLimit(alarmUnit.getAlarmItemList().get(j).getUpperLimit());
												alarmUnitItem.setLowerLimit(alarmUnit.getAlarmItemList().get(j).getLowerLimit());
												alarmUnitItem.setHystersis(alarmUnit.getAlarmItemList().get(j).getHystersis());
												alarmUnitItem.setDelay(alarmUnit.getAlarmItemList().get(j).getDelay());
												alarmUnitItem.setAlarmLevel(alarmUnit.getAlarmItemList().get(j).getAlarmLevel());
												alarmUnitItem.setAlarmSign(alarmUnit.getAlarmItemList().get(j).getAlarmSign());
												alarmUnitItem.setType(alarmUnit.getAlarmItemList().get(j).getType());
												alarmUnitItem.setIsSendMessage(alarmUnit.getAlarmItemList().get(j).getIsSendMessage());
												alarmUnitItem.setIsSendMail(alarmUnit.getAlarmItemList().get(j).getIsSendMail());
												getBaseDao().addObject(alarmUnitItem);
											}
										}
										
										//如果报警单元存在，判断对应的报警实例是否存在
										if(importProtocolContent.getAlarmInstanceList()!=null && importProtocolContent.getAlarmInstanceList().size()>0 && exportProtocolConfig.getAlarmInstanceList()!=null){
											for(int m=0;m<exportProtocolConfig.getAlarmInstanceList().size();m++){
												if(StringManagerUtils.existOrNot(importProtocolContent.getAlarmInstanceList(), exportProtocolConfig.getAlarmInstanceList().get(m).getId())
														&& exportProtocolConfig.getAlarmInstanceList().get(m).getAlarmUnitId()==alarmUnit.getId()){
													String addAlarmInstanceSql="select t.id,t.code "
															+ " from TBL_PROTOCOLALARMINSTANCE t "
															+ " where t.name='"+exportProtocolConfig.getAlarmInstanceList().get(m).getName()+"' "
															+ " and t.alarmunitid="+existAlarmUnitId
															+ " order by t.id desc";
													List<?> addAlarmInstanceList=this.findCallSql(addAlarmInstanceSql);
													if(addAlarmInstanceList.size()>0){//报警实例已存在
														Object[] addAlarmInstanceObj = (Object[]) addAlarmInstanceList.get(0);
														int existAlarmInstanceId=StringManagerUtils.stringToInteger(addAlarmInstanceObj[0]+"");
														String existAlarmInstanceCode=addAlarmInstanceObj[1]+"";
														result_json.append("{\"classes\":1,\"type\":5,\"id\":"+exportProtocolConfig.getAlarmInstanceList().get(m).getId()+",\"text\":\""+exportProtocolConfig.getAlarmInstanceList().get(m).getName()+"\"},");
														//更新报警实例
														ProtocolAlarmInstance protocolAlarmInstance=new ProtocolAlarmInstance();
														protocolAlarmInstance.setId(existAlarmInstanceId);
														protocolAlarmInstance.setCode(existAlarmInstanceCode);
														protocolAlarmInstance.setName(exportProtocolConfig.getAlarmInstanceList().get(m).getName());
														protocolAlarmInstance.setAlarmUnitId(existAlarmUnitId);
														protocolAlarmInstance.setSort(exportProtocolConfig.getAlarmInstanceList().get(m).getSort()>=0?exportProtocolConfig.getAlarmInstanceList().get(m).getSort():null);
														getBaseDao().updateObject(protocolAlarmInstance);
													}else{//报警实例不存在
														//添加报警实例
														ProtocolAlarmInstance protocolAlarmInstance=new ProtocolAlarmInstance();
														protocolAlarmInstance.setName(exportProtocolConfig.getAlarmInstanceList().get(m).getName());
														protocolAlarmInstance.setAlarmUnitId(existAlarmUnitId);
														protocolAlarmInstance.setSort(exportProtocolConfig.getAlarmInstanceList().get(m).getSort()>=0?exportProtocolConfig.getAlarmInstanceList().get(m).getSort():null);
														getBaseDao().addObject(protocolAlarmInstance);
													}
												}
											}
										}
									}else{//报警单元不存在
										//添加报警单元
										AlarmUnit updateAlarmUnit=new AlarmUnit();
										updateAlarmUnit.setUnitName(alarmUnit.getUnitName());
										updateAlarmUnit.setProtocol(alarmUnit.getProtocol());
										updateAlarmUnit.setRemark(alarmUnit.getRemark());
										getBaseDao().addObject(updateAlarmUnit);
										//添加后，获取自动生成的id和code
										addAlarmUnitSql="select t.id,t.unit_code from TBL_ALARM_UNIT_CONF t "
												+ " where t.unit_name='"+alarmUnit.getUnitName()+"' "
												+ " and t.protocol='"+alarmUnit.getProtocol()+"' "
												+ " order by t.id desc";
										addAlarmUnitList=this.findCallSql(addAlarmUnitSql);
										if(addAlarmUnitList.size()>0){
											Object[] addAlarmUnitObj = (Object[]) addAlarmUnitList.get(0);
											int addAlarmUnitId=StringManagerUtils.stringToInteger(addAlarmUnitObj[0]+"");
											String addAlarmUnitCode=addAlarmUnitObj[1]+"";
											//添加报警项
											this.deleteCurrentAlarmUnitOwnItems(addAlarmUnitId+"");
											if(alarmUnit.getAlarmItemList()!=null && alarmUnit.getAlarmItemList().size()>0 ){
												for(int j=0;j<alarmUnit.getAlarmItemList().size();j++){
													AlarmUnitItem alarmUnitItem=new AlarmUnitItem();
													alarmUnitItem.setId(alarmUnit.getAlarmItemList().get(j).getId());
													alarmUnitItem.setUnitId(addAlarmUnitId);
													alarmUnitItem.setItemId(alarmUnit.getAlarmItemList().get(j).getItemId());
													alarmUnitItem.setItemName(alarmUnit.getAlarmItemList().get(j).getItemName());
													alarmUnitItem.setItemCode(alarmUnit.getAlarmItemList().get(j).getItemCode());
													alarmUnitItem.setItemAddr(alarmUnit.getAlarmItemList().get(j).getItemAddr());
													alarmUnitItem.setBitIndex(alarmUnit.getAlarmItemList().get(j).getBitIndex());
													alarmUnitItem.setValue(alarmUnit.getAlarmItemList().get(j).getValue());
													alarmUnitItem.setUpperLimit(alarmUnit.getAlarmItemList().get(j).getUpperLimit());
													alarmUnitItem.setLowerLimit(alarmUnit.getAlarmItemList().get(j).getLowerLimit());
													alarmUnitItem.setHystersis(alarmUnit.getAlarmItemList().get(j).getHystersis());
													alarmUnitItem.setDelay(alarmUnit.getAlarmItemList().get(j).getDelay());
													alarmUnitItem.setAlarmLevel(alarmUnit.getAlarmItemList().get(j).getAlarmLevel());
													alarmUnitItem.setAlarmSign(alarmUnit.getAlarmItemList().get(j).getAlarmSign());
													alarmUnitItem.setType(alarmUnit.getAlarmItemList().get(j).getType());
													alarmUnitItem.setIsSendMessage(alarmUnit.getAlarmItemList().get(j).getIsSendMessage());
													alarmUnitItem.setIsSendMail(alarmUnit.getAlarmItemList().get(j).getIsSendMail());
													getBaseDao().addObject(alarmUnitItem);
												}
											}
											
											//添加报警实例
											if(importProtocolContent.getAlarmInstanceList()!=null && importProtocolContent.getAlarmInstanceList().size()>0 && exportProtocolConfig.getAlarmInstanceList()!=null){
												for(int m=0;m<exportProtocolConfig.getAlarmInstanceList().size();m++){
													if(StringManagerUtils.existOrNot(importProtocolContent.getAlarmInstanceList(), exportProtocolConfig.getAlarmInstanceList().get(m).getId())
															&& exportProtocolConfig.getAlarmInstanceList().get(m).getAlarmUnitId()==alarmUnit.getId()){
														//添加报警实例
														ProtocolAlarmInstance protocolAlarmInstance=new ProtocolAlarmInstance();
														protocolAlarmInstance.setName(exportProtocolConfig.getAlarmInstanceList().get(m).getName());
														protocolAlarmInstance.setAlarmUnitId(addAlarmUnitId);
														protocolAlarmInstance.setSort(exportProtocolConfig.getAlarmInstanceList().get(m).getSort()>=0?exportProtocolConfig.getAlarmInstanceList().get(m).getSort():null);
														getBaseDao().addObject(protocolAlarmInstance);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//更新存储字段表
			if(exportProtocolConfig.getProtocol().getItems()!=null && exportProtocolConfig.getProtocol().getItems().size()>0
					&& exportProtocolConfig.getDataMappingList()!=null && exportProtocolConfig.getDataMappingList().size()>0){
				for(int i=0;i<exportProtocolConfig.getDataMappingList().size();i++){
					String mappingItemSql="select t.id from TBL_DATAMAPPING t "
							+ " where t.name ='"+exportProtocolConfig.getDataMappingList().get(i).getName()+"' "
							+ " and t.mappingcolumn='"+exportProtocolConfig.getDataMappingList().get(i).getMappingColumn()+"' ";
					List<?> mappingItemList=this.findCallSql(mappingItemSql);
					if(mappingItemList.size()==0){
						String addSql="insert into tbl_datamapping(name,mappingcolumn,calcolumn,mappingmode) "
								+ " values("
								+ "'"+exportProtocolConfig.getDataMappingList().get(i).getName()+"',"
								+ "'"+exportProtocolConfig.getDataMappingList().get(i).getMappingColumn()+"',"
								+ "'"+exportProtocolConfig.getDataMappingList().get(i).getCalColumn()+"',"
								+1+")";
						this.getBaseDao().updateOrDeleteBySql(addSql);
					}
				}
			}
			//更新运行状态
			if(exportProtocolConfig.getProtocolRunStatusConfigList()!=null && exportProtocolConfig.getProtocolRunStatusConfigList().size()>0){
				String existProtocolSql="select t.id,t.code from TBL_PROTOCOL t where t.name='"+exportProtocolConfig.getProtocol().getName()+"'";
				List<?> existProtocolList=this.findCallSql(existProtocolSql);
				if(existProtocolList.size()>0){
					Object[] existProtocolObj = (Object[]) existProtocolList.get(0);
					int existProtocolId=StringManagerUtils.stringToInteger(existProtocolObj[0]+"");
					String existProtocolCode=existProtocolObj[1]+"";
					
					String runStatusConfigSql="select t.id "
							+ " from tbl_runstatusconfig t,tbl_protocol t2 "
							+ " where t.protocol='"+existProtocolCode+"' ";
					List<?> runStatusConfigList=this.findCallSql(runStatusConfigSql);
					if(runStatusConfigList.size()>0){
						String existRunStatusConfigId=runStatusConfigList.get(0).toString();
						String updateRunStatusConfigSql="update tbl_runstatusconfig t "
								+ "set t.runvalue='"+StringUtils.join(exportProtocolConfig.getProtocolRunStatusConfigList().get(0).getRunValue(), ",")+"',"
								+ " t.stopvalue='"+StringUtils.join(exportProtocolConfig.getProtocolRunStatusConfigList().get(0).getStopValue(), ",")+"' "
								+ " where t.id= "+existRunStatusConfigId;
						this.getBaseDao().updateOrDeleteBySql(updateRunStatusConfigSql);
					}else{
						String addRunStatusConfigSql="insert into tbl_runstatusconfig(protocol,itemname,itemmappingcolumn,runvalue,stopvalue) "
								+ "values ('"+existProtocolCode+"','"+exportProtocolConfig.getProtocolRunStatusConfigList().get(0).getItemName()+"',"
								+ "'"+exportProtocolConfig.getProtocolRunStatusConfigList().get(0).getItemMappingColumn()+"',"
								+ "'"+StringUtils.join(exportProtocolConfig.getProtocolRunStatusConfigList().get(0).getRunValue(), "',")+"',"
								+ "'"+StringUtils.join(exportProtocolConfig.getProtocolRunStatusConfigList().get(0).getStopValue(), "',")
								+")";
						this.getBaseDao().updateOrDeleteBySql(addRunStatusConfigSql);
					}
				}
			}
			
			//更新内存及初始化
			MemoryDataManagerTask.loadAcqInstanceOwnItemByProtocolName(exportProtocolConfig.getProtocol().getName(),"update");
			MemoryDataManagerTask.loadAlarmInstanceOwnItemByProtocolName(exportProtocolConfig.getProtocol().getName(),"update");
			MemoryDataManagerTask.loadDisplayInstanceOwnItemByProtocolName(exportProtocolConfig.getProtocol().getName(),"update");
			
			EquipmentDriverServerTask.initProtocolConfig(exportProtocolConfig.getProtocol().getName(),"update");
			EquipmentDriverServerTask.initInstanceConfigByProtocolName(exportProtocolConfig.getProtocol().getName(),"update");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	
	public String importProtocolCheck(String data){
		StringBuffer result_json = new StringBuffer();
		result_json.append("{\"success\":true,\"overlayList\":[");
		StringBuffer  errorDataBuff=new StringBuffer ();
		errorDataBuff.append("[");
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		
		type = new TypeToken<ImportProtocolContent>() {}.getType();
		ImportProtocolContent importProtocolContent=gson.fromJson(data, type);
		
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null){
			boolean protocolExist=this.judgeProtocolExistOrNot(exportProtocolConfig.getProtocol().getName());
			if(protocolExist){//如果协议已存在
				result_json.append("{\"classes\":0,\"typeName\":\"协议\",\"type\":0,\"id\":"+exportProtocolConfig.getProtocol().getId()+",\"text\":\""+exportProtocolConfig.getProtocol().getName()+"\"},");
				
				String existProtocolSql="select t.id,t.code from TBL_PROTOCOL t where t.name='"+exportProtocolConfig.getProtocol().getName()+"'";
				
				List<Integer> acqUnitIdList=new ArrayList<>();
				if(importProtocolContent.getAcqUnitList()!=null){
					for(int i=0;i<importProtocolContent.getAcqUnitList().size();i++){
						acqUnitIdList.add(importProtocolContent.getAcqUnitList().get(i).getId());
					}
				}
				//数据校验
				//校验采控实例
				if(importProtocolContent.getAcqInstanceList()!=null && exportProtocolConfig.getAcqUnitList()!=null && exportProtocolConfig.getAcqInstanceList()!=null){
					for(int i=0;i<importProtocolContent.getAcqInstanceList().size();i++){
						for(int j=0;j<exportProtocolConfig.getAcqInstanceList().size();j++){
							if(importProtocolContent.getAcqInstanceList().get(i)==exportProtocolConfig.getAcqInstanceList().get(j).getId()){
								if(!StringManagerUtils.existOrNot(acqUnitIdList, exportProtocolConfig.getAcqInstanceList().get(j).getUnitId())){
									errorDataBuff.append("{\"classes\":1,\"typeName\":\"采控实例\",\"type\":3,\"id\":"+exportProtocolConfig.getAcqInstanceList().get(j).getId()+",\"text\":\""+exportProtocolConfig.getAcqInstanceList().get(j).getName()+"\",\"errorInfo\":\"未选择采控实例对应的采控单元\"},");
								}
							}
						}
					}
				}
				//校验显示单元
				if(importProtocolContent.getDisplayUnitList()!=null && exportProtocolConfig.getAcqUnitList()!=null && exportProtocolConfig.getDisplayUnitList()!=null){
					for(int i=0;i<importProtocolContent.getDisplayUnitList().size();i++){
						for(int j=0;j<exportProtocolConfig.getDisplayUnitList().size();j++){
							if(importProtocolContent.getDisplayUnitList().get(i)==exportProtocolConfig.getDisplayUnitList().get(j).getId()){
								if(!StringManagerUtils.existOrNot(acqUnitIdList, exportProtocolConfig.getDisplayUnitList().get(j).getAcqUnitId())){
									errorDataBuff.append("{\"classes\":1,\"typeName\":\"显示单元\",\"type\":1,\"id\":"+exportProtocolConfig.getDisplayUnitList().get(j).getId()+",\"text\":\""+exportProtocolConfig.getDisplayUnitList().get(j).getUnitName()+"\",\"errorInfo\":\"未选择显示单元对应的采控单元\"},");
								}
							}
						}
					}
				}
				//检验显示实例
				if(importProtocolContent.getDisplayInstanceList()!=null && exportProtocolConfig.getDisplayInstanceList()!=null){
					for(int i=0;i<importProtocolContent.getDisplayInstanceList().size();i++){
						for(int j=0;j<exportProtocolConfig.getDisplayInstanceList().size();j++){
							if(importProtocolContent.getDisplayInstanceList().get(i)==exportProtocolConfig.getDisplayInstanceList().get(j).getId()){
								if(!StringManagerUtils.existOrNot(importProtocolContent.getDisplayUnitList(), exportProtocolConfig.getDisplayInstanceList().get(j).getDisplayUnitId())){
									errorDataBuff.append("{\"classes\":1,\"typeName\":\"显示实例\",\"type\":4,\"id\":"+exportProtocolConfig.getDisplayInstanceList().get(j).getId()+",\"text\":\""+exportProtocolConfig.getDisplayInstanceList().get(j).getName()+"\",\"errorInfo\":\"未选择显示实例对应的显示单元\"},");
								}
							}
						}
					}
				}
				//检验报警实例
				if(importProtocolContent.getAlarmInstanceList()!=null && exportProtocolConfig.getAlarmInstanceList()!=null){
					for(int i=0;i<importProtocolContent.getAlarmInstanceList().size();i++){
						for(int j=0;j<exportProtocolConfig.getAlarmInstanceList().size();j++){
							if(importProtocolContent.getAlarmInstanceList().get(i)==exportProtocolConfig.getAlarmInstanceList().get(j).getId()){
								if(!StringManagerUtils.existOrNot(importProtocolContent.getAlarmUnitList(), exportProtocolConfig.getAlarmInstanceList().get(j).getAlarmUnitId())){
									errorDataBuff.append("{\"classes\":1,\"typeName\":\"报警实例\",\"type\":5,\"id\":"+exportProtocolConfig.getAlarmInstanceList().get(j).getId()+",\"text\":\""+exportProtocolConfig.getAlarmInstanceList().get(j).getName()+"\",\"errorInfo\":\"未选择报警实例对应的报警单元\"},");
								}
							}
						}
					}
				}
				
				List<?> existProtocolList=this.findCallSql(existProtocolSql);
				if(existProtocolList.size()>0){
					Object[] existProtocolObj = (Object[]) existProtocolList.get(0);
					int existProtocolId=StringManagerUtils.stringToInteger(existProtocolObj[0]+"");
					String existProtocolCode=existProtocolObj[1]+"";
					//判断采控单元是否存在
					if(importProtocolContent.getAcqUnitList()!=null && importProtocolContent.getAcqUnitList().size()>0 && exportProtocolConfig.getAcqUnitList()!=null ){
						for(int i=0;i<importProtocolContent.getAcqUnitList().size();i++){
							ExportProtocolConfig.AcqUnit acqUnit=null;
							for(int j=0;j<exportProtocolConfig.getAcqUnitList().size();j++){
								if(importProtocolContent.getAcqUnitList().get(i).getId()==exportProtocolConfig.getAcqUnitList().get(j).getId()){
									acqUnit=exportProtocolConfig.getAcqUnitList().get(j);
									break;
								}
							}
							if(acqUnit!=null){
								String addAcqUnitSql="select t.id,t.unit_code "
										+ " from TBL_ACQ_UNIT_CONF t "
										+ " where t.protocol='"+acqUnit.getProtocol()+"' and t.unit_name='"+acqUnit.getUnitName()+"'"
										+ " order by t.id desc";
								List<?> addAcqUnitList=this.findCallSql(addAcqUnitSql);
								if(addAcqUnitList.size()>0){//采控单元已存在
									Object[] existAcqUnitObj = (Object[]) addAcqUnitList.get(0);
									int existAcqUnitId=StringManagerUtils.stringToInteger(existAcqUnitObj[0]+"");
									String existAcqUnitCode=existAcqUnitObj[1]+"";
									result_json.append("{\"classes\":1,\"typeName\":\"采控单元\",\"type\":0,\"id\":"+acqUnit.getId()+",\"text\":\""+acqUnit.getUnitName()+"\"},");
									//判断采控组是否存在
									if(acqUnit.getAcqGroupList()!=null && acqUnit.getAcqGroupList().size()>0){
										for(int j=0;j<acqUnit.getAcqGroupList().size();j++){
											if(StringManagerUtils.existOrNot(importProtocolContent.getAcqUnitList().get(i).getAcqGroupList(), acqUnit.getAcqGroupList().get(j).getId())){
												String addAcqGroupSql="select t.id,t.group_code from TBL_ACQ_GROUP_CONF t,tbl_acq_group2unit_conf t2,tbl_acq_unit_conf t3 "
														+ " where t.id=t2.groupid and t2.unitid=t3.id "
														+ " and t3.protocol='"+acqUnit.getAcqGroupList().get(j).getProtocol()+"' "
														+ " and t3.id="+existAcqUnitId
														+ " and t.group_name='"+acqUnit.getAcqGroupList().get(j).getGroupName()+"'";
												
												List<?> addAcqGroupList=this.findCallSql(addAcqGroupSql);
												if(addAcqGroupList.size()>0){//采控组已存在
													Object[] existAcqGroupObj = (Object[]) addAcqGroupList.get(0);
													int existAcqGroupId=StringManagerUtils.stringToInteger(existAcqGroupObj[0]+"");
													result_json.append("{\"classes\":2,\"typeName\":\"采控组\",\"type\":0,\"id\":"+acqUnit.getAcqGroupList().get(j).getId()+",\"text\":\""+acqUnit.getAcqGroupList().get(j).getGroupName()+"\",\"unitId\":"+acqUnit.getId()+"},");
												}
											}
										}
									}
									//判断采控实例是否存在
									if(importProtocolContent.getAcqInstanceList()!=null && importProtocolContent.getAcqInstanceList().size()>0){
										for(int j=0;j<importProtocolContent.getAcqInstanceList().size();j++){
											ExportProtocolConfig.AcqInstance acqInstance=null;
											for(int k=0;k<exportProtocolConfig.getAcqInstanceList().size();k++){
												if(importProtocolContent.getAcqInstanceList().get(j)==exportProtocolConfig.getAcqInstanceList().get(k).getId()
														&& exportProtocolConfig.getAcqInstanceList().get(k).getUnitId()==acqUnit.getId()){
													acqInstance=exportProtocolConfig.getAcqInstanceList().get(k);
													break;
												}
											}
											if(acqInstance!=null){
												String addAcqInstanceSql="select t.id,t.code from TBL_PROTOCOLINSTANCE t where t.name='"+acqInstance.getName()+"' and t.unitid="+existAcqUnitId;
												List<?> addAcqInstanceList=this.findCallSql(addAcqInstanceSql);
												if(addAcqInstanceList.size()>0){//采控实例已存在
													Object[] existAcqInstanceObj = (Object[]) addAcqInstanceList.get(0);
													int existAcqInstanceId=StringManagerUtils.stringToInteger(existAcqInstanceObj[0]+"");
													result_json.append("{\"classes\":1,\"typeName\":\"采控实例\",\"type\":3,\"id\":"+acqInstance.getId()+",\"text\":\""+acqInstance.getName()+"\"},");
												}
											}
										}
									}
									
									//判断显示单元
									if(importProtocolContent.getDisplayUnitList()!=null && importProtocolContent.getDisplayUnitList().size()>0){
										for(int j=0;j<importProtocolContent.getDisplayUnitList().size();j++){
											ExportProtocolConfig.DisplayUnit displayUnit=null;
											for(int k=0;k<exportProtocolConfig.getDisplayUnitList().size();k++){
												if(exportProtocolConfig.getDisplayUnitList().get(k).getAcqUnitId()==acqUnit.getId()
														&& exportProtocolConfig.getDisplayUnitList().get(k).getId()==importProtocolContent.getDisplayUnitList().get(j)){
													displayUnit=exportProtocolConfig.getDisplayUnitList().get(k);
													break;
												}
											}
											if(displayUnit!=null){
												String addDisplayUnitSql="select t.id,t.unit_code from TBL_DISPLAY_UNIT_CONF t where t.unit_name='"+displayUnit.getUnitName()+"' and t.acqunitid= "+existAcqUnitId;
												List<?> addDisplayUnitList=this.findCallSql(addDisplayUnitSql);
												if(addDisplayUnitList.size()>0){//显示单元已存在
													Object[] existDisplayUnitObj = (Object[]) addDisplayUnitList.get(0);
													int existDisplayUnitId=StringManagerUtils.stringToInteger(existDisplayUnitObj[0]+"");
													result_json.append("{\"classes\":1,\"typeName\":\"显示单元\",\"type\":1,\"id\":"+displayUnit.getId()+",\"text\":\""+displayUnit.getUnitName()+"\"},");
													
													//如果显示单元存在，判断改单元对应的显示实例是否存在
													if(importProtocolContent.getDisplayInstanceList()!=null && importProtocolContent.getDisplayInstanceList().size()>0 ){
														for(int m=0;m<exportProtocolConfig.getDisplayInstanceList().size();m++){
															if(StringManagerUtils.existOrNot(importProtocolContent.getDisplayInstanceList(), exportProtocolConfig.getDisplayInstanceList().get(m).getId())
																	&& exportProtocolConfig.getDisplayInstanceList().get(m).getDisplayUnitId()==displayUnit.getId()){
																String addDisplayInstanceSql="select t.id,t.code from TBL_PROTOCOLDISPLAYINSTANCE t where t.name='"+exportProtocolConfig.getDisplayInstanceList().get(m).getName()+"' and t.displayunitid="+existDisplayUnitId;
																List<?> addDisplayInstanceList=this.findCallSql(addDisplayInstanceSql);
																if(addDisplayInstanceList.size()>0){//显示实例已存在
																	Object[] existDisplayInstanceObj = (Object[]) addDisplayInstanceList.get(0);
																	int existDisplayInstanceId=StringManagerUtils.stringToInteger(existDisplayInstanceObj[0]+"");
																	result_json.append("{\"classes\":1,\"typeName\":\"显示实例\",\"type\":4,\"id\":"+exportProtocolConfig.getDisplayInstanceList().get(m).getId()+",\"text\":\""+exportProtocolConfig.getDisplayInstanceList().get(m).getName()+"\"},");
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
					//判断报警单元
					if(importProtocolContent.getAlarmUnitList()!=null && importProtocolContent.getAlarmUnitList().size()>0 && exportProtocolConfig.getAlarmUnitList()!=null){
						for(int i=0;i<importProtocolContent.getAlarmUnitList().size();i++){
							ExportProtocolConfig.AlarmUnit alarmUnit=null;
							for(int j=0;j<exportProtocolConfig.getAlarmUnitList().size();j++){
								if(importProtocolContent.getAlarmUnitList().get(i)==exportProtocolConfig.getAlarmUnitList().get(j).getId()  ){
									alarmUnit=exportProtocolConfig.getAlarmUnitList().get(j);
									break;
								}
							}
							if(alarmUnit!=null){
								String addAlarmUnitSql="select t.id,t.unit_code "
										+ " from TBL_ALARM_UNIT_CONF t "
										+ " where t.unit_name='"+alarmUnit.getUnitName()+"' "
										+ " and t.protocol='"+alarmUnit.getProtocol()+"' "
										+ " order by t.id desc";
								List<?> addAlarmUnitList=this.findCallSql(addAlarmUnitSql);
								if(addAlarmUnitList.size()>0){//报警单元已存在
									Object[] addAlarmUnitObj = (Object[]) addAlarmUnitList.get(0);
									int existAlarmUnitId=StringManagerUtils.stringToInteger(addAlarmUnitObj[0]+"");
									String existAlarmUnitCode=addAlarmUnitObj[1]+"";
									result_json.append("{\"classes\":1,\"typeName\":\"报警单元\",\"type\":2,\"id\":"+alarmUnit.getId()+",\"text\":\""+alarmUnit.getUnitName()+"\"},");
									
									//如果报警单元存在，判断对应的报警实例是否存在
									if(importProtocolContent.getAlarmInstanceList()!=null && importProtocolContent.getAlarmInstanceList().size()>0 && exportProtocolConfig.getAlarmInstanceList()!=null){
										for(int m=0;m<exportProtocolConfig.getAlarmInstanceList().size();m++){
											if(StringManagerUtils.existOrNot(importProtocolContent.getAlarmInstanceList(), exportProtocolConfig.getAlarmInstanceList().get(m).getId())
													&& exportProtocolConfig.getAlarmInstanceList().get(m).getAlarmUnitId()==alarmUnit.getId()){
												String addAlarmInstanceSql="select t.id,t.code "
														+ " from TBL_PROTOCOLALARMINSTANCE t "
														+ " where t.name='"+exportProtocolConfig.getAlarmInstanceList().get(m).getName()+"' "
														+ " and t.alarmunitid="+existAlarmUnitId
														+ " order by t.id desc";
												List<?> addAlarmInstanceList=this.findCallSql(addAlarmInstanceSql);
												if(addAlarmInstanceList.size()>0){//报警实例已存在
													Object[] addAlarmInstanceObj = (Object[]) addAlarmInstanceList.get(0);
													int existAlarmInstanceId=StringManagerUtils.stringToInteger(addAlarmInstanceObj[0]+"");
													String existAlarmInstanceCode=addAlarmInstanceObj[1]+"";
													result_json.append("{\"classes\":1,\"typeName\":\"报警实例\",\"type\":5,\"id\":"+exportProtocolConfig.getAlarmInstanceList().get(m).getId()+",\"text\":\""+exportProtocolConfig.getAlarmInstanceList().get(m).getName()+"\"},");
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		if(errorDataBuff.toString().endsWith(",")){
			errorDataBuff.deleteCharAt(errorDataBuff.length() - 1);
		}
		errorDataBuff.append("]");
		result_json.append("],\"errorDataList\":"+ errorDataBuff+"}");
		return result_json.toString();
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
	
	public void doProtocolAdd(T protocolModel) throws Exception {
		getBaseDao().addObject(protocolModel);
	}
	
	public void doAcquisitionUnitAdd(T acquisitionUnit) throws Exception {
		getBaseDao().addObject(acquisitionUnit);
	}
	
	public void doAcquisitionUnitEdit(T acquisitionUnit) throws Exception {
		getBaseDao().updateObject(acquisitionUnit);
	}
	
	public void doAcquisitionUnitBulkDelete(final String ids) throws Exception {
		int delorUpdateCount=0;
		String tableName="tbl_device";
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
	
	public void doDeleteProtocolAssociation(String protocolName) throws Exception {
		int delorUpdateCount=0;
		String tableName="tbl_device";
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
	
	public void grantReportItemsPermission(T reportUnitItem) throws Exception {
		getBaseDao().saveOrUpdateObject(reportUnitItem);
	}
	
	public void deleteCurrentDisplayUnitOwnItems(final String unitId) throws Exception {
		final String hql = "DELETE DisplayUnitItem u where u.unitId ="+unitId;
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void deleteCurrentDisplayUnitOwnItems(final String unitId,final String itemType) throws Exception {
		final String hql = "DELETE DisplayUnitItem u where u.unitId ="+unitId+" and u.type="+itemType;
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void deleteCurrentReportUnitOwnItems(final String unitId,final String reportType) throws Exception {
		final String hql = "DELETE ReportUnitItem u where u.unitId ="+unitId+" and u.reportType="+reportType;
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void doModbusProtocolInstanceAdd(T protocolInstance) throws Exception {
		getBaseDao().addObject(protocolInstance);
	}
	public void doModbusProtocolInstanceEdit(T protocolInstance) throws Exception {
		getBaseDao().updateObject(protocolInstance);
	}
	
	public void doModbusProtocolInstanceBulkDelete(final String ids) throws Exception {
		String tableName="tbl_device";
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
	
	public void doDisplayUnitBulkDelete(final String ids) throws Exception {
		int delorUpdateCount=0;
		String tableName="tbl_device";
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
	
	public void deleteCurrentAlarmUnitOwnItems(String unidId) throws Exception {
		String hql = "DELETE AlarmUnitItem u where u.unitId ="+unidId;
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void grantAlarmItemsPermission(T alarmUnitItem) throws Exception {
		getBaseDao().addObject(alarmUnitItem);
	}
	
	public void doModbusProtocolDisplayInstanceEdit(T protocolDisplayInstance) throws Exception {
		getBaseDao().updateObject(protocolDisplayInstance);
	}
	
	public void doModbusProtocolReportUnitEdit(T reportUnit) throws Exception {
		getBaseDao().updateObject(reportUnit);
	}
	
	public void doModbusProtocolReportInstanceEdit(T protocolReportInstance) throws Exception {
		getBaseDao().updateObject(protocolReportInstance);
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
	
	
	public void doModbusProtocolReportUnitBulkDelete(final String ids) throws Exception {
		int delorUpdateCount=0;
		String sql = "update tbl_rpcdevice t set t.reportinstancecode='' where t.reportinstancecode in ( select t2.code from tbl_protocolreportinstance t2  where t2.unitid in ("+ids+") )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql = "update tbl_pcpdevice t set t.reportinstancecode='' where t.reportinstancecode in ( select t2.code from tbl_protocolreportinstance t2  where t2.unitid in ("+ids+") )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql="delete from tbl_report_items2unit_conf t where t.unitid in("+ids+")";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		
		sql="delete from tbl_protocolreportinstance t where t.unitid in("+ids+")";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		final String hql = "DELETE ReportUnit u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}
	
	public void doModbusProtocolReportInstanceBulkDelete(final String ids) throws Exception {
		int delorUpdateCount=0;
		String sql = "update tbl_rpcdevice t set t.reportinstancecode='' where t.reportinstancecode in (select t2.code from tbl_protocolreportinstance t2 where t2.id in("+ids+"))";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		sql = "update tbl_pcpdevice t set t.reportinstancecode='' where t.reportinstancecode in (select t2.code from tbl_protocolreportinstance t2 where t2.id in("+ids+") )";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		final String hql = "DELETE ProtocolReportInstance u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}
	
	public void doModbusProtocolDisplayInstanceAdd(T protocolDisplayInstance) throws Exception {
		getBaseDao().addObject(protocolDisplayInstance);
	}
	
	public void doModbusProtocolReportUnitAdd(T reportUnit) throws Exception {
		getBaseDao().addObject(reportUnit);
	}
	
	public void doModbusProtocolReportInstanceAdd(T protocolReportInstance) throws Exception {
		getBaseDao().addObject(protocolReportInstance);
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
