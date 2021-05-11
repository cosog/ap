package com.gao.thread.calculate;

import java.sql.SQLException;
import java.util.List;

import com.gao.model.calculate.PCPCalculateRequestData;
import com.gao.model.calculate.PCPCalculateResponseData;
import com.gao.model.calculate.RPCCalculateRequestData;
import com.gao.model.calculate.RPCCalculateResponseData;
import com.gao.model.calculate.CommResponseData;
import com.gao.model.calculate.ElectricCalculateResponseData;
import com.gao.model.calculate.TimeEffResponseData;
import com.gao.service.base.CommonDataService;
import com.gao.service.datainterface.CalculateDataService;
import com.gao.utils.Config;
import com.gao.utils.Config2;
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
		System.out.println("线程"+threadId+"开始计算"+wellNo+"井");
		String url[]=Config.getInstance().configFile.getAgileCalculate().getFESDiagram();
		String screwPumpCalUrl[]=Config.getInstance().configFile.getAgileCalculate().getPcpProduction();
		CommonDataService commonDataService=new CommonDataService();
		String sql="select * from ("
				+ "select t3.wellname,t3.liftingtype,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss'),"
				+ " t2.crudeOilDensity,t2.waterDensity,t2.naturalGasRelativeDensity,t2.saturationPressure,t2.reservoirdepth,t2.reservoirtemperature,"
				+ " t2.rodstring,"
				+ " t2.tubingstringinsidediameter,"
				+ " t2.pumptype,t2.barreltype,t2.pumpgrade,t2.plungerlength,t2.pumpborediameter,"
				+ " t2.casingstringinsidediameter,"
				+ " t2.watercut,t2.productiongasoilratio,t2.tubingpressure,t2.casingpressure,t2.wellheadfluidtemperature,"
				+ " t2.producingfluidlevel,t2.pumpsettingdepth,"
				+ " decode(t.resultstatus,2,t.levelcorrectvalue,t3.levelcorrectvalue) as levelcorrectvalue,"
				+ " t2.netgrossratio,"
				+ " t.stroke,t.spm,"
				+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
				+ " decode(t.datasource,1,1,0) as datasource,"
				+ " 0 as manualInterventionCode,"
				+ " t.resultstatus,t.id"
				+ " from tbl_rpc_diagram_hist t,tbl_rpc_productiondata_hist t2,tbl_wellinformation t3"
				+ " where t.wellid=t3.id and t.productiondataid=t2.id  "
				+ " and t.resultstatus in (0,2)  "
				+ " and t.wellid="+wellNo+""
				+ " order by t.acqTime "
				+ " ) v where rownum<=100";
		String totalDateSql="select distinct(to_char(t.acqTime,'yyyy-mm-dd'))"
				+ " from tbl_rpc_diagram_hist t,tbl_rpc_productiondata_hist t2,tbl_wellinformation t3"
				+ " where t.wellid=t3.id and t.productiondataid=t2.id  "
				+ " and t.resultstatus in (0,2)  "
				+ " and t.wellid="+wellNo+"";
		List<?> list = calculateDataService.findCallSql(sql);
		List<?> totalDateList = calculateDataService.findCallSql(totalDateSql);
		Gson gson = new Gson();
		for(int i=0;i<list.size();i++){
			try{
				Object[] obj=(Object[])list.get(i);
				//诊断计产
				String requestData=calculateDataService.getObjectToRPCCalculateRequestData(obj);
//				System.out.println(requestData);
				java.lang.reflect.Type type = new TypeToken<RPCCalculateRequestData>() {}.getType();
				RPCCalculateRequestData calculateRequestData=gson.fromJson(requestData, type);
				String responseData="";
				if(calculateRequestData!=null){
					if(StringManagerUtils.stringToInteger(obj[1]+"")>=400&&StringManagerUtils.stringToInteger(obj[1]+"")<500){//举升类型为螺杆泵时
						responseData=StringManagerUtils.sendPostMethod(screwPumpCalUrl[i%(screwPumpCalUrl.length)], requestData,"utf-8");
					}else{
						responseData=StringManagerUtils.sendPostMethod(url[i%(url.length)], requestData,"utf-8");
					}
					type = new TypeToken<RPCCalculateResponseData>() {}.getType();
					RPCCalculateResponseData calculateResponseData=gson.fromJson(responseData, type);
					
					int jlbh=Integer.parseInt(obj[obj.length-1].toString());
					if(calculateResponseData==null){
						System.out.println("记录:"+jlbh+"计算无数据返回");
					}
					calculateDataService.saveCalculateResult(jlbh,calculateResponseData);
					String totalUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/FSDiagramDailyCalculation";
					totalUrl+="?date="+(obj[1]+"").split(" ")[0];
					totalUrl+="&wellId="+wellNo;
					totalUrl+="&endAcqTime="+obj[1];
					StringManagerUtils.sendPostMethod(totalUrl, "","utf-8");
				}
			}catch(Exception e){
				continue;
			}
		}
		
		for(int i=0;i<totalDateList.size();i++){
			String remainSql="select count(1)"
					+ " from tbl_rpc_diagram_hist t,tbl_rpc_productiondata_hist t2,tbl_wellinformation t3"
					+ " where t.wellid=t3.id and t.productiondataid=t2.id  "
					+ " and t.resultstatus in (0,2)  "
					+ " and to_char(t.acqTime,'yyyy-mm-dd')='"+totalDateList.get(i)+"'"
					+ " and t.wellid="+wellNo+"";
			int remainTotals=calculateDataService.getTotalCountRows(remainSql);
			if(remainTotals==0){
				String totalUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/FSDiagramDailyCalculation";
				totalUrl+="?date="+totalDateList.get(i);
				totalUrl+="&wellId="+wellNo;
				StringManagerUtils.sendPostMethod(totalUrl, "","utf-8");
				
				totalUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/FSDiagramDailyCalculation";
				totalUrl+="?date="+StringManagerUtils.getCurrentTime();
				totalUrl+="&wellId="+wellNo;
				StringManagerUtils.sendPostMethod(totalUrl, "","utf-8");
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
