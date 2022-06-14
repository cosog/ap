package com.cosog.model.calculate;

import java.util.List;

public class DiskProbeResponseData {

	private String Part;

    private float Total;
    
    private float Used;
    
    private float Free;
    
    private float UsedPercent;

	public String getPart() {
		return Part;
	}

	public void setPart(String part) {
		Part = part;
	}

	public float getTotal() {
		return Total;
	}

	public void setTotal(float total) {
		Total = total;
	}

	public float getUsed() {
		return Used;
	}

	public void setUsed(float used) {
		Used = used;
	}

	public float getFree() {
		return Free;
	}

	public void setFree(float free) {
		Free = free;
	}

	public float getUsedPercent() {
		return UsedPercent;
	}

	public void setUsedPercent(float usedPercent) {
		UsedPercent = usedPercent;
	}
}
