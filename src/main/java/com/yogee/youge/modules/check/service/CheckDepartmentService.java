/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yogee.youge.common.persistence.Page;
import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.entity.CheckDepartment;
import com.yogee.youge.modules.check.dao.CheckDepartmentDao;

/**
 * 部门信息Service
 * @author sunye
 * @version 2018-12-19
 */
@Service
@Transactional(readOnly = true)
public class CheckDepartmentService extends CrudService<CheckDepartmentDao, CheckDepartment> {

	public CheckDepartment get(String id) {
		return super.get(id);
	}
	
	public List<CheckDepartment> findList(CheckDepartment checkDepartment) {
		return super.findList(checkDepartment);
	}
	
	public Page<CheckDepartment> findPage(Page<CheckDepartment> page, CheckDepartment checkDepartment) {
		return super.findPage(page, checkDepartment);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckDepartment checkDepartment) {
		super.save(checkDepartment);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckDepartment checkDepartment) {
		super.delete(checkDepartment);
	}

	@Transactional(readOnly = false)
	public void insert(CheckDepartment checkDepartmentParent) {
		dao.insert(checkDepartmentParent);
	}

	public List<CheckDepartment> findDepartment() {
		return dao.findDepartment();
	}

	public int findDepartmentByName(String name) {
		return dao.findDepartmentByName(name);
	}

	public List<CheckDepartment> findParentDepartment() {
		return dao.findParentDepartment();
	}
}