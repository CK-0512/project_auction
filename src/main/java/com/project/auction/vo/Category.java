package com.project.auction.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
	private int id;
	private String regDate;
	private String updateDate;
	private String name;
	private int delStatus;
	private String delDate;
}
