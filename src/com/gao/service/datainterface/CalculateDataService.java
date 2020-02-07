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

import com.gao.model.calculate.CalculateRequestData;
import com.gao.model.calculate.CalculateResponseData;
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

	public String getCalculateRequestData(String wellId,String FSDiagramId) throws SQLException, IOException, ParseException{
		Gson gson = new Gson();
		CalculateRequestData calculateRequestData=new CalculateRequestData();
		String sql="select t007.jh,"
				+ "t001.yymd,t001.smd,t001.trqxdmd,t001.bhyl,"
				+ "t001.yqcyl,t001.yqczbsd,t001.yqczbwd,"
				+ "decode(t008.yjgjb,1,'C',2,'D',3,'K',4,'H'),t008.yjgcd,t008.yjgj,decode(t008.ejgjb,1,'C',2,'D',3,'K',4,'H'),t008.ejgcd,t008.ejgj,decode(t008.sjgjb,1,'C',2,'D',3,'K',4,'H'),t008.sjgcd,t008.sjgj," //17
				+ "t008.ygnj,"
				+ "decode(t023.blx,1,'T',2,'R'),t023.bjb,t023.zsc,t023.bj,"
				+ "t008.yctgnj," //23
				+ "t008.hsld,t008.scqyb,t008.yy,t008.ty,t008.jklw,t011.dym,t008.bg,t008.jmb, " //31
				+ "t010.gtsj,"
				+ "decode(t008.gkjgly,0,0,t008.rggygklx) "
				+ "from t_wellinformation t007,t_outputwellproduction t008, t_indicatordiagram t010,"
				+ "t_reservoirproperty t001, t_pump t023,t_dynamicliquidlevel t011 "
				+ "where t007.jlbh=t008.jbh and t007.jlbh=t010.jbh and t007.yqcbh=t001.yqcbh and t008.bbh=t023.jlbh and t008.dymbh=t011.jlbh "
				+ "and t007.jlbh=36 and t010.jlbh=2571";
		List<?> list = this.findCallSql(sql);
		if(list.size()>0){
			Object[] object=(Object[]) list.get(0);
			calculateRequestData.setAKString("");
			calculateRequestData.setWellName(object[0]+"");
			
			//流体PVT物性
			calculateRequestData.setFluidPVT(new CalculateRequestData.FluidPVT());
			calculateRequestData.getFluidPVT().setCrudeOilDensity(StringManagerUtils.StringToFloat(object[1]+""));
			calculateRequestData.getFluidPVT().setWaterDensity(StringManagerUtils.StringToFloat(object[2]+""));
			calculateRequestData.getFluidPVT().setNaturalGasRelativeDensity(StringManagerUtils.StringToFloat(object[3]+""));
			calculateRequestData.getFluidPVT().setSaturationPressure(StringManagerUtils.StringToFloat(object[4]+""));
			
			//油藏物性
			calculateRequestData.setReservoir(new CalculateRequestData.Reservoir());
			calculateRequestData.getReservoir().setPressure(StringManagerUtils.StringToFloat(object[5]+""));
			calculateRequestData.getReservoir().setDepth(StringManagerUtils.StringToFloat(object[6]+""));
			calculateRequestData.getReservoir().setTemperature(StringManagerUtils.StringToFloat(object[7]+""));
			
			//井深轨迹
			calculateRequestData.setWellboreTrajectory(new CalculateRequestData.WellboreTrajectory());
			calculateRequestData.getWellboreTrajectory().setMeasuringDepth(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().setVerticalDepth(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().setDeviationAngle(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().setAzimuthAngle(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().getMeasuringDepth().add((float) 100);
			calculateRequestData.getWellboreTrajectory().getMeasuringDepth().add((float) 200);
			calculateRequestData.getWellboreTrajectory().getVerticalDepth().add((float) 100);
			calculateRequestData.getWellboreTrajectory().getVerticalDepth().add((float) 200);
			calculateRequestData.getWellboreTrajectory().getDeviationAngle().add((float) 0);
			calculateRequestData.getWellboreTrajectory().getDeviationAngle().add((float) 0);
			calculateRequestData.getWellboreTrajectory().getAzimuthAngle().add((float) 0);
			calculateRequestData.getWellboreTrajectory().getAzimuthAngle().add((float) 0);
			
			//抽油杆参数
			CalculateRequestData.EveryRod everyRod1=new CalculateRequestData.EveryRod();
			everyRod1.setType(1);
			
			everyRod1.setGrade(object[8]+"");
			everyRod1.setLength(StringManagerUtils.StringToFloat(object[9]+""));
			everyRod1.setOutsideDiameter(StringManagerUtils.StringToFloat(object[10]+"")/1000);
			
			CalculateRequestData.EveryRod everyRod2=new CalculateRequestData.EveryRod();
			everyRod2.setType(1);
			everyRod2.setGrade(object[11]+"");
			everyRod2.setLength(StringManagerUtils.StringToFloat(object[12]+""));
			everyRod2.setOutsideDiameter(StringManagerUtils.StringToFloat(object[13]+"")/1000);
			
			CalculateRequestData.EveryRod everyRod3=new CalculateRequestData.EveryRod();
			everyRod3.setType(1);
			everyRod3.setGrade(object[14]+"");
			everyRod3.setLength(StringManagerUtils.StringToFloat(object[15]+""));
			everyRod3.setOutsideDiameter(StringManagerUtils.StringToFloat(object[16]+"")/1000);
			
			calculateRequestData.setRodString(new CalculateRequestData.RodString());
			calculateRequestData.getRodString().setEveryRod(new ArrayList<CalculateRequestData.EveryRod>());
			if(everyRod1.getLength()>0){
				calculateRequestData.getRodString().getEveryRod().add(everyRod1);
			}
			if(everyRod2.getLength()>0){
				calculateRequestData.getRodString().getEveryRod().add(everyRod2);
			}
			if(everyRod3.getLength()>0){
				calculateRequestData.getRodString().getEveryRod().add(everyRod3);
			}
			
			//油管参数
			CalculateRequestData.EveryTubing everyTubing=new CalculateRequestData.EveryTubing();
			everyTubing.setInsideDiameter(StringManagerUtils.StringToFloat(object[17]+"")/1000);
			calculateRequestData.setTubingString(new CalculateRequestData.TubingString());
			calculateRequestData.getTubingString().setEveryTubing(new ArrayList<CalculateRequestData.EveryTubing>());
			calculateRequestData.getTubingString().getEveryTubing().add(everyTubing);
			
			//抽油泵参数
			calculateRequestData.setPump(new CalculateRequestData.Pump());
			calculateRequestData.getPump().setPumpType(object[18]+"");
			calculateRequestData.getPump().setBarrelType("L");
			calculateRequestData.getPump().setPumpGrade(StringManagerUtils.StringToInteger(object[19]+""));
			calculateRequestData.getPump().setPlungerLength(StringManagerUtils.StringToFloat(object[20]+""));
			calculateRequestData.getPump().setPumpBoreDiameter(StringManagerUtils.StringToFloat(object[21]+"")/1000);
			
			//套管数据
			CalculateRequestData.EveryCasing  everyCasing=new CalculateRequestData.EveryCasing();
			everyCasing.setInsideDiameter(StringManagerUtils.StringToFloat(object[22]+"")/1000);
			everyCasing.setLength(StringManagerUtils.StringToFloat(object[29]+""));
			calculateRequestData.setCasingString(new CalculateRequestData.CasingString());
			calculateRequestData.getCasingString().setEveryCasing(new ArrayList<CalculateRequestData.EveryCasing>());
			calculateRequestData.getCasingString().getEveryCasing().add(everyCasing);
			
			//生产数据
			calculateRequestData.setProductionParameter(new CalculateRequestData.ProductionParameter());
			calculateRequestData.getProductionParameter().setWaterCut(StringManagerUtils.StringToFloat(object[23]+""));
			calculateRequestData.getProductionParameter().setProductionGasOilRatio(StringManagerUtils.StringToFloat(object[24]+""));
			calculateRequestData.getProductionParameter().setTubingPressure(StringManagerUtils.StringToFloat(object[25]+""));
			calculateRequestData.getProductionParameter().setCasingPressure(StringManagerUtils.StringToFloat(object[26]+""));
			calculateRequestData.getProductionParameter().setWellHeadFluidTemperature(StringManagerUtils.StringToFloat(object[27]+""));
			calculateRequestData.getProductionParameter().setProducingfluidLevel(StringManagerUtils.StringToFloat(object[28]+""));
			calculateRequestData.getProductionParameter().setPumpSettingDepth(StringManagerUtils.StringToFloat(object[29]+""));
			calculateRequestData.getProductionParameter().setSubmergence(StringManagerUtils.StringToFloat(object[29]+"")-StringManagerUtils.StringToFloat(object[28]+""));
			//calculateRequestData.getProductionParameter().setOutputCoefficient(StringManagerUtils.StringToFloat(object[30]+""));
			
			//功图数据
			SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[31]);
			CLOB realClob = (CLOB) proxy.getWrappedClob(); 
			String gtsj=StringManagerUtils.CLOBtoString(realClob);
			
//			SerializableBlobProxy proxy = (SerializableBlobProxy )Proxy.getInvocationHandler(object[31]);
//			BLOB blob = (BLOB) proxy.getWrappedBlob(); 
//			BufferedInputStream bis = new BufferedInputStream(blob.getBinaryStream());  
//			byte[] bytes = new byte[(int)blob.length()];  
//		    int len = bytes.length;  
//		    int offest = 0;  
//		    int read = 0;  
//		    while(offest<len&&(read=bis.read(bytes, offest, len-offest))>0){  
//		        offest+=read;  
//		    } 
//	        String gtsj=new String(bytes);
	        String arrgtsj[]=gtsj.replaceAll("\r\n", "\n").split("\n");
	        String cjsj=arrgtsj[0]+" "+arrgtsj[1];
	        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HHmmss");
	        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = df.parse(cjsj);
	        String testtime=df2.format(date);
	        calculateRequestData.setFSDiagram(new CalculateRequestData.FSDiagram());
	        calculateRequestData.getFSDiagram().setAcquisitionTime(testtime);
	        calculateRequestData.getFSDiagram().setStroke(StringManagerUtils.StringToFloat(arrgtsj[4]));
	        calculateRequestData.getFSDiagram().setSPM(StringManagerUtils.StringToFloat(arrgtsj[3]));
	        List<List<Float>> F=new ArrayList<List<Float>>();
	        List<List<Float>> S=new ArrayList<List<Float>>();
	        for(int i=0;i<StringManagerUtils.StringToInteger(arrgtsj[2]);i++){
	        	List<Float> FList=new ArrayList<Float>();
				List<Float> SList=new ArrayList<Float>();
	        	SList.add(StringManagerUtils.StringToFloat(arrgtsj[i*2+5]));
	        	FList.add(StringManagerUtils.StringToFloat(arrgtsj[i*2+6]));
	        	S.add(SList);
		        F.add(FList);
	        }
	        calculateRequestData.getFSDiagram().setF(F);
	        calculateRequestData.getFSDiagram().setS(S);
	        
	        //人工干预
	        calculateRequestData.setManualIntervention(new CalculateRequestData.ManualIntervention());
	        calculateRequestData.getManualIntervention().setCode(StringManagerUtils.StringToInteger(object[32]+""));
	        calculateRequestData.getManualIntervention().setNetGrossRatio(StringManagerUtils.StringToFloat(object[30]+""));
	        //calculateRequestData.getFSDiagram().getPolishedRodPowerDiagramAnalysis().setManualInterventionStatus(StringManagerUtils.StringToInteger(object[32]+"")==1?true:false);
	        //calculateRequestData.getFSDiagram().getPolishedRodPowerDiagramAnalysis().setManualInterventionCode(StringManagerUtils.StringToInteger(object[33]+""));
	        
	        //诊断分析参数
//	        calculateRequestData.getFSDiagram().setAnalysis(new CalculateRequestData.Analysis());
//	        calculateRequestData.getFSDiagram().getAnalysis().setAnalysisParameter(new CalculateRequestData.AnalysisParameter());
//	        
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setClosedGraphNumber(30);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setBandCoefficient(0.3f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setApproximationCoefficient(0.9f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setMiddleWidthRatio(0.1f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setFlowingWellFluidLevel(10);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setNarrowCoefficient(0.4f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setBlockageCoefficient(1.5f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setClosedAreaRatio1(0.005f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setClosedAreaRatio2(3);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setUpperLowerCoefficient(0.5f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setSandParameter1(50);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setSandParameter2(100);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setTopLeakCoefficient(0.7f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setBottomLeakCoefficient(0.7f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setTopLeftK(1);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setTopRightK(1);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setBottomLeftK(1);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setBottomRightK(1);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setDeflectionDegree(15);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setWaxCoefficient1(2.5f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setWaxCoefficient2(3.5f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setFullnessCoefficient1(0.01f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setFullnessCoefficient2(0.1f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setFullnessCoefficient3(0.3f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setFullnessCoefficient4(0.6f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setNormalSubmergence(200);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setPumpOffSubmergence(20);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setIntakeBlockingSubmergence(200);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setProlapseCoefficient(0.7f);
//	        calculateRequestData.getFSDiagram().getAnalysis().getAnalysisParameter().setCheckRangeCoefficient(0.3f);
		}
		String requsetdata=gson.toJson(calculateRequestData);
		return requsetdata;
	}
	
	public String getCalculateRequestDataTest(String FSDiagramId) throws SQLException, IOException, ParseException{
		Gson gson = new Gson();
		CalculateRequestData calculateRequestData=new CalculateRequestData();
		String sql="select t007.jh,"
				+ " t033.yymd,t033.smd,t033.trqxdmd,t033.bhyl,t033.yqcyl,t033.yqczbsd,t033.yqczbwd,"
				+ " decode(t008.yjgjb,1,'C',2,'D',3,'K',4,'H'),t008.yjgcd,t008.yjgj,decode(t008.ejgjb,1,'C',2,'D',3,'K',4,'H'),t008.ejgcd,t008.ejgj,decode(t008.sjgjb,1,'C',2,'D',3,'K',4,'H'),t008.sjgcd,t008.sjgj,t008.ygnj,"
				+ " decode(t023.blx,1,'T',2,'R'),t023.bjb,t023.zsc,t023.bj,"
				+ " t008.yctgnj,"
				+ " t033.hsl,t033.scqyb,t033.yy,t033.ty,t033.jklw,t011.dym,t033.bg,t033.jmb,"
				+ " t010.gtsj,decode(t008.gkjgly,0,0,t008.rggygklx) "
				+ " from t_wellinformation t007,t_outputwellproduction t008,t_indicatordiagram t010,"
				+ " t_outputwellhistory t033,t_pump t023,t_dynamicliquidlevel t011 "
				+ " where t007.jlbh=t008.jbh and t007.jlbh=t010.jbh"
				+ " and t007.jlbh=t033.jbh and t033.bbh=t023.jlbh"
				+ " and t033.dymbh=t011.jlbh and t033.gtbh=t010.jlbh"
				+ " and t033.jlbh="+FSDiagramId;
		List<?> list = this.findCallSql(sql);
		if(list.size()>0){
			calculateRequestData.setAKString("");
			Object[] object=(Object[]) list.get(0);
			calculateRequestData.setWellName(object[0]+"");
			
			//流体PVT物性
			calculateRequestData.setFluidPVT(new CalculateRequestData.FluidPVT());
			calculateRequestData.getFluidPVT().setCrudeOilDensity(StringManagerUtils.StringToFloat(object[1]+""));
			calculateRequestData.getFluidPVT().setWaterDensity(StringManagerUtils.StringToFloat(object[2]+""));
			calculateRequestData.getFluidPVT().setNaturalGasRelativeDensity(StringManagerUtils.StringToFloat(object[3]+""));
			calculateRequestData.getFluidPVT().setSaturationPressure(StringManagerUtils.StringToFloat(object[4]+""));
			
			//油藏物性
			calculateRequestData.setReservoir(new CalculateRequestData.Reservoir());
			calculateRequestData.getReservoir().setPressure(StringManagerUtils.StringToFloat(object[5]+""));
			calculateRequestData.getReservoir().setDepth(StringManagerUtils.StringToFloat(object[6]+""));
			calculateRequestData.getReservoir().setTemperature(StringManagerUtils.StringToFloat(object[7]+""));
			
			//井深轨迹
			calculateRequestData.setWellboreTrajectory(new CalculateRequestData.WellboreTrajectory());
			calculateRequestData.getWellboreTrajectory().setMeasuringDepth(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().setVerticalDepth(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().setDeviationAngle(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().setAzimuthAngle(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().getMeasuringDepth().add((float) 100);
			calculateRequestData.getWellboreTrajectory().getMeasuringDepth().add((float) 200);
			calculateRequestData.getWellboreTrajectory().getVerticalDepth().add((float) 100);
			calculateRequestData.getWellboreTrajectory().getVerticalDepth().add((float) 200);
			calculateRequestData.getWellboreTrajectory().getDeviationAngle().add((float) 0);
			calculateRequestData.getWellboreTrajectory().getDeviationAngle().add((float) 0);
			calculateRequestData.getWellboreTrajectory().getAzimuthAngle().add((float) 0);
			calculateRequestData.getWellboreTrajectory().getAzimuthAngle().add((float) 0);
			
			//抽油杆参数
			CalculateRequestData.EveryRod everyRod1=new CalculateRequestData.EveryRod();
			everyRod1.setType(1);
			everyRod1.setGrade(object[8]+"");
			everyRod1.setLength(StringManagerUtils.StringToFloat(object[9]+""));
			everyRod1.setOutsideDiameter(StringManagerUtils.StringToFloat(object[10]+"")/1000);
			
			CalculateRequestData.EveryRod everyRod2=new CalculateRequestData.EveryRod();
			everyRod2.setType(1);
			everyRod2.setGrade(object[11]+"");
			everyRod2.setLength(StringManagerUtils.StringToFloat(object[12]+""));
			everyRod2.setOutsideDiameter(StringManagerUtils.StringToFloat(object[13]+"")/1000);
			
			CalculateRequestData.EveryRod everyRod3=new CalculateRequestData.EveryRod();
			everyRod3.setType(1);
			everyRod3.setGrade(object[14]+"");
			everyRod3.setLength(StringManagerUtils.StringToFloat(object[15]+""));
			everyRod3.setOutsideDiameter(StringManagerUtils.StringToFloat(object[16]+"")/1000);
			
			calculateRequestData.setRodString(new CalculateRequestData.RodString());
			calculateRequestData.getRodString().setEveryRod(new ArrayList<CalculateRequestData.EveryRod>());
			if(everyRod1.getLength()>0){
				calculateRequestData.getRodString().getEveryRod().add(everyRod1);
			}
			if(everyRod2.getLength()>0){
				calculateRequestData.getRodString().getEveryRod().add(everyRod2);
			}
			if(everyRod3.getLength()>0){
				calculateRequestData.getRodString().getEveryRod().add(everyRod3);
			}
			
			//油管参数
			CalculateRequestData.EveryTubing everyTubing=new CalculateRequestData.EveryTubing();
			everyTubing.setInsideDiameter(StringManagerUtils.StringToFloat(object[17]+"")/1000);
			calculateRequestData.setTubingString(new CalculateRequestData.TubingString());
			calculateRequestData.getTubingString().setEveryTubing(new ArrayList<CalculateRequestData.EveryTubing>());
			calculateRequestData.getTubingString().getEveryTubing().add(everyTubing);
			
			//抽油泵参数
			calculateRequestData.setPump(new CalculateRequestData.Pump());
			calculateRequestData.getPump().setPumpType(object[18]+"");
			calculateRequestData.getPump().setBarrelType("L");
			calculateRequestData.getPump().setPumpGrade(StringManagerUtils.StringToInteger(object[19]+""));
			calculateRequestData.getPump().setPlungerLength(StringManagerUtils.StringToFloat(object[20]+""));
			calculateRequestData.getPump().setPumpBoreDiameter(StringManagerUtils.StringToFloat(object[21]+"")/1000);
			
			//套管数据
			CalculateRequestData.EveryCasing  everyCasing=new CalculateRequestData.EveryCasing();
			everyCasing.setInsideDiameter(StringManagerUtils.StringToFloat(object[22]+"")/1000);
			everyCasing.setLength(StringManagerUtils.StringToFloat(object[29]+""));
			calculateRequestData.setCasingString(new CalculateRequestData.CasingString());
			calculateRequestData.getCasingString().setEveryCasing(new ArrayList<CalculateRequestData.EveryCasing>());
			calculateRequestData.getCasingString().getEveryCasing().add(everyCasing);
			
			//生产数据
			calculateRequestData.setProductionParameter(new CalculateRequestData.ProductionParameter());
			calculateRequestData.getProductionParameter().setWaterCut(StringManagerUtils.StringToFloat(object[23]+""));
			calculateRequestData.getProductionParameter().setProductionGasOilRatio(StringManagerUtils.StringToFloat(object[24]+""));
			calculateRequestData.getProductionParameter().setTubingPressure(StringManagerUtils.StringToFloat(object[25]+""));
			calculateRequestData.getProductionParameter().setCasingPressure(StringManagerUtils.StringToFloat(object[26]+""));
			calculateRequestData.getProductionParameter().setWellHeadFluidTemperature(StringManagerUtils.StringToFloat(object[27]+""));
			calculateRequestData.getProductionParameter().setProducingfluidLevel(StringManagerUtils.StringToFloat(object[28]+""));
			calculateRequestData.getProductionParameter().setPumpSettingDepth(StringManagerUtils.StringToFloat(object[29]+""));
			calculateRequestData.getProductionParameter().setSubmergence(StringManagerUtils.StringToFloat(object[29]+"")-StringManagerUtils.StringToFloat(object[28]+""));
			//calculateRequestData.getProductionParameter().setOutputCoefficient(StringManagerUtils.StringToFloat(object[30]+""));
			
			//功图数据
			SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[31]);
			CLOB realClob = (CLOB) proxy.getWrappedClob(); 
			String gtsj=StringManagerUtils.CLOBtoString(realClob);
	        String arrgtsj[]=gtsj.replaceAll("\r\n", "\n").split("\n");
	        String cjsj=arrgtsj[0]+" "+arrgtsj[1];
	        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HHmmss");
	        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = df.parse(cjsj);
	        String testtime=df2.format(date);
	        calculateRequestData.setFSDiagram(new CalculateRequestData.FSDiagram());
	        calculateRequestData.getFSDiagram().setAcquisitionTime(testtime);
	        calculateRequestData.getFSDiagram().setStroke(StringManagerUtils.StringToFloat(arrgtsj[4]));
	        calculateRequestData.getFSDiagram().setSPM(StringManagerUtils.StringToFloat(arrgtsj[3]));
	        List<List<Float>> F=new ArrayList<List<Float>>();
	        List<List<Float>> S=new ArrayList<List<Float>>();
	        for(int i=0;i<StringManagerUtils.StringToInteger(arrgtsj[2]);i++){
	        	List<Float> FList=new ArrayList<Float>();
				List<Float> SList=new ArrayList<Float>();
	        	SList.add(StringManagerUtils.StringToFloat(arrgtsj[i*2+5]));
	        	FList.add(StringManagerUtils.StringToFloat(arrgtsj[i*2+6]));
	        	S.add(SList);
		        F.add(FList);
	        }
	        calculateRequestData.getFSDiagram().setF(F);
	        calculateRequestData.getFSDiagram().setS(S);
	        
	      //人工干预
	        calculateRequestData.setManualIntervention(new CalculateRequestData.ManualIntervention());
	        calculateRequestData.getManualIntervention().setCode(StringManagerUtils.StringToInteger(object[32]+""));
	        calculateRequestData.getManualIntervention().setNetGrossRatio(StringManagerUtils.StringToFloat(object[30]+""));
		}
		String requsetdata=gson.toJson(calculateRequestData);
		return requsetdata;
	}
	
	
	
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
	
	public String getObjectToCalculateRequestData(Object[] object) throws SQLException, IOException, ParseException{
		Gson gson = new Gson();
		CalculateRequestData calculateRequestData=new CalculateRequestData();
		try{
			calculateRequestData.setAKString("");
			calculateRequestData.setWellName(object[0]+"");
			calculateRequestData.setLiftingType(StringManagerUtils.StringToInteger(object[1]+""));
			calculateRequestData.setAcquisitionTime(object[2]+"");
			//如果是螺杆泵，添加转速
			if(calculateRequestData.getLiftingType()>=400&&calculateRequestData.getLiftingType()<500){
				calculateRequestData.setRPM(StringManagerUtils.StringToFloat(object[55]+""));
			}
			//流体PVT物性
			calculateRequestData.setFluidPVT(new CalculateRequestData.FluidPVT());
			calculateRequestData.getFluidPVT().setCrudeOilDensity(StringManagerUtils.StringToFloat(object[3]+""));
			calculateRequestData.getFluidPVT().setWaterDensity(StringManagerUtils.StringToFloat(object[4]+""));
			calculateRequestData.getFluidPVT().setNaturalGasRelativeDensity(StringManagerUtils.StringToFloat(object[5]+""));
			calculateRequestData.getFluidPVT().setSaturationPressure(StringManagerUtils.StringToFloat(object[6]+""));
			
			//油藏物性
			calculateRequestData.setReservoir(new CalculateRequestData.Reservoir());
			calculateRequestData.getReservoir().setPressure(StringManagerUtils.StringToFloat(object[7]+""));
			calculateRequestData.getReservoir().setDepth(StringManagerUtils.StringToFloat(object[8]+""));
			calculateRequestData.getReservoir().setTemperature(StringManagerUtils.StringToFloat(object[9]+""));
			
			//井深轨迹
			calculateRequestData.setWellboreTrajectory(new CalculateRequestData.WellboreTrajectory());
			calculateRequestData.getWellboreTrajectory().setMeasuringDepth(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().setVerticalDepth(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().setDeviationAngle(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().setAzimuthAngle(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().getMeasuringDepth().add((float) 100);
			calculateRequestData.getWellboreTrajectory().getMeasuringDepth().add((float) 200);
			calculateRequestData.getWellboreTrajectory().getVerticalDepth().add((float) 100);
			calculateRequestData.getWellboreTrajectory().getVerticalDepth().add((float) 200);
			calculateRequestData.getWellboreTrajectory().getDeviationAngle().add((float) 0);
			calculateRequestData.getWellboreTrajectory().getDeviationAngle().add((float) 0);
			calculateRequestData.getWellboreTrajectory().getAzimuthAngle().add((float) 0);
			calculateRequestData.getWellboreTrajectory().getAzimuthAngle().add((float) 0);
			
			//抽油杆参数
			CalculateRequestData.EveryRod everyRod1=new CalculateRequestData.EveryRod();
			everyRod1.setType(1);
			everyRod1.setGrade(object[10]+"");
			everyRod1.setLength(StringManagerUtils.StringToFloat(object[11]+""));
			everyRod1.setOutsideDiameter(StringManagerUtils.StringToFloat(object[12]+"")/1000);
			everyRod1.setInsideDiameter(StringManagerUtils.StringToFloat(object[40]+"")/1000);
			
			CalculateRequestData.EveryRod everyRod2=new CalculateRequestData.EveryRod();
			everyRod2.setType(1);
			everyRod2.setGrade(object[13]+"");
			everyRod2.setLength(StringManagerUtils.StringToFloat(object[14]+""));
			everyRod2.setOutsideDiameter(StringManagerUtils.StringToFloat(object[15]+"")/1000);
			everyRod2.setInsideDiameter(StringManagerUtils.StringToFloat(object[41]+"")/1000);
			
			CalculateRequestData.EveryRod everyRod3=new CalculateRequestData.EveryRod();
			everyRod3.setType(1);
			everyRod3.setGrade(object[16]+"");
			everyRod3.setLength(StringManagerUtils.StringToFloat(object[17]+""));
			everyRod3.setOutsideDiameter(StringManagerUtils.StringToFloat(object[18]+"")/1000);
			everyRod3.setInsideDiameter(StringManagerUtils.StringToFloat(object[42]+"")/1000);
			
			CalculateRequestData.EveryRod everyRod4=new CalculateRequestData.EveryRod();
			everyRod4.setType(1);
			everyRod4.setGrade(object[19]+"");
			everyRod4.setLength(StringManagerUtils.StringToFloat(object[20]+""));
			everyRod4.setOutsideDiameter(StringManagerUtils.StringToFloat(object[21]+"")/1000);
			everyRod4.setInsideDiameter(StringManagerUtils.StringToFloat(object[43]+"")/1000);
			
			calculateRequestData.setRodString(new CalculateRequestData.RodString());
			calculateRequestData.getRodString().setEveryRod(new ArrayList<CalculateRequestData.EveryRod>());
			if(everyRod1.getLength()>0){
				calculateRequestData.getRodString().getEveryRod().add(everyRod1);
			}
			if(everyRod2.getLength()>0){
				calculateRequestData.getRodString().getEveryRod().add(everyRod2);
			}
			if(everyRod3.getLength()>0){
				calculateRequestData.getRodString().getEveryRod().add(everyRod3);
			}if(everyRod4.getLength()>0){
				calculateRequestData.getRodString().getEveryRod().add(everyRod4);
			}
			
			//油管参数
			CalculateRequestData.EveryTubing everyTubing=new CalculateRequestData.EveryTubing();
			everyTubing.setInsideDiameter(StringManagerUtils.StringToFloat(object[22]+"")/1000);
			calculateRequestData.setTubingString(new CalculateRequestData.TubingString());
			calculateRequestData.getTubingString().setEveryTubing(new ArrayList<CalculateRequestData.EveryTubing>());
			calculateRequestData.getTubingString().getEveryTubing().add(everyTubing);
			
			//抽油泵参数
			calculateRequestData.setPump(new CalculateRequestData.Pump());
			//如果是螺杆泵，添加螺杆泵参数
			if(calculateRequestData.getLiftingType()>=400&&calculateRequestData.getLiftingType()<500){
				calculateRequestData.getPump().setBarrelLength(StringManagerUtils.StringToFloat(object[57]+""));
				calculateRequestData.getPump().setBarrelSeries((int)(StringManagerUtils.StringToFloat(object[58]+"")+0.5));
				calculateRequestData.getPump().setRotorDiameter(StringManagerUtils.StringToFloat(object[59]+"")/1000);
				calculateRequestData.getPump().setQPR(StringManagerUtils.StringToFloat(object[60]+"")/1000000);
			}else{
				calculateRequestData.getPump().setPumpType(object[23]+"");
				calculateRequestData.getPump().setBarrelType("L");
				calculateRequestData.getPump().setPumpGrade(StringManagerUtils.StringToInteger(object[24]+""));
				calculateRequestData.getPump().setPlungerLength(StringManagerUtils.StringToFloat(object[25]+""));
				calculateRequestData.getPump().setPumpBoreDiameter(StringManagerUtils.StringToFloat(object[26]+"")/1000);
			}
			
			
			//套管数据
			CalculateRequestData.EveryCasing  everyCasing=new CalculateRequestData.EveryCasing();
			everyCasing.setInsideDiameter(StringManagerUtils.StringToFloat(object[27]+"")/1000);
			everyCasing.setLength(StringManagerUtils.StringToFloat(object[34]+""));
			calculateRequestData.setCasingString(new CalculateRequestData.CasingString());
			calculateRequestData.getCasingString().setEveryCasing(new ArrayList<CalculateRequestData.EveryCasing>());
			calculateRequestData.getCasingString().getEveryCasing().add(everyCasing);
			
			//生产数据
			calculateRequestData.setProductionParameter(new CalculateRequestData.ProductionParameter());
			calculateRequestData.getProductionParameter().setWaterCut(StringManagerUtils.StringToFloat(object[28]+""));
			calculateRequestData.getProductionParameter().setProductionGasOilRatio(StringManagerUtils.StringToFloat(object[29]+""));
			calculateRequestData.getProductionParameter().setTubingPressure(StringManagerUtils.StringToFloat(object[30]+""));
			calculateRequestData.getProductionParameter().setCasingPressure(StringManagerUtils.StringToFloat(object[31]+""));
			calculateRequestData.getProductionParameter().setWellHeadFluidTemperature(StringManagerUtils.StringToFloat(object[32]+""));
			calculateRequestData.getProductionParameter().setProducingfluidLevel(StringManagerUtils.StringToFloat(object[33]+""));
			calculateRequestData.getProductionParameter().setPumpSettingDepth(StringManagerUtils.StringToFloat(object[34]+""));
			calculateRequestData.getProductionParameter().setSubmergence(StringManagerUtils.StringToFloat(object[34]+"")-StringManagerUtils.StringToFloat(object[34]+""));
			//calculateRequestData.getProductionParameter().setOutputCoefficient(StringManagerUtils.StringToFloat(object[30]+""));
			
			//功图数据
			if(object[36]!=null){
				SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[36]);
				CLOB realClob = (CLOB) proxy.getWrappedClob(); 
				String gtsj=StringManagerUtils.CLOBtoString(realClob);
				String arrgtsj[]=gtsj.replaceAll("\r\n", "\n").split("\n");
				if(StringManagerUtils.isNotNull(gtsj)&&arrgtsj.length>0){
			        String cjsj=arrgtsj[0].replaceAll("-", "").replaceAll("/", "").replaceAll(":", "")
			        		+" "+arrgtsj[1].replaceAll("-", "").replaceAll("/", "").replaceAll(":", "");
			        
			        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HHmmss");
			        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        Date date = df.parse(cjsj);
			        String testtime=df2.format(date);
			        calculateRequestData.setFSDiagram(new CalculateRequestData.FSDiagram());
			        calculateRequestData.getFSDiagram().setAcquisitionTime(testtime);
			        calculateRequestData.getFSDiagram().setStroke(StringManagerUtils.StringToFloat(arrgtsj[4]));
			        calculateRequestData.getFSDiagram().setSPM(StringManagerUtils.StringToFloat(arrgtsj[3]));
			        List<List<Float>> F=new ArrayList<List<Float>>();
			        List<List<Float>> S=new ArrayList<List<Float>>();
			        List<Float> P=new ArrayList<Float>();
			        List<Float> A=new ArrayList<Float>();
			        for(int i=0;i<(arrgtsj.length-5)/2;i++){
			        	List<Float> FList=new ArrayList<Float>();
						List<Float> SList=new ArrayList<Float>();
			        	SList.add(StringManagerUtils.StringToFloat(arrgtsj[i*2+5]));
			        	FList.add(StringManagerUtils.StringToFloat(arrgtsj[i*2+6]));
			        	S.add(SList);
				        F.add(FList);
			        }
			        if(StringManagerUtils.isNotNull(object[37]+"")){
						String powerCurve[]=(object[37]+"").replaceAll("null", "").split(",");
						for(int i=0;i<powerCurve.length;i++){
				        	P.add(StringManagerUtils.StringToFloat(powerCurve[i]));
				        }
					}
					if(StringManagerUtils.isNotNull(object[38]+"")){
						String currentCurve[]=(object[38]+"").replaceAll("null", "").split(",");
						for(int i=0;i<currentCurve.length;i++){
				        	A.add(StringManagerUtils.StringToFloat(currentCurve[i]));
				        }
					}
			        
			        calculateRequestData.getFSDiagram().setF(F);
			        calculateRequestData.getFSDiagram().setS(S);
			        calculateRequestData.getFSDiagram().setP(P);
			        calculateRequestData.getFSDiagram().setA(A);
				}
			}
	        calculateRequestData.setSystemEfficiency(new CalculateRequestData.SystemEfficiency());
	        
	      //人工干预
	        calculateRequestData.setManualIntervention(new CalculateRequestData.ManualIntervention());
	        calculateRequestData.getManualIntervention().setCode(StringManagerUtils.StringToInteger(object[39]+""));
	        calculateRequestData.getManualIntervention().setNetGrossRatio(StringManagerUtils.StringToFloat(object[35]+""));
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		String requsetdata=gson.toJson(calculateRequestData);
		return requsetdata;
	}
	
	public String getObjectToCalculateRequestDataHYTX(Object[] object) throws SQLException, IOException, ParseException{
		Gson gson = new Gson();
		Connection connOuter = null;   
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    float activePower=0;
		CalculateRequestData calculateRequestData=new CalculateRequestData();
		try{
			calculateRequestData.setAKString("");
			calculateRequestData.setWellName(object[0]+"");
			
			//流体PVT物性
			calculateRequestData.setFluidPVT(new CalculateRequestData.FluidPVT());
			calculateRequestData.getFluidPVT().setCrudeOilDensity(StringManagerUtils.StringToFloat(object[1]+""));
			calculateRequestData.getFluidPVT().setWaterDensity(StringManagerUtils.StringToFloat(object[2]+""));
			calculateRequestData.getFluidPVT().setNaturalGasRelativeDensity(StringManagerUtils.StringToFloat(object[3]+""));
			calculateRequestData.getFluidPVT().setSaturationPressure(StringManagerUtils.StringToFloat(object[4]+""));
			
			//油藏物性
			calculateRequestData.setReservoir(new CalculateRequestData.Reservoir());
			calculateRequestData.getReservoir().setPressure(StringManagerUtils.StringToFloat(object[5]+""));
			calculateRequestData.getReservoir().setDepth(StringManagerUtils.StringToFloat(object[6]+""));
			calculateRequestData.getReservoir().setTemperature(StringManagerUtils.StringToFloat(object[7]+""));
			
			//井深轨迹
			calculateRequestData.setWellboreTrajectory(new CalculateRequestData.WellboreTrajectory());
			calculateRequestData.getWellboreTrajectory().setMeasuringDepth(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().setVerticalDepth(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().setDeviationAngle(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().setAzimuthAngle(new ArrayList<Float>());
			calculateRequestData.getWellboreTrajectory().getMeasuringDepth().add((float) 100);
			calculateRequestData.getWellboreTrajectory().getMeasuringDepth().add((float) 200);
			calculateRequestData.getWellboreTrajectory().getVerticalDepth().add((float) 100);
			calculateRequestData.getWellboreTrajectory().getVerticalDepth().add((float) 200);
			calculateRequestData.getWellboreTrajectory().getDeviationAngle().add((float) 0);
			calculateRequestData.getWellboreTrajectory().getDeviationAngle().add((float) 0);
			calculateRequestData.getWellboreTrajectory().getAzimuthAngle().add((float) 0);
			calculateRequestData.getWellboreTrajectory().getAzimuthAngle().add((float) 0);
			
			//抽油杆参数
			CalculateRequestData.EveryRod everyRod1=new CalculateRequestData.EveryRod();
			everyRod1.setType(1);
			everyRod1.setGrade(object[8]+"");
			everyRod1.setLength(StringManagerUtils.StringToFloat(object[9]+""));
			everyRod1.setOutsideDiameter(StringManagerUtils.StringToFloat(object[10]+"")/1000);
			
			CalculateRequestData.EveryRod everyRod2=new CalculateRequestData.EveryRod();
			everyRod2.setType(1);
			everyRod2.setGrade(object[11]+"");
			everyRod2.setLength(StringManagerUtils.StringToFloat(object[12]+""));
			everyRod2.setOutsideDiameter(StringManagerUtils.StringToFloat(object[13]+"")/1000);
			
			CalculateRequestData.EveryRod everyRod3=new CalculateRequestData.EveryRod();
			everyRod3.setType(1);
			everyRod3.setGrade(object[14]+"");
			everyRod3.setLength(StringManagerUtils.StringToFloat(object[15]+""));
			everyRod3.setOutsideDiameter(StringManagerUtils.StringToFloat(object[16]+"")/1000);
			
			calculateRequestData.setRodString(new CalculateRequestData.RodString());
			calculateRequestData.getRodString().setEveryRod(new ArrayList<CalculateRequestData.EveryRod>());
			if(everyRod1.getLength()>0){
				calculateRequestData.getRodString().getEveryRod().add(everyRod1);
			}
			if(everyRod2.getLength()>0){
				calculateRequestData.getRodString().getEveryRod().add(everyRod2);
			}
			if(everyRod3.getLength()>0){
				calculateRequestData.getRodString().getEveryRod().add(everyRod3);
			}
			
			//油管参数
			CalculateRequestData.EveryTubing everyTubing=new CalculateRequestData.EveryTubing();
			everyTubing.setInsideDiameter(StringManagerUtils.StringToFloat(object[17]+"")/1000);
			calculateRequestData.setTubingString(new CalculateRequestData.TubingString());
			calculateRequestData.getTubingString().setEveryTubing(new ArrayList<CalculateRequestData.EveryTubing>());
			calculateRequestData.getTubingString().getEveryTubing().add(everyTubing);
			
			//抽油泵参数
			calculateRequestData.setPump(new CalculateRequestData.Pump());
			calculateRequestData.getPump().setPumpType(object[18]+"");
			calculateRequestData.getPump().setBarrelType("L");
			calculateRequestData.getPump().setPumpGrade(StringManagerUtils.StringToInteger(object[19]+""));
			calculateRequestData.getPump().setPlungerLength(StringManagerUtils.StringToFloat(object[20]+""));
			calculateRequestData.getPump().setPumpBoreDiameter(StringManagerUtils.StringToFloat(object[21]+"")/1000);
			
			//套管数据
			CalculateRequestData.EveryCasing  everyCasing=new CalculateRequestData.EveryCasing();
			everyCasing.setInsideDiameter(StringManagerUtils.StringToFloat(object[22]+"")/1000);
			everyCasing.setLength(StringManagerUtils.StringToFloat(object[29]+""));
			calculateRequestData.setCasingString(new CalculateRequestData.CasingString());
			calculateRequestData.getCasingString().setEveryCasing(new ArrayList<CalculateRequestData.EveryCasing>());
			calculateRequestData.getCasingString().getEveryCasing().add(everyCasing);
			
			//生产数据
			calculateRequestData.setProductionParameter(new CalculateRequestData.ProductionParameter());
			calculateRequestData.getProductionParameter().setWaterCut(StringManagerUtils.StringToFloat(object[23]+""));
			calculateRequestData.getProductionParameter().setProductionGasOilRatio(StringManagerUtils.StringToFloat(object[24]+""));
			calculateRequestData.getProductionParameter().setTubingPressure(StringManagerUtils.StringToFloat(object[25]+""));
			calculateRequestData.getProductionParameter().setCasingPressure(StringManagerUtils.StringToFloat(object[26]+""));
			calculateRequestData.getProductionParameter().setWellHeadFluidTemperature(StringManagerUtils.StringToFloat(object[27]+""));
			calculateRequestData.getProductionParameter().setProducingfluidLevel(StringManagerUtils.StringToFloat(object[28]+""));
			calculateRequestData.getProductionParameter().setPumpSettingDepth(StringManagerUtils.StringToFloat(object[29]+""));
			calculateRequestData.getProductionParameter().setSubmergence(StringManagerUtils.StringToFloat(object[29]+"")-StringManagerUtils.StringToFloat(object[28]+""));
			//calculateRequestData.getProductionParameter().setOutputCoefficient(StringManagerUtils.StringToFloat(object[30]+""));
			
			//功图数据
			SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[31]);
			CLOB realClob = (CLOB) proxy.getWrappedClob(); 
			String gtsj=StringManagerUtils.CLOBtoString(realClob);
			
	        String arrgtsj[]=gtsj.replaceAll("\r\n", "\n").split("\n");
	        String cjsj=arrgtsj[0]+" "+arrgtsj[1];
	        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HHmmss");
	        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = df.parse(cjsj);
	        String testtime=df2.format(date);
	        calculateRequestData.setFSDiagram(new CalculateRequestData.FSDiagram());
	        calculateRequestData.getFSDiagram().setAcquisitionTime(testtime);
	        calculateRequestData.getFSDiagram().setStroke(StringManagerUtils.StringToFloat(arrgtsj[4]));
	        calculateRequestData.getFSDiagram().setSPM(StringManagerUtils.StringToFloat(arrgtsj[3]));
	        List<List<Float>> F=new ArrayList<List<Float>>();
	        List<List<Float>> S=new ArrayList<List<Float>>();
	        for(int i=0;i<(arrgtsj.length-5)/2;i++){
	        	List<Float> FList=new ArrayList<Float>();
				List<Float> SList=new ArrayList<Float>();
	        	SList.add(StringManagerUtils.StringToFloat(arrgtsj[i*2+5]));
	        	FList.add(StringManagerUtils.StringToFloat(arrgtsj[i*2+6]));
	        	S.add(SList);
		        F.add(FList);
	        }
	        calculateRequestData.getFSDiagram().setF(F);
	        calculateRequestData.getFSDiagram().setS(S);
	        
	      //人工干预
	        calculateRequestData.setManualIntervention(new CalculateRequestData.ManualIntervention());
	        calculateRequestData.getManualIntervention().setCode(StringManagerUtils.StringToInteger(object[32]+""));
	        calculateRequestData.getManualIntervention().setNetGrossRatio(StringManagerUtils.StringToFloat(object[30]+""));
	      
//	        String sql="select * from ("
//					+ " select  decode(t.e_q,null,0,t.e_p) from well_data_his t where t.well_name='"+object[0]+"' "
//					+ " order by  abs(t.stime-to_date('"+testtime+"','yyyy-mm-dd hh24:mi:ss')),t.stime "
//					+ ") where rownum<=1";
	        String sql="select decode(v1.stime-v2.stime,0,0,trunc((v1.e_e-v2.e_e)/(to_number(v1.stime-v2.stime)*24),2)) from "
	        		+ " (select * from "
	        		+ "    ( select  decode(t.e_e,null,0,t.e_e) as e_e,t.stime "
	        		+ "    from well_data_his t "
	        		+ "    where t.well_name='"+object[0]+"' "
	        		+ "    order by  abs(t.stime-to_date('"+testtime+"','yyyy-mm-dd hh24:mi:ss')),t.stime "
	        		+ " ) where rownum<=1) v1,"
	        		+ " (select * from "
	        		+ "    (select  decode(t.e_e,null,0,t.e_e) as e_e,t.stime "
	        		+ "     from well_data_his t "
	        		+ "     where t.well_name='"+object[0]+"' "
	        		+ "     order by  abs(t.stime-"
	        		+ "                           (select * from "
	        		+ "								(select t.stime from well_gt_data t "
	        		+ "                              where t.well_name='"+object[0]+"' and t.stime<to_date('"+testtime+"','yyyy-mm-dd hh24:mi:ss') "
	        		+ "								 order by t.stime desc)"
	        		+ "                           where rownum<=1))"
	        		+ "          ,t.stime ) "
	        		+ "    where rownum<=1) v2";
			connOuter=OracleJdbcUtis.getOuterConnection();
			if(connOuter!=null){
				pstmt = connOuter.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY); 
				rs=pstmt.executeQuery();
				while(rs.next()){
					activePower=rs.getFloat(1);
				}
			}
			
			calculateRequestData.setSystemEfficiency(new CalculateRequestData.SystemEfficiency());
			calculateRequestData.getSystemEfficiency().setMotorInputActivePower(activePower);
			
		}catch(Exception e){
			return "";
		}finally{
			if(pstmt!=null)
				pstmt.close();
			if(rs!=null){
				rs.close();
			}
			connOuter.close();
		}
		String requsetdata=gson.toJson(calculateRequestData);
		return requsetdata;
	}
	
	
	public String getDefaultWellname(String orgId){
		String sql="select v.* from "
				+ " (select t007.jh from t_wellinformation t007,sc_org org where t007.dwbh=org.org_code and org.org_id in ("+orgId+") order by t007.jh) v "
				+ " where rownum<=1";
		List<?> list = this.findCallSql(sql);
		
		return list.get(0).toString();
	}
	
	public boolean saveCalculateResult(int jlbh,CalculateResponseData calculateResponseData) throws SQLException, ParseException{
		StringBuffer bgtStrBuff = new StringBuffer();
		if(calculateResponseData.getCalculationStatus().getResultStatus()!=-99){
			int curvecount=calculateResponseData.getFSDiagram().getF().get(0).size();
			int pointcount=calculateResponseData.getFSDiagram().getCNT();
			bgtStrBuff.append(curvecount+";"+pointcount+";");
			for(int i=0;i<curvecount;i++){
				for(int j=0;j<pointcount;j++){
					bgtStrBuff.append(calculateResponseData.getFSDiagram().getS().get(j).get(i)+",");//位移
					bgtStrBuff.append(calculateResponseData.getFSDiagram().getF().get(j).get(i)+",");//载荷
				}
				if(pointcount>0){
					bgtStrBuff.deleteCharAt(bgtStrBuff.length() - 1);
				}
				bgtStrBuff.append(";");
			}
			if(curvecount>0){
				bgtStrBuff.deleteCharAt(bgtStrBuff.length() - 1);
			}
		}
		String bgtStr=bgtStrBuff.toString();
		
		return this.getBaseDao().saveCalculateResult(jlbh,calculateResponseData,bgtStr,"");
	}
	
	public boolean saveCalculateResult(int jlbh,CalculateResponseData calculateResponseData,
			ElectricCalculateResponseData electricCalculateResponseData,TimeEffResponseData timeEffResponseData,CommResponseData commResponseData) throws SQLException, ParseException{
		StringBuffer bgtStrBuff = new StringBuffer();
		if(calculateResponseData.getCalculationStatus().getResultStatus()==1&&calculateResponseData.getFSDiagram()!=null){
			int curvecount=calculateResponseData.getFSDiagram().getF().get(0).size();
			int pointcount=calculateResponseData.getFSDiagram().getCNT();
			bgtStrBuff.append(curvecount+";"+pointcount+";");
			for(int i=0;i<curvecount;i++){
				for(int j=0;j<pointcount;j++){
					bgtStrBuff.append(calculateResponseData.getFSDiagram().getS().get(j).get(i)+",");//位移
					bgtStrBuff.append(calculateResponseData.getFSDiagram().getF().get(j).get(i)+",");//载荷
				}
				if(pointcount>0){
					bgtStrBuff.deleteCharAt(bgtStrBuff.length() - 1);
				}
				bgtStrBuff.append(";");
			}
			if(curvecount>0){
				bgtStrBuff.deleteCharAt(bgtStrBuff.length() - 1);
			}
		}
		String bgtStr=bgtStrBuff.toString();
		
		return this.getBaseDao().saveCalculateResult(jlbh,calculateResponseData,electricCalculateResponseData,timeEffResponseData,commResponseData,bgtStr,"");
	}
	
	public int deleteInvalidData() throws SQLException{
		String sql="delete from t_outputwellhistory where jsbz=0 and jlbh not in"
				+ " (select t033.jlbh"
				+ " from t_wellinformation t007, t_indicatordiagram t010,"
				+ " t_dynamicliquidlevel t011,t_outputwellhistory t033  "
				+ " where t007.jlbh=t010.jbh and t033.jbh=t007.jlbh and t033.gtbh=t010.jlbh  "
				+ " and t033.dymbh=t011.jlbh  "
				+ " and  t033.jsbz in (0,2))";
		return this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public int deleteInvalidData(int jbh) throws SQLException{
		String sql="delete from t_outputwellhistory where jsbz=0 and jlbh not in"
				+ " (select t033.jlbh"
				+ " from t_wellinformation t007, t_indicatordiagram t010,"
				+ " t_dynamicliquidlevel t011,t_outputwellhistory t033  "
				+ " where t007.jlbh=t010.jbh and t033.jbh=t007.jlbh and t033.gtbh=t010.jlbh  "
				+ " and t033.dymbh=t011.jlbh  "
				+ " and  t033.jsbz in (0,2))"
				+ " and t033.jbh="+jbh;
		return this.getBaseDao().updateOrDeleteBySql(sql);
	}
	
	public boolean saveHYTXCalculateResult(int jlbh,CalculateResponseData calculateResponseData,String jh) throws SQLException, ParseException{
		StringBuffer bgtStrBuff = new StringBuffer();
		if(calculateResponseData.getCalculationStatus().getResultStatus()==1&&calculateResponseData.getCalculationStatus().getResultCode()!=1232){
			int curvecount=calculateResponseData.getFSDiagram().getF().get(0).size();
			int pointcount=calculateResponseData.getFSDiagram().getF().size();
			bgtStrBuff.append(curvecount+";"+pointcount+";");
			for(int i=0;i<curvecount;i++){
				for(int j=0;j<pointcount;j++){
					bgtStrBuff.append(calculateResponseData.getFSDiagram().getS().get(j).get(i)+",");//位移
					bgtStrBuff.append(calculateResponseData.getFSDiagram().getF().get(j).get(i)+",");//载荷
				}
				if(pointcount>0){
					bgtStrBuff.deleteCharAt(bgtStrBuff.length() - 1);
				}
				bgtStrBuff.append(";");
			}
			if(curvecount>0){
				bgtStrBuff.deleteCharAt(bgtStrBuff.length() - 1);
			}
		}
		String bgtStr=bgtStrBuff.toString();
		
		this.getBaseDao().saveCalculateResult(jlbh,calculateResponseData,bgtStr,jh);
		if(calculateResponseData.getCalculationStatus().getResultStatus()==1){
			this.getBaseDao().saveHYTXCalculateResult_Resp(calculateResponseData,jh);
			this.getBaseDao().saveHYTXCalculateResult_Prod(calculateResponseData,jh);
			this.getBaseDao().saveHYTXCalculateResult_Eff(calculateResponseData,jh);
			this.getBaseDao().saveHYTXCalculateResult_Diag(calculateResponseData,jh);
		}
		return false;
	}
	
	public List<String> getTotalCalculationDataList(String tatalDate,String wellName) throws ParseException{
		StringBuffer dataSbf=null;
		List<String> requestDataList=new ArrayList<String>();
		String timeEffTotalDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(tatalDate),-1);
		String timeEffTotalUrl=Config.getTimeEfficiencyHttpServerURL();
		String commTotalUrl=Config.getCommHttpServerURL();
		String wellinformationSql="select t.wellname,t2.runtime,t.runtimeefficiencysource,t.driveraddr,t.driverid from t_wellinformation t,t_outputwellproduction t2 where t.id=t2.wellid  ";
		String singleCalculateResuleSql="select t007.wellname,to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as acquisitiontime,t.workingconditioncode,"
											+" t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,prod.watercut,"
											+" t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction, prod.watercut_w"
											+" t.surfacesystemefficiency,t.welldownsystemefficiency,t.systemefficiency,t.powerconsumptionperthm,"
											+" t.fullnesscoefficient,t.stroke,t.spm,"
											+" prod.productiongasoilratio,prod.producingfluidlevel,prod.pumpsettingdepth,prod.pumpsettingdepth-prod.producingfluidlevel as submergence,t.pumpeff,prod.pumpborediameter/1000 as pumpborediameter,"
											+" t.idegreebalance,t.wattdegreebalance,"
											+" prod.tubingpressure,prod.casingpressure,prod.wellheadfluidtemperature,"
											+" t.currenta,t.currentb,t.currentc,t.voltagea,t.voltageb,t.voltagec,"
											+" t.activepowerconsumption,t.reactivepowerconsumption,"
											+" t.activepower,t.reactivepower,t.powerfactor,"
											+ "t.egklx,t.yxsj,t.yxsl,t.yxsjd,t.rydl,t.txzt,t.yxzt,t.txsj,t.txsl,t.txqj,"
											+ "t.bpyxpl,t.rpm "
											+" from t_indicatordiagram t ,t_wellinformation t007 ,t_outputwellproduction prod"
											+" where t.wellid=t007.id and t.productiondataid=prod.id "
											+" and  t.acquisitiontime > "
											+" (select max(to_date(to_char(t2.acquisitiontime,'yyyy-mm-dd'),'yyyy-mm-dd')) "
											+" from t_indicatordiagram t2 where t2.wellId=t.wellid and t2.resultstatus=1 "
											+" and t2.acquisitiontime< to_date('"+tatalDate+"','yyyy-mm-dd')) "
											+" and t.resultstatus=1 and t.workingconditioncode<>1232 and t.acquisitiontime<to_date('"+tatalDate+"','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(wellName)){
			wellinformationSql+=" and t.wellName in ("+wellName+")";
			singleCalculateResuleSql+=" and t007.jh in ("+wellName+")";
		}
//		wellinformationSql+=" and t.jh='龙1-斜09'";
//		singleCalculateResuleSql+=" and t007.jh='龙1-斜09'";
		
		singleCalculateResuleSql+=" order by t.jbh,t.acquisitiontime";
		wellinformationSql+=" order by t.jlbh";
		List<?> welllist = findCallSql(wellinformationSql);
		List<?> singleresultlist = findCallSql(singleCalculateResuleSql);

		for(int i=0;i<welllist.size();i++){
			try{
				Object[] wellObj=(Object[]) welllist.get(i);
				TimeEffResponseData timeEffResponseData=null;
				CommResponseData commResponseData=null;
//				if(StringManagerUtils.getCurrentTime().equals(tatalDate)){
//					Gson gson = new Gson();
//					java.lang.reflect.Type type=null;
//					//通信汇总
//					if(wellObj[4]!=null&&wellObj[5]!=null){
//						String commTotalRequestData="{\"AKString\":\"\","
//								+ "\"WellName\":\""+wellObj[0]+"\","
//								+ "\"Date\":\""+timeEffTotalDate+"\"}";
//						String commTotalResponse=StringManagerUtils.sendPostMethod(commTotalUrl, commTotalRequestData,"utf-8");
//						type = new TypeToken<commResponseData>() {}.getType();
//						commResponseData = gson.fromJson(commTotalResponse, type);
//					}
//					
//					//时率汇总
//					if(!"0".equals(wellObj[3]+"")){//时率来源不为人工录入时
//						String timeEffTotalRequestData="{\"AKString\":\"\","
//								+ "\"WellName\":\""+wellObj[0]+"\","
//								+ "\"Date\":\""+timeEffTotalDate+"\"}";
//						String timeEffTotalResponse=StringManagerUtils.sendPostMethod(timeEffTotalUrl, timeEffTotalRequestData,"utf-8");
//						type = new TypeToken<TimeEffResponseData>() {}.getType();
//						timeEffResponseData = gson.fromJson(timeEffTotalResponse, type);
//						if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1&&timeEffResponseData.getRunRange()!=null&&timeEffResponseData.getRunRange().size()>0){
//							System.out.println("隔天汇总时间:"+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+",请求数据:"+timeEffTotalRequestData);
//							System.out.println("隔天汇总返回数据:"+timeEffTotalResponse);
//						}
//					}else if("2".equals(wellObj[3])){//时率来源为电参
//						
//					}
//				}
				dataSbf = new StringBuffer();
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+wellObj[0]+"\",");
				dataSbf.append("\"EveryTime\": [");
				String wellRunRime=getWellRuningTime(StringManagerUtils.getOneDayDateString(-1),wellObj[2],wellObj[1]);
				boolean isHaveRestlut=false;
				for(int j=0;j<singleresultlist.size();j++){
					Object[] resuleObj=(Object[]) singleresultlist.get(j);
					if(wellObj[0].toString().equals(resuleObj[0].toString())){
						isHaveRestlut=true;
						if(commResponseData!=null&&commResponseData.getResultStatus()==1&&commResponseData.getDaily().getCommEfficiency().getRange()!=null&&commResponseData.getDaily().getCommEfficiency().getRange().size()>0){
							dataSbf.append("{\"CommStatus\":"+commResponseData.getDaily().getCommStatus()+",");
							dataSbf.append("\"CommTime\":"+commResponseData.getDaily().getCommEfficiency().getTime()+",");
							dataSbf.append("\"CommTimeEfficiency\":"+commResponseData.getDaily().getCommEfficiency().getEfficiency()+",");
							dataSbf.append("\"CommRange\":\""+commResponseData.getDaily().getCommEfficiency().getRangeString()+"\",");
						}else{
							dataSbf.append("{\"CommStatus\":"+resuleObj[45]+",");
							dataSbf.append("\"CommTime\":"+resuleObj[47]+",");
							dataSbf.append("\"CommTimeEfficiency\":"+resuleObj[48]+",");
							dataSbf.append("\"CommRange\":\""+resuleObj[49]+"\",");
						}
						if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1&&timeEffResponseData.getDaily().getRunEfficiency().getRange()!=null&&timeEffResponseData.getDaily().getRunEfficiency().getRange().size()>0){
							dataSbf.append("\"RunStatus\":"+timeEffResponseData.getDaily().getRunStatus()+",");
							dataSbf.append("\"RunTime\":"+timeEffResponseData.getDaily().getRunEfficiency().getTime()+",");
							dataSbf.append("\"RunTimeEfficiency\":"+timeEffResponseData.getDaily().getRunEfficiency().getEfficiency()+",");
							dataSbf.append("\"RunRange\":\""+timeEffResponseData.getDaily().getRunEfficiency().getRangeString()+"\",");
						}else{
							if(!"0".equals(wellObj[3]+"")){//时率结果不是人工录入
								dataSbf.append("\"RunStatus\":"+resuleObj[46]+",");
								dataSbf.append("\"RunTime\":"+resuleObj[41]+",");
								dataSbf.append("\"RunTimeEfficiency\":"+resuleObj[42]+",");
								dataSbf.append("\"RunRange\":\""+resuleObj[43]+"\",");
							}else{
								dataSbf.append("\"RunStatus\":"+resuleObj[46]+",");
								dataSbf.append("\"RunTime\":"+wellObj[2]+",");
								dataSbf.append("\"RunTimeEfficiency\":"+(StringManagerUtils.StringToFloat(wellObj[2]+"", 2))/24+",");
								dataSbf.append("\"RunRange\":\""+wellRunRime+"\",");
							}
						}
						
						dataSbf.append("\"StopReason\":1,");
						dataSbf.append("\"StartReason\":1,");
						dataSbf.append("\"AcquisitionTime\":\""+resuleObj[1]+"\",");
						dataSbf.append("\"FSResultCode\":"+resuleObj[2]+",");
						dataSbf.append("\"ETResultCode\":"+resuleObj[40]+",");
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
						dataSbf.append("\"ADegreeOfBalance\":"+resuleObj[24]+",");
						dataSbf.append("\"PDegreeOfBalance\":"+resuleObj[25]+",");
						dataSbf.append("\"TubingPressure\":"+resuleObj[26]+",");
						dataSbf.append("\"CasingPressure\":"+resuleObj[27]+",");
						dataSbf.append("\"WellHeadFluidTemperature\":"+resuleObj[28]+",");
						dataSbf.append("\"CurrentA\":"+resuleObj[29]+",");
						dataSbf.append("\"CurrentB\":"+resuleObj[30]+",");
						dataSbf.append("\"CurrentC\":"+resuleObj[31]+",");
						dataSbf.append("\"VoltageA\":"+resuleObj[32]+",");
						dataSbf.append("\"VoltageB\":"+resuleObj[33]+",");
						dataSbf.append("\"VoltageC\":"+resuleObj[34]+",");
						if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
//							dataSbf.append("\"DailyAPC\":"+timeEffResponseData.getAPC()+",");
//							dataSbf.append("\"DailyRPC\":"+timeEffResponseData.getRPC()+",");
						}else{
							dataSbf.append("\"DailyAPC\":"+resuleObj[44]+",");
							dataSbf.append("\"DailyRPC\":null,");
						}
						dataSbf.append("\"TotalAPC\":"+resuleObj[35]+",");
						dataSbf.append("\"TotalRPC\":"+resuleObj[36]+",");
						dataSbf.append("\"ActivePowerSum\":"+resuleObj[37]+",");
						dataSbf.append("\"ReactivePowerSum\":"+resuleObj[38]+",");
						dataSbf.append("\"CompositePowerFactor\":"+resuleObj[39]+",");
						dataSbf.append("\"RunFrequency\":"+StringManagerUtils.StringToFloat(resuleObj[50]+"")+",");
						dataSbf.append("\"RPM\":"+StringManagerUtils.StringToFloat(resuleObj[51]+"")+"},");
						
					}
				}
				if(isHaveRestlut){
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
	
	public List<String> getFSDiagramDailyCalculationRequestData(String tatalDate,String wellId) throws ParseException{
		StringBuffer dataSbf=null;
		List<String> requestDataList=new ArrayList<String>();
		String timeEffTotalUrl=Config.getTimeEfficiencyHttpServerURL();
		String commTotalUrl=Config.getCommHttpServerURL();
		String wellinformationSql="select t.wellname,t2.runtime as runtime2,t.runtimeefficiencysource,t.driveraddr,t.driverid,"
				+ " t3.commstatus,t3.commtime,t3.commtimeefficiency,t3.commrange,"
				+ " t3.runstatus,t3.runtime,t3.runtimeefficiency,t3.runrange "
				+ " from t_wellinformation t "
				+ " left outer join t_outputwellproduction_rt t2 on t.id=t2.wellid  "
				+ " left outer join t_discretedata_rt  t3 on t3.wellId=t.id"
				+ " where 1=1";
		String singleCalculateResuleSql="select t007.wellname,to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as acquisitiontime,t.workingconditioncode,"
											+" t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,prod.watercut,"
											+" t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction, prod.watercut_w,"
											+" t.surfacesystemefficiency,t.welldownsystemefficiency,t.systemefficiency,t.powerconsumptionperthm,"
											+" t.fullnesscoefficient,t.stroke,t.spm,"
											+" prod.productiongasoilratio,prod.producingfluidlevel,prod.pumpsettingdepth,prod.pumpsettingdepth-prod.producingfluidlevel as submergence,t.pumpeff,prod.pumpborediameter/1000 as pumpborediameter,"
											+" t.idegreebalance,t.wattdegreebalance,"
											+" prod.tubingpressure,prod.casingpressure,prod.wellheadfluidtemperature"
											+" from t_indicatordiagram t ,t_wellinformation t007 ,t_outputwellproduction prod"
											+" where t.wellid=t007.id and t.productiondataid=prod.id "
											+" and  t.acquisitiontime > "
											+" (select max(to_date(to_char(t2.acquisitiontime,'yyyy-mm-dd'),'yyyy-mm-dd')) "
											+" from t_indicatordiagram t2 where t2.wellId=t.wellid and t2.resultstatus=1 "
											+" and t2.acquisitiontime< to_date('"+tatalDate+"','yyyy-mm-dd')) "
											+" and t.resultstatus=1 and t.workingconditioncode<>1232 and t.acquisitiontime<to_date('"+tatalDate+"','yyyy-mm-dd')";
		String statusSql="select well.wellname,to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as acquisitiontime,"
				+ "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange,"
				+ "t.runstatus,t.runtimeefficiency,t.runtime,t.runrange "
				+ " from t_discretedata t,t_wellinformation well "
				+ " where t.wellid=well.id and t.acquisitiontime=( select max(t2.acquisitiontime) from t_discretedata t2 where t2.wellid=t.wellid and t2.acquisitiontime between to_date('"+tatalDate+"','yyyy-mm-dd')-1 and to_date('"+tatalDate+"','yyyy-mm-dd'))";
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
							if(statusObj[2]!=null&&StringManagerUtils.StringToInteger(statusObj[2]+"")==1){
								commStatus=true;
							}
							if(statusObj[6]!=null&&StringManagerUtils.StringToInteger(statusObj[6]+"")==1){
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
							dataSbf.append("\"RunTimeEfficiency\":"+(StringManagerUtils.StringToFloat(wellObj[1]+"", 2))/24+",");
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
	
	
	public List<String> getDiscreteDailyCalculation(String tatalDate,String wellId) throws ParseException{
		StringBuffer dataSbf=null;
		List<String> requestDataList=new ArrayList<String>();
		String wellinformationSql="select t.wellname,t2.runtime,t.runtimeefficiencysource,t.driveraddr,t.driverid from t_wellinformation t left outer join t_outputwellproduction_rt t2 on t.id=t2.wellid  where 1=1";
		String singleCalculateResuleSql="select t007.wellname,to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as acquisitiontime,"
				+" t.ia,t.ib,t.ic,t.va,t.vb,t.vc,"
				+" t.frequencyrunvalue"
				+" from t_discretedata t ,t_wellinformation t007"
				+" where t.wellid=t007.id "
				+" and t.acquisitiontime between to_date('2020-01-17','yyyy-mm-dd')-1 and to_date('2020-01-17','yyyy-mm-dd')";
		if(StringManagerUtils.isNotNull(wellId)){
			wellinformationSql+=" and t.id in ("+wellId+")";
			singleCalculateResuleSql+=" and t007.id in ("+wellId+")";
		}
//		wellinformationSql+=" and t.jh='龙1-斜09'";
//		singleCalculateResuleSql+=" and t007.jh='龙1-斜09'";
		
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
						dataSbf.append("\"RunFrequency\":"+StringManagerUtils.StringToFloat(resuleObj[8]+"")+"},");
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
	public boolean saveDiscreteDailyCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException{
		return this.getBaseDao().saveDiscreteDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData, tatalDate);
	}
	
}