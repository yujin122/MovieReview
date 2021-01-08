<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/user.css">
<link rel="stylesheet" href="../css/edit.css">
</head>
<body>
<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		<div id="join_box">
			<img src="../img/logo_box.png">
			<h2>회원정보수정</h2>
			<form name = "frm" action="#" onsubmit = "return check()">
				<table>
					<tr>
						<th>이름 </th>
						<td><input type = "text" name = "name" id = "name" size = "20">
					</tr>
					<tr>
						<th>아이디</th>
						<td><input type = "text" name = "id" id = "id" size = "20">
						</td>	
					</tr>
					<tr>
						<th>비밀번호</th>
						<td><input type = "password" name = "pwd1" id = "pwd_1"><br/>
							<!--  <font size = "2">(영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 8자~16자)</font> -->
						</td>
					</tr>
					<tr>
						<th>비밀번호 확인</th>
						<td><input type = "password" name = "pwd2" id = "pwd_2">
						<p class = "txt"> </p>
						</td>
					</tr>
					<tr>
						<th>닉네임</th>
						<td><input type = "text" name = "nickname" id = "nickname" >
						</td>
					</tr>
					<tr>
						<th>생일</th>
						<td><input type = "date" name = "birth" ></td>
					</tr>
					<tr>
						<th>성별</th>
						<td><input type = "radio" name = "gender" value = "여자"> 여자
							<input type = "radio" name = "gender" value = "남자"> 남자
					</tr>
					<tr>
						<th rowspan = "3">주소</th>
						<td><input type = "text" name = "zip">
							<input type = "button" value = "우편번호" class="btn_white">
						</td>
					</tr>
					<tr>
						<td>
							<input type = "text" name = "addr1" id = "addr1" size="30">
							<p>기본주소</p>
						</td>
					</tr>
					<tr>
						<td>
							<input type = "text" name = "addr2" id = "addr2" size="30">
							<p>나머지주소</p>
						</td>
					</tr>
					
					<tr>
						<th>휴대전화</th>
						<td><select name = "phone1">
								<option value = "010">010</option>
								<option value = "011">011</option>
								<option value = "016">016</option>
								<option value = "017">017</option>
								<option value = "018">018</option>
								<option value = "019">019</option>
							</select> - 
							<input type = "text" name = "phone2" id = "phone2" size="5"> - 
							<input type = "text" name = "phone3" id = "phone3" size="5">
						</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td><input type = "email" name = "email"></td>
					</tr>
				</table>
				<div id = "join_btn">
					<input type = "submit" value ="수정" class = "btn_red">
					<input type = "button" onclick = "javascript:history.back()" value = "취소" class = "btn_white">
				</div>
			</form>
		</div>
		
	</div>
<%@ include file = "../include/bottom.jsp" %>