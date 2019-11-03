/**
 * @filename:SzSpendDetailsService 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.service.master;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.pojo.SzSpendDetails;
/**   
 *  
 * @Description:  花费详情信息——SERVICE
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
public interface SzSpendDetailsService {
	
	/**
	 * @explain 查询花费详情信息对象
	 * @param   对象参数：id
	 * @return  SzSpendDetails
	 * @author  qiu_hf
	 */
	public SzSpendDetails selectByPrimaryKey(String id);
	
	/**
	 * @explain 删除花费详情信息对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 */
	public int deleteByPrimaryKey(String id);
	
	/**
	 * @explain 添加花费详情信息对象
	 * @param   对象参数：SzSpendDetails
	 * @return  int
	 * @author  qiu_hf
	 */
	public int insertSelective(SzSpendDetails szSpendDetails);
	
	/**
	 * @explain 修改花费详情信息对象
	 * @param   对象参数：SzSpendDetails
	 * @return  int
	 * @author  qiu_hf
	 */
	public int updateByPrimaryKeySelective(SzSpendDetails szSpendDetails);
	
	/**
	 * @explain 查询花费详情信息集合
	 * @param   对象参数：SzSpendDetails
	 * @return  List<SzSpendDetails>
	 * @author  qiu_hf
	 */
	public List<SzSpendDetails> querySzSpendDetailsList(SzSpendDetails szSpendDetails);
	
	/**
	 * @explain 分页查询花费详情信息
	 * @param   对象参数：SzSpendDetails
	 * @return  PageInfo<SzSpendDetails>
	 * @author  qiu_hf
	 */
	public PageInfo<SzSpendDetails> getSzSpendDetailsBySearch(AppPage<SzSpendDetails> page);

	/**
	 * 根据type删除消费详情 
	 * @param id 消费类型
	 * @return 删除结果（删除了几条）
	 */
	public int deleteByType(String id);
}