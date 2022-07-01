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

import com.cosog.model.calculate.CommResponseData;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.RPCProductionData;
import com.cosog.model.calculate.TimeEffResponseData;
import com.cosog.model.drive.AcquisitionGroupResolutionData;
import com.cosog.model.drive.AcquisitionItemInfo;
import com.cosog.service.base.BaseService;
import com.cosog.utils.AlarmInfoMap;
import com.cosog.utils.Config;
import com.cosog.utils.Config2;
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
	
	public void saveAndSendAlarmInfo(String wellName,String deviceType,String acqTime,List<AcquisitionItemInfo> acquisitionItemInfoList) throws SQLException{
		boolean isSendSMS=false;
		boolean isSendMail=false;
		StringBuffer SMSContent = new StringBuffer();
		StringBuffer EMailContent = new StringBuffer();
		SMSContent.append(((StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200)?"抽油机":"螺杆泵")+"设备"+wellName+"于"+acqTime+"发生报警:");
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
				String key=wellName+","+deviceType+","+acquisitionItemInfoList.get(i).getColumn()+","+acquisitionItemInfoList.get(i).getAlarmInfo();
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
		String SMSUrl=Config.getInstance().configFile.getAd().getWriteSMS();
		String deviceTableName="tbl_rpcdevice";
		if(StringManagerUtils.stringToInteger(deviceType)>=100 && StringManagerUtils.stringToInteger(deviceType)<200){//如果是抽油机
			deviceTableName="tbl_rpcdevice";
		}else if(StringManagerUtils.stringToInteger(deviceType)>=200 && StringManagerUtils.stringToInteger(deviceType)<300){//否则螺杆泵
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
				StringManagerUtils.sendPostMethod(SMSUrl, sendContent.toString(), "utf-8");
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
			String productionData=object[9].toString(); 
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
	        calculateRequestData.getFESDiagram().setStroke(StringManagerUtils.stringToFloat(object[2]+""));
	        calculateRequestData.getFESDiagram().setSPM(StringManagerUtils.stringToFloat(object[3]+""));
			
	        List<Float> F=new ArrayList<Float>();
	        List<Float> S=new ArrayList<Float>();
	        List<Float> Watt=new ArrayList<Float>();
	        List<Float> I=new ArrayList<Float>();
	        SerializableClobProxy proxy=null;
	        CLOB realClob =null;
	        String clobStr="";
	        String[] curveData=null;
	        if(object[4]!=null){//位移曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[4]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				curveData=clobStr.split(",");
				for(int i=0;i<curveData.length;i++){
					S.add(StringManagerUtils.stringToFloat(curveData[i]));
				}
	        }
	        if(object[5]!=null){//载荷曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[5]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				curveData=clobStr.split(",");
				for(int i=0;i<curveData.length;i++){
					F.add(StringManagerUtils.stringToFloat(curveData[i]));
				}
	        }
	        if(object[6]!=null){//功率曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[6]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				if(StringManagerUtils.isNotNull(clobStr)){
					curveData=clobStr.split(",");
					for(int i=0;i<curveData.length;i++){
						Watt.add(StringManagerUtils.stringToFloat(curveData[i]));
					}
				}
	        }
	        if(object[7]!=null){//电流曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[7]);
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
	        
	        calculateRequestData.getProduction().setLevelCorrectValue(StringManagerUtils.stringToFloat(object[8]+""));
	        
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
}