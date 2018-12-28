package com.yogee.youge.interfaces.check;

import com.yogee.youge.common.utils.IdGen;
import com.yogee.youge.common.utils.StringUtils;
import com.yogee.youge.interfaces.util.HttpResultUtil;
import com.yogee.youge.interfaces.util.HttpServletRequestUtils;
import com.yogee.youge.modules.check.entity.CheckDepartment;
import com.yogee.youge.modules.check.service.CheckDepartmentService;
import com.yogee.youge.modules.sys.entity.Dict;
import com.yogee.youge.modules.sys.service.DictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sun.ye  on 2018/12/20.
 */
@Controller
@RequestMapping("${apiPath}")
public class DictInterface {
    private static final Logger logger = LoggerFactory.getLogger(DictInterface.class);
    @Autowired
    private CheckDepartmentService checkDepartmentService;
    @Autowired
    private DictService dictService;

    /**
     * 新增部门
     * @param request
     * @return
     */
    @RequestMapping(value = "insertDepartment", method = RequestMethod.POST)
    @ResponseBody
    public String insertDepartment(HttpServletRequest request, HttpServletResponse response) {
        logger.info("insertDepartment ----------Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String parentName = (String)jsonData.get("parentName");
        String parentId = (String)jsonData.get("parentId");
        String sonName = (String)jsonData.get("sonName");
        try {
            if(StringUtils.isNotEmpty(parentName)){
                int countParentName = checkDepartmentService.findDepartmentByName(parentName);
                if(countParentName>0){
                    return HttpResultUtil.errorJson("您添加的一级部门名字重复!");
                }
                if(StringUtils.isNotEmpty(sonName)){
                    int countSonName = checkDepartmentService.findDepartmentByName(sonName);
                    if(countSonName>0){
                        return HttpResultUtil.errorJson("您添加的二级部门名字重复!");
                    }
                    //添加一级部门同时添加二级部门
                    CheckDepartment checkDepartmentParent = new CheckDepartment();
                    String uuid = IdGen.uuid();
                    checkDepartmentParent.setId(uuid);
                    checkDepartmentParent.setName(parentName);
                    checkDepartmentParent.setDepartmentType("1");
                    checkDepartmentParent.setCreateDate(new Date());
                    checkDepartmentParent.setUpdateDate(new Date());
                    checkDepartmentService.insert(checkDepartmentParent);
                    CheckDepartment checkDepartmentSon = new CheckDepartment();
                    checkDepartmentSon.setName(sonName);
                    checkDepartmentSon.setParentId(uuid);
                    checkDepartmentSon.setDepartmentType("2");
                    checkDepartmentService.save(checkDepartmentSon);
                }else{
                    //只添加了一级部门
                    CheckDepartment checkDepartmentParent = new CheckDepartment();
                    String uuid = IdGen.uuid();
                    checkDepartmentParent.setId(uuid);
                    checkDepartmentParent.setName(parentName);
                    checkDepartmentParent.setDepartmentType("1");
                    checkDepartmentParent.setCreateDate(new Date());
                    checkDepartmentParent.setUpdateDate(new Date());
                    checkDepartmentService.insert(checkDepartmentParent);
                }
            }else if(StringUtils.isNotEmpty(parentId) && StringUtils.isNotEmpty(sonName)){
                int countSonName = checkDepartmentService.findDepartmentByName(sonName);
                if(countSonName>0){
                    return HttpResultUtil.errorJson("您添加的二级部门名字重复!");
                }
                //只添加添加二级部门
                CheckDepartment checkDepartmentSon = new CheckDepartment();
                checkDepartmentSon.setName(sonName);
                checkDepartmentSon.setParentId(parentId);
                checkDepartmentSon.setDepartmentType("2");
                checkDepartmentService.save(checkDepartmentSon);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Map dataMap = new HashMap();
        return HttpResultUtil.successJson(dataMap);
    }
    /**
     * 部门列表
     * @param request
     * @return
     */
    @RequestMapping(value = "findDepartment", method = RequestMethod.POST)
    @ResponseBody
    public String findDepartment(HttpServletRequest request,HttpServletResponse response) {
        logger.info("findDepartment ----------Start--------");
//        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        List<CheckDepartment> departmentList = checkDepartmentService.findDepartment();
        Map dataMap = new HashMap();
        dataMap.put("list",departmentList);
        return HttpResultUtil.successJson(dataMap);
    }
    /**
     * 一级部门列表
     * @param request
     * @return
     */
    @RequestMapping(value = "findParentDepartment", method = RequestMethod.POST)
    @ResponseBody
    public String findParentDepartment(HttpServletRequest request,HttpServletResponse response) {
        logger.info("findParentDepartment ----------Start--------");
        List<CheckDepartment> departmentList = checkDepartmentService.findParentDepartment();
        Map dataMap = new HashMap();
        dataMap.put("list",departmentList);
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 修改部门
     * @param request
     * @return
     */
    @RequestMapping(value = "updateDepartment", method = RequestMethod.POST)
    @ResponseBody
    public String updateDepartment(HttpServletRequest request,HttpServletResponse response) {
        logger.info("updateDepartment ----------Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String parentName = (String)jsonData.get("parentName");
        String parentId = (String)jsonData.get("parentId");
        String sonName = (String)jsonData.get("sonName");
        String sonId = (String)jsonData.get("sonId");
        int countParentName = checkDepartmentService.findDepartmentByNameAndId(parentName,parentId);
        if(countParentName>0){
            return HttpResultUtil.errorJson("您修改的一级部门名字重复!");
        }
        checkDepartmentService.updateDepartment(parentName,parentId);
        if(StringUtils.isNotEmpty(sonId)){
            int countSonName = checkDepartmentService.findDepartmentByNameAndId(sonName,sonId);
            if(countSonName>0){
                return HttpResultUtil.errorJson("您修改的二级部门名字重复!");
            }
            checkDepartmentService.updateDepartment(sonName,sonId);
        }
        Map dataMap = new HashMap();
        return HttpResultUtil.successJson(dataMap);
    }
    /**
     * 删除部门
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteDepartment", method = RequestMethod.POST)
    @ResponseBody
    public String deleteDepartment(HttpServletRequest request,HttpServletResponse response) {
        logger.info("deleteDepartment ----------Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String parentId = (String)jsonData.get("parentId");
        String sonId = (String)jsonData.get("sonId");
        if(StringUtils.isNotEmpty(sonId)){
            checkDepartmentService.delete(checkDepartmentService.get(sonId));//删除二级部门
        }else{
            checkDepartmentService.delete(checkDepartmentService.get(parentId));//删除一级部门
        }
        Map dataMap = new HashMap();
        return HttpResultUtil.successJson(dataMap);
    }



    /**
     * 字典列表
     * @param request
     * @return
     */
    @RequestMapping(value = "findDict", method = RequestMethod.POST)
    @ResponseBody
    public String findDict(HttpServletRequest request,HttpServletResponse response) {
        logger.info("findDict ----------Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String label = (String)jsonData.get("label");
        String description = (String)jsonData.get("description");
        Map reqMap = new HashMap();
        reqMap.put("label",label);
        reqMap.put("description",description);
        List<Dict> dictList = dictService.findDictByMap(reqMap);
        Map dataMap = new HashMap();
        dataMap.put("list",dictList);
        return HttpResultUtil.successJson(dataMap);
    }
    /**
     * 新增字典
     * @param request
     * @return
     */
    @RequestMapping(value = "insetDict", method = RequestMethod.POST)
    @ResponseBody
    public String insetDict(HttpServletRequest request,HttpServletResponse response) {
        logger.info("insetDict ----------Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String value = (String)jsonData.get("value");
        String label = (String)jsonData.get("label");
        String type = (String)jsonData.get("type");
        String description = (String)jsonData.get("description");
        Integer sort = (Integer)jsonData.get("sort");
        if (StringUtils.isEmpty(value)){
            return HttpResultUtil.errorJson("键值为空!");
        }
        if (StringUtils.isEmpty(label)){
            return HttpResultUtil.errorJson("标签为空!");
        }
        if (StringUtils.isEmpty(type)){
            return HttpResultUtil.errorJson("类型为空!");
        }
        if (StringUtils.isEmpty(description)){
            return HttpResultUtil.errorJson("描述为空!");
        }
        if(sort==null){
            return HttpResultUtil.errorJson("排序为空!");
        }
//        int sortInt = 0;
//        if (StringUtils.isEmpty(sort)){
//            return HttpResultUtil.errorJson("排序为空!");
//        }else{
//            sortInt =Integer.parseInt(sort);
//        }
        try {
            Dict dict = new Dict();
            dict.setId(IdGen.uuid());
            dict.setValue(value);
            dict.setLabel(label);
            dict.setType(type);
            dict.setDescription(description);
            dict.setSort(sort);
            dict.setParentId("0");
            dictService.insetDict(dict);
        }catch (Exception e){
            e.printStackTrace();
        }
        Map dataMap = new HashMap();
        return HttpResultUtil.successJson(dataMap);
    }


}
