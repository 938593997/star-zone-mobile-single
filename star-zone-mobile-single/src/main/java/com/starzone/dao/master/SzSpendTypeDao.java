/**
 * @filename:SzSpendTypeDao 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.dao.master;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.starzone.pojo.SzSpendType;

/**   
 *  
 * @Description:  花费类型——DAO
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
@Mapper
public interface SzSpendTypeDao {
	
	public SzSpendType selectByPrimaryKey(String id);
	
	public int deleteByPrimaryKey(String id);
	
	public int insertSelective(SzSpendType szSpendType);
	
	public int updateByPrimaryKeySelective(SzSpendType szSpendType);
	
	public List<SzSpendType> querySzSpendTypeList(SzSpendType szSpendType);
}
