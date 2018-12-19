/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.entity;

import org.hibernate.validator.constraints.Length;

import com.yogee.youge.common.persistence.DataEntity;

/**
 * 时间设置Entity
 * @author sunye
 * @version 2018-12-17
 */
public class CheckBusinessDate extends DataEntity<CheckBusinessDate> {
	
	private static final long serialVersionUID = 1L;
	private String shangbanDate;		// 上班时间
	private String wuxiuDate;		// 午休时间
	private String xiabanDate;		// 下班时间
	
	public CheckBusinessDate() {
		super();
	}

	public CheckBusinessDate(String id){
		super(id);
	}

	@Length(min=0, max=64, message="上班时间长度必须介于 0 和 64 之间")
	public String getShangbanDate() {
		return shangbanDate;
	}

	public void setShangbanDate(String shangbanDate) {
		this.shangbanDate = shangbanDate;
	}
	
	@Length(min=0, max=64, message="午休时间长度必须介于 0 和 64 之间")
	public String getWuxiuDate() {
		return wuxiuDate;
	}

	public void setWuxiuDate(String wuxiuDate) {
		this.wuxiuDate = wuxiuDate;
	}
	
	@Length(min=0, max=64, message="下班时间长度必须介于 0 和 64 之间")
	public String getXiabanDate() {
		return xiabanDate;
	}

	public void setXiabanDate(String xiabanDate) {
		this.xiabanDate = xiabanDate;
	}
	
}