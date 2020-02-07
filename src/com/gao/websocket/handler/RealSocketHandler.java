package com.gao.websocket.handler;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class RealSocketHandler implements WebSocketHandler {
	private static final ArrayList<WebSocketSession> clients = new ArrayList<WebSocketSession>();
	private static Logger logger = Logger.getLogger(RealSocketHandler.class); 

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("websocket connection closed");  
        System.out.println("afterConnectionClosed");  
        clients.remove(session); 
	}

	//初次连接成功
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("connect to the websocket succcess ... ...");  
        System.out.println("afterConnectionEstablished");  
        clients.add(session); 
        String userName = (String) session.getAttributes().get("session_username"); 
        if(userName!=null){
        	int count=5;
        	session.sendMessage(new TextMessage(count+""));
        }
	}

	//接收消息处理信息
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> arg1) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("handleMessage");
		sendMessageToUsers(new TextMessage(arg1.getPayload()+""));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("handleTransportError");  
        logger.debug("websocket connection closed");  
        if(session.isOpen()){
        	session.close();
        }
        clients.remove(session);  
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
     *             
     */  
    public void sendMessageToUsers(TextMessage message) {  
        for (WebSocketSession client : clients) {  
            try {  
                if (client.isOpen()) {  
                	client.sendMessage(message);  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /** 
     * 给某个用户发送消息 
     * 
     * @param userName 
     * @param message 
     */  
    public void sendMessageToUser(String userName, TextMessage message) {  
        for (WebSocketSession client : clients) {  
            if (client.getAttributes().get("websocket_username").equals(userName)) {  
                try {  
                    if (client.isOpen()) {  
                    	client.sendMessage(message);  
                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                break;  
            }  
        }  
    }  

}
