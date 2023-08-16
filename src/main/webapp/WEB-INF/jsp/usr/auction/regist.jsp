<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="경매 등록" />
<%@ include file="../common/headWithToastUIEditorLib.jsp" %>

<script>
	document.addEventListener("DOMContentLoaded", function() {
	    let bidDate = document.getElementsByName("bidDate");
	    for (let i = 0; i < bidDate.length; i++) {
	        bidDate[i].addEventListener("click", setCharge);
	    }
	});

	function setCharge() {
		let buyNow = document.getElementsByName("buyNow")[0];
		let bidDate = document.getElementsByName("bidDate");
		let charge = document.getElementsByName("charge")[0];
		let message = document.getElementById("charge-message");
        
		let buyNowVal = parseFloat(buyNow.value) || 0;
        let bidDateVal = 0;
        for (var i = 0; i < bidDate.length; i++) {
            if (bidDate[i].checked) {
                bidDateVal = parseInt(bidDate[i].value);
                break;
            }
        }
        
        let chargeRate = 0; 
        if (bidDateVal == 1) {
        	chargeRate = 0.05;
        } else if (bidDateVal == 3) {
        	chargeRate = 0.07;
        } else if (bidDateVal == 7) {
        	chargeRate = 0.1;
        }
        
        if (!buyNowVal) {
        	charge.value = 0;
        	message.textContent = "즉시 구매가를 설정하지 않으면 수수료는 낙찰가를 기준으로 책정됩니다.";
        	message.style.color = "red";
        } else {
        	charge.value = (buyNowVal * chargeRate).toFixed(0);
        	message.textContent = charge.value + " 원";
        	message.style.color = "green";
        }
	}
	
	function regist_submitForm(form) {
		
		form.name.value = form.name.value.trim();
		if (!form.name.value) {
			alert('제품명을 입력해주세요');
			form.name.focus();
			return;
		}
		
		selectedCategory = document.querySelector("select[name='categoryId']").value;
		if (!selectedCategory) {
		    alert("제품 카테고리를 선택해주세요.");
		    return;
		}

		if (!form.file.files) {
		    alert("제품 사진을 등록해주세요.");
		    return;
		}
		
		form.startBid.value = form.startBid.value.trim();
		if (!form.startBid.value) {
			alert('경매 시작가를 설정해주세요.');
			form.startBid.focus();
			return;
		}
		
		selectedDate = document.querySelector("input[type='radio'][name='bidDate']:checked");
        if (!selectedDate) {
            alert("경매 기간을 선택해주세요.");
            return;
  	  	}
		
        productDescription = document.querySelector(".toast-ui-editor script").value.trim();
        if (!productDescription) {
            alert("제품 설명을 입력해주세요.");
            return;
        }
  	 	
  	 	let confirmMessage = "제품의 경매 시작가는 " + form.startBid.value +
        					"원, 경매 기간은 " + selectedDate.value + "일, 즉시 구매가는 " +
        					form.buyNow.value + "원입니다. 이대로 등록하시겠습니까?";
  	 	if (!confirm(confirmMessage)) {
  	 		return;
  	 	}
  	 	
  	 	if (!confirm("제품 등록후에는 취소가 불가능하며, 제품 설명만 수정이 가능합니다. 정말 등록하시겠습니까?")){
  	 		return;	
  	 	}
  	 	
  	 	setCharge();
		form.submit();
	}
</script>

	<section class="mt-8">
		<div class="container mx-auto">
			<form action="doRegist" method="POST" onsubmit="regist_submitForm(this); return false;" enctype="multipart/form-data">
				<input type="hidden" name="body" />
				<div class="table-box-type-1">
					<table class="table">
						<colgroup>
							<col width="200" />
						</colgroup>
						<tbody>
							<tr >
								<th>제 품 명</th>
								<td colspan="3"><input class="input input-bordered input-accent w-full" type="text" name="name" placeholder="제품명을 입력해주세요" /></td>
							</tr>
							<tr>
								<th>제품 카테고리</th>
								<td>
									<select name="categoryId" class="select select-accent select-bordered">
										<c:forEach var="category" items="${categories}">
											<option value="${category.id }">${category.name }</option>
										</c:forEach>
									</select>
								</td>
								<th>제품 사진</th>
								<td class="align-center">
									<input type="file" name="file" multiple/>
								</td>
							</tr>
							<tr>
								<th>경매 시작가</th>
								<td>
									<input class="input input-bordered input-accent text-right" type="text" name="startBid" value="0"/> 원
								</td>
								<th>즉시 구매가</th>
								<td>
									<input class="input input-bordered input-accent text-right" type="text" name="buyNow" onblur="setCharge();" value="0"/> 원
								</td>
							</tr>
							<tr>
								<th>경매 기간</th>
								<td>
									<label class="radio-label">
										<input type="radio" name="bidDate" value="1"/>
										<span>1일</span>
									</label>
									<label class="radio-label">
										<input type="radio" name="bidDate" value="3"/>
										<span>3일</span>
									</label>
									<label class="radio-label">
										<input type="radio" name="bidDate" value="7"/>
										<span>7일</span>
									</label>
								</td>
								<th>수 수 료</th>
								<td>
									<input type="hidden" name="charge"/>
									<div id="charge-message" class="text-lg"></div>
									<div class="text-xs text-red-500">수수료는 경매 기간에 따라 변동됩니다.(1일 5%, 3일 7%, 7일 10%)</div>
								</td>
							</tr>
							<tr>
								<th>제품 설명</th>
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