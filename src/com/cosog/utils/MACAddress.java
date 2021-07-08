package com.cosog.utils;

import java.io.BufferedReader;   
import java.io.IOException;   
import java.io.InputStreamReader;   

public class MACAddress {

	   /**  
     * 获取当前操作系统名称.  
     * return 操作系统名称 例如:windows xp,linux 等.  
     */  
    private String getOSName() {   
        return System.getProperty("os.name").toLowerCase();   
    }   
       
    /**  
     * 获取unix网卡的mac地址.  
     * 非windows的系统默认调用本方法获取.如果有特殊系统请继续扩充新的取mac地址方法.  
     * @return mac地址  
     */  
    private String getUnixMACAddress() {   
        String mac = null;   
        BufferedReader bufferedReader = null;   
        Process process = null;   
        try {   
            process = Runtime.getRuntime().exec("ifconfig eth0");// linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息   
            bufferedReader = new BufferedReader(new InputStreamReader(process   
                    .getInputStream()));   
            String line = null;   
            int index = -1;   
            while ((line = bufferedReader.readLine()) != null) {   
                index = line.toLowerCase().indexOf("hwaddr");// 寻找标示字符串[hwaddr]   
                if (index >= 0) {// 找到了   
					mac = line.substring(index +"hwaddr".length()+ 1).trim();//  取出mac地址并去除2边空格   
                    break;   
                }   
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (bufferedReader != null) {   
                    bufferedReader.close();   
                }   
            } catch (IOException e1) {   
                e1.printStackTrace();   
            }   
            bufferedReader = null;   
            process = null;   
        }   
  
        return mac;   
    }   
  
    /**  
     * 获取widnows网卡的mac地址.  
     * @return mac地址  
     */  
    private String getWindowsMACAddress() {   
        String mac = null;   
        BufferedReader bufferedReader = null;   
        Process process = null;   
        try {   
            process = Runtime.getRuntime().exec("ipconfig /all");// windows下的命令，显示信息中包含有mac地址信息   
            bufferedReader = new BufferedReader(new InputStreamReader(process   
                    .getInputStream()));   
            String line = null;   
            int index = -1;   
            while ((line = bufferedReader.readLine()) != null) {  
            	//System.out.println(line);
                index = line.toLowerCase().indexOf("physical address");// 寻找标示字符串[physical address]
                if(index < 0){
                	index = line.toLowerCase().indexOf("物理地址");
                }
                if (index >= 0) {// 找到了   
                    index = line.indexOf(":");// 寻找":"的位置   
                    if (index>=0) {   
                        mac = line.substring(index + 1).trim();//  取出mac地址并去除2边空格   
                    }   
                    break;   
                }   
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (bufferedReader != null) {   
                    bufferedReader.close();   
                }   
            } catch (IOException e1) {   
                e1.printStackTrace();   
            }   
            bufferedReader = null;   
            process = null;   
        }   
  
        return mac;   
    }   
  
    public String getMACAddress() { 
    	String mac = "";
        String os = getOSName();   
        System.out.println(os);   
        if(os.startsWith("windows")){   
            //本地是windows   
            mac = getWindowsMACAddress();   
        }else{   
            //本地是非windows系统 一般就是unix   
            mac = getUnixMACAddress();   
        }   
        return mac;
    }
    /**  
     * 测试用的main方法.  
     *   
     * @param argc  
     *            运行参数.  
     */  
    public static void main(String[] argc) { 
    	MACAddress address = new MACAddress();
    	System.out.println(address.getMACAddress());
    }   


}
