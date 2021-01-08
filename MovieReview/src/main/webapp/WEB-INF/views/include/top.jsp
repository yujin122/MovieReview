<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href = "${path }/resources/css/top.css">
<link rel = "stylesheet" href = "${path }/resources/css/mov_comm.css">
</head>
<body>
	<div id = "header_cont">
		<form method = "post" action = "#">
			<div id = "header_search">
				<img src="${path }/resources/img/logo3.png">
				<input type = "text" id= "search_txt" name = "title" placeholder="영화제목을 입력해주세요"><input type = "button" id="search_btn" value="검색">
			</div>
		</form>
		<div id = "header_main">
			<table>
				<tr>
					<td><a href="#"> 리뷰</a></td>
					<td><a href="#"> 영화</a></td>
					<td><a href="#">자유게시판</a></td>
					<td><a href="#">마이페이지</a></td>
				</tr>
			</table>
			<div id = "header_login">
				<ul>
					<li><a href = "#">로그인</a></li>
					<li><a href = "#">회원가입</a></li>
				</ul>
			</div>	
		</div>
	</div>
