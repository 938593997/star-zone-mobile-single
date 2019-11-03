package com.starzone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * 多数据源JPA配置事物
 * @doc 说明
 * @FileName EntityManagerFactoriesConfiguration.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年5月11日
 * @history 1.0.0.0 2019年5月11日 上午10:47:39 created by【qiu_hf】
 */
@Configuration
public class EntityManagerFactoriesConfiguration {
	
	@Autowired
    private MasterDataSourceConfig masterDataSourceConfig;

	@Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean emf() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(masterDataSourceConfig.masterDataSource());
        emf.setPackagesToScan(new String[]{"com.starzone.pojo"});
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return emf;
    }
}
