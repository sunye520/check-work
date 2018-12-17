/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.entity;

import org.hibernate.validator.constraints.Length;

import com.yogee.youge.common.persistence.DataEntity;

/**
 * 调岗记录表Entity
 * @author zhangjian
 * @version 2018-12-14
 */
public class CheckAdjustingPosts extends DataEntity<CheckAdjustingPosts> {
	
	private static final long serialVersionUID = 1L;
	private String bumen;		// 部门
	private String erjiBumen;		// 二级部门
	private String gangwei;		// 岗位
	private String jishuLeibie;		// 技术类别
	private String fromBumen;		//原部门
	private String fromErjiBumen;  // 原二级部门
	private String fromGangwei;   //原岗位



	
	public CheckAdjustingPosts() {
		super();
	}

	public CheckAdjustingPosts(String id){
		super(id);
	}

	@Length(min=0, max=64, message="部门长度必须介于 0 和 64 之间")
	public String getBumen() {
		return bumen;
	}

	public void setBumen(String bumen) {
		this.bumen = bumen;
	}
	
	@Length(min=0, max=64, message="二级部门长度必须介于 0 和 64 之间")
	public String getErjiBumen() {
		return erjiBumen;
	}

	public void setErjiBumen(String erjiBumen) {
		this.erjiBumen = erjiBumen;
	}
	
	@Length(min=0, max=64, message="岗位长度必须介于 0 和 64 之间")
	public String getGangwei() {
		return gangwei;
	}

	public void setGangwei(String gangwei) {
		this.gangwei = gangwei;
	}
	
	@Length(min=0, max=64, message="技术类别长度必须介于 0 和 64 之间")
	public String getJishuLeibie() {
		return jishuLeibie;
	}

	public void setJishuLeibie(String jishuLeibie) {
		this.jishuLeibie = jishuLeibie;
	}

	public String getFromBumen() {
		return fromBumen;
	}

	public void setFromBumen(String fromBumen) {
		this.fromBumen = fromBumen;
	}

	public String getFromErjiBumen() {
		return fromErjiBumen;
	}

	public void setFromErjiBumen(String fromErjiBumen) {
		this.fromErjiBumen = fromErjiBumen;
	}

	public String getFromGangwei() {
		return fromGangwei;
	}

	public void setFromGangwei(String fromGangwei) {
		this.fromGangwei = fromGangwei;
	}
}