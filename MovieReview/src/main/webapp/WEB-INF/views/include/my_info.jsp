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
	
	<div id = "content">
		<div id = "my_info">
			<table>
				<tr>
					<td rowspan="2">
						<img id = "info_img" src="../img/pro.jpg">
					</td>
					<th>총 리뷰</th>
					<th>총 게시물</th>
					<th><img id = "heart_img" src="../img/heart.png"></th>
				</tr>
				<tr>
					<td rowspan = "2">1</td>
					<td rowspan = "2">2</td>
					<td rowspan = "2">3</td>
				</tr>
				<tr>
					<td>
						<p><span>첼</span>(qhfk48)</p>
					</td>
				</tr>
			</table>
		</div>