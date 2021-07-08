package com.cosog.common.filter;




import java.io.IOException;
import java.util.*;    
import javax.servlet.*;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;   
  
/**
 * <p>描述：采用gzip压缩js文件后，利用该过滤器来 预先处理加载gzjs文件解压成js、css类型</p>
 * 
 * @author gao 2014-05-09
 * @version 1.0
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})  
public class GzFilter implements Filter {  
	
    private Map expiresMap = new HashMap();
    FilterConfig fc;   
    
    @Override  
    public void destroy() {   
        this.fc = null;   
    } 
    
    public void doFilter(ServletRequest httpRequest, ServletResponse httpResponse, FilterChain chain)  
            throws IOException, ServletException {  
    	HttpServletRequest request  = (HttpServletRequest) httpRequest;
		HttpServletResponse response = (HttpServletResponse) httpResponse;
		 
		String uriStr = request.getServletPath(); 
        // 设置请求的编码格式  
        request.setCharacterEncoding("GBK");  
        // 使用Map调用其entrySet()返回一个Set<Map.Entry>的集合,Map.Entry为Map的成员内部类，Map.Entry提供两个方法getKey(),getValue()  
        for (Iterator it = expiresMap.entrySet().iterator(); it.hasNext();) {  
            Map.Entry entry = (Map.Entry) it.next();  
            // 循环配置响应头部信息  
            response.setHeader((String) entry.getKey(), (String) entry.getValue());  
        }
        
        String[] getSplit=uriStr.split("\\/");
     	String get_images = getSplit[1];
     	//指定缓存的目录
        if(get_images.equals("images")||get_images.equals("scripts")||get_images.equals("styles")){ 
        	 //向缓存里添加元素
			Enumeration e=fc.getInitParameterNames();
    		 String headerName = (String)e.nextElement();
             response.addHeader(headerName, fc.getInitParameter(headerName));
             response.setHeader("Cache-Control", "max-age=3600"); 
   		     response.setHeader("Cache-Control", "no-cache");
		     response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		     response.setDateHeader("Expires", 0);
    	   }
    	//************
        // 执行下一步  
        chain.doFilter(request, response);  
        
    }  

    @Override  
    public void init(FilterConfig filterConfig) throws ServletException {  
    	 
    	 this.fc = filterConfig;  
    	 expiresMap.clear();
        // 获取headers初始化属性值 对应  
        /* 
         * <init-param> <param-name>headers</param> 
         * <param-value>Content-Encoding=gzip</param-value> </init-param> 
         */  
        String headerStr = filterConfig.getInitParameter("headers");  
        // 切割初始化参数headers的数据信息  
        String[] headers = headerStr.split(",");  
        // 解析web.xml中Filter配置标签中的初始化数据信息  
        /* 
         * 在这里提供了一个配置所有相应头部信息的扩展功能，例如可以在xm文件中配置多个参数信息 
         * <param-value>Content-Encoding=gzip,cache=nocache</param-value> 
         */  
        for (int i = 0; i < headers.length; ++i) {  
            String[] temp = headerStr.split("=");  
            // 将配置信息通过=分割后以键值对的形式保存 例如：headers.put("Content-Encoding","gzip");  
            this.expiresMap.put(temp[0].trim(), temp[1].trim());  
        }  
    }  
  
}  
