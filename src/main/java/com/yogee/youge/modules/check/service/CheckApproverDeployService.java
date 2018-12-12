/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import com.google.common.collect.Maps;
import com.yogee.youge.common.persistence.Page;
import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.dao.CheckApproverDeployDao;
import com.yogee.youge.modules.check.entity.CheckApproverDeploy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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


	//根据部门，请假类型，时差查询审核人信息
	public CheckApproverDeploy queryDeployInfo(String departmentId,String leaveType,String time){
		Map map= Maps.newHashMap();
		map.put("departmentId",departmentId);
		map.put("leaveType",leaveType);
		map.put("time",time);
		return dao.queryDeployInfo(map);
	}

	
}