/**
 * @filename:AskingServiceImpl 2019年4月13日
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
import com.starzone.pojo.Asking;
import com.starzone.dao.master.AskingDao;
import com.starzone.service.master.AskingService;

/**   
 *  
 * @Description:  问卷调查——SERVICEIMPL
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
@Service
public class AskingServiceImpl implements AskingService {
	
	@Autowired
	public AskingDao askingDao;
	
	//查询对象
	@Override
	public Asking selectByPrimaryKey(String id) {
		return askingDao.selectByPrimaryKey(id);
	}
	
	//删除对象
	@Override
	public int deleteByPrimaryKey(String id) {
		return askingDao.deleteByPrimaryKey(id);
	}
	
	//添加对象
	@Override
	public int insertSelective(Asking asking) {
		return askingDao.insertSelective(asking);
	}
	
	//修改对象
	@Override
	public int updateByPrimaryKeySelective(Asking asking) {
		return askingDao.updateByPrimaryKeySelective(asking);
	}
	
	//查询集合
	@Override
	public List<Asking> queryAskingList(Asking asking) {
		return askingDao.queryAskingList(asking);
	}
	
	//分页查询
	@Override
	public PageInfo<Asking> getAskingBySearch(AppPage<Asking> page) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize()); // 设置分页信息
		List<Asking> list = askingDao.queryAskingList(page.getParam()); // 自动将分页信息与返回数据组装
		PageInfo<Asking> pageInfo = new PageInfo<Asking>(list);
		return pageInfo;
	}
}