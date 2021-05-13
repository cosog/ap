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

import org.apache.commons.lang.StringUtils;
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
import com.gao.utils.Config2;
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
		
		String AcqTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String elecCalRequest="{\"AKString\":\"\","
				+ "\"WellName\":\""+object[0]+"\","
				+ "\"AcqTime\":\""+object[63]+"\","
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
				+ "\"AcqTime\":\""+object[63]+"\","
				+ "\"RunStatus\":"+runStatus+","
				+ "\"TotalAPC\":"+object[53]+","
				+ "\"TotalRPC\":"+object[54]+""
				+ "}";
		return elecCalRequest;
	}
	
	public String getObjectToCommCalculateRequestData(Object[] object) throws SQLException, IOException, ParseException{
		String elecCalRequest="{\"AKString\":\"\","
				+ "\"WellName\":\""+object[0]+"\","
				+ "\"AcqTime\":\""+object[2]+"\","
				+ "\"CommStatus\":1"
				+ "}";
		return elecCalRequest;
	}
	
	public String getObjectToRPCCalculateRequestData(Object[] object) throws SQLException, IOException, ParseException{
		Gson gson = new Gson();
		RPCCalculateRequestData calculateRequestData=new RPCCalculateRequestData();
		
		try{
			String rpcInformationSql="select t2.manufacturer,t2.model,decode(t2.crankrotationdirection,'顺时针','Clockwise','Anticlockwise'),"
					+ " t2.offsetangleofcrank,t2.crankgravityradius,"
					+ " t2.singlecrankweight,t2.singlecrankpinweight,"
					+ " t2.structuralunbalance,"
					+ " t2.balanceposition,t2.balanceweight,t2.prtf"
					+ " from  tbl_wellinformation t,tbl_rpcinformation t2"
					+ " where t.id=t2.wellid"
					+ " and t.wellname='"+object[0]+"'";
			String wellboretrajectorySql="select t2.measuringdepth,t2.deviationangle,t2.azimuthangle"
					+ " from  tbl_wellinformation t,tbl_wellboretrajectory t2"
					+ " where t.id=t2.wellid"
					+ " and t.wellname='"+object[0]+"'";
			List<?> rpcInformationList = this.findCallSql(rpcInformationSql);
			List<?> wellboretrajectoryList = this.findCallSql(wellboretrajectorySql);
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
			
			//井身轨迹
			if(wellboretrajectoryList.size()>0){
				Object[] wellboretrajectoryObject=(Object[])wellboretrajectoryList.get(0);
				calculateRequestData.setWellboreTrajectory(new RPCCalculateRequestData.WellboreTrajectory());
				String measuringDepthStr="";
				String deviationAngleStr="";
				String azimuthAngleStr="";
				List<Float> measuringDepth=new ArrayList<Float>();
		        List<Float> deviationAngle=new ArrayList<Float>();
		        List<Float> azimuthAngle=new ArrayList<Float>();
		        
		        SerializableClobProxy   proxy = null;
				CLOB realClob = null;
				
		        if(wellboretrajectoryObject[0]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(wellboretrajectoryObject[0]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					measuringDepthStr=StringManagerUtils.CLOBtoString(realClob);
				}
				if(wellboretrajectoryObject[1]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(wellboretrajectoryObject[1]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					deviationAngleStr=StringManagerUtils.CLOBtoString(realClob);
				}
				if(wellboretrajectoryObject[2]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(wellboretrajectoryObject[2]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					azimuthAngleStr=StringManagerUtils.CLOBtoString(realClob);
				}
				
				if(StringManagerUtils.isNotNull(measuringDepthStr)){
					String measuringDepthArr[]=measuringDepthStr.split(",");
					for(int i=0;i<measuringDepthArr.length;i++){
						measuringDepth.add(StringManagerUtils.stringToFloat(measuringDepthArr[i]));
					}
				}
				if(StringManagerUtils.isNotNull(deviationAngleStr)){
					String deviationAngleArr[]=deviationAngleStr.split(",");
					for(int i=0;i<deviationAngleArr.length;i++){
						deviationAngle.add(StringManagerUtils.stringToFloat(deviationAngleArr[i]));
					}
				}
				if(StringManagerUtils.isNotNull(azimuthAngleStr)){
					String azimuthAngleArr[]=azimuthAngleStr.split(",");
					for(int i=0;i<azimuthAngleArr.length;i++){
						azimuthAngle.add(StringManagerUtils.stringToFloat(azimuthAngleArr[i]));
					}
				}
				calculateRequestData.getWellboreTrajectory().setMeasuringDepth(measuringDepth);
				calculateRequestData.getWellboreTrajectory().setDeviationAngle(deviationAngle);
				calculateRequestData.getWellboreTrajectory().setAzimuthAngle(azimuthAngle);
			}
			
			//抽油杆参数
			calculateRequestData.setRodString(new RPCCalculateRequestData.RodString());
			calculateRequestData.getRodString().setEveryRod(new ArrayList<RPCCalculateRequestData.EveryRod>());
			String rodStringArr[]=(object[9]+"").split(";");
			for(int i=0;i<rodStringArr.length;i++){
				if(StringManagerUtils.isNotNull(rodStringArr[i])){
					String everyRodStringArr[]=rodStringArr[i].split(",");
					if(everyRodStringArr.length>0&&StringManagerUtils.stringToFloat(everyRodStringArr[3]+"")>0){
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
			calculateRequestData.getPump().setBarrelType(object[12]+"");
			calculateRequestData.getPump().setPumpGrade(StringManagerUtils.stringToInteger(object[13]+""));
			calculateRequestData.getPump().setPlungerLength(StringManagerUtils.stringToFloat(object[14]+""));
			calculateRequestData.getPump().setPumpBoreDiameter(StringManagerUtils.stringToFloat(object[15]+"")/1000);
			
			
			//套管数据
			RPCCalculateRequestData.EveryCasing  everyCasing=new RPCCalculateRequestData.EveryCasing();
			everyCasing.setInsideDiameter(StringManagerUtils.stringToFloat(object[16]+"")/1000);
			calculateRequestData.setCasingString(new RPCCalculateRequestData.CasingString());
			calculateRequestData.getCasingString().setEveryCasing(new ArrayList<RPCCalculateRequestData.EveryCasing>());
			calculateRequestData.getCasingString().getEveryCasing().add(everyCasing);
			
			
			//抽油机数据
			if(rpcInformationList.size()>0){
				Object[] rpcObject=(Object[])rpcInformationList.get(0);
				calculateRequestData.setPumpingUnit(new RPCCalculateRequestData.PumpingUnit());
				calculateRequestData.getPumpingUnit().setManufacturer(rpcObject[0]+"");
				calculateRequestData.getPumpingUnit().setModel(rpcObject[1]+"");
				calculateRequestData.getPumpingUnit().setCrankRotationDirection(rpcObject[2]+"");
				calculateRequestData.getPumpingUnit().setOffsetAngleOfCrank(StringManagerUtils.stringToFloat(rpcObject[3]+""));
				calculateRequestData.getPumpingUnit().setCrankGravityRadius(StringManagerUtils.stringToFloat(rpcObject[4]+""));
				calculateRequestData.getPumpingUnit().setSingleCrankWeight(StringManagerUtils.stringToFloat(rpcObject[5]+""));
				calculateRequestData.getPumpingUnit().setSingleCrankPinWeight(StringManagerUtils.stringToFloat(rpcObject[6]+""));
				calculateRequestData.getPumpingUnit().setStructuralUnbalance(StringManagerUtils.stringToFloat(rpcObject[7]+""));
				
				String[] BalancePositionArr=(rpcObject[8]+"").split(",");
				String[] BalanceWeightArr=(rpcObject[9]+"").split(",");
				calculateRequestData.getPumpingUnit().setBalance(new RPCCalculateRequestData.Balance());
				calculateRequestData.getPumpingUnit().getBalance().setEveryBalance(new ArrayList<RPCCalculateRequestData.EveryBalance>());
				for(int j=0;BalanceWeightArr!=null&&j<BalanceWeightArr.length;j++){
					RPCCalculateRequestData.EveryBalance everyBalance=new RPCCalculateRequestData.EveryBalance();
					everyBalance.setWeight(StringManagerUtils.stringToFloat(BalanceWeightArr[j]));
					calculateRequestData.getPumpingUnit().getBalance().getEveryBalance().add(everyBalance);
				}
			}
			
			//生产数据
			calculateRequestData.setProduction(new RPCCalculateRequestData.Production());
			calculateRequestData.getProduction().setWaterCut(StringManagerUtils.stringToFloat(object[17]+""));
			calculateRequestData.getProduction().setProductionGasOilRatio(StringManagerUtils.stringToFloat(object[18]+""));
			calculateRequestData.getProduction().setTubingPressure(StringManagerUtils.stringToFloat(object[19]+""));
			calculateRequestData.getProduction().setCasingPressure(StringManagerUtils.stringToFloat(object[20]+""));
			calculateRequestData.getProduction().setWellHeadFluidTemperature(StringManagerUtils.stringToFloat(object[21]+""));
			calculateRequestData.getProduction().setProducingfluidLevel(StringManagerUtils.stringToFloat(object[22]+""));
			calculateRequestData.getProduction().setPumpSettingDepth(StringManagerUtils.stringToFloat(object[23]+""));
			calculateRequestData.getProduction().setLevelCorrectValue(StringManagerUtils.stringToFloat(object[24]+""));
			
			//功图数据
			calculateRequestData.setFESDiagram(new RPCCalculateRequestData.FESDiagram());
			calculateRequestData.getFESDiagram().setSrc(StringManagerUtils.stringToInteger(object[32]+""));
	        calculateRequestData.getFESDiagram().setAcqTime(object[2]+"");
	        calculateRequestData.getFESDiagram().setStroke(StringManagerUtils.stringToFloat(object[26]+""));
	        calculateRequestData.getFESDiagram().setSPM(StringManagerUtils.stringToFloat(object[27]+""));
			
	        List<Float> F=new ArrayList<Float>();
	        List<Float> S=new ArrayList<Float>();
	        List<Float> Watt=new ArrayList<Float>();
	        List<Float> I=new ArrayList<Float>();
	        SerializableClobProxy proxy=null;
	        CLOB realClob =null;
	        String clobStr="";
	        String[] curveData=null;
	        if(object[28]!=null){//位移曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[28]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				curveData=clobStr.split(",");
				for(int i=0;i<curveData.length;i++){
					S.add(StringManagerUtils.stringToFloat(curveData[i]));
				}
	        }
	        if(object[29]!=null){//载荷曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[29]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				curveData=clobStr.split(",");
				for(int i=0;i<curveData.length;i++){
					F.add(StringManagerUtils.stringToFloat(curveData[i]));
				}
	        }
	        if(object[30]!=null){//功率曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[30]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				if(StringManagerUtils.isNotNull(clobStr)){
					curveData=clobStr.split(",");
					for(int i=0;i<curveData.length;i++){
						Watt.add(StringManagerUtils.stringToFloat(curveData[i]));
					}
				}
	        }
	        if(object[31]!=null){//电流曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[31]);
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
			
	        //系统效率
	        calculateRequestData.setSystemEfficiency(new RPCCalculateRequestData.SystemEfficiency());
	        
	        //人工干预
	        calculateRequestData.setManualIntervention(new RPCCalculateRequestData.ManualIntervention());
	        calculateRequestData.getManualIntervention().setCode(StringManagerUtils.stringToInteger(object[33]+""));
	        calculateRequestData.getManualIntervention().setNetGrossRatio(StringManagerUtils.stringToFloat(object[25]+""));
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
	
	public List<String> getFSDiagramDailyCalculationRequestData(String tatalDate,String wellId,String endAcqTime) throws ParseException{
		String date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(tatalDate),-1);
		StringBuffer dataSbf=null;
		List<String> requestDataList=new ArrayList<String>();
		String timeEffTotalUrl=Config.getInstance().configFile.getAgileCalculate().getRun()[0];
		String commTotalUrl=Config.getInstance().configFile.getAgileCalculate().getCommunication()[0];
		String wellinformationSql="select t.wellname,t2.runtime as runtime2,t.runtimeefficiencysource,t.deviceaddr,t.deviceid,"
				+ " t3.commstatus,t3.commtime,t3.commtimeefficiency,t3.commrange,"
				+ " t3.runstatus,t3.runtime,t3.runtimeefficiency,t3.runrange "
				+ " from tbl_wellinformation t "
				+ " left outer join tbl_rpc_productiondata_latest t2 on t.id=t2.wellid  "
				+ " left outer join tbl_rpc_discrete_latest  t3 on t3.wellId=t.id"
				+ " where t.liftingType between 200 and 299 ";
		String singleCalculateResuleSql="select t007.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.workingconditioncode,"
											+ "t.TheoreticalProduction,"
											+" t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,prod.watercut,"
											+ "t.availableplungerstrokeprod_v,t.pumpclearanceleakprod_v,t.tvleakvolumetricproduction,t.svleakvolumetricproduction,t.gasinfluenceprod_v,"
											+" t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction, prod.watercut_w,"
											+ "t.availableplungerstrokeprod_W,t.pumpclearanceleakprod_W,t.tvleakweightproduction,t.svleakweightproduction,t.gasinfluenceprod_W,"
											+" t.surfacesystemefficiency,t.welldownsystemefficiency,t.systemefficiency,t.powerconsumptionperthm,"
											+ "t.motorInputActivePower,t.polishrodPower,t.waterPower,"
											+ "t.stroke,t.spm,"
											+ "t.UpperLoadLine,t.LowerLoadLine,t.UpperLoadLineOfExact,"
											+ "t.UpperLoadLine-t.LowerLoadLine as DeltaLoadLine,t.UpperLoadLineOfExact-t.LowerLoadLine as DeltaLoadLineOfExact,"
											+ "t.FMax,t.FMin,t.FMax-t.FMin as DeltaF,t.fsdiagramarea,"
											+" t.PlungerStroke,t.AvailablePlungerStroke,"
											+ "t.noLiquidAvailablePlungerStroke,"//加1
											+ "t.fullnesscoefficient,"
											+ "t.noLiquidFullnessCoefficient,"//加1
											+ "prod.pumpborediameter/1000 as pumpborediameter,"
											+" case when prod.producingfluidlevel>=0 then prod.producingfluidlevel else t.inverProducingfluidlevel end as producingfluidlevel,"
											+ "prod.pumpsettingdepth,prod.pumpsettingdepth-prod.producingfluidlevel as submergence,"
											+ "t.levelCorrectValue,"//加1
											+ "t.pumpintakep,t.pumpintaket,t.pumpintakegol,t.pumpintakevisl,t.pumpintakebo,"
											+ "t.pumpoutletp,t.pumpoutlett,t.pumpOutletGol,t.pumpoutletvisl,t.pumpoutletbo,"
											+ "t.pumpeff,t.pumpeff1,t.pumpeff2,t.pumpeff3,t.pumpeff4,t.RodFlexLength,t.TubingFlexLength,t.InertiaLength,"
											+" t.wattdegreebalance,t.upstrokewattmax,t.downstrokewattmax,"
											+ "t.idegreebalance,t.upstrokeimax,t.downstrokeimax,"
											+ "t.deltaRadius,"
											+" prod.tubingpressure,prod.casingpressure,prod.wellheadfluidtemperature,prod.productiongasoilratio"
											+" from tbl_rpc_diagram_hist t ,tbl_wellinformation t007 ,tbl_rpc_productiondata_hist prod"
											+" where t.wellid=t007.id and t.productiondataid=prod.id "
											+" and  t.acqTime > "
											+" (select max(to_date(to_char(t2.acqTime,'yyyy-mm-dd'),'yyyy-mm-dd')) "
											+" from tbl_rpc_diagram_hist t2 where t2.wellId=t.wellid and t2.resultstatus=1 "
											+" and t2.acqTime< to_date('"+tatalDate+"','yyyy-mm-dd')) "
											+" and t.resultstatus=1 "
//											+" and t.workingconditioncode<>1232 "
											+" and t.acqTime<to_date('"+tatalDate+"','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(endAcqTime)){
			singleCalculateResuleSql+=" and t.acqTime<to_date('"+endAcqTime+"','yyyy-mm-dd hh24:mi:ss')";
		}
		String statusSql="select well.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange,"
				+ "t.runstatus,t.runtimeefficiency,t.runtime,t.runrange "
				+ " from tbl_rpc_discrete_hist t,tbl_wellinformation well "
				+ " where t.wellid=well.id and t.acqTime=( select max(t2.acqTime) from tbl_rpc_discrete_hist t2 where t2.wellid=t.wellid and t2.acqTime between to_date('"+tatalDate+"','yyyy-mm-dd')-1 and to_date('"+tatalDate+"','yyyy-mm-dd'))";
		if(StringManagerUtils.isNotNull(wellId)){
			wellinformationSql+=" and t.id in ("+wellId+")";
			singleCalculateResuleSql+=" and t007.id in ("+wellId+")";
			statusSql+=" and t.wellid in("+wellId+")";
		}
		
		singleCalculateResuleSql+=" order by t007.sortnum,t.acqTime";
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
									+ "\"AcqTime\": \""+statusObj[1]+"\","
									+ "\"CommStatus\": "+commStatus+","
									+ "\"CommEfficiency\": {"
									+ "\"Efficiency\": "+statusObj[3]+","
									+ "\"Time\": "+statusObj[4]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(statusObj[5]))+""
//									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(statusObj[5]+"")+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcqTime\":\""+tatalDate+" 01:00:00\","
									+ "\"CommStatus\":true"
									+ "}"
									+ "}";
							String runTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+wellObj[0]+"\","
									+ "\"Last\":{"
									+ "\"AcqTime\": \""+statusObj[1]+"\","
									+ "\"RunStatus\": "+runStatus+","
									+ "\"RunEfficiency\": {"
									+ "\"Efficiency\": "+statusObj[7]+","
									+ "\"Time\": "+statusObj[8]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(statusObj[9]))+""
//									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(statusObj[9]+"")+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcqTime\":\""+tatalDate+" 01:00:00\","
									+ "\"RunStatus\":true"
									+ "}"
									+ "}";
							Gson gson = new Gson();
							java.lang.reflect.Type type=null;
							if(!"0".equals(wellObj[2]+"")){
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
				List<String> acqTimeList=new ArrayList<String>();
				
				List<Integer> commStatusList=new ArrayList<Integer>();
				float commTime=0;
				float commTimeEfficiency=0;
				String commRange="";
				
				List<Integer> runStatusList=new ArrayList<Integer>();
				float runTime=0;
				float runTimeEfficiency=0;
				String runRange="";
				
				List<Integer> stopReasonList=new ArrayList<Integer>();
				List<Integer> startReasonList=new ArrayList<Integer>();
				
				List<Float> tubingPressureList=new ArrayList<Float>();
				List<Float> casingPressureList=new ArrayList<Float>();
				List<Float> wellHeadFluidTemperatureList=new ArrayList<Float>();
				List<Float> productionGasOilRatioList=new ArrayList<Float>();
				
				List<Integer> ResultCodeList=new ArrayList<Integer>();
				List<Float> strokeList=new ArrayList<Float>();
				List<Float> spmList=new ArrayList<Float>();
				
				List<Float> upperLoadLineList=new ArrayList<Float>();
				List<Float> lowerLoadLineList=new ArrayList<Float>();
				List<Float> upperLoadLineOfExactList=new ArrayList<Float>();
				List<Float> deltaLoadLineList=new ArrayList<Float>();
				List<Float> deltaLoadLineOfExactList=new ArrayList<Float>();
				
				List<Float> FMaxList=new ArrayList<Float>();
				List<Float> FMinList=new ArrayList<Float>();
				List<Float> deltaFList=new ArrayList<Float>();
				List<Float> areaList=new ArrayList<Float>();
				
				List<Float> plungerStrokeList=new ArrayList<Float>();
				List<Float> availablePlungerStrokeList=new ArrayList<Float>();
				List<Float> noLiquidAvailablePlungerStrokeList=new ArrayList<Float>();
				List<Float> fullnessCoefficientList=new ArrayList<Float>();
				List<Float> noLiquidFullnessCoefficientList=new ArrayList<Float>();
				
				List<Float> theoreticalProductionList=new ArrayList<Float>();
				
				List<Float> liquidVolumetricProductionList=new ArrayList<Float>();
				List<Float> oilVolumetricProductionList=new ArrayList<Float>();
				List<Float> waterVolumetricProductionList=new ArrayList<Float>();
				List<Float> volumeWaterCutList=new ArrayList<Float>();
				List<Float> availablePlungerStrokeVolumetricProductionList=new ArrayList<Float>();
				List<Float> pumpClearanceLeakVolumetricProductionList=new ArrayList<Float>();
				List<Float> TVLeakVolumetricProductionList=new ArrayList<Float>();
				List<Float> SVLeakVolumetricProductionList=new ArrayList<Float>();
				List<Float> gasInfluenceVolumetricProductionList=new ArrayList<Float>();
				
				List<Float> liquidWeightProductionList=new ArrayList<Float>();
				List<Float> oilWeightProductionList=new ArrayList<Float>();
				List<Float> waterWeightProductionList=new ArrayList<Float>();
				List<Float> weightWaterCutList=new ArrayList<Float>();
				List<Float> availablePlungerStrokeWeightProductionList=new ArrayList<Float>();
				List<Float> pumpClearanceLeakWeightProductionList=new ArrayList<Float>();
				List<Float> TVLeakWeightProductionList=new ArrayList<Float>();
				List<Float> SVLeakWeightProductionList=new ArrayList<Float>();
				List<Float> gasInfluenceWeightProductionList=new ArrayList<Float>();
				
				List<Float> pumpEffList=new ArrayList<Float>();
				List<Float> pumpEff1List=new ArrayList<Float>();
				List<Float> pumpEff2List=new ArrayList<Float>();
				List<Float> pumpEff3List=new ArrayList<Float>();
				List<Float> pumpEff4List=new ArrayList<Float>();
				
				List<Float> rodFlexLengthList=new ArrayList<Float>();
				List<Float> tubingFlexLengthList=new ArrayList<Float>();
				List<Float> inertiaLengthList=new ArrayList<Float>();
				
				
				List<Float> pumpBoreDiameterList=new ArrayList<Float>();
				List<Float> pumpSettingDepthList=new ArrayList<Float>();
				List<Float> producingfluidLevelList=new ArrayList<Float>();
				List<Float> levelCorrectValueList=new ArrayList<Float>();
				List<Float> submergenceList=new ArrayList<Float>();
				
				List<Float> pumpIntakePList=new ArrayList<Float>();
				List<Float> pumpIntakeTList=new ArrayList<Float>();
				List<Float> pumpIntakeGOLList=new ArrayList<Float>();
				List<Float> pumpIntakeVislList=new ArrayList<Float>();
				List<Float> pumpIntakeBoList=new ArrayList<Float>();
				
				List<Float> pumpOutletPList=new ArrayList<Float>();
				List<Float> pumpOutletTList=new ArrayList<Float>();
				List<Float> pumpOutletGOLList=new ArrayList<Float>();
				List<Float> pumpOutletVislList=new ArrayList<Float>();
				List<Float> pumpOutletBoList=new ArrayList<Float>();
				
				List<Float> wattDegreeBalanceList=new ArrayList<Float>();
				List<Float> upStrokeWattMaxList=new ArrayList<Float>();
				List<Float> downStrokeWattMaxList=new ArrayList<Float>();
				List<Float> iDegreeBalanceList=new ArrayList<Float>();
				List<Float> upStrokeIMaxList=new ArrayList<Float>();
				List<Float> downStrokeIMaxList=new ArrayList<Float>();
				List<Float> deltaRadiusList=new ArrayList<Float>();
				
				List<Float> surfaceSystemEfficiencyList=new ArrayList<Float>();
				List<Float> wellDownSystemEfficiencyList=new ArrayList<Float>();
				List<Float> systemEfficiencyList=new ArrayList<Float>();
				List<Float> powerConsumptionPerTHMList=new ArrayList<Float>();
				List<Float> avgWattList=new ArrayList<Float>();
				List<Float> polishRodPowerList=new ArrayList<Float>();
				List<Float> waterPowerList=new ArrayList<Float>();
				
				List<Integer> ETResultCodeList=new ArrayList<Integer>();
				List<Float> IaList=new ArrayList<Float>();
				List<Float> IbList=new ArrayList<Float>();
				List<Float> IcList=new ArrayList<Float>();
				List<Float> VaList=new ArrayList<Float>();
				List<Float> VbList=new ArrayList<Float>();
				List<Float> VcList=new ArrayList<Float>();
				List<Float> RunFrequencyList=new ArrayList<Float>();
				List<Float> Watt3List=new ArrayList<Float>();
				List<Float> Var3List=new ArrayList<Float>();
				List<Float> VA3List=new ArrayList<Float>();
				List<Float> PF3List=new ArrayList<Float>();
				
				List<Float> SignalList=new ArrayList<Float>();
				List<Float> rpmList=new ArrayList<Float>();
				
				
				
				for(int j=0;j<singleresultlist.size();j++){
					Object[] resuleObj=(Object[]) singleresultlist.get(j);
					if(wellObj[0].toString().equals(resuleObj[0].toString())){
						acqTimeList.add(resuleObj[1]+"");
						
						if(commResponseData!=null&&commResponseData.getResultStatus()==1&&commResponseData.getDaily().getCommEfficiency().getRange()!=null&&commResponseData.getDaily().getCommEfficiency().getRange().size()>0){
							commStatusList.add(commResponseData.getDaily().getCommStatus()?1:0);
							commTime=commResponseData.getDaily().getCommEfficiency().getTime();
							commTimeEfficiency=commResponseData.getDaily().getCommEfficiency().getEfficiency();
							commRange=commResponseData.getDaily().getCommEfficiency().getRangeString();
						}else if(StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())).equals(tatalDate)){//如果是实时汇总
							commStatusList.add(StringManagerUtils.stringToInteger(wellObj[5]+""));
							commTime=StringManagerUtils.stringToFloat(wellObj[6]+"");
							commTimeEfficiency=StringManagerUtils.stringToFloat(wellObj[7]+"");
							commRange=StringManagerUtils.CLOBObjectToString(wellObj[8]);
//							commRange=wellObj[8]+"";
						}
						if("0".equals(wellObj[2]+"")){//如果时率来源是人工计算
							runStatusList.add(1);
							runTime=StringManagerUtils.stringToFloat(wellObj[1]+"");
							runTimeEfficiency=StringManagerUtils.stringToFloat(wellObj[1]+"", 2)/24;
							runRange=getWellRuningTime(StringManagerUtils.getOneDayDateString(-1),wellObj[1],null);
						}else{
							if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1&&timeEffResponseData.getDaily().getRunEfficiency().getRange()!=null&&timeEffResponseData.getDaily().getRunEfficiency().getRange().size()>0){
								runStatusList.add(timeEffResponseData.getDaily().getRunStatus()?1:0);
								runTime=timeEffResponseData.getDaily().getRunEfficiency().getTime();
								runTimeEfficiency=timeEffResponseData.getDaily().getRunEfficiency().getEfficiency();
								runRange=timeEffResponseData.getDaily().getRunEfficiency().getRangeString();
							}else if(StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())).equals(tatalDate)){//如果是实时汇总
								runStatusList.add(StringManagerUtils.stringToInteger(wellObj[9]+""));
								runTime=StringManagerUtils.stringToFloat(wellObj[10]+"");
								runTimeEfficiency=StringManagerUtils.stringToFloat(wellObj[11]+"");
								runRange=StringManagerUtils.CLOBObjectToString(wellObj[12]);
//								runRange=wellObj[12]+"";
							}
						}
						ResultCodeList.add(StringManagerUtils.stringToInteger(resuleObj[2]+""));
						theoreticalProductionList.add(StringManagerUtils.stringToFloat(resuleObj[3]+""));
						liquidVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[4]+""));
						oilVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[5]+""));
						waterVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[6]+""));
						volumeWaterCutList.add(StringManagerUtils.stringToFloat(resuleObj[7]+""));
						availablePlungerStrokeVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[8]+""));
						pumpClearanceLeakVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[9]+""));
						TVLeakVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[10]+""));
						SVLeakVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[11]+""));
						gasInfluenceVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[12]+""));
						
						liquidWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[13]+""));
						oilWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[14]+""));
						waterWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[15]+""));
						weightWaterCutList.add(StringManagerUtils.stringToFloat(resuleObj[16]+""));
						availablePlungerStrokeWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[17]+""));
						pumpClearanceLeakWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[18]+""));
						TVLeakWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[19]+""));
						SVLeakWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[20]+""));
						gasInfluenceWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[21]+""));
						
						surfaceSystemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[22]+""));
						wellDownSystemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[23]+""));
						systemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[24]+""));
						powerConsumptionPerTHMList.add(StringManagerUtils.stringToFloat(resuleObj[25]+""));
						avgWattList.add(StringManagerUtils.stringToFloat(resuleObj[26]+""));
						polishRodPowerList.add(StringManagerUtils.stringToFloat(resuleObj[27]+""));
						waterPowerList.add(StringManagerUtils.stringToFloat(resuleObj[28]+""));
						
						strokeList.add(StringManagerUtils.stringToFloat(resuleObj[29]+""));
						spmList.add(StringManagerUtils.stringToFloat(resuleObj[30]+""));
						upperLoadLineList.add(StringManagerUtils.stringToFloat(resuleObj[31]+""));
						lowerLoadLineList.add(StringManagerUtils.stringToFloat(resuleObj[32]+""));
						upperLoadLineOfExactList.add(StringManagerUtils.stringToFloat(resuleObj[33]+""));
						deltaLoadLineList.add(StringManagerUtils.stringToFloat(resuleObj[34]+""));
						deltaLoadLineOfExactList.add(StringManagerUtils.stringToFloat(resuleObj[35]+""));
						FMaxList.add(StringManagerUtils.stringToFloat(resuleObj[36]+""));
						FMinList.add(StringManagerUtils.stringToFloat(resuleObj[37]+""));
						deltaFList.add(StringManagerUtils.stringToFloat(resuleObj[38]+""));
						areaList.add(StringManagerUtils.stringToFloat(resuleObj[39]+""));
						plungerStrokeList.add(StringManagerUtils.stringToFloat(resuleObj[40]+""));
						availablePlungerStrokeList.add(StringManagerUtils.stringToFloat(resuleObj[41]+""));
						noLiquidAvailablePlungerStrokeList.add(StringManagerUtils.stringToFloat(resuleObj[42]+""));
						fullnessCoefficientList.add(StringManagerUtils.stringToFloat(resuleObj[43]+""));
						noLiquidFullnessCoefficientList.add(StringManagerUtils.stringToFloat(resuleObj[44]+""));
						
						pumpBoreDiameterList.add(StringManagerUtils.stringToFloat(resuleObj[45]+""));
						producingfluidLevelList.add(StringManagerUtils.stringToFloat(resuleObj[46]+""));
						pumpSettingDepthList.add(StringManagerUtils.stringToFloat(resuleObj[47]+""));
						submergenceList.add(StringManagerUtils.stringToFloat(resuleObj[48]+""));
						levelCorrectValueList.add(StringManagerUtils.stringToFloat(resuleObj[49]+""));
						
						pumpIntakePList.add(StringManagerUtils.stringToFloat(resuleObj[50]+""));
						pumpIntakeTList.add(StringManagerUtils.stringToFloat(resuleObj[51]+""));
						pumpIntakeGOLList.add(StringManagerUtils.stringToFloat(resuleObj[52]+""));
						pumpIntakeVislList.add(StringManagerUtils.stringToFloat(resuleObj[53]+""));
						pumpIntakeBoList.add(StringManagerUtils.stringToFloat(resuleObj[54]+""));
						
						pumpOutletPList.add(StringManagerUtils.stringToFloat(resuleObj[55]+""));
						pumpOutletTList.add(StringManagerUtils.stringToFloat(resuleObj[56]+""));
						pumpOutletGOLList.add(StringManagerUtils.stringToFloat(resuleObj[57]+""));
						pumpOutletVislList.add(StringManagerUtils.stringToFloat(resuleObj[58]+""));
						pumpOutletBoList.add(StringManagerUtils.stringToFloat(resuleObj[59]+""));
						
						pumpEffList.add(StringManagerUtils.stringToFloat(resuleObj[60]+""));
						pumpEff1List.add(StringManagerUtils.stringToFloat(resuleObj[61]+""));
						pumpEff2List.add(StringManagerUtils.stringToFloat(resuleObj[62]+""));
						pumpEff3List.add(StringManagerUtils.stringToFloat(resuleObj[63]+""));
						pumpEff4List.add(StringManagerUtils.stringToFloat(resuleObj[64]+""));
						
						rodFlexLengthList.add(StringManagerUtils.stringToFloat(resuleObj[65]+""));
						tubingFlexLengthList.add(StringManagerUtils.stringToFloat(resuleObj[66]+""));
						inertiaLengthList.add(StringManagerUtils.stringToFloat(resuleObj[67]+""));
						
						wattDegreeBalanceList.add(StringManagerUtils.stringToFloat(resuleObj[68]+""));
						upStrokeWattMaxList.add(StringManagerUtils.stringToFloat(resuleObj[69]+""));
						downStrokeWattMaxList.add(StringManagerUtils.stringToFloat(resuleObj[70]+""));
						
						iDegreeBalanceList.add(StringManagerUtils.stringToFloat(resuleObj[71]+""));
						upStrokeIMaxList.add(StringManagerUtils.stringToFloat(resuleObj[72]+""));
						downStrokeIMaxList.add(StringManagerUtils.stringToFloat(resuleObj[73]+""));
						
						deltaRadiusList.add(StringManagerUtils.stringToFloat(resuleObj[74]+""));
						
						tubingPressureList.add(StringManagerUtils.stringToFloat(resuleObj[75]+""));
						casingPressureList.add(StringManagerUtils.stringToFloat(resuleObj[76]+""));
						wellHeadFluidTemperatureList.add(StringManagerUtils.stringToFloat(resuleObj[77]+""));
						productionGasOilRatioList.add(StringManagerUtils.stringToFloat(resuleObj[78]+""));
					}
				}
				
				dataSbf = new StringBuffer();
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+wellObj[0]+"\",");
				dataSbf.append("\"Date\":\""+date+"\",");
				if(StringManagerUtils.isNotNull(endAcqTime)){
					dataSbf.append("\"EndAcqTime\":\""+endAcqTime+"\",");
				}else{
					dataSbf.append("\"EndAcqTime\":\""+acqTimeList.get(acqTimeList.size()-1)+"\",");
				}
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
				dataSbf.append("\"AvailablePlungerStrokeVolumetricProduction\":["+StringUtils.join(availablePlungerStrokeVolumetricProductionList, ",")+"],");
				dataSbf.append("\"PumpClearanceLeakVolumetricProduction\":["+StringUtils.join(pumpClearanceLeakVolumetricProductionList, ",")+"],");
				dataSbf.append("\"TVLeakVolumetricProduction\":["+StringUtils.join(TVLeakVolumetricProductionList, ",")+"],");
				dataSbf.append("\"SVLeakVolumetricProduction\":["+StringUtils.join(SVLeakVolumetricProductionList, ",")+"],");
				dataSbf.append("\"GasInfluenceVolumetricProduction\":["+StringUtils.join(gasInfluenceVolumetricProductionList, ",")+"],");
				
				dataSbf.append("\"LiquidWeightProduction\":["+StringUtils.join(liquidWeightProductionList, ",")+"],");
				dataSbf.append("\"OilWeightProduction\":["+StringUtils.join(oilWeightProductionList, ",")+"],");
				dataSbf.append("\"WaterWeightProduction\":["+StringUtils.join(waterWeightProductionList, ",")+"],");
				dataSbf.append("\"WeightWaterCut\":["+StringUtils.join(weightWaterCutList, ",")+"],");
				dataSbf.append("\"AvailablePlungerStrokeWeightProduction\":["+StringUtils.join(availablePlungerStrokeWeightProductionList, ",")+"],");
				dataSbf.append("\"PumpClearanceLeakWeightProduction\":["+StringUtils.join(pumpClearanceLeakWeightProductionList, ",")+"],");
				dataSbf.append("\"TVLeakWeightProduction\":["+StringUtils.join(TVLeakWeightProductionList, ",")+"],");
				dataSbf.append("\"SVLeakWeightProduction\":["+StringUtils.join(SVLeakWeightProductionList, ",")+"],");
				dataSbf.append("\"GasInfluenceWeightProduction\":["+StringUtils.join(gasInfluenceWeightProductionList, ",")+"],");
				
				dataSbf.append("\"SurfaceSystemEfficiency\":["+StringUtils.join(surfaceSystemEfficiencyList, ",")+"],");
				dataSbf.append("\"WellDownSystemEfficiency\":["+StringUtils.join(wellDownSystemEfficiencyList, ",")+"],");
				dataSbf.append("\"SystemEfficiency\":["+StringUtils.join(systemEfficiencyList, ",")+"],");
				dataSbf.append("\"EnergyPer100mLift\":["+StringUtils.join(powerConsumptionPerTHMList, ",")+"],");
				dataSbf.append("\"AvgWatt\":["+StringUtils.join(avgWattList, ",")+"],");
				dataSbf.append("\"PolishRodPower\":["+StringUtils.join(polishRodPowerList, ",")+"],");
				dataSbf.append("\"WaterPower\":["+StringUtils.join(waterPowerList, ",")+"],");
				
				dataSbf.append("\"Stroke\":["+StringUtils.join(strokeList, ",")+"],");
				dataSbf.append("\"SPM\":["+StringUtils.join(spmList, ",")+"],");
				dataSbf.append("\"UpperLoadLine\":["+StringUtils.join(upperLoadLineList, ",")+"],");
				dataSbf.append("\"LowerLoadLine\":["+StringUtils.join(lowerLoadLineList, ",")+"],");
				dataSbf.append("\"UpperLoadLineOfExact\":["+StringUtils.join(upperLoadLineOfExactList, ",")+"],");
				dataSbf.append("\"DeltaLoadLine\":["+StringUtils.join(deltaLoadLineList, ",")+"],");
				dataSbf.append("\"DeltaLoadLineOfExact\":["+StringUtils.join(deltaLoadLineOfExactList, ",")+"],");
				
				dataSbf.append("\"FMax\":["+StringUtils.join(FMaxList, ",")+"],");
				dataSbf.append("\"FMin\":["+StringUtils.join(FMinList, ",")+"],");
				dataSbf.append("\"DeltaF\":["+StringUtils.join(deltaFList, ",")+"],");
				dataSbf.append("\"Area\":["+StringUtils.join(areaList, ",")+"],");
				dataSbf.append("\"PlungerStroke\":["+StringUtils.join(plungerStrokeList, ",")+"],");
				dataSbf.append("\"AvailablePlungerStroke\":["+StringUtils.join(availablePlungerStrokeList, ",")+"],");
				dataSbf.append("\"NoLiquidAvailablePlungerStroke\":["+StringUtils.join(noLiquidAvailablePlungerStrokeList, ",")+"],");
				dataSbf.append("\"FullnessCoefficient\":["+StringUtils.join(fullnessCoefficientList, ",")+"],");
				dataSbf.append("\"NoLiquidFullnessCoefficient\":["+StringUtils.join(noLiquidFullnessCoefficientList, ",")+"],");
				
				dataSbf.append("\"PumpBoreDiameter\":["+StringUtils.join(pumpBoreDiameterList, ",")+"],");
				dataSbf.append("\"ProducingfluidLevel\":["+StringUtils.join(producingfluidLevelList, ",")+"],");
				dataSbf.append("\"PumpSettingDepth\":["+StringUtils.join(pumpSettingDepthList, ",")+"],");
				dataSbf.append("\"Submergence\":["+StringUtils.join(submergenceList, ",")+"],");
				dataSbf.append("\"LevelCorrectValue\":["+StringUtils.join(levelCorrectValueList, ",")+"],");
				
				dataSbf.append("\"PumpIntakeP\":["+StringUtils.join(pumpIntakePList, ",")+"],");
				dataSbf.append("\"PumpIntakeT\":["+StringUtils.join(pumpIntakeTList, ",")+"],");
				dataSbf.append("\"PumpIntakeGOL\":["+StringUtils.join(pumpIntakeGOLList, ",")+"],");
				dataSbf.append("\"PumpIntakeVisl\":["+StringUtils.join(pumpIntakeVislList, ",")+"],");
				dataSbf.append("\"PumpIntakeBo\":["+StringUtils.join(pumpIntakeBoList, ",")+"],");
				
				dataSbf.append("\"PumpOutletP\":["+StringUtils.join(pumpOutletPList, ",")+"],");
				dataSbf.append("\"PumpOutletT\":["+StringUtils.join(pumpOutletTList, ",")+"],");
				dataSbf.append("\"PumpOutletGOL\":["+StringUtils.join(pumpOutletGOLList, ",")+"],");
				dataSbf.append("\"PumpOutletVisl\":["+StringUtils.join(pumpOutletVislList, ",")+"],");
				dataSbf.append("\"PumpOutletBo\":["+StringUtils.join(pumpOutletBoList, ",")+"],");
				
				dataSbf.append("\"PumpEff\":["+StringUtils.join(pumpEffList, ",")+"],");
				dataSbf.append("\"PumpEff1\":["+StringUtils.join(pumpEff1List, ",")+"],");
				dataSbf.append("\"PumpEff2\":["+StringUtils.join(pumpEff2List, ",")+"],");
				dataSbf.append("\"PumpEff3\":["+StringUtils.join(pumpEff3List, ",")+"],");
				dataSbf.append("\"PumpEff4\":["+StringUtils.join(pumpEff4List, ",")+"],");
				
				dataSbf.append("\"RodFlexLength\":["+StringUtils.join(rodFlexLengthList, ",")+"],");
				dataSbf.append("\"TubingFlexLength\":["+StringUtils.join(tubingFlexLengthList, ",")+"],");
				dataSbf.append("\"InertiaLength\":["+StringUtils.join(inertiaLengthList, ",")+"],");
				
				dataSbf.append("\"WattDegreeBalance\":["+StringUtils.join(wattDegreeBalanceList, ",")+"],");
				dataSbf.append("\"UpStrokeWattMax\":["+StringUtils.join(upStrokeWattMaxList, ",")+"],");
				dataSbf.append("\"DownStrokeWattMax\":["+StringUtils.join(downStrokeWattMaxList, ",")+"],");
				
				dataSbf.append("\"IDegreeBalance\":["+StringUtils.join(iDegreeBalanceList, ",")+"],");
				dataSbf.append("\"UpStrokeIMax\":["+StringUtils.join(upStrokeIMaxList, ",")+"],");
				dataSbf.append("\"DownStrokeIMax\":["+StringUtils.join(downStrokeIMaxList, ",")+"],");
				
				dataSbf.append("\"DeltaRadius\":["+StringUtils.join(deltaRadiusList, ",")+"],");
				
				dataSbf.append("\"TubingPressure\":["+StringUtils.join(tubingPressureList, ",")+"],");
				dataSbf.append("\"CasingPressure\":["+StringUtils.join(casingPressureList, ",")+"],");
				dataSbf.append("\"WellHeadFluidTemperature\":["+StringUtils.join(wellHeadFluidTemperatureList, ",")+"],");
				dataSbf.append("\"ProductionGasOilRatio\":["+StringUtils.join(productionGasOilRatioList, ",")+"]");
				
				dataSbf.append("}");
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
		String date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(tatalDate),-1);
		StringBuffer dataSbf=null;
		List<String> requestDataList=new ArrayList<String>();
		String timeEffTotalUrl=Config.getInstance().configFile.getAgileCalculate().getRun()[0];
		String commTotalUrl=Config.getInstance().configFile.getAgileCalculate().getCommunication()[0];
		String wellinformationSql="select t.wellname,t2.runtime as runtime2,t.runtimeefficiencysource,t.deviceaddr,t.deviceid,"
				+ " t3.commstatus,t3.commtime,t3.commtimeefficiency,t3.commrange,"
				+ " t3.runstatus,t3.runtime,t3.runtimeefficiency,t3.runrange "
				+ " from tbl_wellinformation t "
				+ " left outer join tbl_pcp_productiondata_latest t2 on t.id=t2.wellid  "
				+ " left outer join tbl_pcp_discrete_latest  t3 on t3.wellId=t.id"
				+ " where t.liftingType between 400 and 499 ";
		String singleCalculateResuleSql="select t007.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.rpm,"
				+ "t.TheoreticalProduction,"							
				+" t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,prod.watercut,"
				+" t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction, prod.watercut_w,"
				+" t.systemefficiency,t.powerconsumptionperthm,"
				+ "t.motorInputActivePower,t.waterPower,"
				+" prod.producingfluidlevel,prod.pumpsettingdepth,prod.pumpsettingdepth-prod.producingfluidlevel as submergence,"
				+ "t.pumpeff,t.pumpeff1,t.pumpeff2,"
				+" prod.tubingpressure,prod.casingpressure,prod.wellheadfluidtemperature,prod.productiongasoilratio"
				+" from tbl_pcp_rpm_hist t ,tbl_wellinformation t007 ,tbl_pcp_productiondata_hist prod"
				+" where t.wellid=t007.id and t.productiondataid=prod.id "
				+" and  t.acqTime > "
				+" (select max(to_date(to_char(t2.acqTime,'yyyy-mm-dd'),'yyyy-mm-dd')) "
				+" from tbl_pcp_rpm_hist t2 where t2.wellId=t.wellid and t2.resultstatus=1 "
				+" and t2.acqTime< to_date('"+tatalDate+"','yyyy-mm-dd')) "
				+" and t.resultstatus=1 and t.acqTime<to_date('"+tatalDate+"','yyyy-mm-dd')";
		String statusSql="select well.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange,"
				+ "t.runstatus,t.runtimeefficiency,t.runtime,t.runrange "
				+ " from tbl_pcp_discrete_hist t,tbl_wellinformation well "
				+ " where t.wellid=well.id and t.acqTime=( select max(t2.acqTime) from tbl_pcp_discrete_hist t2 where t2.wellid=t.wellid and t2.acqTime between to_date('"+tatalDate+"','yyyy-mm-dd')-1 and to_date('"+tatalDate+"','yyyy-mm-dd'))";
		if(StringManagerUtils.isNotNull(wellId)){
			wellinformationSql+=" and t.id in ("+wellId+")";
			singleCalculateResuleSql+=" and t007.id in ("+wellId+")";
			statusSql+=" and t.wellid in("+wellId+")";
		}
		
		singleCalculateResuleSql+=" order by t007.sortnum,t.acqTime";
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
									+ "\"AcqTime\": \""+statusObj[1]+"\","
									+ "\"CommStatus\": "+commStatus+","
									+ "\"CommEfficiency\": {"
									+ "\"Efficiency\": "+statusObj[3]+","
									+ "\"Time\": "+statusObj[4]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(statusObj[5]))+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcqTime\":\""+tatalDate+" 01:00:00\","
									+ "\"CommStatus\":true"
									+ "}"
									+ "}";
							String runTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+wellObj[0]+"\","
									+ "\"Last\":{"
									+ "\"AcqTime\": \""+statusObj[1]+"\","
									+ "\"RunStatus\": "+runStatus+","
									+ "\"RunEfficiency\": {"
									+ "\"Efficiency\": "+statusObj[7]+","
									+ "\"Time\": "+statusObj[8]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(statusObj[9]))+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcqTime\":\""+tatalDate+" 01:00:00\","
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
				
				List<String> acqTimeList=new ArrayList<String>();
				
				List<Integer> commStatusList=new ArrayList<Integer>();
				float commTime=0;
				float commTimeEfficiency=0;
				String commRange="";
				
				List<Integer> runStatusList=new ArrayList<Integer>();
				float runTime=0;
				float runTimeEfficiency=0;
				String runRange="";
				
				List<Integer> stopReasonList=new ArrayList<Integer>();
				List<Integer> startReasonList=new ArrayList<Integer>();
				
				List<Float> tubingPressureList=new ArrayList<Float>();
				List<Float> casingPressureList=new ArrayList<Float>();
				List<Float> wellHeadFluidTemperatureList=new ArrayList<Float>();
				List<Float> productionGasOilRatioList=new ArrayList<Float>();
				
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
				
				List<Float> pumpSettingDepthList=new ArrayList<Float>();
				List<Float> producingfluidLevelList=new ArrayList<Float>();
				List<Float> submergenceList=new ArrayList<Float>();
				
				List<Float> systemEfficiencyList=new ArrayList<Float>();
				List<Float> powerConsumptionPerTHMList=new ArrayList<Float>();
				List<Float> avgWattList=new ArrayList<Float>();
				List<Float> waterPowerList=new ArrayList<Float>();
				
				List<Float> rpmList=new ArrayList<Float>();
				
				for(int j=0;j<singleresultlist.size();j++){
					Object[] resuleObj=(Object[]) singleresultlist.get(j);
					if(wellObj[0].toString().equals(resuleObj[0].toString())){
						acqTimeList.add(resuleObj[1]+"");
						if(commResponseData!=null&&commResponseData.getResultStatus()==1&&commResponseData.getDaily().getCommEfficiency().getRange()!=null&&commResponseData.getDaily().getCommEfficiency().getRange().size()>0){
							commStatusList.add(commResponseData.getDaily().getCommStatus()?1:0);
							commTime=commResponseData.getDaily().getCommEfficiency().getTime();
							commTimeEfficiency=commResponseData.getDaily().getCommEfficiency().getEfficiency();
							commRange=commResponseData.getDaily().getCommEfficiency().getRangeString();
						}else if(StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())).equals(tatalDate)){//如果是实时汇总
							commStatusList.add(StringManagerUtils.stringToInteger(wellObj[5]+""));
							commTime=StringManagerUtils.stringToFloat(wellObj[6]+"");
							commTimeEfficiency=StringManagerUtils.stringToFloat(wellObj[7]+"");
							commRange=StringManagerUtils.CLOBObjectToString(wellObj[8]);
//							commRange=wellObj[8]+"";
						}
						if("0".equals(wellObj[2]+"")){//如果时率来源是人工计算
							runStatusList.add(1);
							runTime=StringManagerUtils.stringToFloat(wellObj[1]+"");
							runTimeEfficiency=StringManagerUtils.stringToFloat(wellObj[1]+"", 2)/24;
							runRange=getWellRuningTime(StringManagerUtils.getOneDayDateString(-1),wellObj[1],null);
							
						}else{
							if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1&&timeEffResponseData.getDaily().getRunEfficiency().getRange()!=null&&timeEffResponseData.getDaily().getRunEfficiency().getRange().size()>0){
								runStatusList.add(timeEffResponseData.getDaily().getRunStatus()?1:0);
								runTime=timeEffResponseData.getDaily().getRunEfficiency().getTime();
								runTimeEfficiency=timeEffResponseData.getDaily().getRunEfficiency().getEfficiency();
								runRange=timeEffResponseData.getDaily().getRunEfficiency().getRangeString();
							}else if(StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())).equals(tatalDate)){//如果是实时汇总
								runStatusList.add(StringManagerUtils.stringToInteger(wellObj[9]+""));
								runTime=StringManagerUtils.stringToFloat(wellObj[10]+"");
								runTimeEfficiency=StringManagerUtils.stringToFloat(wellObj[11]+"");
								runRange=StringManagerUtils.CLOBObjectToString(wellObj[12]);
//								runRange=wellObj[12]+"";
							}
						}
						rpmList.add(StringManagerUtils.stringToFloat(resuleObj[2]+""));
						
						theoreticalProductionList.add(StringManagerUtils.stringToFloat(resuleObj[3]+""));
						
						liquidVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[4]+""));
						oilVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[5]+""));
						waterVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[6]+""));
						volumeWaterCutList.add(StringManagerUtils.stringToFloat(resuleObj[7]+""));
						
						liquidWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[8]+""));
						oilWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[9]+""));
						waterWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[10]+""));
						weightWaterCutList.add(StringManagerUtils.stringToFloat(resuleObj[11]+""));
						
						systemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[12]+""));
						powerConsumptionPerTHMList.add(StringManagerUtils.stringToFloat(resuleObj[13]+""));
						avgWattList.add(StringManagerUtils.stringToFloat(resuleObj[14]+""));
						waterPowerList.add(StringManagerUtils.stringToFloat(resuleObj[15]+""));
						
						producingfluidLevelList.add(StringManagerUtils.stringToFloat(resuleObj[16]+""));
						pumpSettingDepthList.add(StringManagerUtils.stringToFloat(resuleObj[17]+""));
						submergenceList.add(StringManagerUtils.stringToFloat(resuleObj[18]+""));
						
						pumpEffList.add(StringManagerUtils.stringToFloat(resuleObj[19]+""));
						pumpEff1List.add(StringManagerUtils.stringToFloat(resuleObj[20]+""));
						pumpEff2List.add(StringManagerUtils.stringToFloat(resuleObj[21]+""));
						
						tubingPressureList.add(StringManagerUtils.stringToFloat(resuleObj[22]+""));
						casingPressureList.add(StringManagerUtils.stringToFloat(resuleObj[23]+""));
						wellHeadFluidTemperatureList.add(StringManagerUtils.stringToFloat(resuleObj[24]+""));
						productionGasOilRatioList.add(StringManagerUtils.stringToFloat(resuleObj[25]+""));
					}
				}
				
				dataSbf = new StringBuffer();
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+wellObj[0]+"\",");
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
				dataSbf.append("\"PowerConsumptionPerTHM\":["+StringUtils.join(powerConsumptionPerTHMList, ",")+"],");
				dataSbf.append("\"AvgWatt\":["+StringUtils.join(avgWattList, ",")+"],");
				dataSbf.append("\"WaterPower\":["+StringUtils.join(waterPowerList, ",")+"],");
				
				
				
				dataSbf.append("\"ProducingfluidLevel\":["+StringUtils.join(producingfluidLevelList, ",")+"],");
				dataSbf.append("\"PumpSettingDepth\":["+StringUtils.join(pumpSettingDepthList, ",")+"],");
				dataSbf.append("\"Submergence\":["+StringUtils.join(submergenceList, ",")+"],");
				
				dataSbf.append("\"PumpEff\":["+StringUtils.join(pumpEffList, ",")+"],");
				dataSbf.append("\"PumpEff1\":["+StringUtils.join(pumpEff1List, ",")+"],");
				dataSbf.append("\"PumpEff2\":["+StringUtils.join(pumpEff2List, ",")+"],");
				
				dataSbf.append("\"TubingPressure\":["+StringUtils.join(tubingPressureList, ",")+"],");
				dataSbf.append("\"CasingPressure\":["+StringUtils.join(casingPressureList, ",")+"],");
				dataSbf.append("\"WellHeadFluidTemperature\":["+StringUtils.join(wellHeadFluidTemperatureList, ",")+"],");
				dataSbf.append("\"ProductionGasOilRatio\":["+StringUtils.join(productionGasOilRatioList, ",")+"],");
				
				dataSbf.append("\"RPM\":["+StringUtils.join(rpmList, ",")+"]");
				
				dataSbf.append("}");
				requestDataList.add(dataSbf.toString());
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		//遍历无井环的井
		
		return requestDataList;
	}
	
	public List<String> getPCPRPMDailyCalculationRequestData2(String tatalDate,String wellId) throws ParseException{
		String date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(tatalDate),-1);
		StringBuffer dataSbf=null;
		List<String> requestDataList=new ArrayList<String>();
		String timeEffTotalUrl=Config.getInstance().configFile.getAgileCalculate().getRun()[0];
		String commTotalUrl=Config.getInstance().configFile.getAgileCalculate().getCommunication()[0];
		String wellinformationSql="select t.wellname,t2.runtime as runtime2,t.runtimeefficiencysource,t.deviceaddr,t.deviceid,"
				+ " t3.commstatus,t3.commtime,t3.commtimeefficiency,t3.commrange,"
				+ " t3.runstatus,t3.runtime,t3.runtimeefficiency,t3.runrange "
				+ " from tbl_wellinformation t "
				+ " left outer join tbl_pcp_productiondata_latest t2 on t.id=t2.wellid  "
				+ " left outer join tbl_pcp_discrete_latest  t3 on t3.wellId=t.id"
				+ " where t.liftingType between 400 and 499 ";
		String singleCalculateResuleSql="select t007.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.rpm,"
				+ "t.TheoreticalProduction,"							
				+" t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,prod.watercut,"
				+" t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction, prod.watercut_w,"
				+" t.systemefficiency,t.powerconsumptionperthm,"
				+ "t.motorInputActivePower,t.waterPower,"
				+" prod.producingfluidlevel,prod.pumpsettingdepth,prod.pumpsettingdepth-prod.producingfluidlevel as submergence,"
				+ "t.pumpeff,t.pumpeff1,t.pumpeff2,"
				+" prod.tubingpressure,prod.casingpressure,prod.wellheadfluidtemperature,prod.productiongasoilratio"
				+" from tbl_pcp_rpm_hist t ,tbl_wellinformation t007 ,tbl_pcp_productiondata_hist prod"
				+" where t.wellid=t007.id and t.productiondataid=prod.id "
				+" and  t.acqTime > "
				+" (select max(to_date(to_char(t2.acqTime,'yyyy-mm-dd'),'yyyy-mm-dd')) "
				+" from tbl_pcp_rpm_hist t2 where t2.wellId=t.wellid and t2.resultstatus=1 "
				+" and t2.acqTime< to_date('"+tatalDate+"','yyyy-mm-dd')) "
				+" and t.resultstatus=1 and t.acqTime<to_date('"+tatalDate+"','yyyy-mm-dd')";
		String statusSql="select well.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange,"
				+ "t.runstatus,t.runtimeefficiency,t.runtime,t.runrange "
				+ " from tbl_pcp_discrete_hist t,tbl_wellinformation well "
				+ " where t.wellid=well.id and t.acqTime=( select max(t2.acqTime) from tbl_pcp_discrete_hist t2 where t2.wellid=t.wellid and t2.acqTime between to_date('"+tatalDate+"','yyyy-mm-dd')-1 and to_date('"+tatalDate+"','yyyy-mm-dd'))";
		if(StringManagerUtils.isNotNull(wellId)){
			wellinformationSql+=" and t.id in ("+wellId+")";
			singleCalculateResuleSql+=" and t007.id in ("+wellId+")";
			statusSql+=" and t.wellid in("+wellId+")";
		}
		
		singleCalculateResuleSql+=" order by t007.sortnum,t.acqTime";
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
									+ "\"AcqTime\": \""+statusObj[1]+"\","
									+ "\"CommStatus\": "+commStatus+","
									+ "\"CommEfficiency\": {"
									+ "\"Efficiency\": "+statusObj[3]+","
									+ "\"Time\": "+statusObj[4]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(statusObj[5]))+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcqTime\":\""+tatalDate+" 01:00:00\","
									+ "\"CommStatus\":true"
									+ "}"
									+ "}";
							String runTotalRequestData="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+wellObj[0]+"\","
									+ "\"Last\":{"
									+ "\"AcqTime\": \""+statusObj[1]+"\","
									+ "\"RunStatus\": "+runStatus+","
									+ "\"RunEfficiency\": {"
									+ "\"Efficiency\": "+statusObj[7]+","
									+ "\"Time\": "+statusObj[8]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(statusObj[9]))+""
									+ "}"
									+ "},"
									+ "\"Current\": {"
									+ "\"AcqTime\":\""+tatalDate+" 01:00:00\","
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
				dataSbf.append("\"Date\":\""+date+"\",");
				dataSbf.append("\"EveryTime\": [");
				for(int j=0;j<singleresultlist.size();j++){
					Object[] resuleObj=(Object[]) singleresultlist.get(j);
					if(wellObj[0].toString().equals(resuleObj[0].toString())){
						dataSbf.append("{\"AcqTime\":\""+resuleObj[1]+"\",");
						if(commResponseData!=null&&commResponseData.getResultStatus()==1&&commResponseData.getDaily().getCommEfficiency().getRange()!=null&&commResponseData.getDaily().getCommEfficiency().getRange().size()>0){
							dataSbf.append("\"CommStatus\":"+(commResponseData.getDaily().getCommStatus()?1:0)+",");
							dataSbf.append("\"CommTime\":"+commResponseData.getDaily().getCommEfficiency().getTime()+",");
							dataSbf.append("\"CommTimeEfficiency\":"+commResponseData.getDaily().getCommEfficiency().getEfficiency()+",");
							dataSbf.append("\"CommRange\":\""+commResponseData.getDaily().getCommEfficiency().getRangeString()+"\",");
						}else if(StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())).equals(tatalDate)){//如果是实时汇总
							dataSbf.append("\"CommStatus\":"+wellObj[5]+",");
							dataSbf.append("\"CommTime\":"+wellObj[6]+",");
							dataSbf.append("\"CommTimeEfficiency\":"+wellObj[7]+",");
							dataSbf.append("\"CommRange\":\""+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(wellObj[8]))+"\",");
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
								dataSbf.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(StringManagerUtils.CLOBObjectToString(wellObj[12]))+"\",");
							}
						}
						dataSbf.append("\"RPM\":"+resuleObj[2]+",");
						
						dataSbf.append("\"TheoreticalProduction\":"+resuleObj[3]+",");
						
						dataSbf.append("\"LiquidVolumetricProduction\":"+resuleObj[4]+",");
						dataSbf.append("\"OilVolumetricProduction\":"+resuleObj[5]+",");
						dataSbf.append("\"WaterVolumetricProduction\":"+resuleObj[6]+",");
						dataSbf.append("\"VolumeWaterCut\":"+resuleObj[7]+",");
						
						dataSbf.append("\"LiquidWeightProduction\":"+resuleObj[8]+",");
						dataSbf.append("\"OilWeightProduction\":"+resuleObj[9]+",");
						dataSbf.append("\"WaterWeightProduction\":"+resuleObj[10]+",");
						dataSbf.append("\"WeightWaterCut\":"+resuleObj[11]+",");
						
						dataSbf.append("\"SystemEfficiency\":"+resuleObj[12]+",");
						dataSbf.append("\"EnergyPer100mLif\":"+resuleObj[13]+",");
						dataSbf.append("\"AvgWatt\":"+resuleObj[14]+",");
						dataSbf.append("\"WaterPower\":"+resuleObj[15]+",");
						
						dataSbf.append("\"ProducingfluidLevel\":"+resuleObj[16]+",");
						dataSbf.append("\"PumpSettingDepth\":"+resuleObj[17]+",");
						dataSbf.append("\"Submergence\":"+resuleObj[18]+",");
						
						dataSbf.append("\"PumpEff\":"+resuleObj[19]+",");
						dataSbf.append("\"PumpEff1\":"+resuleObj[20]+",");
						dataSbf.append("\"PumpEff2\":"+resuleObj[21]+",");
						
						
						dataSbf.append("\"TubingPressure\":"+resuleObj[22]+",");
						dataSbf.append("\"CasingPressure\":"+resuleObj[23]+",");
						dataSbf.append("\"WellHeadFluidTemperature\":"+resuleObj[24]+",");
						dataSbf.append("\"ProductionGasOilRatio\":"+resuleObj[25]+"},");
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
	
	
//	public List<String> getDiscreteDailyCalculation(String tatalDate,String wellId) throws ParseException{
//		String date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(tatalDate),-1);
//		StringBuffer dataSbf=null;
//		List<String> requestDataList=new ArrayList<String>();
//		String wellinformationSql="select t.wellname,t2.runtime,t.runtimeefficiencysource,t.deviceaddr,t.deviceid "
//				+ " from tbl_wellinformation t "
//				+ " left outer join tbl_rpc_productiondata_latest t2 on t.id=t2.wellid  "
//				+ " where t.liftingType between 200 and 299 ";
//		String singleCalculateResuleSql="select t007.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.workingconditioncode,"
//				+" t.ia,t.ib,t.ic,t.va,t.vb,t.vc,"
//				+" t.frequencyrunvalue,"
//				+ "t.Signal,t.Watt3,t.Var3,t.VA3,t.PF3"
//				+" from tbl_rpc_discrete_hist t ,tbl_wellinformation t007"
//				+" where t.wellid=t007.id "
//				+ " and t.acqTime between to_date('"+tatalDate+"','yyyy-mm-dd')-1 and to_date('"+tatalDate+"','yyyy-mm-dd')";
//		if(StringManagerUtils.isNotNull(wellId)){
//			wellinformationSql+=" and t.id in ("+wellId+")";
//			singleCalculateResuleSql+=" and t007.id in ("+wellId+")";
//		}
//		
//		singleCalculateResuleSql+=" order by t007.sortnum,t.acqTime";
//		wellinformationSql+=" order by t.sortnum";
//		List<?> welllist = findCallSql(wellinformationSql);
//		List<?> singleresultlist = findCallSql(singleCalculateResuleSql);
//
//		for(int i=0;i<welllist.size();i++){
//			try{
//				Object[] wellObj=(Object[]) welllist.get(i);
//				dataSbf = new StringBuffer();
//				dataSbf.append("{\"AKString\":\"\",");
//				dataSbf.append("\"WellName\":\""+wellObj[0]+"\",");
//				dataSbf.append("\"Date\":\""+date+"\",");
//				dataSbf.append("\"EveryTime\": [");
//				String wellRunRime=getWellRuningTime(StringManagerUtils.getOneDayDateString(-1),wellObj[1],null);
//				for(int j=0;j<singleresultlist.size();j++){
//					Object[] resuleObj=(Object[]) singleresultlist.get(j);
//					if(wellObj[0].toString().equals(resuleObj[0].toString())){
//						dataSbf.append("{\"AcqTime\":\""+resuleObj[1]+"\",");
//						dataSbf.append("\"ETResultCode\":"+resuleObj[2]+",");
//						dataSbf.append("\"IA\":"+resuleObj[3]+",");
//						dataSbf.append("\"IB\":"+resuleObj[4]+",");
//						dataSbf.append("\"IC\":"+resuleObj[5]+",");
//						dataSbf.append("\"VA\":"+resuleObj[6]+",");
//						dataSbf.append("\"VB\":"+resuleObj[7]+",");
//						dataSbf.append("\"VC\":"+resuleObj[8]+",");
//						dataSbf.append("\"RunFrequency\":"+resuleObj[9]+",");
//						dataSbf.append("\"Signal\":"+resuleObj[10]+",");
//						dataSbf.append("\"Watt3\":"+resuleObj[11]+",");
//						dataSbf.append("\"Var3\":"+resuleObj[12]+",");
//						dataSbf.append("\"VA3\":"+resuleObj[13]+",");
//						dataSbf.append("\"PF3\":"+StringManagerUtils.stringToFloat(resuleObj[14]+"")+"},");
//					}
//				}
//				if(dataSbf.toString().endsWith(",")){
//					dataSbf.deleteCharAt(dataSbf.length() - 1);
//				}
//				dataSbf.append("]}");
//				requestDataList.add(dataSbf.toString());
//			}catch(Exception e){
//				e.printStackTrace();
//				continue;
//			}
//		}
//		//遍历无井环的井
//		
//		return requestDataList;
//	}
	
	public List<String> getDiscreteDailyCalculation(String tatalDate,String wellId) throws ParseException{
		String date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(tatalDate),-1);
		StringBuffer dataSbf=null;
		List<String> requestDataList=new ArrayList<String>();
		String wellinformationSql="select t.wellname,t2.runtime,t.runtimeefficiencysource,t.deviceaddr,t.deviceid "
				+ " from tbl_wellinformation t "
				+ " left outer join tbl_rpc_productiondata_latest t2 on t.id=t2.wellid  "
				+ " where t.liftingType between 200 and 299 ";
		String singleCalculateResuleSql="select t007.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+" t.ia,t.ib,t.ic,t.va,t.vb,t.vc,"
				+" t.frequencyrunvalue,"
				+ "t.Signal,t.WattSum,t.VarSum,t.VASum,t.PFSum"
				+" from tbl_rpc_discrete_hist t ,tbl_wellinformation t007"
				+" where t.wellid=t007.id "
				+ " and t.acqTime between to_date('"+tatalDate+"','yyyy-mm-dd')-1 and to_date('"+tatalDate+"','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(wellId)){
			wellinformationSql+=" and t.id in ("+wellId+")";
			singleCalculateResuleSql+=" and t007.id in ("+wellId+")";
		}
		
		singleCalculateResuleSql+=" order by t007.sortnum,t.acqTime";
		wellinformationSql+=" order by t.sortnum";
		List<?> welllist = findCallSql(wellinformationSql);
		List<?> singleresultlist = findCallSql(singleCalculateResuleSql);

		for(int i=0;i<welllist.size();i++){
			try{
				Object[] wellObj=(Object[]) welllist.get(i);
				
				List<String> acqTimeList=new ArrayList<String>();
				List<Float> IaList=new ArrayList<Float>();
				List<Float> IbList=new ArrayList<Float>();
				List<Float> IcList=new ArrayList<Float>();
				List<Float> VaList=new ArrayList<Float>();
				List<Float> VbList=new ArrayList<Float>();
				List<Float> VcList=new ArrayList<Float>();
				List<Float> runFrequencyList=new ArrayList<Float>();
				List<Float> signalList=new ArrayList<Float>();
				List<Float> watt3List=new ArrayList<Float>();
				List<Float> var3List=new ArrayList<Float>();
				List<Float> va3List=new ArrayList<Float>();
				List<Float> pf3List=new ArrayList<Float>();
				
				for(int j=0;j<singleresultlist.size();j++){
					Object[] resuleObj=(Object[]) singleresultlist.get(j);
					if(wellObj[0].toString().equals(resuleObj[0].toString())){
						acqTimeList.add(resuleObj[1]+"");
						IaList.add(StringManagerUtils.stringToFloat(resuleObj[2]+""));
						IbList.add(StringManagerUtils.stringToFloat(resuleObj[3]+""));
						IcList.add(StringManagerUtils.stringToFloat(resuleObj[4]+""));
						
						VaList.add(StringManagerUtils.stringToFloat(resuleObj[5]+""));
						VbList.add(StringManagerUtils.stringToFloat(resuleObj[6]+""));
						VcList.add(StringManagerUtils.stringToFloat(resuleObj[7]+""));
						
						runFrequencyList.add(StringManagerUtils.stringToFloat(resuleObj[8]+""));
						signalList.add(StringManagerUtils.stringToFloat(resuleObj[9]+""));
						watt3List.add(StringManagerUtils.stringToFloat(resuleObj[10]+""));
						var3List.add(StringManagerUtils.stringToFloat(resuleObj[11]+""));
						va3List.add(StringManagerUtils.stringToFloat(resuleObj[12]+""));
						pf3List.add(StringManagerUtils.stringToFloat(resuleObj[13]+""));
					}
				}
				
				dataSbf = new StringBuffer();
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+wellObj[0]+"\",");
				dataSbf.append("\"Date\":\""+date+"\",");
				dataSbf.append("\"AcqTime\":["+StringManagerUtils.joinStringArr(acqTimeList, ",")+"],");
				dataSbf.append("\"IA\":["+StringUtils.join(IaList, ",")+"],");
				dataSbf.append("\"IB\":["+StringUtils.join(IbList, ",")+"],");
				dataSbf.append("\"IC\":["+StringUtils.join(IcList, ",")+"],");
				dataSbf.append("\"VA\":["+StringUtils.join(VaList, ",")+"],");
				dataSbf.append("\"VB\":["+StringUtils.join(VbList, ",")+"],");
				dataSbf.append("\"VC\":["+StringUtils.join(VcList, ",")+"],");
				
				dataSbf.append("\"RunFrequency\":["+StringUtils.join(runFrequencyList, ",")+"],");
				dataSbf.append("\"Signal\":["+StringUtils.join(signalList, ",")+"],");
				dataSbf.append("\"Watt3\":["+StringUtils.join(watt3List, ",")+"],");
				dataSbf.append("\"Var3\":["+StringUtils.join(var3List, ",")+"],");
				dataSbf.append("\"VA3\":["+StringUtils.join(va3List, ",")+"],");
				dataSbf.append("\"PF3\":["+StringUtils.join(pf3List, ",")+"]");
				
				dataSbf.append("}");
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
		String date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(tatalDate),-1);
		String wellinformationSql="select t.wellname,t2.runtime,t.runtimeefficiencysource,t.deviceaddr,t.deviceid "
				+ " from tbl_wellinformation t "
				+ " left outer join tbl_pcp_productiondata_latest t2 on t.id=t2.wellid  "
				+ " where t.liftingType between 400 and 499 ";
		String singleCalculateResuleSql="select t007.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+" t.ia,t.ib,t.ic,t.va,t.vb,t.vc,"
				+" t.frequencyrunvalue,"
				+ "t.WattSum,t.VarSum,t.VASum,t.PFSum"
				+" from tbl_pcp_discrete_hist t ,tbl_wellinformation t007"
				+" where t.wellid=t007.id "
				+" and t.acqTime between to_date('2020-01-17','yyyy-mm-dd')-1 and to_date('2020-01-17','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(wellId)){
			wellinformationSql+=" and t.id in ("+wellId+")";
			singleCalculateResuleSql+=" and t007.id in ("+wellId+")";
		}
		
		singleCalculateResuleSql+=" order by t007.sortnum,t.acqTime";
		wellinformationSql+=" order by t.sortnum";
		List<?> welllist = findCallSql(wellinformationSql);
		List<?> singleresultlist = findCallSql(singleCalculateResuleSql);

		for(int i=0;i<welllist.size();i++){
			try{
				Object[] wellObj=(Object[]) welllist.get(i);
				List<String> acqTimeList=new ArrayList<String>();
				List<Float> IaList=new ArrayList<Float>();
				List<Float> IbList=new ArrayList<Float>();
				List<Float> IcList=new ArrayList<Float>();
				List<Float> VaList=new ArrayList<Float>();
				List<Float> VbList=new ArrayList<Float>();
				List<Float> VcList=new ArrayList<Float>();
				List<Float> runFrequencyList=new ArrayList<Float>();
				List<Float> watt3List=new ArrayList<Float>();
				List<Float> var3List=new ArrayList<Float>();
				List<Float> va3List=new ArrayList<Float>();
				List<Float> pf3List=new ArrayList<Float>();
				
				dataSbf = new StringBuffer();
				
				String wellRunRime=getWellRuningTime(StringManagerUtils.getOneDayDateString(-1),wellObj[1],null);
				for(int j=0;j<singleresultlist.size();j++){
					Object[] resuleObj=(Object[]) singleresultlist.get(j);
					if(wellObj[0].toString().equals(resuleObj[0].toString())){
						acqTimeList.add(resuleObj[1]+"");
						IaList.add(StringManagerUtils.stringToFloat(resuleObj[2]+""));
						IbList.add(StringManagerUtils.stringToFloat(resuleObj[3]+""));
						IcList.add(StringManagerUtils.stringToFloat(resuleObj[4]+""));
						
						VaList.add(StringManagerUtils.stringToFloat(resuleObj[5]+""));
						VbList.add(StringManagerUtils.stringToFloat(resuleObj[6]+""));
						VcList.add(StringManagerUtils.stringToFloat(resuleObj[7]+""));
						
						runFrequencyList.add(StringManagerUtils.stringToFloat(resuleObj[8]+""));
						watt3List.add(StringManagerUtils.stringToFloat(resuleObj[9]+""));
						var3List.add(StringManagerUtils.stringToFloat(resuleObj[10]+""));
						va3List.add(StringManagerUtils.stringToFloat(resuleObj[11]+""));
						pf3List.add(StringManagerUtils.stringToFloat(resuleObj[12]+""));
					}
				}
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+wellObj[0]+"\",");
				dataSbf.append("\"Date\":\""+date+"\",");
				dataSbf.append("\"AcqTime\":["+StringManagerUtils.joinStringArr(acqTimeList, ",")+"],");
				dataSbf.append("\"IA\":["+StringUtils.join(IaList, ",")+"],");
				dataSbf.append("\"IB\":["+StringUtils.join(IbList, ",")+"],");
				dataSbf.append("\"IC\":["+StringUtils.join(IcList, ",")+"],");
				dataSbf.append("\"VA\":["+StringUtils.join(VaList, ",")+"],");
				dataSbf.append("\"VB\":["+StringUtils.join(VbList, ",")+"],");
				dataSbf.append("\"VC\":["+StringUtils.join(VcList, ",")+"],");
				
				dataSbf.append("\"RunFrequency\":["+StringUtils.join(runFrequencyList, ",")+"],");
				dataSbf.append("\"Watt3\":["+StringUtils.join(watt3List, ",")+"],");
				dataSbf.append("\"Var3\":["+StringUtils.join(var3List, ",")+"],");
				dataSbf.append("\"VA3\":["+StringUtils.join(va3List, ",")+"],");
				dataSbf.append("\"PF3\":["+StringUtils.join(pf3List, ",")+"]");
				dataSbf.append("}");
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