package com.cosog.model;

public class ProjectTabConfig {
	
	private DeviceRealTimeMonitoring DeviceRealTimeMonitoring;

    private DeviceHistoryQuery DeviceHistoryQuery;

    private AlarmQuery AlarmQuery;

    public void setDeviceRealTimeMonitoring(DeviceRealTimeMonitoring DeviceRealTimeMonitoring){
        this.DeviceRealTimeMonitoring = DeviceRealTimeMonitoring;
    }
    public DeviceRealTimeMonitoring getDeviceRealTimeMonitoring(){
        return this.DeviceRealTimeMonitoring;
    }
    public void setDeviceHistoryQuery(DeviceHistoryQuery DeviceHistoryQuery){
        this.DeviceHistoryQuery = DeviceHistoryQuery;
    }
    public DeviceHistoryQuery getDeviceHistoryQuery(){
        return this.DeviceHistoryQuery;
    }
    public void setAlarmQuery(AlarmQuery AlarmQuery){
        this.AlarmQuery = AlarmQuery;
    }
    public AlarmQuery getAlarmQuery(){
        return this.AlarmQuery;
    }
    
	public static class DeviceRealTimeMonitoring
	{
	    private boolean FESDiagramStatPie;

	    private boolean CommStatusStatPie;

	    private boolean RunStatusStatPie;

	    private boolean NumStatusStatPie;

	    public void setFESDiagramStatPie(boolean FESDiagramStatPie){
	        this.FESDiagramStatPie = FESDiagramStatPie;
	    }
	    public boolean getFESDiagramStatPie(){
	        return this.FESDiagramStatPie;
	    }
	    public void setCommStatusStatPie(boolean CommStatusStatPie){
	        this.CommStatusStatPie = CommStatusStatPie;
	    }
	    public boolean getCommStatusStatPie(){
	        return this.CommStatusStatPie;
	    }
	    public void setRunStatusStatPie(boolean RunStatusStatPie){
	        this.RunStatusStatPie = RunStatusStatPie;
	    }
	    public boolean getRunStatusStatPie(){
	        return this.RunStatusStatPie;
	    }
	    public void setNumStatusStatPie(boolean NumStatusStatPie){
	        this.NumStatusStatPie = NumStatusStatPie;
	    }
	    public boolean getNumStatusStatPie(){
	        return this.NumStatusStatPie;
	    }
	}
	
	public static class DeviceHistoryQuery
	{
	    private boolean FESDiagramStatPie;

	    private boolean CommStatusStatPie;

	    private boolean RunStatusStatPie;

	    private boolean NumStatusStatPie;

	    public void setFESDiagramStatPie(boolean FESDiagramStatPie){
	        this.FESDiagramStatPie = FESDiagramStatPie;
	    }
	    public boolean getFESDiagramStatPie(){
	        return this.FESDiagramStatPie;
	    }
	    public void setCommStatusStatPie(boolean CommStatusStatPie){
	        this.CommStatusStatPie = CommStatusStatPie;
	    }
	    public boolean getCommStatusStatPie(){
	        return this.CommStatusStatPie;
	    }
	    public void setRunStatusStatPie(boolean RunStatusStatPie){
	        this.RunStatusStatPie = RunStatusStatPie;
	    }
	    public boolean getRunStatusStatPie(){
	        return this.RunStatusStatPie;
	    }
	    public void setNumStatusStatPie(boolean NumStatusStatPie){
	        this.NumStatusStatPie = NumStatusStatPie;
	    }
	    public boolean getNumStatusStatPie(){
	        return this.NumStatusStatPie;
	    }
	}
	
	public static class AlarmQuery
	{
	    private boolean FESDiagramResultAlarm;

	    private boolean RunStatusAlarm;

	    private boolean CommStatusAlarm;

	    private boolean NumericValueAlarm;

	    private boolean EnumValueAlarm;

	    private boolean SwitchingValueAlarm;

	    public void setFESDiagramResultAlarm(boolean FESDiagramResultAlarm){
	        this.FESDiagramResultAlarm = FESDiagramResultAlarm;
	    }
	    public boolean getFESDiagramResultAlarm(){
	        return this.FESDiagramResultAlarm;
	    }
	    public void setRunStatusAlarm(boolean RunStatusAlarm){
	        this.RunStatusAlarm = RunStatusAlarm;
	    }
	    public boolean getRunStatusAlarm(){
	        return this.RunStatusAlarm;
	    }
	    public void setCommStatusAlarm(boolean CommStatusAlarm){
	        this.CommStatusAlarm = CommStatusAlarm;
	    }
	    public boolean getCommStatusAlarm(){
	        return this.CommStatusAlarm;
	    }
	    public void setNumericValueAlarm(boolean NumericValueAlarm){
	        this.NumericValueAlarm = NumericValueAlarm;
	    }
	    public boolean getNumericValueAlarm(){
	        return this.NumericValueAlarm;
	    }
	    public void setEnumValueAlarm(boolean EnumValueAlarm){
	        this.EnumValueAlarm = EnumValueAlarm;
	    }
	    public boolean getEnumValueAlarm(){
	        return this.EnumValueAlarm;
	    }
	    public void setSwitchingValueAlarm(boolean SwitchingValueAlarm){
	        this.SwitchingValueAlarm = SwitchingValueAlarm;
	    }
	    public boolean getSwitchingValueAlarm(){
	        return this.SwitchingValueAlarm;
	    }
	}
}
