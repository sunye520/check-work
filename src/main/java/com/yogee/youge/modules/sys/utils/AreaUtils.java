/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.sys.utils;

import com.yogee.youge.common.utils.SpringContextHolder;
import com.yogee.youge.modules.sys.dao.AreaDao;
import com.yogee.youge.modules.sys.entity.Area;

import java.util.List;

/**
 * 区域工具类
 * @author Haipeng.Ren
 * @version 2017-7-27
 */

public class AreaUtils {
	private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);

	public static List<Area> getAreaList(String level, String pId) {
		Area area = new Area();
		area.setType(level);
		area.setParentIds(pId);
		return areaDao.getByType(area);
	}
}
