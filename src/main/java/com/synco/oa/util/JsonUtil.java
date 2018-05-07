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
		String dataJson = jsonResult.get("data").toString();
		return dataJson;
	}

}
