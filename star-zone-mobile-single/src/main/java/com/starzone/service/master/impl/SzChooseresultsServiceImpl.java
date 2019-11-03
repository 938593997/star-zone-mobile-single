/**
 * @filename:SzChooseresultsServiceImpl 2019年4月13日
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
import com.starzone.pojo.SzChooseresults;
import com.starzone.dao.master.SzChooseresultsDao;
import com.starzone.service.master.SzChooseresultsService;

/**   
 *  
 * @Description:  选择结果——SERVICEIMPL
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
@Service
public class SzChooseresultsServiceImpl implements SzChooseresultsService {
	
	@Autowired
	public SzChooseresultsDao szChooseresultsDao;
	
	//查询对象
	@Override
	public SzChooseresults selectByPrimaryKey(String id) {
		return szChooseresultsDao.selectByPrimaryKey(id);
	}
	
	//删除对象
	@Override
	public int deleteByPrimaryKey(String id) {
		return szChooseresultsDao.deleteByPrimaryKey(id);
	}
	
	//添加对象
	@Override
	public int insertSelective(SzChooseresults szChooseresults) {
		return szChooseresultsDao.insertSelective(szChooseresults);
	}
	
	//修改对象
	@Override
	public int updateByPrimaryKeySelective(SzChooseresults szChooseresults) {
		return szChooseresultsDao.updateByPrimaryKeySelective(szChooseresults);
	}
	
	//查询集合
	@Override
	public List<SzChooseresults> querySzChooseresultsList(SzChooseresults szChooseresults) {
		return szChooseresultsDao.querySzChooseresultsList(szChooseresults);
	}
	
	//分页查询
	@Override
	public PageInfo<SzChooseresults> getSzChooseresultsBySearch(AppPage<SzChooseresults> page) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize()); // 设置分页信息
		List<SzChooseresults> list = szChooseresultsDao.querySzChooseresultsList(page.getParam()); // 自动将分页信息与返回数据组装
		PageInfo<SzChooseresults> pageInfo = new PageInfo<SzChooseresults>(list);
		return pageInfo;
	}

	// 通过选择chooseId删除选择结果
	@Override
	public int deleteByChooseId(String chooseId) {
		return szChooseresultsDao.deleteByChooseId(chooseId);
	}
}