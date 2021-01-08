<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${path }/resources/css/movie.css">
</head>
<body>
<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		<div id="mov_search">
			<img src="${path }/resources/img/logo_box.png">
			<h2>영화 검색</h2>
			<form method = "post" action = "#">
				<input name = "search_txt" placeholder="영화이름입력">
			</form>
			<hr>
		</div>
		<div id = "search_list">
			<table>
				<tr>
					<td rowspan = "5"><img src = "${path }/resources/img/heart_empty.png" id = "like" onclick="#"></td>
					<td rowspan = "5"><img src="${path }/resources/img/post.jpg" id="post"></td>
					<td>삼진그룹 영어토익반</td>
					<td><span>★</span>4.8</td>
					<td rowspan = "5"><button class = "btn_red">리뷰쓰기</button></td>
				</tr>
				<tr></tr>
				<tr>
					<td colspan = "2">이종필 | 한국 | 2020.10.21</td>
				</tr>
				<tr>
					<td colspan = "2">드라마 | 110분</td>
				</tr>
				<tr>
					<td colspan = "2">고아성, 이솜, 박혜수 외 ...</td>
				</tr>
			</table>
		</div>
		<div id ="paging_box">
			<a href = "#">[1]</a>
		</div>
	</div>
<%@ include file = "../include/bottom.jsp" %>