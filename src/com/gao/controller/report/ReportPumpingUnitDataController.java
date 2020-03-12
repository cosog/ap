package com.gao.controller.report;

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

import com.gao.controller.base.BaseController;
import com.gao.model.User;
import com.gao.service.base.CommonDataService;
import com.gao.service.report.ReportProductionWellService;
import com.gao.utils.Page;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;

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
@RequestMapping("/reportPumpingUnitDataController")
@Scope("prototype")
public class ReportPumpingUnitDataController extends BaseController {
	private static Log log = LogFactory.getLog(ReportPumpingUnitDataController.class);
	private static final long serialVersionUID = 1L;
	private String wellName = "";
	private String calculateDate;
	private int limit = 10;
	private String orgId;
	private int page = 1;
	@Autowired
	private CommonDataService commonDataService;
	@Autowired
	private ReportProductionWellService reportProductionWellService;


	/** <p>描述：采出井日报表json数据方法</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showDiagnosisDailyReportData")
	public String showDiagnosisDailyReportData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = ParamUtils.getParameter(request, "wellName");
		calculateDate = ParamUtils.getParameter(request, "calculateDate");
		String wellType = ParamUtils.getParameter(request, "wellType");
		String tableName="tbl_rpc_total_day";
		if("400".equals(wellType)){
			tableName="tbl_pcp_total_day";
		}
		if (!StringUtils.isNotBlank(orgId)) {
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		if (StringManagerUtils.isNotNull(wellName)) {
			v.add(wellName);
		}else{
			v.add(null);
		}
		if (!StringUtils.isNotBlank(calculateDate)) {
			String sql = " select * from (select  to_char(t.calculateDate,'yyyy-mm-dd') from "+tableName+" t order by calculateDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.findCallSql(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				calculateDate = list.get(0).toString();
			} else {
				calculateDate = StringManagerUtils.getCurrentTime();
			}
		}
		v.add(calculateDate);
		
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setJssj(calculateDate);
		if("400".equals(wellType)){
			json = reportProductionWellService.showPCPDailyReportData(pager, orgId, wellName, calculateDate);
		}else{
			json = reportProductionWellService.showRPCDailyReportData(pager, orgId, wellName, calculateDate,wellType);
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
	@RequestMapping("/exportDiagnosisDailyReportExcelData")
	public String exportDiagnosisDailyReportExcelData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = ParamUtils.getParameter(request, "wellName");
		calculateDate = ParamUtils.getParameter(request, "calculateDate");
		String wellType = ParamUtils.getParameter(request, "wellType");
		if (!StringUtils.isNotBlank(orgId)) {
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		if (StringManagerUtils.isNotNull(wellName)) {
			v.add(wellName);
		}else{
			v.add(null);
		}
		if (!StringUtils.isNotBlank(calculateDate)) {
			String sql = " select * from (select  to_char(t.calculateDate,'yyyy-mm-dd') from tbl_rpc_total_day t order by calculateDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.reportDateJssj(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				calculateDate = list.get(0).toString();
			} else {
				calculateDate = StringManagerUtils.getCurrentTime();
			}
		}
		v.add(calculateDate);
		
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setJssj(calculateDate);
		json = reportProductionWellService.exportDiagnosisDailyReportExcelData(pager, orgId, wellName, calculateDate,wellType);
		
		JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+json+"}");//解析数据
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		try {
	        /** 
	         * web端生成保存打开excel弹出框 
	         */  
	        response.setContentType("application/x-msdownload;charset=gbk");      
	        String fileName = "抽油机井生产报表-"+calculateDate+".xls";
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
	        sheetOne.setColumnView(22, 15); // 设置列的宽度
	        sheetOne.setColumnView(23, 15); // 设置列的宽度
	        sheetOne.setColumnView(24, 20); // 设置列的宽度
	        
	        sheetOne.setColumnView(25, 15); // 设置列的宽度
	        
	        sheetOne.setColumnView(26, 15); // 设置列的宽度
	   
	        //在Label对象的构造子中指名单元格位置是第一列第一行(0,0)     
	        //以及单元格内容为test
	        Label title=new Label(0,0,"抽油机生产报表",wcf_title);
	        
	        Label header1=new Label(0,1,"序号",wcf_head);
	        Label header2=new Label(1,1,"井名",wcf_head);
	        Label header3=new Label(2,1,"日期",wcf_head);
	        
	        Label header4=new Label(3,1,"通信",wcf_head);
	        Label header4_1=new Label(3,2,"在线时间(h)",wcf_head);
	        Label header4_2=new Label(4,2,"在线区间",wcf_head);
	        Label header4_3=new Label(5,2,"在线时率(%)",wcf_head);
	        
	        Label header5=new Label(6,1,"时率",wcf_head);
	        Label header5_1=new Label(6,2,"运行时间(h)",wcf_head);
	        Label header5_2=new Label(7,2,"运行区间",wcf_head);
	        Label header5_3=new Label(8,2,"运行时率(%)",wcf_head);
	           
	       Label header6=new Label(9,1,"工况",wcf_head);
	       Label header6_1=new Label(9,2,"功图工况",wcf_head);
	       Label header6_2=new Label(10,2,"优化建议",wcf_head);
	       
	       Label header7=new Label(11,1,"产量",wcf_head);
	       Label header7_1=new Label(11,2,"产液量(t/d)",wcf_head);
	       Label header7_2=new Label(12,2,"产油量(t/d)",wcf_head);
	       Label header7_3=new Label(13,2,"产水量(t/d)",wcf_head);
	       Label header7_4=new Label(14,2,"含水率(%)",wcf_head);
	       Label header7_5=new Label(15,2,"充满系数",wcf_head);
	       
	       Label header8=new Label(16,1,"平衡",wcf_head);
	       Label header8_1=new Label(16,2,"功率平衡状态",wcf_head);
	       Label header8_2=new Label(17,2,"功率平衡度(%)",wcf_head);
	       Label header8_3=new Label(18,2,"电流平衡状态",wcf_head);
	       Label header8_4=new Label(19,2,"电流平衡度(%)",wcf_head);
	       Label header8_5=new Label(20,2,"移动距离(m)",wcf_head);
	       
	       Label header9=new Label(21,1,"效率",wcf_head);
	       Label header9_1=new Label(21,2,"系统效率(%)",wcf_head);
	       Label header9_2=new Label(22,2,"地面效率(%)",wcf_head);
	       Label header9_3=new Label(23,2,"井下效率(%)",wcf_head);
	       Label header9_4=new Label(24,2,"吨液百米耗电量(kW·h/100·t)",wcf_head);
	       
	       Label header10=new Label(25,1,"日用电量(kW·h)",wcf_head);
	       
	       Label header11=new Label(26,1,"备注",wcf_head);
	       
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
           sheetOne.addCell(header8_4);
           sheetOne.addCell(header8_5);
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
           sheetOne.mergeCells(16, 1, 20, 1);
           sheetOne.mergeCells(21, 1, 24, 1);
           sheetOne.mergeCells(25, 1, 25, 2);
           sheetOne.mergeCells(26, 1, 26, 2);
           /*动态数据   */ 
           float sumCommTime=0;
           float sumRunTime=0;
           float sumLiquidWeightProduction=0;
           float sumOilWeightProduction=0;
           float sumWaterWeightProduction=0;
           
           int commTimeRecords=0;
           int runTimeRecords=0;
           int liquidWeightProductionRecords=0;
           int oilWeightProductionRecords=0;
           int waterWeightProductionRecords=0;
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
        	   Label content26=null;
        	   Label content27=null;
        	   if(i<jsonArray.size()){
        		   JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
        		   sumCommTime+=StringManagerUtils.stringToFloat(everydata.getString("commTime"));
        		   sumRunTime+=StringManagerUtils.stringToFloat(everydata.getString("runTime"));
        		   sumLiquidWeightProduction+=StringManagerUtils.stringToFloat(everydata.getString("liquidWeightProduction"));
        		   sumOilWeightProduction+=StringManagerUtils.stringToFloat(everydata.getString("oilWeightProduction"));
        		   sumWaterWeightProduction+=StringManagerUtils.stringToFloat(everydata.getString("waterWeightProduction"));
            	   
            	   if(StringManagerUtils.stringToFloat(everydata.getString("commTime"))>0){
            		   commTimeRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("runTime"))>0){
            		   runTimeRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("liquidWeightProduction"))>0){
            		   liquidWeightProductionRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("oilWeightProduction"))>0){
            		   oilWeightProductionRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("waterWeightProduction"))>0){
            		   waterWeightProductionRecords+=1;
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
            	   
            	   content10=new Label(9,i+3,everydata.getString("workingConditionName"),wcf_table);
            	   content11=new Label(10,i+3,everydata.getString("optimizationSuggestion"),wcf_table);
            	   
            	   
            	   content12=new Label(11,i+3,everydata.getString("liquidWeightProduction"),wcf_table);
            	   content13=new Label(12,i+3,everydata.getString("oilWeightProduction"),wcf_table);
            	   content14=new Label(13,i+3,everydata.getString("waterWeightProduction"),wcf_table);
            	   content15=new Label(14,i+3,everydata.getString("waterCut"),wcf_table);
            	   content16=new Label(15,i+3,everydata.getString("fullnesscoEfficient"),wcf_table);
            	   
            	   content17=new Label(16,i+3,everydata.getString("wattDegreeBalanceLevel"),wcf_table);
            	   content18=new Label(17,i+3,everydata.getString("wattDegreeBalance"),wcf_table);
            	   content19=new Label(18,i+3,everydata.getString("iDegreeBalanceLevel"),wcf_table);
            	   content20=new Label(19,i+3,everydata.getString("iDegreeBalance"),wcf_table);
            	   content21=new Label(20,i+3,everydata.getString("deltaRadius"),wcf_table);
            	   
            	   content22=new Label(21,i+3,everydata.getString("systemEfficiency"),wcf_table);
            	   content23=new Label(22,i+3,everydata.getString("surfaceSystemEfficiency"),wcf_table);
            	   content24=new Label(23,i+3,everydata.getString("welldownSystemEfficiency"),wcf_table);
            	   content25=new Label(24,i+3,everydata.getString("powerConsumptionPerthm"),wcf_table);
            	   
            	   content26=new Label(25,i+3,everydata.getString("todayWattEnergy"),wcf_table);
            	   content27=new Label(26,i+3,everydata.getString("remark"),wcf_table);
        	   }else if(i==jsonArray.size()){
        		   sumCommTime=StringManagerUtils.stringToFloat(sumCommTime+"",2);
        		   sumRunTime=StringManagerUtils.stringToFloat(sumRunTime+"",2);
        		   sumLiquidWeightProduction=StringManagerUtils.stringToFloat(sumLiquidWeightProduction+"",2);
        		   sumOilWeightProduction=StringManagerUtils.stringToFloat(sumOilWeightProduction+"",2);
        		   sumWaterWeightProduction=StringManagerUtils.stringToFloat(sumWaterWeightProduction+"",2);
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
            	   
            	   
            	   content12=new Label(11,i+3,sumLiquidWeightProduction+"",wcf_table);
            	   content13=new Label(12,i+3,sumOilWeightProduction+"",wcf_table);
            	   content14=new Label(13,i+3,sumWaterWeightProduction+"",wcf_table);
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
            	   
            	   content26=new Label(25,i+3,"",wcf_table);
            	   content27=new Label(26,i+3,"",wcf_table);
        	   }else{
        		   float averageCommTime=0;
                   float averageRunTime=0;
                   float averageLiquidWeightProduction=0;
                   float averageOilWeightProduction=0;
                   float averageWaterWeightProduction=0;
        		   if(commTimeRecords>0){
        			   averageCommTime=StringManagerUtils.stringToFloat(sumCommTime/commTimeRecords+"",2);
        		   }
        		   if(runTimeRecords>0){
        			   averageRunTime=StringManagerUtils.stringToFloat(sumRunTime/runTimeRecords+"",2);
        		   }
        		   if(liquidWeightProductionRecords>0){
        			   averageLiquidWeightProduction=StringManagerUtils.stringToFloat(sumLiquidWeightProduction/liquidWeightProductionRecords+"",2);
        		   }
        		   if(oilWeightProductionRecords>0){
        			   averageOilWeightProduction=StringManagerUtils.stringToFloat(sumOilWeightProduction/oilWeightProductionRecords+"",2);
        		   }
        		   if(waterWeightProductionRecords>0){
        			   averageWaterWeightProduction=StringManagerUtils.stringToFloat(sumWaterWeightProduction/waterWeightProductionRecords+"",2);
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
            	   
            	   
            	   content12=new Label(11,i+3,averageLiquidWeightProduction+"",wcf_table);
            	   content13=new Label(12,i+3,averageOilWeightProduction+"",wcf_table);
            	   content14=new Label(13,i+3,averageWaterWeightProduction+"",wcf_table);
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
            	   
            	   content26=new Label(25,i+3,"",wcf_table);
            	   content27=new Label(26,i+3,"",wcf_table);
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
        	   sheetOne.addCell(content26);
        	   sheetOne.addCell(content27);
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
	@RequestMapping("/exportScrewPumpDailyReportExcelData")
	public String exportScrewPumpDailyReportExcelData() throws Exception {
		log.debug("reportOutputWell enter==");
		Vector<String> v = new Vector<String>();
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = ParamUtils.getParameter(request, "wellName");
		calculateDate = ParamUtils.getParameter(request, "calculateDate");
		String wellType = ParamUtils.getParameter(request, "wellType");
		if (!StringUtils.isNotBlank(orgId)) {
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		if (StringManagerUtils.isNotNull(wellName)) {
			v.add(wellName);
		}else{
			v.add(null);
		}
		if (!StringUtils.isNotBlank(calculateDate)) {
			String sql = " select * from (select  to_char(t.calculateDate,'yyyy-mm-dd') from tbl_pcp_total_day t order by calculateDate desc) where rownum=1 ";
			List<?> list = this.commonDataService.reportDateJssj(sql);
			if (list.size() > 0 && list.get(0)!=null ) {
				calculateDate = list.get(0).toString();
			} else {
				calculateDate = StringManagerUtils.getCurrentTime();
			}
		}
		v.add(calculateDate);
		
		String json = "";
		this.pager = new Page("pagerForm", request);
		pager.setJssj(calculateDate);
		json = reportProductionWellService.exportDiagnosisDailyReportExcelData(pager, orgId, wellName, calculateDate,wellType);
		
		JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+json+"}");//解析数据
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		try {
	        /** 
	         * web端生成保存打开excel弹出框 
	         */  
	        response.setContentType("application/x-msdownload;charset=gbk");      
	        String fileName = "螺杆泵井生产报表-"+calculateDate+".xls";
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
	        sheetOne.setColumnView(15, 15); // 设置列的宽度
	        sheetOne.setColumnView(16, 15); // 设置列的宽度
	        
	        sheetOne.setColumnView(17, 15); // 设置列的宽度
	        sheetOne.setColumnView(18, 20); // 设置列的宽度
	        
	        sheetOne.setColumnView(19, 15); // 设置列的宽度
	        
	        sheetOne.setColumnView(20, 15); // 设置列的宽度
	   
	        //在Label对象的构造子中指名单元格位置是第一列第一行(0,0)     
	        //以及单元格内容为test
	        Label title=new Label(0,0,"螺杆泵生产报表",wcf_title);
	        
	        Label header1=new Label(0,1,"序号",wcf_head);
	        Label header2=new Label(1,1,"井名",wcf_head);
	        Label header3=new Label(2,1,"日期",wcf_head);
	        
	        Label header4=new Label(3,1,"通信",wcf_head);
	        Label header4_1=new Label(3,2,"在线时间(h)",wcf_head);
	        Label header4_2=new Label(4,2,"在线区间",wcf_head);
	        Label header4_3=new Label(5,2,"在线时率(%)",wcf_head);
	        
	        Label header5=new Label(6,1,"时率",wcf_head);
	        Label header5_1=new Label(6,2,"运行时间(h)",wcf_head);
	        Label header5_2=new Label(7,2,"运行区间",wcf_head);
	        Label header5_3=new Label(8,2,"运行时率(%)",wcf_head);
	       
	       Label header6=new Label(9,1,"产量",wcf_head);
	       Label header6_1=new Label(9,2,"产液量(t/d)",wcf_head);
	       Label header6_2=new Label(10,2,"产油量(t/d)",wcf_head);
	       Label header6_3=new Label(11,2,"产水量(t/d)",wcf_head);
	       Label header6_4=new Label(12,2,"含水率(%)",wcf_head);
	       Label header6_5=new Label(13,2,"转速(r/min)",wcf_head);
	       Label header6_6=new Label(14,2,"泵挂(m)",wcf_head);
	       Label header6_7=new Label(15,2,"动液面(m)",wcf_head);
	       Label header6_8=new Label(16,2,"沉没度(m)",wcf_head);
	       
	       Label header7=new Label(17,1,"效率",wcf_head);
	       Label header7_1=new Label(17,2,"系统效率(%)",wcf_head);
	       Label header7_2=new Label(18,2,"吨液百米耗电量(kW·h/100·t)",wcf_head);
	       
	       Label header8=new Label(19,1,"日用电量(kW·h)",wcf_head);
	       
	       Label header9=new Label(20,1,"备注",wcf_head);
	       
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
           sheetOne.addCell(header6_6);
           sheetOne.addCell(header6_7);
           sheetOne.addCell(header6_8);
           sheetOne.addCell(header7);
           sheetOne.addCell(header7_1);
           sheetOne.addCell(header7_2);
           sheetOne.addCell(header8);
           sheetOne.addCell(header9);
           
         //合： 第1列第1行  到 第13列第1行     
           sheetOne.mergeCells(0, 0, 20, 0);   
           
           sheetOne.mergeCells(0, 1, 0, 2);     
           sheetOne.mergeCells(1, 1, 1, 2);
           sheetOne.mergeCells(2, 1, 2, 2);
           sheetOne.mergeCells(3, 1, 5, 1);
           sheetOne.mergeCells(6, 1, 8, 1);
           sheetOne.mergeCells(9, 1, 16, 1);
           sheetOne.mergeCells(17, 1, 18, 1);
           sheetOne.mergeCells(19, 1, 19, 2);
           sheetOne.mergeCells(20, 1, 20, 2);
           /*动态数据   */ 
           float sumCommTime=0;
           float sumRunTime=0;
           float sumLiquidWeightProduction=0;
           float sumOilWeightProduction=0;
           float sumWaterWeightProduction=0;
           
           int commTimeRecords=0;
           int runTimeRecords=0;
           int liquidWeightProductionRecords=0;
           int oilWeightProductionRecords=0;
           int waterWeightProductionRecords=0;
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
        	   if(i<jsonArray.size()){
        		   JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
        		   sumCommTime+=StringManagerUtils.stringToFloat(everydata.getString("commTime"));
        		   sumRunTime+=StringManagerUtils.stringToFloat(everydata.getString("runTime"));
        		   sumLiquidWeightProduction+=StringManagerUtils.stringToFloat(everydata.getString("liquidWeightProduction"));
        		   sumOilWeightProduction+=StringManagerUtils.stringToFloat(everydata.getString("oilWeightProduction"));
        		   sumWaterWeightProduction+=StringManagerUtils.stringToFloat(everydata.getString("waterWeightProduction"));
            	   
            	   if(StringManagerUtils.stringToFloat(everydata.getString("commTime"))>0){
            		   commTimeRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("runTime"))>0){
            		   runTimeRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("liquidWeightProduction"))>0){
            		   liquidWeightProductionRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("oilWeightProduction"))>0){
            		   oilWeightProductionRecords+=1;
            	   }
            	   if(StringManagerUtils.stringToFloat(everydata.getString("waterWeightProduction"))>0){
            		   waterWeightProductionRecords+=1;
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
            	   
            	   content10=new Label(9,i+3,everydata.getString("liquidWeightProduction"),wcf_table);
            	   content11=new Label(10,i+3,everydata.getString("oilWeightProduction"),wcf_table);
            	   content12=new Label(11,i+3,everydata.getString("waterWeightProduction"),wcf_table);
            	   content13=new Label(12,i+3,everydata.getString("waterCut"),wcf_table);
            	   content14=new Label(13,i+3,everydata.getString("rpm"),wcf_table);
            	   content15=new Label(14,i+3,everydata.getString("pumpSettingDepth"),wcf_table);
            	   content16=new Label(15,i+3,everydata.getString("producingFluidLevel"),wcf_table);
            	   content17=new Label(16,i+3,everydata.getString("submergence"),wcf_table);
            	   
            	   content18=new Label(17,i+3,everydata.getString("systemEfficiency"),wcf_table);
            	   content19=new Label(18,i+3,everydata.getString("powerConsumptionPerthm"),wcf_table);
            	   
            	   content20=new Label(19,i+3,everydata.getString("todayWattEnergy"),wcf_table);
            	   
            	   content21=new Label(20,i+3,everydata.getString("remark"),wcf_table);
        	   }else if(i==jsonArray.size()){
        		   sumCommTime=StringManagerUtils.stringToFloat(sumCommTime+"",2);
        		   sumRunTime=StringManagerUtils.stringToFloat(sumRunTime+"",2);
        		   sumLiquidWeightProduction=StringManagerUtils.stringToFloat(sumLiquidWeightProduction+"",2);
        		   sumOilWeightProduction=StringManagerUtils.stringToFloat(sumOilWeightProduction+"",2);
        		   sumWaterWeightProduction=StringManagerUtils.stringToFloat(sumWaterWeightProduction+"",2);
            	   content1=new Label(0,i+3,"合计",wcf_table);
            	   content2=new Label(1,i+3,"",wcf_table);
            	   content3=new Label(2,i+3,"",wcf_table);
            	   
            	   content4=new Label(3,i+3,sumCommTime+"",wcf_table);
            	   content5=new Label(4,i+3,"",wcf_table);
            	   content6=new Label(5,i+3,"",wcf_table);
            	   
            	   content7=new Label(6,i+3,sumRunTime+"",wcf_table);
            	   content8=new Label(7,i+3,"",wcf_table);
            	   content9=new Label(8,i+3,"",wcf_table);
            	   
            	   content10=new Label(9,i+3,sumLiquidWeightProduction+"",wcf_table);
            	   content11=new Label(10,i+3,sumOilWeightProduction+"",wcf_table);
            	   content12=new Label(11,i+3,sumWaterWeightProduction+"",wcf_table);
            	   content13=new Label(12,i+3,"",wcf_table);
            	   content14=new Label(13,i+3,"",wcf_table);
            	   content15=new Label(14,i+3,"",wcf_table);
            	   content16=new Label(15,i+3,"",wcf_table);
            	   content17=new Label(16,i+3,"",wcf_table);
            	   
            	   content18=new Label(17,i+3,"",wcf_table);
            	   content19=new Label(18,i+3,"",wcf_table);
            	   
            	   content20=new Label(19,i+3,"",wcf_table);
            	   content21=new Label(20,i+3,"",wcf_table);
        	   }else{
        		   float averageCommTime=0;
                   float averageRunTime=0;
                   float averageLiquidWeightProduction=0;
                   float averageOilWeightProduction=0;
                   float averageWaterWeightProduction=0;
        		   if(commTimeRecords>0){
        			   averageCommTime=StringManagerUtils.stringToFloat(sumCommTime/commTimeRecords+"",2);
        		   }
        		   if(runTimeRecords>0){
        			   averageRunTime=StringManagerUtils.stringToFloat(sumRunTime/runTimeRecords+"",2);
        		   }
        		   if(liquidWeightProductionRecords>0){
        			   averageLiquidWeightProduction=StringManagerUtils.stringToFloat(sumLiquidWeightProduction/liquidWeightProductionRecords+"",2);
        		   }
        		   if(oilWeightProductionRecords>0){
        			   averageOilWeightProduction=StringManagerUtils.stringToFloat(sumOilWeightProduction/oilWeightProductionRecords+"",2);
        		   }
        		   if(waterWeightProductionRecords>0){
        			   averageWaterWeightProduction=StringManagerUtils.stringToFloat(sumWaterWeightProduction/waterWeightProductionRecords+"",2);
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
            	   
            	   content10=new Label(9,i+3,averageLiquidWeightProduction+"",wcf_table);
            	   content11=new Label(10,i+3,averageOilWeightProduction+"",wcf_table);
            	   content12=new Label(11,i+3,averageWaterWeightProduction+"",wcf_table);
            	   content13=new Label(12,i+3,"",wcf_table);
            	   content14=new Label(13,i+3,"",wcf_table);
            	   content15=new Label(14,i+3,"",wcf_table);
            	   content16=new Label(15,i+3,"",wcf_table);
            	   content17=new Label(16,i+3,"",wcf_table);
            	   
            	   content18=new Label(17,i+3,"",wcf_table);
            	   content19=new Label(18,i+3,"",wcf_table);
            	   
            	   content20=new Label(19,i+3,"",wcf_table);
            	   
            	   content21=new Label(20,i+3,"",wcf_table);
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
		// String ReportPumpingUnit = "采出---抽油机---日报表";
		this.commonDataService.exportDataExcel(response, fileName, "采出井日报数据", null, heads, fields, orgId, "pumpUnitDayReport", v);
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

}
