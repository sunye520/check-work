package com.yogee.youge.interfaces.check;

import com.yogee.youge.common.utils.StringUtils;
import com.yogee.youge.interfaces.util.HttpResultUtil;
import com.yogee.youge.interfaces.util.HttpServletRequestUtils;
import com.yogee.youge.modules.sys.entity.User;
import com.yogee.youge.modules.sys.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun.ye  on 2018/12/19.
 */

@Controller
@RequestMapping("${apiPath}")
public class LoginInterface {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterface.class);

    @Autowired
    SystemService systemService;



    /**
     * 登录
     * @param request
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
        logger.info("login ----------Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String name = (String)jsonData.get("name");
        String password = (String)jsonData.get("password");
        if (StringUtils.isEmpty(name)){
            return HttpResultUtil.errorJson("登录名为空!");
        }
        if (StringUtils.isEmpty(password)){
            return HttpResultUtil.errorJson("密码为空!");
        }
        User user =   systemService.getUserByLoginName(name);
        if(user==null){
            return HttpResultUtil.errorJson("您的登录账号不存在!");
        }
        if (!SystemService.validatePassword(password, user.getPassword())) {
            return HttpResultUtil.errorJson("您的登录密码不正确!");
        }
        Map dataMap = new HashMap();
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 修改密码
     * @param request
     * @return
     */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public String updatePassword(HttpServletRequest request, HttpServletResponse response) {
        logger.info("updatePassword ----------Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String name = (String)jsonData.get("name");
        String password = (String)jsonData.get("password");
        String xinPassword = (String)jsonData.get("xinPassword");
        if (StringUtils.isEmpty(name)){
            return HttpResultUtil.errorJson("登录名为空!");
        }
        if (StringUtils.isEmpty(password)){
            return HttpResultUtil.errorJson("原密码为空!");
        }
        if (StringUtils.isEmpty(xinPassword)){
            return HttpResultUtil.errorJson("新密码为空!");
        }
        User user =   systemService.getUserByLoginName(name);
        if(user==null){
            return HttpResultUtil.errorJson("您的登录账号不存在!");
        }
        if (!SystemService.validatePassword(password, user.getPassword())) {
            return HttpResultUtil.errorJson("您的原密码不正确!");
        }
        user.setPassword(SystemService.entryptPassword(xinPassword));
        Map dataMap = new HashMap();
        return HttpResultUtil.successJson(dataMap);
    }


}
