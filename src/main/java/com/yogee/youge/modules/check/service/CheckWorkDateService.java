/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yogee.youge.common.persistence.Page;
import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.entity.CheckWorkDate;
import com.yogee.youge.modules.check.dao.CheckWorkDateDao;

/**
 * 排班Service
 * @author cheng
 * @version 2018-12-12
 */
@Service
@Transactional(readOnly = true)
public class CheckWorkDateService extends CrudService<CheckWorkDateDao, CheckWorkDate> {

	public CheckWorkDate get(String id) {
		return super.get(id);
	}
	
	public List<CheckWorkDate> findList(CheckWorkDate checkWorkDate) {
		return super.findList(checkWorkDate);
	}
	
	public Page<CheckWorkDate> findPage(Page<CheckWorkDate> page, CheckWorkDate checkWorkDate) {
		return super.findPage(page, checkWorkDate);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckWorkDate checkWorkDate) {
		super.save(checkWorkDate);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckWorkDate checkWorkDate) {
		super.delete(checkWorkDate);
	}

	/**
	 * 根据日期找排班数据
	 * @param date
	 * @return
	 */
	public CheckWorkDate findByDate(String date) {
		return dao.findByDate(date);
	}

	/**
	 * 根据年月找排班数据
	 * @param date
	 * @return
	 */
	public List<CheckWorkDate> findByYearAndMonth(String date) {
		return dao.findByYearAndMonth(date);
	}
}