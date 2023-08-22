<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="경매 수정" />
<%@ include file="../common/headWithToastUIEditorLib.jsp" %>

<script>
	function modify_submitForm(form) {
		const editor = $(form).find('.toast-ui-editor').data('data-toast-editor');
	  	const markdown = editor.getMarkdown().trim();
	  
	  	if (markdown.length == 0){
	   	  	alert('내용을 입력해주세요');
	  	  	editor.focus();
	    	return;
	  	}
	  
	  	form.body.value = markdown;
  	 	
  	 	if (!confirm("이대로 수정하시겠습니까?")){
  	 		return;	
  	 	}
  	 	
		form.submit();
	}
</script>

	<section class="mt-8">
		<div class="container mx-auto">
			<form action="doModify" method="POST" onsubmit="modify_submitForm(this); return false;" enctype="multipart/form-data">
				<input type="hidden" name="id" value="${auction.id }"/>
				<input type="hidden" name="body" />
				<div class="table-box-type-1">
					<table class="table">
						<colgroup>
							<col width="200" />
						</colgroup>
						<tbody>
							<tr>
								<th>제 품 명</th>
								<td>${auction.name }</td>
								<th>제품 카테고리</th>
								<td>${categoryName }</td>
							</tr>
							<tr>
								<th>제품 사진</th>
								<td colspan="3"></td>
							</tr>
							<tr>
								<th>경매 시작가</th>
								<td>${auction.startBid }원</td>
								<th>즉시 구매가</th>
								<td>
									<c:if test="${auction.buyNow != 0}">
										${auction.buyNow }원
									</c:if>
									<c:if test="${auction.buyNow == 0}">
										<span>즉시구매가 미설정</span>
									</c:if>
								</td>
							</tr>
							<tr>
								<th>경매 기간</th>
								<td>${auction.bidDate }일</td>
								<th>수 수 료</th>
								<td>
									<c:if test="${auction.buyNow != 0 }">
										${auction.charge }원
									</c:if>
									<c:if test="${auction.buyNow == 0 }">
										<span>즉시 구매가를 설정하지 않아 수수료는 낙찰가에서 공제됩니다.</span>
									</c:if>
								</td>
							</tr>
							<tr>
								<th>제품 설명</th>
								<td colspan="3">
									<div class="toast-ui-editor">
								    	<script type="text/x-template">${auction.description }</script>
								    </div>
								</td>
							</tr>
							<tr>
								<td colspan="4"><button class="btn btn-accent btn-sm">수정</button></td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
			<div class="mt-2">
				<button class="btn btn-accent btn-sm" onclick="history.back();">뒤로가기</button>
			</div>
		</div>
	</section>
	
<%@ include file="../common/foot.jsp" %>