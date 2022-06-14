package com.cosog.websocket.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.cosog.utils.StringManagerUtils;

public class SpringWebSocketHandler implements WebSocketHandler {
//	private static final ArrayList<WebSocketSession> clients = new ArrayList<WebSocketSession>();
	private static Logger logger = Logger.getLogger(SpringWebSocketHandler.class); 
	private static final Map<String, WebSocketSession> clients;  //Map来存储WebSocketSession，key用USER_ID 即在线用户列表
	 
    //用户标识
    private static final String USER_ID = "WEBSOCKET_USERID";   //对应监听器从的key
 
 
    static {
    	clients =  new HashMap<String, WebSocketSession>();
    }
 
    public SpringWebSocketHandler() {}
	
	/**
     * 关闭连接时触发
     */
	@Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String userId= (String) session.getAttributes().get(USER_ID);
        StringManagerUtils.printLog("用户"+userId+"已退出！");
        clients.remove(userId);
        StringManagerUtils.printLog("剩余在线用户"+clients.size());
    }

	/**
	 * 初次连接成功
     * 连接成功时候，会触发页面上onopen方法
     */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("connect to the websocket succcess ... ...");  
        StringManagerUtils.printLog("afterConnectionEstablished");  
        String userId = (String) session.getAttributes().get(USER_ID);
        clients.put(userId,session);
        StringManagerUtils.printLog("接收到客户端连接:"+userId);
        StringManagerUtils.printLog("当前线上用户数量:"+clients.size());
        //这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        TextMessage returnMessage = new TextMessage("Connect successfully!");
        session.sendMessage(returnMessage);
	}
	
	/**
	 * 接收消息处理信息
     * js调用websocket.send时候，会调用该方法
     */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> arg1) throws Exception {
		// TODO Auto-generated method stub
		String message=arg1.getPayload()+"";
		StringManagerUtils.printLog("服务器收到消息："+message);
		
		if(message.startsWith("#anyone#")){ //单发某人
			 
//            sendMessageToUser((String)session.getAttributes().get(USER_ID), new TextMessage("服务器单发：" +message)) ;

       }else if(message.startsWith("#everyone#")){

//            sendMessageToUsers(new TextMessage("服务器群发：" +message));

       }else{

       }
	}


	@Override
	public void handleTransportError(WebSocketSession session, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub
		StringManagerUtils.printLog("handleTransportError");  
        logger.debug("websocket connection closed");  
        if(session.isOpen()){
        	session.close();
        }
        String userId= (String) session.getAttributes().get(USER_ID);
        StringManagerUtils.printLog(userId+"传输出现异常，关闭websocket连接... ");
        clients.remove(userId);
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}
    
    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (String id : clients.keySet()) {
        	synchronized (clients.get(id)) {
        		try {
                    if (clients.get(id).isOpen()) {
                    	clients.get(id).sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        	}
        }
    }
    
    /**
     * 给某个用户发送消息
     *
     * @param userId
     * @param message
     */
    public void sendMessageToUser(String userId, TextMessage message) {
        for (String id : clients.keySet()) {
            if (id.equals(userId)) {
            	synchronized (clients.get(id)) {
                	try {
                        if (clients.get(id).isOpen()) {
                        	clients.get(id).sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            	}

            }
        }
    }


    /**
     * 同同一模块的用户发送消息
     *
     * @param userId
     * @param message
     */
    public void sendMessageToUserByModule(String userId, TextMessage message) {
        for (String id : clients.keySet()) {
            if (id.contains(userId)) {
            	synchronized (clients.get(id)) {
                	try {
                        if (clients.get(id).isOpen()) {
                        	clients.get(id).sendMessage(message);
//                        	StringManagerUtils.printLog("WebSocket服务端向"+userId+"客户端发送数据："+message.toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    break;
            	}
            }
        }
    }
}
