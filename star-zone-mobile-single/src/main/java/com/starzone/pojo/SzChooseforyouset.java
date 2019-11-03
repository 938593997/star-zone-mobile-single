/**
 * @filename:SzChooseforyouset 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**   
 * @Description:  我帮你选选项定义
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@SuppressWarnings("unused")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SzChooseforyouset implements Serializable {

	private static final long serialVersionUID = 1555163241085L;
	
	@ApiModelProperty(name = "id" , value = "")
	private Integer id;
	@ApiModelProperty(name = "chooseId" , value = "")
	private String chooseId;
	@ApiModelProperty(name = "position" , value = "")
	private String position;
	@ApiModelProperty(name = "name" , value = "")
	private String name;
	@ApiModelProperty(name = "type" , value = "")
	private String type;
	@ApiModelProperty(name = "createTime" , value = "")
	private String createTime;
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
	
	private List<String> setList; // 接收前端的选择项
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChooseId() {
		return chooseId;
	}
	public void setChooseId(String chooseId) {
		this.chooseId = chooseId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
	public List<String> getSetList() {
		return setList;
	}
	public void setSetList(List<String> setList) {
		this.setList = setList;
	}
}
