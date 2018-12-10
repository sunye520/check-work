package com.yogee.youge.common.utils;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/4.
 */


public class VerifyTools {

    /**
     * 验证不能为空的key是否为空
     * @param parameterMap 需要验证的Map
     * @param verifyKeys value不能为空的key
     * @return 都不为空返回null，为空返回错误信息
     */
    public static String isContainEmptyKey (Map<String,String> parameterMap, String[] verifyKeys)
    {
        if (parameterMap == null)
        {
            return "参数不是json";
        }
        String emptyKey = null;
        for (int i = 0; i<verifyKeys.length; i++)
        {
            //如果Map里不包含给定的key
            if (!parameterMap.containsKey(verifyKeys[i]))
            {
                emptyKey = verifyKeys[i] + "为空或不存在";
                break;
            }
            else
            {
                //如果Map里包含给定的key，且value为null或者""
                if (parameterMap.get(verifyKeys[i]).equals("") || parameterMap.get(verifyKeys[i]) == null)
                {
                    emptyKey = verifyKeys[i] + "为空或不存在";
                    break;
                }
            }
        }
        return emptyKey;
    }

    /**
     * 验证给定字符串是否合法（minLength和maxLength相等则代表必须minLength位，minLength为0代表可以为空）
     * @param verifyString 待验证的字符串
     * @param name 字符串的key
     * @param minLength 最短长度
     * @param maxLength 最长长度
     * @return 合法返回null，非法返回错误信息
     */
    public static String isIllegal (String verifyString,String name,int minLength,int maxLength)
    {
        if (minLength == maxLength && minLength != verifyString.length())
        {
            return name + "不合法(" + name + "只能" + minLength + "位)";
        }
        if (minLength != 0 && name.length() == 0)
        {
            return name + "不合法(" + name + "不能为空)";
        }
        if (verifyString.length() < minLength)
        {
            return name + "不合法(" + name + "最少" + minLength + "位)";
        }
        if (verifyString.length() > maxLength)
        {
            return name + "不合法(" + name + "最多" + maxLength + "位)";
        }
        return null;
    }

    /**
     * 验证给定字符串是否是手机号
     * @param verifyString 待验证的字符串
     * @return 合法返回null，非法返回错误信息
     */
    public static String isNotPhoneNumber (String verifyString)
    {
        if ((verifyString.startsWith("13") || verifyString.startsWith("14")|| verifyString.startsWith("15")|| verifyString.startsWith("16")|| verifyString.startsWith("17")|| verifyString.startsWith("18")) && verifyString.length() == 11 && verifyString != null)
        {
            return null;
        }
        return "手机号码格式有误";
    }
}
