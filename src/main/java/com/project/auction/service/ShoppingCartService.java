package com.project.auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.auction.dao.ShoppingCartDao;
import com.project.auction.vo.Auction;

@Service
public class ShoppingCartService {
	
	private ShoppingCartDao shoppingCartDao;
	
	@Autowired
	ShoppingCartService(ShoppingCartDao shoppingCartDao){
		this.shoppingCartDao = shoppingCartDao;
	}

	public void addCart(int loginedMemberId, Auction auction) {
		shoppingCartDao.addCart(loginedMemberId, auction);
	}
}
