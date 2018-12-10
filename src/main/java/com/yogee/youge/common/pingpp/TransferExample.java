/* *
 * Ping++ Server SDK
 * 说明：
 * 以下代码只是为了方便商户测试而提供的样例代码，商户可根据自己网站需求按照技术文档编写, 并非一定要使用该代码。
 * 接入企业付款流程参考开发者中心：https://www.pingxx.com/docs/server/transfer ，文档可筛选后端语言和接入渠道。
 * 该代码仅供学习和研究 Ping++ SDK 使用，仅供参考。
*/
package com.yogee.youge.common.pingpp;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Transfer;
import com.pingplusplus.model.TransferCollection;
import com.yogee.youge.common.config.Global;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 该实例演示如何使用 Ping++ 进行企业转账。
 *
 * 开发者需要填写 apiKey ，openid 和 appId ,
 *
 * apiKey 有 TestKey 和 LiveKey 两种。
 *
 * TestKey 模式下不会产生真实的交易。
 *
 * openid 是发送红包的对象在公共平台下的 openid ,获得 openid 的方法可以参考微信文档：http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
 *
 */
public class TransferExample {

    public static String apiKey = Global.getConfig("pingpp.api.key");
    public static String appId = Global.getConfig("pingpp.api.id");

    /**
     * 接收者的 openid
     */
    public static String openid ="USER_OPENID";// 用户在商户微信公众号下的唯一标识，获取方式可参考 WxPubOAuthExample.java

    public static void runDemos(String appId) {

//        TransferExample transferExample = new TransferExample(appId);
//        System.out.println("------- 创建 Transfer -------");
//        Transfer transfer = transferExample.transfer();
//        System.out.println("------- 查询 Transfer -------");
//        transferExample.retrieve(transfer.getId());
//        System.out.println("------- 查询 Transfer 列表 -------");
//        transferExample.list();

    }

//    TransferExample(String appId) {
//        this.appId = appId;
//    }

    /**
     * 创建企业转账
     *
     * 创建企业转账需要传递一个 map 给 Transfer.create();
     * map 填写的具体介绍可以参考：https://www.pingxx.com/api#api-t-new
     *
     * @return
     */
    public static Transfer transfer(String channel,String openid,int momey,String description, String card_number, String user_name, String bank_code) {

        Pingpp.apiKey = apiKey;
        Pingpp.privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
                "MIICXQIBAAKBgQDSmX8TU/G2qOeVqTV1Tp1mtUQ9aW1ZTlMhvRRLjsN6Aw2HIzjE\n" +
                "YpwWHZyyAwWbsTpo6KGsnPT0+q6mkbyvsiWApzyANbArgiuMehCQ31c9wYvDAmTA\n" +
                "e1RMKCBDQ2mapmwsMv4xvklam6FZiLH2UIiNdnbaVRc8cX6y04GY7o0dWQIDAQAB\n" +
                "AoGAbEsKEUJFNQw9qbPNDXlTbxEe+gVQeW/RR7QWz5Ol6QPQ3QC7nCCo6qZngRIU\n" +
                "vC6yPmgBWuWLAL43OOgQAri0lzh7nbeMB1EbAs0V/wrUWaXrD2UO9iFaEtw3VnF3\n" +
                "zzQ9LTa9YGN43vy6N4Um4p/raSTZczvsDpINxkBoGo+FSVECQQDv27BPPPXSct6D\n" +
                "Krk2LR2MxyPNMIdYeY10zBE5Pws5llFIGybjLLuwIs6tA0QRyqP/KUCMwkTPFjXJ\n" +
                "heoPqHpXAkEA4MW8pFkGnDn4vjjqhiou1bdNj3ZRgM5BOGbczTH/6bADiz5EXqRl\n" +
                "YzR9jdDZ/60KMIPWshCFCJ+Li/xjylK3zwJBAJ4dUhJJ+zppoqkQxtshznL4627d\n" +
                "6Lq4FjnCNDBqkLm7j4Va8L2cxXUM3PVMS9j2njkcNoMTpUxmNbrM3kjIJS0CQQCs\n" +
                "8xlar/GpJeYin/K8txqAcWGpgJXn8Uw31NYSwWUzb67aZlf/Jqmossvk4g3LcF8F\n" +
                "w3vgWVuzreeXF+XE2O77AkBoR4/9MO9b0Ek25W4UOsWQ7FFusl9l7FmHWOJckCBM\n" +
                "uTFSUCvkPo7AV5SFV4pViL+HmGRX4itUqRwbo1Yp973L\n" +
                "-----END RSA PRIVATE KEY-----";
        Transfer transfer = null;
        String orderNo = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        Map<String, Object> transferMap = new HashMap<String, Object>();
        transferMap.put("channel", channel);
        transferMap.put("amount", momey);
        transferMap.put("type", "b2c");
        transferMap.put("order_no", orderNo);
        transferMap.put("currency", "cny");
//        transferMap.put("recipient", openid);
        transferMap.put("description", description);
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", appId);
        transferMap.put("app", app);
        Map<String, Object> extra = new HashMap<String, Object>();
        extra.put("card_number", card_number);
        extra.put("user_name", user_name);
        extra.put("open_bank_code", bank_code);
        transferMap.put("extra", extra);

        try {
            transfer = Transfer.create(transferMap);
            System.out.println(transfer);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (ChannelException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }
        return transfer;
    }

    /**
     * 根据 ID 查询
     *
     * 根据 ID 查询企业转账记录。
     * 参考文档：https://www.pingxx.com/api#api-t-inquiry
     * @param id
     */
    public void retrieve(String id) {
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            Transfer transfer = Transfer.retrieve(id, param);
            System.out.println(transfer);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (ChannelException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }

    }

    /**
     * 批量查询
     *
     * 批量查询企业转账记录，默认一次查询 10 条，用户可以使用 limit 自定义查询数目，但是最多不超过 100 条。
     */
    public void list() {
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("limit", 3);

        try {
            TransferCollection transferCollection = Transfer.list(parm);
            System.out.println(transferCollection);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (ChannelException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }
    }
}
