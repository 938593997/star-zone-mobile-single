/**   
 * Copyright © 2019 dream horse Info. Tech Ltd. All rights reserved.
 * @Package: com.github.mybatis.fl.util
 * @author: flying-cattle  
 * @date: 2019年4月9日 下午8:15:25 
 */
package com.starzone.utils.generator.util;

import com.starzone.utils.generator.convert.DateType;
import com.starzone.utils.generator.convert.MySqlTypeConvert;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 获奖java中需要的驼峰命名</P>
 * @version: v2.1.0
 * @author: qiu_hf
 * 
 * Modification History:
 * Date         	Author          Version          Description
 *---------------------------------------------------------------*
 * 2019年4月9日      		qiu_hf           v2.1.0           initialize
 */
public class MySqlToJavaUtil {
	
	/**
     * <p>说明:获取java类名</p>
     * @param table  表名
     * @return String
     */
	public static String getClassName(String table) {
		table=changeToJavaFiled(table);
		StringBuilder sbuilder = new StringBuilder();
		char[] cs = table.toCharArray();
		cs[0] -= 32;
		sbuilder.append(String.valueOf(cs));
		return sbuilder.toString();
	}
	
	/**
     * <p>说明:获取字段名，把"_"后面字母变大写</p>
     * @param field  字段名
     * @return String
     */
	public static String changeToJavaFiled(String field) {
		String[] fields = field.split("_");
		StringBuilder sbuilder = new StringBuilder(fields[0]);
		for (int i = 1; i < fields.length; i++) {
			char[] cs = fields[i].toCharArray();
			cs[0] -= 32;
			sbuilder.append(String.valueOf(cs));
		}
		return sbuilder.toString();
	}
	

	/**
     * <p>说明:把sql的数据类型转为java需要的类型</p>
     * @param sqlType  sql类型
     * @return String  java类型
     */
	public static String jdbcTypeToJavaType(String sqlType) {
		MySqlTypeConvert typeConvert= new MySqlTypeConvert();
		return typeConvert.processTypeConvert(DateType.ONLY_DATE, sqlType).getType();
	}
}
