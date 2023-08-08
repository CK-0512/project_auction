<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="myPage" />
<%@ include file="../common/commonHead.jsp" %>

	<section class="mt-8">
		<div class="container mx-auto">
			<div class="table-box-type-1">
				<table class="table">
					<colgroup>
						<col width="200" />
					</colgroup>
					<tbody>
						<tr>
							<th>가입일</th>
							<td>${rq.loginedMember.regDate }</td>
						</tr>
						<tr>
							<th>로그인 아이디</th>
							<td>${rq.loginedMember.loginId }</td>
						</tr>
						<tr>
							<th>이름</th>
							<td>${rq.loginedMember.name }</td>
						</tr>
						<tr>
							<th>닉네임</th>
							<td>${rq.loginedMember.nickname }</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td>${rq.loginedMember.cellphoneNum }</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td>${rq.loginedMember.email }</td>
						</tr>
						<tr>
							<th>충전된 금액</th>
							<td>
								<span>${rq.loginedMember.money }원</span>
								<button class="btn btn-accent btn-sm" onclick="window.open('chargeMoney', 'chargeMoney', 'top=200, left=300, width=600, height=300')">충전하기</button>
							</td>
							
						</tr>
						<tr>
							<th>계좌번호</th>
							<td>${rq.loginedMember.account }</td>
						</tr>
						<tr>
							<th>관심 카테고리</th>
							<td>
								<c:forEach var="interestCategory" items="${rq.interestCategories }">
									${interestCategory.name }&nbsp;
								</c:forEach>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="mt-2 flex justify-between">
				<button class="btn btn-accent btn-sm" onclick="history.back();">뒤로가기</button>
				<a class="btn btn-accent btn-sm" href="passwordChk">회원정보수정</a>
			</div>
		</div>
	</section>
	
<%@ include file="../common/foot.jsp" %>