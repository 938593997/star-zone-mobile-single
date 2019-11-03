/**
 * @filename:SzSpendDetailsServiceImpl 2019年5月17日
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
import com.starzone.dao.master.SzSpendDetailsDao;
import com.starzone.service.master.SzSpendDetailsService;

/**   
 * @Description:  消费详情——SERVICEIMPL
 * @Author:       qiu_hf   
 * @CreateDate:   2019年5月17日
 * @Version:      V1.0
 */
@Service
public class SzSpendDetailsServiceImpl implements SzSpendDetailsService {
	
	@Autowired
	public SzSpendDetailsDao szSpendDetailsDao;
	
	//查询对象
	@Override
	public SzSpendDetails selectByPrimaryKey(String id) {
		return szSpendDetailsDao.selectByPrimaryKey(id);
	}
	
	//删除对象
	@Override
	public int deleteByPrimaryKey(String id) {
		return szSpendDetailsDao.deleteByPrimaryKey(id);
	}
	
	//添加对象
	@Override
	public int insertSelective(SzSpendDetails szSpendDetails) {
		return szSpendDetailsDao.insertSelective(szSpendDetails);
	}
	
	//修改对象
	@Override
	public int updateByPrimaryKeySelective(SzSpendDetails szSpendDetails) {
		return szSpendDetailsDao.updateByPrimaryKeySelective(szSpendDetails);
	}
	
	//查询集合
	@Override
	public List<SzSpendDetails> querySzSpendDetailsList(SzSpendDetails szSpendDetails) {
		return szSpendDetailsDao.querySzSpendDetailsList(szSpendDetails);
	}
	
	//分页查询
	@Override
	public PageInfo<SzSpendDetails> getSzSpendDetailsBySearch(AppPage<SzSpendDetails> page) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize()); // 设置分页信息
		List<SzSpendDetails> list = szSpendDetailsDao.querySzSpendDetailsList(page.getParam()); // 自动将分页信息与返回数据组装
		PageInfo<SzSpendDetails> pageInfo = new PageInfo<SzSpendDetails>(list);
		return pageInfo;
	}

	//根据type删除消费详情
	@Override
	public int deleteByType(String type) {
		return szSpendDetailsDao.deleteByType(type);
	}
}