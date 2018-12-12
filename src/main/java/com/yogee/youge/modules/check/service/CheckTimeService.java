/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yogee.youge.common.persistence.Page;
import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.entity.CheckTime;
import com.yogee.youge.modules.check.dao.CheckTimeDao;

/**
 * 上班时间Service
 * @author cheng
 * @version 2018-12-12
 */
@Service
@Transactional(readOnly = true)
public class CheckTimeService extends CrudService<CheckTimeDao, CheckTime> {

	public CheckTime get(String id) {
		return super.get(id);
	}
	
	public List<CheckTime> findList(CheckTime checkTime) {
		return super.findList(checkTime);
	}
	
	public Page<CheckTime> findPage(Page<CheckTime> page, CheckTime checkTime) {
		return super.findPage(page, checkTime);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckTime checkTime) {
		super.save(checkTime);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckTime checkTime) {
		super.delete(checkTime);
	}

	/**
	 * 获取唯一数据
	 * @return
	 */
	public CheckTime getOne() {
		return dao.getOne();
	}
}