package com.example.stock.kis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties


// 금고지기(KisConfig)


@EnableConfigurationProperties
@ConfigurationProperties(prefix = "kis")
class KisConfig {
    lateinit var appKey: String
    lateinit var appSecret: String
    lateinit var baseUrl: String
}