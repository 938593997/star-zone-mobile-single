/**   
 * Copyright © 2019 dream horse Info. Tech Ltd. All rights reserved.
 * @Package: com.github.mybatis.fl.entity
 * @author: flying-cattle  
 * @date: 2019年4月9日 下午8:15:25 
 */
package com.starzone.utils.generator.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 获取到数据库的信息</P>
 * @version: v2.1.0
 * @author: qiu_hf
 * 
 * Modification History:
 * Date         	Author          Version          Description
 *---------------------------------------------------------------*
 * 2019年4月9日      		qiu_hf           v2.1.0           initialize
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyInfo implements Serializable{
	
	private static final long serialVersionUID = 123124L;
	
	private String column;

	private String jdbcType;
	 
	private String comment;
	 
	private String property;
	 
	private String javaType;

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	
}
