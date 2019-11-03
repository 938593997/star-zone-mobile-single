/**
 * @filename:SzChooseresultsDao 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.dao.master;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.starzone.pojo.SzChooseresults;

/**   
 * @Description:  选择结果——DAO
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@Mapper
public interface SzChooseresultsDao {
	
	public SzChooseresults selectByPrimaryKey(String id);
	
	public int deleteByPrimaryKey(String id);
	
	public int insertSelective(SzChooseresults szChooseresults);
	
	public int updateByPrimaryKeySelective(SzChooseresults szChooseresults);
	
	public List<SzChooseresults> querySzChooseresultsList(SzChooseresults szChooseresults);

	public int deleteByChooseId(String chooseId);
}
