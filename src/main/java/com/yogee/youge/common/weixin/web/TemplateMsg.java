package com.yogee.youge.common.weixin.web;


import com.yogee.youge.common.config.Global;
import com.yogee.youge.common.mapper.JsonMapper;
import com.yogee.youge.common.utils.CacheUtils;
import com.yogee.youge.common.utils.StringUtils;
import com.yogee.youge.common.weixin.api.AccessToken;
import com.yogee.youge.common.weixin.api.AccessTokenApi;
import com.yogee.youge.common.weixin.api.ApiConfig;
import com.yogee.youge.common.weixin.entity.Template;
import com.yogee.youge.common.weixin.entity.TemplateParam;
import com.yogee.youge.common.weixin.kit.HttpKit;
import com.yogee.youge.interfaces.util.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板消息接口
 */
public class TemplateMsg {

    /**
     * 订单支付成功
     * @param first 标题
     * @param openId 用户微信id
     * @param money 订单金额
     * @param productName 订单商品名称
     * @param remark 备注
     * @param url 跳转链接
     */
    public static void pushMsgOrderSuccess(String openId, String first, String money, String productName, String remark, String url){

        ApiConfig.setAppId(Global.getConfig("wechat.app.id"));
        ApiConfig.setAppSecret(Global.getConfig("wechat.app.secret"));
        //获取accessToken
        String accessToken = (String) CacheUtils.get("wxCache", "accessToken");
        if(StringUtils.isEmpty(accessToken) || StringUtils.isBlank(accessToken)){
            AccessToken at = AccessTokenApi.getAccessToken();
            String result = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+at.getAccessToken()+"&type=jsapi";
            String jsonResult = HttpKit.get(result);
            Map<String, String> mapData = JsonMapper.nonDefaultMapper().fromJson(jsonResult, HashMap.class);
            //缓存access_token和jsapi_ticket
            CacheUtils.put("wxCache", "accessToken", at.getAccessToken());
            accessToken = at.getAccessToken();
        }

        //生成模板
        Template tem=new Template();
        tem.setTemplateId(Constant.WECHAT_TEMPLATE_MSG_PAY);
        tem.setTopColor("#00DD00");
        tem.setUrl(url);

        List<TemplateParam> paras=new ArrayList<TemplateParam>();
        paras.add(new TemplateParam("first", first, "#FF3333"));
        paras.add(new TemplateParam("orderMoneySum", money, "#0044BB"));
        paras.add(new TemplateParam("orderProductName", productName, "#0044BB"));
        paras.add(new TemplateParam("remark", remark, "#AAAAAA"));

        tem.setTemplateParamList(paras);

        //获取用户信息
        tem.setToUser(openId);
        boolean ret= WeiXinTemplateMsg.sendTemplateMsg(accessToken, tem);
    }

    /**
     * 订单发货通知
     * @param first 标题
     * @param openId 用户微信id
     * @param kword1 订单内容
     * @param kword2 物流服务
     * @param kword3 快递单号
     * @param kword4 收货信息
     * @param remark 备注
     * @param url 跳转链接
     */
    public static void pushMsgDelivery(String openId, String first, String kword1, String kword2, String kword3, String kword4, String remark, String url){

        ApiConfig.setAppId(Global.getConfig("wechat.app.id"));
        ApiConfig.setAppSecret(Global.getConfig("wechat.app.secret"));
        //获取accessToken
        String accessToken = (String) CacheUtils.get("wxCache", "accessToken");
        if(StringUtils.isEmpty(accessToken) || StringUtils.isBlank(accessToken)){
            AccessToken at = AccessTokenApi.getAccessToken();
            String result = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+at.getAccessToken()+"&type=jsapi";
            String jsonResult = HttpKit.get(result);
            Map<String, String> mapData = JsonMapper.nonDefaultMapper().fromJson(jsonResult, HashMap.class);
            //缓存access_token和jsapi_ticket
            CacheUtils.put("wxCache", "accessToken", at.getAccessToken());
            accessToken = at.getAccessToken();
        }

        //生成模板
        Template tem=new Template();
        tem.setTemplateId(Constant.WECHAT_TEMPLATE_MSG_DELIVERY);
        tem.setTopColor("#00DD00");
        tem.setUrl(url);

        List<TemplateParam> paras=new ArrayList<TemplateParam>();
        paras.add(new TemplateParam("first", first, "#FF3333"));
        paras.add(new TemplateParam("keyword1", kword1, "#0044BB"));
        paras.add(new TemplateParam("keyword2", kword2, "#0044BB"));
        paras.add(new TemplateParam("keyword3", kword3, "#0044BB"));
        paras.add(new TemplateParam("keyword4", kword4, "#0044BB"));
        paras.add(new TemplateParam("remark", remark, "#AAAAAA"));

        tem.setTemplateParamList(paras);

        //获取用户信息
        tem.setToUser(openId);
        boolean ret= WeiXinTemplateMsg.sendTemplateMsg(accessToken, tem);
    }

    /**
     * 退款通知
     * @param first 标题
     * @param openId 用户微信id
     * @param reason 退款原因
     * @param refund 退款金额
     * @param remark 备注
     * @param url 跳转链接
     */
    public static void pushMsgBackpay(String openId, String first, String reason, String refund, String remark, String url){

        ApiConfig.setAppId(Global.getConfig("wechat.app.id"));
        ApiConfig.setAppSecret(Global.getConfig("wechat.app.secret"));
        //获取accessToken
        String accessToken = (String) CacheUtils.get("wxCache", "accessToken");
        if(StringUtils.isEmpty(accessToken) || StringUtils.isBlank(accessToken)){
            AccessToken at = AccessTokenApi.getAccessToken();
            String result = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+at.getAccessToken()+"&type=jsapi";
            String jsonResult = HttpKit.get(result);
            Map<String, String> mapData = JsonMapper.nonDefaultMapper().fromJson(jsonResult, HashMap.class);
            //缓存access_token和jsapi_ticket
            CacheUtils.put("wxCache", "accessToken", at.getAccessToken());
            accessToken = at.getAccessToken();
        }

        //生成模板
        Template tem=new Template();
        tem.setTemplateId(Constant.WECHAT_TEMPLATE_MSG_BACKPAY);
        tem.setTopColor("#00DD00");
        tem.setUrl(url);

        List<TemplateParam> paras=new ArrayList<TemplateParam>();
        paras.add(new TemplateParam("first", first, "#FF3333"));
        paras.add(new TemplateParam("reason", reason, "#0044BB"));
        paras.add(new TemplateParam("refund", refund, "#0044BB"));
        paras.add(new TemplateParam("remark", remark, "#AAAAAA"));

        tem.setTemplateParamList(paras);

        //获取用户信息
        tem.setToUser(openId);
        boolean ret= WeiXinTemplateMsg.sendTemplateMsg(accessToken, tem);
    }

    /**
     * 店铺审核通知
     * @param first 标题
     * @param openId 用户微信id
     * @param kword1 店铺名称
     * @param kword2 审核结果
     * @param kword3 处理时间
     * @param remark 备注
     * @param url 跳转链接
     */
    public static void pushMsgAuditSuccess(String openId, String first, String kword1, String kword2, String kword3, String remark, String url){

        ApiConfig.setAppId(Global.getConfig("wechat.app.id"));
        ApiConfig.setAppSecret(Global.getConfig("wechat.app.secret"));
        //获取accessToken
        String accessToken = (String) CacheUtils.get("wxCache", "accessToken");
        if(StringUtils.isEmpty(accessToken) || StringUtils.isBlank(accessToken)){
            AccessToken at = AccessTokenApi.getAccessToken();
            String result = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+at.getAccessToken()+"&type=jsapi";
            String jsonResult = HttpKit.get(result);
            Map<String, String> mapData = JsonMapper.nonDefaultMapper().fromJson(jsonResult, HashMap.class);
            //缓存access_token和jsapi_ticket
            CacheUtils.put("wxCache", "accessToken", at.getAccessToken());
            accessToken = at.getAccessToken();
        }

        //生成模板
        Template tem=new Template();
        tem.setTemplateId(Constant.WECHAT_TEMPLATE_MSG_AUDIT);
        tem.setTopColor("#00DD00");
        tem.setUrl(url);

        List<TemplateParam> paras=new ArrayList<TemplateParam>();
        paras.add(new TemplateParam("first", first, "#FF3333"));
        paras.add(new TemplateParam("keyword1", kword1, "#0044BB"));
        paras.add(new TemplateParam("keyword2", kword2, "#0044BB"));
        paras.add(new TemplateParam("keyword3", kword3, "#0044BB"));
        paras.add(new TemplateParam("remark", remark, "#AAAAAA"));

        tem.setTemplateParamList(paras);

        //获取用户信息
        tem.setToUser(openId);
        boolean ret= WeiXinTemplateMsg.sendTemplateMsg(accessToken, tem);
    }


}

