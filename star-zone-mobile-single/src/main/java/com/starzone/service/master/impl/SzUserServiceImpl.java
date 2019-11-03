/**
 * @filename:SzUserServiceImpl 2019年4月13日
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
import com.starzone.pojo.SzUser;
import com.starzone.dao.master.SzUserDao;
import com.starzone.service.master.SzUserService;

/**   
 * @Description:  star-zone用户——SERVICEIMPL
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@Service
public class SzUserServiceImpl implements SzUserService {
	
	@Autowired
	public SzUserDao szUserDao;
	
	//查询对象
	@Override
	public SzUser selectByPrimaryKey(String id) {
		return szUserDao.selectByPrimaryKey(id);
	}
	
	//删除对象
	@Override
	public int deleteByPrimaryKey(String id) {
		return szUserDao.deleteByPrimaryKey(id);
	}
	
	//添加对象
	@Override
	public int insertSelective(SzUser szUser) {
		return szUserDao.insertSelective(szUser);
	}
	
	//修改对象
	@Override
	public int updateByPrimaryKeySelective(SzUser szUser) {
		return szUserDao.updateByPrimaryKeySelective(szUser);
	}
	
	//查询集合
	@Override
	public List<SzUser> querySzUserList(SzUser szUser) {
		return szUserDao.querySzUserList(szUser);
	}
	
	//分页查询
	@Override
	public PageInfo<SzUser> getSzUserBySearch(AppPage<SzUser> page) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize());// 设置分页
		List<SzUser> list = szUserDao.querySzUserList(page.getParam());
		PageInfo<SzUser> pageInfo = new PageInfo<SzUser>(list);
		return pageInfo;
	}

	// 通过用户名 密钥查询用户
	@Override
	public SzUser CheckName(String name) {
		return szUserDao.CheckName(name);
	}

	// 用户登入信息查询
	@Override
	public SzUser loginCheck(SzUser szUser) {
		return szUserDao.loginCheck(szUser);
	}
}