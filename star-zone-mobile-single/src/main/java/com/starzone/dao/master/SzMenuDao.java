/**
 * @filename:SzMenuDao 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.dao.master;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.starzone.pojo.SzMenu;

/**   
 *  
 * @Description:  菜单——DAO
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
@Mapper
public interface SzMenuDao {
	
	public SzMenu selectByPrimaryKey(String id);
	
	public int deleteByPrimaryKey(String id);
	
	public int insertSelective(SzMenu szMenu);
	
	public int updateByPrimaryKeySelective(SzMenu szMenu);
	
	public List<SzMenu> querySzMenuList(SzMenu szMenu);
}
