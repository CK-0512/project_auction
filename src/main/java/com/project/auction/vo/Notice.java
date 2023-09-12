package com.project.auction.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice {
	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String message;
	private String noticeUrl;
	private int noticeType;
	private int endStatus;

}