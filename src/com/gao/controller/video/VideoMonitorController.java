package com.gao.controller.video;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.User;
import com.gao.model.Video;
import com.gao.service.video.VideoMonitorService;
import com.gao.utils.PageHandler;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;

@Controller
@RequestMapping("/videoMonitorController")
@Scope("prototype")
public class VideoMonitorController extends BaseController {

	private static Log log = LogFactory.getLog(VideoMonitorController.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer currentPage = 1;
	private Integer offset = 1;
	private PageHandler pageHandler = null;
	private Integer pageSize;
	private String wellStation;
	private String items;

	private Integer orgId;

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getWellStation() {
		return wellStation;
	}

	public void setWellStation(String wellStation) {
		this.wellStation = wellStation;
	}

	private Integer recordCount;

	private VideoMonitorService<Video> videoMonitorService;

	private List<Video> videos;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public Integer getOffset() {
		return offset;
	}

	public PageHandler getPageHandler() {
		return pageHandler;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public VideoMonitorService<Video> getVideoMonitorService() {
		return videoMonitorService;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public void setPageHandler(PageHandler pageHandler) {
		this.pageHandler = pageHandler;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	@Resource
	public void setVideoMonitorService(
			VideoMonitorService<Video> videoMonitorService) {
		this.videoMonitorService = videoMonitorService;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	@Override
	@RequestMapping("/execute")
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		// pageSize = 4;
		pageSize = ParamUtils.getIntParameter(request, "pageSize", 6);
		Integer size = (Integer) session.get("pageSize");
		if (size == null) {
			size = 6;
		}
		pageSize = pageSize == 0 ? size : pageSize;
		wellStation = ParamUtils.getParameter(request, "wellStation");
		items = ParamUtils.getParameter(request, "items");
		if (items != "" || !items.isEmpty()) {
			items = items.substring(0, items.lastIndexOf(","));
			items = StringManagerUtils.spitData(items);
		}
		wellStation = new String(wellStation.getBytes("ISO-8859-1"), "UTF-8");
		if (wellStation.equalsIgnoreCase("�����뾮����ѯ...")) {
			wellStation = "";
		}
		// wellStation = wellStation == "�����뾮����ѯ..." ? "" : wellStation;
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if (orgId == null) {
			if (user != null) {
				orgId = user.getUserOrgid();
			}
		}
		orgId = ParamUtils.getIntParameter(request, "orgId", orgId);
		log.debug("wellStation==" + wellStation + "  items==" + items);
		log.debug("orgId==" + orgId);
		currentPage = ParamUtils.getIntParameter(request, "currentPage", 1);
		offset = (currentPage - 1) * pageSize;
		recordCount = this.videoMonitorService.getTotalRows(wellStation, items,
				orgId);
		pageHandler = new PageHandler(currentPage, recordCount, pageSize);
		request.setAttribute("pageHandler", pageHandler);
		// request.setAttribute("pageSize", pageSize);
//		session.put("pageSize", pageSize);
//		session.put("orgId", orgId);
		videos = this.videoMonitorService.queryVideos(wellStation, items,
				orgId, offset, pageSize, Video.class);
		request.setAttribute("videos", videos);
		log.debug("videos==" + videos.size());
		return SUCCESS;
	}

}
