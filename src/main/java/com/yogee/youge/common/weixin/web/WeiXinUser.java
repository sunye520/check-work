package com.yogee.youge.common.weixin.web;


import com.yogee.youge.common.mapper.JsonMapper;
import com.yogee.youge.common.weixin.api.AccessToken;
import com.yogee.youge.common.weixin.api.AccessTokenApi;
import com.yogee.youge.common.weixin.entity.Template;
import com.yogee.youge.common.weixin.kit.HttpKit;
import com.yogee.youge.common.weixin.util.CommonUtil;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * �模板消息接口
 */
public class WeiXinUser {

    public static boolean sendTemplateMsg(String token,Template template){
        boolean flag=false;

        String requestUrl="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        requestUrl=requestUrl.replace("ACCESS_TOKEN", token);

        JSONObject jsonResult= CommonUtil.httpsRequest(requestUrl, "POST", template.toJSON());
        if(jsonResult!=null){
            int errorCode=jsonResult.getInt("errcode");
            String errorMessage=jsonResult.getString("errmsg");
            if(errorCode==0){
                flag=true;
            }else{
                System.out.println("模板消息发送失败:"+errorCode+","+errorMessage);
                flag=false;
            }
        }
        return flag;
    }


    public static boolean testSendTemplateMsg(String token,Template template){
        boolean flag=false;

        String requestUrl="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        requestUrl=requestUrl.replace("ACCESS_TOKEN", token);

        JSONObject jsonResult= CommonUtil.httpsRequest(requestUrl, "POST", template.toJSON());
        if(jsonResult!=null){
            int errorCode=jsonResult.getInt("errcode");
            String errorMessage=jsonResult.getString("errmsg");
            if(errorCode==0){
                flag=true;
            }else{
                System.out.println("模板消息发送失败:"+errorCode+","+errorMessage);
                flag=false;
            }
        }
        return flag;
    }

    public static void main(String[] args) {

        String accessToken = "";
        AccessToken at = AccessTokenApi.getAccessToken();
        String result = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+at.getAccessToken()+"&type=jsapi";
        String jsonResult = HttpKit.get(result);
        Map<String, String> mapData = JsonMapper.nonDefaultMapper().fromJson(jsonResult, HashMap.class);
        String ticket = mapData.get("ticket");
//        缓存access_token和jsapi_ticket
//        CacheUtils.put("wxCache", "accessToken", at.getAccessToken());
//        CacheUtils.put("wxCache", "ticket", ticket);
        accessToken = at.getAccessToken();

        String requestUrl="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=";
        requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);

        JSONObject UserList= CommonUtil.httpsRequest(requestUrl, "POST", "");
        System.out.println(UserList);
    }
}

