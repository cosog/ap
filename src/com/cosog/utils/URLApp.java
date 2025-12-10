package com.cosog.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class URLApp
{
   @SuppressWarnings("unused")
void display()
   {
    byte buf[]=new byte[1000];
    try
   {
    System.out.print("请输入文件的URL地址:");
    //读取用户输入的URL
    //InputStream consoleis=System.in;
    //int count=consoleis.read(buf);
    //String addr=new String(buf,0,count);
    //将用户输入的URL字符串传入URL类对象
    String addr="http://192.168.1.168/ap/userLoginManagerController/dynUploadLogin?userId=tyjh&userPwd=tyjh1010";
    URL url=new URL (addr);
    //创建URLConnection对象，用URL的openConnection方法将连接通过返回给URLConnection的对象
      //实际上URL的openConnection的返回值就是一个URLConnection
    URLConnection c = url.openConnection(); //*
    //用URLConnection的connect()方法建立连接
    c.connect();                            //*
    // 显示该连接的相关信息，这些都是URLConnection的方法
    StringManagerUtils.printLog("内容类型: "+c.getContentType(),0);
    StringManagerUtils.printLog("内容长度: "+c.getContentLength(),0);
    StringManagerUtils.printLog("创建日期: "+new Date(c.getDate()),0);
    StringManagerUtils.printLog("最后修改日期: "+new Date(c.getLastModified()),0);
    StringManagerUtils.printLog("终止日期: "+new Date(c.getExpiration()),0);

    InputStream is=c.getInputStream();
    InputStreamReader isr=new InputStreamReader(is);
    BufferedReader br=new BufferedReader(isr);
    String str=null;
    while((str=br.readLine())!=null)
    {
      StringManagerUtils.printLog(str,0);
    }
      
      
   }
   catch(IOException e)
   {
	   String a="a";
	   String b="b";
	   if(Integer.parseInt(a)>Integer.parseInt(b)){
		   
	   }
	   
	   StringManagerUtils.printLog(e.getMessage(),2);
     }
   }
   public static void main(String[] args)
   {
   URLApp app=new URLApp();
        app.display();
   }
}