/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.entity;

import org.hibernate.validator.constraints.Length;

import com.yogee.youge.common.persistence.DataEntity;

/**
 * 审核人员配置表Entity
 * @author zhangjian
 * @version 2018-12-12
 */
public class CheckApproverDeploy extends DataEntity<CheckApproverDeploy> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型（1-事假，2-病假，3-婚假，4-产假，5-陪产假，6-丧假，7-年假，8-出差，9-外出，10-补卡）
	private String departmentId;		// 部门id
	private String approverId;		// 审核人id（用 | 分割）
	private String approverType;		// 审核状态（1-审核成功，2-审核中，3审核失败）
	private String ccId;		// 抄送人id（用 | 分割）
	private String time;		// 请假时间
	
	public CheckApproverDeploy() {
		super();
	}

	public CheckApproverDeploy(String id){
		super(id);
	}

	@Length(min=0, max=10, message="类型（1-事假，2-病假，3-婚假，4-产假，5-陪产假，6-丧假，7-年假，8-出差，9-外出，10-补卡）长度必须介于 0 和 10 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="部门id长度必须介于 0 和 64 之间")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	
	@Length(min=0, max=64, message="审核状态（1-审核成功，2-审核中，3审核失败）长度必须介于 0 和 64 之间")
	public String getApproverType() {
		return approverType;
	}

	public void setApproverType(String approverType) {
		this.approverType = approverType;
	}
	
	@Length(min=0, max=64, message="抄送人id（用 | 分割）长度必须介于 0 和 64 之间")
	public String getCcId() {
		return ccId;
	}

	public void setCcId(String ccId) {
		this.ccId = ccId;
	}
	
	@Length(min=0, max=10, message="请假时间长度必须介于 0 和 10 之间")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}