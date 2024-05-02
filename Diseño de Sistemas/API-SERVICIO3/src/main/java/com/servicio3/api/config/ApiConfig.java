package com.servicio3.api.config;

import com.servicio3.api.domain.rankings.GeneradorRanking;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {
    @Bean
    public GeneradorRanking crearGeneradorRanking() {
        return new GeneradorRanking();
    }
}
