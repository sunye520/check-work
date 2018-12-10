package com.yogee.youge.common.weixin.api;


import com.yogee.youge.common.config.Global;
import com.yogee.youge.common.weixin.kit.HttpKit;

import java.net.URLEncoder;

public class OAuthApi {
    public static String  GetCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    //public static String GetOpenIDRequest ="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    public static String getCodeRequest(String state){
        String result = null;
        GetCodeRequest  = GetCodeRequest.replace("APPID", urlEnodeUTF8(Global.getConfig("appId")));
        GetCodeRequest  = GetCodeRequest.replace("REDIRECT_URI",urlEnodeUTF8(Global.getConfig("redirect_url")));
        GetCodeRequest = GetCodeRequest.replace("STATE", state);
        GetCodeRequest = GetCodeRequest.replace("SCOPE", "snsapi_base");
        result = GetCodeRequest;
        return result;
    }
    public static String getCodeRequest(){
        String result = null;
        GetCodeRequest  = GetCodeRequest.replace("APPID", urlEnodeUTF8("wx502edb5c26fa32ca"));
        GetCodeRequest  = GetCodeRequest.replace("REDIRECT_URI",urlEnodeUTF8("http://a.zhaoshiba.cn/app/prize"));
//        GetCodeRequest  = GetCodeRequest.replace("REDIRECT_URI",urlEnodeUTF8("http://knbc4.ngrok.natapp.cn/app/prize"));

        GetCodeRequest = GetCodeRequest.replace("SCOPE", "snsapi_base");
        result = GetCodeRequest;
        return result;
    }


    public static String GetOpenIDRequest(String code){

        String GetOpenIDRequest ="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";


        String result = null;
        GetOpenIDRequest  = GetOpenIDRequest.replace("APPID", urlEnodeUTF8(Global.getConfig("appId")));
        GetOpenIDRequest  = GetOpenIDRequest.replace("SECRET",urlEnodeUTF8(Global.getConfig("appSecret")));

        GetOpenIDRequest = GetOpenIDRequest.replace("CODE", code);

        result =   GetOpenIDRequest;
        String jsonResult = HttpKit.get(result);
        return jsonResult;
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

        // System.out.print(GetOpenIDRequest("aaa"));
        // System.out.print(getCodeRequest("1")) ;
        System.out.print(getCodeRequest()) ;



    }
}