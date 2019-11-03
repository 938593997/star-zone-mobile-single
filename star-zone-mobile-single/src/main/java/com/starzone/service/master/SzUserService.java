/**
 * @filename:SzUserService 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.service.master;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.pojo.SzUser;
/**   
 *  
 * @Description:  star-zone用户——SERVICE
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
public interface SzUserService {
	
	/**
	 * @explain 查询star-zone用户对象
	 * @param   对象参数：id
	 * @return  SzUser
	 * @author  qiu_hf
	 */
	public SzUser selectByPrimaryKey(String id);
	
	/**
	 * @explain 删除star-zone用户对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 */
	public int deleteByPrimaryKey(String id);
	
	/**
	 * @explain 添加star-zone用户对象
	 * @param   对象参数：SzUser
	 * @return  int
	 * @author  qiu_hf
	 */
	public int insertSelective(SzUser szUser);
	
	/**
	 * @explain 修改star-zone用户对象
	 * @param   对象参数：SzUser
	 * @return  int
	 * @author  qiu_hf
	 */
	public int updateByPrimaryKeySelective(SzUser szUser);
	
	/**
	 * @explain 查询star-zone用户集合
	 * @param   对象参数：SzUser
	 * @return  List<SzUser>
	 * @author  qiu_hf
	 */
	public List<SzUser> querySzUserList(SzUser szUser);
	
	/**
	 * @explain 分页查询star-zone用户
	 * @param   对象参数：SzUser
	 * @return  PageInfo<SzUser>
	 * @author  qiu_hf
	 */
	public PageInfo<SzUser> getSzUserBySearch(AppPage<SzUser> page);

	/**
	 * 根据加密后的用户名查询用户
	 * @doc 说明
	 * @param name 用户名
	 * @return 用户信息
	 * @author qiu_hf
	 * @history 2019年4月21日 下午1:58:10 Create by 【qiu_hf】
	 */
	public SzUser CheckName(String name);

	/**
	 * 用户登入
	 * @doc 说明
	 * @param szUser 加密后的用户名、密钥。密码
	 * @return 用户信息
	 * @author qiu_hf
	 * @history 2019年4月21日 下午3:29:06 Create by 【qiu_hf】
	 */
	public SzUser loginCheck(SzUser szUser);
	
}