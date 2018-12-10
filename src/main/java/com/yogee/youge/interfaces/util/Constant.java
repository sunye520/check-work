package com.yogee.youge.interfaces.util;

/**
 * Created by Administrator on 2015/10/23.
 */
public class Constant {

    /** json 响应的key start */
    /**  响应状态，success 成功 , error 错误 */
    public static final String RESPONSE_STATE_KEY = "state";
    /** 消息 */
    public static final String RESPONSE_MSG_KEY = "msg";
    /** 数据集合 */
    public static final String RESPONSE_DATA_KEY = "data";
    /** result */
    public static final String RESPONSE_RESULT_KEY = "result";
    /** json 响应的key end */

    /*** 参数异常*/
    public static final String PARAMETER_ERROR = "json参数错误！";
    /** 错误 */
    public static final String ERROR = "error";

    /**成功 */
    public static final String SUCCESS = "success";

    /** 讨论上传的json */
    public static final String DATA_JSON = "data";
    /** 文件的md5 */
    public static final String MD5HASH = "MD5Hash";

    //金额格式化：12,555,366.38
    public static final String FORMAT_MONEY = "%,10.2f";

    //短信上限（勿删，不要擅自改动）
    public static final int MSG_MAX = 12500;

    //短信统计开始时间（勿删，不要擅自改动）
    public static final String MSG_DATE_START = "2017-08-08";

    /**
     * 交易状态（0：失败；1：成功；2：处理中）
     */
    public static final String TRADE_STATUS_FAILED = "0";
    public static final String TRADE_STATUS_SUCCESS = "1";
    public static final String TRADE_STATUS_PENDING = "2";

    /**
     * 审核状态（0：失败；1：成功；2：审核中）
     */
    public static final String AUDIT_FLAG_FAILED = "0";
    public static final String AUDIT_FLAG_SUCCESS = "1";
    public static final String AUDIT_FLAG_AUDITING = "2";

    /** sys_area表四川省的id */
    public static final String AREA_SICHUAN = "a6da7987f2a8476c971859e3fd679061";

    /** 微信的模板消息id */
    public static final String WECHAT_TEMPLATE_MSG_PAY = "WBm7nvEV-VKzTyXVWdYqMI4jTbM0cpP09dxrkqCyh0Q";
    public static final String WECHAT_TEMPLATE_MSG_DELIVERY = "Ny5lQIyakWsbZ8i60aMS4DsNgydMWteozGIdc9WrbIk";
    public static final String WECHAT_TEMPLATE_MSG_BACKPAY = "i-q4uMrUWiz96FRsoOf1m19RroP5YN2IyCGIZHNr1PM";
    public static final String WECHAT_TEMPLATE_MSG_AUDIT = "IiH-Fl7bkrbruXmtUPs7xgcskMxhMO2vgdurpOKHQrw";

}
