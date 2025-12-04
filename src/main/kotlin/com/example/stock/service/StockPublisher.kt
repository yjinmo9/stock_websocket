package com.example.stock.service

import com.example.stock.kis.KisService
import com.example.stock.model.StockPrice
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentSkipListSet

@Service
class StockPublisher(
    private val template: SimpMessagingTemplate,
    private val kisService: KisService
) {
    // [변경] 동적 구독 리스트 (Set)
    private val activeSymbols = ConcurrentSkipListSet<String>()

    fun addSymbol(symbol: String) {
        activeSymbols.add(symbol)
        println("✅ 구독 추가: $symbol")
    }

    fun removeSymbol(symbol: String) {
        activeSymbols.remove(symbol)
        println("🗑️ 구독 취소: $symbol")
    }

    @Scheduled(fixedRate = 1000)
    fun publishStockData() {
        if (activeSymbols.isEmpty()) return

        activeSymbols.forEach { symbol ->
            try {
                val price = kisService.getCurrentPrice(symbol)
                val stockData = StockPrice(symbol, price, LocalDateTime.now())

                // [변경] 종목별 전용 채널로 송출
                template.convertAndSend("/topic/stock/$symbol", stockData)

                Thread.sleep(200) // 과부하 방지
            } catch (e: Exception) {
                println("💥 방송 에러($symbol): ${e.message}")
            }
        }
    }
}