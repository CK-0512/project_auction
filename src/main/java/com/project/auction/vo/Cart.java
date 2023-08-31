package com.project.auction.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
	private int id;
	private String regDate;
	private String updateDate;
	private String endDate;
	private int auctionId;
	private int memberId;
	private String name;
	private String description;
	private int memberBid;
	private int nowBid;
	private int endBid;
	private int buyNow;
	private int charge;
	private int endStatus;
	private int buyerId;
}