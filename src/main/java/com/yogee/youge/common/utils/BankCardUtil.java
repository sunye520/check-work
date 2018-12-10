package com.yogee.youge.common.utils;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by RenHaipeng on 2017/3/15 0009.
 */
public class BankCardUtil {

    public static Map<Object,Object> getBankInfor(String card_num){

        //阿里支持银行API
        String aliUrl = "";
        StringBuffer strBuf;

        try {
            aliUrl = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo="+card_num+"&cardBinCheck=true";
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        strBuf = new StringBuffer();

        try{
            URL url = new URL(aliUrl);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));//转码。
            String line = null;
            while ((line = reader.readLine()) != null)
                strBuf.append(line + " ");
            reader.close();
        }catch(MalformedURLException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println(strBuf.toString());

        Map<Object,Object> datas = getBankType(strBuf.toString());
        return datas;
    }

    //进行银行卡信息获取和拆分
    private static Map<Object,Object> getBankType(String cardJson){
        Map<Object,Object> datas = new LinkedHashMap<Object,Object>();
        JSONObject dataOfJson = JSONObject.fromObject(cardJson);
        if("true".equals(dataOfJson.getString("validated"))){
            datas.put("bank",dataOfJson.getString("bank") == null ? "" : dataOfJson.getString("bank"));//所属银行
            datas.put("cardType", dataOfJson.getString("cardType") == null ? "" : dataOfJson.getString("cardType"));//银行卡类型(cardType,DC:借记卡，CC:信用卡)
            datas.put("validated",dataOfJson.getString("validated") == null ? "" : dataOfJson.getString("validated"));//银行Bin号的合法与否
            datas.put("stat",dataOfJson.getString("stat") == null ? "" : dataOfJson.getString("stat"));
        }else{
            datas.put("bank","");//所属银行
            datas.put("cardType","");//银行卡类型(cardType,DC:借记卡，CC:信用卡)
            datas.put("validated","false");//银行Bin号的合法与否
            datas.put("stat","");
        }

        //返回信息

        return datas;
    }
}
