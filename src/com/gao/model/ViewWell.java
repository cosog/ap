package com.gao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VIEW_WELL")
public class ViewWell implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String dwbh;
	private String jz;
	private String jc;
	private String jhh;
	private String jh;
	private Integer jlx;

	// Constructors

	/** default constructor */
	public ViewWell() {
	}

	/** minimal constructor */
	public ViewWell(Integer id, String jh) {
		this.id = id;
		this.jh = jh;
	}

	/** full constructor */
	public ViewWell(Integer id, String dwbh, String jz, String jc, String jhh,
			String jh, Integer jlx) {
		this.id = id;
		this.dwbh = dwbh;
		this.jz = jz;
		this.jc = jc;
		this.jhh = jhh;
		this.jh = jh;
		this.jlx = jlx;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "DWBH", length = 200)
	public String getDwbh() {
		return this.dwbh;
	}

	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}

	@Column(name = "JZ", length = 200)
	public String getJz() {
		return this.jz;
	}

	public void setJz(String jz) {
		this.jz = jz;
	}

	@Column(name = "JC", length = 200)
	public String getJc() {
		return this.jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	@Column(name = "JHH", length = 200)
	public String getJhh() {
		return this.jhh;
	}

	public void setJhh(String jhh) {
		this.jhh = jhh;
	}

	@Column(name = "JH", nullable = false, length = 200)
	public String getJh() {
		return this.jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	@Column(name = "JLX", precision = 22, scale = 0)
	public Integer getJlx() {
		return this.jlx;
	}

	public void setJlx(Integer jlx) {
		this.jlx = jlx;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "dwbh" + dwbh + "  **  jc=**" + jc + "**jh=*" + jh;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ViewWell))
			return false;
		ViewWell castOther = (ViewWell) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getDwbh() == castOther.getDwbh()) || (this.getDwbh() != null
						&& castOther.getDwbh() != null && this.getDwbh()
						.equals(castOther.getDwbh())))
				&& ((this.getJz() == castOther.getJz()) || (this.getJz() != null
						&& castOther.getJz() != null && this.getJz().equals(
						castOther.getJz())))
				&& ((this.getJc() == castOther.getJc()) || (this.getJc() != null
						&& castOther.getJc() != null && this.getJc().equals(
						castOther.getJc())))
				&& ((this.getJhh() == castOther.getJhh()) || (this.getJhh() != null
						&& castOther.getJhh() != null && this.getJhh().equals(
						castOther.getJhh())))
				&& ((this.getJh() == castOther.getJh()) || (this.getJh() != null
						&& castOther.getJh() != null && this.getJh().equals(
						castOther.getJh())))
				&& ((this.getJlx() == castOther.getJlx()) || (this.getJlx() != null
						&& castOther.getJlx() != null && this.getJlx().equals(
						castOther.getJlx())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getDwbh() == null ? 0 : this.getDwbh().hashCode());
		result = 37 * result + (getJz() == null ? 0 : this.getJz().hashCode());
		result = 37 * result + (getJc() == null ? 0 : this.getJc().hashCode());
		result = 37 * result
				+ (getJhh() == null ? 0 : this.getJhh().hashCode());
		result = 37 * result + (getJh() == null ? 0 : this.getJh().hashCode());
		result = 37 * result
				+ (getJlx() == null ? 0 : this.getJlx().hashCode());
		return result;
	}

}