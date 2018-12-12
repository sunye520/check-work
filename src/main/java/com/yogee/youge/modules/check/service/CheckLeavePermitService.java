/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yogee.youge.common.persistence.Page;
import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.entity.CheckLeavePermit;
import com.yogee.youge.modules.check.dao.CheckLeavePermitDao;

/**
 * 假条Service
 * @author zhangjian
 * @version 2018-12-12
 */
@Service
@Transactional(readOnly = true)
public class CheckLeavePermitService extends CrudService<CheckLeavePermitDao, CheckLeavePermit> {

	public CheckLeavePermit get(String id) {
		return super.get(id);
	}
	
	public List<CheckLeavePermit> findList(CheckLeavePermit checkLeavePermit) {
		return super.findList(checkLeavePermit);
	}
	
	public Page<CheckLeavePermit> findPage(Page<CheckLeavePermit> page, CheckLeavePermit checkLeavePermit) {
		return super.findPage(page, checkLeavePermit);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckLeavePermit checkLeavePermit) {
		super.save(checkLeavePermit);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckLeavePermit checkLeavePermit) {
		super.delete(checkLeavePermit);
	}
	
}