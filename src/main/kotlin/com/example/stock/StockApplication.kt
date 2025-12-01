package com.example.stock

import com.example.stock.kis.KisConfig // [1] 이거 import 꼭 필요!
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

//사장님(Application)

/* 전체 흐름 한 방 정리

** 사장님(Application)이 출근해서 "방송 시작!" 버튼을 누릅니다.

** 앵커(Publisher)가 1초마다 기자(KisService)를 쪼읍니다. "야, 가격 알아와!"

** 기자는 금고지기(KisConfig)한테 출입증 받아서 한투 서버 다녀옵니다.

** 가져온 정보를 통역사(KisDto)가 해석해줍니다.

** 앵커는 그 정보를 예쁜 상자(StockPrice)에 담습니다.

** 송신탑(WebSocketConfig)을 통해 전 국민(index.html)에게 쏩니다. */



@SpringBootApplication
@EnableScheduling // [도장 1] "1초마다 방송해라!" (이거 없으면 앵커가 잡니다)
@EnableConfigurationProperties(KisConfig::class) // [도장 2] "금고지기 권한 승인!" (이거 없으면 키 못 읽음)
class StockApplication

fun main(args: Array<String>) {
    // 방송국 전원 ON!
    runApplication<StockApplication>(*args)
}

