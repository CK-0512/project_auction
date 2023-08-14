package com.project.auction.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.auction.service.AuctionService;
import com.project.auction.service.CategoryService;
import com.project.auction.service.FileService;
import com.project.auction.util.Util;
import com.project.auction.vo.Auction;
import com.project.auction.vo.Category;
import com.project.auction.vo.FileVO;
import com.project.auction.vo.Rq;

@Controller
public class UsrAuctionController {
	
	private AuctionService auctionService;
	private CategoryService categoryService;
	private FileService fileService;
	private Rq rq;
	
	@Autowired
	public UsrAuctionController(AuctionService auctionService, CategoryService categoryService, FileService fileService, Rq rq) {
		this.auctionService = auctionService;
		this.categoryService = categoryService;
		this.fileService = fileService;
		this.rq = rq;
	}
	
	@RequestMapping("/usr/auction/list")
	public String showList(Model model, 
			@RequestParam(defaultValue = "0") int categoryId,
			@RequestParam(defaultValue = "0") int endStatus,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "") String searchKeyword) {

		if (page <= 0) {
			return rq.jsReturnOnView("페이지번호가 올바르지 않습니다");
		}
		
		List<Category> selectedCategory = categoryService.getCategoryById(categoryId);

		if (selectedCategory.isEmpty()) {
			return rq.jsReturnOnView("존재하지 않는 카테고리입니다");
		}
		
		List<Category> categories = categoryService.getCategories(); 

		int auctionCnt = auctionService.getAuctionCnt(categoryId, searchKeyword, endStatus);
		
		int itemsInAPage = 16;
		
		int pagesCnt = (int) Math.ceil((double) auctionCnt / itemsInAPage);

		List<Auction> auctionContents = auctionService.getAuctionContents(categoryId, searchKeyword, endStatus, itemsInAPage, page);

		List<FileVO> files = fileService.getAuctionContentsFirstFiles(auctionContents);

		model.addAttribute("files", files);
		model.addAttribute("auctionContents", auctionContents);
		model.addAttribute("pagesCnt", pagesCnt);
		model.addAttribute("auctionCnt", auctionCnt);
		model.addAttribute("categories", categories);
		model.addAttribute("selectedCategory", selectedCategory);
		model.addAttribute("page", page);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("endStatus", endStatus);

		return "usr/auction/list";
	}
	
	@RequestMapping("/usr/auction/regist")
	public String regist() {
		return "usr/auction/regist";
	}
	
	@RequestMapping("/usr/auction/doRegist")
	@ResponseBody
	public String doRegist(String title, int categoryId, MultipartFile file, int startBid, @RequestParam(defaultValue="0")int buyNow, int bidDate, @RequestParam(defaultValue="0")int charge, String body) {
		
		if (Util.empty(title)) {
			return Util.jsHistoryBack("제품명을 입력해주세요");
		}
		
		if (Util.empty(categoryId)) {
			return Util.jsHistoryBack("카테고리를 선택해주세요");
		}
	
		if (Util.empty(file)) {
			return Util.jsHistoryBack("제품사진을 등록해주세요");
		}
		
		if (Util.empty(startBid)) {
			return Util.jsHistoryBack("경매 시작가를 입력해주세요");
		}
	
		if (Util.empty(bidDate)) {
			return Util.jsHistoryBack("경매 기간을 선택해주세요");
		}
		
		if (Util.empty(body)) {
			return Util.jsHistoryBack("제품 설명을 입력해주세요");
		}

		
		try {
			auctionService.registAuction(rq.getLoginedMemberId(), title, categoryId, startBid, buyNow, bidDate, charge, body);
			int auctionId = auctionService.getLastInsertId();
			fileService.saveFile(file, auctionId);
			
			return Util.jsReplace(Util.f("'%f' 제품의 경매가 등록되었습니다.", title), Util.f("detail?id=%d", auctionId));
		} catch (IOException e) {
			e.printStackTrace();
			
			return "오류 발생";
		}
		
	}
}
