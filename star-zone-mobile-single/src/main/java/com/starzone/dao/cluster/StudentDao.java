package com.starzone.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import com.starzone.dao.BaseDao;
import com.starzone.pojo.Student;

/**
 * StudentDao
 * @doc 说明
 * @FileName StudentDao.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年4月13日
 * @history 1.0.0.0 2019年4月13日 上午11:56:44 created by【qiu_hf】
 */
@Mapper
public interface StudentDao extends BaseDao<Student>{

}
