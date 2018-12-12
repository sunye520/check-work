/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yogee.youge.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 外出Entity
 * @author zhangjian
 * @version 2018-12-12
 */
public class CheckGoOut extends DataEntity<CheckGoOut> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型（1-出差，2-外出）
	private String userId;		// 用户id
	private String userName;		// 用户名称
	private Date startTime;		// 开始时间
	private Date endTime;		// 结束时间
	private String approverType;		// 审核状态（1-审核成功，2-审核失败，3-审核中）
	private String time;		// 出差/外出时长
	private String content;		// 出差/外出事由
	private String vehicle;		// 交通工具（1-飞机，2-火车，3-汽车，4-其他）
	private String oneWayRoundtrip;		// 单程往返（1-单程，2-往返）
	private String departureCity;		// 出发城市
	private String objectiveCity;		// 目的城市
	private String departmentId;		// 部门id
	
	public CheckGoOut() {
		super();
	}

	public CheckGoOut(String id){
		super(id);
	}

	@Length(min=0, max=1, message="类型（1-出差，2-外出）长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUser(String user) {
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
	
	@Length(min=0, max=10, message="出差/外出时长长度必须介于 0 和 10 之间")
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
	
	@Length(min=0, max=1, message="交通工具（1-飞机，2-火车，3-汽车，4-其他）长度必须介于 0 和 1 之间")
	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	
	@Length(min=0, max=1, message="单程往返（1-单程，2-往返）长度必须介于 0 和 1 之间")
	public String getOneWayRoundtrip() {
		return oneWayRoundtrip;
	}

	public void setOneWayRoundtrip(String oneWayRoundtrip) {
		this.oneWayRoundtrip = oneWayRoundtrip;
	}
	
	@Length(min=0, max=64, message="出发城市长度必须介于 0 和 64 之间")
	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}
	
	@Length(min=0, max=64, message="目的城市长度必须介于 0 和 64 之间")
	public String getObjectiveCity() {
		return objectiveCity;
	}

	public void setObjectiveCity(String objectiveCity) {
		this.objectiveCity = objectiveCity;
	}
	
	@Length(min=0, max=64, message="部门id长度必须介于 0 和 64 之间")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
}