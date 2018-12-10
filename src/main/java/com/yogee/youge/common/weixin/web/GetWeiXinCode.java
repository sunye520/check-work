package com.yogee.youge.common.weixin.web;

import com.yogee.youge.common.config.Global;

import java.net.URLEncoder;

public class GetWeiXinCode {
  //wx8c1552ab3b1d7aee
    //wxaa7efad0e7585be7
    public static String  GetCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    public static String getCodeRequest(){
        String result = null;
        GetCodeRequest  = GetCodeRequest.replace("APPID", urlEnodeUTF8(Global.getConfig("appId")));
        GetCodeRequest  = GetCodeRequest.replace("REDIRECT_URI",urlEnodeUTF8("http://192.168.51.72/app/vote"));

        GetCodeRequest = GetCodeRequest.replace("SCOPE", "snsapi_base");
        result = GetCodeRequest;
        return result;
    }
    public static String urlEnodeUTF8(String str){
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args) {
        System.out.println(getCodeRequest());

        String str = "{\n" +
                "\t\"order_no\":  \"1234567890\",\n" +
                "\t\"app\":       {\"id\": \"app_1234567890abcDEF\"},\n" +
                "\t\"channel\":   \"wx_pub\",\n" +
                "\t\"amount\":    100,\n" +
                "\t\"client_ip\": \"127.0.0.1\",\n" +
                "\t\"currency\":  \"cny\",\n" +
                "\t\"subject\":   \"Your Subject\",\n" +
                "\t\"body\":      \"Your Body\",\n" +
                "\t\"extra\": {\n" +
                "\t\"open_id\": oW7a5jsNne91C7yxIKtKgoZ9k0EA\n" +
                "\t}\n" +
                "\t}";
    }
}

