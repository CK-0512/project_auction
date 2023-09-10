package com.project.auction.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidHistory {
	private int id;
	private String regDate;
	private int auctionId;
	private int memberId;
	private String memberName;
	private int bidMoney;
	private int bidSuccessful;
	private int auctionType;

}