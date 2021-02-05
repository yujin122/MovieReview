<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href="${path }/resources/css/board.css">
</head>
<body>
<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		<div id="b_search">
			<p><span>${keyword }</span> 에 대한 검색 결과입니다.</p>
			<form action="${pageContext.request.contextPath }/board_search.do">
				<select name="label" id="search_label">
					<option value="category">카테고리</option>
					<option value="cont">본문</option>
					<option value="writer">글쓴이</option>
				</select><input type="text" class="search_txt" name="keyword"><input type="submit" class="search_btn" value="검색">
			</form>
		</div>
		<div id = "bor_list">
			<table id = "mov_tb">
				<tr>
					<th>번호</th>
					<th>카테고리</th>
					<th>제목</th>
					<th>작성자</th>
					<th>날짜</th>
					<th>조회수</th>
				</tr>
				<tr>
					<c:set var = "list" value = "${list }" />
					<c:if test="${!empty list }">
						<c:forEach items="${list }" var="board">
							<tr>
								<td>${board.getBoard_num() }</td>
								<td>${board.getBoard_category() }</td>
								<td id = "tit_td">
									<c:forEach begin = "1" end = "${board.getBoard_indent() }">
										&nbsp;&nbsp;
									</c:forEach>
									<a href = "${pageContext.request.contextPath }/board_cont.do?bnum=${board.getBoard_num() }&page=${pagination.page }&label=${label }&keyword=${keyword }">${board.getBoard_title() }</a> <c:if test = "${board.getBoard_comm_cnt() != 0 }"><span>${board.getBoard_comm_cnt() }</span></c:if>
								</td>
								<td>${board.getBoard_writer() }</td>
								<td>${board.getBoard_regdate().substring(0,10) }</td>
								<td>${board.getBoard_hit() }</td>
							</tr>
						</c:forEach>
				</c:if>
				<c:if test="${empty list }">
					<tr>
						<td colspan = "6"><h3>검색 결과가 없습니다.</h3></td>
					</tr>
				</c:if>
				</tr>
			</table>	
		</div>
		<div id ="paging_box">
			<c:if test="${pagination.prev }">
				<a href = "${pageContext.request.contextPath}/board_search.do?page=${pagination.StartPage-1 }&label=${label }&keyword=${keyword }"> < </a>
			</c:if>
			<c:forEach begin = "${pagination.startPage }" end = "${pagination.endPage }" var = "i">
				<c:if test="${pagination.page == i }">
					<b><a href = "${pageContext.request.contextPath}/board_search.do?page=${i }&label=${label }&keyword=${keyword }">${i }</a></b>
				</c:if>
				<c:if test="${pagination.page != i }">
					<a href = "${pageContext.request.contextPath}/board_search.do?page=${i }&label=${label }&keyword=${keyword }">${i }</a>
				</c:if>
			</c:forEach>
			<c:if test="${pagination.next }">
				<a href = "${pageContext.request.contextPath}/board_search.do?page=${pagination.endPage+1 }&label=${label }&keyword=${keyword }')">i</a>
			</c:if>
		</div>
	</div>
<%@ include file = "../include/bottom.jsp" %>