package com.example.stock.kis

import com.fasterxml.jackson.annotation.JsonProperty

// [기존] 현재가용 (건드리지 마세요)
data class KisPriceResponse(
    @JsonProperty("output")
    val output: KisOutput?
)

data class KisOutput(
    @JsonProperty("stck_prpr")
    val price: String
)

// [수정] 일봉 차트용 그릇 (이름 변경: output1 -> output)
data class KisDailyPriceResponse(
    // ▼▼▼ [중요] 여기를 output1 에서 output 으로 바꿔야 합니다! ▼▼▼
    @JsonProperty("output")
    val output: List<KisDailyPriceOutput>?
)

data class KisDailyPriceOutput(
    @JsonProperty("stck_bsop_date") val date: String,
    @JsonProperty("stck_oprc") val openPrice: String,
    @JsonProperty("stck_hgpr") val highPrice: String,
    @JsonProperty("stck_lwpr") val lowPrice: String,
    @JsonProperty("stck_clpr") val closePrice: String,
    @JsonProperty("acml_vol") val volume: String
)

// [기존] 토큰용 (건드리지 마세요)
data class KisTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String
)