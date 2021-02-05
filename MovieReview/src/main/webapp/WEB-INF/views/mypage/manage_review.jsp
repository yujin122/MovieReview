<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href="${path }/resources/css/mypage.css">
<style type="text/css">
	#side_bar ul li:nth-child(2) a {font-weight: bold; color : #ff3131; text-decoration: underline;}
</style>
<script type="text/javascript" src="${path }/resources/js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	$(function(){
		$("#allcheck").click(function(){
			if($("#allcheck").prop("checked")){
				$("input:checkbox").prop("checked", true);
			}else{
				$("input:checkbox").prop("checked", false);
			}
		});
		
		if('${msg}' != ''){
			alert('${msg}');
		}
	});
	
	function review_del_check(){
		if(!$("input:checkbox").is(":checked")){
			alert('삭제할 게시글을 선택해주세요');
			return false;
		}
		
		if(confirm('게시글을 삭제하시겠습니까?')){
			return true;	
		}else
			return false;
	}
</script>
</head>
<body>

	<%@ include file="../include/my_info.jsp"%>
	
	<div id = "mypage_list">
		<form method = "post" action = "${path }/my_review_del.do" onsubmit="return review_del_check()">
			<input type = "submit" value = "삭제" class = "btn_white">
			<table id = "mov_tb">
				<tr>
					<th><input type = "checkbox" id = "allcheck"></th>
					<th>번호</th>
					<th>별점</th>
					<th>영화</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회수</th>
				</tr>
				<c:set var = "list" value = "${list }"/>
				<c:if test="${!empty list }">
					<c:forEach items="${list }" var = "review">
						<tr>
							<td><input type = "checkbox" name = "check" value = "${review.getReview_num() }"></td>
							<td>${review.getReview_num() }</td>
							<td><span id = "rating">★</span>${review.getReview_rating() }</td>
							<td><a href = "${pageContext.request.contextPath }/review_post.do?review_num=${review.getReview_num()}">${review.getMov_title() }</a></td>
							<td>${review.getReview_writer() }</td>
							<td>${review.getReview_regdate().substring(0,10) }</td>
							<td>${review.getReview_hit() }</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty list }">
					<tr>
						<td colspan = "7">
							<h3>아직 쓴 리뷰가 없습니다.</h3>
						</td>
					</tr>
				</c:if>
			</table>
		</form>
		<div id ="paging_box">
			<c:if test="${pagination.prev }">
				<a href = "${path }/my_review_list.do?page=${pagination.StartPage-1 }"> < </a>
			</c:if>
			<c:forEach begin = "${pagination.startPage }" end = "${pagination.endPage }" var = "i">
				<c:if test="${pagination.page == i }">
					<b><a href = "${path }/my_review_list.do?page=${i }">${i }</a></b>
				</c:if>
				<c:if test="${pagination.page != i }">
					<a href = "${path }/my_review_list.do?page=${i }">${i }</a>
				</c:if>
			</c:forEach>
			<c:if test="${pagination.next }">
				<a href = "${path }/my_review_list.do?page=${pagination.endPage+1 }">i</a>
			</c:if>
		</div>
	</div>
	</div>
</div>

	<%@ include file = "../include/bottom.jsp" %>