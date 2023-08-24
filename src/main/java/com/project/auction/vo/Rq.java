package com.project.auction.vo;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.project.auction.service.CartService;
import com.project.auction.service.CategoryService;
import com.project.auction.service.MemberService;
import com.project.auction.util.Util;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;
	@Getter
	private List<Category> interestCategories;
	@Getter
	private int cartSize;
	
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	
	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService, CategoryService categoryService, CartService cartService) {
		
		this.req = req;
		this.resp = resp;
		
		this.session = req.getSession();
		
		int loginedMemberId = 0;
		Member loginedMember = null;
		List<Category> interestCategories = null;
		int cartSize = 0;
		
		if(session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
			interestCategories = categoryService.getInterestCategories(loginedMemberId);
			cartSize = cartService.getCartCntWithOutEither(loginedMemberId);
		}
		
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;
		this.interestCategories = interestCategories;
		this.cartSize = cartSize;
		
		this.req.setAttribute("rq", this);
		
	}

	public void jsPrintHistoryBack(String msg) {
		this.resp.setContentType("text/html; charset=UTF-8;");
		
		print(Util.jsHistoryBack(msg));
		
	}
	
	private void print(String str) {
		
		try {
			this.resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void login(Member member) {
		this.session.setAttribute("loginedMemberId", member.getId());
	}

	public void logout() {
		this.session.removeAttribute("loginedMemberId");
	}

	public String jsReturnOnView(String msg) {
		this.req.setAttribute("msg", msg);
		return "usr/common/js";
	}

	public void init() {
		
	}
}