/**
 * @filename:${entityName}Dao ${createTime}
 * @project ${project}  ${version}
 * Copyright(c) 2019 ${author} Co. Ltd. 
 * All right reserved. 
 */
package ${daoUrl};

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import ${entityUrl}.${entityName};

/**   
 * @Description:  ${entityComment}——DAO
 * @Author:       ${author}   
 * @CreateDate:   ${createTime}
 * @Version:      ${version}
 */
@Mapper
@NoRepositoryBean // 加这个注解来防止存储库接口被选为候选者，最终最终成为存储库bean实例（不然会以jap的规则来校验自定义的接口）
public interface ${entityName}Dao extends JpaRepository<${entityName}, ${idType}>, JpaSpecificationExecutor<${entityName}>{
	
	public ${entityName} selectByPrimaryKey(${idType} id);
	
	public int deleteByPrimaryKey(${idType} id);
	
	public int insertSelective(${entityName} ${objectName});
	
	public int updateByPrimaryKeySelective(${entityName} ${objectName});
	
	public List<${entityName}> query${entityName}List(${entityName} ${objectName});
}
	