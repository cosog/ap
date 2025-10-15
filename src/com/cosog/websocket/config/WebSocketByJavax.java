package com.cosog.websocket.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosog.utils.AdvancedMemoryMonitorUtils;
import com.cosog.utils.StringManagerUtils;

@ServerEndpoint(value="/websocketServer/{userId}")
public class WebSocketByJavax {
	public Logger logger = LoggerFactory.getLogger(WebSocketByJavax.class);
	public static int onlineCount = 0; 
	public String userId;
	public Session session; 
	private static final int MAX_CONNECTIONS = 10000;
    public static final ConcurrentHashMap<String, WebSocketByJavax> clients;
    
    static {
    	clients =  new ConcurrentHashMap<String, WebSocketByJavax>();
    }
    //连接时执行
//    @OnOpen
//    public void onOpen(@PathParam("userId") String userId,Session session) throws IOException{
//        synchronized(clients){
//        	this.userId = userId+"_"+new Date().getTime();
//            this.session=session;
//        	clients.put(this.userId,this);
//            addOnlineCount();
//            logger.debug("新连接：{}",this.userId);
//            StringManagerUtils.printLog("接收到客户端连接:"+this.userId);
//            StringManagerUtils.printLog("当前线上用户数量:"+clients.size()+","+this.getOnlineCount());
//        }
//    }
    
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) throws IOException {
        synchronized(clients) {
            if (clients.size() >= MAX_CONNECTIONS) {
                session.close(new CloseReason(CloseReason.CloseCodes.TRY_AGAIN_LATER, "服务器连接数已达上限"));
                return;
            }
            synchronized(clients){
            	this.userId = userId+"_"+new Date().getTime();
                this.session=session;
            	clients.put(this.userId,this);
                addOnlineCount();
                logger.debug("新连接：{}",this.userId);
                StringManagerUtils.printLog("接收到客户端连接:"+this.userId);
                StringManagerUtils.printLog("当前线上用户数量:"+clients.size()+","+this.getOnlineCount());
            }
        }
    }
    
    //关闭时执行
//    @SuppressWarnings("static-access")
//	@OnClose
//    public void onClose(){
//        synchronized(clients){
//        	logger.debug("连接：{} 关闭",this.userId);
//        	if(clients.containsKey(userId)){
//        		clients.remove(userId);
//                subOnlineCount();
//                StringManagerUtils.printLog("用户"+userId+"已退出！");
//                StringManagerUtils.printLog("剩余在线用户"+clients.size()+","+this.getOnlineCount());
//        	}
//        }
//    }
    
    //关闭时执行
    @SuppressWarnings("static-access")
    @OnClose
    public void onClose() {
        synchronized(clients) {
            logger.debug("连接：{} 关闭", this.userId);
            if(clients.containsKey(userId)) {
                WebSocketByJavax client = clients.remove(userId);
                if (client.session != null && client.session.isOpen()) {
                    try {
                        client.session.close();
                    } catch (IOException e) {
                        logger.error("关闭 session 失败: {}", userId, e);
                    }
                }
                subOnlineCount();
                StringManagerUtils.printLog("用户"+userId+"已退出！");
                StringManagerUtils.printLog("剩余在线用户"+clients.size()+","+this.getOnlineCount());
            }
        }
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
//        error.printStackTrace();
        if(this.session!=null && this.session.isOpen()){
        	this.session.close();
        }
    }
    
    public void sendMessageTo(String To,String message) {
		for (WebSocketByJavax item : clients.values()) { 
			String[] clientInfo=item.userId.split("_");
        	if (clientInfo!=null && clientInfo.length==3&&To.equals(clientInfo[1])) {
        		synchronized(item.session){
        			try{
            			if(item.session!=null && item.session.isOpen()){
            				item.session.getBasicRemote().sendText(message);//getBasicRemote同步发送 getAsyncRemote异步发送
            			}
            		}catch(Exception e){
            			e.printStackTrace();
            		}
        		}
        	}
        }
    }
    
    public void sendMessageToBy(String To,String message){
		for (WebSocketByJavax item : clients.values()) {
        	if (item.userId.contains(To) ) {
        		synchronized(item.session){
        			try{
            			if(item.session!=null && item.session.isOpen()){
            				item.session.getBasicRemote().sendText(message);//getBasicRemote同步发送 getAsyncRemote异步发送
            			}
            		}catch(Exception e){
            			e.printStackTrace();
            			System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss") + ":" +"webSocket信息推送失败:"+message.length());
            		}
        		}
        	}
        }
    }
    
//    public void sendMessageToUser(String userAccount,String message){
//		for (WebSocketByJavax item : clients.values()) { 
//            String[] clientInfo=item.userId.split("_");
//        	if(clientInfo!=null && clientInfo.length==3 && userAccount.equals(clientInfo[1])){
//        		synchronized(item.session){
//        			try{
//            			if(item.session!=null && item.session.isOpen()){
//            				item.session.getBasicRemote().sendText(message);//getBasicRemote同步发送 getAsyncRemote异步发送
//            			}
//            		}catch(Exception e){
//            			e.printStackTrace();
//            			System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss") + ":" +"webSocket信息推送失败:"+message.length());
//            		}
//        		}
//            }
//        }
//    }
    
    public void sendMessageToUser(String userAccount, String message) {
        List<WebSocketByJavax> targets = new ArrayList<>();
        
        // 先收集目标客户端，减少同步块范围
        synchronized(clients) {
            for (WebSocketByJavax item : clients.values()) { 
                String[] clientInfo = item.userId.split("_");
                if(clientInfo != null && clientInfo.length == 3 && 
                   userAccount.equals(clientInfo[1]) && 
                   item.session != null && item.session.isOpen()) {
                    targets.add(item);
                }
            }
        }
        
        // 然后发送消息
        for (WebSocketByJavax target : targets) {
            synchronized(target.session) {
                try {
                    target.session.getBasicRemote().sendText(message);
                } catch (Exception e) {
                    logger.error("发送消息失败，用户: {}", target.userId, e);
                    System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss") + ":" +"webSocket信息推送失败:"+message.length());
                    // 可以考虑移除无效连接
                }
            }
        }
    }
    
    
    public void sendMessageAll(String message) throws IOException {
		for (WebSocketByJavax item : clients.values()) {
			synchronized(item.session){
				try{
	       			if(item.session!=null && item.session.isOpen()){
	       				item.session.getBasicRemote().sendText(message);//getBasicRemote同步发送 getAsyncRemote异步发送
	    			}
	        	}catch(Exception e){
	        		e.printStackTrace();
	        		System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss") + ":" +"webSocket信息推送失败:"+message.length());
	        	}
			}
		}
    }
    
    //添加连接清理机制
    public static void cleanupDeadConnections() {
        synchronized(clients) {
            clients.entrySet().removeIf(entry -> {
                WebSocketByJavax client = entry.getValue();
                if (client.session == null || !client.session.isOpen()) {
                    subOnlineCount();
                    return true;
                }
                return false;
            });
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
    
    public static class SendMessageThread extends Thread{
    	private Session session; 
    	private String message;
		public SendMessageThread(Session session, String message) {
			super();
			this.session = session;
			this.message = message;
		}
		public void run(){
			try {
				if(session.isOpen()){
					session.getBasicRemote().sendText(message);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}
