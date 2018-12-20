/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.entity;

import org.hibernate.validator.constraints.Length;

import com.yogee.youge.common.persistence.DataEntity;

/**
 * 人员异动结果表Entity
 * @author zhangjian
 * @version 2018-12-14
 */
public class CheckChangeResult extends DataEntity<CheckChangeResult> {
	
	private static final long serialVersionUID = 1L;
	private String bumen;		// 部门
	private String benyueRenshu;		// 本月人数
	private String shangyuemoRenshu;		// 上月末人数
	private String ruzhi;		// 入职
	private String lizhiZhudong;		// 离职-主动
	private String lizhiBeidong;		// 离职-被动
	private String zhuanzheng;		// 转正
	private String tiaogang;		// 调岗
	private String tiaoxin;		// 调薪
	private String zhixingShijian;		// 执行时间
	private String beizhu;		// 备注\
	private String type;
	
	public CheckChangeResult() {
		super();
	}

	public CheckChangeResult(String id){
		super(id);
	}

	@Length(min=0, max=64, message="部门长度必须介于 0 和 64 之间")
	public String getBumen() {
		return bumen;
	}

	public void setBumen(String bumen) {
		this.bumen = bumen;
	}
	
	@Length(min=0, max=64, message="本月人数长度必须介于 0 和 64 之间")
	public String getBenyueRenshu() {
		return benyueRenshu;
	}

	public void setBenyueRenshu(String benyueRenshu) {
		this.benyueRenshu = benyueRenshu;
	}
	
	@Length(min=0, max=64, message="上月末人数长度必须介于 0 和 64 之间")
	public String getShangyuemoRenshu() {
		return shangyuemoRenshu;
	}

	public void setShangyuemoRenshu(String shangyuemoRenshu) {
		this.shangyuemoRenshu = shangyuemoRenshu;
	}
	
	@Length(min=0, max=64, message="入职长度必须介于 0 和 64 之间")
	public String getRuzhi() {
		return ruzhi;
	}

	public void setRuzhi(String ruzhi) {
		this.ruzhi = ruzhi;
	}
	
	@Length(min=0, max=64, message="离职-主动长度必须介于 0 和 64 之间")
	public String getLizhiZhudong() {
		return lizhiZhudong;
	}

	public void setLizhiZhudong(String lizhiZhudong) {
		this.lizhiZhudong = lizhiZhudong;
	}
	
	@Length(min=0, max=64, message="离职-被动长度必须介于 0 和 64 之间")
	public String getLizhiBeidong() {
		return lizhiBeidong;
	}

	public void setLizhiBeidong(String lizhiBeidong) {
		this.lizhiBeidong = lizhiBeidong;
	}
	
	@Length(min=0, max=64, message="转正长度必须介于 0 和 64 之间")
	public String getZhuanzheng() {
		return zhuanzheng;
	}

	public void setZhuanzheng(String zhuanzheng) {
		this.zhuanzheng = zhuanzheng;
	}
	
	@Length(min=0, max=64, message="调岗长度必须介于 0 和 64 之间")
	public String getTiaogang() {
		return tiaogang;
	}

	public void setTiaogang(String tiaogang) {
		this.tiaogang = tiaogang;
	}
	
	@Length(min=0, max=64, message="调薪长度必须介于 0 和 64 之间")
	public String getTiaoxin() {
		return tiaoxin;
	}

	public void setTiaoxin(String tiaoxin) {
		this.tiaoxin = tiaoxin;
	}
	
	@Length(min=0, max=64, message="执行时间长度必须介于 0 和 64 之间")
	public String getZhixingShijian() {
		return zhixingShijian;
	}

	public void setZhixingShijian(String zhixingShijian) {
		this.zhixingShijian = zhixingShijian;
	}
	
	@Length(min=0, max=64, message="备注长度必须介于 0 和 64 之间")
	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}