package com.starzone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 配置swagger2信息
 * @doc 说明:
		    paramType：表示参数放在哪个地方
		    header-->请求参数的获取：@RequestHeader(代码中接收注解)
		    query-->请求参数的获取：@RequestParam(代码中接收注解)
		    path（用于restful接口）-->请求参数的获取：@PathVariable(代码中接收注解)
		    body-->请求参数的获取：@RequestBody(代码中接收注解)
		    form（不常用）
 * @FileName Swagger2Config.java
 * @author qiu_hf
 * @version 1.0.0
 * @since 2019年4月13日
 * @history 1.0.0.0 2019年4月13日 下午5:55:49 created by【qiu_hf】
 */
@Configuration      //让Spring来加载该类配置
@EnableWebMvc       //启用Mvc，非springboot框架需要引入注解@EnableWebMvc
@EnableSwagger2     //启用Swagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                //扫描指定包中的swagger注解
//                .apis(RequestHandlerSelectors.basePackage("com.starzone.web"))
                //扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    @SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("star-zone-mobile RESTful APIs")
                .description("基础平台 RESTful 风格的接口文档，内容详细，极大的减少了前后端的沟通成本，同时确保代码与文档保持高度一致，极大的减少维护文档的时间。")
                .termsOfServiceUrl("https://www.baidu.com")
                .contact("qiu_hf")
                .version("1.0.0")
                .build();
    }
}
