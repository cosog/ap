package com.cosog.controller.sceneModelController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cosog.controller.base.BaseController;
import com.cosog.model.DeviceTabManager;
import com.cosog.model.SceneModel;
import com.cosog.model.User;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.sceneModelController.SceneModelService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.Constants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/sceneModelController")
@Scope("prototype")
public class SceneModelController extends BaseController {
    private static Log log = LogFactory.getLog(SceneModelController.class);
    private static final long serialVersionUID = -281275682819237996L;

    @Autowired
    private SceneModelService<SceneModel> sceneModelService;

    @Autowired
    private CommonDataService service;
    
    @InitBinder("sceneModel")
	public void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("sceneModel.");
	}

    // ============================================================
    // 场景列表
    // ============================================================
    @RequestMapping("/getSceneModelList")
    public String getSceneModelList() throws IOException, SQLException {
        HttpSession session = request.getSession();
        String orgId = ParamUtils.getParameter(request, "orgId");
        User user = (User) session.getAttribute("userLogin");
        String language = "";
        if (user != null) {
            language = "" + user.getLanguageName();
        }
        if (!StringManagerUtils.isNotNull(orgId)) {
            if (user != null) {
                orgId = "" + user.getUserOrgIds();
            }
        }

        String json = sceneModelService.getSceneModelList(orgId, user);
        response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter pw = response.getWriter();
        pw.print(json);
        pw.flush();
        pw.close();
        return null;
    }
    
    @RequestMapping("/addSceneModel")
	public String addSceneModel(@ModelAttribute SceneModel sceneModel) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			sceneModel.setId(1);
			
			this.sceneModelService.addSceneModel(sceneModel);
			result = "{\"success\":true,\"msg\":true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			out.print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{\"success\":false,\"msg\":false}";
			out.print(result);
		}
		return null;
	}

    // ============================================================
    // 设备列表
    // ============================================================
    @RequestMapping("/getDeviceList")
    public String getDeviceList() throws IOException, SQLException {
        HttpSession session = request.getSession();
        String orgId = ParamUtils.getParameter(request, "orgId");
        User user = (User) session.getAttribute("userLogin");
        String language = "";
        if (user != null) {
            language = "" + user.getLanguageName();
        }
        if (!StringManagerUtils.isNotNull(orgId)) {
            if (user != null) {
                orgId = "" + user.getUserOrgid();
            }
        }

        String json = sceneModelService.getDeviceList(orgId, user);
        response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter pw = response.getWriter();
        pw.print(json);
        pw.flush();
        pw.close();
        return null;
    }

    // ============================================================
    // 设备字段
    // ============================================================
    @RequestMapping("/getDeviceFields")
    public String getDeviceFields() throws IOException, SQLException {
        HttpSession session = request.getSession();
        String deviceId = ParamUtils.getParameter(request, "deviceId");
        String calculateType = ParamUtils.getParameter(request, "calculateType");
        String protocolCode = ParamUtils.getParameter(request, "protocolCode");
        User user = (User) session.getAttribute("userLogin");
        String json = sceneModelService.getDeviceFields(deviceId, calculateType, protocolCode, user);
        response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter pw = response.getWriter();
        pw.print(json);
        pw.flush();
        pw.close();
        return null;
    }

    // ============================================================
    // 场景图片列表（双层编码 URL）
    // ============================================================
    @RequestMapping("/getSceneImageList")
    public String getSceneImageList() throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

        try {
        	String sceneImagePath = Config.getInstance().configFile.getAp().getOem().getSceneImagePath();
            String realPath = request.getSession().getServletContext().getRealPath(sceneImagePath);
            String contextPath = request.getContextPath();
            File dir = new File(realPath);

            if (dir.exists() && dir.isDirectory()) {
                File[] files = dir.listFiles();
                if (files != null) {
                    Arrays.sort(files, new Comparator<File>() {
                        public int compare(File a, File b) {
                            return a.getName().compareToIgnoreCase(b.getName());
                        }
                    });
                    for (File f : files) {
                        if (f.isFile() && isImageFile(f.getName())) {
                            Map<String, Object> item = new HashMap<String, Object>();
                            item.put("name", f.getName());

                            // ★ 双层编码 + Controller 转发
                            String encoded1 = URLEncoder.encode(f.getName(), "UTF-8");
                            String encoded2 = URLEncoder.encode(encoded1, "UTF-8");
                            item.put("url", contextPath
                                    + "/sceneModelController/getSceneImage?name=" + encoded2);

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

    // ============================================================
    // 上传场景图片（保留中文名，返回双层编码 URL）
    // ============================================================
    @RequestMapping("/uploadSceneImage")
    public String uploadSceneImage(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            if (file == null || file.isEmpty()) {
                result.put("success", false);
                result.put("message", "文件为空");
            } else {
            	String sceneImagePath = Config.getInstance().configFile.getAp().getOem().getSceneImagePath();
                String realPath = request.getSession().getServletContext().getRealPath(sceneImagePath);
                String contextPath = request.getContextPath();
                File dir = new File(realPath);
                if (!dir.exists()) dir.mkdirs();

                String originalName = file.getOriginalFilename();
                if (originalName == null) originalName = "upload";
                originalName = originalName.replace("\\", "/");
                int slashIdx = originalName.lastIndexOf("/");
                if (slashIdx >= 0) originalName = originalName.substring(slashIdx + 1);

                if (!isImageFile(originalName)) {
                    result.put("success", false);
                    result.put("message", "只支持图片格式");
                } else {
                    // 保留中文名，重名时加时间戳
                    String fileName = originalName;
                    File dest = new File(dir, fileName);
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

                    // ★ 双层编码 + Controller 转发
                    String encoded1 = URLEncoder.encode(fileName, "UTF-8");
                    String encoded2 = URLEncoder.encode(encoded1, "UTF-8");
                    String imageUrl = contextPath
                            + "/sceneModelController/getSceneImage?name=" + encoded2;

                    result.put("success", true);
                    result.put("imageUrl", imageUrl);
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
    
    // ============================================================
    // 删除场景图片
    // ============================================================
    @RequestMapping("/deleteSceneImage")
    public String deleteSceneImage() throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();
        String name = ParamUtils.getParameter(request, "name");

        if (!StringManagerUtils.isNotNull(name)) {
            result.put("success", false);
            result.put("message", "缺少图片名称");
        } else {
            // 安全检查：防目录穿越
            if (name.contains("..") || name.contains("/") || name.contains("\\")) {
                result.put("success", false);
                result.put("message", "非法的文件名");
            } else if ("__blank__.svg".equals(name)) {
                // 保护系统自带的空白背景图不被误删
                result.put("success", false);
                result.put("message", "系统内置空白背景图不能删除");
            } else {
                try {
                    String sceneImagePath = Config.getInstance().configFile.getAp().getOem().getSceneImagePath();
                    String realPath = request.getSession().getServletContext().getRealPath(sceneImagePath);
                    File file = new File(realPath, name);

                    if (file.exists() && file.isFile()) {
                        if (file.delete()) {
                            result.put("success", true);
                            result.put("message", "删除成功");
                        } else {
                            result.put("success", false);
                            result.put("message", "文件删除失败，可能被服务器占用");
                        }
                    } else {
                        result.put("success", false);
                        result.put("message", "文件不存在或已被删除");
                    }
                } catch (Exception e) {
                    log.error("删除场景图片失败", e);
                    result.put("success", false);
                    result.put("message", e.getMessage());
                }
            }
        }

        response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter pw = response.getWriter();
        pw.print(new Gson().toJson(result));
        pw.flush();
        pw.close();
        return null;
    }

    // ============================================================
    // 以流的形式返回场景图片（解决中文文件名 404）
    // ============================================================
    @RequestMapping("/getSceneImage")
    public void getSceneImage() throws IOException {
        String rawParam = ParamUtils.getParameter(request, "name");

        if (!StringManagerUtils.isNotNull(rawParam)) {
            response.sendError(404);
            return;
        }

        // ★ 双层解码
        String name;
        try {
            name = URLDecoder.decode(rawParam, "UTF-8");
        } catch (Exception e) {
            name = rawParam;
        }

        // ★★★ 特殊：__blank__.svg 返回空白 SVG（供前端清空背景用，避免 data URI 导致爆栈）★★★
        if ("__blank__.svg".equals(name)) {
            response.setContentType("image/svg+xml");
            response.setHeader("Cache-Control", "no-cache");   // ★ 关键：别缓存
            response.getOutputStream().write(
                ("<svg xmlns=\"http://www.w3.org/2000/svg\" " +
                 "width=\"800\" height=\"600\">" +
                 "<rect width=\"100%\" height=\"100%\" fill=\"#f8f9fa\"/>" +
                 "</svg>").getBytes("UTF-8"));
            return;
        }

        // 安全检查：防目录穿越
        if (name.contains("..") || name.contains("/") || name.contains("\\")) {
            response.sendError(403);
            return;
        }

        String sceneImagePath = Config.getInstance().configFile.getAp().getOem().getSceneImagePath();
        String realPath = request.getSession().getServletContext().getRealPath(sceneImagePath);
        File file = new File(realPath, name);

        if (!file.exists() || !file.isFile()) {
            log.warn("图片不存在: " + file.getAbsolutePath());
            response.sendError(404);
            return;
        }

        // Content-Type
        String lower = name.toLowerCase();
        String contentType = "application/octet-stream";
        if (lower.endsWith(".png"))                                contentType = "image/png";
        else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) contentType = "image/jpeg";
        else if (lower.endsWith(".gif"))                           contentType = "image/gif";
        else if (lower.endsWith(".bmp"))                           contentType = "image/bmp";
        else if (lower.endsWith(".svg"))                           contentType = "image/svg+xml";
        else if (lower.endsWith(".webp"))                          contentType = "image/webp";
        else if (lower.endsWith(".ico"))                           contentType = "image/x-icon";

        response.setContentType(contentType);
        response.setHeader("Cache-Control", "max-age=86400");
        response.setContentLengthLong(file.length());

        FileInputStream fis = null;
        OutputStream os = null;
        try {
            fis = new FileInputStream(file);
            os = response.getOutputStream();
            byte[] buf = new byte[8192];
            int len;
            while ((len = fis.read(buf)) > 0) {
                os.write(buf, 0, len);
            }
            os.flush();
        } catch (Exception e) {
            log.error("读取图片流失败", e);
        } finally {
            if (fis != null) try { fis.close(); } catch (Exception e) {}
            if (os != null)  try { os.close(); }  catch (Exception e) {}
        }
    }

    // ============================================================
    // 保存单个场景的标注配置
    // ============================================================
    @RequestMapping("/saveSceneModel")
    public String saveSceneModel() throws IOException, SQLException {
        Map<String, Object> result = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        String id = ParamUtils.getParameter(request, "id");
        String config = ParamUtils.getParameter(request, "config");
        User user = (User) session.getAttribute("userLogin");
        String language = user != null ? user.getLanguageName() : "";
        Map<String, String> languageResourceMap = MemoryDataManagerTask.getLanguageResource(language);
        boolean success = sceneModelService.saveSceneModel(Integer.parseInt(id), config);
        result.put("success", success);
        result.put("message", success
                ? languageResourceMap.get("savedSuccessfully")
                : languageResourceMap.get("saveFailed"));

        response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter pw = response.getWriter();
        pw.print(new Gson().toJson(result));
        pw.flush();
        pw.close();
        return null;
    }

    // ============================================================
    // 获取单个场景的 config
    // ============================================================
    @RequestMapping("/getSceneModelConfig")
    public String getSceneModelConfig() throws IOException, SQLException {
        Map<String, Object> result = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userLogin");
        try {
            String id = ParamUtils.getParameter(request, "id");

            if (!StringManagerUtils.isNotNull(id)) {
                result.put("success", false);
                result.put("message", "缺少场景 id");
            } else {
                Map<String, Object> data = sceneModelService.getSceneModelConfig(Integer.parseInt(id),user != null ? user.getLanguageName() : "");
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
    
    @RequestMapping("/getSceneModelConfigByDeviceId")
    public String getSceneModelConfigByDeviceId() throws IOException, SQLException {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            String deviceId = ParamUtils.getParameter(request, "deviceId");

            if (!StringManagerUtils.isNotNull(deviceId)) {
                result.put("success", false);
                result.put("message", "缺少设备 id");
            } else {
                Map<String, Object> data = sceneModelService.getSceneModelConfigByDeviceId(
                        Integer.parseInt(deviceId));
                if (data == null) {
                    // 设备不存在 → 返回默认 config
                    Map<String, Object> defaultCfg = new HashMap<String, Object>();
                    defaultCfg.put("imageUrl", "");
                    defaultCfg.put("shapes", new ArrayList<Object>());
                    result.put("success", true);
                    result.put("config", new Gson().toJson(defaultCfg));
                } else {
                    result.put("success", true);
                    result.put("modelId", data.get("id"));
                    result.put("config", data.get("config"));
                }
            }
        } catch (Exception e) {
            log.error("按设备获取场景配置失败", e);
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
    
    @RequestMapping("/saveSceneModelInfo")
	public String saveSceneModelInfo() throws Exception {
		String result ="{\"success\":true,\"msg\":false}";
		HttpSession session=request.getSession();
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		String data = ParamUtils.getParameter(request, "data");
		
		type = new TypeToken<List<SceneModel>>() {}.getType();
		List<SceneModel> list=gson.fromJson(data, type);
		
		
		if(list!=null){
			for(SceneModel sceneModel:list){
				this.sceneModelService.saveSceneModelInfo(sceneModel);
			}
		}
		
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(result);
		log.debug("saveDeviceTypeMaintenanceData json==" + result);
		pw.flush();
		pw.close();
		return null;
	}
    
    @RequestMapping("/deleteSceneModel")
	public String deleteSceneModel() throws Exception {
		String result ="{\"success\":true,\"msg\":false}";
		HttpSession session=request.getSession();
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		String ids = ParamUtils.getParameter(request, "ids");
		
		this.sceneModelService.deleteSceneModel(ids);
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(result);
		log.debug("saveDeviceTypeMaintenanceData json==" + result);
		pw.flush();
		pw.close();
		return null;
	}

    // ============================================================
    // 工具：判断是否图片文件
    // ============================================================
    private boolean isImageFile(String name) {
        if (name == null) return false;
        String lower = name.toLowerCase();
        return lower.endsWith(".png") || lower.endsWith(".jpg")
                || lower.endsWith(".jpeg") || lower.endsWith(".gif")
                || lower.endsWith(".bmp") || lower.endsWith(".svg")
                || lower.endsWith(".webp") || lower.endsWith(".ico");
    }
}