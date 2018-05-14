/**
 * 
 */
package com.synco.oa.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author LiQian
 *
 */
public class JsonUtil {
	public static String getJsonPojo(String json) {
		// 提取JOSN格式化
		JSONObject jsonResult = JSONObject.parseObject(json);
		// 获取data的数据
		//user:::{"error_code":10105,"error_msg":"用户访问令牌失效","success":false}
		//user:::{"data":{"account_id":
		//System.out.println("user:::"+jsonResult);
		Object r=jsonResult.get("data");
		if(r==null) {return null;}
		String dataJson =r .toString();
		return dataJson;
	}

}
