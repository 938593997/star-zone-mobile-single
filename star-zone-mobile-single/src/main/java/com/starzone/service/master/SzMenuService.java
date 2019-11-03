/**
 * @filename:SzMenuService 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.service.master;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.pojo.SzMenu;
/**   
 *  
 * @Description:  菜单——SERVICE
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
public interface SzMenuService {
	
	/**
	 * @explain 查询菜单对象
	 * @param   对象参数：id
	 * @return  SzMenu
	 * @author  qiu_hf
	 */
	public SzMenu selectByPrimaryKey(String id);
	
	/**
	 * @explain 删除菜单对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 */
	public int deleteByPrimaryKey(String id);
	
	/**
	 * @explain 添加菜单对象
	 * @param   对象参数：SzMenu
	 * @return  int
	 * @author  qiu_hf
	 */
	public int insertSelective(SzMenu szMenu);
	
	/**
	 * @explain 修改菜单对象
	 * @param   对象参数：SzMenu
	 * @return  int
	 * @author  qiu_hf
	 */
	public int updateByPrimaryKeySelective(SzMenu szMenu);
	
	/**
	 * @explain 查询菜单集合
	 * @param   对象参数：SzMenu
	 * @return  List<SzMenu>
	 * @author  qiu_hf
	 */
	public List<SzMenu> querySzMenuList(SzMenu szMenu);
	
	/**
	 * @explain 分页查询菜单
	 * @param   对象参数：SzMenu
	 * @return  PageInfo<SzMenu>
	 * @author  qiu_hf
	 */
	public PageInfo<SzMenu> getSzMenuBySearch(AppPage<SzMenu> page);
}