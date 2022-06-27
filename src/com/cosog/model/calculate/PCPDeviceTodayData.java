package com.cosog.model.calculate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cosog.model.calculate.PCPCalculateRequestData.*;
import com.cosog.model.drive.AcquisitionItemInfo;

public class PCPDeviceTodayData implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private List<AcquisitionItemInfo> acquisitionItemInfoList;
	private List<PCPCalculateResponseData> PCPCalculateList;
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public List<AcquisitionItemInfo> getAcquisitionItemInfoList() {
		return acquisitionItemInfoList;
	}


	public void setAcquisitionItemInfoList(List<AcquisitionItemInfo> acquisitionItemInfoList) {
		this.acquisitionItemInfoList = acquisitionItemInfoList;
	}

	public List<PCPCalculateResponseData> getPCPCalculateList() {
		if(PCPCalculateList==null){
			this.setPCPCalculateList(new ArrayList<PCPCalculateResponseData>());
		}
		return this.PCPCalculateList;
	}


	public void setPCPCalculateList(List<PCPCalculateResponseData> pCPCalculateList) {
		PCPCalculateList = pCPCalculateList;
	}
}
