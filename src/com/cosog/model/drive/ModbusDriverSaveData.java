package com.cosog.model.drive;

import java.util.List;

import com.cosog.utils.StringManagerUtils;

import java.util.ArrayList;

public class ModbusDriverSaveData {

	private String ProtocolName;
	
	private String ProtocolCode;
	
	private String ProtocolType;
	
	private String SignInPrefix="";
	
	private String SignInSuffix="";
	
	private String HeartbeatPrefix="";
	
	private String HeartbeatSuffix="";
	
	private List<String> delidslist;

    private List<DataConfig> DataConfig;
    
    public void setProtocolName(String ProtocolName){
        this.ProtocolName = ProtocolName;
    }
    public String getProtocolName(){
        return this.ProtocolName;
    }
    
    public void setDataConfig(List<DataConfig> DataConfig){
        this.DataConfig = DataConfig;
    }
    public List<DataConfig> getDataConfig(){
        return this.DataConfig;
    }
    
    public void dataFiltering(){
    	if(!StringManagerUtils.isNotNull(this.getProtocolName())){
    		this.setProtocolName("");
    	}
    	if(!StringManagerUtils.isNotNull(this.getProtocolType())){
    		this.setProtocolType("");
    	}
    	
    	if(!StringManagerUtils.isNotNull(this.getHeartbeatPrefix())){
    		this.setHeartbeatPrefix("");
    	}
    	if(!StringManagerUtils.isNotNull(this.getHeartbeatSuffix())){
    		this.setHeartbeatSuffix("");
    	}
    	if(!StringManagerUtils.isNotNull(this.getSignInPrefix())){
    		this.setSignInPrefix("");
    	}
    	if(!StringManagerUtils.isNotNull(this.getSignInSuffix())){
    		this.setSignInSuffix("");
    	}
    	if(this.getDataConfig()!=null){
    		for(int i=0;i<this.getDataConfig().size();i++){
    			if(!StringManagerUtils.isNotNull(this.getDataConfig().get(i).getTitle())){
    				this.getDataConfig().remove(i);
    			}else{
    				if(!StringManagerUtils.isNotNull(this.getDataConfig().get(i).getTitle())){
        				this.getDataConfig().get(i).setTitle("");
        	    	}
        			if(!StringManagerUtils.isNotNull(this.getDataConfig().get(i).getStoreDataType())){
        				this.getDataConfig().get(i).setStoreDataType("");
        	    	}
        			if(!StringManagerUtils.isNotNull(this.getDataConfig().get(i).getIFDataType())){
        				this.getDataConfig().get(i).setIFDataType("");
        	    	}
        			if(!StringManagerUtils.isNotNull(this.getDataConfig().get(i).getRWType())){
        				this.getDataConfig().get(i).setRWType("");
        	    	}
        			if(!StringManagerUtils.isNotNull(this.getDataConfig().get(i).getUnit())){
        				this.getDataConfig().get(i).setUnit("");
        	    	}
        			if(!StringManagerUtils.isNotNull(this.getDataConfig().get(i).getAcqMode())){
        				this.getDataConfig().get(i).setAcqMode("");
        	    	}
    			}
    		}
    	}
    }
	
	public static class DataConfig
	{
	    private String Name;
	    
	    private String Title;

	    private int Addr;

	    private int Quantity;

	    private String StoreDataType="";
	    
	    private String IFDataType="";
	    
	    private String Unit="";

	    private float Ratio=1;
	    
	    private String AcqMode="";
	    
	    private String RWType="";

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
		}

		public int getAddr() {
			return Addr;
		}

		public void setAddr(int addr) {
			Addr = addr;
		}

		public int getQuantity() {
			return Quantity;
		}

		public void setQuantity(int quantity) {
			Quantity = quantity;
		}

		public String getStoreDataType() {
			return StoreDataType;
		}

		public void setStoreDataType(String storeDataType) {
			StoreDataType = storeDataType;
		}

		public String getIFDataType() {
			return IFDataType;
		}

		public void setIFDataType(String IFDataType) {
			this.IFDataType = IFDataType;
		}

		public String getUnit() {
			return Unit;
		}

		public void setUnit(String unit) {
			Unit = unit;
		}

		public float getRatio() {
			return Ratio;
		}

		public void setRatio(float ratio) {
			Ratio = ratio;
		}

		public String getAcqMode() {
			return AcqMode;
		}

		public void setAcqMode(String acqMode) {
			AcqMode = acqMode;
		}

		public String getRWType() {
			return RWType;
		}

		public void setRWType(String rWType) {
			RWType = rWType;
		}

		public String getTitle() {
			return Title;
		}

		public void setTitle(String title) {
			Title = title;
		}
	}

	public String getProtocolType() {
		return ProtocolType;
	}
	public void setProtocolType(String protocolType) {
		ProtocolType = protocolType;
	}
	public String getHeartbeatPrefix() {
		return HeartbeatPrefix;
	}
	public void setHeartbeatPrefix(String heartbeatPrefix) {
		HeartbeatPrefix = heartbeatPrefix;
	}
	public String getHeartbeatSuffix() {
		return HeartbeatSuffix;
	}
	public void setHeartbeatSuffix(String heartbeatSuffix) {
		HeartbeatSuffix = heartbeatSuffix;
	}
	public String getSignInPrefix() {
		return SignInPrefix;
	}
	public void setSignInPrefix(String signInPrefix) {
		SignInPrefix = signInPrefix;
	}
	public String getSignInSuffix() {
		return SignInSuffix;
	}
	public void setSignInSuffix(String signInSuffix) {
		SignInSuffix = signInSuffix;
	}
	public String getProtocolCode() {
		return ProtocolCode;
	}
	public void setProtocolCode(String protocolCode) {
		ProtocolCode = protocolCode;
	}
	public List<String> getDelidslist() {
		return delidslist;
	}
	public void setDelidslist(List<String> delidslist) {
		this.delidslist = delidslist;
	}
	
}
