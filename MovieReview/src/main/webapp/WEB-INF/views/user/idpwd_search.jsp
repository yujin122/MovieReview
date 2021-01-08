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
		<div id="idpwd_box">
			<img src="${path }/resources/img/logo_box.png">
			<div class = "search">
				<p>아이디 찾기</p>
				<form method = "post" action = "#">
					<table>
						<tr>
							<th>이 름</th>
							<td><input type="text" name="name" placeholder="이름을 입력해주세요.">
						</tr>
						<tr>
							<th>핸드폰번호</th>
							<td><input type= "text" name="phone" placeholder="'-' 을 빼고 입력해주세요."></td>
						</tr>
					</table>
					<div class = "idpwd_search">
						<input type = "submit" class = "btn_red" value = "조회">
						<input type = "button" class = "btn_white" onclick = "javascript:history.back()" value = "취소"> 
					</div>
				</form>
			</div>
			<hr>
			<div class = "search">
				<p>비밀번호 찾기</p>
				<form method = "post" action = "#">
					<table>
						<tr>
							<th>아이디</th>
							<td><input type = "text" name = "id" placeholder="아이디를 입력해주세요.">
						</tr>
						<tr>
							<th>이름</th>
							<td><input type = "text" name = "name" placeholder="이름을 입력해주세요.">
						</tr>
						<tr>
							<th>핸드폰번호</th>
							<td>
							<input type = "text" name = "phone" placeholder="'-' 을 빼고 입력해주세요."> 
							</td>
						</tr>
					</table>
					<div class = "idpwd_search">
						<input type = "submit" class = "btn_red" value = "조회">
						<input type = "button" class = "btn_white" onclick = "javascript:history.back()" value = "취소"> 
					</div>
				</form>
			</div>
		</div>
	</div>
<%@ include file = "../include/bottom.jsp" %>