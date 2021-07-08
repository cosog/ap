package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：离散数据报警限值 实体类  T_112_DISTRETEALARMLIMIT</p>
 *  
 * @author zhao  2016-04-25
 *
 */
//@Entity
//@Table(name = "T_112_DISTRETEALARMLIMIT")
public class DistreteAlarmLimit implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private Integer jbh;
	private Double currentamax;
	private Double currentamin;
	private Double voltageamax;
	private Double voltageamin;
	private Double activepoweramax;
	private Double activepoweramin;
	
	private Double currentbmax;
	private Double currentbmin;
	private Double voltagebmax;
	private Double voltagebmin;
	private Double activepowerbmax;
	private Double activepowerbmin;
	
	private Double currentcmax;
	private Double currentcmin;
	private Double voltagecmax;
	private Double voltagecmin;
	private Double activepowercmax;
	private Double activepowercmin;
	
	private Double currentavgmax;
	private Double currentavgmin;
	private Double voltageavgmax;
	private Double voltageavgmin;
	private Double activepowersummax;
	private Double activepowersummin;
	
	

	public DistreteAlarmLimit() {
		super();
	}

	
	public DistreteAlarmLimit(Integer jlbh, Integer jbh, Double currentamax, Double currentamin, Double voltageamax,
			Double voltageamin, Double activepoweramax, Double activepoweramin, Double currentbmax, Double currentbmin,
			Double voltagebmax, Double voltagebmin, Double activepowerbmax, Double activepowerbmin, Double currentcmax,
			Double currentcmin, Double voltagecmax, Double voltagecmin, Double activepowercmax, Double activepowercmin,
			Double currentavgmax, Double currentavgmin, Double voltageavgmax, Double voltageavgmin,
			Double activepowersummax, Double activepowersummin) {
		super();
		this.jlbh = jlbh;
		this.jbh = jbh;
		this.currentamax = currentamax;
		this.currentamin = currentamin;
		this.voltageamax = voltageamax;
		this.voltageamin = voltageamin;
		this.activepoweramax = activepoweramax;
		this.activepoweramin = activepoweramin;
		this.currentbmax = currentbmax;
		this.currentbmin = currentbmin;
		this.voltagebmax = voltagebmax;
		this.voltagebmin = voltagebmin;
		this.activepowerbmax = activepowerbmax;
		this.activepowerbmin = activepowerbmin;
		this.currentcmax = currentcmax;
		this.currentcmin = currentcmin;
		this.voltagecmax = voltagecmax;
		this.voltagecmin = voltagecmin;
		this.activepowercmax = activepowercmax;
		this.activepowercmin = activepowercmin;
		this.currentavgmax = currentavgmax;
		this.currentavgmin = currentavgmin;
		this.voltageavgmax = voltageavgmax;
		this.voltageavgmin = voltageavgmin;
		this.activepowersummax = activepowersummax;
		this.activepowersummin = activepowersummin;
	}

	// Property accessors
	//@Id
	//@Column(name = "JLBH", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getJlbh() {
		return jlbh;
	}


	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}

	//@Column(name = "JBH", nullable = false, precision = 22, scale = 0)
	public Integer getJbh() {
		return jbh;
	}


	public void setJbh(Integer jbh) {
		this.jbh = jbh;
	}

	//@Column(name = "CURRENTAMAX", precision = 8)
	public Double getCurrentamax() {
		return currentamax;
	}


	public void setCurrentamax(Double currentamax) {
		this.currentamax = currentamax;
	}

	//@Column(name = "CURRENTAMIN", precision = 8)
	public Double getCurrentamin() {
		return currentamin;
	}


	public void setCurrentamin(Double currentamin) {
		this.currentamin = currentamin;
	}

	//@Column(name = "VOLTAGEAMAX", precision = 8)
	public Double getVoltageamax() {
		return voltageamax;
	}


	public void setVoltageamax(Double voltageamax) {
		this.voltageamax = voltageamax;
	}

	//@Column(name = "VOLTAGEAMIN", precision = 8)
	public Double getVoltageamin() {
		return voltageamin;
	}


	public void setVoltageamin(Double voltageamin) {
		this.voltageamin = voltageamin;
	}

	//@Column(name = "ACTIVEPOWERAMAX", precision = 8)
	public Double getActivepoweramax() {
		return activepoweramax;
	}


	public void setActivepoweramax(Double activepoweramax) {
		this.activepoweramax = activepoweramax;
	}

	//@Column(name = "ACTIVEPOWERAMIN", precision = 8)
	public Double getActivepoweramin() {
		return activepoweramin;
	}


	public void setActivepoweramin(Double activepoweramin) {
		this.activepoweramin = activepoweramin;
	}

	//@Column(name = "CURRENTBMAX", precision = 8)
	public Double getCurrentbmax() {
		return currentbmax;
	}


	public void setCurrentbmax(Double currentbmax) {
		this.currentbmax = currentbmax;
	}

	//@Column(name = "CURRENTBMIN", precision = 8)
	public Double getCurrentbmin() {
		return currentbmin;
	}


	public void setCurrentbmin(Double currentbmin) {
		this.currentbmin = currentbmin;
	}

	//@Column(name = "VOLTAGEBMAX", precision = 8)
	public Double getVoltagebmax() {
		return voltagebmax;
	}


	public void setVoltagebmax(Double voltagebmax) {
		this.voltagebmax = voltagebmax;
	}

	//@Column(name = "VOLTAGEBMIN", precision = 8)
	public Double getVoltagebmin() {
		return voltagebmin;
	}


	public void setVoltagebmin(Double voltagebmin) {
		this.voltagebmin = voltagebmin;
	}

	//@Column(name = "ACTIVEPOWERBMAX", precision = 8)
	public Double getActivepowerbmax() {
		return activepowerbmax;
	}


	public void setActivepowerbmax(Double activepowerbmax) {
		this.activepowerbmax = activepowerbmax;
	}

	//@Column(name = "ACTIVEPOWERBMIN", precision = 8)
	public Double getActivepowerbmin() {
		return activepowerbmin;
	}


	public void setActivepowerbmin(Double activepowerbmin) {
		this.activepowerbmin = activepowerbmin;
	}

	//@Column(name = "CURRENTCMAX", precision = 8)
	public Double getCurrentcmax() {
		return currentcmax;
	}


	public void setCurrentcmax(Double currentcmax) {
		this.currentcmax = currentcmax;
	}

	//@Column(name = "CURRENTCMIN", precision = 8)
	public Double getCurrentcmin() {
		return currentcmin;
	}


	public void setCurrentcmin(Double currentcmin) {
		this.currentcmin = currentcmin;
	}

	//@Column(name = "VOLTAGECMAX", precision = 8)
	public Double getVoltagecmax() {
		return voltagecmax;
	}


	public void setVoltagecmax(Double voltagecmax) {
		this.voltagecmax = voltagecmax;
	}

	//@Column(name = "VOLTAGECMIN", precision = 8)
	public Double getVoltagecmin() {
		return voltagecmin;
	}


	public void setVoltagecmin(Double voltagecmin) {
		this.voltagecmin = voltagecmin;
	}

	//@Column(name = "ACTIVEPOWERCMAX", precision = 8)
	public Double getActivepowercmax() {
		return activepowercmax;
	}


	public void setActivepowercmax(Double activepowercmax) {
		this.activepowercmax = activepowercmax;
	}

	//@Column(name = "ACTIVEPOWERCMIN", precision = 8)
	public Double getActivepowercmin() {
		return activepowercmin;
	}


	public void setActivepowercmin(Double activepowercmin) {
		this.activepowercmin = activepowercmin;
	}

	//@Column(name = "CURRENTAVGMAX", precision = 8)
	public Double getCurrentavgmax() {
		return currentavgmax;
	}


	public void setCurrentavgmax(Double currentavgmax) {
		this.currentavgmax = currentavgmax;
	}

	//@Column(name = "CURRENTAVGMIN", precision = 8)
	public Double getCurrentavgmin() {
		return currentavgmin;
	}


	public void setCurrentavgmin(Double currentavgmin) {
		this.currentavgmin = currentavgmin;
	}

	//@Column(name = "VOLTAGEAVGMAX", precision = 8)
	public Double getVoltageavgmax() {
		return voltageavgmax;
	}


	public void setVoltageavgmax(Double voltageavgmax) {
		this.voltageavgmax = voltageavgmax;
	}

	//@Column(name = "VOLTAGEAVGMIN", precision = 8)
	public Double getVoltageavgmin() {
		return voltageavgmin;
	}


	public void setVoltageavgmin(Double voltageavgmin) {
		this.voltageavgmin = voltageavgmin;
	}

	//@Column(name = "ACTIVEPOWERSUMMAX", precision = 8)
	public Double getActivepowersummax() {
		return activepowersummax;
	}


	public void setActivepowersummax(Double activepowersummax) {
		this.activepowersummax = activepowersummax;
	}

	//@Column(name = "ACTIVEPOWERSUMMIN", precision = 8)
	public Double getActivepowersummin() {
		return activepowersummin;
	}


	public void setActivepowersummin(Double activepowersummin) {
		this.activepowersummin = activepowersummin;
	}

}