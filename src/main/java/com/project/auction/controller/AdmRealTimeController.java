package com.project.auction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.auction.service.FileService;
import com.project.auction.service.RealTimeService;
import com.project.auction.util.Util;
import com.project.auction.vo.FileVO;
import com.project.auction.vo.RealTime;
import com.project.auction.vo.Rq;


@Controller
public class AdmRealTimeController {

	private RealTimeService realTimeService;
	private FileService fileService;
	private Rq rq;
	private int auctionType;

	@Autowired
	public AdmRealTimeController(RealTimeService realTimeService, FileService fileService, Rq rq) {
		this.realTimeService = realTimeService;
		this.fileService = fileService;
		this.rq = rq;
		this.auctionType = 2;
	}

	@RequestMapping("/adm/realTime/detail")
	public String showDetail(Model model, int id) {
		RealTime realTime = realTimeService.getRealTimeById(id);
		
		List<FileVO> files = fileService.getContentsFiles(auctionType, id);
		
		model.addAttribute("realTime", realTime);
		model.addAttribute("files", files);
		
		return "adm/realTime/detail";
	}
	
	@RequestMapping("/adm/realTime/doConfirm")
	@ResponseBody
	public String doConfirm(int id, String startDate) {

		RealTime realTime = realTimeService.getRealTimeById(id);

		if (realTime == null) {
			return Util.jsHistoryBack(Util.f("%d번 상품은 존재하지 않습니다", id));
		}

		if (rq.getLoginedMember().getAuthLevel() != 3) {
			return Util.jsHistoryBack("해당 기능의 권한이 없습니다");
		}

		realTimeService.confirmRealTime(id, startDate);

		return Util.jsReplace(Util.f("%s 상품이 경매 대기열에 등록되었습니다", realTime.getName()), Util.f("usr/realTime/detail?id=%d", id));
	}
	
	@RequestMapping("/adm/realTime/doReject")
	@ResponseBody
	public String doReject(int id) {

		RealTime realTime = realTimeService.getRealTimeById(id);

		if (realTime == null) {
			return Util.jsHistoryBack(Util.f("%d번 상품은 존재하지 않습니다", id));
		}

		if (rq.getLoginedMember().getAuthLevel() != 3) {
			return Util.jsHistoryBack("해당 기능의 권한이 없습니다");
		}

		realTimeService.rejectRealTime(id);

		return Util.jsReplace(Util.f("%s 상품이 반려되었습니다", realTime.getName()), Util.f("list?confirmStatus=0"));
	}

}