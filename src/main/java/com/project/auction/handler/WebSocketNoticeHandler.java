package com.project.auction.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.project.auction.util.Util;
import com.project.auction.vo.Category;

@Component
public class WebSocketNoticeHandler extends AbstractWebSocketHandler {
	
	private List<WebSocketSession> userSessions = new ArrayList<>();
	
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    	System.out.println("afterConnectionEstablished:" + session);
        userSessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage:" + session + " : " + message);

		String msg = message.getPayload();
		if (!Util.empty(msg)) {
			String[] strs = msg.split(",");
			if (strs != null && strs.length == 4) {
				String cmd = strs[0];
				String realTimeContent = strs[1];
				String categoryId = strs[2];
				String realTimeId = strs[3];

				if ("realTime".equals(cmd)) {
					sendMessageToInterestedUsers(categoryId, realTimeId, realTimeContent);
				}
			}
		}
	}
    
    private void sendMessageToInterestedUsers(String categoryId, String realTimeId, String realTimeContent) throws IOException {
        for (WebSocketSession userSession : userSessions) {
            List<Category> interestCategories = (List<Category>) userSession.getAttributes().get("memberInterestCategories");
            for (Category category : interestCategories) {
                if (String.valueOf(category.getCategoryId()).equals(categoryId)) {
                    String messageText = Util.f("<a href='../../usr/realTime/detail?id=%s'>%s 카테고리의 %s 상품이 대기열에 등록되었습니다.</a>",
                            realTimeId, category.getName(), realTimeContent);
                    TextMessage message = new TextMessage(messageText);
                    userSession.sendMessage(message);
                }
            }
        }
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userSessions.remove(session);
    }

}