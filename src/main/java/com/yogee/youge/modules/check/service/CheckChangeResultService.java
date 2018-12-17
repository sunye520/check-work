/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import com.yogee.youge.common.persistence.Page;
import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.dao.CheckChangeResultDao;
import com.yogee.youge.modules.check.entity.CheckChangeResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 人员异动结果表Service
 * @author zhangjian
 * @version 2018-12-14
 */
@Service
@Transactional(readOnly = true)
public class CheckChangeResultService extends CrudService<CheckChangeResultDao, CheckChangeResult> {

	public CheckChangeResult get(String id) {
		return super.get(id);
	}
	
	public List<CheckChangeResult> findList(CheckChangeResult checkChangeResult) {
		return super.findList(checkChangeResult);
	}
	
	public Page<CheckChangeResult> findPage(Page<CheckChangeResult> page, CheckChangeResult checkChangeResult) {
		return super.findPage(page, checkChangeResult);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckChangeResult checkChangeResult) {
		super.save(checkChangeResult);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckChangeResult checkChangeResult) {
		super.delete(checkChangeResult);
	}
	
}