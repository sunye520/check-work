/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.entity;

import org.hibernate.validator.constraints.Length;
import com.yogee.youge.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.yogee.youge.common.persistence.DataEntity;

/**
 * 假条Entity
 * @author zhangjian
 * @version 2018-12-12
 */
public class CheckLeavePermit extends DataEntity<CheckLeavePermit> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型（1-事假，2-病假，3-婚假，4-产假，5-陪产假，6-丧假，7-年假）
	private User user;		// 用户人id
	private String userName;		// 用户名称
	private Date startTime;		// 开始时间
	private Date endTime;		// 结束时间
	private String approverType;		// 审核状态（1-审核成功，2-审核中，3审核失败）
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
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=64, message="用户名称长度必须介于 0 和 64 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
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