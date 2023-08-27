<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="${realTime.name }" />
<%@ include file="../common/headWithToastUIEditorLib.jsp"%>

<script type="text/javascript">
	function confirmApproval(id) {
		let confirmed = confirm("이 경매를 승인하시겠습니까?");
		if(confirmed) {
			location.replace("usr/realTime/doConfirm?id=" + id);
		}
	}
</script>

<section class="mt-8">
	<div class="container mx-auto pb-5 border-bottom-line">
		<div class="table-box-type-1">
			<table class="table">
				<colgroup>
					<col width="200" />
				</colgroup>
				<tbody>
					<tr>
						<th>제품명</th>
						<td>${realTime.name }</td>
						<th>판매자</th>
						<td>${realTime.memberName }</td>
					</tr>
					<c:if test="${realTime.confirmStatus != 1 }">
						<tr>
							<c:if test="${realTime.confirmStatus == 0 }">
								<th>등록일</th>
								<td colspan="3">${realTime.regDate }</td>
							</c:if>
							<c:if test="${realTime.confirmStatus == 2 }">
								<th>경매 시작일</th>
								<td colspan="3">${realTime.startDate }</td>
							</c:if>
						</tr>
					</c:if>
					<tr>
						<th>경매 시작가</th>
						<td colspan="3">${realTime.startBid }원</td>
					</tr>
					<c:if test="${realTime.endStatus == 1 }">
						<tr>
							<th>낙찰가</th>
							<td>${realTime.endBid }원</td>
							<th>입찰건수</th>
							<td>${realTime.bidCount }회</td>
						</tr>
					</c:if>
					<tr>
						<th>설명</th>
						<td colspan="3">
							<div class="toast-ui-viewer">
								<script type="text/x-template">${auction.description }</script>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="mt-2 flex justify-end">
			<button class="btn btn-accent btn-sm" onclick="history.back();">뒤로가기</button>

			<c:if test="${realTime.memberId == rq.getLoginedMemberId() }">
				<a class="btn btn-accent btn-sm ml-1" href="modify?id=${realTime.id}">수정</a>
			</c:if>
			<c:if test="${rq.loginedMember.authLebel == 3 }">
				<a class="btn btn-accent btn-sm ml-1" href="javascript:confirmApproval(${realTime.id })">승인</a>
			</c:if>
		</div>
	</div>
</section>


<%@ include file="../common/foot.jsp"%>