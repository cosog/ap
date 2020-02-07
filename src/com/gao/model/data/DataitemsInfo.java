package com.gao.model.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenericGenerator;

/**
 * 系统数据字典数据项值表
 * 
 * @author 钱邓水
 * @data 2014-4-10
 */
@Entity
@Table(name = "tbl_dataitemsinfo")
public class DataitemsInfo implements Serializable {
	/**
	 * 缺省VersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String dataitemid; // id（主键）
	private String tenantid; // 租户
	private String sysdataid; // 数据字典id
	private String cname; // 中文名称
	private String ename; // 英文名称
	private String datavalue; // 数据项的值
	private Integer sorts; // 排序
	private Integer status; // 删除标记,0未删除，1删除
	private String creator; // 创建人
	private String updateuser; // 修改人
	private Date updatetime; // 修改时间
	private Date createdate; // 创建时间

	public DataitemsInfo(String dataitemid, String cname, String ename,
			String datavalue) {
		super();
		this.dataitemid = dataitemid;
		this.cname = cname;
		this.ename = ename;
		this.datavalue = datavalue;
	}

	public DataitemsInfo() {
		super();
	}

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "dataitemid", unique = true, nullable = false, precision = 22, scale = 0)
	public String getDataitemid() {
		return dataitemid;
	}

	public void setDataitemid(String dataitemid) {
		this.dataitemid = dataitemid;
	}

	public String getSysdataid() {
		return sysdataid;
	}

	public void setSysdataid(String sysdataid) {
		this.sysdataid = sysdataid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getDatavalue() {
		return datavalue;
	}

	public void setDatavalue(String datavalue) {
		this.datavalue = datavalue;
	}

	public Integer getSorts() {
		return sorts;
	}

	public void setSorts(Integer sorts) {
		this.sorts = sorts;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

}
