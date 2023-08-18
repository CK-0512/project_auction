<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="${auction.name }" />
<%@ include file="../common/headWithToastUIEditorLib.jsp" %>

<script>
	function bid(auctionId, money) {
		const bid = bidForm.bid.value.trim();
		
		if (bid) {
			alert("입찰금액을 입력해주세요.");
			return;
		}
		
		if (money < bid) {
			let isConfirmed = confirm("현재 충전된 금액이 부족합니다. 충전하시겠습니까?");
			if (isConfirmed) {
				window.open
			}
		}
	}
</script>

	<section class="mt-8">
		<div class="container mx-auto pb-5 border-bottom-line">
			<div class="table-box-type-1">
				<form method="post" name="bidForm">
					<table class="table">
						<colgroup>
							<col width="200" />
						</colgroup>
						<tbody>
							<tr>
								<th>제품명</th>
								<td>${auction.name }</td>
								<th>판매자</th>
								<td>${auction.memberName }</td>
							</tr>
							<tr>
								<th>경매 시작일</th>
								<td>${article.regDate }</td>
								<th>종료까지</th>
								<td>
									<span id="auctionId" data-id="${auction.id}"></span>
									<span id="remainTime-${auction.id }"></span>
								</td>
							</tr>
							<tr>
								<th>현재가</th>
								<td>${auction.nowBid }</td>
								<th>입찰 횟수</th>
								<td><span id="articleDetail_increaseHitCnt">${auction.bidCount }</span></td>
							</tr>
							<tr>
								<th>입찰</th>
								<td>
									<input class="ml-2 input input-bordered input-accent input-sm w-32 align-right" name="bid" type="text" placeholder="${auction.nowBid + auction.minimumBid}원 이상부터 입찰이 가능합니다."/>
									<button class="ml-2 btn btn-accent btn-sm" onClick="bid(${auction.id }, ${rq.loginedMember.money })">입찰하기</button>
								</td>
								<th>즉시구매</th>
								<td>
									<c:if test="${auction.buyNow != 0 }">
										<span>${auction.buyNow }원</span>
										<button class="ml-2 btn btn-accent btn-sm">구매하기</button>
									</c:if>
									<c:if test="${auction.buyNow == 0 }">
										<span class="text-red-500 font-sm font-bold">즉시 구매가 불가능한 제품입니다.</span>
									</c:if>
								</td>
							</tr>
							<tr>
								<th>설명</th>
								<td>
									<div class="toast-ui-viewer">
	  									<script type="text/x-template">${auction.description }</script>
	  								</div>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div class="mt-2">
				<button class="btn btn-accent btn-sm" onclick="history.back();">뒤로가기</button>
				
				<c:if test="${auction.memberId == rq.getLoginedMemberId() }">
					<a class="btn btn-accent btn-sm" href="modify?id=${auction.id}">수정</a>
				</c:if>
			</div>
		</div>
	</section>
	
	<script>
		const auctionId = document.getElementById('auctionId').getAttribute('data-id');

		const socket = new WebSocket("ws://localhost:8081/auctionSocket?auctionId=" + auctionId);						   		
    
		socket.onmessage = (event) => {
	        const data = JSON.parse(event.data);
	        const auctionId = data.auctionId;
	        const remainingTime = data.remainingTime;
	
	        const timerSpan = document.getElementById(`remainTime-${auctionId}`);
	        if (timerSpan) {
	            updateCountdownTimer(timerSpan, remainingTime);
	        }
   	 	};
    
	    function updateCountdownTimer(element, remainingTime) {
	        const days = Math.floor(remainingTime / (60 * 60 * 24));
	        const hours = Math.floor((remainingTime % (60 * 60 * 24)) / (60 * 60));
	        const minutes = Math.floor((remainingTime % (60 * 60)) / 60);
	        const seconds = remainingTime % 60;
	
	        element.innerHTML = `${days}d ${hours}h ${minutes}m ${seconds}s`;
	
	        remainingTime--;
	        
	        if (remainingTime >= 0) {
	            setTimeout(() => {
	                updateCountdownTimer(element, remainingTime);
	            }, 1000);
	        }
	    }
	</script>

	
<%@ include file="../common/foot.jsp" %>