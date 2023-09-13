package com.project.auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.auction.dao.BidHistoryDao;
import com.project.auction.vo.Auction;

@Service
public class BidHistoryService {
	
	private BidHistoryDao bidHistoryDao;
	
	@Autowired
	BidHistoryService(BidHistoryDao bidHistoryDao){
		this.bidHistoryDao = bidHistoryDao;
	}

	public void addAuctionHistory(int loginedMemberId, Auction auction, int bidSuccessful, int auctionType) {
		bidHistoryDao.addAuctionHistory(auction.getId(), loginedMemberId, auction.getNowBid(), bidSuccessful, auctionType);
	}
	
}