/**
 * @filename:SzChooseresultsService 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.service.master;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.pojo.SzChooseresults;
/**   
 *  
 * @Description:  选择结果——SERVICE
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
public interface SzChooseresultsService {
	
	/**
	 * @explain 查询选择结果对象
	 * @param   对象参数：id
	 * @return  SzChooseresults
	 * @author  qiu_hf
	 */
	public SzChooseresults selectByPrimaryKey(String id);
	
	/**
	 * @explain 删除选择结果对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 */
	public int deleteByPrimaryKey(String id);
	
	/**
	 * @explain 添加选择结果对象
	 * @param   对象参数：SzChooseresults
	 * @return  int
	 * @author  qiu_hf
	 */
	public int insertSelective(SzChooseresults szChooseresults);
	
	/**
	 * @explain 修改选择结果对象
	 * @param   对象参数：SzChooseresults
	 * @return  int
	 * @author  qiu_hf
	 */
	public int updateByPrimaryKeySelective(SzChooseresults szChooseresults);
	
	/**
	 * @explain 查询选择结果集合
	 * @param   对象参数：SzChooseresults
	 * @return  List<SzChooseresults>
	 * @author  qiu_hf
	 */
	public List<SzChooseresults> querySzChooseresultsList(SzChooseresults szChooseresults);
	
	/**
	 * @explain 分页查询选择结果
	 * @param   对象参数：SzChooseresults
	 * @return  PageInfo<SzChooseresults>
	 * @author  qiu_hf
	 */
	public PageInfo<SzChooseresults> getSzChooseresultsBySearch(AppPage<SzChooseresults> page);

	/**
	 * 通过chooseId删除选择结果
	 * @param id 选择信息id
	 * @return 删除结果
	 */
	public int deleteByChooseId(String id);
}