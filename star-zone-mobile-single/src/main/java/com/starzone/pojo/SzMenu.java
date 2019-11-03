/**
 * @filename:SzMenu 2019年4月13日
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
 * @Description:  菜单
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@SuppressWarnings("unused")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SzMenu implements Serializable {

	private static final long serialVersionUID = 1555163140366L;
	
	@ApiModelProperty(name = "id" , value = "")
	private String id;
	@ApiModelProperty(name = "name" , value = "")
	private String name;
	@ApiModelProperty(name = "type" , value = "")
	private String type;
	@ApiModelProperty(name = "createTime" , value = "")
	private String createTime;
	@ApiModelProperty(name = "isdelete" , value = "")
	private String isdelete;
	@ApiModelProperty(name = "ext1" , value = "")
	private String ext1;
	@ApiModelProperty(name = "ext2" , value = "")
	private String ext2;
	@ApiModelProperty(name = "ext3" , value = "")
	private String ext3;
	@ApiModelProperty(name = "ext4" , value = "")
	private String ext4;
	@ApiModelProperty(name = "ext5" , value = "")
	private String ext5;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public String getExt4() {
		return ext4;
	}
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}
	public String getExt5() {
		return ext5;
	}
	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}
}
