package com.cosog.websocket.interceptor;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
import org.springframework.http.server.ServerHttpRequest;  
import org.springframework.http.server.ServerHttpResponse;  
import org.springframework.http.server.ServletServerHttpRequest;  
import org.springframework.web.socket.WebSocketHandler;  
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * 创建websocket连接是的拦截器，记录建立连接的用户的session以便根据不同session来通信
 * 
 * */
public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor implements HandshakeInterceptor {  
	  
    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(HandshakeInterceptor.class);  
    @Override  
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {  
        if (request instanceof ServletServerHttpRequest) {  
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;  
            HttpSession session = servletRequest.getServletRequest().getSession(false);  
            String userName="WEBSOCKETCLIENT";//+new Date().getTime();
            if (session != null) {  
                //使用userName区分WebSocketHandler，以便定向发送消息  
//                userName = (String) session.getAttribute("SESSION_USERNAME");  
            	userName = ((ServletServerHttpRequest) request).getServletRequest().getParameter("module_Code")+new Date().getTime(); 
                if(userName != null){  
                    attributes.put("WEBSOCKET_USERID",userName);  
                }  
            }  
        }  
        return true;  
    }  
  
    @Override  
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {  
    	super.afterHandshake(request, response, wsHandler, ex);
    }
}
