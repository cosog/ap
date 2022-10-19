package com.cosog.model;

import java.io.Serializable;

public class AccessToken  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Data data;
	
	private String code;
	
	private String msg;
	
	public static class Data implements Serializable{
		private static final long serialVersionUID = 1L;
		private String accessToken;
		private long expireTime;
		public String getAccessToken() {
			return accessToken;
		}
		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}
		public long getExpireTime() {
			return expireTime;
		}
		public void setExpireTime(long expireTime) {
			this.expireTime = expireTime;
		}
		
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
