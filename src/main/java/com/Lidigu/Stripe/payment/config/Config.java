package com.Lidigu.Stripe.payment.config;

import com.Lidigu.Stripe.payment.repository.configRepo;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class Config implements configRepo {
    @Override
    public void addCorsMapping(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("POST");
    }
}
