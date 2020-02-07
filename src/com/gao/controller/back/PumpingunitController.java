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
import com.gao.model.Pumpingunit;
import com.gao.service.back.PumpingunitService;
import com.gao.utils.Constants;
import com.gao.utils.Page;
import com.gao.utils.ParamUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 后台管理-数据配置－设备管理-抽油机1
 * 
 * @author ding
 * 
 */
@Controller
@RequestMapping("/pumpingunitController")
@Scope("prototype")
public class PumpingunitController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(PumpingunitController.class);
	@Autowired
	private PumpingunitService<Pumpingunit> service;
	private Pumpingunit pumpingunit = new Pumpingunit();
	private List<Pumpingunit> list;
	private int limit = 30;
	private int page;
	private int totals = 0;
	private String sccj = "";
	private String cyjxh = "";
	private String paramsId;

	@RequestMapping("/addPumpingunit")
	public String addPumpingunit() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			this.service.insertObject(pumpingunit);
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

	@RequestMapping("/updatePumpingunit")
	public String updatePumpingunit() {
		String result = "";
		try {
			this.service.update(pumpingunit);
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

	@RequestMapping("/deletePumpingunit")
	public String deletePumpingunit() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		paramsId = ParamUtils.getParameter(request, "paramsId");
		try {
			this.service.deleteObject(paramsId);
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

	@RequestMapping("/findAllList")
	public String findAllList() throws Exception {
		log.debug(" pumpingunitAction Enter ==");
		list = new ArrayList<Pumpingunit>();
		String sccj = ParamUtils.getParameter(request, "sccj");
		String cyjxh = ParamUtils.getParameter(request, "cyjxh");
		int intpage = page == 0 ? 1 : page;
		int pageSize = limit;
		int offset = (intpage - 1) * pageSize;
		log.debug(" sccj=======" + sccj + ",cyjxh=========" + cyjxh);
		String json = "";
		this.pager = new Page("pagerForm", request);
		json=this.service.findAllLIst(offset, pageSize, sccj, cyjxh,pager);
		log.debug("json ======" + json);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	@RequestMapping("/queryPumpUnitParams")
	public String queryPumpUnitParams() throws Exception {
		String sccj = ParamUtils.getParameter(request, "sccj");
		String cyjxh = ParamUtils.getParameter(request, "cyjxh");
		String type = ParamUtils.getParameter(request, "type");
		String json = this.service.queryPumpUnitParams(sccj, cyjxh, type);
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
	@RequestMapping("/findByLiIdst")
	public String findByLiIdst() throws Exception {
		list = new ArrayList<Pumpingunit>();
		list = this.service.findByLiIdst(sccj);
		totals = list.size();
		String json = "";
		StringBuffer sbf = new StringBuffer();
		if (list.size() > 0) {
			sbf.append("{");
			sbf.append("\"totals\":" + totals).append(" ,\"list\":[");
			for (Object o : list) {
				Object[] obj = (Object[]) o;
				sbf.append(" {\"sccj\":\"" + obj[0]).append("\",");
				sbf.append(" \"cyjxh\":\"" + obj[1]).append("\"},");
			}
			json = sbf.toString();
			json = json.substring(0, json.length() - 1);
			json += "]}";
			sbf.append("]}");
		} else {
			json = "{list:[],totals:0}";
		}
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
	@RequestMapping("/findBycyjxhList")
	public String findBycyjxhList() throws Exception {
		String sccj = ParamUtils.getParameter(request, "sccj");
		list = new ArrayList<Pumpingunit>();
		list = this.service.findBycyjxhList(sccj);
		totals = list.size();
		String json = "";
		StringBuffer sbf = new StringBuffer();
		if (list.size() > 0) {
			sbf.append("{");
			sbf.append("\"totals\":" + totals).append(" ,\"list\":[");
			for (Object o : list) {
				Object[] obj = (Object[]) o;
				sbf.append(" {\"cyjid\":" + obj[0]).append(",");
				sbf.append(" \"cyjxh\":\"" + obj[1]).append("\"}");

			}
			json = sbf.toString();
			json = json.substring(0, json.length() - 1);
			json += "]}";
		} else {
			json = "{list:[],totals:0}";
		}
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
	@RequestMapping("/findBysccjList")
	public String findBysccjList() throws Exception {
		list = new ArrayList<Pumpingunit>();
		list = this.service.findBysccjList();
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
	@RequestMapping("/savePumpingUnitEditerGridData")
	public String savePumpingUnitEditerGridData() throws Exception {
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "");
		JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+data+"}");//解析数据
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		for(int i=0;i<jsonArray.size();i++){
			JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
			this.service.savePumpingUnitEditerGridData(everydata);
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

	public Pumpingunit getPumpingunit() {
		return pumpingunit;
	}

	public void setPumpingunit(Pumpingunit pumpingunit) {
		this.pumpingunit = pumpingunit;
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

	public List<Pumpingunit> getList() {
		return list;
	}

	public void setList(List<Pumpingunit> list) {
		this.list = list;
	}

	public String getSccj() {
		return sccj;
	}

	public void setSccj(String sccj) {
		this.sccj = sccj;
	}

	public String getCyjxh() {
		return cyjxh;
	}

	public void setCyjxh(String cyjxh) {
		this.cyjxh = cyjxh;
	}

	public String getParamsId() {
		return paramsId;
	}

	public void setParamsId(String paramsId) {
		this.paramsId = paramsId;
	}

}
