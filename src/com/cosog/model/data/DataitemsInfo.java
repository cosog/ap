package com.cosog.model.data;

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
@Table(name = "tbl_dist_item")
public class DataitemsInfo implements Serializable {
	/**
	 * 缺省VersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String dataitemid; // id（主键）
	private String tenantid; // 租户
	private String sysdataid; // 数据字典id
	private String name_zh_CN; // 中文名称
	private String name_en;
	private String name_ru;
	private String code; // 编码
	private String datavalue; // 数据项的值
	private Integer sorts; // 排序
	private Integer status; // 删除标记,0未删除，1删除
	private String creator; // 创建人
	private String updateuser; // 修改人
	private Date updatetime; // 修改时间
	private Date createdate; // 创建时间
	private Integer dataSource; // 数据源
	private Integer deviceType; // 设备类型
	public DataitemsInfo() {
		super();
	}

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "DATAITEMID", unique = true, nullable = false, precision = 22, scale = 0)
	public String getDataitemid() {
		return dataitemid;
	}

	public void setDataitemid(String dataitemid) {
		this.dataitemid = dataitemid;
	}

	@Column(name = "SYSDATAID")
	public String getSysdataid() {
		return sysdataid;
	}

	public void setSysdataid(String sysdataid) {
		this.sysdataid = sysdataid;
	}

	@Column(name = "DATAVALUE")
	public String getDatavalue() {
		return datavalue;
	}

	public void setDatavalue(String datavalue) {
		this.datavalue = datavalue;
	}

	@Column(name = "SORTS")
	public Integer getSorts() {
		return sorts;
	}

	public void setSorts(Integer sorts) {
		this.sorts = sorts;
	}

	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "CREATOR")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "UPDATEUSER")
	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	@Column(name = "UPDATETIME")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "CREATEDATE")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Column(name = "TENANTID")
	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME_ZH_CN")
	public String getName_zh_CN() {
		return name_zh_CN;
	}

	public void setName_zh_CN(String name_zh_CN) {
		this.name_zh_CN = name_zh_CN;
	}

	@Column(name = "NAME_EN")
	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	@Column(name = "NAME_RU")
	public String getName_ru() {
		return name_ru;
	}

	public void setName_ru(String name_ru) {
		this.name_ru = name_ru;
	}

	@Column(name = "DATASOURCE")
	public Integer getDataSource() {
		return dataSource;
	}

	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}

	@Column(name = "DEVICETYPE")
	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

}
