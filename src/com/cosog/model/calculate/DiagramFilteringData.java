package com.cosog.model.calculate;

import java.io.Serializable;

public class DiagramFilteringData  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int FTimes;
	
	private int ITimes;
	
	private int WattTimes;

	public DiagramFilteringData(int fTimes, int iTimes, int wattTimes) {
		super();
		FTimes = fTimes;
		ITimes = iTimes;
		WattTimes = wattTimes;
	}

	public int getFTimes() {
		return FTimes;
	}

	public void setFTimes(int fTimes) {
		FTimes = fTimes;
	}

	public int getITimes() {
		return ITimes;
	}

	public void setITimes(int iTimes) {
		ITimes = iTimes;
	}

	public int getWattTimes() {
		return WattTimes;
	}

	public void setWattTimes(int wattTimes) {
		WattTimes = wattTimes;
	}
	
	
}
