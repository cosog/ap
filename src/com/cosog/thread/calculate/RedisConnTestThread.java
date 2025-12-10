package com.cosog.thread.calculate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;

import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.StringManagerUtils;
import com.cosog.websocket.config.WebSocketByJavax;

import redis.clients.jedis.Jedis;

public class RedisConnTestThread extends Thread{
	public int value;
	public RedisConnTestThread(int value) {
		super();
		this.value = value;
	}

	public void run(){
		test2();
	}
	
	public void test2(){
		Jedis jedis=null;
		Set<byte[]> keySet=test1();
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String key="DeviceRealtimeAcqData_1";
//			Set<byte[]> keySet=test1(jedis);
			keySet=jedis.hkeys(key.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public Set<byte[]> test1(){
		Jedis jedis=null;
		Set<byte[]> keySet=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String key="DeviceRealtimeAcqData_1";
			keySet=jedis.hkeys(key.getBytes());
			StringManagerUtils.printLog("test1_DeviceRealtimeAcqData_1_"+value,0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return keySet;
	}
	
	public Set<byte[]> test1(Jedis jedis){
		
		Set<byte[]> keySet=null;
		try {
			String key="DeviceRealtimeAcqData_1";
			keySet=jedis.hkeys(key.getBytes());
			StringManagerUtils.printLog("test1_DeviceRealtimeAcqData_1_"+value,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keySet;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	
}
