package com.cosog.websocket.config;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value="/websocketServer/{userId}")
public class WebSocketByJavax {
	private Logger logger = LoggerFactory.getLogger(WebSocketByJavax.class);
	private static int onlineCount = 0; 
    private String userId;
    private Session session; 
    private static Map<String, WebSocketByJavax> clients;
    
    static {
    	clients =  new ConcurrentHashMap<String, WebSocketByJavax>();
    }
    //连接时执行
    @OnOpen
    public void onOpen(@PathParam("userId") String userId,Session session) throws IOException{
        this.userId = userId+new Date().getTime();
        this.session=session;
        clients.put(this.userId,this);
        addOnlineCount();
        logger.debug("新连接：{}",this.userId);
        System.out.println("接收到客户端连接:"+this.userId);
        System.out.println("当前线上用户数量:"+clients.size()+","+this.getOnlineCount());
//        session.getBasicRemote().sendText("收到 "+this.userId+" 的连接.");
    }
    
    //关闭时执行
    @SuppressWarnings("static-access")
	@OnClose
    public void onClose(){
        logger.debug("连接：{} 关闭",this.userId);
        clients.remove(userId);
        subOnlineCount();
        System.out.println("用户"+userId+"已退出！");
        System.out.println("剩余在线用户"+clients.size()+","+this.getOnlineCount());
    }
    
    //收到消息时执行
    @SuppressWarnings({ "static-access","static-access" })
	@OnMessage
    public void onMessage(String message, Session session) throws IOException {
        logger.debug("收到用户{}的消息{}",this.userId,message);
        session.getBasicRemote().sendText("收到 "+this.userId+" 的消息: "+message); //回复用户
    }
    
    //连接错误时执行
    @OnError
    public void onError(Session session, Throwable error) throws IOException{
        logger.debug("用户id为：{}的连接发送错误",this.userId);
        error.printStackTrace();
        this.session.close();
    }
    
    public void sendMessageTo(String To,String message) throws IOException {
        for (WebSocketByJavax item : clients.values()) { 
            if (item.userId.equals(To) ) 
                item.session.getAsyncRemote().sendText(message);
        }
    }
    
    public void sendMessageToBy(String To,String message) throws IOException {
        for (WebSocketByJavax item : clients.values()) { 
            if (item.userId.contains(To) ) 
                item.session.getAsyncRemote().sendText(message);
        }
    }
    
    public void sendMessageAll(String message) throws IOException {
        for (WebSocketByJavax item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }
    
    public static synchronized int getOnlineCount() { 
        return onlineCount; 
    } 

    public static synchronized void addOnlineCount() {
    	WebSocketByJavax.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
    	WebSocketByJavax.onlineCount--; 
    }

    public static synchronized Map<String, WebSocketByJavax> getClients() {
        return clients;
    }
}
