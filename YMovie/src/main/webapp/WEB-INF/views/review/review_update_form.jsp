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
<script type="text/javascript">
	function r_edit_check(){
		if(frm.r_cont.value == ""){
			alert("내용을 입력해주세요");
			frm.r_cont.focus();
			return false;
		}
	}
</script>
</head>
<body>

<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		<div id ="review_post">
			<div id="mov_info">
				<table>
					<tr>
						<td rowspan = "5" id = "post_td"><img src="${path }/resources/img/post.jpg"></td>
						<td colspan = "4">삼진그룹 영어토익반</td>
					</tr>
					<tr>
						<th>감독</th>
						<td>이종필</td>
						<th>장르</th>
						<td>드라마</td>
					</tr>
					<tr>
						<th>상영시간</th>
						<td>110분</td>
						<th>제작국가</th>
						<td>한국</td>
					</tr>
					<tr>
						<th>주연배우</th>
						<td colspan = "3">고아성, 이솜, 박혜수 외 ...</td>
					</tr>
					<tr>
						<th>평균 평점</th>
						<td colspan = "3"><span>★</span>5.0</td>
					</tr>
				</table>
			</div>
			<form name = "frm" method = "post" action = "#" onsubmit="return r_edit_check()">
				<div id = "review_cont">
					평점 : <select name = "point">
						<option>5.0</option>
						<option>4.5</option>
						<option>4.0</option>
						<option>3.5</option>
						<option>3.0</option>
						<option>2.5</option>
						<option>2.0</option>
						<option>1.5</option>
						<option>1.0</option>
						<option>0.5</option>
						<option>0.0</option>
					</select>
					<textarea name = "r_cont">배우들 케미가 너무 좋아요!!! 넘 재밌게봤습니다!</textarea>
				</div>
				<div id = "review_up_btn">
					<input type = "submit" class="btn_red" value = "수정">
					<button class="btn_white" onclick = "javascript:history.back()">취소</button>
				</div>
			</form>
		</div>
	</div>
<%@ include file = "../include/bottom.jsp" %>
