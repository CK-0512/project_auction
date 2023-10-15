<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="실시간 경매" />
<%@ include file="../common/commonHead.jsp" %>

	<section>
		<div class="container mx-auto">
			<c:if test="${rq.loginedMember.authLevel == '3' }">
				<div>
					<a href="list?confirmStatus=0" class="${confirmStatus == '0' ? 'selected' : ''}">승인 대기중</a>
				</div>
			</c:if>
			<div>
				<a href="list?endStatus=0" class="${endStatus == '0' && confirmStatus != '0' ? 'selected' : ''}">대기중인 경매</a>
				<span> ||| </span>
				<a href="list?endStatus=1" class="${endStatus == '1' && confirmStatus != '0' ? 'selected' : ''}">진행중인 경매</a>
				<span> ||| </span>
				<a href="list?endStatus=2" class="${endStatus == '2' && confirmStatus != '0' ? 'selected' : ''}">종료된 경매</a>
			</div>
			<div class="mb-2 flex">
				<div class="table-box-type-2">
					<table class="table w-28 h-full">
						<tr>
							<td class="text-center">
								<a href="list?endStatus=${endStatus }">모든품목</a>
							</td>
						</tr>
						<c:forEach var="category" items="${categories }">
							<tr>
								<td class="text-center">
									<a href="list?endStatus=${endStatus }&categoryId=${category.id }">${category.name }</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="flex flex-wrap flex-grow">
					<c:choose>
						<c:when test="${confirmStatus == '0' }">
							<c:forEach var="realTime" items="${realTimeContents }">
								<div id="auctionContent" class="contents-box w-1/5 h-1/2 p-2 mb-2 mx-6">
									<c:forEach var="file" items="${files }">
										<c:if test="${realTime.id == file.auctionId }">
											<a href="../../adm/realTime/detail?id=${realTime.id }" class="flex h-3/5 justify-center">
												<img src="/usr/home/file/${file.id }">
											</a>
										</c:if>
									</c:forEach>
									<div class="text-center font-thin">
										<span>${realTime.name }</span>
									</div>
									<div class="text-center text-red-500 font-medium">
										<span>신청인 : ${realTime.memberName }</span>
									</div>
								 </div>
							</c:forEach>
						</c:when>
						<c:when test="${endStatus == '0' }">
							<c:forEach var="realTime" items="${realTimeContents }">
								<div id="auctionContent" class="contents-box w-1/5 h-1/2 p-2 mb-2 mx-6">
									<c:forEach var="file" items="${files }">
										<c:if test="${realTime.id == file.auctionId }">
											<a href="detail?id=${realTime.id }" class="flex h-3/5 justify-center">
												<img src="/usr/home/file/${file.id }">
											</a>
										</c:if>
									</c:forEach>
									<div class="text-center font-thin">
										<span>${realTime.name }</span>
									</div>
									<div class="text-center font-medium">
										<span>경매 일자 : ${realTime.startDate }</span>
								    </div>
									<div class="text-center text-green-500 font-bold">
										<span>시작가 : ${realTime.startBid }</span>
									</div>
								 </div>
							</c:forEach>
						</c:when>
						<c:when test="${endStatus == '1' }">
							<c:forEach var="realTime" items="${realTimeContents }">
								<div id="auctionContent" class="contents-box w-1/5 h-1/2 p-2 mb-2 mx-6">
									<c:forEach var="file" items="${files }">
										<c:if test="${realTime.id == file.auctionId }">
											<a href="detail?id=${realTime.id }" class="flex h-3/5 justify-center">
												<img src="/usr/home/file/${file.id }">
											</a>
										</c:if>
									</c:forEach>
									<div class="text-center font-thin">
										<span>${realTime.name }</span>
									</div>
									<div class="text-center font-medium">
										<span>현재 ${realTime.startDate }명 참가중</span>
								    </div>
									<div class="text-center text-red-500 font-bold">
										<span>현재가 : ${realTime.nowBid }</span>
									</div>
								 </div>
							</c:forEach>
						</c:when>
						<c:when test="${endStatus == '2' }">
							<c:forEach var="realTime" items="${realTimeContents }">
								<div id="auctionContent" class="contents-box w-1/5 h-1/2 p-2 mb-2 mx-6">
									<c:forEach var="file" items="${files }">
										<c:if test="${realTime.id == file.auctionId }">
											<a href="detail?id=${realTime.id }" class="flex h-3/5 justify-center">
												<img src="/usr/home/file/${file.id }">
											</a>
										</c:if>
									</c:forEach>
									<div class="text-center font-thin">
										<span>${realTime.name }</span>
									</div>
									<div class="text-center font-medium">
										<span>입찰건수 : ${realTime.bidCount }</span>
								    </div>
									<div class="text-center text-blue-500 font-bold">
										<span>낙찰가 : ${realTime.startBid }</span>
									</div>
								 </div>
							</c:forEach>
						</c:when>
					</c:choose>
				</div>
			</div>
			
			<c:if test="${rq.getLoginedMemberId() != 0 }">
				<div class="mt-2 flex justify-end">
					<a class="btn btn-accent btn-sm" href="regist">경매 신청</a>
				</div>
			</c:if>
			
			<div class="mt-2 flex justify-center">
				<div class="join">
					<c:set var="pageMenuLen" value="5" />
					<c:set var="startPage" value="${page - pageMenuLen >= 1 ? page - pageMenuLen : 1 }" />
					<c:set var="endPage" value="${page + pageMenuLen <= pagesCnt ? page + pageMenuLen : pagesCnt }" />
					
					<c:set var="pageBaseUri" value="?categoryId=${selectedCategory.size() == 1 ? selectedCategory[0].id : 0}&endStatus=${endStatus }&searchKeyword=${searchKeyword }" />
					
					<c:if test="${page == 1 }">
						<a class="join-item btn btn-disabled">«</a>
						<a class="join-item btn btn-disabled">&lt;</a>
					</c:if>
					<c:if test="${page > 1 }">
						<a class="join-item btn" href="${pageBaseUri }&page=1">«</a>
						<a class="join-item btn" href="${pageBaseUri }&page=${page - 1 }">&lt;</a>
					</c:if>
					<c:forEach begin="${startPage }" end="${endPage }" var="i">
						<a class="join-item btn ${page == i ? 'btn-active' : '' }" href="${pageBaseUri }&page=${i }">${i }</a>
					</c:forEach>
					<c:if test="${page < pagesCnt }">
						<a class="join-item btn" href="${pageBaseUri }&page=${page + 1 }">&gt;</a>
						<a class="join-item btn" href="${pageBaseUri }&page=${pagesCnt }">»</a>
					</c:if>
					<c:if test="${page == pagesCnt }">
						<a class="join-item btn btn-disabled">&gt;</a>
						<a class="join-item btn btn-disabled">»</a>
					</c:if>
				</div>
			</div>
		</div>
	</section>
	
<%@ include file="../common/foot.jsp" %>