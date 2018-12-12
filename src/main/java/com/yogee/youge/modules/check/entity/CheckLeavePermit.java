/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yogee.youge.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 假条Entity
 * @author zhangjian
 * @version 2018-12-12
 */
public class CheckLeavePermit extends DataEntity<CheckLeavePermit> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型（1-事假，2-病假，3-婚假，4-产假，5-陪产假，6-丧假，7-年假）
	private String userId;	// 用户人id
	private String userName;		// 用户名称
	private String startTime;		// 开始时间
	private String endTime;		// 结束时间
	private String approverType;		// 审核状态（1-审核成功，2-审核失败，3-审核中）
	private String time;		// 请假时长
	private String content;		// 请假事由
	private String departmentId;		// 部门id
	
	public CheckLeavePermit() {
		super();
	}

	public CheckLeavePermit(String id){
		super(id);
	}

	@Length(min=0, max=1, message="类型（1-事假，2-病假，3-婚假，4-产假，5-陪产假，6-丧假，7-年假）长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="用户名称长度必须介于 0 和 64 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@Length(min=0, max=64, message="审核状态（1-审核成功，2-审核中，3审核失败）长度必须介于 0 和 64 之间")
	public String getApproverType() {
		return approverType;
	}

	public void setApproverType(String approverType) {
		this.approverType = approverType;
	}
	
	@Length(min=0, max=10, message="请假时长长度必须介于 0 和 10 之间")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=64, message="部门id长度必须介于 0 和 64 之间")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
}