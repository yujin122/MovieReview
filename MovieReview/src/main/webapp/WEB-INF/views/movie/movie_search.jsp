<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${path }/resources/css/movie.css">
<script type="text/javascript" src="${path }/resources/js/jquery-3.5.1.js"></script>
<script type="text/javascript">
$(function(){
	$("#mov_search").keyup(function(event){
		var txt = $("#mov_txt").val();
		if(event.which === 13){
			mov_list(1);
		}
	});
});	
	
	function mov_list(page){
		var txt = $("#mov_txt").val();
		
		$.ajax({
			url : "${pageContext.request.contextPath }/mov_list.do",
			
			dataType : "json",
			data : {
				"txt" : txt,
				"page" : page
			},
			success : function(data){
				console.log(data);
				
				if(data){
					
					$("#search_list").empty();
					
					var tb = "";
					
					var mov_info = data.mov_info;
					var mov_like = data.mov_like;
					var mov_poster = data.mov_poster;
					console.log(mov_info);
					console.log(mov_like);
					console.log(mov_poster);
					
					for(i in mov_info){
						var mov_detail = mov_info[i].mov_info;
						
						tb += "<table><tr>";
						tb += "<td rowspan = '5'>";
						if('${session_mem_id}' != ''){
							tb += "<img src = '${pageContext.request.contextPath }/resources/img/"+mov_like[i]+"' class = 'like' id = 'like_"+mov_detail.mov_code+"' onclick='like(\""+mov_detail.mov_code+"\")'>";	
						}else{
							tb += "<img src = '${pageContext.request.contextPath }/resources/img/heart_empty.png' class = 'like' id = 'like_"+mov_detail.mov_code+"' onclick='like(\""+mov_detail.mov_code+"\")'>";
						}
						tb += "</td>";
						tb += "<td rowspan = '5'><img src='"+mov_poster[i].mov_poster+"' id='post'></td>";
						tb += "<td><a href ='"+mov_poster[i].mov_link+"'>"+mov_detail.mov_title+"</a></td>";
						tb += "<td>";
						if(mov_detail.mov_avgrating != null){
							tb += "<span>★</span>"+ mov_detail.mov_avgrating;
						}
						tb += "</td>";
						tb += "<td rowspan = '5'>";
						if('${session_mem_id}' != ''){
							tb += "<button class = 'btn_red' onclick = 'review_form(\""+mov_detail.mov_code+"\")'>리뷰쓰기</button></td>"
						}
						tb += "</tr><tr></tr><tr>";
						tb += "<td colspan = '2'>";
						
						tb += mov_detail.mov_director;
						tb += mov_detail.mov_nations;
						if(mov_detail.mov_opendt != ""){
							tb += mov_detail.mov_opendt.substring(0,4) + "." + mov_detail.mov_opendt.substring(4,6) + "." + mov_detail.mov_opendt.substring(6,8);
						}
						tb += "</td>";
						tb += "</tr><tr>";
						tb += "<td colspan = '2'>";
						tb += mov_info[i].genre;
						if(mov_detail.mov_showtm != ""){
							tb += mov_detail.mov_showtm + "분";
						}
						tb += "</td>";
						tb += "</tr><tr>";
						tb += "<td colspan = '2'>";
						tb += mov_detail.mov_actors;
						tb += "</td></tr></table>";
					}
					
					var pagination = data.pagination;
					var page = "";
					
					page += "<div id = 'paging_box'>";
					
					if(pagination.prev){
						page += "<a href = 'javascript:mov_list(\""+(pagination.startPage-1)+"\");'> < </a>";
					}
					for(var i = pagination.startPage; i <= pagination.endPage; i++){
						if(pagination.page == i){
							page += "<b><a href = 'javascript:mov_list(\""+i+"\");'>["+i+"]</a></b>";	
						}else{
							page += "<a href = 'javascript:mov_list(\""+i+"\");'>["+i+"]</a>";
						}			
					}
					if(pagination.next){
						page += "<a href = 'javascript:mov_list(\""+(pagination.endPage-1)+"\");'> > </a>";
					}
					
					page += "</div>"
					
					$("#search_list").append(tb);
				 	$("#search_list").append(page); 
					
				}else{
					alert("영화 가져오기 실패");
				}
			},
			error : function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		})
	} 
	
	function like(mov_num){
		if('${session_mem_id}' != ''){
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
		}else{
			alert('로그인 후 이용가능합니다.');
		}
		
	}
	
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

</script>
</head>
<body>
<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		<div id="mov_search">
			<img src="${path }/resources/img/logo_box.png">
			<h2>영화 검색</h2><br>
			<input type = "text" id = "mov_txt" placeholder="영화이름입력">
			<hr>
		</div>
		<div id = "search_list">
		<c:forEach begin="1" end="${end }">
			
		</c:forEach>
		</div>
		
	</div>
<%@ include file = "../include/bottom.jsp" %>