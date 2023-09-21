package com.cosog.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil implements Serializable{
    
    private static final long serialVersionUID = -1149678082569464779L;

    //Redis服务器IP
    private static String addr=Config.getInstance().configFile.getAp().getRedis().getAddr();
    
    //Redis的端口号
    private static int port=Config.getInstance().configFile.getAp().getRedis().getPort();
    
    //访问密码
    private static String auth=Config.getInstance().configFile.getAp().getRedis().getPassword();
    
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int maxActive=Config.getInstance().configFile.getAp().getRedis().getMaxActive();
    
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int maxIdle=Config.getInstance().configFile.getAp().getRedis().getMaxIdle();
    
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int maxWait=Config.getInstance().configFile.getAp().getRedis().getMaxWait();
    
    private static int timeOut=Config.getInstance().configFile.getAp().getRedis().getTimeOut();
    
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean testOnBorrow=true;
    
    public static Jedis jedis;//非切片额客户端连接
    
    public static JedisPool jedisPool;//非切片连接池
    
    static{
    	try {
			afterPropertiesSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private static void initialPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActive); 
        config.setMaxIdle(maxIdle); 
        config.setMaxWaitMillis(maxWait); 
        config.setTestOnBorrow(testOnBorrow);
        if(StringManagerUtils.isNotNull(auth)){
        	jedisPool = new JedisPool(config, addr, port,timeOut,auth);
        }else{
        	jedisPool = new JedisPool(config, addr, port,timeOut);
        }
        
    }

    public  static void afterPropertiesSet(){
        // TODO Auto-generated method stub
        initialPool(); 
        try {
             jedis = jedisPool.getResource();
        } catch (Exception e) {
            System.out.println("连接jedisPool失败!");
        } finally{
        	if(jedis!=null){
				jedis.close();
			}
        }
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }
}
