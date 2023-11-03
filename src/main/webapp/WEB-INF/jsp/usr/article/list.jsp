<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="자유 게시판" />
<%@ include file="../common/commonHead.jsp" %>

	<section class="mt-24">
		<div class="container mx-auto">
			<div class="mb-2 flex justify-between">
				<div>
					<span>글 개수 : ${articlesCnt }</span>
					<div class="py-1">
						<c:set var="boardId" value="${board.size() == 1 ? board[0].id : 0}" />
						<a href="list" class="${boardId == '0' ? 'selected' : ''}">전체글</a>
						<span>|||</span>
						<c:if test="boardId"></c:if>
						<a href="list?boardId=1" class="${boardId == '1' ? 'selected' : ''}">공지사항</a>
						<span>|||</span>
						<a href="list?boardId=2" class="${boardId == '2' ? 'selected' : ''}">일반</a>
						<span>|||</span>
						<a href="list?boardId=3" class="${boardId == '3' ? 'selected' : ''}">거래</a>
					</div>
				</div>
				<div>
					<form>
						<input type="hidden" name="boardId" value="${board.size() == 1 ? board[0].id : 0}" />
						<select data-value="${searchKeywordType }" class="select select-accent select-sm w-28" name="searchKeywordType">
							<option value="title">제목</option>
							<option value="body">내용</option>
							<option value="title,body">제목 + 내용</option>
						</select>
						<input class="ml-2 input input-bordered input-accent input-sm w-64" type="text" name="searchKeyword" placeholder="검색어를 입력해주세요" maxlength="20" value="${searchKeyword }"/>
						<button class="ml-2 btn btn-accent btn-sm">검색</button>
					</form>
				</div>
			</div>
			<div class="table-box-type-1">
				<table class="table">
					<thead>
						<tr>
							<th>번호</th>
							<th>작성일</th>
							<th>제목</th>
							<th>작성자</th>
							<th>추천</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="article" items="${articles }">
							<tr class="hover">
								<td>${article.id }</td>
								<td>${article.regDate.substring(2, 16) }</td>
								<td><a class="hover:underline" href="detail?id=${article.id }">${article.title }</a></td>
								<td>${article.writerName }</td>
								<td>${article.sumReactionPoint }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			<c:if test="${rq.getLoginedMemberId() != 0 }">
				<div class="mt-2 flex justify-end">
					<a class="btn btn-accent btn-sm" href="write">게시글 작성</a>
				</div>
			</c:if>
			
			<div class="mt-2 flex justify-center">
				<div class="join">
					<c:set var="pageMenuLen" value="5" />
					<c:set var="startPage" value="${page - pageMenuLen >= 1 ? page - pageMenuLen : 1 }" />
					<c:set var="endPage" value="${page + pageMenuLen <= pagesCnt ? page + pageMenuLen : pagesCnt }" />
					
					<c:set var="pageBaseUri" value="?boardId=${board.size() == 1 ? board[0].id : 0}&searchKeywordType=${searchKeywordType }&searchKeyword=${searchKeyword }" />
					
					<c:if test="${page == 1 }">
						<a class="join-item btn btn-disabled">«</a>
						<a class="join-item btn btn-disabled">&lt;</a>
					</c:if>
					<c:if test="${page > 1 }">
						<a class="join-item btn" href="${pageBaseUri }&page=1">«</a>
						<a class="join-item btn" href="${pageBaseUri }&page=${page - 1 }">&lt;</a>
					</c:if>
					<c:forEach begin="${startPage }" end="${endPage }" var="i">
						<a class="join-item btn ${page == i ? 'btn-active' : '' }" href="${pageBaseUri }&page=${i }">${i }</a>
					</c:forEach>
					<c:if test="${page < pagesCnt }">
						<a class="join-item btn" href="${pageBaseUri }&page=${page + 1 }">&gt;</a>
						<a class="join-item btn" href="${pageBaseUri }&page=${pagesCnt }">»</a>
					</c:if>
					<c:if test="${page == pagesCnt }">
						<a class="join-item btn btn-disabled">&gt;</a>
						<a class="join-item btn btn-disabled">»</a>
					</c:if>
				</div>
			</div>
		</div>
	</section>
	
<%@ include file="../common/foot.jsp" %>