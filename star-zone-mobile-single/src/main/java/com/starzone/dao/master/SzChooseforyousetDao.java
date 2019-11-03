/**
 * @filename:SzChooseforyousetDao 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.dao.master;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.starzone.pojo.SzChooseforyouset;

/**   
 * @Description:  我帮你选选项定义——DAO
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@Mapper
public interface SzChooseforyousetDao {
	
	public SzChooseforyouset selectByPrimaryKey(Integer id);
	
	public int deleteByPrimaryKey(Integer id);
	
	public int insertSelective(SzChooseforyouset szChooseforyouset);
	
	public int updateByPrimaryKeySelective(SzChooseforyouset szChooseforyouset);
	
	public List<SzChooseforyouset> querySzChooseforyousetList(SzChooseforyouset szChooseforyouset);

	public int deleteByChooseId(String chooseId);
}
