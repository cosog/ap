package com.cosog.service.pstofs;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.DistreteAlarmLimit;
import com.cosog.model.WellInformation;
import com.cosog.model.calculate.InversioneFSdiagramResponseData;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.WellAcquisitionData;
import com.cosog.model.calculate.RPCCalculateRequestData.PumpingUnit;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.drive.KafkaConfig;
import com.cosog.model.gridmodel.InverOptimizeHandsontableChangedData;
import com.cosog.model.gridmodel.WellHandsontableChangedData;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.KafkaServerTask;
import com.cosog.task.MQTTServerTask.TransferDaily;
import com.cosog.task.MQTTServerTask.TransferDiagram;
import com.cosog.task.MQTTServerTask.TransferDiscrete;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.Page;
import com.cosog.utils.PageHandler;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;

/**
 * <p>
 * 电功图反演地面功图Service
 * </p>
 * 
 * @author zhao 2018-9-07
 * 
 * @param <T>
 */
@Service("PSToFSService")
public class PSToFSService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService service;
	public String getPSToFSPumpingUnitData(String orgId) throws SQLException, IOException{
		StringBuffer result_json = new StringBuffer();
		String sql="select t.id,t2.wellName,t.manufacturer,t.model,t.stroke,t.crankrotationdirection,"
				+ " t.offsetangleofcrank,t.crankgravityradius,t.singlecrankweight,t.singlecrankpinweight,"
				+ " t.structuralunbalance,"
				+ " t.balanceposition,t.balanceweight,t.prtf "
				+ " from tbl_rpcinformation t,tbl_wellinformation t2,tbl_org org "
				+ " where t.wellId=t2.id and t2.orgid=org.org_id "
				+ " and org.org_id in("+orgId+") "
				+ " order by t2.sortNum";
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String prtf="[]";
			if(obj[13]!=null){
				SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[13]);
				CLOB realClob = (CLOB) proxy.getWrappedClob(); 
				prtf=StringManagerUtils.CLOBtoString(realClob);
			}
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"WellName\":\""+obj[1]+"\",");
			result_json.append("\"Manufacturer\":\""+obj[2]+"\",");
			result_json.append("\"Model\":\""+obj[3]+"\",");
			result_json.append("\"Stroke\":\""+obj[4]+"\",");
			result_json.append("\"CrankRotationDirection\":\""+obj[5]+"\",");
			result_json.append("\"OffsetAngleOfCrank\":\""+obj[6]+"\",");
			result_json.append("\"CrankGravityRadius\":\""+obj[7]+"\",");
			result_json.append("\"SingleCrankWeight\":\""+obj[8]+"\",");
			result_json.append("\"SingleCrankPinWeight\":\""+obj[9]+"\",");
			result_json.append("\"StructuralUnbalance\":\""+obj[10]+"\",");
			result_json.append("\"BalancePosition\":\""+obj[11]+"\",");
			result_json.append("\"BalanceWeight\":\""+obj[12]+"\",");
			result_json.append("\"prtf\":"+prtf+"},");
		}
		for(int i=list.size()+1;i<=30;i++){
			result_json.append("{},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getPSToFSMotorData(String orgId) throws SQLException, IOException{
		StringBuffer result_json = new StringBuffer();
		String sql="select t.id,t2.wellName,t.manufacturer,t.model,"
				+ " t.beltpulleydiameter,t.synchrospeed,t.performancecurver"
				+ " from tbl_rpc_motor t ,tbl_wellinformation t2,tbl_org org "
				+ " where t.wellId=t2.id and t2.orgid=org.org_id "
				+ " and org.org_id in("+orgId+") "
				+ " order by t2.sortNum";
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String PerformanceCurver="";
			if(obj[6]!=null){
				SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[6]);
				CLOB realClob = (CLOB) proxy.getWrappedClob(); 
				PerformanceCurver=StringManagerUtils.CLOBtoString(realClob);
			}
			if(!StringManagerUtils.isNotNull(PerformanceCurver)){
				PerformanceCurver="[]";
			}
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"WellName\":\""+obj[1]+"\",");
			result_json.append("\"Manufacturer\":\""+obj[2]+"\",");
			result_json.append("\"Model\":\""+obj[3]+"\",");
			result_json.append("\"BeltPulleyDiameter\":\""+obj[4]+"\",");
			result_json.append("\"SynchroSpeed\":\""+obj[5]+"\",");
			
			result_json.append("\"PerformanceCurver\":"+PerformanceCurver+"},");
		}
		for(int i=list.size()+1;i<=30;i++){
			result_json.append("{},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	
	public String getInverOptimizeData(String orgId,String wellInformationName,int recordCount) throws SQLException, IOException{
		StringBuffer result_json = new StringBuffer();
		String sql="select t.id,t2.wellName,t.OffsetAngleOfCrankPS,t.SurfaceSystemEfficiency,"
				+ " t.FS_LeftPercent,t.FS_RightPercent,t.WattAngle,t.FilterTime_Watt,t.FilterTime_I,t.FilterTime_RPM,"
				+ " t.FilterTime_FSDiagram,t.FilterTime_FSDiagram_L,t.FilterTime_FSDiagram_R"
				+ " from tbl_rpc_inver_opt t,tbl_wellinformation t2,tbl_org org "
				+ " where t.wellId=t2.id and t2.orgid=org.org_id "
				+ " and org.org_id in("+orgId+") ";
		if(StringManagerUtils.isNotNull(wellInformationName)){
			sql+=" and t2.wellName='"+wellInformationName+"'";
		}
		sql+= " order by t2.sortNum";
		List<?> list = this.findCallSql(sql);
//		int totalCount=getTotalCountRows(sql);//获取总记录数
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\"},"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"WellName\" },"
				+ "{ \"header\":\"曲柄位置开关偏置角(°)\",\"dataIndex\":\"OffsetAngleOfCrankPS\" },"
				+ "{ \"header\":\"地面效率\",\"dataIndex\":\"SurfaceSystemEfficiency\" },"
				+ "{ \"header\":\"左侧截取百分比\",\"dataIndex\":\"FS_LeftPercent\" },"
				+ "{ \"header\":\"右侧截取百分比\",\"dataIndex\":\"FS_RightPercent\" },"
				+ "{ \"header\":\"功率滤波角度(°)\",\"dataIndex\":\"WattAngle\" },"
				+ "{ \"header\":\"功率滤波次数\",\"dataIndex\":\"FilterTime_Watt\" },"
				+ "{ \"header\":\"电流滤波次数\",\"dataIndex\":\"FilterTime_I\" },"
				+ "{ \"header\":\"转速滤波次数\",\"dataIndex\":\"FilterTime_RPM\" },"
				+ "{ \"header\":\"功图滤波次数\",\"dataIndex\":\"FilterTime_FSDiagram\" },"
				+ "{ \"header\":\"功图左侧滤波次数\",\"dataIndex\":\"FilterTime_FSDiagram_L\" },"
				+ "{ \"header\":\"功图右侧滤波次数\",\"dataIndex\":\"FilterTime_FSDiagram_R\" }"
				+ "]";
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalCount\":"+list.size()+",");
		result_json.append("\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"WellName\":\""+obj[1]+"\",");
			result_json.append("\"OffsetAngleOfCrankPS\":\""+obj[2]+"\",");
			result_json.append("\"SurfaceSystemEfficiency\":\""+obj[3]+"\",");
			result_json.append("\"FS_LeftPercent\":\""+obj[4]+"\",");
			result_json.append("\"FS_RightPercent\":\""+obj[5]+"\",");
			result_json.append("\"WattAngle\":\""+obj[6]+"\",");
			result_json.append("\"FilterTime_Watt\":\""+obj[7]+"\",");
			result_json.append("\"FilterTime_I\":\""+obj[8]+"\",");
			result_json.append("\"FilterTime_RPM\":\""+obj[9]+"\",");
			result_json.append("\"FilterTime_FSDiagram\":\""+obj[10]+"\",");
			result_json.append("\"FilterTime_FSDiagram_L\":\""+obj[11]+"\",");
			result_json.append("\"FilterTime_FSDiagram_R\":\""+obj[12]+"\"},");
		}
		for(int i=list.size()+1;i<=recordCount;i++){
			result_json.append("{},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public void saveInverOptimizeHandsontableData(InverOptimizeHandsontableChangedData inverOptimizeHandsontableChangedData,String orgId,String orgCode) throws Exception {
		getBaseDao().saveInverOptimizeHandsontableData(inverOptimizeHandsontableChangedData, orgId,orgCode);
	}
	
	public String getInversionCalaulateResult(String wellName,String cjsj) throws SQLException, IOException{
		StringBuffer result_json = new StringBuffer();
		String sql="select t.wellname,t.cjsj,t.electricdata,t.startpoint,t.endpoint,t.fsdiagramid,t.stroke,t.spm,t.cnt,t.fsdiagram "
				+ " from t_inversiondata t where 1=1";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and t.wellname='"+wellName+"'";
		}
		if(StringManagerUtils.isNotNull(cjsj)){
			sql+=" and t.cjsj='"+cjsj+"'";
		}
		sql+="";
		sql+=" order by wellname,cjsj desc";
		sql="select v.* from ("+sql+") v where rownum=1"; 
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		result_json.append("\"Total\":"+list.size());
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[2]);
			CLOB electricdataClob = (CLOB) proxy.getWrappedClob(); 
			String electricdata=StringManagerUtils.CLOBtoString(electricdataClob);
			
			proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[9]);
			CLOB fsdiagramClob = (CLOB) proxy.getWrappedClob(); 
			String fsdiagram=StringManagerUtils.CLOBtoString(fsdiagramClob);
			
			result_json.append(",\"WellName\":\""+obj[0]+"\",");
			result_json.append("\"cjsj\":\""+obj[1]+"\",");
			result_json.append("\"StartPoint\":"+obj[3]+",");
			result_json.append("\"EndPoint\":"+obj[4]+",");
			result_json.append("\"FSDiagramId\":"+obj[5]+",");
			result_json.append("\"Stroke\":"+obj[6]+",");
			result_json.append("\"SPM\":"+obj[7]+",");
			result_json.append("\"CNT\":"+obj[8]+",");
			result_json.append("\"ElectricData\":"+electricdata+",");
			result_json.append("\"FSDiagram\":"+fsdiagram+"");
		}
		result_json.append("}");
		return result_json.toString();
	}
	
	public String getCalaulateResult(String wellName,String cjsj,String data) throws SQLException, IOException{
		StringBuffer result_json = new StringBuffer();
		String pumpingUnitSql="select t.manufacturer,t.model,t.stroke,decode(t.crankrotationdirection,'顺时针','Clockwise','Anticlockwise'),"
				+ " t.offsetangleofcrank,t.crankgravityradius,t.singlecrankweight,"
				+ " t.structuralunbalance,t.gearreducerratio,t.gearreducerbeltpulleydiameter,"
				+ " t.balanceposition,t.balanceweight,t.prtf "
				+ " from tbl_rpcinformation t "
				+ " where t.wellname='"+wellName+"'";
		String motorSql="select t.manufacturer,t.model,"
				+ " t.beltpulleydiameter,t.synchrospeed,t.performancecurver"
				+ " from tbl_rpc_motor t "
				+ " where t.wellname='"+wellName+"'";
		
		List<?> pumpingUnitList = this.findCallSql(pumpingUnitSql);
		List<?> motorList = this.findCallSql(motorSql);
		result_json.append("{\"AKString\":\"\",");
		result_json.append("\"WellName\":\""+wellName+"\",");
		result_json.append("\"AcqTime\":\""+cjsj+"\",");
		
		//拼接有功功率数据
		String PA="[";
		String PB="[";
		String PC="[";
		JSONObject elecJsonObject = JSONObject.fromObject("{\"data\":"+data+"}");//解析数据
		JSONArray elecJsonArray = elecJsonObject.getJSONArray("data");
		for(int i=0;i<elecJsonArray.size();i++){
			JSONObject everydata = JSONObject.fromObject(elecJsonArray.getString(i));
			PA+=everydata.getString("activePowerA");
			PB+=everydata.getString("activePowerB");
			PC+=everydata.getString("activePowerB");
			if(i<elecJsonArray.size()-1){
				PA+=",";
				PB+=",";
				PC+=",";
			}
		}
		PA+="]";
		PB+="]";
		PC+="]";
		result_json.append("\"PA\":"+PA+",");
		result_json.append("\"PB\":"+PB+",");
		result_json.append("\"PC\":"+PC+",");
		
		//拼接电机数据
		result_json.append("\"Motor\":{");
		for(int i=0;i<motorList.size();i++){
			Object[] obj=(Object[]) motorList.get(i);
			
			SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[4]);
			CLOB realClob = (CLOB) proxy.getWrappedClob(); 
			String PerformanceCurver=StringManagerUtils.CLOBtoString(realClob);
			
			result_json.append("\"Manufacturer\":\""+obj[0]+"\",");
			result_json.append("\"Model\":\""+obj[1]+"\",");
			result_json.append("\"BeltPulleyDiameter\":"+obj[2]+",");
			result_json.append("\"SynchroSpeed\":"+obj[3]+",");
			result_json.append("\"PerformanceCurver\":{");
			
			String InputActivePower="[";
			String OutputPower="[";
			String SlipRatio="[";
			JSONObject motorJsonObject = JSONObject.fromObject("{\"data\":"+PerformanceCurver+"}");//解析数据
			JSONArray motorJsonArray = motorJsonObject.getJSONArray("data");
			for(int j=0;j<motorJsonArray.size();j++){
				JSONObject everydata = JSONObject.fromObject(motorJsonArray.getString(j));
				InputActivePower+=everydata.getString("InputActivePower");
				OutputPower+=everydata.getString("OutputPower");
				SlipRatio+=everydata.getString("SlipRatio");
				if(j<motorJsonArray.size()-1){
					InputActivePower+=",";
					OutputPower+=",";
					SlipRatio+=",";
				}
			}
			InputActivePower+="]";
			OutputPower+="]";
			SlipRatio+="]";
			result_json.append("\"InputActivePower\":"+InputActivePower+",");
			result_json.append("\"OutputPower\":"+OutputPower+",");
			result_json.append("\"SlipRatio\":"+SlipRatio+"}");
		}
		result_json.append("},");
		
		//拼接抽油机数据
		result_json.append("\"PumpingUnit\":{");
		for(int i=0;i<pumpingUnitList.size();i++){
			Object[] obj=(Object[]) pumpingUnitList.get(i);
			
			SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[12]);
			CLOB realClob = (CLOB) proxy.getWrappedClob(); 
			String prtf=StringManagerUtils.CLOBtoString(realClob);
			
			result_json.append("\"Manufacturer\":\""+obj[0]+"\",");
			result_json.append("\"Model\":\""+obj[1]+"\",");
			result_json.append("\"Stroke\":"+obj[2]+",");
			result_json.append("\"CrankRotationDirection\":\""+obj[3]+"\",");
			result_json.append("\"OffsetAngleOfCrank\":"+obj[4]+",");
			result_json.append("\"CrankGravityRadius\":"+obj[5]+",");
			result_json.append("\"SingleCrankWeight\":"+obj[6]+",");
			result_json.append("\"StructuralUnbalance\":"+obj[7]+",");
			result_json.append("\"GearReducerRatio\":"+obj[8]+",");
			result_json.append("\"GearReducerBeltPulleyDiameter\":"+obj[9]+",");
			result_json.append("\"Balance\":{");
			result_json.append("\"EveryBalance\":[");
			
			//拼接抽油机平衡块数据
			String[] BalancePositionArr=(obj[10]+"").split(",");
			String[] BalanceWeightArr=(obj[11]+"").split(",");
			for(int j=0;j<BalancePositionArr.length&&j<BalanceWeightArr.length;j++){
				result_json.append("{\"Position\":"+BalancePositionArr[j]+",");
				result_json.append("\"Weight\":"+BalanceWeightArr[j]+"}");
				if(j<BalancePositionArr.length-1&&j<BalanceWeightArr.length-1){
					result_json.append(",");
				}
			}
			result_json.append("]},");
			//拼接抽油机位置扭矩因数曲线数据
			result_json.append("\"PRTF\":{");
			String CrankAngle="[";
			String PR="[";
			String TF="[";
			JSONObject prtfJsonObject = JSONObject.fromObject("{\"data\":"+prtf+"}");//解析数据
			JSONArray prtfJsonArray = prtfJsonObject.getJSONArray("data");
			for(int j=0;j<prtfJsonArray.size();j++){
				JSONObject everydata = JSONObject.fromObject(prtfJsonArray.getString(j));
				CrankAngle+=everydata.getString("CrankAngle");
				PR+=everydata.getString("PR");
				TF+=everydata.getString("TF");
				if(j<prtfJsonArray.size()-1){
					CrankAngle+=",";
					PR+=",";
					TF+=",";
				}
			}
			CrankAngle+="]";
			PR+="]";
			TF+="]";
			result_json.append("\"CrankAngle\":"+CrankAngle+",");
			result_json.append("\"PR\":"+PR+",");
			result_json.append("\"TF\":"+TF+"}");
		}
		result_json.append("}");
		result_json.append("}");
		return result_json.toString();
	}
	
	public String getWellNameList(Page pager) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		String sql = "select distinct(t.wellname) from t_inversiondata t order by t.wellname";
		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
		sqlCuswhere.append(""+sql);
		int maxvalue=pager.getLimit()+pager.getStart();
		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
		sqlCuswhere.append(" where rn >"+pager.getStart());
		String finalsql=sqlCuswhere.toString();
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(finalsql);
			result_json.append("{\"totals\":"+totals+",\"list\":[");
			String get_key = "";
			String get_val = "";
			if (null != list && list.size() > 0) {
				for (Object o : list) {
					Object[] obj = (Object[]) o;
					get_key = obj[0] + "";
					get_val = obj[0] + "";
					result_json.append("{boxkey:\"" + get_key + "\",");
					result_json.append("boxval:\"" + get_val + "\"},");
				}
				if (result_json.toString().length() > 1) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("]}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getAcqTimeList(Page pager,String wellName) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		String sql = "select distinct(t.cjsj) from t_inversiondata t where 1=1";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and t.wellname='"+wellName+"'";
		}else{
			sql+=" and 1=2";
		}
		sql +=" order by t.cjsj desc";
		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
		sqlCuswhere.append(""+sql);
		int maxvalue=pager.getLimit()+pager.getStart();
		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
		sqlCuswhere.append(" where rn >"+pager.getStart());
		String finalsql=sqlCuswhere.toString();
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(finalsql);
			result_json.append("{\"totals\":"+totals+",\"list\":[");
			String get_key = "";
			String get_val = "";
			if (null != list && list.size() > 0) {
				for (Object o : list) {
					Object[] obj = (Object[]) o;
					get_key = obj[0] + "";
					get_val = obj[0] + "";
					result_json.append("{boxkey:\"" + get_key + "\",");
					result_json.append("boxval:\"" + get_val + "\"},");
				}
				if (result_json.toString().length() > 1) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("]}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString().replaceAll("null", "");
	}
	
	public boolean savePumpingUnitData(String PumpingUnitData,String PumpingUnitPTRData,String wellName) throws SQLException, ParseException{
		return this.getBaseDao().savePSToFSPumpingUnitData(PumpingUnitData,PumpingUnitPTRData,wellName);
	}
	
	public String downKafkaPumpingData(String PumpingUnitData,String PumpingUnitPTRData,String wellName){
		Gson gson = new Gson();
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
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
			JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+PumpingUnitData+"}");//解析数据
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for(int i=0;i<jsonArray.size();i++){
				JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
				String sql="select t.protocolcode,t.signinid from tbl_wellinformation t where t.wellname='"+everydata.getString("WellName")+"'";
				List list = this.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					String protocolCode=obj[0]==null?"":obj[0].toString();
					String ID=obj[1]==null?"":obj[1].toString();
					if(protocolCode.toUpperCase().contains("KAFKA")&&StringManagerUtils.isNotNull(ID)){
						String topic=driveConfig.getTopic().getDown().getModel_PumpingUnit().replace("-ID-", "-"+ID+"-");
						RPCCalculateRequestData.PumpingUnit pumpingUnit=new RPCCalculateRequestData.PumpingUnit();
						pumpingUnit.setManufacturer(everydata.getString("Manufacturer"));
						pumpingUnit.setModel(everydata.getString("Model"));
						pumpingUnit.setStroke(StringManagerUtils.stringToFloat(everydata.getString("Stroke")));
						pumpingUnit.setCrankRotationDirection("顺时针".equalsIgnoreCase(everydata.getString("CrankRotationDirection"))?"Clockwise":"Anticlockwise");
						pumpingUnit.setOffsetAngleOfCrank(StringManagerUtils.stringToFloat(everydata.getString("OffsetAngleOfCrank")));
						pumpingUnit.setCrankGravityRadius(StringManagerUtils.stringToFloat(everydata.getString("CrankGravityRadius")));
						pumpingUnit.setSingleCrankWeight(StringManagerUtils.stringToFloat(everydata.getString("SingleCrankWeight")));
						pumpingUnit.setSingleCrankPinWeight(StringManagerUtils.stringToFloat(everydata.getString("SingleCrankPinWeight")));
						pumpingUnit.setStructuralUnbalance(StringManagerUtils.stringToFloat(everydata.getString("StructuralUnbalance")));
						String[] BalanceWeightArr=(everydata.getString("BalanceWeight")).split(",");
						pumpingUnit.setBalance(new RPCCalculateRequestData.Balance());
						pumpingUnit.getBalance().setEveryBalance(new ArrayList<RPCCalculateRequestData.EveryBalance>());
						for(int j=0;BalanceWeightArr!=null&&j<BalanceWeightArr.length;j++){
							RPCCalculateRequestData.EveryBalance everyBalance=new RPCCalculateRequestData.EveryBalance();
							everyBalance.setWeight(StringManagerUtils.stringToFloat(BalanceWeightArr[j]));
							pumpingUnit.getBalance().getEveryBalance().add(everyBalance);
						}
						KafkaServerTask.producerMsg(topic, "下行抽油机数据", gson.toJson(pumpingUnit));
					}
				}
			}
		}
		return null;
	}
	
	public boolean saveMotorData(String MotorData,String MotorPerformanceCurverData,String wellName) throws SQLException, ParseException{
		return this.getBaseDao().savePSToFSMotorData(MotorData,MotorPerformanceCurverData,wellName);
	}
	
	public String getDiagramDataList(String orgId,String wellName,String startDate,String endDate, Page pager)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String columns=service.showTableHeadersColumns("elecInverDiagram_Realtime");
		String sqlAll="select  t.id,well.wellName,to_char(t.acqTime@'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ " stroke,spm,  "
				+ " decode(t.fmax@0@null@t.fmax) as fmax,decode(t.fmin@0@null@t.fmin) as fmin,"
				+ " upStrokeIMax,downStrokeIMax,iDegreeBalance,upStrokeWattMax,downStrokeWattMax,wattDegreeBalance,"
				+ " signal,interval,deviceVer "
				+ " from tbl_rpc_diagram_latest t,tbl_wellinformation well  "
				+ " where t.wellid=well.id  and t.datasource=1"
				+ " and well.orgid in ("+orgId+") "
				+ " order by well.sortnum";
		
		if(StringManagerUtils.isNotNull(wellName)){
			sqlAll="select  t.id,well.wellName,to_char(t.acqTime@'yyyy-mm-dd hh24:mi:ss') as acqTime,"
					+ " stroke,spm,  "
					+ " decode(t.fmax@0@null@t.fmax) as fmax,decode(t.fmin@0@null@t.fmin) as fmin,"
					+ " upStrokeIMax,downStrokeIMax,iDegreeBalance,upStrokeWattMax,downStrokeWattMax,wattDegreeBalance, "
					+ " signal,interval,deviceVer "
					+ " from tbl_rpc_diagram_hist t,tbl_wellinformation well "
					+ " where t.wellid=well.id and t.datasource=1  and well.wellname='"+wellName+"' and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1 "
					+ " order by t.acqTime desc";
		}
		int maxvalue=pager.getLimit()+pager.getStart();
		String finalSql="select * from   ( select a.*,rownum as rn from ("+sqlAll+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		String getResult = this.findCustomPageBySqlEntity(sqlAll,finalSql, columns, 20 + "", pager);
		return getResult.replaceAll("null", "");
	}
	
	public String getSingleInverDiagramData(String id,String wellName) throws SQLException, IOException{
        StringBuffer dataSbf = new StringBuffer();
        String table="tbl_rpc_diagram_hist";
        if(!StringManagerUtils.isNotNull(wellName)){
        	table="tbl_rpc_diagram_latest";
        }
        String sql="select well.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
        		+ " t.stroke,t.spm,t.fmax,t.fmin,"
        		+ " t.upstrokeimax,t.downstrokeimax,t.idegreebalance,t.upstrokewattmax,t.downstrokewattmax,t.wattdegreebalance,"
        		+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,t.rpm_curve "
        		+ " from "+table+" t,tbl_wellinformation well "
        		+ " where t.wellid=well.id  and t.id="+id;
		List<?> list=this.findCallSql(sql);
		String positionCurveData="";
        String loadCurveData="";
        String powerCurveData="";
        String currentCurveData="";
        String rpmCurveData="";
        SerializableClobProxy   proxy=null;
        CLOB realClob=null;
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			if(obj[12]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[12]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				positionCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[13]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[13]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				loadCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(obj[14]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[14]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				powerCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(obj[15]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[15]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				currentCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(obj[16]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[16]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				rpmCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
	        dataSbf.append("{success:true,");
	        dataSbf.append("wellName:\""+obj[0]+"\",");           // 井名
	        dataSbf.append("acqTime:\""+obj[1]+"\",");         // 时间
	        dataSbf.append("stroke:\""+obj[2]+"\",");         // 冲程
	        dataSbf.append("SPM:\""+obj[3]+"\",");         // 冲次
	        dataSbf.append("fmax:\""+obj[4]+"\",");         // 最大载荷
	        dataSbf.append("fmin:\""+obj[5]+"\",");         // 最小载荷
	        
	        dataSbf.append("upStrokeIMax:\""+obj[6]+"\",");         // 冲次
	        dataSbf.append("downStrokeIMax:\""+obj[7]+"\",");         // 冲次
	        dataSbf.append("iDegreeBalance:\""+obj[8]+"\",");         // 冲次
	        dataSbf.append("upStrokeWattMax:\""+obj[9]+"\",");         // 冲次
	        dataSbf.append("downStrokeWattMax:\""+obj[10]+"\",");         // 冲次
	        dataSbf.append("wattDegreeBalance:\""+obj[11]+"\",");         // 冲次
	        
	        
	        dataSbf.append("positionCurveData:\""+positionCurveData+"\",");         //位移曲线
	        dataSbf.append("loadCurveData:\""+loadCurveData+"\",");             //载荷曲线
	        dataSbf.append("powerCurveData:\""+powerCurveData+"\",");            //功率曲线
	        dataSbf.append("currentCurveData:\""+currentCurveData+"\",");           //电流曲线
	        dataSbf.append("rpmCurveData:\""+rpmCurveData+"\"");           //电流曲线
	        dataSbf.append("}");
		}else{
			dataSbf.append("{success:true,");
			dataSbf.append("wellName:\"\",");
	        dataSbf.append("acqTime:\"\",");
	        dataSbf.append("stroke:\"\",");  
	        dataSbf.append("SPM:\"\",");
	        dataSbf.append("fmax:\"\",");         // 最大载荷
	        dataSbf.append("fmin:\"\",");         // 最小载荷
	        dataSbf.append("upStrokeIMax:\"\",");
	        dataSbf.append("downStrokeIMax:\"\",");
	        dataSbf.append("iDegreeBalance:\"\",");
	        dataSbf.append("upStrokeWattMax:\"\",");
	        dataSbf.append("downStrokeWattMax:\"\",");
	        dataSbf.append("wattDegreeBalance:\"\",");
	        dataSbf.append("positionCurveData:\"\",");
	        dataSbf.append("loadCurveData:\"\",");
	        dataSbf.append("powerCurveData:\"\",");
	        dataSbf.append("currentCurveData:\"\",");
	        dataSbf.append("rpmCurveData:\"\"");
	        dataSbf.append("}");
		}
		return dataSbf.toString().replaceAll("null", "");
	}
	
	public String getSingleElecCurveData(String id,String wellName) throws SQLException, IOException{
        StringBuffer dataSbf = new StringBuffer();
        String table="tbl_rpc_diagram_hist";
        if(!StringManagerUtils.isNotNull(wellName)){
        	table="tbl_rpc_diagram_latest";
        }
        String sql="select well.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
        		+ " t.stroke,t.spm,t.fmax,t.fmin,"
        		+ " t.position_curve,t.load_curve,"
        		+ " t.upstrokeimax,t.downstrokeimax,t.idegreebalance,t.upstrokewattmax,t.downstrokewattmax,t.wattdegreebalance,"
        		+ " t.power_curve,t.current_curve,t.rpm_curve, "
        		+ " t.rawpower_curve,t.rawcurrent_curve,t.rawrpm_curve "
        		+ " from "+table+" t,tbl_wellinformation well "
        		+ " where t.wellid=well.id  and t.id="+id;
		List<?> list=this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
	        dataSbf.append("{success:true,");
	        dataSbf.append("wellName:\""+obj[0]+"\",");           // 井名
	        dataSbf.append("acqTime:\""+obj[1]+"\",");         // 时间
	        dataSbf.append("stroke:\""+obj[2]+"\",");         // 冲程
	        dataSbf.append("SPM:\""+obj[3]+"\",");         // 冲次
	        dataSbf.append("fmax:\""+obj[4]+"\",");         // 最大载荷
	        dataSbf.append("fmin:\""+obj[5]+"\",");         // 最小载荷
	        
	        String positionCurveData="";
	        String loadCurveData="";
	        String powerCurveData="";
	        String currentCurveData="";
	        String rpmCurveData="";
	        String powerCurveData_raw="";
	        String currentCurveData_raw="";
	        String rpmCurveData_raw="";
	        SerializableClobProxy   proxy=null;
	        CLOB realClob=null;
	        if(obj[6]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[6]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				positionCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(obj[7]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[7]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				loadCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(obj[14]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[14]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				powerCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(obj[15]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[15]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				currentCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(obj[16]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[16]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				rpmCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(obj[17]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[17]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				powerCurveData_raw=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(obj[18]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[18]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				currentCurveData_raw=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(obj[19]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[19]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				rpmCurveData_raw=StringManagerUtils.CLOBtoString(realClob);
			}
	        
	        dataSbf.append("positionCurveData:\""+positionCurveData+"\",");         //位移曲线
	        dataSbf.append("loadCurveData:\""+loadCurveData+"\",");             //载荷曲线
	        
	        dataSbf.append("upStrokeIMax:\""+obj[8]+"\",");         // 上冲程最大电流
	        dataSbf.append("downStrokeIMax:\""+obj[9]+"\",");         // 下冲程最大电流
	        dataSbf.append("iDegreeBalance:\""+obj[10]+"\",");         // 电流平衡度
	        dataSbf.append("upStrokeWattMax:\""+obj[11]+"\",");         // 上冲程最大功率
	        dataSbf.append("downStrokeWattMax:\""+obj[12]+"\",");         // 下冲程最大功率
	        dataSbf.append("wattDegreeBalance:\""+obj[13]+"\",");         // 功率平衡度
	        
	        dataSbf.append("powerCurveData:\""+powerCurveData+"\",");            //功率曲线
	        dataSbf.append("currentCurveData:\""+currentCurveData+"\",");           //电流曲线
	        dataSbf.append("rpmCurveData:\""+rpmCurveData+"\",");           //转速曲线
	        
	        dataSbf.append("powerCurveData_raw:\""+powerCurveData_raw+"\",");            //功率曲线
	        dataSbf.append("currentCurveData_raw:\""+currentCurveData_raw+"\",");           //电流曲线
	        dataSbf.append("rpmCurveData_raw:\""+rpmCurveData_raw+"\"");           //转速曲线
	        
	        dataSbf.append("}");
		}else{
			dataSbf.append("{success:true,");
			dataSbf.append("wellName:\"\",");
	        dataSbf.append("acqTime:\"\",");
	        dataSbf.append("stroke:\"\",");  
	        dataSbf.append("SPM:\"\",");
	        dataSbf.append("fmax:\"\",");         // 最大载荷
	        dataSbf.append("fmin:\"\",");         // 最小载荷
	        dataSbf.append("upStrokeIMax:\"\",");
	        dataSbf.append("downStrokeIMax:\"\",");
	        dataSbf.append("iDegreeBalance:\"\",");
	        dataSbf.append("upStrokeWattMax:\"\",");
	        dataSbf.append("downStrokeWattMax:\"\",");
	        dataSbf.append("wattDegreeBalance:\"\",");
	        dataSbf.append("positionCurveData:\"\",");
	        dataSbf.append("loadCurveData:\"\",");
	        dataSbf.append("powerCurveData:\"\",");
	        dataSbf.append("currentCurveData:\"\",");
	        dataSbf.append("rpmCurveData:\"\"");
	        dataSbf.append("upstrokeamax_raw:\"\",");
	        dataSbf.append("downstrokeamax_raw:\"\",");
	        dataSbf.append("adegreeofbalance_raw:\"\",");
	        dataSbf.append("upstrokepmax_raw:\"\",");
	        dataSbf.append("downstrokepmax_raw:\"\",");
	        dataSbf.append("pdegreeofbalance_raw:\"\",");
	        
	        dataSbf.append("powerCurveData_raw:\"\",");
	        dataSbf.append("currentCurveData_raw:\"\",");
	        dataSbf.append("rpmCurveData_raw:\"\"");
	        dataSbf.append("}");
		}
		return dataSbf.toString().replaceAll("null", "");
	}
	
	public String getSingleElecInverDiagramCheckData(String recordId,String wellName) throws SQLException, IOException{
        StringBuffer dataSbf = new StringBuffer();
        String table="tbl_rpc_diagram_hist";
        if(!StringManagerUtils.isNotNull(wellName)){
        	table="tbl_rpc_diagram_latest";
        }
        String sql="select well.wellName,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
        		+ " t.stroke,t.spm,t.fmax,t.fmin,"
        		+ " t.position_curve,t.load_curve,"
        		+ " t.position360_curve,t.angle360_curve,t.load360_curve"
        		+ " from "+table+" t,tbl_wellinformation well "
        		+ " where t.wellId=well.id  and t.id="+recordId;
		List<?> list=this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			SerializableClobProxy proxy=null;
			CLOB realClob=null;
			String positionCurveData="",loadCurveData="",position360CurveData="",angle360CurveData="",load360CurveData="";
			
			if(obj[6]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[6]);
				realClob = (CLOB) proxy.getWrappedClob();
				positionCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[7]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[7]);
				realClob = (CLOB) proxy.getWrappedClob();
				loadCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			
			if(obj[8]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[8]);
				realClob = (CLOB) proxy.getWrappedClob();
				position360CurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[9]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[9]);
				realClob = (CLOB) proxy.getWrappedClob();
				angle360CurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[10]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[10]);
				realClob = (CLOB) proxy.getWrappedClob();
				load360CurveData=StringManagerUtils.CLOBtoString(realClob);
			}
	        dataSbf.append("{success:true,");
	        dataSbf.append("wellName:\""+obj[0]+"\",");           // 井名
	        dataSbf.append("acqTime:\""+obj[1]+"\",");         // 时间
	        dataSbf.append("stroke:\""+obj[2]+"\",");         // 冲程
	        dataSbf.append("SPM:\""+obj[3]+"\",");         // 冲次
	        dataSbf.append("fmax:\""+obj[4]+"\",");         // 最大载荷
	        dataSbf.append("fmin:\""+obj[5]+"\",");         // 最小载荷
	        
	        dataSbf.append("positionCurveData:\""+positionCurveData+"\",");         //位移曲线
	        dataSbf.append("loadCurveData:\""+loadCurveData+"\",");             //载荷曲线
	        
	        dataSbf.append("position360CurveData:\""+position360CurveData+"\",");         //位移曲线_360
	        dataSbf.append("angle360CurveData:\""+angle360CurveData+"\",");         //角度曲线_360
	        dataSbf.append("load360CurveData:\""+load360CurveData+"\"");             //载荷曲线_360
	        
	        dataSbf.append("}");
		}else{
			dataSbf.append("{success:true,");
			dataSbf.append("wellName:\"\",");
	        dataSbf.append("acqTime:\"\",");
	        dataSbf.append("stroke:\"\",");  
	        dataSbf.append("SPM:\"\",");
	        dataSbf.append("fmax:\"\",");         // 最大载荷
	        dataSbf.append("fmin:\"\",");         // 最小载荷
	        dataSbf.append("positionCurveData:\"\",");         //位移曲线
	        dataSbf.append("loadCurveData:\"\",");             //载荷曲线
	        dataSbf.append("position360CurveData:\"\",");
	        dataSbf.append("angle360CurveData:\"\",");
	        dataSbf.append("load360CurveData:\"\"");
	        dataSbf.append("}");
		}
		return dataSbf.toString().replaceAll("null", "");
	}
	
	public boolean exportSingleElecInverDiagramCheckData(String recordId,String wellName,HttpServletResponse response) throws SQLException, IOException{
		StringBuffer dataSbf = new StringBuffer();
        String table="tbl_rpc_diagram_hist";
        if(!StringManagerUtils.isNotNull(wellName)){
        	table="tbl_rpc_diagram_latest";
        }
        String sql="select well.wellName,to_char(t.acqTime,'yyyymmddhh24miss') as acqTime,"
        		+ " t.position360_curve,t.angle360_curve,t.load360_curve"
        		+ " from "+table+" t,tbl_wellinformation well "
        		+ " where t.wellId=well.id  and t.id="+recordId;
		List<?> list=this.findCallSql(sql);
		boolean result=false;
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			SerializableClobProxy proxy=null;
			CLOB realClob=null;
			String diagramWellName=obj[0]+"";
			String acqTime=obj[1]+"";
			String[] position360CurveData={},angle360CurveData={},load360CurveData={};
			if(obj[2]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[2]);
				realClob = (CLOB) proxy.getWrappedClob();
				position360CurveData=StringManagerUtils.CLOBtoString(realClob).split(",");
			}
			
			if(obj[3]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[3]);
				realClob = (CLOB) proxy.getWrappedClob();
				angle360CurveData=StringManagerUtils.CLOBtoString(realClob).split(",");
			}
			
			if(obj[4]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[4]);
				realClob = (CLOB) proxy.getWrappedClob();
				load360CurveData=StringManagerUtils.CLOBtoString(realClob).split(",");
			}
	        dataSbf.append("[");
	        if(StringManagerUtils.stringToFloat(angle360CurveData[0])>0){ //如果是逆时针 将最后0°的数据补充到最前端360°数据
	        	dataSbf.append("{\"angle\":360,");
		        dataSbf.append("\"position\":"+position360CurveData[position360CurveData.length-1]+",");
		        dataSbf.append("\"load\":"+load360CurveData[load360CurveData.length-1]+"},");
	        }
	        for(int i=0;i<angle360CurveData.length;i++){
	        	dataSbf.append("{\"angle\":"+(int)StringManagerUtils.stringToFloat(angle360CurveData[i])+",");
		        dataSbf.append("\"position\":"+position360CurveData[i]+",");
		        dataSbf.append("\"load\":"+load360CurveData[i]+"},");
	        }
	        if(dataSbf.toString().endsWith(",")){
	        	dataSbf.deleteCharAt(dataSbf.length() - 1);
	        }
	        
	        if(StringManagerUtils.stringToFloat(angle360CurveData[0])==0){ //如果是顺时针 将最前0°数据补充到最后段360°数据
	        	dataSbf.append(",{\"angle\":360,");
		        dataSbf.append("\"position\":"+position360CurveData[0]+",");
		        dataSbf.append("\"load\":"+load360CurveData[0]+"}");
	        }
	        
	        dataSbf.append("]}");
	        String fileName=diagramWellName+"反演功图数据-"+acqTime;
	        String sheetName="反演功图数据";
	        String head="角度(°),位移(m),载荷(kN)";
	        String field="angle,position,load";
	        result= this.service.exportGridPanelDataWhithOutTime(response,fileName,sheetName,head,field,dataSbf.toString());
		}
		return result;
	}
	
	public String getInverDiagramChartData(Page pager,String orgId,String wellName,String startDate,String endDate,String diagramType) throws SQLException, IOException {
		StringBuffer dataSbf = new StringBuffer();
		int intPage = pager.getPage();
		int limit = pager.getLimit();
		int start = pager.getStart();
		int maxvalue = limit + start;
		String allsql="",sql="";
		allsql="select id,wellName,to_char(acqTime,'yyyy-mm-dd hh24:mi:ss'),";
		if("FSDiagram".equalsIgnoreCase(diagramType)){//地面功图
			allsql+="stroke,spm,fmax,fmin,load_curve,";
		}else if("PSDiagram".equalsIgnoreCase(diagramType)){//电功图
			allsql+="upStrokeWattMax,downStrokeWattMax,wattDegreeBalance,power_curve,";
		}else if("ASDiagram".equalsIgnoreCase(diagramType)){//电流图
			allsql+="upStrokeIMax,downStrokeIMax,IDegreeBalance,current_curve,";
		}
		allsql+="position_curve from viw_rpc_diagramquery_latest where 1=1 and datasource=1 ";
		
		if(StringManagerUtils.isNotNull(wellName)){  // 井名不为空 查询该井历史曲线
			allsql=allsql.replaceAll("viw_rpc_diagramquery_latest", "viw_rpc_diagramquery_hist");
			allsql+=" and acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1 and wellName='"+wellName+"' order by acqTime desc";
		}else{// 井名为空 查询每口井实时曲线
			allsql+=" and orgid in("+orgId+") order by sortnum";
		}
		sql="select b.* from (select a.*,rownum as rn from  ("+ allsql +") a where rownum <= "+ maxvalue +") b where rn > "+ start +"";
		int totals = getTotalCountRows(allsql);//获取总记录数
		List<?> list=this.findCallSql(sql);
		PageHandler handler = new PageHandler(intPage, totals, limit);
		int totalPages = handler.getPageCount(); // 总页数
		dataSbf.append("{success:true,totals:\"" + totals + "\",totalPages:\"" + totalPages + "\",startDate:\"" + startDate + "\",endDate:\"" + endDate + "\",list:[");
		
		for (int i = 0; i < list.size(); i++) {
			Object[] obj=(Object[])list.get(i);
			CLOB realClob=null;
			SerializableClobProxy   proxy=null;
			String DiagramXData="";
	        String DiagramYData="";
	        dataSbf.append("{\"id\":"+obj[0]+",");
	        dataSbf.append("wellName:\""+obj[1]+"\",");           // 井名
	        dataSbf.append("acqTime:\""+obj[2]+"\",");         // 时间
	        
	        if("FSDiagram".equalsIgnoreCase(diagramType)){//地面功图
	        	dataSbf.append("stroke:\""+obj[3]+"\",");
		        dataSbf.append("SPM:\""+obj[4]+"\",");
		        dataSbf.append("fmax:\""+obj[5]+"\",");
		        dataSbf.append("fmin:\""+obj[6]+"\",");
		        
		        if(obj[7]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[7]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					DiagramYData=StringManagerUtils.CLOBtoString(realClob);
				}
		        if(obj[8]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[8]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					DiagramXData=StringManagerUtils.CLOBtoString(realClob);
				}
		        dataSbf.append("positionCurveData:\""+DiagramXData+"\",");
		        dataSbf.append("loadCurveData:\""+DiagramYData+"\"},");
	        	
			}else if("PSDiagram".equalsIgnoreCase(diagramType)){//电功图
				dataSbf.append("upStrokeWattMax:\""+obj[3]+"\",");         // 上冲程最大功率
		        dataSbf.append("downStrokeWattMax:\""+obj[4]+"\",");         // 下冲程最大功率
		        dataSbf.append("wattDegreeBalance:\""+obj[5]+"\",");         // 功率平衡度
		        
		        if(obj[6]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[6]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					DiagramYData=StringManagerUtils.CLOBtoString(realClob);
				}
		        if(obj[7]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[7]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					DiagramXData=StringManagerUtils.CLOBtoString(realClob);
				}
		        dataSbf.append("positionCurveData:\""+DiagramXData+"\",");
		        dataSbf.append("powerCurveData:\""+DiagramYData+"\"},");
			}else if("ASDiagram".equalsIgnoreCase(diagramType)){//电流图
				dataSbf.append("upStrokeIMax:\""+obj[3]+"\",");         // 上冲程最大功率
		        dataSbf.append("downStrokeIMax:\""+obj[4]+"\",");         // 下冲程最大功率
		        dataSbf.append("iDegreeBalance:\""+obj[5]+"\",");         // 功率平衡度
		        
		        if(obj[6]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[6]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					DiagramYData=StringManagerUtils.CLOBtoString(realClob);
				}
		        if(obj[7]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[7]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					DiagramXData=StringManagerUtils.CLOBtoString(realClob);
				}
		        dataSbf.append("positionCurveData:\""+DiagramXData+"\",");
		        dataSbf.append("currentCurveData:\""+DiagramYData+"\"},");
			}
		}
		if(dataSbf.toString().endsWith(",")){
			dataSbf.deleteCharAt(dataSbf.length() - 1);
		}
		dataSbf.append("]}");
		return dataSbf.toString().replaceAll("null", "");
	}
	
	
	
	public boolean saveMQTTTransferElecDiagramData(TransferDiagram transferDiagram) throws SQLException, ParseException{
		String SStr=StringUtils.join(transferDiagram.getS(), ",");
		String FStr=StringUtils.join(transferDiagram.getF(), ",");
		String PStr=StringUtils.join(transferDiagram.getWatt(), ",");
		String AStr=StringUtils.join(transferDiagram.getI(), ",");
		String RPMStr=StringUtils.join(transferDiagram.getRPM(), ",");
		
		String P_FilterStr=StringUtils.join(transferDiagram.getWatt_Filter(), ",");
		String A_FilterStr=StringUtils.join(transferDiagram.getI_Filter(), ",");
		String RPM_FilterStr=StringUtils.join(transferDiagram.getRPM_Filter(), ",");
		
		String F360Str="",S360Str="",A360Str="";
		if(transferDiagram.getF360()!=null){
			F360Str=StringUtils.join(transferDiagram.getF360(), ",");
		}
		if(transferDiagram.getS360()!=null){
			S360Str=StringUtils.join(transferDiagram.getS360(), ",");
		}
		if(transferDiagram.getA360()!=null){
			A360Str=StringUtils.join(transferDiagram.getA360(), ",");
		}
		
		return this.getBaseDao().saveSurfaceCard(transferDiagram.getWellName(),transferDiagram.getAcquisitionTime(),
				transferDiagram.getF().size(),transferDiagram.getStroke(),transferDiagram.getSPM(),transferDiagram.getMaxF(),transferDiagram.getMinF(),
				SStr,FStr,
				S360Str,A360Str,F360Str,
				A_FilterStr,P_FilterStr,RPM_FilterStr,
				transferDiagram.getUpstrokeIMax_Filter(),transferDiagram.getDownstrokeIMax_Filter(),transferDiagram.getUpstrokeWattMax_Filter(),transferDiagram.getDownstrokeWattMax_Filter(),
				transferDiagram.getIDegreeBalance_Filter(),transferDiagram.getWattDegreeBalance_Filter(),
				AStr,PStr,RPMStr,
				transferDiagram.getUpstrokeIMax(),transferDiagram.getDownstrokeIMax(),transferDiagram.getUpstrokeWattMax(),transferDiagram.getDownstrokeWattMax(),
				transferDiagram.getIDegreeBalance(),transferDiagram.getWattDegreeBalance(),
				transferDiagram.getMotorInputAvgWatt(),
				transferDiagram.getResultStatus(),
				transferDiagram.getSignal(),transferDiagram.getInterval2(),transferDiagram.getVer());
	}
	
	
	public boolean reInverDiagram(String recordId,InversioneFSdiagramResponseData inversioneFSdiagramResponseData) throws SQLException, ParseException{
		String SStr=StringUtils.join(inversioneFSdiagramResponseData.getS(), ",");
		String FStr=StringUtils.join(inversioneFSdiagramResponseData.getF(), ",");
		String PStr=StringUtils.join(inversioneFSdiagramResponseData.getWatt(), ",");
		String AStr=StringUtils.join(inversioneFSdiagramResponseData.getI(), ",");
		String RPMStr=StringUtils.join(inversioneFSdiagramResponseData.getRPM(), ",");
		
		String F360Str="",S360Str="",A360Str="";
		if(inversioneFSdiagramResponseData.getF360()!=null){
			F360Str=StringUtils.join(inversioneFSdiagramResponseData.getF360(), ",");
		}
		if(inversioneFSdiagramResponseData.getS360()!=null){
			S360Str=StringUtils.join(inversioneFSdiagramResponseData.getS360(), ",");
		}
		if(inversioneFSdiagramResponseData.getA360()!=null){
			A360Str=StringUtils.join(inversioneFSdiagramResponseData.getA360(), ",");
		}
		
		return this.getBaseDao().reInverDiagram(recordId,inversioneFSdiagramResponseData.getAcquisitionTime(),
				inversioneFSdiagramResponseData.getCNT(),inversioneFSdiagramResponseData.getStroke(),inversioneFSdiagramResponseData.getSPM(),
				inversioneFSdiagramResponseData.getMaxF(),inversioneFSdiagramResponseData.getMinF(),
				SStr,FStr,
				S360Str,A360Str,F360Str,
				AStr,PStr,RPMStr,
				inversioneFSdiagramResponseData.getUpstrokeIMax(),inversioneFSdiagramResponseData.getDownstrokeIMax(),
				inversioneFSdiagramResponseData.getUpstrokeWattMax(),inversioneFSdiagramResponseData.getDownstrokeWattMax(),
				inversioneFSdiagramResponseData.getIDegreeBalance(),inversioneFSdiagramResponseData.getWattDegreeBalance(),
				inversioneFSdiagramResponseData.getResultStatus());
	}
	
	
	public int saveMQTTTransferElecDiscreteData(TransferDiscrete transferDiscrete) throws SQLException, ParseException{
		String currentaalarm="";
		String currentbalarm="";
		String currentcalarm="";
		String voltageaalarm="";
		String voltagebalarm="";
		String voltagecalarm="";
		String resultString=transferDiscrete.getResultStr();
		if(!StringManagerUtils.isNotNull(resultString)){
			resultString=transferDiscrete.getResultCode()+"";
		}
		String updateDiscreteData="update tbl_rpc_discrete_latest t set t.CommStatus=1,"
				+ "t.runFrequency="+transferDiscrete.getFREQ()+","
				+ "t.signal="+transferDiscrete.getSignal()+","
				+ "t.interval="+transferDiscrete.getInterval2()+","
				+ "t.deviceVer='"+transferDiscrete.getVer()+"',"
				+ "t.AcqTime=to_date('"+transferDiscrete.getAcquisitionTime()+"','yyyy-mm-dd hh24:mi:ss')"
				+ ",t.RunTimeEfficiency= "+transferDiscrete.getRunEfficiency().getEfficiency()
				+ " ,t.RunTime= "+transferDiscrete.getRunEfficiency().getTime()
//				+ " ,t.RunRange= '"+transferDiscrete.getRunEfficiency().getRangeString()+"'"
				+ " ,t.todayKWattH= "+transferDiscrete.getTodayEnergy().getWatt()
				+ " ,t.todayPKWattH= "+transferDiscrete.getTodayEnergy().getPWatt()
				+ " ,t.todayNKWattH= "+transferDiscrete.getTodayEnergy().getNWatt()
				+ " ,t.todayKVarH= "+transferDiscrete.getTodayEnergy().getVar()
				+ " ,t.todayPKVarH= "+transferDiscrete.getTodayEnergy().getPVar()
				+ " ,t.todayNKVarH= "+transferDiscrete.getTodayEnergy().getNVar()
				+ " ,t.todayKVAH= "+transferDiscrete.getTodayEnergy().getVA()
				
				+ " ,t.totalKWattH= "+transferDiscrete.getTotalEnergy().getWatt()
				+ " ,t.totalPKWattH= "+transferDiscrete.getTotalEnergy().getPWatt()
				+ " ,t.totalNKWattH= "+transferDiscrete.getTotalEnergy().getNWatt()
				+ " ,t.totalKVarH= "+transferDiscrete.getTotalEnergy().getVar()
				+ " ,t.totalPKVarH= "+transferDiscrete.getTotalEnergy().getPVar()
				+ " ,t.totalNKVarH= "+transferDiscrete.getTotalEnergy().getNVar()
				+ " ,t.totalKVAH= "+transferDiscrete.getTotalEnergy().getVA()
				+ ",t.RunStatus="+(transferDiscrete.getRunStatus()?1:0)
				+ " ,t.resultCode= "+transferDiscrete.getResultCode()
				+ " ,t.resultString= '"+resultString+"'"
				+ " ,t.IaAlarm= '"+currentaalarm+"'"
				+ " ,t.IbAlarm= '"+currentbalarm+"'"
				+ " ,t.IcAlarm= '"+currentcalarm+"'"
				+ " ,t.VaAlarm= '"+voltageaalarm+"'"
				+ " ,t.VbAlarm= '"+voltagebalarm+"'"
				+ " ,t.VcAlarm= '"+voltagecalarm+"'"
				+ " ,t.Ia= "+transferDiscrete.getI().getA()+""
				+ " ,t.Ib= "+transferDiscrete.getI().getB()+""
				+ " ,t.Ic= "+transferDiscrete.getI().getC()+""
				+ " ,t.IAvg= "+transferDiscrete.getI().getAvg()+""
				+ " ,t.Va= "+transferDiscrete.getV().getA()+""
				+ " ,t.Vb= "+transferDiscrete.getV().getB()+""
				+ " ,t.Vc= "+transferDiscrete.getV().getC()+""
				+ " ,t.VAvg= "+transferDiscrete.getV().getAvg()+""
				+ " ,t.watt3= "+transferDiscrete.getWatt().getSum()+""
				+ " ,t.WattA= "+transferDiscrete.getWatt().getA()+""
				+ " ,t.WattB= "+transferDiscrete.getWatt().getB()+""
				+ " ,t.WattC= "+transferDiscrete.getWatt().getC()+""
				+ " ,t.var3= "+transferDiscrete.getVar().getSum()+""
				+ " ,t.VarA= "+transferDiscrete.getVar().getA()+""
				+ " ,t.VarB= "+transferDiscrete.getVar().getB()+""
				+ " ,t.VarC= "+transferDiscrete.getVar().getC()+""
				+ " ,t.va3= "+transferDiscrete.getVA().getSum()+""
				+ " ,t.VAA= "+transferDiscrete.getVA().getA()+""
				+ " ,t.VAB= "+transferDiscrete.getVA().getB()+""
				+ " ,t.VAC= "+transferDiscrete.getVA().getC()+""
				+ " ,t.ReversePower= "+0+""
				+ " ,t.pf3= "+transferDiscrete.getPF().getSum()+""
				+ " ,t.PFA= "+transferDiscrete.getPF().getA()+""
				+ " ,t.PFB= "+transferDiscrete.getPF().getB()+""
				+ " ,t.PFC= "+transferDiscrete.getPF().getC()+""
				+ " ,t.IaUpLimit= "+transferDiscrete.getElectricLimit().getI().getA().getMax()+""
				+ " ,t.IaDownLimit= "+transferDiscrete.getElectricLimit().getI().getA().getMin()+""
				+ " ,t.IaZero= "+transferDiscrete.getElectricLimit().getI().getA().getZero()+""
				+ " ,t.IbUpLimit= "+transferDiscrete.getElectricLimit().getI().getB().getMax()+""
				+ " ,t.IbDownLimit= "+transferDiscrete.getElectricLimit().getI().getB().getMin()+""
				+ " ,t.IbZero= "+transferDiscrete.getElectricLimit().getI().getB().getZero()+""
				+ " ,t.IcUpLimit= "+transferDiscrete.getElectricLimit().getI().getC().getMax()+""
				+ " ,t.IcDownLimit= "+transferDiscrete.getElectricLimit().getI().getC().getMin()+""
				+ " ,t.IcZero= "+transferDiscrete.getElectricLimit().getI().getC().getZero()+""
				+ " ,t.VaUpLimit= "+transferDiscrete.getElectricLimit().getV().getA().getMax()+""
				+ " ,t.VaDownLimit= "+transferDiscrete.getElectricLimit().getV().getA().getMin()+""
				+ " ,t.VaZero= "+transferDiscrete.getElectricLimit().getV().getA().getZero()+""
				+ " ,t.VbUpLimit= "+transferDiscrete.getElectricLimit().getV().getB().getMax()+""
				+ " ,t.VbDownLimit= "+transferDiscrete.getElectricLimit().getV().getB().getMin()+""
				+ " ,t.VbZero= "+transferDiscrete.getElectricLimit().getV().getB().getZero()+""
				+ " ,t.VcUpLimit= "+transferDiscrete.getElectricLimit().getV().getC().getMax()+""
				+ " ,t.VcDownLimit= "+transferDiscrete.getElectricLimit().getV().getC().getMin()+""
				+ " ,t.VcZero= "+transferDiscrete.getElectricLimit().getV().getC().getZero()+""
				+ " where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+transferDiscrete.getWellName()+"') ";
		int result= this.getBaseDao().updateOrDeleteBySql(updateDiscreteData);
		
		String updateRunRangeClobSql="update tbl_rpc_discrete_latest t set t.commrange=? where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+transferDiscrete.getWellName()+"') ";
		List<String> clobCont=new ArrayList<String>();
		clobCont.add(transferDiscrete.getRunEfficiency().getRangeString());
		result=this.getBaseDao().executeSqlUpdateClob(updateRunRangeClobSql,clobCont);
		
		return result;
	}
	
	public boolean saveMQTTTransferElecDailyData(TransferDaily transferDaily) throws SQLException, ParseException{
		return this.getBaseDao().saveTotalCalculationData(transferDaily);
	}
	
	public String getElectricAnalysisRealtimeProfilePieData(String orgId,String type) throws SQLException, IOException{
		StringBuffer result_json = new StringBuffer();
		String tableName="";
		if("wattdegreebalanceName".equalsIgnoreCase(type)||"idegreebalanceName".equalsIgnoreCase(type)){
			tableName="viw_rpc_diagram_latest";
		}else{
			tableName="viw_rpc_discrete_latest";
		}
        String sql="select  t."+type+" ,count(id) from "+tableName+" t "
        		+ " where   liftingtype>=200 and liftingtype<400 "
        		+ " and length(t.signinid)=16"
        		+ " and t.org_id in("+orgId+")  group by t."+type+""
        		+ " order by t."+type;
		List<?> list=this.findCallSql(sql);
		String columns =  "["
		+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50},"
		+ "{ \"header\":\"统计项\",\"dataIndex\":\"item\"},"
		+ "{ \"header\":\"井数\",\"dataIndex\":\"count\" }"
		+ "]";
		result_json.append("{ \"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			if(StringManagerUtils.isNotNull(obj[0]+"")){
				result_json.append("{\"id\":\""+(i+1)+"\",");
				result_json.append("\"item\":\""+obj[0]+"\",");
				result_json.append("\"count\":"+obj[1]+"},");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	
	public String getElectricAnalysisRealtimeProfileData(String orgId) throws SQLException, IOException{
		StringBuffer result_json = new StringBuffer();
        String runWellCountSql="select count(1) from viw_rpc_discrete_latest t where  length(t.signinid)=16 and t.commstatus=1 and t.runStatus=1 and t.org_id in ("+orgId+")";
        String stopWellCountSql="select count(1) from viw_rpc_discrete_latest t where length(t.signinid)=16 and t.commstatus=1 and t.runStatus=0 and t.org_id in ("+orgId+")";
        String warnningWellCountSql="select count(1) from viw_rpc_discrete_latest t,viw_rpc_diagram_latest t2 "
        		+ " where t.wellId=t2.wellId and length(t.signinid)=16"
        		+ " and (t.resultAlarmLevel>0  or t.commAlarmLevel>0 or t.runAlarmLevel>0 or t2.iDegreeBalanceAlarmLevel>0 or t2.wattDegreeBalanceAlarmlevel>0)  "
        		+ " and t.org_id in ("+orgId+")";
        int runWellCount=this.getTotalCountRows(runWellCountSql);
        int stopWellCount=this.getTotalCountRows(stopWellCountSql);
        int warnningWellCount=this.getTotalCountRows(warnningWellCountSql);
        
        result_json.append("{ \"success\":true,");
        result_json.append(" \"runWellCount\":"+runWellCount+",");
        result_json.append(" \"stopWellCount\":"+stopWellCount+",");
        result_json.append(" \"warnningWellCount\":"+warnningWellCount+"");
        result_json.append("}");
        
		return result_json.toString().replaceAll("null", "");
	}
	
	
	public String getElectricAnalysisRealtimeDatailsList(String orgId, String wellName, Page pager,String type,String wellType,String startDate,String endDate,String statValue,
			String egkmc,String timeEff,String wattBalance,String iBalance,String rydl)
			throws Exception {
		DataDictionary ddic = null;
		String columns= "";
		String sql="";
		String sqlHis="";
		String finalSql="";
		String sqlAll="";
		columns=service.showTableHeadersColumns("elecInverDetails_Realtime");
		
		String sourcesql="select "
				+ " id,wellName,to_char(t.acqTime@'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "	commStatus,commStatusName,commAlarmLevel,"
				+ "	runStatus,runStatusName,runAlarmLevel,"
				+ " resultCode,resultName,resultAlarmLevel as resultAlarmLevel_E,"
				+ " runTime,runTimeEfficiency,t.runRange,"
				+ " Ia,Ib,Ic,IAvg,IStr,Va,Vb,Vc,VAvg,VStr,"
				+ " WattA,WattB,WattC,watt3,WattStr,"
				+ " VarA,VarB,VarC,var3,VarStr,"
				+ " PFA,PFB,PFC,pf3,PFStr,"
				+ " totalKWattH,totalPKWattH,totalNKWattH,totalKVarH,totalPKVarH,totalNKVarH,totalKVAH,"
				+ " todayKWattH,todayPKWattH,todayNKWattH,todayKVarH,todayPKVarH,todayNKVarH,todayKVAH,"
				+ " runFrequency,signal,interval,deviceVer "
				+ " from viw_rpc_discrete_latest t "
				+ " where t.org_id in("+orgId+") and length(t.signinid)=16 ";
		
		if(StringManagerUtils.isNotNull(wellType)){
			sourcesql+=" and liftingtype>="+wellType+" and liftingtype<("+wellType+"+100) ";
		}
		sql=sourcesql;
		sqlHis=sourcesql.replace("viw_rpc_discrete_latest", "viw_rpc_discrete_hist");
		
		if(StringManagerUtils.isNotNull(egkmc)){
			sql+=" and resultName='"+egkmc+"' ";
		}
		if(StringManagerUtils.isNotNull(timeEff)){
			sql+=" and runtimeefficiencyLevel='"+timeEff+"' ";
		}
		if(StringManagerUtils.isNotNull(rydl)){
			sql+=" and todayKWattHLevel='"+rydl+"' ";
		}
		if(StringManagerUtils.isNotNull(type)){
			if("runStatus".equalsIgnoreCase(type)){
				sql+=" and runStatusName='"+statValue+"' ";
			}else if("alarmStatus".equalsIgnoreCase(type)&&"1".equals(statValue)){
				sql+=" and (commAlarmLevel>0 or runAlarmLevel>0 or resultAlarmLevel>0 )";
			}else if("alarmStatus".equalsIgnoreCase(type)&&"0".equals(statValue)){
				sql+=" and commAlarmLevel=0 and runAlarmLevel=0 and resultAlarmLevel=0";
			}
		}
		sql+=" order by t.sortnum, t.wellName";
		
		sqlHis+=" and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1 ";
		sqlHis+=" and wellName='"+wellName+"'";
		sqlHis+=" order by t.acqTime desc";
		
		if(StringManagerUtils.isNotNull(wellName.trim())){
			sqlAll=sqlHis;
		}else{
			sqlAll=sql;
		}
		
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sqlAll+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		String getResult = this.findCustomPageBySqlEntity(sqlAll,finalSql, columns, 20 + "", pager);
		return getResult.replaceAll("//", "");
	}
	
	public String getRealtimeAnalysisAndAcqData(String id,String wellName)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String tableName="";
		if(StringManagerUtils.isNotNull(wellName)){
			tableName="viw_rpc_discrete_hist";
		}else{
			tableName="viw_rpc_discrete_latest";
		}
		String sql="select "
				+ " runTime,runTimeEfficiency,"
				+ " Ia,Ib,Ic,IAvg,Va,Vb,Vc,VAvg,"
				+ " WattA,WattB,WattC,watt3,"
				+ " VarA,VarB,VarC,var3,"
				+ " PFA,PFB,PFC,pf3,"
				+ " totalKWattH,totalPKWattH,totalNKWattH,totalKVarH,totalPKVarH,totalNKVarH,totalKVAH,"
				+ " todayKWattH,todayPKWattH,todayNKWattH,todayKVarH,todayPKVarH,todayNKVarH,todayKVAH,"
				+ " runFrequency,signal,interval,"
				+ " runrange,resultstring "
				+ " from "+tableName+" t where id="+id;
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			result_json.append("\"runTime\":\""+obj[0]+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[1]+"\",");
			result_json.append("\"Ia\":\""+obj[2]+"\",");
			result_json.append("\"Ib\":\""+obj[3]+"\",");
			result_json.append("\"Ic\":\""+obj[4]+"\",");
			result_json.append("\"IAvg\":\""+obj[5]+"\",");
			result_json.append("\"Va\":\""+obj[6]+"\",");
			result_json.append("\"Vb\":\""+obj[7]+"\",");
			result_json.append("\"Vc\":\""+obj[8]+"\",");
			result_json.append("\"VAvg\":\""+obj[9]+"\",");
			result_json.append("\"WattA\":\""+obj[10]+"\",");
			result_json.append("\"WattB\":\""+obj[11]+"\",");
			result_json.append("\"WattC\":\""+obj[12]+"\",");
			result_json.append("\"watt3\":\""+obj[13]+"\",");
			result_json.append("\"VarA\":\""+obj[14]+"\",");
			result_json.append("\"VarB\":\""+obj[15]+"\",");
			result_json.append("\"VarC\":\""+obj[16]+"\",");
			result_json.append("\"var3\":\""+obj[17]+"\",");
			result_json.append("\"PFA\":\""+obj[18]+"\",");
			result_json.append("\"PFB\":\""+obj[19]+"\",");
			result_json.append("\"PFC\":\""+obj[20]+"\",");
			result_json.append("\"pf3\":\""+obj[21]+"\",");
			result_json.append("\"totalKWattH\":\""+obj[22]+"\",");
			result_json.append("\"totalPKWattH\":\""+obj[23]+"\",");
			result_json.append("\"totalNKWattH\":\""+obj[24]+"\",");
			result_json.append("\"totalKVarH\":\""+obj[25]+"\",");
			result_json.append("\"totalPKVarH\":\""+obj[26]+"\",");
			result_json.append("\"totalNKVarH\":\""+obj[27]+"\",");
			result_json.append("\"totalKVAH\":\""+obj[28]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[29]+"\",");
			result_json.append("\"todayPKWattH\":\""+obj[30]+"\",");
			result_json.append("\"todayNKWattH\":\""+obj[31]+"\",");
			result_json.append("\"todayKVarH\":\""+obj[32]+"\",");
			result_json.append("\"todayPKVarH\":\""+obj[33]+"\",");
			result_json.append("\"todayNKVarH\":\""+obj[34]+"\",");
			result_json.append("\"todayKVAH\":\""+obj[35]+"\",");
			result_json.append("\"runFrequency\":\""+obj[36]+"\",");
			result_json.append("\"signal\":\""+obj[37]+"\",");
			result_json.append("\"interval\":\""+obj[38]+"\",");
			result_json.append("\"runRange\":\""+obj[39]+"\",");
			result_json.append("\"resultString\":\""+obj[40]+"\"");
		}
		result_json.append("}");
		return result_json.toString().replaceAll("null", "").replaceAll("//", "");
	}
	
	public String getRealtimeDiagramAnalysisAndAcqData(String id,String wellName)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String tableName="";
		if(StringManagerUtils.isNotNull(wellName)){
			tableName="tbl_rpc_diagram_hist";
		}else{
			tableName="tbl_rpc_diagram_latest";
		}
		String sql="select t.stroke,t.spm,t.fmax,t.fmin,"
				+ "t.upstrokeimax,t.downstrokeimax,t.idegreebalance,"
				+ "t.upstrokewattmax,t.downstrokewattmax,t.wattdegreebalance,"
				+ "signal,interval "
				+ " from "+tableName+" t where id="+id;
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			result_json.append("\"stroke\":\""+obj[0]+"\",");
			result_json.append("\"spm\":\""+obj[1]+"\",");
			result_json.append("\"fmax\":\""+obj[2]+"\",");
			result_json.append("\"fmin\":\""+obj[3]+"\",");
			result_json.append("\"upStrokeIMax\":\""+obj[4]+"\",");
			result_json.append("\"downStrokeIMax\":\""+obj[5]+"\",");
			result_json.append("\"iDegreeBalance\":\""+obj[6]+"\",");
			result_json.append("\"upStrokeWattMax\":\""+obj[7]+"\",");
			result_json.append("\"downStrokeWattMax\":\""+obj[8]+"\",");
			result_json.append("\"wattDegreeBalance\":\""+obj[9]+"\",");
			result_json.append("\"signal\":\""+obj[10]+"\",");
			result_json.append("\"interval\":\""+obj[11]+"\"");
			
		}
		result_json.append("}");
		return result_json.toString().replaceAll("null", "").replaceAll("//", "");
	}
	
	public String getElecAnalysisRealtimeDetailsCurveData(String wellName,String startDate,String endDate,String itemName,String itemCode,String type) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		String uplimit="";
		String downlimit="";
		String zero="";
		String tableName="viw_rpc_discrete_hist";
		String item=itemCode;
		if("Ia".equalsIgnoreCase(itemCode)||"Ib".equalsIgnoreCase(itemCode)||"Ic".equalsIgnoreCase(itemCode)
				||"Va".equalsIgnoreCase(itemCode)||"Vb".equalsIgnoreCase(itemCode)||"Vc".equalsIgnoreCase(itemCode)){
			item="t."+itemCode+",t."+itemCode+"UpLimit,t."+itemCode+"DownLimit,t."+itemCode+"Zero ";
		}else if("downAndUpStrokeIMax".equalsIgnoreCase(itemCode)){
			item="t.downStrokeIMax,t.upStrokeIMax ";
		}else if("downAndUpStrokeWattMax".equalsIgnoreCase(itemCode)){
			item="t.downStrokeWattMax,t.upStrokeWattMax ";
		}
		if("Discrete".equalsIgnoreCase(type)){
			tableName="viw_rpc_discrete_hist";
		}else if("Diagram".equalsIgnoreCase(type)){
			tableName="viw_rpc_diagramquery_hist";
		}
		String sql="select to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss'),"+item+" "
				+ " from "+tableName+" t "
				+ " where t.wellName='"+wellName+"' "
				+ " and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1 "
				+ " order by t.acqTime";
		
		
		int totals = getTotalCountRows(sql);//获取总记录数
		List<?> list=this.findCallSql(sql);
		
		dynSbf.append("{\"success\":true,\"totalCount\":" + totals + ",\"itemNum\":"+item.split(",").length + ",\"wellName\":\""+wellName+"\",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				if(obj[1]!=null){
					dynSbf.append("{ \"acqTime\":\"" + obj[0] + "\",");
					dynSbf.append("\"value\":\""+obj[1]+"\"");
					if("downAndUpStrokeIMax".equalsIgnoreCase(itemCode)||"downAndUpStrokeWattMax".equalsIgnoreCase(itemCode)){
						dynSbf.append(",\"value2\":\""+obj[2]+"\"");
					}
					dynSbf.append("},");
					if(obj.length==5&&i==list.size()-1){
						uplimit=obj[2]+"";
						downlimit=obj[3]+"";
						zero=obj[4]+"";
					}
				}
			}
			if(dynSbf.toString().endsWith(",")){
				dynSbf.deleteCharAt(dynSbf.length() - 1);
			}
		}
		dynSbf.append("],\"uplimit\":\""+uplimit+"\",\"downlimit\":\""+downlimit+"\",\"zero\":\""+zero+"\"}");
		return dynSbf.toString().replaceAll("null", "");
	}
	
	public String getElectricAnalysisDailyProfileData(String orgId,String date) throws SQLException, IOException{
		StringBuffer result_json = new StringBuffer();
        String runWellCountSql="select count(1) from viw_rpc_total_day t where length(t.signinid)=16 and  t.runStatus=1 and t.commStatus=1 and t.org_id in ("+orgId+") and t.calculatedate=to_date('"+date+"','yyyy-mm-dd')";
        String stopWellCountSql="select count(1) from viw_rpc_total_day t where length(t.signinid)=16 and  t.runStatus=0 and t.commStatus=1 and t.org_id in ("+orgId+") and t.calculatedate=to_date('"+date+"','yyyy-mm-dd')";
        String warnningWellCountSql="select count(1) from viw_rpc_total_day t where length(t.signinid)=16 and (t.resultAlarmLevel_E>0  or commAlarmLevel>0 or runAlarmLevel>0 or idegreebalanceAlarmLevel>0 or wattdegreebalanceAlarmLevel>0)  and t.org_id in ("+orgId+") and t.calculatedate=to_date('"+date+"','yyyy-mm-dd')";
        int runWellCount=this.getTotalCountRows(runWellCountSql);
        int stopWellCount=this.getTotalCountRows(stopWellCountSql);
        int warnningWellCount=this.getTotalCountRows(warnningWellCountSql);
        
        result_json.append("{ \"success\":true,");
        result_json.append(" \"runWellCount\":"+runWellCount+",");
        result_json.append(" \"stopWellCount\":"+stopWellCount+",");
        result_json.append(" \"warnningWellCount\":"+warnningWellCount+"");
        result_json.append("}");
        
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getElectricAnalysisDailyProfilePieData(String orgId,String date,String type) throws SQLException, IOException{
		StringBuffer result_json = new StringBuffer();
        String sql="select  t."+type+" ,count(id) from viw_rpc_total_day t "
        		+ " where length(t.signinid)=16 "
        		+ " and t.org_id in("+orgId+") "
        		+ " and t.calculatedate=to_date('"+date+"','yyyy-mm-dd')"
        		+ " group by t."+type+"";
        List<?> list=this.findCallSql(sql);
		String columns =  "["
		+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50},"
		+ "{ \"header\":\"统计项\",\"dataIndex\":\"item\"},"
		+ "{ \"header\":\"井数\",\"dataIndex\":\"count\" }"
		+ "]";
		result_json.append("{ \"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			if(StringManagerUtils.isNotNull(obj[0]+"")){
				result_json.append("{\"id\":\""+(i+1)+"\",");
				result_json.append("\"item\":\""+obj[0]+"\",");
				result_json.append("\"count\":"+obj[1]+"},");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getElectricAnalysisDailyDatailsList(String orgId,String wellName,Page pager,String  wellType,String  date,String startDate,String endDate)
			throws Exception {
		DataDictionary ddic = null;
		String columns= "";
		String sql="";
		String sqlHis="";
		String finalSql="";
		String sqlAll="";
		columns=service.showTableHeadersColumns("elecInverDaily");
		
		String sourcesql="select "
				+ " id,wellName,to_char(t.calculateDate@'yyyy-mm-dd') as calculateDate,"
				+ "	commStatus,commStatusName,commAlarmLevel,"
				+ " runStatus,runStatusName,runAlarmLevel,"
				+ " resultCode_E,resultName_E,resultAlarmLevel_E,"
				+ " runTime,runTimeEfficiency,t.runRange,"
				+ " SPMMax,SPMMin,SPM,SPMStr,"
				+ " FMax,FMin,F,FStr,"
				+ " IaMax,IaMin,Ia,IaStr,"
				+ " IbMax,IbMin,Ib,IbStr,"
				+ " IcMax,IcMin,Ic,IcStr,"
				+ " VaMax,VaMin,Va,VaStr,"
				+ " VbMax,VbMin,Vb,VbStr,"
				+ " VcMax,VcMin,Vc,VcStr,"
				+ " wattDegreeBalanceMax,wattDegreeBalanceMin,wattDegreeBalance,wattDegreeBalanceStr,wattDegreeBalanceLevel,wattDegreeBalanceAlarmLevel,"
				+ " iDegreeBalanceMax,iDegreeBalanceMin,iDegreeBalance,iDegreeBalanceStr,iDegreeBalanceLevel,iDegreeBalanceAlarmLevel,"
				+ " todayKWattH,todayPKWattH,todayNKWattH,todayKVarH,todayPKVarH,todayNKVarH,todayKVAH,"
				+ " signalMax,signalMin,signal,signalStr"
				+ " from viw_rpc_total_day t "
				+ " where t.org_id in("+orgId+") and length(t.signinid)=16";
		
		if(StringManagerUtils.isNotNull(wellType)){
			sourcesql+=" and liftingtype>="+wellType+" and liftingtype<("+wellType+"+100) ";
		}
		if(!StringManagerUtils.isNotNull(wellName)){
			sourcesql+=" and t.calculateDate=to_date('"+date+"','yyyy-mm-dd') order by t.sortnum";
		}else{
			sourcesql+=" and t.calculateDate between  to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') and t.wellName='"+wellName+"' order by t.calculateDate desc";
		}
		sqlAll=sourcesql;
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sqlAll+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		String getResult = this.findCustomPageBySqlEntity(sqlAll,finalSql, columns, 20 + "", pager);
//		System.out.println(getResult);
		return getResult.replaceAll("//", "");
	}
	
	
	@SuppressWarnings("deprecation")
	public String getFSdiagramOverlayData(Page pager,String orgId,String wellName,String calculateDate) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		String sql="select t.id,t.wellname,to_char(t.acqTime,'hh24:mi:ss'),t.stroke,t.spm,t.fmax,t.fmin,"
				+ " t.iDegreeBalanceLevel,t.iDegreeBalance,t.iDegreeBalanceAlarmLevel,"
				+ " t.wattDegreeBalanceLevel,t.wattDegreeBalance,t.wattDegreeBalanceAlarmLevel,"
				+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve  "
				+ " from viw_rpc_diagramquery_hist t "
				+ " where t.orgid in ("+orgId+") "
				+ " and t.acqTime between to_date('"+calculateDate+"','yyyy-mm-dd') and to_date('"+calculateDate+"','yyyy-mm-dd')+1 "
				+ " and t.wellname='"+wellName+"' "
				+ " order by t.acqTime";
		
		List<?> list=this.findCallSql(sql);
		
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50},"
				+ "{ \"header\":\"采集时间\",\"dataIndex\":\"calculateDate\"},"
				+ "{ \"header\":\"冲程(m)\",\"dataIndex\":\"stroke\"},"
				+ "{ \"header\":\"冲次(1/min)\",\"dataIndex\":\"spm\"},"
				+ "{ \"header\":\"最大载荷(kN)\",\"dataIndex\":\"fmax\"},"
				+ "{ \"header\":\"最小载荷(kN)\",\"dataIndex\":\"fmin\"},"
				+ "{ \"header\":\"电流平衡度(%)\",\"dataIndex\":\"iDegreeBalance\"},"
				+ "{ \"header\":\"电流平衡状态\",\"dataIndex\":\"iDegreeBalanceLevel\"},"
				+ "{ \"header\":\"功率平衡度(%)\",\"dataIndex\":\"wattDegreeBalance\"},"
				+ "{ \"header\":\"功率平衡状态\",\"dataIndex\":\"wattDegreeBalanceLevel\"}"
				+ "]";
		
		dynSbf.append("{\"success\":true,\"totalCount\":" + list.size() + ",\"wellName\":\""+wellName+"\",\"calculateDate\":\""+calculateDate+"\",\"columns\":"+columns+",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String positionCurveData="",loadCurveData="",powerCurveData="",currentCurveData="";
				SerializableClobProxy   proxy=null;
				CLOB realClob=null;
				Object[] obj = (Object[]) list.get(i);
				if(obj[13]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[13]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					positionCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				if(obj[14]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[14]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					loadCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				if(obj[15]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[15]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					powerCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				if(obj[16]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[16]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					currentCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				dynSbf.append("{ \"id\":\"" + obj[0] + "\",");
				dynSbf.append("\"wellName\":\"" + obj[1] + "\",");
				dynSbf.append("\"calculateDate\":\"" + obj[2] + "\",");
				dynSbf.append("\"stroke\":\""+obj[3]+"\",");
				dynSbf.append("\"spm\":\""+obj[4]+"\",");
				dynSbf.append("\"fmax\":\""+obj[5]+"\",");
				dynSbf.append("\"fmin\":\""+obj[6]+"\",");
				dynSbf.append("\"iDegreeBalanceLevel\":\"" + obj[7] + "\",");
				dynSbf.append("\"iDegreeBalance\":\"" + obj[8] + "\",");
				dynSbf.append("\"iDegreeBalanceAlarmLevel\":\"" + obj[9] + "\",");
				dynSbf.append("\"wattDegreeBalanceLevel\":\"" + obj[10] + "\",");
				dynSbf.append("\"wattDegreeBalance\":\"" + obj[11] + "\",");
				dynSbf.append("\"wattDegreeBalanceAlarmLevel\":\"" + obj[12] + "\",");
				dynSbf.append("\"positionCurveData\":\"" + positionCurveData + "\",");
				dynSbf.append("\"loadCurveData\":\"" + loadCurveData + "\",");
				dynSbf.append("\"powerCurveData\":\"" + powerCurveData + "\",");
				dynSbf.append("\"currentCurveData\":\"" + currentCurveData + "\"},");
			}
			if(dynSbf.toString().endsWith(",")){
				dynSbf.deleteCharAt(dynSbf.length() - 1);
			}
		}
		dynSbf.append("]}");
		return dynSbf.toString().replaceAll("null", "");
	}
	
	public String getDailyAnalysisAndAcqData(String id)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql="select t.runtime,t.runtimeefficiency,"
				+ " t.idegreebalance,t.idegreebalancemax,t.idegreebalancemin,"
				+ " t.wattdegreebalance,t.wattdegreebalancemax,t.wattdegreebalancemin,"
				+ " t.spm,t.spmmax,t.spmmin,t.f,t.fmax,t.fmin,"
				+ " t.ia,t.iamax,t.iamin,t.ib,t.ibmax,t.ibmin,t.ic,t.icmax,t.icmin,"
				+ " t.va,t.vamax,t.vamin,t.vb,t.vbmax,t.vbmin,t.vc,t.vcmax,t.vcmin,"
				+ " t.todayKWattH,t.todayPKWattH,t.todayNKWattH,t.todayKVarH,t.todayPKVarH,t.todayNKVarH,t.todayKVAH,"
				+ " t.signal,t.signalmax,t.signalmin,"
				+ " t.runrange,t.resultstring_e"
				+ " from tbl_rpc_total_day t where id="+id;
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			result_json.append("\"runTime\":\""+obj[0]+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[1]+"\",");
			result_json.append("\"iDegreeBalance\":\""+obj[2]+"\",");
			result_json.append("\"iDegreeBalanceMax\":\""+obj[3]+"\",");
			result_json.append("\"iDegreeBalanceMin\":\""+obj[4]+"\",");
			result_json.append("\"wattDegreeBalance\":\""+obj[5]+"\",");
			result_json.append("\"wattDegreeBalanceMax\":\""+obj[6]+"\",");
			result_json.append("\"wattDegreeBalanceMin\":\""+obj[7]+"\",");
			result_json.append("\"SPM\":\""+obj[8]+"\",");
			result_json.append("\"SPMMax\":\""+obj[9]+"\",");
			result_json.append("\"SPMMin\":\""+obj[10]+"\",");
			result_json.append("\"F\":\""+obj[11]+"\",");
			result_json.append("\"FMax\":\""+obj[12]+"\",");
			result_json.append("\"FMin\":\""+obj[13]+"\",");
			result_json.append("\"Ia\":\""+obj[14]+"\",");
			result_json.append("\"IaMax\":\""+obj[15]+"\",");
			result_json.append("\"IaMin\":\""+obj[16]+"\",");
			result_json.append("\"Ib\":\""+obj[17]+"\",");
			result_json.append("\"IbMax\":\""+obj[18]+"\",");
			result_json.append("\"IbMin\":\""+obj[19]+"\",");
			result_json.append("\"Ic\":\""+obj[20]+"\",");
			result_json.append("\"IcMax\":\""+obj[21]+"\",");
			result_json.append("\"IcMin\":\""+obj[22]+"\",");
			result_json.append("\"Va\":\""+obj[23]+"\",");
			result_json.append("\"VaMax\":\""+obj[24]+"\",");
			result_json.append("\"VaMin\":\""+obj[25]+"\",");
			result_json.append("\"Vb\":\""+obj[26]+"\",");
			result_json.append("\"VbMax\":\""+obj[27]+"\",");
			result_json.append("\"VbMin\":\""+obj[28]+"\",");
			result_json.append("\"Vc\":\""+obj[29]+"\",");
			result_json.append("\"VcMax\":\""+obj[30]+"\",");
			result_json.append("\"VcMin\":\""+obj[31]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[32]+"\",");
			result_json.append("\"todayPKWattH\":\""+obj[33]+"\",");
			result_json.append("\"todayNKWattH\":\""+obj[34]+"\",");
			result_json.append("\"todayKVarH\":\""+obj[35]+"\",");
			result_json.append("\"todayPKVarH\":\""+obj[36]+"\",");
			result_json.append("\"todayNKVarH\":\""+obj[37]+"\",");
			result_json.append("\"todayKVAH\":\""+obj[38]+"\",");
			result_json.append("\"signal\":\""+obj[39]+"\",");
			result_json.append("\"signalMax\":\""+obj[40]+"\",");
			result_json.append("\"signalMin\":\""+obj[41]+"\",");
			result_json.append("\"runRange\":\""+obj[42]+"\",");
			result_json.append("\"resultString_E\":\""+obj[43]+"\"");
			
		}
		result_json.append("}");
		return result_json.toString().replaceAll("null", "").replaceAll("//", "");
	}
	
	
	public String getDailyHistoryCurveData(String wellName,String startDate,String endDate,String itemName,String itemCode) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		if(!"runTime".equalsIgnoreCase(itemCode)
				&&!"runTimeEfficiency".equalsIgnoreCase(itemCode)
				&&!"todayKWattH".equalsIgnoreCase(itemCode)
				&&!"todayPKWattH".equalsIgnoreCase(itemCode)
				&&!"todayNKWattH".equalsIgnoreCase(itemCode)
				&&!"todayKVarH".equalsIgnoreCase(itemCode)
				&&!"todayPKVarH".equalsIgnoreCase(itemCode)
				&&!"todayNKVarH".equalsIgnoreCase(itemCode)
				&&!"todayKVAH".equalsIgnoreCase(itemCode)
			){
			itemCode="t."+itemCode+",t."+itemCode+"max,t."+itemCode+"min";
		}else{
			itemCode="t."+itemCode;
		}
		String sql="select to_char(t.calculateDate,'yyyy-mm-dd'),"+itemCode+" from tbl_rpc_total_day t,tbl_wellinformation well "
				+ " where t.wellId=well.id and  well.wellName='"+wellName+"' and t.calculateDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') order by t.calculateDate";
		
		int totals = getTotalCountRows(sql);//获取总记录数
		List<?> list=this.findCallSql(sql);
		
		dynSbf.append("{\"success\":true,\"totalCount\":" + totals+",\"itemNum\":"+itemCode.split(",").length + ",\"wellName\":\""+wellName+"\",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				if(obj[1]!=null){
					dynSbf.append("{ \"calculateDate\":\"" + obj[0] + "\",");
					dynSbf.append("\"value\":\""+obj[1]+"\"");
					if(obj.length==4){
						dynSbf.append(",\"maxValue\":\""+obj[2]+"\"");
						dynSbf.append(",\"minValue\":\""+obj[3]+"\"");
					}
					dynSbf.append("},");
				}
			}
			if(dynSbf.toString().endsWith(",")){
				dynSbf.deleteCharAt(dynSbf.length() - 1);
			}
		}
		dynSbf.append("]}");
		return dynSbf.toString();
	}
}
