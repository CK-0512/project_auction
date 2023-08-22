package com.project.auction.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartDao {
	
	public void addCart(int memberId, int auctionId, String name, String description, int nowBid, int buyNow,
		int endStatus);
	
}
