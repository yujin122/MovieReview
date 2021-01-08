<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href="../css/mypage.css">
</head>
<body>

	<%@ include file="../include/my_info.jsp"%>
	
	<div id = "mypage_menu">
		<table>
			<tr>
				<td><button>내가 쓴 리뷰</button></td>
				<td><button>내가 쓴 게시물</button></td>
				<td><button>내가 쓴 댓글</button></td>
			</tr>
			<tr>
				<td><button>찜한 영화</button></td>
				<td><button>회원 정보 수정</button></td>
				<td><button>Q&A</button></td>
			</tr>
		</table>
	</div>
</div>

	<%@ include file = "../include/bottom.jsp" %>