/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import com.yogee.youge.common.persistence.Page;
import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.dao.CheckAdjustingPostsDao;
import com.yogee.youge.modules.check.entity.CheckAdjustingPosts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调岗记录表Service
 * @author zhangjian
 * @version 2018-12-14
 */
@Service
@Transactional(readOnly = true)
public class CheckAdjustingPostsService extends CrudService<CheckAdjustingPostsDao, CheckAdjustingPosts> {

	public CheckAdjustingPosts get(String id) {
		return super.get(id);
	}
	
	public List<CheckAdjustingPosts> findList(CheckAdjustingPosts checkAdjustingPosts) {
		return super.findList(checkAdjustingPosts);
	}
	
	public Page<CheckAdjustingPosts> findPage(Page<CheckAdjustingPosts> page, CheckAdjustingPosts checkAdjustingPosts) {
		return super.findPage(page, checkAdjustingPosts);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckAdjustingPosts checkAdjustingPosts) {
		super.save(checkAdjustingPosts);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckAdjustingPosts checkAdjustingPosts) {
		super.delete(checkAdjustingPosts);
	}

	// 根据创建时间和部门 查询调岗记录总和（部门）
	public List<Map> findCountByTimeDepartment(String bumen , String date) {
		Map map = new HashMap();
		map.put("bumen",bumen);
		map.put("date",date);
		return dao.findCountByTimeDepartment(map);
	}
	// 根据创建时间和技术岗位 查询调岗记录总和（技术）
	public List<Map> findCountByTimeTechnology(String bumen , String date) {
		Map map = new HashMap();
		map.put("bumen",bumen);
		map.put("date",date);
		return dao.findCountByTimeTechnology(map);
	}

	// 根据创建时间查询调岗记录总和（合计）
	public List<Map> findTimeTechnologyCount(String date) {
		Map map = new HashMap();
		map.put("date",date);
		return dao.findTimeTechnologyCount(map);
	}










	
}