package com.project.auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.auction.dao.CartDao;
import com.project.auction.vo.Auction;

@Service
public class CartService {
	
	private CartDao cartDao;
	
	@Autowired
	CartService(CartDao cartDao){
		this.cartDao = cartDao;
	}

	public void addCart(int loginedMemberId, Auction auction) {
		cartDao.addCart(loginedMemberId, auction.getId(), auction.getName(), auction.getDescription(), auction.getNowBid(), auction.getBuyNow(), auction.getEndStatus());
	}
}
