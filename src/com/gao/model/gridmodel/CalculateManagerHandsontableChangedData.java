package com.gao.model.gridmodel;

import java.util.List;

public class CalculateManagerHandsontableChangedData {

	private List<String> delidslist;

    private List<Updatelist> updatelist;

    private List<Updatelist> insertlist;

    public void setDelidslist(List<String> delidslist){
        this.delidslist = delidslist;
    }
    public List<String> getDelidslist(){
        return this.delidslist;
    }
    public void setUpdatelist(List<Updatelist> updatelist){
        this.updatelist = updatelist;
    }
    public List<Updatelist> getUpdatelist(){
        return this.updatelist;
    }
    public void setInsertlist(List<Updatelist> insertlist){
        this.insertlist = insertlist;
    }
    public List<Updatelist> getInsertlist(){
        return this.insertlist;
    }
	
	public static class Updatelist
	{
		private String id;

	    private String jh;

	    private String gtcjsj;
	    
	    private String yymd="0.86";
	    
	    private String smd="1";
	    
	    private String trqxdmd="0.7";
	    
	    private String bhyl="0";
	    
	    private String yqczbsd="0";
	    
	    private String yqczbwd="0";

	    private String yy="0.5";

	    private String ty="0.5";

	    private String jklw="35";

	    private String hsl="0";

	    private String scqyb="10";

	    private String dym;

	    private String bg;

	    private String bjb="1";

	    private String bj="38";

	    private String zsc="1.2";

	    private String btc="2";

	    private String bjs="0";

	    private String zzjmzj="0";

	    private String gcpl="0";

	    private String ygnj="62";

	    private String yctgnj="139.7";

	    private String yjgjb="D";

	    private String yjgj="25";

	    private String yjgnj="0";

	    private String yjgcd="0";

	    private String ejgjb="D";

	    private String ejgj="25";

	    private String ejgnj="0";

	    private String ejgcd="0";

	    private String sjgjb="D";

	    private String sjgj="25";

	    private String sjgnj="0";

	    private String sjgcd="0";

	    private String sijgjb="D";

	    private String sijgj="25";

	    private String sijgnj="0";

	    private String sijgcd="0";
	    
	    private String mdzt="未锚定";

	    private String jmb="1";

	    private String jsbz;

	    public void setId(String id){
	        this.id = id;
	    }
	    public String getId(){
	        return this.id;
	    }
	    public void setJh(String jh){
	        this.jh = jh;
	    }
	    public String getJh(){
	        return this.jh;
	    }
	    public void setGtcjsj(String gtcjsj){
	        this.gtcjsj = gtcjsj;
	    }
	    public String getGtcjsj(){
	        return this.gtcjsj;
	    }
	    public void setYy(String yy){
	        this.yy = yy;
	    }
	    public String getYy(){
	        return this.yy;
	    }
	    public void setTy(String ty){
	        this.ty = ty;
	    }
	    public String getTy(){
	        return this.ty;
	    }
	    public void setJklw(String jklw){
	        this.jklw = jklw;
	    }
	    public String getJklw(){
	        return this.jklw;
	    }
	    public void setHsl(String hsl){
	        this.hsl = hsl;
	    }
	    public String getHsl(){
	        return this.hsl;
	    }
	    public void setScqyb(String scqyb){
	        this.scqyb = scqyb;
	    }
	    public String getScqyb(){
	        return this.scqyb;
	    }
	    public void setDym(String dym){
	        this.dym = dym;
	    }
	    public String getDym(){
	        return this.dym;
	    }
	    public void setBg(String bg){
	        this.bg = bg;
	    }
	    public String getBg(){
	        return this.bg;
	    }
	    public void setBjb(String bjb){
	        this.bjb = bjb;
	    }
	    public String getBjb(){
	        return this.bjb;
	    }
	    public void setBj(String bj){
	        this.bj = bj;
	    }
	    public String getBj(){
	        return this.bj;
	    }
	    public void setZsc(String zsc){
	        this.zsc = zsc;
	    }
	    public String getZsc(){
	        return this.zsc;
	    }
	    public void setBtc(String btc){
	        this.btc = btc;
	    }
	    public String getBtc(){
	        return this.btc;
	    }
	    public void setBjs(String bjs){
	        this.bjs = bjs;
	    }
	    public String getBjs(){
	        return this.bjs;
	    }
	    public void setZzjmzj(String zzjmzj){
	        this.zzjmzj = zzjmzj;
	    }
	    public String getZzjmzj(){
	        return this.zzjmzj;
	    }
	    public void setGcpl(String gcpl){
	        this.gcpl = gcpl;
	    }
	    public String getGcpl(){
	        return this.gcpl;
	    }
	    public void setYgnj(String ygnj){
	        this.ygnj = ygnj;
	    }
	    public String getYgnj(){
	        return this.ygnj;
	    }
	    public void setYctgnj(String yctgnj){
	        this.yctgnj = yctgnj;
	    }
	    public String getYctgnj(){
	        return this.yctgnj;
	    }
	    public void setYjgjb(String yjgjb){
	        this.yjgjb = yjgjb;
	    }
	    public String getYjgjb(){
	        return this.yjgjb;
	    }
	    public void setYjgj(String yjgj){
	        this.yjgj = yjgj;
	    }
	    public String getYjgj(){
	        return this.yjgj;
	    }
	    public void setYjgnj(String yjgnj){
	        this.yjgnj = yjgnj;
	    }
	    public String getYjgnj(){
	        return this.yjgnj;
	    }
	    public void setYjgcd(String yjgcd){
	        this.yjgcd = yjgcd;
	    }
	    public String getYjgcd(){
	        return this.yjgcd;
	    }
	    public void setEjgjb(String ejgjb){
	        this.ejgjb = ejgjb;
	    }
	    public String getEjgjb(){
	        return this.ejgjb;
	    }
	    public void setEjgj(String ejgj){
	        this.ejgj = ejgj;
	    }
	    public String getEjgj(){
	        return this.ejgj;
	    }
	    public void setEjgnj(String ejgnj){
	        this.ejgnj = ejgnj;
	    }
	    public String getEjgnj(){
	        return this.ejgnj;
	    }
	    public void setEjgcd(String ejgcd){
	        this.ejgcd = ejgcd;
	    }
	    public String getEjgcd(){
	        return this.ejgcd;
	    }
	    public void setSjgjb(String sjgjb){
	        this.sjgjb = sjgjb;
	    }
	    public String getSjgjb(){
	        return this.sjgjb;
	    }
	    public void setSjgj(String sjgj){
	        this.sjgj = sjgj;
	    }
	    public String getSjgj(){
	        return this.sjgj;
	    }
	    public void setSjgnj(String sjgnj){
	        this.sjgnj = sjgnj;
	    }
	    public String getSjgnj(){
	        return this.sjgnj;
	    }
	    public void setSjgcd(String sjgcd){
	        this.sjgcd = sjgcd;
	    }
	    public String getSjgcd(){
	        return this.sjgcd;
	    }
	    public void setSijgjb(String sijgjb){
	        this.sijgjb = sijgjb;
	    }
	    public String getSijgjb(){
	        return this.sijgjb;
	    }
	    public void setSijgj(String sijgj){
	        this.sijgj = sijgj;
	    }
	    public String getSijgj(){
	        return this.sijgj;
	    }
	    public void setSijgnj(String sijgnj){
	        this.sijgnj = sijgnj;
	    }
	    public String getSijgnj(){
	        return this.sijgnj;
	    }
	    public void setSijgcd(String sijgcd){
	        this.sijgcd = sijgcd;
	    }
	    public String getSijgcd(){
	        return this.sijgcd;
	    }
	    public void setJmb(String jmb){
	        this.jmb = jmb;
	    }
	    public String getJmb(){
	        return this.jmb;
	    }
	    public void setJsbz(String jsbz){
	        this.jsbz = jsbz;
	    }
	    public String getJsbz(){
	        return this.jsbz;
	    }
		public String getYymd() {
			return yymd;
		}
		public void setYymd(String yymd) {
			this.yymd = yymd;
		}
		public String getSmd() {
			return smd;
		}
		public void setSmd(String smd) {
			this.smd = smd;
		}
		public String getTrqxdmd() {
			return trqxdmd;
		}
		public void setTrqxdmd(String trqxdmd) {
			this.trqxdmd = trqxdmd;
		}
		public String getBhyl() {
			return bhyl;
		}
		public void setBhyl(String bhyl) {
			this.bhyl = bhyl;
		}
		public String getYqczbsd() {
			return yqczbsd;
		}
		public void setYqczbsd(String yqczbsd) {
			this.yqczbsd = yqczbsd;
		}
		public String getYqczbwd() {
			return yqczbwd;
		}
		public void setYqczbwd(String yqczbwd) {
			this.yqczbwd = yqczbwd;
		}
		public String getMdzt() {
			return mdzt;
		}
		public void setMdzt(String mdzt) {
			this.mdzt = mdzt;
		}
	}
}
