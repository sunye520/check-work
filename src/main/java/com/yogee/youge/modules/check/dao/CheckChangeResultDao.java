/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.dao;

import com.yogee.youge.common.persistence.CrudDao;
import com.yogee.youge.common.persistence.annotation.MyBatisDao;
import com.yogee.youge.modules.check.entity.CheckChangeResult;

/**
 * 人员异动结果表DAO接口
 * @author zhangjian
 * @version 2018-12-14
 */
@MyBatisDao
public interface CheckChangeResultDao extends CrudDao<CheckChangeResult> {
	
}