package com.yogee.youge.common.weixin.kit;


import com.yogee.youge.common.weixin.api.ApiConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 测试用的账号：
 * appID = wx9803d1188fa5fbda
 * appsecret = db859c968763c582794e7c3d003c3d87
 * url = http://www.jfinal.com/weixin
 * token = __my__token__
 */
public class SignatureCheckKit {

	public static final SignatureCheckKit me = new SignatureCheckKit();


	/**
	 * php 示例
	 *  $signature = $_GET["signature"];
	 $timestamp = $_GET["timestamp"];
	 $nonce = $_GET["nonce"];

	 $token = TOKEN;
	 $tmpArr = array($token, $timestamp, $nonce);
	 sort($tmpArr, SORT_STRING);
	 $tmpStr = implode( $tmpArr );
	 $tmpStr = sha1( $tmpStr );

	 if( $tmpStr == $signature ){
	 return true;
	 }else{
	 return false;
	 }
	 * @return
	 */
	public boolean checkSignature(String signature, String timestamp, String nonce) {
		String TOKEN = ApiConfig.getToken();
		String array[] = {TOKEN, timestamp, nonce};
		Arrays.sort(array);
		String tempStr = new StringBuilder().append(array[0] + array[1] + array[2]).toString();
		tempStr = EncryptionKit.sha1Encrypt(tempStr);
		return tempStr.equalsIgnoreCase(signature);
	}

	public boolean checkSignature(HttpServletRequest request) {
		return checkSignature(request.getParameter("signature"), request.getParameter("timestamp"), request.getParameter("nonce"));
	}
}



