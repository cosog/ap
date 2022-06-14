package com.cosog.model.calculate;

import java.util.List;

public class PumpingPRTFData {

	private List<EveryStroke> List;

    public void setList(List<EveryStroke> List){
        this.List = List;
    }
    public List<EveryStroke> getList(){
        return this.List;
    }
	
	public static class EveryStroke
	{
	    private float Stroke;

	    private List<PRTF> PRTF;

	    public void setStroke(float Stroke){
	        this.Stroke = Stroke;
	    }
	    public float getStroke(){
	        return this.Stroke;
	    }
	    public void setPRTF(List<PRTF> PRTF){
	        this.PRTF = PRTF;
	    }
	    public List<PRTF> getPRTF(){
	        return this.PRTF;
	    }
	}
	
	public static class PRTF
	{
	    private float CrankAngle;

	    private float PR;

	    private float TF;

	    public void setCrankAngle(float CrankAngle){
	        this.CrankAngle = CrankAngle;
	    }
	    public float getCrankAngle(){
	        return this.CrankAngle;
	    }
	    public void setPR(float PR){
	        this.PR = PR;
	    }
	    public float getPR(){
	        return this.PR;
	    }
	    public void setTF(float TF){
	        this.TF = TF;
	    }
	    public float getTF(){
	        return this.TF;
	    }
	}
}
