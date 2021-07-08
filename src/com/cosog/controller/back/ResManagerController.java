package com.cosog.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.Res;
import com.cosog.model.ResParent;
import com.cosog.model.ResWellInfoBean;
import com.cosog.service.back.ResManagerService;
import com.cosog.service.base.CommonDataService;
import com.cosog.utils.Constants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.ResRecursion;
import com.google.gson.Gson;

/**
 * <P>
 * 描述：油气藏维护信息Action层
 * </p>
 * 
 * @author gao 2014-06-04
 * 
 */
@Controller
@RequestMapping("/resManagerController")
@Scope("prototype")
public class ResManagerController extends BaseController {

	private static Log log = LogFactory.getLog(ResManagerController.class);
	private static final long serialVersionUID = -281275682819237996L;
	@Autowired
	private ResManagerService<Res> resService;
	@Autowired
	private CommonDataService service;
	private List<Res> list;
	private Res res;


	//添加绑定前缀 
	@InitBinder("res")
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("res.");
	}

	/**
	 * @return
	 * @throws Exception
	 *             为后台井名信息模块提供油气藏信息
	 */
	@RequestMapping("/loadWellInfoRess")
	public String loadWellInfoRess() throws Exception {
		// TODO Auto-generated method stub
		List<?> list = this.resService.loadWellInfoRess(Res.class);
		log.debug("loadWellInfoRess list==" + list.size());
		ResWellInfoBean op = null;
		List<ResWellInfoBean> olist = new ArrayList<ResWellInfoBean>();
		for (int i = 0; i < list.size(); i++) {

			// 使用对象数组
			Object[] objArray = (Object[]) list.get(i);
			// 最后使用forEach迭代obj对象
			op = new ResWellInfoBean();
			op.setResCode(((String) objArray[0]));
			op.setResName(objArray[1].toString());
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

	/**<p>描述：创建油气藏维护的的树形表格</p>
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/constructResTreeGridTree")
	public String constructResTreeGridTree() throws IOException {
		String resName = ParamUtils.getParameter(request, "resName");
		list = this.resService.queryRess(Res.class, resName);
		String json = "";
		ResRecursion r = new ResRecursion();
		if (list != null) {
			for (Res org : list) {

				if (!r.hasParent(list, org)) {
					json = r.recursionResTreeFn(list, org);
				}
			}
		}
		json = r.modifyResStr(json);
		StringBuffer strBuf=new StringBuffer();
	    String columns	=this.service.showTableHeadersColumns("resManage");
		   strBuf.append("{success:true,");
		   strBuf.append("columns:"+columns);
		   strBuf.append(",children:"+json+"}");
		   
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(strBuf.toString());
		pw.flush();
		pw.close();
		return null;
	}

	/**<p>新增油气藏数据信息</p>
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/doResAdd")
	public String doResAdd(@ModelAttribute Res res) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			if (res.getResParent() == null) {
				res.setResParent(0);
			}
			this.resService.addRes(res);
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
	
	/**<p>批量删除油气藏数据信息</p>
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/doResBulkDelete")
	public String doResBulkDelete() {
		try {
			String ResIds = ParamUtils.getParameter(request, "paramsId");
			this.resService.bulkDelete(ResIds);
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			String result = "{success:true,flag:true}";
			response.getWriter().print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**<p>编辑油气藏数据信息</p>
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/doResEdit")
	public String doResEdit(@ModelAttribute Res res) {
		try {
			if (res.getResParent() == null) {
				res.setResParent(0);
			}
			this.resService.modifyRes(res);
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

	@RequestMapping("/findMaxNum")
	public String findMaxNum() {
		try {
			Integer resLevel = ParamUtils.getIntAttribute(request, "resLevel", 1);
			int maxId = this.resService.findMaxNum(resLevel).intValue();
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			String result = "{success:true,msg:true,maxId:" + (maxId) + "}";
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

	@RequestMapping("/findByPrimary")
	public String findByPrimary() {
		try {

			Integer parentId = ParamUtils.getIntAttribute(request, "parentId", 0);
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			String result = "{success:true,msg:true" + ",resLevel:" + this.resService.findByPrimary(parentId).get(0) + "}";
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

	@RequestMapping("/findResChildrenByparentId")
	public String findResChildrenByparentId() {
		try {
			Integer parentId = Integer.parseInt(request.getParameter("parentId"));
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			list = this.resService.findResChildrenByparentId(parentId);
			String resCode = "";
			log.debug("parentId==" + parentId);
			if (parentId != 0) {
				resCode = this.resService.findCurrentResCodeByparentId(parentId).get(0).getResCode();
			} else {
				resCode = "0" + (list.size() + 1);
			}

			String result = "{success:true,msg:true,childNodes:" + list.size() + ",resCode:'" + resCode + "'}";
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

	@RequestMapping("/loadResParentListInfo")
	public String loadResParentListInfo() throws Exception {
		// TODO Auto-generated method stub
		List<?> list = this.resService.loadParentRess(Res.class);
		log.debug("loadResListInfo list==" + list.size());
		ResParent op = null;
		List<ResParent> olist = new ArrayList<ResParent>();
		for (int i = 0; i < list.size(); i++) {

			// 使用对象数组
			Object[] objArray = (Object[]) list.get(i);
			// 最后使用forEach迭代obj对象
			op = new ResParent();
			op.setResId((Integer) objArray[0]);
			op.setResName(objArray[1].toString());
			olist.add(op);

		}
		Gson g = new Gson();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("totals", 100000);
		jsonMap.put("list", olist);
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
	public List<Res> getList() {
		return list;
	}

	public void setList(List<Res> list) {
		this.list = list;
	}

	public Res getRes() {
		return res;
	}

	public void setRes(Res res) {
		this.res = res;
	}

}
