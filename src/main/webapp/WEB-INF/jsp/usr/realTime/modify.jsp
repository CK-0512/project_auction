<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="경매추첨 등록" />
<%@ include file="../common/headWithToastUIEditorLib.jsp" %>

<script>
	function modify_submitForm(form) {
		
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
		
		form.startBid.value = form.startBid.value.trim();
		if (!form.startBid.value) {
			alert('경매 시작가를 설정해주세요.');
			form.startBid.focus();
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
  	  	
  	  	if (form.file.files.length) {
	  	  	let confirmMessage = "기존 상품사진을 새 사진으로 변경합니다. 이대로 진행하시겠습니까?";
	  	 	if (!confirm(confirmMessage)) {
	  	 		return;
  	 		}
		}
  	 	
  	 	if (!confirm("추첨된 후에는 더 이상 수정과 취소가 불가능합니다. 정말 수정하시겠습니까?")){
  	 		return;	
  	 	}
  	 	
		form.submit();
	}
</script>

	<section>
		<div class="container mx-auto">
			<form action="doModify" method="POST" onsubmit="modify_submitForm(this); return false;" enctype="multipart/form-data">
				<input type="hidden" name="id" value="${realTime.id }" />
				<input type="hidden" name="body" />
				<div class="table-box-type-1">
					<table class="table">
						<colgroup>
							<col width="200" />
						</colgroup>
						<tbody>
							<tr >
								<th>상 품 명</th>
								<td><input class="input input-bordered input-accent w-full" type="text" name="name" placeholder="상품명을 입력해주세요" value="${realTime.name }"/></td>
								<th>상품 카테고리</th>
								<td>
									<select name="categoryId" class="select select-accent select-bordered">
										<c:forEach var="category" items="${categories}">
											<option value="${category.id }" selected="${category.id == realTime.categoryId ? 'selected' : '' }">${category.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th>기존 상품사진</th>
								<td>
									<c:forEach var="oldFile" items="${oldFiles }">
										<div class="inline">
											<img src="/usr/home/file/${oldFile.id }">
										</div>
									</c:forEach>
								</td>
								<th>수정할 사진 등록</th>
								<td class="align-center"><input type="file" name="newFile" multiple/></td>
							</tr>
							<tr>
								<th>경매 시작가</th>
								<td>
									<input class="input input-bordered input-accent text-right" type="text" name="startBid" placeholder="0" value="${realTime.startBid }"/> 원
								</td>
								<th>수 수 료</th>
								<td>
									<div class="text-xs text-red-500">수수료는 경매 기간에 따라 변동됩니다.(1일 5%, 3일 7%, 7일 10%)</div>
								</td>
							</tr>
							<tr>
								<th>상품 설명</th>
								<td colspan="3">
									<div class="toast-ui-editor">
								    	<script type="text/x-template">${realTime.description }</script>
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