package com.YH.yeohaenghama.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "https://web-yeohaenghama-frontend-dc9c2nlsmwen6i.sel5.cloudtype.app",
                        "http://127.0.0.1",
                        "http://localhost:8080",
                        "http://10.0.2.2",
                        "http://172.30.1.62"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
