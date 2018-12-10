package com.yogee.youge.common.weixin.web;


import com.yogee.youge.common.mapper.JsonMapper;
import com.yogee.youge.common.utils.StringUtils;
import com.yogee.youge.common.web.BaseController;
import com.yogee.youge.common.weixin.api.AccessToken;
import com.yogee.youge.common.weixin.api.AccessTokenApi;
import com.yogee.youge.common.weixin.api.ApiConfig;
import com.yogee.youge.common.weixin.api.OAuthApi;
import com.yogee.youge.common.weixin.kit.HttpKit;
import com.yogee.youge.common.weixin.kit.SignatureCheckKit;
import com.yogee.youge.common.weixin.msg.InMsgParaser;
import com.yogee.youge.common.weixin.msg.in.InMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * ΢����� public/weixin
 */

@Controller
@RequestMapping(value = "${publicPath}")
public class WeixinController  extends BaseController {
    private String inMsgXml = null;		// �������� xml����
    private InMsg inMsg = null;			// �������� xml ������� InMsg ����
    public static Map<String,String> map=new HashMap<String,String>();


    private static Logger log = LoggerFactory.getLogger(WeixinController.class);
    /** ��־�ļ��� */
    private static final boolean isDebug = log.isDebugEnabled();


    @RequestMapping(value = "weixin",method = RequestMethod.GET)
    public void index1(HttpServletRequest request, HttpServletResponse response)  throws IOException {
        PrintWriter out = response.getWriter();
        String echostr = request.getParameter("echostr");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        boolean isOk = SignatureCheckKit.me.checkSignature(signature, timestamp, nonce);
        if (isOk) {
            out.print(echostr);
            out.close();
        }
        else
            log.error("��֤ʧ�ܣ�configServer");
    }




   @ResponseBody
   @RequestMapping(value = "weixin",method = RequestMethod.POST,produces = "application/xml;charset=UTF-8")
   public void index(HttpServletRequest request, HttpServletResponse response) {
        InMsg msg = getInMsg(request);
        WeiXinMsgHandle.handle(msg,response);
    }

   public InMsg getInMsg(HttpServletRequest request) {

         inMsgXml = HttpKit.readIncommingRequestData(request);
         log.debug("xml详情：" + inMsgXml);
        return  InMsgParaser.parse(inMsgXml);
   }


    @RequestMapping(value = "ping")
    public String ping(HttpServletRequest request,Model model) {


        String state = request.getParameter("state");
        //�õ������ߵ�code
        String code = request.getParameter("code");
        log.debug(" ����ƴ����ҳ��-------{} ", state   +"------code== " +code);
        //ͨ��code�õ�΢���û���openID
        String json = OAuthApi.GetOpenIDRequest(code);
        Map<String, String> mapData = new HashMap<String, String>();
        mapData = JsonMapper.nonDefaultMapper().fromJson(json, HashMap.class);
        String openid = mapData.get("openid");
      /*  List<AppPingDanUser> appPingDanUserList = appPingDanUserService.findUserByPinDan(state,openid);
        if(appPingDanUserList ==null || appPingDanUserList.size() ==0) {
            log.debug("pingdan ID ======" + state);

            AppPingDanUser appPingDanUser = new AppPingDanUser();
            AppPingDan appPingDan = appPingDanService.get(state);
            appPingDanUser.setOpenId(openid);
            appPingDanUser.setAppPingDan(appPingDan);
            appPingDanUserService.save(appPingDanUser);
            model.addAttribute("appPingDan", appPingDan);
        }else{

            AppPingDan appPingDan = appPingDanService.get(state);
            model.addAttribute("appPingDan", appPingDan);
        model.addAttribute("msg", "1");
    }
        String url = getRequestURLPath(request);
        model.addAttribute("url", url);*/
        String str ="{\n" +
                "  \"order_no\":  \"1234567890\",\n" +
                "  \"app\":       {\"id\": \"app_1234567890abcDEF\"},\n" +
                "  \"channel\":   \"wx_pub\",\n" +
                "  \"amount\":    100,\n" +
                "  \"client_ip\": \"127.0.0.1\",\n" +
                "  \"currency\":  \"cny\",\n" +
                "  \"subject\":   \"Your Subject\",\n" +
                "  \"body\":      \"Your Body\",\n" +
                "  \"extra\": {\n" +
                "    \"open_id\": "+openid+"\n" +
                "  }\n" +
                "}";

        System.out.print(str);
        return "modules/aaa/guanfang";
    //return str;
    }




    @RequestMapping(value = "post")
    @ResponseBody
    private  String post(HttpServletRequest request ,String pindanid){

       /* log.debug("pindanid ======"+  pindanid);
        AppPingDan appPingDan =appPingDanService.get(pindanid) ;
        Integer id =  appPingDan.getNumber()+1;
        appPingDan.setNumber(id);
        appPingDanService.save(appPingDan);
*/
        return "";
    }




    @RequestMapping(value = "aaa")
    public String aaa(HttpServletRequest request,Model model) {
        if (isDebug) {
            log.debug("����aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }

       // AccessToken at = AccessTokenApi.getAccessToken();
       // at.getAccessToken();
        ApiConfig.setAppId("wxcbf3201e251f8bc8");
        ApiConfig.setAppSecret("ddcbaeef38a62a9dbd0c30be6b895ab0");
        AccessToken at = AccessTokenApi.getAccessToken();


       String result = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+at.getAccessToken()+"&type=jsapi";
        String jsonResult = HttpKit.get(result);
        String  ticket = StringUtils.EMPTY;
        if(null !=null){
             ticket = map.get("ticket");
       }else{
            Map<String, String> mapData = new HashMap<String, String>();
            mapData = JsonMapper.nonDefaultMapper().fromJson(jsonResult, HashMap.class);
             ticket = mapData.get("ticket");
        }
        if (isDebug) {
            log.debug("ticketticketticketticketticketticketticket ======" + ticket);
        }


        String url1 = "http://su.tunnel.mobi/public/aaa";
        Map<String, String> ret = sign(ticket, url1);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }


       String jsapi_ticket =  ret.get("jsapi_ticket");
        String nonceStr =  ret.get("nonceStr");
        String timestamp =ret.get("timestamp");
       String   signature = ret.get("signature");
        String   url = ret.get("url");

        model.addAttribute("jsapi_ticket", jsapi_ticket);
        model.addAttribute("nonceStr", nonceStr);
        model.addAttribute("timestamp", timestamp);
        model.addAttribute("signature", signature);
        model.addAttribute("url", url);

        if (isDebug) {
            log.debug("nonceStr ======" + nonceStr+"timestamp"+timestamp+"signature"+signature);
        }

    return "modules/aaa/weixin";
    }





    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        //String nonce_str = "82693e11-b9bc-448e-892f-f5289f46cd0f";
        //String timestamp = "1419835025";
        String string1;
        String signature = "";

        //ע���������������ȫ��Сд���ұ�������
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}


