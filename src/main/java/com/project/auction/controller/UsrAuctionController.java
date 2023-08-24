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

import com.project.auction.handler.WebSocketHandler;
import com.project.auction.service.AuctionService;
import com.project.auction.service.CategoryService;
import com.project.auction.service.FileService;
import com.project.auction.service.MemberService;
import com.project.auction.service.CartService;
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
	private MemberService memberService;
	private CartService cartService;
	private Rq rq;
    private WebSocketHandler webSocketHandler;
	
	@Autowired
	public UsrAuctionController(AuctionService auctionService, CategoryService categoryService, FileService fileService, MemberService memberService, CartService cartService, Rq rq, WebSocketHandler webSocketHandler) {
		this.auctionService = auctionService;
		this.categoryService = categoryService;
		this.fileService = fileService;
		this.memberService = memberService;
		this.cartService = cartService;
		this.rq = rq;
        this.webSocketHandler = webSocketHandler;
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
		
		for (Auction auction : auctionContents) {
	        long currentTimeMillis = System.currentTimeMillis();
	        long endTimeMillis = auction.getEndDate().getTime();
	        long remainingTime = (endTimeMillis - currentTimeMillis) / 1000;

	        try {
				webSocketHandler.broadcastRemainingTime(auction.getId(), remainingTime);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
		
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
	public String regist(Model model) {
		List<Category> categories = categoryService.getCategories(); 
		
		model.addAttribute("categories", categories);
		
		return "usr/auction/regist";
	}
	
	@RequestMapping("/usr/auction/doRegist")
	@ResponseBody
	public String doRegist(String name, int categoryId, @RequestParam(defaultValue = "1")int auctionType, MultipartFile file, int startBid, @RequestParam(defaultValue="0")int buyNow, int bidDate, @RequestParam(defaultValue="0")int charge, String body) {
		
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
	
		if (Util.empty(bidDate)) {
			return Util.jsHistoryBack("경매 기간을 선택해주세요");
		}
		
		int isExist = auctionService.getAuctionByName(name);
		
		if (isExist != 0) {
			return Util.jsHistoryBack("동일 상품을 등록할 수 없습니다.");
		}

		
		try {
			auctionService.registAuction(rq.getLoginedMemberId(), name, categoryId, startBid, buyNow, bidDate, charge, body);
			int auctionId = auctionService.getLastInsertId();
			fileService.saveFile(auctionType, file, auctionId);
			
			return Util.jsReplace(Util.f("%s 상품이 등록되었습니다.", name), Util.f("detail?id=%d", auctionId));
		} catch (IOException e) {
			e.printStackTrace();
			
			return "오류 발생";
		}
		
	}
	
	@RequestMapping("/usr/auction/detail")
	public String showDetail(Model model, int id) {
		Auction auction = auctionService.getAuctionById(id);
		
		List<FileVO> files = fileService.getAuctionContentFiles(id);
		
		model.addAttribute("auction", auction);
		model.addAttribute("files", files);
		
		return "usr/auction/detail";
	}
	
	@RequestMapping("/usr/auction/doBid")
	@ResponseBody
	public String doBid(int auctionId, int bid, @RequestParam(defaultValue = "0") int buyNow) {
		
		if (Util.empty(bid)) {
			return Util.jsHistoryBack("입찰금액을 입력해주세요");
		}
		
		auctionService.bidAuction(auctionId, bid, buyNow);
		
		memberService.spendMoney(rq.getLoginedMemberId(), bid);
		
		Auction auction = auctionService.getAuctionById(auctionId);
		
		cartService.addCart(rq.getLoginedMemberId(), auction);
		
		return Util.jsReplace("상품을 입찰하였습니다.", Util.f("detail?id=%d", auctionId));
	}
	
	@RequestMapping("/usr/auction/doBuy")
	@ResponseBody
	public String doBuy(int auctionId, int buyNow, @RequestParam(defaultValue = "0") int bid) {
		
		if (Util.empty(buyNow)) {
			return Util.jsHistoryBack("즉시구매 오류");
		}
		
		auctionService.buyAuction(rq.getLoginedMemberId(), auctionId, buyNow);
		
		memberService.spendMoney(rq.getLoginedMemberId(), buyNow);
		
		Auction auction = auctionService.getAuctionById(auctionId);
		
		cartService.addCart(rq.getLoginedMemberId(), auction);
		
		return Util.jsReplace("상품을 구매하였습니다.", "cart");
	}
	
	@RequestMapping("/usr/auction/modify")
	public String modify(Model model, int id) {
		
		Auction auction = auctionService.getAuctionById(id);
		
		if (auction == null) {
			return rq.jsReturnOnView(Util.f("%d번 상품은 존재하지 않습니다", id));
		}
		
		if (rq.getLoginedMemberId() != auction.getMemberId()) {
			return rq.jsReturnOnView("해당 상품에 대한 권한이 없습니다");
		}
		
		String categoryName = categoryService.getCategoryNameById(auction.getCategoryId());

		model.addAttribute("auction", auction);
		model.addAttribute("categoryName", categoryName);
		
		return "usr/auction/modify";
	}
	
	@RequestMapping("/usr/auction/doModify")
	@ResponseBody
	public String doModify(int id, String body) {

		Auction auction = auctionService.getAuctionById(id);

		if (auction == null) {
			return Util.jsHistoryBack(Util.f("%d번 상품은 존재하지 않습니다", id));
		}

		if (rq.getLoginedMemberId() != auction.getMemberId()) {
			return Util.jsHistoryBack("해당 상품에 대한 권한이 없습니다");
		}

		auctionService.modifyAuction(id, body);

		return Util.jsReplace(Util.f("%s 상품이 수정되었습니다", auction.getName()), Util.f("detail?id=%d", id));
	}
}
