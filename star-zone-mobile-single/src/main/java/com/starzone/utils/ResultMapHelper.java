package com.starzone.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Map工具类
 * @doc 说明
 * @FileName ResultMapHelper.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年9月23日
 * @history 1.0.0.0 2019年9月23日 下午8:36:02 created by【qiu_hf】
 */
public class ResultMapHelper {

	public static final Map<String, Object> getSuccessMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 1);
		map.put("result", "success");
		return map;
	}
}
