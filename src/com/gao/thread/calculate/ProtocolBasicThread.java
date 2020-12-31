package com.gao.thread.calculate;

public class ProtocolBasicThread extends Thread{
	public int threadId;
	public boolean isExit=false;
	public int getThreadId() {
		return threadId;
	}
	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}
	public boolean isExit() {
		return isExit;
	}
	public void setExit(boolean isExit) {
		this.isExit = isExit;
	}
	
}
