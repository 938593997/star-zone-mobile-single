/**
 * @filename:SzSpendTypeService 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.service.master;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.pojo.SzSpendDetails;
import com.starzone.pojo.SzSpendType;
/**   
 *  
 * @Description:  花费类型——SERVICE
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
public interface SzSpendTypeService {
	
	/**
	 * @explain 查询花费类型对象
	 * @param   对象参数：id
	 * @return  SzSpendType
	 * @author  qiu_hf
	 */
	public SzSpendType selectByPrimaryKey(String id);
	
	/**
	 * @explain 删除花费类型对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 */
	public int deleteByPrimaryKey(String id);
	
	/**
	 * @explain 添加花费类型对象
	 * @param   对象参数：SzSpendType
	 * @return  int
	 * @author  qiu_hf
	 */
	public int insertSelective(SzSpendType szSpendType);
	
	/**
	 * @explain 修改花费类型对象
	 * @param   对象参数：SzSpendType
	 * @return  int
	 * @author  qiu_hf
	 */
	public int updateByPrimaryKeySelective(SzSpendType szSpendType);
	
	/**
	 * @explain 查询花费类型集合
	 * @param   对象参数：SzSpendType
	 * @return  List<SzSpendType>
	 * @author  qiu_hf
	 */
	public List<SzSpendType> querySzSpendTypeList(SzSpendType szSpendType);
	
	/**
	 * @explain 分页查询花费类型
	 * @param   对象参数：SzSpendType
	 * @return  PageInfo<SzSpendType>
	 * @author  qiu_hf
	 */
	public PageInfo<SzSpendType> getSzSpendTypeBySearch(AppPage<SzSpendType> page);

	/**
	 * pie、Bar按年分组查询数据
	 * @param szSpendType 用户owner
	 * @return pie、Bar按年分组查询到的数据
	 */
	public List<SzSpendDetails> findPieAndBarDatasGroupByYear(SzSpendType szSpendType);

	/**
	 * pie、Bar按消费类型分组查的数据，可以按年过滤
	 * @param szSpendType 用户owner
	 * @return pie、Bar按消费类型分组查出的数据
	 */
	public List<SzSpendDetails> findPieAndBarDatasGroupByType(SzSpendType szSpendType);
}