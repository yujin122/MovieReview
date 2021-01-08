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
		<div id = "search_cont"></div>
	</div>
<%@ include file = "../include/bottom.jsp" %>