package com.project.auction.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeDao {

	void registNotice(int memberId, String noticeUrl, String message, int noticeType);
	
}