package com.project.auction.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BidHistoryDao {

	public void addAuctionHistory(int auctionId, int memberId, int nowBid, int bidSuccessful, int auctionType);
	
}