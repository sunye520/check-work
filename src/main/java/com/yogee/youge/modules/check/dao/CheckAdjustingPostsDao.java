/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.dao;

import com.yogee.youge.common.persistence.CrudDao;
import com.yogee.youge.common.persistence.annotation.MyBatisDao;
import com.yogee.youge.modules.check.entity.CheckAdjustingPosts;

import java.util.List;
import java.util.Map;

/**
 * 调岗记录表DAO接口
 * @author zhangjian
 * @version 2018-12-14
 */
@MyBatisDao
public interface CheckAdjustingPostsDao extends CrudDao<CheckAdjustingPosts> {

    List<Map> findCountByTimeDepartment(Map map);
    List<Map> findCountByTimeTechnology(Map map);



    List<Map> findTimeTechnologyCount(Map map);


}