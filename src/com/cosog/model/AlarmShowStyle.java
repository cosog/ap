package com.cosog.model;

public class AlarmShowStyle implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CommAlarmStyle Comm;
	
	private RunAlarmStyle Run;
	
	private AlarmLevelStyle Data;
	
	public AlarmShowStyle() {
		this.Comm=new CommAlarmStyle();
		this.Run=new RunAlarmStyle();
		this.Data=new AlarmLevelStyle();
		
		this.Comm.setOnline(new AlarmStyle());
		this.Comm.setOffline(new AlarmStyle());
		
		this.Run.setRun(new AlarmStyle());
		this.Run.setStop(new AlarmStyle());
		
		this.Data.setNormal(new AlarmStyle());
		this.Data.setFirstLevel(new AlarmStyle());
		this.Data.setSecondLevel(new AlarmStyle());
		this.Data.setThirdLevel(new AlarmStyle());
	}

	public static class CommAlarmStyle implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
    	private AlarmStyle online;

        private AlarmStyle offline;

		public AlarmStyle getOnline() {
			return online;
		}

		public void setOnline(AlarmStyle online) {
			this.online = online;
		}

		public AlarmStyle getOffline() {
			return offline;
		}

		public void setOffline(AlarmStyle offline) {
			this.offline = offline;
		}
    }
	
	public static class RunAlarmStyle implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
    	private AlarmStyle run;

        private AlarmStyle stop;

		public AlarmStyle getRun() {
			return run;
		}

		public void setRun(AlarmStyle run) {
			this.run = run;
		}

		public AlarmStyle getStop() {
			return stop;
		}

		public void setStop(AlarmStyle stop) {
			this.stop = stop;
		}
    }
    
    public static class AlarmLevelStyle  implements java.io.Serializable {
    	private static final long serialVersionUID = 1L;
    	
    	private AlarmStyle Normal;

        private AlarmStyle FirstLevel;

        private AlarmStyle SecondLevel;

        private AlarmStyle ThirdLevel;

		public AlarmStyle getNormal() {
			return Normal;
		}

		public void setNormal(AlarmStyle normal) {
			Normal = normal;
		}

		public AlarmStyle getFirstLevel() {
			return FirstLevel;
		}

		public void setFirstLevel(AlarmStyle firstLevel) {
			FirstLevel = firstLevel;
		}

		public AlarmStyle getSecondLevel() {
			return SecondLevel;
		}

		public void setSecondLevel(AlarmStyle secondLevel) {
			SecondLevel = secondLevel;
		}

		public AlarmStyle getThirdLevel() {
			return ThirdLevel;
		}

		public void setThirdLevel(AlarmStyle thirdLevel) {
			ThirdLevel = thirdLevel;
		}
    }
	
	public static class AlarmStyle implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
	    private int Value;

	    private String BackgroundColor;
	    
	    private String Color;
	    
	    private String Opacity;

	    private String FontStyle;

	    public void setValue(int Value){
	        this.Value = Value;
	    }
	    public int getValue(){
	        return this.Value;
	    }
	    public void setBackgroundColor(String BackgroundColor){
	        this.BackgroundColor = BackgroundColor;
	    }
	    public String getBackgroundColor(){
	        return this.BackgroundColor;
	    }
	    public void setFontStyle(String FontStyle){
	        this.FontStyle = FontStyle;
	    }
	    public String getFontStyle(){
	        return this.FontStyle;
	    }
		public String getColor() {
			return Color;
		}
		public void setColor(String color) {
			Color = color;
		}
		public String getOpacity() {
			return Opacity;
		}
		public void setOpacity(String opacity) {
			Opacity = opacity;
		}
	}

	public AlarmLevelStyle getData() {
		return Data;
	}

	public void setData(AlarmLevelStyle data) {
		Data = data;
	}

	public CommAlarmStyle getComm() {
		return Comm;
	}

	public void setComm(CommAlarmStyle comm) {
		Comm = comm;
	}

	public RunAlarmStyle getRun() {
		return Run;
	}

	public void setRun(RunAlarmStyle run) {
		Run = run;
	}
}