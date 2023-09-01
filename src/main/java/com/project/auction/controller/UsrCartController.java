package com.project.auction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.auction.service.CartService;
import com.project.auction.service.FileService;
import com.project.auction.vo.Cart;
import com.project.auction.vo.FileVO;
import com.project.auction.vo.Rq;

@Controller
public class UsrCartController {

	private CartService cartService;
	private FileService fileService;
	private Rq rq;
	private int auctionType;
	
	@Autowired
	public UsrCartController(CartService cartService, FileService fileService, Rq rq) {
		this.cartService = cartService;
		this.fileService = fileService;
		this.rq = rq;
		this.auctionType = 1;
	}
	
	@RequestMapping("/usr/cart/list")
	public String showList(Model model, 
			@RequestParam(defaultValue = "0") int memberId,
			@RequestParam(defaultValue = "0") int endStatus,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "") String searchKeyword) {

		memberId = rq.getLoginedMemberId();
		
		if (page <= 0) {
			return rq.jsReturnOnView("페이지번호가 올바르지 않습니다");
		}
		
		int cartCnt = cartService.getCartCnt(memberId, searchKeyword, endStatus);
		
		int itemsInAPage = 10;
		
		int pagesCnt = (int) Math.ceil((double) cartCnt / itemsInAPage);

		List<Cart> carts = cartService.getCarts(memberId, searchKeyword, endStatus, itemsInAPage, page);

		List<FileVO> files = fileService.getCartsFirstFile(auctionType, carts);
		
		model.addAttribute("files", files);
		model.addAttribute("carts", carts);
		model.addAttribute("pagesCnt", pagesCnt);
		model.addAttribute("cartCnt", cartCnt);
		model.addAttribute("page", page);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("endStatus", endStatus);

		return "usr/cart/list";
	}
}
