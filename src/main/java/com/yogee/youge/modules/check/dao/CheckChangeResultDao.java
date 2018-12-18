/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.dao;

import com.yogee.youge.common.persistence.CrudDao;
import com.yogee.youge.common.persistence.annotation.MyBatisDao;
import com.yogee.youge.modules.check.entity.CheckChangeResult;

import java.util.List;
import java.util.Map;

/**
 * 人员异动结果表DAO接口
 * @author zhangjian
 * @version 2018-12-14
 */
@MyBatisDao
public interface CheckChangeResultDao extends CrudDao<CheckChangeResult> {

    List<CheckChangeResult> queryChangeResultByType(Map map);

    List<CheckChangeResult> queryChangeResultCount(Map map);

    List<CheckChangeResult> selectPokemons(Map map);
}