
//장비실

package com.example.stock.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
open class AppConfig {

    @Bean
    // 괄호 안에 (builder: RestTemplateBuilder) 이거를 지우세요!
    open fun restTemplate(): RestTemplate {
        // [수정] 빌더 없이 직접 생성 ("그냥 새 거 하나 줘!")
        return RestTemplate()
    }
}
