/**
 * @filename:SzSpendDetails 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**   
 * @Description:  花费详情信息
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@SuppressWarnings("unused")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SzSpendDetails implements Serializable {

	private static final long serialVersionUID = 1555160893616L;
	
	@ApiModelProperty(name = "id" , value = "")
	private String id;
	@ApiModelProperty(name = "name" , value = "")
	private String name;
	@ApiModelProperty(name = "detailShowWay" , value = "")
	private String detailShowWay;
	@ApiModelProperty(name = "picUrl" , value = "")
	private String picUrl;
	@ApiModelProperty(name = "color" , value = "")
	private String color;
	@ApiModelProperty(name = "season" , value = "")
	private String season;
	@ApiModelProperty(name = "price" , value = "")
	private String price;
	@ApiModelProperty(name = "snacks" , value = "")
	private String snacks;
	@ApiModelProperty(name = "bigEat" , value = "")
	private String bigEat;
	@ApiModelProperty(name = "simpleEat" , value = "")
	private String simpleEat;
	@ApiModelProperty(name = "oil" , value = "")
	private String oil;
	@ApiModelProperty(name = "rice" , value = "")
	private String rice;
	@ApiModelProperty(name = "discount" , value = "")
	private String discount;
	@ApiModelProperty(name = "buyWay" , value = "")
	private String buyWay;
	@ApiModelProperty(name = "happenTime" , value = "")
	private String happenTime;
	@ApiModelProperty(name = "createTime" , value = "")
	private String createTime;
	@ApiModelProperty(name = "favoriteTime" , value = "")
	private String favoriteTime;
	@ApiModelProperty(name = "favoriteDegree" , value = "")
	private String favoriteDegree;
	@ApiModelProperty(name = "size" , value = "")
	private String size;
	@ApiModelProperty(name = "buyAge" , value = "")
	private String buyAge;
	@ApiModelProperty(name = "period" , value = "")
	private String period;
	@ApiModelProperty(name = "detailType" , value = "")
	private String detailType;
	
	@Transient
	private Integer detailTypeInt;
	@Transient
	private Integer periodInt;
	@Transient
	private Integer happyDegreeInt;
	@Transient
	private String prices; // 每年使用的总钱数
	@Transient
	private String years; // 年份
	@Transient
	private String typePrices; // 年份
	
	@ApiModelProperty(name = "address" , value = "")
	private String address;
	@ApiModelProperty(name = "owner" , value = "")
	private String owner;
	@ApiModelProperty(name = "waterSpend" , value = "")
	private String waterSpend;
	@ApiModelProperty(name = "type" , value = "")
	private String type;
	@ApiModelProperty(name = "electricSpend" , value = "")
	private String electricSpend;
	@ApiModelProperty(name = "livingType" , value = "")
	private String livingType;
	@ApiModelProperty(name = "happyDegree" , value = "")
	private String happyDegree;
	@ApiModelProperty(name = "who" , value = "")
	private String who;
	@ApiModelProperty(name = "isReturn" , value = "")
	private String isReturn;
	@ApiModelProperty(name = "outOrGet" , value = "")
	private String outOrGet;
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
	public String getDetailShowWay() {
		return detailShowWay;
	}
	public void setDetailShowWay(String detailShowWay) {
		this.detailShowWay = detailShowWay;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSnacks() {
		return snacks;
	}
	public void setSnacks(String snacks) {
		this.snacks = snacks;
	}
	public String getBigEat() {
		return bigEat;
	}
	public void setBigEat(String bigEat) {
		this.bigEat = bigEat;
	}
	public String getSimpleEat() {
		return simpleEat;
	}
	public void setSimpleEat(String simpleEat) {
		this.simpleEat = simpleEat;
	}
	public String getOil() {
		return oil;
	}
	public void setOil(String oil) {
		this.oil = oil;
	}
	public String getRice() {
		return rice;
	}
	public void setRice(String rice) {
		this.rice = rice;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getBuyWay() {
		return buyWay;
	}
	public void setBuyWay(String buyWay) {
		this.buyWay = buyWay;
	}
	public String getHappenTime() {
		return happenTime;
	}
	public void setHappenTime(String happenTime) {
		this.happenTime = happenTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getFavoriteTime() {
		return favoriteTime;
	}
	public void setFavoriteTime(String favoriteTime) {
		this.favoriteTime = favoriteTime;
	}
	public String getFavoriteDegree() {
		return favoriteDegree;
	}
	public void setFavoriteDegree(String favoriteDegree) {
		this.favoriteDegree = favoriteDegree;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getBuyAge() {
		return buyAge;
	}
	public void setBuyAge(String buyAge) {
		this.buyAge = buyAge;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getDetailType() {
		return detailType;
	}
	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getWaterSpend() {
		return waterSpend;
	}
	public void setWaterSpend(String waterSpend) {
		this.waterSpend = waterSpend;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getElectricSpend() {
		return electricSpend;
	}
	public void setElectricSpend(String electricSpend) {
		this.electricSpend = electricSpend;
	}
	public String getLivingType() {
		return livingType;
	}
	public void setLivingType(String livingType) {
		this.livingType = livingType;
	}
	public String getHappyDegree() {
		return happyDegree;
	}
	public void setHappyDegree(String happyDegree) {
		this.happyDegree = happyDegree;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getIsReturn() {
		return isReturn;
	}
	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}
	public String getOutOrGet() {
		return outOrGet;
	}
	public void setOutOrGet(String outOrGet) {
		this.outOrGet = outOrGet;
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
	public Integer getDetailTypeInt() {
		return detailTypeInt;
	}
	public void setDetailTypeInt(Integer detailTypeInt) {
		this.detailTypeInt = detailTypeInt;
	}
	public Integer getPeriodInt() {
		return periodInt;
	}
	public void setPeriodInt(Integer periodInt) {
		this.periodInt = periodInt;
	}
	public Integer getHappyDegreeInt() {
		return happyDegreeInt;
	}
	public void setHappyDegreeInt(Integer happyDegreeInt) {
		this.happyDegreeInt = happyDegreeInt;
	}
	public String getPrices() {
		return prices;
	}
	public void setPrices(String prices) {
		this.prices = prices;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public String getTypePrices() {
		return typePrices;
	}
	public void setTypePrices(String typePrices) {
		this.typePrices = typePrices;
	}
}
