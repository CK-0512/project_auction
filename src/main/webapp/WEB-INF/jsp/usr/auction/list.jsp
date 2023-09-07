<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="온라인 경매" />
<%@ include file="../common/commonHead.jsp" %>

	<section>
		<div class="container mx-auto">
			<div>
				<a href="list?endStatus=0" class="${endStatus == '0' ? 'selected' : ''}">진행중인 경매</a>
				<span>|</span>
				<a href="list?endStatus=1" class="${endStatus == '1' ? 'selected' : ''}">종료된 경매</a>
			</div>
			<div class="mb-2 flex">
				<div class="table-box-type-2">
					<table class="table">
						<tr>
							<td>
								<a href="list?endStatus=${endStatus }" class="${categoryId == 0 ? 'selected' : ''}">모든품목</a>
							</td>
						</tr>
						<c:forEach var="category" items="${categories }">
							<tr>
								<td>
									<a href="list?endStatus=${endStatus }&categoryId=${category.id }" class="${categoryId == category.id ? 'selected' : ''}">${category.name }</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="flex">
					<c:if test="${endStatus == '0'}">
						<c:forEach var="auction" items="${auctionContents }">
							<div class="contents-box">
								<c:forEach var="file" items="${files }">
									<c:if test="${auction.id == file.auctionId }">
										<a href="detail?id=${auction.id }">
											<img src="/usr/home/file/${file.id }">
										</a>
									</c:if>
								</c:forEach>
								<div>
									<span>상품명 : ${auction.name }</span>
								</div>
								<div>
									<span>현재가 : ${auction.nowBid }원</span>
								</div>
								<c:if test="${auction.buyNow } != 0">
									<div>
										<span>즉시구매가 : ${auction.buyNow }</span>
									</div>
								</c:if>
								<div>
									<span id="auctionId" data-id="${auction.id}"></span>
									<span id="remainTime-${auction.id }"></span>
							    </div>
							 </div>
						</c:forEach>
					</c:if>
					
					<c:if test="${endStatus == '1'}">
						<c:forEach var="auction" items="${auctionContents }">
							<div class="contents-box">
								<c:forEach var="file" items="${files }">
									<c:if test="${auction.id == file.auctionId }">
										<a href="detail?id=${auction.id }">
											<img src="/usr/home/file/${file.id }">
										</a>
									</c:if>
								</c:forEach>
								<div>
									<span>상품명 : ${auction.name }</span>
								</div>
								<div>
									<span>낙찰가 : ${auction.endBid }원</span>
								</div>
								<div>
									<span>최종입찰건수 : ${auction.bidCount }회</span>
								</div>
								<div>
									<span>종료일시 : ${auction.endDate }</span>
							    </div>
							 </div>
						</c:forEach>
					</c:if>
				</div>
			</div>
			
			<c:if test="${rq.getLoginedMemberId() != 0 }">
				<div class="mt-2 flex justify-end">
					<a class="btn btn-accent btn-sm" href="regist">경매 등록</a>
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