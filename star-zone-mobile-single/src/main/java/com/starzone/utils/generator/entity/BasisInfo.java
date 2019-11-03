/**   
 * Copyright © 2019 dream horse Info. Tech Ltd. All rights reserved.
 * @Package: com.github.mybatis.fl.entity
 * @author: flying-cattle  
 * @date: 2019年4月9日 下午8:15:25 
 */
package com.starzone.utils.generator.entity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 自动生成需要的基本信息</P>
 * @version: v2.1.0
 * @author: qiu_hf
 * 
 * Modification History:
 * Date         	Author          Version          Description
 *---------------------------------------------------------------*
 * 2019年4月9日      		qiu_hf          v2.1.0           initialize
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasisInfo implements Serializable{
	private static final long serialVersionUID = 123123L;

	private String project;
	
	private String author;
	
	private String version;
	
	private String dbUrl;
	
	private String dbName;
	
	private String dbPassword;
	
	private String database;
	
	private String table;
	
	private String entityName;
	
	private String objectName;
	
	private String entityComment;
	
	private String createTime;
	
	private String agile;
	
	private String entityUrl;
	
	private String daoUrl;
	
	private String mapperUrl;
	
	private String serviceUrl;
	
	private String serviceImplUrl;
	
	private String controllerUrl;
	
	private String idType;
	
	private String idJdbcType;
	
	private List<PropertyInfo> cis;
	
	public BasisInfo(String project, String author, String version, String dbUrl, String dbName, String dbPassword,
			String database, String createTime, String agile, String entityUrl, String daoUrl, String mapperUrl,
			String serviceUrl, String serviceImplUrl, String controllerUrl) {
		super();
		this.project = project;
		this.author = author;
		this.version = version;
		this.dbUrl = dbUrl;
		this.dbName = dbName;
		this.dbPassword = dbPassword;
		this.database = database;
		this.createTime = createTime;
		this.agile = agile;
		this.entityUrl = entityUrl;
		this.daoUrl = daoUrl;
		this.mapperUrl = mapperUrl;
		this.serviceUrl = serviceUrl;
		this.serviceImplUrl = serviceImplUrl;
		this.controllerUrl = controllerUrl;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getEntityComment() {
		return entityComment;
	}

	public void setEntityComment(String entityComment) {
		this.entityComment = entityComment;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAgile() {
		return agile;
	}

	public void setAgile(String agile) {
		this.agile = agile;
	}

	public String getEntityUrl() {
		return entityUrl;
	}

	public void setEntityUrl(String entityUrl) {
		this.entityUrl = entityUrl;
	}

	public String getDaoUrl() {
		return daoUrl;
	}

	public void setDaoUrl(String daoUrl) {
		this.daoUrl = daoUrl;
	}

	public String getMapperUrl() {
		return mapperUrl;
	}

	public void setMapperUrl(String mapperUrl) {
		this.mapperUrl = mapperUrl;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getServiceImplUrl() {
		return serviceImplUrl;
	}

	public void setServiceImplUrl(String serviceImplUrl) {
		this.serviceImplUrl = serviceImplUrl;
	}

	public String getControllerUrl() {
		return controllerUrl;
	}

	public void setControllerUrl(String controllerUrl) {
		this.controllerUrl = controllerUrl;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdJdbcType() {
		return idJdbcType;
	}

	public void setIdJdbcType(String idJdbcType) {
		this.idJdbcType = idJdbcType;
	}

	public List<PropertyInfo> getCis() {
		return cis;
	}

	public void setCis(List<PropertyInfo> cis) {
		this.cis = cis;
	}
	
}
