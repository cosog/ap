package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 *  <p>描述：辅件设备信息 实体类  tbl_auxiliarydevice</p>
 *  
 * @author zhao  2021-12-17
 *
 */
@Entity
@Table(name = "tbl_pumpingmodel")
public class PumpingModelInformation implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String manufacturer;
	private String model;
	private String stroke;
	private String crankRotationDirection;
	private float offsetAngleOfCrank;
	private float crankGravityRadius;
	private float singleCrankWeight;
	private float singleCrankPinWeight;
	private float structuralUnbalance;
	private String balanceWeight;

	// Constructors
	/** default constructor */
	public PumpingModelInformation() {
	}

	/** full constructor */
	public PumpingModelInformation(Integer id, String manufacturer, String model, String stroke,
			String crankRotationDirection, float offsetAngleOfCrank, float crankGravityRadius, float singleCrankWeight,
			float singleCrankPinWeight, float structuralUnbalance,String balanceWeight) {
		super();
		this.id = id;
		this.manufacturer = manufacturer;
		this.model = model;
		this.stroke = stroke;
		this.crankRotationDirection = crankRotationDirection;
		this.offsetAngleOfCrank = offsetAngleOfCrank;
		this.crankGravityRadius = crankGravityRadius;
		this.singleCrankWeight = singleCrankWeight;
		this.singleCrankPinWeight = singleCrankPinWeight;
		this.structuralUnbalance = structuralUnbalance;
		this.balanceWeight = balanceWeight;
	}
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "manufacturer", nullable = false, length = 50)
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Column(name = "model", nullable = false, length = 50)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	@Column(name = "stroke", nullable = false, length = 50)
	public String getStroke() {
		return stroke;
	}

	public void setStroke(String stroke) {
		this.stroke = stroke;
	}

	@Column(name = "crankRotationDirection", nullable = false, length = 50)
	public String getCrankRotationDirection() {
		return crankRotationDirection;
	}
	
	public void setCrankRotationDirection(String crankRotationDirection) {
		this.crankRotationDirection = crankRotationDirection;
	}

	@Column(name = "offsetAngleOfCrank", precision = 8)
	public float getOffsetAngleOfCrank() {
		return offsetAngleOfCrank;
	}

	public void setOffsetAngleOfCrank(float offsetAngleOfCrank) {
		this.offsetAngleOfCrank = offsetAngleOfCrank;
	}

	@Column(name = "crankGravityRadius", precision = 8)
	public float getCrankGravityRadius() {
		return crankGravityRadius;
	}

	public void setCrankGravityRadius(float crankGravityRadius) {
		this.crankGravityRadius = crankGravityRadius;
	}

	@Column(name = "singleCrankWeight", precision = 8)
	public float getSingleCrankWeight() {
		return singleCrankWeight;
	}

	public void setSingleCrankWeight(float singleCrankWeight) {
		this.singleCrankWeight = singleCrankWeight;
	}

	@Column(name = "singleCrankPinWeight", precision = 8)
	public float getSingleCrankPinWeight() {
		return singleCrankPinWeight;
	}

	public void setSingleCrankPinWeight(float singleCrankPinWeight) {
		this.singleCrankPinWeight = singleCrankPinWeight;
	}

	@Column(name = "structuralUnbalance", precision = 8)
	public float getStructuralUnbalance() {
		return structuralUnbalance;
	}

	public void setStructuralUnbalance(float structuralUnbalance) {
		this.structuralUnbalance = structuralUnbalance;
	}

	@Column(name = "balanceWeight", nullable = false, length = 50)
	public String getBalanceWeight() {
		return balanceWeight;
	}

	public void setBalanceWeight(String balanceWeight) {
		this.balanceWeight = balanceWeight;
	}

	
}