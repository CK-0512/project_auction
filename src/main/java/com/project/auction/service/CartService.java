package com.project.auction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.auction.dao.CartDao;
import com.project.auction.vo.Auction;
import com.project.auction.vo.Cart;

@Service
public class CartService {
	
	private CartDao cartDao;
	
	@Autowired
	CartService(CartDao cartDao){
		this.cartDao = cartDao;
	}

	public void addCart(int loginedMemberId, Auction auction) {
		cartDao.addCart(loginedMemberId, auction.getId(), auction.getName(), auction.getDescription(), auction.getNowBid(), auction.getEndStatus());
	}
	
	public int getCartCntWithOutEither(int loginedMemberId) {
		return cartDao.getCartCntWithOutEither(loginedMemberId);
	}

	public int getCartCnt(int memberId, String searchKeyword, int endStatus) {
		return cartDao.getCartCnt(memberId, searchKeyword, endStatus);
	}

	public List<Cart> getCarts(int memberId, String searchKeyword, int endStatus, int itemsInAPage, int page) {

		int limitStart = (page - 1) * itemsInAPage;
		
		List<Cart> list = cartDao.getCarts(memberId, searchKeyword, endStatus, limitStart, itemsInAPage);
		
		return list;
	}

	public void modifyCart(int id, String body) {
		cartDao.modifyCart(id, body);
	}

}
