package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cosog.utils.StringManagerUtils;
/**
 *  <p>描述：采集组实体类  tbl_acq_group_conf</p>
 *  
 * @author zhao  2018-11-02
 *
 */
@Entity
@Table(name = "tbl_acq_group_conf")
public class AcquisitionGroup implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String groupCode;
	private String groupName;
	private Integer groupTimingInterval;
	private Integer groupSavingInterval;
	private String protocol;
	private Integer type;
	private String remark;

	// Constructors

	/** default constructor */
	public AcquisitionGroup() {
	}

	/** full constructor */
	public AcquisitionGroup(Integer id, String groupCode, String groupName, Integer groupTimingInterval, Integer groupSavingInterval,
			String protocol, Integer type, String remark) {
		super();
		this.id = id;
		this.groupCode = groupCode;
		this.groupName = groupName;
		this.groupTimingInterval = groupTimingInterval;
		this.groupSavingInterval = groupSavingInterval;
		this.protocol = protocol;
		this.type = type;
		this.remark = remark;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "GROUP_CODE", nullable = false, length = 20)
	public String getGroupCode() {
		return this.groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name = "GROUP_NAME", nullable = false, length = 40)
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@Column(name = "groupTimingInterval", precision = 22, scale = 0)
	public Integer getGroupTimingInterval() {
		return groupTimingInterval;
	}

	public void setGroupTimingInterval(Integer groupTimingInterval) {
		this.groupTimingInterval = groupTimingInterval;
	}

	@Column(name = "groupSavingInterval", precision = 22, scale = 0)
	public Integer getGroupSavingInterval() {
		return groupSavingInterval;
	}

	public void setGroupSavingInterval(Integer groupSavingInterval) {
		this.groupSavingInterval = groupSavingInterval;
	}
	
	@Column(name = "REMARK", nullable = true, length = 10)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		if(!StringManagerUtils.isNotNull(remark)){
			this.remark = "";
		}else{
			this.remark = remark;
		}
	}
	
	@Column(name = "protocol", nullable = true, length = 10)
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Column(name = "type", precision = 22, scale = 0)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}