<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${path }/resources/css/review.css">
</head>
<body>
<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		<div id="r_search">
			<p><span>삼진</span> 에 대한 검색 결과입니다.</p>
			<form action="#">
				<select name="label" id="search_label">
					<option value="title">영화</option>
					<option value="cont">본문</option>
					<option value="writer">글쓴이</option>
				</select><input type="text" id="search_txt" name="search"><input type="submit" id="search_btn" value="검색">
			</form>
		</div>
		<div id = "mov_list">
			<table id = "mov_tb">
				<tr>
					<th>번호</th>
					<th>별점</th>
					<th>영화</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회수</th>
				</tr>
				<tr>
					<td>1</td>
					<td><span>★</span>4.5</td>
					<td id = "tit_td"><a href = "#">삼진웅앵웅</a></td>
					<td>첼</td>
					<td>2020.10.09</td>
					<td>3</td>
				</tr>
			</table>	
		</div>
		<div id ="paging_box">
			<a href = "#">[1]</a>
		</div>
	</div>
<%@ include file = "../include/bottom.jsp" %>