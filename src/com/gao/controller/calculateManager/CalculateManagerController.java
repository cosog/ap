package com.gao.controller.calculateManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.User;
import com.gao.model.calculate.InversioneFSdiagramResponseData;
import com.gao.model.gridmodel.CalculateManagerHandsontableChangedData;
import com.gao.model.gridmodel.ElecInverCalculateManagerHandsontableChangedData;
import com.gao.service.base.CommonDataService;
import com.gao.service.calculateManager.CalculateManagerService;
import com.gao.utils.Config;
import com.gao.utils.Page;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;

/**
 * 计算结果管理controller
 * 
 * @author zhao 2018-11-30
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/calculateManagerController")
@Scope("prototype")
public class CalculateManagerController extends BaseController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private CalculateManagerService<?> calculateManagerService;
	@Autowired
	private CommonDataService service;
	private int page;
	private int limit;
	private int totals;
	private String wellName;
	private String orgId;
	
	@RequestMapping("/getCalculateResultData")
	public String getCalculateResultData() throws Exception {
		orgId = ParamUtils.getParameter(request, "orgId");
		wellName = ParamUtils.getParameter(request, "wellName");
		
		String wellType = ParamUtils.getParameter(request, "wellType");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String calculateSign = ParamUtils.getParameter(request, "calculateSign");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.acquisitionTime),'yyyy-mm-dd') from tbl_rpc_diagram_hist t";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),0);
		}
//		startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),-120);
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		
		String json = calculateManagerService.getCalculateResultData(orgId, wellName, pager,wellType,startDate,endDate,calculateSign,calculateType);
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
	
	@SuppressWarnings("unused")
	@RequestMapping("/saveRecalculateData")
	public String saveRecalculateData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String orgid=user.getUserorgids();
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		Gson gson = new Gson();
		String json ="{success:true}";
		if("1".equals(calculateType)){
			java.lang.reflect.Type type = new TypeToken<CalculateManagerHandsontableChangedData>() {}.getType();
			CalculateManagerHandsontableChangedData calculateManagerHandsontableChangedData=gson.fromJson(data, type);
			this.calculateManagerService.saveRecalculateData(calculateManagerHandsontableChangedData);
			json ="{success:true}";
		}else if("5".equals(calculateType)){
			java.lang.reflect.Type type = new TypeToken<ElecInverCalculateManagerHandsontableChangedData>() {}.getType();
			ElecInverCalculateManagerHandsontableChangedData elecInverCalculateManagerHandsontableChangedData=gson.fromJson(data, type);
			this.calculateManagerService.saveElecInverPumpingUnitData(elecInverCalculateManagerHandsontableChangedData);
			this.calculateManagerService.saveElecInverOptimizeHandsontableData(elecInverCalculateManagerHandsontableChangedData, orgid);
			
			//进行反演计算
			String inversionUrl=Config.getInstance().configFile.getAgileCalculate().getESDiagram().getInversion().getUrl().getMotorauto()[0];
			StringBuffer result_json = new StringBuffer();
			for(int i=0;elecInverCalculateManagerHandsontableChangedData!=null&&i<elecInverCalculateManagerHandsontableChangedData.getUpdatelist().size();i++){
				String sql="select t.wellname,t2.id as diagramid,to_char(t2.acquisitionTime,'yyyy-mm-dd hh24:mi:ss') as acquisitionTime,"
	    				+ " t2.spm,t2.rawpower_curve,t2.rawcurrent_curve,t2.rawrpm_curve, "
	    				+ " t4.manufacturer,t4.model,t4.stroke,decode(t4.crankrotationdirection,'顺时针','Clockwise','Anticlockwise'),"
	    				+ " t4.offsetangleofcrank,t5.offsetangleofcrankps,t4.crankgravityradius,t4.singlecrankweight,t4.structuralunbalance,"
	    				+ " t4.gearreducerratio,t4.gearreducerbeltpulleydiameter, t4.balanceposition,t4.balanceweight,"
	    				+ " t5.surfacesystemefficiency,t5.fs_leftpercent,t5.fs_rightpercent,"
	    				+ " t5.wattangle,t5.filtertime_watt,t5.filtertime_i,t5.filtertime_rpm,t5.filtertime_fsdiagram,t5.filtertime_fsdiagram_l,t5.filtertime_fsdiagram_r,"
	    				+ " t4.prtf "
	    				+ " from tbl_wellinformation t,tbl_rpc_diagram_hist t2,tbl_rpcinformation t4,tbl_rpc_inver_opt t5 "
	    				+ " where t.id=t2.wellid and t.id=t4.wellid and t.id=t5.wellid "
						+ " and t2.id="+elecInverCalculateManagerHandsontableChangedData.getUpdatelist().get(i).getId();
	    			
	    		List<?> list = service.findCallSql(sql);
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
	    			result_json.append("\"SurfaceSystemEfficiency\":"+obj[20]+",");
	    			
	    			result_json.append("\"LeftPercent\":"+obj[21]+",");
					result_json.append("\"RightPercent\":"+obj[22]+",");
					result_json.append("\"WattAngle\":"+obj[23]+",");
					result_json.append("\"WattTimes\":"+obj[24]+",");
					result_json.append("\"ITimes\":"+obj[25]+",");
					result_json.append("\"RPMTimes\":"+obj[26]+",");
					result_json.append("\"FSDiagramTimes\":"+obj[27]+",");
					result_json.append("\"FSDiagramLeftTimes\":"+obj[28]+",");
					result_json.append("\"FSDiagramRightTimes\":"+obj[29]+",");
	    			
	    			//抽油机数据
	    			result_json.append("\"PumpingUnit\":{");
	    			
	    			//位置扭矩因数
	    			String prtf="";
	    			if(obj[30]!=null){
	    				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[30]);
						realClob = (CLOB) proxy.getWrappedClob(); 
						prtf=StringManagerUtils.CLOBtoString(realClob);
	    			}
	    			
					
					result_json.append("\"Manufacturer\":\""+obj[7]+"\",");
					result_json.append("\"Model\":\""+obj[8]+"\",");
					result_json.append("\"Stroke\":"+obj[9]+",");
					result_json.append("\"CrankRotationDirection\":\""+obj[10]+"\",");
					result_json.append("\"OffsetAngleOfCrank\":"+obj[11]+",");
					result_json.append("\"OffsetAngleOfCrankPS\":"+obj[12]+",");
					result_json.append("\"CrankGravityRadius\":"+obj[13]+",");
					result_json.append("\"SingleCrankWeight\":"+obj[14]+",");
					result_json.append("\"StructuralUnbalance\":"+obj[15]+",");
					result_json.append("\"GearReducerRatio\":"+obj[16]+",");
					result_json.append("\"GearReducerBeltPulleyDiameter\":"+obj[17]+",");
					result_json.append("\"Balance\":{");
					result_json.append("\"EveryBalance\":[");
					
					//拼接抽油机平衡块数据
					String[] BalancePositionArr=(obj[18]+"").split(",");
					String[] BalanceWeightArr=(obj[19]+"").split(",");
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
					
					if(StringManagerUtils.isNotNull(prtf)){
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
	    			
	    			java.lang.reflect.Type type2 = new TypeToken<InversioneFSdiagramResponseData>() {}.getType();
	    			InversioneFSdiagramResponseData inversioneFSdiagramResponseData=gson.fromJson(responseData, type2);
	    			if(inversioneFSdiagramResponseData!=null){
	    				this.calculateManagerService.reInverDiagram(obj[1]+"",inversioneFSdiagramResponseData);
	    			}
	    		}
			}
			
			
		}
		
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		
		return null;
	}
	
	
	/**
	 * <p>
	 * 描述：计算维护模块模块计算标志下拉框
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getCalculateStatusList")
	public String getCalculateStatusList() throws Exception {
		orgId = ParamUtils.getParameter(request, "orgId");
		String welName = ParamUtils.getParameter(request, "welName");
		String wellType = ParamUtils.getParameter(request, "wellType");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		if (!StringManagerUtils.isNotNull(orgId)) {
			User user = null;
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserOrgid();
			}
		}
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.acquisitionTime),'yyyy-mm-dd') from tbl_rpc_diagram_hist t";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),0);
		}
		String json = this.calculateManagerService.getCalculateStatusList(orgId,welName,wellType,startDate,endDate);
//		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
//		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * <p>
	 * 描述：关联当前生产数据重新计算历史
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/recalculateByProductionData")
	public String recalculateByProductionData() throws Exception {
		orgId = ParamUtils.getParameter(request, "orgId");
		String wellName = ParamUtils.getParameter(request, "wellName");
		String wellType = ParamUtils.getParameter(request, "wellType");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String calculateSign = ParamUtils.getParameter(request, "calculateSign");
		if (!StringManagerUtils.isNotNull(orgId)) {
			User user = null;
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserOrgid();
			}
		}
		this.calculateManagerService.recalculateByProductionData(orgId,wellName,wellType,startDate,endDate,calculateSign);
		String json ="{success:true}";
//		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
//		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * <p>
	 * 描述：导出功图诊断计产请求数据
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportFSDiagramCalculateRequestData")
	public String exportFSDiagramCalculateRequestData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd_HHmmss");//设置日期格式
		
		String wellName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "wellName"),"utf-8");
		String acquisitionTime=ParamUtils.getParameter(request, "acquisitionTime");
		String json=calculateManagerService.getFSDiagramCalculateRequestData(wellName, acquisitionTime);
		
		Date date = df.parse(acquisitionTime);
		acquisitionTime=df2.format(date);
		
		String fileName="请求数据-"+wellName+"-"+acquisitionTime+".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		try {
        	response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
            InputStream in = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer,0,len);
            }
            in.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		StringManagerUtils.deleteFile(path);
		return null;
	}
	

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotals() {
		return totals;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}
	
	

	public String getWellName() {
		return wellName;
	}

	public void setWellName(String wellName) {
		this.wellName = wellName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
