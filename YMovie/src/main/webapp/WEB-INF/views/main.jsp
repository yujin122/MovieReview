<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<title>Home</title>
<link rel = "stylesheet" href="${path }/resources/css/review.css">
</head>
<body>

<%@ include file = "./include/top.jsp" %>

	<div id = "content">
		<h3>리뷰 게시판</h3>
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
		<div id="r_search">
			<form name = "frm" method = "post" action="#" onsubmit = "return check()">
				<select name="label" id="search_label">
					<option value="title">영화</option>
					<option value="cont">본문</option>
					<option value="writer">글쓴이</option>
				</select><input type="text" class="search_txt" name="search"><input type="submit" class="search_btn" value="검색">
			</form>
		</div>
	</div>
<%@ include file = "./include/bottom.jsp" %>
