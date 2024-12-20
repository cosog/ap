package com.cosog.controller.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.User;
import com.cosog.model.data.DataitemsInfo;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.service.data.SystemdataInfoService;
import com.cosog.utils.Page;

/**
 * 系统数据字典数据项值表
 * 
 * @author 钱邓水
 * @data 2014-4-10
 */
@Controller
@RequestMapping("/dataitemsInfoController")
@Scope("prototype")
public class DataitemsInfoController extends BaseController {

	/**
	 * 需要VersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 实体类
	 */
	private DataitemsInfo dataitemsInfo;

	public DataitemsInfo getDataitemsInfo() {
		return dataitemsInfo;
	}

	public void setDataitemsInfo(DataitemsInfo dataitemsInfo) {
		this.dataitemsInfo = dataitemsInfo;
	}

	/**
	 * 导入service
	 */
	@Autowired
	private SystemdataInfoService systemdataInfoService;
	private DataitemsInfoService dataitemsInfoService;

	public DataitemsInfoService getDataitemsInfoService() {
		return dataitemsInfoService;
	}

	@Resource
	public void setDataitemsInfoService(DataitemsInfoService dataitemsInfoService) {
		this.dataitemsInfoService = dataitemsInfoService;
	}

	//添加绑定前缀 
	@InitBinder("dataitemsInfo")
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("dataitemsInfo.");
	}
	
	/**
	 * 查询数据项值信息
	 */
	@RequestMapping("/findDataitemsInfoByListId")
	public void findDataitemsInfoByListId() throws Exception {
		try {
			String getSysId = request.getParameter("sysId");
			String getdataTabId = request.getParameter("dataTabId");
			String getdataName = request.getParameter("dataName");
			this.pager = new Page("pagerForm", request);
			User userInfo = this.findCurrentUserInfo();
			List<DataitemsInfo> dataitemsInfoList = dataitemsInfoService.findDataitemsInfoPageListById(pager, userInfo, getSysId, getdataTabId, getdataName);

			response.setCharacterEncoding("utf-8");
			PrintWriter pw;
			String json=this.getArrayTojsonPage(dataitemsInfoList);
			try {
				pw = response.getWriter();
				pw.print(json);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 创建字典信息
	 */
	@RequestMapping("/addDataitemsInfo")
	public void addDataitemsInfo(@ModelAttribute DataitemsInfo dataitemsInfo) throws Exception {
		String jsonaddstr = "";
		if (null != dataitemsInfo && !"".equals(dataitemsInfo)) {
			User userInfo = this.findCurrentUserInfo();
			String sysId = request.getParameter("sysId");
			jsonaddstr = dataitemsInfoService.saveDataitemsInfo(dataitemsInfo, userInfo, sysId);
		} else {
			jsonaddstr = "{success:true,msg:false}";
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(jsonaddstr);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改字典信息
	 */
	@RequestMapping("/editDataitemsInfo")
	public void editDataitemsInfo(@ModelAttribute DataitemsInfo dataitemsInfo) throws Exception {
		String jsonaddstr = "";
		if (null != dataitemsInfo && !"".equals(dataitemsInfo)) {
			User userInfo = this.findCurrentUserInfo();
			jsonaddstr = dataitemsInfoService.editDataitemsInfo(dataitemsInfo, userInfo);
		} else {
			jsonaddstr = "{success:true,msg:false}";
		}
//		systemdataInfoService.initDataDictionaryPutInCache();
		response.setCharacterEncoding("utf-8");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(jsonaddstr);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除数据项信息
	 */
	@RequestMapping("/deleteDataitemsInfoById")
	public void deleteDataitemsInfoById() throws Exception {
		try {
			String jsondelete = "";
			String getDatatId = request.getParameter("paramsId");
//			systemdataInfoService.initDataDictionaryPutInCache();
			if (StringUtils.isNotBlank(getDatatId)) {
				boolean boo = dataitemsInfoService.deleteDataitemsInfoById(this.findCurrentUserInfo(), getDatatId);
				if (boo) {
					jsondelete = "{success:true,flag:true}";
				} else {
					jsondelete = "{success:true,flag:false}";
				}
			} else {
				jsondelete = "{success:false,flag:false}";
			}
			// 处理乱码。
			response.setCharacterEncoding("utf-8");
			// 输出json数据。
			PrintWriter pw;
			try {
				pw = response.getWriter();
				pw.print(jsondelete);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@RequestMapping("/initProductionDataDictionary")
	public String initProductionDataDictionary() throws Exception {
		String json = "";
		json=dataitemsInfoService.initProductionDataDictionary();
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
}
