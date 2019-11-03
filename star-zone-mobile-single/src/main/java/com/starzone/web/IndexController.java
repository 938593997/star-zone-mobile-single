package com.starzone.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页跳转
 * @doc 说明
 * @FileName IndexController.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年9月13日
 * @history 1.0.0.0 2019年9月13日 下午10:09:51 created by【qiu_hf】
 */
@Controller
@RequestMapping
public class IndexController {
	
	@RequestMapping("/")
    public String toIndexPage() {
		return "index";
    }
}
