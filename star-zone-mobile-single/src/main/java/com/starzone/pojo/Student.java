/**
 * @filename:Student 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.pojo;

import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**   
 * @Description:  问卷调查
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@SuppressWarnings("unused")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

	private static final long serialVersionUID = 1555163938107L;
	
	@ApiModelProperty(name = "id" , value = "")
	private Integer id;
	@ApiModelProperty(name = "name" , value = "")
	private String name;
	@ApiModelProperty(name = "age" , value = "")
	private Integer age;
}
