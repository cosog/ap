package com.gao.websocket.config;

import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.web.servlet.config.annotation.EnableWebMvc;  
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;  
import org.springframework.web.socket.WebSocketHandler;  
import org.springframework.web.socket.config.annotation.EnableWebSocket;  
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;  
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.gao.websocket.interceptor.WebSocketHandshakeInterceptor;
import com.gao.websocket.handler.RealSocketHandler;

/** 
 * Spring框架WebSocket配置管理类 
 * @author guoyankun 
 */  
@Configuration  
@EnableWebMvc  
@EnableWebSocket 
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {  
	
	public WebSocketConfig(){
		
	}
	/** 
	 * 注册websocket 
	 */  
	@Override  
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {  
		// 注册多个websocket服务，addHandler第一个参数是websocket的具体业务处理类，第二个参数collectionList相当于endpoint  
		System.out.println("启动Websocket服务端");
		registry.addHandler(RealSocketHandler(), "/collectionList").addInterceptors(new WebSocketHandshakeInterceptor());  
		// 可以注册多个websocket的endpoint  
	}  
	@Bean  
	public WebSocketHandler RealSocketHandler() {  
		return new RealSocketHandler();  
	}  
}  
