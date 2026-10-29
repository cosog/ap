package com.cosog.controller.back;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cosog.controller.base.BaseController;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.User;
import com.cosog.model.drive.ModbusDriverSaveData;
import com.cosog.service.back.AlarmSetManagerService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.Constants;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.Page;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**<p>描述：后台管理-报警设置Action</p>
 * 
 * @author gao 2014-06-06
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/alarmSetManagerController")
@Scope("prototype")
public class AlarmSetManagerController extends BaseController {
	private static Log log = LogFactory.getLog(AlarmSetManagerController.class);
	private static final long serialVersionUID = -281275682819237996L;
//	private DistreteAlarmLimit limit;

	@Autowired
	private AlarmSetManagerService<?> alarmSetManagerService;
	@Autowired
	private CommonDataService commonDataService;
    
	private String orgId;
	private String jh;
	
	/**
	 * 获取报警级别颜色方法
	 * 
	 * @return null
	 * @throws Exception
	 * @author zhao 2016-3-8
	 * 
	 */
	@RequestMapping("/getAlarmLevelColor")
	public String getAlarmLevelColor() throws Exception {
		String json=alarmSetManagerService.getAlarmLevelColor();
//		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * 设置报警级别对应颜色方法
	 * 
	 * @return null
	 * @throws Exception
	 * @author zhao 2016-3-8
	 * 
	 */
	@RequestMapping("/setAlarmLevelColor")
	public String setAlarmLevelColor() throws Exception {
		Gson gson = new Gson();
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		AlarmShowStyle alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		if(alarmShowStyle==null){
			MemoryDataManagerTask.initAlarmStyle();
			alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		}
		
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<AlarmShowStyle>() {}.getType();
		AlarmShowStyle alarmShowStyleSaveData=gson.fromJson(data, type);
		String json="";
		try {
			if(alarmShowStyleSaveData!=null){
				
			}
			alarmSetManagerService.setAlarmLevelColor(alarmShowStyleSaveData);
			MemoryDataManagerTask.initAlarmStyle();
			json="{\"success\":true,\"msg\":true}";
		} catch (Exception e) {
			json = "{\"success\":true,\"msg\":false}";
			e.printStackTrace();
		}
//		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/setAlarmSound")
	public String setAlarmSound() throws Exception {
	    Map<String, Object> resp = new HashMap<String, Object>();
	    try {
	        String data = ParamUtils.getParameter(request, "data");
	        // data 格式：{"100":"level1.mp3", "200":"level2.mp3", "300":""}
	        
	        Gson gson = new Gson();
	        Map<String, String> soundMap = gson.fromJson(data, new TypeToken<Map<String, String>>(){}.getType());
	        
	        // 1. 更新数据库 TBL_CODE.ALARMSOUND
	        for (Map.Entry<String, String> entry : soundMap.entrySet()) {
	            int level = Integer.parseInt(entry.getKey());
	            String fileName = entry.getValue() == null ? "" : entry.getValue().trim();
	            String updateSql = "update tbl_code t set t.itemname='" + fileName + "' "
	                             + " where t.itemcode='ALARMSOUND' and t.itemvalue=" + level;
	            alarmSetManagerService.getBaseDao().updateOrDeleteBySql(updateSql);
	        }
	        
	        // 2. 刷新内存缓存
	        MemoryDataManagerTask.initAlarmStyle();
	        
	        resp.put("success", true);
	    } catch (Exception e) {
	        log.error("setAlarmSound error", e);
	        resp.put("success", false);
	        resp.put("message", e.getMessage());
	    }
	    
	    response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
	    PrintWriter pw = response.getWriter();
	    pw.print(new Gson().toJson(resp));
	    pw.flush();
	    pw.close();
	    return null;
	}
	
	@RequestMapping("/setAlarmColor")
	public String setAlarmColor() throws Exception {
		Gson gson = new Gson();
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<AlarmShowStyle>() {}.getType();
		AlarmShowStyle alarmShowStyleSaveData=gson.fromJson(data, type);
		String json= "{\"success\":true,\"msg\":false}";
		try {
			if(alarmShowStyleSaveData!=null){
				alarmSetManagerService.setAlarmColor(alarmShowStyleSaveData);
				MemoryDataManagerTask.initAlarmStyle();
				json="{\"success\":true,\"msg\":true}";
			}
		} catch (Exception e) {
			json = "{\"success\":true,\"msg\":false}";
			e.printStackTrace();
		}
//		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/** 报警声音物理目录（用于 File IO），依赖 oem 配置的 alarmSound */
	private String getAlarmSoundPhysicalDir() {
	    String base = Config.getInstance().configFile.getAp().getOem().getAlarmSound();
	    if (!StringManagerUtils.isNotNull(base)) return null;
	    File f = new File(base);
	    if (f.isAbsolute()) return base;
	    // 相对路径 → 走 ServletContext
	    String real = request.getSession().getServletContext().getRealPath(base);
	    return real;
	}
	
	/** 报警声音 web 基础路径（用于前端 <audio src>），直接取 oem 配置 */
	private String getAlarmSoundWebBase() {
	    String base = Config.getInstance().configFile.getAp().getOem().getAlarmSound();
	    if (!StringManagerUtils.isNotNull(base)) return "";
	    
	    // 去除配置路径末尾的斜杠，防止出现双斜杠 //
	    if (base.endsWith("/")) {
	        base = base.substring(0, base.length() - 1);
	    }
	    
	    // 如果配置里没有带项目名，自动补上项目的 context path
	    String ctx = request.getContextPath(); // 获取 /ap 或实际部署名
	    if (!base.startsWith(ctx) && StringManagerUtils.isNotNull(ctx)) {
	        return ctx + base;
	    }
	    return base;
	}
	
	/** 判断文件是否存在（物理维度） */
	private boolean alarmSoundExists(String fileName) {
	    if (!StringManagerUtils.isNotNull(fileName)) return false;
	    String dir = getAlarmSoundPhysicalDir();
	    if (dir == null) return false;
	    return new File(dir, fileName).exists();
	}
	
	@RequestMapping("/getAlarmSoundBaseUrl")
	public String getAlarmSoundBaseUrl() throws Exception {
	    Map<String, Object> resp = new HashMap<String, Object>();
	    try {
	        resp.put("success", true);
	        resp.put("baseUrl", getAlarmSoundWebBase());
	    } catch (Exception e) {
	        resp.put("success", false);
	    }
	    response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
	    PrintWriter pw = response.getWriter();
	    pw.print(new Gson().toJson(resp));
	    pw.flush();
	    pw.close();
	    return null;
	}
	
	/**
     * 获取当前报警声音配置列表
     */
	@RequestMapping("/getAlarmSoundList")
	public String getAlarmSoundList() throws Exception {
	    Map<String, Object> resp = new HashMap<String, Object>();
	    try {
	        String webBase = getAlarmSoundWebBase();
	        resp.put("baseUrl", webBase);

	        // ★ 直接从内存取（内部命中 Redis 缓存，无库查询）
	        AlarmShowStyle alarmShowStyle = MemoryDataManagerTask.getAlarmShowStyle();
	        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

	        if (alarmShowStyle != null && alarmShowStyle.getData() != null) {
	            AlarmShowStyle.AlarmLevelStyle ds = alarmShowStyle.getData();
	            data.add(buildSoundItem(100, "一级报警", webBase,
	                    ds.getFirstLevel()  == null ? "" : ds.getFirstLevel().getSoundFile()));
	            data.add(buildSoundItem(200, "二级报警", webBase,
	                    ds.getSecondLevel() == null ? "" : ds.getSecondLevel().getSoundFile()));
	            data.add(buildSoundItem(300, "三级报警", webBase,
	                    ds.getThirdLevel()  == null ? "" : ds.getThirdLevel().getSoundFile()));
	        } else {
	            // 兜底：内存异常时返回三条空记录，前端仍能渲染
	            data.add(buildSoundItem(100, "一级报警", webBase, ""));
	            data.add(buildSoundItem(200, "二级报警", webBase, ""));
	            data.add(buildSoundItem(300, "三级报警", webBase, ""));
	        }

	        resp.put("success", true);
	        resp.put("data", data);
	    } catch (Exception e) {
	        log.error("getAlarmSoundList error", e);
	        resp.put("success", false);
	        resp.put("data", new ArrayList<Map<String, Object>>());
	    }

	    response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter pw = response.getWriter();
	    pw.print(new Gson().toJson(resp));
	    pw.flush();
	    pw.close();
	    return null;
	}

	/** 组装单条声音配置项：文件名来自内存，存在性与 url 在接口层拼 */
	private Map<String, Object> buildSoundItem(int level, String levelName,
	                                           String webBase, String fileName) {
	    if (fileName == null) fileName = "";
	    fileName = fileName.trim();

	    boolean exists = alarmSoundExists(fileName);

	    Map<String, Object> item = new HashMap<String, Object>();
	    item.put("level", level);
	    item.put("name", levelName);
	    item.put("fileName", exists ? fileName : "");
	    item.put("exists", exists);
	    item.put("url", exists
	            ? webBase + "/" + fileName + "?t=" + System.currentTimeMillis()
	            : "");
	    return item;
	}

    /**
     * 上传报警声音
     */
    @RequestMapping("/uploadAlarmSound")
    public String uploadAlarmSound(Integer level, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resp = new HashMap<>();
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            
            if (file != null && !file.isEmpty()) {
                String dirPath = getAlarmSoundPhysicalDir();
                if (!StringManagerUtils.isNotNull(dirPath)) {
                    resp.put("success", false);
                    resp.put("message", "找不到音频目录");
                    return new Gson().toJson(resp);
                }
                
                File dir = new File(dirPath);
                if (!dir.exists()) dir.mkdirs();
                
                String originalName = file.getOriginalFilename();
                if (originalName == null) originalName = "audio.mp3";
                originalName = originalName.replace("\\", "/");
                int slashIdx = originalName.lastIndexOf("/");
                if (slashIdx >= 0) originalName = originalName.substring(slashIdx + 1);
                
                String lower = originalName.toLowerCase();
                if (!lower.endsWith(".mp3") && !lower.endsWith(".wav") && !lower.endsWith(".ogg")) {
                    resp.put("success", false);
                    resp.put("message", "仅支持 mp3/wav/ogg 格式");
                    return new Gson().toJson(resp);
                }

                // ★ 参考场景图片上传逻辑：同名文件自动加时间戳
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
                
                resp.put("success", true);
                resp.put("fileName", fileName); // 返回最终生成的文件名
            } else {
                resp.put("success", false);
                resp.put("message", "文件为空");
            }
        } catch (Exception e) {
            log.error("uploadAlarmSound error", e);
            resp.put("success", false);
            resp.put("message", e.getMessage());
        }
        
        response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
        try {
            PrintWriter pw = response.getWriter();
            pw.print(new Gson().toJson(resp));
            pw.flush();
            pw.close();
        } catch (Exception ignore) {}
        return null;
    }

    /**
     * 删除报警声音
     */
    @RequestMapping("/deleteAlarmSound")
    public String deleteAlarmSound(Integer level) {
        Map<String, Object> resp = new HashMap<>();
        try {
            if (level != null) {
                String basePath = Config.getInstance().configFile.getAp().getOem().getAlarmSound();
                String fileName = "level" + (level / 100) + ".mp3";
                File f = new File(basePath + File.separator + fileName);
                if (f.exists()) f.delete();
                resp.put("success", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.put("success", false);
        }
        return new Gson().toJson(resp);
    }
    
    @RequestMapping("/getAlarmSoundPool")
    public String getAlarmSoundPool() throws Exception {
        Map<String, Object> resp = new HashMap<String, Object>();
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        try {
            String dir = getAlarmSoundPhysicalDir();
            if (StringManagerUtils.isNotNull(dir)) {
                File folder = new File(dir);
                if (folder.exists() && folder.isDirectory()) {
                    File[] files = folder.listFiles();
                    String webBase = getAlarmSoundWebBase();
                    if (files != null) {
                        for (File f : files) {
                            if (f.isFile()) {
                                String name = f.getName().toLowerCase();
                                if (name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".ogg")) {
                                    Map<String, Object> item = new HashMap<String, Object>();
                                    item.put("fileName", f.getName());
                                    item.put("url", webBase + "/" + f.getName() + "?t=" + System.currentTimeMillis());
                                    item.put("size", f.length());
                                    data.add(item);
                                }
                            }
                        }
                    }
                }
            }
            resp.put("success", true);
            resp.put("data", data);
        } catch (Exception e) {
            log.error("getAlarmSoundPool error", e);
            resp.put("success", false);
        }
        response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
        PrintWriter pw = response.getWriter();
        pw.print(new Gson().toJson(resp));
        pw.flush();
        pw.close();
        return null;
    }
    
    @RequestMapping("/playAlarmSound")
    public void playAlarmSound(String fileName, HttpServletResponse response) {
        try {
            String dir = getAlarmSoundPhysicalDir();
            if (!StringManagerUtils.isNotNull(fileName) || !StringManagerUtils.isNotNull(dir)) {
                response.setStatus(404); return;
            }
            File file = new File(dir, fileName);
            if (file.exists()) {
                response.setContentType("audio/mpeg");
                response.setHeader("Content-Disposition", "inline; filename=" + fileName);
                response.setContentLength((int) file.length());
                java.io.InputStream in = new java.io.FileInputStream(file);
                java.io.OutputStream out = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.flush();
            } else {
                response.setStatus(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }
    
    @RequestMapping("/deleteAlarmSoundPool")
    public String deleteAlarmSoundPool(String fileName) {
        Map<String, Object> resp = new HashMap<String, Object>();
        try {
            if (StringManagerUtils.isNotNull(fileName)) {
                String dir = getAlarmSoundPhysicalDir();
                if (StringManagerUtils.isNotNull(dir)) {
                    File file = new File(dir, fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    resp.put("success", true);
                } else {
                    resp.put("success", false);
                    resp.put("message", "找不到音频目录");
                }
            } else {
                resp.put("success", false);
                resp.put("message", "文件名为空");
            }
        } catch (Exception e) {
            log.error("deleteAlarmSoundPool error", e);
            resp.put("success", false);
            resp.put("message", e.getMessage());
        }
        response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
        try {
            PrintWriter pw = response.getWriter();
            pw.print(new Gson().toJson(resp));
            pw.flush();
            pw.close();
        } catch (Exception ignore) {}
        return null;
    }

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

//	public DistreteAlarmLimit getLimit() {
//		return limit;
//	}
//
//	public void setLimit(DistreteAlarmLimit limit) {
//		this.limit = limit;
//	}
}