package com.example.stock.kis

import com.fasterxml.jackson.annotation.JsonProperty

// 통역사(KisDto)

// 한투 현재가 API 응답 구조
data class KisPriceResponse(
    @JsonProperty("output")
    val output: KisOutput?
)

data class KisOutput(
    @JsonProperty("stck_prpr") // 주식 현재가 키값
    val price: String
)

// 한투 토큰 발급 응답 구조
data class KisTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String
)