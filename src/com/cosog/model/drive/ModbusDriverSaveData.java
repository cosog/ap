package com.cosog.model.drive;

import java.util.List;

import com.cosog.model.drive.ModbusProtocolConfig.Items;
import com.cosog.model.drive.ModbusProtocolConfig.ItemsMeaning;
import com.cosog.utils.StringManagerUtils;

import java.util.ArrayList;

public class ModbusDriverSaveData {

	private String ProtocolName;
	
	private String ProtocolCode;
	
	private int DeviceType=0;
	
	private int Sort=0;
	
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
    	if(!StringManagerUtils.isNotNull(this.getSort()+"")){
    		this.setSort(0);
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
        			if(!StringManagerUtils.isNotNull(this.getDataConfig().get(i).getResolutionMode())){
        				this.getDataConfig().get(i).setResolutionMode("");
        	    	}
    			}
    		}
    	}
    }
    
    public static class ItemsMeaning implements Comparable<ItemsMeaning>
    {
    	private int Value;
    	
    	private String Meaning;

		public int getValue() {
			return Value;
		}

		public void setValue(int value) {
			Value = value;
		}

		public String getMeaning() {
			return Meaning;
		}

		public void setMeaning(String meaning) {
			Meaning = meaning;
		}
    	
		@Override
		public int compareTo(ItemsMeaning itemsMeaning) {     //重写Comparable接口的compareTo方法
			return this.Value-itemsMeaning.getValue();   // 根据值或者位升序排列，降序修改相减顺序即可
		}
    }
	
	public static class DataConfig implements Comparable<DataConfig>
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
	    
	    private String ResolutionMode;
	    
	    private List<ItemsMeaning> Meaning;

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

		public String getResolutionMode() {
			return ResolutionMode;
		}

		public void setResolutionMode(String resolutionMode) {
			ResolutionMode = resolutionMode;
		}

		public List<ItemsMeaning> getMeaning() {
			return Meaning;
		}

		public void setMeaning(List<ItemsMeaning> meaning) {
			Meaning = meaning;
		}
		
		@Override
		public int compareTo(DataConfig dataConfig) {     //重写Comparable接口的compareTo方法
			return this.Addr-dataConfig.getAddr();   // 根据地址升序排列，降序修改相减顺序即可
		}
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
	public int getSort() {
		return Sort;
	}
	public void setSort(int sort) {
		Sort = sort;
	}
	public int getDeviceType() {
		return DeviceType;
	}
	public void setDeviceType(int deviceType) {
		DeviceType = deviceType;
	}
	
}
