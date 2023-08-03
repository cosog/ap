package com.cosog.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import com.cosog.model.User;

public class SessionLockHelper {
	public static Map<String,HttpSession> map=new HashMap<String,HttpSession>();
	
	public static void putSession(HttpSession session){
		String key=session.getId();
		map.put(key, session);
		System.out.println("添加session:"+key+"，当前用户数:"+map.size());
	}
	
	public static void moveSession(HttpSession session){
//		User user=(User) session.getAttribute("userLogin");
		map.remove(session.getId());
		System.out.println("移除session:"+session.getId()+"，当前用户数:"+map.size());
	}
	
	public static void destroySession(String key){
		HttpSession session=map.get(key);
		if(session!=null && isValid(session)){
			session.invalidate();
		}
		moveSession(session);
	}
	
//	public static void destroySessionByUserId(String userId){
//		Iterator<Entry<String, HttpSession>> entries = map.entrySet().iterator();
//		while (entries.hasNext()) {
//			Entry<String, HttpSession> entry = entries.next();
//			User user=(User) entry.getValue().getAttribute("userLogin");
//			if(user!=null&&userId.equals(user.getUserId())){
//				entry.getValue().invalidate();
//				System.out.println("销毁session:"+entry.getValue().getId()+"，当前用户数:"+map.size());
//			}
//		}
//	}
	
	public static void destroySessionByUserNo(int userNo){
		Iterator<Entry<String, HttpSession>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, HttpSession> entry = entries.next();
			if(entry.getValue()!=null && isValid(entry.getValue()) ){
				try{
					User user=(User) entry.getValue().getAttribute("userLogin");
					if(user!=null&&userNo==user.getUserNo()){
						entry.getValue().invalidate();
						entries.remove();
						System.out.println("销毁session:"+entry.getValue().getId()+"，当前用户数:"+map.size());
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}else{
				entries.remove();
				System.out.println("销毁session:"+entry.getValue().getId()+"，当前用户数:"+map.size());
			}
		}
	}
	
	public static boolean isValid(HttpSession session){
		boolean valid=false;
		try{
			long lastAccessedTime = session.getLastAccessedTime();
			long currentTime = System.currentTimeMillis();
			long sessionTimeout = session.getMaxInactiveInterval() * 1000;
			if (sessionTimeout>0 && (currentTime - lastAccessedTime > sessionTimeout) ) {
				session.invalidate();
				valid=false;
			}else{
				valid=true;
			}
		}catch (Exception e) {
			valid=false;
		}
		return  valid;
	}
}
