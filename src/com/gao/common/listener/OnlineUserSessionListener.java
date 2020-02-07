package com.gao.common.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineUserSessionListener implements HttpSessionListener {  
    
    private long onlineCount;  
  
    public void sessionCreated(HttpSessionEvent event) {  
        // TODO Auto-generated method stub  
    	System.out.println("新的会话开始创建~!");
        this.onlineCount=this.onlineCount+1;  
               //保存在application作用域  
        event.getSession().getServletContext().setAttribute("onlineCount", onlineCount);  
    }  
  
    public void sessionDestroyed(HttpSessionEvent event) {  
        // TODO Auto-generated method stub  
    	System.out.println("会话开始销毁~!");
        this.onlineCount=this.onlineCount-1;  
        event.getSession().getServletContext().setAttribute("onlineCount", onlineCount);  
    }  
  
}  