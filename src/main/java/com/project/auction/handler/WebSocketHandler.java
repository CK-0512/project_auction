package com.project.auction.handler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

    private Map<Integer, WebSocketSession> auctionSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	URI uri = session.getUri();
        String query = uri.getQuery();
        int auctionId = 0;

        if (query != null) {
            String[] queryParams = query.split("&");
            for (String param : queryParams) {
                String[] pair = param.split("=");
                if (pair.length == 2 && pair[0].equals("auctionId")) {
                    auctionId = Integer.parseInt(pair[1]);
                    break; // auctionId를 찾았으므로 반복문 중단
                }
            }
        }

        // Auction ID를 세션의 속성으로 저장
        session.getAttributes().put("auctionId", auctionId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        
    }

    public void broadcastRemainingTime(int auctionId, long remainingTimeInSeconds) throws IOException {
        TextMessage message = new TextMessage(
                "{\"remainingTime\": " + remainingTimeInSeconds + "}"
        );

        WebSocketSession session = auctionSessions.get(auctionId);
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        }
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        int auctionId = (int) session.getAttributes().get("auctionId");
        auctionSessions.remove(auctionId);
    }

}