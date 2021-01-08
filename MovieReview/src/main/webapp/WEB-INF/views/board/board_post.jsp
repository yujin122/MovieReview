<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href="${path }/resources/css/board.css">
</head>
<body>
<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		<div id = "board_post">
			<div id = "b_cont">
				<p id = "tit"><span id = "cate">[잡담] </span>안녕하세요</p>
				<p id = "mem_info"><img id = "info_img" src="${path }/resources/img/pro.jpg"> 첼<span class="mem">2021.01.03 08:08</span><span class="mem">조회수 : 8 </span></p>
				<textarea readonly>안녕하세요! 오늘 가입했어요~ 잘부탁드립니다!</textarea>
			</div>
			<div id = "comment_box">
				<p>댓글</p>
				<div id = "comm">
					<table>
						<tr>
							<td rowspan = "2"><img src="${path }/resources/img/13.jpg"></td>
							<td>밍 <span>2021.01.03 08:10 </span> </td>
							<td><a href="#">답글</a></td>
							<td><a href="#">수정</a></td>
							<td><a href="#">삭제</a></td>
						</tr>
						<tr>
							<td colspan = "4">
								안녕하세요!!
							</td>
						</tr>
						<tr>
							<td></td>
							<td colspan = "3">
								<textarea>안녕하세요!!</textarea>
							</td>
							<td>
								<button class = "btn_red">등록</button>
								<button class = "btn_white">취소</button>
							</td>
						</tr>
					</table>
				</div>
				<div id="paging_box">
					<a href = "#">[1]</a>
				</div>
				<form method = "post" action = "#">
					<div id = "comm_form">
						<table>
							<tr>
								<td><textarea name="comm" placeholder="댓글을 입력해주세요."></textarea></td>
								<td><input type = "submit" class = "btn_white" value = "등록"></td>
							</tr>
						</table>
					</div>
				</form>
				<div id = "b_btn_box">
					<button class="btn_white">수정</button>
					<button class="btn_white">삭제</button>
					<button class="btn_red">목록</button>
				</div>
			</div>
		</div>
	</div>
<%@ include file = "../include/bottom.jsp" %>