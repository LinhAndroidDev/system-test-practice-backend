package net.javaguides.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Cho tất cả API
                        .allowedOrigins("http://localhost:3000") // Cho phép React gọi
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các method được phép
                        .allowedHeaders("*") // Cho phép tất cả headers
                        .allowCredentials(true); // Cho phép gửi cookie/session
            }
        };
    }

}
