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
				<input type = "text" class= "search_txt" name = "title" placeholder="영화제목을 입력해주세요"><input type = "button" class="search_btn" value="검색">
			</div>
		</form>
		<div id = "header_main">
			<table>
				<tr>
					<td><a href="${pageContext.request.contextPath }/"> 리뷰</a></td>
					<td><a href="${pageContext.request.contextPath }/movie_main.do"> 영화</a></td>
					<td><a href="${pageContext.request.contextPath }/board_list.do">자유게시판</a></td>
					<td>
						<c:if test="${!empty session_mem_id }">
							<a href="${pageContext.request.contextPath }/mypage_main.do">마이페이지</a>					
						</c:if>
						<c:if test="${empty session_mem_id }">
							<a href="javascript:alert('로그인 후 이용가능합니다.')">마이페이지</a>					
						</c:if>
					</td>
				</tr>
			</table>
			<div id = "header_login">
				<ul>
					<c:if test="${empty session_mem_id }">
						<li><a href = "${pageContext.request.contextPath }/login_main.do">로그인</a></li>
						<li><a href = "${pageContext.request.contextPath }/join_main.do">회원가입</a></li>
					</c:if>
					<c:if test="${!empty session_mem_id }">
						<li><a href = "${pageContext.request.contextPath }/logout.do">로그아웃</a></li>
					</c:if>
				</ul>
			</div>	
		</div>
	</div>
