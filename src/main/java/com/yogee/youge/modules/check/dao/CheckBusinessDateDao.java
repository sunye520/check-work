/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.dao;

import com.yogee.youge.common.persistence.CrudDao;
import com.yogee.youge.common.persistence.annotation.MyBatisDao;
import com.yogee.youge.modules.check.entity.CheckBusinessDate;

/**
 * 时间设置DAO接口
 * @author sunye
 * @version 2018-12-17
 */
@MyBatisDao
public interface CheckBusinessDateDao extends CrudDao<CheckBusinessDate> {
	
}