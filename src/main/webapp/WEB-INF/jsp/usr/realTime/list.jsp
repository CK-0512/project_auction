<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="온라인 경매" />
<%@ include file="../common/commonHead.jsp" %>

	<section>
		<div class="container mx-auto">
			<c:if test="${rq.loginedMember.authLevel == '3' }">
				<div>
					<a href="list?confirmStatus=0" class="${confirmStatus == '0' ? 'selected' : ''}">승인 대기중</a>
				</div>
			</c:if>
			<div>
				<a href="list?endStatus=0" class="${endStatus == '0' ? 'selected' : ''}">대기중인 경매</a>
				<span>|</span>
				<a href="list?endStatus=1" class="${endStatus == '1' ? 'selected' : ''}">진행중인 경매</a>
				<span>|</span>
				<a href="list?endStatus=2" class="${endStatus == '2' ? 'selected' : ''}">종료된 경매</a>
			</div>
			<div class="mb-2 flex">
				<div class="table-box-type-2">
					<table class="table">
						<tr>
							<td>
								<a href="list?endStatus=${endStatus }">모든품목</a>
							</td>
						</tr>
						<c:forEach var="category" items="${categories }">
							<tr>
								<td>
									<a href="list?endStatus=${endStatus }&categoryId=${category.id }">${category.name }</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="flex">
					<c:choose>
						<c:when test="${confirmStatus == '0' }">
							<c:forEach var="realTime" items="${realTimeContents }">
								<div class="contents-box">
									<c:forEach var="file" items="${files }">
										<c:if test="${cart.auctionId == file.auctionId }">
											<a href="detail?id=${realTime.id }">
												<img src="/usr/home/file/${file.id }">
											</a>
										</c:if>
									</c:forEach>
									<div>
										<span>${realTime.name }</span>
									</div>
									<div>
										<span>신청자 : ${realTime.memberName }</span>
									</div>
								 </div>
							</c:forEach>
						</c:when>
						<c:when test="${endStatus == '0' }">
							<c:forEach var="realTime" items="${realTimeContents }">
								<div class="contents-box">
									<c:forEach var="file" items="${files }">
										<c:if test="${cart.auctionId == file.auctionId }">
											<a href="detail?id=${realTime.id }">
												<img src="/usr/home/file/${file.id }">
											</a>
										</c:if>
									</c:forEach>
									<div>
										<span>${realTime.name }</span>
									</div>
									<div>
										<span>시작가 : ${realTime.startBid }</span>
									</div>
									<div>
										<span>경매 일시 : ${realTime.startDate }</span>
								    </div>
								 </div>
							</c:forEach>
						</c:when>
						<c:when test="${endStatus == '1' }">
							<c:forEach var="realTime" items="${realTimeContents }">
								<div class="contents-box">
									<c:forEach var="file" items="${files }">
										<c:if test="${cart.auctionId == file.auctionId }">
											<a href="detail?id=${realTime.id }">
												<img src="/usr/home/file/${file.id }">
											</a>
										</c:if>
									</c:forEach>
									<div>
										<span>${realTime.name }</span>
									</div>
									<div>
										<span>현재가 : ${realTime.nowBid }</span>
									</div>
									<div>
										<span>참가 인원수 : ${realTime.startDate }</span>
								    </div>
								 </div>
							</c:forEach>
						</c:when>
						<c:when test="${endStatus == '2' }">
							<c:forEach var="realTime" items="${realTimeContents }">
								<div class="contents-box">
									<c:forEach var="file" items="${files }">
										<c:if test="${cart.auctionId == file.auctionId }">
											<a href="detail?id=${realTime.id }">
												<img src="/usr/home/file/${file.id }">
											</a>
										</c:if>
									</c:forEach>
									<div>
										<span>${realTime.name }</span>
									</div>
									<div>
										<span>낙찰가 : ${realTime.startBid }</span>
									</div>
									<div>
										<span>최종 입찰건수 : ${realTime.bidCount }</span>
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