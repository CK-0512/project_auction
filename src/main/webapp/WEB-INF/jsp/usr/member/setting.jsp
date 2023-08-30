<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="Setting" />
<%@ include file="../common/commonHead.jsp" %>

	<section>
		<div class="container mx-auto">
			<form action="doModify" method="POST">
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
								<td><input class="input input-bordered input-accent w-96" type="text" name="nickname" placeholder="닉네임을 입력해주세요" value="${rq.loginedMember.nickname }" /></td>
							</tr>
							<tr>
								<th>전화번호</th>
								<td><input class="input input-bordered input-accent w-96" type="text" name="cellphoneNum" placeholder="전화번호를 입력해주세요" value="${rq.loginedMember.cellphoneNum }" /></td>
							</tr>
							<tr>
								<th>이메일</th>
								<td><input class="input input-bordered input-accent w-96" type="text" name="email" placeholder="이메일을 입력해주세요" value="${rq.loginedMember.email }" /></td>
							</tr>
							<tr>
								<th>계좌번호</th>
								<td><input class="input input-bordered input-accent w-96" type="text" name="account" placeholder="계좌번호를 입력해주세요" value="${rq.loginedMember.account }" /></td>
							</tr>
							<tr class="item-tr">
								<th>관심 카테고리</th>
								<td>
									<c:forEach var="category" items="${categories}">
									  <div class="form-control">
									    <label class="label">
									      <c:set var="checked" value="false" />
								            <c:forEach var="interestCategory" items="${rq.interestCategories}">
								                <c:if test="${category.id == interestCategory.categoryId}">
								                    <c:set var="checked" value="true" />
								                </c:if>
								            </c:forEach>
								            <input type="checkbox" name="category" value="${category.name}" class="checkbox" ${checked ? 'checked="checked"' : ''} />
									      <span class="label-text">${category.name}</span>
									    </label>
									  </div>
									</c:forEach>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="mt-2 flex justify-between">
					<button class="btn btn-accent btn-sm" onclick="history.back();">뒤로가기</button>
					<div>
						<a class="btn btn-accent btn-sm mr-2" href="passwordModify">비밀번호변경</a>
						<button class="btn btn-accent btn-sm">수정</button>
					</div>
				</div>
			</form>
		</div>
	</section>
	
<%@ include file="../common/foot.jsp" %>