/**
 * @filename:SzMenuServiceImpl 2019年4月13日
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
import com.starzone.pojo.SzMenu;
import com.starzone.dao.master.SzMenuDao;
import com.starzone.service.master.SzMenuService;

/**   
 *  
 * @Description:  菜单——SERVICEIMPL
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
@Service
public class SzMenuServiceImpl implements SzMenuService {
	
	@Autowired
	public SzMenuDao szMenuDao;
	
	//查询对象
	@Override
	public SzMenu selectByPrimaryKey(String id) {
		return szMenuDao.selectByPrimaryKey(id);
	}
	
	//删除对象
	@Override
	public int deleteByPrimaryKey(String id) {
		return szMenuDao.deleteByPrimaryKey(id);
	}
	
	//添加对象
	@Override
	public int insertSelective(SzMenu szMenu) {
		return szMenuDao.insertSelective(szMenu);
	}
	
	//修改对象
	@Override
	public int updateByPrimaryKeySelective(SzMenu szMenu) {
		return szMenuDao.updateByPrimaryKeySelective(szMenu);
	}
	
	//查询集合
	@Override
	public List<SzMenu> querySzMenuList(SzMenu szMenu) {
		return szMenuDao.querySzMenuList(szMenu);
	}
	
	//分页查询
	@Override
	public PageInfo<SzMenu> getSzMenuBySearch(AppPage<SzMenu> page) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize()); // 设置分页信息
		List<SzMenu> list = szMenuDao.querySzMenuList(page.getParam()); // 自动将分页信息与返回数据组装
		PageInfo<SzMenu> pageInfo = new PageInfo<SzMenu>(list);
		return pageInfo;
	}
}