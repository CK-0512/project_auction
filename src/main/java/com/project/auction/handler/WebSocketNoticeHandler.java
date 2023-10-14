package com.project.auction.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
	private ApplicationContext applicationContext;

    @Autowired
    public WebSocketNoticeHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
	
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
			if (strs != null) {
				String cmd = strs[0];

				if ("realTimeConfirm".equals(cmd)) {
					String categoryId = strs[1];
					String registUserId = strs[2];
					String realTimeContent = strs[3];
					String realTimeId = strs[4];
					sendMessageToRegisterByConfirm(registUserId, realTimeContent, realTimeId);
					sendMessageToInterestedUsers(categoryId, registUserId, realTimeContent, realTimeId);
				} else if ("realTimeReject".equals(cmd)) {
					String registUserId = strs[1];
					String realTimeContent = strs[2];
					String realTimeId = strs[3];
					sendMessageToRegisterByReject(registUserId, realTimeContent, realTimeId);
				}
			}
		}
	}
    
    private void sendMessageToRegisterByConfirm(String registUserId, String realTimeContent, String realTimeId) throws IOException {
    	WebSocketSession userSession = userSessions.get((Integer.valueOf(registUserId)));
		
		String messageText = Util.f("<a href='../../usr/realTime/detail?id=%s'>신청하신 %s 상품의 경매신청이 승인되어, 대기열에 등록되었습니다.</a>",
                realTimeId, realTimeContent);
		TextMessage confirmMessage = new TextMessage(messageText);
		userSession.sendMessage(confirmMessage);	
	}

	private void sendMessageToInterestedUsers(String categoryId, String registUserId, String realTimeContent, String realTimeId) throws IOException {
        for (WebSocketSession userSession : userSessions) {
        	if (userSession.getAttributes().get("loginedMemberId") != registUserId) {
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
    }
	
	private void sendMessageToRegisterByReject(String registUserId, String realTimeContent, String realTimeId) throws IOException {
    	WebSocketSession userSession = userSessions.get((Integer.valueOf(registUserId)));
		
		String messageText = Util.f("<a href='../../usr/realTime/detail?id=%s'>신청하신 %s 상품의 경매신청이 반려되었습니다. 다음 기회를 기대해주세요.</a>",
                realTimeId, realTimeContent);
		TextMessage rejectMessage = new TextMessage(messageText);
		userSession.sendMessage(rejectMessage);	
	}
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userSessions.remove(session);
    }

}