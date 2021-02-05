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
<script type="text/javascript" src="${path }/resources/js/jquery-3.5.1.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="${path }/resources/js/join.js"></script>
<script type="text/javascript">
function id_check(){
	var mem_id = frm.id.value;
	
	if(mem_id == ""){
		alert("아이디를 입력해주세요");
	}else{
		$.ajax({
			url : "${pageContext.request.contextPath }/check_id.do",
			dataType : "text",
			data : {"mem_id" : mem_id},
			success : function(data){
				console.log(data);
				if(data > 0){
					alert("중복된 아이디가 있습니다.");
					$("#id").val("");
				}else{
					alert("사용 가능한 아이디입니다.");
				}
			},
			errror: function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});	
	}	
}

function addr_search() {
	new daum.Postcode({
	    oncomplete: function(data) {
	    	var roadAddr = data.roadAddress;
	        var extraRoadAddr = ''; 
	        
	        if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	            extraRoadAddr += data.bname;
	        }
	        
	        if(data.buildingName !== '' && data.apartment === 'Y'){
	            extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	         }
	        
	        if(extraRoadAddr !== ''){
	            extraRoadAddr = ' (' + extraRoadAddr + ')';
	        }
	        
	        $('#zip').val(data.zonecode);
	        $("#addr1").val(roadAddr);
	        
	        if(roadAddr !== ''){
	            $("#addr2").val(extraRoadAddr);
	        } else {
	        	$("#addr2").val('');
	        }

	    }
	}).open();	
}

function email_check() {
	var mem_email = $("#email").val();
	
	if(mem_email == ""){
		alert("이메일을 입력해주세요");
	}else{
		$.ajax({
			url : "${pageContext.request.contextPath }/email_check.do",
			dataType : "text",
			data : {"mem_email" : mem_email},
			success : function(data){
				console.log(data);
				if(data > 0){
					alert("중복된 이메일이 있습니다.");
					$("#email").val("");
				}else{
					alert("사용 가능한 이메일입니다..");
				}
			},
			errror: function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
}
</script>
</head>
<body>
<%@ include file = "../include/top.jsp" %>

	<div id = "content">
		<div id="join_box">
			<img src="${path }/resources/img/logo_box.png">
			<h2>회원가입</h2>
			<form name = "frm" action="#" onsubmit = "return join_check()">
				<table>
					<tr>
						<th>이름 </th>
						<td><input type = "text" name = "name" size = "20">
					</tr>
					<tr>
						<th>아이디</th>
						<td><input type = "text" name = "id" size = "20" id = "id">
							<input type = "button" value = "중복확인" class="btn_white" onclick = "id_check()">
						</td>	
					</tr>
					<tr>
						<th>비밀번호</th>
						<td><input type = "password" name = "pwd1"><br/>
							<!--  <font size = "2">(영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 8자~16자)</font> -->
						</td>
					</tr>
					<tr>
						<th>비밀번호 확인</th>
						<td><input type = "password" name = "pwd2">
						<p class = "txt"> </p>
						</td>
					</tr>
					<tr>
						<th>닉네임</th>
						<td><input type = "text" name = "nickname">
						</td>
					</tr>
					<tr>
						<th>생일</th>
						<td><input type = "date" name = "birth" ></td>
					</tr>
					<tr>
						<th>성별</th>
						<td><input type = "radio" name = "gender" value = "F"> 여자
							<input type = "radio" name = "gender" value = "M"> 남자
					</tr>
					<tr>
						<th rowspan = "3">주소</th>
						<td><input type = "text" name = "zip" id = "zip">
							<input type = "button" value = "우편번호" class="btn_white" onclick = "addr_search()">
						</td>
					</tr>
					<tr>
						<td>
							<input type = "text" name = "addr1" id = "addr1" size="30">
							<p>도로명주소</p>
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
							<input type = "text" name = "phone2" size="5"> - 
							<input type = "text" name = "phone3" size="5">
						</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>
							<input type = "email" name = "email" id = "email">
							<input type = "button" value = "중복확인" class="btn_white" onclick = "email_check()">
						</td>
					</tr>
				</table>
				<div id = "join_btn">
					<input type = "submit" value ="가입" class = "btn_red">
					<input type = "button" onclick = "javascript:history.back()" value = "취소" class = "btn_white">
				</div>
			</form>
		</div>
		
	</div>
<%@ include file = "../include/bottom.jsp" %>