package com.gao.thread.calculate;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import com.gao.model.balance.BalanceRequestData;
import com.gao.model.balance.BalanceResponseData;
import com.gao.service.datainterface.BalanceDataAInterfaceService;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BalanceTorqueMethodCalculateThread extends Thread{

	private int threadId;
	private Object[] object;
	private String CalculateHttpURL;
	private BalanceDataAInterfaceService<?> balanceDataAInterfaceService;

	public BalanceTorqueMethodCalculateThread(int threadId, Object[] object, String calculateHttpURL,
			BalanceDataAInterfaceService<?> balanceDataAInterfaceService) {
		super();
		this.threadId = threadId;
		this.object = object;
		CalculateHttpURL = calculateHttpURL;
		this.balanceDataAInterfaceService = balanceDataAInterfaceService;
	}


	public void run(){
		Gson gson = new Gson();
		String requestData="";
		String responseData="";
		try {
			requestData=balanceDataAInterfaceService.getObjectToBalanceRequestData(object);
//			System.out.println(requestData);
			int i=0;
			do{
				responseData=StringManagerUtils.sendPostMethod(CalculateHttpURL, requestData,"utf-8");
				i++;
			}while((!StringManagerUtils.isNotNull(responseData))&&i<=5);
			java.lang.reflect.Type type = new TypeToken<BalanceResponseData>() {}.getType();
			BalanceResponseData balanceResponseData=gson.fromJson(responseData, type);
			if(balanceResponseData==null){
//				System.out.println("记录数:"+object[object.length-1]+"返回结果为空");
			}else{
				type = new TypeToken<BalanceRequestData>() {}.getType();
				BalanceRequestData balanceRequestData=gson.fromJson(requestData, type);
				int jlbh=Integer.parseInt(object[0].toString());
				balanceDataAInterfaceService.saveBalanceResponseData(jlbh,balanceRequestData.getFSDiagram().getStroke(),balanceRequestData.getFSDiagram().getSPM(),balanceResponseData);
			}
		} catch (SQLException | IOException | ParseException e) {
			// TODO Auto-generated catch block
//			System.out.println("线程"+threadId+"中记录"+object[0]+"计算失败"+e.toString()+"返回数据:"+responseData);
			e.printStackTrace();
		}
	}
	
	
	public Object[] getObject() {
		return object;
	}

	public void setObject(Object[] object) {
		this.object = object;
	}

	

	public String getCalculateHttpURL() {
		return CalculateHttpURL;
	}

	public void setCalculateHttpURL(String calculateHttpURL) {
		CalculateHttpURL = calculateHttpURL;
	}


	public int getThreadId() {
		return threadId;
	}


	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}


	public BalanceDataAInterfaceService<?> getBalanceDataAInterfaceService() {
		return balanceDataAInterfaceService;
	}


	public void setBalanceDataAInterfaceService(BalanceDataAInterfaceService<?> balanceDataAInterfaceService) {
		this.balanceDataAInterfaceService = balanceDataAInterfaceService;
	}
	
	
}
