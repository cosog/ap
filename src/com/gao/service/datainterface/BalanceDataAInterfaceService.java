package com.gao.service.datainterface;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.stereotype.Service;

import com.gao.model.balance.BalanceResponseData;
import com.gao.model.balance.CycleEvaluaion;
import com.gao.model.balance.TorqueBalanceResponseData;
import com.gao.model.calculate.CalculateRequestData;
import com.gao.model.calculate.CalculateResponseData;
import com.gao.model.calculate.TotalCalculateResponseData;
import com.gao.service.base.BaseService;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

@SuppressWarnings("deprecation")
@Service("balanceDataAInterfaceService")
public class BalanceDataAInterfaceService<T> extends BaseService<T> {
	public String getObjectToBalanceTorqueRequestData(Object[] object) throws SQLException, IOException, ParseException{
		String requsetdata="";
		StringBuffer jsonbuff = new StringBuffer();
		StringBuffer fbuff = new StringBuffer();
		StringBuffer sbuff = new StringBuffer();
		jsonbuff.append("{\"AKString\":\"\",");
		jsonbuff.append("\"WellName\":\""+object[1]+"\",");
		jsonbuff.append("\"PumpingUnit\":{");
		jsonbuff.append("\"Manufacturer\":\""+object[2]+"\",");
		jsonbuff.append("\"Model\":\""+object[3]+"\",");
		jsonbuff.append("\"Type\":"+object[4]+",");
		jsonbuff.append("\"CrankRotationDirection\":\""+object[5]+"\",");
		jsonbuff.append("\"OffsetAngleOfCrank\":"+object[6]+",");
		jsonbuff.append("\"CrankGravityRadius\":"+object[7]+",");
		jsonbuff.append("\"SingleCrankWeight\":"+object[8]+",");
		jsonbuff.append("\"BalanceMaxMoveSpace\":"+object[9]+",");
		jsonbuff.append("\"StructuralUnbalance\":"+object[10]+",");
		jsonbuff.append("\"Balance\":{");
		jsonbuff.append("\"MaxCNT\":"+object[11]+",");
		jsonbuff.append("\"EveryBalance\":[");
		String[] balanceArr=object[12].toString().split(";");
		for(int i=0;i<balanceArr.length;i++){
			String[] everyBalance=balanceArr[i].split(",");
			jsonbuff.append("{\"Name\":\"\",");
			jsonbuff.append("\"Position\":"+everyBalance[0]+",");
			jsonbuff.append("\"Weight\":"+everyBalance[1]+"},");
		}
		if(balanceArr.length>0){
			jsonbuff.deleteCharAt(jsonbuff.length() - 1);
		}
		jsonbuff.append("]}},");
		jsonbuff.append("\"FSDiagram\": {");
		jsonbuff.append("\"AcquisitionTime\":\""+object[13]+"\",");
		String gtsj=StringManagerUtils.CLOBObjecttoString(object[14]);
		String[] gtsjArr=gtsj.replaceAll("\r\n", "\n").split("\n");
		jsonbuff.append("\"SPM\":"+gtsjArr[3]+",");
		jsonbuff.append("\"Stroke\":"+gtsjArr[4]+",");
		int count=Integer.parseInt(gtsjArr[2]);
		fbuff.append("[");
		sbuff.append("[");
		for(int i=0;i<count;i++){
			if(gtsjArr[i*2+5].indexOf(".")!=0){
				sbuff.append("["+gtsjArr[i*2+5]+"],");
			}else{
				sbuff.append("[0"+gtsjArr[i*2+5]+"],");
			}
			
			if(gtsjArr[i*2+6].indexOf(".")!=0){
				fbuff.append("["+gtsjArr[i*2+6]+"],");
			}else{
				fbuff.append("[0"+gtsjArr[i*2+6]+"],");
			}
		}
		if(fbuff.length()>2){
			sbuff.deleteCharAt(sbuff.length() - 1);
			fbuff.deleteCharAt(fbuff.length() - 1);
		}
		fbuff.append("]");
		sbuff.append("]");
		jsonbuff.append("\"F\":"+fbuff+",");
		jsonbuff.append("\"S\":"+sbuff+"},");
		jsonbuff.append("\"SystemEfficiency\": {");
		jsonbuff.append("\"FourBarLinkageEfficiency\":"+object[15]+"");
		jsonbuff.append("},");
		jsonbuff.append("\"ManualIntervention\": {");
		jsonbuff.append("\"Code\":0");
		jsonbuff.append("}}");
		requsetdata=jsonbuff.toString();
		return requsetdata;
	}
	
	public String getObjectToBalanceRequestData(Object[] object) throws SQLException, IOException, ParseException{
		String requsetdata="";
		StringBuffer jsonbuff = new StringBuffer();
		StringBuffer fbuff = new StringBuffer();
		StringBuffer sbuff = new StringBuffer();
		boolean NoNegative=false;
		boolean NoPositive=false;
		jsonbuff.append("{\"AKString\":\"\",");
		jsonbuff.append("\"WellName\":\""+object[1]+"\",");
		jsonbuff.append("\"PumpingUnit\":{");
		jsonbuff.append("\"Manufacturer\":\""+object[2]+"\",");
		jsonbuff.append("\"Model\":\""+object[3]+"\",");
		jsonbuff.append("\"Type\":"+object[4]+",");
		jsonbuff.append("\"CrankRotationDirection\":\""+object[5]+"\",");
		jsonbuff.append("\"OffsetAngleOfCrank\":"+object[6]+",");
		jsonbuff.append("\"InitialAngleOfCrank\":"+12+",");
		jsonbuff.append("\"CrankGravityRadius\":"+object[7]+",");
		jsonbuff.append("\"SingleCrankWeight\":"+object[8]+",");
		jsonbuff.append("\"BalanceMaxMoveSpace\":"+object[9]+",");
		jsonbuff.append("\"StructuralUnbalance\":"+object[10]+",");
		jsonbuff.append("\"Balance\":{");
		jsonbuff.append("\"MaxCNT\":"+object[11]+",");
		jsonbuff.append("\"EveryBalance\":[");
		String[] balanceArr=object[12].toString().split(";");
		for(int i=0;i<balanceArr.length;i++){
			String[] everyBalance=balanceArr[i].split(",");
			jsonbuff.append("{\"Position\":"+everyBalance[0]+",");
			jsonbuff.append("\"Weight\":"+everyBalance[1]+"},");
		}
		if(balanceArr.length>0){
			jsonbuff.deleteCharAt(jsonbuff.length() - 1);
		}
		jsonbuff.append("]},");
		jsonbuff.append("\"PRTF\":{\"CrankAngle\":[],\"PR\":[],\"TF\":[]}");
		jsonbuff.append("},");
		jsonbuff.append("\"FSDiagram\": {");
		jsonbuff.append("\"AcquisitionTime\":\""+object[13]+"\",");
		String gtsj=StringManagerUtils.CLOBObjecttoString(object[14]);
		String[] gtsjArr=gtsj.replaceAll("\r\n", "\n").split("\n");
		jsonbuff.append("\"SPM\":"+gtsjArr[3]+",");
		jsonbuff.append("\"Stroke\":"+gtsjArr[4]+",");
		int count=Integer.parseInt(gtsjArr[2]);
		fbuff.append("[");
		sbuff.append("[");
		for(int i=0;i<count;i++){
			if(gtsjArr[i*2+5].indexOf(".")!=0){
				sbuff.append(gtsjArr[i*2+5]+",");
			}else{
				sbuff.append("0"+gtsjArr[i*2+5]+",");
			}
			
			if(gtsjArr[i*2+6].indexOf(".")!=0){
				fbuff.append(gtsjArr[i*2+6]+",");
			}else{
				fbuff.append("0"+gtsjArr[i*2+6]+",");
			}
		}
		if(fbuff.length()>2){
			sbuff.deleteCharAt(sbuff.length() - 1);
			fbuff.deleteCharAt(fbuff.length() - 1);
		}
		fbuff.append("]");
		sbuff.append("]");
		jsonbuff.append("\"F\":"+fbuff+",");
		jsonbuff.append("\"S\":"+sbuff+"},");
		jsonbuff.append("\"SystemEfficiency\": {");
		jsonbuff.append("\"FourBarLinkageEfficiency\":"+object[15]+",");
		jsonbuff.append("\"MotorEfficiency\":"+object[16]+",");
		jsonbuff.append("\"BeltEfficiency\":"+object[17]+",");
		jsonbuff.append("\"GearReducerEfficiency\":"+object[18]);
		jsonbuff.append("},");
		
		if("1".equals(object[19].toString())){
			NoNegative=true;
		}else if("2".equals(object[19].toString())){
			NoPositive=true;
		}
		
		jsonbuff.append("\"ManualIntervention\": {");
		jsonbuff.append("\"NoNegative\":"+NoNegative+",");
		jsonbuff.append("\"NoPositive\":"+NoPositive);
		jsonbuff.append("}}");
		requsetdata=jsonbuff.toString();
		return requsetdata;
	}
	
	public boolean saveTorqueBalanceResponseData(int jlbh,TorqueBalanceResponseData torqueBalanceResponseData) throws SQLException, ParseException{
		StringBuffer CurrentTorqueCurve = new StringBuffer();
		StringBuffer OptimizitionTorqueCurve = new StringBuffer();
		StringBuffer OptimizationBalance = new StringBuffer();
		StringBuffer MotionCurve = new StringBuffer();
		StringBuffer CurrentTorqueCurve2 = new StringBuffer();
		StringBuffer OptimizitionTorqueCurve2 = new StringBuffer();
		StringBuffer OptimizationBalance2 = new StringBuffer();
		if(torqueBalanceResponseData.getCalculationStatus().getResultStatus()==1){
			//拼接扭矩最大值法优化后平衡位置
			for(int i=0;i<torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizationBalance().getEveryBalance().size();i++){
				OptimizationBalance.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizationBalance().getEveryBalance().get(i).getPosition()+
						","+torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizationBalance().getEveryBalance().get(i).getWeight()+";");
			}
			if(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizationBalance().getEveryBalance().size()>0){
				OptimizationBalance.deleteCharAt(OptimizationBalance.length() - 1);
			}
			//拼接扭矩均方根法优化后平衡位置
			for(int i=0;i<torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizationBalance().getEveryBalance().size();i++){
				OptimizationBalance2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizationBalance().getEveryBalance().get(i).getPosition()+
						","+torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizationBalance().getEveryBalance().get(i).getWeight()+";");
			}
			if(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizationBalance().getEveryBalance().size()>0){
				OptimizationBalance2.deleteCharAt(OptimizationBalance2.length() - 1);
			}
			
			//拼接扭矩最大值法结果曲线
			CurrentTorqueCurve.append(torqueBalanceResponseData.getMotionCurve().getCrankAngle().size()+";");
			CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getMaxValue()+";");
			CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getIndexMaxValue()+";");
			CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getMinValue()+";");
			CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getIndexMinValue()+";");
			CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getAverageValue()+";");
			CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getMeanSquareRoot()+";");
			CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getUpStrokeMaxValue()+";");
			CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getIndexUpStrokeMaxValue()+";");
			CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getDownStrokeMaxValue()+";");
			CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getIndexDownStrokeMaxValue()+";");
			CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getUpStrokeAverageValue()+";");
			CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getDownStrokeAverageValue()+";");
			
			OptimizitionTorqueCurve.append(torqueBalanceResponseData.getMotionCurve().getCrankAngle().size()+";");
			OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getMaxValue()+";");
			OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getIndexMaxValue()+";");
			OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getMinValue()+";");
			OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getIndexMinValue()+";");
			OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getAverageValue()+";");
			OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getMeanSquareRoot()+";");
			OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getUpStrokeMaxValue()+";");
			OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getIndexUpStrokeMaxValue()+";");
			OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getDownStrokeMaxValue()+";");
			OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getIndexDownStrokeMaxValue()+";");
			OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getUpStrokeAverageValue()+";");
			OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getDownStrokeAverageValue()+";");
			
			//拼接扭矩均方根法结果曲线
			CurrentTorqueCurve2.append(torqueBalanceResponseData.getMotionCurve().getCrankAngle().size()+";");
			CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNetAnalysis().getMaxValue()+";");
			CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNetAnalysis().getIndexMaxValue()+";");
			CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNetAnalysis().getMinValue()+";");
			CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNetAnalysis().getIndexMinValue()+";");
			CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNetAnalysis().getAverageValue()+";");
			CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNetAnalysis().getMeanSquareRoot()+";");
			CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNetAnalysis().getUpStrokeMaxValue()+";");
			CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNetAnalysis().getIndexUpStrokeMaxValue()+";");
			CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNetAnalysis().getDownStrokeMaxValue()+";");
			CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNetAnalysis().getIndexDownStrokeMaxValue()+";");
			CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNetAnalysis().getUpStrokeAverageValue()+";");
			CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNetAnalysis().getDownStrokeAverageValue()+";");
			
			OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getMotionCurve().getCrankAngle().size()+";");
			OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNetAnalysis().getMaxValue()+";");
			OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNetAnalysis().getIndexMaxValue()+";");
			OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNetAnalysis().getMinValue()+";");
			OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNetAnalysis().getIndexMinValue()+";");
			OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNetAnalysis().getAverageValue()+";");
			OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNetAnalysis().getMeanSquareRoot()+";");
			OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNetAnalysis().getUpStrokeMaxValue()+";");
			OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNetAnalysis().getIndexUpStrokeMaxValue()+";");
			OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNetAnalysis().getDownStrokeMaxValue()+";");
			OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNetAnalysis().getIndexDownStrokeMaxValue()+";");
			OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNetAnalysis().getUpStrokeAverageValue()+";");
			OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNetAnalysis().getDownStrokeAverageValue()+";");
			for(int i=0;i<torqueBalanceResponseData.getMotionCurve().getCrankAngle().size();i++){
				//拼接扭矩最大值法结果曲线
				CurrentTorqueCurve.append(torqueBalanceResponseData.getMotionCurve().getCrankAngle().get(i)+",");
				CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getLoad().get(i)+",");
				CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getBalance().get(i)+",");
				CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getCrank().get(i)+",");
				CurrentTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNet().get(i)+";");
				
				OptimizitionTorqueCurve.append(torqueBalanceResponseData.getMotionCurve().getCrankAngle().get(i)+",");
				OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getLoad().get(i)+",");
				OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getBalance().get(i)+",");
				OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getCrank().get(i)+",");
				OptimizitionTorqueCurve.append(torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNet().get(i)+";");
				
				//拼接扭矩均方根法结果曲线
				CurrentTorqueCurve2.append(torqueBalanceResponseData.getMotionCurve().getCrankAngle().get(i)+",");
				CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getLoad().get(i)+",");
				CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getBalance().get(i)+",");
				CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getCrank().get(i)+",");
				CurrentTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNet().get(i)+";");
				
				OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getMotionCurve().getCrankAngle().get(i)+",");
				OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getLoad().get(i)+",");
				OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getBalance().get(i)+",");
				OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getCrank().get(i)+",");
				OptimizitionTorqueCurve2.append(torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNet().get(i)+";");
				
				//拼接抽油机运动特性曲线
				MotionCurve.append(torqueBalanceResponseData.getMotionCurve().getCrankAngle().get(i)+",");
				MotionCurve.append(torqueBalanceResponseData.getMotionCurve().getS().get(i)+",");
				MotionCurve.append(torqueBalanceResponseData.getMotionCurve().getV().get(i)+",");
				MotionCurve.append(torqueBalanceResponseData.getMotionCurve().getA().get(i)+",");
				MotionCurve.append(torqueBalanceResponseData.getMotionCurve().getPR().get(i)+",");
				MotionCurve.append(torqueBalanceResponseData.getMotionCurve().getTF().get(i)+";");
			}
			if(torqueBalanceResponseData.getMotionCurve().getCrankAngle().size()>0){
				CurrentTorqueCurve.deleteCharAt(CurrentTorqueCurve.length() - 1);
				OptimizitionTorqueCurve.deleteCharAt(OptimizitionTorqueCurve.length() - 1);
				CurrentTorqueCurve2.deleteCharAt(CurrentTorqueCurve2.length() - 1);
				OptimizitionTorqueCurve2.deleteCharAt(OptimizitionTorqueCurve2.length() - 1);
				MotionCurve.deleteCharAt(MotionCurve.length() - 1);
			}
		}
		boolean result=false;
		
		result= this.getBaseDao().saveTorqueBalanceResponseData(jlbh,torqueBalanceResponseData,
				CurrentTorqueCurve.toString(),OptimizitionTorqueCurve.toString(),OptimizationBalance.toString(),
				CurrentTorqueCurve2.toString(),OptimizitionTorqueCurve2.toString(),OptimizationBalance2.toString(),MotionCurve.toString());
		return result;
	}
	
	public boolean saveBalanceResponseData(int jlbh,float Stroke,float SPM,BalanceResponseData balanceResponseData) throws SQLException, ParseException{
		StringBuffer CurrentTorqueCurve = new StringBuffer();//当前扭矩曲线
		StringBuffer MotionCurve = new StringBuffer();//运动特性曲线
		
		StringBuffer OptimizitionTorqueCurve = new StringBuffer();//最大值法预期扭矩曲线
		StringBuffer OptimizationBalance = new StringBuffer();//最大值法预期平衡块信息
		
		StringBuffer OptimizitionTorqueCurve2 = new StringBuffer();//均方根法预期扭矩曲线
		StringBuffer OptimizationBalance2 = new StringBuffer();//均方根法预期平衡块信息
		
		StringBuffer OptimizitionTorqueCurve3 = new StringBuffer();//平均功率法预期扭矩曲线
		StringBuffer OptimizationBalance3 = new StringBuffer();//平均功率法预期平衡块信息
		if(balanceResponseData.getCalculationStatus().getResultStatus()==1){
			//拼接扭矩最大值法优化后平衡位置
			for(int i=0;i<balanceResponseData.getMaxValueMethod().getBalance().getEveryBalance().size();i++){
				OptimizationBalance.append(balanceResponseData.getMaxValueMethod().getBalance().getEveryBalance().get(i).getPosition()+
						","+balanceResponseData.getMaxValueMethod().getBalance().getEveryBalance().get(i).getWeight()+";");
			}
			if(balanceResponseData.getMaxValueMethod().getBalance().getEveryBalance().size()>0){
				OptimizationBalance.deleteCharAt(OptimizationBalance.length() - 1);
			}
			//拼接均方根法优化后平衡位置
			for(int i=0;i<balanceResponseData.getMeanSquareRootMethod().getBalance().getEveryBalance().size();i++){
				OptimizationBalance2.append(balanceResponseData.getMeanSquareRootMethod().getBalance().getEveryBalance().get(i).getPosition()+
						","+balanceResponseData.getMeanSquareRootMethod().getBalance().getEveryBalance().get(i).getWeight()+";");
			}
			if(balanceResponseData.getMeanSquareRootMethod().getBalance().getEveryBalance().size()>0){
				OptimizationBalance2.deleteCharAt(OptimizationBalance2.length() - 1);
			}
			
			//拼接平均功率法优化后平衡位置
			for(int i=0;i<balanceResponseData.getAveragePowerMethod().getBalance().getEveryBalance().size();i++){
				OptimizationBalance3.append(balanceResponseData.getAveragePowerMethod().getBalance().getEveryBalance().get(i).getPosition()+
						","+balanceResponseData.getAveragePowerMethod().getBalance().getEveryBalance().get(i).getWeight()+";");
			}
			if(balanceResponseData.getAveragePowerMethod().getBalance().getEveryBalance().size()>0){
				OptimizationBalance3.deleteCharAt(OptimizationBalance3.length() - 1);
			}
			
			//拼接当前扭矩曲线
			CurrentTorqueCurve.append(balanceResponseData.getCurrentTorqueCurve().getNetAnalysis().getMeanSquareRoot()+";");
			CurrentTorqueCurve.append(balanceResponseData.getCurrentTorqueCurve().getNetAnalysis().getUpStrokeMeanSquareRoot()+";");
			CurrentTorqueCurve.append(balanceResponseData.getCurrentTorqueCurve().getNetAnalysis().getDownStrokeMeanSquareRoot()+";");
			CurrentTorqueCurve.append(balanceResponseData.getCurrentTorqueCurve().getNetAnalysis().getUpStrokeAveragePower()+";");
			CurrentTorqueCurve.append(balanceResponseData.getCurrentTorqueCurve().getNetAnalysis().getDownStrokeAveragePower()+";");
			CurrentTorqueCurve.append(balanceResponseData.getCurrentTorqueCurve().getNetAnalysis().getUpStrokeMaxValue()+";");
			CurrentTorqueCurve.append(balanceResponseData.getCurrentTorqueCurve().getNetAnalysis().getDownStrokeMaxValue()+";");
			
			//最大值法预期扭矩曲线
			OptimizitionTorqueCurve.append(balanceResponseData.getMaxValueMethod().getTorqueCurve().getNetAnalysis().getMeanSquareRoot()+";");
			OptimizitionTorqueCurve.append(balanceResponseData.getMaxValueMethod().getTorqueCurve().getNetAnalysis().getUpStrokeMeanSquareRoot()+";");
			OptimizitionTorqueCurve.append(balanceResponseData.getMaxValueMethod().getTorqueCurve().getNetAnalysis().getDownStrokeMeanSquareRoot()+";");
			OptimizitionTorqueCurve.append(balanceResponseData.getMaxValueMethod().getTorqueCurve().getNetAnalysis().getUpStrokeAveragePower()+";");
			OptimizitionTorqueCurve.append(balanceResponseData.getMaxValueMethod().getTorqueCurve().getNetAnalysis().getDownStrokeAveragePower()+";");
			OptimizitionTorqueCurve.append(balanceResponseData.getMaxValueMethod().getTorqueCurve().getNetAnalysis().getUpStrokeMaxValue()+";");
			OptimizitionTorqueCurve.append(balanceResponseData.getMaxValueMethod().getTorqueCurve().getNetAnalysis().getDownStrokeMaxValue()+";");
			
			//均方根法预期扭矩曲线
			OptimizitionTorqueCurve2.append(balanceResponseData.getMeanSquareRootMethod().getTorqueCurve().getNetAnalysis().getMeanSquareRoot()+";");
			OptimizitionTorqueCurve2.append(balanceResponseData.getMeanSquareRootMethod().getTorqueCurve().getNetAnalysis().getUpStrokeMeanSquareRoot()+";");
			OptimizitionTorqueCurve2.append(balanceResponseData.getMeanSquareRootMethod().getTorqueCurve().getNetAnalysis().getDownStrokeMeanSquareRoot()+";");
			OptimizitionTorqueCurve2.append(balanceResponseData.getMeanSquareRootMethod().getTorqueCurve().getNetAnalysis().getUpStrokeAveragePower()+";");
			OptimizitionTorqueCurve2.append(balanceResponseData.getMeanSquareRootMethod().getTorqueCurve().getNetAnalysis().getDownStrokeAveragePower()+";");
			OptimizitionTorqueCurve2.append(balanceResponseData.getMeanSquareRootMethod().getTorqueCurve().getNetAnalysis().getUpStrokeMaxValue()+";");
			OptimizitionTorqueCurve2.append(balanceResponseData.getMeanSquareRootMethod().getTorqueCurve().getNetAnalysis().getDownStrokeMaxValue()+";");
			
			//功率法预期扭矩曲线
			OptimizitionTorqueCurve3.append(balanceResponseData.getAveragePowerMethod().getTorqueCurve().getNetAnalysis().getMeanSquareRoot()+";");
			OptimizitionTorqueCurve3.append(balanceResponseData.getAveragePowerMethod().getTorqueCurve().getNetAnalysis().getUpStrokeMeanSquareRoot()+";");
			OptimizitionTorqueCurve3.append(balanceResponseData.getAveragePowerMethod().getTorqueCurve().getNetAnalysis().getDownStrokeMeanSquareRoot()+";");
			OptimizitionTorqueCurve3.append(balanceResponseData.getAveragePowerMethod().getTorqueCurve().getNetAnalysis().getUpStrokeAveragePower()+";");
			OptimizitionTorqueCurve3.append(balanceResponseData.getAveragePowerMethod().getTorqueCurve().getNetAnalysis().getDownStrokeAveragePower()+";");
			OptimizitionTorqueCurve3.append(balanceResponseData.getAveragePowerMethod().getTorqueCurve().getNetAnalysis().getUpStrokeMaxValue()+";");
			OptimizitionTorqueCurve3.append(balanceResponseData.getAveragePowerMethod().getTorqueCurve().getNetAnalysis().getDownStrokeMaxValue()+";");
			
			for(int i=0;i<balanceResponseData.getMotionCurve().getCrankAngle().size();i++){
				
				CurrentTorqueCurve.append(balanceResponseData.getMotionCurve().getCrankAngle().get(i)+",");
				OptimizitionTorqueCurve.append(balanceResponseData.getMotionCurve().getCrankAngle().get(i)+",");
				OptimizitionTorqueCurve2.append(balanceResponseData.getMotionCurve().getCrankAngle().get(i)+",");
				OptimizitionTorqueCurve3.append(balanceResponseData.getMotionCurve().getCrankAngle().get(i)+",");
				
				if(balanceResponseData.getCurrentTorqueCurve().getLoad()!=null){
					CurrentTorqueCurve.append(balanceResponseData.getCurrentTorqueCurve().getLoad().get(i)+",");
					OptimizitionTorqueCurve.append(balanceResponseData.getCurrentTorqueCurve().getLoad().get(i)+",");
					OptimizitionTorqueCurve2.append(balanceResponseData.getCurrentTorqueCurve().getLoad().get(i)+",");
					OptimizitionTorqueCurve3.append(balanceResponseData.getCurrentTorqueCurve().getLoad().get(i)+",");
				}
				
				if(balanceResponseData.getCurrentTorqueCurve().getBalance()!=null){
					CurrentTorqueCurve.append(balanceResponseData.getCurrentTorqueCurve().getBalance().get(i)+",");
				}
				if(balanceResponseData.getMaxValueMethod().getTorqueCurve().getBalance()!=null){
					OptimizitionTorqueCurve.append(balanceResponseData.getMaxValueMethod().getTorqueCurve().getBalance().get(i)+",");
				}
				if(balanceResponseData.getMeanSquareRootMethod().getTorqueCurve().getBalance()!=null){
					OptimizitionTorqueCurve2.append(balanceResponseData.getMeanSquareRootMethod().getTorqueCurve().getBalance().get(i)+",");
				}
				if(balanceResponseData.getAveragePowerMethod().getTorqueCurve().getBalance()!=null){
					OptimizitionTorqueCurve3.append(balanceResponseData.getAveragePowerMethod().getTorqueCurve().getBalance().get(i)+",");
				}
				
				if(balanceResponseData.getCurrentTorqueCurve().getCrank()!=null){
					CurrentTorqueCurve.append(balanceResponseData.getCurrentTorqueCurve().getCrank().get(i)+",");
					OptimizitionTorqueCurve.append(balanceResponseData.getCurrentTorqueCurve().getCrank().get(i)+",");
					OptimizitionTorqueCurve2.append(balanceResponseData.getCurrentTorqueCurve().getCrank().get(i)+",");
					OptimizitionTorqueCurve3.append(balanceResponseData.getCurrentTorqueCurve().getCrank().get(i)+",");
				}
				
				
				if(balanceResponseData.getCurrentTorqueCurve().getNet()!=null){
					CurrentTorqueCurve.append(balanceResponseData.getCurrentTorqueCurve().getNet().get(i)+";");
				}
				if(balanceResponseData.getMaxValueMethod().getTorqueCurve().getNet()!=null){
					OptimizitionTorqueCurve.append(balanceResponseData.getMaxValueMethod().getTorqueCurve().getNet().get(i)+";");
				}
				if(balanceResponseData.getMeanSquareRootMethod().getTorqueCurve().getNet()!=null){
					OptimizitionTorqueCurve2.append(balanceResponseData.getMeanSquareRootMethod().getTorqueCurve().getNet().get(i)+";");
				}
				if(balanceResponseData.getAveragePowerMethod().getTorqueCurve().getNet()!=null){
					OptimizitionTorqueCurve3.append(balanceResponseData.getAveragePowerMethod().getTorqueCurve().getNet().get(i)+";");
				}
				
				
				//拼接抽油机运动特性曲线
				MotionCurve.append(balanceResponseData.getMotionCurve().getCrankAngle().get(i)+",");
				MotionCurve.append(balanceResponseData.getMotionCurve().getS().get(i)+",");
				MotionCurve.append(balanceResponseData.getMotionCurve().getV().get(i)+",");
				MotionCurve.append(balanceResponseData.getMotionCurve().getA().get(i)+",");
				MotionCurve.append(balanceResponseData.getPRTF().getPR().get(i)+",");
				MotionCurve.append(balanceResponseData.getPRTF().getTF().get(i)+";");
			}
			if(balanceResponseData.getMotionCurve().getCrankAngle().size()>0){
				CurrentTorqueCurve.deleteCharAt(CurrentTorqueCurve.length() - 1);
				OptimizitionTorqueCurve.deleteCharAt(OptimizitionTorqueCurve.length() - 1);
				OptimizitionTorqueCurve2.deleteCharAt(OptimizitionTorqueCurve2.length() - 1);
				OptimizitionTorqueCurve3.deleteCharAt(OptimizitionTorqueCurve3.length() - 1);
				MotionCurve.deleteCharAt(MotionCurve.length() - 1);
			}
		}
		boolean result=false;
		
		result= this.getBaseDao().saveBalanceResponseData(jlbh,Stroke,SPM,balanceResponseData,
				CurrentTorqueCurve.toString(),
				OptimizitionTorqueCurve.toString(),OptimizationBalance.toString(),
				OptimizitionTorqueCurve2.toString(),OptimizationBalance2.toString(),
				OptimizitionTorqueCurve3.toString(),OptimizationBalance3.toString(),
				MotionCurve.toString());
		return result;
	}
	
	public List<String> getTotalCalculationDataList(int type) throws ParseException{
		List<String> totalQequestDataList=new ArrayList<String>();
		StringBuffer dataSbf=null;
		String sqlWell="select t007.jh,t203.positionandweight from t_wellinformation t007,t_203_balanceinformation t203 "
				+ " where t007.jlbh=t203.jbh and t203.updatetime=(select max(t.updatetime) from t_203_balanceinformation t where t.jbh=t203.jbh ) order by t007.jh ";
		String sql="select t007.jh,to_char(t.gtcjsj,'yyyy-mm-dd hh24:mi:ss') as gtcjsj,t.deltaradius,t.deltablock,t.currentdegreeofbalance,t.optimizationbalance "
				+ " from t_201_balanceanalysis t ,t_wellinformation t007"
				+ " where t.jbh=t007.jlbh  and  t.gtcjsj > (select max(to_date(to_char(t2.gtcjsj,'yyyy-mm-dd'),'yyyy-mm-dd')) from t_201_balanceanalysis t2 where t2.jbh=t.jbh and t2.jsbz=1 and t2.calculatetype="+type+" and t2.gtcjsj <to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd'))"
				+ " and t.jsbz=1 and t.calculatetype="+type+" and t.gtcjsj <to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')order by t007.jh,t.gtcjsj";
		List<?> welllist = this.findCallSql(sqlWell);
		List<?> totallist=this.findCallSql(sql);
		
		
		
		for(int i=0;i<welllist.size();i++){
			try{
				dataSbf = new StringBuffer();
				Object[] wellObj=(Object[]) welllist.get(i);
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+wellObj[0]+"\",");
				dataSbf.append("\"CurrentBalance\":{\"EveryBalance\": [");
				String[] balanceArr=wellObj[1].toString().split(";");
				for(int j=0;j<balanceArr.length;j++){
					String[] everyBalance=balanceArr[j].split(",");
					dataSbf.append("{\"Name\": \"\",");
					dataSbf.append("\"Position\":"+everyBalance[0]+",");
					dataSbf.append("\"Weight\":"+everyBalance[1]+"},");
				}
				if(balanceArr.length>0)
					dataSbf.deleteCharAt(dataSbf.length() - 1);
				dataSbf.append("]},");
				dataSbf.append("\"EveryTime\": [");
				boolean haveRecord=false;
				for(int j=0;j<totallist.size();j++){
					Object[] totalObj=(Object[]) totallist.get(j);
					if(wellObj[0].toString().equals(totalObj[0].toString())){//判断该条记录是否属于此井
						haveRecord=true;
						dataSbf.append("{\"AcquisitionTime\":\""+totalObj[1]+"\",");
						dataSbf.append("\"DeltaRadius\":"+totalObj[2]+",");
						dataSbf.append("\"DeltaBlock\":"+totalObj[3]+",");
						dataSbf.append("\"CurrentDegreeOfBalance\":"+totalObj[4]+",");
						dataSbf.append("\"ResultBalance\": {\"EveryBalance\": [");
						String[] balanceArr2=totalObj[5].toString().split(";");
						for(int k=0;k<balanceArr2.length;k++){
							String[] everyBalance=balanceArr2[k].split(",");
							dataSbf.append("{\"Name\": \"\",");
							dataSbf.append("\"Position\":"+everyBalance[0]+",");
							dataSbf.append("\"Weight\":"+everyBalance[1]+"},");
						}
						if(balanceArr2.length>0)
							dataSbf.deleteCharAt(dataSbf.length() - 1);
						dataSbf.append("]}},");
					}
				}
				if(haveRecord)
					dataSbf.deleteCharAt(dataSbf.length() - 1);
				dataSbf.append("]}");
				if(haveRecord){
					totalQequestDataList.add(dataSbf.toString());
				}
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		return totalQequestDataList;
	}
	
	public List<String> getCycleCalculationDataList(int type,String jh) throws ParseException{
		List<String> cycleQequestDataList=new ArrayList<String>();
		StringBuffer dataSbf=null;
		String sqlWell="select t007.jh,t203.positionandweight from t_wellinformation t007,t_203_balanceinformation t203 "
				+ " where  t007.jlbh=t203.jbh and t203.updatetime=(select max(t.updatetime) from t_203_balanceinformation t where t.jbh=t203.jbh )  ";
		
		
		
		String sql="select t007.jh,to_char(t.gtcjsj,'yyyy-mm-dd hh24:mi:ss') as gtcjsj,t.deltaradius,t.deltablock,t.currentdegreeofbalance,t.optimizationbalance  "
				+ " from t_201_balanceanalysis t ,t_wellinformation t007 "
				+ " where t.jbh=t007.jlbh and "
				+ " to_date(to_char(t.gtcjsj,'yyyy-mm-dd'),'yyyy-mm-dd') <=(select max(to_date(to_char(t2.gtcjsj,'yyyy-mm-dd'),'yyyy-mm-dd'))"
				+ "  from t_201_balanceanalysis t2 where t2.jbh=t.jbh and t2.jsbz=1 and t2.calculatetype="+type+" and t2.gtcjsj <to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') ) "
				+ " and to_date(to_char(t.gtcjsj,'yyyy-mm-dd'),'yyyy-mm-dd') > ( select max(to_date(to_char(t2.gtcjsj,'yyyy-mm-dd'),'yyyy-mm-dd')-t007.sczq) "
				+ "  from t_201_balanceanalysis t2 where t2.jbh=t.jbh and t2.jsbz=1 and t2.calculatetype="+type+" and t2.gtcjsj <to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') ) "
				+ " and t.jsbz=1 and t.calculatetype="+type;
		if (StringManagerUtils.isNotNull(jh)) {
			sqlWell+=" and t007.jh='"+jh+"'";
			sql+=" and t007.jh='"+jh+"'";
		}
		
		sqlWell+=" order by t007.jh";
		sql+=" order by t007.jh,t.gtcjsj";
		List<?> welllist = this.findCallSql(sqlWell);
		List<?> totallist=this.findCallSql(sql);
		
		
		
		for(int i=0;i<welllist.size();i++){
			try{
				dataSbf = new StringBuffer();
				Object[] wellObj=(Object[]) welllist.get(i);
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"WellName\":\""+wellObj[0]+"\",");
				dataSbf.append("\"CurrentBalance\":{\"EveryBalance\": [");
				String[] balanceArr=wellObj[1].toString().split(";");
				for(int j=0;j<balanceArr.length;j++){
					String[] everyBalance=balanceArr[j].split(",");
					dataSbf.append("{\"Name\": \"\",");
					dataSbf.append("\"Position\":"+everyBalance[0]+",");
					dataSbf.append("\"Weight\":"+everyBalance[1]+"},");
				}
				if(balanceArr.length>0)
					dataSbf.deleteCharAt(dataSbf.length() - 1);
				dataSbf.append("]},");
				dataSbf.append("\"EveryTime\": [");
				boolean haveRecord=false;
				for(int j=0;j<totallist.size();j++){
					Object[] totalObj=(Object[]) totallist.get(j);
					if(wellObj[0].toString().equals(totalObj[0].toString())){//判断该条记录是否属于此井
						haveRecord=true;
						dataSbf.append("{\"AcquisitionTime\":\""+totalObj[1]+"\",");
						dataSbf.append("\"DeltaRadius\":"+totalObj[2]+",");
						dataSbf.append("\"DeltaBlock\":"+totalObj[3]+",");
						dataSbf.append("\"CurrentDegreeOfBalance\":"+totalObj[4]+",");
						dataSbf.append("\"ResultBalance\": {\"EveryBalance\": [");
						String[] balanceArr2=totalObj[5].toString().split(";");
						for(int k=0;k<balanceArr2.length;k++){
							String[] everyBalance=balanceArr2[k].split(",");
							dataSbf.append("{\"Name\": \"\",");
							dataSbf.append("\"Position\":"+everyBalance[0]+",");
							dataSbf.append("\"Weight\":"+everyBalance[1]+"},");
						}
						if(balanceArr2.length>0)
							dataSbf.deleteCharAt(dataSbf.length() - 1);
						dataSbf.append("]}},");
					}
				}
				if(haveRecord)
					dataSbf.deleteCharAt(dataSbf.length() - 1);
				dataSbf.append("]}");
				if(haveRecord){
					cycleQequestDataList.add(dataSbf.toString());
				}
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		return cycleQequestDataList;
	}
	
	public boolean saveBalanceTotalCalculationData(CycleEvaluaion cycleEvaluaion,int type) throws SQLException, ParseException{
		StringBuffer currentBalance = new StringBuffer();
		StringBuffer optBalance = new StringBuffer();
		for(int i=0;i<cycleEvaluaion.getCurrentBalance().getEveryBalance().size();i++){
			currentBalance.append(cycleEvaluaion.getCurrentBalance().getEveryBalance().get(i).getPosition()+
					","+cycleEvaluaion.getCurrentBalance().getEveryBalance().get(i).getWeight()+";");
		}
		if(cycleEvaluaion.getCurrentBalance().getEveryBalance().size()>0){
			currentBalance.deleteCharAt(currentBalance.length() - 1);
		}
		for(int i=0;i<cycleEvaluaion.getEvaluationBalance().getEveryBalance().size();i++){
			optBalance.append(cycleEvaluaion.getEvaluationBalance().getEveryBalance().get(i).getPosition()+
					","+cycleEvaluaion.getEvaluationBalance().getEveryBalance().get(i).getWeight()+";");
		}
		if(cycleEvaluaion.getEvaluationBalance().getEveryBalance().size()>0){
			optBalance.deleteCharAt(optBalance.length() - 1);
		}
		return this.getBaseDao().saveBalanceTotalCalculationData(cycleEvaluaion,currentBalance.toString(),optBalance.toString(),type);
	}
	
	public boolean saveBalanceCycleCalculationData(CycleEvaluaion cycleEvaluaion,int type) throws SQLException, ParseException{
		StringBuffer currentBalance = new StringBuffer();
		StringBuffer optBalance = new StringBuffer();
		for(int i=0;i<cycleEvaluaion.getCurrentBalance().getEveryBalance().size();i++){
			currentBalance.append(cycleEvaluaion.getCurrentBalance().getEveryBalance().get(i).getPosition()+
					","+cycleEvaluaion.getCurrentBalance().getEveryBalance().get(i).getWeight()+";");
		}
		if(cycleEvaluaion.getCurrentBalance().getEveryBalance().size()>0){
			currentBalance.deleteCharAt(currentBalance.length() - 1);
		}
		for(int i=0;i<cycleEvaluaion.getEvaluationBalance().getEveryBalance().size();i++){
			optBalance.append(cycleEvaluaion.getEvaluationBalance().getEveryBalance().get(i).getPosition()+
					","+cycleEvaluaion.getEvaluationBalance().getEveryBalance().get(i).getWeight()+";");
		}
		if(cycleEvaluaion.getEvaluationBalance().getEveryBalance().size()>0){
			optBalance.deleteCharAt(optBalance.length() - 1);
		}
		return this.getBaseDao().saveBalanceCycleCalculationData(cycleEvaluaion,currentBalance.toString(),optBalance.toString(),type);
	}
}
