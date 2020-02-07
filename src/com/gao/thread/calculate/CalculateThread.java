package com.gao.thread.calculate;

import java.sql.SQLException;
import java.util.List;

import com.gao.model.calculate.CalculateRequestData;
import com.gao.model.calculate.CalculateResponseData;
import com.gao.model.calculate.CommResponseData;
import com.gao.model.calculate.ElectricCalculateResponseData;
import com.gao.model.calculate.TimeEffResponseData;
import com.gao.service.base.CommonDataService;
import com.gao.service.datainterface.CalculateDataService;
import com.gao.utils.Config;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CalculateThread extends Thread{

	private int threadId;
	private int wellNo;
	private CalculateDataService<?> calculateDataService=null;

	public CalculateThread(int threadId, int wellNo, CalculateDataService<?> calculateDataService) {
		super();
		this.threadId = threadId;
		this.wellNo = wellNo;
		this.calculateDataService = calculateDataService;
	}

	public void run(){
		String url[]=Config.getCalculateHttpServerURL().split(",");
		String screwPumpCalUrl[]=Config.getScrewPumpCalculateHttpServerURL().split(",");
		String totalUrl=Config.getProjectAccessPath()+"/calculateDataController/totalCalculation";
		String elecCalUrl[]=Config.getPumpingunitElecCalculateHttpServerURL().split(",");
		String screwPumpElecCalUrl[]=Config.getScrewpumpElecCalculateHttpServerURL().split(",");
		String tiemEffUrl[]=Config.getTimeEfficiencyHttpServerURL().split(",");
		String commCalUrl[]=Config.getCommHttpServerURL().split(",");
		String totalDate = StringManagerUtils.getCurrentTime();
		CommonDataService commonDataService=new CommonDataService();
		totalUrl+="?date="+totalDate;
		String wellName="";
		String sql="select * from ("
				+ "select t007.jh,t007.jslx,to_char(t033.gtcjsj,'yyyy-mm-dd hh24:mi:ss'),"
				+ " t033.yymd,t033.smd,t033.trqxdmd,t033.bhyl,t033.yqcyl,t033.yqczbsd,t033.yqczbwd,"
				+ " t033.yjgjb,t033.yjgcd,t033.yjgj,t033.ejgjb,t033.ejgcd,t033.ejgj,t033.sjgjb,t033.sjgcd,t033.sjgj,t033.sijgjb,t033.sijgcd,t033.sijgj,"
				+ " t033.ygnj,"
				+ " t033.blx,t033.bjb,t033.zsc,t033.bj,"
				+ " t033.yctgnj,t033.hsl,t033.scqyb,t033.yy,t033.ty,t033.jklw,t011.dym,t033.bg,t033.jmb,"
				+ " t010.gtsj,t010.power_curve,t010.current_curve,decode(t033.gkjgly,0,0,t033.gklx) ,"
				+ " t033.yjgnj,t033.ejgnj,t033.sjgnj,t033.sijgnj,"
				+ " t033.currenta,t033.currentb,t033.currentc,t033.voltagea,t033.voltageb,t033.voltagec,"
				+ " t033.activepower,t033.reactivepower,t033.powerfactor,t033.activepowerconsumption,t033.reactivepowerconsumption,"
				+ " t033.rpm,t033.torque,t033.btc,t033.bjs,t033.zzjmzj,t033.gcpl,"
				+ " t007.slly,t033.yxzt,"
				+ " t033.jsbz,t033.jlbh"
				+ " from t_wellinformation t007, t_indicatordiagram t010,"
				+ " t_dynamicliquidlevel t011,t_outputwellhistory t033  "
				+ " where t007.jlbh=t010.jbh and t033.jbh=t007.jlbh and t033.gtbh=t010.jlbh  "
				+ " and t033.dymbh=t011.jlbh  "
				+ " and t033.jsbz in (0,2)  "
				+ " and t033.jbh="+wellNo+""
				+ " order by t033.gtcjsj "
				+ " ) v where rownum<=100";
		String sqlAll="select count(1) from t_outputwellhistory t033  where t033.jsbz in (0,2) and jbh="+wellNo;
		List<?> list = calculateDataService.findCallSql(sql);
		Gson gson = new Gson();
		for(int i=0;i<list.size();i++){
			try{
				Object[] obj=(Object[])list.get(i);
				wellName=obj[0]+"";
				//诊断计产
				String requestData=calculateDataService.getObjectToCalculateRequestData(obj);
				java.lang.reflect.Type type = new TypeToken<CalculateRequestData>() {}.getType();
				CalculateRequestData calculateRequestData=gson.fromJson(requestData, type);
				String responseData="";
				if(calculateRequestData!=null){
					if(calculateRequestData.getLiftingType()>=400&&calculateRequestData.getLiftingType()<500){//举升类型为螺杆泵时
						responseData=StringManagerUtils.sendPostMethod(screwPumpCalUrl[i%(screwPumpCalUrl.length)], requestData,"utf-8");
					}else{
						responseData=StringManagerUtils.sendPostMethod(url[i%(url.length)], requestData,"utf-8");
					}
					type = new TypeToken<CalculateResponseData>() {}.getType();
					CalculateResponseData calculateResponseData=gson.fromJson(responseData, type);
					
					ElectricCalculateResponseData electricCalculateResponseData=null;
					TimeEffResponseData timeEffResponseData=null;
					CommResponseData commResponseData=null;
					if("0".equals(obj[63]+"")){//如果计算标志为0
						//电参诊断
						String elecRequestData=calculateDataService.getObjectToElecCalculateRequestData(obj);
						String elecCalResponse="";
						if(calculateRequestData.getLiftingType()>=400&&calculateRequestData.getLiftingType()<500){//举升类型为螺杆泵时
							elecCalResponse=StringManagerUtils.sendPostMethod(screwPumpElecCalUrl[i%(screwPumpElecCalUrl.length)], elecRequestData,"utf-8");
						}else{
							elecCalResponse=StringManagerUtils.sendPostMethod(elecCalUrl[i%(elecCalUrl.length)], elecRequestData,"utf-8");
						}
						type = new TypeToken<ElectricCalculateResponseData>() {}.getType();
						electricCalculateResponseData=gson.fromJson(elecCalResponse, type);
						//时率计算
						int runStatus=1;
						if(!"0".equals(obj[61]+"")){//时率来源为人工录入时，不进行时率计算
							if("1".equals(obj[61]+"")){//时率来源为DI信号时
								runStatus=StringManagerUtils.StringToInteger(obj[62]+"");
							}else if("2".equals(obj[61]+"")&&electricCalculateResponseData!=null&&electricCalculateResponseData.getResultStatus()==1){//时率来源为电参计算时
								runStatus=electricCalculateResponseData.getRunStatus();
							}else if("3".equals(obj[61]+"")&&calculateResponseData!=null&&calculateResponseData.getCalculationStatus().getResultStatus()==1){//时率来源为转速计算时
								runStatus=calculateResponseData.getRunStatus();
							}
							String timeRequestData=calculateDataService.getObjectToTimeEffCalculateRequestData(obj,runStatus);
							String timeCalResponse=StringManagerUtils.sendPostMethod(tiemEffUrl[i%(tiemEffUrl.length)], timeRequestData,"utf-8");
							type = new TypeToken<TimeEffResponseData>() {}.getType();
							timeEffResponseData=gson.fromJson(timeCalResponse, type);
						}
						
						//通信计算
						String commRequestData=calculateDataService.getObjectToCommCalculateRequestData(obj);
						String commCalResponse=StringManagerUtils.sendPostMethod(commCalUrl[i%(commCalUrl.length)], commRequestData,"utf-8");
						type = new TypeToken<CommResponseData>() {}.getType();
						commResponseData=gson.fromJson(commCalResponse, type);
					}
					
					if(calculateResponseData==null){
						int jlbh=Integer.parseInt(obj[obj.length-1].toString());
						System.out.println("记录:"+jlbh+"计算无数据返回");
					}else{
						int jlbh=Integer.parseInt(obj[obj.length-1].toString());
						calculateDataService.saveCalculateResult(jlbh,calculateResponseData,electricCalculateResponseData,timeEffResponseData,commResponseData);
					}
				}
			}catch(Exception e){
				continue;
			}
		}
		if(list.size()>0&&StringManagerUtils.isNotNull(wellName)){
			totalUrl+="&jh="+wellName;
			int totals=commonDataService.getTotalCountRows(sqlAll);
			if(totals==0){//全部计算完成  汇总
				StringManagerUtils.sendPostMethod(totalUrl, "","utf-8");
			}
		}else{
			try {
				calculateDataService.deleteInvalidData(wellNo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	}


	public int getThreadId() {
		return threadId;
	}


	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}


	public int getWellNo() {
		return wellNo;
	}


	public void setWellNo(int wellNo) {
		this.wellNo = wellNo;
	}


	public CalculateDataService<?> getCalculateDataService() {
		return calculateDataService;
	}


	public void setCalculateDataService(CalculateDataService<?> calculateDataService) {
		this.calculateDataService = calculateDataService;
	}
	
}
