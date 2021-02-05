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
	
	<div id = "mypage_list">
		<h3>내가 쓴 리뷰</h3>
		<form method = "post" action = "#">
			<input type = "submit" value = "삭제" class = "btn_white">
			<table id = "mov_tb">
				<tr>
					<th></th>
					<th>번호</th>
					<th>별점</th>
					<th>영화</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회수</th>
				</tr>
				<tr>
					<td><input type = "checkbox" name = "check"></td>
					<td>1</td>
					<td><span>★</span>4.5</td>
					<td id = "tit_td"><a href = "#">삼진웅앵웅</a></td>
					<td>첼</td>
					<td>2020.10.09</td>
					<td>3</td>
				</tr>
			</table>
		</form>
		<div id ="paging_box">
			<a href = "#">[1]</a>
		</div>
	</div>
	</div>
</div>

	<%@ include file = "../include/bottom.jsp" %>