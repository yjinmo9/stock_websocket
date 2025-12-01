
// 예쁜 상자(StockPrice)

package com.example.stock.model

import java.time.LocalDateTime

// 예쁜 상자 (데이터 모델)
data class StockPrice(
    val symbol: String,
    val price: Double,
    val timestamp: LocalDateTime = LocalDateTime.now()
)