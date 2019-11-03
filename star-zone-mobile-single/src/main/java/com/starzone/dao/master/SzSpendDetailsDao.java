/**
 * @filename:SzSpendDetailsDao 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.dao.master;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.starzone.pojo.SzSpendDetails;
import com.starzone.pojo.SzSpendType;

/**   
 *  
 * @Description:  花费详情信息——DAO
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
@Mapper
public interface SzSpendDetailsDao {
	
	public SzSpendDetails selectByPrimaryKey(String id);
	
	public int deleteByPrimaryKey(String id);
	
	public int insertSelective(SzSpendDetails szSpendDetails);
	
	public int updateByPrimaryKeySelective(SzSpendDetails szSpendDetails);
	
	public List<SzSpendDetails> querySzSpendDetailsList(SzSpendDetails szSpendDetails);

	public int deleteByType(String type);

	public List<SzSpendDetails> findPieAndBarDatasGroupByYear(SzSpendType szSpendType);

	public List<SzSpendDetails> findPieAndBarDatasGroupByType(SzSpendType szSpendType);
}
