package com.gao.service.graphicalupload;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.stereotype.Service;

import com.gao.model.DistreteAlarmLimit;
import com.gao.model.WellInformation;
import com.gao.model.calculate.PCPCalculateRequestData;
import com.gao.model.calculate.RPCCalculateRequestData;
import com.gao.model.calculate.RPCCalculateResponseData;
import com.gao.model.calculate.PCPCalculateResponseData;
import com.gao.model.calculate.FSDiagramModel;
import com.gao.model.calculate.WellAcquisitionData;
import com.gao.service.base.BaseService;
import com.gao.tast.KafkaServerTast.KafkaUpData;
import com.gao.tast.KafkaServerTast.KafkaUpRawData;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;

import oracle.sql.CLOB;

/**
 * <p>
 * 功图上传Service
 * </p>
 * 
 * @author zhao 2016-10-31
 * 
 * @param <T>
 */
@Service("graphicalUploadService")
public class GraphicalUploadService<T> extends BaseService<T> {
	public String getSurfaceCardTrpeList(){
		StringBuffer result_json = new StringBuffer();
		String sql="select id,itemname,itemvalue,remark from tbl_code t where t.itemcode='GTLX' order by t.itemvalue ";
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		String columns = "[{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },{ \"header\":\"功图类型\",\"dataIndex\":\"gtlxName\" ,children:[] }]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"gtlxName\":\""+obj[1]+"\",");
			result_json.append("\"gtlx\":"+obj[2]+",");
			result_json.append("\"filetype\":\""+obj[3]+"\"},");
		}
		if(list.size()>0){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
		
	public boolean saveSurfaceCard(FSDiagramModel FSDiagramModel) throws SQLException, ParseException{
		return this.getBaseDao().saveSurfaceCard(FSDiagramModel);
	}
	
	public boolean saveSurfaceCard(WellAcquisitionData wellAcquisitionData) throws SQLException, ParseException{
		return this.getBaseDao().saveSurfaceCard(wellAcquisitionData);
	}
	
	public boolean saveSurfaceCard(String wellName,String cjsjstr,int point,float stroke,float frequency,String SStr,String FStr,String AStr,String PStr) throws SQLException, ParseException{
		return this.getBaseDao().saveSurfaceCard(wellName,cjsjstr,point,stroke,frequency,SStr,FStr,AStr,PStr);
	}
	
	public String getFSdiagramCalculateRequestData(WellAcquisitionData wellAcquisitionData) throws SQLException, IOException, ParseException{
		Gson gson = new Gson();
		RPCCalculateRequestData calculateRequestData=null;
		String requsetdata="";
		try{
			String prodDataSql="select t.id,"
					+ " t.crudeoildensity,t.waterdensity,t.naturalgasrelativedensity,t.saturationpressure,"
					+ " t.reservoirdepth,t.reservoirtemperature,"
					+ " t.rodstring,"
					+ " t.tubingstringinsidediameter,"
					+ " t.pumptype,t.pumpgrade,t.plungerlength,t.pumpborediameter,"
					+ " t.casingstringinsidediameter,"
					+ " t.watercut,t.productiongasoilratio,t.tubingpressure,t.casingpressure,t.wellheadfluidtemperature,t.producingfluidlevel,t.pumpsettingdepth,"
					+ " t.netgrossratio,"
					+ " t3.levelcorrectvalue,"
					+ " t.wellid "
					+ " from tbl_rpc_productiondata_hist t,tbl_rpc_productiondata_latest t2,  tbl_wellinformation t3 "
					+ " where t.wellid=t2.wellid and t.acqTime=t2.acqTime and t.wellid=t3.id"
					+ " and t3.wellName='"+wellAcquisitionData.getWellName()+"'";
			String rpcInformationSql="select t2.manufacturer,t2.model,decode(t2.crankrotationdirection,'顺时针','Clockwise','Anticlockwise'),"
					+ " t2.offsetangleofcrank,t2.crankgravityradius,t2.singlecrankweight,"
					+ " t2.structuralunbalance,"
					+ " t2.balanceposition,t2.balanceweight,t2.prtf"
					+ " from  tbl_wellinformation t,tbl_rpcinformation t2"
					+ " where t.id=t2.wellid"
					+ " and t.wellname='"+wellAcquisitionData.getWellName()+"'";
			String wellboretrajectorySql="select t2.measuringdepth,t2.deviationangle,t2.azimuthangle"
					+ " from  tbl_wellinformation t,tbl_wellboretrajectory t2"
					+ " where t.id=t2.wellid"
					+ " and t.wellname='"+wellAcquisitionData.getWellName()+"'";
			List<?> prodDataList = this.findCallSql(prodDataSql);
			List<?> rpcInformationList = this.findCallSql(rpcInformationSql);
			List<?> wellboretrajectoryList = this.findCallSql(wellboretrajectorySql);
			if(prodDataList.size()>0){
				calculateRequestData=new RPCCalculateRequestData();
				Object[] object=(Object[])prodDataList.get(0);
				wellAcquisitionData.setProdDataId(StringManagerUtils.stringToInteger(object[0]+""));
				wellAcquisitionData.setWellId(StringManagerUtils.stringToInteger(object[object.length-1]+""));
				calculateRequestData.setWellName(wellAcquisitionData.getWellName());
				
				//流体PVT物性
				calculateRequestData.setFluidPVT(new RPCCalculateRequestData.FluidPVT());
				calculateRequestData.getFluidPVT().setCrudeOilDensity(StringManagerUtils.stringToFloat(object[1]+""));
				calculateRequestData.getFluidPVT().setWaterDensity(StringManagerUtils.stringToFloat(object[2]+""));
				calculateRequestData.getFluidPVT().setNaturalGasRelativeDensity(StringManagerUtils.stringToFloat(object[3]+""));
				calculateRequestData.getFluidPVT().setSaturationPressure(StringManagerUtils.stringToFloat(object[4]+""));
				
				//油藏物性
				calculateRequestData.setReservoir(new RPCCalculateRequestData.Reservoir());
				calculateRequestData.getReservoir().setDepth(StringManagerUtils.stringToFloat(object[5]+""));
				calculateRequestData.getReservoir().setTemperature(StringManagerUtils.stringToFloat(object[6]+""));
				
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
				
				//井深轨迹
//				calculateRequestData.setWellboreTrajectory(new RPCCalculateRequestData.WellboreTrajectory());
				
				//抽油杆参数
				calculateRequestData.setRodString(new RPCCalculateRequestData.RodString());
				calculateRequestData.getRodString().setEveryRod(new ArrayList<RPCCalculateRequestData.EveryRod>());
				String rodStringArr[]=(object[7]+"").split(";");
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
				everyTubing.setInsideDiameter(StringManagerUtils.stringToFloat(object[8]+"")/1000);
				calculateRequestData.setTubingString(new RPCCalculateRequestData.TubingString());
				calculateRequestData.getTubingString().setEveryTubing(new ArrayList<RPCCalculateRequestData.EveryTubing>());
				calculateRequestData.getTubingString().getEveryTubing().add(everyTubing);
				
				//抽油泵参数
				calculateRequestData.setPump(new RPCCalculateRequestData.Pump());
				calculateRequestData.getPump().setPumpType(object[9]+"");
				calculateRequestData.getPump().setBarrelType("L");
				calculateRequestData.getPump().setPumpGrade(StringManagerUtils.stringToInteger(object[10]+""));
				calculateRequestData.getPump().setPlungerLength(StringManagerUtils.stringToFloat(object[11]+""));
				calculateRequestData.getPump().setPumpBoreDiameter(StringManagerUtils.stringToFloat(object[12]+"")/1000);
				
				
				//套管数据
				RPCCalculateRequestData.EveryCasing  everyCasing=new RPCCalculateRequestData.EveryCasing();
				everyCasing.setInsideDiameter(StringManagerUtils.stringToFloat(object[13]+"")/1000);
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
					calculateRequestData.getPumpingUnit().setStructuralUnbalance(StringManagerUtils.stringToFloat(rpcObject[6]+""));
					
					String[] BalancePositionArr=(rpcObject[7]+"").split(",");
    				String[] BalanceWeightArr=(rpcObject[8]+"").split(",");
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
				calculateRequestData.getProduction().setWaterCut(StringManagerUtils.stringToFloat(object[14]+""));
				calculateRequestData.getProduction().setProductionGasOilRatio(StringManagerUtils.stringToFloat(object[15]+""));
				calculateRequestData.getProduction().setTubingPressure(StringManagerUtils.stringToFloat(object[16]+""));
				calculateRequestData.getProduction().setCasingPressure(StringManagerUtils.stringToFloat(object[17]+""));
				calculateRequestData.getProduction().setWellHeadFluidTemperature(StringManagerUtils.stringToFloat(object[18]+""));
				calculateRequestData.getProduction().setProducingfluidLevel(StringManagerUtils.stringToFloat(object[19]+""));
				calculateRequestData.getProduction().setLevelCorrectValue(StringManagerUtils.stringToFloat(object[22]+""));
				calculateRequestData.getProduction().setPumpSettingDepth(StringManagerUtils.stringToFloat(object[20]+""));
				
				//功图数据
				if(wellAcquisitionData.getDiagram()!=null){
			        calculateRequestData.setFESDiagram(new RPCCalculateRequestData.FESDiagram());
			        calculateRequestData.getFESDiagram().setSrc(0);
			        calculateRequestData.getFESDiagram().setAcqTime(wellAcquisitionData.getAcqTime());
			        calculateRequestData.getFESDiagram().setStroke(wellAcquisitionData.getDiagram().getStroke());
			        calculateRequestData.getFESDiagram().setSPM(wellAcquisitionData.getDiagram().getSPM());
			        List<Float> F=new ArrayList<Float>();
			        List<Float> S=new ArrayList<Float>();
			        List<Float> Watt=new ArrayList<Float>();
			        List<Float> I=new ArrayList<Float>();
			        for(int i=0;i<wellAcquisitionData.getDiagram().getF().size();i++){
			        	F.add(wellAcquisitionData.getDiagram().getF().get(i));
			        }
			        for(int i=0;i<wellAcquisitionData.getDiagram().getS().size();i++){
			        	S.add(wellAcquisitionData.getDiagram().getS().get(i));
			        }
			        for(int i=0;i<wellAcquisitionData.getDiagram().getP().size();i++){
			        	Watt.add(wellAcquisitionData.getDiagram().getP().get(i));
			        }
			        for(int i=0;i<wellAcquisitionData.getDiagram().getA().size();i++){
			        	I.add(wellAcquisitionData.getDiagram().getA().get(i));
			        }
			        
			        calculateRequestData.getFESDiagram().setF(F);
			        calculateRequestData.getFESDiagram().setS(S);
			        calculateRequestData.getFESDiagram().setWatt(Watt);
			        calculateRequestData.getFESDiagram().setI(I);
				}
		        calculateRequestData.setSystemEfficiency(new RPCCalculateRequestData.SystemEfficiency());
		        
		      //人工干预
		        calculateRequestData.setManualIntervention(new RPCCalculateRequestData.ManualIntervention());
		        calculateRequestData.getManualIntervention().setNetGrossRatio(StringManagerUtils.stringToFloat(object[21]+""));
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		if(calculateRequestData!=null){
			requsetdata=gson.toJson(calculateRequestData);
		}
		return requsetdata;
	}
	
	public String getFSdiagramCalculateRequestData(KafkaUpData kafkaUpData) throws SQLException, IOException, ParseException{
		Gson gson = new Gson();
		RPCCalculateRequestData calculateRequestData=null;
		String requsetdata="";
		try{
			String prodDataSql="select t.id,"
					+ " t.crudeoildensity,t.waterdensity,t.naturalgasrelativedensity,t.saturationpressure,"
					+ " t.reservoirdepth,t.reservoirtemperature,"
					+ " t.rodstring,"
					+ " t.tubingstringinsidediameter,"
					+ " t.pumptype,t.pumpgrade,t.plungerlength,t.pumpborediameter,"
					+ " t.casingstringinsidediameter,"
					+ " t.watercut,t.productiongasoilratio,t.tubingpressure,t.casingpressure,t.wellheadfluidtemperature,t.producingfluidlevel,t.pumpsettingdepth,"
					+ " t.netgrossratio,"
					+ " t3.levelcorrectvalue,"
					+ " t.wellid "
					+ " from tbl_rpc_productiondata_hist t,tbl_rpc_productiondata_latest t2,  tbl_wellinformation t3 "
					+ " where t.wellid=t2.wellid and t.acqTime=t2.acqTime and t.wellid=t3.id"
					+ " and t3.wellName='"+kafkaUpData.getWellName()+"'";
			String rpcInformationSql="select t2.manufacturer,t2.model,decode(t2.crankrotationdirection,'顺时针','Clockwise','Anticlockwise'),"
					+ " t2.offsetangleofcrank,t2.crankgravityradius,t2.singlecrankweight,"
					+ " t2.structuralunbalance,"
					+ " t2.balanceposition,t2.balanceweight,t2.prtf"
					+ " from  tbl_wellinformation t,tbl_rpcinformation t2"
					+ " where t.id=t2.wellid"
					+ " and t.wellname='"+kafkaUpData.getWellName()+"'";
			String wellboretrajectorySql="select t2.measuringdepth,t2.deviationangle,t2.azimuthangle"
					+ " from  tbl_wellinformation t,tbl_wellboretrajectory t2"
					+ " where t.id=t2.wellid"
					+ " and t.wellname='"+kafkaUpData.getWellName()+"'";
			List<?> prodDataList = this.findCallSql(prodDataSql);
			List<?> rpcInformationList = this.findCallSql(rpcInformationSql);
			List<?> wellboretrajectoryList = this.findCallSql(wellboretrajectorySql);
			if(prodDataList.size()>0){
				calculateRequestData=new RPCCalculateRequestData();
				Object[] object=(Object[])prodDataList.get(0);
				kafkaUpData.setProdDataId(StringManagerUtils.stringToInteger(object[0]+""));
				kafkaUpData.setWellId(StringManagerUtils.stringToInteger(object[object.length-1]+""));
				calculateRequestData.setWellName(kafkaUpData.getWellName());
				
				//流体PVT物性
				calculateRequestData.setFluidPVT(new RPCCalculateRequestData.FluidPVT());
				calculateRequestData.getFluidPVT().setCrudeOilDensity(StringManagerUtils.stringToFloat(object[1]+""));
				calculateRequestData.getFluidPVT().setWaterDensity(StringManagerUtils.stringToFloat(object[2]+""));
				calculateRequestData.getFluidPVT().setNaturalGasRelativeDensity(StringManagerUtils.stringToFloat(object[3]+""));
				calculateRequestData.getFluidPVT().setSaturationPressure(StringManagerUtils.stringToFloat(object[4]+""));
				
				//油藏物性
				calculateRequestData.setReservoir(new RPCCalculateRequestData.Reservoir());
				calculateRequestData.getReservoir().setDepth(StringManagerUtils.stringToFloat(object[5]+""));
				calculateRequestData.getReservoir().setTemperature(StringManagerUtils.stringToFloat(object[6]+""));
				
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
				
				//井深轨迹
//				calculateRequestData.setWellboreTrajectory(new RPCCalculateRequestData.WellboreTrajectory());
				
				//抽油杆参数
				calculateRequestData.setRodString(new RPCCalculateRequestData.RodString());
				calculateRequestData.getRodString().setEveryRod(new ArrayList<RPCCalculateRequestData.EveryRod>());
				String rodStringArr[]=(object[7]+"").split(";");
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
				everyTubing.setInsideDiameter(StringManagerUtils.stringToFloat(object[8]+"")/1000);
				calculateRequestData.setTubingString(new RPCCalculateRequestData.TubingString());
				calculateRequestData.getTubingString().setEveryTubing(new ArrayList<RPCCalculateRequestData.EveryTubing>());
				calculateRequestData.getTubingString().getEveryTubing().add(everyTubing);
				
				//抽油泵参数
				calculateRequestData.setPump(new RPCCalculateRequestData.Pump());
				calculateRequestData.getPump().setPumpType(object[9]+"");
				calculateRequestData.getPump().setBarrelType("L");
				calculateRequestData.getPump().setPumpGrade(StringManagerUtils.stringToInteger(object[10]+""));
				calculateRequestData.getPump().setPlungerLength(StringManagerUtils.stringToFloat(object[11]+""));
				calculateRequestData.getPump().setPumpBoreDiameter(StringManagerUtils.stringToFloat(object[12]+"")/1000);
				
				
				//套管数据
				RPCCalculateRequestData.EveryCasing  everyCasing=new RPCCalculateRequestData.EveryCasing();
				everyCasing.setInsideDiameter(StringManagerUtils.stringToFloat(object[13]+"")/1000);
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
					calculateRequestData.getPumpingUnit().setStructuralUnbalance(StringManagerUtils.stringToFloat(rpcObject[6]+""));
					
					String[] BalancePositionArr=(rpcObject[7]+"").split(",");
    				String[] BalanceWeightArr=(rpcObject[8]+"").split(",");
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
				calculateRequestData.getProduction().setWaterCut(StringManagerUtils.stringToFloat(object[14]+""));
				calculateRequestData.getProduction().setProductionGasOilRatio(StringManagerUtils.stringToFloat(object[15]+""));
				calculateRequestData.getProduction().setTubingPressure(StringManagerUtils.stringToFloat(object[16]+""));
				calculateRequestData.getProduction().setCasingPressure(StringManagerUtils.stringToFloat(object[17]+""));
				calculateRequestData.getProduction().setWellHeadFluidTemperature(StringManagerUtils.stringToFloat(object[18]+""));
				calculateRequestData.getProduction().setProducingfluidLevel(StringManagerUtils.stringToFloat(object[19]+""));
				calculateRequestData.getProduction().setLevelCorrectValue(StringManagerUtils.stringToFloat(object[22]+""));
				calculateRequestData.getProduction().setPumpSettingDepth(StringManagerUtils.stringToFloat(object[20]+""));
				
				//功图数据
				if(kafkaUpData.getS()!=null&&kafkaUpData.getS().size()>0){
			        calculateRequestData.setFESDiagram(new RPCCalculateRequestData.FESDiagram());
			        calculateRequestData.getFESDiagram().setSrc(0);
			        calculateRequestData.getFESDiagram().setAcqTime(kafkaUpData.getAcqTime());
			        calculateRequestData.getFESDiagram().setStroke(kafkaUpData.getStroke());
			        calculateRequestData.getFESDiagram().setSPM(kafkaUpData.getSPM());
			        List<Float> F=new ArrayList<Float>();
			        List<Float> S=new ArrayList<Float>();
			        List<Float> Watt=new ArrayList<Float>();
			        List<Float> I=new ArrayList<Float>();
			        if(kafkaUpData.getF()!=null&&kafkaUpData.getF().size()>0){
			        	for(int i=0;i<kafkaUpData.getF().size();i++){
				        	F.add(kafkaUpData.getF().get(i));
				        }
			        }
			        if(kafkaUpData.getS()!=null&&kafkaUpData.getS().size()>0){
			        	for(int i=0;i<kafkaUpData.getS().size();i++){
				        	S.add(kafkaUpData.getS().get(i));
				        }
			        }
			        if(kafkaUpData.getWatt()!=null&&kafkaUpData.getWatt().size()>0){
			        	for(int i=0;i<kafkaUpData.getWatt().size();i++){
				        	Watt.add(kafkaUpData.getWatt().get(i));
				        }
			        }
			        if(kafkaUpData.getI()!=null&&kafkaUpData.getI().size()>0){
			        	for(int i=0;i<kafkaUpData.getI().size();i++){
				        	I.add(kafkaUpData.getI().get(i));
				        }
			        }
			        
			        calculateRequestData.getFESDiagram().setF(F);
			        calculateRequestData.getFESDiagram().setS(S);
			        calculateRequestData.getFESDiagram().setWatt(Watt);
			        calculateRequestData.getFESDiagram().setI(I);
				}
		        calculateRequestData.setSystemEfficiency(new RPCCalculateRequestData.SystemEfficiency());
		        
		      //人工干预
		        calculateRequestData.setManualIntervention(new RPCCalculateRequestData.ManualIntervention());
		        calculateRequestData.getManualIntervention().setNetGrossRatio(StringManagerUtils.stringToFloat(object[21]+""));
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		if(calculateRequestData!=null){
			requsetdata=gson.toJson(calculateRequestData);
		}
		return requsetdata;
	}
	
	public String getScrewPumpRPMCalculateRequestData(WellAcquisitionData wellAcquisitionData) throws SQLException, IOException, ParseException{
		Gson gson = new Gson();
		PCPCalculateRequestData calculateRequestData=null;
		String requsetdata="";
		try{
			String prodDataSql="select t.id,"
					+ " t.crudeoildensity,t.waterdensity,t.naturalgasrelativedensity,t.saturationpressure,"
					+ " t.reservoirdepth,t.reservoirtemperature,"
					+ " t.rodstring,"
					+ " t.tubingstringinsidediameter,"
					+ " t.barrellength,t.barrelseries,t.rotordiameter,t.qpr,"
					+ " t.casingstringinsidediameter,"
					+ " t.watercut,t.productiongasoilratio,t.tubingpressure,t.casingpressure,t.wellheadfluidtemperature,t.producingfluidlevel,t.pumpsettingdepth,"
					+ " t.netgrossratio,"
					+ " t.wellid "
					+ " from tbl_pcp_productiondata_hist t,tbl_pcp_productiondata_latest t2,  tbl_wellinformation t3 "
					+ " where t.wellid=t2.wellid and t.acqTime=t2.acqTime and t.wellid=t3.id"
					+ " and t3.wellName='"+wellAcquisitionData.getWellName()+"'";
			List<?> prodDataList = this.findCallSql(prodDataSql);
			if(prodDataList.size()>0){
				calculateRequestData=new PCPCalculateRequestData();
				Object[] object=(Object[])prodDataList.get(0);
				wellAcquisitionData.setProdDataId(StringManagerUtils.stringToInteger(object[0]+""));
				wellAcquisitionData.setWellId(StringManagerUtils.stringToInteger(object[object.length-1]+""));
				calculateRequestData.setAKString("");
				calculateRequestData.setWellName(wellAcquisitionData.getWellName());
				calculateRequestData.setAcqTime(wellAcquisitionData.getAcqTime());
				
				//转速
				calculateRequestData.setRPM(wellAcquisitionData.getScrewPump().getRPM());
				
				//流体PVT物性
				calculateRequestData.setFluidPVT(new PCPCalculateRequestData.FluidPVT());
				calculateRequestData.getFluidPVT().setCrudeOilDensity(StringManagerUtils.stringToFloat(object[1]+""));
				calculateRequestData.getFluidPVT().setWaterDensity(StringManagerUtils.stringToFloat(object[2]+""));
				calculateRequestData.getFluidPVT().setNaturalGasRelativeDensity(StringManagerUtils.stringToFloat(object[3]+""));
				calculateRequestData.getFluidPVT().setSaturationPressure(StringManagerUtils.stringToFloat(object[4]+""));
				
				//油藏物性
				calculateRequestData.setReservoir(new PCPCalculateRequestData.Reservoir());
				calculateRequestData.getReservoir().setDepth(StringManagerUtils.stringToFloat(object[5]+""));
				calculateRequestData.getReservoir().setTemperature(StringManagerUtils.stringToFloat(object[6]+""));
				
				//井深轨迹
				calculateRequestData.setWellboreTrajectory(new PCPCalculateRequestData.WellboreTrajectory());
				
				//抽油杆参数
				calculateRequestData.setRodString(new PCPCalculateRequestData.RodString());
				calculateRequestData.getRodString().setEveryRod(new ArrayList<PCPCalculateRequestData.EveryRod>());
				String rodStringArr[]=(object[7]+"").split(";");
				for(int i=0;i<rodStringArr.length;i++){
					if(StringManagerUtils.isNotNull(rodStringArr[i])){
						String everyRodStringArr[]=rodStringArr[i].split(",");
						if(everyRodStringArr.length==4&&StringManagerUtils.stringToFloat(everyRodStringArr[3]+"")>0){
							String rodGrade=everyRodStringArr[0];
							String rodOutsideDiameter=everyRodStringArr[1];
							String rodInsideDiameter=everyRodStringArr[2];
							String rodLength=everyRodStringArr[3];
							PCPCalculateRequestData.EveryRod everyRod=new PCPCalculateRequestData.EveryRod();
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
				PCPCalculateRequestData.EveryTubing everyTubing=new PCPCalculateRequestData.EveryTubing();
				everyTubing.setInsideDiameter(StringManagerUtils.stringToFloat(object[8]+"")/1000);
				calculateRequestData.setTubingString(new PCPCalculateRequestData.TubingString());
				calculateRequestData.getTubingString().setEveryTubing(new ArrayList<PCPCalculateRequestData.EveryTubing>());
				calculateRequestData.getTubingString().getEveryTubing().add(everyTubing);
				
				//抽油泵参数
				calculateRequestData.setPump(new PCPCalculateRequestData.Pump());
				calculateRequestData.getPump().setBarrelLength(StringManagerUtils.stringToFloat(object[9]+""));
				calculateRequestData.getPump().setBarrelSeries((int)(StringManagerUtils.stringToFloat(object[10]+"")+0.5));
				calculateRequestData.getPump().setRotorDiameter(StringManagerUtils.stringToFloat(object[11]+"")/1000);
				calculateRequestData.getPump().setQPR(StringManagerUtils.stringToFloat(object[12]+"")/1000000);
				
				
				//套管数据
				PCPCalculateRequestData.EveryCasing  everyCasing=new PCPCalculateRequestData.EveryCasing();
				everyCasing.setInsideDiameter(StringManagerUtils.stringToFloat(object[13]+"")/1000);
				calculateRequestData.setCasingString(new PCPCalculateRequestData.CasingString());
				calculateRequestData.getCasingString().setEveryCasing(new ArrayList<PCPCalculateRequestData.EveryCasing>());
				calculateRequestData.getCasingString().getEveryCasing().add(everyCasing);
				
				//生产数据
				calculateRequestData.setProduction(new PCPCalculateRequestData.Production());
				calculateRequestData.getProduction().setWaterCut(StringManagerUtils.stringToFloat(object[14]+""));
				calculateRequestData.getProduction().setProductionGasOilRatio(StringManagerUtils.stringToFloat(object[15]+""));
				calculateRequestData.getProduction().setTubingPressure(StringManagerUtils.stringToFloat(object[16]+""));
				calculateRequestData.getProduction().setCasingPressure(StringManagerUtils.stringToFloat(object[17]+""));
				calculateRequestData.getProduction().setWellHeadFluidTemperature(StringManagerUtils.stringToFloat(object[18]+""));
				calculateRequestData.getProduction().setProducingfluidLevel(StringManagerUtils.stringToFloat(object[19]+""));
				calculateRequestData.getProduction().setPumpSettingDepth(StringManagerUtils.stringToFloat(object[20]+""));
				calculateRequestData.getProduction().setSubmergence(StringManagerUtils.stringToFloat(object[20]+"")-StringManagerUtils.stringToFloat(object[19]+""));
				
				//系统效率
		        calculateRequestData.setSystemEfficiency(new PCPCalculateRequestData.SystemEfficiency());
		        
		      //人工干预
		        calculateRequestData.setManualIntervention(new PCPCalculateRequestData.ManualIntervention());
		        calculateRequestData.getManualIntervention().setNetGrossRatio(StringManagerUtils.stringToFloat(object[21]+""));
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		if(calculateRequestData!=null){
			requsetdata=gson.toJson(calculateRequestData);
		}
		return requsetdata;
	}
	
	
	public boolean savePCPAcquisitionAndCalculateData(WellAcquisitionData wellAcquisitionData,PCPCalculateResponseData calculateResponseData) throws SQLException, ParseException{
		boolean result=false;
		result=this.getBaseDao().saveScrewPumpRPMAndCalculateData(wellAcquisitionData,calculateResponseData);
		return result;
	}
	
	public boolean saveRPCAcquisitionAndCalculateData(WellAcquisitionData wellAcquisitionData,RPCCalculateResponseData calculateResponseData) throws SQLException, ParseException{
		boolean result=false;
		result=this.getBaseDao().saveFSDiagramAndCalculateData(wellAcquisitionData,calculateResponseData);
		return result;
	}
	public boolean saveRPCAcquisitionAndCalculateData(KafkaUpData kafkaUpData,RPCCalculateResponseData calculateResponseData) throws SQLException, ParseException{
		boolean result=false;
		result=this.getBaseDao().saveFSDiagramAndCalculateData(kafkaUpData,calculateResponseData);
		return result;
	}
	public boolean saveKafkaUpRawData(KafkaUpRawData kafkaUpRawData) throws SQLException, ParseException{
		boolean result=false;
		result=this.getBaseDao().saveKafkaUpRawData(kafkaUpRawData);
		return result;
	}
}
