/**
 * @filename:SzChooseforyousetService 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.service.master;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.pojo.SzChooseforyouset;
/**   
 *  
 * @Description:  我帮你选选项定义——SERVICE
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
public interface SzChooseforyousetService {
	
	/**
	 * @explain 查询我帮你选选项定义对象
	 * @param   对象参数：id
	 * @return  SzChooseforyouset
	 * @author  qiu_hf
	 */
	public SzChooseforyouset selectByPrimaryKey(Integer id);
	
	/**
	 * @explain 删除我帮你选选项定义对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 */
	public int deleteByPrimaryKey(Integer id);
	
	/**
	 * @explain 添加我帮你选选项定义对象
	 * @param   对象参数：SzChooseforyouset
	 * @return  int
	 * @author  qiu_hf
	 */
	public int insertSelective(SzChooseforyouset szChooseforyouset);
	
	/**
	 * @explain 修改我帮你选选项定义对象
	 * @param   对象参数：SzChooseforyouset
	 * @return  int
	 * @author  qiu_hf
	 */
	public int updateByPrimaryKeySelective(SzChooseforyouset szChooseforyouset);
	
	/**
	 * @explain 查询我帮你选选项定义集合
	 * @param   对象参数：SzChooseforyouset
	 * @return  List<SzChooseforyouset>
	 * @author  qiu_hf
	 */
	public List<SzChooseforyouset> querySzChooseforyousetList(SzChooseforyouset szChooseforyouset);
	
	/**
	 * @explain 分页查询我帮你选选项定义
	 * @param   对象参数：SzChooseforyouset
	 * @return  PageInfo<SzChooseforyouset>
	 * @author  qiu_hf
	 */
	public PageInfo<SzChooseforyouset> getSzChooseforyousetBySearch(AppPage<SzChooseforyouset> page);

    /**
     * 通过选择ID删除选项（删除选择下的选项）
     * @param chooseId 选择id
     * @return 删除结果
     */
	public int deleteByChooseId(String chooseId);
}