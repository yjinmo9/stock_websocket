package com.example.stock.service

import com.example.stock.kis.KisService
import com.example.stock.model.StockPrice
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

// 앵커(Publisher)

@Service
class StockPublisher(
    private val template: SimpMessagingTemplate, // 방송용 마이크
    private val kisService: KisService           // 현장 기자 연결
) {

    // [방송 큐] "1초(1000ms)마다 방송 내보냅니다!"
    @Scheduled(fixedRate = 1000)
    fun publishStockData() {
        try {
            // 1. 기자 연결: "삼성전자(005930) 지금 얼마입니까?"
            val price = kisService.getCurrentPrice("005930")

            // 2. 자막 제작: 예쁜 상자에 담기
            val stockData = StockPrice(
                symbol = "SAMSUNG",
                price = price,
                timestamp = LocalDateTime.now()
            )

            // 3. 송출: "/topic/stocks" 채널 시청자들에게 발사!
            template.convertAndSend("/topic/stocks", stockData)

            // (방송국 모니터링용 로그)
            println("🎥 방송 송출 완료: 삼성전자 ${price}원")

        } catch (e: Exception) {
            println("💥 방송 사고 발생: ${e.message}")
        }
    }
}