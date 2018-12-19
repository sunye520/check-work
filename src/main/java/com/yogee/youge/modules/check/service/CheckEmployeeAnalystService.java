/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.dao.CheckUserDao;
import com.yogee.youge.modules.check.entity.CheckUser;
import com.yogee.youge.modules.sys.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工信息Service
 *
 * @author sunye
 * @version 2018-12-13
 */
@Service
@Transactional(readOnly = true)
public class CheckEmployeeAnalystService extends CrudService<CheckUserDao, CheckUser> {

    @Autowired
    DictService dictService;

    /**
     * 根据性别查询人数
     */
    public List<Map> findCheckUserByGender() {
        return dao.findCheckUserByGender();
    }

    /**
     * 根据学历查询人数
     */
    public List<Map> findCheckUserByEducation() {
        return dao.findCheckUserByEducation();
    }

    /**
     * 根据技术类型查询人数
     */
    public List<Map> findCheckUserByTechnology() {
        return dao.findCheckUserByTechnology();
    }

    /**
     * 根据层级类别查询人数
     */
    public List<Map> findCheckUserByLevel() {
        return dao.findCheckUserByLevel();
    }

    /**
     * 根据政治面貌查询人数
     */
    public List<Map> findCheckUserByPolitics() {
        return dao.findCheckUserByPolitics();
    }

    /**
     * 根据年龄分组
     *
     * @return
     */
    public List<Map<String,Object>> findCheckUserByAge() {
        /*Dict dict = new Dict();
        dict.setType("age");
        List<Dict> ageGroup = dictService.findList(dict);*/
        List<Map> ageMaps = dao.findCheckUserAge();//根据生日查询员工的年龄
        int a = 0;//20岁以下的人数
        int b = 0;//21岁~30岁的人数
        int c = 0;//31岁~40岁的人数
        int d = 0;//41岁~50岁的人数
        int e = 0;//51岁~60岁的人数
        for (Map m : ageMaps) {
            int age = Integer.parseInt(m.get("age").toString());
            if (age <= 20) {
                a++;
            } else if (age <= 30) {
                b++;
            } else if (age <= 40) {
                c++;
            } else if (age <= 50) {
                d++;
            } else if (age <= 60) {
                e++;
            }
        }
        System.out.println(a + "," + b + "," + c + "," + d + "," + e);
        List<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> m1 = new HashMap<>();
        m1.put("name", "≤20");
        m1.put("count", a);
        maps.add(m1);
        Map<String, Object> m2 = new HashMap<>();
        m2.put("name", "20-30");
        m2.put("count", b);
        maps.add(m2);
        Map<String, Object> m3 = new HashMap<>();
        m3.put("name", "31-40");
        m3.put("count", c);
        maps.add(m3);
        Map<String, Object> m4 = new HashMap<>();
        m4.put("name", "41-50");
        m4.put("count", d);
        maps.add(m4);
        Map<String, Object> m5 = new HashMap<>();
        m5.put("name", "51-60");
        m5.put("count", e);
        maps.add(m5);

        return maps;
    }

    public List<Map<String, Object>> findCheckUserByWorkingYears() {

        List<Map> workingYearsMaps = dao.findCheckUserWorkingYears();//根据入职时间查询员工的司龄
        int a = 0;//1年及其以下的人数
        int b = 0;//1年以上3年以下的人数
        int c = 0;//3年以上5年以下的人数
        int d = 0;//5年以上10年以下的人数
        int e = 0;//10年以上的人数

        for (Map m : workingYearsMaps) {
            int years = Integer.parseInt(m.get("years").toString());
            if (years <= 1) {
                a++;
            } else if (years <= 3) {
                b++;
            } else if (years <= 5) {
                c++;
            } else if (years <= 10) {
                d++;
            } else {
                e++;
            }
        }

        List<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> m1 = new HashMap<>();
        m1.put("name", "1年及其以下");
        m1.put("count", a);
        maps.add(m1);
        Map<String, Object> m2 = new HashMap<>();
        m2.put("name", "1年以上3年以下");
        m2.put("count", b);
        maps.add(m2);
        Map<String, Object> m3 = new HashMap<>();
        m3.put("name", "3年以上5年以下");
        m3.put("count", c);
        maps.add(m3);
        Map<String, Object> m4 = new HashMap<>();
        m4.put("name", "5年以上10年以下5年以上10年以下");
        m4.put("count", d);
        maps.add(m4);
        Map<String, Object> m5 = new HashMap<>();
        m5.put("name", "10年以上");
        m5.put("count", e);
        maps.add(m5);
        return maps;
    }

    public List<Map> findCheckUserByDepartment() {
        return dao.findCheckUserByDepartment();
    }
}