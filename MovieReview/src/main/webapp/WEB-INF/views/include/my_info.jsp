<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel ="stylesheet" href="${path }/resources/css/my_info.css">
</head>
<body>
	<%@ include file = "../include/top.jsp" %>

	<div id = "my_content">
		<%@ include file = "../include/side_bar.jsp" %>
		<div id = "mypage_main">
			<div id = "my_info">
				<c:set var = "member" value="${mem_dto }" />
				<table>
					<tr>
						<td rowspan="2">
							<c:if test="${empty member.getMem_img() }">
								<img id = "info_img" src="${path }/resources/img/default.png">	
							</c:if>
							<c:if test="${!empty member.getMem_img() }">
								<img id = "info_img" src="${path }/resources/upload/${member.getMem_img()}">
							</c:if>
						</td>
						<th>총 리뷰</th>
						<th>총 게시물</th>
						<th><img id = "heart_img" src="${path }/resources/img/heart.png"></th>
					</tr>
					<tr>
						<td rowspan = "2">${reviewCnt }</td>
						<td rowspan = "2">${boardCnt }</td>
						<td rowspan = "2">${likeCnt }</td>
					</tr>
					<tr>
						<td>
							<p><span>${member.getMem_id() }</span></p>
						</td>
					</tr>
				</table>
			</div>