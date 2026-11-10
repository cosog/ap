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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.TextMessage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cosog.controller.base.BaseController;
import com.cosog.model.Code;
import com.cosog.model.DataMapping;
import com.cosog.model.KeyValue;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.CommResponseData;
import com.cosog.model.calculate.TimeEffResponseData;
import com.cosog.model.calculate.TimeEffTotalResponseData;
import com.cosog.model.calculate.TotalAnalysisRequestData;
import com.cosog.model.calculate.TotalAnalysisResponseData;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.datainterface.CalculateDataService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.thread.calculate.CalculateThread;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.utils.AdvancedMemoryMonitorUtils;
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.Constants;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.excel.ExcelUtils;
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
	private static final Pattern HEADER_NEWLINE = Pattern.compile("[\\r\\n\\t]+\\s*");
	private static final SimpleDateFormat SDF_FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
	
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
				+ " from tbl_srpacqdata_hist t "
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
		StringManagerUtils.printLog(json,0);
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
		StringManagerUtils.printLog(json,0);
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
//		String timeStr=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
//		StringManagerUtils.printLog("采集数据日汇总前内存情况,时间:"+timeStr);
//		StringManagerUtils.printLog(AdvancedMemoryMonitorUtils.getFormattedMemoryUsageKB());
		
		String tatalDate=ParamUtils.getParameter(request, "date");
		String deviceId=ParamUtils.getParameter(request, "deviceId");
		
		String date="";
		if(!StringManagerUtils.isNotNull(tatalDate)){
			date=StringManagerUtils.addDay(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime("yyyy-MM-dd")),-1);
		}else{
			date=tatalDate;
		}
		
		calculateDataService.AcquisitionDataDailyCalculation(tatalDate,deviceId);
		
		StringManagerUtils.printLog("采集数据汇总完成",0);
//		StringManagerUtils.printLog("采集数据日汇总后内存情况,时间:"+timeStr);
//		StringManagerUtils.printLog(AdvancedMemoryMonitorUtils.getFormattedMemoryUsageKB());
		
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
//		String timeStr=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
//		StringManagerUtils.printLog("功图数据日汇总前内存情况,时间:"+timeStr);
//		StringManagerUtils.printLog(AdvancedMemoryMonitorUtils.getFormattedMemoryUsageKB());
		
		String tatalDate=ParamUtils.getParameter(request, "date");
		String wellId=ParamUtils.getParameter(request, "wellId");
		List<String> requestDataList=null;
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
					StringManagerUtils.printLog("功图数据汇总error:"+requestDataList.get(i),0);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		StringManagerUtils.printLog("功图数据隔天汇总完成",0);
//		StringManagerUtils.printLog("功图数据日汇总后内存情况,时间:"+timeStr);
//		StringManagerUtils.printLog(AdvancedMemoryMonitorUtils.getFormattedMemoryUsageKB());
		
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
		
//		StringManagerUtils.printLog("采集数据定时汇总前内存情况,时间:"+timeStr);
//		StringManagerUtils.printLog(AdvancedMemoryMonitorUtils.getFormattedMemoryUsageKB());
		
//		deviceId="522";
//		timeStr="2024-09-29 16:00:00";
		
		long t1 = System.nanoTime();
		calculateDataService.AcquisitionDataTimingTotalCalculation(timeStr,deviceId);
		long t2 = System.nanoTime();
		StringManagerUtils.printLog("采集数据定时汇总完成"+ ",总耗时:" + StringManagerUtils.getTimeDiff(t1, t2),0);
		
//		StringManagerUtils.printLog("采集数据定时汇总后内存情况,时间:"+timeStr);
//		StringManagerUtils.printLog(AdvancedMemoryMonitorUtils.getFormattedMemoryUsageKB());
		
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
	
	@RequestMapping("/SRPTimingTotalCalculation")
	public String SRPTimingTotalCalculation() throws ParseException, SQLException, IOException{
		String time=ParamUtils.getParameter(request, "time");
		String timeStr=StringManagerUtils.timeStampToString(StringManagerUtils.stringToLong(time),"yyyy-MM-dd HH:mm:ss");
		
//		StringManagerUtils.printLog("功图数据定时汇总前内存情况,时间:"+timeStr);
//		StringManagerUtils.printLog(AdvancedMemoryMonitorUtils.getFormattedMemoryUsageKB());
		long t1 = System.nanoTime();
		calculateDataService.SRPTimingTotalCalculation(timeStr);
		long t2 = System.nanoTime();
		StringManagerUtils.printLog("功图定时汇总完成"+ ",总耗时:" + StringManagerUtils.getTimeDiff(t1, t2),0);
		
//		StringManagerUtils.printLog("功图数据定时汇总后内存情况,时间:"+timeStr);
//		StringManagerUtils.printLog(AdvancedMemoryMonitorUtils.getFormattedMemoryUsageKB());
		
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
//		String timeStr=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
//		StringManagerUtils.printLog("转速数据日汇总前内存情况,时间:"+timeStr);
//		StringManagerUtils.printLog(AdvancedMemoryMonitorUtils.getFormattedMemoryUsageKB());
		
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
					StringManagerUtils.printLog("转速数据汇总error:"+requestDataList.get(i),0);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		StringManagerUtils.printLog("转速数据隔天汇总完成",0);
//		StringManagerUtils.printLog("转速数据日汇总后内存情况,时间:"+timeStr);
//		StringManagerUtils.printLog(AdvancedMemoryMonitorUtils.getFormattedMemoryUsageKB());
		
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
		
//		StringManagerUtils.printLog("转速数据定时汇总前内存情况,时间:"+timeStr);
//		StringManagerUtils.printLog(AdvancedMemoryMonitorUtils.getFormattedMemoryUsageKB());
		
//		timeStr="2024-11-04 18:00:00";
		long t1 = System.nanoTime();
		calculateDataService.PCPTimingTotalCalculation(timeStr);
		long t2 = System.nanoTime();
		StringManagerUtils.printLog("转速数据定时汇总完成"+ ",总耗时:" + StringManagerUtils.getTimeDiff(t1, t2),0);
		
//		StringManagerUtils.printLog("转速数据定时汇总后内存情况,时间:"+timeStr);
//		StringManagerUtils.printLog(AdvancedMemoryMonitorUtils.getFormattedMemoryUsageKB());
		
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
		StringManagerUtils.printLog(deviceTypeName+"报表数据初始化完成-"+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"),0);
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
	
	@RequestMapping("/AcquisitionDataTimingRecord")
	public String AcquisitionDataTimingRecord() throws ParseException, SQLException, IOException{
		String time=ParamUtils.getParameter(request, "time");
		String deviceId=ParamUtils.getParameter(request, "deviceId");
		String timeStr=StringManagerUtils.timeStampToString(StringManagerUtils.stringToLong(time),"yyyy-MM-dd HH:mm:ss");
		
		long t1 = System.nanoTime();
		calculateDataService.AcquisitionDataTimingRecord(timeStr,deviceId);
		long t2 = System.nanoTime();
		StringManagerUtils.printLog("采集数据定时记录完成"+ ",总耗时:" + StringManagerUtils.getTimeDiff(t1, t2),0);
		
		String json ="";
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
	
	@RequestMapping("/dailyReportImport")
	public String dailyReportImport() throws ParseException, SQLException, IOException {
	    StringManagerUtils stringManagerUtils = new StringManagerUtils();
	    String filePath = Config.getInstance().configFile.getAp().getOem().getLanguageResourcePath().replace("locale", "importData");
	    String path = stringManagerUtils.getFilePath(filePath + "/dailyReport.xlsx");
	    File file = StringManagerUtils.getFile(path);
	    String msg = "";
	    boolean flag = false;

	    if (file == null) {
	        msg = "文件不存在";
	    } else {
	        try {
	            Map<String, DataMapping> loadProtocolMappingColumnByTitleMap = MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
	            Gson gson = new Gson();
	            long t1 = System.nanoTime();
	            Map<String, JSONArray> sheetMap = ExcelUtils.readFileManySheet(file);
	            long t2 = System.nanoTime();
	            System.out.println("读Excel耗时:" + StringManagerUtils.getTimeDiff(t1, t2));

	            Iterator<Map.Entry<String, JSONArray>> iterator = sheetMap.entrySet().iterator();
	            while (iterator.hasNext()) {
	                Map.Entry<String, JSONArray> entry = iterator.next();
	                String sheetName = entry.getKey();
	                if (sheetName.equalsIgnoreCase("生产数据") || sheetName.equalsIgnoreCase("排采曲线")) {
	                    continue;
	                }
	                // ★ sheet 级别 try-catch，单 sheet 失败不影响后续
	                try {
	                    String sql = "select t.id,t.devicename,t4.code "
	                            + " from tbl_device t "
	                            + " left outer join tbl_protocolinstance t2 on t.instancecode=t2.code "
	                            + " left outer join tbl_acq_unit_conf t3 on t3.id=t2.unitid "
	                            + " left outer join tbl_protocol t4 on t4.code=t3.protocol "
	                            + " where t.devicename='" + sheetName.replace("'", "''") + "'";
	                    List<?> deviceList = calculateDataService.findCallSql(sql);
	                    if (deviceList.size() == 0) {
	                        msg += sheetName + "不存在;";
	                        continue;
	                    }

	                    Object[] deviceObj = (Object[]) deviceList.get(0);
	                    String deviceId = deviceObj[0] + "";
	                    String portocolCode = deviceObj[2] + "";
	                    if (!StringManagerUtils.isNotNull(portocolCode)) {
	                        msg += sheetName + "未配置协议;";
	                        continue;
	                    }
	                    ModbusProtocolConfig.Protocol protocol = MemoryDataManagerTask.getProtocolByCode(portocolCode);
	                    if (protocol == null) {
	                        msg += sheetName + "协议不存在;";
	                        continue;
	                    }

	                    JSONArray arr = entry.getValue();
	                    if (arr == null || arr.isEmpty()) {
	                        continue;
	                    }

	                    // 算一次 key 映射（只对含换行的 key）
	                    Map<String, String> keyFixMap = new HashMap<>();
	                    Map<String, String> revKeyMap = new HashMap<>();
	                    JSONObject first = arr.getJSONObject(0);
	                    for (String k : first.keySet()) {
	                        if (k == null) continue;
	                        String clean = HEADER_NEWLINE.matcher(k).replaceAll("").trim();
	                        if (!clean.equals(k)) {
	                            keyFixMap.put(k, clean);
	                            revKeyMap.put(clean, k);
	                        }
	                    }

	                    // 收集本 sheet 所有 (key, 原始行)
	                    List<String> keyList = new ArrayList<>();
	                    List<JSONObject> rowList = new ArrayList<>();
	                    String startDate = "";
	                    String endDate = "";
	                    for (int i = 0; i < arr.size(); i++) {
	                        JSONObject obj = arr.getJSONObject(i);
	                        String date = getClean(obj, keyFixMap, revKeyMap, "生产日期");
	                        if (!StringManagerUtils.isNotNull(date)) continue;
	                        String dateOnly = date.length() >= 10 ? date.substring(0, 10) : date;
	                        keyList.add(deviceId + "_" + dateOnly);
	                        rowList.add(obj);
	                        if (startDate.isEmpty()) startDate = dateOnly;
	                        endDate = dateOnly;
	                    }
	                    if (keyList.isEmpty()) {
	                        continue;
	                    }

	                    // 查已存在
	                    Map<String, String> existMap = new LinkedHashMap<>();
	                    String currentSql = "select t.id,to_char(t.caldate,'yyyy-mm-dd') from tbl_dailycalculationdata t "
	                            + " where t.deviceid = " + deviceId
	                            + " and t.caldate between to_date('" + startDate + "','yyyy-mm-dd')"
	                            + " and to_date('" + endDate + "','yyyy-mm-dd')";
	                    List<?> existList = calculateDataService.findCallSql(currentSql);
	                    for (int i = 0; i < existList.size(); i++) {
	                        Object[] existObj = (Object[]) existList.get(i);
	                        // ★ 防御性 trim
	                        String existKey = deviceId + "_" + (existObj[1] + "").trim();
	                        existMap.put(existKey, existKey);
	                    }

	                    List<Object[]> toInsert = new ArrayList<>();
	                    List<Object[]> toUpdate = new ArrayList<>();

	                    for (int i = 0; i < rowList.size(); i++) {
	                        JSONObject obj = rowList.get(i);
	                        String key = keyList.get(i);

	                        String calDateStr = getClean(obj, keyFixMap, revKeyMap, "生产日期");
	                        // ★ 先判空，再 substring，避免 NPE
	                        if (!StringManagerUtils.isNotNull(calDateStr)) continue;
	                        String dateOnly = calDateStr.length() >= 10 ? calDateStr.substring(0, 10) : calDateStr;

	                        String runTime      = getClean(obj, keyFixMap, revKeyMap, "生产时间（h）");
	                        String rpm          = getClean(obj, keyFixMap, revKeyMap, "转速(转/分)");
	                        String current      = getClean(obj, keyFixMap, revKeyMap, "上行电流（A）");
	                        String casingPress  = getClean(obj, keyFixMap, revKeyMap, "套压(MPa)");
	                        String tubingPress  = getClean(obj, keyFixMap, revKeyMap, "管压(MPa)");
	                        String intakePress  = getClean(obj, keyFixMap, revKeyMap, "压力计压力（MPa）");
	                        String submergence  = getClean(obj, keyFixMap, revKeyMap, "煤没度(m)");
	                        String dailyGas     = getClean(obj, keyFixMap, revKeyMap, "日产气(m3/d)");
	                        String dailyWater   = getClean(obj, keyFixMap, revKeyMap, "日产水 (m3/d)");
	                        String totlGas      = getClean(obj, keyFixMap, revKeyMap, "累计产气(×104m3)");
	                        String totalWater   = getClean(obj, keyFixMap, revKeyMap, "累计产水(m3)");
	                        String remark       = getClean(obj, keyFixMap, revKeyMap, "备注");
	                        
	                        if (StringManagerUtils.isNotNull(totlGas)) {
	                            try {
	                                totlGas = new java.math.BigDecimal(totlGas.trim())
	                                        .multiply(new java.math.BigDecimal("10000"))
	                                        .stripTrailingZeros()
	                                        .toPlainString();
	                            } catch (NumberFormatException e) {
	                                // 非数字原样保留或置空
	                                e.printStackTrace();
	                            }
	                        }

	                        // ★ null → ""
	                        rpm         = rpm         == null ? "" : rpm;
	                        current     = current     == null ? "" : current;
	                        casingPress = casingPress == null ? "" : casingPress;
	                        tubingPress = tubingPress == null ? "" : tubingPress;
	                        intakePress = intakePress == null ? "" : intakePress;
	                        submergence = submergence == null ? "" : submergence;
	                        totlGas     = totlGas     == null ? "" : totlGas;
	                        totalWater  = totalWater  == null ? "" : totalWater;
	                        dailyGas    = dailyGas    == null ? "" : dailyGas;
	                        dailyWater  = dailyWater  == null ? "" : dailyWater;

	                        // ★ remark 截断到 4000
	                        if (remark != null && remark.length() > 4000) {
	                            remark = remark.substring(0, 4000);
	                        }
	                        
	                     // ---------- 运行时间 → 通信/运行状态、运行时率、运行区间 ----------
	                        Double runTimeD = StringManagerUtils.stringToDouble(runTime);   // 你原来的写法
	                        if (runTimeD == null) runTimeD = 0.0;
	                        if (runTimeD < 0) runTimeD = 0.0;
	                        if (runTimeD > 24) runTimeD = 24.0;   // 防御：超过 24 按 24 处理

	                        int commStatus = 1;                   // 按业务：导入数据视为已通信
	                        int runStatus;
	                        java.math.BigDecimal runTimeEfficiency;
	                        String runRange;

	                        if (runTimeD == 0.0) {
	                            runStatus = 0;
	                            runTimeEfficiency = java.math.BigDecimal.ZERO.setScale(2);   // "0.00"
	                            runRange = "";
	                        } else if (runTimeD == 24.0) {
	                            runStatus = 1;
	                            runTimeEfficiency = java.math.BigDecimal.ONE.setScale(2);    // "1.00"
	                            runRange = "00:00~00:00";
	                        } else {
	                            runStatus = 1;
	                            runTimeEfficiency = new java.math.BigDecimal(runTimeD.toString())
	                                    .divide(new java.math.BigDecimal("24"), 2, java.math.RoundingMode.HALF_UP);
	                            runRange = "00:00~" + doubleHourToHHmm(runTimeD);
	                        }

	                        String rpmCol = loadProtocolMappingColumnByTitleMap.get("转速") != null ? loadProtocolMappingColumnByTitleMap.get("转速").getMappingColumn() : "";
	                        String currentCol = loadProtocolMappingColumnByTitleMap.get("输出电流") != null ? loadProtocolMappingColumnByTitleMap.get("输出电流").getMappingColumn() : "";
	                        String casingPressCol = loadProtocolMappingColumnByTitleMap.get("套压") != null ? loadProtocolMappingColumnByTitleMap.get("套压").getMappingColumn() : "";
	                        String tubingPressCol = loadProtocolMappingColumnByTitleMap.get("油压") != null ? loadProtocolMappingColumnByTitleMap.get("油压").getMappingColumn() : "";
	                        String intakePressCol = loadProtocolMappingColumnByTitleMap.get("泵入口压力") != null ? loadProtocolMappingColumnByTitleMap.get("泵入口压力").getMappingColumn() : "";
	                        String submergenceCol = loadProtocolMappingColumnByTitleMap.get("沉没度") != null ? loadProtocolMappingColumnByTitleMap.get("沉没度").getMappingColumn() : "";
	                        String totlGasCol = loadProtocolMappingColumnByTitleMap.get("标况累积气量") != null ? loadProtocolMappingColumnByTitleMap.get("标况累积气量").getMappingColumn() : "";
	                        String totalWaterCol = loadProtocolMappingColumnByTitleMap.get("标况累积水量") != null ? loadProtocolMappingColumnByTitleMap.get("标况累积水量").getMappingColumn() : "";

	                        List<KeyValue> calDataList = new ArrayList<>();
	                        if (StringManagerUtils.isNotNull(rpmCol)) {
	                            calDataList.add(new KeyValue(rpmCol, rpm + ";" + rpm + ";" + rpm + ";" + rpm + ";" + rpm + "; "));
	                        }
	                        if (StringManagerUtils.isNotNull(currentCol)) {
	                            calDataList.add(new KeyValue(currentCol, current + ";" + current + ";" + current + ";" + current + ";" + current + "; "));
	                        }
	                        if (StringManagerUtils.isNotNull(casingPressCol)) {
	                            calDataList.add(new KeyValue(casingPressCol, casingPress + ";" + casingPress + ";" + casingPress + ";" + casingPress + ";" + casingPress + "; "));
	                        }
	                        if (StringManagerUtils.isNotNull(tubingPressCol)) {
	                            calDataList.add(new KeyValue(tubingPressCol, tubingPress + ";" + tubingPress + ";" + tubingPress + ";" + tubingPress + ";" + tubingPress + "; "));
	                        }
	                        if (StringManagerUtils.isNotNull(intakePressCol)) {
	                            calDataList.add(new KeyValue(intakePressCol, intakePress + ";" + intakePress + ";" + intakePress + ";" + intakePress + ";" + intakePress + "; "));
	                        }
	                        if (StringManagerUtils.isNotNull(submergenceCol)) {
	                            calDataList.add(new KeyValue(submergenceCol, submergence + ";" + submergence + ";" + submergence + ";" + submergence + ";" + submergence + "; "));
	                        }
	                        if (StringManagerUtils.isNotNull(totlGasCol)) {
	                            calDataList.add(new KeyValue(totlGasCol, totlGas + ";" + totlGas + ";" + totlGas + ";" + totlGas + ";" + totlGas + ";" + dailyGas));
	                        }
	                        if (StringManagerUtils.isNotNull(totalWaterCol)) {
	                            calDataList.add(new KeyValue(totalWaterCol, totalWater + ";" + totalWater + ";" + totalWater + ";" + totalWater + ";" + totalWater + ";" + dailyWater));
	                        }
	                        String calDataJson = gson.toJson(calDataList);

	                        Object[] params = new Object[]{
	                                deviceId,                                       // 1 deviceid
	                                dateOnly,                                       // 2 caldate
	                                StringManagerUtils.stringToDouble(runTime),     // 3 runtime
	                                commStatus,                                     // 4 commstatus
	                                runStatus,                                      // 5 runstatus
	                                runTimeEfficiency.doubleValue(),                // 6 runtimeefficiency
	                                runRange,                                       // 7 runrange
	                                remark,                                         // 8 remark
	                                calDataJson                                     // 9 caldata (CLOB)
	                        };

	                        if (existMap.containsKey(key)) {
	                            toUpdate.add(params);
	                        } else {
	                            toInsert.add(params);
	                        }
	                    }

	                    // ========== ⑤ 批量入库（同一事务，插+更 原子） ==========
	                    int[] result = calculateDataService.batchSaveDailyCal(toInsert, toUpdate);
	                    int inserted = result[0], updated = result[1];

	                    flag = true;
	                    msg += sheetName + "(新增" + inserted + ",更新" + updated + ");";
	                } catch (Exception sheetEx) {
	                    sheetEx.printStackTrace();
	                    msg += sheetName + "导入失败:" + sheetEx.getMessage() + ";";
	                    // ★ 单 sheet 失败继续下一 sheet
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            msg += "导入异常:" + e.getMessage();
	        }
	    }

	    String json = "{\"flag\":" + flag + ",\"msg\":\"" + msg.replace("\"", "\\\"").replace("\r", "").replace("\n", "") + "\"}";
	    response.setContentType("application/json;charset=utf-8");
	    PrintWriter pw = null;
	    try {
	        pw = response.getWriter();
	        pw.write(json);
	        pw.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (pw != null) pw.close();
	    }
	    return null;
	}
	
	/** 小时数（如 7.5）→ "HH:mm"（如 07:30） */
	private static String doubleHourToHHmm(double hours) {
	    if (hours < 0) hours = 0;
	    if (hours > 24) hours = 24;
	    int totalMinutes = (int) Math.round(hours * 60);
	    int hh = totalMinutes / 60;
	    int mm = totalMinutes % 60;
	    return String.format("%02d:%02d", hh, mm);
	}
	
	/** 先按清理后 key 取，取不到再按原始 key 取（兼容表头换行） */
	private static String getClean(JSONObject obj, Map<String, String> fixMap,
	                               Map<String, String> revMap, String cleanKey) {
	    String v = obj.getString(cleanKey);
	    if (v != null) return v;
	    String originalKey = revMap.get(cleanKey);
	    return originalKey == null ? null : obj.getString(originalKey);
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
