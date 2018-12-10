/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.sys.service;

import java.util.List;

import com.yogee.youge.modules.sys.dao.AreaDao;
import com.yogee.youge.modules.sys.entity.Area;
import com.yogee.youge.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yogee.youge.common.service.TreeService;

/**
 * 区域Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	@Autowired
	private AreaDao areaDao;

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}

	//根据级别和上级id获取数据
	public List<Area> getByType(String level, String pId) {
		Area area = new Area();
		area.setType(level);
		area.setParentIds(pId);
		return areaDao.getByType(area);
	}

	//根据级别和上级id获取数据
	public List<Area> getByScope(String level) {
		Area area = new Area();
		area.setType(level);
		return areaDao.getByScope(area);
	}

}
