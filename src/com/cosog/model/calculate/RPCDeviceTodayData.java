package com.cosog.model.calculate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cosog.model.calculate.RPCCalculateResponseData;
import com.cosog.model.drive.AcquisitionItemInfo;

public class RPCDeviceTodayData implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private List<AcquisitionItemInfo> acquisitionItemInfoList;
	private List<RPCCalculateResponseData> RPCCalculateList;

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

	public List<RPCCalculateResponseData> getRPCCalculateList() {
		if(RPCCalculateList==null){
			this.setRPCCalculateList(new ArrayList<RPCCalculateResponseData>());
		}
		return this.RPCCalculateList;
	}

	public void setRPCCalculateList(List<RPCCalculateResponseData> rPCCalculateList) {
		RPCCalculateList = rPCCalculateList;
	}

}
