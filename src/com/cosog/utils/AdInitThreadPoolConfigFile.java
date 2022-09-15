package com.cosog.utils;

public class AdInitThreadPoolConfigFile {
	
	private ThreadPool idAndIpPort;
	
	private ThreadPool dataSynchronization;
	
	public static class ThreadPool
	{

	    private int corePoolSize;

	    private int maximumPoolSize;

	    private int keepAliveTime;

	    private int wattingCount;

		public int getCorePoolSize() {
			return corePoolSize;
		}

		public void setCorePoolSize(int corePoolSize) {
			this.corePoolSize = corePoolSize;
		}

		public int getMaximumPoolSize() {
			return maximumPoolSize;
		}

		public void setMaximumPoolSize(int maximumPoolSize) {
			this.maximumPoolSize = maximumPoolSize;
		}

		public int getKeepAliveTime() {
			return keepAliveTime;
		}

		public void setKeepAliveTime(int keepAliveTime) {
			this.keepAliveTime = keepAliveTime;
		}

		public int getWattingCount() {
			return wattingCount;
		}

		public void setWattingCount(int wattingCount) {
			this.wattingCount = wattingCount;
		}
	    
	}



	public ThreadPool getIdAndIpPort() {
		return idAndIpPort;
	}



	public void setIdAndIpPort(ThreadPool idAndIpPort) {
		this.idAndIpPort = idAndIpPort;
	}



	public ThreadPool getDataSynchronization() {
		return dataSynchronization;
	}



	public void setDataSynchronization(ThreadPool dataSynchronization) {
		this.dataSynchronization = dataSynchronization;
	}
	
	
}
