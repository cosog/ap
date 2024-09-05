package com.cosog.controller.datainterface;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.TextMessage;

import com.cosog.controller.base.BaseController;
import com.cosog.model.calculate.CommResponseData;
import com.cosog.model.calculate.TimeEffResponseData;
import com.cosog.model.calculate.TimeEffTotalResponseData;
import com.cosog.model.calculate.TotalAnalysisRequestData;
import com.cosog.model.calculate.TotalAnalysisResponseData;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.datainterface.CalculateDataService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.thread.calculate.CalculateThread;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.Constants;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;
import com.cosog.websocket.config.WebSocketByJavax;
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
	@Bean//这个注解会从Spring容器拿出Bean
    public static WebSocketByJavax infoHandler2() {
        return new WebSocketByJavax();
    }
	
	@RequestMapping("/getBatchCalculateTime")
	public String getBatchCalculateTime() throws SQLException, IOException, ParseException, InterruptedException,Exception{
		String json="";
		long startTime=0;
		long endTime=0;
		long allTime=0;
		
		ThreadPool executor = new ThreadPool("FESDiagramReCalculate",
				Config.getInstance().configFile.getAp().getThreadPool().getCalculateMaintaining().getCorePoolSize(), 
				Config.getInstance().configFile.getAp().getThreadPool().getCalculateMaintaining().getMaximumPoolSize(), 
				Config.getInstance().configFile.getAp().getThreadPool().getCalculateMaintaining().getKeepAliveTime(), 
				TimeUnit.SECONDS, 
				Config.getInstance().configFile.getAp().getThreadPool().getCalculateMaintaining().getWattingCount());
		String wellListSql="select distinct deviceid,to_char(t.fesdiagramacqtime,'yyyy-mm-dd') as acqdate "
				+ " from tbl_rpcacqdata_hist t "
				+ " where t.productiondata is not null "
				+ " and t.fesdiagramacqtime is not null "
				+ " and resultstatus =2";
		List<?> wellList = calculateDataService.findCallSql(wellListSql);
		startTime=new Date().getTime();
		for(int j=0;j<wellList.size();j++){
			Object[] obj=(Object[]) wellList.get(j);
			executor.execute(new CalculateThread(StringManagerUtils.stringToInteger(obj[0]+""),
					StringManagerUtils.stringToInteger(obj[0]+""),obj[1]+"",1,calculateDataService));
		}
		while (!executor.isCompletedByTaskCount()) {
			Thread.sleep(1000*1);
	    }
		
		endTime=new Date().getTime();
		allTime=endTime-startTime;
		json="计算完成，共用时:"+allTime+"毫秒";
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
	
	@RequestMapping("/getPCPBatchCalculateTime")
	public String getPCPBatchCalculateTime() throws SQLException, IOException, ParseException, InterruptedException,Exception{
		String json="";
		long startTime=0;
		long endTime=0;
		long allTime=0;
		
		ThreadPool executor = new ThreadPool("RPMReCalculate",
				Config.getInstance().configFile.getAp().getThreadPool().getCalculateMaintaining().getCorePoolSize(), 
				Config.getInstance().configFile.getAp().getThreadPool().getCalculateMaintaining().getMaximumPoolSize(), 
				Config.getInstance().configFile.getAp().getThreadPool().getCalculateMaintaining().getKeepAliveTime(), 
				TimeUnit.SECONDS, 
				Config.getInstance().configFile.getAp().getThreadPool().getCalculateMaintaining().getWattingCount());
		String wellListSql="select distinct deviceid,to_char(t.acqtime,'yyyy-mm-dd') as acqdate "
				+ " from tbl_pcpacqdata_hist t "
				+ " where t.productiondata is not null "
				+ " and t.rpm is not null "
				+ " and resultstatus =2";
		List<?> wellList = calculateDataService.findCallSql(wellListSql);
		startTime=new Date().getTime();
		for(int j=0;j<wellList.size();j++){
			Object[] obj=(Object[]) wellList.get(j);
			executor.execute(new CalculateThread(StringManagerUtils.stringToInteger(obj[0]+""),
					StringManagerUtils.stringToInteger(obj[0]+""),obj[1]+"",2,calculateDataService));
		}
		while (!executor.isCompletedByTaskCount()) {
			Thread.sleep(1000*1);
	    }
		endTime=new Date().getTime();
		allTime=endTime-startTime;
		json="计算完成，共用时:"+allTime+"毫秒";
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
	
	@RequestMapping("/AcquisitionDataDailyCalculation")
	public String AcquisitionDataDailyCalculation() throws ParseException, SQLException, IOException{
		String tatalDate=ParamUtils.getParameter(request, "date");
		String deviceId=ParamUtils.getParameter(request, "deviceId");
		
		String date="";
		if(!StringManagerUtils.isNotNull(tatalDate)){
			date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime("yyyy-MM-dd")),-1);
		}else{
			date=tatalDate;
		}
		
		calculateDataService.AcquisitionDataDailyCalculation(tatalDate,deviceId);
		
		System.out.println("采集数据汇总完成");
		
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
	
	@RequestMapping("/FESDiagramDailyCalculation")
	public String FESDiagramDailyCalculation() throws ParseException, SQLException, IOException{
		String tatalDate=ParamUtils.getParameter(request, "date");
		String wellId=ParamUtils.getParameter(request, "wellId");
		List<String> requestDataList=null;
		String endAcqTime=java.net.URLDecoder.decode(ParamUtils.getParameter(request, "endAcqTime"),"utf-8");
		
		String date="";
		if(!StringManagerUtils.isNotNull(tatalDate)){
			date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime("yyyy-MM-dd")),-1);
		}else{
			date=tatalDate;
		}
		
		requestDataList=calculateDataService.getFSDiagramDailyCalculationRequestData(tatalDate,wellId);
		for(int i=0;requestDataList!=null&&i<requestDataList.size();i++){
			try {
				Gson gson = new Gson();
				java.lang.reflect.Type typeRequest = new TypeToken<TotalAnalysisRequestData>() {}.getType();
				TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(requestDataList.get(i), typeRequest);
				
				TotalAnalysisResponseData totalAnalysisResponseData=CalculateUtils.totalCalculate(requestDataList.get(i).toString());
				
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					calculateDataService.saveFSDiagramDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData,date);
				}else{
					System.out.println("功图数据汇总error:"+requestDataList.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		System.out.println("功图数据隔天汇总完成");
		
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
	
	@RequestMapping("/AcquisitionDataTimingTotalCalculation")
	public String AcquisitionDataTimingTotalCalculation() throws ParseException, SQLException, IOException{
		String time=ParamUtils.getParameter(request, "time");
		String deviceId=ParamUtils.getParameter(request, "deviceId");
		String timeStr=StringManagerUtils.timeStampToString(StringManagerUtils.stringToLong(time),"yyyy-MM-dd HH:mm:ss");
		timeStr="2024-09-01 23:00:00";
		
		long t1 = System.nanoTime();
		calculateDataService.AcquisitionDataTimingTotalCalculation(timeStr,deviceId);
		long t2 = System.nanoTime();
		System.out.println("采集数据定时汇总完成"+ ",总耗时:" + StringManagerUtils.getTimeDiff(t1, t2));
		
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
	
	@RequestMapping("/RPCTimingTotalCalculation")
	public String RPCTimingTotalCalculation() throws ParseException, SQLException, IOException{
		String time=ParamUtils.getParameter(request, "time");
		String timeStr=StringManagerUtils.timeStampToString(StringManagerUtils.stringToLong(time),"yyyy-MM-dd HH:mm:ss");
		calculateDataService.RPCTimingTotalCalculation(timeStr);
		
		System.out.println("功图定时汇总完成");
		
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
	
	@SuppressWarnings("static-access")
	@RequestMapping("/RPMDailyCalculation")
	public String RPMDailyCalculation() throws ParseException, SQLException, IOException{
		String tatalDate=ParamUtils.getParameter(request, "date");
		String wellId=ParamUtils.getParameter(request, "wellId");
		List<String> requestDataList=null;
		String endAcqTime=java.net.URLDecoder.decode(ParamUtils.getParameter(request, "endAcqTime"),"utf-8");
		
		String date="";
		if(!StringManagerUtils.isNotNull(tatalDate)){
			date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime("yyyy-MM-dd")),-1);
		}else{
			date=tatalDate;
		}
		requestDataList=calculateDataService.getRPMDailyCalculationRequestData(tatalDate,wellId);
		for(int i=0;requestDataList!=null&&i<requestDataList.size();i++){
			try {
				Gson gson = new Gson();
				java.lang.reflect.Type typeRequest = new TypeToken<TotalAnalysisRequestData>() {}.getType();
				TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(requestDataList.get(i), typeRequest);
				TotalAnalysisResponseData totalAnalysisResponseData=CalculateUtils.totalCalculate(requestDataList.get(i));
				
				
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					calculateDataService.saveRPMTotalCalculateData(totalAnalysisResponseData,totalAnalysisRequestData,date);
				}else{
					System.out.println("转速数据汇总error:"+requestDataList.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		System.out.println("转速数据隔天汇总完成");
		
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
	
	@RequestMapping("/PCPTimingTotalCalculation")
	public String PCPTimingTotalCalculation() throws ParseException, SQLException, IOException{
		String time=ParamUtils.getParameter(request, "time");
		String timeStr=StringManagerUtils.timeStampToString(StringManagerUtils.stringToLong(time),"yyyy-MM-dd HH:mm:ss");
		calculateDataService.PCPTimingTotalCalculation(timeStr);
		System.out.println("转速数据定时汇总完成");
		
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
	
	@RequestMapping("/initDailyReportData")
	public String initDailyReportData() throws ParseException, SQLException, IOException{
		String calculateTypeStr=ParamUtils.getParameter(request, "calculateType");
		String deviceTypeName="设备";
		int calculateType=StringManagerUtils.stringToInteger(calculateTypeStr);
		if(calculateType==1){
			deviceTypeName="抽油机井";
		}else if(calculateType==2){
			deviceTypeName="螺杆泵井";
		}
		calculateDataService.initDailyReportData(calculateType);
		System.out.println(deviceTypeName+"报表数据初始化完成-"+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
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
