/**
 * @filename:SzChooseforyouServiceImpl 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.service.master.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.pojo.SzChooseforyou;
import com.starzone.dao.master.SzChooseforyouDao;
import com.starzone.service.master.SzChooseforyouService;

/**   
 *  
 * @Description:  我帮你选——SERVICEIMPL
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
@Service
public class SzChooseforyouServiceImpl implements SzChooseforyouService {
	
	@Autowired
	public SzChooseforyouDao szChooseforyouDao;
	
	//查询对象
	@Override
	public SzChooseforyou selectByPrimaryKey(String id) {
		return szChooseforyouDao.selectByPrimaryKey(id);
	}
	
	//删除对象
	@Override
	public int deleteByPrimaryKey(String id) {
		return szChooseforyouDao.deleteByPrimaryKey(id);
	}
	
	//添加对象
	@Override
	public int insertSelective(SzChooseforyou szChooseforyou) {
		return szChooseforyouDao.insertSelective(szChooseforyou);
	}
	
	//修改对象
	@Override
	public int updateByPrimaryKeySelective(SzChooseforyou szChooseforyou) {
		return szChooseforyouDao.updateByPrimaryKeySelective(szChooseforyou);
	}
	
	//查询集合
	@Override
	public List<SzChooseforyou> querySzChooseforyouList(SzChooseforyou szChooseforyou) {
		return szChooseforyouDao.querySzChooseforyouList(szChooseforyou);
	}
	
	//分页查询
	@Override
	public PageInfo<SzChooseforyou> getSzChooseforyouBySearch(AppPage<SzChooseforyou> page) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize()); // 设置分页信息
		List<SzChooseforyou> list = szChooseforyouDao.querySzChooseforyouList(page.getParam()); // 自动将分页信息与返回数据组装
		PageInfo<SzChooseforyou> pageInfo = new PageInfo<SzChooseforyou>(list);
		return pageInfo;
	}

	@Override
	public List<SzChooseforyou> querySzChooseforyouListExample(Example<SzChooseforyou> example) {
		return szChooseforyouDao.findAll(example);
	}
}