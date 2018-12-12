/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.entity;

import org.hibernate.validator.constraints.Length;

import com.yogee.youge.common.persistence.DataEntity;

/**
 * 上班时间Entity
 * @author cheng
 * @version 2018-12-12
 */
public class CheckTime extends DataEntity<CheckTime> {
	
	private static final long serialVersionUID = 1L;
	private String morning;		// 上午上班时间
	private String afternoon;		// 下午上班时间
	
	public CheckTime() {
		super();
	}

	public CheckTime(String id){
		super(id);
	}

	@Length(min=0, max=255, message="上午上班时间长度必须介于 0 和 255 之间")
	public String getMorning() {
		return morning;
	}

	public void setMorning(String morning) {
		this.morning = morning;
	}
	
	@Length(min=0, max=255, message="下午上班时间长度必须介于 0 和 255 之间")
	public String getAfternoon() {
		return afternoon;
	}

	public void setAfternoon(String afternoon) {
		this.afternoon = afternoon;
	}
	
}