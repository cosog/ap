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
    	
    	private String Status0;
    	
    	private String Status1;

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

		public String getStatus0() {
			return Status0;
		}

		public void setStatus0(String status0) {
			Status0 = status0;
		}

		public String getStatus1() {
			return Status1;
		}

		public void setStatus1(String status1) {
			Status1 = status1;
		}
    }
    
	public static class Items implements Comparable<Items>, Serializable{
		
		private static final long serialVersionUID = 1L;

	    private String Title;

	    private int Addr;
	    
	    private String HighLowByte="";//高低字节 ""-不按字节操作 higt-高字节 low-低字节

	    private String StoreDataType;
	    
	    private String IFDataType;
	    
	    private int Prec;

	    private int Quantity;

	    private float Ratio;

	    private String RWType;

	    private String Unit;
	    
	    private int ResolutionMode;//解析模式 0-开关量 1-灭枚举量 2-数据量

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
		public String getHighLowByte() {
			return HighLowByte;
		}
		public void setHighLowByte(String highLowByte) {
			HighLowByte = highLowByte;
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
	    
	    private String DeviceTypeAllPath_zh_CN;
	    
	    private String DeviceTypeAllPath_en;
	    
	    private String DeviceTypeAllPath_ru;

	    private List<Items> Items;
	    
	    private List<ExtendedField> ExtendedFields;

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
		public List<ExtendedField> getExtendedFields() {
			return ExtendedFields;
		}
		public void setExtendedFields(List<ExtendedField> extendedFields) {
			ExtendedFields = extendedFields;
		}
		public String getDeviceTypeAllPath_zh_CN() {
			return DeviceTypeAllPath_zh_CN;
		}
		public void setDeviceTypeAllPath_zh_CN(String deviceTypeAllPath_zh_CN) {
			DeviceTypeAllPath_zh_CN = deviceTypeAllPath_zh_CN;
		}
		public String getDeviceTypeAllPath_en() {
			return DeviceTypeAllPath_en;
		}
		public void setDeviceTypeAllPath_en(String deviceTypeAllPath_en) {
			DeviceTypeAllPath_en = deviceTypeAllPath_en;
		}
		public String getDeviceTypeAllPath_ru() {
			return DeviceTypeAllPath_ru;
		}
		public void setDeviceTypeAllPath_ru(String deviceTypeAllPath_ru) {
			DeviceTypeAllPath_ru = deviceTypeAllPath_ru;
		}
	}
	
	public static class ExtendedField  implements Serializable
    {
    	private static final long serialVersionUID = 1L;
    	
    	private String Title;
    	
    	private int Prec;

	    private float Ratio;

	    private String Unit;
	    
	    private String Title1;
	    
	    private String Title2;
	    
	    private int Operation;
	    
	    private int AdditionalConditions;
	    
	    private int Type;
	    
	    private String HighLowByte;
	    
	    private int ResolutionMode;
	    
	    private List<ItemsMeaning> Meaning;

		public String getTitle() {
			return Title;
		}

		public void setTitle(String title) {
			Title = title;
		}

		public int getPrec() {
			return Prec;
		}

		public void setPrec(int prec) {
			Prec = prec;
		}

		public float getRatio() {
			return Ratio;
		}

		public void setRatio(float ratio) {
			Ratio = ratio;
		}

		public String getUnit() {
			return Unit;
		}

		public void setUnit(String unit) {
			Unit = unit;
		}

		public String getTitle1() {
			return Title1;
		}

		public void setTitle1(String title1) {
			Title1 = title1;
		}

		public String getTitle2() {
			return Title2;
		}

		public void setTitle2(String title2) {
			Title2 = title2;
		}

		public int getOperation() {
			return Operation;
		}

		public void setOperation(int operation) {
			Operation = operation;
		}

		public int getAdditionalConditions() {
			return AdditionalConditions;
		}

		public void setAdditionalConditions(int additionalConditions) {
			AdditionalConditions = additionalConditions;
		}

		public int getType() {
			return Type;
		}

		public void setType(int type) {
			Type = type;
		}

		public String getHighLowByte() {
			return HighLowByte;
		}

		public void setHighLowByte(String highLowByte) {
			HighLowByte = highLowByte;
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
	}
}
