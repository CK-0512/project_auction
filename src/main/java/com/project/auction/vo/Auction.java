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
	private int bidDate;
	private String endDate;
	private int memberId;
	private String memberName;
	private int categoryId;
	private String name;
	private String description;
	private int startBid;
	private int minimumBid;
	private int nowBid;
	private int endBid;
	private int bidCount;
	private int buyNow;
	private int charge;
	private int endStatus;
	private int buyerId;
}