package com.gao.controller.pstofs;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gao.controller.base.BaseController;
import com.gao.model.User;
import com.gao.model.calculate.ElectricCalculateResponseData;
import com.gao.model.calculate.InversioneFSdiagramResponseData;
import com.gao.model.calculate.TimeEffResponseData;
import com.gao.model.calculate.WellAcquisitionData;
import com.gao.model.gridmodel.InverOptimizeHandsontableChangedData;
import com.gao.model.gridmodel.WellHandsontableChangedData;
import com.gao.service.base.CommonDataService;
import com.gao.service.pstofs.PSToFSService;
import com.gao.tast.EquipmentDriverServerTast;
import com.gao.tast.MQTTServerTast;
import com.gao.tast.MQTTServerTast.TransferDaily;
import com.gao.tast.MQTTServerTast.TransferDiagram;
import com.gao.tast.MQTTServerTast.TransferDiscrete;
import com.gao.utils.Config;
import com.gao.utils.Constants;
import com.gao.utils.Page;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;

/**<p>描述：电功图反演地面功图Controller</p>
 * 
 * @author zhao 2018-09-07
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/PSToFSController")
@Scope("prototype")
public class PSToFSController extends BaseController {
	private static Log log = LogFactory.getLog(PSToFSController.class);
	private static final long serialVersionUID = -281275682819237996L;
	private List<File> SurfaceCardFile;
	private List<String> SurfaceCardFileFileName;// 上传文件的名字 ,FileName 固定的写法  
	private List<String> SurfaceCardFileContentType ; //上传文件的类型， ContentType 固定的写法
	
	@Autowired
	private PSToFSService<?> PSToFSService;
	@Autowired
	private CommonDataService commonDataService;
	private String orgId;
	private String wellName;
	
	@RequestMapping("/getPSToFSPumpingUnitData")
	public String getPSToFSPumpingUnitData() throws Exception {
		orgId=ParamUtils.getParameter(request, "orgId");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = PSToFSService.getPSToFSPumpingUnitData(orgId);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getPSToFSMotorData")
	public String getPSToFSMotorData() throws Exception {
		orgId=ParamUtils.getParameter(request, "orgId");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = PSToFSService.getPSToFSMotorData(orgId);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getInverOptimizeData")
	public String getInverOptimizeData() throws Exception {
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		String wellInformationName = ParamUtils.getParameter(request, "wellInformationName");
		orgId=ParamUtils.getParameter(request, "orgId");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = PSToFSService.getInverOptimizeData(orgId,wellInformationName,recordCount);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping("/saveInverOptimizeHandsontableData")
	public String saveInverOptimizeHandsontableData() throws Exception {

		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String orgid=user.getUserorgids();
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "");
		System.out.println(data);
		String orgCode = ParamUtils.getParameter(request, "orgCode");
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<InverOptimizeHandsontableChangedData>() {}.getType();
		InverOptimizeHandsontableChangedData inverOptimizeHandsontableChangedData=gson.fromJson(data, type);
		this.PSToFSService.saveInverOptimizeHandsontableData(inverOptimizeHandsontableChangedData, orgid,orgCode);
		String json ="{success:true}";
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		
		return null;
	}
	
	@RequestMapping("/getInversionCalaulateResult")
	public String getInversionCalaulateResult() throws Exception {
		String wellName = ParamUtils.getParameter(request, "wellName");
		String cjsj = ParamUtils.getParameter(request, "cjsj");
		String json = PSToFSService.getInversionCalaulateResult(wellName,cjsj);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getCalaulateResult")
	public String getCalaulateResult() throws Exception {
		String wellName = ParamUtils.getParameter(request, "wellName");
		String cjsj = ParamUtils.getParameter(request, "cjsj");
		String data = ParamUtils.getParameter(request, "data");
		String requestData = PSToFSService.getCalaulateResult(wellName,cjsj,data);
		String responseData=StringManagerUtils.sendPostMethod(Config.getElectricToFSDiagramHttpServerURL(), requestData,"utf-8");
		if(!StringManagerUtils.isNotNull(responseData)){
			responseData="{\"WellName\": \""+wellName+"\",";
			responseData+="\"ResultStatus\": "+-9999+"}";
		}
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(responseData);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveCalaulateResult")
	public String saveCalaulateResult() throws Exception {
		HttpSession session=request.getSession();
		String wellName = ParamUtils.getParameter(request, "wellName");
		String cjsj = ParamUtils.getParameter(request, "cjsj");
		String ElectricData = ParamUtils.getParameter(request, "ElectricData");
		String StartPoint = ParamUtils.getParameter(request, "StartPoint");
		String EndPoint = ParamUtils.getParameter(request, "EndPoint");
		String FSDiagramId = ParamUtils.getParameter(request, "FSDiagramId");
		String Stroke = ParamUtils.getParameter(request, "Stroke");
		String SPM = ParamUtils.getParameter(request, "SPM");
		String CNT = ParamUtils.getParameter(request, "CNT");
		String FSDiagram = ParamUtils.getParameter(request, "FSDiagram");
		this.PSToFSService.saveCalaulateResult(wellName,cjsj,ElectricData,StartPoint,EndPoint,FSDiagramId,Stroke,SPM,CNT,FSDiagram);
		String json ="{success:true}";
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
//		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/savePumpingUnitData")
	public String savePumpingUnitData() throws Exception {

		HttpSession session=request.getSession();
		String wellName = ParamUtils.getParameter(request, "wellName");
		String PumpingUnitData = ParamUtils.getParameter(request, "PumpingUnitData");
		String PumpingUnitPTRData = ParamUtils.getParameter(request, "PumpingUnitPTRData");
		this.PSToFSService.savePumpingUnitData(PumpingUnitData,PumpingUnitPTRData,wellName);
		String json ="{success:true}";
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
//		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	
	@RequestMapping("/saveMotorData")
	public String saveMotorData() throws Exception {
		HttpSession session=request.getSession();
		String wellName = ParamUtils.getParameter(request, "wellName");
		String MotorData = ParamUtils.getParameter(request, "MotorData");
		String MotorPerformanceCurverData = ParamUtils.getParameter(request, "MotorPerformanceCurverData");
		this.PSToFSService.saveMotorData(MotorData,MotorPerformanceCurverData,wellName);
		String json ="{success:true}";
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
//		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	
	@RequestMapping("/getPSToFSElectricReaultData")
	public String getPSToFSElectricReaultData(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String json="";
		String cjsj="";
		String wellName="";
		int pointCount=0;
		List<Float> currentAList=new ArrayList<Float>();
		List<Float> currentBList=new ArrayList<Float>();
		List<Float> currentCList=new ArrayList<Float>();
		List<Float> voltageAList=new ArrayList<Float>();
		List<Float> voltageBList=new ArrayList<Float>();
		List<Float> voltageCList=new ArrayList<Float>();
		List<Float> activePowerAList=new ArrayList<Float>();
		List<Float> activePowerBList=new ArrayList<Float>();
		List<Float> activePowerCList=new ArrayList<Float>();
		for(int i=0;i<files.length;i++){
			if(!files[i].isEmpty()){
				try{
					if(files[i].getFileItem().getName().lastIndexOf("CSV")>0||files[i].getFileItem().getName().lastIndexOf("csv")>0){
						System.out.println("CSV文件:"+files[i].getFileItem().getName());
					}else if(files[i].getFileItem().getName().lastIndexOf("XLSX")>0||files[i].getFileItem().getName().lastIndexOf("xlsx")>0){
						System.out.println("xlsx文件:"+files[i].getFileItem().getName());
					}else if(files[i].getFileItem().getName().lastIndexOf("XLS")>0||files[i].getFileItem().getName().lastIndexOf("xls")>0){
						System.out.println("xls文件:"+files[i].getFileItem().getName());
						Workbook rwb=Workbook.getWorkbook(files[i].getInputStream());
						rwb.getNumberOfSheets();
						Sheet oFirstSheet = rwb.getSheet(0);// 使用索引形式获取第一个工作表，也可以使用rwb.getSheet(sheetName);其中sheetName表示的是工作表的名称  
				        int rows = oFirstSheet.getRows();//获取工作表中的总行数  
				        int columns = oFirstSheet.getColumns();//获取工作表中的总列数  
				        
				        for (int j = 1; j < 10; j++) {
				        	if(j==1){
				        		cjsj = oFirstSheet.getCell(1,j).getContents();
				        		wellName= oFirstSheet.getCell(0,j).getContents();
				        	}
				        	String cjsjtemp=oFirstSheet.getCell(1,j).getContents();
				        	if(cjsjtemp.equals(cjsj)){//只解析第一组数据
				        		String bsid=oFirstSheet.getCell(2,j).getContents();
					        	if("1".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		if(dataList.size()>pointCount){
					        			pointCount=dataList.size();
					        		}
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*0.02);
						        		currentAList.add(dataReault);
					        		}
					        	}else if("2".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		if(dataList.size()>pointCount){
					        			pointCount=dataList.size();
					        		}
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*0.1);
						        		voltageAList.add(dataReault);
					        		}
					        	}else if("3".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		if(dataList.size()>pointCount){
					        			pointCount=dataList.size();
					        		}
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*5*0.001);
						        		activePowerAList.add(dataReault);
					        		}
					        	}else if("4".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		if(dataList.size()>pointCount){
					        			pointCount=dataList.size();
					        		}
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*0.02);
						        		currentBList.add(dataReault);
					        		}
					        	}else if("5".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		if(dataList.size()>pointCount){
					        			pointCount=dataList.size();
					        		}
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*0.1);
						        		voltageBList.add(dataReault);
					        		}
					        	}else if("6".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*5*0.001);
						        		activePowerBList.add(dataReault);
					        		}
					        	}else if("7".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		if(dataList.size()>pointCount){
					        			pointCount=dataList.size();
					        		}
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*0.02);
						        		currentCList.add(dataReault);
					        		}
					        	}else if("8".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		if(dataList.size()>pointCount){
					        			pointCount=dataList.size();
					        		}
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*0.1);
						        		voltageCList.add(dataReault);
					        		}
					        	}else if("9".equals(bsid)){
					        		String dataStr=oFirstSheet.getCell(3,j).getContents();
					        		List<String> dataList=StringManagerUtils.SubStringToList(dataStr, 4);
					        		for(int k=0;k<dataList.size();k++){
						        		int data=Integer.valueOf(dataList.get(k).trim(),16).shortValue();
						        		float dataReault=(float) (data*5*0.001);
						        		activePowerCList.add(dataReault);
					        		}
					        	}
				        	}
				        }
				        rwb.close();
					}
				}catch(Exception e){
					e.printStackTrace();
					result_json = new StringBuffer();
					result_json.append("{\"success\":false}");
					continue;
				}
			}
		}
		if(result_json.length()==0){
			result_json.append("{\"success\":true,\"cjsj\":\""+cjsj+"\",\"WellName\":\""+wellName+"\",");
			result_json.append("\"totalRoot\":[");
			for(int i=0;i<pointCount;i++){
				float activePowerSum=0;
				String data="{";
				data+="\"id\":"+(i+1)+",";
//				if(currentAList.size()>i){
//					data+="\"currentA\":"+currentAList.get(i)+",";
//				}
//				if(currentBList.size()>i){
//					data+="\"currentB\":"+currentBList.get(i)+",";
//				}
//				if(currentCList.size()>i){
//					data+="\"currentC\":"+currentCList.get(i)+",";
//				}
//				if(voltageAList.size()>i){
//					data+="\"voltageA\":"+voltageAList.get(i)+",";
//				}
//				if(voltageBList.size()>i){
//					data+="\"voltageB\":"+voltageBList.get(i)+",";
//				}
//				if(voltageCList.size()>i){
//					data+="\"voltageC\":"+voltageCList.get(i)+",";
//				}
				if(activePowerAList.size()>i){
					data+="\"activePowerA\":"+activePowerAList.get(i)+",";
					activePowerSum+=activePowerAList.get(i);
				}
				if(activePowerBList.size()>i){
					data+="\"activePowerB\":"+activePowerBList.get(i)+",";
					activePowerSum+=activePowerBList.get(i);
				}
				if(activePowerCList.size()>i){
					data+="\"activePowerC\":"+activePowerCList.get(i)+"},";
					activePowerSum+=activePowerCList.get(i);
				}
//				data+="\"activePowerSum\":"+StringManagerUtils.stringToFloat(activePowerSum+"", 3)+"},";
				result_json.append(data);
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]}");
		}
		json=result_json.toString();
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
//		log.warn("doAlarmsSetShow json*********=" + json);
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getWellNameList")
	public String getWellNameList() throws Exception {
		this.pager=new Page("pageForm",request);
		String json = this.PSToFSService.getWellNameList(pager);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getAcquisitionTimeList")
	public String getAcquisitionTimeList() throws Exception {
		this.pager=new Page("pageForm",request);
		String wellName = ParamUtils.getParameter(request, "wellName");
		String json = this.PSToFSService.getAcquisitionTimeList(pager,wellName);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getWellList")
	public String getWellList() throws Exception {
		orgId=ParamUtils.getParameter(request, "orgId");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String wellName=ParamUtils.getParameter(request, "wellName");
		
		String json = this.PSToFSService.getWellList(orgId,wellName);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDiagramDataList")
	public String getDiagramDataList() throws Exception {
		orgId=ParamUtils.getParameter(request, "orgId");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String wellName=ParamUtils.getParameter(request, "wellName");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		this.pager = new Page("pagerForm", request);
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.acquisitionTime),'yyyy-mm-dd') from t_indicatordiagram t,t_wellinformation t2 where t.wellId=t2.id and  t2.wellName='"+wellName+"'  ";
			List list = this.commonDataService.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-1);
		}
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		
		String json = PSToFSService.getDiagramDataList(orgId,wellName,startDate,endDate, pager);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getSingleInverDiagramData")
	public String getSingleInverDiagramData()throws Exception{
		String id = ParamUtils.getParameter(request, "id");
		String wellName = ParamUtils.getParameter(request, "wellName");
		String json = this.PSToFSService.getSingleInverDiagramData(id,wellName);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getSingleElecCurveData")
	public String getSingleElecCurveData()throws Exception{
		String id = ParamUtils.getParameter(request, "id");
		String wellName = ParamUtils.getParameter(request, "wellName");
		String json = this.PSToFSService.getSingleElecCurveData(id,wellName);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getSingleElecInverDiagramCheckData")
	public String getSingleElecInverDiagramCheckData()throws Exception{
		String recordId = ParamUtils.getParameter(request, "recordId");
		String wellName = ParamUtils.getParameter(request, "wellName");
		String json = this.PSToFSService.getSingleElecInverDiagramCheckData(recordId,wellName);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportSingleElecInverDiagramCheckData")
	public String exportSingleElecInverDiagramCheckData()throws Exception{
		String recordId = ParamUtils.getParameter(request, "recordId");
		String wellName = ParamUtils.getParameter(request, "wellName");
		this.PSToFSService.exportSingleElecInverDiagramCheckData(recordId,wellName,response);
		return null;
	}
	
	@RequestMapping("/getInverDiagramChartData")
	public String getInverDiagramChartData()throws Exception{
		orgId = ParamUtils.getParameter(request, "orgId");
		String wellName = ParamUtils.getParameter(request, "wellName");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String diagramType=ParamUtils.getParameter(request, "diagramType");
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		this.pager = new Page("pagerForm", request);
		
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		if(StringManagerUtils.isNotNull(wellName)&&(!StringManagerUtils.isNotNull(endDate))){
			String sql = " select to_char(max(t.acquisitionTime),'yyyy-mm-dd') from t_indicatordiagram t,t_wellinformation t2 where t.wellId=t2.id and  t2.wellName='"+wellName+"'  ";
			List list = this.commonDataService.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-1);
		}
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		
		
		String json = "";
		json = this.PSToFSService.getInverDiagramChartData(pager, orgId, wellName, startDate, endDate,diagramType);
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	//离散数据
	@RequestMapping("/saveMQTTTransferElecDiscreteData")
	public String saveMQTTTransferElecDiscreteData() throws Exception {
		
		Gson gson=new Gson();
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8");
		
		ElectricCalculateResponseData electricCalculateResponseData=null;
		TimeEffResponseData timeEffResponseData=null;
		java.lang.reflect.Type type = new TypeToken<TransferDiscrete>() {}.getType();
		TransferDiscrete transferDiscrete=gson.fromJson(data, type);
		
		if(transferDiscrete!=null){
//			transferDiscrete.setAcquisitionTime(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
			String wellName="";
			String sql="select t.wellName from t_wellinformation t where REGEXP_LIKE(t.driveraddr, '("+transferDiscrete.getID()+")', 'i')";//不区分ID大小写
			List list = this.commonDataService.reportDateJssj(sql);
			
			if(list.size()>0){
				wellName=list.get(0).toString();
			}
			if(StringManagerUtils.isNotNull(wellName)){
				transferDiscrete.setWellName(wellName);
				this.PSToFSService.saveMQTTTransferElecDiscreteData(transferDiscrete);
			}
		}
		String json = "{success:true,flag:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	//曲线数据
	@RequestMapping("/saveMQTTTransferElecDiagramData")
	public String saveMQTTTransferElecDiagramData() throws Exception {
		
		Gson gson=new Gson();
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8");
		
		java.lang.reflect.Type type = new TypeToken<TransferDiagram>() {}.getType();
    	TransferDiagram transferDiagram=gson.fromJson(data, type);
    	
    	String inversionUrl=Config.getElectricToFSDiagramHttpServerURL();
    	
    	if(transferDiagram!=null){
//    		transferDiagram.setAcquisitionTime(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    		String wellName="";
    		String wellSql="select t.wellName from t_wellinformation t where REGEXP_LIKE(t.driveraddr, '("+transferDiagram.getID()+")', 'i')";//不区分ID大小写
    		List list = this.commonDataService.reportDateJssj(wellSql);
    		
    		if(list.size()>0){
    			wellName=list.get(0).toString();
    		}
        	if(StringManagerUtils.isNotNull(wellName)){
        		transferDiagram.setWellName(wellName);
        		
        		transferDiagram.setI_Filter(new ArrayList<Float>());
    			transferDiagram.setWatt_Filter(new ArrayList<Float>());
    			transferDiagram.setRPM_Filter(new ArrayList<Float>());
    			
    			if(transferDiagram.getRPM()==null||transferDiagram.getRPM().size()==0){
    				transferDiagram.setRPM(new ArrayList<Float>());
    				if(transferDiagram.getInterval()!=null&&transferDiagram.getInterval().size()>0){
    					for(int i=0;i<transferDiagram.getInterval().size();i++){
        					float rpm=StringManagerUtils.stringToFloat(((float)1*60*1000*1000*1000)/transferDiagram.getInterval().get(i)+"", 2);
        					transferDiagram.getRPM().add(rpm);
        				}
    				}
    			}
        		
        		if("1".equals(Config.getInversionSwitch())||(!(transferDiagram.getS()!=null&&transferDiagram.getS().size()>0&&transferDiagram.getF()!=null&&transferDiagram.getF().size()>0))){//下位机未进行反演计算 由下位机计算
        			transferDiagram.setS(new ArrayList<Float>());
        			transferDiagram.setF(new ArrayList<Float>());
        			
        			String sql="select t2.manufacturer,t2.model,t2.stroke,decode(t2.crankrotationdirection,'顺时针','Clockwise','Anticlockwise'),"
        					+ " t2.offsetangleofcrank,t2.crankgravityradius,t2.singlecrankweight,"
        					+ " t2.structuralunbalance,t2.gearreducerratio,t2.gearreducerbeltpulleydiameter,"
        					+ " t2.balanceposition,t2.balanceweight,t2.prtf,"
        					+ " t4.offsetangleofcrankps,t4.surfacesystemefficiency,"
        					+ " t4.fs_leftpercent,t4.fs_rightpercent,"
        					+ " t4.wattangle,t4.filtertime_watt,t4.filtertime_i,t4.filtertime_rpm,"
        					+ " t4.filtertime_fsdiagram,t4.filtertime_fsdiagram_l,t4.filtertime_fsdiagram_r"
        					+ " from  t_wellinformation t,t_inver_pumpingunit t2,t_inver_optimize t4"
        					+ " where t.id=t2.wellid and t.id=t4.wellid"
        					+ " and t.wellname='"+transferDiagram.getWellName()+"'";
        			
        			List<?> inverDataList = commonDataService.findCallSql(sql);
        			if(inverDataList.size()>0){
        				StringBuffer result_json = new StringBuffer();
        				Object[] obj=(Object[]) inverDataList.get(0);
        				result_json.append("{\"AKString\":\"\",");
            			result_json.append("\"WellName\":\""+transferDiagram.getWellName()+"\",");
            			result_json.append("\"AcquisitionTime\":\""+transferDiagram.getAcquisitionTime()+"\",");
            			result_json.append("\"SPM\":"+transferDiagram.getSPM()+",");
            			
            			result_json.append("\"SurfaceSystemEfficiency\":"+obj[14]+",");
        				result_json.append("\"LeftPercent\":"+obj[15]+",");
        				result_json.append("\"RightPercent\":"+obj[16]+",");
        				result_json.append("\"WattAngle\":"+obj[17]+",");
        				result_json.append("\"WattTimes\":"+obj[18]+",");
        				result_json.append("\"ITimes\":"+obj[19]+",");
        				result_json.append("\"RPMTimes\":"+obj[20]+",");
        				result_json.append("\"FSDiagramTimes\":"+obj[21]+",");
        				result_json.append("\"FSDiagramLeftTimes\":"+obj[22]+",");
        				result_json.append("\"FSDiagramRightTimes\":"+obj[23]+",");
        				
        				//功率曲线
        				String Watt ="[";
            			for(int i=0;i<transferDiagram.getWatt().size();i++){
            				Watt +=transferDiagram.getWatt().get(i);
            				if(i<transferDiagram.getWatt().size()-1){
            					Watt +=",";
            				}
            			}
            			Watt +="]";
            			result_json.append("\"Watt\":"+Watt+",");
            			
            			//电流和转速曲线
            			if(StringManagerUtils.isNotNull(transferDiagram.getVer())&&transferDiagram.getVer().startsWith("2")){//如果2.0版本
            				String I ="[";
                			for(int i=0;i<transferDiagram.getI().size();i++){
                				I +=transferDiagram.getI().get(i);
                				if(i<transferDiagram.getI().size()-1){
                					I +=",";
                				}
                			}
                			I +="]";
                			result_json.append("\"I\":"+I+",");
            				
            				String RPM="[";
            				for(int i=0;i<transferDiagram.getRPM().size();i++){
            					RPM +=transferDiagram.getRPM().get(i);
                				if(i<transferDiagram.getRPM().size()-1){
                					RPM +=",";
                				}
                			}
            				RPM +="]";
            				result_json.append("\"RPM\":"+RPM+",");
            			}
        				
        				//拼接抽油机数据
            			result_json.append("\"PumpingUnit\":{");
            			SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[12]);
        				CLOB realClob = (CLOB) proxy.getWrappedClob(); 
        				String prtf=StringManagerUtils.CLOBtoString(realClob);
        				
        				result_json.append("\"Manufacturer\":\""+obj[0]+"\",");
        				result_json.append("\"Model\":\""+obj[1]+"\",");
        				result_json.append("\"Stroke\":"+obj[2]+",");
        				result_json.append("\"CrankRotationDirection\":\""+obj[3]+"\",");
        				result_json.append("\"OffsetAngleOfCrank\":"+obj[4]+",");
        				result_json.append("\"OffsetAngleOfCrankPS\":"+obj[18]+",");
        				result_json.append("\"CrankGravityRadius\":"+obj[5]+",");
        				result_json.append("\"SingleCrankWeight\":"+obj[6]+",");
        				result_json.append("\"StructuralUnbalance\":"+obj[7]+",");
        				result_json.append("\"GearReducerRatio\":"+obj[8]+",");
        				result_json.append("\"GearReducerBeltPulleyDiameter\":"+obj[9]+",");
        				result_json.append("\"Balance\":{");
        				result_json.append("\"EveryBalance\":[");
        				
        				//拼接抽油机平衡块数据
        				String[] BalancePositionArr=(obj[10]+"").split(",");
        				String[] BalanceWeightArr=(obj[11]+"").split(",");
        				for(int j=0;j<BalancePositionArr.length&&j<BalanceWeightArr.length;j++){
        					result_json.append("{\"Position\":"+BalancePositionArr[j]+",");
        					result_json.append("\"Weight\":"+BalanceWeightArr[j]+"}");
        					if(j<BalancePositionArr.length-1&&j<BalanceWeightArr.length-1){
        						result_json.append(",");
        					}
        				}
        				result_json.append("]},");
        				//拼接抽油机位置扭矩因数曲线数据
        				result_json.append("\"PRTF\":{");
        				String CrankAngle="[";
        				String PR="[";
        				String TF="[";
        				JSONObject prtfJsonObject = JSONObject.fromObject("{\"data\":"+prtf+"}");//解析数据
        				JSONArray prtfJsonArray = prtfJsonObject.getJSONArray("data");
        				for(int j=0;j<prtfJsonArray.size();j++){
        					JSONObject everydata = JSONObject.fromObject(prtfJsonArray.getString(j));
        					CrankAngle+=everydata.getString("CrankAngle");
        					PR+=everydata.getString("PR");
        					TF+=everydata.getString("TF");
        					if(j<prtfJsonArray.size()-1){
        						CrankAngle+=",";
        						PR+=",";
        						TF+=",";
        					}
        				}
        				CrankAngle+="]";
        				PR+="]";
        				TF+="]";
        				result_json.append("\"CrankAngle\":"+CrankAngle+",");
        				result_json.append("\"PR\":"+PR+",");
        				result_json.append("\"TF\":"+TF+"}");
        				result_json.append("}");
        				
            			result_json.append("}");
            			
            			if((!StringManagerUtils.isNotNull(transferDiagram.getVer()))||transferDiagram.getVer().startsWith("1")){//版本判断
            				inversionUrl=Config.getElectricToFSDiagramHttpServerURL();
            			}else{
            				inversionUrl=Config.getElectricToFSDiagramAutoHttpServerURL();
            			}
            			
            			String responseData=StringManagerUtils.sendPostMethod(inversionUrl, result_json.toString(),"utf-8");
            			
            			type = new TypeToken<InversioneFSdiagramResponseData>() {}.getType();
            			InversioneFSdiagramResponseData inversioneFSdiagramResponseData=gson.fromJson(responseData, type);
            			if(inversioneFSdiagramResponseData!=null&&inversioneFSdiagramResponseData.getResultStatus()==1){
            				transferDiagram.setResultStatus(inversioneFSdiagramResponseData.getResultStatus());
            				transferDiagram.setStroke(inversioneFSdiagramResponseData.getStroke());
            				transferDiagram.setMaxF(inversioneFSdiagramResponseData.getMaxF());
            				transferDiagram.setMinF(inversioneFSdiagramResponseData.getMinF());
            				
            				transferDiagram.setUpstrokeIMax_Filter(inversioneFSdiagramResponseData.getUpstrokeIMax());
            				transferDiagram.setDownstrokeIMax_Filter(inversioneFSdiagramResponseData.getDownstrokeIMax());
            				transferDiagram.setUpstrokeWattMax_Filter(inversioneFSdiagramResponseData.getUpstrokeWattMax());
            				transferDiagram.setDownstrokeWattMax_Filter(inversioneFSdiagramResponseData.getDownstrokeWattMax());
            				transferDiagram.setIDegreeBalance_Filter(inversioneFSdiagramResponseData.getIDegreeBalance());
            				transferDiagram.setWattDegreeBalance_Filter(inversioneFSdiagramResponseData.getWattDegreeBalance());
            				transferDiagram.setMotorInputAvgWatt(inversioneFSdiagramResponseData.getMotorInputAvgWatt());
            				for(int i=0;i<inversioneFSdiagramResponseData.getF().size();i++){
            					transferDiagram.getF().add(inversioneFSdiagramResponseData.getF().get(i));
            					transferDiagram.getS().add(inversioneFSdiagramResponseData.getS().get(i));
            					
            					transferDiagram.getWatt_Filter().add(inversioneFSdiagramResponseData.getWatt().get(i));
            					transferDiagram.getI_Filter().add(inversioneFSdiagramResponseData.getI().get(i));
            					transferDiagram.getRPM_Filter().add(inversioneFSdiagramResponseData.getRPM().get(i));
            				}
            			}
        			}
        		}else{
        			transferDiagram.setUpstrokeIMax_Filter(transferDiagram.getUpstrokeIMax());
    				transferDiagram.setDownstrokeIMax_Filter(transferDiagram.getDownstrokeIMax());
    				transferDiagram.setUpstrokeWattMax_Filter(transferDiagram.getUpstrokeWattMax());
    				transferDiagram.setDownstrokeWattMax_Filter(transferDiagram.getDownstrokeWattMax());
    				transferDiagram.setIDegreeBalance_Filter(transferDiagram.getIDegreeBalance());
    				transferDiagram.setWattDegreeBalance_Filter(transferDiagram.getWattDegreeBalance());
    				
        			for(int i=0;i<transferDiagram.getF().size();i++){
    					transferDiagram.getWatt_Filter().add(transferDiagram.getWatt().get(i));
    					transferDiagram.getI_Filter().add(transferDiagram.getI().get(i));
    					if(i<transferDiagram.getRPM().size()){
    						transferDiagram.getRPM_Filter().add(transferDiagram.getRPM().get(i));
    					}
    				}
        		}
        		this.PSToFSService.saveMQTTTransferElecDiagramData(transferDiagram);
        	}
    	}
    	
		String json = "{success:true,flag:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	//日汇总数据
	@RequestMapping("/saveMQTTTransferElecDailyData")
	public String saveMQTTTransferElecDailyData() throws Exception {
		
		Gson gson=new Gson();
		ServletInputStream ss = request.getInputStream();
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8");
		
		java.lang.reflect.Type type = new TypeToken<TransferDaily>() {}.getType();
		TransferDaily transferDaily=gson.fromJson(data, type);
    	
		String wellName="";
		String sql="select t.wellName from t_wellinformation t where REGEXP_LIKE(t.driveraddr, '("+transferDaily.getID()+")', 'i')";//不区分ID大小写
		List list = this.commonDataService.reportDateJssj(sql);
		
		if(list.size()>0){
			wellName=list.get(0).toString();
		}
		if(StringManagerUtils.isNotNull(wellName)){
			transferDaily.setWellName(wellName);
			this.PSToFSService.saveMQTTTransferElecDailyData(transferDaily);
		}
    	
		String json = "{success:true,flag:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/reInverDiagram")
	public String reInverDiagram() throws Exception {
		Gson gson=new Gson();
		String inversionUrl=Config.getElectricToFSDiagramAutoHttpServerURL();
		String diagramIds=ParamUtils.getParameter(request, "diagramIds");
		String wellName=ParamUtils.getParameter(request, "wellName");
		
		String [] diagramIdArr=diagramIds.split(",");
		StringBuffer result_json = new StringBuffer();
		int totalCount=0;
		int successCount=0;
		int defaultCount=0;
    	for(int i=0;i<diagramIdArr.length;i++){
    		totalCount++;
    		String sql="select t.wellname,t2.id as diagramid,to_char(t2.acquisitionTime,'yyyy-mm-dd hh24:mi:ss') as acquisitionTime,"
    				+ " t2.spm,t2.rawpower_curve,t2.rawcurrent_curve,t2.rawrpm_curve, "
    				+ " t3.manufacturer as manufacturer_motor,t3.model as model_motor,t3.beltpulleydiameter,"
    				+ " t4.manufacturer,t4.model,t4.stroke,decode(t4.crankrotationdirection,'顺时针','Clockwise','Anticlockwise'),"
    				+ " t4.offsetangleofcrank,t5.offsetangleofcrankps,t4.crankgravityradius,t4.singlecrankweight,t4.structuralunbalance,"
    				+ " t4.gearreducerratio,t4.gearreducerbeltpulleydiameter, t4.balanceposition,t4.balanceweight,"
    				+ " t5.surfacesystemefficiency,t5.fs_leftpercent,t5.fs_rightpercent,"
    				+ " t5.wattangle,t5.filtertime_watt,t5.filtertime_i,t5.filtertime_rpm,t5.filtertime_fsdiagram,t5.filtertime_fsdiagram_l,t5.filtertime_fsdiagram_r,"
    				+ " t4.prtf "
    				+ " from t_wellinformation t,t_indicatordiagram t2,t_inver_motor t3,t_inver_pumpingunit t4,t_inver_optimize t5 ";
    		if(!StringManagerUtils.isNotNull(wellName)){//如果是实时
    			sql+= " ,t_indicatordiagram_rt t6";
    		}
    		
    		sql+= " where t.id=t2.wellid and t.id=t3.wellid and t.id=t4.wellid and t.id=t5.wellid ";
    				
    		if(StringManagerUtils.isNotNull(wellName)){
    			sql+= " and t2.id="+diagramIdArr[i];
    		}else{
    			sql+=" and t2.wellid=t6.wellid and t2.acquisitionTime=t6.acquisitionTime and t6.id="+diagramIdArr[i];
    		}	
    		List<?> list = commonDataService.findCallSql(sql);
    		result_json = new StringBuffer();
    		
    		if(list.size()>0){
    			Object[] obj=(Object[]) list.get(0);
    			String WattString="";
    			String IString="";
    			String RPMString="";
    			SerializableClobProxy   proxy=null;
    	        CLOB realClob=null;
    			if(obj[4]!=null){
    				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[4]);
    				realClob = (CLOB) proxy.getWrappedClob(); 
    				WattString=StringManagerUtils.CLOBtoString(realClob);
    			}
    			if(obj[5]!=null){
    				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[5]);
    				realClob = (CLOB) proxy.getWrappedClob(); 
    				IString=StringManagerUtils.CLOBtoString(realClob);
    			}
    			if(obj[6]!=null){
    				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[6]);
    				realClob = (CLOB) proxy.getWrappedClob(); 
    				RPMString=StringManagerUtils.CLOBtoString(realClob);
    			}
    			result_json.append("{\"AKString\":\"\",");
    			result_json.append("\"WellName\":\""+obj[0]+"\",");
    			result_json.append("\"AcquisitionTime\":\""+obj[2]+"\",");
    			result_json.append("\"SPM\":"+obj[3]+",");
    			result_json.append("\"Watt\":["+WattString+"],");
    			result_json.append("\"I\":["+IString+"],");
    			result_json.append("\"RPM\":["+RPMString+"],");
    			result_json.append("\"SurfaceSystemEfficiency\":"+obj[23]+",");
    			
    			result_json.append("\"LeftPercent\":"+obj[24]+",");
				result_json.append("\"RightPercent\":"+obj[25]+",");
				result_json.append("\"WattAngle\":"+obj[26]+",");
				result_json.append("\"WattTimes\":"+obj[27]+",");
				result_json.append("\"ITimes\":"+obj[28]+",");
				result_json.append("\"RPMTimes\":"+obj[29]+",");
				result_json.append("\"FSDiagramTimes\":"+obj[30]+",");
				result_json.append("\"FSDiagramLeftTimes\":"+obj[31]+",");
				result_json.append("\"FSDiagramRightTimes\":"+obj[32]+",");
    			
    			//电机数据
    			result_json.append("\"Motor\":{");
    			result_json.append("\"Manufacturer\":\""+obj[7]+"\",");
				result_json.append("\"Model\":\""+obj[8]+"\",");
				result_json.append("\"BeltPulleyDiameter\":"+obj[9]+"");
				result_json.append("},");
    			//抽油机数据
    			result_json.append("\"PumpingUnit\":{");
    			
    			proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[33]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				String prtf=StringManagerUtils.CLOBtoString(realClob);
				
				result_json.append("\"Manufacturer\":\""+obj[10]+"\",");
				result_json.append("\"Model\":\""+obj[11]+"\",");
				result_json.append("\"Stroke\":"+obj[12]+",");
				result_json.append("\"CrankRotationDirection\":\""+obj[13]+"\",");
				result_json.append("\"OffsetAngleOfCrank\":"+obj[14]+",");
				result_json.append("\"OffsetAngleOfCrankPS\":"+obj[15]+",");
				result_json.append("\"CrankGravityRadius\":"+obj[16]+",");
				result_json.append("\"SingleCrankWeight\":"+obj[17]+",");
				result_json.append("\"StructuralUnbalance\":"+obj[18]+",");
				result_json.append("\"GearReducerRatio\":"+obj[19]+",");
				result_json.append("\"GearReducerBeltPulleyDiameter\":"+obj[20]+",");
				result_json.append("\"Balance\":{");
				result_json.append("\"EveryBalance\":[");
				
				//拼接抽油机平衡块数据
				String[] BalancePositionArr=(obj[21]+"").split(",");
				String[] BalanceWeightArr=(obj[22]+"").split(",");
				for(int j=0;j<BalancePositionArr.length&&j<BalanceWeightArr.length;j++){
					result_json.append("{\"Position\":"+BalancePositionArr[j]+",");
					result_json.append("\"Weight\":"+BalanceWeightArr[j]+"}");
					if(j<BalancePositionArr.length-1&&j<BalanceWeightArr.length-1){
						result_json.append(",");
					}
				}
				result_json.append("]},");
				//拼接抽油机位置扭矩因数曲线数据
				result_json.append("\"PRTF\":{");
				String CrankAngle="[";
				String PR="[";
				String TF="[";
				JSONObject prtfJsonObject = JSONObject.fromObject("{\"data\":"+prtf+"}");//解析数据
				JSONArray prtfJsonArray = prtfJsonObject.getJSONArray("data");
				for(int j=0;j<prtfJsonArray.size();j++){
					JSONObject everydata = JSONObject.fromObject(prtfJsonArray.getString(j));
					CrankAngle+=everydata.getString("CrankAngle");
					PR+=everydata.getString("PR");
					TF+=everydata.getString("TF");
					if(j<prtfJsonArray.size()-1){
						CrankAngle+=",";
						PR+=",";
						TF+=",";
					}
				}
				CrankAngle+="]";
				PR+="]";
				TF+="]";
				result_json.append("\"CrankAngle\":"+CrankAngle+",");
				result_json.append("\"PR\":"+PR+",");
				result_json.append("\"TF\":"+TF+"}");
    			
				result_json.append("}");
    			result_json.append("}");
    			
    			
    			String responseData=StringManagerUtils.sendPostMethod(inversionUrl, result_json.toString(),"utf-8");
    			
    			java.lang.reflect.Type type = new TypeToken<InversioneFSdiagramResponseData>() {}.getType();
    			InversioneFSdiagramResponseData inversioneFSdiagramResponseData=gson.fromJson(responseData, type);
    			if(inversioneFSdiagramResponseData!=null&&inversioneFSdiagramResponseData.getResultStatus()==1){
    				if(this.PSToFSService.reInverDiagram(obj[1]+"",inversioneFSdiagramResponseData)){
    					successCount++;
    				}else{
    					defaultCount++;
    				}
    			}else{
    				defaultCount++;
    			}
    		}
    	}
    	
		String json = "{success:true,totalCount:"+totalCount+",successCount:"+successCount+",defaultCount:"+defaultCount+"}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getElectricAnalysisRealtimeProfileData")
	public String getElectricAnalysisRealtimeProfileData() throws Exception {
		orgId=ParamUtils.getParameter(request, "orgId");
		
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			User user=null;
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = PSToFSService.getElectricAnalysisRealtimeProfileData(orgId);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getElectricAnalysisRealtimeProfilePieData")
	public String getElectricAnalysisRealtimeProfilePieData() throws Exception {
		orgId=ParamUtils.getParameter(request, "orgId");
		String type = ParamUtils.getParameter(request, "type");
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			User user=null;
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = PSToFSService.getElectricAnalysisRealtimeProfilePieData(orgId,type);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	
	@RequestMapping("/getElectricAnalysisRealtimeDetailsList")
	public String getElectricAnalysisRealtimeDetailsList() throws Exception {
		orgId = ParamUtils.getParameter(request, "orgId");
		orgId = findCurrentUserOrgIdInfo(orgId);
		wellName = ParamUtils.getParameter(request, "wellName");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		
		String egkmc = ParamUtils.getParameter(request, "egkmc");
		String timeEff = ParamUtils.getParameter(request, "timeEff");
		String wattBalance = ParamUtils.getParameter(request, "wattBalance");
		String iBalance = ParamUtils.getParameter(request, "iBalance");
		String rydl = ParamUtils.getParameter(request, "rydl");
		
		String type = ParamUtils.getParameter(request, "type");
		String statValue = ParamUtils.getParameter(request, "statValue");
		String wellType = ParamUtils.getParameter(request, "wellType");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		if(StringManagerUtils.isNotNull(wellName)&&!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.acquisitionTime),'yyyy-mm-dd') from v_analysishistory_elec t where t.wellName='"+wellName+"'  ";
			List list = this.commonDataService.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-1);
		}
		
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		
		String json = PSToFSService.getElectricAnalysisRealtimeDatailsList(orgId, wellName, pager,type,wellType,startDate,endDate,statValue,
				egkmc,timeEff,wattBalance,iBalance,rydl);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getRealtimeAnalysisAndAcqData")
	public String getRealtimeAnalysisAndAcqData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		wellName = ParamUtils.getParameter(request, "wellName");
		this.pager = new Page("pagerForm", request);
		String json =PSToFSService.getRealtimeAnalysisAndAcqData(id,wellName);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getRealtimeDiagramAnalysisAndAcqData")
	public String getRealtimeDiagramAnalysisAndAcqData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		wellName = ParamUtils.getParameter(request, "wellName");
		this.pager = new Page("pagerForm", request);
		String json =PSToFSService.getRealtimeDiagramAnalysisAndAcqData(id,wellName);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getElecAnalysisRealtimeDetailsCurveData")
	public String getElecAnalysisRealtimeDetailsCurveData() throws Exception {
		String json = "";
		String wellName = ParamUtils.getParameter(request, "wellName");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String itemName = ParamUtils.getParameter(request, "itemName");
		String itemCode = ParamUtils.getParameter(request, "itemCode");
		String type = ParamUtils.getParameter(request, "type");
		this.pager = new Page("pagerForm", request);
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.acquisitionTime),'yyyy-mm-dd') from v_analysishistory_elec t where wellName='"+wellName+"'  ";
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
		json =  this.PSToFSService.getElecAnalysisRealtimeDetailsCurveData(wellName, startDate,endDate,itemName,itemCode,type);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getElectricAnalysisDailyProfileData")
	public String getElectricAnalysisDailyProfileData() throws Exception {
		orgId=ParamUtils.getParameter(request, "orgId");
		String date=ParamUtils.getParameter(request, "date");
		if(!StringManagerUtils.isNotNull(date)){
			String sql = " select to_char(max(t.acquisitionTime),'yyyy-mm-dd') from v_analysishistory_elec t ";
			List list = this.commonDataService.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				date = list.get(0).toString();
			} else {
				date = StringManagerUtils.getCurrentTime();
			}
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			User user=null;
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = PSToFSService.getElectricAnalysisDailyProfileData(orgId,date);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getElectricAnalysisDailyProfilePieData")
	public String getElectricAnalysisDailyProfilePieData() throws Exception {
		orgId=ParamUtils.getParameter(request, "orgId");
		String date=ParamUtils.getParameter(request, "date");
		if(!StringManagerUtils.isNotNull(date)){
			String sql = " select to_char(max(t.calculatedate),'yyyy-mm-dd') from v_dailydata t ";
			List list = this.commonDataService.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				date = list.get(0).toString();
			} else {
				date = StringManagerUtils.getCurrentTime();
			}
		}
		String type = ParamUtils.getParameter(request, "type");
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			User user=null;
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = PSToFSService.getElectricAnalysisDailyProfilePieData(orgId,date,type);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getElectricAnalysisDailyDatailsList")
	public String getElectricAnalysisDailyDatailsList() throws Exception {
		orgId = ParamUtils.getParameter(request, "orgId");
		orgId = findCurrentUserOrgIdInfo(orgId);
		String wellName = ParamUtils.getParameter(request, "wellName");
		String date = ParamUtils.getParameter(request, "date");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		
		String wellType = ParamUtils.getParameter(request, "wellType");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		if(StringManagerUtils.isNotNull(wellName)&&!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.calculatedate),'yyyy-mm-dd') from v_dailydata t where t.wellName='"+wellName+"' ";
			List list = this.commonDataService.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-9);
		}
		
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		
		String json = PSToFSService.getElectricAnalysisDailyDatailsList(orgId, wellName, pager,wellType,date,startDate,endDate);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 描述：查询叠加功图
	 */
	@RequestMapping("/getFSdiagramOverlayData")
	public String getFSdiagramOverlayData() throws IOException {
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = ParamUtils.getParameter(request, "wellName");
		String calculateDate = ParamUtils.getParameter(request, "calculateDate");
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		
		this.pager = new Page("pagerForm", request);
		String json = "";
		try {
			json = PSToFSService.getFSdiagramOverlayData(pager, orgId, wellName, calculateDate);
			pw.print(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDailyAnalysisAndAcqData")
	public String getDailyAnalysisAndAcqData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		this.pager = new Page("pagerForm", request);
		String json =PSToFSService.getDailyAnalysisAndAcqData(id);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getDailyHistoryCurveData")
	public String getDailyHistoryCurveData() throws Exception {
		String json = "";
		wellName = ParamUtils.getParameter(request, "wellName");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String itemName = ParamUtils.getParameter(request, "itemName");
		String itemCode = ParamUtils.getParameter(request, "itemCode");
		
		this.pager = new Page("pagerForm", request);
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.calculateDate),'yyyy-mm-dd') from v_dailydata t  where wellName= '"+wellName+"' ";
			List list = this.commonDataService.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if (!StringManagerUtils.isNotNull(startDate)) {
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-30);
		}
		
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		json =  PSToFSService.getDailyHistoryCurveData(wellName, startDate,endDate,itemName,itemCode);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<File> getSurfaceCardFile() {
		return SurfaceCardFile;
	}

	public void setSurfaceCardFile(List<File> surfaceCardFile) {
		SurfaceCardFile = surfaceCardFile;
	}

	public List<String> getSurfaceCardFileFileName() {
		return SurfaceCardFileFileName;
	}

	public void setSurfaceCardFileFileName(List<String> surfaceCardFileFileName) {
		SurfaceCardFileFileName = surfaceCardFileFileName;
	}

	public List<String> getSurfaceCardFileContentType() {
		return SurfaceCardFileContentType;
	}

	public void setSurfaceCardFileContentType(List<String> surfaceCardFileContentType) {
		SurfaceCardFileContentType = surfaceCardFileContentType;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getWellName() {
		return wellName;
	}

	public void setWellName(String wellName) {
		this.wellName = wellName;
	}

	
}