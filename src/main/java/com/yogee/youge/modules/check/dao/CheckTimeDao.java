/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.dao;

import com.yogee.youge.common.persistence.CrudDao;
import com.yogee.youge.common.persistence.annotation.MyBatisDao;
import com.yogee.youge.modules.check.entity.CheckTime;

/**
 * 上班时间DAO接口
 * @author cheng
 * @version 2018-12-12
 */
@MyBatisDao
public interface CheckTimeDao extends CrudDao<CheckTime> {

    CheckTime getOne();

}