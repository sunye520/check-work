/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yogee.youge.common.persistence.Page;
import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.entity.CheckPunchCard;
import com.yogee.youge.modules.check.dao.CheckPunchCardDao;

/**
 * 打卡记录信息Service
 * @author sunye
 * @version 2018-12-18
 */
@Service
@Transactional(readOnly = true)
public class CheckPunchCardService extends CrudService<CheckPunchCardDao, CheckPunchCard> {

	public CheckPunchCard get(String id) {
		return super.get(id);
	}
	
	public List<CheckPunchCard> findList(CheckPunchCard checkPunchCard) {
		return super.findList(checkPunchCard);
	}
	
	public Page<CheckPunchCard> findPage(Page<CheckPunchCard> page, CheckPunchCard checkPunchCard) {
		return super.findPage(page, checkPunchCard);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckPunchCard checkPunchCard) {
		super.save(checkPunchCard);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckPunchCard checkPunchCard) {
		super.delete(checkPunchCard);
	}

	public List<CheckPunchCard> findCountByNameAndPunchDate(String name, String punchDate) {
		Map map = new HashMap<>();
		map.put("name",name);
		map.put("punchDate",punchDate);
		return dao.findCountByNameAndPunchDate(map);
	}
	@Transactional(readOnly = false)
	public void deleteByNameAndPunchDate(String name, String punchDate) {
		Map map = new HashMap<>();
		map.put("name",name);
		map.put("punchDate",punchDate);
		dao.deleteByNameAndPunchDate(map);
	}
	@Transactional(readOnly = false)
	public void saveList(List<Map<String, String>> list) {
		Map map = new HashMap<>();
		map.put("list",list);
		dao.saveList(map);
	}

	public List<CheckPunchCard> findByYearMonth(String punchDate) {
		Map map = new HashMap<>();
		map.put("punchDate",punchDate);
		return dao.findByYearMonth(map);
	}

	public CheckPunchCard findCountByNumberAndPunchDate(String number, String punchDate) {
		Map map = new HashMap<>();
		map.put("punchDate",punchDate);
		map.put("number",number);
		return dao.findCountByNumberAndPunchDate(map);
	}

	public List<CheckPunchCard> findNumberByYearMonth(String punchDate) {
		Map map = new HashMap<>();
		map.put("punchDate",punchDate);
		return dao.findNumberByYearMonth(map);
	}

	public List<CheckPunchCard> findAskLiveTimeByNumber(String number,String punchDate) {
		Map map = new HashMap<>();
		map.put("number",number);
		map.put("punchDate",punchDate);
		return dao.findAskLiveTimeByNumber(map);
	}

	public List<CheckPunchCard> findShangChiByNumber(String number, String punchDate) {
		Map map = new HashMap<>();
		map.put("number",number);
		map.put("punchDate",punchDate);
		return dao.findShangChiByNumber(map);
	}

	public List<CheckPunchCard> findXiaZaoByNumber(String number, String punchDate) {
		Map map = new HashMap<>();
		map.put("number",number);
		map.put("punchDate",punchDate);
		return dao.findXiaZaoByNumber(map);
	}
}