package com.yogee.youge.common.weixin.api;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
//import com.pili.common.Utils;
//import com.squareup.okhttp.Request;
import com.yogee.youge.common.weixin.kit.HttpKit;

import java.io.IOException;

/**
 * menu api
 */
public class MenuApi {

    private static String getMenu = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=";
    private static String createMenu = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    /**
     * 查询菜单
     */
    public static ApiResult getMenu() {
        String jsonResult = HttpKit.get(getMenu + AccessTokenApi.getAccessToken().getAccessToken());
        return new ApiResult(jsonResult);
    }

    /**
     * 创建菜单
     */
    public static ApiResult createMenu(String jsonStr) {
        String jsonResult = HttpKit.post(createMenu + AccessTokenApi.getAccessToken().getAccessToken(), jsonStr);
        return new ApiResult(jsonResult);
    }


    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {


        /*ApiConfig.setAppId("wx1f534a2bafbc1388");
        ApiConfig.setAppSecret("2f2ef543c4ecfe78c1333ca6a6d8922c");
        // AccessToken at =AccessTokenApi.getAccessToken();
        String json = " {\n" +
                "     \"button\":[\n" +
                "     {\t\n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"逛一逛\",\n" +
                "          \"url\":\"http://www.yizhanyou.com/\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"菜单\",\n" +
                "           \"sub_button\":[\n" +
                "           {\t\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"搜索\",\n" +
                "               \"url\":\"http://www.soso.com/\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"视频\",\n" +
                "               \"url\":\"http://v.qq.com/\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"click\",\n" +
                "               \"name\":\"赞一下我们\",\n" +
                "               \"key\":\"http://shop.keyunxin.com/\"\n" +
                "            }]\n" +
                "       }]\n" +
                " }";
        createMenu(json).getJson();
        getMenu();*/



       /* if (at.isAvailable())
            System.out.println("access_token : " + at.getAccessToken());
        else
            System.out.println(at.getErrorCode() + " : " + at.getErrorMsg());
    }*/


        //    String jsonResult = HttpKit.post("http://m.cyjoycity.com/Activity/ListView", "{\"groupID\":\"\",\"type\":\"2\",\"pageIndex\":\"3\",\"pageSize\":\"10\"}");
        // System.out.println(jsonResult);


        //String json ="{\"grant_type\":\"client_credentials\",\"client_id\":\"YXA6ZITRoNH7EeSlqLE2Z0h5hg\",\"client_secret\":\"YXA6jrSYbm8gRIYjfuE0p_3Z-m5Zm4I\"}";
        //  String result =   post("http://www.easemob.com/funx/funx/token",json);
        //String jsonResult = HttpKit.post("http://a1.easemob.com/funx/funx/token", json);
        //System.out.println(jsonResult);

        //{"access_token":"YWMtrSSyENH-EeSQHZPJAZ97xgAAAU1_3DjX_fubG1HbHWgKzu_sLl9VEobXYGA","expires_in":5184000,"application":"6484d1a0-d1fb-11e4-a5a8-b13667487986"}

     /*   Map<String, String> map = new HashMap<String, String>();
        map.put("Authorization","Bearer YWMtrSSyENH-EeSQHZPJAZ97xgAAAU1_3DjX_fubG1HbHWgKzu_sLl9VEobXYGA");


       String json = "{\"username\":\"12e3adc527ece406a96a797e7987b6df4\",\"password\":\"123456\"}";

        try {
            HttpKit.post("http://a1.easemob.com/funx/funx/users", json,map);
        }catch (Exception e){
            System.out.print("aaaaaaaaaaaaaaaaaaa");
        }*/

        // /{SoftVersion}/Accounts/{accountSid}/Messages/templateSMS

        //  https://api.ucpaas.com

    /*    {
            "templateSMS" : {
            "appId"       : "e462aba25bc6498fa5ada7eefe1401b7",
                    "param"       : "0000",
                    "templateId"  : "1",
                    "to"          : "18612345678"
        }
        }*/

        //  HttpKit.post("https://api.ucpaas.com/2015-3-26/Accounts/3207969149439840b3f7860a61b2b8d9/Messages/templateSMS", json,map);
  /*      String json = "{\"account\":\"cf_szf\",\"password\":\"119119\",\"mobile\":\"18210275969\",\"content\":\"test\"}";
       String aa = HttpKit.post("http://106.ihuyi.cn/webservice/sms.php?method=Submit", json);
        System.out.print(aa);

      String bb =   HttpKit.get("http://106.ihuyi.cn/webservice/sms.php?method=Submit&account=cf_szf&password=119119&mobile=18210275969&content=您的验证码是：【3256】。请不要把验证码泄露给其他人。") ;
*/
        String bb =   HttpKit.get("http://pili.qiniuapi.com/v2/hubs/dreamgo/streams/57a9448175b62521e8039104/preview");





        System.out.print(bb);
    }
}


