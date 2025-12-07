package com.example.stock.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server // [필수] Server 객체 import
import io.swagger.v3.oas.models.tags.Tag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {

        // 형님이 연결해준 도메인
        val domain = "ws-stock.froggy1014.dev"

        return OpenAPI()
            // ▼▼▼ [핵심] 이 줄이 있어야 버튼 눌렀을 때 HTTPS로 나갑니다! ▼▼▼
            .addServersItem(Server().url("https://$domain").description("Production Server (HTTPS)"))
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
                    Tag()
                        .name("API Guide")
                        .description(
                            """
                            # 🔌 WebSocket Guide

                            ## 📍 Endpoint
                            - **URL**: `wss://$domain/ws-stock`
                            - **Protocol**: STOMP (over SockJS)
                            - **용도**: 실시간 주가 스트리밍 (1초 간격)

                            ---

                            ## 🎧 1) 듣기 (Subscribe)
                            데이터를 수신하려면 해당 종목의 채널을 구독하세요.

                            - **Path**: `/topic/stock/{symbol}`
                            - **예시**: `/topic/stock/005930` (삼성전자)

                            ---

                            ## 📤 2) 말하기 (Publish)
                            서버에 구독/취소 요청을 보냅니다.

                            - **Path**: `/app/subscribe`

                            ### ➕ 구독 추가 (페이지 진입 시)
                            ```json
                            {
                              "symbol": "005930",
                              "action": "ADD"
                            }
                            ```

                            ### ➖ 구독 해제 (페이지 이탈 시)
                            ```json
                            {
                              "symbol": "005930",
                              "action": "REMOVE"
                            }
                            ```
                            
                            ---
                            
                            # 📊 Chart Data (REST)

                            ## 📍 일봉 데이터 조회
                            차트의 뼈대가 되는 과거 데이터를 가져옵니다.
                            
                            - **URL**: `https://$domain/api/v1/chart/{symbol}`
                            - **Method**: `GET`
                            
                            **반환 데이터 예시:**
                            ```json
                            [
                              {
                                "stck_bsop_date": "20240101",
                                "stck_clpr": "75000",
                                "acml_vol": "1500000"
                              },
                              ...
                            ]
                            ```
                            
                            ---
                            
                            # 🔍 Server Status

                            ## 📍 서버 상태 확인
                            - **URL**: `https://$domain/api/test/status`
                            - **Method**: `GET`

                            **응답 예시:**
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