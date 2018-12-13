/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.dao;

import com.yogee.youge.common.persistence.CrudDao;
import com.yogee.youge.common.persistence.annotation.MyBatisDao;
import com.yogee.youge.modules.check.entity.CheckUser;

/**
 * 员工信息DAO接口
 * @author sunye
 * @version 2018-12-13
 */
@MyBatisDao
public interface CheckUserDao extends CrudDao<CheckUser> {
	
}