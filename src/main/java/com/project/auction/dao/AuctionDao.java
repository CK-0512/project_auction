package com.project.auction.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.auction.vo.Auction;

@Mapper
public interface AuctionDao {

	public int getAuctionCnt(int categoryId, String searchKeyword, int endStatus);

	public List<Auction> getAuctionContents(int categoryId, String searchKeyword, int endStatus, int limitStart, int itemsInAPage);

	public void registAuction(int memberId, String name, int categoryId, int startBid, int buyNow, int bidDate,
			int charge, String body);

	public int getLastInsertId();

	public Auction getAuctionById(int id);

	public void modifyAuction(int id, String description);

	public int searchExistAuction(String name, String description);

	public void bidAuction(int id, int bid, int buyNow, int memberId);

	public void buyAuction(int buyerId, int id, int buyNow);

	public List<Auction> findExpiredAuctions(LocalDateTime nowTime);

	public void expireAuction(int id, int charge);
	
}
