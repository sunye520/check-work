/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yogee.youge.common.persistence.Page;
import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.entity.CheckBusinessDate;
import com.yogee.youge.modules.check.dao.CheckBusinessDateDao;

/**
 * 时间设置Service
 * @author sunye
 * @version 2018-12-17
 */
@Service
@Transactional(readOnly = true)
public class CheckBusinessDateService extends CrudService<CheckBusinessDateDao, CheckBusinessDate> {

	public CheckBusinessDate get(String id) {
		return super.get(id);
	}
	
	public List<CheckBusinessDate> findList(CheckBusinessDate checkBusinessDate) {
		return super.findList(checkBusinessDate);
	}
	
	public Page<CheckBusinessDate> findPage(Page<CheckBusinessDate> page, CheckBusinessDate checkBusinessDate) {
		return super.findPage(page, checkBusinessDate);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckBusinessDate checkBusinessDate) {
		super.save(checkBusinessDate);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckBusinessDate checkBusinessDate) {
		super.delete(checkBusinessDate);
	}
	
}