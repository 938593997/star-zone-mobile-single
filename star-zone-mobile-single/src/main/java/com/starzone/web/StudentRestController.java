package com.starzone.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.starzone.pojo.Student;
import com.starzone.service.cluster.StudentService;

/**
 * 用户控制层
 * @doc 说明
 * @FileName StudentRestController.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年4月13日
 * @history 1.0.0.0 2019年4月13日 上午11:53:55 created by【qiu_hf】
 */
@RestController
@RequestMapping(value = "/api")
//@CrossOrigin(origins = {"http://127.0.0.1:8000", "null"})
public class StudentRestController {
	
	@Autowired
    private StudentService service;
 
	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public boolean addStudent(@RequestBody Student student) {
    	System.out.println("开始新增...");
        return service.insert(student);
    }
    
	@RequestMapping(value = "/updateStudent", method = RequestMethod.PUT)
    public boolean updateStudent(@RequestBody Student student) {
    	System.out.println("开始更新...");
        return service.update(student);
    }
	
	@RequestMapping(value = "/deleteStudent", method = RequestMethod.DELETE)
    public boolean delete(@RequestBody Student student) {
    	System.out.println("开始删除...");
        return service.delete(student);
    }
	
    @RequestMapping(value = "/findByStudent", method = RequestMethod.GET)
    public List<Student> findByStudent(Student student) {
    	System.out.println("开始查询...");
        return service.findByListEntity(student);
    }
    
}
