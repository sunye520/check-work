/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yogee.youge.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 审批Entity
 * @author zhangjian
 * @version 2018-12-12
 */
public class CheckApprover extends DataEntity<CheckApprover> {
	
	private static final long serialVersionUID = 1L;
	private String time;		// 审核时间
	private String approverId;		// 审核人id
	private String leavePermitId;		// 假条/外出id
	private String type;		// 类型（1-假条，2-外出）
	private String approverType;		// 审核状态（1-审核成功，2-审核失败，3-审核中）
	private String childrenId;		// 下级审批人id
	private String display;		// 是否显示（1-显示，2-隐藏）
	
	public CheckApprover() {
		super();
	}

	public CheckApprover(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	@Length(min=0, max=64, message="审核人id长度必须介于 0 和 64 之间")
	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	
	@Length(min=0, max=64, message="假条/外出id长度必须介于 0 和 64 之间")
	public String getLeavePermitId() {
		return leavePermitId;
	}

	public void setLeavePermitId(String leavePermitId) {
		this.leavePermitId = leavePermitId;
	}
	
	@Length(min=0, max=1, message="类型（1-假条，2-外出）长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="审核状态（1-审核成功，2-审核中，3审核失败）长度必须介于 0 和 64 之间")
	public String getApproverType() {
		return approverType;
	}

	public void setApproverType(String approverType) {
		this.approverType = approverType;
	}
	
	@Length(min=0, max=64, message="下级审批人id长度必须介于 0 和 64 之间")
	public String getChildrenId() {
		return childrenId;
	}

	public void setChildrenId(String childrenId) {
		this.childrenId = childrenId;
	}
	
	@Length(min=0, max=1, message="是否显示（1-显示，2-隐藏）长度必须介于 0 和 1 之间")
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	
}