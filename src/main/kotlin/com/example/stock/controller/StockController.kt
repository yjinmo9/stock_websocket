package com.example.stock.controller

import com.example.stock.kis.KisDailyPriceOutput
import com.example.stock.kis.KisService
import com.example.stock.service.StockPublisher
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

data class SubscribeRequest(
    val symbol: String = "",
    val action: String = "ADD"
)

@RestController // [변경] REST API 지원을 위해 변경
//@Tag(name = "Stock API", description = "주식 데이터 관련 API")
class StockController(
    private val stockPublisher: StockPublisher,
    private val kisService: KisService
) {

    // 1. 웹소켓 구독/취소 요청 처리
    @MessageMapping("/subscribe")
    fun handleSubscription(request: SubscribeRequest) {
        if (request.symbol.isBlank()) return

        if (request.action == "REMOVE") {
            stockPublisher.removeSymbol(request.symbol)
        } else {
            stockPublisher.addSymbol(request.symbol)
        }
    }

    // [추가] 2. 차트용 과거 데이터 조회 (REST API)
    @GetMapping("/api/v1/chart/{symbol}")
    @Tag(name = "Chart Data")
    @Operation(summary = "일봉 차트 데이터 조회", description = "해당 종목의 과거 일봉 데이터를 조회합니다.")
    fun getDailyChartData(@PathVariable symbol: String): List<KisDailyPriceOutput> {
        println("📝 차트 데이터 요청: $symbol")
        return kisService.getDailyChartData(symbol)
    }

    // [추가] 3. Swagger용 테스트 API
    @GetMapping("/api/test/status")
    @Tag(name = "Test")
    @Operation(summary = "서버 상태 확인", description = "서버가 정상 작동 중인지 확인합니다.")
    fun getStatus(): Map<String, String> {
        return mapOf("status" to "OK", "message" to "Server is running")
    }
}