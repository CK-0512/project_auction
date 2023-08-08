package com.project.auction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.auction.dao.AuctionDao;
import com.project.auction.vo.Auction;

@Service
public class AuctionService {
	
	private AuctionDao auctionDao;
	
	@Autowired
	AuctionService(AuctionDao auctionDao){
		this.auctionDao = auctionDao;
	}

	public int getAuctionCnt(int categoryId, String searchKeyword, int endStatus) {
		return auctionDao.getAuctionCnt(categoryId, searchKeyword, endStatus);
	}

	public List<Auction> getAuctionContents(int categoryId, String searchKeyword, int itemsInAPage, int page, int endStatus) {
		
		int limitStart = (page - 1) * itemsInAPage;
		
		return auctionDao.getAuctionContents(categoryId, searchKeyword, endStatus, limitStart, itemsInAPage);
	}
}
