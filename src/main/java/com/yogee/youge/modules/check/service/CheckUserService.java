/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import com.yogee.youge.common.persistence.Page;
import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.dao.CheckUserDao;
import com.yogee.youge.modules.check.entity.CheckUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工信息Service
 * @author sunye
 * @version 2018-12-13
 */
@Service
@Transactional(readOnly = true)
public class CheckUserService extends CrudService<CheckUserDao, CheckUser> {

	public CheckUser get(String id) {
		return super.get(id);
	}
	
	public List<CheckUser> findList(CheckUser checkUser) {
		return super.findList(checkUser);
	}
	
	public Page<CheckUser> findPage(Page<CheckUser> page, CheckUser checkUser) {
		return super.findPage(page, checkUser);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckUser checkUser) {
		super.save(checkUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckUser checkUser) {
		super.delete(checkUser);
	}

	/**
	 * 根据搜索查找用户
	 * @param nameLike
	 * @param total
	 * @param count
	 * @return
	 */
	public List<Map> findListByNameLikeAndCounts(String nameLike, String total, String count) {
		Map map = new HashMap();
		map.put("nameLike",nameLike);
		map.put("total",Integer.parseInt(total));
		map.put("count",Integer.parseInt(count));
		return dao.findListByNameLikeAndCounts(map);
	}


	/**
	 * 根据工号查找用户
	 * @return
	 */
	public CheckUser findBynumber(String number) {
		Map map = new HashMap();
		map.put("number",number);
		return dao.findBynumber(map);
	}

	/**
	 * 所有员工
	 * @return
	 */
	public List<CheckUser> findAll() {
		return dao.findAll();
	}
	//根据员工姓名查询
	public List<CheckUser> findListByName(String name) {
		Map map = new HashMap();
		map.put("name",name);
		return dao.findListByName(map);
	}


	public List<CheckUser> findByNumber (String number){
		Map map = new HashMap();
		map.put("number",number);
		return dao.findByNumber(map);
	}
	public List<CheckUser> findByName (String name){
		Map map = new HashMap();
		map.put("name",name);
		return dao.findByName(map);
	}

	/**
	 * 所有在职员工总人数
	 * @param checkUser
	 * @return
	 */
	public long findAllCount(CheckUser checkUser){return dao.findAllCount(checkUser);}
}