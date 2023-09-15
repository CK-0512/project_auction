package com.project.auction.service;

import java.time.Duration;
import java.time.LocalDateTime;
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

	public List<Auction> getAuctionContents(int categoryId, String searchKeyword, int endStatus, int itemsInAPage, int page) {
		
		int limitStart = (page - 1) * itemsInAPage;
		
		List<Auction> list = auctionDao.getAuctionContents(categoryId, searchKeyword, endStatus, limitStart, itemsInAPage);
		
		return list;
	}

	public void registAuction(int memberId, String name, int categoryId, int startBid, int buyNow, int bidDate,
			int charge, String body) {
		auctionDao.registAuction(memberId, name, categoryId, startBid, buyNow, bidDate, charge, body);
	}

	public int getLastInsertId() {
		return auctionDao.getLastInsertId();
	}
	
	public Duration calculateTimeRemaining(LocalDateTime endDate) {
        LocalDateTime now = LocalDateTime.now();
        return Duration.between(now, endDate);
    }

	public Auction getAuctionById(int id) {
		return auctionDao.getAuctionById(id);
	}

	public void modifyAuction(int id, String description) {
		auctionDao.modifyAuction(id, description);
	}


	public int searchExistAuction(String name, String description) {
		return auctionDao.searchExistAuction(name, description);
	}

	public void bidAuction(int auctionId, int bid, int buyNow) {
		auctionDao.bidAuction(auctionId, bid, buyNow);
	}

	public void buyAuction(int loginedMemberId, int auctionId, int buyNow) {
		auctionDao.buyAuction(loginedMemberId, auctionId, buyNow);
	}
}
