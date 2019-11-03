package com.starzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;

/**
 * 项目启动类（star-zone-mobile-single）
 * @doc 说明
 * @FileName App.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年4月13日
 * @history 1.0.0.0 2019年4月13日 上午11:51:52 created by【qiu_hf】
 */
@SpringBootApplication(exclude = {PageHelperAutoConfiguration.class}) // pagehelper-spring-boot-starter这个依赖，提供了自动配置分页插件的功能,这里要排除这个自动配置，用自己在多数据源中配置的分页
public class App extends SpringBootServletInitializer {
	
    public static void main( String[] args ){
    	// 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
		SpringApplication.run(App.class, args);
		System.err.println("程序正在运行...");
    }
    
    @Override // 为了打包springboot项目
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(App.class);
    }
}
