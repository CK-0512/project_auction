<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ include file="../common/commonHead.jsp" %>

	<section>
		<div class="container mx-auto">
			<form action="doLogin" method="POST">
				<div class="table-box-type-1">
					<table>
						<colgroup>
							<col width="200" />
						</colgroup>
						<tbody>
							<tr>
								<th>로그인 아이디</th>
								<td><input class="input input-bordered input-accent w-96" type="text" name="loginId" placeholder="아이디를 입력해주세요" /></td>
							</tr>
							<tr>
								<th>로그인 비밀번호</th>
								<td><input class="input input-bordered input-accent w-96" type="text" name="loginPw" placeholder="비밀번호를 입력해주세요" /></td>
							</tr>
							<tr>
								<td colspan="2"><button class="btn btn-accent btn-sm">로그인</button></td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
			<div class="mt-2 flex justify-between">
				<button class="btn btn-accent btn-sm" onclick="history.back();">뒤로가기</button>
				<div>
					<a class="btn btn-accent btn-sm" href="findLoginId">아이디 찾기</a>
					<a class="btn btn-accent btn-sm" href="findLoginPw">비밀번호 찾기</a>
				</div>
			</div>
		</div>
	</section>
	
<%@ include file="../common/foot.jsp" %>