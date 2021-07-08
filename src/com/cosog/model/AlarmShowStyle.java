package com.cosog.model;

public class AlarmShowStyle implements java.io.Serializable {
	
	public AlarmShowStyle() {
		this.Normal=new AlarmStyle();
		this.FirstLevel=new AlarmStyle();
		this.SecondLevel=new AlarmStyle();
		this.ThirdLevel=new AlarmStyle();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AlarmStyle Normal;

    private AlarmStyle FirstLevel;

    private AlarmStyle SecondLevel;

    private AlarmStyle ThirdLevel;

    public void setNormal(AlarmStyle Normal){
        this.Normal = Normal;
    }
    public AlarmStyle getNormal(){
        return this.Normal;
    }
    public void setFirstLevel(AlarmStyle FirstLevel){
        this.FirstLevel = FirstLevel;
    }
    public AlarmStyle getFirstLevel(){
        return this.FirstLevel;
    }
    public void setSecondLevel(AlarmStyle SecondLevel){
        this.SecondLevel = SecondLevel;
    }
    public AlarmStyle getSecondLevel(){
        return this.SecondLevel;
    }
    public void setThirdLevel(AlarmStyle ThirdLevel){
        this.ThirdLevel = ThirdLevel;
    }
    public AlarmStyle getThirdLevel(){
        return this.ThirdLevel;
    }
	
	public static class AlarmStyle
	{
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
}