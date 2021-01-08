<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href="${path }/resources/css/board.css">
</head>
<body>
<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		
		<div id = "board_post">
			<h3>자유게시판</h3>
			<form method = "post" action = "#">
				<div id = "b_form">
				<table>
					<tr>
						<td>
							<select name = "category">
								<option value = "잡담">잡담</option>
								<option value = "영화추천">영화추천</option>
								<option value = "질문">질문</option>
							</select>	
						</td>
					</tr>
					<tr>
						<td><input type = "text" name = "title" placeholder="제목을 입력해주세요."></td>
					</tr>
					<tr>
						<td><textarea placeholder="내용을 입력해주세요."></textarea></td>
					</tr>
				</table>
				<div id = "b_form_btn">
					<input type = "submit" class = "btn_red" value = "등록">
					<input type = "button" class = "btn_white" onclick = "javascript:history.back()" value = "취소">
				</div>
				</div>
			</form>
		</div>
	</div>
<%@ include file = "../include/bottom.jsp" %>