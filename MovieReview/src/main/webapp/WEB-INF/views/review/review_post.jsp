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
		like_check();
		like_cnt();
	});
	
	function like_check(){
		var review_num = $("#review_num").val();
		
		if('${session_mem_id}' != ''){
			$.ajax({
				url: "${path}/like_check.do",
				dataType: "text",
				data: {
					"review_num": review_num
				},
				success: function(data){
					var path = "";
					
					if(data > 0){
						path = "${path }/resources/img/heart.png";	
					}else{
						path = "${path }/resources/img/heart_empty.png";
					}
					
					$('#like_img').attr('src',path);
				},
				error : function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});	
		}else{
			$('#like_img').attr('src', "${path }/resources/img/heart_empty.png");
		}
	}
	
	function like_cnt(){
		var review_num = $("#review_num").val();
		
		$.ajax({
			url: "${path}/like_cnt.do",
			dataType: "text",
			data: {
				"review_num": review_num
			},
			success: function(data){
				
				$("#like_cnt").text(data);
			},
			error : function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	
	function add_like(){
		var review_num = $("#review_num").val();
		
		if('${session_mem_id}' == ''){
			alert('로그인 후 이용가능합니다.');
		}else{
			$.ajax({
				url: "${path}/review_like.do",
				dataType: "text",
				data: {
					"review_num": review_num
				},
				success: function(data){
					if(data > 0){
						like_check();
						like_cnt();
					}else{
						alert("좋아요 실패");
					}
				},
				error : function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});	
		}
	}
	
	function review_allList(){
		if('${search}' != '' && '${label}' != ''){
			location = '${path}/review_search.do?page=${page}&search=${search}&label=${label}';
		}else{
			location = '${path}/?page=${page}';
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
			<input type = "hidden" id = "review_num" value = "${info.getReview_num() }">
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
						<th>평점</th>
						<td colspan = "3"><span>★</span>${info.getReview_rating() }</td>
					</tr>
				</table>
			</div>
			<div id = "writer_info">
				<table>
					<tr>
						<td>
							<img id = "info_img" src="${path }/resources/upload/${info.getMem_img()}">
						</td>
						<td>${info.getReview_writer() }</td>
						<td>${info.getReview_regdate() }</td>
						<td>조회수 : ${info.getReview_hit() }</td>
					</tr>
				</table>
			</div>
			<div id = "review_cont">
				<textarea readonly>${info.getReview_cont() }</textarea>
			</div>
			<div id= "like_btn">
				<img id = "like_img" onclick = "add_like()"> <span id = "like_cnt"></span>
			</div>
			<div id = "review_btn">
				<c:if test="${session_mem_id == info.getReview_writer() }">
					<button class="btn_white" onclick = "location = '${path}/review_update_form.do?review_num=${info.getReview_num() }'">수정</button>
					<button class="btn_white" onclick = "if(confirm('삭제하시겠습니까?')){
						location = '${path}/review_delete.do?review_num=${info.getReview_num() }';
					}else{return;}">삭제</button>
				</c:if>
				<button class="btn_red" onclick = "review_allList()">목록</button>
			</div>
		</div>
	</div>
<%@ include file = "../include/bottom.jsp" %>
