package com.cosog.controller.report;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.User;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.report.ReportDataManagerService;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.Constants;
import com.cosog.utils.Page;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <p>描述：采出井日报表Action</p>
 * 
 * @author gao 2014-06-04
 * 
 */
@Controller
@RequestMapping("/reportDataMamagerController")
@Scope("prototype")
public class ReportDataMamagerController extends BaseController {
	private static Log log = LogFactory.getLog(ReportDataMamagerController.class);
	private static final long serialVersionUID = 1L;
	private String wellName = "";
	private String calculateDate;
	private String calculateEndDate;
	private int limit = 10;
	private String orgId;
	private int page = 1;
	@Autowired
	private CommonDataService commonDataService;
	@Autowired
	private ReportDataManagerService<?> reportDataManagerService;


	/** <p>描述：采出井日报表json数据方法</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDailyReportData")
	public String getDailyReportData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		String wellName = ParamUtils.getParameter(request, "wellName");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String tableName="tbl_rpcdailycalculationdata";
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pcpdailycalculationdata";
		}
		if (!StringUtils.isNotBlank(orgId)) {
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where1=1";
			if(StringManagerUtils.isNotNull(wellName)){
				sql+= " and t.wellname='"+wellName+"' ";
			}	
			sql+= "order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
			if(!StringManagerUtils.isNotNull(startDate)){
				startDate=endDate;
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=endDate;
		}
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			json = reportDataManagerService.showPCPDailyReportData(pager, orgId, wellName, startDate,endDate);
		}else{
			json = reportDataManagerService.getRPCDailyReportData(pager, orgId, wellName, startDate,endDate);
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
			e.printStackTrace();
		}
		return null;
	}
	
	/** <p>描述：导出采出井日报表json数据方法</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportRPCDailyReportData")
	public String exportRPCDailyReportData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		String wellName = ParamUtils.getParameter(request, "wellName");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String tableName="tbl_rpcdailycalculationdata";
		if (!StringUtils.isNotBlank(orgId)) {
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where1=1";
			if(StringManagerUtils.isNotNull(wellName)){
				sql+= " and t.wellname='"+wellName+"' ";
			}	
			sql+= "order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
			if(!StringManagerUtils.isNotNull(startDate)){
				startDate=endDate;
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=endDate;
		}
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setJssj(calculateDate);
		json = reportDataManagerService.exportRPCDailyReportData(pager, orgId, wellName, startDate,endDate);
		
		JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+json+"}");//解析数据
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		try {
	        /** 
	         * web端生成保存打开excel弹出框 
	         */  
	        response.setContentType("application/x-msdownload;charset=gbk");      
	        String fileName = "抽油机井";
	        if(StringManagerUtils.isNotNull(wellName)){
	        	fileName+=wellName;
	        }
	        fileName+="生产报表-"+startDate;
	        if(!startDate.equalsIgnoreCase(endDate)){
	        	fileName+="~"+endDate;
	        }
	        fileName+=".xls";
	        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
	        OutputStream os = response.getOutputStream();     
	        //打开文件    
	        WritableWorkbook book= Workbook.createWorkbook(os);     
	        WritableSheet sheetOne=book.createSheet("生产报表",0);    
	        
	        WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 20,WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色    
	        WritableFont wf_head = new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色    
	        WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 11,WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色    
	   
	        WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义    
	        wcf_title.setBackground(jxl.format.Colour.WHITE); // 设置单元格的背景颜色    
	        wcf_title.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式    
	        wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); // 设置垂直对齐方式   
	        wcf_title.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.BLACK); //设置边框    
	        
	        WritableCellFormat wcf_head = new WritableCellFormat(wf_head);     
	        wcf_head.setBackground(jxl.format.Colour.GRAY_25);    
	        wcf_head.setAlignment(jxl.format.Alignment.CENTRE);     
	        wcf_head.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); // 设置垂直对齐方式   
	        wcf_head.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.BLACK); 
	        wcf_head.setWrap(true);
	   
	        WritableCellFormat wcf_table = new WritableCellFormat(wf_table);    
	        wcf_table.setBackground(jxl.format.Colour.WHITE);     
	        wcf_table.setAlignment(jxl.format.Alignment.CENTRE);     
	        wcf_table.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); // 设置垂直对齐方式   
	        wcf_table.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.BLACK);     
	               
	        sheetOne.setColumnView(0, 10); // 设置列的宽度    
	        sheetOne.setColumnView(1, 15); // 设置列的宽度    
	        sheetOne.setColumnView(2, 15); // 设置列的宽度    
	        
	        sheetOne.setColumnView(3, 15); // 设置列的宽度    
	        sheetOne.setColumnView(4, 30); // 设置列的宽度    
	        sheetOne.setColumnView(5, 15); // 设置列的宽度    
	        
	        sheetOne.setColumnView(6, 15); // 设置列的宽度    
	        sheetOne.setColumnView(7, 30); // 设置列的宽度    
	        sheetOne.setColumnView(8, 15); // 设置列的宽度    
	        
	        sheetOne.setColumnView(9, 15); // 设置列的宽度    
	        sheetOne.setColumnView(10, 20); // 设置列的宽度    
	        
	        sheetOne.setColumnView(11, 15); // 设置列的宽度   
	        sheetOne.setColumnView(12, 15); // 设置列的宽度  
	        sheetOne.setColumnView(13, 15); // 设置列的宽度
	        sheetOne.setColumnView(14, 15); // 设置列的宽度
	        sheetOne.setColumnView(15, 15); // 设置列的宽度
	        
	        sheetOne.setColumnView(16, 15); // 设置列的宽度  
	        sheetOne.setColumnView(17, 15); // 设置列的宽度
	        sheetOne.setColumnView(18, 15); // 设置列的宽度
	        
	        sheetOne.setColumnView(19, 15); // 设置列的宽度
	        sheetOne.setColumnView(20, 15); // 设置列的宽度
	        sheetOne.setColumnView(21, 15); // 设置列的宽度
	        sheetOne.setColumnView(22, 20); // 设置列的宽度
	        
	        sheetOne.setColumnView(23, 15); // 设置列的宽度
	        
	        sheetOne.setColumnView(24, 15); // 设置列的宽度
	   
	        //在Label对象的构造子中指名单元格位置是第一列第一行(0,0)     
	        //以及单元格内容为test
	        ConfigFile configFile=Config.getInstance().configFile;
	        String productionUnit="t/d"; 
	        if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
	        	productionUnit="t/d"; 
			}else{
				productionUnit="m^3/d"; 
			}
	        Label title=new Label(0,0,"抽油机井生产报表",wcf_title);
	        
	        Label header1=new Label(0,1,"序号",wcf_head);
	        Label header2=new Label(1,1,"井名",wcf_head);
	        Label header3=new Label(2,1,"日期",wcf_head);
	        
	        Label header4=new Label(3,1,"通信",wcf_head);
	        Label header4_1=new Label(3,2,"在线时间(h)",wcf_head);
	        Label header4_2=new Label(4,2,"在线区间",wcf_head);
	        Label header4_3=new Label(5,2,"在线时率(小数)",wcf_head);
	        
	        Label header5=new Label(6,1,"时率",wcf_head);
	        Label header5_1=new Label(6,2,"运行时间(h)",wcf_head);
	        Label header5_2=new Label(7,2,"运行区间",wcf_head);
	        Label header5_3=new Label(8,2,"运行时率(小数)",wcf_head);
	           
	       Label header6=new Label(9,1,"工况",wcf_head);
	       Label header6_1=new Label(9,2,"功图工况",wcf_head);
	       Label header6_2=new Label(10,2,"优化建议",wcf_head);
	       
	       Label header7=new Label(11,1,"产量",wcf_head);
	       Label header7_1=new Label(11,2,"产液量("+productionUnit+")",wcf_head);
	       Label header7_2=new Label(12,2,"产油量("+productionUnit+")",wcf_head);
	       Label header7_3=new Label(13,2,"产水量("+productionUnit+")",wcf_head);
	       Label header7_4=new Label(14,2,"含水率(%)",wcf_head);
	       Label header7_5=new Label(15,2,"充满系数(小数)",wcf_head);
	       
	       Label header8=new Label(16,1,"平衡",wcf_head);
	       Label header8_1=new Label(16,2,"功率平衡度(%)",wcf_head);
	       Label header8_2=new Label(17,2,"电流平衡度(%)",wcf_head);
	       Label header8_3=new Label(18,2,"移动距离(cm)",wcf_head);
	       
	       Label header9=new Label(19,1,"效率",wcf_head);
	       Label header9_1=new Label(19,2,"系统效率(%)",wcf_head);
	       Label header9_2=new Label(20,2,"地面效率(%)",wcf_head);
	       Label header9_3=new Label(21,2,"井下效率(%)",wcf_head);
	       Label header9_4=new Label(22,2,"吨液百米耗电量(kW·h/100·t)",wcf_head);
	       
	       Label header10=new Label(23,1,"日用电量(kW·h)",wcf_head);
	       
	       Label header11=new Label(24,1,"备注",wcf_head);
	       
	     //或者WritableCell cell =  new jxl.write.Number(column, row, value, wcf)    
           //将定义好的单元格添加到工作表中     
           sheetOne.addCell(title);   
           sheetOne.addCell(header1);     
           sheetOne.addCell(header2);
           sheetOne.addCell(header3);     
           sheetOne.addCell(header4);     
           sheetOne.addCell(header4_1);     
           sheetOne.addCell(header4_2);     
           sheetOne.addCell(header4_3); 
           sheetOne.addCell(header5);
           sheetOne.addCell(header5_1);     
           sheetOne.addCell(header5_2);   
           sheetOne.addCell(header5_3);  
           sheetOne.addCell(header6);
           sheetOne.addCell(header6_1);     
           sheetOne.addCell(header6_2);
           sheetOne.addCell(header7);
           sheetOne.addCell(header7_1);
           sheetOne.addCell(header7_2);
           sheetOne.addCell(header7_3);
           sheetOne.addCell(header7_4);
           sheetOne.addCell(header7_5);
           sheetOne.addCell(header8);
           sheetOne.addCell(header8_1);
           sheetOne.addCell(header8_2);
           sheetOne.addCell(header8_3);
           sheetOne.addCell(header9);
           sheetOne.addCell(header9_1);
           sheetOne.addCell(header9_2);
           sheetOne.addCell(header9_3);
           sheetOne.addCell(header9_4);
           sheetOne.addCell(header10);     
           sheetOne.addCell(header11);
           
         //合： 第1列第1行  到 第13列第1行     
           sheetOne.mergeCells(0, 0, 26, 0);   
           
           sheetOne.mergeCells(0, 1, 0, 2);     
           sheetOne.mergeCells(1, 1, 1, 2);
           sheetOne.mergeCells(2, 1, 2, 2);
           sheetOne.mergeCells(3, 1, 5, 1);
           sheetOne.mergeCells(6, 1, 8, 1);
           sheetOne.mergeCells(9, 1, 10, 1);
           sheetOne.mergeCells(11, 1, 15, 1);
           sheetOne.mergeCells(16, 1, 18, 1);
           sheetOne.mergeCells(19, 1, 22, 1);
           sheetOne.mergeCells(23, 1, 23, 2);
           sheetOne.mergeCells(24, 1, 24, 2);
           /*动态数据   */ 
           float sumCommTime=0;
           float sumRunTime=0;
           float sumLiquidProduction=0;
           float sumOilProduction=0;
           float sumWaterProduction=0;
           
           int commTimeRecords=0;
           int runTimeRecords=0;
           int liquidProductionRecords=0;
           int oilProductionRecords=0;
           int waterProductionRecords=0;
           for(int i=0;i<=jsonArray.size()+1;i++){
        	   Label content1=null;
        	   Label content2=null;
        	   Label content3=null;
        	   Label content4=null;
        	   Label content5=null;
        	   Label content6=null;
        	   Label content7=null;
        	   Label content8=null;
        	   Label content9=null;
        	   Label content10=null;
        	   Label content11=null;
        	   Label content12=null;
        	   Label content13=null;
        	   Label content14=null;
        	   Label content15=null;
        	   Label content16=null;
        	   Label content17=null;
        	   Label content18=null;
        	   Label content19=null;
        	   Label content20=null;
        	   Label content21=null;
        	   Label content22=null;
        	   Label content23=null;
        	   Label content24=null;
        	   Label content25=null;
        	   if(i<jsonArray.size()){
        		   JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
        		   sumCommTime+=StringManagerUtils.stringToFloat(everydata.getString("commTime"));
        		   sumRunTime+=StringManagerUtils.stringToFloat(everydata.getString("runTime"));
        		   sumLiquidProduction+=StringManagerUtils.stringToFloat(everydata.getString("liquidProduction"));
        		   sumOilProduction+=StringManagerUtils.stringToFloat(everydata.getString("oilProduction"));
        		   sumWaterProduction+=StringManagerUtils.stringToFloat(everydata.getString("waterProduction"));
            	   
            	   if(StringManagerUtils.stringToFloat(everydata.getString("commTime"))>0){
            		   commTimeRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("runTime"))>0){
            		   runTimeRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("liquidProduction"))>0){
            		   liquidProductionRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("oilProduction"))>0){
            		   oilProductionRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("waterProduction"))>0){
            		   waterProductionRecords+=1;
            	   }
            	   
            	   
            	   content1=new Label(0,i+3,i+1+"",wcf_table);
            	   content2=new Label(1,i+3,everydata.getString("wellName"),wcf_table);
            	   content3=new Label(2,i+3,everydata.getString("calculateDate"),wcf_table);
            	   
            	   content4=new Label(3,i+3,everydata.getString("commTime"),wcf_table);
            	   content5=new Label(4,i+3,everydata.getString("commRange"),wcf_table);
            	   content6=new Label(5,i+3,everydata.getString("commTimeEfficiency"),wcf_table);
            	   
            	   content7=new Label(6,i+3,everydata.getString("runTime"),wcf_table);
            	   content8=new Label(7,i+3,everydata.getString("runRange"),wcf_table);
            	   content9=new Label(8,i+3,everydata.getString("runTimeEfficiency"),wcf_table);
            	   
            	   content10=new Label(9,i+3,everydata.getString("resultName"),wcf_table);
            	   content11=new Label(10,i+3,everydata.getString("optimizationSuggestion"),wcf_table);
            	   
            	   
            	   content12=new Label(11,i+3,everydata.getString("liquidProduction"),wcf_table);
            	   content13=new Label(12,i+3,everydata.getString("oilProduction"),wcf_table);
            	   content14=new Label(13,i+3,everydata.getString("waterProduction"),wcf_table);
            	   content15=new Label(14,i+3,everydata.getString("waterCut"),wcf_table);
            	   content16=new Label(15,i+3,everydata.getString("fullnesscoEfficient"),wcf_table);
            	   
            	   content17=new Label(16,i+3,everydata.getString("wattDegreeBalance"),wcf_table);
            	   content18=new Label(17,i+3,everydata.getString("iDegreeBalance"),wcf_table);
            	   content19=new Label(18,i+3,everydata.getString("deltaRadius"),wcf_table);
            	   
            	   content20=new Label(19,i+3,everydata.getString("systemEfficiency"),wcf_table);
            	   content21=new Label(20,i+3,everydata.getString("surfaceSystemEfficiency"),wcf_table);
            	   content22=new Label(21,i+3,everydata.getString("welldownSystemEfficiency"),wcf_table);
            	   content23=new Label(22,i+3,everydata.getString("energyPer100mLift"),wcf_table);
            	   
            	   content24=new Label(23,i+3,everydata.getString("todayKWattH"),wcf_table);
            	   content25=new Label(24,i+3,everydata.getString("remark"),wcf_table);
        	   }else if(i==jsonArray.size()){
        		   sumCommTime=StringManagerUtils.stringToFloat(sumCommTime+"",2);
        		   sumRunTime=StringManagerUtils.stringToFloat(sumRunTime+"",2);
        		   sumLiquidProduction=StringManagerUtils.stringToFloat(sumLiquidProduction+"",2);
        		   sumOilProduction=StringManagerUtils.stringToFloat(sumOilProduction+"",2);
        		   sumWaterProduction=StringManagerUtils.stringToFloat(sumWaterProduction+"",2);
            	   content1=new Label(0,i+3,"合计",wcf_table);
            	   content2=new Label(1,i+3,"",wcf_table);
            	   content3=new Label(2,i+3,"",wcf_table);
            	   
            	   content4=new Label(3,i+3,sumCommTime+"",wcf_table);
            	   content5=new Label(4,i+3,"",wcf_table);
            	   content6=new Label(5,i+3,"",wcf_table);
            	   
            	   content7=new Label(6,i+3,sumRunTime+"",wcf_table);
            	   content8=new Label(7,i+3,"",wcf_table);
            	   content9=new Label(8,i+3,"",wcf_table);
            	   
            	   content10=new Label(9,i+3,"",wcf_table);
            	   content11=new Label(10,i+3,"",wcf_table);
            	   
            	   
            	   content12=new Label(11,i+3,sumLiquidProduction+"",wcf_table);
            	   content13=new Label(12,i+3,sumOilProduction+"",wcf_table);
            	   content14=new Label(13,i+3,sumWaterProduction+"",wcf_table);
            	   content15=new Label(14,i+3,"",wcf_table);
            	   content16=new Label(15,i+3,"",wcf_table);
            	   
            	   content17=new Label(16,i+3,"",wcf_table);
            	   content18=new Label(17,i+3,"",wcf_table);
            	   content19=new Label(18,i+3,"",wcf_table);
            	   
            	   content20=new Label(19,i+3,"",wcf_table);
            	   content21=new Label(20,i+3,"",wcf_table);
            	   content22=new Label(21,i+3,"",wcf_table);
            	   content23=new Label(22,i+3,"",wcf_table);
            	   
            	   content24=new Label(23,i+3,"",wcf_table);
            	   content25=new Label(24,i+3,"",wcf_table);
        	   }else{
        		   float averageCommTime=0;
                   float averageRunTime=0;
                   float averageLiquidProduction=0;
                   float averageOilProduction=0;
                   float averageWaterProduction=0;
        		   if(commTimeRecords>0){
        			   averageCommTime=StringManagerUtils.stringToFloat(sumCommTime/commTimeRecords+"",2);
        		   }
        		   if(runTimeRecords>0){
        			   averageRunTime=StringManagerUtils.stringToFloat(sumRunTime/runTimeRecords+"",2);
        		   }
        		   if(liquidProductionRecords>0){
        			   averageLiquidProduction=StringManagerUtils.stringToFloat(sumLiquidProduction/liquidProductionRecords+"",2);
        		   }
        		   if(oilProductionRecords>0){
        			   averageOilProduction=StringManagerUtils.stringToFloat(sumOilProduction/oilProductionRecords+"",2);
        		   }
        		   if(waterProductionRecords>0){
        			   averageWaterProduction=StringManagerUtils.stringToFloat(sumWaterProduction/waterProductionRecords+"",2);
        		   }
            	   content1=new Label(0,i+3,"平均",wcf_table);
            	   content2=new Label(1,i+3,"",wcf_table);
            	   content3=new Label(2,i+3,"",wcf_table);
            	   
            	   content4=new Label(3,i+3,averageCommTime+"",wcf_table);
            	   content5=new Label(4,i+3,"",wcf_table);
            	   content6=new Label(5,i+3,"",wcf_table);
            	   
            	   content7=new Label(6,i+3,averageRunTime+"",wcf_table);
            	   content8=new Label(7,i+3,"",wcf_table);
            	   content9=new Label(8,i+3,"",wcf_table);
            	   
            	   content10=new Label(9,i+3,"",wcf_table);
            	   content11=new Label(10,i+3,"",wcf_table);
            	   
            	   
            	   content12=new Label(11,i+3,averageLiquidProduction+"",wcf_table);
            	   content13=new Label(12,i+3,averageOilProduction+"",wcf_table);
            	   content14=new Label(13,i+3,averageWaterProduction+"",wcf_table);
            	   content15=new Label(14,i+3,"",wcf_table);
            	   content16=new Label(15,i+3,"",wcf_table);
            	   
            	   content17=new Label(16,i+3,"",wcf_table);
            	   content18=new Label(17,i+3,"",wcf_table);
            	   content19=new Label(18,i+3,"",wcf_table);
            	   
            	   content20=new Label(19,i+3,"",wcf_table);
            	   content21=new Label(20,i+3,"",wcf_table);
            	   content22=new Label(21,i+3,"",wcf_table);
            	   content23=new Label(22,i+3,"",wcf_table);
            	   
            	   content24=new Label(23,i+3,"",wcf_table);
            	   content25=new Label(24,i+3,"",wcf_table);
        	   }
        	   sheetOne.addCell(content1);
        	   sheetOne.addCell(content2);
        	   sheetOne.addCell(content3);
        	   sheetOne.addCell(content4);
        	   sheetOne.addCell(content5);
        	   sheetOne.addCell(content6);
        	   sheetOne.addCell(content7);
        	   sheetOne.addCell(content8);
        	   sheetOne.addCell(content9);
        	   sheetOne.addCell(content10);
        	   sheetOne.addCell(content11);
        	   sheetOne.addCell(content12);
        	   sheetOne.addCell(content13);
        	   sheetOne.addCell(content14);
        	   sheetOne.addCell(content15);
        	   sheetOne.addCell(content16);
        	   sheetOne.addCell(content17);
        	   sheetOne.addCell(content18);
        	   sheetOne.addCell(content19);
        	   sheetOne.addCell(content20);
        	   sheetOne.addCell(content21);
        	   sheetOne.addCell(content22);
        	   sheetOne.addCell(content23);
        	   sheetOne.addCell(content24);
        	   sheetOne.addCell(content25);
           }
           //写入数据并关闭文件     
           book.write();     
           book.close();
	    } catch (Exception e) {  
	        // TODO: handle exception  
	        e.printStackTrace();  
	    }
		return null;
	}
	
	/** <p>描述：导出采出井日报表json数据方法</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportPCPDailyReportData")
	public String exportPCPDailyReportData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		String wellName = ParamUtils.getParameter(request, "wellName");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate= ParamUtils.getParameter(request, "endDate");
		String tableName="tbl_pcpdailycalculationdata";
		if (!StringUtils.isNotBlank(orgId)) {
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		if (!StringManagerUtils.isNotNull(endDate)) {
			String sql = " select * from (select  to_char(t.calDate,'yyyy-mm-dd') from "+tableName+" t where1=1";
			if(StringManagerUtils.isNotNull(wellName)){
				sql+= " and t.wellname='"+wellName+"' ";
			}	
			sql+= "order by calDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
			if(!StringManagerUtils.isNotNull(startDate)){
				startDate=endDate;
			}
		}
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=endDate;
		}
		
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setJssj(calculateDate);
		json = reportDataManagerService.exportPCPDailyReportData(pager, orgId, wellName, startDate,endDate);
		
		JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+json+"}");//解析数据
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		try {
	        /** 
	         * web端生成保存打开excel弹出框 
	         */  
	        response.setContentType("application/x-msdownload;charset=gbk");      
	        String fileName = "螺杆泵井";
	        if(StringManagerUtils.isNotNull(wellName)){
	        	fileName+=wellName;
	        }
	        fileName+="生产报表-"+startDate;
	        if(!startDate.equalsIgnoreCase(endDate)){
	        	fileName+="~"+endDate;
	        }
	        fileName+=".xls";
	        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
	        OutputStream os = response.getOutputStream();     
	        //打开文件    
	        WritableWorkbook book= Workbook.createWorkbook(os);     
	        WritableSheet sheetOne=book.createSheet("生产报表",0);    
	        
	        WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 20,WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色    
	        WritableFont wf_head = new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色    
	        WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 11,WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色    
	   
	        WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义    
	        wcf_title.setBackground(jxl.format.Colour.WHITE); // 设置单元格的背景颜色    
	        wcf_title.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式    
	        wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); // 设置垂直对齐方式   
	        wcf_title.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.BLACK); //设置边框    
	        
	        WritableCellFormat wcf_head = new WritableCellFormat(wf_head);     
	        wcf_head.setBackground(jxl.format.Colour.GRAY_25);    
	        wcf_head.setAlignment(jxl.format.Alignment.CENTRE);     
	        wcf_head.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); // 设置垂直对齐方式   
	        wcf_head.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.BLACK); 
	        wcf_head.setWrap(true);
	   
	        WritableCellFormat wcf_table = new WritableCellFormat(wf_table);    
	        wcf_table.setBackground(jxl.format.Colour.WHITE);     
	        wcf_table.setAlignment(jxl.format.Alignment.CENTRE);     
	        wcf_table.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); // 设置垂直对齐方式   
	        wcf_table.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.BLACK);     
	               
	        sheetOne.setColumnView(0, 10); // 设置列的宽度    
	        sheetOne.setColumnView(1, 15); // 设置列的宽度    
	        sheetOne.setColumnView(2, 15); // 设置列的宽度    
	        
	        sheetOne.setColumnView(3, 15); // 设置列的宽度    
	        sheetOne.setColumnView(4, 30); // 设置列的宽度    
	        sheetOne.setColumnView(5, 15); // 设置列的宽度    
	        
	        sheetOne.setColumnView(6, 15); // 设置列的宽度    
	        sheetOne.setColumnView(7, 30); // 设置列的宽度    
	        sheetOne.setColumnView(8, 15); // 设置列的宽度    
	        
	        sheetOne.setColumnView(9, 15); // 设置列的宽度    
	        sheetOne.setColumnView(10, 15); // 设置列的宽度   
	        sheetOne.setColumnView(11, 15); // 设置列的宽度   
	        sheetOne.setColumnView(12, 15); // 设置列的宽度   
	        sheetOne.setColumnView(13, 15); // 设置列的宽度 
	        
	        sheetOne.setColumnView(14, 15); // 设置列的宽度
	        sheetOne.setColumnView(15, 20); // 设置列的宽度
	        
	        sheetOne.setColumnView(16, 15); // 设置列的宽度
	        
	        sheetOne.setColumnView(17, 15); // 设置列的宽度
	   
	        //在Label对象的构造子中指名单元格位置是第一列第一行(0,0)     
	        //以及单元格内容为test
	        ConfigFile configFile=Config.getInstance().configFile;
	        String productionUnit="t/d"; 
	        if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
	        	productionUnit="t/d"; 
			}else{
				productionUnit="m^3/d"; 
			}
	        
	        Label title=new Label(0,0,"螺杆泵井生产报表",wcf_title);
	        
	        Label header1=new Label(0,1,"序号",wcf_head);
	        Label header2=new Label(1,1,"井名",wcf_head);
	        Label header3=new Label(2,1,"日期",wcf_head);
	        
	        Label header4=new Label(3,1,"通信",wcf_head);
	        Label header4_1=new Label(3,2,"在线时间(h)",wcf_head);
	        Label header4_2=new Label(4,2,"在线区间",wcf_head);
	        Label header4_3=new Label(5,2,"在线时率(小数)",wcf_head);
	        
	        Label header5=new Label(6,1,"时率",wcf_head);
	        Label header5_1=new Label(6,2,"运行时间(h)",wcf_head);
	        Label header5_2=new Label(7,2,"运行区间",wcf_head);
	        Label header5_3=new Label(8,2,"运行时率(小数)",wcf_head);
	       
	       Label header6=new Label(9,1,"产量",wcf_head);
	       Label header6_1=new Label(9,2,"产液量("+productionUnit+")",wcf_head);
	       Label header6_2=new Label(10,2,"产油量("+productionUnit+")",wcf_head);
	       Label header6_3=new Label(11,2,"产水量("+productionUnit+")",wcf_head);
	       Label header6_4=new Label(12,2,"含水率(%)",wcf_head);
	       Label header6_5=new Label(13,2,"转速(r/min)",wcf_head);
	       
	       Label header7=new Label(14,1,"效率",wcf_head);
	       Label header7_1=new Label(14,2,"系统效率(%)",wcf_head);
	       Label header7_2=new Label(15,2,"吨液百米耗电量(kW·h/100·t)",wcf_head);
	       
	       Label header8=new Label(16,1,"日用电量(kW·h)",wcf_head);
	       
	       Label header9=new Label(17,1,"备注",wcf_head);
	       
           sheetOne.addCell(title);   
           sheetOne.addCell(header1);     
           sheetOne.addCell(header2);
           sheetOne.addCell(header3);     
           sheetOne.addCell(header4);     
           sheetOne.addCell(header4_1);     
           sheetOne.addCell(header4_2);     
           sheetOne.addCell(header4_3); 
           sheetOne.addCell(header5);
           sheetOne.addCell(header5_1);     
           sheetOne.addCell(header5_2);   
           sheetOne.addCell(header5_3);  
           sheetOne.addCell(header6);
           sheetOne.addCell(header6_1);     
           sheetOne.addCell(header6_2);
           sheetOne.addCell(header6_3);
           sheetOne.addCell(header6_4);
           sheetOne.addCell(header6_5);
           sheetOne.addCell(header7);
           sheetOne.addCell(header7_1);
           sheetOne.addCell(header7_2);
           sheetOne.addCell(header8);
           sheetOne.addCell(header9);
           
         //合： 第1列第1行  到 第13列第1行     
           sheetOne.mergeCells(0, 0, 17, 0);   
           
           sheetOne.mergeCells(0, 1, 0, 2);     
           sheetOne.mergeCells(1, 1, 1, 2);
           sheetOne.mergeCells(2, 1, 2, 2);
           sheetOne.mergeCells(3, 1, 5, 1);
           sheetOne.mergeCells(6, 1, 8, 1);
           sheetOne.mergeCells(9, 1, 13, 1);
           sheetOne.mergeCells(14, 1, 15, 1);
           sheetOne.mergeCells(16, 1, 16, 2);
           sheetOne.mergeCells(17, 1, 17, 2);
           /*动态数据   */ 
           float sumCommTime=0;
           float sumRunTime=0;
           float sumLiquidProduction=0;
           float sumOilProduction=0;
           float sumWaterProduction=0;
           
           int commTimeRecords=0;
           int runTimeRecords=0;
           int liquidProductionRecords=0;
           int oilProductionRecords=0;
           int waterProductionRecords=0;
           for(int i=0;i<=jsonArray.size()+1;i++){
        	   Label content1=null;
        	   Label content2=null;
        	   Label content3=null;
        	   Label content4=null;
        	   Label content5=null;
        	   Label content6=null;
        	   Label content7=null;
        	   Label content8=null;
        	   Label content9=null;
        	   Label content10=null;
        	   Label content11=null;
        	   Label content12=null;
        	   Label content13=null;
        	   Label content14=null;
        	   Label content15=null;
        	   Label content16=null;
        	   Label content17=null;
        	   Label content18=null;
        	   if(i<jsonArray.size()){
        		   JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
        		   sumCommTime+=StringManagerUtils.stringToFloat(everydata.getString("commTime"));
        		   sumRunTime+=StringManagerUtils.stringToFloat(everydata.getString("runTime"));
        		   sumLiquidProduction+=StringManagerUtils.stringToFloat(everydata.getString("liquidProduction"));
        		   sumOilProduction+=StringManagerUtils.stringToFloat(everydata.getString("oilProduction"));
        		   sumWaterProduction+=StringManagerUtils.stringToFloat(everydata.getString("waterProduction"));
            	   
            	   if(StringManagerUtils.stringToFloat(everydata.getString("commTime"))>0){
            		   commTimeRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("runTime"))>0){
            		   runTimeRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("liquidProduction"))>0){
            		   liquidProductionRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("oilProduction"))>0){
            		   oilProductionRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("waterProduction"))>0){
            		   waterProductionRecords+=1;
            	   }
            	   
            	   content1=new Label(0,i+3,i+1+"",wcf_table);
            	   content2=new Label(1,i+3,everydata.getString("wellName"),wcf_table);
            	   content3=new Label(2,i+3,everydata.getString("calculateDate"),wcf_table);
            	   
            	   content4=new Label(3,i+3,everydata.getString("commTime"),wcf_table);
            	   content5=new Label(4,i+3,everydata.getString("commRange"),wcf_table);
            	   content6=new Label(5,i+3,everydata.getString("commTimeEfficiency"),wcf_table);
            	   
            	   content7=new Label(6,i+3,everydata.getString("runTime"),wcf_table);
            	   content8=new Label(7,i+3,everydata.getString("runRange"),wcf_table);
            	   content9=new Label(8,i+3,everydata.getString("runTimeEfficiency"),wcf_table);
            	   
            	   content10=new Label(9,i+3,everydata.getString("liquidProduction"),wcf_table);
            	   content11=new Label(10,i+3,everydata.getString("oilProduction"),wcf_table);
            	   content12=new Label(11,i+3,everydata.getString("waterProduction"),wcf_table);
            	   content13=new Label(12,i+3,everydata.getString("volumeWaterCut"),wcf_table);
            	   content14=new Label(13,i+3,everydata.getString("rpm"),wcf_table);
            	   
            	   content15=new Label(14,i+3,everydata.getString("systemEfficiency"),wcf_table);
            	   content16=new Label(15,i+3,everydata.getString("energyPer100mLift"),wcf_table);
            	   
            	   content17=new Label(16,i+3,everydata.getString("todayKWattH"),wcf_table);
            	   
            	   content18=new Label(17,i+3,everydata.getString("remark"),wcf_table);
        	   }else if(i==jsonArray.size()){
        		   sumCommTime=StringManagerUtils.stringToFloat(sumCommTime+"",2);
        		   sumRunTime=StringManagerUtils.stringToFloat(sumRunTime+"",2);
        		   sumLiquidProduction=StringManagerUtils.stringToFloat(sumLiquidProduction+"",2);
        		   sumOilProduction=StringManagerUtils.stringToFloat(sumOilProduction+"",2);
        		   sumWaterProduction=StringManagerUtils.stringToFloat(sumWaterProduction+"",2);
            	   content1=new Label(0,i+3,"合计",wcf_table);
            	   content2=new Label(1,i+3,"",wcf_table);
            	   content3=new Label(2,i+3,"",wcf_table);
            	   
            	   content4=new Label(3,i+3,sumCommTime+"",wcf_table);
            	   content5=new Label(4,i+3,"",wcf_table);
            	   content6=new Label(5,i+3,"",wcf_table);
            	   
            	   content7=new Label(6,i+3,sumRunTime+"",wcf_table);
            	   content8=new Label(7,i+3,"",wcf_table);
            	   content9=new Label(8,i+3,"",wcf_table);
            	   
            	   content10=new Label(9,i+3,sumLiquidProduction+"",wcf_table);
            	   content11=new Label(10,i+3,sumOilProduction+"",wcf_table);
            	   content12=new Label(11,i+3,sumWaterProduction+"",wcf_table);
            	   content13=new Label(12,i+3,"",wcf_table);
            	   content14=new Label(13,i+3,"",wcf_table);
            	   
            	   content15=new Label(14,i+3,"",wcf_table);
            	   content16=new Label(15,i+3,"",wcf_table);
            	   
            	   content17=new Label(16,i+3,"",wcf_table);
            	   content18=new Label(17,i+3,"",wcf_table);
        	   }else{
        		   float averageCommTime=0;
                   float averageRunTime=0;
                   float averageLiquidProduction=0;
                   float averageOilProduction=0;
                   float averageWaterProduction=0;
        		   if(commTimeRecords>0){
        			   averageCommTime=StringManagerUtils.stringToFloat(sumCommTime/commTimeRecords+"",2);
        		   }
        		   if(runTimeRecords>0){
        			   averageRunTime=StringManagerUtils.stringToFloat(sumRunTime/runTimeRecords+"",2);
        		   }
        		   if(liquidProductionRecords>0){
        			   averageLiquidProduction=StringManagerUtils.stringToFloat(sumLiquidProduction/liquidProductionRecords+"",2);
        		   }
        		   if(oilProductionRecords>0){
        			   averageOilProduction=StringManagerUtils.stringToFloat(sumOilProduction/oilProductionRecords+"",2);
        		   }
        		   if(waterProductionRecords>0){
        			   averageWaterProduction=StringManagerUtils.stringToFloat(sumWaterProduction/waterProductionRecords+"",2);
        		   }
        		   content1=new Label(0,i+3,"合计",wcf_table);
            	   content2=new Label(1,i+3,"",wcf_table);
            	   content3=new Label(2,i+3,"",wcf_table);
            	   
            	   content4=new Label(3,i+3,averageCommTime+"",wcf_table);
            	   content5=new Label(4,i+3,"",wcf_table);
            	   content6=new Label(5,i+3,"",wcf_table);
            	   
            	   content7=new Label(6,i+3,averageRunTime+"",wcf_table);
            	   content8=new Label(7,i+3,"",wcf_table);
            	   content9=new Label(8,i+3,"",wcf_table);
            	   
            	   content10=new Label(9,i+3,averageLiquidProduction+"",wcf_table);
            	   content11=new Label(10,i+3,averageOilProduction+"",wcf_table);
            	   content12=new Label(11,i+3,averageWaterProduction+"",wcf_table);
            	   content13=new Label(12,i+3,"",wcf_table);
            	   content14=new Label(13,i+3,"",wcf_table);
            	   
            	   content15=new Label(14,i+3,"",wcf_table);
            	   content16=new Label(15,i+3,"",wcf_table);
            	   
            	   content17=new Label(16,i+3,"",wcf_table);
            	   
            	   content18=new Label(17,i+3,"",wcf_table);
        	   }
        	   sheetOne.addCell(content1);
        	   sheetOne.addCell(content2);
        	   sheetOne.addCell(content3);
        	   sheetOne.addCell(content4);
        	   sheetOne.addCell(content5);
        	   sheetOne.addCell(content6);
        	   sheetOne.addCell(content7);
        	   sheetOne.addCell(content8);
        	   sheetOne.addCell(content9);
        	   sheetOne.addCell(content10);
        	   sheetOne.addCell(content11);
        	   sheetOne.addCell(content12);
        	   sheetOne.addCell(content13);
        	   sheetOne.addCell(content14);
        	   sheetOne.addCell(content15);
        	   sheetOne.addCell(content16);
        	   sheetOne.addCell(content17);
        	   sheetOne.addCell(content18);
           }
           //写入数据并关闭文件     
           book.write();     
           book.close();
	    } catch (Exception e) {  
	        // TODO: handle exception  
	        e.printStackTrace();  
	    }
		return null;
	}
	
	@RequestMapping("/showTouchtest")
	public String showTouchtest() throws Exception {
		String json = "{success:true,totals:4,items:[{\"name\":\"Jean Luc2\",\"email\":\"jeanluc.picard@enterprise.com\",\"phone\": \"555-111-1111\"},{\"name\":\"Worf2\",\"email\":\"worf.moghsson@enterprise.com\",\"phone\":\"555-222-2222\"},{\"name\":\"Deanna2\",\"email\":\"deanna.troi@enterprise.com\",\"phone\":\"555-333-3333\" },{\"name\":\"Data2\",\"email\":\"mr.data@enterprise.com\",\"phone\":\"555-444-4444\"}]}";
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
			e.printStackTrace();
		}
		return null;
	}
	

	/**<p>描述：采出井日报表Excel文件</p>
	 * 
	 * @return null
	 * @throws Exception
	 *            
	 */
	@RequestMapping("/exportExcelReportPumpingUnitData")
	public String exportExcelReportPumpingUnitData() throws Exception {
		// TODO Auto-generated method stub
		log.debug("ReportPumpingUnit enter==");
		String heads = ParamUtils.getParameter(request, "heads");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName=ParamUtils.getParameter(request, "fileName");
		heads = java.net.URLDecoder.decode(heads, "utf-8");
		fields = java.net.URLDecoder.decode(fields, "utf-8");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = ParamUtils.getParameter(request, "wellName");
		calculateDate = ParamUtils.getParameter(request, "calculateDate");
		orgId=this.findCurrentUserOrgIdInfo(orgId);
		wellName = java.net.URLDecoder.decode(wellName, "utf-8");
		if (StringManagerUtils.isNotNull(wellName)) {
			v.add(wellName);
		}else{
			v.add(null);
		}
		String addDate = "";
		if (!"".equals(calculateDate) && null != calculateDate && calculateDate.length() > 0) {
			v.add(calculateDate);
			addDate = StringManagerUtils.formatStringDate(StringManagerUtils.addDate(StringManagerUtils.stringToDate(calculateDate)));
			v.add(addDate);
		} else {
			v.add(StringManagerUtils.getCurrentTime());
			addDate = StringManagerUtils.formatStringDate(StringManagerUtils.addDate(StringManagerUtils.stringToDate(StringManagerUtils.getCurrentTime())));
			v.add(addDate);
		}
		// String ReportPumpingUnit = "采出---抽油机井---日报表";
		this.commonDataService.exportDataExcel(response, fileName, "采出井日报数据", null, heads, fields, orgId, "pumpUnitDayReport", v);
		return null;
	}
	
	@RequestMapping("/getWellList")
	public String getWellList() throws Exception {
		String json = "";
		String orgId = ParamUtils.getParameter(request, "orgId");
		String wellName = ParamUtils.getParameter(request, "wellName");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = reportDataManagerService.getWellList(orgId,wellName,deviceType);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}


	public static String formatStringDate(Calendar Month) {
		Month = Calendar.getInstance();
		String time = null;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		time = sd.format(Month.getTime());
		return time;
	}


	
	public String getWellName() {
		return wellName;
	}

	public void setEellName(String wellName) {
		this.wellName = wellName;
	}

	public String getCalculateDate() {
		return calculateDate;
	}

	public void setCalculateDate(String calculateDate) {
		this.calculateDate = calculateDate;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getCalculateEndDate() {
		return calculateEndDate;
	}

	public void setCalculateEndDate(String calculateEndDate) {
		this.calculateEndDate = calculateEndDate;
	}

	public void setWellName(String wellName) {
		this.wellName = wellName;
	}

}
