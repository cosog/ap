package com.gao.model.drive;

import java.util.List;
import java.util.ArrayList;

public class ModbusDriverSaveData {

	private String ProtocolName;
	
	private String ProtocolCode;
	
	private String ProtocolType;
	
	private String StoreMode;
	
	private String SignInPrefix;
	
	private String SignInSuffix;
	
	private String HeartbeatPrefix;
	
	private String HeartbeatSuffix;

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
	
	public static class DataConfig
	{
	    private String Name;

	    private int Addr;

	    private int Quantity;

	    private String DataType;
	    
	    private String IFDataType;
	    
	    private String Unit;

	    private float Ratio;
	    
	    private String AcqMode;
	    
	    private String RWType;

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

		public String getDataType() {
			return DataType;
		}

		public void setDataType(String dataType) {
			DataType = dataType;
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
	}

	public String getProtocolType() {
		return ProtocolType;
	}
	public void setProtocolType(String protocolType) {
		ProtocolType = protocolType;
	}
	public String getStoreMode() {
		return StoreMode;
	}
	public void setStoreMode(String storeMode) {
		StoreMode = storeMode;
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
	
}
