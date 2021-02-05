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
<script type="text/javascript" src="${path }/resources/js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	$(function(){
		if('${msg}' != ""){
			alert('${msg}');
		}
	});
</script>
</head>
<body>
<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		<div id="idpwd_box">
			<img src="${path }/resources/img/logo_box.png">
			<div class = "search">
				<p>아이디 찾기</p>
				<form method = "post" action = "${pageContext.request.contextPath }/id_search.do">
					<table>
						<tr>
							<th>이 름</th>
							<td><input type="text" name="name" placeholder="이름을 입력해주세요.">
						</tr>
						<tr>
							<th>이메일</th>
							<td><input type= "text" name="email" placeholder="이메일을 입력해주세요."></td>
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
				<form method = "post" action = "${pageContext.request.contextPath }/pwd_search.do">
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
							<th>이메일</th>
							<td>
							<input type = "text" name = "email" placeholder="이메일을 입력해주세요."> 
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