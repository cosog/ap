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
import com.gao.model.Org;
import com.gao.model.User;
import com.gao.model.gridmodel.WellGridPanelData;
import com.gao.model.gridmodel.WellHandsontableChangedData;
import com.gao.model.WellInformation;
import com.gao.model.calculate.PCPCalculateResponseData;
import com.gao.service.back.WellInformationManagerService;
import com.gao.service.base.CommonDataService;
import com.gao.tast.EquipmentDriverServerTast;
import com.gao.utils.Constants;
import com.gao.utils.Page;
import com.gao.utils.PagingConstants;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wellInformationManagerController")
@Scope("prototype")
public class WellInformationManagerController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(WellInformationManagerController.class);
	@Autowired
	private WellInformationManagerService<WellInformation> wellInformationManagerService;
	@Autowired
	private CommonDataService service;
	private WellInformation wellInformation;
	private String limit;
	private String msg = "";
	private List<WellInformation> list;
	private String wellInformationName;
	private String liftingType;
	private String orgCode;
	private String resCode;
	private String page;
	private String orgId;
	private int totals;
	
	/**
	 * <p>
	 * 描述：实现采出井下拉菜单多级联动方法
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadWellComboxList")
	public String loadWellComboxList() throws Exception {
		this.pager=new Page("pageForm",request);
		String wellName = ParamUtils.getParameter(request, "wellName");
		String wellType = ParamUtils.getParameter(request, "wellType");
		orgId=ParamUtils.getParameter(request, "orgId");
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = this.wellInformationManagerService.loadWellComboxList(pager,orgId, wellName,wellType);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	@RequestMapping("/doWellInformationShow")
	public String doWellInformationShow() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		int offset = (intPage - 1) * pageSize + 1;
		//wellInformationName = new String(wellInformationName.getBytes("iso-8859-1"), "utf-8");
//		String orgId=this.findCurrentUserOrgIdInfo("");
		wellInformationName = ParamUtils.getParameter(request, "wellInformationName");
		liftingType=ParamUtils.getParameter(request, "liftingType");
		orgId=ParamUtils.getParameter(request, "orgId");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		orgCode = ParamUtils.getParameter(request, "orgCode");
		resCode = ParamUtils.getParameter(request, "resCode");
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("wellInformationName", wellInformationName);
		map.put("liftingType", liftingType);
		map.put("orgCode", orgCode);
		map.put("resCode", resCode);
		map.put("orgId", orgId);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);// 新疆分页Page 工具类
		String json = this.wellInformationManagerService.getWellInformationProList(map, pager,recordCount);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportWellInformationData")
	public String exportWellInformationData() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		int offset = (intPage - 1) * pageSize + 1;
		//wellInformationName = new String(wellInformationName.getBytes("iso-8859-1"), "utf-8");
//		String orgId=this.findCurrentUserOrgIdInfo("");
		String jh = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "jh"),"utf-8");
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		orgId=ParamUtils.getParameter(request, "orgId");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		orgCode = ParamUtils.getParameter(request, "orgCode");
		resCode = ParamUtils.getParameter(request, "resCode");
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("wellInformationName", wellInformationName);
		map.put("orgCode", orgCode);
		map.put("resCode", resCode);
		map.put("orgId", orgId);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);// 新疆分页Page 工具类
		String json = this.wellInformationManagerService.exportWellInformationData(map, pager,recordCount);
		
		
		this.service.exportGridPanelData(response,fileName,title, heads, fields,json);
		return null;
	}

	@RequestMapping("/judgeWellExistOrNot")
	public String judgeWellExistOrNot() throws IOException {
		String jh = ParamUtils.getParameter(request, "jh");
		boolean flag = this.wellInformationManagerService.judgeWellExistsOrNot(jh);
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

	@RequestMapping("/loadWellInformationID")
	public String loadWellInformationID() throws Exception {
		List<?> list = this.wellInformationManagerService.loadWellInformationID(WellInformation.class);
		log.debug("loadWellInfoOrgs list==" + list.size());
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

	@RequestMapping("/loadWellOrgInfo")
	public String loadWellOrgInfo() throws Exception {
		List<?> list = this.wellInformationManagerService.loadWellOrgInfo();
		log.debug("loadWellOrgInfo list==" + list.size());
		Org op = null;
		List<Org> olist = new ArrayList<Org>();
		for (int i = 0; i < list.size(); i++) {
			// 使用对象数组
			Object[] objArray = (Object[]) list.get(i);
			// 最后使用forEach迭代obj对象
			op = new Org();
			op.setOrgCode(objArray[0].toString());
			op.setOrgName(objArray[1].toString());
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

	@RequestMapping("/queryWellInfoParams")
	public String queryWellInfoParams() throws Exception {
		this.pager=new Page("pageForm",request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String orgid=user.getUserorgids();
		String orgCode = ParamUtils.getParameter(request, "orgCode");
		String resCode = ParamUtils.getParameter(request, "resCode");
		String type = ParamUtils.getParameter(request, "type");
		String jh =ParamUtils.getParameter(request, "jh");
		String jhh =ParamUtils.getParameter(request, "jhh");
		String jc =ParamUtils.getParameter(request, "jc");
		String jtype =ParamUtils.getParameter(request, "jtype");
		String json = this.wellInformationManagerService.queryWellInfoParams(pager,orgid,orgCode, resCode, type,jc,jhh,jh,jtype);
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
	
	

	@RequestMapping("/findByJhList")
	public String findByJhList() throws Exception {
		list = new ArrayList<WellInformation>();
		list = this.wellInformationManagerService.fingWellByJhList();
		totals = list.size();
		String json = "";
		StringBuffer sbf = new StringBuffer();
		sbf.append("{");
		sbf.append("\"totals\":" + totals).append(" ,\"list\":[");
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

	@RequestMapping("/showWellTypeTree")
	public String showWellTypeTree() throws Exception {
		String json = this.wellInformationManagerService.showWellTypeTree();
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
	 * 描述：加载所属井网的下拉菜单数据信息A
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadSsjwType")
	public String loadSsjwType() throws Exception {
		String type = ParamUtils.getParameter(request, "type");
		String json = this.wellInformationManagerService.loadSsjwType(type);
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
	@SuppressWarnings("static-access")
	@RequestMapping("/saveWellHandsontableData")
	public String saveWellHandsontableData() throws Exception {

		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String orgids=user.getUserorgids();
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		System.out.println(data);
		String orgId = ParamUtils.getParameter(request, "orgId");
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<WellHandsontableChangedData>() {}.getType();
		WellHandsontableChangedData wellHandsontableChangedData=gson.fromJson(data, type);
		this.wellInformationManagerService.saveWellEditerGridData(wellHandsontableChangedData, orgids,orgId);
		String json ="{success:true}";
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		
		EquipmentDriverServerTast beeTechDriverServerTast=EquipmentDriverServerTast.getInstance();
		beeTechDriverServerTast.updateWellConfif(wellHandsontableChangedData);
		
		return null;
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping("/editWellName")
	public String editWellName() throws Exception {

		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String orgid=user.getUserorgids();
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "");
		JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+data+"}");//解析数据
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		for(int i=0;i<jsonArray.size();i++){
			JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
			String oldWellName=everydata.getString("oldWellName");
			String newWellName=everydata.getString("newWellName");
			this.wellInformationManagerService.editWellName(oldWellName,newWellName,orgid);
		}
		String json ="{success:true}";
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		
		EquipmentDriverServerTast beeTechDriverServerTast=EquipmentDriverServerTast.getInstance();
		beeTechDriverServerTast.updateWellName(data);
		
		return null;
	}
	
	
	/**
	 * <p>
	 * 描述：获取角色类型的下拉菜单数据信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadSszcdyType")
	public String loadSszcdyType() throws Exception {

		String type = ParamUtils.getParameter(request, "type");
		String json = this.wellInformationManagerService.loadSszcdyType(type);
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

	public WellInformation getWellInformation() {
		return wellInformation;
	}

	public void setWellInformation(WellInformation wellInformation) {
		this.wellInformation = wellInformation;
	}

	public String getLimit() {
		return limit;
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

	public List<WellInformation> getList() {
		return list;
	}

	public void setList(List<WellInformation> list) {
		this.list = list;
	}

	public String getWellInformationName() {
		return wellInformationName;
	}

	public void setWellInformationName(String wellInformationName) {
		this.wellInformationName = wellInformationName;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public int getTotals() {
		return totals;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getLiftingType() {
		return liftingType;
	}

	public void setLiftingType(String liftingType) {
		this.liftingType = liftingType;
	}

}
