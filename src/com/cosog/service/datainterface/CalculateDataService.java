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

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.stereotype.Service;

import com.cosog.model.ReportTemplate;
import com.cosog.model.ReportUnitItem;
import com.cosog.model.calculate.CommResponseData;
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
		String SMSUrl=StringManagerUtils.getRequesrUrl(Config.getInstance().configFile.getAd().getIp(), Config.getInstance().configFile.getAd().getPort(), Config.getInstance().configFile.getAd().getRw().getWriteSMS());
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
			//功图数据
			calculateRequestData.setFESDiagram(new RPCCalculateRequestData.FESDiagram());
	        calculateRequestData.getFESDiagram().setAcqTime(object[1]+"");
	        calculateRequestData.getFESDiagram().setSrc(StringManagerUtils.stringToInteger(object[2]+""));
	        calculateRequestData.getFESDiagram().setStroke(StringManagerUtils.stringToFloat(object[3]+""));
	        calculateRequestData.getFESDiagram().setSPM(StringManagerUtils.stringToFloat(object[4]+""));
			
	        List<Float> F=new ArrayList<Float>();
	        List<Float> S=new ArrayList<Float>();
	        List<Float> Watt=new ArrayList<Float>();
	        List<Float> I=new ArrayList<Float>();
	        SerializableClobProxy proxy=null;
	        CLOB realClob =null;
	        String clobStr="";
	        String[] curveData=null;
	        if(object[5]!=null){//位移曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[5]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				curveData=clobStr.split(",");
				for(int i=0;i<curveData.length;i++){
					S.add(StringManagerUtils.stringToFloat(curveData[i]));
				}
	        }
	        if(object[6]!=null){//载荷曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[6]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				curveData=clobStr.split(",");
				for(int i=0;i<curveData.length;i++){
					F.add(StringManagerUtils.stringToFloat(curveData[i]));
				}
	        }
	        if(object[7]!=null){//功率曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[7]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				if(StringManagerUtils.isNotNull(clobStr)){
					curveData=clobStr.split(",");
					for(int i=0;i<curveData.length;i++){
						Watt.add(StringManagerUtils.stringToFloat(curveData[i]));
					}
				}
	        }
	        if(object[8]!=null){//电流曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[8]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				if(StringManagerUtils.isNotNull(clobStr)){
					curveData=clobStr.split(",");
					for(int i=0;i<curveData.length;i++){
						I.add(StringManagerUtils.stringToFloat(curveData[i]));
					}
				}
	        }
	        calculateRequestData.getFESDiagram().setF(F);
	        calculateRequestData.getFESDiagram().setS(S);
	        calculateRequestData.getFESDiagram().setWatt(Watt);
	        calculateRequestData.getFESDiagram().setI(I);
	        
	        calculateRequestData.getProduction().setLevelCorrectValue(StringManagerUtils.stringToFloat(object[9]+""));
	        
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
	        result=gson.toJson(calculateRequestData);
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
			String productionData=object[3].toString();
			type = new TypeToken<PCPCalculateRequestData>() {}.getType();
			PCPCalculateRequestData calculateRequestData=gson.fromJson(productionData, type);
			if(calculateRequestData==null){
				calculateRequestData=new PCPCalculateRequestData();
				calculateRequestData.init();
			}
			calculateRequestData.setWellName(object[0]+"");
			calculateRequestData.setAcqTime(object[1]+"");
			calculateRequestData.setRPM(StringManagerUtils.stringToFloat(object[2]+""));
	        
	        result=gson.toJson(calculateRequestData);
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
				+ "t.surfacesystemefficiency,t.welldownsystemefficiency,t.systemefficiency,t.energyper100mlift "
				+ " from tbl_rpcacqdata_hist t,tbl_rpcdevice t2 "
				+ " where t.wellid=t2.id "
				+ " and t.fesdiagramacqtime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1 "
				+ " and t.resultstatus=1 ";
		String statusSql="select t2.id, t2.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange,"
				+ "t.runstatus,t.runtimeefficiency,t.runtime,t.runrange "
				+ " from tbl_rpcacqdata_hist t,tbl_rpcdevice t2 "
				+ " where t.wellid=t2.id and t.acqTime=( select max(t3.acqTime) from tbl_rpcacqdata_hist t3 where t3.wellid=t.wellid and t3.acqTime between to_date('"+date+"','yyyy-mm-dd') and  to_date('"+date+"','yyyy-mm-dd')+1 )";
		
		String totalStatusSql="select t2.id,t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
				+ " from tbl_rpcdailycalculationdata t,tbl_rpcdevice t2 "
				+ " where t.wellid=t2.id "
				+ " and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(wellId)){
			sql+=" and t.id in ("+wellId+")";
			fesDiagramSql+=" and t2.id in ("+wellId+")";
			statusSql+=" and t2.id in("+wellId+")";
			totalStatusSql+=" and t2.id in ("+wellId+")";
		}
		sql+=" order by t.id";
		fesDiagramSql+= " order by t2.id,t.fesdiagramacqtime";
		statusSql+=" order by t2.id";
		totalStatusSql+=" order by t2.id";
		List<?> welllist = findCallSql(sql);
		List<?> singleresultlist = findCallSql(fesDiagramSql);
		List<?> statusList=null;
		if(!StringManagerUtils.isNotNull(tatalDate)){//如果是跨天汇总
			statusList = findCallSql(statusSql);
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
					for(int j=0;j<statusList.size();j++){
						Object[] statusObj=(Object[]) statusList.get(j);
						if(deviceId.equals(statusObj[0].toString())){
							
							if(statusObj[3]!=null&&StringManagerUtils.stringToInteger(statusObj[3]+"")>=1){
								commStatus=true;
							}
							if(statusObj[7]!=null&&StringManagerUtils.stringToInteger(statusObj[7]+"")==1){
								runStatus=true;
							}
							String commTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+wellName+"\","
									+ "\"Last\":{"
									+ "\"AcqTime\": \""+statusObj[2]+"\","
									+ "\"CommStatus\": "+commStatus+","
									+ "\"CommEfficiency\": {"
									+ "\"Efficiency\": "+statusObj[4]+","
									+ "\"Time\": "+statusObj[5]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(statusObj[6]))+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcqTime\":\""+StringManagerUtils.getCurrentTime("yyyy-MM-dd")+" 01:00:00\","
									+ "\"CommStatus\":true"
									+ "}"
									+ "}";
							String runTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+wellName+"\","
									+ "\"Last\":{"
									+ "\"AcqTime\": \""+statusObj[2]+"\","
									+ "\"RunStatus\": "+runStatus+","
									+ "\"RunEfficiency\": {"
									+ "\"Efficiency\": "+statusObj[8]+","
									+ "\"Time\": "+statusObj[9]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(statusObj[10]))+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcqTime\":\""+StringManagerUtils.getCurrentTime("yyyy-MM-dd")+" 01:00:00\","
									+ "\"RunStatus\":true"
									+ "}"
									+ "}";
							commResponseData=CalculateUtils.commCalculate(commTotalRequestData);
							timeEffResponseData=CalculateUtils.runCalculate(runTotalRequestData);
							if(commResponseData!=null&&commResponseData.getResultStatus()==1&&commResponseData.getDaily().getCommEfficiency().getRange()!=null&&commResponseData.getDaily().getCommEfficiency().getRange().size()>0){
								commStatus=commResponseData.getDaily().getCommStatus();
								commTime=commResponseData.getDaily().getCommEfficiency().getTime();
								commTimeEfficiency=commResponseData.getDaily().getCommEfficiency().getEfficiency();
								commRange=commResponseData.getDaily().getCommEfficiency().getRangeString();
							}

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
					}
				}
				
				dataSbf = new StringBuffer();
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+deviceId+"\",");
				dataSbf.append("\"CurrentCommStatus\":"+(commStatus?1:0)+",");
				dataSbf.append("\"CurrentRunStatus\":"+(runStatus?1:0)+",");
				dataSbf.append("\"Date\":\""+date+"\",");
				dataSbf.append("\"OffsetHour\":0,");
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
				dataSbf.append("\"DeltaRadius\":["+StringUtils.join(deltaRadiusList, ",")+"]");
				dataSbf.append("}");
				requestDataList.add(dataSbf.toString());
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		return requestDataList;
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
		
		
		StringBuffer dataSbf=null;
		List<String> requestDataList=new ArrayList<String>();
		String sql="select t.id,t.wellname from tbl_pcpdevice t ";
		String fesDiagramSql="select t2.id, "
				+ "to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss'),t.rpm,"
				+ "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
				+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
				+ "t.productiondata,"
				+ "t.pumpeff,t.pumpeff1,t.pumpeff2,"
				+ "t.systemefficiency,t.energyper100mlift "
				+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2 "
				+ " where t.wellid=t2.id "
				+ " and t.acqtime between to_date('"+date+"','yyyy-mm-dd') and to_date('"+date+"','yyyy-mm-dd')+1 "
				+ " and t.resultstatus=1 ";
		String statusSql="select t2.id, t2.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange,"
				+ "t.runstatus,t.runtimeefficiency,t.runtime,t.runrange "
				+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2 "
				+ " where t.wellid=t2.id and t.acqTime=( select max(t3.acqTime) from tbl_pcpacqdata_hist t3 where t3.wellid=t.wellid and t3.acqTime between to_date('"+date+"','yyyy-mm-dd') and  to_date('"+date+"','yyyy-mm-dd')+1 )";
		
		String totalStatusSql="select t2.id,t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
				+ " from tbl_pcpdailycalculationdata t,tbl_pcpdevice t2 "
				+ " where t.wellid=t2.id "
				+ " and t.caldate=to_date('"+date+"','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(wellId)){
			sql+=" and t.id in ("+wellId+")";
			fesDiagramSql+=" and t2.id in ("+wellId+")";
			statusSql+=" and t2.id in("+wellId+")";
			totalStatusSql+=" and t2.id in ("+wellId+")";
		}
		sql+=" order by t.id";
		fesDiagramSql+= " order by t2.id,t.acqtime";
		statusSql+=" order by t2.id";
		totalStatusSql+=" order by t2.id";
		List<?> welllist = findCallSql(sql);
		List<?> singleresultlist = findCallSql(fesDiagramSql);
		List<?> statusList=null;
		if(!StringManagerUtils.isNotNull(tatalDate)){//如果是跨天汇总
			statusList = findCallSql(statusSql);
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
					for(int j=0;j<statusList.size();j++){
						Object[] statusObj=(Object[]) statusList.get(j);
						if(deviceId.equals(statusObj[0].toString())){
							
							if(statusObj[3]!=null&&StringManagerUtils.stringToInteger(statusObj[3]+"")>=1){
								commStatus=true;
							}
							if(statusObj[7]!=null&&StringManagerUtils.stringToInteger(statusObj[7]+"")==1){
								runStatus=true;
							}
							String commTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+wellName+"\","
									+ "\"Last\":{"
									+ "\"AcqTime\": \""+statusObj[2]+"\","
									+ "\"CommStatus\": "+commStatus+","
									+ "\"CommEfficiency\": {"
									+ "\"Efficiency\": "+statusObj[4]+","
									+ "\"Time\": "+statusObj[5]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(statusObj[6]))+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcqTime\":\""+StringManagerUtils.getCurrentTime("yyyy-MM-dd")+" 01:00:00\","
									+ "\"CommStatus\":true"
									+ "}"
									+ "}";
							String runTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+wellName+"\","
									+ "\"Last\":{"
									+ "\"AcqTime\": \""+statusObj[2]+"\","
									+ "\"RunStatus\": "+runStatus+","
									+ "\"RunEfficiency\": {"
									+ "\"Efficiency\": "+statusObj[8]+","
									+ "\"Time\": "+statusObj[9]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(statusObj[10]))+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcqTime\":\""+StringManagerUtils.getCurrentTime("yyyy-MM-dd")+" 01:00:00\","
									+ "\"RunStatus\":true"
									+ "}"
									+ "}";
							commResponseData=CalculateUtils.commCalculate(commTotalRequestData);
							timeEffResponseData=CalculateUtils.runCalculate(runTotalRequestData);
							if(commResponseData!=null&&commResponseData.getResultStatus()==1&&commResponseData.getDaily().getCommEfficiency().getRange()!=null&&commResponseData.getDaily().getCommEfficiency().getRange().size()>0){
								commStatus=commResponseData.getDaily().getCommStatus();
								commTime=commResponseData.getDaily().getCommEfficiency().getTime();
								commTimeEfficiency=commResponseData.getDaily().getCommEfficiency().getEfficiency();
								commRange=commResponseData.getDaily().getCommEfficiency().getRangeString();
							}

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
				
				for(int j=0;j<singleresultlist.size();j++){
					Object[] resuleObj=(Object[]) singleresultlist.get(j);
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
					}
				}
				
				dataSbf = new StringBuffer();
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+deviceId+"\",");
				dataSbf.append("\"CurrentCommStatus\":"+(commStatus?1:0)+",");
				dataSbf.append("\"CurrentRunStatus\":"+(runStatus?1:0)+",");
				dataSbf.append("\"Date\":\""+date+"\",");
				dataSbf.append("\"OffsetHour\":0,");
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
	
	public void saveFSDiagramDailyCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException{
		int recordCount=totalAnalysisRequestData.getAcqTime()!=null?totalAnalysisRequestData.getAcqTime().size():0;
		this.getBaseDao().saveFESDiagramTotalCalculateData(totalAnalysisResponseData,totalAnalysisRequestData,tatalDate,recordCount);
	}
	
	public void saveRPMTotalCalculateData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException{
		int recordCount=totalAnalysisRequestData.getAcqTime()!=null?totalAnalysisRequestData.getAcqTime().size():0;
		this.getBaseDao().saveRPMTotalCalculateData(totalAnalysisResponseData,totalAnalysisRequestData,tatalDate,recordCount);
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
		
		ReportTemplate reportTemplate=MemoryDataManagerTask.getReportTemplateConfig();
		if(reportTemplate!=null && reportTemplate.getReportTemplate()!=null && reportTemplate.getReportTemplate().size()>0){
			for(int i=0;i<reportTemplate.getReportTemplate().size();i++){
				ReportTemplate.Template template=reportTemplate.getReportTemplate().get(i);
				if(template.getDeviceType()==deviceType && template.getEditable()!=null && template.getEditable().size()>0){
					String reportItemSql="select t.itemname,t.itemcode,t.sort,t.datatype "
							+ " from TBL_REPORT_ITEMS2UNIT_CONF t "
							+ " where t.unitcode='"+template.getTemplateCode()+"' "
							+ " and t.sort>=0"
							+ " order by t.sort";
					List<ReportUnitItem> reportItemList=new ArrayList<ReportUnitItem>();
					List<?> reportItemQuertList = this.findCallSql(reportItemSql);
					
					for(int j=0;j<reportItemQuertList.size();j++){
						Object[] reportItemObj=(Object[]) reportItemQuertList.get(j);
						ReportUnitItem reportUnitItem=new ReportUnitItem();
						reportUnitItem.setItemName(reportItemObj[0]+"");
						reportUnitItem.setItemCode(reportItemObj[1]+"");
						reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2]+""));
						reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3]+""));
						
						for(int k=0;k<template.getEditable().size();k++){
							ReportTemplate.Editable editable=template.getEditable().get(k);
							if(editable.getStartRow()>=template.getHeader().size() && reportUnitItem.getSort()-1>=editable.getStartColumn() && reportUnitItem.getSort()-1<=editable.getEndColumn()){//索引起始不同
								reportItemList.add(reportUnitItem);
								break;
							}
						}
					}
					if(reportItemList.size()>0){
						StringBuffer updateColBuff = new StringBuffer();
						for(int j=0;j<reportItemList.size();j++){
							updateColBuff.append(reportItemList.get(j).getItemCode()+",");
						}
						if(updateColBuff.toString().endsWith(",")){
							updateColBuff.deleteCharAt(updateColBuff.length() - 1);
						}
						
						String updateSql="update "+tableName+" t set ("+updateColBuff+")="
								+ " (select "+updateColBuff+" from "+tableName+" t2 "
										+ " where t2.wellid=t.wellid and t2.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')-1) "
								+ " where t.caldate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') "
								+ " and t.wellid in ( select t3.id from "+deviceTableName+" t3,tbl_protocolreportinstance t4 "
									+ " where t3.reportinstancecode=t4.code and t4.unitcode='"+template.getTemplateCode()+"'   )";
						try {
							int r=this.getBaseDao().updateOrDeleteBySql(updateSql);
//							System.out.println(updateSql);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							continue;
						}
					}
				}
			}
		}
	}
}