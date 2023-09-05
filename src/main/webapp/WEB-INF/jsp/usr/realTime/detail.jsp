<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="${realTime.name }" />
<%@ include file="../common/headWithToastUIEditorLib.jsp"%>

<script type="text/javascript">
	function deleteRealTime() {
		let confirmed = confirm("해당 신청을 취소해도 유예기간은 사라지지 않습니다. 정말 진행하시겠습니까?");
		if (confirmed) {
			confirmForm.action = "doDelete"
			confirmForm.submit();
		}
	}
</script>

<section>
	<div class="container mx-auto pb-5 border-bottom-line">
		<div class="table-box-type-1">
			<form method="post" name="confirmForm">
				<input type="hidden" name="id" value="${realTime.id }" />
				<table class="table">
					<colgroup>
						<col width="200" />
					</colgroup>
					<tbody>
						<tr>
							<th>상품명</th>
							<td>${realTime.name }</td>
							<th>판매자</th>
							<td>${realTime.memberName }</td>
						</tr>
						<tr>
							<th>상품사진</th>
							<td class="flex" colspan="3"><c:forEach var="file" items="${files }">
									<div>
										<img src="/usr/home/file/${file.id }">
									</div>
								</c:forEach>
							</td>
						</tr>
						<c:if test="${realTime.endStatus == '0' }">
							<c:if test="${realTime.confirmStatus == '0' }">
								<tr>
									<th>등록일</th>
									<td colspan="3">${realTime.regDate }</td>
								</tr>
							</c:if>
							<c:if test="${realTime.confirmStatus == '1' }">
								<tr>
									<th>경매 시작일시</th>
									<td colspan="3">${realTime.startDate }</td>
								</tr>
							</c:if>
						</c:if>
						<tr>
							<th>경매 시작가</th>
							<td colspan="3">${realTime.startBid }원</td>
						</tr>
						<c:if test="${realTime.endStatus == '1' }">
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
									<script type="text/x-template">${realTime.description }</script>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div class="mt-2 flex justify-end">
			<button class="btn btn-accent btn-sm" onclick="history.back();">뒤로가기</button>

			<c:if test="${realTime.memberId == rq.getLoginedMemberId() && realTime.confirmStatus == '0' }">
				<a class="btn btn-accent btn-sm ml-1"
					href="modify?id=${realTime.id}">수정</a>
				<a class="btn btn-accent btn-sm ml-1"
					href="javascript:deleteRealTime()">삭제</a>
			</c:if>
		</div>
	</div>
</section>


<%@ include file="../common/foot.jsp"%>