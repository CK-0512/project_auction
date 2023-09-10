package com.project.auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.auction.dao.BidHistoryDao;

@Service
public class BidHistoryService {
	
	private BidHistoryDao bidHistoryDao;
	
	@Autowired
	BidHistoryService(BidHistoryDao bidHistoryDao){
		this.bidHistoryDao = bidHistoryDao;
	}
	
}