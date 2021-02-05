<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id = "side_bar">
		<h2>마이페이지</h2>
		<ul>
			<li><a href = "${pageContext.request.contextPath }/mypage_main.do">마이페이지</a></li>
			<li><a href = "#">내가 쓴 리뷰</a></li>
			<li><a href = "${pageContext.request.contextPath }/my_board_list.do">내가 쓴 게시물</a></li>
			<li><a href = "${pageContext.request.contextPath }/my_comm_list.do">내가 쓴 댓글</a></li>
			<li><a href = "#">찜한 영화</a></li>
			<li><a href = "#">회원 정보 수정</a></li>
		</ul>
	</div>
</body>
</html>