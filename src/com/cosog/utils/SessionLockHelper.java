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
		if(session!=null){
			session.invalidate();
			System.out.println("销毁session:"+session.getId()+"，当前用户数:"+map.size());
		}
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
			User user=(User) entry.getValue().getAttribute("userLogin");
			if(user!=null&&userNo==user.getUserNo()){
				entry.getValue().invalidate();
				System.out.println("销毁session:"+entry.getValue().getId()+"，当前用户数:"+map.size());
			}
		}
	}
}
