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
		<h3>자유게시판</h3>
		<div id = "bor_list">
			<table id = "mov_tb">
				<tr>
					<th>번호</th>
					<th>카테고리</th>
					<th>제목</th>
					<th>작성자</th>
					<th>날짜</th>
					<th>조회수</th>
				</tr>
				<tr>
					<td>1</td>
					<td>잡담</td>
					<td id = "tit_td"><a href = "#">안녕하세요~</a></td>
					<td>첼</td>
					<td>2020.10.09</td>
					<td>3</td>
				</tr>
			</table>	
		</div>
		<div id ="paging_box">
			<a href = "#">[1]</a>
		</div>
		<div id="b_search">
			<form action="#">
				<select name="label" id="search_label">
					<option value="title">영화</option>
					<option value="cont">본문</option>
					<option value="writer">글쓴이</option>
				</select><input type="text" id="search_txt" name="search"><input type="submit" id="search_btn" value="검색">
			</form>
		</div>
	</div>
<%@ include file = "../include/bottom.jsp" %>