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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<CheckChangeResult> queryChangeResultByType(String date,String type){
		Map map = new HashMap();
		map.put("date",date);
		map.put("type",type);
		return dao.queryChangeResultByType(map);
	}

	public List<CheckChangeResult> queryChangeResultCount(String date){
		Map map = new HashMap();
		map.put("date",date);
		return dao.queryChangeResultCount(map);
	}


	// 根据创建时间查询调岗记录总和（技术）
	public List<CheckChangeResult> selectPokemons(List<String> list , String bumen) {
		Map map = new HashMap();
		map.put("list",list);
		map.put("bumen",bumen);
		return dao.selectPokemons(map);
	}
}