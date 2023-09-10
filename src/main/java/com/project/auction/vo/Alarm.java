package com.project.auction.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {
	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String message;
	private String alarmUrl;
	private int alarmStatus;
	private int endStatus;

}