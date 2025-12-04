package com.example.stock.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {

    // [변경] Builder 대신 직접 생성 (오류 해결 및 단순화)
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}
