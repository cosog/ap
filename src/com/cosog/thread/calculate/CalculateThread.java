package com.cosog.thread.calculate;

import java.util.List;

import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.PCPCalculateResponseData;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.RPCCalculateResponseData;
import com.cosog.service.datainterface.CalculateDataService;
import com.cosog.utils.Config;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CalculateThread extends Thread{

	private int threadId;
	private int wellNo;
	private int deviceType;
	private CalculateDataService<?> calculateDataService=null;

	public CalculateThread(int threadId, int wellNo,int deviceType, CalculateDataService<?> calculateManagerService) {
		super();
		this.threadId = threadId;
		this.wellNo = wellNo;
		this.deviceType = deviceType;
		this.calculateDataService = calculateManagerService;
	}

	public void run(){
		System.out.println("线程"+threadId+"开始计算"+(deviceType==0?"抽油机":"螺杆泵")+"编号"+wellNo+"井");
		Gson gson = new Gson();
		if(deviceType==0){
			String url[]=Config.getInstance().configFile.getAc().getFESDiagram();
			String sql="select * from ("
					+ " select t2.wellname,to_char(t.fesdiagramacqTime,'yyyy-mm-dd hh24:mi:ss'),"
					+ " t.stroke,t.spm,"
					+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
					+ " t.levelcorrectvalue,"
					+ " t.productiondata,"
					+ " t3.id as pumpingmodelid,t3.manufacturer,t3.model,t3.crankrotationdirection,t3.offsetangleofcrank,t3.crankgravityradius,t3.singlecrankweight,t3.singlecrankpinweight,t3.structuralunbalance,"
					+ " t.balanceinfo,"
					+ " t.id"
					+ " from tbl_rpcacqdata_hist t"
					+ " left outer join tbl_rpcdevice t2 on t.wellid=t2.id"
					+ " left outer join tbl_pumpingmodel t3 on t3.id=t.pumpingmodelid"
					+ " where 1=1  "
					+ " and t.resultstatus in (0,2)  "
					+ " and t.wellid="+wellNo+""
					+ " order by t.fesdiagramacqTime "
					+ " ) v where rownum<=100";
			List<?> list = calculateDataService.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				try{
					Object[] obj=(Object[])list.get(i);
					//诊断计产
					String requestData=calculateDataService.getObjectToRPCCalculateRequestData(obj);
					java.lang.reflect.Type type = new TypeToken<RPCCalculateRequestData>() {}.getType();
					RPCCalculateRequestData calculateRequestData=gson.fromJson(requestData, type);
					String responseData="";
					if(calculateRequestData!=null){
						responseData=StringManagerUtils.sendPostMethod(url[i%(url.length)], requestData,"utf-8");
						type = new TypeToken<RPCCalculateResponseData>() {}.getType();
						RPCCalculateResponseData calculateResponseData=gson.fromJson(responseData, type);
						
						int recordId=Integer.parseInt(obj[obj.length-1].toString());
						if(calculateResponseData==null){
							System.out.println("记录:"+recordId+"计算无数据返回");
						}
						calculateDataService.getBaseDao().saveFESDiagramCalculateResult(recordId,calculateResponseData);
					}
				}catch(Exception e){
					continue;
				}
			}
		
		}else{
			String url[]=Config.getInstance().configFile.getAc().getPcpProduction();
			String sql="select * from ("
					+ " select t2.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss'),"
					+ " t.rpm,t.productiondata,"
					+ " t.id"
					+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2"
					+ " where t.wellid=t2.id  "
					+ " and t.resultstatus in (0,2)  "
					+ " and t.wellid="+wellNo+""
					+ " order by t.acqTime "
					+ " ) v where rownum<=100";
			List<?> list = calculateDataService.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				try{
					Object[] obj=(Object[])list.get(i);
					//转速计产
					String requestData=calculateDataService.getObjectToRPMCalculateRequestData(obj);
					
					java.lang.reflect.Type type = new TypeToken<PCPCalculateRequestData>() {}.getType();
					PCPCalculateRequestData calculateRequestData=gson.fromJson(requestData, type);
					String responseData="";
					if(calculateRequestData!=null){
						responseData=StringManagerUtils.sendPostMethod(url[i%(url.length)], requestData,"utf-8");
						type = new TypeToken<PCPCalculateResponseData>() {}.getType();
						PCPCalculateResponseData calculateResponseData=gson.fromJson(responseData, type);
						
						int recordId=Integer.parseInt(obj[obj.length-1].toString());
						if(calculateResponseData==null){
							System.out.println("螺杆泵：记录:"+recordId+"计算无数据返回");
						}
						calculateDataService.getBaseDao().saveRPMCalculateResult(recordId,calculateResponseData);
					}
				}catch(Exception e){
					continue;
				}
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
