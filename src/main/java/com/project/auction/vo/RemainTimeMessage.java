package com.project.auction.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RemainTimeMessage {
	private int auctionId;
    private int hours;
    private int minutes;
    private int seconds;
    
    public RemainTimeMessage(int auctionId, int hours, int minutes, int seconds) {
        this.auctionId = auctionId;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }
	
}
