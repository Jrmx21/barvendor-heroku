package com.davidruiz.barvendor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Configura los endpoints de tu API que deseas permitir el acceso CORS
                .allowedOrigins("http://localhost:8100") // Permite el acceso desde este origen
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Permite los métodos HTTP especificados
                .allowCredentials(true); // Permite el envío de credenciales (si es necesario)
                
    }
}
