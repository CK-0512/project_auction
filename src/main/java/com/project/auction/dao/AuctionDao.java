package com.project.auction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.auction.vo.Auction;

@Mapper
public interface AuctionDao {

	public int getAuctionCnt(int categoryId, String searchKeyword, int endStatus);

	public List<Auction> getAuctionContents(int categoryId, String searchKeyword, int itemsInAPage, int page, int endStatus);

}
