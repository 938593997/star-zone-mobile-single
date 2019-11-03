/**
 * @filename:AskingService 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.service.master;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.pojo.Asking;
/**   
 * @Description:  问卷调查——SERVICE
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
public interface AskingService {
	
	/**
	 * @explain 查询问卷调查对象
	 * @param   对象参数：id
	 * @return  Asking
	 * @author  qiu_hf
	 */
	public Asking selectByPrimaryKey(String id);
	
	/**
	 * @explain 删除问卷调查对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 */
	public int deleteByPrimaryKey(String id);
	
	/**
	 * @explain 添加问卷调查对象
	 * @param   对象参数：Asking
	 * @return  int
	 * @author  qiu_hf
	 */
	public int insertSelective(Asking asking);
	
	/**
	 * @explain 修改问卷调查对象
	 * @param   对象参数：Asking
	 * @return  int
	 * @author  qiu_hf
	 */
	public int updateByPrimaryKeySelective(Asking asking);
	
	/**
	 * @explain 查询问卷调查集合
	 * @param   对象参数：Asking
	 * @return  List<Asking>
	 * @author  qiu_hf
	 */
	public List<Asking> queryAskingList(Asking asking);
	
	/**
	 * @explain 分页查询问卷调查
	 * @param   对象参数：Asking
	 * @return  PageInfo<Asking>
	 * @author  qiu_hf
	 */
	public PageInfo<Asking> getAskingBySearch(AppPage<Asking> page);
}