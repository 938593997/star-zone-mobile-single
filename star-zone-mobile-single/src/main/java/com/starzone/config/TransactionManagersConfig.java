package com.starzone.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 多数据源JPA配置事物
 * @doc 说明
 * @FileName TransactionManagersConfig.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年5月11日
 * @history 1.0.0.0 2019年5月11日 上午10:50:34 created by【qiu_hf】
 */
@Configuration
@EnableTransactionManagement
public class TransactionManagersConfig {

	@Autowired
    EntityManagerFactory emf;
	
    @Autowired
    private MasterDataSourceConfig masterDataSourceConfig;

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(emf);
        tm.setDataSource(masterDataSourceConfig.masterDataSource());
        return tm;
    }
}
