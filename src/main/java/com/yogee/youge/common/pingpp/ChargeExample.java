package com.yogee.youge.common.pingpp;


import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.App;
import com.pingplusplus.model.Charge;
import com.yogee.youge.common.config.Global;
import com.yogee.youge.common.utils.Hashids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;


/**
 * Charge 对象相关示例
 * @author sunkai
 *
 * 该实例程序演示了如何从 ping++ 服务器获得 charge ，查询 charge。
 *
 * 开发者需要填写 apiKey 和 appId , apiKey 可以在 ping++ 管理平台【应用信息里面查看】
 *
 * apiKey 有 TestKey 和 LiveKey 两种。
 *
 * TestKey 模式下不会产生真实的交易。
 */

@Controller
@RequestMapping(value = "${publicPath}")
public class ChargeExample {
    //用于对id进行加密
    private Hashids hashids = new Hashids(Global.getConfig("web.hashids.salt"));
    private static Logger logger = LoggerFactory.getLogger(ChargeExample.class);
    private static boolean isDebugLogger = logger.isDebugEnabled();

    /**
     * pingpp 管理平台对应的 API key sk_test_anb1SSmbD4uLOWnT0Kvnv1eT
     */

     public static String apiKey = Global.getConfig("pingpp.api.key");

    /**
     * pingpp 管理平台对应的应用 ID
     */

    public static String appId = Global.getConfig("pingpp.api.id");


    public static void main(String[] args) {
        Pingpp.apiKey = apiKey;
        ChargeExample ce = new ChargeExample();
//        System.out.println("---------创建 charge");
//         Charge charge = ce.charge();
//          System.out.println("---------查询 charge");
//          ce.retrieve(charge.getId());
//            System.out.println("---------查询 charge列表");
//            ce.all();
    }


    /**
     * 查询 Charge
     * <p/>
     * 该接口根据 charge Id 查询对应的 charge 。
     * 参考文档：https://pingxx.com/document/api#api-c-inquiry
     * <p/>
     * 该接口可以传递一个 expand ， 返回的 charge 中的 app 会变成 app 对象。
     * 参考文档： https://pingxx.com/document/api#api-expanding
     *
     * @param id
     */

    public Charge retrieve(String id) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            List<String> expande = new ArrayList<String>();
            expande.add("app");
            param.put("expand", expande);
            //Charge charge = Charge.retrieve(id);
            //Expand app
            Charge charge = Charge.retrieve(id, param);
            if (charge.getApp() instanceof App) {
                //App app = (App) charge.getApp();
                // System.out.println("App Object ,appId = " + app.getId());
            } else {
                // System.out.println("String ,appId = " + charge.getApp());
            }

            System.out.println(charge);
            return charge;

        } catch (PingppException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 分页查询Charge
     * <p/>
     * 该接口为批量查询接口，默认一次查询10条。
     * 用户可以通过添加 limit 参数自行设置查询数目，最多一次不能超过 100 条。
     * <p/>
     * 该接口同样可以使用 expand 参数。
     *
     * @return
     */

//    public ChargeCollection all() {
//        ChargeCollection chargeCollection = null;
//        Map<String, Object> chargeParams = new HashMap<String, Object>();
//        chargeParams.put("limit", 10);
//
////增加此处设施，刻意获取app expande
////        List<String> expande = new ArrayList<String>();
////        expande.add("app");
////        chargeParams.put("expand", expande);
//
//        try {
//            chargeCollection = Charge.all(chargeParams);
//            System.out.println(chargeCollection);
//        } catch (AuthenticationException e) {
//            e.printStackTrace();
//        } catch (InvalidRequestException e) {
//            e.printStackTrace();
//        } catch (APIConnectionException e) {
//            e.printStackTrace();
//        } catch (APIException e) {
//            e.printStackTrace();
//        } catch (ChannelException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return chargeCollection;
//    }



    public Charge postComment(Integer amount ,String channel,String subject,String body,String uuid,String type,String userId) {

        Pingpp.apiKey = apiKey;
        Pingpp.privateKeyPath = Global.getConfig("userfiles.basedir")+"/WEB-INF/classes/com/yogee/youge/common/pingpp/your_rsa_private_key_pkcs8.pem";
        Charge charge = null;
        Map<String, Object> chargeMap = new HashMap<String, Object>();

        //订单总金额, 单位为对应币种的最小货币单位，
        // 例如：人民币为分（如订单总金额为 1 元，此处请填 100）。
        chargeMap.put("amount", amount);
        //三位 ISO 货币代码，目前仅支持人民币 cny。
        chargeMap.put("currency", "cny");
        //商品的标题
        chargeMap.put("subject", subject);
        //商品的描述信息，该参数最长为 128 个 Unicode 字符，
        chargeMap.put("body", body);
        //order_no
        //商户订单号
        chargeMap.put("order_no", uuid);



        Map<String, String> initialMetadata = new HashMap<String, String>();
//        initialMetadata.put("type", type);
        if(channel.equals("alipay_wap")){
            initialMetadata.put("success_url", Global.getConfig("domain.web")+"/#/indexOrder?userId=" + hashids.encode(Long.valueOf(userId)));
        }else if(channel.equals("alipay_pc_direct")){
            initialMetadata.put("success_url", Global.getConfig("domain.web")+"/#/indexOrder?userId=" + hashids.encode(Long.valueOf(userId)));
        }else if(channel.equals("upacp_wap")){
            initialMetadata.put("result_url", Global.getConfig("domain.web")+"/#/indexOrder?userId=" + hashids.encode(Long.valueOf(userId)));
        }else if(channel.equals("upacp_pc ")){
            initialMetadata.put("result_url", Global.getConfig("domain.web")+"/#/indexOrder?userId=" + hashids.encode(Long.valueOf(userId)));
        }else if(channel.equals("wx_pub")){
            initialMetadata.put("open_id", "oaGvzsjWKkyOR3waqgSV2HgNo_kU");
        }else {

        }
        chargeMap.put("extra", initialMetadata);

        //channel
        //alipay:支付宝手机支付
        //alipay_wap:支付宝手机网页支付
        //alipay_qr:支付宝扫码支付
        //wx:微信支付
        //wx_pub:微信公众账号支付
        chargeMap.put("channel", channel);
        chargeMap.put("client_ip", "127.0.0.1");
        Map<String, String> app = new HashMap<String, String>();
        app.put("id",appId);
        chargeMap.put("app", app);
        try {
            //发起交易请求
            charge = Charge.create(chargeMap);

            System.out.println(charge);
        } catch (PingppException e) {
            e.printStackTrace();
        }
        return charge;
    }

}
