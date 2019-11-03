/**
 * @filename:ApplyForLeaveServiceImpl 2019年9月27日
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
import com.starzone.pojo.ApplyForLeave;
import com.starzone.dao.master.ApplyForLeaveDao;
import com.starzone.service.master.ApplyForLeaveService;

/**   
 * @Description:  请假申请——SERVICEIMPL
 * @Author:       qiu_hf   
 * @CreateDate:   2019年9月27日
 * @Version:      V1.0
 */
@Service
public class ApplyForLeaveServiceImpl implements ApplyForLeaveService {
	
	@Autowired
	public ApplyForLeaveDao applyForLeaveDao;
	
	//查询对象
	@Override
	public ApplyForLeave selectByPrimaryKey(String id) {
		return applyForLeaveDao.selectByPrimaryKey(id);
	}
	
	//删除对象
	@Override
	public int deleteByPrimaryKey(String id) {
		return applyForLeaveDao.deleteByPrimaryKey(id);
	}
	
	//添加对象
	@Override
	public int insertSelective(ApplyForLeave applyForLeave) {
		return applyForLeaveDao.insertSelective(applyForLeave);
	}
	
	//修改对象
	@Override
	public int updateByPrimaryKeySelective(ApplyForLeave applyForLeave) {
		return applyForLeaveDao.updateByPrimaryKeySelective(applyForLeave);
	}
	
	//查询集合
	@Override
	public List<ApplyForLeave> queryApplyForLeaveList(ApplyForLeave applyForLeave) {
		return applyForLeaveDao.queryApplyForLeaveList(applyForLeave);
	}
	
	//分页查询
	@Override
	public PageInfo<ApplyForLeave> getApplyForLeaveBySearch(AppPage<ApplyForLeave> page) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize()); // 设置分页信息
		List<ApplyForLeave> list = applyForLeaveDao.queryApplyForLeaveList(page.getParam()); // 自动将分页信息与返回数据组装
		PageInfo<ApplyForLeave> pageInfo = new PageInfo<ApplyForLeave>(list);
		return pageInfo;
	}

	@Override
	public ApplyForLeave queryApplyInfoByProcessId(String processId) {
		return applyForLeaveDao.queryApplyInfoByProcessId(processId);
	}

	@Override
	public int updateByProcessId(ApplyForLeave applyForLeave) {
		return applyForLeaveDao.updateByProcessId(applyForLeave);
	}
}