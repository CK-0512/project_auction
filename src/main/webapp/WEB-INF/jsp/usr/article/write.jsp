<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="글작성" />
<%@ include file="../common/headWithToastUIEditorLib.jsp" %>

	<section class="mt-8">
		<div class="container mx-auto">
			<form action="doWrite" method="POST" onsubmit="submitForm(this); return false;">
				<input type="hidden" name="body" />
				<div class="table-box-type-1">
					<div class="w-full max-w-xs mb-2">
						<span class="text-gray-700 font-bold text-sm ml-4 mr-2">말머리</span>
						<select data-value="${boadrdId }" name="boardId" class="select select-accent select-bordered">
						    <c:if test="${rq.loginedMember.authLevel == 3 }">
								<option value="1">공지사항</option>
							</c:if>
						    <option value="2">일반</option>
							<option value="3">거래</option>
						</select>
					</div>
					<table class="table">
						<colgroup>
							<col width="200" />
						</colgroup>
						<tbody>
							<tr>
								<th>제목</th>
								<td><input class="input input-bordered input-accent w-full" type="text" name="title" placeholder="제목을 입력해주세요" /></td>
							</tr>
							<tr>
								<th>내용</th>
								<td>
									<div class="toast-ui-editor">
								    	<script type="text/x-template"></script>
								    </div>
								</td>
							</tr>
							<tr>
								<td colspan="2"><button class="btn btn-accent btn-sm">작성</button></td>
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