/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length;

import com.yogee.youge.common.persistence.DataEntity;

/**
 * 打卡记录信息Entity
 * @author sunye
 * @version 2018-12-18
 */
public class CheckPunchCard extends DataEntity<CheckPunchCard> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String number;		// 工号
	private String punchDate;		// 打卡日期
	private String shangCellTime;		// 上午打卡时间
	private String shangChi;		// 迟到早退标识(0:正常打卡  1：迟到 )
	private String shangChiTime;		// 迟到分钟数(单位分钟)
	private String shangChiName;		// 迟到中文写法
	private String shangAskLeave;		// 上午请假标识(0：未请假  1：已请假）
	private String shangAskLeaveTime;		// 请假时间(单位小时）
	private String xiaCellTime;		// 下班打卡时间
	private String xiaZao;		// 早退标识(0:正常打卡 1：早退)
	private String xiaZaoTime;		// 早退分钟数(单位分钟)
	private String xiaZaoName;		// 早退中文写法
	private String xiaAskLeave;		// 下午请假标识(0：未请假  1：已请假）
	private String xiaAskLeaveTime;		// 请假时间(单位小时）

	private String value;		// 请假类型
	private String sumTime;		// 请假时间
	
	public CheckPunchCard() {
		super();
	}

	public CheckPunchCard(String id){
		super(id);
	}

	@Length(min=0, max=255, message="姓名长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="工号长度必须介于 0 和 255 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=0, max=64, message="打卡日期长度必须介于 0 和 64 之间")
	public String getPunchDate() {
		return punchDate;
	}

	public void setPunchDate(String punchDate) {
		this.punchDate = punchDate;
	}
	
	@Length(min=0, max=64, message="上午打卡时间长度必须介于 0 和 64 之间")
	public String getShangCellTime() {
		return shangCellTime;
	}

	public void setShangCellTime(String shangCellTime) {
		this.shangCellTime = shangCellTime;
	}
	
	@Length(min=0, max=64, message="迟到早退标识(0:正常打卡  1：迟到 )长度必须介于 0 和 64 之间")
	public String getShangChi() {
		return shangChi;
	}

	public void setShangChi(String shangChi) {
		this.shangChi = shangChi;
	}
	
	@Length(min=0, max=64, message="迟到分钟数(单位分钟)长度必须介于 0 和 64 之间")
	public String getShangChiTime() {
		return shangChiTime;
	}

	public void setShangChiTime(String shangChiTime) {
		this.shangChiTime = shangChiTime;
	}



	@Length(min=0, max=32, message="迟到中文写法长度必须介于 0 和 32 之间")
	public String getShangChiName() {
		return shangChiName;
	}

	public void setShangChiName(String shangChiName) {
		this.shangChiName = shangChiName;
	}
	
	@Length(min=0, max=64, message="上午请假标识(0：未请假  1：已请假）长度必须介于 0 和 64 之间")
	public String getShangAskLeave() {
		return shangAskLeave;
	}

	public void setShangAskLeave(String shangAskLeave) {
		this.shangAskLeave = shangAskLeave;
	}
	
	@Length(min=0, max=64, message="请假时间(单位小时）长度必须介于 0 和 64 之间")
	public String getShangAskLeaveTime() {
		return shangAskLeaveTime;
	}

	public void setShangAskLeaveTime(String shangAskLeaveTime) {
		this.shangAskLeaveTime = shangAskLeaveTime;
	}
	
	@Length(min=0, max=64, message="下班打卡时间长度必须介于 0 和 64 之间")
	public String getXiaCellTime() {
		return xiaCellTime;
	}

	public void setXiaCellTime(String xiaCellTime) {
		this.xiaCellTime = xiaCellTime;
	}
	
	@Length(min=0, max=64, message="早退标识(0:正常打卡 1：早退)长度必须介于 0 和 64 之间")
	public String getXiaZao() {
		return xiaZao;
	}

	public void setXiaZao(String xiaZao) {
		this.xiaZao = xiaZao;
	}
	
	@Length(min=0, max=64, message="早退分钟数(单位分钟)长度必须介于 0 和 64 之间")
	public String getXiaZaoTime() {
		return xiaZaoTime;
	}

	public void setXiaZaoTime(String xiaZaoTime) {
		this.xiaZaoTime = xiaZaoTime;
	}



	@Length(min=0, max=32, message="早退中文写法长度必须介于 0 和 32 之间")
	public String getXiaZaoName() {
		return xiaZaoName;
	}

	public void setXiaZaoName(String xiaZaoName) {
		this.xiaZaoName = xiaZaoName;
	}
	
	@Length(min=0, max=64, message="下午请假标识(0：未请假  1：已请假）长度必须介于 0 和 64 之间")
	public String getXiaAskLeave() {
		return xiaAskLeave;
	}

	public void setXiaAskLeave(String xiaAskLeave) {
		this.xiaAskLeave = xiaAskLeave;
	}
	
	@Length(min=0, max=64, message="请假时间(单位小时）长度必须介于 0 和 64 之间")
	public String getXiaAskLeaveTime() {
		return xiaAskLeaveTime;
	}

	public void setXiaAskLeaveTime(String xiaAskLeaveTime) {
		this.xiaAskLeaveTime = xiaAskLeaveTime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSumTime() {
		return sumTime;
	}

	public void setSumTime(String sumTime) {
		this.sumTime = sumTime;
	}
}