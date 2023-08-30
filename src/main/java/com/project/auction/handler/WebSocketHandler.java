package com.project.auction.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.project.auction.util.Util;

@Component
public class WebSocketHandler extends AbstractWebSocketHandler {
	
	List<WebSocketSession> sessions = new ArrayList<>();
	Map<String, WebSocketSession> userSessions = new HashMap<>();
	Map<String, List<WebSocketSession>> boardWriterSessions = new HashMap<>();

//    private Map<Integer, WebSocketSession> auctionSessions = new ConcurrentHashMap<>();
	
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//    	URI uri = session.getUri();
//        String query = uri.getQuery();
//        int auctionId = 0;
//
//        if (query != null) {
//            String[] queryParams = query.split("&");
//            for (String param : queryParams) {
//                String[] pair = param.split("=");
//                if (pair.length == 2 && pair[0].equals("auctionId")) {
//                    auctionId = Integer.parseInt(pair[1]);
//                    break;
//                }
//            }
//        }
//
//        session.getAttributes().put("auctionId", auctionId);
    	
    	System.out.println("afterConnectionEstablished:" + session);
		sessions.add(session);
		String senderId = sendPushUsername(session);
		userSessions.put(senderId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage:" + session + " : " + message);
//		String senderId = getId(session);
//		for (WebSocketSession sess: sessions) {
//			sess.sendMessage(new TextMessage(senderId + ": " + message.getPayload()));
//		}

		//protocol: cmd,상품,관심유저,id  (ex: realTime,realTimeContent1,user1,234)
		String msg = message.getPayload();
		if (!Util.empty(msg)) {
			String[] strs = msg.split(",");
			if (strs != null && strs.length == 4) {
				String cmd = strs[0];
				String realTimeContent = strs[1];
				String reciveUser = strs[2];
				String id = strs[3];

				WebSocketSession userSession = userSessions.get(reciveUser);
				if ("realTime".equals(cmd) && userSession != null) {
					TextMessage tmpMsg = new TextMessage("<a href='/usr/auction/?id=" + id + "'>" + realTimeContent + "</a> 상품이 등록되었습니다.");
					userSession.sendMessage(tmpMsg);
				}
			}
		}
	}
    
    public void addBoardWriterSession(String boardWriter, WebSocketSession session) {
        boardWriterSessions.computeIfAbsent(boardWriter, key -> new ArrayList<>()).add(session);
    }
    
    private String sendPushUsername(WebSocketSession session) {
		String loginUsername;
		
		if (session.getPrincipal() == null) {
			loginUsername = null;
		} else {
			loginUsername = session.getPrincipal().getName();
		}
		return loginUsername;
	}

    public void broadcastRemainingTime(int auctionId, long remainingTime) throws IOException {
        TextMessage message = new TextMessage(
                "{\"remainingTime\": " + remainingTime + "}"
        );

//        WebSocketSession session = auctionSession.get(auctionId);
//        if (session != null && session.isOpen()) {
//            session.sendMessage(message);
//        }
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        String auctionId = (String) session.getAttributes().get("auctionId");
//        auctionSessions.remove(auctionId);
        String senderId = sendPushUsername(session);
        sessions.remove(session);
        userSessions.remove(senderId);

        boardWriterSessions.forEach((key, value) -> value.removeIf(s -> s.equals(session)));
    }

}