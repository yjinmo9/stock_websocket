package com.example.stock.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.tags.Tag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.List

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {

        val serverIp = "3.26.94.208"

        return OpenAPI()
            .info(
                Info()
                    .title("📡 Stock Real-Time & Chart API")
                    .version("v1.0.0")
                    .description(
                        """
                        실시간 WebSocket 스트리밍 + REST 차트 데이터 API 문서입니다.  
                        아래 **각 섹션(WebSocket / Chart / Test)** 을 클릭하여 자세한 설명을 확인하세요.
                        """.trimIndent()
                    )
            )
            .tags(
                listOf(

                    // -----------------------------
                    // 1) WebSocket Tag
                    // -----------------------------
                    Tag()
                        //.name("WebSocket")
                        .description(
                            """
                            # 🔌 WebSocket Guide

                            ## 📍 Endpoint
                            ```
                            ws://$serverIp:8080/ws-stock
                            ```
                            - 프로토콜: STOMP, SockJS  
                            - 용도: 실시간 주가 스트리밍

                            ---

                            ## 🎧 1) 구독(Subscribe)
                            실시간 가격을 수신하려면 아래 채널을 구독하세요.

                            ```
                            /topic/stock/{symbol}
                            ```
                            예시:
                            ```
                            /topic/stock/005930
                            ```

                            ---

                            ## 📤 2) 요청(Publish)
                            종목 실시간 스트리밍 요청:

                            ```
                            /app/subscribe
                            ```

                            ### ➕ 구독 추가
                            ```json
                            {
                              "symbol": "005930",
                              "action": "ADD"
                            }
                            ```

                            ### ➖ 구독 해제
                            ```json
                            {
                              "symbol": "005930",
                              "action": "REMOVE"
                            }
                            ```
                            
                            
                        
                            
                    
                    
                            # 📊 Chart Data (REST)

                            ## 📍 일봉 데이터 조회
                            ```
                            GET /api/v1/chart/{symbol}
                            ```

                            예시:
                            ```
                            GET /api/v1/chart/005930
                            ```

                            반환 항목:
                            - 날짜  
                            - 시가 / 고가 / 저가 / 종가  
                            - 거래량  
                            
                            # 🔍 Server Status

                            ## 📍 서버 상태 확인
                            ```
                            GET /api/test/status
                            ```

                            응답 예시:
                            ```json
                            {
                              "status": "OK",
                              "message": "Server is running"
                            }
                            ```
                            """.trimIndent()
                        )
                )
            )
    }
}


