package com.project.auction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.auction.vo.Notice;

@Mapper
public interface NoticeDao {

	public void registNotice(int memberId, String noticeUrl, String message, int noticeType);

	public List<Notice> getMemberNoticesInMenuBar(int memberId);
	
}