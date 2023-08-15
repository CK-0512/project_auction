package com.project.auction.handler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.project.auction.vo.RemainTimeMessage;

@Controller
public class WebSocketHandler {

    private final Map<Integer, LocalDateTime> auctionEndTimes = new HashMap<>();

    @MessageMapping("/auction/remainTime")
    @SendTo("/topic/auction/remainTime")
    public RemainTimeMessage getAuctionRemainTime(int id) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime endTime = auctionEndTimes.get(id);

        if (endTime == null) {
            // Handle auction not found
            return null;
        }

        Duration duration = Duration.between(currentDateTime, endTime);
        long remainingSeconds = Math.max(duration.getSeconds(), 0);

        int hours = (int) (remainingSeconds / 3600);
        int minutes = (int) ((remainingSeconds % 3600) / 60);
        int seconds = (int) (remainingSeconds % 60);

        return new RemainTimeMessage(id, hours, minutes, seconds);
    }

    public void updateAuctionEndTime(int id, LocalDateTime endTime) {
        auctionEndTimes.put(id, endTime);
    }

    public void removeAuctionEndTime(int id) {
        auctionEndTimes.remove(id);
    }
}