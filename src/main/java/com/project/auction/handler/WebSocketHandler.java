package com.project.auction.handler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

    private Map<Integer, WebSocketSession> auctionSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        int auctionId = extractAuctionIdFromSession(session);
        auctionSessions.put(auctionId, session);
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

    private int extractAuctionIdFromSession(WebSocketSession session) {
        // Implement this method to extract auction ID from the session or the URL
        // Return the appropriate auction ID
        return 0; // Placeholder, replace with actual auction ID extraction logic
    }
}