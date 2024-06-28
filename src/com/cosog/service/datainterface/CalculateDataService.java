package com.cosog.service.datainterface;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.ReportTemplate;
import com.cosog.model.ReportUnitItem;
import com.cosog.model.calculate.CommResponseData;
import com.cosog.model.calculate.EnergyCalculateResponseData;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.RPCProductionData;
import com.cosog.model.calculate.TimeEffResponseData;
import com.cosog.model.calculate.TotalAnalysisRequestData;
import com.cosog.model.calculate.TotalAnalysisResponseData;
import com.cosog.model.drive.AcquisitionGroupResolutionData;
import com.cosog.model.drive.AcquisitionItemInfo;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.thread.calculate.TimingTotalCalculateThread;
import com.cosog.utils.AlarmInfoMap;
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

@SuppressWarnings("deprecation")
@Service("calculateDataService")
public class CalculateDataService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService commonDataService;
	
	public void saveAlarmInfo(String deviceName,String deviceType,String acqTime,List<AcquisitionItemInfo> acquisitionItemInfoList) throws SQLException{
		getBaseDao().saveAlarmInfo(deviceName,deviceType,acqTime,acquisitionItemInfoList);
	}
	
	public void saveAndSendAlarmInfo(int deviceId,String deviceName,String deviceType,String acqTime,List<AcquisitionItemInfo> acquisitionItemInfoList) throws SQLException{
		boolean isSendSMS=false;
		boolean isSendMail=false;
		StringBuffer SMSContent = new StringBuffer();
		StringBuffer EMailContent = new StringBuffer();
		SMSContent.append(((StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200)?"抽油机井":"螺杆泵井")+"设备"+deviceName+"于"+acqTime+"发生报警:");
		Map<String, String> alarmInfoMap=AlarmInfoMap.getMapObject();
		List<AcquisitionItemInfo> saveAcquisitionItemInfoList=new ArrayList<AcquisitionItemInfo>();
		for(int i=0;i<acquisitionItemInfoList.size();i++){
			if(acquisitionItemInfoList.get(i).getAlarmLevel()>0){
				String alarmLevelName="";
				if(acquisitionItemInfoList.get(i).getAlarmLevel()==100){
					alarmLevelName="一级报警";
				}else if(acquisitionItemInfoList.get(i).getAlarmLevel()==200){
					alarmLevelName="二级报警";
				}else if(acquisitionItemInfoList.get(i).getAlarmLevel()==300){
					alarmLevelName="三级报警";
				}
				String key=deviceId+","+deviceType+","+acquisitionItemInfoList.get(i).getColumn()+","+acquisitionItemInfoList.get(i).getAlarmInfo();
				String lastAlarmTime=alarmInfoMap.get(key);
				
				long timeDiff=StringManagerUtils.getTimeDifference(lastAlarmTime, acqTime, "yyyy-MM-dd HH:mm:ss");
				if(timeDiff>acquisitionItemInfoList.get(i).getAlarmDelay()*1000){
					alarmInfoMap.put(key, acqTime);
					saveAcquisitionItemInfoList.add(acquisitionItemInfoList.get(i));
					if(acquisitionItemInfoList.get(i).getIsSendMessage()==1){//如果该报警项发送短信
						isSendSMS=true;
						if(acquisitionItemInfoList.get(i).getAlarmType()==3){//开关量报警
							SMSContent.append(acquisitionItemInfoList.get(i).getAlarmInfo()+",报警级别:"+alarmLevelName);
						}else if(acquisitionItemInfoList.get(i).getAlarmType()==2){//枚举量报警
							SMSContent.append(acquisitionItemInfoList.get(i).getAlarmInfo()+",报警级别:"+alarmLevelName);
						}else if(acquisitionItemInfoList.get(i).getAlarmType()==1){//数值量报警
							SMSContent.append(acquisitionItemInfoList.get(i).getTitle()+acquisitionItemInfoList.get(i).getAlarmInfo()
									+",报警值"+acquisitionItemInfoList.get(i).getValue()
									+",限值"+acquisitionItemInfoList.get(i).getAlarmLimit()
									+",回差"+acquisitionItemInfoList.get(i).getHystersis()
									+",报警级别:"+alarmLevelName
									+";");
						}else{
							SMSContent.append(acquisitionItemInfoList.get(i).getAlarmInfo()+",报警级别:"+alarmLevelName);
						}
					}
					if(acquisitionItemInfoList.get(i).getIsSendMail()==1){//如果该报警项发送邮件
						isSendMail=true;
						if(acquisitionItemInfoList.get(i).getAlarmType()==3){//开关量报警
							EMailContent.append(acquisitionItemInfoList.get(i).getAlarmInfo()+",报警级别:"+alarmLevelName);
						}else if(acquisitionItemInfoList.get(i).getAlarmType()==2){//枚举量报警
							EMailContent.append(acquisitionItemInfoList.get(i).getAlarmInfo()+",报警级别:"+alarmLevelName);
						}else if(acquisitionItemInfoList.get(i).getAlarmType()==1){//数值量报警
							EMailContent.append(acquisitionItemInfoList.get(i).getTitle()+acquisitionItemInfoList.get(i).getAlarmInfo()
									+",报警值"+acquisitionItemInfoList.get(i).getValue()
									+",限值"+acquisitionItemInfoList.get(i).getAlarmLimit()
									+",回差"+acquisitionItemInfoList.get(i).getHystersis()
									+",报警级别:"+alarmLevelName
									+";");
						}else{
							EMailContent.append(acquisitionItemInfoList.get(i).getAlarmInfo()+",报警级别:"+alarmLevelName);
						}
					}
				}
			}else{
				try{
					String keyIndex=deviceName+","+deviceType+","+acquisitionItemInfoList.get(i).getTitle();
					boolean reset=false;
					Iterator<String> it = alarmInfoMap.keySet().iterator();
					while(it.hasNext()){
						if(it.next().indexOf(keyIndex)>=0){
							 reset=true;
							 it.remove();
							 break;
						 }
					}
					if(reset){
						 
					 }
				}catch(Exception e){
//					e.printStackTrace();
				}
			}
		}
		if(saveAcquisitionItemInfoList.size()>0){
			getBaseDao().saveAlarmInfo(deviceName,deviceType,acqTime,saveAcquisitionItemInfoList);
		}
		if(isSendSMS || isSendMail){
			sendAlarmSMS(deviceName,deviceType,isSendSMS,isSendMail,SMSContent.toString(),EMailContent.toString());
		}
	}
	
	public void sendAlarmSMS(String deviceName,String deviceType,boolean isSendSMS,boolean isSendMail,String SMSContent,String EMailContent) throws SQLException{
		String SMSUrl=Config.getInstance().configFile.getAd().getRw().getWriteSMS();
		String deviceTableName="tbl_device";
		
		String userSql="select u.user_id,u.user_phone,u.user_receivesms,u.user_in_email,u.user_receivemail "
				+ " from tbl_user u,tbl_role r "
				+ " where u.user_type=r.role_id and (u.user_orgid in (select org_id from tbl_org t start with org_id=( select t2.orgid from "+deviceTableName+" t2 where t2.deviceName='"+deviceName+"' and t2.devicetype="+deviceType+" ) connect by prior  org_parent=org_id) or u.user_orgid=0)";
		List<?> list = this.findCallSql(userSql);
		List<String> receivingEMailAccount=new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			if(isSendSMS&&"1".equalsIgnoreCase(obj[2]+"") && StringManagerUtils.isNotNull(obj[1]+"") && StringManagerUtils.isPhoneLegal(obj[1]+"")){
				StringBuffer sendContent = new StringBuffer();
				sendContent.append("{\"Mobile\":\""+obj[1]+"\",\"Value\":\""+SMSContent+"\"}");
				StringManagerUtils.sendPostMethod(SMSUrl, sendContent.toString(), "utf-8",0,0);
			}
			if("1".equalsIgnoreCase(obj[4]+"") && StringManagerUtils.isNotNull(obj[3]+"") && StringManagerUtils.isMailLegal(obj[3]+"")){
				receivingEMailAccount.add(obj[3]+"");
			}
		}
		if(isSendMail&&receivingEMailAccount.size()>0){
			StringManagerUtils.sendEMail(((StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200)?"泵":"管")+"设备"+deviceName+"报警", EMailContent, receivingEMailAccount);
		}
	}
	
	public String getObjectToRPCCalculateRequestData(Object[] object) throws SQLException, IOException, ParseException{
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String result="";
		try{
			String productionData=object[10].toString();
			
			type = new TypeToken<RPCCalculateRequestData>() {}.getType();
			RPCCalculateRequestData calculateRequestData=gson.fromJson(productionData, type);
			if(calculateRequestData==null){
				calculateRequestData=new RPCCalculateRequestData();
				calculateRequestData.init();
			}
			
			calculateRequestData.setWellName(object[0]+"");
			calculateRequestData.setScene(object[1]+"");

			//功图数据
			calculateRequestData.setFESDiagram(new RPCCalculateRequestData.FESDiagram());
	        calculateRequestData.getFESDiagram().setAcqTime(object[2]+"");
	        calculateRequestData.getFESDiagram().setSrc(StringManagerUtils.stringToInteger(object[3]+""));
	        calculateRequestData.getFESDiagram().setStroke(StringManagerUtils.stringToFloat(object[4]+""));
	        calculateRequestData.getFESDiagram().setSPM(StringManagerUtils.stringToFloat(object[5]+""));
			
	        List<Float> F=new ArrayList<Float>();
	        List<Float> S=new ArrayList<Float>();
	        List<Float> Watt=new ArrayList<Float>();
	        List<Float> I=new ArrayList<Float>();
	        
	        int count =Integer.MAX_VALUE;
//	        if(StringManagerUtils.isNum(object[21]+"") || StringManagerUtils.isNumber(object[21]+"")){
//	        	count=StringManagerUtils.stringToInteger(object[21]+"");
//	        }
	        
	        SerializableClobProxy proxy=null;
	        CLOB realClob =null;
	        String clobStr="";
	        String[] curveData=null;
	        if(object[6]!=null){//位移曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[6]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				curveData=clobStr.split(",");
				for(int i=0;i<curveData.length && i<count;i++){
					S.add(StringManagerUtils.stringToFloat(curveData[i]));
				}
	        }
	        if(object[7]!=null){//载荷曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[7]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				curveData=clobStr.split(",");
				for(int i=0;i<curveData.length && i<count;i++){
					F.add(StringManagerUtils.stringToFloat(curveData[i]));
				}
	        }
	        if(object[8]!=null){//功率曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[8]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				if(StringManagerUtils.isNotNull(clobStr)){
					curveData=clobStr.split(",");
					for(int i=0;i<curveData.length && i<count;i++){
						Watt.add(StringManagerUtils.stringToFloat(curveData[i]));
					}
				}
	        }
	        if(object[9]!=null){//电流曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[9]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				if(StringManagerUtils.isNotNull(clobStr)){
					curveData=clobStr.split(",");
					for(int i=0;i<curveData.length && i<count;i++){
						I.add(StringManagerUtils.stringToFloat(curveData[i]));
					}
				}
	        }
	        calculateRequestData.getFESDiagram().setF(F);
	        calculateRequestData.getFESDiagram().setS(S);
	        calculateRequestData.getFESDiagram().setWatt(Watt);
	        calculateRequestData.getFESDiagram().setI(I);
	        
	        
	        if(object.length>11){
	        	int pumpingModelId=StringManagerUtils.stringToInteger(object[11]+"");
	        	if(pumpingModelId>0){
	        		calculateRequestData.setPumpingUnit(new RPCCalculateRequestData.PumpingUnit());
	        		calculateRequestData.getPumpingUnit().setManufacturer(object[12]+"");
	        		calculateRequestData.getPumpingUnit().setModel(object[13]+"");
	        		calculateRequestData.getPumpingUnit().setCrankRotationDirection(object[14]+"");
	        		calculateRequestData.getPumpingUnit().setOffsetAngleOfCrank(StringManagerUtils.stringToFloat(object[15]+""));
					calculateRequestData.getPumpingUnit().setCrankGravityRadius(StringManagerUtils.stringToFloat(object[16]+""));
					calculateRequestData.getPumpingUnit().setSingleCrankWeight(StringManagerUtils.stringToFloat(object[17]+""));
					calculateRequestData.getPumpingUnit().setSingleCrankPinWeight(StringManagerUtils.stringToFloat(object[18]+""));
					calculateRequestData.getPumpingUnit().setStructuralUnbalance(StringManagerUtils.stringToFloat(object[19]+""));
					String balanceInfo=object[20]+"";
					type = new TypeToken<RPCCalculateRequestData.Balance>() {}.getType();
					RPCCalculateRequestData.Balance balance=gson.fromJson(balanceInfo, type);
					if(balance!=null){
						calculateRequestData.getPumpingUnit().setBalance(balance);
					}
	        	}else{
	        		calculateRequestData.setPumpingUnit(null);
	        	}
	        }else{
        		calculateRequestData.setPumpingUnit(null);
        	}
	        result=calculateRequestData.toString();
	        
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		return result;
	}
	
	public String getObjectToRPMCalculateRequestData(Object[] object) throws SQLException, IOException, ParseException{
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String result="";
		try{
			String productionData=object[4].toString();
			type = new TypeToken<PCPCalculateRequestData>() {}.getType();
			PCPCalculateRequestData calculateRequestData=gson.fromJson(productionData, type);
			if(calculateRequestData==null){
				calculateRequestData=new PCPCalculateRequestData();
				calculateRequestData.init();
			}
			calculateRequestData.setWellName(object[0]+"");
			calculateRequestData.setScene(object[1]+"");
			
			
			calculateRequestData.setAcqTime(object[2]+"");
			calculateRequestData.setRPM(StringManagerUtils.stringToFloat(object[3]+""));
			
			result=calculateRequestData.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		return result;
	}
	
	public void AcquisitionDataDailyCalculation(String tatalDate,String deviceId) throws ParseException{
//		String date="";
//		Gson gson = new Gson();
//		java.lang.reflect.Type type=null;
//		if(!StringManagerUtils.isNotNull(tatalDate)){
//			date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime("yyyy-MM-dd")),-1);
//		}else{
//			date=tatalDate;
//		}
//		int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
//		
//		
//		String wellSql="select t.id,t.devicename from tbl_device t where 1=1";
//		
//		String commStatusSql="select t2.id, t2.devicename,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
//				+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange"
//				+ " from tbl_acqdata_hist t,tbl_device t2 "
//				+ " where t.deviceId=t2.id "
//				+ " and t.acqTime=( select max(t3.acqTime) from tbl_acqdata_hist t3 where t3.deviceId=t.deviceId and t3.acqTime between to_date('"+date+"','yyyy-mm-dd') +"+offsetHour+"/24 and  to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 )";
//		String runStatusSql="select t2.id, t2.devicename,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
//				+ "t.runstatus,t.runtimeefficiency,t.runtime,t.runrange "
//				+ " from tbl_acqdata_hist t,tbl_device t2 "
//				+ " where t.deviceId=t2.id "
//				+ " and t.acqTime=( select max(t3.acqTime) from tbl_acqdata_hist t3 where t3.deviceId=t.deviceId and t3.commstatus=1 and t3.acqTime between to_date('"+date+"','yyyy-mm-dd') +"+offsetHour+"/24 and  to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 )";
//		String totalStatusSql="select t2.id,t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
//				+ " from tbl_dailycalculationdata t,tbl_device t2 "
//				+ " where t.deviceId=t2.id "
//				+ " and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
//		if(StringManagerUtils.isNotNull(deviceId)){
//			wellSql+=" and t.id in ("+deviceId+")";
//			commStatusSql+=" and t2.id in("+deviceId+")";
//			runStatusSql+=" and t2.id in("+deviceId+")";
//			totalStatusSql+=" and t2.id in ("+deviceId+")";
//		}
//		wellSql+=" order by t.id";
//		commStatusSql+=" order by t2.id";
//		runStatusSql+=" order by t2.id";
//		totalStatusSql+=" order by t2.id";
//		List<?> statusList=null;
//		
//		List<?> commStatusQueryList=null;
//		List<?> runStatusQueryList=null;
//		
//		if(!StringManagerUtils.isNotNull(tatalDate)){//如果是跨天汇总
//			commStatusQueryList = findCallSql(commStatusSql);
//			runStatusQueryList = findCallSql(runStatusSql);
//		}else{
//			statusList = findCallSql(totalStatusSql);
//		}
//		
//
//		List<String> tableColumnList=MemoryDataManagerTask.getAcqTableColumn("tbl_acqdata_hist");
//		List<String> totalTableColumnList=MemoryDataManagerTask.getAcqTableColumn("tbl_dailycalculationdata");
//		CommResponseData.Range dateTimeRange= StringManagerUtils.getTimeRange(date,Config.getInstance().configFile.getAp().getReport().getOffsetHour());
//		
//		List<String> columnList=new ArrayList<>();
//		for(int i=0;i<tableColumnList.size();i++){
//			if(StringManagerUtils.existOrNot(totalTableColumnList, tableColumnList.get(i), false)){
//				columnList.add(tableColumnList.get(i));
//			}
//		}
//		if(columnList.size()>0){
//			String sql="select deviceId";
//			String newestDataSql="select deviceId";
//			String oldestDataSql="select deviceId";
//			
//			String newestDailyTotalDataSql="select t.id,t.deviceid,t.acqtime,t.itemcolumn,t.itemname,t.totalvalue,t.todayvalue "
//					+ " from tbl_dailytotalcalculate_hist t,"
//					+ " (select deviceid,max(acqtime) as acqtime,itemcolumn  "
//					+ "  from tbl_dailytotalcalculate_hist "
//					+ "  where acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') "
//					+ "  group by deviceid,itemcolumn) v "
//					+ " where t.deviceid=v.deviceid and t.acqtime=v.acqtime and t.itemcolumn=v.itemcolumn"
//					+ " and t.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') ";
//			
//			
//			for(int i=0;i<columnList.size();i++){
//				String column=columnList.get(i);
//				sql+=",max(CASE WHEN REGEXP_LIKE(t."+column+", '^(-)*[[:digit:]]+(\\.[[:digit:]]+)*([Ee][+-]?[[:digit:]]+)*$') THEN t."+column+" ELSE null END)||';"
//						+ "'||min(CASE WHEN REGEXP_LIKE(t."+column+", '^(-)*[[:digit:]]+(\\.[[:digit:]]+)*([Ee][+-]?[[:digit:]]+)*$') THEN t."+column+" ELSE null END)||';"
//						+ "'||round(avg(CASE WHEN REGEXP_LIKE(t."+column+", '^(-)*[[:digit:]]+(\\.[[:digit:]]+)*([Ee][+-]?[[:digit:]]+)*$') THEN t."+column+" ELSE null END),2) as "+column+"";
//				newestDataSql+=",CASE WHEN REGEXP_LIKE(t."+column+", '^(-)*[[:digit:]]+(\\.[[:digit:]]+)*([Ee][+-]?[[:digit:]]+)*$') THEN t."+column+" ELSE null END as "+column;
//				oldestDataSql+=",CASE WHEN REGEXP_LIKE(t."+column+", '^(-)*[[:digit:]]+(\\.[[:digit:]]+)*([Ee][+-]?[[:digit:]]+)*$') THEN t."+column+" ELSE null END as "+column;
//			}
//			
//			sql+=" from tbl_acqdata_hist t "
//				+ " where t.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') ";
//			newestDataSql+=" from tbl_acqdata_hist t"
//					+ " where t.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') ";
//			oldestDataSql+=" from tbl_acqdata_hist t"
//					+ " where t.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') ";
//			if(StringManagerUtils.isNotNull(deviceId)){
//				sql+=" and t.deviceId="+deviceId;
//				newestDataSql+=" and t.deviceId="+deviceId;
//				oldestDataSql+=" and t.deviceId="+deviceId;
//				newestDailyTotalDataSql+=" and t.deviceid="+deviceId;
//			}else{
//				newestDataSql+=" and t.acqtime=(select min(t2.acqtime) from tbl_acqdata_hist t2 "
//						+ " where t2.deviceId=t.deviceId"
//						+ " and t2.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') "
//						+ " )";
//				oldestDataSql+=" and t.acqtime=(select max(t2.acqtime) from tbl_acqdata_hist t2 "
//						+ " where t2.deviceId=t.deviceId"
//						+ " and t2.acqtime between to_date('"+dateTimeRange.getStartTime()+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+dateTimeRange.getEndTime()+"','yyyy-mm-dd hh24:mi:ss') "
//						+ " )";
//			}
//			
//			
//			if(StringManagerUtils.isNotNull(deviceId)){
//				newestDataSql+=" order by t.acqtime";
//				newestDataSql="select * from("+newestDataSql+") where rownum=1";
//				oldestDataSql+=" order by t.acqtime";
//				oldestDataSql="select * from("+oldestDataSql+") where rownum=1";
//			}
//			
//			
//			sql+="group by t.deviceId";
//			newestDailyTotalDataSql+=" order by t.deviceid";
//			
//			List<?> totalList=findCallSql(sql);
//			List<?> newestValueList=findCallSql(newestDataSql);
//			List<?> oldestValueList=findCallSql(oldestDataSql);
//			List<?> newestDailyTotalDataList=findCallSql(newestDailyTotalDataSql);
//			
//			for(int i=0;i<totalList.size();i++){
//				Object[] obj=(Object[]) totalList.get(i);
//				String deviceIdStr=obj[0]+"";
//				Object[] newestValueObj=null;
//				Object[] oldestValueObj=null;
//				for(int j=0;j<newestValueList.size();j++){
//					Object[] newestValueListObj=(Object[]) newestValueList.get(j);
//					if(deviceIdStr.equalsIgnoreCase(newestValueListObj[0]+"")){
//						newestValueObj=newestValueListObj;
//						break;
//					}
//				}
//				for(int j=0;j<oldestValueList.size();j++){
//					Object[] oldestValueListObj=(Object[]) oldestValueList.get(j);
//					if(deviceIdStr.equalsIgnoreCase(oldestValueListObj[0]+"")){
//						oldestValueObj=oldestValueListObj;
//						break;
//					}
//				}
//				
//				int commStatus=0;
//				float commTime=0;
//				float commTimeEfficiency=0;
//				String commRange="";
//				
//				int runStatus=0;
//				float runTime=0;
//				float runTimeEfficiency=0;
//				String runRange="";
//				
//
//				try{
//					TimeEffResponseData timeEffResponseData=null;
//					CommResponseData commResponseData=null;
//					
//					
//					if(!StringManagerUtils.isNotNull(tatalDate)){//如果是跨天汇总
//						for(int j=0;j<commStatusQueryList.size();j++){
//							Object[] commStatusObj=(Object[]) commStatusQueryList.get(j);
//							if(deviceIdStr.equals(commStatusObj[0].toString())){
//								if(commStatusObj[3]!=null&&StringManagerUtils.stringToInteger(commStatusObj[3]+"")>=1){
//									commStatus=StringManagerUtils.stringToInteger(commStatusObj[3]+"");
//								}
//								String commTotalRequestData="{"
//										+ "\"AKString\":\"\","
//										+ "\"WellName\":\""+deviceIdStr+"\","
//										+ "\"OffsetHour\":"+offsetHour+","
//										+ "\"Last\":{"
//										+ "\"AcqTime\": \""+commStatusObj[2]+"\","
//										+ "\"CommStatus\": "+(commStatus>=1)+","
//										+ "\"CommEfficiency\": {"
//										+ "\"Efficiency\": "+commStatusObj[4]+","
//										+ "\"Time\": "+commStatusObj[5]+","
//										+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(commStatusObj[6]))+""
//										+ "}"
//										+ "},"
//										+ "\"Current\": {"
//										+ "\"AcqTime\":\""+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"\","
//										+ "\"CommStatus\":true"
//										+ "}"
//										+ "}";
//								commResponseData=CalculateUtils.commCalculate(commTotalRequestData);
//								if(commResponseData!=null&&commResponseData.getResultStatus()==1&&commResponseData.getDaily().getCommEfficiency().getRange()!=null&&commResponseData.getDaily().getCommEfficiency().getRange().size()>0){
//									commTime=commResponseData.getDaily().getCommEfficiency().getTime();
//									commTimeEfficiency=commResponseData.getDaily().getCommEfficiency().getEfficiency();
//									commRange=commResponseData.getDaily().getCommEfficiency().getRangeString();
//								}
//								break;
//							}
//						}
//						
//						for(int j=0;j<runStatusQueryList.size();j++){
//							Object[] runStatusObj=(Object[]) runStatusQueryList.get(j);
//							if(deviceIdStr.equals(runStatusObj[0].toString())){		
//								if(runStatusObj[3]!=null&&StringManagerUtils.stringToInteger(runStatusObj[3]+"")>=1){
//									runStatus=StringManagerUtils.stringToInteger(runStatusObj[3]+"");
//								}
//								String runTotalRequestData="{"
//										+ "\"AKString\":\"\","
//										+ "\"WellName\":\""+deviceIdStr+"\","
//										+ "\"OffsetHour\":"+offsetHour+","
//										+ "\"Last\":{"
//										+ "\"AcqTime\": \""+runStatusObj[2]+"\","
//										+ "\"RunStatus\": "+(runStatus>=1)+","
//										+ "\"RunEfficiency\": {"
//										+ "\"Efficiency\": "+runStatusObj[4]+","
//										+ "\"Time\": "+runStatusObj[5]+","
//										+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(runStatusObj[6]))+""
//										+ "}"
//										+ "},"
//										+ "\"Current\": {"
//										+ "\"AcqTime\":\""+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"\","
//										+ "\"RunStatus\":true"
//										+ "}"
//										+ "}";
//								timeEffResponseData=CalculateUtils.runCalculate(runTotalRequestData);
//								if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1&&timeEffResponseData.getDaily().getRunEfficiency().getRange()!=null&&timeEffResponseData.getDaily().getRunEfficiency().getRange().size()>0){
//									runTime=timeEffResponseData.getDaily().getRunEfficiency().getTime();
//									runTimeEfficiency=timeEffResponseData.getDaily().getRunEfficiency().getEfficiency();
//									runRange=timeEffResponseData.getDaily().getRunEfficiency().getRangeString();
//								}
//								break;
//							}
//						}
//					}else{
//						for(int j=0;j<statusList.size();j++){
//							Object[] statusObj=(Object[]) statusList.get(j);
//							if(deviceIdStr.equals(statusObj[0].toString())){
//								commStatus=StringManagerUtils.stringToInteger(statusObj[1]+"");
//								commTime=StringManagerUtils.stringToFloat(statusObj[2]+"");
//								commTimeEfficiency=StringManagerUtils.stringToFloat(statusObj[3]+"");
//								commRange=StringManagerUtils.CLOBObjectToString(statusObj[4]);
//								
//								runStatus=StringManagerUtils.stringToInteger(statusObj[5]+"");
//								runTime=StringManagerUtils.stringToFloat(statusObj[6]+"");
//								runTimeEfficiency=StringManagerUtils.stringToFloat(statusObj[7]+"");
//								runRange=StringManagerUtils.CLOBObjectToString(statusObj[8]);
//								break;
//							}
//						}
//					}
//				}catch(Exception e){
//					e.printStackTrace();
//					continue;
//				}
//			
//				String updateSql="update tbl_dailycalculationdata t set t.CommStatus="+commStatus+",commTimeEfficiency="+commTimeEfficiency+",commTime="+commTime;
//				updateSql+=",runStatus="+runStatus+",runTimeEfficiency="+runTimeEfficiency+",runTime="+runTime;
//				
//				for(int j=1;j<obj.length;j++){
//					String oldestValue=oldestValueObj==null?" ":(oldestValueObj[j]+"");
//					String newestValue=oldestValueObj==null?" ":(newestValueObj[j]+"");
//					String dailyTotalValue=" ";
//					
//					String colnum=columnList.get(j-1);
//					String totalColumn=(colnum+"_total").toUpperCase();
//					
//					for(int k=0;k<newestDailyTotalDataList.size();k++){
//						Object[] newestDailyTotalDataObj=(Object[]) newestDailyTotalDataList.get(k);
//						if(deviceIdStr.equalsIgnoreCase(newestDailyTotalDataObj[1]+"") && totalColumn.equalsIgnoreCase(newestDailyTotalDataObj[3]+"")){
//							dailyTotalValue=newestDailyTotalDataObj[6]+"";
//							break;
//						}
//					}
//					
//					
//					String tatalValue=(obj[j]+";"+oldestValue+";"+newestValue+";"+dailyTotalValue).replaceAll("null", "");
//					updateSql+=",t."+colnum+"='"+tatalValue+"'";
//				}
//				
//				updateSql+=" where t.deviceId="+deviceIdStr+" and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
//				
//				try {
//					int r=this.getBaseDao().updateOrDeleteBySql(updateSql);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				
//				try {
//					List<String> clobCont=new ArrayList<String>();
//					String updateRangeClobSql="update tbl_dailycalculationdata t set t.commrange=?, t.runrange=?";
//					clobCont.add(commRange);
//					clobCont.add(runRange);
//					updateRangeClobSql+=" where t.deviceId="+deviceIdStr+" and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
//					int r=this.getBaseDao().executeSqlUpdateClob(updateRangeClobSql,clobCont);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}
	
	public List<String> getFSDiagramDailyCalculationRequestData(String tatalDate,String deviceIds) throws ParseException{
		String date="";
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		if(!StringManagerUtils.isNotNull(tatalDate)){
			date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime("yyyy-MM-dd")),-1);
		}else{
			date=tatalDate;
		}
		int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
		
		StringBuffer dataSbf=null;
		List<String> requestDataList=new ArrayList<String>();
		String sql="select t.id,t.devicename from tbl_device t where 1=1";
		String fesDiagramSql="select t2.id, to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss'),t.resultcode,"
				+ "t.stroke,t.spm,t.fmax,t.fmin,t.fullnesscoefficient,"
				+ "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
				+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
				+ "t.productiondata,"
				+ "t.pumpeff,t.pumpeff1,t.pumpeff2,t.pumpeff3,t.pumpeff4,"
				+ "t.wattdegreebalance,t.idegreebalance,t.deltaradius,"
				+ "t.surfacesystemefficiency,t.welldownsystemefficiency,t.systemefficiency,t.energyper100mlift,"
				+ "t.calcProducingfluidLevel,t.levelDifferenceValue,"
				+ "t.submergence,"
				+ "t.rpm "
				+ " from tbl_rpcacqdata_hist t,tbl_device t2 "
				+ " where t.deviceId=t2.id "
				+ " and t2.calculateType=1"
				+ " and t.fesdiagramacqtime between to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24 and to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 "
				+ " and t.resultstatus=1 ";
		String commStatusSql="select t2.id, t2.devicename,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange"
				+ " from tbl_rpcacqdata_hist t,tbl_device t2 "
				+ " where t.deviceId=t2.id "
				+ " and t2.calculateType=1"
				+ " and t.acqTime=( select max(t3.acqTime) from tbl_rpcacqdata_hist t3 where t3.deviceId=t.deviceId and t3.acqTime between to_date('"+date+"','yyyy-mm-dd') +"+offsetHour+"/24 and  to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 )";
		String runStatusSql="select t2.id, t2.devicename,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.runstatus,t.runtimeefficiency,t.runtime,t.runrange "
				+ " from tbl_rpcacqdata_hist t,tbl_device t2 "
				+ " where t.deviceId=t2.id "
				+ " and t2.calculateType=1"
				+ " and t.acqTime=( select max(t3.acqTime) from tbl_rpcacqdata_hist t3 where t3.deviceId=t.deviceId and t3.commstatus=1 and t3.acqTime between to_date('"+date+"','yyyy-mm-dd') +"+offsetHour+"/24 and  to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 )";
		String totalStatusSql="select t2.id,t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
				+ " from tbl_rpcdailycalculationdata t,tbl_device t2 "
				+ " where t.deviceId=t2.id "
				+ " and t2.calculateType=1"
				+ " and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(deviceIds)){
			sql+=" and t.id in ("+deviceIds+")";
			fesDiagramSql+=" and t2.id in ("+deviceIds+")";
			commStatusSql+=" and t2.id in("+deviceIds+")";
			runStatusSql+=" and t2.id in("+deviceIds+")";
			totalStatusSql+=" and t2.id in ("+deviceIds+")";
		}
		sql+=" order by t.id";
		fesDiagramSql+= " order by t2.id,t.fesdiagramacqtime";
		commStatusSql+=" order by t2.id";
		runStatusSql+=" order by t2.id";
		totalStatusSql+=" order by t2.id";
		List<?> welllist = findCallSql(sql);
		List<?> singleresultlist = findCallSql(fesDiagramSql);
		List<?> statusList=null;
		
		List<?> commStatusQueryList=null;
		List<?> runStatusQueryList=null;
		
		if(!StringManagerUtils.isNotNull(tatalDate)){//如果是跨天汇总
			commStatusQueryList = findCallSql(commStatusSql);
			runStatusQueryList = findCallSql(runStatusSql);
		}else{
			statusList = findCallSql(totalStatusSql);
		}
		for(int i=0;i<welllist.size();i++){
			try{
				Object[] wellObj=(Object[]) welllist.get(i);
				String deviceId=wellObj[0]+"";
				String deviceName=wellObj[1]+"";
				TimeEffResponseData timeEffResponseData=null;
				CommResponseData commResponseData=null;
				
				boolean commStatus=false;
				float commTime=0;
				float commTimeEfficiency=0;
				String commRange="";
				
				boolean runStatus=false;
				float runTime=0;
				float runTimeEfficiency=0;
				String runRange="";
				
				if(!StringManagerUtils.isNotNull(tatalDate)){//如果是跨天汇总
					for(int j=0;j<commStatusQueryList.size();j++){
						Object[] commStatusObj=(Object[]) commStatusQueryList.get(j);
						if(deviceId.equals(commStatusObj[0].toString())){
							if(commStatusObj[3]!=null&&StringManagerUtils.stringToInteger(commStatusObj[3]+"")>=1){
								commStatus=true;
							}
							String commTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+deviceName+"\","
									+ "\"OffsetHour\":"+offsetHour+","
									+ "\"Last\":{"
									+ "\"AcqTime\": \""+commStatusObj[2]+"\","
									+ "\"CommStatus\": "+commStatus+","
									+ "\"CommEfficiency\": {"
									+ "\"Efficiency\": "+commStatusObj[4]+","
									+ "\"Time\": "+commStatusObj[5]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(commStatusObj[6]))+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcqTime\":\""+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"\","
									+ "\"CommStatus\":true"
									+ "}"
									+ "}";
							commResponseData=CalculateUtils.commCalculate(commTotalRequestData);
							if(commResponseData!=null&&commResponseData.getResultStatus()==1&&commResponseData.getDaily().getCommEfficiency().getRange()!=null&&commResponseData.getDaily().getCommEfficiency().getRange().size()>0){
								commStatus=commResponseData.getDaily().getCommStatus();
								commTime=commResponseData.getDaily().getCommEfficiency().getTime();
								commTimeEfficiency=commResponseData.getDaily().getCommEfficiency().getEfficiency();
								commRange=commResponseData.getDaily().getCommEfficiency().getRangeString();
							}
							break;
						}
					}
					
					for(int j=0;j<runStatusQueryList.size();j++){
						Object[] runStatusObj=(Object[]) runStatusQueryList.get(j);
						if(deviceId.equals(runStatusObj[0].toString())){		
							if(runStatusObj[3]!=null&&StringManagerUtils.stringToInteger(runStatusObj[3]+"")>=1){
								runStatus=true;
							}
							String runTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+deviceName+"\","
									+ "\"OffsetHour\":"+offsetHour+","
									+ "\"Last\":{"
									+ "\"AcqTime\": \""+runStatusObj[2]+"\","
									+ "\"RunStatus\": "+runStatus+","
									+ "\"RunEfficiency\": {"
									+ "\"Efficiency\": "+runStatusObj[4]+","
									+ "\"Time\": "+runStatusObj[5]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(runStatusObj[6]))+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcqTime\":\""+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"\","
									+ "\"RunStatus\":true"
									+ "}"
									+ "}";
							timeEffResponseData=CalculateUtils.runCalculate(runTotalRequestData);
							if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1&&timeEffResponseData.getDaily().getRunEfficiency().getRange()!=null&&timeEffResponseData.getDaily().getRunEfficiency().getRange().size()>0){
								runStatus=timeEffResponseData.getDaily().getRunStatus();
								runTime=timeEffResponseData.getDaily().getRunEfficiency().getTime();
								runTimeEfficiency=timeEffResponseData.getDaily().getRunEfficiency().getEfficiency();
								runRange=timeEffResponseData.getDaily().getRunEfficiency().getRangeString();
							}
							break;
						}
					}
				}else{
					for(int j=0;j<statusList.size();j++){
						Object[] statusObj=(Object[]) statusList.get(j);
						if(deviceId.equals(statusObj[0].toString())){
							commStatus=StringManagerUtils.stringToInteger(statusObj[1]+"")>=1;
							commTime=StringManagerUtils.stringToFloat(statusObj[2]+"");
							commTimeEfficiency=StringManagerUtils.stringToFloat(statusObj[3]+"");
							commRange=StringManagerUtils.CLOBObjectToString(statusObj[4]);
							
							runStatus=StringManagerUtils.stringToInteger(statusObj[5]+"")==1;
							runTime=StringManagerUtils.stringToFloat(statusObj[6]+"");
							runTimeEfficiency=StringManagerUtils.stringToFloat(statusObj[7]+"");
							runRange=StringManagerUtils.CLOBObjectToString(statusObj[8]);
							break;
						}
					}
				}
				
				
				List<String> acqTimeList=new ArrayList<String>();
				List<Integer> commStatusList=new ArrayList<Integer>();
				List<Integer> runStatusList=new ArrayList<Integer>();
				
				List<Float> rpmList=new ArrayList<Float>();
				
				List<Integer> ResultCodeList=new ArrayList<Integer>();
				List<Float> strokeList=new ArrayList<Float>();
				List<Float> spmList=new ArrayList<Float>();
				
				List<Float> FMaxList=new ArrayList<Float>();
				List<Float> FMinList=new ArrayList<Float>();
				
				List<Float> fullnessCoefficientList=new ArrayList<Float>();
				
				List<Float> theoreticalProductionList=new ArrayList<Float>();
				List<Float> liquidVolumetricProductionList=new ArrayList<Float>();
				List<Float> oilVolumetricProductionList=new ArrayList<Float>();
				List<Float> waterVolumetricProductionList=new ArrayList<Float>();
				List<Float> volumeWaterCutList=new ArrayList<Float>();
				
				List<Float> liquidWeightProductionList=new ArrayList<Float>();
				List<Float> oilWeightProductionList=new ArrayList<Float>();
				List<Float> waterWeightProductionList=new ArrayList<Float>();
				List<Float> weightWaterCutList=new ArrayList<Float>();
				
				List<Float> pumpEffList=new ArrayList<Float>();
				List<Float> pumpEff1List=new ArrayList<Float>();
				List<Float> pumpEff2List=new ArrayList<Float>();
				List<Float> pumpEff3List=new ArrayList<Float>();
				List<Float> pumpEff4List=new ArrayList<Float>();
				
				
				List<Float> wattDegreeBalanceList=new ArrayList<Float>();
				List<Float> iDegreeBalanceList=new ArrayList<Float>();
				List<Float> deltaRadiusList=new ArrayList<Float>();
				
				List<Float> surfaceSystemEfficiencyList=new ArrayList<Float>();
				List<Float> wellDownSystemEfficiencyList=new ArrayList<Float>();
				List<Float> systemEfficiencyList=new ArrayList<Float>();
				List<Float> energyPer100mLiftList=new ArrayList<Float>();
				
				List<Float> pumpSettingDepthList=new ArrayList<Float>();
				List<Float> producingfluidLevelList=new ArrayList<Float>();
				List<Float> calcProducingfluidLevelList=new ArrayList<Float>();
				List<Float> levelDifferenceValueList=new ArrayList<Float>();
				List<Float> submergenceList=new ArrayList<Float>();
				
				List<Float> tubingPressureList=new ArrayList<Float>();
				List<Float> casingPressureList=new ArrayList<Float>();
				
				for(int j=0;j<singleresultlist.size();j++){
					Object[] resuleObj=(Object[]) singleresultlist.get(j);
					if(deviceId.toString().equals(resuleObj[0].toString())){
						String productionData=resuleObj[15].toString();
						type = new TypeToken<RPCCalculateRequestData>() {}.getType();
						RPCCalculateRequestData rpcProductionData=gson.fromJson(productionData, type);
						
						acqTimeList.add(resuleObj[1]+"");
						commStatusList.add(commStatus?1:0);
						runStatusList.add(runStatus?1:0);
					
						ResultCodeList.add(StringManagerUtils.stringToInteger(resuleObj[2]+""));
						strokeList.add(StringManagerUtils.stringToFloat(resuleObj[3]+""));
						spmList.add(StringManagerUtils.stringToFloat(resuleObj[4]+""));
						FMaxList.add(StringManagerUtils.stringToFloat(resuleObj[5]+""));
						FMinList.add(StringManagerUtils.stringToFloat(resuleObj[6]+""));
						fullnessCoefficientList.add(StringManagerUtils.stringToFloat(resuleObj[7]+""));
						
						theoreticalProductionList.add(StringManagerUtils.stringToFloat(resuleObj[8]+""));
						liquidVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[9]+""));
						oilVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[10]+""));
						waterVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[11]+""));
						
						if(rpcProductionData!=null&&rpcProductionData.getProduction()!=null){
							volumeWaterCutList.add(rpcProductionData.getProduction().getWaterCut());
						}else{
							volumeWaterCutList.add(0.0f);
						}
						
						liquidWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[12]+""));
						oilWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[13]+""));
						waterWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[14]+""));
						if(rpcProductionData!=null&&rpcProductionData.getProduction()!=null){
							weightWaterCutList.add(rpcProductionData.getProduction().getWeightWaterCut());
						}else{
							weightWaterCutList.add(0.0f);
						}
						
						if(rpcProductionData!=null&&rpcProductionData.getProduction()!=null){
							tubingPressureList.add(rpcProductionData.getProduction().getTubingPressure());
							casingPressureList.add(rpcProductionData.getProduction().getCasingPressure());
							pumpSettingDepthList.add(rpcProductionData.getProduction().getPumpSettingDepth());
							producingfluidLevelList.add(rpcProductionData.getProduction().getProducingfluidLevel());
						}else{
							tubingPressureList.add(0.0f);
							casingPressureList.add(0.0f);
							pumpSettingDepthList.add(0.0f);
							producingfluidLevelList.add(0.0f);
						}
						
						
						pumpEffList.add(StringManagerUtils.stringToFloat(resuleObj[16]+""));
						pumpEff1List.add(StringManagerUtils.stringToFloat(resuleObj[17]+""));
						pumpEff2List.add(StringManagerUtils.stringToFloat(resuleObj[18]+""));
						pumpEff3List.add(StringManagerUtils.stringToFloat(resuleObj[19]+""));
						pumpEff4List.add(StringManagerUtils.stringToFloat(resuleObj[20]+""));
						
						wattDegreeBalanceList.add(StringManagerUtils.stringToFloat(resuleObj[21]+""));
						iDegreeBalanceList.add(StringManagerUtils.stringToFloat(resuleObj[22]+""));
						deltaRadiusList.add(StringManagerUtils.stringToFloat(resuleObj[23]+""));
						
						surfaceSystemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[24]+""));
						wellDownSystemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[25]+""));
						systemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[26]+""));
						energyPer100mLiftList.add(StringManagerUtils.stringToFloat(resuleObj[27]+""));
						
						calcProducingfluidLevelList.add(StringManagerUtils.stringToFloat(resuleObj[28]+""));
						levelDifferenceValueList.add(StringManagerUtils.stringToFloat(resuleObj[29]+""));
						submergenceList.add(StringManagerUtils.stringToFloat(resuleObj[30]+""));
						
						rpmList.add(StringManagerUtils.stringToFloat(resuleObj[31]+""));
					}
				}
				
				dataSbf = new StringBuffer();
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+deviceId+"\",");
				dataSbf.append("\"CurrentCommStatus\":"+(commStatus?1:0)+",");
				dataSbf.append("\"CurrentRunStatus\":"+(runStatus?1:0)+",");
				dataSbf.append("\"Date\":\""+date+"\",");
				dataSbf.append("\"OffsetHour\":"+offsetHour+",");
				dataSbf.append("\"AcqTime\":["+StringManagerUtils.joinStringArr(acqTimeList, ",")+"],");
				dataSbf.append("\"CommStatus\":["+StringUtils.join(commStatusList, ",")+"],");
				dataSbf.append("\"CommTime\":"+commTime+",");
				dataSbf.append("\"CommTimeEfficiency\":"+commTimeEfficiency+",");
				dataSbf.append("\"CommRange\":\""+commRange+"\",");
				dataSbf.append("\"RunStatus\":["+StringUtils.join(runStatusList, ",")+"],");
				dataSbf.append("\"RunTime\":"+runTime+",");
				dataSbf.append("\"RunTimeEfficiency\":"+runTimeEfficiency+",");
				dataSbf.append("\"RunRange\":\""+runRange+"\",");
				dataSbf.append("\"ResultCode\":["+StringUtils.join(ResultCodeList, ",")+"],");
				dataSbf.append("\"TheoreticalProduction\":["+StringUtils.join(theoreticalProductionList, ",")+"],");
				dataSbf.append("\"LiquidVolumetricProduction\":["+StringUtils.join(liquidVolumetricProductionList, ",")+"],");
				dataSbf.append("\"OilVolumetricProduction\":["+StringUtils.join(oilVolumetricProductionList, ",")+"],");
				dataSbf.append("\"WaterVolumetricProduction\":["+StringUtils.join(waterVolumetricProductionList, ",")+"],");
				dataSbf.append("\"VolumeWaterCut\":["+StringUtils.join(volumeWaterCutList, ",")+"],");
				dataSbf.append("\"LiquidWeightProduction\":["+StringUtils.join(liquidWeightProductionList, ",")+"],");
				dataSbf.append("\"OilWeightProduction\":["+StringUtils.join(oilWeightProductionList, ",")+"],");
				dataSbf.append("\"WaterWeightProduction\":["+StringUtils.join(waterWeightProductionList, ",")+"],");
				dataSbf.append("\"WeightWaterCut\":["+StringUtils.join(weightWaterCutList, ",")+"],");
				dataSbf.append("\"SurfaceSystemEfficiency\":["+StringUtils.join(surfaceSystemEfficiencyList, ",")+"],");
				dataSbf.append("\"WellDownSystemEfficiency\":["+StringUtils.join(wellDownSystemEfficiencyList, ",")+"],");
				dataSbf.append("\"SystemEfficiency\":["+StringUtils.join(systemEfficiencyList, ",")+"],");
				dataSbf.append("\"EnergyPer100mLift\":["+StringUtils.join(energyPer100mLiftList, ",")+"],");
				dataSbf.append("\"Stroke\":["+StringUtils.join(strokeList, ",")+"],");
				dataSbf.append("\"SPM\":["+StringUtils.join(spmList, ",")+"],");
				dataSbf.append("\"FMax\":["+StringUtils.join(FMaxList, ",")+"],");
				dataSbf.append("\"FMin\":["+StringUtils.join(FMinList, ",")+"],");
				dataSbf.append("\"FullnessCoefficient\":["+StringUtils.join(fullnessCoefficientList, ",")+"],");
				dataSbf.append("\"PumpEff\":["+StringUtils.join(pumpEffList, ",")+"],");
				dataSbf.append("\"PumpEff1\":["+StringUtils.join(pumpEff1List, ",")+"],");
				dataSbf.append("\"PumpEff2\":["+StringUtils.join(pumpEff2List, ",")+"],");
				dataSbf.append("\"PumpEff3\":["+StringUtils.join(pumpEff3List, ",")+"],");
				dataSbf.append("\"PumpEff4\":["+StringUtils.join(pumpEff4List, ",")+"],");
				dataSbf.append("\"WattDegreeBalance\":["+StringUtils.join(wattDegreeBalanceList, ",")+"],");
				dataSbf.append("\"IDegreeBalance\":["+StringUtils.join(iDegreeBalanceList, ",")+"],");
				dataSbf.append("\"DeltaRadius\":["+StringUtils.join(deltaRadiusList, ",")+"],");
				dataSbf.append("\"PumpSettingDepth\":["+StringUtils.join(pumpSettingDepthList, ",")+"],");
				dataSbf.append("\"ProducingfluidLevel\":["+StringUtils.join(producingfluidLevelList, ",")+"],");
				dataSbf.append("\"CalcProducingfluidLevel\":["+StringUtils.join(calcProducingfluidLevelList, ",")+"],");
				dataSbf.append("\"LevelDifferenceValue\":["+StringUtils.join(levelDifferenceValueList, ",")+"],");
				dataSbf.append("\"Submergence\":["+StringUtils.join(submergenceList, ",")+"],");
				dataSbf.append("\"TubingPressure\":["+StringUtils.join(tubingPressureList, ",")+"],");
				dataSbf.append("\"CasingPressure\":["+StringUtils.join(casingPressureList, ",")+"],");
				dataSbf.append("\"RPM\":["+StringUtils.join(rpmList, ",")+"]");
				dataSbf.append("}");
				requestDataList.add(dataSbf.toString());
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		return requestDataList;
	}
	
	public List<String> AcquisitionDataTimingTotalCalculation(String timeStr){
		ThreadPool executor = new ThreadPool("AcquisitionDataTinmingTotalCalculate",
				Config.getInstance().configFile.getAp().getThreadPool().getTimingTotalCalculate().getCorePoolSize(), 
				Config.getInstance().configFile.getAp().getThreadPool().getTimingTotalCalculate().getMaximumPoolSize(), 
				Config.getInstance().configFile.getAp().getThreadPool().getTimingTotalCalculate().getKeepAliveTime(), 
				TimeUnit.SECONDS, 
				Config.getInstance().configFile.getAp().getThreadPool().getTimingTotalCalculate().getWattingCount());
		
		String sql="select t.id,t.devicename,t3.singleWellDailyReportTemplate,t2.unitid "
				+ " from tbl_device t "
				+ " left outer join tbl_protocolreportinstance t2 on t.reportinstancecode=t2.code"
				+ " left outer join tbl_report_unit_conf t3 on t2.unitid=t3.id "
				+ " where 1=1"
//				+ " and t.id=1"
				+ " order by t.id";
		List<?> welllist = findCallSql(sql);
		for(int i=0;i<welllist.size();i++){
			try{
				Object[] wellObj=(Object[]) welllist.get(i);
				int deviceId=StringManagerUtils.stringToInteger(wellObj[0]+"");
				String deviceName=wellObj[1]+"";
				String templateCode=(wellObj[2]+"").replaceAll("null", ""); 
				String reportUnitId=wellObj[3]+"";
				TimingTotalCalculateThread thread=new TimingTotalCalculateThread(deviceId, deviceId, deviceName, timeStr, templateCode,
						reportUnitId, 0, commonDataService);
				executor.execute(thread);
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		
		while (!executor.isCompletedByTaskCount()) {
			try {
				Thread.sleep(1000*1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
		
		return null;
	}
	
	public List<String> RPCTimingTotalCalculation(String timeStr){
		ThreadPool executor = new ThreadPool("RPCTinmingTotalCalculate",
				Config.getInstance().configFile.getAp().getThreadPool().getTimingTotalCalculate().getCorePoolSize(), 
				Config.getInstance().configFile.getAp().getThreadPool().getTimingTotalCalculate().getMaximumPoolSize(), 
				Config.getInstance().configFile.getAp().getThreadPool().getTimingTotalCalculate().getKeepAliveTime(), 
				TimeUnit.SECONDS, 
				Config.getInstance().configFile.getAp().getThreadPool().getTimingTotalCalculate().getWattingCount());
		
		String sql="select t.id,t.devicename,t3.singleWellDailyReportTemplate,t2.unitid "
				+ " from tbl_device t "
				+ " left outer join tbl_protocolreportinstance t2 on t.reportinstancecode=t2.code"
				+ " left outer join tbl_report_unit_conf t3 on t2.unitid=t3.id "
				+ " where 1=1"
				+ " and t.calculateType=1"
				+ " order by t.id";
		List<?> welllist = findCallSql(sql);
		for(int i=0;i<welllist.size();i++){
			try{
				Object[] wellObj=(Object[]) welllist.get(i);
				int deviceId=StringManagerUtils.stringToInteger(wellObj[0]+"");
				String deviceName=wellObj[1]+"";
				String templateCode=(wellObj[2]+"").replaceAll("null", ""); 
				String reportUnitId=wellObj[3]+"";
				TimingTotalCalculateThread thread=new TimingTotalCalculateThread(deviceId, deviceId, deviceName, timeStr, templateCode,
						reportUnitId, 1, commonDataService);
				executor.execute(thread);
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		
		while (!executor.isCompletedByTaskCount()) {
			try {
				Thread.sleep(1000*1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
		
		return null;
	}
	
	public List<String> getRPMDailyCalculationRequestData(String tatalDate,String deviceIds) throws ParseException{
		String date="";
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		if(!StringManagerUtils.isNotNull(tatalDate)){
			date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime("yyyy-MM-dd")),-1);
		}else{
			date=tatalDate;
		}
		int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
		
		StringBuffer dataSbf=null;
		List<String> requestDataList=new ArrayList<String>();
		String sql="select t.id,t.deviceName from tbl_device t where t.calculateType=2 ";
		String rpmSql="select t2.id, "
				+ "to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss'),t.rpm,"
				+ "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
				+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
				+ "t.productiondata,"
				+ "t.pumpeff,t.pumpeff1,t.pumpeff2,"
				+ "t.systemefficiency,t.energyper100mlift,"
				+ "t.submergence "
				+ " from tbl_pcpacqdata_hist t,tbl_device t2 "
				+ " where t2.calculateType=2 and t.deviceId=t2.id "
				+ " and t.acqtime between to_date('"+date+"','yyyy-mm-dd') +"+offsetHour+"/24 and to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 "
				+ " and t.resultstatus=1 ";
		String commStatusSql="select t2.id, t2.deviceName,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange "
				+ " from tbl_pcpacqdata_hist t,tbl_device t2 "
				+ " where t2.calculateType=2 and t.deviceId=t2.id and t.acqTime=( select max(t3.acqTime) from tbl_pcpacqdata_hist t3 where t3.deviceId=t.deviceId and t3.acqTime between to_date('"+date+"','yyyy-mm-dd') +"+offsetHour+"/24 and  to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 )";
		
		String runStatusSql="select t2.id, t2.deviceName,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.runstatus,t.runtimeefficiency,t.runtime,t.runrange "
				+ " from tbl_pcpacqdata_hist t,tbl_device t2 "
				+ " where t2.calculateType=2 and t.deviceId=t2.id and t.acqTime=( select max(t3.acqTime) from tbl_pcpacqdata_hist t3 where t3.deviceId=t.deviceId and t3.commstatus=1 and t3.acqTime between to_date('"+date+"','yyyy-mm-dd') +"+offsetHour+"/24 and  to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 )";
		
		String totalStatusSql="select t2.id,t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
				+ " from tbl_pcpdailycalculationdata t,tbl_device t2 "
				+ " where t.deviceId=t2.id and t2.calculateType=2"
				+ " and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(deviceIds)){
			sql+=" and t.id in ("+deviceIds+")";
			rpmSql+=" and t2.id in ("+deviceIds+")";
			commStatusSql+=" and t2.id in("+deviceIds+")";
			runStatusSql+=" and t2.id in("+deviceIds+")";
			totalStatusSql+=" and t2.id in ("+deviceIds+")";
		}
		sql+=" order by t.id";
		rpmSql+= " order by t2.id,t.acqtime";
		commStatusSql+=" order by t2.id";
		runStatusSql+=" order by t2.id";
		totalStatusSql+=" order by t2.id";
		List<?> welllist = findCallSql(sql);
		List<?> singleResultlist = findCallSql(rpmSql);
		List<?> statusList=null;
		List<?> commStatusQueryList=null;
		List<?> runStatusQueryList=null;
		
		if(!StringManagerUtils.isNotNull(tatalDate)){//如果是跨天汇总
			commStatusQueryList = findCallSql(commStatusSql);
			runStatusQueryList = findCallSql(runStatusSql);
		}else{
			statusList = findCallSql(totalStatusSql);
		}
		
		for(int i=0;i<welllist.size();i++){
			try{
				Object[] wellObj=(Object[]) welllist.get(i);
				String deviceId=wellObj[0]+"";
				String deviceName=wellObj[1]+"";
				TimeEffResponseData timeEffResponseData=null;
				CommResponseData commResponseData=null;
				
				boolean commStatus=false;
				float commTime=0;
				float commTimeEfficiency=0;
				String commRange="";
				
				boolean runStatus=false;
				float runTime=0;
				float runTimeEfficiency=0;
				String runRange="";
				
				if(!StringManagerUtils.isNotNull(tatalDate)){//如果是跨天汇总
					for(int j=0;j<commStatusQueryList.size();j++){
						Object[] commStatusObj=(Object[]) commStatusQueryList.get(j);
						if(deviceId.equals(commStatusObj[0].toString())){
							if(commStatusObj[3]!=null&&StringManagerUtils.stringToInteger(commStatusObj[3]+"")>=1){
								commStatus=true;
							}
							String commTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+deviceName+"\","
									+ "\"OffsetHour\":"+offsetHour+","
									+ "\"Last\":{"
									+ "\"AcqTime\": \""+commStatusObj[2]+"\","
									+ "\"CommStatus\": "+commStatus+","
									+ "\"CommEfficiency\": {"
									+ "\"Efficiency\": "+commStatusObj[4]+","
									+ "\"Time\": "+commStatusObj[5]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(commStatusObj[6]))+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcqTime\":\""+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"\","
									+ "\"CommStatus\":true"
									+ "}"
									+ "}";
							commResponseData=CalculateUtils.commCalculate(commTotalRequestData);
							if(commResponseData!=null&&commResponseData.getResultStatus()==1&&commResponseData.getDaily().getCommEfficiency().getRange()!=null&&commResponseData.getDaily().getCommEfficiency().getRange().size()>0){
								commStatus=commResponseData.getDaily().getCommStatus();
								commTime=commResponseData.getDaily().getCommEfficiency().getTime();
								commTimeEfficiency=commResponseData.getDaily().getCommEfficiency().getEfficiency();
								commRange=commResponseData.getDaily().getCommEfficiency().getRangeString();
							}
							break;
						}
					}
					
					for(int j=0;j<runStatusQueryList.size();j++){
						Object[] runStatusObj=(Object[]) runStatusQueryList.get(j);
						if(deviceId.equals(runStatusObj[0].toString())){		
							if(runStatusObj[3]!=null&&StringManagerUtils.stringToInteger(runStatusObj[3]+"")>=1){
								runStatus=true;
							}
							String runTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+deviceName+"\","
									+ "\"OffsetHour\":"+offsetHour+","
									+ "\"Last\":{"
									+ "\"AcqTime\": \""+runStatusObj[2]+"\","
									+ "\"RunStatus\": "+runStatus+","
									+ "\"RunEfficiency\": {"
									+ "\"Efficiency\": "+runStatusObj[4]+","
									+ "\"Time\": "+runStatusObj[5]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(runStatusObj[6]))+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcqTime\":\""+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+"\","
									+ "\"RunStatus\":true"
									+ "}"
									+ "}";
							timeEffResponseData=CalculateUtils.runCalculate(runTotalRequestData);
							if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1&&timeEffResponseData.getDaily().getRunEfficiency().getRange()!=null&&timeEffResponseData.getDaily().getRunEfficiency().getRange().size()>0){
								runStatus=timeEffResponseData.getDaily().getRunStatus();
								runTime=timeEffResponseData.getDaily().getRunEfficiency().getTime();
								runTimeEfficiency=timeEffResponseData.getDaily().getRunEfficiency().getEfficiency();
								runRange=timeEffResponseData.getDaily().getRunEfficiency().getRangeString();
							}
							break;
						}
					}
				}else{
					for(int j=0;j<statusList.size();j++){
						Object[] statusObj=(Object[]) statusList.get(j);
						if(deviceId.equals(statusObj[0].toString())){
							commStatus=StringManagerUtils.stringToInteger(statusObj[1]+"")==1;
							commTime=StringManagerUtils.stringToFloat(statusObj[2]+"");
							commTimeEfficiency=StringManagerUtils.stringToFloat(statusObj[3]+"");
							commRange=StringManagerUtils.CLOBObjectToString(statusObj[4]);
							
							runStatus=StringManagerUtils.stringToInteger(statusObj[5]+"")==1;
							runTime=StringManagerUtils.stringToFloat(statusObj[6]+"");
							runTimeEfficiency=StringManagerUtils.stringToFloat(statusObj[7]+"");
							runRange=StringManagerUtils.CLOBObjectToString(statusObj[8]);
							break;
						}
					}
				}
				
				
				List<String> acqTimeList=new ArrayList<String>();
				List<Integer> commStatusList=new ArrayList<Integer>();
				List<Integer> runStatusList=new ArrayList<Integer>();
				
				List<Float> rpmList=new ArrayList<Float>();
				
				List<Float> theoreticalProductionList=new ArrayList<Float>();
				List<Float> liquidVolumetricProductionList=new ArrayList<Float>();
				List<Float> oilVolumetricProductionList=new ArrayList<Float>();
				List<Float> waterVolumetricProductionList=new ArrayList<Float>();
				List<Float> volumeWaterCutList=new ArrayList<Float>();
				
				List<Float> liquidWeightProductionList=new ArrayList<Float>();
				List<Float> oilWeightProductionList=new ArrayList<Float>();
				List<Float> waterWeightProductionList=new ArrayList<Float>();
				List<Float> weightWaterCutList=new ArrayList<Float>();
				
				List<Float> pumpEffList=new ArrayList<Float>();
				List<Float> pumpEff1List=new ArrayList<Float>();
				List<Float> pumpEff2List=new ArrayList<Float>();
				
				List<Float> systemEfficiencyList=new ArrayList<Float>();
				List<Float> energyPer100mLiftList=new ArrayList<Float>();
				
				List<Float> pumpSettingDepthList=new ArrayList<Float>();
				List<Float> producingfluidLevelList=new ArrayList<Float>();
				List<Float> submergenceList=new ArrayList<Float>();
				
				List<Float> tubingPressureList=new ArrayList<Float>();
				List<Float> casingPressureList=new ArrayList<Float>();
				
				for(int j=0;j<singleResultlist.size();j++){
					Object[] resuleObj=(Object[]) singleResultlist.get(j);
					if(deviceId.toString().equals(resuleObj[0].toString())){
						String productionData=resuleObj[10].toString();
						type = new TypeToken<PCPCalculateRequestData>() {}.getType();
						PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
						
						acqTimeList.add(resuleObj[1]+"");
						commStatusList.add(commStatus?1:0);
						runStatusList.add(runStatus?1:0);
						rpmList.add(StringManagerUtils.stringToFloat(resuleObj[2]+""));
						
						theoreticalProductionList.add(StringManagerUtils.stringToFloat(resuleObj[3]+""));
						liquidVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[4]+""));
						oilVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[5]+""));
						waterVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[6]+""));
						
						if(pcpProductionData!=null&&pcpProductionData.getProduction()!=null){
							volumeWaterCutList.add(pcpProductionData.getProduction().getWaterCut());
						}else{
							volumeWaterCutList.add(0.0f);
						}
						
						
						liquidWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[7]+""));
						oilWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[8]+""));
						waterWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[9]+""));
						if(pcpProductionData!=null&&pcpProductionData.getProduction()!=null){
							weightWaterCutList.add(pcpProductionData.getProduction().getWeightWaterCut());
						}else{
							weightWaterCutList.add(0.0f);
						}
						
						pumpEffList.add(StringManagerUtils.stringToFloat(resuleObj[11]+""));
						pumpEff1List.add(StringManagerUtils.stringToFloat(resuleObj[12]+""));
						pumpEff2List.add(StringManagerUtils.stringToFloat(resuleObj[13]+""));
						
						systemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[14]+""));
						energyPer100mLiftList.add(StringManagerUtils.stringToFloat(resuleObj[15]+""));
						
						if(pcpProductionData!=null&&pcpProductionData.getProduction()!=null){
							tubingPressureList.add(pcpProductionData.getProduction().getTubingPressure());
							casingPressureList.add(pcpProductionData.getProduction().getCasingPressure());
							pumpSettingDepthList.add(pcpProductionData.getProduction().getPumpSettingDepth());
							producingfluidLevelList.add(pcpProductionData.getProduction().getProducingfluidLevel());
						}else{
							tubingPressureList.add(0.0f);
							casingPressureList.add(0.0f);
							pumpSettingDepthList.add(0.0f);
							producingfluidLevelList.add(0.0f);
						}
						submergenceList.add(StringManagerUtils.stringToFloat(resuleObj[16]+""));
					}
				}
				
				dataSbf = new StringBuffer();
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+deviceId+"\",");
				dataSbf.append("\"CurrentCommStatus\":"+(commStatus?1:0)+",");
				dataSbf.append("\"CurrentRunStatus\":"+(runStatus?1:0)+",");
				dataSbf.append("\"Date\":\""+date+"\",");
				dataSbf.append("\"OffsetHour\":"+offsetHour+",");
				dataSbf.append("\"AcqTime\":["+StringManagerUtils.joinStringArr(acqTimeList, ",")+"],");
				dataSbf.append("\"CommStatus\":["+StringUtils.join(commStatusList, ",")+"],");
				dataSbf.append("\"CommTime\":"+commTime+",");
				dataSbf.append("\"CommTimeEfficiency\":"+commTimeEfficiency+",");
				dataSbf.append("\"CommRange\":\""+commRange+"\",");
				dataSbf.append("\"RunStatus\":["+StringUtils.join(runStatusList, ",")+"],");
				dataSbf.append("\"RunTime\":"+runTime+",");
				dataSbf.append("\"RunTimeEfficiency\":"+runTimeEfficiency+",");
				dataSbf.append("\"RunRange\":\""+runRange+"\",");
				dataSbf.append("\"RPM\":["+StringUtils.join(rpmList, ",")+"],");
				dataSbf.append("\"PumpSettingDepth\":["+StringUtils.join(pumpSettingDepthList, ",")+"],");
				dataSbf.append("\"ProducingfluidLevel\":["+StringUtils.join(producingfluidLevelList, ",")+"],");
				dataSbf.append("\"Submergence\":["+StringUtils.join(submergenceList, ",")+"],");
				dataSbf.append("\"TubingPressure\":["+StringUtils.join(tubingPressureList, ",")+"],");
				dataSbf.append("\"CasingPressure\":["+StringUtils.join(casingPressureList, ",")+"],");
				dataSbf.append("\"TheoreticalProduction\":["+StringUtils.join(theoreticalProductionList, ",")+"],");
				dataSbf.append("\"LiquidVolumetricProduction\":["+StringUtils.join(liquidVolumetricProductionList, ",")+"],");
				dataSbf.append("\"OilVolumetricProduction\":["+StringUtils.join(oilVolumetricProductionList, ",")+"],");
				dataSbf.append("\"WaterVolumetricProduction\":["+StringUtils.join(waterVolumetricProductionList, ",")+"],");
				dataSbf.append("\"VolumeWaterCut\":["+StringUtils.join(volumeWaterCutList, ",")+"],");
				dataSbf.append("\"LiquidWeightProduction\":["+StringUtils.join(liquidWeightProductionList, ",")+"],");
				dataSbf.append("\"OilWeightProduction\":["+StringUtils.join(oilWeightProductionList, ",")+"],");
				dataSbf.append("\"WaterWeightProduction\":["+StringUtils.join(waterWeightProductionList, ",")+"],");
				dataSbf.append("\"WeightWaterCut\":["+StringUtils.join(weightWaterCutList, ",")+"],");
				dataSbf.append("\"SystemEfficiency\":["+StringUtils.join(systemEfficiencyList, ",")+"],");
				dataSbf.append("\"EnergyPer100mLift\":["+StringUtils.join(energyPer100mLiftList, ",")+"],");
				dataSbf.append("\"PumpEff\":["+StringUtils.join(pumpEffList, ",")+"],");
				dataSbf.append("\"PumpEff1\":["+StringUtils.join(pumpEff1List, ",")+"],");
				dataSbf.append("\"PumpEff2\":["+StringUtils.join(pumpEff2List, ",")+"]");
				dataSbf.append("}");
				requestDataList.add(dataSbf.toString());
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		return requestDataList;
	}
	
	public List<String> PCPTimingTotalCalculation(String timeStr){
		ThreadPool executor = new ThreadPool("PCPTinmingTotalCalculate",
				Config.getInstance().configFile.getAp().getThreadPool().getTimingTotalCalculate().getCorePoolSize(), 
				Config.getInstance().configFile.getAp().getThreadPool().getTimingTotalCalculate().getMaximumPoolSize(), 
				Config.getInstance().configFile.getAp().getThreadPool().getTimingTotalCalculate().getKeepAliveTime(), 
				TimeUnit.SECONDS, 
				Config.getInstance().configFile.getAp().getThreadPool().getTimingTotalCalculate().getWattingCount());
		
		String sql="select t.id,t.deviceName,t3.singleWellDailyReportTemplate,t2.unitid "
				+ " from tbl_device t "
				+ " left outer join tbl_protocolreportinstance t2 on t.reportinstancecode=t2.code"
				+ " left outer join tbl_report_unit_conf t3 on t2.unitid=t3.id "
				+ " where t.calculateType=2"
				+ " order by t.id";
		List<?> welllist = findCallSql(sql);
		for(int i=0;i<welllist.size();i++){
			try{
				Object[] wellObj=(Object[]) welllist.get(i);
				int deviceId=StringManagerUtils.stringToInteger(wellObj[0]+"");
				String deviceName=wellObj[1]+"";
				String templateCode=(wellObj[2]+"").replaceAll("null", ""); 
				String reportUnitId=wellObj[3]+"";
				TimingTotalCalculateThread thread=new TimingTotalCalculateThread(deviceId, deviceId, deviceName, timeStr, templateCode,
						reportUnitId, 2, commonDataService);
				executor.execute(thread);
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		
		while (!executor.isCompletedByTaskCount()) {
			try {
				Thread.sleep(1000*1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
		
		return null;
	}
	
	public void saveFSDiagramDailyCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException{
		int recordCount=totalAnalysisRequestData.getAcqTime()!=null?totalAnalysisRequestData.getAcqTime().size():0;
		this.getBaseDao().saveFESDiagramTotalCalculateData(totalAnalysisResponseData,totalAnalysisRequestData,tatalDate,recordCount);
	}
	
	public void saveRPMTotalCalculateData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException{
		int recordCount=totalAnalysisRequestData.getAcqTime()!=null?totalAnalysisRequestData.getAcqTime().size():0;
		this.getBaseDao().saveRPMTotalCalculateData(totalAnalysisResponseData,totalAnalysisRequestData,tatalDate,recordCount);
	}
	
	public void saveFSDiagramTimingTotalCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String timeStr) throws SQLException, ParseException{
		int recordCount=totalAnalysisRequestData.getAcqTime()!=null?totalAnalysisRequestData.getAcqTime().size():0;
		this.getBaseDao().saveFSDiagramTimingTotalCalculationData(totalAnalysisResponseData,totalAnalysisRequestData,timeStr,recordCount);
	}
	
	public void saveRPMTimingTotalCalculateData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String timeStr) throws SQLException, ParseException{
		int recordCount=totalAnalysisRequestData.getAcqTime()!=null?totalAnalysisRequestData.getAcqTime().size():0;
		this.getBaseDao().saveRPMTimingTotalCalculateData(totalAnalysisResponseData,totalAnalysisRequestData,timeStr,recordCount);
	}
	
	@SuppressWarnings("unused")
	public void initDailyReportData(int calculateType){
		String deviceTableName="tbl_device";
		String tableName="tbl_dailycalculationdata";
		if(calculateType==1){
			tableName="tbl_pcpdailycalculationdata";
		}else if(calculateType==2){
			tableName="tbl_pcpdailycalculationdata";
		}
		
		
		boolean initResult=this.getBaseDao().initDailyReportData();
		
		String instanceSql="select t.id,t.code,t.unitid,t2.singleWellRangeReportTemplate "
				+ " from tbl_protocolreportinstance t,tbl_report_unit_conf t2 "
				+ " where t.unitid=t2.id ";
		List<?> instanceList = this.findCallSql(instanceSql);
		if(instanceList.size()>0){
			ReportTemplate reportTemplate=MemoryDataManagerTask.getReportTemplateConfig();
			if(reportTemplate!=null && reportTemplate.getSingleWellRangeReportTemplate()!=null && reportTemplate.getSingleWellRangeReportTemplate().size()>0){
				for(int i=0;i<instanceList.size();i++){
					Object[] instanceObj=(Object[]) instanceList.get(i);
					String instanceCode=(instanceObj[1]+"").replaceAll("null", "");
					String unitId=(instanceObj[2]+"").replaceAll("null", "");
					String templateCode=(instanceObj[3]+"").replaceAll("null", "");
					if(StringManagerUtils.isNotNull(templateCode)){
						for(int j=0;j<reportTemplate.getSingleWellRangeReportTemplate().size();j++){
							ReportTemplate.Template template=reportTemplate.getSingleWellRangeReportTemplate().get(j);
							if(templateCode.equalsIgnoreCase(template.getTemplateCode())){
								if(template.getEditable()!=null && template.getEditable().size()>0){
									String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype "
											+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
											+ " where t.unitid="+unitId+" "
											+ " and t.sort>=0"
											+ " and t.reporttype=0"
											+ " order by t.sort";
									List<ReportUnitItem> reportItemList=new ArrayList<ReportUnitItem>();
									List<?> reportItemQuertList = this.findCallSql(reportItemSql);
									
									for(int k=0;k<reportItemQuertList.size();k++){
										Object[] reportItemObj=(Object[]) reportItemQuertList.get(k);
										ReportUnitItem reportUnitItem=new ReportUnitItem();
										reportUnitItem.setItemName(reportItemObj[0]+"");
										reportUnitItem.setItemCode(reportItemObj[1]+"");
										reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2]+""));
										reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3]+""));
										
										
										for(int l=0;l<template.getEditable().size();l++){
											ReportTemplate.Editable editable=template.getEditable().get(l);
											if(editable.getStartRow()>=template.getHeader().size() && reportUnitItem.getSort()-1>=editable.getStartColumn() && reportUnitItem.getSort()-1<=editable.getEndColumn()){//索引起始不同
												reportItemList.add(reportUnitItem);
												break;
											}
										}
									}
									if(reportItemList.size()>0){
										StringBuffer updateColBuff = new StringBuffer();
										for(int m=0;m<reportItemList.size();m++){
											updateColBuff.append(reportItemList.get(m).getItemCode()+",");
										}
										if(updateColBuff.toString().endsWith(",")){
											updateColBuff.deleteCharAt(updateColBuff.length() - 1);
										}
										
										String updateSql="update "+tableName+" t set ("+updateColBuff+")="
												+ " (select "+updateColBuff+" from "+tableName+" t2 "
														+ " where t2.deviceId=t.deviceId and t2.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')-1) "
												+ " where t.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') "
												+ " and t.deviceId in ( select t3.id from "+deviceTableName+" t3 where t3.reportinstancecode='"+instanceCode+"'   )";
										try {
											int r=this.getBaseDao().updateOrDeleteBySql(updateSql);
											System.out.println(updateSql);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											continue;
										}
									}
								}
								break;
							}
						}
					}
				}
			}
		}
	}
}