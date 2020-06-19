package com.gao.controller.datainterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.calculate.PCPCalculateRequestData;
import com.gao.model.calculate.PCPCalculateResponseData;
import com.gao.model.calculate.RPCCalculateResponseData;
import com.gao.model.calculate.CommResponseData;
import com.gao.model.calculate.ElectricCalculateResponseData;
import com.gao.model.calculate.TimeEffResponseData;
import com.gao.model.calculate.TimeEffTotalResponseData;
import com.gao.model.calculate.TotalAnalysisRequestData;
import com.gao.model.calculate.TotalAnalysisResponseData;
import com.gao.model.calculate.TotalCalculateRequestData;
import com.gao.model.calculate.TotalCalculateResponseData;
import com.gao.service.base.CommonDataService;
import com.gao.service.datainterface.CalculateDataService;
import com.gao.thread.calculate.CalculateThread;
import com.gao.utils.Config;
import com.gao.utils.Config2;
import com.gao.utils.Constants;
import com.gao.utils.OracleJdbcUtis;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

@Controller
@RequestMapping("/calculateDataController")
@Scope("prototype")
public class CalculateDataController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	CalculateDataService<?> calculateDataService;
	@Autowired
	private CommonDataService commonDataService;
	
		
	
	@RequestMapping("/getBatchCalculateTime")
	public String getBatchCalculateTime() throws SQLException, IOException, ParseException, InterruptedException,Exception{
		String url[]=Config.getInstance().configFile.getAgileCalculate().getFESDiagram();
		String screwPumpCalUrl[]=Config.getInstance().configFile.getAgileCalculate().getPcpProduction();
		String json="";
		long startTime=0;
		long endTime=0;
		long allTime=0;
		String totalDate = StringManagerUtils.getCurrentTime();
		CalculateThread calculateThreadList[]=new CalculateThread[20];
		for(int i=0;i<20;i++){
			calculateThreadList[i]=null;
		}
		
		String wellListSql="select distinct(wellid) from tbl_rpc_diagram_hist t  where t.resultstatus in (0,2) and t.productiondataid >0 ";
		String sqlAll="select count(1) from tbl_rpc_diagram_hist t  where t.resultstatus in (0,2) and t.productiondataid >0";
		List<?> wellList = calculateDataService.findCallSql(wellListSql);
		int calCount=0;
		startTime=new Date().getTime();
		for(int j=0;j<wellList.size();j++){
			boolean isCal=false;
			while(!isCal){
				for(int i=0;i<calculateThreadList.length;i++){
					if(calculateThreadList[i]==null || !(calculateThreadList[i].isAlive())){
						calculateThreadList[i]=new CalculateThread(i,StringManagerUtils.stringToInteger(wellList.get(j)+""),calculateDataService);
						calculateThreadList[i].start();
						isCal=true;
						break;
					}
				}
			}
			
//			String wellId=wellList.get(j)+"";
//			String totalUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/FSDiagramDailyCalculation";
//			totalUrl+="?date="+totalDate;
//			totalUrl+="&wellId="+wellId;
//			String sql="select * from ("
//					+ "select t3.wellname,t3.liftingtype,to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss'),"
//					+ " t2.crudeOilDensity,t2.waterDensity,t2.naturalGasRelativeDensity,t2.saturationPressure,t2.reservoirdepth,t2.reservoirtemperature,"
//					+ " t2.rodstring,"
//					+ " t2.tubingstringinsidediameter,"
//					+ " t2.pumptype,t2.pumpgrade,t2.plungerlength,t2.pumpborediameter,"
//					+ " t2.casingstringinsidediameter,"
//					+ " t2.watercut,t2.productiongasoilratio,t2.tubingpressure,t2.casingpressure,t2.wellheadfluidtemperature,t2.producingfluidlevel,t2.pumpsettingdepth,"
//					+ " t2.netgrossratio,"
//					+ " t.stroke,t.spm,"
//					+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
//					+ " 0 as manualInterventionCode,"
//					+ " t.resultstatus,t.id"
//					+ " from tbl_rpc_diagram_hist t,tbl_rpc_productiondata_hist t2,tbl_wellinformation t3"
//					+ " where t.wellid=t3.id and t.productiondataid=t2.id  "
//					+ " and t.resultstatus in (0,2)  "
//					+ " and t.wellid="+wellId+""
//					+ " order by t.acquisitiontime "
//					+ " ) v where rownum<=100";
//			List<?> list = calculateDataService.findCallSql(sql);
//			calCount+=list.size();
//			int jsbz0=0;
//			Gson gson = new Gson();
//			for(int i=0;i<list.size();i++){
//				try{
//					Object[] obj=(Object[])list.get(i);
//					//诊断计产
//					String requestData=calculateDataService.getObjectToRPCCalculateRequestData(obj);
//					java.lang.reflect.Type type = new TypeToken<PCPCalculateRequestData>() {}.getType();
//					PCPCalculateRequestData calculateRequestData=gson.fromJson(requestData, type);
//					String responseData="";
//					if(calculateRequestData!=null){
//						if(calculateRequestData.getLiftingType()>=400&&calculateRequestData.getLiftingType()<500){//举升类型为螺杆泵时
//							responseData=StringManagerUtils.sendPostMethod(screwPumpCalUrl[i%(screwPumpCalUrl.length)], requestData,"utf-8");
//						}else{
//							responseData=StringManagerUtils.sendPostMethod(url[i%(url.length)], requestData,"utf-8");
//						}
//						type = new TypeToken<RPCCalculateResponseData>() {}.getType();
//						RPCCalculateResponseData calculateResponseData=gson.fromJson(responseData, type);
//						int id=Integer.parseInt(obj[obj.length-1].toString());
//						if(calculateResponseData==null){
//							System.out.println("记录:"+id+"计算无数据返回");
//						}else{
//							calculateDataService.saveCalculateResult(id,calculateResponseData);
//						}
//						if(i==list.size()-1){//如果计算完成，汇总该井当天记录
//							String remainSql="select count(1)"
//									+ " from tbl_rpc_diagram_hist t,tbl_rpc_productiondata_hist t2,tbl_wellinformation t3"
//									+ " where t.wellid=t3.id and t.productiondataid=t2.id  "
//									+ " and t.resultstatus in (0,2)  "
//									+ " and t.wellid="+wellId+"";
//							int remainTotals=calculateDataService.getTotalCountRows(remainSql);
//							if(remainTotals==0){
//								StringManagerUtils.sendPostMethod(totalUrl, "","utf-8");
//							}
//							
//						}
//					}
//				}catch(Exception e){
//					continue;
//				}
//			}
		}
		
		boolean finish=false;
		while(!finish){
			for(int i=0;i<calculateThreadList.length;i++){
				if(calculateThreadList[i]!=null&&calculateThreadList[i].isAlive()){
					finish=false;
					break;
				}
				finish=true;
			}
			
		}
		
		endTime=new Date().getTime();
		allTime=endTime-startTime;
		json=calCount+"条记录计算完成，共用时:"+allTime+"毫秒";
		System.out.println(json);
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
	
	@RequestMapping("/FSDiagramDailyCalculation")
	public String FSDiagramDailyCalculation() throws ParseException{
		String tatalDate=ParamUtils.getParameter(request, "date");
		String wellId=ParamUtils.getParameter(request, "wellId");
		if(StringManagerUtils.isNotNull(tatalDate)){
			tatalDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(tatalDate));
		}else{
			tatalDate=StringManagerUtils.getCurrentTime();
		}
		List<String> requestDataList=calculateDataService.getFSDiagramDailyCalculationRequestData(tatalDate,wellId);
		String url=Config.getInstance().configFile.getAgileCalculate().getTotalCalculation().getWell()[0];
		for(int i=0;i<requestDataList.size();i++){//TotalCalculateResponseData
			try {
//				System.out.println(requestDataList.get(i));
				Gson gson = new Gson();
				java.lang.reflect.Type typeRequest = new TypeToken<TotalAnalysisRequestData>() {}.getType();
				TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(requestDataList.get(i), typeRequest);
				String responseData=StringManagerUtils.sendPostMethod(url, requestDataList.get(i),"utf-8");
				java.lang.reflect.Type type = new TypeToken<TotalAnalysisResponseData>() {}.getType();
				TotalAnalysisResponseData totalAnalysisResponseData = gson.fromJson(responseData, type);
				
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					calculateDataService.saveFSDiagramDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData,tatalDate);
				}else{
					System.out.println("诊断计算汇总error:"+requestDataList.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		System.out.println("汇总完成");
		
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
	
	@RequestMapping("/PCPRPMDailyCalculation")
	public String PCPRPMDailyCalculation() throws ParseException{
		String tatalDate=ParamUtils.getParameter(request, "date");
		String wellId=ParamUtils.getParameter(request, "wellId");
		if(StringManagerUtils.isNotNull(tatalDate)){
			tatalDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(tatalDate));
		}else{
			tatalDate=StringManagerUtils.getCurrentTime();
		}
		List<String> requestDataList=calculateDataService.getPCPRPMDailyCalculationRequestData(tatalDate,wellId);
		String url=Config.getInstance().configFile.getAgileCalculate().getTotalCalculation().getWell()[0];
		for(int i=0;i<requestDataList.size();i++){//TotalCalculateResponseData
			try {
//				System.out.println(requestDataList.get(i));
				Gson gson = new Gson();
				java.lang.reflect.Type typeRequest = new TypeToken<TotalAnalysisRequestData>() {}.getType();
				TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(requestDataList.get(i), typeRequest);
				String responseData=StringManagerUtils.sendPostMethod(url, requestDataList.get(i),"utf-8");
				java.lang.reflect.Type type = new TypeToken<TotalAnalysisResponseData>() {}.getType();
				TotalAnalysisResponseData totalAnalysisResponseData = gson.fromJson(responseData, type);
				
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					calculateDataService.savePCPRPMDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData,tatalDate);
				}else{
					System.out.println("诊断计算汇总error:"+requestDataList.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		System.out.println("汇总完成");
		
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
	
	@RequestMapping("/DiscreteDailyCalculation")
	public String DiscreteDailyCalculation() throws ParseException{
		String tatalDate=ParamUtils.getParameter(request, "date");
		String wellId=ParamUtils.getParameter(request, "wellId");
		if(StringManagerUtils.isNotNull(tatalDate)){
			tatalDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(tatalDate));
		}else{
			tatalDate=StringManagerUtils.getCurrentTime();
		}
		List<String> requestDataList=calculateDataService.getDiscreteDailyCalculation(tatalDate,wellId);
		String url=Config.getInstance().configFile.getAgileCalculate().getTotalCalculation().getWell()[0];
		for(int i=0;i<requestDataList.size();i++){
			try {
				Gson gson = new Gson();
				java.lang.reflect.Type typeRequest = new TypeToken<TotalAnalysisRequestData>() {}.getType();
				TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(requestDataList.get(i), typeRequest);
				String responseData=StringManagerUtils.sendPostMethod(url, requestDataList.get(i),"utf-8");
				java.lang.reflect.Type type = new TypeToken<TotalAnalysisResponseData>() {}.getType();
				TotalAnalysisResponseData totalAnalysisResponseData = gson.fromJson(responseData, type);
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					calculateDataService.saveDiscreteDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData,tatalDate);
				}else{
					System.out.println("诊断计算汇总error:"+requestDataList.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		System.out.println("汇总完成");
		
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
	
	@RequestMapping("/PCPDiscreteDailyCalculation")
	public String PCPDiscreteDailyCalculation() throws ParseException{
		String tatalDate=ParamUtils.getParameter(request, "date");
		String wellId=ParamUtils.getParameter(request, "wellId");
		if(StringManagerUtils.isNotNull(tatalDate)){
			tatalDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(tatalDate));
		}else{
			tatalDate=StringManagerUtils.getCurrentTime();
		}
		List<String> requestDataList=calculateDataService.getPCPDiscreteDailyCalculation(tatalDate,wellId);
		String url=Config.getInstance().configFile.getAgileCalculate().getTotalCalculation().getWell()[0];
		for(int i=0;i<requestDataList.size();i++){
			try {
				Gson gson = new Gson();
				java.lang.reflect.Type typeRequest = new TypeToken<TotalAnalysisRequestData>() {}.getType();
				TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(requestDataList.get(i), typeRequest);
				String responseData=StringManagerUtils.sendPostMethod(url, requestDataList.get(i),"utf-8");
				java.lang.reflect.Type type = new TypeToken<TotalAnalysisResponseData>() {}.getType();
				TotalAnalysisResponseData totalAnalysisResponseData = gson.fromJson(responseData, type);
				
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					calculateDataService.savePCPDiscreteDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData,tatalDate);
				}else{
					System.out.println("诊断计算汇总error:"+requestDataList.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		System.out.println("汇总完成");
		
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
	
	@RequestMapping("/pubSubModelCommCalculation")
	public String PubSubModelCommCalculation() throws ParseException{
		String commUrl=Config.getInstance().configFile.getAgileCalculate().getCommunication()[0];
		Gson gson = new Gson();
		String sql="select t.wellid,"
				+ " comm.wellName,comm.commstatus,"
				+ " to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as lastAcquisitiontime,"
				+ " t.commstatus as lastcCommstatus,t.commtime as lastCommtime,t.commtimeefficiency as lastCommtimeefficiency,t.commrange as lastCommrange"
				+ " from tbl_rpc_discrete_latest t,viw_commstatus comm"
				+ " where t.wellid=comm.id";
		List<?> list = calculateDataService.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String AcquisitionTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String commRequest="{"
					+ "\"AKString\":\"\","
					+ "\"WellName\":\""+obj[1]+"\",";
			if(StringManagerUtils.isNotNull(obj[3]+"")&&StringManagerUtils.isNotNull(obj[7]+"")){
				commRequest+= "\"Last\":{"
						+ "\"AcquisitionTime\": \""+obj[3]+"\","
						+ "\"CommStatus\": "+(("1".equals(obj[4]+""))?true:false)+","
						+ "\"CommEfficiency\": {"
						+ "\"Efficiency\": "+obj[6]+","
						+ "\"Time\": "+obj[5]+","
						+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(obj[7]+"")+""
						+ "}"
						+ "},";
			}	
			commRequest+= "\"Current\": {"
					+ "\"AcquisitionTime\":\""+AcquisitionTime+"\","
					+ "\"CommStatus\":"+(("1".equals(obj[2]+""))?true:false)
					+ "}"
					+ "}";
			
			String commResponse=StringManagerUtils.sendPostMethod(commUrl, commRequest,"utf-8");
			java.lang.reflect.Type type = new TypeToken<CommResponseData>() {}.getType();
			CommResponseData commResponseData=gson.fromJson(commResponse, type);
			if(commResponseData!=null&&commResponseData.getResultStatus()==1){
				String updateSql="update tbl_rpc_discrete_latest t set t.commstatus="+(commResponseData.getCurrent().getCommStatus()?1:0)+","
						+ "t.commtime="+commResponseData.getCurrent().getCommEfficiency().getTime()+","
						+ "t.commtimeefficiency="+commResponseData.getCurrent().getCommEfficiency().getEfficiency()+","
						+ "t.commrange='"+commResponseData.getCurrent().getCommEfficiency().getRangeString()+"'"
						+ " where t.wellid="+obj[0];
				int result=calculateDataService.getBaseDao().executeSqlUpdate(updateSql);
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
	
	//设置字符串utf-8编码
    public static String StringToUTF8(String xml,String type){
    	StringBuffer sb = new StringBuffer();
    	sb.append(xml);
    	String xmString = "";
    	String xmlUTF8="";
    	try {
    		xmString = new String(sb.toString().getBytes("UTF-8"),"ISO-8859-1");
//    		xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
    		xmlUTF8 = new String(xmString.getBytes("ISO-8859-1"),"UTF-8");
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
        }
        return xmlUTF8;
   } 
    
  //设置字符串utf-8编码
    public static String UTF8ToGBK(String xml){
    	String gbk="";
    	try {
    		String utf8 = new String(xml.getBytes( "UTF-8"));  
    		String unicode = new String(utf8.getBytes(),"UTF-8");   
    		gbk = new String(unicode.getBytes("GBK"));  
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
        }
        return gbk;
   } 
    
    public static String GBKToUTF8(String xml){
    	String utf8="";
    	try {
//    		String gbk = new String(xml.getBytes( "GBK"));  
//    		String unicode = new String(gbk.getBytes(),"GBK");   
//    		utf8 = new String(unicode.getBytes("UTF-8"));  
    		utf8 = new String (xml.getBytes("gbk"),"utf-8");
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
        }
        return utf8;
   }
    
    public static String getUTF8StringFromGBKString(String gbkStr) {
    	try {
    		return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
    	} catch (UnsupportedEncodingException e) {
    		throw new InternalError();
    	}  
    }
    
    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
    	int n = gbkStr.length();
    	byte[] utfBytes = new byte[3 * n];
    	int k = 0;
    	for (int i = 0; i < n; i++) {
    		int m = gbkStr.charAt(i);
    		if (m < 128 && m >= 0) {
    			utfBytes[k++] = (byte) m;
    			continue;
    		}
    		utfBytes[k++] = (byte) (0xe0 | (m >> 12));
    		utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
    		utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
    	}
    	if (k < utfBytes.length) {
    		byte[] tmp = new byte[k];
    		System.arraycopy(utfBytes, 0, tmp, 0, k);
    		return tmp;
    	}
    	return utfBytes;
    }
    
    public static String getEncoding(String str) {        
        String encode = "GB2312";        
       try {        
           if (str.equals(new String(str.getBytes(encode), encode))) {        
                String s = encode;        
               return s;        
            }        
        } catch (Exception exception) {        
        }        
        encode = "ISO-8859-1";        
       try {        
           if (str.equals(new String(str.getBytes(encode), encode))) {        
                String s1 = encode;        
               return s1;        
            }        
        } catch (Exception exception1) {        
        }        
        encode = "UTF-8";        
       try {        
           if (str.equals(new String(str.getBytes(encode), encode))) {        
                String s2 = encode;        
               return s2;        
            }        
        } catch (Exception exception2) {        
        }        
        encode = "GBK";        
       try {        
           if (str.equals(new String(str.getBytes(encode), encode))) {        
                String s3 = encode;        
               return s3;        
            }        
        } catch (Exception exception3) {        
        }        
       return "";        
    }
    
    
    
    @RequestMapping("/encodeTest")
	public String encodeTest() throws SQLException, IOException, ParseException, InterruptedException{
    	String str="中国";
    	byte[] unicodeByte= str.getBytes("unicode");
    	byte[] utf8Byte= str.getBytes("utf8");
    	byte[] gbkByte= str.getBytes("gbk");
    	byte[] isoByte= str.getBytes("iso8859-1");
    	
    	String strUnicode=new String(unicodeByte,"unicode");
    	String strUtf8=new String(utf8Byte,"utf8");
    	String strGbk=new String(gbkByte,"gbk");
    	String strIso=new String(isoByte,"iso8859");
    	
    	return null;
    }

}
