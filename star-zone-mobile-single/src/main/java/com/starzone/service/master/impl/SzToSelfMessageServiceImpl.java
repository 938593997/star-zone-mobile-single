/**
 * @filename:SzToSelfMessageServiceImpl 2019年4月13日
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
import com.starzone.pojo.SzToSelfMessage;
import com.starzone.dao.master.SzToSelfMessageDao;
import com.starzone.service.master.SzToSelfMessageService;

/**   
 * @Description:  给自己的信息——SERVICEIMPL
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@Service
public class SzToSelfMessageServiceImpl implements SzToSelfMessageService {
	
	@Autowired
	public SzToSelfMessageDao szToSelfMessageDao;
	
	//查询对象
	@Override
	public SzToSelfMessage selectByPrimaryKey(String id) {
		return szToSelfMessageDao.selectByPrimaryKey(id);
	}
	
	//删除对象
	@Override
	public int deleteByPrimaryKey(String id) {
		return szToSelfMessageDao.deleteByPrimaryKey(id);
	}
	
	//添加对象
	@Override
	public int insertSelective(SzToSelfMessage szToSelfMessage) {
		return szToSelfMessageDao.insertSelective(szToSelfMessage);
	}
	
	//修改对象
	@Override
	public int updateByPrimaryKeySelective(SzToSelfMessage szToSelfMessage) {
		return szToSelfMessageDao.updateByPrimaryKeySelective(szToSelfMessage);
	}
	
	//查询集合
	@Override
	public List<SzToSelfMessage> querySzToSelfMessageList(SzToSelfMessage szToSelfMessage) {
		return szToSelfMessageDao.querySzToSelfMessageList(szToSelfMessage);
	}
	
	//分页查询
	@Override
	public PageInfo<SzToSelfMessage> getSzToSelfMessageBySearch(AppPage<SzToSelfMessage> page) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<SzToSelfMessage> list = szToSelfMessageDao.querySzToSelfMessageList(page.getParam());
		PageInfo<SzToSelfMessage> pageInfo = new PageInfo<SzToSelfMessage>(list);
		return pageInfo;
	}
}