<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="장바구니" />
<%@ include file="../common/commonHead.jsp"%>

<section>
	<div class="container mx-auto">
		<div>
			<a href="list?endStatus=0"
				class="${endStatus == '0' ? 'selected' : ''}">참가중인 경매</a> <span>|</span>
			<a href="list?endStatus=1"
				class="${endStatus == '1' ? 'selected' : ''}">상품 구매 목록</a>
		</div>
		<div class="table-box-type-1">
			<table class="table">
				<c:if test="${endStatus == '0' }">
					<thead>
						<tr>
							<th>상품번호</th>
							<th>상품사진</th>
							<th>상품명</th>
							<th>본인 입찰가</th>
							<th>현재가</th>
							<th>즉시구매가</th>
							<th>종료까지</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="cart" items="${carts }">
							<tr>
								<td>${cart.auctionId }</td>
								<td><c:forEach var="file" items="${files }">
										<c:if test="${cart.auctionId == file.auctionId }">
											<a href="/usr/auction/detail?id=${cart.auctionId }"> <img
												src="/usr/home/file/${file.id }" class="h-24">
											</a>
										</c:if>
									</c:forEach></td>
								<td>${cart.name }</td>
								<td>${cart.memberBid }원</td>
								<td>${cart.nowBid }원</td>
								<c:if test="${cart.buyNow != 0 }">
									<td>${cart.buyNow }원</td>
								</c:if>
								<c:if test="${cart.buyNow == 0}">
									<td><span class="text-red-500">즉시구매가 불가능한 상품입니다.</span></td>
								</c:if>
								<td>
									<div class="grid grid-flow-col gap-5 text-center auto-cols-max">
										<div class="flex flex-col p-2 bg-neutral rounded-box text-neutral-content">
											<span class="countdown font-mono text-5xl"> <span
												id="days-${cart.auctionId }" style="--value: 0;"></span>
											</span> days
										</div>
										<div class="flex flex-col p-2 bg-neutral rounded-box text-neutral-content">
											<span class="countdown font-mono text-5xl"> <span
												id="hours-${cart.auctionId }" style="--value: 0;"></span>
											</span> hours
										</div>
										<div class="flex flex-col p-2 bg-neutral rounded-box text-neutral-content">
											<span class="countdown font-mono text-5xl"> <span
												id="minutes-${cart.auctionId }" style="--value: 0;"></span>
											</span> min
										</div>
										<div class="flex flex-col p-2 bg-neutral rounded-box text-neutral-content">
											<span class="countdown font-mono text-5xl"> <span
												id="seconds-${cart.auctionId }" style="--value: 0;"></span>
											</span> sec
										</div>
									</div>
								</td>
							</tr>
							<script>
						    	function timerWork_${cart.auctionId}() {
						            let remainingTime = calculateRemainingTime("${cart.endDate}");
						            const auctionId = "${cart.auctionId}";
						            
						
						            function calculateRemainingTime(endDateString) {
						                const currentTime = new Date();
						                const endTime = new Date(endDateString.replace(/-/g, '/'));
						                const remainingTimeInSeconds = Math.max(0, Math.floor((endTime - currentTime) / 1000));
						                return remainingTimeInSeconds;
						            }
						
						            function updateCountdownTimer() {
						            	const daysName = "days-" + auctionId;
						            	const hoursName = "hours-" + auctionId;
						            	const minutesName = "minutes-" + auctionId;
						            	const secondsName = "seconds-" + auctionId;
						            	
						            	const daysElement = document.getElementById(daysName);
						    	        const hoursElement = document.getElementById(hoursName);
						    	        const minutesElement = document.getElementById(minutesName);
						    	        const secondsElement = document.getElementById(secondsName);
						            	
						                const days = Math.floor(remainingTime / (60 * 60 * 24));
						                const hours = Math.floor((remainingTime % (60 * 60 * 24)) / (60 * 60));
						                const minutes = Math.floor((remainingTime % (60 * 60)) / 60);
						                const seconds = remainingTime % 60;
						
						                if (daysElement) {
						    	            daysElement.style.setProperty("--value", days);
						    	        }
						    	        if (hoursElement) {
						    	            hoursElement.style.setProperty("--value", hours);
						    	        }
						    	        if (minutesElement) {
						    	            minutesElement.style.setProperty("--value", minutes);
						    	        }
						    	        if (secondsElement) {
						    	            secondsElement.style.setProperty("--value", seconds);
						    	        }
						    	
						    	        remainingTime--;
						    	
						    	        if (remainingTime >= 0) {
						    	            setTimeout(updateCountdownTimer, 1000);
						    	        }
						    	    }
						    	
						    	    updateCountdownTimer();
						    	}
						    	timerWork_${cart.auctionId}();
						    </script>
						</c:forEach>
					</tbody>
				</c:if>
				<c:if test="${endStatus == '1' }">
					<thead>
						<tr>
							<th>상품번호</th>
							<th>상품사진</th>
							<th>내용</th>
							<th>구매가</th>
							<th>수수료</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="cart" items="${carts }">
							<tr>
								<td>${cart.auctionId }</td>
								<td><c:forEach var="file" items="${files }">
										<c:if test="${cart.auctionId == file.auctionId }">
											<td><img src="/usr/home/file/${file.id }"></td>
										</c:if>
									</c:forEach></td>
								<td><span>${cart.name }</span> <span
									class="whitespace-nowrap overflow-hidden overflow-ellipsis">${cart.description }</span>
								</td>
								<td>${cart.endBid }원</td>
								<td>${cart.charge }원</td>
							</tr>
						</c:forEach>
					</tbody>
				</c:if>
			</table>
		</div>

		<div class="mt-2 flex justify-center">
			<div class="join">
				<c:set var="pageMenuLen" value="5" />
				<c:set var="startPage"
					value="${page - pageMenuLen >= 1 ? page - pageMenuLen : 1 }" />
				<c:set var="endPage"
					value="${page + pageMenuLen <= pagesCnt ? page + pageMenuLen : pagesCnt }" />

				<c:set var="pageBaseUri"
					value="?memberId=${rq.loginedMemberId }&endStatus=${endStatus }&searchKeyword=${searchKeyword }" />

				<c:if test="${page == 1 }">
					<a class="join-item btn btn-disabled">«</a>
					<a class="join-item btn btn-disabled">&lt;</a>
				</c:if>
				<c:if test="${page > 1 }">
					<a class="join-item btn" href="${pageBaseUri }&page=1">«</a>
					<a class="join-item btn" href="${pageBaseUri }&page=${page - 1 }">&lt;</a>
				</c:if>
				<c:forEach begin="${startPage }" end="${endPage }" var="i">
					<a class="join-item btn ${page == i ? 'btn-active' : '' }"
						href="${pageBaseUri }&page=${i }">${i }</a>
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

<%@ include file="../common/foot.jsp"%>