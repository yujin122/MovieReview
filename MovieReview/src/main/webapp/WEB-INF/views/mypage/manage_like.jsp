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
<script type="text/javascript" src="${path }/resources/js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	function review_form(mov_code){
		
		$.ajax({
			url: "${path}/review_check.do",
			dataType: "text",
			data: {
				"mov_code": mov_code
			},
			success: function(data){
				if(data > 0){
					alert("이미 리뷰를 작성한 영화입니다.");
				}else{
					location = "${pageContext.request.contextPath}/review_write_form.do?mov_num="+mov_code		
				}
			},
			error : function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	
	function like(mov_num){
		$.ajax({
			url : "${pageContext.request.contextPath }/like.do",
			dataType : "text",
			data : {
				"mov_num" : mov_num
			},
			success : function(data){
				var path = "${pageContext.request.contextPath }/resources/img/"+data;
				$('#like_'+mov_num).attr('src',path);
			},
			error : function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});	
	}
</script>
</head>
<body>
	<%@ include file="../include/my_info.jsp"%>
	
	<div id = "mypage_mov">
		<c:set var = "mov_list" value = "${list }" />
		<c:if test="${!empty mov_list }">
			<c:forEach items="${mov_list }" var = "mov">
				<table>
					<tr>
						<td rowspan = "5"><img src = "${path }/resources/img/heart.png" class = "like" id = "like_${mov.getMov_code() }" onclick="like('${mov.getMov_code() }')"></td>
						<td rowspan = "5"><img src="${mov.getMov_poster() }" class="post"></td>
						<td><a href = "${mov.getMov_link() }">${mov.getMov_title() }</a></td>
						<td>
							<c:if test="${mov.getMov_avgrating() != '0.0' }">
								<span>★</span>${mov.getMov_avgrating() }
							</c:if>
						</td>
						<td rowspan = "5"><button class = "btn_red" onclick = "review_form('${mov.getMov_code() }')">리뷰쓰기</button></td>
					</tr>
					<tr></tr>
					<tr>
						<td colspan = "2">${mov.getMov_director() } | ${mov.getMov_nations() } | ${mov.getMov_opendt().substring(0,4) }.${mov.getMov_opendt().substring(4,6) }.${mov.getMov_opendt().substring(6,8) }</td>
					</tr>
					<tr>
						<td colspan = "2">${mov.getMov_genres() } | ${mov.getMov_showtm() }분</td>
					</tr>
					<tr>
						<td colspan = "2">${mov.getMov_actors() }</td>
					</tr>
				</table>
			</c:forEach>
		</c:if>
		<c:if test="${empty mov_list }">
			<h3>찜한 영화가 존재하지 않습니다.</h3>
		</c:if>
		<div id ="paging_box">
			<c:if test="${pagination.prev }">
				<a href = "${path }/my_like_mov.do?page=${pagination.StartPage-1 }"> < </a>
			</c:if>
			<c:forEach begin = "${pagination.startPage }" end = "${pagination.endPage }" var = "i">
				<c:if test="${pagination.page == i }">
					<b><a href = "${path }/my_like_mov.do?page=${i }">${i }</a></b>
				</c:if>
				<c:if test="${pagination.page != i }">
					<a href = "${path }/my_like_mov.do?page=${i }">${i }</a>
				</c:if>
			</c:forEach>
			<c:if test="${pagination.next }">
				<a href = "${path }/my_like_mov.do?page=${pagination.endPage+1 }">i</a>
			</c:if>
		</div>
	</div>
	</div>
</div>

	<%@ include file = "../include/bottom.jsp" %>