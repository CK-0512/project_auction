package com.project.auction.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.auction.service.CategoryService;
import com.project.auction.service.FileService;
import com.project.auction.service.RealTimeService;
import com.project.auction.util.Util;
import com.project.auction.vo.Category;
import com.project.auction.vo.FileVO;
import com.project.auction.vo.RealTime;
import com.project.auction.vo.Rq;

@Controller
public class UsrRealTimeController {
	
	private RealTimeService realTimeService;
	private CategoryService categoryService;
	private FileService fileService;
	private Rq rq;
	private int auctionType;
	
	@Autowired
	public UsrRealTimeController(RealTimeService realTimeService, CategoryService categoryService, FileService fileService, Rq rq) {
		this.realTimeService = realTimeService;
		this.categoryService = categoryService;
		this.fileService = fileService;
		this.rq = rq;
		this.auctionType = 2;
	}
	
//	@RequestMapping("/usr/realTime/list")
//	public String showList(Model model, 
//			@RequestParam(defaultValue = "0") int categoryId,
//			@RequestParam(defaultValue = "0") int endStatus,
//			@RequestParam(defaultValue = "1") int page,
//			@RequestParam(defaultValue = "") String searchKeyword) {
//
//		if (page <= 0) {
//			return rq.jsReturnOnView("페이지번호가 올바르지 않습니다");
//		}
//		
//		List<Category> selectedCategory = categoryService.getCategoryById(categoryId);
//
//		if (selectedCategory.isEmpty()) {
//			return rq.jsReturnOnView("존재하지 않는 카테고리입니다");
//		}
//		
//		List<Category> categories = categoryService.getCategories(); 
//
//		int auctionCnt = auctionService.getAuctionCnt(categoryId, searchKeyword, endStatus);
//		
//		int itemsInAPage = 16;
//		
//		int pagesCnt = (int) Math.ceil((double) auctionCnt / itemsInAPage);
//
//		List<Auction> auctionContents = auctionService.getAuctionContents(categoryId, searchKeyword, endStatus, itemsInAPage, page);
//
//		List<FileVO> files = fileService.getAuctionContentsFirstFiles(auctionContents);
//		
//		for (Auction auction : auctionContents) {
//	        long currentTimeMillis = System.currentTimeMillis();
//	        long endTimeMillis = auction.getEndDate().getTime();
//	        long remainingTime = (endTimeMillis - currentTimeMillis) / 1000;
//
//	        try {
//				webSocketHandler.broadcastRemainingTime(auction.getId(), remainingTime);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//	    }
//		
//		model.addAttribute("files", files);
//		model.addAttribute("auctionContents", auctionContents);
//		model.addAttribute("pagesCnt", pagesCnt);
//		model.addAttribute("auctionCnt", auctionCnt);
//		model.addAttribute("categories", categories);
//		model.addAttribute("selectedCategory", selectedCategory);
//		model.addAttribute("page", page);
//		model.addAttribute("searchKeyword", searchKeyword);
//		model.addAttribute("endStatus", endStatus);
//
//		return "usr/auction/list";
//	}
	
	@RequestMapping("/usr/realTime/regist")
	public String regist(Model model) {
		List<Category> categories = categoryService.getCategories(); 
		
		model.addAttribute("categories", categories);
		
		return "usr/realTime/regist";
	}
	
	@RequestMapping("/usr/realTime/doRegist")
	@ResponseBody
	public String doRegist(String name, int categoryId, MultipartFile file, int startBid, String body) {
		
		if (Util.empty(name)) {
			return Util.jsHistoryBack("상품명을 입력해주세요");
		}
		
		if (Util.empty(categoryId)) {
			return Util.jsHistoryBack("카테고리를 선택해주세요");
		}
	
		if (Util.empty(file)) {
			return Util.jsHistoryBack("상품사진을 등록해주세요");
		}
		
		if (Util.empty(startBid)) {
			return Util.jsHistoryBack("경매 시작가를 입력해주세요");
		}
		
		int isExist = realTimeService.getLastDateByMemberId(rq.getLoginedMemberId());
		
		if (isExist < 7) {
			return Util.jsHistoryBack("마지막 등록일로부터 일주일이 경과되지 않았습니다.");
		}

		
		try {
			realTimeService.registRealTime(rq.getLoginedMemberId(), name, categoryId, startBid, body);
			int realTimeId = realTimeService.getLastInsertId();
			fileService.saveFile(auctionType, file, realTimeId);
			
			return Util.jsReplace(Util.f("%s 상품이 등록되었습니다.", name), Util.f("detail?id=%d", realTimeId));
		} catch (IOException e) {
			e.printStackTrace();
			
			return "오류 발생";
		}
		
	}
	
	@RequestMapping("/usr/auction/detail")
	public String showDetail(Model model, int id) {
		RealTime realTime = realTimeService.getRealTimeById(id);
		
		List<FileVO> files = fileService.getContentsFiles(auctionType, id);
		
		model.addAttribute("realTime", realTime);
		model.addAttribute("files", files);
		
		return "usr/realTime/detail";
	}

	@RequestMapping("/usr/realTime/modify")
	public String modify(Model model, int id) {
		
		RealTime realTime = realTimeService.getRealTimeById(id);
		
		if (realTime == null) {
			return rq.jsReturnOnView(Util.f("%d번 상품은 존재하지 않습니다", id));
		}
		
		if (rq.getLoginedMemberId() != realTime.getMemberId()) {
			return rq.jsReturnOnView("해당 상품에 대한 권한이 없습니다");
		}
		
		List<Category> categories = categoryService.getCategories(); 
		
		model.addAttribute("realTime", realTime);
		model.addAttribute("categories", categories);
		
		return "usr/realTime/modify";
	}
	
	@RequestMapping("/usr/realTime/doModify")
	@ResponseBody
	public String doModify(int id, int categoryId, MultipartFile file, int startBid, String name, String body) {

		RealTime realTime = realTimeService.getRealTimeById(id);

		if (realTime == null) {
			return Util.jsHistoryBack(Util.f("%d번 상품은 존재하지 않습니다", id));
		}

		if (rq.getLoginedMemberId() != realTime.getMemberId()) {
			return Util.jsHistoryBack("해당 상품에 대한 권한이 없습니다");
		}

		realTimeService.modifyRealTime(id, categoryId, startBid, name, body);

		return Util.jsReplace(Util.f("%s 상품이 수정되었습니다", realTime.getName()), Util.f("detail?id=%d", id));
	}
}
