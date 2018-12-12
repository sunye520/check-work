/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.dao;

import com.yogee.youge.common.persistence.CrudDao;
import com.yogee.youge.common.persistence.annotation.MyBatisDao;
import com.yogee.youge.modules.check.entity.CheckLeavePermit;

/**
 * 假条DAO接口
 * @author zhangjian
 * @version 2018-12-12
 */
@MyBatisDao
public interface CheckLeavePermitDao extends CrudDao<CheckLeavePermit> {
	
}