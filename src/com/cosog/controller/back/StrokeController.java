package com.cosog.controller.back;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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

import com.cosog.controller.base.BaseController;
import com.cosog.model.Stroke;
import com.cosog.service.back.StrokeService;
import com.cosog.utils.Constants;
import com.cosog.utils.ParamUtils;

import oracle.sql.BLOB;

/**
 * 后台管理-数据配置－设备管理-抽油机2----抽油机冲程
 * 
 * @author ding
 * 
 */
@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/strokeController")
@Scope("prototype")
public class StrokeController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(StrokeController.class);
	@Autowired
	private StrokeService<Stroke> service;
	private Stroke stroke = new Stroke();
	private List<Stroke> list;
	private int limit = 3000;
	private int page;
	private int totals = 0;
	private String sccj = "";
	private String cyjxh = "";
	private String paramsId;

	@RequestMapping("/addstroke")
	public String addstroke() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			this.service.insertObject(stroke);
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

	@RequestMapping("/updatestroke")
	public String updatestroke() {
		String result = "";
		try {
			this.service.update(stroke);
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

	@RequestMapping("/deletestroke")
	public String deletestroke() throws IOException {
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
		log.debug(" strokeAction Enter ==");
		list = new ArrayList<Stroke>();
		int intpage = page == 0 ? 1 : page;
		int pageSize = limit;
		int offset = (intpage - 1) * pageSize;
		log.debug(" sccj=======" + sccj + ",cyjxh=========" + cyjxh);
		list = this.service.findAllLIst(offset, pageSize, sccj, cyjxh);
		String json = "";
		StringBuffer sbf = new StringBuffer();
		totals = this.service.rowCount(sccj, cyjxh);
		if (list.size() > 0) {
			sbf.append("{");
			sbf.append("\"totals\":" + totals).append(",\"list\":[");
			for (Object o : list) {
				Object[] obj = (Object[]) o;
				sbf.append(" {\"jlbh\":" + obj[0]).append(",");
				sbf.append(" \"cyjbh\":" + obj[1]).append(",");
				sbf.append(" \"sccj\":\"" + obj[2]).append("\",");
				sbf.append(" \"cyjxh\":\"" + obj[3]).append("\",");
				sbf.append(" \"cc\":" + obj[4]).append(",");
				sbf.append(" \"qbkj\":" + obj[5]).append(",");
				sbf.append(" \"wzjnjys\":\"" + obj[6]).append("\"},");
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

	// 把数据库中blob类型转换成String类型
	@RequestMapping("/convertBlobToString")
	public String convertBlobToString(BLOB blob) {

		String result = "";
		try {
			ByteArrayInputStream msgContent = (ByteArrayInputStream) blob
					.getBinaryStream();
			byte[] byte_data = new byte[msgContent.available()];
			msgContent.read(byte_data, 0, byte_data.length);
			result = new String(byte_data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("/queryStrokeParams")
	public String queryStrokeParams() throws Exception {
		String sccj = ParamUtils.getParameter(request, "sccj");
		String cyjxh = ParamUtils.getParameter(request, "cyjxh");
		String type = ParamUtils.getParameter(request, "type");
		sccj=new String(sccj.getBytes("iso-8859-1"),"utf-8");
		String json = this.service.queryStrokeParams(sccj, cyjxh, type);
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
	
	@RequestMapping("/queryStrokeWinParams")
	public String queryStrokeWinParams() throws Exception {
		String sccj = ParamUtils.getParameter(request, "sccj");
		String cyjxh = ParamUtils.getParameter(request, "cyjxh");
		String type = ParamUtils.getParameter(request, "type");
		sccj=new String(sccj.getBytes("iso-8859-1"),"utf-8");
		String json = this.service.queryStrokeWinParams(sccj, cyjxh, type);
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
		list = new ArrayList<Stroke>();
		list = this.service.findByLiIdst(sccj);
		totals = list.size();
		String json = "";
		StringBuffer sbf = new StringBuffer();
		if (list.size() > 0) {
			sbf.append("{");
			sbf.append("\"totals\":" + totals).append(",\"list\":[");
			for (Object o : list) {
				Object[] obj = (Object[]) o;
				sbf.append(" {\"sccj\":\"" + obj[0]).append("\",");
				sbf.append(" \"cyjxh\":\"" + obj[1]).append("\",");
				sbf.append(" \"cyjbh\":" + obj[2]).append("},");
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

	@RequestMapping("/findBysccj")
	public String findBysccj() throws Exception {
		list = new ArrayList<Stroke>();
		list = this.service.findBysccj();
		totals = list.size();
		String json = "";
		StringBuffer sbf = new StringBuffer();
		sbf.append("{");
		sbf.append("\"totals\":" + totals).append(",\"list\":[");
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

	public Stroke getStroke() {
		return stroke;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	public List<Stroke> getList() {
		return list;
	}

	public void setList(List<Stroke> list) {
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
