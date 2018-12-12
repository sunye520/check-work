/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.dao;

import com.yogee.youge.common.persistence.CrudDao;
import com.yogee.youge.common.persistence.annotation.MyBatisDao;
import com.yogee.youge.modules.check.entity.CheckGoOut;

import java.util.List;
import java.util.Map;

/**
 * 外出DAO接口
 * @author zhangjian
 * @version 2018-12-12
 */
@MyBatisDao
public interface CheckGoOutDao extends CrudDao<CheckGoOut> {

    //根据用户id查询所有出差信息，按照审核状态倒叙
    List<CheckGoOut> queryAllByUserid(Map map);

    String queryAllCount();
}