package com.starzone.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 自定义配置文件
 * @doc 说明
 * @FileName IpConfig.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年4月13日
 * @history 1.0.0.0 2019年4月13日 上午11:56:04 created by【qiu_hf】
 */
@Component
@ConfigurationProperties(prefix = "ipconfig")
@PropertySource(value = "classpath:ipconfig.properties")
public class IpConfig {  
  
    @Value("${ipWhiteList}")
    private String ipWhiteList;

	/**  
	 * 获取ipWhiteList  
	 * @return  ipWhiteList  
	 */
	public String getIpWhiteList() {
		return ipWhiteList;
	}
    
}  
