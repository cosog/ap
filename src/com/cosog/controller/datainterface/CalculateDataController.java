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
import com.cosog.model.drive.KafkaConfig;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.datainterface.CalculateDataService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.thread.calculate.CalculateThread;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.utils.AdInitThreadPoolConfig;
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.Config2;
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
//		CalculateThread calculateThreadList[]=new CalculateThread[20];
//		for(int i=0;i<20;i++){
//			calculateThreadList[i]=null;
//		}
		
		ThreadPool executor = new ThreadPool("FESDiagramReCalculate",20, 25, 5,TimeUnit.SECONDS,0);
		
		String wellListSql="select distinct(wellid) from tbl_rpcacqdata_hist t where resultstatus =2 and t.productiondata is not null and t.fesdiagramacqtime is not null";
		List<?> wellList = calculateDataService.findCallSql(wellListSql);
		startTime=new Date().getTime();
		for(int j=0;j<wellList.size();j++){
			executor.execute(new CalculateThread(StringManagerUtils.stringToInteger(wellList.get(j)+""),StringManagerUtils.stringToInteger(wellList.get(j)+""),0,calculateDataService));
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
		
		ThreadPool executor = new ThreadPool("RPMReCalculate",20, 25, 5,TimeUnit.SECONDS,0);
		String wellListSql="select distinct(wellid) from tbl_pcpacqdata_hist t where resultstatus =2 and t.productiondata is not null not null and t.rpm is not null";
		List<?> wellList = calculateDataService.findCallSql(wellListSql);
		startTime=new Date().getTime();
		for(int j=0;j<wellList.size();j++){
			executor.execute(new CalculateThread(StringManagerUtils.stringToInteger(wellList.get(j)+""),StringManagerUtils.stringToInteger(wellList.get(j)+""),1,calculateDataService));
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
	
	@SuppressWarnings("static-access")
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
		
		for(int i=0;requestDataList!=null&&i<requestDataList.size();i++){//TotalCalculateResponseData
			try {
				Gson gson = new Gson();
				java.lang.reflect.Type typeRequest = new TypeToken<TotalAnalysisRequestData>() {}.getType();
				TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(requestDataList.get(i), typeRequest);
				
				TotalAnalysisResponseData totalAnalysisResponseData=CalculateUtils.totalCalculate(requestDataList.get(i).toString());
				
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					calculateDataService.saveFSDiagramDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData,date);
				}else{
					System.out.println("抽油机井曲线数据汇总error:"+requestDataList.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		System.out.println("抽油机井曲线数据汇总完成");
		
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
		
		if(StringManagerUtils.isNotNull(endAcqTime) && StringManagerUtils.isNotNull(wellId)){
//			requestDataList=calculateDataService.getFSDiagramDailyCalculationRequestData(tatalDate,wellId,endAcqTime);
		}else{
			requestDataList=calculateDataService.getRPMDailyCalculationRequestData(tatalDate,wellId);
		}
		
		for(int i=0;requestDataList!=null&&i<requestDataList.size();i++){//TotalCalculateResponseData
			try {
				Gson gson = new Gson();
				java.lang.reflect.Type typeRequest = new TypeToken<TotalAnalysisRequestData>() {}.getType();
				TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(requestDataList.get(i), typeRequest);
				TotalAnalysisResponseData totalAnalysisResponseData=CalculateUtils.totalCalculate(requestDataList.get(i));
				
				
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					calculateDataService.saveRPMTotalCalculateData(totalAnalysisResponseData,totalAnalysisRequestData,date);
				}else{
					System.out.println("抽油机井曲线数据汇总error:"+requestDataList.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		System.out.println("螺杆泵井转速数据汇总完成");
		
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
