package com.example.stock.kis

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

// 기자(KisService)

@Service
class KisService(
    private val config: KisConfig, // 금고지기한테 키 받아옴
    private val restTemplate: RestTemplate // 법인차(브라우저) 받아옴
) {
    // 기자수첩: 받은 토큰을 잠시 적어두는 곳
    private var accessToken: String = ""

    // [업무 1] 출입증(토큰) 받아오기
    fun getAccessToken(): String {
        // 이미 받아둔 게 있으면 그거 씁니다 (재활용)
        if (accessToken.isNotEmpty()) return accessToken

        // 한투 보안실 주소
        val url = "${config.baseUrl}/oauth2/tokenP"
        // 제출할 서류 (내 아이디, 비번)
        val body = mapOf(
            "grant_type" to "client_credentials",
            "appkey" to config.appKey.trim(),
            "appsecret" to config.appSecret.trim()
        )


        // 법인차 타고 가서 서류 내고 출입증 받아옴


        try {
            val response = restTemplate.postForObject(url, body, KisTokenResponse::class.java)
            accessToken = response?.accessToken ?: ""
            println("✅ 토큰 발급 성공: $accessToken") // 성공 로그
            return accessToken
        } catch (e: Exception) {
            // ▼▼▼ 여기에 에러 내용을 자세히 찍어보세요! ▼▼▼
            println("🚨 토큰 발급 실패! 원인: ${e.message}")
            return ""
        }

    }

    // [업무 2] 진짜 가격 물어보기
    fun getCurrentPrice(symbol: String): Double {
        val token = getAccessToken() // 수첩에서 출입증 꺼냄

        // 한투 시세 게시판 주소
        val url = "${config.baseUrl}/uapi/domestic-stock/v1/quotations/inquire-price?FID_COND_MRKT_DIV_CODE=J&FID_INPUT_ISCD=$symbol"

        // 질문할 때 갖춰야 할 예의 (헤더 설정)
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set("authorization", "Bearer $token")
            // [수정 1] 여기도 .trim()을 꼭 붙여야 합니다! (실전 서버는 예민함)
            set("appkey", config.appKey.trim())
            set("appsecret", config.appSecret.trim())
            // [수정 2] "주식 현재가 시세"용 ID는 실전/모의 똑같이 이겁니다.
            set("tr_id", "FHKST01010100")
            // [수정 3] 혹시 모르니 "개인 고객(P)"이라고 신분을 밝힙니다.
            set("custtype", "P")
        }

        val entity = HttpEntity<String>(headers)


        try {
            // 법인차 타고 가서 질문하고 답변 받아옴
            val response = restTemplate.exchange(url, HttpMethod.GET, entity, KisPriceResponse::class.java)
            // 답변서(KisDto)에서 가격 숫자만 쏙 빼서 줌
            return response.body?.output?.price?.toDouble() ?: 0.0
        } catch (e: Exception) {
            // 에러 나면 로그에 URL이랑 원인을 자세히 찍어줌
            println("💥 방송 사고 발생: ${e.message}")
            return 0.0
        }
    }
}