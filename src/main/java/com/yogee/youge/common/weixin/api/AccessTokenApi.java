package com.yogee.youge.common.weixin.api;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.yogee.youge.common.config.Global;
import com.yogee.youge.common.weixin.kit.HttpKit;
import com.yogee.youge.common.weixin.kit.ParaMap;

import java.io.IOException;
import java.util.Map;

/**
 * 认证并获取 access_token API
 * http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96access_token
 */
public class AccessTokenApi {

	// "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

	private static AccessToken accessToken;

	public static AccessToken getAccessToken() {
		if (accessToken != null && accessToken.isAvailable())
			return accessToken;

		for (int i=0; i<3; i++) {
			accessToken = requestAccesToken();
			if (accessToken.isAvailable())
				break;
		}
		return accessToken;
	}

	private static AccessToken requestAccesToken() {
		final String appId = ApiConfig.getAppId();
		final String appSecret = ApiConfig.getAppSecret();
		Map<String, String> queryParas = ParaMap.create("appid", appId).put("secret", appSecret).getData();
		String json = HttpKit.get(url, queryParas);
		return new AccessToken(json);
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ApiConfig.setAppId(Global.getConfig("wechat.app.id"));
		ApiConfig.setAppSecret(Global.getConfig("wechat.app.secret"));

		AccessToken at = getAccessToken();
		if (at.isAvailable())
			System.out.println("access_token : " + at.getAccessToken());
		else
			System.out.println(at.getErrorCode() + " : " + at.getErrorMsg());
	}
}
