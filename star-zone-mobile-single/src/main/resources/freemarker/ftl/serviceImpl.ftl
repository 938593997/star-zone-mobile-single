/**
 * @filename:${entityName}ServiceImpl ${createTime}
 * @project ${project}  ${version}
 * Copyright(c) 2019 ${author} Co. Ltd. 
 * All right reserved. 
 */
package ${serviceImplUrl};

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.starzone.utils.AppPage;
import ${entityUrl}.${entityName};
import ${daoUrl}.${entityName}Dao;
import ${serviceUrl}.${entityName}Service;

/**   
 * @Description:  ${entityComment}——SERVICEIMPL
 * @Author:       ${author}   
 * @CreateDate:   ${createTime}
 * @Version:      ${version}
 */
@Service
public class ${entityName}ServiceImpl implements ${entityName}Service {
	
	@Autowired
	public ${entityName}Dao ${objectName}Dao;
	
	//查询对象
	@Override
	public ${entityName} selectByPrimaryKey(${idType} id) {
		return ${objectName}Dao.selectByPrimaryKey(id);
	}
	
	//删除对象
	@Override
	public int deleteByPrimaryKey(${idType} id) {
		return ${objectName}Dao.deleteByPrimaryKey(id);
	}
	
	//添加对象
	@Override
	public int insertSelective(${entityName} ${objectName}) {
		return ${objectName}Dao.insertSelective(${objectName});
	}
	
	//修改对象
	@Override
	public int updateByPrimaryKeySelective(${entityName} ${objectName}) {
		return ${objectName}Dao.updateByPrimaryKeySelective(${objectName});
	}
	
	//查询集合
	@Override
	public List<${entityName}> query${entityName}List(${entityName} ${objectName}) {
		return ${objectName}Dao.query${entityName}List(${objectName});
	}
	
	//分页查询
	@Override
	public PageInfo<${entityName}> get${entityName}BySearch(AppPage<${entityName}> page) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize()); // 设置分页信息
		List<${entityName}> list = ${objectName}Dao.query${entityName}List(page.getParam()); // 自动将分页信息与返回数据组装
		PageInfo<${entityName}> pageInfo = new PageInfo<${entityName}>(list);
		return pageInfo;
	}
}