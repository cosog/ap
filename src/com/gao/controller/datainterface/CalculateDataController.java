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
import com.gao.model.calculate.CalculateRequestData;
import com.gao.model.calculate.CalculateResponseData;
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
		String url[]=Config.getCalculateHttpServerURL().split(",");
		String screwPumpCalUrl[]=Config.getScrewPumpCalculateHttpServerURL().split(",");
		String totalUrl=Config.getProjectAccessPath()+"/calculateDataController/totalCalculation";
		String elecCalUrl[]=Config.getPumpingunitElecCalculateHttpServerURL().split(",");
		String screwPumpElecCalUrl[]=Config.getScrewpumpElecCalculateHttpServerURL().split(",");
		String tiemEffUrl[]=Config.getTimeEfficiencyHttpServerURL().split(",");
		String commCalUrl[]=Config.getCommHttpServerURL().split(",");
		String json="";
		long startTime=0;
		long endTime=0;
		long allTime=0;
		String totalDate = StringManagerUtils.getCurrentTime();
		totalUrl+="?date="+totalDate;
		//String FSDiagramId=ParamUtils.getParameter(request, "FSDiagramId");
		//String strCount=ParamUtils.getParameter(request, "claculateCount");
		
		CalculateThread calculateThreadList[]=new CalculateThread[20];
		for(int i=0;i<20;i++){
			calculateThreadList[i]=null;
		}
		
		String wellListSql="select distinct(jbh) from t_outputwellhistory t033  where t033.jsbz in (0,2)";
		String sqlAll="select count(1) from t_outputwellhistory t033  where t033.jsbz in (0,2)";
		List<?> wellList = calculateDataService.findCallSql(wellListSql);
		int calCount=0;
		startTime=new Date().getTime();
		for(int j=0;j<wellList.size();j++){
			String jbh=wellList.get(j)+"";
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
					+ " to_char(t033.cjsj,'yyyy-mm-dd hh24:mi:ss'),"
					+ " t033.jsbz,t033.jlbh"
					+ " from tbl_wellinformation t007, tbl_rpc_diagram_hist t010,"
					+ " t_dynamicliquidlevel t011,t_outputwellhistory t033  "
					+ " where t007.jlbh=t010.jbh and t033.jbh=t007.jlbh and t033.gtbh=t010.jlbh  "
					+ " and t033.dymbh=t011.jlbh  "
					+ " and t033.jsbz in (0,2)  "
					+ " and t033.jbh="+jbh+""
					+ " order by t033.gtcjsj "
					+ " ) v where rownum<=100";
			List<?> list = calculateDataService.findCallSql(sql);
			calCount+=list.size();
			int jsbz0=0;
			Gson gson = new Gson();
			for(int i=0;i<list.size();i++){
				try{

					Object[] obj=(Object[])list.get(i);
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
						
						if(calculateRequestData.getLiftingType()>=400&&calculateRequestData.getLiftingType()<500){
							if("0".equals(obj[64]+"")){//如果计算标志为0
								jsbz0++;
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
										runStatus=StringManagerUtils.stringToInteger(obj[62]+"");
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
						}
						
						if(calculateResponseData==null){
							int jlbh=Integer.parseInt(obj[obj.length-1].toString());
							System.out.println("记录:"+jlbh+"计算无数据返回");
						}else{
							int jlbh=Integer.parseInt(obj[obj.length-1].toString());
//							if(calculateResponseData.getCalculationStatus().getResultStatus()!=1){
//								System.out.println("计算状态"+calculateResponseData.getCalculationStatus().getResultStatus()+"，请求数据："+requestData);
//							}
							calculateDataService.saveCalculateResult(jlbh,calculateResponseData,electricCalculateResponseData,timeEffResponseData,commResponseData);
						}
					}
				}catch(Exception e){
					continue;
				}
			}
		}
		
		boolean finish=false;
		while(!finish){
			for(int i=0;i<calculateThreadList.length;i++){
				if(calculateThreadList[i]!=null&&calculateThreadList[i].isAlive()){
					break;
				}
			}
			finish=true;
		}
		
		endTime=new Date().getTime();
		allTime=endTime-startTime;
		json=calCount+"条记录计算完成，共用时:"+allTime+"毫秒";
		System.out.println(json);
		int totals=this.commonDataService.getTotalCountRows(sqlAll);
		if(calCount==0){
			calculateDataService.deleteInvalidData();
		}else if(totals==0){//全部计算完成  汇总
			StringManagerUtils.sendPostMethod(totalUrl, "","utf-8");
		}
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
		String url=Config.getTotalCalculateHttpServerURL();
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
		String url=Config.getTotalCalculateHttpServerURL();
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
