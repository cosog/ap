package com.gao.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.ReservoirProperty;
import com.gao.model.User;
import com.gao.model.gridmodel.ResProHandsontableChangedData;
import com.gao.model.gridmodel.ReservoirPropertyGridPanelData;
import com.gao.model.gridmodel.WellHandsontableChangedData;
import com.gao.model.gridmodel.WellProHandsontableChangedData;
import com.gao.service.back.ReservoirPropertyManagerService;
import com.gao.service.base.CommonDataService;
import com.gao.utils.Constants;
import com.gao.utils.Page;
import com.gao.utils.PagingConstants;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * <p>
 * 描述：区块物性数据处理Action层
 * </p>
 * 
 * @author gao 2014-06-04
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/reservoirPropertyManagerController")
@Scope("prototype")
public class ReservoirPropertyManagerController extends BaseController {

	private static Log log = LogFactory.getLog(ReservoirPropertyManagerController.class);
	private static final long serialVersionUID = -281275682819237996L;
	@Autowired
	private ReservoirPropertyManagerService<ReservoirProperty> reservoirPropertyService;
	@Autowired
	private CommonDataService commonDataService;
	private List<ReservoirProperty> list;

	private ReservoirProperty reservoirProperty;
	private String limit;
	private String msg = "";
	private String reservoirPropertyName;

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadResMenuList")
	public String loadResMenuList() throws Exception {

		String resCode = ParamUtils.getParameter(request, "resCode");
		String type = ParamUtils.getParameter(request, "type");

		String json = this.reservoirPropertyService.loadResMenuList(resCode, type);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
		// return "MonitorPumpingUnitOutputParams";
	}

	/**
	 * <p>
	 * 描述：区块物性数据新增
	 * </p>
	 * 
	 * @return null
	 * @throws IOException
	 * @argument object "no" ,"add"
	 */
	@RequestMapping("/doReservoirPropertyAdd")
	public String doReservoirPropertyAdd() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			this.reservoirPropertyService.saveOrUpdateorDeleteProReservoirProperty(reservoirProperty, "no", "add");
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			out.print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
			out.print(result);
		}
		return null;
	}

	/**<p>描述：判断该油藏物性数据信息是否存在</p>
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/judgeResExistOrNot")
	public String judgeResExistOrNot() throws IOException {
		String resCode = ParamUtils.getParameter(request, "resCode");
		boolean flag = this.reservoirPropertyService.judgeResExistsOrNot(resCode);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		String json = "";
		if (flag) {
			json = "{success:true,msg:'1'}";
		} else {
			json = "{success:true,msg:'0'}";
		}
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	/**
	 * <p>
	 * 描述：区块物性数据批量删除
	 * </p>
	 * 
	 * @return
	 */
	@RequestMapping("/doReservoirPropertyBulkDelete")
	public String doReservoirPropertyBulkDelete() {
		try {
			reservoirProperty = new ReservoirProperty();
			String ReservoirPropertyIds = ParamUtils.getParameter(request, "paramsId");
			reservoirProperty.setJlbh(0);
			reservoirProperty.setYymd(0.00);
			reservoirProperty.setSmd(0.00);
			reservoirProperty.setTrqxdmd(0.00);
			//reservoirProperty.setYsrjqyb(0.00);
			reservoirProperty.setBhyl(0.00);
			//reservoirProperty.setDmtqyynd(0.00);
			reservoirProperty.setYqcyl(0.00);
			reservoirProperty.setYqczbsd(0.00);
			reservoirProperty.setYqczbwd(0.00);
			if (ReservoirPropertyIds != null || ReservoirPropertyIds != "") {

				this.reservoirPropertyService.saveOrUpdateorDeleteProReservoirProperty(reservoirProperty, ReservoirPropertyIds, "delete");
			}
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			String result = "{success:true,flag:true}";
			response.getWriter().print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>
	 * 描述：区块物性数据编辑
	 * </p>
	 * 
	 * @return
	 */
	@RequestMapping("/doReservoirPropertyEdit")
	public String doReservoirPropertyEdit() {
		try {
			this.reservoirPropertyService.saveOrUpdateorDeleteProReservoirProperty(reservoirProperty, "no", "modify");
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			String result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.getWriter().print(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/saveReservoirPropertyGridData")
	public String saveReservoirPropertyGridData() throws Exception {
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "");
		Gson gson = new Gson();
		List<ReservoirPropertyGridPanelData> list=new ArrayList<ReservoirPropertyGridPanelData>();
		list= gson.fromJson(data,new TypeToken<List<ReservoirPropertyGridPanelData>>() {}.getType());
		for(int i=0;i<list.size();i++){
			this.reservoirPropertyService.saveReservoirPropertyGridData(list.get(i), "no", "modify");
		}
		String json ="";
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
	

	/**
	 * <p>
	 * 描述：油井基本信息Handsontable表格编辑数据保存
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveReservoirPropertyHandsontableData")
	public String saveReservoirPropertyHandsontableData() throws Exception {
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "");
		System.out.println(data);
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<ResProHandsontableChangedData>() {}.getType();
		ResProHandsontableChangedData resProHandsontableChangedData=gson.fromJson(data, type);
		this.reservoirPropertyService.saveReservoirPropertyGridData(resProHandsontableChangedData);
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

	/**
	 * <p>
	 * 描述：查询区块物性数据
	 * </p>
	 * 
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping("/doReservoirPropertyShow")
	public String doReservoirPropertyShow() throws IOException, SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		reservoirPropertyName=ParamUtils.getParameter(request, "reservoirPropertyName");
		page=ParamUtils.getParameter(request, "page");
		limit=ParamUtils.getParameter(request, "limit");
		int recordCount =StringManagerUtils.StringToInteger(ParamUtils.getParameter(request, "recordCount"));
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "10" : limit);
		int offset = (intPage - 1) * pageSize + 1;
		
		//String orgId=this.findCurrentUserOrgIdInfo("");
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("reservoirPropertyName", reservoirPropertyName);
		//map.put("orgId", orgId);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		String json = this.reservoirPropertyService.getReservoirPropertyProList(map,recordCount);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportReservoirPropertyData")
	public String exportReservoirPropertyData() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		reservoirPropertyName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "reservoirPropertyName"),"utf-8");
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		page=ParamUtils.getParameter(request, "page");
		limit=ParamUtils.getParameter(request, "limit");
		
		map.put("reservoirPropertyName", reservoirPropertyName);
		String json = this.reservoirPropertyService.exportReservoirPropertyData(map);
		this.commonDataService.exportGridPanelData(response,fileName,title, heads, fields,json);
		return null;
	}
	
	@RequestMapping("/getReservoirListData")
	public String getReservoirListData() throws Exception {
		this.pager=new Page("pageForm",request);
		String reservoirPropertyName = ParamUtils.getParameter(request, "reservoirPropertyName");
		
		String json = this.reservoirPropertyService.getReservoirListData(pager,reservoirPropertyName);
//		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	

	public String getLimit() {
		return limit;
	}

	public String getReservoirPropertyName() {
		return reservoirPropertyName;
	}

	public void setReservoirPropertyName(String reservoirPropertyName) {
		this.reservoirPropertyName = reservoirPropertyName;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	private String page;

	public List<ReservoirProperty> getList() {
		return list;
	}

	public void setList(List<ReservoirProperty> list) {
		this.list = list;
	}

	public ReservoirProperty getReservoirProperty() {
		return reservoirProperty;
	}

	public void setReservoirProperty(ReservoirProperty reservoirProperty) {
		this.reservoirProperty = reservoirProperty;
	}

}
