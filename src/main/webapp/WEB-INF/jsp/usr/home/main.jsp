<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="CK Auction" />
<%@ include file="../common/commonHead.jsp"%>

<section class="flex">
	<div class="container mx-auto">
		<div class="text-center font-extrabold text-5xl italic ml-72 pl-24">C K A</div>
		<div class="flex flex-grow flex-col justify-between ml-6">
			<div class="mt-6">
				<div class="text-center text-xl">진행중인 경매</div>
				<div
					class="flex flex-wrap flex-grow border-2 rounded-md border-gray-400 p-2 m-2">
					<c:if test="${startedRealTime.size() != 0 }">
						<c:forEach var="startedRealTime" items="${startedRealTime }">
							<div class="contents-box w-1/6 h-1/2 p-2 mb-2 mx-6">
								<c:forEach var="file" items="${startedRealTimeFiles }">
									<c:if test="${startedRealTime.id == file.auctionId }">
										<a href="/usr/realTime/detail?id=${startedRealTime.id }"
											class="flex h-3/5 justify-center"> <img class="h-48"
											src="/usr/home/file/${file.id }">
										</a>
									</c:if>
								</c:forEach>
								<div class="text-center font-thin">
									<span>${startedRealTime.name }</span>
								</div>
								<div class="text-center text-red-500 font-medium">
									<span>현재가 ${startedRealTime.nowBid }원</span>
								</div>
								<div>
									<span>입찰된 횟수 : ${startedRealTime.bidCount }</span>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${startedRealTime.size() == 0 }">
						<div class="flex justify-center items-center w-full h-16">
							<span class="text-red-500 font-bold text-2xl">진행중인 경매가
								없습니다.</span>
						</div>
					</c:if>
				</div>
			</div>
			<div class="mt-6">
				<div class="text-center text-xl">예정된 경매</div>
				<div
					class="flex flex-wrap flex-grow border-2 rounded-md border-gray-400 p-2 m-2">
					<c:if test="${realTimeContents.size() != 0 }">
						<c:forEach var="realTime" items="${realTimeContents }">
							<div class="contents-box w-1/6 h-1/2 p-2 mb-2 mx-6">
								<c:forEach var="file" items="${realTimeFiles }">
									<c:if test="${realTime.id == file.auctionId }">
										<a href="/usr/realTime/detail?id=${realTime.id }"
											class="flex h-3/5 justify-center"> <img class="h-48"
											src="/usr/home/file/${file.id }">
										</a>
									</c:if>
								</c:forEach>
								<div class="text-center font-thin">
									<span>${realTime.name }</span>
								</div>
								<div class="text-center text-red-500 font-medium">
									<span>시작가 : ${realTime.startBid }원</span>
								</div>
								<div class="text-center">
									<span>시작일 : ${realTime.startDate.substring(2, 16) }</span>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${realTimeContents.size() == 0 }">
						<div class="flex justify-center items-center w-full">
							<span class="text-red-500 font-bold text-2xl">예정된 경매가
								없습니다.</span>
						</div>
					</c:if>
				</div>
			</div>
			<div class="mt-6">
				<div class="text-center text-xl">인기 상품</div>
				<div
					class="flex flex-wrap flex-grow border-2 rounded-md border-gray-400 p-2 m-2">
					<c:if test="${auctionContents.size() != 0 }">
						<c:forEach var="auction" items="${auctionContents }">
							<div class="contents-box w-1/6 h-1/2 p-2 mb-2 mx-6">
								<c:forEach var="file" items="${auctionFiles }">
									<c:if test="${auction.id == file.auctionId }">
										<a href="/usr/auction/detail?id=${auction.id }"
											class="flex h-3/5 justify-center"> <img class="h-48"
											src="/usr/home/file/${file.id }">
										</a>
									</c:if>
								</c:forEach>
								<div class="text-center font-thin">
									<span>${auction.name }</span>
								</div>
								<div class="text-center text-red-500 font-medium">
									<span>현재가 ${auction.nowBid }원</span>
								</div>
								<div class="text-center">
									<span>입찰된 횟수 : ${auction.bidCount }</span>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${auctionContents.size() == 0 }">
						<div class="flex justify-center items-center w-full">
							<span class="text-red-500 font-bold font-2xl">등록된 상품이
								없습니다.</span>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<div class="h-full mt-24 mx-8 px-4 border-2 rounded-xl border-green-200 w-1/5">
		<ul class="border-b-2 pt-4 pb-2 border-green-200 text-center">
			<c:if test="${rq.getLoginedMemberId() == 0}">
				<li class="hover:underline"><a class="h-full align-center"
					href="/usr/member/join">회원가입</a></li>
				<li class="hover:underline"><a class="h-full align-center"
					href="/usr/member/login">로그인</a></li>
			</c:if>
			<c:if test="${rq.getLoginedMemberId() != 0}">
				<c:choose>
					<c:when test="${rq.getLoginedMember().authLevel != 3 }">
						<li class="hover:underline"><a class="h-full align-center"
							href="/usr/member/myPage">내정보</a></li>
					</c:when>
					<c:otherwise>
						<li class="hover:underline"><a class="h-full align-center"
							href="/adm/member/list">회원관리</a></li>
					</c:otherwise>
				</c:choose>
				<li class="hover:underline"><a class="h-full align-center"
					href="/usr/member/doLogout">로그아웃</a></li>
			</c:if>
		</ul>
		<ul class="py-4 -mx-2">
			<li><span class="text-red-500 font-bold text-lg mx-2">화제글</span></li>
			<c:forEach var="article" items="${articles }">
				<li class="border-2 rounded-md border-red-300 p-1 m-2"><a
					class="hover:underline overflow-hidden"
					href="/usr/article/detail?id=${article.id }">${article.title }</a></li>
			</c:forEach>
		</ul>
	</div>
</section>

<%@ include file="../common/foot.jsp"%>