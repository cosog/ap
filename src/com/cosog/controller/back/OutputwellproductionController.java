package com.cosog.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.Outputwellproduction;
import com.cosog.service.back.OutputwellproductionService;
import com.cosog.utils.Constants;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.ParamUtils;

/**
 * <p>描述：后台数据配置--采出井生产数据信息</p>
 * 
 * @author gao 2014-06-04
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/outputwellproductionController")
@Scope("prototype")
public class OutputwellproductionController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory
			.getLog(OutputwellproductionController.class);
	private OutputwellproductionService<Outputwellproduction> outputwellproductionService;
	private Outputwellproduction outputwellproduction = new Outputwellproduction();
	private List<Outputwellproduction> list;
	private int limit = 30;
	private String msg = "";
	private int page = 0;
	private int totals = 0;
	private String outputwellproductionName;
	private String jc = "";
	private String jhh = "";
	private String jh = "";
	private String paramsId;

	/**<p>描述：新增采出井生产数据信息</p>
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/addOutputwellproduction")
	public String addOutputwellproduction() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			this.outputwellproductionService.insertObject(outputwellproduction);
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

	@RequestMapping("/updateOutputwellproduction")
	public String updateOutputwellproduction() {
		String result = "";
		int jbh = outputwellproduction.getJbh();
		double rcyl = outputwellproduction.getRcyl();
		Date cjsj = outputwellproduction.getCjsj();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(cjsj);
		try {
			this.outputwellproductionService.update(jbh, rcyl, strDate);
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

	@RequestMapping("/deleteOutputwellproduction")
	public String deleteOutputwellproduction() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		paramsId = ParamUtils.getParameter(request, "paramsId");
		try {
			this.outputwellproductionService.deleteObject(paramsId);
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

	@SuppressWarnings("unused")
	@RequestMapping("/doOutputwellproductionActionShow")
	public String doOutputwellproductionActionShow() throws IOException, SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		int intPage = page == 0 ? 1 : page;
		int pageSize = limit;
		int offset = (intPage - 1) * pageSize + 1;
		// String jc = ParamUtils.getParameter(request, "jc");
		// String jhh = ParamUtils.getParameter(request, "jhh");
		String jh = ParamUtils.getParameter(request, "jh");
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		// map.put("jc", jc);
		// map.put("jhh", jhh);
		// map.put("outputwellproductionName", outputwellproductionName);
		map.put("outputwellproductionName", outputwellproductionName);
//		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		String json = this.outputwellproductionService
				.getOutputwellproductionProList(map);
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	@RequestMapping("/findAllList")
	public String findAllList() throws Exception {
//		log.debug(" strokeAction Enter ==");
		list = new ArrayList<Outputwellproduction>();
		int intpage = page == 0 ? 1 : page;
		int pageSize = limit;
		int offset = (intpage - 1) * pageSize;
//		log.debug(" jh=======" + jh + ",jhh=========" + jhh + ",jc==" + jc);
		list = this.outputwellproductionService.findAllList(offset, pageSize,
				jc, jhh, jh);
		String json = "";
		StringBuffer sbf = new StringBuffer();
		totals = this.outputwellproductionService.rowCount(jc, jhh, jh);
		if (list.size() > 0) {
			sbf.append("{");
			sbf.append("\"totals\":" + totals).append(",\"list\":[");
			for (Object o : list) {
				Object[] obj = (Object[]) o;
				sbf.append(" {\"jlbh\":" + obj[0]).append(",");
				sbf.append(" \"jbh\":" + obj[1]).append(",");
				sbf.append(" \"jc\":\"" + obj[2]).append("\",");
				sbf.append(" \"jhh\":\"" + obj[3]).append("\",");
				sbf.append(" \"jh\":\"" + obj[4]).append("\",");
				sbf.append(" \"rcyl\":" + obj[5]).append(",");
				sbf.append(" \"cjsj\":\"" + (String) obj[6]).append("\"},");
			}
			json = sbf.toString();
			json = json.substring(0, json.length() - 1);
			json += "]}";
		} else {
			json = "{list:[],totals:0}";
		}
//		log.debug(" json = " + json);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	@RequestMapping("/querySingleWellParam")
	public String querySingleWellParam() throws Exception {

		String jhh = ParamUtils.getParameter(request, "jhh");
		String jh = ParamUtils.getParameter(request, "jh");
		String jc = ParamUtils.getParameter(request, "jc");
		String type = ParamUtils.getParameter(request, "type");
		String json = this.outputwellproductionService.querySingleWellParam(jc,jhh, jh, type);
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

	@RequestMapping("/findByJCList")
	public String findByJCList() throws Exception {
		list = new ArrayList<Outputwellproduction>();
		list = this.outputwellproductionService.fingWellByJCList();
		totals = list.size();
		String json = "";
		StringBuffer sbf = new StringBuffer();
		if (list.size() > 0) {
			sbf.append("{");
			sbf.append("\"totals\":" + totals).append(" ,\"list\":[");
			for (int i = 0; i < list.size(); i++) {
				sbf.append(" {\"jc\":\"" + list.get(i)).append("\"}");
				if (i != list.size() - 1) {
					sbf.append(",");
				}
			}
			sbf.append("]}");
		} else {
			json = "{list:[],totals:0}";
		}
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

	@RequestMapping("/findByJhhList")
	public String findByJhhList() throws Exception {
		list = new ArrayList<Outputwellproduction>();
		list = this.outputwellproductionService.fingWellByJhhList(jc);
		totals = list.size();
		String json = "";
		StringBuffer sbf = new StringBuffer();
		if (list.size() > 0) {
			sbf.append("{");
			sbf.append("\"totals\":" + totals).append(" ,\"list\":[");
			for (int i = 0; i < list.size(); i++) {
				sbf.append(" {\"jhh\":\"" + list.get(i)).append("\"}");
				if (i != list.size() - 1) {
					sbf.append(",");
				}
			}
			sbf.append("]}");
		} else {
			json = "{list:[],totals:0}";
		}
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

	@RequestMapping("/findByJhList")
	public String findByJhList() throws Exception {
		list = new ArrayList<Outputwellproduction>();
		list = this.outputwellproductionService.fingWellByJhList(jc, jhh);
		totals = list.size();
		String json = "";
		StringBuffer sbf = new StringBuffer();
		if (list.size() > 0) {
			sbf.append("{");
			sbf.append("\"totals\":" + totals).append(" ,\"list\":[");
			for (Object o : list) {
				Object[] obj = (Object[]) o;
				sbf.append(" {\"jhId\":" + obj[0]).append(",");
				sbf.append(" \"jh\":\"" + obj[1]).append("\"},");
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

	public OutputwellproductionService<Outputwellproduction> getOutputwellproductionService() {
		return outputwellproductionService;
	}

	@Resource
	public void setOutputwellproductionService(
			OutputwellproductionService<Outputwellproduction> outputwellproductionService) {
		this.outputwellproductionService = outputwellproductionService;
	}

	public Outputwellproduction getOutputwellproduction() {
		return outputwellproduction;
	}

	public void setOutputwellproduction(
			Outputwellproduction outputwellproduction) {
		this.outputwellproduction = outputwellproduction;
	}

	public List<Outputwellproduction> getList() {
		return list;
	}

	public void setList(List<Outputwellproduction> list) {
		this.list = list;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOutputwellproductionName() {
		return outputwellproductionName;
	}

	public void setOutputwellproductionName(String outputwellproductionName) {
		this.outputwellproductionName = outputwellproductionName;
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

	public String getJc() {
		return jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	public String getJhh() {
		return jhh;
	}

	public void setJhh(String jhh) {
		this.jhh = jhh;
	}

	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public String getParamsId() {
		return paramsId;
	}

	public void setParamsId(String paramsId) {
		this.paramsId = paramsId;
	}

}
