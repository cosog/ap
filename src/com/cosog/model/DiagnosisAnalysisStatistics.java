package com.cosog.model;

/**
 * 工况统计
 */

public class DiagnosisAnalysisStatistics {

	/**
	 * 
	 */
	private String gklx;
	private String gkmc;
	private Integer total;

	// Constructors
	/** default constructor */
	public DiagnosisAnalysisStatistics() {
	}

	/** minimal constructor */
	public DiagnosisAnalysisStatistics(String gkmc) {
		this.gkmc = gkmc;
	}

	/** full constructor */
	public DiagnosisAnalysisStatistics(String gklx, String gkmc, Integer total) {
		this.gklx = gklx;
		this.gkmc = gkmc;
		this.total = total;
	}

	
	public String getGklx() {
		return this.gklx;
	}

	public void setGklx(String gklx) {
		this.gklx = gklx;
	}

	
	public String getGkmc() {
		return this.gkmc;
	}

	public void setGkmc(String gkmc) {
		this.gkmc = gkmc;
	}

	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}