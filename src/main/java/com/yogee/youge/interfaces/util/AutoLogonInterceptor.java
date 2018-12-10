/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yogee.youge.interfaces.util;

import com.yogee.youge.common.config.Global;
import com.yogee.youge.common.mapper.JsonMapper;
import com.yogee.youge.common.weixin.api.ApiConfig;
import com.yogee.youge.common.weixin.api.ApiResult;
import com.yogee.youge.common.weixin.api.OAuthApi;
import com.yogee.youge.common.weixin.api.UserApi;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆拦截器.
 *
 * @author leizhimin 2014/6/26 16:08
 */
public class AutoLogonInterceptor extends HandlerInterceptorAdapter {

    private static final String[] IGNORE_URI = {"/f/listOpen"};
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String url = request.getRequestURL().toString();
        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                flag = true;
                break;
            }
        }
        if (flag) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession session=request.getSession();

            String openids = (String)session.getAttribute("openid");
            if(null != openids){
                String open = session.getAttribute("openid").toString();
                System.out.println(open);
                return true;
            }

            String state = request.getParameter("state");
            //得到访问者的code
            String code = request.getParameter("code");
            //通过code得到微信用户的openID
            String json = OAuthApi.GetOpenIDRequest(code);
            Map<String, String> mapData = new HashMap<String, String>();
            mapData = JsonMapper.nonDefaultMapper().fromJson(json, HashMap.class);
            String openid = mapData.get("openid");

            ApiConfig.setAppId(Global.getConfig("appId"));
            ApiConfig.setAppSecret(Global.getConfig("appSecret"));
            //获取拉去的所有信息
            ApiResult apiResult = UserApi.getUserInfo(openid);

            String nickname=apiResult.get("nickname").toString();
            String sex=apiResult.get("sex").toString();
            String headImage=apiResult.get("headimgurl").toString();
            String country=apiResult.get("country").toString();
            String province=apiResult.get("province").toString();
            String city=apiResult.get("city").toString();

            //业务
            session.setAttribute("openid", openid);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
