package com.gao.controller.kafkaConfig;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.User;
import com.gao.service.base.CommonDataService;
import com.gao.service.kafkaConfig.KafkaConfigService;
import com.gao.utils.Constants;
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
import oracle.sql.CLOB;

@Controller
@RequestMapping("/kafkaConfigController")
@Scope("prototype")
public class KafkaConfigController extends BaseController {
	private static Log log = LogFactory.getLog(KafkaConfigController.class);
	private static final long serialVersionUID = 1L;
	@Autowired
	private CommonDataService commonDataService;
	@Autowired
	private KafkaConfigService<?> kafkaConfigService;
	
	@RequestMapping("/loadDeviceComboxList")
	public String loadDeviceComboxList() throws Exception {
		this.pager=new Page("pageForm",request);
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		User user = null;
		String json = this.kafkaConfigService.loadDeviceComboxList(pager,deviceName);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getA9RowDataList")
	public String getA9RowDataList() throws Exception {
		String json = "";
		this.pager = new Page("pagerForm", request);
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		
		if(StringManagerUtils.isNotNull(deviceId)&&!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd') from tbl_a9rawdata_hist t where t.deviceId='"+deviceId+"'";
			List list = this.commonDataService.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-10);
		}
		
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		
		json = kafkaConfigService.getA9RowDataList(pager,deviceId,startDate,endDate);
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
	
	@RequestMapping("/getA9RawCurveChartsData")
	public String getA9RawCurveChartsData()throws Exception{
		String id = ParamUtils.getParameter(request, "id");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String selectedDeviceId = ParamUtils.getParameter(request, "selectedDeviceId");
		String json  = this.kafkaConfigService.getA9RawCurveChartsData(id,deviceId,selectedDeviceId);
		
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportA9RawDataExcel")
	public String exportA9RawDataExcel()throws Exception{
		String id = ParamUtils.getParameter(request, "id");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String selectedDeviceId = ParamUtils.getParameter(request, "selectedDeviceId");
		
		String tableName="tbl_a9rawdata_latest";
        if(StringManagerUtils.isNotNull(selectedDeviceId)){
        	tableName="tbl_a9rawdata_hist";
        }else{
        	tableName="tbl_a9rawdata_latest";
        }
        String sql="select t.deviceid, to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.signal,t.deviceVer,t.interval,t.a,t.f,t.watt,t.i from "+tableName+" t where 1=1 ";
        if(StringManagerUtils.isNotNull(selectedDeviceId)){
        	sql+=" and t.id="+id;
        }else{
        	sql+=" and t.deviceId='"+deviceId+"'";
        }
        List<?> list=kafkaConfigService.findCallSql(sql);
        try {
        	response.setContentType("application/x-msdownload;charset=gbk");      
	        String fileName = deviceId+".xls";
	        if(list.size()>0){
	        	Object[] obj=(Object[])list.get(0);
	        	fileName = deviceId+"-"+obj[1]+".xls";
	        }
	        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
	        OutputStream os = response.getOutputStream();    
	      //打开文件    
	        WritableWorkbook book= Workbook.createWorkbook(os);     
	        WritableSheet sheetOne=book.createSheet("原始数据",0);
	        
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
	        
	        sheetOne.setColumnView(0, 30); // 设置列的宽度    
	        sheetOne.setColumnView(1, 30); // 设置列的宽度    
	        sheetOne.setColumnView(2, 15); // 设置列的宽度    
	        sheetOne.setColumnView(3, 15); // 设置列的宽度    
	        
	        sheetOne.setColumnView(4, 15); // 设置列的宽度    
	        sheetOne.setColumnView(5, 30); // 设置列的宽度    
	        sheetOne.setColumnView(6, 30); // 设置列的宽度    
	        sheetOne.setColumnView(7, 20); // 设置列的宽度    
	        sheetOne.setColumnView(8, 15); // 设置列的宽度    
	        
	        Label header1=new Label(0,0,"设备ID",wcf_head);
	        Label header2=new Label(1,0,"采集时间",wcf_head);
	        Label header3=new Label(2,0,"信号强度",wcf_head);
	        Label header4=new Label(3,0,"设备版本",wcf_head);
	        
	        Label header5=new Label(4,0,"采集间隔(ms)",wcf_head);
	        Label header6=new Label(5,0,"角度电流值(mA)",wcf_head);
	        Label header7=new Label(6,0,"载荷电流值(mA)",wcf_head);
	        Label header8=new Label(7,0,"有功功率(kW)",wcf_head);
	        Label header9=new Label(8,0,"电流(A)",wcf_head);
	        
	        sheetOne.addCell(header1);
	        sheetOne.addCell(header2);
	        sheetOne.addCell(header3);
	        sheetOne.addCell(header4);
	        sheetOne.addCell(header5);
	        sheetOne.addCell(header6);
	        sheetOne.addCell(header7);
	        sheetOne.addCell(header8);
	        sheetOne.addCell(header9);
	        if(list.size()>0){
	        	Object[] obj=(Object[])list.get(0);
	        	SerializableClobProxy   proxy=null;
				CLOB realClob=null;
	        	String intervalCurveData="";
	    		String aCurveData="";
	    		String fCurveData="";
	    		String wattCurveData="";
	    		String iCurveData="";
	    		if(obj[4]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[4]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					intervalCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				
				if(obj[5]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[5]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					aCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				
				if(obj[6]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[6]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					fCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				
				if(obj[7]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[7]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					wattCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				
				if(obj[8]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[8]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					iCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				Label content1=new Label(0,1,obj[0]+"",wcf_table);
				Label content2=new Label(1,1,obj[1]+"",wcf_table);
				Label content3=new Label(2,1,obj[2]+"",wcf_table);
				Label content4=new Label(3,1,obj[3]+"",wcf_table);
				sheetOne.addCell(content1);
				sheetOne.addCell(content2);
				sheetOne.addCell(content3);
				sheetOne.addCell(content4);
				
				String[] intervalData=intervalCurveData.split(",");
				String[] aData=aCurveData.split(",");
				String[] fData=fCurveData.split(",");
				String[] wattData=wattCurveData.split(",");
				String[] iData=iCurveData.split(",");
				
				for(int i=0;i<intervalData.length;i++){
					Label content=new Label(4,i+1,intervalData[i]+"",wcf_table);
					sheetOne.addCell(content);
				}
				for(int i=0;i<aData.length;i++){
					Label content=new Label(5,i+1,aData[i]+"",wcf_table);
					sheetOne.addCell(content);
				}
				for(int i=0;i<fData.length;i++){
					Label content=new Label(6,i+1,fData[i]+"",wcf_table);
					sheetOne.addCell(content);
				}
				for(int i=0;i<wattData.length;i++){
					Label content=new Label(7,i+1,wattData[i]+"",wcf_table);
					sheetOne.addCell(content);
				}
				for(int i=0;i<iData.length;i++){
					Label content=new Label(8,i+1,iData[i]+"",wcf_table);
					sheetOne.addCell(content);
				}
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
}
