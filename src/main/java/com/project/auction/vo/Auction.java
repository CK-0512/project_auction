package com.project.auction.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
	private int id;
	private String regDate;
	private String updateDate;
	private int productId;
	private int memberId;
	private int categoryId;
	private String name;
	private String description;
	private int buyerId;
	
	private String startDate;
	private String endDate;
	private int startBid;
	private int minimumBid;
	private int nowBid;
	private int endBid;
	private int bidCount;
	private int buyNow;
	
}