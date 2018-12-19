/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.yogee.youge.common.persistence.DataEntity;

/**
 * 部门信息Entity
 * @author sunye
 * @version 2018-12-19
 */
public class CheckDepartment extends DataEntity<CheckDepartment> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 部门名称
	private String parentId;		// 父级部门
	private String parentName;		// 父级部门名称
	private String departmentType;		// 部门类型（1：一级部门  2：二级部门）
	private String sonId;		// 儿子部门
	private String sonName;		// 儿子部门名称

	
	public CheckDepartment() {
		super();
	}

	public CheckDepartment(String id){
		super(id);
	}

	@Length(min=0, max=64, message="部门名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Length(min=0, max=64, message="父级部门名称长度必须介于 0 和 64 之间")
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	@Length(min=0, max=255, message="部门类型（1：一级部门  2：二级部门）长度必须介于 0 和 255 之间")
	public String getDepartmentType() {
		return departmentType;
	}

	public void setDepartmentType(String departmentType) {
		this.departmentType = departmentType;
	}

	public String getSonId() {
		return sonId;
	}

	public void setSonId(String sonId) {
		this.sonId = sonId;
	}

	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
}