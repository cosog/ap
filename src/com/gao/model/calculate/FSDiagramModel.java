package com.gao.model.calculate;

import java.util.List;

public class FSDiagramModel {
	private String wellName;

    private String acqTime;

    private float stroke;

    private float spm;

    private List<Float> S;

    private List<Float> F;

    private List<Float> Watt;

    private List<Float> I;

	public String getWellName() {
		return wellName;
	}

	public void setWellName(String wellName) {
		this.wellName = wellName;
	}

	public String getAcqTime() {
		return acqTime;
	}

	public void setAcqTime(String acqTime) {
		this.acqTime = acqTime;
	}

	public float getStroke() {
		return stroke;
	}

	public void setStroke(float stroke) {
		this.stroke = stroke;
	}

	public float getSpm() {
		return spm;
	}

	public void setSpm(float spm) {
		this.spm = spm;
	}

	public List<Float> getS() {
		return S;
	}

	public void setS(List<Float> s) {
		S = s;
	}

	public List<Float> getF() {
		return F;
	}

	public void setF(List<Float> f) {
		F = f;
	}

	public List<Float> getWatt() {
		return Watt;
	}

	public void setWatt(List<Float> watt) {
		Watt = watt;
	}

	public List<Float> getI() {
		return I;
	}

	public void setI(List<Float> i) {
		I = i;
	}

    
}
