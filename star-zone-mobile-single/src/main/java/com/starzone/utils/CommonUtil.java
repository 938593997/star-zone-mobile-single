package com.starzone.utils;

/**
 * 系统公共工具类
 * @doc 说明
 * @FileName CommonUtil.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年5月3日
 * @history 1.0.0.0 2019年5月3日 下午12:51:17 created by【qiu_hf】
 */
public final class CommonUtil {
	
	/**
	 * 用户登入信息失效
	 * @doc 说明
	 * @param obj 从session获取的user对象
	 * @return 校验结果
	 * @author qiu_hf
	 * @history 2019年5月3日 下午1:00:33 Create by 【qiu_hf】
	 */
	public static JsonResult<Object> checkReturn(Object obj){
		JsonResult<Object> result = new JsonResult<Object>();
		if (null == obj) {
			result.setCode(-600);
			return result;
		}
		return null;
	}

}
