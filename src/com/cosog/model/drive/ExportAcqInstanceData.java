package com.cosog.model.drive;

public class ExportAcqInstanceData {

	private int Id;

    private String Name;

    private String Code;

    private String AcqProtocolType;

    private String CtrlProtocolType;

    private int SignInPrefixSuffixHex;

    private String SignInPrefix;
    
    private String SigninSuffix;

    private int SignInIDHex;

    private int HeartbeatPrefixSuffixHex;

    private String HeartbeatPrefix;

    private String HeartbeatSuffix;

    private int PacketSendInterval;

    private int Sort;

    private int UnitId;

    private String UnitName;
    
    private String Protocol;

    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }
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
    public void setAcqProtocolType(String AcqProtocolType){
        this.AcqProtocolType = AcqProtocolType;
    }
    public String getAcqProtocolType(){
        return this.AcqProtocolType;
    }
    public void setCtrlProtocolType(String CtrlProtocolType){
        this.CtrlProtocolType = CtrlProtocolType;
    }
    public String getCtrlProtocolType(){
        return this.CtrlProtocolType;
    }
    public void setSignInPrefixSuffixHex(int SignInPrefixSuffixHex){
        this.SignInPrefixSuffixHex = SignInPrefixSuffixHex;
    }
    public int getSignInPrefixSuffixHex(){
        return this.SignInPrefixSuffixHex;
    }
    public void setSignInPrefix(String SignInPrefix){
        this.SignInPrefix = SignInPrefix;
    }
    public String getSignInPrefix(){
        return this.SignInPrefix;
    }
    public void setSignInIDHex(int SignInIDHex){
        this.SignInIDHex = SignInIDHex;
    }
    public int getSignInIDHex(){
        return this.SignInIDHex;
    }
    public void setHeartbeatPrefixSuffixHex(int HeartbeatPrefixSuffixHex){
        this.HeartbeatPrefixSuffixHex = HeartbeatPrefixSuffixHex;
    }
    public int getHeartbeatPrefixSuffixHex(){
        return this.HeartbeatPrefixSuffixHex;
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
    public void setPacketSendInterval(int PacketSendInterval){
        this.PacketSendInterval = PacketSendInterval;
    }
    public int getPacketSendInterval(){
        return this.PacketSendInterval;
    }
    public void setSort(int Sort){
        this.Sort = Sort;
    }
    public int getSort(){
        return this.Sort;
    }
    public void setUnitId(int UnitId){
        this.UnitId = UnitId;
    }
    public int getUnitId(){
        return this.UnitId;
    }
    public void setUnitName(String UnitName){
        this.UnitName = UnitName;
    }
    public String getUnitName(){
        return this.UnitName;
    }
	public String getProtocol() {
		return Protocol;
	}
	public void setProtocol(String protocol) {
		Protocol = protocol;
	}
	public String getSigninSuffix() {
		return SigninSuffix;
	}
	public void setSigninSuffix(String signinSuffix) {
		SigninSuffix = signinSuffix;
	}
}
