<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "path" value = "${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${path }/resources/css/user.css">
<link rel="stylesheet" href="${path }/resources/css/edit.css">
<script type="text/javascript" src="${path }/resources/js/jquery-3.5.1.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="${path }/resources/js/join.js"></script>
<script type="text/javascript">
$(function(){
	if('${msg}' != ''){
		alert("${msg}");
});
</script>
</head>
<body>
<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		<div id="join_box">
			<img src="${path }/resources/img/logo_box.png">
			<h2>회원정보수정</h2>
			<c:set var = "mem" value="${dto }" />
			<form name = "frm" method = "post" action="${path }/info_update.do" onsubmit = "return join_check()" enctype="multipart/form-data">
				<table>
					<tr>
						<th>이름 </th>
						<td><input type = "text" name = "mem_name" size = "20" value = "${mem.getMem_name() }">
					</tr>
					<tr>
						<th>아이디</th>
						<td>
						<input type = "hidden" name = "mem_id" size = "20" value = "${mem.getMem_id() }">
						${mem.getMem_id() }
						</td>	
					</tr>
					<tr>
						<th>비밀번호</th>
						<td><input type = "password" name = "mem_pwd" id = "pwd1"><br/>
							<!--  <font size = "2">(영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 8자~16자)</font> -->
						</td>
					</tr>
					<tr>
						<th>비밀번호 확인</th>
						<td><input type = "password" id = "pwd2">
						<p class = "txt" id = "pwd_check"></p>
						</td>
					</tr>
					<tr>
						<th>생일</th>
						<td><input type = "date" name = "mem_birth" value = "${mem.getMem_birth() }"></td>
					</tr>
					<tr>
						<th>성별</th>
						<td><input type = "radio" name = "mem_gender" value = "F" <c:if test = "${mem.getMem_gender() == 'F' }">checked</c:if>> 여자
							<input type = "radio" name = "mem_gender" value = "M" <c:if test = "${mem.getMem_gender() == 'M' }">checked</c:if>> 남자
					</tr>
					<tr>
						<c:set var = "addr" value = "${mem.getMem_addr().split('/') }" />
						<th rowspan = "3">주소</th>
						<td><input type = "text" name = "zip" id = "zip" value = "${addr[0] }">
							<input type = "button" value = "우편번호" class="btn_white" onclick = "addr_search()">
						</td>
					</tr>
					<tr>
						<td>
							<input type = "text" name = "addr1" id = "addr1" size="30" value = "${addr[1] }">
							<p>도로명주소</p>
						</td>
					</tr>
					<tr>
						<td>
							<input type = "text" name = "addr2" id = "addr2" size="30" value = "${addr[2] }">
							<p>나머지주소</p>
						</td>
					</tr>
					
					<tr>
						<th>휴대전화</th>
						<td>
							<c:set var = "phone" value = "${mem.getMem_phone().split('-') }" />
							<select name = "phone1">
								<option value = "010" <c:if test = "${phone[0] == '010' }">selected</c:if>>010</option>
								<option value = "011" <c:if test = "${phone[0] == '011' }">selected</c:if>>011</option>
								<option value = "016" <c:if test = "${phone[0] == '016' }">selected</c:if>>016</option>
								<option value = "017" <c:if test = "${phone[0] == '017' }">selected</c:if>>017</option>
								<option value = "018" <c:if test = "${phone[0] == '018' }">selected</c:if>>018</option>
								<option value = "019" <c:if test = "${phone[0] == '019' }">selected</c:if>>019</option>
							</select> - 
							<input type = "text" name = "phone2" size="5" value = "${phone[1] }"> - 
							<input type = "text" name = "phone3" size="5" value = "${phone[2] }">
						</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>
							<input type = "email" name = "mem_email" id = "email" value = "${mem.getMem_email() }">
							<input type = "button" value = "중복확인" class="btn_white" onclick = "email_check()">
						</td>
					</tr>
					<tr>
						<th>프로필사진</th>
						<td>
							<input type = "hidden" name = "mem_img" value = "${mem.getMem_img() }">
							<input type = "file" name = "file">
						</td>
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