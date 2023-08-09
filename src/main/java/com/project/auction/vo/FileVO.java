package com.project.auction.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVO {
	private int id;
	private int productId;
	private String orgNm;
	private String savedNm;
	private String savedPath;
}