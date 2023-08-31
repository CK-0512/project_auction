package com.project.auction.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping("/usr/realTime/list")
	public String showList(Model model, 
			@RequestParam(defaultValue = "0") int categoryId,
			@RequestParam(defaultValue = "0") int endStatus,
			@RequestParam(defaultValue = "1") int confirmStatus,
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

		int realTimeCnt = realTimeService.getRealTimeCnt(categoryId, searchKeyword, endStatus, confirmStatus);
		
		int itemsInAPage = 16;
		
		int pagesCnt = (int) Math.ceil((double) realTimeCnt / itemsInAPage);

		List<RealTime> realTimeContents = realTimeService.getRealTimeContents(categoryId, searchKeyword, endStatus, confirmStatus, itemsInAPage, page);

		List<FileVO> files = new ArrayList<>();
 		for(RealTime rt : realTimeContents) {
 			FileVO file = fileService.getContentsFirstFile(auctionType, rt.getId());
 			files.add(file);
 		}
		
		model.addAttribute("files", files);
		model.addAttribute("realTimeContents", realTimeContents);
		model.addAttribute("pagesCnt", pagesCnt);
		model.addAttribute("realTimeCnt", realTimeCnt);
		model.addAttribute("categories", categories);
		model.addAttribute("selectedCategory", selectedCategory);
		model.addAttribute("page", page);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("endStatus", endStatus);
		model.addAttribute("confirmStatus", confirmStatus);

		return "usr/realTime/list";
	}
	
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
		
		int isExist = realTimeService.isExist(rq.getLoginedMemberId());
		int lastDate = 7;
		
		if (isExist != 0) {
			lastDate = realTimeService.getLastDateByMemberId(rq.getLoginedMemberId());
		}
		
		if (lastDate < 7) {
			return Util.jsReplace(Util.f("마지막 등록일로부터 일주일이 경과되지 않아 %d일 후에 등록이 가능합니다.", 7 - lastDate), "list");
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
	
	@RequestMapping("/usr/realTime/detail")
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
		
		if (realTime.getConfirmStatus() != 0) {
			return rq.jsReturnOnView(Util.f("해당 상품은 더 이상 수정이 불가능합니다", id));
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
		
		if (realTime.getConfirmStatus() != 0) {
			return rq.jsReturnOnView("해당 상품은 더 이상 수정이 불가능합니다");
		}

		if (rq.getLoginedMemberId() != realTime.getMemberId()) {
			return Util.jsHistoryBack("해당 상품에 대한 권한이 없습니다");
		}

		realTimeService.modifyRealTime(id, categoryId, startBid, name, body);

		return Util.jsReplace(Util.f("%s 상품이 수정되었습니다", realTime.getName()), Util.f("detail?id=%d", id));
	}
	
	@RequestMapping("/usr/realTime/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		RealTime realTime = realTimeService.getRealTimeById(id);

		if (realTime == null) {
			return Util.jsHistoryBack(Util.f("%d번 상품은 존재하지 않습니다", id));
		}
		
		if (realTime.getConfirmStatus() != 0) {
			return rq.jsReturnOnView("해당 상품은 더 이상 삭제가 불가능합니다");
		}

		if (rq.getLoginedMemberId() != realTime.getMemberId()) {
			return Util.jsHistoryBack("해당 상품에 대한 권한이 없습니다");
		}

		realTimeService.deleteRealTime(id);

		return Util.jsReplace(Util.f("%s 상품의 신청이 삭제되었습니다", realTime.getName()), "usr/realTime/list");
	}
	
	@RequestMapping("/usr/realTime/doConfirm")
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

		return Util.jsReplace(Util.f("%s 상품이 경매 대기열에 등록되었습니다", realTime.getName()), Util.f("detail?id=%d", id));
	}
	
	@RequestMapping("/usr/realTime/doReject")
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
