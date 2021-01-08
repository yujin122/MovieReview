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
		<div id="login_box">
			<img src="${path }/resources/img/logo_box.png">
			<form method = "post" action = "#">
				<table>
					<tr>
						<td><input type="text" placeholder = "아이디"></td>
					</tr>
					<tr>	
						<td><input type="password" name = "pwd" placeholder = "비밀번호"></td>
					</tr>
				</table>
				<div id="login_btn">
					<input type = "submit" class = "btn_red" value = "로그인">
				</div>
			</form>
			<div id = "user_search">
				<a href = "#">회원가입</a>
				<a href = "#">아이디 비밀번호 찾기</a>
			</div>
		</div>
	</div>
<%@ include file = "../include/bottom.jsp" %>