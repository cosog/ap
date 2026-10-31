package com.cosog.service.alarmQuery;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AlarmShowStyle;
import com.cosog.model.User;
import com.cosog.model.WorkType;
import com.cosog.model.data.DataDictionary;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.DeviceAlarmInfo;
import com.cosog.utils.DeviceAlarmInfoMap;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.excel.ExcelUtils;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

@Service("alarmQueryService")
public class AlarmQueryService<T> extends BaseService<T>  {

	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	
	public String getAlarmStatData(String orgId,String deviceType,String statType,String alarmQueryStatRangeType,Page pager,String language){
		StringBuffer result_json = new StringBuffer();
		int diagramResultAlarmDeviceCount=0,diagramResultAlarmLevel1DeviceCount=0,diagramResultAlarmLevel2DeviceCount=0,diagramResultAlarmLevel3DeviceCount=0;
		int commStatusAlarmDeviceCount=0,commStatusAlarmLevel1DeviceCount=0,commStatusAlarmLevel2DeviceCount=0,commStatusAlarmLevel3DeviceCount=0;
		int	runStatusAlarmDeviceCount=0,runStatusAlarmLevel1DeviceCount=0,runStatusAlarmLevel2DeviceCount=0,runStatusAlarmLevel3DeviceCount=0;
		int numericValueAlarmDeviceCount=0,numericValueAlarmLevel1DeviceCount=0,numericValueAlarmLevel2DeviceCount=0,numericValueAlarmLevel3DeviceCount=0;
		int enumValueAlarmDeviceCount=0,enumValueAlarmLevel1DeviceCount=0,enumValueAlarmLevel2DeviceCount=0,enumValueAlarmLevel3DeviceCount=0;
		int switchingValueAlarmDeviceCount=0,switchingValueAlarmLevel1DeviceCount=0,switchingValueAlarmLevel2DeviceCount=0,switchingValueAlarmLevel3DeviceCount=0;
		int alarmLevel1DeviceCount=0,alarmLevel2DeviceCount=0,alarmLevel3DeviceCount=0;
		
//		String statColumn="alarmType";
//		if(StringManagerUtils.stringToInteger(statType)==1){
//			statColumn="alarmLevel";
//		}
		
		String alarmTypeStatSql="select t2.alarmtype,t2.alarmlevel,count(1) from VIW_ALARMINFO_LATEST t2,"
				+ " (select t.deviceid,t.alarmType,max(t.id) as id from VIW_ALARMINFO_LATEST t  "
				+ " where t.orgid in ("+orgId+")"
				+ " and t.devicetype in ("+deviceType+")";
		String alarmLevelStatSql="select t2.alarmtype,t2.alarmlevel,count(1) from VIW_ALARMINFO_LATEST t2,"
				+ " (select t.deviceid,t.alarmLevel,max(t.id) as id from VIW_ALARMINFO_LATEST t  "
				+ " where t.orgid in ("+orgId+")"
				+ " and t.devicetype in ("+deviceType+")";
		if("0".equalsIgnoreCase(alarmQueryStatRangeType)){
			String date=StringManagerUtils.getCurrentTime();
			alarmTypeStatSql+=" and t.alarmtime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
			alarmLevelStatSql+=" and t.alarmtime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
		}
		alarmTypeStatSql+= " group by t.deviceid,t.alarmtype"
				+ " ) v"
				+ " where t2.id=v.id"
				+ " group by t2.alarmtype,t2.alarmlevel";
		alarmLevelStatSql+= " group by t.deviceid,t.alarmLevel"
				+ " ) v"
				+ " where t2.id=v.id"
				+ " group by t2.alarmtype,t2.alarmlevel";
		
		List<?> alarmTypeStatList=this.findCallSql(alarmTypeStatSql);
		List<?> alarmLevelStatList=this.findCallSql(alarmLevelStatSql);
		for(int i=0;i<alarmLevelStatList.size();i++){
			Object[]obj=(Object[]) alarmLevelStatList.get(i);
			int alarmType=StringManagerUtils.stringToInteger(obj[0]+"");
			int alarmLevel=StringManagerUtils.stringToInteger(obj[1]+"");
			int deviceCount=StringManagerUtils.stringToInteger(obj[2]+"");
			if(alarmLevel==100){
				alarmLevel1DeviceCount+=deviceCount;
			}else if(alarmLevel==200){
				alarmLevel2DeviceCount+=deviceCount;
			}else if(alarmLevel==300){
				alarmLevel3DeviceCount+=deviceCount;
			}
		}
		for(int i=0;i<alarmTypeStatList.size();i++){
			Object[]obj=(Object[]) alarmTypeStatList.get(i);
			int alarmType=StringManagerUtils.stringToInteger(obj[0]+"");
			int alarmLevel=StringManagerUtils.stringToInteger(obj[1]+"");
			int deviceCount=StringManagerUtils.stringToInteger(obj[2]+"");
			
			if(alarmType==4){
				diagramResultAlarmDeviceCount+=deviceCount;
				if(alarmLevel==100){
					diagramResultAlarmLevel1DeviceCount+=deviceCount;
				}else if(alarmLevel==200){
					diagramResultAlarmLevel2DeviceCount+=deviceCount;
				}else if(alarmLevel==300){
					diagramResultAlarmLevel3DeviceCount+=deviceCount;
				}
			}else if(alarmType==3){
				commStatusAlarmDeviceCount+=deviceCount;
				if(alarmLevel==100){
					commStatusAlarmLevel1DeviceCount+=deviceCount;
				}else if(alarmLevel==200){
					commStatusAlarmLevel2DeviceCount+=deviceCount;
				}else if(alarmLevel==300){
					commStatusAlarmLevel3DeviceCount+=deviceCount;
				}
			}else if(alarmType==6){
				runStatusAlarmDeviceCount+=deviceCount;
				if(alarmLevel==100){
					runStatusAlarmLevel1DeviceCount+=deviceCount;
				}else if(alarmLevel==200){
					runStatusAlarmLevel2DeviceCount+=deviceCount;
				}else if(alarmLevel==300){
					runStatusAlarmLevel3DeviceCount+=deviceCount;
				}
			}else if(alarmType==2 || alarmType==5 || alarmType==7){
				numericValueAlarmDeviceCount+=deviceCount;
				if(alarmLevel==100){
					numericValueAlarmLevel1DeviceCount+=deviceCount;
				}else if(alarmLevel==200){
					numericValueAlarmLevel2DeviceCount+=deviceCount;
				}else if(alarmLevel==300){
					numericValueAlarmLevel3DeviceCount+=deviceCount;
				}
			}else if(alarmType==0){
				switchingValueAlarmDeviceCount+=deviceCount;
				if(alarmLevel==100){
					switchingValueAlarmLevel1DeviceCount+=deviceCount;
				}else if(alarmLevel==200){
					switchingValueAlarmLevel2DeviceCount+=deviceCount;
				}else if(alarmLevel==300){
					switchingValueAlarmLevel3DeviceCount+=deviceCount;
				}
			}else if(alarmType==1){
				enumValueAlarmDeviceCount+=deviceCount;
				if(alarmLevel==100){
					enumValueAlarmLevel1DeviceCount+=deviceCount;
				}else if(alarmLevel==200){
					enumValueAlarmLevel2DeviceCount+=deviceCount;
				}else if(alarmLevel==300){
					enumValueAlarmLevel3DeviceCount+=deviceCount;
				}
			}
		}
		
		
		result_json.append("{\"success\":true,"
				+ "\"totalCount\":"+6+","
				+ "\"start_date\":\""+pager.getStart_date()+"\","
				+ "\"end_date\":\""+pager.getEnd_date()+"\","
				+ "\"diagramResultAlarmDeviceCount\":"+diagramResultAlarmDeviceCount+","
				+ "\"diagramResultAlarmLevel1DeviceCount\":"+diagramResultAlarmLevel1DeviceCount+","
				+ "\"diagramResultAlarmLevel2DeviceCount\":"+diagramResultAlarmLevel2DeviceCount+","
				+ "\"diagramResultAlarmLevel3DeviceCount\":"+diagramResultAlarmLevel3DeviceCount+","
				
				+ "\"commStatusAlarmDeviceCount\":"+commStatusAlarmDeviceCount+","
				+ "\"commStatusAlarmLevel1DeviceCount\":"+commStatusAlarmLevel1DeviceCount+","
				+ "\"commStatusAlarmLevel2DeviceCount\":"+commStatusAlarmLevel2DeviceCount+","
				+ "\"commStatusAlarmLevel3DeviceCount\":"+commStatusAlarmLevel3DeviceCount+","
				
				+ "\"runStatusAlarmDeviceCount\":"+runStatusAlarmDeviceCount+","
				+ "\"runStatusAlarmLevel1DeviceCount\":"+runStatusAlarmLevel1DeviceCount+","
				+ "\"runStatusAlarmLevel2DeviceCount\":"+runStatusAlarmLevel2DeviceCount+","
				+ "\"runStatusAlarmLevel3DeviceCount\":"+runStatusAlarmLevel3DeviceCount+","
				
				+ "\"numericValueAlarmDeviceCount\":"+numericValueAlarmDeviceCount+","
				+ "\"numericValueAlarmLevel1DeviceCount\":"+numericValueAlarmLevel1DeviceCount+","
				+ "\"numericValueAlarmLevel2DeviceCount\":"+numericValueAlarmLevel2DeviceCount+","
				+ "\"numericValueAlarmLevel3DeviceCount\":"+numericValueAlarmLevel3DeviceCount+","
				
				+ "\"enumValueAlarmDeviceCount\":"+enumValueAlarmDeviceCount+","
				+ "\"enumValueAlarmLevel1DeviceCount\":"+enumValueAlarmLevel1DeviceCount+","
				+ "\"enumValueAlarmLevel2DeviceCount\":"+enumValueAlarmLevel2DeviceCount+","
				+ "\"enumValueAlarmLevel3DeviceCount\":"+enumValueAlarmLevel3DeviceCount+","
				
				+ "\"switchingValueAlarmDeviceCount\":"+switchingValueAlarmDeviceCount+","
				+ "\"switchingValueAlarmLevel1DeviceCount\":"+switchingValueAlarmLevel1DeviceCount+","
				+ "\"switchingValueAlarmLevel2DeviceCount\":"+switchingValueAlarmLevel2DeviceCount+","
				+ "\"switchingValueAlarmLevel3DeviceCount\":"+switchingValueAlarmLevel3DeviceCount+","
				
				+ "\"alarmLevel1DeviceCount\":"+alarmLevel1DeviceCount+","
				+ "\"alarmLevel2DeviceCount\":"+alarmLevel2DeviceCount+","
				+ "\"alarmLevel3DeviceCount\":"+alarmLevel3DeviceCount
				+"}");
		
		return result_json.toString();
	}
	
	public String getAlarmData(String orgId,String deviceType,String deviceId,String deviceName,String dictDeviceType,String alarmType,String alarmLevel,String isSendMessage,Page pager,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String ddicCode="alarmQuery_CommStatusAlarm";
		if(StringManagerUtils.stringToInteger(alarmType)==0){
			ddicCode="alarmQuery_StatusAlarm";
		}else if(StringManagerUtils.stringToInteger(alarmType)==1){
			ddicCode="alarmQuery_EnumeratedAlarm";
		}else if(StringManagerUtils.stringToInteger(alarmType)==2){
			ddicCode="alarmQuery_DigitalAlarm";
		}else if(StringManagerUtils.stringToInteger(alarmType)==3){
			ddicCode="alarmQuery_CommStatusAlarm";
		}
		
		String tableName="viw_alarminfo_hist";
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		DataDictionary ddic = null;
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicCode,dictDeviceType,language);
		String columns = ddic.getTableHeader();
		String sql="select t.id,t.deviceid,t.devicename,t.devicetype,t.deviceTypeName_"+language+",to_char(t.alarmtime,'yyyy-mm-dd hh24:mi:ss') as alarmtime,"
				+ " t.itemname,t.alarmtype,"
				+ " t.alarmvalue,t.alarminfo,t.alarmlimit,t.hystersis,"
				+ " t.alarmlevel,"
				+ " t.delay,t.retriggertime,"
				+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
				+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail,"
				+ " t.recoverytime,"
				+ " t.ack_status,decode(t.ack_status,1,'"+languageResourceMap.get("confirmed")+"','"+languageResourceMap.get("unconfirmed")+"') as ackStatusName,"
				+ " decode(t.ack_status,1,t.ack_user,''),"
				+ " decode(t.ack_status,1,t.ack_ip,''),"
				+ " decode(t.ack_status,1,to_char(t.ack_time,'yyyy-mm-dd hh24:mi:ss'),'') as ackTime,"
				+ " t.orgid "
				+ " from "+tableName+" t where t.orgid in ("+orgId+") "
				+ " and t.alarmtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
		if(StringManagerUtils.isNotNull(deviceId)){
			sql+=" and t.deviceid="+deviceId;
		}
		if(StringManagerUtils.isNotNull(alarmType)){
			if(StringManagerUtils.stringToInteger(alarmType)==2){
				sql+=" and t.alarmType in(2,5,7)";
			}else {
				sql+=" and t.alarmType="+alarmType;
			}
		}
		if(StringManagerUtils.isNotNull(alarmLevel)){
			sql+=" and t.alarmLevel="+alarmLevel;
		}
		if(StringManagerUtils.isNotNull(isSendMessage)){
			sql+=" and t.isSendMessage="+isSendMessage;
		}
		sql+=" order by t.alarmtime desc";
		int maxvalue=pager.getLimit()+pager.getStart();
		String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		
		List<?> list=this.findCallSql(finalSql);
		int totals=this.getTotalCountRows(sql);
		
		result_json.append("{ \"success\":true,\"totalCount\":"+totals+ ",\"start_date\":\"" + pager.getStart_date() + "\",\"end_date\":\"" + pager.getEnd_date() + "\",\"columns\":"+columns+",\"totalRoot\":[");
		
		for(int i=0;i<list.size();i++){
			Object[]obj=(Object[]) list.get(i);
			
			String itemName=obj[6]+"";
			String alarmTypeStr=obj[7]+"";
			String alarmValue=obj[8]+"";
			String alarmInfo=obj[9]+"";
			
			if(StringManagerUtils.stringToInteger(alarmTypeStr)==0){
				if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("switchingOpenValue"), alarmInfo, true)){
					alarmInfo=languageResourceMap.get("switchingOpenValue");
				}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("switchingCloseValue"), alarmInfo, true)){
					alarmInfo=languageResourceMap.get("switchingCloseValue");
				}
			}else if(StringManagerUtils.stringToInteger(alarmTypeStr)==1){
				
			}else if(StringManagerUtils.stringToInteger(alarmTypeStr)==2){
				if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("highAlarm"), alarmInfo, true)){
					alarmInfo=languageResourceMap.get("highAlarm");
				}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("lowAlarm"), alarmInfo, true)){
					alarmInfo=languageResourceMap.get("lowAlarm");
				}
			}else if(StringManagerUtils.stringToInteger(alarmTypeStr)==3){
				itemName=languageResourceMap.get("commStatusAlarm");
				if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("goOnline"), alarmInfo, true)){
					alarmInfo=languageResourceMap.get("goOnline");
				}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("online"), alarmInfo, true)){
					alarmInfo=languageResourceMap.get("online");
				}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("offline"), alarmInfo, true)){
					alarmInfo=languageResourceMap.get("offline");
				}
			}else if(StringManagerUtils.stringToInteger(alarmTypeStr)==4){
				itemName=languageResourceMap.get("FESDiagramResultAlarm");
				alarmInfo =MemoryDataManagerTask.getWorkTypeName(alarmValue,language);
			}else if(StringManagerUtils.stringToInteger(alarmTypeStr)==5){
				if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("highAlarm"), alarmInfo, true)){
					alarmInfo=languageResourceMap.get("highAlarm");
				}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("lowAlarm"), alarmInfo, true)){
					alarmInfo=languageResourceMap.get("lowAlarm");
				}
				itemName=MemoryDataManagerTask.calItemLanguageSwitchover(itemName,language);
			}else if(StringManagerUtils.stringToInteger(alarmTypeStr)==6){
				itemName=languageResourceMap.get("runStatusAlarm");
				
				if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("run"), alarmInfo, true)){
					alarmInfo=languageResourceMap.get("run");
				}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("stop"), alarmInfo, true)){
					alarmInfo=languageResourceMap.get("stop");
				}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("emptyMsg"), alarmInfo, true)){
					alarmInfo=languageResourceMap.get("emptyMsg");
				}
				
			}
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"deviceId\":\""+obj[1]+"\",");
			result_json.append("\"deviceName\":\""+obj[2]+"\",");
			result_json.append("\"deviceType\":\""+obj[3]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[4]+"\",");
			result_json.append("\"alarmTime\":\""+obj[5]+"\",");
			result_json.append("\"itemName\":\""+itemName+"\",");
			result_json.append("\"alarmType\":\""+alarmTypeStr+"\",");
			result_json.append("\"alarmTypeName\":\""+MemoryDataManagerTask.getCodeName("ALARMTYPE",obj[7]+"", language)+"\",");
			result_json.append("\"alarmValue\":\""+alarmValue+"\",");
			result_json.append("\"alarmInfo\":\""+alarmInfo+"\",");
			result_json.append("\"alarmLimit\":\""+obj[10]+"\",");
			result_json.append("\"hystersis\":\""+obj[11]+"\",");
			result_json.append("\"alarmLevel\":\""+obj[12]+"\",");
			result_json.append("\"alarmLevelName\":\""+MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[12]+"", language)+"\",");
			
			result_json.append("\"delay\":\""+obj[13]+"\",");
			result_json.append("\"retriggerTime\":\""+obj[14]+"\",");
			
			result_json.append("\"isSendMessage\":\""+obj[15]+"\",");
			result_json.append("\"isSendMail\":\""+obj[16]+"\",");
			result_json.append("\"recoveryTime\":\""+obj[17]+"\",");
			
			result_json.append("\"ackStatus\":\""+obj[18]+"\",");
			result_json.append("\"ackStatusName\":\""+obj[19]+"\",");
			result_json.append("\"ackUser\":\""+obj[20]+"\",");
			result_json.append("\"ackIP\":\""+obj[21]+"\",");
			result_json.append("\"ackTime\":\""+obj[22]+"\",");
			
			result_json.append("\"orgId\":\""+obj[23]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public boolean exportAlarmData(User user,HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,
			String deviceType,String dictDeviceType,
			String deviceId,String deviceName,String alarmType,String alarmLevel,String isSendMessage,Page pager,String language){
		try{
			
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			String tableName="viw_alarminfo_hist";
			
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			
			List<Object> headRow = new ArrayList<>();
			for(int i=0;i<heads.length;i++){
				headRow.add(heads[i]);
			}
		    List<List<Object>> sheetDataList = new ArrayList<>();
		    sheetDataList.add(headRow);
			
		    String sql="select t.id,t.deviceid,t.devicename,t.devicetype,t.deviceTypeName_"+language+",to_char(t.alarmtime,'yyyy-mm-dd hh24:mi:ss') as alarmtime,"
					+ " t.itemname,t.alarmtype,"
					+ " t.alarmvalue,t.alarminfo,t.alarmlimit,t.hystersis,"
					+ " t.alarmlevel,"
					+ " t.delay,t.retriggertime,"
					+ " decode(t.issendmessage,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmessage,"
					+ " decode(t.issendmail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as issendmail,"
					+ " t.recoverytime,"
					+ " t.ack_status,decode(t.ack_status,1,'"+languageResourceMap.get("confirmed")+"','"+languageResourceMap.get("unconfirmed")+"') as ackStatusName,"
					+ " decode(t.ack_status,1,t.ack_user,''),"
					+ " decode(t.ack_status,1,t.ack_ip,''),"
					+ " decode(t.ack_status,1,to_char(t.ack_time,'yyyy-mm-dd hh24:mi:ss'),'') as ackTime,"
					+ " t.orgid "
					+ " from "+tableName+" t where t.orgid in ("+orgId+") "
					+ " and t.alarmtime between to_date('"+pager.getStart_date()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+pager.getEnd_date()+"','yyyy-mm-dd hh24:mi:ss')";
			
			
			if(StringManagerUtils.isNotNull(deviceId)){
				sql+=" and t.deviceid="+deviceId;
			}
			if(StringManagerUtils.isNotNull(alarmType)){
				if(StringManagerUtils.stringToInteger(alarmType)==2){
					sql+=" and t.alarmType in(2,5,7)";
				}else {
					sql+=" and t.alarmType="+alarmType;
				}
			}
			if(StringManagerUtils.isNotNull(alarmLevel)){
				sql+=" and t.alarmLevel="+alarmLevel;
			}
			if(StringManagerUtils.isNotNull(isSendMessage)){
				sql+=" and t.isSendMessage="+isSendMessage;
			}
			sql+=" order by t.alarmtime desc";
			
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			
			List<?> list=this.findCallSql(finalSql);
			List<Object> record=null;
			JSONObject jsonObject=null;
			Object[] obj=null;
			StringBuffer result_json = null;
			for(int i=0;i<list.size();i++){
				obj=(Object[]) list.get(i);
				result_json = new StringBuffer();
				record = new ArrayList<>();
				
				String itemName=obj[6]+"";
				String alarmTypeStr=obj[7]+"";
				String alarmValue=obj[8]+"";
				String alarmInfo=obj[9]+"";
				
				if(StringManagerUtils.stringToInteger(alarmTypeStr)==0){
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("switchingOpenValue"), alarmInfo, true)){
						alarmInfo=languageResourceMap.get("switchingOpenValue");
					}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("switchingCloseValue"), alarmInfo, true)){
						alarmInfo=languageResourceMap.get("switchingCloseValue");
					}
				}else if(StringManagerUtils.stringToInteger(alarmTypeStr)==1){
					
				}else if(StringManagerUtils.stringToInteger(alarmTypeStr)==2){
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("highAlarm"), alarmInfo, true)){
						alarmInfo=languageResourceMap.get("highAlarm");
					}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("lowAlarm"), alarmInfo, true)){
						alarmInfo=languageResourceMap.get("lowAlarm");
					}
				}else if(StringManagerUtils.stringToInteger(alarmTypeStr)==3){
					itemName=languageResourceMap.get("commStatusAlarm");
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("goOnline"), alarmInfo, true)){
						alarmInfo=languageResourceMap.get("goOnline");
					}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("online"), alarmInfo, true)){
						alarmInfo=languageResourceMap.get("online");
					}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("offline"), alarmInfo, true)){
						alarmInfo=languageResourceMap.get("offline");
					}
				}else if(StringManagerUtils.stringToInteger(alarmTypeStr)==4){
					itemName=languageResourceMap.get("FESDiagramResultAlarm");
					alarmInfo =MemoryDataManagerTask.getWorkTypeName(alarmValue,language);
				}else if(StringManagerUtils.stringToInteger(alarmTypeStr)==5){
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("highAlarm"), alarmInfo, true)){
						alarmInfo=languageResourceMap.get("highAlarm");
					}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("lowAlarm"), alarmInfo, true)){
						alarmInfo=languageResourceMap.get("lowAlarm");
					}
					itemName=MemoryDataManagerTask.calItemLanguageSwitchover(itemName,language);
				}else if(StringManagerUtils.stringToInteger(alarmTypeStr)==6){
					itemName=languageResourceMap.get("runStatusAlarm");
					
					if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("run"), alarmInfo, true)){
						alarmInfo=languageResourceMap.get("run");
					}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("stop"), alarmInfo, true)){
						alarmInfo=languageResourceMap.get("stop");
					}else if(StringManagerUtils.existOrNot(MemoryDataManagerTask.getLanguageResourceValueList("emptyMsg"), alarmInfo, true)){
						alarmInfo=languageResourceMap.get("emptyMsg");
					}
					
				}
				
				result_json.append("{\"id\":\""+(i+1)+"\",");
				result_json.append("\"deviceId\":\""+obj[1]+"\",");
				result_json.append("\"deviceName\":\""+obj[2]+"\",");
				result_json.append("\"deviceType\":\""+obj[3]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[4]+"\",");
				result_json.append("\"alarmTime\":\""+obj[5]+"\",");
				result_json.append("\"itemName\":\""+itemName+"\",");
				result_json.append("\"alarmType\":\""+alarmTypeStr+"\",");
				result_json.append("\"alarmTypeName\":\""+MemoryDataManagerTask.getCodeName("ALARMTYPE",obj[7]+"", user.getLanguageName())+"\",");
				result_json.append("\"alarmValue\":\""+alarmValue+"\",");
				result_json.append("\"alarmInfo\":\""+alarmInfo+"\",");
				result_json.append("\"alarmLimit\":\""+obj[10]+"\",");
				result_json.append("\"hystersis\":\""+obj[11]+"\",");
				result_json.append("\"alarmLevel\":\""+obj[12]+"\",");
				result_json.append("\"alarmLevelName\":\""+MemoryDataManagerTask.getCodeName("ALARMLEVEL",obj[12]+"", user.getLanguageName())+"\",");
				result_json.append("\"delay\":\""+obj[13]+"\",");
				result_json.append("\"retriggerTime\":\""+obj[14]+"\",");
				result_json.append("\"isSendMessage\":\""+obj[15]+"\",");
				result_json.append("\"isSendMail\":\""+obj[16]+"\",");
				result_json.append("\"recoveryTime\":\""+obj[17]+"\",");
				result_json.append("\"ackStatus\":\""+obj[18]+"\",");
				
				result_json.append("\"ackStatusName\":\""+obj[19]+"\",");
				result_json.append("\"ackUser\":\""+obj[20]+"\",");
				result_json.append("\"ackIP\":\""+obj[21]+"\",");
				result_json.append("\"ackTime\":\""+obj[22]+"\",");
				
				result_json.append("\"orgId\":\""+obj[23]+"\"}");
				
				jsonObject = JSONObject.fromObject(result_json.toString().replaceAll("null", ""));
				for (int j = 0; j < columns.length; j++) {
					if(jsonObject.has(columns[j])){
						record.add(jsonObject.getString(columns[j]));
					}else{
						record.add("");
					}
				}
				sheetDataList.add(record);
			}
		
			ExcelUtils.export(response,fileName,title, sheetDataList,1);
			if(user!=null){
		    	try {
					saveSystemLog(user,4,languageResourceMap.get("exportFile")+":"+title);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String getAlarmOverviewData(String orgId,String deviceType,String statType,String deviceName,
			String alarmType,String alarmLevel,String alarmQueryStatRangeType,
			String isSendMessage,Page pager,String language) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String tableName="viw_alarminfo_latest";
		String statColumn="alarmType";
		if(StringManagerUtils.stringToInteger(statType)==1){
			statColumn="alarmLevel";
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String columns="["
				+ "{\"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",\"width\":50,\"children\":[]},"
				+ "{\"header\":\""+languageResourceMap.get("deviceName")+"\",\"dataIndex\":\"deviceName\",\"flex\":8,\"children\":[]},"
				+ "{\"header\":\""+languageResourceMap.get("alarmTime")+"\",\"dataIndex\":\"alarmTime\",\"flex\":12,\"children\":[]},"
				+ "{\"header\":\""+languageResourceMap.get("alarmLevel")+"\",\"dataIndex\":\"alarmLevelAgg\",\"flex\":20,\"children\":[]},"
				+ "{\"header\":\""+languageResourceMap.get("alarmType")+"\",\"dataIndex\":\"alarmTypeAgg\",\"flex\":20,\"children\":[]}"
//				+ "{ \"header\":\""+languageResourceMap.get("deviceType")+"\",\"dataIndex\":\"deviceTypeName\",\"flex\":6,\"children\":[] }"
				+ "]";
		

//		result_json.append("\"alarmTypeAgg\":\""+alarmTypeAgg+"\",");
//		result_json.append("\"alarmLevelAgg\":\""+alarmLevelAgg+"\",");
		
		String sql="select t.deviceid,t."+statColumn+",max(t.id) as id from VIW_ALARMINFO_LATEST t  "
				+ " where t.orgid in ("+orgId+")"
				+ " and t.devicetype in ("+deviceType+")";
		
		String alarmLevelStatSql="select t.deviceid,t.alarmLevel,max(t.id) as id from VIW_ALARMINFO_LATEST t  "
				+ " where t.orgid in ("+orgId+")"
				+ " and t.devicetype in ("+deviceType+")";
		String alarmTypelStatSql="select t.deviceid,t.alarmType,max(t.id) as id from VIW_ALARMINFO_LATEST t  "
				+ " where t.orgid in ("+orgId+")"
				+ " and t.devicetype in ("+deviceType+")";
		
		if("0".equalsIgnoreCase(alarmQueryStatRangeType)){
			String date=StringManagerUtils.getCurrentTime();
			sql+=" and t.alarmtime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
			alarmLevelStatSql+=" and t.alarmtime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
			alarmTypelStatSql+=" and t.alarmtime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
		}
		sql+= " group by t.deviceid,t."+statColumn+"";
		alarmLevelStatSql+= " group by t.deviceid,t.alarmLevel";
		alarmTypelStatSql+= " group by t.deviceid,t.alarmType";
		
		if(StringManagerUtils.isNotNull(alarmType)){
			sql=alarmTypelStatSql;
		}
		
		List<?> alarmTypeLatestStatusList = this.findCallSql(alarmTypelStatSql);
		List<?> alarmLevelLatestStatusList = this.findCallSql(alarmLevelStatSql);
		
		String alarmCountSql="select count(1) "
				+ " from "+tableName+" t2,("+alarmTypelStatSql+") v "
				+ " where t2.id=v.id";
		
		sql="select t2.deviceid as deviceid,t2.devicename,t2.devicetypename_zh_CN,to_char(max(t2.alarmtime ) ,'yyyy-mm-dd hh24:mi:ss') as alarmtime"
				+ " from "+tableName+" t2,("+sql+") v "
				+ " where t2.id=v.id";
		
		
		if(StringManagerUtils.isNotNull(alarmType)){
			if(StringManagerUtils.stringToInteger(alarmType)==2){
				sql+=" and t2.alarmType in(2,5,7)";
				alarmCountSql+=" and t2.alarmType in(2,5,7)";
			}else {
				sql+= " and t2.alarmtype="+alarmType;
				alarmCountSql+= " and t2.alarmtype="+alarmType;
			}
		}
		
		
		if(StringManagerUtils.isNotNull(alarmLevel)){
			sql+=" and t2.alarmLevel="+alarmLevel+"";
			alarmCountSql+=" and t2.alarmLevel="+alarmLevel+"";
		}
		if(StringManagerUtils.isNotNull(isSendMessage)){
			sql+=" and t2.isSendMessage="+isSendMessage+"";
			alarmCountSql+=" and t2.isSendMessage="+isSendMessage+"";
		}
		
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and t2.deviceName='"+deviceName+"'";
			alarmCountSql+=" and t2.deviceName='"+deviceName+"'";
		}
		sql+=" group by t2.deviceid,t2.devicename,t2.devicetypename_zh_CN";
		sql+=" ORDER BY max(t2.alarmtime) DESC";
		int maxvalue=pager.getLimit()+pager.getStart();
		String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		int alarmCount=this.getTotalCountRows(alarmCountSql);
		List<?> list = this.findCallSql(finalSql);
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"alarmCount\":"+alarmCount+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String alarmTypeAgg="";
			String alarmLevelAgg="";
			List<Integer> deviceAlarmTypeList=new ArrayList<>();
			List<Integer> deviceAlarmLevelList=new ArrayList<>();
			for(int j=0;j<alarmTypeLatestStatusList.size();j++){
				Object[] alarmTypeLatestStatusObj=(Object[]) alarmTypeLatestStatusList.get(j);
				if(StringManagerUtils.stringToInteger(obj[0]+"") == StringManagerUtils.stringToInteger(alarmTypeLatestStatusObj[0]+"")){
					deviceAlarmTypeList.add(StringManagerUtils.stringToInteger(alarmTypeLatestStatusObj[1]+""));
				}
			}
			for(int j=0;j<alarmLevelLatestStatusList.size();j++){
				Object[] alarmLevelLatestStatusObj=(Object[]) alarmLevelLatestStatusList.get(j);
				if(StringManagerUtils.stringToInteger(obj[0]+"") == StringManagerUtils.stringToInteger(alarmLevelLatestStatusObj[0]+"")){
					deviceAlarmLevelList.add(StringManagerUtils.stringToInteger(alarmLevelLatestStatusObj[1]+""));
				}
			}
			
			if(StringManagerUtils.existOrNot(deviceAlarmTypeList, 4)){
				alarmTypeAgg+=MemoryDataManagerTask.getCodeName("ALARMTYPE","4",language)+";";
			}
			if(StringManagerUtils.existOrNot(deviceAlarmTypeList, 3)){
				alarmTypeAgg+=MemoryDataManagerTask.getCodeName("ALARMTYPE","3",language)+";";
			}
			if(StringManagerUtils.existOrNot(deviceAlarmTypeList, 6)){
				alarmTypeAgg+=MemoryDataManagerTask.getCodeName("ALARMTYPE","6",language)+";";
			}
			if(StringManagerUtils.existOrNot(deviceAlarmTypeList, 2)){
				alarmTypeAgg+=MemoryDataManagerTask.getCodeName("ALARMTYPE","2",language)+";";
			}
			if(StringManagerUtils.existOrNot(deviceAlarmTypeList, 1)){
				alarmTypeAgg+=MemoryDataManagerTask.getCodeName("ALARMTYPE","1",language)+";";
			}
			if(StringManagerUtils.existOrNot(deviceAlarmTypeList, 0)){
				alarmTypeAgg+=MemoryDataManagerTask.getCodeName("ALARMTYPE","0",language)+";";
			}
			
			if(StringManagerUtils.existOrNot(deviceAlarmLevelList, 100)){
				alarmLevelAgg+=MemoryDataManagerTask.getCodeName("ALARMLEVEL","100",language)+";";
			}
			if(StringManagerUtils.existOrNot(deviceAlarmLevelList, 200)){
				alarmLevelAgg+=MemoryDataManagerTask.getCodeName("ALARMLEVEL","200",language)+";";
			}
			if(StringManagerUtils.existOrNot(deviceAlarmLevelList, 300)){
				alarmLevelAgg+=MemoryDataManagerTask.getCodeName("ALARMLEVEL","300",language)+";";
			}
			
			if(alarmTypeAgg.endsWith(";")){
				alarmTypeAgg=alarmTypeAgg.substring(0, alarmTypeAgg.length()-1);
			}
			if(alarmLevelAgg.endsWith(";")){
				alarmLevelAgg=alarmLevelAgg.substring(0, alarmLevelAgg.length()-1);
			}
			
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"deviceName\":\""+obj[1]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[2]+"\",");
			result_json.append("\"alarmTypeAgg\":\""+alarmTypeAgg+"\",");
			result_json.append("\"alarmLevelAgg\":\""+alarmLevelAgg+"\",");
			result_json.append("\"alarmTime\":\""+obj[3]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public boolean exportAlarmOverviewData(User user,HttpServletResponse response,String fileName,String title,String head,String field,
			String orgId,String deviceType,String statType,
			String deviceName,String alarmType,String alarmLevel,String alarmQueryStatRangeType,
			String isSendMessage,Page pager,String language){
		try{
			int maxvalue=Config.getInstance().configFile.getAp().getOthers().getExportLimit();
			String tableName="viw_alarminfo_latest";
			String statColumn="alarmType";
			if(StringManagerUtils.stringToInteger(statType)==1){
				statColumn="alarmLevel";
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
			
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			
			List<Object> headRow = new ArrayList<>();
			for(int i=0;i<heads.length;i++){
				headRow.add(heads[i]);
			}
		    List<List<Object>> sheetDataList = new ArrayList<>();
		    sheetDataList.add(headRow);
		    
		    String sql="select t.deviceid,t."+statColumn+",max(t.id) as id from VIW_ALARMINFO_LATEST t  "
					+ " where t.orgid in ("+orgId+")"
					+ " and t.devicetype in ("+deviceType+")";
			String alarmLevelStatSql="select t.deviceid,t.alarmLevel,max(t.id) as id from VIW_ALARMINFO_LATEST t  "
					+ " where t.orgid in ("+orgId+")"
					+ " and t.devicetype in ("+deviceType+")";
			String alarmTypelStatSql="select t.deviceid,t.alarmType,max(t.id) as id from VIW_ALARMINFO_LATEST t  "
					+ " where t.orgid in ("+orgId+")"
					+ " and t.devicetype in ("+deviceType+")";
			if("0".equalsIgnoreCase(alarmQueryStatRangeType)){
				String date=StringManagerUtils.getCurrentTime();
				sql+=" and t.alarmtime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
				alarmLevelStatSql+=" and t.alarmtime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
				alarmTypelStatSql+=" and t.alarmtime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1";
			}
			sql+= " group by t.deviceid,t."+statColumn+"";
			alarmLevelStatSql+= " group by t.deviceid,t.alarmLevel";
			alarmTypelStatSql+= " group by t.deviceid,t.alarmType";
			
			if(StringManagerUtils.isNotNull(alarmType)){
				sql=alarmTypelStatSql;
			}
			
			List<?> alarmTypeLatestStatusList = this.findCallSql(alarmTypelStatSql);
			List<?> alarmLevelLatestStatusList = this.findCallSql(alarmLevelStatSql);
			
			
			sql="select t2.deviceid as deviceid,t2.devicename,t2.devicetypename_zh_CN,to_char(max(t2.alarmtime ) ,'yyyy-mm-dd hh24:mi:ss') as alarmtime"
					+ " from "+tableName+" t2,("+sql+") v "
					+ " where t2.id=v.id";
			if(StringManagerUtils.isNotNull(alarmType)){
				if(StringManagerUtils.stringToInteger(alarmType)==2){
					sql+=" and t2.alarmType in(2,5,7)";
				}else {
					sql+= " and t2.alarmtype="+alarmType;
				}
			}
			if(StringManagerUtils.isNotNull(alarmLevel)){
				sql+=" and t2.alarmLevel="+alarmLevel+"";
			}
			if(StringManagerUtils.isNotNull(isSendMessage)){
				sql+=" and t2.isSendMessage="+isSendMessage+"";
			}
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and t2.deviceName='"+deviceName+"'";
			}
			sql+=" group by t2.deviceid,t2.devicename,t2.devicetypename_zh_CN";
			sql+=" ORDER BY max(t2.alarmtime) DESC";
		    
			
			String finalSql="select a.* from ("+sql+" ) a where  rownum <="+maxvalue;
			List<?> list=this.findCallSql(finalSql);
			List<Object> record=null;
			JSONObject jsonObject=null;
			StringBuffer result_json = null;
			Object[] obj=null;
			for(int i=0;i<list.size();i++){
				obj=(Object[]) list.get(i);
				result_json = new StringBuffer();
				record = new ArrayList<>();
				
				String alarmTypeAgg="";
				String alarmLevelAgg="";
				List<Integer> deviceAlarmTypeList=new ArrayList<>();
				List<Integer> deviceAlarmLevelList=new ArrayList<>();
				for(int j=0;j<alarmTypeLatestStatusList.size();j++){
					Object[] alarmTypeLatestStatusObj=(Object[]) alarmTypeLatestStatusList.get(j);
					if(StringManagerUtils.stringToInteger(obj[0]+"") == StringManagerUtils.stringToInteger(alarmTypeLatestStatusObj[0]+"")){
						deviceAlarmTypeList.add(StringManagerUtils.stringToInteger(alarmTypeLatestStatusObj[1]+""));
					}
				}
				for(int j=0;j<alarmLevelLatestStatusList.size();j++){
					Object[] alarmLevelLatestStatusObj=(Object[]) alarmLevelLatestStatusList.get(j);
					if(StringManagerUtils.stringToInteger(obj[0]+"") == StringManagerUtils.stringToInteger(alarmLevelLatestStatusObj[0]+"")){
						deviceAlarmLevelList.add(StringManagerUtils.stringToInteger(alarmLevelLatestStatusObj[1]+""));
					}
				}
				
				if(StringManagerUtils.existOrNot(deviceAlarmTypeList, 4)){
					alarmTypeAgg+=MemoryDataManagerTask.getCodeName("ALARMTYPE","4",language)+";";
				}
				if(StringManagerUtils.existOrNot(deviceAlarmTypeList, 3)){
					alarmTypeAgg+=MemoryDataManagerTask.getCodeName("ALARMTYPE","3",language)+";";
				}
				if(StringManagerUtils.existOrNot(deviceAlarmTypeList, 6)){
					alarmTypeAgg+=MemoryDataManagerTask.getCodeName("ALARMTYPE","6",language)+";";
				}
				if(StringManagerUtils.existOrNot(deviceAlarmTypeList, 2)){
					alarmTypeAgg+=MemoryDataManagerTask.getCodeName("ALARMTYPE","2",language)+";";
				}
				if(StringManagerUtils.existOrNot(deviceAlarmTypeList, 1)){
					alarmTypeAgg+=MemoryDataManagerTask.getCodeName("ALARMTYPE","1",language)+";";
				}
				if(StringManagerUtils.existOrNot(deviceAlarmTypeList, 0)){
					alarmTypeAgg+=MemoryDataManagerTask.getCodeName("ALARMTYPE","0",language)+";";
				}
				
				if(StringManagerUtils.existOrNot(deviceAlarmLevelList, 100)){
					alarmLevelAgg+=MemoryDataManagerTask.getCodeName("ALARMLEVEL","100",language)+";";
				}
				if(StringManagerUtils.existOrNot(deviceAlarmLevelList, 200)){
					alarmLevelAgg+=MemoryDataManagerTask.getCodeName("ALARMLEVEL","200",language)+";";
				}
				if(StringManagerUtils.existOrNot(deviceAlarmLevelList, 300)){
					alarmLevelAgg+=MemoryDataManagerTask.getCodeName("ALARMLEVEL","300",language)+";";
				}
				
				if(alarmTypeAgg.endsWith(";")){
					alarmTypeAgg=alarmTypeAgg.substring(0, alarmTypeAgg.length()-1);
				}
				if(alarmLevelAgg.endsWith(";")){
					alarmLevelAgg=alarmLevelAgg.substring(0, alarmLevelAgg.length()-1);
				}
				
				result_json.append("{\"id\":"+(i+1)+",");
				result_json.append("\"deviceName\":\""+obj[1]+"\",");
				result_json.append("\"deviceTypeName\":\""+obj[2]+"\",");
				result_json.append("\"alarmTypeAgg\":\""+alarmTypeAgg+"\",");
				result_json.append("\"alarmLevelAgg\":\""+alarmLevelAgg+"\",");
				result_json.append("\"alarmTime\":\""+obj[3]+"\"}");
				
				try{
					jsonObject = JSONObject.fromObject(result_json.toString().replaceAll("null", ""));
					for (int j = 0; j < columns.length; j++) {
						if(jsonObject.has(columns[j])){
							record.add(jsonObject.getString(columns[j]));
						}else{
							record.add("");
						}
					}
					sheetDataList.add(record);
				}catch(Exception e){
					e.printStackTrace();
					System.out.println(result_json.toString().replaceAll("null", ""));
				}
				
			}
			ExcelUtils.export(response,fileName,title, sheetDataList,1);
			if(user!=null){
		    	try {
					saveSystemLog(user,4,languageResourceMap.get("exportFile")+":"+title);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// ============================================================
	// 批量确认报警（同时批量确认同测点的历史积压记录）
	// ============================================================
	public Map<String, Object> batchAckAlarm(List<Number> alertIds, String ackUser, String ackIp) {
	    Map<String, Object> result = new HashMap<String, Object>();
	    List<Long> successIds = new ArrayList<Long>();
	    List<Long> alreadyAckedIds = new ArrayList<Long>();
	    List<Long> failedIds = new ArrayList<Long>();
	    Set<Integer> deviceIdSet = new HashSet<Integer>();

	    if (alertIds == null || alertIds.isEmpty()) {
	        result.put("successIds", successIds);
	        result.put("alreadyAckedIds", alreadyAckedIds);
	        result.put("failedIds", failedIds);
	        result.put("deviceIds", new ArrayList<Integer>());
	        return result;
	    }

	    String ackTime = StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
	    String safeUser = ackUser == null ? "" : ackUser.replace("'", "''");
	    String safeIp = ackIp == null ? "" : ackIp.replace("'", "''");

	    for (Number n : alertIds) {
	        if (n == null) continue;
	        long id = n.longValue();
	        try {
	            // 查出该条报警的详细信息（增加 alarmtype 和 alarmvalue）
	            String querySql = "select t.deviceid, t.itemcode, t.bitindex, nvl(t.ack_status,0), t.alarminfo, t.alarmlevel, t.alarmtype, t.alarmvalue "
	                    + " from tbl_alarminfo_hist t where t.id = " + id;
	            List<?> ql = findCallSql(querySql);
	            if (ql == null || ql.isEmpty()) {
	                failedIds.add(id);
	                continue;
	            }
	            Object[] row = (Object[]) ql.get(0);
	            int deviceId = StringManagerUtils.stringToInteger(row[0] + "");
	            String itemCode = row[1] == null ? "" : row[1].toString().replace("'", "''");
	            String bitIndex = row[2] == null ? "" : row[2].toString();
	            int ackStatus = StringManagerUtils.stringToInteger(row[3] + "");
	            String alarmInfo = row[4] == null ? "" : row[4].toString().replace("'", "''");
	            int alarmLevel = StringManagerUtils.stringToInteger(row[5] + "");
	            int alarmType = StringManagerUtils.stringToInteger(row[6] + "");
	            String alarmValue = row[7] == null ? "" : row[7].toString().replace("'", "''");

	            if (ackStatus == 1) {
	                alreadyAckedIds.add(id);
	                deviceIdSet.add(deviceId);
	                continue;
	            }

	            // ★ 根据报警类型区分确认条件
	            String whereCondition = " where t.deviceid = " + deviceId
	                    + "   and t.itemcode = '" + itemCode + "'"
	                    + "   and t.alarmlevel = " + alarmLevel
	                    + "   and (t.ack_status is null or t.ack_status = 0)";

	            // 如果是 2 或 5（数据量报警），按 alarminfo (高报/低报) 区分
	            if (alarmType == 2 || alarmType == 5) {
	                whereCondition += " and t.alarmtype = " + alarmType;
	                whereCondition += " and t.alarminfo = '" + alarmInfo + "'";
	            } else {
	                // 其他类型（通信、运行、工况、开关量等），按 alarmvalue 区分
	                whereCondition += " and t.alarminfo = '" + alarmInfo + "'"; 
	                whereCondition += " and t.alarmvalue = " + (StringManagerUtils.isNum(alarmValue) ? alarmValue : "'" + alarmValue + "'");
	            }

	            if (StringManagerUtils.isNotNull(bitIndex)) {
	                whereCondition += " and t.bitindex = " + bitIndex;
	            } else {
	                whereCondition += " and t.bitindex is null";
	            }

	            // 更新历史表
	            String updateHist = "update tbl_alarminfo_hist t "
	                    + " set t.ack_status = 1, "
	                    + "     t.ack_user   = '" + safeUser + "', "
	                    + "     t.ack_ip     = '" + safeIp + "', "
	                    + "     t.ack_time   = to_date('" + ackTime + "','yyyy-mm-dd hh24:mi:ss') "
	                    + whereCondition;
	            
	            int n1 = getBaseDao().updateOrDeleteBySql(updateHist);

	            if (n1 > 0) {
	                successIds.add(id);
	                deviceIdSet.add(deviceId);

	                // 同步更新实时表 tbl_alarminfo_latest
	                String updateLatest = "update tbl_alarminfo_latest t "
	                        + " set t.ack_status = 1, "
	                        + "     t.ack_user   = '" + safeUser + "', "
	                        + "     t.ack_ip     = '" + safeIp + "', "
	                        + "     t.ack_time   = to_date('" + ackTime + "','yyyy-mm-dd hh24:mi:ss') "
	                        + whereCondition;
	                getBaseDao().updateOrDeleteBySql(updateLatest);

	                // 同步内存（这里要考虑 Key 的生成逻辑，确保内存里的也置为确认）
	                try { 
	                    syncMemoryAck(deviceId, itemCode, bitIndex, alarmType, alarmInfo, alarmValue, safeUser, safeIp, ackTime); 
	                } catch (Exception e) { 
	                    e.printStackTrace(); 
	                }
	            } else {
	                alreadyAckedIds.add(id);
	                deviceIdSet.add(deviceId);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            failedIds.add(id);
	        }
	    }

	    result.put("successIds", successIds);
	    result.put("alreadyAckedIds", alreadyAckedIds);
	    result.put("failedIds", failedIds);
	    result.put("deviceIds", new ArrayList<Integer>(deviceIdSet));
	    return result;
	}

	/** 同步内存：alarmInfo.alertId=null, triggerAlarm=false（避免下一轮采集被当成新报警） */
	/**
	 * 同步内存：将内存中匹配的报警状态置为已确认
	 */
	private void syncMemoryAck(int deviceId, String column, String bitIndex, int alarmType, String alarmInfo, String alarmValue, String ackUser, String ackIp, String ackTime) {
	    Map<Integer, DeviceAlarmInfo> deviceAlarmInfoMap = DeviceAlarmInfoMap.getDeviceAlarmInfoMap();
	    if (deviceAlarmInfoMap == null) return;
	    DeviceAlarmInfo dai = deviceAlarmInfoMap.get(deviceId);
	    if (dai == null || dai.getAlarmInfoMap() == null) return;

	    // ★ 因为内存 Map 的 Key 是 deviceId_column_bitIndex，当存在上线/离线时，可能会有多个 AlarmInfo 对象
	    // 我们需要遍历，找到业务上匹配的那一条
	    for (Map.Entry<String, DeviceAlarmInfo.AlarmInfo> entry : dai.getAlarmInfoMap().entrySet()) {
	        String key = entry.getKey();
	        DeviceAlarmInfo.AlarmInfo ai = entry.getValue();
	        
	        // 1. 匹配基础项 (deviceId 和 column)
	        if (!key.startsWith(deviceId + "_" + column)) continue;
	        
	        // 2. 匹配 bitIndex
	        String currentBitIndex = ai.getBitIndex();
	        if (StringManagerUtils.isNotNull(bitIndex)) {
	            if (!bitIndex.equals(currentBitIndex)) continue;
	        } else {
	            if (StringManagerUtils.isNotNull(currentBitIndex)) continue;
	        }

	        // 3. 根据报警类型匹配具体的业务特征
	        boolean isMatch = false;
	        if (alarmType == 2 || alarmType == 5) {
	            // 类型 2、5：匹配报警信息（高报/低报）
	            String currentInfo = ai.getAlarmInfo(); // 假设 DeviceAlarmInfo.AlarmInfo 中有 getAlarmInfo()
	            if (alarmInfo == null) alarmInfo = "";
	            if (currentInfo == null) currentInfo = "";
	            if (currentInfo.equalsIgnoreCase(alarmInfo)) {
	                isMatch = true;
	            }
	        } else {
	            // 其他类型：匹配报警值（上线/离线，运行/停止）
	            String currentValue = ai.getRawValue(); // 或 getValue()，看你的内存对象存的是 rawValue 还是 value
	            if (alarmValue == null) alarmValue = "";
	            if (currentValue == null) currentValue = "";
	            
	            // 注意：内存中可能存的是 value("上线")，而库里是 rawValue("2")，需要兼容
	            if (currentValue.equalsIgnoreCase(alarmValue) || 
	                (ai.getValue() != null && ai.getValue().equalsIgnoreCase(alarmValue))) {
	                isMatch = true;
	            }
	        }

	        // 如果匹配成功，进行内存状态更新
	        if (isMatch) {
	            ai.setAlertId(null); 
	            ai.setTriggerAlarm(false);
	            ai.setAckUser(ackUser);
	            ai.setAckIp(ackIp);
	            ai.setAckTime(ackTime);
	            break; // 找到对应项并确认后，跳出循环
	        }
	    }
	}

	// ============================================================
	// 查询未确认报警（前端初次进入拉取）
	// ============================================================
	public List<Map<String, Object>> getPendingAlarms(String orgIds, String deviceType, int limit, String language) {
	    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	    StringBuilder sql = new StringBuilder();
	    Map<String,String> languageResourceMap = MemoryDataManagerTask.getLanguageResource(language);
	    
	    // 拉取所有未恢复的报警（包括已确认的）
	    sql.append("select * from ( ")
	       .append("  select a.*, rownum rn2 from ( ")
	       .append("    select t.id, t.deviceid, d.devicename, d.orgid, d.devicetype, ")
	       .append("           t.itemcode, t.itemname, ")
	       .append("           to_char(t.alarmvalue) as alarmvalue, ")
	       .append("           t.alarminfo, ")        // ★ 新增：用于分组和翻译
	       .append("           t.alarmtype, ")         // ★ 新增：用于判断报警类型
	       .append("           t.alarmlevel, ")
	       .append("           to_char(t.alarmtime,'yyyy-mm-dd hh24:mi:ss') as acqtime, ")
	       .append("           nvl(t.ack_status, 0) as ack_status, ")
	       .append("           row_number() over ( ")
	       // ★ 核心修改：动态分组逻辑
	       .append("               partition by t.deviceid, t.itemcode, nvl(t.bitindex,-1), ")
	       .append("               CASE ")
	       // 如果是数据量报警(2)或数值报警(5)，按报警信息分组
	       .append("                   WHEN t.alarmtype in (2, 5) THEN t.alarminfo ")
	       // 其他类型（通信、工况、开关量等），按报警值分组
	       .append("                   ELSE to_char(t.alarmvalue) ")
	       .append("               END ")
	       .append("               order by t.alarmtime desc ")
	       .append("           ) as rn ")
	       .append("      from tbl_alarminfo_hist t, tbl_device d ")
	       .append("     where t.deviceid = d.id ")
	       .append("       and t.recoverytime is null ")
	       .append("       and t.alarmlevel >= 0 "); // ★ 修改：>= 0 保证上线也能查出来

	    if (StringManagerUtils.isNotNull(orgIds)) {
	        sql.append(" and d.orgid in (").append(orgIds).append(") ");
	    }
	    if (StringManagerUtils.isNotNull(deviceType)) {
	        sql.append(" and d.devicetype in (").append(deviceType).append(") ");
	    }

	    sql.append("  ) a where a.rn = 1 ")
	       .append("    order by a.alarmlevel asc, a.acqtime desc ")
	       .append(") where rownum <= ").append(limit);

	    List<?> list = findCallSql(sql.toString());
	    if (list != null) {
	        for (Object o : list) {
	            Object[] row = (Object[]) o;
	            
	            // ★ 注意：因为上面 select 加了两列(alarminfo, alarmtype)，下面取值的下标整体后移了 2 位！
	            String column = row[5] == null ? "" : row[5].toString();       // itemcode
	            String columnName = row[6] == null ? "" : row[6].toString();   // itemname
	            String value = row[7] == null ? "" : row[7].toString();        // alarmvalue
	            // row[8] = alarminfo
	            // row[9] = alarmtype
	            // row[10] = alarmlevel
	            // row[11] = acqtime
	            // row[12] = ack_status
	            
	            int ackStatus = StringManagerUtils.stringToInteger(row[12] + ""); // ★ 索引变为 12
	            
	            // 多语言及枚举值翻译（维持原有逻辑）
	            if ("resultCode".equalsIgnoreCase(column) || "resultName".equalsIgnoreCase(column)) {
	                WorkType workType = MemoryDataManagerTask.getWorkTypeByCode(value, language);
	                if (workType != null) value = workType.getResultName();
	                columnName = languageResourceMap.get("FSDiagramWorkType");
	            } else if ("runStatus".equalsIgnoreCase(column) || "RunStatusName".equalsIgnoreCase(column)) {
	                if ("1".equalsIgnoreCase(value)) value = languageResourceMap.get("run");
	                else if ("0".equalsIgnoreCase(value)) value = languageResourceMap.get("stop");
	                else value = languageResourceMap.get("emptyMsg");
	                columnName = languageResourceMap.get("runStatus");
	            } else if ("commStatus".equalsIgnoreCase(column) || "CommStatusName".equalsIgnoreCase(column)) {
	                if ("1".equalsIgnoreCase(value)) value = languageResourceMap.get("online");
	                else if ("2".equalsIgnoreCase(value)) value = languageResourceMap.get("goOnline");
	                else value = languageResourceMap.get("offline");
	                columnName = languageResourceMap.get("commStatus");
	            }
	            
	            Map<String, Object> item = new HashMap<String, Object>();
	            item.put("alertId",    StringManagerUtils.stringToLong(row[0] + ""));
	            item.put("deviceId",   StringManagerUtils.stringToInteger(row[1] + ""));
	            item.put("deviceName", row[2] == null ? "" : row[2].toString());
	            item.put("orgId",      StringManagerUtils.stringToInteger(row[3] + ""));
	            item.put("deviceType", StringManagerUtils.stringToInteger(row[4] + ""));
	            item.put("column",     column);
	            item.put("columnName", columnName);
	            item.put("value",      value);
	            item.put("alarmInfo", row[8] == null ? "" : row[8].toString());
	            item.put("alarmType", StringManagerUtils.stringToInteger(row[9] + "")); 
	            item.put("alarmLevel", StringManagerUtils.stringToInteger(row[10] + "")); 
	            item.put("acqTime",    row[11] == null ? "" : row[11].toString());
	            item.put("ackStatus",  ackStatus);
	            
	            result.add(item);
	        }
	    }
	    return result;
	}
}
