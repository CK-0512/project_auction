package com.project.auction.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.project.auction.service.AuctionService;

@Component
public class AuctionScheduler {

    private final AuctionService auctionService;

    @Autowired
    public AuctionScheduler(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @Scheduled(fixedRate = 60000) 
    public void scheduledTask() {
        auctionService.endExpiredAuctions();
    }
}