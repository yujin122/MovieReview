<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel = "stylesheet" href="${path }/resources/css/board.css">
</head>
<body>
<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		
		<div id = "board_post">
			<h3>자유게시판</h3>
			<form method = "post" action = "${pageContext.request.contextPath }/board_update.do">
			<c:set var = "board" value = "${dto }" />
			<input type = "hidden" name = "board_num" value = "${board.getBoard_num() }">
			<input type = "hidden" name = "page" value = "${page }">
			<c:if test="${!empty label }">
				<input type = "hidden" name = "label" value = "${label }">
				<input type = "hidden" name = "keyword" value = "${keyword }">
			</c:if>
				<div id = "b_form">
				<table>
					<tr>
						<td>
							<select name = "board_category">
								<option value = "잡담" <c:if test = "${board.getBoard_category() == '잡담' }">selected</c:if>>잡담</option>
								<option value = "영화추천" <c:if test = "${board.getBoard_category() == '영화추천' }">selected</c:if>>영화추천</option>
								<option value = "질문" <c:if test = "${board.getBoard_category() == '질문' }">selected</c:if>>질문</option>
							</select>	
						</td>
					</tr>
					<tr>
						<td><input type = "text" name = "board_title" placeholder="제목을 입력해주세요." value = ${board.getBoard_title() }></td>
					</tr>
					<tr>
						<td><textarea placeholder="내용을 입력해주세요." name = "board_cont">${board.getBoard_cont() }</textarea></td>
					</tr>
				</table>
				<div id = "b_form_btn">
					<input type = "submit" class = "btn_red" value = "수정">
					<input type = "button" class = "btn_white" onclick = "javascript:history.back()" value = "취소">
				</div>
				</div>
			</form>
		</div>
	</div>
<%@ include file = "../include/bottom.jsp" %>