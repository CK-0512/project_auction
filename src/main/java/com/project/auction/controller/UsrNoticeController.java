package com.project.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.auction.service.NoticeService;
import com.project.auction.util.Util;
import com.project.auction.vo.ResultData;

@Controller
public class UsrNoticeController {

	private NoticeService noticeService;

	@Autowired
	public UsrNoticeController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	@RequestMapping("/usr/notice/registNotice")
	@ResponseBody
	public ResultData<String> registNotice(int memberId, String noticeUrl, String message, int noticeType) {
		
		noticeService.registNotice(memberId, noticeUrl, message, noticeType);
		
		return ResultData.from("S-1", Util.f("%s", message), "URL", noticeUrl);
	}
}