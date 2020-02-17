package com.gao.model.data;

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
	
	@Id 
	@Column(name="sysdataid", nullable=false, insertable=true, updatable=true, length=32)
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
