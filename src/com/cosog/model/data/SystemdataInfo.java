package com.cosog.model.data;

import java.io.Serializable;
import java.util.Date; 
import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.Id;
import javax.persistence.Table; 

/**
 * 系统数据字典表
 * @author 钱邓水
 * @data 2014-4-10
 */  
@Entity
@Table(name = "tbl_dist_name")
public class SystemdataInfo implements Serializable{
	/**
     *  缺省VersionUID
     */
    private static final long serialVersionUID = 1L;
	private String   sysdataid;   //id（主键） 
	private String   tenantid;    //租户
    private String   cname;       //中文名称
    private String   ename;       //英文名称
    private Integer  sorts;		   //排序	
	private Integer  status;	   //删除标记,0未删除，1删除
	private String 	 creator;	   //创建人
	private String 	 updateuser;   //修改人
	private Date     updatetime;      //修改时间
	private Date     createdate;      //创建时间	
	private Integer  moduleId;		   //模块id
	
	@Id 
	@Column(name="sysdataid", nullable=false, insertable=true, updatable=true, length=32)
    public String getSysdataid() {
		return sysdataid;
	}
	public void setSysdataid(String sysdataid) {
		this.sysdataid = sysdataid;
	}
	 
	@Column(name="CNAME")
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	@Column(name="ENAME")
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	
	@Column(name="SORTS")
	public Integer getSorts() {
		return sorts;
	}
	public void setSorts(Integer sorts) {
		this.sorts = sorts;
	}
	
	@Column(name="STATUS")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="CREATOR")
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name="UPDATEUSER")
	public String getUpdateuser() {
		return updateuser;
	}
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}
	
	@Column(name="UPDATETIME")
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@Column(name="CREATEDATE")
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	@Column(name="TENANTID")
	public String getTenantid() {
		return tenantid;
	}
	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}
	
	@Column(name="MODULEID")
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
}
