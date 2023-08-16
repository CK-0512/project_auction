<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="온라인 경매" />
<%@ include file="../common/commonHead.jsp" %>

	<section class="mt-8">
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
					<c:forEach var="auction" items="${auctionContents }">
						<div class="contents-box">
							<div>
								<img src="/usr/home/file/${file.id }">
							</div>
							<div>
								<span>${auction.name }</span>
							</div>
							<div>
								<span>현재가 : ${auction.nowBid }</span>
							</div>
							<c:if test="${auction.buyNow } != 0">
								<div>
									<span>즉시구매가 : ${auction.buyNow }</span>
								</div>
							</c:if>
							<div>
								<span id="remainTime-${auction.id }">Loading...</span>
						    </div>
						 </div>
						
						    <script>
						        var socket = new WebSocket("ws://localhost:8081/ws");
						        var stompClient = Stomp.over(socket);
						
						        stompClient.connect({}, function (frame) {
						            stompClient.subscribe('/topic/auction/remainTime', function (message) {
						                var remainTimeMessage = JSON.parse(message.body);
						                if (remainTimeMessage.auctionId === ${auction.id }) {
						                    var remainTimeElement = document.getElementById('remainTime-${auction.id}');
						                    remainTimeElement.innerText =
						                        remainTimeMessage.hours + "h " +
						                        remainTimeMessage.minutes + "m " +
						                        remainTimeMessage.seconds + "s";
						                }
						            });
						        });
						    </script>
					</c:forEach>
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