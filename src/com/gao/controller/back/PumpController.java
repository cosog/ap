package com.gao.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.Pump;
import com.gao.model.gridmodel.PumpGridPanelData;
import com.gao.service.back.PumpService;
import com.gao.utils.Constants;
import com.gao.utils.Page;
import com.gao.utils.ParamUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 后台管理-数据配置－设备管理-抽油泵
 * 
 * @author dingyaqiang
 * 
 */
@Controller
@RequestMapping("/pumpController")
@Scope("prototype")
public class PumpController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(PumpController.class);
	@Autowired
	private PumpService<Pump> service;
	private Pump pump = new Pump();
	private List<Pump> list;
	private int limit;
	private int page;
	private int totals = 0;
	private String sccj = "";
	private String cybxh = "";
	private int paramsId;

	@RequestMapping("/addPump")
	public String addPump() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			//this.service.insertObject(pump);
			this.service.saveOrUpdateorDeletePump(pump,"no","add");
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			out.print(result);
		} catch (Exception e) {
			e.printStackTrace();
			result = "{success:false,msg:false}";
			out.print(result);
		}
		return null;
	}

	@RequestMapping("/loadPumpInfoForProduceDataOut")
	public String loadPumpInfoForProduceDataOut() throws Exception {

		sccj = ParamUtils.getParameter(request, "sccj");
		String type = ParamUtils.getParameter(request, "type");
		String cybxh=ParamUtils.getParameter(request, "cybxh");
		//sccj = new String(sccj.getBytes("ISO-8859-1"), "utf-8");
		// sccj = java.net.URLDecoder.decode(sccj, "utf-8");
		String json = this.service.loadProduceDataOutWell(sccj,cybxh, type);
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

	@RequestMapping("/delectPump")
	public String delectPump() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			String WellringIds = ParamUtils.getParameter(request, "paramsId");
			//this.service.deleteObject(paramsId);
			pump.initPump();
			this.service.saveOrUpdateorDeletePump(pump,WellringIds,"delete");
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			out.print(result);
		} catch (Exception e) {
			e.printStackTrace();
			result = "{success:false,msg:false}";
			out.print(result);
		}
		return null;
	}

	@RequestMapping("/updatePump")
	public String updatePump() {
		String result = "";
		try {
			//this.service.update(pump);
			this.service.saveOrUpdateorDeletePump(pump,"no","modify");
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.getWriter().print(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			result = "{success:false,msg:false}";
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/savePumpEditerGridData")
	public String savePumpEditerGridData() throws Exception {
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "");
		Gson gson = new Gson();
		List<PumpGridPanelData> list=new ArrayList<PumpGridPanelData>();
		list= gson.fromJson(data,new TypeToken<List<PumpGridPanelData>>() {}.getType());
		for(int i=0;i<list.size();i++){
			this.service.savePumpEditerGridData(list.get(i), "no", "modify");
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

	@RequestMapping("/findAllList")
	public String findAllList() throws Exception {
		log.debug(" PumpAction Enter ==");
		list = new ArrayList<Pump>();
		String sccj =ParamUtils.getParameter(request, "sccj");
		String cybxh =ParamUtils.getParameter(request, "cybxh");
		int intpage = page == 0 ? 1 : page;
		int pageSize = limit;
		int offset = (intpage - 1) * pageSize;
		String json = "";
		this.pager = new Page("pagerForm", request);
		json = this.service.findAllLIst(offset, pageSize, sccj, cybxh,pager);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	@RequestMapping("/findBycybxh")
	public String findBycybxh() throws Exception {
		list = new ArrayList<Pump>();
		list = this.service.findBycybxh(sccj);
		totals = list.size();
		String json = "";
		StringBuffer sbf = new StringBuffer();
		sbf.append("{");
		sbf.append("\"totals\":" + totals).append(" ,\"list\":[");
		for (int i = 0; i < list.size(); i++) {
			sbf.append(" {\"cybxh\":\"" + list.get(i)).append("\"}");
			if (i != list.size() - 1) {
				sbf.append(",");
			}
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

	@RequestMapping("/findByLiIdst")
	public String findByLiIdst() throws Exception {
		list = new ArrayList<Pump>();
		list = this.service.findBycybxh(sccj);
		totals = list.size();
		String json = "";
		StringBuffer sbf = new StringBuffer();
		sbf.append("{");
		sbf.append("\"totals\":" + totals).append(" ,\"list\":[");
		for (int i = 0; i < list.size(); i++) {
			sbf.append(" {\"cybxh\":\"" + list.get(i)).append("\"}");
			if (i != list.size() - 1) {
				sbf.append(",");
			}
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

	@RequestMapping("/queryPumpParams")
	public String queryPumpParams() throws Exception {
		this.pager=new Page("pageForm",request);
		String sccj = ParamUtils.getParameter(request, "sccj");
		String cybxh = ParamUtils.getParameter(request, "cybxh");
		String type = ParamUtils.getParameter(request, "type");
		//sccj=new String(sccj.getBytes("iso-8859-1"),"utf-8");
		String json = this.service.queryPumpParams(pager,sccj, cybxh, type);
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

	@RequestMapping("/findBySCCJ")
	public String findBySCCJ() throws Exception {
		list = new ArrayList<Pump>();
		list = this.service.findBySCCJ();
		totals = list.size();
		String json = "";
		StringBuffer sbf = new StringBuffer();
		sbf.append("{");
		sbf.append("\"totals\":" + totals).append(" ,\"list\":[");
		for (int i = 0; i < list.size(); i++) {
			sbf.append(" {\"sccj\":\"" + list.get(i)).append("\"}");
			if (i != list.size() - 1) {
				sbf.append(",");
			}
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

	public Pump getPump() {
		return pump;
	}

	public void setPump(Pump pump) {
		this.pump = pump;
	}

	public List<Pump> getList() {
		return list;
	}

	public void setList(List<Pump> list) {
		this.list = list;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotals() {
		return totals;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}

	public String getSccj() {
		return sccj;
	}

	public void setSccj(String sccj) {
		this.sccj = sccj;
	}

	public String getcybxh() {
		return cybxh;
	}

	public void setcybxh(String cybxh) {
		this.cybxh = cybxh;
	}

	public int getParamsId() {
		return paramsId;
	}

	public void setParamsId(int paramsId) {
		this.paramsId = paramsId;
	}

}
