package com.cosog.thread.calculate;

public class ViThreadPoolManager  extends ThreadPoolManager{
	
	private static ThreadPoolManager threadPool = null;
	
	public synchronized static ThreadPoolManager getInstance() {
		if(threadPool == null) {
			threadPool = new ViThreadPoolManager();
		}
		return threadPool;
	}
	
	@Override
	protected String getThreadPoolName() {
		return "com.tool.me.vi";
	}

	@Override
	protected int corePoolSize() {
		return 10;
	}

	@Override
	protected int maximumPoolSize() {
		return 20;
	}
}
