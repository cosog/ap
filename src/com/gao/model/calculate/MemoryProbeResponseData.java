package com.gao.model.calculate;

public class MemoryProbeResponseData {

	private float Total;

    private float Free;

    private float Buffers;

    private float Cached;

    private float Used;

    private float UsedPercent;

    public void setTotal(float Total){
        this.Total = Total;
    }
    public float getTotal(){
        return this.Total;
    }
    public void setFree(float Free){
        this.Free = Free;
    }
    public float getFree(){
        return this.Free;
    }
    public void setBuffers(float Buffers){
        this.Buffers = Buffers;
    }
    public float getBuffers(){
        return this.Buffers;
    }
    public void setCached(float Cached){
        this.Cached = Cached;
    }
    public float getCached(){
        return this.Cached;
    }
    public void setUsed(float Used){
        this.Used = Used;
    }
    public float getUsed(){
        return this.Used;
    }
    public void setUsedPercent(float UsedPercent){
        this.UsedPercent = UsedPercent;
    }
    public float getUsedPercent(){
        return this.UsedPercent;
    }
}
