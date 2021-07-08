package com.cosog.thread.calculate;

import com.cosog.utils.StringManagerUtils;

public class SendHttpRequestThread extends Thread{

	private int threadId;
	private String params;
	private String CalculateHttpURL;

	public SendHttpRequestThread(int threadId, String params, String calculateHttpURL) {
		super();
		this.threadId = threadId;
		this.params = params;
		CalculateHttpURL = calculateHttpURL;
	}


	public void run(){
		StringManagerUtils.sendPostMethod(CalculateHttpURL, params,"utf-8");
	}


	public int getThreadId() {
		return threadId;
	}


	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}


	public String getParams() {
		return params;
	}


	public void setParams(String params) {
		this.params = params;
	}


	public String getCalculateHttpURL() {
		return CalculateHttpURL;
	}


	public void setCalculateHttpURL(String calculateHttpURL) {
		CalculateHttpURL = calculateHttpURL;
	}
	
	
	
	
	
}
