package com.cosog.model.drive;

import java.io.Serializable;
import java.util.List;

import com.cosog.utils.StringManagerUtils;

public class ModbusProtocolConfig implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<Protocol> Protocol;

    public void setProtocol(List<Protocol> Protocol){
        this.Protocol = Protocol;
    }
    public List<Protocol> getProtocol(){
        return this.Protocol;
    }
    
    public static class ItemsMeaning implements Serializable, Comparable<ItemsMeaning>
    {
    	private static final long serialVersionUID = 1L;
    	
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
    
	public static class Items implements Comparable<Items>, Serializable{
		
		private static final long serialVersionUID = 1L;

	    private String Title;

	    private int Addr;

	    private String StoreDataType;
	    
	    private String IFDataType;
	    
	    private int Prec;

	    private int Quantity;

	    private float Ratio;

	    private String RWType;

	    private String Unit;
	    
	    private int ResolutionMode;

	    private String AcqMode;
	    
	    private List<ItemsMeaning> Meaning;

	    public void setTitle(String Title){
	        this.Title = Title;
	    }
	    public String getTitle(){
	        return this.Title;
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
	    public void setRWType(String RWType){
	        this.RWType = RWType;
	    }
	    public String getRWType(){
	        return this.RWType;
	    }
	    public void setUnit(String Unit){
	        this.Unit = Unit;
	    }
	    public String getUnit(){
	        return this.Unit;
	    }
	    public void setAcqMode(String AcqMode){
	        this.AcqMode = AcqMode;
	    }
	    public String getAcqMode(){
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

		public int getResolutionMode() {
			return ResolutionMode;
		}
		public void setResolutionMode(int resolutionMode) {
			ResolutionMode = resolutionMode;
		}

		public List<ItemsMeaning> getMeaning() {
			return Meaning;
		}
		public void setMeaning(List<ItemsMeaning> meaning) {
			Meaning = meaning;
		}
		
//		public String toString(){
//			StringBuffer result=new StringBuffer();
//			result.append("{\"header\": \""+this.Title);
//			if(StringManagerUtils.isNotNull(this.Unit)){
//				result.append("("+this.Unit+")");
//			}
//			result.append("\",\"dataIndex\": \""+this.Name+"\", children: []}");
//			return result.toString();
//		}
		
		@Override
		public int compareTo(Items item) {     //重写Comparable接口的compareTo方法
			return this.Addr-item.getAddr();   // 根据地址升序排列，降序修改相减顺序即可
		}
		public int getPrec() {
			return Prec;
		}
		public void setPrec(int prec) {
			Prec = prec;
		}
	}
	
	public static class Protocol  implements Comparable<Protocol>,Serializable{
		
		private static final long serialVersionUID = 1L;
		
		private int Id;
	    
		private String Name;

	    private String Code;

	    private int Sort;
	    
	    private int DeviceType;
	    
	    private int Language;

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
	    
	    @Override
		public int compareTo(Protocol protocol) {     //重写Comparable接口的compareTo方法
			return this.Sort-protocol.getSort();   // 根据地址升序排列，降序修改相减顺序即可
		}
		public int getId() {
			return Id;
		}
		public void setId(int id) {
			Id = id;
		}
		public int getDeviceType() {
			return DeviceType;
		}
		public void setDeviceType(int deviceType) {
			DeviceType = deviceType;
		}
		public int getLanguage() {
			return Language;
		}
		public void setLanguage(int language) {
			Language = language;
		}
	}
}
