/**
 * @filename:AskingDao 2019年4月13日
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
import com.starzone.pojo.Asking;

/**   
 * @Description:  问卷调查——DAO
 * @Author:       qiu_hf   
 * @CreateDate:   2019年4月13日
 * @Version:      V1.0
 */
@Mapper
@NoRepositoryBean // 加这个注解来防止存储库接口被选为候选者，最终最终成为存储库bean实例（不然会以jap的规则来校验自定义的接口）
public interface AskingDao extends JpaRepository<Asking, String>, JpaSpecificationExecutor<Asking>{
	
	public Asking selectByPrimaryKey(String id);
	
	public int deleteByPrimaryKey(String id);
	
	public int insertSelective(Asking asking);
	
	public int updateByPrimaryKeySelective(Asking asking);
	
	public List<Asking> queryAskingList(Asking asking);
}
