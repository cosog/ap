package com.cosog.controller.sceneModelController;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cosog.controller.base.BaseController;
import com.cosog.model.User;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.sceneModelController.SceneModelService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Constants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

@Controller
@RequestMapping("/sceneModelController")
@Scope("prototype")
public class SceneModelController extends BaseController {
	private static Log log = LogFactory.getLog(SceneModelController.class);
	private static final long serialVersionUID = -281275682819237996L;
	@Autowired
	private SceneModelService<?> sceneModelService;
	@Autowired
	private CommonDataService service;
	/**
	 * 场景图片固定目录（WebRoot 下的相对路径）
	 */
	private static final String SCENE_IMAGE_DIR = "/oem/cnpc/sceneImage";
	
	@RequestMapping("/getSceneModelList")
	public String getSceneModelList() throws IOException, SQLException {
		HttpSession session=request.getSession();
		String orgId=ParamUtils.getParameter(request, "orgId");
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
			}
		}
		
		String json = sceneModelService.getSceneModelList(orgId,user);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceList")
	public String getDeviceList() throws IOException, SQLException {
		HttpSession session=request.getSession();
		String orgId=ParamUtils.getParameter(request, "orgId");
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserOrgid();
			}
		}
		
		String json = sceneModelService.getDeviceList(orgId,user);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceFields")
	public String getDeviceFields() throws IOException, SQLException {
		HttpSession session=request.getSession();
		String deviceId=ParamUtils.getParameter(request, "deviceId");
		String calculateType=ParamUtils.getParameter(request, "calculateType");
		String protocolCode=ParamUtils.getParameter(request, "protocolCode");
		User user = (User) session.getAttribute("userLogin");
		String json = sceneModelService.getDeviceFields(deviceId,calculateType,protocolCode,user);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * 获取场景图片列表（固定目录，无路径参数）
	 */
	@RequestMapping("/getSceneImageList")
	public String getSceneImageList() throws IOException {
	    Map<String,Object> result = new HashMap<>();
	    List<Map<String,Object>> dataList = new ArrayList<>();

	    try {
	        String realPath = request.getSession().getServletContext().getRealPath(SCENE_IMAGE_DIR);
	        String contextPath = request.getContextPath();
	        File dir = new File(realPath);

	        if (dir.exists() && dir.isDirectory()) {
	            File[] files = dir.listFiles();
	            if (files != null) {
	                // 按文件名排序，图片先展示
	                Arrays.sort(files, new Comparator<File>() {
	                    public int compare(File a, File b) {
	                        return a.getName().compareToIgnoreCase(b.getName());
	                    }
	                });
	                for (File f : files) {
	                    if (f.isFile() && isImageFile(f.getName())) {
	                        Map<String,Object> item = new HashMap<>();
	                        item.put("name", f.getName());
	                        item.put("url", contextPath + SCENE_IMAGE_DIR + "/" + f.getName());
	                        item.put("size", f.length());
	                        item.put("lastModified", f.lastModified());
	                        dataList.add(item);
	                    }
	                }
	            }
	        }

	        result.put("success", true);
	        result.put("data", dataList);
	    } catch (Exception e) {
	        log.error("获取场景图片列表失败", e);
	        result.put("success", false);
	        result.put("message", e.getMessage());
	        result.put("data", dataList);
	    }

	    response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter pw = response.getWriter();
	    pw.print(new Gson().toJson(result));
	    pw.flush();
	    pw.close();
	    return null;
	}

	/**
	 * 上传场景图片到固定目录
	 */
	@RequestMapping("/uploadSceneImage")
	public String uploadSceneImage(@RequestParam("file") MultipartFile file) throws IOException {
	    Map<String,Object> result = new HashMap<>();

	    try {
	        if (file == null || file.isEmpty()) {
	            result.put("success", false);
	            result.put("message", "文件为空");
	        } else {
	            String realPath = request.getSession().getServletContext().getRealPath(SCENE_IMAGE_DIR);
	            String contextPath = request.getContextPath();
	            File dir = new File(realPath);
	            if (!dir.exists()) {
	                dir.mkdirs();
	            }

	            // 处理原始文件名，去掉路径部分，防止目录穿越
	            String originalName = file.getOriginalFilename();
	            if (originalName == null) originalName = "upload";
	            originalName = originalName.replace("\\", "/");
	            int slashIdx = originalName.lastIndexOf("/");
	            if (slashIdx >= 0) originalName = originalName.substring(slashIdx + 1);

	            // 校验扩展名
	            if (!isImageFile(originalName)) {
	                result.put("success", false);
	                result.put("message", "只支持图片格式");
	            } else {
	                String fileName = originalName;
	                File dest = new File(dir, fileName);
	                // 重名时追加时间戳
	                if (dest.exists()) {
	                    int dotIdx = originalName.lastIndexOf(".");
	                    if (dotIdx > 0) {
	                        fileName = originalName.substring(0, dotIdx)
	                                 + "_" + System.currentTimeMillis()
	                                 + originalName.substring(dotIdx);
	                    } else {
	                        fileName = originalName + "_" + System.currentTimeMillis();
	                    }
	                    dest = new File(dir, fileName);
	                }

	                file.transferTo(dest);

	                result.put("success", true);
	                result.put("imageUrl", contextPath + SCENE_IMAGE_DIR + "/" + fileName);
	                result.put("fileName", fileName);
	            }
	        }
	    } catch (Exception e) {
	        log.error("上传场景图片失败", e);
	        result.put("success", false);
	        result.put("message", e.getMessage());
	    }

	    response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter pw = response.getWriter();
	    pw.print(new Gson().toJson(result));
	    pw.flush();
	    pw.close();
	    return null;
	}
	
	/**
	 * 保存单个场景的标注配置
	 * 请求体：{ "id": 1, "config": "{...}" }
	 */
	@RequestMapping("/saveSceneModel")
	public String saveSceneModel() throws IOException, SQLException {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session=request.getSession();
		String id=ParamUtils.getParameter(request, "id");
		String config=ParamUtils.getParameter(request, "config");
		User user = (User) session.getAttribute("userLogin");
		String language=user!=null?user.getLanguageName():"";
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		boolean success = sceneModelService.saveSceneModel(Integer.parseInt(id), config);
		result.put("success", success);
        result.put("message", success ? languageResourceMap.get("savedSuccessfully") : languageResourceMap.get("saveFailed"));
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(new Gson().toJson(result));
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * 获取单个场景的 config（点击场景时加载标注用）
	 * 请求参数：id
	 * 返回：{ success:true, config:"...", modelId:... }
	 */
	@RequestMapping("/getSceneModelConfig")
	public String getSceneModelConfig() throws IOException, SQLException {
	    Map<String, Object> result = new HashMap<String, Object>();

	    try {
	        String id = ParamUtils.getParameter(request, "id");

	        if (!StringManagerUtils.isNotNull(id)) {
	            result.put("success", false);
	            result.put("message", "缺少场景 id");
	        } else {
	            Map<String, Object> data = sceneModelService.getSceneModelConfig(Integer.parseInt(id));
	            if (data == null) {
	                result.put("success", false);
	                result.put("message", "场景不存在");
	            } else {
	                result.put("success", true);
	                result.put("modelId", data.get("id"));
	                result.put("config", data.get("config"));
	            }
	        }
	    } catch (Exception e) {
	        log.error("获取场景配置失败", e);
	        result.put("success", false);
	        result.put("message", e.getMessage());
	    }

	    response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter pw = response.getWriter();
	    pw.print(new Gson().toJson(result));
	    pw.flush();
	    pw.close();
	    return null;
	}

	/**
	 * 判断是否是图片文件
	 */
	private boolean isImageFile(String name) {
	    if (name == null) return false;
	    String lower = name.toLowerCase();
	    return lower.endsWith(".png") || lower.endsWith(".jpg")
	        || lower.endsWith(".jpeg") || lower.endsWith(".gif")
	        || lower.endsWith(".bmp") || lower.endsWith(".svg")
	        || lower.endsWith(".webp") || lower.endsWith(".ico");
	}
}
