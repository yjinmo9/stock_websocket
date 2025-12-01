package com.example.stock.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

//송신탑(WebSocketConfig)

@Configuration
@EnableWebSocketMessageBroker // "송신탑 전원 켭니다!"
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // "시청자 여러분, 주파수(URL)는 '/ws-stock' 입니다. 여기로 들어오세요!"
        registry.addEndpoint("/ws-stock")
            .setAllowedOriginPatterns("*") // "어디서 접속하든 다 받아줍니다"
            .withSockJS() // "오래된 TV(브라우저)도 볼 수 있게 해줍니다"
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // "뉴스 채널은 '/topic'으로 시작합니다. 이 채널 구독하신 분들에게만 쏩니다."
        registry.enableSimpleBroker("/topic")
    }
}