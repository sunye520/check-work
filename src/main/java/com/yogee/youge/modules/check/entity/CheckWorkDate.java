/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.entity;

import org.hibernate.validator.constraints.Length;

import com.yogee.youge.common.persistence.DataEntity;

/**
 * 排班Entity
 * @author cheng
 * @version 2018-12-12
 */
public class CheckWorkDate extends DataEntity<CheckWorkDate> {
	
	private static final long serialVersionUID = 1L;
	private String dates;		// 日期（年月）
	private String status;		// 上班状态 0上班1休息
	private String allDate;		// 日期（年月）
	
	public CheckWorkDate() {
		super();
	}

	public CheckWorkDate(String id){
		super(id);
	}

	@Length(min=0, max=10, message="日期（年月）长度必须介于 0 和 10 之间")
	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}
	
	@Length(min=0, max=2, message="上班状态 0上班1休息长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=10, message="日期（年月）长度必须介于 0 和 10 之间")
	public String getAllDate() {
		return allDate;
	}

	public void setAllDate(String allDate) {
		this.allDate = allDate;
	}
	
}