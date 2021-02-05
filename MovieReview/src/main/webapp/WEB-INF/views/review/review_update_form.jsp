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
<script type="text/javascript" src="${path }/resources/js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	$(function(){
		if('${msg}' != ''){
			alert("${msg}");
		}
	});
	
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
				<c:set var = "info" value = "${dto }" />
					<table>
						<tr>
							<td rowspan = "5" id = "post_td">
								<c:if test="${!empty info.getMov_poster() }">
									<img src="${info.getMov_poster() }">
								</c:if>
								<c:if test="${empty info.getMov_poster() }">
									<img src="${path }/resources/img/nophoto.png">
								</c:if>
							</td>
							<td colspan = "4">${info.getMov_title() }</td>
						</tr>
						<tr>
							<th>감독</th>
							<td>${info.getMov_director() }</td>
							<th>장르</th>
							<td>
								<c:set value="${g_list }" var = "genre_list"/>
								<c:if test="${!empty genre_list }">
									<c:forEach items="${genre_list }" var = "genre">
										${genre.getMov_genre_name() } 
									</c:forEach>
								</c:if>
							</td>
						</tr>
						<tr>
							<th>상영시간</th>
							<td>${info.getMov_showtm() }</td>
							<th>제작국가</th>
							<td>${info.getMov_nations() }</td>
						</tr>
						<tr>
							<th>주연배우</th>
							<td colspan = "3">${info.getMov_actors() }</td>
						</tr>
						<tr>
							<th>평균 평점</th>
							<td colspan = "3"><span>★</span></td>
						</tr>
					</table>
				</div>
				<form name = "frm" method = "post" action = "${path }/review_update.do" onsubmit="return r_edit_check()">
					<input type = "hidden" id = "review_num" name = "review_num" value = "${info.getReview_num() }">
					<div id = "review_cont">
						평점 : <select name = "review_rating">
							<option value = "5.0" <c:if test = "${info.getReview_rating() == '5.0'}">selected</c:if>>5.0</option>
							<option value = "4.5" <c:if test = "${info.getReview_rating() == '4.5'}">selected</c:if>>4.5</option>
							<option value = "4.0" <c:if test = "${info.getReview_rating() == '4.0'}">selected</c:if>>4.0</option>
							<option value = "3.5" <c:if test = "${info.getReview_rating() == '3.5'}">selected</c:if>>3.5</option>
							<option value = "3.0" <c:if test = "${info.getReview_rating() == '3.0'}">selected</c:if>>3.0</option>
							<option value = "2.5" <c:if test = "${info.getReview_rating() == '2.5'}">selected</c:if>>2.5</option>
							<option value = "2.0" <c:if test = "${info.getReview_rating() == '2.0'}">selected</c:if>>2.0</option>
							<option value = "1.5" <c:if test = "${info.getReview_rating() == '1.5'}">selected</c:if>>1.5</option>
							<option value = "1.0" <c:if test = "${info.getReview_rating() == '1.0'}">selected</c:if>>1.0</option>
							<option value = "0.5" <c:if test = "${info.getReview_rating() == '0.5'}">selected</c:if>>0.5</option>
							<option value = "0.0" <c:if test = "${info.getReview_rating() == '0.0'}">selected</c:if>>0.0</option>
						</select>
						<textarea name = "review_cont">${info.getReview_cont() }</textarea>
					</div>
					<div id = "review_up_btn">
						<input type = "submit" class="btn_red" value = "수정">
						<button class="btn_white" onclick = "javascript:history.back()">취소</button>
					</div>
				</form>
			</div>
		</div>
<%@ include file = "../include/bottom.jsp" %>
