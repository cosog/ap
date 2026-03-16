package com.cosog.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter {

    // 开发模式：true 表示允许所有来源（动态返回 Origin），false 表示仅允许白名单
    private static final boolean DEV_MODE = true; // 开发时可改为 true，生产必须 false

    // 生产环境白名单（当 DEV_MODE = false 时生效）
    private static final String[] PROD_ALLOWED_ORIGINS = {
        "http://你的外部系统.com",
        "http://127.0.0.1:8080"
    };

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        String origin = request.getHeader("Origin");

        if (origin != null && !origin.isEmpty()) {
            if (DEV_MODE) {
                // 开发模式：直接返回请求的 Origin（允许所有，且可携带凭证）
                response.setHeader("Access-Control-Allow-Origin", origin);
                response.setHeader("Access-Control-Allow-Credentials", "true");
            } else {
                // 生产模式：检查白名单
                for (String allowed : PROD_ALLOWED_ORIGINS) {
                    if (allowed.equals(origin)) {
                        response.setHeader("Access-Control-Allow-Origin", origin);
                        response.setHeader("Access-Control-Allow-Credentials", "true");
                        break;
                    }
                }
            }
        }

        // 其他跨域头
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, X-Access-Token");
        response.setHeader("Access-Control-Max-Age", "3600");

        // 处理 OPTIONS 预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}