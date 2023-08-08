package com.project.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.auction.service.ReactionPointService;
import com.project.auction.util.Util;
import com.project.auction.vo.ReactionPoint;
import com.project.auction.vo.ResultData;
import com.project.auction.vo.Rq;

@Controller
public class UsrReactionPointController {

	private ReactionPointService reactionPointService;
	private Rq rq;
	
	@Autowired
	public UsrReactionPointController(ReactionPointService reactionPointService, Rq rq) {
		this.reactionPointService = reactionPointService;
		this.rq = rq;
	}
	
	@RequestMapping("/usr/reactionPoint/getReactionPoint")
	@ResponseBody
	public ResultData<ReactionPoint> getReactionPoint(String relTypeCode, int relId) {
		
		ReactionPoint reactionPoint = reactionPointService.getReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		return ResultData.from("S-1", "리액션 정보 조회 성공", "reactionPoint", reactionPoint);
	}
	
	@RequestMapping("/usr/reactionPoint/doInsertReactionPoint")
	@ResponseBody
	public String doInsertReactionPoint(String relTypeCode, int relId, int point) {
		
		ReactionPoint reactionPoint = reactionPointService.getReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		if (reactionPoint.getSumReactionPoint() != 0) {
			reactionPointService.doDeleteReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		}
		
		reactionPointService.doInsertReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId, point);
		
		if (point == 1) {
			return Util.jsReplace(Util.f("%d번 글에 좋아요", relId), Util.f("../article/detail?id=%d", relId));
		} else {
			return Util.jsReplace(Util.f("%d번 글에 싫어요", relId), Util.f("../article/detail?id=%d", relId));
		}
	}
	
	@RequestMapping("/usr/reactionPoint/doDeleteReactionPoint")
	@ResponseBody
	public String doDeleteReactionPoint(String relTypeCode, int relId, int point) {
		
		reactionPointService.doDeleteReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		if (point == 1) {
			return Util.jsReplace(Util.f("%d번 글에 좋아요 취소", relId), Util.f("../article/detail?id=%d", relId));
		} else {
			return Util.jsReplace(Util.f("%d번 글에 싫어요 취소", relId), Util.f("../article/detail?id=%d", relId));
		}
	}
	
}