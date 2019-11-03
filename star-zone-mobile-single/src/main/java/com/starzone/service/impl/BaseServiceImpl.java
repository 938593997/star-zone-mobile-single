package com.starzone.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.PageHelper;
import com.starzone.dao.BaseDao;
import com.starzone.pojo.Student;
import com.starzone.pojo.User;

/**
 * 数据层公共实现类 
 * @doc 说明
 * @FileName BaseServiceImpl.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年4月13日
 * @history 1.0.0.0 2019年4月13日 下午12:04:05 created by【qiu_hf】
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements com.starzone.service.BaseService<T> {
	
	private static final Logger logger= LoggerFactory.getLogger(BaseServiceImpl.class);
	protected abstract BaseDao<T> getMapper();
	
	@Override
	public boolean insert(T entity)  {
		boolean falg=false;
		try {
			getMapper().insert(entity);
			falg=true;
		} catch (Exception e) {
			logger.error("新增"+getClassName(entity)+"失败!原因是:",e);
		}
		return falg;
	}
	
	
	@Override
	public boolean update(T entity){
		boolean falg=false;
		try {
			 getMapper().update(entity);
			 falg=true;
		} catch (Exception e) {
			logger.error("更新"+getClassName(entity)+"失败!原因是:",e);
		}
		return falg;
	}
	
	@Override
	public boolean deleteByPrimaryKey(int id)  {
		boolean falg=false;
		try {
			 getMapper().deleteByPrimaryKey(id);
			 falg=true;
		} catch (Exception e) {
			logger.error("id:"+id+"删除失败!原因是:",e);
		}
		return falg;
	}
	
	@Override
	public boolean delete(T entity){
		boolean falg=false;
		try {
			getMapper().delete(entity);
			falg=true;
		} catch (Exception e) {
			logger.error("删除"+getClassName(entity)+"失败!原因是:",e);
		}
		return falg;
	}
	
	@Override
	public T findByPrimaryKey(int id) {
		T obj = null;
		try {
			obj = getMapper().findByPrimaryKey(id);
		} catch (Exception e) {
			logger.error("id:"+id+"查询失败!原因是:",e);
		}
		return obj;
	}
	
	@Override
	public T findByEntity(T entity) {
		T obj = null;
		try {
			obj = getMapper().findByEntity(entity);
		} catch (Exception e) {
			logger.error("查询"+getClassName(entity)+"失败!原因是:",e);
		}
		return obj;
	}
	
	@Override
	public List<T> findByListEntity(T entity) {
		List<T> list = null;
		try {
			PageHelper.startPage(1,2);// 设置分页信息
			list = getMapper().findByListEntity(entity);// 自动将分页信息与返回数据组装
		} catch (Exception e) {
			logger.error("查询"+getClassName(entity)+"失败!原因是:",e);
		}
		return list;// 重要参数： Page{pageNum=1, pageSize=2, total=1, pages=1}
	}
	
	@Override
	public List<T> findAll() {
		List<T> list = null;
		try {
			list =  getMapper().findAll();
		} catch (Exception e) {
			logger.error("查询失败!原因是:",e);
		}
		return list;
	}
	
	@Override
	public Object findByObject(Object obj) {
		Object result = null;
		try {
			result = getMapper().findByObject(obj);
		} catch (Exception e) {
			logger.error("查询"+obj+"失败!原因是:",e);
		}
		return result;
	}
	
	private String getClassName(T t){
		String str="";
		if(t instanceof User){
			str="User";
		}else if(t instanceof Student){
			str="Studnet";
		}
		return str;
	}
}
