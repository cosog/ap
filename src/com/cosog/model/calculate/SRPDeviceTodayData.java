package com.cosog.model.calculate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cosog.model.calculate.SRPCalculateResponseData;
import com.cosog.model.drive.AcquisitionItemInfo;

public class SRPDeviceTodayData implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private List<AcquisitionItemInfo> acquisitionItemInfoList;
	private List<SRPCalculateResponseData> SRPCalculateList;

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

	public List<SRPCalculateResponseData> getSRPCalculateList() {
		if(SRPCalculateList==null){
			this.setSRPCalculateList(new ArrayList<SRPCalculateResponseData>());
		}
		return this.SRPCalculateList;
	}

	public void setSRPCalculateList(List<SRPCalculateResponseData> SRPCalculateList) {
		this.SRPCalculateList = SRPCalculateList;
	}

}
