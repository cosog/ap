package com.cosog.thread.calculate;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.cosog.model.calculate.TotalAnalysisRequestData;
import com.cosog.model.calculate.TotalAnalysisResponseData;
import com.cosog.service.datainterface.CalculateDataService;
import com.cosog.utils.Config;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TotalCalculateThread extends Thread{
	private int threadId;
	private int wellNo;
	private int liftingType;
	private String tatalDate;
	private String endAcqTime;
	private CalculateDataService<?> calculateDataService=null;
	public TotalCalculateThread(int threadId, int wellNo, int liftingType,String tatalDate,String endAcqTime,
			CalculateDataService<?> calculateDataService) {
		super();
		this.threadId = threadId;
		this.wellNo = wellNo;
		this.liftingType = liftingType;
		this.tatalDate=tatalDate;
		this.endAcqTime=endAcqTime;
		this.calculateDataService = calculateDataService;
	}
	
	public void run(){
		List<String> requestDataList=null;
		List<String> discreteRequestDataList=null;
		Gson gson = new Gson();
		java.lang.reflect.Type typeRequest=null;
		String url=Config.getInstance().configFile.getAgileCalculate().getTotalCalculation().getWell()[0];
		try {
			if(liftingType>=200 && liftingType<300){//抽油机
				if(StringManagerUtils.isNotNull(endAcqTime)){
					requestDataList=calculateDataService.getFSDiagramDailyCalculationRequestData(tatalDate,wellNo+"",endAcqTime);
				}else{
					requestDataList=calculateDataService.getFSDiagramDailyCalculationRequestData(tatalDate,wellNo+"");
				}
				discreteRequestDataList=calculateDataService.getDiscreteDailyCalculation(tatalDate,wellNo+"");
			}else{//螺杆泵
				requestDataList=calculateDataService.getPCPRPMDailyCalculationRequestData(tatalDate,wellNo+"");
				discreteRequestDataList=calculateDataService.getPCPDiscreteDailyCalculation(tatalDate,wellNo+"");
			}
			for(int i=0;i<requestDataList.size();i++){
				gson = new Gson();
				typeRequest = new TypeToken<TotalAnalysisRequestData>() {}.getType();
				TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(requestDataList.get(i), typeRequest);
				String responseData=StringManagerUtils.sendPostMethod(url, requestDataList.get(i),"utf-8");
				java.lang.reflect.Type type = new TypeToken<TotalAnalysisResponseData>() {}.getType();
				TotalAnalysisResponseData totalAnalysisResponseData = gson.fromJson(responseData, type);
				
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					if(liftingType>=200 && liftingType<300){
						calculateDataService.saveFSDiagramDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData,tatalDate);
					}else{
						calculateDataService.savePCPRPMDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData,tatalDate);
					}
				}else{
					System.out.println("周期数据汇总error:"+requestDataList.get(i));
				}
			}
			for(int i=0;i<discreteRequestDataList.size();i++){
				gson = new Gson();
				typeRequest = new TypeToken<TotalAnalysisRequestData>() {}.getType();
				TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(discreteRequestDataList.get(i), typeRequest);
				String responseData=StringManagerUtils.sendPostMethod(url, requestDataList.get(i),"utf-8");
				java.lang.reflect.Type type = new TypeToken<TotalAnalysisResponseData>() {}.getType();
				TotalAnalysisResponseData totalAnalysisResponseData = gson.fromJson(responseData, type);
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					if(liftingType>=200 && liftingType<300){
						calculateDataService.saveDiscreteDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData,tatalDate);
					}else{
						calculateDataService.savePCPDiscreteDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData,tatalDate);
					}
					
				}else{
					System.out.println("离散数据汇总error:"+requestDataList.get(i));
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
