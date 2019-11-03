package com.starzone.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.starzone.pojo.User;
import com.starzone.service.master.UserService;

/**
 * 用户控制层
 * @doc 说明
 * @FileName UserRestController.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年4月13日
 * @history 1.0.0.0 2019年4月13日 上午11:53:05 created by【qiu_hf】
 */
@RestController
//@CrossOrigin(origins = {"http://127.0.0.1:8000", "null"})
@RequestMapping(value = "/api")
public class UserRestController {
	
	@Autowired
    private UserService userService;
 
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
    public boolean insert(@RequestBody User user) {
    	System.out.println("开始新增...");
        return userService.insert(user);
    }
    
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
    public boolean update(@RequestBody User user) {
    	System.out.println("开始更新...");
        return userService.update(user);
    }
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public boolean delete(@RequestBody User user)  {
    	System.out.println("开始删除...");
        return userService.delete(user);
    }
	
    @RequestMapping(value = "/findByUser", method = RequestMethod.GET)
    public List<User> findByUser(User user) {
    	System.out.println("开始查询...");
        return userService.findByListEntity(user);
    }
    
}
