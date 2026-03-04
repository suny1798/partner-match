package com.yupi.patrentmatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // 1. 允许携带 Cookie (关键点 1)
        config.setAllowCredentials(true);

        // 2. 允许的前端地址 (关键点 2)
        // 注意：当 setAllowCredentials(true) 时，这里不能写 "*"，必须写具体的 IP 或域名
        config.addAllowedOrigin("http://localhost:5173"); 
        
        // 3. 允许所有请求头
        config.addAllowedHeader("*");

        // 4. 允许所有请求方法 (GET, POST, PUT, DELETE 等)
        config.addAllowedMethod("*");

        // 5. 设置有效时长（秒）
        config.setMaxAge(3600L);

        // 6. 应用配置
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}
