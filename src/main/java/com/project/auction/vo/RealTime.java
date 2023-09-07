package com.project.auction.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealTime {
	private int id;
	private String regDate;
	private String updateDate;
	private String hopeDate;
	private String startDate;
	private String endDate;
	private int memberId;
	private String memberName;
	private int categoryId;
	private String name;
	private String description;
	private int startBid;
	private int bidIncrease;
	private int endBid;
	private int bidCount;
	private int confirmStatus;
	private int endStatus;
	private int buyerId;
}
