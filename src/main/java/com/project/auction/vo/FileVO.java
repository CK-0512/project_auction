package com.project.auction.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVO {
	private int id;
	private String originName;
	private String savedName;
	private String savedPath;
	private int auctionId;
	private int auctionType;
}