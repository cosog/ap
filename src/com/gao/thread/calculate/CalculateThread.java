package com.gao.thread.calculate;

import java.sql.SQLException;
import java.util.List;

import com.gao.model.calculate.PCPCalculateRequestData;
import com.gao.model.calculate.PCPCalculateResponseData;
import com.gao.model.calculate.RPCCalculateResponseData;
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
		String totalDate = StringManagerUtils.getCurrentTime();
		CommonDataService commonDataService=new CommonDataService();
		totalUrl+="?date="+totalDate;
		String wellName="";
		String sql="select * from ("
				+ "select t3.wellname,t3.liftingtype,to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss'),"
				+ " t2.crudeOilDensity,t2.waterDensity,t2.naturalGasRelativeDensity,t2.saturationPressure,t2.reservoirdepth,t2.reservoirtemperature,"
				+ " t2.rodstring,"
				+ " t2.tubingstringinsidediameter,"
				+ " t2.pumptype,t2.pumpgrade,t2.plungerlength,t2.pumpborediameter,"
				+ " t2.casingstringinsidediameter,"
				+ " t2.watercut,t2.productiongasoilratio,t2.tubingpressure,t2.casingpressure,t2.wellheadfluidtemperature,t2.producingfluidlevel,t2.pumpsettingdepth,"
				+ " t2.netgrossratio,"
				+ " t.stroke,t.spm,"
				+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
				+ " 0 as manualInterventionCode,"
				+ " t.tresultstatus,t.id"
				+ " from tbl_rpc_diagram_hist t,tbl_rpc_productiondata_hist t2,tbl_wellinformation t3"
				+ " where t.wellid=t3.id and t.productiondataid=t2.id  "
				+ " and tresultstatus in (0,2)  "
				+ " and t.wellid="+wellNo+""
				+ " order by t.acquisitiontime "
				+ " ) v where rownum<=100";
		String sqlAll="select count(1) from tbl_rpc_diagram_hist t  where t.jsbz tresultstatus (0,2) and wellid="+wellNo;
		List<?> list = calculateDataService.findCallSql(sql);
		Gson gson = new Gson();
		for(int i=0;i<list.size();i++){
			try{
				Object[] obj=(Object[])list.get(i);
				wellName=obj[0]+"";
				//诊断计产
				String requestData=calculateDataService.getObjectToRPCCalculateRequestData(obj);
				java.lang.reflect.Type type = new TypeToken<PCPCalculateRequestData>() {}.getType();
				PCPCalculateRequestData calculateRequestData=gson.fromJson(requestData, type);
				String responseData="";
				if(calculateRequestData!=null){
					if(calculateRequestData.getLiftingType()>=400&&calculateRequestData.getLiftingType()<500){//举升类型为螺杆泵时
						responseData=StringManagerUtils.sendPostMethod(screwPumpCalUrl[i%(screwPumpCalUrl.length)], requestData,"utf-8");
					}else{
						responseData=StringManagerUtils.sendPostMethod(url[i%(url.length)], requestData,"utf-8");
					}
					type = new TypeToken<RPCCalculateResponseData>() {}.getType();
					RPCCalculateResponseData calculateResponseData=gson.fromJson(responseData, type);
					
					if(calculateResponseData==null){
						int jlbh=Integer.parseInt(obj[obj.length-1].toString());
						System.out.println("记录:"+jlbh+"计算无数据返回");
					}else{
						int jlbh=Integer.parseInt(obj[obj.length-1].toString());
						calculateDataService.saveCalculateResult(jlbh,calculateResponseData);
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
