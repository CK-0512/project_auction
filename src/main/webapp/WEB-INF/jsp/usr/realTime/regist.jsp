<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="경매추첨 등록" />
<%@ include file="../common/headWithToastUIEditorLib.jsp" %>

<script>
	function regist_submitForm(form) {
		
		form.name.value = form.name.value.trim();
		if (!form.name.value) {
			alert('상품명을 입력해주세요');
			form.name.focus();
			return;
		}
		
		selectedCategory = document.querySelector("select[name='categoryId']").value;
		if (!selectedCategory) {
		    alert("상품 카테고리를 선택해주세요.");
		    return;
		}

		if (!form.file.files.length) {
		    alert("상품 사진을 등록해주세요.");
		    return;
		}
		
		form.startBid.value = form.startBid.value.trim();
		if (!form.startBid.value) {
			alert('경매 시작가를 설정해주세요.');
			form.startBid.focus();
			return;
		}
		
		let dateTimeInput = new Date(form.hopeDate.value);
		let currentDateTime = new Date();
		let minimumValidDate = new Date(currentDateTime);
		minimumValidDate.setDate(minimumValidDate.getDate() + 7);
		
		if(dateTimeInput < minimumValidDate) {
			alert("경매 희망일은 오늘로부터 최소 7일 이후로 가능합니다.");
			return;
		}
  	  
  	  	const editor = $(form).find('.toast-ui-editor').data('data-toast-editor');
  	  	const markdown = editor.getMarkdown().trim();
  	  
  	  	if (markdown.length == 0){
  	   	  	alert('내용을 입력해주세요');
  	  	  	editor.focus();
  	    	return;
  	  	}
  	  
  	  	form.body.value = markdown;
  	  	
  	 	let confirmMessage = "상품의 경매 시작가는 " + form.startBid.value + "원입니다. 또한, 경매일자는 상황에 따라 희망일과는 달라질 수 있습니다. 이대로 등록하시겠습니까?";
  	 	if (!confirm(confirmMessage)) {
  	 		return;
  	 	}
  	 	
  	 	if (!confirm("추첨 등록은 1주일에 1번만 가능하며, 추첨된 후에는 더 이상 수정과 취소가 불가능합니다. 정말 등록하시겠습니까?")){
  	 		return;	
  	 	}
  	 	
		form.submit();
	}
</script>

	<section>
		<div class="container mx-auto">
			<form action="doRegist" method="POST" onsubmit="regist_submitForm(this); return false;" enctype="multipart/form-data">
				<input type="hidden" name="body" />
				<div class="table-box-type-1">
					<table class="table">
						<colgroup>
							<col width="200" />
						</colgroup>
						<tbody>
							<tr>
								<th>상 품 명</th>
								<td><input class="input input-bordered input-accent w-full" type="text" name="name" placeholder="상품명을 입력해주세요" /></td>
								<th>상품 카테고리</th>
								<td>
									<select name="categoryId" class="select select-accent select-bordered">
										<c:forEach var="category" items="${categories}">
											<option value="${category.id }">${category.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th>경매 시작가</th>
								<td>
									<input class="input input-bordered input-accent text-right" type="text" name="startBid" placeholder="0"/> 원
									<div class="text-xs text-red-500">수수료는 낙찰가의 10%입니다.</div>
								</td>
								<th>경매 희망일자</th>
								<td><input name="hopeDate" type="datetime-local" /></td>
							</tr>
							<tr>
								<th>상품 사진 등록</th>
								<td colspan="3" class="align-center">
									<input type="file" name="file" multiple/>
								</td>
							</tr>
							<tr>
								<th>상품 설명</th>
								<td colspan="3">
									<div class="toast-ui-editor">
								    	<script type="text/x-template"></script>
								    </div>
								</td>
							</tr>
							<tr>
								<td colspan="4"><button class="btn btn-accent btn-sm">등록</button></td>
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