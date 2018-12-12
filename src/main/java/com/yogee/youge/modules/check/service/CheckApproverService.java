/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yogee.youge.common.persistence.Page;
import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.entity.CheckApprover;
import com.yogee.youge.modules.check.dao.CheckApproverDao;

/**
 * 审批Service
 * @author zhangjian
 * @version 2018-12-12
 */
@Service
@Transactional(readOnly = true)
public class CheckApproverService extends CrudService<CheckApproverDao, CheckApprover> {

	public CheckApprover get(String id) {
		return super.get(id);
	}
	
	public List<CheckApprover> findList(CheckApprover checkApprover) {
		return super.findList(checkApprover);
	}
	
	public Page<CheckApprover> findPage(Page<CheckApprover> page, CheckApprover checkApprover) {
		return super.findPage(page, checkApprover);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckApprover checkApprover) {
		super.save(checkApprover);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckApprover checkApprover) {
		super.delete(checkApprover);
	}
	
}