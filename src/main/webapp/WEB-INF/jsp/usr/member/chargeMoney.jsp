<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="chargeMoney" />
<%@ include file="../common/head.jsp" %>
</head>
<body>

<script>
	function charge_submitForm(form) {
		
		form.money.value = form.money.value.trim();
		if (!form.money.value) {
			alert('금액을 입력해주세요');
			form.money.focus();
			return;
		}
		
		form.submit();
		window.opener.location.reload();
	}
	
	document.addEventListener('DOMContentLoaded', function () {
        const closeButton = document.querySelector('.btn-close');
        closeButton.addEventListener('click', function () {
            self.close();
        });
    });
</script>

<section>
	<div class="container mx-auto">
		<form action="doCharge" method="POST" onSubmit="charge_submitForm(this); return false;">
			<div class="table-box-type-1">
				<table class="table">
					<colgroup>
						<col width="200" />
					</colgroup>
					<tbody>
						<tr>
							<th>연결계좌</th>
							<td>${rq.loginedMember.account }</td>
						</tr>
						<tr>
							<th>잔액</th>
							<td>${rq.loginedMember.money }원</td>
						</tr>
						<tr>
							<th>충전할 금액</th>
							<td><input class="input input-bordered input-accent w-96" type="text" name="money" placeholder="금액을 입력해주세요"></td>
						</tr>
						<tr>
							<td colspan="2">
								<button class="btn btn-accent btn-sm" onclick="if(confirm('정말 충전하시겠습니까?') == false) return false;">충전</button>
								<button class="btn btn-accent btn-sm btn-close">취소</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
	</div>
</section>

<%@ include file="../common/foot.jsp"%>