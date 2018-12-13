package com.yogee.youge.interfaces.check;

import com.yogee.youge.common.utils.StringUtils;
import com.yogee.youge.interfaces.util.HttpResultUtil;
import com.yogee.youge.interfaces.util.HttpServletRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工花名册
 * author:cheng
 */
@Controller
@RequestMapping("${apiPath}")
public class EmployeeInterface {

    private final static Logger logger = LoggerFactory.getLogger(EmployeeInterface.class);

    /**
     * 员工列表
     * @param req
     * @return
     */
    @RequestMapping(value = "employeeList",method = RequestMethod.POST)
    @ResponseBody
    public String employeeList(HttpServletRequest req){
        logger.info("app employeeList---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(req);
        String total = (String)jsonData.get("total");
        String count = (String)jsonData.get("count");
        String nameLike = (String)jsonData.get("nameLike");
        if (StringUtils.isEmpty(total) || StringUtils.isEmpty(count)){
            total = "0";
            count = "0";
        }
//        List
        //...
        Map mapData = new HashMap();
        return HttpResultUtil.successJson(mapData);
    }

    /**
     * 修改(新增)员工信息
     * @param req
     * @return
     */
    @RequestMapping(value = "updateEmployee",method = RequestMethod.POST)
    @ResponseBody
    public String updateEmployee(HttpServletRequest req){
        logger.info("app updateEmployee---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(req);
        String type = (String)jsonData.get("type"); //0新增1修改

        return HttpResultUtil.successJson(new HashMap());
    }
}
