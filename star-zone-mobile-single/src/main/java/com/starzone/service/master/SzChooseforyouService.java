/**
 * @filename:SzChooseforyouService 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.service.master;

import java.util.List;

import org.springframework.data.domain.Example;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.pojo.SzChooseforyou;
/**   
 *  
 * @Description:  我帮你选——SERVICE
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
public interface SzChooseforyouService {
	
	/**
	 * @explain 查询我帮你选对象
	 * @param   对象参数：id
	 * @return  SzChooseforyou
	 * @author  qiu_hf
	 */
	public SzChooseforyou selectByPrimaryKey(String id);
	
	/**
	 * @explain 删除我帮你选对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 */
	public int deleteByPrimaryKey(String id);
	
	/**
	 * @explain 添加我帮你选对象
	 * @param   对象参数：SzChooseforyou
	 * @return  int
	 * @author  qiu_hf
	 */
	public int insertSelective(SzChooseforyou szChooseforyou);
	
	/**
	 * @explain 修改我帮你选对象
	 * @param   对象参数：SzChooseforyou
	 * @return  int
	 * @author  qiu_hf
	 */
	public int updateByPrimaryKeySelective(SzChooseforyou szChooseforyou);
	
	/**
	 * @explain 查询我帮你选集合
	 * @param   对象参数：SzChooseforyou
	 * @return  List<SzChooseforyou>
	 * @author  qiu_hf
	 */
	public List<SzChooseforyou> querySzChooseforyouList(SzChooseforyou szChooseforyou);
	
	/**
	 * @explain 分页查询我帮你选
	 * @param   对象参数：SzChooseforyou
	 * @return  PageInfo<SzChooseforyou>
	 * @author  qiu_hf
	 */
	public PageInfo<SzChooseforyou> getSzChooseforyouBySearch(AppPage<SzChooseforyou> page);

	/**
	 * Example的方式查询
	 * @doc 说明
	 * @param example 过滤条件
	 * @return 添加的选择列表
	 * @author qiu_hf
	 * @history 2019年5月12日 下午5:58:59 Create by 【qiu_hf】
	 */
	public List<SzChooseforyou> querySzChooseforyouListExample(Example<SzChooseforyou> example);
}