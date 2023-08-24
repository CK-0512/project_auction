package com.project.auction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.auction.vo.Auction;

@Mapper
public interface RealTimeDao {

	public int getAuctionCnt(int categoryId, String searchKeyword, int endStatus);

	public List<Auction> getAuctionContents(int categoryId, String searchKeyword, int endStatus, int limitStart, int itemsInAPage);

	public void registAuction(int memberId, String name, int categoryId, int startBid, int buyNow, int bidDate,
			int charge, String body);

	public int getLastInsertId();

	public Auction getAuctionById(int id);

	public void modifyAuction(int id, String description);

	public int getAuctionByName(String name);

	public void bidAuction(int id, int bid, int buyNow);

	public void buyAuction(int buyerId, int id, int buyNow);
	
}
