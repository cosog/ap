package com.cosog.websocket.config;

import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.WebSocketHandler;  
import org.springframework.web.socket.config.annotation.EnableWebSocket;  
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;  
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.cosog.websocket.handler.SpringWebSocketHandler;
import com.cosog.websocket.interceptor.SpringWebSocketHandlerInterceptor;

/** 
 * Spring框架WebSocket配置管理类 
 * @author guoyankun 
 */  
@Configuration  
@EnableWebMvc  
@EnableWebSocket 
public class WebSocketConfig implements WebMvcConfigurer, WebSocketConfigurer {  
	
	public WebSocketConfig(){
		
	}
	/** 
	 * 注册websocket 
	 * 向spring容器注册一个handler地址
	 */  
	@Override  
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {  
		// 注册多个websocket服务，addHandler第一个参数是websocket的具体业务处理类，第二个参数collectionList相当于endpoint  
		System.out.println("启动Websocket服务端");
		String[] allowsOrigins = {"*"};
		registry.addHandler(RealSocketHandler(), "/collectionList").setAllowedOrigins(allowsOrigins).addInterceptors(new SpringWebSocketHandlerInterceptor());  
		
		registry.addHandler(RealSocketHandler(),"/websocket/socketServer").setAllowedOrigins("*").addInterceptors(new SpringWebSocketHandlerInterceptor());

		registry.addHandler(RealSocketHandler(), "/sockjs/socketServer").setAllowedOrigins("http://localhost:28180").addInterceptors(new SpringWebSocketHandlerInterceptor()).withSockJS();
		
		
		
		// 可以注册多个websocket的endpoint  
	}  
	@Bean  
	public WebSocketHandler RealSocketHandler() {  
		return new SpringWebSocketHandler();  
	}  
}  
