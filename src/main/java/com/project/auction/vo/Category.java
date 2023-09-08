package com.project.auction.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
	private int id;
	private String regDate;
	private String updateDate;
	private String name;
	private int delStatus;
	private String delDate;
	private int categoryId;
}
