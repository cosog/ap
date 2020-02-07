package com.gao.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.ProductionOutWellInfo;
import com.gao.model.User;
import com.gao.model.WellInformation;
import com.gao.model.gridmodel.ProductionOutGridPanelData;
import com.gao.model.gridmodel.WellHandsontableChangedData;
import com.gao.model.gridmodel.WellProHandsontableChangedData;
import com.gao.service.back.ProductionDataManagerService;
import com.gao.service.base.CommonDataService;
import com.gao.utils.Constants;
import com.gao.utils.Page;
import com.gao.utils.PagingConstants;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * <p>描述：采出井生产数据</p>
 * 
 * @author gao 2014-05-09
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/productionDataController")
@Scope("prototype")
public class ProductionDataController extends BaseController {
	private static Log log = LogFactory.getLog(ProductionDataController.class);
	private static final long serialVersionUID = 1L;
	private String startDate;
	private String endDate;
	private String jbh;
	private String jh;
	private String limit;
	private List<ProductionOutWellInfo> list;
	private String msg = "";
	private String orgCode;
	private String orgId;
	private ProductionOutWellInfo out;
	private String page;
	private String resCode;
	@Autowired
	private ProductionDataManagerService<ProductionOutWellInfo> services;
	@Autowired
	private CommonDataService commonDataService;
	

	/**
	 * @return null
	 * @throws IOException
	 * @argument object "no" ,"add"
	 */
	@RequestMapping("/doBzgtBhEdit")
	public String doBzgtBhEdit() throws IOException {
		String result = "";
		PrintWriter out1 = response.getWriter();
		ProductionOutWellInfo pro = new ProductionOutWellInfo();
		try {
			int bzgtbh = ParamUtils.getIntegerParameter(request, "bzgtbh", 1);
			int jbh = ParamUtils.getIntegerParameter(request, "jbh", 1);
			pro.setBzgtbh(bzgtbh);
			pro.setJbh(jbh);
			pro.setRcql(0.0);
			this.services.doBzgtBhEdit(pro);
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			out1.print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
			out1.print(result);
		}
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
	@RequestMapping("/saveWellProHandsontableData")
	public String saveWellProHandsontableData() throws Exception {

		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String orgid=user.getUserorgids();
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "");
		System.out.println(data);
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<WellProHandsontableChangedData>() {}.getType();
		WellProHandsontableChangedData wellProHandsontableChangedData=gson.fromJson(data, type);
		this.services.saveProductionDataEditerGridData(wellProHandsontableChangedData, orgid);
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

	@RequestMapping("/findProductionDataByJhList")
	public String findProductionDataByJhList() throws Exception {
		String orgCode = ParamUtils.getParameter(request, "orgCode");
		list = this.services.fingProductionDataByJhList(orgCode);
		String json = "";
		StringBuffer sbf = new StringBuffer();
		sbf.append("{");
		sbf.append("\"totals\":" + 10000).append(" ,\"list\":[");
		for (int i = 0; i < list.size(); i++) {
			sbf.append(" {\"jh\":\"" + list.get(i)).append("\"},");
			log.debug(" Jh list = " + list.get(i) + " , ");
		}
		sbf.append("]}");
		json = sbf.toString();
		log.debug(" json = " + json);
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
	 * <p>描述：判断当前井名是否已经已经存在了</p>
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/judgeWellExistOrNot")
	public String judgeWellExistOrNot() throws IOException {
		String jbh = ParamUtils.getParameter(request, "jbh");
		boolean flag = this.services.judgeWellExistsOrNot(jbh);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		StringBuffer dynSbf = new StringBuffer();
		try {
			if (flag) {
				dynSbf.append("{success:true,msg:'1'}");
			} else {
				dynSbf.append("{success:true,msg:'0'}");
			}

			String json = "";
			json = dynSbf.toString();
			pw.print(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.flush();
		pw.close();
		return null;
	}

	@RequestMapping("/loadProductionOutWellID")
	public String loadProductionOutWellID() throws Exception {
		// TODO Auto-generated method stub
		List<?> list = this.services.loadproductionOutWellID(ProductionOutWellInfo.class);
		log.debug("loadProductionInWellID list==" + list.size());
		WellInformation op = null;
		List<WellInformation> olist = new ArrayList<WellInformation>();
		for (int i = 0; i < list.size(); i++) {
			// 使用对象数组
			Object[] objArray = (Object[]) list.get(i);
			// 最后使用forEach迭代obj对象
			op = new WellInformation();
			op.setJlbh((Integer) objArray[0]);
			op.setJh(objArray[1].toString());
			olist.add(op);
		}
		Gson g = new Gson();
		String json = g.toJson(olist);
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
	 * <p>描述：下拉菜单三级联动共用Action数据方法</p>
	 * 
	 * @author gao 2014-05-09
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryProduceOutDataParams")
	public String queryProduceOutDataParams() throws Exception {
		String orgCode = ParamUtils.getParameter(request, "orgCode");
		String resCode = ParamUtils.getParameter(request, "resCode");
		String type = ParamUtils.getParameter(request, "type");
		String json = this.services.queryProduceOutDataInfoParams(orgCode, resCode, type);
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
	/**<p>描述：获取举升类型的下拉菜单树形数据方法</p>
	 * 
	 * @return null
	 * @throws Exception
	 */
	@RequestMapping("/showLiftTypeTree")
	public String showLiftTypeTree() throws Exception {
		String json = this.services.showLiftTypeTree();
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

	/** <p>描述：显示采出井生产数据grid 信息</p>
	 * 
	 * @author gao 2014-05-09
	 * @return
	 */
	@RequestMapping("/showProductionOutData")
	public String showProductionOutData() {
		Map<String, Object> map = new HashMap<String, Object>();
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		int offset = (intPage - 1) * pageSize + 1;
		jh = ParamUtils.getParameter(request, "jh");
		orgId=ParamUtils.getParameter(request, "orgId");
		String wellType=ParamUtils.getParameter(request, "wellType");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		int recordCount =StringManagerUtils.StringToInteger(ParamUtils.getParameter(request, "recordCount")) ;
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("jh", jh);
		map.put("orgCode", orgCode);
		map.put("resCode", resCode);
		map.put("orgId", orgId);
		this.pager = new Page("pagerForm", request);// 分页Page 工具类
		String json = this.services.getProductionWellProductionData(jh,orgId,pager,recordCount,wellType);
//		log.warn("showProductionOutData=-= " + json);
		//HttpServletResponse response = ServletActionContext.getResponse();
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
	
	@RequestMapping("/exportWellProdInformationData")
	public String exportWellProdInformationData() throws Exception {
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		int offset = (intPage - 1) * pageSize + 1;
		//wellInformationName = new String(wellInformationName.getBytes("iso-8859-1"), "utf-8");
//		String orgId=this.findCurrentUserOrgIdInfo("");
		jh = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "jh"),"utf-8");
		orgId=ParamUtils.getParameter(request, "orgId");
		String wellType=ParamUtils.getParameter(request, "wellType");
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);// 新疆分页Page 工具类
		String json = this.services.exportWellProdInformationData(jh,orgId,pager,wellType);
		
		
		this.commonDataService.exportGridPanelData(response,fileName,title, heads, fields,json);
		return null;
	}

	/**
	 * <p>
	 * 描述：加载采出井生产数据的下拉菜单数据信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadMenuTypeData")
	public String loadMenuTypeData() throws Exception {
		String type = ParamUtils.getParameter(request, "type");
		String json = this.services.loadMenuTypeData(type);
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
	 * 描述：加载采出井生产数据的躯替类型下拉菜单数据信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadQtlxTypeData")
	public String loadQtlxTypeData() throws Exception {
		String type = ParamUtils.getParameter(request, "type");
		String json = this.services.loadQtlxTypeData(type);
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
	 * 描述：显示采出井生产数据中的标准功图
	 * </p>
	 * @return
	 */
	@RequestMapping("/showBzgtData")
	public String showBzgtData()throws Exception{
		jbh = ParamUtils.getParameter(request, "jbh");
		String json = this.services.queryBzgtData(jbh);
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
	 * 描述：显示采出井生产数据中的功图列表
	 * </p>
	 * @return
	 */
	@RequestMapping("/showSurfaceCardListData")
	public String showSurfaceCardListData() throws IOException {
	jbh = ParamUtils.getParameter(request, "jbh");
	startDate = ParamUtils.getParameter(request, "startDate");
	endDate = ParamUtils.getParameter(request, "endDate");
	if(jbh==""){
		jbh="0";
	}
	response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
	response.setHeader("Cache-Control", "no-cache");
	PrintWriter pw = response.getWriter();
	this.pager = new Page("pagerForm", request);
	String json = "";
	try {
		if (endDate != null && !endDate.equals("new") && !"".equals(endDate)) {
			endDate = StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate));
		}
		json = this.services.querySurfaceCardlistData(pager, jbh, startDate, endDate);
		pw.print(json);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	pw.flush();
	pw.close();
	return null;
}
	
	
	
	public String getEndDate() {
		return endDate;
	}

	public String getJbh() {
		return jbh;
	}

	public String getJh() {
		return jh;
	}

	public String getLimit() {
		return limit;
	}

	public List<ProductionOutWellInfo> getList() {
		return list;
	}

	public String getMsg() {
		return msg;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public ProductionOutWellInfo getOut() {
		return out;
	}

	public String getPage() {
		return page;
	}

	public String getResCode() {
		return resCode;
	}

	public String getStartDate() {
		return startDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setJbh(String jbh) {
		this.jbh = jbh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void setList(List<ProductionOutWellInfo> list) {
		this.list = list;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOut(ProductionOutWellInfo out) {
		this.out = out;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getOrgId() {
		return orgId;
	}


	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}



}
