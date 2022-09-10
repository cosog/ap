package com.cosog.model.calculate;

import java.util.List;

public class AppRunStatusProbeResonanceData {

	private String Ver;
	
	private int LicenseNumber;
	
	private int IDSlaveNumber;
	
	private int IPPortSlaveNumber;

    private List<String> URL;

    public void setVer(String Ver){
        this.Ver = Ver;
    }
    public String getVer(){
        return this.Ver;
    }
    public void setURL(List<String> URL){
        this.URL = URL;
    }
    public List<String> getURL(){
        return this.URL;
    }
	public int getLicenseNumber() {
		return LicenseNumber;
	}
	public void setLicenseNumber(int licenseNumber) {
		LicenseNumber = licenseNumber;
	}
	public int getIDSlaveNumber() {
		return IDSlaveNumber;
	}
	public void setIDSlaveNumber(int iDSlaveNumber) {
		IDSlaveNumber = iDSlaveNumber;
	}
	public int getIPPortSlaveNumber() {
		return IPPortSlaveNumber;
	}
	public void setIPPortSlaveNumber(int iPPortSlaveNumber) {
		IPPortSlaveNumber = iPPortSlaveNumber;
	}
}
