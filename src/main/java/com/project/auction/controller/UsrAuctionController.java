package com.project.auction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.auction.service.AuctionService;
import com.project.auction.service.CategoryService;
import com.project.auction.service.FileService;
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
		
		List<Category> category = categoryService.getCategoryById(categoryId);

		if (category.isEmpty()) {
			return rq.jsReturnOnView("존재하지 않는 카테고리입니다");
		}

		int auctionCnt = auctionService.getAuctionCnt(categoryId, searchKeyword, endStatus);
		
		int itemsInAPage = 16;
		
		int pagesCnt = (int) Math.ceil((double) auctionCnt / itemsInAPage);

		List<Auction> auctionContents = auctionService.getAuctionContents(categoryId, searchKeyword, endStatus, itemsInAPage, page);

		List<FileVO> files = fileService.getAuctionContentsFirstFiles(auctionContents);

		model.addAttribute("files", files);
		model.addAttribute("auctionContents", auctionContents);
		model.addAttribute("pagesCnt", pagesCnt);
		model.addAttribute("auctionCnt", auctionCnt);
		model.addAttribute("category", category);
		model.addAttribute("page", page);
		model.addAttribute("searchKeyword", searchKeyword);

		return "usr/auction/list";
	}
}
