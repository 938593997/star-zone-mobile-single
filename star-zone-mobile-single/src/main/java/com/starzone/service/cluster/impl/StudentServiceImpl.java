package com.starzone.service.cluster.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starzone.dao.BaseDao;
import com.starzone.dao.cluster.StudentDao;
import com.starzone.pojo.Student;
import com.starzone.service.cluster.StudentService;
import com.starzone.service.impl.BaseServiceImpl;

/**
 * 用户操作实现类 
 * @doc 说明
 * @FileName StudentServiceImpl.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年4月13日
 * @history 1.0.0.0 2019年4月13日 下午12:02:23 created by【qiu_hf】
 */
@Service
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {
	
	@Autowired
	private StudentDao studentDao;
	
	@Override
	protected BaseDao<Student> getMapper() {
		return this.studentDao;
	}
	
}
