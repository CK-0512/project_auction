package com.project.auction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.auction.vo.Cart;

@Mapper
public interface CartDao {
	
	public void addCart(int memberId, int auctionId, String name, String description, int nowBid, int endStatus);

	public int getCartCnt(int memberId, String searchKeyword, int endStatus);

	public List<Cart> getCarts(int memberId, String searchKeyword, int endStatus, int limitStart, int itemsInAPage);

	public int getCartCntWithOutEither(int memberId);

	public void modifyCart(int auctionId, String description);
	
}
