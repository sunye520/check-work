package com.yogee.youge.interfaces.util;

import com.google.common.collect.Lists;
import com.yogee.youge.common.utils.CacheUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Haipeng.Ren on 2017/6/28 0028.
 */
public class OrderNumberUtils {

    /**
     * 锁对象，可以为任意对象
     */
    private static Object lockObj = "lockerOrder";
    /**
     * 订单号生成计数器
     */
    private static long orderNumCount = 0L;
    /**
     * 每毫秒生成订单号数量最大值
     */
    private static int maxPerMSECSize=1000;
    /**
     * 缓存名称
     */
    private static final String ORDER_NUMBER_CACHE = "orderNumberCache";

    /**
     * 订单号形式【1+1+2+2+2+3+3】共14位
     * 订单号说明【操作平台+业务编号+年的后两位+月+日+毫秒+本批次顺序号】
     * 此方法用于生成订单号的后半部分【年的后两位+月+日+毫秒+顺序号】
     */
    public static void createOrderNumber(){
        // 最终生成的订单号
        String finOrderNum = "";
        //定义List保存订单号
        List orderNumberList = Lists.newArrayList();
        //循环生成订单号
        for(int i = 0; i < maxPerMSECSize - 1; i++){
            long nowLong = Long.parseLong(new SimpleDateFormat("yyMMddSSS").format(new Date()));
            orderNumCount++;
            String countStr = maxPerMSECSize + orderNumCount + "";
            finOrderNum = nowLong+countStr.substring(1);
            orderNumberList.add(finOrderNum);
//            System.out.println(finOrderNum);
        }
        //存储到缓存
        CacheUtils.put(ORDER_NUMBER_CACHE, "orderNumber", orderNumberList);
    }

    /**
     * 获取订单号
     * @param plateform 平台编号【1：web；2：安卓；3：IOS】
     * @param type 交易类型【1：主订单；2：子订单；3：提现；4：还款】
     * @return
     */
    public static String getOrderNumber(String plateform, String type){
        String realNumber = "";
        List numbers = (List) CacheUtils.get(ORDER_NUMBER_CACHE, "orderNumber");
        if(null != numbers && numbers.size() > 0){
            realNumber = plateform + type + numbers.get(0);
            numbers.remove(0);
            CacheUtils.put(ORDER_NUMBER_CACHE, "orderNumber", numbers);
        }else{
            createOrderNumber();
            realNumber = getOrderNumber(plateform, type);
        }
        return realNumber;
    }

//    public static void main(String[] args) {
//        createOrderNumber();
//    }

}
