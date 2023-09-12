<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="Admin Page - ${realTime.name }" />
<%@ include file="../../usr/common/headWithToastUIEditorLib.jsp"%>

<script type="text/javascript">
	function confirmApproval() {
		let dateTimeInput = new Date(confirmForm.startDate.value);
		let currentDateTime = new Date();
		let minimumValidDate = new Date(currentDateTime);
		minimumValidDate.setDate(minimumValidDate.getDate() + 3);
		
		if(dateTimeInput < minimumValidDate) {
			alert("경매 일자는 오늘로부터 최소 3일 이후로 가능합니다.");
			return;
		}
		
		let confirmed = confirm("경매 일시는 " + dateTimeInput + " 입니다. 이 신청을 승인하시겠습니까?");
		if (confirmed) {
			confirmForm.action = "doConfirm"
			confirmForm.submit();
			
			let memberId = `${realTime.memberId}`;
		    let noticeUrl = `../../usr/realTime/detail?id=${realTime.id}`;
		    let socketMsg = `realTimeConfirm,${realTime.categoryId},${realTime.memberId},${realTime.name},${realTime.id}`;
			
		    $.ajax({
		        url: '../../usr/notice/registNotice',
		        method: 'POST',
		        data: {
		            memberId: memberId,
		            noticeUrl: noticeUrl,
		            message: socketMsg,
		            noticeType: '1'
		        },
		        dataType: 'json',
		        success: function (data) {
		            if (noticeSocket) {
		    			noticeSocket.send(socketMsg);
		    		}
		        },
		        error: function (err) {
		           		console.log(err);
		        }
		    });
		}
	}
	
	function reject() {
		let confirmed = confirm("정말 이 신청을 반려하시겠습니까?");
		if (confirmed) {
			confirmForm.action = "doReject"
			confirmForm.submit();
			
			 if (noticeSocket) {
				let socketMsg = `realTimeReject,${realTime.memberId},${realTime.name},${realTime.id}`;
	    		noticeSocket.send(socketMsg);
	    	}
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
							<td class="flex" colspan="3"><c:forEach var="file"
									items="${files }">
									<div>
										<img src="/usr/home/file/${file.id }">
									</div>
								</c:forEach></td>
						</tr>
						<tr>
							<th>등록일</th>
							<td colspan="3">${realTime.regDate }</td>
						</tr>
						<tr>
							<th>본인 희망 경매일자</th>
							<td>${realTime.hopeDate }</td>
							<th>경매 시작일자 설정</th>
							<td><input name="startDate" type="datetime-local" /></td>
						</tr>
						<tr>
							<th>경매 시작가</th>
							<td colspan="3">${realTime.startBid }원</td>
						</tr>
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
			<a class="btn btn-accent btn-sm ml-1"
				href="javascript:confirmApproval()">승인</a> <a
				class="btn btn-accent btn-sm ml-1" href="javascript:reject()">반려</a>
		</div>
	</div>
</section>


<%@ include file="../../usr/common/foot.jsp"%>