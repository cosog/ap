package com.gao.service.datainterface;

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
import java.util.List;
import java.util.Map;

import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.stereotype.Service;

import com.gao.model.calculate.PCPCalculateRequestData;
import com.gao.model.calculate.RPCCalculateRequestData;
import com.gao.model.calculate.RPCCalculateResponseData;
import com.gao.model.calculate.PCPCalculateResponseData;
import com.gao.model.calculate.CommResponseData;
import com.gao.model.calculate.ElectricCalculateResponseData;
import com.gao.model.calculate.TimeEffResponseData;
import com.gao.model.calculate.TotalAnalysisRequestData;
import com.gao.model.calculate.TotalAnalysisResponseData;
import com.gao.service.base.BaseService;
import com.gao.utils.Config;
import com.gao.utils.OracleJdbcUtis;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

@SuppressWarnings("deprecation")
@Service("calculateDataService")
public class CalculateDataService<T> extends BaseService<T> {
	
	public String getObjectToElecCalculateRequestData(Object[] object) throws SQLException, IOException, ParseException{
		
		String AcquisitionTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String elecCalRequest="{\"AKString\":\"\","
				+ "\"WellName\":\""+object[0]+"\","
				+ "\"AcquisitionTime\":\""+object[63]+"\","
				+ "\"CurrentA\":"+object[44]+","
				+ "\"CurrentB\":"+object[45]+","
				+ "\"CurrentC\":"+object[46]+","
				+ "\"VoltageA\":"+object[47]+","
				+ "\"VoltageB\":"+object[48]+","
				+ "\"VoltageC\":"+object[49]+","
				+ "\"ActivePowerSum\":"+object[50]+","
				+ "\"ReactivePowerSum\":"+object[51]+","
				+ "\"CompositePowerFactor\":"+object[52]+""
				+ "}";
		return elecCalRequest;
	}
	
	public String getObjectToTimeEffCalculateRequestData(Object[] object,int runStatus) throws SQLException, IOException, ParseException{
		
		String elecCalRequest="{\"AKString\":\"\","
				+ "\"WellName\":\""+object[0]+"\","
				+ "\"AcquisitionTime\":\""+object[63]+"\","
				+ "\"RunStatus\":"+runStatus+","
				+ "\"TotalAPC\":"+object[53]+","
				+ "\"TotalRPC\":"+object[54]+""
				+ "}";
		return elecCalRequest;
	}
	
	public String getObjectToCommCalculateRequestData(Object[] object) throws SQLException, IOException, ParseException{
		String elecCalRequest="{\"AKString\":\"\","
				+ "\"WellName\":\""+object[0]+"\","
				+ "\"AcquisitionTime\":\""+object[2]+"\","
				+ "\"CommStatus\":1"
				+ "}";
		return elecCalRequest;
	}
	
	public String getObjectToRPCCalculateRequestData(Object[] object) throws SQLException, IOException, ParseException{
		Gson gson = new Gson();
		RPCCalculateRequestData calculateRequestData=new RPCCalculateRequestData();
		
		try{
			calculateRequestData.setWellName(object[0]+"");
			//流体PVT物性
			calculateRequestData.setFluidPVT(new RPCCalculateRequestData.FluidPVT());
			calculateRequestData.getFluidPVT().setCrudeOilDensity(StringManagerUtils.stringToFloat(object[3]+""));
			calculateRequestData.getFluidPVT().setWaterDensity(StringManagerUtils.stringToFloat(object[4]+""));
			calculateRequestData.getFluidPVT().setNaturalGasRelativeDensity(StringManagerUtils.stringToFloat(object[5]+""));
			calculateRequestData.getFluidPVT().setSaturationPressure(StringManagerUtils.stringToFloat(object[6]+""));
			
			//油藏物性
			calculateRequestData.setReservoir(new RPCCalculateRequestData.Reservoir());
			calculateRequestData.getReservoir().setDepth(StringManagerUtils.stringToFloat(object[7]+""));
			calculateRequestData.getReservoir().setTemperature(StringManagerUtils.stringToFloat(object[8]+""));
			
			//井深轨迹
			calculateRequestData.setWellboreTrajectory(new RPCCalculateRequestData.WellboreTrajectory());
			
			//抽油杆参数
			calculateRequestData.setRodString(new RPCCalculateRequestData.RodString());
			calculateRequestData.getRodString().setEveryRod(new ArrayList<RPCCalculateRequestData.EveryRod>());
			String rodStringArr[]=(object[9]+"").split(";");
			for(int i=0;i<rodStringArr.length;i++){
				if(StringManagerUtils.isNotNull(rodStringArr[i])){
					String everyRodStringArr[]=rodStringArr[i].split(",");
					if(StringManagerUtils.stringToFloat(everyRodStringArr[3]+"")>0){
						String rodGrade=everyRodStringArr[0];
						String rodOutsideDiameter=everyRodStringArr[1];
						String rodInsideDiameter=everyRodStringArr[2];
						String rodLength=everyRodStringArr[3];
						RPCCalculateRequestData.EveryRod everyRod=new RPCCalculateRequestData.EveryRod();
						everyRod.setType(1);
						everyRod.setGrade(rodGrade);
						everyRod.setLength(StringManagerUtils.stringToFloat(rodLength));
						everyRod.setOutsideDiameter(StringManagerUtils.stringToFloat(rodOutsideDiameter)/1000);
						everyRod.setInsideDiameter(StringManagerUtils.stringToFloat(rodInsideDiameter)/1000);
						calculateRequestData.getRodString().getEveryRod().add(everyRod);
					}
				}
			}
			
			//油管参数
			RPCCalculateRequestData.EveryTubing everyTubing=new RPCCalculateRequestData.EveryTubing();
			everyTubing.setInsideDiameter(StringManagerUtils.stringToFloat(object[10]+"")/1000);
			calculateRequestData.setTubingString(new RPCCalculateRequestData.TubingString());
			calculateRequestData.getTubingString().setEveryTubing(new ArrayList<RPCCalculateRequestData.EveryTubing>());
			calculateRequestData.getTubingString().getEveryTubing().add(everyTubing);
			
			//抽油泵参数
			calculateRequestData.setPump(new RPCCalculateRequestData.Pump());
			calculateRequestData.getPump().setPumpType(object[11]+"");
			calculateRequestData.getPump().setBarrelType("L");
			calculateRequestData.getPump().setPumpGrade(StringManagerUtils.stringToInteger(object[12]+""));
			calculateRequestData.getPump().setPlungerLength(StringManagerUtils.stringToFloat(object[13]+""));
			calculateRequestData.getPump().setPumpBoreDiameter(StringManagerUtils.stringToFloat(object[14]+"")/1000);
			
			
			//套管数据
			RPCCalculateRequestData.EveryCasing  everyCasing=new RPCCalculateRequestData.EveryCasing();
			everyCasing.setInsideDiameter(StringManagerUtils.stringToFloat(object[15]+"")/1000);
			calculateRequestData.setCasingString(new RPCCalculateRequestData.CasingString());
			calculateRequestData.getCasingString().setEveryCasing(new ArrayList<RPCCalculateRequestData.EveryCasing>());
			calculateRequestData.getCasingString().getEveryCasing().add(everyCasing);
			
			//生产数据
			calculateRequestData.setProduction(new RPCCalculateRequestData.Production());
			calculateRequestData.getProduction().setWaterCut(StringManagerUtils.stringToFloat(object[16]+""));
			calculateRequestData.getProduction().setProductionGasOilRatio(StringManagerUtils.stringToFloat(object[17]+""));
			calculateRequestData.getProduction().setTubingPressure(StringManagerUtils.stringToFloat(object[18]+""));
			calculateRequestData.getProduction().setCasingPressure(StringManagerUtils.stringToFloat(object[19]+""));
			calculateRequestData.getProduction().setWellHeadFluidTemperature(StringManagerUtils.stringToFloat(object[20]+""));
			calculateRequestData.getProduction().setProducingfluidLevel(StringManagerUtils.stringToFloat(object[21]+""));
			calculateRequestData.getProduction().setPumpSettingDepth(StringManagerUtils.stringToFloat(object[22]+""));
			
			//功图数据
			calculateRequestData.setFESDiagram(new RPCCalculateRequestData.FESDiagram());
	        calculateRequestData.getFESDiagram().setAcquisitionTime(object[2]+"");
	        calculateRequestData.getFESDiagram().setStroke(StringManagerUtils.stringToFloat(object[24]+""));
	        calculateRequestData.getFESDiagram().setSPM(StringManagerUtils.stringToFloat(object[25]+""));
			
	        List<Float> F=new ArrayList<Float>();
	        List<Float> S=new ArrayList<Float>();
	        List<Float> Watt=new ArrayList<Float>();
	        List<Float> I=new ArrayList<Float>();
	        SerializableClobProxy proxy=null;
	        CLOB realClob =null;
	        String clobStr="";
	        String[] curveData=null;
	        if(object[26]!=null){//位移曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[26]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				curveData=clobStr.split(",");
				for(int i=0;i<curveData.length;i++){
					S.add(StringManagerUtils.stringToFloat(curveData[i]));
				}
	        }
	        if(object[27]!=null){//载荷曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[27]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				curveData=clobStr.split(",");
				for(int i=0;i<curveData.length;i++){
					F.add(StringManagerUtils.stringToFloat(curveData[i]));
				}
	        }
	        if(object[28]!=null){//功率曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[28]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				curveData=clobStr.split(",");
				for(int i=0;i<curveData.length;i++){
					Watt.add(StringManagerUtils.stringToFloat(curveData[i]));
				}
	        }
	        if(object[29]!=null){//电流曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[29]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				curveData=clobStr.split(",");
				for(int i=0;i<curveData.length;i++){
					I.add(StringManagerUtils.stringToFloat(curveData[i]));
				}
	        }
	        calculateRequestData.getFESDiagram().setF(F);
	        calculateRequestData.getFESDiagram().setS(S);
	        calculateRequestData.getFESDiagram().setWatt(Watt);
	        calculateRequestData.getFESDiagram().setI(I);
			
	        //系统效率
	        calculateRequestData.setSystemEfficiency(new RPCCalculateRequestData.SystemEfficiency());
	        
	        //人工干预
	        calculateRequestData.setManualIntervention(new RPCCalculateRequestData.ManualIntervention());
	        calculateRequestData.getManualIntervention().setCode(StringManagerUtils.stringToInteger(object[30]+""));
	        calculateRequestData.getManualIntervention().setNetGrossRatio(StringManagerUtils.stringToFloat(object[23]+""));
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		String requsetdata=gson.toJson(calculateRequestData);
		return requsetdata;
	}
		
	public String getDefaultWellname(String orgId){
		String sql="select v.* from "
				+ " (select t007.jh from tbl_wellinformation t007,tbl_org org where t007.dwbh=org.org_code and org.org_id in ("+orgId+") order by t007.jh) v "
				+ " where rownum<=1";
		List<?> list = this.findCallSql(sql);
		
		return list.get(0).toString();
	}
	
	public boolean saveCalculateResult(int jlbh,RPCCalculateResponseData calculateResponseData) throws SQLException, ParseException{
		return this.getBaseDao().saveCalculateResult(jlbh,calculateResponseData);
	}
	
	public int deleteInvalidData() throws SQLException{
		String sql="delete from tbl_rpc_diagram_hist where jsbz=0 and jlbh not in"
				+ " (select t033.jlbh"
				+ " from tbl_wellinformation t007, tbl_rpc_diagram_hist t010,"
				+ " t_dynamicliquidlevel t011,tbl_rpc_diagram_hist t033  "
				+ " where t007.jlbh=t010.jbh and t033.jbh=t007.jlbh and t033.gtbh=t010.jlbh  "
				+ " and t033.dymbh=t011.jlbh  "
				+ " and  t033.jsbz in (0,2))";
		return this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public int deleteInvalidData(int jbh) throws SQLException{
		String sql="delete from tbl_rpc_diagram_hist where jsbz=0 and jlbh not in"
				+ " (select t033.jlbh"
				+ " from tbl_wellinformation t007, tbl_rpc_diagram_hist t010,"
				+ " t_dynamicliquidlevel t011,tbl_rpc_diagram_hist t033  "
				+ " where t007.jlbh=t010.jbh and t033.jbh=t007.jlbh and t033.gtbh=t010.jlbh  "
				+ " and t033.dymbh=t011.jlbh  "
				+ " and  t033.jsbz in (0,2))"
				+ " and t033.jbh="+jbh;
		return this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	
	public List<String> getFSDiagramDailyCalculationRequestData(String tatalDate,String wellId) throws ParseException{
		StringBuffer dataSbf=null;
		List<String> requestDataList=new ArrayList<String>();
		String timeEffTotalUrl=Config.getTimeEfficiencyHttpServerURL();
		String commTotalUrl=Config.getCommHttpServerURL();
		String wellinformationSql="select t.wellname,t2.runtime as runtime2,t.runtimeefficiencysource,t.driveraddr,t.driverid,"
				+ " t3.commstatus,t3.commtime,t3.commtimeefficiency,t3.commrange,"
				+ " t3.runstatus,t3.runtime,t3.runtimeefficiency,t3.runrange "
				+ " from tbl_wellinformation t "
				+ " left outer join tbl_rpc_productiondata_latest t2 on t.id=t2.wellid  "
				+ " left outer join tbl_rpc_discrete_latest  t3 on t3.wellId=t.id"
				+ " where t.liftingType between 200 and 299 ";
		String singleCalculateResuleSql="select t007.wellname,to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as acquisitiontime,t.workingconditioncode,"
											+" t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,prod.watercut,"
											+" t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction, prod.watercut_w,"
											+" t.surfacesystemefficiency,t.welldownsystemefficiency,t.systemefficiency,t.powerconsumptionperthm,"
											+" t.fullnesscoefficient,t.stroke,t.spm,"
											+" prod.productiongasoilratio,prod.producingfluidlevel,prod.pumpsettingdepth,prod.pumpsettingdepth-prod.producingfluidlevel as submergence,t.pumpeff,prod.pumpborediameter/1000 as pumpborediameter,"
											+" t.idegreebalance,t.wattdegreebalance,"
											+" prod.tubingpressure,prod.casingpressure,prod.wellheadfluidtemperature"
											+" from tbl_rpc_diagram_hist t ,tbl_wellinformation t007 ,tbl_rpc_productiondata_hist prod"
											+" where t.wellid=t007.id and t.productiondataid=prod.id "
											+" and  t.acquisitiontime > "
											+" (select max(to_date(to_char(t2.acquisitiontime,'yyyy-mm-dd'),'yyyy-mm-dd')) "
											+" from tbl_rpc_diagram_hist t2 where t2.wellId=t.wellid and t2.resultstatus=1 "
											+" and t2.acquisitiontime< to_date('"+tatalDate+"','yyyy-mm-dd')) "
											+" and t.resultstatus=1 and t.workingconditioncode<>1232 and t.acquisitiontime<to_date('"+tatalDate+"','yyyy-mm-dd')";
		String statusSql="select well.wellname,to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as acquisitiontime,"
				+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange,"
				+ "t.runstatus,t.runtimeefficiency,t.runtime,t.runrange "
				+ " from tbl_rpc_discrete_hist t,tbl_wellinformation well "
				+ " where t.wellid=well.id and t.acquisitiontime=( select max(t2.acquisitiontime) from tbl_rpc_discrete_hist t2 where t2.wellid=t.wellid and t2.acquisitiontime between to_date('"+tatalDate+"','yyyy-mm-dd')-1 and to_date('"+tatalDate+"','yyyy-mm-dd'))";
		if(StringManagerUtils.isNotNull(wellId)){
			wellinformationSql+=" and t.id in ("+wellId+")";
			singleCalculateResuleSql+=" and t007.id in ("+wellId+")";
			statusSql+=" and t.wellid in("+wellId+")";
		}
//		wellinformationSql+=" and t.jh='龙1-斜09'";
//		singleCalculateResuleSql+=" and t007.jh='龙1-斜09'";
		
		singleCalculateResuleSql+=" order by t007.sortnum,t.acquisitiontime";
		wellinformationSql+=" order by t.sortnum";
		statusSql+=" order by well.sortnum";
		List<?> welllist = findCallSql(wellinformationSql);
		List<?> singleresultlist = findCallSql(singleCalculateResuleSql);

		for(int i=0;i<welllist.size();i++){
			try{
				Object[] wellObj=(Object[]) welllist.get(i);
				TimeEffResponseData timeEffResponseData=null;
				CommResponseData commResponseData=null;
				if(!StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())).equals(tatalDate)){//如果不是当天实时汇总，进行跨天汇总
					List<?> statusList = findCallSql(statusSql);
					for(int j=0;j<statusList.size();j++){
						Object[] statusObj=(Object[]) statusList.get(j);
						if(wellObj[0].toString().equals(statusObj[0].toString())){
							boolean commStatus=false;
							boolean runStatus=false;
							if(statusObj[2]!=null&&StringManagerUtils.stringToInteger(statusObj[2]+"")==1){
								commStatus=true;
							}
							if(statusObj[6]!=null&&StringManagerUtils.stringToInteger(statusObj[6]+"")==1){
								runStatus=true;
							}
							String commTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+wellObj[0]+"\","
									+ "\"Last\":{"
									+ "\"AcquisitionTime\": \""+statusObj[1]+"\","
									+ "\"CommStatus\": "+commStatus+","
									+ "\"CommEfficiency\": {"
									+ "\"Efficiency\": "+statusObj[3]+","
									+ "\"Time\": "+statusObj[4]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(statusObj[5]+"")+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcquisitionTime\":\""+tatalDate+" 01:00:00\","
									+ "\"CommStatus\":true"
									+ "}"
									+ "}";
							String runTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+wellObj[0]+"\","
									+ "\"Last\":{"
									+ "\"AcquisitionTime\": \""+statusObj[1]+"\","
									+ "\"RunStatus\": "+runStatus+","
									+ "\"RunEfficiency\": {"
									+ "\"Efficiency\": "+statusObj[7]+","
									+ "\"Time\": "+statusObj[8]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(statusObj[9]+"")+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcquisitionTime\":\""+tatalDate+" 01:00:00\","
									+ "\"RunStatus\":true"
									+ "}"
									+ "}";
							Gson gson = new Gson();
							java.lang.reflect.Type type=null;
							if(wellObj[3]!=null&&wellObj[4]!=null){
								String commTotalResponse=StringManagerUtils.sendPostMethod(commTotalUrl, commTotalRequestData,"utf-8");
								type = new TypeToken<CommResponseData>() {}.getType();
								commResponseData = gson.fromJson(commTotalResponse, type);
							}
							if(!"0".equals(wellObj[2]+"")){
								String runTotalResponse=StringManagerUtils.sendPostMethod(timeEffTotalUrl, runTotalRequestData,"utf-8");
								type = new TypeToken<TimeEffResponseData>() {}.getType();
								timeEffResponseData = gson.fromJson(runTotalResponse, type);
							}
							break;
						}
					}
				}
				dataSbf = new StringBuffer();
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+wellObj[0]+"\",");
				dataSbf.append("\"EveryTime\": [");
				for(int j=0;j<singleresultlist.size();j++){
					Object[] resuleObj=(Object[]) singleresultlist.get(j);
					if(wellObj[0].toString().equals(resuleObj[0].toString())){
						dataSbf.append("{\"AcquisitionTime\":\""+resuleObj[1]+"\",");
						if(commResponseData!=null&&commResponseData.getResultStatus()==1&&commResponseData.getDaily().getCommEfficiency().getRange()!=null&&commResponseData.getDaily().getCommEfficiency().getRange().size()>0){
							dataSbf.append("\"CommStatus\":"+(commResponseData.getDaily().getCommStatus()?1:0)+",");
							dataSbf.append("\"CommTime\":"+commResponseData.getDaily().getCommEfficiency().getTime()+",");
							dataSbf.append("\"CommTimeEfficiency\":"+commResponseData.getDaily().getCommEfficiency().getEfficiency()+",");
							dataSbf.append("\"CommRange\":\""+commResponseData.getDaily().getCommEfficiency().getRangeString()+"\",");
						}else if(StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())).equals(tatalDate)){//如果是实时汇总
							dataSbf.append("\"CommStatus\":"+wellObj[5]+",");
							dataSbf.append("\"CommTime\":"+wellObj[6]+",");
							dataSbf.append("\"CommTimeEfficiency\":"+wellObj[7]+",");
							dataSbf.append("\"CommRange\":\""+wellObj[8]+"\",");
						}
						if("0".equals(wellObj[2]+"")){//如果时率来源是人工计算
							String wellRunRime=getWellRuningTime(StringManagerUtils.getOneDayDateString(-1),wellObj[1],null);
							dataSbf.append("\"RunStatus\":1,");
							dataSbf.append("\"RunTime\":"+wellObj[1]+",");
							dataSbf.append("\"RunTimeEfficiency\":"+(StringManagerUtils.stringToFloat(wellObj[1]+"", 2))/24+",");
							dataSbf.append("\"RunRange\":\""+wellRunRime+"\",");
						}else{
							if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1&&timeEffResponseData.getDaily().getRunEfficiency().getRange()!=null&&timeEffResponseData.getDaily().getRunEfficiency().getRange().size()>0){
								dataSbf.append("\"RunStatus\":"+(timeEffResponseData.getDaily().getRunStatus()?1:0)+",");
								dataSbf.append("\"RunTime\":"+timeEffResponseData.getDaily().getRunEfficiency().getTime()+",");
								dataSbf.append("\"RunTimeEfficiency\":"+timeEffResponseData.getDaily().getRunEfficiency().getEfficiency()+",");
								dataSbf.append("\"RunRange\":\""+timeEffResponseData.getDaily().getRunEfficiency().getRangeString()+"\",");
							}else if(StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())).equals(tatalDate)){//如果是实时汇总
								dataSbf.append("\"RunStatus\":"+wellObj[9]+",");
								dataSbf.append("\"RunTime\":"+wellObj[10]+",");
								dataSbf.append("\"RunTimeEfficiency\":"+wellObj[11]+",");
								dataSbf.append("\"RunRange\":\""+wellObj[12]+"\",");
							}
						}
						dataSbf.append("\"FSResultCode\":"+resuleObj[2]+",");
						dataSbf.append("\"LiquidVolumetricProduction\":"+resuleObj[3]+",");
						dataSbf.append("\"OilVolumetricProduction\":"+resuleObj[4]+",");
						dataSbf.append("\"WaterVolumetricProduction\":"+resuleObj[5]+",");
						dataSbf.append("\"VolumeWaterCut\":"+resuleObj[6]+",");
						dataSbf.append("\"LiquidWeightProduction\":"+resuleObj[7]+",");
						dataSbf.append("\"OilWeightProduction\":"+resuleObj[8]+",");
						dataSbf.append("\"WaterWeightProduction\":"+resuleObj[9]+",");
						dataSbf.append("\"WeightWaterCut\":"+resuleObj[10]+",");
						dataSbf.append("\"SurfaceSystemEfficiency\":"+resuleObj[11]+",");
						dataSbf.append("\"WellDownSystemEfficiency\":"+resuleObj[12]+",");
						dataSbf.append("\"SystemEfficiency\":"+resuleObj[13]+",");
						dataSbf.append("\"PowerConsumptionPerTHM\":"+resuleObj[14]+",");
						dataSbf.append("\"FullnessCoefficient\":"+resuleObj[15]+",");
						dataSbf.append("\"Stroke\":"+resuleObj[16]+",");
						dataSbf.append("\"SPM\":"+resuleObj[17]+",");
						dataSbf.append("\"ProductionGasOilRatio\":"+resuleObj[18]+",");
						dataSbf.append("\"ProducingfluidLevel\":"+resuleObj[19]+",");
						dataSbf.append("\"PumpSettingDepth\":"+resuleObj[20]+",");
						dataSbf.append("\"Submergence\":"+resuleObj[21]+",");
						dataSbf.append("\"PumpEff\":"+resuleObj[22]+",");
						dataSbf.append("\"PumpBoreDiameter\":"+resuleObj[23]+",");
						dataSbf.append("\"WattDegreeBalance\":"+resuleObj[24]+",");
						dataSbf.append("\"IDegreeBalance\":"+resuleObj[25]+",");
						dataSbf.append("\"TubingPressure\":"+resuleObj[26]+",");
						dataSbf.append("\"CasingPressure\":"+resuleObj[27]+",");
						dataSbf.append("\"WellHeadFluidTemperature\":"+resuleObj[28]+"},");
					}
				}
				if(dataSbf.toString().endsWith(",")){
					dataSbf.deleteCharAt(dataSbf.length() - 1);
				}
				dataSbf.append("]}");
				requestDataList.add(dataSbf.toString());
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		//遍历无井环的井
		
		return requestDataList;
	}
	
	public List<String> getPCPRPMDailyCalculationRequestData(String tatalDate,String wellId) throws ParseException{
		StringBuffer dataSbf=null;
		List<String> requestDataList=new ArrayList<String>();
		String timeEffTotalUrl=Config.getTimeEfficiencyHttpServerURL();
		String commTotalUrl=Config.getCommHttpServerURL();
		String wellinformationSql="select t.wellname,t2.runtime as runtime2,t.runtimeefficiencysource,t.driveraddr,t.driverid,"
				+ " t3.commstatus,t3.commtime,t3.commtimeefficiency,t3.commrange,"
				+ " t3.runstatus,t3.runtime,t3.runtimeefficiency,t3.runrange "
				+ " from tbl_wellinformation t "
				+ " left outer join tbl_pcp_productiondata_latest t2 on t.id=t2.wellid  "
				+ " left outer join tbl_pcp_discrete_latest  t3 on t3.wellId=t.id"
				+ " where t.liftingType between 400 and 499 ";
		String singleCalculateResuleSql="select t007.wellname,to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as acquisitiontime,t.rpm,"
											+" t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,prod.watercut,"
											+" t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction, prod.watercut_w,"
											+" t.systemefficiency,t.powerconsumptionperthm,"
											+" prod.productiongasoilratio,prod.producingfluidlevel,prod.pumpsettingdepth,prod.pumpsettingdepth-prod.producingfluidlevel as submergence,t.pumpeff,"
											+" prod.tubingpressure,prod.casingpressure,prod.wellheadfluidtemperature"
											+" from tbl_pcp_rpm_hist t ,tbl_wellinformation t007 ,tbl_pcp_productiondata_hist prod"
											+" where t.wellid=t007.id and t.productiondataid=prod.id "
											+" and  t.acquisitiontime > "
											+" (select max(to_date(to_char(t2.acquisitiontime,'yyyy-mm-dd'),'yyyy-mm-dd')) "
											+" from tbl_pcp_rpm_hist t2 where t2.wellId=t.wellid and t2.resultstatus=1 "
											+" and t2.acquisitiontime< to_date('"+tatalDate+"','yyyy-mm-dd')) "
											+" and t.resultstatus=1 and t.acquisitiontime<to_date('"+tatalDate+"','yyyy-mm-dd')";
		String statusSql="select well.wellname,to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as acquisitiontime,"
				+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange,"
				+ "t.runstatus,t.runtimeefficiency,t.runtime,t.runrange "
				+ " from tbl_pcp_discrete_hist t,tbl_wellinformation well "
				+ " where t.wellid=well.id and t.acquisitiontime=( select max(t2.acquisitiontime) from tbl_pcp_discrete_hist t2 where t2.wellid=t.wellid and t2.acquisitiontime between to_date('"+tatalDate+"','yyyy-mm-dd')-1 and to_date('"+tatalDate+"','yyyy-mm-dd'))";
		if(StringManagerUtils.isNotNull(wellId)){
			wellinformationSql+=" and t.id in ("+wellId+")";
			singleCalculateResuleSql+=" and t007.id in ("+wellId+")";
			statusSql+=" and t.wellid in("+wellId+")";
		}
//		wellinformationSql+=" and t.jh='龙1-斜09'";
//		singleCalculateResuleSql+=" and t007.jh='龙1-斜09'";
		
		singleCalculateResuleSql+=" order by t007.sortnum,t.acquisitiontime";
		wellinformationSql+=" order by t.sortnum";
		statusSql+=" order by well.sortnum";
		List<?> welllist = findCallSql(wellinformationSql);
		List<?> singleresultlist = findCallSql(singleCalculateResuleSql);

		for(int i=0;i<welllist.size();i++){
			try{
				Object[] wellObj=(Object[]) welllist.get(i);
				TimeEffResponseData timeEffResponseData=null;
				CommResponseData commResponseData=null;
				if(!StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())).equals(tatalDate)){//如果不是当天实时汇总，进行跨天汇总
					List<?> statusList = findCallSql(statusSql);
					for(int j=0;j<statusList.size();j++){
						Object[] statusObj=(Object[]) statusList.get(j);
						if(wellObj[0].toString().equals(statusObj[0].toString())){
							boolean commStatus=false;
							boolean runStatus=false;
							if(statusObj[2]!=null&&StringManagerUtils.stringToInteger(statusObj[2]+"")==1){
								commStatus=true;
							}
							if(statusObj[6]!=null&&StringManagerUtils.stringToInteger(statusObj[6]+"")==1){
								runStatus=true;
							}
							String commTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+wellObj[0]+"\","
									+ "\"Last\":{"
									+ "\"AcquisitionTime\": \""+statusObj[1]+"\","
									+ "\"CommStatus\": "+commStatus+","
									+ "\"CommEfficiency\": {"
									+ "\"Efficiency\": "+statusObj[3]+","
									+ "\"Time\": "+statusObj[4]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(statusObj[5]+"")+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcquisitionTime\":\""+tatalDate+" 01:00:00\","
									+ "\"CommStatus\":true"
									+ "}"
									+ "}";
							String runTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+wellObj[0]+"\","
									+ "\"Last\":{"
									+ "\"AcquisitionTime\": \""+statusObj[1]+"\","
									+ "\"RunStatus\": "+runStatus+","
									+ "\"RunEfficiency\": {"
									+ "\"Efficiency\": "+statusObj[7]+","
									+ "\"Time\": "+statusObj[8]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(statusObj[9]+"")+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcquisitionTime\":\""+tatalDate+" 01:00:00\","
									+ "\"RunStatus\":true"
									+ "}"
									+ "}";
							Gson gson = new Gson();
							java.lang.reflect.Type type=null;
							if(wellObj[3]!=null&&wellObj[4]!=null){
								String commTotalResponse=StringManagerUtils.sendPostMethod(commTotalUrl, commTotalRequestData,"utf-8");
								type = new TypeToken<CommResponseData>() {}.getType();
								commResponseData = gson.fromJson(commTotalResponse, type);
							}
							if(!"0".equals(wellObj[2]+"")){
								String runTotalResponse=StringManagerUtils.sendPostMethod(timeEffTotalUrl, runTotalRequestData,"utf-8");
								type = new TypeToken<TimeEffResponseData>() {}.getType();
								timeEffResponseData = gson.fromJson(runTotalResponse, type);
							}
							break;
						}
					}
				}
				dataSbf = new StringBuffer();
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+wellObj[0]+"\",");
				dataSbf.append("\"EveryTime\": [");
				for(int j=0;j<singleresultlist.size();j++){
					Object[] resuleObj=(Object[]) singleresultlist.get(j);
					if(wellObj[0].toString().equals(resuleObj[0].toString())){
						dataSbf.append("{\"AcquisitionTime\":\""+resuleObj[1]+"\",");
						if(commResponseData!=null&&commResponseData.getResultStatus()==1&&commResponseData.getDaily().getCommEfficiency().getRange()!=null&&commResponseData.getDaily().getCommEfficiency().getRange().size()>0){
							dataSbf.append("\"CommStatus\":"+(commResponseData.getDaily().getCommStatus()?1:0)+",");
							dataSbf.append("\"CommTime\":"+commResponseData.getDaily().getCommEfficiency().getTime()+",");
							dataSbf.append("\"CommTimeEfficiency\":"+commResponseData.getDaily().getCommEfficiency().getEfficiency()+",");
							dataSbf.append("\"CommRange\":\""+commResponseData.getDaily().getCommEfficiency().getRangeString()+"\",");
						}else if(StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())).equals(tatalDate)){//如果是实时汇总
							dataSbf.append("\"CommStatus\":"+wellObj[5]+",");
							dataSbf.append("\"CommTime\":"+wellObj[6]+",");
							dataSbf.append("\"CommTimeEfficiency\":"+wellObj[7]+",");
							dataSbf.append("\"CommRange\":\""+wellObj[8]+"\",");
						}
						if("0".equals(wellObj[2]+"")){//如果时率来源是人工计算
							String wellRunRime=getWellRuningTime(StringManagerUtils.getOneDayDateString(-1),wellObj[1],null);
							dataSbf.append("\"RunStatus\":1,");
							dataSbf.append("\"RunTime\":"+wellObj[1]+",");
							dataSbf.append("\"RunTimeEfficiency\":"+(StringManagerUtils.stringToFloat(wellObj[1]+"", 2))/24+",");
							dataSbf.append("\"RunRange\":\""+wellRunRime+"\",");
						}else{
							if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1&&timeEffResponseData.getDaily().getRunEfficiency().getRange()!=null&&timeEffResponseData.getDaily().getRunEfficiency().getRange().size()>0){
								dataSbf.append("\"RunStatus\":"+(timeEffResponseData.getDaily().getRunStatus()?1:0)+",");
								dataSbf.append("\"RunTime\":"+timeEffResponseData.getDaily().getRunEfficiency().getTime()+",");
								dataSbf.append("\"RunTimeEfficiency\":"+timeEffResponseData.getDaily().getRunEfficiency().getEfficiency()+",");
								dataSbf.append("\"RunRange\":\""+timeEffResponseData.getDaily().getRunEfficiency().getRangeString()+"\",");
							}else if(StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())).equals(tatalDate)){//如果是实时汇总
								dataSbf.append("\"RunStatus\":"+wellObj[9]+",");
								dataSbf.append("\"RunTime\":"+wellObj[10]+",");
								dataSbf.append("\"RunTimeEfficiency\":"+wellObj[11]+",");
								dataSbf.append("\"RunRange\":\""+wellObj[12]+"\",");
							}
						}
						dataSbf.append("\"RPM\":"+resuleObj[2]+",");
						dataSbf.append("\"LiquidVolumetricProduction\":"+resuleObj[3]+",");
						dataSbf.append("\"OilVolumetricProduction\":"+resuleObj[4]+",");
						dataSbf.append("\"WaterVolumetricProduction\":"+resuleObj[5]+",");
						dataSbf.append("\"VolumeWaterCut\":"+resuleObj[6]+",");
						dataSbf.append("\"LiquidWeightProduction\":"+resuleObj[7]+",");
						dataSbf.append("\"OilWeightProduction\":"+resuleObj[8]+",");
						dataSbf.append("\"WaterWeightProduction\":"+resuleObj[9]+",");
						dataSbf.append("\"WeightWaterCut\":"+resuleObj[10]+",");
						dataSbf.append("\"SystemEfficiency\":"+resuleObj[11]+",");
						dataSbf.append("\"PowerConsumptionPerTHM\":"+resuleObj[12]+",");
						dataSbf.append("\"ProductionGasOilRatio\":"+resuleObj[13]+",");
						dataSbf.append("\"ProducingfluidLevel\":"+resuleObj[14]+",");
						dataSbf.append("\"PumpSettingDepth\":"+resuleObj[15]+",");
						dataSbf.append("\"Submergence\":"+resuleObj[16]+",");
						dataSbf.append("\"PumpEff\":"+resuleObj[17]+",");
						dataSbf.append("\"TubingPressure\":"+resuleObj[18]+",");
						dataSbf.append("\"CasingPressure\":"+resuleObj[19]+",");
						dataSbf.append("\"WellHeadFluidTemperature\":"+resuleObj[20]+"},");
					}
				}
				if(dataSbf.toString().endsWith(",")){
					dataSbf.deleteCharAt(dataSbf.length() - 1);
				}
				dataSbf.append("]}");
				requestDataList.add(dataSbf.toString());
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		//遍历无井环的井
		
		return requestDataList;
	}
	
	
	public List<String> getDiscreteDailyCalculation(String tatalDate,String wellId) throws ParseException{
		StringBuffer dataSbf=null;
		List<String> requestDataList=new ArrayList<String>();
		String wellinformationSql="select t.wellname,t2.runtime,t.runtimeefficiencysource,t.driveraddr,t.driverid "
				+ " from tbl_wellinformation t "
				+ " left outer join tbl_rpc_productiondata_latest t2 on t.id=t2.wellid  "
				+ " where t.liftingType between 200 and 299 ";
		String singleCalculateResuleSql="select t007.wellname,to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as acquisitiontime,"
				+" t.ia,t.ib,t.ic,t.va,t.vb,t.vc,"
				+" t.frequencyrunvalue"
				+" from tbl_rpc_discrete_hist t ,tbl_wellinformation t007"
				+" where t.wellid=t007.id "
				+" and t.acquisitiontime between to_date('2020-01-17','yyyy-mm-dd')-1 and to_date('2020-01-17','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(wellId)){
			wellinformationSql+=" and t.id in ("+wellId+")";
			singleCalculateResuleSql+=" and t007.id in ("+wellId+")";
		}
		
		singleCalculateResuleSql+=" order by t007.sortnum,t.acquisitiontime";
		wellinformationSql+=" order by t.sortnum";
		List<?> welllist = findCallSql(wellinformationSql);
		List<?> singleresultlist = findCallSql(singleCalculateResuleSql);

		for(int i=0;i<welllist.size();i++){
			try{
				Object[] wellObj=(Object[]) welllist.get(i);
				dataSbf = new StringBuffer();
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+wellObj[0]+"\",");
				dataSbf.append("\"EveryTime\": [");
				String wellRunRime=getWellRuningTime(StringManagerUtils.getOneDayDateString(-1),wellObj[1],null);
				for(int j=0;j<singleresultlist.size();j++){
					Object[] resuleObj=(Object[]) singleresultlist.get(j);
					if(wellObj[0].toString().equals(resuleObj[0].toString())){
						dataSbf.append("{\"AcquisitionTime\":\""+resuleObj[1]+"\",");
						dataSbf.append("\"IA\":"+resuleObj[2]+",");
						dataSbf.append("\"IB\":"+resuleObj[3]+",");
						dataSbf.append("\"IC\":"+resuleObj[4]+",");
						dataSbf.append("\"VA\":"+resuleObj[5]+",");
						dataSbf.append("\"VB\":"+resuleObj[6]+",");
						dataSbf.append("\"VC\":"+resuleObj[7]+",");
						dataSbf.append("\"RunFrequency\":"+StringManagerUtils.stringToFloat(resuleObj[8]+"")+"},");
					}
				}
				if(dataSbf.toString().endsWith(",")){
					dataSbf.deleteCharAt(dataSbf.length() - 1);
				}
				dataSbf.append("]}");
				requestDataList.add(dataSbf.toString());
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		//遍历无井环的井
		
		return requestDataList;
	}
	
	public List<String> getPCPDiscreteDailyCalculation(String tatalDate,String wellId) throws ParseException{
		StringBuffer dataSbf=null;
		List<String> requestDataList=new ArrayList<String>();
		String wellinformationSql="select t.wellname,t2.runtime,t.runtimeefficiencysource,t.driveraddr,t.driverid "
				+ " from tbl_wellinformation t "
				+ " left outer join tbl_pcp_productiondata_latest t2 on t.id=t2.wellid  "
				+ " where t.liftingType between 400 and 499 ";
		String singleCalculateResuleSql="select t007.wellname,to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as acquisitiontime,"
				+" t.ia,t.ib,t.ic,t.va,t.vb,t.vc,"
				+" t.frequencyrunvalue"
				+" from tbl_pcp_discrete_hist t ,tbl_wellinformation t007"
				+" where t.wellid=t007.id "
				+" and t.acquisitiontime between to_date('2020-01-17','yyyy-mm-dd')-1 and to_date('2020-01-17','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(wellId)){
			wellinformationSql+=" and t.id in ("+wellId+")";
			singleCalculateResuleSql+=" and t007.id in ("+wellId+")";
		}
		
		singleCalculateResuleSql+=" order by t007.sortnum,t.acquisitiontime";
		wellinformationSql+=" order by t.sortnum";
		List<?> welllist = findCallSql(wellinformationSql);
		List<?> singleresultlist = findCallSql(singleCalculateResuleSql);

		for(int i=0;i<welllist.size();i++){
			try{
				Object[] wellObj=(Object[]) welllist.get(i);
				dataSbf = new StringBuffer();
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+wellObj[0]+"\",");
				dataSbf.append("\"EveryTime\": [");
				String wellRunRime=getWellRuningTime(StringManagerUtils.getOneDayDateString(-1),wellObj[1],null);
				for(int j=0;j<singleresultlist.size();j++){
					Object[] resuleObj=(Object[]) singleresultlist.get(j);
					if(wellObj[0].toString().equals(resuleObj[0].toString())){
						dataSbf.append("{\"AcquisitionTime\":\""+resuleObj[1]+"\",");
						dataSbf.append("\"IA\":"+resuleObj[2]+",");
						dataSbf.append("\"IB\":"+resuleObj[3]+",");
						dataSbf.append("\"IC\":"+resuleObj[4]+",");
						dataSbf.append("\"VA\":"+resuleObj[5]+",");
						dataSbf.append("\"VB\":"+resuleObj[6]+",");
						dataSbf.append("\"VC\":"+resuleObj[7]+",");
						dataSbf.append("\"RunFrequency\":"+StringManagerUtils.stringToFloat(resuleObj[8]+"")+"},");
					}
				}
				if(dataSbf.toString().endsWith(",")){
					dataSbf.deleteCharAt(dataSbf.length() - 1);
				}
				dataSbf.append("]}");
				requestDataList.add(dataSbf.toString());
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		//遍历无井环的井
		
		return requestDataList;
	}
	
	public boolean saveTotalCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException{
		return this.getBaseDao().saveTotalCalculationData(totalAnalysisResponseData,totalAnalysisRequestData, tatalDate);
	}
	public boolean saveFSDiagramDailyCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException{
		return this.getBaseDao().saveFSDiagramDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData, tatalDate);
	}
	public boolean savePCPRPMDailyCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException{
		return this.getBaseDao().savePCPRPMDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData, tatalDate);
	}
	public boolean saveDiscreteDailyCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException{
		return this.getBaseDao().saveDiscreteDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData, tatalDate);
	}
	public boolean savePCPDiscreteDailyCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException{
		return this.getBaseDao().savePCPDiscreteDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData, tatalDate);
	}
	
}