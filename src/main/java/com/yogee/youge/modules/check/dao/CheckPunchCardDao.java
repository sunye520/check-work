/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.dao;

import com.yogee.youge.common.persistence.CrudDao;
import com.yogee.youge.common.persistence.annotation.MyBatisDao;
import com.yogee.youge.modules.check.entity.CheckPunchCard;

import java.util.List;
import java.util.Map;

/**
 * 打卡记录信息DAO接口
 * @author sunye
 * @version 2018-12-18
 */
@MyBatisDao
public interface CheckPunchCardDao extends CrudDao<CheckPunchCard> {

    List<CheckPunchCard> findCountByNameAndPunchDate(Map map);

    void deleteByNameAndPunchDate(Map map);

    void saveList(Map map);

    List<CheckPunchCard> findByYearMonth(Map map);

    CheckPunchCard findCountByNumberAndPunchDate(Map map);

    List<CheckPunchCard> findNumberByYearMonth(Map map);

    List<CheckPunchCard> findAskLiveTimeByNumber(Map map);

    List<CheckPunchCard> findShangChiByNumber(Map map);

    List<CheckPunchCard> findXiaZaoByNumber(Map map);
}