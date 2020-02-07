package com.gao.controller.graphical;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.service.graphical.GraphicalOnclickService;
import com.gao.utils.ParamUtils;

/**
 * <p>图形点击查看Action类 </p>
 * 
 * li 2015-06-18
 * 
 */

@Controller
@RequestMapping("/graphicalOnclickManagerController")
@Scope("prototype")
public class GraphicalOnclickManagerController extends BaseController {

	private static final long serialVersionUID = 1L;
	private String type;
	private int id;
	@Autowired
	private GraphicalOnclickService<?> service;

	/**
	 * <p>
	 * 描述：查询实时地面功图数据信息
	 * </p>
	 * 
	 * @return
	 */
	@RequestMapping("/graphicalQuery")
	public String graphicalQuery()throws Exception{
		type = ParamUtils.getParameter(request, "type");
		id = Integer.parseInt(ParamUtils.getParameter(request, "id"));
		String json = "";
		if(type.equals("gtsj")){
			json = this.service.querySurfaceCardOnclick(id);
		}else if(type.equals("bgt")){
			json = this.service.queryPumpCardOnclick(id);
		}else if(type.equals("dlqx")){
			
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

	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

	public String gettype() {
		return type;
	}

	public void settype(String type) {
		this.type = type;
	}
}