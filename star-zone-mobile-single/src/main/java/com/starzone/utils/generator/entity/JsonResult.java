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
 * <p>说明： 返回结果json对象</P>
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
public class JsonResult implements Serializable{
	
	private static final long serialVersionUID = 123126L;
	
	private Integer code;
	
	private String message;
	
	private Object data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
