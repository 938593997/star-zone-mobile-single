/**
 * @filename:SzSpendTypeServiceImpl 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.service.master.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.pojo.SzSpendDetails;
import com.starzone.pojo.SzSpendType;
import com.starzone.dao.master.SzSpendDetailsDao;
import com.starzone.dao.master.SzSpendTypeDao;
import com.starzone.service.master.SzSpendTypeService;

/**   
 *  
 * @Description:  花费类型——SERVICEIMPL
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
@Service
public class SzSpendTypeServiceImpl implements SzSpendTypeService {
	
	@Autowired
	public SzSpendTypeDao szSpendTypeDao;
	
	@Autowired
	public SzSpendDetailsDao szSpendDetailsDao;
	
	//查询对象
	@Override
	public SzSpendType selectByPrimaryKey(String id) {
		return szSpendTypeDao.selectByPrimaryKey(id);
	}
	
	//删除对象
	@Override
	public int deleteByPrimaryKey(String id) {
		return szSpendTypeDao.deleteByPrimaryKey(id);
	}
	
	//添加对象
	@Override
	public int insertSelective(SzSpendType szSpendType) {
		return szSpendTypeDao.insertSelective(szSpendType);
	}
	
	//修改对象
	@Override
	public int updateByPrimaryKeySelective(SzSpendType szSpendType) {
		return szSpendTypeDao.updateByPrimaryKeySelective(szSpendType);
	}
	
	//查询集合
	@Override
	public List<SzSpendType> querySzSpendTypeList(SzSpendType szSpendType) {
		return szSpendTypeDao.querySzSpendTypeList(szSpendType);
	}
	
	//分页查询
	@Override
	public PageInfo<SzSpendType> getSzSpendTypeBySearch(AppPage<SzSpendType> page) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize()); // 设置分页信息
		List<SzSpendType> list = szSpendTypeDao.querySzSpendTypeList(page.getParam()); // 自动将分页信息与返回数据组装
		PageInfo<SzSpendType> pageInfo = new PageInfo<SzSpendType>(list);
		return pageInfo;
	}

	// pie、Bar按年分组查询数据
	@Override
	public List<SzSpendDetails> findPieAndBarDatasGroupByYear(SzSpendType szSpendType) {
		return szSpendDetailsDao.findPieAndBarDatasGroupByYear(szSpendType);
	}

	// pie、Bar按消费类型分组查的数据，可以按年过滤
	@Override
	public List<SzSpendDetails> findPieAndBarDatasGroupByType(SzSpendType szSpendType) {
		return szSpendDetailsDao.findPieAndBarDatasGroupByType(szSpendType);
	}
}