package com.example.stock.config

// [필수] 누락되었던 import 문들
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    // Bean 충돌 방지를 위해 함수 이름을 customOpenAPI로 사용
    @Bean
    fun customOpenAPI(): OpenAPI {

        // AWS 퍼블릭 IP (나중에 Elastic IP로 변경해야 함)
        val awsIp = "3.26.94.208"

        return OpenAPI()
            .info(
                Info()
                    .title("📈 주식 웹소켓 서버 API")
                    .description("""
                        ## 🚀 웹소켓 접속 정보
                        
                        이 서버는 **실시간 주식 데이터**를 제공하기 위해 **WebSocket (STOMP)** 프로토콜을 사용합니다.
                        REST API 목록에는 나오지 않으므로 아래 정보를 참고하세요.
                        
                        | 구분 | 설명 |
                        | :--- | :--- |
                        | **서버 주소 (Broker URL)** | `http://$awsIp:8080/ws-stock` |
                        | **프로토콜** | STOMP (SockJS 사용 권장) |
                        | **CORS** | `*` (모든 도메인 허용) |
                        
                        ---
                        
                        ## 📡 1. 구독 요청 (Client ➡ Server)
                        **목적지 (Destination):** `/app/subscribe`
                        
                        **보낼 데이터 (Payload):**
                        ```json
                        {
                          "symbol": "005930",
                          "action": "ADD"  // "ADD"(구독) 또는 "REMOVE"(취소)
                        }
                        ```
                        
                        ---
                        
                        ## 📺 2. 데이터 수신 (Server ➡ Client)
                        **구독 채널 (Subscribe):** `/topic/stock/{종목코드}`
                        *(예시: `/topic/stock/005930`)*
                        
                        **받을 데이터 (Response):**
                        ```json
                        {
                          "symbol": "005930",
                          "price": 75000.0,
                          "timestamp": "2024-12-04T10:00:00"
                        }
                        ```
                    """.trimIndent())
                    .version("v1.0.0")
            )
        // [추가] REST API 그룹핑을 위한 설정 (지금은 웹소켓이라 비어있습니다)
        // .tags(List.of(
        //     new Tag().name("StockQuotes").description("주식 시세 및 차트 정보"),
        //     new Tag().name("OrderManagement").description("주식 매수/매도 주문 관리")
        // ))
    }
}