package com.gao.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.WellTrajectory;
import com.gao.service.back.WellTrajectoryService;
import com.gao.service.base.CommonDataService;
import com.gao.utils.Constants;
import com.gao.utils.ParamUtils;
import com.google.gson.Gson;

/**<p>描述：井身轨迹数据处理Action</p>
 * @author gao 2014-06-04
 *
 */
@Controller
@RequestMapping("/wellTrajectoryController")
@Scope("prototype")
public class WellTrajectoryController extends BaseController {
	private static final long serialVersionUID = 1L;
	@Autowired
private CommonDataService sservice;
	private static Log log = LogFactory.getLog(WellTrajectoryController.class);
	private String jh;
	private Integer jbh;
	private WellTrajectory well;
	private List<WellTrajectory> list;
	@Autowired
	private WellTrajectoryService<WellTrajectory> service;
	private String orgCode;
	private String resCode;
	
	/**  <p>描述：获取井身轨迹下来菜单信息</p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryWellTrajectoryParams")
	public String queryWellTrajectoryParams() throws Exception {
		String orgCode = ParamUtils.getParameter(request, "orgCode");
		String resCode = ParamUtils.getParameter(request, "resCode");
		String type = ParamUtils.getParameter(request, "type");
		String json = this.service.queryWellTrajectoryParams(orgCode, resCode, type);
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
	/**<p>描述：井身轨迹数据添加</p>
	 * 
	 * @return null
	 * @throws IOException
	 * @argument object "no" ,"add"
	 */
	@RequestMapping("/doWellTrajectoryAdd")
	public String doWellTrajectoryAdd() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {

			well.setJsgj((String) (session.get("jsgj")));
			this.service.addOrUpdateWellTrajectoryByJbh(well, well.getJh());
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

	/**<p>描述：批量删除井身轨迹数据信息 </p>
	 * @return
	 */
	@RequestMapping("/doWellTrajectoryBulkDelete")
	public String doWellTrajectoryBulkDelete() {
		try {
			int line = ParamUtils.getIntParameter(request, "paramsId", 0);
			this.service.deleteWellTrajectory(((String) session.get("jsgj")),
					line, jbh);
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			String result = "{success:true,flag:true}";
			response.getWriter().print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**<p>编辑井身轨迹数据信息</p>
	 * 
	 * @return
	 */
	@RequestMapping("/doWellTrajectoryEdit")
	public String doWellTrajectoryEdit() {
		try {
			well.setJsgj((String) (session.get("jsgj")));
			this.service.updateWellTrajectory(well, well.getJlbh(),
					well.getJbh());
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
	/**<p>显示井身轨迹数据信息</p>
	 * 
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping("/showWellTracjectoryInfo")
	public String showWellTracjectoryInfo() throws SQLException {

		log.debug("scanWellTracjectoryInfo enter.....");
		List<WellTrajectory> jhs = this.service.getWellTrajectoryJhList();
		String defaultJh = "";
		if (jhs.size() > 0) {
			defaultJh = jhs.get(0).getJh();
		}
		if (!StringUtils.isNotBlank(jh)) {
			jh = defaultJh;
		}
		list = this.service.scanWellTrajectoryInfo(orgCode,resCode,jh);
		StringBuffer sb = new StringBuffer();
		for (WellTrajectory wtvo : list) {
			sb.append(wtvo.getJsgj() + ";");
		}
		//session.put("jsgj", sb.toString());
		String json = null;
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		// session.put("jsgj", list.get(index))
		PrintWriter pw;
		try {
        int total=100000;
        Gson g = new Gson();
			String columns=sservice.showTableHeadersColumns("wellTrajectory");
			StringBuffer strBuf = new StringBuffer();
			strBuf.append("{success:true,");
			strBuf.append("totals:"+total+",");
			strBuf.append("columns:" + columns + ",");
			String data = g.toJson(list);
			strBuf.append("list:" + data + "}");
			json=strBuf.toString();
			pw = response.getWriter();
			pw.print(json);
			log.debug(" scanWellTracjectoryInfo ***json==****" + json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/loadWellTrajectoryJh")
	public String loadWellTrajectoryJh() throws Exception {
		list = this.service.getWellTrajectoryJhList();
		String json = "";
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{");
		strBuf.append("\"totals\":" + 500).append(",\"list\":[");
		for (int i = 0; i < list.size(); i++) {
			log.debug("partmas jh====" + list.get(i));
			strBuf.append("{\"jh\":\"" + list.get(i).getJh()).append("\"}");
			if (i != list.size() - 1) {
				strBuf.append(",");
			}
		}
		strBuf.append("]}");
		json = strBuf.toString();
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("loadWellTrajectoryJh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}

	@RequestMapping("/loadWellJhList")
	public String loadWellJhList() throws Exception {
		list = this.service.getWellJhList();
		String json = "";
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{");
		strBuf.append("\"totals\":" + 500).append(",\"list\":[");
		for (int i = 0; i < list.size(); i++) {
			log.debug("partmas jh====" + list.get(i));
			strBuf.append("{\"jh\":\"" + list.get(i).getJh()).append("\"}");
			if (i != list.size() - 1) {
				strBuf.append(",");
			}
		}
		strBuf.append("]}");
		json = strBuf.toString();
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("loadWellTrajectoryJh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public List<WellTrajectory> getList() {
		return list;
	}

	public void setList(List<WellTrajectory> list) {
		this.list = list;
	}

	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public Integer getJbh() {
		return jbh;
	}

	public void setJbh(Integer jbh) {
		this.jbh = jbh;
	}

	public WellTrajectory getWell() {
		return well;
	}

	public void setWell(WellTrajectory well) {
		this.well = well;
	}
}
