<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${path }/resources/css/review.css">
</head>
<body>

<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		<div id ="review_post">
			<div id="mov_info">
			<c:set var = "dto" value = "${dto }" />
				<table>
					<tr>
						<td rowspan = "5" id = "post_td">
							<c:if test="${dto.getMov_poster() == null }">
								<img src="${path }/resources/img/nophoto.png">
							</c:if>
							<c:if test="${dto.getMov_poster() != null }">
								<img src="${dto.getMov_poster() }">
							</c:if>
						</td>
						<td colspan = "4">${dto.getMov_title() }</td>
					</tr>
					<tr>
						<th>감독</th>
						<td>${dto.getMov_director() }</td>
						<th>장르</th>
						<td>
							<c:set var = "list" value = "${genre }" />
							<c:if test="${!empty list }">
								<c:forEach items="${list }" var = "genre">
									${genre } 
								</c:forEach>
							</c:if>
						</td>
					</tr>
					<tr>
						<th>상영시간</th>
						<td>${dto.getMov_showtm() }</td>
						<th>제작국가</th>
						<td>${dto.getMov_nations() }</td>
					</tr>
					<tr>
						<th>주연배우</th>
						<td colspan = "3">${dto.getMov_actors() }</td>
					</tr>
					<tr>
						<th>평균 평점</th>
						<td colspan = "3"><span>★</span>5.0</td>
					</tr>
				</table>
			</div>
			<div id = "review_cont">
				평점 : <select>
					<option>5.0</option>
					<option>4.5</option>
					<option>4.0</option>
					<option>3.5</option>
					<option>3.0</option>
					<option>2.5</option>
					<option>2.0</option>
					<option>1.5</option>
					<option>1.0</option>
					<option>0.5</option>
					<option>0.0</option>
				</select>
				<textarea></textarea>
			</div>
			<div id = "review_up_btn">
				<button class="btn_red">등록</button>
				<button class="btn_white">취소</button>
			</div>
		</div>
	</div>
<%@ include file = "../include/bottom.jsp" %>