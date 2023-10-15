<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="${auction.name }" />
<%@ include file="../common/headWithToastUIEditorLib.jsp" %>

<script>
	function doBid(money) {
		const bid = auctionForm.bid;
		let bidValue = bid.value.trim();
		
		if (!bidValue) {
			alert("입찰금액을 입력해주세요.");
			bid.focus();
			return;
		}
		
		if (money < bidValue) {
			let isConfirmed = confirm("잔액이 부족합니다. 충전하시겠습니까?");
			if (isConfirmed) {
				window.open('/usr/member/chargeMoney', 'chargeMoney', 'top=200, left=300, width=600, height=300');
				return;
			}
		}
		
		let isBidConfirmed = confirm("상품을 " + bidValue + "원에 입찰합니다.");
		if (isBidConfirmed) {
			auctionForm.action = "doBid";
			auctionForm.submit();
		}
		
	}
	
	function doBuy(money, buyNow) {
		
		if (money < buyNow) {
			let isConfirmed = confirm("잔액이 부족합니다. 충전하시겠습니까?");
			if (isConfirmed) {
				window.open('chargeMoney', 'chargeMoney', 'top=200, left=300, width=600, height=300');
				return;
			}
		}
		
		let isBuyConfirmed = confirm("상품을 즉시구매합니다");
		if (isBuyConfirmed) {
			auctionForm.action = "doBuy";
			auctionForm.submit();
		}
	}
</script>

	<section>
		<div class="container mx-auto pb-5 border-bottom-line">
			<div class="table-box-type-1">
				<form method="post" name="auctionForm">
					<input type="hidden" name="auctionId" value="${auction.id }"/>
					<input type="hidden" name="buyNow" value="${auction.buyNow }"/>
					<table class="table">
						<colgroup>
							<col width="200" />
						</colgroup>
						<tbody>
							<tr>
								<th>상품명</th>
								<td>${auction.name }</td>
								<th>판매자</th>
								<td>${auction.memberName }</td>
							</tr>
							<tr>
								<th>상품사진</th>
								<td colspan="3">
									<c:forEach var="file" items="${files }">
										<div class="w-96">
											<img src="/usr/home/file/${file.id }">
										</div>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th>경매 시작일</th>
								<td>${auction.regDate }</td>
								<c:if test="${auction.endStatus == 0 }">
									<th>종료까지</th>
									<td>
										<div class="grid grid-flow-col gap-5 text-center auto-cols-max">
							                <div class="flex flex-col p-2 bg-neutral rounded-box text-neutral-content">
							                    <span class="countdown font-mono text-5xl">
							                        <span id="days" style="--value:0;"></span>
							                    </span>
							                    days
							                </div>
							                <div class="flex flex-col p-2 bg-neutral rounded-box text-neutral-content">
							                    <span class="countdown font-mono text-5xl">
							                        <span id="hours" style="--value:0;"></span>
							                    </span>
							                    hours
							                </div>
							                <div class="flex flex-col p-2 bg-neutral rounded-box text-neutral-content">
							                    <span class="countdown font-mono text-5xl">
							                        <span id="minutes" style="--value:0;"></span>
							                    </span>
							                    min
							                </div>
							                <div class="flex flex-col p-2 bg-neutral rounded-box text-neutral-content">
							                    <span class="countdown font-mono text-5xl">
							                        <span id="seconds" style="--value:0;"></span>
							                    </span>
							                    sec
							                </div>
							            </div>
									</td>
								</c:if>
								<c:if test="${auction.endStatus == 1 }">
									<th>종료일자</th>
									<td>${auction.endDate }</td>
								</c:if>
							</tr>
							<tr>
								<c:if test="${auction.endStatus == 0 }">
									<th>현재가</th>
									<td>${auction.nowBid }</td>
									<th>입찰건수</th>
									<td><span id="articleDetail_increaseHitCnt">${auction.bidCount }</span></td>
								</c:if>
								<c:if test="${auction.endStatus == 1 }">
									<th>낙찰가</th>
									<td>${auction.endBid }원</td>
									<th>입찰건수</th>
									<td><span id="articleDetail_increaseHitCnt">${auction.bidCount }회</span></td>
								</c:if>
							</tr>
							<c:if test="${auction.endStatus == 0 }">
								<tr>
									<th>입찰하기</th>
									<td>
										<input class="ml-2 input input-bordered input-accent input-sm w-56 text-right" name="bid" type="text" placeholder="${auction.nowBid + auction.minimumBid} 이상부터 입찰가능"/>
										<input type="button" class="ml-2 btn btn-active btn-accent btn-sm" onClick="doBid(${rq.loginedMember.money })" value="입찰하기"/>
									</td>
									<th>즉시구매가</th>
									<td>
										<c:if test="${auction.buyNow != 0 }">
											<span>${auction.buyNow }</span>
											<input type="button" class="ml-2 btn btn-active btn-accent btn-sm" onClick="doBuy(${rq.loginedMember.money }, ${auction.buyNow })" value="구매하기"/>
										</c:if>
										<c:if test="${auction.buyNow == 0 }">
											<span class="text-red-500 font-sm font-bold">즉시 구매가 불가능한 상품입니다.</span>
										</c:if>
									</td>
								</tr>
							</c:if>
							<tr>
								<th>상품설명</th>
								<td colspan="3">
									<div class="toast-ui-viewer">
	  									<script type="text/x-template">${auction.description }</script>
	  								</div>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div class="mt-2 flex justify-end">
				<button class="btn btn-accent btn-sm" onclick="history.back();">뒤로가기</button>
				
				<c:if test="${auction.memberId == rq.getLoginedMemberId() }">
					<a class="btn btn-accent btn-sm ml-1" href="modify?id=${auction.id}">수정</a>
				</c:if>
			</div>
		</div>
	</section>
	
	<script>
		let remainingTime = calculateRemainingTime("${auction.endDate}");
	
	    function calculateRemainingTime(endDateString) {
	        const currentTime = new Date();
	        const endTime = new Date(endDateString.replace(/-/g, '/'));
	        const remainingTimeInSeconds = Math.max(0, Math.floor((endTime - currentTime) / 1000));
	        return remainingTimeInSeconds;
	    }
	
	    function updateCountdownTimer() {
	        const daysElement = document.getElementById(`days`);
	        const hoursElement = document.getElementById(`hours`);
	        const minutesElement = document.getElementById(`minutes`);
	        const secondsElement = document.getElementById(`seconds`);
	
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
	</script>

	
<%@ include file="../common/foot.jsp" %>