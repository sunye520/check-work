package com.yogee.youge.common.pingpp;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Identification;
import com.yogee.youge.common.config.Global;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 身份证银行卡信息认证接口参考文档：https://www.pingxx.com/api#身份证银行卡信息认证接口
 *
 * 该实例演示如何调用身份证银行卡信息认证接口
 *
 */
@Controller
@RequestMapping(value = "${publicPath}")
public class IdentificationExample {

  private String appId = Global.getConfig("pingpp.api.id");
  private String apiKey = Global.getConfig("pingpp.api.key");

//  public IdentificationExample(String appId) {
//    this.appId = appId;
//  }

//  public static void runDemos(String appId) {
//    IdentificationExample eventExample = new IdentificationExample(appId);
//    System.out.println("------- 认证身份证信息 -------");
//    eventExample.identifyIdCard();
//
//    System.out.println("------- 认证银行卡信息 -------");
//    eventExample.identifyBankCard();
//  }

  /**
   * 认证身份证信息
   *
   * 参考文档：https://www.pingxx.com/api#请求认证接口
   */
  public void identifyIdCard() {
    try {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("app", appId);
      params.put("type", "id_card"); // 身份证信息或者银行卡信息串，取值范围: "id_card"（身份证信息串）；"bank_card"（银行卡信息串）。
      Map<String, String> data = new HashMap<String, String>();
      data.put("id_name", "张三");
      data.put("id_number", "320291198811110000");
      params.put("data", data);
      Identification result = Identification.identify(params);
      if (result.getResultCode() == 0) {
        System.out.println("身份认证通过");
      } else {
        System.out.println(result.getResultCode());
        System.out.println(result.getMessage());
      }
      System.out.println(result);
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
   * 认证银行卡信息
   *
   * 参考文档：https://www.pingxx.com/api#请求认证接口
   */
  public void identifyBankCard() {
    try {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("app", appId);
      params.put("type", "bank_card"); // 身份证信息或者银行卡信息串，取值范围: "id_card"（身份证信息串）；"bank_card"（银行卡信息串）。
      Map<String, String> data = new HashMap<String, String>();
      data.put("id_name", "张三");
      data.put("id_number", "320291198811110000");
      data.put("card_number", "6201111122223333");
      params.put("data", data);
      Identification result = Identification.identify(params);
      if (result.getResultCode() == 0) {
        System.out.println("银行卡信息认证通过");
      } else {
        System.out.println(result.getResultCode());
        System.out.println(result.getMessage());
      }
      System.out.println(result);
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
   * 认证银行卡信息
   *
   * 参考文档：https://www.pingxx.com/api#请求认证接口
   */
  public Integer certBankCard(String name, String id_card, String card, String appId) {
    Pingpp.apiKey = apiKey;
//    Pingpp.privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
//            "MIICXAIBAAKBgQCUN6NYkinqFv5BrpeCa3wJSJ69Oy9g1gfUtIh6GtWZkZl8wHZF\n" +
//            "QCYCva92mhLHEdjKU3ppBIUsRlyW3aMawHmD7PYfCC3DGHF5syyWssCBELESzN2H\n" +
//            "gpMxVxbfGxkhRI+83vcHyd79Ay5KyJHekAapQFKZzBUZko5iiljJzJ2kWQIDAQAB\n" +
//            "AoGAU++2R6BusCzZTGUNVER+d9tyWgO8bwCGasTaTRwJPvCflXCBCfKsE/1L8sSK\n" +
//            "QcYvmVTxC6wNpucp5B4cEjhSF7FiwYtOn9+om9U/PKXZzWIIPj+km0R6u2KXKgOq\n" +
//            "2m1H6EG0ze6PmkPEOgAAYI0Y+mkD9n92bqcfBtqU7JqnmckCQQDFauGNUY4HYbJc\n" +
//            "zyyfEzljj0xIfZZlg5Ujv9OKWmwaLL3tz5A8rtqHGImufx3hSihnSgunD5Iuc6j9\n" +
//            "pbXp/onHAkEAwDMxIfY+pwcCIlJPUfk2/C6IwZQqKdzw2nd5TTJvotvqR33pOryN\n" +
//            "PMLRgL/TOiQ0y03y+ooS2eQjCAuNgf9g3wJBAJ0YQyoIhuPa22s9NT3Ut2tBYauh\n" +
//            "vGdFgQ10nWpQMAvYJL8z14xie+TbItynrOhU8bfX1VP4cRPj18sX7Z2pfosCQHz7\n" +
//            "jRKxp1JEK24YKj57GfdpZ0LCT5zDcNgP4xCBILk92p480YKBhDKj2Uqo8vmW+8HM\n" +
//            "4ZlsWg/Y27LqmE2VvO8CQBoYh/BjzO/O3jNfMNl512coTdmgG7KO6eDDu1RgeCcG\n" +
//            "Pvvh+JH7ldASqnfIGVO44eOr+a9ocI7T1ZipUC1VHx4=\n" +
//            "-----END RSA PRIVATE KEY-----";
    Pingpp.privateKeyPath = Global.getConfig("userfiles.basedir")+"/WEB-INF/classes/com/yogee/youge/common/pingpp/your_rsa_private_key_pkcs8.pem";
    try {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("app", Global.getConfig("pingpp.api.id"));
      params.put("type", "bank_card"); // 身份证信息或者银行卡信息串，取值范围: "id_card"（身份证信息串）；"bank_card"（银行卡信息串）。
      Map<String, String> data = new HashMap<String, String>();
      data.put("id_name", name);
      data.put("id_number", id_card);
      data.put("card_number", card);
      params.put("data", data);
      Identification result = Identification.identify(params);
//      if (result.getResultCode() == 0) {
//        System.out.println("银行卡信息认证通过");
//      } else {
//        System.out.println(result.getResultCode());
//        System.out.println(result.getMessage());
//      }
//      System.out.println(result);
      return result.getResultCode();
//      3431	身份证信息不匹配
//      3432	身份证信息匹配失败
//      3433	身份验证活体照片非法
//      3434	身份验证上传登记照失败
//      3435	身份验证照片比对失败
//      3441	银行卡信息认证失败
//      3442	银行卡信息认证请求异常
//      3443	银行卡信息认证请求超时
//      3451	银行卡信息认证校验失败
//      3452	银行卡信息认证校验异常
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
    return 400;
  }
}