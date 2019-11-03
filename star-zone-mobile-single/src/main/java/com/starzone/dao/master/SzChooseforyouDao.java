/**
 * @filename:SzChooseforyouDao 2019年4月13日
 * @project star-zone  V1.0
 * Copyright(c) 2019 qiu_hf Co. Ltd. 
 * All right reserved. 
 */
package com.starzone.dao.master;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.starzone.pojo.SzChooseforyou;

/**   
 *  
 * @Description:  我帮你选——DAO
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 *    
 */
@Mapper
@NoRepositoryBean
public interface SzChooseforyouDao extends JpaRepository<SzChooseforyou, String>, JpaSpecificationExecutor<SzChooseforyou>{
	
	public SzChooseforyou selectByPrimaryKey(String id);
	
	public int deleteByPrimaryKey(String id);
	
	public int insertSelective(SzChooseforyou szChooseforyou);
	
	public int updateByPrimaryKeySelective(SzChooseforyou szChooseforyou);
	
	public List<SzChooseforyou> querySzChooseforyouList(SzChooseforyou szChooseforyou);
}
