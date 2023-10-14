package com.project.auction.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.auction.service.ArticleService;
import com.project.auction.service.AuctionService;
import com.project.auction.service.FileService;
import com.project.auction.service.RealTimeService;
import com.project.auction.vo.Article;
import com.project.auction.vo.Auction;
import com.project.auction.vo.FileVO;
import com.project.auction.vo.RealTime;

@Controller
public class UsrHomeController {
	
	private AuctionService auctionService;
	private RealTimeService realTimeService;
	private FileService fileService;
	private ArticleService articleService;
	
	@Autowired
	public UsrHomeController(AuctionService auctionService, RealTimeService realTimeService, FileService fileService, ArticleService articleService) {
		this.auctionService = auctionService;
		this.realTimeService = realTimeService;
		this.fileService = fileService;
		this.articleService = articleService;
	}
	
	@RequestMapping("/usr/home/main")
	public String showMain(Model model) {
		
		List<RealTime> startedRealTime = realTimeService.getRealTimeContentsForHome(1);
		List<FileVO> startedRealTimeFiles = new ArrayList<>();
 		for(RealTime rt : startedRealTime) {
 			FileVO file = fileService.getContentsFirstFile(2, rt.getId());
 			startedRealTimeFiles.add(file);
 		}
		List<RealTime> realTimeContents = realTimeService.getRealTimeContentsForHome(0);
		List<FileVO> realTimeFiles = new ArrayList<>();
 		for(RealTime rt : realTimeContents) {
 			FileVO file = fileService.getContentsFirstFile(2, rt.getId());
 			realTimeFiles.add(file);
 		}
		List<Auction> auctionContents = auctionService.getAuctionContentsForHome(0);
		List<FileVO> auctionFiles = new ArrayList<>();
 		for(Auction auction : auctionContents) {
 			FileVO file = fileService.getContentsFirstFile(1, auction.getId());
 			auctionFiles.add(file);
 		}
 		
 		List<Article> articles = articleService.getArticlesForHome();

 		model.addAttribute("startedRealTimeFiles", startedRealTimeFiles);
 		model.addAttribute("startedRealTime", startedRealTime);
 		model.addAttribute("realTimeFiles", realTimeFiles);
 		model.addAttribute("realTimeContents", realTimeContents);
 		model.addAttribute("auctionFiles", auctionFiles);
 		model.addAttribute("auctionContents", auctionContents);
 		model.addAttribute("articles", articles);
		
		return "usr/home/main";
	}
	
	@RequestMapping("/")
	public String showRoot(Model model) {
		
		List<RealTime> realTimeContents = realTimeService.getRealTimeContentsForHome(0);
		List<FileVO> realTimeFiles = new ArrayList<>();
 		for(RealTime rt : realTimeContents) {
 			FileVO file = fileService.getContentsFirstFile(2, rt.getId());
 			realTimeFiles.add(file);
 		}
		List<RealTime> startedRealTime = realTimeService.getRealTimeContentsForHome(1);
		List<FileVO> startedRealTimeFiles = new ArrayList<>();
 		for(RealTime rt : startedRealTime) {
 			FileVO file = fileService.getContentsFirstFile(2, rt.getId());
 			realTimeFiles.add(file);
 		}
		List<Auction> auctionContents = auctionService.getAuctionContentsForHome(0);
		List<FileVO> auctionFiles = new ArrayList<>();
 		for(Auction auction : auctionContents) {
 			FileVO file = fileService.getContentsFirstFile(1, auction.getId());
 			auctionFiles.add(file);
 		}
 		
 		List<Article> articles = articleService.getArticlesForHome();
 		
 		model.addAttribute("realTimeFiles", realTimeFiles);
 		model.addAttribute("realTimeContents", realTimeContents);
 		model.addAttribute("startedRealTimeFiles", startedRealTimeFiles);
 		model.addAttribute("startedRealTime", startedRealTime);
 		model.addAttribute("auctionFiles", auctionFiles);
 		model.addAttribute("auctionContents", auctionContents);
 		model.addAttribute("articles", articles);
		
		return "redirect:/usr/home/main";
	}

}