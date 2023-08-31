package com.project.auction.handler;

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
public class WebSocketNoticeHandler extends AbstractWebSocketHandler {
	
	private List<WebSocketSession> sessions = new ArrayList<>();
	private Map<String, WebSocketSession> userSessions = new HashMap<>();
	
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    	System.out.println("afterConnectionEstablished:" + session);
    	sessions.add(session);
        String senderId = sendPushUserId(session);
        if (senderId != null) {
            userSessions.put(senderId, session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage:" + session + " : " + message);

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
					TextMessage tmpMsg = new TextMessage("<a href='usr/realTime/?id=" + id + "'>" + realTimeContent + "</a> 상품이 등록되었습니다.");
					userSession.sendMessage(tmpMsg);
				}
			}
		}
	}

    private String sendPushUserId(WebSocketSession session) {
		String loginUserId;
		
		if (session.getPrincipal() == null) {
			loginUserId = null;
		} else {
			loginUserId = session.getPrincipal().getName();
		}
		return loginUserId;
	}
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String senderId = sendPushUserId(session);
        sessions.remove(session);
        if (senderId != null) {
            userSessions.remove(senderId);
        }
    }

}