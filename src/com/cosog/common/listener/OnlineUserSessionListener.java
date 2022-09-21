package com.cosog.common.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.cosog.utils.SessionLockHelper;
import com.cosog.utils.StringManagerUtils;

public class OnlineUserSessionListener implements HttpSessionListener {  
    
    private long onlineCount;  
  
    public void sessionCreated(HttpSessionEvent event) {  
        // TODO Auto-generated method stub  
    	StringManagerUtils.printLog("新的会话开始创建~!");
        this.onlineCount=this.onlineCount+1;  
               //保存在application作用域  
        event.getSession().getServletContext().setAttribute("onlineCount", onlineCount);  
    }  
  
    public void sessionDestroyed(HttpSessionEvent event) {  
        // TODO Auto-generated method stub  
    	StringManagerUtils.printLog("会话开始销毁~!");
        this.onlineCount=this.onlineCount-1;
        event.getSession().getServletContext().setAttribute("onlineCount", onlineCount);  
//        SessionLockHelper.moveSession(event.getSession());
    }  
  
}  