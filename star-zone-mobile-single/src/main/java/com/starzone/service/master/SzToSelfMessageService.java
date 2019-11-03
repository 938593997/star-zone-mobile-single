/**
 * @filename:SzToSelfMessageService 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.service.master;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.pojo.SzToSelfMessage;
/**   
 * @Description:  给自己的信息——SERVICE
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
public interface SzToSelfMessageService {
	
	/**
	 * @explain 查询给自己的信息对象
	 * @param   对象参数：id
	 * @return  SzToSelfMessage
	 * @author  qiu_hf
	 */
	public SzToSelfMessage selectByPrimaryKey(String id);
	
	/**
	 * @explain 删除给自己的信息对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 */
	public int deleteByPrimaryKey(String id);
	
	/**
	 * @explain 添加给自己的信息对象
	 * @param   对象参数：SzToSelfMessage
	 * @return  int
	 * @author  qiu_hf
	 */
	public int insertSelective(SzToSelfMessage szToSelfMessage);
	
	/**
	 * @explain 修改给自己的信息对象
	 * @param   对象参数：SzToSelfMessage
	 * @return  int
	 * @author  qiu_hf
	 */
	public int updateByPrimaryKeySelective(SzToSelfMessage szToSelfMessage);
	
	/**
	 * @explain 查询给自己的信息集合
	 * @param   对象参数：SzToSelfMessage
	 * @return  List<SzToSelfMessage>
	 * @author  qiu_hf
	 */
	public List<SzToSelfMessage> querySzToSelfMessageList(SzToSelfMessage szToSelfMessage);
	
	/**
	 * @explain 分页查询给自己的信息
	 * @param   对象参数：SzToSelfMessage
	 * @return  PageInfo<SzToSelfMessage>
	 * @author  qiu_hf
	 */
	public PageInfo<SzToSelfMessage> getSzToSelfMessageBySearch(AppPage<SzToSelfMessage> page);
}