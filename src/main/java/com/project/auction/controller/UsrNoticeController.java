package com.project.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.auction.service.NoticeService;
import com.project.auction.util.Util;
import com.project.auction.vo.ResultData;
import com.project.auction.vo.Rq;

@Controller
public class UsrNoticeController {

	private NoticeService noticeService;
	private Rq rq;

	@Autowired
	public UsrNoticeController(NoticeService noticeService, Rq rq) {
		this.noticeService = noticeService;
		this.rq = rq;
	}
	
	@RequestMapping("/usr/notice/doRegist")
	@ResponseBody
	public ResultData<String> doRegist(int memberId, String noticeUrl, String message, int noticeType) {
		
		noticeService.registNotice(memberId, noticeUrl, message, noticeType);
		
		return ResultData.from("S-1", Util.f("%s", message), "URL", noticeUrl);
	}
}