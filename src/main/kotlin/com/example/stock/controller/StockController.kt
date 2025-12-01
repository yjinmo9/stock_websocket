package com.example.stock.controller

import com.example.stock.kis.KisService
import com.example.stock.model.StockPrice
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import java.time.LocalDateTime

// [1] 쪽지 모양(DTO)을 명확하게 정의합니다. (Map보다 훨씬 안전함)
data class SearchRequest(
    val symbol: String = ""
)

@Controller
class StockController(
    private val kisService: KisService
) {

    @MessageMapping("/search")
    @SendTo("/topic/search-result")
    // [2] Map 대신 SearchRequest 객체로 받습니다.
    fun handleSearchRequest(request: SearchRequest): StockPrice? {
        println("📨 컨트롤러 도착! 요청 내용: ${request.symbol}")

        try {
            val symbol = if (request.symbol.isBlank()) "005930" else request.symbol

            // 기자 호출
            val price = kisService.getCurrentPrice(symbol)
            println("✅ 가격 조회 성공: $price")

            return StockPrice(
                symbol = symbol,
                price = price,
                timestamp = LocalDateTime.now()
            )
        } catch (e: Exception) {
            println("🚨 컨트롤러 에러 발생: ${e.message}")
            e.printStackTrace() // 에러 상세 내용 출력
            return null
        }
    }
}