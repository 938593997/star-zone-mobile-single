/**
 * @filename:SzChooseforyousetServiceImpl 2019年4月13日
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
import com.starzone.pojo.SzChooseforyouset;
import com.starzone.dao.master.SzChooseforyousetDao;
import com.starzone.service.master.SzChooseforyousetService;

/**   
 *  
 * @Description:  我帮你选选项定义——SERVICEIMPL
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
@Service
public class SzChooseforyousetServiceImpl implements SzChooseforyousetService {
	
	@Autowired
	public SzChooseforyousetDao szChooseforyousetDao;
	
	//查询对象
	@Override
	public SzChooseforyouset selectByPrimaryKey(Integer id) {
		return szChooseforyousetDao.selectByPrimaryKey(id);
	}
	
	//删除对象
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return szChooseforyousetDao.deleteByPrimaryKey(id);
	}
	
	//添加对象
	@Override
	public int insertSelective(SzChooseforyouset szChooseforyouset) {
		return szChooseforyousetDao.insertSelective(szChooseforyouset);
	}
	
	//修改对象
	@Override
	public int updateByPrimaryKeySelective(SzChooseforyouset szChooseforyouset) {
		return szChooseforyousetDao.updateByPrimaryKeySelective(szChooseforyouset);
	}
	
	//查询集合
	@Override
	public List<SzChooseforyouset> querySzChooseforyousetList(SzChooseforyouset szChooseforyouset) {
		return szChooseforyousetDao.querySzChooseforyousetList(szChooseforyouset);
	}
	
	//分页查询
	@Override
	public PageInfo<SzChooseforyouset> getSzChooseforyousetBySearch(AppPage<SzChooseforyouset> page) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize()); // 设置分页信息
		List<SzChooseforyouset> list = szChooseforyousetDao.querySzChooseforyousetList(page.getParam()); // 自动将分页信息与返回数据组装
		PageInfo<SzChooseforyouset> pageInfo = new PageInfo<SzChooseforyouset>(list);
		return pageInfo;
	}

	// 通过选择id删除选项
	@Override
	public int deleteByChooseId(String chooseId) {
		return szChooseforyousetDao.deleteByChooseId(chooseId);
	}
}