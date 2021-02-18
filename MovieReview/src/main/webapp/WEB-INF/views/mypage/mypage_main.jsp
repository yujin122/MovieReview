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
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.7.3/dist/Chart.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@0.7.0"></script>
<script type="text/javascript">
	
	function mov_chart_data(){
		$.ajax({
			url: "${path}/mov_review_chart.do",
			dataType: "json",
			success: function(data){
				console.log(data);
				var labelList = [];
				var dataList = [];
				
				for(i in data){
					labelList.push(data[i].mov_genre_name);
					dataList.push(data[i].cnt);
				}
				
				
				chartDrawing(labelList, dataList);
				
				var cnt = 1;
				var li = "";
				li += "<ul id = 'ul1'>";
				for(j in labelList){
					li += "<li>"+labelList[j]+"</li>";
					if(cnt % 4 == 0){
						li += "</ul><br>";
						li += "<ul id = 'ul2'>";
					}
					cnt += 1;
				}
				li += "</ul>";
				
				$("#chart_box").append(li);
			},
			error : function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	
	function chartDrawing(labelList, dataList){
		var ctx = document.getElementById('myChart');
		var ctx = document.getElementById('myChart').getContext('2d');
		
		var myChart = new Chart(ctx, {
			type: 'pie',
			data: {
				labels: labelList,
				datasets: [{
					data: dataList,
					backgroundColor: [
						'rgba(235, 29, 14, 0.3)',
		                'rgba(235, 158, 14, 0.3)',
		                'rgba(242, 250, 90, 0.3)',
		                'rgba(118, 214, 58, 0.3)',
		                'rgba(47, 204, 247, 0.3)',
		                'rgba(142, 69, 237, 0.3)',
		                'rgba(240, 120, 212, 0.3)',
		                'rgba(89, 46, 29, 0.3)'
					],
					borderColor: [
						'rgba(235, 29, 14, 1)',
		                'rgba(235, 158, 14, 1)',
		                'rgba(242, 250, 90, 1)',
		                'rgba(118, 214, 58, 1)',
		                'rgba(47, 204, 247, 1)',
		                'rgba(142, 69, 237, 1)',
		                'rgba(240, 120, 212, 1)',
		                'rgba(89, 46, 29, 1)'
					],
					borderWidth: 1
				}]
			},
			options: {
				responsive: false,
				layout: {
					padding: {
						top: 10,
						left: 0,
						right: 0,
						bottom: 20
					}
				},
				legend: {
					display: false
				},
		        plugins: {
		            // Change options for ALL labels of THIS CHART
		            datalabels: {
		                color: '#36A2EB',
		            }
		        }
		    }
		}) 
	}
</script>
<style type="text/css">
	#side_bar ul li:nth-child(1) a {font-weight: bold; color : #ff3131; text-decoration: underline;}
</style>
</head>
<body>
	
	<%@ include file="../include/my_info.jsp"%>
	
	<div id = "mypage_menu">
		<h3># 취향 분포도</h3>
		<c:set var="movList" value="${mov_list }" />
		<c:if test="${!empty movList }">
			<div id = "chart">
				<div id = "chart_box">
				<canvas id = "myChart" width="auto" height="320"></canvas>
				<script type="text/javascript">
					mov_chart_data()
				</script>
				</div>
				<div id = "recommend_box">
					<h4>추천</h4>
						<c:forEach items="${movList }" var = "movie">
							<c:set var = "cnt" value = "${cnt+1 }" />
							<table onclick = "location = '${movie.getMov_link() }'">
								<tr>
									<th><img src="${movie.getMov_poster() }"></th>
								</tr>
								<tr>
									<td>${movie.getMov_title() }</td>
								</tr>
							</table>
							<c:if test="${cnt%3 == 0 }">
								<br>
							</c:if>
						</c:forEach>
				</div>
			</div>
		</c:if>
		<c:if test="${empty movList }">
			<h4>리뷰한 영화가 없습니다.</h4>
		</c:if>
		<div id = "like_mov">
			<h3># 찜한 영화</h3><span><a href = "${path }/my_like_mov.do">더보기 ></a></span><br>
			<c:set var= "likeList" value = "${like_list }" />
			<c:if test="${!empty likeList }">
				<c:forEach items="${likeList }" var = "like">
				<c:set var = "cntt" value = "${cntt+1 }" />
					<table onclick = "location = '${like.getMov_link() }'">
						<tr>
							<th><img src="${like.getMov_poster() }"></th>
						</tr>
						<tr>
							<td>${like.getMov_title() }</td>
						</tr>
					</table>
					<c:if test="${cntt%6 == 0 }">
						<br>
					</c:if>
				</c:forEach>
			</c:if>
			<c:if test="${empty likeList }">
				<h4>아직 찜한 영화가 없습니다.</h4>
			</c:if>
		</div>
	</div>
	</div>
</div>
	<%@ include file = "../include/bottom.jsp" %>