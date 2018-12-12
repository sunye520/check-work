/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yogee.youge.common.persistence.Page;
import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.entity.CheckApproverDeploy;
import com.yogee.youge.modules.check.dao.CheckApproverDeployDao;

/**
 * 审核人员配置表Service
 * @author zhangjian
 * @version 2018-12-12
 */
@Service
@Transactional(readOnly = true)
public class CheckApproverDeployService extends CrudService<CheckApproverDeployDao, CheckApproverDeploy> {

	public CheckApproverDeploy get(String id) {
		return super.get(id);
	}
	
	public List<CheckApproverDeploy> findList(CheckApproverDeploy checkApproverDeploy) {
		return super.findList(checkApproverDeploy);
	}
	
	public Page<CheckApproverDeploy> findPage(Page<CheckApproverDeploy> page, CheckApproverDeploy checkApproverDeploy) {
		return super.findPage(page, checkApproverDeploy);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckApproverDeploy checkApproverDeploy) {
		super.save(checkApproverDeploy);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckApproverDeploy checkApproverDeploy) {
		super.delete(checkApproverDeploy);
	}
	
}