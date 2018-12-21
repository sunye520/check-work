/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.service;

import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.modules.check.dao.CheckUserDao;
import com.yogee.youge.modules.check.entity.CheckDepartment;
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
    CheckDepartmentService checkDepartmentService;
    @Autowired
    DictService dictService;

    /**
     * 根据性别查询人数
     */
    public List<Map<String, Object>> findCheckUserByGender() {
        long total = dao.findAllCount(new CheckUser());//在职员工总数
        List<Map<String, Object>> maps = dao.findCheckUserByGender();
        for (Map<String, Object> m : maps) {
            long count = (long) m.get("count");
            double d = count * 1.0 / total * 100.f;
            m.put("ratio", d + "%");
        }
        return maps;
    }

    /**
     * 根据学历查询人数
     */
    public List<Map<String, Object>> findCheckUserByEducation() {
        long total = dao.findAllCount(new CheckUser());//在职员工总数
        List<Map<String, Object>> maps = dao.findCheckUserByEducation();
        for (Map<String, Object> m : maps) {
            long count = (long) m.get("count");
            double d = count * 1.0 / total * 100.f;
            m.put("ratio", d + "%");
        }
        return maps;
    }

    /**
     * 根据技术类型查询人数
     */
    public List<Map<String, Object>> findCheckUserByTechnology() {
        long total = dao.findAllCount(new CheckUser());//在职员工总数
        List<Map<String, Object>> maps = dao.findCheckUserByTechnology();
        for (Map<String, Object> m : maps) {
            long count = (long) m.get("count");
            double d = count * 1.0 / total * 100.f;
            m.put("ratio", d + "%");
        }
        return maps;
    }

    /**
     * 根据层级类别查询人数
     */
    public List<Map<String, Object>> findCheckUserByLevel() {
        long total = dao.findAllCount(new CheckUser());//在职员工总数
        List<Map<String, Object>> maps = dao.findCheckUserByLevel();
        for (Map<String, Object> m : maps) {
            long count = (long) m.get("count");
            double d = count * 1.0 / total * 100.f;
            m.put("ratio", d + "%");
        }
        return maps;
    }

    /**
     * 根据政治面貌查询人数
     */
    public List<Map<String, Object>> findCheckUserByPolitics() {
        long total = dao.findAllCount(new CheckUser());//在职员工总数
        List<Map<String, Object>> maps = dao.findCheckUserByPolitics();
        for (Map<String, Object> m : maps) {
            long count = (long) m.get("count");
            double d = count * 1.0 / total * 100.f;
            m.put("ratio", d + "%");
        }
        return maps;
    }

    /**
     * 根据年龄分组
     *
     * @return
     */
    public List<Map<String, Object>> findCheckUserByAge() {
        long total = dao.findAllCount(new CheckUser());//在职员工总数
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
        double ar = a * 1.0 / total * 100.f;
        m1.put("name", "≤20");
        m1.put("count", a);
        m1.put("ratio", ar + "%");
        maps.add(m1);
        Map<String, Object> m2 = new HashMap<>();
        double br = b * 1.0 / total * 100.f;
        m2.put("name", "20-30");
        m2.put("count", b);
        m2.put("ratio", br + "%");
        maps.add(m2);
        Map<String, Object> m3 = new HashMap<>();
        double cr = c * 1.0 / total * 100.f;
        m3.put("name", "31-40");
        m3.put("count", c);
        m3.put("ratio", cr + "%");
        maps.add(m3);
        Map<String, Object> m4 = new HashMap<>();
        double dr = d * 1.0 / total * 100.f;
        m4.put("name", "41-50");
        m4.put("count", d);
        m4.put("ratio", dr + "%");
        maps.add(m4);
        Map<String, Object> m5 = new HashMap<>();
        double er = e * 1.0 / total * 100.f;
        m5.put("name", "51-60");
        m5.put("count", e);
        m5.put("ratio", er + "%");
        maps.add(m5);

        return maps;
    }

    public List<Map<String, Object>> findCheckUserByWorkingYears() {
        long total = dao.findAllCount(new CheckUser());//在职员工总数
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
        double ar = a * 1.0 / total * 100.f;
        Map<String, Object> m1 = new HashMap<>();
        m1.put("name", "1年及其以下");
        m1.put("count", a);
        m1.put("ratio", ar + "%");
        maps.add(m1);
        Map<String, Object> m2 = new HashMap<>();
        double br = b * 1.0 / total * 100.f;
        m2.put("name", "1年以上3年以下");
        m2.put("count", b);
        m2.put("ratio", br + "%");
        maps.add(m2);
        Map<String, Object> m3 = new HashMap<>();
        double cr = c * 1.0 / total * 100.f;
        m3.put("name", "3年以上5年以下");
        m3.put("count", c);
        m3.put("ratio", cr + "%");
        maps.add(m3);
        Map<String, Object> m4 = new HashMap<>();
        double dr = d * 1.0 / total * 100.f;
        m4.put("name", "5年以上10年以下");
        m4.put("count", d);
        m4.put("ratio", dr + "%");
        maps.add(m4);
        Map<String, Object> m5 = new HashMap<>();
        double er = e * 1.0 / total * 100.f;
        m5.put("name", "10年以上");
        m5.put("count", e);
        m5.put("ratio", er + "%");
        maps.add(m5);
        return maps;
    }

    public List<Map<String, Object>> findCheckUserByDepartment() {
        long total = dao.findAllCount(new CheckUser());//在职员工总数
        List<Map<String, Object>> maps = dao.findCheckUserByDepartment();
        for (Map<String, Object> m : maps) {
            long count = (long) m.get("count");
            double d = count * 1.0 / total * 100.f;
            m.put("ratio", d + "%");
        }
        return maps;
    }

    public Map<String, Object> findExcelData() {
        List<Map> departmentMaps = new ArrayList<>();
        List<Map> technologyMaps = new ArrayList<>();
        CheckDepartment department = new CheckDepartment();
        department.setDelFlag("0");
        department.setDepartmentType("1");//一级部门
        List<CheckDepartment> checkDepartments = checkDepartmentService.findList(department);
        //按部门查询各个属性人数
        for (CheckDepartment cd : checkDepartments) {
            CheckUser user = new CheckUser();
            user.setBumen(cd.getName());
            Map<String, Object> excelMap = dao.findExcelDataByDepartment(user);

            List<Map> ageMaps = dao.findCheckUserAgeByDepartment(cd.getName());
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
            excelMap.put("age1", a);
            excelMap.put("age2", b);
            excelMap.put("age3", c);
            excelMap.put("age4", d);
            excelMap.put("age5", e);
            List<Map> workingYearsMaps = dao.findCheckUserWorkingYearsByDepartment(cd.getName());
            int f = 0;//1年及其以下的人数
            int g = 0;//1年以上3年以下的人数
            int h = 0;//3年以上5年以下的人数
            int i = 0;//5年以上10年以下的人数
            int j = 0;//10年以上的人数
            for (Map m : workingYearsMaps) {
                int years = Integer.parseInt(m.get("years").toString());
                if (years <= 1) {
                    f++;
                } else if (years <= 3) {
                    g++;
                } else if (years <= 5) {
                    h++;
                } else if (years <= 10) {
                    i++;
                } else {
                    j++;
                }
            }
            excelMap.put("workingYears1", f);
            excelMap.put("workingYears2", g);
            excelMap.put("workingYears3", h);
            excelMap.put("workingYears4", i);
            excelMap.put("workingYears5", j);

            departmentMaps.add(excelMap);
        }

        //按技术类别查询各个属性人数
        List<String> dicts = dictService.findBytype("jishuleibie");
        for (String s : dicts) {
            CheckUser user = new CheckUser();
            user.setJishuLeibie(s);
            Map<String, Object> excelMap = dao.findExcelDataByTechnology(user);
            //按技术类别查询年龄,并分组
            List<Map> ageMaps = dao.findCheckUserAgeByTechnology(s);
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
            excelMap.put("age1", a);
            excelMap.put("age2", b);
            excelMap.put("age3", c);
            excelMap.put("age4", d);
            excelMap.put("age5", e);
            //按技术类别查询司龄,并分组
            List<Map> workingYearsMaps = dao.findCheckUserWorkingYearsByTechnology(s);
            int f = 0;//1年及其以下的人数
            int g = 0;//1年以上3年以下的人数
            int h = 0;//3年以上5年以下的人数
            int i = 0;//5年以上10年以下的人数
            int j = 0;//10年以上的人数
            for (Map m : workingYearsMaps) {
                int years = Integer.parseInt(m.get("years").toString());
                if (years <= 1) {
                    f++;
                } else if (years <= 3) {
                    g++;
                } else if (years <= 5) {
                    h++;
                } else if (years <= 10) {
                    i++;
                } else {
                    j++;
                }
            }
            excelMap.put("workingYears1", f);
            excelMap.put("workingYears2", g);
            excelMap.put("workingYears3", h);
            excelMap.put("workingYears4", i);
            excelMap.put("workingYears5", j);
            technologyMaps.add(excelMap);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("department", departmentMaps);
        map.put("technology", technologyMaps);
        //合计

        Map<String, Object> totalMap = dao.findExcelDataByDepartment(new CheckUser());
        List<Map<String, Object>> ageMap = findCheckUserByAge();//年龄分布
        for (Map m : ageMap) {
            if ("≤20".equals(m.get("name"))) {
                totalMap.put("age1", m.get("count"));
            } else if ("20-30".equals(m.get("name"))) {
                totalMap.put("age2", m.get("count"));
            } else if ("31-40".equals(m.get("name"))) {
                totalMap.put("age3", m.get("count"));
            } else if ("41-50".equals(m.get("name"))) {
                totalMap.put("age4", m.get("count"));
            } else if ("51-60".equals(m.get("name"))) {
                totalMap.put("age5", m.get("count"));
            }
        }
        List<Map<String, Object>> workingYearsMap = findCheckUserByWorkingYears();//司龄分布

        for (Map m : workingYearsMap) {
            if ("1年及其以下".equals(m.get("name"))) {
                totalMap.put("workingYears1", m.get("count"));
            } else if ("1年以上3年以下".equals(m.get("name"))) {
                totalMap.put("workingYears2", m.get("count"));
            } else if ("3年以上5年以下".equals(m.get("name"))) {
                totalMap.put("workingYears3", m.get("count"));
            } else if ("5年以上10年以下".equals(m.get("name"))) {
                totalMap.put("workingYears4", m.get("count"));
            } else if ("10年以上".equals(m.get("name"))) {
                totalMap.put("workingYears5", m.get("count"));
            }
        }
        map.put("totalMap", totalMap);
        return map;
    }
}