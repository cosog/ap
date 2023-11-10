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

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.engine.jdbc.SerializableClobProxy;
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
import com.cosog.task.MemoryDataManagerTask;
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
	public void saveAlarmInfo(String wellName,String deviceType,String acqTime,List<AcquisitionItemInfo> acquisitionItemInfoList) throws SQLException{
		if(StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200){
			getBaseDao().saveRPCAlarmInfo(wellName,deviceType,acqTime,acquisitionItemInfoList);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=200&&StringManagerUtils.stringToInteger(deviceType)<300){
			getBaseDao().savePCPAlarmInfo(wellName,deviceType,acqTime,acquisitionItemInfoList);
		}
	}
	
	public void saveAndSendAlarmInfo(int deviceId,String wellName,String deviceType,String acqTime,List<AcquisitionItemInfo> acquisitionItemInfoList) throws SQLException{
		boolean isSendSMS=false;
		boolean isSendMail=false;
		StringBuffer SMSContent = new StringBuffer();
		StringBuffer EMailContent = new StringBuffer();
		SMSContent.append(((StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200)?"抽油机井":"螺杆泵井")+"设备"+wellName+"于"+acqTime+"发生报警:");
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
					String keyIndex=wellName+","+deviceType+","+acquisitionItemInfoList.get(i).getTitle();
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
			if(StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200){
				getBaseDao().saveRPCAlarmInfo(wellName,deviceType,acqTime,saveAcquisitionItemInfoList);
			}else if(StringManagerUtils.stringToInteger(deviceType)>=200&&StringManagerUtils.stringToInteger(deviceType)<300){
				getBaseDao().savePCPAlarmInfo(wellName,deviceType,acqTime,saveAcquisitionItemInfoList);
			}
			
			
		}
		if(isSendSMS || isSendMail){
			sendAlarmSMS(wellName,deviceType,isSendSMS,isSendMail,SMSContent.toString(),EMailContent.toString());
		}
	}
	
	public void sendAlarmSMS(String wellName,String deviceType,boolean isSendSMS,boolean isSendMail,String SMSContent,String EMailContent) throws SQLException{
		String SMSUrl=Config.getInstance().configFile.getAd().getRw().getWriteSMS();
		String deviceTableName="tbl_rpcdevice";
		if(StringManagerUtils.stringToInteger(deviceType)>=100 && StringManagerUtils.stringToInteger(deviceType)<200){//如果是抽油机井
			deviceTableName="tbl_rpcdevice";
		}else if(StringManagerUtils.stringToInteger(deviceType)>=200 && StringManagerUtils.stringToInteger(deviceType)<300){//否则螺杆泵井
			deviceTableName="tbl_pcpdevice";
		}
		
		String userSql="select u.user_id,u.user_phone,u.user_receivesms,u.user_in_email,u.user_receivemail "
				+ " from tbl_user u,tbl_role r "
				+ " where u.user_type=r.role_id and (u.user_orgid in (select org_id from tbl_org t start with org_id=( select t2.orgid from "+deviceTableName+" t2 where t2.wellname='"+wellName+"' and t2.devicetype="+deviceType+" ) connect by prior  org_parent=org_id) or u.user_orgid=0)";
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
			StringManagerUtils.sendEMail(((StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200)?"泵":"管")+"设备"+wellName+"报警", EMailContent, receivingEMailAccount);
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
	
	public List<String> getFSDiagramDailyCalculationRequestData(String tatalDate,String wellId) throws ParseException{
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
		String sql="select t.id,t.wellname from tbl_rpcdevice t where 1=1";
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
				+ " from tbl_rpcacqdata_hist t,tbl_rpcdevice t2 "
				+ " where t.wellid=t2.id "
				+ " and t.fesdiagramacqtime between to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24 and to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 "
				+ " and t.resultstatus=1 ";
		String commStatusSql="select t2.id, t2.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange"
				+ " from tbl_rpcacqdata_hist t,tbl_rpcdevice t2 "
				+ " where t.wellid=t2.id and t.acqTime=( select max(t3.acqTime) from tbl_rpcacqdata_hist t3 where t3.wellid=t.wellid and t3.acqTime between to_date('"+date+"','yyyy-mm-dd') +"+offsetHour+"/24 and  to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 )";
		String runStatusSql="select t2.id, t2.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.runstatus,t.runtimeefficiency,t.runtime,t.runrange "
				+ " from tbl_rpcacqdata_hist t,tbl_rpcdevice t2 "
				+ " where t.wellid=t2.id and t.acqTime=( select max(t3.acqTime) from tbl_rpcacqdata_hist t3 where t3.wellid=t.wellid and t3.commstatus=1 and t3.acqTime between to_date('"+date+"','yyyy-mm-dd') +"+offsetHour+"/24 and  to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 )";
		String totalStatusSql="select t2.id,t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
				+ " from tbl_rpcdailycalculationdata t,tbl_rpcdevice t2 "
				+ " where t.wellid=t2.id "
				+ " and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(wellId)){
			sql+=" and t.id in ("+wellId+")";
			fesDiagramSql+=" and t2.id in ("+wellId+")";
			commStatusSql+=" and t2.id in("+wellId+")";
			runStatusSql+=" and t2.id in("+wellId+")";
			totalStatusSql+=" and t2.id in ("+wellId+")";
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
				String wellName=wellObj[1]+"";
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
									+ "\"WellName\":\""+wellName+"\","
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
									+ "\"WellName\":\""+wellName+"\","
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
	
	public List<String> RPCTimingTotalCalculation(String timeStr){
//		timeStr="2023-10-17 08:00:00";
		int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
		int interval = Config.getInstance().configFile.getAp().getReport().getInterval();
		String date=timeStr.split(" ")[0];
		if(!StringManagerUtils.timeMatchDate(timeStr, date, offsetHour)){
			date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(date),-1);
		}
		CommResponseData.Range range= StringManagerUtils.getTimeRange(date,offsetHour);
		Gson gson = new Gson();
		java.lang.reflect.Type type=new TypeToken<TotalAnalysisRequestData>() {}.getType();
		
		StringBuffer dataSbf=null;
		String sql="select t.id,t.wellname,t3.singleWellDailyReportTemplate,t2.unitid from tbl_rpcdevice t "
				+ " left outer join tbl_protocolreportinstance t2 on t.reportinstancecode=t2.code"
				+ " left outer join tbl_report_unit_conf t3 on t2.unitid=t3.id "
				+ " where 1=1";
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
				+ " from tbl_rpcacqdata_hist t,tbl_rpcdevice t2 "
				+ " where t.wellid=t2.id "
				+ " and t.fesdiagramacqtime between to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24 and to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') "
				+ " and t.resultstatus=1 ";
		
		String labelInfoSql="select t.wellid, t.headerlabelinfo from tbl_rpctimingcalculationdata t "
				+ " where t.caltime=( select max(t2.caltime) from tbl_rpctimingcalculationdata t2 where t2.wellid=t.wellid and t2.headerLabelInfo is not null)";
		
		sql+=" order by t.id";
		fesDiagramSql+= " order by t2.id,t.fesdiagramacqtime";
		List<?> welllist = findCallSql(sql);
		List<?> singleresultlist = findCallSql(fesDiagramSql);
		List<?> labelInfoQueryList=findCallSql(labelInfoSql);
		for(int i=0;i<welllist.size();i++){
			try{
				int recordCount=0;
				Object[] wellObj=(Object[]) welllist.get(i);
				String deviceId=wellObj[0]+"";
				String wellName=wellObj[1]+"";
				String templateCode=(wellObj[2]+"").replaceAll("null", ""); 
				String reportUnitId=wellObj[3]+"";
				
				String historyCommStatusSql="select t.id,t.wellid,t2.wellname,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
						+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange"
						+ " from tbl_rpcacqdata_hist t,tbl_rpcdevice t2 "
						+ " where t.wellid=t2.id "
						+ " and t.acqtime=("
						+ " select max(t3.acqtime) from  tbl_rpcacqdata_hist t3  "
						+ " where t3.wellid="+deviceId+" and t3.acqtime<=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss')"
						+ " )"
						+ " and t.wellid="+deviceId;
				
				String historyRunStatusSql="select t.id,t.wellid,t2.wellname,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
						+ " t.runstatus,t.runtimeefficiency,t.runtime,t.runrange"
						+ " from tbl_rpcacqdata_hist t,tbl_rpcdevice t2 "
						+ " where t.wellid=t2.id "
						+ " and t.acqtime=("
						+ " select max(t3.acqtime) from  tbl_rpcacqdata_hist t3  "
						+ " where t3.commstatus=1 and t3.runstatus in (0,1) and t3.acqtime<=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') and t3.wellid="+deviceId
						+ " )"
						+ " and t.wellid="+deviceId;
				
				String historyEnergyStatusSql="select t.id,t.wellid,t2.wellname,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
						+ " t.totalkwatth,t.todaykwatth"
						+ " from tbl_rpcacqdata_hist t,tbl_rpcdevice t2 "
						+ " where t.wellid=t2.id "
						+ " and t.acqtime=("
						+ " select max(t3.acqtime) from  tbl_rpcacqdata_hist t3  "
						+ " where t3.commstatus=1 and t3.totalkwatth>0 and t3.acqtime<=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') and t3.wellid="+deviceId
						+ " )"
						+ " and t.wellid="+deviceId;
				
				String historyGasStatusSql="select t.id,t.wellid,t2.wellname,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
						+ " t.totalgasvolumetricproduction,t.gasvolumetricproduction"
						+ " from tbl_rpcacqdata_hist t,tbl_rpcdevice t2 "
						+ " where t.wellid=t2.id "
						+ " and t.acqtime=("
						+ " select max(t3.acqtime) from  tbl_rpcacqdata_hist t3  "
						+ " where t3.commstatus=1 and t3.totalgasvolumetricproduction>0 and t3.acqtime<=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') and t3.wellid="+deviceId
						+ " )"
						+ " and t.wellid="+deviceId;
				
				String historyWaterStatusSql="select t.id,t.wellid,t2.wellname,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
						+ " t.totalwatervolumetricproduction,t.watervolumetricproduction "
						+ " from tbl_rpcacqdata_hist t,tbl_rpcdevice t2 "
						+ " where t.wellid=t2.id "
						+ " and t.acqtime=("
						+ " select max(t3.acqtime) from  tbl_rpcacqdata_hist t3  "
						+ " where t3.commstatus=1 and t3.totalwatervolumetricproduction>0 and t3.acqtime<=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') and t3.wellid="+deviceId
						+ " )"
						+ " and t.wellid="+deviceId;
				
				String updateRealtimeAcqProdSql="update tbl_rpctimingcalculationdata t set "
						+ " (t.realtimewatervolumetricproduction,t.realtimegasvolumetricproduction) "
						+" =( select t2.realtimewatervolumetricproduction,t2.realtimegasvolumetricproduction"
						+ " from tbl_rpcacqdata_hist t2 "
						+ " where t2.acqtime=("
						+ " select max(t3.acqtime) from  tbl_rpcacqdata_hist t3  "
						+ " where t3.commstatus=1 and t3.realtimewatervolumetricproduction is not null and t3.acqtime<=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') and t3.wellid="+deviceId
						+ " )"
						+ " and t2.wellid="+deviceId+" )"
						+" where t.wellid="+deviceId+" and t.caltime=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss')";
				
				String updateRealtimeCalDataSql="update tbl_rpctimingcalculationdata t set "
						+ " ("
						+ " t.resultcode,t.stroke,t.spm,t.fmax,t.fmin,t.fullnesscoefficient,"
						+ " t.theoreticalproduction,"
						+ " t.realtimeliquidvolumetricproduction,t.realtimeoilvolumetricproduction,t.realtimewatervolumetricproduction,"
						+ " t.realtimeliquidweightproduction,t.realtimeoilweightproduction,t.realtimewaterweightproduction,"
						+ " t.pumpeff,t.pumpeff1,t.pumpeff2,t.pumpeff3,t.pumpeff4,"
						+ " t.wattdegreebalance,t.idegreebalance,t.deltaradius,"
						+ " t.surfacesystemefficiency,t.welldownsystemefficiency,t.systemefficiency,t.energyper100mlift,"
						+ " t.calcProducingfluidLevel,t.levelDifferenceValue,"
						+ " t.submergence,"
						+ " t.rpm"
						+ " ) "
						+" =( "
						+ " select "
						+ " t2.resultcode,t2.stroke,t2.spm,t2.fmax,t2.fmin,t2.fullnesscoefficient,"
						+ " t2.theoreticalproduction,"
						+ " t2.realtimeliquidvolumetricproduction,t2.realtimeoilvolumetricproduction,t2.realtimewatervolumetricproduction,"
						+ " t2.realtimeliquidweightproduction,t2.realtimeoilweightproduction,t2.realtimewaterweightproduction,"
						+ " t2.pumpeff,t2.pumpeff1,t2.pumpeff2,t2.pumpeff3,t2.pumpeff4,"
						+ " t2.wattdegreebalance,t2.idegreebalance,t2.deltaradius,"
						+ " t2.surfacesystemefficiency,t2.welldownsystemefficiency,t2.systemefficiency,t2.energyper100mlift,"
						+ " t2.calcProducingfluidLevel,t2.levelDifferenceValue,"
						+ " t2.submergence,"
						+ " t2.rpm"
						+ " from tbl_rpcacqdata_hist t2 "
						+ " where t2.acqtime=("
						+ " select max(t3.acqtime) from  tbl_rpcacqdata_hist t3  "
						+ " where t3.commstatus=1 and t3.resultstatus=1 and t3.acqtime<=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') and t3.wellid="+deviceId
						+ " )"
						+ " and t2.wellid="+deviceId+" )"
						+" where t.wellid="+deviceId+" and t.caltime=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss')";
				TimeEffResponseData timeEffResponseData=null;
				CommResponseData commResponseData=null;
				EnergyCalculateResponseData energyCalculateResponseData=null;
				EnergyCalculateResponseData totalGasCalculateResponseData=null;
				EnergyCalculateResponseData totalWaterCalculateResponseData=null;
				
				String lastRunTime="";
				String lastCommTime="";
				
				String lastEnergyTime="";
				String lastGasTime="";
				String lastWaterTime="";
				
				int commStatus=0;
				float commTime=0;
				float commTimeEfficiency=0;
				String commRange="";
				
				int runStatus=0;
				float runTime=0;
				float runTimeEfficiency=0;
				String runRange="";
				
				float totalkwatth=0,todaykwatth=0;
				float totalgasvolumetricproduction=0,gasvolumetricproduction=0;
				float totalwatervolumetricproduction=0,watervolumetricproduction=0;
				
				boolean isAcqEnergy=false,isAcqTotalGasProd=false,isAcqTotalWaterProd=false;
				
				String labelInfo="";
				ReportTemplate.Template template=null;
				
				//继承表头信息
				for(int j=0;j<labelInfoQueryList.size();j++){
					Object[] labelInfoObj=(Object[]) labelInfoQueryList.get(j);
					if(deviceId.equals(labelInfoObj[0].toString())){
						labelInfo=labelInfoObj[1]+"";
						break;
					}
				}
				String recordCountSql="select count(1) from tbl_rpctimingcalculationdata t  where t.wellid="+deviceId+" and t.caltime=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss')";
				List<?> recordCountList = this.findCallSql(recordCountSql);
				if(recordCountList.size()>0){
					recordCount=StringManagerUtils.stringToInteger(recordCountList.get(0)+"");
				}
				
				String insertHistSql="insert into tbl_rpctimingcalculationdata (wellid,caltime,stroke,spm,tubingpressure,casingpressure,producingfluidlevel,bottomholepressure)"
						+ " select "+deviceId+",to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss'),t2.stroke,t2.spm,t2.tubingpressure,t2.casingpressure,t2.producingfluidlevel,t2.bottomholepressure"
						+ " from TBL_RPCDAILYCALCULATIONDATA t2 "
						+ " where t2.caldate=to_date('"+date+"','yyyy-mm-dd') "
						+ " and t2.wellid="+deviceId+""
						+ " and rownum=1";
				String insertHistSql2="insert into tbl_rpctimingcalculationdata (wellid,caltime)values("+deviceId+",to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss'))";
				String updateSql="update tbl_rpctimingcalculationdata t set t.headerlabelinfo='"+labelInfo+"'"; 
				if(recordCount==0){
					try {
						int r=this.getBaseDao().updateOrDeleteBySql(insertHistSql);
						if(r==0){
							r=this.getBaseDao().updateOrDeleteBySql(insertHistSql2);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				try {
					int r=this.getBaseDao().updateOrDeleteBySql(updateRealtimeAcqProdSql);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//报表继承可编辑数据
				if(StringManagerUtils.isNotNull(templateCode)){
					template=MemoryDataManagerTask.getSingleWellDailyReportTemplateByCode(templateCode);
				}
				if(template!=null){
					if(template.getEditable()!=null && template.getEditable().size()>0){
						String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype "
								+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
								+ " where t.unitid="+reportUnitId+" "
								+ " and t.sort>=0"
								+ " and t.reporttype=2"
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
							
							String updateEditDataSql="update tbl_rpctimingcalculationdata t set ("+updateColBuff+")="
									+ " (select "+updateColBuff+" from tbl_rpctimingcalculationdata t2 "
											+ " where t2.wellid=t.wellid "
											+ " and t2.caltime=(select max(caltime) from tbl_rpctimingcalculationdata t3 where t3.wellid=t2.wellid and t3.caltime<to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') )"
											+ " and rownum=1"
										+ ") "
									+ " where t.wellid="+deviceId
									+ " and t.caltime=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') ";
							try {
								int r=this.getBaseDao().updateOrDeleteBySql(updateEditDataSql);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				

				List<?> historyCommStatusQueryList=findCallSql(historyCommStatusSql);
				List<?> historyRunStatusQueryList=findCallSql(historyRunStatusSql);
				List<?> historyEnergyStatusQueryList=findCallSql(historyEnergyStatusSql);
				List<?> historyGasStatusQueryList=findCallSql(historyGasStatusSql);
				List<?> historyWaterStatusQueryList=findCallSql(historyWaterStatusSql);
				if(historyCommStatusQueryList.size()>0){
					Object[] historyCommStatusObj=(Object[]) historyCommStatusQueryList.get(0);
					lastCommTime=historyCommStatusObj[3]+"";
					commStatus=StringManagerUtils.stringToInteger(historyCommStatusObj[4]+"");
					commTimeEfficiency=StringManagerUtils.stringToFloat(historyCommStatusObj[5]+"");
					commTime=StringManagerUtils.stringToFloat(historyCommStatusObj[6]+"");
					commRange=StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(historyCommStatusObj[7]));
				}
				
				if(historyRunStatusQueryList.size()>0){
					Object[] historyRunStatuObj=(Object[]) historyRunStatusQueryList.get(0);
					lastRunTime=historyRunStatuObj[3]+"";
					runStatus=StringManagerUtils.stringToInteger(historyRunStatuObj[4]+"");
					runTimeEfficiency=StringManagerUtils.stringToFloat(historyRunStatuObj[5]+"");
					runTime=StringManagerUtils.stringToFloat(historyRunStatuObj[6]+"");
					runRange=StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(historyRunStatuObj[7]));
				}
				
				if(historyEnergyStatusQueryList.size()>0){
					Object[] historyEnergyStatuObj=(Object[]) historyEnergyStatusQueryList.get(0);
					lastEnergyTime=historyEnergyStatuObj[3]+"";
					if(historyEnergyStatuObj[4]!=null){
						isAcqEnergy=true;
					}
					totalkwatth=StringManagerUtils.stringToFloat(historyEnergyStatuObj[4]+"");
					todaykwatth=StringManagerUtils.stringToFloat(historyEnergyStatuObj[5]+"");
				}
				
				if(historyGasStatusQueryList.size()>0){
					Object[] historyGasStatuObj=(Object[]) historyGasStatusQueryList.get(0);
					lastGasTime=historyGasStatuObj[3]+"";
					if(historyGasStatuObj[4]!=null){
						isAcqTotalGasProd=true;
					}
					totalgasvolumetricproduction=StringManagerUtils.stringToFloat(historyGasStatuObj[4]+"");
					gasvolumetricproduction=StringManagerUtils.stringToFloat(historyGasStatuObj[5]+"");
				}
				
				if(historyWaterStatusQueryList.size()>0){
					Object[] historyWaterStatuObj=(Object[]) historyWaterStatusQueryList.get(0);
					lastWaterTime=historyWaterStatuObj[3]+"";
					if(historyWaterStatuObj[4]!=null){
						isAcqTotalWaterProd=true;
					}
					totalwatervolumetricproduction=StringManagerUtils.stringToFloat(historyWaterStatuObj[4]+"");
					watervolumetricproduction=StringManagerUtils.stringToFloat(historyWaterStatuObj[5]+"");
				}
				
				String commTotalRequestData="{"
						+ "\"AKString\":\"\","
						+ "\"WellName\":\""+wellName+"\","
						+ "\"OffsetHour\":"+offsetHour+","
						+ "\"Last\":{"
						+ "\"AcqTime\": \""+lastCommTime+"\","
						+ "\"CommStatus\": "+(commStatus>=1)+","
						+ "\"CommEfficiency\": {"
						+ "\"Efficiency\": "+commTimeEfficiency+","
						+ "\"Time\": "+commTime+","
						+ "\"Range\": "+commRange+""
						+ "}"
						+ "},"
						+ "\"Current\": {"
						+ "\"AcqTime\":\""+timeStr+"\","
						+ "\"CommStatus\":"+(commStatus>=1)+""
						+ "}"
						+ "}";
				commResponseData=CalculateUtils.commCalculate(commTotalRequestData);
				
				updateSql+=",CommStatus="+commStatus;
				if(commResponseData!=null&&commResponseData.getResultStatus()==1){
					if(timeStr.equalsIgnoreCase(range.getEndTime()) && commResponseData.getDaily()!=null && StringManagerUtils.isNotNull(commResponseData.getDaily().getDate()) ){
						commTime=commResponseData.getDaily().getCommEfficiency().getTime();
						commTimeEfficiency=commResponseData.getDaily().getCommEfficiency().getEfficiency();
						commRange=commResponseData.getDaily().getCommEfficiency().getRangeString();
					}else{
						commTime=commResponseData.getCurrent().getCommEfficiency().getTime();
						commTimeEfficiency=commResponseData.getCurrent().getCommEfficiency().getEfficiency();
						commRange=commResponseData.getCurrent().getCommEfficiency().getRangeString();
					}
					updateSql+=",commTimeEfficiency="+commTimeEfficiency+",commTime="+commTime;
				}
				
				String runTotalRequestData="{"
						+ "\"AKString\":\"\","
						+ "\"WellName\":\""+wellName+"\","
						+ "\"OffsetHour\":"+offsetHour+","
						+ "\"Last\":{"
						+ "\"AcqTime\": \""+lastRunTime+"\","
						+ "\"RunStatus\": "+(runStatus>=1)+","
						+ "\"RunEfficiency\": {"
						+ "\"Efficiency\": "+runTimeEfficiency+","
						+ "\"Time\": "+runTime+","
						+ "\"Range\": "+runRange+""
						+ "}"
						+ "},"
						+ "\"Current\": {"
						+ "\"AcqTime\":\""+timeStr+"\","
						+ "\"RunStatus\":"+(runStatus>=1)+""
						+ "}"
						+ "}";
				timeEffResponseData=CalculateUtils.runCalculate(runTotalRequestData);
				updateSql+=",runStatus="+runStatus;
				if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
					if(timeStr.equalsIgnoreCase(range.getEndTime()) && timeEffResponseData.getDaily()!=null && StringManagerUtils.isNotNull(timeEffResponseData.getDaily().getDate()) ){
						runTime=timeEffResponseData.getDaily().getRunEfficiency().getTime();
						runTimeEfficiency=timeEffResponseData.getDaily().getRunEfficiency().getEfficiency();
						runRange=timeEffResponseData.getDaily().getRunEfficiency().getRangeString();
					}else{
						runTime=timeEffResponseData.getCurrent().getRunEfficiency().getTime();
						runTimeEfficiency=timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency();
						runRange=timeEffResponseData.getCurrent().getRunEfficiency().getRangeString();
					}
					updateSql+=",runTimeEfficiency="+runTimeEfficiency+",runTime="+runTime;
				}
				
				//判断是否采集了电量，如采集则进行电量计算
				if(isAcqEnergy){
					String energyRequest="{"
							+ "\"AKString\":\"\","
							+ "\"WellName\":\""+wellName+"\","
							+ "\"OffsetHour\":"+offsetHour+",";
					energyRequest+= "\"Last\":{"
							+ "\"AcqTime\": \""+lastEnergyTime+"\","
							+ "\"Total\":{"
							+ "\"KWattH\":"+totalkwatth
							+ "},\"Today\":{"
							+ "\"KWattH\":"+todaykwatth
							+ "}"
							+ "},";
					energyRequest+= "\"Current\": {"
							+ "\"AcqTime\":\""+timeStr+"\","
							+ "\"Total\":{"
							+ "\"KWattH\":"+totalkwatth
							+ "}"
							+ "}"
							+ "}";
					energyCalculateResponseData=CalculateUtils.energyCalculate(energyRequest);
					updateSql+=",totalKWattH="+totalkwatth;
					if(energyCalculateResponseData!=null&&energyCalculateResponseData.getResultStatus()==1){
						if(timeStr.equalsIgnoreCase(range.getEndTime()) && energyCalculateResponseData.getDaily()!=null && StringManagerUtils.isNotNull(energyCalculateResponseData.getDaily().getDate()) ){
							updateSql+=",todayKWattH="+energyCalculateResponseData.getDaily().getKWattH();
						}else{
							updateSql+=",todayKWattH="+energyCalculateResponseData.getCurrent().getToday().getKWattH();
						}
					}
				}
				
				//判断是否采集了累计气量，如采集则进行日产气量计算
				if(isAcqTotalGasProd){
					String energyRequest="{"
							+ "\"AKString\":\"\","
							+ "\"WellName\":\""+wellName+"\","
							+ "\"OffsetHour\":"+offsetHour+",";
					energyRequest+= "\"Last\":{"
							+ "\"AcqTime\": \""+lastGasTime+"\","
							+ "\"Total\":{"
							+ "\"KWattH\":"+totalgasvolumetricproduction
							+ "},\"Today\":{"
							+ "\"KWattH\":"+gasvolumetricproduction
							+ "}"
							+ "},";
					energyRequest+= "\"Current\": {"
							+ "\"AcqTime\":\""+timeStr+"\","
							+ "\"Total\":{"
							+ "\"KWattH\":"+totalgasvolumetricproduction
							+ "}"
							+ "}"
							+ "}";
					updateSql+=",totalgasvolumetricproduction="+totalgasvolumetricproduction;
					totalGasCalculateResponseData=CalculateUtils.energyCalculate(energyRequest);
					if(totalGasCalculateResponseData!=null&&totalGasCalculateResponseData.getResultStatus()==1){
						if(timeStr.equalsIgnoreCase(range.getEndTime()) && totalGasCalculateResponseData.getDaily()!=null && StringManagerUtils.isNotNull(totalGasCalculateResponseData.getDaily().getDate()) ){
							updateSql+=",gasvolumetricproduction="+totalGasCalculateResponseData.getDaily().getKWattH();
						}else{
							updateSql+=",gasvolumetricproduction="+totalGasCalculateResponseData.getCurrent().getToday().getKWattH();
						}
					}
				}
				
				//判断是否采集了累计水量，如采集则进行日产水量计算
				if(isAcqTotalWaterProd){
					String energyRequest="{"
							+ "\"AKString\":\"\","
							+ "\"WellName\":\""+wellName+"\","
							+ "\"OffsetHour\":"+offsetHour+",";
					energyRequest+= "\"Last\":{"
							+ "\"AcqTime\": \""+lastWaterTime+"\","
							+ "\"Total\":{"
							+ "\"KWattH\":"+totalwatervolumetricproduction
							+ "},\"Today\":{"
							+ "\"KWattH\":"+watervolumetricproduction
							+ "}"
							+ "},";
					energyRequest+= "\"Current\": {"
							+ "\"AcqTime\":\""+timeStr+"\","
							+ "\"Total\":{"
							+ "\"KWattH\":"+totalwatervolumetricproduction
							+ "}"
							+ "}"
							+ "}";
					updateSql+=",totalWatervolumetricproduction="+totalwatervolumetricproduction;
					totalWaterCalculateResponseData=CalculateUtils.energyCalculate(energyRequest);
					if(totalWaterCalculateResponseData!=null&&totalWaterCalculateResponseData.getResultStatus()==1){
						if(timeStr.equalsIgnoreCase(range.getEndTime()) && totalWaterCalculateResponseData.getDaily()!=null && StringManagerUtils.isNotNull(totalWaterCalculateResponseData.getDaily().getDate()) ){
							updateSql+=",Watervolumetricproduction="+totalWaterCalculateResponseData.getDaily().getKWattH();
						}else{
							updateSql+=",Watervolumetricproduction="+totalWaterCalculateResponseData.getCurrent().getToday().getKWattH();
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
						commStatusList.add(commStatus>=1?1:0);
						runStatusList.add(runStatus>=1?1:0);
					
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
				dataSbf.append("\"CurrentCommStatus\":"+(commStatus>=1?1:0)+",");
				dataSbf.append("\"CurrentRunStatus\":"+(runStatus>=1?1:0)+",");
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
				
				TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(dataSbf.toString(), new TypeToken<TotalAnalysisRequestData>() {}.getType());
				TotalAnalysisResponseData totalAnalysisResponseData=CalculateUtils.totalCalculate(dataSbf.toString());
				updateSql+=" where t.wellid="+deviceId+" and t.caltime=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss')";
				try {
					int r=this.getBaseDao().updateOrDeleteBySql(updateSql);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(commResponseData!=null&&commResponseData.getResultStatus()==1){
					List<String> clobCont=new ArrayList<String>();
					String updateHisRangeClobSql="update tbl_rpctimingcalculationdata t set t.commrange=?";
					clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
					if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
						updateHisRangeClobSql+=", t.runrange=?";
						clobCont.add(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());
					}
					updateHisRangeClobSql+=" where t.wellid="+deviceId +" and t.caltime="+"to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss')";
					try {
						int r=this.getBaseDao().executeSqlUpdateClob(updateHisRangeClobSql,clobCont);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					this.saveFSDiagramTimingTotalCalculationData(totalAnalysisResponseData,totalAnalysisRequestData,timeStr);
					
					if((totalAnalysisRequestData.getAcqTime()!=null?totalAnalysisRequestData.getAcqTime().size():0)>0){
						try {
							int r=this.getBaseDao().updateOrDeleteBySql(updateRealtimeCalDataSql);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		return null;
	}
	
	public List<String> getRPMDailyCalculationRequestData(String tatalDate,String wellId) throws ParseException{
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
		String sql="select t.id,t.wellname from tbl_pcpdevice t ";
		String rpmSql="select t2.id, "
				+ "to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss'),t.rpm,"
				+ "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
				+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
				+ "t.productiondata,"
				+ "t.pumpeff,t.pumpeff1,t.pumpeff2,"
				+ "t.systemefficiency,t.energyper100mlift,"
				+ "t.submergence "
				+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2 "
				+ " where t.wellid=t2.id "
				+ " and t.acqtime between to_date('"+date+"','yyyy-mm-dd') +"+offsetHour+"/24 and to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 "
				+ " and t.resultstatus=1 ";
		String commStatusSql="select t2.id, t2.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange,"
				+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2 "
				+ " where t.wellid=t2.id and t.acqTime=( select max(t3.acqTime) from tbl_pcpacqdata_hist t3 where t3.wellid=t.wellid and t3.acqTime between to_date('"+date+"','yyyy-mm-dd') +"+offsetHour+"/24 and  to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 )";
		
		String runStatusSql="select t2.id, t2.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.runstatus,t.runtimeefficiency,t.runtime,t.runrange "
				+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2 "
				+ " where t.wellid=t2.id and t.acqTime=( select max(t3.acqTime) from tbl_pcpacqdata_hist t3 where t3.wellid=t.wellid and t3.commstatus=1 and t3.acqTime between to_date('"+date+"','yyyy-mm-dd') +"+offsetHour+"/24 and  to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 )";
		
		String totalStatusSql="select t2.id,t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
				+ " from tbl_pcpdailycalculationdata t,tbl_pcpdevice t2 "
				+ " where t.wellid=t2.id "
				+ " and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(wellId)){
			sql+=" and t.id in ("+wellId+")";
			rpmSql+=" and t2.id in ("+wellId+")";
			commStatusSql+=" and t2.id in("+wellId+")";
			runStatusSql+=" and t2.id in("+wellId+")";
			totalStatusSql+=" and t2.id in ("+wellId+")";
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
				String wellName=wellObj[1]+"";
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
									+ "\"WellName\":\""+wellName+"\","
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
									+ "\"WellName\":\""+wellName+"\","
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
		String date=timeStr.split(" ")[0];
		int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
		int interval = Config.getInstance().configFile.getAp().getReport().getInterval();
		if(!StringManagerUtils.timeMatchDate(timeStr, date, Config.getInstance().configFile.getAp().getReport().getOffsetHour())){
			date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(date),-1);
		}
		CommResponseData.Range range= StringManagerUtils.getTimeRange(date,offsetHour);
		Gson gson = new Gson();
		java.lang.reflect.Type type=new TypeToken<TotalAnalysisRequestData>() {}.getType();
		
		StringBuffer dataSbf=null;
		String sql="select t.id,t.wellname,t3.singleWellDailyReportTemplate,t2.unitid "
				+ " from tbl_pcpdevice t "
				+ " left outer join tbl_protocolreportinstance t2 on t.reportinstancecode=t2.code"
				+ " left outer join tbl_report_unit_conf t3 on t2.unitid=t3.id "
				+ " where 1=1";
		String rpmSql="select t2.id, "
				+ "to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss'),t.rpm,"
				+ "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
				+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
				+ "t.productiondata,"
				+ "t.pumpeff,t.pumpeff1,t.pumpeff2,"
				+ "t.systemefficiency,t.energyper100mlift,"
				+ "t.submergence "
				+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2 "
				+ " where t.wellid=t2.id "
				+ " and t.acqtime between to_date('"+date+"','yyyy-mm-dd') +"+offsetHour+"/24 and to_date('"+date+"','yyyy-mm-dd')+"+offsetHour+"/24+1 "
				+ " and t.resultstatus=1 ";
		String labelInfoSql="select t.wellid, t.headerlabelinfo from tbl_pcptimingcalculationdata t "
				+ " where t.caltime=( select max(t2.caltime) from tbl_pcptimingcalculationdata t2 where t2.wellid=t.wellid and t2.headerLabelInfo is not null)";
		
		sql+=" order by t.id";
		rpmSql+= " order by t2.id,t.acqtime";
		List<?> welllist = findCallSql(sql);
		List<?> singleresultlist = findCallSql(rpmSql);
		List<?> labelInfoQueryList=findCallSql(labelInfoSql);
		
		for(int i=0;i<welllist.size();i++){
			try{
				int recordCount=0;
				Object[] wellObj=(Object[]) welllist.get(i);
				String deviceId=wellObj[0]+"";
				String wellName=wellObj[1]+"";
				String templateCode=(wellObj[2]+"").replaceAll("null", ""); 
				String reportUnitId=wellObj[3]+"";
				TimeEffResponseData timeEffResponseData=null;
				CommResponseData commResponseData=null;
				EnergyCalculateResponseData energyCalculateResponseData=null;
				EnergyCalculateResponseData totalGasCalculateResponseData=null;
				EnergyCalculateResponseData totalWaterCalculateResponseData=null;
				
				String lastRunTime="";
				String lastCommTime="";
				
				String lastEnergyTime="";
				String lastGasTime="";
				String lastWaterTime="";
				
				int commStatus=0;
				float commTime=0;
				float commTimeEfficiency=0;
				String commRange="";
				
				int runStatus=0;
				float runTime=0;
				float runTimeEfficiency=0;
				String runRange="";
				
				float totalkwatth=0,todaykwatth=0;
				float totalgasvolumetricproduction=0,gasvolumetricproduction=0;
				float totalwatervolumetricproduction=0,watervolumetricproduction=0;
				
				boolean isAcqEnergy=false,isAcqTotalGasProd=false,isAcqTotalWaterProd=false;
				
				String labelInfo="";
				ReportTemplate.Template template=null;
				
				String historyCommStatusSql="select t.id,t.wellid,t2.wellname,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
						+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange"
						+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2 "
						+ " where t.wellid=t2.id "
						+ " and t.acqtime=("
						+ " select max(t3.acqtime) from  tbl_pcpacqdata_hist t3  "
						+ " where t3.wellid="+deviceId+" and t3.acqtime<=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss')"
						+ " )"
						+ " and t.wellid="+deviceId;
				
				String historyRunStatusSql="select t.id,t.wellid,t2.wellname,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
						+ " t.runstatus,t.runtimeefficiency,t.runtime,t.runrange"
						+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2 "
						+ " where t.wellid=t2.id "
						+ " and t.acqtime=("
						+ " select max(t3.acqtime) from  tbl_pcpacqdata_hist t3  "
						+ " where t3.commstatus=1 and t3.runstatus in (0,1) and t3.acqtime<=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') and t3.wellid="+deviceId
						+ " )"
						+ " and t.wellid="+deviceId;
				
				String historyEnergyStatusSql="select t.id,t.wellid,t2.wellname,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
						+ " t.totalkwatth,t.todaykwatth"
						+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2 "
						+ " where t.wellid=t2.id "
						+ " and t.acqtime=("
						+ " select max(t3.acqtime) from  tbl_pcpacqdata_hist t3  "
						+ " where t3.commstatus=1 and t3.totalkwatth>0 and t3.acqtime<=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') and t3.wellid="+deviceId
						+ " )"
						+ " and t.wellid="+deviceId;
				
				String historyGasStatusSql="select t.id,t.wellid,t2.wellname,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
						+ " t.totalgasvolumetricproduction,t.gasvolumetricproduction"
						+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2 "
						+ " where t.wellid=t2.id "
						+ " and t.acqtime=("
						+ " select max(t3.acqtime) from  tbl_pcpacqdata_hist t3  "
						+ " where t3.commstatus=1 and t3.totalgasvolumetricproduction>0 and t3.acqtime<=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') and t3.wellid="+deviceId
						+ " )"
						+ " and t.wellid="+deviceId;
				
				String historyWaterStatusSql="select t.id,t.wellid,t2.wellname,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
						+ " t.totalwatervolumetricproduction,t.watervolumetricproduction "
						+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2 "
						+ " where t.wellid=t2.id "
						+ " and t.acqtime=("
						+ " select max(t3.acqtime) from  tbl_pcpacqdata_hist t3  "
						+ " where t3.commstatus=1 and t3.totalwatervolumetricproduction>0 and t3.acqtime<=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') and t3.wellid="+deviceId
						+ " )"
						+ " and t.wellid="+deviceId;
				
				String updateRealtimeAcqProdSql="update tbl_pcptimingcalculationdata t set "
						+ " (t.realtimewatervolumetricproduction,t.realtimegasvolumetricproduction) "
						+" =( select t2.realtimewatervolumetricproduction,t2.realtimegasvolumetricproduction"
						+ " from tbl_pcpacqdata_hist t2 "
						+ " where t2.acqtime=("
						+ " select max(t3.acqtime) from  tbl_pcpacqdata_hist t3  "
						+ " where t3.commstatus=1 and t3.realtimewatervolumetricproduction is not null and t3.acqtime<=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') and t3.wellid="+deviceId
						+ " )"
						+ " and t2.wellid="+deviceId+" )"
						+" where t.wellid="+deviceId+" and t.caltime=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss')";
				
				String updateRealtimeCalDataSql="update tbl_pcptimingcalculationdata t set "
						+ " ("
						+ " t.theoreticalproduction,"
						+ " t.realtimeliquidvolumetricproduction,t.realtimeoilvolumetricproduction,t.realtimewatervolumetricproduction,"
						+ " t.realtimeliquidweightproduction,t.realtimeoilweightproduction,t.realtimewaterweightproduction,"
						+ " t.pumpeff,t.pumpeff1,t.pumpeff2,"
						+ " t.systemefficiency,t.energyper100mlift,"
						+ " t.submergence,"
						+ " t.rpm"
						+ " ) "
						+" =( "
						+ " select "
						+ " t2.theoreticalproduction,"
						+ " t2.realtimeliquidvolumetricproduction,t2.realtimeoilvolumetricproduction,t2.realtimewatervolumetricproduction,"
						+ " t2.realtimeliquidweightproduction,t2.realtimeoilweightproduction,t2.realtimewaterweightproduction,"
						+ " t2.pumpeff,t2.pumpeff1,t2.pumpeff2,"
						+ " t2.systemefficiency,t2.energyper100mlift,"
						+ " t2.submergence,"
						+ " t2.rpm"
						+ " from tbl_pcpacqdata_hist t2 "
						+ " where t2.acqtime=("
						+ " select max(t3.acqtime) from  tbl_pcpacqdata_hist t3  "
						+ " where t3.commstatus=1 and t3.resultstatus=1 and t3.acqtime<=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') and t3.wellid="+deviceId
						+ " )"
						+ " and t2.wellid="+deviceId+" )"
						+" where t.wellid="+deviceId+" and t.caltime=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss')";
				//继承表头信息
				for(int j=0;j<labelInfoQueryList.size();j++){
					Object[] labelInfoObj=(Object[]) labelInfoQueryList.get(j);
					if(deviceId.equals(labelInfoObj[0].toString())){
						labelInfo=labelInfoObj[1]+"";
						break;
					}
				}
				String recordCountSql="select count(1) from tbl_pcptimingcalculationdata t  where t.wellid="+deviceId+" and t.caltime=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss')";
				List<?> recordCountList = this.findCallSql(recordCountSql);
				if(recordCountList.size()>0){
					recordCount=StringManagerUtils.stringToInteger(recordCountList.get(0)+"");
				}
				String insertHistSql="insert into tbl_pcptimingcalculationdata (wellid,caltime,tubingpressure,casingpressure,producingfluidlevel,bottomholepressure)"
						+ " select "+deviceId+",to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss'),t2.tubingpressure,t2.casingpressure,t2.producingfluidlevel,t2.bottomholepressure"
						+ " from TBL_PCPDAILYCALCULATIONDATA t2 "
						+ " where t2.caldate=to_date('"+date+"','yyyy-mm-dd') "
						+ " and t2.wellid="+deviceId+""
						+ " and rownum=1";
				
				String insertHistSql2="insert into tbl_pcptimingcalculationdata (wellid,caltime)values("+deviceId+",to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss'))";
				String updateSql="update tbl_pcptimingcalculationdata t set t.headerlabelinfo='"+labelInfo+"'"; 
				if(recordCount==0){
					try {
						int r=this.getBaseDao().updateOrDeleteBySql(insertHistSql);
						if(r==0){
							r=this.getBaseDao().updateOrDeleteBySql(insertHistSql2);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				try {
					int r=this.getBaseDao().updateOrDeleteBySql(updateRealtimeAcqProdSql);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//报表继承可编辑数据
				if(StringManagerUtils.isNotNull(templateCode)){
					template=MemoryDataManagerTask.getSingleWellDailyReportTemplateByCode(templateCode);
				}
				if(template!=null){
					if(template.getEditable()!=null && template.getEditable().size()>0){
						String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype "
								+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
								+ " where t.unitid="+reportUnitId+" "
								+ " and t.sort>=0"
								+ " and t.reporttype=2"
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
							
							String updateEditDataSql="update tbl_pcptimingcalculationdata t set ("+updateColBuff+")="
									+ " (select "+updateColBuff+" from tbl_pcptimingcalculationdata t2 "
											+ " where t2.wellid=t.wellid "
											+ " and t2.caltime=(select max(caltime) from tbl_pcptimingcalculationdata t3 where t3.wellid=t2.wellid and t3.caltime<to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') )"
											+ " and rownum=1"
										+ ") "
									+ " where t.wellid="+deviceId
									+ " and t.caltime=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss') ";
							try {
								int r=this.getBaseDao().updateOrDeleteBySql(updateEditDataSql);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}

				List<?> historyCommStatusQueryList=findCallSql(historyCommStatusSql);
				List<?> historyRunStatusQueryList=findCallSql(historyRunStatusSql);
				List<?> historyEnergyStatusQueryList=findCallSql(historyEnergyStatusSql);
				List<?> historyGasStatusQueryList=findCallSql(historyGasStatusSql);
				List<?> historyWaterStatusQueryList=findCallSql(historyWaterStatusSql);
				if(historyCommStatusQueryList.size()>0){
					Object[] historyCommStatusObj=(Object[]) historyCommStatusQueryList.get(0);
					lastCommTime=historyCommStatusObj[3]+"";
					commStatus=StringManagerUtils.stringToInteger(historyCommStatusObj[4]+"");
					commTimeEfficiency=StringManagerUtils.stringToFloat(historyCommStatusObj[5]+"");
					commTime=StringManagerUtils.stringToFloat(historyCommStatusObj[6]+"");
					commRange=StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(historyCommStatusObj[7]));
				}
				
				if(historyRunStatusQueryList.size()>0){
					Object[] historyRunStatuObj=(Object[]) historyRunStatusQueryList.get(0);
					lastRunTime=historyRunStatuObj[3]+"";
					runStatus=StringManagerUtils.stringToInteger(historyRunStatuObj[4]+"");
					runTimeEfficiency=StringManagerUtils.stringToFloat(historyRunStatuObj[5]+"");
					runTime=StringManagerUtils.stringToFloat(historyRunStatuObj[6]+"");
					runRange=StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(historyRunStatuObj[7]));
				}
				
				if(historyEnergyStatusQueryList.size()>0){
					Object[] historyEnergyStatuObj=(Object[]) historyEnergyStatusQueryList.get(0);
					lastEnergyTime=historyEnergyStatuObj[3]+"";
					if(historyEnergyStatuObj[4]!=null){
						isAcqEnergy=true;
					}
					totalkwatth=StringManagerUtils.stringToFloat(historyEnergyStatuObj[4]+"");
					todaykwatth=StringManagerUtils.stringToFloat(historyEnergyStatuObj[5]+"");
				}
				
				if(historyGasStatusQueryList.size()>0){
					Object[] historyGasStatuObj=(Object[]) historyGasStatusQueryList.get(0);
					lastGasTime=historyGasStatuObj[3]+"";
					if(historyGasStatuObj[4]!=null){
						isAcqTotalGasProd=true;
					}
					totalgasvolumetricproduction=StringManagerUtils.stringToFloat(historyGasStatuObj[4]+"");
					gasvolumetricproduction=StringManagerUtils.stringToFloat(historyGasStatuObj[5]+"");
				}
				
				if(historyWaterStatusQueryList.size()>0){
					Object[] historyWaterStatuObj=(Object[]) historyWaterStatusQueryList.get(0);
					lastWaterTime=historyWaterStatuObj[3]+"";
					if(historyWaterStatuObj[4]!=null){
						isAcqTotalWaterProd=true;
					}
					totalwatervolumetricproduction=StringManagerUtils.stringToFloat(historyWaterStatuObj[4]+"");
					watervolumetricproduction=StringManagerUtils.stringToFloat(historyWaterStatuObj[5]+"");
				}
			
				
				String commTotalRequestData="{"
						+ "\"AKString\":\"\","
						+ "\"WellName\":\""+wellName+"\","
						+ "\"OffsetHour\":"+offsetHour+","
						+ "\"Last\":{"
						+ "\"AcqTime\": \""+lastCommTime+"\","
						+ "\"CommStatus\": "+(commStatus>=1)+","
						+ "\"CommEfficiency\": {"
						+ "\"Efficiency\": "+commTimeEfficiency+","
						+ "\"Time\": "+commTime+","
						+ "\"Range\": "+commRange+""
						+ "}"
						+ "},"
						+ "\"Current\": {"
						+ "\"AcqTime\":\""+timeStr+"\","
						+ "\"CommStatus\":"+(commStatus>=1)+""
						+ "}"
						+ "}";
				commResponseData=CalculateUtils.commCalculate(commTotalRequestData);
				
				updateSql+=",CommStatus="+commStatus;
				if(commResponseData!=null&&commResponseData.getResultStatus()==1){
					if(timeStr.equalsIgnoreCase(range.getEndTime()) && commResponseData.getDaily()!=null && StringManagerUtils.isNotNull(commResponseData.getDaily().getDate()) ){
						commTime=commResponseData.getDaily().getCommEfficiency().getTime();
						commTimeEfficiency=commResponseData.getDaily().getCommEfficiency().getEfficiency();
						commRange=commResponseData.getDaily().getCommEfficiency().getRangeString();
					}else{
						commTime=commResponseData.getCurrent().getCommEfficiency().getTime();
						commTimeEfficiency=commResponseData.getCurrent().getCommEfficiency().getEfficiency();
						commRange=commResponseData.getCurrent().getCommEfficiency().getRangeString();
					}
					updateSql+=",commTimeEfficiency="+commTimeEfficiency+",commTime="+commTime;
				}
				
				String runTotalRequestData="{"
						+ "\"AKString\":\"\","
						+ "\"WellName\":\""+wellName+"\","
						+ "\"OffsetHour\":"+offsetHour+","
						+ "\"Last\":{"
						+ "\"AcqTime\": \""+lastRunTime+"\","
						+ "\"RunStatus\": "+(runStatus>=1)+","
						+ "\"RunEfficiency\": {"
						+ "\"Efficiency\": "+runTimeEfficiency+","
						+ "\"Time\": "+runTime+","
						+ "\"Range\": "+runRange+""
						+ "}"
						+ "},"
						+ "\"Current\": {"
						+ "\"AcqTime\":\""+timeStr+"\","
						+ "\"RunStatus\":"+(runStatus>=1)+""
						+ "}"
						+ "}";
				timeEffResponseData=CalculateUtils.runCalculate(runTotalRequestData);
				updateSql+=",runStatus="+runStatus;
				if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
					if(timeStr.equalsIgnoreCase(range.getEndTime()) && timeEffResponseData.getDaily()!=null && StringManagerUtils.isNotNull(timeEffResponseData.getDaily().getDate()) ){
						runTime=timeEffResponseData.getDaily().getRunEfficiency().getTime();
						runTimeEfficiency=timeEffResponseData.getDaily().getRunEfficiency().getEfficiency();
						runRange=timeEffResponseData.getDaily().getRunEfficiency().getRangeString();
					}else{
						runTime=timeEffResponseData.getCurrent().getRunEfficiency().getTime();
						runTimeEfficiency=timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency();
						runRange=timeEffResponseData.getCurrent().getRunEfficiency().getRangeString();
					}
					updateSql+=",runTimeEfficiency="+runTimeEfficiency+",runTime="+runTime;
				}
				
				//判断是否采集了电量，如采集则进行电量计算
				if(isAcqEnergy){
					String energyRequest="{"
							+ "\"AKString\":\"\","
							+ "\"WellName\":\""+wellName+"\","
							+ "\"OffsetHour\":"+offsetHour+",";
					energyRequest+= "\"Last\":{"
							+ "\"AcqTime\": \""+lastEnergyTime+"\","
							+ "\"Total\":{"
							+ "\"KWattH\":"+totalkwatth
							+ "},\"Today\":{"
							+ "\"KWattH\":"+todaykwatth
							+ "}"
							+ "},";
					energyRequest+= "\"Current\": {"
							+ "\"AcqTime\":\""+timeStr+"\","
							+ "\"Total\":{"
							+ "\"KWattH\":"+totalkwatth
							+ "}"
							+ "}"
							+ "}";
					energyCalculateResponseData=CalculateUtils.energyCalculate(energyRequest);
					updateSql+=",totalKWattH="+totalkwatth;
					if(energyCalculateResponseData!=null&&energyCalculateResponseData.getResultStatus()==1){
						if(timeStr.equalsIgnoreCase(range.getEndTime()) && energyCalculateResponseData.getDaily()!=null && StringManagerUtils.isNotNull(energyCalculateResponseData.getDaily().getDate()) ){
							updateSql+=",todayKWattH="+energyCalculateResponseData.getDaily().getKWattH();
						}else{
							updateSql+=",todayKWattH="+energyCalculateResponseData.getCurrent().getToday().getKWattH();
						}
					}
				}
				
				//判断是否采集了累计气量，如采集则进行日产气量计算
				if(isAcqTotalGasProd){
					String energyRequest="{"
							+ "\"AKString\":\"\","
							+ "\"WellName\":\""+wellName+"\","
							+ "\"OffsetHour\":"+offsetHour+",";
					energyRequest+= "\"Last\":{"
							+ "\"AcqTime\": \""+lastGasTime+"\","
							+ "\"Total\":{"
							+ "\"KWattH\":"+totalgasvolumetricproduction
							+ "},\"Today\":{"
							+ "\"KWattH\":"+gasvolumetricproduction
							+ "}"
							+ "},";
					energyRequest+= "\"Current\": {"
							+ "\"AcqTime\":\""+timeStr+"\","
							+ "\"Total\":{"
							+ "\"KWattH\":"+totalgasvolumetricproduction
							+ "}"
							+ "}"
							+ "}";
					updateSql+=",totalgasvolumetricproduction="+totalgasvolumetricproduction;
					totalGasCalculateResponseData=CalculateUtils.energyCalculate(energyRequest);
					if(totalGasCalculateResponseData!=null&&totalGasCalculateResponseData.getResultStatus()==1){
						if(timeStr.equalsIgnoreCase(range.getEndTime()) && totalGasCalculateResponseData.getDaily()!=null && StringManagerUtils.isNotNull(totalGasCalculateResponseData.getDaily().getDate()) ){
							updateSql+=",gasvolumetricproduction="+totalGasCalculateResponseData.getDaily().getKWattH();
						}else{
							updateSql+=",gasvolumetricproduction="+totalGasCalculateResponseData.getCurrent().getToday().getKWattH();
						}
					}
				}
				
				//判断是否采集了累计水量，如采集则进行日产水量计算
				if(isAcqTotalWaterProd){
					String energyRequest="{"
							+ "\"AKString\":\"\","
							+ "\"WellName\":\""+wellName+"\","
							+ "\"OffsetHour\":"+offsetHour+",";
					energyRequest+= "\"Last\":{"
							+ "\"AcqTime\": \""+lastWaterTime+"\","
							+ "\"Total\":{"
							+ "\"KWattH\":"+totalwatervolumetricproduction
							+ "},\"Today\":{"
							+ "\"KWattH\":"+watervolumetricproduction
							+ "}"
							+ "},";
					energyRequest+= "\"Current\": {"
							+ "\"AcqTime\":\""+timeStr+"\","
							+ "\"Total\":{"
							+ "\"KWattH\":"+totalwatervolumetricproduction
							+ "}"
							+ "}"
							+ "}";
					updateSql+=",totalWatervolumetricproduction="+totalwatervolumetricproduction;
					totalWaterCalculateResponseData=CalculateUtils.energyCalculate(energyRequest);
					if(totalWaterCalculateResponseData!=null&&totalWaterCalculateResponseData.getResultStatus()==1){
						if(timeStr.equalsIgnoreCase(range.getEndTime()) && totalWaterCalculateResponseData.getDaily()!=null && StringManagerUtils.isNotNull(totalWaterCalculateResponseData.getDaily().getDate()) ){
							updateSql+=",Watervolumetricproduction="+totalWaterCalculateResponseData.getDaily().getKWattH();
						}else{
							updateSql+=",Watervolumetricproduction="+totalWaterCalculateResponseData.getCurrent().getToday().getKWattH();
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
				
				for(int j=0;j<singleresultlist.size();j++){
					Object[] resuleObj=(Object[]) singleresultlist.get(j);
					if(deviceId.toString().equals(resuleObj[0].toString())){
						String productionData=resuleObj[10].toString();
						type = new TypeToken<PCPCalculateRequestData>() {}.getType();
						PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
						
						acqTimeList.add(resuleObj[1]+"");
						commStatusList.add(commStatus>=1?1:0);
						runStatusList.add(runStatus>=1?1:0);
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
				dataSbf.append("\"CurrentCommStatus\":"+(commStatus>=1?1:0)+",");
				dataSbf.append("\"CurrentRunStatus\":"+(runStatus>=1?1:0)+",");
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
				
				TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(dataSbf.toString(), new TypeToken<TotalAnalysisRequestData>() {}.getType());
				TotalAnalysisResponseData totalAnalysisResponseData=CalculateUtils.totalCalculate(dataSbf.toString());
				
				updateSql+=" where t.wellid="+deviceId+" and t.caltime=to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss')";
				try {
					int r=this.getBaseDao().updateOrDeleteBySql(updateSql);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(commResponseData!=null&&commResponseData.getResultStatus()==1){
					List<String> clobCont=new ArrayList<String>();
					String updateHisRangeClobSql="update tbl_pcptimingcalculationdata t set t.commrange=?";
					clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
					if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
						updateHisRangeClobSql+=", t.runrange=?";
						clobCont.add(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());
					}
					updateHisRangeClobSql+=" where t.wellid="+deviceId +" and t.caltime="+"to_date('"+timeStr+"','yyyy-mm-dd hh24:mi:ss')";
					try {
						int r=this.getBaseDao().executeSqlUpdateClob(updateHisRangeClobSql,clobCont);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					this.saveRPMTimingTotalCalculateData(totalAnalysisResponseData,totalAnalysisRequestData,timeStr);
					if((totalAnalysisRequestData.getAcqTime()!=null?totalAnalysisRequestData.getAcqTime().size():0)>0){
						try {
							int r=this.getBaseDao().updateOrDeleteBySql(updateRealtimeCalDataSql);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				continue;
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
	public void initDailyReportData(int deviceType){
		String deviceTableName="tbl_rpcdevice";
		String tableName="tbl_rpcdailycalculationdata";
		if(deviceType==1){
			tableName="tbl_pcpdailycalculationdata";
			deviceTableName="tbl_pcpdevice";
		}
		
		
		boolean initResult=this.getBaseDao().initDailyReportData();
		
		String instanceSql="select t.id,t.code,t.unitid,t2.singleWellRangeReportTemplate "
				+ " from tbl_protocolreportinstance t,tbl_report_unit_conf t2 "
				+ " where t.unitid=t2.id and t.devicetype="+deviceType;
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
							if(template.getDeviceType()==deviceType && templateCode.equalsIgnoreCase(template.getTemplateCode())){
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
														+ " where t2.wellid=t.wellid and t2.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')-1) "
												+ " where t.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') "
												+ " and t.wellid in ( select t3.id from "+deviceTableName+" t3 where t3.reportinstancecode='"+instanceCode+"'   )";
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