package com.example.stock.kis

import org.springframework.boot.context.properties.ConfigurationProperties

// [변경] @Configuration 제거 (StockApplication에서 등록하므로 중복 방지)
@ConfigurationProperties(prefix = "kis")
class KisConfig {
    lateinit var appKey: String
    lateinit var appSecret: String
    lateinit var baseUrl: String
}