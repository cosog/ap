package com.cosog.model;

import java.io.Serializable;

public class OutputStatistics_CN implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String abilitylevel;
	private Double amount;
	private Double average;
	private Double totals;

	// Constructors

	/** default constructor */
	public OutputStatistics_CN() {
	}

	/** full constructor */
	public OutputStatistics_CN(String abilitylevel, Double amount,
			Double average, Double totals) {
		this.abilitylevel = abilitylevel;
		this.amount = amount;
		this.average = average;
		this.totals = totals;
	}

	public String getAbilitylevel() {
		return this.abilitylevel;
	}

	public void setAbilitylevel(String abilitylevel) {
		this.abilitylevel = abilitylevel;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAverage() {
		return this.average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	public Double getTotals() {
		return this.totals;
	}

	public void setTotals(Double totals) {
		this.totals = totals;
	}

}
