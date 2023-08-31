package com.project.auction.handler;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class WebSocketAuctionHandler extends AbstractWebSocketHandler {
	
    private Map<String, WebSocketSession> auctionSessions = new HashMap<>();
	
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	System.out.println("afterConnectionEstablished:" + session);
    	URI uri = session.getUri();
        String query = uri.getQuery();
        String auctionId = "";

        if (query != null) {
            String[] queryParams = query.split("&");
            for (String param : queryParams) {
                String[] pair = param.split("=");
                if (pair.length == 2 && pair[0].equals("auctionId")) {
                    auctionId = pair[1];
                    break;
                }
            }
        }

        session.getAttributes().put("auctionId", auctionId);
        auctionSessions.put(auctionId, session);
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage:" + session + " : " + message);
		
	}

    public void broadcastRemainingTime(int auctionId, long remainingTime) throws IOException {
        TextMessage message = new TextMessage(
                "{\"remainingTime\": " + remainingTime + "}"
        );

        WebSocketSession session = auctionSessions.get(auctionId);
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        }
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String auctionId = String.valueOf(session.getAttributes().get("auctionId"));
        auctionSessions.remove(auctionId);
    }

}