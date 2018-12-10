package com.yogee.youge.common.weixin.web;


import com.yogee.youge.common.config.Global;
import com.yogee.youge.common.mapper.JsonMapper;
import com.yogee.youge.common.weixin.api.AccessToken;
import com.yogee.youge.common.weixin.api.AccessTokenApi;
import com.yogee.youge.common.weixin.api.ApiConfig;
import com.yogee.youge.common.weixin.entity.Template;
import com.yogee.youge.common.weixin.entity.TemplateParam;
import com.yogee.youge.common.weixin.kit.HttpKit;
import com.yogee.youge.common.weixin.util.CommonUtil;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板消息接口
 */
public class WeiXinTemplateMsg {

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
        ApiConfig.setAppId(Global.getConfig("wechat.app.id"));
        ApiConfig.setAppSecret(Global.getConfig("wechat.app.secret"));
        AccessToken at = AccessTokenApi.getAccessToken();
        String result = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+at.getAccessToken()+"&type=jsapi";
        String jsonResult = HttpKit.get(result);
        Map<String, String> mapData = JsonMapper.nonDefaultMapper().fromJson(jsonResult, HashMap.class);
        String ticket = mapData.get("ticket");
//        缓存access_token和jsapi_ticket
//        CacheUtils.put("wxCache", "accessToken", at.getAccessToken());
//        CacheUtils.put("wxCache", "ticket", ticket);
        accessToken = at.getAccessToken();
        Template tem=new Template();
        tem.setTemplateId("WBm7nvEV-VKzTyXVWdYqMI4jTbM0cpP09dxrkqCyh0Q");
        tem.setTopColor("#00DD00");
        tem.setToUser("oUFogxBcgqRzeiB0hAJt4ygKaJdk");
        tem.setUrl("");

        List<TemplateParam> paras=new ArrayList<TemplateParam>();
        paras.add(new TemplateParam("first","订单支付成功","#FF3333"));
        paras.add(new TemplateParam("orderMoneySum","2","#0044BB"));
        paras.add(new TemplateParam("orderProductName","2017-06-04 12:00:21","#0044BB"));
        paras.add(new TemplateParam("remark","请尽快处理","#AAAAAA"));

        tem.setTemplateParamList(paras);

        boolean ret=sendTemplateMsg(accessToken,tem);
    }


}

