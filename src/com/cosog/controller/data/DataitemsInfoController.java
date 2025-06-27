package com.cosog.controller.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.cosog.utils.Constants;
import com.cosog.utils.Page;
import com.cosog.utils.ParamUtils;

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
	 * 查询数据项值信息
	 */
	@RequestMapping("/getDataDictionaryItemList")
	public void getDataDictionaryItemList() throws Exception {
		try {
			String dictionaryId = request.getParameter("dictionaryId");
			String type = request.getParameter("type");
			String value = request.getParameter("value");
			String deviceType = request.getParameter("deviceType");
			this.pager = new Page("pagerForm", request);
			User userInfo = this.findCurrentUserInfo();
			String json = dataitemsInfoService.getDataDictionaryItemList(pager, userInfo, dictionaryId, type, value,deviceType);
			response.setCharacterEncoding("utf-8");
			PrintWriter pw;
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
	
	@RequestMapping("/updateDataDictionaryItemInfo")
	public String updateDataDictionaryItemInfo() throws IOException {
		String result = "{success:true,flag:true}";
		try {
			String dataitemid = ParamUtils.getParameter(request, "dataitemid");
			String name = ParamUtils.getParameter(request, "name");
			String code = ParamUtils.getParameter(request, "code");
			String sorts = ParamUtils.getParameter(request, "sorts");
			String datavalue = ParamUtils.getParameter(request, "datavalue");
			String status = ParamUtils.getParameter(request, "status");
			String status_cn = ParamUtils.getParameter(request, "status_cn");
			String status_en = ParamUtils.getParameter(request, "status_en");
			String status_ru = ParamUtils.getParameter(request, "status_ru");
			User userInfo = this.findCurrentUserInfo();
			int r=this.dataitemsInfoService.updateDataDictionaryItemInfo(dataitemid,name,code,sorts,datavalue,status,status_cn,status_en,status_ru,userInfo.getLanguageName());
			if(r==1){
				result = "{success:true,flag:true}";
			}else if(r==2){
				result = "{success:true,flag:false}";
			}else{
				result = "{success:false,flag:false}";
			}
		} catch (Exception e) {
			result = "{success:false,flag:false}";
			e.printStackTrace();
		}
		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		response.getWriter().print(result);
		pw.flush();
		pw.close();
		return null;
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
			String configItemName = request.getParameter("configItemName");
			String itemColumn = request.getParameter("itemColumn");
			dataitemsInfo.setConfigItemName(configItemName);
			dataitemsInfo.setCode(itemColumn);
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
	
	@RequestMapping("/updateDataitemsInfo")
	public void updateDataitemsInfo(@ModelAttribute DataitemsInfo dataitemsInfo) throws Exception {
		String jsonaddstr = "";
		if (null != dataitemsInfo && !"".equals(dataitemsInfo)) {
			User userInfo = this.findCurrentUserInfo();
			String sysId = request.getParameter("sysId");
			String configItemName = request.getParameter("configItemName");
			String itemColumn = request.getParameter("itemColumn");
			String dictItemDataItemId = request.getParameter("dictItemDataItemId");
			dataitemsInfo.setDataitemid(dictItemDataItemId);
			dataitemsInfo.setConfigItemName(configItemName);
			dataitemsInfo.setCode(itemColumn);
			jsonaddstr = dataitemsInfoService.updateDataitemsInfo(dataitemsInfo, userInfo, sysId);
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
	
	@RequestMapping("/getAddInfoOrDriverConfigItemList")
	public String getAddInfoOrDriverConfigItemList() throws IOException {
		String dictDataSource = ParamUtils.getParameter(request, "dictDataSource");
		String dataSource = ParamUtils.getParameter(request, "dataSource");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String json = this.dataitemsInfoService.getAddInfoOrDriverConfigItemList(dictDataSource,dataSource,user);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
}
