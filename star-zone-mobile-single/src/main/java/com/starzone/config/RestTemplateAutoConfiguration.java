package com.starzone.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 配置后，通过注入RestTemplate来获取在zuulservice工程注册过的工程里面提供的方法
 * @doc 说明
 * @FileName RestTemplateAutoConfiguration.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年9月19日
 * @history 1.0.0.0 2019年9月19日 下午9:24:44 created by【qiu_hf】
 */
@Configuration
public class RestTemplateAutoConfiguration {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
