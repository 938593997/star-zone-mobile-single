/**   
 * Copyright © 2019 dream horse Info. Tech Ltd. All rights reserved.
 * @Package: com.github.mybatis.fl.convert
 * @author: flying-cattle  
 * @date: 2019年4月9日 下午8:15:25 
 */
package com.starzone.utils.generator.generator;

import java.sql.SQLException;
import java.util.Date;

import com.starzone.utils.generator.entity.BasisInfo;
import com.starzone.utils.generator.util.EntityInfoUtil;
import com.starzone.utils.generator.util.Generator;
import com.starzone.utils.generator.util.MySqlToJavaUtil;
/**
 * 修改生成信息后，run as即可在指定目录下生成代码
 * @doc 说明
 * @FileName Generator.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年4月13日
 * @history 1.0.0.0 2019年4月13日 下午4:05:04 created by【qiu_hf】
 */
public class GeneratorUtil {
	//基础信息
	public static final String PROJECT="star-zone";
	public static final String AUTHOR="qiu_hf";
	public static final String VERSION="V1.0";
	//数据库连接信息
	public static final String URL="jdbc:mysql://localhost:3306/star_zone?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true";
	public static final String NAME="root";
	public static final String PASSWORD="123456";
	public static final String DATABASE="star_zone";
	//类信息
	public static final String TABLE="t_apply_for_leave";
	public static final String CLASSNAME="applyForLeave";// 首字母小写，生成时会变成大写
	public static final String CLASSCOMMENT="请假申请";
	public static final String TIME="2019年9月27日";
	public static final String AGILE=new Date().getTime()+"";
	//路径信息
	public static final String ENTITY_URL="com.starzone.pojo";
	public static final String DAO_URL="com.starzone.dao.master";
	public static final String XML_URL="mapper.master";
	public static final String SERVICE_URL="com.starzone.service.master";
	public static final String SERVICE_IMPL_URL="com.starzone.service.master.impl";
	public static final String CONTROLLER_URL="com.starzone.web";
	
	
	public static void main(String[] args) {
		BasisInfo bi=new BasisInfo(PROJECT, AUTHOR, VERSION, URL, NAME, PASSWORD, DATABASE, TIME, AGILE, ENTITY_URL, DAO_URL, XML_URL, SERVICE_URL, SERVICE_IMPL_URL, CONTROLLER_URL);
		bi.setTable(TABLE);
		bi.setEntityName(MySqlToJavaUtil.getClassName(CLASSNAME));
		bi.setObjectName(MySqlToJavaUtil.changeToJavaFiled(CLASSNAME));
		bi.setEntityComment(CLASSCOMMENT);
		try {
			bi=EntityInfoUtil.getInfo(bi);
			String aa1=Generator.createEntity("C:\\generatorCodes\\", bi).toString();
			String aa2=Generator.createDao("C:\\generatorCodes\\", bi).toString();
			String aa3=Generator.createDaoImpl("C:\\generatorCodes\\", bi).toString();
			String aa4=Generator.createService("C:\\generatorCodes\\", bi).toString();
			String aa5=Generator.createServiceImpl("C:\\generatorCodes\\", bi).toString();
			String aa6=Generator.createController("C:\\generatorCodes\\", bi).toString();
			
			System.out.println(aa1);
			System.out.println(aa2);
			System.out.println(aa3);
			System.out.println(aa4);
			System.out.println(aa5);
			System.out.println(aa6);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
