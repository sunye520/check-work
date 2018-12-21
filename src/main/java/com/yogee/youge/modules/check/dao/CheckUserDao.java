/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.dao;

import com.yogee.youge.common.persistence.CrudDao;
import com.yogee.youge.common.persistence.annotation.MyBatisDao;
import com.yogee.youge.modules.check.entity.CheckUser;

import java.util.List;
import java.util.Map;

/**
 * 员工信息DAO接口
 * @author sunye
 * @version 2018-12-13
 */
@MyBatisDao
public interface CheckUserDao extends CrudDao<CheckUser> {

    List<Map> findListByNameLikeAndCounts(Map map);

    CheckUser findBynumber(Map map);

    List<CheckUser> findAll();

    List<CheckUser> findListByName(Map map);
    List<CheckUser> findByNumber(Map map);

    List<CheckUser> findByName(Map map);

    long findAllCount(CheckUser checkUser);

    /**
     * 按性别查询人员分布
     * @return
     */
    List<Map> findCheckUserByGender();
    /**
     * 按学历查询人员分布
     * @return
     */
    List<Map> findCheckUserByEducation();

    /**
     * 按技术类别查询人员分布
     * @return
     */
    List<Map> findCheckUserByTechnology();

    /**
     * 按层级查询人员分布
     * @return
     */
    List<Map> findCheckUserByLevel();

    /**
     * 按政治面貌查询人员分布
     * @return
     */
    List<Map> findCheckUserByPolitics();

    /**
     * 查询每个人的年龄分布
     * @return
     */
    List<Map> findCheckUserAge();

    /**
     * 查询每个人的司龄分布
     * @return
     */
    List<Map> findCheckUserWorkingYears();

    /**
     * 按部门统计人员
     * @return
     */
    List<Map> findCheckUserByDepartment();

    /**
     *按部门统计人数
     * @param user
     * @return
     */
    Map<String,Object> findExcelDataByDepartment(CheckUser user);

    /**
     * 按部门查询每个人年龄
     * @param name
     * @return
     */
    List<Map> findCheckUserAgeByDepartment(String name);

    /**
     * 按部门查询每个人司龄
     * @param name
     * @return
     */
    List<Map> findCheckUserWorkingYearsByDepartment(String name);

    /**
     * 根据技术类别统计人数
     * @param user
     * @return
     */
    Map<String,Object> findExcelDataByTechnology(CheckUser user);

    /**
     * 根据技术类别查询每个人年龄
     * @param name
     * @return
     */
    List<Map> findCheckUserAgeByTechnology(String name);

    /**
     * 根据技术类别查询每个人司龄
     * @param name
     * @return
     */
    List<Map> findCheckUserWorkingYearsByTechnology(String name);
}