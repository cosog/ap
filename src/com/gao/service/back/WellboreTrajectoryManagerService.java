package com.gao.service.back;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gao.model.gridmodel.WellGridPanelData;
import com.gao.model.gridmodel.WellHandsontableChangedData;
import com.gao.model.WellInformation;
import com.gao.model.calculate.RPCCalculateRequestData;
import com.gao.model.calculate.RPCCalculateRequestData.WellboreTrajectory;
import com.gao.model.calculate.WellboreTrajectoryResponseData;
import com.gao.model.drive.KafkaConfig;
import com.gao.model.drive.RTUDriveConfig;
import com.gao.service.base.BaseService;
import com.gao.service.base.CommonDataService;
import com.gao.task.EquipmentDriverServerTask;
import com.gao.task.KafkaServerTask;
import com.gao.utils.Config;
import com.gao.utils.EquipmentDriveMap;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;

@Service("wellboreTrajectoryManagerService")
public class WellboreTrajectoryManagerService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService service;

	public String getWellboreTrajectoryList(String orgId,String wellName) throws SQLException, IOException{
		StringBuffer result_json = new StringBuffer();
		String sql="select t.id,t.wellName"
				+ " from viw_wellboretrajectory t"
				+ " where t.orgid in("+orgId+") ";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and t.wellName='"+wellName+"'";
		}
		sql+= " order by t.sortNum";
		
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"WellName\":\""+obj[1]+"\"},");
		}
		
		for(int i=list.size()+1;i<=50;i++){
			result_json.append("{},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	
	public String getWellboreTrajectoryDetailsData(String wellName) throws SQLException, IOException{
		StringBuffer result_json = new StringBuffer();
		String sql="select t.wellname,t.measuringdepth,t.verticaldepth,t.deviationangle,t.azimuthangle,t.x,t.y,t.z"
				+ " from viw_wellboretrajectory t "
				+ " where t.wellName='"+wellName+"'";
		
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,\"wellName\":\""+wellName+"\",");
		result_json.append("\"totalRoot\":[");
		int total=0;
		boolean isHaveData=false;
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			SerializableClobProxy   proxy = null;
			CLOB realClob = null;
			String measuringDepth="";
			String verticalDepth="";
			String deviationAngle="";
			String azimuthAngle="";
			String X="";
			String Y="";
			String Z="";
			if(obj[1]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[1]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				measuringDepth=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[2]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[2]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				verticalDepth=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[3]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[3]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				deviationAngle=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[4]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[4]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				azimuthAngle=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[5]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[5]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				X=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[6]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[6]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				Y=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[7]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[7]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				Z=StringManagerUtils.CLOBtoString(realClob);
			}
			if(StringManagerUtils.isNotNull(measuringDepth)){
				isHaveData=true;
				String measuringDepthArr[]=measuringDepth.split(",");
				String verticalDepthArr[]=verticalDepth.split(",");
				String deviationAngleArr[]=deviationAngle.split(",");
				String azimuthAngleArr[]=azimuthAngle.split(",");
				String XArr[]=X.split(",");
				String YArr[]=Y.split(",");
				String ZArr[]=Z.split(",");
				total=XArr.length;
				for(int i=0;i<XArr.length&&StringManagerUtils.isNotNull(XArr[i]);i++){
					result_json.append("{\"measuringDepth\":"+measuringDepthArr[i]+",");
					result_json.append("\"verticalDepth\":"+verticalDepthArr[i]+",");
					result_json.append("\"deviationAngle\":"+deviationAngleArr[i]+",");
					result_json.append("\"azimuthAngle\":"+azimuthAngleArr[i]+",");
					result_json.append("\"X\":"+XArr[i]+",");
					result_json.append("\"Y\":"+YArr[i]+",");
					result_json.append("\"Z\":"+ZArr[i]+"},");
				}
			}
		}
		for(int i=total+1;i<=50;i++){
			result_json.append("{},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("],\"isHaveData\":"+isHaveData+"}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public  String saveWellboreTrajectoryData(String wellName,String wellboreTrajectoryData) throws SQLException {
		StringBuffer requestBuff = new StringBuffer();
		StringBuffer MeasuringDepthBuff = new StringBuffer();
		StringBuffer DeviationAngleBuff = new StringBuffer();
		StringBuffer AzimuthAngleBuff = new StringBuffer();
		StringBuffer result_json = new StringBuffer();
		Gson gson = new Gson();
		String url=Config.getInstance().configFile.getAgileCalculate().getPlugin().getWellboreTrajectory();
		
		requestBuff.append("{\"WellName\":\""+wellName+"\",\"WellboreTrajectory\": {");
		MeasuringDepthBuff.append("\"MeasuringDepth\":[");
		DeviationAngleBuff.append("\"DeviationAngle\":[");
		AzimuthAngleBuff.append("\"AzimuthAngle\":[");
		result_json.append("{ \"success\":true,\"wellName\":\""+wellName+"\",");
		result_json.append("\"totalRoot\":[");
		String measuringDepth="";
		String deviationAngle="";;
		String azimuthAngle="";;
		
		JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+wellboreTrajectoryData+"}");//解析数据
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		
		for(int i=0;i<jsonArray.size();i++){
			JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
			MeasuringDepthBuff.append(everydata.getString("measuringDepth")+",");
			DeviationAngleBuff.append(everydata.getString("deviationAngle")+",");
			AzimuthAngleBuff.append(everydata.getString("azimuthAngle")+",");
			
			measuringDepth+=everydata.getString("measuringDepth");
			deviationAngle+=everydata.getString("deviationAngle");
			azimuthAngle+=everydata.getString("azimuthAngle");
			if(i<jsonArray.size()-1){
				measuringDepth+=",";
				deviationAngle+=",";
				azimuthAngle+=",";
			}
		}
		
		if(MeasuringDepthBuff.toString().endsWith(",")){
			MeasuringDepthBuff.deleteCharAt(MeasuringDepthBuff.length() - 1);
		}
		if(DeviationAngleBuff.toString().endsWith(",")){
			DeviationAngleBuff.deleteCharAt(DeviationAngleBuff.length() - 1);
		}
		if(AzimuthAngleBuff.toString().endsWith(",")){
			AzimuthAngleBuff.deleteCharAt(AzimuthAngleBuff.length() - 1);
		}
		MeasuringDepthBuff.append("]");
		DeviationAngleBuff.append("]");
		AzimuthAngleBuff.append("]");
		requestBuff.append(MeasuringDepthBuff.toString()+","+DeviationAngleBuff.toString()+","+AzimuthAngleBuff.toString());
		requestBuff.append("}}");
		
		String responseData=StringManagerUtils.sendPostMethod(url, requestBuff.toString(),"utf-8");
		java.lang.reflect.Type type = new TypeToken<WellboreTrajectoryResponseData>() {}.getType();
		WellboreTrajectoryResponseData wellboreTrajectoryResponseData=gson.fromJson(responseData, type);
		if(wellboreTrajectoryResponseData!=null&&wellboreTrajectoryResponseData.getCalculationStatus().getResultStatus()==1){
			for(int i=0;i<wellboreTrajectoryResponseData.getWellboreTrajectory().getCNT();i++){
				result_json.append("{\"measuringDepth\":"+wellboreTrajectoryResponseData.getWellboreTrajectory().getMeasuringDepth().get(i)+",");
				result_json.append("\"verticalDepth\":"+wellboreTrajectoryResponseData.getWellboreTrajectory().getVerticalDepth().get(i)+",");
				result_json.append("\"deviationAngle\":"+wellboreTrajectoryResponseData.getWellboreTrajectory().getDeviationAngle().get(i)+",");
				result_json.append("\"azimuthAngle\":"+wellboreTrajectoryResponseData.getWellboreTrajectory().getAzimuthAngle().get(i)+",");
				result_json.append("\"X\":"+wellboreTrajectoryResponseData.getWellboreTrajectory().getX().get(i)+",");
				result_json.append("\"Y\":"+wellboreTrajectoryResponseData.getWellboreTrajectory().getY().get(i)+",");
				result_json.append("\"Z\":"+wellboreTrajectoryResponseData.getWellboreTrajectory().getZ().get(i)+"},");
			}
		}
		
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		this.getBaseDao().saveWellboreTrajectoryData(wellName,wellboreTrajectoryResponseData,measuringDepth,deviationAngle,azimuthAngle);
		
		return result_json.toString();
	}
	
	public  String downKafkaWellboreTrajectoryData(String wellName,String wellboreTrajectoryData) throws SQLException {
		Gson gson = new Gson();
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.initDriverConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		
		KafkaConfig driveConfig=(KafkaConfig)equipmentDriveMap.get("KafkaDrive");
		if(driveConfig==null){
			String path=stringManagerUtils.getFilePath("KafkaDriverConfig.json","protocolConfig/");
			String DriverConfigData=stringManagerUtils.readFile(path,"utf-8");
			java.lang.reflect.Type type = new TypeToken<KafkaConfig>() {}.getType();
			driveConfig=gson.fromJson(DriverConfigData, type);
		}
		if(driveConfig!=null){
			String sql="select t.protocolcode,t.deviceaddr from tbl_wellinformation t where t.wellname='"+wellName+"'";
			List list = this.findCallSql(sql);
			if(list.size()>0){
				Object[] obj=(Object[]) list.get(0);
				String driverCode=obj[0].toString();
				String ID=obj[1].toString();
				if(driverCode.toUpperCase().contains("KAFKA")&&StringManagerUtils.isNotNull(ID)){
					String topic=driveConfig.getTopic().getDown().getModel_WellboreTrajectory().replace("-ID-", "-"+ID+"-");
					RPCCalculateRequestData.WellboreTrajectory wellboreTrajectory=new RPCCalculateRequestData.WellboreTrajectory();
					List<Float> measuringDepth=new ArrayList<Float>();
			        List<Float> deviationAngle=new ArrayList<Float>();
			        List<Float> azimuthAngle=new ArrayList<Float>();
			        
					JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+wellboreTrajectoryData+"}");//解析数据
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for(int i=0;i<jsonArray.size();i++){
						JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
						measuringDepth.add(StringManagerUtils.stringToFloat(everydata.getString("measuringDepth")));
						deviationAngle.add(StringManagerUtils.stringToFloat(everydata.getString("deviationAngle")));
						azimuthAngle.add(StringManagerUtils.stringToFloat(everydata.getString("azimuthAngle")));
					}
					wellboreTrajectory.setMeasuringDepth(measuringDepth);
					wellboreTrajectory.setDeviationAngle(deviationAngle);
					wellboreTrajectory.setAzimuthAngle(azimuthAngle);
					
					KafkaServerTask.producerMsg(topic, "下行井身轨迹数据", gson.toJson(wellboreTrajectory));
				}
			}
		}
		
		return null;
	}
}
