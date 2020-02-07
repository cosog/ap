package com.gao.controller.datainterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.balance.BalanceRequestData;
import com.gao.model.balance.BalanceResponseData;
import com.gao.model.balance.CycleEvaluaion;
import com.gao.model.balance.TorqueBalanceResponseData;
import com.gao.service.datainterface.BalanceDataAInterfaceService;
import com.gao.thread.calculate.BalanceTorqueMethodCalculateThread;
import com.gao.utils.Config;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/balanceDataAInterfaceController")
@Scope("prototype")
public class BalanceDataAInterfaceController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	BalanceDataAInterfaceService<?> balanceDataAInterfaceService;
	
	@RequestMapping("/getBalanceTorqueCalulate")
	public String getBalanceTorqueCalulate() throws SQLException, IOException, ParseException, InterruptedException{
		String url=Config.getBalanceTorqueHttpServerURL();
		String urlArr[]=url.split(",");
		String json="";
		long startTime=0;
		long endTime=0;
		long allTime=0;
		
		
//		Gson gson = new Gson();
//		String responseData= StringManagerUtils.ReadFile("e:/BalanceResponseStruct.json").replaceAll(" ", "");
//		System.out.println(responseData);
//		java.lang.reflect.Type type = new TypeToken<BalanceResponseData>() {}.getType();
//		BalanceResponseData balanceResponseData=gson.fromJson(responseData, type);
//		if(balanceResponseData!=null){
//			balanceDataAInterfaceService.saveBalanceResponseData(111111,3.5f,4.2f,balanceResponseData);
//		}
		
		
		BalanceTorqueMethodCalculateThread balanceTorqueMethodCalculateThreadList[]=new BalanceTorqueMethodCalculateThread[20];
		for(int i=0;i<20;i++){
			balanceTorqueMethodCalculateThreadList[i]=null;
		}
		String sql="select * from ("
				+ " select t.jlbh,t007.jh,t020.manufacturer,t020.model,"
				+ " case when t020.type>=200 and t020.type<300 then 2 when t020.type>=300 then 3 else 1 end as type,"
				+ " t020.crankrotationdirection,t020.offsetangleofcrank,"
				+ " t020.crankgravityradius,t020.singlecrankweight,t020.balancemaxmovespace,t020.structuralunbalance,t020.maxcnt,t.currentbalance,"
				+ " to_char(t010.cjsj,'yyyy-mm-dd hh24:mi:ss') as gtcjsj,t010.gtsj,"
				+ " t.fourbarlinkageefficiency,t.motorefficiency,t.beltefficiency,t.gearreducerefficiency,t.ktzt  "
				+ " from t_201_balanceanalysis t,t_pumpingunit t020,t_wellinformation t007,t_indicatordiagram t010 "
				+ " where t.cyjbh=t020.jlbh and t.jbh=t007.jlbh and t.gtbh=t010.jlbh and t.calculatetype=2 and  jsbz=0"
				+ ") v where rownum<=1000";
		
		List<?> list = balanceDataAInterfaceService.findCallSql(sql);
		
		startTime=new Date().getTime();
		for(int i=0;i<list.size();i++){
			Gson gson = new Gson();
			String requestData="";
			requestData=balanceDataAInterfaceService.getObjectToBalanceRequestData((Object[])list.get(i));
			String responseData=StringManagerUtils.sendPostMethod(urlArr[i%(urlArr.length)], requestData,"utf-8");
//			System.out.println(responseData);
			java.lang.reflect.Type type = new TypeToken<BalanceResponseData>() {}.getType();
			BalanceResponseData balanceResponseData=gson.fromJson(responseData, type);
			if(balanceResponseData!=null){
				int jlbh=Integer.parseInt(((Object[])list.get(i))[0].toString());
				type = new TypeToken<BalanceRequestData>() {}.getType();
				BalanceRequestData balanceRequestData=gson.fromJson(requestData, type);
				balanceDataAInterfaceService.saveBalanceResponseData(jlbh,balanceRequestData.getFSDiagram().getStroke(),balanceRequestData.getFSDiagram().getSPM(),balanceResponseData);
			}else{
				System.out.println("记录"+((Object[])list.get(i))[0]+"计算失败。");
			}
//			boolean isCalculate=false;
//			while(!isCalculate){
//				for(int j=0;j<balanceTorqueMethodCalculateThreadList.length;j++){
//					if(balanceTorqueMethodCalculateThreadList[j]==null||(!balanceTorqueMethodCalculateThreadList[j].isAlive())){
//						balanceTorqueMethodCalculateThreadList[j]=null;
//						balanceTorqueMethodCalculateThreadList[j]=new BalanceTorqueMethodCalculateThread(j,(Object[])list.get(i),urlArr[i%(urlArr.length)],balanceDataAInterfaceService);
//						balanceTorqueMethodCalculateThreadList[j].start();
//						isCalculate=true;
//						break;
//					}
//				}
//			}
		}
		endTime=new Date().getTime();
		allTime=endTime-startTime;
		json=list.size()+"条记录计算完成，共用时:"+allTime+"毫秒";
		System.out.println(list.size()+"条记录计算完成，共用时:"+allTime+"毫秒");
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/balanceTotalCalculation")
	public String balanceTotalCalculation() throws ParseException{
		List<String> maxValueRequestDataList=balanceDataAInterfaceService.getTotalCalculationDataList(2);
		List<String> netTorqueRequestDataList=balanceDataAInterfaceService.getTotalCalculationDataList(1);
		List<String> averagePowerRequestDataList=balanceDataAInterfaceService.getTotalCalculationDataList(1);
		String url=Config.getBalanceCycleHttpServerURL();
		for(int i=0;i<maxValueRequestDataList.size();i++){//TotalCalculateResponseData
			try {
				String responseData=StringManagerUtils.sendPostMethod(url, maxValueRequestDataList.get(i),"utf-8");
				Gson gson = new Gson();
				java.lang.reflect.Type modeltype = new TypeToken<CycleEvaluaion>() {}.getType();
				CycleEvaluaion cycleEvaluaion = gson.fromJson(responseData, modeltype);
				if(cycleEvaluaion!=null){
					balanceDataAInterfaceService.saveBalanceTotalCalculationData(cycleEvaluaion,2);
				}
			} catch (Exception e) {
//				e.printStackTrace();
				continue;
			}
		}
		
		for(int i=0;i<netTorqueRequestDataList.size();i++){//TotalCalculateResponseData
			try {
				String responseData=StringManagerUtils.sendPostMethod(url, netTorqueRequestDataList.get(i),"utf-8");
				Gson gson = new Gson();
				java.lang.reflect.Type modeltype = new TypeToken<CycleEvaluaion>() {}.getType();
				CycleEvaluaion cycleEvaluaion = gson.fromJson(responseData, modeltype);
				if(cycleEvaluaion!=null){
					balanceDataAInterfaceService.saveBalanceTotalCalculationData(cycleEvaluaion,1);
				}
			} catch (Exception e) {
//				e.printStackTrace();
				continue;
			}
		}
		
		for(int i=0;i<averagePowerRequestDataList.size();i++){//TotalCalculateResponseData
			try {
				String responseData=StringManagerUtils.sendPostMethod(url, averagePowerRequestDataList.get(i),"utf-8");
				Gson gson = new Gson();
				java.lang.reflect.Type modeltype = new TypeToken<CycleEvaluaion>() {}.getType();
				CycleEvaluaion cycleEvaluaion = gson.fromJson(responseData, modeltype);
				if(cycleEvaluaion!=null){
					balanceDataAInterfaceService.saveBalanceTotalCalculationData(cycleEvaluaion,3);
				}
			} catch (Exception e) {
//				e.printStackTrace();
				continue;
			}
		}
		
		String json ="";
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/balanceCycleCalculation")
	public String BalanceCycleCalculation() throws ParseException{
		String jh = ParamUtils.getParameter(request, "jh");
		List<String> maxValueRequestDataList=balanceDataAInterfaceService.getCycleCalculationDataList(2,jh);
		List<String> netTorqueRequestDataList=balanceDataAInterfaceService.getCycleCalculationDataList(1,jh);
		List<String> avgPowerRequestDataList=balanceDataAInterfaceService.getCycleCalculationDataList(3,jh);
		String url=Config.getBalanceCycleHttpServerURL();
		for(int i=0;i<maxValueRequestDataList.size();i++){//TotalCalculateResponseData
			try {
				String responseData=StringManagerUtils.sendPostMethod(url, maxValueRequestDataList.get(i),"utf-8");
				Gson gson = new Gson();
				java.lang.reflect.Type modeltype = new TypeToken<CycleEvaluaion>() {}.getType();
				CycleEvaluaion cycleEvaluaion = gson.fromJson(responseData, modeltype);
				if(cycleEvaluaion!=null){
					balanceDataAInterfaceService.saveBalanceCycleCalculationData(cycleEvaluaion,2);
				}
			} catch (Exception e) {
//				e.printStackTrace();
				continue;
			}
		}
		
		for(int i=0;i<netTorqueRequestDataList.size();i++){//TotalCalculateResponseData
			try {
				String responseData=StringManagerUtils.sendPostMethod(url, netTorqueRequestDataList.get(i),"utf-8");
				Gson gson = new Gson();
				java.lang.reflect.Type modeltype = new TypeToken<CycleEvaluaion>() {}.getType();
				CycleEvaluaion cycleEvaluaion = gson.fromJson(responseData, modeltype);
				if(cycleEvaluaion!=null){
					balanceDataAInterfaceService.saveBalanceCycleCalculationData(cycleEvaluaion,1);
				}
			} catch (Exception e) {
//				e.printStackTrace();
				continue;
			}
		}
		
		for(int i=0;i<avgPowerRequestDataList.size();i++){//TotalCalculateResponseData
			try {
				String responseData=StringManagerUtils.sendPostMethod(url, avgPowerRequestDataList.get(i),"utf-8");
				Gson gson = new Gson();
				java.lang.reflect.Type modeltype = new TypeToken<CycleEvaluaion>() {}.getType();
				CycleEvaluaion cycleEvaluaion = gson.fromJson(responseData, modeltype);
				if(cycleEvaluaion!=null){
					balanceDataAInterfaceService.saveBalanceCycleCalculationData(cycleEvaluaion,3);
				}
			} catch (Exception e) {
				continue;
			}
		}
		
		String json ="";
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
