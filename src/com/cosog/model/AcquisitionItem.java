package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：模块信息 实体类  tbl_acq_item_conf</p>
 *  
 * @author zhao  2020-02-17
 *
 */
@Entity
@Table(name = "tbl_acq_item_conf")
public  class AcquisitionItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer parentid;
	private String itemName;
	private String itemCode;
	private String address;
	private Integer length;
	private Integer dataType;
	private Double zoom;
	private Integer seq;
	
	public AcquisitionItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** minimal constructor */
	public AcquisitionItem(Integer id, Integer parentid, String itemName, String itemCode, String address,
			Integer length, Integer dataType, Double zoom,Integer seq) {
		super();
		this.id = id;
		this.parentid = parentid;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.address = address;
		this.length = length;
		this.dataType = dataType;
		this.zoom = zoom;
		this.seq=seq;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "parentid", nullable = false, precision = 22, scale = 0)
	public Integer getParentid() {
		return this.parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	@Column(name = "itemname",length = 100)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "itemcode", length = 200)
	public String getItemCode() {
		return this.itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name = "address",length = 100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "length", precision = 22, scale = 0)
	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	@Column(name = "datatype", precision = 22, scale = 0)
	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	@Column(name = "zoom", precision = 8)
	public Double getZoom() {
		return zoom;
	}

	public void setZoom(Double zoom) {
		this.zoom = zoom;
	}

	@Column(name = "seq", precision = 22, scale = 0)
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}