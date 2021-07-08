package com.cosog.model;

import java.io.Serializable;

/**
 * 工况诊断（统计view） pojo类
 * 
 * @author ding
 * 
 */

public class DiagnosisAnalysisAll implements Serializable {
	private static final long serialVersionUID = 1L;
	private String gkmc;
	private Integer total;

	public String getGkmc() {
		return gkmc;
	}

	public DiagnosisAnalysisAll(String gkmc, Integer total) {
		super();
		this.gkmc = gkmc;
		this.total = total;
	}

	public void setGkmc(String gkmc) {
		this.gkmc = gkmc;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
