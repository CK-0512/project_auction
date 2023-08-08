<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="CK Auction" />
<%@ include file="../common/commonHead.jsp" %>
	
	<section class="mt-8">
		<div class="container mx-auto">
			<div class="text-center font-extrabold text-5xl italic">C K A</div>
			<div class="flex">
				<div class="flex flex-grow flex-col justify-between">
					<div>
						<div class="text-center">진행중인 경매</div>
						<div class="flex">
							<div>품목들</div>
							<div>품목들</div>
							<div>품목들</div>
							<div>품목들</div>
						</div>
					</div>
					<div>
						<div class="text-center">예정된 경매</div>
						<div class="flex">
							<div>품목들</div>
							<div>품목들</div>
							<div>품목들</div>
							<div>품목들</div>
						</div>
					</div>
					<div>
						<div class="text-center">인기 상품</div>
						<div class="flex">
							<div>품목들</div>
							<div>품목들</div>
							<div>품목들</div>
							<div>품목들</div>
						</div>
					</div>
				</div>
				<div class="w-40">
					<ul>
						<c:if test="${rq.getLoginedMemberId() == 0}">
							<li class="hover:underline"><a class="h-full px-3 flex items-center" href="/usr/member/join">회원가입</a></li>
							<li class="hover:underline"><a class="h-full px-3 flex items-center" href="/usr/member/login">로그인</a></li>
						</c:if>
						<c:if test="${rq.getLoginedMemberId() != 0}">
							<c:choose>
								<c:when test="${rq.getLoginedMember().authLevel != 3 }">
									<li><a class="h-full px-5 flex items-center" href="/usr/member/myPage">내정보</a></li>
								</c:when>
								<c:otherwise>
									<li><a class="h-full px-5 flex items-center" href="/adm/member/list">회원관리</a></li>
								</c:otherwise>
							</c:choose>
							<li class="hover:underline"><a class="h-full px-5 flex items-center" href="/usr/member/doLogout">로그아웃</a></li>
						</c:if>
					</ul>
					<ul>
						<li><span>실시간 화제글</span></li>
						<li>글</li>
						<li>글</li>
						<li>글</li>
						<li>글</li>
						<li>글</li>
						<li>글</li>
					</ul>
				</div>
			</div>
		</div>
	</section>

<%@ include file="../common/foot.jsp" %>