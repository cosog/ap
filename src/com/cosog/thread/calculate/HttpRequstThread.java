package com.cosog.thread.calculate;

import com.cosog.utils.StringManagerUtils;

public class HttpRequstThread implements Runnable{

	private String url;
	private String param;
	private String encoding;
	private int connectTimeout;
	private int readTimeout;

	public HttpRequstThread(String url, String param, String encoding, int connectTimeout, int readTimeout) {
		super();
		this.url = url;
		this.param = param;
		this.encoding = encoding;
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}

	public void run(){
		StringManagerUtils.sendPostMethod(url, param,encoding,connectTimeout,readTimeout);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
}
