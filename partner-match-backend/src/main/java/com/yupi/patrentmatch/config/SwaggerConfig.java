package com.yupi.patrentmatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 访问地址
 * doc: 		http://localhost:8080/doc.html
 * Swagger: 	http://localhost:8080/swagger-ui.html
 **/
@Configuration        //配置类，项目启动的时候自动调用加载
@EnableOpenApi
public class SwaggerConfig {
    /**
     * 通过knife4j生成接口文档
     *
     * @return
     */
    @Bean
    public Docket docket1() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("用户中心接口文档")
                .version("2.0")
                .description("用户中心接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("管理端接口")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yupi.patrentmatch.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}

