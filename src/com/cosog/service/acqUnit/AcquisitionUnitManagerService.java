package com.cosog.service.acqUnit;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AcquisitionGroup;
import com.cosog.model.AcquisitionGroupItem;
import com.cosog.model.AcquisitionUnit;
import com.cosog.model.AcquisitionUnitGroup;
import com.cosog.model.AlarmUnit;
import com.cosog.model.AlarmUnitItem;
import com.cosog.model.Code;
import com.cosog.model.CurveConf;
import com.cosog.model.DataMapping;
import com.cosog.model.DisplayUnit;
import com.cosog.model.DisplayUnitItem;
import com.cosog.model.ProtocolAlarmInstance;
import com.cosog.model.ProtocolDisplayInstance;
import com.cosog.model.ProtocolInstance;
import com.cosog.model.ProtocolModel;
import com.cosog.model.ProtocolReportInstance;
import com.cosog.model.ProtocolRunStatusConfig;
import com.cosog.model.ProtocolSMSInstance;
import com.cosog.model.ReportTemplate;
import com.cosog.model.ReportUnitItem;
import com.cosog.model.User;
import com.cosog.model.WorkType;
import com.cosog.model.ReportTemplate.Template;
import com.cosog.model.calculate.CalculateColumnInfo;
import com.cosog.model.calculate.CalculateColumnInfo.CalculateColumn;
import com.cosog.model.drive.ExportAcqInstanceData;
import com.cosog.model.drive.ExportAcqUnitData;
import com.cosog.model.drive.ExportAlarmInstanceData;
import com.cosog.model.drive.ExportAlarmUnitData;
import com.cosog.model.drive.ExportDisplayInstanceData;
import com.cosog.model.drive.ExportDisplayUnitData;
import com.cosog.model.drive.ExportProtocolConfig;
import com.cosog.model.drive.ExportReportInstanceData;
import com.cosog.model.drive.ExportReportUnitData;
import com.cosog.model.drive.ImportProtocolContent;
import com.cosog.model.drive.InitInstance;
import com.cosog.model.drive.InitProtocol;
import com.cosog.model.drive.ModbusProtocolAlarmUnitSaveData;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.drive.ModbusProtocolConfig.Protocol;
import com.cosog.model.gridmodel.DatabaseMappingProHandsontableChangedData;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.service.right.TabInfoManagerService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.task.MemoryDataManagerTask.CalItem;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.OracleJdbcUtis;
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
	@Autowired
	private TabInfoManagerService<?> tabInfoManagerService;

	public String getAcquisitionUnitList(Map map,Page pager,String language) {
		String protocolName = (String) map.get("protocolName");
		String unitName = (String) map.get("unitName");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark "
				+ " from tbl_acq_unit_conf t where 1=1");
		sqlBuffer.append(" and t.protocol = '" + protocolName + "' ");
		
		
		sqlBuffer.append(" and t.protocol='"+protocolName+"'");
		if (StringManagerUtils.isNotNull(unitName)) {
			sqlBuffer.append(" and t.unit_name like '%" + unitName + "%' ");
		}
		
		sqlBuffer.append(" order by t.id  asc");
		String json = "";
		String columns=service.showTableHeadersColumns("acquisitionUnit",language);
		try {
			json=this.findPageBySqlEntity(sqlBuffer.toString(),columns , pager );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public String doAcquisitionGroupShow(Map map,Page pager,String language) {
		String groupName = (String) map.get("groupName");
		String protocolName = (String) map.get("protocolName");
		StringBuffer sqlBuffer = new StringBuffer();
		StringBuffer jsonBuffer = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
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
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"groupName\" ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("code")+"\",\"dataIndex\":\"groupCode\" ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("groupTimingInterval")+"(s)\",\"dataIndex\":\"groupTimingInterval\" ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("groupSavingInterval")+"(s)\",\"dataIndex\":\"groupSavingInterval\" ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("description")+"\",\"dataIndex\":\"remark\",width:200 ,children:[] }"
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
	
	public String getProtocolItemsConfigData(String protocolName,String classes,String code,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("quantity")+"\",\"dataIndex\":\"quantity\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("storeDataType")+"\",\"dataIndex\":\"storeDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("IFDataType")+"\",\"dataIndex\":\"IFDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("prec")+"\",\"dataIndex\":\"prec\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("ratio")+"\",\"dataIndex\":\"ratio\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("RWType")+"\",\"dataIndex\":\"RWType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("resolutionMode")+"\",\"dataIndex\":\"resolutionMode\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("acqMode")+"\",\"dataIndex\":\"acqMode\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] }"
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

		ModbusProtocolConfig.Protocol protocolConfig=MemoryDataManagerTask.getProtocolByName(protocolName);
		if(protocolConfig!=null){
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
				String resolutionMode=languageResourceMap.get("numericValue");
				if(protocolConfig.getItems().get(j).getResolutionMode()==0){
					resolutionMode=languageResourceMap.get("switchingValue");
				}else if(protocolConfig.getItems().get(j).getResolutionMode()==1){
					resolutionMode=languageResourceMap.get("enumValue");
				}
				String RWType=languageResourceMap.get("readOnly");
				if("r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
					RWType=languageResourceMap.get("readOnly");
				}else if("w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
					RWType=languageResourceMap.get("writeOnly");
				}else if("rw".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
					RWType=languageResourceMap.get("readWrite");
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
						+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?languageResourceMap.get("activeAcqModel"):languageResourceMap.get("passiveAcqModel"))+"\","
						+ "\"sort\":\""+sort+"\"},");
				
			}
		}
	
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolAcqUnitItemsConfigData(String protocolName,String classes,String code,String type,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		ModbusProtocolConfig.Protocol protocolConfig=MemoryDataManagerTask.getProtocolByName(protocolName);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("quantity")+"\",\"dataIndex\":\"quantity\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("storeDataType")+"\",\"dataIndex\":\"storeDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("IFDataType")+"\",\"dataIndex\":\"IFDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("RWType")+"\",\"dataIndex\":\"RWType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("ratio")+"\",\"dataIndex\":\"ratio\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("resolutionMode")+"\",\"dataIndex\":\"resolutionMode\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("acqMode")+"\",\"dataIndex\":\"acqMode\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("curveSort")+"\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("curveColor")+"\",\"dataIndex\":\"realtimeCurveColor\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("curveSort")+"\",\"dataIndex\":\"historyCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("curveColor")+"\",\"dataIndex\":\"historyCurveColor\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<Integer> dailyTotalCalculateList=new ArrayList<Integer>();
		List<String> dailyTotalCalculateNameList=new ArrayList<String>();
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.bitindex,t.dailyTotalCalculate,t.dailyTotalCalculateName from "
					+ " TBL_ACQ_ITEM2GROUP_CONF t,tbl_acq_group_conf t2 "
					+ " where t.groupid=t2.id and t2.group_code='"+code+"' order by t.id";
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsBitIndexList.add(obj[1]+"");
				dailyTotalCalculateList.add(StringManagerUtils.stringToInteger(obj[2]+""));
				dailyTotalCalculateNameList.add(obj[3]+"");
			}
		}

		if(protocolConfig!=null){
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
					boolean dailyTotalCalculate=false;
					String dailyTotalCalculateName="";
					String resolutionMode=languageResourceMap.get("numericValue");
					if(protocolConfig.getItems().get(j).getResolutionMode()==0){
						resolutionMode=languageResourceMap.get("switchingValue");
					}else if(protocolConfig.getItems().get(j).getResolutionMode()==1){
						resolutionMode=languageResourceMap.get("enumValue");
					}
					String RWType=languageResourceMap.get("readOnly");
					if("r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWType=languageResourceMap.get("readOnly");
					}else if("w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWType=languageResourceMap.get("writeOnly");
					}else if("rw".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWType=languageResourceMap.get("readWrite");
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
									dailyTotalCalculate=dailyTotalCalculateList.get(m)==1;
									if(dailyTotalCalculate){
										dailyTotalCalculateName=dailyTotalCalculateNameList.get(m);
									}
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
									+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?languageResourceMap.get("activeAcqModel"):languageResourceMap.get("passiveAcqModel"))+"\","
											+ "\"dailyTotalCalculate\":"+dailyTotalCalculate+","
											+ "\"dailyTotalCalculateName\":\""+dailyTotalCalculateName+"\""
									+ "},");
							index++;
						}
					}else{
						checked=StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(),false);
						
						for(int m=0;m<itemsList.size();m++){
							if(itemsList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())){
								checked=true;
								dailyTotalCalculate=dailyTotalCalculateList.get(m)==1;
								if(dailyTotalCalculate){
									dailyTotalCalculateName=dailyTotalCalculateNameList.get(m);
								}
								break;
							}
						}
						
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
								+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?languageResourceMap.get("activeAcqModel"):languageResourceMap.get("passiveAcqModel"))+"\","
								+ "\"dailyTotalCalculate\":"+dailyTotalCalculate+","
								+ "\"dailyTotalCalculateName\":\""+dailyTotalCalculateName+"\""
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
	
	public String getProtocolEnumOrSwitchItemsConfigData(String protocolCode,String resolutionMode,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"protocolCode\":\""+protocolCode+"\",\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		ModbusProtocolConfig.Protocol protocolConfig=MemoryDataManagerTask.getProtocolByCode(protocolCode);
		if(protocolConfig!=null){
			for(int j=0;j<protocolConfig.getItems().size();j++){
				if((!"w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())) && protocolConfig.getItems().get(j).getResolutionMode()==StringManagerUtils.stringToInteger(resolutionMode)){
					result_json.append("{\"id\":"+(j+1)+","
							+ "\"protocolCode\":\""+protocolCode+"\","
							+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
							+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+"},");
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
	
	public String getProtocolItemMeaningConfigData(String protocolCode,String itemAddr,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer totalRoot = new StringBuffer();
		Gson gson = new Gson();
		int resolutionMode=0;
		String title="";
		totalRoot.append("[");
		int totolCount=0;

		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		ModbusProtocolConfig.Protocol protocolConfig=MemoryDataManagerTask.getProtocolByCode(protocolCode);
		if(protocolConfig!=null){
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
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("value")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("meaning")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] }"
				+ "]";
		if(resolutionMode==0){
			columns = "["
					+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("bit")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("meaning")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] }"
					+ "]";
		}
		result_json.append("{ \"success\":true,\"columns\":"+columns+",\"itemResolutionMode\":"+resolutionMode+",\"itemTitle\":\""+title+"\",");
		result_json.append("\"totalRoot\":"+totalRoot+"");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusProtocolNumAlarmItemsConfigData(String protocolName,String classes,String code,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\"\",\"dataIndex\":\"checked\",width:20 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("upperLimit")+"\",\"dataIndex\":\"upperLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("lowerLimit")+"\",\"dataIndex\":\"lowerLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("hystersis")+"\",\"dataIndex\":\"hystersis\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<Integer> itemAddrsList=new ArrayList<Integer>();
		List<?> list=null;
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.itemaddr,t.upperlimit,t.lowerlimit,t.hystersis,"
					+ " t.delay,t.retriggerTime,"
					+ " t.alarmlevel,"
					+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as alarmsign,"
					+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
					+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2  "
					+ " where t.type=2 and t.unitid=t2.id "
					+ " and t2.unit_code='"+code+"' "
					+ " order by t.id";
			list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				itemAddrsList.add(StringManagerUtils.stringToInteger(obj[2]+""));
			}
		}

		ModbusProtocolConfig.Protocol protocolConfig=MemoryDataManagerTask.getProtocolByName(protocolName);
		if(protocolConfig!=null){
			Collections.sort(protocolConfig.getItems());
			int index=1;
			for(int j=0;j<protocolConfig.getItems().size();j++){
				if((!"w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())) && protocolConfig.getItems().get(j).getResolutionMode()==2){
					String upperLimit="",lowerLimit="",hystersis="",delay="",retriggerTime="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
					boolean checked=false;
					for(int k=0;k<itemAddrsList.size();k++){
						Object[] obj = (Object[]) list.get(k);
						if(itemAddrsList.get(k)==protocolConfig.getItems().get(j).getAddr()){
							checked=true;
							upperLimit=obj[3]+"";
							lowerLimit=obj[4]+"";
							hystersis=obj[5]+"";
							delay=obj[6]+"";
							retriggerTime=obj[7]+"";
							
							alarmLevel=MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[8]+"", language);
							
							alarmSign=obj[9]+"";
							isSendMessage=obj[10]+"";
							isSendMail=obj[11]+"";
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
							+ "\"retriggerTime\":\""+retriggerTime+"\","
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
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusProtocolCalNumAlarmItemsConfigData(String deviceType,String classes,String code,String calculateType,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		List<CalItem> calItemList=null;
		try{
			if(StringManagerUtils.stringToInteger(calculateType)==1){
				calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
			}else if(StringManagerUtils.stringToInteger(calculateType)==2){
				calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
			}else{
				calItemList=MemoryDataManagerTask.getAcqCalculateItem(language);
			}
			String columns = "["
					+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:120 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("upperLimit")+"\",\"dataIndex\":\"upperLimit\",width:80 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("lowerLimit")+"\",\"dataIndex\":\"lowerLimit\",width:80 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("hystersis")+"\",\"dataIndex\":\"hystersis\",width:80 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
					+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
					+ "]";
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"totalRoot\":[");
			if(calItemList!=null){
				List<String> itemsList=new ArrayList<String>();
				List<?> list=null;
				if("3".equalsIgnoreCase(classes)){
					String sql="select t.itemname,t.itemcode,t.upperlimit,t.lowerlimit,t.hystersis,"
							+ " t.delay,t.retriggerTime,"
							+ " t.alarmlevel,"
							+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as alarmsign,"
							+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
							+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
							+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2  "
							+ " where t.type=5 and t.unitid=t2.id "
							+ " and t2.unit_code='"+code+"' "
							+ " order by t.id";
					list=this.findCallSql(sql);
					for(int i=0;i<list.size();i++){
						Object[] obj = (Object[]) list.get(i);
						itemsList.add(obj[1]+"");
					}
				}
				
				int index=1;
				for(CalItem calItem:calItemList){
					if(calItem.getDataType()==2){
						String upperLimit="",lowerLimit="",hystersis="",delay="",retriggerTime="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
						boolean checked=false;
						for(int k=0;k<itemsList.size();k++){
							Object[] obj = (Object[]) list.get(k);
							if(calItem.getCode().equalsIgnoreCase(itemsList.get(k))){
								checked=true;
								upperLimit=obj[2]+"";
								lowerLimit=obj[3]+"";
								hystersis=obj[4]+"";
								delay=obj[5]+"";
								retriggerTime=obj[6]+"";
								
								alarmLevel=MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[7]+"", language);
								
								alarmSign=obj[8]+"";
								isSendMessage=obj[9]+"";
								isSendMail=obj[10]+"";
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
								+ "\"retriggerTime\":\""+retriggerTime+"\","
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
			
		}
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusProtocolEnumAlarmItemsConfigData(String protocolName,String classes,String unitCode,String itemAddr,String itemResolutionMode,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\"\",\"dataIndex\":\"checked\",width:20 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("value")+"\",\"dataIndex\":\"value\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("meaning")+"\",\"dataIndex\":\"meaning\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<Integer> itemValueList=new ArrayList<Integer>();
		List<?> list=null;
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.itemaddr,t.value,"
					+ " t.delay,t.retriggerTime,"
					+ " t.alarmlevel,"
					+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"'), "
					+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
					+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2  "
					+ " where t.type="+itemResolutionMode+" and t.itemAddr="+itemAddr+" and t.unitid=t2.id "
					+ " and t2.unit_code='"+unitCode+"' "
					+ " order by t.id";
			list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				itemValueList.add(StringManagerUtils.stringToInteger(obj[3]+""));
			}
		}

		ModbusProtocolConfig.Protocol protocolConfig=MemoryDataManagerTask.getProtocolByName(protocolName);
		if(protocolConfig!=null){
			Collections.sort(protocolConfig.getItems());
			int index=1;
			for(int j=0;j<protocolConfig.getItems().size();j++){
				if(protocolConfig.getItems().get(j).getResolutionMode()==StringManagerUtils.stringToInteger(itemResolutionMode)
						&& protocolConfig.getItems().get(j).getAddr()==StringManagerUtils.stringToInteger(itemAddr)){
					for(int k=0;protocolConfig.getItems().get(j).getMeaning()!=null&&k<protocolConfig.getItems().get(j).getMeaning().size();k++){
						String value="",delay="",retriggerTime="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
						boolean checked=false;
						for(int m=0;m<itemValueList.size();m++){
							Object[] obj = (Object[]) list.get(m);
							if(itemValueList.get(m)==protocolConfig.getItems().get(j).getMeaning().get(k).getValue()){
								checked=true;
								delay=obj[4]+"";
								retriggerTime=obj[5]+"";
								alarmLevel=MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[6]+"", language);
								alarmSign=obj[7]+"";
								isSendMessage=obj[8]+"";
								isSendMail=obj[9]+"";
								break;
							}
						}
						result_json.append("{\"checked\":"+checked+","
								+ "\"id\":"+(index)+","
								+ "\"value\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"\","
								+ "\"meaning\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
								+ "\"delay\":\""+delay+"\","
								+ "\"retriggerTime\":\""+retriggerTime+"\","
								+ "\"alarmLevel\":\""+alarmLevel+"\","
								+ "\"alarmSign\":\""+alarmSign+"\","
								+ "\"isSendMessage\":\""+isSendMessage+"\","
								+ "\"isSendMail\":\""+isSendMail+"\""
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
	
	public String getModbusProtocolSwitchAlarmItemsConfigData(String protocolName,String classes,String unitCode,String itemAddr,String itemResolutionMode,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\"\",\"dataIndex\":\"checked\",width:20 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("bit")+"\",\"dataIndex\":\"bitIndex\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("meaning")+"\",\"dataIndex\":\"meaning\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("switchItemAlarmValue")+"\",\"dataIndex\":\"value\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<Integer> itemValueList=new ArrayList<Integer>();
		List<?> list=null;
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.itemaddr,t.bitindex,t.value,"
					+ " t.delay,t.retriggerTime,"
					+ " t.alarmlevel,"
					+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"'), "
					+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
					+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2  "
					+ " where t.type="+itemResolutionMode+" and t.itemAddr="+itemAddr+" and t.unitid=t2.id "
					+ " and t2.unit_code='"+unitCode+"' "
					+ " order by t.id";
			list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				itemValueList.add(StringManagerUtils.stringToInteger(obj[3]+""));
			}
		}

		ModbusProtocolConfig.Protocol protocolConfig=MemoryDataManagerTask.getProtocolByName(protocolName);
		if(protocolConfig!=null){
			int index=1;
			Collections.sort(protocolConfig.getItems());
			for(int j=0;j<protocolConfig.getItems().size();j++){
				if(protocolConfig.getItems().get(j).getResolutionMode()==StringManagerUtils.stringToInteger(itemResolutionMode)
						&& protocolConfig.getItems().get(j).getAddr()==StringManagerUtils.stringToInteger(itemAddr)){
					for(int k=0;protocolConfig.getItems().get(j).getMeaning()!=null&&k<protocolConfig.getItems().get(j).getMeaning().size();k++){
						String value="",delay="",retriggerTime="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
						boolean checked=false;
						for(int m=0;m<itemValueList.size();m++){
							Object[] obj = (Object[]) list.get(m);
							if(itemValueList.get(m)==protocolConfig.getItems().get(j).getMeaning().get(k).getValue()){
								checked=true;
								if("0".equals(obj[4]+"")){
									value=languageResourceMap.get("switchingCloseValue");
								}else if("1".equals(obj[4]+"")){
									value=languageResourceMap.get("switchingOpenValue");
								}
								delay=obj[5]+"";
								retriggerTime=obj[6]+"";
								alarmLevel=MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[7]+"", language);
								alarmSign=obj[8]+"";
								isSendMessage=obj[9]+"";
								isSendMail=obj[10]+"";
								break;
							}
						}
						result_json.append("{\"checked\":"+checked+","
								+ "\"id\":"+(index)+","
								+ "\"bitIndex\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"\","
								+ "\"meaning\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
								+ "\"value\":\""+value+"\","
								+ "\"delay\":\""+delay+"\","
								+ "\"retriggerTime\":\""+retriggerTime+"\","
								+ "\"alarmLevel\":\""+alarmLevel+"\","
								+ "\"alarmSign\":\""+alarmSign+"\","
								+ "\"isSendMessage\":\""+isSendMessage+"\","
								+ "\"isSendMail\":\""+isSendMail+"\""
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
	
	public String getModbusProtocolCommStatusAlarmItemsConfigData(String protocolName,String classes,String code,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\"\",\"dataIndex\":\"checked\",width:20 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemCodeList=new ArrayList<String>();
		List<String> commStatusItemCodeList=new ArrayList<String>();
		commStatusItemCodeList.add("goOnline");
		commStatusItemCodeList.add("offline");
		List<?> list=null;
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,"
					+ " t.delay,t.retriggerTime,"
					+ " t.alarmlevel,"
					+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as alarmsign,"
					+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
					+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2  "
					+ " where t.type=3 and t.unitid=t2.id "
					+ " and t2.unit_code='"+code+"' "
					+ " order by t.id";
			list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				itemCodeList.add(obj[1]+"");
			}
		}
		
		
		for(int i=0;i<commStatusItemCodeList.size();i++){
			boolean checked=false;
			String delay="",retriggerTime="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
			String itemName=languageResourceMap.get(commStatusItemCodeList.get(i));
			String itemCode=commStatusItemCodeList.get(i);
			int value=1;
			if("offline".equalsIgnoreCase(itemCode)){
				value=0;
			}else if("online".equalsIgnoreCase(itemCode)){
				value=1;
			}else if("goOnline".equalsIgnoreCase(itemCode)){
				value=2;
			}
			if(StringManagerUtils.existOrNot(itemCodeList,commStatusItemCodeList.get(i),false)){
				for(int j=0;j<list.size();j++){
					Object[] obj = (Object[]) list.get(j);
					if(commStatusItemCodeList.get(i).equalsIgnoreCase(obj[1]+"")){
						checked=true;
						delay=obj[2]+"";
						retriggerTime=obj[3]+"";
						alarmLevel=MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[4]+"", language);
						alarmSign=obj[5]+"";
						isSendMessage=obj[6]+"";
						isSendMail=obj[7]+"";
						break;
					}
				}
			}
			result_json.append("{\"checked\":"+checked+","
					+ "\"id\":"+(i+1)+","
					+ "\"title\":\""+itemName+"\","
					+ "\"code\":\""+itemCode+"\","
					+ "\"value\":\""+value+"\","
					+ "\"delay\":\""+delay+"\","
					+ "\"retriggerTime\":\""+retriggerTime+"\","
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
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusProtocolRunStatusAlarmItemsConfigData(String protocolName,String classes,String code,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\"\",\"dataIndex\":\"checked\",width:20 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemCodeList=new ArrayList<String>();
		List<String> runStatusItemCodeList=new ArrayList<String>();
		runStatusItemCodeList.add("run");
		runStatusItemCodeList.add("stop");
		List<?> list=null;
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,"
					+ " t.delay,t.retriggerTime,"
					+ " t.alarmlevel,"
					+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as alarmsign,"
					+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
					+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2  "
					+ " where t.type=6 and t.unitid=t2.id "
					+ " and t2.unit_code='"+code+"' "
					+ " order by t.id";
			list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				itemCodeList.add(obj[1]+"");
			}
		}
		
		
		for(int i=0;i<runStatusItemCodeList.size();i++){
			boolean checked=false;
			String delay="",retriggerTime="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
			
			String itemName=languageResourceMap.get(runStatusItemCodeList.get(i));
			String itemCode=runStatusItemCodeList.get(i);
			
			int value=2;
			if("stop".equalsIgnoreCase(itemCode)){
				value=0;
			}else if("run".equalsIgnoreCase(itemCode)){
				value=1;
			}
			
			if(StringManagerUtils.existOrNot(itemCodeList,runStatusItemCodeList.get(i),false)){
				for(int j=0;j<list.size();j++){
					Object[] obj = (Object[]) list.get(j);
					if(runStatusItemCodeList.get(i).equalsIgnoreCase(obj[1]+"")){
						checked=true;
						delay=obj[2]+"";
						retriggerTime=obj[3]+"";
						alarmLevel=MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[4]+"", language);
						alarmSign=obj[5]+"";
						isSendMessage=obj[6]+"";
						isSendMail=obj[7]+"";
						break;
					}
				}
			}
			result_json.append("{\"checked\":"+checked+","
					+ "\"id\":"+(i+1)+","
					+ "\"title\":\""+itemName+"\","
					+ "\"code\":\""+itemCode+"\","
					+ "\"value\":\""+value+"\","
					+ "\"delay\":\""+delay+"\","
					+ "\"retriggerTime\":\""+retriggerTime+"\","
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
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusProtocolFESDiagramConditionsAlarmItemsConfigData(String protocolName,String classes,String code,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Map<String,WorkType> workTypeMap=MemoryDataManagerTask.getWorkTypeMap(language);
		
		String columns = "["
				+ "{ \"header\":\"\",\"dataIndex\":\"checked\",width:20 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<?> list=null;
		if("3".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,"
					+ " t.delay,t.retriggerTime,"
					+ " t.alarmlevel,"
					+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as alarmsign,"
					+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
					+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2  "
					+ " where t.type=4 and t.unitid=t2.id "
					+ " and t2.unit_code='"+code+"' "
					+ " order by t.id";
			list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				itemsList.add(obj[1]+"");
			}
		}
		
		int idx=0;
		Iterator<Map.Entry<String, WorkType>> it = workTypeMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, WorkType> entry = it.next();
			String resultCode=new String(entry.getKey());
			WorkType workType=entry.getValue();
			boolean checked=false;
			idx++;
			String delay="",retriggerTime="",alarmLevel="",alarmSign="",isSendMessage="",isSendMail="";
			if(StringManagerUtils.existOrNot(itemsList,workType.getResultCode()+"",false)){
				for(int j=0;j<list.size();j++){
					Object[] obj = (Object[]) list.get(j);
					if((workType.getResultCode()+"").equalsIgnoreCase(obj[1]+"")){
						checked=true;
						delay=obj[2]+"";
						retriggerTime=obj[3]+"";
						alarmLevel=MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[4]+"", language);
						alarmSign=obj[5]+"";
						isSendMessage=obj[6]+"";
						isSendMail=obj[7]+"";
						break;
					}
				}
			}
			result_json.append("{\"checked\":"+checked+","
					+ "\"id\":"+idx+","
					+ "\"title\":\""+workType.getResultName()+"\","
					+ "\"code\":\""+workType.getResultCode()+"\","
					+ "\"delay\":\""+delay+"\","
					+ "\"retriggerTime\":\""+retriggerTime+"\","
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
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolDisplayUnitAcqItemsConfigData(String protocolName,String classes,String code,String unitId,String acqUnitId,String calculateType,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		java.lang.reflect.Type type=null;
		List<CalItem> calItemList=new ArrayList<>();
		try{
			if("1".equalsIgnoreCase(calculateType)){
				calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
			}else if("2".equalsIgnoreCase(calculateType)){
				calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
			}else{
				calItemList=MemoryDataManagerTask.getAcqCalculateItem(language);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		List<CalItem> inputItemList=new ArrayList<>();
		try{
			if("1".equalsIgnoreCase(calculateType)){
				inputItemList=MemoryDataManagerTask.getSRPInputItem(language);
			}else if("2".equalsIgnoreCase(calculateType)){
				inputItemList=MemoryDataManagerTask.getPCPInputItem(language);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurveConf\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveConf\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> acqItemsList=new ArrayList<String>();
		List<String> acqItemsBitIndexList=new ArrayList<String>();
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsCodeList=new ArrayList<String>();
		List<String> itemsRealtimeSortList=new ArrayList<String>();
		List<String> itemsHistorySortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveConfList=new ArrayList<String>();
		List<String> historyCurveConfList=new ArrayList<String>();
		
		List<String> realtimeColorList=new ArrayList<String>();
		List<String> realtimeBgColorList=new ArrayList<String>();
		List<String> historyColorList=new ArrayList<String>();
		List<String> historyBgColorList=new ArrayList<String>();
		
		List<Boolean> realtimeDataList=new ArrayList<>();
		
		List<Boolean> realtimeOverviewList=new ArrayList<>();
		List<String> realtimeOverviewSortList=new ArrayList<String>();
		
		List<Boolean> historyDataList=new ArrayList<>();
		
		List<Boolean> historyOverviewList=new ArrayList<>();
		List<String> historyOverviewSortList=new ArrayList<String>();
		
		ModbusProtocolConfig.Protocol protocolConfig=MemoryDataManagerTask.getProtocolByName(protocolName);
		
		
		//calculate
		if("2".equalsIgnoreCase(classes) && StringManagerUtils.isNotNull(unitId)){
			String dailyTotalItemsSql="select t.itemname,t.dailytotalcalculatename,t6.mappingcolumn "
					+ " from TBL_ACQ_ITEM2GROUP_CONF t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4,tbl_display_unit_conf t5,"
					+ " tbl_datamapping t6 "
					+ " where t.groupid=t2.id and t2.id=t3.groupid and t3.unitid=t4.id and t4.id=t5.acqunitid "
					+ " and t.itemname=t6.name and t.dailytotalcalculate=1 and t5.id="+unitId
					+ " order by t.id";
			List<?> unitDailyTotalItemsList=this.findCallSql(dailyTotalItemsSql);
			for(int i=0;i<unitDailyTotalItemsList.size();i++){
				Object[] obj=(Object[])unitDailyTotalItemsList.get(i);
				String itemName=obj[0]+"";
				String name=obj[1]+"";
				String totalCode=(obj[2]+"_TOTAL").toUpperCase();
				String unit="";
				if(protocolConfig!=null && protocolConfig.getItems()!=null){
					for(ModbusProtocolConfig.Items item:protocolConfig.getItems()){
						if(itemName.equalsIgnoreCase(item.getTitle())){
							unit=item.getUnit();
							break;
						}
					}
				}
				CalItem calItem=new CalItem(name,totalCode,unit,2,languageResourceMap.get("configuration"),itemName+languageResourceMap.get("dailyCalculate"));
				calItemList.add(calItem);
			}
		}
		
		if("2".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.realtimeSort,t.historySort,"
					+ "t.showlevel,t.realtimeCurveConf,t.historyCurveConf,"
					+ "t.realtimeColor,t.realtimeBgColor,t.historyColor,t.historyBgColor,"
					+ "t.realtimeOverview,t.realtimeOverviewSort,t.realtimeData, "
					+ "t.historyOverview,t.historyOverviewSort,t.historyData "
					+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2 "
					+ " where t.unitid=t2.id and t2.id= "+unitId+" and t.type=1"
					+ " order by t.realtimeSort";
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsCodeList.add(obj[1]+"");
				itemsRealtimeSortList.add(obj[2]+"");
				itemsHistorySortList.add(obj[3]+"");
				
				itemsShowLevelList.add(obj[4]+"");
				String realtimeCurveConf=obj[5]+"";
				if(!StringManagerUtils.isNotNull(obj[5]+"")){
					realtimeCurveConf="\"\"";
				}
				String historyCurveConf=obj[6]+"";
				if(!StringManagerUtils.isNotNull(obj[6]+"")){
					historyCurveConf="\"\"";
				}
				
				realtimeCurveConfList.add(realtimeCurveConf);
				historyCurveConfList.add(historyCurveConf);
				
				realtimeColorList.add(obj[7]+"");
				realtimeBgColorList.add(obj[8]+"");
				historyColorList.add(obj[9]+"");
				historyBgColorList.add(obj[10]+"");
				
				
				realtimeOverviewList.add(StringManagerUtils.stringToInteger(obj[11]+"")==1);
				realtimeOverviewSortList.add(obj[12]+"");
				realtimeDataList.add(StringManagerUtils.stringToInteger(obj[13]+"")==1);
				
				historyOverviewList.add(StringManagerUtils.stringToInteger(obj[14]+"")==1);
				historyOverviewSortList.add(obj[15]+"");
				historyDataList.add(StringManagerUtils.stringToInteger(obj[16]+"")==1);
			}
		}
		
		int index=1;
		for(CalItem calItem:calItemList){
			boolean checked=false;
			String realtimeSort="";
			String historySort="";
			String showLevel="";
			String realtimeCurveConf="\"\"";
			String realtimeCurveConfShowValue="";
			String historyCurveConf="\"\"";
			String historyCurveConfShowValue="";
			String realtimeColor=""; 
			String realtimeBgColor="";
			String historyColor="";
			String historyBgColor="";
			
			boolean realtimeOverview=false;
			String  realtimeOverviewSort="";
			boolean realtimeData=false;
			
			boolean historyOverview=false;
			String  historyOverviewSort="";
			boolean historyData=false;

			checked=StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(),false);
			if(checked){
				for(int k=0;k<itemsList.size();k++){
					if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
						realtimeSort=itemsRealtimeSortList.get(k);
						historySort=itemsHistorySortList.get(k);
						showLevel=itemsShowLevelList.get(k);
						realtimeCurveConf=realtimeCurveConfList.get(k);
						historyCurveConf=historyCurveConfList.get(k);
						
						realtimeColor=realtimeColorList.get(k);
						realtimeBgColor=realtimeBgColorList.get(k);
						historyColor=historyColorList.get(k);
						historyBgColor=historyBgColorList.get(k);
						
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
							realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+(realtimeCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+realtimeCurveConfObj.getColor();
						}
						if(historyCurveConfObj!=null){
							historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+(historyCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+historyCurveConfObj.getColor();
						}
						
						realtimeOverview=realtimeOverviewList.get(k);
						realtimeOverviewSort=realtimeOverviewSortList.get(k);
						realtimeData=realtimeDataList.get(k);
						
						historyOverview=historyOverviewList.get(k);
						historyOverviewSort=historyOverviewSortList.get(k);
						historyData=historyDataList.get(k);
						
						break;
					}
				}
			}
			result_json.append("{\"checked\":"+checked+","
					+ "\"id\":"+(index)+","
					+ "\"title\":\""+calItem.getName()+"\","
					+ "\"unit\":\""+calItem.getUnit()+"\","
					+ "\"showLevel\":\""+showLevel+"\","
					+ "\"realtimeSort\":\""+realtimeSort+"\","
					+ "\"realtimeColor\":\""+realtimeColor+"\","
					+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
					+ "\"historySort\":\""+historySort+"\","
					+ "\"historyColor\":\""+historyColor+"\","
					+ "\"historyBgColor\":\""+historyBgColor+"\","
					+ "\"realtimeCurveConf\":"+realtimeCurveConf+","
					+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
					+ "\"historyCurveConf\":"+historyCurveConf+","
					+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\","
					+ "\"code\":\""+calItem.getCode()+"\","
//					+ "\"dataSource\":\""+calItem.getDataSource()+"\""
					+ "\"type\":1,"
					+ "\"dataSource\":\""+languageResourceMap.get("calculate")+"\","
					+ "\"realtimeOverview\":"+realtimeOverview+","
					+ "\"realtimeOverviewSort\":\""+realtimeOverviewSort+"\","
					+ "\"realtimeData\":"+realtimeData+","
					+ "\"historyOverview\":"+historyOverview+","
					+ "\"historyOverviewSort\":\""+historyOverviewSort+"\","
					+ "\"historyData\":"+historyData+""
					+ "},");
			index++;
		
		}
		
		//input
		acqItemsList=new ArrayList<String>();
		acqItemsBitIndexList=new ArrayList<String>();
		
		itemsList=new ArrayList<String>();
		itemsCodeList=new ArrayList<String>();
		itemsRealtimeSortList=new ArrayList<String>();
		itemsHistorySortList=new ArrayList<String>();
		itemsBitIndexList=new ArrayList<String>();
		itemsShowLevelList=new ArrayList<String>();
		realtimeCurveConfList=new ArrayList<String>();
		historyCurveConfList=new ArrayList<String>();
		
		realtimeColorList=new ArrayList<String>();
		realtimeBgColorList=new ArrayList<String>();
		historyColorList=new ArrayList<String>();
		historyBgColorList=new ArrayList<String>();
		
		realtimeDataList=new ArrayList<>();
		realtimeOverviewList=new ArrayList<>();
		realtimeOverviewSortList=new ArrayList<String>();
		
		historyDataList=new ArrayList<>();
		historyOverviewList=new ArrayList<>();
		historyOverviewSortList=new ArrayList<String>();
		
		if("2".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.realtimeSort,t.historySort,"
					+ "t.showlevel,t.realtimeCurveConf,t.historyCurveConf,"
					+ "t.realtimeColor,t.realtimeBgColor,t.historyColor,t.historyBgColor, "
					+ "t.realtimeOverview,t.realtimeOverviewSort,t.realtimeData, "
					+ "t.historyOverview,t.historyOverviewSort,t.historyData "
					+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2 "
					+ " where t.unitid=t2.id and t2.id= "+unitId+" and t.type=3"
					+ " order by t.realtimeSort";
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsCodeList.add(obj[1]+"");
				itemsRealtimeSortList.add(obj[2]+"");
				itemsHistorySortList.add(obj[3]+"");
				itemsShowLevelList.add(obj[4]+"");
				String realtimeCurveConf=obj[5]+"";
				if(!StringManagerUtils.isNotNull(obj[5]+"")){
					realtimeCurveConf="\"\"";
				}
				String historyCurveConf=obj[6]+"";
				if(!StringManagerUtils.isNotNull(obj[6]+"")){
					historyCurveConf="\"\"";
				}
				
				realtimeCurveConfList.add(realtimeCurveConf);
				historyCurveConfList.add(historyCurveConf);
				
				realtimeColorList.add(obj[7]+"");
				realtimeBgColorList.add(obj[8]+"");
				historyColorList.add(obj[9]+"");
				historyBgColorList.add(obj[10]+"");
				
				realtimeOverviewList.add(StringManagerUtils.stringToInteger(obj[11]+"")==1);
				realtimeOverviewSortList.add(obj[12]+"");
				realtimeDataList.add(StringManagerUtils.stringToInteger(obj[13]+"")==1);
				
				historyOverviewList.add(StringManagerUtils.stringToInteger(obj[14]+"")==1);
				historyOverviewSortList.add(obj[15]+"");
				historyDataList.add(StringManagerUtils.stringToInteger(obj[16]+"")==1);
			}
		}

		for(CalItem calItem:inputItemList){
			boolean checked=false;
			String realtimeSort="";
			String historySort="";
			String showLevel="";
			String realtimeCurveConf="\"\"";
			String realtimeCurveConfShowValue="";
			String historyCurveConf="\"\"";
			String historyCurveConfShowValue="";
			String realtimeColor=""; 
			String realtimeBgColor="";
			String historyColor="";
			String historyBgColor="";
			
			boolean realtimeOverview=false;
			String  realtimeOverviewSort="";
			boolean realtimeData=false;
			
			boolean historyOverview=false;
			String  historyOverviewSort="";
			boolean historyData=false;

			checked=StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(),false);
			if(checked){
				for(int k=0;k<itemsList.size();k++){
					if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
						realtimeSort=itemsRealtimeSortList.get(k);
						historySort=itemsHistorySortList.get(k);
						showLevel=itemsShowLevelList.get(k);
						realtimeCurveConf=realtimeCurveConfList.get(k);
						historyCurveConf=historyCurveConfList.get(k);
						realtimeColor=realtimeColorList.get(k);
						realtimeBgColor=realtimeBgColorList.get(k);
						historyColor=historyColorList.get(k);
						historyBgColor=historyBgColorList.get(k);
						
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
							realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+(realtimeCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+realtimeCurveConfObj.getColor();
						}
						if(historyCurveConfObj!=null){
							historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+(historyCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+historyCurveConfObj.getColor();
						}
						
						realtimeOverview=realtimeOverviewList.get(k);
						realtimeOverviewSort=realtimeOverviewSortList.get(k);
						realtimeData=realtimeDataList.get(k);
						
						historyOverview=historyOverviewList.get(k);
						historyOverviewSort=historyOverviewSortList.get(k);
						historyData=historyDataList.get(k);
						break;
					}
				}
			}
			result_json.append("{\"checked\":"+checked+","
					+ "\"id\":"+(index)+","
					+ "\"title\":\""+calItem.getName()+"\","
					+ "\"unit\":\""+calItem.getUnit()+"\","
					+ "\"showLevel\":\""+showLevel+"\","
					+ "\"realtimeSort\":\""+realtimeSort+"\","
					+ "\"realtimeColor\":\""+realtimeColor+"\","
					+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
					+ "\"historySort\":\""+historySort+"\","
					+ "\"historyColor\":\""+historyColor+"\","
					+ "\"historyBgColor\":\""+historyBgColor+"\","
					+ "\"realtimeCurveConf\":"+realtimeCurveConf+","
					+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
					+ "\"historyCurveConf\":"+historyCurveConf+","
					+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\","
					+ "\"code\":\""+calItem.getCode()+"\","
					+ "\"type\":3,"
					+ "\"dataSource\":\""+languageResourceMap.get("input")+"\","
					+ "\"realtimeOverview\":"+realtimeOverview+","
					+ "\"realtimeOverviewSort\":\""+realtimeOverviewSort+"\","
					+ "\"realtimeData\":"+realtimeData+","
					+ "\"historyOverview\":"+historyOverview+","
					+ "\"historyOverviewSort\":\""+historyOverviewSort+"\","
					+ "\"historyData\":"+historyData+""
					+ "},");
			index++;
		
		}
		
		//acquisition
		acqItemsList=new ArrayList<String>();
		acqItemsBitIndexList=new ArrayList<String>();
		
		itemsList=new ArrayList<String>();
		itemsCodeList=new ArrayList<String>();
		itemsRealtimeSortList=new ArrayList<String>();
		itemsHistorySortList=new ArrayList<String>();
		itemsBitIndexList=new ArrayList<String>();
		itemsShowLevelList=new ArrayList<String>();
		realtimeCurveConfList=new ArrayList<String>();
		historyCurveConfList=new ArrayList<String>();
		
		realtimeColorList=new ArrayList<String>();
		realtimeBgColorList=new ArrayList<String>();
		historyColorList=new ArrayList<String>();
		historyBgColorList=new ArrayList<String>();
		
		realtimeDataList=new ArrayList<>();
		realtimeOverviewList=new ArrayList<>();
		realtimeOverviewSortList=new ArrayList<String>();
		
		historyDataList=new ArrayList<>();
		historyOverviewList=new ArrayList<>();
		historyOverviewSortList=new ArrayList<String>();
		
		if("2".equalsIgnoreCase(classes)){
			String acqUnitIiemsSql="select distinct t.itemname,t.bitindex "
					+ "from TBL_ACQ_ITEM2GROUP_CONF t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4 "
					+ "where t.groupid=t2.id  and t2.id=t3.groupid and t3.unitid=t4.id and t4.id="+acqUnitId+" and t2.type=0";
			
			String sql="select t.itemname,t.bitindex,t.realtimeSort,t.historySort,"
					+ " t.showlevel,t.realtimeCurveConf,historyCurveConf,"
					+ " t.realtimeColor,t.realtimeBgColor,t.historyColor,t.historyBgColor, "
					+ "t.realtimeOverview,t.realtimeOverviewSort,t.realtimeData, "
					+ "t.historyOverview,t.historyOverviewSort,t.historyData "
					+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2 "
					+ " where t.unitid=t2.id and t2.id= "+unitId+" and t.type=0"
					+ " order by t.realtimeSort";
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
				itemsRealtimeSortList.add(obj[2]+"");
				itemsHistorySortList.add(obj[3]+"");
				
				itemsShowLevelList.add(obj[4]+"");
				
				String realtimeCurveConf=obj[5]+"";
				if(!StringManagerUtils.isNotNull(obj[5]+"")){
					realtimeCurveConf="\"\"";
				}
				String historyCurveConf=obj[6]+"";
				if(!StringManagerUtils.isNotNull(obj[6]+"")){
					historyCurveConf="\"\"";
				}
				
				realtimeCurveConfList.add(realtimeCurveConf);
				historyCurveConfList.add(historyCurveConf);
				
				realtimeColorList.add(obj[7]+"");
				realtimeBgColorList.add(obj[8]+"");
				historyColorList.add(obj[9]+"");
				historyBgColorList.add(obj[10]+"");
				
				realtimeOverviewList.add(StringManagerUtils.stringToInteger(obj[11]+"")==1);
				realtimeOverviewSortList.add(obj[12]+"");
				realtimeDataList.add(StringManagerUtils.stringToInteger(obj[13]+"")==1);
				
				historyOverviewList.add(StringManagerUtils.stringToInteger(obj[14]+"")==1);
				historyOverviewSortList.add(obj[15]+"");
				historyDataList.add(StringManagerUtils.stringToInteger(obj[16]+"")==1);
			}
		}
		
		if(protocolConfig!=null){
			Collections.sort(protocolConfig.getItems());
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
						String realtimeSort="";
						String historySort="";
						String showLevel="";
						String realtimeCurveConf="\"\"";
						String realtimeCurveConfShowValue="";
						String historyCurveConf="\"\"";
						String historyCurveConfShowValue="";
						String resolutionMode=languageResourceMap.get("numericValue");
						String realtimeColor=""; 
						String realtimeBgColor="";
						String historyColor="";
						String historyBgColor="";
						
						boolean realtimeOverview=false;
						String  realtimeOverviewSort="";
						boolean realtimeData=false;
						
						boolean historyOverview=false;
						String  historyOverviewSort="";
						boolean historyData=false;
						
						if(protocolConfig.getItems().get(j).getResolutionMode()==0){
							resolutionMode=languageResourceMap.get("switchingValue");
						}else if(protocolConfig.getItems().get(j).getResolutionMode()==1){
							resolutionMode=languageResourceMap.get("enumValue");
						}
						String RWType=languageResourceMap.get("readOnly");
						if("r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
							RWType=languageResourceMap.get("readOnly");
						}else if("w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
							RWType=languageResourceMap.get("writeOnly");
						}else if("rw".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
							RWType=languageResourceMap.get("readWrite");
						}
						if(protocolConfig.getItems().get(j).getResolutionMode()==0
								&&protocolConfig.getItems().get(j).getMeaning()!=null
								&&protocolConfig.getItems().get(j).getMeaning().size()>0){
							Collections.sort(protocolConfig.getItems().get(j).getMeaning());//排序
							for(int k=0;k<protocolConfig.getItems().get(j).getMeaning().size();k++){
								for(int i=0;i<acqItemsList.size();i++){
									if(acqItemsList.get(i).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())
											&& acqItemsBitIndexList.get(i).equalsIgnoreCase(protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"")){
										checked=false;
										realtimeSort="";
										historySort="";
										showLevel="";
										realtimeCurveConf="\"\"";
										realtimeCurveConfShowValue="";
										historyCurveConf="\"\"";
										historyCurveConfShowValue="";
										
										realtimeColor=""; 
										realtimeBgColor="";
										historyColor="";
										historyBgColor="";
										
										realtimeOverview=false;
										realtimeOverviewSort="";
										realtimeData=false;
										
										historyOverview=false;
										historyOverviewSort="";
										historyData=false;
										
										for(int m=0;m<itemsList.size();m++){
											if(itemsList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())
													&&itemsBitIndexList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"")
												){
												checked=true;
												realtimeSort=itemsRealtimeSortList.get(m);
												historySort=itemsHistorySortList.get(m);
												showLevel=itemsShowLevelList.get(m);
												realtimeCurveConf=realtimeCurveConfList.get(m);
												historyCurveConf=historyCurveConfList.get(m);
												
												realtimeColor=realtimeColorList.get(m);
												realtimeBgColor=realtimeBgColorList.get(m);
												historyColor=historyColorList.get(m);
												historyBgColor=historyBgColorList.get(m);
												
												
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
													realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+(realtimeCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+realtimeCurveConfObj.getColor();
												}
												if(historyCurveConfObj!=null){
													historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+(historyCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+historyCurveConfObj.getColor();
												}
												
												realtimeOverview=realtimeOverviewList.get(k);
												realtimeOverviewSort=realtimeOverviewSortList.get(k);
												realtimeData=realtimeDataList.get(k);
												
												historyOverview=historyOverviewList.get(k);
												historyOverviewSort=historyOverviewSortList.get(k);
												historyData=historyDataList.get(k);
												break;
											}
										}
										
										result_json.append("{"
												+ "\"checked\":"+checked+","
												+ "\"id\":"+(index)+","
												+ "\"title\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
												+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+","
												+ "\"bitIndex\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"\","
												+ "\"RWType\":\""+RWType+"\","
												+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
												+ "\"resolutionMode\":\""+resolutionMode+"\","
												+ "\"showLevel\":\""+showLevel+"\","
												+ "\"realtimeSort\":\""+realtimeSort+"\","
												+ "\"realtimeColor\":\""+realtimeColor+"\","
												+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
												+ "\"historySort\":\""+historySort+"\","
												+ "\"historyColor\":\""+historyColor+"\","
												+ "\"historyBgColor\":\""+historyBgColor+"\","
												+ "\"realtimeCurveConf\":"+realtimeCurveConf+","
												+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
												+ "\"historyCurveConf\":"+historyCurveConf+","
												+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\","
												+ "\"type\":0,"
												+ "\"dataSource\":\""+languageResourceMap.get("acquisition")+"\","
												+ "\"realtimeOverview\":"+realtimeOverview+","
												+ "\"realtimeOverviewSort\":\""+realtimeOverviewSort+"\","
												+ "\"realtimeData\":"+realtimeData+","
												+ "\"historyOverview\":"+historyOverview+","
												+ "\"historyOverviewSort\":\""+historyOverviewSort+"\","
												+ "\"historyData\":"+historyData+""
												+ "},");
										index++;
										break;
									}
								}
							}
						}else{
							checked=StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(),false);
							if(checked){
								for(int k=0;k<itemsList.size();k++){
									if(itemsList.get(k).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())){
										realtimeSort=itemsRealtimeSortList.get(k);
										historySort=itemsHistorySortList.get(k);
										showLevel=itemsShowLevelList.get(k);
										
										realtimeCurveConf=realtimeCurveConfList.get(k);
										historyCurveConf=historyCurveConfList.get(k);
										
										realtimeColor=realtimeColorList.get(k);
										realtimeBgColor=realtimeBgColorList.get(k);
										historyColor=historyColorList.get(k);
										historyBgColor=historyBgColorList.get(k);
										
										realtimeOverview=realtimeOverviewList.get(k);
										realtimeOverviewSort=realtimeOverviewSortList.get(k);
										realtimeData=realtimeDataList.get(k);
										
										historyOverview=historyOverviewList.get(k);
										historyOverviewSort=historyOverviewSortList.get(k);
										historyData=historyDataList.get(k);
										
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
											realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+(realtimeCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+realtimeCurveConfObj.getColor();
										}
										if(historyCurveConfObj!=null){
											historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+(historyCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+historyCurveConfObj.getColor();
										}
										break;
									}
								}
							}
							result_json.append("{\"checked\":"+checked+","
									+ "\"id\":"+(index)+","
									+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
									+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+","
									+ "\"bitIndex\":\"\","
									+ "\"RWType\":\""+RWType+"\","
									+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
									+ "\"resolutionMode\":\""+resolutionMode+"\","
									+ "\"showLevel\":\""+showLevel+"\","
									+ "\"realtimeSort\":\""+realtimeSort+"\","
									+ "\"realtimeColor\":\""+realtimeColor+"\","
									+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
									+ "\"historySort\":\""+historySort+"\","
									+ "\"historyColor\":\""+historyColor+"\","
									+ "\"historyBgColor\":\""+historyBgColor+"\","
									+ "\"realtimeCurveConf\":"+realtimeCurveConf+","
									+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
									+ "\"historyCurveConf\":"+historyCurveConf+","
									+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\","
									+ "\"type\":0,"
									+ "\"dataSource\":\""+languageResourceMap.get("acquisition")+"\","
									+ "\"realtimeOverview\":"+realtimeOverview+","
									+ "\"realtimeOverviewSort\":\""+realtimeOverviewSort+"\","
									+ "\"realtimeData\":"+realtimeData+","
									+ "\"historyOverview\":"+historyOverview+","
									+ "\"historyOverviewSort\":\""+historyOverviewSort+"\","
									+ "\"historyData\":"+historyData+""
									+ "},");
							index++;
						}
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
	
	public String getProtocolDisplayUnitCtrlItemsConfigData(String protocolName,String classes,String code,String unitId,String acqUnitId,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> acqItemsList=new ArrayList<String>();
		List<String> acqItemsBitIndexList=new ArrayList<String>();
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsRealtimeSortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		if("2".equalsIgnoreCase(classes)){
			String acqUnitIiemsSql="select distinct t.itemname,t.bitindex "
					+ "from TBL_ACQ_ITEM2GROUP_CONF t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4 "
					+ "where t.groupid=t2.id  and t2.id=t3.groupid and t3.unitid=t4.id and t4.id="+acqUnitId+" and t2.type=1";
			String sql="select t.itemname,t.bitindex,t.realtimeSort,t.showlevel "
					+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2 "
					+ " where t.unitid=t2.id and t2.id= "+unitId+" and t.type=2"
					+ " order by t.realtimeSort";
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
				itemsRealtimeSortList.add(obj[2]+"");
				itemsShowLevelList.add(obj[3]+"");
			}
		}

		ModbusProtocolConfig.Protocol protocolConfig=MemoryDataManagerTask.getProtocolByName(protocolName);
		if(protocolConfig!=null){
//			Collections.sort(protocolConfig.getItems());
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
						String realtimeSort="";
						String showLevel="";
						String resolutionMode=languageResourceMap.get("numericValue");
						if(protocolConfig.getItems().get(j).getResolutionMode()==0){
							resolutionMode=languageResourceMap.get("switchingValue");
						}else if(protocolConfig.getItems().get(j).getResolutionMode()==1){
							resolutionMode=languageResourceMap.get("enumValue");
						}
						String RWType=languageResourceMap.get("readOnly");
						if("r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
							RWType=languageResourceMap.get("readOnly");
						}else if("w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
							RWType=languageResourceMap.get("writeOnly");
						}else if("rw".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
							RWType=languageResourceMap.get("readWrite");
						}
						if(protocolConfig.getItems().get(j).getResolutionMode()==0
								&&protocolConfig.getItems().get(j).getMeaning()!=null
								&&protocolConfig.getItems().get(j).getMeaning().size()>0){
							Collections.sort(protocolConfig.getItems().get(j).getMeaning());//排序
							for(int k=0;k<protocolConfig.getItems().get(j).getMeaning().size();k++){
								checked=false;
								realtimeSort="";
								showLevel="";
								for(int m=0;m<itemsList.size();m++){
									if(itemsList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())
											&&itemsBitIndexList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"")
										){
										checked=true;
										realtimeSort=itemsRealtimeSortList.get(m);
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
										+ "\"realtimeSort\":\""+realtimeSort+"\""
										+ "},");
								index++;
							}
						}else{
							checked=StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(),false);
							if(checked){
								for(int k=0;k<itemsList.size();k++){
									if(itemsList.get(k).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())){
										realtimeSort=itemsRealtimeSortList.get(k);
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
									+ "\"realtimeSort\":\""+realtimeSort+"\""
									+ "},");
							index++;
						}
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
	
	public String getProtocolDisplayUnitCalItemsConfigData(String classes,String unitId,String calculateType,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		List<CalItem> calItemList=new ArrayList<>();
		try{
			if("1".equalsIgnoreCase(calculateType)){
				calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
			}else if("2".equalsIgnoreCase(calculateType)){
				calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
			}else{
				calItemList=MemoryDataManagerTask.getAcqCalculateItem(language);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurveConf\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveConf\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsCodeList=new ArrayList<String>();
		List<String> itemsRealtimeSortList=new ArrayList<String>();
		List<String> itemsHistorySortList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveConfList=new ArrayList<String>();
		List<String> historyCurveConfList=new ArrayList<String>();
		
		List<String> realtimeColorList = new ArrayList<String>();
	    List<String> realtimeBgColorList = new ArrayList<String>();
	    List<String> historyColorList = new ArrayList<String>();
	    List<String> historyBgColorList = new ArrayList<String>();
		
		if("2".equalsIgnoreCase(classes) && StringManagerUtils.isNotNull(unitId)){
			ModbusProtocolConfig.Protocol protocol=null;
			String protocolSql="select t.protocol from TBL_ACQ_UNIT_CONF t,tbl_display_unit_conf t2 where t.id=t2.acqunitid and t2.id="+unitId;
			List<?> protocolList=this.findCallSql(protocolSql);
			if(protocolList.size()>0){
				protocol=MemoryDataManagerTask.getProtocolByName(protocolList.get(0)+"");
			}
			String dailyTotalItemsSql="select t.itemname,t.dailytotalcalculatename,t6.mappingcolumn "
					+ " from TBL_ACQ_ITEM2GROUP_CONF t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4,tbl_display_unit_conf t5,"
					+ " tbl_datamapping t6 "
					+ " where t.groupid=t2.id and t2.id=t3.groupid and t3.unitid=t4.id and t4.id=t5.acqunitid "
					+ " and t.itemname=t6.name and t.dailytotalcalculate=1 and t5.id="+unitId
					+ " order by t.id";
			List<?> unitDailyTotalItemsList=this.findCallSql(dailyTotalItemsSql);
			for(int i=0;i<unitDailyTotalItemsList.size();i++){
				Object[] obj=(Object[])unitDailyTotalItemsList.get(i);
				String itemName=obj[0]+"";
				String name=obj[1]+"";
				String code=(obj[2]+"_TOTAL").toUpperCase();
				String unit="";
				if(protocol!=null&&protocol.getItems()!=null){
					for(ModbusProtocolConfig.Items item:protocol.getItems()){
						if(itemName.equalsIgnoreCase(item.getTitle())){
							unit=item.getUnit();
							break;
						}
					}
				}
				CalItem calItem=new CalItem(name,code,unit,2,languageResourceMap.get("configuration"),itemName+languageResourceMap.get("dailyCalculate"));
				calItemList.add(calItem);
			}
		}
		
		if("2".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.realtimeSort,t.historySort,"
					+ "t.showlevel,t.realtimeCurveConf,t.historyCurveConf,"
					+ "t.realtimeColor,t.realtimeBgColor,t.historyColor,t.historyBgColor "
					+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2 "
					+ " where t.unitid=t2.id and t2.id= "+unitId+" and t.type=1"
					+ " order by t.realtimeSort";
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsCodeList.add(obj[1]+"");
				itemsRealtimeSortList.add(obj[2]+"");
				itemsHistorySortList.add(obj[3]+"");
				
				itemsShowLevelList.add(obj[4]+"");
				String realtimeCurveConf=obj[5]+"";
				if(!StringManagerUtils.isNotNull(obj[5]+"")){
					realtimeCurveConf="\"\"";
				}
				String historyCurveConf=obj[6]+"";
				if(!StringManagerUtils.isNotNull(obj[6]+"")){
					historyCurveConf="\"\"";
				}
				
				realtimeCurveConfList.add(realtimeCurveConf);
				historyCurveConfList.add(historyCurveConf);
				
				realtimeColorList.add(obj[7]+"");
				realtimeBgColorList.add(obj[8]+"");
				historyColorList.add(obj[9]+"");
				historyBgColorList.add(obj[10]+"");
			}
		}
		
		int index=1;

		for(CalItem calItem:calItemList){
			boolean checked=false;
			String realtimeSort="";
			String historySort="";
			String showLevel="";
			String realtimeCurveConf="\"\"";
			String realtimeCurveConfShowValue="";
			String historyCurveConf="\"\"";
			String historyCurveConfShowValue="";
			String realtimeColor=""; 
			String realtimeBgColor="";
			String historyColor="";
			String historyBgColor="";

			checked=StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(),false);
			if(checked){
				for(int k=0;k<itemsList.size();k++){
					if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
						realtimeSort=itemsRealtimeSortList.get(k);
						historySort=itemsHistorySortList.get(k);
						showLevel=itemsShowLevelList.get(k);
						realtimeCurveConf=realtimeCurveConfList.get(k);
						historyCurveConf=historyCurveConfList.get(k);
						
						realtimeColor=realtimeColorList.get(k);
						realtimeBgColor=realtimeBgColorList.get(k);
						historyColor=historyColorList.get(k);
						historyBgColor=historyBgColorList.get(k);
						
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
							realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+(realtimeCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+realtimeCurveConfObj.getColor();
						}
						if(historyCurveConfObj!=null){
							historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+(historyCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+historyCurveConfObj.getColor();
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
					+ "\"realtimeSort\":\""+realtimeSort+"\","
					+ "\"realtimeColor\":\""+realtimeColor+"\","
					+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
					+ "\"historySort\":\""+historySort+"\","
					+ "\"historyColor\":\""+historyColor+"\","
					+ "\"historyBgColor\":\""+historyBgColor+"\","
					+ "\"realtimeCurveConf\":"+realtimeCurveConf+","
					+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
					+ "\"historyCurveConf\":"+historyCurveConf+","
					+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\","
					+ "\"code\":\""+calItem.getCode()+"\","
					+ "\"dataSource\":\""+calItem.getDataSource()+"\""
					+ "},");
			index++;
		
		}
	
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolDisplayUnitInputItemsConfigData(String deviceType,String classes,String unitId,String calculateType,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		
		List<CalItem> inputItemList=new ArrayList<>();
		try{
			if("1".equalsIgnoreCase(calculateType)){
				inputItemList=MemoryDataManagerTask.getSRPInputItem(language);
			}else if("2".equalsIgnoreCase(calculateType)){
				inputItemList=MemoryDataManagerTask.getPCPInputItem(language);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurveConf\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveConf\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsCodeList=new ArrayList<String>();
		List<String> itemsRealtimeSortList=new ArrayList<String>();
		List<String> itemsHistorySortList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveConfList=new ArrayList<String>();
		List<String> historyCurveConfList=new ArrayList<String>();
		List<String> realtimeColorList=new ArrayList<String>();
		List<String> realtimeBgColorList=new ArrayList<String>();
		List<String> historyColorList=new ArrayList<String>();
		List<String> historyBgColorList=new ArrayList<String>();
		if("2".equalsIgnoreCase(classes)){
			String sql="select t.itemname,t.itemcode,t.realtimeSort,t.historySort,"
					+ "t.showlevel,t.realtimeCurveConf,t.historyCurveConf,"
					+ "t.realtimeColor,t.realtimeBgColor,t.historyColor,t.historyBgColor "
					+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2 "
					+ " where t.unitid=t2.id and t2.id= "+unitId+" and t.type=3"
					+ " order by t.realtimeSort";
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsCodeList.add(obj[1]+"");
				itemsRealtimeSortList.add(obj[2]+"");
				itemsHistorySortList.add(obj[3]+"");
				itemsShowLevelList.add(obj[4]+"");
				String realtimeCurveConf=obj[5]+"";
				if(!StringManagerUtils.isNotNull(obj[5]+"")){
					realtimeCurveConf="\"\"";
				}
				String historyCurveConf=obj[6]+"";
				if(!StringManagerUtils.isNotNull(obj[6]+"")){
					historyCurveConf="\"\"";
				}
				
				realtimeCurveConfList.add(realtimeCurveConf);
				historyCurveConfList.add(historyCurveConf);
				
				realtimeColorList.add(obj[7]+"");
				realtimeBgColorList.add(obj[8]+"");
				historyColorList.add(obj[9]+"");
				historyBgColorList.add(obj[10]+"");
			}
		}
		
		int index=1;

		for(CalItem calItem:inputItemList){
			boolean checked=false;
			String realtimeSort="";
			String historySort="";
			String showLevel="";
			String realtimeCurveConf="\"\"";
			String realtimeCurveConfShowValue="";
			String historyCurveConf="\"\"";
			String historyCurveConfShowValue="";
			String realtimeColor=""; 
			String realtimeBgColor="";
			String historyColor="";
			String historyBgColor="";

			checked=StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(),false);
			if(checked){
				for(int k=0;k<itemsList.size();k++){
					if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
						realtimeSort=itemsRealtimeSortList.get(k);
						historySort=itemsHistorySortList.get(k);
						showLevel=itemsShowLevelList.get(k);
						realtimeCurveConf=realtimeCurveConfList.get(k);
						historyCurveConf=historyCurveConfList.get(k);
						realtimeColor=realtimeColorList.get(k);
						realtimeBgColor=realtimeBgColorList.get(k);
						historyColor=historyColorList.get(k);
						historyBgColor=historyBgColorList.get(k);
						
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
							realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+(realtimeCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+realtimeCurveConfObj.getColor();
						}
						if(historyCurveConfObj!=null){
							historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+(historyCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+historyCurveConfObj.getColor();
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
					+ "\"realtimeSort\":\""+realtimeSort+"\","
					+ "\"realtimeColor\":\""+realtimeColor+"\","
					+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
					+ "\"historySort\":\""+historySort+"\","
					+ "\"historyColor\":\""+historyColor+"\","
					+ "\"historyBgColor\":\""+historyBgColor+"\","
					+ "\"realtimeCurveConf\":"+realtimeCurveConf+","
					+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
					+ "\"historyCurveConf\":"+historyCurveConf+","
					+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\","
					+ "\"code\":\""+calItem.getCode()+"\""
					+ "},");
			index++;
		
		}
	
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getReportUnitTotalCalItemsConfigData(String calculateType,String reportType,String templateCode,String unitId,String classes,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer rawData = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		ReportTemplate reportTemplate=MemoryDataManagerTask.getReportTemplateConfig();
		ReportTemplate.Template template=null;
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
					if(templateCode.equalsIgnoreCase(templateList.get(i).getTemplateCode()) ){
						template=templateList.get(i);
						break;
					}
				}
			}
		}
		
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalRoot\":[");
		rawData.append("[");
		if(template!=null && (template.getHeader().size()>0 || template.getColumnWidths_zh_CN().size()>0 || template.getColumnWidths_en().size()>0 || template.getColumnWidths_ru().size()>0  )   ){
			int columnCoumnt=template.getColumnWidths_zh_CN().size();
			if("zh_CN".equalsIgnoreCase(language)){
				columnCoumnt=template.getColumnWidths_zh_CN().size();
			}else if("en".equalsIgnoreCase(language)){
				columnCoumnt=template.getColumnWidths_en().size();
			}else if("ru".equalsIgnoreCase(language)){
				columnCoumnt=template.getColumnWidths_ru().size();
			}
			
			if(template.getHeader().size()>0){
				if("zh_CN".equalsIgnoreCase(language)){
					columnCoumnt=template.getHeader().get(template.getHeader().size()-1).getTitle_zh_CN().size();
				}else if("en".equalsIgnoreCase(language)){
					columnCoumnt=template.getHeader().get(template.getHeader().size()-1).getTitle_en().size();
				}else if("ru".equalsIgnoreCase(language)){
					columnCoumnt=template.getHeader().get(template.getHeader().size()-1).getTitle_ru().size();
				}
			}
			
			List<String> itemsList=new ArrayList<String>();
			List<String> itemsCodeList=new ArrayList<String>();
			List<String> itemsSortList=new ArrayList<String>();
			List<String> itemsShowLevelList=new ArrayList<String>();
			
			List<String> itemsPrecList=new ArrayList<String>();
			
			List<String> sumSignList=new ArrayList<String>();
			List<String> averageSignList=new ArrayList<String>();
			
			List<String> reportCurveConfList=new ArrayList<String>();
			
			List<String> curveStatTypeList=new ArrayList<String>();
			
			List<String> itemsTotalTypeList=new ArrayList<String>();
			
			List<String> dataSourceList=new ArrayList<String>();
			
			List<String> dataTypeList=new ArrayList<String>();
			
			if("1".equalsIgnoreCase(classes)){
				String sql="select t.itemname,t.itemcode,t.sort,t.showlevel,t.sumsign,t.averagesign,t.reportCurveconf,t.curvestattype,t.prec,"
						+ " decode(t.totalType,1,'"+languageResourceMap.get("maxValue")+"',2,'"+languageResourceMap.get("minValue")+"',3,'"+languageResourceMap.get("avgValue")+"',4,'"+languageResourceMap.get("newestValue")+"',5,'"+languageResourceMap.get("oldestValue")+"',6,'"+languageResourceMap.get("dailyTotalValue")+"',''),"
						+ " t.dataSource,t.dataType "
						+ " from tbl_report_items2unit_conf t "
						+ " where t.sort>0 "
						+ " and t.unitid="+unitId+" "
						+ " and t.reportType="+reportType
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
					itemsTotalTypeList.add(obj[9]+"");
					dataSourceList.add(obj[10]+"");
					dataTypeList.add(obj[11]+"");
				}
			}
			int index=1;
			for(int i=0;i<columnCoumnt;i++){
				String headerName="";
				if(template.getHeader().size()>0){
					if("zh_CN".equalsIgnoreCase(language)){
						headerName=template.getHeader().get(template.getHeader().size()-1).getTitle_zh_CN().get(i);
						if( (!StringManagerUtils.isNotNull(headerName)) && template.getHeader().size()>=2 &&  template.getHeader().get(template.getHeader().size()-2).getTitle_zh_CN().size()>=i ){
							headerName=template.getHeader().get(template.getHeader().size()-2).getTitle_zh_CN().get(i);
						}
					}else if("en".equalsIgnoreCase(language)){
						headerName=template.getHeader().get(template.getHeader().size()-1).getTitle_en().get(i);
						if( (!StringManagerUtils.isNotNull(headerName)) && template.getHeader().size()>=2 &&  template.getHeader().get(template.getHeader().size()-2).getTitle_en().size()>=i ){
							headerName=template.getHeader().get(template.getHeader().size()-2).getTitle_en().get(i);
						}
					}else if("ru".equalsIgnoreCase(language)){
						headerName=template.getHeader().get(template.getHeader().size()-1).getTitle_ru().get(i);
						if( (!StringManagerUtils.isNotNull(headerName)) && template.getHeader().size()>=2 &&  template.getHeader().get(template.getHeader().size()-2).getTitle_ru().size()>=i ){
							headerName=template.getHeader().get(template.getHeader().size()-2).getTitle_ru().get(i);
						}
					}
				}
				
				String itemName="";
				String itemCode="";
				
				String unit="";
				String dataSource="";
				
				String totalType="";
				
				String sort="";
				String showLevel="";
				
				String prec="";
				
				boolean sumSign=false;
				boolean averageSign=false;
				
				String reportCurveConf="\"\"";
				String reportCurveConfShowValue="";
				
				String curveStatType="";
				
				String dataType="";
				
				String action="config";
				
				for(int k=0;k<itemsList.size();k++){
					if(StringManagerUtils.stringToInteger(itemsSortList.get(k))==index){
						itemName=itemsList.get(k);
						itemCode=itemsCodeList.get(k);
						
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
							reportCurveConfShowValue=reportCurveConfObj.getSort()+";"+(reportCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+reportCurveConfObj.getColor();
						}
						
						
						String curveStatTypeStr=curveStatTypeList.get(k).replaceAll("null", "");
						if(StringManagerUtils.isNum(curveStatTypeStr) || StringManagerUtils.isNumber(curveStatTypeStr)){
							if(StringManagerUtils.stringToInteger(curveStatTypeStr)==1){
								curveStatType=languageResourceMap.get("curveStatType_sum");
							}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==2){
								curveStatType=languageResourceMap.get("curveStatType_avg");
							}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==3){
								curveStatType=languageResourceMap.get("curveStatType_max");
							}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==4){
								curveStatType=languageResourceMap.get("curveStatType_min");
							}
						}
						
						dataSource=dataSourceList.get(k);
						totalType=itemsTotalTypeList.get(k);
						dataType=dataTypeList.get(k);
						break;
					}
				}
				
				result_json.append("{"
						+ "\"id\":"+(index)+","
						+ "\"headerName\":\""+headerName+"\","
						+ "\"itemName\":\""+itemName+"\","
						+ "\"unit\":\""+unit+"\","
						+ "\"dataSource\":\""+dataSource+"\","
						+ "\"totalType\":\""+totalType+"\","
						+ "\"showLevel\":\""+showLevel+"\","
						+ "\"sort\":\""+sort+"\","
						+ "\"prec\":\""+prec+"\","
						+ "\"sumSign\":"+sumSign+","
						+ "\"averageSign\":"+averageSign+","
						+ "\"reportCurveConfShowValue\":\""+reportCurveConfShowValue+"\","
						+ "\"reportCurveConf\":"+reportCurveConf+","
						+ "\"curveStatType\":\""+curveStatType+"\","
						+ "\"dataType\":\""+dataType+"\","
						+ "\"itemCode\":\""+itemCode+"\","
						+ "\"remark\":\"\","
						+ "\"action\":\""+action+"\","
						+ "\"dataChange\":0"
						+ "},");
				
				rawData.append("{"
						+ "\"id\":"+(index)+","
						+ "\"headerName\":\""+headerName+"\","
						+ "\"itemName\":\""+itemName+"\","
						+ "\"unit\":\""+unit+"\","
						+ "\"dataSource\":\""+dataSource+"\","
						+ "\"totalType\":\""+totalType+"\","
						+ "\"showLevel\":\""+showLevel+"\","
						+ "\"sort\":\""+sort+"\","
						+ "\"prec\":\""+prec+"\","
						+ "\"sumSign\":"+sumSign+","
						+ "\"averageSign\":"+averageSign+","
						+ "\"reportCurveConfShowValue\":\""+reportCurveConfShowValue+"\","
						+ "\"reportCurveConf\":"+reportCurveConf+","
						+ "\"curveStatType\":\""+curveStatType+"\","
						+ "\"dataType\":\""+dataType+"\","
						+ "\"itemCode\":\""+itemCode+"\","
						+ "\"remark\":\"\","
						+ "\"action\":\""+action+"\","
						+ "\"dataChange\":0"
						+ "},");
				index++;
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		if(rawData.toString().endsWith(",")){
			rawData.deleteCharAt(rawData.length() - 1);
		}
		result_json.append("]");
		rawData.append("]");
		result_json.append(",\"rawData\":"+rawData.toString());
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getReportUnitTotalItemsConfigColInfoData(String calculateType,String reportType,String templateCode,String unitId,String classes,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		ReportTemplate reportTemplate=MemoryDataManagerTask.getReportTemplateConfig();
		ReportTemplate.Template template=null;
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
					if(templateCode.equalsIgnoreCase(templateList.get(i).getTemplateCode()) ){
						template=templateList.get(i);
						break;
					}
				}
			}
		}
		
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalRoot\":[");
		
		if(template!=null && (template.getHeader().size()>0 || template.getColumnWidths_zh_CN().size()>0 || template.getColumnWidths_en().size()>0 || template.getColumnWidths_ru().size()>0  )   ){
			int columnCoumnt=template.getColumnWidths_zh_CN().size();
			if("zh_CN".equalsIgnoreCase(language)){
				columnCoumnt=template.getColumnWidths_zh_CN().size();
			}else if("en".equalsIgnoreCase(language)){
				columnCoumnt=template.getColumnWidths_en().size();
			}else if("ru".equalsIgnoreCase(language)){
				columnCoumnt=template.getColumnWidths_ru().size();
			}
			if(template.getHeader().size()>0){
				if("zh_CN".equalsIgnoreCase(language)){
					columnCoumnt=template.getHeader().get(template.getHeader().size()-1).getTitle_zh_CN().size();
				}else if("en".equalsIgnoreCase(language)){
					columnCoumnt=template.getHeader().get(template.getHeader().size()-1).getTitle_en().size();
				}else if("ru".equalsIgnoreCase(language)){
					columnCoumnt=template.getHeader().get(template.getHeader().size()-1).getTitle_ru().size();
				}
			}
			
			List<String> itemsList=new ArrayList<String>();
			List<String> itemsCodeList=new ArrayList<String>();
			List<String> itemsSortList=new ArrayList<String>();
			List<String> itemsShowLevelList=new ArrayList<String>();
			
			List<String> itemsPrecList=new ArrayList<String>();
			
			List<String> sumSignList=new ArrayList<String>();
			List<String> averageSignList=new ArrayList<String>();
			
			List<String> reportCurveConfList=new ArrayList<String>();
			
			List<String> curveStatTypeList=new ArrayList<String>();
			
			List<String> itemsTotalTypeList=new ArrayList<String>();
			
			List<String> dataSourceList=new ArrayList<String>();
			
			List<String> dataTypeList=new ArrayList<String>();
			
			if("1".equalsIgnoreCase(classes)){
				String sql="select t.itemname,t.itemcode,t.sort,t.showlevel,t.sumsign,t.averagesign,t.reportCurveconf,t.curvestattype,t.prec,"
						+ " decode(t.totalType,1,'"+languageResourceMap.get("maxValue")+"',2,'"+languageResourceMap.get("minValue")+"',3,'"+languageResourceMap.get("avgValue")+"',4,'"+languageResourceMap.get("newestValue")+"',5,'"+languageResourceMap.get("oldestValue")+"',6,'"+languageResourceMap.get("dailyTotalValue")+"',''),"
						+ " t.dataSource,t.dataType "
						+ " from tbl_report_items2unit_conf t "
						+ " where t.sort>0 "
						+ " and t.unitid="+unitId+" "
						+ " and t.reportType="+reportType
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
					itemsTotalTypeList.add(obj[9]+"");
					dataSourceList.add(obj[10]+"");
					dataTypeList.add(obj[11]+"");
				}
			}
			int index=1;
			for(int i=0;i<columnCoumnt;i++){
				String headerName="";
				if(template.getHeader().size()>0){
					if("zh_CN".equalsIgnoreCase(language)){
						headerName=template.getHeader().get(template.getHeader().size()-1).getTitle_zh_CN().get(i);
						if( (!StringManagerUtils.isNotNull(headerName)) && template.getHeader().size()>=2 &&  template.getHeader().get(template.getHeader().size()-2).getTitle_zh_CN().size()>=i ){
							headerName=template.getHeader().get(template.getHeader().size()-2).getTitle_zh_CN().get(i);
						}
					}else if("en".equalsIgnoreCase(language)){
						headerName=template.getHeader().get(template.getHeader().size()-1).getTitle_en().get(i);
						if( (!StringManagerUtils.isNotNull(headerName)) && template.getHeader().size()>=2 &&  template.getHeader().get(template.getHeader().size()-2).getTitle_en().size()>=i ){
							headerName=template.getHeader().get(template.getHeader().size()-2).getTitle_en().get(i);
						}
					}else if("ru".equalsIgnoreCase(language)){
						headerName=template.getHeader().get(template.getHeader().size()-1).getTitle_ru().get(i);
						if( (!StringManagerUtils.isNotNull(headerName)) && template.getHeader().size()>=2 &&  template.getHeader().get(template.getHeader().size()-2).getTitle_ru().size()>=i ){
							headerName=template.getHeader().get(template.getHeader().size()-2).getTitle_ru().get(i);
						}
					}
				}
				
				String itemName="";
				String itemCode="";
				
				String unit="";
				String dataSource="";
				
				String totalType="";
				
				String sort="";
				String showLevel="";
				
				String prec="";
				
				boolean sumSign=false;
				boolean averageSign=false;
				
				String reportCurveConf="\"\"";
				String reportCurveConfShowValue="";
				
				String curveStatType="";
				
				String dataType="";
				
				String action="config";
				
				for(int k=0;k<itemsList.size();k++){
					if(StringManagerUtils.stringToInteger(itemsSortList.get(k))==index){
						itemName=itemsList.get(k);
						itemCode=itemsCodeList.get(k);
						
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
							reportCurveConfShowValue=reportCurveConfObj.getSort()+";"+(reportCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+reportCurveConfObj.getColor();
						}
						
						
						String curveStatTypeStr=curveStatTypeList.get(k).replaceAll("null", "");
						if(StringManagerUtils.isNum(curveStatTypeStr) || StringManagerUtils.isNumber(curveStatTypeStr)){
							if(StringManagerUtils.stringToInteger(curveStatTypeStr)==1){
								curveStatType=languageResourceMap.get("curveStatType_sum");
							}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==2){
								curveStatType=languageResourceMap.get("curveStatType_avg");
							}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==3){
								curveStatType=languageResourceMap.get("curveStatType_max");
							}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==4){
								curveStatType=languageResourceMap.get("curveStatType_min");
							}
						}
						
						dataSource=dataSourceList.get(k);
						totalType=itemsTotalTypeList.get(k);
						dataType=dataTypeList.get(k);
						break;
					}
				}
				
				result_json.append("{"
						+ "\"id\":"+(index)+","
						+ "\"headerName\":\""+headerName+"\","
						+ "\"itemName\":\""+itemName+"\","
						+ "\"unit\":\""+unit+"\","
						+ "\"dataSource\":\""+dataSource+"\","
						+ "\"totalType\":\""+totalType+"\","
						+ "\"showLevel\":\""+showLevel+"\","
						+ "\"sort\":\""+sort+"\","
						+ "\"prec\":\""+prec+"\","
						+ "\"sumSign\":"+sumSign+","
						+ "\"averageSign\":"+averageSign+","
						+ "\"reportCurveConfShowValue\":\""+reportCurveConfShowValue+"\","
						+ "\"reportCurveConf\":"+reportCurveConf+","
						+ "\"curveStatType\":\""+curveStatType+"\","
						+ "\"dataType\":\""+dataType+"\","
						+ "\"itemCode\":\""+itemCode+"\","
						+ "\"remark\":\"\","
						+ "\"action\":\""+action+"\""
						+ "},");
				index++;
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getReportUnitContentConfigItemsData(String unitId,String calculateType,String reportType,int sort,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
		
		List<CalItem> totalItemList=null;
		try{

			if(StringManagerUtils.stringToInteger(reportType)==2){
				if("1".equalsIgnoreCase(calculateType)){
					totalItemList=MemoryDataManagerTask.getSRPTimingTotalCalculateItem(language);
				}else if("2".equalsIgnoreCase(calculateType)){
					totalItemList=MemoryDataManagerTask.getPCPTimingTotalCalculateItem(language);
				}else{
					totalItemList=MemoryDataManagerTask.getAcqTimingTotalCalculateItem(language);
				}
			}else{
				if("1".equalsIgnoreCase(calculateType)){
					totalItemList=MemoryDataManagerTask.getSRPTotalCalculateItem(language);
				}else if("2".equalsIgnoreCase(calculateType)){
					totalItemList=MemoryDataManagerTask.getPCPTotalCalculateItem(language);
				}else{
					totalItemList=MemoryDataManagerTask.getAcqTotalCalculateItem(language);
				}
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("prec")+"\",\"dataIndex\":\"prec\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("sumSign")+"\",\"dataIndex\":\"sumSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("averageSign")+"\",\"dataIndex\":\"averageSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("reportCurve")+"\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("curveStatType")+"\",\"dataIndex\":\"curveStatType\",width:80 ,children:[] }"
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
		
		List<String> itemsTotalTypeList=new ArrayList<String>();
		if(sort>0 && StringManagerUtils.stringToInteger(unitId)>0){
			String sql="select t.itemname,t.itemcode,t.sort,t.showlevel,t.sumsign,t.averagesign,t.reportCurveconf,t.curvestattype,t.prec,"
					+ " decode(t.totalType,1,'"+languageResourceMap.get("maxValue")+"',2,'"+languageResourceMap.get("minValue")+"',3,'"+languageResourceMap.get("avgValue")+"',4,'"+languageResourceMap.get("newestValue")+"',5,'"+languageResourceMap.get("oldestValue")+"',6,'"+languageResourceMap.get("dailyTotalValue")+"','') "
					+ " from tbl_report_items2unit_conf t "
					+ " where t.unitid="+unitId+" "
					+ " and t.reportType="+reportType
					+ " and t.sort="+sort
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
				itemsTotalTypeList.add(obj[9]+"");
			}
		}
		
		int index=1;
		if(totalItemList!=null){
			for(CalItem calItem:totalItemList){
				boolean checked=false;
				String sortStr="";
				String showLevel="";
				String prec="";
				
				boolean sumSign=false;
				boolean averageSign=false;
				
				String reportCurveConf="\"\"";
				String reportCurveConfShowValue="";
				
				String curveStatType="";
				
				String totalType="";

				checked=StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(),false);
				if(checked){
					for(int k=0;k<itemsList.size();k++){
						if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
							sortStr=itemsSortList.get(k);
							showLevel=itemsShowLevelList.get(k);
							prec=itemsPrecList.get(k);
							totalType=itemsTotalTypeList.get(k);
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
								reportCurveConfShowValue=reportCurveConfObj.getSort()+";"+(reportCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+reportCurveConfObj.getColor();
							}
							
							
							String curveStatTypeStr=curveStatTypeList.get(k).replaceAll("null", "");
							if(StringManagerUtils.isNum(curveStatTypeStr) || StringManagerUtils.isNumber(curveStatTypeStr)){
								if(StringManagerUtils.stringToInteger(curveStatTypeStr)==1){
									curveStatType=languageResourceMap.get("curveStatType_sum");
								}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==2){
									curveStatType=languageResourceMap.get("curveStatType_avg");
								}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==3){
									curveStatType=languageResourceMap.get("curveStatType_max");
								}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==4){
									curveStatType=languageResourceMap.get("curveStatType_min");
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
						+ "\"totalType\":\""+totalType+"\","
						+ "\"dataSource\":\""+calItem.getDataSource()+"\","
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
		
		Iterator<Map.Entry<String, DataMapping>> iterator = loadProtocolMappingColumnByTitleMap.entrySet().iterator();
		while (iterator.hasNext()) {
		    Map.Entry<String, DataMapping> entry = iterator.next();
		    DataMapping dataMappingColumn = entry.getValue();
		    
		    boolean checked=false;
			String sortStr="";
			String showLevel="";
			String prec="";
			
			boolean sumSign=false;
			boolean averageSign=false;
			
			String reportCurveConf="\"\"";
			String reportCurveConfShowValue="";
			
			String curveStatType="";
			
			String totalType="";

			checked=StringManagerUtils.existOrNot(itemsCodeList, dataMappingColumn.getMappingColumn(),false);
			if(checked){
				for(int k=0;k<itemsList.size();k++){
					if(itemsCodeList.get(k).equalsIgnoreCase(dataMappingColumn.getMappingColumn())){
						sortStr=itemsSortList.get(k);
						showLevel=itemsShowLevelList.get(k);
						prec=itemsPrecList.get(k);
						totalType=itemsTotalTypeList.get(k);
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
							reportCurveConfShowValue=reportCurveConfObj.getSort()+";"+(reportCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+reportCurveConfObj.getColor();
						}
						
						
						String curveStatTypeStr=curveStatTypeList.get(k).replaceAll("null", "");
						if(StringManagerUtils.isNum(curveStatTypeStr) || StringManagerUtils.isNumber(curveStatTypeStr)){
							if(StringManagerUtils.stringToInteger(curveStatTypeStr)==1){
								curveStatType=languageResourceMap.get("curveStatType_sum");
							}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==2){
								curveStatType=languageResourceMap.get("curveStatType_avg");
							}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==3){
								curveStatType=languageResourceMap.get("curveStatType_max");
							}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==4){
								curveStatType=languageResourceMap.get("curveStatType_min");
							}
						}
						break;
					}
				}
			}
		    
		    
		    
			result_json.append("{\"checked\":"+checked+","
					+ "\"id\":"+(index)+","
					+ "\"title\":\""+dataMappingColumn.getName()+"\","
					+ "\"unit\":\"\","
					+ "\"totalType\":\""+totalType+"\","
					+ "\"dataSource\":\""+languageResourceMap.get("acquisition")+"\","
					+ "\"showLevel\":\""+showLevel+"\","
					+ "\"sort\":\""+sortStr+"\","
					+ "\"prec\":\""+prec+"\","
					+ "\"sumSign\":"+sumSign+","
					+ "\"averageSign\":"+averageSign+","
					+ "\"reportCurveConfShowValue\":\""+reportCurveConfShowValue+"\","
					+ "\"reportCurveConf\":"+reportCurveConf+","
					+ "\"curveStatType\":\""+curveStatType+"\","
					+ "\"dataType\":2,"
					+ "\"code\":\""+dataMappingColumn.getMappingColumn()+"\","
					+ "\"remark\":\"\""
					+ "},");
			index++;
		}
		
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getReportInstanceTotalCalItemsConfigData(String calculateType,String unitId,String reportType,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		List<CalItem> calItemList=null;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try{

			if("1".equalsIgnoreCase(calculateType)){
				calItemList=MemoryDataManagerTask.getSRPTotalCalculateItem(language);
			}else if("2".equalsIgnoreCase(calculateType)){
				calItemList=MemoryDataManagerTask.getPCPTotalCalculateItem(language);
			}else{
				calItemList=MemoryDataManagerTask.getAcqTotalCalculateItem(language);
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("prec")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("sumSign")+"\",\"dataIndex\":\"sumSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("averageSign")+"\",\"dataIndex\":\"averageSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"报表曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("curveStatType")+"\",\"dataIndex\":\"curveStatType\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		String sql="select t.itemname,t.itemcode,t.sort,t.showlevel,t.sumsign,t.averagesign,t.reportCurveConf,t.curvestattype,t.prec,"
				+ "t.dataSource, decode(t.totalType,1,'"+languageResourceMap.get("maxValue")+"',2,'"+languageResourceMap.get("minValue")+"',3,'"+languageResourceMap.get("avgValue")+"',4,'"+languageResourceMap.get("newestValue")+"',5,'"+languageResourceMap.get("oldestValue")+"',6,'"+languageResourceMap.get("dailyTotalValue")+"','') as totalType"
				+ " from tbl_report_items2unit_conf t "
				+ " where t.unitid="+unitId+" and t.reportType="+reportType
				+ " order by t.sort";
		
		List<?> list=this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[])list.get(i);
			String unit="";
			String dataType="\"\"";
			if(calItemList!=null){
				for(CalItem calItem:calItemList){
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
				reportCurveConfShowValue=reportCurveConfObj.getSort()+";"+(reportCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+reportCurveConfObj.getColor();
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
					curveStatType=languageResourceMap.get("curveStatType_sum");
				}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==2){
					curveStatType=languageResourceMap.get("curveStatType_avg");
				}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==3){
					curveStatType=languageResourceMap.get("curveStatType_max");
				}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==4){
					curveStatType=languageResourceMap.get("curveStatType_min");
				}
			}
			
			
			result_json.append("{\"id\":"+(i+1)+","
					+ "\"title\":\""+obj[0]+""+"\","
					+ "\"unit\":\""+unit+"\","
					+ "\"dataSource\":\""+obj[9]+"\","
					+ "\"totalType\":\""+obj[10]+"\","
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
	
	public String getReportInstanceTimingTotalCalItemsConfigData(String calculateType,String unitId,String reportType,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		List<CalItem> calItemList=null;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try{
			if("1".equalsIgnoreCase(calculateType)){
				calItemList=MemoryDataManagerTask.getSRPTimingTotalCalculateItem(language);
			}else if("2".equalsIgnoreCase(calculateType)){
				calItemList=MemoryDataManagerTask.getPCPTimingTotalCalculateItem(language);
			}else{
				calItemList=MemoryDataManagerTask.getAcqTimingTotalCalculateItem(language);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("prec")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("sumSign")+"\",\"dataIndex\":\"sumSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("averageSign")+"\",\"dataIndex\":\"averageSign\",width:80 ,children:[] },"
				+ "{ \"header\":\"报表曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("curveStatType")+"\",\"dataIndex\":\"curveStatType\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		String sql="select t.itemname,t.itemcode,t.sort,t.showlevel,t.sumsign,t.averagesign,t.reportCurveConf,t.curvestattype,t.prec,"
				+ "t.dataSource, decode(t.totalType,1,'"+languageResourceMap.get("maxValue")+"',2,'"+languageResourceMap.get("minValue")+"',3,'"+languageResourceMap.get("avgValue")+"',4,'"+languageResourceMap.get("newestValue")+"',5,'"+languageResourceMap.get("oldestValue")+"',6,'"+languageResourceMap.get("dailyTotalValue")+"','') as totalType"
				+ " from tbl_report_items2unit_conf t "
				+ " where t.unitid="+StringManagerUtils.stringToInteger(unitId)+" and t.reportType="+reportType
				+ " order by t.sort";
		
		List<?> list=this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[])list.get(i);
			String unit="";
			String dataType="\"\"";
			if(calItemList!=null){
				for(CalItem calItem:calItemList){
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
				reportCurveConfShowValue=reportCurveConfObj.getSort()+";"+(reportCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+reportCurveConfObj.getColor();
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
					curveStatType=languageResourceMap.get("curveStatType_sum");
				}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==2){
					curveStatType=languageResourceMap.get("curveStatType_avg");
				}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==3){
					curveStatType=languageResourceMap.get("curveStatType_max");
				}else if(StringManagerUtils.stringToInteger(curveStatTypeStr)==4){
					curveStatType=languageResourceMap.get("curveStatType_min");
				}
			}
			
			
			result_json.append("{\"id\":"+(i+1)+","
					+ "\"title\":\""+obj[0]+""+"\","
					+ "\"unit\":\""+unit+"\","
					+ "\"dataSource\":\""+obj[9]+"\","
					+ "\"totalType\":\""+obj[10]+"\","
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
	
	public String getProtocolInstanceItemsConfigData(String id,String classes,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		int index=1;
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("quantity")+"\",\"dataIndex\":\"quantity\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("storeDataType")+"\",\"dataIndex\":\"storeDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("IFDataType")+"\",\"dataIndex\":\"IFDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("prec")+"\",\"dataIndex\":\"prec\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("ratio")+"\",\"dataIndex\":\"ratio\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("RWType")+"\",\"dataIndex\":\"RWType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("acqMode")+"\",\"dataIndex\":\"acqMode\",width:80 ,children:[] }"
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

			ModbusProtocolConfig.Protocol protocolConfig=MemoryDataManagerTask.getProtocolByName(protocolName);
			if(protocolConfig!=null){
				Collections.sort(protocolConfig.getItems());
				for(int j=0;j<protocolConfig.getItems().size();j++){
					if(StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(),false)){
//						if(RWType.equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){}
						String RWTypeName=languageResourceMap.get("readOnly");
						if("r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
							RWTypeName=languageResourceMap.get("readOnly");
						}else if("w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
							RWTypeName=languageResourceMap.get("writeOnly");
						}else if("rw".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
							RWTypeName=languageResourceMap.get("readWrite");
						}
						
						String resolutionMode=languageResourceMap.get("numericValue");
						if(protocolConfig.getItems().get(j).getResolutionMode()==0){
							resolutionMode=languageResourceMap.get("switchingValue");
						}else if(protocolConfig.getItems().get(j).getResolutionMode()==1){
							resolutionMode=languageResourceMap.get("enumValue");
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
								+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?languageResourceMap.get("activeAcqModel"):languageResourceMap.get("passiveAcqModel"))+"\"},");
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
		return result_json.toString();
	}
	
	public String getProtocolDisplayInstanceAcqItemsConfigData(String id,String classes,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveColor\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsRealtimeSortList=new ArrayList<String>();
		List<String> itemsHistorySortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveConfList=new ArrayList<String>();
		List<String> historyCurveConfList=new ArrayList<String>();
		List<String> realtimeColorList=new ArrayList<String>();
		List<String> realtimeBgColorList=new ArrayList<String>();
		List<String> historyColorList=new ArrayList<String>();
		List<String> historyBgColorList=new ArrayList<String>();
		
		String protocolSql="select t.protocol from tbl_display_unit_conf t,tbl_protocoldisplayinstance t2 where t.id=t2.displayunitid and t2.id="+id+"";
		String sql="select t.itemname,t.bitindex,"
				+ "t.realtimeSort,t.historySort,"
				+ "t.showlevel,"
				+ "t.realtimeCurveConf,historyCurveConf,"
				+ "t.realtimeColor,t.realtimeBgColor,t.historyColor,t.historyBgColor "
				+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2,tbl_protocoldisplayinstance t3 "
				+ " where t.unitid=t2.id and t2.id=t3.displayunitid and t.type=0 and t3.id= "+id
				+ " order by t.id";
		List<?> protocolList=this.findCallSql(protocolSql);
		if(protocolList.size()>0){
			String protocolName=protocolList.get(0)+"";
			
			ModbusProtocolConfig.Protocol protocolConfig =MemoryDataManagerTask.getProtocolByName(protocolName);
			
			List<?> list=this.findCallSql(sql);
			
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsBitIndexList.add(obj[1]+"");
				itemsRealtimeSortList.add(obj[2]+"");
				itemsHistorySortList.add(obj[3]+"");
				
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
					realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+(realtimeCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+realtimeCurveConfObj.getColor();
				}
				if(historyCurveConfObj!=null){
					historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+(historyCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+historyCurveConfObj.getColor();
				}
				
				realtimeCurveConfList.add(realtimeCurveConfShowValue);
				historyCurveConfList.add(historyCurveConfShowValue);
				
				realtimeColorList.add(obj[7]+"");
				realtimeBgColorList.add(obj[8]+"");
				historyColorList.add(obj[9]+"");
				historyBgColorList.add(obj[10]+"");
			}
			if(protocolConfig!=null){
				Collections.sort(protocolConfig.getItems());
				int index=1;
				for(int j=0;j<protocolConfig.getItems().size();j++){
					if(StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(), false)){
						String realtimeSort="";
						String historySort="";
						String showLevel="";
						
						String realtimeCurveConfShowValue="";
						String historyCurveConfShowValue="";
						String realtimeColor=""; 
						String realtimeBgColor="";
						String historyColor="";
						String historyBgColor="";
						
						if(protocolConfig.getItems().get(j).getResolutionMode()==0
								&&protocolConfig.getItems().get(j).getMeaning()!=null
								&&protocolConfig.getItems().get(j).getMeaning().size()>0){
							Collections.sort(protocolConfig.getItems().get(j).getMeaning());//排序
							for(int k=0;k<protocolConfig.getItems().get(j).getMeaning().size();k++){
								realtimeSort="";
								historySort="";
								showLevel="";
								realtimeCurveConfShowValue="";
								historyCurveConfShowValue="";
								realtimeColor=""; 
								realtimeBgColor="";
								historyColor="";
								historyBgColor="";
								for(int m=0;m<itemsList.size();m++){
									if(itemsList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())
											&&itemsBitIndexList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"")
										){
										realtimeSort=itemsRealtimeSortList.get(m);
										historySort=itemsHistorySortList.get(m);
										showLevel=itemsShowLevelList.get(m);
										realtimeCurveConfShowValue=realtimeCurveConfList.get(m);
										historyCurveConfShowValue=historyCurveConfList.get(m);
										realtimeColor=realtimeColorList.get(m);
										realtimeBgColor=realtimeBgColorList.get(m);
										historyColor=historyColorList.get(m);
										historyBgColor=historyBgColorList.get(m);
										
										result_json.append("{"
												+ "\"id\":"+(index)+","
												+ "\"title\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
												+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
												+ "\"showLevel\":\""+showLevel+"\","
												+ "\"realtimeSort\":\""+realtimeSort+"\","
												+ "\"realtimeColor\":\""+realtimeColor+"\","
												+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
												+ "\"historySort\":\""+historySort+"\","
												+ "\"historyColor\":\""+historyColor+"\","
												+ "\"historyBgColor\":\""+historyBgColor+"\","
												+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
												+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\""
												+ "},");
										index++;
										break;
									}
								}
							}
						}else{
							for(int k=0;k<itemsList.size();k++){
								if(itemsList.get(k).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())){
									realtimeSort=itemsRealtimeSortList.get(k);
									historySort=itemsHistorySortList.get(k);
									showLevel=itemsShowLevelList.get(k);
									realtimeCurveConfShowValue=realtimeCurveConfList.get(k);
									historyCurveConfShowValue=realtimeCurveConfList.get(k);
									realtimeColor=realtimeColorList.get(k);
									realtimeBgColor=realtimeBgColorList.get(k);
									historyColor=historyColorList.get(k);
									historyBgColor=historyBgColorList.get(k);
									break;
								}
							}
							result_json.append("{"
									+ "\"id\":"+(index)+","
									+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
									+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
									+ "\"showLevel\":\""+showLevel+"\","
									+ "\"realtimeSort\":\""+realtimeSort+"\","
									+ "\"realtimeColor\":\""+realtimeColor+"\","
									+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
									+ "\"historySort\":\""+historySort+"\","
									+ "\"historyColor\":\""+historyColor+"\","
									+ "\"historyBgColor\":\""+historyBgColor+"\","
									+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
									+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\""
									+ "},");
							index++;
						}
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
	
	public String getProtocolDisplayInstanceCtrlItemsConfigData(String id,String classes,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsRealtimeSortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		
		String protocolSql="select t.protocol from tbl_display_unit_conf t,tbl_protocoldisplayinstance t2 where t.id=t2.displayunitid and t2.id="+id+"";
		String sql="select t.itemname,t.bitindex,t.realtimeSort,t.showlevel"
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
				itemsRealtimeSortList.add(obj[2]+"");
				itemsShowLevelList.add(obj[3]+"");
			}

			ModbusProtocolConfig.Protocol protocolConfig=MemoryDataManagerTask.getProtocolByName(protocolName);
			if(protocolConfig!=null){
//				Collections.sort(protocolConfig.getItems());
				int index=1;
				for(int j=0;j<protocolConfig.getItems().size();j++){
					if(StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(), false)){
						String realtimeSort="";
						String showLevel="";
						if(protocolConfig.getItems().get(j).getResolutionMode()==0
								&&protocolConfig.getItems().get(j).getMeaning()!=null
								&&protocolConfig.getItems().get(j).getMeaning().size()>0){
							Collections.sort(protocolConfig.getItems().get(j).getMeaning());//排序
							for(int k=0;k<protocolConfig.getItems().get(j).getMeaning().size();k++){
								realtimeSort="";
								showLevel="";
								for(int m=0;m<itemsList.size();m++){
									if(itemsList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())
											&&itemsBitIndexList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"")
										){
										realtimeSort=itemsRealtimeSortList.get(m);
										showLevel=itemsShowLevelList.get(m);
										break;
									}
								}
								
								result_json.append("{"
										+ "\"id\":"+(index)+","
										+ "\"title\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
										+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
										+ "\"showLevel\":\""+showLevel+"\","
										+ "\"realtimeSort\":\""+realtimeSort+"\""
										+ "},");
								index++;
							}
						}else{
							for(int k=0;k<itemsList.size();k++){
								if(itemsList.get(k).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())){
									realtimeSort=itemsRealtimeSortList.get(k);
									showLevel=itemsShowLevelList.get(k);
									break;
								}
							}
							result_json.append("{"
									+ "\"id\":"+(index)+","
									+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
									+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
									+ "\"showLevel\":\""+showLevel+"\","
									+ "\"realtimeSort\":\""+realtimeSort+"\""
									+ "},");
							index++;
						}
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
	
	public String getProtocolDisplayInstanceCalItemsConfigData(String id,String classes,String calculateType,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		List<CalItem> calItemList=new ArrayList<>();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try{
			if("1".equalsIgnoreCase(calculateType)){
				calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
			}else if("2".equalsIgnoreCase(calculateType)){
				calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
			}else{
				calItemList=MemoryDataManagerTask.getAcqCalculateItem(language);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveColor\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsCodeList=new ArrayList<String>();
		List<String> itemsRealtimeSortList=new ArrayList<String>();
		List<String> itemsHistorySortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveConfList=new ArrayList<String>();
		List<String> historyCurveConfList=new ArrayList<String>();
		List<String> realtimeColorList=new ArrayList<String>();
		List<String> realtimeBgColorList=new ArrayList<String>();
		List<String> historyColorList=new ArrayList<String>();
		List<String> historyBgColorList=new ArrayList<String>();
		
		if(StringManagerUtils.isNotNull(id)){
			
			ModbusProtocolConfig.Protocol protocol=null;
			String protocolSql="select t.protocol from TBL_ACQ_UNIT_CONF t,tbl_display_unit_conf t2,tbl_protocoldisplayinstance t3 where t.id=t2.acqunitid and t2.id=t3.displayunitid and t3.id="+id;
			List<?> protocolList=this.findCallSql(protocolSql);
			if(protocolList.size()>0){
				protocol=MemoryDataManagerTask.getProtocolByName(protocolList.get(0)+"");
			}
			
			String dailyTotalItemsSql="select t.itemname,t.dailytotalcalculatename,t6.mappingcolumn "
					+ " from TBL_ACQ_ITEM2GROUP_CONF t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4,tbl_display_unit_conf t5,"
					+ " tbl_datamapping t6,tbl_protocoldisplayinstance t7 "
					+ " where t.groupid=t2.id and t2.id=t3.groupid and t3.unitid=t4.id and t4.id=t5.acqunitid and t5.id=t7.displayunitid "
					+ " and t.itemname=t6.name "
					+ " and t.dailytotalcalculate=1 and t7.id= "+id
					+ " order by t.id";
			List<?> unitDailyTotalItemsList=this.findCallSql(dailyTotalItemsSql);
			for(int i=0;i<unitDailyTotalItemsList.size();i++){
				Object[] obj=(Object[])unitDailyTotalItemsList.get(i);
				String itemName=obj[0]+"";
				String name=obj[1]+"";
				String code=(obj[2]+"_TOTAL").toUpperCase();
				String unit="";
				if(protocol!=null&&protocol.getItems()!=null){
					for(ModbusProtocolConfig.Items item:protocol.getItems()){
						if(itemName.equalsIgnoreCase(item.getTitle())){
							unit=item.getUnit();
							break;
						}
					}
				}
				CalItem calItem=new CalItem(name,code,unit,2,languageResourceMap.get("configuration"),itemName+languageResourceMap.get("dailyCalculate"));
				calItemList.add(calItem);
			}
		}
		
		String sql="select t.itemname,t.itemcode,t.bitindex,"
				+ "t.realtimeSort,t.historySort,"
				+ "t.showlevel,"
				+ "t.realtimeCurveConf,historyCurveConf,"
				+ "t.realtimeColor,t.realtimeBgColor,t.historyColor,t.historyBgColor "
				+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2,tbl_protocoldisplayinstance t3 "
				+ " where t.unitid=t2.id and t2.id=t3.displayunitid and t.type=1 and t3.id= "+id
				+ " order by t.id";
		if(calItemList!=null && calItemList.size()>0){
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsCodeList.add(obj[1]+"");
				itemsBitIndexList.add(obj[2]+"");
				itemsRealtimeSortList.add(obj[3]+"");
				itemsHistorySortList.add(obj[4]+"");
				itemsShowLevelList.add(obj[5]+"");
				String realtimeCurveConfShowValue="";
				String historyCurveConfShowValue="";
				
				CurveConf realtimeCurveConfObj=null;
				if(StringManagerUtils.isNotNull(obj[6]+"") && !"\"\"".equals(obj[6]+"")){
					type = new TypeToken<CurveConf>() {}.getType();
					realtimeCurveConfObj=gson.fromJson(obj[6]+"", type);
				}
				
				CurveConf historyCurveConfObj=null;
				if(StringManagerUtils.isNotNull(obj[7]+"") && !"\"\"".equals(obj[7]+"")){
					type = new TypeToken<CurveConf>() {}.getType();
					historyCurveConfObj=gson.fromJson(obj[7]+"", type);
				}
				
				if(realtimeCurveConfObj!=null){
					realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+(realtimeCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+realtimeCurveConfObj.getColor();
				}
				if(historyCurveConfObj!=null){
					historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+(historyCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+historyCurveConfObj.getColor();
				}
				
				realtimeCurveConfList.add(realtimeCurveConfShowValue);
				historyCurveConfList.add(historyCurveConfShowValue);
				
				realtimeColorList.add(obj[8]+"");
				realtimeBgColorList.add(obj[9]+"");
				historyColorList.add(obj[10]+"");
				historyBgColorList.add(obj[11]+"");
			}
			int index=1;
			for(CalItem calItem:calItemList){
				if(StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(), false)){
					String realtimeSort="";
					String historySort="";
					String showLevel="";
					String realtimeCurveConfShowValue="";
					String historyCurveConfShowValue="";
					String realtimeColor=""; 
					String realtimeBgColor="";
					String historyColor="";
					String historyBgColor="";

					for(int k=0;k<itemsList.size();k++){
						if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
							realtimeSort=itemsRealtimeSortList.get(k);
							historySort=itemsHistorySortList.get(k);
							showLevel=itemsShowLevelList.get(k);
							realtimeCurveConfShowValue=realtimeCurveConfList.get(k);
							historyCurveConfShowValue=historyCurveConfList.get(k);
							realtimeColor=realtimeColorList.get(k);
							realtimeBgColor=realtimeBgColorList.get(k);
							historyColor=historyColorList.get(k);
							historyBgColor=historyBgColorList.get(k);
							break;
						}
					}
					result_json.append("{"
							+ "\"id\":"+index+","
							+ "\"title\":\""+calItem.getName()+"\","
							+ "\"unit\":\""+calItem.getUnit()+"\","
							+ "\"showLevel\":\""+showLevel+"\","
							+ "\"realtimeSort\":\""+realtimeSort+"\","
							+ "\"realtimeColor\":\""+realtimeColor+"\","
							+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
							+ "\"historySort\":\""+historySort+"\","
							+ "\"historyColor\":\""+historyColor+"\","
							+ "\"historyBgColor\":\""+historyBgColor+"\","
							+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
							+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\","
							+ "\"dataSource\":\""+calItem.getDataSource()+"\""
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
	
	public String getProtocolDisplayInstanceInputItemsConfigData(String id,String classes,String calculateType,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		List<CalItem> inputItemList=new ArrayList<>();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try{
			if("1".equalsIgnoreCase(calculateType)){
				inputItemList=MemoryDataManagerTask.getSRPInputItem(language);
			}else if("2".equalsIgnoreCase(calculateType)){
				inputItemList=MemoryDataManagerTask.getPCPInputItem(language);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveColor\",width:80 ,children:[] }"
				+ "]";
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		List<String> itemsList=new ArrayList<String>();
		List<String> itemsCodeList=new ArrayList<String>();
		List<String> itemsRealtimeSortList=new ArrayList<String>();
		List<String> itemsHistorySortList=new ArrayList<String>();
		List<String> itemsBitIndexList=new ArrayList<String>();
		List<String> itemsShowLevelList=new ArrayList<String>();
		List<String> realtimeCurveConfList=new ArrayList<String>();
		List<String> historyCurveConfList=new ArrayList<String>();
		List<String> realtimeColorList=new ArrayList<String>();
		List<String> realtimeBgColorList=new ArrayList<String>();
		List<String> historyColorList=new ArrayList<String>();
		List<String> historyBgColorList=new ArrayList<String>();
		
		String sql="select t.itemname,t.itemcode,t.bitindex,"
				+ "t.realtimeSort,t.historySort,"
				+ "t.showlevel,"
				+ "t.realtimeCurveConf,historyCurveConf,"
				+ "t.realtimeColor,t.realtimeBgColor,t.historyColor,t.historyBgColor "
				+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2,tbl_protocoldisplayinstance t3 "
				+ " where t.unitid=t2.id and t2.id=t3.displayunitid and t.type=3 "
				+ " and t3.id= "+id
				+ " order by t.id";
		if(inputItemList!=null){
			List<?> list=this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				itemsList.add(obj[0]+"");
				itemsCodeList.add(obj[1]+"");
				itemsBitIndexList.add(obj[2]+"");
				itemsRealtimeSortList.add(obj[3]+"");
				itemsHistorySortList.add(obj[4]+"");
				itemsShowLevelList.add(obj[5]+"");
				String realtimeCurveConfShowValue="";
				String historyCurveConfShowValue="";
				
				CurveConf realtimeCurveConfObj=null;
				if(StringManagerUtils.isNotNull(obj[6]+"") && !"\"\"".equals(obj[6]+"")){
					type = new TypeToken<CurveConf>() {}.getType();
					realtimeCurveConfObj=gson.fromJson(obj[6]+"", type);
				}
				
				CurveConf historyCurveConfObj=null;
				if(StringManagerUtils.isNotNull(obj[7]+"") && !"\"\"".equals(obj[7]+"")){
					type = new TypeToken<CurveConf>() {}.getType();
					historyCurveConfObj=gson.fromJson(obj[7]+"", type);
				}
				
				if(realtimeCurveConfObj!=null){
					realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+(realtimeCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+realtimeCurveConfObj.getColor();
				}
				if(historyCurveConfObj!=null){
					historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+(historyCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+historyCurveConfObj.getColor();
				}
				
				realtimeCurveConfList.add(realtimeCurveConfShowValue);
				historyCurveConfList.add(historyCurveConfShowValue);
				
				realtimeColorList.add(obj[8]+"");
				realtimeBgColorList.add(obj[9]+"");
				historyColorList.add(obj[10]+"");
				historyBgColorList.add(obj[11]+"");
			}
			int index=1;
			for(CalItem calItem:inputItemList){
				if(StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(), false)){
					String realtimeSort="";
					String historySort="";
					String showLevel="";
					String realtimeCurveConfShowValue="";
					String historyCurveConfShowValue="";
					String realtimeColor=""; 
					String realtimeBgColor="";
					String historyColor="";
					String historyBgColor="";

					for(int k=0;k<itemsList.size();k++){
						if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
							realtimeSort=itemsRealtimeSortList.get(k);
							historySort=itemsHistorySortList.get(k);
							showLevel=itemsShowLevelList.get(k);
							realtimeCurveConfShowValue=realtimeCurveConfList.get(k);
							historyCurveConfShowValue=historyCurveConfList.get(k);
							realtimeColor=realtimeColorList.get(k);
							realtimeBgColor=realtimeBgColorList.get(k);
							historyColor=historyColorList.get(k);
							historyBgColor=historyBgColorList.get(k);
							break;
						}
					}
					result_json.append("{"
							+ "\"id\":"+index+","
							+ "\"title\":\""+calItem.getName()+"\","
							+ "\"unit\":\""+calItem.getUnit()+"\","
							+ "\"showLevel\":\""+showLevel+"\","
							+ "\"realtimeSort\":\""+realtimeSort+"\","
							+ "\"realtimeColor\":\""+realtimeColor+"\","
							+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
							+ "\"historySort\":\""+historySort+"\","
							+ "\"historyColor\":\""+historyColor+"\","
							+ "\"historyBgColor\":\""+historyBgColor+"\","
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
	
	public String getProtocolAlarmInstanceNumItemsConfigData(String id,String classes,String resolutionMode,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson = new Gson();
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("upperLimit")+"\",\"dataIndex\":\"upperLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("lowerLimit")+"\",\"dataIndex\":\"lowerLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("hystersis")+"\",\"dataIndex\":\"hystersis\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		List<Integer> itemAddrsList=new ArrayList<Integer>();
		
		String itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.upperlimit,t.lowerlimit,t.hystersis,"
				+ " t.delay,t.retriggerTime,"
				+ " t.alarmlevel,"
				+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"'), "
				+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
				+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.itemaddr";
		
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.upperlimit,t.lowerlimit,t.hystersis,"
					+ " t.delay,t.retriggerTime,"
					+ " t.alarmlevel,"
					+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"'), "
					+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
					+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2 "
					+ " where t.unitid=t2.id "
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
					+ "\"retriggerTime\":\""+obj[8]+"\","
					+ "\"alarmLevel\":\""+MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[9]+"", language)+"\","
					+ "\"alarmSign\":\""+obj[10]+"\","
					+ "\"isSendMessage\":\""+obj[11]+"\","
					+ "\"isSendMail\":\""+obj[12]+"\"},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolAlarmInstanceCalNumItemsConfigData(String id,String classes,String resolutionMode,String calculateType,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson = new Gson();
		List<CalItem> calItemList=null;
		try{
			if(StringManagerUtils.stringToInteger(calculateType)==1){
				calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
			}else if(StringManagerUtils.stringToInteger(calculateType)==2){
				calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
			}else{
				calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("upperLimit")+"\",\"dataIndex\":\"upperLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("lowerLimit")+"\",\"dataIndex\":\"lowerLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("hystersis")+"\",\"dataIndex\":\"hystersis\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		List<Integer> itemAddrsList=new ArrayList<Integer>();
		
		String itemsSql="select t.id, t.itemname,t.itemcode,t.upperlimit,t.lowerlimit,t.hystersis,"
				+ " t.delay,t.retriggerTime,"
				+ " t.alarmlevel,"
				+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"'), "
				+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
				+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.id";
		
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,t.upperlimit,t.lowerlimit,t.hystersis,"
					+ " t.delay,t.retriggerTime,"
					+ " t.alarmlevel,"
					+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"'), "
					+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
					+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2 "
					+ " where t.unitid=t2.id "
					+ " and t2.id="+id+" "
					+ " and t.type="+resolutionMode
					+ " order by t.id";
		}
		
		List<?> list=this.findCallSql(itemsSql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String unit="";
			if(calItemList!=null){
				for(CalItem calItem:calItemList){
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
					+ "\"retriggerTime\":\""+obj[7]+"\","
					+ "\"alarmLevel\":\""+MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[8]+"", language)+"\","
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
	
	public String getProtocolAlarmInstanceSwitchItemsConfigData(String id,String classes,String resolutionMode,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("bit")+"\",\"dataIndex\":\"bitIndex\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("meaning")+"\",\"dataIndex\":\"meaning\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("switchItemAlarmValue")+"\",\"dataIndex\":\"value\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		List<Integer> itemAddrsList=new ArrayList<Integer>();
		
		String itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.bitIndex,t.value,"
				+ "t.delay,t.retriggerTime,"
				+ " t.alarmlevel,"
				+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"'), "
				+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
				+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail,"
				+ " t2.protocol "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.itemaddr,t.bitindex";
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.bitIndex,t.value,"
					+ " t.delay,t.retriggerTime,"
					+ " t.alarmlevel,"
					+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"'), "
					+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
					+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail,"
					+ " t2.protocol "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2 "
					+ " where t.unitid=t2.id "
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
			protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
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
					+ "\"value\":\""+("1".equalsIgnoreCase(obj[5]+"")?languageResourceMap.get("switchingOpenValue"):languageResourceMap.get("switchingCloseValue"))+"\","
					+ "\"delay\":\""+obj[6]+"\","
					+ "\"retriggerTime\":\""+obj[7]+"\","
					+ "\"alarmLevel\":\""+MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[8]+"", language)+"\","
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
	
	public String getProtocolAlarmInstanceEnumItemsConfigData(String id,String classes,String resolutionMode,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("value")+"\",\"dataIndex\":\"value\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("meaning")+"\",\"dataIndex\":\"meaning\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		List<Integer> itemAddrsList=new ArrayList<Integer>();
		
		String itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.value,"
				+ " t.delay,t.retriggerTime,"
				+ " t.alarmlevel,"
				+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"'),"
				+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
				+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail,"
				+ " t2.protocol "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.itemaddr,t.bitindex";
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,t.value,"
					+ "t.delay,t.retriggerTime,"
					+ " t.alarmlevel,"
					+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"'),"
					+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
					+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail,"
					+ " t2.protocol "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2 "
					+ " where t.unitid=t2.id "
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
			protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
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
					+ "\"retriggerTime\":\""+obj[6]+"\","
					+ "\"alarmLevel\":\""+MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[7]+"", language)+"\","
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
	
	public String getProtocolAlarmInstanceFESDiagramResultItemsConfigData(String id,String classes,String resolutionMode,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		String itemsSql="select t.id, t.itemname,t.itemcode,"
				+ "t.delay,t.retriggerTime,"
				+ " t4.itemname as alarmLevel,"
				+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as alarmsign, "
				+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
				+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.id";
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,"
					+ "t.delay,t.retriggerTime,"
					+ " t.alarmlevel,"
					+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as alarmsign,"
					+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
					+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2 "
					+ " where t.unitid=t2.id "
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
					+ "\"retriggerTime\":\""+obj[4]+"\","
					+ "\"alarmLevel\":\""+obj[5]+"\","
					+ "\"alarmSign\":\""+obj[6]+"\","
					+ "\"isSendMessage\":\""+obj[7]+"\","
					+ "\"isSendMail\":\""+obj[8]+"\"},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolAlarmInstanceRunStatusItemsConfigData(String id,String classes,String resolutionMode,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		String itemsSql="select t.id, t.itemname,t.itemcode,"
				+ "t.delay,t.retriggerTime,"
				+ " t.alarmlevel,"
				+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as alarmsign, "
				+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
				+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.id";
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,"
					+ " t.delay,t.retriggerTime,"
					+ " t.alarmlevel,"
					+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as alarmsign,"
					+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
					+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2 "
					+ " where t.unitid=t2.id "
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
					+ "\"retriggerTime\":\""+obj[4]+"\","
					+ "\"alarmLevel\":\""+MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[5]+"", language)+"\","
					+ "\"alarmSign\":\""+obj[6]+"\","
					+ "\"isSendMessage\":\""+obj[7]+"\","
					+ "\"isSendMail\":\""+obj[8]+"\"},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getProtocolAlarmInstanceCommStatusItemsConfigData(String id,String classes,String resolutionMode,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		String itemsSql="select t.id, t.itemname,t.itemcode,"
				+ " t.delay,t.retriggerTime,"
				+ " t.alarmlevel,"
				+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as alarmsign, "
				+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
				+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
				+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3 "
				+ " where t.unitid=t2.id and t2.id=t3.alarmunitid "
				+ " and t3.id="+id+" "
				+ " and t.type="+resolutionMode
				+ " order by t.id";
		if("2".equals(classes)){
			itemsSql="select t.id, t.itemname,t.itemcode,"
					+ "t.delay,t.retriggerTime,"
					+ " t.alarmlevel,"
					+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as alarmsign,"
					+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
					+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2 "
					+ " where t.unitid=t2.id "
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
					+ "\"retriggerTime\":\""+obj[4]+"\","
					+ "\"alarmLevel\":\""+MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[5]+"", language)+"\","
					+ "\"alarmSign\":\""+obj[6]+"\","
					+ "\"isSendMessage\":\""+obj[7]+"\","
					+ "\"isSendMail\":\""+obj[8]+"\"},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getAcquisitionUnitTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		tree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			String unitSql="select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark,t.protocol "
					+ " from tbl_acq_unit_conf t,tbl_protocol t2"
					+ " where t.protocol=t2.name";
			if(StringManagerUtils.isNotNull(deviceTypeIds)){
				unitSql+=" and t2.devicetype in ("+deviceTypeIds+")";
			}else{
				unitSql+=" and 1=2 ";
			}
			unitSql+= " order by t.protocol,t.id";
			String groupSql="select t3.id,t3.group_code,t3.group_name,t3.grouptiminginterval,t3.groupsavinginterval,t3.remark,t3.protocol,t3.type,t2.id as unitId "
					+ " from TBL_ACQ_GROUP2UNIT_CONF t,tbl_acq_unit_conf t2,tbl_acq_group_conf t3 "
					+ " where t.unitid=t2.id and t.groupid=t3.id "
					+ " order by t3.protocol,t2.unit_code,t3.type,t3.id";
			List<?> unitList=this.findCallSql(unitSql);
			List<?> groupList=this.findCallSql(groupSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(StringManagerUtils.existOrNot(deviceTypeIdArr, modbusProtocolConfig.getProtocol().get(i).getDeviceType()+"")){
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
									tree_json.append("\"typeName\":\""+("0".equalsIgnoreCase(groupObj[7]+"")?languageResourceMap.get("acqGroup"):languageResourceMap.get("controlGroup"))+"\",");
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
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("unitList")+"\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportAcqUnitTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		tree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			String unitSql="select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark,t.protocol "
					+ " from tbl_acq_unit_conf t,tbl_protocol t2"
					+ " where t.protocol=t2.name";
			if(StringManagerUtils.isNotNull(deviceTypeIds)){
				unitSql+=" and t2.devicetype in ("+deviceTypeIds+")";
			}else{
				unitSql+=" and 1=2 ";
			}
			unitSql+= " order by t.protocol,t.id";
			String groupSql="select t3.id,t3.group_code,t3.group_name,t3.grouptiminginterval,t3.groupsavinginterval,t3.remark,t3.protocol,t3.type,t2.id as unitId "
					+ " from TBL_ACQ_GROUP2UNIT_CONF t,tbl_acq_unit_conf t2,tbl_acq_group_conf t3 "
					+ " where t.unitid=t2.id and t.groupid=t3.id "
					+ " order by t3.protocol,t2.unit_code,t3.type,t3.id";
			List<?> unitList=this.findCallSql(unitSql);
			List<?> groupList=this.findCallSql(groupSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(StringManagerUtils.existOrNot(deviceTypeIdArr, modbusProtocolConfig.getProtocol().get(i).getDeviceType()+"")){
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
							tree_json.append("\"checked\": false,");
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
									tree_json.append("\"typeName\":\""+("0".equalsIgnoreCase(groupObj[7]+"")?languageResourceMap.get("acqGroup"):languageResourceMap.get("controlGroup"))+"\",");
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
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("unitList")+"\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getDisplayUnitTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		tree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			String unitSql="select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark,t.protocol,t.acqunitid,t2.unit_name as acqunitname,"
					+ " t.calculateType, "
					+ " decode(t.calculateType,1,'"+languageResourceMap.get("SRPCalculate")+"',2,'"+languageResourceMap.get("PCPCalculate")+"','"+languageResourceMap.get("nothing")+"') as calculateTypeName"
					+ " from tbl_display_unit_conf t,tbl_acq_unit_conf t2,tbl_protocol t3 "
					+ " where t.acqunitid=t2.id and t2.protocol=t3.name";
			if(StringManagerUtils.isNotNull(deviceTypeIds)){
				unitSql+=" and t3.devicetype in ("+deviceTypeIds+")";
			}else{
				unitSql+=" and 1=2 ";
			}
			unitSql+= " order by t.protocol,t.id";
			List<?> unitList=this.findCallSql(unitSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(StringManagerUtils.existOrNot(deviceTypeIdArr, modbusProtocolConfig.getProtocol().get(i).getDeviceType()+"")){
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
							tree_json.append("\"calculateType\":\""+unitObj[7]+"\",");
							tree_json.append("\"calculateTypeName\":\""+unitObj[8]+"\",");
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
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("unitList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportDisplayUnitTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		tree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			String unitSql="select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark,t.protocol,t.acqunitid,t2.unit_name as acqunitname,"
					+ " t.calculateType, "
					+ " decode(t.calculateType,1,'"+languageResourceMap.get("SRPCalculate")+"',2,'"+languageResourceMap.get("PCPCalculate")+"','"+languageResourceMap.get("nothing")+"') as calculateTypeName"
					+ " from tbl_display_unit_conf t,tbl_acq_unit_conf t2,tbl_protocol t3 "
					+ " where t.acqunitid=t2.id and t2.protocol=t3.name";
			if(StringManagerUtils.isNotNull(deviceTypeIds)){
				unitSql+=" and t3.devicetype in ("+deviceTypeIds+")";
			}else{
				unitSql+=" and 1=2 ";
			}
			unitSql+= " order by t.protocol,t.id";
			List<?> unitList=this.findCallSql(unitSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(StringManagerUtils.existOrNot(deviceTypeIdArr, modbusProtocolConfig.getProtocol().get(i).getDeviceType()+"")){
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
							tree_json.append("\"calculateType\":\""+unitObj[7]+"\",");
							tree_json.append("\"calculateTypeName\":\""+unitObj[8]+"\",");
							tree_json.append("\"iconCls\": \"acqUnit\",");
							tree_json.append("\"checked\": false,");
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
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("unitList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
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
	
	public String modbusProtocolAddrMappingTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		tree_json.append("[");
		
		String sql="select t.name,t.code,t.sort,t.devicetype,t2.name_"+language+" as deviceTypeName,t2.allpath_"+language+" as deviceTypeAllPath "
				+ " from tbl_protocol t,viw_devicetypeinfo t2 "
				+ " where t.devicetype=t2.id "
				+ " and t.devicetype in ("+deviceTypeIds+") "
				+ " order by t.sort,t.name";
		List<?> list=this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			tree_json.append("{\"classes\":1,");
			tree_json.append("\"text\":\""+obj[0]+"\",");
			tree_json.append("\"code\":\""+obj[1]+"\",");
			tree_json.append("\"sort\":\""+obj[2]+"\",");
			tree_json.append("\"deviceType\":\""+obj[3]+"\",");
			tree_json.append("\"deviceTypeName\":\""+obj[4]+"\",");
			tree_json.append("\"deviceTypeAllPath\":\""+obj[5]+"\",");
			tree_json.append("\"iconCls\": \"protocol\",");
			tree_json.append("\"leaf\": true");
			tree_json.append("},");
		}
		
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("protocolList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		
		return result_json.toString();
	}
	
	public String exportProtocolTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		tree_json.append("[");
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		if(modbusProtocolConfig!=null){
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				
				if(StringManagerUtils.existOrNot(deviceTypeIdArr, modbusProtocolConfig.getProtocol().get(i).getDeviceType()+"")){
					tree_json.append("{\"classes\":1,");
					tree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					tree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					tree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					tree_json.append("\"iconCls\": \"protocol\",");
					tree_json.append("\"checked\": false,");
					tree_json.append("\"leaf\": true");
					tree_json.append("},");
				}
			}
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("+protocolList+")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		
		return result_json.toString();
	}
	
	public String modbusProtocolAndAcqUnitTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		result_json.append("[");
		if(modbusProtocolConfig!=null){
			String unitSql="select t.id as id,t.unit_code as unitCode,t.unit_name as unitName,t.remark,t.protocol from tbl_acq_unit_conf t order by t.protocol,t.id";
			List<?> unitList=this.findCallSql(unitSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(StringManagerUtils.existOrNot(deviceTypeIdArr, modbusProtocolConfig.getProtocol().get(i).getDeviceType()+"")){
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
	
	public String modbusProtocolAndDisplayUnitTreeData(String deviceTypeIds){
		StringBuffer result_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		result_json.append("[");
		if(modbusProtocolConfig!=null){
			String alarmUnitSql="select t.id,t.unit_code,t.unit_name,t.remark,t.protocol "
					+ " from tbl_display_unit_conf t ,tbl_acq_unit_conf t2,tbl_protocol t3 "
					+ " where t.acqunitid=t2.id and t2.protocol=t3.name";
			if(StringManagerUtils.isNotNull(deviceTypeIds)){
				alarmUnitSql+=" and t3.devicetype in ("+deviceTypeIds+")";
			}else{
				alarmUnitSql+=" and 1=2 ";
			}
			alarmUnitSql+= " order by t.protocol,t.id";
			List<?> alarmUnitList=this.findCallSql(alarmUnitSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(StringManagerUtils.existOrNot(deviceTypeIdArr, modbusProtocolConfig.getProtocol().get(i).getDeviceType()+"")){
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
	
	
	public String modbusProtocolAndAlarmUnitTreeData(String deviceTypeIds){
		StringBuffer result_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		result_json.append("[");
		if(modbusProtocolConfig!=null){
			String alarmUnitSql="select t.id,t.unit_code,t.unit_name,t.remark,t.protocol "
					+ " from tbl_alarm_unit_conf t,tbl_protocol t2"
					+ " where t.protocol=t2.name ";
			if(StringManagerUtils.isNotNull(deviceTypeIds)){
				alarmUnitSql+=" and t2.devicetype in ("+deviceTypeIds+")";
			}else{
				alarmUnitSql+=" and 1=2 ";
			}
			alarmUnitSql+= " order by t.protocol,t.id";
			List<?> alarmUnitList=this.findCallSql(alarmUnitSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(StringManagerUtils.existOrNot(deviceTypeIdArr, modbusProtocolConfig.getProtocol().get(i).getDeviceType()+"")){
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
	
	public String repoerUnitTreeData(String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		tree_json.append("[");
		String unitSql="select t.id,t.unit_code,t.unit_name,"
				+ "t.calculateType,"
				+ " decode(t.calculateType,1,'"+languageResourceMap.get("SRPCalculate")+"',2,'"+languageResourceMap.get("PCPCalculate")+"','"+languageResourceMap.get("nothing")+"') as calculateTypeName,"
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
			tree_json.append("\"calculateType\":\""+obj[3]+"\",");
			tree_json.append("\"calculateTypeName\":\""+obj[4]+"\",");
			tree_json.append("\"singleWellRangeReportTemplate\":\""+obj[5]+"\",");
			tree_json.append("\"singleWellDailyReportTemplate\":\""+obj[6]+"\",");
			tree_json.append("\"productionReportTemplate\":\""+obj[7]+"\",");
			tree_json.append("\"sort\":\""+obj[8]+"\",");
			tree_json.append("\"iconCls\": \"protocol\",");
			tree_json.append("\"leaf\": true");
			tree_json.append("},");
		
		}
		
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("unitList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportReportUnitTreeData(String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		tree_json.append("[");
		String unitSql="select t.id,t.unit_code,t.unit_name,"
				+ "t.calculateType,"
				+ " decode(t.calculateType,1,'"+languageResourceMap.get("SRPCalculate")+"',2,'"+languageResourceMap.get("PCPCalculate")+"','"+languageResourceMap.get("nothing")+"') as calculateTypeName,"
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
			tree_json.append("\"calculateType\":\""+obj[3]+"\",");
			tree_json.append("\"calculateTypeName\":\""+obj[4]+"\",");
			tree_json.append("\"singleWellRangeReportTemplate\":\""+obj[5]+"\",");
			tree_json.append("\"singleWellDailyReportTemplate\":\""+obj[6]+"\",");
			tree_json.append("\"productionReportTemplate\":\""+obj[7]+"\",");
			tree_json.append("\"sort\":\""+obj[8]+"\",");
			tree_json.append("\"iconCls\": \"protocol\",");
			tree_json.append("\"checked\": false,");
			tree_json.append("\"leaf\": true");
			tree_json.append("},");
		
		}
		
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("unitList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getReportDataTemplateList(String reportType){
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
					result_json.append("\"templateCode\":\""+templateList.get(i).getTemplateCode()+"\",");
					result_json.append("\"calculateType\":\""+templateList.get(i).getCalculateType()+"\"},");
				}
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("],\"totalCount\":"+totalCount+"}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getReportTemplateData(String reportType,String code,String calculateType){
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
					if(code.equalsIgnoreCase(templateList.get(i).getTemplateCode()) ){
						Gson gson=new Gson();
						if(StringManagerUtils.stringToInteger(reportType)==1){
							result=gson.toJson(templateList.get(i)).replaceAll("orgNameLabel", "label").replaceAll("label", "***");
						}else{
							result=gson.toJson(templateList.get(i)).replaceAll("deviceNameLabel", "label").replaceAll("label", "***");
						}
						break;
					}
				}
			}
		}
		
		return result;
	}
	
	public String modbusProtocolAlarmUnitTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		tree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			String unitSql="select t.id,t.unit_code,t.unit_name,t.remark,t.protocol,"
					+ " t.calculateType, "
					+ " decode(t.calculateType,1,'"+languageResourceMap.get("SRPCalculate")+"',2,'"+languageResourceMap.get("PCPCalculate")+"','"+languageResourceMap.get("nothing")+"') as calculateTypeName"
					+ " from tbl_alarm_unit_conf t,tbl_protocol t2"
					+ " where t.protocol=t2.name ";
			if(StringManagerUtils.isNotNull(deviceTypeIds)){
				unitSql+=" and t2.devicetype in ("+deviceTypeIds+")";
			}else{
				unitSql+=" and 1=2 ";
			}
			unitSql+= " order by t.protocol,t.unit_code";
			List<?> unitList=this.findCallSql(unitSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(StringManagerUtils.existOrNot(deviceTypeIdArr, modbusProtocolConfig.getProtocol().get(i).getDeviceType()+"")){
					tree_json.append("{\"classes\":1,");
					tree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					tree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					tree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
					tree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					tree_json.append("\"iconCls\": \"protocol\",");
					tree_json.append("\"expanded\": true,");
					tree_json.append("\"children\": [");
					for(int j=0;j<unitList.size();j++){
						Object[] unitObj = (Object[]) unitList.get(j);
						String protocol=unitObj[4]+"";
						if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(protocol)){
							tree_json.append("{\"classes\":3,");
							tree_json.append("\"id\":"+unitObj[0]+",");
							tree_json.append("\"deviceType\":"+0+",");
							tree_json.append("\"code\":\""+unitObj[1]+"\",");
							tree_json.append("\"text\":\""+unitObj[2]+"\",");
							tree_json.append("\"remark\":\""+unitObj[3]+"\",");
							tree_json.append("\"protocol\":\""+protocol+"\",");
							tree_json.append("\"calculateType\":\""+unitObj[5]+"\",");
							tree_json.append("\"calculateTypeName\":\""+unitObj[6]+"\",");
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
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"deviceType\": 0,\"text\":\""+languageResourceMap.get("unitList")+"\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportAlarmUnitTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		tree_json.append("[");
		
		if(modbusProtocolConfig!=null){
			String unitSql="select t.id,t.unit_code,t.unit_name,t.remark,t.protocol"
					+ " from tbl_alarm_unit_conf t,tbl_protocol t2"
					+ " where t.protocol=t2.name ";
			if(StringManagerUtils.isNotNull(deviceTypeIds)){
				unitSql+=" and t2.devicetype in ("+deviceTypeIds+")";
			}else{
				unitSql+=" and 1=2 ";
			}
			unitSql+= " order by t.protocol,t.unit_code";
			List<?> unitList=this.findCallSql(unitSql);
			//排序
			Collections.sort(modbusProtocolConfig.getProtocol());
			
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(StringManagerUtils.existOrNot(deviceTypeIdArr, modbusProtocolConfig.getProtocol().get(i).getDeviceType()+"")){
					tree_json.append("{\"classes\":1,");
					tree_json.append("\"text\":\""+modbusProtocolConfig.getProtocol().get(i).getName()+"\",");
					tree_json.append("\"code\":\""+modbusProtocolConfig.getProtocol().get(i).getCode()+"\",");
					tree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
					tree_json.append("\"sort\":\""+modbusProtocolConfig.getProtocol().get(i).getSort()+"\",");
					tree_json.append("\"iconCls\": \"protocol\",");
					tree_json.append("\"expanded\": true,");
					tree_json.append("\"children\": [");
					for(int j=0;j<unitList.size();j++){
						Object[] unitObj = (Object[]) unitList.get(j);
						if(modbusProtocolConfig.getProtocol().get(i).getName().equalsIgnoreCase(unitObj[unitObj.length-1]+"")){
							tree_json.append("{\"classes\":3,");
							tree_json.append("\"id\":"+unitObj[0]+",");
							tree_json.append("\"deviceType\":"+modbusProtocolConfig.getProtocol().get(i).getDeviceType()+",");
							tree_json.append("\"code\":\""+unitObj[1]+"\",");
							tree_json.append("\"text\":\""+unitObj[2]+"\",");
							tree_json.append("\"remark\":\""+unitObj[3]+"\",");
							tree_json.append("\"protocol\":\""+unitObj[4]+"\",");
							tree_json.append("\"iconCls\": \"acqGroup\",");
							tree_json.append("\"checked\": false,");
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
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"deviceType\": 0,\"text\":\""+languageResourceMap.get("unitList")+"\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
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
		StringBuffer srpUnit_json = new StringBuffer();
		StringBuffer pcpUnit_json = new StringBuffer();

		srpUnit_json.append("[");
		pcpUnit_json.append("[");
		String acqUnitSql="select t.unit_code,t.unit_name,t.protocol from TBL_ACQ_UNIT_CONF t order by t.id";
		List<?> unitList=this.findCallSql(acqUnitSql);
		for(int i=0;i<unitList.size();i++){
			Object[] obj = (Object[]) unitList.get(i);
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()!=null){
				for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
					if(modbusProtocolConfig.getProtocol().get(j).getName().equalsIgnoreCase(obj[2]+"")){

						srpUnit_json.append("\""+obj[1]+"\",");
					
						break;
					}
				}
			}
		}
		if(srpUnit_json.toString().endsWith(",")){
			srpUnit_json.deleteCharAt(srpUnit_json.length() - 1);
		}
		if(pcpUnit_json.toString().endsWith(",")){
			pcpUnit_json.deleteCharAt(pcpUnit_json.length() - 1);
		}
		srpUnit_json.append("]");
		pcpUnit_json.append("]");
		result_json.append("{\"srpAcqUnit\":"+srpUnit_json+",");
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
	
	public String getModbusProtocolInstanceConfigTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		tree_json.append("[");
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		String sql="select t.id,t.name,t.code,t.acqprotocoltype,t.ctrlprotocoltype,"//0~4
				+ " t.SignInPrefixSuffixHex,t.signinprefix,t.signinsuffix,t.SignInIDHex,"//5~8
				+ " t.HeartbeatPrefixSuffixHex,t.heartbeatprefix,t.heartbeatsuffix,"//9~11
				+ " t.packetsendinterval,"//12
				+ " t.sort,t.unitid,t2.unit_name "//13~15
				+ " from tbl_protocolinstance t ,tbl_acq_unit_conf t2,tbl_protocol t3 "
				+ " where t.unitid=t2.id and t2.protocol=t3.name";
		if(StringManagerUtils.isNotNull(deviceTypeIds)){
			sql+=" and t3.devicetype in ("+deviceTypeIds+")";
		}else{
			sql+=" and 1=2 ";
		}	
		sql+= " order by t.sort";
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
		
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("instanceList")+"\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportProtocolAcqInstanceTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		tree_json.append("[");
		String sql="select t.id,t.name,t.code,t.acqprotocoltype,t.ctrlprotocoltype,"//0~4
				+ " t.SignInPrefixSuffixHex,t.signinprefix,t.signinsuffix,t.SignInIDHex,"//5~8
				+ " t.HeartbeatPrefixSuffixHex,t.heartbeatprefix,t.heartbeatsuffix,"//9~11
				+ " t.packetsendinterval,"//12
				+ " t.sort,t.unitid,t2.unit_name "//13~15
				+ " from tbl_protocolinstance t ,tbl_acq_unit_conf t2,tbl_protocol t3 "
				+ " where t.unitid=t2.id and t2.protocol=t3.name";
		if(StringManagerUtils.isNotNull(deviceTypeIds)){
			sql+=" and t3.devicetype in ("+deviceTypeIds+")";
		}else{
			sql+=" and 1=2 ";
		}	
		sql+= " order by t.sort";
		List<?> list=this.findCallSql(sql);
		
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
			tree_json.append("\"checked\": false,");
			tree_json.append("\"leaf\": true");
			tree_json.append("},");
		}
		
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("instanceList")+"\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusDisplayProtocolInstanceConfigTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		tree_json.append("[");
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		String sql="select t.id,t.name,t.code,t.displayUnitId,t2.unit_name,t.sort ,t2.calculatetype  "
				+ " from tbl_protocoldisplayinstance t ,tbl_display_unit_conf t2,tbl_acq_unit_conf t3,tbl_protocol t4 "
				+ " where t.displayUnitId=t2.id and t2.acqunitid=t3.id and t3.protocol=t4.name";
		if(StringManagerUtils.isNotNull(deviceTypeIds)){
			sql+=" and t4.devicetype in ("+deviceTypeIds+")";
		}else{
			sql+=" and 1=2 ";
		}
		sql+= " order by t.sort";
		List<?> list=this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);

			tree_json.append("{\"classes\":1,");
			tree_json.append("\"id\":\""+obj[0]+"\",");
			tree_json.append("\"text\":\""+obj[1]+"\",");
			tree_json.append("\"code\":\""+obj[2]+"\",");
			tree_json.append("\"displayUnitId\":"+obj[3]+",");
			tree_json.append("\"displayUnitName\":\""+obj[4]+"\",");
			tree_json.append("\"calculateType\":\""+obj[6]+"\",");
			tree_json.append("\"sort\":\""+obj[5]+"\",");
			tree_json.append("\"iconCls\": \"protocol\",");
			tree_json.append("\"expanded\": true,");
			tree_json.append("\"children\": [");
			tree_json.append("{\"classes\":2,");
			tree_json.append("\"id\":"+obj[3]+",");
			tree_json.append("\"text\":\""+obj[4]+"\",");
			tree_json.append("\"calculateType\":\""+obj[6]+"\",");
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
		
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("instanceList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportProtocolDisplayInstanceTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		tree_json.append("[");
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		String sql="select t.id,t.name,t.code,t.displayUnitId,t2.unit_name,t.sort   "
				+ " from tbl_protocoldisplayinstance t ,tbl_display_unit_conf t2,tbl_acq_unit_conf t3,tbl_protocol t4 "
				+ " where t.displayUnitId=t2.id and t2.acqunitid=t3.id and t3.protocol=t4.name";
		if(StringManagerUtils.isNotNull(deviceTypeIds)){
			sql+=" and t4.devicetype in ("+deviceTypeIds+")";
		}else{
			sql+=" and 1=2 ";
		}
		sql+= " order by t.sort";
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
			tree_json.append("\"checked\": false,");
			tree_json.append("\"leaf\": true");
			tree_json.append("},");
		}
		
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("instanceList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String modbusReportInstanceConfigTreeData(String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		tree_json.append("[");
		String sql="select t.id,t.name,t.code,t.unitid,t2.unit_name,t.sort,"
				+ " t2.singleWellRangeReportTemplate,t2.singleWellDailyReportTemplate,t2.productionreporttemplate,"
				+ " t2.calculateType "
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
			tree_json.append("\"calculateType\":\""+obj[9]+"\",");
			tree_json.append("\"iconCls\": \"protocol\",");
			tree_json.append("\"expanded\": true,");
			tree_json.append("\"children\": [");
			tree_json.append("{\"classes\":2,");
			tree_json.append("\"unitId\":"+obj[3]+",");
			tree_json.append("\"text\":\""+obj[4]+"\",");
			tree_json.append("\"singleWellRangeReportTemplate\":\""+obj[6]+"\",");
			tree_json.append("\"singleWellDailyReportTemplate\":\""+obj[7]+"\",");
			tree_json.append("\"productionReportTemplate\":\""+obj[8]+"\",");
			tree_json.append("\"calculateType\":\""+obj[9]+"\",");
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
		
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("instanceList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"},");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getExportProtocolReportInstanceTreeData(String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		tree_json.append("[");
		String sql="select t.id,t.name,t.code,t.unitid,t2.unit_name,t.sort,"
				+ " t2.singleWellRangeReportTemplate,t2.singleWellDailyReportTemplate,t2.productionreporttemplate,"
				+ " t2.calculateType "
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
			tree_json.append("\"calculateType\":\""+obj[9]+"\",");
			tree_json.append("\"iconCls\": \"protocol\",");
			tree_json.append("\"expanded\": true,");
			tree_json.append("\"checked\": false,");
			tree_json.append("\"leaf\": true");
			tree_json.append("},");
		}
		
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("instanceList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"},");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusProtocolAlarmInstanceConfigTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		tree_json.append("[");
		String sql="select t.id,t.name,t.code,t.alarmUnitId,t2.unit_name,t.sort   "
				+ " from tbl_protocolalarminstance t ,tbl_alarm_unit_conf t2,tbl_protocol t3 "
				+ " where t.alarmunitid=t2.id and t2.protocol=t3.name";
		if(StringManagerUtils.isNotNull(deviceTypeIds)){
			sql+=" and t3.devicetype in ("+deviceTypeIds+")";
		}else{
			sql+=" and 1=2 ";
		}
		sql+= " order by t.sort";
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
		
		result_json.append("{\"classes\":0,\"deviceType\":0,\"text\":\""+languageResourceMap.get("instanceList")+"\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"},");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String exportProtocolAlarmInstanceTreeData(String deviceTypeIds,String language){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		tree_json.append("[");
		String sql="select t.id,t.name,t.code,t.alarmUnitId,t2.unit_name,t.sort   "
				+ " from tbl_protocolalarminstance t ,tbl_alarm_unit_conf t2,tbl_protocol t3 "
				+ " where t.alarmunitid=t2.id and t2.protocol=t3.name";
		if(StringManagerUtils.isNotNull(deviceTypeIds)){
			sql+=" and t3.devicetype in ("+deviceTypeIds+")";
		}else{
			sql+=" and 1=2 ";
		}
		sql+= " order by t.sort";
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
			tree_json.append("\"checked\": false,");
			tree_json.append("\"leaf\": true");
			tree_json.append("},");
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		result_json.append("[");
		result_json.append("{\"classes\":0,\"deviceType\":0,\"text\":\""+languageResourceMap.get("instanceList")+"\",\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"},");
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getModbusProtoclCombList(String deviceTypeIds){
		StringBuffer result_json = new StringBuffer();
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		//排序
		Collections.sort(modbusProtocolConfig.getProtocol());
		
		result_json.append("{\"totals\":"+modbusProtocolConfig.getProtocol().size()+",\"list\":[");
		for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
			if(StringManagerUtils.existOrNot(deviceTypeIdArr, modbusProtocolConfig.getProtocol().get(i).getDeviceType()+"")){
				result_json.append("{boxkey:\"" + modbusProtocolConfig.getProtocol().get(i).getName() + "\",");
				result_json.append("boxval:\"" + modbusProtocolConfig.getProtocol().get(i).getName() + "\"},");
			}
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
	
	public String getSMSInstanceList(String name,Page pager,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
		+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
		+ "{ \"header\":\""+languageResourceMap.get("instanceName")+"\",\"dataIndex\":\"name\" ,children:[] },"
		+ "{ \"header\":\""+languageResourceMap.get("acqProtocolType")+"\",\"dataIndex\":\"acqProtocolType\" ,children:[] },"
		+ "{ \"header\":\""+languageResourceMap.get("ctrlProtocolType")+"\",\"dataIndex\":\"ctrlProtocolType\" ,children:[] },"
		+ "{ \"header\":\""+languageResourceMap.get("sortNum")+"\",\"dataIndex\":\"sort\",children:[] }"
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
	
	
	
	public String getAcquisitionUnitCombList(String protocol,String deviceTypeIds){
		StringBuffer result_json = new StringBuffer();
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		String sql="select t.id,t.unit_name "
				+ " from TBL_ACQ_UNIT_CONF t,tbl_protocol t2 "
				+ " where t.protocol=t2.name";
		if(StringManagerUtils.isNotNull(deviceTypeIds)){
			sql+=" and t2.devicetype in ("+deviceTypeIds+")";
		}else{
			sql+=" and 1=2 ";
		}
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
	
	public String getDatabaseColumnMappingTable(String classes,String deviceType,String protocolCode,String language) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer calColumnNameBuff = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String sql="select t.id,t.name,t.mappingcolumn,t.calcolumn from tbl_datamapping t where 1=1";
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
		if(StringManagerUtils.stringToInteger(classes)==1){//如果选中的是协议
			List<String> protocolMappingColumnList=new ArrayList<String>();
			ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByCode(protocolCode);
			if(protocol!=null){
				for(int j=0;j<protocol.getItems().size();j++){
					String columnName="";
					if(loadProtocolMappingColumnByTitleMap.containsKey(protocol.getItems().get(j).getTitle())){
						columnName=loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(j).getTitle()).getMappingColumn();
					}
					protocolMappingColumnList.add(columnName);
				}
				if(protocolMappingColumnList.size()>0){
					sql+=" and t.mappingcolumn in ("+StringManagerUtils.joinStringArr2(protocolMappingColumnList, ",")+")";
				}else{
					sql+=" and 1=2";
				}
			}
		
		}
				
				
		sql+=" order by t.id";
		String columns="["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"itemName\" ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataColumn")+"\",\"dataIndex\":\"itemColumn\",children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("calculationColumn")+"\",\"dataIndex\":\"calColumn\",children:[] }"
				+ "]";
		
		calColumnNameBuff.append("[''");
		CalculateColumnInfo calculateColumnInfo=MemoryDataManagerTask.getCalColumnsInfo(language);
		List<CalculateColumn> calculateColumnList=calculateColumnInfo.getSRPCalculateColumnList();

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
			String calColumnName=MemoryDataManagerTask.getCalculateColumnNameFromCode(calColumn,language);
			
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
	
	public String getProtocolRunStatusItems(String classes,String deviceType,String protocolCode,String language) {
		StringBuffer result_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
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
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",\"width\":50 ,\"children\":[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"itemName\",\"flex\":3,\"children\":[] }"
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
	
	public String getProtocolRunStatusItemsMeaning(String status,String deviceType,String protocolCode,String itemName,String itemColumn,String resolutionMode,String language) {
		StringBuffer result_json = new StringBuffer();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
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
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",\"width\":50 ,\"children\":[] },"
				+ "{ \"header\":\""+languageResourceMap.get("value")+"\",\"dataIndex\":\"value\",\"flex\":1,\"children\":[] },"
				+ "{ \"header\":\""+languageResourceMap.get("meaning")+"\",\"dataIndex\":\"meaning\",\"flex\":2,\"children\":[] }"
				+ "]";
		
		if(StringManagerUtils.stringToInteger(resolutionMode)==2){//数据量
			columns="["
					+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",\"width\":50 ,\"children\":[] },"
					+ "{ \"header\":\""+languageResourceMap.get("alarmLogic")+"\",\"dataIndex\":\"condition\",\"flex\":1,\"children\":[] },"
					+ "{ \"header\":\""+languageResourceMap.get("value")+"\",\"dataIndex\":\"value\",\"flex\":1,\"children\":[] }"
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
			result_json.append("\"condition\":\""+languageResourceMap.get("greaterThan")+"\",");
			if("1".equals(status)){
				result_json.append("\"value\":\""+runValue1+"\"},");
			}else{
				result_json.append("\"value\":\""+stopValue1+"\"},");
			}
			
			result_json.append("{\"id\":2,");
			result_json.append("\"condition\":\""+languageResourceMap.get("greatThanOrEqualTo")+"\",");
			if("1".equals(status)){
				result_json.append("\"value\":\""+runValue2+"\"},");
			}else{
				result_json.append("\"value\":\""+stopValue2+"\"},");
			}
			
			result_json.append("{\"id\":3,");
			result_json.append("\"condition\":\""+languageResourceMap.get("lessThanOrEqualTo")+"\",");
			if("1".equals(status)){
				result_json.append("\"value\":\""+runValue3+"\"},");
			}else{
				result_json.append("\"value\":\""+stopValue3+"\"},");
			}
			
			result_json.append("{\"id\":4,");
			result_json.append("\"condition\":\""+languageResourceMap.get("lessThan")+"\",");
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
	
	public void saveDatabaseColumnMappingTable(DatabaseMappingProHandsontableChangedData databaseMappingProHandsontableChangedData,String protocolType,String language) throws Exception {
		if(databaseMappingProHandsontableChangedData.getUpdatelist()!=null){
			String updateSql="";
			for(int i=0;i<databaseMappingProHandsontableChangedData.getUpdatelist().size();i++){
				String calColumn=MemoryDataManagerTask.getCalculateColumnFromName(StringManagerUtils.stringToInteger(protocolType), 
						databaseMappingProHandsontableChangedData.getUpdatelist().get(i).getCalColumnName(),language);
				updateSql="update tbl_datamapping t set t.calcolumn='"+calColumn+"'"
						+ " where t.name='"+databaseMappingProHandsontableChangedData.getUpdatelist().get(i).getItemName().replaceAll("&nbsp;", "").replaceAll("null", "")+"' "
						+ " and t.mappingcolumn='"+databaseMappingProHandsontableChangedData.getUpdatelist().get(i).getItemColumn().replaceAll("&nbsp;", "").replaceAll("null", "")+"' ";
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
							String displayItemSql="select t.id,t.itemid,t.itemname,t.itemcode,t.realtimeSort,t.bitindex,t.showlevel,t.realtimecurveconf,t.historycurveconf,t.type,t.matrix,t.unitid "
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
	
	
	public String getProtocolExportData(String protocolListStr){
		Gson gson=new Gson();
		List<ModbusProtocolConfig.Protocol> protocolList=new ArrayList<>();
		String[] protocolArr=protocolListStr.split(",");
		for(int i=0;i<protocolArr.length;i++){
			ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByCode(protocolArr[i]);
			if(protocol!=null){
				protocolList.add(protocol);
			}
		}
		return gson.toJson(protocolList);
	}
	
	public String exportProtocolInitData(String protocolListStr){
		Gson gson=new Gson();
		List<InitProtocol> initProtocolList=new ArrayList<>();
		String[] protocolArr=protocolListStr.split(",");
		for(int i=0;i<protocolArr.length;i++){
			ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByCode(protocolArr[i]);
			if(protocol!=null){
				InitProtocol initProtocol=new InitProtocol(protocol);
				if(initProtocol!=null){
					initProtocol.setMethod("update");
					initProtocolList.add(initProtocol);
				}
			}
		}
		return gson.toJson(initProtocolList);
	}
	
	public String getImportProtocolContentData(String id,String classes,String type,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		int index=1;
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("quantity")+"\",\"dataIndex\":\"quantity\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("storeDataType")+"\",\"dataIndex\":\"storeDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("IFDataType")+"\",\"dataIndex\":\"IFDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("prec")+"\",\"dataIndex\":\"prec\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("ratio")+"\",\"dataIndex\":\"ratio\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("RWType")+"\",\"dataIndex\":\"RWType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("acqMode")+"\",\"dataIndex\":\"acqMode\",width:80 ,children:[] }"
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
					String RWTypeName=languageResourceMap.get("readOnly");
					if("r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWTypeName=languageResourceMap.get("readOnly");
					}else if("w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWTypeName=languageResourceMap.get("writeOnly");
					}else if("rw".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWTypeName=languageResourceMap.get("readWrite");
					}
					
					String resolutionMode=languageResourceMap.get("numericValue");
					if(protocolConfig.getItems().get(j).getResolutionMode()==0){
						resolutionMode=languageResourceMap.get("switchingValue");
					}else if(protocolConfig.getItems().get(j).getResolutionMode()==1){
						resolutionMode=languageResourceMap.get("enumValue");
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
							+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?languageResourceMap.get("activeAcqModel"):languageResourceMap.get("passiveAcqModel"))+"\"},");
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
	
	public String getImportProtocolDisplayInstanceAcqItemsConfigData(String id,String typeStr,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
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
										realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+(realtimeCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+realtimeCurveConfObj.getColor();
									}
									if(historyCurveConfObj!=null){
										historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+(historyCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+historyCurveConfObj.getColor();
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
	
	public String getImportProtocolDisplayInstanceCalItemsConfigData(String id,String typeStr,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		List<CalItem> srpCalItemList=null;
		
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
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
			String key="srpCalItemList";
			srpCalItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
			
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
										realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+(realtimeCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+realtimeCurveConfObj.getColor();
									}
									if(historyCurveConfObj!=null){
										historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+(historyCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+historyCurveConfObj.getColor();
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
		
		if(srpCalItemList!=null){
			int index=1;
			for(CalItem calItem:srpCalItemList){
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
	
	public String getImportProtocolDisplayInstanceCtrlItemsConfigData(String id,String typeStr,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] }"
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
	
	public String getImportProtocolAlarmContentNumItemsConfigData(String id,String typeStr,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("upperLimit")+"\",\"dataIndex\":\"upperLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("lowerLimit")+"\",\"dataIndex\":\"lowerLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("hystersis")+"\",\"dataIndex\":\"hystersis\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
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
											+ "\"alarmSign\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmSign()==1?languageResourceMap.get("enable"):languageResourceMap.get("disable") )+"\","
											+ "\"isSendMessage\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMessage()==1?languageResourceMap.get("yes"):languageResourceMap.get("no") )+"\","
											+ "\"isSendMail\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMail()==1?languageResourceMap.get("yes"):languageResourceMap.get("no") )+"\"},");
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
	
	public String getImportProtocolAlarmContentCalNumItemsConfigData(String id,String typeStr,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		List<CalItem> calItemList=null;
		
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("upperLimit")+"\",\"dataIndex\":\"upperLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("lowerLimit")+"\",\"dataIndex\":\"lowerLimit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("hystersis")+"\",\"dataIndex\":\"hystersis\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null && exportProtocolConfig.getAlarmUnitList()!=null && exportProtocolConfig.getAlarmUnitList().size()>0 ){
			
			calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
			
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
									if(calItemList!=null){
										for(CalItem calItem:calItemList){
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
											+ "\"alarmSign\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmSign()==1?languageResourceMap.get("enable"):languageResourceMap.get("disable") )+"\","
											+ "\"isSendMessage\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMessage()==1?languageResourceMap.get("yes"):languageResourceMap.get("no") )+"\","
											+ "\"isSendMail\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMail()==1?languageResourceMap.get("yes"):languageResourceMap.get("no") )+"\"},");
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
	
	public String getImportProtocolAlarmContentSwitchItemsConfigData(String id,String typeStr,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("bit")+"\",\"dataIndex\":\"bitIndex\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("meaning")+"\",\"dataIndex\":\"meaning\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("switchItemAlarmValue")+"\",\"dataIndex\":\"value\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
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
											+ "\"value\":\""+("1".equalsIgnoreCase(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getValue()+"")?languageResourceMap.get("switchingOpenValue"):languageResourceMap.get("switchingCloseValue"))+"\","
											+ "\"delay\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getDelay()+"\","
											+ "\"alarmLevel\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmLevel()+"\","
											+ "\"alarmSign\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmSign()==1?languageResourceMap.get("enable"):languageResourceMap.get("disable") )+"\","
											+ "\"isSendMessage\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMessage()==1?languageResourceMap.get("yes"):languageResourceMap.get("no") )+"\","
											+ "\"isSendMail\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMail()==1?languageResourceMap.get("yes"):languageResourceMap.get("no") )+"\"},");
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
	
	public String getImportProtocolAlarmContentEnumItemsConfigData(String id,String typeStr,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("value")+"\",\"dataIndex\":\"value\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("meaning")+"\",\"dataIndex\":\"meaning\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
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
											+ "\"alarmSign\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmSign()==1?languageResourceMap.get("enable"):languageResourceMap.get("disable") )+"\","
											+ "\"isSendMessage\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMessage()==1?languageResourceMap.get("yes"):languageResourceMap.get("no") )+"\","
											+ "\"isSendMail\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMail()==1?languageResourceMap.get("yes"):languageResourceMap.get("no") )+"\"},");
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
	
	public String getImportProtocolAlarmContentFESDiagramResultItemsConfigData(String id,String typeStr,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
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
											+ "\"alarmSign\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmSign()==1?languageResourceMap.get("enable"):languageResourceMap.get("disable") )+"\","
											+ "\"isSendMessage\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMessage()==1?languageResourceMap.get("yes"):languageResourceMap.get("no") )+"\","
											+ "\"isSendMail\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMail()==1?languageResourceMap.get("yes"):languageResourceMap.get("no") )+"\"},");
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
	
	public String getImportProtocolAlarmContentRunStatusItemsConfigData(String id,String typeStr,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
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
											+ "\"alarmSign\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmSign()==1?languageResourceMap.get("enable"):languageResourceMap.get("disable") )+"\","
											+ "\"isSendMessage\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMessage()==1?languageResourceMap.get("yes"):languageResourceMap.get("no") )+"\","
											+ "\"isSendMail\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMail()==1?languageResourceMap.get("yes"):languageResourceMap.get("no") )+"\"},");
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
	
	public String getImportProtocolAlarmContentCommStatusItemsConfigData(String id,String typeStr,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("delay")+"(s)\",\"dataIndex\":\"delay\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("alarmSign")+"\",\"dataIndex\":\"alarmSign\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendMessage")+"\",\"dataIndex\":\"isSendMessage\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("isSendEmail")+"\",\"dataIndex\":\"isSendMail\",width:80 ,children:[] }"
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
											+ "\"alarmSign\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getAlarmSign()==1?languageResourceMap.get("enable"):languageResourceMap.get("disable") )+"\","
											+ "\"isSendMessage\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMessage()==1?languageResourceMap.get("yes"):languageResourceMap.get("no") )+"\","
											+ "\"isSendMail\":\""+(exportProtocolConfig.getAlarmUnitList().get(i).getAlarmItemList().get(j).getIsSendMail()==1?languageResourceMap.get("yes"):languageResourceMap.get("no") )+"\"},");
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
												displayUnitItem.setRealtimeSort(displayUnit.getDisplayItemList().get(j).getSort()>=0?displayUnit.getDisplayItemList().get(j).getSort():null);
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
																displayUnitItem.setRealtimeSort(displayUnit.getDisplayItemList().get(k).getSort()>=0?displayUnit.getDisplayItemList().get(k).getSort():null);
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
																	displayUnitItem.setRealtimeSort(displayUnit.getDisplayItemList().get(k).getSort()>=0?displayUnit.getDisplayItemList().get(k).getSort():null);
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
																	displayUnitItem.setRealtimeSort(displayUnit.getDisplayItemList().get(k).getSort()>=0?displayUnit.getDisplayItemList().get(k).getSort():null);
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
	
	
	public String importProtocolCheck(String data,String language){
		StringBuffer result_json = new StringBuffer();
		result_json.append("{\"success\":true,\"overlayList\":[");
		StringBuffer  errorDataBuff=new StringBuffer ();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
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
				result_json.append("{\"classes\":0,\"typeName\":\""+languageResourceMap.get("protocol")+"\",\"type\":0,\"id\":"+exportProtocolConfig.getProtocol().getId()+",\"text\":\""+exportProtocolConfig.getProtocol().getName()+"\"},");
				
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
									errorDataBuff.append("{\"classes\":1,\"typeName\":\""+languageResourceMap.get("acqInstance")+"\",\"type\":3,\"id\":"+exportProtocolConfig.getAcqInstanceList().get(j).getId()+",\"text\":\""+exportProtocolConfig.getAcqInstanceList().get(j).getName()+"\",\"errorInfo\":\""+languageResourceMap.get("acqUnitUnselected")+"\"},");
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
									errorDataBuff.append("{\"classes\":1,\"typeName\":\""+languageResourceMap.get("displayUnit")+"\",\"type\":1,\"id\":"+exportProtocolConfig.getDisplayUnitList().get(j).getId()+",\"text\":\""+exportProtocolConfig.getDisplayUnitList().get(j).getUnitName()+"\",\"errorInfo\":\""+languageResourceMap.get("acqUnitUnselected")+"\"},");
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
									errorDataBuff.append("{\"classes\":1,\"typeName\":\""+languageResourceMap.get("displayInstance")+"\",\"type\":4,\"id\":"+exportProtocolConfig.getDisplayInstanceList().get(j).getId()+",\"text\":\""+exportProtocolConfig.getDisplayInstanceList().get(j).getName()+"\",\"errorInfo\":\""+languageResourceMap.get("displayUnitUnselected")+"\"},");
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
									errorDataBuff.append("{\"classes\":1,\"typeName\":\""+languageResourceMap.get("alarmInstance")+"\",\"type\":5,\"id\":"+exportProtocolConfig.getAlarmInstanceList().get(j).getId()+",\"text\":\""+exportProtocolConfig.getAlarmInstanceList().get(j).getName()+"\",\"errorInfo\":\""+languageResourceMap.get("alarmUnitUnselected")+"\"},");
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
									result_json.append("{\"classes\":1,\"typeName\":\""+languageResourceMap.get("acqUnit")+"\",\"type\":0,\"id\":"+acqUnit.getId()+",\"text\":\""+acqUnit.getUnitName()+"\"},");
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
													result_json.append("{\"classes\":2,\"typeName\":\""+languageResourceMap.get("acqGroup")+"\",\"type\":0,\"id\":"+acqUnit.getAcqGroupList().get(j).getId()+",\"text\":\""+acqUnit.getAcqGroupList().get(j).getGroupName()+"\",\"unitId\":"+acqUnit.getId()+"},");
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
													result_json.append("{\"classes\":1,\"typeName\":\""+languageResourceMap.get("acqInstance")+"\",\"type\":3,\"id\":"+acqInstance.getId()+",\"text\":\""+acqInstance.getName()+"\"},");
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
													result_json.append("{\"classes\":1,\"typeName\":\""+languageResourceMap.get("displayUnit")+"\",\"type\":1,\"id\":"+displayUnit.getId()+",\"text\":\""+displayUnit.getUnitName()+"\"},");
													
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
																	result_json.append("{\"classes\":1,\"typeName\":\""+languageResourceMap.get("displayInstance")+"\",\"type\":4,\"id\":"+exportProtocolConfig.getDisplayInstanceList().get(m).getId()+",\"text\":\""+exportProtocolConfig.getDisplayInstanceList().get(m).getName()+"\"},");
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
									result_json.append("{\"classes\":1,\"typeName\":\""+languageResourceMap.get("alarmUnit")+"\",\"type\":2,\"id\":"+alarmUnit.getId()+",\"text\":\""+alarmUnit.getUnitName()+"\"},");
									
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
													result_json.append("{\"classes\":1,\"typeName\":\""+languageResourceMap.get("alarmInstance")+"\",\"type\":5,\"id\":"+exportProtocolConfig.getAlarmInstanceList().get(m).getId()+",\"text\":\""+exportProtocolConfig.getAlarmInstanceList().get(m).getName()+"\"},");
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
	
	public void doAcquisitionUnitAdd(AcquisitionUnit acquisitionUnit) throws Exception {
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
	
	public void grantAcquisitionItemsPermission(AcquisitionGroupItem acquisitionUnitItem) throws Exception {
		getBaseDao().saveOrUpdateObject(acquisitionUnitItem);
	}
	
	public void grantAcquisitionGroupsPermission(AcquisitionUnitGroup r) throws Exception {
		getBaseDao().saveOrUpdateObject(r);
	}
	
	public void grantDisplayItemsPermission(DisplayUnitItem displayUnitItem) throws Exception {
		getBaseDao().saveOrUpdateObject(displayUnitItem);
	}
	
	public void grantReportItemsPermission(ReportUnitItem reportUnitItem) throws Exception {
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
	
	public void deleteCurrentReportUnitOwnItems(final String unitId,final String reportType,String sort) throws Exception {
		final String hql = "DELETE ReportUnitItem u "
				+ " where u.unitId ="+unitId
				+" and u.reportType="+reportType
				+" and u.sort="+sort;
		getBaseDao().bulkObjectDelete(hql);
	}
	
	public void doModbusProtocolInstanceAdd(ProtocolInstance protocolInstance) throws Exception {
		getBaseDao().addObject(protocolInstance);
	}
	public void doModbusProtocolInstanceEdit(ProtocolInstance protocolInstance) throws Exception {
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
	
	public void grantAlarmItemsPermission(AlarmUnitItem alarmUnitItem) throws Exception {
		getBaseDao().addObject(alarmUnitItem);
	}
	
	public void doModbusProtocolDisplayInstanceEdit(ProtocolDisplayInstance protocolDisplayInstance) throws Exception {
		getBaseDao().updateObject(protocolDisplayInstance);
	}
	
	public void doModbusProtocolReportUnitEdit(T reportUnit) throws Exception {
		getBaseDao().updateObject(reportUnit);
	}
	
	public void doModbusProtocolReportInstanceEdit(ProtocolReportInstance protocolReportInstance) throws Exception {
		getBaseDao().updateObject(protocolReportInstance);
	}
	
	public void doModbusProtocolAlarmInstanceEdit(ProtocolAlarmInstance protocolAlarmInstance) throws Exception {
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
		String sql = "update tbl_device t set t.reportinstancecode='' where t.reportinstancecode in ( select t2.code from tbl_protocolreportinstance t2  where t2.unitid in ("+ids+") )";
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
		String sql = "update tbl_device t set t.reportinstancecode='' where t.reportinstancecode in (select t2.code from tbl_protocolreportinstance t2 where t2.id in("+ids+"))";
		delorUpdateCount=this.getBaseDao().updateOrDeleteBySql(sql);
		
		final String hql = "DELETE ProtocolReportInstance u where u.id in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}
	
	public String getProtocolDeviceTypeChangeProtocolList(String deviceTypeIds,String language) throws Exception {
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String[] deviceTypeIdArr=deviceTypeIds.split(",");
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"name\",width:120 ,children:[] },"
				+ "{ \"header\":\"设备类型\",\"dataIndex\":\"deviceTypeName\",width:120 ,children:[] }"
				+ "]";
		
		String sql="select t.id,t.name,t.devicetype,t2.allpath_"+language+" "
				+ " from tbl_protocol t, viw_devicetypeinfo t2 "
				+ " where t.devicetype=t2.id ";
		if(StringManagerUtils.isNotNull(deviceTypeIds)){
			sql+= " and t.devicetype in("+deviceTypeIds+") ";
		}else{
			sql+=" 1=2";
		}
		sql+= " order by t.sort";
		List<?> list=this.findCallSql(sql);
		
		
		result_json.append("{\"success\":true,\"columns\":"+columns+",\"totalCount\":"+list.size()+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"name\":\""+obj[1]+"\",");
			result_json.append("\"deviceType\":\""+obj[2]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[3]+"\"},");
		}
		
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public void changeProtocolDeviceType(String selectedProtocolId,String selectedDeviceTypeId) throws Exception {
		if(StringManagerUtils.stringToInteger(selectedDeviceTypeId)>0 && StringManagerUtils.isNotNull(selectedProtocolId)){
			String sql = "update tbl_protocol t set t.devicetype="+selectedDeviceTypeId+" where t.id in ("+selectedProtocolId+")";
			this.getBaseDao().updateOrDeleteBySql(sql);
			
			try{
				ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
				String [] protocolArr=selectedProtocolId.split(",");
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					if(StringManagerUtils.existOrNot(protocolArr, modbusProtocolConfig.getProtocol().get(i).getId()+"")){
						modbusProtocolConfig.getProtocol().get(i).setDeviceType(StringManagerUtils.stringToInteger(selectedDeviceTypeId));
					}
				}
				MemoryDataManagerTask.updateProtocolConfig(modbusProtocolConfig);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				
			}
		}
	}
	
	public String getProtocolAcqUnitExportData(String unitListStr){
		StringBuffer result_json = new StringBuffer();
		result_json.append("[");
		String acqUnitSql="select t.id,t.unit_code,t.unit_name,t.protocol,t.remark from TBL_ACQ_UNIT_CONF t where t.id in ("+unitListStr+") order by t.id";
		List<?> acqUnitQueryList = this.findCallSql(acqUnitSql);
		
		for(int i=0;i<acqUnitQueryList.size();i++){
			result_json.append("{");
			Object[] acqUnitObj=(Object[])acqUnitQueryList.get(i);
			result_json.append("\"Id\":"+StringManagerUtils.stringToInteger(acqUnitObj[0]+"")+",");
			result_json.append("\"UnitCode\":\""+acqUnitObj[1]+"\",");
			result_json.append("\"UnitName\":\""+acqUnitObj[2]+"\",");
			result_json.append("\"Protocol\":\""+acqUnitObj[3]+"\",");
			result_json.append("\"Remark\":\""+acqUnitObj[4]+"\",");
			
			
			String acqGroupSql="select t.id,t.group_code,t.group_name,t.grouptiminginterval,t.groupsavinginterval,t.protocol,t.type,t.remark,t3.id as unitid "
					+ " from tbl_acq_group_conf t,tbl_acq_group2unit_conf t2,tbl_acq_unit_conf t3 "
					+ " where t.id=t2.groupid and t2.unitid=t3.id "
					+ " and t3.id ="+StringManagerUtils.stringToInteger(acqUnitObj[0]+"")
					+ " order by t3.id,t.id";
			String acqItemSql="select t.id,t.itemid,t.itemname,t.itemcode,t.groupid,t.bitindex,t.matrix,t.dailytotalcalculate,t.dailytotalcalculatename "
					+ " from tbl_acq_item2group_conf t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4 "
					+ " where t.groupid=t2.id and t2.id=t3.groupid and t3.unitid=t4.id "
					+ " and t4.id = "+StringManagerUtils.stringToInteger(acqUnitObj[0]+"")
					+ " order by t4.id,t2.id,t.id";
			List<?> acqGroupQueryList = this.findCallSql(acqGroupSql);
			List<?> acqItemQueryList = this.findCallSql(acqItemSql);
			
			result_json.append("\"GroupList\":[");
			for(int j=0;j<acqGroupQueryList.size();j++){
				Object[] acqGroupObj=(Object[])acqGroupQueryList.get(j);
				int groupId=StringManagerUtils.stringToInteger(acqGroupObj[0]+"");
				result_json.append("{");
				result_json.append("\"Id\":"+groupId+",");
				result_json.append("\"GroupCode\":\""+acqGroupObj[1]+"\",");
				result_json.append("\"GroupName\":\""+acqGroupObj[2]+"\",");
				result_json.append("\"GroupTimingInterval\":"+StringManagerUtils.stringToInteger(acqGroupObj[3]+"")+",");
				result_json.append("\"GroupSavingInterval\":"+StringManagerUtils.stringToInteger(acqGroupObj[4]+"")+",");
				result_json.append("\"Protocol\":\""+acqGroupObj[5]+"\",");
				result_json.append("\"Type\":"+StringManagerUtils.stringToInteger(acqGroupObj[6]+"")+",");
				result_json.append("\"Remark\":\""+((acqGroupObj[7]+"").replaceAll("null", ""))+"\",");
				result_json.append("\"ItemList\":[");
				for(int k=0;k<acqItemQueryList.size();k++){
					Object[] acqItemObj=(Object[])acqItemQueryList.get(k);
					if(StringManagerUtils.stringToInteger(acqItemObj[4]+"")==groupId){
						result_json.append("{");
						result_json.append("\"Id\":"+StringManagerUtils.stringToInteger(acqItemObj[0]+"")+",");
						result_json.append("\"ItemId\":"+StringManagerUtils.stringToInteger(acqItemObj[1]+"")+",");
						result_json.append("\"ItemName\":\""+((acqItemObj[2]+"").replaceAll("null", ""))+"\",");
						result_json.append("\"ItemCode\":\""+((acqItemObj[3]+"").replaceAll("null", ""))+"\",");
						if(StringManagerUtils.isInteger(acqItemObj[5]+"")){
							result_json.append("\"BitIndex\":"+StringManagerUtils.stringToInteger(acqItemObj[5]+"")+",");
						}else{
							result_json.append("\"BitIndex\":-99,");
						}
						result_json.append("\"Matrix\":\""+((acqItemObj[6]+"").replaceAll("null", ""))+"\",");
						result_json.append("\"DailyTotalCalculate\":"+StringManagerUtils.stringToInteger(acqItemObj[7]+"")+",");
						result_json.append("\"DailyTotalCalculateName\":\""+((acqItemObj[8]+"").replaceAll("null", ""))+"\"");
						result_json.append("},");
					}
				}
				
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
				result_json.append("]");
				result_json.append("},");
			}
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]");
			result_json.append("},");
		}
		
		
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString();
	}
	
	public String getProtocolAlarmUnitExportData(String unitListStr){
		StringBuffer result_json = new StringBuffer();
		result_json.append("[");
		String alarmUnitSql="select t.id,t.unit_code,t.unit_name,t.protocol,t.remark from TBL_ALARM_UNIT_CONF t where t.id in("+unitListStr+")";
		String alarmItemSql="select t.id,t.itemid,t.itemname,t.itemcode,t.itemaddr,t.value,t.upperlimit,t.lowerlimit,t.hystersis,t.delay,"
				+ " t.alarmlevel,t.alarmsign,t.type,t.bitindex,t.issendmessage,t.issendmail,t.unitid "
				+ " from tbl_alarm_item2unit_conf t,  TBL_ALARM_UNIT_CONF t2 "
				+ " where t.unitid=t2.id "
				+ " and t2.id in("+unitListStr+") "
				+ " order by t2.id,t.id";
		List<?> alarmUnitQueryList = this.findCallSql(alarmUnitSql);
		List<?> alarmItemQueryList = this.findCallSql(alarmItemSql);
		
		for(int i=0;i<alarmUnitQueryList.size();i++){
			Object[] alarmUnitObj=(Object[])alarmUnitQueryList.get(i);
			int unitId=StringManagerUtils.stringToInteger(alarmUnitObj[0]+"");
			result_json.append("{");
			result_json.append("\"Id\":"+unitId+",");
			result_json.append("\"UnitCode\":\""+alarmUnitObj[1]+"\",");
			result_json.append("\"UnitName\":\""+alarmUnitObj[2]+"\",");
			result_json.append("\"Protocol\":\""+alarmUnitObj[3]+"\",");
			result_json.append("\"Remark\":\""+alarmUnitObj[4]+"\",");
			result_json.append("\"ItemList\":[");
			for(int j=0;j<alarmItemQueryList.size();j++){
				Object[] alarmItemObj=(Object[])alarmItemQueryList.get(j);
				if(StringManagerUtils.stringToInteger(alarmItemObj[16]+"")==unitId){
					result_json.append("{");
					result_json.append("\"Id\":"+StringManagerUtils.stringToInteger(alarmItemObj[0]+"")+",");
					result_json.append("\"ItemId\":\""+(StringManagerUtils.isInteger(alarmItemObj[1]+"")?StringManagerUtils.stringToInteger(alarmItemObj[1]+""):"")+"\",");
					result_json.append("\"ItemName\":\""+alarmItemObj[2]+"\",");
					result_json.append("\"ItemCode\":\""+alarmItemObj[3]+"\",");
					result_json.append("\"ItemAddr\":\""+(StringManagerUtils.isInteger(alarmItemObj[4]+"")?StringManagerUtils.stringToInteger(alarmItemObj[4]+""):"")+"\",");
					result_json.append("\"Value\":"+StringManagerUtils.stringToFloat(alarmItemObj[5]+"")+",");
					
					result_json.append("\"UpperLimit\":\""+(StringManagerUtils.isNum(alarmItemObj[6]+"")?StringManagerUtils.stringToFloat(alarmItemObj[6]+""):"")+"\",");
					result_json.append("\"LowerLimit\":\""+(StringManagerUtils.isNum(alarmItemObj[7]+"")?StringManagerUtils.stringToFloat(alarmItemObj[7]+""):"")+"\",");
					result_json.append("\"Hystersis\":\""+(StringManagerUtils.isNum(alarmItemObj[8]+"")?StringManagerUtils.stringToFloat(alarmItemObj[8]+""):"")+"\",");
					
					result_json.append("\"Delay\":"+StringManagerUtils.stringToInteger(alarmItemObj[9]+"")+",");
					
					result_json.append("\"AlarmLevel\":"+StringManagerUtils.stringToInteger(alarmItemObj[10]+"")+",");
					result_json.append("\"AlarmSign\":"+StringManagerUtils.stringToInteger(alarmItemObj[11]+"")+",");
					result_json.append("\"Type\":"+StringManagerUtils.stringToInteger(alarmItemObj[12]+"")+",");
					
					result_json.append("\"BitIndex\":\""+(StringManagerUtils.isInteger(alarmItemObj[13]+"")?StringManagerUtils.stringToInteger(alarmItemObj[13]+""):"")+"\",");
					
					result_json.append("\"SendMessage\":"+StringManagerUtils.stringToInteger(alarmItemObj[14]+"")+",");
					result_json.append("\"SendMail\":"+StringManagerUtils.stringToInteger(alarmItemObj[15]+"")+"");
					result_json.append("},");
				}
			}
			
			
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]");
			result_json.append("},");
		}
		
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString();
	}
	
	public String getProtocolDisplayUnitExportData(String unitListStr){
		StringBuffer result_json = new StringBuffer();
		result_json.append("[");
		String displayUnitSql="select t.id,t.unit_code,t.unit_name,t.protocol,t2.unit_name as acqUnit,t.remark,t.calculatetype "
				+ " from tbl_display_unit_conf t,tbl_acq_unit_conf t2 "
				+ " where t.acqunitid=t2.id and t.id in ("+unitListStr+") "
				+ " order by t2.id,t.id";
		String displayItemSql="select t.id,t.itemid,t.itemname,t.itemcode,t.realtimeSort,t.bitindex,t.showlevel,"
				+ "t.realtimecurveconf,t.historycurveconf,t.type,t.matrix,t.historysort,"
				+ "t.realtimeColor,t.realtimeBgColor,t.historyColor,t.historyBgColor,"
				+ "t.unitid "
				+ " from tbl_display_items2unit_conf t, tbl_display_unit_conf t2 "
				+ " where t.unitid=t2.id "
				+ " and t2.id in ("+unitListStr+")"
				+ " order by t2.id,t.type,t.id";
		List<?> displayUnitQueryList = this.findCallSql(displayUnitSql);
		List<?> displayItemQueryList = this.findCallSql(displayItemSql);
		
		for(int i=0;i<displayUnitQueryList.size();i++){
			Object[] displayUnitObj=(Object[])displayUnitQueryList.get(i);
			int unitId=StringManagerUtils.stringToInteger(displayUnitObj[0]+"");
			result_json.append("{");
			result_json.append("\"Id\":"+unitId+",");
			result_json.append("\"UnitCode\":\""+displayUnitObj[1]+"\",");
			result_json.append("\"UnitName\":\""+displayUnitObj[2]+"\",");
			result_json.append("\"Protocol\":\""+displayUnitObj[3]+"\",");
			result_json.append("\"AcqUnit\":\""+displayUnitObj[4]+"\",");
			result_json.append("\"Remark\":\""+displayUnitObj[5]+"\",");
			result_json.append("\"CalculateType\":"+StringManagerUtils.stringToInteger(displayUnitObj[6]+"")+",");
			result_json.append("\"ItemList\":[");
			for(int j=0;j<displayItemQueryList.size();j++){
				Object[] displayItemObj=(Object[])displayItemQueryList.get(j);
				if(StringManagerUtils.stringToInteger(displayItemObj[16]+"")==unitId){
					result_json.append("{");
					result_json.append("\"Id\":"+StringManagerUtils.stringToInteger(displayItemObj[0]+"")+",");
					result_json.append("\"ItemId\":\""+(StringManagerUtils.isInteger(displayItemObj[1]+"")?StringManagerUtils.stringToInteger(displayItemObj[1]+""):"")+"\",");
					result_json.append("\"ItemName\":\""+displayItemObj[2]+"\",");
					result_json.append("\"ItemCode\":\""+displayItemObj[3]+"\",");
					result_json.append("\"RealtimeSort\":"+(StringManagerUtils.isInteger(displayItemObj[4]+"")?StringManagerUtils.stringToInteger(displayItemObj[4]+""):-99)+",");
					result_json.append("\"BitIndex\":"+(StringManagerUtils.isInteger(displayItemObj[5]+"")?StringManagerUtils.stringToInteger(displayItemObj[5]+""):-99)+",");
					result_json.append("\"ShowLevel\":"+(StringManagerUtils.isInteger(displayItemObj[6]+"")?StringManagerUtils.stringToInteger(displayItemObj[6]+""):-99)+",");
					result_json.append("\"RealtimeCurveConf\":"+(displayItemObj[7]!=null?(displayItemObj[7]+"").replaceAll("null", ""):"{}")+",");
					result_json.append("\"HistoryCurveConf\":"+(displayItemObj[8]!=null?(displayItemObj[8]+"").replaceAll("null", ""):"{}")+",");
					result_json.append("\"Type\":"+StringManagerUtils.stringToInteger(displayItemObj[9]+"")+",");
					result_json.append("\"Matrix\":\""+((displayItemObj[10]+"").replaceAll("null", ""))+"\",");
					result_json.append("\"HistorySort\":"+(StringManagerUtils.isInteger(displayItemObj[11]+"")?StringManagerUtils.stringToInteger(displayItemObj[11]+""):-99)+",");
					result_json.append("\"RealtimeColor\":\""+((displayItemObj[12]+"").replaceAll("null", ""))+"\",");
					result_json.append("\"RealtimeBgColor\":\""+((displayItemObj[13]+"").replaceAll("null", ""))+"\",");
					result_json.append("\"HistoryColor\":\""+((displayItemObj[14]+"").replaceAll("null", ""))+"\",");
					result_json.append("\"HistoryBgColor\":\""+((displayItemObj[15]+"").replaceAll("null", ""))+"\"");
					result_json.append("},");
				}
			}
			
			
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]");
			result_json.append("},");
		}
		
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString();
	}
	
	public String getProtocolReportUnitExportData(String unitListStr,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		result_json.append("[");
		String reportUnitSql="select t.id,t.unit_code,t.unit_name,t.calculatetype,"
				+ "t.singlewelldailyreporttemplate,t.singlewellrangereporttemplate,t.productionreporttemplate,"
				+ "t.sort "
				+ "from tbl_report_unit_conf t"
				+ " where t.id in("+unitListStr+")"
				+ " order by t.sort";
		String reportItemSql="select t.id,t.itemid,t.itemname,t.itemcode,t.showlevel,"
				+ "t.sumsign,t.averagesign,"
				+ "t.reportcurveconf,"
				+ "decode(t.curvestattype,1,'"+languageResourceMap.get("curveStatType_sum")+"',2,'"+languageResourceMap.get("curveStatType_avg")+"',3,'"+languageResourceMap.get("curveStatType_max")+"',4,'"+languageResourceMap.get("curveStatType_min")+"','') as curvestattype,"
				+ "t.datatype,"
				+ "decode(t.reporttype,0,'"+languageResourceMap.get("deviceDailyReport")+"',1,'"+languageResourceMap.get("areaDailyReport")+"',2,'"+languageResourceMap.get("deviceHourlyReport")+"','') as reporttype,"
				+ "t.prec,"
				+ "decode(t.totalType,1,'"+languageResourceMap.get("maxValue")+"',2,'"+languageResourceMap.get("minValue")+"',3,'"+languageResourceMap.get("avgValue")+"',4,'"+languageResourceMap.get("newestValue")+"',5,'"+languageResourceMap.get("oldestValue")+"',6,'"+languageResourceMap.get("dailyTotalValue")+"','') as totalType,"
				+ "t.datasource,"
				+ "t.sort,t.matrix,"
				+ "t.unitid "
				+ "from tbl_report_items2unit_conf t,tbl_report_unit_conf t2  "
				+ "where t.unitid=t2.id and t2.id in("+unitListStr+")"
				+ "order by t2.sort,t.reporttype,t.sort";
		List<?> reportUnitQueryList = this.findCallSql(reportUnitSql);
		List<?> reportItemQueryList = this.findCallSql(reportItemSql);
		
		for(int i=0;i<reportUnitQueryList.size();i++){
			Object[] reportUnitObj=(Object[])reportUnitQueryList.get(i);
			int unitId=StringManagerUtils.stringToInteger(reportUnitObj[0]+"");
			
			String singleWellDailyReportTemplateName=MemoryDataManagerTask.getSingleWellDailyReportTemplateNameFromCode(reportUnitObj[4]+"");
			String singleWellRangeReportTemplateName=MemoryDataManagerTask.getSingleWellRangeReportTemplateNameFromCode(reportUnitObj[5]+"");
			String productionReportTemplateName=MemoryDataManagerTask.getProductionReportTemplateNameFromCode(reportUnitObj[6]+"");
			
			result_json.append("{");
			result_json.append("\"Id\":"+unitId+",");
			result_json.append("\"UnitCode\":\""+reportUnitObj[1]+"\",");
			result_json.append("\"UnitName\":\""+reportUnitObj[2]+"\",");
			result_json.append("\"CalculateType\":"+reportUnitObj[3]+",");
			result_json.append("\"SingleWellDailyReportTemplate\":\""+singleWellDailyReportTemplateName+"\",");
			result_json.append("\"SingleWellRangeReportTemplate\":\""+singleWellRangeReportTemplateName+"\",");
			result_json.append("\"ProductionReportTemplate\":\""+productionReportTemplateName+"\",");
			result_json.append("\"Sort\":\""+reportUnitObj[7]+"\",");
			result_json.append("\"ItemList\":[");
			for(int j=0;j<reportItemQueryList.size();j++){
				Object[] reportItemObj=(Object[])reportItemQueryList.get(j);
				if(StringManagerUtils.stringToInteger(reportItemObj[reportItemObj.length-1]+"")==unitId){
					result_json.append("{");
					result_json.append("\"Id\":"+StringManagerUtils.stringToInteger(reportItemObj[0]+"")+",");
					result_json.append("\"ItemId\":\""+(StringManagerUtils.isInteger(reportItemObj[1]+"")?StringManagerUtils.stringToInteger(reportItemObj[1]+""):"")+"\",");
					result_json.append("\"ItemName\":\""+reportItemObj[2]+"\",");
					result_json.append("\"ItemCode\":\""+reportItemObj[3]+"\",");
					result_json.append("\"ShowLevel\":\""+(StringManagerUtils.isInteger(reportItemObj[4]+"")?StringManagerUtils.stringToInteger(reportItemObj[4]+""):"")+"\",");
					result_json.append("\"SumSign\":\""+(StringManagerUtils.isInteger(reportItemObj[5]+"")?StringManagerUtils.stringToInteger(reportItemObj[5]+""):"")+"\",");
					result_json.append("\"AverageSign\":\""+(StringManagerUtils.isInteger(reportItemObj[6]+"")?StringManagerUtils.stringToInteger(reportItemObj[6]+""):"")+"\",");
					result_json.append("\"ReportCurveConf\":"+(reportItemObj[7]!=null?(reportItemObj[7]+"").replaceAll("null", ""):"{}")+",");
					result_json.append("\"CurveStatType\":\""+(reportItemObj[8]+"").replaceAll("null", "")+"\",");
					result_json.append("\"DataType\":\""+(reportItemObj[9]+"").replaceAll("null", "")+"\",");
					result_json.append("\"ReportType\":\""+(reportItemObj[10]+"").replaceAll("null", "")+"\",");
					result_json.append("\"Prec\":\""+(reportItemObj[11]+"").replaceAll("null", "")+"\",");
					result_json.append("\"TotalType\":\""+(reportItemObj[12]+"").replaceAll("null", "")+"\",");
					result_json.append("\"DataSource\":\""+(reportItemObj[13]+"").replaceAll("null", "")+"\",");
					result_json.append("\"Sort\":\""+(reportItemObj[14]+"").replaceAll("null", "")+"\",");
					result_json.append("\"Matrix\":\""+(reportItemObj[15]+"").replaceAll("null", "")+"\"");
					result_json.append("},");
				}
			}
			
			
			if (result_json.toString().endsWith(",")) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]");
			result_json.append("},");
		}
		
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString();
	}
	
	public String getProtocolAcqInstanceExportData(String instanceList){
		StringBuffer result_json = new StringBuffer();
		result_json.append("[");
		String sql="select t.id,t.name,t.code,t.acqprotocoltype,t.ctrlprotocoltype,"//0~4
				+ " t.SignInPrefixSuffixHex,t.signinprefix,t.signinsuffix,t.SignInIDHex,"//5~8
				+ " t.HeartbeatPrefixSuffixHex,t.heartbeatprefix,t.heartbeatsuffix,"//9~11
				+ " t.packetsendinterval,"//12
				+ " t.sort,t.unitid,t2.unit_name, "//13~15
				+ " t2.protocol"
				+ " from tbl_protocolinstance t ,tbl_acq_unit_conf t2 "
				+ " where t.unitid=t2.id ";
		if(StringManagerUtils.isNotNull(instanceList)){
			sql+=" and t.id in ("+instanceList+")";
		}else{
			sql+=" and 1=2 ";
		}	
		sql+= " order by t.sort";
		List<?> instanceQueryList = this.findCallSql(sql);
		for(int i=0;i<instanceQueryList.size();i++){
			Object[] obj=(Object[])instanceQueryList.get(i);
			result_json.append("{");
			result_json.append("\"Id\":"+obj[0]+",");
			result_json.append("\"Name\":\""+obj[1]+"\",");
			result_json.append("\"Code\":\""+obj[2]+"\",");
			result_json.append("\"AcqProtocolType\":\""+obj[3]+"\",");
			result_json.append("\"CtrlProtocolType\":\""+obj[4]+"\",");
			result_json.append("\"SignInPrefixSuffixHex\":"+StringManagerUtils.stringToInteger(obj[5]+"")+",");
			result_json.append("\"SignInPrefix\":\""+((obj[6]+"").replaceAll("null", ""))+"\",");
			result_json.append("\"SigninSuffix\":\""+((obj[7]+"").replaceAll("null", ""))+"\",");
			result_json.append("\"SignInIDHex\":"+StringManagerUtils.stringToInteger(obj[8]+"")+",");
			
			result_json.append("\"HeartbeatPrefixSuffixHex\":"+StringManagerUtils.stringToInteger(obj[9]+"")+",");
			result_json.append("\"HeartbeatPrefix\":\""+((obj[10]+"").replaceAll("null", ""))+"\",");
			result_json.append("\"HeartbeatSuffix\":\""+((obj[11]+"").replaceAll("null", ""))+"\",");
			
			
			result_json.append("\"PacketSendInterval\":"+StringManagerUtils.stringToInteger(obj[12]+"")+",");
			result_json.append("\"Sort\":"+StringManagerUtils.stringToInteger(obj[13]+"")+",");
			result_json.append("\"UnitId\":"+StringManagerUtils.stringToInteger(obj[14]+"")+",");
			result_json.append("\"UnitName\":\""+obj[15]+"\",");
			result_json.append("\"Protocol\":\""+obj[16]+"\"");

			result_json.append("},");
		}
		
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString();
	}
	
	public String getProtocolAcqInstanceInitExportData(String instanceList){
		Gson gson=new Gson();
		List<InitInstance> initInstanceList=new ArrayList<>();
		
		String instanceSql="select t.name,t.acqprotocoltype,t.ctrlprotocoltype,"
				+ " t.SignInPrefixSuffixHex,t.signinprefix,t.signinsuffix,t.SignInIDHex,"
				+ " t.HeartbeatPrefixSuffixHex,t.heartbeatprefix,t.heartbeatsuffix,"
				+ " t.packetsendinterval,"
				+ " t3.name "
				+ " from tbl_protocolinstance t "
				+ " left outer join tbl_acq_unit_conf t2 on t.unitid=t2.id "
				+ " left outer join tbl_protocol t3 on t2.protocol=t3.name"
				+ " where 1=1";
		String sql="select t5.name as instanceName,t2.id as groupId,t2.group_name,t2.type,t2.grouptiminginterval,"
				+ " t.itemname,t.itemcode, t6.name as protocolName "
				+ " from tbl_acq_item2group_conf t,"
				+ " tbl_acq_group_conf t2,"
				+ " tbl_acq_group2unit_conf t3,"
				+ " tbl_acq_unit_conf t4,"
				+ " tbl_protocolinstance t5,"
				+ " tbl_protocol t6 "
				+ " where t.groupid=t2.id and t2.id=t3.groupid and t3.unitid=t4.id and t4.id=t5.unitid and t4.protocol=t6.name ";
		
		
		if(StringManagerUtils.isNotNull(instanceList)){
			sql+=" and t5.id in("+instanceList+")";
			instanceSql+=" and t.id in("+instanceList+")";
		}
		sql+= " order by t5.sort,t.groupid,t.id";
		instanceSql+=" order by t.sort";
		
		List<Object[]> instanceQueryList=OracleJdbcUtis.query(instanceSql);
		List<Object[]> itemsQueryList=OracleJdbcUtis.query(sql);
		
		
		
		if(instanceQueryList!=null && instanceQueryList.size()>0){
			Map<String,InitInstance> InstanceListMap=new LinkedHashMap<String,InitInstance>();
			for(Object[] obj:instanceQueryList){
				InitInstance initInstance=new InitInstance();
				initInstance.setMethod("update");
				initInstance.setInstanceName(obj[0]+"");
				initInstance.setProtocolName(obj[11]+"");
				initInstance.setAcqProtocolType(obj[1]+"");
				initInstance.setCtrlProtocolType(obj[2]+"");
				
				initInstance.setSignInPrefixSuffixHex(StringManagerUtils.stringToInteger(obj[3]+"")==1);
				initInstance.setSignInPrefix((obj[4]+"").replaceAll("null", ""));
				initInstance.setSignInSuffix((obj[5]+"").replaceAll("null", ""));
				initInstance.setSignInIDHex(StringManagerUtils.stringToInteger(obj[6]+"")==1);
				
				initInstance.setHeartbeatPrefixSuffixHex(StringManagerUtils.stringToInteger(obj[7]+"")==1);
				initInstance.setHeartbeatPrefix((obj[8]+"").replaceAll("null", ""));
				initInstance.setHeartbeatSuffix((obj[9]+"").replaceAll("null", ""));
				
				initInstance.setPacketSendInterval(StringManagerUtils.stringToInteger(obj[10]+""));
				
				initInstance.setAcqGroup(new ArrayList<InitInstance.Group>());
				initInstance.setCtrlGroup(new ArrayList<InitInstance.Group>());
				
				InstanceListMap.put(initInstance.getInstanceName(), initInstance);
			}
			
			for(Entry<String, InitInstance> entry:InstanceListMap.entrySet()){
				InitInstance initInstance=entry.getValue();
				String key=entry.getKey();
				ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(initInstance.getProtocolName());
				
				for(Object[] obj:itemsQueryList){
					String instanceName=obj[0]+"";
					int groupId=StringManagerUtils.stringToInteger(obj[1]+"");
					String groupName=obj[2]+"";
					int groupType=StringManagerUtils.stringToInteger(obj[3]+"");
					int groupTimingInterval=StringManagerUtils.stringToInteger(obj[4]+"");
					
					String itemName=obj[5]+"";
					String protocolName=obj[7]+"";
					if(instanceName.equalsIgnoreCase(initInstance.getInstanceName())){
						if(!initInstance.containGroup(groupType, groupId)){
							InitInstance.Group group=new InitInstance.Group();
							group.setId(groupId);
							group.setGroupTimingInterval(groupTimingInterval);
							group.setAddr(new ArrayList<Integer>());
							if(groupType==0){
								initInstance.getAcqGroup().add(group);
							}else if(groupType==1){
								initInstance.getCtrlGroup().add(group);
							}
						}
						InitInstance.Group group=initInstance.getGroup(groupType, groupId);
						if(group!=null){
							ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, itemName);
							if(item!=null && !StringManagerUtils.existOrNot(group.getAddr(), item.getAddr())){
								group.getAddr().add(item.getAddr());
							}
						}
					}
				}
			}
			for(Entry<String, InitInstance> entry:InstanceListMap.entrySet()){
				InitInstance initInstance=entry.getValue();
				String key=entry.getKey();
				for(InitInstance.Group group:initInstance.getAcqGroup() ){
					Collections.sort(group.getAddr());
				}
				for(InitInstance.Group group:initInstance.getCtrlGroup() ){
					Collections.sort(group.getAddr());
				}
				
				initInstanceList.add(entry.getValue());
			}
		}
		return gson.toJson(initInstanceList);
	}
	
	public String getProtocolDisplayInstanceExportData(String instanceList){
		StringBuffer result_json = new StringBuffer();
		result_json.append("[");
		String displayInstanceSql="select t.id,t.name,t.code,t.displayunitid,t2.unit_name,t.sort,t3.unit_name as acqUnitName, t3.protocol  "
				+ " from TBL_PROTOCOLDISPLAYINSTANCE t ,tbl_display_unit_conf t2,tbl_acq_unit_conf t3"
				+ " where t.displayunitid=t2.id and t2.acqunitid=t3.id "
				+ " and t.id in("+instanceList+")"
				+ " order by t.displayunitid,t.id";
		List<?> displayInstanceQueryList = this.findCallSql(displayInstanceSql);
		for(int i=0;i<displayInstanceQueryList.size();i++){
			Object[] displayInstanceObj=(Object[])displayInstanceQueryList.get(i);
			result_json.append("{");
			result_json.append("\"Id\":"+StringManagerUtils.stringToInteger(displayInstanceObj[0]+"")+",");
			result_json.append("\"Name\":\""+displayInstanceObj[1]+"\",");
			result_json.append("\"Code\":\""+displayInstanceObj[2]+"\",");
			result_json.append("\"DisplayUnitId\":"+StringManagerUtils.stringToInteger(displayInstanceObj[3]+"")+",");
			result_json.append("\"DisplayUnitName\":\""+displayInstanceObj[4]+"\",");
			result_json.append("\"Sort\":"+StringManagerUtils.stringToInteger(displayInstanceObj[5]+"")+",");
			result_json.append("\"AcqUnitName\":\""+displayInstanceObj[6]+"\",");
			result_json.append("\"Protocol\":\""+displayInstanceObj[7]+"\"");
			result_json.append("},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString();
	}
	
	public String getProtocolAlarmInstanceExportData(String instanceList){
		StringBuffer result_json = new StringBuffer();
		result_json.append("[");
		String displayInstanceSql="select t.id,t.name,t.code,t.alarmunitid,t2.unit_name,t.sort ,t2.protocol"
				+ " from TBL_PROTOCOLALARMINSTANCE t ,tbl_alarm_unit_conf t2"
				+ " where t.alarmunitid=t2.id and t.id in("+instanceList+")"
				+ " order by t.alarmunitid,t.id";
		List<?> displayInstanceQueryList = this.findCallSql(displayInstanceSql);
		for(int i=0;i<displayInstanceQueryList.size();i++){
			Object[] displayInstanceObj=(Object[])displayInstanceQueryList.get(i);
			result_json.append("{");
			result_json.append("\"Id\":"+StringManagerUtils.stringToInteger(displayInstanceObj[0]+"")+",");
			result_json.append("\"Name\":\""+displayInstanceObj[1]+"\",");
			result_json.append("\"Code\":\""+displayInstanceObj[2]+"\",");
			result_json.append("\"UnitId\":"+StringManagerUtils.stringToInteger(displayInstanceObj[3]+"")+",");
			result_json.append("\"UnitName\":\""+displayInstanceObj[4]+"\",");
			result_json.append("\"Sort\":"+StringManagerUtils.stringToInteger(displayInstanceObj[5]+"")+",");
			result_json.append("\"Protocol\":\""+displayInstanceObj[6]+"\"");
			result_json.append("},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString();
	}
	
	public String getProtocolReportInstanceExportData(String instanceList){
		StringBuffer result_json = new StringBuffer();
		result_json.append("[");
		String displayInstanceSql="select t.id,t.name,t.code,t.unitid,t2.unit_name,t.sort "
				+ " from TBL_PROTOCOLREPORTINSTANCE t,tbl_report_unit_conf t2 "
				+ " where t.unitid=t2.id and t.id in("+instanceList+")"
				+ " order by t.unitid,t.id";
		List<?> displayInstanceQueryList = this.findCallSql(displayInstanceSql);
		for(int i=0;i<displayInstanceQueryList.size();i++){
			Object[] displayInstanceObj=(Object[])displayInstanceQueryList.get(i);
			result_json.append("{");
			result_json.append("\"Id\":"+StringManagerUtils.stringToInteger(displayInstanceObj[0]+"")+",");
			result_json.append("\"Name\":\""+displayInstanceObj[1]+"\",");
			result_json.append("\"Code\":\""+displayInstanceObj[2]+"\",");
			result_json.append("\"UnitId\":"+StringManagerUtils.stringToInteger(displayInstanceObj[3]+"")+",");
			result_json.append("\"UnitName\":\""+displayInstanceObj[4]+"\",");
			result_json.append("\"Sort\":"+StringManagerUtils.stringToInteger(displayInstanceObj[5]+"")+"");
			result_json.append("},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString();
	}
	
	public String getUploadedProtocolTreeData(List<ModbusProtocolConfig.Protocol> protocolList,String deviceType,User user){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		String allDeviceIds=tabInfoManagerService.queryTabs(user);
		
		String overlayProtoolSql="select t.name, substr(v.path||'/'||t.name,2) as allpath  from tbl_protocol t, "
				+ " (select t2.id, sys_connect_by_path(t2.name_"+user.getLanguageName()+",'/') as path"
				+ " from tbl_devicetypeinfo t2"
				+ " start with t2.parentid=0"
				+ " connect by   t2.parentid= prior t2.id) v"
				+ " where t.devicetype=v.id"
				+ " and t.devicetype in ("+allDeviceIds+")";
//				+ " and t.devicetype in (select id from tbl_devicetypeinfo start with id="+deviceType+" connect by prior  id=parentid)";
		
		String collisionProtoolSql="select t.name, substr(v.path||'/'||t.name,2) as allpath  from tbl_protocol t, "
				+ " (select t2.id, sys_connect_by_path(t2.name_"+user.getLanguageName()+",'/') as path"
				+ " from tbl_devicetypeinfo t2"
				+ " start with t2.parentid=0"
				+ " connect by   t2.parentid= prior t2.id) v"
				+ " where t.devicetype=v.id"
				+ " and t.devicetype not in ("+allDeviceIds+")";
//				+ " and t.devicetype not in (select id from tbl_devicetypeinfo start with id="+deviceType+" connect by prior  id=parentid)";
		
		List<?> overlayProtoolList = this.findCallSql(overlayProtoolSql);
		List<?> collisionProtoolList = this.findCallSql(collisionProtoolSql);
		int overlayCount=0;
		int collisionCount=0;
		tree_json.append("[");
		if(protocolList!=null && protocolList.size()>0){
			for(int i=0;i<protocolList.size();i++){
				String msg="";
				int saveSign=0;//无冲突覆盖
				if(overlayProtoolList.size()>0){
					for(int j=0;j<overlayProtoolList.size();j++){
						Object[] obj=(Object[])overlayProtoolList.get(j);
						if((obj[0]+"").equalsIgnoreCase(protocolList.get(i).getName())){
							saveSign=1;//覆盖
							overlayCount++;
							msg=obj[1]+languageResourceMap.get("uploadCollisionInfo1");
							break;
						}
					}
				}
				
				if(collisionProtoolList.size()>0){
					for(int j=0;j<collisionProtoolList.size();j++){
						Object[] obj=(Object[])collisionProtoolList.get(i);
						if((obj[0]+"").equalsIgnoreCase(protocolList.get(i).getName())){
							saveSign=2;//冲突
							collisionCount++;
							msg=obj[1]+languageResourceMap.get("uploadCollisionInfo2");
							break;
						}
					}
				}
				tree_json.append("{\"classes\":1,");
				tree_json.append("\"text\":\""+protocolList.get(i).getName()+"\",");
				tree_json.append("\"code\":\""+protocolList.get(i).getCode()+"\",");
				tree_json.append("\"sort\":\""+protocolList.get(i).getSort()+"\",");
				tree_json.append("\"msg\":\""+msg+"\",");
				tree_json.append("\"saveSign\":\""+saveSign+"\",");
				tree_json.append("\"iconCls\": \"protocol\",");
				tree_json.append("\"action\": \"\",");
				tree_json.append("\"leaf\": true");
				tree_json.append("},");
			}
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("+protocolList+")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		
		return result_json.toString();
	}
	
	public String getUploadedProtocolItemsConfigData(String protocolName,String classes,String code,List<ModbusProtocolConfig.Protocol> protocolList,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("quantity")+"\",\"dataIndex\":\"quantity\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("storeDataType")+"\",\"dataIndex\":\"storeDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("IFDataType")+"\",\"dataIndex\":\"IFDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("prec")+"\",\"dataIndex\":\"prec\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("ratio")+"\",\"dataIndex\":\"ratio\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("RWType")+"\",\"dataIndex\":\"RWType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("resolutionMode")+"\",\"dataIndex\":\"resolutionMode\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("acqMode")+"\",\"dataIndex\":\"acqMode\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		ModbusProtocolConfig.Protocol protocolConfig=null;
		if(protocolList!=null){
			for(ModbusProtocolConfig.Protocol protocol:protocolList){
				if(protocolName.equalsIgnoreCase(protocol.getName())){
					protocolConfig=protocol;
				}
			}
		}
		if(protocolConfig!=null){
			Collections.sort(protocolConfig.getItems());
			for(int j=0;j<protocolConfig.getItems().size();j++){
				String resolutionMode=languageResourceMap.get("numericValue");
				if(protocolConfig.getItems().get(j).getResolutionMode()==0){
					resolutionMode=languageResourceMap.get("switchingValue");
				}else if(protocolConfig.getItems().get(j).getResolutionMode()==1){
					resolutionMode=languageResourceMap.get("enumValue");
				}
				String RWType=languageResourceMap.get("readOnly");
				if("r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
					RWType=languageResourceMap.get("readOnly");
				}else if("w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
					RWType=languageResourceMap.get("writeOnly");
				}else if("rw".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
					RWType=languageResourceMap.get("readWrite");
				}
				result_json.append("{"
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
						+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?languageResourceMap.get("activeAcqModel"):languageResourceMap.get("passiveAcqModel"))+"\""
						+ "},");
				
			}
		}
	
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public int updateProtocol(ModbusProtocolConfig.Protocol protocol,User user){
		int r=0;
		if(protocol!=null){
			Gson gson = new Gson();
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
			String updateSql="update TBL_PROTOCOL t set t.sort="+(protocol.getSort()<=0?"null":(protocol.getSort()+""))+",t.deviceType="+protocol.getDeviceType()+",t.items=?"
					+" where t.name='"+protocol.getName()+"'";
			List<String> clobCont=new ArrayList<String>();
			clobCont.add(gson.toJson(protocol.getItems()));
			r=service.getBaseDao().executeSqlUpdateClob(updateSql,clobCont);
			if(r==0){
				String insertSql="insert into TBL_PROTOCOL (name,sort,deviceType,items) values ('"+protocol.getName()+"',"+(protocol.getSort()<=0?"null":(protocol.getSort()+""))+","+protocol.getDeviceType()+",?)";
				clobCont=new ArrayList<String>();
				clobCont.add(gson.toJson(protocol.getItems()));
				r=service.getBaseDao().executeSqlUpdateClob(insertSql,clobCont);
			}
			
			if(r>0){
				MemoryDataManagerTask.loadProtocolConfig(protocol.getName());
				if(user!=null){
					try {
						this.service.saveSystemLog(user,2,languageResourceMap.get("importProtocol")+":"+protocol.getName());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return r;
	}
	
	
	public String getUploadedAcqUnitTreeData(List<ExportAcqUnitData> uploadAcqUnitList,String deviceType,User user){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		String allDeviceIds=tabInfoManagerService.queryTabs(user);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		
		List<String> protoolList=new ArrayList<>();
		String protocolSql="select t.name from tbl_protocol t where t.deviceType in("+allDeviceIds+")";
		List<?> protoolQueryList = this.findCallSql(protocolSql);
		for(int i=0;i<protoolQueryList.size();i++){
			protoolList.add(protoolQueryList.get(i)+"");
		}
		
		String unitSql="select t.unit_name,t.protocol from tbl_acq_unit_conf t, tbl_protocol t2 where t.protocol=t2.name and t2.deviceType in("+allDeviceIds+")";
		List<?> unitQueryList = this.findCallSql(unitSql);
		
		
		tree_json.append("[");
		if(uploadAcqUnitList!=null && uploadAcqUnitList.size()>0){
			for(int i=0;i<uploadAcqUnitList.size();i++){
				String msg="";
				int saveSign=0;//无冲突覆盖
				
				if(StringManagerUtils.existOrNot(protoolList, uploadAcqUnitList.get(i).getProtocol(), false)){
					if(unitQueryList.size()>0){
						for(int j=0;j<unitQueryList.size();j++){
							Object[] obj=(Object[])unitQueryList.get(j);
							if((obj[0]+"").equalsIgnoreCase(uploadAcqUnitList.get(i).getUnitName()) && (obj[1]+"").equalsIgnoreCase(uploadAcqUnitList.get(i).getProtocol())){
								saveSign=1;//覆盖
								msg=obj[0]+languageResourceMap.get("uploadCollisionInfo1");
								break;
							}
						}
					}
				}else{
					saveSign=2;
					msg=languageResourceMap.get("protocolDoesNotExist");
				}
				
				tree_json.append("{\"classes\":1,");
				tree_json.append("\"text\":\""+uploadAcqUnitList.get(i).getUnitName()+"\",");
				tree_json.append("\"code\":\""+uploadAcqUnitList.get(i).getUnitCode()+"\",");
				tree_json.append("\"protocol\":\""+uploadAcqUnitList.get(i).getProtocol()+"\",");
				tree_json.append("\"msg\":\""+msg+"\",");
				tree_json.append("\"saveSign\":\""+saveSign+"\",");
				tree_json.append("\"iconCls\": \"acqUnit\",");
				tree_json.append("\"action\": \"\",");
				if(uploadAcqUnitList.get(i).getGroupList()!=null && uploadAcqUnitList.get(i).getGroupList().size()>0){
					tree_json.append("\"expanded\": true,");
					tree_json.append("\"children\": [");
					for(int k=0;k<uploadAcqUnitList.get(i).getGroupList().size();k++){
						tree_json.append("{\"classes\":2,");
						tree_json.append("\"id\":"+uploadAcqUnitList.get(i).getGroupList().get(k).getId()+",");
						tree_json.append("\"code\":\""+uploadAcqUnitList.get(i).getGroupList().get(k).getGroupCode()+"\",");
						tree_json.append("\"text\":\""+uploadAcqUnitList.get(i).getGroupList().get(k).getGroupName()+"\",");
						tree_json.append("\"groupTimingInterval\":\""+uploadAcqUnitList.get(i).getGroupList().get(k).getGroupTimingInterval()+"\",");
						tree_json.append("\"groupSavingInterval\":\""+uploadAcqUnitList.get(i).getGroupList().get(k).getGroupSavingInterval()+"\",");
						tree_json.append("\"remark\":\""+uploadAcqUnitList.get(i).getGroupList().get(k).getRemark()+"\",");
						tree_json.append("\"protocol\":\""+uploadAcqUnitList.get(i).getGroupList().get(k).getProtocol()+"\",");
						tree_json.append("\"type\":"+uploadAcqUnitList.get(i).getGroupList().get(k).getType()+",");
						tree_json.append("\"typeName\":\""+(uploadAcqUnitList.get(i).getGroupList().get(k).getType()==0?languageResourceMap.get("acqGroup"):languageResourceMap.get("controlGroup"))+"\",");
						tree_json.append("\"iconCls\": \"acqGroup\",");
						tree_json.append("\"leaf\": true");
						tree_json.append("},");
					}
					if(tree_json.toString().endsWith(",")){
						tree_json.deleteCharAt(tree_json.length() - 1);
					}
					
					tree_json.append("]");
				}else{
					tree_json.append("\"leaf\": true");
				}
				
				
				
				
				tree_json.append("},");
			}
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("unitList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		
		return result_json.toString();
	}
	
	
	public String getUploadedAcqUnitItemsConfigData(String protocolName,String classes,String unitName,String groupName,String groupType,List<ExportAcqUnitData> uploadAcqUnitList,String language){
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		int index=1;
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("address")+"\",\"dataIndex\":\"addr\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("quantity")+"\",\"dataIndex\":\"quantity\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("storeDataType")+"\",\"dataIndex\":\"storeDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("IFDataType")+"\",\"dataIndex\":\"IFDataType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("prec")+"\",\"dataIndex\":\"prec\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("ratio")+"\",\"dataIndex\":\"ratio\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("RWType")+"\",\"dataIndex\":\"RWType\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("acqMode")+"\",\"dataIndex\":\"acqMode\",width:80 ,children:[] }"
				+ "]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		

		ModbusProtocolConfig.Protocol protocolConfig=MemoryDataManagerTask.getProtocolByName(protocolName);
		if(protocolConfig!=null && uploadAcqUnitList!=null){
			Collections.sort(protocolConfig.getItems());
			
			List<String> itemsList=new ArrayList<String>();
			Map<String,ExportAcqUnitData.AcqItem> itemsMap=new HashMap<>();
			
			for(int i=0;i<uploadAcqUnitList.size();i++){
				if(unitName.equalsIgnoreCase(uploadAcqUnitList.get(i).getUnitName()) && protocolName.equalsIgnoreCase(uploadAcqUnitList.get(i).getProtocol()) ){
					if("1".equalsIgnoreCase(classes)){
						if(uploadAcqUnitList.get(i).getGroupList()!=null && uploadAcqUnitList.get(i).getGroupList().size()>0){
							for(ExportAcqUnitData.AcqGroup group:uploadAcqUnitList.get(i).getGroupList()){
								for(ExportAcqUnitData.AcqItem acqItem:group.getItemList()){
									if(!StringManagerUtils.existOrNot(itemsList, acqItem.getItemName(), false)){
										itemsList.add(acqItem.getItemName());
										itemsMap.put(acqItem.getItemName(), acqItem);
									}
								}
							}
						}
					}else if("2".equalsIgnoreCase(classes)){
						if(uploadAcqUnitList.get(i).getGroupList()!=null && uploadAcqUnitList.get(i).getGroupList().size()>0){
							for(ExportAcqUnitData.AcqGroup group:uploadAcqUnitList.get(i).getGroupList()){
								if(groupName.equalsIgnoreCase(group.getGroupName()) && StringManagerUtils.stringToInteger(groupType)== group.getType()){
									for(ExportAcqUnitData.AcqItem acqItem:group.getItemList()){
										if(!StringManagerUtils.existOrNot(itemsList, acqItem.getItemName(), false)){
											itemsList.add(acqItem.getItemName());
											itemsMap.put(acqItem.getItemName(), acqItem);
										}
									}
									break;
								}
							}
						}
					}
					break;
				}
			}
			
			for(int j=0;j<protocolConfig.getItems().size();j++){
				if(StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(),false)){
					String RWTypeName=languageResourceMap.get("readOnly");
					if("r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWTypeName=languageResourceMap.get("readOnly");
					}else if("w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWTypeName=languageResourceMap.get("writeOnly");
					}else if("rw".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
						RWTypeName=languageResourceMap.get("readWrite");
					}
					
					String resolutionMode=languageResourceMap.get("numericValue");
					if(protocolConfig.getItems().get(j).getResolutionMode()==0){
						resolutionMode=languageResourceMap.get("switchingValue");
					}else if(protocolConfig.getItems().get(j).getResolutionMode()==1){
						resolutionMode=languageResourceMap.get("enumValue");
					}
					
					ExportAcqUnitData.AcqItem acqItem=itemsMap.get(protocolConfig.getItems().get(j).getTitle());
					boolean dailyTotalCalculate=false;
					String dailyTotalCalculateName="";
					if(acqItem!=null){
						dailyTotalCalculate=acqItem.getDailyTotalCalculate()==1;
						if(dailyTotalCalculate){
							dailyTotalCalculateName=acqItem.getDailyTotalCalculateName();
						}
					}
					
					result_json.append("{"
							+ "\"id\":"+(index)+","
							+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
							+ "\"bitIndex\":\"\","
							+ "\"addr\":"+protocolConfig.getItems().get(j).getAddr()+","
							+ "\"quantity\":"+1+","
							+ "\"storeDataType\":\""+protocolConfig.getItems().get(j).getStoreDataType()+"\","
							+ "\"IFDataType\":\""+protocolConfig.getItems().get(j).getIFDataType()+"\","
							+ "\"ratio\":"+protocolConfig.getItems().get(j).getRatio()+","
							+ "\"RWType\":\""+RWTypeName+"\","
							+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
							+ "\"resolutionMode\":\""+resolutionMode+"\","
							+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?languageResourceMap.get("activeAcqModel"):languageResourceMap.get("passiveAcqModel"))+"\","
							+ "\"dailyTotalCalculate\":"+dailyTotalCalculate+","
							+ "\"dailyTotalCalculateName\":\""+dailyTotalCalculateName+"\""
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
		return result_json.toString();
	}
	
	public void importAcqUnit(ExportAcqUnitData exportAcqUnitData,User user){
		String unitsql="select t.id,t.unit_code from tbl_acq_unit_conf t,tbl_protocol t2 "
				+ " where t.protocol=t2.name "
				+ " and t.unit_name='"+exportAcqUnitData.getUnitName()+"' and t2.name='"+exportAcqUnitData.getProtocol()+"'"
				+ " order by t.id desc";
		List<?> unitList = this.findCallSql(unitsql);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		String unitId="";
		String unitCode="";
		int r=0;
		if(unitList.size()>0){
			Object[] obj=(Object[]) unitList.get(0);
			unitId=obj[0]+"";
			unitCode=obj[1]+"";
			String delItemSql="delete from tbl_acq_item2group_conf t4 where t4.id in( select t.id from tbl_acq_item2group_conf t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3 where t.groupid=t2.id and t2.id=t3.groupid and t3.unitid="+unitId+")";
			r=this.getBaseDao().updateOrDeleteBySql(delItemSql);
			String delGroupSql="delete from tbl_acq_group_conf t3 where t3.id in( select t.id from tbl_acq_group_conf t,tbl_acq_group2unit_conf t2 where t.id=t2.groupid and t2.unitid="+unitId+")";
			r=this.getBaseDao().updateOrDeleteBySql(delGroupSql);
			String delGroupUnitsql="delete from tbl_acq_group2unit_conf t where t.unitid="+unitId+"";
			r=this.getBaseDao().updateOrDeleteBySql(delGroupSql);

			String updateUnitSql="update tbl_acq_unit_conf t set t.remark='"+exportAcqUnitData.getRemark()+"' "
					+ " where t.id="+unitId;
			r=this.getBaseDao().updateOrDeleteBySql(updateUnitSql);
		}else{
			try {
				String insertUnitSql="insert into tbl_acq_unit_conf (unit_code,unit_name,protocol,remark) values ('"+exportAcqUnitData.getUnitName()+"','"+exportAcqUnitData.getUnitName()+"','"+exportAcqUnitData.getProtocol()+"','"+exportAcqUnitData.getRemark()+"')";
				r=this.getBaseDao().updateOrDeleteBySql(insertUnitSql);
				unitsql="select t.id,t.unit_code from tbl_acq_unit_conf t,tbl_protocol t2 "
						+ " where t.protocol=t2.name "
						+ " and t.unit_name='"+exportAcqUnitData.getUnitName()+"' and t2.name='"+exportAcqUnitData.getProtocol()+"'"
						+ " order by t.id desc";
				unitList = this.findCallSql(unitsql);
				unitId="";
				unitCode="";
				if(unitList.size()>0){
					Object[] obj=(Object[]) unitList.get(0);
					unitId=obj[0]+"";
					unitCode=obj[1]+"";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(StringManagerUtils.isNotNull(unitId) && exportAcqUnitData.getGroupList()!=null && exportAcqUnitData.getGroupList().size()>0){
			for(int i=0;i<exportAcqUnitData.getGroupList().size();i++){
				
				try {
					String insertGroupSql="insert into TBL_ACQ_GROUP_CONF (group_code,group_name,grouptiminginterval,groupsavinginterval,protocol,type,remark )"
							+ " values('"+exportAcqUnitData.getGroupList().get(i).getGroupName()+"','"+exportAcqUnitData.getGroupList().get(i).getGroupName()+"',"
							+ ""+exportAcqUnitData.getGroupList().get(i).getGroupTimingInterval()+","+exportAcqUnitData.getGroupList().get(i).getGroupSavingInterval()+","
							+ "'"+exportAcqUnitData.getGroupList().get(i).getProtocol()+"',"+exportAcqUnitData.getGroupList().get(i).getType()+",'"+exportAcqUnitData.getGroupList().get(i).getRemark()+"')";
					r=this.getBaseDao().updateOrDeleteBySql(insertGroupSql);
					
					String addGroupSql="select t.id from TBL_ACQ_GROUP_CONF t "
							+ " where t.group_name='"+exportAcqUnitData.getGroupList().get(i).getGroupName()
							+"' and t.protocol='"+exportAcqUnitData.getGroupList().get(i).getProtocol()+"'"
							+ " order by t.id desc";
					String groupId="";
					List list = this.service.findCallSql(addGroupSql);
					if(list.size()>0){
						groupId=list.get(0).toString();
						AcquisitionUnitGroup acquisitionUnitGroup = new AcquisitionUnitGroup();
						acquisitionUnitGroup.setUnitId(StringManagerUtils.stringToInteger(unitId));
						acquisitionUnitGroup.setGroupId(StringManagerUtils.stringToInteger(groupId));
						acquisitionUnitGroup.setMatrix("0,0,0");
						try {
							this.grantAcquisitionGroupsPermission(acquisitionUnitGroup);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if(exportAcqUnitData.getGroupList().get(i).getItemList()!=null){
						for(int j=0;j<exportAcqUnitData.getGroupList().get(i).getItemList().size();j++){
							AcquisitionGroupItem acquisitionGroupItem = new AcquisitionGroupItem();
							acquisitionGroupItem.setGroupId(Integer.parseInt(groupId));
							
							acquisitionGroupItem.setItemName(exportAcqUnitData.getGroupList().get(i).getItemList().get(j).getItemName());
							acquisitionGroupItem.setBitIndex(exportAcqUnitData.getGroupList().get(i).getItemList().get(j).getBitIndex());
							
							acquisitionGroupItem.setMatrix(exportAcqUnitData.getGroupList().get(i).getItemList().get(j).getMatrix());
							acquisitionGroupItem.setDailyTotalCalculateName(exportAcqUnitData.getGroupList().get(i).getItemList().get(j).getDailyTotalCalculateName());
							acquisitionGroupItem.setDailyTotalCalculate(exportAcqUnitData.getGroupList().get(i).getItemList().get(j).getDailyTotalCalculate());
							try {
								this.grantAcquisitionItemsPermission(acquisitionGroupItem);
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		
		if(StringManagerUtils.isNotNull(unitId)){
			EquipmentDriverServerTask.initInstanceConfigByAcqUnitId(unitId, "update");
			EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByAcqUnitId(unitId, "update");
			MemoryDataManagerTask.loadAcqInstanceOwnItemByUnitId(unitId, "update");
			MemoryDataManagerTask.loadDisplayInstanceOwnItemByAcqUnitId(unitId, "update");
			
			if(user!=null){
				try {
					this.service.saveSystemLog(user,2,languageResourceMap.get("importAcqUnit")+":"+exportAcqUnitData.getUnitName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getUploadedAlarmUnitTreeData(List<ExportAlarmUnitData> uploadUnitList,String deviceType,User user){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		String allDeviceIds=tabInfoManagerService.queryTabs(user);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		
		List<String> protoolList=new ArrayList<>();
		String protocolSql="select t.name from tbl_protocol t where t.deviceType in("+allDeviceIds+")";
		List<?> protoolQueryList = this.findCallSql(protocolSql);
		for(int i=0;i<protoolQueryList.size();i++){
			protoolList.add(protoolQueryList.get(i)+"");
		}
		
		String unitSql="select t.unit_name,t.protocol from TBL_ALARM_UNIT_CONF t, tbl_protocol t2 where t.protocol=t2.name and t2.deviceType in("+allDeviceIds+")";
		List<?> unitQueryList = this.findCallSql(unitSql);
		
		
		tree_json.append("[");
		if(uploadUnitList!=null && uploadUnitList.size()>0){
			for(int i=0;i<uploadUnitList.size();i++){
				String msg="";
				int saveSign=0;//无冲突覆盖
				
				if(StringManagerUtils.existOrNot(protoolList, uploadUnitList.get(i).getProtocol(), false)){
					if(unitQueryList.size()>0){
						for(int j=0;j<unitQueryList.size();j++){
							Object[] obj=(Object[])unitQueryList.get(j);
							if((obj[0]+"").equalsIgnoreCase(uploadUnitList.get(i).getUnitName()) && (obj[1]+"").equalsIgnoreCase(uploadUnitList.get(i).getProtocol())){
								saveSign=1;//覆盖
								msg=obj[0]+languageResourceMap.get("uploadCollisionInfo1");
								break;
							}
						}
					}
				}else{
					saveSign=2;
					msg=languageResourceMap.get("protocolDoesNotExist");
				}
				
				tree_json.append("{\"classes\":1,");
				tree_json.append("\"text\":\""+uploadUnitList.get(i).getUnitName()+"\",");
				tree_json.append("\"code\":\""+uploadUnitList.get(i).getUnitCode()+"\",");
				tree_json.append("\"protocol\":\""+uploadUnitList.get(i).getProtocol()+"\",");
				tree_json.append("\"msg\":\""+msg+"\",");
				tree_json.append("\"saveSign\":\""+saveSign+"\",");
				tree_json.append("\"iconCls\": \"acqUnit\",");
				tree_json.append("\"action\": \"\",");
				tree_json.append("\"leaf\": true");
				tree_json.append("},");
			}
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("unitList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		
		return result_json.toString();
	}
	
	public String getImportAlarmUnitItemsData(List<ExportAlarmUnitData> uploadUnitList,String protocolName,String unitName,String alarmType,User user){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		result_json.append("{ \"success\":true,\"columns\":[],");
		result_json.append("\"totalRoot\":[");
		
		Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
		
		List<ExportAlarmUnitData.AlarmItem> alarmItemList=new ArrayList<>();
		
		if(uploadUnitList!=null && uploadUnitList.size()>0){
			for(int i=0;i<uploadUnitList.size();i++){
				if(protocolName.equalsIgnoreCase(uploadUnitList.get(i).getProtocol()) && unitName.equalsIgnoreCase(uploadUnitList.get(i).getUnitName())){
					if(uploadUnitList.get(i).getItemList()!=null && uploadUnitList.get(i).getItemList().size()>0){
						for(int j=0;j<uploadUnitList.get(i).getItemList().size();j++){
							if(StringManagerUtils.stringToInteger(alarmType)==uploadUnitList.get(i).getItemList().get(j).getType()){
								alarmItemList.add(uploadUnitList.get(i).getItemList().get(j));
							}
						}
					}
					break;
				}
			}
		}
		for(int i=0;i<alarmItemList.size();i++){
			String meaning="";
			String alarmLevel="";
			String value=alarmItemList.get(i).getValue();
			if(alarmItemList.get(i).getAlarmLevel()==100){
				alarmLevel=languageResourceMap.get("alarmLevel1");
			}else if(alarmItemList.get(i).getAlarmLevel()==200){
				alarmLevel=languageResourceMap.get("alarmLevel2");
			}else if(alarmItemList.get(i).getAlarmLevel()==300){
				alarmLevel=languageResourceMap.get("alarmLevel3");
			}else if(alarmItemList.get(i).getAlarmLevel()==400){
				alarmLevel=languageResourceMap.get("alarmLevel4");
			}
			
			if(alarmItemList.get(i).getType()==0 && protocol!=null && protocol.getItems()!=null){
				for(int j=0;protocol!=null&&j<protocol.getItems().size();j++){
					if(StringManagerUtils.stringToInteger(alarmItemList.get(i).getItemAddr())==protocol.getItems().get(j).getAddr()
							&&protocol.getItems().get(j).getResolutionMode()==0
							&&protocol.getItems().get(j).getMeaning()!=null
							&&protocol.getItems().get(j).getMeaning().size()>0){
						for(int k=0;k<protocol.getItems().get(j).getMeaning().size();k++){
							if(StringManagerUtils.stringToInteger(alarmItemList.get(i).getBitIndex())==protocol.getItems().get(j).getMeaning().get(k).getValue()){
								meaning=protocol.getItems().get(j).getMeaning().get(k).getMeaning();
								break;
							}
						}
						break;
					}
				}
			}else if(alarmItemList.get(i).getType()==1 && protocol!=null && protocol.getItems()!=null){
				for(int j=0;protocol!=null&&j<protocol.getItems().size();j++){
					if(StringManagerUtils.stringToInteger(alarmItemList.get(i).getItemAddr())==protocol.getItems().get(j).getAddr()
							&&protocol.getItems().get(j).getResolutionMode()==1
							&&protocol.getItems().get(j).getMeaning()!=null
							&&protocol.getItems().get(j).getMeaning().size()>0){
						for(int k=0;k<protocol.getItems().get(j).getMeaning().size();k++){
							if(StringManagerUtils.stringToInteger(alarmItemList.get(i).getValue())==protocol.getItems().get(j).getMeaning().get(k).getValue()){
								meaning=protocol.getItems().get(j).getMeaning().get(k).getMeaning();
								break;
							}
						}
						break;
					}
				}
			}
			
			if(alarmItemList.get(i).getType()==0){
				value="1".equalsIgnoreCase(value)?languageResourceMap.get("switchingOpenValue"):languageResourceMap.get("switchingCloseValue");
			}

			result_json.append("{\"id\":"+(i+1)+","
					+ "\"title\":\""+alarmItemList.get(i).getItemName()+"\","
					+ "\"code\":\""+alarmItemList.get(i).getItemCode()+"\","
					+ "\"addr\":\""+alarmItemList.get(i).getItemAddr()+"\","
					+ "\"bitIndex\":\""+alarmItemList.get(i).getBitIndex()+"\","
					+ "\"value\":\""+value+"\","
					+ "\"upperLimit\":\""+alarmItemList.get(i).getUpperLimit()+"\","
					+ "\"lowerLimit\":\""+alarmItemList.get(i).getLowerLimit()+"\","
					+ "\"hystersis\":\""+alarmItemList.get(i).getHystersis()+"\","
					+ "\"delay\":\""+alarmItemList.get(i).getDelay()+"\","
					+ "\"alarmLevel\":\""+alarmLevel+"\","
					+ "\"alarmSign\":\""+(alarmItemList.get(i).getAlarmSign()==1?languageResourceMap.get("enable"):languageResourceMap.get("disable"))+"\","
					+ "\"meaning\":\""+meaning+"\","
					+ "\"isSendMessage\":\""+(alarmItemList.get(i).getSendMessage()==1?languageResourceMap.get("yes"):languageResourceMap.get("no"))+"\","
					+ "\"isSendMail\":\""+(alarmItemList.get(i).getSendMail()==1?languageResourceMap.get("yes"):languageResourceMap.get("no"))+"\"},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		
		return result_json.toString().replaceAll("null", "");
	}
	
	public void importAlarmUnit(ExportAlarmUnitData exportAlarmUnitData,User user){
		String unitsql="select t.id,t.unit_code from tbl_alarm_unit_conf t,tbl_protocol t2 "
				+ " where t.protocol=t2.name "
				+ " and t.unit_name='"+exportAlarmUnitData.getUnitName()+"' and t2.name='"+exportAlarmUnitData.getProtocol()+"'"
				+ " order by t.id desc";
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		List<?> unitList = this.findCallSql(unitsql);
		String unitId="";
		String unitCode="";
		int r=0;
		if(unitList.size()>0){
			Object[] obj=(Object[]) unitList.get(0);
			unitId=obj[0]+"";
			unitCode=obj[1]+"";
			String delItemSql="delete from tbl_alarm_item2unit_conf t where t.unitid="+unitId+"";
			r=this.getBaseDao().updateOrDeleteBySql(delItemSql);
			String updateUnitSql="update tbl_alarm_unit_conf t set t.remark='"+exportAlarmUnitData.getRemark()+"' "
					+ " where t.id="+unitId;
			r=this.getBaseDao().updateOrDeleteBySql(updateUnitSql);
		}else{
			try {
				String insertUnitSql="insert into tbl_alarm_unit_conf (unit_code,unit_name,protocol,remark) values ('"+exportAlarmUnitData.getUnitName()+"','"+exportAlarmUnitData.getUnitName()+"','"+exportAlarmUnitData.getProtocol()+"','"+exportAlarmUnitData.getRemark()+"')";
				r=this.getBaseDao().updateOrDeleteBySql(insertUnitSql);
				unitsql="select t.id,t.unit_code from tbl_alarm_unit_conf t,tbl_protocol t2 "
						+ " where t.protocol=t2.name "
						+ " and t.unit_name='"+exportAlarmUnitData.getUnitName()+"' and t2.name='"+exportAlarmUnitData.getProtocol()+"'"
						+ " order by t.id desc";
				unitList = this.findCallSql(unitsql);
				unitId="";
				unitCode="";
				if(unitList.size()>0){
					Object[] obj=(Object[]) unitList.get(0);
					unitId=obj[0]+"";
					unitCode=obj[1]+"";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(StringManagerUtils.isNotNull(unitId) && exportAlarmUnitData.getItemList()!=null && exportAlarmUnitData.getItemList().size()>0){
			for(int i=0;i<exportAlarmUnitData.getItemList().size();i++){
				try {
					AlarmUnitItem alarmUnitItem=new AlarmUnitItem();
					alarmUnitItem.setUnitId(exportAlarmUnitData.getItemList().get(i).getId());
					alarmUnitItem.setItemName(exportAlarmUnitData.getItemList().get(i).getItemName());
					alarmUnitItem.setItemCode(exportAlarmUnitData.getItemList().get(i).getItemCode());
					alarmUnitItem.setItemAddr(StringManagerUtils.isInteger(exportAlarmUnitData.getItemList().get(i).getItemAddr())?StringManagerUtils.stringToInteger(exportAlarmUnitData.getItemList().get(i).getItemAddr()):null  );
					alarmUnitItem.setType(exportAlarmUnitData.getItemList().get(i).getType());
					
					alarmUnitItem.setBitIndex(StringManagerUtils.isInteger(exportAlarmUnitData.getItemList().get(i).getBitIndex())?StringManagerUtils.stringToInteger(exportAlarmUnitData.getItemList().get(i).getBitIndex()):null  );
					alarmUnitItem.setValue(StringManagerUtils.isNum(exportAlarmUnitData.getItemList().get(i).getValue())?StringManagerUtils.stringToFloat(exportAlarmUnitData.getItemList().get(i).getValue()):null  );
					
					alarmUnitItem.setUpperLimit(StringManagerUtils.isNum(exportAlarmUnitData.getItemList().get(i).getUpperLimit())?StringManagerUtils.stringToFloat(exportAlarmUnitData.getItemList().get(i).getUpperLimit()):null  );
					alarmUnitItem.setLowerLimit(StringManagerUtils.isNum(exportAlarmUnitData.getItemList().get(i).getLowerLimit())?StringManagerUtils.stringToFloat(exportAlarmUnitData.getItemList().get(i).getLowerLimit()):null  );
					alarmUnitItem.setHystersis(StringManagerUtils.isNum(exportAlarmUnitData.getItemList().get(i).getHystersis())?StringManagerUtils.stringToFloat(exportAlarmUnitData.getItemList().get(i).getHystersis()):null  );
				
					alarmUnitItem.setDelay(StringManagerUtils.isInteger(exportAlarmUnitData.getItemList().get(i).getDelay())?StringManagerUtils.stringToInteger(exportAlarmUnitData.getItemList().get(i).getDelay()):null  );
					
					alarmUnitItem.setAlarmLevel(exportAlarmUnitData.getItemList().get(i).getAlarmLevel());
					alarmUnitItem.setAlarmSign(exportAlarmUnitData.getItemList().get(i).getAlarmSign());
					
					alarmUnitItem.setIsSendMessage(exportAlarmUnitData.getItemList().get(i).getSendMessage());
					alarmUnitItem.setIsSendMail(exportAlarmUnitData.getItemList().get(i).getSendMail());
					
					this.grantAlarmItemsPermission(alarmUnitItem);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		
		if(StringManagerUtils.isNotNull(unitId)){
			MemoryDataManagerTask.loadAlarmInstanceOwnItemByUnitId(unitId, "update");
			if(user!=null){
				try {
					this.service.saveSystemLog(user,2,languageResourceMap.get("importAlarmUnit")+":"+exportAlarmUnitData.getUnitName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getUploadedDisplayUnitTreeData(List<ExportDisplayUnitData> uploadUnitList,String deviceType,User user){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		String allDeviceIds=tabInfoManagerService.queryTabs(user);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		
		List<String> protoolList=new ArrayList<>();
		String protocolSql="select t.name from tbl_protocol t where t.deviceType in("+allDeviceIds+")";
		List<?> protoolQueryList = this.findCallSql(protocolSql);
		for(int i=0;i<protoolQueryList.size();i++){
			protoolList.add(protoolQueryList.get(i)+"");
		}
		
		String displayUnitSql="select t.unit_name,t2.unit_name as acqUnit,t3.name "
				+ " from tbl_display_unit_conf t, tbl_acq_unit_conf t2,tbl_protocol t3 "
				+ " where t.acqunitid=t2.id and t2.protocol=t3.name "
				+ " and t3.devicetype in("+allDeviceIds+")";
		List<?> displayUnitQueryList = this.findCallSql(displayUnitSql);
		
		tree_json.append("[");
		if(uploadUnitList!=null && uploadUnitList.size()>0){
			for(int i=0;i<uploadUnitList.size();i++){
				String msg="";
				int saveSign=0;//无冲突覆盖
				if(StringManagerUtils.existOrNot(protoolList, uploadUnitList.get(i).getProtocol(), false)){
					String acqUnitSql="select t.unit_name from TBL_ACQ_UNIT_CONF t where t.protocol='"+uploadUnitList.get(i).getProtocol()+"' and t.unit_name ='"+uploadUnitList.get(i).getAcqUnit()+"'";
					List<?> acqUnitQueryList = this.findCallSql(acqUnitSql);
					if(acqUnitQueryList.size()>0){
						if(displayUnitQueryList.size()>0){
							for(int j=0;j<displayUnitQueryList.size();j++){
								Object[] obj=(Object[])displayUnitQueryList.get(j);
								if((obj[0]+"").equalsIgnoreCase(uploadUnitList.get(i).getUnitName()) 
										&& (obj[1]+"").equalsIgnoreCase(uploadUnitList.get(i).getAcqUnit())
										&& (obj[2]+"").equalsIgnoreCase(uploadUnitList.get(i).getProtocol())
										){
									saveSign=1;//覆盖
									msg=obj[0]+languageResourceMap.get("uploadCollisionInfo1");
									break;
								}
							}
						}
					}else{
						saveSign=2;
						msg=languageResourceMap.get("protocolDoesNotExist");
					}
				}else{
					saveSign=2;
					msg=languageResourceMap.get("acqUnitDoesNotExist");
				}
				
				tree_json.append("{\"classes\":1,");
				tree_json.append("\"text\":\""+uploadUnitList.get(i).getUnitName()+"\",");
				tree_json.append("\"code\":\""+uploadUnitList.get(i).getUnitCode()+"\",");
				tree_json.append("\"acqUnit\":\""+uploadUnitList.get(i).getAcqUnit()+"\",");
				tree_json.append("\"protocol\":\""+uploadUnitList.get(i).getProtocol()+"\",");
				tree_json.append("\"calculateType\":\""+uploadUnitList.get(i).getCalculateType()+"\",");
				tree_json.append("\"msg\":\""+msg+"\",");
				tree_json.append("\"saveSign\":\""+saveSign+"\",");
				tree_json.append("\"iconCls\": \"acqUnit\",");
				tree_json.append("\"action\": \"\",");
				tree_json.append("\"leaf\": true");
				tree_json.append("},");
			}
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("unitList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		
		return result_json.toString();
	}
	
	public String getImportDisplayUnitItemsConfigData(List<ExportDisplayUnitData> uploadUnitList,String protocolName,String acqUnitName,String unitName,String calculateType,String type,String language){
		StringBuffer result_json = new StringBuffer();
		Gson gson=new Gson();
		result_json.append("{ \"success\":true,\"columns\":[],");
		result_json.append("\"totalRoot\":[");
		ModbusProtocolConfig.Protocol protocol =MemoryDataManagerTask.getProtocolByName(protocolName);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		List<ExportDisplayUnitData.DisplayItem> displayItemList=new ArrayList<>();
		
		List<CalItem> calItemList=new ArrayList<>();
		if("1".equalsIgnoreCase(calculateType)){
			calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
		}else if("2".equalsIgnoreCase(calculateType)){
			calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
		}
		
		List<CalItem> inputItemList=new ArrayList<>();
		if("1".equalsIgnoreCase(calculateType)){
			inputItemList=MemoryDataManagerTask.getSRPInputItem(language);
		}else if("2".equalsIgnoreCase(calculateType)){
			inputItemList=MemoryDataManagerTask.getPCPInputItem(language);
		}
		
		if(protocol!=null && uploadUnitList!=null && uploadUnitList.size()>0){
			for(ExportDisplayUnitData exportDisplayUnitData:uploadUnitList){
				if(protocolName.equalsIgnoreCase(exportDisplayUnitData.getProtocol())
						&& acqUnitName.equalsIgnoreCase(exportDisplayUnitData.getAcqUnit())
						&& unitName.equalsIgnoreCase(exportDisplayUnitData.getUnitName())){
					if(exportDisplayUnitData.getItemList()!=null){
						for(ExportDisplayUnitData.DisplayItem displayItem:exportDisplayUnitData.getItemList()){
							if(StringManagerUtils.stringToInteger(type)==displayItem.getType()){
								displayItemList.add(displayItem);
							}
						}
					}
					break;
				}
			}
		}
		for(int i=0;i<displayItemList.size();i++){
			String unit="";
			String dataSource="";
			String realtimeCurveConfShowValue="";
			String historyCurveConfShowValue="";
			if(displayItemList.get(i).getType()==0 || displayItemList.get(i).getType()==2){
				ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItem(protocol, displayItemList.get(i).getItemName());
				if(item!=null){
					unit=item.getUnit();
				}
			}else if(displayItemList.get(i).getType()==1){
				CalItem item=MemoryDataManagerTask.getSingleCalItem(displayItemList.get(i).getItemName(), calItemList);
				if(item!=null){
					unit=item.getUnit();
					dataSource=item.getDataSource();
				}
			}else if(displayItemList.get(i).getType()==3){
				CalItem item=MemoryDataManagerTask.getSingleCalItem(displayItemList.get(i).getItemName(), inputItemList);
				if(item!=null){
					unit=item.getUnit();
				}
			}
			
			if(displayItemList.get(i).getRealtimeCurveConf()!=null && displayItemList.get(i).getRealtimeCurveConf().getSort()>0 && StringManagerUtils.isNotNull(displayItemList.get(i).getRealtimeCurveConf().getColor())){
				realtimeCurveConfShowValue=displayItemList.get(i).getRealtimeCurveConf().getSort()+";"+(displayItemList.get(i).getRealtimeCurveConf().getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+displayItemList.get(i).getRealtimeCurveConf().getColor();
			}
			if(displayItemList.get(i).getHistoryCurveConf()!=null && displayItemList.get(i).getHistoryCurveConf().getSort()>0 && StringManagerUtils.isNotNull(displayItemList.get(i).getHistoryCurveConf().getColor())){
				historyCurveConfShowValue=displayItemList.get(i).getHistoryCurveConf().getSort()+";"+(displayItemList.get(i).getHistoryCurveConf().getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+displayItemList.get(i).getHistoryCurveConf().getColor();
			}
			
			result_json.append("{"
					+ "\"id\":"+(i+1)+","
					+ "\"title\":\""+displayItemList.get(i).getItemName()+"\","
					+ "\"unit\":\""+unit+"\","
					+ "\"dataSource\":\""+dataSource+"\","
					+ "\"showLevel\":\""+(displayItemList.get(i).getShowLevel()>0?displayItemList.get(i).getShowLevel():"")+"\","
					+ "\"realtimeSort\":\""+(displayItemList.get(i).getShowLevel()>0?displayItemList.get(i).getRealtimeSort():"")+"\","
					+ "\"realtimeColor\":\""+displayItemList.get(i).getRealtimeColor()+"\","
					+ "\"realtimeBgColor\":\""+displayItemList.get(i).getRealtimeBgColor()+"\","
					+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
					+ "\"historySort\":\""+(displayItemList.get(i).getShowLevel()>0?displayItemList.get(i).getHistorySort():"")+"\","
					+ "\"historyColor\":\""+displayItemList.get(i).getHistoryColor()+"\","
					+ "\"historyBgColor\":\""+displayItemList.get(i).getHistoryBgColor()+"\","
					+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\""
					+ "},");
		}
		
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		
		return result_json.toString();
	}
	
	public void importDisplayUnit(ExportDisplayUnitData exportDisplayUnitData,User user){
		Gson gson=new Gson();
		String unitsql="select t.id,t.unit_code from tbl_display_unit_conf t,tbl_acq_unit_conf t2 "
				+ " where t.acqunitid=t2.id "
				+ " and t.unit_name='"+exportDisplayUnitData.getUnitName()
				+"' and t2.unit_name='"+exportDisplayUnitData.getAcqUnit()
				+"' and t2.protocol='"+exportDisplayUnitData.getProtocol()+"' "
				+ " order by t.id desc";
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		List<?> unitList = this.findCallSql(unitsql);
		String unitId="";
		String unitCode="";
		int r=0;
		if(unitList.size()>0){
			Object[] obj=(Object[]) unitList.get(0);
			unitId=obj[0]+"";
			unitCode=obj[1]+"";
			String delItemSql="delete from TBL_DISPLAY_ITEMS2UNIT_CONF t where t.unitid="+unitId;
			r=this.getBaseDao().updateOrDeleteBySql(delItemSql);
			String updateUnitSql="update tbl_alarm_unit_conf t set t.remark='"+exportDisplayUnitData.getRemark()+"' ,t.calculatetype="+exportDisplayUnitData.getCalculateType()
					+ " where t.id="+unitId;
			r=this.getBaseDao().updateOrDeleteBySql(updateUnitSql);
		}else{
			try {
				
				String acqUnitSql="select t.id from tbl_acq_unit_conf t "
						+ "where t.unit_name='"+exportDisplayUnitData.getAcqUnit()
						+"' and t.protocol='"+exportDisplayUnitData.getProtocol()+"' "
						+ " order by t.id desc";
				String acqUnidId="";
				List<?> acqUnitList = this.findCallSql(acqUnitSql);
				if(acqUnitList.size()>0){
					acqUnidId=acqUnitList.get(0)+"";
				}
				if(StringManagerUtils.isNotNull(acqUnidId)){
					String insertUnitSql="insert into tbl_display_unit_conf (unit_code,unit_name,protocol,acqunitid,calculatetype,remark) "
							+ "values ('"+exportDisplayUnitData.getUnitName()+"','"+exportDisplayUnitData.getUnitName()+"','"+exportDisplayUnitData.getProtocol()+"',"+acqUnidId+","+exportDisplayUnitData.getCalculateType()+",'"+exportDisplayUnitData.getRemark()+"')";
					r=this.getBaseDao().updateOrDeleteBySql(insertUnitSql);
					unitsql="select t.id,t.unit_code from tbl_display_unit_conf t,tbl_acq_unit_conf t2 "
							+ " where t.acqunitid=t2.id "
							+ " and t.unit_name='"+exportDisplayUnitData.getUnitName()
							+"' and t2.unit_name='"+exportDisplayUnitData.getAcqUnit()
							+"' and t2.protocol='"+exportDisplayUnitData.getProtocol()+"' "
							+ " order by t.id desc";
					unitList = this.findCallSql(unitsql);
					unitId="";
					unitCode="";
					if(unitList.size()>0){
						Object[] obj=(Object[]) unitList.get(0);
						unitId=obj[0]+"";
						unitCode=obj[1]+"";
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(StringManagerUtils.isNotNull(unitId) && exportDisplayUnitData.getItemList()!=null && exportDisplayUnitData.getItemList().size()>0){
			for(int i=0;i<exportDisplayUnitData.getItemList().size();i++){
				try {
					DisplayUnitItem displayUnitItem = new DisplayUnitItem();
					displayUnitItem.setUnitId(StringManagerUtils.stringToInteger(unitId));
					displayUnitItem.setItemName(exportDisplayUnitData.getItemList().get(i).getItemName());
					displayUnitItem.setItemCode(exportDisplayUnitData.getItemList().get(i).getItemCode());
					displayUnitItem.setType(exportDisplayUnitData.getItemList().get(i).getType());
					displayUnitItem.setRealtimeSort(exportDisplayUnitData.getItemList().get(i).getRealtimeSort()>0?exportDisplayUnitData.getItemList().get(i).getRealtimeSort():null);
					displayUnitItem.setRealtimeColor(exportDisplayUnitData.getItemList().get(i).getRealtimeColor());
					displayUnitItem.setRealtimeBgColor(exportDisplayUnitData.getItemList().get(i).getRealtimeBgColor());
					displayUnitItem.setHistorySort(exportDisplayUnitData.getItemList().get(i).getHistorySort()>0?exportDisplayUnitData.getItemList().get(i).getHistorySort():null);
					displayUnitItem.setHistoryColor(exportDisplayUnitData.getItemList().get(i).getHistoryColor());
					displayUnitItem.setHistoryBgColor(exportDisplayUnitData.getItemList().get(i).getHistoryBgColor());
					displayUnitItem.setBitIndex(exportDisplayUnitData.getItemList().get(i).getBitIndex()>=0?exportDisplayUnitData.getItemList().get(i).getBitIndex():null);
					displayUnitItem.setShowLevel(exportDisplayUnitData.getItemList().get(i).getShowLevel()>0?exportDisplayUnitData.getItemList().get(i).getShowLevel():null);
					displayUnitItem.setRealtimeCurveConf(exportDisplayUnitData.getItemList().get(i).getRealtimeCurveConf()==null?null:gson.toJson(exportDisplayUnitData.getItemList().get(i).getRealtimeCurveConf()));
					displayUnitItem.setHistoryCurveConf(exportDisplayUnitData.getItemList().get(i).getHistoryCurveConf()==null?null:gson.toJson(exportDisplayUnitData.getItemList().get(i).getHistoryCurveConf()));
					displayUnitItem.setMatrix(exportDisplayUnitData.getItemList().get(i).getMatrix());
					this.grantDisplayItemsPermission(displayUnitItem);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		
		if(StringManagerUtils.isNotNull(unitId)){
			MemoryDataManagerTask.loadDisplayInstanceOwnItemByUnitId(unitId, "update");
			if(user!=null){
				try {
					this.service.saveSystemLog(user,2,languageResourceMap.get("importDisplayUnit")+":"+exportDisplayUnitData.getUnitName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getUploadedReportUnitTreeData(List<ExportReportUnitData> uploadUnitList,String deviceType,User user){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		
		String unitSql="select t.unit_name,t.unit_code from TBL_REPORT_UNIT_CONF t";
		List<?> unitQueryList = this.findCallSql(unitSql);
		
		
		tree_json.append("[");
		if(uploadUnitList!=null && uploadUnitList.size()>0){
			for(int i=0;i<uploadUnitList.size();i++){
				String msg="";
				int saveSign=0;//无冲突覆盖
				
				if(unitQueryList.size()>0){
					for(int j=0;j<unitQueryList.size();j++){
						Object[] obj=(Object[])unitQueryList.get(j);
						if((obj[0]+"").equalsIgnoreCase(uploadUnitList.get(i).getUnitName())){
							saveSign=1;//覆盖
							msg=obj[0]+languageResourceMap.get("uploadCollisionInfo1");
							break;
						}
					}
				}
				
				tree_json.append("{\"classes\":1,");
				tree_json.append("\"text\":\""+uploadUnitList.get(i).getUnitName()+"\",");
				tree_json.append("\"code\":\""+uploadUnitList.get(i).getUnitCode()+"\",");
				tree_json.append("\"msg\":\""+msg+"\",");
				tree_json.append("\"saveSign\":\""+saveSign+"\",");
				tree_json.append("\"iconCls\": \"acqUnit\",");
				tree_json.append("\"action\": \"\",");
				tree_json.append("\"leaf\": true");
				tree_json.append("},");
			}
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("unitList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		
		return result_json.toString();
	}
	
	public String getImportReportUnitTemplateData(List<ExportReportUnitData> uploadUnitList,String reportType,String unitName){
		String result="{}";
		String templateName="";
		ReportTemplate reportTemplate=MemoryDataManagerTask.getReportTemplateConfig();
		if(uploadUnitList!=null && uploadUnitList.size()>0){
			for(int i=0;i<uploadUnitList.size();i++){
				if(unitName.equalsIgnoreCase(uploadUnitList.get(i).getUnitName()) ){
					if(StringManagerUtils.stringToInteger(reportType)==0){
						templateName=uploadUnitList.get(i).getSingleWellRangeReportTemplate();
					}else if(StringManagerUtils.stringToInteger(reportType)==2){
						templateName=uploadUnitList.get(i).getSingleWellDailyReportTemplate();
					}else{
						templateName=uploadUnitList.get(i).getProductionReportTemplate();
					}
					break;
				}
			}
		}
		
		if(StringManagerUtils.isNotNull(templateName) && reportTemplate!=null){
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
					if(templateName.equalsIgnoreCase(templateList.get(i).getTemplateName()) ){
						Gson gson=new Gson();
						if(StringManagerUtils.stringToInteger(reportType)==1){
							result=gson.toJson(templateList.get(i)).replaceAll("orgNameLabel", "label").replaceAll("label", "***");
						}else{
							result=gson.toJson(templateList.get(i)).replaceAll("deviceNameLabel", "label").replaceAll("label", "***");
						}
						break;
					}
				}
			}
		}
		
		return result;
	}
	
	public String getImportReportUnitItemsConfigData(List<ExportReportUnitData> uploadUnitList,String reportType,String unitName,String language){
		StringBuffer result_json = new StringBuffer();
		List<CalItem> calItemList=new ArrayList<>();
		result_json.append("{ \"success\":true,\"columns\":[],");
		result_json.append("\"totalRoot\":[");
		List<ExportReportUnitData.ReportItem> reportItemList=new ArrayList<>();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		if(uploadUnitList!=null && uploadUnitList.size()>0){
			for(int i=0;i<uploadUnitList.size();i++){
				if(unitName.equalsIgnoreCase(uploadUnitList.get(i).getUnitName()) ){
					if(uploadUnitList.get(i).getCalculateType()==2){
						if(uploadUnitList.get(i).getCalculateType()==1){
							calItemList=MemoryDataManagerTask.getSRPTimingTotalCalculateItem(language);
						}else if(uploadUnitList.get(i).getCalculateType()==2){
							calItemList=MemoryDataManagerTask.getPCPTimingTotalCalculateItem(language);
						}else{
							calItemList=MemoryDataManagerTask.getAcqTimingTotalCalculateItem(language);
						}
					}else{
						if(uploadUnitList.get(i).getCalculateType()==1){
							calItemList=MemoryDataManagerTask.getSRPTotalCalculateItem(language);
						}else if(uploadUnitList.get(i).getCalculateType()==2){
							calItemList=MemoryDataManagerTask.getPCPTotalCalculateItem(language);
						}else{
							calItemList=MemoryDataManagerTask.getAcqTotalCalculateItem(language);
						}
					}
					
					if(uploadUnitList.get(i).getItemList()!=null && uploadUnitList.get(i).getItemList().size()>0){
						for(ExportReportUnitData.ReportItem reportItem:uploadUnitList.get(i).getItemList()){
							int itemReportType=-99;
							if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("deviceDailyReport"), reportItem.getReportType(), false)){
								itemReportType=0;
							}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("areaDailyReport"), reportItem.getReportType(), false)){
								itemReportType=1;
							}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("deviceHourlyReport"), reportItem.getReportType(), false)){
								itemReportType=2;
							}
							
							if(StringManagerUtils.stringToInteger(reportType)==itemReportType){
								reportItemList.add(reportItem);
							}
						}
					}
					break;
				}
			}
		}
		
		for(int i=0;i<reportItemList.size();i++){
			String unit="";
			String dataType="\"\"";
			for(CalItem calItem:calItemList){
				if((reportItemList.get(i).getItemCode()).equalsIgnoreCase(calItem.getCode()) ){
					unit=calItem.getUnit();
					dataType=calItem.getDataType()+"";
					break;
				}
			}
			
			boolean sumSign=false;
			boolean averageSign=false;
			String sumSignStr=reportItemList.get(i).getSumSign();
			String averageSignStr=reportItemList.get(i).getAverageSign();
			String reportCurveConfShowValue="";
			if(reportItemList.get(i).getReportCurveConf()!=null){
				reportCurveConfShowValue=reportItemList.get(i).getReportCurveConf().getSort()+";"+(reportItemList.get(i).getReportCurveConf().getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+reportItemList.get(i).getReportCurveConf().getColor();
			}
			
			String curveStatType=reportItemList.get(i).getCurveStatType().replaceAll("null", "");
			
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
			
			result_json.append("{\"id\":"+(i+1)+","
					+ "\"title\":\""+reportItemList.get(i).getItemName()+""+"\","
					+ "\"code\":\""+reportItemList.get(i).getItemCode()+""+"\","
					+ "\"showLevel\":\""+reportItemList.get(i).getShowLevel()+""+"\","
					+ "\"sumSign\":"+sumSign+""+","
					+ "\"averageSign\":"+averageSign+""+","
					+ "\"reportCurveConfShowValue\":\""+reportCurveConfShowValue+""+"\","
					+ "\"curveStatType\":\""+curveStatType+""+"\","
					+ "\"dataType\":"+dataType+","
					+ "\"prec\":\""+reportItemList.get(i).getPrec()+""+"\","
					+ "\"totalType\":\""+reportItemList.get(i).getTotalType()+"\","
					+ "\"dataSource\":\""+reportItemList.get(i).getDataSource()+"\","
					+ "\"unit\":\""+unit+"\","
					+ "\"sort\":\""+reportItemList.get(i).getSort()+""+"\","
					+ "\"matrix\":\""+reportItemList.get(i).getMatrix()+""+"\""
					+ "},");
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public void importReportUnit(ExportReportUnitData exportReportUnitData,User user){
		Gson gson = new Gson();
		String unitsql="select t.id,t.unit_code from TBL_REPORT_UNIT_CONF t where t.unit_name='"+exportReportUnitData.getUnitName()+"' order by t.id desc";
		List<?> unitList = this.findCallSql(unitsql);
		String unitId="";
		String unitCode="";
		int r=0;
		if(unitList.size()>0){
			Object[] obj=(Object[]) unitList.get(0);
			unitId=obj[0]+"";
			unitCode=obj[1]+"";
			String delItemSql="delete from TBL_REPORT_ITEMS2UNIT_CONF t where t.unitid="+unitId+"";
			r=this.getBaseDao().updateOrDeleteBySql(delItemSql);
			String updateUnitSql="update TBL_REPORT_UNIT_CONF t set "
					+ "t.singlewellrangereporttemplate='"+MemoryDataManagerTask.getSingleWellRangeReportTemplateCodeFromName(exportReportUnitData.getSingleWellRangeReportTemplate())+"',"
					+ "t.singlewelldailyreporttemplate='"+MemoryDataManagerTask.getSingleWellDailyReportTemplateCodeFromName(exportReportUnitData.getSingleWellDailyReportTemplate())+"',"
					+ "t.productionreporttemplate='"+MemoryDataManagerTask.getProductionReportTemplateCodeFromName(exportReportUnitData.getProductionReportTemplate())+"',"
					+ "t.calculatetype="+StringManagerUtils.stringToInteger(exportReportUnitData.getCalculateType()+"")+","
					+ "t.sort="+(StringManagerUtils.stringToInteger(exportReportUnitData.getSort())>0?StringManagerUtils.stringToInteger(exportReportUnitData.getSort()):"null")
					+ " where t.id="+unitId;
			r=this.getBaseDao().updateOrDeleteBySql(updateUnitSql);
		}else{
			try {
				String insertUnitSql="insert into TBL_REPORT_UNIT_CONF "
						+ "(unit_code,unit_name,singlewellrangereporttemplate,singlewelldailyreporttemplate,productionreporttemplate,calculatetype,sort) "
						+ "values "
						+ "('"+exportReportUnitData.getUnitName()+"',"
						+ "'"+exportReportUnitData.getUnitName()+"',"
						+ "'"+exportReportUnitData.getSingleWellRangeReportTemplate()+"',"
						+ "'"+exportReportUnitData.getSingleWellDailyReportTemplate()+"',"
						+ "'"+exportReportUnitData.getProductionReportTemplate()+"',"
						+exportReportUnitData.getCalculateType()+","
						+(StringManagerUtils.stringToInteger(exportReportUnitData.getSort())>0?StringManagerUtils.stringToInteger(exportReportUnitData.getSort()):"null")+")";
				r=this.getBaseDao().updateOrDeleteBySql(insertUnitSql);
				unitsql="select t.id,t.unit_code from tbl_alarm_unit_conf t,tbl_protocol t2 "
						+ " where t.protocol=t2.name "
						+ " and t.unit_name='"+exportReportUnitData.getUnitName()+"'"
						+ " order by t.id desc";
				unitList = this.findCallSql(unitsql);
				unitId="";
				unitCode="";
				if(unitList.size()>0){
					Object[] obj=(Object[]) unitList.get(0);
					unitId=obj[0]+"";
					unitCode=obj[1]+"";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(StringManagerUtils.isNotNull(unitId) && exportReportUnitData.getItemList()!=null && exportReportUnitData.getItemList().size()>0){
			for(int i=0;i<exportReportUnitData.getItemList().size();i++){
				try {
					ReportUnitItem reportUnitItem = new ReportUnitItem();
					reportUnitItem.setUnitId(StringManagerUtils.stringToInteger(unitId));
					reportUnitItem.setReportType(StringManagerUtils.stringToInteger(exportReportUnitData.getItemList().get(i).getReportType()));
					reportUnitItem.setItemName(exportReportUnitData.getItemList().get(i).getItemName());
					reportUnitItem.setItemCode(exportReportUnitData.getItemList().get(i).getItemCode());
					
					reportUnitItem.setTotalType( (exportReportUnitData.getItemList().get(i).getTotalType()!=null && StringManagerUtils.isNumber(exportReportUnitData.getItemList().get(i).getTotalType()) )?StringManagerUtils.stringToInteger(exportReportUnitData.getItemList().get(i).getTotalType()):null);
					
					reportUnitItem.setSort( (exportReportUnitData.getItemList().get(i).getSort()!=null && StringManagerUtils.isNumber(exportReportUnitData.getItemList().get(i).getSort()) )?StringManagerUtils.stringToInteger(exportReportUnitData.getItemList().get(i).getSort()):null);
					reportUnitItem.setShowLevel( (exportReportUnitData.getItemList().get(i).getShowLevel()!=null && StringManagerUtils.isNumber(exportReportUnitData.getItemList().get(i).getShowLevel()) )?StringManagerUtils.stringToInteger(exportReportUnitData.getItemList().get(i).getShowLevel()):null);
					
					reportUnitItem.setPrec( (exportReportUnitData.getItemList().get(i).getPrec()!=null && StringManagerUtils.isNumber(exportReportUnitData.getItemList().get(i).getPrec()) )?StringManagerUtils.stringToInteger(exportReportUnitData.getItemList().get(i).getPrec()):null);
					
					reportUnitItem.setSumSign((exportReportUnitData.getItemList().get(i).getSumSign()!=null && StringManagerUtils.isNumber(exportReportUnitData.getItemList().get(i).getSumSign()) )?StringManagerUtils.stringToInteger(exportReportUnitData.getItemList().get(i).getSumSign()):null);
					reportUnitItem.setAverageSign( (exportReportUnitData.getItemList().get(i).getAverageSign()!=null && StringManagerUtils.isNumber(exportReportUnitData.getItemList().get(i).getAverageSign()) )?StringManagerUtils.stringToInteger(exportReportUnitData.getItemList().get(i).getAverageSign()):null);
					
					reportUnitItem.setReportCurveConf(gson.toJson(exportReportUnitData.getItemList().get(i).getReportCurveConf()));
					
					reportUnitItem.setCurveStatType( (exportReportUnitData.getItemList().get(i).getCurveStatType()!=null && StringManagerUtils.isNumber(exportReportUnitData.getItemList().get(i).getCurveStatType()) )?StringManagerUtils.stringToInteger(exportReportUnitData.getItemList().get(i).getCurveStatType()):null);
					
					reportUnitItem.setDataType( (exportReportUnitData.getItemList().get(i).getDataType()!=null && StringManagerUtils.isNumber(exportReportUnitData.getItemList().get(i).getDataType()) )?StringManagerUtils.stringToInteger(exportReportUnitData.getItemList().get(i).getDataType()):null);
					
					reportUnitItem.setDataSource(exportReportUnitData.getItemList().get(i).getDataSource());
					reportUnitItem.setMatrix(exportReportUnitData.getItemList().get(i).getMatrix()!=null?exportReportUnitData.getItemList().get(i).getMatrix():"");
					this.grantReportItemsPermission(reportUnitItem);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		
		if(StringManagerUtils.isNotNull(unitId)){
			if(user!=null){
				try {
					Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
					this.service.saveSystemLog(user,2,languageResourceMap.get("importReportUnit")+":"+exportReportUnitData.getUnitName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public String getUploadedAcqInstanceTreeData(List<ExportAcqInstanceData> uploadInstanceList,String deviceType,User user){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		String allDeviceIds=tabInfoManagerService.queryTabs(user);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		
		List<String> protoolList=new ArrayList<>();
		String protocolSql="select t.name from tbl_protocol t where t.deviceType in("+allDeviceIds+")";
		List<?> protoolQueryList = this.findCallSql(protocolSql);
		for(int i=0;i<protoolQueryList.size();i++){
			protoolList.add(protoolQueryList.get(i)+"");
		}
		
		String instanceSql="select t.name,t2.unit_name,t2.protocol "
				+ " from tbl_protocolinstance t,tbl_acq_unit_conf t2,tbl_protocol t3 "
				+ " where t.unitid=t2.id and t2.protocol=t3.name "
				+ " and t3.devicetype in ("+allDeviceIds+")";
		List<?> instanceQueryList = this.findCallSql(instanceSql);
		
		tree_json.append("[");
		if(uploadInstanceList!=null && uploadInstanceList.size()>0){
			for(int i=0;i<uploadInstanceList.size();i++){
				String msg="";
				int saveSign=0;//无冲突覆盖
				if(StringManagerUtils.existOrNot(protoolList, uploadInstanceList.get(i).getProtocol(), false)){
					String acqUnitSql="select t.unit_name from TBL_ACQ_UNIT_CONF t where t.protocol='"+uploadInstanceList.get(i).getProtocol()+"' and t.unit_name ='"+uploadInstanceList.get(i).getUnitName()+"'";
					List<?> acqUnitQueryList = this.findCallSql(acqUnitSql);
					if(acqUnitQueryList.size()>0){
						if(instanceQueryList.size()>0){
							for(int j=0;j<instanceQueryList.size();j++){
								Object[] obj=(Object[])instanceQueryList.get(j);
								if((obj[0]+"").equalsIgnoreCase(uploadInstanceList.get(i).getName()) 
										&& (obj[1]+"").equalsIgnoreCase(uploadInstanceList.get(i).getUnitName())
										&& (obj[2]+"").equalsIgnoreCase(uploadInstanceList.get(i).getProtocol())
										){
									saveSign=1;//覆盖
									msg=uploadInstanceList.get(i).getName()+languageResourceMap.get("uploadCollisionInfo1");
									break;
								}
							}
						}
					}else{
						saveSign=2;
						msg=languageResourceMap.get("acqUnitDoesNotExist");
					}
				}else{
					saveSign=2;
					msg=languageResourceMap.get("protocolDoesNotExist");
				}
				
				tree_json.append("{\"classes\":1,");
				tree_json.append("\"text\":\""+uploadInstanceList.get(i).getName()+"\",");
				tree_json.append("\"code\":\""+uploadInstanceList.get(i).getCode()+"\",");
				tree_json.append("\"unitName\":\""+uploadInstanceList.get(i).getUnitName()+"\",");
				tree_json.append("\"protocol\":\""+uploadInstanceList.get(i).getProtocol()+"\",");
				tree_json.append("\"msg\":\""+msg+"\",");
				tree_json.append("\"saveSign\":\""+saveSign+"\",");
				tree_json.append("\"iconCls\": \"acqUnit\",");
				tree_json.append("\"action\": \"\",");
				tree_json.append("\"leaf\": true");
				tree_json.append("},");
			}
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("instanceList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		
		return result_json.toString();
	}
	
	public String getImportAcqInstanceItemsData(List<ExportAcqInstanceData> uploadInstanceList,String protocolName,String unitName,String instanceName,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson = new Gson();
		int index=1;
		result_json.append("{ \"success\":true,\"columns\":[],");
		result_json.append("\"totalRoot\":[");
		List<String> itemsList=new ArrayList<String>();
		ModbusProtocolConfig.Protocol protocolConfig=MemoryDataManagerTask.getProtocolByName(protocolName);
		if(uploadInstanceList!=null && protocolConfig!=null){
			for(ExportAcqInstanceData exportAcqInstanceData:uploadInstanceList){
				if(protocolName.equalsIgnoreCase(exportAcqInstanceData.getProtocol())
						&& unitName.equalsIgnoreCase(exportAcqInstanceData.getUnitName())
						&& instanceName.equalsIgnoreCase(exportAcqInstanceData.getName())
						){
					String unitIdSql="select t.id from tbl_acq_unit_conf t  where t.unit_name='"+unitName+"' and t.protocol='"+protocolName+"'";
					List<?> unitList=this.findCallSql(unitIdSql);
					if(unitList.size()>0){
						String unitId=unitList.get(0)+"";
						String itemsSql="select distinct(t.itemname) "
								+ " from tbl_acq_item2group_conf t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4 "
								+ " where t.groupid=t2.id and t2.id=t3.groupid and t3.unitid=t4.id and t4.id="+unitId+"";
						List<?> list=this.findCallSql(itemsSql);
						for(int i=0;i<list.size();i++){
							itemsList.add(list.get(i)+"");
						}
						

						Collections.sort(protocolConfig.getItems());
						for(int j=0;j<protocolConfig.getItems().size();j++){
							if(StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(),false)){
								String RWTypeName=languageResourceMap.get("readOnly");
								if("r".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
									RWTypeName=languageResourceMap.get("readOnly");
								}else if("w".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
									RWTypeName=languageResourceMap.get("writeOnly");
								}else if("rw".equalsIgnoreCase(protocolConfig.getItems().get(j).getRWType())){
									RWTypeName=languageResourceMap.get("readWrite");
								}
								
								String resolutionMode=languageResourceMap.get("numericValue");
								if(protocolConfig.getItems().get(j).getResolutionMode()==0){
									resolutionMode=languageResourceMap.get("switchingValue");
								}else if(protocolConfig.getItems().get(j).getResolutionMode()==1){
									resolutionMode=languageResourceMap.get("enumValue");
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
										+ "\"acqMode\":\""+("active".equalsIgnoreCase(protocolConfig.getItems().get(j).getAcqMode())?languageResourceMap.get("activeAcqModel"):languageResourceMap.get("passiveAcqModel"))+"\"},");
								index++;
							}
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
	
	public void importAcqInstance(ExportAcqInstanceData instanceData,User user){
		Gson gson=new Gson();
		String instanceSql="select t.id,t.code,t.unitId "
				+ " from tbl_protocolinstance t,tbl_acq_unit_conf t2 "
				+ " where t.unitid=t2.id "
				+ " and t.name='"+instanceData.getName()+"' "
				+ " and t2.unit_name='"+instanceData.getUnitName()+"' "
				+ " and t2.protocol='"+instanceData.getProtocol()+"' "
				+ " order by t.id desc";
		List<?> instanceList = this.findCallSql(instanceSql);
		String instanceId="";
		String instanceCode="";
		String unitId="";
		int r=0;
		if(instanceList.size()>0){
			Object[] obj=(Object[]) instanceList.get(0);
			instanceId=obj[0]+"";
			instanceCode=obj[1]+"";
			unitId=obj[2]+"";
			
			ProtocolInstance protocolInstance =new ProtocolInstance();
			
			protocolInstance.setId(StringManagerUtils.stringToInteger(instanceId));
			protocolInstance.setName(instanceData.getName());
			protocolInstance.setCode(instanceCode);
			protocolInstance.setAcqProtocolType(instanceData.getAcqProtocolType());
			protocolInstance.setCtrlProtocolType(instanceData.getCtrlProtocolType());
			protocolInstance.setSignInPrefixSuffixHex(instanceData.getSignInPrefixSuffixHex());
			protocolInstance.setSignInIDHex(instanceData.getSignInIDHex());
			protocolInstance.setSignInPrefix(instanceData.getSignInPrefix());
			protocolInstance.setSignInSuffix(instanceData.getSigninSuffix());
			protocolInstance.setHeartbeatPrefixSuffixHex(instanceData.getHeartbeatPrefixSuffixHex());
			protocolInstance.setHeartbeatPrefix(instanceData.getHeartbeatPrefix());
			protocolInstance.setHeartbeatSuffix(instanceData.getHeartbeatSuffix());
			protocolInstance.setPacketSendInterval(instanceData.getPacketSendInterval());
			protocolInstance.setUnitId(StringManagerUtils.stringToInteger(unitId));
			protocolInstance.setSort(instanceData.getSort());
			try {
				this.doModbusProtocolInstanceEdit(protocolInstance);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				
				String unitSql="select t.id from tbl_acq_unit_conf t "
						+ "where t.unit_name='"+instanceData.getUnitName()
						+"' and t.protocol='"+instanceData.getProtocol()+"' "
						+ " order by t.id desc";
				String unidId="";
				List<?> unitList = this.findCallSql(unitSql);
				if(unitList.size()>0){
					unidId=unitList.get(0)+"";
				}
				if(StringManagerUtils.isNotNull(unidId)){
					ProtocolInstance protocolInstance =new ProtocolInstance();
					
					protocolInstance.setId(StringManagerUtils.stringToInteger(instanceId));
					protocolInstance.setName(instanceData.getName());
					protocolInstance.setCode(instanceCode);
					protocolInstance.setAcqProtocolType(instanceData.getAcqProtocolType());
					protocolInstance.setCtrlProtocolType(instanceData.getCtrlProtocolType());
					protocolInstance.setSignInPrefixSuffixHex(instanceData.getSignInPrefixSuffixHex());
					protocolInstance.setSignInIDHex(instanceData.getSignInIDHex());
					protocolInstance.setSignInPrefix(instanceData.getSignInPrefix());
					protocolInstance.setSignInSuffix(instanceData.getSigninSuffix());
					protocolInstance.setHeartbeatPrefixSuffixHex(instanceData.getHeartbeatPrefixSuffixHex());
					protocolInstance.setHeartbeatPrefix(instanceData.getHeartbeatPrefix());
					protocolInstance.setHeartbeatSuffix(instanceData.getHeartbeatSuffix());
					protocolInstance.setPacketSendInterval(instanceData.getPacketSendInterval());
					protocolInstance.setUnitId(StringManagerUtils.stringToInteger(unitId));
					protocolInstance.setSort(instanceData.getSort());
					this.doModbusProtocolInstanceAdd(protocolInstance);
					
					unitList = this.findCallSql(instanceSql);
					instanceId="";
					instanceCode="";
					unitId="";
					if(instanceList.size()>0){
						Object[] obj=(Object[]) instanceList.get(0);
						instanceId=obj[0]+"";
						instanceCode=obj[1]+"";
						unitId=obj[2]+"";
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		if(StringManagerUtils.isNotNull(instanceId)){
			List<String> instanceInitList=new ArrayList<String>();
			instanceInitList.add(instanceData.getName());
			
			MemoryDataManagerTask.loadAcqInstanceOwnItemById(instanceId,"update");
			MemoryDataManagerTask.loadDeviceInfoByInstanceId(instanceId,"update");
			MemoryDataManagerTask.loadDeviceInfoByInstanceId(instanceId,"update");
			EquipmentDriverServerTask.initInstanceConfig(instanceInitList,"update");
			EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolInstanceId(instanceId,"update");
			
			
			if(user!=null){
				try {
					Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
					this.service.saveSystemLog(user,2,languageResourceMap.get("importAcqInstance")+":"+instanceData.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public String getUploadedDisplayInstanceTreeData(List<ExportDisplayInstanceData> uploadInstanceList,String deviceType,User user){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		String allDeviceIds=tabInfoManagerService.queryTabs(user);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		
		List<String> protoolList=new ArrayList<>();
		String protocolSql="select t.name from tbl_protocol t where t.deviceType in("+allDeviceIds+")";
		List<?> protoolQueryList = this.findCallSql(protocolSql);
		for(int i=0;i<protoolQueryList.size();i++){
			protoolList.add(protoolQueryList.get(i)+"");
		}
		
		String instanceSql="select t.name,t2.unit_name as displayUnitName,t3.unit_name as acqUnitName,t4.name as protocol "
				+ " from TBL_PROTOCOLDISPLAYINSTANCE t ,tbl_display_unit_conf t2,tbl_acq_unit_conf t3,tbl_protocol t4 "
				+ " where t.displayunitid=t2.id and t2.acqunitid=t3.id and t3.protocol=t4.name "
				+ " and t4.devicetype in ("+allDeviceIds+")";
		List<?> instanceQueryList = this.findCallSql(instanceSql);
		
		tree_json.append("[");
		if(uploadInstanceList!=null && uploadInstanceList.size()>0){
			for(int i=0;i<uploadInstanceList.size();i++){
				String msg="";
				int saveSign=0;//无冲突覆盖
				if(StringManagerUtils.existOrNot(protoolList, uploadInstanceList.get(i).getProtocol(), false)){
					String unitSql="select t.unit_name from tbl_display_unit_conf t,tbl_acq_unit_conf t2 "
							+ " where t.acqunitid=t2.id"
							+ " and t2.unit_name='"+uploadInstanceList.get(i).getAcqUnitName()+"' and t.unit_name ='"+uploadInstanceList.get(i).getDisplayUnitName()+"'";
					List<?> unitQueryList = this.findCallSql(unitSql);
					if(unitQueryList.size()>0){
						if(instanceQueryList.size()>0){
							for(int j=0;j<instanceQueryList.size();j++){
								Object[] obj=(Object[])instanceQueryList.get(j);
								if((obj[0]+"").equalsIgnoreCase(uploadInstanceList.get(i).getName()) 
										&& (obj[1]+"").equalsIgnoreCase(uploadInstanceList.get(i).getDisplayUnitName())
										&& (obj[2]+"").equalsIgnoreCase(uploadInstanceList.get(i).getAcqUnitName())
										&& (obj[2]+"").equalsIgnoreCase(uploadInstanceList.get(i).getProtocol())
										){
									saveSign=1;//覆盖
									msg=uploadInstanceList.get(i).getName()+languageResourceMap.get("uploadCollisionInfo1");
									break;
								}
							}
						}
					}else{
						saveSign=2;
						msg=languageResourceMap.get("displayUnitDoesNotExist");
					}
				}else{
					saveSign=2;
					msg=languageResourceMap.get("protocolDoesNotExist");
				}
				
				tree_json.append("{\"classes\":1,");
				tree_json.append("\"text\":\""+uploadInstanceList.get(i).getName()+"\",");
				tree_json.append("\"code\":\""+uploadInstanceList.get(i).getCode()+"\",");
				tree_json.append("\"displayUnitName\":\""+uploadInstanceList.get(i).getDisplayUnitName()+"\",");
				tree_json.append("\"acqUnitName\":\""+uploadInstanceList.get(i).getAcqUnitName()+"\",");
				tree_json.append("\"protocol\":\""+uploadInstanceList.get(i).getProtocol()+"\",");
				tree_json.append("\"msg\":\""+msg+"\",");
				tree_json.append("\"saveSign\":\""+saveSign+"\",");
				tree_json.append("\"iconCls\": \"acqUnit\",");
				tree_json.append("\"action\": \"\",");
				tree_json.append("\"leaf\": true");
				tree_json.append("},");
			}
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("instanceList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		
		return result_json.toString();
	}
	
	public String getImportDisplayInstanceItemsConfigData(List<ExportDisplayInstanceData> uploadInstanceList,
			String protocolName,String acqUnitName,String displayUnitName,String instanceName,String itemType,String language){
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("name")+"\",\"dataIndex\":\"title\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("unit")+"\",\"dataIndex\":\"unit\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("showLevel")+"\",\"dataIndex\":\"showLevel\",width:80 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("dataSort")+"\",\"dataIndex\":\"sort\",width:80 ,children:[] },"
				+ "{ \"header\":\"实时曲线\",\"dataIndex\":\"realtimeCurve\",width:80 ,children:[] },"
				+ "{ \"header\":\"历史曲线\",\"dataIndex\":\"historyCurveColor\",width:80 ,children:[] }"
				+ "]";
		ModbusProtocolConfig.Protocol protocolConfig =MemoryDataManagerTask.getProtocolByName(protocolName);
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		
		if(uploadInstanceList!=null && protocolConfig!=null){
			for(ExportDisplayInstanceData exportDisplayInstanceData:uploadInstanceList){
				if(protocolName.equalsIgnoreCase(exportDisplayInstanceData.getProtocol())
						&& acqUnitName.equalsIgnoreCase(exportDisplayInstanceData.getAcqUnitName())
						&& displayUnitName.equalsIgnoreCase(exportDisplayInstanceData.getDisplayUnitName())
						&& instanceName.equalsIgnoreCase(exportDisplayInstanceData.getName())
						){
					
					String displayUnitSql="select t.id,t.calculatetype from tbl_display_unit_conf t,tbl_acq_unit_conf t2 "
							+ " where t.acqunitid=t2.id "
							+ " and t2.protocol='"+protocolName+"'"
							+ " and t2.unit_name='"+acqUnitName+"'"
							+ " and t.unit_name='"+displayUnitName+"'";
					List<?> displayUnitList=this.findCallSql(displayUnitSql);
					if(displayUnitList.size()>0){
						Object[] displayUnitObj=(Object[])displayUnitList.get(0);
						String displayUnitId=displayUnitObj[0]+"";
						String displayUnitCalType=displayUnitObj[1]+"";
						
						List<String> itemsList=new ArrayList<String>();
						List<String> itemsCodeList=new ArrayList<String>();
						List<String> itemsRealtimeSortList=new ArrayList<String>();
						
						List<String> itemsHistorySortList=new ArrayList<String>();
						List<String> itemsBitIndexList=new ArrayList<String>();
						List<String> itemsShowLevelList=new ArrayList<String>();
						List<String> realtimeCurveConfList=new ArrayList<String>();
						List<String> historyCurveConfList=new ArrayList<String>();
						
						List<String> realtimeColorList=new ArrayList<String>();
						List<String> realtimeBgColorList=new ArrayList<String>();
						List<String> historyColorList=new ArrayList<String>();
						List<String> historyBgColorList=new ArrayList<String>();
						
						String sql="select t.itemname,t.bitindex,"
								+ " t.realtimeSort,t.historySort,"
								+ " t.showlevel,"
								+ " t.realtimeCurveConf,historyCurveConf,"
								+ " t.itemcode, "
								+ " t.realtimeColor,t.realtimeBgColor,t.historyColor,t.historyBgColor "
								+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2,tbl_acq_unit_conf t3"
								+ " where t.unitid=t2.id and t2.acqunitid=t3.id and t.type= "+itemType
								+ " and t3.protocol='"+protocolName+"' "
								+ " and t3.unit_name='"+acqUnitName+"' "
								+ " and t2.unit_name='"+displayUnitName+"'"
								+ " order by t.id";
						
						List<?> list=this.findCallSql(sql);
						
						for(int i=0;i<list.size();i++){
							Object[] obj=(Object[])list.get(i);
							itemsList.add(obj[0]+"");
							itemsBitIndexList.add(obj[1]+"");
							itemsRealtimeSortList.add(obj[2]+"");
							itemsHistorySortList.add(obj[3]+"");
							
							itemsShowLevelList.add(obj[4]+"");
							
							itemsCodeList.add(obj[7]+"");
							
							realtimeColorList.add(obj[8]+"");
							realtimeBgColorList.add(obj[9]+"");
							historyColorList.add(obj[10]+"");
							historyBgColorList.add(obj[11]+"");
							
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
								realtimeCurveConfShowValue=realtimeCurveConfObj.getSort()+";"+(realtimeCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+realtimeCurveConfObj.getColor();
							}
							if(historyCurveConfObj!=null){
								historyCurveConfShowValue=historyCurveConfObj.getSort()+";"+(historyCurveConfObj.getYAxisOpposite()?languageResourceMap.get("right"):languageResourceMap.get("left"))+";"+historyCurveConfObj.getColor();
							}
							
							realtimeCurveConfList.add(realtimeCurveConfShowValue);
							historyCurveConfList.add(historyCurveConfShowValue);
						}
						
						if(StringManagerUtils.stringToInteger(itemType)==0 || StringManagerUtils.stringToInteger(itemType)==2 ){
							Collections.sort(protocolConfig.getItems());
							int index=1;
							for(int j=0;j<protocolConfig.getItems().size();j++){
								if(StringManagerUtils.existOrNot(itemsList, protocolConfig.getItems().get(j).getTitle(), false)){
									String realtimeSort="";
									String historySort="";
									String showLevel="";
									
									String realtimeCurveConfShowValue="";
									String historyCurveConfShowValue="";
									
									String realtimeColor=""; 
									String realtimeBgColor="";
									String historyColor="";
									String historyBgColor="";
									
									if(protocolConfig.getItems().get(j).getResolutionMode()==0
											&&protocolConfig.getItems().get(j).getMeaning()!=null
											&&protocolConfig.getItems().get(j).getMeaning().size()>0){
										Collections.sort(protocolConfig.getItems().get(j).getMeaning());//排序
										for(int k=0;k<protocolConfig.getItems().get(j).getMeaning().size();k++){
											realtimeSort="";
											historySort="";
											showLevel="";
											realtimeCurveConfShowValue="";
											historyCurveConfShowValue="";
											realtimeColor=""; 
											realtimeBgColor="";
											historyColor="";
											historyBgColor="";
											for(int m=0;m<itemsList.size();m++){
												if(itemsList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())
														&&itemsBitIndexList.get(m).equalsIgnoreCase(protocolConfig.getItems().get(j).getMeaning().get(k).getValue()+"")
													){
													realtimeSort=itemsRealtimeSortList.get(m);
													historySort=itemsHistorySortList.get(m);
													showLevel=itemsShowLevelList.get(m);
													realtimeCurveConfShowValue=realtimeCurveConfList.get(m);
													historyCurveConfShowValue=historyCurveConfList.get(m);
													realtimeColor=realtimeColorList.get(m);
													realtimeBgColor=realtimeBgColorList.get(m);
													historyColor=historyColorList.get(m);
													historyBgColor=historyBgColorList.get(m);
													
													result_json.append("{"
															+ "\"id\":"+(index)+","
															+ "\"title\":\""+protocolConfig.getItems().get(j).getMeaning().get(k).getMeaning()+"\","
															+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
															+ "\"showLevel\":\""+showLevel+"\","
															+ "\"realtimeSort\":\""+realtimeSort+"\","
															+ "\"realtimeColor\":\""+realtimeColor+"\","
															+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
															+ "\"historySort\":\""+historySort+"\","
															+ "\"historyColor\":\""+historyColor+"\","
															+ "\"historyBgColor\":\""+historyBgColor+"\","
															+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
															+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\""
															+ "},");
													index++;
													break;
												}
											}
										}
									}else{
										for(int k=0;k<itemsList.size();k++){
											if(itemsList.get(k).equalsIgnoreCase(protocolConfig.getItems().get(j).getTitle())){
												realtimeSort=itemsRealtimeSortList.get(k);
												historySort=itemsHistorySortList.get(k);
												showLevel=itemsShowLevelList.get(k);
												realtimeCurveConfShowValue=realtimeCurveConfList.get(k);
												historyCurveConfShowValue=realtimeCurveConfList.get(k);
												realtimeColor=realtimeColorList.get(k);
												realtimeBgColor=realtimeBgColorList.get(k);
												historyColor=historyColorList.get(k);
												historyBgColor=historyBgColorList.get(k);
												break;
											}
										}
										result_json.append("{"
												+ "\"id\":"+(index)+","
												+ "\"title\":\""+protocolConfig.getItems().get(j).getTitle()+"\","
												+ "\"unit\":\""+protocolConfig.getItems().get(j).getUnit()+"\","
												+ "\"showLevel\":\""+showLevel+"\","
												+ "\"realtimeSort\":\""+realtimeSort+"\","
												+ "\"realtimeColor\":\""+realtimeColor+"\","
												+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
												+ "\"historySort\":\""+historySort+"\","
												+ "\"historyColor\":\""+historyColor+"\","
												+ "\"historyBgColor\":\""+historyBgColor+"\","
												+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
												+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\""
												+ "},");
										index++;
									}
								}
							}
						}else if(StringManagerUtils.stringToInteger(itemType)==1){
							List<CalItem> calItemList=new ArrayList<>();
							try{
								if("1".equalsIgnoreCase(displayUnitCalType)){
									calItemList=MemoryDataManagerTask.getSRPCalculateItem(language);
								}else if("2".equalsIgnoreCase(displayUnitCalType)){
									calItemList=MemoryDataManagerTask.getPCPCalculateItem(language);
								}else{
									calItemList=MemoryDataManagerTask.getAcqCalculateItem(language);
								}
							}catch(Exception e){
								e.printStackTrace();
							}
							
							String dailyTotalItemsSql="select t.itemname,t.dailytotalcalculatename,t6.mappingcolumn "
									+ " from TBL_ACQ_ITEM2GROUP_CONF t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4,tbl_display_unit_conf t5,"
									+ " tbl_datamapping t6 "
									+ " where t.groupid=t2.id and t2.id=t3.groupid and t3.unitid=t4.id and t4.id=t5.acqunitid "
									+ " and t.itemname=t6.name "
									+ " and t.dailytotalcalculate=1 and t5.id= "+displayUnitId
									+ " order by t.id";
							List<?> unitDailyTotalItemsList=this.findCallSql(dailyTotalItemsSql);
							for(int i=0;i<unitDailyTotalItemsList.size();i++){
								Object[] obj=(Object[])unitDailyTotalItemsList.get(i);
								String itemName=obj[0]+"";
								String name=obj[1]+"";
								String code=(obj[2]+"_TOTAL").toUpperCase();
								String unit="";
								if(protocolConfig!=null&&protocolConfig.getItems()!=null){
									for(ModbusProtocolConfig.Items item:protocolConfig.getItems()){
										if(itemName.equalsIgnoreCase(item.getTitle())){
											unit=item.getUnit();
											break;
										}
									}
								}
								CalItem calItem=new CalItem(name,code,unit,2,languageResourceMap.get("configuration"),itemName+languageResourceMap.get("dailyCalculate"));
								calItemList.add(calItem);
							}
							
							int index=1;
							for(CalItem calItem:calItemList){
								if(StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(), false)){
									String realtimeSort="";
									String historySort="";
									String showLevel="";
									String realtimeCurveConfShowValue="";
									String historyCurveConfShowValue="";
									
									String realtimeColor=""; 
									String realtimeBgColor="";
									String historyColor="";
									String historyBgColor="";

									for(int k=0;k<itemsList.size();k++){
										if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
											realtimeSort=itemsRealtimeSortList.get(k);
											historySort=itemsHistorySortList.get(k);
											showLevel=itemsShowLevelList.get(k);
											realtimeCurveConfShowValue=realtimeCurveConfList.get(k);
											historyCurveConfShowValue=historyCurveConfList.get(k);
											realtimeColor=realtimeColorList.get(k);
											realtimeBgColor=realtimeBgColorList.get(k);
											historyColor=historyColorList.get(k);
											historyBgColor=historyBgColorList.get(k);
											break;
										}
									}
									result_json.append("{"
											+ "\"id\":"+index+","
											+ "\"title\":\""+calItem.getName()+"\","
											+ "\"unit\":\""+calItem.getUnit()+"\","
											+ "\"showLevel\":\""+showLevel+"\","
											+ "\"realtimeSort\":\""+realtimeSort+"\","
											+ "\"realtimeColor\":\""+realtimeColor+"\","
											+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
											+ "\"historySort\":\""+historySort+"\","
											+ "\"historyColor\":\""+historyColor+"\","
											+ "\"historyBgColor\":\""+historyBgColor+"\","
											+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
											+ "\"historyCurveConfShowValue\":\""+historyCurveConfShowValue+"\","
											+ "\"dataSource\":\""+calItem.getDataSource()+"\""
											+ "},");
									index++;
								}
							}
						}else if(StringManagerUtils.stringToInteger(itemType)==3){
							List<CalItem> inputItemList=new ArrayList<>();
							try{
								if("1".equalsIgnoreCase(displayUnitCalType)){
									inputItemList=MemoryDataManagerTask.getSRPInputItem(language);
								}else if("2".equalsIgnoreCase(displayUnitCalType)){
									inputItemList=MemoryDataManagerTask.getPCPInputItem(language);
								}
							}catch(Exception e){
								e.printStackTrace();
							}
							int index=1;
							for(CalItem calItem:inputItemList){
								if(StringManagerUtils.existOrNot(itemsCodeList, calItem.getCode(), false)){
									String realtimeSort="";
									String historySort="";
									String showLevel="";
									String realtimeCurveConfShowValue="";
									String historyCurveConfShowValue="";
									String realtimeColor=""; 
									String realtimeBgColor="";
									String historyColor="";
									String historyBgColor="";

									for(int k=0;k<itemsList.size();k++){
										if(itemsCodeList.get(k).equalsIgnoreCase(calItem.getCode())){
											realtimeSort=itemsRealtimeSortList.get(k);
											historySort=itemsHistorySortList.get(k);
											showLevel=itemsShowLevelList.get(k);
											realtimeCurveConfShowValue=realtimeCurveConfList.get(k);
											historyCurveConfShowValue=historyCurveConfList.get(k);
											realtimeColor=realtimeColorList.get(k);
											realtimeBgColor=realtimeBgColorList.get(k);
											historyColor=historyColorList.get(k);
											historyBgColor=historyBgColorList.get(k);
											break;
										}
									}
									result_json.append("{"
											+ "\"id\":"+index+","
											+ "\"title\":\""+calItem.getName()+"\","
											+ "\"unit\":\""+calItem.getUnit()+"\","
											+ "\"showLevel\":\""+showLevel+"\","
											+ "\"realtimeSort\":\""+realtimeSort+"\","
											+ "\"realtimeColor\":\""+realtimeColor+"\","
											+ "\"realtimeBgColor\":\""+realtimeBgColor+"\","
											+ "\"historySort\":\""+historySort+"\","
											+ "\"historyColor\":\""+historyColor+"\","
											+ "\"historyBgColor\":\""+historyBgColor+"\","
											+ "\"realtimeCurveConfShowValue\":\""+realtimeCurveConfShowValue+"\","
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
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public void importDisplayInstance(ExportDisplayInstanceData instanceData,User user){
		Gson gson=new Gson();
		String instanceSql="select t.id,t.code,t.displayunitid "
				+ " from TBL_PROTOCOLDISPLAYINSTANCE t ,tbl_display_unit_conf t2,tbl_acq_unit_conf t3,tbl_protocol t4 "
				+ " where t.displayunitid=t2.id and t2.acqunitid=t3.id and t3.protocol=t4.name "
				+ " and t.name='"+instanceData.getName()+"' "
				+ " and t2.unit_name='"+instanceData.getDisplayUnitName()+"' "
				+ " and t3.unit_name='"+instanceData.getAcqUnitName()+"' "
				+ " and t4.name='"+instanceData.getProtocol()+"' "
				+ " order by t.id desc";
		List<?> instanceList = this.findCallSql(instanceSql);
		String instanceId="";
		String instanceCode="";
		String unitId="";
		int r=0;
		if(instanceList.size()>0){
			Object[] obj=(Object[]) instanceList.get(0);
			instanceId=obj[0]+"";
			instanceCode=obj[1]+"";
			unitId=obj[2]+"";
			
			ProtocolDisplayInstance protocolDisplayInstance=new ProtocolDisplayInstance();
			protocolDisplayInstance.setId(instanceData.getId());
			protocolDisplayInstance.setCode(instanceData.getCode());
			protocolDisplayInstance.setName(instanceData.getName());
			protocolDisplayInstance.setDisplayUnitId(StringManagerUtils.stringToInteger(unitId));
			protocolDisplayInstance.setSort(instanceData.getSort());
			
			
			try {
				this.doModbusProtocolDisplayInstanceEdit(protocolDisplayInstance);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				
				String unitSql="select t.id from tbl_display_unit_conf t,tbl_acq_unit_conf t2 "
						+ "where t.acqunitid=t2.id "
						+ " and t.unit_name='"+instanceData.getDisplayUnitName()+"'"
						+ " and t2.unit_name='"+instanceData.getAcqUnitName()+"'"
						+ " and t2.protocol='"+instanceData.getProtocol()+"' "
						+ " order by t.id desc";
				String unidId="";
				List<?> unitList = this.findCallSql(unitSql);
				if(unitList.size()>0){
					unidId=unitList.get(0)+"";
				}
				if(StringManagerUtils.isNotNull(unidId)){
					ProtocolDisplayInstance protocolDisplayInstance=new ProtocolDisplayInstance();
					protocolDisplayInstance.setId(instanceData.getId());
					protocolDisplayInstance.setCode(instanceData.getCode());
					protocolDisplayInstance.setName(instanceData.getName());
					protocolDisplayInstance.setDisplayUnitId(StringManagerUtils.stringToInteger(unitId));
					protocolDisplayInstance.setSort(instanceData.getSort());
					
					
					this.doModbusProtocolDisplayInstanceAdd(protocolDisplayInstance);
					
					unitList = this.findCallSql(instanceSql);
					instanceId="";
					instanceCode="";
					unitId="";
					if(instanceList.size()>0){
						Object[] obj=(Object[]) instanceList.get(0);
						instanceId=obj[0]+"";
						instanceCode=obj[1]+"";
						unitId=obj[2]+"";
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		if(StringManagerUtils.isNotNull(instanceId)){
			MemoryDataManagerTask.loadDisplayInstanceOwnItemById(instanceId,"update");
			MemoryDataManagerTask.loadDeviceInfoByInstanceId(instanceId,"update");
			MemoryDataManagerTask.loadDeviceInfoByInstanceId(instanceId,"update");
			
			
			if(user!=null){
				try {
					Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
					this.service.saveSystemLog(user,2,languageResourceMap.get("importDisplayInstance")+":"+instanceData.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getUploadedAlarmInstanceTreeData(List<ExportAlarmInstanceData> uploadInstanceList,String deviceType,User user){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		String allDeviceIds=tabInfoManagerService.queryTabs(user);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		
		List<String> protoolList=new ArrayList<>();
		String protocolSql="select t.name from tbl_protocol t where t.deviceType in("+allDeviceIds+")";
		List<?> protoolQueryList = this.findCallSql(protocolSql);
		for(int i=0;i<protoolQueryList.size();i++){
			protoolList.add(protoolQueryList.get(i)+"");
		}
		
		String instanceSql="select t.name,t2.unit_name,t2.protocol "
				+ " from tbl_protocolalarminstance t,tbl_alarm_unit_conf t2,tbl_protocol t3 "
				+ " where t.alarmunitid=t2.id and t2.protocol=t3.name "
				+ " and t3.devicetype in ("+allDeviceIds+")";
		List<?> instanceQueryList = this.findCallSql(instanceSql);
		
		tree_json.append("[");
		if(uploadInstanceList!=null && uploadInstanceList.size()>0){
			for(int i=0;i<uploadInstanceList.size();i++){
				String msg="";
				int saveSign=0;//无冲突覆盖
				if(StringManagerUtils.existOrNot(protoolList, uploadInstanceList.get(i).getProtocol(), false)){
					String unitSql="select t.unit_name from tbl_alarm_unit_conf t where t.protocol='"+uploadInstanceList.get(i).getProtocol()+"' and t.unit_name ='"+uploadInstanceList.get(i).getUnitName()+"'";
					List<?> unitQueryList = this.findCallSql(unitSql);
					if(unitQueryList.size()>0){
						if(instanceQueryList.size()>0){
							for(int j=0;j<instanceQueryList.size();j++){
								Object[] obj=(Object[])instanceQueryList.get(j);
								if((obj[0]+"").equalsIgnoreCase(uploadInstanceList.get(i).getName()) 
										&& (obj[1]+"").equalsIgnoreCase(uploadInstanceList.get(i).getUnitName())
										&& (obj[2]+"").equalsIgnoreCase(uploadInstanceList.get(i).getProtocol())
										){
									saveSign=1;//覆盖
									msg=uploadInstanceList.get(i).getName()+languageResourceMap.get("uploadCollisionInfo1");
									break;
								}
							}
						}
					}else{
						saveSign=2;
						msg=languageResourceMap.get("alarmUnitDoesNotExist");
					}
				}else{
					saveSign=2;
					msg=languageResourceMap.get("protocolDoesNotExist");
				}
				
				tree_json.append("{\"classes\":1,");
				tree_json.append("\"text\":\""+uploadInstanceList.get(i).getName()+"\",");
				tree_json.append("\"code\":\""+uploadInstanceList.get(i).getCode()+"\",");
				tree_json.append("\"unitName\":\""+uploadInstanceList.get(i).getUnitName()+"\",");
				tree_json.append("\"protocol\":\""+uploadInstanceList.get(i).getProtocol()+"\",");
				tree_json.append("\"msg\":\""+msg+"\",");
				tree_json.append("\"saveSign\":\""+saveSign+"\",");
				tree_json.append("\"iconCls\": \"acqUnit\",");
				tree_json.append("\"action\": \"\",");
				tree_json.append("\"leaf\": true");
				tree_json.append("},");
			}
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("instanceList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		
		return result_json.toString();
	}
	
	public String getImportAlarmInstanceItemsData(List<ExportAlarmInstanceData> uploadInstanceList,String protocolName,String unitName,String instanceName,String alarmType,User user){
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		result_json.append("{ \"success\":true,\"columns\":[],");
		result_json.append("\"totalRoot\":[");
		Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
		if(uploadInstanceList!=null && protocolName!=null){
			for(ExportAlarmInstanceData exportAlarmInstanceData:uploadInstanceList){
				if(protocolName.equalsIgnoreCase(exportAlarmInstanceData.getProtocol())
						&& unitName.equalsIgnoreCase(exportAlarmInstanceData.getUnitName())
						&& instanceName.equalsIgnoreCase(exportAlarmInstanceData.getName())
						){
					String unitSql="select t.id from tbl_alarm_unit_conf t where t.protocol='"+protocolName+"' and t.unit_name='"+unitName+"'";
					List<?> unitList=this.findCallSql(unitSql);
					if(unitList.size()>0){
						String unitId=unitList.get(0)+"";
						String itemsSql="select t.id, t.itemname,t.itemcode,t.itemaddr,"
								+ "t.value,"
								+ "t.upperlimit,t.lowerlimit,t.hystersis,t.delay,"
								+ "t.bitIndex,"
								+ "t.alarmlevel,"
								+ " decode(t.alarmsign,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"'), "
								+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
								+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail "
								+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2 "
								+ " where t.unitid=t2.id "
								+ " and t2.id="+unitId+" "
								+ " and t.type="+alarmType
								+ " order by t.itemaddr";
						List<?> list=this.findCallSql(itemsSql);
						for(int i=0;i<list.size();i++){
							Object[] obj = (Object[]) list.get(i);
							int itemAddr=StringManagerUtils.stringToInteger(obj[3]+"");
							int bitIndex=StringManagerUtils.stringToInteger(obj[9]+"");
							int value=StringManagerUtils.stringToInteger(obj[4]+"");
							String meaning="";
							
							if(StringManagerUtils.stringToInteger(alarmType)==0){
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
							}else if(StringManagerUtils.stringToInteger(alarmType)==1){
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
							}
							
							result_json.append("{\"id\":"+(i+1)+","
									+ "\"title\":\""+obj[1]+"\","
									+ "\"code\":\""+obj[2]+"\","
									+ "\"addr\":\""+itemAddr+"\","
									+ "\"value\":\""+obj[4]+"\","
									+ "\"meaning\":\""+meaning+"\","
									+ "\"upperLimit\":\""+obj[5]+"\","
									+ "\"lowerLimit\":\""+obj[6]+"\","
									+ "\"hystersis\":\""+obj[7]+"\","
									+ "\"delay\":\""+obj[8]+"\","
									+ "\"bitIndex\":\""+bitIndex+"\","
									+ "\"alarmLevel\":\""+MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[10]+"", user.getLanguageName())+"\","
									+ "\"alarmSign\":\""+obj[11]+"\","
									+ "\"isSendMessage\":\""+obj[12]+"\","
									+ "\"isSendMail\":\""+obj[13]+"\"},");
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
		return result_json.toString().replaceAll("null", "");
	}
	
	public void importAlarmInstance(ExportAlarmInstanceData instanceData,User user){
		Gson gson=new Gson();
		String instanceSql="select t.id,t.code,t.unitId "
				+ " from tbl_protocolalarminstance t,tbl_alarm_unit_conf t2 "
				+ " where t.alarmunitid=t2.id "
				+ " and t.name='"+instanceData.getName()+"' "
				+ " and t2.unit_name='"+instanceData.getUnitName()+"' "
				+ " and t2.protocol='"+instanceData.getProtocol()+"' "
				+ " order by t.id desc";
		List<?> instanceList = this.findCallSql(instanceSql);
		String instanceId="";
		String instanceCode="";
		String unitId="";
		int r=0;
		if(instanceList.size()>0){
			Object[] obj=(Object[]) instanceList.get(0);
			instanceId=obj[0]+"";
			instanceCode=obj[1]+"";
			unitId=obj[2]+"";
			
			ProtocolAlarmInstance protocolAlarmInstance=new ProtocolAlarmInstance();
			protocolAlarmInstance.setId(instanceData.getId());
			protocolAlarmInstance.setCode(instanceData.getCode());
			protocolAlarmInstance.setName(instanceData.getName());
			protocolAlarmInstance.setAlarmUnitId(StringManagerUtils.stringToInteger(unitId));
			protocolAlarmInstance.setSort(instanceData.getSort());
			try {
				this.doModbusProtocolAlarmInstanceEdit(protocolAlarmInstance);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				
				String unitSql="select t.id from tbl_alarm_unit_conf t "
						+ "where t.unit_name='"+instanceData.getUnitName()+"' "
						+" and t.protocol='"+instanceData.getProtocol()+"' "
						+ " order by t.id desc";
				String unidId="";
				List<?> unitList = this.findCallSql(unitSql);
				if(unitList.size()>0){
					unidId=unitList.get(0)+"";
				}
				if(StringManagerUtils.isNotNull(unidId)){
					ProtocolAlarmInstance protocolAlarmInstance=new ProtocolAlarmInstance();
					protocolAlarmInstance.setId(instanceData.getId());
					protocolAlarmInstance.setCode(instanceData.getCode());
					protocolAlarmInstance.setName(instanceData.getName());
					protocolAlarmInstance.setAlarmUnitId(StringManagerUtils.stringToInteger(unitId));
					protocolAlarmInstance.setSort(instanceData.getSort());
					this.doModbusProtocolAlarmInstanceAdd(protocolAlarmInstance);
					
					unitList = this.findCallSql(instanceSql);
					instanceId="";
					instanceCode="";
					unitId="";
					if(instanceList.size()>0){
						Object[] obj=(Object[]) instanceList.get(0);
						instanceId=obj[0]+"";
						instanceCode=obj[1]+"";
						unitId=obj[2]+"";
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		if(StringManagerUtils.isNotNull(instanceId)){
			MemoryDataManagerTask.loadAlarmInstanceOwnItemById(instanceId,"update");
			MemoryDataManagerTask.loadDeviceInfoByAlarmInstanceId(instanceId,"update");
			MemoryDataManagerTask.loadDeviceInfoByAlarmInstanceId(instanceId,"update");
			if(user!=null){
				try {
					Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
					this.service.saveSystemLog(user,2,languageResourceMap.get("importAlarmInstance")+":"+instanceData.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public String getUploadedReportInstanceTreeData(List<ExportReportInstanceData> uploadInstanceList,String deviceType,User user){
		StringBuffer result_json = new StringBuffer();
		StringBuffer tree_json = new StringBuffer();
		String allDeviceIds=tabInfoManagerService.queryTabs(user);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		String instanceSql="select t.name,t2.unit_name "
				+ " from tbl_protocolreportinstance t,tbl_report_unit_conf t2 "
				+ " where t.unitid=t2.id ";
		List<?> instanceQueryList = this.findCallSql(instanceSql);
		
		tree_json.append("[");
		if(uploadInstanceList!=null && uploadInstanceList.size()>0){
			for(int i=0;i<uploadInstanceList.size();i++){
				String msg="";
				int saveSign=0;//无冲突覆盖

				String unitSql="select t.unit_name from tbl_report_unit_conf t where  t.unit_name ='"+uploadInstanceList.get(i).getUnitName()+"'";
				List<?> unitQueryList = this.findCallSql(unitSql);
				if(unitQueryList.size()>0){
					if(instanceQueryList.size()>0){
						for(int j=0;j<instanceQueryList.size();j++){
							Object[] obj=(Object[])instanceQueryList.get(j);
							if((obj[0]+"").equalsIgnoreCase(uploadInstanceList.get(i).getName()) 
									&& (obj[1]+"").equalsIgnoreCase(uploadInstanceList.get(i).getUnitName())
									){
								saveSign=1;//覆盖
								msg=uploadInstanceList.get(i).getName()+languageResourceMap.get("uploadCollisionInfo1");
								break;
							}
						}
					}
				}else{
					saveSign=2;
					msg=languageResourceMap.get("reportUnitDoesNotExist");
				}
				tree_json.append("{\"classes\":1,");
				tree_json.append("\"text\":\""+uploadInstanceList.get(i).getName()+"\",");
				tree_json.append("\"code\":\""+uploadInstanceList.get(i).getCode()+"\",");
				tree_json.append("\"unitName\":\""+uploadInstanceList.get(i).getUnitName()+"\",");
				tree_json.append("\"msg\":\""+msg+"\",");
				tree_json.append("\"saveSign\":\""+saveSign+"\",");
				tree_json.append("\"iconCls\": \"acqUnit\",");
				tree_json.append("\"action\": \"\",");
				tree_json.append("\"leaf\": true");
				tree_json.append("},");
			}
		}
		if(tree_json.toString().endsWith(",")){
			tree_json.deleteCharAt(tree_json.length() - 1);
		}
		tree_json.append("]");
		
		result_json.append("[");
		result_json.append("{\"classes\":0,\"text\":\""+languageResourceMap.get("instanceList")+"\",\"deviceType\":0,\"iconCls\": \"device\",\"expanded\": true,\"children\": "+tree_json+"}");
		result_json.append("]");
		
		return result_json.toString();
	}
	
	public void importReportInstance(ExportReportInstanceData instanceData,User user){
		Gson gson=new Gson();
		String instanceSql="select t.id,t.code,t.unitId "
				+ " from tbl_protocolreportinstance t,tbl_report_unit_conf t2 "
				+ " where t.unitid=t2.id "
				+ " and t.name='"+instanceData.getName()+"' "
				+ " and t2.unit_name='"+instanceData.getUnitName()+"' "
				+ " order by t.id desc";
		List<?> instanceList = this.findCallSql(instanceSql);
		String instanceId="";
		String instanceCode="";
		String unitId="";
		int r=0;
		if(instanceList.size()>0){
			Object[] obj=(Object[]) instanceList.get(0);
			instanceId=obj[0]+"";
			instanceCode=obj[1]+"";
			unitId=obj[2]+"";
			
			ProtocolReportInstance protocolReportInstance=new ProtocolReportInstance();
			protocolReportInstance.setId(instanceData.getId());
			protocolReportInstance.setCode(instanceData.getCode());
			protocolReportInstance.setName(instanceData.getName());
			protocolReportInstance.setUnitId(StringManagerUtils.stringToInteger(unitId));
			protocolReportInstance.setSort(instanceData.getSort());
			
			try {
				this.doModbusProtocolReportInstanceEdit(protocolReportInstance);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				
				String unitSql="select t.id from tbl_report_unit_conf t "
						+ "where t.unit_name='"+instanceData.getUnitName()+"' "
						+ " order by t.id desc";
				String unidId="";
				List<?> unitList = this.findCallSql(unitSql);
				if(unitList.size()>0){
					unidId=unitList.get(0)+"";
				}
				if(StringManagerUtils.isNotNull(unidId)){
					ProtocolReportInstance protocolReportInstance=new ProtocolReportInstance();
					protocolReportInstance.setId(instanceData.getId());
					protocolReportInstance.setCode(instanceData.getCode());
					protocolReportInstance.setName(instanceData.getName());
					protocolReportInstance.setUnitId(StringManagerUtils.stringToInteger(unitId));
					protocolReportInstance.setSort(instanceData.getSort());
					this.doModbusProtocolReportInstanceAdd(protocolReportInstance);
					
					unitList = this.findCallSql(instanceSql);
					instanceId="";
					instanceCode="";
					unitId="";
					if(instanceList.size()>0){
						Object[] obj=(Object[]) instanceList.get(0);
						instanceId=obj[0]+"";
						instanceCode=obj[1]+"";
						unitId=obj[2]+"";
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(StringManagerUtils.isNotNull(instanceId)){
			if(user!=null){
				try {
					Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
					this.service.saveSystemLog(user,2,languageResourceMap.get("importReportInstance")+":"+instanceData.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void doModbusProtocolDisplayInstanceAdd(ProtocolDisplayInstance protocolDisplayInstance) throws Exception {
		getBaseDao().addObject(protocolDisplayInstance);
	}
	
	public void doModbusProtocolReportUnitAdd(T reportUnit) throws Exception {
		getBaseDao().addObject(reportUnit);
	}
	
	public void doModbusProtocolReportInstanceAdd(ProtocolReportInstance protocolReportInstance) throws Exception {
		getBaseDao().addObject(protocolReportInstance);
	}
	
	public void doModbusProtocolAlarmInstanceAdd(ProtocolAlarmInstance protocolAlarmInstance) throws Exception {
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
