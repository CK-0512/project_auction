package com.project.auction.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.project.auction.handler.WebSocketNoticeHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer  {

	@Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new WebSocketNoticeHandler(), "/webSocket/notice").setAllowedOrigins("*").addInterceptors(new HttpSessionHandshakeInterceptor());
    }
}