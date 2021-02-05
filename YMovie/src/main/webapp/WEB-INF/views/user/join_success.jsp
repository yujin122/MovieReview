<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${path }/resources/css/user.css">
</head>
<body>
<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		<div id="success">
			<img src="${path }/resources/img/logo_box.png">
			<h1>환영합니다.</h1>
			<p>ymovie 회원가입이 완료 되었습니다.</p>
			<p>유진님의 회원가입을 축하합니다.</p>
			<div id = "loginpage">
				<a href = "#">로그인 페이지로 이동</a>
			</div>
		</div>
	</div>
<%@ include file = "../include/bottom.jsp" %>