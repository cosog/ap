package com.cosog.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosog.controller.right.ExternalLoginController;
import com.cosog.controller.right.ExternalLoginController.TokenInfo;
import com.cosog.model.User;
import com.cosog.utils.Config;

public class ExternalTokenFilter implements Filter {
    
    private static final Log log = LogFactory.getLog(ExternalTokenFilter.class);
    
    // 共享Token缓存（需要和登录控制器使用同一个）
    private static Map<String, TokenInfo> tokenCache;
    // 不需要Token验证的路径
    private static final String[] EXCLUDED_PATHS = {
        "/userLogin",           // 登录接口
        "/external/login",      // 外部系统登录接口
        "/css/",                // 静态资源
        "/js/",
        "/images/",
        "/fonts/",
        "/error",
        "/favicon.ico"
    };
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 获取Token缓存（可以从Spring容器获取）
        tokenCache = ExternalLoginController.getTokenCache();
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        
        String path = request.getServletPath();
        String contextPath = request.getContextPath();
        String fullPath = request.getRequestURI();
        
        // 1. 检查是否是排除的路径（静态资源、登录接口等）
        if (isExcludedPath(path)) {
            log.debug("排除路径，直接放行: " + path);
            chain.doFilter(req, resp);
            return;
        }
        
        // 2. 检查是否有正常登录的session（内部系统用户）
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("userLogin");
            if (user != null) {
                log.debug("正常登录用户，直接放行: " + user.getUserName());
                chain.doFilter(req, resp);
                return;
            }
        }
        
        // 3. 检查是否是外部系统的请求（需要token验证的路径）
        if (path.contains("/externalHome")) {
            String token = request.getParameter("token");
            // 尝试从Header获取token
            if (token == null || token.isEmpty()) {
                token = request.getHeader("X-Access-Token");
            }
            
            // 如果没有token，可能是直接访问的非法请求
            if (token == null || token.isEmpty()) {
                // 检查是否是从外部系统跳转过来的
                String referer = request.getHeader("Referer");
                if (referer != null && referer.contains("external")) {
                    sendError(response, 401, "缺少访问令牌");
                    return;
                } else {
                    // 可能是内部系统直接访问但没有session（session过期）
                    log.warn("无token且无session的直接访问，重定向到登录页: " + path);
                    response.sendRedirect(contextPath + "/login.jsp");
                    return;
                }
            }
            
            // 验证Token
            TokenInfo tokenInfo = tokenCache.get(token);
            
            if (tokenInfo == null) {
                sendError(response, 401, "无效的访问令牌");
                return;
            }
            
            // 检查Token是否过期
            if (System.currentTimeMillis() > tokenInfo.getExpireTime()) {
                tokenCache.remove(token);
                sendError(response, 401, "访问令牌已过期");
                return;
            }
            
            // 恢复Session
            HttpSession newSession = restoreSession(request, tokenInfo);
            
            if (newSession == null) {
                log.error("会话恢复失败，token: " + token);
                sendError(response, 401, "会话已失效");
                return;
            }
            
            // Token使用一次后立即失效（一次性使用）
            tokenCache.remove(token);
            log.info("Token验证成功，用户：" + tokenInfo.getUserId() + "，路径：" + path);
        }
        
        chain.doFilter(req, resp);
    }
    
    /**
     * 判断是否是排除的路径
     */
    private boolean isExcludedPath(String path) {
        if (path == null || path.isEmpty()) {
            return true;
        }
        
        for (String excludePath : EXCLUDED_PATHS) {
            if (path.startsWith(excludePath) || path.equals(excludePath)) {
                return true;
            }
        }
        
        // 静态资源文件
        if (path.endsWith(".css") || path.endsWith(".js") || 
            path.endsWith(".png") || path.endsWith(".jpg") || 
            path.endsWith(".gif") || path.endsWith(".ico") ||
            path.endsWith(".woff") || path.endsWith(".woff2") ||
            path.endsWith(".ttf") || path.endsWith(".eot")) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 恢复Session
     */
    /**
     * 恢复Session - 恢复完整的用户信息和权限
     */
    private HttpSession restoreSession(HttpServletRequest request, TokenInfo tokenInfo) {
        try {
            HttpSession session = request.getSession(true);
            
            if (tokenInfo.getUser() != null) {
                // 从TokenInfo中恢复完整的User对象
                User user = tokenInfo.restoreUser();
                
                // 获取语言（优先从URL参数）
                String language = request.getParameter("lang");
                if (language == null || language.isEmpty()) {
                    language = tokenInfo.getLanguage();
                }
                if (language == null || language.isEmpty()) {
                    language = "zh_CN";
                }
                user.setLanguageName(language);
                
                // 恢复所有Session属性
                session.setAttribute("userLogin", user);
                session.setAttribute("SESSION_USERNAME", user.getUserName());
                session.setAttribute("browserLang", language);
                
                // 恢复权限相关属性
                session.setAttribute("orgtreeid", user.getOrgtreeid());
                session.setAttribute("userParentOrgids", user.getUserParentOrgids());
                session.setAttribute("userOrgIds", user.getUserOrgIds());
                session.setAttribute("userOrgNames", user.getUserOrgNames());
                session.setAttribute("allOrgPatentNodeIds", user.getAllOrgPatentNodeIds());
                session.setAttribute("allModParentNodeIds", user.getAllModParentNodeIds());
                session.setAttribute("deviceTypeIds", user.getDeviceTypeIds());
                
                // 恢复语言资源
                session.setAttribute("languageResource", user.getLanguageResource());
                session.setAttribute("languageResourceFirstLower", user.getLanguageResourceFirstLower());
                
                // 恢复配置信息
                session.setAttribute("pageSize", user.getPageSize());
                session.setAttribute("syncOrAsync", user.getSyncOrAsync());
                session.setAttribute("defaultComboxSize", user.getDefaultComboxSize());
                session.setAttribute("defaultGraghSize", user.getDefaultGraghSize());
                
                // 设置Locale
        		Locale locale = Locale.getDefault(); 
                
                if (language.equals("zh_CN")) { 
                	locale = new Locale("zh", "CN"); 
        		}else if (language.equals("en")) { 
        			locale = new Locale("en", "US"); 
        		}else if (language.equals("ru")) { 
        			locale = new Locale("ru", "RU"); 
        		}else{
        			locale = new Locale("zh", "CN"); 
        		}
                session.setAttribute("WW_TRANS_I18N_LOCALE", locale);
                
                
                session.setAttribute("tabInfo", tokenInfo.getTabInfo());
				session.setAttribute("configFile", tokenInfo.getConfigFile());
				session.setAttribute("oem", tokenInfo.getOem());
				session.setAttribute("viewProjectName", tokenInfo.getViewProjectName());
				session.setAttribute("favicon", tokenInfo.getFavicon());
				session.setAttribute("bannerCSS", tokenInfo.getBannerCSS());
				session.setAttribute("showLogo", tokenInfo.getShowLogo());
				session.setAttribute("oemStaticResourceTimestamp", tokenInfo.getOemStaticResourceTimestamp());
				session.setAttribute("otherStaticResourceTimestamp", tokenInfo.getOtherStaticResourceTimestamp());
				session.setAttribute("loadingUI", tokenInfo.getLoadingUI());
				session.setAttribute("helpDocumentUrl", tokenInfo.getHelpDocumentUrl());
				session.setAttribute("showVideo", tokenInfo.getShowLogo());
                
                log.info("Session恢复成功 - 用户: " + user.getUserName() + 
                        ", 语言: " + language + 
                        ", 组织: " + user.getUserOrgNames() +
                        ", SessionID: " + session.getId());
                
                return session;
            } else {
                log.error("TokenInfo中User对象为空");
            }
        } catch (Exception e) {
            log.error("恢复Session异常", e);
        }
        return null;
    }
    
    /**
     * 发送错误响应
     */
    private void sendError(HttpServletResponse response, int code, String message) 
            throws IOException {
        response.setStatus(code);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(String.format(
            "{\"success\":false,\"code\":%d,\"message\":\"%s\"}", code, message));
        out.flush();
    }
    
    @Override
    public void destroy() {}
}
