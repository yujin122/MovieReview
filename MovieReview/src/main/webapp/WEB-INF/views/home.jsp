<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<title>Home</title>
<link rel = "stylesheet" href="${path }/resources/css/review.css">
</head>
<body>

<%@ include file = "./include/top.jsp" %>

	<div id = "content">
		<h3>리뷰 게시판</h3>
		<div id = "mov_list">
			<table id = "mov_tb">
				<tr>
					<th>번호</th>
					<th>별점</th>
					<th id = "tit_th">영화</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회수</th>
				</tr>
				<c:set var = "list" value = "${list }"/>
				<c:if test="${!empty list }">
					<c:forEach items="${list }" var = "review">
						<tr>
							<td>${review.getReview_num() }</td>
							<td><span>★</span>${review.getReview_rating() }</td>
							<td id = "tit_td"><a href = "${pageContext.request.contextPath }/review_post.do?review_num=${review.getReview_num()}&page=${pagination.page}">${review.getMov_title() }</a></td>
							<td>${review.getReview_writer() }</td>
							<td>${review.getReview_regdate().substring(0,10) }</td>
							<td>${review.getReview_hit() }</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty list }">
					<tr>
						<td colspan = "6">
							<h3>아직 리뷰가 없습니다.</h3>
						</td>
					</tr>
				</c:if>
			</table>	
		</div>
		<div id ="paging_box">
			<c:if test="${pagination.prev }">
				<a href = "${pageContext.request.contextPath}/?page=${pagination.StartPage-1 }"> < </a>
			</c:if>
			<c:forEach begin = "${pagination.startPage }" end = "${pagination.endPage }" var = "i">
				<c:if test="${pagination.page == i }">
					<b><a href = "${pageContext.request.contextPath}/?page=${i }">${i }</a></b>
				</c:if>
				<c:if test="${pagination.page != i }">
					<a href = "${pageContext.request.contextPath}/?page=${i }">${i }</a>
				</c:if>
			</c:forEach>
			<c:if test="${pagination.next }">
				<a href = "${pageContext.request.contextPath}/?page=${pagination.endPage+1 }')">i</a>
			</c:if>
		</div>
		<div id="r_search">
			<form name = "frm" method = "post" action="${path }/review_search.do" onsubmit = "return check()">
				<select name="label" id="search_label">
					<option value="title">영화</option>
					<option value="cont">본문</option>
					<option value="writer">글쓴이</option>
				</select><input type="text" class="search_txt" name="search"><input type="submit" class="search_btn" value="검색">
			</form>
		</div>
	</div>
<%@ include file = "./include/bottom.jsp" %>
