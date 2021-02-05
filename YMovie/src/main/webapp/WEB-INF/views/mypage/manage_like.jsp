<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href="../css/mypage.css">
</head>
<body>
	<%@ include file="../include/my_info.jsp"%>
	
	<div id = "mypage_mov">
		<h3>찜한 영화</h3>
		<table>
			<tr>
				<td rowspan = "5"><img src = "../img/heart.png" id = "like" onclick="#"></td>
				<td rowspan = "5"><img src="../img/post.jpg" id="post"></td>
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
		<div id ="paging_box">
			<a href = "#">[1]</a>
		</div>
	</div>
	</div>
</div>

	<%@ include file = "../include/bottom.jsp" %>