/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.dao;

import com.yogee.youge.common.persistence.CrudDao;
import com.yogee.youge.common.persistence.annotation.MyBatisDao;
import com.yogee.youge.modules.check.entity.CheckDepartment;

import java.util.List;
import java.util.Map;

/**
 * 部门信息DAO接口
 * @author sunye
 * @version 2018-12-19
 */
@MyBatisDao
public interface CheckDepartmentDao extends CrudDao<CheckDepartment> {

    List<CheckDepartment> findDepartment();

    int findDepartmentByName(String name);

    List<CheckDepartment> findParentDepartment();

    List<String> findByDepartmentType(String str);

    List<CheckDepartment> querySonById(String id);

    String findByname(String name);

    int findDepartmentByNameAndId(Map map);

    void updateDepartment(Map map);
}