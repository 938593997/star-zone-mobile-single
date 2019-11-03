package com.starzone.dao.master;

import org.apache.ibatis.annotations.Mapper;

import com.starzone.dao.BaseDao;
import com.starzone.pojo.User;

/**
 * UserDao
 * @doc 说明
 * @FileName UserDao.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年4月13日
 * @history 1.0.0.0 2019年4月13日 上午11:57:03 created by【qiu_hf】
 */
@Mapper
public interface UserDao extends BaseDao<User>{
    
}
