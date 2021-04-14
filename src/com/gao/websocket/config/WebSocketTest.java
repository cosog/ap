package com.gao.websocket.config;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value="/websocketTest/{userId}")
public class WebSocketTest {
	private Logger logger = LoggerFactory.getLogger(WebSocketTest.class);
    private static String userId;
    //连接时执行
    @OnOpen
    public void onOpen(@PathParam("userId") String userId,Session session) throws IOException{
        this.userId = userId;
        logger.debug("新连接：{}",userId);
        session.getBasicRemote().sendText("收到 "+this.userId+" 的连接.");
    }
    
    //关闭时执行
    @SuppressWarnings("static-access")
	@OnClose
    public void onClose(){
        logger.debug("连接：{} 关闭",this.userId);
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
    public void onError(Session session, Throwable error){
        logger.debug("用户id为：{}的连接发送错误",this.userId);
        error.printStackTrace();
    }
}
