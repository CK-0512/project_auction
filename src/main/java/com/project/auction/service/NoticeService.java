package com.project.auction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.auction.dao.NoticeDao;
import com.project.auction.vo.Notice;

@Service
public class NoticeService {
	
	private NoticeDao noticeDao;
	
	@Autowired
	NoticeService(NoticeDao noticeDao){
		this.noticeDao = noticeDao;
	}

	public void registNotice(int memberId, String noticeUrl, String message, int noticeType) {
		noticeDao.registNotice(memberId, noticeUrl, message, noticeType);
	}


	public List<Notice> getMemberNoticesInMenuBar(int loginedMemberId) {
		return noticeDao.getMemberNoticesInMenuBar(loginedMemberId);
	}
	
}