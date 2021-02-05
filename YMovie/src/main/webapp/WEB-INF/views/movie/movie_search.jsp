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
				if(data){
					console.log(data);
					
					$("#search_list").empty();
					
					var tb = "";
					
					var arr = data[0].mov_info;
					var like = data[2].like
					for(i in arr){
						var mov = arr[i];
						console.log(data);
						
						tb += "<table><tr>";
						tb += "<td rowspan = '5'>";
						if('${session_mem_id}' != ''){
							tb += "<img src = '${pageContext.request.contextPath }/resources/img/"+like[i]+"' class = 'like' id = 'like_"+mov.movieCd+"' onclick='like(\""+mov.movieCd+"\")'>";	
						}else{
							tb += "<img src = '${pageContext.request.contextPath }/resources/img/heart_empty.png' class = 'like' id = 'like_"+mov.movieCd+"' onclick='like(\""+mov.movieCd+"\")'>";
						}
						tb += "</td>";
						tb += "<td rowspan = '5'><img src='"+mov.img+"' id='post'></td>";
						tb += "<td><a href ='"+mov.link+"'>"+mov.movieNm+"</a></td>";
						tb += "<td><span>★</span>4.8</td>";
						tb += "<td rowspan = '5'><button class = 'btn_red' onclick = 'location = \"${pageContext.request.contextPath}/review_write_form.do?mov_num="+mov.movieCd+"\"'>리뷰쓰기</button></td>";
						tb += "</tr><tr></tr><tr>";
						tb += "<td colspan = '2'>";
						
						for(j in mov.directors){
							tb += mov.directors[j].peopleNm + " ";
						}
						tb += "| ";
						for(p in mov.nations){
							tb += mov.nations[p].nationNm + " ";
						}
						tb += "| ";
						if(mov.openDt != ""){
							tb += mov.openDt.substring(0,4) + "." + mov.openDt.substring(4,6) + "." + mov.openDt.substring(6,8);
						}
						tb += "</td>";
						tb += "</tr><tr>";
						tb += "<td colspan = '2'>";
						for(u in mov.genres){
							tb += mov.genres[u].genreNm + " ";
						}
						tb += "| ";
						if(mov.showTm != ""){
							tb += mov.showTm + "분";
						}
						tb += "</td>";
						tb += "</tr><tr>";
						tb += "<td colspan = '2'>";
						if(mov.actors.length > 5){
							for(var n = 0; n < 5; n++){
								tb += mov.actors[n].peopleNm + " ";
							}
							tb += "외 " + (mov.actors.length - 5) + "명";
						}else {
							for(m in mov.actors){
								tb += mov.actors[m].peopleNm + " ";
							}
						}
						tb += "</td></tr></table>";
					}
					
					var paging = data[1];
					var page = "";
					
					page += "<div id = 'paging_box'>";
					
					if(paging.prev){
						page += "<a href = 'javascript:mov_list(\""+(paging.startPage-1)+"\");'> < </a>";
					}
					for(var i = paging.startPage; i <= paging.endPage; i++){
						if(paging.page == i){
							page += "<b><a href = 'javascript:mov_list(\""+i+"\");'>["+i+"]</a></b>";	
						}else{
							page += "<a href = 'javascript:mov_list(\""+i+"\");'>["+i+"]</a>";
						}			
					}
					if(paging.next){
						page += "<a href = 'javascript:mov_list(\""+(paging.endPage+1)+"\");'> > </a>";
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
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		</div>
		
	</div>
<%@ include file = "../include/bottom.jsp" %>