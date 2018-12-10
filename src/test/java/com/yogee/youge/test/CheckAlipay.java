//package com.yogee.youge.test;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.URL;
//
//import javax.net.ssl.HttpsURLConnection;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//
///**
// * JSONP方式调用，地址 /alipay.check ,回调函数名 callback
// * 检测有效返回数据格式（已注册的账号）：
// * {"nick":"cai***","acctstatus":"taobaouser","acctType":"email","stat":"ok"}
// * 非淘宝，仅注册了支付宝：
// * {"acctstatus":"login","acctType":"email","stat":"ok"}
// * 检测无效返回数据格式（未注册的账号）：
// * {"acctstatus":"available","acctType":"email","stat":"ok"}
// * 账号格式不正确：
// * {"acctstatus":"invalid","acctType":null,"stat":"ok"}
// */
//@WebServlet(description = "检测支付宝账号是否有效", urlPatterns = { "/alipay.check" })
//public class CheckAlipay extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    // 有效的账户状态
//    private static final String ACCTSTATUS = "acctstatus";
//    private static final String VALID_ACCTSTATUS = "taobaouser,login";
//
//    // 请求验证的地址
//    private static final String ACCTSTATUSCHECK_URL = "https://memberprod.alipay.com/account/reg/acctStatusCheck.json";
//
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public CheckAlipay() {
//        super();
//    }
//
//    /**
//     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//     */
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String account = request.getParameter("pay_num");
//        String funcName = request.getParameter("callback");
//
//        if(null != account)
//            account = account.trim();
//
//        response.setContentType("text/javascript;charset=utf-8");
//        response.setHeader("Cache-Control","no-cache");
//        PrintWriter out= response.getWriter();
//
//        // 判断是否为JSONP格式调用，不是则直接返回boolean
//        if(funcName == null) {
//            out.print(CheckAlipay.checkAlipay(account));
//        } else {
//            funcName = funcName.trim();
//
//            String result = CheckAlipay.check(account);
//            if (! "".equals(result)) {
//                out.print(funcName + "(" + result + ")");
//            } else {
//                funcName = "callback";
//                out.print(funcName + "{acctstatus: 'invalid'}");
//            }
//        }
//        out.flush();
//        out.close();
//        return;
//    }
//
//    /**
//     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//     */
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        this.doGet(request, response);
//    }
//
//    /**
//     * 检测有效返回数据格式（已注册的账号）：
//     * {"nick":"cai***","acctstatus":"taobaouser","acctType":"email","stat":"ok"}
//     * 检测无效返回数据格式（未注册的账号）：
//     * {"acctstatus":"available","acctType":"email","stat":"ok"}
//     * 账号格式不正确：
//     * {"acctstatus":"invalid","acctType":null,"stat":"ok"}
//     *
//     * @param account 支付宝账号，可以是邮箱或手机号码
//     * @return json格式的验证结果
//     * @throws IOException
//     */
//    private static String check(String account) throws IOException {
//        URL url = new URL(CheckAlipay.ACCTSTATUSCHECK_URL);
////		String acctType = "email"; //可以为空
//
//        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//        HttpsURLConnection.setFollowRedirects(true);
//
//        connection.setDoInput(true);
//        connection.setDoOutput(true);
//
//        connection.setRequestMethod("POST");
//        String query = "_input_charset=utf-8";
//        //	query += "&acctType=mobile";
//        //	query += "&acctType=email";
//        query += "&acctname=";
//        query += account;
//
//        connection.setRequestProperty("Content-length", String.valueOf(query.length()));
//        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:8.0) Gecko/20100101 Firefox/8.0");
//
//        // open up the output stream of the connection
//        DataOutputStream output = new DataOutputStream(
//                connection.getOutputStream());
//
//        output.writeBytes(query);
//
//        //	System.out.println("Resp Code:" + connection.getResponseCode());
//        //	System.out.println("Resp Message:" + connection.getResponseMessage());
//
//        // get ready to read the response from the url
//        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//        String result = "", r ;
//
//        // read in each character until end-of-stream is detected
//        while ((r = reader.readLine()) != null) {
//            result += r;
//        }
//
//        reader.close();
//
//        return result;
//    }
//
//    /**
//     * 检测支付宝账号是否有效
//     * @param account 支付宝账号
//     * @return
//     */
//    public static boolean checkAlipay(String account) {
//        try {
//            String result = CheckAlipay.check(account);
//            JSONObject resultJSON = JSON.parseObject(result);
//
//            String acctstatus = resultJSON.getString(CheckAlipay.ACCTSTATUS);
//
//            return CheckAlipay.VALID_ACCTSTATUS.indexOf(acctstatus) != -1;
//
//        } catch (IOException e) {
//            //e.printStackTrace();
//            return false;
//        }
//    }
//
//
//    public static void main(String[] args) {
//        System.err.println(CheckAlipay.checkAlipay("caiceclb@163.com"));
//        System.err.println(CheckAlipay.checkAlipay("13524990353"));
//        System.err.println(CheckAlipay.checkAlipay("2577339893@qq.com"));
//    }
//}
