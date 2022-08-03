package com.cosog.model.drive;

import java.util.List;

public class WaterCutRawData {

	private int ResultStatus;
	
	private int HeartbeatInterval;

    private String ID;

    private Message Message;

    private int Signal;

    private String SysTime;

    private int TransferInterval;

    private String Ver;

    public void setHeartbeatInterval(int HeartbeatInterval){
        this.HeartbeatInterval = HeartbeatInterval;
    }
    public int getHeartbeatInterval(){
        return this.HeartbeatInterval;
    }
    public void setID(String ID){
        this.ID = ID;
    }
    public String getID(){
        return this.ID;
    }
    public void setMessage(Message Message){
        this.Message = Message;
    }
    public Message getMessage(){
        return this.Message;
    }
    public void setSignal(int Signal){
        this.Signal = Signal;
    }
    public int getSignal(){
        return this.Signal;
    }
    public void setSysTime(String SysTime){
        this.SysTime = SysTime;
    }
    public String getSysTime(){
        return this.SysTime;
    }
    public void setTransferInterval(int TransferInterval){
        this.TransferInterval = TransferInterval;
    }
    public int getTransferInterval(){
        return this.TransferInterval;
    }
    public void setVer(String Ver){
        this.Ver = Ver;
    }
    public String getVer(){
        return this.Ver;
    }
	
	public static class Message
	{
	    private String AcqTime;

	    private List<Integer> Interval;

	    private List<String> Position;

	    private List<Float> TubingPressure;

	    private List<Float> WaterCut;

		public String getAcqTime() {
			return AcqTime;
		}

		public void setAcqTime(String acqTime) {
			AcqTime = acqTime;
		}

		public List<Integer> getInterval() {
			return Interval;
		}

		public void setInterval(List<Integer> interval) {
			Interval = interval;
		}

		public List<String> getPosition() {
			return Position;
		}

		public void setPosition(List<String> position) {
			Position = position;
		}

		public List<Float> getTubingPressure() {
			return TubingPressure;
		}

		public void setTubingPressure(List<Float> tubingPressure) {
			TubingPressure = tubingPressure;
		}

		public List<Float> getWaterCut() {
			return WaterCut;
		}

		public void setWaterCut(List<Float> waterCut) {
			WaterCut = waterCut;
		}
	    
	}

	public int getResultStatus() {
		return ResultStatus;
	}
	public void setResultStatus(int resultStatus) {
		ResultStatus = resultStatus;
	}
}
