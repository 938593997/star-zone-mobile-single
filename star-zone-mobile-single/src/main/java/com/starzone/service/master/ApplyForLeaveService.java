/**
 * @filename:ApplyForLeaveService 2019年9月27日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.service.master;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import com.starzone.pojo.ApplyForLeave;
/**   
 * @Description:  请假申请——SERVICE
 * @Author:       qiu_hf   
 * @CreateDate:   2019年9月27日
 * @Version:      V1.0
 */
public interface ApplyForLeaveService {
	
	/**
	 * @explain 查询请假申请对象
	 * @param   对象参数：id
	 * @return  ApplyForLeave
	 * @author  qiu_hf
	 */
	public ApplyForLeave selectByPrimaryKey(String id);
	
	/**
	 * @explain 删除请假申请对象
	 * @param   对象参数：id
	 * @return  int
	 * @author  qiu_hf
	 */
	public int deleteByPrimaryKey(String id);
	
	/**
	 * @explain 添加请假申请对象
	 * @param   对象参数：ApplyForLeave
	 * @return  int
	 * @author  qiu_hf
	 */
	public int insertSelective(ApplyForLeave applyForLeave);
	
	/**
	 * @explain 修改请假申请对象
	 * @param   对象参数：ApplyForLeave
	 * @return  int
	 * @author  qiu_hf
	 */
	public int updateByPrimaryKeySelective(ApplyForLeave applyForLeave);
	
	/**
	 * @explain 查询请假申请集合
	 * @param   对象参数：ApplyForLeave
	 * @return  List<ApplyForLeave>
	 * @author  qiu_hf
	 */
	public List<ApplyForLeave> queryApplyForLeaveList(ApplyForLeave applyForLeave);
	
	/**
	 * @explain 分页查询请假申请
	 * @param   对象参数：ApplyForLeave
	 * @return  PageInfo<ApplyForLeave>
	 * @author  qiu_hf
	 */
	public PageInfo<ApplyForLeave> getApplyForLeaveBySearch(AppPage<ApplyForLeave> page);

	/**
	 * @explain 查询请假申请对象  <swagger GET请求>（通过processId）
	 * @param   对象参数：id
	 * @return  applyForLeave
	 * @author  qiu_hf
	 * @time    2019年9月27日
	 */
	public ApplyForLeave queryApplyInfoByProcessId(String processId);

	/**
	 * 通过流程实例id修改重新提交的数据
	 * @doc 说明
	 * @param applyForLeave
	 * @return
	 * @author qiu_hf
	 * @history 2019年9月30日 下午10:05:20 Create by 【qiu_hf】
	 */
	public int updateByProcessId(ApplyForLeave applyForLeave);
}