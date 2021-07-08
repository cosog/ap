package com.cosog.model.drive;

import java.util.List;

public class ModbusProtocolConfig {
	
	private List<Protocol> Protocol;

    public void setProtocol(List<Protocol> Protocol){
        this.Protocol = Protocol;
    }
    public List<Protocol> getProtocol(){
        return this.Protocol;
    }
    
	public static class Items
	{
	    private String Name;

	    private String Code;

	    private int Addr;

	    private String StoreDataType;
	    
	    private String IFDataType;

	    private int Quantity;

	    private float Ratio;

	    private boolean RWType;

	    private String Unit;

	    private boolean AcqMode;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setCode(String Code){
	        this.Code = Code;
	    }
	    public String getCode(){
	        return this.Code;
	    }
	    public void setAddr(int Addr){
	        this.Addr = Addr;
	    }
	    public int getAddr(){
	        return this.Addr;
	    }
	    public void setQuantity(int Quantity){
	        this.Quantity = Quantity;
	    }
	    public int getQuantity(){
	        return this.Quantity;
	    }
	    public void setRatio(float Ratio){
	        this.Ratio = Ratio;
	    }
	    public float getRatio(){
	        return this.Ratio;
	    }
	    public void setRWType(boolean RWType){
	        this.RWType = RWType;
	    }
	    public boolean getRWType(){
	        return this.RWType;
	    }
	    public void setUnit(String Unit){
	        this.Unit = Unit;
	    }
	    public String getUnit(){
	        return this.Unit;
	    }
	    public void setAcqMode(boolean AcqMode){
	        this.AcqMode = AcqMode;
	    }
	    public boolean getAcqMode(){
	        return this.AcqMode;
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
	}
	
	public static class Protocol
	{
	    private String Name;

	    private String Code;

	    private int Type;

	    private String SignInPrefix;

	    private String SignInSuffix;

	    private String HeartbeatPrefix;

	    private String HeartbeatSuffix;

	    private int Sort;

	    private List<Items> Items;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setCode(String Code){
	        this.Code = Code;
	    }
	    public String getCode(){
	        return this.Code;
	    }
	    public void setType(int Type){
	        this.Type = Type;
	    }
	    public int getType(){
	        return this.Type;
	    }
	    public void setSignInPrefix(String SignInPrefix){
	        this.SignInPrefix = SignInPrefix;
	    }
	    public String getSignInPrefix(){
	        return this.SignInPrefix;
	    }
	    public void setSignInSuffix(String SignInSuffix){
	        this.SignInSuffix = SignInSuffix;
	    }
	    public String getSignInSuffix(){
	        return this.SignInSuffix;
	    }
	    public void setHeartbeatPrefix(String HeartbeatPrefix){
	        this.HeartbeatPrefix = HeartbeatPrefix;
	    }
	    public String getHeartbeatPrefix(){
	        return this.HeartbeatPrefix;
	    }
	    public void setHeartbeatSuffix(String HeartbeatSuffix){
	        this.HeartbeatSuffix = HeartbeatSuffix;
	    }
	    public String getHeartbeatSuffix(){
	        return this.HeartbeatSuffix;
	    }
	    public void setSort(int Sort){
	        this.Sort = Sort;
	    }
	    public int getSort(){
	        return this.Sort;
	    }
	    public void setItems(List<Items> Items){
	        this.Items = Items;
	    }
	    public List<Items> getItems(){
	        return this.Items;
	    }
	}
}
