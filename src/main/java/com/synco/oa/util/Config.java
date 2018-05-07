package com.synco.oa.util;

public class Config {
	
	public static final String AUTHURL="https://api.mingdao.com/oauth2/authorize?";
	public static final  String ACCEURL="https://api.mingdao.com/oauth2/access_token?";
	
	public static final  String APPKEY="0FE52514F596";//需要换成您的应用的appkey
	public static final  String APPSECRET="731869AC43DF57DD50C48C2A676848F";//需要换成您的应用的appSecret
	public static final  String RESPONSE_TYPE="token";//token或者code
	public static final  String REDIRECT_URI="http://127.0.0.1:8080/Test/getAccessTokenUrl";//需要换成您的应用设置的回调地址
	
	public static String getAuthorizeUrl(){
		String url=AUTHURL
			+"app_key="+APPKEY
			+"&redirect_uri="+REDIRECT_URI
			+"&response_type="+RESPONSE_TYPE;
		return url;
	}
	
	public static String getAccessTokenUrl(String code){
		String url=ACCEURL
			+"app_key="+APPKEY
			+"&app_secret="+APPSECRET
			+"&grant_type=authorization_code"
			+"&format=json"
			+"&redirect_uri="+REDIRECT_URI
			+"&code="+code;
		return url;
	}
	public static String getAccessTokenByCode(String code){
		String url =getAccessTokenUrl(code);
		String result=HttpUtil.httpByGet2StringSSL(url, null, null);
		return result;
	}
}

