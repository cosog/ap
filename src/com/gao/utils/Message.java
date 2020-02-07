package com.gao.utils;

public class Message {
	private String errorMsg;
	private boolean result;
	
	public Message(){}
	
	public Message(String errorMsg){
		super();
		this.errorMsg = errorMsg;
	}
	
	public Message(String errorMsg, boolean result) {
		super();
		this.errorMsg = errorMsg;
		this.result = result;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	
}
