package com.cosog.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;;

public class ReportTemplate  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Template> singleWellRangeReportTemplate;
	
	private List<Template> singleWellDailyReportTemplate;

	private List<Template> productionReportTemplate;
	
	public static class TdStyle implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
	    private String fontWeight;

	    private String fontSize;

	    private String fontFamily;

	    private String height;

	    private String color;

	    private String backgroundColor;
	    
	    private String textAlign;

	    public void setFontWeight(String fontWeight){
	        this.fontWeight = fontWeight;
	    }
	    public String getFontWeight(){
	        return this.fontWeight;
	    }
	    public void setFontSize(String fontSize){
	        this.fontSize = fontSize;
	    }
	    public String getFontSize(){
	        return this.fontSize;
	    }
	    public void setFontFamily(String fontFamily){
	        this.fontFamily = fontFamily;
	    }
	    public String getFontFamily(){
	        return this.fontFamily;
	    }
	    public void setHeight(String height){
	        this.height = height;
	    }
	    public String getHeight(){
	        return this.height;
	    }
	    public void setColor(String color){
	        this.color = color;
	    }
	    public String getColor(){
	        return this.color;
	    }
	    public void setBackgroundColor(String backgroundColor){
	        this.backgroundColor = backgroundColor;
	    }
	    public String getBackgroundColor(){
	        return this.backgroundColor;
	    }
		public String getTextAlign() {
			return textAlign;
		}
		public void setTextAlign(String textAlign) {
			this.textAlign = textAlign;
		}
	}
	
	public static class Header implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
	    private TdStyle tdStyle;

	    private List<String> title;

	    public void setTdStyle(TdStyle tdStyle){
	        this.tdStyle = tdStyle;
	    }
	    public TdStyle getTdStyle(){
	        return this.tdStyle;
	    }
	    public void setTitle(List<String> title){
	        this.title = title;
	    }
	    public List<String> getTitle(){
	        return this.title;
	    }
	}
	
	public static class MergeCells implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
	    private int row;

	    private int col;

	    private int rowspan;

	    private int colspan;

	    public void setRow(int row){
	        this.row = row;
	    }
	    public int getRow(){
	        return this.row;
	    }
	    public void setCol(int col){
	        this.col = col;
	    }
	    public int getCol(){
	        return this.col;
	    }
	    public void setRowspan(int rowspan){
	        this.rowspan = rowspan;
	    }
	    public int getRowspan(){
	        return this.rowspan;
	    }
	    public void setColspan(int colspan){
	        this.colspan = colspan;
	    }
	    public int getColspan(){
	        return this.colspan;
	    }
	}
	
	public static class Editable implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
	    private int startRow;

	    private int startColumn;

	    private int endRow;

	    private int endColumn;

	    public void setStartRow(int startRow){
	        this.startRow = startRow;
	    }
	    public int getStartRow(){
	        return this.startRow;
	    }
	    public void setStattColumn(int startColumn){
	        this.startColumn = startColumn;
	    }
	    public int getStartColumn(){
	        return this.startColumn;
	    }
	    public void setEndRow(int endRow){
	        this.endRow = endRow;
	    }
	    public int getEndRow(){
	        return this.endRow;
	    }
	    public void setEndColumn(int endColumn){
	        this.endColumn = endColumn;
	    }
	    public int getEndColumn(){
	        return this.endColumn;
	    }
	}
	
	public static class Template implements Comparable<Template>,Serializable
	{
		private static final long serialVersionUID = 1L;

	    private String templateCode;

	    private String templateName;
	    
	    private int sort;

	    private List<Header> header;

	    private int fixedRowsTop;

	    private int fixedRowsBottom;

	    private List<Integer> rowHeights;

	    private List<Integer> columnWidths;

	    private List<MergeCells> mergeCells;

	    private List<Editable> editable;

	    @Override
		public int compareTo(Template template) {     //重写Comparable接口的compareTo方法
			return this.sort-template.getSort();   // 根据地址升序排列，降序修改相减顺序即可
		}
	    
	    public void setTemplateCode(String templateCode){
	        this.templateCode = templateCode;
	    }
	    public String getTemplateCode(){
	        return this.templateCode;
	    }
	    public void setTemplateName(String templateName){
	        this.templateName = templateName;
	    }
	    public String getTemplateName(){
	        return this.templateName;
	    }
	    public void setHeader(List<Header> header){
	        this.header = header;
	    }
	    public List<Header> getHeader(){
	        return this.header;
	    }
	    public void setFixedRowsTop(int fixedRowsTop){
	        this.fixedRowsTop = fixedRowsTop;
	    }
	    public int getFixedRowsTop(){
	        return this.fixedRowsTop;
	    }
	    public void setFixedRowsBottom(int fixedRowsBottom){
	        this.fixedRowsBottom = fixedRowsBottom;
	    }
	    public int getFixedRowsBottom(){
	        return this.fixedRowsBottom;
	    }
	    public void setRowHeights(List<Integer> rowHeights){
	        this.rowHeights = rowHeights;
	    }
	    public List<Integer> getRowHeights(){
	        return this.rowHeights;
	    }
	    public void setColumnWidths(List<Integer> columnWidths){
	        this.columnWidths = columnWidths;
	    }
	    public List<Integer> getColumnWidths(){
	        return this.columnWidths;
	    }
	    public void setMergeCells(List<MergeCells> mergeCells){
	        this.mergeCells = mergeCells;
	    }
	    public List<MergeCells> getMergeCells(){
	        return this.mergeCells;
	    }
	    public void setEditable(List<Editable> editable){
	        this.editable = editable;
	    }
	    public List<Editable> getEditable(){
	        return this.editable;
	    }
		public int getSort() {
			return sort;
		}
		public void setSort(int sort) {
			this.sort = sort;
		}
	}

	public List<Template> getProductionReportTemplate() {
		return productionReportTemplate;
	}

	public void setProductionReportTemplate(List<Template> productionReportTemplate) {
		this.productionReportTemplate = productionReportTemplate;
	}

	public List<Template> getSingleWellRangeReportTemplate() {
		return singleWellRangeReportTemplate;
	}

	public void setSingleWellRangeReportTemplate(List<Template> singleWellRangeReportTemplate) {
		this.singleWellRangeReportTemplate = singleWellRangeReportTemplate;
	}

	public List<Template> getSingleWellDailyReportTemplate() {
		return singleWellDailyReportTemplate;
	}

	public void setSingleWellDailyReportTemplate(List<Template> singleWellDailyReportTemplate) {
		this.singleWellDailyReportTemplate = singleWellDailyReportTemplate;
	}
}
