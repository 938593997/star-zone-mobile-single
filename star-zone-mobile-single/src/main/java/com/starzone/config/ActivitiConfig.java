package com.starzone.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * 配置Activiti字体
 * @doc 说明： 这里设置了宋体，本地开发是没问题的，因为本地开发Windows系统带有这个字体，
 * 			要是部署到阿里云服务器，流程跟踪出现乱码，说明阿里云服务器没有宋体，需要安装字体。
 * @FileName ActivitiConfig.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年11月15日
 * @history 1.0.0.0 2019年11月15日 下午8:16:27 created by【qiu_hf】
 */
@Configuration
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {
    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        
        processEngineConfiguration.setDbIdentityUsed(false);
        // 这段代码表示是否使用activiti自带用户组织表，如果是，这里为true，如果不是，这里为false
        processEngineConfiguration.setDatabaseSchemaUpdate("true");
    }
    
}
