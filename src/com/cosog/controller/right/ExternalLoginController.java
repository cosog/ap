package com.cosog.controller.right;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.Module;
import com.cosog.model.Org;
import com.cosog.model.User;
import com.cosog.service.data.SystemdataInfoService;
import com.cosog.service.right.ModuleManagerService;
import com.cosog.service.right.OrgManagerService;
import com.cosog.service.right.TabInfoManagerService;
import com.cosog.service.right.UserManagerService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.Constants;
import com.cosog.utils.Page;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.SessionLockHelper;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.UnixPwdCrypt;
import com.opensymphony.xwork2.ActionContext;

@Controller
@RequestMapping("/external")
public class ExternalLoginController extends BaseController {
    
    private static final Log log = LogFactory.getLog(ExternalLoginController.class);
    
    @Autowired
    private UserManagerService<User> service;
    @Autowired
	private OrgManagerService<?> orgManagerService;
	@Autowired
	private SystemdataInfoService systemdataInfoService;
	@Autowired
	private OrgManagerService<Org> orgService;
	@Autowired
	private ModuleManagerService<Module> modService;
	@Autowired
	private TabInfoManagerService<?> tabInfoManagerService;
    
    // Token缓存（生产环境建议用Redis）
    private static final Map<String, TokenInfo> tokenCache = new ConcurrentHashMap<>();
    
    @RequestMapping("/login")
    public String externalLogin() throws Exception {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        
        try {
            String userId = ParamUtils.getParameter(request, "userId");
            String userPwd = ParamUtils.getParameter(request, "userPwd");
            String callback = ParamUtils.getParameter(request, "callback"); // 可选，回调地址
            
            String clientIp=StringManagerUtils.getIpAddr(request);
            HttpSession session=request.getSession();
            
            if (!StringManagerUtils.isNotNull(userId) || !StringManagerUtils.isNotNull(userPwd)) {
                out.print("{\"success\":false,\"code\":400,\"message\":\"用户名密码不能为空\"}");
                return null;
            }
            
            // URL解码
            String username = URLDecoder.decode(userId, "UTF-8");
            String password = URLDecoder.decode(userPwd, "UTF-8");
            
            String locale=Config.getInstance().configFile.getAp().getOthers().getLoginLanguage();
    		Locale l = Locale.getDefault(); 
    		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(locale);
            
            // 登录验证
            User user = this.service.doLogin(username, StringManagerUtils.stringToMD5(password));
            
            if (user != null && user.getUserEnable() == 1) {
            	service.setUserLanguage(user);
				service.setUserRoleRight(user);
				locale=user.getLanguageName().toLowerCase().replace("zh_cn", "zh_CN");
				String languageResourceStr=MemoryDataManagerTask.getLanguageResourceStr(locale);
				String languageResourceFirstLower=MemoryDataManagerTask.getLanguageResourceStr_FirstLetterLowercase(locale);
				languageResourceMap=MemoryDataManagerTask.getLanguageResource(locale);
				
				
				
				user.setOrgtreeid(orgManagerService.findOrgById(user.getUserOrgid()));
				user.setUserParentOrgids(orgService.findParentIds(user.getUserOrgid()));
				user.setUserOrgIds(orgService.findChildIds(user.getUserOrgid()));
				user.setUserOrgNames(orgService.findChildNames(user.getUserOrgid(),user.getLanguageName()));
				user.setAllOrgPatentNodeIds(orgService.fingAllOrgParentNodeIds());
				user.setAllModParentNodeIds(modService.fingAllModParentNodeIds());
				user.setDeviceTypeIds(tabInfoManagerService.queryTabs(user));
				user.setLoginIp(clientIp);
				user.setLanguageResource(languageResourceStr);
				user.setLanguageResourceFirstLower(languageResourceFirstLower);
				user.setLoginTime(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				
				session.setAttribute("userLogin", user);
				session.setAttribute("SESSION_USERNAME", username);
				session.setAttribute("WW_TRANS_I18N_LOCALE", l);
				session.setAttribute("browserLang", locale);
				SessionLockHelper.putSession(session);
                
                // 生成一次性Token
				String token = generateToken(session.getId(), user,clientIp);
                
                // 构建返回结果
                StringBuilder result = new StringBuilder();
                result.append("{");
                result.append("\"success\":true,");
                result.append("\"code\":200,");
                result.append("\"message\":\"登录成功\",");
                result.append("\"token\":\"").append(token).append("\",");
                result.append("\"language\":\"").append(user.getLanguageName()).append("\",");
                result.append("\"userId\":\"").append(user.getUserId()).append("\",");
                result.append("\"userName\":\"").append(user.getUserName()).append("\",");
                result.append("\"homeUrl\":\"").append(getHomeUrl(token)).append("\"");
                
                // 如果有回调地址，添加回调URL
                if (StringManagerUtils.isNotNull(callback)) {
                    result.append(",\"callbackUrl\":\"").append(callback);
                    if (callback.contains("?")) {
                        result.append("&token=").append(token);
                    } else {
                        result.append("?token=").append(token);
                    }
                    result.append("\"");
                }
                result.append("}");
                out.print(result.toString());
                // 保存系统日志
                this.service.saveSystemLog(user, 0, "外部系统登录");
            } else {
                out.print("{\"success\":false,\"code\":401,\"message\":\"用户名或密码错误\"}");
                log.warn("外部系统登录失败，用户名：" + username);
            }
        } catch (Exception e) {
            log.error("外部系统登录异常", e);
            out.print("{\"success\":false,\"code\":500,\"message\":\"系统内部错误\"}");
        }
        
        return null;
    }
    
    /**
     * 生成一次性Token
     */
    private String generateToken(String sessionId, User user, String clientIp) {
        // 生成唯一Token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        
        // 创建TokenInfo对象
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setSessionId(sessionId);
        tokenInfo.setClientIp(clientIp);
        tokenInfo.setUserAgent(request.getHeader("User-Agent"));
        tokenInfo.setCreateTime(System.currentTimeMillis());
        tokenInfo.setExpireTime(System.currentTimeMillis() + 30 * 1000); // 30秒有效期
        
        // 保存完整的User对象（包含所有权限信息）
        tokenInfo.setUser(user);
        
        // 存入缓存
        tokenCache.put(token, tokenInfo);
        
        log.info("Token生成成功，用户：" + user.getUserName() + 
                 "，语言：" + user.getLanguageName() + 
                 "，权限信息已保存");
        
        return token;
    }
    
    /**
     * 获取主页URL
     */
    private String getHomeUrl(String token) {
        String baseUrl = request.getScheme() + "://" + request.getServerName();
        
        int port = request.getServerPort();
        if (port != 80 && port != 443) {
            baseUrl += ":" + port;
        }
        
        String contextPath = request.getContextPath();
        
        // 从tokenCache中获取语言信息
        TokenInfo tokenInfo = tokenCache.get(token);
        String language = "zh_CN";
        if (tokenInfo != null && tokenInfo.getLanguage() != null) {
            language = tokenInfo.getLanguage();
        }
        
        // 在URL中同时传递token和language
        return baseUrl + contextPath + "/home?token=" + token + "&lang=" + language;
    }
    
    /**
     * 获取Token缓存（供过滤器使用）
     */
    public static Map<String, TokenInfo> getTokenCache() {
        return tokenCache;
    }
    
    /**
     * 清理过期的Token（可以定时调用）
     */
    @Scheduled(fixedDelay = 60000) // 每分钟执行一次
    public void cleanExpiredTokens() {
        int beforeSize = tokenCache.size();
        long now = System.currentTimeMillis();
        tokenCache.entrySet().removeIf(entry -> entry.getValue().isExpired());
        int afterSize = tokenCache.size();
        log.info("清理过期Token，清理前：" + beforeSize + "，清理后：" + afterSize);
    }
    
    /**
     * Token验证接口（供外部系统验证Token有效性）
     */
    @RequestMapping("/verifyToken")
    public String verifyToken() throws Exception {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        
        String token = ParamUtils.getParameter(request, "token");
        
        if (!StringManagerUtils.isNotNull(token)) {
            out.print("{\"success\":false,\"code\":400,\"message\":\"Token不能为空\"}");
            return null;
        }
        
        TokenInfo tokenInfo = tokenCache.get(token);
        
        if (tokenInfo == null) {
            out.print("{\"success\":false,\"code\":401,\"message\":\"Token无效或已过期\"}");
            return null;
        }
        
        if (tokenInfo.isExpired()) {
            tokenCache.remove(token);
            out.print("{\"success\":false,\"code\":401,\"message\":\"Token已过期\"}");
            return null;
        }
        
        out.print("{\"success\":true,\"code\":200,\"message\":\"Token有效\"," +
                 "\"userId\":" + tokenInfo.getUserId() + "," +
                 "\"userName\":\"" + tokenInfo.getUserName() + "\"," +
                 "\"language\":\"" + tokenInfo.getLanguage() + "\"}");
        
        return null;
    }
    
    /**
     * Token信息类
     */
    /**
     * Token信息类 - 存储完整的用户信息和权限
     */
    public static class TokenInfo {
        private User user;              // 完整的用户对象
        private String sessionId;        // 原始会话ID
        private String userId;           // 用户ID
        private String userName;         // 用户名
        private String language;         // 语言
        private long createTime;         // 创建时间
        private long expireTime;         // 过期时间
        private String clientIp;         // 客户端IP
        private String userAgent;        // 用户代理
        
        // 权限相关
        private String orgtreeid;        // 组织树ID
        private String userParentOrgids; // 父组织ID
        private String userOrgIds;       // 子组织ID
        private String userOrgNames;     // 组织名称
        private String allOrgPatentNodeIds; // 所有组织父节点
        private String allModParentNodeIds; // 所有模块父节点
        private String deviceTypeIds;    // 设备类型ID
        private String languageResource; // 语言资源
        private String languageResourceFirstLower; // 语言资源(首字母小写)
        private String loginTime;        // 登录时间
        private int pageSize;            // 分页大小
        private String syncOrAsync;      // 同步异步
        private int defaultComboxSize;   // 默认下拉框大小
        private int defaultGraghSize;    // 默认图表大小
        
        // getters and setters...
        public User getUser() {
            return user;
        }
        
        public void setUser(User user) {
            this.user = user;
            // 从user对象中提取关键信息
            if (user != null) {
                this.userId = String.valueOf(user.getUserId());
                this.userName = user.getUserName();
                this.language = user.getLanguageName();
                this.orgtreeid = user.getOrgtreeid();
                this.userParentOrgids = user.getUserParentOrgids();
                this.userOrgIds = user.getUserOrgIds();
                this.userOrgNames = user.getUserOrgNames();
                this.allOrgPatentNodeIds = user.getAllOrgPatentNodeIds();
                this.allModParentNodeIds = user.getAllModParentNodeIds();
                this.deviceTypeIds = user.getDeviceTypeIds();
                this.languageResource = user.getLanguageResource();
                this.languageResourceFirstLower = user.getLanguageResourceFirstLower();
                this.loginTime = user.getLoginTime();
                this.pageSize = Integer.parseInt(user.getPageSize() != null ? user.getPageSize() : "25");
                this.syncOrAsync = user.getSyncOrAsync();
                this.defaultComboxSize = Integer.parseInt(user.getDefaultComboxSize() != null ? user.getDefaultComboxSize() : "10");
                this.defaultGraghSize = Integer.parseInt(user.getDefaultGraghSize() != null ? user.getDefaultGraghSize() : "5");
            }
        }
        
        // 检查是否过期
        public boolean isExpired() {
            return System.currentTimeMillis() > expireTime;
        }
        
        // 将TokenInfo中的信息恢复到User对象
        public User restoreUser() {
            User user = new User();
            user.setUserId(this.userId);
            user.setUserName(this.userName);
            user.setLanguageName(this.language);
            user.setOrgtreeid(this.orgtreeid);
            user.setUserParentOrgids(this.userParentOrgids);
            user.setUserOrgIds(this.userOrgIds);
            user.setUserOrgNames(this.userOrgNames);
            user.setAllOrgPatentNodeIds(this.allOrgPatentNodeIds);
            user.setAllModParentNodeIds(this.allModParentNodeIds);
            user.setDeviceTypeIds(this.deviceTypeIds);
            user.setLanguageResource(this.languageResource);
            user.setLanguageResourceFirstLower(this.languageResourceFirstLower);
            user.setLoginTime(this.loginTime);
            user.setPageSize(String.valueOf(this.pageSize));
            user.setSyncOrAsync(this.syncOrAsync);
            user.setDefaultComboxSize(String.valueOf(this.defaultComboxSize));
            user.setDefaultGraghSize(String.valueOf(this.defaultGraghSize));
            user.setLoginIp(this.clientIp);
            return user;
        }

		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}

		public long getExpireTime() {
			return expireTime;
		}

		public void setExpireTime(long expireTime) {
			this.expireTime = expireTime;
		}

		public String getClientIp() {
			return clientIp;
		}

		public void setClientIp(String clientIp) {
			this.clientIp = clientIp;
		}

		public String getUserAgent() {
			return userAgent;
		}

		public void setUserAgent(String userAgent) {
			this.userAgent = userAgent;
		}

		public String getOrgtreeid() {
			return orgtreeid;
		}

		public void setOrgtreeid(String orgtreeid) {
			this.orgtreeid = orgtreeid;
		}

		public String getUserParentOrgids() {
			return userParentOrgids;
		}

		public void setUserParentOrgids(String userParentOrgids) {
			this.userParentOrgids = userParentOrgids;
		}

		public String getUserOrgIds() {
			return userOrgIds;
		}

		public void setUserOrgIds(String userOrgIds) {
			this.userOrgIds = userOrgIds;
		}

		public String getUserOrgNames() {
			return userOrgNames;
		}

		public void setUserOrgNames(String userOrgNames) {
			this.userOrgNames = userOrgNames;
		}

		public String getAllOrgPatentNodeIds() {
			return allOrgPatentNodeIds;
		}

		public void setAllOrgPatentNodeIds(String allOrgPatentNodeIds) {
			this.allOrgPatentNodeIds = allOrgPatentNodeIds;
		}

		public String getAllModParentNodeIds() {
			return allModParentNodeIds;
		}

		public void setAllModParentNodeIds(String allModParentNodeIds) {
			this.allModParentNodeIds = allModParentNodeIds;
		}

		public String getDeviceTypeIds() {
			return deviceTypeIds;
		}

		public void setDeviceTypeIds(String deviceTypeIds) {
			this.deviceTypeIds = deviceTypeIds;
		}

		public String getLanguageResource() {
			return languageResource;
		}

		public void setLanguageResource(String languageResource) {
			this.languageResource = languageResource;
		}

		public String getLanguageResourceFirstLower() {
			return languageResourceFirstLower;
		}

		public void setLanguageResourceFirstLower(String languageResourceFirstLower) {
			this.languageResourceFirstLower = languageResourceFirstLower;
		}

		public String getLoginTime() {
			return loginTime;
		}

		public void setLoginTime(String loginTime) {
			this.loginTime = loginTime;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public String getSyncOrAsync() {
			return syncOrAsync;
		}

		public void setSyncOrAsync(String syncOrAsync) {
			this.syncOrAsync = syncOrAsync;
		}

		public int getDefaultComboxSize() {
			return defaultComboxSize;
		}

		public void setDefaultComboxSize(int defaultComboxSize) {
			this.defaultComboxSize = defaultComboxSize;
		}

		public int getDefaultGraghSize() {
			return defaultGraghSize;
		}

		public void setDefaultGraghSize(int defaultGraghSize) {
			this.defaultGraghSize = defaultGraghSize;
		}
    }
}
