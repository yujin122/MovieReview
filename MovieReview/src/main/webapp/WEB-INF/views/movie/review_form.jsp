<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			alert('${msg}');
		}
	})
</script>
</head>
<body>

<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		<div id ="review_post">
			<form method = "post" action = "${pageContext.request.contextPath }/review_insert.do">
				<div id="mov_info">
				<c:set var = "dto" value = "${dto }" />
				<input type = "hidden" name = "mov_code" value = "${dto.getMov_code() }">
				<input type = "hidden" name = "mov_opendt" value = "${dto.getMov_opendt() }">
				<input type = "hidden" name = "mov_prdtyear" value = "${dto.getMov_prdtyear() }">
				<input type = "hidden" name = "mov_link" value = "${dto.getMov_link() }">
					<table>
						<tr>
							<td rowspan = "5" id = "post_td">
								<c:if test="${dto.getMov_poster() == null }">
									<input type = "hidden" name = "mov_poster" value = "${dto.getMov_poster() }">
									<img src="${path }/resources/img/nophoto.png">
								</c:if>
								<c:if test="${dto.getMov_poster() != null }">
									<input type = "hidden" name = "mov_poster" value = "${dto.getMov_poster() }">
									<img src="${dto.getMov_poster() }">
								</c:if>
							</td>
							<td colspan = "4">
								<input type = "hidden" name = "mov_title" value = "${dto.getMov_title() }">
								${dto.getMov_title() }
							</td>
						</tr>
						<tr>
							<th>감독</th>
							<td>
								<input type = "hidden" name = "mov_director" value = "${dto.getMov_director() }">
								${dto.getMov_director() }
							</td>
							<th>장르</th>
							<td>
								<c:set var = "genre" value = "${genre }" />
								<input type = "hidden" value = "${genre }" name = "mov_genre">
								<c:forEach items="${fn:split(genre, '|') }" var = "g">
									${g } 
								</c:forEach>
							</td>
						</tr>
						<tr>
							<th>상영시간</th>
							<td>
								<input type = "hidden" name = "mov_showtm" value = "${dto.getMov_showtm() }">
								${dto.getMov_showtm() }
							</td>
							<th>제작국가</th>
							<td>
								<input type = "hidden" name = "mov_nations" value = "${dto.getMov_nations() }">
								${dto.getMov_nations() }
							</td>
						</tr>
						<tr>
							<th>주연배우</th>
							<td colspan = "3">
								<input type = "hidden" name = "mov_actors" value = "${dto.getMov_actors() }">
								${dto.getMov_actors() }
							</td>
						</tr>
						<tr>
							<c:if test="${dto.getMov_avgrating() != '0.0'}">
								<th>평균 평점</th>
								<td>
									<span>★</span><fmt:formatNumber value="${dto.getMov_avgrating() }" pattern=".0" />
								</td>
							</c:if>
							<c:if test="${expect != 0 }">
								<th>예상 평점</th>
									<td>
										<span>★</span><fmt:formatNumber value="${expect }" pattern=".0" />
									</td>
							</c:if>
						</tr>
					</table>
				</div>
				<div id = "review_cont">
					평점 : <select name = "review_rating">
						<option value = "5.0">5.0</option>
						<option value = "4.5">4.5</option>
						<option value = "4.0">4.0</option>
						<option value = "3.5">3.5</option>
						<option value = "3.0">3.0</option>
						<option value = "2.5">2.5</option>
						<option value = "2.0">2.0</option>
						<option value = "1.5">1.5</option>
						<option value = "1.0">1.0</option>
						<option value = "0.5">0.5</option>
						<option value = "0.0">0.0</option>
					</select>
					<textarea name = "review_cont"></textarea>
				</div>
				<div id = "review_up_btn">
					<input type = "submit" value = "등록" class = "btn_red">
					<input type = "button" onclick = "javascript:history.back()" value = "취소"  class="btn_white">
				</div>
			</form>
		</div>
		
	</div>
<%@ include file = "../include/bottom.jsp" %>