package com.example.stock.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // [연결] 클라이언트 접속 주소
        registry.addEndpoint("/ws-stock")
            .setAllowedOriginPatterns("*") // 모든 도메인 허용 (CORS 해결)
            .withSockJS()
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // [수신] 서버 -> 클라이언트 방송 채널
        registry.enableSimpleBroker("/topic")

        // [송신] 클라이언트 -> 서버 요청 채널 (/app/subscribe 등)
        registry.setApplicationDestinationPrefixes("/app")
    }
}