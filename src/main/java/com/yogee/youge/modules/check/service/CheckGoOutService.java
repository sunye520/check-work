/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import com.yogee.youge.common.persistence.Page;
import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.dao.CheckGoOutDao;
import com.yogee.youge.modules.check.entity.CheckGoOut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外出Service
 * @author zhangjian
 * @version 2018-12-12
 */
@Service
@Transactional(readOnly = true)
public class CheckGoOutService extends CrudService<CheckGoOutDao, CheckGoOut> {

	public CheckGoOut get(String id) {
		return super.get(id);
	}
	
	public List<CheckGoOut> findList(CheckGoOut checkGoOut) {
		return super.findList(checkGoOut);
	}
	
	public Page<CheckGoOut> findPage(Page<CheckGoOut> page, CheckGoOut checkGoOut) {
		return super.findPage(page, checkGoOut);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckGoOut checkGoOut) {
		super.save(checkGoOut);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckGoOut checkGoOut) {
		super.delete(checkGoOut);
	}


	//根据用户id查询所有出差信息，按照审核状态倒叙
	public List<CheckGoOut> queryAllByUserid(String total, String count,String userId){
		Map map = new HashMap();
		map.put("total",Integer.parseInt(total));
		map.put("count",Integer.parseInt(count));
		map.put("userId",userId);
		return dao.queryAllByUserid(map);
	}


	
}